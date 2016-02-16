//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.X12Segment;

/**
* ST/SE combination.  Containted within functional group (GS/GE).  In claims, there will be one transaction per carrier.
*/
public class X12Transaction   
{
    /**
    * A collection of all the X12Segments for this transaction, in the order they originally appeared.
    */
    public List<X12Segment> Segments = new List<X12Segment>();
    /**
    * The segment that identifies this functional group
    */
    public X12Segment Header;
    /**
    * Supply the transaction header(ST) when creating this object.
    */
    public X12Transaction(X12Segment header) throws Exception {
        Header = header.copy();
    }

    public X12Segment getSegmentByID(String segID) throws Exception {
        for (int i = 0;i < Segments.Count;i++)
        {
            if (StringSupport.equals(Segments[i].SegmentID, segID))
            {
                return Segments[i];
            }
             
        }
        return null;
    }

}


