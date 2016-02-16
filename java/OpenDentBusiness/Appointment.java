//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Appointment;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Appointments can show in the Appointments module, or they can be on the unscheduled list.  An appointment object is also used to store the Planned appointment.  The planned appointment never gets scheduled, but instead gets copied.
*/
public class Appointment  extends TableBase 
{
    /**
    * Primary key.
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
    * If true, then the program will not attempt to reset the user's time pattern and length when adding or removing procedures.
    */
    public boolean TimeLocked = new boolean();
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
    * FK to appointment.AptNum.  A better description of this field would be PlannedAptNum.  Only used to show that this apt is derived from specified planned apt. Otherwise, 0.
    */
    public long NextAptNum = new long();
    /**
    * FK to definition.DefNum.  The definition.Category in the definition table is DefCat.RecallUnschedStatus.  Only used if this is an Unsched or Planned appt.
    */
    public long UnschedStatus = new long();
    /**
    * This is the first appoinment this patient has had at this office.  Somewhat automated.
    */
    public boolean IsNewPatient = new boolean();
    /**
    * A one line summary of all procedures.  Can be used in various reports, Unscheduled list, and Planned appointment tracker.  Not user editable right now, so it doesn't show on the screen.
    */
    public String ProcDescript = new String();
    /**
    * FK to employee.EmployeeNum.  You can assign an assistant to the appointment.
    */
    public long Assistant = new long();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.
    */
    public long ClinicNum = new long();
    /**
    * Set true if this is a hygiene appt.  The only purpose of this flag is to cause the hygiene provider's color to show.  This flag is frequently not set even when it is a hygiene appointment because some offices want the dentist color on the appointments.
    */
    public boolean IsHygiene = new boolean();
    /**
    * Automatically updated by MySQL every time a row is added or changed. Could be changed due to user editing, custom queries or program updates.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * The date and time that the patient checked in.  Date is largely ignored since it should be the same as the appt.
    */
    public DateTime DateTimeArrived = new DateTime();
    /**
    * The date and time that the patient was seated in the chair in the operatory.
    */
    public DateTime DateTimeSeated = new DateTime();
    /**
    * The date and time that the patient got up out of the chair
    */
    public DateTime DateTimeDismissed = new DateTime();
    /**
    * FK to insplan.PlanNum for the primary insurance plan at the time the appointment is set complete. May be 0. We can't tell later which subscriber is involved; only the plan.
    */
    public long InsPlan1 = new long();
    /**
    * FK to insplan.PlanNum for the secoondary insurance plan at the time the appointment is set complete. May be 0. We can't tell later which subscriber is involved; only the plan.
    */
    public long InsPlan2 = new long();
    /**
    * Date and time patient asked to arrive, or minval if patient not asked to arrive at a different time than appt.
    */
    public DateTime DateTimeAskedToArrive = new DateTime();
    /**
    * Stores XML for the procs colors
    */
    public String ProcsColored = new String();
    /**
    * If set to anything but 0, then this will override the graphic color for the appointment.
    */
    public Color ColorOverride = new Color();
    /**
    * Used only for serialization purposes
    */
    public int getColorOverrideXml() throws Exception {
        return ColorOverride.ToArgb();
    }

    public void setColorOverrideXml(int value) throws Exception {
        ColorOverride = Color.FromArgb(value);
    }

    /**
    * Returns a copy of the appointment.
    */
    public Appointment clone() {
        try
        {
            return (Appointment)this.MemberwiseClone();
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


