//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RecallTrigger;
import OpenDentBusiness.TableBase;

/**
* Links one procedurecode to one recalltype.  The presence of this trigger is used when determining DatePrevious in the recall table.
*/
public class RecallTrigger  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RecallTriggerNum = new long();
    /**
    * FK to recalltype.RecallTypeNum
    */
    public long RecallTypeNum = new long();
    /**
    * FK to procedurecode.CodeNum
    */
    public long CodeNum = new long();
    public RecallTrigger copy() throws Exception {
        return (RecallTrigger)this.MemberwiseClone();
    }

}


