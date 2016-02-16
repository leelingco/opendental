//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.ReportLog;

// (Default): Will behave as NoOutput for
// Textboxes with constant values,
// ContentsOnly for Rectangles and Output for
// all other items
public class DataElementOutput   
{
    static public DataElementOutputEnum getStyle(String s, ReportLog rl) throws Exception {
        DataElementOutputEnum rs = DataElementOutputEnum.Output;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Output"))
        {
            rs = DataElementOutputEnum.Output;
        }
        else if (__dummyScrutVar0.equals("NoOutput"))
        {
            rs = DataElementOutputEnum.NoOutput;
        }
        else if (__dummyScrutVar0.equals("ContentsOnly"))
        {
            rs = DataElementOutputEnum.ContentsOnly;
        }
        else if (__dummyScrutVar0.equals("Auto"))
        {
            rs = DataElementOutputEnum.Auto;
        }
        else
        {
            rl.logError(4,"Unknown DataElementOutput '" + s + "'.  Auto assumed.");
            rs = DataElementOutputEnum.Auto;
        }    
        return rs;
    }

}


