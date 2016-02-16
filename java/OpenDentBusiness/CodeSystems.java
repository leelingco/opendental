//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.Cdcrec;
import OpenDentBusiness.Cdcrecs;
import OpenDentBusiness.CodeSystem;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Cvx;
import OpenDentBusiness.Cvxs;
import OpenDentBusiness.Db;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Sop;
import OpenDentBusiness.Sops;
import OpenDentBusiness.Ucum;
import OpenDentBusiness.Ucums;

/**
* Import functions in this class should typically be called from a worker thread.
*/
public class CodeSystems   
{
    public static class __MultiProgressArgs   implements OpenDentBusiness.CodeSystems.ProgressArgs
    {
        public void invoke(int numTotal, int numDone) throws Exception {
            IList<OpenDentBusiness.CodeSystems.ProgressArgs> copy = new IList<OpenDentBusiness.CodeSystems.ProgressArgs>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<OpenDentBusiness.CodeSystems.ProgressArgs>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                OpenDentBusiness.CodeSystems.ProgressArgs d = (OpenDentBusiness.CodeSystems.ProgressArgs)__dummyForeachVar0;
                d.invoke(numTotal, numDone);
            }
        }

        private System.Collections.Generic.IList<OpenDentBusiness.CodeSystems.ProgressArgs> _invocationList = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
        public static OpenDentBusiness.CodeSystems.ProgressArgs combine(OpenDentBusiness.CodeSystems.ProgressArgs a, OpenDentBusiness.CodeSystems.ProgressArgs b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiProgressArgs ret = new __MultiProgressArgs();
            /**
            * Returns a list of code systems in the code system table.  This query will change from version to version depending on what code systems we have available.
            */
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static OpenDentBusiness.CodeSystems.ProgressArgs remove(OpenDentBusiness.CodeSystems.ProgressArgs a, OpenDentBusiness.CodeSystems.ProgressArgs b) throws Exception {
            //string command="SELECT * FROM codesystem WHERE CodeSystemName!='AdministrativeSex' AND CodeSystemName!='CDT'";
            //string command="SELECT * FROM codesystem WHERE CodeSystemName IN ('ICD9CM','ICD10','RXNORM','CPT','CVX','UCUM'"+(IsMemberNation?",'SNOMEDCT'":"")+")";
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<OpenDentBusiness.CodeSystems.ProgressArgs> aInvList = a.getInvocationList();
            /**
            * //Returns a list of code systems in the code system table.  This query will change from version to version depending on what code systems we have available.
            */
            //public static List<CodeSystem> GetForCurrentVersionNoSnomed() {
            //	if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
            //		return Meth.GetObject<List<CodeSystem>>(MethodBase.GetCurrentMethod());
            //	}
            //	//string command="SELECT * FROM codesystem WHERE CodeSystemName!='AdministrativeSex' AND CodeSystemName!='CDT'";
            //	string command="SELECT * FROM codesystem WHERE CodeSystemName IN ('ICD9CM','RXNORM','CPT')";
            //	return Crud.CodeSystemCrud.SelectMany(command);
            //}
            /**
            * 
            */
            System.Collections.Generic.IList<OpenDentBusiness.CodeSystems.ProgressArgs> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiProgressArgs ret = new __MultiProgressArgs();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        /**
        * Updates VersionCurrent to the VersionAvail of the codeSystem object passed in. Used by code system importer after successful import.
        */
        public System.Collections.Generic.IList<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface ProgressArgs   
    {
        void invoke(int numTotal, int numDone) throws Exception ;

        System.Collections.Generic.IList<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception ;
    
    }

    public static List<CodeSystem> getForCurrentVersion(boolean IsMemberNation) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CodeSystem>>GetObject(MethodBase.GetCurrentMethod(), IsMemberNation);
        }
         
        String command = "SELECT * FROM codesystem WHERE CodeSystemName NOT IN ('AdministrativeSex','CDT'" + (!IsMemberNation ? ",'SNOMEDCT'" : "") + ")";
        return Crud.CodeSystemCrud.SelectMany(command);
    }

