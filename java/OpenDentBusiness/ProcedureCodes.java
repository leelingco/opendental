//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SubstitutionCondition;
import OpenDentBusiness.Tooth;

/**
* 
*/
public class ProcedureCodes   
{
    public static final String GroupProcCode = "~GRP~";
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM procedurecode ORDER BY ProcCat,ProcCode";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "ProcedureCode";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProcedureCodeC.setListt(Crud.ProcedureCodeCrud.TableToList(table));
        ProcedureCodeC.setHList(new Hashtable());
        for (int i = 0;i < ProcedureCodeC.getListt().Count;i++)
        {
            try
            {
                ProcedureCodeC.getHList().Add(ProcedureCodeC.getListt()[i].ProcCode, ProcedureCodeC.getListt()[i].Copy());
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
    }

    public static List<ProcedureCode> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcedureCode>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM procedurecode WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.ProcedureCodeCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(ProcedureCode code) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            code.CodeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), code);
            return code.CodeNum;
        }
         
        return Crud.ProcedureCodeCrud.Insert(code);
    }

    //must have already checked procCode for nonduplicate.
    /**
    * 
    */
    public static void update(ProcedureCode code) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), code);
            return ;
        }
         
        Crud.ProcedureCodeCrud.Update(code);
    }

    /**
    * Returns the ProcedureCode for the supplied procCode such as such as D####.
    */
    public static ProcedureCode getProcCode(String myCode) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (myCode == null)
        {
            return new ProcedureCode();
        }
         
        //MessageBox.Show(Lans.g("ProcCodes","Error. Invalid procedure code."));
        if (ProcedureCodeC.getHList().Contains(myCode))
        {
            return (ProcedureCode)ProcedureCodeC.getHList()[myCode];
        }
        else
        {
            return new ProcedureCode();
        } 
    }

    /**
    * The new way of getting a procCode. Uses the primary key instead of string code.
    */
    public static ProcedureCode getProcCode(long codeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (codeNum == 0)
        {
            return new ProcedureCode();
        }
         
        for (int i = 0;i < ProcedureCodeC.getListt().Count;i++)
        {
            //MessageBox.Show(Lans.g("ProcCodes","Error. Invalid procedure code."));
            if (ProcedureCodeC.getListt()[i].CodeNum == codeNum)
            {
                return ProcedureCodeC.getListt()[i];
            }
             
        }
        return new ProcedureCode();
    }

    /**
    * Gets code from db to avoid having to constantly refresh in FormProcCodes
    */
    public static ProcedureCode getProcCodeFromDb(long codeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ProcedureCode>GetObject(MethodBase.GetCurrentMethod(), codeNum);
        }
         
        ProcedureCode retval = Crud.ProcedureCodeCrud.SelectOne(codeNum);
        if (retval == null)
        {
            return new ProcedureCode();
        }
         
        return retval;
    }

    //We clasically return an empty procedurecode object here instead of null.
    /**
    * Supply the human readable proc code such as D####
    */
    public static long getCodeNum(String myCode) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (myCode == null || StringSupport.equals(myCode, ""))
        {
            return 0;
        }
         
        if (ProcedureCodeC.getHList().Contains(myCode))
        {
            return ((ProcedureCode)ProcedureCodeC.getHList()[myCode]).CodeNum;
        }
         
        return 0;
    }

    //else {
    //	throw new ApplicationException("Missing code");
    //}
    /**
    * If a substitute exists for the given proc code, then it will give the CodeNum of that code.  Otherwise, it will return the codeNum for the given procCode.
    */
    public static long getSubstituteCodeNum(String procCode, String toothNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (procCode == null || StringSupport.equals(procCode, ""))
        {
            return 0;
        }
         
        if (!ProcedureCodeC.getHList().Contains(procCode))
        {
            return 0;
        }
         
        ProcedureCode proc = (ProcedureCode)ProcedureCodeC.getHList()[procCode];
        if (!StringSupport.equals(proc.SubstitutionCode, "") && ProcedureCodeC.getHList().Contains(proc.SubstitutionCode))
        {
            if (proc.SubstOnlyIf == SubstitutionCondition.Always)
            {
                return ((ProcedureCode)ProcedureCodeC.getHList()[proc.SubstitutionCode]).CodeNum;
            }
             
            if (proc.SubstOnlyIf == SubstitutionCondition.Molar && Tooth.isMolar(toothNum))
            {
                return ((ProcedureCode)ProcedureCodeC.getHList()[proc.SubstitutionCode]).CodeNum;
            }
             
            if (proc.SubstOnlyIf == SubstitutionCondition.SecondMolar && Tooth.isSecondMolar(toothNum))
            {
                return ((ProcedureCode)ProcedureCodeC.getHList()[proc.SubstitutionCode]).CodeNum;
            }
             
        }
         
        return proc.CodeNum;
    }

    public static String getStringProcCode(long codeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (codeNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < ProcedureCodeC.getListt().Count;i++)
        {
            //throw new ApplicationException("CodeNum cannot be zero.");
            if (ProcedureCodeC.getListt()[i].CodeNum == codeNum)
            {
                return ProcedureCodeC.getListt()[i].ProcCode;
            }
             
        }
        throw new ApplicationException("Missing codenum");
    }

    /**
    * 
    */
    public static boolean isValidCode(String myCode) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (myCode == null || StringSupport.equals(myCode, ""))
        {
            return false;
        }
         
        if (ProcedureCodeC.getHList().Contains(myCode))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    /**
    * Grouped by Category.  Used only in FormRpProcCodes.
    */
    public static ProcedureCode[] getProcList() throws Exception {
        //No need to check RemotingRole; no call to db.
        List<ProcedureCode> retVal = new List<ProcedureCode>();
        for (int j = 0;j < DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()].Length;j++)
        {
            for (int k = 0;k < ProcedureCodeC.getListt().Count;k++)
            {
                if (DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()][j].DefNum == ProcedureCodeC.getListt()[k].ProcCat)
                {
                    retVal.Add(ProcedureCodeC.getListt()[k].Copy());
                }
                 
            }
        }
        return retVal.ToArray();
    }

    /**
    * Gets a list of procedure codes directly from the database.  If categories.length==0, then we will get for all categories.  Categories are defnums.  FeeScheds are, for now, defnums.
    */
    public static DataTable getProcTable(String abbr, String desc, String code, List<long> categories, long feeSched, long feeSchedComp1, long feeSchedComp2) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), abbr, desc, code, categories, feeSched, feeSchedComp1, feeSchedComp2);
        }
         
        String whereCat = new String();
        if (categories.Count == 0)
        {
            whereCat = "1";
        }
        else
        {
            whereCat = "(";
            for (int i = 0;i < categories.Count;i++)
            {
                if (i > 0)
                {
                    whereCat += " OR ";
                }
                 
                whereCat += "ProcCat=" + POut.Long(categories[i]);
            }
            whereCat += ")";
        } 
        //Query changed to be compatible with both MySQL and Oracle (not tested).
        String command = "SELECT ProcCat,Descript,AbbrDesc,procedurecode.ProcCode," + "CASE WHEN (fee1.Amount IS NULL) THEN -1 ELSE fee1.Amount END FeeAmt1," + "CASE WHEN (fee2.Amount IS NULL) THEN -1 ELSE fee2.Amount END FeeAmt2," + "CASE WHEN (fee3.Amount IS NULL) THEN -1 ELSE fee3.Amount END FeeAmt3, " + "procedurecode.CodeNum " + "FROM procedurecode " + "LEFT JOIN fee fee1 ON fee1.CodeNum=procedurecode.CodeNum AND fee1.FeeSched=" + POut.long(feeSched) + " " + "LEFT JOIN fee fee2 ON fee2.CodeNum=procedurecode.CodeNum AND fee2.FeeSched=" + POut.long(feeSchedComp1) + " " + "LEFT JOIN fee fee3 ON fee3.CodeNum=procedurecode.CodeNum AND fee3.FeeSched=" + POut.long(feeSchedComp2) + " " + "LEFT JOIN definition ON definition.DefNum=procedurecode.ProcCat " + "WHERE " + whereCat + " AND Descript LIKE '%" + POut.string(desc) + "%' " + "AND AbbrDesc LIKE '%" + POut.string(abbr) + "%' " + "AND procedurecode.ProcCode LIKE '%" + POut.string(code) + "%' " + "ORDER BY definition.ItemOrder,procedurecode.ProcCode";
        return Db.getTable(command);
    }

    /**
    * Returns the LaymanTerm for the supplied codeNum, or the description if none present.
    */
    public static String getLaymanTerm(long codeNum) throws Exception {
        for (int i = 0;i < ProcedureCodeC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProcedureCodeC.getListt()[i].CodeNum == codeNum)
            {
                if (!StringSupport.equals(ProcedureCodeC.getListt()[i].LaymanTerm, ""))
                {
                    return ProcedureCodeC.getListt()[i].LaymanTerm;
                }
                 
                return ProcedureCodeC.getListt()[i].Descript;
            }
             
        }
        return "";
    }

    /**
    * Used to check whether codes starting with T exist and are in a visible category.  If so, it moves them to the Obsolete category.  If the T code has never been used, then it deletes it.
    */
    public static void tcodesClear() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        //first delete any unused T codes
        String command = "SELECT CodeNum,ProcCode FROM procedurecode\r\n" + 
        "\t\t\t\tWHERE NOT EXISTS(SELECT * FROM procedurelog WHERE procedurelog.CodeNum=procedurecode.CodeNum)\r\n" + 
        "\t\t\t\tAND ProcCode LIKE \'T%\'";
        DataTable table = Db.getTable(command);
        long codenum = new long();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            codenum = PIn.Long(table.Rows[i]["CodeNum"].ToString());
            command = "DELETE FROM fee WHERE CodeNum=" + POut.long(codenum);
            Db.nonQ(command);
            command = "DELETE FROM procedurecode WHERE CodeNum=" + POut.long(codenum);
            Db.nonQ(command);
        }
        //then, move any other T codes to obsolete category
        command = "SELECT DISTINCT ProcCat FROM procedurecode,definition \r\n" + 
        "\t\t\t\tWHERE procedurecode.ProcCode LIKE \'T%\'\r\n" + 
        "\t\t\t\tAND definition.IsHidden=0\r\n" + 
        "\t\t\t\tAND procedurecode.ProcCat=definition.DefNum";
        table = Db.getTable(command);
        long catNum = DefC.getByExactName(DefCat.ProcCodeCats,"Obsolete");
        //check to make sure an Obsolete category exists.
        Def def;
        if (catNum != 0)
        {
            //if a category exists with that name
            def = DefC.getDef(DefCat.ProcCodeCats,catNum);
            if (!def.IsHidden)
            {
                def.IsHidden = true;
                Defs.update(def);
                Defs.refreshCache();
            }
             
        }
         
        if (catNum == 0)
        {
            def = new Def();
            def.Category = DefCat.ProcCodeCats;
            def.ItemName = "Obsolete";
            def.ItemOrder = DefC.getLong()[((Enum)DefCat.ProcCodeCats).ordinal()].Length;
            def.IsHidden = true;
            Defs.insert(def);
            Defs.refreshCache();
            catNum = def.DefNum;
        }
         
        for (int i = 0;i < table.Rows.Count;i++)
        {
            command = "UPDATE procedurecode SET ProcCat=" + POut.long(catNum) + " WHERE ProcCat=" + table.Rows[i][0].ToString() + " AND procedurecode.ProcCode LIKE 'T%'";
            Db.nonQ(command);
        }
        //finally, set Never Used category to be hidden.  This isn't really part of clearing Tcodes, but is required
        //because many customers won't have that category hidden
        catNum = DefC.getByExactName(DefCat.ProcCodeCats,"Never Used");
        if (catNum != 0)
        {
            //if a category exists with that name
            def = DefC.getDef(DefCat.ProcCodeCats,catNum);
            if (!def.IsHidden)
            {
                def.IsHidden = true;
                Defs.update(def);
                Defs.refreshCache();
            }
             
        }
         
    }

    public static void resetApptProcsQuickAdd() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "DELETE FROM definition WHERE Category=3";
        Db.nonQ(command);
        String[] array = new String[]{ "CompEx-4BW-Pano-Pro-Flo", "D0150,D0274,D0330,D1110,D1208", "CompEx-2BW-Pano-ChPro-Flo", "D0150,D0272,D0330,D1120,D1208", "PerEx-4BW-Pro-Flo", "D0120,D0274,D1110,D1208", "LimEx-PA", "D0140,D0220", "PerEx-4BW-Pro-Flo", "D0120,D0274,D1110,D1208", "PerEx-2BW-ChildPro-Flo", "D0120,D0272,D1120,D1208", "Comp Exam", "D0150", "Per Exam", "D0120", "Lim Exam", "D0140", "1 PA", "D0220", "2BW", "D0272", "4BW", "D0274", "Pano", "D0330", "Pro Adult", "D1110", "Fluor", "D1208", "Pro Child", "D1120", "PostOp", "N4101", "DentAdj", "N4102", "Consult", "D9310" };
        Def def;
        String[] codelist = new String[]();
        boolean allvalid = new boolean();
        int itemorder = 0;
        for (int i = 0;i < array.Length;i += 2)
        {
            //first, test all procedures for valid
            codelist = array[i + 1].Split(',');
            allvalid = true;
            for (int c = 0;c < codelist.Length;c++)
            {
                if (!ProcedureCodes.IsValidCode(codelist[c]))
                {
                    allvalid = false;
                }
                 
            }
            if (!allvalid)
            {
                continue;
            }
             
            def = new Def();
            def.Category = DefCat.ApptProcsQuickAdd;
            def.ItemOrder = itemorder;
            def.ItemName = array[i];
            def.ItemValue = array[i + 1];
            Defs.insert(def);
            itemorder++;
        }
    }

    /**
    * Resets the descriptions for all ADA codes to the official wording.  Required by the license.
    */
    public static int resetADAdescriptions() throws Exception {
        return ResetADAdescriptions(CDT.Class1.GetADAcodes());
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Resets the descriptions for all ADA codes to the official wording.  Required by the license.
    */
    public static int resetADAdescriptions(List<ProcedureCode> codeList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProcedureCode code;
        int count = 0;
        for (int i = 0;i < codeList.Count;i++)
        {
            if (!ProcedureCodes.IsValidCode(codeList[i].ProcCode))
            {
                continue;
            }
             
            //If this code is not in this database
            code = ProcedureCodes.GetProcCode(codeList[i].ProcCode);
            if (StringSupport.equals(code.Descript, codeList[i].Descript))
            {
                continue;
            }
             
            code.Descript = codeList[i].Descript;
            ProcedureCodes.update(code);
            count++;
        }
        return count;
    }

    //don't forget to refresh procedurecodes.
    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT ProcCode FROM procedurecode";
        DataTable Table = Db.getTable(command);
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            retVal.Add(Table.Rows[i][0].ToString());
        }
        return retVal;
    }

}


/* js These are not currently in use.  This probably needs to be consolidated with code from other places.  ProcsColored and InsSpans comes to mind.
		///<summary>Returns true if any of the codes in the list fall within the code range.</summary>
		public static bool IsCodeInRange(List<string> myCodes,string range) {
			for(int i=0;i<myCodes.Count;i++) {
				if(IsCodeInRange(myCodes[i],range)) {
					return true;
				}
			}
			return false;
		}
		///<summary>Returns true if myCode is within the code range.  Ex: myCode="D####", range="D####-D####"</summary>
		public static bool IsCodeInRange(string myCode,string range) {
			string code1="";
			string code2="";
			if(range.Contains("-")) {
				string[] codeSplit=range.Split('-');
				code1=codeSplit[0].Trim();
				code2=codeSplit[1].Trim();
			}
			else{
				code1=range.Trim();
				code2=range.Trim();
			}
			if(myCode.CompareTo(code1)<0 || myCode.CompareTo(code2)>0) {
				return false;
			}
			return true;
		}*/