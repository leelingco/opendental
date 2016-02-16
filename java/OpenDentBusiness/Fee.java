//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Fee;
import OpenDentBusiness.TableBase;

/**
* There is one entry in this table for each fee for a single procedurecode.  So if there are 5 different fees stored for one procedurecode, then there will be five entries here.
*/
public class Fee  extends TableBase 
{
    /**
    * Primary key.
    */
    public long FeeNum = new long();
    /**
    * The amount usually charged.  If an amount is unknown, then the entire Fee entry is deleted from the database.  The absence of a fee is sometimes shown in the user interface as a blank entry, and sometimes as 0.00.
    */
    public double Amount = new double();
    /**
    * Do not use.
    */
    public String OldCode = new String();
    /**
    * FK to feesched.FeeSchedNum.
    */
    public long FeeSched = new long();
    /**
    * Not used.
    */
    public boolean UseDefaultFee = new boolean();
    /**
    * Not used.
    */
    public boolean UseDefaultCov = new boolean();
    /**
    * FK to procedurecode.CodeNum.
    */
    public long CodeNum = new long();
    /**
    * 
    */
    public Fee copy() throws Exception {
        return (Fee)MemberwiseClone();
    }

}


