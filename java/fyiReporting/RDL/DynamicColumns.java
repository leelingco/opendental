//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.Subtotal;
import fyiReporting.RDL.Visibility;

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
* In Matrix, the dynamic columns with what subtotal information is needed.
*/
public class DynamicColumns  extends ReportLink 
{
    Grouping _Grouping;
    // The expressions to group the data by.
    Sorting _Sorting;
    // The expressions to sort the columns by.
    Subtotal _Subtotal;
    // Indicates an automatic subtotal column should be included
    ReportItems _ReportItems;
    // The elements of the column header layout
    // This ReportItems collection must contain exactly one
    // ReportItem. The Top, Left, Height and Width for this
    // ReportItem are ignored. The position is taken to be 0,
    // 0 and the size to be 100%, 100%.
    Visibility _Visibility;
    // Indicates if all of the dynamic columns for this
    // grouping should be hidden and replaced with a
    // subtotal column for this grouping scope
    public DynamicColumns(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Grouping = null;
        _Sorting = null;
        _Subtotal = null;
        _ReportItems = null;
        _Visibility = null;
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
            else if (__dummyScrutVar0.equals("Subtotal"))
            {
                _Subtotal = new Subtotal(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ReportItems"))
            {
                _ReportItems = new ReportItems(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Visibility"))
            {
                _Visibility = new Visibility(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown DynamicColumn element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
        if (_Grouping == null)
            OwnerReport.rl.logError(8,"DynamicColumns requires the Grouping element.");
         
        if (_ReportItems == null || _ReportItems.getItems().Count != 1)
            OwnerReport.rl.logError(8,"DynamicColumns requires the ReportItems element defined with exactly one report item.");
         
    }

    public void finalPass() throws Exception {
        if (_Grouping != null)
            _Grouping.finalPass();
         
        if (_Sorting != null)
            _Sorting.finalPass();
         
        if (_Subtotal != null)
            _Subtotal.finalPass();
         
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        if (_Visibility != null)
            _Visibility.finalPass();
         
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

    public Subtotal getSubtotal() throws Exception {
        return _Subtotal;
    }

    public void setSubtotal(Subtotal value) throws Exception {
        _Subtotal = value;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

}


