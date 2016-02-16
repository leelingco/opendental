//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ChartElementOutputEnum;
import fyiReporting.RDL.ReportLog;

public class ChartElementOutput   
{
    static public ChartElementOutputEnum getStyle(String s, ReportLog rl) throws Exception {
        ChartElementOutputEnum ceo = ChartElementOutputEnum.Output;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Output"))
        {
            ceo = ChartElementOutputEnum.Output;
        }
        else if (__dummyScrutVar0.equals("NoOutput"))
        {
            ceo = ChartElementOutputEnum.NoOutput;
        }
        else
        {
            rl.logError(4,"Unknown ChartElementOutput '" + s + "'.  Output assumed.");
            ceo = ChartElementOutputEnum.Output;
        }  
        return ceo;
    }

}


