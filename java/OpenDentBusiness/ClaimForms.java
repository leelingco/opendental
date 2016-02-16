//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimFormItems;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ClaimForms   
{
    private static ClaimForm[] listLong = new ClaimForm[]();
    private static ClaimForm[] listShort = new ClaimForm[]();
    /**
    * List of all claim forms.
    */
    //No need to check RemotingRole; no call to db.
    public static ClaimForm[] getListLong() throws Exception {
        if (listLong == null)
        {
            refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(ClaimForm[] value) throws Exception {
        listLong = value;
    }

    /**
    * List of all claim forms except those marked as hidden.
    */
    //No need to check RemotingRole; no call to db.
    public static ClaimForm[] getListShort() throws Exception {
        if (listShort == null)
        {
            refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(ClaimForm[] value) throws Exception {
        listShort = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM claimform";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ClaimForm";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listLong = Crud.ClaimFormCrud.TableToList(table).ToArray();
        List<ClaimForm> ls = new List<ClaimForm>();
        for (int i = 0;i < listLong.Length;i++)
        {
            getListLong()[i].Items = ClaimFormItems.GetListForForm(getListLong()[i].ClaimFormNum);
            if (!listLong[i].IsHidden)
            {
                ls.Add(getListLong()[i]);
            }
             
        }
        listShort = ls.ToArray();
    }

    /**
    * Inserts this claimform into database and retrieves the new primary key.
    */
    public static long insert(ClaimForm cf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cf.ClaimFormNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cf);
            return cf.ClaimFormNum;
        }
         
        return Crud.ClaimFormCrud.Insert(cf);
    }

    /**
    * 
    */
    public static void update(ClaimForm cf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cf);
            return ;
        }
         
        Crud.ClaimFormCrud.Update(cf);
    }

    /**
    * Called when cancelling out of creating a new claimform, and from the claimform window when clicking delete. Returns true if successful or false if dependencies found.
    */
    public static boolean delete(ClaimForm cf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), cf);
        }
         
        //first, do dependency testing
        String command = "SELECT * FROM insplan WHERE claimformnum = '" + cf.ClaimFormNum.ToString() + "' ";
        command += DbHelper.limitAnd(1);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 1)
        {
            return false;
        }
         
        //Then, delete the claimform
        command = "DELETE FROM claimform " + "WHERE ClaimFormNum = '" + POut.long(cf.ClaimFormNum) + "'";
        Db.nonQ(command);
        command = "DELETE FROM claimformitem " + "WHERE ClaimFormNum = '" + POut.long(cf.ClaimFormNum) + "'";
        Db.nonQ(command);
        return true;
    }

    /**
    * Updates all claimforms with this unique id including all attached items.
    */
    public static void updateByUniqueID(ClaimForm cf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cf);
            return ;
        }
         
        //first get a list of the ClaimFormNums with this UniqueId
        String command = "SELECT ClaimFormNum FROM claimform WHERE UniqueID ='" + cf.UniqueID.ToString() + "'";
        DataTable table = Db.getTable(command);
        long[] claimFormNums = new long[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            claimFormNums[i] = PIn.Long(table.Rows[i][0].ToString());
        }
        for (int i = 0;i < claimFormNums.Length;i++)
        {
            //loop through each matching claimform
            cf.ClaimFormNum = claimFormNums[i];
            update(cf);
            command = "DELETE FROM claimformitem " + "WHERE ClaimFormNum = '" + POut.Long(claimFormNums[i]) + "'";
            Db.nonQ(command);
            for (int j = 0;j < cf.Items.Length;j++)
            {
                cf.Items[j].ClaimFormNum = claimFormNums[i];
                ClaimFormItems.Insert(cf.Items[j]);
            }
        }
    }

    /**
    * Returns the claim form specified by the given claimFormNum
    */
    public static ClaimForm getClaimForm(long claimFormNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].ClaimFormNum == claimFormNum)
            {
                return getListLong()[i];
            }
             
        }
        MessageBox.Show("Error. Could not locate Claim Form.");
        return null;
    }

    /**
    * Returns the claim form specified by the given claimFormNum
    */
    public static ClaimForm getClaimFormByUniqueId(String uniqueId) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListLong()[i].UniqueID, uniqueId))
            {
                return getListLong()[i];
            }
             
        }
        return null;
    }

    //MessageBox.Show("Error. Could not locate Claim Form.");
    /**
    * Returns number of insplans affected.
    */
    public static long reassign(long oldClaimFormNum, long newClaimFormNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), oldClaimFormNum, newClaimFormNum);
        }
         
        String command = "UPDATE insplan SET ClaimFormNum=" + POut.long(newClaimFormNum) + " WHERE ClaimFormNum=" + POut.long(oldClaimFormNum);
        return Db.nonQ(command);
    }

}


