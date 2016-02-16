//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableRows;
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
* Definition of footer rows for a table or group.
*/
public class Footer  extends ReportLink 
{
    TableRows _TableRows;
    // The footer rows for the table or group
    boolean _RepeatOnNewPage = new boolean();
    // Indicates this footer should be displayed on
    // each page that the table (or group) is displayed
    public Footer(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _TableRows = null;
        _RepeatOnNewPage = false;
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
            else if (__dummyScrutVar0.equals("RepeatOnNewPage"))
            {
                _RepeatOnNewPage = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Footer element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if (_TableRows == null)
            OwnerReport.rl.logError(8,"TableRows element is required with a Footer but not specified.");
         
    }

    public void finalPass() throws Exception {
        _TableRows.finalPass();
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        _TableRows.run(ip,row);
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        Page p = pgs.getCurrentPage();
        if (p.getYOffset() + heightOfRows(pgs,row) > pgs.getBottomOfPage())
        {
            p = getOwnerTable().runPageNew(pgs,p);
            getOwnerTable().runPageHeader(pgs,row,false,null);
        }
         
        _TableRows.runPage(pgs,row);
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

    public boolean getRepeatOnNewPage() throws Exception {
        return _RepeatOnNewPage;
    }

    public void setRepeatOnNewPage(boolean value) throws Exception {
        _RepeatOnNewPage = value;
    }

    public Table getOwnerTable() throws Exception {
        ReportLink rl = this.Parent;
        while (rl != null)
        {
            if (rl instanceof Table)
                return rl instanceof Table ? (Table)rl : (Table)null;
             
            rl = rl.Parent;
        }
        return null;
    }

}


