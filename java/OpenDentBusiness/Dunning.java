//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Dunning;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.YN;

/**
* A message that will show on certain patient statements when printing bills.  Criteria must be met in order for the dunning message to show.
*/
public class Dunning  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DunningNum = new long();
    /**
    * The actual dunning message that will go on the patient bill.
    */
    public String DunMessage = new String();
    /**
    * FK to definition.DefNum.
    */
    public long BillingType = new long();
    /**
    * Program forces only 0,30,60,or 90.
    */
    public byte AgeAccount = new byte();
    /**
    * Enum:YN Set Y to only show if insurance is pending.
    */
    public YN InsIsPending = YN.Unknown;
    /**
    * A message that will be copied to the NoteBold field of the Statement.
    */
    public String MessageBold = new String();
    /**
    * An override for the default email subject.
    */
    public String EmailSubject = new String();
    /**
    * An override for the default email body. Limit in db: 16M char.
    */
    public String EmailBody = new String();
    /**
    * Returns a copy of this Dunning.
    */
    public Dunning copy() throws Exception {
        return (Dunning)this.MemberwiseClone();
    }

}


