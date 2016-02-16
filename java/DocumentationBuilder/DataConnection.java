//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package DocumentationBuilder;

import CodeBase.ODFileUtils;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* This is replacing the DataClass as the preferred way to interact with the database.
*/
public class DataConnection   
{
    //
    /**
    * This data adapter is used for all queries to the database.
    */
    private MySqlDataAdapter da = new MySqlDataAdapter();
    /**
    * This is the connection that is used by the data adapter for all queries.
    */
    private MySqlConnection con = new MySqlConnection();
    /**
    * Used to get very small bits of data from the db when the data adapter would be overkill.  For instance retrieving the response after a command is sent.
    */
    private MySqlDataReader dr = new MySqlDataReader();
    /**
    * Stores the string of the command that will be sent to the database.
    */
    private MySqlCommand cmd = new MySqlCommand();
    /**
    * After inserting a row, this variable will contain the primary key for the newly inserted row.  This can frequently save an additional query to the database.
    */
    public int InsertID = new int();
    /**
    * 
    */
    public String ConnStr = new String();
    /**
    * Constructor sets the connection values.
    */
    public DataConnection() throws Exception {
        ConnStr = getConnectionString();
        con = new MySqlConnection(ConnStr);
        //dr = null;
        cmd = new MySqlCommand();
        cmd.Connection = con;
    }

    //table=new DataTable();
    /**
    * 
    */
    private String getConnectionString() throws Exception {
        XmlDocument document = new XmlDocument();
        String configFile = ODFileUtils.CombinePaths(new String[]{ "..", "..", "..", "OpenDental", "bin", "Release", "FreeDentalConfig.xml" });
        if (!File.Exists(configFile))
        {
            MessageBox.Show(configFile + " does not exist.");
            Application.Exit();
            return "";
        }
         
        document.Load(configFile);
        XmlNodeReader reader = new XmlNodeReader(document);
        String currentElement = "";
        String ComputerName = "";
        String Database = "";
        String DbUser = "";
        String Password = "";
        while (reader.Read())
        {
            if (reader.NodeType == XmlNodeType.Element)
            {
                currentElement = reader.Name;
            }
            else if (reader.NodeType == XmlNodeType.Text)
            {
                System.String __dummyScrutVar0 = currentElement;
                if (__dummyScrutVar0.equals("ComputerName"))
                {
                    ComputerName = reader.Value;
                }
                else if (__dummyScrutVar0.equals("Database"))
                {
                    Database = reader.Value;
                }
                else if (__dummyScrutVar0.equals("User"))
                {
                    DbUser = reader.Value;
                }
                else if (__dummyScrutVar0.equals("Password"))
                {
                    Password = reader.Value;
                }
                    
            }
              
        }
        reader.Close();
        return "Server=" + ComputerName + ";Database=" + Database + ";User ID=" + DbUser + ";Password=" + Password + ";CharSet=utf8";
    }

    /*
    		///<summary>Sets the connection to an alternate database for backup purposes.  Currently only used during conversions to do a quick backup first, and in FormConfig to get db names.</summary>
    		public DataConnection(string db){
    		  con= new MySqlConnection(FormChooseDatabase.GetAlternateConnStr(db));
    			//dr = null;
    			cmd = new MySqlCommand();
    			cmd.Connection = con;
    			//table=new DataTable(null);
    		}*/
    /**
    * Tests to see if the connection is valid.
    */
    public boolean isValid() throws Exception {
        try
        {
            con.Open();
            con.Close();
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }

        return true;
    }

    //(MySQLDriverCS.MySQLException ex){
    /**
    * Fills table with data from the database.
    */
    public DataTable getTable(String command) throws Exception {
        cmd.CommandText = command;
        DataTable table = new DataTable();
        try
        {
            da = new MySqlDataAdapter(cmd);
            da.Fill(table);
        }
        catch (MySql.Data.Types.MySqlConversionException __dummyCatchVar1)
        {
        }
        finally
        {
            //MessageBox.Show(ex.Message);
            //MessageBox.Show(this,"Invalid date found. Please fix dates by running the Database Maintenance tool.  Include the initial check.");
            //catch(MySqlException e){
            //MsgBoxCopyPaste MB=new MsgBoxCopyPaste(Lan.g("DataConnection","Error in query:")+"\r\n"
            //	+e.Message+"\r\n"+"\r\n"
            //	+command);
            //MB.ShowDialog();
            //MessageBox.Show(command);
            //}
            //System.Net.Sockets.SocketException will be thrown if database connection is unavailable.
            //SocketException should be handled by the calling function.
            //usually only a problem for timed refreshes.
            //catch(MySqlException e){
            //	MessageBox.Show("Error: "+e.Message+","+cmd.CommandText);
            //}
            con.Close();
        }
        return table;
    }

    /*
    		///<summary>Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.</summary>
    		public int NonQ(string command){
    			return NonQ(command,false);
    		}*/
    /**
    * Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.
    */
    public int nonQ(String command) throws Exception {
        //,bool getInsertID){
        cmd.CommandText = command;
        int rowsChanged = 0;
        //try{
        con.Open();
        rowsChanged = cmd.ExecuteNonQuery();
        /*if(getInsertID){
        					cmd.CommandText="SELECT LAST_INSERT_ID()";
        					dr=(MySqlDataReader)cmd.ExecuteReader();
        					if(dr.Read())
        						InsertID=PIn.PInt(dr[0].ToString());
        				}*/
        //}
        //catch(MySqlException e){
        //	MsgBoxCopyPaste MB=new MsgBoxCopyPaste(Lan.g("DataConnection","Error in query:")+"\r\n"
        //		+e.Message+"\r\n"+"\r\n"
        //		+command);
        //	MB.ShowDialog();
        //MessageBox.Show("Error: "+e.Message+","+cmd.CommandText);
        //}
        //catch{
        //	MessageBox.Show("Error: "+);
        //}
        //finally{
        con.Close();
        return rowsChanged;
    }

    //}
    /**
    * Submits an array of commands in sequence. Used in conversions. Throws an exception if unsuccessful.  Returns the number of rows affected
    */
    public int nonQ(String[] commands) throws Exception {
        int rowsChanged = 0;
        con.Open();
        for (int i = 0;i < commands.Length;i++)
        {
            cmd.CommandText = commands[i];
            //Debug.WriteLine(cmd.CommandText);
            rowsChanged += cmd.ExecuteNonQuery();
        }
        con.Close();
        return rowsChanged;
    }

    /**
    * Use this for count(*) queries.  They are always guaranteed to return one and only one value.  Uses datareader instead of datatable, so faster.  Can also be used when retrieving prefs manually, since they will also return exactly one value
    */
    public String getCount(String command) throws Exception {
        cmd.CommandText = command;
        con.Open();
        dr = (MySqlDataReader)cmd.ExecuteReader();
        dr.Read();
        String retVal = dr[0].ToString();
        con.Close();
        return retVal;
    }

}


