//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Value codes for institutional 'claims'.  Can have up to 12 per claim.
*/
public class ClaimValCodeLog  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClaimValCodeLogNum = new long();
    /**
    * FK to claim.ClaimNum.
    */
    public long ClaimNum = new long();
    /**
    * Descriptive abbreviation to help place field on form (Ex: "FL55" for field 55).
    */
    public String ClaimField = new String();
    /**
    * Value Code. 2 char.
    */
    public String ValCode = new String();
    /**
    * Value Code Amount.
    */
    public double ValAmount = new double();
    /**
    * Order of Value Code
    */
    public int Ordinal = new int();
}


