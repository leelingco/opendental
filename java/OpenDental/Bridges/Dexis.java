//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* Also used by the XDR bridge.
*/
public class Dexis   
{
    /**
    * 
    */
    public Dexis() throws Exception {
    }

    /**
    * Sends data for Patient.Cur to the InfoFile and launches the program.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"InfoFile path");
        String infoFile = PPCur.PropertyValue;
        if (pat != null)
        {
            try
            {
                //patientID can be any string format, max 8 char.
                //There is no validation to ensure that length is 8 char or less.
                PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                String id = "";
                if (StringSupport.equals(PPCur.PropertyValue, "0"))
                {
                    id = pat.PatNum.ToString();
                }
                else
                {
                    id = pat.ChartNumber;
                } 
                StreamWriter sw = new StreamWriter(infoFile, false);
                try
                {
                    {
                        sw.WriteLine(pat.LName + ", " + pat.FName + "  " + pat.Birthdate.ToShortDateString() + "  (" + id + ")");
                        sw.WriteLine("PN=" + id);
                        //sw.WriteLine("PN="+pat.PatNum.ToString());
                        sw.WriteLine("LN=" + pat.LName);
                        sw.WriteLine("FN=" + pat.FName);
                        sw.WriteLine("BD=" + pat.Birthdate.ToShortDateString());
                        if (pat.Gender == PatientGender.Female)
                            sw.WriteLine("SX=F");
                        else
                            sw.WriteLine("SX=M"); 
                    }
                }
                finally
                {
                    if (sw != null)
                        Disposable.mkDisposable(sw).dispose();
                     
                }
                Process.Start(path, "@" + infoFile);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(path + " is not available.");
            }
        
        }
        else
        {
            try
            {
                //if patient is loaded
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar1)
            {
                //should start Dexis without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


