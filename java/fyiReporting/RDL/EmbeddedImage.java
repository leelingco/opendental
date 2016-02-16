//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Name;
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
* The defintion of an embedded images, including the actual image data and type.
*/
public class EmbeddedImage  extends ReportLink 
{
    Name _Name;
    // Name of the image.
    String _MIMEType = new String();
    // The MIMEType for the image. Valid values are:
    // image/bmp, image/jpeg, image/gif, image/png, image/xpng.
    String _ImageData = new String();
    // Base-64 encoded image data.
    public EmbeddedImage(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _MIMEType = null;
        _ImageData = null;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("MIMEType"))
            {
                _MIMEType = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("ImageData"))
            {
                _ImageData = xNodeLoop.InnerText;
            }
            else
            {
                this.OwnerReport.rl.logError(4,"Unknown Report element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if (this.getName() == null)
        {
            OwnerReport.rl.logError(8,"EmbeddedImage Name is required but not specified.");
        }
        else
        {
            try
            {
                OwnerReport.getLUEmbeddedImages().Add(this.getName().getNm(), this);
            }
            catch (Exception __dummyCatchVar0)
            {
                // add to referenceable embedded images
                // Duplicate name
                OwnerReport.rl.logError(4,"Duplicate EmbeddedImage  name '" + this.getName().getNm() + "' ignored.");
            }
        
        } 
        if (_MIMEType == null)
            OwnerReport.rl.logError(8,"EmbeddedImage MIMEType is required but not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        if (_ImageData == null)
            OwnerReport.rl.logError(8,"EmbeddedImage ImageData is required but not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
    }

    public void finalPass() throws Exception {
        return ;
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public String getMIMEType() throws Exception {
        return _MIMEType;
    }

    public void setMIMEType(String value) throws Exception {
        _MIMEType = value;
    }

    public String getImageData() throws Exception {
        return _ImageData;
    }

    public void setImageData(String value) throws Exception {
        _ImageData = value;
    }

}


