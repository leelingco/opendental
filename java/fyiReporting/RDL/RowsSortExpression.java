//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;

// in case of tie use original row number
public class RowsSortExpression   
{
    public Expression expr;
    public boolean bAscending = new boolean();
    public RowsSortExpression(Expression e, boolean asc) throws Exception {
        expr = e;
        bAscending = asc;
    }

    public RowsSortExpression(Expression e) throws Exception {
        expr = e;
        bAscending = true;
    }

}


