//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.ReportParm;
import fyiReporting.RdlDesign.SqlColumn;
import fyiReporting.RdlDesign.SqlSchemaInfo;

//#endif
/**
* Static utility classes used in the Rdl Designer
*/
public class DesignerUtility   
{
    static public Color colorFromHtml(String sc, Color dc) throws Exception {
        Color c = dc;
        try
        {
            c = ColorTranslator.FromHtml(sc);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return c;
    }

    // Probably should report this error
    /**
    * Read the registry to find out the ODBC names
    */
    static public void fillOdbcNames(ComboBox cbOdbcNames) throws Exception {
        if (cbOdbcNames.Items.Count > 0)
            return ;
         
        // System names
        RegistryKey rk = (Registry.LocalMachine).OpenSubKey("Software");
        if (rk == null)
            return ;
         
        rk = rk.OpenSubKey("ODBC");
        if (rk == null)
            return ;
         
        rk = rk.OpenSubKey("ODBC.INI");
        String[] nms = rk.GetSubKeyNames();
        if (nms != null)
        {
            for (Object __dummyForeachVar0 : nms)
            {
                String name = (String)__dummyForeachVar0;
                if (StringSupport.equals(name, "ODBC Data Sources") || StringSupport.equals(name, "ODBC File DSN") || StringSupport.equals(name, "ODBC"))
                    continue;
                 
                cbOdbcNames.Items.Add(name);
            }
        }
         
        // User names
        rk = (Registry.CurrentUser).OpenSubKey("Software");
        if (rk == null)
            return ;
         
        rk = rk.OpenSubKey("ODBC");
        if (rk == null)
            return ;
         
        rk = rk.OpenSubKey("ODBC.INI");
        nms = rk.GetSubKeyNames();
        if (nms != null)
        {
            for (Object __dummyForeachVar1 : nms)
            {
                String name = (String)__dummyForeachVar1;
                if (StringSupport.equals(name, "ODBC Data Sources") || StringSupport.equals(name, "ODBC File DSN") || StringSupport.equals(name, "ODBC"))
                    continue;
                 
                cbOdbcNames.Items.Add(name);
            }
        }
         
        return ;
    }

    static public String formatXml(String sDoc) throws Exception {
        XmlDocument xDoc = new XmlDocument();
        xDoc.PreserveWhitespace = false;
        xDoc.LoadXml(sDoc);
        // this will throw an exception if invalid XML
        StringWriter sw = new StringWriter();
        XmlTextWriter xtw = new XmlTextWriter(sw);
        xtw.IndentChar = ' ';
        xtw.Indentation = 2;
        xtw.Formatting = Formatting.Indented;
        xDoc.WriteContentTo(xtw);
        xtw.Close();
        sw.Close();
        return sw.ToString();
    }

    static public void getSqlData(String dataProvider, String connection, String sql, IList parameters, DataTable dt) throws Exception {
        IDbConnection cnSQL = null;
        IDbCommand cmSQL = null;
        IDataReader dr = null;
        Cursor saveCursor = Cursor.Current;
        Cursor.Current = Cursors.WaitCursor;
        try
        {
            // Open up a connection
            cnSQL = RdlEngineConfig.getConnection(dataProvider,connection);
            if (cnSQL == null)
                return ;
             
            cnSQL.Open();
            cmSQL = cnSQL.CreateCommand();
            cmSQL.CommandText = sql;
            addParameters(cmSQL,parameters);
            dr = cmSQL.ExecuteReader(CommandBehavior.SingleResult);
            String[] rowValues = new String[dt.Columns.Count];
            while (dr.Read())
            {
                int ci = 0;
                for (Object __dummyForeachVar2 : dt.Columns)
                {
                    DataColumn dc = (DataColumn)__dummyForeachVar2;
                    rowValues[ci++] = dr[dc.ColumnName].ToString();
                }
                dt.Rows.Add(rowValues);
            }
        }
        finally
        {
            if (cnSQL != null)
            {
                cnSQL.Close();
                cnSQL.Dispose();
                if (cmSQL != null)
                {
                    cmSQL.Dispose();
                    if (dr != null)
                        dr.Close();
                     
                }
                 
            }
             
            Cursor.Current = saveCursor;
        }
        return ;
    }

    static public List<SqlColumn> getSqlColumns(DesignXmlDraw d, String ds, String sql) throws Exception {
        XmlNode dsNode = d.dataSourceName(ds);
        if (dsNode == null)
            return null;
         
        String dataProvider = new String();
        String connection = new String();
        XmlNode dp = DesignXmlDraw.findNextInHierarchy(dsNode,"ConnectionProperties","DataProvider");
        if (dp == null)
            return null;
         
        dataProvider = dp.InnerText;
        dp = DesignXmlDraw.findNextInHierarchy(dsNode,"ConnectionProperties","ConnectString");
        if (dp == null)
            return null;
         
        connection = dp.InnerText;
        IList parameters = null;
        return getSqlColumns(dataProvider,connection,sql,parameters);
    }

    static public List<SqlColumn> getSqlColumns(String dataProvider, String connection, String sql, IList parameters) throws Exception {
        List<SqlColumn> cols = new List<SqlColumn>();
        IDbConnection cnSQL = null;
        IDbCommand cmSQL = null;
        IDataReader dr = null;
        Cursor saveCursor = Cursor.Current;
        Cursor.Current = Cursors.WaitCursor;
        try
        {
            // Open up a connection
            cnSQL = RdlEngineConfig.getConnection(dataProvider,connection);
            if (cnSQL == null)
                return cols;
             
            cnSQL.Open();
            cmSQL = cnSQL.CreateCommand();
            cmSQL.CommandText = sql;
            addParameters(cmSQL,parameters);
            dr = cmSQL.ExecuteReader(CommandBehavior.SchemaOnly);
            for (int i = 0;i < dr.FieldCount;i++)
            {
                SqlColumn sc = new SqlColumn();
                sc.setName(dr.GetName(i));
                sc.setDataType(dr.GetFieldType(i));
                cols.Add(sc);
            }
        }
        catch (SqlException sqle)
        {
            MessageBox.Show(sqle.Message, "SQL Error");
        }
        catch (Exception e)
        {
            MessageBox.Show(e.InnerException == null ? e.Message : e.InnerException.Message, "Error");
        }
        finally
        {
            if (cnSQL != null)
            {
                if (cmSQL != null)
                {
                    cmSQL.Dispose();
                    if (dr != null)
                        dr.Close();
                     
                }
                 
                cnSQL.Close();
                cnSQL.Dispose();
            }
             
            Cursor.Current = saveCursor;
        }
        return cols;
    }

    static public List<SqlSchemaInfo> getSchemaInfo(DesignXmlDraw d, String ds) throws Exception {
        XmlNode dsNode = d.dataSourceName(ds);
        if (dsNode == null)
            return null;
         
        String dataProvider = new String();
        String connection = new String();
        XmlNode dp = DesignXmlDraw.findNextInHierarchy(dsNode,"ConnectionProperties","DataProvider");
        if (dp == null)
            return null;
         
        dataProvider = dp.InnerText;
        dp = DesignXmlDraw.findNextInHierarchy(dsNode,"ConnectionProperties","ConnectString");
        if (dp == null)
            return null;
         
        connection = dp.InnerText;
        return getSchemaInfo(dataProvider,connection);
    }

    static public List<SqlSchemaInfo> getSchemaInfo(String dataProvider, String connection) throws Exception {
        List<SqlSchemaInfo> schemaList = new List<SqlSchemaInfo>();
        IDbConnection cnSQL = null;
        IDbCommand cmSQL = null;
        IDataReader dr = null;
        Cursor saveCursor = Cursor.Current;
        Cursor.Current = Cursors.WaitCursor;
        try
        {
            // Get the schema information
            int ID_TABLE = 0;
            int ID_TYPE = 1;
            // Open up a connection
            cnSQL = RdlEngineConfig.getConnection(dataProvider,connection);
            if (cnSQL == null)
            {
                MessageBox.Show(String.Format("Unable to connect using dataProvider '{0}'", dataProvider), "SQL Error");
                return schemaList;
            }
             
            cnSQL.Open();
            // Take advantage of .Net metadata if available
            if (cnSQL instanceof System.Data.SqlClient.SqlConnection)
                return GetSchemaInfo((System.Data.SqlClient.SqlConnection)cnSQL, schemaList);
             
            if (cnSQL instanceof System.Data.Odbc.OdbcConnection)
                return GetSchemaInfo((System.Data.Odbc.OdbcConnection)cnSQL, schemaList);
             
            if (cnSQL instanceof System.Data.OleDb.OleDbConnection)
                return GetSchemaInfo((System.Data.OleDb.OleDbConnection)cnSQL, schemaList);
             
            // Obtain the query needed to get table/view list
            String sql = RdlEngineConfig.getTableSelect(dataProvider,cnSQL);
            if (sql == null || sql.Length == 0)
                return schemaList;
             
            // when no query string; no meta information available
            // Obtain the query needed to get table/view list
            cmSQL = cnSQL.CreateCommand();
            cmSQL.CommandText = sql;
            dr = cmSQL.ExecuteReader();
            String type = "TABLE";
            while (dr.Read())
            {
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                if (ID_TYPE >= 0 && dr.FieldCount < ID_TYPE && StringSupport.equals((String)dr[ID_TYPE], "VIEW"))
                    type = "VIEW";
                 
                ssi.setType(type);
                ssi.setName((String)dr[ID_TABLE]);
                schemaList.Add(ssi);
            }
        }
        catch (SqlException sqle)
        {
            MessageBox.Show(sqle.Message, "SQL Error");
        }
        catch (Exception e)
        {
            MessageBox.Show(e.InnerException == null ? e.Message : e.InnerException.Message, "Error");
        }
        finally
        {
            if (cnSQL != null)
            {
                cnSQL.Close();
                if (cmSQL != null)
                {
                    cmSQL.Dispose();
                }
                 
                if (dr != null)
                    dr.Close();
                 
            }
             
            Cursor.Current = saveCursor;
        }
        return schemaList;
    }

    static public List<SqlSchemaInfo> getSchemaInfo(System.Data.SqlClient.SqlConnection con, List<SqlSchemaInfo> schemaList) throws Exception {
        try
        {
            DataTable tbl = con.GetSchema(System.Data.SqlClient.SqlClientMetaDataCollectionNames.Tables);
            for (Object __dummyForeachVar3 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar3;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("TABLE");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
            tbl = con.GetSchema(System.Data.SqlClient.SqlClientMetaDataCollectionNames.Views);
            for (Object __dummyForeachVar4 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar4;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("VIEW");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return schemaList;
    }

    static public List<SqlSchemaInfo> getSchemaInfo(System.Data.OleDb.OleDbConnection con, List<SqlSchemaInfo> schemaList) throws Exception {
        try
        {
            DataTable tbl = con.GetSchema(System.Data.OleDb.OleDbMetaDataCollectionNames.Tables);
            for (Object __dummyForeachVar5 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar5;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("TABLE");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
            tbl = con.GetSchema(System.Data.OleDb.OleDbMetaDataCollectionNames.Views);
            for (Object __dummyForeachVar6 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar6;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("VIEW");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        return schemaList;
    }

    static public List<SqlSchemaInfo> getSchemaInfo(System.Data.Odbc.OdbcConnection con, List<SqlSchemaInfo> schemaList) throws Exception {
        try
        {
            DataTable tbl = con.GetSchema(System.Data.Odbc.OdbcMetaDataCollectionNames.Tables);
            for (Object __dummyForeachVar7 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar7;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("TABLE");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
            tbl = con.GetSchema(System.Data.Odbc.OdbcMetaDataCollectionNames.Views);
            for (Object __dummyForeachVar8 : tbl.Rows)
            {
                DataRow row = (DataRow)__dummyForeachVar8;
                SqlSchemaInfo ssi = new SqlSchemaInfo();
                ssi.setType("VIEW");
                ssi.setName((String)row["table_name"]);
                schemaList.Add(ssi);
            }
        }
        catch (Exception __dummyCatchVar3)
        {
        }

        return schemaList;
    }

    static public boolean testConnection(String dataProvider, String connection) throws Exception {
        IDbConnection cnSQL = null;
        boolean bResult = false;
        try
        {
            cnSQL = RdlEngineConfig.getConnection(dataProvider,connection);
            cnSQL.Open();
            bResult = true;
        }
        catch (Exception e)
        {
            // we opened the connection
            MessageBox.Show(e.InnerException == null ? e.Message : e.InnerException.Message, "Unable to open connection");
        }
        finally
        {
            if (cnSQL != null)
            {
                cnSQL.Close();
                cnSQL.Dispose();
            }
             
        }
        return bResult;
    }

    static public boolean isNumeric(Type t) throws Exception {
        String st = t.ToString();
        System.String __dummyScrutVar0 = st;
        if (__dummyScrutVar0.equals("System.Int16") || __dummyScrutVar0.equals("System.Int32") || __dummyScrutVar0.equals("System.Int64") || __dummyScrutVar0.equals("System.Single") || __dummyScrutVar0.equals("System.Double") || __dummyScrutVar0.equals("System.Decimal"))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    /**
    * Validates a size parameter
    * 
    *  @param t 
    *  @param bZero true if 0 is valid size
    *  @param bMinus true if minus is allowed
    *  @return Throws exception with the invalid message
    */
    static public void validateSize(String t, boolean bZero, boolean bMinus) throws Exception {
        t = t.Trim();
        if (t.Length == 0)
            return ;
         
        // not specified is ok?
        // Ensure we have valid units
        if (t.IndexOf("in") < 0 && t.IndexOf("cm") < 0 && t.IndexOf("mm") < 0 && t.IndexOf("pt") < 0 && t.IndexOf("pc") < 0)
        {
            throw new Exception("Size unit is not valid.  Must be in, cm, mm, pt, or pc.");
        }
         
        int space = t.LastIndexOf(' ');
        String n = "";
        // number string
        String u = new String();
        try
        {
            // unit string
            // Convert.ToDecimal can be very picky
            if (space != -1)
            {
                // any spaces
                n = t.Substring(0, space).Trim();
                // number string
                u = t.Substring(space).Trim();
            }
            else // unit string
            if (t.Length >= 3)
            {
                n = t.Substring(0, t.Length - 2).Trim();
                u = t.Substring(t.Length - 2).Trim();
            }
              
        }
        catch (Exception ex)
        {
            throw new Exception(ex.Message);
        }

        if (n.Length == 0 || !Regex.IsMatch(n, "\\A[ ]*[-]?[0-9]*[.]?[0-9]*[ ]*\\Z"))
        {
            throw new Exception("Number format is invalid.  ###.## is the proper form.");
        }
         
        float v = DesignXmlDraw.getSize(t);
        if (!bZero)
        {
            if (v < .1)
                throw new Exception("Size can't be zero.");
             
        }
        else if (v < 0 && !bMinus)
            throw new Exception("Size can't be less than zero.");
          
        return ;
    }

    static public String makeValidSize(String t, boolean bZero) throws Exception {
        return makeValidSize(t,bZero,false);
    }

    /**
    * Ensures that a user provided string results in a valid size
    * 
    *  @param t 
    *  @return
    */
    static public String makeValidSize(String t, boolean bZero, boolean bNegative) throws Exception {
        // Ensure we have valid units
        if (t.IndexOf("in") < 0 && t.IndexOf("cm") < 0 && t.IndexOf("mm") < 0 && t.IndexOf("pt") < 0 && t.IndexOf("pc") < 0)
        {
            t += "in";
        }
         
        float v = DesignXmlDraw.getSize(t);
        if (!bZero)
        {
            if (v < .1)
                t = ".1pt";
             
        }
         
        if (!bNegative)
        {
            if (v < 0)
                t = "0in";
             
        }
         
        return t;
    }

    static private void addParameters(IDbCommand cmSQL, IList parameters) throws Exception {
        if (parameters == null || parameters.Count <= 0)
            return ;
         
        for (Object __dummyForeachVar9 : parameters)
        {
            ReportParm rp = (ReportParm)__dummyForeachVar9;
            String paramName = new String();
            // force the name to start with @
            if (rp.getName()[0] == '@')
                paramName = rp.getName();
            else
                paramName = "@" + rp.getName(); 
            IDbDataParameter dp = cmSQL.CreateParameter();
            dp.ParameterName = paramName;
            if (rp.getDefaultValue() == null || rp.getDefaultValue().Count == 0)
            {
                Object pvalue = null;
                // put some dummy values in it;  some drivers (e.g. mysql odbc) don't like null values
                System.String.APPLY __dummyScrutVar1 = rp.getDataType().ToLower();
                if (__dummyScrutVar1.equals("datetime"))
                {
                    pvalue = new DateTime(0);
                }
                else if (__dummyScrutVar1.equals("double"))
                {
                    pvalue = new double();
                }
                else if (__dummyScrutVar1.equals("boolean"))
                {
                    pvalue = new Boolean();
                }
                else
                {
                    pvalue = (Object)"";
                }   
                dp.Value = pvalue;
            }
            else
            {
                String val = (String)rp.getDefaultValue()[0];
                dp.Value = val;
            } 
            cmSQL.Parameters.Add(dp);
        }
    }

}


