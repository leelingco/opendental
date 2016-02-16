//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcGroupItem;
import OpenDentBusiness.TableBase;

/**
* Links Procedures(groupnotes) to Procedures in a 1-n relationship.
*/
public class ProcGroupItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcGroupItemNum = new long();
    /**
    * FK to procedurelog.ProcNum.
    */
    public long ProcNum = new long();
    /**
    * FK to procedurelog.ProcNum.
    */
    public long GroupNum = new long();
    /**
    * 
    */
    public ProcGroupItem clone() {
        try
        {
            return (ProcGroupItem)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


