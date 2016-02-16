//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MatrixCells;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.RSize;

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
* Handle a Matrix Row: i.e. height and matrix cells that make up the row.
*/
public class MatrixRow  extends ReportLink 
{
    RSize _Height;
    // Height of each detail cell in this row.
    MatrixCells _MatrixCells;
    // The set of cells in a row in the detail section of the Matrix.
    public MatrixRow(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Height = null;
        _MatrixCells = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Height"))
            {
                _Height = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("MatrixCells"))
            {
                _MatrixCells = new MatrixCells(r,this,xNodeLoop);
            }
            else
            {
            }  
        }
        if (_MatrixCells == null)
            OwnerReport.rl.logError(8,"MatrixRow requires the MatrixCells element.");
         
    }

    public void finalPass() throws Exception {
        if (_MatrixCells != null)
            _MatrixCells.finalPass();
         
        return ;
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    public MatrixCells getMatrixCells() throws Exception {
        return _MatrixCells;
    }

    public void setMatrixCells(MatrixCells value) throws Exception {
        _MatrixCells = value;
    }

}


