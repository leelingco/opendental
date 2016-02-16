//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.UserGroup;

/**
* A group of users.  Security permissions are determined by the usergroup of a user.
*/
public class UserGroup  extends TableBase 
{
    /**
    * Primary key.
    */
    public long UserGroupNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * 
    */
    public UserGroup copy() throws Exception {
        UserGroup u = new UserGroup();
        u.UserGroupNum = UserGroupNum;
        u.Description = Description;
        return u;
    }

}


