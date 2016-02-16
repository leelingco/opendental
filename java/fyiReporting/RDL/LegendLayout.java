//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.LegendLayoutEnum;
import fyiReporting.RDL.ReportLog;

public class LegendLayout   
{
    static public LegendLayoutEnum getStyle(String s, ReportLog rl) throws Exception {
        LegendLayoutEnum rs = LegendLayoutEnum.Column;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Column"))
        {
            rs = LegendLayoutEnum.Column;
        }
        else if (__dummyScrutVar0.equals("Row"))
        {
            rs = LegendLayoutEnum.Row;
        }
        else if (__dummyScrutVar0.equals("Table"))
        {
            rs = LegendLayoutEnum.Table;
        }
        else
        {
            rl.logError(4,"Unknown LegendLayout '" + s + "'.  Column assumed.");
            rs = LegendLayoutEnum.Column;
        }   
        return rs;
    }

}


