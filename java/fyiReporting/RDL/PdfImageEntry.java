//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;
import fyiReporting.RDL.PdfPage;

/**
* Represents a image entry used in a pdf page
*/
public class PdfImageEntry  extends PdfBase 
{
    public String name = new String();
    public ImageFormat imf = new ImageFormat();
    public byte[] ba = new byte[]();
    public String imgDict = new String();
    /**
    * Create the image Dictionary
    */
    public PdfImageEntry(PdfAnchor pa, PdfPage p, int contentRef, String nm, ImageFormat imgf, byte[] im, int width, int height) throws Exception {
        super(pa);
        name = nm;
        imf = imgf;
        ba = im;
        String filter = new String();
        if (imf == ImageFormat.Jpeg)
            filter = "/DCTDecode";
        else if (imf == ImageFormat.Png)
            // TODO: this still doesn't work
            filter = "/FlateDecode /DecodeParms <</Predictor 15 /Colors 3 /BitsPerComponent 8 /Columns 80>>";
        else if (imf == ImageFormat.Gif)
            // TODO: this still doesn't work
            filter = "/LZWDecode";
        else
            filter = "";   
        imgDict = String.Format("\r\n{0} 0 obj<</Type/XObject/Subtype /Image /Width {1} /Height {2} /ColorSpace /DeviceRGB /BitsPerComponent 8 /Length {3} /Filter {4} >>\nstream\n", this.objectNum, width, height, ba.Length, filter);
        p.addResource(this,contentRef);
    }

}


