//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Db;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.Mobile.ICD9m;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ICD9mCrud   
{
    /**
    * Gets one ICD9m object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static ICD9m selectOne(long customerNum, long iCD9Num) throws Exception {
        String command = "SELECT * FROM icd9m " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND ICD9Num = " + POut.long(iCD9Num);
        List<ICD9m> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ICD9m object from the database using a query.
    */
    public static ICD9m selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ICD9m> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ICD9m objects from the database using a query.
    */
    public static List<ICD9m> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ICD9m> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ICD9m> tableToList(DataTable table) throws Exception {
        List<ICD9m> retVal = new List<ICD9m>();
        ICD9m iCD9m;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            iCD9m = new ICD9m();
            iCD9m.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            iCD9m.ICD9Num = PIn.Long(table.Rows[i]["ICD9Num"].ToString());
            iCD9m.ICD9Code = PIn.String(table.Rows[i]["ICD9Code"].ToString());
            iCD9m.Description = PIn.String(table.Rows[i]["Description"].ToString());
            retVal.Add(iCD9m);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one ICD9m into the database.
    */
    public static long insert(ICD9m iCD9m, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            iCD9m.ICD9Num = ReplicationServers.getKey("icd9m","ICD9Num");
        }
         
        String command = "INSERT INTO icd9m (";
        command += "ICD9Num,";
        command += "CustomerNum,ICD9Code,Description) VALUES(";
        command += POut.long(iCD9m.ICD9Num) + ",";
        command += POut.long(iCD9m.CustomerNum) + "," + "'" + POut.string(iCD9m.ICD9Code) + "'," + "'" + POut.string(iCD9m.Description) + "')";
        Db.nonQ(command);
        return iCD9m.ICD9Num;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one ICD9m in the database.
    */
    public static void update(ICD9m iCD9m) throws Exception {
        String command = "UPDATE icd9m SET " + "ICD9Code   = '" + POut.string(iCD9m.ICD9Code) + "', " + "Description= '" + POut.string(iCD9m.Description) + "' " + "WHERE CustomerNum = " + POut.long(iCD9m.CustomerNum) + " AND ICD9Num = " + POut.long(iCD9m.ICD9Num);
        Db.nonQ(command);
    }

    /**
    * Deletes one ICD9m from the database.
    */
    public static void delete(long customerNum, long iCD9Num) throws Exception {
        String command = "DELETE FROM icd9m " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND ICD9Num = " + POut.long(iCD9Num);
        Db.nonQ(command);
    }

    /**
    * Converts one ICD9 object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static ICD9m convertToM(ICD9 iCD9) throws Exception {
        ICD9m iCD9m = new ICD9m();
        //CustomerNum cannot be set.  Remains 0.
        iCD9m.ICD9Num = iCD9.ICD9Num;
        iCD9m.ICD9Code = iCD9.ICD9Code;
        iCD9m.Description = iCD9.Description;
        return iCD9m;
    }

}


