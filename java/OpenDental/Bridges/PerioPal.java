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
public class PerioPal   
{
    /**
    * 
    */
    public PerioPal() throws Exception {
    }

    /**
    * Launches the program using command line.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //Usage: [Application Path]PerioPal "PtChart; PtName ; PtBday; PtMedAlert;"
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            return ;
        }
         
        String info = "\"";
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += pat.PatNum.ToString();
        }
        else
        {
            info += cleanup(pat.ChartNumber);
        } 
        info += ";" + cleanup(pat.LName) + ";" + cleanup(pat.FName) + ";" + pat.Birthdate.ToShortDateString() + ";";
        boolean hasMedicalAlert = false;
        if (!StringSupport.equals(pat.MedUrgNote, ""))
        {
            hasMedicalAlert = true;
        }
         
        if (pat.Premed)
        {
            hasMedicalAlert = true;
        }
         
        if (hasMedicalAlert)
        {
            info += "Y;";
        }
        else
        {
            info += "N;";
        } 
        try
        {
            //MessageBox.Show(info);
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " " + info + " is not available.");
        }
    
    }

    /**
    * Makes sure invalid characters don't slip through.
    */
    private static String cleanup(String input) throws Exception {
        String retVal = input;
        retVal = retVal.Replace(";", "");
        //get rid of any semicolon
        retVal = retVal.Replace(" ", "");
        return retVal;
    }

}


//get rid of any spaces