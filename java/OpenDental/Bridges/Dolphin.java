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
public class Dolphin   
{
    /**
    * 
    */
    public Dolphin() throws Exception {
    }

    //"DOLCTRL patnum" launches Dolphin with selected patient.
    //C:\DOLPHIN\DOLDB.EXE imports patient info from a file
    /**
    * Launches the program using a command line tools.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        String pathDolDb = Path.Combine(path, "dolDb.exe");
        String pathDolCtrl = Path.Combine(path, "dolCtrl.exe");
        if (pat == null)
        {
            try
            {
                Process.Start(pathDolCtrl);
            }
            catch (Exception e)
            {
                //should start Dolphin without bringing up a pt.
                MessageBox.Show(e.Message + "\r\n" + pathDolCtrl);
            }

            return ;
        }
         
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        String patientId = pat.PatNum.ToString();
        if (StringSupport.equals(PPCur.PropertyValue, "1"))
        {
            patientId = pat.ChartNumber;
        }
         
        PPCur = ProgramProperties.getCur(ForProgram,"Filename");
        String filename = PPCur.PropertyValue;
        StringBuilder txt = new StringBuilder();
        txt.Append("[Patient Info]\r\n");
        txt.Append("PatientID=" + tidy(patientId,10) + "\r\n");
        txt.Append("LastName=" + tidy(pat.LName,50) + "\r\n");
        txt.Append("FirstName=" + tidy(pat.FName,50) + "\r\n");
        if (pat.Birthdate.Year > 1880)
        {
            txt.Append("Birthdate=" + pat.Birthdate.ToString("MM-dd-yyyy") + "\r\n");
        }
         
        //mm-dd-yyyy
        String gender = "0";
        if (pat.Gender == PatientGender.Female)
        {
            gender = "1";
        }
         
        txt.Append("Gender=" + gender + "\r\n");
        txt.Append("NickName=" + tidy(pat.Preferred,30) + "\r\n");
        txt.Append("Title=" + tidy(pat.Title,10) + "\r\n");
        txt.Append("Email=" + tidy(pat.Email,60) + "\r\n");
        //txt.Append("Notes="+Tidy(,)+"\r\n");//no limit?
        //there are more fields, but that's enough for now.
        File.WriteAllText(filename, txt.ToString());
        try
        {
            Process proc = new Process();
            proc.StartInfo = new ProcessStartInfo(pathDolDb, "Find -i" + patientId);
            proc.Start();
            proc.WaitForExit();
            //proc.WaitForExit(5000);//5 seconds
            int exitcode = proc.ExitCode;
            //0=notfound, 135=found
            if (exitcode == 0)
            {
                //not found
                Process.Start(pathDolDb, "AddPatient -f\"" + filename + "\" -i" + patientId);
            }
            else if (exitcode == 135)
            {
                //found
                Process.Start(pathDolDb, "UpdatePatient -f\"" + filename + "\" -i" + patientId);
            }
            else
            {
                MessageBox.Show("Error in dolDb.exe Find.  Code=" + exitcode.ToString());
            }  
            Process.Start(pathDolCtrl, patientId);
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
        }
    
    }

    private static String tidy(String txt, int maxLength) throws Exception {
        if (txt.Length > maxLength)
        {
            return txt.Substring(0, maxLength);
        }
         
        return txt;
    }

}


