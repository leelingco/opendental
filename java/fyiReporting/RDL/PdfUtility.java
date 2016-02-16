//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
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
* This class contains general Utility for the creation of pdf
* Creates the Header
* Creates XrefTable
* Creates the Trailer
*/
public class PdfUtility   
{
    private int numTableEntries = new int();
    PdfAnchor pa;
    public PdfUtility(PdfAnchor p) throws Exception {
        pa = p;
        numTableEntries = 0;
    }

    /**
    * Creates the xref table using the byte offsets in the array.
    * 
    *  @return
    */
    public byte[] createXrefTable(long fileOffset, RefSupport<int> size) throws Exception {
        //Store the Offset of the Xref table for startxRef
        String table = null;
        try
        {
            ObjectList objList = new ObjectList(0,fileOffset);
            pa.offsets.Add(objList);
            pa.offsets.Sort();
            numTableEntries = (int)pa.offsets.Count;
            table = String.Format("\r\nxref {0} {1}\r\n0000000000 65535 f\r\n", 0, numTableEntries);
            for (int entries = 1;entries < numTableEntries;entries++)
            {
                ObjectList obj = pa.offsets[entries];
                table += obj.offset.ToString().PadLeft(10, '0');
                table += " 00000 n\r\n";
            }
        }
        catch (Exception e)
        {
            Exception error = new Exception(e.Message + " In Utility.CreateXrefTable()");
            throw error;
        }

        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = getUTF8Bytes(table,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

    /**
    * Returns the Header
    * 
    *  @param version 
    *  @param size 
    *  @return
    */
    public byte[] getHeader(String version, RefSupport<int> size) throws Exception {
        String header = String.Format("%PDF-{0}\r%{1}\r\n", version, "\x82\x82");
        RefSupport<int> refVar___1 = new RefSupport<int>();
        resVar___1 = getUTF8Bytes(header,refVar___1);
        size.setValue(refVar___1.getValue());
        return resVar___1;
    }

    /**
    * Creates the trailer and return the bytes array
    * 
    *  @return
    */
    public byte[] getTrailer(int refRoot, int refInfo, RefSupport<int> size) throws Exception {
        String trailer = null;
        String infoDict = new String();
        try
        {
            if (refInfo > 0)
            {
                infoDict = String.Format("/Info {0} 0 R", refInfo);
            }
            else
                infoDict = ""; 
            //The sorted array will be already sorted to contain the file offset at the zeroth position
            ObjectList objList = pa.offsets[0];
            trailer = String.Format("trailer\n<</Size {0}/Root {1} 0 R {2}" + ">>\r\nstartxref\r\n{3}\r\n%%EOF\r\n", numTableEntries, refRoot, infoDict, objList.offset);
            pa.reset();
        }
        catch (Exception e)
        {
            Exception error = new Exception(e.Message + " In Utility.GetTrailer()");
            throw error;
        }

        RefSupport<int> refVar___2 = new RefSupport<int>();
        resVar___2 = getUTF8Bytes(trailer,refVar___2);
        size.setValue(refVar___2.getValue());
        return resVar___2;
    }

    /**
    * Converts the string to byte array in utf 8 encoding
    * 
    *  @param str 
    *  @param size 
    *  @return
    */
    static public byte[] getUTF8Bytes(String str, RefSupport<int> size) throws Exception {
        try
        {
            byte[] ubuf = Encoding.Unicode.GetBytes(str);
            Encoding enc = Encoding.GetEncoding(1252);
            byte[] abuf = Encoding.Convert(Encoding.Unicode, enc, ubuf);
            size.setValue(abuf.Length);
            return abuf;
        }
        catch (Exception e)
        {
            Exception error = new Exception(e.Message + " In Utility.GetUTF8Bytes()");
            throw error;
        }
    
    }

}


