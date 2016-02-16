//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Family;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RegistrationKey;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Used to keep track of which product keys have been assigned to which customers. This class is only used if the program is being run from a distributor installation.
*/
public class RegistrationKeys   
{
    /**
    * Retrieves all registration keys for a particular customer's family. There can be multiple keys assigned to a single customer, or keys assigned to individual family members, since the customer may have multiple physical locations of business.
    */
    public static RegistrationKey[] getForPatient(long patNum) throws Exception {
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
        return Crud.RegistrationKeyCrud.SelectMany(command).ToArray();
    }

    /**
    * Updates the given key data to the database.
    */
    public static void update(RegistrationKey registrationKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), registrationKey);
            return ;
        }
         
        Crud.RegistrationKeyCrud.Update(registrationKey);
    }

    /**
    * Inserts a new and unique registration key into the database.
    */
    public static long insert(RegistrationKey registrationKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            registrationKey.RegistrationKeyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), registrationKey);
            return registrationKey.RegistrationKeyNum;
        }
         
        do
        {
            if (registrationKey.IsForeign)
            {
                Random rand = new Random();
                StringBuilder strBuild = new StringBuilder();
                for (int i = 0;i < 16;i++)
                {
                    int r = rand.Next(0, 35);
                    if (r < 10)
                    {
                        strBuild.Append((char)('0' + r));
                    }
                    else
                    {
                        strBuild.Append((char)('A' + r - 10));
                    } 
                }
                registrationKey.RegKey = strBuild.ToString();
            }
            else
            {
                registrationKey.RegKey = CDT.Class1.GenerateRandKey();
            } 
            if (StringSupport.equals(registrationKey.RegKey, ""))
            {
                return 0;
            }
             
        }
        while (keyIsInUse(registrationKey.RegKey));
        return Crud.RegistrationKeyCrud.Insert(registrationKey);
    }

    //Don't loop forever when software is unverified.
    //not sure what consequence this would have.
    public static void delete(long registrationKeyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), registrationKeyNum);
            return ;
        }
         
        String command = "DELETE FROM registrationkey WHERE RegistrationKeyNum='" + POut.long(registrationKeyNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Returns true if the given registration key is currently in use by a customer, false otherwise.
    */
    public static boolean keyIsInUse(String regKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), regKey);
        }
         
        String command = "SELECT RegKey FROM registrationkey WHERE RegKey='" + POut.string(regKey) + "'";
        DataTable table = Db.getTable(command);
        return (table.Rows.Count > 0);
    }

    /**
    * Returns any active registration keys that have no repeating charges on any corresponding family members.  Columns include PatNum, LName, FName, and RegKey.
    */
    public static DataTable getAllWithoutCharges() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        /*
        			DataTable table=new DataTable();
        			table.Columns.Add("dateStop");
        			table.Columns.Add("family");
        			table.Columns.Add("PatNum");
        			table.Columns.Add("RegKey");
        			string command=@"
        				DROP TABLE IF EXISTS tempRegKeys;
        				CREATE TABLE tempRegKeys(
        					tempRegKeyId int auto_increment NOT NULL,
        					PatNum bigint NOT NULL,
        					RegKey VARCHAR(255) NOT NULL,
        					IsMissing tinyint NOT NULL,
        					Date_ DATE NOT NULL DEFAULT '0001-01-01',
        					PRIMARY KEY(tempRegKeyId),
        					KEY(PatNum));
        				-- Fill table with patnums for all guarantors of regkeys that are still active.
        				INSERT INTO tempRegKeys (PatNum,RegKey,Date_) 
        				SELECT patient.Guarantor,RegKey,'0001-01-01'
        				FROM registrationkey
        				LEFT JOIN patient ON registrationkey.PatNum=patient.PatNum
        				WHERE DateDisabled='0001-01-01'
        				AND DateEnded='0001-01-01'
        				AND IsFreeVersion=0 
        				AND IsOnlyForTesting=0;
        				-- Set indicators on keys with missing repeatcharges
        				UPDATE tempRegKeys
        				SET IsMissing=1
        				WHERE NOT EXISTS(SELECT * FROM repeatcharge WHERE repeatcharge.PatNum=tempRegKeys.PatNum);
        				-- Now, look for expired repeating charges.  This is done in two steps.
        				-- Step 1: Mark all keys that have expired repeating charges.
        				-- Step 2: Then, remove those markings for all keys that also have unexpired repeating charges.
        				UPDATE tempRegKeys
        				SET Date_=(
        				SELECT IFNULL(MAX(DateStop),'0001-01-01')
        				FROM repeatcharge
        				WHERE repeatcharge.PatNum=tempRegKeys.PatNum
        				AND DateStop < "+DbHelper.Now()+@" AND DateStop > '0001-01-01');
        				-- Step 2:
        				UPDATE tempRegKeys
        				SET Date_='0001-01-01'
        				WHERE EXISTS(
        				SELECT * FROM repeatcharge
        				WHERE repeatcharge.PatNum=tempRegKeys.PatNum
        				AND DateStop = '0001-01-01');
        				SELECT LName,FName,tempRegKeys.PatNum,tempRegKeys.RegKey,IsMissing,Date_
        				FROM tempRegKeys
        				LEFT JOIN patient ON patient.PatNum=tempRegKeys.PatNum
        				WHERE IsMissing=1
        				OR Date_ > '0001-01-01'
        				ORDER BY tempRegKeys.PatNum;
        				DROP TABLE IF EXISTS tempRegKeys;";
        			DataTable raw=Db.GetTable(command);
        			DataRow row;
        			DateTime dateRepeatStop;
        			for(int i=0;i<raw.Rows.Count;i++) {
        				row=table.NewRow();
        				if(raw.Rows[i]["IsMissing"].ToString()=="1") {
        					row["dateStop"]="Missing Repeat Charge";
        				}
        				else {
        					row["dateStop"]="";
        				}
        				dateRepeatStop=PIn.Date(raw.Rows[i]["Date_"].ToString());
        				if(dateRepeatStop.Year>1880) {
        					if(row["dateStop"].ToString()!="") {
        						row["dateStop"]+="\r\n";
        					}
        					row["dateStop"]+="Expired Repeat Charge:"+dateRepeatStop.ToShortDateString();
        				}
        				row["family"]=raw.Rows[i]["LName"].ToString()+", "+raw.Rows[i]["FName"].ToString();
        				row["PatNum"]=raw.Rows[i]["PatNum"].ToString();
        				row["RegKey"]=raw.Rows[i]["RegKey"].ToString();
        				table.Rows.Add(row);
        			}
        			return table;
        			*/
        //The detailed queries above were taking far too long and were too complicated.
        //Instead, we will look for any active registration keys that have no repeating charges on any corresponding family members.
        String command = "SELECT registrationkey.PatNum,registrationkey.RegKey,patient.LName,patient.FName\r\n" + 
        "\t\t\t\tFROM registrationkey\r\n" + 
        "\t\t\t\tLEFT JOIN patient ON registrationkey.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN (\r\n" + 
        "\t\t\t\t\tSELECT family.PatNum\r\n" + 
        "\t\t\t\t\tFROM patient family\r\n" + 
        "\t\t\t\t\tWHERE family.PatNum IN (SELECT repeatcharge.PatNum FROM repeatcharge \r\n" + 
        "\t\t\t\t\t\tWHERE repeatcharge.DateStop >= NOW() OR repeatcharge.DateStop=\'0001-01-01\')\r\n" + 
        "\t\t\t\t\tOR family.Guarantor IN (SELECT repeatcharge.PatNum FROM repeatcharge\r\n" + 
        "\t\t\t\t\t\tWHERE repeatcharge.DateStop >= NOW() OR repeatcharge.DateStop=\'0001-01-01\')\r\n" + 
        "\t\t\t\t\t) AS activecharges ON registrationkey.PatNum=activecharges.PatNum \r\n" + 
        "\t\t\t\tWHERE registrationkey.DateDisabled=\'0001-01-01\'\r\n" + 
        "\t\t\t\tAND registrationkey.DateEnded=\'0001-01-01\'\r\n" + 
        "\t\t\t\tAND registrationkey.IsFreeVersion=0 \r\n" + 
        "\t\t\t\tAND registrationkey.IsOnlyForTesting=0\r\n" + 
        "\t\t\t\tAND ISNULL(activecharges.PatNum)";
        return Db.getTable(command);
    }

    public static RegistrationKey getByKey(String regKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RegistrationKey>GetObject(MethodBase.GetCurrentMethod(), regKey);
        }
         
        if (!Regex.IsMatch(regKey, "^[A-Z0-9]{16}$"))
        {
            throw new ApplicationException("Invalid registration key format.");
        }
         
        String command = "SELECT * FROM  registrationkey WHERE RegKey='" + POut.string(regKey) + "'";
        DataTable table = Db.getTable(command);
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