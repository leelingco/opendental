//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PatientNotes   
{
    /**
    * 
    */
    public static PatientNote refresh(long patNum, long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PatientNote>GetObject(MethodBase.GetCurrentMethod(), patNum, guarantor);
        }
         
        String command = "SELECT COUNT(*) FROM patientnote WHERE patnum = '" + POut.long(patNum) + "'";
        DataTable table = Db.getTable(command);
        if (StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            insertRow(patNum);
        }
         
        command = "SELECT * FROM patientnote WHERE patnum ='" + POut.long(patNum) + "'";
        PatientNote Cur = Crud.PatientNoteCrud.SelectOne(command);
        //fam financial note:
        command = "SELECT * FROM patientnote WHERE patnum ='" + POut.long(guarantor) + "'";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            insertRow(guarantor);
        }
         
        command = "SELECT famfinancial FROM patientnote WHERE patnum ='" + POut.long(guarantor) + "'";
        table = Db.getTable(command);
        Cur.FamFinancial = PIn.String(table.Rows[0][0].ToString());
        return Cur;
    }

    //overrides original FamFinancial value.
    /**
    * 
    */
    public static void update(PatientNote Cur, long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur, guarantor);
            return ;
        }
         
        Crud.PatientNoteCrud.Update(Cur);
        //FamFinancial gets skipped
        String command = "UPDATE patientnote SET " + "FamFinancial = '" + POut.string(Cur.FamFinancial) + "'" + " WHERE patnum = '" + POut.long(guarantor) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    private static void insertRow(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        try
        {
            //Random keys not necessary to check because of 1:1 patNum.
            //However, this is a lazy insert, so multiple locations might attempt it.
            //Just in case, we will have it fail silently.
            String command = "INSERT INTO patientnote (patnum" + ") VALUES('" + patNum + "')";
            Db.nonQ(command);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

}


//Fail Silently.