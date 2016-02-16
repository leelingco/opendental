//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;

public class GraphicsHelper   
{
    private static int topPad = 2;
    private static int rightPad = 5;
    //helps get measurements better.
    //private static float hScale=.983f;
    /**
    * This line spacing is specifically picked to match the RichTextBox.
    */
    private static float lineSpacingForFont(String fontName) throws Exception {
        if (StringSupport.equals(fontName.ToLower(), "arial"))
        {
            return 1.08f;
        }
        else if (StringSupport.equals(fontName.ToLower(), "courier new"))
        {
            return 1.08f;
        }
          
        return 1.05f;
    }

    /**
    * Since Graphics doesn't have a line height property.  The second graphics object is used for measurement purposes.
    */
    public static void drawString(Graphics g, Graphics gfx, String str, Font font, Brush brush, Rectangle bounds) throws Exception {
        SizeF fit = new SizeF(bounds.Width - rightPad, font.Height);
        StringFormat format = StringFormat.GenericTypographic;
        float pixelsPerLine = LineSpacingForFont(font.Name) * (float)font.Height;
        float lineIdx = 0;
        int chars = new int();
        int lines = new int();
        RectangleF layoutRectangle = new RectangleF();
        float layoutH = new float();
        for (int ix = 0;ix < str.Length;ix += chars)
        {
            if (bounds.Y + topPad + pixelsPerLine * lineIdx > bounds.Bottom)
            {
                break;
            }
             
            RefSupport<int> refVar___0 = new RefSupport<int>();
            RefSupport<int> refVar___1 = new RefSupport<int>();
            gfx.MeasureString(str.Substring(ix), font, fit, format, refVar___0, refVar___1);
            chars = refVar___0.getValue();
            lines = refVar___1.getValue();
            if (bounds.Y + topPad + pixelsPerLine * lineIdx + font.Height > bounds.Bottom)
            {
                layoutH = bounds.Bottom - (bounds.Y + topPad + pixelsPerLine * lineIdx);
            }
            else
            {
                layoutH = font.Height + 2;
            } 
            //any amount of extra padding here will not cause malfunction
            layoutRectangle = new RectangleF(bounds.X, (float)(bounds.Y + topPad + pixelsPerLine * lineIdx), bounds.Width + 50, layoutH);
            g.DrawString(str.Substring(ix, chars), font, brush, layoutRectangle);
            lineIdx += 1;
        }
    }

    /**
    * The pdfSharp version of drawstring.  g is used for measurement.  scaleToPix scales xObjects to pixels.
    */
    public static void drawStringX(XGraphics xg, Graphics g, double scaleToPix, String str, XFont xfont, XBrush xbrush, XRect xbounds) throws Exception {
        //There are two coordinate systems here: pixels (used by us) and points (used by PdfSharp).
        //MeasureString and ALL related measurement functions must use pixels.
        //DrawString is the ONLY function that uses points.
        //pixels:
        Rectangle bounds = new Rectangle((int)(scaleToPix * xbounds.Left), (int)(scaleToPix * xbounds.Top), (int)(scaleToPix * xbounds.Width), (int)(scaleToPix * xbounds.Height));
        FontStyle fontstyle = FontStyle.Regular;
        if (xfont.Style == XFontStyle.Bold)
        {
            fontstyle = FontStyle.Bold;
        }
         
        //pixels: (except Size is em-size)
        Font font = new Font(xfont.Name, (float)xfont.Size, fontstyle);
        //pixels:
        SizeF fit = new SizeF((float)(bounds.Width - rightPad), (float)(font.Height));
        StringFormat format = StringFormat.GenericTypographic;
        //pixels:
        float pixelsPerLine = LineSpacingForFont(font.Name) * (float)font.Height;
        float lineIdx = 0;
        int chars = new int();
        int lines = new int();
        //points:
        RectangleF layoutRectangle = new RectangleF();
        for (int ix = 0;ix < str.Length;ix += chars)
        {
            if (bounds.Y + topPad + pixelsPerLine * lineIdx > bounds.Bottom)
            {
                break;
            }
             
            //pixels:
            RefSupport<int> refVar___2 = new RefSupport<int>();
            RefSupport<int> refVar___3 = new RefSupport<int>();
            g.MeasureString(str.Substring(ix), font, fit, format, refVar___2, refVar___3);
            chars = refVar___2.getValue();
            lines = refVar___3.getValue();
            //PdfSharp isn't smart enough to cut off the lower half of a line.
            //if(bounds.Y+topPad+pixelsPerLine*lineIdx+font.Height > bounds.Bottom) {
            //	layoutH=bounds.Bottom-(bounds.Y+topPad+pixelsPerLine*lineIdx);
            //}
            //else {
            //	layoutH=font.Height+2;
            //}
            //use points here:
            float adjustTextDown = 10f;
            //this value was arrived at by trial and error.
            //(float)(xbounds.Y+(float)topPad/scaleToPix+(pixelsPerLine/scaleToPix)*lineIdx),
            layoutRectangle = new RectangleF((float)xbounds.X, (float)(xbounds.Y + adjustTextDown + (pixelsPerLine / scaleToPix) * lineIdx), (float)xbounds.Width + 50, 0);
            //any amount of extra padding here will not cause malfunction
            //layoutH);
            xg.DrawString(str.Substring(ix, chars), xfont, xbrush, (double)layoutRectangle.Left, (double)layoutRectangle.Top);
            lineIdx += 1;
        }
    }

    public static int measureStringH(Graphics g, String text, Font font, int width) throws Exception {
        return (int)measureString(g,text,font,width).Height;
    }

    /**
    * This also differs from the regular MeasureString in that it will correctly measure trailing carriage returns as requiring another line.
    */
    public static SizeF measureString(Graphics g, String text, Font font, int width) throws Exception {
        StringFormat format = StringFormat.GenericTypographic;
        float pixelsPerLine = LineSpacingForFont(font.Name) * (float)font.Height;
        int chars = new int();
        int lines = new int();
        SizeF fit = new SizeF(width - rightPad, float.MaxValue);
        //arbitrarily large height
        RefSupport<int> refVar___4 = new RefSupport<int>();
        RefSupport<int> refVar___5 = new RefSupport<int>();
        g.MeasureString(text, font, fit, format, refVar___4, refVar___5);
        chars = refVar___4.getValue();
        lines = refVar___5.getValue();
        float h = topPad + ((float)lines) * pixelsPerLine;
        if (text.EndsWith("\n"))
        {
            h += font.Height;
        }
         
        return new SizeF((float)width, h);
    }

}


//add another line to handle the trailing Carriage return.