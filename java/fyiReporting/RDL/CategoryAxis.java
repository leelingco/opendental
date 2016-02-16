//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Axis;
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
* CategoryAxis definition and processing.
*/
public class CategoryAxis  extends ReportLink 
{
    Axis _Axis;
    // Display properties for the category axis
    public CategoryAxis(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Axis = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Axis"))
            {
                _Axis = new Axis(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown CategoryAxis element '" + xNodeLoop.Name + "' ignored.");
            } 
        }
    }

    public void finalPass() throws Exception {
        if (_Axis != null)
            _Axis.finalPass();
         
        return ;
    }

    public Axis getAxis() throws Exception {
        return _Axis;
    }

    public void setAxis(Axis value) throws Exception {
        _Axis = value;
    }

}


