//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSet;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.DataSetsDefn;

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
* The sets of data (defined by DataSet) that are retrieved as part of the Report.
*/
public class DataSets   
{
    fyiReporting.RDL.Report _rpt;
    // runtime report
    IDictionary _Items = new IDictionary();
    // list of report items
    public DataSets(fyiReporting.RDL.Report rpt, DataSetsDefn dsn) throws Exception {
        _rpt = rpt;
        if (dsn.getItems().Count < 10)
            _Items = new ListDictionary();
        else
            // Hashtable is overkill for small lists
            _Items = new Hashtable(dsn.getItems().Count); 
        for (Object __dummyForeachVar0 : dsn.getItems().Values)
        {
            // Loop thru all the child nodes
            DataSetDefn dsd = (DataSetDefn)__dummyForeachVar0;
            DataSet ds = new DataSet(rpt,dsd);
            _Items.Add(dsd.getName().getNm(), ds);
        }
    }

    public DataSet get___idx(String name) throws Exception {
        return _Items[name] instanceof DataSet ? (DataSet)_Items[name] : (DataSet)null;
    }

}


