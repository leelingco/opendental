//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Filter;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Row;

public class RecursiveCompare  extends IComparer<Row> 
{
    fyiReporting.RDL.Report rpt;
    TypeCode _Type = new TypeCode();
    IExpr parentExpr;
    IExpr groupExpr;
    public RecursiveCompare(fyiReporting.RDL.Report r, IExpr pExpr, TypeCode tc, IExpr gExpr) throws Exception {
        rpt = r;
        _Type = tc;
        parentExpr = pExpr;
        groupExpr = gExpr;
    }

    public int compare(Row x, Row y) throws Exception {
        Object xv = parentExpr.evaluate(rpt,x);
        Object yv = groupExpr.evaluate(rpt,y);
        return -Filter.applyCompare(_Type,yv,xv);
    }

}


