//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class ClioSoft   
{
    /**
    * 
    */
    public ClioSoft() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    * Arguments: ClioSoft.exe “-Id;FirstName;LastName;DateOfBirth;SocialSecurityNumber;”
    * Only Id, FirstName and LastName are required.
    * Example 1: ClioSoft.exe "-100;Jim;Jones;01/02/2000;123-456-789;"
    * Example 2: ClioSoft.exe "-200;Jane;Smith;;;"
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start ClioSoft without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        String info = "\"";
        //Patient id can be any string format
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            info += "-" + pat.PatNum.ToString() + ";";
        }
        else
        {
            info += "-" + tidy(pat.ChartNumber) + ";";
        } 
        info += tidy(pat.FName) + ";";
        info += tidy(pat.LName) + ";";
        //Birthdate is optional, so we only send if valid.
        if (pat.Birthdate.Year > 1880)
        {
            info += pat.Birthdate.ToString("MM/dd/yyyy") + ";";
        }
        else
        {
            info += ";";
        } 
        info += tidy(pat.SSN) + ";";
        //They don't care about dashes and there is no length restriction, just not semi-colons.
        info += "\"";
        try
        {
            Process.Start(path, info);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Removes semi-colons.
    */
    private static String tidy(String input) throws Exception {
        String retVal = input.Replace(";", "");
        return retVal;
    }

}


//get rid of any semicolons.