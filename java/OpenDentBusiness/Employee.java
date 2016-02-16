//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* An employee at the dental office.
*/
public class Employee  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmployeeNum = new long();
    /**
    * Employee's last name.
    */
    public String LName = new String();
    /**
    * First name.
    */
    public String FName = new String();
    /**
    * Middle initial or name.
    */
    public String MiddleI = new String();
    /**
    * If hidden, the employee will not show on the list.
    */
    public boolean IsHidden = new boolean();
    /**
    * This is just text used to quickly display the clockstatus.  eg Working,Break,Lunch,Home, etc.
    */
    public String ClockStatus = new String();
    /**
    * The phone extension for the employee.  e.g. 101,102,etc.  This field is only visible for user editing if the pref DockPhonePanelShow is true (1).
    */
    public int PhoneExt = new int();
    /**
    * Used to store the payroll identification number used to generate payroll reports. ADP uses six digit number between 000051 and 999999.
    */
    public String PayrollID = new String();
}


//public string Abbrev;//Not in use
//public bool IsAdmin;//Not in use
//public string TimePeriodType;//Not in use