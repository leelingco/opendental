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
* Toggle image definition.
*/
public class ToggleImage  extends ReportLink 
{
    Expression _InitialState;
    //(Boolean)
    //A Boolean expression, the value of which
    //determines the initial state of the toggle image.
    //True = “expanded” (i.e. a minus sign). False =
    //“collapsed” (i.e. a plus sign)
    public ToggleImage(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _InitialState = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("InitialState"))
            {
                _InitialState = new Expression(r,this,xNodeLoop,ExpressionType.Boolean);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ToggleImage element '" + xNodeLoop.Name + "' ignored.");
            } 
        }
        if (_InitialState == null)
            OwnerReport.rl.logError(8,"ToggleImage requires the InitialState element.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_InitialState != null)
            _InitialState.finalPass();
         
        return ;
    }

    public Expression getInitialState() throws Exception {
        return _InitialState;
    }

    public void setInitialState(Expression value) throws Exception {
        _InitialState = value;
    }

}


