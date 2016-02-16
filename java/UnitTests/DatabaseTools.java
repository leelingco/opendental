//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDental.FormProcCodes;
import OpenDentBusiness.AutoCodes;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import UnitTests.Properties.Resources;

/**
* Contains the queries, scripts, and tools to clear the database of data from previous unitTest runs.
*/
public class DatabaseTools   
{
    //public static string DbName;
    //public static bool DbExists(){
    //	string command="";
    //}
    /**
    * This is analogous to FormChooseDatabase.TryToConnect.  Empty string is allowed.
    */
    public static boolean setDbConnection(String dbName, boolean isOracle) throws Exception {
        OpenDentBusiness.DataConnection dcon;
        try
        {
            //Try to connect to the database directly
            if (!isOracle)
            {
                OpenDentBusiness.DataConnection.DBtype = OpenDentBusiness.DatabaseType.MySql;
                dcon = new OpenDentBusiness.DataConnection(OpenDentBusiness.DataConnection.DBtype);
                dcon.setDb("Server=localhost;Database=" + dbName + ";User ID=root;Password=;CharSet=utf8;Treat Tiny As Boolean=false","",OpenDentBusiness.DataConnection.DBtype,true);
                RemotingClient.RemotingRole = RemotingRole.ClientDirect;
                return true;
            }
            else
            {
                OpenDentBusiness.DataConnection.DBtype = OpenDentBusiness.DatabaseType.Oracle;
                dcon = new OpenDentBusiness.DataConnection(OpenDentBusiness.DataConnection.DBtype);
                dcon.setDb("Data Source=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=Jason)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=XE)));User Id=unittest;Password=unittest;","",OpenDentBusiness.DataConnection.DBtype,true);
                RemotingClient.RemotingRole = RemotingRole.ClientDirect;
                return true;
            } 
        }
        catch (Exception ex)
        {
            //throw new Exception(ex.Message);
            //MessageBox.Show(ex.Message);
            //textResults.Text="Make a copy of any OD db and rename it to unittest.";
            if (isOracle)
            {
                MessageBox.Show("May need to create a Fresh Db for Oracle.");
            }
             
            return false;
        }
    
    }

    public static String freshFromDump(boolean isOracle) throws Exception {
        if (!isOracle)
        {
            String command = "DROP DATABASE IF EXISTS unittest";
            try
            {
                DataCore.nonQ(command);
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new Exception("Database could not be dropped.  Please remove any remaining text files and try again.");
            }

            command = "CREATE DATABASE unittest";
            DataCore.nonQ(command);
            setDbConnection("unittest",false);
            command = Resources.getdump();
            DataCore.nonQ(command);
            String toVersion = Assembly.GetAssembly(OpenDental.PrefL.class).GetName().Version.ToString();
            //MessageBox.Show(Application.ProductVersion+" - "+
            if (!OpenDental.PrefL.convertDB(true,toVersion))
            {
                throw new Exception("Wrong version.");
            }
             
            ProcedureCodes.tcodesClear();
            FormProcCodes.ImportProcCodes("", CDT.Class1.GetADAcodes(), "");
            //IF THIS LINE CRASHES:
            //Go to Solution, Configuration Manager.  Exclude UnitTest project from build.
            AutoCodes.setToDefault();
            ProcButtons.setToDefault();
            ProcedureCodes.resetApptProcsQuickAdd();
            //RefreshCache (might be missing a few)  Or, it might make more sense to do this as an entirely separate method when running.
            ProcedureCodes.refreshCache();
        }
        else
        {
        } 
        return "Fresh database loaded from sql dump.\r\n";
    }

    //This stopped working. Might look into it later: for now manually create the unittest db
    //Make sure the command CREATE OR REPLACE DIRECTORY dmpdir AS 'c:\oraclexe\app\tmp'; was run
    //and there is an opendental user with matching username/pass
    //The unittest.dmp was taken from a fresh unittest db created from the code above.  No need to alter it further.
    //string command=@"impdp opendental/opendental DIRECTORY=dmpdir DUMPFILE=unittest.dmp TABLE_EXISTS_ACTION=replace LOGFILE=impschema.log";
    //ExecuteCommand(command);
    private static void executeCommand(String Command) throws Exception {
        try
        {
            System.Diagnostics.ProcessStartInfo ProcessInfo = new System.Diagnostics.ProcessStartInfo();
            System.Diagnostics.Process Process = new System.Diagnostics.Process();
            ProcessInfo = new System.Diagnostics.ProcessStartInfo("cmd.exe", "/C " + Command);
            ProcessInfo.CreateNoWindow = false;
            ProcessInfo.UseShellExecute = false;
            Process = System.Diagnostics.Process.Start(ProcessInfo);
            Process.Close();
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new Exception("Running cmd failed.");
        }
    
    }

    public static String clearDb() throws Exception {
        String command = "\r\n" + 
        "\t\t\t\tDELETE FROM appointment;\r\n" + 
        "\t\t\t\tDELETE FROM carrier;\r\n" + 
        "\t\t\t\tDELETE FROM claim;\r\n" + 
        "\t\t\t\tDELETE FROM claimproc;\r\n" + 
        "\t\t\t\tDELETE FROM clockevent;\r\n" + 
        "\t\t\t\tDELETE FROM employee;\r\n" + 
        "\t\t\t\tDELETE FROM fee;\r\n" + 
        "\t\t\t\tDELETE FROM feesched WHERE FeeSchedNum !=53; /*because this is the default fee schedule for providers*/\r\n" + 
        "\t\t\t\tDELETE FROM hl7def;\r\n" + 
        "\t\t\t\tDELETE FROM hl7msg;\r\n" + 
        "\t\t\t\tDELETE FROM insplan;\r\n" + 
        "\t\t\t\tDELETE FROM patient;\r\n" + 
        "\t\t\t\tDELETE FROM patplan;\r\n" + 
        "\t\t\t\tDELETE FROM payperiod;\r\n" + 
        "\t\t\t\tDELETE FROM procedurelog;\r\n" + 
        "\t\t\t\tDELETE FROM provider WHERE ProvNum>2;\r\n" + 
        "\t\t\t\tDELETE FROM timeadjust;\r\n" + 
        "\t\t\t\tDELETE FROM timecardrule;\r\n" + 
        "\t\t\t\t";
        DataCore.nonQ(command);
        Providers.refreshCache();
        FeeScheds.refreshCache();
        return "Database cleared of old data.\r\n";
    }

}


