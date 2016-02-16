//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogExprEditor;
import fyiReporting.RdlDesign.IProperty;
import fyiReporting.RdlDesign.ReportNames;

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
* Grouping specification: used for DataRegions (List, Chart, Table, Matrix), DataSets, group instances
*/
public class GroupingCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private XmlNode _GroupingParent = new XmlNode();
    private DataTable _DataTable = new DataTable();
    //		private DGCBColumn dgtbGE;
    private DataGridTextBoxColumn dgtbGE = new DataGridTextBoxColumn();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Button bUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGrid dgGroup = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbLabelExpr = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbParentExpr = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.CheckBox chkPBS = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkPBE = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkRepeatHeader = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkGrpHeader = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkRepeatFooter = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkGrpFooter = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label lParent = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bValueExpr = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bLabelExpr = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bParentExpr = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public GroupingCtl(DesignXmlDraw dxDraw, XmlNode groupingParent) throws Exception {
        _Draw = dxDraw;
        _GroupingParent = groupingParent;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        // Initialize the DataGrid columns
        //			dgtbGE = new DGCBColumn(ComboBoxStyle.DropDown);
        dgtbGE = new DataGridTextBoxColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbGE });
        //
        // dgtbGE
        //
        dgtbGE.HeaderText = "Expression";
        dgtbGE.MappingName = "Expression";
        dgtbGE.Width = 175;
        // Get the parent's dataset name
        //			string dataSetName = _Draw.GetDataSetNameValue(_GroupingParent);
        //
        //			string[] fields = _Draw.GetFields(dataSetName, true);
        //			if (fields != null)
        //				dgtbGE.CB.Items.AddRange(fields);
        // Initialize the DataTable
        _DataTable = new DataTable();
        _DataTable.Columns.Add(new DataColumn("Expression", String.class));
        String[] rowValues = new String[1];
        XmlNode grouping = _Draw.getNamedChildNode(_GroupingParent,"Grouping");
        // Handle the group expressions
        XmlNode groupingExs = _Draw.getNamedChildNode(grouping,"GroupExpressions");
        if (groupingExs != null)
            for (Object __dummyForeachVar0 : groupingExs.ChildNodes)
            {
                XmlNode gNode = (XmlNode)__dummyForeachVar0;
                if (gNode.NodeType != XmlNodeType.Element || !StringSupport.equals(gNode.Name, "GroupExpression"))
                    continue;
                 
                rowValues[0] = gNode.InnerText;
                _DataTable.Rows.Add(rowValues);
            }
         
        this.dgGroup.DataSource = _DataTable;
        DataGridTableStyle ts = dgGroup.TableStyles[0];
        //	ts.PreferredRowHeight = dgtbGE.CB.Height;
        ts.GridColumnStyles[0].Width = 330;
        //
        if (grouping == null)
        {
            this.tbName.Text = "";
            this.cbParentExpr.Text = "";
            this.cbLabelExpr.Text = "";
        }
        else
        {
            this.chkPBE.Checked = StringSupport.equals(_Draw.getElementValue(grouping,"PageBreakAtEnd","false").ToLower(), "true");
            this.chkPBS.Checked = StringSupport.equals(_Draw.getElementValue(grouping,"PageBreakAtStart","false").ToLower(), "true");
            this.tbName.Text = _Draw.getElementAttribute(grouping,"Name","");
            this.cbParentExpr.Text = _Draw.getElementValue(grouping,"Parent","");
            this.cbLabelExpr.Text = _Draw.getElementValue(grouping,"Label","");
        } 
        if (StringSupport.equals(_GroupingParent.Name, "TableGroup"))
        {
            XmlNode repeat = new XmlNode();
            repeat = DesignXmlDraw.findNextInHierarchy(_GroupingParent,"Header","RepeatOnNewPage");
            if (repeat != null)
                this.chkRepeatHeader.Checked = StringSupport.equals(repeat.InnerText.ToLower(), "true");
             
            repeat = DesignXmlDraw.findNextInHierarchy(_GroupingParent,"Footer","RepeatOnNewPage");
            if (repeat != null)
                this.chkRepeatFooter.Checked = StringSupport.equals(repeat.InnerText.ToLower(), "true");
             
            this.chkGrpHeader.Checked = _Draw.getNamedChildNode(_GroupingParent,"Header") != null;
            this.chkGrpFooter.Checked = _Draw.getNamedChildNode(_GroupingParent,"Footer") != null;
        }
        else
        {
            this.chkRepeatFooter.Visible = false;
            this.chkRepeatHeader.Visible = false;
            this.chkGrpFooter.Visible = false;
            this.chkGrpHeader.Visible = false;
        } 
        if (StringSupport.equals(_GroupingParent.Name, "DynamicColumns") || StringSupport.equals(_GroupingParent.Name, "DynamicRows"))
        {
            this.chkPBE.Visible = this.chkPBS.Visible = false;
        }
        else if (StringSupport.equals(_GroupingParent.Name, "DynamicSeries") || StringSupport.equals(_GroupingParent.Name, "DynamicCategories"))
        {
            this.chkPBE.Visible = this.chkPBS.Visible = false;
            this.cbParentExpr.Visible = this.lParent.Visible = false;
            this.cbLabelExpr.Text = _Draw.getElementValue(_GroupingParent,"Label","");
        }
          
        // load label and parent controls with fields
        String dsn = _Draw.getDataSetNameValue(_GroupingParent);
        if (dsn != null)
        {
            // found it
            String[] f = _Draw.getFields(dsn,true);
            this.cbParentExpr.Items.AddRange(f);
            this.cbLabelExpr.Items.AddRange(f);
        }
         
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
        this.dgGroup = new System.Windows.Forms.DataGrid();
        this.dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
        this.bDelete = new System.Windows.Forms.Button();
        this.bUp = new System.Windows.Forms.Button();
        this.bDown = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.tbName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.cbLabelExpr = new System.Windows.Forms.ComboBox();
        this.cbParentExpr = new System.Windows.Forms.ComboBox();
        this.lParent = new System.Windows.Forms.Label();
        this.chkPBS = new System.Windows.Forms.CheckBox();
        this.chkPBE = new System.Windows.Forms.CheckBox();
        this.chkRepeatHeader = new System.Windows.Forms.CheckBox();
        this.chkGrpHeader = new System.Windows.Forms.CheckBox();
        this.chkRepeatFooter = new System.Windows.Forms.CheckBox();
        this.chkGrpFooter = new System.Windows.Forms.CheckBox();
        this.bValueExpr = new System.Windows.Forms.Button();
        this.bLabelExpr = new System.Windows.Forms.Button();
        this.bParentExpr = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgGroup)).BeginInit();
        this.SuspendLayout();
        //
        // dgGroup
        //
        this.dgGroup.CaptionVisible = false;
        this.dgGroup.DataMember = "";
        this.dgGroup.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgGroup.Location = new System.Drawing.Point(8, 48);
        this.dgGroup.Name = "dgGroup";
        this.dgGroup.Size = new System.Drawing.Size(376, 88);
        this.dgGroup.TabIndex = 1;
        this.dgGroup.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyle });
        //
        // dgTableStyle
        //
        this.dgTableStyle.AllowSorting = false;
        this.dgTableStyle.DataGrid = this.dgGroup;
        this.dgTableStyle.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyle.MappingName = "";
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(392, 69);
        this.bDelete.Name = "bDelete";
        this.bDelete.Size = new System.Drawing.Size(48, 20);
        this.bDelete.TabIndex = 2;
        this.bDelete.Text = "Delete";
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // bUp
        //
        this.bUp.Location = new System.Drawing.Point(392, 94);
        this.bUp.Name = "bUp";
        this.bUp.Size = new System.Drawing.Size(48, 20);
        this.bUp.TabIndex = 3;
        this.bUp.Text = "Up";
        this.bUp.Click += new System.EventHandler(this.bUp_Click);
        //
        // bDown
        //
        this.bDown.Location = new System.Drawing.Point(392, 119);
        this.bDown.Name = "bDown";
        this.bDown.Size = new System.Drawing.Size(48, 20);
        this.bDown.TabIndex = 4;
        this.bDown.Text = "Down";
        this.bDown.Click += new System.EventHandler(this.bDown_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(48, 23);
        this.label1.TabIndex = 5;
        this.label1.Text = "Name";
        //
        // tbName
        //
        this.tbName.Location = new System.Drawing.Point(56, 8);
        this.tbName.Name = "tbName";
        this.tbName.Size = new System.Drawing.Size(328, 20);
        this.tbName.TabIndex = 0;
        this.tbName.Text = "textBox1";
        this.tbName.Validating += new System.ComponentModel.CancelEventHandler(this.tbName_Validating);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 144);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(40, 23);
        this.label2.TabIndex = 7;
        this.label2.Text = "Label";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(8, 32);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(56, 16);
        this.label3.TabIndex = 9;
        this.label3.Text = "Group By";
        //
        // cbLabelExpr
        //
        this.cbLabelExpr.Location = new System.Drawing.Point(48, 144);
        this.cbLabelExpr.Name = "cbLabelExpr";
        this.cbLabelExpr.Size = new System.Drawing.Size(336, 21);
        this.cbLabelExpr.TabIndex = 5;
        this.cbLabelExpr.Text = "comboBox1";
        //
        // cbParentExpr
        //
        this.cbParentExpr.Location = new System.Drawing.Point(48, 176);
        this.cbParentExpr.Name = "cbParentExpr";
        this.cbParentExpr.Size = new System.Drawing.Size(336, 21);
        this.cbParentExpr.TabIndex = 6;
        this.cbParentExpr.Text = "comboBox1";
        //
        // lParent
        //
        this.lParent.Location = new System.Drawing.Point(8, 176);
        this.lParent.Name = "lParent";
        this.lParent.Size = new System.Drawing.Size(40, 23);
        this.lParent.TabIndex = 11;
        this.lParent.Text = "Parent";
        //
        // chkPBS
        //
        this.chkPBS.Location = new System.Drawing.Point(8, 208);
        this.chkPBS.Name = "chkPBS";
        this.chkPBS.Size = new System.Drawing.Size(136, 24);
        this.chkPBS.TabIndex = 7;
        this.chkPBS.Text = "Page Break at Start";
        //
        // chkPBE
        //
        this.chkPBE.Location = new System.Drawing.Point(232, 208);
        this.chkPBE.Name = "chkPBE";
        this.chkPBE.Size = new System.Drawing.Size(136, 24);
        this.chkPBE.TabIndex = 8;
        this.chkPBE.Text = "Page Break at End";
        //
        // chkRepeatHeader
        //
        this.chkRepeatHeader.Location = new System.Drawing.Point(232, 232);
        this.chkRepeatHeader.Name = "chkRepeatHeader";
        this.chkRepeatHeader.Size = new System.Drawing.Size(136, 24);
        this.chkRepeatHeader.TabIndex = 13;
        this.chkRepeatHeader.Text = "Repeat group header";
        //
        // chkGrpHeader
        //
        this.chkGrpHeader.Location = new System.Drawing.Point(8, 232);
        this.chkGrpHeader.Name = "chkGrpHeader";
        this.chkGrpHeader.Size = new System.Drawing.Size(136, 24);
        this.chkGrpHeader.TabIndex = 12;
        this.chkGrpHeader.Text = "Include group header";
        //
        // chkRepeatFooter
        //
        this.chkRepeatFooter.Location = new System.Drawing.Point(232, 256);
        this.chkRepeatFooter.Name = "chkRepeatFooter";
        this.chkRepeatFooter.Size = new System.Drawing.Size(136, 24);
        this.chkRepeatFooter.TabIndex = 15;
        this.chkRepeatFooter.Text = "Repeat group footer";
        //
        // chkGrpFooter
        //
        this.chkGrpFooter.Location = new System.Drawing.Point(8, 256);
        this.chkGrpFooter.Name = "chkGrpFooter";
        this.chkGrpFooter.Size = new System.Drawing.Size(136, 24);
        this.chkGrpFooter.TabIndex = 14;
        this.chkGrpFooter.Text = "Include group footer";
        //
        // bValueExpr
        //
        this.bValueExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bValueExpr.Location = new System.Drawing.Point(392, 48);
        this.bValueExpr.Name = "bValueExpr";
        this.bValueExpr.Size = new System.Drawing.Size(22, 16);
        this.bValueExpr.TabIndex = 16;
        this.bValueExpr.Tag = "value";
        this.bValueExpr.Text = "fx";
        this.bValueExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bValueExpr.Click += new System.EventHandler(this.bValueExpr_Click);
        //
        // bLabelExpr
        //
        this.bLabelExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bLabelExpr.Location = new System.Drawing.Point(392, 147);
        this.bLabelExpr.Name = "bLabelExpr";
        this.bLabelExpr.Size = new System.Drawing.Size(22, 16);
        this.bLabelExpr.TabIndex = 17;
        this.bLabelExpr.Tag = "label";
        this.bLabelExpr.Text = "fx";
        this.bLabelExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bLabelExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // bParentExpr
        //
        this.bParentExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bParentExpr.Location = new System.Drawing.Point(392, 180);
        this.bParentExpr.Name = "bParentExpr";
        this.bParentExpr.Size = new System.Drawing.Size(22, 16);
        this.bParentExpr.TabIndex = 18;
        this.bParentExpr.Tag = "parent";
        this.bParentExpr.Text = "fx";
        this.bParentExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bParentExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // GroupingCtl
        //
        this.Controls.Add(this.bParentExpr);
        this.Controls.Add(this.bLabelExpr);
        this.Controls.Add(this.bValueExpr);
        this.Controls.Add(this.chkRepeatFooter);
        this.Controls.Add(this.chkGrpFooter);
        this.Controls.Add(this.chkRepeatHeader);
        this.Controls.Add(this.chkGrpHeader);
        this.Controls.Add(this.chkPBE);
        this.Controls.Add(this.chkPBS);
        this.Controls.Add(this.cbParentExpr);
        this.Controls.Add(this.lParent);
        this.Controls.Add(this.cbLabelExpr);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.tbName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.bDown);
        this.Controls.Add(this.bUp);
        this.Controls.Add(this.bDelete);
        this.Controls.Add(this.dgGroup);
        this.Name = "GroupingCtl";
        this.Size = new System.Drawing.Size(488, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgGroup)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        // Check to see if we have an expression
        boolean bRows = hasRows();
        // If no rows and no data
        if (!bRows && this.tbName.Text.Trim().Length == 0)
        {
            if (StringSupport.equals(_GroupingParent.Name, "Details") || StringSupport.equals(_GroupingParent.Name, "List"))
                return true;
             
            MessageBox.Show("Group must be defined.", "Grouping");
            return false;
        }
         
        // Grouping must have name
        XmlNode grouping = _Draw.getNamedChildNode(_GroupingParent,"Grouping");
        String nerr = _Draw.GroupingNameCheck(grouping, this.tbName.Text);
        if (nerr != null)
        {
            MessageBox.Show(nerr, "Group Name in Error");
            return false;
        }
         
        if (!bRows)
        {
            MessageBox.Show("No expressions have been defined for the group.", "Group");
            return false;
        }
         
        if (!StringSupport.equals(_GroupingParent.Name, "DynamicSeries"))
            return true;
         
        // DynamicSeries grouping must have a label for the legend
        if (this.cbLabelExpr.Text.Length > 0)
            return true;
         
        MessageBox.Show("Chart series must have label defined for the legend.", "Chart");
        return false;
    }

    private boolean hasRows() throws Exception {
        boolean bRows = false;
        for (Object __dummyForeachVar1 : _DataTable.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar1;
            if (dr[0] == DBNull.Value)
                continue;
             
            String ge = (String)dr[0];
            if (ge.Length <= 0)
                continue;
             
            bRows = true;
            break;
        }
        return bRows;
    }

    public void apply() throws Exception {
        if (!hasRows())
        {
            // No expressions in grouping; get rid of grouping
            _Draw.removeElement(_GroupingParent,"Grouping");
            return ;
        }
         
        // can't have a grouping
        // Get the group
        XmlNode grouping = _Draw.getCreateNamedChildNode(_GroupingParent,"Grouping");
        _Draw.SetGroupName(grouping, tbName.Text.Trim());
        // Handle the label
        if (StringSupport.equals(_GroupingParent.Name, "DynamicSeries") || StringSupport.equals(_GroupingParent.Name, "DynamicCategories"))
        {
            if (this.cbLabelExpr.Text.Length > 0)
                _Draw.SetElement(_GroupingParent, "Label", cbLabelExpr.Text);
            else
                _Draw.removeElement(_GroupingParent,"Label"); 
        }
        else
        {
            if (this.cbLabelExpr.Text.Length > 0)
                _Draw.SetElement(grouping, "Label", cbLabelExpr.Text);
            else
                _Draw.removeElement(grouping,"Label"); 
            _Draw.setElement(grouping,"PageBreakAtStart",this.chkPBS.Checked ? "true" : "false");
            _Draw.setElement(grouping,"PageBreakAtEnd",this.chkPBE.Checked ? "true" : "false");
            if (cbParentExpr.Text.Length > 0)
                _Draw.SetElement(grouping, "Parent", cbParentExpr.Text);
            else
                _Draw.removeElement(grouping,"Parent"); 
        } 
        // Loop thru and add all the group expressions
        XmlNode grpExprs = _Draw.getCreateNamedChildNode(grouping,"GroupExpressions");
        grpExprs.RemoveAll();
        String firstexpr = null;
        for (Object __dummyForeachVar2 : _DataTable.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar2;
            if (dr[0] == DBNull.Value)
                continue;
             
            String ge = (String)dr[0];
            if (ge.Length <= 0)
                continue;
             
            _Draw.createElement(grpExprs,"GroupExpression",ge);
            if (firstexpr == null)
                firstexpr = ge;
             
        }
        if (!grpExprs.HasChildNodes)
        {
            // With no group expressions there are no groups
            grouping.RemoveChild(grpExprs);
            grouping.ParentNode.RemoveChild(grouping);
            grouping = null;
        }
         
        if (StringSupport.equals(_GroupingParent.Name, "TableGroup") && grouping != null)
        {
            if (this.chkGrpHeader.Checked)
            {
                XmlNode header = _Draw.getCreateNamedChildNode(_GroupingParent,"Header");
                _Draw.setElement(header,"RepeatOnNewPage",chkRepeatHeader.Checked ? "true" : "false");
                XmlNode tblRows = _Draw.getCreateNamedChildNode(header,"TableRows");
                if (!tblRows.HasChildNodes)
                {
                    // We need to create a row
                    _Draw.insertTableRow(tblRows);
                }
                 
            }
            else
            {
                _Draw.removeElement(_GroupingParent,"Header");
            } 
            if (this.chkGrpFooter.Checked)
            {
                XmlNode footer = _Draw.getCreateNamedChildNode(_GroupingParent,"Footer");
                _Draw.setElement(footer,"RepeatOnNewPage",chkRepeatFooter.Checked ? "true" : "false");
                XmlNode tblRows = _Draw.getCreateNamedChildNode(footer,"TableRows");
                if (!tblRows.HasChildNodes)
                {
                    // We need to create a row
                    _Draw.insertTableRow(tblRows);
                }
                 
            }
            else
            {
                _Draw.removeElement(_GroupingParent,"Footer");
            } 
        }
        else if (StringSupport.equals(_GroupingParent.Name, "DynamicColumns") || StringSupport.equals(_GroupingParent.Name, "DynamicRows"))
        {
            XmlNode ritems = _Draw.getNamedChildNode(_GroupingParent,"ReportItems");
            if (ritems == null)
                ritems = _Draw.getCreateNamedChildNode(_GroupingParent,"ReportItems");
             
            XmlNode item = ritems.FirstChild;
            if (item == null)
            {
                item = _Draw.getCreateNamedChildNode(ritems,"Textbox");
                XmlNode vnode = _Draw.getCreateNamedChildNode(item,"Value");
                vnode.InnerText = firstexpr == null ? "" : firstexpr;
            }
             
        }
          
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgGroup.CurrentRowIndex;
        if (cr < 0)
            return ;
        else // already at the top
        if (cr == 0)
        {
            DataRow dr = _DataTable.Rows[0];
            dr[0] = null;
        }
          
        this._DataTable.Rows.RemoveAt(cr);
    }

    private void bUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgGroup.CurrentRowIndex;
        if (cr <= 0)
            return ;
         
        // already at the top
        SwapRow(_DataTable.Rows[cr - 1], _DataTable.Rows[cr]);
        dgGroup.CurrentRowIndex = cr - 1;
    }

    private void bDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgGroup.CurrentRowIndex;
        if (cr < 0)
            return ;
         
        // invalid index
        if (cr + 1 >= _DataTable.Rows.Count)
            return ;
         
        // already at end
        SwapRow(_DataTable.Rows[cr + 1], _DataTable.Rows[cr]);
        dgGroup.CurrentRowIndex = cr + 1;
    }

    private void swapRow(DataRow tdr, DataRow fdr) throws Exception {
        // column 1
        Object save = tdr[0];
        tdr[0] = fdr[0];
        fdr[0] = save;
        // column 2
        save = tdr[1];
        tdr[1] = fdr[1];
        fdr[1] = save;
        // column 3
        save = tdr[2];
        tdr[2] = fdr[2];
        fdr[2] = save;
        return ;
    }

    private void tbName_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        boolean bRows = hasRows();
        // If no rows and no data in name it's ok
        if (!bRows && this.tbName.Text.Trim().Length == 0)
            return ;
         
        if (!ReportNames.IsNameValid(tbName.Text))
        {
            e.Cancel = true;
            MessageBox.Show(String.Format("{0} is an invalid name.", tbName.Text), "Name");
        }
         
    }

    private void bValueExpr_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgGroup.CurrentRowIndex;
        if (cr < 0)
        {
            // No rows yet; create one
            String[] rowValues = new String[1];
            rowValues[0] = null;
            _DataTable.Rows.Add(rowValues);
            cr = 0;
        }
         
        DataGridCell dgc = dgGroup.CurrentCell;
        int cc = dgc.ColumnNumber;
        DataRow dr = _DataTable.Rows[cr];
        String cv = dr[cc] instanceof String ? (String)dr[cc] : (String)null;
        DialogExprEditor ee = new DialogExprEditor(_Draw,cv,_GroupingParent,false);
        DialogResult dlgr = ee.ShowDialog();
        if (dlgr == DialogResult.OK)
            dr[cc] = ee.getExpression();
         
    }

    private void bExpr_Click(Object sender, System.EventArgs e) throws Exception {
        Button b = sender instanceof Button ? (Button)sender : (Button)null;
        if (b == null)
            return ;
         
        Control c = null;
        System.String __dummyScrutVar0 = b.Tag instanceof String ? (String)b.Tag : (String)null;
        if (__dummyScrutVar0.equals("label"))
        {
            c = this.cbLabelExpr;
        }
        else if (__dummyScrutVar0.equals("parent"))
        {
            c = this.cbParentExpr;
        }
          
        if (c == null)
            return ;
         
        DialogExprEditor ee = new DialogExprEditor(_Draw, c.Text, _GroupingParent, false);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
            c.Text = ee.getExpression();
         
        return ;
    }

}


