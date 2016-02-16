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
public class Lightyear   
{
    /**
    * 
    */
    public Lightyear() throws Exception {
    }

    /**
    * Launches the program using the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first");
            return ;
        }
         
        String info = "";
        //Patient id can be any string format
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += "-i \"" + pat.PatNum.ToString() + "\" ";
        }
        else
        {
            info += "-i \"" + pat.ChartNumber.Replace("\"", "") + "\" ";
        } 
        info += "-n \"" + pat.LName.Replace("\"", "") + ", " + pat.FName.Replace("\"", "") + "\"";
        try
        {
            //MessageBox.Show(info);
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


