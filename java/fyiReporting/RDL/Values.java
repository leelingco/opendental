//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
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
* Ordered list of values used as a default for a parameter
*/
public class Values  extends ReportLink implements System.Collections.Generic.ICollection<Expression>
{
    List<Expression> _Items = new List<Expression>();
    // list of expression items
    public Values(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        Expression v;
        _Items = new List<Expression>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Value"))
            {
                v = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else
            {
                v = null;
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Value element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (v != null)
                _Items.Add(v);
             
        }
        if (_Items.Count > 0)
            _Items.TrimExcess();
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            Expression e = (Expression)__dummyForeachVar1;
            e.finalPass();
        }
        return ;
    }

    public List<Expression> getItems() throws Exception {
        return _Items;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _Items.GetEnumerator();
    }

    public void add(Expression item) throws Exception {
        _Items.Add(item);
    }

    public void clear() throws Exception {
        _Items.Clear();
    }

    public boolean contains(Expression item) throws Exception {
        return _Items.Contains(item);
    }

    public void copyTo(Expression[] array, int arrayIndex) throws Exception {
        _Items.CopyTo(array, arrayIndex);
    }

    public int getCount() throws Exception {
        return _Items.Count;
    }

    public boolean getIsReadOnly() throws Exception {
        return false;
    }

    public boolean remove(Expression item) throws Exception {
        return _Items.Remove(item);
    }

    IEnumerator<Expression> iEnumerable<Expression>___GetEnumerator() throws Exception {
        return _Items.GetEnumerator();
    }

}


