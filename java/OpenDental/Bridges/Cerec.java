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
public class Cerec   
{
    /**
    * 
    */
    public Cerec() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //Example CerPI.exe -<patNum>;<fname>;<lname>;<birthday DD.MM.YYYY>; (Date format specified in the windows Regional Settings)
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start Cerec without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        String info = " -";
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            info += pat.PatNum.ToString() + ";";
        }
        else
        {
            info += pat.ChartNumber.ToString() + ";";
        } 
        info += pat.FName + ";" + pat.LName + ";" + pat.Birthdate.ToShortDateString() + ";";
        try
        {
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(path + " is not available, or there is an error in the command line options.");
        }
    
    }

}


