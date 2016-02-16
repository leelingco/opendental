//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

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
* The Custom element allows report design tools to pass information to report output components.
* This element may contain any valid XML. The engine will simply pass the contents of Custom
* unchanged. Client applications using the Custom element are recommended to place their
* custom properties under their own single subelement of Custom, defining a namespace for that
* node.
* Example:
* True .... 
* The HTML renderer uses this information to generate JavaScript to allow
* user sorting of the table in the browser.
*/
public class Custom  extends ReportLink 
{
    //The Custom element allows report design tools to pass information to report output components.
    //This element may contain any valid XML. The engine will simply pass the contents of Custom
    //unchanged. Client applications using the Custom element are recommended to place their
    //custom properties under their own single subelement of Custom, defining a namespace for that
    //node.
    //  Example:
    //   <Table><Custom><HTML><SortAble>True</SortAble></HTML> .... </Table>
    //     The HTML renderer uses this information to generate JavaScript to allow
    //     user sorting of the table in the browser.
    String _XML = new String();
    // custom information for report.
    XmlDocument _CustomDoc = new XmlDocument();
    // XML document just for Custom subtree
    public Custom(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _XML = xNode.OuterXml;
        // this includes the "Custom" tag at the top level
        // Put the subtree into its own document
        XmlDocument doc = new XmlDocument();
        doc.PreserveWhitespace = false;
        doc.LoadXml(_XML);
        _CustomDoc = doc;
    }

    public void finalPass() throws Exception {
        return ;
    }

    public String getXML() throws Exception {
        return _XML;
    }

    public void setXML(String value) throws Exception {
        _XML = value;
    }

    public XmlNode getCustomXmlNode() throws Exception {
        XmlNode xNode = new XmlNode();
        xNode = _CustomDoc.LastChild;
        return xNode;
    }

}


