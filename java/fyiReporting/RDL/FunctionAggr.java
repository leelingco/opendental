//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RowEnumerable;
import fyiReporting.RDL.Rows;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Base class for all aggregate functions
*/
public abstract class FunctionAggr   
{
    public IExpr _Expr;
    // aggregate expression
    public Object _Scope = new Object();
    // DataSet or Grouping or DataRegion that contains (directly or
    //  indirectly) the report item that the aggregate
    //  function is used in
    // Can also hold the Matrix object
    boolean _LevelCheck = new boolean();
    // row processing requires level check
    //   i.e. simple specified on recursive row check
    /**
    * Base class of all aggregate functions
    */
    public FunctionAggr(IExpr e, Object scp) throws Exception {
        _Expr = e;
        _Scope = scp;
        _LevelCheck = false;
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        if (_Expr != null)
            _Expr = _Expr.constantOptimization();
         
        return (IExpr)this;
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return false;
    }

    public IExpr getExpr() throws Exception {
        return _Expr;
    }

    public Object getScope() throws Exception {
        return _Scope;
    }

    public boolean getLevelCheck() throws Exception {
        return _LevelCheck;
    }

    public void setLevelCheck(boolean value) throws Exception {
        _LevelCheck = value;
    }

    // return an IEnumerable that represents the scope of the data
    protected RowEnumerable getDataScope(fyiReporting.RDL.Report rpt, Row row, RefSupport<boolean> bSave) throws Exception {
        bSave.setValue(true);
        RowEnumerable re = null;
        if (this._Scope != null)
        {
            Type t = this._Scope.GetType();
            if (t == Grouping.class)
            {
                bSave.setValue(false);
                Grouping g = (Grouping)(this._Scope);
                if (g.getInMatrix())
                {
                    Rows rows = g.getRows(rpt);
                    if (rows == null)
                        return null;
                     
                    re = new RowEnumerable(0, rows.getData().Count - 1, rows.getData(), _LevelCheck);
                }
                else
                {
                    if (row == null)
                        return null;
                     
                    GroupEntry ge = row.getR().getCurrentGroups()[g.getIndex(rpt)];
                    re = new RowEnumerable(ge.getStartRow(),ge.getEndRow(),row.getR().getData(),_LevelCheck);
                } 
            }
            else if (t == Matrix.class)
            {
                bSave.setValue(false);
                Matrix m = (Matrix)(this._Scope);
                Rows mData = m.getMyData(rpt);
                re = new RowEnumerable(0, mData.getData().Count - 1, mData.getData(), false);
            }
            else if (t == String.class)
            {
                // happens on page header/footer scope
                if (row != null)
                    re = new RowEnumerable(0, row.getR().getData().Count - 1, row.getR().getData(), false);
                 
                bSave.setValue(false);
            }
            else if (row != null)
            {
                re = new RowEnumerable(0, row.getR().getData().Count - 1, row.getR().getData(), false);
            }
            else
            {
                DataSetDefn ds = this._Scope instanceof DataSetDefn ? (DataSetDefn)this._Scope : (DataSetDefn)null;
                if (ds != null && ds.getQuery() != null)
                {
                    Rows rows = ds.getQuery().getMyData(rpt);
                    if (rows != null)
                        re = new RowEnumerable(0, rows.getData().Count - 1, rows.getData(), false);
                     
                }
                 
            }    
        }
        else if (row != null)
        {
            re = new RowEnumerable(0, row.getR().getData().Count - 1, row.getR().getData(), false);
        }
          
        return re;
    }

}


