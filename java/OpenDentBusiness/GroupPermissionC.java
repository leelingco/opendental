//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.GroupPermission;
import OpenDentBusiness.GroupPermissions;

public class GroupPermissionC   
{
    private static GroupPermission[] list = new GroupPermission[]();
    /**
    * A list of all GroupPermissions for all groups.
    */
    public static GroupPermission[] getList() throws Exception {
        if (list == null)
        {
            GroupPermissions.refreshCache();
        }
         
        return list;
    }

    public static void setList(GroupPermission[] value) throws Exception {
        list = value;
    }

}