    public static void update(CodeSystem codeSystem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), codeSystem);
            return ;
        }
         
        Crud.CodeSystemCrud.Update(codeSystem);
    }

    public static void updateCurrentVersion(CodeSystem codeSystem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), codeSystem);
            return ;
        }
         
        codeSystem.VersionCur = codeSystem.VersionAvail;
        Crud.CodeSystemCrud.Update(codeSystem);
    }

    /**
    * Called after file is downloaded.  Throws exceptions.
    */
    //public static void ImportAdministrativeSex(string tempFileName) ... not necessary.
    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importCdcrec(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Cdcrecs.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayCDCREC = new String[]();
        Cdcrec cdcrec = new Cdcrec();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayCDCREC = lines[i].Split('\t');
            if (codeHash.Contains(arrayCDCREC[0]))
            {
                continue;
            }
             
            //code already existed
            cdcrec.CdcrecCode = arrayCDCREC[0];
            cdcrec.HeirarchicalCode = arrayCDCREC[1];
            cdcrec.Description = arrayCDCREC[2];
            Cdcrecs.insert(cdcrec);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.
    */
    //public static void ImportCDT(string tempFileName) ... not necessary.
    /**
    * Called after user provides resource file.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importCpt(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Cpts.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayCpt = new String[]();
        boolean isHeader = true;
        Cpt cpt = new Cpt();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            if (isHeader)
            {
                if (!lines[i].Contains("\t"))
                {
                    continue;
                }
                 
                //Copyright info is present at the head of the file.
                isHeader = false;
            }
             
            arrayCpt = lines[i].Split('\t');
            if (codeHash.Contains(arrayCpt[0]))
            {
                continue;
            }
             
            //code already exists
            cpt.CptCode = arrayCpt[0];
            cpt.Description = arrayCpt[1];
            Cpts.insert(cpt);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importCvx(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Cvxs.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayCvx = new String[]();
        Cvx cvx = new Cvx();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayCvx = lines[i].Split('\t');
            if (codeHash.Contains(arrayCvx[0]))
            {
                continue;
            }
             
            //code already exists
            cvx.CvxCode = arrayCvx[0];
            cvx.Description = arrayCvx[1];
            Cvxs.insert(cvx);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importHcpcs(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Hcpcses.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayHCPCS = new String[]();
        Hcpcs hcpcs = new Hcpcs();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayHCPCS = lines[i].Split('\t');
            if (codeHash.Contains(arrayHCPCS[0]))
            {
                continue;
            }
             
            //code already exists
            hcpcs.HcpcsCode = arrayHCPCS[0];
            hcpcs.DescriptionShort = arrayHCPCS[1];
            Hcpcses.insert(hcpcs);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importIcd10(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Icd10s.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayICD10 = new String[]();
        Icd10 icd10 = new Icd10();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayICD10 = lines[i].Split('\t');
            if (codeHash.Contains(arrayICD10[0]))
            {
                continue;
            }
             
            //code already exists
            icd10.Icd10Code = arrayICD10[0];
            icd10.Description = arrayICD10[1];
            icd10.IsCode = arrayICD10[2];
            Icd10s.insert(icd10);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importIcd9(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        //Customers may have an old codeset that has a truncated uppercase description, if so we want to update with new descriptions.
        boolean IsOldDescriptions = ICD9s.isOldDescriptions();
        HashSet<String> codeHash = new HashSet<String>(ICD9s.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayICD9 = new String[]();
        ICD9 icd9 = new ICD9();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayICD9 = lines[i].Split('\t');
            if (codeHash.Contains(arrayICD9[0]))
            {
                //code already exists
                if (!IsOldDescriptions)
                {
                    continue;
                }
                 
                //code exists and has updated description
                String command = "UPDATE icd9 SET description='" + POut.String(arrayICD9[1]) + "' WHERE ICD9Code='" + POut.String(arrayICD9[0]) + "'";
                Db.nonQ(command);
                continue;
            }
             
            //we have updated the description of an existing code.
            icd9.ICD9Code = arrayICD9[0];
            icd9.Description = arrayICD9[1];
            ICD9s.insert(icd9);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importLoinc(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Loincs.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayLoinc = new String[]();
        Loinc loinc = new Loinc();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayLoinc = lines[i].Split('\t');
            if (codeHash.Contains(arrayLoinc[0]))
            {
                continue;
            }
             
            //code already exists
            loinc.LoincCode = arrayLoinc[0];
            loinc.Component = arrayLoinc[1];
            loinc.PropertyObserved = arrayLoinc[2];
            loinc.TimeAspct = arrayLoinc[3];
            loinc.SystemMeasured = arrayLoinc[4];
            loinc.ScaleType = arrayLoinc[5];
            loinc.MethodType = arrayLoinc[6];
            loinc.StatusOfCode = arrayLoinc[7];
            loinc.NameShort = arrayLoinc[8];
            loinc.ClassType = arrayLoinc[9];
            loinc.UnitsRequired = StringSupport.equals(arrayLoinc[10], "Y");
            loinc.OrderObs = arrayLoinc[11];
            loinc.HL7FieldSubfieldID = arrayLoinc[12];
            loinc.ExternalCopyrightNotice = arrayLoinc[13];
            loinc.NameLongCommon = arrayLoinc[14];
            loinc.UnitsUCUM = arrayLoinc[15];
            loinc.RankCommonTests = PIn.Int(arrayLoinc[16]);
            loinc.RankCommonOrders = PIn.Int(arrayLoinc[17]);
            Loincs.insert(loinc);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importRxNorm(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(RxNorms.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayRxNorm = new String[]();
        RxNorm rxNorm = new RxNorm();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayRxNorm = lines[i].Split('\t');
            if (codeHash.Contains(arrayRxNorm[0]))
            {
                continue;
            }
             
            //code already exists
            rxNorm.RxCui = arrayRxNorm[0];
            rxNorm.MmslCode = arrayRxNorm[1];
            rxNorm.Description = arrayRxNorm[2];
            RxNorms.insert(rxNorm);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importSnomed(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Snomeds.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arraySnomed = new String[]();
        Snomed snomed = new Snomed();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arraySnomed = lines[i].Split('\t');
            if (codeHash.Contains(arraySnomed[0]))
            {
                continue;
            }
             
            //code already exists
            snomed.SnomedCode = arraySnomed[0];
            snomed.Description = arraySnomed[1];
            Snomeds.insert(snomed);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importSop(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Sops.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arraySop = new String[]();
        Sop sop = new Sop();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arraySop = lines[i].Split('\t');
            if (codeHash.Contains(arraySop[0]))
            {
                continue;
            }
             
            //code already exists
            sop.SopCode = arraySop[0];
            sop.Description = arraySop[1];
            Sops.insert(sop);
        }
    }

    /**
    * Called after file is downloaded.  Throws exceptions.  It is assumed that this is called from a worker thread.  Progress delegate will be called every 100th iteration to inform thread of current progress. Quit flag can be set at any time in order to quit importing prematurely.
    */
    public static void importUcum(String tempFileName, OpenDentBusiness.CodeSystems.ProgressArgs progress, RefSupport<boolean> quit) throws Exception {
        if (tempFileName == null)
        {
            return ;
        }
         
        HashSet<String> codeHash = new HashSet<String>(Ucums.getAllCodes());
        String[] lines = File.ReadAllLines(tempFileName);
        String[] arrayUcum = new String[]();
        Ucum ucum = new Ucum();
        for (int i = 0;i < lines.Length;i++)
        {
            //each loop should read exactly one line of code. and each line of code should be a unique code
            if (quit.getValue())
            {
                return ;
            }
             
            if (i % 100 == 0)
            {
                progress.invoke(i + 1,lines.Length);
            }
             
            arrayUcum = lines[i].Split('\t');
            if (codeHash.Contains(arrayUcum[0]))
            {
                continue;
            }
             
            //code already exists
            ucum.UcumCode = arrayUcum[0];
            ucum.Description = arrayUcum[1];
            ucum.IsInUse = false;
            Ucums.insert(ucum);
        }
    }

}


