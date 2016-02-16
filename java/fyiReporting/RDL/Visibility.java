//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;

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
* Visibility definition and processing.
*/
public class Visibility  extends ReportLink 
{
    Expression _Hidden;
    // (Boolean) Indicates if the item should be initially hidden.
    String _ToggleItem = new String();
    // The name of the textbox used to
    // hide/unhide this report item. Clicking on
    //an instance of the ToggleItem will toggle
    //the hidden state of every corresponding
    //instance of this item. If the Toggle item
    //becomes hidden, this item should become
    //hidden.
    //Must be a textbox in the same grouping
    //scope as this item or in any containing (ancestor) grouping scope
    //If omitted, no item will toggle the hidden
    //state of this item.
    //Not allowed on and cannot refer to report
    //items contained in a page header or
    //footer.
    //Cannot refer to a report item contained
    //within the current report item unless
    //current grouping scope has a Parent.
    public Visibility(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Hidden = null;
        _ToggleItem = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Hidden"))
            {
                _Hidden = new Expression(r,this,xNodeLoop,ExpressionType.Boolean);
            }
            else if (__dummyScrutVar0.equals("ToggleItem"))
            {
                _ToggleItem = xNodeLoop.InnerText;
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Visibility element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Hidden != null)
            _Hidden.finalPass();
         
        return ;
    }

    public Expression getHidden() throws Exception {
        return _Hidden;
    }

    public void setHidden(Expression value) throws Exception {
        _Hidden = value;
    }

    public boolean isHidden(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Hidden == null)
            return false;
         
        return _Hidden.evaluateBoolean(rpt,r);
    }

    public String getToggleItem() throws Exception {
        return _ToggleItem;
    }

    public void setToggleItem(String value) throws Exception {
        _ToggleItem = value;
    }

}


