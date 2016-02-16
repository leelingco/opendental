//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;


// row grouping subtotals
public class MatrixItem   
{
    XmlNode _ReportItem = new XmlNode();
    float _Width = new float();
    float _Height = new float();
    public MatrixItem(XmlNode ri) throws Exception {
        _ReportItem = ri;
    }

    public XmlNode getReportItem() throws Exception {
        return _ReportItem;
    }

    public void setReportItem(XmlNode value) throws Exception {
        _ReportItem = value;
    }

    public float getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(float value) throws Exception {
        _Width = value;
    }

    public float getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(float value) throws Exception {
        _Height = value;
    }

}


