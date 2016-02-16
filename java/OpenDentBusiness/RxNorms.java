//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxNorm;

/**
* 
*/
public class RxNorms   
{
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all RxNorms.</summary>
    		private static List<RxNorm> listt;
    		///<summary>A list of all RxNorms.</summary>
    		public static List<RxNorm> Listt{
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
    			string command="SELECT * FROM rxnorm ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="RxNorm";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.RxNormCrud.TableToList(table);
    		}
    		#endregion*/
    public static boolean isRxNormTableEmpty() throws Exception {
        String command = "SELECT COUNT(*) FROM rxnorm";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return true;
        }
         
        return false;
    }

    public static RxNorm getByRxCUI(String rxCui) throws Exception {
        String command = "SELECT * FROM rxnorm WHERE RxCui='" + POut.string(rxCui) + "' AND MmslCode=''";
        return Crud.RxNormCrud.SelectOne(command);
    }

    /**
    * //Deprecated. Truncates the current rxnorm and refills based on the rxnorm.zip resource.  May take a few seconds.
    */
    //public static void CreateFreshRxNormTableFromZip() {
    //	if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    //		Meth.GetVoid(MethodBase.GetCurrentMethod());
    //		return;
    //	}
    //	MemoryStream ms=new MemoryStream();
    //	using(ZipFile unzipped=ZipFile.Read(Properties.Resources.rxnorm)) {
    //		ZipEntry ze=unzipped["rxnorm.txt"];
    //		ze.Extract(ms);
    //	}
    //	StreamReader reader=new StreamReader(ms);
    //	ms.Position=0;
    //	StringBuilder command=new StringBuilder();
    //	command.AppendLine("TRUNCATE TABLE rxnorm;");
    //	string line;
    //	if(DataConnection.DBtype==DatabaseType.MySql) {
    //		while((line=reader.ReadLine())!=null) {
    //			string[] lineSplit=line.Split('\t');
    //			command.AppendLine("INSERT INTO rxnorm(RxCui,MmslCode,Description) VALUES('"+lineSplit[0]+"','"+lineSplit[1]+"','"+lineSplit[2]+"');");
    //		}
    //	}
    //	else {//oracle
    //		long count=0;
    //		while((line=reader.ReadLine())!=null) {
    //			string[] lineSplit=line.Split('\t');
    //			if(count<1) {//Hardcode first insert for oracle.
    //				command.AppendLine("INSERT INTO rxnorm(RxNormNum,RxCui,MmslCode,Description) "
    //				+"VALUES(1,'"+lineSplit[0]+"','"+lineSplit[1]+"','"+lineSplit[2]+"');");
    //			}
    //			else {
    //				command.AppendLine("INSERT INTO rxnorm(RxNormNum,RxCui,MmslCode,Description) "
    //				+"VALUES((SELECT MAX(RxNormNum)+1 FROM rxnorm),'"+lineSplit[0]+"','"+lineSplit[1]+"','"+lineSplit[2]+"');");
    //			}
    //			count++;
    //		}
    //	}
    //	Db.NonQ(command.ToString());
    //	ms.Close();
    //	reader.Close();
    //}
    /**
    * Never returns multums, only used for displaying after a search.
    */
    public static List<RxNorm> getListByCodeOrDesc(String codeOrDesc, boolean isExact, boolean ignoreNumbers) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RxNorm>>GetObject(MethodBase.GetCurrentMethod(), codeOrDesc);
        }
         
        String command = "";
        if (isExact)
        {
            command = "SELECT * FROM rxnorm WHERE (RxCui LIKE '" + POut.string(codeOrDesc) + "' OR Description LIKE '" + POut.string(codeOrDesc) + "') " + "AND MmslCode=''";
        }
        else
        {
            command = "SELECT * FROM rxnorm WHERE (RxCui LIKE '%" + POut.string(codeOrDesc) + "%' OR Description LIKE '%" + POut.string(codeOrDesc) + "%') " + "AND MmslCode=''";
        } 
        if (ignoreNumbers)
        {
            command += " AND Description NOT REGEXP '.*[0-9]+.*'";
        }
         
        command += " ORDER BY Description";
        return Crud.RxNormCrud.SelectMany(command);
    }

    /**
    * Used to return the multum code based on RxCui.  If blank, use the Description instead.
    */
    public static String getMmslCodeByRxCui(String rxCui) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), rxCui);
        }
         
        String command = "SELECT MmslCode FROM rxnorm WHERE MmslCode!='' AND RxCui='" + rxCui + "'";
        return Db.getScalar(command);
    }

    /**
    * 
    */
    public static String getDescByRxCui(String rxCui) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), rxCui);
        }
         
        String command = "SELECT Description FROM rxnorm WHERE MmslCode='' AND RxCui='" + rxCui + "'";
        return Db.getScalar(command);
    }

    /**
    * Gets one RxNorm from the db.
    */
    public static RxNorm getOne(long rxNormNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RxNorm>GetObject(MethodBase.GetCurrentMethod(), rxNormNum);
        }
         
        return Crud.RxNormCrud.SelectOne(rxNormNum);
    }

    /**
    * 
    */
    public static long insert(RxNorm rxNorm) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            rxNorm.RxNormNum = Meth.GetLong(MethodBase.GetCurrentMethod(), rxNorm);
            return rxNorm.RxNormNum;
        }
         
        return Crud.RxNormCrud.Insert(rxNorm);
    }

    /**
    * Returns a list of just the codes for use in the codesystem import tool.
    */
    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT RxCui FROM rxnorm";
        //will return some duplicates due to the nature of the data in the table. This is acceptable.
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i].ItemArray[0].ToString());
        }
        return retVal;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<RxNorm> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<RxNorm>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM rxnorm WHERE PatNum = "+POut.Long(patNum);
			return Crud.RxNormCrud.SelectMany(command);
		}
		*/