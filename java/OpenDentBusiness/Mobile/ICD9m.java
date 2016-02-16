//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.ICD9m;
import OpenDentBusiness.TableBase;

public class ICD9m  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
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
    * 
    */
    public ICD9m copy() throws Exception {
        return (ICD9m)this.MemberwiseClone();
    }

}


