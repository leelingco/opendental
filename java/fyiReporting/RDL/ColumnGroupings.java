//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ColumnGrouping;
import fyiReporting.RDL.MatrixEntry;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

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
* ColumnGroupings definition and processing.
*/
public class ColumnGroupings  extends ReportLink 
{
    List<ColumnGrouping> _Items = new List<ColumnGrouping>();
    // list of report items
    int _StaticCount = new int();
    public ColumnGroupings(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        ColumnGrouping g;
        _Items = new List<ColumnGrouping>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("ColumnGrouping"))
            {
                g = new ColumnGrouping(r,this,xNodeLoop);
            }
            else
            {
                g = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ColumnGroupings element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (g != null)
                _Items.Add(g);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For ColumnGroups at least one ColumnGrouping is required.");
        else
        {
            _Items.TrimExcess();
            _StaticCount = getStaticCount();
        } 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            ColumnGrouping g = (ColumnGrouping)__dummyForeachVar1;
            g.finalPass();
        }
        return ;
    }

    public float defnHeight() throws Exception {
        float height = 0;
        for (Object __dummyForeachVar2 : _Items)
        {
            ColumnGrouping g = (ColumnGrouping)__dummyForeachVar2;
            height += g.getHeight().getPoints();
        }
        return height;
    }

    /**
    * Calculates the number of static columns
    * 
    *  @return
    */
    private int getStaticCount() throws Exception {
        for (Object __dummyForeachVar3 : _Items)
        {
            // Find the static column
            ColumnGrouping cg = (ColumnGrouping)__dummyForeachVar3;
            if (cg.getStaticColumns() == null)
                continue;
             
            return cg.getStaticColumns().getItems().Count;
        }
        return 0;
    }

    /**
    * # of static columns;  0 if no static columns defined
    */
    public int getStaticCount() throws Exception {
        return _StaticCount;
    }

    public List<ColumnGrouping> getItems() throws Exception {
        return _Items;
    }

    public MatrixEntry getME(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        return wc.ME;
    }

    public void setME(fyiReporting.RDL.Report rpt, MatrixEntry me) throws Exception {
        WorkClass wc = getWC(rpt);
        wc.ME = me;
    }

    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        if (rpt == null)
            return new WorkClass();
         
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public MatrixEntry ME;
        // Used at runtime to contain data values
        public WorkClass() throws Exception {
            ME = null;
        }
    
    }

}


