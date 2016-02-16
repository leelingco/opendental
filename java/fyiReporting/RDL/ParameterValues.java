//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.ParameterValue;
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
* Collection of parameter values.
*/
public class ParameterValues  extends ReportLink 
{
    List<ParameterValue> _Items = new List<ParameterValue>();
    // list of ParameterValue
    public ParameterValues(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        ParameterValue pv;
        _Items = new List<ParameterValue>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("ParameterValue"))
            {
                pv = new ParameterValue(r,this,xNodeLoop);
            }
            else
            {
                pv = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ParameterValues element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (pv != null)
                _Items.Add(pv);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For ParameterValues at least one ParameterValue is required.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            ParameterValue pv = (ParameterValue)__dummyForeachVar1;
            pv.finalPass();
        }
        return ;
    }

    public List<ParameterValue> getItems() throws Exception {
        return _Items;
    }

    public void supplyValues(fyiReporting.RDL.Report rpt, RefSupport<String[]> displayValues, RefSupport<Object[]> dataValues) throws Exception {
        displayValues.setValue(new String[_Items.Count]);
        dataValues.setValue(new Object[_Items.Count]);
        int index = 0;
        for (Object __dummyForeachVar2 : _Items)
        {
            // go thru the parameters extracting the data values
            ParameterValue pv = (ParameterValue)__dummyForeachVar2;
            if (pv.getValue() == null)
                dataValues.getValue()[index] = null;
            else
                dataValues.getValue()[index] = pv.getValue().evaluate(rpt,null); 
            if (pv.getLabel() == null)
            {
                // if label is null use the data value; if not provided use ""
                if (dataValues.getValue()[index] == null)
                    displayValues.getValue()[index] = "";
                else
                    displayValues.getValue()[index] = dataValues.getValue()[index].ToString(); 
            }
            else
            {
                displayValues.getValue()[index] = pv.getLabel().evaluateString(rpt,null);
                if (displayValues.getValue()[index] == null)
                    displayValues.getValue()[index] = "";
                 
            } 
            index++;
        }
        return ;
    }

}


