//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReqNeededs;
import OpenDentBusiness.ReqStudent;
import OpenDentBusiness.ReqStudents;

/**
* 
*/
public class ReqStudents   
{
    public static List<ReqStudent> getForAppt(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ReqStudent>>GetObject(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        String command = "SELECT * FROM reqstudent WHERE AptNum=" + POut.long(aptNum) + " ORDER BY ProvNum,Descript";
        return Crud.ReqStudentCrud.SelectMany(command);
    }

    public static ReqStudent getOne(long ReqStudentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ReqStudent>GetObject(MethodBase.GetCurrentMethod(), ReqStudentNum);
        }
         
        String command = "SELECT * FROM reqstudent WHERE ReqStudentNum=" + POut.long(ReqStudentNum);
        return Crud.ReqStudentCrud.SelectOne(ReqStudentNum);
    }

    /**
    * 
    */
    public static void update(ReqStudent req) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), req);
            return ;
        }
         
        Crud.ReqStudentCrud.Update(req);
    }

    /**
    * 
    */
    public static long insert(ReqStudent req) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            req.ReqStudentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), req);
            return req.ReqStudentNum;
        }
         
        return Crud.ReqStudentCrud.Insert(req);
    }

    /**
    * Surround with try/catch.
    */
    public static void delete(long reqStudentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reqStudentNum);
            return ;
        }
         
        ReqStudent req = getOne(reqStudentNum);
        //if a reqneeded exists, then disallow deletion.
        if (ReqNeededs.getReq(req.ReqNeededNum) == null)
        {
            throw new Exception(Lans.g("ReqStudents","Cannot delete requirement.  Delete the requirement needed instead."));
        }
         
        String command = "DELETE FROM reqstudent WHERE ReqStudentNum = " + POut.long(reqStudentNum);
        Db.nonQ(command);
    }

    public static DataTable refreshOneStudent(long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), provNum);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("appointment");
        table.Columns.Add("course");
        table.Columns.Add("done");
        table.Columns.Add("patient");
        table.Columns.Add("ReqStudentNum");
        table.Columns.Add("requirement");
        String command = "SELECT AptDateTime,CourseID,reqStudent.Descript ReqDescript," + "schoolcourse.Descript CourseDescript,reqstudent.DateCompleted, " + "patient.LName,patient.FName,patient.MiddleI,patient.Preferred,ProcDescript,reqstudent.ReqStudentNum " + "FROM reqstudent " + "LEFT JOIN schoolcourse ON reqstudent.SchoolCourseNum=schoolcourse.SchoolCourseNum " + "LEFT JOIN patient ON reqstudent.PatNum=patient.PatNum " + "LEFT JOIN appointment ON reqstudent.AptNum=appointment.AptNum " + "WHERE reqstudent.ProvNum=" + POut.long(provNum) + " ORDER BY CourseID,ReqDescript";
        DataTable raw = Db.getTable(command);
        DateTime AptDateTime = new DateTime();
        DateTime dateCompleted = new DateTime();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            AptDateTime = PIn.DateT(raw.Rows[i]["AptDateTime"].ToString());
            if (AptDateTime.Year > 1880)
            {
                row["appointment"] = AptDateTime.ToShortDateString() + " " + AptDateTime.ToShortTimeString() + " " + raw.Rows[i]["ProcDescript"].ToString();
            }
             
            row["course"] = raw.Rows[i]["CourseID"].ToString();
            //+" "+raw.Rows[i]["CourseDescript"].ToString();
            dateCompleted = PIn.Date(raw.Rows[i]["DateCompleted"].ToString());
            if (dateCompleted.Year > 1880)
            {
                row["done"] = "X";
            }
             
            row["patient"] = PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
            row["ReqStudentNum"] = raw.Rows[i]["ReqStudentNum"].ToString();
            row["requirement"] = raw.Rows[i]["ReqDescript"].ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    public static DataTable refreshManyStudents(long classNum, long courseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), classNum, courseNum);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("donereq");
        table.Columns.Add("FName");
        table.Columns.Add("LName");
        table.Columns.Add("studentNum");
        //ProvNum
        table.Columns.Add("totalreq");
        //not used yet.  It will be changed to be based upon reqneeded. Or not used at all.
        String command = "SELECT COUNT(DISTINCT req2.ReqStudentNum) donereq,FName,LName,provider.ProvNum," + "COUNT(DISTINCT req1.ReqStudentNum) totalreq " + "FROM provider " + "LEFT JOIN reqstudent req1 ON req1.ProvNum=provider.ProvNum AND req1.SchoolCourseNum=" + POut.long(courseNum) + " " + "LEFT JOIN reqstudent req2 ON req2.ProvNum=provider.ProvNum AND " + DbHelper.year("req2.DateCompleted") + " > 1880 " + "AND req2.SchoolCourseNum=" + POut.long(courseNum) + " " + "WHERE provider.SchoolClassNum=" + POut.long(classNum) + " GROUP BY FName,LName,provider.ProvNum " + "ORDER BY LName,FName";
        DataTable raw = Db.getTable(command);
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["donereq"] = raw.Rows[i]["donereq"].ToString();
            row["FName"] = raw.Rows[i]["FName"].ToString();
            row["LName"] = raw.Rows[i]["LName"].ToString();
            row["studentNum"] = raw.Rows[i]["ProvNum"].ToString();
            row["totalreq"] = raw.Rows[i]["totalreq"].ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    public static List<Provider> getStudents(long classNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Provider> retVal = new List<Provider>();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ProviderC.getListShort()[i].SchoolClassNum == classNum)
            {
                retVal.Add(ProviderC.getListShort()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Provider(student) is required.
    */
    public static DataTable getForCourseClass(long schoolCourse, long schoolClass) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), schoolCourse, schoolClass);
        }
         
        String command = "SELECT Descript,ReqNeededNum " + "FROM reqneeded ";
        //if(schoolCourse==0){
        //	command+="WHERE ProvNum="+POut.PInt(provNum);
        //}
        //else{
        //+" AND ProvNum="+POut.PInt(provNum);
        //}
        command += "WHERE SchoolCourseNum=" + POut.long(schoolCourse) + " AND SchoolClassNum=" + POut.long(schoolClass);
        command += " ORDER BY Descript";
        return Db.getTable(command);
    }

    /**
    * All fields for all reqs will have already been set.  All except for reqstudent.ReqStudentNum if new.  Now, they just have to be persisted to the database.
    */
    public static void synchApt(List<ReqStudent> reqsAttached, long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reqsAttached, aptNum);
            return ;
        }
         
        //first, detach all from this appt
        String command = "UPDATE reqstudent SET AptNum=0 WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
        if (reqsAttached.Count == 0)
        {
            return ;
        }
         
        for (int i = 0;i < reqsAttached.Count;i++)
        {
            if (reqsAttached[i].ReqStudentNum == 0)
            {
                ReqStudents.Insert(reqsAttached[i]);
            }
            else
            {
                ReqStudents.Update(reqsAttached[i]);
            } 
        }
    }

    /**
    * Before reqneeded.Delete, this checks to make sure that req is not in use by students.  Used to prompt user.
    */
    public static String inUseBy(long reqNeededNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), reqNeededNum);
        }
         
        String command = "SELECT LName,FName FROM provider,reqstudent " + "WHERE provider.ProvNum=reqstudent.ProvNum " + "AND reqstudent.ReqNeededNum=" + POut.long(reqNeededNum) + " AND reqstudent.DateCompleted > " + POut.date(new DateTime(1880, 1, 1));
        DataTable table = Db.getTable(command);
        String retVal = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal += table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + "\r\n";
        }
        return retVal;
    }

}


/*
		///<summary>Attaches a req to an appointment.  Importantly, it also sets the patNum to match the apt.</summary>
		public static void AttachToApt(int reqStudentNum,int aptNum) {
			string command="SELECT PatNum FROM appointment WHERE AptNum="+POut.PInt(aptNum);
			string patNum=Db.GetCount(command);
			command="UPDATE reqstudent SET AptNum="+POut.PInt(aptNum)
				+", PatNum="+patNum
				+" WHERE ReqStudentNum="+POut.PInt(reqStudentNum);
			Db.NonQ(command);
		}*/