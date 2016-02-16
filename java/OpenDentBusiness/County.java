//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Used in public health.
*/
public class County  extends TableBase 
{
    /**
    * Primary Key.
    */
    public long CountyNum = new long();
    /**
    * Frequently used as the primary key of this table.  But it's allowed to change.  Change is programmatically synchronized.
    */
    public String CountyName = new String();
    /**
    * Optional. Usage varies.
    */
    public String CountyCode = new String();
    /**
    * Not a database field. This is the unaltered CountyName. Used for Update.
    */
    public String OldCountyName = new String();
}


