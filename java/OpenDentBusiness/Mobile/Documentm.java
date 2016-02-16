//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Mobile.Documentm;
import OpenDentBusiness.TableBase;

/**
* Links allergies to patients. Patient portal version
*/
public class Documentm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long DocNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * The raw file data encoded as base64.
    */
    public String RawBase64 = new String();
    /**
    * 
    */
    public Documentm copy() throws Exception {
        return (Documentm)this.MemberwiseClone();
    }

}


