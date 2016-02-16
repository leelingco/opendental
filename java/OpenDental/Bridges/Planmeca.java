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

/**
* 
*/
public class Planmeca   
{
    /**
    * 
    */
    public Planmeca() throws Exception {
    }

    /**
    * Launches the program using the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //DxStart.exe ”PatientID” ”FamilyName” ”FirstName” ”BirthDate”
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first");
            return ;
        }
         
        String info = "";
        //Patient id can be any string format
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += "\"" + pat.PatNum.ToString() + "\" ";
        }
        else
        {
            info += "\"" + pat.ChartNumber.Replace("\"", "") + "\" ";
        } 
        info += "\"" + pat.LName.Replace("\"", "") + "\" " + "\"" + pat.FName.Replace("\"", "") + "\" " + "\"" + pat.Birthdate.ToString("dd/MM/yyyy") + "\"";
        try
        {
            //Planmeca is a Finland based company, so their date format is dd/MM/yyyy. We used to send date format "MM/dd/yyyy" for our American customers before 12/19/2011.
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


