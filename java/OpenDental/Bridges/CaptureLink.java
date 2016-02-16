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
public class CaptureLink   
{
    /**
    * 
    */
    public CaptureLink() throws Exception {
    }

    /**
    * This bridge has not yet been added to the database.  CaptureLink reads the bridge parameters from the clipboard.
    */
    //Command format: LName FName PatID
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        if (pat == null)
        {
            MessageBox.Show("No patient selected.");
            return ;
        }
         
        String path = Programs.getProgramPath(ProgramCur);
        String info = tidy(pat.LName) + " ";
        info += tidy(pat.FName) + " ";
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            info += pat.PatNum.ToString();
        }
        else
        {
            if (pat.ChartNumber == null || StringSupport.equals(pat.ChartNumber, ""))
            {
                MsgBox.show("CaptureLink","This patient does not have a chart number.");
                return ;
            }
             
            info += tidy(pat.ChartNumber);
        } 
        Clipboard.Clear();
        Clipboard.SetText(info, TextDataFormat.Text);
        try
        {
            Process.Start(path);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Removes double-quotes and spaces.
    */
    private static String tidy(String str) throws Exception {
        str = str.Replace("\"", "");
        return str.Replace(" ", "");
    }

}


//Remove double-quotes.
//Remove spaces.