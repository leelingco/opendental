//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.RDLParser;
import fyiReporting.RDL.UserReportParameter;
import fyiReporting.RdlDesign.DrillParameter;
import fyiReporting.RdlDesign.DrillParametersDialog;

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
* Drillthrough reports; pick report and specify parameters
*/
public class DrillParametersDialog  extends System.Windows.Forms.Form 
{
    private String _DrillReport = new String();
    private DataTable _DataTable = new DataTable();
    private DataGridTextBoxColumn dgtbName = new DataGridTextBoxColumn();
    private DataGridTextBoxColumn dgtbValue = new DataGridTextBoxColumn();
    private DataGridTextBoxColumn dgtbOmit = new DataGridTextBoxColumn();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bFile = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbReportFile = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DataGrid dgParms = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bRefreshParms = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DrillParametersDialog(String report, List<DrillParameter> parameters) throws Exception {
        _DrillReport = report;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        InitValues(parameters);
    }

    private void initValues(List<DrillParameter> parameters) throws Exception {
        this.tbReportFile.Text = _DrillReport;
        // Initialize the DataGrid columns
        dgtbName = new DataGridTextBoxColumn();
        dgtbValue = new DataGridTextBoxColumn();
        dgtbOmit = new DataGridTextBoxColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbName, this.dgtbValue, this.dgtbOmit });
        //
        // dgtbFE
        //
        dgtbName.HeaderText = "Parameter Name";
        dgtbName.MappingName = "ParameterName";
        dgtbName.Width = 75;
        //
        // dgtbValue
        //
        this.dgtbValue.HeaderText = "Value";
        this.dgtbValue.MappingName = "Value";
        this.dgtbValue.Width = 75;
        //
        // dgtbOmit
        //
        this.dgtbOmit.HeaderText = "Omit";
        this.dgtbOmit.MappingName = "Omit";
        this.dgtbOmit.Width = 75;
        // Initialize the DataTable
        _DataTable = new DataTable();
        _DataTable.Columns.Add(new DataColumn("ParameterName", String.class));
        _DataTable.Columns.Add(new DataColumn("Value", String.class));
        _DataTable.Columns.Add(new DataColumn("Omit", String.class));
        String[] rowValues = new String[3];
        if (parameters != null)
            for (Object __dummyForeachVar0 : parameters)
            {
                DrillParameter dp = (DrillParameter)__dummyForeachVar0;
                rowValues[0] = dp.ParameterName;
                rowValues[1] = dp.ParameterValue;
                rowValues[2] = dp.ParameterOmit;
                _DataTable.Rows.Add(rowValues);
            }
         
        // Don't allow new rows; do this by creating a DataView over the DataTable
        //			DataView dv = new DataView(_DataTable);	// this has bad side effects
        //			dv.AllowNew = false;
        this.dgParms.DataSource = _DataTable;
        DataGridTableStyle ts = dgParms.TableStyles[0];
        ts.GridColumnStyles[0].Width = 140;
        ts.GridColumnStyles[0].ReadOnly = true;
        ts.GridColumnStyles[1].Width = 140;
        ts.GridColumnStyles[2].Width = 70;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(DrillParametersDialog.class);
        this.dgParms = new System.Windows.Forms.DataGrid();
        this.dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
        this.label1 = new System.Windows.Forms.Label();
        this.tbReportFile = new System.Windows.Forms.TextBox();
        this.bFile = new System.Windows.Forms.Button();
        this.bRefreshParms = new System.Windows.Forms.Button();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).BeginInit();
        this.SuspendLayout();
        //
        // dgParms
        //
        this.dgParms.CaptionVisible = false;
        this.dgParms.DataMember = "";
        this.dgParms.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgParms.Location = new System.Drawing.Point(8, 40);
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
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(88, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Report name";
        //
        // tbReportFile
        //
        this.tbReportFile.Location = new System.Drawing.Point(104, 8);
        this.tbReportFile.Name = "tbReportFile";
        this.tbReportFile.Size = new System.Drawing.Size(312, 20);
        this.tbReportFile.TabIndex = 4;
        //
        // bFile
        //
        this.bFile.Location = new System.Drawing.Point(424, 8);
        this.bFile.Name = "bFile";
        this.bFile.Size = new System.Drawing.Size(24, 23);
        this.bFile.TabIndex = 5;
        this.bFile.Text = "...";
        this.bFile.Click += new System.EventHandler(this.bFile_Click);
        //
        // bRefreshParms
        //
        this.bRefreshParms.Location = new System.Drawing.Point(400, 40);
        this.bRefreshParms.Name = "bRefreshParms";
        this.bRefreshParms.Size = new System.Drawing.Size(56, 23);
        this.bRefreshParms.TabIndex = 10;
        this.bRefreshParms.Text = "Refresh";
        this.bRefreshParms.Click += new System.EventHandler(this.bRefreshParms_Click);
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.OK;
        this.bOK.Location = new System.Drawing.Point(288, 216);
        this.bOK.Name = "bOK";
        this.bOK.Size = new System.Drawing.Size(75, 23);
        this.bOK.TabIndex = 11;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.CausesValidation = false;
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(376, 216);
        this.bCancel.Name = "bCancel";
        this.bCancel.Size = new System.Drawing.Size(75, 23);
        this.bCancel.TabIndex = 12;
        this.bCancel.Text = "Cancel";
        //
        // DrillParametersDialog
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.CausesValidation = false;
        this.ClientSize = new System.Drawing.Size(464, 248);
        this.ControlBox = false;
        this.Controls.Add(this.bOK);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.bRefreshParms);
        this.Controls.Add(this.bFile);
        this.Controls.Add(this.tbReportFile);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.dgParms);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DrillParametersDialog";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.Text = "Specify Drillthrough Report and Parameters";
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    public String getDrillthroughReport() throws Exception {
        return this._DrillReport;
    }

    public List<DrillParameter> getDrillParameters() throws Exception {
        List<DrillParameter> parms = new List<DrillParameter>();
        for (Object __dummyForeachVar1 : _DataTable.Rows)
        {
            // Loop thru and add all the filters
            DataRow dr = (DataRow)__dummyForeachVar1;
            if (dr[0] == DBNull.Value || dr[1] == DBNull.Value)
                continue;
             
            String name = (String)dr[0];
            String val = (String)dr[1];
            String omit = dr[2] == DBNull.Value ? "false" : (String)dr[2];
            if (name.Length <= 0 || val.Length <= 0)
                continue;
             
            DrillParameter dp = new DrillParameter(name,val,omit);
            parms.Add(dp);
        }
        if (parms.Count == 0)
            return null;
         
        return parms;
    }

    private void bFile_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Report files (*.rdl)|*.rdl" + "All files (*.*)|*.*|";
        ofd.FilterIndex = 1;
        ofd.FileName = "*.rdl";
        ofd.Title = "Specify Report File Name";
        ofd.DefaultExt = "rdl";
        ofd.AddExtension = true;
        if (ofd.ShowDialog() == DialogResult.OK)
        {
            String file = Path.GetFileNameWithoutExtension(ofd.FileName);
            tbReportFile.Text = file;
        }
         
    }

    private void bRefreshParms_Click(Object sender, System.EventArgs e) throws Exception {
        // Obtain the source
        Cursor savec = Cursor.Current;
        Cursor.Current = Cursors.WaitCursor;
        try
        {
            // this can take some time
            String filename = "";
            if (tbReportFile.Text.Length > 0)
                filename = tbReportFile.Text + ".rdl";
             
            filename = getFileNameWithPath(filename);
            String source = this.getSource(filename);
            if (source == null)
                return ;
             
            // error: message already displayed
            // Compile the report
            fyiReporting.RDL.Report report = this.getReport(source,filename);
            if (report == null)
                return ;
             
            // error: message already displayed
            ICollection rps = report.getUserReportParameters();
            String[] rowValues = new String[3];
            _DataTable.Rows.Clear();
            for (Object __dummyForeachVar2 : rps)
            {
                UserReportParameter rp = (UserReportParameter)__dummyForeachVar2;
                rowValues[0] = rp.getName();
                rowValues[1] = "";
                rowValues[2] = "false";
                _DataTable.Rows.Add(rowValues);
            }
            this.dgParms.Refresh();
            this.dgParms.Focus();
        }
        finally
        {
            Cursor.Current = savec;
        }
    }

    private String getFileNameWithPath(String file) throws Exception {
        return file;
    }

    // todo: should prefix this with the path of the open file
    private String getSource(String file) throws Exception {
        StreamReader fs = null;
        String prog = null;
        try
        {
            fs = new StreamReader(file);
            prog = fs.ReadToEnd();
        }
        catch (Exception e)
        {
            prog = null;
            MessageBox.Show(e.Message, "Error reading report file");
        }
        finally
        {
            if (fs != null)
                fs.Close();
             
        }
        return prog;
    }

    private fyiReporting.RDL.Report getReport(String prog, String file) throws Exception {
        // Now parse the file
        RDLParser rdlp;
        fyiReporting.RDL.Report r;
        try
        {
            rdlp = new RDLParser(prog);
            String folder = Path.GetDirectoryName(file);
            if (StringSupport.equals(folder, ""))
            {
                folder = Environment.CurrentDirectory;
            }
             
            rdlp.setFolder(folder);
            r = rdlp.parse();
            if (r.getErrorMaxSeverity() > 4)
            {
                MessageBox.Show(String.Format("Report {0} has errors and cannot be processed.", "Report"));
                r = null;
            }
             
        }
        catch (Exception e)
        {
            // don't return when severe errors
            r = null;
            MessageBox.Show(e.Message, "Report load failed");
        }

        return r;
    }

    private void drillParametersDialog_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        for (Object __dummyForeachVar3 : _DataTable.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar3;
            if (dr[1] == DBNull.Value)
            {
                e.Cancel = true;
                break;
            }
             
            String val = (String)dr[1];
            if (val.Length <= 0)
            {
                e.Cancel = true;
                break;
            }
             
        }
        if (e.Cancel)
        {
            MessageBox.Show("Value must be specified for every parameter", this.Text);
        }
         
    }

    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        CancelEventArgs ce = new CancelEventArgs();
        DrillParametersDialog_Validating(this, ce);
        if (ce.Cancel)
        {
            DialogResult = DialogResult.None;
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

}


