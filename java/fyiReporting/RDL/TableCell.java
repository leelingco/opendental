//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableColumn;
import fyiReporting.RDL.XmlUtil;

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
* TableCell definition and processing.
*/
public class TableCell  extends ReportLink 
{
    ReportItems _ReportItems;
    // An element of the report layout (e.g. List, Textbox,
    // Line). This ReportItems collection must contain
    // exactly one ReportItem. The Top, Left, Height and
    // Width for this ReportItem are ignored. The
    // position is taken to be 0, 0 and the size to be 100%,
    // 100%. Pagebreaks on report items inside a
    // TableCell are ignored.
    int _ColSpan = new int();
    // Indicates the number of columns this cell spans.1
    // A ColSpan of 1 is the same as not specifying a ColSpan
    // some bookkeeping fields
    Table _OwnerTable;
    // Table that owns this column
    int _ColIndex = new int();
    // Column number within table; used for
    //    xrefing with other parts of table columns; e.g. column headers with details
    boolean _InTableHeader = new boolean();
    // true if tablecell is part of header; simplifies HTML processing
    boolean _InTableFooter = new boolean();
    // true if tablecell is part of footer; simplifies HTML processing
    public TableCell(ReportDefn r, ReportLink p, XmlNode xNode, int colIndex) throws Exception {
        super(r, p);
        _ColIndex = colIndex;
        _ReportItems = null;
        _ColSpan = 1;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("ReportItems"))
            {
                _ReportItems = new ReportItems(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ColSpan"))
            {
                _ColSpan = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableCell element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        // Must have exactly one ReportItems
        if (_ReportItems == null)
            OwnerReport.rl.logError(8,"ReportItems element is required with a TableCell but not specified.");
        else if (_ReportItems.getItems().Count != 1)
            OwnerReport.rl.logError(8,"Only one element in ReportItems element is allowed within a TableCell.");
          
        // Obtain the tablecell's owner table;
        //		determine if tablecell is part of table header
        _InTableHeader = false;
        ReportLink rl;
        for (rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof Table)
            {
                _OwnerTable = (Table)rl;
                break;
            }
             
            if (rl instanceof Header && rl.Parent instanceof Table)
            {
                // Header and parent is Table (not TableGroup)
                _InTableHeader = true;
            }
             
            if (rl instanceof Footer && rl.Parent instanceof Table)
            {
                // Header and parent is Table (not TableGroup)
                _InTableFooter = true;
            }
             
        }
        return ;
    }

    public void finalPass() throws Exception {
        _ReportItems.finalPass();
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        // todo: visibility on the column should really only be evaluated once at the beginning
        //   of the table processing;  also this doesn't account for the affect of colspan correctly
        //   where if any of the spanned columns are visible the value would show??
        TableColumn tc = _OwnerTable.getTableColumns().get___idx(_ColIndex);
        if (tc.getVisibility() != null && tc.getVisibility().isHidden(ip.report(),row))
            return ;
         
        // column visible?
        //  no nothing to do
        ip.tableCellStart(this,row);
        _ReportItems.getItems()[0].Run(ip, row);
        ip.tableCellEnd(this,row);
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        // todo: visibility on the column should really only be evaluated once at the beginning
        //   of the table processing;  also this doesn't account for the affect of colspan correctly
        //   where if any of the spanned columns are visible the value would show??
        TableColumn tc = _OwnerTable.getTableColumns().get___idx(_ColIndex);
        if (tc.getVisibility() != null && tc.getVisibility().isHidden(pgs.getReport(),row))
            return ;
         
        // column visible?
        //  no nothing to do
        _ReportItems.getItems()[0].RunPage(pgs, row);
        return ;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public Table getOwnerTable() throws Exception {
        return _OwnerTable;
    }

    public int getColSpan() throws Exception {
        return _ColSpan;
    }

    public void setColSpan(int value) throws Exception {
        _ColSpan = value;
    }

    public int getColIndex() throws Exception {
        return _ColIndex;
    }

    public boolean getInTableFooter() throws Exception {
        return _InTableFooter;
    }

    public boolean getInTableHeader() throws Exception {
        return _InTableHeader;
    }

}


