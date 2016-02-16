//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Mobile.Appointmentm;
import OpenDentBusiness.TableBase;

/**
* Appointments can show in the Appointments module, or they can be on the unscheduled list.  An appointment object is also used to store the Planned appointment.  The planned appointment never gets scheduled, but instead gets copied.
*/
public class Appointmentm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long AptNum = new long();
    /**
    * FK to patient.PatNum.  The patient that the appointment is for.
    */
    public long PatNum = new long();
    /**
    * Enum:ApptStatus .
    */
    public ApptStatus AptStatus = ApptStatus.None;
    /**
    * Time pattern, X for Dr time, / for assist time. Stored in 5 minute increments.  Converted as needed to 10 or 15 minute representations for display.
    */
    public String Pattern = new String();
    /**
    * FK to definition.DefNum.  This field can also be used to show patient arrived, in chair, etc.  The Category column in the definition table is DefCat.ApptConfirmed.
    */
    public long Confirmed = new long();
    /**
    * FK to operatory.OperatoryNum.
    */
    public long Op = new long();
    /**
    * Note.
    */
    public String Note = new String();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to provider.ProvNum.  Optional.  Only used if a hygienist is assigned to this appt.
    */
    public long ProvHyg = new long();
    /**
    * Appointment Date and time.  If you need just the date or time for an SQL query, you can use DATE(AptDateTime) and TIME(AptDateTime) in your query.
    */
    public DateTime AptDateTime = new DateTime();
    /**
    * This is the first appoinment this patient has had at this office.  Somewhat automated.
    */
    public boolean IsNewPatient = new boolean();
    /**
    * A one line summary of all procedures.  Can be used in various reports, Unscheduled list, and Planned appointment tracker.  Not user editable right now, so it doesn't show on the screen.
    */
    public String ProcDescript = new String();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.
    */
    public long ClinicNum = new long();
    /**
    * Set true if this is a hygiene appt.  The hygiene provider's color will show.
    */
    public boolean IsHygiene = new boolean();
    /**
    * Returns a copy of the appointment.
    */
    public Appointmentm clone() {
        try
        {
            return (Appointmentm)this.MemberwiseClone();
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


