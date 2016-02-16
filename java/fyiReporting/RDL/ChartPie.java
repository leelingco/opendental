//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import fyiReporting.RDL.Axis;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.ChartTypeEnum;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
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
* Pie chart definition and processing.
*/
public class ChartPie  extends ChartBase 
{
    public ChartPie(fyiReporting.RDL.Report r, Row row, Chart c, MatrixCellEntry[][] m) throws Exception {
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
                drawChartStyle(rpt,g);
                // Draw title; routine determines if necessary
                drawTitle(rpt,g,getChartDefn().getTitle(),new System.Drawing.Rectangle(0, 0, _bm.Width, Layout.getTopMargin()));
                // Draw legend
                System.Drawing.Rectangle lRect = drawLegend(rpt,g,false,true);
                // Adjust the bottom margin to depend on the Category Axis
                Size caSize = categoryAxisSize(rpt,g);
                Layout.setBottomMargin(caSize.Height);
                adjustMargins(lRect);
                // Adjust margins based on legend.
                // Draw Plot area
                drawPlotAreaStyle(rpt,g,lRect);
                // Draw Category Axis
                if (caSize.Height > 0)
                    drawCategoryAxis(rpt,g,new System.Drawing.Rectangle(Layout.getLeftMargin(), _bm.Height - Layout.getBottomMargin(), _bm.Width - Layout.getLeftMargin() - Layout.getRightMargin(), caSize.Height));
                 
                if (getChartDefn().getType() == ChartTypeEnum.Doughnut)
                    drawPlotAreaDoughnut(rpt,g);
                else
                    drawPlotAreaPie(rpt,g); 
                drawLegend(rpt,g,false,false);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    void drawPlotAreaDoughnut(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        // Draw Plot area data
        int widthPie = Layout.getPlotArea().Width;
        int maxHeight = Layout.getPlotArea().Height;
        int maxPieSize = Math.Min(widthPie, maxHeight);
        int doughWidth = maxPieSize / 4 / getCategoryCount();
        if (doughWidth < 2)
            doughWidth = 2;
         
        // enforce minimum width
        float startAngle = new float();
        float endAngle = new float();
        int pieLocX = new int();
        int pieLocY = new int();
        int pieSize = new int();
        // Go and draw the pies
        int left = Layout.getPlotArea().Left + (maxPieSize == widthPie ? 0 : (widthPie - maxPieSize) / 2);
        int top = Layout.getPlotArea().Top + (maxPieSize == maxHeight ? 0 : (maxHeight - maxPieSize) / 2);
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            pieLocX = left + ((iRow - 1) * doughWidth);
            pieLocY = top + ((iRow - 1) * doughWidth);
            double total = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                // sum up for this category
                total += this.getDataValue(rpt,iRow,iCol);
            }
            // Pie size decreases as we go in
            startAngle = 0.0f;
            pieSize = maxPieSize - ((iRow - 1) * doughWidth * 2);
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                double v = this.getDataValue(rpt,iRow,iCol);
                endAngle = (float)(startAngle + v / total * 360);
                DrawPie(g, getSeriesBrush()[iCol - 1], new System.Drawing.Rectangle(pieLocX, pieLocY, pieSize, pieSize), iRow, iCol, startAngle, endAngle);
                startAngle = endAngle;
            }
        }
        // Now draw the center hole with the plot area style
        if (getChartDefn().getPlotArea() == null || getChartDefn().getPlotArea().getStyle() == null)
            return ;
         
        pieLocX = left + (getCategoryCount() * doughWidth);
        pieLocY = top + (getCategoryCount() * doughWidth);
        pieSize = maxPieSize - (getCategoryCount() * doughWidth * 2);
        System.Drawing.Rectangle rect = new System.Drawing.Rectangle(pieLocX, pieLocY, pieSize, pieSize);
        Style s = getChartDefn().getPlotArea().getStyle();
        Rows cData = getChartDefn().getChartMatrix().getMyData(rpt);
        Row r = cData.getData()[0];
        s.drawBackgroundCircle(rpt,g,r,rect);
    }

    void drawPlotAreaPie(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        int piesNeeded = getCategoryCount();
        int gapsNeeded = getCategoryCount() + 1;
        int gapSize = 13;
        // Draw Plot area data
        int widthPie = (int)((Layout.getPlotArea().Width - gapsNeeded * gapSize) / piesNeeded);
        int maxHeight = (int)(Layout.getPlotArea().Height);
        int maxPieSize = Math.Min(widthPie, maxHeight);
        int pieLocY = Layout.getPlotArea().Top + (maxHeight - maxPieSize) / 2;
        float startAngle = new float();
        float endAngle = new float();
        // calculate the size of the largest category
        //   all pie sizes will be relative to that maximum
        double maxCategory = 0;
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            double total = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                total += this.getDataValue(rpt,iRow,iCol);
            }
            if (total > maxCategory)
                maxCategory = total;
             
        }
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            // Go and draw the pies
            int pieLocX = (int)(Layout.getPlotArea().Left + (iRow - 1) * ((double)(Layout.getPlotArea().Width) / getCategoryCount()));
            pieLocX += gapSize;
            // space before series
            double total = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                total += this.getDataValue(rpt,iRow,iCol);
            }
            // Pie size is a ratio of the area of the pies (not the diameter)
            startAngle = 0.0f;
            int pieSize = (int)(2 * Math.Sqrt(Math.PI * ((maxPieSize / 2) * (maxPieSize / 2) * total / maxCategory) / Math.PI));
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                double v = this.getDataValue(rpt,iRow,iCol);
                endAngle = (float)(startAngle + v / total * 360);
                DrawPie(g, getSeriesBrush()[iCol - 1], new System.Drawing.Rectangle(pieLocX, pieLocY, pieSize, pieSize), iRow, iCol, startAngle, endAngle);
                startAngle = endAngle;
            }
        }
    }

    // Calculate the size of the category axis
    protected Size categoryAxisSize(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        Size size = Size.Empty;
        if (this.getChartDefn().getCategoryAxis() == null || this.getChartDefn().getType() == ChartTypeEnum.Doughnut)
            return size;
         
        // doughnut doesn't get this
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
            RefSupport<TypeCode> refVar___0 = new RefSupport<TypeCode>();
            Object v = this.getCategoryValue(rpt,iRow,refVar___0);
            tc = refVar___0.getValue();
            Size tSize = new Size();
            if (s == null)
                tSize = Style.MeasureStringDefaults(rpt, g, v, tc, null, int.MaxValue);
            else
                tSize = s.MeasureString(rpt, g, v, tc, null, int.MaxValue); 
            if (tSize.Height > maxHeight)
                maxHeight = tSize.Height;
             
        }
        // Add on the tallest category name
        size.Height += maxHeight;
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
        int drawWidth = rect.Width / getCategoryCount();
        TypeCode tc = new TypeCode();
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            RefSupport<TypeCode> refVar___1 = new RefSupport<TypeCode>();
            Object v = this.getCategoryValue(rpt,iRow,refVar___1);
            tc = refVar___1.getValue();
            int drawLoc = (int)(rect.Left + (iRow - 1) * ((double)rect.Width / getCategoryCount()));
            // Draw the category text
            if (a.getVisible())
            {
                System.Drawing.Rectangle drawRect = new System.Drawing.Rectangle(drawLoc, rect.Top, drawWidth, rect.Height - tSize.Height);
                if (s == null)
                    Style.drawStringDefaults(g,v,drawRect);
                else
                    s.drawString(rpt,g,v,tc,null,drawRect); 
            }
             
        }
        return ;
    }

    void drawPie(Graphics g, Brush brush, System.Drawing.Rectangle rect, int iRow, int iCol, float startAngle, float endAngle) throws Exception {
        if (this.getChartDefn().getSubtype() == ChartSubTypeEnum.Exploded)
        {
            // Need to adjust the rectangle
            int side = (int)(rect.Width * .75);
            // side needs to be smaller to account for exploded pies
            int offset = (int)(side * .1);
            //  we add a little to the left and top
            int adjX = new int(), adjY = new int();
            adjX = adjY = (int)(side * .1);
            float midAngle = startAngle + (endAngle - startAngle) / 2;
            if (midAngle < 90)
            {
            }
            else if (midAngle < 180)
            {
                adjX = -adjX;
            }
            else if (midAngle < 270)
            {
                adjX = adjY = -adjX;
            }
            else
            {
                adjY = -adjY;
            }   
            rect = new System.Drawing.Rectangle(rect.Left + adjX + offset, rect.Top + adjY + offset, side, side);
        }
         
        g.FillPie(brush, rect, startAngle, endAngle - startAngle);
        g.DrawPie(Pens.Black, rect, startAngle, endAngle - startAngle);
        return ;
    }

}


