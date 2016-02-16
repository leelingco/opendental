//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.SubtotalPositionEnum;

// right/below
public class SubtotalPosition   
{
    static public SubtotalPositionEnum getStyle(String s, ReportLog rl) throws Exception {
        SubtotalPositionEnum rs = SubtotalPositionEnum.Before;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Before"))
        {
            rs = SubtotalPositionEnum.Before;
        }
        else if (__dummyScrutVar0.equals("After"))
        {
            rs = SubtotalPositionEnum.After;
        }
        else
        {
            rl.logError(4,"Unknown SubtotalPosition '" + s + "'.  Before assumed.");
            rs = SubtotalPositionEnum.Before;
        }  
        return rs;
    }

}


