//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Drillthrough;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;

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
* Action definition and processing.
*/
public class Action  extends ReportLink 
{
    Expression _Hyperlink;
    // (URL)An expression that evaluates to the URL of the hyperlink
    Drillthrough _Drillthrough;
    // The drillthrough report that should be executed
    // by clicking on the hyperlink
    Expression _BookmarkLink;
    // (string)
    //An expression that evaluates to the ID of a
    //bookmark within the report to go to when this
    //report item is clicked on.
    //(If no bookmark with this ID is found, the link
    //will not be included in the report. If the
    //bookmark is hidden, the link will go to the start
    //of the page the bookmark is on. If multiple
    //bookmarks with this ID are found, the link will
    //go to the first one)
    // Constructor
    public Action(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Hyperlink = null;
        _Drillthrough = null;
        _BookmarkLink = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Hyperlink"))
            {
                _Hyperlink = new Expression(r,this,xNodeLoop,ExpressionType.URL);
            }
            else if (__dummyScrutVar0.equals("Drillthrough"))
            {
                _Drillthrough = new Drillthrough(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("BookmarkLink"))
            {
                _BookmarkLink = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else
            {
            }   
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Hyperlink != null)
            _Hyperlink.finalPass();
         
        if (_Drillthrough != null)
            _Drillthrough.finalPass();
         
        if (_BookmarkLink != null)
            _BookmarkLink.finalPass();
         
        return ;
    }

    public Expression getHyperlink() throws Exception {
        return _Hyperlink;
    }

    public void setHyperlink(Expression value) throws Exception {
        _Hyperlink = value;
    }

    public String hyperLinkValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Hyperlink == null)
            return null;
         
        return _Hyperlink.evaluateString(rpt,r);
    }

    public Drillthrough getDrill() throws Exception {
        return _Drillthrough;
    }

    public void setDrill(Drillthrough value) throws Exception {
        _Drillthrough = value;
    }

    public Expression getBookmarkLink() throws Exception {
        return _BookmarkLink;
    }

    public void setBookmarkLink(Expression value) throws Exception {
        _BookmarkLink = value;
    }

    public String bookmarkLinkValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_BookmarkLink == null)
            return null;
         
        return _BookmarkLink.evaluateString(rpt,r);
    }

}


