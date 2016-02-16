//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PlotTypeEnum;
import fyiReporting.RDL.ReportLog;

public class PlotType   
{
    static public PlotTypeEnum getStyle(String s, ReportLog rl) throws Exception {
        PlotTypeEnum pt = PlotTypeEnum.Auto;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Auto"))
        {
            pt = PlotTypeEnum.Auto;
        }
        else if (__dummyScrutVar0.equals("Line"))
        {
            pt = PlotTypeEnum.Line;
        }
        else
        {
            rl.logError(4,"Unknown PlotType '" + s + "'.  Auto assumed.");
            pt = PlotTypeEnum.Auto;
        }  
        return pt;
    }

}


