//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Style;
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
* Defines the page footer of the report
*/
public class PageFooter  extends ReportLink 
{
    RSize _Height;
    // Height of the page footer
    boolean _PrintOnFirstPage = new boolean();
    // Indicates if the page footer should be shown on
    // the first page of the report
    boolean _PrintOnLastPage = new boolean();
    // Indicates if the page footer should be shown on
    // the last page of the report. Not used in singlepage reports.
    ReportItems _ReportItems;
    // The region that contains the elements of the footer layout
    // No data regions or subreports are allowed in the page footer
    Style _Style;
    // Style information for the page footer
    public PageFooter(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Height = null;
        _PrintOnFirstPage = true;
        _PrintOnLastPage = true;
        _ReportItems = null;
        _Style = null;
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
            else if (__dummyScrutVar0.equals("PrintOnFirstPage"))
            {
                _PrintOnFirstPage = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("PrintOnLastPage"))
            {
                _PrintOnLastPage = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("ReportItems"))
            {
                _ReportItems = new ReportItems(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown PageFooter element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
        if (_Height == null)
            OwnerReport.rl.logError(8,"PageFooter Height is required.");
         
    }

    public void finalPass() throws Exception {
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        if (OwnerReport.getSubreport() != null)
            return ;
         
        // don't process page footers for sub-reports
        fyiReporting.RDL.Report rpt = ip.report();
        rpt.TotalPages = rpt.PageNumber = 1;
        ip.pageFooterStart(this);
        if (_ReportItems != null)
            _ReportItems.run(ip,row);
         
        ip.pageFooterEnd(this);
    }

    public void runPage(Pages pgs) throws Exception {
        if (OwnerReport.getSubreport() != null)
            return ;
         
        // don't process page footers for sub-reports
        if (_ReportItems == null)
            return ;
         
        fyiReporting.RDL.Report rpt = pgs.getReport();
        rpt.TotalPages = pgs.getPageCount();
        for (Object __dummyForeachVar1 : pgs)
        {
            Page p = (Page)__dummyForeachVar1;
            rpt.setCurrentPage(p);
            // needs to know for page header/footer expr processing
            p.setYOffset(OwnerReport.getPageHeight().getPoints() - OwnerReport.getBottomMargin().getPoints() - this._Height.getPoints());
            p.setXOffset(0);
            pgs.setCurrentPage(p);
            rpt.PageNumber = p.getPageNumber();
            if (p.getPageNumber() == 1 && pgs.getCount() > 1 && !_PrintOnFirstPage)
                continue;
             
            // Don't put footer on the first page
            if (p.getPageNumber() == pgs.getCount() && !_PrintOnLastPage)
                continue;
             
            // Don't put footer on the last page
            _ReportItems.runPage(pgs,null,OwnerReport.getLeftMargin().getPoints());
        }
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    public boolean getPrintOnFirstPage() throws Exception {
        return _PrintOnFirstPage;
    }

    public void setPrintOnFirstPage(boolean value) throws Exception {
        _PrintOnFirstPage = value;
    }

    public boolean getPrintOnLastPage() throws Exception {
        return _PrintOnLastPage;
    }

    public void setPrintOnLastPage(boolean value) throws Exception {
        _PrintOnLastPage = value;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

}


