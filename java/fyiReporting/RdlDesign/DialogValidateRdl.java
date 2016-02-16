//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.MDIChild;
import fyiReporting.RdlDesign.RdlDesigner;
import fyiReporting.RdlDesign.RdlEditPreview;

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
* Summary description for DialogAbout.
*/
public class DialogValidateRdl  extends System.Windows.Forms.Form 
{
    private final String SCHEMA2003 = "http://schemas.microsoft.com/sqlserver/reporting/2003/10/reportdefinition";
    private final String SCHEMA2003NAME = "http://schemas.microsoft.com/sqlserver/reporting/2003/10/reportdefinition/ReportDefinition.xsd";
    private final String SCHEMA2005 = "http://schemas.microsoft.com/sqlserver/reporting/2005/01/reportdefinition";
    private final String SCHEMA2005NAME = "http://schemas.microsoft.com/sqlserver/reporting/2005/01/reportdefinition/ReportDefinition.xsd";
    static public final String MSDESIGNERSCHEMA = "http://schemas.microsoft.com/SQLServer/reporting/reportdesigner";
    static public final String DESIGNERSCHEMA = "http://www.fyireporting.com/schemas";
    private int _ValidationErrorCount = new int();
    private int _ValidationWarningCount = new int();
    private RdlDesigner _RdlDesigner;
    private System.Windows.Forms.Button bClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bValidate = new System.Windows.Forms.Button();
    private System.Windows.Forms.ListBox lbSchemaErrors = new System.Windows.Forms.ListBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogValidateRdl(RdlDesigner designer) throws Exception {
        _RdlDesigner = designer;
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        return ;
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
        this.bClose = new System.Windows.Forms.Button();
        this.lbSchemaErrors = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.bValidate = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // bClose
        //
        this.bClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.bClose.CausesValidation = false;
        this.bClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bClose.Location = new System.Drawing.Point(413, 234);
        this.bClose.Name = "bClose";
        this.bClose.TabIndex = 3;
        this.bClose.Text = "Close";
        this.bClose.Click += new System.EventHandler(this.bClose_Click);
        //
        // lbSchemaErrors
        //
        this.lbSchemaErrors.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.lbSchemaErrors.HorizontalScrollbar = true;
        this.lbSchemaErrors.Location = new System.Drawing.Point(9, 54);
        this.lbSchemaErrors.Name = "lbSchemaErrors";
        this.lbSchemaErrors.Size = new System.Drawing.Size(484, 173);
        this.lbSchemaErrors.TabIndex = 2;
        this.lbSchemaErrors.DoubleClick += new System.EventHandler(this.lbSchemaErrors_DoubleClick);
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(485, 38);
        this.label1.TabIndex = 0;
        this.label1.Text = "Press Validate button to do schema validation.  Double Click on a line to scroll " + "to the line of the error.   Note: schema validation does not reliably indicate w" + "hether or not report will run in this or other products that support RDL.";
        //
        // bValidate
        //
        this.bValidate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.bValidate.Location = new System.Drawing.Point(328, 234);
        this.bValidate.Name = "bValidate";
        this.bValidate.TabIndex = 1;
        this.bValidate.Text = "Validate";
        this.bValidate.Click += new System.EventHandler(this.bValidate_Click);
        //
        // DialogValidateRdl
        //
        this.AcceptButton = this.bValidate;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bClose;
        this.ClientSize = new System.Drawing.Size(503, 261);
        this.Controls.Add(this.bValidate);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.lbSchemaErrors);
        this.Controls.Add(this.bClose);
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogValidateRdl";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Show;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Validate RDL Syntax";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.DialogValidateRdl_Closing);
        this.ResumeLayout(false);
    }

    private void bValidate_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = _RdlDesigner.ActiveMdiChild instanceof MDIChild ? (MDIChild)_RdlDesigner.ActiveMdiChild : (MDIChild)null;
        if (mc == null || !StringSupport.equals(mc.getDesignTab(), "edit"))
        {
            MessageBox.Show("Select the 'RDL Text' tab before validating.");
            return ;
        }
         
        String syntax = mc.getSourceRdl();
        boolean bNone = true;
        boolean b2005 = true;
        Cursor saveCursor = Cursor.Current;
        Cursor.Current = Cursors.WaitCursor;
        StringReader sr = null;
        XmlTextReader tr = null;
        XmlReader vr = null;
        try
        {
            // Find the namespace information in the <Report> element.
            //   We could be more precise and really parse it but it doesn't really help
            //   since we don't know the name and location of where the actual .xsd file is
            //   in the general case.  (e.g. xmlns="..." doesn't contain name of the .xsd file.
            int ir = syntax.IndexOf("<Report");
            if (ir >= 0)
            {
                int er = syntax.IndexOf(">", ir);
                if (er >= 0)
                {
                    if (syntax.IndexOf("xmlns", ir, er - ir) >= 0)
                    {
                        bNone = false;
                        if (syntax.IndexOf("2005", ir, er - ir) < 0)
                            b2005 = false;
                         
                    }
                     
                }
                 
            }
             
            _ValidationErrorCount = 0;
            _ValidationWarningCount = 0;
            this.lbSchemaErrors.Items.Clear();
            sr = new StringReader(syntax);
            tr = new XmlTextReader(sr);
            XmlReaderSettings xrs = new XmlReaderSettings();
            xrs.ValidationEventHandler += new ValidationEventHandler(ValidationHandler);
            xrs.ValidationFlags = XmlSchemaValidationFlags.AllowXmlAttributes | XmlSchemaValidationFlags.ProcessIdentityConstraints | XmlSchemaValidationFlags.ProcessSchemaLocation | XmlSchemaValidationFlags.ProcessInlineSchema;
            // add any schemas needed
            if (!bNone)
            {
                if (b2005)
                    xrs.Schemas.Add(SCHEMA2005, SCHEMA2005NAME);
                else
                    xrs.Schemas.Add(SCHEMA2003, SCHEMA2003NAME); 
            }
             
            // we always use the designer schema
            String designerSchema = String.Format("file://{0}{1}", AppDomain.CurrentDomain.BaseDirectory, "Designer.xsd");
            xrs.Schemas.Add(DESIGNERSCHEMA, designerSchema);
            vr = XmlReader.Create(tr, xrs);
            while (vr.Read())
                ;
            this.lbSchemaErrors.Items.Add(String.Format("Validation completed with {0} warnings and {1} errors.", _ValidationWarningCount, _ValidationErrorCount));
        }
        catch (Exception ex)
        {
            this.lbSchemaErrors.Items.Add(ex.Message + "  Processing terminated.");
        }
        finally
        {
            Cursor.Current = saveCursor;
            if (sr != null)
                sr.Close();
             
            if (tr != null)
                tr.Close();
             
            if (vr != null)
                vr.Close();
             
        }
    }

    public void validationHandler(Object sender, ValidationEventArgs args) throws Exception {
        if (args.Severity == XmlSeverityType.Error)
            this._ValidationErrorCount++;
        else
            this._ValidationWarningCount++; 
        this.lbSchemaErrors.Items.Add(String.Format("{0}: {1} ({2}, {3})", args.Severity, args.Message, args.Exception.LineNumber, args.Exception.LinePosition));
    }

    private void lbSchemaErrors_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        RdlEditPreview rep = _RdlDesigner.getEditor();
        if (rep == null || this.lbSchemaErrors.SelectedIndex < 0)
            return ;
         
        try
        {
            // line numbers are reported as (line#, character offset) e.g. (110, 32)
            String v = this.lbSchemaErrors.Items[lbSchemaErrors.SelectedIndex] instanceof String ? (String)this.lbSchemaErrors.Items[lbSchemaErrors.SelectedIndex] : (String)null;
            int li = v.LastIndexOf("(");
            if (li < 0)
                return ;
             
            v = v.Substring(li + 1);
            li = v.IndexOf(",");
            // find the
            v = v.Substring(0, li);
            int nLine = Int32.Parse(v);
            rep.Goto(this, nLine);
            this.BringToFront();
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    // user doesn't really care if something went wrong
    private void bClose_Click(Object sender, System.EventArgs e) throws Exception {
        this.Close();
    }

    private void dialogValidateRdl_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        this._RdlDesigner.validateSchemaClosing();
    }

}


