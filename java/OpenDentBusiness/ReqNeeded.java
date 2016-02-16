//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ReqNeeded;
import OpenDentBusiness.TableBase;

/**
* For Dental Schools.  Requirements needed in order to complete a course.
*/
public class ReqNeeded  extends TableBase 
{
    /**
    * Primary key.
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
    * FK to schoolclass.SchoolClassNum.  Never 0.
    */
    public long SchoolClassNum = new long();
    /**
    * 
    */
    public ReqNeeded copy() throws Exception {
        ReqNeeded r = new ReqNeeded();
        r.ReqNeededNum = ReqNeededNum;
        r.Descript = Descript;
        r.SchoolCourseNum = SchoolCourseNum;
        r.SchoolClassNum = SchoolClassNum;
        return r;
    }

}


