//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
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
* Matrix static column definition.
*/
public class StaticColumn  extends ReportLink 
{
    ReportItems _ReportItems;
    // The elements of the column header layout
    // This ReportItems collection must contain exactly
    // one ReportItem. The Top, Left, Height and Width
    // for this ReportItem are ignored. The position is
    // taken to be 0, 0 and the size to be 100%, 100%.
    public StaticColumn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _ReportItems = null;
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
            else
            {
            } 
        }
        if (_ReportItems == null)
            OwnerReport.rl.logError(8,"StaticColumn requires the ReportItems element.");
         
    }

    public void finalPass() throws Exception {
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        return ;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

}


