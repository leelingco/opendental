//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.Bridges.FormTrophyNamePick;
import OpenDental.Bridges.TrophyFolder;
import OpenDental.PIn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class TrophyEnhanced   
{
    /**
    * 
    */
    public TrophyEnhanced() throws Exception {
    }

    /**
    * Launches the program using command line.  It is confirmed that there is no space after the -P or -N
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //ArrayList ForProgram=ProgramProperties.GetForProgram(ProgramCur.ProgramNum);;
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //should start Trophy without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        String storagePath = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Storage Path");
        if (!Directory.Exists(storagePath))
        {
            MessageBox.Show("Invalid storage path: " + storagePath);
            return ;
        }
         
        String patFolder = "";
        if (StringSupport.equals(pat.TrophyFolder, ""))
        {
            //no trophy folder assigned yet
            boolean isNumberedMode = StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 1 to enable Numbered Mode"), "1");
            try
            {
                if (isNumberedMode)
                {
                    patFolder = automaticallyGetTrophyFolderNumbered(pat,storagePath);
                }
                else
                {
                    patFolder = automaticallyGetTrophyFolder(pat,storagePath);
                } 
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }

            if (StringSupport.equals(patFolder, ""))
            {
                return ;
            }
             
            //exit without displaying any further message.
            patFolder = CodeBase.ODFileUtils.combinePaths(storagePath,patFolder);
        }
        else
        {
            //pat.TrophyFolder was already previously entered.
            patFolder = CodeBase.ODFileUtils.combinePaths(storagePath,pat.TrophyFolder);
        } 
        //can't do this because the folder won't exist yet for new patients.
        //if(!Directory.Exists(patFolder)) {
        //	MessageBox.Show("Invalid patient folder: "+patFolder);
        //	return;
        //}
        String comline = "-P" + patFolder + " -N" + tidy(pat.LName) + "," + tidy(pat.FName);
        try
        {
            //MessageBox.Show(comline);
            Process.Start(path, comline);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

    /**
    * Guaranteed to always return a valid foldername unless major error or user chooses to exit.  This also saves the TrophyFolder value to this patient in the db and creates the new folder.
    */
    private static String automaticallyGetTrophyFolderNumbered(Patient pat, String trophyPath) throws Exception {
        //if this a patient with existing images in a trophy folder, find that folder
        //Different Trophy might? be organized differently.
        //But our logic will only cover the one situation that we are aware of.
        //In numbered mode, the folder numbering scheme is [trophyImageDir]\XX\PatNum.  The two digits XX are obtained by retrieving the 5th and 6th (indexes 4 and 5) digits of the patient's PatNum, left padded with 0's to ensure the PatNum is at least 6 digits long.  Examples: PatNum=103, left pad to 000103, substring to 03, patient's folder location is [trophyDirectory]\03\103.  PatNum=1003457, no need to left pad, substring to 45, pat folder is [trophyDirectory]\45\1003457.
        String retVal = CodeBase.ODFileUtils.CombinePaths(pat.PatNum.ToString().PadLeft(6, '0').Substring(4, 2), pat.PatNum.ToString());
        //this is our default return value
        String fullPath = CodeBase.ODFileUtils.combinePaths(trophyPath,retVal);
        if (!Directory.Exists(fullPath))
        {
            try
            {
                Directory.CreateDirectory(fullPath);
            }
            catch (Exception __dummyCatchVar2)
            {
                throw new Exception("Error.  Could not create folder: " + fullPath);
            }
        
        }
         
        //folder either existed before we got here, or we successfully created it
        Patient PatOld = pat.copy();
        pat.TrophyFolder = retVal;
        Patients.update(pat,PatOld);
        return retVal;
    }

    /**
    * Guaranteed to always return a valid foldername unless major error or user chooses to exit.  This also saves the TrophyFolder value to this patient in the db.
    */
    private static String automaticallyGetTrophyFolder(Patient pat, String storagePath) throws Exception {
        String retVal = "";
        //try to find the correct trophy folder
        String rvgPortion = pat.LName.Substring(0, 1) + ".rvg";
        String alphaPath = CodeBase.ODFileUtils.combinePaths(storagePath,rvgPortion);
        if (!Directory.Exists(alphaPath))
        {
            throw new ApplicationException("Could not find expected path: " + alphaPath + ".  The enhanced Trophy bridge assumes that folders already exist with that naming convention.");
        }
         
        DirectoryInfo dirInfo = new DirectoryInfo(alphaPath);
        DirectoryInfo[] dirArray = dirInfo.GetDirectories();
        List<TrophyFolder> listMatchesNot = new List<TrophyFolder>();
        //list of all patients found, all with same first letter of last name.
        List<TrophyFolder> listMatchesName = new List<TrophyFolder>();
        //list of all perfect matches for name but not birthdate.
        TrophyFolder folder;
        String maxFolderName = "";
        String datafilePath = new String();
        String[] datafileLines = new String[]();
        String date = new String();
        for (int i = 0;i < dirArray.Length;i++)
        {
            //loop through each folder.
            if (String.Compare(dirArray[i].Name, maxFolderName) > 0)
            {
                //eg, if G0000035 > G0000024
                maxFolderName = dirArray[i].Name;
            }
             
            datafilePath = CodeBase.ODFileUtils.CombinePaths(dirArray[i].FullName, "FILEDATA.txt");
            if (!File.Exists(datafilePath))
            {
                continue;
            }
             
            //fail silently.
            //if this folder is already in use by some other patient, then skip
            if (Patients.IsTrophyFolderInUse(dirArray[i].Name))
            {
                continue;
            }
             
            folder = new TrophyFolder();
            folder.FolderName = dirArray[i].Name;
            datafileLines = File.ReadAllLines(datafilePath);
            if (datafileLines.Length < 2)
            {
                continue;
            }
             
            folder.FName = GetValueFromLines("PRENOM", datafileLines);
            folder.LName = GetValueFromLines("NOM", datafileLines);
            date = GetValueFromLines("DATE", datafileLines);
            try
            {
                folder.BirthDate = DateTime.ParseExact(date, "yyyyMMdd", CultureInfo.CurrentCulture.DateTimeFormat);
            }
            catch (Exception __dummyCatchVar3)
            {
            }

            if (pat.LName.ToUpper() == folder.LName.ToUpper() && pat.FName.ToUpper() == folder.FName.ToUpper())
            {
                if (pat.Birthdate == folder.BirthDate)
                {
                    //We found a perfect match here, so do not display any dialog to user.
                    retVal = rvgPortion + "\\" + dirArray[i].Name;
                }
                else
                {
                    //name is perfect match, but not birthdate.  Maybe birthdate was not entered in one system or the other.
                    listMatchesName.Add(folder);
                } 
            }
             
            listMatchesNot.Add(folder);
        }
        if (StringSupport.equals(retVal, ""))
        {
            //perfect match not found
            if (listMatchesName.Count == 1)
            {
                //exactly one name matched even though birthdays did not
                retVal = rvgPortion + "\\" + listMatchesName[0].FolderName;
            }
            else
            {
                //no or multiple matches
                FormTrophyNamePick formPick = new FormTrophyNamePick();
                formPick.ListMatches = listMatchesNot;
                formPick.ShowDialog();
                if (formPick.DialogResult != DialogResult.OK)
                {
                    return "";
                }
                 
                //triggers total exit
                if (StringSupport.equals(formPick.PickedName, ""))
                {
                    //Need to generate new folder name
                    int maxInt = 0;
                    if (!StringSupport.equals(maxFolderName, ""))
                    {
                        maxInt = PIn.Int(maxFolderName.Substring(1));
                    }
                     
                    //It will crash here if can't parse the int.
                    maxInt++;
                    String paddedInt = maxInt.ToString().PadLeft(7, '0');
                    retVal = rvgPortion + "\\" + pat.LName.Substring(0, 1).ToUpper() + paddedInt;
                }
                else
                {
                    retVal = rvgPortion + "\\" + formPick.PickedName;
                } 
            } 
        }
         
        Patient patOld = pat.copy();
        pat.TrophyFolder = retVal;
        Patients.update(pat,patOld);
        return retVal;
    }

    private static String getValueFromLines(String keyName, String[] lines) throws Exception {
        for (int i = 0;i < lines.Length;i++)
        {
            if (lines[i].StartsWith(keyName))
            {
                int equalsPos = lines[i].IndexOf('=');
                if (equalsPos == -1)
                {
                    return "";
                }
                 
                if (lines[i].Length <= equalsPos + 1)
                {
                    return "";
                }
                 
                //eg, L=4, equalsPos=3 (last char). Nothing comes after =.
                String retVal = lines[i].Substring(equalsPos + 1);
                retVal = retVal.TrimStart(' ');
                return retVal;
            }
             
        }
        return "";
    }

    private static String tidy(String str) throws Exception {
        String retVal = str.Replace("\"", "");
        //gets rid of any quotes
        retVal = retVal.Replace("'", "");
        return retVal;
    }

}


//gets rid of any single quotes