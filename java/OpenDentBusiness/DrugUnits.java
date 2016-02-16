//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.DrugUnit;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DrugUnits   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types. (Done.)
    /**
    * A list of all DrugUnits.
    */
    private static List<DrugUnit> listt = new List<DrugUnit>();
    /**
    * A list of all DrugUnits.
    */
    public static List<DrugUnit> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<DrugUnit> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM drugunit ORDER BY UnitIdentifier";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "DrugUnit";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.DrugUnitCrud.TableToList(table);
    }

    /**
    * Gets one DrugUnit from the db.
    */
    public static DrugUnit getOne(long drugUnitNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DrugUnit>GetObject(MethodBase.GetCurrentMethod(), drugUnitNum);
        }
         
        return Crud.DrugUnitCrud.SelectOne(drugUnitNum);
    }

    /**
    * 
    */
    public static long insert(DrugUnit drugUnit) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            drugUnit.DrugUnitNum = Meth.GetLong(MethodBase.GetCurrentMethod(), drugUnit);
            return drugUnit.DrugUnitNum;
        }
         
        return Crud.DrugUnitCrud.Insert(drugUnit);
    }

    /**
    * 
    */
    public static void update(DrugUnit drugUnit) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), drugUnit);
            return ;
        }
         
        Crud.DrugUnitCrud.Update(drugUnit);
    }

    /**
    * Surround with a try/catch.  Will fail if drug unit is in use.
    */
    public static void delete(long drugUnitNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), drugUnitNum);
            return ;
        }
         
        //validation
        String command = new String();
        //no longer used in labresult
        //command="SELECT COUNT(*) FROM labresult WHERE DrugUnitNum="+POut.Long(drugUnitNum);
        //if(Db.GetCount(command)!="0") {
        //	throw new ApplicationException(Lans.g("FormDrugUnitEdit","Cannot delete: DrugUnit is in use by LabResult."));
        //}
        command = "SELECT COUNT(*) FROM vaccinepat WHERE DrugUnitNum=" + POut.long(drugUnitNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormDrugUnitEdit","Cannot delete: DrugUnit is in use by VaccinePat."));
        }
         
        command = "DELETE FROM drugunit WHERE DrugUnitNum = " + POut.long(drugUnitNum);
        Db.nonQ(command);
    }

    /*
    		///<summary>For example, mL</summary>
    		public static string GetIdentifier(long drugUnitNum) {
    			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    			for(int i=0;i<Listt.Count;i++) {//using public Listt in case it's null.
    				if(Listt[i].DrugUnitNum==drugUnitNum) {
    					return Listt[i].UnitIdentifier;
    				}
    			}
    			return "";//should never happen
    		}*/
    public static List<long> getChangedSinceDrugUnitNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT DrugUnitNum FROM drugunit WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> drugUnitNums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            drugUnitNums.Add(PIn.Long(dt.Rows[i]["DrugUnitNum"].ToString()));
        }
        return drugUnitNums;
    }

    /**
    * Used along with GetChangedSinceDrugUnitNums
    */
    public static List<DrugUnit> getMultDrugUnits(List<long> drugUnitNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DrugUnit>>GetObject(MethodBase.GetCurrentMethod(), drugUnitNums);
        }
         
        if (drugUnitNums.Count == 0)
        {
            return new List<DrugUnit>();
        }
         
        String strDrugUnitNums = "";
        for (int i = 0;i < drugUnitNums.Count;i++)
        {
            if (i > 0)
            {
                strDrugUnitNums += "OR ";
            }
             
            strDrugUnitNums += "DrugUnitNum='" + drugUnitNums[i].ToString() + "' ";
        }
        String command = "SELECT * FROM drugunit WHERE " + strDrugUnitNums;
        DataTable table = Db.getTable(command);
        return Crud.DrugUnitCrud.TableToList(table);
    }

}


