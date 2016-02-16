//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.OdSqlParameter;

//MS_Sql
/**
* 
*/
public class DataConnection   
{
    //
    /**
    * The value here is now reliable for public use.  FormChooseDatabase.DBtype, which used to be used for the client is now gone.
    */
    public static OpenDentBusiness.DatabaseType DBtype = OpenDentBusiness.DatabaseType.MySql;
    /**
    * This data adapter is used for all queries to the database.
    */
    private MySqlDataAdapter da = new MySqlDataAdapter();
    /**
    * Data adapter when 'isOracle' is set to true.
    */
    private OracleDataAdapter daOr = new OracleDataAdapter();
    /**
    * This is the connection that is used by the data adapter for all queries.  8/30/2010 js Changed this to be not static so that we can use it with multiple threads.  Has potential to cause bugs.
    */
    private MySqlConnection con = new MySqlConnection();
    /**
    * Connection that is being used when 'isOracle' is set to true.
    */
    private OracleConnection conOr = new OracleConnection();
    /**
    * Used to get very small bits of data from the db when the data adapter would be overkill.  For instance retrieving the response after a command is sent.
    */
    private MySqlDataReader dr = new MySqlDataReader();
    /**
    * The data reader being used when 'isOracle' is set to true.
    */
    private OracleDataReader drOr = new OracleDataReader();
    /**
    * Stores the string of the command that will be sent to the database.
    */
    private MySqlCommand cmd = new MySqlCommand();
    /**
    * The command to set when 'isOracle' is set to true?
    */
    public OracleCommand cmdOr = new OracleCommand();
    /**
    * After inserting a row, this variable will contain the primary key for the newly inserted row.  This can frequently save an additional query to the database.
    */
    public long InsertID = new long();
    private static String Database = new String();
    private static String ServerName = new String();
    private static String MysqlUser = new String();
    private static String MysqlPass = new String();
    //User with lower privileges:
    private static String MysqlUserLow = new String();
    private static String MysqlPassLow = new String();
    /**
    * If this is used, then none of the fields above will be set.
    */
    private static String ConnectionString = "";
    //For queries that do not use this flag, all queries are split into single commands. For those SQL commands which
    //are a single command but contain multiple semi-colons, then this string should be set to false before the
    //command is executed, then set back to true immediately thereafter.
    //public static bool splitStrings=true;
    public static int defaultPortNum() throws Exception {
        switch(DBtype)
        {
            case Oracle: 
                return 1521;
            case MySql: 
                return 3306;
            default: 
                return 3306;
        
        }
    }

    //Guess (same as MySQL, to target larger customer crowd).
    /**
    * From local variable.  Does not query the database.
    */
    public static String getDatabaseName() throws Exception {
        return Database;
    }

    /**
    * From local variable.  Does not query the database.
    */
    public static String getServerName() throws Exception {
        return ServerName;
    }

