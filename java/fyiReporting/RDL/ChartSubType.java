//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.ReportLog;

public class ChartSubType   
{
    static public ChartSubTypeEnum getStyle(String s, ReportLog rl) throws Exception {
        ChartSubTypeEnum st = ChartSubTypeEnum.Plain;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Plain"))
        {
            st = ChartSubTypeEnum.Plain;
        }
        else if (__dummyScrutVar0.equals("Stacked"))
        {
            st = ChartSubTypeEnum.Stacked;
        }
        else if (__dummyScrutVar0.equals("PercentStacked"))
        {
            st = ChartSubTypeEnum.PercentStacked;
        }
        else if (__dummyScrutVar0.equals("Smooth"))
        {
            st = ChartSubTypeEnum.Smooth;
        }
        else if (__dummyScrutVar0.equals("Exploded"))
        {
            st = ChartSubTypeEnum.Exploded;
        }
        else if (__dummyScrutVar0.equals("Line"))
        {
            st = ChartSubTypeEnum.Line;
        }
        else if (__dummyScrutVar0.equals("SmoothLine"))
        {
            st = ChartSubTypeEnum.SmoothLine;
        }
        else if (__dummyScrutVar0.equals("HighLowClose"))
        {
            st = ChartSubTypeEnum.HighLowClose;
        }
        else if (__dummyScrutVar0.equals("OpenHighLowClose"))
        {
            st = ChartSubTypeEnum.OpenHighLowClose;
        }
        else if (__dummyScrutVar0.equals("Candlestick"))
        {
            st = ChartSubTypeEnum.Candlestick;
        }
        else
        {
            rl.logError(4,"Unknown ChartSubType '" + s + "'.  Plain assumed.");
            st = ChartSubTypeEnum.Plain;
        }          
        return st;
    }

}


