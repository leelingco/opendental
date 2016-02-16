//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;

/**
* Represents a outline entry
*/
public class PdfOutlineEntry  extends PdfBase 
{
    int _p = new int();
    // page object number
    String _text = new String();
    // text for bookmark
    float _x = new float();
    // x location on page
    float _y = new float();
    // y location on page
    /**
    * Create the image Dictionary
    */
    public PdfOutlineEntry(PdfAnchor pa, int p, String text, float x, float y) throws Exception {
        super(pa);
        _p = p;
        _x = x;
        _y = y;
        _text = text;
    }

    public int getP() throws Exception {
        return _p;
    }

    public String getText() throws Exception {
        return _text;
    }

    public float getX() throws Exception {
        return _x;
    }

    public float getY() throws Exception {
        return _y;
    }

}


