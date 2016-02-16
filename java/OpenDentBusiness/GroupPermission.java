//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.GroupPermission;
import OpenDentBusiness.TableBase;

/**
* Every user group has certain permissions.  This defines a permission for a group.  The absense of permission would cause that row to be deleted from this table.
*/
public class GroupPermission  extends TableBase 
{
    /**
    * Primary key.
    */
    public long GroupPermNum = new long();
    /**
    * Only granted permission if newer than this date.  Can be Minimum (01-01-0001) to always grant permission.
    */
    public DateTime NewerDate = new DateTime();
    /**
    * Can be 0 to always grant permission.  Otherwise, only granted permission if item is newer than the given number of days.  1 would mean only if entered today.
    */
    public int NewerDays = new int();
    /**
    * FK to usergroup.UserGroupNum.  The user group for which this permission is granted.  If not authorized, then this groupPermission will have been deleted.
    */
    public long UserGroupNum = new long();
    /**
    * Enum:Permissions
    */
    public OpenDentBusiness.Permissions PermType = OpenDentBusiness.Permissions.None;
    /**
    * 
    */
    public GroupPermission copy() throws Exception {
        return (GroupPermission)this.MemberwiseClone();
    }

}


