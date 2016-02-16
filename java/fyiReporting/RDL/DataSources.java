//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSource;
import fyiReporting.RDL.DataSourceDefn;
import fyiReporting.RDL.DataSourcesDefn;

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
* Contains list of DataSource about how to connect to sources of data used by the DataSets.
*/
public class DataSources   
{
    fyiReporting.RDL.Report _rpt;
    // Runtime report
    ListDictionary _Items = new ListDictionary();
    // list of report items
    public DataSources(fyiReporting.RDL.Report rpt, DataSourcesDefn dsds) throws Exception {
        _rpt = rpt;
        _Items = new ListDictionary();
        for (Object __dummyForeachVar0 : dsds.getItems().Values)
        {
            // Loop thru all the child nodes
            DataSourceDefn dsd = (DataSourceDefn)__dummyForeachVar0;
            DataSource ds = new DataSource(rpt,dsd);
            _Items.Add(dsd.getName().getNm(), ds);
        }
    }

    public DataSource get___idx(String name) throws Exception {
        return _Items[name] instanceof DataSource ? (DataSource)_Items[name] : (DataSource)null;
    }

}


