//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.Visibility;

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
* TableGroup definition and processing.
*/
public class TableGroup  extends ReportLink 
{
    Grouping _Grouping;
    // The expressions to group the data by.
    Sorting _Sorting;
    // The expressions to sort the data by.
    Header _Header;
    // A group header row.
    Footer _Footer;
    // A group footer row.
    Visibility _Visibility;
    // Indicates if the group (and all groups embedded
    // within it) should be hidden.
    Textbox _ToggleTextbox;
    //  resolved TextBox for toggling visibility
    public TableGroup(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Grouping = null;
        _Sorting = null;
        _Header = null;
        _Footer = null;
        _Visibility = null;
        _ToggleTextbox = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Grouping"))
            {
                _Grouping = new Grouping(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Sorting"))
            {
                _Sorting = new Sorting(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Header"))
            {
                _Header = new Header(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Footer"))
            {
                _Footer = new Footer(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Visibility"))
            {
                _Visibility = new Visibility(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableGroup element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
        if (_Grouping == null)
            OwnerReport.rl.logError(8,"TableGroup requires the Grouping element.");
         
    }

    public void finalPass() throws Exception {
        if (_Grouping != null)
            _Grouping.finalPass();
         
        if (_Sorting != null)
            _Sorting.finalPass();
         
        if (_Header != null)
            _Header.finalPass();
         
        if (_Footer != null)
            _Footer.finalPass();
         
        if (_Visibility != null)
        {
            _Visibility.finalPass();
            if (_Visibility.getToggleItem() != null)
            {
                _ToggleTextbox = (Textbox)(OwnerReport.getLUReportItems()[_Visibility.getToggleItem()]);
                if (_ToggleTextbox != null)
                    _ToggleTextbox.setIsToggle(true);
                 
            }
             
        }
         
        return ;
    }

    public float defnHeight() throws Exception {
        float height = 0;
        if (_Header != null)
            height += _Header.getTableRows().defnHeight();
         
        if (_Footer != null)
            height += _Footer.getTableRows().defnHeight();
         
        return height;
    }

    public Grouping getGrouping() throws Exception {
        return _Grouping;
    }

    public void setGrouping(Grouping value) throws Exception {
        _Grouping = value;
    }

    public Sorting getSorting() throws Exception {
        return _Sorting;
    }

    public void setSorting(Sorting value) throws Exception {
        _Sorting = value;
    }

    public Header getHeader() throws Exception {
        return _Header;
    }

    public void setHeader(Header value) throws Exception {
        _Header = value;
    }

    public int getHeaderCount() throws Exception {
        if (_Header == null)
            return 0;
        else
            return _Header.getTableRows().getItems().Count; 
    }

    public Footer getFooter() throws Exception {
        return _Footer;
    }

    public void setFooter(Footer value) throws Exception {
        _Footer = value;
    }

    public int getFooterCount() throws Exception {
        if (_Footer == null)
            return 0;
        else
            return _Footer.getTableRows().getItems().Count; 
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

    public Textbox getToggleTextbox() throws Exception {
        return _ToggleTextbox;
    }

}


