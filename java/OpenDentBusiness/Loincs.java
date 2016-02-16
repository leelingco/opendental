//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Loincs   
{
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Loincs.</summary>
    		private static List<Loinc> listt;
    		///<summary>A list of all Loincs.</summary>
    		public static List<Loinc> Listt{
    			get {
    				if(listt==null) {
    					RefreshCache();
    				}
    				return listt;
    			}
    			set {
    				listt=value;
    			}
    		}
    		///<summary></summary>
    		public static DataTable RefreshCache(){
    			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    			string command="SELECT * FROM loinc ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Loinc";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.LoincCrud.TableToList(table);
    		}
    		#endregion*/
    /**
    * 
    */
    public static long insert(Loinc lOINC) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            lOINC.LoincNum = Meth.GetLong(MethodBase.GetCurrentMethod(), lOINC);
            return lOINC.LoincNum;
        }
         
        return Crud.LoincCrud.Insert(lOINC);
    }

    /**
    * 
    */
    public static List<Loinc> getBySearchString(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Loinc>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String command = new String();
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "SELECT * FROM loinc WHERE LoincCode LIKE '%" + POut.string(searchText) + "%' OR NameLongCommon LIKE '%" + POut.string(searchText) + "%' ORDER BY RankCommonTests=0, RankCommonTests";
        }
        else
        {
            //common tests are at top of list.
            //oracle
            command = "SELECT * FROM loinc WHERE LoincCode LIKE '%" + POut.string(searchText) + "%' OR NameLongCommon LIKE '%" + POut.string(searchText) + "%'";
        } 
        return Crud.LoincCrud.SelectMany(command);
    }

    /**
    * Gets one Loinc from the db based on LoincCode, returns null if not found.
    */
    public static Loinc getByCode(String loincCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Loinc>GetObject(MethodBase.GetCurrentMethod(), loincCode);
        }
         
        String command = "SELECT * FROM loinc WHERE LoincCode='" + POut.string(loincCode) + "'";
        List<Loinc> retVal = Crud.LoincCrud.SelectMany(command);
        if (retVal.Count > 0)
        {
            return retVal[0];
        }
         
        return null;
    }

    /**
    * Gets a list of Loinc objects from the db based on codeList.  codeList is a comma-delimited list of LoincCodes in the format "code,code,code,code".  Returns an empty list if none in the loinc table.
    */
    public static List<Loinc> getForCodeList(String codeList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Loinc>>GetObject(MethodBase.GetCurrentMethod(), codeList);
        }
         
        String[] codes = codeList.Split(',');
        String command = "SELECT * FROM loinc WHERE LoincCode IN(";
        for (int i = 0;i < codes.Length;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += "'" + POut.String(codes[i]) + "'";
        }
        command += ") ";
        return Crud.LoincCrud.SelectMany(command);
    }

    /**
    * CAUTION, this empties the entire loinc table. "DELETE FROM loinc"
    */
    public static void deleteAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "DELETE FROM loinc";
        Db.nonQ(command);
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE loinc AUTO_INCREMENT = 1";
            //resets the primary key to start counting from 1 again.
            Db.nonQ(command);
        }
         
        return ;
    }

    /**
    * Returns a list of just the codes for use in update or insert logic.
    */
    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT LoincCode FROM loinc";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i].ItemArray[0].ToString());
        }
        return retVal;
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String LoincCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), LoincCode);
        }
         
        String command = "SELECT COUNT(*) FROM loinc WHERE LoincCode='" + POut.string(LoincCode) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Loinc> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Loinc>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM loinc WHERE PatNum = "+POut.Long(patNum);
			return Crud.LoincCrud.SelectMany(command);
		}
		///<summary>Gets one Loinc from the db.</summary>
		public static Loinc GetOne(long lOINCNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Loinc>(MethodBase.GetCurrentMethod(),lOINCNum);
			}
			return Crud.LoincCrud.SelectOne(lOINCNum);
		}
		///<summary></summary>
		public static void Update(Loinc lOINC){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),lOINC);
				return;
			}
			Crud.LoincCrud.Update(lOINC);
		}
		///<summary></summary>
		public static void Delete(long lOINCNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),lOINCNum);
				return;
			}
			string command= "DELETE FROM loinc WHERE LoincNum = "+POut.Long(lOINCNum);
			Db.NonQ(command);
		}
		*/