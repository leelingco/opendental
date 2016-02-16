//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EtransMessageText;
import OpenDentBusiness.TableBase;

/**
* Each row is big.  The entire X12 message text is stored here, since it can be the same for multiple etrans objects, and since the messages can be so big.
*/
public class EtransMessageText  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EtransMessageTextNum = new long();
    /**
    * The entire message text, including carriage returns.
    */
    public String MessageText = new String();
    /**
    * 
    */
    public EtransMessageText copy() throws Exception {
        return (EtransMessageText)this.MemberwiseClone();
    }

}


