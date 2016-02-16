//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import fyiReporting.RDL.Axis;
import fyiReporting.RDL.AxisTickMarksEnum;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.ChartGridLines;
import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.ChartTypeEnum;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.Row;
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
* Column chart definition and processing.
*/
public class ChartColumn  extends ChartBase 
{
    int _GapSize = 12;
    // TODO: hard code for now
    public ChartColumn(fyiReporting.RDL.Report r, Row row, Chart c, MatrixCellEntry[][] m) throws Exception {
        super(r, row, c, m);
    }

    public void draw(fyiReporting.RDL.Report rpt) throws Exception {
        createSizedBitmap();
        Graphics g = Graphics.FromImage(_bm);
        try
        {
            {
                // Adjust the top margin to depend on the title height
                Size titleSize = drawTitleMeasure(rpt,g,getChartDefn().getTitle());
                Layout.setTopMargin(titleSize.Height);
                double max = 0, min = 0;
                // Get the max and min values
                RefSupport<double> refVar___0 = new RefSupport<double>(max);
                RefSupport<double> refVar___1 = new RefSupport<double>(min);
                getValueMaxMin(rpt,refVar___0,refVar___1);
                max = refVar___0.getValue();
                min = refVar___1.getValue();
                drawChartStyle(rpt,g);
                // Draw title; routine determines if necessary
                drawTitle(rpt,g,getChartDefn().getTitle(),new System.Drawing.Rectangle(0, 0, _bm.Width, Layout.getTopMargin()));
                // Adjust the left margin to depend on the Value Axis
                Size vaSize = valueAxisSize(rpt,g,min,max);
                Layout.setLeftMargin(vaSize.Width);
                // Draw legend
                System.Drawing.Rectangle lRect = drawLegend(rpt,g,false,true);
                // Adjust the bottom margin to depend on the Category Axis
                Size caSize = categoryAxisSize(rpt,g);
                Layout.setBottomMargin(caSize.Height);
                adjustMargins(lRect);
                // Adjust margins based on legend.
                // Draw Plot area
                drawPlotAreaStyle(rpt,g,lRect);
                // Draw Value Axis
                if (vaSize.Width > 0)
                    // If we made room for the axis - we need to draw it
                    drawValueAxis(rpt,g,min,max,new System.Drawing.Rectangle(Layout.getLeftMargin() - vaSize.Width, Layout.getTopMargin(), vaSize.Width, _bm.Height - Layout.getTopMargin() - Layout.getBottomMargin()),Layout.getLeftMargin(),Layout.getWidth() - Layout.getRightMargin());
                 
                // Draw Category Axis
                if (caSize.Height > 0)
                    drawCategoryAxis(rpt,g,new System.Drawing.Rectangle(Layout.getLeftMargin(), _bm.Height - Layout.getBottomMargin(), Layout.getPlotArea().Width, caSize.Height));
                 
                if (getChartDefn().getSubtype() == ChartSubTypeEnum.Stacked)
                    drawPlotAreaStacked(rpt,g,max,min);
                else if (getChartDefn().getSubtype() == ChartSubTypeEnum.PercentStacked)
                    drawPlotAreaPercentStacked(rpt,g);
                else
                    drawPlotAreaPlain(rpt,g,max,min);  
                drawLegend(rpt,g,false,false);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    void drawPlotAreaPercentStacked(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        int barsNeeded = getCategoryCount();
        int gapsNeeded = getCategoryCount() * 2;
        // Draw Plot area data
        double max = 1;
        int widthBar = (int)((Layout.getPlotArea().Width - gapsNeeded * _GapSize) / barsNeeded);
        int maxBarHeight = (int)(Layout.getPlotArea().Height);
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            // Loop thru calculating all the data points
            int barLoc = (int)(Layout.getPlotArea().Left + (iRow - 1) * ((double)(Layout.getPlotArea().Width) / getCategoryCount()));
            barLoc += _GapSize;
            // space before series
            double sum = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                double t = getDataValue(rpt,iRow,iCol);
                if (t.CompareTo(double.NaN) == 0)
                    t = 0;
                 
                sum += t;
            }
            double v = 0;
            Point saveP = Point.Empty;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                double t = getDataValue(rpt,iRow,iCol);
                if (t.CompareTo(double.NaN) == 0)
                    t = 0;
                 
                v += t;
                int h = (int)((Math.Min(v / sum, max) / max) * maxBarHeight);
                Point p = new Point(barLoc, Layout.getPlotArea().Top + (maxBarHeight - h));
                System.Drawing.Rectangle rect = new System.Drawing.Rectangle();
                if (saveP == Point.Empty)
                    rect = new System.Drawing.Rectangle(p, new Size(widthBar, h));
                else
                    rect = new System.Drawing.Rectangle(p, new Size(widthBar, saveP.Y - p.Y)); 
                DrawColumnBar(rpt, g, getSeriesBrush()[iCol - 1], rect, iRow, iCol);
                saveP = p;
            }
        }
        return ;
    }

