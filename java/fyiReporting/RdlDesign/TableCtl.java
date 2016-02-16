//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
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
* Summary description for ReportCtl.
*/
public class TableCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    boolean fDataSet = new boolean(), fPBBefore = new boolean(), fPBAfter = new boolean(), fNoRows = new boolean();
    boolean fDetailElementName = new boolean(), fDetailCollectionName = new boolean(), fRenderDetails = new boolean();
    boolean fCheckRows = new boolean();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbDataSet = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkPBBefore = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkPBAfter = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbNoRows = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox chkRenderDetails = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox tbDetailElementName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbDetailCollectionName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkDetails = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkHeaderRows = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkFooterRows = new System.Windows.Forms.CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public TableCtl(DesignXmlDraw dxDraw, List<XmlNode> ris) throws Exception {
        _ReportItems = ris;
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        XmlNode riNode = _ReportItems[0];
        tbNoRows.Text = _Draw.getElementValue(riNode,"NoRows","");
        cbDataSet.Items.AddRange(_Draw.getDataSetNames());
        cbDataSet.Text = _Draw.getDataSetNameValue(riNode);
        if (_Draw.getReportItemDataRegionContainer(riNode) != null)
            cbDataSet.Enabled = false;
         
        chkPBBefore.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"PageBreakAtStart","false").ToLower(), "true") ? true : false;
        chkPBAfter.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"PageBreakAtEnd","false").ToLower(), "true") ? true : false;
        this.chkRenderDetails.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"DetailDataElementOutput","output").ToLower(), "output");
        this.tbDetailElementName.Text = _Draw.getElementValue(riNode,"DetailDataElementName","Details");
        this.tbDetailCollectionName.Text = _Draw.getElementValue(riNode,"DetailDataCollectionName","Details_Collection");
        this.chkDetails.Checked = _Draw.getNamedChildNode(riNode,"Details") != null;
        this.chkFooterRows.Checked = _Draw.getNamedChildNode(riNode,"Footer") != null;
        this.chkHeaderRows.Checked = _Draw.getNamedChildNode(riNode,"Header") != null;
        fNoRows = fDataSet = fPBBefore = fPBAfter = fDetailElementName = fDetailCollectionName = fRenderDetails = fCheckRows = false;
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
        this.label2 = new System.Windows.Forms.Label();
        this.cbDataSet = new System.Windows.Forms.ComboBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.chkPBAfter = new System.Windows.Forms.CheckBox();
        this.chkPBBefore = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.tbNoRows = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.tbDetailCollectionName = new System.Windows.Forms.TextBox();
        this.tbDetailElementName = new System.Windows.Forms.TextBox();
        this.chkRenderDetails = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.chkFooterRows = new System.Windows.Forms.CheckBox();
        this.chkHeaderRows = new System.Windows.Forms.CheckBox();
        this.chkDetails = new System.Windows.Forms.CheckBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(24, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(80, 23);
        this.label2.TabIndex = 5;
        this.label2.Text = "DataSet Name";
        //
        // cbDataSet
        //
        this.cbDataSet.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataSet.Location = new System.Drawing.Point(120, 16);
        this.cbDataSet.Name = "cbDataSet";
        this.cbDataSet.Size = new System.Drawing.Size(304, 21);
        this.cbDataSet.TabIndex = 0;
        this.cbDataSet.SelectedIndexChanged += new System.EventHandler(this.cbDataSet_SelectedIndexChanged);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.chkPBAfter);
        this.groupBox1.Controls.Add(this.chkPBBefore);
        this.groupBox1.Location = new System.Drawing.Point(24, 64);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(400, 48);
        this.groupBox1.TabIndex = 2;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Page Breaks";
        //
        // chkPBAfter
        //
        this.chkPBAfter.Location = new System.Drawing.Point(192, 16);
        this.chkPBAfter.Name = "chkPBAfter";
        this.chkPBAfter.Size = new System.Drawing.Size(128, 24);
        this.chkPBAfter.TabIndex = 1;
        this.chkPBAfter.Text = "Insert after Table";
        this.chkPBAfter.CheckedChanged += new System.EventHandler(this.chkPBAfter_CheckedChanged);
        //
        // chkPBBefore
        //
        this.chkPBBefore.Location = new System.Drawing.Point(16, 16);
        this.chkPBBefore.Name = "chkPBBefore";
        this.chkPBBefore.Size = new System.Drawing.Size(128, 24);
        this.chkPBBefore.TabIndex = 0;
        this.chkPBBefore.Text = "Insert before Table";
        this.chkPBBefore.CheckedChanged += new System.EventHandler(this.chkPBBefore_CheckedChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 40);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 23);
        this.label1.TabIndex = 6;
        this.label1.Text = "No rows message";
        //
        // tbNoRows
        //
        this.tbNoRows.Location = new System.Drawing.Point(120, 40);
        this.tbNoRows.Name = "tbNoRows";
        this.tbNoRows.Size = new System.Drawing.Size(304, 20);
        this.tbNoRows.TabIndex = 1;
        this.tbNoRows.Text = "textBox1";
        this.tbNoRows.TextChanged += new System.EventHandler(this.tbNoRows_TextChanged);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.tbDetailCollectionName);
        this.groupBox2.Controls.Add(this.tbDetailElementName);
        this.groupBox2.Controls.Add(this.chkRenderDetails);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Location = new System.Drawing.Point(24, 176);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(400, 104);
        this.groupBox2.TabIndex = 4;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "XML";
        //
        // tbDetailCollectionName
        //
        this.tbDetailCollectionName.Location = new System.Drawing.Point(160, 72);
        this.tbDetailCollectionName.Name = "tbDetailCollectionName";
        this.tbDetailCollectionName.Size = new System.Drawing.Size(224, 20);
        this.tbDetailCollectionName.TabIndex = 3;
        this.tbDetailCollectionName.Text = "textBox1";
        this.tbDetailCollectionName.TextChanged += new System.EventHandler(this.tbDetailCollectionName_TextChanged);
        //
        // tbDetailElementName
        //
        this.tbDetailElementName.Location = new System.Drawing.Point(160, 40);
        this.tbDetailElementName.Name = "tbDetailElementName";
        this.tbDetailElementName.Size = new System.Drawing.Size(224, 20);
        this.tbDetailElementName.TabIndex = 2;
        this.tbDetailElementName.Text = "textBox1";
        this.tbDetailElementName.TextChanged += new System.EventHandler(this.tbDetailElementName_TextChanged);
        //
        // chkRenderDetails
        //
        this.chkRenderDetails.Location = new System.Drawing.Point(16, 16);
        this.chkRenderDetails.Name = "chkRenderDetails";
        this.chkRenderDetails.Size = new System.Drawing.Size(160, 24);
        this.chkRenderDetails.TabIndex = 0;
        this.chkRenderDetails.Text = "Render Details in Output";
        this.chkRenderDetails.CheckedChanged += new System.EventHandler(this.chkRenderDetails_CheckedChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(16, 72);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(120, 16);
        this.label4.TabIndex = 1;
        this.label4.Text = "Detail Collection Name";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 48);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(112, 16);
        this.label3.TabIndex = 1;
        this.label3.Text = "Detail Element Name";
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.chkFooterRows);
        this.groupBox3.Controls.Add(this.chkHeaderRows);
        this.groupBox3.Controls.Add(this.chkDetails);
        this.groupBox3.Location = new System.Drawing.Point(24, 120);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(400, 48);
        this.groupBox3.TabIndex = 3;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Include Table Rows";
        //
        // chkFooterRows
        //
        this.chkFooterRows.Location = new System.Drawing.Point(272, 16);
        this.chkFooterRows.Name = "chkFooterRows";
        this.chkFooterRows.TabIndex = 2;
        this.chkFooterRows.Text = "Footer Rows";
        this.chkFooterRows.CheckedChanged += new System.EventHandler(this.chkRows_CheckedChanged);
        //
        // chkHeaderRows
        //
        this.chkHeaderRows.Location = new System.Drawing.Point(144, 16);
        this.chkHeaderRows.Name = "chkHeaderRows";
        this.chkHeaderRows.TabIndex = 1;
        this.chkHeaderRows.Text = "Header Rows";
        this.chkHeaderRows.CheckedChanged += new System.EventHandler(this.chkRows_CheckedChanged);
        //
        // chkDetails
        //
        this.chkDetails.Location = new System.Drawing.Point(16, 16);
        this.chkDetails.Name = "chkDetails";
        this.chkDetails.TabIndex = 1;
        this.chkDetails.Text = "Detail Rows";
        this.chkDetails.CheckedChanged += new System.EventHandler(this.chkRows_CheckedChanged);
        //
        // TableCtl
        //
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.tbNoRows);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.cbDataSet);
        this.Controls.Add(this.label2);
        this.Name = "TableCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        if (this.chkDetails.Checked || this.chkFooterRows.Checked || this.chkHeaderRows.Checked)
            return true;
         
        MessageBox.Show("Table must have at least one Header, Details or Footer row defined.", "Table");
        return false;
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
        fNoRows = fDataSet = fPBBefore = fPBAfter = fDetailElementName = fDetailCollectionName = fRenderDetails = fCheckRows = false;
    }

    public void applyChanges(XmlNode node) throws Exception {
        if (fNoRows)
            _Draw.SetElement(node, "NoRows", this.tbNoRows.Text);
         
        if (fDataSet)
            _Draw.SetElement(node, "DataSetName", this.cbDataSet.Text);
         
        if (fPBBefore)
            _Draw.setElement(node,"PageBreakAtStart",this.chkPBBefore.Checked ? "true" : "false");
         
        if (fPBAfter)
            _Draw.setElement(node,"PageBreakAtEnd",this.chkPBAfter.Checked ? "true" : "false");
         
        if (fCheckRows)
        {
            if (this.chkDetails.Checked)
                createTableRow(node,"Details");
            else
                _Draw.removeElement(node,"Details"); 
            if (this.chkHeaderRows.Checked)
                createTableRow(node,"Header");
            else
                _Draw.removeElement(node,"Header"); 
            if (this.chkFooterRows.Checked)
                createTableRow(node,"Footer");
            else
                _Draw.removeElement(node,"Footer"); 
        }
         
        if (fRenderDetails)
            _Draw.setElement(node,"DetailDataElementOutput",this.chkRenderDetails.Checked ? "Output" : "NoOutput");
         
        if (this.fDetailElementName)
        {
            if (this.tbDetailElementName.Text.Length > 0)
                _Draw.SetElement(node, "DetailDataElementName", this.tbDetailElementName.Text);
            else
                _Draw.removeElement(node,"DetailDataElementName"); 
        }
         
        if (this.fDetailCollectionName)
        {
            if (this.tbDetailCollectionName.Text.Length > 0)
                _Draw.SetElement(node, "DetailDataCollectionName", this.tbDetailCollectionName.Text);
            else
                _Draw.removeElement(node,"DetailDataCollectionName"); 
        }
         
    }

    private void createTableRow(XmlNode tblNode, String elementName) throws Exception {
        XmlNode node = _Draw.getNamedChildNode(tblNode,elementName);
        if (node == null)
        {
            node = _Draw.createElement(tblNode,elementName,null);
            XmlNode tblRows = _Draw.createElement(node,"TableRows",null);
            _Draw.insertTableRow(tblRows);
        }
         
        return ;
    }

    private void cbDataSet_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fDataSet = true;
    }

    private void chkPBBefore_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fPBBefore = true;
    }

    private void chkPBAfter_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fPBAfter = true;
    }

    private void tbNoRows_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fNoRows = true;
    }

    private void chkRows_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        this.fCheckRows = true;
    }

    private void chkRenderDetails_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fRenderDetails = true;
    }

    private void tbDetailElementName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fDetailElementName = true;
    }

    private void tbDetailCollectionName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fDetailCollectionName = true;
    }

}


