//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ZipCode;

/**
* 
*/
public class ZipCodes   
{
    /**
    * 
    */
    private static ZipCode[] list = new ZipCode[]();
    /**
    * 
    */
    private static ArrayList aLFrequent = new ArrayList();
    /**
    * Only used from UI.
    */
    public static ArrayList ALMatches = new ArrayList();
    //No need to check RemotingRole; no call to db.
    public static ZipCode[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(ZipCode[] value) throws Exception {
        list = value;
    }

    //No need to check RemotingRole; no call to db.
    public static ArrayList getALFrequent() throws Exception {
        if (aLFrequent == null)
        {
            refreshCache();
        }
         
        return aLFrequent;
    }

    public static void setALFrequent(ArrayList value) throws Exception {
        aLFrequent = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from zipcode ORDER BY zipcodedigits";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ZipCode";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ZipCodeCrud.TableToList(table).ToArray();
        aLFrequent = new ArrayList();
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].IsFrequent)
            {
                aLFrequent.Add(list[i]);
            }
             
        }
    }

    /**
    * 
    */
    public static long insert(ZipCode Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ZipCodeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ZipCodeNum;
        }
         
        return Crud.ZipCodeCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(ZipCode Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ZipCodeCrud.Update(Cur);
    }

    /**
    * 
    */
    public static void delete(ZipCode Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from zipcode WHERE zipcodenum = '" + POut.long(Cur.ZipCodeNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void getALMatches(String zipCodeDigits) throws Exception {
        //No need to check RemotingRole; no call to db.
        ALMatches = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (StringSupport.equals(getList()[i].ZipCodeDigits, zipCodeDigits))
            {
                ALMatches.Add(getList()[i]);
            }
             
        }
    }

}


