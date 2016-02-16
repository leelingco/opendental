//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Used to link images to an EHR lab.
*/
public class EhrLabImage  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabImageNum = new long();
    /**
    * FK to ehrlab.EhrLabNum.
    */
    public long EhrLabNum = new long();
    /**
    * FK to document.DocNum.  Will be -1 to indicate that lab is expecting image results.
    */
    public long DocNum = new long();
}


