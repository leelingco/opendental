//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.RefAttachL;
import OpenDentBusiness.Family;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
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
public class DrCeph   
{
    /**
    * 
    */
    public DrCeph() throws Exception {
    }

    /**
    * Uses a VB dll to launch.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            MessageBox.Show("Please select a patient first.");
            return ;
        }
         
        //js This was originally added on 9/2/09, probably due to race affecting ceph proportions.  But since we can't find any documentation, and since a customer is complaining, we are removing it for now.  If we add it back, we will document exactly why.
        //if(pat.Race==PatientRace.Unknown) {
        //  MessageBox.Show("Race must be entered first.");
        //  return;
        //}
        //Make sure the program is running
        if (Process.GetProcessesByName("DrCeph").Length == 0)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show("DrCeph","Program path not set properly.");
                return ;
            }

            Thread.Sleep(TimeSpan.FromSeconds(4));
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        String patID = "";
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            patID = pat.PatNum.ToString();
        }
        else
        {
            patID = pat.ChartNumber;
        } 
        try
        {
            List<RefAttach> referalList = RefAttaches.Refresh(pat.PatNum);
            Provider prov = Providers.getProv(Patients.getProvNum(pat));
            String provName = prov.FName + " " + prov.MI + " " + prov.LName + " " + prov.Suffix;
            Family fam = Patients.getFamily(pat.PatNum);
            Patient guar = fam.ListPats[0];
            String relat = "";
            if (guar.PatNum == pat.PatNum)
            {
                relat = "Self";
            }
            else if (guar.Gender == PatientGender.Male && pat.Position == PatientPosition.Child)
            {
                relat = "Father";
            }
            else if (guar.Gender == PatientGender.Female && pat.Position == PatientPosition.Child)
            {
                relat = "Mother";
            }
            else
            {
                relat = "Unknown";
            }   
            VBbridges.DrCephNew.Launch(patID, pat.FName, pat.MiddleI, pat.LName, pat.Address, pat.Address2, pat.City, pat.State, pat.Zip, pat.HmPhone, pat.SSN, pat.Gender.ToString(), pat.Race.ToString(), "", pat.Birthdate.ToString(), DateTime.Today.ToShortDateString(), RefAttachL.GetReferringDr(referalList), provName, guar.getNameFL(), guar.Address, guar.Address2, guar.City, guar.State, guar.Zip, guar.HmPhone, relat);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("DrCeph not responding.  It might not be installed properly.");
        }
    
    }

}


