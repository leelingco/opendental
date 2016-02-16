//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.GroupExpression;
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
* Collection of group expressions.
*/
public class GroupExpressions  extends ReportLink 
{
    List<GroupExpression> _Items = new List<GroupExpression>();
    // list of GroupExpression
    public GroupExpressions(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        GroupExpression g;
        _Items = new List<GroupExpression>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("GroupExpression"))
            {
                g = new GroupExpression(r,this,xNodeLoop);
            }
            else
            {
                g = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown GroupExpressions element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (g != null)
                _Items.Add(g);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"GroupExpressions require at least one GroupExpression be defined.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            GroupExpression g = (GroupExpression)__dummyForeachVar1;
            g.finalPass();
        }
        return ;
    }

    public List<GroupExpression> getItems() throws Exception {
        return _Items;
    }

}


