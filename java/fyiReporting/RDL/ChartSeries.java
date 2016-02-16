//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataPoints;
import fyiReporting.RDL.PlotTypeEnum;
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
* Chart series definition and processing.
*/
public class ChartSeries  extends ReportLink 
{
    DataPoints _Datapoints;
    // Data points within a series
    PlotTypeEnum _PlotType = PlotTypeEnum.Auto;
    // Indicates whether the series should be plotted
    // as a line in a Column chart. If set to auto,
    // should be plotted per the primary chart type.
    // Auto (Default) | Line
    public ChartSeries(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Datapoints = null;
        _PlotType = PlotTypeEnum.Auto;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataPoints"))
            {
                _Datapoints = new DataPoints(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("PlotType"))
            {
                _PlotType = fyiReporting.RDL.PlotType.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ChartSeries element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if (_Datapoints == null)
            OwnerReport.rl.logError(8,"ChartSeries requires the DataPoints element.");
         
    }

    public void finalPass() throws Exception {
        if (_Datapoints != null)
            _Datapoints.finalPass();
         
        return ;
    }

    public DataPoints getDatapoints() throws Exception {
        return _Datapoints;
    }

    public void setDatapoints(DataPoints value) throws Exception {
        _Datapoints = value;
    }

    public PlotTypeEnum getPlotType() throws Exception {
        return _PlotType;
    }

    public void setPlotType(PlotTypeEnum value) throws Exception {
        _PlotType = value;
    }

}


