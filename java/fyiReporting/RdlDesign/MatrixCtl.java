//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
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
public class MatrixCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    boolean fDataSet = new boolean(), fPBBefore = new boolean(), fPBAfter = new boolean(), fNoRows = new boolean(), fCellDataElementOutput = new boolean(), fCellDataElementName = new boolean();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbDataSet = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkPBBefore = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkPBAfter = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbNoRows = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkCellContents = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox tbCellDataElementName = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public MatrixCtl(DesignXmlDraw dxDraw, List<XmlNode> ris) throws Exception {
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
        this.chkCellContents.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"CellDataElementOutput","Output"), "Output") ? true : false;
        this.tbCellDataElementName.Text = _Draw.getElementValue(riNode,"CellDataElementName","Cell");
        fNoRows = fDataSet = fPBBefore = fPBAfter = fCellDataElementOutput = fCellDataElementName = false;
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
        this.tbCellDataElementName = new System.Windows.Forms.TextBox();
        this.chkCellContents = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(24, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(80, 23);
        this.label2.TabIndex = 0;
        this.label2.Text = "DataSet Name";
        //
        // cbDataSet
        //
        this.cbDataSet.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataSet.Location = new System.Drawing.Point(120, 16);
        this.cbDataSet.Name = "cbDataSet";
        this.cbDataSet.Size = new System.Drawing.Size(304, 21);
        this.cbDataSet.TabIndex = 1;
        this.cbDataSet.SelectedIndexChanged += new System.EventHandler(this.cbDataSet_SelectedIndexChanged);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.chkPBAfter);
        this.groupBox1.Controls.Add(this.chkPBBefore);
        this.groupBox1.Location = new System.Drawing.Point(24, 88);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(400, 48);
        this.groupBox1.TabIndex = 4;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Page Breaks";
        //
        // chkPBAfter
        //
        this.chkPBAfter.Location = new System.Drawing.Point(192, 16);
        this.chkPBAfter.Name = "chkPBAfter";
        this.chkPBAfter.Size = new System.Drawing.Size(128, 24);
        this.chkPBAfter.TabIndex = 1;
        this.chkPBAfter.Text = "Insert after Matrix";
        this.chkPBAfter.CheckedChanged += new System.EventHandler(this.chkPBAfter_CheckedChanged);
        //
        // chkPBBefore
        //
        this.chkPBBefore.Location = new System.Drawing.Point(16, 16);
        this.chkPBBefore.Name = "chkPBBefore";
        this.chkPBBefore.Size = new System.Drawing.Size(128, 24);
        this.chkPBBefore.TabIndex = 0;
        this.chkPBBefore.Text = "Insert before Matrix";
        this.chkPBBefore.CheckedChanged += new System.EventHandler(this.chkPBBefore_CheckedChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 48);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 23);
        this.label1.TabIndex = 2;
        this.label1.Text = "No rows message";
        //
        // tbNoRows
        //
        this.tbNoRows.Location = new System.Drawing.Point(120, 48);
        this.tbNoRows.Name = "tbNoRows";
        this.tbNoRows.Size = new System.Drawing.Size(304, 20);
        this.tbNoRows.TabIndex = 3;
        this.tbNoRows.Text = "textBox1";
        this.tbNoRows.TextChanged += new System.EventHandler(this.tbNoRows_TextChanged);
        //
        // tbCellDataElementName
        //
        this.tbCellDataElementName.Location = new System.Drawing.Point(133, 42);
        this.tbCellDataElementName.Name = "tbCellDataElementName";
        this.tbCellDataElementName.Size = new System.Drawing.Size(183, 20);
        this.tbCellDataElementName.TabIndex = 0;
        this.tbCellDataElementName.Text = "";
        this.tbCellDataElementName.TextChanged += new System.EventHandler(this.tbCellDataElementName_TextChanged);
        //
        // chkCellContents
        //
        this.chkCellContents.Location = new System.Drawing.Point(16, 16);
        this.chkCellContents.Name = "chkCellContents";
        this.chkCellContents.Size = new System.Drawing.Size(160, 24);
        this.chkCellContents.TabIndex = 0;
        this.chkCellContents.Text = "Render cell contents";
        this.chkCellContents.CheckedChanged += new System.EventHandler(this.chkCellContents_CheckedChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 48);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(112, 16);
        this.label3.TabIndex = 1;
        this.label3.Text = "Cell Element Name";
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.tbCellDataElementName);
        this.groupBox2.Controls.Add(this.chkCellContents);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Location = new System.Drawing.Point(24, 152);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(400, 72);
        this.groupBox2.TabIndex = 5;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "XML";
        //
        // MatrixCtl
        //
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.tbNoRows);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.cbDataSet);
        this.Controls.Add(this.label2);
        this.Name = "MatrixCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
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
        fNoRows = fDataSet = fPBBefore = fPBAfter = fCellDataElementOutput = fCellDataElementName = false;
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
         
        if (fCellDataElementOutput)
            _Draw.setElement(node,"CellDataElementOutput",this.chkCellContents.Checked ? "Output" : "NoOutput");
         
        if (fCellDataElementName)
        {
            if (this.tbCellDataElementName.Text.Length > 0)
                _Draw.SetElement(node, "CellDataElementName", this.tbCellDataElementName.Text);
            else
                _Draw.removeElement(node,"CellDataElementName"); 
        }
         
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

    private void tbCellDataElementName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fCellDataElementName = true;
    }

    private void chkCellContents_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        this.fCellDataElementOutput = true;
    }

}


