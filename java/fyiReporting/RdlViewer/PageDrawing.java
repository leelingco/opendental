//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RdlViewer;

import CS2JNet.System.LCC.Disposable;
import fyiReporting.RDL.BackgroundGradientTypeEnum;
import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.PageLine;
import fyiReporting.RDL.PageRectangle;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.PageText;
import fyiReporting.RDL.PageTextHtml;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.TextAlignEnum;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RDL.VerticalAlignEnum;
import fyiReporting.RDL.WritingModeEnum;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This library is distributed in the hope that it will be useful,
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
* PageDrawing draws to a graphics context the loaded Pages class.   This
* class is usually created from running a RDL file thru the renderer.
*/
public class PageDrawing  extends UserControl 
{
    private Pages _pgs;
    // the pages of the report to view
    // During drawing these are set
    float _left = new float();
    float _top = new float();
    float _vScroll = new float();
    float _hScroll = new float();
    float DpiX = new float();
    float DpiY = new float();
    // Mouse handling
    List<HitListEntry> _HitList = new List<HitListEntry>();
    float _LastZoom = new float();
    ToolTip _tt = new ToolTip();
    public PageDrawing(Pages pgs) throws Exception {
        // Set up the tooltip
        _tt = new ToolTip();
        _tt.Active = false;
        _tt.ShowAlways = true;
        _HitList = new List<HitListEntry>();
        _LastZoom = 1;
        _pgs = pgs;
        // Get our graphics DPI
        Graphics ga = null;
        try
        {
            ga = this.CreateGraphics();
            DpiX = ga.DpiX;
            DpiY = ga.DpiY;
        }
        catch (Exception __dummyCatchVar0)
        {
            DpiX = DpiY = 96;
        }
        finally
        {
            if (ga != null)
                ga.Dispose();
             
        }
        // force to double buffering for smoother drawing
        this.SetStyle(ControlStyles.DoubleBuffer | ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint, true);
    }

    public Pages getPgs() throws Exception {
        return _pgs;
    }

    public void setPgs(Pages value) throws Exception {
        _pgs = value;
    }

    /**
    * Draw- simple draw of an entire page.  Useful when printing or creating an image.
    * 
    *  @param g 
    *  @param page 
    *  @param clipRectangle
    */
    public void draw(Graphics g, int page, System.Drawing.Rectangle clipRectangle, boolean drawBackground) throws Exception {
        DpiX = g.DpiX;
        // this can change (e.g. printing graphics context)
        DpiY = g.DpiY;
        //			g.InterpolationMode = InterpolationMode.HighQualityBilinear;	// try to unfuzz charts
        g.PageUnit = GraphicsUnit.Pixel;
        g.ScaleTransform(1, 1);
        _left = 0;
        _top = 0;
        _hScroll = 0;
        _vScroll = 0;
        RectangleF r = new RectangleF(clipRectangle.X, clipRectangle.Y, clipRectangle.Width, clipRectangle.Height);
        if (drawBackground)
            g.FillRectangle(Brushes.White, pixelsX(_left), pixelsY(_top), pixelsX(_pgs.getPageWidth()), pixelsY(_pgs.getPageHeight()));
         
        processPage(g,_pgs.get___idx(page),r,false);
    }

