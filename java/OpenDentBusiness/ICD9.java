//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.TableBase;

/**
* Other tables generally use the ICD9Code string as their foreign key.  Currently synched to mobile server in a very inefficient manner.  It is implied that these are all ICD9CMs, although that may not be the case in the future.
*/
public class ICD9  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ICD9Num = new long();
    /**
    * Not allowed to edit this column once saved in the database.
    */
    public String ICD9Code = new String();
    /**
    * Description.
    */
    public String Description = new String();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * 
    */
    public ICD9 copy() throws Exception {
        return (ICD9)this.MemberwiseClone();
    }

}


