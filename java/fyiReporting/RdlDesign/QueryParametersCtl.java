//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:21 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DataSetValues;
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
* QueryParametersCtl provides values for the DataSet Query QueryParameters rdl elements
*/
public class QueryParametersCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private DataSetValues _dsv;
    private DataGridTextBoxColumn dgtbName = new DataGridTextBoxColumn();
    private DataGridTextBoxColumn dgtbValue = new DataGridTextBoxColumn();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.DataGrid dgParms = new System.Windows.Forms.DataGrid();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public QueryParametersCtl(DesignXmlDraw dxDraw, DataSetValues dsv) throws Exception {
        _Draw = dxDraw;
        _dsv = dsv;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        // Initialize the DataGrid columns
        dgtbName = new DataGridTextBoxColumn();
        dgtbValue = new DataGridTextBoxColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbName, this.dgtbValue });
        //
        // dgtbFE
        //
        dgtbName.HeaderText = "Parameter Name";
        dgtbName.MappingName = "Name";
        dgtbName.Width = 75;
        //
        // dgtbValue
        //
        this.dgtbValue.HeaderText = "Value";
        this.dgtbValue.MappingName = "Value";
        this.dgtbValue.Width = 75;
        //			string[] parms = _Draw.GetReportParameters(true);
        //			if (parms != null)
        //				dgtbFV.CB.Items.AddRange(parms);
        // Initialize the DataGrid
        this.dgParms.DataSource = _dsv.getQueryParameters();
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
        this.dgParms.Size = new System.Drawing.Size(384, 168);
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
        // SubreportCtl
        //
        this.Controls.Add(this.dgParms);
        this.Name = "SubreportCtl";
        this.Size = new System.Drawing.Size(464, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        return ;
    }

}


// the apply is done as part of the DataSetsCtl