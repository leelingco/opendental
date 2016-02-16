//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MatrixRow;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

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
* Collection of matrix rows
*/
public class MatrixRows  extends ReportLink 
{
    List<MatrixRow> _Items = new List<MatrixRow>();
    // list of MatrixRow
    public MatrixRows(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        MatrixRow m;
        _Items = new List<MatrixRow>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("MatrixRow"))
            {
                m = new MatrixRow(r,this,xNodeLoop);
            }
            else
            {
                m = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown MatrixRows element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (m != null)
                _Items.Add(m);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For MatrixRows at least one MatrixRow is required.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            MatrixRow m = (MatrixRow)__dummyForeachVar1;
            m.finalPass();
        }
        return ;
    }

    public float defnHeight() throws Exception {
        float height = 0;
        for (Object __dummyForeachVar2 : _Items)
        {
            MatrixRow m = (MatrixRow)__dummyForeachVar2;
            height += m.getHeight().getPoints();
        }
        return height;
    }

    public List<MatrixRow> getItems() throws Exception {
        return _Items;
    }

    /**
    * CellCount requires a correctly configured Matrix structure.  This is true at runtime
    * but not necessarily true during Matrix parse.
    */
    public int getCellCount() throws Exception {
        MatrixRow mr = _Items[0] instanceof MatrixRow ? (MatrixRow)_Items[0] : (MatrixRow)null;
        return mr.getMatrixCells().getItems().Count;
    }

}


