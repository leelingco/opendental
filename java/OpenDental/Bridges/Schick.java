//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

/**
* Provides bridging functionality to Schick CDR.
*/
public class Schick   
{
    /**
    * Default constructor
    */
    public Schick() throws Exception {
    }

    /**
    * Launches the main Patient Document window of Schick.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        if (pat == null)
        {
            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        String patID = "";
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            patID = pat.PatNum.ToString();
        }
        else
        {
            patID = pat.ChartNumber;
        } 
        try
        {
            VBbridges.Schick3.Launch(patID, pat.LName, pat.FName);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Error launching Schick CDR Dicom.");
        }
    
    }

}


