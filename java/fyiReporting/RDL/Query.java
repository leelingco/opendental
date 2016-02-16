//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataSourceDefn;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Field;
import fyiReporting.RDL.Fields;
import fyiReporting.RDL.Filters;
import fyiReporting.RDL.QueryColumn;
import fyiReporting.RDL.QueryCommandTypeEnum;
import fyiReporting.RDL.QueryParameter;
import fyiReporting.RDL.QueryParameters;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.XmlUtil;

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
* Query representation against a data source.  Holds the data at runtime.
*/
public class Query  extends ReportLink 
{
    String _DataSourceName = new String();
    // Name of the data source to execute the query against
    DataSourceDefn _DataSourceDefn;
    //  the data source object the DataSourceName references.
    QueryCommandTypeEnum _QueryCommandType = QueryCommandTypeEnum.Text;
    // Indicates what type of query is contained in the CommandText
    Expression _CommandText;
    //	(string) The query to execute to obtain the data for the report
    QueryParameters _QueryParameters;
    // A list of parameters that are passed to the data
    // source as part of the query.
    int _Timeout = new int();
    // Number of seconds to allow the query to run before
    // timing out.   Must be >= 0; If omitted or zero; no timeout
    int _RowLimit = new int();
    // Number of rows to retrieve before stopping retrieval; 0 means no limit
    IDictionary _Columns = new IDictionary();
    // QueryColumn (when SQL)
    public Query(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataSourceName = null;
        _QueryCommandType = QueryCommandTypeEnum.Text;
        _CommandText = null;
        _QueryParameters = null;
        _Timeout = 0;
        _RowLimit = 0;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataSourceName"))
            {
                _DataSourceName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("CommandType"))
            {
                _QueryCommandType = fyiReporting.RDL.QueryCommandType.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("CommandText"))
            {
                _CommandText = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("QueryParameters"))
            {
                _QueryParameters = new QueryParameters(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Timeout"))
            {
                _Timeout = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("RowLimit"))
            {
                // Extension of RDL specification
                _RowLimit = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Query element '" + xNodeLoop.Name + "' ignored.");
            }      
        }
        // end of switch
        // end of foreach
        // Resolve the data source name to the object
        if (_DataSourceName == null)
        {
            r.rl.logError(8,"DataSourceName element not specified for Query.");
            return ;
        }
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_CommandText != null)
            _CommandText.finalPass();
         
        if (_QueryParameters != null)
            _QueryParameters.finalPass();
         
        // verify the data source
        DataSourceDefn ds = null;
        if (OwnerReport.getDataSourcesDefn() != null && OwnerReport.getDataSourcesDefn().getItems() != null)
        {
            ds = OwnerReport.getDataSourcesDefn().get___idx(_DataSourceName);
        }
         
        if (ds == null)
        {
            OwnerReport.rl.logError(8,"Query references unknown data source '" + _DataSourceName + "'");
            return ;
        }
         
        _DataSourceDefn = ds;
        IDbConnection cnSQL = ds.sqlConnect(null);
        if (cnSQL == null || _CommandText == null)
            return ;
         
        // Treat this as a SQL statement
        String sql = _CommandText.evaluateString(null,null);
        IDbCommand cmSQL = null;
        IDataReader dr = null;
        try
        {
            cmSQL = cnSQL.CreateCommand();
            cmSQL.CommandText = addParametersAsLiterals(null,cnSQL,sql,false);
            if (this._QueryCommandType == QueryCommandTypeEnum.StoredProcedure)
                cmSQL.CommandType = CommandType.StoredProcedure;
             
            addParameters(null,cnSQL,cmSQL,false);
            dr = cmSQL.ExecuteReader(CommandBehavior.SchemaOnly);
            if (dr.FieldCount < 10)
                _Columns = new ListDictionary();
            else
                // Hashtable is overkill for small lists
                _Columns = new Hashtable(dr.FieldCount); 
            for (int i = 0;i < dr.FieldCount;i++)
            {
                QueryColumn qc = new QueryColumn(i, dr.GetName(i), Type.GetTypeCode(dr.GetFieldType(i)));
                try
                {
                    _Columns.Add(qc.colName, qc);
                }
                catch (Exception __dummyCatchVar0)
                {
                    // name has already been added to list:
                    // According to the RDL spec SQL names are matched by Name not by relative
                    //   position: this seems wrong to me and causes this problem; but
                    //   user can fix by using "as" keyword to name columns in Select
                    //    e.g.  Select col as "col1", col as "col2" from tableA
                    OwnerReport.rl.LogError(8, String.Format("Column '{0}' is not uniquely defined within the SQL Select columns.", qc.colName));
                }
            
            }
        }
        catch (Exception e)
        {
            OwnerReport.rl.logError(4,"SQL Exception during report compilation: " + e.Message + "\r\nSQL: " + sql);
        }
        finally
        {
            if (cmSQL != null)
            {
                cmSQL.Dispose();
                if (dr != null)
                    dr.Close();
                 
            }
             
        }
        return ;
    }

