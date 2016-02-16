//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Apteryx   
{
    /**
    * 
    */
    public Apteryx() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        if (pat != null)
        {
            //We remove double-quotes from the first and last name of the patient so extra double-quotes don't
            //cause confusion in the command line parameters for Apteryx.
            String info = "\"" + pat.LName.Replace("\"", "") + ", " + pat.FName.Replace("\"", "") + "::";
            if (pat.SSN.Length == 9)
            {
                info += pat.SSN.Substring(0, 3) + "-" + pat.SSN.Substring(3, 2) + "-" + pat.SSN.Substring(5, 4);
            }
             
            //Patient id can be any string format
            ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                info += "::" + pat.PatNum.ToString();
            }
            else
            {
                info += "::" + pat.ChartNumber;
            } 
            info += "::" + pat.Birthdate.ToShortDateString() + "::";
            if (pat.Gender == PatientGender.Female)
                info += "F";
            else
                info += "M"; 
            info += "\"";
            try
            {
                //commandline default is /p
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
                //should start Apteryx without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


