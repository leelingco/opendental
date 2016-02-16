//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class BioPAK   
{
    /**
    * 
    */
    public BioPAK() throws Exception {
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
            String info = " -n";
            if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
            {
                info += pat.PatNum.ToString();
            }
            else
            {
                info += pat.ChartNumber;
            } 
            info += " -l" + pat.LName.Replace(" ", "").Replace("\"", "") + " -f" + pat.FName.Replace(" ", "").Replace("\"", "") + " -i" + pat.MiddleI.Replace(" ", "").Replace("\"", "");
            if (pat.Gender == PatientGender.Female)
            {
                info += " -sF";
            }
            else if (pat.Gender == PatientGender.Male)
            {
                info += " -sM";
            }
              
            if (pat.Birthdate.Year > 1880)
            {
                info += " -m" + pat.Birthdate.Month + " -d" + pat.Birthdate.Day + " -y" + pat.Birthdate.Year;
            }
             
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