    public void getData(fyiReporting.RDL.Report rpt, Fields flds, Filters f) throws Exception {
        Rows uData = this.getMyUserData(rpt);
        if (uData != null)
        {
            this.setMyData(rpt,uData);
            return ;
        }
         
        // Treat this as a SQL statement
        DataSourceDefn ds = _DataSourceDefn;
        if (ds == null || _CommandText == null)
        {
            this.setMyData(rpt,null);
            return ;
        }
         
        IDbConnection cnSQL = ds.sqlConnect(rpt);
        if (cnSQL == null)
        {
            this.setMyData(rpt,null);
            return ;
        }
         
        Rows _Data = new Rows(rpt,null,null,null);
        // no sorting and grouping at base data
        String sql = _CommandText.evaluateString(rpt,null);
        IDbCommand cmSQL = null;
        IDataReader dr = null;
        try
        {
            cmSQL = cnSQL.CreateCommand();
            cmSQL.CommandText = addParametersAsLiterals(rpt,cnSQL,sql,true);
            if (this._QueryCommandType == QueryCommandTypeEnum.StoredProcedure)
                cmSQL.CommandType = CommandType.StoredProcedure;
             
            if (this._Timeout > 0)
            {
            }
             
            //cmSQL.CommandTimeout = this._Timeout;
            addParameters(rpt,cnSQL,cmSQL,true);
            dr = cmSQL.ExecuteReader(CommandBehavior.SingleResult);
            List<Row> ar = new List<Row>();
            _Data.setData(ar);
            int rowCount = 0;
            int maxRows = _RowLimit > 0 ? _RowLimit : int.MaxValue;
            int fieldCount = flds.getItems().Count;
            // Determine the query column number for each field
            int[] qcn = new int[flds.getItems().Count];
            for (Object __dummyForeachVar1 : flds)
            {
                Field fld = (Field)__dummyForeachVar1;
                qcn[fld.getColumnNumber()] = -1;
                if (fld.getValue() != null)
                    continue;
                 
                try
                {
                    qcn[fld.getColumnNumber()] = dr.GetOrdinal(fld.getDataField());
                }
                catch (Exception __dummyCatchVar1)
                {
                    qcn[fld.getColumnNumber()] = -1;
                }
            
            }
            while (dr.Read())
            {
                Row or = new Row(_Data,fieldCount);
                for (Object __dummyForeachVar2 : flds)
                {
                    Field fld = (Field)__dummyForeachVar2;
                    if (qcn[fld.getColumnNumber()] != -1)
                    {
                        or.getData()[fld.getColumnNumber()] = dr.GetValue(qcn[fld.getColumnNumber()]);
                    }
                     
                }
                // Apply the filters
                if (f == null || f.apply(rpt,or))
                {
                    or.setRowNumber(rowCount);
                    //
                    rowCount++;
                    ar.Add(or);
                }
                 
                if (--maxRows <= 0)
                    break;
                 
            }
            // don't retrieve more than max
            ar.TrimExcess();
            // free up any extraneous space; can be sizeable for large # rows
            if (f != null)
                f.applyFinalFilters(rpt,_Data,false);
             
        }
        catch (Exception e)
        {
            //#if DEBUG
            //				rpt.rl.LogError(4, "Rows Read:" + ar.Count.ToString() + " SQL:" + sql );
            //#endif
            rpt.rl.logError(8,"SQL Exception" + e.Message + "\r\n" + e.StackTrace);
        }
        finally
        {
            if (cmSQL != null)
            {
                cmSQL.Dispose();
                if (dr != null)
                    dr.Close();
                 
            }
             
        }
        this.setMyData(rpt,_Data);
    }

