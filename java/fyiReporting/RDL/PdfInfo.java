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
* Store information about the document,Title, Author, Company,
*/
public class PdfInfo  extends PdfBase 
{
    private String info = new String();
    public PdfInfo(PdfAnchor pa) throws Exception {
        super(pa);
        info = null;
    }

    /**
    * Fill the Info Dict
    */
    public void setInfo(String title, String author, String subject, String company) throws Exception {
        info = String.Format("\r\n{0} 0 obj<</ModDate({1})/CreationDate({1})/Title({2})/Creator(fyiReporting Software, LLC)" + "/Author({3})/Subject ({4})/Producer(fyiReporting Software, LLC)/Company({5})>>\tendobj\t", this.objectNum, getDateTime(), title == null ? "" : title, author == null ? "" : author, subject == null ? "" : subject, company == null ? "" : company);
    }

    /**
    * Get the Document Information Dictionary
    * 
    *  @return
    */
    public byte[] getInfoDict(long filePos, RefSupport<int> size) throws Exception {
        RefSupport<int> refVar___0 = new RefSupport<int>();
        resVar___0 = getUTF8Bytes(info,filePos,refVar___0);
        size.setValue(refVar___0.getValue());
        return resVar___0;
    }

    /**
    * Get Date as Adobe needs ie similar to ISO/IEC 8824 format
    * 
    *  @return
    */
    private String getDateTime() throws Exception {
        DateTime universalDate = DateTime.UtcNow;
        DateTime localDate = DateTime.Now;
        String pdfDate = String.Format("D:{0:yyyyMMddhhmmss}", localDate);
        TimeSpan diff = localDate.Subtract(universalDate);
        int uHour = diff.Hours;
        int uMinute = diff.Minutes;
        char sign = '+';
        if (uHour < 0)
            sign = '-';
         
        uHour = Math.Abs(uHour);
        pdfDate += String.Format("{0}{1}'{2}'", sign, uHour.ToString().PadLeft(2, '0'), uMinute.ToString().PadLeft(2, '0'));
        return pdfDate;
    }

}


