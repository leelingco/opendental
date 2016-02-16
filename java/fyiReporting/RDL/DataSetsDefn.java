//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataSetDefn;
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
* The sets of data (defined by DataSet) that are retrieved as part of the Report.
*/
public class DataSetsDefn  extends ReportLink 
{
    IDictionary _Items = new IDictionary();
    // list of report items
    public DataSetsDefn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        if (xNode.ChildNodes.Count < 10)
            _Items = new ListDictionary();
        else
            // Hashtable is overkill for small lists
            _Items = new Hashtable(xNode.ChildNodes.Count); 
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (StringSupport.equals(xNodeLoop.Name, "DataSet"))
            {
                DataSetDefn ds = new DataSetDefn(r,this,xNodeLoop);
                if (ds != null && ds.getName() != null)
                    _Items.Add(ds.getName().getNm(), ds);
                 
            }
             
        }
    }

    public DataSetDefn get___idx(String name) throws Exception {
        return _Items[name] instanceof DataSetDefn ? (DataSetDefn)_Items[name] : (DataSetDefn)null;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items.Values)
        {
            DataSetDefn ds = (DataSetDefn)__dummyForeachVar1;
            ds.finalPass();
        }
        return ;
    }

    public void getData(fyiReporting.RDL.Report rpt) throws Exception {
        for (Object __dummyForeachVar2 : _Items.Values)
        {
            DataSetDefn ds = (DataSetDefn)__dummyForeachVar2;
            ds.getData(rpt);
        }
        return ;
    }

    public IDictionary getItems() throws Exception {
        return _Items;
    }

}


