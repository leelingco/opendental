//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.ReportParameter;

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
* Collection of report parameters.
*/
public class ReportParameters  extends ReportLink implements ICollection
{
    IDictionary _Items = new IDictionary();
    // list of report items
    public ReportParameters(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
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
             
            if (StringSupport.equals(xNodeLoop.Name, "ReportParameter"))
            {
                ReportParameter rp = new ReportParameter(r,this,xNodeLoop);
                if (rp.getName() != null)
                    _Items.Add(rp.getName().getNm(), rp);
                 
            }
            else
                OwnerReport.rl.logError(4,"Unknown ReportParameters element '" + xNodeLoop.Name + "' ignored."); 
        }
    }

    public void setRuntimeValues(fyiReporting.RDL.Report rpt, IDictionary parms) throws Exception {
        for (Object __dummyForeachVar1 : parms.Keys)
        {
            // Fill the values to use in the report parameters
            String pname = (String)__dummyForeachVar1;
            // Loop thru the passed parameters
            ReportParameter rp = (ReportParameter)_Items[pname];
            if (rp == null)
            {
                // When not found treat it as a warning message
                if (!pname.StartsWith("rs:"))
                    // don't care about report server parameters
                    rpt.rl.logError(4,"Unknown ReportParameter passed '" + pname + "' ignored.");
                 
                continue;
            }
             
            rp.setRuntimeValue(rpt,parms[pname]);
        }
        return ;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar2 : _Items.Values)
        {
            ReportParameter rp = (ReportParameter)__dummyForeachVar2;
            rp.finalPass();
        }
        return ;
    }

    public IDictionary getItems() throws Exception {
        return _Items;
    }

    public boolean getIsSynchronized() throws Exception {
        return _Items.IsSynchronized;
    }

    public int getCount() throws Exception {
        return _Items.Count;
    }

    public void copyTo(Array array, int index) throws Exception {
        _Items.CopyTo(array, index);
    }

    public Object getSyncRoot() throws Exception {
        return _Items.SyncRoot;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _Items.Values.GetEnumerator();
    }

}


