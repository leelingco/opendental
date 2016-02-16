//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Action;
import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.DataLabel;
import fyiReporting.RDL.DataValues;
import fyiReporting.RDL.Marker;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Style;

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
* DataPoint definition and processing.
*/
public class DataPoint  extends ReportLink 
{
    DataValues _DataValues;
    //Data value set for the Y axis.
    DataLabel _DataLabel;
    // Indicates the values should be marked with data labels.
    Action _Action;
    // Action to execute.
    Style _Style;
    // Defines border and background style
    // properties for the data point.
    Marker _Marker;
    // Defines marker properties. Markers do
    //	not apply to data points of pie, doughnut
    //	and any stacked chart types.
    String _DataElementName = new String();
    // The name to use for the data element for
    //	this data point.
    //	Default: Name of corresponding static
    //	series or category. If there is no static
    //	series or categories, “Value”
    DataElementOutputEnum _DataElementOutput = DataElementOutputEnum.Output;
    // Indicates whether the data point should
    // appear in a data rendering.
    public DataPoint(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataValues = null;
        _DataLabel = null;
        _Action = null;
        _Style = null;
        _Marker = null;
        _DataElementName = null;
        _DataElementOutput = DataElementOutputEnum.Output;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataValues"))
            {
                _DataValues = new DataValues(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("DataLabel"))
            {
                _DataLabel = new DataLabel(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Action"))
            {
                _Action = new Action(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Marker"))
            {
                _Marker = new Marker(r,this,xNodeLoop);
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
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown DataPoint element '" + xNodeLoop.Name + "' ignored.");
            }       
        }
        if (_DataValues == null)
            OwnerReport.rl.logError(8,"ChartSeries requires the DataValues element.");
         
    }

    public void finalPass() throws Exception {
        if (_DataValues != null)
            _DataValues.finalPass();
         
        if (_DataLabel != null)
            _DataLabel.finalPass();
         
        if (_Action != null)
            _Action.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        if (_Marker != null)
            _Marker.finalPass();
         
        return ;
    }

    public DataValues getDataValues() throws Exception {
        return _DataValues;
    }

    public void setDataValues(DataValues value) throws Exception {
        _DataValues = value;
    }

    public DataLabel getDataLabel() throws Exception {
        return _DataLabel;
    }

    public void setDataLabel(DataLabel value) throws Exception {
        _DataLabel = value;
    }

    public Action getAction() throws Exception {
        return _Action;
    }

    public void setAction(Action value) throws Exception {
        _Action = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public Marker getMarker() throws Exception {
        return _Marker;
    }

    public void setMarker(Marker value) throws Exception {
        _Marker = value;
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


