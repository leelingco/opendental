//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SchoolClass;
import OpenDentBusiness.TableBase;

/**
* Used in dental schools.  eg. Dental 2009 or Hygiene 2007.
*/
public class SchoolClass  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SchoolClassNum = new long();
    /**
    * The year this class will graduate
    */
    public int GradYear = new int();
    /**
    * Description of this class. eg Dental or Hygiene
    */
    public String Descript = new String();
    /**
    * 
    */
    public SchoolClass copy() throws Exception {
        SchoolClass sc = new SchoolClass();
        sc.SchoolClassNum = SchoolClassNum;
        sc.GradYear = GradYear;
        sc.Descript = Descript;
        return sc;
    }

}


