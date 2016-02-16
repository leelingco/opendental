//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.PharmacyC;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Pharmacies   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM pharmacy ORDER BY StoreName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "Pharmacy";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        PharmacyC.setListt(Crud.PharmacyCrud.TableToList(table));
    }

    /**
    * Gets one Pharmacy from the database.
    */
    public static Pharmacy getOne(long pharmacyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Pharmacy>GetObject(MethodBase.GetCurrentMethod(), pharmacyNum);
        }
         
        return Crud.PharmacyCrud.SelectOne(pharmacyNum);
    }

    /**
    * 
    */
    public static long insert(Pharmacy pharmacy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pharmacy.PharmacyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pharmacy);
            return pharmacy.PharmacyNum;
        }
         
        return Crud.PharmacyCrud.Insert(pharmacy);
    }

    /**
    * 
    */
    public static void update(Pharmacy pharmacy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pharmacy);
            return ;
        }
         
        Crud.PharmacyCrud.Update(pharmacy);
    }

    /**
    * 
    */
    public static void deleteObject(long pharmacyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pharmacyNum);
            return ;
        }
         
        Crud.PharmacyCrud.Delete(pharmacyNum);
    }

    public static String getDescription(long PharmacyNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (PharmacyNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < PharmacyC.getListt().Count;i++)
        {
            if (PharmacyC.getListt()[i].PharmacyNum == PharmacyNum)
            {
                return PharmacyC.getListt()[i].StoreName;
            }
             
        }
        return "";
    }

    public static List<long> getChangedSincePharmacyNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT PharmacyNum FROM pharmacy WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> provnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            provnums.Add(PIn.Long(dt.Rows[i]["PharmacyNum"].ToString()));
        }
        return provnums;
    }

    /**
    * Used along with GetChangedSincePharmacyNums
    */
    public static List<Pharmacy> getMultPharmacies(List<long> pharmacyNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Pharmacy>>GetObject(MethodBase.GetCurrentMethod(), pharmacyNums);
        }
         
        String strPharmacyNums = "";
        DataTable table = new DataTable();
        if (pharmacyNums.Count > 0)
        {
            for (int i = 0;i < pharmacyNums.Count;i++)
            {
                if (i > 0)
                {
                    strPharmacyNums += "OR ";
                }
                 
                strPharmacyNums += "PharmacyNum='" + pharmacyNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM pharmacy WHERE " + strPharmacyNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Pharmacy[] multPharmacys = Crud.PharmacyCrud.TableToList(table).ToArray();
        List<Pharmacy> pharmacyList = new List<Pharmacy>(multPharmacys);
        return pharmacyList;
    }

}


