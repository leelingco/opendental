//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataSourceDefn;
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
* Contains list of DataSource about how to connect to sources of data used by the DataSets.
*/
public class DataSourcesDefn  extends ReportLink 
{
    ListDictionary _Items = new ListDictionary();
    // list of report items
    public DataSourcesDefn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        // Run thru the attributes
        //			foreach(XmlAttribute xAttr in xNode.Attributes)
        //			{
        //			}
        _Items = new ListDictionary();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (StringSupport.equals(xNodeLoop.Name, "DataSource"))
            {
                DataSourceDefn ds = new DataSourceDefn(r,this,xNodeLoop);
                if (ds.getName() != null)
                    _Items.Add(ds.getName().getNm(), ds);
                 
            }
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For DataSources at least one DataSource is required.");
         
    }

    public DataSourceDefn get___idx(String name) throws Exception {
        return _Items[name] instanceof DataSourceDefn ? (DataSourceDefn)_Items[name] : (DataSourceDefn)null;
    }

    public void cleanUp(fyiReporting.RDL.Report rpt) throws Exception {
        for (Object __dummyForeachVar1 : _Items.Values)
        {
            // closes any connections
            DataSourceDefn ds = (DataSourceDefn)__dummyForeachVar1;
            ds.cleanUp(rpt);
        }
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar2 : _Items.Values)
        {
            DataSourceDefn ds = (DataSourceDefn)__dummyForeachVar2;
            ds.finalPass();
        }
        return ;
    }

    public boolean connectDataSources(fyiReporting.RDL.Report rpt) throws Exception {
        // Handle any parent connections if any	(ie we're in a subreport and want to use parent report connections
        if (rpt.getParentConnections() != null && rpt.getParentConnections().getItems() != null)
        {
            for (Object __dummyForeachVar4 : _Items.Values)
            {
                // we treat subreport merged transaction connections as set by the User
                DataSourceDefn ds = (DataSourceDefn)__dummyForeachVar4;
                for (Object __dummyForeachVar3 : rpt.getParentConnections().getItems().Values)
                {
                    DataSourceDefn dsp = (DataSourceDefn)__dummyForeachVar3;
                    if (ds.areSameDataSource(dsp))
                    {
                        ds.setUserConnection(rpt,dsp.getConnection(rpt));
                        break;
                    }
                     
                }
            }
        }
         
        for (Object __dummyForeachVar5 : _Items.Values)
        {
            DataSourceDefn ds = (DataSourceDefn)__dummyForeachVar5;
            ds.connectDataSource(rpt);
        }
        return true;
    }

    public ListDictionary getItems() throws Exception {
        return _Items;
    }

}


