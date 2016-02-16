//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.PdfFonts;
import fyiReporting.RDL.PdfImages;
import fyiReporting.RDL.PdfPage;
import fyiReporting.RDL.PdfPageSize;
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
* Different elements in the pdf file
*/
public class PdfElements   
{
    private PdfPageSize pSize = new PdfPageSize();
    private StringBuilder elements = new StringBuilder();
    private PdfPage p;
    // Below are used when rotating text 90% -  numbers are odd but by experimentation PDF readers liked them best
    static final double rads = -283.0 / 180.0;
    static final double radsCos = Math.Cos(rads);
    static final double radsSin = Math.Sin(rads);
    public PdfElements(PdfPage pg, PdfPageSize pageSize) throws Exception {
        p = pg;
        pSize = pageSize;
        elements = new StringBuilder();
    }

    public PdfPageSize getPageSize() throws Exception {
        return pSize;
    }

    /**
    * Page line element at the X Y to X2 Y2 position
    * 
    *  @return
    */
    public void addLine(float x, float y, float x2, float y2, StyleInfo si) throws Exception {
        AddLine(x, y, x2, y2, si.BWidthTop, si.BColorTop, si.BStyleTop);
    }

    /**
    * Page line element at the X Y to X2 Y2 position
    * 
    *  @return
    */
    public void addLine(float x, float y, float x2, float y2, float width, System.Drawing.Color c, BorderStyleEnum ls) throws Exception {
        // Get the line color
        double red = c.R;
        double green = c.G;
        double blue = c.B;
        red = Math.Round((red / 255), 3);
        green = Math.Round((green / 255), 3);
        blue = Math.Round((blue / 255), 3);
        // Get the line style Dotted - Dashed - Solid
        String linestyle = new String();
        switch(ls)
        {
            case Dashed: 
                linestyle = "[3 2] 0 d";
                break;
            case Dotted: 
                linestyle = "[2] 0 d";
                break;
            case Solid: 
            default: 
                linestyle = "[] 0 d";
                break;
        
        }
        // line width
        // line color
        // line style
        elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nq\t{0} w\t{1} {2} {3} RG\t{4}\t{5} {6} m\t{7} {8} l\tS\tQ\t", width, red, green, blue, linestyle, x, pSize.yHeight - y, x2, pSize.yHeight - y2);
    }

    // positioning
    /**
    * Add a filled rectangle
    * 
    *  @return
    */
    public void addFillRect(float x, float y, float width, float height, Color c) throws Exception {
        // Get the fill color
        double red = c.R;
        double green = c.G;
        double blue = c.B;
        red = Math.Round((red / 255), 3);
        green = Math.Round((green / 255), 3);
        blue = Math.Round((blue / 255), 3);
        // color
        elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nq\t{0} {1} {2} RG\t{0} {1} {2} rg\t{3} {4} {5} {6} re\tf\tQ\t", red, green, blue, x, pSize.yHeight - y - height, width, height);
    }

    // positioning
    /**
    * Add image to the page.  Adds a new XObject Image and a reference to it.
    * 
    *  @return string Image name
    */
    public String addImage(PdfImages images, String name, int contentRef, StyleInfo si, ImageFormat imf, float x, float y, float width, float height, byte[] im, int samplesW, int samplesH, String url) throws Exception {
        String imgname = images.GetPdfImage(this.p, name, contentRef, imf, im, samplesW, samplesH);
        elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nq\t{2} 0 0 {3} {0} {1} cm\t", x, pSize.yHeight - y - height, width, height);
        // push graphics state, positioning
        elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\t/{0} Do\tQ\t", imgname);
        // do the image then pop graphics state
        if (url != null)
            p.AddHyperlink(x, pSize.yHeight - y, height, width, url);
         
        // Border goes around the image padding
        addBorder(si,x - si.PaddingLeft,y - si.PaddingTop,height + si.PaddingTop + si.PaddingBottom,width + si.PaddingLeft + si.PaddingRight);
        return imgname;
    }

