//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
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
import OpenDentBusiness.Providers;

/**
* 
*/
public class Sirona   
{
    private static String iniFile = new String();
    /**
    * 
    */
    public Sirona() throws Exception {
    }

    //this is the windows function for writing to ini files.
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ long writePrivateProfileString(String section, String key, String val, String filePath) throws Exception ;

    //this is the windows function for reading from ini files.
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int getPrivateProfileString(String section, String key, String def, StringBuilder retVal, int size, String filePath) throws Exception ;

    private static String readValue(String section, String key) throws Exception {
        StringBuilder strBuild = new StringBuilder(255);
        int i = getPrivateProfileString(section,key,"",strBuild,255,iniFile);
        return strBuild.ToString();
    }

    /**
    * Sends data for Patient to a mailbox file and launches the program.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        if (pat != null)
        {
            //read file C:\sidexis\sifiledb.ini
            iniFile = Path.GetDirectoryName(path) + "\\sifiledb.ini";
            if (!File.Exists(iniFile))
            {
                MessageBox.Show(iniFile + " could not be found. Is Sidexis installed properly?");
                return ;
            }
             
            //read FromStation0 | File to determine location of comm file (sendBox) (siomin.sdx)
            //example:
            //[FromStation0]
            //File=F:\PDATA\siomin.sdx  //only one sendBox on entire network.
            String sendBox = readValue("FromStation0","File");
            //read Multistations | GetRequest (=1) to determine if station can take xrays.
            //but we don't care at this point, so ignore
            //set OfficeManagement | OffManConnected = 1 to make sidexis ready to accept a message.
            writePrivateProfileString("OfficeManagement","OffManConnected","1",iniFile);
            //line formats: first two bytes are the length of line including first two bytes and \r\n
            //each field is terminated by null (byte 0).
            //Append U token to siomin.sdx file
            StringBuilder line = new StringBuilder();
            FileStream fs = new FileStream(sendBox, FileMode.Append);
            BinaryWriter bw = new BinaryWriter(fs);
            try
            {
                {
                    char nTerm = (char)0;
                    //Convert.ToChar(0);
                    line.Append("U");
                    //U signifies Update patient in sidexis. Gets ignored if new patient.
                    line.Append(nTerm);
                    line.Append(pat.LName);
                    line.Append(nTerm);
                    line.Append(pat.FName);
                    line.Append(nTerm);
                    line.Append(pat.Birthdate.ToString("dd.MM.yyyy"));
                    line.Append(nTerm);
                    //leave initial patient id blank. This updates sidexis to patNums used in Open Dental
                    line.Append(nTerm);
                    line.Append(pat.LName);
                    line.Append(nTerm);
                    line.Append(pat.FName);
                    line.Append(nTerm);
                    line.Append(pat.Birthdate.ToString("dd.MM.yyyy"));
                    line.Append(nTerm);
                    //Patient id:
                    ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
                        ;
                    if (StringSupport.equals(PPCur.PropertyValue, "0"))
                    {
                        line.Append(pat.PatNum.ToString());
                    }
                    else
                    {
                        line.Append(pat.ChartNumber);
                    } 
                    line.Append(nTerm);
                    if (pat.Gender == PatientGender.Female)
                        line.Append("F");
                    else
                        line.Append("M"); 
                    line.Append(nTerm);
                    line.Append(Providers.getAbbr(Patients.getProvNum(pat)));
                    line.Append(nTerm);
                    line.Append("OpenDental");
                    line.Append(nTerm);
                    line.Append("Sidexis");
                    line.Append(nTerm);
                    line.Append("\r\n");
                    bw.Write(IntToByteArray(line.Length + 2));
                    //the 2 accounts for these two chars.
                    bw.Write(strBuildToBytes(line));
                    //Append N token to siomin.sdx file
                    //N signifies create New patient in sidexis. If patient already exists,
                    //then it simply updates any old data.
                    line = new StringBuilder();
                    line.Append("N");
                    line.Append(nTerm);
                    line.Append(pat.LName);
                    line.Append(nTerm);
                    line.Append(pat.FName);
                    line.Append(nTerm);
                    line.Append(pat.Birthdate.ToString("dd.MM.yyyy"));
                    line.Append(nTerm);
                    //Patient id:
                    if (StringSupport.equals(PPCur.PropertyValue, "0"))
                    {
                        line.Append(pat.PatNum.ToString());
                    }
                    else
                    {
                        line.Append(pat.ChartNumber);
                    } 
                    line.Append(nTerm);
                    if (pat.Gender == PatientGender.Female)
                        line.Append("F");
                    else
                        line.Append("M"); 
                    line.Append(nTerm);
                    line.Append(Providers.getAbbr(Patients.getProvNum(pat)));
                    line.Append(nTerm);
                    line.Append("OpenDental");
                    line.Append(nTerm);
                    line.Append("Sidexis");
                    line.Append(nTerm);
                    line.Append("\r\n");
                    bw.Write(IntToByteArray(line.Length + 2));
                    bw.Write(strBuildToBytes(line));
                    //Append A token to siomin.sdx file
                    //A signifies Autoselect patient.
                    line = new StringBuilder();
                    line.Append("A");
                    line.Append(nTerm);
                    line.Append(pat.LName);
                    line.Append(nTerm);
                    line.Append(pat.FName);
                    line.Append(nTerm);
                    line.Append(pat.Birthdate.ToString("dd.MM.yyyy"));
                    line.Append(nTerm);
                    if (StringSupport.equals(PPCur.PropertyValue, "0"))
                    {
                        line.Append(pat.PatNum.ToString());
                    }
                    else
                    {
                        line.Append(pat.ChartNumber);
                    } 
                    line.Append(nTerm);
                    line.Append(SystemInformation.ComputerName);
                    line.Append(nTerm);
                    line.Append(DateTime.Now.ToString("dd.MM.yyyy"));
                    line.Append(nTerm);
                    line.Append(DateTime.Now.ToString("HH.mm.ss"));
                    line.Append(nTerm);
                    line.Append("OpenDental");
                    line.Append(nTerm);
                    line.Append("Sidexis");
                    line.Append(nTerm);
                    line.Append("0");
                    //0=no image selection
                    line.Append(nTerm);
                    line.Append("\r\n");
                    bw.Write(IntToByteArray(line.Length + 2));
                    bw.Write(strBuildToBytes(line));
                }
            }
            finally
            {
                if (bw != null)
                    Disposable.mkDisposable(bw).dispose();
                 
            }
            fs.Close();
        }
         
        try
        {
            //if patient is loaded
            //Start Sidexis.exe whether patient loaded or not.
            Process.Start(path);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(path + " is not available.");
        }
    
    }

    /**
    * this is my way of converting to hex. I don't like their file format at all. Always returns an array of 2 chars.  Used when measuring length of line.
    */
    private static byte[] intToByteArray(int toConvert) throws Exception {
        byte[] retVal = new byte[2];
        retVal[0] = (byte)Math.IEEERemainder(toConvert, 256);
        retVal[1] = (byte)(toConvert / 256);
        return retVal;
    }

