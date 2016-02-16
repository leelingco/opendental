//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CodeBase.ODFileUtils;
import OpenDentBusiness.PIn;

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
    public long InsertID = new long();
    /**
    * Constructor sets the connection values.
    */
    public DataConnection() throws Exception {
        con = new MySqlConnection(getOpenDentalConnStr());
        //dr = null;
        cmd = new MySqlCommand();
        cmd.Connection = con;
    }

    //table=new DataTable();
    /**
    * 
    */
    private String getOpenDentalConnStr() throws Exception {
        XmlDocument document = new XmlDocument();
        String path = ODFileUtils.CombinePaths(Application.StartupPath, "FreeDentalConfig.xml");
        if (!File.Exists(path))
        {
            return "";
        }
         
        String computerName = "";
        String database = "";
        String user = "";
        String password = "";
        try
        {
            document.Load(path);
            XmlNodeReader reader = new XmlNodeReader(document);
            String currentElement = "";
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
                        computerName = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("Database"))
                    {
                        database = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("User"))
                    {
                        user = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("Password"))
                    {
                        password = reader.Value;
                    }
                        
                }
                  
            }
            reader.Close();
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }

        return "Server=" + computerName + ";Database=" + database + ";User ID=" + user + ";Password=" + password + ";CharSet=utf8";
    }

    //example:
    //Server=localhost;Database=opendental;User ID=root;Password=;CharSet=utf8
    /**
    * The problem with this is that if multiple copies of OD are open at the same time, it might get data from only the most recently opened database.  This won't work for some users, so we will normally dynamically alter the connection string.
    */
    public static String getODConnStr() throws Exception {
        //return "Server=localhost;Database=development54;User ID=root;Password=;CharSet=utf8";
        XmlDocument document = new XmlDocument();
        String path = ODFileUtils.CombinePaths(Application.StartupPath, "FreeDentalConfig.xml");
        if (!File.Exists(path))
        {
            return "";
        }
         
        String computerName = "";
        String database = "";
        String user = "";
        String password = "";
        try
        {
            document.Load(path);
            XmlNodeReader reader = new XmlNodeReader(document);
            String currentElement = "";
            while (reader.Read())
            {
                if (reader.NodeType == XmlNodeType.Element)
                {
                    currentElement = reader.Name;
                }
                else if (reader.NodeType == XmlNodeType.Text)
                {
                    System.String __dummyScrutVar1 = currentElement;
                    if (__dummyScrutVar1.equals("ComputerName"))
                    {
                        computerName = reader.Value;
                    }
                    else if (__dummyScrutVar1.equals("Database"))
                    {
                        database = reader.Value;
                    }
                    else if (__dummyScrutVar1.equals("User"))
                    {
                        user = reader.Value;
                    }
                    else if (__dummyScrutVar1.equals("Password"))
                    {
                        password = reader.Value;
                    }
                        
                }
                  
            }
            reader.Close();
        }
        catch (Exception __dummyCatchVar1)
        {
            return "";
        }

        return "Server=" + computerName + ";Database=" + database + ";User ID=" + user + ";Password=" + password + ";CharSet=utf8";
    }

    //example:
    //Server=localhost;Database=opendental;User ID=root;Password=;CharSet=utf8
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
        catch (Exception __dummyCatchVar2)
        {
            //catch(MySql.Data.Types.MySqlConversionException){
            //	MsgBox.Show(this,"Invalid date found. Please fix dates in the Check Database Integrity tool in your main menu under misc tools");
            //}
            MessageBox.Show(command);
        }
        finally
        {
            //catch(MySqlException e){
            //	MessageBox.Show("Error: "+e.Message+","+cmd.CommandText);
            //}
            con.Close();
        }
        return table;
    }

    /**
    * Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.
    */
    public long nonQ(String command) throws Exception {
        return nonQ(command,false);
    }

    /**
    * Sends a non query command to the database and returns the number of rows affected. If true, then InsertID will be set to the value of the primary key of the newly inserted row.
    */
    public long nonQ(String command, boolean getInsertID) throws Exception {
        cmd.CommandText = command;
        int rowsChanged = 0;
        try
        {
            con.Open();
            rowsChanged = cmd.ExecuteNonQuery();
            if (getInsertID)
            {
                cmd.CommandText = "SELECT LAST_INSERT_ID()";
                dr = (MySqlDataReader)cmd.ExecuteReader();
                if (dr.Read())
                {
                    InsertID = PIn.Long(dr[0].ToString());
                }
                 
            }
             
        }
        catch (MySqlException e)
        {
            MessageBox.Show("Error: " + e.Message + "," + cmd.CommandText);
        }
        finally
        {
            //catch{
            //	MessageBox.Show("Error: "+);
            //}
            con.Close();
        }
        return rowsChanged;
    }

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


