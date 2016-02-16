//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartExpression;
import fyiReporting.RDL.ChartLayout;
import fyiReporting.RDL.ChartMarkerEnum;
import fyiReporting.RDL.ChartPaletteEnum;
import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.DataPoint;
import fyiReporting.RDL.Legend;
import fyiReporting.RDL.LegendLayoutEnum;
import fyiReporting.RDL.LegendPositionEnum;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.Title;

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
* Base class of all charts.
*/
public abstract class ChartBase  extends IDisposable 
{
    Chart _ChartDefn;
    MatrixCellEntry[][] _DataDefn = new MatrixCellEntry[][]();
    protected Bitmap _bm = new Bitmap();
    protected ChartLayout Layout;
    Brush[] _SeriesBrush = new Brush[]();
    ChartMarkerEnum[] _SeriesMarker = new ChartMarkerEnum[]();
    protected int _LastCategoryWidth = 0;
    protected Row _row;
    // row chart created on
    public ChartBase(fyiReporting.RDL.Report r, Row row, Chart c, MatrixCellEntry[][] m) throws Exception {
        _ChartDefn = c;
        _row = row;
        _DataDefn = m;
        _bm = null;
        int width = _ChartDefn.widthCalc(r,null);
        int height = RSize.pixelsFromPoints(_ChartDefn.getHeightOrOwnerHeight());
        Layout = new ChartLayout(width,height);
        _SeriesBrush = null;
        _SeriesMarker = null;
    }

    public void draw(fyiReporting.RDL.Report rpt) throws Exception {
    }

    public void save(fyiReporting.RDL.Report rpt, System.IO.Stream stream, ImageFormat im) throws Exception {
        if (_bm == null)
            draw(rpt);
         
        _bm.Save(stream, im);
    }

    public System.Drawing.Image image(fyiReporting.RDL.Report rpt) throws Exception {
        if (_bm == null)
            draw(rpt);
         
        return _bm;
    }

    protected Bitmap createSizedBitmap() throws Exception {
        if (_bm != null)
        {
            _bm.Dispose();
            _bm = null;
        }
         
        _bm = new Bitmap(Layout.getWidth(), Layout.getHeight());
        return _bm;
    }

    protected int getAxisTickMarkMajorLen() throws Exception {
        return 6;
    }

    protected int getAxisTickMarkMinorLen() throws Exception {
        return 3;
    }

    protected int getCategoryCount() throws Exception {
        return (_DataDefn.GetLength(0) - 1);
    }

    protected Chart getChartDefn() throws Exception {
        return _ChartDefn;
    }

    protected MatrixCellEntry[][] getDataDefn() throws Exception {
        return _DataDefn;
    }

    protected Brush[] getSeriesBrush() throws Exception {
        if (_SeriesBrush == null)
            _SeriesBrush = getSeriesBrushes();
         
        return _SeriesBrush;
    }

    // These are all from Brushes class; so no Dispose should be used
    protected ChartMarkerEnum[] getSeriesMarker() throws Exception {
        if (_SeriesMarker == null)
            _SeriesMarker = getSeriesMarkers();
         
        return _SeriesMarker;
    }

    protected int getSeriesCount() throws Exception {
        return (_DataDefn.GetLength(1) - 1);
    }

    protected void drawChartStyle(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        System.Drawing.Rectangle rect = new System.Drawing.Rectangle(0, 0, Layout.getWidth(), Layout.getHeight());
        if (_ChartDefn.getStyle() == null)
        {
            g.FillRectangle(Brushes.White, rect);
        }
        else
        {
            Row r = firstChartRow(rpt);
            _ChartDefn.getStyle().drawBorder(rpt,g,r,rect);
            _ChartDefn.getStyle().drawBackground(rpt,g,r,rect);
        } 
        return ;
    }

