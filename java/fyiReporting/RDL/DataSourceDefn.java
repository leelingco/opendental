//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.ConnectionProperties;
import fyiReporting.RDL.DataSourceDefn;
import fyiReporting.RDL.DataSourceReference;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
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
* Information about the data source (e.g. a database connection string).
*/
public class DataSourceDefn  extends ReportLink 
{
    Name _Name;
    // The name of the data source
    // Must be unique within the report
    boolean _Transaction = new boolean();
    // Indicates the data sets that use this data
    // source should be executed in a single transaction.
    ConnectionProperties _ConnectionProperties;
    //Information about how to connect to the data source
    String _DataSourceReference = new String();
    //The full path (e.g.
    // “/salesreports/salesdatabase”) or relative path
    // (e.g. “salesdatabase”) to a data source
    // reference. Relative paths start in the same
    // location as the report.
    IDbConnection _ParseConnection = new IDbConnection();
    // while parsing we sometimes need to connect
    public DataSourceDefn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Transaction = false;
        _ConnectionProperties = null;
        _DataSourceReference = null;
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
            if (__dummyScrutVar1.equals("Transaction"))
            {
                _Transaction = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("ConnectionProperties"))
            {
                _ConnectionProperties = new ConnectionProperties(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("DataSourceReference"))
            {
                _DataSourceReference = xNodeLoop.InnerText;
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown DataSource element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_Name == null)
            OwnerReport.rl.logError(8,"DataSource Name is required but not specified.");
        else if (_ConnectionProperties == null && _DataSourceReference == null)
            OwnerReport.rl.LogError(8, String.Format("Either ConnectionProperties or DataSourceReference must be specified for DataSource {0}.", this._Name.getNm()));
        else if (_ConnectionProperties != null && _DataSourceReference != null)
            OwnerReport.rl.LogError(8, String.Format("Either ConnectionProperties or DataSourceReference must be specified for DataSource {0} but not both.", this._Name.getNm()));
           
    }

    public void finalPass() throws Exception {
        if (_ConnectionProperties != null)
            _ConnectionProperties.finalPass();
         
        connectDataSource(null);
        return ;
    }

    public boolean isConnected(fyiReporting.RDL.Report rpt) throws Exception {
        return getConnection(rpt) == null ? false : true;
    }

    public boolean areSameDataSource(DataSourceDefn dsd) throws Exception {
        if (this.getDataSourceReference() != null && StringSupport.equals(this.getDataSourceReference(), dsd.getDataSourceReference()))
            return true;
         
        // datasource references are the same
        if (this.getConnectionProperties() == null || dsd.getConnectionProperties() == null)
            return false;
         
        ConnectionProperties cp1 = this.getConnectionProperties();
        ConnectionProperties cp2 = dsd.getConnectionProperties();
        return (StringSupport.equals(cp1.getDataProvider(), cp2.getDataProvider()) && StringSupport.equals(cp1.getConnectstringValue(), cp2.getConnectstringValue()) && cp1.getIntegratedSecurity() == cp2.getIntegratedSecurity());
    }

    public boolean connectDataSource(fyiReporting.RDL.Report rpt) throws Exception {
        IDbConnection cn = getConnection(rpt);
        if (cn != null)
            return true;
         
        if (_DataSourceReference != null)
            connectDataSourceReference(rpt);
         
        // this will create a _ConnectionProperties
        if (_ConnectionProperties == null || _ConnectionProperties.getConnectstringValue() == null)
            return false;
         
        boolean rc = false;
        try
        {
            cn = RdlEngineConfig.getConnection(_ConnectionProperties.getDataProvider(),_ConnectionProperties.connectstring(rpt));
            if (cn != null)
            {
                cn.Open();
                rc = true;
            }
             
        }
        catch (Exception e)
        {
            String err = String.Format("DataSource '{0}'.\r\n{1}", _Name, e.InnerException == null ? e.Message : e.InnerException.Message);
            if (rpt == null)
                OwnerReport.rl.logError(4,err);
            else
                // error occurred during parse phase
                rpt.rl.logError(4,err); 
            if (cn != null)
            {
                cn.Close();
                cn = null;
            }
             
        }

        if (cn != null)
            setSysConnection(rpt,cn);
        else
        {
            String err = String.Format("Unable to connect to datasource '{0}'.", this._Name.getNm());
            if (rpt == null)
                OwnerReport.rl.logError(4,err);
            else
                // error occurred during parse phase
                rpt.rl.logError(4,err); 
        } 
        return rc;
    }

