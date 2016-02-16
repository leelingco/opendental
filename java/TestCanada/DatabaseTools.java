//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import OpenDentBusiness.DataCore;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import TestCanada.Properties.Resources;

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
    public static boolean setDbConnection(String dbName) throws Exception {
        OpenDentBusiness.DataConnection dcon;
        try
        {
            //Try to connect to the database directly
            OpenDentBusiness.DataConnection.DBtype = OpenDentBusiness.DatabaseType.MySql;
            dcon = new OpenDentBusiness.DataConnection(OpenDentBusiness.DataConnection.DBtype);
            dcon.setDb("Server=localhost;Database=" + dbName + ";User ID=root;Password=;CharSet=utf8;Treat Tiny As Boolean=false","",OpenDentBusiness.DataConnection.DBtype,true);
            RemotingClient.RemotingRole = RemotingRole.ClientDirect;
            return true;
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }
    
    }

    //(Exception ex){
    //throw new Exception(ex.Message);
    //MessageBox.Show(ex.Message);
    //textResults.Text="Make a copy of any OD db and rename it to canadatest.";
    public static String freshFromDump() throws Exception {
        String command = "DROP DATABASE IF EXISTS canadatest";
        try
        {
            DataCore.nonQ(command);
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new Exception("Database could not be dropped.  Please remove any remaining text files and try again.");
        }

        command = "CREATE DATABASE canadatest";
        DataCore.nonQ(command);
        setDbConnection("canadatest");
        command = Resources.getdumpcanada();
        DataCore.nonQ(command);
        String toVersion = Assembly.GetAssembly(OpenDental.PrefL.class).GetName().Version.ToString();
        //MessageBox.Show(Application.ProductVersion+" - "+
        if (!OpenDental.PrefL.convertDB(true,toVersion))
        {
            throw new Exception("Wrong version.");
        }
         
        return "Fresh database loaded from sql dump.\r\n";
    }

    /*
    			ProcedureCodes.TcodesClear();
    			//FormProcCodes.ImportProcCodes("",null,OpenDental.Properties.Resources.NoFeeProcCodes);
    			FormProcCodes.ImportProcCodes("",CDT.Class1.GetADAcodes(),"");//Yes, this will be broken if not on a specially configured development machine.
    			AutoCodes.SetToDefault();
    			ProcButtons.SetToDefault();
    			ProcedureCodes.ResetApptProcsQuickAdd();
    			//RefreshCache (might be missing a few)  Or, it might make more sense to do this as an entirely separate method when running.
    			ProcedureCodes.RefreshCache();
    */
    public static String clearDb() throws Exception {
        String command = "\r\n" + 
        "\t\t\t\tDELETE FROM carrier;\r\n" + 
        "\t\t\t\tDELETE FROM claim;\r\n" + 
        "\t\t\t\tDELETE FROM claimproc;\r\n" + 
        "\t\t\t\tDELETE FROM fee;\r\n" + 
        "\t\t\t\tDELETE FROM feesched WHERE FeeSchedNum !=53; /*because this is the default fee schedule for providers*/\r\n" + 
        "\t\t\t\tDELETE FROM insplan;\r\n" + 
        "\t\t\t\tDELETE FROM patient;\r\n" + 
        "\t\t\t\tDELETE FROM patplan;\r\n" + 
        "\t\t\t\tDELETE FROM procedurelog;\r\n" + 
        "\t\t\t\tDELETE FROM etrans;\r\n" + 
        "\t\t\t\t";
        DataCore.nonQ(command);
        ProcedureCodes.refreshCache();
        ProcedureCode procCode;
        if (!ProcedureCodes.isValidCode("99222"))
        {
            procCode = new ProcedureCode();
            procCode.ProcCode = "99222";
            procCode.Descript = "Lab2";
            procCode.AbbrDesc = "Lab2";
            procCode.IsCanadianLab = true;
            procCode.ProcCat = 256;
            procCode.ProcTime = "/X/";
            procCode.TreatArea = TreatmentArea.Mouth;
            ProcedureCodes.insert(procCode);
            ProcedureCodes.refreshCache();
        }
         
        procCode = ProcedureCodes.getProcCode("99111");
        procCode.IsCanadianLab = true;
        ProcedureCodes.update(procCode);
        ProcedureCodes.refreshCache();
        if (!ProcedureCodes.isValidCode("27213"))
        {
            procCode = new ProcedureCode();
            procCode.ProcCode = "27213";
            procCode.Descript = "Crown";
            procCode.AbbrDesc = "Crn";
            procCode.ProcCat = 250;
            procCode.ProcTime = "/X/";
            procCode.TreatArea = TreatmentArea.Tooth;
            procCode.PaintType = ToothPaintingType.CrownLight;
            ProcedureCodes.insert(procCode);
            ProcedureCodes.refreshCache();
        }
         
        procCode = ProcedureCodes.getProcCode("67211");
        procCode.TreatArea = TreatmentArea.Quad;
        ProcedureCodes.update(procCode);
        ProcedureCodes.refreshCache();
        return "Database cleared of old data.\r\n";
    }

}


