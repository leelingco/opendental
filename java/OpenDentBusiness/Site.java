//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Site;
import OpenDentBusiness.TableBase;

/**
* Generally used by mobile clinics to track the temporary locations where treatment is performed, such as schools, nursing homes, and community centers.  Replaces the old school table.
*/
public class Site  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SiteNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * Notes could include phone, address, contacts, etc.
    */
    public String Note = new String();
    public Site copy() throws Exception {
        return (Site)this.MemberwiseClone();
    }

}


