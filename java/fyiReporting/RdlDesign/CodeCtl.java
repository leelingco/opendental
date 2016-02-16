//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RdlDesign.CodeCtl;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.IProperty;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Summary description for CodeCtl.
*/
public class CodeCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    static public long Counter = new long();
    // counter used for unique expression count
    private DesignXmlDraw _Draw;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bCheckSyntax = new System.Windows.Forms.Button();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.TextBox tbCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox lbErrors = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Splitter splitter1 = new System.Windows.Forms.Splitter();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public CodeCtl(DesignXmlDraw dxDraw) throws Exception {
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        XmlNode cNode = _Draw.getNamedChildNode(rNode,"Code");
        tbCode.Text = "";
        if (cNode == null)
            return ;
         
        StringReader tr = new StringReader(cNode.InnerText);
        List<String> ar = new List<String>();
        while (tr.Peek() >= 0)
        {
            String line = tr.ReadLine();
            ar.Add(line);
        }
        tr.Close();
        //    tbCode.Lines = ar.ToArray("".GetType()) as string[];
        tbCode.Lines = ar.ToArray();
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.label1 = new System.Windows.Forms.Label();
        this.bCheckSyntax = new System.Windows.Forms.Button();
        this.panel1 = new System.Windows.Forms.Panel();
        this.tbCode = new System.Windows.Forms.TextBox();
        this.splitter1 = new System.Windows.Forms.Splitter();
        this.lbErrors = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(98, 6);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(144, 16);
        this.label1.TabIndex = 0;
        this.label1.Text = "Visual Basic Function Code";
        //
        // bCheckSyntax
        //
        this.bCheckSyntax.Location = new System.Drawing.Point(365, 3);
        this.bCheckSyntax.Name = "bCheckSyntax";
        this.bCheckSyntax.Size = new System.Drawing.Size(82, 23);
        this.bCheckSyntax.TabIndex = 2;
        this.bCheckSyntax.Text = "Check Syntax";
        this.bCheckSyntax.Click += new System.EventHandler(this.bCheckSyntax_Click);
        //
        // panel1
        //
        this.panel1.Controls.Add(this.tbCode);
        this.panel1.Controls.Add(this.splitter1);
        this.panel1.Controls.Add(this.lbErrors);
        this.panel1.Location = new System.Drawing.Point(13, 31);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(439, 252);
        this.panel1.TabIndex = 4;
        //
        // tbCode
        //
        this.tbCode.AcceptsReturn = true;
        this.tbCode.AcceptsTab = true;
        this.tbCode.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tbCode.HideSelection = false;
        this.tbCode.Location = new System.Drawing.Point(87, 0);
        this.tbCode.Multiline = true;
        this.tbCode.Name = "tbCode";
        this.tbCode.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tbCode.Size = new System.Drawing.Size(352, 252);
        this.tbCode.TabIndex = 2;
        this.tbCode.Text = "";
        this.tbCode.WordWrap = false;
        //
        // splitter1
        //
        this.splitter1.Location = new System.Drawing.Point(82, 0);
        this.splitter1.Name = "splitter1";
        this.splitter1.Size = new System.Drawing.Size(5, 252);
        this.splitter1.TabIndex = 1;
        this.splitter1.TabStop = false;
        //
        // lbErrors
        //
        this.lbErrors.Dock = System.Windows.Forms.DockStyle.Left;
        this.lbErrors.HorizontalScrollbar = true;
        this.lbErrors.IntegralHeight = false;
        this.lbErrors.Location = new System.Drawing.Point(0, 0);
        this.lbErrors.Name = "lbErrors";
        this.lbErrors.Size = new System.Drawing.Size(82, 252);
        this.lbErrors.TabIndex = 0;
        this.lbErrors.SelectedIndexChanged += new System.EventHandler(this.lbErrors_SelectedIndexChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(15, 7);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(69, 15);
        this.label2.TabIndex = 5;
        this.label2.Text = "Msgs";
        //
        // CodeCtl
        //
        this.Controls.Add(this.label2);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.bCheckSyntax);
        this.Controls.Add(this.label1);
        this.Name = "CodeCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        if (tbCode.Text.Trim().Length > 0)
            _Draw.SetElement(rNode, "Code", tbCode.Text);
        else
            _Draw.removeElement(rNode,"Code"); 
    }

    private void bCheckSyntax_Click(Object sender, System.EventArgs e) throws Exception {
        checkAssembly();
    }

    private void checkAssembly() throws Exception {
        lbErrors.Items.Clear();
        // clear out existing items
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
        RefSupport refVar___0 = new RefSupport(CodeCtl.Counter);
        String unique = Interlocked.Increment(refVar___0).ToString();
        CodeCtl.Counter = refVar___0.getValue();
        lines.Add("Imports System");
        lines.Add("Namespace fyiReporting.vbgen");
        String className = "MyClass" + unique;
        lines.Add("Public Class " + className);
        lines.Add("Sub New()");
        lines.Add("End Sub");
        // Read and write code as lines
        StringReader tr = new StringReader(this.tbCode.Text);
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
            sb.AppendFormat(l + Environment.NewLine);
        }
        String vbcode = sb.ToString();
        // Create Assembly
        CompilerParameters cp = new CompilerParameters();
        cp.ReferencedAssemblies.Add("System.dll");
        cp.GenerateExecutable = false;
        cp.GenerateInMemory = true;
        cp.IncludeDebugInformation = false;
        CompilerResults cr = vbcp.CompileAssemblyFromSource(cp, vbcode);
        if (cr.Errors.Count > 0)
        {
            StringBuilder err = new StringBuilder(String.Format("Code has {0} error(s).", cr.Errors.Count));
            for (Object __dummyForeachVar1 : cr.Errors)
            {
                CompilerError ce = (CompilerError)__dummyForeachVar1;
                lbErrors.Items.Add(String.Format("Ln {0}- {1}", ce.Line - 5, ce.ErrorText));
            }
        }
        else
            MessageBox.Show("No errors", "Code Verification"); 
        return ;
    }

    private void lbErrors_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (lbErrors.Items.Count == 0)
            return ;
         
        String line = lbErrors.Items[lbErrors.SelectedIndex] instanceof String ? (String)lbErrors.Items[lbErrors.SelectedIndex] : (String)null;
        if (!line.StartsWith("Ln"))
            return ;
         
        int l = line.IndexOf('-');
        if (l < 0)
            return ;
         
        line = line.Substring(3, l - 3);
        try
        {
            int i = Convert.ToInt32(line);
            goto(i);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return ;
    }

    // we don't care about the error
    public void goto(int nLine) throws Exception {
        int offset = 0;
        nLine = Math.Min(nLine, tbCode.Lines.Length);
        for (int i = 0;i < nLine - 1 && i < tbCode.Lines.Length;++i)
            // don't go off the end
            offset += (this.tbCode.Lines[i].Length + 2);
        Control savectl = this.ActiveControl;
        tbCode.Focus();
        tbCode.Select(offset, this.tbCode.Lines[nLine > 0 ? nLine - 1 : 0].Length);
        this.ActiveControl = savectl;
    }

}


