//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;

/**
* Insurance Answers Plus
*/
public class Iap   
{
    /**
    * 
    */
    public static final int NEXT = 1;
    /**
    * 
    */
    public static final int PREV = 2;
    /**
    * 
    */
    public static final int EXACT = 3;
    /**
    * 
    */
    public static final int SEARCH = 4;
    /**
    * 
    */
    public static final int FIRST = 5;
    /**
    * 
    */
    public static final int NOERROR = 0;
    /**
    * 
    */
    public static final int FILE_NOT_FOUND = 3;
    /**
    * 
    */
    public static final int BAD_VERSION = 4;
    /**
    * 
    */
    public static final int DATA_OK = 0;
    /**
    * 
    */
    public static final int DATA_END = 255;
    /**
    * 
    */
    public static final int KEY_NOT_FOUND = 2;
    /**
    * 
    */
    public static final int Employer = 1;
    /**
    * 
    */
    public static final int Phone = 2;
    /**
    * 
    */
    public static final int InsUnder = 3;
    /**
    * 
    */
    public static final int Carrier = 4;
    /**
    * 
    */
    public static final int CarrierPh = 5;
    /**
    * 
    */
    public static final int Group = 6;
    /**
    * 
    */
    public static final int MailTo = 7;
    /**
    * 
    */
    public static final int MailTo2 = 8;
    //place holder
    /**
    * 
    */
    public static final int MailTo3 = 9;
    /**
    * 
    */
    public static final int EClaims = 10;
    /**
    * 
    */
    public static final int FAXClaims = 11;
    /**
    * 
    */
    public static final int DMOOption = 12;
    /**
    * 
    */
    public static final int Medical = 13;
    /**
    * 
    */
    public static final int GroupNum = 14;
    /**
    * 
    */
    public static final int Phone2 = 15;
    /**
    * 
    */
    public static final int Deductible = 16;
    /**
    * 
    */
    public static final int FamilyDed = 17;
    /**
    * 
    */
    public static final int Maximum = 18;
    /**
    * 
    */
    public static final int BenefitYear = 19;
    /**
    * 
    */
    public static final int DependentAge = 20;
    /**
    * 
    */
    public static final int Preventive = 21;
    /**
    * 
    */
    public static final int Basic = 22;
    /**
    * 
    */
    public static final int Major = 23;
    /**
    * 
    */
    public static final int InitialPlacement = 24;
    /**
    * 
    */
    public static final int ExtractionClause = 25;
    /**
    * 
    */
    public static final int Replacement = 26;
    /**
    * 
    */
    public static final int Other = 27;
    /**
    * 
    */
    public static final int Orthodontics = 28;
    /**
    * 
    */
    public static final int Deductible2 = 29;
    /**
    * 
    */
    public static final int Maximum2 = 30;
    /**
    * 
    */
    public static final int PymtSchedule = 31;
    /**
    * 
    */
    public static final int AgeLimit = 33;
    /**
    * 
    */
    public static final int SignatureonFile = 34;
    /**
    * 
    */
    public static final int StandardADAForm = 35;
    /**
    * 
    */
    public static final int CoordinationRule = 36;
    /**
    * 
    */
    public static final int CoordinationCOB = 37;
    /**
    * 
    */
    public static final int NightguardsforBruxism = 41;
    /**
    * 
    */
    public static final int OcclusalAdjustments = 43;
    /**
    * 
    */
    public static final int XXXXXX = 44;
    /**
    * 
    */
    public static final int TMJNonSurgical = 45;
    /**
    * 
    */
    public static final int Implants = 47;
    /**
    * 
    */
    public static final int InfectionControl = 49;
    /**
    * 
    */
    public static final int Cleanings = 53;
    /**
    * 
    */
    public static final int OralEvaluation = 55;
    /**
    * 
    */
    public static final int Fluoride1200s = 57;
    /**
    * 
    */
    public static final int Code0220 = 60;
    /**
    * 
    */
    public static final int Code0272_0274 = 62;
    /**
    * 
    */
    public static final int Code0210 = 64;
    /**
    * 
    */
    public static final int Code0330 = 66;
    /**
    * 
    */
    public static final int SpaceMaintainers = 68;
    /**
    * 
    */
    public static final int EmergencyExams = 70;
    /**
    * 
    */
    public static final int EmergencyTreatment = 72;
    /**
    * 
    */
    public static final int Sealants1351 = 74;
    /**
    * 
    */
    public static final int Fillings2100 = 77;
    /**
    * 
    */
    public static final int Extractions = 78;
    /**
    * 
    */
    public static final int RootCanals = 79;
    /**
    * 
    */
    public static final int MolarRootCanal = 80;
    /**
    * 
    */
    public static final int OralSurgery = 81;
    /**
    * 
    */
    public static final int ImpactionSoftTissue = 83;
    /**
    * 
    */
    public static final int ImpactionPartialBony = 85;
    /**
    * 
    */
    public static final int ImpactionCompleteBony = 87;
    /**
    * 
    */
    public static final int SurgicalProceduresGeneral = 89;
    /**
    * 
    */
    public static final int PerioSurgicalPerioOsseous = 91;
    /**
    * 
    */
    public static final int SurgicalPerioOther = 93;
    /**
    * 
    */
    public static final int RootPlaning = 96;
    /**
    * 
    */
    public static final int Scaling4345 = 99;
    /**
    * 
    */
    public static final int PerioPx = 101;
    /**
    * 
    */
    public static final int PerioComment = 104;
    /**
    * 
    */
    public static final int IVSedation = 105;
    /**
    * 
    */
    public static final int General9220 = 106;
    /**
    * 
    */
    public static final int Relines5700s = 108;
    /**
    * 
    */
    public static final int StainlessSteelCrowns = 109;
    /**
    * 
    */
    public static final int Crowns2700s = 110;
    /**
    * 
    */
    public static final int Bridges6200 = 111;
    /**
    * 
    */
    public static final int Partials5200s = 112;
    /**
    * 
    */
    public static final int Dentures5100s = 113;
    /**
    * 
    */
    public static final int EmpNumberXXX = 114;
    /**
    * 
    */
    public static final int DateXXX = 115;
    /**
    * 
    */
    public static final int Line4 = 116;
    /**
    * 
    */
    public static final int Note = 117;
    /**
    * 
    */
    public static final int Plan = 118;
    /**
    * 
    */
    public static final int BuildUps = 119;
    /**
    * 
    */
    public static final int PosteriorComposites = 121;
    //<summary></summary>
    // public string dbFile; // Practice-Web AAD / SDK 03/07
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int iAPInitSystem(String UserRegNumber) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int iAPOpenDatabase(String zDataPath) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int iAPReadARecord(RefSupport<int> iReadAction, String zReadType, String zReadKey) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ void iAPCloseDataBase() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ String iAPReturnData(RefSupport<int> DataFldNo) throws Exception ;

