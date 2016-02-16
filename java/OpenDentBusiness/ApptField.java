//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptField;
import OpenDentBusiness.TableBase;

/**
* These are custom fields added to appointments and managed by the user.
*/
public class ApptField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ApptFieldNum = new long();
    /**
    * FK to appointment.AptNum
    */
    public long AptNum = new long();
    /**
    * FK to apptfielddef.FieldName.  The full name is shown here for ease of use when running queries.  But the user is only allowed to change fieldNames in the patFieldDef setup window.
    */
    public String FieldName = new String();
    /**
    * Any text that the user types in.  Will later allow some automation.
    */
    public String FieldValue = new String();
    /**
    * 
    */
    public ApptField copy() throws Exception {
        return (ApptField)this.MemberwiseClone();
    }

}


