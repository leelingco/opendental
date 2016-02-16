//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataSourcesDefn;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RDLParser;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.SubreportParameter;
import fyiReporting.RDL.SubReportParameters;
import fyiReporting.RDL.UserReportParameter;
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
* The definition of a Subreport (report name, parameters, ...).
*/
public class Subreport  extends ReportItem 
{
    String _ReportName = new String();
    // The full path (e.g. “/salesreports/orderdetails”) or
    // relative path (e.g. “orderdetails”) to a subreport.
    // Relative paths start in the same folder as the current
    // Report output formats unable to support FitProportional or Clip should output as Fit instead.
    // (parent) report.
    // Cannot be an empty string (ignoring whitespace)
    SubReportParameters _Parameters;
    //Parameters to the Subreport
    // If the subreport is executed without parameters
    // (and contains no Toggle elements), it will only be
    // executed once (even if it appears inside of a list,
    // table or matrix)
    Expression _NoRows;
    // (string)	Message to display in the subreport (instead of the
    // region layout) when no rows of data are available
    // in any data set in the subreport
    // Note: Style information on the subreport applies to
    // this text.
    boolean _MergeTransactions = new boolean();
    // Indicates that transactions in the subreport should
    //be merged with transactions in the parent report
    //(into a single transaction for the entire report) if the
    //data sources use the same connection.
    ReportDefn _ReportDefn;
    // loaded report definition
    public Subreport(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _ReportName = null;
        _Parameters = null;
        _NoRows = null;
        _MergeTransactions = true;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("ReportName"))
            {
                _ReportName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("Parameters"))
            {
                _Parameters = new SubReportParameters(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("NoRows"))
            {
                _NoRows = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("MergeTransactions"))
            {
                _MergeTransactions = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (reportItemElement(xNodeLoop))
                    break;
                 
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Image element " + xNodeLoop.Name + " ignored.");
            }    
        }
        if (_ReportName == null)
            OwnerReport.rl.logError(8,"Subreport requires the ReportName element.");
         
