//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* Provides bridging functionality to Patterson Imaging.
*/
public class Patterson   
{
    /**
    * Default Constructor
    */
    public Patterson() throws Exception {
    }

    /**
    * Launches Patterson Imaging, logs user in, and opens current patient.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            return ;
        }
         
        String strPathToIni = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"System path to Patterson Imaging ini");
        if (!strPathToIni.ToLower().EndsWith(".ini"))
        {
            //A customer specified an exe path here once, and then the exe file was overwritten.
            MsgBox.show("Patterson","System path to Patterson Imaging ini is invalid in program link setup.");
            return ;
        }
         
        Provider prov = Providers.getProv(pat.PriProv);
        String ssn = Tidy(pat.SSN.ToString(), 9);
        if (StringSupport.equals(ssn.Replace("-", "").Replace("0", "").Trim(), ""))
        {
            ssn = "";
        }
         
        try
        {
            //We do not send if the ssn is all zeros, because Patterson treats ssn like a primary key if present. If more than one patient have the same ssn, then they are treated as the same patient.
            //Tidy(pat.MiddleI,1),//When there is no SSN and the name changes, Patterson creates a whole new patient record, which is troublesome for our customers.
            //Tidy(pat.Preferred,40),//When there is no SSN and the name changes, Patterson creates a whole new patient record, which is troublesome for our customers.
            //This only works with ssn in america with no punctuation
            //uses "M" for male "F" for female and " " for unkown
            //LTidy(pat.PatNum.ToString(),5),//Limit is 5 characters, but that would only be exceeded if they are using random primary keys or they have a lot of data, neither case is common.
            //LTidy(prov.ProvNum.ToString(),3),//Limit is 3 characters, but that would only be exceeded if they are using random primary keys or they have a lot of data, neither case is common.
            VBbridges.Patterson.Launch(tidy(pat.FName,40), "", tidy(pat.LName,40), "", tidy(pat.Address,40), tidy(pat.City,30), tidy(pat.State,2), tidy(pat.Zip,10), ssn, tidy((pat.Gender == PatientGender.Male ? "M" : (pat.Gender == PatientGender.Female ? "F" : " ")),1), Tidy(pat.Birthdate.ToShortDateString(), 11), LTidy(pat.PatNum.ToString(), 5), LTidy(prov.ProvNum.ToString(), 3), tidy(prov.FName,40), tidy(prov.LName,40), path, strPathToIni);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Error launching Patterson Imaging.");
        }
    
    }

    /**
    * Will only return the beginning amount of characters based on maximum value.
    */
    private static String tidy(String str, int maxL) throws Exception {
        str.Trim();
        if (str.Length > maxL)
        {
            str = str.Substring(0, maxL);
        }
         
        return str.Trim();
    }

    /**
    * Will only return the last amount of characters based on maximum value.
    */
    private static String lTidy(String str, int maxL) throws Exception {
        if (str.Length > maxL)
        {
            str = str.Substring(str.Length - maxL);
        }
         
        return str.Trim();
    }

}


/**
* //Appended with whitespace and returns FIRST maxL characters. Accepts null to return string full of spaces.
*/
//private static string Tidy(string str,int maxL) {
//  if(str==null){
//    str="";
//  }
//  str=str.Trim().PadRight(maxL,' ');
//  return str.Substring(0,maxL);
//}
/**
* //PREpended with whitespace and returns LAST maxL characters. Accepts null to return string full of spaces.
*/
//private static string LTidy(string str,int maxL) {
//  if(str==null) {
//    str="";
//  }
//  str=str.Trim().PadLeft(maxL,' ');
//  return str.Substring(str.Length-maxL,maxL);
//}