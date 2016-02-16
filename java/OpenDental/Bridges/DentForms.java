//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class DentForms   
{
    /**
    * 
    */
    public DentForms() throws Exception {
    }

    /**
    * Launches the program using the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //mtconnector.exe -patid 123  -fname John  -lname Doe  -ssn 123456789  -dob 01/25/1962  -gender M
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first");
            return ;
        }
         
        String info = "-patid ";
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += pat.PatNum.ToString() + "  ";
        }
        else
        {
            info += pat.ChartNumber + "  ";
        } 
        info += "-fname " + pat.FName + "  " + "-lname " + pat.LName + "  " + "-ssn " + pat.SSN + "  " + "-dob " + pat.Birthdate.ToShortDateString() + "  " + "-gender ";
        if (pat.Gender == PatientGender.Male)
        {
            info += "M";
        }
        else
        {
            info += "F";
        } 
        try
        {
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


