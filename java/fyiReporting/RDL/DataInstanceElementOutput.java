//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataInstanceElementOutputEnum;
import fyiReporting.RDL.ReportLog;

// Indicates the list instances should not appear in the output
public class DataInstanceElementOutput   
{
    static public DataInstanceElementOutputEnum getStyle(String s, ReportLog rl) throws Exception {
        DataInstanceElementOutputEnum rs = DataInstanceElementOutputEnum.Output;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Output"))
        {
            rs = DataInstanceElementOutputEnum.Output;
        }
        else if (__dummyScrutVar0.equals("NoOutput"))
        {
            rs = DataInstanceElementOutputEnum.NoOutput;
        }
        else
        {
            rl.logError(4,"Unknown DataInstanceElementOutput '" + s + "'.  Output assumed.");
            rs = DataInstanceElementOutputEnum.Output;
        }  
        return rs;
    }

}


