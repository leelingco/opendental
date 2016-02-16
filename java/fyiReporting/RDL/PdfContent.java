//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.CompressionConfig;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfBase;
import fyiReporting.RDL.PdfUtility;
import fyiReporting.RDL.RdlEngineConfig;

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
* Represents the general content stream in a Pdf Page.
* This is used only by the PageObjec
*/
public class PdfContent  extends PdfBase 
{
    private String content = new String();
    private String contentStream = new String();
    private boolean CanCompress = new boolean();
    public PdfContent(PdfAnchor pa) throws Exception {
        super(pa);
        CanCompress = pa.CanCompress;
        content = null;
        //			contentStream="%stream\r";
        contentStream = "";
    }

    /**
    * Set the Stream of this Content Dict.
    * Stream is taken from PdfElements Objects
    * 
    *  @param stream
    */
    public void setStream(String stream) throws Exception {
        if (stream == null)
            return ;
         
        contentStream += stream;
    }

    /**
    * Content object
    * 
    * Get the Content Dictionary
    */
    public byte[] getContentDict(long filePos, RefSupport<int> size) throws Exception {
        // When no compression
        if (!CanCompress)
        {
            content = String.Format("\r\n{0} 0 obj<</Length {1}>>stream\r{2}\rendstream\rendobj\r", this.objectNum, contentStream.Length, contentStream);
            RefSupport<int> refVar___0 = new RefSupport<int>();
            resVar___0 = getUTF8Bytes(content,filePos,refVar___0);
            size.setValue(refVar___0.getValue());
            return resVar___0;
        }
         
        // Try to use compression; could still fail in which case fall back to uncompressed
        Stream strm = null;
        MemoryStream cs = null;
        try
        {
            CompressionConfig cc = RdlEngineConfig.getCompression();
            cs = new MemoryStream();
            // this will contain the content stream
            if (cc != null)
                strm = cc.GetStream(cs);
             
            if (strm == null)
            {
                // can't compress string
                cs.Close();
                content = String.Format("\r\n{0} 0 obj<</Length {1}>>stream\r{2}\rendstream\rendobj\r", this.objectNum, contentStream.Length, contentStream);
                RefSupport<int> refVar___1 = new RefSupport<int>();
                resVar___1 = getUTF8Bytes(content,filePos,refVar___1);
                size.setValue(refVar___1.getValue());
                return resVar___1;
            }
             
            // Compress the contents
            int cssize = new int();
            RefSupport<int> refVar___2 = new RefSupport<int>();
            byte[] ca = PdfUtility.getUTF8Bytes(contentStream,refVar___2);
            cssize = refVar___2.getValue();
            strm.Write(ca, 0, cssize);
            strm.Flush();
            cc.callStreamFinish(strm);
            // Now output the PDF command
            MemoryStream ms = new MemoryStream();
            int s = new int();
            byte[] ba = new byte[]();
            // get the compressed data;  we need the lenght now
            cs.Position = 0;
            byte[] cmpData = cs.ToArray();
            // write the beginning portion of the PDF object
            String ws = String.Format("\r\n{0} 0 obj<< /Filter /FlateDecode /Length {1}>>stream\r", this.objectNum, cmpData.Length);
            RefSupport<int> refVar___3 = new RefSupport<int>();
            ba = getUTF8Bytes(ws,filePos,refVar___3);
            s = refVar___3.getValue();
            // this will also register the object
            ms.Write(ba, 0, ba.Length);
            filePos += s;
            // write the Compressed data
            ms.Write(cmpData, 0, cmpData.Length);
            filePos += ba.Length;
            // write the end portion of the PDF object
            RefSupport<int> refVar___4 = new RefSupport<int>();
            ba = PdfUtility.getUTF8Bytes("\rendstream\rendobj\r",refVar___4);
            s = refVar___4.getValue();
            ms.Write(ba, 0, ba.Length);
            filePos += s;
            // now the final output array
            ba = ms.ToArray();
            size.setValue(ba.Length);
            return ba;
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
        }
    }

}


