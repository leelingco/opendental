//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Tscan   
{
    /**
    * 
    */
    public Tscan() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    * Arguments: tscan.exe [-fFirstname [-mMiddlename] -lLastname -iPatientid [-dBirthday] [-jBirthmonth] [-yBirthyear] [-gGender]]
    * Example: tscan.exe -fBrent -lThompson -iBT1000 -d07 -j02 -y1962 -g2
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
                //should start Tscan without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        String info = "-f" + tidy(pat.FName) + " ";
        //First name can only be alpha-numeric, so we remove non-alpha-numeric characters.
        if (!StringSupport.equals(tidy(pat.MiddleI), ""))
        {
            //Only send middle name if available, since it is optional.
            info += "-m" + tidy(pat.MiddleI) + " ";
        }
         
        //Middle name can only be alpha-numeric, so we remove non-alpha-numeric characters.
        info += "-l" + tidy(pat.LName) + " ";
        //Last name can only be alpha-numeric, so we remove non-alpha-numeric characters.
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            info += "-i" + pat.PatNum.ToString() + " ";
        }
        else
        {
            info += "-i" + tidy(pat.ChartNumber) + " ";
        } 
        //Patient id only alpha-numeric as required.
        //Birthdate is optional, so we only send if valid.
        if (pat.Birthdate.Year > 1880)
        {
            info += "-d" + pat.Birthdate.Day.ToString().PadLeft(2, '0') + " ";
            //The example in specification shows days with two digits, so we pad.
            info += "-j" + pat.Birthdate.Month.ToString().PadLeft(2, '0') + " ";
            //The example in specification shows months with two digits, so we pad.
            info += "-y" + pat.Birthdate.Year.ToString() + " ";
        }
         
        //The example specification shows years 4 digits long.
        //Gender is optional, so we only send if not Unknown.
        if (pat.Gender == PatientGender.Female)
        {
            info += "-g1 ";
        }
        else if (pat.Gender == PatientGender.Male)
        {
            info += "-g2 ";
        }
          
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
    * Removes non-alpha-numeric characters.
    */
    private static String tidy(String input) throws Exception {
        String retVal = Regex.Replace(input, "[^a-zA-Z0-9]", "");
        return retVal;
    }

}


//get rid of any non-alpha-numeric characters.