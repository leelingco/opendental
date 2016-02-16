//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.DataType;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Fields;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.Query;
import fyiReporting.RDL.QueryColumn;
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
* Definition of a field within a DataSet.
*/
public class Field  extends ReportLink 
{
    Name _Name;
    // Name to use for the field within the report
    // Note: Field names need only be unique
    //  within the containing Fields collection
    // Note: Either _DataField or _Value must be specified but not both
    String _DataField = new String();
    // Name of the field in the query
    // Note: DataField names do not need to be
    // unique. Multiple fields can refer to the same
    // data field.
    int _ColumnNumber = new int();
    // Column number
    TypeCode _Type = new TypeCode();
    // The data type of the field
    QueryColumn qc;
    // Query column: resolved from the query SQL
    Expression _Value;
    // (Variant) An expression that evaluates to the value of
    //  this field.  For example, =Fields!Price.Value+Fields!Tax.Value
    // The expression cannot contain aggregates or references to report items.
    public Field(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _DataField = null;
        _Value = null;
        _ColumnNumber = -1;
        _Type = TypeCode.String;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("DataField"))
            {
                _DataField = xNodeLoop.InnerText;
            }
            else // Extension !!!!!!!!!!!!!!!!!
            if (__dummyScrutVar1.equals("TypeName") || __dummyScrutVar1.equals("rd:TypeName"))
            {
                // Microsoft Designer uses this extension
                _Type = DataType.GetStyle(xNodeLoop.InnerText, this.OwnerReport);
            }
            else if (__dummyScrutVar1.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Field element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_DataField != null && _Value != null)
            OwnerReport.rl.logError(8,"Only DataField or Value may be specified in a Field element, not both.");
        else if (_DataField == null && _Value == null)
            OwnerReport.rl.logError(8,"Either DataField or Value must be specified in a Field element.");
          
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Value != null)
            _Value.finalPass();
         
        // Resolve the field if specified
        if (_DataField != null)
        {
            Fields f = (Fields)this.Parent;
            DataSetDefn ds = (DataSetDefn)f.Parent;
            Query q = ds.getQuery();
            if (q != null && q.getColumns() != null)
            {
                qc = (QueryColumn)q.getColumns()[_DataField];
                if (qc == null)
                {
                    // couldn't find the data field
                    OwnerReport.rl.logError(8,"DataField '" + _DataField + "' not part of query.");
                }
                 
            }
             
        }
         
        return ;
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public String getDataField() throws Exception {
        return _DataField;
    }

    public void setDataField(String value) throws Exception {
        _DataField = value;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

    public void setValue(Expression value) throws Exception {
        _Value = value;
    }

    public int getColumnNumber() throws Exception {
        return _ColumnNumber;
    }

    public void setColumnNumber(int value) throws Exception {
        _ColumnNumber = value;
    }

    public QueryColumn getqColumn() throws Exception {
        return qc;
    }

    public TypeCode getType() throws Exception {
        return _Type;
    }

    public void setType(TypeCode value) throws Exception {
        _Type = value;
    }

    public TypeCode getRunType() throws Exception {
        if (qc != null)
            return qc.getcolType();
        else
            return _Type; 
    }

}


