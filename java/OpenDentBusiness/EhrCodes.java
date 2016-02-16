//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cdcrecs;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Cvxs;
import OpenDentBusiness.EhrCode;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Sops;

/**
* Never insert or update, use cache pattern only.  This is not referencing a real table in the database, it is a static object filled by the contents of the EHR.dll.
*/
public class EhrCodes   
{
    //Atypical cache pattern. Cache is only filled when we have access to the EHR.dll file, otherwise listt will be an empty list of EHR codes (not null, just empty as if the table were there but with no codes in it.)
    /**
    * A list of all EhrCodes.
    */
    private static List<EhrCode> listt = new List<EhrCode>();
    /**
    * A list of all EhrCodes.
    */
    public static List<EhrCode> getListt() throws Exception {
        if (listt == null)
        {
            //instead of refreshing the cache using the normal pattern we must retrieve the cache from the EHR.dll. No call to DB.
            Object ObjEhrCodeList = new Object();
            Assembly AssemblyEHR = new Assembly();
            String dllPathEHR = CodeBase.ODFileUtils.CombinePaths(System.Windows.Forms.Application.StartupPath, "EHR.dll");
            ObjEhrCodeList = null;
            AssemblyEHR = null;
            if (System.IO.File.Exists(dllPathEHR))
            {
                //EHR.dll is available, so load it up
                AssemblyEHR = Assembly.LoadFile(dllPathEHR);
                Type type = AssemblyEHR.GetType("EHR.EhrCodeList");
                //namespace.class
                ObjEhrCodeList = Activator.CreateInstance(type);
                Object[] args = null;
                listt = Crud.EhrCodeCrud.TableToList((DataTable)type.InvokeMember("GetListt", System.Reflection.BindingFlags.InvokeMethod, null, ObjEhrCodeList, args));
            }
            else
            {
                //no EHR.dll. "Return" empty list.
                listt = new List<EhrCode>();
            } 
            updateCodeExistsHelper();
        }
         
        return listt;
    }

    public static void setListt(List<EhrCode> value) throws Exception {
        listt = value;
    }

    /**
    * If the CodeValue of the EhrCode exists in its respective code table (I.e. Snomed, Loinc, Cpt, etc.) this will set IsInDb=true otherwise false.
    */
    private static void updateCodeExistsHelper() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (listt.Count == 0)
        {
            return ;
        }
         
        //Cache lists of codes.
        HashSet<String> cdcrecHS = new HashSet<String>(Cdcrecs.getAllCodes());
        HashSet<String> cdtHS = new HashSet<String>(ProcedureCodes.getAllCodes());
        HashSet<String> cptHS = new HashSet<String>(Cpts.getAllCodes());
        HashSet<String> cvxHS = new HashSet<String>(Cvxs.getAllCodes());
        HashSet<String> hcpcsHS = new HashSet<String>(Hcpcses.getAllCodes());
        HashSet<String> icd10HS = new HashSet<String>(Icd10s.getAllCodes());
        HashSet<String> icd9HS = new HashSet<String>(ICD9s.getAllCodes());
        HashSet<String> loincHS = new HashSet<String>(Loincs.getAllCodes());
        HashSet<String> rxnormHS = new HashSet<String>(RxNorms.getAllCodes());
        HashSet<String> snomedHS = new HashSet<String>(Snomeds.getAllCodes());
        HashSet<String> sopHS = new HashSet<String>(Sops.getAllCodes());
        for (int i = 0;i < listt.Count;i++)
        {
            CodeSystem __dummyScrutVar0 = listt[i].CodeSystem;
            if (__dummyScrutVar0.equals("AdministrativeSex"))
            {
                //always "in DB", even though there is no DB table
                listt[i].IsInDb = true;
            }
            else if (__dummyScrutVar0.equals("CDCREC"))
            {
                listt[i].IsInDb = cdcrecHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("CDT"))
            {
                listt[i].IsInDb = cdtHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("CPT"))
            {
                listt[i].IsInDb = cptHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("CVX"))
            {
                listt[i].IsInDb = cvxHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("HCPCS"))
            {
                listt[i].IsInDb = hcpcsHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("ICD9CM"))
            {
                listt[i].IsInDb = icd9HS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("ICD10CM"))
            {
                listt[i].IsInDb = icd10HS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("LOINC"))
            {
                listt[i].IsInDb = loincHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("RXNORM"))
            {
                listt[i].IsInDb = rxnormHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("SNOMEDCT"))
            {
                listt[i].IsInDb = snomedHS.Contains(listt[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("SOP"))
            {
                listt[i].IsInDb = sopHS.Contains(listt[i].CodeValue);
            }
                        
        }
    }

    //This updates the last column "ExistsInDatabse" based on weather or not the code is found in another table in the database.
    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.EhrCodeCrud.TableToList(table);
    }

