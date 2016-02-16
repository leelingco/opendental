//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.SubtotalPosition;
import fyiReporting.RDL.SubtotalPositionEnum;

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
* Definition of a subtotal column or row.
*/
public class Subtotal  extends ReportLink 
{
    ReportItems _ReportItems;
    // The header cell for a subtotal column or row.
    // This ReportItems collection must contain
    // exactly one Textbox. The Top, Left, Height
    // and Width for this ReportItem are ignored.
    // The position is taken to be 0, 0 and the size to
    // be 100%, 100%.
    Style _Style;
    // Style properties that override the style
    // properties for all top-level report items
    // contained in the subtotal column/row
    // At Subtotal Column/Row intersections, Row
    // style takes priority
    SubtotalPositionEnum _Position = SubtotalPositionEnum.Before;
    // Before | After (default)
    // Indicates whether this subtotal column/row
    // should appear before (left/above) or after
    // (right/below) the detail columns/rows.
    String _DataElementName = new String();
    // The name to use for this subtotal.
    //  Default: “Total”
    DataElementOutputEnum _DataElementOutput = DataElementOutputEnum.Output;
    // Indicates whether the subtotal should appear in a data rendering.
    // Default: NoOutput
    public Subtotal(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _ReportItems = null;
        _Style = null;
        _Position = SubtotalPositionEnum.After;
        _DataElementName = "Total";
        _DataElementOutput = DataElementOutputEnum.NoOutput;
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
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Position"))
            {
                _Position = SubtotalPosition.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("DataElementName"))
            {
                _DataElementName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("DataElementOutput"))
            {
                _DataElementOutput = fyiReporting.RDL.DataElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
            }     
        }
        if (_ReportItems == null)
            OwnerReport.rl.logError(8,"Subtotal requires the ReportItems element.");
         
    }

    public void finalPass() throws Exception {
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public SubtotalPositionEnum getPosition() throws Exception {
        return _Position;
    }

    public void setPosition(SubtotalPositionEnum value) throws Exception {
        _Position = value;
    }

    public String getDataElementName() throws Exception {
        return _DataElementName;
    }

    public void setDataElementName(String value) throws Exception {
        _DataElementName = value;
    }

    public DataElementOutputEnum getDataElementOutput() throws Exception {
        return _DataElementOutput;
    }

    public void setDataElementOutput(DataElementOutputEnum value) throws Exception {
        _DataElementOutput = value;
    }

}


