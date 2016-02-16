//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.PageLine;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;

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
* Represents the report item for a line.
*/
public class Line  extends ReportItem 
{
    // Line has no additional elements/attributes beyond ReportItem
    public Line(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            // nothing beyond reportitem for now
            if (!reportItemElement(xNodeLoop))
            {
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Line element " + xNodeLoop.Name + " ignored.");
            }
             
        }
    }

    public void run(IPresent ip, Row row) throws Exception {
        ip.line(this,row);
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        setPagePositionBegin(pgs);
        PageLine pl = new PageLine();
        setPagePositionAndStyle(r,pl,row);
        //pl.X = GetOffsetCalc(r) + LeftCalc(r);
        //if (Top != null)
        //    pl.Y = Top.Points;
        //pl.Y2 = Y2;
        //pl.X2 = GetX2(pgs.Report);
        //if (Style != null)
        //    pl.SI = Style.GetStyleInfo(r, row);
        //else
        //    pl.SI = new StyleInfo();	// this will just default everything
        pgs.getCurrentPage().addObject(pl);
        setPagePositionEnd(pgs,pl.getY());
    }

    public float getX2(fyiReporting.RDL.Report rpt) throws Exception {
        float x2 = getOffsetCalc(rpt) + leftCalc(rpt);
        if (getWidth() != null)
            x2 += getWidth().getPoints();
         
        return x2;
    }

    public int getiX2() throws Exception {
        int x2 = 0;
        if (getLeft() != null)
            x2 = getLeft().getSize();
         
        if (getWidth() != null)
            x2 += getWidth().getSize();
         
        return x2;
    }

    public int getiY2() throws Exception {
        int y2 = 0;
        if (getTop() != null)
            y2 = getTop().getSize();
         
        if (getHeight() != null)
            y2 += getHeight().getSize();
         
        return y2;
    }

    public float getY2() throws Exception {
        float y2 = 0;
        if (getTop() != null)
            y2 = getTop().getPoints();
         
        if (getHeight() != null)
            y2 += getHeight().getPoints();
         
        return y2;
    }

}


