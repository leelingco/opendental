//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;

/**
* Represents a font entry used in a pdf page
*/
public class PdfFontEntry  extends PdfBase 
{
    public String fontDict = new String();
    public String font = new String();
    /**
    * Create the font Dictionary
    */
    public PdfFontEntry(PdfAnchor pa, String fontName, String fontFace) throws Exception {
        super(pa);
        font = fontName;
        fontDict = String.Format("\r\n{0} 0 obj<</Type/Font/Name /{1}/BaseFont/{2}/Subtype/Type1/Encoding /WinAnsiEncoding>>\tendobj\t", this.objectNum, fontName, fontFace);
    }

    /**
    * Get the font entry to be written to the file
    * 
    *  @return
    */
    public byte[] getFontDict(long filePos, RefSupport<int> size) throws Exception {
        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = this.getUTF8Bytes(fontDict,filePos,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

}


