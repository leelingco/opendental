//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IExpr;

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
* Binary operator
*/
public abstract class FunctionBinary   
{
    public IExpr _lhs;
    // lhs
    public IExpr _rhs;
    // rhs
    /**
    * Arbitrary binary operater; might be a
    */
    public FunctionBinary() throws Exception {
        _lhs = null;
        _rhs = null;
    }

    public FunctionBinary(IExpr l, IExpr r) throws Exception {
        _lhs = l;
        _rhs = r;
    }

    public boolean isConstant() throws Exception {
        if (_lhs.isConstant())
            return _rhs.isConstant();
         
        return false;
    }

    //		virtual public bool EvaluateBoolean(Report rpt, Row row)
    //		{
    //			return false;
    //		}
    public IExpr getLhs() throws Exception {
        return _lhs;
    }

    public void setLhs(IExpr value) throws Exception {
        _lhs = value;
    }

    public IExpr getRhs() throws Exception {
        return _rhs;
    }

    public void setRhs(IExpr value) throws Exception {
        _rhs = value;
    }

}


