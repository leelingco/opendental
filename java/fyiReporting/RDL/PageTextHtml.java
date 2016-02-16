//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.BackgroundGradientTypeEnum;
import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.PageText;
import fyiReporting.RDL.PageTextHtmlCmdLexer;
import fyiReporting.RDL.PageTextHtmlLexer;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.TextAlignEnum;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RDL.VerticalAlignEnum;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
///<summary>
/// PageTextHtml handles text that should to be formatted as HTML.  It only handles
/// a subset of HTML (e.g. "<b>,<br>, ..."
///</summary>
public class PageTextHtml  extends PageText implements IEnumerable, ICloneable
{
    List<PageItem> _items = null;
    Stack _StyleStack = new Stack();
    // work variable when processing styles
    float _TotalHeight = 0;
    public PageTextHtml(String t) throws Exception {
        super(t);
    }

    /**
    * Reset will force a recalculation of the embedded PageItems;
    */
    public void reset() throws Exception {
        _items = null;
    }

    public float getTotalHeight() throws Exception {
        if (_items == null)
            throw new Exception("Build method must be called prior to referencing TotalHeight.");
         
        return _TotalHeight;
    }

    public void build(Graphics g) throws Exception {
        PageText model = new PageText("");
        model.setHyperLink(null);
        model.setTooltip(null);
        int fontSizeModel = 3;
        if (_items != null)
            return ;
         
        // this has already been built
        _items = new List<PageItem>();
        _StyleStack = new Stack();
        // The first item is always a text box with the border and background attributes
        PageText pt = new PageText("");
        pt.setX(this.getX());
        pt.setY(this.getY());
        pt.setH(this.getH());
        pt.setW(this.getW());
        pt.setCanGrow(false);
        pt.setSI(this.getSI().clone() instanceof StyleInfo ? (StyleInfo)this.getSI().clone() : (StyleInfo)null);
        pt.getSI().PaddingBottom = pt.getSI().PaddingLeft = pt.getSI().PaddingRight = pt.getSI().PaddingTop = 0;
        pt.getSI().TextAlign = TextAlignEnum.Left;
        _items.Add(pt);
        // Now we create multiple items that represent what is in the box
        PageTextHtmlLexer hl = new PageTextHtmlLexer(this.getText());
        List<String> tokens = hl.lex();
        float textWidth = this.getW() - pt.getSI().PaddingLeft - pt.getSI().PaddingRight;
        // Now set the default style for the rest of the members
        StyleInfo si = this.getSI().clone() instanceof StyleInfo ? (StyleInfo)this.getSI().clone() : (StyleInfo)null;
        si.BStyleBottom = si.BStyleLeft = si.BStyleRight = si.BStyleTop = BorderStyleEnum.None;
        pt.getSI().TextAlign = TextAlignEnum.Left;
        pt.getSI().VerticalAlign = VerticalAlignEnum.Top;
        si.BackgroundColor = Color.Empty;
        si.BackgroundGradientType = BackgroundGradientTypeEnum.None;
        si.BackgroundImage = null;
        boolean bFirstInLine = true;
        StringBuilder sb = new StringBuilder();
        // this will hold the accumulating line
        float lineXPos = 0;
        float xPos = 0;
        float yPos = 0;
        float maxLineHeight = 0;
        float maxDescent = 0;
        float descent = new float();
        // working value for descent
        SizeF ms = new SizeF();
        boolean bWhiteSpace = false;
        List<PageItem> lineItems = new List<PageItem>();
        for (Object __dummyForeachVar0 : tokens)
        {
            String token = (String)__dummyForeachVar0;
            if (token[0] == PageTextHtmlLexer.HTMLCMD)
            {
                // indicates an HTML command
                // we need to create a PageText since the styleinfo is changing
                if (sb.Length != 0)
                {
                    pt = new PageText(sb.ToString());
                    pt.setHyperLink(model.getHyperLink());
                    pt.setTooltip(model.getTooltip());
                    pt.setNoClip(true);
                    sb = new StringBuilder();
                    pt.setX(this.getX() + lineXPos);
                    pt.setY(this.getY() + yPos);
                    pt.setCanGrow(false);
                    pt.setSI(currentStyle(si).clone() instanceof StyleInfo ? (StyleInfo)currentStyle(si).clone() : (StyleInfo)null);
                    _items.Add(pt);
                    lineItems.Add(pt);
                    RefSupport<float> refVar___0 = new RefSupport<float>();
                    ms = this.measureString(pt.getText(),pt.getSI(),g,refVar___0);
                    descent = refVar___0.getValue();
                    maxDescent = Math.Max(maxDescent, descent);
                    pt.setW(ms.Width);
                    pt.setH(ms.Height);
                    pt.setDescent(descent);
                    maxLineHeight = Math.Max(maxLineHeight, ms.Height);
                    lineXPos = xPos;
                }
                 
                // Now reset the styleinfo
                StyleInfo cs = currentStyle(si);
                String ltoken = token.Substring(1, Math.Min(token.Length - 1, 10)).ToLower();
                if (StringSupport.equals(ltoken, "<b>") || StringSupport.equals(ltoken, "<strong>"))
                    cs.FontWeight = FontWeightEnum.Bold;
                else if (StringSupport.equals(ltoken, "</b>") || StringSupport.equals(ltoken, "</strong>"))
                    cs.FontWeight = FontWeightEnum.Normal;
                else if (StringSupport.equals(ltoken, "<i>") || StringSupport.equals(ltoken, "<cite>") || StringSupport.equals(ltoken, "<var>") || StringSupport.equals(ltoken, "<em>"))
                    cs.FontStyle = fyiReporting.RDL.FontStyleEnum.Italic;
                else if (StringSupport.equals(ltoken, "</i>") || StringSupport.equals(ltoken, "</cite>") || StringSupport.equals(ltoken, "</var>") || StringSupport.equals(ltoken, "</em>"))
                    cs.FontStyle = fyiReporting.RDL.FontStyleEnum.Normal;
                else if (StringSupport.equals(ltoken, "<code>") || StringSupport.equals(ltoken, "<samp>"))
                    cs.FontFamily = "Courier New";
                else if (StringSupport.equals(ltoken, "</code>") || StringSupport.equals(ltoken, "</samp>"))
                    cs.FontFamily = this.getSI().FontFamily;
                else if (StringSupport.equals(ltoken, "<kbd>"))
                {
                    cs.FontFamily = "Courier New";
                    cs.FontWeight = FontWeightEnum.Bold;
                }
                else if (StringSupport.equals(ltoken, "</kdd>"))
                {
                    cs.FontFamily = this.getSI().FontFamily;
                    cs.FontWeight = FontWeightEnum.Normal;
                }
                else if (StringSupport.equals(ltoken, "<big>"))
                {
                    // big makes it bigger by 20% for each time over the baseline of 3
                    fontSizeModel++;
                    float inc = 1;
                    for (int i = 3;i < fontSizeModel;i++)
                    {
                        inc += .2f;
                    }
                    float h = this.getSI().FontSize * inc;
                    cs.FontSize = h;
                }
                else if (StringSupport.equals(ltoken, "</big>"))
                {
                    // undoes the effect of big
                    fontSizeModel--;
                    float inc = 1;
                    for (int i = 3;i < fontSizeModel;i++)
                    {
                        inc += .2f;
                    }
                    float h = this.getSI().FontSize / inc;
                    cs.FontSize = h;
                }
                else if (StringSupport.equals(ltoken, "<small>"))
                {
                    // small makes it smaller by 20% for each time under the baseline of 3
                    fontSizeModel--;
                    float inc = 1;
                    for (int i = 3;i > fontSizeModel;i--)
                    {
                        inc += .2f;
                    }
                    float h = this.getSI().FontSize / inc;
                    cs.FontSize = h;
                }
                else if (StringSupport.equals(ltoken, "</small>"))
                {
                    // undoes the effect of small
                    fontSizeModel++;
                    float inc = 1;
                    for (int i = 3;i > fontSizeModel;i--)
                    {
                        inc += .2f;
                    }
                    float h = this.getSI().FontSize * inc;
                    cs.FontSize = h;
                }
                else if (ltoken.StartsWith("<br"))
                {
                    yPos += maxLineHeight;
                    NormalizeLineHeight(lineItems, maxLineHeight, maxDescent);
                    maxLineHeight = xPos = lineXPos = maxDescent = 0;
                    bFirstInLine = true;
                    bWhiteSpace = false;
                }
                else if (ltoken.StartsWith("<p"))
                {
                    yPos += maxLineHeight * 2;
                    NormalizeLineHeight(lineItems, maxLineHeight, maxDescent);
                    maxLineHeight = xPos = lineXPos = maxDescent = 0;
                    bFirstInLine = true;
                    bWhiteSpace = false;
                }
                else if (ltoken.StartsWith("<a"))
                {
                    BuildAnchor(token.Substring(1), cs, model);
                }
                else if (ltoken.StartsWith("<img"))
                {
                    PageImage pimg = BuildImage(g, token.Substring(1), cs, model);
                    if (pimg != null)
                    {
                        // We got an image; add to process list
                        pimg.setY(this.getY() + yPos);
                        pimg.setX(this.getX());
                        _items.Add(pimg);
                        yPos += pimg.getH();
                        // Increment y position
                        maxLineHeight = xPos = lineXPos = maxDescent = 0;
                        bFirstInLine = true;
                        bWhiteSpace = false;
                    }
                     
                }
                else if (StringSupport.equals(ltoken, "</a>"))
                {
                    model.setHyperLink(model.setTooltip(null));
                    popStyle();
                }
                else if (ltoken.StartsWith("<span"))
                {
                    handleStyle(ltoken,si);
                }
                else if (StringSupport.equals(ltoken, "</span>"))
                {
                    popStyle();
                }
                                   
                continue;
            }
             
            if (StringSupport.equals(token, PageTextHtmlLexer.WHITESPACE))
            {
                if (!bFirstInLine)
                    bWhiteSpace = true;
                 
                continue;
            }
             
            if (!StringSupport.equals(token, PageTextHtmlLexer.EOF))
            {
                String ntoken = bWhiteSpace ? " " + token : token;
                bWhiteSpace = false;
                // can only use whitespace once
                RefSupport<float> refVar___1 = new RefSupport<float>();
                ms = this.measureString(ntoken,currentStyle(si),g,refVar___1);
                descent = refVar___1.getValue();
                if (xPos + ms.Width < textWidth)
                {
                    bFirstInLine = false;
                    sb.Append(ntoken);
                    maxDescent = Math.Max(maxDescent, descent);
                    maxLineHeight = Math.Max(maxLineHeight, ms.Height);
                    xPos += ms.Width;
                    continue;
                }
                 
            }
            else if (sb.Length == 0)
                continue;
              
            // EOF and no previous string means we're done
            pt = new PageText(sb.ToString());
            pt.setNoClip(true);
            pt.setHyperLink(model.getHyperLink());
            pt.setTooltip(model.getTooltip());
            sb = new StringBuilder();
            sb.Append(token);
            pt.setSI(currentStyle(si).clone() instanceof StyleInfo ? (StyleInfo)currentStyle(si).clone() : (StyleInfo)null);
            RefSupport<float> refVar___2 = new RefSupport<float>();
            ms = this.measureString(pt.getText(),pt.getSI(),g,refVar___2);
            descent = refVar___2.getValue();
            pt.setX(this.getX() + lineXPos);
            pt.setY(this.getY() + yPos);
            pt.setH(ms.Height);
            pt.setW(ms.Width);
            pt.setDescent(descent);
            pt.setCanGrow(false);
            _items.Add(pt);
            lineItems.Add(pt);
            maxDescent = Math.Max(maxDescent, descent);
            maxLineHeight = Math.Max(maxLineHeight, ms.Height);
            yPos += maxLineHeight;
            // Increment y position
            NormalizeLineHeight(lineItems, maxLineHeight, maxDescent);
            lineXPos = maxLineHeight = maxDescent = 0;
            // start line height over
            // Now set the xPos just after the current token
            RefSupport<float> refVar___3 = new RefSupport<float>();
            ms = this.measureString(token,currentStyle(si),g,refVar___3);
            descent = refVar___3.getValue();
            xPos = ms.Width;
        }
        _TotalHeight = yPos;
        // set the calculated height of the result
        _StyleStack = null;
        return ;
    }

