//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Field;
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
* Collection of fields for a DataSet.
*/
public class Fields  extends ReportLink implements ICollection
{
    IDictionary _Items = new IDictionary();
    // dictionary of items
    public Fields(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        Field f;
        if (xNode.ChildNodes.Count < 10)
            _Items = new ListDictionary();
        else
            // Hashtable is overkill for small lists
            _Items = new Hashtable(xNode.ChildNodes.Count); 
        // Loop thru all the child nodes
        int iCol = 0;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Field"))
            {
                f = new Field(r,this,xNodeLoop);
                f.setColumnNumber(iCol++);
            }
            else
            {
                // Assign the column number
                f = null;
                r.rl.logError(4,"Unknown element '" + xNodeLoop.Name + "' in fields list.");
            } 
            if (f != null)
            {
                if (_Items.Contains(f.getName().getNm()))
                {
                    r.rl.logError(4,"Field " + f.getName() + " has duplicates.");
                }
                else
                    _Items.Add(f.getName().getNm(), f); 
            }
             
        }
    }

    public Field get___idx(String s) throws Exception {
        return _Items[s] instanceof Field ? (Field)_Items[s] : (Field)null;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items.Values)
        {
            Field f = (Field)__dummyForeachVar1;
            f.finalPass();
        }
        return ;
    }

    public IDictionary getItems() throws Exception {
        return _Items;
    }

    public boolean getIsSynchronized() throws Exception {
        return _Items.Values.IsSynchronized;
    }

    public int getCount() throws Exception {
        return _Items.Values.Count;
    }

    public void copyTo(Array array, int index) throws Exception {
        _Items.Values.CopyTo(array, index);
    }

    public Object getSyncRoot() throws Exception {
        return _Items.Values.SyncRoot;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _Items.Values.GetEnumerator();
    }

}


