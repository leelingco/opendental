//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Employers are refreshed as needed. A full refresh is frequently triggered if an employerNum cannot be found in the HList.  Important retrieval is done directly from the db.
*/
public class Employers   
{
    private static Employer[] list = new Employer[]();
    private static Hashtable hList = new Hashtable();
    //No need to check RemotingRole; no call to db.
    public static Employer[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(Employer[] value) throws Exception {
        list = value;
    }

    /**
    * A hashtable of all employers.
    */
    //No need to check RemotingRole; no call to db.
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        //As of 12/15/2011 we are not making use of Address info.  Selecting empty strings makes loading the cache much faster.
        String command = "SELECT EmployerNum,EmpName,'' Address,'' Address2,'' City,'' State,'' Zip,'' Phone FROM employer";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Employer";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.EmployerCrud.TableToList(table).ToArray();
        setHList(new Hashtable());
        for (int i = 0;i < list.Length;i++)
        {
            getHList().Add(list[i].EmployerNum, list[i]);
        }
    }

    /*
    		 * Not using this because it turned out to be more efficient to refresh the whole
    		 * list if an empnum could not be found.
    		///<summary>Just refreshes Cur from the db with info for one employer.</summary>
    		public static void Refresh(int employerNum){
    			Cur=new Employer();//just in case no rows are returned
    			if(employerNum==0) return;
    			string command="SELECT * FROM employer WHERE EmployerNum = '"+employerNum+"'";
    			DataTable table=Db.GetTable(command);;
    			for(int i=0;i<table.Rows.Count;i++){//almost always just 1 row, but sometimes 0
    				Cur.EmployerNum   =PIn.PInt   (table.Rows[i][0].ToString());
    				Cur.EmpName       =PIn.PString(table.Rows[i][1].ToString());
    			}
    		}*/
    /**
    * 
    */
    public static void update(Employer Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.EmployerCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(Employer Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.EmployerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.EmployerNum;
        }
         
        return Crud.EmployerCrud.Insert(Cur);
    }

    /**
    * There MUST not be any dependencies before calling this or there will be invalid foreign keys.  This is only called from FormEmployers after proper validation.
    */
    public static void delete(Employer Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from employer WHERE EmployerNum = '" + Cur.EmployerNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Returns a list of patients that are dependent on the Cur employer. The list includes carriage returns for easy display.  Used before deleting an employer to make sure employer is not in use.
    */
    public static String dependentPatients(Employer Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), Cur);
        }
         
        String command = "SELECT CONCAT(CONCAT(LName,', '),FName) FROM patient" + " WHERE EmployerNum = '" + POut.long(Cur.EmployerNum) + "'";
        DataTable table = Db.getTable(command);
        String retStr = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                retStr += "\r\n";
            }
             
            //return, newline for multiple names.
            retStr += PIn.String(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Returns a list of insplans that are dependent on the Cur employer. The list includes carriage returns for easy display.  Used before deleting an employer to make sure employer is not in use.
    */
    public static String dependentInsPlans(Employer Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), Cur);
        }
         
        String command = "SELECT carrier.CarrierName,CONCAT(CONCAT(patient.LName,', '),patient.FName) " + "FROM insplan " + "LEFT JOIN inssub ON insplan.PlanNum=inssub.PlanNum " + "LEFT JOIN patient ON inssub.Subscriber=patient.PatNum " + "LEFT JOIN carrier ON insplan.CarrierNum=carrier.CarrierNum " + "WHERE insplan.EmployerNum = " + POut.long(Cur.EmployerNum);
        DataTable table = Db.getTable(command);
        String retStr = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                retStr += "\r\n";
            }
             
            //return, newline for multiple names.
            retStr += PIn.String(table.Rows[i][1].ToString()) + ": " + PIn.String(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Gets the name of an employer based on the employerNum.  This also refreshes the list if necessary, so it will work even if the list has not been refreshed recently.
    */
    public static String getName(long employerNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (employerNum == 0)
        {
            return "";
        }
         
        if (getHList().ContainsKey(employerNum))
        {
            return ((Employer)getHList()[employerNum]).EmpName;
        }
         
        //if the employerNum could not be found:
        refreshCache();
        if (getHList().ContainsKey(employerNum))
        {
            return ((Employer)getHList()[employerNum]).EmpName;
        }
         
        return "";
    }

    //this could only happen if corrupted:
    /**
    * Gets an employer based on the employerNum. This will work even if the list has not been refreshed recently, but if you are going to need a lot of names all at once, then it is faster to refresh first.
    */
    public static Employer getEmployer(long employerNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (employerNum == 0)
        {
            return new Employer();
        }
         
        if (getHList().ContainsKey(employerNum))
        {
            return (Employer)getHList()[employerNum];
        }
         
        //if the employerNum could not be found:
        refreshCache();
        if (getHList().ContainsKey(employerNum))
        {
            return (Employer)getHList()[employerNum];
        }
         
        return new Employer();
    }

    //this could only happen if corrupted:
    /**
    * Gets an employerNum from the database based on the supplied name.  If that empName does not exist, then a new employer is created, and the employerNum for the new employer is returned.
    */
    public static long getEmployerNum(String empName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), empName);
        }
         
        if (StringSupport.equals(empName, ""))
        {
            return 0;
        }
         
        String command = "SELECT EmployerNum FROM employer" + " WHERE EmpName = '" + POut.string(empName) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            return PIn.Long(table.Rows[0][0].ToString());
        }
         
        Employer Cur = new Employer();
        Cur.EmpName = empName;
        insert(Cur);
        return Cur.EmployerNum;
    }

    //MessageBox.Show(Cur.EmployerNum.ToString());
    /**
    * Returns an arraylist of Employers with names similar to the supplied string.  Used in dropdown list from employer field for faster entry.  There is a small chance that the list will not be completely refreshed when this is run, but it won't really matter if one employer doesn't show in dropdown.
    */
    public static List<Employer> getSimilarNames(String empName) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Employer> retVal = new List<Employer>();
        for (int i = 0;i < getList().Length;i++)
        {
            //if(Regex.IsMatch(List[i].EmpName,"^"+empName,RegexOptions.IgnoreCase))
            if (getList()[i].EmpName.StartsWith(empName, StringComparison.CurrentCultureIgnoreCase))
            {
                retVal.Add(getList()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Combines all the given employers into one. Updates patient and insplan. Then deletes all the others.
    */
    public static void combine(List<long> employerNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employerNums);
            return ;
        }
         
        String newNum = employerNums[0].ToString();
        for (int i = 1;i < employerNums.Count;i++)
        {
            String command = "UPDATE patient SET EmployerNum = '" + newNum + "' WHERE EmployerNum = '" + employerNums[i].ToString() + "'";
            //MessageBox.Show(string command);
            Db.nonQ(command);
            command = "UPDATE insplan SET EmployerNum = '" + newNum + "' WHERE EmployerNum = '" + employerNums[i].ToString() + "'";
            Db.nonQ(command);
            command = "DELETE FROM employer" + " WHERE EmployerNum = '" + employerNums[i].ToString() + "'";
            Db.nonQ(command);
        }
    }

}


