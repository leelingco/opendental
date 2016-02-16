//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* XCharge transactions that have been imported into OD.  Used by reconcile tool.  Keeps a history, but no references to these rows from other tables.
*/
public class XChargeTransaction  extends TableBase 
{
    /**
    * Primary key.
    */
    public long XChargeTransactionNum = new long();
    /**
    * Usually "CCPurchase."
    */
    public String TransType = new String();
    /**
    * Amount.
    */
    public double Amount = new double();
    /**
    * Credit card entry method. Usually "Keyed".
    */
    public String CCEntry = new String();
    /**
    * PatNum.
    */
    public long PatNum = new long();
    /**
    * Result: AP for approved, DECLINE for declined.
    */
    public String Result = new String();
    /**
    * ClerkID. Open Dental username with a possible " R" at the end to indicate a recurring charge.
    */
    public String ClerkID = new String();
    /**
    * ResultCode: 000 for approved, 005 for declined.
    */
    public String ResultCode = new String();
    /**
    * Expiration is shown as a four digit number (string since it may contain leading zeros).
    */
    public String Expiration = new String();
    /**
    * VISA, AMEX, MC, DISC etc.
    */
    public String CCType = new String();
    /**
    * Usually looks like 123456XXXXXX7890.
    */
    public String CreditCardNum = new String();
    /**
    * BatchNum.
    */
    public String BatchNum = new String();
    /**
    * ItemNum. Starts at 0001 for each batch.
    */
    public String ItemNum = new String();
    /**
    * Approval code. 6 characters. 72142Z for example.
    */
    public String ApprCode = new String();
    /**
    * TransactionDateTime. Is taken from the Date and Time columns in X-Charge.
    */
    public DateTime TransactionDateTime = new DateTime();
}


