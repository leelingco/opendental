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

//using System.Text.RegularExpressions;
/**
* 
*/
public class Trophy   
{
    /**
    * 
    */
    public Trophy() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat != null)
        {
            ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Storage Path");
            String comline = "-P" + PPCur.PropertyValue + "\\";
            //Patient id can be any string format
            PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                ;
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                comline += pat.PatNum.ToString();
            }
            else
            {
                comline += pat.ChartNumber;
            } 
            comline += " -N" + pat.LName + ", " + pat.FName;
            comline = comline.Replace("\"", "");
            //gets rid of any quotes
            comline = comline.Replace("'", "");
            try
            {
                //gets rid of any single quotes
                Process.Start(path, comline);
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
                //should start Trophy without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


