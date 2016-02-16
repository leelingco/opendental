//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patients;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLog;
import OpenDentBusiness.SecurityLogHashes;
import OpenDentBusiness.SecurityLogs;

/**
* 
*/
public class SecurityLogs   
{
    /**
    * Used when viewing securityLog from the security admin window.  PermTypes can be length 0 to get all types.
    */
    public static SecurityLog[] refresh(DateTime dateFrom, DateTime dateTo, OpenDentBusiness.Permissions permType, long patNum, long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SecurityLog[]>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo, permType, patNum, userNum);
        }
         
        String command = "SELECT securitylog.*,LName,FName,Preferred,MiddleI,LogHash FROM securitylog " + "LEFT JOIN patient ON patient.PatNum=securitylog.PatNum " + "LEFT JOIN securityloghash ON securityloghash.SecurityLogNum=securitylog.SecurityLogNum " + "WHERE LogDateTime >= " + POut.date(dateFrom) + " " + "AND LogDateTime <= " + POut.Date(dateTo.AddDays(1));
        if (patNum != 0)
        {
            command += " AND securitylog.PatNum= '" + POut.long(patNum) + "'";
        }
         
        if (permType != OpenDentBusiness.Permissions.None)
        {
            command += " AND PermType=" + POut.Long(((Enum)permType).ordinal());
        }
         
        if (userNum != 0)
        {
            command += " AND UserNum=" + POut.long(userNum);
        }
         
        command += " ORDER BY LogDateTime";
        DataTable table = Db.getTable(command);
        List<SecurityLog> list = Crud.SecurityLogCrud.TableToList(table);
        for (int i = 0;i < list.Count;i++)
        {
            if (StringSupport.equals(table.Rows[i]["PatNum"].ToString(), "0"))
            {
                list[i].PatientName = "";
            }
            else
            {
                list[i].PatientName = table.Rows[i]["PatNum"].ToString() + "-" + Patients.GetNameLF(table.Rows[i]["LName"].ToString(), table.Rows[i]["FName"].ToString(), table.Rows[i]["Preferred"].ToString(), table.Rows[i]["MiddleI"].ToString());
            } 
            list[i].LogHash = table.Rows[i]["LogHash"].ToString();
        }
        return list.ToArray();
    }

    /**
    * 
    */
    public static long insert(SecurityLog log) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            log.SecurityLogNum = Meth.GetLong(MethodBase.GetCurrentMethod(), log);
            return log.SecurityLogNum;
        }
         
        return Crud.SecurityLogCrud.Insert(log);
    }

    //there are no methods for deleting or changing log entries because that will never be allowed.
    /**
    * Used when viewing various audit trails of specific types.  Only implemented Appointments,ProcFeeEdit,InsPlanChangeCarrierName so far. patNum only used for Appointments.  The other two are zero.
    */
    public static SecurityLog[] refresh(long patNum, List<OpenDentBusiness.Permissions> permTypes, long fKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SecurityLog[]>GetObject(MethodBase.GetCurrentMethod(), patNum, permTypes, fKey);
        }
         
        String types = "";
        for (int i = 0;i < permTypes.Count;i++)
        {
            if (i > 0)
            {
                types += " OR";
            }
             
            types += " PermType=" + POut.Long((int)permTypes[i]);
        }
        String command = "SELECT * FROM securitylog " + "WHERE (" + types + ") " + "AND FKey=" + POut.long(fKey) + " ";
        if (patNum != 0)
        {
            //appointments
            command += " AND PatNum=" + POut.long(patNum) + " ";
        }
         
        command += "ORDER BY LogDateTime";
        return Crud.SecurityLogCrud.SelectMany(command).ToArray();
    }

    /*
    			DataTable table=Db.GetTable(command);
    			SecurityLog[] List=new SecurityLog[table.Rows.Count];
    			for(int i=0;i<List.Length;i++){
    				List[i]=new SecurityLog();
    				List[i].SecurityLogNum= PIn.Long   (table.Rows[i]["SecurityLogNum"].ToString());
    				List[i].PermType      = (Permissions)PIn.Long(table.Rows[i]["PermType"].ToString());
    				List[i].UserNum       = PIn.Long   (table.Rows[i]["UserNum"].ToString());
    				List[i].LogDateTime   = PIn.DateT (table.Rows[i]["LogDateTime"].ToString());	
    				List[i].LogText       = PIn.String(table.Rows[i]["LogText"].ToString());
    				List[i].PatNum        = PIn.Long   (table.Rows[i]["PatNum"].ToString());
    				List[i].FKey					= PIn.Long   (table.Rows[i]["FKey"].ToString());
    			}
    			return List;*/
    /**
    * Returns one SecurityLog from the db.  Called from SecurityLogHashs.CreateSecurityLogHash()
    */
    public static SecurityLog getOne(long securityLogNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SecurityLog>GetObject(MethodBase.GetCurrentMethod(), securityLogNum);
        }
         
        return Crud.SecurityLogCrud.SelectOne(securityLogNum);
    }

    /**
    * PatNum can be 0.
    */
    public static void makeLogEntry(OpenDentBusiness.Permissions permType, long patNum, String logText) throws Exception {
        //No need to check RemotingRole; no call to db.
        MakeLogEntry(permType, patNum, logText, 0);
    }

    /**
    * Takes a foreign key to a table associated with that PermType.  PatNum can be 0.
    */
    public static void makeLogEntry(OpenDentBusiness.Permissions permType, long patNum, String logText, long fKey) throws Exception {
        //No need to check RemotingRole; no call to db.
        SecurityLog securityLog = new SecurityLog();
        securityLog.PermType = permType;
        if (Security.getCurUser() != null)
        {
            //if this is generated by Patient Portal web service then we won't have a CurUser set
            securityLog.UserNum = Security.getCurUser().UserNum;
        }
         
        securityLog.LogText = logText;
        //"From: "+Environment.MachineName+" - "+logText;
        securityLog.CompName = Environment.MachineName;
        securityLog.PatNum = patNum;
        securityLog.FKey = fKey;
        securityLog.SecurityLogNum = SecurityLogs.insert(securityLog);
        //Create a hash of the security log.
        SecurityLogHashes.insertSecurityLogHash(securityLog.SecurityLogNum);
    }

}


//uses db date/time