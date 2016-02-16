//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
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
public class MediaDent   
{
    /**
    * 
    */
    public MediaDent() throws Exception {
    }

    /**
    * Launches the program by passing the name of a file with data in it.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //ArrayList ForProgram=ProgramProperties.GetForProgram(ProgramCur.ProgramNum); ;
        String version4or5 = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"MediaDent Version 4 or 5");
        if (StringSupport.equals(version4or5, "4"))
        {
            sendData4(ProgramCur,pat);
            return ;
        }
         
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start Mediadent without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        }
         
        String infoFile = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Text file path");
        try
        {
            StreamWriter sw = new StreamWriter(infoFile, false);
            try
            {
                {
                    String id = "";
                    if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
                    {
                        id = pat.PatNum.ToString();
                    }
                    else
                    {
                        id = pat.ChartNumber;
                    } 
                    sw.WriteLine(pat.LName + ", " + pat.FName + " " + pat.Birthdate.ToShortDateString() + " " + id);
                    sw.WriteLine();
                    sw.WriteLine("PN=" + id);
                    sw.WriteLine("LN=" + pat.LName);
                    sw.WriteLine("FN=" + pat.FName);
                    sw.WriteLine("BD=" + pat.Birthdate.ToString("MM/dd/yyyy"));
                    if (pat.Gender == PatientGender.Female)
                    {
                        sw.WriteLine("SX=F");
                    }
                    else
                    {
                        sw.WriteLine("SX=M");
                    } 
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Unable to write to text file: " + infoFile);
            return ;
        }

        try
        {
            Process.Start(path, "@" + infoFile);
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

    /**
    * Launches the program using command line.
    */
    private static void sendData4(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //Usage: mediadent.exe /P<Patient Name> /D<Practitioner> /L<Language> /F<Image folder> /B<Birthdate>
        //Example: mediadent.exe /PJan Met De Pet /DOtté Gunter /L1 /Fc:\Mediadent\patients\1011 /B27071973
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
            ;
        if (pat == null)
        {
            return ;
        }
         
        String info = "/P" + cleanup(pat.FName + " " + pat.LName);
        Provider prov = Providers.getProv(Patients.getProvNum(pat));
        info += " /D" + prov.FName + " " + prov.LName + " /L1 /F";
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Image Folder");
        info += PPCur.PropertyValue;
        PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            info += pat.PatNum.ToString();
        }
        else
        {
            info += cleanup(pat.ChartNumber);
        } 
        info += " /B" + pat.Birthdate.ToString("ddMMyyyy");
        try
        {
            //MessageBox.Show(info);
            //not used yet: /inputfile "path to file"
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show(path + " " + info + " is not available.");
        }
    
    }

    /**
    * Makes sure invalid characters don't slip through.
    */
    private static String cleanup(String input) throws Exception {
        String retVal = input;
        retVal = retVal.Replace("\"", "");
        //get rid of any quotes
        retVal = retVal.Replace("'", "");
        //get rid of any single quotes
        retVal = retVal.Replace("/", "");
        return retVal;
    }

}


//get rid of any /