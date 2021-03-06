//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.ParameterValueItem;

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
* DialogValidValues allow user to provide ValidValues: Value and Label lists
*/
public class DialogValidValues  extends System.Windows.Forms.Form 
{
    private DataTable _DataTable = new DataTable();
    private DataGridTextBoxColumn dgtbLabel = new DataGridTextBoxColumn();
    private DataGridTextBoxColumn dgtbValue = new DataGridTextBoxColumn();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.DataGrid dgParms = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogValidValues(List<ParameterValueItem> list) throws Exception {
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        InitValues(list);
    }

    public List<ParameterValueItem> getValidValues() throws Exception {
        List<ParameterValueItem> list = new List<ParameterValueItem>();
        for (Object __dummyForeachVar0 : _DataTable.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar0;
            if (dr[0] == DBNull.Value)
                continue;
             
            String val = (String)dr[0];
            if (val.Length <= 0)
                continue;
             
            String label = new String();
            if (dr[1] == DBNull.Value)
                label = null;
            else
                label = (String)dr[1]; 
            ParameterValueItem pvi = new ParameterValueItem();
            pvi.Value = val;
            pvi.Label = label;
            list.Add(pvi);
        }
        return list.Count > 0 ? list : null;
    }

    private void initValues(List<ParameterValueItem> list) throws Exception {
        // Initialize the DataGrid columns
        dgtbLabel = new DataGridTextBoxColumn();
        dgtbValue = new DataGridTextBoxColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbValue, this.dgtbLabel });
        //
        // dgtbFE
        //
        dgtbValue.HeaderText = "Value";
        dgtbValue.MappingName = "Value";
        dgtbValue.Width = 75;
        //
        // dgtbValue
        //
        this.dgtbLabel.HeaderText = "Label";
        this.dgtbLabel.MappingName = "Label";
        this.dgtbLabel.Width = 75;
        // Initialize the DataGrid
        //this.dgParms.DataSource = _dsv.QueryParameters;
        _DataTable = new DataTable();
        _DataTable.Columns.Add(new DataColumn("Value", String.class));
        _DataTable.Columns.Add(new DataColumn("Label", String.class));
        String[] rowValues = new String[2];
        if (list != null)
            for (Object __dummyForeachVar1 : list)
            {
                ParameterValueItem pvi = (ParameterValueItem)__dummyForeachVar1;
                rowValues[0] = pvi.Value;
                rowValues[1] = pvi.Label;
                _DataTable.Rows.Add(rowValues);
            }
         
        this.dgParms.DataSource = _DataTable;
        /**
        * /
        */
        DataGridTableStyle ts = dgParms.TableStyles[0];
        ts.GridColumnStyles[0].Width = 140;
        ts.GridColumnStyles[1].Width = 140;
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
        this.dgParms = new System.Windows.Forms.DataGrid();
        this.dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.bDelete = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).BeginInit();
        this.SuspendLayout();
        //
        // dgParms
        //
        this.dgParms.CaptionVisible = false;
        this.dgParms.DataMember = "";
        this.dgParms.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgParms.Location = new System.Drawing.Point(8, 8);
        this.dgParms.Name = "dgParms";
        this.dgParms.Size = new System.Drawing.Size(320, 168);
        this.dgParms.TabIndex = 2;
        this.dgParms.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyle });
        //
        // dgTableStyle
        //
        this.dgTableStyle.AllowSorting = false;
        this.dgTableStyle.DataGrid = this.dgParms;
        this.dgTableStyle.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyle.MappingName = "";
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.OK;
        this.bOK.Location = new System.Drawing.Point(216, 192);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 3;
        this.bOK.Text = "OK";
        //
        // bCancel
        //
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(312, 192);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 4;
        this.bCancel.Text = "Cancel";
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(336, 16);
        this.bDelete.Name = "bDelete";
        this.bDelete.Size = new System.Drawing.Size(48, 23);
        this.bDelete.TabIndex = 5;
        this.bDelete.Text = "Delete";
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // DialogValidValues
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(392, 222);
        this.ControlBox = false;
        this.Controls.Add(this.bDelete);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.bOK);
        this.Controls.Add(this.dgParms);
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogValidValues";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.Text = "Valid Values";
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).EndInit();
        this.ResumeLayout(false);
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        this._DataTable.Rows.RemoveAt(this.dgParms.CurrentRowIndex);
    }

}


