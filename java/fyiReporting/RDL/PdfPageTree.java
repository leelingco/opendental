//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;

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
* The PageTree object contains references to all the pages used within the Pdf.
* All individual pages are referenced through the kids string
*/
public class PdfPageTree  extends PdfBase 
{
    private String pageTree = new String();
    private String kids = new String();
    private int MaxPages = new int();
    public PdfPageTree(PdfAnchor pa) throws Exception {
        super(pa);
        kids = "[ ";
        MaxPages = 0;
    }

    /**
    * Add a page to the Page Tree. ObjNum is the object number of the page to be added.
    * pageNum is the page number of the page.
    * 
    *  @param objNum
    */
    public void addPage(int objNum) throws Exception {
        Debug.Assert(objNum >= 0 && objNum <= this.getCurrent());
        MaxPages++;
        String refPage = objNum + " 0 R ";
        kids = kids + refPage;
    }

    /**
    * returns the Page Tree Dictionary
    * 
    *  @return
    */
    public byte[] getPageTree(long filePos, RefSupport<int> size) throws Exception {
        pageTree = String.Format("\r\n{0} 0 obj<</Count {1}/Kids {2}]>> endobj\t", this.objectNum, MaxPages, kids);
        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = this.getUTF8Bytes(pageTree,filePos,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

}


