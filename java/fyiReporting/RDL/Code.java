//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.Parser;
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
* Code represents the Code report element.
*/
public class Code  extends ReportLink 
{
    String _Source = new String();
    // The source code
    String _Classname = new String();
    // Class name of generated class
    Assembly _Assembly = new Assembly();
    // the compiled assembly
    public Code(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Source = xNode.InnerText;
        _Assembly = getAssembly();
    }

    public void finalPass() throws Exception {
        return ;
    }

    private Assembly getAssembly() throws Exception {
        // Generate the proxy source code
        List<String> lines = new List<String>();
        // hold lines in array in case of error
        VBCodeProvider vbcp = new VBCodeProvider();
        StringBuilder sb = new StringBuilder();
        //  Generate code with the following general form
        //Imports System
        //Namespace fyiReportingvbgen
        //Public Class MyClassn	   // where n is a uniquely generated integer
        //Sub New()
        //End Sub
        //  ' this is the code in the <Code> tag
        //End Class
        //End Namespace
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        String unique = Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        lines.Add("Imports System");
        lines.Add("Namespace fyiReporting.vbgen");
        _Classname = "MyClass" + unique;
        lines.Add("Public Class " + _Classname);
        lines.Add("Sub New()");
        lines.Add("End Sub");
        // Read and write code as lines
        StringReader tr = new StringReader(_Source);
        while (tr.Peek() >= 0)
        {
            String line = tr.ReadLine();
            lines.Add(line);
        }
        tr.Close();
        lines.Add("End Class");
        lines.Add("End Namespace");
        for (Object __dummyForeachVar0 : lines)
        {
            String l = (String)__dummyForeachVar0;
            sb.AppendFormat(l + "\r\n");
        }
        String vbcode = sb.ToString();
        // debug code !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //						StreamWriter tsw = File.CreateText(@"c:\temp\vbcode.txt");
        //						tsw.Write(vbcode);
        //						tsw.Close();
        // debug code !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Create Assembly
        CompilerParameters cp = new CompilerParameters();
        cp.ReferencedAssemblies.Add("System.dll");
        cp.GenerateExecutable = false;
        cp.GenerateInMemory = false;
        // just loading into memory causes problems when instantiating
        cp.IncludeDebugInformation = false;
        CompilerResults cr = vbcp.CompileAssemblyFromSource(cp, vbcode);
        if (cr.Errors.Count > 0)
        {
            StringBuilder err = new StringBuilder(String.Format("Code element has {0} error(s).  Line numbers are relative to Code element.", cr.Errors.Count));
            for (Object __dummyForeachVar1 : cr.Errors)
            {
                CompilerError ce = (CompilerError)__dummyForeachVar1;
                String l = new String();
                if (ce.Line >= 1 && ce.Line <= lines.Count)
                    l = lines[ce.Line - 1] instanceof String ? (String)lines[ce.Line - 1] : (String)null;
                else
                    l = "Unknown"; 
                err.AppendFormat("\r\nLine {0} '{1}' : {2} {3}", ce.Line - 5, l, ce.ErrorNumber, ce.ErrorText);
            }
            this.OwnerReport.rl.LogError(4, err.ToString());
            return null;
        }
         
        return Assembly.LoadFrom(cr.PathToAssembly);
    }

    // We need an assembly loaded from the file system
    //   or instantiation of object complains
    public Type codeType() throws Exception {
        if (_Assembly == null)
            return null;
         
        Type t = null;
        try
        {
            Object instance = _Assembly.CreateInstance("fyiReporting.vbgen." + this._Classname, false);
            t = instance.GetType();
        }
        catch (Exception e)
        {
            OwnerReport.rl.LogError(4, String.Format("Unable to load instance of Code\r\n{0}", e.Message));
        }

        return t;
    }

    public Object load(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        if (wc.bCreateFailed)
            return wc.Instance;
         
        // We only try to create once.
        if (wc.Instance != null)
            return wc.Instance;
         
        // Already loaded
        if (_Assembly == null)
        {
            wc.bCreateFailed = true;
            return null;
        }
         
        // we don't have an assembly
        // Load an instance of the object
        String err = "";
        try
        {
            wc.Instance = _Assembly.CreateInstance("fyiReporting.vbgen." + this._Classname, false);
        }
        catch (Exception e)
        {
            wc.Instance = null;
            err = e.Message;
        }

        if (wc.Instance == null)
        {
            String e = String.Format("Unable to create instance of local code class.\r\n{0}", err);
            if (rpt == null)
                OwnerReport.rl.logError(4,e);
            else
                rpt.rl.logError(4,e); 
            wc.bCreateFailed = true;
        }
         
        return wc.Instance;
    }

    public String getSource() throws Exception {
        return _Source;
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


