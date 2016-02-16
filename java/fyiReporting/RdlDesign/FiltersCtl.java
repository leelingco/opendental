//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogExprEditor;
import fyiReporting.RdlDesign.DialogFilterOperator;
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
* Filters specification: used for DataRegions (List, Chart, Table, Matrix), DataSets, group instances
*/
public class FiltersCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private XmlNode _FilterParent = new XmlNode();
    private DataGridViewTextBoxColumn dgtbFE = new DataGridViewTextBoxColumn();
    private DataGridViewComboBoxColumn dgtbOP = new DataGridViewComboBoxColumn();
    private DataGridViewTextBoxColumn dgtbFV = new DataGridViewTextBoxColumn();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGridView dgFilters = new System.Windows.Forms.DataGridView();
    private System.Windows.Forms.Button bUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bValueExpr = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public FiltersCtl(DesignXmlDraw dxDraw, XmlNode filterParent) throws Exception {
        _Draw = dxDraw;
        _FilterParent = filterParent;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        // Initialize the DataGrid columns
        dgtbFE = new DataGridViewTextBoxColumn();
        dgtbOP = new DataGridViewComboBoxColumn();
        dgtbOP.Items.AddRange(new String[]{ "Equal", "Like", "NotEqual", "GreaterThan", "GreaterThanOrEqual", "LessThan", "LessThanOrEqual", "TopN", "BottomN", "TopPercent", "BottomPercent", "In", "Between" });
        dgtbFV = new DataGridViewTextBoxColumn();
        dgFilters.Columns.Add(dgtbFE);
        dgFilters.Columns.Add(dgtbOP);
        dgFilters.Columns.Add(dgtbFV);
        //
        // dgtbFE
        //
        dgtbFE.HeaderText = "Filter Expression";
        dgtbFE.Width = 130;
        // Get the parent's dataset name
        String dataSetName = _Draw.getDataSetNameValue(_FilterParent);
        // unfortunately no way to make combo box editable
        //string[] fields = _Draw.GetFields(dataSetName, true);
        //if (fields != null)
        //    dgtbFE.Items.AddRange(fields);
        dgtbOP.HeaderText = "Operator";
        dgtbOP.Width = 100;
        dgtbOP.DropDownWidth = 140;
        //
        // dgtbFV
        //
        this.dgtbFV.HeaderText = "Value(s)";
        this.dgtbFV.Width = 130;
        //string[] parms = _Draw.GetReportParameters(true);
        //if (parms != null)
        //    dgtbFV.Items.AddRange(parms);
        XmlNode filters = _Draw.getNamedChildNode(_FilterParent,"Filters");
        if (filters != null)
            for (Object __dummyForeachVar1 : filters.ChildNodes)
            {
                XmlNode fNode = (XmlNode)__dummyForeachVar1;
                if (fNode.NodeType != XmlNodeType.Element || !StringSupport.equals(fNode.Name, "Filter"))
                    continue;
                 
                // Get the values
                XmlNode vNodes = _Draw.getNamedChildNode(fNode,"FilterValues");
                StringBuilder sb = new StringBuilder();
                if (vNodes != null)
                {
                    for (Object __dummyForeachVar0 : vNodes.ChildNodes)
                    {
                        XmlNode v = (XmlNode)__dummyForeachVar0;
                        if (v.InnerText.Length <= 0)
                            continue;
                         
                        if (sb.Length != 0)
                            sb.Append(", ");
                         
                        sb.Append(v.InnerText);
                    }
                }
                 
                // Add the row
                dgFilters.Rows.Add(_Draw.getElementValue(fNode,"FilterExpression",""), _Draw.getElementValue(fNode,"Operator",""), sb.ToString());
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
        this.dgFilters = new System.Windows.Forms.DataGridView();
        this.bDelete = new System.Windows.Forms.Button();
        this.bUp = new System.Windows.Forms.Button();
        this.bDown = new System.Windows.Forms.Button();
        this.bValueExpr = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgFilters)).BeginInit();
        this.SuspendLayout();
        //
        // dgFilters
        //
        this.dgFilters.Location = new System.Drawing.Point(8, 8);
        this.dgFilters.Name = "dgFilters";
        this.dgFilters.Size = new System.Drawing.Size(376, 264);
        this.dgFilters.TabIndex = 2;
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(392, 40);
        this.bDelete.Name = "bDelete";
        this.bDelete.Size = new System.Drawing.Size(48, 23);
        this.bDelete.TabIndex = 1;
        this.bDelete.Text = "Delete";
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // bUp
        //
        this.bUp.Location = new System.Drawing.Point(392, 71);
        this.bUp.Name = "bUp";
        this.bUp.Size = new System.Drawing.Size(48, 23);
        this.bUp.TabIndex = 3;
        this.bUp.Text = "Up";
        this.bUp.Click += new System.EventHandler(this.bUp_Click);
        //
        // bDown
        //
        this.bDown.Location = new System.Drawing.Point(392, 102);
        this.bDown.Name = "bDown";
        this.bDown.Size = new System.Drawing.Size(48, 23);
        this.bDown.TabIndex = 4;
        this.bDown.Text = "Down";
        this.bDown.Click += new System.EventHandler(this.bDown_Click);
        //
        // bValueExpr
        //
        this.bValueExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.bValueExpr.Location = new System.Drawing.Point(392, 16);
        this.bValueExpr.Name = "bValueExpr";
        this.bValueExpr.Size = new System.Drawing.Size(22, 16);
        this.bValueExpr.TabIndex = 5;
        this.bValueExpr.Tag = "value";
        this.bValueExpr.Text = "fx";
        this.bValueExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bValueExpr.Click += new System.EventHandler(this.bValueExpr_Click);
        //
        // FiltersCtl
        //
        this.Controls.Add(this.bValueExpr);
        this.Controls.Add(this.bDown);
        this.Controls.Add(this.bUp);
        this.Controls.Add(this.bDelete);
        this.Controls.Add(this.dgFilters);
        this.Name = "FiltersCtl";
        this.Size = new System.Drawing.Size(488, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgFilters)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        // Remove the old filters
        XmlNode filters = null;
        _Draw.removeElement(_FilterParent,"Filters");
        for (Object __dummyForeachVar4 : this.dgFilters.Rows)
        {
            // Loop thru and add all the filters
            DataGridViewRow dr = (DataGridViewRow)__dummyForeachVar4;
            String fe = dr.Cells[0].Value instanceof String ? (String)dr.Cells[0].Value : (String)null;
            String op = dr.Cells[1].Value instanceof String ? (String)dr.Cells[1].Value : (String)null;
            String fv = dr.Cells[2].Value instanceof String ? (String)dr.Cells[2].Value : (String)null;
            if (fe == null || fe.Length <= 0 || op == null || op.Length <= 0 || fv == null || fv.Length <= 0)
                continue;
             
            if (filters == null)
                filters = _Draw.createElement(_FilterParent,"Filters",null);
             
            XmlNode fNode = _Draw.createElement(filters,"Filter",null);
            _Draw.createElement(fNode,"FilterExpression",fe);
            _Draw.createElement(fNode,"Operator",op);
            XmlNode fvNode = _Draw.createElement(fNode,"FilterValues",null);
            if (StringSupport.equals(op, "In"))
            {
                String[] vs = fv.Split(',');
                for (Object __dummyForeachVar2 : vs)
                {
                    String v = (String)__dummyForeachVar2;
                    _Draw.CreateElement(fvNode, "FilterValue", v.Trim());
                }
            }
            else if (StringSupport.equals(op, "Between"))
            {
                String[] vs = fv.Split(new char[]{ ',' }, 2);
                for (Object __dummyForeachVar3 : vs)
                {
                    String v = (String)__dummyForeachVar3;
                    _Draw.CreateElement(fvNode, "FilterValue", v.Trim());
                }
            }
            else
            {
                _Draw.createElement(fvNode,"FilterValue",fv);
            }  
        }
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!dgFilters.Rows[dgFilters.CurrentRow.Index].IsNewRow)
            // can't delete the new row
            dgFilters.Rows.RemoveAt(this.dgFilters.CurrentRow.Index);
        else
        {
            // just empty out the values
            DataGridViewRow dgrv = dgFilters.Rows[this.dgFilters.CurrentRow.Index];
            dgrv.Cells[0].Value = null;
            dgrv.Cells[1].Value = null;
            dgrv.Cells[2].Value = null;
        } 
    }

    private void bUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgFilters.CurrentRow.Index;
        if (cr <= 0)
            return ;
         
        // already at the top
        SwapRow(dgFilters.Rows[cr - 1], dgFilters.Rows[cr]);
        dgFilters.CurrentCell = dgFilters.Rows[cr - 1].Cells[dgFilters.CurrentCell.ColumnIndex];
    }

    private void bDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgFilters.CurrentRow.Index;
        if (cr < 0)
            return ;
         
        // invalid index
        if (cr + 1 >= dgFilters.Rows.Count)
            return ;
         
        // already at end
        SwapRow(dgFilters.Rows[cr + 1], dgFilters.Rows[cr]);
        dgFilters.CurrentCell = dgFilters.Rows[cr + 1].Cells[dgFilters.CurrentCell.ColumnIndex];
    }

    private void swapRow(DataGridViewRow tdr, DataGridViewRow fdr) throws Exception {
        // column 1
        Object save = tdr.Cells[0].Value;
        tdr.Cells[0].Value = fdr.Cells[0].Value;
        fdr.Cells[0].Value = save;
        // column 2
        save = tdr.Cells[1].Value;
        tdr.Cells[1].Value = fdr.Cells[1].Value;
        fdr.Cells[1].Value = save;
        // column 3
        save = tdr.Cells[2].Value;
        tdr.Cells[2].Value = fdr.Cells[2].Value;
        fdr.Cells[2].Value = save;
        return ;
    }

    private void bValueExpr_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgFilters.CurrentRow.Index;
        //if (cr < 0)
        //{	// No rows yet; create one
        //    string[] rowValues = new string[3];
        //    rowValues[0] = null;
        //    rowValues[1] = null;
        //    rowValues[2] = null;
        //    _DataTable.Rows.Add(rowValues);
        //    cr = 0;
        //}
        DataGridViewCell dgc = dgFilters.CurrentCell;
        int cc = dgc.ColumnIndex;
        String cv = dgc.Value instanceof String ? (String)dgc.Value : (String)null;
        if (cc == 1)
        {
            // This is the FilterOperator
            DialogFilterOperator fo = new DialogFilterOperator(cv);
            DialogResult dlgr = fo.ShowDialog();
            if (dlgr == DialogResult.OK)
                dgc.Value = fo.getOperator();
             
        }
        else
        {
            DialogExprEditor ee = new DialogExprEditor(_Draw,cv,_FilterParent,false);
            DialogResult dlgr = ee.ShowDialog();
            if (dlgr == DialogResult.OK)
                dgc.Value = ee.getExpression();
             
        } 
    }

}


