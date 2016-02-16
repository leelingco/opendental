//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DataCore;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Replaces old "General" class.  Used to send queries.  The methods will soon be internal since it is no longer acceptable for the UI to be sending queries.
*/
public class Db   
{
    /**
    * 
    */
    public static DataTable getTable(String command) throws Exception {
        DataTable retVal = new DataTable();
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  For user sql, use GetTableLow.  Othewise, rewrite the calling class to not use this query:\r\n" + command);
        }
        else
        {
            retVal = DataCore.getTable(command);
        } 
        retVal.TableName = "";
        return retVal;
    }

    //this is needed for FormQuery dataGrid
    /**
    * This is used for queries written by the user.  If using direct connection, it gets a table in the ordinary way.  If ServerWeb, it uses the user with lower privileges to prevent injection attack.
    */
    public static DataTable getTableLow(String command) throws Exception {
        DataTable retVal = new DataTable();
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Rewrite the calling class to pass this query off to the server:\r\n" + command);
        }
        else if (RemotingClient.RemotingRole == RemotingRole.ClientDirect)
        {
            retVal = DataCore.getTable(command);
        }
        else
        {
            //ServerWeb
            retVal = DataCore.getTableLow(command);
        }  
        retVal.TableName = "";
        return retVal;
    }

    //this is needed for FormQuery dataGrid
    /*
    		///<summary>This is for multiple queries all concatenated together with ;</summary>
    		internal static DataSet GetDataSet(string commands) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n"+commands);
    			}
    			else {
    				return DataCore.GetDataSet(commands);
    			}
    		}*/
    /**
    * This query is run with full privileges.  This is for commands generated by the main program, and the user will not have access for injection attacks.  Result is usually number of rows changed, or can be insert id if requested.
    */
    public static long nonQ(String command, boolean getInsertID, OdSqlParameter... parameters) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
        else
        {
            return DataCore.NonQ(command, getInsertID, parameters);
        } 
    }

    /**
    * This query is run with full privileges.  This is for commands generated by the main program, and the user will not have access for injection attacks.  Result is usually number of rows changed, or can be insert id if requested.
    */
    public static long nonQ(String command, OdSqlParameter... parameters) throws Exception {
        return NonQ(command, false, parameters);
    }

    /**
    * We need to get away from this due to poor support from databases.  For now, each command will be sent entirely separately.  This never returns number of rows affected.
    */
    public static long nonQ(String[] commands) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + commands[0]);
        }
         
        for (int i = 0;i < commands.Length;i++)
        {
            DataCore.NonQ(commands[i], false);
        }
        return 0;
    }

    /**
    * This is used only for historical commands in ConvertDatabase.
    */
    public static int nonQ32(String command, boolean getInsertID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
        else
        {
            return (int)DataCore.nonQ(command,getInsertID);
        } 
    }

    /**
    * This is used for historical commands in ConvertDatabase.  Seems to also be used in DBmaint when counting rows affected.
    */
    public static int nonQ32(String command) throws Exception {
        return nonQ32(command,false);
    }

    /**
    * This is used only for historical commands in ConvertDatabase.
    */
    public static int nonQ32(String[] commands) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + commands[0]);
        }
         
        for (int i = 0;i < commands.Length;i++)
        {
            DataCore.NonQ(commands[i], false);
        }
        return 0;
    }

    /**
    * Use this for count(*) queries.  They are always guaranteed to return one and only one value.  Not any faster, just handier.  Can also be used when retrieving prefs manually, since they will also return exactly one value.
    */
    public static String getCount(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
        else
        {
            return DataCore.getTable(command).Rows[0][0].ToString();
        } 
    }

    /**
    * Use this only for queries that return one value.
    */
    public static String getScalar(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("No longer allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
        else
        {
            return DataCore.getScalar(command);
        } 
    }

    /**
    * Always throws exception.
    */
    public static DataTable getTableOld(String command) throws Exception {
        throw new ApplicationException("No queries allowed in the UI layer.");
    }

    /**
    * Always throws exception.
    */
    public static int nonQOld(String[] commands) throws Exception {
        throw new ApplicationException("No queries allowed in the UI layer.");
    }

    /**
    * Always throws exception.
    */
    public static int nonQOld(String command) throws Exception {
        throw new ApplicationException("No queries allowed in the UI layer.");
    }

}