    /**
    * 
    */
    public static String getMeasureIdsForCode(String codeValue, String codeSystem) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retval = "";
        for (int i = 0;i < getListt().Count;i++)
        {
            //System.Diagnostics.Stopwatch sw=new System.Diagnostics.Stopwatch();
            //sw.Start();
            if (StringSupport.equals(getListt()[i].CodeValue, codeValue) && StringSupport.equals(getListt()[i].CodeSystem, codeSystem))
            {
                if (retval.Contains(getListt()[i].MeasureIds))
                {
                    continue;
                }
                 
                if (!StringSupport.equals(retval, ""))
                {
                    retval += ",";
                }
                 
                retval += getListt()[i].MeasureIds;
            }
             
        }
        return retval;
    }

    //sw.Stop();
    /**
    * Returns a list of EhrCode objects that belong to one of the value sets identified by the ValueSetOIDs supplied.
    */
    public static List<EhrCode> getForValueSetOIDs(List<String> listValueSetOIDs) throws Exception {
        return GetForValueSetOIDs(listValueSetOIDs, false);
    }

    /**
    * Returns a list of EhrCode objects that belong to one of the value sets identified by the ValueSetOIDs supplied AND only those codes that exist in the corresponding table in the database.
    */
    public static List<EhrCode> getForValueSetOIDs(List<String> listValueSetOIDs, boolean usingIsInDb) throws Exception {
        List<EhrCode> retval = new List<EhrCode>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (usingIsInDb && !getListt()[i].IsInDb)
            {
                continue;
            }
             
            if (listValueSetOIDs.Contains(getListt()[i].ValueSetOID))
            {
                retval.Add(getListt()[i]);
            }
             
        }
        return retval;
    }

    /**
    * Returns a dictionary of CodeValue and CodeSystem pairs where the value set is in the supplied list.
    */
    public static Dictionary<String, String> getCodeAndCodeSystem(List<String> listValueSetOIDs, boolean usingIsInDb) throws Exception {
        Dictionary<String, String> retval = new Dictionary<String, String>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (usingIsInDb && !getListt()[i].IsInDb)
            {
                continue;
            }
             
            for (int j = 0;j < listValueSetOIDs.Count;j++)
            {
                if (getListt()[i].ValueSetOID != listValueSetOIDs[j])
                {
                    continue;
                }
                 
                if (!retval.ContainsKey(getListt()[i].CodeValue))
                {
                    retval.Add(getListt()[i].CodeValue, getListt()[i].CodeSystem);
                }
                 
                break;
            }
        }
        return retval;
    }

    /**
    * Returns a dictionary of CodeValue,CodeSystem pairs of all codes that belong to every ValueSetOID sent in the incoming list as long as the code exists in the corresponding table in the database.
    */
    public static Dictionary<String, String> getCodesExistingInAllSets(List<String> listValueSetOIDs) throws Exception {
        Dictionary<String, String> retval = new Dictionary<String, String>();
        Dictionary<String, int> codecount = new Dictionary<String, int>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (!getListt()[i].IsInDb)
            {
                continue;
            }
             
            for (int j = 0;j < listValueSetOIDs.Count;j++)
            {
                if (getListt()[i].ValueSetOID != listValueSetOIDs[j])
                {
                    continue;
                }
                 
                String keyCur = getListt()[i].CodeValue + "," + getListt()[i].CodeSystem;
                if (codecount.ContainsKey(keyCur))
                {
                    codecount[keyCur]++;
                }
                else
                {
                    //code already in list, increase find count
                    codecount.Add(keyCur, 1);
                } 
            }
        }
        for (Object __dummyForeachVar0 : codecount)
        {
            //new find
            KeyValuePair<String, int> kpairCur = (KeyValuePair<String, int>)__dummyForeachVar0;
            String[] codeValueSystem = kpairCur.Key.Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries);
            if (retval.ContainsKey(codeValueSystem[0]))
            {
                continue;
            }
             
            if (kpairCur.Value == listValueSetOIDs.Count)
            {
                retval.Add(codeValueSystem[0], codeValueSystem[1]);
            }
             
        }
        return retval;
    }

    public static List<String> getValueSetOIDsForCode(String codeValue, String codeSystem) throws Exception {
        List<String> retval = new List<String>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (retval.Contains(getListt()[i].ValueSetOID))
            {
                continue;
            }
             
            if (StringSupport.equals(getListt()[i].CodeValue, codeValue) && StringSupport.equals(getListt()[i].CodeSystem, codeSystem))
            {
                retval.Add(getListt()[i].ValueSetOID);
            }
             
        }
        return retval;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<string> GetValueSetFromCodeAndCategory(string codeValue,string codeSystem,string category) {
			List<string> retval=new List<string>();
			for(int i=0;i<Listt.Count;i++) {
				if(Listt[i].CodeValue==codeValue && Listt[i].CodeSystem==codeSystem && Listt[i].QDMCategory==category) {
					retval.Add(Listt[i].ValueSetName);
				}
			}
			return retval;
		}
		///<summary>Used for adding codes, returns a hashset of codevalue+valuesetoid.</summary>
		public static HashSet<string> GetAllCodesHashSet() {
			HashSet<string> retVal=new HashSet<string>();
			for(int i=0;i<Listt.Count;i++) {
				retVal.Add(Listt[i].CodeValue+Listt[i].ValueSetOID);
			}
			return retVal;
		}
		 * 
		///<summary></summary>
		public static List<EhrCode> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrCode>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrcode WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrCodeCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(EhrCode ehrCode) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				ehrCode.EhrCodeNum=Meth.GetLong(MethodBase.GetCurrentMethod(),ehrCode);
				return ehrCode.EhrCodeNum;
			}
			return Crud.EhrCodeCrud.Insert(ehrCode);
		}
		///<summary>Gets one EhrCode from the db.</summary>
		public static EhrCode GetOne(long ehrCodeNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrCode>(MethodBase.GetCurrentMethod(),ehrCodeNum);
			}
			return Crud.EhrCodeCrud.SelectOne(ehrCodeNum);
		}
		///<summary></summary>
		public static void Update(EhrCode ehrCode){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrCode);
				return;
			}
			Crud.EhrCodeCrud.Update(ehrCode);
		}
		///<summary></summary>
		public static void Delete(long ehrCodeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrCodeNum);
				return;
			}
			string command= "DELETE FROM ehrcode WHERE EhrCodeNum = "+POut.Long(ehrCodeNum);
			Db.NonQ(command);
		}
		*/