//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.TableBase;

/**
* Used in public health.  Programming note: There are many extra fields in common with the screen table, but they are only in this struct and not in the database itself, where that data is stored with the individual screen items. The data in this table is irrelevant in reports.  It is just used to help organize the user interface.
*/
public class ScreenGroup  extends TableBase 
{
    /**
    * Primary key
    */
    public long ScreenGroupNum = new long();
    /**
    * Up to the user.
    */
    public String Description = new String();
    /**
    * Date used to help order the groups.
    */
    public DateTime SGDate = new DateTime();
    /**
    * Not a database column. Used if ProvNum=0.
    */
    public String ProvName = new String();
    /**
    * Not a database column. Foreign key to provider.ProvNum. Can be 0 if not a standard provider.  In that case, a ProvName should be entered.
    */
    public long ProvNum = new long();
    /**
    * Not a database column. See the PlaceOfService enum.
    */
    public PlaceOfService PlaceService = PlaceOfService.Office;
    /**
    * Not a database column. Foreign key to county.CountyName, although it will not crash if key absent.
    */
    public String County = new String();
    /**
    * Not a database column. Foreign key to school.SchoolName, although it will not crash if key absent.
    */
    public String GradeSchool = new String();
}


