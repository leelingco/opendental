//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

/**
* 
*/
public class VixWinOld   
{
    /**
    * 
    */
    public VixWinOld() throws Exception {
    }

    /**
    * Sends data for Patient.Cur to the QuikLink directory. No further action is required.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"QuikLink directory.");
        String quikLinkDir = PPCur.PropertyValue;
        if (pat == null)
        {
            return ;
        }
         
        if (!Directory.Exists(quikLinkDir))
        {
            MessageBox.Show(quikLinkDir + " is not a valid folder.");
            return ;
        }
         
        try
        {
            String patID = new String();
            PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                ;
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                patID = pat.PatNum.ToString().PadLeft(6, '0');
            }
            else
            {
                patID = pat.ChartNumber.PadLeft(6, '0');
            } 
            if (patID.Length > 6)
            {
                MessageBox.Show("Patient ID is longer than six digits, so link failed.");
                return ;
            }
             
            String fileName = quikLinkDir + patID + ".DDE";
            //MessageBox.Show(fileName);
            StreamWriter sw = new StreamWriter(fileName, false);
            try
            {
                {
                    sw.WriteLine("\"" + pat.FName + "\"," + "\"" + pat.LName + "\"," + "\"" + patID + "\"");
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Error creating file.");
        }
    
    }

}


