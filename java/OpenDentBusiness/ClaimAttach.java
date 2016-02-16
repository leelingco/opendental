//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimAttach;
import OpenDentBusiness.TableBase;

/**
* Keeps track of one image file attached to a claim.  Multiple files can be attached to a claim using this method.
*/
public class ClaimAttach  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClaimAttachNum = new long();
    /**
    * FK to claim.ClaimNum
    */
    public long ClaimNum = new long();
    /**
    * The name of the file that shows on the claim.  For example: tooth2.jpg.
    */
    public String DisplayedFileName = new String();
    /**
    * The actual file is stored in the A-Z folder in EmailAttachments.  (yes, even though it's not actually an email attachment)  The files are named automatically based on Date/time along with a random number.  This ensures that they will be sequential as well as unique.
    */
    public String ActualFileName = new String();
    public ClaimAttach copy() throws Exception {
        return (ClaimAttach)this.MemberwiseClone();
    }

}


