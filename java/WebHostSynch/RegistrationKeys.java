//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import OpenDentBusiness.Family;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import WebHostSynch.RegistrationKey;

/**
* Used to keep track of which product keys have been assigned to which customers. This class is only used if the program is being run from a distributor installation.
*/
public class RegistrationKeys   
{
    private WebHostSynch.Db db = new WebHostSynch.Db();
    public void setDb(String connectStr) throws Exception {
        db.setConn(connectStr);
    }

    /**
    * Retrieves all registration keys for a particular customer's family. There can be multiple keys assigned to a single customer, or keys assigned to individual family members, since the customer may have multiple physical locations of business.
    */
    public RegistrationKey[] getForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RegistrationKey[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM registrationkey WHERE ";
        Family fam = Patients.getFamily(patNum);
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            command += "PatNum=" + POut.Long(fam.ListPats[i].PatNum) + " ";
            if (i < fam.ListPats.Length - 1)
            {
                command += "OR ";
            }
             
        }
        DataTable table = db.getTable(command);
        RegistrationKey[] keys = new RegistrationKey[table.Rows.Count];
        for (int i = 0;i < keys.Length;i++)
        {
            keys[i] = new RegistrationKey();
            keys[i].RegistrationKeyNum = PIn.Long(table.Rows[i][0].ToString());
            keys[i].PatNum = PIn.Long(table.Rows[i][1].ToString());
            keys[i].RegKey = PIn.String(table.Rows[i][2].ToString());
            keys[i].Note = PIn.String(table.Rows[i][3].ToString());
            keys[i].DateStarted = PIn.Date(table.Rows[i][4].ToString());
            keys[i].DateDisabled = PIn.Date(table.Rows[i][5].ToString());
            keys[i].DateEnded = PIn.Date(table.Rows[i][6].ToString());
            keys[i].IsForeign = PIn.Bool(table.Rows[i][7].ToString());
            keys[i].UsesServerVersion = PIn.Bool(table.Rows[i][8].ToString());
            keys[i].IsFreeVersion = PIn.Bool(table.Rows[i][9].ToString());
            keys[i].IsOnlyForTesting = PIn.Bool(table.Rows[i][10].ToString());
            keys[i].VotesAllotted = PIn.Int(table.Rows[i][11].ToString());
        }
        return keys;
    }

    /**
    * Returns true if the given registration key is currently in use by a customer, false otherwise.
    */
    public boolean keyIsInUse(String regKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), regKey);
        }
         
        String command = "SELECT RegKey FROM registrationkey WHERE RegKey='" + POut.string(regKey) + "'";
        DataTable table = db.getTable(command);
        return (table.Rows.Count > 0);
    }

    public RegistrationKey getByKey(String regKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RegistrationKey>GetObject(MethodBase.GetCurrentMethod(), regKey);
        }
         
        if (!Regex.IsMatch(regKey, "^[A-Z0-9]{16}$"))
        {
            throw new ApplicationException("Invalid registration key format.");
        }
         
        String command = "SELECT * FROM  registrationkey WHERE RegKey='" + POut.string(regKey) + "'";
        DataTable table = db.getTable(command);
        if (table.Rows.Count == 0)
        {
            throw new ApplicationException("Invalid registration key.");
        }
         
        RegistrationKey key = null;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            key = new RegistrationKey();
            key.RegistrationKeyNum = PIn.Int(table.Rows[i][0].ToString());
            key.PatNum = PIn.Int(table.Rows[i][1].ToString());
            key.RegKey = PIn.String(table.Rows[i][2].ToString());
            key.Note = PIn.String(table.Rows[i][3].ToString());
            key.DateStarted = PIn.Date(table.Rows[i][4].ToString());
            key.DateDisabled = PIn.Date(table.Rows[i][5].ToString());
            key.DateEnded = PIn.Date(table.Rows[i][6].ToString());
            key.IsForeign = PIn.Bool(table.Rows[i][7].ToString());
            //key.UsesServerVersion  	=PIn.PBool(table.Rows[i][8].ToString());
            key.IsFreeVersion = PIn.Bool(table.Rows[i][9].ToString());
            key.IsOnlyForTesting = PIn.Bool(table.Rows[i][10].ToString());
        }
        return key;
    }

}


//key.VotesAllotted     	=PIn.PInt(table.Rows[i][11].ToString());
//if(key.DateDisabled.Year>1880){
//	throw new ApplicationException("This key has been disabled.  Please call for assistance.");
//}