    // Obtain the data from the XML
    public void getData(fyiReporting.RDL.Report rpt, String xmlData, Fields flds, Filters f) throws Exception {
        Rows uData = this.getMyUserData(rpt);
        if (uData != null)
        {
            this.setMyData(rpt,uData);
            return ;
        }
         
        int fieldCount = flds.getItems().Count;
        XmlDocument doc = new XmlDocument();
        doc.PreserveWhitespace = false;
        doc.LoadXml(xmlData);
        XmlNode xNode = new XmlNode();
        xNode = doc.LastChild;
        if (xNode == null || !(StringSupport.equals(xNode.Name, "Rows") || StringSupport.equals(xNode.Name, "fyi:Rows")))
        {
            throw new Exception("Error: XML Data must contain top level rows.");
        }
         
        Rows _Data = new Rows(rpt,null,null,null);
        List<Row> ar = new List<Row>();
        _Data.setData(ar);
        int rowCount = 0;
        for (Object __dummyForeachVar4 : xNode.ChildNodes)
        {
            XmlNode xNodeRow = (XmlNode)__dummyForeachVar4;
            if (xNodeRow.NodeType != XmlNodeType.Element)
                continue;
             
            if (!StringSupport.equals(xNodeRow.Name, "Row"))
                continue;
             
            Row or = new Row(_Data,fieldCount);
            for (Object __dummyForeachVar3 : xNodeRow.ChildNodes)
            {
                XmlNode xNodeColumn = (XmlNode)__dummyForeachVar3;
                Field fld = (Field)(flds.getItems()[xNodeColumn.Name]);
                // Find the column
                if (fld == null)
                    continue;
                 
                // Extraneous data is ignored
                TypeCode tc = fld.getqColumn() != null ? fld.getqColumn().getcolType() : fld.getType();
                if (xNodeColumn.InnerText == null || xNodeColumn.InnerText.Length == 0)
                    or.getData()[fld.getColumnNumber()] = null;
                else if (tc == TypeCode.String)
                    or.getData()[fld.getColumnNumber()] = xNodeColumn.InnerText;
                else
                {
                    try
                    {
                        or.getData()[fld.getColumnNumber()] = Convert.ChangeType(xNodeColumn.InnerText, tc, NumberFormatInfo.InvariantInfo);
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                        // all conversion errors result in a null value
                        or.getData()[fld.getColumnNumber()] = null;
                    }
                
                }  
            }
            // Apply the filters
            if (f == null || f.apply(rpt,or))
            {
                or.setRowNumber(rowCount);
                //
                rowCount++;
                ar.Add(or);
            }
             
        }
        ar.TrimExcess();
        // free up any extraneous space; can be sizeable for large # rows
        if (f != null)
            f.applyFinalFilters(rpt,_Data,false);
         
        setMyData(rpt,_Data);
    }

    public void setData(fyiReporting.RDL.Report rpt, IEnumerable ie, Fields flds, Filters f) throws Exception {
        if (ie == null)
        {
            // Does user want to remove user data?
            setMyUserData(rpt,null);
            return ;
        }
         
        Rows rows = new Rows(rpt,null,null,null);
        // no sorting and grouping at base data
        List<Row> ar = new List<Row>();
        rows.setData(ar);
        int rowCount = 0;
        int maxRows = _RowLimit > 0 ? _RowLimit : int.MaxValue;
        int fieldCount = flds.getItems().Count;
        for (Object __dummyForeachVar6 : ie)
        {
            Object dt = (Object)__dummyForeachVar6;
            // Get the type.
            Type myType = dt.GetType();
            // Build the row
            Row or = new Row(rows,fieldCount);
            for (Object __dummyForeachVar5 : flds)
            {
                // Go thru each field and try to obtain a value
                Field fld = (Field)__dummyForeachVar5;
                // Get the type and fields of FieldInfoClass.
                FieldInfo fi = myType.GetField(fld.getName().getNm(), BindingFlags.Instance | BindingFlags.Public);
                if (fi != null)
                {
                    or.getData()[fld.getColumnNumber()] = fi.GetValue(dt);
                }
                 
            }
            // Apply the filters
            if (f == null || f.apply(rpt,or))
            {
                or.setRowNumber(rowCount);
                //
                rowCount++;
                ar.Add(or);
            }
             
            if (--maxRows <= 0)
                break;
             
        }
        // don't retrieve more than max
        ar.TrimExcess();
        // free up any extraneous space; can be sizeable for large # rows
        if (f != null)
            f.applyFinalFilters(rpt,rows,false);
         
        setMyUserData(rpt,rows);
    }

