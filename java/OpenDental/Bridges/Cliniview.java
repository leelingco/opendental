//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Cliniview   
{
    /**
    * 
    */
    public Cliniview() throws Exception {
    }

    /**
    * Launches the program using command line.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //usage: C:\Program Files\CliniView\CliniView.exe -123;John;Smith;123456789;01/01/1980
        //Critical not to have any spaces in argument string.
        //We got this info directly from the programmers at CliniView
        if (pat == null)
        {
            MsgBox.show("Cliniview","Please select a patient first.");
            return ;
        }
         
        if (!File.Exists(path))
        {
            MessageBox.Show(path + " not found.");
            return ;
        }
         
        String info = "-";
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "1"))
        {
            if (StringSupport.equals(pat.ChartNumber, ""))
            {
                MsgBox.show("Cliniview","This patient has no ChartNumber entered.");
                return ;
            }
             
            info += pat.ChartNumber;
        }
        else
        {
            info += pat.PatNum.ToString();
        } 
        //dashes already missing
        info += ";" + tidy(pat.FName) + ";" + tidy(pat.LName) + ";" + pat.SSN + ";" + pat.Birthdate.ToString("MM/dd/yyyy");
        Process process = new Process();
        process.StartInfo = new ProcessStartInfo(path, info);
        try
        {
            process.Start();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Removes semicolons and spaces.
    */
    private static String tidy(String input) throws Exception {
        String retVal = input.Replace(";", "");
        //get rid of any semicolons.
        retVal = retVal.Replace(" ", "");
        return retVal;
    }

}


