//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Name;
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
* ReportClass represents the Class report element.
*/
public class ReportClass  extends ReportLink 
{
    String _ClassName = new String();
    // The name of the class
    Name _InstanceName;
    // The name of the variable to assign the class to.
    // This variable can be used in expressions
    // throughout the report.
    public ReportClass(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _ClassName = null;
        _InstanceName = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("ClassName"))
            {
                _ClassName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("InstanceName"))
            {
                _InstanceName = new Name(xNodeLoop.InnerText);
            }
            else
            {
            }  
        }
        if (_ClassName == null)
            OwnerReport.rl.logError(8,"Class ClassName is required but not specified.");
         
        if (_InstanceName == null)
            OwnerReport.rl.logError(8,"Class InstanceName is required but not specified or invalid for " + _ClassName == null ? "<unknown name>" : _ClassName);
         
    }

    public void finalPass() throws Exception {
        return ;
    }

    public Object load(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        if (wc.bCreateFailed)
            return wc.Instance;
         
        // We only try to create once.
        if (wc.Instance != null)
            return wc.Instance;
         
        // Already loaded
        if (OwnerReport.getCodeModules() == null)
            return null;
         
        // nothing to load against
        // Load an instance of the object
        String err = "";
        try
        {
            Type tp = OwnerReport.getCodeModules().get___idx(_ClassName);
            if (tp != null)
            {
                Assembly asm = tp.Assembly;
                wc.Instance = asm.CreateInstance(_ClassName, false);
            }
            else
                err = "Class not found."; 
        }
        catch (Exception e)
        {
            wc.Instance = null;
            err = e.Message;
        }

        if (wc.Instance == null)
        {
            String e = String.Format("Unable to create instance of class {0}.  {1}", _ClassName, err);
            if (rpt == null)
                OwnerReport.rl.logError(4,e);
            else
                rpt.rl.logError(4,e); 
            wc.bCreateFailed = true;
        }
         
        return wc.Instance;
    }

    public String getClassName() throws Exception {
        return _ClassName;
    }

    public Name getInstanceName() throws Exception {
        return _InstanceName;
    }

    public Object instance(fyiReporting.RDL.Report rpt) throws Exception {
        return load(rpt);
    }

    // load if necessary
    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        if (rpt == null)
            return new WorkClass();
         
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public Object Instance = new Object();
        public boolean bCreateFailed = new boolean();
        public WorkClass() throws Exception {
            Instance = null;
            //
            bCreateFailed = false;
        }
    
    }

}


