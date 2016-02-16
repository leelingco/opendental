//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CreditCard;
import OpenDentBusiness.TableBase;

/**
* One credit card along with any recurring charge information.
*/
public class CreditCard  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CreditCardNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Postal code.
    */
    public String Zip = new String();
    /**
    * Token for X-Charge. Alphanumeric, upper and lower case, about 15 char long.  Passed into Xcharge instead of the actual card number.
    */
    public String XChargeToken = new String();
    /**
    * Credit Card Number.  Will be stored masked: XXXXXXXXXXXX1234.
    */
    public String CCNumberMasked = new String();
    /**
    * Only month and year are used, the day will usually be 1.
    */
    public DateTime CCExpiration = new DateTime();
    /**
    * The order that multiple cards will show.  Zero-based.  First one will be default.
    */
    public int ItemOrder = new int();
    /**
    * Amount set for recurring charges.
    */
    public Double ChargeAmt = new Double();
    /**
    * Start date for recurring charges.
    */
    public DateTime DateStart = new DateTime();
    /**
    * Stop date for recurring charges.
    */
    public DateTime DateStop = new DateTime();
    /**
    * Any notes about the credit card or account goes here.
    */
    public String Note = new String();
    /**
    * FK to payplan.PayPlanNum.
    */
    public long PayPlanNum = new long();
    /**
    * 
    */
    public CreditCard clone() {
        try
        {
            return (CreditCard)this.MemberwiseClone();
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


