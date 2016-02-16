//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DynamicCategories;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.StaticCategories;

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
* CategoryGrouping definition and processing.
*/
public class CategoryGrouping  extends ReportLink 
{
    // A CategoryGrouping must have either DynamicCategories or StaticCategories
    //  but can't have both.
    DynamicCategories _DynamicCategories;
    // Dynamic Category headings for this grouping
    StaticCategories _StaticCategories;
    // Category headings for this grouping
    public CategoryGrouping(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DynamicCategories = null;
        _StaticCategories = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DynamicCategories"))
            {
                _DynamicCategories = new DynamicCategories(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("StaticCategories"))
            {
                _StaticCategories = new StaticCategories(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown CategoryGrouping element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if ((_DynamicCategories == null && _StaticCategories == null) || (_DynamicCategories != null && _StaticCategories != null))
            OwnerReport.rl.logError(8,"CategoryGrouping requires either DynamicCategories element or StaticCategories element, but not both.");
         
    }

    public void finalPass() throws Exception {
        if (_DynamicCategories != null)
            _DynamicCategories.finalPass();
         
        if (_StaticCategories != null)
            _StaticCategories.finalPass();
         
        return ;
    }

    public DynamicCategories getDynamicCategories() throws Exception {
        return _DynamicCategories;
    }

    public void setDynamicCategories(DynamicCategories value) throws Exception {
        _DynamicCategories = value;
    }

    public StaticCategories getStaticCategories() throws Exception {
        return _StaticCategories;
    }

    public void setStaticCategories(StaticCategories value) throws Exception {
        _StaticCategories = value;
    }

}


