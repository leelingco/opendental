//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MatrixLayoutDirectionEnum;
import fyiReporting.RDL.ReportLog;

// Right to Left
public class MatrixLayoutDirection   
{
    static public MatrixLayoutDirectionEnum getStyle(String s, ReportLog rl) throws Exception {
        MatrixLayoutDirectionEnum rs = MatrixLayoutDirectionEnum.LTR;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("LTR") || __dummyScrutVar0.equals("LeftToRight"))
        {
            rs = MatrixLayoutDirectionEnum.LTR;
        }
        else if (__dummyScrutVar0.equals("RTL") || __dummyScrutVar0.equals("RightToLeft"))
        {
            rs = MatrixLayoutDirectionEnum.RTL;
        }
        else
        {
            rl.logError(4,"Unknown MatrixLayoutDirection '" + s + "'.  LTR assumed.");
            rs = MatrixLayoutDirectionEnum.LTR;
        }  
        return rs;
    }

}


