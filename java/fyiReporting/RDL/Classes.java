//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.ReportClass;
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
* Contains information about which classes to instantiate during report initialization.
* These instances can then be used in expressions throughout the report.
*/
public class Classes  extends ReportLink implements IEnumerable
{
    List<ReportClass> _Items = new List<ReportClass>();
    // list of report class
    public Classes(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Items = new List<ReportClass>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (StringSupport.equals(xNodeLoop.Name, "Class"))
            {
                ReportClass rc = new ReportClass(r,this,xNodeLoop);
                _Items.Add(rc);
            }
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For Classes at least one Class is required.");
        else
            _Items.TrimExcess(); 
    }

    public ReportClass get___idx(String s) throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            ReportClass rc = (ReportClass)__dummyForeachVar1;
            if (StringSupport.equals(rc.getInstanceName().getNm(), s))
                return rc;
             
        }
        return null;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            ReportClass rc = (ReportClass)__dummyForeachVar2;
            rc.finalPass();
        }
        return ;
    }

    public void load(fyiReporting.RDL.Report rpt) throws Exception {
        for (Object __dummyForeachVar3 : _Items)
        {
            ReportClass rc = (ReportClass)__dummyForeachVar3;
            rc.load(rpt);
        }
        return ;
    }

    public List<ReportClass> getItems() throws Exception {
        return _Items;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _Items.GetEnumerator();
    }

}

