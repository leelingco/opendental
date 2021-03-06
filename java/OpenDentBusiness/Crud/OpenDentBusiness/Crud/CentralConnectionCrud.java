//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:56 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CentralConnectionCrud   
{
    /**
    * Gets one CentralConnection object from the database using the primary key.  Returns null if not found.
    */
    public static CentralConnection selectOne(long centralConnectionNum) throws Exception {
        String command = "SELECT * FROM centralconnection " + "WHERE CentralConnectionNum = " + POut.Long(centralConnectionNum);
        List<CentralConnection> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one CentralConnection object from the database using a query.
    */
    public static CentralConnection selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CentralConnection> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of CentralConnection objects from the database using a query.
    */
    public static List<CentralConnection> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CentralConnection> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CentralConnection> tableToList(DataTable table) throws Exception {
        List<CentralConnection> retVal = new List<CentralConnection>();
        CentralConnection centralConnection = new CentralConnection();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            centralConnection = new CentralConnection();
            centralConnection.CentralConnectionNum = PIn.Long(table.Rows[i]["CentralConnectionNum"].ToString());
            centralConnection.ServerName = PIn.String(table.Rows[i]["ServerName"].ToString());
            centralConnection.DatabaseName = PIn.String(table.Rows[i]["DatabaseName"].ToString());
            centralConnection.MySqlUser = PIn.String(table.Rows[i]["MySqlUser"].ToString());
            centralConnection.MySqlPassword = PIn.String(table.Rows[i]["MySqlPassword"].ToString());
            centralConnection.ServiceURI = PIn.String(table.Rows[i]["ServiceURI"].ToString());
            centralConnection.OdUser = PIn.String(table.Rows[i]["OdUser"].ToString());
            centralConnection.OdPassword = PIn.String(table.Rows[i]["OdPassword"].ToString());
            centralConnection.Note = PIn.String(table.Rows[i]["Note"].ToString());
            centralConnection.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            centralConnection.WebServiceIsEcw = PIn.Bool(table.Rows[i]["WebServiceIsEcw"].ToString());
            retVal.Add(centralConnection);
        }
        return retVal;
    }

    /**
    * Inserts one CentralConnection into the database.  Returns the new priKey.
    */
    public static long insert(CentralConnection centralConnection) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            centralConnection.CentralConnectionNum = DbHelper.GetNextOracleKey("centralconnection", "CentralConnectionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(centralConnection,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        centralConnection.CentralConnectionNum++;
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
            return insert(centralConnection,false);
        } 
    }

    /**
    * Inserts one CentralConnection into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CentralConnection centralConnection, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            centralConnection.CentralConnectionNum = ReplicationServers.GetKey("centralconnection", "CentralConnectionNum");
        }
         
        String command = "INSERT INTO centralconnection (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "CentralConnectionNum,";
        }
         
        command += "ServerName,DatabaseName,MySqlUser,MySqlPassword,ServiceURI,OdUser,OdPassword,Note,ItemOrder,WebServiceIsEcw) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(centralConnection.CentralConnectionNum) + ",";
        }
         
        command += "'" + POut.String(centralConnection.ServerName) + "'," + "'" + POut.String(centralConnection.DatabaseName) + "'," + "'" + POut.String(centralConnection.MySqlUser) + "'," + "'" + POut.String(centralConnection.MySqlPassword) + "'," + "'" + POut.String(centralConnection.ServiceURI) + "'," + "'" + POut.String(centralConnection.OdUser) + "'," + "'" + POut.String(centralConnection.OdPassword) + "'," + "'" + POut.String(centralConnection.Note) + "'," + POut.Int(centralConnection.ItemOrder) + "," + POut.Bool(centralConnection.WebServiceIsEcw) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            centralConnection.CentralConnectionNum = Db.NonQ(command, true);
        } 
        return centralConnection.CentralConnectionNum;
    }

    /**
    * Updates one CentralConnection in the database.
    */
    public static void update(CentralConnection centralConnection) throws Exception {
        String command = "UPDATE centralconnection SET " + "ServerName          = '" + POut.String(centralConnection.ServerName) + "', " + "DatabaseName        = '" + POut.String(centralConnection.DatabaseName) + "', " + "MySqlUser           = '" + POut.String(centralConnection.MySqlUser) + "', " + "MySqlPassword       = '" + POut.String(centralConnection.MySqlPassword) + "', " + "ServiceURI          = '" + POut.String(centralConnection.ServiceURI) + "', " + "OdUser              = '" + POut.String(centralConnection.OdUser) + "', " + "OdPassword          = '" + POut.String(centralConnection.OdPassword) + "', " + "Note                = '" + POut.String(centralConnection.Note) + "', " + "ItemOrder           =  " + POut.Int(centralConnection.ItemOrder) + ", " + "WebServiceIsEcw     =  " + POut.Bool(centralConnection.WebServiceIsEcw) + " " + "WHERE CentralConnectionNum = " + POut.Long(centralConnection.CentralConnectionNum);
        Db.NonQ(command);
    }

    /**
    * Updates one CentralConnection in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(CentralConnection centralConnection, CentralConnection oldCentralConnection) throws Exception {
        String command = "";
        if (centralConnection.ServerName != oldCentralConnection.ServerName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ServerName = '" + POut.String(centralConnection.ServerName) + "'";
        }
         
        if (centralConnection.DatabaseName != oldCentralConnection.DatabaseName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DatabaseName = '" + POut.String(centralConnection.DatabaseName) + "'";
        }
         
        if (centralConnection.MySqlUser != oldCentralConnection.MySqlUser)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MySqlUser = '" + POut.String(centralConnection.MySqlUser) + "'";
        }
         
        if (centralConnection.MySqlPassword != oldCentralConnection.MySqlPassword)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MySqlPassword = '" + POut.String(centralConnection.MySqlPassword) + "'";
        }
         
        if (centralConnection.ServiceURI != oldCentralConnection.ServiceURI)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ServiceURI = '" + POut.String(centralConnection.ServiceURI) + "'";
        }
         
        if (centralConnection.OdUser != oldCentralConnection.OdUser)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OdUser = '" + POut.String(centralConnection.OdUser) + "'";
        }
         
        if (centralConnection.OdPassword != oldCentralConnection.OdPassword)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OdPassword = '" + POut.String(centralConnection.OdPassword) + "'";
        }
         
        if (centralConnection.Note != oldCentralConnection.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.String(centralConnection.Note) + "'";
        }
         
        if (centralConnection.ItemOrder != oldCentralConnection.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(centralConnection.ItemOrder) + "";
        }
         
        if (centralConnection.WebServiceIsEcw != oldCentralConnection.WebServiceIsEcw)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "WebServiceIsEcw = " + POut.Bool(centralConnection.WebServiceIsEcw) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE centralconnection SET " + command + " WHERE CentralConnectionNum = " + POut.Long(centralConnection.CentralConnectionNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one CentralConnection from the database.
    */
    public static void delete(long centralConnectionNum) throws Exception {
        String command = "DELETE FROM centralconnection " + "WHERE CentralConnectionNum = " + POut.Long(centralConnectionNum);
        Db.NonQ(command);
    }

}


