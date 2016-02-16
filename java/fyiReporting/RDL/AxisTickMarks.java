//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.AxisTickMarksEnum;
import fyiReporting.RDL.ReportLog;

public class AxisTickMarks   
{
    static public AxisTickMarksEnum getStyle(String s, ReportLog rl) throws Exception {
        AxisTickMarksEnum rs = AxisTickMarksEnum.None;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("None"))
        {
            rs = AxisTickMarksEnum.None;
        }
        else if (__dummyScrutVar0.equals("Inside"))
        {
            rs = AxisTickMarksEnum.Inside;
        }
        else if (__dummyScrutVar0.equals("Outside"))
        {
            rs = AxisTickMarksEnum.Outside;
        }
        else if (__dummyScrutVar0.equals("Cross"))
        {
            rs = AxisTickMarksEnum.Cross;
        }
        else
        {
            rl.logError(4,"Unknown Axis Tick Mark '" + s + "'.  None assumed.");
            rs = AxisTickMarksEnum.None;
        }    
        return rs;
    }

}


