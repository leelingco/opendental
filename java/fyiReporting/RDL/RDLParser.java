//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RDL.ParserException;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLog;

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
* The RDLParser class takes an XML representation (either string or DOM) of a
* RDL file and compiles a Report.
*/
public class RDLParser   
{
    XmlDocument _RdlDocument = new XmlDocument();
    // the RDL XML syntax
    boolean bPassed = false;
    // has Report passed definition
    fyiReporting.RDL.Report _Report = null;
    // The report; complete if bPassed true
    NeedPassword _DataSourceReferencePassword;
    // password for decrypting data source reference file
    String _Folder = new String();
    // folder that will contain report; needed when DataSourceReference used
    /**
    * RDLParser takes in an RDL XML file and creates the
    * definition that will be used at runtime.  It validates
    * that the syntax is correct according to the specification.
    */
    public RDLParser(String xml) throws Exception {
        try
        {
            _RdlDocument = new XmlDocument();
            _RdlDocument.PreserveWhitespace = false;
            _RdlDocument.LoadXml(xml);
        }
        catch (XmlException ex)
        {
            throw new ParserException("Error: XML failed " + ex.Message);
        }
    
    }

    /**
    * RDLParser takes in an RDL XmlDocument and creates the
    * definition that will be used at runtime.  It validates
    * that the syntax is correct according to the specification.
    */
    public RDLParser(XmlDocument xml) throws Exception {
        // preparsed XML
        _RdlDocument = xml;
    }

    public XmlDocument getRdlDocument() throws Exception {
        return _RdlDocument;
    }

    public void setRdlDocument(XmlDocument value) throws Exception {
        // With a new document existing report is not valid
        _RdlDocument = value;
        bPassed = false;
        _Report = null;
    }

    /**
    * Get the compiled report.
    */
    // Only return a report if it has been fully constructed
    public fyiReporting.RDL.Report getReport() throws Exception {
        if (bPassed)
            return _Report;
        else
            return null; 
    }

    /**
    * Returns a parsed RPL report instance.
    * 
    *  @return A Report instance.
    */
    public fyiReporting.RDL.Report parse() throws Exception {
        return parse(0);
    }

    public fyiReporting.RDL.Report parse(int oc) throws Exception {
        if (_RdlDocument == null)
            return null;
        else // no document?
        // nothing to do
        if (bPassed)
            return _Report;
          
        // If I've already parsed it
        // then return existing Report
        //  Need to create a report.
        XmlNode xNode = new XmlNode();
        xNode = _RdlDocument.LastChild;
        if (xNode == null || !StringSupport.equals(xNode.Name, "Report"))
        {
            throw new ParserException("Error: RDL doesn't contain a report element. ");
        }
         
        ReportLog rl = new ReportLog();
        // create a report log
        ReportDefn rd = new ReportDefn(xNode,rl,this._Folder,this._DataSourceReferencePassword,oc);
        _Report = new fyiReporting.RDL.Report(rd);
        bPassed = true;
        return _Report;
    }

    /**
    * For shared data sources, the DataSourceReferencePassword is the user phrase
    * used to decrypt the report.
    */
    public NeedPassword getDataSourceReferencePassword() throws Exception {
        return _DataSourceReferencePassword;
    }

    public void setDataSourceReferencePassword(NeedPassword value) throws Exception {
        _DataSourceReferencePassword = value;
    }

    /**
    * Folder is the location of the report.
    */
    public String getFolder() throws Exception {
        return _Folder;
    }

    public void setFolder(String value) throws Exception {
        _Folder = value;
    }

}


