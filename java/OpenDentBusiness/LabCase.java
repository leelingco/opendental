//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.TableBase;

/**
* A lab case.
*/
public class LabCase  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LabCaseNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to laboratory.LaboratoryNum. The lab that the case gets sent to.  Required.
    */
    public long LaboratoryNum = new long();
    /**
    * FK to appointment.AptNum.  This is how a lab case is attached to a scheduled appointment. 1:1 relationship for now.  Only one labcase per appointment, and (obviously) only one appointment per labcase.  Labcase can exist without being attached to any appointments at all, making this zero.
    */
    public long AptNum = new long();
    /**
    * FK to appointment.AptNum.  This is how a lab case is attached to a planned appointment in addition to the scheduled appointment.
    */
    public long PlannedAptNum = new long();
    /**
    * The due date that is put on the labslip.  NOT when you really need the labcase back, which is usually a day or two later and is the date of the appointment this case is attached to.
    */
    public DateTime DateTimeDue = new DateTime();
    /**
    * When this lab case was created. User can edit.
    */
    public DateTime DateTimeCreated = new DateTime();
    /**
    * Time that it actually went out to the lab.
    */
    public DateTime DateTimeSent = new DateTime();
    /**
    * Date/time received back from the lab.  If this is filled, then the case is considered received.
    */
    public DateTime DateTimeRecd = new DateTime();
    /**
    * Date/time that quality was checked.  It is now completely ready for the patient.
    */
    public DateTime DateTimeChecked = new DateTime();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * The text instructions for this labcase.
    */
    public String Instructions = new String();
    /**
    * There is no UI built yet for this field.  Plugins might be making use of this field.
    */
    public double LabFee = new double();
    public LabCase copy() throws Exception {
        return (LabCase)this.MemberwiseClone();
    }

}