    void connectDataSourceReference(fyiReporting.RDL.Report rpt) throws Exception {
        if (_ConnectionProperties != null)
            return ;
         
        try
        {
            String file = new String();
            String folder = rpt == null ? OwnerReport.getParseFolder() : rpt.getFolder();
            if (_DataSourceReference[0] != Path.DirectorySeparatorChar)
                file = folder + Path.DirectorySeparatorChar + _DataSourceReference + ".dsr";
            else
                file = folder + _DataSourceReference + ".dsr"; 
            String pswd = OwnerReport.GetDataSourceReferencePassword == null ? null : OwnerReport.GetDataSourceReferencePassword.invoke();
            if (pswd == null)
                throw new Exception("No password provided for shared DataSource reference");
             
            String xml = DataSourceReference.retrieve(file,pswd);
            XmlDocument xDoc = new XmlDocument();
            xDoc.LoadXml(xml);
            XmlNode xNodeLoop = xDoc.FirstChild;
            _ConnectionProperties = new ConnectionProperties(OwnerReport,this,xNodeLoop);
            _ConnectionProperties.finalPass();
        }
        catch (Exception e)
        {
            OwnerReport.rl.LogError(4, e.Message);
            _ConnectionProperties = null;
        }

        return ;
    }

    public boolean isUserConnection(fyiReporting.RDL.Report rpt) throws Exception {
        if (rpt == null)
            return false;
         
        Object uc = rpt.getCache().get(this,"UserConnection");
        return uc == null ? false : true;
    }

    public void setUserConnection(fyiReporting.RDL.Report rpt, IDbConnection cn) throws Exception {
        if (cn == null)
            rpt.getCache().remove(this,"UserConnection");
        else
            rpt.getCache().add(this,"UserConnection",cn); 
    }

    private void setSysConnection(fyiReporting.RDL.Report rpt, IDbConnection cn) throws Exception {
        if (rpt == null)
            _ParseConnection = cn;
        else if (cn == null)
            rpt.getCache().remove(this,"SysConnection");
        else
            rpt.getCache().add(this,"SysConnection",cn);  
    }

    public IDbConnection getConnection(fyiReporting.RDL.Report rpt) throws Exception {
        IDbConnection cn = new IDbConnection();
        if (rpt == null)
            return _ParseConnection;
         
        cn = rpt.getCache().get(this,"UserConnection") instanceof IDbConnection ? (IDbConnection)rpt.getCache().get(this,"UserConnection") : (IDbConnection)null;
        if (cn == null)
        {
            cn = rpt.getCache().get(this,"SysConnection") instanceof IDbConnection ? (IDbConnection)rpt.getCache().get(this,"SysConnection") : (IDbConnection)null;
        }
         
        return cn;
    }

    public void cleanUp(fyiReporting.RDL.Report rpt) throws Exception {
        if (isUserConnection(rpt))
            return ;
         
        IDbConnection cn = getConnection(rpt);
        if (cn == null)
            return ;
         
        try
        {
            cn.Close();
        }
        catch (Exception ex)
        {
            // cn.Dispose();		// not good for connection pooling
            // report the error but keep going
            if (rpt != null)
                rpt.rl.LogError(4, String.Format("Error closing connection. {0}", ex.Message));
            else
                this.OwnerReport.rl.LogError(4, String.Format("Error closing connection. {0}", ex.Message)); 
        }

        setSysConnection(rpt,null);
        return ;
    }

    // get rid of connection from cache
    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public boolean getTransaction() throws Exception {
        return _Transaction;
    }

    public void setTransaction(boolean value) throws Exception {
        _Transaction = value;
    }

    public ConnectionProperties getConnectionProperties() throws Exception {
        return _ConnectionProperties;
    }

    public void setConnectionProperties(ConnectionProperties value) throws Exception {
        _ConnectionProperties = value;
    }

    public IDbConnection sqlConnect(fyiReporting.RDL.Report rpt) throws Exception {
        return getConnection(rpt);
    }

    public String getDataSourceReference() throws Exception {
        return _DataSourceReference;
    }

    public void setDataSourceReference(String value) throws Exception {
        _DataSourceReference = value;
    }

}


