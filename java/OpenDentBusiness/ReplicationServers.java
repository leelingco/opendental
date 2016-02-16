//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServer;

/**
* 
*/
public class ReplicationServers   
{
    /**
    * 
    */
    private static List<ReplicationServer> listt = new List<ReplicationServer>();
    /**
    * This value is only retrieved once upon startup.
    */
    private static int server_id = -1;
    /**
    * The first time this is accessed, the value is obtained using a query.  Will be 0 unless a server id was set in my.ini.
    */
    public static int getServer_id() throws Exception {
        if (server_id == -1)
        {
            server_id = getServer_id();
        }
         
        return server_id;
    }

    public static void setServer_id(int value) throws Exception {
        server_id = value;
    }

    public static List<ReplicationServer> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String cmd = "SELECT * FROM replicationserver ORDER BY ServerId";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), cmd);
        table.TableName = "replicationserver";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ReplicationServerCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(ReplicationServer serv) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            serv.ReplicationServerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), serv);
            return serv.ReplicationServerNum;
        }
         
        return Crud.ReplicationServerCrud.Insert(serv);
    }

    /**
    * 
    */
    public static void update(ReplicationServer serv) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), serv);
            return ;
        }
         
        Crud.ReplicationServerCrud.Update(serv);
    }

    public static void deleteObject(long replicationServerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), replicationServerNum);
            return ;
        }
         
        Crud.ReplicationServerCrud.Delete(replicationServerNum);
    }

    public static int getServer_id() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        if (OpenDentBusiness.DataConnection.DBtype != OpenDentBusiness.DatabaseType.MySql)
        {
            return 0;
        }
         
        String command = "SHOW VARIABLES LIKE 'server_id'";
        DataTable table = Db.getTable(command);
        return PIn.Int(table.Rows[0][1].ToString());
    }

    /**
    * Generates a random primary key.  Tests to see if that key already exists before returning it for use.  The range of returned values is greater than 0, and less than or equal to 9223372036854775807.
    */
    public static long getKey(String tablename, String field) throws Exception {
        //No need to check RemotingRole; no call to db.
        //establish the range for this server
        long rangeStart = 10000;
        long rangeEnd = long.MaxValue;
        //the following line triggers a separate call to db if server_id=-1.  Must be cap.
        if (getServer_id() != 0)
        {
            //if it IS 0, then there is no server_id set.
            ReplicationServer thisServer = null;
            for (int i = 0;i < getListt().Count;i++)
            {
                if (getListt()[i].ServerId == getServer_id())
                {
                    thisServer = getListt()[i];
                    break;
                }
                 
            }
            if (thisServer != null)
            {
                //a ReplicationServer row was found for this server_id
                if (thisServer.RangeEnd - thisServer.RangeStart >= 999999)
                {
                    //and a valid range was entered that was at least 1,000,000
                    rangeStart = thisServer.RangeStart;
                    rangeEnd = thisServer.RangeEnd;
                }
                 
            }
             
        }
         
        Random random = new Random();
        long rndLong = new long();
        long span = rangeEnd - rangeStart;
        do
        {
            rndLong = (long)(random.NextDouble() * span) + rangeStart;
        }
        while (rndLong == 0 || rndLong < rangeStart || rndLong > rangeEnd || keyInUse(tablename,field,rndLong));
        return rndLong;
    }

    //rnd=random.Next(myPartitionStart,myPartitionEnd);
    private static boolean keyInUse(String tablename, String field, long keynum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), tablename, field, keynum);
        }
         
        String command = "SELECT COUNT(*) FROM " + tablename + " WHERE " + field + "=" + keynum.ToString();
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    //already in use
    /**
    * If this server id is 0, or if no AtoZ entered for this server, then returns empty string.
    */
    public static String getAtoZpath() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getServer_id() == 0)
        {
            return "";
        }
         
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].ServerId == getServer_id())
            {
                return getListt()[i].AtoZpath;
            }
             
        }
        return "";
    }

    //could be empty string.
    /**
    * If this server id is 0, this returns null.  Or if there is no ReplicationServer object for this server id, then this returns null.
    */
    public static ReplicationServer getForLocalComputer() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getServer_id() == 0)
        {
            return null;
        }
         
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].ServerId == getServer_id())
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Used during database maint and from update window. We cannot use objects.
    */
    public static boolean serverIsBlocked() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return true;
        }
         
        //even though we are supposed to be guaranteed to not be a web client
        String command = "SELECT COUNT(*) FROM replicationserver WHERE ServerId=" + POut.int(getServer_id()) + " AND UpdateBlocked=1";
        try
        {
            //does trigger another query if during startup
            if (StringSupport.equals(Db.getScalar(command), "0"))
            {
                return false;
            }
            else
            {
                return true;
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }
    
    }

    /**
    * Get the status of the replication server.
    */
    public static DataTable getSlaveStatus() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SHOW SLAVE STATUS";
        return Db.getTable(command);
    }

}


