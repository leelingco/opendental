//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.RxPatm;
import OpenDentBusiness.TableBase;

/**
* One Rx for one patient.
*/
public class RxPatm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long RxNum = new long();
    /**
    * FK to patientm.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date of Rx.
    */
    public DateTime RxDate = new DateTime();
    /**
    * Drug name.
    */
    public String Drug = new String();
    /**
    * Directions.
    */
    public String Sig = new String();
    /**
    * Amount to dispense.
    */
    public String Disp = new String();
    /**
    * Number of refills.
    */
    public String Refills = new String();
    /**
    * FK to providerm.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * 
    */
    public RxPatm copy() throws Exception {
        return (RxPatm)this.MemberwiseClone();
    }

}


