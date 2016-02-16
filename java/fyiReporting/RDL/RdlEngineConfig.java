//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.CompressionConfig;
import fyiReporting.RDL.CustomReportItemEntry;
import fyiReporting.RDL.ICustomReportItem;
import fyiReporting.RDL.SqlConfigEntry;
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
* Handle SQL configuration and connections
*/
public class RdlEngineConfig   
{
    static public IDictionary SqlEntries = new ListDictionary();
    // list of entries
    static public IDictionary<String, CustomReportItemEntry> CustomReportItemEntries = new System.Collections.Generic.Dictionary<String, CustomReportItemEntry>(5);
    // list of entries
    // Compression entries
    static CompressionConfig _Compression = null;
    static {
        try
        {
            // Constructor
            String optFileName = AppDomain.CurrentDomain.BaseDirectory + "RdlEngineConfig.xml";
            boolean bLoaded = false;
            try
            {
                XmlDocument xDoc = new XmlDocument();
                xDoc.PreserveWhitespace = false;
                try
                {
                    xDoc.Load(optFileName);
                    bLoaded = true;
                }
                catch (Exception __dummyCatchVar0)
                {
                    String relative = AppDomain.CurrentDomain.RelativeSearchPath;
                    if (relative != null && !StringSupport.equals(relative, String.Empty))
                    {
                        optFileName = AppDomain.CurrentDomain.BaseDirectory + relative + Path.DirectorySeparatorChar + "RdlEngineConfig.xml";
                        try
                        {
                            xDoc.Load(optFileName);
                            bLoaded = true;
                        }
                        catch (Exception __dummyCatchVar1)
                        {
                        }
                    
                    }
                     
                }

                // ok use a hard coded version of the configuration file
                if (!bLoaded)
                    // we couldn't find the configuration so we'll
                    xDoc.InnerXml = "\r\n" + 
                    "<config>\r\n" + 
                    "\t<DataSources>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>MySQL.NET</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>MySql.Data.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>MySql.Data.MySqlClient.MySqlConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect>show tables</TableSelect>\r\n" + 
                    "\t\t\t<Interface>SQL</Interface>\r\n" + 
                    "\t\t\t<ReplaceParameters>true</ReplaceParameters>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>SQL</DataProvider>\r\n" + 
                    "\t\t\t<TableSelect>SELECT TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES ORDER BY 2, 1</TableSelect>\r\n" + 
                    "\t\t\t<Interface>SQL</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>ODBC</DataProvider>\r\n" + 
                    "\t\t\t<TableSelect>SELECT TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES ORDER BY 2, 1</TableSelect>\r\n" + 
                    "\t\t\t<Interface>SQL</Interface>\r\n" + 
                    "\t\t\t<ReplaceParameters>true</ReplaceParameters>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>OLEDB</DataProvider>\r\n" + 
                    "\t\t\t<TableSelect>SELECT TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES ORDER BY 2, 1</TableSelect>\r\n" + 
                    "\t\t\t<Interface>SQL</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>XML</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>DataProviders.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>fyiReporting.Data.XmlConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect></TableSelect>\r\n" + 
                    "\t\t\t<Interface>File</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>WebService</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>DataProviders.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>fyiReporting.Data.WebServiceConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect></TableSelect>\r\n" + 
                    "\t\t\t<Interface>WebService</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>WebLog</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>DataProviders.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>fyiReporting.Data.LogConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect></TableSelect>\r\n" + 
                    "\t\t\t<Interface>File</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>Text</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>DataProviders.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>fyiReporting.Data.TxtConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect></TableSelect>\r\n" + 
                    "\t\t\t<Interface>File</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t\t<DataSource>\r\n" + 
                    "\t\t\t<DataProvider>FileDirectory</DataProvider>\r\n" + 
                    "\t\t\t<CodeModule>DataProviders.dll</CodeModule>\r\n" + 
                    "\t\t\t<ClassName>fyiReporting.Data.FileDirConnection</ClassName>\r\n" + 
                    "\t\t\t<TableSelect></TableSelect>\r\n" + 
                    "\t\t\t<Interface>File</Interface>\r\n" + 
                    "\t\t</DataSource>\r\n" + 
                    "\t</DataSources>\r\n" + 
                    "  <CustomReportItems>\r\n" + 
                    "    <CustomReportItem>\r\n" + 
                    "      <Type>BarCode</Type>\r\n" + 
                    "      <CodeModule>RdlCri.dll</CodeModule>\r\n" + 
                    "      <ClassName>fyiReporting.CRI.BarCode</ClassName>\r\n" + 
                    "    </CustomReportItem>\r\n" + 
                    "  </CustomReportItems>\r\n" + 
                    "</config>";
                 
                XmlNode xNode = new XmlNode();
                xNode = xDoc.SelectSingleNode("//config");
                for (Object __dummyForeachVar0 : xNode.ChildNodes)
                {
                    // Loop thru all the child nodes
                    XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
                    if (xNodeLoop.NodeType != XmlNodeType.Element)
                        continue;
                     
                    Name __dummyScrutVar0 = xNodeLoop.Name;
                    if (__dummyScrutVar0.equals("DataSources"))
                    {
                        getDataSources(xNodeLoop);
                    }
                    else if (__dummyScrutVar0.equals("Compression"))
                    {
                        getCompression(xNodeLoop);
                    }
                    else if (__dummyScrutVar0.equals("CustomReportItems"))
                    {
                        getCustomReportItems(xNodeLoop);
                    }
                    else
                    {
                    }   
                }
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            return ;
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    // Didn't sucessfully get the startup state; but nobody to complain to
    public static CompressionConfig getCompression() throws Exception {
        return _Compression;
    }

    static void getCompression(XmlNode xNode) throws Exception {
        // loop thru looking to process all the datasource elements
        String cm = null;
        String cn = null;
        String fn = null;
        boolean bEnable = true;
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("CodeModule"))
            {
                if (xNodeLoop.InnerText.Length > 0)
                    cm = xNodeLoop.InnerText;
                 
            }
            else if (__dummyScrutVar1.equals("ClassName"))
            {
                if (xNodeLoop.InnerText.Length > 0)
                    cn = xNodeLoop.InnerText;
                 
            }
            else if (__dummyScrutVar1.equals("Finish"))
            {
                if (xNodeLoop.InnerText.Length > 0)
                    fn = xNodeLoop.InnerText;
                 
            }
            else if (__dummyScrutVar1.equals("Enable"))
            {
                if (StringSupport.equals(xNodeLoop.InnerText.ToLower(), "false"))
                    bEnable = false;
                 
            }
                
        }
        if (bEnable)
            _Compression = new CompressionConfig(cm,cn,fn);
         
    }

    static void getDataSources(XmlNode xNode) throws Exception {
        for (Object __dummyForeachVar2 : xNode.ChildNodes)
        {
            // loop thru looking to process all the datasource elements
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar2;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (!StringSupport.equals(xNodeLoop.Name, "DataSource"))
                continue;
             
            getDataSource(xNodeLoop);
        }
    }

    static void getDataSource(XmlNode xNode) throws Exception {
        String provider = null;
        String codemodule = null;
        String cname = null;
        String inter = "SQL";
        String tselect = null;
        boolean replaceparameters = false;
        for (Object __dummyForeachVar3 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar3;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar2 = xNodeLoop.Name;
            if (__dummyScrutVar2.equals("DataProvider"))
            {
                provider = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar2.equals("CodeModule"))
            {
                codemodule = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar2.equals("Interface"))
            {
                inter = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar2.equals("ClassName"))
            {
                cname = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar2.equals("TableSelect"))
            {
                tselect = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar2.equals("ReplaceParameters"))
            {
                if (StringSupport.equals(xNodeLoop.InnerText.ToLower(), "true"))
                    replaceparameters = true;
                 
            }
            else
            {
            }      
        }
        if (provider == null)
            return ;
         
        // nothing to do if no provider specified
        SqlConfigEntry sce;
        try
        {
            // load the module early; saves problems with concurrency later
            String msg = null;
            Assembly la = null;
            if (codemodule != null && cname != null)
            {
                la = XmlUtil.assemblyLoadFrom(codemodule);
                if (la == null)
                    msg = String.Format("{0} could not be loaded", codemodule);
                 
            }
             
            sce = new SqlConfigEntry(provider,cname,la,tselect,msg);
            SqlEntries.Add(provider, sce);
        }
        catch (Exception e)
        {
            // keep exception;  if this DataProvided is ever useed we will see the message
            sce = new SqlConfigEntry(provider, cname, null, tselect, e.Message);
            SqlEntries.Add(provider, sce);
        }

        sce.ReplaceParameters = replaceparameters;
    }

    public static IDbConnection getConnection(String provider, String cstring) throws Exception {
        IDbConnection cn = null;
        System.String.APPLY __dummyScrutVar3 = provider.ToLower();
        if (__dummyScrutVar3.equals("sql"))
        {
            // can't connect unless information provided;
            //   when user wants to set the connection programmatically this they should do this
            if (cstring.Length > 0)
                cn = new SqlConnection(cstring);
             
        }
        else if (__dummyScrutVar3.equals("odbc"))
        {
            cn = new OdbcConnection(cstring);
        }
        else if (__dummyScrutVar3.equals("oledb"))
        {
            cn = new OleDbConnection(cstring);
        }
        else
        {
            SqlConfigEntry sce = SqlEntries[provider] instanceof SqlConfigEntry ? (SqlConfigEntry)SqlEntries[provider] : (SqlConfigEntry)null;
            if (sce == null || sce.CodeModule == null)
            {
                if (sce != null && sce.ErrorMsg != null)
                    throw new Exception(sce.ErrorMsg);
                 
                break;
            }
             
            // error during initialization??
            Object[] args = new Object[]{ cstring };
            Assembly asm = sce.CodeModule;
            Object o = asm.CreateInstance(sce.ClassName, false, BindingFlags.CreateInstance, null, args, null, null);
            if (o == null)
                throw new Exception(String.Format("Unable to create instance of '{0}' for provider '{1}'", sce.ClassName, provider));
             
            cn = o instanceof IDbConnection ? (IDbConnection)o : (IDbConnection)null;
        }   
        return cn;
    }

    static public String getTableSelect(String provider) throws Exception {
        return getTableSelect(provider,null);
    }

    static public boolean doParameterReplacement(String provider, IDbConnection cn) throws Exception {
        SqlConfigEntry sce = SqlEntries[provider] instanceof SqlConfigEntry ? (SqlConfigEntry)SqlEntries[provider] : (SqlConfigEntry)null;
        return sce == null ? false : sce.ReplaceParameters;
    }

    static public String getTableSelect(String provider, IDbConnection cn) throws Exception {
        SqlConfigEntry sce = SqlEntries[provider] instanceof SqlConfigEntry ? (SqlConfigEntry)SqlEntries[provider] : (SqlConfigEntry)null;
        if (sce == null)
        {
            if (cn != null)
            {
                OdbcConnection oc = cn instanceof OdbcConnection ? (OdbcConnection)cn : (OdbcConnection)null;
                if (oc != null && oc.Driver.ToLower().IndexOf("my") >= 0)
                    return "show tables";
                 
            }
             
            return "SELECT TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES ORDER BY 2, 1";
        }
         
        // not a good way but ...
        // mysql syntax is non-standard
        if (cn != null)
        {
            OdbcConnection oc = cn instanceof OdbcConnection ? (OdbcConnection)cn : (OdbcConnection)null;
            if (oc != null && oc.Driver.ToLower().IndexOf("my") >= 0)
                return "show tables";
             
        }
         
        return sce.TableSelect;
    }

    // not a good way but ...
    // mysql syntax is non-standard
    static public String[] getProviders() throws Exception {
        if (SqlEntries.Count == 0)
            return null;
         
        String[] items = new String[SqlEntries.Count];
        int i = 0;
        for (Object __dummyForeachVar4 : SqlEntries.Values)
        {
            SqlConfigEntry sce = (SqlConfigEntry)__dummyForeachVar4;
            items[i++] = sce.Provider;
        }
        return items;
    }

    static public String[] getCustomReportTypes() throws Exception {
        if (CustomReportItemEntries.Count == 0)
            return null;
         
        String[] items = new String[CustomReportItemEntries.Count];
        int i = 0;
        for (Object __dummyForeachVar5 : CustomReportItemEntries.Values)
        {
            CustomReportItemEntry crie = (CustomReportItemEntry)__dummyForeachVar5;
            items[i++] = crie.Type;
        }
        return items;
    }

    static void getCustomReportItems(XmlNode xNode) throws Exception {
        for (Object __dummyForeachVar6 : xNode.ChildNodes)
        {
            // loop thru looking to process all the datasource elements
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar6;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (!StringSupport.equals(xNodeLoop.Name, "CustomReportItem"))
                continue;
             
            getCustomReportItem(xNodeLoop);
        }
    }

    static void getCustomReportItem(XmlNode xNode) throws Exception {
        String type = null;
        String codemodule = null;
        String classname = null;
        for (Object __dummyForeachVar7 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar7;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar4 = xNodeLoop.Name;
            if (__dummyScrutVar4.equals("Type"))
            {
                type = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar4.equals("CodeModule"))
            {
                codemodule = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar4.equals("ClassName"))
            {
                classname = xNodeLoop.InnerText;
            }
            else
            {
            }   
        }
        if (type == null)
            return ;
         
        // nothing to do if no provider specified
        CustomReportItemEntry crie;
        try
        {
            // load the module early; saves problems with concurrency later
            String msg = null;
            Assembly la = null;
            if (codemodule != null && classname != null)
            {
                la = XmlUtil.assemblyLoadFrom(codemodule);
                if (la == null)
                    msg = String.Format("{0} could not be loaded", codemodule);
                 
            }
             
            crie = new CustomReportItemEntry(type,classname,la,msg);
            CustomReportItemEntries.Add(type, crie);
        }
        catch (Exception e)
        {
            // keep exception;  if this CustomReportItem is ever used we will see the message
            crie = new CustomReportItemEntry(type, classname, null, e.Message);
            CustomReportItemEntries.Add(type, crie);
        }
    
    }

    public static ICustomReportItem createCustomReportItem(String type) throws Exception {
        CustomReportItemEntry crie = null;
        try
        {
            crie = CustomReportItemEntries[type];
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new Exception(String.Format("{0} is not a known CustomReportItem type", type));
        }

        // KeyNotFoundException typically
        if (crie.CodeModule == null)
        {
            if (crie != null && crie.ErrorMsg != null)
                throw new Exception(crie.ErrorMsg);
            else
                throw new Exception(String.Format("{0} is not a known CustomReportItem type", type)); 
        }
         
        // error during initialization??
        Assembly asm = crie.CodeModule;
        Object o = asm.CreateInstance(crie.ClassName, false, BindingFlags.CreateInstance, null, null, null, null);
        if (o == null)
            throw new Exception(String.Format("Unable to create instance of '{0}' for CustomReportType '{1}'", crie.ClassName, type));
         
        return o instanceof ICustomReportItem ? (ICustomReportItem)o : (ICustomReportItem)null;
    }

}


