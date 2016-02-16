//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Classes;
import fyiReporting.RDL.CodeModules;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.DataSetsDefn;
import fyiReporting.RDL.Field;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.ReportClass;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.ReportParameter;
import fyiReporting.RDL.Textbox;

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
* Parsing name lookup.  Fields, parameters, report items, globals, user, aggregate scopes, grouping,...
*/
public class NameLookup   
{
    IDictionary fields = new IDictionary();
    IDictionary parameters = new IDictionary();
    IDictionary reportitems = new IDictionary();
    IDictionary globals = new IDictionary();
    IDictionary user = new IDictionary();
    IDictionary aggrScope = new IDictionary();
    Grouping g;
    // if expression in a table group or detail group
    //   used to default aggregates to the right group
    Matrix m;
    // if expression used in a Matrix
    //   used to default aggregate to the right matrix
    Classes instances;
    CodeModules cms;
    Type codeType = new Type();
    DataSetsDefn dsets;
    ReportLink _PageFooterHeader;
    // when expression is in page header or footer this is set
    String _ExprName = new String();
    // name of the expression; this isn't always set
    public NameLookup(IDictionary f, IDictionary p, IDictionary r, IDictionary gbl, IDictionary u, IDictionary ascope, Grouping ag, Matrix mt, CodeModules cm, Classes i, DataSetsDefn ds, Type ct) throws Exception {
        fields = f;
        parameters = p;
        reportitems = r;
        globals = gbl;
        user = u;
        aggrScope = ascope;
        g = ag;
        m = mt;
        cms = cm;
        instances = i;
        dsets = ds;
        codeType = ct;
    }

    public ReportLink getPageFooterHeader() throws Exception {
        return _PageFooterHeader;
    }

    public void setPageFooterHeader(ReportLink value) throws Exception {
        _PageFooterHeader = value;
    }

    public boolean getIsPageScope() throws Exception {
        return _PageFooterHeader != null;
    }

    public String getExpressionName() throws Exception {
        return _ExprName;
    }

    public void setExpressionName(String value) throws Exception {
        _ExprName = value;
    }

    public IDictionary getFields() throws Exception {
        return fields;
    }

    public IDictionary getParameters() throws Exception {
        return parameters;
    }

    public IDictionary getReportItems() throws Exception {
        return reportitems;
    }

    public IDictionary getUser() throws Exception {
        return user;
    }

    public IDictionary getGlobals() throws Exception {
        return globals;
    }

    public ReportClass lookupInstance(String name) throws Exception {
        if (instances == null)
            return null;
         
        return instances.get___idx(name);
    }

    public Field lookupField(String name) throws Exception {
        if (fields == null)
            return null;
         
        return (Field)fields[name];
    }

    public ReportParameter lookupParameter(String name) throws Exception {
        if (parameters == null)
            return null;
         
        return (ReportParameter)parameters[name];
    }

    public Textbox lookupReportItem(String name) throws Exception {
        if (reportitems == null)
            return null;
         
        return (Textbox)reportitems[name];
    }

    public IExpr lookupGlobal(String name) throws Exception {
        if (globals == null)
            return null;
         
        return (IExpr)globals[name];
    }

    public Type lookupType(String clsname) throws Exception {
        if (cms == null)
            return null;
         
        return cms.get___idx(clsname);
    }

    public CodeModules getCMS() throws Exception {
        return cms;
    }

    public Type getCodeClassType() throws Exception {
        return codeType;
    }

    public IExpr lookupUser(String name) throws Exception {
        if (user == null)
            return null;
         
        return (IExpr)user[name];
    }

    public Grouping lookupGrouping() throws Exception {
        return g;
    }

    public Matrix lookupMatrix() throws Exception {
        return m;
    }

    public Object lookupScope(String name) throws Exception {
        if (aggrScope == null)
            return null;
         
        return aggrScope[name];
    }

    public DataSetDefn scopeDataSet(String name) throws Exception {
        if (name == null)
        {
            // Only allowed when there is only one dataset
            if (dsets.getItems().Count != 1)
                return null;
             
            for (Object __dummyForeachVar0 : dsets.getItems().Values)
            {
                DataSetDefn ds = (DataSetDefn)__dummyForeachVar0;
                return ds;
            }
            return null;
        }
         
        return dsets.get___idx(name);
    }

}


// No easy way to get the item by index