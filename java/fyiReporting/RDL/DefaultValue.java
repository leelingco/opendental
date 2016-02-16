//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.DataSetReference;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Values;

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
* The default value for a parameter.
*/
public class DefaultValue  extends ReportLink 
{
    // Only one of Values and DataSetReference can be specified.
    DataSetReference _DataSetReference;
    // The query to execute to obtain the default value(s) for the parameter.
    // The default is the first value of the ValueField.
    Values _Values;
    // The default values for the parameter
    public DefaultValue(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataSetReference = null;
        _Values = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataSetReference"))
            {
                _DataSetReference = new DataSetReference(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Values"))
            {
                _Values = new Values(r,this,xNodeLoop);
            }
            else
            {
            }  
        }
    }

    public void finalPass() throws Exception {
        if (_DataSetReference != null)
            _DataSetReference.finalPass();
         
        if (_Values != null)
            _Values.finalPass();
         
        return ;
    }

    public DataSetReference getDataSetReference() throws Exception {
        return _DataSetReference;
    }

    public void setDataSetReference(DataSetReference value) throws Exception {
        _DataSetReference = value;
    }

    public Object[] getValue(fyiReporting.RDL.Report rpt) throws Exception {
        if (_Values != null)
            return valuesCalc(rpt);
         
        Object[] dValues = this.getDataValues(rpt);
        if (dValues != null)
            return dValues;
         
        String[] dsValues = new String[]();
        if (_DataSetReference != null)
        {
            RefSupport<String[]> refVar___0 = new RefSupport<String[]>();
            RefSupport<Object[]> refVar___1 = new RefSupport<Object[]>();
            _DataSetReference.SupplyValues(rpt, refVar___0, refVar___1);
            dsValues = refVar___0.getValue();
            dValues = refVar___1.getValue();
        }
         
        this.SetDataValues(rpt, dValues);
        return dValues;
    }

    public Values getValues() throws Exception {
        return _Values;
    }

    public void setValues(Values value) throws Exception {
        _Values = value;
    }

    public Object[] valuesCalc(fyiReporting.RDL.Report rpt) throws Exception {
        if (_Values == null)
            return null;
         
        Object[] result = new Object[_Values.getCount()];
        int index = 0;
        for (Object __dummyForeachVar1 : _Values)
        {
            Expression v = (Expression)__dummyForeachVar1;
            result[index++] = v.evaluate(rpt,null);
        }
        return result;
    }

    private Object[] getDataValues(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(this,"datavalues") instanceof Object[] ? (Object[])rpt.getCache().get(this,"datavalues") : (Object[])null;
    }

    private void setDataValues(fyiReporting.RDL.Report rpt, Object[] vs) throws Exception {
        if (vs == null)
            rpt.getCache().remove(this,"datavalues");
        else
            rpt.getCache().addReplace(this,"datavalues",vs); 
    }

}


