//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Recallm;
import OpenDentBusiness.TableBase;

public class Recallm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long RecallNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * This is the date that is actually used when doing reports for recall. It will usually be the same as DateDueCalc unless user has changed it. System will only update this field if it is the same as DateDueCalc.  Otherwise, it will be left alone.  Gets cleared along with DateDueCalc when resetting recall.  When setting disabled, this field will also be cleared.  This is the field to use if converting from another software.
    */
    public DateTime DateDue = new DateTime();
    /**
    * Not editable. Previous date that procedures were done to trigger this recall. It is calculated and enforced automatically.  If you want to affect this date, add a procedure to the chart with a status of C, EC, or EO.
    */
    public DateTime DatePrevious = new DateTime();
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
    * Default is 0.  If a positive number is entered, then the family balance must be less in order for this recall to show in the recall list.
    */
    public double DisableUntilBalance = new double();
    /**
    * If a date is entered, then this recall will be disabled until that date.
    */
    public DateTime DisableUntilDate = new DateTime();
    /**
    * Returns a copy of the Recallm.
    */
    public Recallm clone() {
        try
        {
            return (Recallm)this.MemberwiseClone();
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


