//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;


/**
* Holds a list of claims to show in the Claim Pay Edit window.  Not an actual database table.
*/
public class ClaimPaySplit   
{
    /**
    * 
    */
    public long ClaimNum = new long();
    /**
    * 
    */
    public String PatName = new String();
    /**
    * 
    */
    public long PatNum = new long();
    /**
    * 
    */
    public String Carrier = new String();
    /**
    * 
    */
    public DateTime DateClaim = new DateTime();
    /**
    * 
    */
    public String ProvAbbr = new String();
    /**
    * 
    */
    public double FeeBilled = new double();
    /**
    * 
    */
    public double InsPayAmt = new double();
    /**
    * 
    */
    public long ClaimPaymentNum = new long();
    /**
    * 1-based
    */
    public int PaymentRow = new int();
    /**
    * 
    */
    public String ClinicDesc = new String();
}


