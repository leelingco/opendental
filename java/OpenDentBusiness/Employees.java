//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Employees   
{
    private static Employee[] listLong = new Employee[]();
    private static Employee[] listShort = new Employee[]();
    //No need to check RemotingRole; no call to db.
    public static Employee[] getListLong() throws Exception {
        if (listLong == null)
        {
            refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(Employee[] value) throws Exception {
        listLong = value;
    }

    /**
    * Does not include hidden employees
    */
    //No need to check RemotingRole; no call to db.
    public static Employee[] getListShort() throws Exception {
        if (listShort == null)
        {
            refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(Employee[] value) throws Exception {
        listShort = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM employee ORDER BY IsHidden,FName,LName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Employee";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listLong = Crud.EmployeeCrud.TableToList(table).ToArray();
        List<Employee> tempList = new List<Employee>();
        for (int i = 0;i < listLong.Length;i++)
        {
            if (!listLong[i].IsHidden)
            {
                tempList.Add(listLong[i]);
            }
             
        }
        listShort = tempList.ToArray();
    }

    /**
    * Instead of using the cache, which sorts by FName, LName.
    */
    public static List<Employee> getForTimeCard() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Employee>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM employee WHERE IsHidden=0 ORDER BY LName,Fname";
        return Crud.EmployeeCrud.SelectMany(command);
    }

    /*public static Employee[] GetListByExtension(){
    			if(ListShort==null){
    				return new Employee[0];
    			}
    			Employee[] arrayCopy=new Employee[ListShort.Length];
    			ListShort.CopyTo(arrayCopy,0);
    			int[] arrayKeys=new int[ListShort.Length];
    			for(int i=0;i<ListShort.Length;i++){
    				arrayKeys[i]=ListShort[i].PhoneExt;
    			}
    			Array.Sort(arrayKeys,arrayCopy);
    			//List<Employee> retVal=new List<Employee>(ListShort);
    			//retVal.Sort(
    			return arrayCopy;
    		}*/
    /**
    * 
    */
    public static void update(Employee Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        if (StringSupport.equals(Cur.LName, "") && StringSupport.equals(Cur.FName, ""))
        {
            throw new ApplicationException(Lans.g("FormEmployeeEdit","Must include either first name or last name"));
        }
         
        Crud.EmployeeCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(Employee Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.EmployeeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.EmployeeNum;
        }
         
        if (StringSupport.equals(Cur.LName, "") && StringSupport.equals(Cur.FName, ""))
        {
            throw new ApplicationException(Lans.g("FormEmployeeEdit","Must include either first name or last name"));
        }
         
        return Crud.EmployeeCrud.Insert(Cur);
    }

    /**
    * Surround with try-catch
    */
    public static void delete(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employeeNum);
            return ;
        }
         
        //appointment.Assistant will not block deletion
        //schedule.EmployeeNum will not block deletion
        String command = "SELECT COUNT(*) FROM clockevent WHERE EmployeeNum=" + POut.long(employeeNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormEmployeeSelect","Not allowed to delete employee because of attached clock events."));
        }
         
        command = "SELECT COUNT(*) FROM timeadjust WHERE EmployeeNum=" + POut.long(employeeNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormEmployeeSelect","Not allowed to delete employee because of attached time adjustments."));
        }
         
        command = "SELECT COUNT(*) FROM userod WHERE EmployeeNum=" + POut.long(employeeNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormEmployeeSelect","Not allowed to delete employee because of attached user."));
        }
         
        command = "UPDATE appointment SET Assistant=0 WHERE Assistant=" + POut.long(employeeNum);
        Db.nonQ(command);
        command = "DELETE FROM schedule WHERE EmployeeNum=" + POut.long(employeeNum);
        Db.nonQ(command);
        command = "DELETE FROM employee WHERE EmployeeNum =" + POut.long(employeeNum);
        Db.nonQ(command);
    }

    /*
    		///<summary>Returns LName,FName MiddleI for the provided employee.</summary>
    		public static string GetNameLF(Employee emp){
    			return(emp.LName+", "+emp.FName+" "+emp.MiddleI);
    		}
    		///<summary>Loops through List to find matching employee, and returns LName,FName MiddleI.</summary>
    		public static string GetNameLF(int employeeNum){
    			for(int i=0;i<ListLong.Length;i++){
    				if(ListLong[i].EmployeeNum==employeeNum){
    					return GetNameLF(ListLong[i]);
    				}
    			}
    			return "";
    		}*/
    /**
    * Returns FName MiddleI LName for the provided employee.
    */
    public static String getNameFL(Employee emp) throws Exception {
        return (emp.FName + " " + emp.MiddleI + " " + emp.LName);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Loops through List to find matching employee, and returns FName MiddleI LName.
    */
    public static String getNameFL(long employeeNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].EmployeeNum == employeeNum)
            {
                return GetNameFL(getListLong()[i]);
            }
             
        }
        return "";
    }

    /**
    * Loops through List to find matching employee, and returns first 2 letters of first name.  Will later be improved with abbr field.
    */
    public static String getAbbr(long employeeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (getListLong()[i].EmployeeNum == employeeNum)
            {
                retVal = getListLong()[i].FName;
                if (retVal.Length > 2)
                    retVal = retVal.Substring(0, 2);
                 
                return retVal;
            }
             
        }
        return "";
    }

    /**
    * From cache
    */
    public static Employee getEmp(long employeeNum) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].EmployeeNum == employeeNum)
            {
                return getListLong()[i];
            }
             
        }
        return null;
    }

    /**
    * Find formatted name in list.  Takes in a name that was previously formatted by Employees.GetNameFL and finds a match in the list.  If no match is found then returns null.
    */
    public static Employee getEmp(String nameFL, List<Employee> employees) throws Exception {
        for (int i = 0;i < employees.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(GetNameFL(employees[i]), nameFL))
            {
                return employees[i];
            }
             
        }
        return null;
    }

    /**
    * Returns -1 if employeeNum is not found.  0 if not hidden and 1 if hidden
    */
    public static int isHidden(long employeeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        int rValue = -1;
        if (getListLong() != null)
        {
            for (int i = 0;i < getListLong().Length;i++)
            {
                if (getListLong()[i].EmployeeNum == employeeNum)
                {
                    rValue = (getListLong()[i].IsHidden ? 1 : 0);
                    i = getListLong().Length;
                }
                 
            }
        }
         
        return rValue;
    }

    /**
    * Loops through List to find the given extension and returns the employeeNum if found.  Otherwise, returns -1;
    */
    public static long getEmpNumAtExtension(int phoneExt) throws Exception {
        for (int i = 0;i < getListLong().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListLong()[i].PhoneExt == phoneExt)
            {
                return getListLong()[i].EmployeeNum;
            }
             
        }
        return -1;
    }

    /**
    * sorting class used to sort Employee in various ways
    */
    public static class EmployeeComparer  extends IComparer<Employee> 
    {
        private SortBy SortOn = SortBy.lastName;
        public EmployeeComparer(SortBy sortBy) throws Exception {
            SortOn = sortBy;
        }

        public int compare(Employee x, Employee y) throws Exception {
            int ret = 0;
            switch(SortOn)
            {
                case empNum: 
                    ret = x.EmployeeNum.CompareTo(y.EmployeeNum);
                    break;
                case ext: 
                    ret = x.PhoneExt.CompareTo(y.PhoneExt);
                    break;
                case firstName: 
                    ret = x.FName.CompareTo(y.FName);
                    break;
                case lastName: 
                default: 
                    ret = x.LName.CompareTo(y.LName);
                    break;
            
            }
            if (ret == 0)
            {
                return x.LName.CompareTo(y.LName);
            }
             
            return ret;
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
            * 2 - By FName.
            */
            firstName,
            /**
            * 2 - By LName.
            */
            lastName
        }
    }

}


