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

public class Progeny   
{
    /**
    * Launches the program using command line.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        Process pibridge = new Process();
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        try
        {
            if (pat != null)
            {
                ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                String id = "";
                if (StringSupport.equals(PPCur.PropertyValue, "0"))
                {
                    id = pat.PatNum.ToString();
                }
                else
                {
                    id = pat.ChartNumber;
                } 
                String lname = pat.LName.Replace("\"", "").Replace(",", "");
                String fname = pat.FName.Replace("\"", "").Replace(",", "");
                pibridge = new Process();
                pibridge.StartInfo.CreateNoWindow = false;
                pibridge.StartInfo.UseShellExecute = true;
                pibridge.StartInfo.FileName = path;
                //Double-quotes are removed from id and name to prevent malformed command. ID could have double-quote if chart number.
                pibridge.StartInfo.Arguments = "cmd=open id=\"" + id.Replace("\"", "") + "\" first=\"" + fname.Replace("\"", "") + "\" last=\"" + lname.Replace("\"", "") + "\"";
                pibridge.Start();
            }
            else
            {
                //if patient is loaded
                //Should start Progeny without bringing up a pt.
                pibridge = new Process();
                pibridge.StartInfo.CreateNoWindow = false;
                pibridge.StartInfo.UseShellExecute = true;
                pibridge.StartInfo.FileName = path;
                pibridge.StartInfo.Arguments = "cmd=start";
                pibridge.Start();
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


