//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class OIDExternalCrud   
{
    /**
    * Gets one OIDExternal object from the database using the primary key.  Returns null if not found.
    */
    public static OIDExternal selectOne(long oIDExternalNum) throws Exception {
        String command = "SELECT * FROM oidexternal " + "WHERE OIDExternalNum = " + POut.Long(oIDExternalNum);
        List<OIDExternal> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one OIDExternal object from the database using a query.
    */
    public static OIDExternal selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<OIDExternal> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of OIDExternal objects from the database using a query.
    */
    public static List<OIDExternal> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<OIDExternal> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<OIDExternal> tableToList(DataTable table) throws Exception {
        List<OIDExternal> retVal = new List<OIDExternal>();
        OIDExternal oIDExternal = new OIDExternal();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            oIDExternal = new OIDExternal();
            oIDExternal.OIDExternalNum = PIn.Long(table.Rows[i]["OIDExternalNum"].ToString());
            String iDType = table.Rows[i]["IDType"].ToString();
            if (StringSupport.equals(iDType, ""))
            {
                oIDExternal.IDType = (IdentifierType)0;
            }
            else
                try
                {
                    oIDExternal.IDType = (IdentifierType)Enum.Parse(IdentifierType.class, iDType);
                }
                catch (Exception __dummyCatchVar0)
                {
                    oIDExternal.IDType = (IdentifierType)0;
                }
             
            oIDExternal.IDInternal = PIn.Long(table.Rows[i]["IDInternal"].ToString());
            oIDExternal.IDExternal = PIn.String(table.Rows[i]["IDExternal"].ToString());
            oIDExternal.rootExternal = PIn.String(table.Rows[i]["rootExternal"].ToString());
            retVal.Add(oIDExternal);
        }
        return retVal;
    }

    /**
    * Inserts one OIDExternal into the database.  Returns the new priKey.
    */
    public static long insert(OIDExternal oIDExternal) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            oIDExternal.OIDExternalNum = DbHelper.GetNextOracleKey("oidexternal", "OIDExternalNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(oIDExternal,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        oIDExternal.OIDExternalNum++;
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
            return insert(oIDExternal,false);
        } 
    }

    /**
    * Inserts one OIDExternal into the database.  Provides option to use the existing priKey.
    */
    public static long insert(OIDExternal oIDExternal, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            oIDExternal.OIDExternalNum = ReplicationServers.GetKey("oidexternal", "OIDExternalNum");
        }
         
        String command = "INSERT INTO oidexternal (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "OIDExternalNum,";
        }
         
        command += "IDType,IDInternal,IDExternal,rootExternal) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(oIDExternal.OIDExternalNum) + ",";
        }
         
        command += "'" + POut.String(oIDExternal.IDType.ToString()) + "'," + POut.Long(oIDExternal.IDInternal) + "," + "'" + POut.String(oIDExternal.IDExternal) + "'," + "'" + POut.String(oIDExternal.rootExternal) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            oIDExternal.OIDExternalNum = Db.NonQ(command, true);
        } 
        return oIDExternal.OIDExternalNum;
    }

    /**
    * Updates one OIDExternal in the database.
    */
    public static void update(OIDExternal oIDExternal) throws Exception {
        String command = "UPDATE oidexternal SET " + "IDType        = '" + POut.String(oIDExternal.IDType.ToString()) + "', " + "IDInternal    =  " + POut.Long(oIDExternal.IDInternal) + ", " + "IDExternal    = '" + POut.String(oIDExternal.IDExternal) + "', " + "rootExternal  = '" + POut.String(oIDExternal.rootExternal) + "' " + "WHERE OIDExternalNum = " + POut.Long(oIDExternal.OIDExternalNum);
        Db.NonQ(command);
    }

    /**
    * Updates one OIDExternal in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(OIDExternal oIDExternal, OIDExternal oldOIDExternal) throws Exception {
        String command = "";
        if (oIDExternal.IDType != oldOIDExternal.IDType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IDType = '" + POut.String(oIDExternal.IDType.ToString()) + "'";
        }
         
        if (oIDExternal.IDInternal != oldOIDExternal.IDInternal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IDInternal = " + POut.Long(oIDExternal.IDInternal) + "";
        }
         
        if (oIDExternal.IDExternal != oldOIDExternal.IDExternal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IDExternal = '" + POut.String(oIDExternal.IDExternal) + "'";
        }
         
        if (oIDExternal.rootExternal != oldOIDExternal.rootExternal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "rootExternal = '" + POut.String(oIDExternal.rootExternal) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE oidexternal SET " + command + " WHERE OIDExternalNum = " + POut.Long(oIDExternal.OIDExternalNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one OIDExternal from the database.
    */
    public static void delete(long oIDExternalNum) throws Exception {
        String command = "DELETE FROM oidexternal " + "WHERE OIDExternalNum = " + POut.Long(oIDExternalNum);
        Db.NonQ(command);
    }

}


