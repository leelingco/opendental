//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DrillthroughParameters;
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
* Defines information needed for creating links to URLs in a report.  Primarily HTML.
*/
public class Drillthrough  extends ReportLink 
{
    String _ReportName = new String();
    // URL The path of the drillthrough report. Paths may be
    // absolute or relative.
    DrillthroughParameters _DrillthroughParameters;
    // Parameters to the drillthrough report
    public Drillthrough(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _ReportName = null;
        _DrillthroughParameters = null;
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
                _DrillthroughParameters = new DrillthroughParameters(r,this,xNodeLoop);
            }
            else
            {
            }  
        }
        if (_ReportName == null)
            OwnerReport.rl.logError(8,"Drillthrough requires the ReportName element.");
         
    }

    public void finalPass() throws Exception {
        if (_DrillthroughParameters != null)
            _DrillthroughParameters.finalPass();
         
        return ;
    }

    public String getReportName() throws Exception {
        return _ReportName;
    }

    public void setReportName(String value) throws Exception {
        _ReportName = value;
    }

    public DrillthroughParameters getDrillthroughParameters() throws Exception {
        return _DrillthroughParameters;
    }

    public void setDrillthroughParameters(DrillthroughParameters value) throws Exception {
        _DrillthroughParameters = value;
    }

}


