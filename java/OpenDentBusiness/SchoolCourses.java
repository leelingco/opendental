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
import OpenDentBusiness.SchoolCourse;

/**
* 
*/
public class SchoolCourses   
{
    /**
    * A list of all schoolcourses, organized by course ID.
    */
    private static SchoolCourse[] list = new SchoolCourse[]();
    //No need to check RemotingRole; no call to db.
    public static SchoolCourse[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(SchoolCourse[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM schoolcourse " + "ORDER BY CourseID";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "SchoolCourse";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.SchoolCourseCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    private static void update(SchoolCourse sc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sc);
            return ;
        }
         
        Crud.SchoolCourseCrud.Update(sc);
    }

    /**
    * 
    */
    private static long insert(SchoolCourse sc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sc.SchoolCourseNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sc);
            return sc.SchoolCourseNum;
        }
         
        return Crud.SchoolCourseCrud.Insert(sc);
    }

    /**
    * 
    */
    public static void insertOrUpdate(SchoolCourse sc, boolean isNew) throws Exception {
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
    * 
    */
    public static void delete(long courseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), courseNum);
            return ;
        }
         
        //check for attached reqneededs---------------------------------------------------------------------
        String command = "SELECT COUNT(*) FROM reqneeded WHERE SchoolCourseNum = '" + POut.long(courseNum) + "'";
        DataTable table = Db.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0][0].ToString()), "0"))
        {
            throw new Exception(Lans.g("SchoolCourses","Course already in use by 'requirements needed' table."));
        }
         
        //check for attached reqstudents--------------------------------------------------------------------------
        command = "SELECT COUNT(*) FROM reqstudent WHERE SchoolCourseNum = '" + POut.long(courseNum) + "'";
        table = Db.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0][0].ToString()), "0"))
        {
            throw new Exception(Lans.g("SchoolCourses","Course already in use by 'student requirements' table."));
        }
         
        //delete---------------------------------------------------------------------------------------------
        command = "DELETE from schoolcourse WHERE SchoolCourseNum = '" + POut.long(courseNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Description is CourseID Descript.
    */
    public static String getDescript(long schoolCourseNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].SchoolCourseNum == schoolCourseNum)
            {
                return GetDescript(getList()[i]);
            }
             
        }
        return "";
    }

    public static String getDescript(SchoolCourse course) throws Exception {
        return course.CourseID + " " + course.Descript;
    }

    //No need to check RemotingRole; no call to db.
    public static String getCourseID(long schoolCourseNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].SchoolCourseNum == schoolCourseNum)
            {
                return getList()[i].CourseID;
            }
             
        }
        return "";
    }

}


