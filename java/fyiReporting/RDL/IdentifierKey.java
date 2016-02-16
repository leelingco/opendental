//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IdentifierKeyEnum;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Row;

public class IdentifierKey   implements IExpr
{
    IdentifierKeyEnum _Value = IdentifierKeyEnum.Recursive;
    // value of the identifier
    /**
    * 
    */
    public IdentifierKey(IdentifierKeyEnum v) throws Exception {
        _Value = v;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Object;
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    public IdentifierKeyEnum getValue() throws Exception {
        return _Value;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return _Value;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Double.NaN;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Decimal.MinValue;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return null;
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return DateTime.MinValue;
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return false;
    }

}


