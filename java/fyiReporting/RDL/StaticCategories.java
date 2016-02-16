//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.StaticMember;

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
* Collection of Chart static categories.
*/
public class StaticCategories  extends ReportLink 
{
    List<StaticMember> _Items = new List<StaticMember>();
    // list of StaticMember
    public StaticCategories(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        StaticMember sm;
        _Items = new List<StaticMember>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("StaticMember"))
            {
                sm = new StaticMember(r,this,xNodeLoop);
            }
            else
            {
                sm = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown StaticCategories element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (sm != null)
                _Items.Add(sm);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For StaticCategories at least one StaticMember is required.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            StaticMember sm = (StaticMember)__dummyForeachVar1;
            sm.finalPass();
        }
        return ;
    }

    public List<StaticMember> getItems() throws Exception {
        return _Items;
    }

}


