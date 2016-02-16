//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Sorting;

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
* In Charts, the dynamic series definition.
*/
public class DynamicSeries  extends ReportLink 
{
    Grouping _Grouping;
    // The expressions to group the data by. Page
    // breaks in the grouping are not allowed.
    Sorting _Sorting;
    // The expressions to sort the columns by
    Expression _Label;
    // (string) The label displayed on the legend.
    public DynamicSeries(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Grouping = null;
        _Sorting = null;
        _Label = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Grouping"))
            {
                _Grouping = new Grouping(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Sorting"))
            {
                _Sorting = new Sorting(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Label"))
            {
                _Label = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else
            {
            }   
        }
        if (_Grouping == null)
            OwnerReport.rl.logError(8,"DynamicSeries requires the Grouping element.");
         
        if (_Label == null)
            OwnerReport.rl.logError(8,"DynamicSeries requires the Label element.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Grouping != null)
            _Grouping.finalPass();
         
        if (_Sorting != null)
            _Sorting.finalPass();
         
        if (_Label != null)
            _Label.finalPass();
         
        return ;
    }

    public Grouping getGrouping() throws Exception {
        return _Grouping;
    }

    public void setGrouping(Grouping value) throws Exception {
        _Grouping = value;
    }

    public Sorting getSorting() throws Exception {
        return _Sorting;
    }

    public void setSorting(Sorting value) throws Exception {
        _Sorting = value;
    }

    public Expression getLabel() throws Exception {
        return _Label;
    }

    public void setLabel(Expression value) throws Exception {
        _Label = value;
    }

}


