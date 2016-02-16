//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.ObjectList;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfImageEntry;
import fyiReporting.RDL.PdfPage;
import fyiReporting.RDL.PdfUtility;

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
public class PdfImages   
{
    PdfAnchor pa;
    Hashtable images = new Hashtable();
    public PdfImages(PdfAnchor a) throws Exception {
        pa = a;
        images = new Hashtable();
    }

    public Hashtable getImages() throws Exception {
        return images;
    }

    public String getPdfImage(PdfPage p, String imgname, int contentRef, ImageFormat imf, byte[] ba, int width, int height) throws Exception {
        PdfImageEntry ie;
        if (imgname != null)
        {
            ie = (PdfImageEntry)images[imgname];
            if (ie != null)
            {
                p.addResource(ie,contentRef);
                return ie.name;
            }
             
        }
        else
            imgname = "I" + (images.Count + 1).ToString(); 
        ie = new PdfImageEntry(pa, p, contentRef, imgname, imf, ba, width, height);
        images.Add(imgname, ie);
        return ie.name;
    }

    /**
    * Gets the image entries to be written to the file
    * 
    *  @return
    */
    public byte[] getImageDict(long filePos, RefSupport<int> size) throws Exception {
        MemoryStream ms = new MemoryStream();
        int s = new int();
        byte[] ba = new byte[]();
        for (Object __dummyForeachVar0 : images.Values)
        {
            PdfImageEntry ie = (PdfImageEntry)__dummyForeachVar0;
            ObjectList objList = new ObjectList(ie.objectNum,filePos);
            RefSupport<int> refVar___0 = new RefSupport<int>();
            ba = PdfUtility.getUTF8Bytes(ie.imgDict,refVar___0);
            s = refVar___0.getValue();
            ms.Write(ba, 0, ba.Length);
            filePos += s;
            ms.Write(ie.ba, 0, ie.ba.Length);
            // write out the image
            filePos += ie.ba.Length;
            RefSupport<int> refVar___1 = new RefSupport<int>();
            ba = PdfUtility.getUTF8Bytes("endstream\r\nendobj\r\n",refVar___1);
            s = refVar___1.getValue();
            ms.Write(ba, 0, ba.Length);
            filePos += s;
            ie.xref.offsets.Add(objList);
        }
        ba = ms.ToArray();
        size.setValue(ba.Length);
        return ba;
    }

}


