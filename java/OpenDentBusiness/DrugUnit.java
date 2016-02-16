//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugUnit;
import OpenDentBusiness.TableBase;

/**
* And other kinds of units.  We will only prefill this list with units needed for the tests.  Users would have to manually add any other units.
*/
public class DrugUnit  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DrugUnitNum = new long();
    /**
    * Example ml, capitalization not critical. Usually entered as lowercase except for L.
    */
    public String UnitIdentifier = new String();
    //VARCHAR(20)/VARCHAR2(20).
    /**
    * Example milliliter.
    */
    public String UnitText = new String();
    /**
    * 
    */
    public DrugUnit copy() throws Exception {
        return (DrugUnit)this.MemberwiseClone();
    }

}


