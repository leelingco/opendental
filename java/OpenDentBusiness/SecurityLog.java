//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Stores an ongoing record of database activity for security purposes.  User not allowed to edit.
*/
public class SecurityLog  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SecurityLogNum = new long();
    /**
    * Enum:Permissions
    */
    public OpenDentBusiness.Permissions PermType = OpenDentBusiness.Permissions.None;
    /**
    * FK to user.UserNum
    */
    public long UserNum = new long();
    /**
    * The date and time of the entry.  It's value is set when inserting and can never change.  Even if a user changes the date on their ocmputer, this remains accurate because it uses server time.
    */
    public DateTime LogDateTime = new DateTime();
    /**
    * The description of exactly what was done. Varies by permission type.
    */
    public String LogText = new String();
    /**
    * FK to patient.PatNum.  Can be 0 if not applicable.
    */
    public long PatNum = new long();
    /**
    * .
    */
    public String CompName = new String();
    /**
    * A foreign key to a table associated with the PermType.  0 indicates not in use.  This is typically used for objects that have specific audit trails so that users can see all audit entries related to a particular object.  For the patient portal, it is used to indicate logs created on behalf of other patients.  It's uses include:  AptNum with PermType AppointmentCreate, AppointmentEdit, or AppointmentMove tracks all appointment logs for a particular appointment.  CodeNum with PermType ProcFeeEdit currently only tracks fee changes.  PatNum with PermType PatientPortal represents an entry that a patient made on behalf of another patient.  The PatNum column will represent the patient who is taking the action.  PlanNum with PermType InsPlanChangeCarrierName tracks carrier name changes.
    */
    public long FKey = new long();
    /**
    * PatNum-NameLF
    */
    public String PatientName = new String();
    /**
    * Existing LogHash from SecurityLogHash table
    */
    public String LogHash = new String();
}


