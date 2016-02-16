//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignerUtility;
import fyiReporting.RdlDesign.DesignXmlDraw;
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
* Summary description for TableColumnCtl.
*/
public class TableColumnCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private XmlNode _TableColumn = new XmlNode();
    private DesignXmlDraw _Draw;
    // flags for controlling whether syntax changed for a particular property
    private boolean fHidden = new boolean(), fToggle = new boolean(), fWidth = new boolean(), fFixedHeader = new boolean();
    private System.Windows.Forms.GroupBox grpBoxVisibility = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbHidden = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox cbToggle = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button bHidden = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbColumnWidth = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox chkFixedHeader = new System.Windows.Forms.CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public TableColumnCtl(DesignXmlDraw dxDraw, XmlNode tc) throws Exception {
        _TableColumn = tc;
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues(tc);
    }

    private void initValues(XmlNode node) throws Exception {
        // Handle Width definition
        this.tbColumnWidth.Text = _Draw.getElementValue(node,"Width","");
        this.chkFixedHeader.Checked = StringSupport.equals(_Draw.getElementValue(node,"FixedHeader","false").ToLower(), "true") ? true : false;
        // Handle Visiblity definition
        XmlNode visNode = _Draw.getNamedChildNode(node,"Visibility");
        if (visNode != null)
        {
            this.tbHidden.Text = _Draw.getElementValue(visNode,"Hidden","");
            this.cbToggle.Text = _Draw.getElementValue(visNode,"ToggleItem","");
        }
         
        IEnumerable list = _Draw.getReportItems("//Textbox");
        if (list != null)
        {
            for (Object __dummyForeachVar0 : list)
            {
                XmlNode tNode = (XmlNode)__dummyForeachVar0;
                XmlAttribute name = tNode.Attributes["Name"];
                if (name != null && name.Value != null && name.Value.Length > 0)
                    cbToggle.Items.Add(name.Value);
                 
            }
        }
         
        // nothing has changed now
        fWidth = fHidden = fToggle = fFixedHeader = false;
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
        this.grpBoxVisibility = new System.Windows.Forms.GroupBox();
        this.bHidden = new System.Windows.Forms.Button();
        this.cbToggle = new System.Windows.Forms.ComboBox();
        this.tbHidden = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.tbColumnWidth = new System.Windows.Forms.TextBox();
        this.chkFixedHeader = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.grpBoxVisibility.SuspendLayout();
        this.SuspendLayout();
        //
        // grpBoxVisibility
        //
        this.grpBoxVisibility.Controls.Add(this.bHidden);
        this.grpBoxVisibility.Controls.Add(this.cbToggle);
        this.grpBoxVisibility.Controls.Add(this.tbHidden);
        this.grpBoxVisibility.Controls.Add(this.label3);
        this.grpBoxVisibility.Controls.Add(this.label2);
        this.grpBoxVisibility.Location = new System.Drawing.Point(8, 8);
        this.grpBoxVisibility.Name = "grpBoxVisibility";
        this.grpBoxVisibility.Size = new System.Drawing.Size(432, 80);
        this.grpBoxVisibility.TabIndex = 1;
        this.grpBoxVisibility.TabStop = false;
        this.grpBoxVisibility.Text = "Visibility";
        //
        // bHidden
        //
        this.bHidden.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bHidden.Location = new System.Drawing.Point(400, 26);
        this.bHidden.Name = "bHidden";
        this.bHidden.Size = new System.Drawing.Size(22, 16);
        this.bHidden.TabIndex = 1;
        this.bHidden.Tag = "visibility";
        this.bHidden.Text = "fx";
        this.bHidden.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bHidden.Click += new System.EventHandler(this.bExpr_Click);
        //
        // cbToggle
        //
        this.cbToggle.Location = new System.Drawing.Point(168, 48);
        this.cbToggle.Name = "cbToggle";
        this.cbToggle.Size = new System.Drawing.Size(152, 21);
        this.cbToggle.TabIndex = 2;
        this.cbToggle.TextChanged += new System.EventHandler(this.cbToggle_SelectedIndexChanged);
        this.cbToggle.SelectedIndexChanged += new System.EventHandler(this.cbToggle_SelectedIndexChanged);
        //
        // tbHidden
        //
        this.tbHidden.Location = new System.Drawing.Point(168, 24);
        this.tbHidden.Name = "tbHidden";
        this.tbHidden.Size = new System.Drawing.Size(224, 20);
        this.tbHidden.TabIndex = 0;
        this.tbHidden.Text = "";
        this.tbHidden.TextChanged += new System.EventHandler(this.tbHidden_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 48);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(152, 23);
        this.label3.TabIndex = 1;
        this.label3.Text = "Toggle Item (Textbox name)";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(16, 24);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(120, 23);
        this.label2.TabIndex = 0;
        this.label2.Text = "Hidden (initial visibility)";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 104);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(80, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "Column Width";
        //
        // tbColumnWidth
        //
        this.tbColumnWidth.Location = new System.Drawing.Point(88, 104);
        this.tbColumnWidth.Name = "tbColumnWidth";
        this.tbColumnWidth.TabIndex = 3;
        this.tbColumnWidth.Text = "";
        this.tbColumnWidth.TextChanged += new System.EventHandler(this.tbWidth_TextChanged);
        //
        // chkFixedHeader
        //
        this.chkFixedHeader.Location = new System.Drawing.Point(8, 136);
        this.chkFixedHeader.Name = "chkFixedHeader";
        this.chkFixedHeader.Size = new System.Drawing.Size(96, 24);
        this.chkFixedHeader.TabIndex = 4;
        this.chkFixedHeader.Text = "Fixed Header";
        this.chkFixedHeader.CheckedChanged += new System.EventHandler(this.cbFixedHeader_CheckedChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(112, 136);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(336, 24);
        this.label4.TabIndex = 5;
        this.label4.Text = "Note: Fixed headers must be contiguous and start at either the left or the right " + "of the table.  Current renderers ignore this parameter.";
        //
        // TableColumnCtl
        //
        this.Controls.Add(this.label4);
        this.Controls.Add(this.chkFixedHeader);
        this.Controls.Add(this.tbColumnWidth);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.grpBoxVisibility);
        this.Name = "TableColumnCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.grpBoxVisibility.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        try
        {
            if (fWidth)
                DesignerUtility.ValidateSize(this.tbColumnWidth.Text, true, false);
             
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Width is Invalid");
            return false;
        }

        if (fHidden)
        {
            String vh = this.tbHidden.Text.Trim();
            if (vh.Length > 0)
            {
                if (vh.StartsWith("="))
                {
                }
                else
                {
                    vh = vh.ToLower();
                    System.String __dummyScrutVar0 = vh;
                    if (__dummyScrutVar0.equals("true") || __dummyScrutVar0.equals("false"))
                    {
                    }
                    else
                    {
                        MessageBox.Show(String.Format("{0} must be an expression or 'true' or 'false'", tbHidden.Text), "Hidden is Invalid");
                        return false;
                    } 
                } 
            }
             
        }
         
        return true;
    }

    public void apply() throws Exception {
        // take information in control and apply to all the style nodes
        //  Only change information that has been marked as modified;
        //   this way when group is selected it is possible to change just
        //   the items you want and keep the rest the same.
        applyChanges(this._TableColumn);
        // nothing has changed now
        fWidth = fHidden = fToggle = false;
    }

    private void applyChanges(XmlNode rNode) throws Exception {
        if (fHidden || fToggle)
        {
            XmlNode visNode = _Draw.setElement(rNode,"Visibility",null);
            if (fHidden)
            {
                String vh = this.tbHidden.Text.Trim();
                if (vh.Length > 0)
                    _Draw.setElement(visNode,"Hidden",vh);
                else
                    _Draw.removeElement(visNode,"Hidden"); 
            }
             
            if (fToggle)
                _Draw.SetElement(visNode, "ToggleItem", this.cbToggle.Text);
             
        }
         
        if (fWidth)
            // already validated
            _Draw.SetElement(rNode, "Width", this.tbColumnWidth.Text);
         
        if (fFixedHeader)
        {
            if (this.chkFixedHeader.Checked)
                _Draw.setElement(rNode,"FixedHeader","true");
            else
                _Draw.removeElement(rNode,"FixedHeader"); 
        }
         
    }

    // just get rid of it
    private void tbHidden_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fHidden = true;
    }

    private void tbWidth_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fWidth = true;
    }

    private void cbToggle_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fToggle = true;
    }

    private void bExpr_Click(Object sender, System.EventArgs e) throws Exception {
        Button b = sender instanceof Button ? (Button)sender : (Button)null;
        if (b == null)
            return ;
         
        Control c = null;
        System.String __dummyScrutVar1 = b.Tag instanceof String ? (String)b.Tag : (String)null;
        if (__dummyScrutVar1.equals("visibility"))
        {
            c = tbHidden;
        }
         
        if (c == null)
            return ;
         
        DialogExprEditor ee = new DialogExprEditor(_Draw, c.Text, _TableColumn);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
            c.Text = ee.getExpression();
         
        return ;
    }

    private void cbFixedHeader_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fFixedHeader = true;
    }

}


