//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Computer;
import OpenDentBusiness.Computers;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Computers   
{
    /**
    * A list of all computers that have logged into the database in the past.  Might be some extra computer names in the list unless user has cleaned it up.
    */
    private static Computer[] list = new Computer[]();
    //No need to check RemotingRole; no call to db.
    public static Computer[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(Computer[] value) throws Exception {
        list = value;
    }

    public static void ensureComputerInDB(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerName);
            return ;
        }
         
        String command = "SELECT * from computer " + "WHERE compname = '" + computerName + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            Computer Cur = new Computer();
            Cur.CompName = computerName;
            Computers.insert(Cur);
        }
         
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        EnsureComputerInDB(Environment.MachineName);
        String command = "SELECT * FROM computer ORDER BY CompName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Computer";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        setList(Crud.ComputerCrud.TableToList(table).ToArray());
    }

    /**
    * ONLY use this if compname is not already present
    */
    public static long insert(Computer comp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            comp.ComputerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), comp);
            return comp.ComputerNum;
        }
         
        return Crud.ComputerCrud.Insert(comp);
    }

    /*
    		///<summary></summary>
    		public static void Update(){
    			string command= "UPDATE computer SET "
    				+"compname = '"    +POut.PString(CompName)+"' "
    				//+"printername = '" +POut.PString(PrinterName)+"' "
    				+"WHERE ComputerNum = '"+POut.PInt(ComputerNum)+"'";
    			//MessageBox.Show(string command);
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /**
    * 
    */
    public static void delete(Computer comp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), comp);
            return ;
        }
         
        String command = "DELETE FROM computer WHERE computernum = '" + comp.ComputerNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Only called from Printers.GetForSit
    */
    public static Computer getCur() throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (Environment.MachineName.ToUpper() == getList()[i].CompName.ToUpper())
            {
                return getList()[i];
            }
             
        }
        return null;
    }

    //this will never happen
    public static List<String> getRunningComputers() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //heartbeat is every three minutes.  We'll allow four to be generous.
        String command = "SELECT CompName FROM computer WHERE LastHeartBeat > SUBTIME(NOW(),'00:04:00')";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command = "SELECT CompName FROM computer WHERE LastHeartBeat > SYSDATE - (4/1440)";
        }
         
        DataTable table = Db.getTable(command);
        List<String> retVal = new List<String>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * When starting up, in an attempt to be fast, it will not add a new computer to the list.
    */
    public static void updateHeartBeat(String computerName, boolean isStartup) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerName, isStartup);
            return ;
        }
         
        if (!isStartup && list == null)
        {
            refreshCache();
        }
         
        //adds new computer to list
        String command = "UPDATE computer SET LastHeartBeat=" + DbHelper.now() + " WHERE CompName = '" + POut.string(computerName) + "'";
        Db.nonQ(command);
    }

    public static void clearHeartBeat(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerName);
            return ;
        }
         
        String command = "UPDATE computer SET LastHeartBeat=" + POut.date(new DateTime(0001, 1, 1),true) + " WHERE CompName = '" + POut.string(computerName) + "'";
        Db.nonQ(command);
    }

    public static void clearAllHeartBeats(String machineNameException) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), machineNameException);
            return ;
        }
         
        String command = "UPDATE computer SET LastHeartBeat=" + POut.date(new DateTime(0001, 1, 1),true) + " " + "WHERE CompName != '" + POut.string(machineNameException) + "'";
        Db.nonQ(command);
    }

}


