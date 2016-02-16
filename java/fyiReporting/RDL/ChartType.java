//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ChartTypeEnum;

// above Count
public class ChartType   
{
    static public ChartTypeEnum getStyle(String s) throws Exception {
        ChartTypeEnum ct = ChartTypeEnum.Column;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Column"))
        {
            ct = ChartTypeEnum.Column;
        }
        else if (__dummyScrutVar0.equals("Bar"))
        {
            ct = ChartTypeEnum.Bar;
        }
        else if (__dummyScrutVar0.equals("Line"))
        {
            ct = ChartTypeEnum.Line;
        }
        else if (__dummyScrutVar0.equals("Pie"))
        {
            ct = ChartTypeEnum.Pie;
        }
        else if (__dummyScrutVar0.equals("Scatter"))
        {
            ct = ChartTypeEnum.Scatter;
        }
        else if (__dummyScrutVar0.equals("Bubble"))
        {
            ct = ChartTypeEnum.Bubble;
        }
        else if (__dummyScrutVar0.equals("Area"))
        {
            ct = ChartTypeEnum.Area;
        }
        else if (__dummyScrutVar0.equals("Doughnut"))
        {
            ct = ChartTypeEnum.Doughnut;
        }
        else if (__dummyScrutVar0.equals("Stock"))
        {
            ct = ChartTypeEnum.Stock;
        }
        else
        {
            // unknown type
            ct = ChartTypeEnum.Unknown;
        }         
        return ct;
    }

}


