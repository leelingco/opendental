//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;

/**
* 
*/
public class PhoneGraphs   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below and edit.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all PhoneGraphs.</summary>
    		private static List<PhoneGraph> listt;
    		///<summary>A list of all PhoneGraphs.</summary>
    		public static List<PhoneGraph> Listt{
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
    			string command="SELECT * FROM phonegraph ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="PhoneGraph";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.PhoneGraphCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static List<PhoneGraph> refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PhoneGraph>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM phonegraph ORDER BY DateEntry, EmployeeNum";
        return Crud.PhoneGraphCrud.SelectMany(command);
    }

    /**
    * Gets one PhoneGraph from the db.
    */
    public static PhoneGraph getOne(long phoneGraphNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PhoneGraph>GetObject(MethodBase.GetCurrentMethod(), phoneGraphNum);
        }
         
        return Crud.PhoneGraphCrud.SelectOne(phoneGraphNum);
    }

    /**
    * 
    */
    public static List<PhoneGraph> getAllForEmployeeNum(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PhoneGraph>>GetObject(MethodBase.GetCurrentMethod(), employeeNum);
        }
         
        String command = "SELECT * FROM phonegraph WHERE EmployeeNum=" + POut.long(employeeNum) + " " + "ORDER BY DateEntry";
        return Crud.PhoneGraphCrud.SelectMany(command);
    }

    public static List<PhoneGraph> getAllForDate(DateTime dateGet) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PhoneGraph>>GetObject(MethodBase.GetCurrentMethod(), dateGet);
        }
         
        String command = "SELECT * FROM phonegraph WHERE DateEntry=" + POut.date(dateGet);
        return Crud.PhoneGraphCrud.SelectMany(command);
    }

    public static PhoneGraph getForEmployeeNumAndDate(long employeeNum, DateTime dateEntry) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PhoneGraph>GetObject(MethodBase.GetCurrentMethod(), employeeNum, dateEntry);
        }
         
        String command = "SELECT * FROM phonegraph WHERE EmployeeNum=" + POut.long(employeeNum) + " " + "AND DateEntry=" + POut.date(dateEntry);
        return Crud.PhoneGraphCrud.SelectOne(command);
    }

    /**
    * external callers should use InsertOrUpdate
    */
    private static long insert(PhoneGraph phoneGraph) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            phoneGraph.PhoneGraphNum = Meth.GetLong(MethodBase.GetCurrentMethod(), phoneGraph);
            return phoneGraph.PhoneGraphNum;
        }
         
        return Crud.PhoneGraphCrud.Insert(phoneGraph);
    }

    /**
    * 
    */
    public static void update(PhoneGraph phoneGraph) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneGraph);
            return ;
        }
         
        Crud.PhoneGraphCrud.Update(phoneGraph);
    }

    /**
    * user may have modified an existing PhoneGraph to look like a different existing so delete duplicates before inserting the new one
    */
    public static void insertOrUpdate(PhoneGraph phoneGraph) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneGraph);
            return ;
        }
         
        //do we have any duplicates?
        String command = "SELECT PhoneGraphNum FROM PhoneGraph " + "WHERE EmployeeNum=" + POut.long(phoneGraph.EmployeeNum) + " " + "AND DateEntry=" + POut.date(phoneGraph.DateEntry);
        long phoneGraphNumOld = PIn.long(Db.getScalar(command));
        if (phoneGraphNumOld > 0)
        {
            //duplicate found, get rid of it
            delete(phoneGraphNumOld);
        }
         
        insert(phoneGraph);
    }

    //its now safe to insert
    /**
    * Each date should have one (and only 1) PhoneGraph entry per employee. Some may already be entered as exceptions to the default. We will fill in the gaps here. This will only be done for today's date (once Today has passed the opportunity to fill the gaps has passed). We don't want to presume that if it was missing on a past date then we should add it. This assumption would fill in gaps on past dates for employees that may not even have worked here on that date.
    */
    public static void addMissingEntriesForToday(List<PhoneEmpDefault> peds) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), peds);
            return ;
        }
         
        //get all overrides created for this date
        List<PhoneGraph> listPhoneGraphs = GetAllForDate(DateTime.Today);
        List<Schedule> listSchedules = Schedules.GetDayList(DateTime.Today);
        for (int iPed = 0;iPed < peds.Count;iPed++)
        {
            //loop through all defaults and check if there are overrides added
            PhoneEmpDefault ped = peds[iPed];
            boolean hasPhoneGraphEntry = false;
            for (int iPG = 0;iPG < listPhoneGraphs.Count;iPG++)
            {
                //we have a default, now loop through all known overrides and find a match
                PhoneGraph pg = listPhoneGraphs[iPG];
                if (ped.EmployeeNum == listPhoneGraphs[iPG].EmployeeNum)
                {
                    //found a match so no op necessary for this employee
                    hasPhoneGraphEntry = true;
                    break;
                }
                 
            }
            if (hasPhoneGraphEntry)
            {
                continue;
            }
             
            //no entry needed, it's already there
            //does employee have a schedule table entry for this date
            boolean hasScheduleEntry = false;
            for (int iSch = 0;iSch < listSchedules.Count;iSch++)
            {
                Schedule schedule = listSchedules[iSch];
                if (ped.EmployeeNum == listSchedules[iSch].EmployeeNum)
                {
                    //found a match so no op necessary for this employee
                    hasScheduleEntry = true;
                    break;
                }
                 
            }
            if (!hasScheduleEntry)
            {
                continue;
            }
             
            //no entry needed if not on the schedule
            //employee is on the schedule but does not have a phonegraph entry, so create one
            PhoneGraph pgNew = new PhoneGraph();
            pgNew.EmployeeNum = ped.EmployeeNum;
            pgNew.DateEntry = DateTime.Today;
            pgNew.IsGraphed = ped.IsGraphed;
            insert(pgNew);
        }
    }

    /**
    * 
    */
    public static void delete(long phoneGraphNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneGraphNum);
            return ;
        }
         
        Crud.PhoneGraphCrud.Delete(phoneGraphNum);
    }

    /**
    * Delete all entries for this date. Used by internal 'Shared Projects Subversion' project which back-fills PhoneGraph entries for past dates.
    */
    public static long deleteDate(DateTime dateEntry) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), dateEntry);
        }
         
        String command = "DELETE FROM phonegraph WHERE DateEntry = " + POut.date(dateEntry);
        return Db.nonQ(command);
    }

}


