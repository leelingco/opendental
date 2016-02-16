//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.SortDirectionEnum;

public class SortDirection   
{
    static public SortDirectionEnum getStyle(String s, ReportLog rl) throws Exception {
        SortDirectionEnum rs = SortDirectionEnum.Ascending;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Ascending"))
        {
            rs = SortDirectionEnum.Ascending;
        }
        else if (__dummyScrutVar0.equals("Descending"))
        {
            rs = SortDirectionEnum.Descending;
        }
        else
        {
            rl.logError(4,"Unknown SortDirection '" + s + "'.  Ascending assumed.");
            rs = SortDirectionEnum.Ascending;
        }  
        return rs;
    }

}


