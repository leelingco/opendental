//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ICD9s   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all ICD9s.
    */
    private static List<ICD9> listt = new List<ICD9>();
    /**
    * A list of all ICD9s.
    */
    public static List<ICD9> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<ICD9> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM icd9 ORDER BY ICD9Code";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ICD9";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ICD9Crud.TableToList(table);
    }

    /**
    * 
    */
    public static List<ICD9> getByCodeOrDescription(String searchTxt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ICD9>>GetObject(MethodBase.GetCurrentMethod(), searchTxt);
        }
         
        String command = "SELECT * FROM icd9 WHERE ICD9Code LIKE '%" + POut.string(searchTxt) + "%' " + "OR Description LIKE '%" + POut.string(searchTxt) + "%'";
        return Crud.ICD9Crud.SelectMany(command);
    }

    /**
    * Gets one ICD9 from the db.
    */
    public static ICD9 getOne(long iCD9Num) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ICD9>GetObject(MethodBase.GetCurrentMethod(), iCD9Num);
        }
         
        return Crud.ICD9Crud.SelectOne(iCD9Num);
    }

    /**
    * 
    */
    public static List<ICD9> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ICD9>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM icd9";
        return Crud.ICD9Crud.SelectMany(command);
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String iCD9Code) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), iCD9Code);
        }
         
        String command = "SELECT COUNT(*) FROM icd9 WHERE ICD9Code = '" + POut.string(iCD9Code) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * 
    */
    public static long insert(ICD9 icd9) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            icd9.ICD9Num = Meth.GetLong(MethodBase.GetCurrentMethod(), icd9);
            return icd9.ICD9Num;
        }
         
        return Crud.ICD9Crud.Insert(icd9);
    }

    /**
    * 
    */
    public static void update(ICD9 icd9) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), icd9);
            return ;
        }
         
        Crud.ICD9Crud.Update(icd9);
    }

    /**
    * 
    */
    public static void delete(long icd9Num) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), icd9Num);
            return ;
        }
         
        String command = "SELECT LName,FName,patient.PatNum FROM patient,disease,diseasedef,icd9 WHERE " + "patient.PatNum=disease.PatNum " + "AND disease.DiseaseDefNum=diseasedef.DiseaseDefNum " + "AND diseasedef.ICD9Code=icd9.ICD9Code " + "AND icd9.ICD9Num='" + POut.long(icd9Num) + "' " + "GROUP BY patient.PatNum";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String s = Lans.g("ICD9","Not allowed to delete. Already in use by ") + table.Rows.Count.ToString() + " " + Lans.g("ICD9","patients, including") + " \r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                s += table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + " - " + table.Rows[i]["PatNum"].ToString() + "\r\n";
            }
            throw new ApplicationException(s);
        }
         
        command = "DELETE FROM icd9 WHERE ICD9Num = " + POut.long(icd9Num);
        Db.nonQ(command);
    }

    /**
    * This method uploads only the ICD9s that are used by the disease table. This is to reduce upload time.
    */
    public static List<long> getChangedSinceICD9Nums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        //string command="SELECT ICD9Num FROM icd9 WHERE DateTStamp > "+POut.DateT(changedSince);//Dennis: delete this line later
        String command = "SELECT ICD9Num FROM icd9 WHERE DateTStamp > " + POut.dateT(changedSince) + " AND ICD9Num in (SELECT ICD9Num FROM disease)";
        DataTable dt = Db.getTable(command);
        List<long> icd9Nums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            icd9Nums.Add(PIn.Long(dt.Rows[i]["ICD9Num"].ToString()));
        }
        return icd9Nums;
    }

    /**
    * Used along with GetChangedSinceICD9Nums
    */
    public static List<ICD9> getMultICD9s(List<long> icd9Nums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ICD9>>GetObject(MethodBase.GetCurrentMethod(), icd9Nums);
        }
         
        String strICD9Nums = "";
        DataTable table = new DataTable();
        if (icd9Nums.Count > 0)
        {
            for (int i = 0;i < icd9Nums.Count;i++)
            {
                if (i > 0)
                {
                    strICD9Nums += "OR ";
                }
                 
                strICD9Nums += "ICD9Num='" + icd9Nums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM icd9 WHERE " + strICD9Nums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        ICD9[] multICD9s = Crud.ICD9Crud.TableToList(table).ToArray();
        List<ICD9> icd9List = new List<ICD9>(multICD9s);
        return icd9List;
    }

    /**
    * Returns the code and description of the icd9.
    */
    public static String getCodeAndDescription(String icd9Code) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListt()[i].ICD9Code, icd9Code))
            {
                return getListt()[i].ICD9Code + "-" + getListt()[i].Description;
            }
             
        }
        return "";
    }

    /**
    * Returns the ICD9 of the code passed in by looking in cache.  If code does not exist, returns null.
    */
    public static ICD9 getByCode(String icd9Code) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListt()[i].ICD9Code, icd9Code))
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Returns true if descriptions have not been updated to non-Caps Lock.  Always returns false if not MySQL.
    */
    public static boolean isOldDescriptions() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        if (OpenDentBusiness.DataConnection.DBtype != OpenDentBusiness.DatabaseType.MySql)
        {
            return false;
        }
         
        String command = "SELECT COUNT(*) FROM icd9 WHERE BINARY description = UPPER(description)";
        //count rows that are all caps
        if (PIn.int(Db.getScalar(command)) > 10000)
        {
            return true;
        }
         
        return false;
    }

    //"Normal" DB should have 4, might be more if hand entered, over 10k means it is the old import.
    /**
    * Returns a list of just the codes for use in update or insert logic.
    */
    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT icd9code FROM icd9";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i].ItemArray[0].ToString());
        }
        return retVal;
    }

}