    //Declare Function IAPInitSystem Lib "iaplus10.dll" (ByVal UserRegNumber As String) As Long
    //Declare Function IAPOpenDatabase Lib "iaplus10.dll" (ByVal zDataPath As String) As Long
    //Declare Function IAPReadARecord Lib "iaplus10.dll" (iReadAction As Long, ByVal zReadType As String, ByVal zReadKey As String) As Long
    //Declare Sub IAPCloseDataBase Lib "iaplus10.dll" ()
    //Declare Function IAPReturnData Lib "iaplus10.dll" (DataFldNo As Long) As String
    /**
    * Gets a list of up to 40 employers. The items in the list alternate between the insurance plan number and the employer name, starting
    * with the first insurance plan number.
    */
    public static ArrayList getList(String iapNumber) throws Exception {
        ArrayList list = new ArrayList();
        String dbFile = Programs.getProgramPath(Programs.getCur(ProgramName.IAP));
        //@"C:\IAPLUS\";
        int retCode = iAPInitSystem("test");
        //any value
        int result = iAPOpenDatabase(dbFile);
        if (result == FILE_NOT_FOUND)
        {
            MessageBox.Show("File not found: " + dbFile);
            return new ArrayList();
        }
         
        if (result == BAD_VERSION)
        {
            MessageBox.Show("Bad version: " + dbFile);
            return new ArrayList();
        }
         
        int iReadAction = SEARCH;
        //FIRST;
        RefSupport<int> refVar___0 = new RefSupport<int>(iReadAction);
        result = iAPReadARecord(refVar___0,"N",iapNumber);
        iReadAction = refVar___0.getValue();
        if (result == KEY_NOT_FOUND)
        {
            return new ArrayList();
        }
        else
        {
            iReadAction = NEXT;
            int DataFldNo = Employer;
            int DataPlanNo = EmpNumberXXX;
            int loop = 0;
            RefSupport<int> refVar___3 = new RefSupport<int>(iReadAction);
            do
            {
                RefSupport<int> refVar___1 = new RefSupport<int>(DataPlanNo);
                list.Add(iAPReturnData(refVar___1));
                DataPlanNo = refVar___1.getValue();
                RefSupport<int> refVar___2 = new RefSupport<int>(DataFldNo);
                list.Add(iAPReturnData(refVar___2));
                DataFldNo = refVar___2.getValue();
                loop++;
            }
            while (loop < 40 && iAPReadARecord(refVar___3,"N",iapNumber) == DATA_OK);
            iReadAction = refVar___3.getValue();
        } 
        //"N" is for search by iap number.
        iAPCloseDataBase();
        return list;
    }

