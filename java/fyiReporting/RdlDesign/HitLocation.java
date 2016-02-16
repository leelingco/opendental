//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:17 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.HitLocationEnum;

public class HitLocation   
{
    public XmlNode HitNode = new XmlNode();
    public XmlNode HitContainer = new XmlNode();
    public HitLocationEnum HitSpot = HitLocationEnum.Inside;
    public PointF HitRelative = new PointF();
    // x,y location of object relative to first container; ie page header, body, page footer, list, rectangle
    public HitLocation(XmlNode hn, XmlNode hc, HitLocationEnum location, PointF offset) throws Exception {
        HitNode = hn;
        HitContainer = hc;
        HitSpot = location;
        HitRelative = offset;
    }

}


