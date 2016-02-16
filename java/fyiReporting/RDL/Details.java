//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableRows;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.Visibility;

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
* For tabular reports, defines the detail rows with grouping and sorting.
*/
public class Details  extends ReportLink 
{
    TableRows _TableRows;
    // The details rows for the table. The details rows
    // cannot contain any DataRegions in any of their TableCells.
    Grouping _Grouping;
    // The expressions to group the detail data by
    Sorting _Sorting;
    // The expressions to sort the detail data by
    Visibility _Visibility;
    // Indicates if the details should be hidden
    Textbox _ToggleTextbox;
    //  resolved TextBox for toggling visibility
    public Details(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _TableRows = null;
        _Grouping = null;
        _Sorting = null;
        _Visibility = null;
        _ToggleTextbox = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableRows"))
            {
                _TableRows = new TableRows(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Grouping"))
            {
                _Grouping = new Grouping(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Sorting"))
            {
                _Sorting = new Sorting(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Visibility"))
            {
                _Visibility = new Visibility(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Details element " + xNodeLoop.Name + " ignored.");
            }    
        }
        if (_TableRows == null)
            OwnerReport.rl.logError(8,"Details requires the TableRows element.");
         
    }

    public void finalPass() throws Exception {
        _TableRows.finalPass();
        if (_Grouping != null)
            _Grouping.finalPass();
         
        if (_Sorting != null)
            _Sorting.finalPass();
         
        if (_Visibility != null)
        {
            _Visibility.finalPass();
            if (_Visibility.getToggleItem() != null)
            {
                _ToggleTextbox = (Textbox)(OwnerReport.getLUReportItems()[_Visibility.getToggleItem()]);
                if (_ToggleTextbox != null)
                    _ToggleTextbox.setIsToggle(true);
                 
            }
             
        }
         
        return ;
    }

    public void run(IPresent ip, Rows rs, int start, int end) throws Exception {
        // if no rows output or rows just leave
        if (rs == null || rs.getData() == null)
            return ;
         
        for (int r = start;r <= end;r++)
        {
            _TableRows.Run(ip, rs.getData()[r]);
        }
        return ;
    }

    public void runPage(Pages pgs, Rows rs, int start, int end, float footerHeight) throws Exception {
        // if no rows output or rows just leave
        if (rs == null || rs.getData() == null)
            return ;
         
        Page p;
        Row row;
        for (int r = start;r <= end;r++)
        {
            p = pgs.getCurrentPage();
            // this can change after running a row
            row = rs.getData()[r];
            float hrows = heightOfRows(pgs,row);
            // height of all the rows in the details
            float height = p.getYOffset() + hrows;
            if (r == end)
                height += footerHeight;
             
            // on last row; may need additional room for footer
            if (height > pgs.getBottomOfPage())
            {
                p = getOwnerTable().runPageNew(pgs,p);
                getOwnerTable().runPageHeader(pgs,row,false,null);
            }
             
            _TableRows.runPage(pgs,row,hrows > pgs.getBottomOfPage());
        }
        return ;
    }

    public TableRows getTableRows() throws Exception {
        return _TableRows;
    }

    public void setTableRows(TableRows value) throws Exception {
        _TableRows = value;
    }

    public float heightOfRows(Pages pgs, Row r) throws Exception {
        return _TableRows.heightOfRows(pgs,r);
    }

    public Grouping getGrouping() throws Exception {
        return _Grouping;
    }

    public void setGrouping(Grouping value) throws Exception {
        _Grouping = value;
    }

    public Sorting getSorting() throws Exception {
        return _Sorting;
    }

    public void setSorting(Sorting value) throws Exception {
        _Sorting = value;
    }

    public Table getOwnerTable() throws Exception {
        return (Table)(this.Parent);
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

    public Textbox getToggleTextbox() throws Exception {
        return _ToggleTextbox;
    }

}


