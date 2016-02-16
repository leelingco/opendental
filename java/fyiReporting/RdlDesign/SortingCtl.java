//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
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
* Sorting specification
*/
public class SortingCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private XmlNode _SortingParent = new XmlNode();
    private DataTable _DataTable = new DataTable();
    private DataGridTextBoxColumn dgtbExpr = new DataGridTextBoxColumn();
    private DataGridBoolColumn dgtbDir = new DataGridBoolColumn();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Button bUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGrid dgSorting = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bValueExpr = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public SortingCtl(DesignXmlDraw dxDraw, XmlNode sortingParent) throws Exception {
        _Draw = dxDraw;
        _SortingParent = sortingParent;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        // Initialize the DataGrid columns
        dgtbExpr = new DataGridTextBoxColumn();
        dgtbDir = new DataGridBoolColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbExpr, this.dgtbDir });
        //
        // dgtbExpr
        //
        dgtbExpr.HeaderText = "Sort Expression";
        dgtbExpr.MappingName = "SortExpression";
        dgtbExpr.Width = 75;
        // Get the parent's dataset name
        //			string dataSetName = _Draw.GetDataSetNameValue(_SortingParent);
        //
        //			string[] fields = _Draw.GetFields(dataSetName, true);
        //			if (fields != null)
        //				dgtbExpr.CB.Items.AddRange(fields);
        //
        // dgtbDir
        //
        dgtbDir.HeaderText = "Sort Ascending";
        dgtbDir.MappingName = "Direction";
        dgtbDir.Width = 70;
        dgtbDir.AllowNull = false;
        // Initialize the DataTable
        _DataTable = new DataTable();
        _DataTable.Columns.Add(new DataColumn("SortExpression", String.class));
        _DataTable.Columns.Add(new DataColumn("Direction", boolean.class));
        Object[] rowValues = new Object[2];
        XmlNode sorts = _Draw.getNamedChildNode(_SortingParent,"Sorting");
        if (sorts != null)
            for (Object __dummyForeachVar0 : sorts.ChildNodes)
            {
                XmlNode sNode = (XmlNode)__dummyForeachVar0;
                if (sNode.NodeType != XmlNodeType.Element || !StringSupport.equals(sNode.Name, "SortBy"))
                    continue;
                 
                rowValues[0] = _Draw.getElementValue(sNode,"SortExpression","");
                if (StringSupport.equals(_Draw.getElementValue(sNode,"Direction","Ascending"), "Ascending"))
                    rowValues[1] = true;
                else
                    rowValues[1] = false; 
                _DataTable.Rows.Add(rowValues);
            }
         
        this.dgSorting.DataSource = _DataTable;
        DataGridTableStyle ts = dgSorting.TableStyles[0];
        ts.PreferredRowHeight = 14;
        ts.GridColumnStyles[0].Width = 240;
        ts.GridColumnStyles[1].Width = 90;
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
        this.dgSorting = new System.Windows.Forms.DataGrid();
        this.dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
        this.bDelete = new System.Windows.Forms.Button();
        this.bUp = new System.Windows.Forms.Button();
        this.bDown = new System.Windows.Forms.Button();
        this.bValueExpr = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgSorting)).BeginInit();
        this.SuspendLayout();
        //
        // dgSorting
        //
        this.dgSorting.CaptionVisible = false;
        this.dgSorting.DataMember = "";
        this.dgSorting.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgSorting.Location = new System.Drawing.Point(8, 8);
        this.dgSorting.Name = "dgSorting";
        this.dgSorting.Size = new System.Drawing.Size(376, 264);
        this.dgSorting.TabIndex = 0;
        this.dgSorting.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyle });
        //
        // dgTableStyle
        //
        this.dgTableStyle.AllowSorting = false;
        this.dgTableStyle.DataGrid = this.dgSorting;
        this.dgTableStyle.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyle.MappingName = "";
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(392, 40);
        this.bDelete.Name = "bDelete";
        this.bDelete.Size = new System.Drawing.Size(48, 23);
        this.bDelete.TabIndex = 2;
        this.bDelete.Text = "Delete";
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // bUp
        //
        this.bUp.Location = new System.Drawing.Point(392, 72);
        this.bUp.Name = "bUp";
        this.bUp.Size = new System.Drawing.Size(48, 23);
        this.bUp.TabIndex = 3;
        this.bUp.Text = "Up";
        this.bUp.Click += new System.EventHandler(this.bUp_Click);
        //
        // bDown
        //
        this.bDown.Location = new System.Drawing.Point(392, 104);
        this.bDown.Name = "bDown";
        this.bDown.Size = new System.Drawing.Size(48, 23);
        this.bDown.TabIndex = 4;
        this.bDown.Text = "Down";
        this.bDown.Click += new System.EventHandler(this.bDown_Click);
        //
        // bValueExpr
        //
        this.bValueExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bValueExpr.Location = new System.Drawing.Point(392, 16);
        this.bValueExpr.Name = "bValueExpr";
        this.bValueExpr.Size = new System.Drawing.Size(22, 16);
        this.bValueExpr.TabIndex = 1;
        this.bValueExpr.Tag = "value";
        this.bValueExpr.Text = "fx";
        this.bValueExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bValueExpr.Click += new System.EventHandler(this.bValueExpr_Click);
        //
        // SortingCtl
        //
        this.Controls.Add(this.bValueExpr);
        this.Controls.Add(this.bDown);
        this.Controls.Add(this.bUp);
        this.Controls.Add(this.bDelete);
        this.Controls.Add(this.dgSorting);
        this.Name = "SortingCtl";
        this.Size = new System.Drawing.Size(488, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgSorting)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        // Remove the old filters
        XmlNode sorts = null;
        _Draw.removeElement(_SortingParent,"Sorting");
        for (Object __dummyForeachVar1 : _DataTable.Rows)
        {
            // Loop thru and add all the filters
            DataRow dr = (DataRow)__dummyForeachVar1;
            if (dr[0] == DBNull.Value)
                continue;
             
            String expr = (String)dr[0];
            boolean dir = dr[1] == DBNull.Value ? true : (boolean)dr[1];
            if (expr.Length <= 0)
                continue;
             
            if (sorts == null)
                sorts = _Draw.createElement(_SortingParent,"Sorting",null);
             
            XmlNode sNode = _Draw.createElement(sorts,"SortBy",null);
            _Draw.createElement(sNode,"SortExpression",expr);
            _Draw.createElement(sNode,"Direction",dir ? "Ascending" : "Descending");
        }
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        this._DataTable.Rows.RemoveAt(this.dgSorting.CurrentRowIndex);
    }

    private void bUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgSorting.CurrentRowIndex;
        if (cr <= 0)
            return ;
         
        // already at the top
        SwapRow(_DataTable.Rows[cr - 1], _DataTable.Rows[cr]);
        dgSorting.CurrentRowIndex = cr - 1;
    }

    private void bDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgSorting.CurrentRowIndex;
        if (cr < 0)
            return ;
         
        // invalid index
        if (cr + 1 >= _DataTable.Rows.Count)
            return ;
         
        // already at end
        SwapRow(_DataTable.Rows[cr + 1], _DataTable.Rows[cr]);
        dgSorting.CurrentRowIndex = cr + 1;
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

    private void bValueExpr_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgSorting.CurrentRowIndex;
        if (cr < 0)
        {
            // No rows yet; create one
            String[] rowValues = new String[2];
            rowValues[0] = null;
            rowValues[1] = null;
            _DataTable.Rows.Add(rowValues);
            cr = 0;
        }
         
        int cc = 0;
        DataRow dr = _DataTable.Rows[cr];
        String cv = dr[cc] instanceof String ? (String)dr[cc] : (String)null;
        DialogExprEditor ee = new DialogExprEditor(_Draw,cv,_SortingParent,false);
        DialogResult dlgr = ee.ShowDialog();
        if (dlgr == DialogResult.OK)
            dr[cc] = ee.getExpression();
         
    }

}


