//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Userm;
import OpenDentBusiness.TableBase;

/**
* One username/password for one customer.
*/
public class Userm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.  Just here for compatibility with existing crud.  Always set to 1.
    */
    public long UsermNum = new long();
    /**
    * 
    */
    public String UserName = new String();
    /**
    * Hashed in the same manner as the main program.  UTF-8, md5, base64.  See userods.EncryptPassword().
    */
    public String Password = new String();
    /**
    * 
    */
    public Userm copy() throws Exception {
        return (Userm)this.MemberwiseClone();
    }

}