    // add any required border
    /**
    * Page Rectangle element at the X Y position
    * 
    *  @return
    */
    public void addRectangle(float x, float y, float height, float width, StyleInfo si, String url) throws Exception {
        // Draw background rectangle if needed
        if (!si.BackgroundColor.IsEmpty && height > 0 && width > 0)
        {
            // background color, height and width are specified
            addFillRect(x,y,width,height,si.BackgroundColor);
        }
         
        addBorder(si,x,y,height,width);
        // add any required border
        if (url != null)
            p.AddHyperlink(x, pSize.yHeight - y, height, width, url);
         
        return ;
    }

    /**
    * Page Text element at the X Y position; multiple lines handled
    * 
    *  @return
    */
    public void addText(float x, float y, float height, float width, String[] sa, StyleInfo si, PdfFonts fonts, float[] tw, boolean bWrap, String url, boolean bNoClip) throws Exception {
        // Calculate the RGB colors e.g. RGB(255, 0, 0) = red = 1 0 0 rg
        double r = si.Color.R;
        double g = si.Color.G;
        double b = si.Color.B;
        r = Math.Round((r / 255), 3);
        g = Math.Round((g / 255), 3);
        b = Math.Round((b / 255), 3);
        String pdfFont = fonts.getPdfFont(si);
        for (int i = 0;i < sa.Length;i++)
        {
            // get the pdf font resource name
            // Loop thru the lines of text
            String text = sa[i];
            float textwidth = tw[i];
            // Calculate the x position
            float startX = x + si.PaddingLeft;
            // TODO: handle tb_rl
            float startY = y + si.PaddingTop + (i * si.FontSize);
            // TODO: handle tb_rl
            if (si.WritingMode == WritingModeEnum.lr_tb)
            {
                // TODO: not sure what alignment means with tb_lr so I'll leave it out for now
                switch(si.TextAlign)
                {
                    case Center: 
                        if (width > 0)
                            startX = x + si.PaddingLeft + (width - si.PaddingLeft - si.PaddingRight) / 2 - textwidth / 2;
                         
                        break;
                    case Right: 
                        if (width > 0)
                            startX = x + width - textwidth - si.PaddingRight;
                         
                        break;
                    case Left: 
                    default: 
                        break;
                
                }
                // Calculate the y position
                switch(si.VerticalAlign)
                {
                    case Middle: 
                        if (height <= 0)
                            break;
                         
                        // calculate the middle of the region
                        startY = y + si.PaddingTop + (height - si.PaddingTop - si.PaddingBottom) / 2 - si.FontSize / 2;
                        // now go up or down depending on which line
                        if (sa.Length == 1)
                            break;
                         
                        if (sa.Length % 2 == 0)
                        {
                            // even number
                            startY = startY - ((sa.Length / 2 - i) * si.FontSize) + si.FontSize / 2;
                        }
                        else
                        {
                            startY = startY - ((sa.Length / 2 - i) * si.FontSize);
                        } 
                        break;
                    case Bottom: 
                        if (height <= 0)
                            break;
                         
                        startY = y + height - si.PaddingBottom - (si.FontSize * (sa.Length - i));
                        break;
                    case Top: 
                    default: 
                        break;
                
                }
            }
             
            // Draw background rectangle if needed (only put out on the first line, since we do whole rectangle)
            if (!si.BackgroundColor.IsEmpty && height > 0 && width > 0 && i == 0)
            {
                // background color, height and width are specified
                addFillRect(x,y,width,height,si.BackgroundColor);
            }
             
            // Set the clipping path
            if (height > 0 && width > 0)
            {
                if (bNoClip)
                    // no clipping but we still want URL checking
                    elements.Append("\r\nq\t");
                else
                    elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nq\t{0} {1} {2} {3} re W n", x, pSize.yHeight - y - height, width, height); 
                if (url != null)
                    p.AddHyperlink(x, pSize.yHeight - y, height, width, url);
                 
            }
            else
                elements.Append("\r\nq\t"); 
            // Escape the text
            String newtext = text.Replace("\\", "\\\\");
            newtext = newtext.Replace("(", "\\(");
            newtext = newtext.Replace(")", "\\)");
            if (si.WritingMode == WritingModeEnum.lr_tb)
            {
                elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nBT/{0} {1} Tf\t{5} {6} {7} rg\t{2} {3} Td \t({4}) Tj\tET\tQ\t", pdfFont, si.FontSize, startX, (pSize.yHeight - startY - si.FontSize), newtext, r, g, b);
            }
            else
            {
                // Rotate text -90 degrees=-.5 radians (this works for english don't know about true tb-rl language)
                //   had to play with reader to find best approximation for this rotation; didn't do what I expected
                //    see pdf spec section 4.2.2 pg 141  "Common Transformations"
                elements.AppendFormat(NumberFormatInfo.InvariantInfo, "\r\nBT/{0} {1} Tf\t{5} {6} {7} rg\t{8} {9} {10} {11} {2} {3} Tm \t({4}) Tj\tET\tQ\t", pdfFont, si.FontSize, startX, (pSize.yHeight - startY), newtext, r, g, b, radsCos, radsSin, -radsSin, radsCos);
            } 
            // Handle underlining etc.
            float maxX = new float();
            switch(si.TextDecoration)
            {
                case Underline: 
                    maxX = width > 0 ? Math.Min(x + width, startX + textwidth) : startX + textwidth;
                    AddLine(startX, startY + si.FontSize + 1, maxX, startY + si.FontSize + 1, 1, si.Color, BorderStyleEnum.Solid);
                    break;
                case LineThrough: 
                    maxX = width > 0 ? Math.Min(x + width, startX + textwidth) : startX + textwidth;
                    AddLine(startX, startY + (si.FontSize / 2) + 1, maxX, startY + (si.FontSize / 2) + 1, 1, si.Color, BorderStyleEnum.Solid);
                    break;
                case Overline: 
                    maxX = width > 0 ? Math.Min(x + width, startX + textwidth) : startX + textwidth;
                    AddLine(startX, startY + 1, maxX, startY + 1, 1, si.Color, BorderStyleEnum.Solid);
                    break;
                case None: 
                default: 
                    break;
            
            }
        }
        addBorder(si,x,y,height,width);
        return ;
    }