        OwnerReport.setContainsSubreport(true);
    }

    // owner report contains a subreport
    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        super.finalPass();
        // Subreports aren't allowed in PageHeader or PageFooter;
        if (this.inPageHeaderOrFooter())
            OwnerReport.rl.LogError(8, String.Format("The Subreport '{0}' is not allowed in a PageHeader or PageFooter", this.getName() == null ? "unknown" : getName().getNm()));
         
        if (_Parameters != null)
            _Parameters.finalPass();
         
        if (_NoRows != null)
            _NoRows.finalPass();
         
        _ReportDefn = getReport(OwnerReport.getParseFolder());
        if (_ReportDefn != null)
            // only null in error case (e.g. subreport not found)
            _ReportDefn.setSubreport(this);
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report r = ip.report();
        super.run(ip,row);
        // need to save the owner report and nest in this defintion
        ReportDefn saveReport = r.getReportDefinition();
        r.setReportDefinition(_ReportDefn);
        r.setFolder(_ReportDefn.getParseFolder());
        // folder needs to get set since the id of the report is used by the cache
        DataSourcesDefn saveDS = r.getParentConnections();
        if (this.getMergeTransactions())
            r.setParentConnections(saveReport.getDataSourcesDefn());
        else
            r.setParentConnections(null); 
        if (_Parameters == null)
        {
            // When no parameters we only retrieve data once
            if (r.getCache().get(this,"report") == null)
            {
                r.runGetData(null);
                r.getCache().add(this,"report",this);
            }
             
        }
        else
        {
            setSubreportParameters(r,row);
            r.runGetData(null);
        } 
        ip.subreport(this,row);
        r.setReportDefinition(saveReport);
        // restore the current report
        r.setParentConnections(saveDS);
    }

    // restore the data connnections
    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        super.runPage(pgs,row);
        // need to save the owner report and nest in this defintion
        ReportDefn saveReport = r.getReportDefinition();
        r.setReportDefinition(_ReportDefn);
        r.setFolder(_ReportDefn.getParseFolder());
        // folder needs to get set since the id of the report is used by the cache
        DataSourcesDefn saveDS = r.getParentConnections();
        if (this.getMergeTransactions())
            r.setParentConnections(saveReport.getDataSourcesDefn());
        else
            r.setParentConnections(null); 
        if (_Parameters == null)
        {
            // When no parameters we only retrieve data once
            if (r.getCache().get(this,"report") == null)
            {
                r.runGetData(null);
                r.getCache().add(this,"report",this);
            }
             
        }
        else
        {
            // just put something in cache to remember
            setSubreportParameters(r,row);
            // apply the parameters
            r.runGetData(null);
        } 
        setPageLeft(r);
        // Set the Left attribute since this will be the margin for this report
        setPagePositionBegin(pgs);
        //
        // Run the subreport -- this is the major effort in creating the display objects in the page
        //
        r.getReportDefinition().getBody().runPage(pgs);
        // create a the subreport items
        r.setReportDefinition(saveReport);
        // restore the current report
        r.setParentConnections(saveDS);
        // restore the data connnections
        setPagePositionEnd(pgs,pgs.getCurrentPage().getYOffset());
    }

    private ReportDefn getReport(String folder) throws Exception {
        String prog = new String();
        String name = new String();
        if (_ReportName[0] == Path.DirectorySeparatorChar || _ReportName[0] == Path.AltDirectorySeparatorChar)
            name = _ReportName;
        else
            name = folder + Path.DirectorySeparatorChar + _ReportName; 
        name = name + ".rdl";
        // TODO: shouldn't necessarily require this extension
        // Load and Compile the report
        RDLParser rdlp;
        fyiReporting.RDL.Report r;
        ReportDefn rdefn = null;
        try
        {
            prog = getRdlSource(name);
            rdlp = new RDLParser(prog);
            rdlp.setFolder(folder);
            r = rdlp.parse(OwnerReport.getObjectNumber());
            OwnerReport.setObjectNumber(r.getReportDefinition().getObjectNumber());
            if (r.getErrorMaxSeverity() > 0)
            {
                String err = new String();
                if (r.getErrorMaxSeverity() > 4)
                    err = String.Format("Subreport {0} failed to compile with the following errors.", this._ReportName);
                else
                    err = String.Format("Subreport {0} compiled with the following warnings.", this._ReportName); 
                OwnerReport.rl.logError(r.getErrorMaxSeverity(),err);
                OwnerReport.rl.logError(r.rl);
                // log all these errors
                OwnerReport.rl.logError(0,"End of Subreport errors");
            }
             
            // If we've loaded the report; we should tell it where it got loaded from
            if (r.getErrorMaxSeverity() <= 4)
            {
                rdefn = r.getReportDefinition();
            }
             
        }
        catch (Exception ex)
        {
            OwnerReport.rl.LogError(8, String.Format("Subreport {0} failed with exception. {1}", this._ReportName, ex.Message));
        }

        return rdefn;
    }

    private String getRdlSource(String name) throws Exception {
        // TODO: at some point might want to provide interface so that read can be controlled
        //         by server:  would allow for caching etc.
        StreamReader fs = null;
        String prog = null;
        try
        {
            fs = new StreamReader(name);
            prog = fs.ReadToEnd();
        }
        finally
        {
            if (fs != null)
                fs.Close();
             
        }
        return prog;
    }

    private void setSubreportParameters(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        UserReportParameter userp;
        for (Object __dummyForeachVar2 : _Parameters.getItems())
        {
            SubreportParameter srp = (SubreportParameter)__dummyForeachVar2;
            userp = null;
            for (Object __dummyForeachVar1 : rpt.getUserReportParameters())
            {
                UserReportParameter urp = (UserReportParameter)__dummyForeachVar1;
                if (StringSupport.equals(urp.getName(), srp.getName().getNm()))
                {
                    userp = urp;
                    break;
                }
                 
            }
            if (userp == null)
            {
                throw new Exception(String.Format("Subreport {0} doesn't define parameter {1}.", _ReportName, srp.getName().getNm()));
            }
             
            // parameter name not found
            Object v = srp.getValue().evaluate(rpt,row);
            userp.setValue(v);
        }
    }

    public String getReportName() throws Exception {
        return _ReportName;
    }

    public void setReportName(String value) throws Exception {
        _ReportName = value;
    }

    public ReportDefn getReportDefn() throws Exception {
        return _ReportDefn;
    }

    public SubReportParameters getParameters() throws Exception {
        return _Parameters;
    }

    public void setParameters(SubReportParameters value) throws Exception {
        _Parameters = value;
    }

    public Expression getNoRows() throws Exception {
        return _NoRows;
    }

    public void setNoRows(Expression value) throws Exception {
        _NoRows = value;
    }

    public boolean getMergeTransactions() throws Exception {
        return _MergeTransactions;
    }

    public void setMergeTransactions(boolean value) throws Exception {
        _MergeTransactions = value;
    }

}


