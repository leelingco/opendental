//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.BackgroundGradientTypeEnum;
import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.CalendarEnum;
import fyiReporting.RDL.DirectionEnum;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.StyleBackgroundImage;
import fyiReporting.RDL.StyleBorderColor;
import fyiReporting.RDL.StyleBorderStyle;
import fyiReporting.RDL.StyleBorderWidth;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TextAlignEnum;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RDL.UnicodeBiDirectionalEnum;
import fyiReporting.RDL.VerticalAlignEnum;
import fyiReporting.RDL.WritingModeEnum;
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
* Style (borders, fonts, background, padding, ...) of a ReportItem.
*/
public class Style  extends ReportLink 
{
    StyleBorderColor _BorderColor;
    // Color of the border
    StyleBorderStyle _BorderStyle;
    // Style of the border
    StyleBorderWidth _BorderWidth;
    // Width of the border
    Expression _BackgroundColor;
    //(Color) Color of the background
    // If omitted, the background is transparent
    Expression _BackgroundGradientType;
    // The type of background gradient
    Expression _BackgroundGradientEndColor;
    //(Color) End color for the background gradient. If
    // omitted, there is no gradient.
    StyleBackgroundImage _BackgroundImage;
    // A background image for the report item.
    // If omitted, there is no background image.
    Expression _FontStyle;
    // (Enum FontStyle) Font style Default: Normal
    Expression _FontFamily;
    //(string)Name of the font family Default: Arial
    Expression _FontSize;
    //(Size) Point size of the font
    // Default: 10 pt. Min: 1 pt. Max: 200 pt.
    Expression _FontWeight;
    //(Enum FontWeight) Thickness of the font
    Expression _Format;
    //(string) .NET Framework formatting string1
    //	Note: Locale-dependent currency
    //	formatting (format code “C”) is based on
    //	the language setting for the report item
    //	Locale-dependent date formatting is
    //	supported and should be based on the
    //	language property of the ReportItem.
    //	Default: No formatting.
    Expression _TextDecoration;
    // (Enum TextDecoration) Special text formatting Default: none
    Expression _TextAlign;
    // (Enum TextAlign) Horizontal alignment of the text Default: General
    Expression _VerticalAlign;
    // (Enum VerticalAlign)	Vertical alignment of the text Default: Top
    Expression _Color;
    // (Color) The foreground color	Default: Black
    Expression _PaddingLeft;
    // (Size)Padding between the left edge of the
    // report item and its contents1
    // Default: 0 pt. Max: 1000 pt.
    Expression _PaddingRight;
    // (Size) Padding between the right edge of the
    // report item and its contents
    // Default: 0 pt. Max: 1000 pt.
    Expression _PaddingTop;
    // (Size) Padding between the top edge of the
    // report item and its contents
    // Default: 0 pt. Max: 1000 pt.
    Expression _PaddingBottom;
    // (Size) Padding between the top edge of the
    //	report item and its contents
    // Default: 0 pt. Max: 1000 pt
    Expression _LineHeight;
    // (Size) Height of a line of text
    // Default: Report output format determines
    // line height based on font size
    // Min: 1 pt. Max: 1000 pt.
    Expression _Direction;
    // (Enum Direction) Indicates whether text is written left-to-right (default)
    // or right-to-left.
    // Does not impact the alignment of text
    // unless using General alignment.
    Expression _WritingMode;
    // (Enum WritingMode) Indicates whether text is written
    // horizontally or vertically.
    Expression _Language;
    // (Language) The primary language of the text.
    // Default is Report.Language.
    Expression _UnicodeBiDirectional;
    // (Enum UnicodeBiDirection)
    // Indicates the level of embedding with
    // respect to the Bi-directional algorithm. Default: normal
    Expression _Calendar;
    // (Enum Calendar)
    //	Indicates the calendar to use for
    //	formatting dates. Must be compatible in
    //	.NET framework with the Language
    //	setting.
    Expression _NumeralLanguage;
    // (Language) The digit format to use as described by its
    // primary language. Any language is legal.
    // Default is the Language property.
    Expression _NumeralVariant;
    //(Integer) The variant of the digit format to use.
    // Currently defined values are:
    // 1: default, follow Unicode context rules
    // 2: 0123456789
    // 3: traditional digits for the script as
    //     defined in GDI+. Currently supported for:
    //		ar | bn | bo | fa | gu | hi | kn | kok | lo | mr |
    //		ms | or | pa | sa | ta | te | th | ur and variants.
    // 4: ko, ja, zh-CHS, zh-CHT only
    // 5: ko, ja, zh-CHS, zh-CHT only
    // 6: ko, ja, zh-CHS, zh-CHT only [Wide
    //     versions of regular digits]
    // 7: ko only
    boolean _ConstantStyle = new boolean();
    //  true if all Style elements are constant
    public Style(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _BorderColor = null;
        _BorderStyle = null;
        _BorderWidth = null;
        _BackgroundColor = null;
        _BackgroundGradientType = null;
        _BackgroundGradientEndColor = null;
        _BackgroundImage = null;
        _FontStyle = null;
        _FontFamily = null;
        _FontSize = null;
        _FontWeight = null;
        _Format = null;
        _TextDecoration = null;
        _TextAlign = null;
        _VerticalAlign = null;
        _Color = null;
        _PaddingLeft = null;
        _PaddingRight = null;
        _PaddingTop = null;
        _PaddingBottom = null;
        _LineHeight = null;
        _Direction = null;
        _WritingMode = null;
        _Language = null;
        _UnicodeBiDirectional = null;
        _Calendar = null;
        _NumeralLanguage = null;
        _NumeralVariant = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("BorderColor"))
            {
                _BorderColor = new StyleBorderColor(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("BorderStyle"))
            {
                _BorderStyle = new StyleBorderStyle(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("BorderWidth"))
            {
                _BorderWidth = new StyleBorderWidth(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("BackgroundColor"))
            {
                _BackgroundColor = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("BackgroundGradientType"))
            {
                _BackgroundGradientType = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("BackgroundGradientEndColor"))
            {
                _BackgroundGradientEndColor = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("BackgroundImage"))
            {
                _BackgroundImage = new StyleBackgroundImage(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("FontStyle"))
            {
                _FontStyle = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("FontFamily"))
            {
                _FontFamily = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("FontSize"))
            {
                _FontSize = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("FontWeight"))
            {
                _FontWeight = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Format"))
            {
                _Format = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("TextDecoration"))
            {
                _TextDecoration = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("TextAlign"))
            {
                _TextAlign = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("VerticalAlign"))
            {
                _VerticalAlign = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Color"))
            {
                _Color = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("PaddingLeft"))
            {
                _PaddingLeft = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("PaddingRight"))
            {
                _PaddingRight = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("PaddingTop"))
            {
                _PaddingTop = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("PaddingBottom"))
            {
                _PaddingBottom = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("LineHeight"))
            {
                _LineHeight = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("Direction"))
            {
                _Direction = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("WritingMode"))
            {
                _WritingMode = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Language"))
            {
                _Language = new Expression(r,this,xNodeLoop,ExpressionType.Language);
            }
            else if (__dummyScrutVar0.equals("UnicodeBiDirectional"))
            {
                _UnicodeBiDirectional = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Calendar"))
            {
                _Calendar = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("NumeralLanguage"))
            {
                _NumeralLanguage = new Expression(r,this,xNodeLoop,ExpressionType.Language);
            }
            else if (__dummyScrutVar0.equals("NumeralVariant"))
            {
                _NumeralVariant = new Expression(r,this,xNodeLoop,ExpressionType.Integer);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Style element '" + xNodeLoop.Name + "' ignored.");
            }                            
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_BorderColor != null)
            _BorderColor.finalPass();
         
        if (_BorderStyle != null)
            _BorderStyle.finalPass();
         
        if (_BorderWidth != null)
            _BorderWidth.finalPass();
         
        if (_BackgroundColor != null)
            _BackgroundColor.finalPass();
         
        if (_BackgroundGradientType != null)
            _BackgroundGradientType.finalPass();
         
        if (_BackgroundGradientEndColor != null)
            _BackgroundGradientEndColor.finalPass();
         
        if (_BackgroundImage != null)
            _BackgroundImage.finalPass();
         
        if (_FontStyle != null)
            _FontStyle.finalPass();
         
        if (_FontFamily != null)
            _FontFamily.finalPass();
         
        if (_FontSize != null)
            _FontSize.finalPass();
         
        if (_FontWeight != null)
            _FontWeight.finalPass();
         
        if (_Format != null)
            _Format.finalPass();
         
        if (_TextDecoration != null)
            _TextDecoration.finalPass();
         
        if (_TextAlign != null)
            _TextAlign.finalPass();
         
        if (_VerticalAlign != null)
            _VerticalAlign.finalPass();
         
        if (_Color != null)
            _Color.finalPass();
         
        if (_PaddingLeft != null)
            _PaddingLeft.finalPass();
         
        if (_PaddingRight != null)
            _PaddingRight.finalPass();
         
        if (_PaddingTop != null)
            _PaddingTop.finalPass();
         
        if (_PaddingBottom != null)
            _PaddingBottom.finalPass();
         
        if (_LineHeight != null)
            _LineHeight.finalPass();
         
        if (_Direction != null)
            _Direction.finalPass();
         
        if (_WritingMode != null)
            _WritingMode.finalPass();
         
        if (_Language != null)
            _Language.finalPass();
         
        if (_UnicodeBiDirectional != null)
            _UnicodeBiDirectional.finalPass();
         
        if (_Calendar != null)
            _Calendar.finalPass();
         
        if (_NumeralLanguage != null)
            _NumeralLanguage.finalPass();
         
        if (_NumeralVariant != null)
            _NumeralVariant.finalPass();
         
        _ConstantStyle = this.isConstant();
        return ;
    }

    public void drawBackground(fyiReporting.RDL.Report rpt, Graphics g, Row r, System.Drawing.Rectangle rect) throws Exception {
        LinearGradientBrush linGrBrush = null;
        if (this.getBackgroundGradientType() != null && this.getBackgroundGradientEndColor() != null && this.getBackgroundColor() != null)
        {
            String bgt = this.getBackgroundGradientType().evaluateString(rpt,r);
            String bgc = this.getBackgroundColor().evaluateString(rpt,r);
            Color c = XmlUtil.ColorFromHtml(bgc, System.Drawing.Color.White, rpt);
            String bgec = this.getBackgroundGradientEndColor().evaluateString(rpt,r);
            Color ec = XmlUtil.ColorFromHtml(bgec, System.Drawing.Color.White, rpt);
            System.String __dummyScrutVar1 = bgt;
            if (__dummyScrutVar1.equals("LeftRight"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
            }
            else if (__dummyScrutVar1.equals("TopBottom"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
            }
            else if (__dummyScrutVar1.equals("Center"))
            {
                //??
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
            }
            else if (__dummyScrutVar1.equals("DiagonalLeft"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.ForwardDiagonal);
            }
            else if (__dummyScrutVar1.equals("DiagonalRight"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.BackwardDiagonal);
            }
            else if (__dummyScrutVar1.equals("HorizontalCenter"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
            }
            else if (__dummyScrutVar1.equals("VerticalCenter"))
            {
                linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
            }
            else
            {
            }       
        }
         
        if (linGrBrush != null)
        {
            g.FillRectangle(linGrBrush, rect);
            linGrBrush.Dispose();
        }
        else
        {
            if (this.getBackgroundColor() != null)
            {
                String bgc = this.getBackgroundColor().evaluateString(rpt,r);
                Color c = XmlUtil.ColorFromHtml(bgc, System.Drawing.Color.White, rpt);
                SolidBrush sb = new SolidBrush(c);
                g.FillRectangle(sb, rect);
                sb.Dispose();
            }
             
        } 
        return ;
    }

    public void drawBackgroundCircle(fyiReporting.RDL.Report rpt, Graphics g, Row r, System.Drawing.Rectangle rect) throws Exception {
        // Don't use the gradient in this case (since it won't match) the rest of the
        //    background.  (Routine is only used by ChartPie in the doughnut case.)
        if (this.getBackgroundColor() != null)
        {
            String bgc = this.getBackgroundColor().evaluateString(rpt,r);
            Color c = XmlUtil.ColorFromHtml(bgc, System.Drawing.Color.White, rpt);
            SolidBrush sb = new SolidBrush(c);
            g.FillEllipse(sb, rect);
            g.DrawEllipse(Pens.Black, rect);
            sb.Dispose();
        }
         
        return ;
    }

    // Draw a border using the current style
    public void drawBorder(fyiReporting.RDL.Report rpt, Graphics g, Row r, System.Drawing.Rectangle rect) throws Exception {
        if (this.getBorderStyle() == null)
            return ;
         
        StyleBorderStyle bs = this.getBorderStyle();
        // Create points for each part of rectangular border
        Point tl = new Point(rect.Left, rect.Top);
        Point tr = new Point(rect.Right, rect.Top);
        Point bl = new Point(rect.Left, rect.Bottom);
        Point br = new Point(rect.Right, rect.Bottom);
        // Determine characteristics for each line to be drawn
        BorderStyleEnum topBS = BorderStyleEnum.None, bottomBS = BorderStyleEnum.None, leftBS = BorderStyleEnum.None, rightBS = BorderStyleEnum.None;
        topBS = bottomBS = leftBS = rightBS = BorderStyleEnum.None;
        String v = new String();
        // temporary work value
        if (getBorderStyle() != null)
        {
            if (getBorderStyle().getDefault() != null)
            {
                v = getBorderStyle().getDefault().evaluateString(rpt,r);
                topBS = bottomBS = leftBS = rightBS = StyleBorderStyle.getBorderStyle(v,BorderStyleEnum.None);
            }
             
            if (getBorderStyle().getTop() != null)
            {
                v = getBorderStyle().getTop().evaluateString(rpt,r);
                topBS = StyleBorderStyle.getBorderStyle(v,topBS);
            }
             
            if (getBorderStyle().getBottom() != null)
            {
                v = getBorderStyle().getBottom().evaluateString(rpt,r);
                bottomBS = StyleBorderStyle.getBorderStyle(v,bottomBS);
            }
             
            if (getBorderStyle().getLeft() != null)
            {
                v = getBorderStyle().getLeft().evaluateString(rpt,r);
                leftBS = StyleBorderStyle.getBorderStyle(v,leftBS);
            }
             
            if (getBorderStyle().getRight() != null)
            {
                v = getBorderStyle().getRight().evaluateString(rpt,r);
                rightBS = StyleBorderStyle.getBorderStyle(v,rightBS);
            }
             
        }
         
        Color topColor = new Color(), bottomColor = new Color(), leftColor = new Color(), rightColor = new Color();
        topColor = bottomColor = leftColor = rightColor = System.Drawing.Color.Black;
        if (getBorderColor() != null)
        {
            if (getBorderColor().getDefault() != null)
            {
                v = getBorderColor().getDefault().evaluateString(rpt,r);
                topColor = bottomColor = leftColor = rightColor = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
             
            if (getBorderColor().getTop() != null)
            {
                v = getBorderColor().getTop().evaluateString(rpt,r);
                topColor = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
             
            if (getBorderColor().getBottom() != null)
            {
                v = getBorderColor().getBottom().evaluateString(rpt,r);
                bottomColor = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
             
            if (getBorderColor().getLeft() != null)
            {
                v = getBorderColor().getLeft().evaluateString(rpt,r);
                leftColor = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
             
            if (getBorderColor().getRight() != null)
            {
                v = getBorderColor().getRight().evaluateString(rpt,r);
                rightColor = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
             
        }
         
        int topWidth = new int(), bottomWidth = new int(), leftWidth = new int(), rightWidth = new int();
        topWidth = bottomWidth = leftWidth = rightWidth = 1;
        if (getBorderWidth() != null)
        {
            if (getBorderWidth().getDefault() != null)
            {
                topWidth = bottomWidth = leftWidth = rightWidth = (int)new RSize(this.OwnerReport,getBorderWidth().getDefault().evaluateString(rpt,r)).getPixelsX();
            }
             
            if (getBorderWidth().getTop() != null)
            {
                topWidth = (int)new RSize(this.OwnerReport,getBorderWidth().getTop().evaluateString(rpt,r)).getPixelsX();
            }
             
            if (getBorderWidth().getBottom() != null)
            {
                bottomWidth = (int)new RSize(this.OwnerReport,getBorderWidth().getBottom().evaluateString(rpt,r)).getPixelsX();
            }
             
            if (getBorderWidth().getLeft() != null)
            {
                leftWidth = (int)new RSize(this.OwnerReport,getBorderWidth().getLeft().evaluateString(rpt,r)).getPixelsY();
            }
             
            if (getBorderWidth().getRight() != null)
            {
                rightWidth = (int)new RSize(this.OwnerReport,getBorderWidth().getRight().evaluateString(rpt,r)).getPixelsY();
            }
             
        }
         
        Pen p = null;
        Brush b = null;
        try
        {
            // top line
            if (topBS != BorderStyleEnum.None)
            {
                b = new SolidBrush(topColor);
                p = new Pen(b, topWidth);
                drawBorderDashStyle(p,topBS);
                g.DrawLine(p, tl, tr);
                b.Dispose();
                b = null;
                p.Dispose();
                p = null;
            }
             
            // right line
            if (rightBS != BorderStyleEnum.None)
            {
                b = new SolidBrush(rightColor);
                p = new Pen(b, rightWidth);
                drawBorderDashStyle(p,rightBS);
                g.DrawLine(p, tr, br);
                b.Dispose();
                b = null;
                p.Dispose();
                p = null;
            }
             
            // bottom line
            if (bottomBS != BorderStyleEnum.None)
            {
                b = new SolidBrush(bottomColor);
                p = new Pen(b, bottomWidth);
                drawBorderDashStyle(p,bottomBS);
                g.DrawLine(p, br, bl);
                // bottom line
                b.Dispose();
                b = null;
                p.Dispose();
                p = null;
            }
             
            // left line
            if (leftBS != BorderStyleEnum.None)
            {
                b = new SolidBrush(leftColor);
                p = new Pen(b, leftWidth);
                drawBorderDashStyle(p,leftBS);
                g.DrawLine(p, bl, tl);
                b.Dispose();
                b = null;
                p.Dispose();
                p = null;
            }
             
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
            if (b != null)
                b.Dispose();
             
        }
    }

    private void drawBorderDashStyle(Pen p, BorderStyleEnum bs) throws Exception {
        switch(bs)
        {
            case Dashed: 
                p.DashStyle = DashStyle.Dash;
                break;
            case Dotted: 
                p.DashStyle = DashStyle.Dot;
                break;
            case Double: 
                p.DashStyle = DashStyle.Solid;
                break;
            case Groove: 
                // TODO:	really need to create custom?
                p.DashStyle = DashStyle.Solid;
                break;
            case Inset: 
                // TODO:
                p.DashStyle = DashStyle.Solid;
                break;
            case None: 
                // TODO:
                p.DashStyle = DashStyle.Solid;
                break;
            case Outset: 
                // only happens for lines
                p.DashStyle = DashStyle.Solid;
                break;
            case Ridge: 
                // TODO:
                p.DashStyle = DashStyle.Solid;
                break;
            case Solid: 
                // TODO:
                p.DashStyle = DashStyle.Solid;
                break;
            case WindowInset: 
                p.DashStyle = DashStyle.Solid;
                break;
            default: 
                // TODO:
                p.DashStyle = DashStyle.Solid;
                break;
        
        }
        return ;
    }

    // really an error
    // Draw a line into the specified graphics object using the current style
    public void drawStyleLine(fyiReporting.RDL.Report rpt, Graphics g, Row r, Point s, Point e) throws Exception {
        Pen p = null;
        Brush b = null;
        try
        {
            int width = new int();
            System.Drawing.Color color = new System.Drawing.Color();
            BorderStyleEnum bs = BorderStyleEnum.None;
            // Border Width default is used for the line width
            if (getBorderWidth() != null && getBorderWidth().getDefault() != null)
                width = (int)new RSize(this.OwnerReport,getBorderWidth().getDefault().evaluateString(rpt,r)).getPixelsX();
            else
                width = 1; 
            // Border Color default is used for the line color
            if (getBorderColor() != null && getBorderColor().getDefault() != null)
            {
                String v = getBorderColor().getDefault().evaluateString(rpt,r);
                color = XmlUtil.ColorFromHtml(v, System.Drawing.Color.Black, rpt);
            }
            else
                color = System.Drawing.Color.Black; 
            //
            if (getBorderStyle() != null && getBorderStyle().getDefault() != null)
            {
                String v = getBorderStyle().getDefault().evaluateString(rpt,r);
                bs = StyleBorderStyle.getBorderStyle(v,BorderStyleEnum.None);
            }
            else
                bs = BorderStyleEnum.Solid; 
            b = new SolidBrush(color);
            p = new Pen(b, width);
            drawBorderDashStyle(p,bs);
            g.DrawLine(p, s, e);
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
            if (b != null)
                b.Dispose();
             
        }
    }

    // Draw a string into the specified graphics object using the current style
    //  information
    public void drawString(fyiReporting.RDL.Report rpt, Graphics g, Object o, TypeCode tc, Row r, System.Drawing.Rectangle rect) throws Exception {
        Font drawFont = null;
        // Font we'll draw with
        Brush drawBrush = null;
        // Brush we'll draw with
        StringFormat drawFormat = null;
        // StringFormat we'll draw with
        String s = new String();
        try
        {
            // the string to draw
            // Want to make sure we dispose of the font and brush (no matter what)
            s = Style.getFormatedString(rpt,this,r,o,tc);
            drawFont = getFont(rpt,r);
            drawBrush = getBrush(rpt,r);
            drawFormat = getStringFormat(rpt,r);
            // Draw string
            drawFormat.FormatFlags |= StringFormatFlags.NoWrap;
            g.DrawString(s, drawFont, drawBrush, rect, drawFormat);
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
    }

    static public void drawStringDefaults(Graphics g, Object o, System.Drawing.Rectangle rect) throws Exception {
        Font drawFont = null;
        SolidBrush drawBrush = null;
        StringFormat drawFormat = null;
        try
        {
            // Just use defaults to Create font and brush.
            drawFont = new Font("Arial", 10);
            drawBrush = new SolidBrush(System.Drawing.Color.Black);
            // Set format of string.
            drawFormat = new StringFormat();
            drawFormat.Alignment = StringAlignment.Center;
            // Draw string to image
            g.DrawString(o.ToString(), drawFont, drawBrush, rect, drawFormat);
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
    }

    // Calc size of a string with the specified graphics object using the current style
    //  information
    public Size measureString(fyiReporting.RDL.Report rpt, Graphics g, Object o, TypeCode tc, Row r, int maxWidth) throws Exception {
        Font drawFont = null;
        // Font we'll draw with
        StringFormat drawFormat = null;
        // StringFormat we'll draw with
        String s = new String();
        // the string to draw
        Size size = Size.Empty;
        try
        {
            // Want to make sure we dispose of the font and brush (no matter what)
            s = Style.getFormatedString(rpt,this,r,o,tc);
            drawFont = getFont(rpt,r);
            drawFormat = getStringFormat(rpt,r);
            // Measure string
            if (maxWidth == int.MaxValue)
                drawFormat.FormatFlags |= StringFormatFlags.NoWrap;
             
            SizeF ms = g.MeasureString(s, drawFont, maxWidth, drawFormat);
            size = new Size((int)Math.Ceiling(ms.Width), (int)Math.Ceiling(ms.Height));
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFormat.Dispose();
             
        }
        return size;
    }

    // Measure a string using the defaults for a Style font
    static public Size measureStringDefaults(fyiReporting.RDL.Report rpt, Graphics g, Object o, TypeCode tc, Row r, int maxWidth) throws Exception {
        Font drawFont = null;
        // Font we'll draw with
        StringFormat drawFormat = null;
        // StringFormat we'll draw with
        String s = new String();
        // the string to draw
        Size size = Size.Empty;
        try
        {
            // Want to make sure we dispose of the font and brush (no matter what)
            s = Style.getFormatedString(rpt,null,r,o,tc);
            drawFont = new Font("Arial", 10);
            drawFormat = new StringFormat();
            drawFormat.Alignment = StringAlignment.Near;
            // Measure string
            if (maxWidth == int.MaxValue)
                drawFormat.FormatFlags |= StringFormatFlags.NoWrap;
             
            SizeF ms = g.MeasureString(s, drawFont, maxWidth, drawFormat);
            size = new Size((int)Math.Ceiling(ms.Width), (int)Math.Ceiling(ms.Height));
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFormat.Dispose();
             
        }
        return size;
    }

    public Brush getBrush(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        Brush drawBrush = new Brush();
        // Get the brush information
        if (this.getColor() != null)
        {
            String c = this.getColor().evaluateString(rpt,r);
            Color color = XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
            drawBrush = new SolidBrush(color);
        }
        else
            drawBrush = new SolidBrush(System.Drawing.Color.Black); 
        return drawBrush;
    }

    public Font getFont(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // Get the font information
        // FAMILY
        String ff = new String();
        if (this.getFontFamily() != null)
            ff = this.getFontFamily().evaluateString(rpt,r);
        else
            ff = "Arial"; 
        // STYLE
        System.Drawing.FontStyle fs = 0;
        if (this.getFontStyle() != null)
        {
            String fStyle = this.getFontStyle().evaluateString(rpt,r);
            if (StringSupport.equals(fStyle, "Italic"))
                fs |= System.Drawing.FontStyle.Italic;
             
        }
         
        if (this.getTextDecoration() != null)
        {
            String td = this.getTextDecoration().evaluateString(rpt,r);
            System.String __dummyScrutVar3 = td;
            if (__dummyScrutVar3.equals("Underline"))
            {
                fs |= System.Drawing.FontStyle.Underline;
            }
            else if (__dummyScrutVar3.equals("Overline"))
            {
            }
            else // Don't support this
            if (__dummyScrutVar3.equals("LineThrough"))
            {
                fs |= System.Drawing.FontStyle.Strikeout;
            }
            else
            {
            }   
        }
         
        // WEIGHT
        if (this.getFontWeight() != null)
        {
            String weight = this.getFontWeight().evaluateString(rpt,r);
            System.String.APPLY __dummyScrutVar4 = weight.ToLower();
            if (__dummyScrutVar4.equals("bold") || __dummyScrutVar4.equals("bolder") || __dummyScrutVar4.equals("500") || __dummyScrutVar4.equals("600") || __dummyScrutVar4.equals("700") || __dummyScrutVar4.equals("800") || __dummyScrutVar4.equals("900"))
            {
                fs |= System.Drawing.FontStyle.Bold;
            }
            else
            {
            } 
        }
         
        // Nothing to do otherwise since we don't have finer gradations
        // SIZE
        float size = new float();
        // Value is in points
        if (this.getFontSize() != null)
        {
            String lsize = this.getFontSize().evaluateString(rpt,r);
            RSize rs = new RSize(this.OwnerReport,lsize);
            size = rs.getPoints();
        }
        else
            size = 10; 
        FontFamily fFamily = StyleInfo.getFontFamily(ff);
        return new Font(fFamily, size, fs);
    }

    public StringFormat getStringFormat(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // Set format of string.
        StringFormat drawFormat = new StringFormat();
        if (this.getDirection() != null)
        {
            String dir = this.getDirection().evaluateString(rpt,r);
            if (StringSupport.equals(dir, "RTL"))
                drawFormat.FormatFlags |= StringFormatFlags.DirectionRightToLeft;
             
        }
         
        if (this.getWritingMode() != null)
        {
            String wm = this.getWritingMode().evaluateString(rpt,r);
            if (StringSupport.equals(wm, "tb-rl"))
                drawFormat.FormatFlags |= StringFormatFlags.DirectionVertical;
             
        }
         
        if (this.getTextAlign() != null)
        {
            String ta = this.getTextAlign().evaluateString(rpt,r);
            System.String.APPLY __dummyScrutVar5 = ta.ToLower();
            if (__dummyScrutVar5.equals("left"))
            {
                drawFormat.Alignment = StringAlignment.Near;
            }
            else if (__dummyScrutVar5.equals("right"))
            {
                drawFormat.Alignment = StringAlignment.Far;
            }
            else
            {
                drawFormat.Alignment = StringAlignment.Center;
            }  
        }
        else
            drawFormat.Alignment = StringAlignment.Center; 
        if (this.getVerticalAlign() != null)
        {
            String va = this.getVerticalAlign().evaluateString(rpt,r);
            System.String.APPLY __dummyScrutVar6 = va.ToLower();
            if (__dummyScrutVar6.equals("bottom"))
            {
                drawFormat.LineAlignment = StringAlignment.Far;
            }
            else if (__dummyScrutVar6.equals("middle"))
            {
                drawFormat.LineAlignment = StringAlignment.Center;
            }
            else
            {
                drawFormat.LineAlignment = StringAlignment.Near;
            }  
        }
        else
            drawFormat.LineAlignment = StringAlignment.Near; 
        drawFormat.Trimming = StringTrimming.None;
        return drawFormat;
    }

    // Generate a CSS string from the specified styles
    public String getCSS(fyiReporting.RDL.Report rpt, Row row, boolean bDefaults) throws Exception {
        WorkClass wc = getWC(rpt);
        if (wc != null && wc.CssStyle != null)
            return wc.CssStyle;
         
        // When CssStyle is available; style is a constant
        //   The first time called bDefaults will affect all subsequant calls
        StringBuilder sb = new StringBuilder();
        if (this.Parent instanceof Table || this.Parent instanceof Matrix)
            sb.Append("border-collapse:collapse;");
         
        // collapse the borders
        if (_BorderColor != null)
            sb.Append(_BorderColor.getCSS(rpt,row,bDefaults));
        else if (bDefaults)
            sb.Append(StyleBorderColor.getCSSDefaults());
          
        if (_BorderStyle != null)
            sb.Append(_BorderStyle.getCSS(rpt,row,bDefaults));
        else if (bDefaults)
            sb.Append(StyleBorderStyle.getCSSDefaults());
          
        if (_BorderWidth != null)
            sb.Append(_BorderWidth.getCSS(rpt,row,bDefaults));
        else if (bDefaults)
            sb.Append(StyleBorderWidth.getCSSDefaults());
          
        if (_BackgroundColor != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "background-color:{0};", _BackgroundColor.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("background-color:transparent;");
          
        if (_BackgroundImage != null)
            sb.Append(_BackgroundImage.getCSS(rpt,row,bDefaults));
        else if (bDefaults)
            sb.Append(StyleBackgroundImage.getCSSDefaults());
          
        if (_FontStyle != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "font-style:{0};", _FontStyle.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("font-style:normal;");
          
        if (_FontFamily != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "font-family:{0};", _FontFamily.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("font-family:Arial;");
          
        if (_FontSize != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "font-size:{0};", _FontSize.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("font-size:10pt;");
          
        if (_FontWeight != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "font-weight:{0};", _FontWeight.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("font-weight:normal;");
          
        if (_TextDecoration != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "text-decoration:{0};", _TextDecoration.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("text-decoration:none;");
          
        if (_TextAlign != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "text-align:{0};", _TextAlign.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("");
          
        // no CSS default for text align
        if (_VerticalAlign != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "vertical-align:{0};", _VerticalAlign.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("vertical-align:top;");
          
        if (_Color != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "color:{0};", _Color.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("color:black;");
          
        if (_PaddingLeft != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "padding-left:{0};", _PaddingLeft.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("padding-left:0pt;");
          
        if (_PaddingRight != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "padding-right:{0};", _PaddingRight.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("padding-right:0pt;");
          
        if (_PaddingTop != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "padding-top:{0};", _PaddingTop.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("padding-top:0pt;");
          
        if (_PaddingBottom != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "padding-bottom:{0};", _PaddingBottom.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("padding-bottom:0pt;");
          
        if (_LineHeight != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "line-height:{0};", _LineHeight.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("line-height:normal;");
          
        if (this._ConstantStyle)
        {
            // We'll only do this work once
            //   when all are constant
            wc.CssStyle = sb.ToString();
            return wc.CssStyle;
        }
         
        return sb.ToString();
    }

    // Generate an evaluated version of all the style parameters; used for page processing
    public StyleInfo getStyleInfo(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        WorkClass wc = getWC(rpt);
        if (wc != null && wc.StyleInfo != null)
        {
            return (StyleInfo)wc.StyleInfo.clone();
        }
         
        // When StyleInfo is available; style is a constant
        // clone it because others can modify it after this
        StyleInfo si = new StyleInfo();
        if (this.getBorderColor() != null)
        {
            StyleBorderColor bc = this.getBorderColor();
            si.BColorLeft = bc.evalLeft(rpt,r);
            si.BColorRight = bc.evalRight(rpt,r);
            si.BColorTop = bc.evalTop(rpt,r);
            si.BColorBottom = bc.evalBottom(rpt,r);
        }
         
        if (this.getBorderStyle() != null)
        {
            StyleBorderStyle bs = this.getBorderStyle();
            si.BStyleLeft = bs.evalLeft(rpt,r);
            si.BStyleRight = bs.evalRight(rpt,r);
            si.BStyleTop = bs.evalTop(rpt,r);
            si.BStyleBottom = bs.evalBottom(rpt,r);
        }
         
        if (this.getBorderWidth() != null)
        {
            StyleBorderWidth bw = this.getBorderWidth();
            si.BWidthLeft = bw.evalLeft(rpt,r);
            si.BWidthRight = bw.evalRight(rpt,r);
            si.BWidthTop = bw.evalTop(rpt,r);
            si.BWidthBottom = bw.evalBottom(rpt,r);
        }
         
        si.BackgroundColor = this.evalBackgroundColor(rpt,r);
        // When background color not specified; and reportitem part of table
        //   use the tables background color
        if (si.BackgroundColor == System.Drawing.Color.Empty)
        {
            ReportItem ri = this.Parent instanceof ReportItem ? (ReportItem)this.Parent : (ReportItem)null;
            if (ri != null)
            {
                if (ri.getTC() != null)
                {
                    Table t = ri.getTC().getOwnerTable();
                    if (t.getStyle() != null)
                        si.BackgroundColor = t.getStyle().evalBackgroundColor(rpt,r);
                     
                }
                 
            }
             
        }
         
        si.BackgroundGradientType = this.evalBackgroundGradientType(rpt,r);
        si.BackgroundGradientEndColor = this.evalBackgroundGradientEndColor(rpt,r);
        if (this._BackgroundImage != null)
        {
            si.BackgroundImage = _BackgroundImage.getPageImage(rpt,r);
        }
        else
            si.BackgroundImage = null; 
        si.FontStyle = this.evalFontStyle(rpt,r);
        si.FontFamily = this.evalFontFamily(rpt,r);
        si.FontSize = this.evalFontSize(rpt,r);
        si.FontWeight = this.evalFontWeight(rpt,r);
        //		Expression _Format;			//(string) .NET Framework formatting string1
        si.TextDecoration = this.evalTextDecoration(rpt,r);
        si.TextAlign = this.evalTextAlign(rpt,r);
        si.VerticalAlign = this.evalVerticalAlign(rpt,r);
        si.Color = this.evalColor(rpt,r);
        si.PaddingLeft = this.evalPaddingLeft(rpt,r);
        si.PaddingRight = this.evalPaddingRight(rpt,r);
        si.PaddingTop = this.evalPaddingTop(rpt,r);
        si.PaddingBottom = this.evalPaddingBottom(rpt,r);
        si.LineHeight = this.evalLineHeight(rpt,r);
        si.Direction = this.evalDirection(rpt,r);
        si.WritingMode = this.evalWritingMode(rpt,r);
        si.Language = this.evalLanguage(rpt,r);
        si.UnicodeBiDirectional = this.evalUnicodeBiDirectional(rpt,r);
        si.Calendar = this.evalCalendar(rpt,r);
        si.NumeralLanguage = this.evalNumeralLanguage(rpt,r);
        si.NumeralVariant = this.evalNumeralVariant(rpt,r);
        if (this._ConstantStyle)
        {
            // We'll only do this work once
            wc.StyleInfo = si;
            //   when all are constant
            si = (StyleInfo)wc.StyleInfo.clone();
        }
         
        return si;
    }

    // Format a string; passed a style but style may be null;
    static public String getFormatedString(fyiReporting.RDL.Report rpt, Style s, Row row, Object o, TypeCode tc) throws Exception {
        String t = null;
        if (o == null)
            return "";
         
        try
        {
            if (s != null && s.getFormat() != null)
            {
                String format = s.getFormat().evaluateString(rpt,row);
                if (format != null && format.Length > 0)
                {
                    TypeCode __dummyScrutVar7 = tc;
                    if (__dummyScrutVar7.equals(TypeCode.DateTime))
                    {
                        t = ((DateTime)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Int16))
                    {
                        t = ((Short)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.UInt16))
                    {
                        t = ((ushort)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Int32))
                    {
                        t = ((Integer)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.UInt32))
                    {
                        t = ((uint)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Int64))
                    {
                        t = ((Long)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.UInt64))
                    {
                        t = ((ulong)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.String))
                    {
                        t = (String)o;
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Decimal))
                    {
                        t = ((double)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Single))
                    {
                        t = ((Float)o).ToString(format);
                    }
                    else if (__dummyScrutVar7.equals(TypeCode.Double))
                    {
                        t = ((Double)o).ToString(format);
                    }
                    else
                    {
                        t = o.ToString();
                    }           
                }
                else
                    t = o.ToString(); 
            }
            else
            {
                // No format provided
                // No style provided
                t = o.ToString();
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
            t = o.ToString();
        }

        return t;
    }

    // probably type mismatch from expectation
    private boolean isConstant() throws Exception {
        boolean rc = true;
        if (_BorderColor != null)
            rc = _BorderColor.isConstant();
         
        if (!rc)
            return false;
         
        if (_BorderStyle != null)
            rc = _BorderStyle.isConstant();
         
        if (!rc)
            return false;
         
        if (_BorderWidth != null)
            rc = _BorderWidth.isConstant();
         
        if (!rc)
            return false;
         
        if (_BackgroundColor != null)
            rc = _BackgroundColor.isConstant();
         
        if (!rc)
            return false;
         
        if (_BackgroundImage != null)
            rc = _BackgroundImage.isConstant();
         
        if (!rc)
            return false;
         
        if (_FontStyle != null)
            rc = _FontStyle.isConstant();
         
        if (!rc)
            return false;
         
        if (_FontFamily != null)
            rc = _FontFamily.isConstant();
         
        if (!rc)
            return false;
         
        if (_FontSize != null)
            rc = _FontSize.isConstant();
         
        if (!rc)
            return false;
         
        if (_FontWeight != null)
            rc = _FontWeight.isConstant();
         
        if (!rc)
            return false;
         
        if (_TextDecoration != null)
            rc = _TextDecoration.isConstant();
         
        if (!rc)
            return false;
         
        if (_TextAlign != null)
            rc = _TextAlign.isConstant();
         
        if (!rc)
            return false;
         
        if (_VerticalAlign != null)
            rc = _VerticalAlign.isConstant();
         
        if (!rc)
            return false;
         
        if (_Color != null)
            rc = _Color.isConstant();
         
        if (!rc)
            return false;
         
        if (_PaddingLeft != null)
            rc = _PaddingLeft.isConstant();
         
        if (!rc)
            return false;
         
        if (_PaddingRight != null)
            rc = _PaddingRight.isConstant();
         
        if (!rc)
            return false;
         
        if (_PaddingTop != null)
            rc = _PaddingTop.isConstant();
         
        if (!rc)
            return false;
         
        if (_PaddingBottom != null)
            rc = _PaddingBottom.isConstant();
         
        if (!rc)
            return false;
         
        if (_LineHeight != null)
            rc = _LineHeight.isConstant();
         
        if (!rc)
            return false;
         
        return rc;
    }

    public System.Drawing.Rectangle paddingAdjust(fyiReporting.RDL.Report rpt, Row r, System.Drawing.Rectangle rect, boolean bAddIn) throws Exception {
        int pbottom = this.evalPaddingBottomPx(rpt,r);
        int ptop = this.evalPaddingTopPx(rpt,r);
        int pleft = this.evalPaddingLeftPx(rpt,r);
        int pright = this.evalPaddingRightPx(rpt,r);
        System.Drawing.Rectangle rt = new System.Drawing.Rectangle();
        if (bAddIn)
            // add in when trying to size the object
            rt = new System.Drawing.Rectangle(rect.Left - pleft, rect.Top - ptop, rect.Width + pleft + pright, rect.Height + ptop + pbottom);
        else
            // otherwise you want the rectangle of the embedded object
            rt = new System.Drawing.Rectangle(rect.Left + pleft, rect.Top + ptop, rect.Width - pleft - pright, rect.Height - ptop - pbottom); 
        return rt;
    }

    public StyleBorderColor getBorderColor() throws Exception {
        return _BorderColor;
    }

    public void setBorderColor(StyleBorderColor value) throws Exception {
        _BorderColor = value;
    }

    public StyleBorderStyle getBorderStyle() throws Exception {
        return _BorderStyle;
    }

    public void setBorderStyle(StyleBorderStyle value) throws Exception {
        _BorderStyle = value;
    }

    public StyleBorderWidth getBorderWidth() throws Exception {
        return _BorderWidth;
    }

    public void setBorderWidth(StyleBorderWidth value) throws Exception {
        _BorderWidth = value;
    }

    public Expression getBackgroundColor() throws Exception {
        return _BackgroundColor;
    }

    public void setBackgroundColor(Expression value) throws Exception {
        _BackgroundColor = value;
    }

    public Color evalBackgroundColor(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_BackgroundColor == null)
            return System.Drawing.Color.Empty;
         
        String c = _BackgroundColor.evaluateString(rpt,row);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Empty, rpt);
    }

    public Expression getBackgroundGradientType() throws Exception {
        return _BackgroundGradientType;
    }

    public void setBackgroundGradientType(Expression value) throws Exception {
        _BackgroundGradientType = value;
    }

    public BackgroundGradientTypeEnum evalBackgroundGradientType(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_BackgroundGradientType == null)
            return BackgroundGradientTypeEnum.None;
         
        String bgt = _BackgroundGradientType.evaluateString(rpt,r);
        return StyleInfo.getBackgroundGradientType(bgt,BackgroundGradientTypeEnum.None);
    }

    public Expression getBackgroundGradientEndColor() throws Exception {
        return _BackgroundGradientEndColor;
    }

    public void setBackgroundGradientEndColor(Expression value) throws Exception {
        _BackgroundGradientEndColor = value;
    }

    public Color evalBackgroundGradientEndColor(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_BackgroundGradientEndColor == null)
            return System.Drawing.Color.Empty;
         
        String c = _BackgroundGradientEndColor.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Empty, rpt);
    }

    public StyleBackgroundImage getBackgroundImage() throws Exception {
        return _BackgroundImage;
    }

    public void setBackgroundImage(StyleBackgroundImage value) throws Exception {
        _BackgroundImage = value;
    }

    public boolean getConstantStyle() throws Exception {
        return _ConstantStyle;
    }

    public Expression getFontStyle() throws Exception {
        return _FontStyle;
    }

    public void setFontStyle(Expression value) throws Exception {
        _FontStyle = value;
    }

    public boolean isFontItalic(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (evalFontStyle(rpt,r) == fyiReporting.RDL.FontStyleEnum.Italic)
            return true;
         
        return false;
    }

    public fyiReporting.RDL.FontStyleEnum evalFontStyle(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_FontStyle == null)
            return fyiReporting.RDL.FontStyleEnum.Normal;
         
        String fs = _FontStyle.evaluateString(rpt,row);
        return StyleInfo.getFontStyle(fs,fyiReporting.RDL.FontStyleEnum.Normal);
    }

    public Expression getFontFamily() throws Exception {
        return _FontFamily;
    }

    public void setFontFamily(Expression value) throws Exception {
        _FontFamily = value;
    }

    public String evalFontFamily(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_FontFamily == null)
            return "Arial";
         
        return _FontFamily.evaluateString(rpt,row);
    }

    public Expression getFontSize() throws Exception {
        return _FontSize;
    }

    public void setFontSize(Expression value) throws Exception {
        _FontSize = value;
    }

    public float evalFontSize(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_FontSize == null)
            return 10;
         
        String pts = new String();
        pts = _FontSize.evaluateString(rpt,row);
        RSize sz = new RSize(this.OwnerReport,pts);
        return sz.getPoints();
    }

    public Expression getFontWeight() throws Exception {
        return _FontWeight;
    }

    public void setFontWeight(Expression value) throws Exception {
        _FontWeight = value;
    }

    public FontWeightEnum evalFontWeight(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_FontWeight == null)
            return FontWeightEnum.Normal;
         
        String weight = this.getFontWeight().evaluateString(rpt,row);
        return StyleInfo.getFontWeight(weight,FontWeightEnum.Normal);
    }

    public boolean isFontBold(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (this.getFontWeight() == null)
            return false;
         
        String weight = this.getFontWeight().evaluateString(rpt,r);
        System.String.APPLY __dummyScrutVar8 = weight.ToLower();
        if (__dummyScrutVar8.equals("bold") || __dummyScrutVar8.equals("bolder") || __dummyScrutVar8.equals("500") || __dummyScrutVar8.equals("600") || __dummyScrutVar8.equals("700") || __dummyScrutVar8.equals("800") || __dummyScrutVar8.equals("900"))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public Expression getFormat() throws Exception {
        return _Format;
    }

    public void setFormat(Expression value) throws Exception {
        _Format = value;
    }

    public Expression getTextDecoration() throws Exception {
        return _TextDecoration;
    }

    public void setTextDecoration(Expression value) throws Exception {
        _TextDecoration = value;
    }

    public TextDecorationEnum evalTextDecoration(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_TextDecoration == null)
            return TextDecorationEnum.None;
         
        String td = _TextDecoration.evaluateString(rpt,r);
        return StyleInfo.getTextDecoration(td,TextDecorationEnum.None);
    }

    public Expression getTextAlign() throws Exception {
        return _TextAlign;
    }

    public void setTextAlign(Expression value) throws Exception {
        _TextAlign = value;
    }

    public TextAlignEnum evalTextAlign(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_TextAlign == null)
            return TextAlignEnum.General;
         
        String a = _TextAlign.evaluateString(rpt,row);
        return StyleInfo.getTextAlign(a,TextAlignEnum.General);
    }

    public Expression getVerticalAlign() throws Exception {
        return _VerticalAlign;
    }

    public void setVerticalAlign(Expression value) throws Exception {
        _VerticalAlign = value;
    }

    public VerticalAlignEnum evalVerticalAlign(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_VerticalAlign == null)
            return VerticalAlignEnum.Top;
         
        String v = _VerticalAlign.evaluateString(rpt,row);
        return StyleInfo.getVerticalAlign(v,VerticalAlignEnum.Top);
    }

    public Expression getColor() throws Exception {
        return _Color;
    }

    public void setColor(Expression value) throws Exception {
        _Color = value;
    }

    public Color evalColor(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_Color == null)
            return System.Drawing.Color.Black;
         
        String c = _Color.evaluateString(rpt,row);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

    public Expression getPaddingLeft() throws Exception {
        return _PaddingLeft;
    }

    public void setPaddingLeft(Expression value) throws Exception {
        _PaddingLeft = value;
    }

    public float evalPaddingLeft(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingLeft == null)
            return 0;
         
        String v = _PaddingLeft.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPoints();
    }

    public int evalPaddingLeftPx(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingLeft == null)
            return 0;
         
        String v = _PaddingLeft.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPixelsX();
    }

    public Expression getPaddingRight() throws Exception {
        return _PaddingRight;
    }

    public void setPaddingRight(Expression value) throws Exception {
        _PaddingRight = value;
    }

    public float evalPaddingRight(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingRight == null)
            return 0;
         
        String v = _PaddingRight.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPoints();
    }

    public int evalPaddingRightPx(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingRight == null)
            return 0;
         
        String v = _PaddingRight.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPixelsX();
    }

    public Expression getPaddingTop() throws Exception {
        return _PaddingTop;
    }

    public void setPaddingTop(Expression value) throws Exception {
        _PaddingTop = value;
    }

    public float evalPaddingTop(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingTop == null)
            return 0;
         
        String v = _PaddingTop.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPoints();
    }

    public int evalPaddingTopPx(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingTop == null)
            return 0;
         
        String v = _PaddingTop.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPixelsY();
    }

    public Expression getPaddingBottom() throws Exception {
        return _PaddingBottom;
    }

    public void setPaddingBottom(Expression value) throws Exception {
        _PaddingBottom = value;
    }

    public float evalPaddingBottom(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingBottom == null)
            return 0;
         
        String v = _PaddingBottom.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPoints();
    }

    public int evalPaddingBottomPx(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (_PaddingBottom == null)
            return 0;
         
        String v = _PaddingBottom.evaluateString(rpt,row);
        RSize rz = new RSize(OwnerReport,v);
        return rz.getPixelsY();
    }

    public Expression getLineHeight() throws Exception {
        return _LineHeight;
    }

    public void setLineHeight(Expression value) throws Exception {
        _LineHeight = value;
    }

    public float evalLineHeight(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_LineHeight == null)
            return float.NaN;
         
        String sz = _LineHeight.evaluateString(rpt,r);
        RSize rz = new RSize(OwnerReport,sz);
        return rz.getPoints();
    }

    public Expression getDirection() throws Exception {
        return _Direction;
    }

    public void setDirection(Expression value) throws Exception {
        _Direction = value;
    }

    public DirectionEnum evalDirection(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Direction == null)
            return DirectionEnum.LTR;
         
        String d = _Direction.evaluateString(rpt,r);
        return StyleInfo.getDirection(d,DirectionEnum.LTR);
    }

    public Expression getWritingMode() throws Exception {
        return _WritingMode;
    }

    public void setWritingMode(Expression value) throws Exception {
        _WritingMode = value;
    }

    public WritingModeEnum evalWritingMode(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_WritingMode == null)
            return WritingModeEnum.lr_tb;
         
        String w = _WritingMode.evaluateString(rpt,r);
        return StyleInfo.getWritingMode(w,WritingModeEnum.lr_tb);
    }

    public Expression getLanguage() throws Exception {
        return _Language;
    }

    public void setLanguage(Expression value) throws Exception {
        _Language = value;
    }

    public String evalLanguage(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Language == null)
            return OwnerReport.evalLanguage(rpt,r);
         
        return _Language.evaluateString(rpt,r);
    }

    public Expression getUnicodeBiDirectional() throws Exception {
        return _UnicodeBiDirectional;
    }

    public void setUnicodeBiDirectional(Expression value) throws Exception {
        _UnicodeBiDirectional = value;
    }

    public UnicodeBiDirectionalEnum evalUnicodeBiDirectional(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_UnicodeBiDirectional == null)
            return UnicodeBiDirectionalEnum.Normal;
         
        String u = _UnicodeBiDirectional.evaluateString(rpt,r);
        return StyleInfo.getUnicodeBiDirectional(u,UnicodeBiDirectionalEnum.Normal);
    }

    public Expression getCalendar() throws Exception {
        return _Calendar;
    }

    public void setCalendar(Expression value) throws Exception {
        _Calendar = value;
    }

    public CalendarEnum evalCalendar(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Calendar == null)
            return CalendarEnum.Gregorian;
         
        String c = _Calendar.evaluateString(rpt,r);
        return StyleInfo.getCalendar(c,CalendarEnum.Gregorian);
    }

    public Expression getNumeralLanguage() throws Exception {
        return _NumeralLanguage;
    }

    public void setNumeralLanguage(Expression value) throws Exception {
        _NumeralLanguage = value;
    }

    public String evalNumeralLanguage(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_NumeralLanguage == null)
            return evalLanguage(rpt,r);
         
        return _NumeralLanguage.evaluateString(rpt,r);
    }

    public Expression getNumeralVariant() throws Exception {
        return _NumeralVariant;
    }

    public void setNumeralVariant(Expression value) throws Exception {
        _NumeralVariant = value;
    }

    public int evalNumeralVariant(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_NumeralVariant == null)
            return 1;
         
        int v = (int)_NumeralVariant.evaluateDouble(rpt,r);
        if (v < 1 || v > 7)
            // correct for bad data
            v = 1;
         
        return v;
    }

    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        if (!this.getConstantStyle())
            return null;
         
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public String CssStyle = new String();
        // When ConstantStyle is true; this will hold cache of css
        public StyleInfo StyleInfo;
        // When ConstantStyle is true; this will hold cache of StyleInfo
        public WorkClass() throws Exception {
            CssStyle = null;
            StyleInfo = null;
        }
    
    }

}


