//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;
import fyiReporting.RDL.PdfFontEntry;
import fyiReporting.RDL.PdfFonts;
import fyiReporting.RDL.PdfImageEntry;
import fyiReporting.RDL.PdfPageSize;

/**
* This class represents individual pages within the pdf.
* The contents of the page belong to this class
*/
public class PdfPage  extends PdfBase 
{
    private String page = new String();
    private String pageSize = new String();
    private String fontRef = new String();
    private String imageRef = new String();
    private String resourceDict = new String(), contents = new String();
    private String annotsDict = new String();
    public PdfPage(PdfAnchor pa) throws Exception {
        super(pa);
        resourceDict = null;
        contents = null;
        pageSize = null;
        fontRef = null;
        imageRef = null;
        annotsDict = null;
    }

    /**
    * Create The Pdf page
    */
    public void createPage(int refParent, PdfPageSize pSize) throws Exception {
        pageSize = String.Format("[0 0 {0} {1}]", pSize.xWidth, pSize.yHeight);
        page = String.Format("\r\n{0} 0 obj<</Type /Page/Parent {1} 0 R/Rotate 0/MediaBox {2}/CropBox {2}", this.objectNum, refParent, pageSize);
    }

    public void addHyperlink(float x, float y, float height, float width, String url) throws Exception {
        if (annotsDict == null)
            annotsDict = "\r/Annots [";
         
        annotsDict += String.Format("<</Type /Annot /Subtype /Link /Rect [{0} {1} {2} {3}] /Border [0 0 0] /A <</S /URI /URI ({4})>>>>", x, y, x + width, y - height, url);
    }

    /**
    * Add Font Resources to the pdf page
    */
    public void addResource(PdfFonts fonts, int contentRef) throws Exception {
        for (Object __dummyForeachVar0 : fonts.getFonts().Values)
        {
            PdfFontEntry font = (PdfFontEntry)__dummyForeachVar0;
            fontRef += String.Format("/{0} {1} 0 R", font.font, font.objectNum);
        }
        if (contentRef > 0)
        {
            contents = String.Format("/Contents {0} 0 R", contentRef);
        }
         
    }

    /**
    * Add Image Resources to the pdf page
    */
    public void addResource(PdfImageEntry ie, int contentRef) throws Exception {
        if (imageRef == null || imageRef.IndexOf("/" + ie.name) < 0)
            // only need it once per page
            //				imageRef+=string.Format("/XObject << /{0} {1} 0 R >>",ie.name,ie.objectNum);
            imageRef += String.Format("/{0} {1} 0 R ", ie.name, ie.objectNum);
         
        if (contentRef > 0)
        {
            contents = String.Format("/Contents {0} 0 R", contentRef);
        }
         
    }

    /**
    * Get the Page Dictionary to be written to the file
    * 
    *  @return
    */
    public byte[] getPageDict(long filePos, RefSupport<int> size) throws Exception {
        if (imageRef == null)
            resourceDict = String.Format("/Resources<</Font<<{0}>>/ProcSet[/PDF/Text]>>", fontRef);
        else
            resourceDict = String.Format("/Resources<</Font<<{0}>>/ProcSet[/PDF/Text/ImageB]/XObject <<{1}>>>>", fontRef, imageRef); 
        if (annotsDict != null)
            page += (annotsDict + "]\r");
         
        page += resourceDict + contents + ">>\r\nendobj\r\n";
        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = this.getUTF8Bytes(page,filePos,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

}


