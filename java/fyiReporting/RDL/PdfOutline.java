//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfOutlineEntry;
import fyiReporting.RDL.PdfOutlineMain;

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
* Represents the outline dictionary
*/
public class PdfOutline   
{
    PdfAnchor _pa;
    List<PdfOutlineEntry> bookmarks = new List<PdfOutlineEntry>();
    // lists of outline entries (ie bookmarks)
    PdfOutlineMain _pom = null;
    public PdfOutline(PdfAnchor pa) throws Exception {
        _pa = pa;
        bookmarks = new List<PdfOutlineEntry>();
    }

    public List<PdfOutlineEntry> getBookmarks() throws Exception {
        return bookmarks;
    }

    public int getObjectNumber() throws Exception {
        if (this.bookmarks.Count == 0)
            return -1;
         
        _pom = new PdfOutlineMain(_pa);
        return _pom.objectNum;
    }

    /**
    * Gets the outline entries to be written to the file
    * 
    *  @return
    */
    public byte[] getOutlineDict(long filePos, RefSupport<int> size) throws Exception {
        MemoryStream ms = new MemoryStream();
        int s = new int();
        byte[] ba = new byte[]();
        //23 0 obj
        //<</Type /Outlines /First 13 0 R
        /**
        * Last 13 0 R>>
        */
        //endobj
        if (_pom == null)
            throw new Exception("GetObjectNumber must be called before GetOutlineDict");
         
        String content = String.Format("\r\n{0} 0 obj<</Type /Outlines /First {1} 0 R\r/Last {2} 0 R\r/Count {3}>>\rendobj\r", _pom.objectNum, bookmarks[0].objectNum, bookmarks[bookmarks.Count - 1].objectNum, bookmarks.Count);
        RefSupport<int> refVar___0 = new RefSupport<int>();
        ba = _pom.getUTF8Bytes(content,filePos,refVar___0);
        s = refVar___0.getValue();
        ms.Write(ba, 0, ba.Length);
        filePos += s;
        String parent = String.Format("\r/Parent {0} 0 R", _pom.objectNum);
        PdfOutlineEntry coe;
        // current outline entry
        PdfOutlineEntry poe;
        // previous outline entry
        PdfOutlineEntry noe;
        for (int i = 0;i < bookmarks.Count;i++)
        {
            // previous outline entry
            coe = bookmarks[i];
            poe = i > 0 ? bookmarks[i - 1] : null;
            noe = i + 1 == bookmarks.Count ? null : bookmarks[i + 1];
            StringBuilder sb = new StringBuilder();
            // <</Title (Yahoo! News: Business)
            String newtext = coe.getText().Replace("\\", "\\\\");
            newtext = newtext.Replace("(", "\\(");
            newtext = newtext.Replace(")", "\\)");
            newtext = newtext.Replace('\r', ' ');
            newtext = newtext.Replace('\n', ' ');
            sb.AppendFormat("\r\n{0} 0 obj<</Title ({1})", coe.objectNum, newtext);
            // /Parent 23 0 R
            sb.Append(parent);
            // /Prev 14 0 R
            if (poe != null)
                sb.AppendFormat("\r/Prev {0} 0 R", poe.objectNum);
             
            // /Next 16 0 R
            if (noe != null)
                sb.AppendFormat("\r/Next {0} 0 R", noe.objectNum);
             
            // /Dest [3 0 R /XYZ 0 400.86 null]
            sb.AppendFormat("\r/Dest [{0} 0 R /XYZ {1} {2} null]", coe.getP(), coe.getX(), coe.getY());
            sb.Append("\r>>\rendobj\r");
            RefSupport<int> refVar___1 = new RefSupport<int>();
            ba = coe.GetUTF8Bytes(sb.ToString(), filePos, refVar___1);
            s = refVar___1.getValue();
            ms.Write(ba, 0, ba.Length);
            filePos += s;
        }
        ba = ms.ToArray();
        size.setValue(ba.Length);
        return ba;
    }

}


