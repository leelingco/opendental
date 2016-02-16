//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.Field;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;

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
* The query to use to obtain a list of values for a parameter.   See ValidValues.
*/
public class DataSetReference  extends ReportLink 
{
    String _DataSetName = new String();
    //Name of the data set to use.
    DataSetDefn _ds;
    // DataSet that matches the name
    String _ValueField = new String();
    //Name of the field to use for the values/defaults for the parameter
    String _LabelField = new String();
    //Name of the field to use for the value to display to the
    // user for the selection.  If not supplied or the returned
    // value is null, the value in the ValueField is used.
    //  not used for DefaultValue.
    Field _vField;
    // resolved value name
    Field _lField;
    // resolved label name
    public DataSetReference(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataSetName = null;
        _ValueField = null;
        _LabelField = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataSetName"))
            {
                _DataSetName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("ValueField"))
            {
                _ValueField = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("LabelField"))
            {
                _LabelField = xNodeLoop.InnerText;
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown DataSetReference element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_DataSetName == null)
            OwnerReport.rl.logError(8,"DataSetReference DataSetName is required but not specified.");
         
        if (_ValueField == null)
            OwnerReport.rl.logError(8,"DataSetReference ValueField is required but not specified for" + _DataSetName == null ? "<unknown name>" : _DataSetName);
         
    }

    public void finalPass() throws Exception {
        _ds = OwnerReport.getDataSetsDefn().get___idx(this._DataSetName);
        if (_ds == null)
            OwnerReport.rl.logError(8,"DataSetReference refers to unknown data set '" + _DataSetName + "'");
        else
        {
            _vField = _ds.getFields().get___idx(_ValueField);
            if (_vField == null)
                OwnerReport.rl.logError(8,"ValueField refers to unknown field '" + _ValueField + "'");
            else
            {
                if (_LabelField == null)
                    _lField = _vField;
                else
                {
                    _lField = _ds.getFields().get___idx(_LabelField);
                    if (_lField == null)
                        OwnerReport.rl.logError(8,"LabelField refers to unknown field '" + _LabelField + "'");
                     
                } 
            } 
        } 
        return ;
    }

    public String getDataSetName() throws Exception {
        return _DataSetName;
    }

    public void setDataSetName(String value) throws Exception {
        _DataSetName = value;
    }

    public String getValueField() throws Exception {
        return _ValueField;
    }

    public void setValueField(String value) throws Exception {
        _ValueField = value;
    }

    public String getLabelField() throws Exception {
        return _LabelField;
    }

    public void setLabelField(String value) throws Exception {
        _LabelField = value;
    }

    public void supplyValues(fyiReporting.RDL.Report rpt, RefSupport<String[]> displayValues, RefSupport<Object[]> dataValues) throws Exception {
        displayValues.setValue(null);
        dataValues.setValue(null);
        Rows rows = _ds.getQuery().getMyData(rpt);
        if (rows == null)
        {
            // do we already have data?
            // TODO:  this is wasteful;  likely to reretrieve the data again when report run with parameters
            //   should mark a dataset as only having one retrieval???
            boolean lConnect = _ds.getQuery().getDataSourceDefn().isConnected(rpt);
            if (!lConnect)
                _ds.getQuery().getDataSourceDefn().connectDataSource(rpt);
             
            // connect; since not already connected
            _ds.getData(rpt);
            // get the data
            if (!lConnect)
                // if we connected; then
                _ds.getQuery().getDataSourceDefn().cleanUp(rpt);
             
            //   we cleanup
            rows = _ds.getQuery().getMyData(rpt);
            if (rows == null)
                return ;
             
        }
         
        // any data now?
        // no out of luck
        displayValues.setValue(new String[rows.getData().Count]);
        dataValues.setValue(new Object[displayValues.getValue().Length]);
        int index = 0;
        Object o = new Object();
        for (Object __dummyForeachVar1 : rows.getData())
        {
            Row r = (Row)__dummyForeachVar1;
            dataValues.getValue()[index] = r.getData()[_vField.getColumnNumber()];
            o = r.getData()[_lField.getColumnNumber()];
            if (o == null || o == DBNull.Value)
                displayValues.getValue()[index] = "";
            else
                displayValues.getValue()[index] = o.ToString(); 
            index++;
        }
    }

}