    void drawPlotAreaPlain(fyiReporting.RDL.Report rpt, Graphics g, double max, double min) throws Exception {
        int barsNeeded = getSeriesCount() * getCategoryCount();
        int gapsNeeded = getCategoryCount() * 2;
        // Draw Plot area data
        int widthBar = (int)((Layout.getPlotArea().Width - gapsNeeded * _GapSize) / barsNeeded);
        int maxBarHeight = (int)(Layout.getPlotArea().Height);
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            //int barLoc=Layout.LeftMargin;
            int barLoc = (int)(Layout.getPlotArea().Left + (iRow - 1) * ((double)(Layout.getPlotArea().Width) / getCategoryCount()));
            barLoc += _GapSize;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                // space before series
                double v = this.getDataValue(rpt,iRow,iCol);
                if (v.CompareTo(double.NaN) == 0)
                    v = min;
                 
                int h = (int)((Math.Min(v, max) / max) * maxBarHeight);
                DrawColumnBar(rpt, g, getSeriesBrush()[iCol - 1], new System.Drawing.Rectangle(barLoc, Layout.getPlotArea().Top + (maxBarHeight - h), widthBar, h), iRow, iCol);
                barLoc += widthBar;
            }
        }
    }

    void drawPlotAreaStacked(fyiReporting.RDL.Report rpt, Graphics g, double max, double min) throws Exception {
        int barsNeeded = getCategoryCount();
        int gapsNeeded = getCategoryCount() * 2;
        int widthBar = (int)((Layout.getPlotArea().Width - gapsNeeded * _GapSize) / barsNeeded);
        int maxBarHeight = (int)(Layout.getPlotArea().Height);
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            // Loop thru calculating all the data points
            int barLoc = (int)(Layout.getPlotArea().Left + (iRow - 1) * ((double)(Layout.getPlotArea().Width) / getCategoryCount()));
            barLoc += _GapSize;
            // space before series
            double v = 0;
            Point saveP = Point.Empty;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                double t = getDataValue(rpt,iRow,iCol);
                if (t.CompareTo(double.NaN) == 0)
                    t = 0;
                 
                v += t;
                int h = (int)((Math.Min(v, max) / max) * maxBarHeight);
                Point p = new Point(barLoc, Layout.getPlotArea().Top + (maxBarHeight - h));
                System.Drawing.Rectangle rect = new System.Drawing.Rectangle();
                if (saveP == Point.Empty)
                    rect = new System.Drawing.Rectangle(p, new Size(widthBar, h));
                else
                    rect = new System.Drawing.Rectangle(p, new Size(widthBar, saveP.Y - p.Y)); 
                DrawColumnBar(rpt, g, getSeriesBrush()[iCol - 1], rect, iRow, iCol);
                saveP = p;
            }
        }
        return ;
    }

    // Calculate the size of the category axis
    protected Size categoryAxisSize(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        _LastCategoryWidth = 0;
        Size size = Size.Empty;
        if (this.getChartDefn().getCategoryAxis() == null)
            return size;
         
        Axis a = this.getChartDefn().getCategoryAxis().getAxis();
        if (a == null)
            return size;
         
        Style s = a.getStyle();
        // Measure the title
        size = drawTitleMeasure(rpt,g,a.getTitle());
        if (!a.getVisible())
            return size;
         
        // don't need to calculate the height
        // Calculate the tallest category name
        TypeCode tc = new TypeCode();
        int maxHeight = 0;
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            RefSupport<TypeCode> refVar___2 = new RefSupport<TypeCode>();
            Object v = this.getCategoryValue(rpt,iRow,refVar___2);
            tc = refVar___2.getValue();
            Size tSize = new Size();
            if (s == null)
                tSize = Style.MeasureStringDefaults(rpt, g, v, tc, null, int.MaxValue);
            else
                tSize = s.MeasureString(rpt, g, v, tc, null, int.MaxValue); 
            if (tSize.Height > maxHeight)
                maxHeight = tSize.Height;
             
            if (iRow == getCategoryCount())
                _LastCategoryWidth = tSize.Width;
             
        }
        // Add on the tallest category name
        size.Height += maxHeight;
        // Account for tick marks
        int tickSize = 0;
        if (a.getMajorTickMarks() == AxisTickMarksEnum.Cross || a.getMajorTickMarks() == AxisTickMarksEnum.Outside)
            tickSize = this.getAxisTickMarkMajorLen();
        else if (a.getMinorTickMarks() == AxisTickMarksEnum.Cross || a.getMinorTickMarks() == AxisTickMarksEnum.Outside)
            tickSize = this.getAxisTickMarkMinorLen();
          
        size.Height += tickSize;
        return size;
    }

    // DrawCategoryAxis
    protected void drawCategoryAxis(fyiReporting.RDL.Report rpt, Graphics g, System.Drawing.Rectangle rect) throws Exception {
        if (this.getChartDefn().getCategoryAxis() == null)
            return ;
         
        Axis a = this.getChartDefn().getCategoryAxis().getAxis();
        if (a == null)
            return ;
         
        Style s = a.getStyle();
        Size tSize = drawTitleMeasure(rpt,g,a.getTitle());
        drawTitle(rpt,g,a.getTitle(),new System.Drawing.Rectangle(rect.Left, rect.Bottom - tSize.Height, rect.Width, tSize.Height));
        // Account for tick marks
        int tickSize = 0;
        if (a.getMajorTickMarks() == AxisTickMarksEnum.Cross || a.getMajorTickMarks() == AxisTickMarksEnum.Outside)
            tickSize = this.getAxisTickMarkMajorLen();
        else if (a.getMinorTickMarks() == AxisTickMarksEnum.Cross || a.getMinorTickMarks() == AxisTickMarksEnum.Outside)
            tickSize = this.getAxisTickMarkMinorLen();
          
        int drawWidth = new int();
        int catCount = getChartDefn().getType() == ChartTypeEnum.Area ? getCategoryCount() - 1 : getCategoryCount();
        drawWidth = rect.Width / catCount;
        TypeCode tc = new TypeCode();
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            RefSupport<TypeCode> refVar___3 = new RefSupport<TypeCode>();
            Object v = this.getCategoryValue(rpt,iRow,refVar___3);
            tc = refVar___3.getValue();
            int drawLoc = (int)(rect.Left + (iRow - 1) * ((double)rect.Width / catCount));
            // Draw the category text
            if (a.getVisible())
            {
                System.Drawing.Rectangle drawRect = new System.Drawing.Rectangle();
                if (getChartDefn().getType() == ChartTypeEnum.Area)
                {
                    // Area chart - value is centered under the tick mark
                    if (s != null)
                    {
                        Size size = s.MeasureString(rpt, g, v, TypeCode.Double, null, int.MaxValue);
                        drawRect = new System.Drawing.Rectangle(drawLoc - size.Width / 2, rect.Top + tickSize, size.Width, size.Height);
                    }
                    else
                    {
                        Size size = Style.MeasureStringDefaults(rpt, g, v, TypeCode.Double, null, int.MaxValue);
                        drawRect = new System.Drawing.Rectangle(drawLoc - size.Width / 2, rect.Top + tickSize, size.Width, size.Height);
                    } 
                }
                else
                    // Column/Line charts are just centered in the region.
                    drawRect = new System.Drawing.Rectangle(drawLoc, rect.Top + tickSize, drawWidth, rect.Height - tSize.Height); 
                if (s == null)
                    Style.drawStringDefaults(g,v,drawRect);
                else
                    s.drawString(rpt,g,v,tc,null,drawRect); 
            }
             
            // Draw the Major Tick Marks (if necessary)
            drawCategoryAxisTick(g,true,a.getMajorTickMarks(),new Point(drawLoc, rect.Top));
        }
        // Draw the end on (if necessary)
        drawCategoryAxisTick(g,true,a.getMajorTickMarks(),new Point(rect.Right, rect.Top));
        return ;
    }

    protected void drawCategoryAxisTick(Graphics g, boolean bMajor, AxisTickMarksEnum tickType, Point p) throws Exception {
        int len = bMajor ? getAxisTickMarkMajorLen() : getAxisTickMarkMinorLen();
        switch(tickType)
        {
            case Outside: 
                g.DrawLine(Pens.Black, new Point(p.X, p.Y), new Point(p.X, p.Y + len));
                break;
            case Inside: 
                g.DrawLine(Pens.Black, new Point(p.X, p.Y), new Point(p.X, p.Y - len));
                break;
            case Cross: 
                g.DrawLine(Pens.Black, new Point(p.X, p.Y - len), new Point(p.X, p.Y + len));
                break;
            case None: 
            default: 
                break;
        
        }
        return ;
    }

    void drawColumnBar(fyiReporting.RDL.Report rpt, Graphics g, Brush brush, System.Drawing.Rectangle rect, int iRow, int iCol) throws Exception {
        if (rect.Height <= 0)
            return ;
         
        g.FillRectangle(brush, rect);
        g.DrawRectangle(Pens.Black, rect);
        if (getChartDefn().getSubtype() == ChartSubTypeEnum.Stacked || getChartDefn().getSubtype() == ChartSubTypeEnum.PercentStacked)
        {
            drawDataPoint(rpt,g,rect,iRow,iCol);
        }
        else
        {
            Point p = new Point();
            p = new Point(rect.Left, rect.Top - 14);
            // todo: 14 is arbitrary
            drawDataPoint(rpt,g,p,iRow,iCol);
        } 
        return ;
    }

    protected void drawValueAxis(fyiReporting.RDL.Report rpt, Graphics g, double min, double max, System.Drawing.Rectangle rect, int plotLeft, int plotRight) throws Exception {
        if (this.getChartDefn().getValueAxis() == null)
            return ;
         
        Axis a = this.getChartDefn().getValueAxis().getAxis();
        if (a == null)
            return ;
         
        Style s = a.getStyle();
        double incr = (max - min) / 10;
        Size tSize = drawTitleMeasure(rpt,g,a.getTitle());
        drawTitle(rpt,g,a.getTitle(),new System.Drawing.Rectangle(rect.Left, rect.Top, tSize.Width, rect.Height));
        double v = max;
        for (int i = 0;i < 11;i++)
        {
            // TODO: hard coding 11 is too simplistic
            int h = (int)((Math.Min(v, max) / max) * rect.Height);
            if (h < 0)
            {
                // this is really some form of error
                v -= incr;
                continue;
            }
             
            if (!a.getVisible())
            {
            }
            else // nothing to do
            if (s != null)
            {
                Size size = s.MeasureString(rpt, g, v, TypeCode.Double, null, int.MaxValue);
                System.Drawing.Rectangle vRect = new System.Drawing.Rectangle(rect.Left + tSize.Width, rect.Top + rect.Height - h - size.Height / 2, rect.Width - tSize.Width, size.Height);
                s.DrawString(rpt, g, v, TypeCode.Double, null, vRect);
            }
            else
            {
                Size size = Style.MeasureStringDefaults(rpt, g, v, TypeCode.Double, null, int.MaxValue);
                System.Drawing.Rectangle vRect = new System.Drawing.Rectangle(rect.Left + tSize.Width, rect.Top + rect.Height - h - size.Height / 2, rect.Width - tSize.Width, size.Height);
                Style.drawStringDefaults(g,v,vRect);
            }  
            drawValueAxisGrid(rpt,g,a.getMajorGridLines(),new Point(plotLeft, rect.Top + rect.Height - h),new Point(plotRight, rect.Top + rect.Height - h));
            drawValueAxisTick(rpt,g,true,a.getMajorTickMarks(),a.getMajorGridLines(),new Point(plotLeft, rect.Top + rect.Height - h));
            v -= incr;
        }
        // Draw the end points of the major grid lines
        drawValueAxisGrid(rpt,g,a.getMajorGridLines(),new Point(plotLeft, rect.Top),new Point(plotLeft, rect.Bottom));
        drawValueAxisTick(rpt,g,true,a.getMajorTickMarks(),a.getMajorGridLines(),new Point(plotLeft, rect.Top));
        drawValueAxisGrid(rpt,g,a.getMajorGridLines(),new Point(plotRight, rect.Top),new Point(plotRight, rect.Bottom));
        drawValueAxisTick(rpt,g,true,a.getMajorTickMarks(),a.getMajorGridLines(),new Point(plotRight, rect.Bottom));
        return ;
    }

    protected void drawValueAxisGrid(fyiReporting.RDL.Report rpt, Graphics g, ChartGridLines gl, Point s, Point e) throws Exception {
        if (gl == null || !gl.getShowGridLines())
            return ;
         
        if (gl.getStyle() != null)
            gl.getStyle().drawStyleLine(rpt,g,null,s,e);
        else
            g.DrawLine(Pens.Black, s, e); 
        return ;
    }

    protected void drawValueAxisTick(fyiReporting.RDL.Report rpt, Graphics g, boolean bMajor, AxisTickMarksEnum tickType, ChartGridLines gl, Point p) throws Exception {
        if (tickType == AxisTickMarksEnum.None)
            return ;
         
        int len = bMajor ? getAxisTickMarkMajorLen() : getAxisTickMarkMinorLen();
        Point s = new Point(), e = new Point();
        switch(tickType)
        {
            case Inside: 
                s = new Point(p.X, p.Y);
                e = new Point(p.X + len, p.Y);
                break;
            case Cross: 
                s = new Point(p.X - len, p.Y);
                e = new Point(p.X + len, p.Y);
                break;
            case Outside: 
            default: 
                s = new Point(p.X - len, p.Y);
                e = new Point(p.X, p.Y);
                break;
        
        }
        Style style = gl.getStyle();
        if (style != null)
            style.drawStyleLine(rpt,g,null,s,e);
        else
            g.DrawLine(Pens.Black, s, e); 
        return ;
    }

    // Calculate the size of the value axis; width is max value width + title width
    //										 height is max value height
    protected Size valueAxisSize(fyiReporting.RDL.Report rpt, Graphics g, double min, double max) throws Exception {
        Size size = Size.Empty;
        if (getChartDefn().getValueAxis() == null)
            return size;
         
        Axis a = getChartDefn().getValueAxis().getAxis();
        if (a == null)
            return size;
         
        Size minSize = new Size();
        Size maxSize = new Size();
        if (!a.getVisible())
        {
            minSize = maxSize = Size.Empty;
        }
        else if (a.getStyle() != null)
        {
            minSize = a.getStyle().MeasureString(rpt, g, min, TypeCode.Double, null, int.MaxValue);
            maxSize = a.getStyle().MeasureString(rpt, g, max, TypeCode.Double, null, int.MaxValue);
        }
        else
        {
            minSize = Style.MeasureStringDefaults(rpt, g, min, TypeCode.Double, null, int.MaxValue);
            maxSize = Style.MeasureStringDefaults(rpt, g, max, TypeCode.Double, null, int.MaxValue);
        }  
        // Choose the largest
        size.Width = Math.Max(minSize.Width, maxSize.Width);
        size.Height = Math.Max(minSize.Height, maxSize.Height);
        // Now we need to add in the width of the title (if any)
        Size titleSize = drawTitleMeasure(rpt,g,a.getTitle());
        size.Width += titleSize.Width;
        return size;
    }

}


