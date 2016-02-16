//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MarkerTypeEnum;
import fyiReporting.RDL.ReportLog;

public class MarkerType   
{
    static public MarkerTypeEnum getStyle(String s, ReportLog rl) throws Exception {
        MarkerTypeEnum rs = MarkerTypeEnum.None;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("None"))
        {
            rs = MarkerTypeEnum.None;
        }
        else if (__dummyScrutVar0.equals("Square"))
        {
            rs = MarkerTypeEnum.Square;
        }
        else if (__dummyScrutVar0.equals("Circle"))
        {
            rs = MarkerTypeEnum.Circle;
        }
        else if (__dummyScrutVar0.equals("Diamond"))
        {
            rs = MarkerTypeEnum.Diamond;
        }
        else if (__dummyScrutVar0.equals("Triangle"))
        {
            rs = MarkerTypeEnum.Triangle;
        }
        else if (__dummyScrutVar0.equals("Cross"))
        {
            rs = MarkerTypeEnum.Cross;
        }
        else if (__dummyScrutVar0.equals("Auto"))
        {
            rs = MarkerTypeEnum.Auto;
        }
        else
        {
            rl.logError(4,"Unknown MarkerType '" + s + "'.  None assumed.");
            rs = MarkerTypeEnum.None;
        }       
        return rs;
    }

}


