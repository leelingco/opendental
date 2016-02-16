//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfFontEntry;
import fyiReporting.RDL.StyleInfo;

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
* Represents the font dictionary used in a pdf page
*/
public class PdfFonts   
{
    PdfAnchor pa;
    Hashtable fonts = new Hashtable();
    public PdfFonts(PdfAnchor a) throws Exception {
        pa = a;
        fonts = new Hashtable();
    }

    public Hashtable getFonts() throws Exception {
        return fonts;
    }

    public String getPdfFont(String facename) throws Exception {
        PdfFontEntry fe = (PdfFontEntry)fonts[facename];
        if (fe != null)
            return fe.font;
         
        String name = "F" + (fonts.Count + 1).ToString();
        fe = new PdfFontEntry(pa,name,facename);
        fonts.Add(facename, fe);
        return fe.font;
    }

    public String getPdfFont(StyleInfo si) throws Exception {
        String face = fontNameNormalize(si.FontFamily);
        if (StringSupport.equals(face, "Times-Roman") && (si.isFontBold() || si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic))
            face = "Times";
         
        if (si.isFontBold() && si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic)
            // bold and italic?
            face = face + "-BoldOblique";
        else if (si.isFontBold())
            // just bold?
            face = face + "-Bold";
        else if (si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic)
            face = face + "-Oblique";
           
        return getPdfFont(face);
    }

    public String fontNameNormalize(String face) throws Exception {
        String faceName = new String();
        System.String.APPLY __dummyScrutVar0 = face.ToLower();
        if (__dummyScrutVar0.equals("times-roman") || __dummyScrutVar0.equals("timesnewroman") || __dummyScrutVar0.equals("times new roman") || __dummyScrutVar0.equals("timesnewromanps") || __dummyScrutVar0.equals("timesnewromanpsmt") || __dummyScrutVar0.equals("serif"))
        {
            faceName = "Times-Roman";
        }
        else if (__dummyScrutVar0.equals("courier") || __dummyScrutVar0.equals("couriernew") || __dummyScrutVar0.equals("courier new") || __dummyScrutVar0.equals("couriernewpsmt") || __dummyScrutVar0.equals("monospace"))
        {
            faceName = "Courier";
        }
        else if (__dummyScrutVar0.equals("symbol"))
        {
            faceName = "Symbol";
        }
        else if (__dummyScrutVar0.equals("zapfdingbats"))
        {
            faceName = "ZapfDingbats";
        }
        else
        {
            faceName = "Helvetica";
        }    
        return faceName;
    }

    /**
    * Gets the font entries to be written to the file
    * 
    *  @return
    */
    public byte[] getFontDict(long filePos, RefSupport<int> size) throws Exception {
        MemoryStream ms = new MemoryStream();
        int s = new int();
        byte[] ba = new byte[]();
        for (Object __dummyForeachVar0 : fonts.Values)
        {
            PdfFontEntry fe = (PdfFontEntry)__dummyForeachVar0;
            RefSupport<int> refVar___0 = new RefSupport<int>();
            ba = fe.getUTF8Bytes(fe.fontDict,filePos,refVar___0);
            s = refVar___0.getValue();
            filePos += s;
            ms.Write(ba, 0, ba.Length);
        }
        ba = ms.ToArray();
        size.setValue(ba.Length);
        return ba;
    }

}


