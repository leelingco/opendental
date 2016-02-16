//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

/**
* 
*/
public class DBSWin   
{
    /**
    * 
    */
    public DBSWin() throws Exception {
    }

    /**
    * Sends data for Patient.Cur to an import file which DBSWin will automatically recognize.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        if (pat == null)
        {
            MsgBox.show("DBSWin","Please select a patient first.");
            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Text file path");
        String infoFile = PPCur.PropertyValue;
        try
        {
            StreamWriter sw = new StreamWriter(infoFile, false);
            try
            {
                {
                    //PATLASTNAME;PATFIRSTNAME;PATBIRTHDAY;PATCARDNUMBER;PATTOWN;PATSTREET;PATPHONENUMBER;PATTITLE;PATSEX;PATPOSTALCODE;
                    //everything after birthday is optional
                    sw.Write(tidy(pat.LName) + ";");
                    sw.Write(tidy(pat.FName) + ";");
                    sw.Write(pat.Birthdate.ToString("d.M.yyyy") + ";");
                    PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                        ;
                    if (StringSupport.equals(PPCur.PropertyValue, "0"))
                    {
                        sw.Write(pat.PatNum.ToString() + ";");
                    }
                    else
                    {
                        sw.Write(tidy(pat.ChartNumber) + ";");
                    } 
                    sw.Write(tidy(pat.City) + ";");
                    sw.Write(tidy(pat.Address) + ";");
                    sw.Write(tidy(pat.HmPhone) + ";");
                    sw.Write(";");
                    //title
                    if (pat.Gender == PatientGender.Female)
                        sw.Write("f;");
                    else
                        sw.Write("m;"); 
                    sw.Write(tidy(pat.Zip) + ";");
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
        }
    
    }

    /**
    * Strips out the semicolons.
    */
    private static String tidy(String str) throws Exception {
        return str.Replace(";", "");
    }

}