    // add any required border
    void addBorder(StyleInfo si, float x, float y, float height, float width) throws Exception {
        // Handle any border required   TODO: optimize border by drawing a rect when possible
        if (height <= 0 || width <= 0)
            return ;
         
        // no bounding box to use
        float ybottom = (y + height);
        float xright = x + width;
        if (si.BStyleTop != BorderStyleEnum.None && si.BWidthTop > 0)
            AddLine(x, y, xright, y, si.BWidthTop, si.BColorTop, si.BStyleTop);
         
        if (si.BStyleRight != BorderStyleEnum.None && si.BWidthRight > 0)
            AddLine(xright, y, xright, ybottom, si.BWidthRight, si.BColorRight, si.BStyleRight);
         
        if (si.BStyleLeft != BorderStyleEnum.None && si.BWidthLeft > 0)
            AddLine(x, y, x, ybottom, si.BWidthLeft, si.BColorLeft, si.BStyleLeft);
         
        if (si.BStyleBottom != BorderStyleEnum.None && si.BWidthBottom > 0)
            AddLine(x, ybottom, xright, ybottom, si.BWidthBottom, si.BColorBottom, si.BStyleBottom);
         
        return ;
    }

    /**
    * No more elements on the page.
    * 
    *  @return
    */
    public String endElements() throws Exception {
        String r = elements.ToString();
        elements = new StringBuilder();
        return r;
    }

}


// restart it