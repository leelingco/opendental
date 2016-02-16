//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.UserGroup;

/**
* 
*/
public class UserGroups   
{
    private static UserGroup[] list = new UserGroup[]();
    /**
    * A list of all user groups, ordered by description.
    */
    //No need to check RemotingRole; no call to db.
    public static UserGroup[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(UserGroup[] value) throws Exception {
        list = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from usergroup ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "UserGroup";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.UserGroupCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static void update(UserGroup group) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), group);
            return ;
        }
         
        Crud.UserGroupCrud.Update(group);
    }

    /**
    * 
    */
    public static long insert(UserGroup group) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            group.UserGroupNum = Meth.GetLong(MethodBase.GetCurrentMethod(), group);
            return group.UserGroupNum;
        }
         
        return Crud.UserGroupCrud.Insert(group);
    }

    /**
    * Checks for dependencies first
    */
    public static void delete(UserGroup group) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), group);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM userod WHERE UserGroupNum='" + POut.long(group.UserGroupNum) + "'";
        DataTable table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            throw new Exception(Lans.g("UserGroups","Must move users to another group first."));
        }
         
        command = "DELETE FROM usergroup WHERE UserGroupNum='" + POut.long(group.UserGroupNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static UserGroup getGroup(long userGroupNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].UserGroupNum == userGroupNum)
            {
                return getList()[i].Copy();
            }
             
        }
        return null;
    }

}


