//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ObjectList;

/**
* Holds the Byte offsets of the objects used in the Pdf Document
*/
public class PdfAnchor   
{
    public List<ObjectList> offsets = new List<ObjectList>();
    public int current = new int();
    public boolean CanCompress = new boolean();
    public PdfAnchor(boolean bCompress) throws Exception {
        offsets = new List<ObjectList>();
        current = 0;
        CanCompress = bCompress;
    }

    public void reset() throws Exception {
        offsets.Clear();
        current = 0;
    }

}


