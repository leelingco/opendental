//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MatrixCellDataElementOutputEnum;
import fyiReporting.RDL.ReportLog;

// Indicates the cell should not appear in the output
public class MatrixCellDataElementOutput   
{
    static public MatrixCellDataElementOutputEnum getStyle(String s, ReportLog rl) throws Exception {
        MatrixCellDataElementOutputEnum rs = MatrixCellDataElementOutputEnum.Output;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Output"))
        {
            rs = MatrixCellDataElementOutputEnum.Output;
        }
        else if (__dummyScrutVar0.equals("NoOutput"))
        {
            rs = MatrixCellDataElementOutputEnum.NoOutput;
        }
        else
        {
            rl.logError(4,"Unknown MatrixCellDataElementOutput '" + s + "'.  Output assumed.");
            rs = MatrixCellDataElementOutputEnum.Output;
        }  
        return rs;
    }

}


