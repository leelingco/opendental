//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

public class Sopro   
{
    /**
    * Launches the program using command line.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        if (pat != null)
        {
            String info = "";
            //Patient id can be any string format
            ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                info += " " + pat.PatNum.ToString();
            }
            else
            {
                info += " " + pat.ChartNumber;
            } 
            //We remove double-quotes from the first and last name of the patient so extra double-quotes don't
            //cause confusion in the command line parameters for Sopro.
            info += " " + pat.LName.Replace("\"", "") + " " + pat.FName.Replace("\"", "");
            try
            {
                Process.Start(path, ProgramCur.CommandLine + info);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(path + " is not available, or there is an error in the command line options.");
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
                //should start Sopro without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


