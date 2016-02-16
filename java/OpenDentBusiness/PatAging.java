//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;


/**
* Not a database table.  Just used in billing and finance charges.
*/
public class PatAging   
{
    /**
    * 
    */
    public long PatNum = new long();
    /**
    * 
    */
    public double Bal_0_30 = new double();
    /**
    * 
    */
    public double Bal_31_60 = new double();
    /**
    * 
    */
    public double Bal_61_90 = new double();
    /**
    * 
    */
    public double BalOver90 = new double();
    /**
    * 
    */
    public double InsEst = new double();
    /**
    * 
    */
    public String PatName = new String();
    /**
    * 
    */
    public double BalTotal = new double();
    /**
    * 
    */
    public double AmountDue = new double();
    /**
    * The patient priprov to assign the finance charge to.
    */
    public long PriProv = new long();
    /**
    * The date of the last statement.
    */
    public DateTime DateLastStatement = new DateTime();
    /**
    * FK to defNum.
    */
    public long BillingType = new long();
    /**
    * 
    */
    public double PayPlanDue = new double();
}


