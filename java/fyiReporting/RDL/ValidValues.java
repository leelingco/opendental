//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.DataSetReference;
import fyiReporting.RDL.ParameterValues;
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
* Query to execute for valid values of a parameter.
*/
public class ValidValues  extends ReportLink 
{
    DataSetReference _DataSetReference;
    // The query to execute to obtain a list of
    // possible values for the parameter.
    ParameterValues _ParameterValues;
    // Hardcoded values for the parameter
    public ValidValues(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataSetReference = null;
        _ParameterValues = null;
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
            else if (__dummyScrutVar0.equals("ParameterValues"))
            {
                _ParameterValues = new ParameterValues(r,this,xNodeLoop);
            }
            else
            {
                OwnerReport.rl.logError(4,"Unknown ValidValues element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if (_DataSetReference == null)
        {
            if (_ParameterValues == null)
            {
                OwnerReport.rl.logError(8,"For ValidValues element either DataSetReference or ParameterValue must be specified, but not both.");
            }
             
        }
        else if (_ParameterValues != null)
        {
            OwnerReport.rl.logError(8,"For ValidValues element either DataSetReference or ParameterValue must be specified, but not both.");
        }
          
    }

    public void finalPass() throws Exception {
        if (_DataSetReference != null)
            _DataSetReference.finalPass();
         
        if (_ParameterValues != null)
            _ParameterValues.finalPass();
         
        return ;
    }

    public DataSetReference getDataSetReference() throws Exception {
        return _DataSetReference;
    }

    public void setDataSetReference(DataSetReference value) throws Exception {
        _DataSetReference = value;
    }

    public ParameterValues getParameterValues() throws Exception {
        return _ParameterValues;
    }

    public void setParameterValues(ParameterValues value) throws Exception {
        _ParameterValues = value;
    }

    public String[] displayValues(fyiReporting.RDL.Report rpt) throws Exception {
        synchronized (this)
        {
            {
                String[] dsplValues = rpt.getCache().get(this,"displayvalues") instanceof String[] ? (String[])rpt.getCache().get(this,"displayvalues") : (String[])null;
                Object[] dataValues = new Object[]();
                if (dsplValues != null)
                    return dsplValues;
                 
                if (_DataSetReference != null)
                {
                    RefSupport<String[]> refVar___0 = new RefSupport<String[]>();
                    RefSupport<Object[]> refVar___1 = new RefSupport<Object[]>();
                    _DataSetReference.SupplyValues(rpt, refVar___0, refVar___1);
                    dsplValues = refVar___0.getValue();
                    dataValues = refVar___1.getValue();
                }
                else
                {
                    RefSupport<String[]> refVar___2 = new RefSupport<String[]>();
                    RefSupport<Object[]> refVar___3 = new RefSupport<Object[]>();
                    _ParameterValues.SupplyValues(rpt, refVar___2, refVar___3);
                    dsplValues = refVar___2.getValue();
                    dataValues = refVar___3.getValue();
                } 
                try
                {
                    // there shouldn't be a problem; but if there is it doesn't matter as values can be recreated
                    rpt.getCache().add(this,"datavalues",dataValues);
                }
                catch (Exception e1)
                {
                    rpt.rl.logError(4,"Error caching data values.  " + e1.Message);
                }

                try
                {
                    rpt.getCache().add(this,"displayvalues",dsplValues);
                }
                catch (Exception e2)
                {
                    rpt.rl.logError(4,"Error caching display values.  " + e2.Message);
                }

                return dsplValues;
            }
        }
    }

    public Object[] dataValues(fyiReporting.RDL.Report rpt) throws Exception {
        synchronized (this)
        {
            {
                String[] dsplValues = new String[]();
                Object[] dataValues = rpt.getCache().get(this,"datavalues") instanceof Object[] ? (Object[])rpt.getCache().get(this,"datavalues") : (Object[])null;
                if (dataValues != null)
                    return dataValues;
                 
                if (_DataSetReference != null)
                {
                    RefSupport<String[]> refVar___4 = new RefSupport<String[]>();
                    RefSupport<Object[]> refVar___5 = new RefSupport<Object[]>();
                    _DataSetReference.SupplyValues(rpt, refVar___4, refVar___5);
                    dsplValues = refVar___4.getValue();
                    dataValues = refVar___5.getValue();
                }
                else
                {
                    RefSupport<String[]> refVar___6 = new RefSupport<String[]>();
                    RefSupport<Object[]> refVar___7 = new RefSupport<Object[]>();
                    _ParameterValues.SupplyValues(rpt, refVar___6, refVar___7);
                    dsplValues = refVar___6.getValue();
                    dataValues = refVar___7.getValue();
                } 
                try
                {
                    // there shouldn't be a problem; but if there is it doesn't matter as values can be recreated
                    rpt.getCache().add(this,"datavalues",dataValues);
                }
                catch (Exception e1)
                {
                    rpt.rl.logError(4,"Error caching data values.  " + e1.Message);
                }

                try
                {
                    rpt.getCache().add(this,"displayvalues",dsplValues);
                }
                catch (Exception e2)
                {
                    rpt.rl.logError(4,"Error caching display values.  " + e2.Message);
                }

                return dataValues;
            }
        }
    }

}


