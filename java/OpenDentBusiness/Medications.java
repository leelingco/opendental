//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Medication;
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
public class Medications   
{
    /**
    * All medications.  Not refreshed with local data.  Only refreshed as needed.  Only used in UI layer.
    */
    public static Medication[] Listt = new Medication[]();
    /**
    * 
    */
    private static Hashtable HList = new Hashtable();
    /**
    * This must refresh Listt on client, not on server.
    */
    public static void refresh() throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Medication> list = getListFromDb();
        HList = new Hashtable();
        for (int i = 0;i < list.Count;i++)
        {
            HList.Add(list[i].MedicationNum, list[i]);
        }
        Listt = list.ToArray();
    }

    /**
    * Checks to see if the medication exists in the current cache.  If not, the local cache will get refreshed and then searched again.  If med is still not found, false is returned because the med does not exist.
    */
    private static boolean hasMedicationInCache(long medicationNum) throws Exception {
        //Check if the medication exists in the cache.
        if (!HList.ContainsKey(medicationNum))
        {
            //Medication not found.  Refresh the cache and check again.
            refresh();
            if (!HList.ContainsKey(medicationNum))
            {
                return false;
            }
             
        }
         
        return true;
    }

    //Medication does not exist in db.
    /**
    * Only public so that the remoting works.  Do not call this from anywhere except in this class.
    */
    public static List<Medication> getListFromDb() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Medication>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM medication ORDER BY MedName";
        return Crud.MedicationCrud.SelectMany(command);
    }

    // WHERE MedName LIKE '%"+POut.String(str)+"%' ORDER BY MedName";
    public static List<Medication> tableToList(DataTable table) throws Exception {
        return Crud.MedicationCrud.TableToList(table);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Returns medications that contain the passed in string.  Blank for all.
    */
    public static List<Medication> getList(String str) throws Exception {
        //No need to check RemotingRole; no call to db.
        refresh();
        List<Medication> retVal = new List<Medication>();
        for (int i = 0;i < Listt.Length;i++)
        {
            if (StringSupport.equals(str, "") || Listt[i].MedName.ToUpper().Contains(str.ToUpper()))
            {
                retVal.Add(Listt[i]);
            }
             
        }
        return retVal;
    }

    /**
    * 
    */
    public static void update(Medication Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.MedicationCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(Medication Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.MedicationNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.MedicationNum;
        }
         
        return Crud.MedicationCrud.Insert(Cur);
    }

    /**
    * Dependent brands and patients will already be checked.  Be sure to surround with try-catch.
    */
    public static void delete(Medication Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String s = isInUse(Cur);
        if (!StringSupport.equals(s, ""))
        {
            throw new ApplicationException(Lans.g("Medications",s));
        }
         
        String command = "DELETE from medication WHERE medicationNum = '" + Cur.MedicationNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Returns a string if medication is in use in medicationpat, allergydef, eduresources, or preference.MedicationsIndicateNone. The string will explain where the medication is in use.
    */
    public static String isInUse(Medication med) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), med.MedicationNum);
        }
         
        String[] brands = new String[]();
        if (med.MedicationNum == med.GenericNum)
        {
            brands = getBrands(med.MedicationNum);
        }
        else
        {
            brands = new String[0];
        } 
        if (brands.Length > 0)
        {
            return "You can not delete a medication that has brand names attached.";
        }
         
        String command = "SELECT COUNT(*) FROM medicationpat WHERE MedicationNum=" + POut.long(med.MedicationNum);
        if (PIn.int(Db.getCount(command)) != 0)
        {
            return "Not allowed to delete medication because it is in use by a patient";
        }
         
        command = "SELECT COUNT(*) FROM allergydef WHERE MedicationNum=" + POut.long(med.MedicationNum);
        if (PIn.int(Db.getCount(command)) != 0)
        {
            return "Not allowed to delete medication because it is in use by an allergy";
        }
         
        command = "SELECT COUNT(*) FROM eduresource WHERE MedicationNum=" + POut.long(med.MedicationNum);
        if (PIn.int(Db.getCount(command)) != 0)
        {
            return "Not allowed to delete medication because it is in use by an education resource";
        }
         
        if (PrefC.getLong(PrefName.MedicationsIndicateNone) == med.MedicationNum)
        {
            return "Not allowed to delete medication because it is in use by a medication";
        }
         
        return "";
    }

    /**
    * Returns an array of all patient names who are using this medication.
    */
    public static String[] getPatNamesForMed(long medicationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), medicationNum);
        }
         
        String command = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(LName,', '),FName),' '),Preferred) FROM medicationpat,patient " + "WHERE medicationpat.PatNum=patient.PatNum " + "AND medicationpat.MedicationNum=" + POut.long(medicationNum);
        DataTable table = Db.getTable(command);
        String[] retVal = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Returns a list of all brands dependend on this generic. Only gets run if this is a generic.
    */
    public static String[] getBrands(long medicationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), medicationNum);
        }
         
        String command = "SELECT MedName FROM medication " + "WHERE GenericNum=" + medicationNum.ToString() + " AND MedicationNum !=" + medicationNum.ToString();
        //except this med
        DataTable table = Db.getTable(command);
        String[] retVal = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Returns null if not found.
    */
    public static Medication getMedication(long medNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!hasMedicationInCache(medNum))
        {
            return null;
        }
         
        return (Medication)HList[medNum];
    }

    //Should never happen.
    /**
    * Deprecated.  Use GetMedication instead.
    */
    public static Medication getMedicationFromDb(long medicationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Medication>GetObject(MethodBase.GetCurrentMethod(), medicationNum);
        }
         
        String command = "SELECT * FROM medication WHERE MedicationNum=" + POut.long(medicationNum);
        return Crud.MedicationCrud.SelectOne(command);
    }

    /**
    * //Returns first medication with matching MedName, if not found returns null.
    */
    public static Medication getMedicationFromDbByName(String medicationName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Medication>GetObject(MethodBase.GetCurrentMethod(), medicationName);
        }
         
        String command = "SELECT * FROM medication WHERE MedName='" + POut.string(medicationName) + "' ORDER BY MedicationNum";
        List<Medication> retVal = Crud.MedicationCrud.SelectMany(command);
        if (retVal.Count > 0)
        {
            return retVal[0];
        }
         
        return null;
    }

    /**
    * Gets the generic medication for the specified medication Num. Returns null if not found.
    */
    public static Medication getGeneric(long medNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!hasMedicationInCache(medNum))
        {
            return null;
        }
         
        return (Medication)HList[((Medication)HList[medNum]).GenericNum];
    }

    /**
    * Gets the medication name.  Also, generic in () if applicable.  Returns empty string if not found.
    */
    public static String getDescription(long medNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (HList == null)
        {
            refresh();
        }
         
        if (!hasMedicationInCache(medNum))
        {
            return "";
        }
         
        Medication med = (Medication)HList[medNum];
        String retVal = med.MedName;
        if (med.GenericNum == med.MedicationNum)
        {
            return retVal;
        }
         
        //this is generic
        if (!HList.ContainsKey(med.GenericNum))
        {
            return retVal;
        }
         
        Medication generic = (Medication)HList[med.GenericNum];
        return retVal + "(" + generic.MedName + ")";
    }

    /**
    * Gets the medication name. Copied from GetDescription.
    */
    public static String getNameOnly(long medNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (HList == null)
        {
            refresh();
        }
         
        if (!hasMedicationInCache(medNum))
        {
            return "";
        }
         
        return ((Medication)HList[medNum]).MedName;
    }

    /**
    * Gets the generic medication name, given it's generic Num.
    */
    public static String getGenericName(long genericNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!hasMedicationInCache(genericNum))
        {
            return "";
        }
         
        return ((Medication)HList[genericNum]).MedName;
    }

    public static List<long> getChangedSinceMedicationNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT MedicationNum FROM medication WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> medicationNums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            medicationNums.Add(PIn.Long(dt.Rows[i]["MedicationNum"].ToString()));
        }
        return medicationNums;
    }

    /**
    * Used along with GetChangedSinceMedicationNums
    */
    public static List<Medication> getMultMedications(List<long> medicationNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Medication>>GetObject(MethodBase.GetCurrentMethod(), medicationNums);
        }
         
        String strMedicationNums = "";
        DataTable table = new DataTable();
        if (medicationNums.Count > 0)
        {
            for (int i = 0;i < medicationNums.Count;i++)
            {
                if (i > 0)
                {
                    strMedicationNums += "OR ";
                }
                 
                strMedicationNums += "MedicationNum='" + medicationNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM medication WHERE " + strMedicationNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Medication[] multMedications = Crud.MedicationCrud.TableToList(table).ToArray();
        List<Medication> MedicationList = new List<Medication>(multMedications);
        return MedicationList;
    }

    /**
    * Deprecated.  Use MedicationPat.Refresh() instead.  Returns medication list for a specific patient.
    */
    public static List<Medication> getMedicationsByPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Medication>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT medication.* " + "FROM medication, medicationpat " + "WHERE medication.MedicationNum=medicationpat.MedicationNum " + "AND medicationpat.PatNum=" + POut.long(patNum);
        return Crud.MedicationCrud.SelectMany(command);
    }

    public static Medication getMedicationFromDbByRxCui(long rxcui) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Medication>GetObject(MethodBase.GetCurrentMethod(), rxcui);
        }
         
        String command = "SELECT * FROM medication WHERE RxCui=" + POut.long(rxcui);
        return Crud.MedicationCrud.SelectOne(command);
    }

}


