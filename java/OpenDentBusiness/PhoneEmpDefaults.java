//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpStatusOverride;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PhoneGraphs;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Not a true Cache pattern.  It only loads the cache once on startup and then never again.  No entry in the Cache file.  No InvalidType for PhoneEmpDefault.  Data is almost always pulled from db in realtime, and this cache is only used for default ringgroups.
*/
public class PhoneEmpDefaults   
{
    /**
    * A list of all PhoneEmpDefaults.
    */
    private static List<PhoneEmpDefault> listt = new List<PhoneEmpDefault>();
    /**
    * A list of all PhoneEmpDefaults.
    */
    public static List<PhoneEmpDefault> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<PhoneEmpDefault> value) throws Exception {
        listt = value;
    }

    /**
    * Not part of the true Cache pattern.  See notes above.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM phoneempdefault";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "PhoneEmpDefault";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.PhoneEmpDefaultCrud.TableToList(table);
    }

    /**
    * 
    */
    public static List<PhoneEmpDefault> refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PhoneEmpDefault>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM phoneempdefault ORDER BY PhoneExt";
        return Crud.PhoneEmpDefaultCrud.SelectMany(command);
    }

    //because that's the order we are used to in the phone panel.
    /**
    * use sparingly as this makes a db call every time. only used for validating user is not modifying "dirty" data
    */
    public static boolean getGraphedStatusForEmployeeDate(long employeeNum, DateTime dateEntry) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), employeeNum, dateEntry);
        }
         
        PhoneEmpDefault phoneEmpDefault = Crud.PhoneEmpDefaultCrud.SelectOne(employeeNum);
        if (phoneEmpDefault == null)
        {
            return false;
        }
         
        boolean isGraphed = phoneEmpDefault.IsGraphed;
        //get employee default
        PhoneGraph phoneGraph = PhoneGraphs.getForEmployeeNumAndDate(employeeNum,dateEntry);
        //check for exception
        if (phoneGraph != null)
        {
            //exception found so use that
            isGraphed = phoneGraph.IsGraphed;
        }
         
        return isGraphed;
    }

    /**
    * Gets one PhoneEmpDefault from the db.  Can return null.
    */
    public static PhoneEmpDefault getOne(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PhoneEmpDefault>GetObject(MethodBase.GetCurrentMethod(), employeeNum);
        }
         
        return Crud.PhoneEmpDefaultCrud.SelectOne(employeeNum);
    }

    /**
    * From local list. Can return null.
    */
    public static PhoneEmpDefault getEmpDefaultFromList(long employeeNum, List<PhoneEmpDefault> listPED) throws Exception {
        for (int i = 0;i < listPED.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (listPED[i].EmployeeNum == employeeNum)
            {
                return listPED[i];
            }
             
        }
        return null;
    }

    /**
    * Can return null.
    */
    public static PhoneEmpDefault getByExtAndEmp(int extension, long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PhoneEmpDefault>GetObject(MethodBase.GetCurrentMethod(), extension, employeeNum);
        }
         
        String command = "SELECT * FROM phoneempdefault WHERE PhoneExt=" + POut.int(extension) + " " + "AND EmployeeNum=" + POut.long(employeeNum);
        return Crud.PhoneEmpDefaultCrud.SelectOne(command);
    }

    public static AsteriskRingGroups getRingGroup(long employeeNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListt()[i].EmployeeNum == employeeNum)
            {
                return getListt()[i].RingGroups;
            }
             
        }
        return AsteriskRingGroups.All;
    }

    /**
    * Default to extension explicitly linked to this computer name in FormPhoneEmpDefaultEdit. If none found then uses last 2 digits of ip address.  Returns 0 if none found.
    */
    public static int getPhoneExtension(String ipAddress, String computerName, List<PhoneEmpDefault> listPED) throws Exception {
        for (int i = 0;i < listPED.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            //first find if there is a computername override entered by staff
            if (StringSupport.equals(listPED[i].ComputerName, computerName))
            {
                return listPED[i].PhoneExt;
            }
             
        }
        //there is no computername override entered by staff, so figure out what the extension should be
        if (ipAddress.Contains("10.10.1.2"))
        {
            return PIn.Int(ipAddress.ToString().Substring(8)) - 100;
        }
         
        return 0;
    }

    //eg 205-100=105
    //couldn't find good extension
    /**
    * Find first employee with this extension and return their IsTriageOperator flag.
    */
    public static boolean isTriageOperatorForExtension(int extension, List<PhoneEmpDefault> listPED) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (extension == 0)
        {
            return false;
        }
         
        for (int i = 0;i < listPED.Count;i++)
        {
            if (listPED[i].PhoneExt == extension)
            {
                return listPED[i].IsTriageOperator;
            }
             
        }
        return false;
    }

    //couldn't find extension
    /**
    * The employee passed in will take over the extension passed in.  Moves any other employee who currently has this extension set (in phoneempdefault) to extension zero.  This prevents duplicate extensions in phoneempdefault.
    */
    public static void setAvailable(int extension, long empNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), extension, empNum);
            return ;
        }
         
        Employee emp = Employees.getEmp(empNum);
        if (emp == null)
        {
            return ;
        }
         
        //Should never happen. This means the employee that's changing their status doesn't exist in the employee table.
        //No longer require users to manually type in extensions.  This could be the first time a user is going to use this extension so we cannot filter by it.
        //+"WHERE PhoneExt="+POut.Int(extension)+" "
        String command = "UPDATE phoneempdefault " + "SET StatusOverride=" + POut.int(((Enum)PhoneEmpStatusOverride.None).ordinal()) + ",PhoneExt=" + POut.int(extension) + ",EmpName='" + POut.string(emp.FName) + "' " + "WHERE EmployeeNum=" + POut.long(empNum);
        Db.nonQ(command);
        //Set the extension to 0 for any other employee that is using this extension to prevent duplicate rows using the same extentions. This would cause confusion for the ring groups.  This is possible if a user logged off and another employee logs into their computer.
        command = "UPDATE phoneempdefault SET PhoneExt=0" + " WHERE PhoneExt=" + POut.int(extension) + " AND EmployeeNum!=" + POut.long(empNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(PhoneEmpDefault phoneEmpDefault) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            phoneEmpDefault.EmployeeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), phoneEmpDefault);
            return phoneEmpDefault.EmployeeNum;
        }
         
        return Crud.PhoneEmpDefaultCrud.Insert(phoneEmpDefault, true);
    }

    //user specifies the PK
    /**
    * 
    */
    public static void update(PhoneEmpDefault phoneEmpDefault) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneEmpDefault);
            return ;
        }
         
        Crud.PhoneEmpDefaultCrud.Update(phoneEmpDefault);
    }

    /**
    * 
    */
    public static void delete(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employeeNum);
            return ;
        }
         
        String command = "DELETE FROM phoneempdefault WHERE EmployeeNum = " + POut.long(employeeNum);
        Db.nonQ(command);
    }

    /**
    * sorting class used to sort PhoneEmpDefault in various ways
    */
    public static class PhoneEmpDefaultComparer  extends IComparer<PhoneEmpDefault> 
    {
        private SortBy SortOn = SortBy.name;
        public PhoneEmpDefaultComparer(SortBy sortBy) throws Exception {
            SortOn = sortBy;
        }

        public int compare(PhoneEmpDefault x, PhoneEmpDefault y) throws Exception {
            int retVal = 0;
            switch(SortOn)
            {
                case empNum: 
                    retVal = x.EmployeeNum.CompareTo(y.EmployeeNum);
                    break;
                case ext: 
                    retVal = x.PhoneExt.CompareTo(y.PhoneExt);
                    break;
                case name: 
                default: 
                    retVal = x.EmpName.CompareTo(y.EmpName);
                    break;
            
            }
            if (retVal == 0)
            {
                return x.EmpName.CompareTo(y.EmpName);
            }
             
            return retVal;
        }

        //last name is tie breaker
        //we got here so our sort was successful
        public enum SortBy
        {
            /**
            * 0 - By Extension.
            */
            ext,
            /**
            * 1 - By EmployeeNum.
            */
            empNum,
            /**
            * 2 - By Name.
            */
            name
        }
    }

}


