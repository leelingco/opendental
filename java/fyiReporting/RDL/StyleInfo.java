//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.BackgroundGradientTypeEnum;
import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.CalendarEnum;
import fyiReporting.RDL.DirectionEnum;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.TextAlignEnum;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RDL.UnicodeBiDirectionalEnum;
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
* StyleInfo (borders, fonts, background, padding, ...)
*/
public class StyleInfo  extends ICloneable 
{
    // note: all sizes are expressed as points
    // _BorderColor
    /**
    * Color of the left border
    */
    public Color BColorLeft = new Color();
    // (Color) Color of the left border
    /**
    * Color of the right border
    */
    public Color BColorRight = new Color();
    // (Color) Color of the right border
    /**
    * Color of the top border
    */
    public Color BColorTop = new Color();
    // (Color) Color of the top border
    /**
    * Color of the bottom border
    */
    public Color BColorBottom = new Color();
    // (Color) Color of the bottom border
    // _BorderStyle
    /**
    * Style of the left border
    */
    public BorderStyleEnum BStyleLeft = BorderStyleEnum.None;
    // (Enum BorderStyle) Style of the left border
    /**
    * Style of the left border
    */
    public BorderStyleEnum BStyleRight = BorderStyleEnum.None;
    // (Enum BorderStyle) Style of the left border
    /**
    * Style of the top border
    */
    public BorderStyleEnum BStyleTop = BorderStyleEnum.None;
    // (Enum BorderStyle) Style of the top border
    /**
    * Style of the bottom border
    */
    public BorderStyleEnum BStyleBottom = BorderStyleEnum.None;
    // (Enum BorderStyle) Style of the bottom border
    // _BorderWdith
    /**
    * Width of the left border. Max: 20 pt Min: 0.25 pt
    */
    public float BWidthLeft = new float();
    //(Size) Width of the left border. Max: 20 pt Min: 0.25 pt
    /**
    * Width of the right border. Max: 20 pt Min: 0.25 pt
    */
    public float BWidthRight = new float();
    //(Size) Width of the right border. Max: 20 pt Min: 0.25 pt
    /**
    * Width of the right border. Max: 20 pt Min: 0.25 pt
    */
    public float BWidthTop = new float();
    //(Size) Width of the right border. Max: 20 pt Min: 0.25 pt
    /**
    * Width of the bottom border. Max: 20 pt Min: 0.25 pt
    */
    public float BWidthBottom = new float();
    //(Size) Width of the bottom border. Max: 20 pt Min: 0.25 pt
    /**
    * Color of the background
    */
    public Color BackgroundColor = new Color();
    //(Color) Color of the background
    /**
    * The type of background gradient
    */
    public BackgroundGradientTypeEnum BackgroundGradientType = BackgroundGradientTypeEnum.None;
    // The type of background gradient
    /**
    * End color for the background gradient.
    */
    public Color BackgroundGradientEndColor = new Color();
    //(Color) End color for the background gradient.
    /**
    * A background image for the report item.
    */
    public PageImage BackgroundImage;
    // A background image for the report item.
    /**
    * Font style Default: Normal
    */
    public fyiReporting.RDL.FontStyleEnum FontStyle = fyiReporting.RDL.FontStyleEnum.Normal;
    // (Enum FontStyle) Font style Default: Normal
    /**
    * Name of the font family Default: Arial
    */
    private String _FontFamily = new String();
    //(string)Name of the font family Default: Arial -- allow comma separated value?
    /**
    * Point size of the font
    */
    public float FontSize = new float();
    //(Size) Point size of the font
    /**
    * Thickness of the font
    */
    public FontWeightEnum FontWeight = FontWeightEnum.Lighter;
    //(Enum FontWeight) Thickness of the font
    //		Expression _Format;			//(string) .NET Framework formatting string1
    /**
    * Special text formatting Default: none
    */
    public TextDecorationEnum TextDecoration = TextDecorationEnum.Underline;
    // (Enum TextDecoration) Special text formatting Default: none
    /**
    * Horizontal alignment of the text Default: General
    */
    public TextAlignEnum TextAlign = TextAlignEnum.Left;
    // (Enum TextAlign) Horizontal alignment of the text Default: General
    /**
    * Vertical alignment of the text Default: Top
    */
    public VerticalAlignEnum VerticalAlign = VerticalAlignEnum.Top;
    // (Enum VerticalAlign)	Vertical alignment of the text Default: Top
    /**
    * The foreground color	Default: Black
    */
    public Color Color = new Color();
    // (Color) The foreground color	Default: Black
    /**
    * Padding between the left edge of the report item.
    */
    public float PaddingLeft = new float();
    // (Size)Padding between the left edge of the report item.
    /**
    * Padding between the right edge of the report item.
    */
    public float PaddingRight = new float();
    // (Size) Padding between the right edge of the report item.
    /**
    * Padding between the top edge of the report item.
    */
    public float PaddingTop = new float();
    // (Size) Padding between the top edge of the report item.
    /**
    * Padding between the bottom edge of the report item.
    */
    public float PaddingBottom = new float();
    // (Size) Padding between the bottom edge of the report item.
    /**
    * Height of a line of text.
    */
    public float LineHeight = new float();
    // (Size) Height of a line of text
    /**
    * Indicates whether text is written left-to-right (default)
    */
    public DirectionEnum Direction = DirectionEnum.LTR;
    // (Enum Direction) Indicates whether text is written left-to-right (default)
    /**
    * Indicates the writing mode; e.g. left right top bottom or top bottom left right.
    */
    public WritingModeEnum WritingMode = WritingModeEnum.lr_tb;
    // (Enum WritingMode) Indicates whether text is written
    /**
    * The primary language of the text.
    */
    public String Language = new String();
    // (Language) The primary language of the text.
    /**
    * Unused.
    */
    public UnicodeBiDirectionalEnum UnicodeBiDirectional = UnicodeBiDirectionalEnum.Normal;
    // (Enum UnicodeBiDirection)
    /**
    * Calendar to use.
    */
    public CalendarEnum Calendar = CalendarEnum.Gregorian;
    // (Enum Calendar)
    /**
    * The digit format to use.
    */
    public String NumeralLanguage = new String();
    // (Language) The digit format to use as described by its
    /**
    * The variant of the digit format to use.
    */
    public int NumeralVariant = new int();
    //(Integer) The variant of the digit format to use.
    /**
    * Constructor using all defaults for the style.
    */
    public StyleInfo() throws Exception {
        BColorLeft = BColorRight = BColorTop = BColorBottom = System.Drawing.Color.Black;
        // (Color) Color of the bottom border
        BStyleLeft = BStyleRight = BStyleTop = BStyleBottom = BorderStyleEnum.None;
        // _BorderWdith
        BWidthLeft = BWidthRight = BWidthTop = BWidthBottom = 1;
        BackgroundColor = System.Drawing.Color.Empty;
        BackgroundGradientType = BackgroundGradientTypeEnum.None;
        BackgroundGradientEndColor = System.Drawing.Color.Empty;
        BackgroundImage = null;
        FontStyle = fyiReporting.RDL.FontStyleEnum.Normal;
        _FontFamily = "Arial";
        FontSize = 10;
        FontWeight = FontWeightEnum.Normal;
        TextDecoration = TextDecorationEnum.None;
        TextAlign = TextAlignEnum.Left;
        VerticalAlign = VerticalAlignEnum.Top;
        Color = System.Drawing.Color.Black;
        PaddingLeft = PaddingRight = PaddingTop = PaddingBottom = 0;
        LineHeight = 0;
        Direction = DirectionEnum.LTR;
        WritingMode = WritingModeEnum.lr_tb;
        Language = "en-US";
        UnicodeBiDirectional = UnicodeBiDirectionalEnum.Normal;
        Calendar = CalendarEnum.Gregorian;
        NumeralLanguage = Language;
        NumeralVariant = 1;
    }

