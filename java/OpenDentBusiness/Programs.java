//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramC;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Programs   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM program ORDER BY ProgDesc";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Program";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProgramC.setHList(new Hashtable());
        ProgramC.setListt(Crud.ProgramCrud.TableToList(table));
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            if (!ProgramC.getHList().ContainsKey(ProgramC.getListt()[i].ProgName))
            {
                ProgramC.getHList().Add(ProgramC.getListt()[i].ProgName, ProgramC.getListt()[i]);
            }
             
        }
    }

    //The lines below are replaced by the logic above.
    /*
    			ProgramC.HList=new Hashtable();
    			Program prog = new Program();
    			ProgramC.Listt=new List<Program>();
    			for (int i=0;i<table.Rows.Count;i++){
    				prog=new Program();
    				prog.ProgramNum =PIn.Long(table.Rows[i][0].ToString());
    				prog.ProgName   =PIn.String(table.Rows[i][1].ToString());
    				prog.ProgDesc   =PIn.String(table.Rows[i][2].ToString());
    				prog.Enabled    =PIn.Bool(table.Rows[i][3].ToString());
    				prog.Path       =PIn.String(table.Rows[i][4].ToString());
    				prog.CommandLine=PIn.String(table.Rows[i][5].ToString());
    				prog.Note       =PIn.String(table.Rows[i][6].ToString());
    				prog.PluginDllName=PIn.String(table.Rows[i][7].ToString());
    				ProgramC.Listt.Add(prog);
    				if(!ProgramC.HList.ContainsKey(prog.ProgName)) {
    					ProgramC.HList.Add(prog.ProgName,prog);
    				}
    			}*/
    /**
    * 
    */
    public static void update(Program Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ProgramCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(Program Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ProgramNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ProgramNum;
        }
         
        return Crud.ProgramCrud.Insert(Cur);
    }

    /**
    * This can only be called by the user if it is a program link that they created. Included program links cannot be deleted.  If calling this from ClassConversion, must delete any dependent ProgramProperties first.  It will delete ToolButItems for you.
    */
    public static void delete(Program prog) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), prog);
            return ;
        }
         
        String command = "DELETE from toolbutitem WHERE ProgramNum = " + POut.long(prog.ProgramNum);
        Db.nonQ(command);
        command = "DELETE from program WHERE ProgramNum = '" + prog.ProgramNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Returns true if a Program link with the given name or number exists and is enabled.
    */
    public static boolean isEnabled(ProgramName progName) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (ProgramC.getHList() == null)
        {
            Programs.refreshCache();
        }
         
        if (ProgramC.getHList().ContainsKey(progName.ToString()) && ((Program)ProgramC.getHList()[progName.ToString()]).Enabled)
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    public static boolean isEnabled(long programNum) throws Exception {
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramC.getListt()[i].ProgramNum == programNum && ProgramC.getListt()[i].Enabled)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Supply a valid program Name, and this will set Cur to be the corresponding Program object.
    */
    public static Program getCur(ProgramName progName) throws Exception {
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramC.getListt()[i].ProgName == progName.ToString())
            {
                return ProgramC.getListt()[i];
            }
             
        }
        return null;
    }

    //to signify that the program could not be located. (user deleted it in an older version)
    /**
    * Supply a valid program Name.  Will return 0 if not found.
    */
    public static long getProgramNum(ProgramName progName) throws Exception {
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramC.getListt()[i].ProgName == progName.ToString())
            {
                return ProgramC.getListt()[i].ProgramNum;
            }
             
        }
        return 0;
    }

    /**
    * Using eClinicalWorks tight integration.
    */
    public static boolean usingEcwTightMode() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (Programs.isEnabled(ProgramName.eClinicalWorks) && StringSupport.equals(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"eClinicalWorksMode"), "0"))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Using eClinicalWorks full mode.
    */
    public static boolean usingEcwFullMode() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (Programs.isEnabled(ProgramName.eClinicalWorks) && StringSupport.equals(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"eClinicalWorksMode"), "2"))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Returns true if using eCW in tight or full mode.  In these modes, appointments ARE allowed to overlap because we block users from seeing them.
    */
    public static boolean usingEcwTightOrFullMode() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (usingEcwTightMode() || usingEcwFullMode())
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    //No need to check RemotingRole; no call to db.
    public static boolean getUsingOrion() throws Exception {
        return Programs.isEnabled(ProgramName.Orion);
    }

    /**
    * Returns the local override path if available or returns original program path.  Always returns a valid path.
    */
    public static String getProgramPath(Program program) throws Exception {
        //No need to check RemotingRole; no call to db.
        String overridePath = ProgramProperties.getLocalPathOverrideForProgram(program.ProgramNum);
        if (!StringSupport.equals(overridePath, ""))
        {
            return overridePath;
        }
         
        return program.Path;
    }

    /**
    * For each enabled bridge, if the bridge uses a file to transmit patient data to the other software, then we need to remove the files or clear the files when OD is exiting.
    * Required for EHR 2014 module d.7 (as stated by proctor).
    */
    public static void scrubExportedPatientData() throws Exception {
        //List all program links here. If there is nothing to do for that link, then create a comment stating so.
        String path = "";
        //Apixia:
        scrubFileForProperty(ProgramName.Apixia,"System path to Apixia Digital Imaging ini file","",true);
        //C:\Program Files\Digirex\Switch.ini
        //Apteryx: Has no file paths containing outgoing patient data from Open Dental.
        //BioPAK: Has no file paths containing outgoing patient data from Open Dental.
        //CallFire: Has no file paths containing outgoing patient data from Open Dental.
        //Camsight: Has no file paths containing outgoing patient data from Open Dental.
        //CaptureLink: Has no file paths containing outgoing patient data from Open Dental.
        //Cerec: Has no file paths containing outgoing patient data from Open Dental.
        //CliniView: Has no file paths containing outgoing patient data from Open Dental.
        //ClioSoft: Has no file paths containing outgoing patient data from Open Dental.
        //DBSWin:
        scrubFileForProperty(ProgramName.DBSWin,"Text file path","",true);
        //C:\patdata.txt
        //DentalEye: Has no file paths containing outgoing patient data from Open Dental.
        //DentalStudio: Has no file paths containing outgoing patient data from Open Dental.
        //DentForms: Has no file paths containing outgoing patient data from Open Dental.
        //DentX: Has no file paths containing outgoing patient data from Open Dental.
        //Dexis:
        scrubFileForProperty(ProgramName.Dexis,"InfoFile path","",true);
        //InfoFile.txt
        //Digora: Has no file paths containing outgoing patient data from Open Dental.
        //Divvy: Has no file paths containing outgoing patient data from Open Dental.
        //Dolphin:
        scrubFileForProperty(ProgramName.Dolphin,"Filename","",true);
        //C:\Dolphin\Import\Import.txt
        //DrCeph: Has no file paths containing outgoing patient data from Open Dental.
        //Dxis: Has no file paths containing outgoing patient data from Open Dental.
        //EasyNotesPro: Has no file paths containing outgoing patient data from Open Dental.
        //eClinicalWorks: HL7 files are created, but eCW is supposed to consume and delete them.
        //EvaSoft: Has no file paths containing outgoing patient data from Open Dental.
        //EwooEZDent:
        Program program = Programs.getCur(ProgramName.EwooEZDent);
        if (program.Enabled)
        {
            path = Programs.getProgramPath(program);
            if (File.Exists(path))
            {
                String dir = Path.GetDirectoryName(path);
                String linkage = CodeBase.ODFileUtils.combinePaths(dir,"linkage.xml");
                if (File.Exists(linkage))
                {
                    try
                    {
                        File.Delete(linkage);
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                    }
                
                }
                 
            }
             
        }
         
        //Another instance of OD might be closing at the same time, in which case the delete will fail. Could also be a permission issue or a concurrency issue. Ignore.
        //FloridaProbe: Has no file paths containing outgoing patient data from Open Dental.
        //Guru: Has no file paths containing outgoing patient data from Open Dental.
        //HouseCalls:
        scrubFileForProperty(ProgramName.HouseCalls,"Export Path","Appt.txt",true);
        //C:\HouseCalls\Appt.txt
        //IAP: Has no file paths containing outgoing patient data from Open Dental.
        //iCat:
        scrubFileForProperty(ProgramName.iCat,"XML output file path","",true);
        //C:\iCat\Out\pm.xml
        //ImageFX: Has no file paths containing outgoing patient data from Open Dental.
        //Lightyear: Has no file paths containing outgoing patient data from Open Dental.
        //MediaDent:
        scrubFileForProperty(ProgramName.MediaDent,"Text file path","",true);
        //C:\MediadentInfo.txt
        //MiPACS: Has no file paths containing outgoing patient data from Open Dental.
        //Mountainside: Has no file paths containing outgoing patient data from Open Dental.
        //NewCrop: Has no file paths containing outgoing patient data from Open Dental.
        //Orion: Has no file paths containing outgoing patient data from Open Dental.
        //OrthoPlex: Has no file paths containing outgoing patient data from Open Dental.
        //Owandy: Has no file paths containing outgoing patient data from Open Dental.
        //PayConnect: Has no file paths containing outgoing patient data from Open Dental.
        //Patterson:
        scrubFileForProperty(ProgramName.Patterson,"System path to Patterson Imaging ini","",true);
        //C:\Program Files\PDI\Shared files\Imaging.ini
        //PerioPal: Has no file paths containing outgoing patient data from Open Dental.
        //Planmeca: Has no file paths containing outgoing patient data from Open Dental.
        //PracticeWebReports: Has no file paths containing outgoing patient data from Open Dental.
        //Progeny: Has no file paths containing outgoing patient data from Open Dental.
        //PT: Per our website "The files involved get deleted immediately after they are consumed."
        //PTupdate: Per our website "The files involved get deleted immediately after they are consumed."
        //RayMage: Has no file paths containing outgoing patient data from Open Dental.
        //Schick: Has no file paths containing outgoing patient data from Open Dental.
        //Sirona:
        program = Programs.getCur(ProgramName.Sirona);
        if (program.Enabled)
        {
            path = Programs.getProgramPath(program);
            //read file C:\sidexis\sifiledb.ini
            String iniFile = Path.GetDirectoryName(path) + "\\sifiledb.ini";
            if (File.Exists(iniFile))
            {
                String sendBox = readValueFromIni("FromStation0","File",iniFile);
                if (File.Exists(sendBox))
                {
                    File.WriteAllText(sendBox, "");
                }
                 
            }
             
        }
         
        //Clear the sendbox instead of deleting.
        //Sopro: Has no file paths containing outgoing patient data from Open Dental.
        //TigerView:
        scrubFileForProperty(ProgramName.TigerView,"Tiger1.ini path","",false);
        //C:\Program Files\PDI\Shared files\Imaging.ini.  TigerView complains if the file is not present.
        //Trojan: Has no file paths containing outgoing patient data from Open Dental.
        //Trophy: Has no file paths containing outgoing patient data from Open Dental.
        //TrophyEnhanced: Has no file paths containing outgoing patient data from Open Dental.
        //Tscan: Has no file paths containing outgoing patient data from Open Dental.
        //UAppoint: Has no file paths containing outgoing patient data from Open Dental.
        //Vipersoft: Has no file paths containing outgoing patient data from Open Dental.
        //VixWin: Has no file paths containing outgoing patient data from Open Dental.
        //VixWinBase41: Has no file paths containing outgoing patient data from Open Dental.
        //VixWinOld: Has no file paths containing outgoing patient data from Open Dental.
        //Xcharge: Has no file paths containing outgoing patient data from Open Dental.
        scrubFileForProperty(ProgramName.XDR,"InfoFile path","",true);
    }

    //C:\XDRClient\Bin\infofile.txt
    /**
    * Needed for Sirona bridge data scrub in ScrubExportedPatientData().
    */
    //this is the Windows function for reading from ini files.
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int getPrivateProfileStringFromIni(String section, String key, String def, StringBuilder retVal, int size, String filePath) throws Exception ;

    /**
    * Needed for Sirona bridge data scrub in ScrubExportedPatientData().
    */
    private static String readValueFromIni(String section, String key, String iniFile) throws Exception {
        StringBuilder strBuild = new StringBuilder(255);
        int i = getPrivateProfileStringFromIni(section,key,"",strBuild,255,iniFile);
        return strBuild.ToString();
    }

    /**
    * If isRemovable is false, then the file referenced in the program property will be cleared.
    * If isRemovable is true, then the file referenced in the program property will be deleted.
    */
    private static void scrubFileForProperty(ProgramName programName, String strFileProperty, String strFilePropertySuffix, boolean isRemovable) throws Exception {
        Program program = Programs.getCur(programName);
        if (!program.Enabled)
        {
            return ;
        }
         
        String strFileToScrub = CodeBase.ODFileUtils.combinePaths(ProgramProperties.getPropVal(program.ProgramNum,strFileProperty),strFilePropertySuffix);
        if (!File.Exists(strFileToScrub))
        {
            return ;
        }
         
        try
        {
            File.WriteAllText(strFileToScrub, "");
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        //Always clear the file contents, in case deleting fails below.
        //Another instance of OD might be closing at the same time, in which case the delete will fail. Could also be a permission issue or a concurrency issue. Ignore.
        if (!isRemovable)
        {
            return ;
        }
         
        try
        {
            File.Delete(strFileToScrub);
        }
        catch (Exception __dummyCatchVar2)
        {
        }
    
    }

}


//Another instance of OD might be closing at the same time, in which case the delete will fail. Could also be a permission issue or a concurrency issue. Ignore.