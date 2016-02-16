//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.LegendPositionEnum;
import fyiReporting.RDL.ReportLog;

public class LegendPosition   
{
    static public LegendPositionEnum getStyle(String s, ReportLog rl) throws Exception {
        LegendPositionEnum rs = LegendPositionEnum.TopLeft;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("TopLeft"))
        {
            rs = LegendPositionEnum.TopLeft;
        }
        else if (__dummyScrutVar0.equals("TopCenter"))
        {
            rs = LegendPositionEnum.TopCenter;
        }
        else if (__dummyScrutVar0.equals("TopRight"))
        {
            rs = LegendPositionEnum.TopRight;
        }
        else if (__dummyScrutVar0.equals("LeftTop"))
        {
            rs = LegendPositionEnum.LeftTop;
        }
        else if (__dummyScrutVar0.equals("LeftCenter"))
        {
            rs = LegendPositionEnum.LeftCenter;
        }
        else if (__dummyScrutVar0.equals("LeftBottom"))
        {
            rs = LegendPositionEnum.LeftBottom;
        }
        else if (__dummyScrutVar0.equals("RightTop"))
        {
            rs = LegendPositionEnum.RightTop;
        }
        else if (__dummyScrutVar0.equals("RightCenter"))
        {
            rs = LegendPositionEnum.RightCenter;
        }
        else if (__dummyScrutVar0.equals("RightBottom"))
        {
            rs = LegendPositionEnum.RightBottom;
        }
        else if (__dummyScrutVar0.equals("BottomRight"))
        {
            rs = LegendPositionEnum.BottomRight;
        }
        else if (__dummyScrutVar0.equals("BottomCenter"))
        {
            rs = LegendPositionEnum.BottomCenter;
        }
        else if (__dummyScrutVar0.equals("BottomLeft"))
        {
            rs = LegendPositionEnum.BottomLeft;
        }
        else
        {
            rl.logError(4,"Unknown LegendPosition '" + s + "'.  RightTop assumed.");
            rs = LegendPositionEnum.RightTop;
        }            
        return rs;
    }

}