    /**
    * Name of the font family Default: Arial
    */
    public String getFontFamily() throws Exception {
        int i = _FontFamily.IndexOf(",");
        return i > 0 ? _FontFamily.Substring(0, i - 1) : _FontFamily;
    }

    public void setFontFamily(String value) throws Exception {
        _FontFamily = value;
    }

    /**
    * Name of the font family Default: Arial.  Support list of families separated by ','.
    */
    public String getFontFamilyFull() throws Exception {
        return _FontFamily;
    }

    /**
    * Gets the FontFamily instance using the FontFamily string.  This supports lists of fonts.
    * 
    *  @return
    */
    public FontFamily getFontFamily() throws Exception {
        return getFontFamily(_FontFamily);
    }

    /**
    * Gets the FontFamily instance using the passed face name.  This supports lists of fonts.
    * 
    *  @return
    */
    static public FontFamily getFontFamily(String fface) throws Exception {
        String[] choices = fface.Split(',');
        FontFamily ff = null;
        for (Object __dummyForeachVar0 : choices)
        {
            String val = (String)__dummyForeachVar0;
            try
            {
                String font = null;
                // TODO: should be better way than to hard code; could put in config file??
                System.String.APPLY __dummyScrutVar0 = val.Trim();
                if (__dummyScrutVar0.equals("serif"))
                {
                    font = "Times New Roman";
                }
                else if (__dummyScrutVar0.equals("sans-serif"))
                {
                    font = "Arial";
                }
                else if (__dummyScrutVar0.equals("cursive"))
                {
                    font = "Comic Sans MS";
                }
                else if (__dummyScrutVar0.equals("fantasy"))
                {
                    font = "Impact";
                }
                else if (__dummyScrutVar0.equals("monospace"))
                {
                    font = "Courier New";
                }
                else
                {
                    font = val;
                }     
                ff = new FontFamily(font);
                if (ff != null)
                    break;
                 
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        // if font doesn't exist we will go to the next
        if (ff == null)
            ff = new FontFamily("Arial");
         
        return ff;
    }

    /**
    * True if font is bold.
    * 
    *  @return
    */
    public boolean isFontBold() throws Exception {
        switch(FontWeight)
        {
            case Bold: 
            case Bolder: 
            case W500: 
            case W600: 
            case W700: 
            case W800: 
            case W900: 
                return true;
            default: 
                return false;
        
        }
    }

    /**
    * Gets the enumerated font weight.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    static public FontWeightEnum getFontWeight(String v, FontWeightEnum def) throws Exception {
        FontWeightEnum fw = FontWeightEnum.Lighter;
        System.String.APPLY __dummyScrutVar2 = v.ToLower();
        if (__dummyScrutVar2.equals("Lighter"))
        {
            fw = FontWeightEnum.Lighter;
        }
        else if (__dummyScrutVar2.equals("Normal"))
        {
            fw = FontWeightEnum.Normal;
        }
        else if (__dummyScrutVar2.equals("bold"))
        {
            fw = FontWeightEnum.Bold;
        }
        else if (__dummyScrutVar2.equals("bolder"))
        {
            fw = FontWeightEnum.Bolder;
        }
        else if (__dummyScrutVar2.equals("500"))
        {
            fw = FontWeightEnum.W500;
        }
        else if (__dummyScrutVar2.equals("600"))
        {
            fw = FontWeightEnum.W600;
        }
        else if (__dummyScrutVar2.equals("700"))
        {
            fw = FontWeightEnum.W700;
        }
        else if (__dummyScrutVar2.equals("800"))
        {
            fw = FontWeightEnum.W800;
        }
        else if (__dummyScrutVar2.equals("900"))
        {
            fw = FontWeightEnum.W900;
        }
        else
        {
            fw = def;
        }         
        return fw;
    }

    /**
    * Returns the font style (normal or italic).
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static fyiReporting.RDL.FontStyleEnum getFontStyle(String v, fyiReporting.RDL.FontStyleEnum def) throws Exception {
        fyiReporting.RDL.FontStyleEnum f = fyiReporting.RDL.FontStyleEnum.Normal;
        System.String.APPLY __dummyScrutVar3 = v.ToLower();
        if (__dummyScrutVar3.equals("normal"))
        {
            f = fyiReporting.RDL.FontStyleEnum.Normal;
        }
        else if (__dummyScrutVar3.equals("italic"))
        {
            f = fyiReporting.RDL.FontStyleEnum.Italic;
        }
        else
        {
            f = def;
        }  
        return f;
    }

    /**
    * Gets the background gradient type.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    static public BackgroundGradientTypeEnum getBackgroundGradientType(String v, BackgroundGradientTypeEnum def) throws Exception {
        BackgroundGradientTypeEnum gt = BackgroundGradientTypeEnum.None;
        System.String.APPLY __dummyScrutVar4 = v.ToLower();
        if (__dummyScrutVar4.equals("none"))
        {
            gt = BackgroundGradientTypeEnum.None;
        }
        else if (__dummyScrutVar4.equals("leftright"))
        {
            gt = BackgroundGradientTypeEnum.LeftRight;
        }
        else if (__dummyScrutVar4.equals("topbottom"))
        {
            gt = BackgroundGradientTypeEnum.TopBottom;
        }
        else if (__dummyScrutVar4.equals("center"))
        {
            gt = BackgroundGradientTypeEnum.Center;
        }
        else if (__dummyScrutVar4.equals("diagonalleft"))
        {
            gt = BackgroundGradientTypeEnum.DiagonalLeft;
        }
        else if (__dummyScrutVar4.equals("diagonalright"))
        {
            gt = BackgroundGradientTypeEnum.DiagonalRight;
        }
        else if (__dummyScrutVar4.equals("horizontalcenter"))
        {
            gt = BackgroundGradientTypeEnum.HorizontalCenter;
        }
        else if (__dummyScrutVar4.equals("verticalcenter"))
        {
            gt = BackgroundGradientTypeEnum.VerticalCenter;
        }
        else
        {
            gt = def;
        }        
        return gt;
    }

    /**
    * Gets the text decoration.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static TextDecorationEnum getTextDecoration(String v, TextDecorationEnum def) throws Exception {
        TextDecorationEnum td = TextDecorationEnum.Underline;
        System.String.APPLY __dummyScrutVar5 = v.ToLower();
        if (__dummyScrutVar5.equals("underline"))
        {
            td = TextDecorationEnum.Underline;
        }
        else if (__dummyScrutVar5.equals("overline"))
        {
            td = TextDecorationEnum.Overline;
        }
        else if (__dummyScrutVar5.equals("linethrough"))
        {
            td = TextDecorationEnum.LineThrough;
        }
        else if (__dummyScrutVar5.equals("none"))
        {
            td = TextDecorationEnum.None;
        }
        else
        {
            td = def;
        }    
        return td;
    }

    /**
    * Gets the text alignment.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static TextAlignEnum getTextAlign(String v, TextAlignEnum def) throws Exception {
        TextAlignEnum ta = TextAlignEnum.Left;
        System.String.APPLY __dummyScrutVar6 = v.ToLower();
        if (__dummyScrutVar6.equals("left"))
        {
            ta = TextAlignEnum.Left;
        }
        else if (__dummyScrutVar6.equals("right"))
        {
            ta = TextAlignEnum.Right;
        }
        else if (__dummyScrutVar6.equals("center"))
        {
            ta = TextAlignEnum.Center;
        }
        else if (__dummyScrutVar6.equals("general"))
        {
            ta = TextAlignEnum.General;
        }
        else
        {
            ta = def;
        }    
        return ta;
    }

    /**
    * Gets the vertical alignment.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static VerticalAlignEnum getVerticalAlign(String v, VerticalAlignEnum def) throws Exception {
        VerticalAlignEnum va = VerticalAlignEnum.Top;
        System.String.APPLY __dummyScrutVar7 = v.ToLower();
        if (__dummyScrutVar7.equals("top"))
        {
            va = VerticalAlignEnum.Top;
        }
        else if (__dummyScrutVar7.equals("middle"))
        {
            va = VerticalAlignEnum.Middle;
        }
        else if (__dummyScrutVar7.equals("bottom"))
        {
            va = VerticalAlignEnum.Bottom;
        }
        else
        {
            va = def;
        }   
        return va;
    }

    /**
    * Gets the direction of the text.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static DirectionEnum getDirection(String v, DirectionEnum def) throws Exception {
        DirectionEnum d = DirectionEnum.LTR;
        System.String.APPLY __dummyScrutVar8 = v.ToLower();
        if (__dummyScrutVar8.equals("ltr"))
        {
            d = DirectionEnum.LTR;
        }
        else if (__dummyScrutVar8.equals("rtl"))
        {
            d = DirectionEnum.RTL;
        }
        else
        {
            d = def;
        }  
        return d;
    }

    /**
    * Gets the writing mode; e.g. left right top bottom or top bottom left right.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static WritingModeEnum getWritingMode(String v, WritingModeEnum def) throws Exception {
        WritingModeEnum w = WritingModeEnum.lr_tb;
        System.String.APPLY __dummyScrutVar9 = v.ToLower();
        if (__dummyScrutVar9.equals("lr-tb"))
        {
            w = WritingModeEnum.lr_tb;
        }
        else if (__dummyScrutVar9.equals("tb-rl"))
        {
            w = WritingModeEnum.tb_rl;
        }
        else
        {
            w = def;
        }  
        return w;
    }

    /**
    * Gets the unicode BiDirectional.
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static UnicodeBiDirectionalEnum getUnicodeBiDirectional(String v, UnicodeBiDirectionalEnum def) throws Exception {
        UnicodeBiDirectionalEnum u = UnicodeBiDirectionalEnum.Normal;
        System.String.APPLY __dummyScrutVar10 = v.ToLower();
        if (__dummyScrutVar10.equals("normal"))
        {
            u = UnicodeBiDirectionalEnum.Normal;
        }
        else if (__dummyScrutVar10.equals("embed"))
        {
            u = UnicodeBiDirectionalEnum.Embed;
        }
        else if (__dummyScrutVar10.equals("bidi-override"))
        {
            u = UnicodeBiDirectionalEnum.BiDi_Override;
        }
        else
        {
            u = def;
        }   
        return u;
    }

    /**
    * Gets the calendar (e.g. Gregorian, GregorianArabic, and so on)
    * 
    *  @param v 
    *  @param def 
    *  @return
    */
    public static CalendarEnum getCalendar(String v, CalendarEnum def) throws Exception {
        CalendarEnum c = CalendarEnum.Gregorian;
        System.String.APPLY __dummyScrutVar11 = v.ToLower();
        if (__dummyScrutVar11.equals("gregorian"))
        {
            c = CalendarEnum.Gregorian;
        }
        else if (__dummyScrutVar11.equals("gregorianarabic"))
        {
            c = CalendarEnum.GregorianArabic;
        }
        else if (__dummyScrutVar11.equals("gregorianmiddleeastfrench"))
        {
            c = CalendarEnum.GregorianMiddleEastFrench;
        }
        else if (__dummyScrutVar11.equals("gregoriantransliteratedenglish"))
        {
            c = CalendarEnum.GregorianTransliteratedEnglish;
        }
        else if (__dummyScrutVar11.equals("gregoriantransliteratedfrench"))
        {
            c = CalendarEnum.GregorianTransliteratedFrench;
        }
        else if (__dummyScrutVar11.equals("gregorianusenglish"))
        {
            c = CalendarEnum.GregorianUSEnglish;
        }
        else if (__dummyScrutVar11.equals("hebrew"))
        {
            c = CalendarEnum.Hebrew;
        }
        else if (__dummyScrutVar11.equals("hijri"))
        {
            c = CalendarEnum.Hijri;
        }
        else if (__dummyScrutVar11.equals("japanese"))
        {
            c = CalendarEnum.Japanese;
        }
        else if (__dummyScrutVar11.equals("korea"))
        {
            c = CalendarEnum.Korea;
        }
        else if (__dummyScrutVar11.equals("taiwan"))
        {
            c = CalendarEnum.Taiwan;
        }
        else if (__dummyScrutVar11.equals("thaibuddhist"))
        {
            c = CalendarEnum.ThaiBuddhist;
        }
        else
        {
            c = def;
        }            
        return c;
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


