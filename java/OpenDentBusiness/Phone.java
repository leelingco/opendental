//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Phone;
import OpenDentBusiness.TableBase;

/**
* This table is not part of the general release.  User would have to add it manually.  All schema changes are done directly on our live database as needed.
*/
public class Phone  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PhoneNum = new long();
    /**
    * 
    */
    public int Extension = new int();
    /**
    * 
    */
    public String EmployeeName = new String();
    /**
    * This enum is stored in the db as a string, so it needs special handling.  In phoneTrackingServer initialize, this value is pulled from employee.ClockStatus as Home, Lunch, Break, or Working(which gets converted to Available).  After that, the phone server uses those 4 in addition to WrapUp, Off, Training, TeamAssist, OfflineAssist, Backup, and None(which is displayed as an empty string).  The main program sets Unavailable sometimes, and pulls from employee.ClockStatus sometimes.
    */
    public ClockStatusEnum ClockStatus = ClockStatusEnum.None;
    /**
    * Either blank or 'In use'
    */
    public String Description = new String();
    /**
    * 
    */
    public Color ColorBar = new Color();
    /**
    * 
    */
    public Color ColorText = new Color();
    /**
    * FK to employee.EmployeeNum.
    */
    public long EmployeeNum = new long();
    /**
    * The phone number or name of customer.
    */
    public String CustomerNumber = new String();
    /**
    * Blank or 'in' or 'out'.
    */
    public String InOrOut = new String();
    /**
    * FK to patient.PatNum.  The customer.
    */
    public long PatNum = new long();
    /**
    * The date/time that the phonecall started.  Used to calculate how long user has been on phone.
    */
    public DateTime DateTimeStart = new DateTime();
    /**
    * The base64 representation of a bitmap.
    */
    public String WebCamImage = new String();
    /**
    * Full path to the most recent screenshot.
    */
    public String ScreenshotPath = new String();
    /**
    * The base64 thumbnail of the most recent screenshot.
    */
    public String ScreenshotImage = new String();
    /**
    * Always set to the phone number of the caller.
    */
    public String CustomerNumberRaw = new String();
    /**
    * A copy of DateTimeStart made when a call has ended.  Gets set to 0001-01-01 after the 30 second wrap up thread has run.
    */
    public DateTime LastCallTimeStart = new DateTime();
    public Phone copy() throws Exception {
        return (Phone)this.MemberwiseClone();
    }

    public String toString() {
        try
        {
            return this.EmployeeName + " - " + this.Extension;
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


