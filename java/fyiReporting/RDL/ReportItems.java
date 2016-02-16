//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartExpression;
import fyiReporting.RDL.CustomReportItem;
import fyiReporting.RDL.Image;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.OFloat;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.Textbox;

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
* Collection of specific reportitems (e.g. TextBoxs, Images, ...)
*/
public class ReportItems  extends ReportLink 
{
    List<ReportItem> _Items = new List<ReportItem>();
    // list of report items
    public ReportItems(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        ReportItem ri;
        _Items = new List<ReportItem>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Rectangle"))
            {
                ri = new Rectangle(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Line"))
            {
                ri = new Line(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Textbox"))
            {
                ri = new Textbox(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Image"))
            {
                ri = new Image(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Subreport"))
            {
                ri = new Subreport(r,this,xNodeLoop);
            }
            else // DataRegions: list, table, matrix, chart
            if (__dummyScrutVar0.equals("List"))
            {
                ri = new List(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Table"))
            {
                ri = new Table(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Matrix"))
            {
                ri = new Matrix(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Chart"))
            {
                ri = new Chart(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ChartExpression"))
            {
                // For internal use only
                ri = new ChartExpression(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("CustomReportItem"))
            {
                ri = new CustomReportItem(r,this,xNodeLoop);
            }
            else
            {
                ri = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ReportItems element '" + xNodeLoop.Name + "' ignored.");
            }           
            if (ri != null)
            {
                _Items.Add(ri);
            }
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"At least one item must be in the ReportItems.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            ReportItem ri = (ReportItem)__dummyForeachVar1;
            ri.finalPass();
        }
        _Items.Sort();
        for (int i = 0;i < _Items.Count;i++)
        {
            // sort on ZIndex; y, x (see ReportItem compare routine)
            ReportItem ri = _Items[i];
            ri.positioningFinalPass(i,_Items);
        }
        return ;
    }

    //foreach (ReportItem ri in _Items)
    //    ri.PositioningFinalPass(_Items);
    public void run(IPresent ip, Row row) throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            ReportItem ri = (ReportItem)__dummyForeachVar2;
            ri.run(ip,row);
        }
        return ;
    }

    public void runPage(Pages pgs, Row row, float xOffset) throws Exception {
        setXOffset(pgs.getReport(),xOffset);
        for (Object __dummyForeachVar3 : _Items)
        {
            ReportItem ri = (ReportItem)__dummyForeachVar3;
            ri.runPage(pgs,row);
        }
        return ;
    }

    public List<ReportItem> getItems() throws Exception {
        return _Items;
    }

    public float getXOffset(fyiReporting.RDL.Report rpt) throws Exception {
        OFloat of = rpt.getCache().get(this,"xoffset") instanceof OFloat ? (OFloat)rpt.getCache().get(this,"xoffset") : (OFloat)null;
        return of == null ? 0 : of.f;
    }

    public void setXOffset(fyiReporting.RDL.Report rpt, float f) throws Exception {
        OFloat of = rpt.getCache().get(this,"xoffset") instanceof OFloat ? (OFloat)rpt.getCache().get(this,"xoffset") : (OFloat)null;
        if (of == null)
            rpt.getCache().add(this,"xoffset",new OFloat(f));
        else
            of.f = f; 
    }

}