    // Draws the Legend and then returns the rectangle it drew in
    protected System.Drawing.Rectangle drawLegend(fyiReporting.RDL.Report rpt, Graphics g, boolean bMarker, boolean bBeforePlotDrawn) throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null)
            return System.Drawing.Rectangle.Empty;
         
        if (!l.getVisible())
            return System.Drawing.Rectangle.Empty;
         
        if (_ChartDefn.getSeriesGroupings() == null)
            return System.Drawing.Rectangle.Empty;
         
        if (bBeforePlotDrawn)
        {
            if (this.isLegendInsidePlotArea())
                return System.Drawing.Rectangle.Empty;
             
        }
        else if (!isLegendInsidePlotArea())
            return System.Drawing.Rectangle.Empty;
          
        // Only draw legend after if inside the plot
        Font drawFont = null;
        Brush drawBrush = null;
        StringFormat drawFormat = null;
        // calculated bounding rectangle of the legend
        System.Drawing.Rectangle rRect = new System.Drawing.Rectangle();
        Style s = l.getStyle();
        try
        {
            // no matter what we want to dispose of the graphic resources
            if (s == null)
            {
                drawFont = new Font("Arial", 10);
                drawBrush = new SolidBrush(Color.Black);
                drawFormat = new StringFormat();
                drawFormat.Alignment = StringAlignment.Near;
            }
            else
            {
                drawFont = s.getFont(rpt,null);
                drawBrush = s.getBrush(rpt,null);
                drawFormat = s.getStringFormat(rpt,null);
            } 
            int x = new int(), y = new int(), h = new int();
            int maxTextWidth = new int(), maxTextHeight = new int();
            drawFormat.FormatFlags |= StringFormatFlags.NoWrap;
            RefSupport<int> refVar___0 = new RefSupport<int>();
            RefSupport<int> refVar___1 = new RefSupport<int>();
            Size[] sizes = drawLegendMeasure(rpt,g,drawFont,drawFormat,new SizeF(Layout.getWidth(), Layout.getHeight()),refVar___0,refVar___1);
            maxTextWidth = refVar___0.getValue();
            maxTextHeight = refVar___1.getValue();
            int boxSize = (int)(maxTextHeight * .8);
            int totalItemWidth = 0;
            // width of a legend item
            int totalWidth = new int(), totalHeight = new int();
            // final height and width of legend
            // calculate the height and width of the rectangle
            switch(l.getLayout())
            {
                case Row: 
                    // we need to loop thru all the width
                    totalWidth = 0;
                    for (int i = 0;i < getSeriesCount();i++)
                    {
                        totalWidth += (sizes[i].Width + (boxSize * 2));
                    }
                    totalHeight = (int)(maxTextHeight + (maxTextHeight * .1));
                    h = totalHeight;
                    totalItemWidth = maxTextWidth + boxSize * 2;
                    drawFormat.Alignment = StringAlignment.Near;
                    break;
                case Table: 
                case Column: 
                default: 
                    // Force alignment to near
                    totalWidth = totalItemWidth = maxTextWidth + boxSize * 2;
                    h = (int)(maxTextHeight + (maxTextHeight * .1));
                    totalHeight = h * getSeriesCount();
                    break;
            
            }
            // calculate the location of the legend rectangle
            if (this.isLegendInsidePlotArea())
                switch(l.getPosition())
                {
                    case BottomCenter: 
                        x = Layout.getPlotArea().X + (Layout.getPlotArea().Width / 2) - (totalWidth / 2);
                        y = Layout.getPlotArea().Y + Layout.getPlotArea().Height - totalHeight - 2;
                        break;
                    case BottomLeft: 
                    case LeftBottom: 
                        x = Layout.getPlotArea().X + 2;
                        y = Layout.getPlotArea().Y + Layout.getPlotArea().Height - totalHeight - 2;
                        break;
                    case BottomRight: 
                    case RightBottom: 
                        x = Layout.getPlotArea().X + Layout.getPlotArea().Width - totalWidth;
                        y = Layout.getPlotArea().Y + Layout.getPlotArea().Height - totalHeight - 2;
                        break;
                    case LeftCenter: 
                        x = Layout.getPlotArea().X + 2;
                        y = Layout.getPlotArea().Y + (Layout.getPlotArea().Height / 2) - (totalHeight / 2);
                        break;
                    case LeftTop: 
                    case TopLeft: 
                        x = Layout.getPlotArea().X + 2;
                        y = Layout.getPlotArea().Y + 2;
                        break;
                    case RightCenter: 
                        x = Layout.getPlotArea().X + Layout.getPlotArea().Width - totalWidth - 2;
                        y = Layout.getPlotArea().Y + (Layout.getPlotArea().Height / 2) - (totalHeight / 2);
                        break;
                    case TopCenter: 
                        x = Layout.getPlotArea().X + (Layout.getPlotArea().Width / 2) - (totalWidth / 2);
                        y = Layout.getPlotArea().Y + +2;
                        break;
                    case TopRight: 
                    case RightTop: 
                    default: 
                        x = Layout.getPlotArea().X + Layout.getPlotArea().Width - totalWidth - 2;
                        y = Layout.getPlotArea().Y + +2;
                        break;
                
                }
            else
                switch(l.getPosition())
                {
                    case BottomCenter: 
                        x = (Layout.getWidth() / 2) - (totalWidth / 2);
                        y = Layout.getHeight() - totalHeight - 2;
                        break;
                    case BottomLeft: 
                    case LeftBottom: 
                        if (isLegendInsidePlotArea())
                            x = Layout.getLeftMargin();
                        else
                            x = 0; 
                        y = Layout.getHeight() - totalHeight - 2;
                        break;
                    case BottomRight: 
                    case RightBottom: 
                        x = Layout.getWidth() - totalWidth;
                        y = Layout.getHeight() - totalHeight - 2;
                        break;
                    case LeftCenter: 
                        x = 2;
                        y = (Layout.getHeight() / 2) - (totalHeight / 2);
                        break;
                    case LeftTop: 
                    case TopLeft: 
                        x = 2;
                        y = Layout.getTopMargin() + 2;
                        break;
                    case RightCenter: 
                        x = Layout.getWidth() - totalWidth - 2;
                        y = (Layout.getHeight() / 2) - (totalHeight / 2);
                        break;
                    case TopCenter: 
                        x = (Layout.getWidth() / 2) - (totalWidth / 2);
                        y = Layout.getTopMargin() + 2;
                        break;
                    case TopRight: 
                    case RightTop: 
                    default: 
                        x = Layout.getWidth() - totalWidth - 2;
                        y = Layout.getTopMargin() + 2;
                        break;
                
                } 
            // We now know enough to calc the bounding rectangle of the legend
            rRect = new System.Drawing.Rectangle(x - 1, y - 1, totalWidth + 2, totalHeight + 2);
            if (s != null)
            {
                s.drawBackground(rpt,g,null,rRect);
                // draw (or not draw) background
                s.drawBorder(rpt,g,null,rRect);
            }
             
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                // draw (or not draw) border depending on style
                String c = getSeriesValue(rpt,iCol);
                System.Drawing.Rectangle rect = new System.Drawing.Rectangle();
                switch(l.getLayout())
                {
                    case Row: 
                        rect = new System.Drawing.Rectangle(x + boxSize + boxSize / 2, y, totalItemWidth - boxSize - boxSize / 2, h);
                        g.DrawString(c, drawFont, drawBrush, rect, drawFormat);
                        DrawLegendBox(g, getSeriesBrush()[iCol - 1], bMarker ? getSeriesMarker()[iCol - 1] : ChartMarkerEnum.None, x, y + 1, boxSize);
                        x += (sizes[iCol - 1].Width + boxSize * 2);
                        break;
                    case Table: 
                    case Column: 
                    default: 
                        rect = new System.Drawing.Rectangle(x + boxSize + boxSize / 2, y, maxTextWidth, h);
                        g.DrawString(c, drawFont, drawBrush, rect, drawFormat);
                        DrawLegendBox(g, getSeriesBrush()[iCol - 1], bMarker ? getSeriesMarker()[iCol - 1] : ChartMarkerEnum.None, x + 1, y, boxSize);
                        y += h;
                        break;
                
                }
            }
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawBrush != null)
                drawBrush.Dispose();
             
            if (drawFormat != null)
                drawFormat.Dispose();
             
        }
        if (s != null)
            rRect = s.paddingAdjust(rpt,null,rRect,true);
         
        return rRect;
    }

    void drawLegendBox(Graphics g, Brush b, ChartMarkerEnum marker, int x, int y, int boxSize) throws Exception {
        Pen p = null;
        int mSize = boxSize / 2;
        try
        {
            // Marker size is 1/2 of box size
            if (marker != ChartMarkerEnum.None)
            {
                p = new Pen(b, 1);
                g.DrawLine(p, new Point(x, y + (boxSize + 1) / 2), new Point(x + boxSize, y + (boxSize + 1) / 2));
                x = x + (boxSize - mSize) / 2;
                y = y + (boxSize - mSize) / 2;
                if (mSize % 2 == 0)
                    mSize++;
                 
            }
             
            if (marker == ChartMarkerEnum.None)
            {
                g.FillRectangle(b, x, y, boxSize, boxSize);
            }
            else
            {
                drawLegendMarker(g,b,p,marker,x,y,mSize);
            } 
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
    }

    public void drawLegendMarker(Graphics g, Brush b, Pen p, ChartMarkerEnum marker, int x, int y, int mSize) throws Exception {
        Point[] points = new Point[]();
        switch(marker)
        {
            case Circle: 
                g.FillEllipse(b, x, y, mSize, mSize);
                break;
            case Square: 
                g.FillRectangle(b, x, y, mSize, mSize);
                break;
            case Plus: 
                g.DrawLine(p, new Point(x + (mSize + 1) / 2, y), new Point(x + (mSize + 1) / 2, y + mSize));
                g.DrawLine(p, new Point(x + (mSize + 1) / 2, y + (mSize + 1) / 2), new Point(x + mSize, y + (mSize + 1) / 2));
                break;
            case Diamond: 
                points = new Point[5];
                points[0] = points[4] = new Point(x + (mSize + 1) / 2, y);
                // starting and ending point
                points[1] = new Point(x, y + (mSize + 1) / 2);
                points[2] = new Point(x + (mSize + 1) / 2, y + mSize);
                points[3] = new Point(x + mSize, y + (mSize + 1) / 2);
                g.FillPolygon(b, points);
                break;
            case Triangle: 
                points = new Point[4];
                points[0] = points[3] = new Point(x + (mSize + 1) / 2, y);
                // starting and ending point
                points[1] = new Point(x, y + mSize);
                points[2] = new Point(x + mSize, y + mSize);
                g.FillPolygon(b, points);
                break;
            case X: 
                g.DrawLine(p, new Point(x, y), new Point(x + mSize, y + mSize));
                g.DrawLine(p, new Point(x + mSize, y + mSize), new Point(x + mSize, y));
                break;
        
        }
        return ;
    }

    // Measures the Legend and then returns the rectangle it drew in
    protected Size[] drawLegendMeasure(fyiReporting.RDL.Report rpt, Graphics g, Font f, StringFormat sf, SizeF maxSize, RefSupport<int> maxWidth, RefSupport<int> maxHeight) throws Exception {
        Size[] sizes = new Size[getSeriesCount()];
        maxWidth.setValue(maxHeight.setValue(0));
        for (int iCol = 1;iCol <= getSeriesCount();iCol++)
        {
            String c = getSeriesValue(rpt,iCol);
            SizeF ms = g.MeasureString(c, f, maxSize, sf);
            sizes[iCol - 1] = new Size((int)Math.Ceiling(ms.Width), (int)Math.Ceiling(ms.Height));
            if (sizes[iCol - 1].Width > maxWidth.getValue())
                maxWidth.setValue(sizes[iCol - 1].Width);
             
            if (sizes[iCol - 1].Height > maxHeight.getValue())
                maxHeight.setValue(sizes[iCol - 1].Height);
             
        }
        return sizes;
    }

    protected void drawPlotAreaStyle(fyiReporting.RDL.Report rpt, Graphics g, System.Drawing.Rectangle crect) throws Exception {
        if (_ChartDefn.getPlotArea() == null || _ChartDefn.getPlotArea().getStyle() == null)
            return ;
         
        System.Drawing.Rectangle rect = Layout.getPlotArea();
        Style s = _ChartDefn.getPlotArea().getStyle();
        Row r = firstChartRow(rpt);
        if (rect.IntersectsWith(crect))
        {
            // This occurs when the legend is drawn inside the plot area
            //    we don't want to draw in the legend
            Region rg = null;
            try
            {
                //				rg = new Region(rect);	// TODO: this doesn't work; nothing draws
                //				rg.Complement(crect);
                //				Region saver = g.Clip;
                //				g.Clip = rg;
                s.drawBackground(rpt,g,r,rect);
            }
            finally
            {
                //				g.Clip = saver;
                if (rg != null)
                    rg.Dispose();
                 
            }
        }
        else
            s.drawBackground(rpt,g,r,rect); 
        return ;
    }

    protected void drawTitle(fyiReporting.RDL.Report rpt, Graphics g, Title t, System.Drawing.Rectangle rect) throws Exception {
        if (t == null)
            return ;
         
        if (t.getCaption() == null)
            return ;
         
        Row r = firstChartRow(rpt);
        Object title = t.getCaption().evaluate(rpt,r);
        if (t.getStyle() != null)
        {
            t.getStyle().DrawString(rpt, g, title, t.getCaption().getTypeCode(), r, rect);
            t.getStyle().drawBorder(rpt,g,r,rect);
        }
        else
            Style.drawStringDefaults(g,title,rect); 
        return ;
    }

    protected Size drawTitleMeasure(fyiReporting.RDL.Report rpt, Graphics g, Title t) throws Exception {
        Size size = Size.Empty;
        if (t == null || t.getCaption() == null)
            return size;
         
        Row r = firstChartRow(rpt);
        Object title = t.getCaption().evaluate(rpt,r);
        if (t.getStyle() != null)
            size = t.getStyle().MeasureString(rpt, g, title, t.getCaption().getTypeCode(), r, int.MaxValue);
        else
            size = Style.MeasureStringDefaults(rpt, g, title, t.getCaption().getTypeCode(), r, int.MaxValue); 
        return size;
    }

    protected Object getCategoryValue(fyiReporting.RDL.Report rpt, int row, RefSupport<TypeCode> tc) throws Exception {
        MatrixCellEntry mce = _DataDefn[row, 0];
        if (mce == null)
        {
            tc.setValue(TypeCode.String);
            return "";
        }
         
        // Not sure what this really means TODO:
        Row lrow;
        this._ChartDefn.getChartMatrix().setMyData(rpt,mce.getData());
        // Must set this for evaluation
        if (mce.getData().getData().Count > 0)
            lrow = mce.getData().getData()[0];
        else
            lrow = null; 
        ChartExpression ce = (ChartExpression)(mce.getDisplayItem());
        Object v = ce.getValue().evaluate(rpt,lrow);
        tc.setValue(ce.getValue().getTypeCode());
        return v;
    }

    protected double getDataValue(fyiReporting.RDL.Report rpt, int row, int col) throws Exception {
        MatrixCellEntry mce = _DataDefn[row, col];
        if (mce == null)
            return 0;
         
        // Not sure what this really means TODO:
        if (mce.getValue() != double.MinValue)
            return mce.getValue();
         
        // Calculate this value; usually a fairly expensive operation
        //   due to the common use of aggregate values.  We need to
        //   go thru the data more than once if we have to auto scale.
        Row lrow;
        this._ChartDefn.getChartMatrix().setMyData(rpt,mce.getData());
        // Must set this for evaluation
        if (mce.getData().getData().Count > 0)
            lrow = mce.getData().getData()[0];
        else
            lrow = null; 
        ChartExpression ce = (ChartExpression)(mce.getDisplayItem());
        double v = ce.getValue().evaluateDouble(rpt,lrow);
        mce.setValue(v);
        return v;
    }

    // cache so we don't need to calculate again
    protected void drawDataPoint(fyiReporting.RDL.Report rpt, Graphics g, Point p, int row, int col) throws Exception {
        DrawDataPoint(rpt, g, p, System.Drawing.Rectangle.Empty, row, col);
    }

    protected void drawDataPoint(fyiReporting.RDL.Report rpt, Graphics g, System.Drawing.Rectangle rect, int row, int col) throws Exception {
        DrawDataPoint(rpt, g, Point.Empty, rect, row, col);
    }

    void drawDataPoint(fyiReporting.RDL.Report rpt, Graphics g, Point p, System.Drawing.Rectangle rect, int row, int col) throws Exception {
        MatrixCellEntry mce = _DataDefn[row, col];
        if (mce == null)
            return ;
         
        // Not sure what this really means TODO:
        ChartExpression ce = (ChartExpression)(mce.getDisplayItem());
        DataPoint dp = ce.getDP();
        if (dp.getDataLabel() == null || !dp.getDataLabel().getVisible())
            return ;
         
        // Calculate the DataPoint value; usually a fairly expensive operation
        //   due to the common use of aggregate values.
        Row lrow;
        this._ChartDefn.getChartMatrix().setMyData(rpt,mce.getData());
        // Must set this for evaluation
        if (mce.getData().getData().Count > 0)
            lrow = mce.getData().getData()[0];
        else
            lrow = null; 
        Object v = null;
        TypeCode tc = new TypeCode();
        if (dp.getDataLabel().getValue() == null)
        {
            // No DataLabel value specified so we use the actual value
            v = ce.getValue().evaluateDouble(rpt,lrow);
            tc = TypeCode.Double;
        }
        else
        {
            // Evaluate the DataLable value for the display
            v = dp.getDataLabel().getValue().evaluate(rpt,lrow);
            tc = dp.getDataLabel().getValue().getTypeCode();
        } 
        if (dp.getDataLabel().getStyle() == null)
        {
            if (rect == System.Drawing.Rectangle.Empty)
            {
                Size size = Style.MeasureStringDefaults(rpt, g, v, tc, lrow, int.MaxValue);
                rect = new System.Drawing.Rectangle(p, size);
            }
             
            Style.drawStringDefaults(g,v,rect);
        }
        else
        {
            if (rect == System.Drawing.Rectangle.Empty)
            {
                Size size = dp.getDataLabel().getStyle().MeasureString(rpt, g, v, tc, lrow, int.MaxValue);
                rect = new System.Drawing.Rectangle(p, size);
            }
             
            dp.getDataLabel().getStyle().drawString(rpt,g,v,tc,lrow,rect);
        } 
        return ;
    }

    protected String getSeriesValue(fyiReporting.RDL.Report rpt, int iCol) throws Exception {
        MatrixCellEntry mce = _DataDefn[0, iCol];
        Row lrow;
        if (mce.getData().getData().Count > 0)
            lrow = mce.getData().getData()[0];
        else
            lrow = null; 
        ChartExpression ce = (ChartExpression)(mce.getDisplayItem());
        String v = ce.getValue().evaluateString(rpt,lrow);
        return v;
    }

    protected void getMaxMinDataValue(fyiReporting.RDL.Report rpt, RefSupport<double> max, RefSupport<double> min) throws Exception {
        if (_ChartDefn.getSubtype() == ChartSubTypeEnum.Stacked)
        {
            RefSupport<double> refVar___2 = new RefSupport<double>();
            RefSupport<double> refVar___3 = new RefSupport<double>();
            getMaxMinDataValueStacked(rpt,refVar___2,refVar___3);
            max.setValue(refVar___2.getValue());
            min.setValue(refVar___3.getValue());
            return ;
        }
         
        min.setValue(double.MaxValue);
        max.setValue(double.MinValue);
        double v = new double();
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                v = getDataValue(rpt,iRow,iCol);
                if (v < min.getValue())
                    min.setValue(v);
                 
                if (v > max.getValue())
                    max.setValue(v);
                 
            }
        }
    }

    void getMaxMinDataValueStacked(fyiReporting.RDL.Report rpt, RefSupport<double> max, RefSupport<double> min) throws Exception {
        min.setValue(double.MaxValue);
        max.setValue(double.MinValue);
        double v = new double();
        for (int iRow = 1;iRow <= getCategoryCount();iRow++)
        {
            v = 0;
            for (int iCol = 1;iCol <= getSeriesCount();iCol++)
            {
                v += getDataValue(rpt,iRow,iCol);
            }
            if (v < min.getValue())
                min.setValue(v);
             
            if (v > max.getValue())
                max.setValue(v);
             
        }
    }

    protected Brush[] getSeriesBrushes() throws Exception {
        Brush[] b = new Brush[getSeriesCount()];
        for (int i = 0;i < getSeriesCount();i++)
        {
            // TODO: In general all the palettes could use a good going over
            //   both in terms of the colors in the lists and their order
            switch(getChartDefn().getPalette())
            {
                case Default: 
                    b[i] = getSeriesBrushesExcel(i);
                    break;
                case EarthTones: 
                    b[i] = getSeriesBrushesEarthTones(i);
                    break;
                case Excel: 
                    b[i] = getSeriesBrushesExcel(i);
                    break;
                case GrayScale: 
                    b[i] = getSeriesBrushesGrayScale(i);
                    break;
                case Light: 
                    b[i] = getSeriesBrushesLight(i);
                    break;
                case Pastel: 
                    b[i] = getSeriesBrushesPastel(i);
                    break;
                case SemiTransparent: 
                    b[i] = getSeriesBrushesExcel(i);
                    break;
                default: 
                    // TODO
                    b[i] = getSeriesBrushesExcel(i);
                    break;
            
            }
        }
        return b;
    }

    Brush getSeriesBrushesEarthTones(int i) throws Exception {
        switch(i % 22)
        {
            case 0: 
                return Brushes.Maroon;
            case 1: 
                return Brushes.Brown;
            case 2: 
                return Brushes.Chocolate;
            case 3: 
                return Brushes.IndianRed;
            case 4: 
                return Brushes.Peru;
            case 5: 
                return Brushes.BurlyWood;
            case 6: 
                return Brushes.AntiqueWhite;
            case 7: 
                return Brushes.FloralWhite;
            case 8: 
                return Brushes.Ivory;
            case 9: 
                return Brushes.LightCoral;
            case 10: 
                return Brushes.DarkSalmon;
            case 11: 
                return Brushes.LightSalmon;
            case 12: 
                return Brushes.PeachPuff;
            case 13: 
                return Brushes.NavajoWhite;
            case 14: 
                return Brushes.Moccasin;
            case 15: 
                return Brushes.PapayaWhip;
            case 16: 
                return Brushes.Goldenrod;
            case 17: 
                return Brushes.DarkGoldenrod;
            case 18: 
                return Brushes.DarkKhaki;
            case 19: 
                return Brushes.Khaki;
            case 20: 
                return Brushes.Beige;
            case 21: 
                return Brushes.Cornsilk;
            default: 
                return Brushes.Brown;
        
        }
    }

    Brush getSeriesBrushesExcel(int i) throws Exception {
        switch(i % 11)
        {
            case 0: 
                return Brushes.DarkBlue;
            case 1: 
                return Brushes.Pink;
            case 2: 
                return Brushes.Yellow;
            case 3: 
                return Brushes.Turquoise;
            case 4: 
                return Brushes.Violet;
            case 5: 
                return Brushes.DarkRed;
            case 6: 
                return Brushes.Teal;
            case 7: 
                return Brushes.Blue;
            case 8: 
                return Brushes.Plum;
            case 9: 
                return Brushes.Ivory;
            case 10: 
                return Brushes.Coral;
            default: 
                return Brushes.DarkBlue;
        
        }
    }

    // Just a guess at what these might actually be
    Brush getSeriesBrushesGrayScale(int i) throws Exception {
        switch(i % 10)
        {
            case 0: 
                return Brushes.Gray;
            case 1: 
                return Brushes.SlateGray;
            case 2: 
                return Brushes.DarkGray;
            case 3: 
                return Brushes.LightGray;
            case 4: 
                return Brushes.DarkSlateGray;
            case 5: 
                return Brushes.DimGray;
            case 6: 
                return Brushes.LightSlateGray;
            case 7: 
                return Brushes.Black;
            case 8: 
                return Brushes.White;
            case 9: 
                return Brushes.Gainsboro;
            default: 
                return Brushes.Gray;
        
        }
    }

    Brush getSeriesBrushesLight(int i) throws Exception {
        switch(i % 13)
        {
            case 0: 
                return Brushes.LightBlue;
            case 1: 
                return Brushes.LightCoral;
            case 2: 
                return Brushes.LightCyan;
            case 3: 
                return Brushes.LightGoldenrodYellow;
            case 4: 
                return Brushes.LightGray;
            case 5: 
                return Brushes.LightGreen;
            case 6: 
                return Brushes.LightPink;
            case 7: 
                return Brushes.LightSalmon;
            case 8: 
                return Brushes.LightSeaGreen;
            case 9: 
                return Brushes.LightSkyBlue;
            case 10: 
                return Brushes.LightSlateGray;
            case 11: 
                return Brushes.LightSteelBlue;
            case 12: 
                return Brushes.LightYellow;
            default: 
                return Brushes.LightBlue;
        
        }
    }

    Brush getSeriesBrushesPastel(int i) throws Exception {
        switch(i % 26)
        {
            case 0: 
                return Brushes.CadetBlue;
            case 1: 
                return Brushes.MediumTurquoise;
            case 2: 
                return Brushes.Aquamarine;
            case 3: 
                return Brushes.LightCyan;
            case 4: 
                return Brushes.Azure;
            case 5: 
                return Brushes.AliceBlue;
            case 6: 
                return Brushes.MintCream;
            case 7: 
                return Brushes.DarkSeaGreen;
            case 8: 
                return Brushes.PaleGreen;
            case 9: 
                return Brushes.LightGreen;
            case 10: 
                return Brushes.MediumPurple;
            case 11: 
                return Brushes.CornflowerBlue;
            case 12: 
                return Brushes.Lavender;
            case 13: 
                return Brushes.GhostWhite;
            case 14: 
                return Brushes.PaleGoldenrod;
            case 15: 
                return Brushes.LightGoldenrodYellow;
            case 16: 
                return Brushes.LemonChiffon;
            case 17: 
                return Brushes.LightYellow;
            case 18: 
                return Brushes.Orchid;
            case 19: 
                return Brushes.Plum;
            case 20: 
                return Brushes.LightPink;
            case 21: 
                return Brushes.Pink;
            case 22: 
                return Brushes.LavenderBlush;
            case 23: 
                return Brushes.Linen;
            case 24: 
                return Brushes.PaleTurquoise;
            case 25: 
                return Brushes.OldLace;
            default: 
                return Brushes.CadetBlue;
        
        }
    }

    protected ChartMarkerEnum[] getSeriesMarkers() throws Exception {
        ChartMarkerEnum[] m = new ChartMarkerEnum[getSeriesCount()];
        for (int i = 0;i < getSeriesCount();i++)
        {
            m[i] = ChartMarkerEnum.values()[(i % ((Enum)ChartMarkerEnum.Count).ordinal())];
        }
        return m;
    }

    protected void getValueMaxMin(fyiReporting.RDL.Report rpt, RefSupport<double> max, RefSupport<double> min) throws Exception {
        if (_ChartDefn.getSubtype() == ChartSubTypeEnum.PercentStacked)
        {
            // Percent stacked is easy; and overrides user provided values
            max.setValue(1);
            min.setValue(0);
            return ;
        }
         
        int valueAxisMax = new int();
        int valueAxisMin = new int();
        if (_ChartDefn.getValueAxis() != null && _ChartDefn.getValueAxis().getAxis() != null)
        {
            valueAxisMax = _ChartDefn.getValueAxis().getAxis().maxEval(rpt,_row);
            valueAxisMin = _ChartDefn.getValueAxis().getAxis().minEval(rpt,_row);
        }
        else
        {
            valueAxisMax = valueAxisMin = int.MinValue;
        } 
        // Check for case where both min and max are provided
        if (valueAxisMax != int.MinValue && valueAxisMin != int.MinValue)
        {
            max.setValue(valueAxisMax);
            min.setValue(valueAxisMin);
            return ;
        }
         
        // OK We have to work for it;  Calculate min/max of data
        RefSupport<double> refVar___4 = new RefSupport<double>();
        RefSupport<double> refVar___5 = new RefSupport<double>();
        getMaxMinDataValue(rpt,refVar___4,refVar___5);
        max.setValue(refVar___4.getValue());
        min.setValue(refVar___5.getValue());
        if (valueAxisMax != int.MinValue)
            max.setValue(valueAxisMax);
        else
        {
            //
            int gridIncrs = 10;
            // assume 10 grid increments for now
            double incr = max.getValue() / gridIncrs;
            // should be range between max and min?
            double log = Math.Floor(Math.Log10(Math.Abs(incr)));
            double logPow = Math.Pow(10, log) * Math.Sign(max.getValue());
            double logDig = (int)(incr / logPow + .5);
            // promote the MSD to either 1, 2, or 5
            if (logDig > 5.0)
                logDig = 10.0;
            else if (logDig > 2.0)
                logDig = 5.0;
            else if (logDig > 1.0)
                logDig = 2.0;
               
            max.setValue(logDig * logPow * gridIncrs);
        } 
        if (valueAxisMin != int.MinValue)
            min.setValue(valueAxisMin);
        else if (min.getValue() > 0)
            min.setValue(0);
        else
        {
            min.setValue(Math.Floor(min.getValue()));
        }  
        return ;
    }

    protected void adjustMargins(System.Drawing.Rectangle legendRect) throws Exception {
        // Adjust the margins based on the legend
        if (!isLegendInsidePlotArea())
        {
            // When inside plot area we don't adjust plot margins
            if (isLegendLeft())
                Layout.setLeftMargin(Layout.getLeftMargin() + legendRect.Width);
            else if (isLegendRight())
                Layout.setRightMargin(Layout.getRightMargin() + legendRect.Width);
              
            if (isLegendTop())
                Layout.setTopMargin(Layout.getTopMargin() + legendRect.Height);
            else if (isLegendBottom())
                Layout.setBottomMargin(Layout.getBottomMargin() + legendRect.Height);
              
        }
         
        // Force some margins; if any are too small
        int min = new RSize(getChartDefn().OwnerReport,".2 in").getPixelsX();
        if (Layout.getRightMargin() < min + this._LastCategoryWidth / 2)
            Layout.setRightMargin(min + this._LastCategoryWidth / 2);
         
        if (Layout.getLeftMargin() < min)
            Layout.setLeftMargin(min);
         
        if (Layout.getTopMargin() < min)
            Layout.setTopMargin(min);
         
        if (Layout.getBottomMargin() < min)
            Layout.setBottomMargin(min);
         
    }

    protected boolean isLegendLeft() throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null || !l.getVisible())
            return false;
         
        boolean rc = new boolean();
        switch(l.getPosition())
        {
            case BottomLeft: 
            case LeftBottom: 
            case LeftCenter: 
            case LeftTop: 
            case TopLeft: 
                rc = true;
                break;
            default: 
                rc = false;
                break;
        
        }
        return rc;
    }

    protected boolean isLegendInsidePlotArea() throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null || !l.getVisible())
            return false;
        else
            return l.getInsidePlotArea(); 
    }

    // doesn't really matter
    protected boolean isLegendRight() throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null || !l.getVisible())
            return false;
         
        boolean rc = new boolean();
        switch(l.getPosition())
        {
            case BottomRight: 
            case RightBottom: 
            case RightCenter: 
            case TopRight: 
            case RightTop: 
                rc = true;
                break;
            default: 
                rc = false;
                break;
        
        }
        return rc;
    }

    protected boolean isLegendTop() throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null || !l.getVisible())
            return false;
         
        boolean rc = new boolean();
        switch(l.getPosition())
        {
            case LeftTop: 
            case TopLeft: 
            case TopCenter: 
            case TopRight: 
            case RightTop: 
                rc = true;
                break;
            default: 
                rc = false;
                break;
        
        }
        return rc;
    }

    protected boolean isLegendBottom() throws Exception {
        Legend l = _ChartDefn.getLegend();
        if (l == null || !l.getVisible())
            return false;
         
        boolean rc = new boolean();
        switch(l.getPosition())
        {
            case BottomCenter: 
            case BottomLeft: 
            case LeftBottom: 
            case BottomRight: 
            case RightBottom: 
                rc = true;
                break;
            default: 
                rc = false;
                break;
        
        }
        return rc;
    }

    private Row firstChartRow(fyiReporting.RDL.Report rpt) throws Exception {
        Rows _Data = _ChartDefn.getChartMatrix().getMyData(rpt);
        if (_Data != null && _Data.getData().Count > 0)
            return _Data.getData()[0];
        else
            return null; 
    }

    public void dispose() throws Exception {
        if (_bm != null)
            _bm.Dispose();
         
    }

}


