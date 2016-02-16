//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Dxis   
{
    /**
    * 
    */
    public Dxis() throws Exception {
    }

    /**
    * Launches the program using a combination of command line characters and the patient.Cur data.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //usage: C:\Dxis\Dxis.exe /i /t:UniqueID - Practice Name
        //The UniqueID can be a combo of patient name and id.  I think we'll combine Lname,Fname,PatNum
        if (pat == null)
        {
            MsgBox.show("Dxis","Please select a patient first.");
            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        String info = "/i /t:" + pat.LName + " " + pat.FName + " " + pat.PatNum.ToString() + " - " + PrefC.getString(PrefName.PracticeTitle);
        Process process = new Process();
        process.StartInfo = new ProcessStartInfo(path, info);
        try
        {
            process.Start();
            process.WaitForExit();
        }
        catch (Exception __dummyCatchVar0)
        {
            //puts OD in sleep mode because the pano is so resource intensive.
            MessageBox.Show(path + " is not available.");
        }
    
    }

}


/*
		///<summary>Clips the length of the string as well as making sure invalid characters don't slip through.</summary>
		private static string ClipTo(string input,int length){
			string retVal=input.Replace(";","");//get rid of any semicolons.
			if(retVal.Length>length){
				retVal=retVal.Substring(0,length);
			}
			return retVal;
		}*/