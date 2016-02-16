//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogEmbeddedImages;
import fyiReporting.RdlDesign.DialogExprEditor;
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
* Summary description for ReportCtl.
*/
public class ImageCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    boolean fSource = new boolean(), fValue = new boolean(), fSizing = new boolean(), fMIMEType = new boolean();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton rbExternal = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbDatabase = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbSizing = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbValueEmbedded = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbMIMEType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbValueDatabase = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox tbValueExternal = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bExternal = new System.Windows.Forms.Button();
    private System.Windows.Forms.RadioButton rbEmbedded = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Button bEmbedded = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDatabaseExpr = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bMimeExpr = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bEmbeddedExpr = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bExternalExpr = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public ImageCtl(DesignXmlDraw dxDraw, List<XmlNode> ris) throws Exception {
        _ReportItems = ris;
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        XmlNode iNode = _ReportItems[0];
        // Populate the EmbeddedImage names
        cbValueEmbedded.Items.AddRange(_Draw.getReportNames().getEmbeddedImageNames());
        String[] flds = _Draw.getReportItemDataRegionFields(iNode,true);
        if (flds != null)
            this.cbValueDatabase.Items.AddRange(flds);
         
        String source = _Draw.getElementValue(iNode,"Source","Embedded");
        String val = _Draw.getElementValue(iNode,"Value","");
        System.String __dummyScrutVar0 = source;
        if (__dummyScrutVar0.equals("Embedded"))
        {
            this.rbEmbedded.Checked = true;
            this.cbValueEmbedded.Text = val;
        }
        else if (__dummyScrutVar0.equals("Database"))
        {
            this.rbDatabase.Checked = true;
            this.cbValueDatabase.Text = val;
            this.cbMIMEType.Text = _Draw.getElementValue(iNode,"MIMEType","image/png");
        }
        else
        {
            this.rbExternal.Checked = true;
            this.tbValueExternal.Text = val;
        }  
        this.cbSizing.Text = _Draw.getElementValue(iNode,"Sizing","AutoSize");
        fSource = fValue = fSizing = fMIMEType = false;
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
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.bEmbedded = new System.Windows.Forms.Button();
        this.bExternal = new System.Windows.Forms.Button();
        this.tbValueExternal = new System.Windows.Forms.TextBox();
        this.cbValueDatabase = new System.Windows.Forms.ComboBox();
        this.cbMIMEType = new System.Windows.Forms.ComboBox();
        this.cbValueEmbedded = new System.Windows.Forms.ComboBox();
        this.rbDatabase = new System.Windows.Forms.RadioButton();
        this.rbEmbedded = new System.Windows.Forms.RadioButton();
        this.rbExternal = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.cbSizing = new System.Windows.Forms.ComboBox();
        this.bDatabaseExpr = new System.Windows.Forms.Button();
        this.bMimeExpr = new System.Windows.Forms.Button();
        this.bEmbeddedExpr = new System.Windows.Forms.Button();
        this.bExternalExpr = new System.Windows.Forms.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.bExternalExpr);
        this.groupBox1.Controls.Add(this.bEmbeddedExpr);
        this.groupBox1.Controls.Add(this.bMimeExpr);
        this.groupBox1.Controls.Add(this.bDatabaseExpr);
        this.groupBox1.Controls.Add(this.bEmbedded);
        this.groupBox1.Controls.Add(this.bExternal);
        this.groupBox1.Controls.Add(this.tbValueExternal);
        this.groupBox1.Controls.Add(this.cbValueDatabase);
        this.groupBox1.Controls.Add(this.cbMIMEType);
        this.groupBox1.Controls.Add(this.cbValueEmbedded);
        this.groupBox1.Controls.Add(this.rbDatabase);
        this.groupBox1.Controls.Add(this.rbEmbedded);
        this.groupBox1.Controls.Add(this.rbExternal);
        this.groupBox1.Location = new System.Drawing.Point(16, 16);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(408, 152);
        this.groupBox1.TabIndex = 0;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Source";
        //
        // bEmbedded
        //
        this.bEmbedded.Location = new System.Drawing.Point(378, 58);
        this.bEmbedded.Name = "bEmbedded";
        this.bEmbedded.Size = new System.Drawing.Size(22, 16);
        this.bEmbedded.TabIndex = 8;
        this.bEmbedded.Text = "...";
        this.bEmbedded.Click += new System.EventHandler(this.bEmbedded_Click);
        //
        // bExternal
        //
        this.bExternal.Location = new System.Drawing.Point(378, 26);
        this.bExternal.Name = "bExternal";
        this.bExternal.Size = new System.Drawing.Size(22, 16);
        this.bExternal.TabIndex = 7;
        this.bExternal.Text = "...";
        this.bExternal.Click += new System.EventHandler(this.bExternal_Click);
        //
        // tbValueExternal
        //
        this.tbValueExternal.Location = new System.Drawing.Point(88, 24);
        this.tbValueExternal.Name = "tbValueExternal";
        this.tbValueExternal.Size = new System.Drawing.Size(256, 20);
        this.tbValueExternal.TabIndex = 6;
        this.tbValueExternal.Text = "";
        this.tbValueExternal.TextChanged += new System.EventHandler(this.Value_TextChanged);
        //
        // cbValueDatabase
        //
        this.cbValueDatabase.Location = new System.Drawing.Point(88, 120);
        this.cbValueDatabase.Name = "cbValueDatabase";
        this.cbValueDatabase.Size = new System.Drawing.Size(256, 21);
        this.cbValueDatabase.TabIndex = 5;
        this.cbValueDatabase.TextChanged += new System.EventHandler(this.Value_TextChanged);
        //
        // cbMIMEType
        //
        this.cbMIMEType.Items.AddRange(new Object[]{ "image/bmp", "image/jpeg", "image/gif", "image/png", "image/x-png" });
        this.cbMIMEType.Location = new System.Drawing.Point(88, 88);
        this.cbMIMEType.Name = "cbMIMEType";
        this.cbMIMEType.Size = new System.Drawing.Size(88, 21);
        this.cbMIMEType.TabIndex = 4;
        this.cbMIMEType.Text = "image/jpeg";
        this.cbMIMEType.SelectedIndexChanged += new System.EventHandler(this.cbMIMEType_SelectedIndexChanged);
        //
        // cbValueEmbedded
        //
        this.cbValueEmbedded.Location = new System.Drawing.Point(88, 56);
        this.cbValueEmbedded.Name = "cbValueEmbedded";
        this.cbValueEmbedded.Size = new System.Drawing.Size(256, 21);
        this.cbValueEmbedded.TabIndex = 3;
        this.cbValueEmbedded.TextChanged += new System.EventHandler(this.Value_TextChanged);
        //
        // rbDatabase
        //
        this.rbDatabase.Location = new System.Drawing.Point(8, 88);
        this.rbDatabase.Name = "rbDatabase";
        this.rbDatabase.Size = new System.Drawing.Size(80, 24);
        this.rbDatabase.TabIndex = 2;
        this.rbDatabase.Text = "Database";
        this.rbDatabase.CheckedChanged += new System.EventHandler(this.rbSource_CheckedChanged);
        //
        // rbEmbedded
        //
        this.rbEmbedded.Location = new System.Drawing.Point(8, 56);
        this.rbEmbedded.Name = "rbEmbedded";
        this.rbEmbedded.Size = new System.Drawing.Size(80, 24);
        this.rbEmbedded.TabIndex = 1;
        this.rbEmbedded.Text = "Embedded";
        this.rbEmbedded.CheckedChanged += new System.EventHandler(this.rbSource_CheckedChanged);
        //
        // rbExternal
        //
        this.rbExternal.Location = new System.Drawing.Point(8, 24);
        this.rbExternal.Name = "rbExternal";
        this.rbExternal.Size = new System.Drawing.Size(80, 24);
        this.rbExternal.TabIndex = 0;
        this.rbExternal.Text = "External";
        this.rbExternal.CheckedChanged += new System.EventHandler(this.rbSource_CheckedChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 184);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(40, 23);
        this.label1.TabIndex = 1;
        this.label1.Text = "Sizing";
        //
        // cbSizing
        //
        this.cbSizing.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbSizing.Items.AddRange(new Object[]{ "AutoSize", "Fit", "FitProportional", "Clip" });
        this.cbSizing.Location = new System.Drawing.Point(72, 184);
        this.cbSizing.Name = "cbSizing";
        this.cbSizing.Size = new System.Drawing.Size(96, 21);
        this.cbSizing.TabIndex = 2;
        this.cbSizing.SelectedIndexChanged += new System.EventHandler(this.cbSizing_SelectedIndexChanged);
        //
        // bDatabaseExpr
        //
        this.bDatabaseExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bDatabaseExpr.Location = new System.Drawing.Point(352, 122);
        this.bDatabaseExpr.Name = "bDatabaseExpr";
        this.bDatabaseExpr.Size = new System.Drawing.Size(22, 16);
        this.bDatabaseExpr.TabIndex = 9;
        this.bDatabaseExpr.Tag = "database";
        this.bDatabaseExpr.Text = "fx";
        this.bDatabaseExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bDatabaseExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // bMimeExpr
        //
        this.bMimeExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bMimeExpr.Location = new System.Drawing.Point(184, 90);
        this.bMimeExpr.Name = "bMimeExpr";
        this.bMimeExpr.Size = new System.Drawing.Size(22, 16);
        this.bMimeExpr.TabIndex = 10;
        this.bMimeExpr.Tag = "mime";
        this.bMimeExpr.Text = "fx";
        this.bMimeExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bMimeExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // bEmbeddedExpr
        //
        this.bEmbeddedExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bEmbeddedExpr.Location = new System.Drawing.Point(352, 58);
        this.bEmbeddedExpr.Name = "bEmbeddedExpr";
        this.bEmbeddedExpr.Size = new System.Drawing.Size(22, 16);
        this.bEmbeddedExpr.TabIndex = 11;
        this.bEmbeddedExpr.Tag = "embedded";
        this.bEmbeddedExpr.Text = "fx";
        this.bEmbeddedExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bEmbeddedExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // bExternalExpr
        //
        this.bExternalExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bExternalExpr.Location = new System.Drawing.Point(352, 26);
        this.bExternalExpr.Name = "bExternalExpr";
        this.bExternalExpr.Size = new System.Drawing.Size(22, 16);
        this.bExternalExpr.TabIndex = 12;
        this.bExternalExpr.Tag = "external";
        this.bExternalExpr.Text = "fx";
        this.bExternalExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bExternalExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // ImageCtl
        //
        this.Controls.Add(this.cbSizing);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox1);
        this.Name = "ImageCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        for (Object __dummyForeachVar0 : this._ReportItems)
        {
            // take information in control and apply to all the style nodes
            //  Only change information that has been marked as modified;
            //   this way when group is selected it is possible to change just
            //   the items you want and keep the rest the same.
            XmlNode riNode = (XmlNode)__dummyForeachVar0;
            applyChanges(riNode);
        }
        // No more changes
        fSource = fValue = fSizing = fMIMEType = false;
    }

    public void applyChanges(XmlNode node) throws Exception {
        if (fSource || fValue || fMIMEType)
        {
            String source = "";
            String val = "";
            if (rbEmbedded.Checked)
            {
                val = cbValueEmbedded.Text;
                source = "Embedded";
            }
            else if (rbDatabase.Checked)
            {
                source = "Database";
                val = cbValueDatabase.Text;
                _Draw.SetElement(node, "MIMEType", this.cbMIMEType.Text);
            }
            else
            {
                // must be external
                source = "External";
                val = tbValueExternal.Text;
            }  
            _Draw.setElement(node,"Source",source);
            _Draw.setElement(node,"Value",val);
        }
         
        if (fSizing)
            _Draw.SetElement(node, "Sizing", this.cbSizing.Text);
         
    }

    private void value_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fValue = true;
    }

    private void cbMIMEType_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fMIMEType = true;
    }

    private void rbSource_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fSource = true;
        this.cbValueDatabase.Enabled = this.cbMIMEType.Enabled = this.bDatabaseExpr.Enabled = this.rbDatabase.Checked;
        this.cbValueEmbedded.Enabled = this.bEmbeddedExpr.Enabled = this.bEmbedded.Enabled = this.rbEmbedded.Checked;
        this.tbValueExternal.Enabled = this.bExternalExpr.Enabled = this.bExternal.Enabled = this.rbExternal.Checked;
    }

    private void cbSizing_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fSizing = true;
    }

    private void bExternal_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Bitmap Files (*.bmp)|*.bmp" + "|JPEG (*.jpg;*.jpeg;*.jpe;*.jfif)|*.jpg;*.jpeg;*.jpe;*.jfif" + "|GIF (*.gif)|*.gif" + "|TIFF (*.tif;*.tiff)|*.tif;*.tiff" + "|PNG (*.png)|*.png" + "|All Picture Files|*.bmp;*.jpg;*.jpeg;*.jpe;*.jfif;*.gif;*.tif;*.tiff;*.png" + "|All files (*.*)|*.*";
        ofd.FilterIndex = 6;
        ofd.CheckFileExists = true;
        if (ofd.ShowDialog(this) == DialogResult.OK)
        {
            tbValueExternal.Text = ofd.FileName;
        }
         
    }

    private void bEmbedded_Click(Object sender, System.EventArgs e) throws Exception {
        DialogEmbeddedImages dlgEI = new DialogEmbeddedImages(this._Draw);
        dlgEI.StartPosition = FormStartPosition.CenterParent;
        DialogResult dr = dlgEI.ShowDialog();
        if (dr != DialogResult.OK)
            return ;
         
        // Populate the EmbeddedImage names
        cbValueEmbedded.Items.Clear();
        cbValueEmbedded.Items.AddRange(_Draw.getReportNames().getEmbeddedImageNames());
    }

    private void bExpr_Click(Object sender, System.EventArgs e) throws Exception {
        Button b = sender instanceof Button ? (Button)sender : (Button)null;
        if (b == null)
            return ;
         
        Control c = null;
        System.String __dummyScrutVar1 = b.Tag instanceof String ? (String)b.Tag : (String)null;
        if (__dummyScrutVar1.equals("external"))
        {
            c = tbValueExternal;
        }
        else if (__dummyScrutVar1.equals("embedded"))
        {
            c = cbValueEmbedded;
        }
        else if (__dummyScrutVar1.equals("mime"))
        {
            c = cbMIMEType;
        }
        else if (__dummyScrutVar1.equals("database"))
        {
            c = cbValueDatabase;
        }
            
        if (c == null)
            return ;
         
        XmlNode sNode = _ReportItems[0];
        DialogExprEditor ee = new DialogExprEditor(_Draw, c.Text, sNode);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
            c.Text = ee.getExpression();
         
        return ;
    }

}


