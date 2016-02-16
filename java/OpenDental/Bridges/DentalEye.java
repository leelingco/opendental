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
* 
*/
public class DentalEye   
{
    /**
    * 
    */
    public DentalEye() throws Exception {
    }

    /**
    * Launches the program if necessary.  Then passes patient.Cur data using DDE.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first");
            return ;
        }
         
        //The path is available in the registry, but we'll just make the user enter it.
        if (!File.Exists(path))
        {
            MessageBox.Show("Could not find " + path);
            return ;
        }
         
        //Make sure the program is running
        if (Process.GetProcessesByName("DentalEye").Length == 0)
        {
            Process.Start(path);
            Thread.Sleep(TimeSpan.FromSeconds(4));
        }
         
        //command="[Add][PatNum][Fname][Lname][Address|Address2|City, ST Zip][phone1][phone2][mobile phone][email][sex(M/F)][birthdate (YYYY-MM-DD)]"
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        String patID = new String();
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            patID = pat.PatNum.ToString();
        }
        else
        {
            if (StringSupport.equals(pat.ChartNumber, ""))
            {
                MessageBox.Show("ChartNumber for this patient is blank.");
                return ;
            }
             
            patID = pat.ChartNumber;
        } 
        String command = "[Add][" + patID + "]" + "[" + pat.FName + "]" + "[" + pat.LName + "]" + "[" + pat.Address + "|";
        if (!StringSupport.equals(pat.Address2, ""))
        {
            command += pat.Address2 + "|";
        }
         
        command += pat.City + ", " + pat.State + " " + pat.Zip + "]" + "[" + pat.HmPhone + "]" + "[" + pat.WkPhone + "]" + "[" + pat.WirelessPhone + "]" + "[" + pat.Email + "]";
        if (pat.Gender == PatientGender.Female)
            command += "[F]";
        else
            command += "[M]"; 
        command += "[" + pat.Birthdate.ToString("yyyy-MM-dd") + "]";
        try
        {
            //MessageBox.Show(command);
            //Create a context that uses a dedicated thread for DDE message pumping.
            DdeContext context = new DdeContext();
            try
            {
                {
                    //Create a client.
                    DdeClient client = new DdeClient("DENTEYE", "Patient", context);
                    try
                    {
                        {
                            //Establish the conversation.
                            client.Connect();
                            //Add patient or modify if already exists
                            client.Execute(command, 2000);
                            //timeout 2 secs
                            //Then, select patient
                            command = "[Search][" + patID + "]";
                            client.Execute(command, 2000);
                        }
                    }
                    finally
                    {
                        if (client != null)
                            Disposable.mkDisposable(client).dispose();
                         
                    }
                }
            }
            finally
            {
                if (context != null)
                    Disposable.mkDisposable(context).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

}


//MessageBox.Show(e.Message);