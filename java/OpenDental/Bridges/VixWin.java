//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class VixWin   
{
    /**
    * 
    */
    public VixWin() throws Exception {
    }

    /**
    * Sends data for Patient.Cur by command line interface.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            return ;
        }
         
        //Example: c:\vixwin\vixwin -I 123ABC -N Bill^Smith -P X:\VXImages\
        String info = "-I ";
        boolean isChartNum = PIn.Bool(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"));
        String ppImagePath = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Optional Image Path");
        if (isChartNum)
        {
            info += pat.ChartNumber;
        }
        else
        {
            //max 64 char
            info += pat.PatNum.ToString();
        } 
        info += " -N " + pat.FName.Replace(" ", "") + "^" + pat.LName.Replace(" ", "");
        //no spaces allowed
        if (!StringSupport.equals(ppImagePath, ""))
        {
            //optional image path
            if (!Directory.Exists(ppImagePath))
            {
                MessageBox.Show("Unable to find image path " + ppImagePath);
                return ;
            }
             
            info += " -P " + ppImagePath;
        }
         
        try
        {
            Process.Start(path, info);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message + "\r\nFile and command line:\r\n" + path + " " + info);
        }
    
    }

}