    private void buildAnchor(String token, StyleInfo oldsi, PageText model) throws Exception {
        StyleInfo si = oldsi.clone() instanceof StyleInfo ? (StyleInfo)oldsi.clone() : (StyleInfo)null;
        // always push a StyleInfo
        _StyleStack.Push(si);
        //   since they will always be popped
        Hashtable ht = parseHtmlCmd(token);
        String href = (String)ht["href"];
        if (href == null || href.Length < 1)
            return ;
         
        model.setHyperLink(model.setTooltip(href));
        si.TextDecoration = TextDecorationEnum.Underline;
        si.Color = Color.Blue;
    }

    private PageImage buildImage(Graphics g, String token, StyleInfo oldsi, PageText model) throws Exception {
        PageTextHtmlCmdLexer hc = new PageTextHtmlCmdLexer(token.Substring(4));
        Hashtable ht = hc.lex();
        String src = (String)ht["src"];
        if (src == null || src.Length < 1)
            return null;
         
        String alt = (String)ht["alt"];
        String height = (String)ht["height"];
        String width = (String)ht["width"];
        String align = (String)ht["align"];
        Stream strm = null;
        System.Drawing.Image im = null;
        PageImage pi = null;
        try
        {
            // Obtain the image stream
            if (src.StartsWith("http:") || src.StartsWith("file:") || src.StartsWith("https:"))
            {
                WebRequest wreq = WebRequest.Create(src);
                WebResponse wres = wreq.GetResponse();
                strm = wres.GetResponseStream();
            }
            else
                strm = new FileStream(src, System.IO.FileMode.Open, FileAccess.Read); 
            im = System.Drawing.Image.FromStream(strm);
            int h = im.Height;
            int w = im.Width;
            MemoryStream ostrm = new MemoryStream();
            ImageFormat imf = new ImageFormat();
            imf = ImageFormat.Jpeg;
            im.Save(ostrm, imf);
            byte[] ba = ostrm.ToArray();
            ostrm.Close();
            pi = new PageImage(imf, ba, w, h);
            pi.setHyperLink(model.getHyperLink());
            pi.setTooltip(alt == null ? model.getTooltip() : alt);
            pi.setX(0);
            pi.setY(0);
            pi.setW(RSize.PointsFromPixels(g, width != null ? Convert.ToInt32(width) : w));
            pi.setH(RSize.PointsFromPixels(g, height != null ? Convert.ToInt32(height) : h));
            pi.setSI(new StyleInfo());
        }
        catch (Exception __dummyCatchVar0)
        {
            pi = null;
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
        return pi;
    }

    private StyleInfo currentStyle(StyleInfo def) throws Exception {
        if (_StyleStack == null || _StyleStack.Count == 0)
            return def;
        else
            return (StyleInfo)_StyleStack.Peek(); 
    }

    private void handleStyle(String token, StyleInfo model) throws Exception {
        StyleInfo si = model.clone() instanceof StyleInfo ? (StyleInfo)model.clone() : (StyleInfo)null;
        // always push a StyleInfo
        _StyleStack.Push(si);
        //   since they will always be popped
        Hashtable ht = parseHtmlCmd(token);
        String style = (String)ht["style"];
        if (style == null || style.Length < 1)
            return ;
         
        String[] styleList = style.Split(new char[]{ ';' });
        for (Object __dummyForeachVar1 : styleList)
        {
            String item = (String)__dummyForeachVar1;
            String[] val = item.Split(new char[]{ ':' });
            if (val.Length != 2)
                continue;
             
            // must be illegal syntax
            String tval = val[1].Trim();
            System.Array<System.String>.INDEXER.APPLY.APPLY __dummyScrutVar0 = val[0].ToLower().Trim();
            if (__dummyScrutVar0.equals("background") || __dummyScrutVar0.equals("background-color"))
            {
                si.BackgroundColor = XmlUtil.colorFromHtml(tval,si.Color);
            }
            else if (__dummyScrutVar0.equals("color"))
            {
                si.Color = XmlUtil.colorFromHtml(tval,si.Color);
            }
            else if (__dummyScrutVar0.equals("font-family"))
            {
                si.FontFamily = tval;
            }
            else if (__dummyScrutVar0.equals("font-size"))
            {
                handleStyleFontSize(si,tval);
            }
            else if (__dummyScrutVar0.equals("font-style"))
            {
                if (StringSupport.equals(tval, "italic"))
                    si.FontStyle = fyiReporting.RDL.FontStyleEnum.Italic;
                 
            }
            else if (__dummyScrutVar0.equals("font-weight"))
            {
                handleStyleFontWeight(si,tval);
            }
                  
        }
        return ;
    }

    private void handleStyleFontSize(StyleInfo si, String size) throws Exception {
        try
        {
            int i = size.IndexOf("pt");
            if (i > 0)
            {
                size = size.Remove(i, 2);
                float n = (float)Convert.ToDouble(size);
                if (size[0] == '+')
                    si.FontSize += n;
                else
                    si.FontSize = n; 
                return ;
            }
             
            i = size.IndexOf("%");
            if (i > 0)
            {
                size = size.Remove(i, 1);
                float n = (float)Convert.ToDouble(size);
                si.FontSize = n * si.FontSize;
                return ;
            }
             
            System.String __dummyScrutVar1 = size;
            if (__dummyScrutVar1.equals("xx-small"))
            {
                si.FontSize = 6;
            }
            else if (__dummyScrutVar1.equals("x-small"))
            {
                si.FontSize = 8;
            }
            else if (__dummyScrutVar1.equals("small"))
            {
                si.FontSize = 10;
            }
            else if (__dummyScrutVar1.equals("medium"))
            {
                si.FontSize = 12;
            }
            else if (__dummyScrutVar1.equals("large"))
            {
                si.FontSize = 14;
            }
            else if (__dummyScrutVar1.equals("x-large"))
            {
                si.FontSize = 16;
            }
            else if (__dummyScrutVar1.equals("xx-large"))
            {
                si.FontSize = 18;
            }
                   
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return ;
    }

    // lots of user errors will cause an exception; ignore
    private void handleStyleFontWeight(StyleInfo si, String w) throws Exception {
        try
        {
            System.String __dummyScrutVar2 = w;
            if (__dummyScrutVar2.equals("bold"))
            {
                si.FontWeight = FontWeightEnum.Bold;
            }
            else if (__dummyScrutVar2.equals("bolder"))
            {
                if (si.FontWeight > FontWeightEnum.Bolder)
                {
                    if (si.FontWeight < FontWeightEnum.W900)
                        si.FontWeight++;
                     
                }
                else if (si.FontWeight == FontWeightEnum.Normal)
                    si.FontWeight = FontWeightEnum.W700;
                else if (si.FontWeight == FontWeightEnum.Bold)
                    si.FontWeight = FontWeightEnum.W900;
                else if (si.FontWeight != FontWeightEnum.Bolder)
                    si.FontWeight = FontWeightEnum.Normal;
                    
            }
            else if (__dummyScrutVar2.equals("lighter"))
            {
                if (si.FontWeight > FontWeightEnum.Bolder)
                {
                    if (si.FontWeight > FontWeightEnum.W100)
                        si.FontWeight--;
                     
                }
                else if (si.FontWeight == FontWeightEnum.Normal)
                    si.FontWeight = FontWeightEnum.W300;
                else if (si.FontWeight == FontWeightEnum.Bold)
                    si.FontWeight = FontWeightEnum.W400;
                else if (si.FontWeight != FontWeightEnum.Lighter)
                    si.FontWeight = FontWeightEnum.Normal;
                    
            }
            else if (__dummyScrutVar2.equals("normal"))
            {
                si.FontWeight = FontWeightEnum.Normal;
            }
            else if (__dummyScrutVar2.equals("100"))
            {
                si.FontWeight = FontWeightEnum.W100;
            }
            else if (__dummyScrutVar2.equals("200"))
            {
                si.FontWeight = FontWeightEnum.W200;
            }
            else if (__dummyScrutVar2.equals("300"))
            {
                si.FontWeight = FontWeightEnum.W300;
            }
            else if (__dummyScrutVar2.equals("400"))
            {
                si.FontWeight = FontWeightEnum.W400;
            }
            else if (__dummyScrutVar2.equals("500"))
            {
                si.FontWeight = FontWeightEnum.W500;
            }
            else if (__dummyScrutVar2.equals("600"))
            {
                si.FontWeight = FontWeightEnum.W600;
            }
            else if (__dummyScrutVar2.equals("700"))
            {
                si.FontWeight = FontWeightEnum.W700;
            }
            else if (__dummyScrutVar2.equals("800"))
            {
                si.FontWeight = FontWeightEnum.W800;
            }
            else if (__dummyScrutVar2.equals("900"))
            {
                si.FontWeight = FontWeightEnum.W900;
            }
                         
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        return ;
    }

    // lots of user errors will cause an exception; ignore
    private void popStyle() throws Exception {
        if (_StyleStack != null && _StyleStack.Count > 0)
            _StyleStack.Pop();
         
    }

    private void normalizeLineHeight(List<PageItem> lineItems, float maxLineHeight, float maxDescent) throws Exception {
        for (Object __dummyForeachVar2 : lineItems)
        {
            PageItem pi = (PageItem)__dummyForeachVar2;
            if (pi instanceof PageText)
            {
                // force the text to line up
                PageText pt = (PageText)pi;
                if (pt.getH() >= maxLineHeight)
                    continue;
                 
                pt.setY(pt.getY() + (maxLineHeight - pt.getH()));
                if (pt.getDescent() > 0 && pt.getDescent() < maxDescent)
                    pt.setY(pt.getY() - (maxDescent - pt.getDescent()));
                 
            }
             
        }
        lineItems.Clear();
    }

    private Hashtable parseHtmlCmd(String token) throws Exception {
        Hashtable ht = new Hashtable();
        // find the start and the end of the command
        int start = token.IndexOf(' ');
        // look for first blank
        if (start < 0)
            return ht;
         
        int end = token.LastIndexOf('>');
        if (end < 0 || end <= start)
            return ht;
         
        String cmd = token.Substring(start, end - start);
        start = 0;
        while (start < cmd.Length)
        {
            // get the cmd: all characters up
            end = -1;
            for (int i = start;i < cmd.Length;i++)
            {
                if (cmd[i] == '=')
                {
                    end = i;
                    break;
                }
                 
            }
            if (end < 0)
                return ht;
             
            start = end + 1;
        }
        String[] keys = cmd.Split(new char[]{ '=' });
        for (int i = 0;i < keys.Length - 1;i += 2)
        {
            // remove " from the value if any
            String v = keys[i + 1];
            if (v.Length > 0 && (v[0] == '"' || v[0] == '\''))
                v = v.Substring(1);
             
            if (v.Length > 0 && (v[v.Length - 1] == '"' || v[v.Length - 1] == '\''))
                v = v.Substring(0, v.Length - 1);
             
            // normalize key to lower case
            String key = keys[i].ToLower().Trim();
            ht.Add(key, v);
        }
        return ht;
    }

    private SizeF measureString(String s, StyleInfo si, Graphics g, RefSupport<float> descent) throws Exception {
        Font drawFont = null;
        StringFormat drawFormat = null;
        SizeF ms = SizeF.Empty;
        descent.setValue(0);
        if (s == null || s.Length == 0)
            return ms;
         
        try
        {
            // STYLE
            System.Drawing.FontStyle fs = 0;
            if (si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic)
                fs |= System.Drawing.FontStyle.Italic;
             
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
            try
            {
                FontFamily ff = si.getFontFamily();
                drawFont = new Font(ff, si.FontSize, fs);
                // following algorithm comes from the C# Font Metrics documentation
                float descentPixel = si.FontSize * ff.GetCellDescent(fs) / ff.GetEmHeight(fs);
                descent.setValue(RSize.pointsFromPixels(g,descentPixel));
            }
            catch (Exception __dummyCatchVar3)
            {
                drawFont = new Font("Arial", si.FontSize, fs);
                // usually because font not found
                descent.setValue(0);
            }

            drawFormat = new StringFormat();
            drawFormat.Alignment = StringAlignment.Near;
            CharacterRange[] cr;
            drawFormat.SetMeasurableCharacterRanges(cr);
            Region[] rs = new Region[1];
            rs = g.MeasureCharacterRanges(s, drawFont, new RectangleF(0, 0, float.MaxValue, float.MaxValue), drawFormat);
            RectangleF mr = rs[0].GetBounds(g);
            ms.Height = RSize.PointsFromPixels(g, mr.Height);
            // convert to points from pixels
            ms.Width = RSize.PointsFromPixels(g, mr.Width);
            return ms;
        }
        finally
        {
            // convert to points from pixels
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFont.Dispose();
             
        }
    }

    public IEnumerator getEnumerator() throws Exception {
        if (_items == null)
            return null;
         
        return _items.GetEnumerator();
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar4)
        {
            throw __dummyCatchVar4;
        }
        catch (Exception __dummyCatchVar4)
        {
            throw new RuntimeException(__dummyCatchVar4);
        }
    
    }

}


