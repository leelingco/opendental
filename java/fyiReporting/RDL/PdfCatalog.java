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
* Models the Catalog dictionary within a pdf file. This is the first created object.
* It contains references to all other objects within the List of Pdf Objects.
*/
public class PdfCatalog  extends PdfBase 
{
    private String catalog = new String();
    private String lang = new String();
    public PdfCatalog(PdfAnchor pa, String l) throws Exception {
        super(pa);
        if (l != null)
            lang = String.Format("/Lang({0})", l);
        else
            lang = ""; 
    }

    /**
    * Returns the Catalog Dictionary
    * 
    *  @return
    */
    public byte[] getCatalogDict(int outline, int refPageTree, long filePos, RefSupport<int> size) throws Exception {
        Debug.Assert(refPageTree >= 1);
        if (outline >= 0)
            catalog = String.Format("\r\n{0} 0 obj<</Type /Catalog{2}/Pages {1} 0 R /Outlines {3} 0 R>>\tendobj\t", this.objectNum, refPageTree, lang, outline);
        else
            catalog = String.Format("\r\n{0} 0 obj<</Type /Catalog{2}/Pages {1} 0 R>>\tendobj\t", this.objectNum, refPageTree, lang); 
        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = this.getUTF8Bytes(catalog,filePos,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

}


