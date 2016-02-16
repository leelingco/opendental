//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;


/**
* Specify the page size in 1/72 inches units.
*/
public class PdfPageSize   
{
    public PdfPageSize() {
    }

    public int xWidth = new int();
    public int yHeight = new int();
    public int leftMargin = new int();
    public int rightMargin = new int();
    public int topMargin = new int();
    public int bottomMargin = new int();
    public PdfPageSize(int width, int height) throws Exception {
        xWidth = width;
        yHeight = height;
        leftMargin = 0;
        rightMargin = 0;
        topMargin = 0;
        bottomMargin = 0;
    }

    public void setMargins(int L, int T, int R, int B) throws Exception {
        leftMargin = L;
        rightMargin = R;
        topMargin = T;
        bottomMargin = B;
    }

}


