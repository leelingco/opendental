//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Phone;
import OpenDentBusiness.TableBase;

/**
* This table is not part of the general release.  User would have to add it manually.  All schema changes are done directly on our live database as needed.
*/
public class PhoneGraph  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PhoneGraphNum = new long();
    /**
    * FK to employee.EmployeeNum.
    */
    public long EmployeeNum = new long();
    /**
    * Ammends PhoneEmpDefault.IsGraphed for the given DateEntry
    */
    public boolean IsGraphed = new boolean();
    /**
    * Date pertaining to this entry.
    */
    public DateTime DateEntry = new DateTime();
    public Phone copy() throws Exception {
        return (Phone)this.MemberwiseClone();
    }

}


