//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SchoolCourse;
import OpenDentBusiness.TableBase;

/**
* Used in dental schools.  eg OP 732 Operative Dentistry Clinic II.
*/
public class SchoolCourse  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SchoolCourseNum = new long();
    /**
    * Alphanumeric.  eg PEDO 732.
    */
    public String CourseID = new String();
    /**
    * eg: Pediatric Dentistry Clinic II
    */
    public String Descript = new String();
    /**
    * 
    */
    public SchoolCourse copy() throws Exception {
        SchoolCourse sc = new SchoolCourse();
        sc.SchoolCourseNum = SchoolCourseNum;
        sc.CourseID = CourseID;
        sc.Descript = Descript;
        return sc;
    }

}


