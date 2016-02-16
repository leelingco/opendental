//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
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
* Information about how to connect to the data source.
*/
public class ConnectionProperties  extends ReportLink 
{
    String _DataProvider = new String();
    // The type of the data source. This will determine
    // the syntax of the Connectstring and
    // CommandText. Supported types are SQL, OLEDB, ODBC, Oracle
    Expression _ConnectString;
    // The connection string for the data source
    boolean _IntegratedSecurity = new boolean();
    // Indicates that this data source should connected
    // to using integrated security
    String _Prompt = new String();
    // The prompt displayed to the user when
    // prompting for database credentials for this data source.
    public ConnectionProperties(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DataProvider = null;
        _ConnectString = null;
        _IntegratedSecurity = false;
        _Prompt = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DataProvider"))
            {
                _DataProvider = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("ConnectString"))
            {
                _ConnectString = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("IntegratedSecurity"))
            {
                _IntegratedSecurity = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Prompt"))
            {
                _Prompt = xNodeLoop.InnerText;
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ConnectionProperties element '" + xNodeLoop.Name + "' ignored.");
            }    
        }
        if (_DataProvider == null)
            OwnerReport.rl.logError(8,"ConnectionProperties DataProvider is required.");
         
        if (_ConnectString == null)
            OwnerReport.rl.logError(8,"ConnectionProperties ConnectString is required.");
         
    }

    public void finalPass() throws Exception {
        if (_ConnectString != null)
            _ConnectString.finalPass();
         
        return ;
    }

    public String getDataProvider() throws Exception {
        return _DataProvider;
    }

    public void setDataProvider(String value) throws Exception {
        _DataProvider = value;
    }

    public String connectstring(fyiReporting.RDL.Report rpt) throws Exception {
        return _ConnectString.evaluateString(rpt,null);
    }

    public String getConnectstringValue() throws Exception {
        return _ConnectString == null ? null : _ConnectString.getSource();
    }

    public boolean getIntegratedSecurity() throws Exception {
        return _IntegratedSecurity;
    }

    public void setIntegratedSecurity(boolean value) throws Exception {
        _IntegratedSecurity = value;
    }

    public String getPrompt() throws Exception {
        return _Prompt;
    }

    public void setPrompt(String value) throws Exception {
        _Prompt = value;
    }

}


