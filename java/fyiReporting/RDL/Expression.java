//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.Constant;
import fyiReporting.RDL.ConstantError;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.Details;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.NameLookup;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.Parser;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.TableGroup;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* A report expression: includes original source, parsed expression and type information.
*/
public class Expression  extends ReportLink implements IExpr
{
    String _Source = new String();
    // source of expression
    IExpr _Expr;
    // expression after parse
    TypeCode _Type = new TypeCode();
    // type of expression; only available after parsed
    ExpressionType _ExpectedType = ExpressionType.Variant;
    // expected type of expression
    String _UniqueName = new String();
    // unique name of expression; not always created
    public Expression(ReportDefn r, ReportLink p, XmlNode xNode, ExpressionType et) throws Exception {
        super(r, p);
        _Source = xNode.InnerText;
        _Type = TypeCode.Empty;
        _ExpectedType = et;
        _Expr = null;
    }

    // UniqueName of expression
    public String getUniqueName() throws Exception {
        return _UniqueName;
    }

    public void finalPass() throws Exception {
        // optimization: avoid expression overhead if this isn't really an expression
        if (_Source == null)
        {
            _Expr = new Constant("");
            return ;
        }
        else // empty expression
        if (StringSupport.equals(_Source, "") || !StringSupport.equals(_Source.Substring(0, 1), "="))
        {
            // if 1st char not '='
            _Expr = new Constant(_Source);
            return ;
        }
          
        //   this is a constant value
        Parser p = new Parser(OwnerReport.getDataCache());
        // find the fields that are part of the DataRegion (if there is one)
        IDictionary fields = null;
        ReportLink dr = Parent;
        Grouping grp = null;
        // remember if in a table group or detail group or list group
        Matrix m = null;
        ReportLink phpf = null;
        while (dr != null)
        {
            if (dr instanceof Grouping)
                p.setNoAggregateFunctions(true);
            else if (dr instanceof TableGroup)
                grp = ((TableGroup)dr).getGrouping();
            else if (dr instanceof Matrix)
            {
                m = (Matrix)dr;
                break;
            }
            else // if matrix we need to pass special
            if (dr instanceof Details)
            {
                grp = ((Details)dr).getGrouping();
            }
            else if (dr instanceof List)
            {
                grp = ((List)dr).getGrouping();
                break;
            }
            else if (dr instanceof PageHeader || dr instanceof PageFooter)
            {
                phpf = dr;
            }
            else if (dr instanceof DataRegion || dr instanceof DataSetDefn)
                break;
                   
            dr = dr.Parent;
        }
        if (dr != null)
        {
            if (dr instanceof DataSetDefn)
            {
                DataSetDefn d = (DataSetDefn)dr;
                if (d.getFields() != null)
                    fields = d.getFields().getItems();
                 
            }
            else
            {
                // must be a DataRegion
                DataRegion d = (DataRegion)dr;
                if (d.getDataSetDefn() != null && d.getDataSetDefn().getFields() != null)
                    fields = d.getDataSetDefn().getFields().getItems();
                 
            } 
        }
         
        NameLookup lu = new NameLookup(fields,OwnerReport.getLUReportParameters(),OwnerReport.getLUReportItems(),OwnerReport.getLUGlobals(),OwnerReport.getLUUser(),OwnerReport.getLUAggrScope(),grp,m,OwnerReport.getCodeModules(),OwnerReport.getClasses(),OwnerReport.getDataSetsDefn(),OwnerReport.getCodeType());
        if (phpf != null)
        {
            // Non-null when expression is in PageHeader or PageFooter;
            //   Expression name needed for dynamic lookup of ReportItems on a page.
            lu.setPageFooterHeader(phpf);
            RefSupport refVar___0 = new RefSupport(Parser.Counter);
            lu.setExpressionName(_UniqueName = "xn_" + Interlocked.Increment(refVar___0).ToString());
            Parser.Counter = refVar___0.getValue();
        }
         
        try
        {
            _Expr = p.parse(lu,_Source);
        }
        catch (Exception e)
        {
            _Expr = new ConstantError(e.Message);
            // Invalid expression
            OwnerReport.rl.logError(8,"Expression '" + _Source + "' failed to parse: " + e.Message);
        }

        try
        {
            // Optimize removing any expression that always result in a constant
            _Expr = _Expr.constantOptimization();
        }
        catch (Exception ex)
        {
            OwnerReport.rl.logError(4,"Expression:" + _Source + "\r\nConstant Optimization exception:\r\n" + ex.Message + "\r\nStack trace:\r\n" + ex.StackTrace);
        }

        _Type = _Expr.getTypeCode();
        return ;
    }

    private void reportError(fyiReporting.RDL.Report rpt, int severity, String err) throws Exception {
        if (rpt == null)
            OwnerReport.rl.logError(severity,err);
        else
            rpt.rl.logError(severity,err); 
    }

    public String getSource() throws Exception {
        return _Source;
    }

    public IExpr getExpr() throws Exception {
        return _Expr;
    }

    public TypeCode getType() throws Exception {
        return _Type;
    }

    public ExpressionType getExpectedType() throws Exception {
        return _ExpectedType;
    }

    public System.TypeCode getTypeCode() throws Exception {
        return _Expr.getTypeCode();
    }

    public boolean isConstant() throws Exception {
        return _Expr.isConstant();
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            // Check to see if we're evaluating an expression in a page header or footer;
            //   If that is the case the rows are cached by page.
            if (row == null && this.getUniqueName() != null)
            {
                Rows rows = rpt.getPageExpressionRows(getUniqueName());
                if (rows != null && rows.getData() != null && rows.getData().Count > 0)
                    row = rows.getData()[0];
                 
            }
             
            return _Expr.evaluate(rpt,row);
        }
        catch (Exception e)
        {
            String err = new String();
            if (e.InnerException != null)
                err = String.Format("Exception evaluating {0}.  {1}.  {2}", _Source, e.Message, e.InnerException.Message);
            else
                err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message); 
            reportError(rpt,4,err);
            return null;
        }
    
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            return _Expr.evaluateString(rpt,row);
        }
        catch (Exception e)
        {
            String err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message);
            reportError(rpt,4,err);
            return null;
        }
    
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            return _Expr.evaluateDouble(rpt,row);
        }
        catch (Exception e)
        {
            String err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message);
            reportError(rpt,4,err);
            return double.NaN;
        }
    
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            return _Expr.evaluateDecimal(rpt,row);
        }
        catch (Exception e)
        {
            String err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message);
            reportError(rpt,4,err);
            return double.MinValue;
        }
    
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            return _Expr.evaluateDateTime(rpt,row);
        }
        catch (Exception e)
        {
            String err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message);
            reportError(rpt,4,err);
            return DateTime.MinValue;
        }
    
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            return _Expr.evaluateBoolean(rpt,row);
        }
        catch (Exception e)
        {
            String err = String.Format("Exception evaluating {0}.  {1}", _Source, e.Message);
            reportError(rpt,4,err);
            return false;
        }
    
    }

}


