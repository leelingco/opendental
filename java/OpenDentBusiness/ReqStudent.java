//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ReqStudent;
import OpenDentBusiness.TableBase;

/**
* For Dental Schools.  The purpose of this table changed significantly in version 4.5.  This now only stores completed requirements.  There can be multiple completed requirements of each ReqNeededNum.  No need to synchronize any longer.
*/
public class ReqStudent  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ReqStudentNum = new long();
    /**
    * FK to reqneeded.ReqNeededNum.
    */
    public long ReqNeededNum = new long();
    /**
    * .
    */
    public String Descript = new String();
    /**
    * FK to schoolcourse.SchoolCourseNum.  Never 0.
    */
    public long SchoolCourseNum = new long();
    /**
    * FK to provider.ProvNum.  The student.  Never 0.
    */
    public long ProvNum = new long();
    /**
    * FK to appointment.AptNum.
    */
    public long AptNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to provider.ProvNum
    */
    public long InstructorNum = new long();
    /**
    * The date that the requirement was completed.
    */
    public DateTime DateCompleted = new DateTime();
    /**
    * 
    */
    public ReqStudent copy() throws Exception {
        ReqStudent r = new ReqStudent();
        r.ReqStudentNum = ReqStudentNum;
        r.ReqNeededNum = ReqNeededNum;
        r.Descript = Descript;
        r.SchoolCourseNum = SchoolCourseNum;
        r.ProvNum = ProvNum;
        r.AptNum = AptNum;
        r.PatNum = PatNum;
        r.InstructorNum = InstructorNum;
        r.DateCompleted = DateCompleted;
        return r;
    }

}


