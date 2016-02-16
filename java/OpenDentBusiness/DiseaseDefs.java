//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DiseaseDefs   
{
    private static DiseaseDef[] listLong = new DiseaseDef[]();
    private static DiseaseDef[] list = new DiseaseDef[]();
    /**
    * A list of all DiseaseDefs.
    */
    //No need to check RemotingRole; no call to db.
    public static DiseaseDef[] getListLong() throws Exception {
        if (listLong == null)
        {
            refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(DiseaseDef[] value) throws Exception {
        listLong = value;
    }

    /**
    * The list that is typically used. Does not include hidden diseases.
    */
    //No need to check RemotingRole; no call to db.
    public static DiseaseDef[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(DiseaseDef[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM diseasedef ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "DiseaseDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listLong = Crud.DiseaseDefCrud.TableToList(table).ToArray();
        List<DiseaseDef> listshort = new List<DiseaseDef>();
        for (int i = 0;i < listLong.Length;i++)
        {
            if (!listLong[i].IsHidden)
            {
                listshort.Add(listLong[i]);
            }
             
        }
        list = listshort.ToArray();
    }

    /**
    * 
    */
    public static void update(DiseaseDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.DiseaseDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(DiseaseDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.DiseaseDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.DiseaseDefNum;
        }
         
        return Crud.DiseaseDefCrud.Insert(def);
    }

    /**
    * Surround with try/catch, because it will throw an exception if any patient is using this def.
    */
    public static void delete(DiseaseDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        if (PrefC.getLong(PrefName.ProblemsIndicateNone) == def.DiseaseDefNum)
        {
            throw new ApplicationException(Lans.g("DiseaseDef","Not allowed to delete. In use as preference \"ProblemsIndicateNone\" in Setup>>Modules."));
        }
         
        //Validate patient attached
        String command = "SELECT LName,FName,patient.PatNum FROM patient,disease WHERE " + "patient.PatNum=disease.PatNum " + "AND disease.DiseaseDefNum='" + POut.long(def.DiseaseDefNum) + "' ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY patient.PatNum";
        }
        else
        {
            //Oracle
            command += "GROUP BY LName,FName,patient.PatNum";
        } 
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String s = Lans.g("DiseaseDef","Not allowed to delete. Already in use by ") + table.Rows.Count.ToString() + " " + Lans.g("DiseaseDef","patients, including") + " \r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                s += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString() + "\r\n";
            }
            throw new ApplicationException(s);
        }
         
        //Validate edu resource attached
        command = "SELECT COUNT(*) FROM eduresource WHERE eduresource.DiseaseDefNum='" + POut.long(def.DiseaseDefNum) + "'";
        int num = PIn.int(Db.getCount(command));
        if (num > 0)
        {
            String s = Lans.g("DiseaseDef","Not allowed to delete.  Already attached to an EHR educational resource.");
            throw new ApplicationException(s);
        }
         
        //Validate family health history attached
        command = "SELECT LName,FName,patient.PatNum FROM patient,familyhealth " + "WHERE patient.PatNum=familyhealth.PatNum " + "AND familyhealth.DiseaseDefNum='" + POut.long(def.DiseaseDefNum) + "' ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY patient.PatNum";
        }
        else
        {
            //Oracle
            command += "GROUP BY LName,FName,patient.PatNum";
        } 
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String s = Lans.g("DiseaseDef","Not allowed to delete. Already in use by") + " " + table.Rows.Count.ToString() + " " + Lans.g("DiseaseDef","patients' family history, including") + ": \r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                s += "#" + table.Rows[i]["PatNum"].ToString() + " " + table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + "\r\n";
            }
            throw new ApplicationException(s);
        }
         
        //End of validation
        command = "DELETE FROM diseasedef WHERE DiseaseDefNum =" + POut.long(def.DiseaseDefNum);
        Db.nonQ(command);
    }

    /**
    * Moves the selected item up in the listLong.
    */
    public static void moveUp(int selected) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("DiseaseDefs","Please select an item first."));
        }
         
        if (selected == 0)
        {
            return ;
        }
         
        //already at top
        if (selected > getListLong().Length - 1)
        {
            throw new ApplicationException(Lans.g("DiseaseDefs","Invalid selection."));
        }
         
        SetOrder(selected - 1, getListLong()[selected].ItemOrder);
        SetOrder(selected, getListLong()[selected].ItemOrder - 1);
    }

    //Selected-=1;
    /**
    * 
    */
    public static void moveDown(int selected) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("DiseaseDefs","Please select an item first."));
        }
         
        if (selected == getListLong().Length - 1)
        {
            return ;
        }
         
        //already at bottom
        if (selected > getListLong().Length - 1)
        {
            throw new ApplicationException(Lans.g("DiseaseDefs","Invalid selection."));
        }
         
        SetOrder(selected + 1, getListLong()[selected].ItemOrder);
        SetOrder(selected, getListLong()[selected].ItemOrder + 1);
    }

    //selected+=1;
    /**
    * Used by MoveUp and MoveDown.
    */
    private static void setOrder(int mySelNum, int myItemOrder) throws Exception {
        //No need to check RemotingRole; no call to db.
        DiseaseDef temp = getListLong()[mySelNum];
        temp.ItemOrder = myItemOrder;
        DiseaseDefs.update(temp);
    }

    /**
    * Returns the order in ListLong, whether hidden or not.
    */
    public static int getOrder(long diseaseDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].DiseaseDefNum == diseaseDefNum)
            {
                return getListLong()[i].ItemOrder;
            }
             
        }
        return 0;
    }

    /**
    * Returns the name of the disease, whether hidden or not.
    */
    public static String getName(long diseaseDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].DiseaseDefNum == diseaseDefNum)
            {
                return getListLong()[i].DiseaseName;
            }
             
        }
        return "";
    }

    /**
    * Returns the name of the disease based on SNOMEDCode, then if no match tries ICD9Code, then if no match returns empty string. Used in EHR Patient Lists.
    */
    public static String getNameByCode(String SNOMEDorICD9Code) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].SnomedCode, SNOMEDorICD9Code))
            {
                return getListLong()[i].DiseaseName;
            }
             
        }
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (StringSupport.equals(getListLong()[i].ICD9Code, SNOMEDorICD9Code))
            {
                return getListLong()[i].DiseaseName;
            }
             
        }
        return "";
    }

    /**
    * Returns the DiseaseDefNum based on SNOMEDCode, then if no match tries ICD9Code, then if no match tries ICD10Code, then if no match returns 0. Used in EHR Patient Lists and when automatically inserting pregnancy Dx from FormVitalsignEdit2014.  Will match hidden diseases.
    */
    public static long getNumFromCode(String CodeValue) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].SnomedCode, CodeValue))
            {
                return getListLong()[i].DiseaseDefNum;
            }
             
        }
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (StringSupport.equals(getListLong()[i].ICD9Code, CodeValue))
            {
                return getListLong()[i].DiseaseDefNum;
            }
             
        }
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (StringSupport.equals(getListLong()[i].Icd10Code, CodeValue))
            {
                return getListLong()[i].DiseaseDefNum;
            }
             
        }
        return 0;
    }

    /**
    * Returns the diseaseDef with the specified num.  Will match hidden diseasedefs.
    */
    public static DiseaseDef getItem(long diseaseDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].DiseaseDefNum == diseaseDefNum)
            {
                return getListLong()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Returns the diseaseDefNum that exactly matches the specified string.  Used in import functions when you only have the name to work with.  Can return 0 if no match.  Does not match hidden diseases.
    */
    public static long getNumFromName(String diseaseName) throws Exception {
        return getNumFromName(diseaseName,false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Returns the diseaseDefNum that exactly matches the specified string.  Will return 0 if no match.  Set matchHidden to true to match hidden diseasedefs as well.
    */
    public static long getNumFromName(String diseaseName, boolean matchHidden) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (matchHidden)
        {
            for (int i = 0;i < getListLong().Length;i++)
            {
                if (StringSupport.equals(diseaseName, getListLong()[i].DiseaseName))
                {
                    return getListLong()[i].DiseaseDefNum;
                }
                 
            }
        }
        else
        {
            for (int i = 0;i < getList().Length;i++)
            {
                if (StringSupport.equals(diseaseName, getList()[i].DiseaseName))
                {
                    return getList()[i].DiseaseDefNum;
                }
                 
            }
        } 
        return 0;
    }

    /**
    * Returns the diseasedef that has a name exactly matching the specified string. Returns null if no match.  Does not match hidden diseases.
    */
    public static DiseaseDef getFromName(String diseaseName) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(diseaseName, getList()[i].DiseaseName))
            {
                return getList()[i];
            }
             
        }
        return null;
    }

    public static List<long> getChangedSinceDiseaseDefNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT DiseaseDefNum FROM diseasedef WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> diseaseDefNums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            diseaseDefNums.Add(PIn.Long(dt.Rows[i]["DiseaseDefNum"].ToString()));
        }
        return diseaseDefNums;
    }

    /**
    * Used along with GetChangedSinceDiseaseDefNums
    */
    public static List<DiseaseDef> getMultDiseaseDefs(List<long> diseaseDefNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DiseaseDef>>GetObject(MethodBase.GetCurrentMethod(), diseaseDefNums);
        }
         
        String strDiseaseDefNums = "";
        DataTable table = new DataTable();
        if (diseaseDefNums.Count > 0)
        {
            for (int i = 0;i < diseaseDefNums.Count;i++)
            {
                if (i > 0)
                {
                    strDiseaseDefNums += "OR ";
                }
                 
                strDiseaseDefNums += "DiseaseDefNum='" + diseaseDefNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM diseasedef WHERE " + strDiseaseDefNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        DiseaseDef[] multDiseaseDefs = Crud.DiseaseDefCrud.TableToList(table).ToArray();
        List<DiseaseDef> diseaseDefList = new List<DiseaseDef>(multDiseaseDefs);
        return diseaseDefList;
    }

    public static boolean containsSnomed(String snomedCode, long excludeDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].SnomedCode, snomedCode) && getListLong()[i].DiseaseDefNum != excludeDefNum)
            {
                return true;
            }
             
        }
        return false;
    }

    public static boolean containsICD9(String icd9Code, long excludeDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].ICD9Code, icd9Code) && getListLong()[i].DiseaseDefNum != excludeDefNum)
            {
                return true;
            }
             
        }
        return false;
    }

    public static boolean containsIcd10(String icd10Code, long excludeDefNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].Icd10Code, icd10Code) && getListLong()[i].DiseaseDefNum != excludeDefNum)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Get all diseasedefs that have a pregnancy code that applies to the three CQM measures with pregnancy as an exclusion condition.
    */
    public static List<DiseaseDef> getAllPregDiseaseDefs() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DiseaseDef>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        Dictionary<String, String> listAllPregCodesForCQMs = EhrCodes.GetCodesExistingInAllSets(new List<String>{ "2.16.840.1.113883.3.600.1.1623", "2.16.840.1.113883.3.526.3.378" });
        List<DiseaseDef> retval = new List<DiseaseDef>();
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (listAllPregCodesForCQMs.ContainsKey(getListLong()[i].ICD9Code) && StringSupport.equals(listAllPregCodesForCQMs[getListLong()[i].ICD9Code], "ICD9CM"))
            {
                retval.Add(getListLong()[i]);
                continue;
            }
             
            if (listAllPregCodesForCQMs.ContainsKey(getListLong()[i].Icd10Code) && StringSupport.equals(listAllPregCodesForCQMs[getListLong()[i].Icd10Code], "ICD10CM"))
            {
                retval.Add(getListLong()[i]);
                continue;
            }
             
            if (listAllPregCodesForCQMs.ContainsKey(getListLong()[i].SnomedCode) && StringSupport.equals(listAllPregCodesForCQMs[getListLong()[i].SnomedCode], "SNOMEDCT"))
            {
                retval.Add(getListLong()[i]);
                continue;
            }
             
        }
        return retval;
    }

}


/*public static DiseaseDef GetByICD9Code(string ICD9Code) {///<summary>Returns the diseasedef that has a name exactly matching the specified string. Returns null if no match.  Does not match hidden diseases.</summary>
			//No need to check RemotingRole; no call to db.
			for(int i=0;i<List.Length;i++) {
				if(ICD9Code==List[i].ICD9Code) {
					return List[i];
				}
			}
			return null;
		}*/