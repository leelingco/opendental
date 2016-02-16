//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.OInt;
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
* Body definition and processing.  Contains the elements of the report body.
*/
public class Body  extends ReportLink 
{
    ReportItems _ReportItems;
    // The region that contains the elements of the report body
    RSize _Height;
    // Height of the body
    int _Columns = new int();
    // Number of columns for the report
    // Default: 1. Min: 1. Max: 1000
    RSize _ColumnSpacing;
    // Size Spacing between each column in multi-column
    // output 	Default: 0.5 in
    Style _Style;
    // Default style information for the body
    public Body(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _ReportItems = null;
        _Height = null;
        _Columns = 1;
        _ColumnSpacing = null;
        _Style = null;
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
            else // need a class for this
            if (__dummyScrutVar0.equals("Height"))
            {
                _Height = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Columns"))
            {
                _Columns = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("ColumnSpacing"))
            {
                _ColumnSpacing = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Body element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
        if (_Height == null)
            OwnerReport.rl.logError(8,"Body Height not specified.");
         
    }

    public void finalPass() throws Exception {
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        ip.bodyStart(this);
        if (_ReportItems != null)
            _ReportItems.run(ip,null);
         
        // not sure about the row here?
        ip.bodyEnd(this);
        return ;
    }

    public void runPage(Pages pgs) throws Exception {
        if (OwnerReport.getSubreport() == null)
        {
            // Only set bottom of pages when on top level report
            pgs.setBottomOfPage(OwnerReport.getBottomOfPage());
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }
         
        this.setCurrentColumn(pgs.getReport(),0);
        if (_ReportItems != null)
            _ReportItems.runPage(pgs,null,OwnerReport.getLeftMargin().getPoints());
         
        return ;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    public int getColumns() throws Exception {
        return _Columns;
    }

    public void setColumns(int value) throws Exception {
        _Columns = value;
    }

    public int getCurrentColumn(fyiReporting.RDL.Report rpt) throws Exception {
        OInt cc = rpt.getCache().get(this,"currentcolumn") instanceof OInt ? (OInt)rpt.getCache().get(this,"currentcolumn") : (OInt)null;
        return cc == null ? 0 : cc.i;
    }

    public int incrCurrentColumn(fyiReporting.RDL.Report rpt) throws Exception {
        OInt cc = rpt.getCache().get(this,"currentcolumn") instanceof OInt ? (OInt)rpt.getCache().get(this,"currentcolumn") : (OInt)null;
        if (cc == null)
        {
            setCurrentColumn(rpt,0);
            cc = rpt.getCache().get(this,"currentcolumn") instanceof OInt ? (OInt)rpt.getCache().get(this,"currentcolumn") : (OInt)null;
        }
         
        cc.i++;
        return cc.i;
    }

    public void setCurrentColumn(fyiReporting.RDL.Report rpt, int col) throws Exception {
        OInt cc = rpt.getCache().get(this,"currentcolumn") instanceof OInt ? (OInt)rpt.getCache().get(this,"currentcolumn") : (OInt)null;
        if (cc == null)
            rpt.getCache().addReplace(this,"currentcolumn",new OInt(col));
        else
            cc.i = col; 
    }

    public RSize getColumnSpacing() throws Exception {
        if (_ColumnSpacing == null)
            _ColumnSpacing = new RSize(this.OwnerReport,".5 in");
         
        return _ColumnSpacing;
    }

    public void setColumnSpacing(RSize value) throws Exception {
        _ColumnSpacing = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

}


