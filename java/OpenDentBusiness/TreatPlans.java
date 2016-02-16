//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcTP;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TreatPlan;

/**
* 
*/
public class TreatPlans   
{
    /**
    * Gets all TreatPlans for a given Patient, ordered by date.
    */
    public static TreatPlan[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TreatPlan[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM treatplan " + "WHERE PatNum=" + POut.long(patNum) + " ORDER BY DateTP";
        return Crud.TreatPlanCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(TreatPlan tp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), tp);
            return ;
        }
         
        Crud.TreatPlanCrud.Update(tp);
    }

    /**
    * 
    */
    public static long insert(TreatPlan tp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            tp.TreatPlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), tp);
            return tp.TreatPlanNum;
        }
         
        return Crud.TreatPlanCrud.Insert(tp);
    }

    /**
    * Dependencies checked first and throws an exception if any found. So surround by try catch
    */
    public static void delete(TreatPlan tp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), tp);
            return ;
        }
         
        //check proctp for dependencies
        String command = "SELECT * FROM proctp WHERE TreatPlanNum =" + POut.long(tp.TreatPlanNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            throw new InvalidProgramException(Lans.g("TreatPlans","Cannot delete treatment plan because it has ProcTP's attached"));
        }
         
        //this should never happen
        command = "DELETE from treatplan WHERE TreatPlanNum = '" + POut.long(tp.TreatPlanNum) + "'";
        Db.nonQ(command);
    }

    public static String getHashString(TreatPlan tp, List<ProcTP> proclist) throws Exception {
        //No need to check RemotingRole; no call to db.
        //the key data is a concatenation of the following:
        //tp: Note, DateTP
        //each proctp: Descript,PatAmt
        //The procedures MUST be in the correct order, and we'll use ItemOrder to order them.
        StringBuilder strb = new StringBuilder();
        strb.Append(tp.Note);
        strb.Append(tp.DateTP.ToString("yyyyMMdd"));
        for (int i = 0;i < proclist.Count;i++)
        {
            strb.Append(proclist[i].Descript);
            strb.Append(proclist[i].PatAmt.ToString("F2"));
        }
        byte[] textbytes = Encoding.UTF8.GetBytes(strb.ToString());
        //byte[] filebytes = GetBytes(doc);
        //int fileLength = filebytes.Length;
        //byte[] buffer = new byte[textbytes.Length + filebytes.Length];
        //Array.Clone(filebytes,0,buffer,0,fileLength);
        //Array.Clone(textbytes,0,buffer,fileLength,textbytes.Length);
        HashAlgorithm algorithm = MD5.Create();
        byte[] hash = algorithm.ComputeHash(textbytes);
        return Encoding.ASCII.GetString(hash);
    }

}


//always results in length of 16.