    /**
    * Draw: accounting for scrolling and zoom factors
    * 
    *  @param g 
    *  @param zoom 
    *  @param leftOffset 
    *  @param pageGap 
    *  @param hScroll 
    *  @param vScroll 
    *  @param clipRectangle
    */
    public void draw(Graphics g, float zoom, float leftOffset, float pageGap, float hScroll, float vScroll, System.Drawing.Rectangle clipRectangle) throws Exception {
        // init for mouse handling
        _HitList.Clear();
        // remove all items from list
        _LastZoom = zoom;
        if (_pgs == null)
        {
            // No pages; means nothing to draw
            g.FillRectangle(Brushes.White, clipRectangle);
            return ;
        }
         
        g.PageUnit = GraphicsUnit.Pixel;
        g.ScaleTransform(zoom, zoom);
        DpiX = g.DpiX;
        DpiY = g.DpiY;
        // Zoom affects how much will show on the screen.  Adjust our perceived clipping rectangle
        //  to account for it.
        RectangleF r = new RectangleF();
        r = new RectangleF((clipRectangle.X) / zoom, (clipRectangle.Y) / zoom, (clipRectangle.Width) / zoom, (clipRectangle.Height) / zoom);
        // Calculate the top of the page
        int fpage = (int)(vScroll / (_pgs.getPageHeight() + pageGap));
        int lpage = (int)((vScroll + r.Height) / (_pgs.getPageHeight() + pageGap)) + 1;
        if (fpage >= _pgs.getPageCount())
            return ;
         
        if (lpage >= _pgs.getPageCount())
            lpage = _pgs.getPageCount() - 1;
         
        _hScroll = hScroll;
        _left = leftOffset;
        _top = pageGap;
        for (int p = fpage;p <= lpage;p++)
        {
            // Loop thru the visible pages
            _vScroll = vScroll - p * (_pgs.getPageHeight() + pageGap);
            System.Drawing.Rectangle pr = new System.Drawing.Rectangle((int)pixelsX(_left - _hScroll), (int)pixelsY(_top - _vScroll), (int)pixelsX(_pgs.getPageWidth()), (int)pixelsY(_pgs.getPageHeight()));
            g.FillRectangle(Brushes.White, pr);
            processPage(g,_pgs.get___idx(p),r,true);
            // Draw the page outline
            Pen pn = new Pen(Brushes.Black, 1);
            try
            {
                {
                    int z3 = Math.Min((int)(3f / zoom), 3);
                    if (z3 <= 0)
                        z3 = 1;
                     
                    int z4 = Math.Min((int)(4f / zoom), 4);
                    if (z4 <= 0)
                        z4 = 1;
                     
                    g.DrawRectangle(pn, pr);
                    // outline of page
                    g.FillRectangle(Brushes.Black, pr.X + pr.Width, pr.Y + z3, z3, pr.Height);
                    // right side of page
                    g.FillRectangle(Brushes.Black, pr.X + z3, pr.Y + pr.Height, pr.Width, z4);
                }
            }
            finally
            {
                if (pn != null)
                    Disposable.mkDisposable(pn).dispose();
                 
            }
        }
    }

    // bottom of page
    protected void onMouseDown(MouseEventArgs mea) throws Exception {
        super.OnMouseDown(mea);
        // allow base to process first
        HitListEntry hle = this.getHitListEntry(mea);
        setHitListCursor(hle);
        // set the cursor based on the hit list entry
        if (mea.Button != MouseButtons.Left || hle == null)
            return ;
         
        if (hle.pi.getHyperLink() != null)
        {
            try
            {
                System.Diagnostics.Process.Start(hle.pi.getHyperLink());
            }
            catch (Exception ex)
            {
                MessageBox.Show(String.Format("Unable to link to {0}{1}{2}", hle.pi.getHyperLink(), Environment.NewLine, ex.Message), "HyperLink Error");
            }
        
        }
         
    }

    protected void onMouseLeave(EventArgs ea) throws Exception {
        _tt.Active = false;
    }

    protected void onMouseMove(MouseEventArgs mea) throws Exception {
        Cursor c = Cursors.Default;
        HitListEntry hle = this.getHitListEntry(mea);
        setHitListCursor(hle);
        setHitListTooltip(hle);
        return ;
    }

    private void setHitListCursor(HitListEntry hle) throws Exception {
        Cursor c = Cursors.Default;
        if (hle == null)
        {
        }
        else if (hle.pi.getHyperLink() != null || hle.pi.getBookmarkLink() != null)
            c = Cursors.Hand;
          
        if (Cursor.Current != c)
            Cursor.Current = c;
         
    }

    private void setHitListTooltip(HitListEntry hle) throws Exception {
        if (hle == null || hle.pi.getTooltip() == null)
            _tt.Active = false;
        else
        {
            _tt.SetToolTip(this, hle.pi.getTooltip());
            _tt.Active = true;
        } 
    }

