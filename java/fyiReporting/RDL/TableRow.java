//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableCells;
import fyiReporting.RDL.TableColumns;
import fyiReporting.RDL.TableRow;
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
* TableRow represents a Row in a table.  This can be part of a header, footer, or detail definition.
*/
public class TableRow  extends ReportLink 
{
    TableCells _TableCells;
    // Contents of the row. One cell per column
    RSize _Height;
    // Height of the row
    Visibility _Visibility;
    // Indicates if the row should be hidden
    boolean _CanGrow = new boolean();
    // indicates that row height can increase in size
    List<Textbox> _GrowList = new List<Textbox>();
    // list of TextBox's that need to be checked for growth
    public TableRow(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _TableCells = null;
        _Height = null;
        _Visibility = null;
        _CanGrow = false;
        _GrowList = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableCells"))
            {
                _TableCells = new TableCells(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Height"))
            {
                _Height = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Visibility"))
            {
                _Visibility = new Visibility(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableRow element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_TableCells == null)
            OwnerReport.rl.logError(8,"TableRow requires the TableCells element.");
         
        if (_Height == null)
            OwnerReport.rl.logError(8,"TableRow requires the Height element.");
         
    }

    public void finalPass() throws Exception {
        _TableCells.finalPass();
        if (_Visibility != null)
            _Visibility.finalPass();
         
        for (Object __dummyForeachVar1 : _TableCells.getItems())
        {
            TableCell tc = (TableCell)__dummyForeachVar1;
            ReportItem ri = tc.getReportItems().getItems()[0] instanceof ReportItem ? (ReportItem)tc.getReportItems().getItems()[0] : (ReportItem)null;
            if (!(ri instanceof Textbox))
                continue;
             
            Textbox tb = ri instanceof Textbox ? (Textbox)ri : (Textbox)null;
            if (tb.getCanGrow())
            {
                if (this._GrowList == null)
                    _GrowList = new List<Textbox>();
                 
                _GrowList.Add(tb);
                _CanGrow = true;
            }
             
        }
        if (_CanGrow)
            // shrink down the resulting list
            _GrowList.TrimExcess();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        if (this.getVisibility() != null && getVisibility().isHidden(ip.report(),row))
            return ;
         
        ip.tableRowStart(this,row);
        _TableCells.run(ip,row);
        ip.tableRowEnd(this,row);
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        if (this.getVisibility() != null && getVisibility().isHidden(pgs.getReport(),row))
            return ;
         
        _TableCells.runPage(pgs,row);
        WorkClass wc = getWC(pgs.getReport());
        pgs.getCurrentPage().setYOffset(pgs.getCurrentPage().getYOffset() + wc.CalcHeight);
        return ;
    }

    public TableCells getTableCells() throws Exception {
        return _TableCells;
    }

    public void setTableCells(TableCells value) throws Exception {
        _TableCells = value;
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    public float heightOfRow(Pages pgs, Row r) throws Exception {
        WorkClass wc = getWC(pgs.getReport());
        if (this.getVisibility() != null && getVisibility().isHidden(pgs.getReport(),r))
        {
            wc.CalcHeight = 0;
            return 0;
        }
         
        float defnHeight = _Height.getPoints();
        if (!_CanGrow)
        {
            wc.CalcHeight = defnHeight;
            return defnHeight;
        }
         
        TableColumns tcs = this.getTable().getTableColumns();
        float height = 0;
        for (Object __dummyForeachVar2 : this._GrowList)
        {
            Textbox tb = (Textbox)__dummyForeachVar2;
            int ci = tb.getTC().getColIndex();
            if (tcs.get___idx(ci).isHidden(pgs.getReport(),r))
                continue;
             
            // if column is hidden don't use in calculation
            height = Math.Max(height, tb.runTextCalcHeight(pgs.getReport(),pgs.getG(),r));
        }
        wc.CalcHeight = Math.Max(height, defnHeight);
        return wc.CalcHeight;
    }

    public float heightCalc(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        return wc.CalcHeight;
    }

    private Table getTable() throws Exception {
        ReportLink p = this.Parent;
        while (p != null)
        {
            if (p instanceof Table)
                return p instanceof Table ? (Table)p : (Table)null;
             
            p = p.Parent;
        }
        throw new Exception("Internal error: TableRow not related to a Table");
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

    public boolean getCanGrow() throws Exception {
        return _CanGrow;
    }

    public List<Textbox> getGrowList() throws Exception {
        return _GrowList;
    }

    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass(this);
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public float CalcHeight = new float();
        // dynamic when CanGrow true
        public WorkClass(TableRow tr) throws Exception {
            CalcHeight = tr.getHeight().getPoints();
        }
    
    }

}


