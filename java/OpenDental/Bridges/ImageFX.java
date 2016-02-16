//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class ImageFX   
{
    /**
    * 
    */
    public ImageFX() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.  They also have an available file based method which passes more information, but we don't use it yet.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat != null)
        {
            String info = "-";
            ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                ;
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                info += ClipTo(pat.PatNum.ToString(), 10) + ";";
            }
            else
            {
                info += clipTo(pat.ChartNumber,10) + ";";
            } 
            info += clipTo(pat.FName,25) + ";" + clipTo(pat.LName,25) + ";" + clipTo(pat.SSN,15) + ";" + pat.Birthdate.ToString("MM/dd/yyyy") + ";";
            try
            {
                Process.Start(path, info);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(path + " is not available.");
            }
        
        }
        else
        {
            try
            {
                //if patient is loaded
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar1)
            {
                //should start ImageFX without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

    /**
    * Clips the length of the string as well as making sure invalid characters don't slip through.
    */
    private static String clipTo(String input, int length) throws Exception {
        String retVal = input.Replace(";", "");
        //get rid of any semicolons.
        if (retVal.Length > length)
        {
            retVal = retVal.Substring(0, length);
        }
         
        return retVal;
    }

}


