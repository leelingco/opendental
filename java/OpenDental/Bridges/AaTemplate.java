//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* This class is just an example template that we use when we build a new bridge.  Start with a copy of this.
*/
public class AaTemplate   
{
    /**
    * 
    */
    public AaTemplate() throws Exception {
    }

    /**
    * 
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            try
            {
                //There are two options here, depending on the bridge
                //1. Launch program without any patient.
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start AaTemplate without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            //2. Tell user to pick a patient first.
            MsgBox.show("AaTemplate","Please select a patient first.");
            return ;
        }
         
        //return in both cases
        //It's common to build a string
        String str = "";
        //ProgramProperties.GetPropVal() is the way to get program properties.
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            str += "Id=" + pat.PatNum.ToString() + " ";
        }
        else
        {
            str += "Id=" + tidy(pat.ChartNumber) + " ";
        } 
        //Nearly always tidy the names in one way or another
        str += tidy(pat.LName) + " ";
        //If birthdates are optional, only send them if they are valid.
        if (pat.Birthdate.Year > 1880)
        {
            str += pat.Birthdate.ToString("MM/dd/yyyy");
        }
         
        //This patterns shows a way to handle gender unknown when gender is optional.
        if (pat.Gender == PatientGender.Female)
        {
            str += "F ";
        }
        else if (pat.Gender == PatientGender.Male)
        {
            str += "M ";
        }
          
        try
        {
            Process.Start(path, str);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Removes semicolons and spaces.
    */
    private static String tidy(String input) throws Exception {
        String retVal = input.Replace(";", "");
        //get rid of any semicolons.
        retVal = retVal.Replace(" ", "");
        return retVal;
    }

}


//Template code for ConvertDatabases2.cs
/**
* /Insert AaTemplate Bridge
*/
//if(DataConnection.DBtype==DatabaseType.MySql) {
//  command="INSERT INTO program (ProgName,ProgDesc,Enabled,Path,CommandLine,Note"
//    +") VALUES("
//    +"'AaTemplate', "
//    +"'AaTemplate from www.AaTemplate.com', "
//    +"'0', "
//    +"'"+POut.String(@"C:\Program Files (x86)\AaTemplate\AaTemplate.exe")+"',"
//    +"'', "
//    +"'')";
//  long programNum=Db.NonQ(command,true);
//  command="INSERT INTO programproperty (ProgramNum,PropertyDesc,PropertyValue"
//    +") VALUES("
//    +"'"+POut.Long(programNum)+"', "
//    +"'Enter 0 to use PatientNum, or 1 to use ChartNum', "
//    +"'0')";
//  Db.NonQ(command);
//  command="INSERT INTO toolbutitem (ProgramNum,ToolBar,ButtonText) "
//    +"VALUES ("
//    +"'"+POut.Long(programNum)+"', "
//    +"'"+POut.Int(((int)ToolBarsAvail.ChartModule))+"', "
//    +"'AaTemplate')";
//  Db.NonQ(command);
//}
//else {//oracle
//  command="INSERT INTO program (ProgramNum,ProgName,ProgDesc,Enabled,Path,CommandLine,Note"
//    +") VALUES("
//    +"(SELECT MAX(ProgramNum)+1 FROM program),"
//    +"'AaTemplate', "
//    +"'AaTemplate from www.AaTemplate.com', "
//    +"'0', "
//    +"'"+POut.String(@"C:\Program Files (x86)\AaTemplate\AaTemplate.exe")+"',"
//    +"'', "
//    +"'')";
//  long programNum=Db.NonQ(command,true);
//  command="INSERT INTO programproperty (ProgramPropertyNum,ProgramNum,PropertyDesc,PropertyValue"
//    +") VALUES("
//    +"(SELECT MAX(ProgramPropertyNum+1) FROM programproperty),"
//    +"'"+POut.Long(programNum)+"', "
//    +"'Enter 0 to use PatientNum, or 1 to use ChartNum', "
//    +"'0')";
//  Db.NonQ(command);
//  command="INSERT INTO toolbutitem (ToolButItemNum,ProgramNum,ToolBar,ButtonText) "
//    +"VALUES ("
//    +"(SELECT MAX(ToolButItemNum)+1 FROM toolbutitem),"
//    +"'"+POut.Long(programNum)+"', "
//    +"'"+POut.Int(((int)ToolBarsAvail.ChartModule))+"', "
//    +"'AaTemplate')";
//  Db.NonQ(command);
//}//end AaTemplate bridge