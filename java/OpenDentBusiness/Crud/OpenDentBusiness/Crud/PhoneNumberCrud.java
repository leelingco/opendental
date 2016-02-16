//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PhoneNumberCrud   
{
    /**
    * Gets one PhoneNumber object from the database using the primary key.  Returns null if not found.
    */
    public static PhoneNumber selectOne(long phoneNumberNum) throws Exception {
        String command = "SELECT * FROM phonenumber " + "WHERE PhoneNumberNum = " + POut.Long(phoneNumberNum);
        List<PhoneNumber> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one PhoneNumber object from the database using a query.
    */
    public static PhoneNumber selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PhoneNumber> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of PhoneNumber objects from the database using a query.
    */
    public static List<PhoneNumber> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PhoneNumber> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<PhoneNumber> tableToList(DataTable table) throws Exception {
        List<PhoneNumber> retVal = new List<PhoneNumber>();
        PhoneNumber phoneNumber = new PhoneNumber();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            phoneNumber = new PhoneNumber();
            phoneNumber.PhoneNumberNum = PIn.Long(table.Rows[i]["PhoneNumberNum"].ToString());
            phoneNumber.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            phoneNumber.PhoneNumberVal = PIn.String(table.Rows[i]["PhoneNumberVal"].ToString());
            retVal.Add(phoneNumber);
        }
        return retVal;
    }

    /**
    * Inserts one PhoneNumber into the database.  Returns the new priKey.
    */
    public static long insert(PhoneNumber phoneNumber) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            phoneNumber.PhoneNumberNum = DbHelper.GetNextOracleKey("phonenumber", "PhoneNumberNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(phoneNumber,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        phoneNumber.PhoneNumberNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(phoneNumber,false);
        } 
    }

    /**
    * Inserts one PhoneNumber into the database.  Provides option to use the existing priKey.
    */
    public static long insert(PhoneNumber phoneNumber, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            phoneNumber.PhoneNumberNum = ReplicationServers.GetKey("phonenumber", "PhoneNumberNum");
        }
         
        String command = "INSERT INTO phonenumber (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PhoneNumberNum,";
        }
         
        command += "PatNum,PhoneNumberVal) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(phoneNumber.PhoneNumberNum) + ",";
        }
         
        command += POut.Long(phoneNumber.PatNum) + "," + "'" + POut.String(phoneNumber.PhoneNumberVal) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            phoneNumber.PhoneNumberNum = Db.NonQ(command, true);
        } 
        return phoneNumber.PhoneNumberNum;
    }

    /**
    * Updates one PhoneNumber in the database.
    */
    public static void update(PhoneNumber phoneNumber) throws Exception {
        String command = "UPDATE phonenumber SET " + "PatNum        =  " + POut.Long(phoneNumber.PatNum) + ", " + "PhoneNumberVal= '" + POut.String(phoneNumber.PhoneNumberVal) + "' " + "WHERE PhoneNumberNum = " + POut.Long(phoneNumber.PhoneNumberNum);
        Db.NonQ(command);
    }

    /**
    * Updates one PhoneNumber in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(PhoneNumber phoneNumber, PhoneNumber oldPhoneNumber) throws Exception {
        String command = "";
        if (phoneNumber.PatNum != oldPhoneNumber.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(phoneNumber.PatNum) + "";
        }
         
        if (phoneNumber.PhoneNumberVal != oldPhoneNumber.PhoneNumberVal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PhoneNumberVal = '" + POut.String(phoneNumber.PhoneNumberVal) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE phonenumber SET " + command + " WHERE PhoneNumberNum = " + POut.Long(phoneNumber.PhoneNumberNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one PhoneNumber from the database.
    */
    public static void delete(long phoneNumberNum) throws Exception {
        String command = "DELETE FROM phonenumber " + "WHERE PhoneNumberNum = " + POut.Long(phoneNumberNum);
        Db.NonQ(command);
    }

}