    public void setData(fyiReporting.RDL.Report rpt, IDataReader dr, Fields flds, Filters f) throws Exception {
        if (dr == null)
        {
            // Does user want to remove user data?
            setMyUserData(rpt,null);
            return ;
        }
         
        Rows rows = new Rows(rpt,null,null,null);
        // no sorting and grouping at base data
        List<Row> ar = new List<Row>();
        rows.setData(ar);
        int rowCount = 0;
        int maxRows = _RowLimit > 0 ? _RowLimit : int.MaxValue;
        while (dr.Read())
        {
            Row or = new Row(rows, dr.FieldCount);
            dr.GetValues(or.getData());
            // Apply the filters
            if (f == null || f.apply(rpt,or))
            {
                or.setRowNumber(rowCount);
                //
                rowCount++;
                ar.Add(or);
            }
             
            if (--maxRows <= 0)
                break;
             
        }
        // don't retrieve more than max
        ar.TrimExcess();
        // free up any extraneous space; can be sizeable for large # rows
        if (f != null)
            f.applyFinalFilters(rpt,rows,false);
         
        setMyUserData(rpt,rows);
    }

    public void setData(fyiReporting.RDL.Report rpt, DataTable dt, Fields flds, Filters f) throws Exception {
        if (dt == null)
        {
            // Does user want to remove user data?
            setMyUserData(rpt,null);
            return ;
        }
         
        Rows rows = new Rows(rpt,null,null,null);
        // no sorting and grouping at base data
        List<Row> ar = new List<Row>();
        rows.setData(ar);
        int rowCount = 0;
        int maxRows = _RowLimit > 0 ? _RowLimit : int.MaxValue;
        int fieldCount = flds.getItems().Count;
        for (Object __dummyForeachVar8 : dt.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar8;
            Row or = new Row(rows,fieldCount);
            for (Object __dummyForeachVar7 : flds.getItems().Values)
            {
                // Loop thru the columns obtaining the data values by name
                Field fld = (Field)__dummyForeachVar7;
                or.getData()[fld.getColumnNumber()] = dr[fld.getDataField()];
            }
            // Apply the filters
            if (f == null || f.apply(rpt,or))
            {
                or.setRowNumber(rowCount);
                //
                rowCount++;
                ar.Add(or);
            }
             
            if (--maxRows <= 0)
                break;
             
        }
        // don't retrieve more than max
        ar.TrimExcess();
        // free up any extraneous space; can be sizeable for large # rows
        if (f != null)
            f.applyFinalFilters(rpt,rows,false);
         
        setMyUserData(rpt,rows);
    }

    public void setData(fyiReporting.RDL.Report rpt, XmlDocument xmlDoc, Fields flds, Filters f) throws Exception {
        if (xmlDoc == null)
        {
            // Does user want to remove user data?
            setMyUserData(rpt,null);
            return ;
        }
         
        Rows rows = new Rows(rpt,null,null,null);
        // no sorting and grouping at base data
        XmlNode xNode = new XmlNode();
        xNode = xmlDoc.LastChild;
        if (xNode == null || !(StringSupport.equals(xNode.Name, "Rows") || StringSupport.equals(xNode.Name, "fyi:Rows")))
        {
            throw new Exception("XML Data must contain top level element Rows.");
        }
         
        List<Row> ar = new List<Row>();
        rows.setData(ar);
        int rowCount = 0;
        int fieldCount = flds.getItems().Count;
        for (Object __dummyForeachVar10 : xNode.ChildNodes)
        {
            XmlNode xNodeRow = (XmlNode)__dummyForeachVar10;
            if (xNodeRow.NodeType != XmlNodeType.Element)
                continue;
             
            if (!StringSupport.equals(xNodeRow.Name, "Row"))
                continue;
             
            Row or = new Row(rows,fieldCount);
            for (Object __dummyForeachVar9 : xNodeRow.ChildNodes)
            {
                XmlNode xNodeColumn = (XmlNode)__dummyForeachVar9;
                Field fld = (Field)(flds.getItems()[xNodeColumn.Name]);
                // Find the column
                if (fld == null)
                    continue;
                 
                // Extraneous data is ignored
                if (xNodeColumn.InnerText == null || xNodeColumn.InnerText.Length == 0)
                    or.getData()[fld.getColumnNumber()] = null;
                else if (fld.getType() == TypeCode.String)
                    or.getData()[fld.getColumnNumber()] = xNodeColumn.InnerText;
                else
                {
                    try
                    {
                        or.getData()[fld.getColumnNumber()] = Convert.ChangeType(xNodeColumn.InnerText, fld.getType(), NumberFormatInfo.InvariantInfo);
                    }
                    catch (Exception __dummyCatchVar3)
                    {
                        // all conversion errors result in a null value
                        or.getData()[fld.getColumnNumber()] = null;
                    }
                
                }  
            }
            // Apply the filters
            if (f == null || f.apply(rpt,or))
            {
                or.setRowNumber(rowCount);
                //
                rowCount++;
                ar.Add(or);
            }
             
        }
        ar.TrimExcess();
        // free up any extraneous space; can be sizeable for large # rows
        if (f != null)
            f.applyFinalFilters(rpt,rows,false);
         
        setMyUserData(rpt,rows);
    }

