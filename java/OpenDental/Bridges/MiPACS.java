//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

public class MiPACS   
{
    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.///
    */
    /*The command line integration works as follows:
    C:\Program Files\MiDentView\Cmdlink.exe /ID=12345 /FN=JOHN /LN=DOE /BD=10/01/1985 /Sex=M
    Parameters:'/ID=' for ID number, '/FN=' for Firstname, '/LN=' for Lastname, '/BD=' for Birthdate, '/Sex=' for Sex.
    Example of a name with special characters (in this case, spaces):
    C:\Program Files\MiDentView\Cmdlink.exe /ID=12345 /FN=Oscar /LN=De La Hoya /BD=10/01/1985 /Sex=M
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
                //should start MiPACS without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        }
         
        String gender = (pat.Gender == PatientGender.Female) ? "F" : "M";
        //M for Male, F for Female, M for Unknown.
        String info = "";
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            info += "/ID=" + pat.PatNum.ToString();
        }
        else
        {
            info += "/ID=" + pat.ChartNumber;
        } 
        //special characters claimed to be ok
        info += " /FN=" + pat.FName + " /LN=" + pat.LName + " /BD=" + pat.Birthdate.ToShortDateString() + " /Sex=" + gender;
        try
        {
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