    /**
    * Surround by try/catch. This should be followed by ReadField calls.  Then, when done, CloseDatabase.
    */
    public static void readRecord(String iapNumber) throws Exception {
        String dbFile = Programs.getCur(ProgramName.IAP).Path;
        //@"C:\IAPlus\";
        iAPInitSystem("test");
        //any value
        int result = iAPOpenDatabase(dbFile);
        if (result == FILE_NOT_FOUND)
        {
            throw new ApplicationException("File not found: " + dbFile);
        }
         
        if (result == BAD_VERSION)
        {
            throw new ApplicationException("Bad version: " + dbFile);
        }
         
        int iReadAction = SEARCH;
        //EXACT;
        RefSupport<int> refVar___4 = new RefSupport<int>(iReadAction);
        result = iAPReadARecord(refVar___4,"N",iapNumber);
        iReadAction = refVar___4.getValue();
        if (result == KEY_NOT_FOUND)
        {
            throw new ApplicationException("Key not found: " + iapNumber);
        }
         
    }

    /**
    * Surround by try/catch. Always call ReadRecord first.  When done with all ReadFields, CloseDatabase.
    */
    public static String readField(int fieldNum) throws Exception {
        RefSupport<int> refVar___5 = new RefSupport<int>(fieldNum);
        resVar___0 = iAPReturnData(refVar___5);
        fieldNum = refVar___5.getValue();
        return resVar___0;
    }

    /**
    * Surround by try/catch. Always call ReadRecord first.  When done with all ReadFields, CloseDatabase.
    */
    public static void closeDatabase() throws Exception {
        iAPCloseDataBase();
    }

}


/*
		Dim dbFile As String
		Dim recordKey As String
		dbFile = "c:\code\clients\iaplus\iaplus10\dmcdata.db"
		IAPInitSystem "test"
		If IAPOpenDatabase(dbFile) Then
				MsgBox "Unable to open database '" + dbFile + "'"
				Exit Sub
		End If
		recordKey = Space$(1000)
		If IAPReadARecord(IAP_FIRST, "N", recordKey) Then
				MsgBox "Error reading first record"
		Else
				Do
						List1.AddItem IAPReturnData(IAP_Employer)
				Loop Until IAPReadARecord(IAP_NEXT, "N", recordKey)
		End If
		IAPCloseDataBase
		*/