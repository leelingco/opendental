//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SchoolClass;

/**
* 
*/
public class SchoolClasses   
{
    /**
    * A list of all classes, ordered by year and descript.
    */
    private static SchoolClass[] list = new SchoolClass[]();
    //No need to check RemotingRole; no call to db.
    public static SchoolClass[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(SchoolClass[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM schoolclass " + "ORDER BY GradYear,Descript";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "SchoolClass";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.SchoolClassCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    private static void update(SchoolClass sc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sc);
            return ;
        }
         
        Crud.SchoolClassCrud.Update(sc);
    }

    /**
    * 
    */
    private static long insert(SchoolClass sc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sc.SchoolClassNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sc);
            return sc.SchoolClassNum;
        }
         
        return Crud.SchoolClassCrud.Insert(sc);
    }

    /**
    * 
    */
    public static void insertOrUpdate(SchoolClass sc, boolean isNew) throws Exception {
        //No need to check RemotingRole; no call to db.
        //if(IsRepeating && DateTask.Year>1880){
        //	throw new Exception(Lans.g(this,"Task cannot be tagged repeating and also have a date."));
        //}
        if (isNew)
        {
            insert(sc);
        }
        else
        {
            update(sc);
        } 
    }

    /**
    * Surround by a try/catch in case there are dependencies.
    */
    public static void delete(long classNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), classNum);
            return ;
        }
         
        //check for attached providers
        String command = "SELECT COUNT(*) FROM provider WHERE SchoolClassNum = '" + POut.long(classNum) + "'";
        DataTable table = Db.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0][0].ToString()), "0"))
        {
            throw new Exception(Lans.g("SchoolClasses","Class already in use by providers."));
        }
         
        //check for attached reqneededs.
        command = "SELECT COUNT(*) FROM reqneeded WHERE SchoolClassNum = '" + POut.long(classNum) + "'";
        table = Db.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0][0].ToString()), "0"))
        {
            throw new Exception(Lans.g("SchoolClasses","Class already in use by 'requirements needed' table."));
        }
         
        command = "DELETE from schoolclass WHERE SchoolClassNum = '" + POut.long(classNum) + "'";
        Db.nonQ(command);
    }

    public static String getDescript(long SchoolClassNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].SchoolClassNum == SchoolClassNum)
            {
                return GetDescript(getList()[i]);
            }
             
        }
        return "";
    }

    public static String getDescript(SchoolClass schoolClass) throws Exception {
        return schoolClass.GradYear + "-" + schoolClass.Descript;
    }

}


//No need to check RemotingRole; no call to db.