//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:17 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DesignXmlDraw;

public class ReportItemSorter  extends IComparer<XmlNode> 
{
    DesignXmlDraw _Draw;
    public ReportItemSorter(DesignXmlDraw d) throws Exception {
        _Draw = d;
    }

    public int compare(XmlNode x, XmlNode y) throws Exception {
        int xi = Convert.ToInt32(_Draw.getElementValue(x,"ZIndex","0"));
        int yi = Convert.ToInt32(_Draw.getElementValue(y,"ZIndex","0"));
        return xi - yi;
    }

}


