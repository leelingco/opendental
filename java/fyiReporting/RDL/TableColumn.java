//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Visibility;
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
* TableColumn definition and processing.
*/
public class TableColumn  extends ReportLink 
{
    RSize _Width;
    // Width of the column
    Visibility _Visibility;
    // Indicates if the column should be hidden
    boolean _FixedHeader = false;
    // Header of this column should be display even when scrolled
    public TableColumn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Width = null;
        _Visibility = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Width"))
            {
                _Width = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Visibility"))
            {
                _Visibility = new Visibility(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("FixedHeader"))
            {
                _FixedHeader = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableColumn element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_Width == null)
            OwnerReport.rl.logError(8,"TableColumn requires the Width element.");
         
    }

    public void finalPass() throws Exception {
        if (_Visibility != null)
            _Visibility.finalPass();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
    }

    public RSize getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(RSize value) throws Exception {
        _Width = value;
    }

    public float getXPosition(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        return wc.XPosition;
    }

    public void setXPosition(fyiReporting.RDL.Report rpt, float xp) throws Exception {
        WorkClass wc = getWC(rpt);
        wc.XPosition = xp;
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

    public boolean isHidden(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Visibility == null)
            return false;
         
        return _Visibility.isHidden(rpt,r);
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
        public float XPosition = new float();
        // Set at runtime by Page processing; potentially dynamic at runtime
        //  since visibility is an expression
        public WorkClass() throws Exception {
            XPosition = 0;
        }
    
    }

}


