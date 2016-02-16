//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SecurityLogHash;
import OpenDentBusiness.TableBase;

/**
* Stores hashes of audit logs for detecting alteration.  User not allowed to edit.
*/
public class SecurityLogHash  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SecurityLogHashNum = new long();
    /**
    * FK to securityLog.SecurityLogNum.
    */
    public long SecurityLogNum = new long();
    /**
    * The SHA-256 hash of PermType, UserNum, LogDateTime, LogText, and PatNum, all concatenated together.  This hash has length of 32 bytes encoded as base64.  Used to detect if the entry has been altered outside of Open Dental.
    */
    public String LogHash = new String();
    /**
    * 
    */
    public SecurityLogHash clone() {
        try
        {
            return (SecurityLogHash)this.MemberwiseClone();
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


