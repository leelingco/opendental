//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Laboratories   
{
    /**
    * Refresh all Laboratories
    */
    public static List<Laboratory> refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Laboratory>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM laboratory ORDER BY Description";
        return Crud.LaboratoryCrud.SelectMany(command);
    }

    /**
    * Gets one laboratory from database
    */
    public static Laboratory getOne(long laboratoryNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Laboratory>GetObject(MethodBase.GetCurrentMethod(), laboratoryNum);
        }
         
        String command = "SELECT * FROM laboratory WHERE LaboratoryNum=" + POut.long(laboratoryNum);
        return Crud.LaboratoryCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(Laboratory laboratory) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            laboratory.LaboratoryNum = Meth.GetLong(MethodBase.GetCurrentMethod(), laboratory);
            return laboratory.LaboratoryNum;
        }
         
        return Crud.LaboratoryCrud.Insert(laboratory);
    }

    /**
    * 
    */
    public static void update(Laboratory laboratory) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), laboratory);
            return ;
        }
         
        Crud.LaboratoryCrud.Update(laboratory);
    }

    /**
    * Checks dependencies first.  Throws exception if can't delete.
    */
    public static void delete(long laboratoryNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), laboratoryNum);
            return ;
        }
         
        String command = new String();
        //check lab cases for dependencies
        command = "SELECT LName,FName FROM patient,labcase " + "WHERE patient.PatNum=labcase.PatNum " + "AND LaboratoryNum =" + POut.long(laboratoryNum) + " " + DbHelper.limitAnd(30);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Laboratories","Cannot delete Laboratory because cases exist for") + pats);
        }
         
        //delete
        command = "DELETE FROM laboratory WHERE LaboratoryNum = " + POut.long(laboratoryNum);
        Db.nonQ(command);
    }

}


