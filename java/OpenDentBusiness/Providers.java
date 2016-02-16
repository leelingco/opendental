//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;

/**
* 
*/
public class Providers   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM provider ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Provider";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProviderC.setListLong(Crud.ProviderCrud.TableToList(table));
        List<Provider> listShort = new List<Provider>();
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (!ProviderC.getListLong()[i].IsHidden)
            {
                listShort.Add(ProviderC.getListLong()[i]);
            }
             
        }
        ProviderC.setListShort(listShort);
    }

    /**
    * 
    */
    public static void update(Provider provider) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), provider);
            return ;
        }
         
        Crud.ProviderCrud.Update(provider);
    }

    /**
    * 
    */
    public static long insert(Provider provider) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            provider.ProvNum = Meth.GetLong(MethodBase.GetCurrentMethod(), provider);
            return provider.ProvNum;
        }
         
        return Crud.ProviderCrud.Insert(provider);
    }

    /**
    * Increments all (privider.ItemOrder)s that are >= the ItemOrder of the provider passed in
    * but does not change the item order of the provider passed in.
    */
    public static void moveDownBelow(Provider provider) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            provider.ProvNum = Meth.GetLong(MethodBase.GetCurrentMethod(), provider);
        }
         
        //Add 1 to all item orders equal to or greater than new provider's item order
        Db.nonQ("UPDATE provider SET ItemOrder=ItemOrder+1" + " WHERE ProvNum!=" + provider.ProvNum + " AND ItemOrder>=" + provider.ItemOrder);
    }

    /**
    * Only used from FormProvEdit if user clicks cancel before finishing entering a new provider.
    */
    public static void delete(Provider prov) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), prov);
            return ;
        }
         
        String command = "DELETE from provider WHERE provnum = '" + prov.ProvNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Gets table for main provider edit list.  SchoolClass is usually zero to indicate all providers.  IsAlph will sort aphabetically instead of by ItemOrder.
    */
    public static DataTable refresh(long schoolClass, boolean isAlph) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), schoolClass, isAlph);
        }
         
        //Max function used for Oracle compatability (some providers may have multiple user names).
        String command = "SELECT Abbr,LName,FName,provider.IsHidden,provider.ItemOrder,provider.ProvNum,GradYear,Descript,MAX(UserName) UserName, PatCount " + "FROM provider LEFT JOIN schoolclass ON provider.SchoolClassNum=schoolclass.SchoolClassNum " + "LEFT JOIN userod ON userod.ProvNum=provider.ProvNum " + "LEFT JOIN (SELECT PriProv, COUNT(*) PatCount FROM patient " + "WHERE patient.PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal()) + " AND patient.PatStatus!=" + POut.int(((Enum)PatientStatus.Deceased).ordinal()) + " " + "GROUP BY PriProv) pat ON provider.ProvNum=pat.PriProv  ";
        if (schoolClass != 0)
        {
            command += "WHERE provider.SchoolClassNum=" + POut.long(schoolClass) + " ";
        }
         
        command += "GROUP BY Abbr,LName,FName,provider.IsHidden,provider.ItemOrder,provider.ProvNum,GradYear,Descript ";
        if (isAlph)
        {
            command += "ORDER BY GradYear,Descript,LName,FName";
        }
        else
        {
            command += "ORDER BY ItemOrder";
        } 
        return Db.getTable(command);
    }

    public static List<Provider> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Provider>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM provider WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.ProviderCrud.SelectMany(command);
    }

    //DataTable table=Db.GetTable(command);
    //return TableToList(table);
    /**
    * 
    */
    public static String getAbbr(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (ProviderC.getListLong() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                return ProviderC.getListLong()[i].Abbr;
            }
             
        }
        return "";
    }

    /**
    * Used in the HouseCalls bridge
    */
    public static String getLName(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retStr = "";
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                retStr = ProviderC.getListLong()[i].LName;
            }
             
        }
        return retStr;
    }

    /**
    * First Last, Suffix
    */
    public static String getFormalName(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retStr = "";
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                retStr = ProviderC.getListLong()[i].FName + " " + ProviderC.getListLong()[i].LName;
                if (!StringSupport.equals(ProviderC.getListLong()[i].Suffix, ""))
                {
                    retStr += ", " + ProviderC.getListLong()[i].Suffix;
                }
                 
            }
             
        }
        return retStr;
    }

    /**
    * Abbr - LName, FName (hidden).
    */
    public static String getLongDesc(long provNum) throws Exception {
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                return ProviderC.getListLong()[i].GetLongDesc();
            }
             
        }
        return "";
    }

    /**
    * 
    */
    public static Color getColor(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        Color retCol = Color.White;
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                retCol = ProviderC.getListLong()[i].ProvColor;
            }
             
        }
        return retCol;
    }

    /**
    * 
    */
    public static Color getOutlineColor(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        Color retCol = Color.Black;
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                retCol = ProviderC.getListLong()[i].OutlineColor;
            }
             
        }
        return retCol;
    }

    /**
    * 
    */
    public static boolean getIsSec(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        boolean retVal = false;
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                retVal = ProviderC.getListLong()[i].IsSecondary;
            }
             
        }
        return retVal;
    }

    /**
    * Gets a provider from ListLong.  If provnum is not valid, then it returns null.
    */
    public static Provider getProv(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (provNum == 0)
        {
            return null;
        }
         
        if (ProviderC.getListLong() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                return ProviderC.getListLong()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Gets a provider from the List.  If EcwID is not found, then it returns null.
    */
    public static Provider getProvByEcwID(String eID) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(eID, ""))
        {
            return null;
        }
         
        if (ProviderC.getListLong() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (StringSupport.equals(ProviderC.getListLong()[i].EcwID, eID))
            {
                return ProviderC.getListLong()[i].Copy();
            }
             
        }
        //If using eCW, a provider might have been added from the business layer.
        //The UI layer won't know about the addition.
        //So we need to refresh if we can't initially find the prov.
        refreshCache();
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (StringSupport.equals(ProviderC.getListLong()[i].EcwID, eID))
            {
                return ProviderC.getListLong()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Takes a provNum. Normally returns that provNum. If in Orion mode, returns the user's ProvNum, if that user is a primary provider. Otherwise, in Orion Mode, returns 0.
    */
    public static long getOrionProvNum(long provNum) throws Exception {
        if (Programs.getUsingOrion())
        {
            Userod user = Security.getCurUser();
            if (user != null)
            {
                Provider prov = Providers.getProv(user.ProvNum);
                if (prov != null)
                {
                    if (!prov.IsSecondary)
                    {
                        return user.ProvNum;
                    }
                     
                }
                 
            }
             
            return 0;
        }
         
        return provNum;
    }

    /**
    * 
    */
    public static int getIndexLong(long provNum) throws Exception {
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProviderC.getListLong()[i].ProvNum == provNum)
            {
                return i;
            }
             
        }
        return 0;
    }

    //should NEVER happen, but just in case, the 0 won't crash
    /**
    * Within the regular list of visible providers.  Will return -1 if the specified provider is not in the list.
    */
    public static int getIndex(long provNum) throws Exception {
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProviderC.getListShort()[i].ProvNum == provNum)
            {
                return i;
            }
             
        }
        return -1;
    }

    /**
    * If useClinic, then clinicInsBillingProv will be used.  Otherwise, the pref for the practice.  Either way, there are three different choices for getting the billing provider.  One of the three is to use the treating provider, so supply that as an argument.  It will return a valid provNum unless the supplied treatProv was invalid.
    */
    public static long getBillingProvNum(long treatProv, long clinicNum) throws Exception {
        //,bool useClinic,int clinicInsBillingProv){
        //No need to check RemotingRole; no call to db.
        long clinicInsBillingProv = 0;
        boolean useClinic = false;
        if (clinicNum > 0)
        {
            useClinic = true;
            clinicInsBillingProv = Clinics.getClinic(clinicNum).InsBillingProv;
        }
         
        if (useClinic)
        {
            if (clinicInsBillingProv == 0)
            {
                return PrefC.getLong(PrefName.PracticeDefaultProv);
            }
            else //default=0
            if (clinicInsBillingProv == -1)
            {
                return treatProv;
            }
            else
            {
                return clinicInsBillingProv;
            }  
        }
        else
        {
            //treat=-1
            if (PrefC.getLong(PrefName.InsBillingProv) == 0)
            {
                return PrefC.getLong(PrefName.PracticeDefaultProv);
            }
            else //default=0
            if (PrefC.getLong(PrefName.InsBillingProv) == -1)
            {
                return treatProv;
            }
            else
            {
                return PrefC.getLong(PrefName.InsBillingProv);
            }  
        } 
    }

    //treat=-1
    /**
    * Used when adding a provider to get the next available itemOrder.
    */
    public static int getNextItemOrder() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        //Is this valid in Oracle??
        String command = "SELECT MAX(ItemOrder) FROM provider";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return 0;
        }
         
        return PIn.Int(table.Rows[0][0].ToString()) + 1;
    }

    /**
    * Used once in the Provider Select window to warn user of duplicate Abbrs.
    */
    public static String getDuplicateAbbrs() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT Abbr FROM provider p1 WHERE EXISTS" + "(SELECT * FROM provider p2 WHERE p1.ProvNum!=p2.ProvNum AND p1.Abbr=p2.Abbr) GROUP BY Abbr";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        String retVal = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                retVal += ",";
            }
             
            retVal += table.Rows[i][0].ToString();
        }
        return retVal;
    }

    public static DataTable getDefaultPracticeProvider() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT FName,LName,Suffix,StateLicense\r\n" + 
        "\t\t\t\tFROM provider\r\n" + 
        "        WHERE provnum=" + PrefC.getString(PrefName.PracticeDefaultProv);
        return Db.getTable(command);
    }

    /**
    * We should merge these results with GetDefaultPracticeProvider(), but
    * that would require restructuring indexes in different places in the code and this is
    * faster to do as we are just moving the queries down in to the business layer for now.
    */
    public static DataTable getDefaultPracticeProvider2() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT FName,LName,Specialty " + "FROM provider WHERE provnum=" + POut.long(PrefC.getLong(PrefName.PracticeDefaultProv));
        return Db.getTable(command);
    }

    //Convert.ToInt32(((Pref)PrefC.HList["PracticeDefaultProv"]).ValueString);
    /**
    * We should merge these results with GetDefaultPracticeProvider(), but
    * that would require restructuring indexes in different places in the code and this is
    * faster to do as we are just moving the queries down in to the business layer for now.
    */
    public static DataTable getDefaultPracticeProvider3() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT NationalProvID " + "FROM provider WHERE provnum=" + POut.long(PrefC.getLong(PrefName.PracticeDefaultProv));
        return Db.getTable(command);
    }

    public static DataTable getPrimaryProviders(long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum);
        }
         
        String command = "SELECT Fname,Lname from provider\r\n" + 
        "                        WHERE provnum in (select priprov from \r\n" + 
        "                        patient where patnum = " + PatNum + ")";
        return Db.getTable(command);
    }

    public static List<long> getChangedSinceProvNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT ProvNum FROM provider WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> provnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            provnums.Add(PIn.Long(dt.Rows[i]["ProvNum"].ToString()));
        }
        return provnums;
    }

    /**
    * Used along with GetChangedSinceProvNums
    */
    public static List<Provider> getMultProviders(List<long> provNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Provider>>GetObject(MethodBase.GetCurrentMethod(), provNums);
        }
         
        String strProvNums = "";
        DataTable table = new DataTable();
        if (provNums.Count > 0)
        {
            for (int i = 0;i < provNums.Count;i++)
            {
                if (i > 0)
                {
                    strProvNums += "OR ";
                }
                 
                strProvNums += "ProvNum='" + provNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM provider WHERE " + strProvNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Provider[] multProviders = Crud.ProviderCrud.TableToList(table).ToArray();
        List<Provider> providerList = new List<Provider>(multProviders);
        return providerList;
    }

    /**
    * Removes a provider from the future schedule.  Currently called after a provider is hidden.
    */
    public static void removeProvFromFutureSchedule(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (provNum < 1)
        {
            return ;
        }
         
        //Invalid provNum, nothing to do.
        List<long> provNums = new List<long>();
        provNums.Add(provNum);
        RemoveProvsFromFutureSchedule(provNums);
    }

    /**
    * Removes the providers from the future schedule.  Currently called from DBM to clean up hidden providers still on the schedule.
    */
    public static void removeProvsFromFutureSchedule(List<long> provNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), provNums);
            return ;
        }
         
        String provs = "";
        for (int i = 0;i < provNums.Count;i++)
        {
            if (provNums[i] < 1)
            {
                continue;
            }
             
            //Invalid provNum, nothing to do.
            if (i > 0)
            {
                provs += ",";
            }
             
            provs += provNums[i].ToString();
        }
        if (StringSupport.equals(provs, ""))
        {
            return ;
        }
         
        //No valid provNums were passed in.  Simply return.
        String command = "DELETE FROM schedule WHERE ProvNum IN (" + provs + ") AND SchedDate > " + DbHelper.now();
        Db.nonQ(command);
    }

}


