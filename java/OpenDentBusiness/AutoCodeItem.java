//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.TableBase;

/**
* Corresponds to the autocodeitem table in the database.  There are multiple AutoCodeItems for a given AutoCode.  Each Item has one ADA code.
*/
public class AutoCodeItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AutoCodeItemNum = new long();
    /**
    * FK to autocode.AutoCodeNum
    */
    public long AutoCodeNum = new long();
    /**
    * Do not use
    */
    public String OldCode = new String();
    /**
    * FK to procedurecode.CodeNum
    */
    public long CodeNum = new long();
    /**
    * Only used in the validation section when closing FormAutoCodeEdit.  Will normally be empty.
    */
    public List<AutoCodeCond> ListConditions = new List<AutoCodeCond>();
}


