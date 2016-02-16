//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
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
public class RayMage   
{
    /**
    * 
    */
    public RayMage() throws Exception {
    }

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
                //should start rayMage without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        }
        else
        {
            String info = " /PATID \"";
            if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
            {
                info += pat.PatNum.ToString();
            }
            else
            {
                info += pat.ChartNumber;
            } 
            info += "\" /NAME \"" + pat.FName.Replace(" ", "").Replace("\"", "") + "\" /SURNAME \"" + pat.LName.Replace(" ", "").Replace("\"", "") + "\"";
            try
            {
                Process.Start(path, ProgramCur.CommandLine + info);
            }
            catch (Exception __dummyCatchVar1)
            {
                MessageBox.Show(path + " is not available, or there is an error in the command line options.");
            }
        
        } 
    }

}