    public DataConnection(boolean isLow) throws Exception {
        String connectStr = ConnectionString;
        if (connectStr.Length < 1 && ServerName != null)
        {
            connectStr = buildSimpleConnectionString(ServerName,Database,MysqlUserLow,MysqlPassLow);
        }
         
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr = new OracleConnection(connectStr);
            //drOr = null;
            cmdOr = new OracleCommand();
            cmdOr.Connection = conOr;
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            con = new MySqlConnection(connectStr);
            //dr = null;
            cmd = new MySqlCommand();
            cmd.Connection = con;
        }
          
    }

    /**
    * 
    */
    public DataConnection() throws Exception {
        String connectStr = ConnectionString;
        if (connectStr.Length < 1 && ServerName != null)
        {
            connectStr = buildSimpleConnectionString(ServerName,Database,MysqlUser,MysqlPass);
        }
         
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr = new OracleConnection(connectStr);
            //drOr = null;
            cmdOr = new OracleCommand();
            cmdOr.Connection = conOr;
        }
        else //table=new DataTable();
        if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            con = new MySqlConnection(connectStr);
            //dr = null;
            cmd = new MySqlCommand();
            cmd.Connection = con;
        }
          
    }

    //table=new DataTable();
    /**
    * Only used from FormChooseDatabase to attempt connection with database.
    */
    public DataConnection(OpenDentBusiness.DatabaseType dbtype) throws Exception {
    }

    //SetDb will be run immediately, so no need to do anything here.
    /**
    * 
    */
    public DataConnection(String database) throws Exception {
        String connectStr = ConnectionString;
        //this doesn't really set it to the new db as intended. Deal with later.
        if (connectStr.Length < 1)
        {
            connectStr = buildSimpleConnectionString(ServerName,database,MysqlUser,MysqlPass);
        }
         
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr = new OracleConnection(connectStr);
            //drOr=null;
            cmdOr = new OracleCommand();
            cmdOr.Connection = conOr;
        }
        else //table=new DataTable();
        if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            con = new MySqlConnection(connectStr);
            //dr = null;
            cmd = new MySqlCommand();
            cmd.Connection = con;
        }
          
    }

    //table=new DataTable();
    /**
    * Only used to fill the list of databases in the ChooseDatabase window and from Employees.GetAsteriskMissedCalls.
    */
    public DataConnection(String serverName, String database, String mysqlUser, String mysqlPass, OpenDentBusiness.DatabaseType dtype) throws Exception {
        String connectStr = ConnectionString;
        if (connectStr.Length < 1)
        {
            connectStr = buildSimpleConnectionString(dtype,serverName,database,mysqlUser,mysqlPass);
        }
         
        if (dtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr = new OracleConnection(connectStr);
            //drOr=null;
            cmdOr = new OracleCommand();
            cmdOr.Connection = conOr;
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            con = new MySqlConnection(connectStr);
            //dr = null;
            cmd = new MySqlCommand();
            cmd.Connection = con;
        }
          
    }

    /**
    * Used by the mobile server because it does not need to worry about 3-tier scenarios.  Only supports MySQL.
    */
    public DataConnection(String connectStr, boolean isMobile) throws Exception {
        //isMobile is ignored.  Just needed to make it different than the other constructor.
        con = new MySqlConnection(connectStr);
        cmd = new MySqlCommand();
        cmd.Connection = con;
    }

    /*
    		///<Summary>This is only meaningful if local connection instead of through server.  This might be added to be able to access from ODR when users start customizing their RDL reports.  But for now, we should build the connection string programmatically</Summary>
    		public static string GetCurrentConnectionString(){
    			//ONLY TEMPORARY
    			return BuildSimpleConnectionString(DatabaseType.MySql,ServerName,Database,DefaultPortNum().ToString(),MysqlUser,MysqlPass);
    		}*/
    public static String buildSimpleConnectionString(OpenDentBusiness.DatabaseType pDbType, String pServer, String pDatabase, String pUserID, String pPassword) throws Exception {
        String serverName = pServer;
        String port = defaultPortNum().ToString();
        if (pServer.Contains(":"))
        {
            String[] serverNamePort = pServer.Split(new char[]{ ':' }, StringSplitOptions.RemoveEmptyEntries);
            serverName = serverNamePort[0];
            port = serverNamePort[1];
        }
         
        String connectStr = "";
        /*
        			if(DBtype==DatabaseType.Oracle){
        				connectStr=
        					"Data Source=(DESCRIPTION=(ADDRESS_LIST="
        				+ "(ADDRESS=(PROTOCOL=TCP)(HOST="+serverName+")(PORT="+port+")))"
        				+ "(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+pDatabase+")));"
        				+ "User Id="+pUserID+";Password="+pPassword+";";
        			}
        			else if(DBtype==DatabaseType.MySql){*/
        //although this does seem to cause a bug in Mono.  We will revisit this bug if needed to exclude the port option only for Mono.
        //+";Connect Timeout=20"
        connectStr = "Server=" + serverName + ";Port=" + port + ";Database=" + pDatabase + ";User ID=" + pUserID + ";Password=" + pPassword + ";CharSet=utf8" + ";Treat Tiny As Boolean=false" + ";Allow User Variables=true" + ";Default Command Timeout=3600";
        return connectStr;
    }

    //one hour timeout on commands.  Prevents crash during conversions, etc.
    //+";Pooling=false";
    //}
    private String buildSimpleConnectionString(String pServer, String pDatabase, String pUserID, String pPassword) throws Exception {
        return buildSimpleConnectionString(DBtype,pServer,pDatabase,pUserID,pPassword);
    }

    //private void PrepOracleConnection(){
    //if(parameters.Count>0) {//Getting parameters for statement.
    //	for(int p=0;p<parameters.Count;p++) {
    //		cmdOr.Parameters.Add(":param"+(p+1),parameters[p]);
    //	}
    //	cmdOr.Prepare();
    //	parameters.Clear();
    //}
    //This affects performance.  We need a better alternative than this:
    //cmdOr.CommandText="ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS'";
    //try{
    //	cmdOr.ExecuteNonQuery();	//Change the date-time format for this oracle connection to match our
    //MySQL date-time format.
    //}
    //catch(Exception e) {
    //	Logger.openlog.LogMB("Oracle SQL Error: "+cmdOr.CommandText+"\r\n"+"Exception: "+e.ToString(),Logger.Severity.ERROR);
    //	throw;//continue to pass the exception one level up.
    //}
    //}
    /**
    * This needs to be run every time we switch databases, especially on startup.  Will throw an exception if fails.  Calling class should catch exception.
    */
    public void setDb(String server, String db, String user, String password, String userLow, String passLow, OpenDentBusiness.DatabaseType dbtype) throws Exception {
        DBtype = dbtype;
        String connectStr = buildSimpleConnectionString(server,db,user,password);
        String connectStrLow = "";
        if (!StringSupport.equals(userLow, ""))
        {
            connectStrLow = buildSimpleConnectionString(server,db,userLow,passLow);
        }
         
        testConnection(connectStr,connectStrLow,dbtype,false);
        //connection strings must be valid, so OK to set permanently
        Database = db;
        ServerName = server;
        //yes, it includes the port
        MysqlUser = user;
        MysqlPass = password;
        MysqlUserLow = userLow;
        MysqlPassLow = passLow;
    }

    /**
    * This needs to be run every time we switch databases, especially on startup.  Will throw an exception if fails.  Calling class should catch exception.
    */
    public void setDb(String connectStr, String connectStrLow, OpenDentBusiness.DatabaseType dbtype, boolean skipValidation) throws Exception {
        testConnection(connectStr,connectStrLow,dbtype,skipValidation);
        //connection string must be valid, so OK to set permanently
        ConnectionString = connectStr;
    }

    /**
    * 
    */
    public void setDb(String connectStr, String connectStrLow, OpenDentBusiness.DatabaseType dbtype) throws Exception {
        setDb(connectStr,connectStrLow,dbtype,false);
    }

    private void testConnection(String connectStr, String connectStrLow, OpenDentBusiness.DatabaseType dbtype, boolean skipValidation) throws Exception {
        DBtype = dbtype;
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr = new OracleConnection(connectStr);
            cmdOr = new OracleCommand();
            conOr.Open();
            cmdOr.Connection = conOr;
            //UPDATE preference SET ValueString = '0' WHERE ValueString = '0'
            cmdOr.CommandText = "UPDATE preference SET PrefName = '0' WHERE PrefName = '0'";
            cmdOr.ExecuteNonQuery();
            conOr.Close();
            if (!StringSupport.equals(connectStrLow, ""))
            {
                conOr = new OracleConnection(connectStrLow);
                cmdOr = new OracleCommand();
                conOr.Open();
                cmdOr.Connection = conOr;
                cmdOr.CommandText = "SELECT * FROM preference WHERE ValueString = 'DataBaseVersion'";
                DataTable table = new DataTable();
                daOr = new OracleDataAdapter(cmdOr);
                daOr.Fill(table);
                conOr.Close();
            }
             
        }
        else
        {
            con = new MySqlConnection(connectStr);
            cmd = new MySqlCommand();
            //cmd.CommandTimeout=30;
            cmd.Connection = con;
            con.Open();
            if (!skipValidation)
            {
                cmd.CommandText = "UPDATE preference SET ValueString = '0' WHERE ValueString = '0'";
                cmd.ExecuteNonQuery();
            }
             
            con.Close();
            if (!StringSupport.equals(connectStrLow, ""))
            {
                con = new MySqlConnection(connectStrLow);
                cmd = new MySqlCommand();
                cmd.Connection = con;
                con.Open();
                cmd.CommandText = "SELECT * FROM preference WHERE ValueString = 'DataBaseVersion'";
                DataTable table = new DataTable();
                da = new MySqlDataAdapter(cmd);
                da.Fill(table);
                con.Close();
            }
             
        } 
    }

    /**
    * Fills table with data from the database.
    */
    public DataTable getTable(String command) throws Exception {
        DataTable table = new DataTable();
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr.Open();
            //PrepOracleConnection();
            cmdOr.CommandText = command;
            daOr = new OracleDataAdapter(cmdOr);
            try
            {
                daOr.Fill(table);
            }
            catch (System.Exception e)
            {
                Logger.openlog.logMB("Oracle SQL Error: " + cmdOr.CommandText + "\r\n" + "Exception: " + e.ToString(),Severity.ERROR);
                throw e;
            }

            //continue to pass the exception one level up.
            conOr.Close();
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            cmd.CommandText = command;
            da = new MySqlDataAdapter(cmd);
            da.Fill(table);
            con.Close();
        }
          
        return table;
    }

    /*
    		///<summary>Fills dataset with data from the database.</summary>
    		public DataSet GetDs(string commands) {
    			#if DEBUG
    				if(logDebugQueries){
    					Debug.WriteLine(commands);
    				}
    				Thread.Sleep(delayForTesting);
    			#endif
    			DataSet ds=new DataSet();
    			if(DBtype==DatabaseType.Oracle){
    				conOr.Open();
    				//PrepOracleConnection();
    				//string[] commandArray=new string[] { commands };
    				//if(splitStrings) {
    				//	commandArray=commands.Split(new char[] { ';' },StringSplitOptions.RemoveEmptyEntries);
    				//}
    				//Can't do batch queries in Oracle, so we have to split them up and run them individually.
    				//foreach(string com in commandArray){
    					cmdOr.CommandText=commands;
    					daOr=new OracleDataAdapter(cmdOr);
    					//DataTable dsTab=new DataTable();
    					//try{
    						daOr.Fill(ds);
    					//}
    					//catch(System.Exception e) {
    					//	Logger.openlog.LogMB("Oracle SQL Error: "+cmdOr.CommandText+"\r\n"+"Exception: "+e.ToString(),Logger.Severity.ERROR);
    					//	throw e;//continue to pass the exception one level up.
    					//}
    					//ds.Tables.Add(dsTab);
    				//}
    				conOr.Close();
    			}
    			else if(DBtype==DatabaseType.MySql){
    				cmd.CommandText=commands;
    				da=new MySqlDataAdapter(cmd);
    				da.Fill(ds);
    				con.Close();
    			}
    			return ds;
    		}*/
    /**
    * Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.
    */
    public long nonQ(String commands, boolean getInsertID, OdSqlParameter... parameters) throws Exception {
        long rowsChanged = 0;
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr.Open();
            //PrepOracleConnection();
            //string[] commandArray=new string[] {commands};
            //if(splitStrings){
            //  commandArray=commands.Split(new char[] {';'},StringSplitOptions.RemoveEmptyEntries);
            //}
            //Can't do batch queries in Oracle, so we have to split them up and run them individually.
            //try{
            //if(getInsertID){
            //	cmdOr.CommandText="LOCK TABLE preference IN EXCLUSIVE MODE";
            //	cmdOr.ExecuteNonQuery();//Lock the preference table, because we need exclusive access to the OracleInsertId.
            //}
            String[] commandList = splitCommands(commands);
            for (int i = 0;i < commandList.Length;i++)
            {
                cmdOr.CommandText = commandList[i];
                for (int p = 0;p < parameters.Length;p++)
                {
                    //Parameters are not used with batch commands so we don't need to worry about adding the parameters mulitple times.
                    cmdOr.Parameters.Add(DbHelper.getParamChar() + parameters[p].ParameterName, parameters[p].GetOracleDbType()).Value = parameters[p].Value;
                }
                //cmdOr.Parameters.Add(parameters[p].GetOracleParameter());//doesn't work
                rowsChanged = cmdOr.ExecuteNonQuery();
            }
            //}
            //}
            //catch(System.Exception e){
            //	Logger.openlog.LogMB("Oracle SQL Error: "+cmdOr.CommandText+"\r\n"+"Exception: "+e.ToString(),Logger.Severity.ERROR);
            //	throw;//continue to pass the exception one level up.
            //}
            //finally{
            /*
            					if(getInsertID){
            						try{
            							cmdOr.CommandText="SELECT ValueString FROM preference WHERE PrefName='OracleInsertId'";
            							daOr=new OracleDataAdapter(cmdOr);
            							DataTable table=new DataTable();
            							daOr.Fill(table);//Will always return a result, unless a critical error, in which case we will catch.
            							this.InsertID=Convert.ToInt32((table.Rows[0][0]).ToString());
            							cmdOr.CommandText="commit";
            							cmdOr.ExecuteNonQuery();//Release the exlusive lock we attaned above.
            						}
            						catch(Exception e){
            							Logger.openlog.LogMB("Oracle SQL Error: "+cmdOr.CommandText+"\r\n"+"Exception: "+e.ToString(),
            								Logger.Severity.ERROR);
            							throw e;//continue to pass the exception one level up.
            						}
            					}*/
            //}
            conOr.Close();
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            cmd.CommandText = commands;
            for (int p = 0;p < parameters.Length;p++)
            {
                cmd.Parameters.Add(DbHelper.getParamChar() + parameters[p].ParameterName, parameters[p].GetMySqlDbType()).Value = parameters[p].Value;
            }
            con.Open();
            try
            {
                rowsChanged = cmd.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                if (ex.Number == 1153)
                {
                    throw new ApplicationException("Please add the following to your my.ini file: max_allowed_packet=40000000");
                }
                 
                throw ex;
            }

            if (getInsertID)
            {
                cmd.CommandText = "SELECT LAST_INSERT_ID()";
                dr = (MySqlDataReader)cmd.ExecuteReader();
                if (dr.Read())
                {
                    InsertID = Convert.ToInt64(dr[0].ToString());
                }
                 
            }
             
            con.Close();
        }
          
        return rowsChanged;
    }

    /**
    * Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.
    */
    public long nonQ(String command) throws Exception {
        return nonQ(command,false);
    }

    /**
    * Use this for count(*) queries.  They are always guaranteed to return one and only one value.  Uses datareader instead of datatable, so faster.  Can also be used when retrieving prefs manually, since they will also return exactly one value
    */
    public String getCount(String command) throws Exception {
        String retVal = "";
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr.Open();
            //PrepOracleConnection();
            cmdOr.CommandText = command;
            try
            {
                drOr = (OracleDataReader)cmdOr.ExecuteReader();
            }
            catch (System.Exception e)
            {
                Logger.openlog.logMB("Oracle SQL Error: " + cmdOr.CommandText + "\r\n" + "Exception: " + e.ToString(),Severity.ERROR);
                throw e;
            }

            //continue to pass the exception one level up.
            drOr.Read();
            retVal = drOr[0].ToString();
            conOr.Close();
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            cmd.CommandText = command;
            con.Open();
            dr = (MySqlDataReader)cmd.ExecuteReader();
            dr.Read();
            retVal = dr[0].ToString();
            con.Close();
        }
          
        return retVal;
    }

    /**
    * Get one value.
    */
    public String getScalar(String command) throws Exception {
        Object scalar = new Object();
        String retVal = "";
        if (DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            conOr.Open();
            //PrepOracleConnection();
            cmdOr.CommandText = command;
            try
            {
                scalar = cmdOr.ExecuteScalar();
            }
            catch (System.Exception e)
            {
                Logger.openlog.logMB("Oracle SQL Error: " + cmdOr.CommandText + "\r\n" + "Exception: " + e.ToString(),Severity.ERROR);
                throw e;
            }

            //continue to pass the exception one level up.
            if (scalar == null)
            {
                retVal = "";
            }
            else
            {
                retVal = scalar.ToString();
            } 
            conOr.Close();
        }
        else if (DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            cmd.CommandText = command;
            con.Open();
            scalar = cmd.ExecuteScalar();
            if (scalar == null)
            {
                retVal = "";
            }
            else
            {
                retVal = scalar.ToString();
            } 
            con.Close();
        }
          
        return retVal;
    }

    /**
    * Oracle only. Used to split a command string into a list of individual commands so that each command can be run individually. It has proven difficult to run multiple commands at one time in Oracle without making drastic changes to existing queries.
    */
    private String[] splitCommands(String batchCmd) throws Exception {
        if (DBtype != OpenDentBusiness.DatabaseType.Oracle)
        {
            return new String[]{ batchCmd };
        }
         
        //Some commands are surrounded by a BEGIN and END block, which does correctly execute in the Oracle connector and is hard for us to parse if there are nested BEGIN and END blocks.
        if (batchCmd.TrimStart().StartsWith("BEGIN", StringComparison.OrdinalIgnoreCase))
        {
            return new String[]{ batchCmd };
        }
         
        //Possibilities within a single statement:
        //Reference for Oracle Text Literals: http://download.oracle.com/docs/cd/B19306_01/server.102/b14200/sql_elements003.htm#SQLRF00218
        //Oracle allows one to use alternative quoting mechanisms instead of single-quotes, but we are not going to worry about that here.
        //In Oracle, one must escape ' with '' so we can just treat these like a string ending and a new one beginning immediately afterward.
        //Commands are terminated with semi-colons. We need to watch for the case when a semi-colon is within a single-quote string. As long as we correctly handle strings and ignore all data in a string, this shouldn't matter.
        //If batchCmd contains a command which has maulformed single or double quotes, this algorithm will not work so we will need to throw an exception in this case.
        List<String> result = new List<String>();
        StringBuilder strb = new StringBuilder();
        for (int i = 0;i < batchCmd.Length;i++)
        {
            //bool beginningChar;
            //bool isInString;
            /*
            				if(batchCmd[i]=='"') {
            					if(isInString && beginningChar=='"') {
            						isInString=false;
            					}
            					else {
            						isInString=true;
            					}
            				}
            				if(batchCmd[i]==";" && !isInString) {
            					if(strb.Length>0){
            						result.Add(strb.ToString();
            					}
            					strb=new StringBuilder();
            					continue;
            				}
            				strb.Append(batchCmd[i]);*/
            if (batchCmd[i] == '\'')
            {
                do
                {
                    //Single quotes are escaped with a single quote. So, for the string 'Hi I''m Bob' there are always an even number of single quotes.
                    strb.Append(batchCmd[i++]);
                    //Uses i then increments it.
                    if (i >= batchCmd.Length)
                    {
                        throw new ApplicationException("Mismatched quotes found while splitting command.");
                    }
                     
                }
                while (batchCmd[i] != '\'');
                strb.Append(batchCmd[i]);
            }
            else if (batchCmd[i] == ';')
            {
                //top-level ; so this is the end of a command.
                if (strb.Length > 0)
                {
                    result.Add(strb.ToString());
                }
                 
                strb = new StringBuilder();
            }
            else
            {
                //All other characters
                strb.Append(batchCmd[i]);
            }  
        }
        if (strb.Length > 0)
        {
            //Make sure to add the last command if it did not have a ;
            result.Add(strb.ToString());
        }
         
        return result.ToArray();
    }

}


