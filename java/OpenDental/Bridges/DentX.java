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

/**
* 
*/
public class DentX   
{
    /**
    * 
    */
    public DentX() throws Exception {
    }

    //this is the windows function for reading from ini files.
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int getPrivateProfileString(String section, String key, String def, StringBuilder retVal, int size, String filePath) throws Exception ;

    private static String readValue(String fileName, String section, String key) throws Exception {
        StringBuilder strBuild = new StringBuilder(255);
        int i = getPrivateProfileString(section,key,"",strBuild,255,fileName);
        return strBuild.ToString();
    }

    /**
    * Launches the program using the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first");
            return ;
        }
         
        //Get the program path from the ini file
        String windir = Environment.GetEnvironmentVariable("windir");
        // C:\WINDOWS
        String iniFile = windir + "\\dentx.ini";
        if (!File.Exists(iniFile))
        {
            MessageBox.Show("Could not find " + iniFile);
            return ;
        }
         
        //Make sure the program is running
        String proimagePath = readValue(iniFile,"imagemgt","MainFile");
        Process[] proImageInstances = Process.GetProcessesByName("ProImage");
        if (proImageInstances.Length == 0)
        {
            Process.Start(proimagePath);
            Thread.Sleep(TimeSpan.FromSeconds(10));
        }
         
        //command="Xray,PatientNo,FirstName,LastName,Birth Date,Sex,Address,City,State,Code"(zip)
        String command = "Xray,";
        //PatientNo can be any string format up to 9 char
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            command += pat.PatNum.ToString() + ",";
        }
        else
        {
            if (StringSupport.equals(pat.ChartNumber, ""))
            {
                MessageBox.Show("ChartNumber for this patient is blank.");
                return ;
            }
             
            command += pat.ChartNumber.Replace(",", "") + ",";
        } 
        command += pat.FName.Replace(",", "") + "," + pat.LName.Replace(",", "") + "," + pat.Birthdate.ToShortDateString() + ",";
        if (pat.Gender == PatientGender.Female)
            command += "F,";
        else
            command += "M,"; 
        command += pat.Address.Replace(",", "") + "," + pat.City.Replace(",", "") + "," + pat.State.Replace(",", "") + "," + pat.Zip.Replace(",", "");
        try
        {
            //MessageBox.Show(command);
            //Create a context that uses a dedicated thread for DDE message pumping.
            DdeContext context = new DdeContext();
            try
            {
                {
                    //Create a client.
                    DdeClient client = new DdeClient("ProImage", "Image", context);
                    try
                    {
                        {
                            //Establish the conversation.
                            client.Connect();
                            //Start ProImage and open to the Xray Chart screen
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


//timeout 2 secs
//MessageBox.Show(e.Message);