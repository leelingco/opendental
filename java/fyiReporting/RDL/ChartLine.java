//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartColumn;
import fyiReporting.RDL.ChartMarkerEnum;
import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.ChartTypeEnum;
import fyiReporting.RDL.MatrixCellEntry;
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
* Line chart definition and processing.
*/
public class ChartLine  extends ChartColumn 
{
    public ChartLine(fyiReporting.RDL.Report r, Row row, Chart c, MatrixCellEntry[][] m) throws Exception {
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
                drawTitle(rpt,g,getChartDefn().getTitle(),new System.Drawing.Rectangle(0, 0, Layout.getWidth(), Layout.getTopMargin()));
                // Adjust the left margin to depend on the Value Axis
                Size vaSize = valueAxisSize(rpt,g,min,max);
                Layout.setLeftMargin(vaSize.Width);
                // Draw legend
                System.Drawing.Rectangle lRect = drawLegend(rpt,g,getChartDefn().getType() == ChartTypeEnum.Area ? false : true,true);
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
                    DrawValueAxis(rpt, g, min, max, new System.Drawing.Rectangle(Layout.getLeftMargin() - vaSize.Width, Layout.getTopMargin(), vaSize.Width, Layout.getPlotArea().Height), Layout.getLeftMargin(), _bm.Width - Layout.getRightMargin());
                 
                // Draw Category Axis
                if (caSize.Height > 0)
                    drawCategoryAxis(rpt,g,new System.Drawing.Rectangle(Layout.getLeftMargin(), _bm.Height - Layout.getBottomMargin(), Layout.getPlotArea().Width, caSize.Height));
                 
                // Draw Plot area data
                if (getChartDefn().getType() == ChartTypeEnum.Area)
                {
                    if (getChartDefn().getSubtype() == ChartSubTypeEnum.Stacked)
                        drawPlotAreaAreaStacked(rpt,g,max);
                    else if (getChartDefn().getSubtype() == ChartSubTypeEnum.PercentStacked)
                        drawPlotAreaAreaPercentStacked(rpt,g);
                    else
                        drawPlotAreaArea(rpt,g,max);  
                }
                else
                {
                    drawPlotAreaLine(rpt,g,max);
                } 
                drawLegend(rpt,g,getChartDefn().getType() == ChartTypeEnum.Area ? false : true,false);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    void drawPlotAreaArea(fyiReporting.RDL.Report rpt, Graphics g, double max) throws Exception {
        // Draw Plot area data
        int maxPointHeight = (int)Layout.getPlotArea().Height;
        double widthCat = ((double)(Layout.getPlotArea().Width) / (getCategoryCount() - 1));
        Point[] saveP = new Point[getCategoryCount()];
        for (int iCol = 1;iCol <= getSeriesCount();iCol++)
        {
            for (int iRow = 1;iRow <= getCategoryCount();iRow++)
            {
                // used for drawing lines between points
                double v = this.getDataValue(rpt,iRow,iCol);
                int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat);
                int y = (int)((Math.Min(v, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveP[iRow - 1] = p;
                DrawLinePoint(rpt, g, getSeriesBrush()[iCol - 1], ChartMarkerEnum.None, p, iRow, iCol);
            }
            DrawAreaBetweenPoints(g, getSeriesBrush()[iCol - 1], saveP, null);
        }
        return ;
    }

    void drawPlotAreaAreaPercentStacked(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        double max = 1;
        // 100% is the max
        // Draw Plot area data
        int maxPointHeight = (int)Layout.getPlotArea().Height;
        double widthCat = ((double)(Layout.getPlotArea().Width) / (getCategoryCount() - 1));
        Point[][] saveAllP = new Point[getCategoryCount(), getSeriesCount()];
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            // used to collect all data points
            // Loop thru calculating all the data points
            int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat);
            double sum = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                sum += getDataValue(rpt,iRow,iCol);
            }
            double v = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                v += getDataValue(rpt,iRow,iCol);
                int y = (int)((Math.Min(v / sum, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveAllP[iRow - 1, iCol - 1] = p;
            }
        }
        // Now loop thru and plot all the points
        Point[] saveP = new Point[getCategoryCount()];
        // used for drawing lines between points
        Point[] priorSaveP = new Point[getCategoryCount()];
        for (int iCol = 1;iCol <= getSeriesCount();iCol++)
        {
            for (int iRow = 1;iRow <= getCategoryCount();iRow++)
            {
                double v = this.getDataValue(rpt,iRow,iCol);
                int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat);
                int y = (int)((Math.Min(v, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveP[iRow - 1] = saveAllP[iRow - 1, iCol - 1];
                DrawLinePoint(rpt, g, getSeriesBrush()[iCol - 1], ChartMarkerEnum.None, p, iRow, iCol);
            }
            DrawAreaBetweenPoints(g, getSeriesBrush()[iCol - 1], saveP, iCol == 1 ? null : priorSaveP);
            for (int i = 0;i < getCategoryCount();i++)
                // Save prior point values
                priorSaveP[i] = saveP[i];
        }
        return ;
    }

    void drawPlotAreaAreaStacked(fyiReporting.RDL.Report rpt, Graphics g, double max) throws Exception {
        // Draw Plot area data
        int maxPointHeight = (int)Layout.getPlotArea().Height;
        double widthCat = ((double)(Layout.getPlotArea().Width) / (getCategoryCount() - 1));
        Point[][] saveAllP = new Point[getCategoryCount(), getSeriesCount()];
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            // used to collect all data points
            // Loop thru calculating all the data points
            int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat);
            double v = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                v += getDataValue(rpt,iRow,iCol);
                int y = (int)((Math.Min(v, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveAllP[iRow - 1, iCol - 1] = p;
            }
        }
        // Now loop thru and plot all the points
        Point[] saveP = new Point[getCategoryCount()];
        // used for drawing lines between points
        Point[] priorSaveP = new Point[getCategoryCount()];
        for (int iCol = 1;iCol <= getSeriesCount();iCol++)
        {
            for (int iRow = 1;iRow <= getCategoryCount();iRow++)
            {
                double v = this.getDataValue(rpt,iRow,iCol);
                int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat);
                int y = (int)((Math.Min(v, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveP[iRow - 1] = saveAllP[iRow - 1, iCol - 1];
                DrawLinePoint(rpt, g, getSeriesBrush()[iCol - 1], ChartMarkerEnum.None, p, iRow, iCol);
            }
            DrawAreaBetweenPoints(g, getSeriesBrush()[iCol - 1], saveP, iCol == 1 ? null : priorSaveP);
            for (int i = 0;i < getCategoryCount();i++)
                // Save prior point values
                priorSaveP[i] = saveP[i];
        }
        return ;
    }

    void drawPlotAreaLine(fyiReporting.RDL.Report rpt, Graphics g, double max) throws Exception {
        // Draw Plot area data
        int maxPointHeight = (int)Layout.getPlotArea().Height;
        double widthCat = ((double)(Layout.getPlotArea().Width) / getCategoryCount());
        Point[] saveP = new Point[getCategoryCount()];
        for (int iCol = 1;iCol <= getSeriesCount();iCol++)
        {
            for (int iRow = 1;iRow <= getCategoryCount();iRow++)
            {
                // used for drawing lines between points
                double v = this.getDataValue(rpt,iRow,iCol);
                int x = (int)(Layout.getPlotArea().Left + (iRow - 1) * widthCat + widthCat / 2);
                int y = (int)((Math.Min(v, max) / max) * maxPointHeight);
                Point p = new Point(x, Layout.getPlotArea().Top + (maxPointHeight - y));
                saveP[iRow - 1] = p;
                DrawLinePoint(rpt, g, getSeriesBrush()[iCol - 1], getSeriesMarker()[iCol - 1], p, iRow, iCol);
            }
            DrawLineBetweenPoints(g, getSeriesBrush()[iCol - 1], saveP);
        }
        return ;
    }

    void drawAreaBetweenPoints(Graphics g, Brush brush, Point[] points, Point[] previous) throws Exception {
        if (points.Length <= 1)
            return ;
         
        // Need at least 2 points
        Pen p = null;
        try
        {
            p = new Pen(brush, 1);
            // todo - use line from style ????
            g.DrawLines(p, points);
            Point[] poly = new Point[]();
            if (previous == null)
            {
                // The bottom is the bottom of the chart
                poly = new Point[points.Length + 3];
                int i = 0;
                for (Object __dummyForeachVar0 : points)
                {
                    Point pt = (Point)__dummyForeachVar0;
                    poly[i++] = pt;
                }
                poly[i++] = new Point(points[points.Length - 1].X, Layout.getPlotArea().Bottom);
                poly[i++] = new Point(points[0].X, Layout.getPlotArea().Bottom);
                poly[i] = new Point(points[0].X, points[0].Y);
            }
            else
            {
                // The bottom is the previous line
                poly = new Point[points.Length * 2 + 1];
                int i = 0;
                for (Object __dummyForeachVar1 : points)
                {
                    Point pt = (Point)__dummyForeachVar1;
                    poly[i] = pt;
                    poly[points.Length + i] = previous[previous.Length - 1 - i];
                    i++;
                }
                poly[poly.Length - 1] = poly[0];
            } 
            g.FillPolygon(brush, poly);
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
        return ;
    }

    void drawLineBetweenPoints(Graphics g, Brush brush, Point[] points) throws Exception {
        if (points.Length <= 1)
            return ;
         
        // Need at least 2 points
        Pen p = null;
        try
        {
            p = new Pen(brush, 1);
            // todo - use line from style ????
            if (getChartDefn().getSubtype() == ChartSubTypeEnum.Smooth && points.Length > 2)
                g.DrawCurve(p, points, 0.5F);
            else
                g.DrawLines(p, points); 
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
        return ;
    }

    void drawLinePoint(fyiReporting.RDL.Report rpt, Graphics g, Brush brush, ChartMarkerEnum marker, Point p, int iRow, int iCol) throws Exception {
        Pen pen = null;
        try
        {
            pen = new Pen(brush);
            DrawLegendMarker(g, brush, pen, marker, p.X - 3, p.Y - 3, 7);
            drawDataPoint(rpt,g,new Point(p.X - 3, p.Y + 3),iRow,iCol);
        }
        finally
        {
            if (pen != null)
                pen.Dispose();
             
        }
        return ;
    }

}


