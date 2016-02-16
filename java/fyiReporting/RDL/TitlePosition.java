//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.TitlePositionEnum;

public class TitlePosition   
{
    static public TitlePositionEnum getStyle(String s, ReportLog rl) throws Exception {
        TitlePositionEnum rs = TitlePositionEnum.Center;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Center"))
        {
            rs = TitlePositionEnum.Center;
        }
        else if (__dummyScrutVar0.equals("Near"))
        {
            rs = TitlePositionEnum.Near;
        }
        else if (__dummyScrutVar0.equals("Far"))
        {
            rs = TitlePositionEnum.Far;
        }
        else
        {
            rl.logError(4,"Unknown TitlePosition '" + s + "'.  Center assumed.");
            rs = TitlePositionEnum.Center;
        }   
        return rs;
    }

}


