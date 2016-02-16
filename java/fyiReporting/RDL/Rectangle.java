//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageRectangle;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
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
* Represent the rectangle report item.
*/
public class Rectangle  extends ReportItem 
{
    ReportItems _ReportItems;
    // Report items contained within the bounds of the rectangle.
    boolean _PageBreakAtStart = new boolean();
    // Indicates the report should page break at the start of the rectangle.
    boolean _PageBreakAtEnd = new boolean();
    // Indicates the report should page break at the end of the rectangle.
    // constructor that doesn't process syntax
    public Rectangle(ReportDefn r, ReportLink p, XmlNode xNode, boolean bNoLoop) throws Exception {
        super(r, p, xNode);
        _ReportItems = null;
        _PageBreakAtStart = false;
        _PageBreakAtEnd = false;
    }

    public Rectangle(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _ReportItems = null;
        _PageBreakAtStart = false;
        _PageBreakAtEnd = false;
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
            else if (__dummyScrutVar0.equals("PageBreakAtStart"))
            {
                _PageBreakAtStart = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("PageBreakAtEnd"))
            {
                _PageBreakAtEnd = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (reportItemElement(xNodeLoop))
                    break;
                 
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Rectangle element " + xNodeLoop.Name + " ignored.");
            }   
        }
    }

    public void finalPass() throws Exception {
        super.finalPass();
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        super.run(ip,row);
        if (_ReportItems == null)
            return ;
         
        if (ip.rectangleStart(this,row))
        {
            _ReportItems.run(ip,row);
            ip.rectangleEnd(this,row);
        }
         
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        setPagePositionBegin(pgs);
        // Handle page breaking at start
        if (this.getPageBreakAtStart() && !isTableOrMatrixCell(r) && !pgs.getCurrentPage().isEmpty())
        {
            // force page break at beginning of dataregion
            pgs.nextOrNew();
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }
         
        PageRectangle pr = new PageRectangle();
        setPagePositionAndStyle(r,pr,row);
        if (pr.getSI().BackgroundImage != null)
            pr.getSI().BackgroundImage.setH(pr.getH());
         
        //   and in the background image
        Page p = pgs.getCurrentPage();
        p.addObject(pr);
        // Handle page breaking at end
        if (this.getPageBreakAtEnd() && !isTableOrMatrixCell(r) && !pgs.getCurrentPage().isEmpty())
        {
            // force page break at beginning of dataregion
            pgs.nextOrNew();
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }
        else if (_ReportItems != null)
        {
            float saveY = p.getYOffset();
            p.setYOffset(p.getYOffset() + (getTop() == null ? 0 : this.getTop().getPoints()));
            _ReportItems.runPage(pgs,row,getOffsetCalc(pgs.getReport()) + leftCalc(r));
            p.setYOffset(saveY);
        }
          
        setPagePositionEnd(pgs,pgs.getCurrentPage().getYOffset());
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public boolean getPageBreakAtStart() throws Exception {
        return _PageBreakAtStart;
    }

    public void setPageBreakAtStart(boolean value) throws Exception {
        _PageBreakAtStart = value;
    }

    public boolean getPageBreakAtEnd() throws Exception {
        return _PageBreakAtEnd;
    }

    public void setPageBreakAtEnd(boolean value) throws Exception {
        _PageBreakAtEnd = value;
    }

}


