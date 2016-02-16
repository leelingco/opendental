//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.Userod;

/**
* (User OD since "user" is a reserved word) Users are a completely separate entity from Providers and Employees even though they can be linked.  A usernumber can never be changed, ensuring a permanent way to record database entries and leave an audit trail.  A user can be a provider, employee, or neither.
*/
public class Userod  extends TableBase 
{
    /**
    * Primary key.
    */
    public long UserNum = new long();
    /**
    * .
    */
    public String UserName = new String();
    /**
    * The password hash, not the actual password.  If no password has been entered, then this will be blank.
    */
    public String Password = new String();
    /**
    * FK to usergroup.UserGroupNum.  Every user belongs to exactly one user group.  The usergroup determines the permissions.
    */
    public long UserGroupNum = new long();
    /**
    * FK to employee.EmployeeNum. Cannot be used if provnum is used. Used for timecards to block access by other users.
    */
    public long EmployeeNum = new long();
    /**
    * FK to clinic.ClinicNum.  Has two purposes.  Firstly, it causes new patients to default to this clinic when entered by this user.  Also, if ClinicIsRestricted is set to be true, then it does not allow this user to have access to other clinics. If 0, then user had access to all clinics regardless of ClinicIsRestricted.
    */
    public long ClinicNum = new long();
    /**
    * FK to provider.ProvNum.  Cannot be used if EmployeeNum is used.
    */
    public long ProvNum = new long();
    /**
    * Set true to hide user from login list.
    */
    public boolean IsHidden = new boolean();
    /**
    * FK to tasklist.TaskListNum.  0 if no inbox setup yet.  It is assumed that the TaskList is in the main trunk, but this is not strictly enforced.  User can't delete an attached TaskList, but they could move it.
    */
    public long TaskListInBox = new long();
    /**
    * Defaults to 3 (regular user) unless specified. Helps populates the Anesthetist, Surgeon, Assistant and Circulator dropdowns properly on FormAnestheticRecord///
    */
    public int AnesthProvType = new int();
    /**
    * If set to true, the hide popups button will start out pressed for this user.
    */
    public boolean DefaultHidePopups = new boolean();
    /**
    * Gets set to true if strong passwords are turned on, and this user changes their password to a strong password.  We don't store actual passwords, so this flag is the only way to tell.
    */
    public boolean PasswordIsStrong = new boolean();
    /**
    * Only used when userod.ClinicNum is set to not be zero.  Prevents user from having access to other clinics.
    */
    public boolean ClinicIsRestricted = new boolean();
    public Userod() throws Exception {
    }

    public Userod(long userNum, String userName, String password, long userGroupNum, long employeeNum, long clinicNum, long provNum, boolean isHidden, int anesthProvType, boolean defaultHidePopups, boolean passwordIsStrong) throws Exception {
        UserNum = userNum;
        UserName = userName;
        Password = password;
        UserGroupNum = userGroupNum;
        EmployeeNum = employeeNum;
        ClinicNum = clinicNum;
        ProvNum = provNum;
        IsHidden = isHidden;
        AnesthProvType = anesthProvType;
        DefaultHidePopups = defaultHidePopups;
        PasswordIsStrong = passwordIsStrong;
    }

    /**
    * 
    */
    public Userod copy() throws Exception {
        return (Userod)this.MemberwiseClone();
    }

    public String toString() {
        try
        {
            return UserName;
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


//public class DtoUserodRefresh:DtoQueryBase {
//}