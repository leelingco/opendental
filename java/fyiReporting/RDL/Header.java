//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ICacheData;
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
* Definition of the header rows for a table.
*/
public class Header  extends ReportLink implements ICacheData
{
    TableRows _TableRows;
    // The header rows for the table or group
    boolean _RepeatOnNewPage = new boolean();
    // Indicates this header should be displayed on
    // each page that the table (or group) is displayed
    public Header(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _TableRows = null;
        _RepeatOnNewPage = true;
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
            }  
        }
        if (_TableRows == null)
            OwnerReport.rl.logError(8,"Header requires the TableRows element.");
         
    }

    public void finalPass() throws Exception {
        _TableRows.finalPass();
        OwnerReport.getDataCache().Add(this);
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        _TableRows.run(ip,row);
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        WorkClass wc = this.getValue(pgs.getReport());
        if (wc.OutputRow == row && wc.OutputPage == pgs.getCurrentPage())
            return ;
         
        Page p = pgs.getCurrentPage();
        float height = p.getYOffset() + heightOfRows(pgs,row);
        if (height > pgs.getBottomOfPage())
        {
            Table t = getOwnerTable();
            p = t.runPageNew(pgs,p);
            t.runPageHeader(pgs,row,false,null);
            if (this.getRepeatOnNewPage())
                return ;
             
        }
         
        // should already be on the page
        _TableRows.runPage(pgs,row);
        wc.OutputRow = row;
        wc.OutputPage = pgs.getCurrentPage();
        return ;
    }

    public Table getOwnerTable() throws Exception {
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof Table)
                return rl instanceof Table ? (Table)rl : (Table)null;
             
        }
        return null;
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

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    private WorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void setValue(fyiReporting.RDL.Report rpt, WorkClass w) throws Exception {
        rpt.getCache().addReplace(this,"wc",w);
    }

    static class WorkClass   
    {
        public Row OutputRow;
        // the previous outputed row
        public Page OutputPage;
        // the previous outputed row
        public WorkClass() throws Exception {
            OutputRow = null;
            OutputPage = null;
        }
    
    }

}


