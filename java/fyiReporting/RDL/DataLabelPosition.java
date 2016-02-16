//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataLabelPositionEnum;
import fyiReporting.RDL.ReportLog;

public class DataLabelPosition   
{
    static public DataLabelPositionEnum getStyle(String s, ReportLog rl) throws Exception {
        DataLabelPositionEnum dlp = DataLabelPositionEnum.Auto;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Auto"))
        {
            dlp = DataLabelPositionEnum.Auto;
        }
        else if (__dummyScrutVar0.equals("Top"))
        {
            dlp = DataLabelPositionEnum.Top;
        }
        else if (__dummyScrutVar0.equals("TopLeft"))
        {
            dlp = DataLabelPositionEnum.TopLeft;
        }
        else if (__dummyScrutVar0.equals("TopRight"))
        {
            dlp = DataLabelPositionEnum.TopRight;
        }
        else if (__dummyScrutVar0.equals("Left"))
        {
            dlp = DataLabelPositionEnum.Left;
        }
        else if (__dummyScrutVar0.equals("Center"))
        {
            dlp = DataLabelPositionEnum.Center;
        }
        else if (__dummyScrutVar0.equals("Right"))
        {
            dlp = DataLabelPositionEnum.Right;
        }
        else if (__dummyScrutVar0.equals("BottomRight"))
        {
            dlp = DataLabelPositionEnum.BottomRight;
        }
        else if (__dummyScrutVar0.equals("Bottom"))
        {
            dlp = DataLabelPositionEnum.Bottom;
        }
        else if (__dummyScrutVar0.equals("BottomLeft"))
        {
            dlp = DataLabelPositionEnum.BottomLeft;
        }
        else
        {
            rl.logError(4,"Unknown DataLablePosition '" + s + "'.  Auto assumed.");
            dlp = DataLabelPositionEnum.Auto;
        }          
        return dlp;
    }

}


