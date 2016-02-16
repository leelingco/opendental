//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

public class EvaSoft   
{
    public EvaSoft() throws Exception {
    }

    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        if (pat == null)
        {
            MsgBox.show("EvaSoft","You must select a patient first.");
            return ;
        }
         
        Process[] evaSoftInstances = Process.GetProcessesByName("EvaSoft");
        if (evaSoftInstances.Length == 0)
        {
            MsgBox.show("EvaSoft","EvaSoft is not running. EvaSoft must be running before the bridge will work.");
            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        UdpClient udpClient = new UdpClient();
        String udpMessage = "REQUEST01123581321~~~0.1b~~~pmaddpatient~~~";
        //Patient id can be any string format
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            udpMessage += pat.PatNum.ToString();
        }
        else
        {
            udpMessage += pat.ChartNumber.Replace(",", "").Trim();
        } 
        //Remove any commas. Not likely to exist, but just to be safe.
        udpMessage += "," + pat.FName.Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        udpMessage += "," + pat.LName.Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        udpMessage += "," + pat.Birthdate.ToString("MM/dd/yyyy");
        udpMessage += "," + ((pat.Gender == PatientGender.Female) ? "female" : "male");
        udpMessage += "," + (pat.Address + " " + pat.Address2).Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        udpMessage += "," + pat.City.Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        udpMessage += "," + pat.State.Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        udpMessage += "," + pat.Zip.Replace(",", "").Trim();
        //Remove commas from data, because they are the separator.
        byte[] udpMessageBytes = Encoding.ASCII.GetBytes(udpMessage);
        udpClient.Send(udpMessageBytes, udpMessageBytes.Length, "localhost", 35678);
    }

}


