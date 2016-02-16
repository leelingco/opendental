//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableRow;

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
* TableRows definition and processing.
*/
public class TableRows  extends ReportLink 
{
    List<TableRow> _Items = new List<TableRow>();
    // list of TableRow
    float _HeightOfRows = new float();
    // height of contained rows
    boolean _CanGrow = new boolean();
    // if any TableRow contains a TextBox with CanGrow
    public TableRows(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        TableRow t;
        _Items = new List<TableRow>();
        _CanGrow = false;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableRow"))
            {
                t = new TableRow(r,this,xNodeLoop);
            }
            else
            {
                t = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableRows element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (t != null)
                _Items.Add(t);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For TableRows at least one TableRow is required.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        _HeightOfRows = 0;
        for (Object __dummyForeachVar1 : _Items)
        {
            TableRow t = (TableRow)__dummyForeachVar1;
            _HeightOfRows += t.getHeight().getPoints();
            t.finalPass();
            _CanGrow |= t.getCanGrow();
        }
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            TableRow t = (TableRow)__dummyForeachVar2;
            t.run(ip,row);
        }
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        runPage(pgs,row,false);
    }

    public void runPage(Pages pgs, Row row, boolean bCheckRows) throws Exception {
        if (bCheckRows)
        {
            for (Object __dummyForeachVar3 : _Items)
            {
                // we need to check to see if a row will fit on the page
                TableRow t = (TableRow)__dummyForeachVar3;
                Page p = pgs.getCurrentPage();
                // this can change after running a row
                float hrows = t.heightOfRow(pgs,row);
                // height of this row
                float height = p.getYOffset() + hrows;
                if (height > pgs.getBottomOfPage())
                {
                    p = getOwnerTable().runPageNew(pgs,p);
                    getOwnerTable().runPageHeader(pgs,row,false,null);
                }
                 
                t.runPage(pgs,row);
            }
        }
        else
        {
            for (Object __dummyForeachVar4 : _Items)
            {
                // all rows will fit on the page
                TableRow t = (TableRow)__dummyForeachVar4;
                t.runPage(pgs,row);
            }
        } 
        return ;
    }

    public Table getOwnerTable() throws Exception {
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof Table)
                return rl instanceof Table ? (Table)rl : (Table)null;
             
        }
        throw new Exception("Internal error.  TableRows must be owned eventually by a table.");
    }

    public float defnHeight() throws Exception {
        float height = 0;
        for (Object __dummyForeachVar5 : this._Items)
        {
            TableRow tr = (TableRow)__dummyForeachVar5;
            height += tr.getHeight().getPoints();
        }
        return height;
    }

    public float heightOfRows(Pages pgs, Row r) throws Exception {
        if (!this._CanGrow)
            return _HeightOfRows;
         
        float height = 0;
        for (Object __dummyForeachVar6 : this._Items)
        {
            TableRow tr = (TableRow)__dummyForeachVar6;
            height += tr.heightOfRow(pgs,r);
        }
        return Math.Max(height, _HeightOfRows);
    }

    public List<TableRow> getItems() throws Exception {
        return _Items;
    }

}


