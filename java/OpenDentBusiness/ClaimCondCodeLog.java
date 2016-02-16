//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* There is either one or zero per claim.
*/
public class ClaimCondCodeLog  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClaimCondCodeLogNum = new long();
    /**
    * FK to claim.ClaimNum.
    */
    public long ClaimNum = new long();
    /**
    * Corresponds with condition code 18 on the UB04.
    */
    public String Code0 = new String();
    /**
    * Corresponds with condition code 19 on the UB04.
    */
    public String Code1 = new String();
    /**
    * Corresponds with condition code 20 on the UB04.
    */
    public String Code2 = new String();
    /**
    * Corresponds with condition code 21 on the UB04.
    */
    public String Code3 = new String();
    /**
    * Corresponds with condition code 22 on the UB04.
    */
    public String Code4 = new String();
    /**
    * Corresponds with condition code 23 on the UB04.
    */
    public String Code5 = new String();
    /**
    * Corresponds with condition code 24 on the UB04.
    */
    public String Code6 = new String();
    /**
    * Corresponds with condition code 25 on the UB04.
    */
    public String Code7 = new String();
    /**
    * Corresponds with condition code 26 on the UB04.
    */
    public String Code8 = new String();
    /**
    * Corresponds with condition code 27 on the UB04.
    */
    public String Code9 = new String();
    /**
    * Corresponds with condition code 28 on the UB04.
    */
    public String Code10 = new String();
}


