//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* 
*/
public class Apixia   
{
    /**
    * 
    */
    public Apixia() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.///
    */
    /*Data like the following:
    [Patient]
    ID=A123456789
    Gender=Male
    First=John
    Last=Smith
    Year=1955
    Month=1
    Day=23
    [Dentist]
    ID=001
    Password=1234
    		 
    Should appear in the following file: C:/Program Files/Digirex/Switch.ini
    and should be accessed/opened by C:/Program Files/Digirex/digirex.ini
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
                //should start Apixia without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        }
        else
        {
            String iniString = "[Patient]\r\n";
            if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
            {
                iniString += "ID=" + pat.PatNum.ToString() + "\r\n";
            }
            else
            {
                iniString += "ID=" + pat.ChartNumber + "\r\n";
            } 
            Provider priProv = Providers.getProv(pat.PriProv);
            iniString += "Gender=" + pat.Gender.ToString() + "\r\n" + "First=" + pat.FName + "\r\n" + "Last=" + pat.LName + "\r\n" + "Year=" + pat.Birthdate.Year.ToString() + "\r\n" + "Month=" + pat.Birthdate.Month.ToString() + "\r\n" + "Day=" + pat.Birthdate.Day.ToString() + "\r\n" + "[Dentist]\r\n" + "ID=" + priProv.Abbr + "\r\n" + "Password=digirex";
            //Abbreviation is guaranteed non-blank, because of UI validation in the provider edit window.
            // Write the string to a file.
            String iniPath = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"System path to Apixia Digital Imaging ini file");
            StreamWriter iniFile = new System.IO.StreamWriter(iniPath);
            iniFile.WriteLine(iniString);
            iniFile.Close();
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar1)
            {
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


