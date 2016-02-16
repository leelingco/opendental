//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Laboratory;
import OpenDentBusiness.TableBase;

/**
* A dental laboratory. Will be attached to lab cases.
*/
public class Laboratory  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LaboratoryNum = new long();
    /**
    * Description of lab.
    */
    public String Description = new String();
    /**
    * Freeform text includes punctuation.
    */
    public String Phone = new String();
    /**
    * Any notes.  No practical limit to amount of text.
    */
    public String Notes = new String();
    /**
    * FK to sheetdef.SheetDefNum.  Lab slips can be set for individual laboratories.  If zero, then the default internal lab slip will be used instead of a custom lab slip.
    */
    public long Slip = new long();
    /**
    * .
    */
    public String Address = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * .
    */
    public String State = new String();
    /**
    * .
    */
    public String Zip = new String();
    /**
    * .
    */
    public String Email = new String();
    /**
    * .
    */
    public String WirelessPhone = new String();
    public Laboratory copy() throws Exception {
        return (Laboratory)this.MemberwiseClone();
    }

}


