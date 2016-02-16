//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.TrueFalseAutoEnum;

public class TrueFalseAuto   
{
    static public TrueFalseAutoEnum getStyle(String s, ReportLog rl) throws Exception {
        TrueFalseAutoEnum rs = TrueFalseAutoEnum.True;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("True"))
        {
            rs = TrueFalseAutoEnum.True;
        }
        else if (__dummyScrutVar0.equals("False"))
        {
            rs = TrueFalseAutoEnum.False;
        }
        else if (__dummyScrutVar0.equals("Auto"))
        {
            rs = TrueFalseAutoEnum.Auto;
        }
        else
        {
            rl.logError(4,"Unknown True False Auto value of '" + s + "'.  Auto assumed.");
            rs = TrueFalseAutoEnum.Auto;
        }   
        return rs;
    }

}


