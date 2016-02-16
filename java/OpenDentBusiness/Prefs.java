//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Pref;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Prefs   
{
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM preference";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Pref";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        PrefC.Dict = new Dictionary<String, Pref>();
        Pref pref;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //PrefName enumpn;
            //Can't use Crud.PrefCrud.TableToList(table) because it will fail the first time someone runs 7.6 before conversion.
            pref = new Pref();
            if (table.Columns.Contains("PrefNum"))
            {
                pref.PrefNum = PIn.Long(table.Rows[i]["PrefNum"].ToString());
            }
             
            pref.PrefName = PIn.String(table.Rows[i]["PrefName"].ToString());
            pref.ValueString = PIn.String(table.Rows[i]["ValueString"].ToString());
            //no need to load up the comments.  Especially since this will fail when user first runs version 5.8.
            PrefC.Dict.Add(pref.PrefName, pref);
        }
    }

    /**
    * 
    */
    public static void clearCache() throws Exception {
        PrefC.Dict = null;
    }

    /**
    * 
    */
    public static void update(Pref pref) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pref);
            return ;
        }
         
        //Don't use CRUD here because we want to update based on PrefName instead of PrefNum.  Otherwise, it might fail the first time someone runs 7.6.
        String command = "UPDATE preference SET " + "ValueString = '" + POut.string(pref.ValueString) + "' " + " WHERE PrefName = '" + POut.string(pref.PrefName) + "'";
        Db.nonQ(command);
    }

    /**
    * Updates a pref of type int.  Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateInt(PrefName prefName, int newValue) throws Exception {
        return UpdateLong(prefName, newValue);
    }

    /**
    * Updates a pref of type long.  Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateLong(PrefName prefName, long newValue) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName.ToString()))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (PrefC.getLong(prefName) == newValue)
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.long(newValue) + "' " + "WHERE PrefName = '" + POut.String(prefName.ToString()) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue);
        }
        else
        {
            Db.nonQ(command);
        } 
        Pref pref = new Pref();
        pref.PrefName = prefName.ToString();
        pref.ValueString = newValue.ToString();
        PrefC.Dict[prefName.ToString()] = pref;
        return retVal;
    }

    //in some cases, we just want to change the pref in local memory instead of doing a refresh afterwards.
    /**
    * Updates a pref of type double.  Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateDouble(PrefName prefName, double newValue) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName.ToString()))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (PrefC.getDouble(prefName) == newValue)
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.double(newValue) + "' " + "WHERE PrefName = '" + POut.String(prefName.ToString()) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue);
        }
        else
        {
            Db.nonQ(command);
        } 
        return retVal;
    }

    /**
    * Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateBool(PrefName prefName, boolean newValue) throws Exception {
        return updateBool(prefName,newValue,false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateBool(PrefName prefName, boolean newValue, boolean isForced) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName.ToString()))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (!isForced && PrefC.getBool(prefName) == newValue)
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.bool(newValue) + "' " + "WHERE PrefName = '" + POut.String(prefName.ToString()) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue, isForced);
        }
        else
        {
            Db.nonQ(command);
        } 
        Pref pref = new Pref();
        pref.PrefName = prefName.ToString();
        pref.ValueString = POut.bool(newValue);
        PrefC.Dict[prefName.ToString()] = pref;
        return retVal;
    }

    /**
    * Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateString(PrefName prefName, String newValue) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName.ToString()))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (StringSupport.equals(PrefC.getString(prefName), newValue))
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.string(newValue) + "' " + "WHERE PrefName = '" + POut.String(prefName.ToString()) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue);
        }
        else
        {
            Db.nonQ(command);
        } 
        Pref pref = new Pref();
        pref.PrefName = prefName.ToString();
        pref.ValueString = newValue;
        PrefC.Dict[prefName.ToString()] = pref;
        return retVal;
    }

    /**
    * Used for prefs that are non-standard.  Especially by outside programmers. Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateRaw(String prefName, String newValue) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (StringSupport.equals(PrefC.getRaw(prefName), newValue))
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.string(newValue) + "' " + "WHERE PrefName = '" + POut.string(prefName) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue);
        }
        else
        {
            Db.nonQ(command);
        } 
        Pref pref = new Pref();
        pref.PrefName = prefName;
        pref.ValueString = newValue;
        PrefC.Dict[prefName] = pref;
        return retVal;
    }

    /**
    * Returns true if a change was required, or false if no change needed.
    */
    public static boolean updateDateT(PrefName prefName, DateTime newValue) throws Exception {
        //Very unusual.  Involves cache, so Meth is used further down instead of here at the top.
        if (!PrefC.Dict.ContainsKey(prefName.ToString()))
        {
            throw new ApplicationException(prefName + " is an invalid pref name.");
        }
         
        if (PrefC.getDateT(prefName) == newValue)
        {
            return false;
        }
         
        //no change needed
        String command = "UPDATE preference SET " + "ValueString = '" + POut.dateT(newValue,false) + "' " + "WHERE PrefName = '" + POut.String(prefName.ToString()) + "'";
        boolean retVal = true;
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            retVal = Meth.GetBool(MethodBase.GetCurrentMethod(), prefName, newValue);
        }
        else
        {
            Db.nonQ(command);
        } 
        Pref pref = new Pref();
        pref.PrefName = prefName.ToString();
        pref.ValueString = POut.dateT(newValue,false);
        PrefC.Dict[prefName.ToString()] = pref;
        return retVal;
    }

    //in some cases, we just want to change the pref in local memory instead of doing a refresh afterwards.
    /**
    * Only run from PrefL.CheckMySqlVersion41().
    */
    public static void convertToMySqlVersion41() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "SHOW TABLES";
        DataTable table = Db.getTable(command);
        String[] tableNames = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            tableNames[i] = table.Rows[i][0].ToString();
        }
        for (int i = 0;i < tableNames.Length;i++)
        {
            if (!StringSupport.equals(tableNames[i], "procedurecode"))
            {
                command = "ALTER TABLE " + tableNames[i] + " CONVERT TO CHARACTER SET utf8";
                Db.nonQ(command);
            }
             
        }
        String[] commands = new String[]{ "ALTER TABLE procedurecode MODIFY Descript varchar(255) character set utf8 NOT NULL", "ALTER TABLE procedurecode MODIFY AbbrDesc varchar(50) character set utf8 NOT NULL", "ALTER TABLE procedurecode MODIFY ProcTime varchar(24) character set utf8 NOT NULL", "ALTER TABLE procedurecode MODIFY DefaultNote text character set utf8 NOT NULL", "ALTER TABLE procedurecode MODIFY AlternateCode1 varchar(15) character set utf8 NOT NULL" };
        //"ALTER TABLE procedurecode CHANGE OldCode OldCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE procedurecode DEFAULT character set utf8"
        //,"ALTER TABLE procedurelog MODIFY OldCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE autocodeitem MODIFY OldCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE procbuttonitem MODIFY OldCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE covspan MODIFY FromCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE covspan MODIFY ToCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        //,"ALTER TABLE fee MODIFY OldCode varchar(15) character set utf8 collate utf8_bin NOT NULL"
        Db.NonQ(commands);
        //and set the default too
        command = "ALTER DATABASE CHARACTER SET utf8";
        Db.nonQ(command);
        command = "INSERT INTO preference VALUES('DatabaseConvertedForMySql41','1')";
        Db.nonQ(command);
    }

    /**
    * Gets a Pref object when the PrefName is provided
    */
    public static Pref getPref(String PrefName) throws Exception {
        Pref pref = PrefC.Dict[PrefName];
        return pref;
    }

}


