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
import fyiReporting.RDL.TableCell;

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
* TableCells definition and processing.
*/
public class TableCells  extends ReportLink 
{
    List<TableCell> _Items = new List<TableCell>();
    // list of TableCell
    public TableCells(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        TableCell tc;
        _Items = new List<TableCell>();
        // Loop thru all the child nodes
        int colIndex = 0;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // keep track of the column numbers
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableCell"))
            {
                tc = new TableCell(r,this,xNodeLoop,colIndex);
                colIndex += tc.getColSpan();
            }
            else
            {
                tc = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableCells element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (tc != null)
                _Items.Add(tc);
             
        }
        if (_Items.Count > 0)
            _Items.TrimExcess();
         
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            TableCell tc = (TableCell)__dummyForeachVar1;
            tc.finalPass();
        }
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            TableCell tc = (TableCell)__dummyForeachVar2;
            tc.run(ip,row);
        }
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        // Start each row in the same location
        //   e.g. if there are two embedded tables in cells they both start at same location
        Page savepg = pgs.getCurrentPage();
        float savey = savepg.getYOffset();
        Page maxpg = savepg;
        float maxy = savey;
        for (Object __dummyForeachVar3 : _Items)
        {
            TableCell tc = (TableCell)__dummyForeachVar3;
            tc.runPage(pgs,row);
            if (maxpg != pgs.getCurrentPage())
            {
                // a page break
                if (maxpg.getPageNumber() < pgs.getCurrentPage().getPageNumber())
                {
                    maxpg = pgs.getCurrentPage();
                    maxy = maxpg.getYOffset();
                }
                 
            }
            else if (maxy > pgs.getCurrentPage().getYOffset())
            {
            }
              
            // maxy = maxy;      TODO what was this meant to do
            // restore the beginning start of the row
            pgs.setCurrentPage(savepg);
            savepg.setYOffset(savey);
        }
        pgs.setCurrentPage(maxpg);
        savepg.setYOffset(maxy);
        return ;
    }

    public List<TableCell> getItems() throws Exception {
        return _Items;
    }

}


