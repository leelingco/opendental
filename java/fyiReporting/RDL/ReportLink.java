//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
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
* Linking mechanism defining the tree of the report.
*/
abstract public class ReportLink   
{
    public ReportDefn OwnerReport;
    // Main Report instance
    public ReportLink Parent;
    // Parent instance
    public int ObjectNumber = new int();
    public ReportLink(ReportDefn r, ReportLink p) throws Exception {
        OwnerReport = r;
        Parent = p;
        ObjectNumber = r.getObjectNumber();
    }

    // Give opportunity for report elements to do additional work
    //   e.g.  expressions should be parsed at this point
    abstract public void finalPass() throws Exception ;

    public boolean inPageHeaderOrFooter() throws Exception {
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof PageHeader || rl instanceof PageFooter)
                return true;
             
        }
        return false;
    }

}


