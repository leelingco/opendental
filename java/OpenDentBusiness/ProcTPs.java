//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcTP;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ProcTPs   
{
    /**
    * Gets all ProcTPs for a given Patient ordered by ItemOrder.
    */
    public static ProcTP[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ProcTP[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM proctp " + "WHERE PatNum=" + POut.long(patNum) + " ORDER BY ItemOrder";
        return Crud.ProcTPCrud.SelectMany(command).ToArray();
    }

    /**
    * Only used when obtaining the signature data.  Ordered by ItemOrder.
    */
    public static List<ProcTP> refreshForTP(long tpNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcTP>>GetObject(MethodBase.GetCurrentMethod(), tpNum);
        }
         
        String command = "SELECT * FROM proctp " + "WHERE TreatPlanNum=" + POut.long(tpNum) + " ORDER BY ItemOrder";
        DataTable table = Db.getTable(command);
        return Crud.ProcTPCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(ProcTP proc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), proc);
            return ;
        }
         
        Crud.ProcTPCrud.Update(proc);
    }

    /**
    * 
    */
    public static long insert(ProcTP proc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            proc.ProcTPNum = Meth.GetLong(MethodBase.GetCurrentMethod(), proc);
            return proc.ProcTPNum;
        }
         
        return Crud.ProcTPCrud.Insert(proc);
    }

    /**
    * 
    */
    public static void insertOrUpdate(ProcTP proc, boolean isNew) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (isNew)
        {
            insert(proc);
        }
        else
        {
            update(proc);
        } 
    }

    /**
    * There are no dependencies.
    */
    public static void delete(ProcTP proc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), proc);
            return ;
        }
         
        String command = "DELETE from proctp WHERE ProcTPNum = '" + POut.long(proc.ProcTPNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets a list for just one tp.  Used in TP module.  Supply a list of all ProcTPs for pt.
    */
    public static ProcTP[] getListForTP(long treatPlanNum, ProcTP[] listAll) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < listAll.Length;i++)
        {
            if (listAll[i].TreatPlanNum != treatPlanNum)
            {
                continue;
            }
             
            AL.Add(listAll[i]);
        }
        ProcTP[] retVal = new ProcTP[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * No dependencies to worry about.
    */
    public static void deleteForTP(long treatPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), treatPlanNum);
            return ;
        }
         
        String command = "DELETE FROM proctp " + "WHERE TreatPlanNum=" + POut.long(treatPlanNum);
        Db.nonQ(command);
    }

}