    private void addParameters(fyiReporting.RDL.Report rpt, IDbConnection cn, IDbCommand cmSQL, boolean bValue) throws Exception {
        // any parameters to substitute
        if (this._QueryParameters == null || this._QueryParameters.getItems() == null || this._QueryParameters.getItems().Count == 0 || this._QueryParameters.getContainsArray())
            return ;
         
        // arrays get handled by AddParametersAsLiterals
        // AddParametersAsLiterals handles it when there is replacement
        if (RdlEngineConfig.doParameterReplacement(getProvider(),cn))
            return ;
         
        for (Object __dummyForeachVar11 : this._QueryParameters.getItems())
        {
            QueryParameter qp = (QueryParameter)__dummyForeachVar11;
            String paramName = new String();
            // force the name to start with @
            if (qp.getName().getNm()[0] == '@')
                paramName = qp.getName().getNm();
            else
                paramName = "@" + qp.getName().getNm(); 
            Object pvalue = bValue ? qp.getValue().evaluate(rpt,null) : null;
            IDbDataParameter dp = cmSQL.CreateParameter();
            dp.ParameterName = paramName;
            if (pvalue instanceof ArrayList)
            {
                // Probably a MultiValue Report parameter result
                ArrayList ar = (ArrayList)pvalue;
                dp.Value = ar.ToArray(ar[0].GetType());
            }
            else
                dp.Value = pvalue; 
            cmSQL.Parameters.Add(dp);
        }
    }

    private String addParametersAsLiterals(fyiReporting.RDL.Report rpt, IDbConnection cn, String sql, boolean bValue) throws Exception {
        // No parameters means nothing to do
        if (this._QueryParameters == null || this._QueryParameters.getItems() == null || this._QueryParameters.getItems().Count == 0)
            return sql;
         
        // Only do this for ODBC datasources - AddParameters handles it in other cases
        if (!RdlEngineConfig.doParameterReplacement(getProvider(),cn))
        {
            if (!_QueryParameters.getContainsArray())
                return sql;
             
        }
         
        // when array we do substitution
        StringBuilder sb = new StringBuilder(sql);
        List<QueryParameter> qlist = new List<QueryParameter>();
        if (_QueryParameters.getItems().Count <= 1)
            qlist = _QueryParameters.getItems();
        else
        {
            // need to sort the list so that longer items are first in the list
            // otherwise substitution could be done incorrectly
            qlist = new List<QueryParameter>(_QueryParameters.getItems());
            qlist.Sort();
        } 
        for (Object __dummyForeachVar12 : qlist)
        {
            QueryParameter qp = (QueryParameter)__dummyForeachVar12;
            String paramName = new String();
            // force the name to start with @
            if (qp.getName().getNm()[0] == '@')
                paramName = qp.getName().getNm();
            else
                paramName = "@" + qp.getName().getNm(); 
            // build the replacement value
            String svalue = new String();
            if (bValue)
            {
                // use the value provided
                svalue = this.parameterValue(rpt,qp);
            }
            else
            {
                // just need a place holder value that will pass parsing
                TypeCode __dummyScrutVar1 = qp.getValue().getExpr().getTypeCode();
                if (__dummyScrutVar1.equals(TypeCode.Char))
                {
                    svalue = "' '";
                }
                else if (__dummyScrutVar1.equals(TypeCode.DateTime))
                {
                    svalue = "'1900-01-01 00:00:00'";
                }
                else if (__dummyScrutVar1.equals(TypeCode.Decimal) || __dummyScrutVar1.equals(TypeCode.Double) || __dummyScrutVar1.equals(TypeCode.Int32) || __dummyScrutVar1.equals(TypeCode.Int64))
                {
                    svalue = "0";
                }
                else if (__dummyScrutVar1.equals(TypeCode.Boolean))
                {
                    svalue = "'false'";
                }
                else
                {
                    svalue = "' '";
                }    
            } 
            sb.Replace(paramName, svalue);
        }
        return sb.ToString();
    }