    //rounds down automatically
    private static byte[] strBuildToBytes(StringBuilder strBuild) throws Exception {
        byte[] retVal = new byte[strBuild.Length];
        for (int i = 0;i < retVal.Length;i++)
        {
            StringBuilder.INDEXER __dummyScrutVar0 = strBuild[i];
            if (__dummyScrutVar0.equals('\u00C7'))
            {
                //C,
                retVal[i] = 128;
            }
            else if (__dummyScrutVar0.equals('\u00FC'))
            {
                //u"
                retVal[i] = 129;
            }
            else if (__dummyScrutVar0.equals('\u00E9'))
            {
                //e'
                retVal[i] = 130;
            }
            else if (__dummyScrutVar0.equals('\u00E2'))
            {
                //a^
                retVal[i] = 131;
            }
            else if (__dummyScrutVar0.equals('\u00E4'))
            {
                //a"
                retVal[i] = 132;
            }
            else if (__dummyScrutVar0.equals('\u00E0'))
            {
                //a`
                retVal[i] = 133;
            }
            else if (__dummyScrutVar0.equals('\u00E5'))
            {
                //ao
                retVal[i] = 134;
            }
            else if (__dummyScrutVar0.equals('\u00E7'))
            {
                //c,
                retVal[i] = 135;
            }
            else if (__dummyScrutVar0.equals('\u00EA'))
            {
                //e^
                retVal[i] = 136;
            }
            else if (__dummyScrutVar0.equals('\u00EB'))
            {
                //e"
                retVal[i] = 137;
            }
            else if (__dummyScrutVar0.equals('\u00E8'))
            {
                //e`
                retVal[i] = 138;
            }
            else if (__dummyScrutVar0.equals('\u00EF'))
            {
                //i"
                retVal[i] = 139;
            }
            else if (__dummyScrutVar0.equals('\u00EE'))
            {
                //i^
                retVal[i] = 140;
            }
            else if (__dummyScrutVar0.equals('\u00EC'))
            {
                //i`
                retVal[i] = 141;
            }
            else if (__dummyScrutVar0.equals('\u00C4'))
            {
                //A"
                retVal[i] = 142;
            }
            else if (__dummyScrutVar0.equals('\u00C5'))
            {
                //Ao
                retVal[i] = 143;
            }
            else if (__dummyScrutVar0.equals('\u00C9'))
            {
                //E'
                retVal[i] = 144;
            }
            else if (__dummyScrutVar0.equals('\u00E6'))
            {
                //ae
                retVal[i] = 145;
            }
            else if (__dummyScrutVar0.equals('\u00C6'))
            {
                //AE
                retVal[i] = 146;
            }
            else if (__dummyScrutVar0.equals('\u00F4'))
            {
                //o^
                retVal[i] = 147;
            }
            else if (__dummyScrutVar0.equals('\u00F6'))
            {
                //o"
                retVal[i] = 148;
            }
            else if (__dummyScrutVar0.equals('\u00F2'))
            {
                //o`
                retVal[i] = 149;
            }
            else if (__dummyScrutVar0.equals('\u00FB'))
            {
                //u^
                retVal[i] = 150;
            }
            else if (__dummyScrutVar0.equals('\u00F9'))
            {
                //u`
                retVal[i] = 151;
            }
            else if (__dummyScrutVar0.equals('\u00FF'))
            {
                //y"
                retVal[i] = 152;
            }
            else if (__dummyScrutVar0.equals('\u00D6'))
            {
                //O"
                retVal[i] = 153;
            }
            else if (__dummyScrutVar0.equals('\u00DC'))
            {
                //U"
                retVal[i] = 154;
            }
            else //skipped 155 through 159
            if (__dummyScrutVar0.equals('\u00E1'))
            {
                //a'
                retVal[i] = 160;
            }
            else if (__dummyScrutVar0.equals('\u00ED'))
            {
                //i'
                retVal[i] = 161;
            }
            else if (__dummyScrutVar0.equals('\u00F3'))
            {
                //o'
                retVal[i] = 162;
            }
            else if (__dummyScrutVar0.equals('\u00FA'))
            {
                //u'
                retVal[i] = 163;
            }
            else if (__dummyScrutVar0.equals('\u00F1'))
            {
                //n~
                retVal[i] = 164;
            }
            else if (__dummyScrutVar0.equals('\u00D1'))
            {
                //N~
                retVal[i] = 165;
            }
            else
            {
                retVal[i] = (byte)strBuild[i];
            }                                 
        }
        return retVal;
    }

}


