//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AccountingAutoPay;
import OpenDentBusiness.AccountingAutoPays;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class AccountingAutoPays   
{
    /**
    * 
    */
    private static List<AccountingAutoPay> listt = new List<AccountingAutoPay>();
    public static List<AccountingAutoPay> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<AccountingAutoPay> value) throws Exception {
        listt = value;
    }

    /**
    * Gets a list of all AccountingAutoPays.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM accountingautopay";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AccountingAutoPay";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.AccountingAutoPayCrud.TableToList(table);
    }

    /**
    * 
    */
    public static void clearCache() throws Exception {
        listt = null;
    }

    /**
    * 
    */
    public static long insert(AccountingAutoPay pay) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pay.AccountingAutoPayNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pay);
            return pay.AccountingAutoPayNum;
        }
         
        return Crud.AccountingAutoPayCrud.Insert(pay);
    }

    /**
    * Converts the comma delimited list of AccountNums into full descriptions separated by carriage returns.
    */
    public static String getPickListDesc(AccountingAutoPay pay) throws Exception {
        //No need to check RemotingRole; no call to db.
        String[] numArray = pay.PickList.Split(new char[]{ ',' });
        String retVal = "";
        for (int i = 0;i < numArray.Length;i++)
        {
            if (StringSupport.equals(numArray[i], ""))
            {
                continue;
            }
             
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += "\r\n";
            }
             
            retVal += Accounts.GetDescript(PIn.Long(numArray[i]));
        }
        return retVal;
    }

    /**
    * Converts the comma delimited list of AccountNums into an array of AccountNums.
    */
    public static long[] getPickListAccounts(AccountingAutoPay pay) throws Exception {
        //No need to check RemotingRole; no call to db.
        String[] numArray = pay.PickList.Split(new char[]{ ',' });
        ArrayList AL = new ArrayList();
        for (int i = 0;i < numArray.Length;i++)
        {
            if (StringSupport.equals(numArray[i], ""))
            {
                continue;
            }
             
            AL.Add(PIn.Long(numArray[i]));
        }
        long[] retVal = new long[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * Loops through the AList to find one with the specified payType (defNum).  If none is found, then it returns null.
    */
    public static AccountingAutoPay getForPayType(long payType) throws Exception {
        for (int i = 0;i < AccountingAutoPays.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (AccountingAutoPays.getListt()[i].PayType == payType)
            {
                return AccountingAutoPays.getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Saves the list of accountingAutoPays to the database.  Deletes all existing ones first.
    */
    public static void saveList(List<AccountingAutoPay> list) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), list);
            return ;
        }
         
        String command = "DELETE FROM accountingautopay";
        Db.nonQ(command);
        for (int i = 0;i < list.Count;i++)
        {
            Insert(list[i]);
        }
    }

}


