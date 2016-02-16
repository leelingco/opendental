//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.EmbeddedImage;
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
* The collection of embedded images in the Report.
*/
public class EmbeddedImages  extends ReportLink 
{
    List<EmbeddedImage> _Items = new List<EmbeddedImage>();
    // list of EmbeddedImage
    public EmbeddedImages(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Items = new List<EmbeddedImage>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (StringSupport.equals(xNodeLoop.Name, "EmbeddedImage"))
            {
                EmbeddedImage ei = new EmbeddedImage(r,this,xNodeLoop);
                _Items.Add(ei);
            }
            else
                this.OwnerReport.rl.logError(4,"Unknown Report element '" + xNodeLoop.Name + "' ignored."); 
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For EmbeddedImages at least one EmbeddedImage is required.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            EmbeddedImage ei = (EmbeddedImage)__dummyForeachVar1;
            ei.finalPass();
        }
        return ;
    }

    public List<EmbeddedImage> getItems() throws Exception {
        return _Items;
    }

}


