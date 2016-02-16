//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* 
*/
public class Vipersoft   
{
    /**
    * 
    */
    public Vipersoft() throws Exception {
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
         
        if (!File.Exists(path))
        {
            MessageBox.Show("Could not find " + path);
            return ;
        }
         
        //Make sure the program is running
        //Documentation says to include the -nostartup command line switch (to avoid optional program preference startup command).
        if (Process.GetProcessesByName("Vipersoft").Length == 0)
        {
            Process.Start(path, "-nostartup");
            Thread.Sleep(TimeSpan.FromSeconds(4));
        }
         
        //Data is sent to the Vipersoft DDE Server by use of the XTYP_EXECUTE DDE message only.
        //The format ot the XTYP_EXECUTE DDE message is"
        //command="\004hwnd|name|ID|Lastname|Firstname|MI|Comments|Provider|Provider Phone|Addrs1|Addrs2|City|State|Zip|Patient Phone|Practice Name|Patient SSN|restore server|"
        //\004 is one byte code for version 4. 0x04 or Char(4)
        //hwnd is calling software's windows handle.
        //name is for name of calling software (Open Dental)
        //ID is patient ID.  Required and must be unique.
        //Provider field is for provider name.
        //hwnd, ID, Lastname, Firstname, and Provider fields are required.  All other fields are optional.
        //All vertical bars (|) are required, including the ending bar.
        //The restore server flag is for a future release's support of the specialized capture/view commands (default is '1')
        //Visual Basic pseudo code:
        //Chan = DDEInitiate("Vipersoft", "Advanced IntraOral")
        //DDE_String$ = "" //etc
        //DDEExecute Chan, DDE_String$ //send XTYP_EXECUTE DDE command:
        //DDETerminate Chan
        Char char4 = Convert.ToChar(4);
        String command = char4.ToString();
        //tested to make sure this is just one non-printable byte.
        IntPtr hwnd = Application.OpenForms[0].Handle;
        command += hwnd.ToString() + "|" + "OpenDental|";
        //hwnd
        //name
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
        command += patID + "|";
        //ID
        command += pat.LName + "|";
        //Lastname
        command += pat.FName + "|";
        //Firstname
        command += pat.MiddleI + "|";
        //
        command += "|";
        //Comments: blank
        Provider prov = Providers.getProv(Patients.getProvNum(pat));
        command += prov.LName + ", " + prov.FName + "|";
        //Provider
        command += "|";
        //Provider phone
        command += "|";
        //Addr
        command += "|";
        //Addr2
        command += "|";
        //City
        command += "|";
        //State
        command += "|";
        //Zip
        command += "|";
        //Phone
        command += "|";
        //Practice
        command += pat.SSN + "|";
        //SSN
        command += "1|";
        try
        {
            //Restore Server
            //MessageBox.Show(command);
            //Create a context that uses a dedicated thread for DDE message pumping.
            DdeContext context = new DdeContext();
            try
            {
                {
                    //Create a client.
                    DdeClient client = new DdeClient("Vipersoft", "Advanced IntraOral", context);
                    try
                    {
                        {
                            //Establish the conversation.
                            client.Connect();
                            //Select patient
                            client.Execute(command, 2000);
                            //timeout 2 secs
                            client.Disconnect();
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