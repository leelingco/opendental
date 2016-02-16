//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

/**
* 
*/
public class Digora   
{
    /**
    * 
    */
    public Digora() throws Exception {
    }

    /*string InvalidCommandLine="999";
    		string GeneralError="998";
    		string NoConnect="997";
    		string PatientNotFound="989";
    		string ManyPatientFound="988";
    		string PatientLocked="987";
    		string ImageNotFound="979";
    		string FileError="969";
    		string OpenManyFoundOK="1";
    		string OpenManyFoundCancel="2";
    		string CreatePatientExist="11";
    		string ChangePatientExist="21";
    		string Successful="0";*/
    /**
    * We will use the clipboard interface, although there is an ole automation interface available.
    */
    //Command format: $$DFWIN$$ <Command> <Options>
    //Return value: $$DFWOUT$$<Return value>[\nReturn string] (we will ignore this value for now)
    //$$DFWIN$$ OPEN -n"LName, FName" -c"PatNum" -r -a
    //option -r creates patient if not found, -a changes focus to Digora
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        if (pat == null)
        {
            MessageBox.Show("No patient selected.");
            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        //if(pat!=null){
        String info = "$$DFWIN$$ OPEN -n\"" + tidy(pat.LName) + ", " + tidy(pat.FName) + "\" -c\"";
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += pat.PatNum.ToString();
        }
        else
        {
            info += pat.ChartNumber;
        } 
        info += "\" -r -a";
        Clipboard.SetText(info, TextDataFormat.Text);
    }

    //}
    /*else{
    				try{
    					Process.Start(ProgramCur.Path);//should start Apteryx without bringing up a pt.
    				}
    				catch{
    					MessageBox.Show(ProgramCur.Path+" is not available.");
    				}
    			}*/
    private static String tidy(String str) throws Exception {
        return str.Replace("\"", "");
    }

}


