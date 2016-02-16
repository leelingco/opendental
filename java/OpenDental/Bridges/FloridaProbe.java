//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
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
public class FloridaProbe   
{
    /**
    * 
    */
    public FloridaProbe() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.  They also have an available file based method which passes more information like missing teeth, but we don't use it yet.  Their bridge specs are freely posted on their website.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start Florida Probe without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        String info = "/search ";
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += "/chart \"" + pat.PatNum.ToString() + "\" ";
        }
        else
        {
            info += "/chart \"" + cleanup(pat.ChartNumber) + "\" ";
        } 
        info += "/first \"" + cleanup(pat.FName) + "\" " + "/last \"" + cleanup(pat.LName) + "\"";
        try
        {
            //MessageBox.Show(info);
            //not used yet: /inputfile "path to file"
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

    /**
    * Makes sure invalid characters don't slip through.
    */
    private static String cleanup(String input) throws Exception {
        return input.Replace("\"", "");
    }

}


//get rid of any quotes