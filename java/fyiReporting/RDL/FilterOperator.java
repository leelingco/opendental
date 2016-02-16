//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.FilterOperatorEnum;

// prior to definition or illegal value
public class FilterOperator   
{
    static public FilterOperatorEnum getStyle(String s) throws Exception {
        FilterOperatorEnum rs = FilterOperatorEnum.Equal;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Equal") || __dummyScrutVar0.equals("="))
        {
            rs = FilterOperatorEnum.Equal;
        }
        else if (__dummyScrutVar0.equals("TopN"))
        {
            rs = FilterOperatorEnum.TopN;
        }
        else if (__dummyScrutVar0.equals("BottomN"))
        {
            rs = FilterOperatorEnum.BottomN;
        }
        else if (__dummyScrutVar0.equals("TopPercent"))
        {
            rs = FilterOperatorEnum.TopPercent;
        }
        else if (__dummyScrutVar0.equals("BottomPercent"))
        {
            rs = FilterOperatorEnum.BottomPercent;
        }
        else if (__dummyScrutVar0.equals("In"))
        {
            rs = FilterOperatorEnum.In;
        }
        else if (__dummyScrutVar0.equals("LessThanOrEqual") || __dummyScrutVar0.equals("<="))
        {
            rs = FilterOperatorEnum.LessThanOrEqual;
        }
        else if (__dummyScrutVar0.equals("LessThan") || __dummyScrutVar0.equals("<"))
        {
            rs = FilterOperatorEnum.LessThan;
        }
        else if (__dummyScrutVar0.equals("GreaterThanOrEqual") || __dummyScrutVar0.equals(">="))
        {
            rs = FilterOperatorEnum.GreaterThanOrEqual;
        }
        else if (__dummyScrutVar0.equals("GreaterThan") || __dummyScrutVar0.equals(">"))
        {
            rs = FilterOperatorEnum.GreaterThan;
        }
        else if (__dummyScrutVar0.equals("NotEqual") || __dummyScrutVar0.equals("!="))
        {
            rs = FilterOperatorEnum.NotEqual;
        }
        else if (__dummyScrutVar0.equals("Between"))
        {
            rs = FilterOperatorEnum.Between;
        }
        else if (__dummyScrutVar0.equals("Like"))
        {
            rs = FilterOperatorEnum.Like;
        }
        else
        {
            // user error just force to normal TODO
            rs = FilterOperatorEnum.Unknown;
        }             
        return rs;
    }

}


