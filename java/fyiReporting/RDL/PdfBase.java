//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.ObjectList;
import fyiReporting.RDL.PdfAnchor;

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
* This is the base object for all objects used within the pdf.
*/
public class PdfBase   
{
    /**
    * Stores the Object Number
    */
    public int objectNum = new int();
    public PdfAnchor xref;
    /**
    * Constructor increments the object number to
    * reflect the currently used object number
    */
    protected PdfBase(PdfAnchor pa) throws Exception {
        xref = pa;
        xref.current++;
        objectNum = xref.current;
    }

    public int getCurrent() throws Exception {
        return xref.current;
    }

    /**
    * Convert the unicode string 16 bits to unicode bytes.
    * This is written to the file to create Pdf
    * 
    *  @return
    */
    public byte[] getUTF8Bytes(String str, long filePos, RefSupport<int> size) throws Exception {
        ObjectList objList = new ObjectList(objectNum,filePos);
        byte[] abuf = new byte[]();
        byte[] ubuf = Encoding.Unicode.GetBytes(str);
        Encoding enc = Encoding.GetEncoding(1252);
        abuf = Encoding.Convert(Encoding.Unicode, enc, ubuf);
        size.setValue(abuf.Length);
        xref.offsets.Add(objList);
        return abuf;
    }

}


