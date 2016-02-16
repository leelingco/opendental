//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.QueryCommandTypeEnum;
import fyiReporting.RDL.ReportLog;

public class QueryCommandType   
{
    static public QueryCommandTypeEnum getStyle(String s, ReportLog rl) throws Exception {
        QueryCommandTypeEnum rs = QueryCommandTypeEnum.Text;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Text"))
        {
            rs = QueryCommandTypeEnum.Text;
        }
        else if (__dummyScrutVar0.equals("StoredProcedure"))
        {
            rs = QueryCommandTypeEnum.StoredProcedure;
        }
        else if (__dummyScrutVar0.equals("TableDirect"))
        {
            rs = QueryCommandTypeEnum.TableDirect;
        }
        else
        {
            // user error just force to normal TODO
            rl.logError(4,"Unknown Query CommandType '" + s + "'.  Text assumed.");
            rs = QueryCommandTypeEnum.Text;
        }   
        return rs;
    }

}


