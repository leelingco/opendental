//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CovSpan;
import OpenDentBusiness.TableBase;

/**
* Always attached to covcats, this describes the span of procedure codes to which the category applies.
*/
public class CovSpan  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CovSpanNum = new long();
    /**
    * FK to covcat.CovCatNum.
    */
    public long CovCatNum = new long();
    /**
    * Lower range of the span.  Does not need to be a valid code.
    */
    public String FromCode = new String();
    /**
    * Upper range of the span.  Does not need to be a valid code.
    */
    public String ToCode = new String();
    /**
    * 
    */
    public CovSpan copy() throws Exception {
        CovSpan c = new CovSpan();
        c.CovSpanNum = CovSpanNum;
        c.CovCatNum = CovCatNum;
        c.FromCode = FromCode;
        c.ToCode = ToCode;
        return c;
    }

}


