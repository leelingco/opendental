//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Interval;
import OpenDentBusiness.Recall;
import OpenDentBusiness.TableBase;

/**
* A patient can only have one recall object per type.  The recall table stores a few dates that must be kept synchronized with other information in the database.  This is difficult.  Anytime one of the following items changes, things need to be synchronized: procedurecode.SetRecall, any procedurelog change for a patient (procs added, deleted, completed, status changed, date changed, etc), patient status changed.  There are expected to be a few bugs in the synchronization logic, so anytime a patient's recall is opened, it will also update.
* 
* During synchronization, the program will frequently alter DateDueCalc, DateDue, and DatePrevious based on trigger procs.  The system will also add and delete recalls as necessary. But it will not delete a recall unless all values are default and there is no useful information.  When a user tries to delete a recall, they will only be successful if the trigger conditions do not apply.  Otherwise, they will have to disable the recall instead.
*/
public class Recall  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RecallNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Not editable.  The calculated date due. Generated by the program and subject to change anytime the conditions change. It can be blank (0001-01-01) if no appropriate triggers.
    */
    public DateTime DateDueCalc = new DateTime();
    /**
    * This is the date that is actually used when doing reports for recall. It will usually be the same as DateDueCalc unless user has changed it. System will only update this field if it is the same as DateDueCalc.  Otherwise, it will be left alone.  Gets cleared along with DateDueCalc when resetting recall.  When setting disabled, this field will also be cleared.  This is the field to use if converting from another software.
    */
    public DateTime DateDue = new DateTime();
    /**
    * Not editable. Previous date that procedures were done to trigger this recall. It is calculated and enforced automatically.  If you want to affect this date, add a procedure to the chart with a status of C, EC, or EO.
    */
    public DateTime DatePrevious = new DateTime();
    /**
    * The interval between recalls.  The Interval struct combines years, months, weeks, and days into a single integer value.
    */
    public Interval RecallInterval = new Interval();
    /**
    * FK to definition.DefNum, or 0 for none.
    */
    public long RecallStatus = new long();
    /**
    * An administrative note for staff use.
    */
    public String Note = new String();
    /**
    * If true, this recall type will be disabled (there's only one type right now). This is usually used rather than deleting the recall type from the patient because the program must enforce the trigger conditions for all patients.
    */
    public boolean IsDisabled = new boolean();
    /**
    * Last datetime that this row was inserted or updated.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK to recalltype.RecallTypeNum.
    */
    public long RecallTypeNum = new long();
    /**
    * Default is 0.  If a positive number is entered, then the family balance must be less in order for this recall to show in the recall list.
    */
    public double DisableUntilBalance = new double();
    /**
    * If a date is entered, then this recall will be disabled until that date.
    */
    public DateTime DisableUntilDate = new DateTime();
    /**
    * This will only have a value if a recall is scheduled.
    */
    public DateTime DateScheduled = new DateTime();
    /**
    * Returns a copy of this Recall.
    */
    public Recall copy() throws Exception {
        return (Recall)this.MemberwiseClone();
    }

}