/**
* Returns number of codes imported.
*  @param tempFile 
*  @param codeCount Returns number of new codes inserted.
*  @param totalCodes Returns number of total codes found.
*  @return
*/
//		public static void ImportEhrCodes(string tempFile,out int newCodeCount,out int totalCodeCount,out int availableCodeCount){
//			newCodeCount=0;
//			totalCodeCount=0;
//			availableCodeCount=0;
//			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
//				Meth.GetVoid(MethodBase.GetCurrentMethod(),tempFile,newCodeCount,totalCodeCount,availableCodeCount);
//				return;
//			}
//			//UNION ALL to speed up query.  Used to determine what codes to add to DB.
//			string command=@"SELECT CdcrecCode FROM cdcrec
//											UNION ALL
//											SELECT ProcCode FROM procedurecode
//											UNION ALL
//											SELECT CptCode FROM cpt
//											UNION ALL
//											SELECT CvxCode FROM cvx
//											UNION ALL
//											SELECT HcpcsCode FROM hcpcs
//											UNION ALL
//											SELECT Icd10Code FROM icd10
//											UNION ALL
//											SELECT ICD9Code FROM icd9
//											UNION ALL
//											SELECT LoincCode FROM loinc
//											UNION ALL
//											SELECT RxCui FROM rxnorm
//											UNION ALL
//											SELECT SnomedCode FROM snomed
//											UNION ALL
//											SELECT SopCode FROM sop";
//			DataTable T = DataCore.GetTable(command);
//			HashSet<string> allCodeHash=new HashSet<string>();
//			for(int i=0;i<T.Rows.Count;i++) {
//				allCodeHash.Add(T.Rows[i][0].ToString());
//			}
//			HashSet<string> ehrCodeHash=EhrCodes.GetAllCodesHashSet();
//			string[] lines=File.ReadAllLines(tempFile);
//			string[] arrayEHRCode;
//			EhrCode ehrc=new EhrCode();
//			for(int i=0;i<lines.Length;i++) {//each loop should read exactly one line of code. and each line of code should be a unique code
//				arrayEHRCode=lines[i].Split('\t');
//				if(!allCodeHash.Contains(arrayEHRCode[0]) && arrayEHRCode[6]!="AdministrativeSex") {//exception for AdministrativeSex because it is not stored in the DB.
//					continue;//code does not exist in the database in one of the standard code system tables.
//				}
//				if(ehrCodeHash.Contains(arrayEHRCode[4]+arrayEHRCode[2])) {
//					continue;//Code already inserted in ehrCodes table
//				}
//				ehrc.MeasureIds		=arrayEHRCode[0];
//				ehrc.ValueSetName	=arrayEHRCode[1];
//				ehrc.ValueSetOID	=arrayEHRCode[2];
//				ehrc.QDMCategory	=arrayEHRCode[3];
//				ehrc.CodeValue		=arrayEHRCode[4];
//				ehrc.Description	=arrayEHRCode[5];
//				ehrc.CodeSystem		=arrayEHRCode[6];
//				ehrc.CodeSystemOID=arrayEHRCode[7];
//				EhrCodes.Insert(ehrc);
//				newCodeCount++;//return value
//			}
//			totalCodeCount=ehrCodeHash.Count+newCodeCount;//return value
//			availableCodeCount=lines.Length;//return value
//		}
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one CodeSystem from the db.</summary>
		public static CodeSystem GetOne(long codeSystemNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<CodeSystem>(MethodBase.GetCurrentMethod(),codeSystemNum);
			}
			return Crud.CodeSystemCrud.SelectOne(codeSystemNum);
		}
		///<summary></summary>
		public static long Insert(CodeSystem codeSystem){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				codeSystem.CodeSystemNum=Meth.GetLong(MethodBase.GetCurrentMethod(),codeSystem);
				return codeSystem.CodeSystemNum;
			}
			return Crud.CodeSystemCrud.Insert(codeSystem);
		}
		///<summary></summary>
		public static void Delete(long codeSystemNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),codeSystemNum);
				return;
			}
			string command= "DELETE FROM codesystem WHERE CodeSystemNum = "+POut.Long(codeSystemNum);
			Db.NonQ(command);
		}
		*/