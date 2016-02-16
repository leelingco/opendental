//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EobAttach;
import OpenDentBusiness.TableBase;

/**
* One file attached to an eob (claimpayment).  Multiple files can be attached to an eob using this method.  Order shown will be based on date/time scanned.
*/
public class EobAttach  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EobAttachNum = new long();
    /**
    * FK to claimpayment.ClaimPaymentNum
    */
    public long ClaimPaymentNum = new long();
    /**
    * Date/time created.
    */
    public DateTime DateTCreated = new DateTime();
    /**
    * The file is stored in the A-Z folder in 'EOBs' folder.  This field stores the name of the file.  The files are named automatically based on Date/time along with EobAttachNum for uniqueness.
    */
    public String FileName = new String();
    /**
    * The raw file data encoded as base64.  Only used if there is no AtoZ folder.
    */
    public String RawBase64 = new String();
    public EobAttach copy() throws Exception {
        return (EobAttach)MemberwiseClone();
    }

}


