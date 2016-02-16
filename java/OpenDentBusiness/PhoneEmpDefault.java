//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpStatusOverride;
import OpenDentBusiness.TableBase;

/**
* This table is not part of the general release.  User would have to add it manually.
*/
public class PhoneEmpDefault  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmployeeNum = new long();
    /**
    * 
    */
    public boolean IsGraphed = new boolean();
    /**
    * 
    */
    public boolean HasColor = new boolean();
    /**
    * Enum:AsteriskRingGroups 0=all, 1=none, 2=backup
    */
    public AsteriskRingGroups RingGroups = AsteriskRingGroups.All;
    /**
    * Just makes management easier.  Not used by the program.
    */
    public String EmpName = new String();
    /**
    * The phone extension for the employee.  e.g. 101,102,etc.  Used to be in the employee table.  This can be changed daily by staff who float from workstation to workstation.  Can be 0 in order to keep two rows from sharing the same extension.
    */
    public int PhoneExt = new int();
    /**
    * Enum:PhoneEmpStatusOverride
    */
    public PhoneEmpStatusOverride StatusOverride = PhoneEmpStatusOverride.None;
    /**
    * Used to be stored as phoneoverride.Explanation.
    */
    public String Notes = new String();
    /**
    * This is used by the cameras.  Only necessary when the ip address doesn't match the 10.10.1.2xx pattern that we normally use.  For example, if Jordan sets this value to JORDANS, then the camera on JORDANS(.186) will send its images to the phone table where extension=104.  The second consequence is that .204 will not send any camera images.  This is used heavily by remote users working from home.  If a staff floats to another .2xx workstation, then this does not need to be set since it will match their changed extension with their current workstation ip address because if follows the normal pattern.  If there are multiple ip addresses, and the camera picks up the wrong one, setting this field can fix it.
    */
    public String ComputerName = new String();
    /**
    * Can only be used by management when handling personnel issues.
    */
    public boolean IsPrivateScreen = new boolean();
    /**
    * Used to launch a task window instead of a commlog window when user clicks on name/phone number on the bottom left.
    */
    public boolean IsTriageOperator = new boolean();
    /**
    * 
    */
    public PhoneEmpDefault clone() {
        try
        {
            return (PhoneEmpDefault)this.MemberwiseClone();
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


