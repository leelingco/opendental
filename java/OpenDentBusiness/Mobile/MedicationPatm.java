//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.MedicationPatm;
import OpenDentBusiness.TableBase;

/**
* Links medications to patients. Patient portal version
*/
public class MedicationPatm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long MedicationPatNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to medication.MedicationNum.
    */
    public long MedicationNum = new long();
    /**
    * Medication notes specific to this patient.
    */
    public String PatNote = new String();
    /**
    * Date that the medication was started.  Can be minval if unknown.
    */
    public DateTime DateStart = new DateTime();
    /**
    * Date that the medication was stopped.  Can be minval if unknown.  If not minval, then this medication is "discontinued".
    */
    public DateTime DateStop = new DateTime();
    /**
    * 
    */
    public MedicationPatm copy() throws Exception {
        return (MedicationPatm)this.MemberwiseClone();
    }

}


