//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class TigerView   
{
    private static String iniFile = new String();
    public TigerView() throws Exception {
    }

    /**
    * A equivalant WriteProfile.. method that should work in linux.
    * Hashtable used to hold key and values.  Look for key and change middle section to key=value.
    * This also puts in a number of checks to prevent crashing.  Returns false if ther was an error creating the ini file.
    * This also does all of the writing at one time reducing the risk of having file write problems.  The previous
    * method wrote each key separately.  Downside is  ini file has to meet exactly what we are looking for
    * keys need to have same text Case.  Section header has to have same case as well.  I really don't think
    * this will be a problem.
    */
    private static boolean writePrivatePofileString2(String section, System.Collections.Hashtable keyVals, String filePath) throws Exception {
        boolean rVal = true;
        System.IO.StreamReader sr = null;
        System.IO.StreamWriter sw = null;
        System.Collections.Generic.List<String> Before_Section_Collection = new System.Collections.Generic.List<String>();
        System.Collections.Generic.List<String> Section_Collection = new System.Collections.Generic.List<String>();
        System.Collections.Generic.List<String> After_Section_Collection = new System.Collections.Generic.List<String>();
        System.Text.RegularExpressions.Regex r = new System.Text.RegularExpressions.Regex("\\[\\s*.+\\s*\\]");
        System.Text.RegularExpressions.Regex regSection = new System.Text.RegularExpressions.Regex("\\[\\s*" + section + "\\s*\\]");
        /**
        * Create a backup file name
        */
        String backupFileName = filePath + ".bak0001";
        if (System.IO.File.Exists(backupFileName))
            for (int i = 2;i < 2000;i++)
            {
                String numString = String.Format("{0:0000}", i);
                //0001, 0002 ect
                backupFileName = filePath + ".bak" + numString;
                if (!System.IO.File.Exists(backupFileName))
                {
                    break;
                }
                 
            }
         
        try
        {
            //Create the backup file
            System.IO.File.Copy(filePath, backupFileName);
            sr = new StreamReader(filePath);
            String line1 = new String();
            boolean SectionFound = false;
            boolean BeforeSection = true;
            boolean AfterSection = false;
            while ((line1 = sr.ReadLine()) != null)
            {
                // Locate Section
                System.Text.RegularExpressions.Match m1 = regSection.Match(line1);
                if (m1.Success)
                {
                    SectionFound = true;
                    BeforeSection = false;
                }
                 
                if (SectionFound && !AfterSection)
                {
                    if (Section_Collection.Count != 0 && r.Match(line1).Success)
                    {
                        AfterSection = true;
                    }
                     
                }
                 
                //Record line in collections
                if (BeforeSection)
                {
                    Before_Section_Collection.Add(line1);
                }
                 
                if (SectionFound && !AfterSection)
                {
                    Section_Collection.Add(line1);
                }
                 
                if (AfterSection)
                {
                    After_Section_Collection.Add(line1);
                }
                 
            }
            sr.Close();
            //need to make sure file is not open so we can rewrite it
            sr.Dispose();
            sr = null;
            for (Object __dummyForeachVar0 : keyVals)
            {
                //Now have the three sections of lines
                //Let us now change the entries in the section in question
                DictionaryEntry de = (DictionaryEntry)__dummyForeachVar0;
                for (int i = 0;i < Section_Collection.Count;i++)
                {
                    if (Section_Collection[i].Contains(de.Key.ToString() + "="))
                    {
                        Section_Collection[i] = de.Key.ToString() + "=" + de.Value.ToString();
                        break;
                    }
                     
                }
            }
            //-- rewrite files
            sw = new StreamWriter(filePath);
            for (int i = 0;i < Before_Section_Collection.Count;i++)
            {
                sw.WriteLine(Before_Section_Collection[i]);
            }
            for (int i = 0;i < Section_Collection.Count;i++)
            {
                sw.WriteLine(Section_Collection[i]);
            }
            for (int i = 0;i < After_Section_Collection.Count;i++)
            {
                sw.WriteLine(After_Section_Collection[i]);
            }
            sw.Close();
            //All is good remove the backup file
            if (System.IO.File.Exists(backupFileName))
            {
                System.IO.File.Delete(backupFileName);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            if (System.IO.File.Exists(backupFileName))
            {
                System.IO.File.Copy(backupFileName, filePath);
            }
             
            // restore backup
            MessageBox.Show("There was an error writing to file: " + filePath + "\nThe operation you have just tried to do will likely fail");
            rVal = false;
        }
        finally
        {
            if (sr != null)
            {
                sr.Close();
            }
             
            if (sw != null)
            {
                sw.Close();
            }
             
        }
        return rVal;
    }

    /**
    * This is the new version of SendData that should be linux compliant.
    * Advoids the kernal32.WritePrivateProfileString call
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Tiger1.ini path");
        iniFile = PPCur.PropertyValue;
        System.Collections.Hashtable htKeyVals = new Hashtable();
        if (pat != null)
        {
            if (!File.Exists(iniFile))
            {
                MessageBox.Show("Could not find " + iniFile);
                return ;
            }
             
            htKeyVals["LastName"] = pat.LName;
            htKeyVals["FirstName"] = pat.FName;
            //Patient Id can be any string format.
            PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            if (StringSupport.equals(PPCur.PropertyValue, "0"))
            {
                htKeyVals["PatientID"] = pat.PatNum.ToString();
            }
            else
            {
                htKeyVals["PatientID"] = pat.ChartNumber;
            } 
            htKeyVals["PatientSSN"] = pat.SSN;
            //WriteValue("SubscriberSSN",pat);
            if (pat.Gender == PatientGender.Female)
            {
                htKeyVals["Gender"] = "Female";
            }
            else
            {
                htKeyVals["Gender"] = "Male";
            } 
            htKeyVals["DOB"] = pat.Birthdate.ToString("MM/dd/yy");
            //WriteValue("ClaimID",pat);??huh
            htKeyVals["AddrStreetNo"] = pat.Address;
            //WriteValue("AddrStreetName",pat);??
            //WriteValue("AddrSuiteNo",pat);??
            htKeyVals["AddrCity"] = pat.City;
            htKeyVals["AddrState"] = pat.State;
            htKeyVals["AddrZip"] = pat.Zip;
            htKeyVals["PhHome"] = limitLength(pat.HmPhone,13);
            htKeyVals["PhWork"] = limitLength(pat.WkPhone,13);
            if (!writePrivatePofileString2("Slave",htKeyVals,iniFile))
            {
                MessageBox.Show(Lan.g(null,"Unable to start external program: ") + path);
            }
            else
            {
                try
                {
                    Process.Start(path, ProgramCur.CommandLine);
                }
                catch (Exception __dummyCatchVar1)
                {
                    MessageBox.Show(path + " is not available.");
                }
            
            } 
        }
        else
        {
            try
            {
                //if patient is loaded
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar2)
            {
                //should start TigerView without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

    private static String limitLength(String str, int length) throws Exception {
        if (str.Length < length + 1)
        {
            return str;
        }
         
        return str.Substring(0, length);
    }

}