    private String parameterValue(fyiReporting.RDL.Report rpt, QueryParameter qp) throws Exception {
        if (!qp.getIsArray())
        {
            // handle non-array
            String svalue = qp.getValue().evaluateString(rpt,null);
            if (svalue == null)
                svalue = "null";
            else
            {
                TypeCode __dummyScrutVar2 = qp.getValue().getExpr().getTypeCode();
                if (__dummyScrutVar2.equals(TypeCode.Char) || __dummyScrutVar2.equals(TypeCode.DateTime) || __dummyScrutVar2.equals(TypeCode.String))
                {
                    // need to double up on "'" and then surround by '
                    svalue = svalue.Replace("'", "''");
                    svalue = "'" + svalue + "'";
                }
                 
            } 
            return svalue;
        }
         
        StringBuilder sb = new StringBuilder();
        ArrayList ar = qp.getValue().evaluate(rpt,null) instanceof ArrayList ? (ArrayList)qp.getValue().evaluate(rpt,null) : (ArrayList)null;
        if (ar == null)
            return null;
         
        boolean bFirst = true;
        for (Object __dummyForeachVar13 : ar)
        {
            Object v = (Object)__dummyForeachVar13;
            if (!bFirst)
                sb.Append(", ");
             
            if (v == null)
            {
                sb.Append("null");
            }
            else
            {
                String sv = v.ToString();
                if (v instanceof String || v instanceof char || v instanceof DateTime)
                {
                    // need to double up on "'" and then surround by '
                    sv = sv.Replace("'", "''");
                    sb.Append("'");
                    sb.Append(sv);
                    sb.Append("'");
                }
                else
                    sb.Append(sv); 
            } 
            bFirst = false;
        }
        return sb.ToString();
    }

    private String getProvider() throws Exception {
        if (this.getDataSourceDefn() == null || this.getDataSourceDefn().getConnectionProperties() == null)
            return "";
         
        return this.getDataSourceDefn().getConnectionProperties().getDataProvider();
    }

    public String getDataSourceName() throws Exception {
        return _DataSourceName;
    }

    public DataSourceDefn getDataSourceDefn() throws Exception {
        return _DataSourceDefn;
    }

    public QueryCommandTypeEnum getQueryCommandType() throws Exception {
        return _QueryCommandType;
    }

    public void setQueryCommandType(QueryCommandTypeEnum value) throws Exception {
        _QueryCommandType = value;
    }

    public Expression getCommandText() throws Exception {
        return _CommandText;
    }

    public void setCommandText(Expression value) throws Exception {
        _CommandText = value;
    }

    public QueryParameters getQueryParameters() throws Exception {
        return _QueryParameters;
    }

    public void setQueryParameters(QueryParameters value) throws Exception {
        _QueryParameters = value;
    }

    public int getTimeout() throws Exception {
        return _Timeout;
    }

    public void setTimeout(int value) throws Exception {
        _Timeout = value;
    }

    public IDictionary getColumns() throws Exception {
        return _Columns;
    }

    // Runtime data
    public Rows getMyData(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(this,"data") instanceof Rows ? (Rows)rpt.getCache().get(this,"data") : (Rows)null;
    }

    private void setMyData(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        if (data == null)
            rpt.getCache().remove(this,"data");
        else
            rpt.getCache().addReplace(this,"data",data); 
    }

    private Rows getMyUserData(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(this,"userdata") instanceof Rows ? (Rows)rpt.getCache().get(this,"userdata") : (Rows)null;
    }

    private void setMyUserData(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        if (data == null)
            rpt.getCache().remove(this,"userdata");
        else
            rpt.getCache().addReplace(this,"userdata",data); 
    }

}