    private HitListEntry getHitListEntry(MouseEventArgs mea) throws Exception {
        if (_HitList.Count <= 0)
            return null;
         
        PointF p = new PointF(mea.X / _LastZoom, mea.Y / _LastZoom);
        try
        {
            for (Object __dummyForeachVar0 : this._HitList)
            {
                HitListEntry hle = (HitListEntry)__dummyForeachVar0;
                if (hle.rect.Contains(p))
                    return hle;
                 
            }
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return null;
    }

    // can get synchronization error due to multi-threading; just skip the error
    private float pixelsX(float x) throws Exception {
        return (x * DpiX / 72.0f);
    }

    private float pixelsY(float y) throws Exception {
        return (y * DpiY / 72.0f);
    }

    // render all the objects in a page (or any composite object
    private void processPage(Graphics g, IEnumerable p, RectangleF clipRect, boolean bHitList) throws Exception {
        for (Object __dummyForeachVar1 : p)
        {
            PageItem pi = (PageItem)__dummyForeachVar1;
            if (pi instanceof PageTextHtml)
            {
                // PageTextHtml is actually a composite object (just like a page)
                processHtml(pi instanceof PageTextHtml ? (PageTextHtml)pi : (PageTextHtml)null,g,clipRect,bHitList);
                continue;
            }
             
            if (pi instanceof PageLine)
            {
                PageLine pl = pi instanceof PageLine ? (PageLine)pi : (PageLine)null;
                drawLine(pl.getSI().BColorLeft,pl.getSI().BStyleLeft,pl.getSI().BWidthLeft,g,pixelsX(pl.getX() + _left - _hScroll),pixelsY(pl.getY() + _top - _vScroll),pixelsX(pl.getX2() + _left - _hScroll),pixelsY(pl.getY2() + _top - _vScroll));
                continue;
            }
             
            RectangleF rect = new RectangleF(pixelsX(pi.getX() + _left - _hScroll), pixelsY(pi.getY() + _top - _vScroll), pixelsX(pi.getW()), pixelsY(pi.getH()));
            // Maintain the hit list
            if (bHitList)
            {
                // Only care about items with links and tips
                if (pi.getHyperLink() != null || pi.getBookmarkLink() != null || pi.getTooltip() != null)
                {
                    _HitList.Add(new HitListEntry(rect,pi));
                }
                 
            }
             
            if (!rect.IntersectsWith(clipRect))
                continue;
             
            if (pi.getSI().BackgroundImage != null)
            {
                // put out any background image
                PageImage i = pi.getSI().BackgroundImage;
                drawImage(i,g,rect);
            }
             
            if (pi instanceof PageText)
            {
                PageText pt = pi instanceof PageText ? (PageText)pi : (PageText)null;
                drawString(pt,g,rect);
            }
            else if (pi instanceof PageImage)
            {
                PageImage i = pi instanceof PageImage ? (PageImage)pi : (PageImage)null;
                drawImage(i,g,rect);
            }
            else if (pi instanceof PageRectangle)
            {
                this.DrawBackground(g, rect, pi.getSI());
            }
               
            drawBorder(pi,g,rect);
        }
    }

    private void drawBackground(Graphics g, System.Drawing.RectangleF rect, StyleInfo si) throws Exception {
        LinearGradientBrush linGrBrush = null;
        SolidBrush sb = null;
        try
        {
            if (si.BackgroundGradientType != BackgroundGradientTypeEnum.None && !si.BackgroundGradientEndColor.IsEmpty && !si.BackgroundColor.IsEmpty)
            {
                Color c = si.BackgroundColor;
                Color ec = si.BackgroundGradientEndColor;
                switch(si.BackgroundGradientType)
                {
                    case LeftRight: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case TopBottom: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
                        break;
                    case Center: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case DiagonalLeft: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.ForwardDiagonal);
                        break;
                    case DiagonalRight: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.BackwardDiagonal);
                        break;
                    case HorizontalCenter: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case VerticalCenter: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
                        break;
                    default: 
                        break;
                
                }
            }
             
            if (linGrBrush != null)
            {
                g.FillRectangle(linGrBrush, rect);
                linGrBrush.Dispose();
            }
            else if (!si.BackgroundColor.IsEmpty)
            {
                sb = new SolidBrush(si.BackgroundColor);
                g.FillRectangle(sb, rect);
                sb.Dispose();
            }
              
        }
        finally
        {
            if (linGrBrush != null)
                linGrBrush.Dispose();
             
            if (sb != null)
                sb.Dispose();
             
        }
        return ;
    }

    private void drawBorder(PageItem pi, Graphics g, RectangleF r) throws Exception {
        if (r.Height <= 0 || r.Width <= 0)
            return ;
         
        // no bounding box to use
        StyleInfo si = pi.getSI();
        DrawLine(si.BColorTop, si.BStyleTop, si.BWidthTop, g, r.X, r.Y, r.Right, r.Y);
        DrawLine(si.BColorRight, si.BStyleRight, si.BWidthRight, g, r.Right, r.Y, r.Right, r.Bottom);
        DrawLine(si.BColorLeft, si.BStyleLeft, si.BWidthLeft, g, r.X, r.Y, r.X, r.Bottom);
        DrawLine(si.BColorBottom, si.BStyleBottom, si.BWidthBottom, g, r.X, r.Bottom, r.Right, r.Bottom);
        return ;
    }

    private void drawImage(PageImage pi, Graphics g, RectangleF r) throws Exception {
        Stream strm = null;
        System.Drawing.Image im = null;
        try
        {
            strm = new MemoryStream(pi.getImageData());
            im = System.Drawing.Image.FromStream(strm);
            DrawImageSized(pi, im, g, r);
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
    }

    private void drawImageSized(PageImage pi, fyiReporting.RDL.Image im, Graphics g, RectangleF r) throws Exception {
        float height = new float(), width = new float();
        // some work variables
        StyleInfo si = pi.getSI();
        // adjust drawing rectangle based on padding
        RectangleF r2 = new RectangleF(r.Left + pixelsX(si.PaddingLeft), r.Top + pixelsY(si.PaddingTop), r.Width - pixelsX(si.PaddingLeft + si.PaddingRight), r.Height - pixelsY(si.PaddingTop + si.PaddingBottom));
        Rectangle ir;
        // int work rectangle
        switch(pi.getSizing())
        {
            case AutoSize: 
                // Note: GDI+ will stretch an image when you only provide
                //  the left/top coordinates.  This seems pretty stupid since
                //  it results in the image being out of focus even though
                //  you don't want the image resized.
                if (g.DpiX == im.HorizontalResolution && g.DpiY == im.VerticalResolution)
                {
                    ir = new Rectangle(Convert.ToInt32(r2.Left), Convert.ToInt32(r2.Top), im.getWidth(), im.getHeight());
                }
                else
                    ir = new Rectangle(Convert.ToInt32(r2.Left), Convert.ToInt32(r2.Top), Convert.ToInt32(r2.Width), Convert.ToInt32(r2.Height)); 
                g.DrawImage(im, ir);
                break;
            case Clip: 
                Region saveRegion = g.Clip;
                Region clipRegion = new Region(g.Clip.GetRegionData());
                clipRegion.Intersect(r2);
                g.Clip = clipRegion;
                if (g.DpiX == im.HorizontalResolution && g.DpiY == im.VerticalResolution)
                {
                    ir = new Rectangle(Convert.ToInt32(r2.Left), Convert.ToInt32(r2.Top), im.getWidth(), im.getHeight());
                }
                else
                    ir = new Rectangle(Convert.ToInt32(r2.Left), Convert.ToInt32(r2.Top), Convert.ToInt32(r2.Width), Convert.ToInt32(r2.Height)); 
                g.DrawImage(im, ir);
                g.Clip = saveRegion;
                break;
            case FitProportional: 
                float ratioIm = (float)im.getHeight() / (float)im.getWidth();
                float ratioR = r2.Height / r2.Width;
                height = r2.Height;
                width = r2.Width;
                if (ratioIm > ratioR)
                {
                    // this means the rectangle width must be corrected
                    width = height * (1 / ratioIm);
                }
                else if (ratioIm < ratioR)
                {
                    // this means the ractangle height must be corrected
                    height = width * ratioIm;
                }
                  
                r2 = new RectangleF(r2.X, r2.Y, width, height);
                g.DrawImage(im, r2);
                break;
            case Fit: 
            default: 
                g.DrawImage(im, r2);
                break;
        
        }
        return ;
    }

    private void drawLine(Color c, BorderStyleEnum bs, float w, Graphics g, float x, float y, float x2, float y2) throws Exception {
        if (bs == BorderStyleEnum.None || c.IsEmpty || w <= 0)
            return ;
         
        // nothing to draw
        Pen p = null;
        try
        {
            p = new Pen(c, w);
            switch(bs)
            {
                case Dashed: 
                    p.DashStyle = DashStyle.Dash;
                    break;
                case Dotted: 
                    p.DashStyle = DashStyle.Dot;
                    break;
                case Double: 
                case Groove: 
                case Inset: 
                case Solid: 
                case Outset: 
                case Ridge: 
                case WindowInset: 
                default: 
                    p.DashStyle = DashStyle.Solid;
                    break;
            
            }
            g.DrawLine(p, x, y, x2, y2);
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
    }

    private void processHtml(PageTextHtml pth, Graphics g, RectangleF clipRect, boolean bHitList) throws Exception {
        pth.build(g);
        // Builds the subobjects that make up the html
        this.processPage(g,pth,clipRect,bHitList);
    }

    private void drawString(PageText pt, Graphics g, RectangleF r) throws Exception {
        StyleInfo si = pt.getSI();
        String s = pt.getText();
        Font drawFont = null;
        StringFormat drawFormat = null;
        Brush drawBrush = null;
        try
        {
            // STYLE
            System.Drawing.FontStyle fs = 0;
            if (si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic)
                fs |= System.Drawing.FontStyle.Italic;
             
            switch(si.TextDecoration)
            {
                case Underline: 
                    fs |= System.Drawing.FontStyle.Underline;
                    break;
                case LineThrough: 
                    fs |= System.Drawing.FontStyle.Strikeout;
                    break;
                case Overline: 
                case None: 
                    break;
            
            }
            // WEIGHT
            switch(si.FontWeight)
            {
                case Bold: 
                case Bolder: 
                case W500: 
                case W600: 
                case W700: 
                case W800: 
                case W900: 
                    fs |= System.Drawing.FontStyle.Bold;
                    break;
                default: 
                    break;
            
            }
            drawFont = new Font(si.getFontFamily(), si.FontSize, fs);
            // si.FontSize already in points
            // ALIGNMENT
            drawFormat = new StringFormat();
            switch(si.TextAlign)
            {
                case Right: 
                    drawFormat.Alignment = StringAlignment.Far;
                    break;
                case Center: 
                    drawFormat.Alignment = StringAlignment.Center;
                    break;
                case Left: 
                default: 
                    drawFormat.Alignment = StringAlignment.Near;
                    break;
            
            }
            if (pt.getSI().WritingMode == WritingModeEnum.tb_rl)
            {
                drawFormat.FormatFlags |= StringFormatFlags.DirectionRightToLeft;
                drawFormat.FormatFlags |= StringFormatFlags.DirectionVertical;
            }
             
            switch(si.VerticalAlign)
            {
                case Bottom: 
                    drawFormat.LineAlignment = StringAlignment.Far;
                    break;
                case Middle: 
                    drawFormat.LineAlignment = StringAlignment.Center;
                    break;
                case Top: 
                default: 
                    drawFormat.LineAlignment = StringAlignment.Near;
                    break;
            
            }
            // draw the background
            DrawBackground(g, r, si);
            // adjust drawing rectangle based on padding
            RectangleF r2 = new RectangleF(r.Left + si.PaddingLeft, r.Top + si.PaddingTop, r.Width - si.PaddingLeft - si.PaddingRight, r.Height - si.PaddingTop - si.PaddingBottom);
            drawBrush = new SolidBrush(si.Color);
            if (pt.getNoClip())
                // request not to clip text
                g.DrawString(pt.getText(), drawFont, drawBrush, new PointF(r.Left, r.Top), drawFormat);
            else
                g.DrawString(pt.getText(), drawFont, drawBrush, r2, drawFormat); 
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFont.Dispose();
             
            if (drawBrush != null)
                drawBrush.Dispose();
             
        }
    }

    static class HitListEntry   
    {
        public RectangleF rect = new RectangleF();
        public PageItem pi;
        public HitListEntry(RectangleF r, PageItem pitem) throws Exception {
            rect = r;
            pi = pitem;
        }
    
    }

}


