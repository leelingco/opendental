//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.X12Segment;
import OpenDentBusiness.X12Transaction;

/**
* GS/GE combination. Contained within an interchange control combination (ISA/IEA). Contains at least one transaction (ST/SE).
*/
public class X12FunctionalGroup   
{
    /**
    * A collection of X12Transactions
    */
    public List<X12Transaction> Transactions = new List<X12Transaction>();
    /**
    * The segment that identifies this functional group
    */
    public X12Segment Header;
    /**
    * Supply the functional group header(GS) when creating this object.
    */
    public X12FunctionalGroup(X12Segment header) throws Exception {
        Header = header.copy();
    }

}


