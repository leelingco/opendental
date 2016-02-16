//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;

/**
* 
*/
public class DentalStudio   
{
    /**
    * 
    */
    public DentalStudio() throws Exception {
    }

    /**
    * 
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            MsgBox.show("DentalStudio","Please select a patient first.");
            return ;
        }
         
        String str = "";
        //The parameters must be in a specific order.
        //Param1: UserName
        String userName = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"UserName (clear to use OD username)");
        if (StringSupport.equals(userName, ""))
        {
            //Give the customer the option to use OD usernames.
            userName = Security.getCurUser().UserName;
        }
         
        str += tidy(userName) + " ";
        //Param2: UserPassword
        String userPassword = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"UserPassword");
        if (StringSupport.equals(userName, ""))
        {
            //Give the customer the option to use OD usernames.
            userPassword = Security.getCurUser().Password;
        }
         
        str += tidy(userPassword) + " ";
        //Param3: PatientLName
        str += tidy(pat.LName) + " ";
        //Param4: PatientFName
        str += tidy(pat.FName) + " ";
        //Param5: PatientSex
        if (pat.Gender == PatientGender.Female)
        {
            str += tidy("F") + " ";
        }
        else if (pat.Gender == PatientGender.Male)
        {
            str += tidy("M") + " ";
        }
        else
        {
            //unknown
            str += tidy("O") + " ";
        }  
        //O=Other
        //Param6: PatientID
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
        {
            str += Tidy(pat.PatNum.ToString()) + " ";
        }
        else
        {
            str += tidy(pat.ChartNumber) + " ";
        } 
        //Param7: Action
        str += tidy("O") + " ";
        //For future use.  Must always be O for now (not zero).  The O stands for "Open".
        //Param8: PatientBDate
        str += Tidy(pat.Birthdate.ToString("MM/dd/yyyy"));
        try
        {
            //DentalStudio patient matching does not depend on birthdate, so we cand send 01/01/0001. DentalStudio will update the birthdate after updated in OD.
            Process.Start(path, str);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Removes double-quotes, then surrounds with double-quotes.
    */
    private static String tidy(String input) throws Exception {
        String retVal = input.Replace("\"", "");
        //get rid of any double-quotes.
        retVal = "\"" + retVal + "\"";
        return retVal;
    }

}


//surround with double-quotes.