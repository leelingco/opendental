//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.AxisTickMarks;
import fyiReporting.RDL.AxisTickMarksEnum;
import fyiReporting.RDL.ChartGridLines;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.Title;
import fyiReporting.RDL.XmlUtil;

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
* Axis definition and processing.
*/
public class Axis  extends ReportLink 
{
    boolean _Visible = new boolean();
    // Whether the axis labels are displayed. Defaults to false.
    Style _Style;
    // Defines text style properties for the axis labels
    // and line style properties for the axis line.
    Title _Title;
    // Defines a title for the axis
    boolean _Margin = new boolean();
    // Indicates whether an axis margin will be
    //	created. The size of the margin is automatically
    //	generated based on the Scale and the number of
    //	data points. Defaults to false.
    AxisTickMarksEnum _MajorTickMarks = AxisTickMarksEnum.None;
    // None (Default)
    AxisTickMarksEnum _MinorTickMarks = AxisTickMarksEnum.None;
    // None (Default)
    ChartGridLines _MajorGridLines;
    // Indicates major gridlines should be displayed for this axis.
    ChartGridLines _MinorGridLines;
    // Indicates minor gridlines should be displayed for this axis.
    Expression _MajorInterval;
    // Unit for major gridlines/tickmarks
    // If omitted, the axis is autodivided
    Expression _MinorInterval;
    // Unit for minor gridlines/tickmarks
    // If omitted, the axis is autodivided
    boolean _Reverse = new boolean();
    // If false (Default) the axis is plotted normally, if
    //  true its direction is reversed.
    int _CrossAt = new int();
    //  Value at which to cross the other axis
    // If omitted, uses the default behavior for the chart type.
    boolean _Interlaced = new boolean();
    // If this property is true then strip lines are drawn
    //every other grid line interval for the axis. If grid
    //lines are not used for the axis then the axis’ tick
    //marks or labels are used to determine the
    //interlaced strip lines interval.
    //Defaults to False.
    boolean _Scalar = new boolean();
    // Indicates the values along this axis are scalar
    //values (i.e. numeric or date) which should be
    //displayed on the chart in a continuous axis.
    //Scalar cannot be true if the axis has more than
    //one grouping, if that grouping is static or has
    //more than one group expression or if the axis
    //values have a label.
    Expression _Min;
    // Minimum value for the axis
    // If omitted, the axis autoscales
    Expression _Max;
    // Maximum value for the axis
    // If omitted, the axis autoscales
    boolean _LogScale = new boolean();
    // Whether the axis is logarithmic. Default is false.
    public Axis(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Visible = false;
        _Style = null;
        _Title = null;
        _Margin = false;
        _MajorTickMarks = AxisTickMarksEnum.None;
        _MinorTickMarks = AxisTickMarksEnum.None;
        _MajorGridLines = null;
        _MinorGridLines = null;
        _MajorInterval = null;
        _MinorInterval = null;
        _Reverse = false;
        _CrossAt = 0;
        _Interlaced = false;
        _Scalar = false;
        _Min = null;
        _Max = null;
        _LogScale = false;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Visible"))
            {
                _Visible = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Title"))
            {
                _Title = new Title(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Margin"))
            {
                _Margin = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("MajorTickMarks"))
            {
                _MajorTickMarks = AxisTickMarks.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("MinorTickMarks"))
            {
                _MinorTickMarks = AxisTickMarks.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("MajorGridLines"))
            {
                _MajorGridLines = new ChartGridLines(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("MinorGridLines"))
            {
                _MinorGridLines = new ChartGridLines(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("MajorInterval"))
            {
                _MajorInterval = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Integer);
                OwnerReport.rl.logError(4,"Axis element MajorInterval is currently ignored.");
            }
            else if (__dummyScrutVar0.equals("MinorInterval"))
            {
                _MinorInterval = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Integer);
                OwnerReport.rl.logError(4,"Axis element MinorInterval is currently ignored.");
            }
            else if (__dummyScrutVar0.equals("Reverse"))
            {
                _Reverse = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("CrossAt"))
            {
                _CrossAt = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Interlaced"))
            {
                _Interlaced = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Scalar"))
            {
                _Scalar = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Min"))
            {
                _Min = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Integer);
            }
            else if (__dummyScrutVar0.equals("Max"))
            {
                _Max = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Integer);
            }
            else if (__dummyScrutVar0.equals("LogScale"))
            {
                _LogScale = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Axis element '" + xNodeLoop.Name + "' ignored.");
            }                 
        }
    }

    public void finalPass() throws Exception {
        if (_MajorInterval != null)
            _MajorInterval.finalPass();
         
        if (_MinorInterval != null)
            _MinorInterval.finalPass();
         
        if (_Max != null)
            _Max.finalPass();
         
        if (_Min != null)
            _Min.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        if (_Title != null)
            _Title.finalPass();
         
        if (_MajorGridLines != null)
            _MajorGridLines.finalPass();
         
        if (_MinorGridLines != null)
            _MinorGridLines.finalPass();
         
        return ;
    }

    public boolean getVisible() throws Exception {
        return _Visible;
    }

    public void setVisible(boolean value) throws Exception {
        _Visible = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public Title getTitle() throws Exception {
        return _Title;
    }

    public void setTitle(Title value) throws Exception {
        _Title = value;
    }

    public boolean getMargin() throws Exception {
        return _Margin;
    }

    public void setMargin(boolean value) throws Exception {
        _Margin = value;
    }

    public AxisTickMarksEnum getMajorTickMarks() throws Exception {
        return _MajorTickMarks;
    }

    public void setMajorTickMarks(AxisTickMarksEnum value) throws Exception {
        _MajorTickMarks = value;
    }

    public AxisTickMarksEnum getMinorTickMarks() throws Exception {
        return _MinorTickMarks;
    }

    public void setMinorTickMarks(AxisTickMarksEnum value) throws Exception {
        _MinorTickMarks = value;
    }

    public ChartGridLines getMajorGridLines() throws Exception {
        return _MajorGridLines;
    }

    public void setMajorGridLines(ChartGridLines value) throws Exception {
        _MajorGridLines = value;
    }

    public ChartGridLines getMinorGridLines() throws Exception {
        return _MinorGridLines;
    }

    public void setMinorGridLines(ChartGridLines value) throws Exception {
        _MinorGridLines = value;
    }

    public Expression getMajorInterval() throws Exception {
        return _MajorInterval;
    }

    public void setMajorInterval(Expression value) throws Exception {
        _MajorInterval = value;
    }

    public Expression getMinorInterval() throws Exception {
        return _MinorInterval;
    }

    public void setMinorInterval(Expression value) throws Exception {
        _MinorInterval = value;
    }

    public boolean getReverse() throws Exception {
        return _Reverse;
    }

    public void setReverse(boolean value) throws Exception {
        _Reverse = value;
    }

    public int getCrossAt() throws Exception {
        return _CrossAt;
    }

    public void setCrossAt(int value) throws Exception {
        _CrossAt = value;
    }

    public boolean getInterlaced() throws Exception {
        return _Interlaced;
    }

    public void setInterlaced(boolean value) throws Exception {
        _Interlaced = value;
    }

    public boolean getScalar() throws Exception {
        return _Scalar;
    }

    public void setScalar(boolean value) throws Exception {
        _Scalar = value;
    }

    public Expression getMin() throws Exception {
        return _Min;
    }

    public void setMin(Expression value) throws Exception {
        _Min = value;
    }

    public Expression getMax() throws Exception {
        return _Max;
    }

    public void setMax(Expression value) throws Exception {
        _Max = value;
    }

    public int maxEval(fyiReporting.RDL.Report r, Row row) throws Exception {
        if (_Max == null)
            return int.MinValue;
         
        return (int)_Max.evaluateDouble(r,row);
    }

    public int minEval(fyiReporting.RDL.Report r, Row row) throws Exception {
        if (_Min == null)
            return int.MinValue;
         
        return (int)_Min.evaluateDouble(r,row);
    }

    public boolean getLogScale() throws Exception {
        return _LogScale;
    }

    public void setLogScale(boolean value) throws Exception {
        _LogScale = value;
    }

}


