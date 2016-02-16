//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.RDLParser;
import fyiReporting.RDL.UserReportParameter;
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
* Filters specification: used for DataRegions (List, Chart, Table, Matrix), DataSets, group instances
*/
public class SubreportCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private XmlNode _Subreport = new XmlNode();
    private DataTable _DataTable = new DataTable();
    private DataGridTextBoxColumn dgtbName = new DataGridTextBoxColumn();
    private DataGridTextBoxColumn dgtbValue = new DataGridTextBoxColumn();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bFile = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbReportFile = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbNoRows = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox chkMergeTrans = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.DataGrid dgParms = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bRefreshParms = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public SubreportCtl(DesignXmlDraw dxDraw, XmlNode subReport) throws Exception {
        _Draw = dxDraw;
        _Subreport = subReport;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        this.tbReportFile.Text = _Draw.getElementValue(_Subreport,"ReportName","");
        this.tbNoRows.Text = _Draw.getElementValue(_Subreport,"NoRows","");
        this.chkMergeTrans.Checked = StringSupport.equals(_Draw.getElementValue(_Subreport,"MergeTransactions","false").ToLower(), "true");
        // Initialize the DataGrid columns
        dgtbName = new DataGridTextBoxColumn();
        dgtbValue = new DataGridTextBoxColumn();
        this.dgTableStyle.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ this.dgtbName, this.dgtbValue });
        //
        // dgtbFE
        //
        dgtbName.HeaderText = "Parameter Name";
        dgtbName.MappingName = "ParameterName";
        dgtbName.Width = 75;
        // Get the parent's dataset name
        //			string dataSetName = _Draw.GetDataSetNameValue(_FilterParent);
        //
        //			string[] fields = _Draw.GetFields(dataSetName, true);
        //			if (fields != null)
        //				dgtbFE.CB.Items.AddRange(fields);
        //
        // dgtbValue
        //
        this.dgtbValue.HeaderText = "Value";
        this.dgtbValue.MappingName = "Value";
        this.dgtbValue.Width = 75;
        //			string[] parms = _Draw.GetReportParameters(true);
        //			if (parms != null)
        //				dgtbFV.CB.Items.AddRange(parms);
        // Initialize the DataTable
        _DataTable = new DataTable();
        _DataTable.Columns.Add(new DataColumn("ParameterName", String.class));
        _DataTable.Columns.Add(new DataColumn("Value", String.class));
        String[] rowValues = new String[2];
        XmlNode parameters = _Draw.getNamedChildNode(_Subreport,"Parameters");
        if (parameters != null)
            for (Object __dummyForeachVar0 : parameters.ChildNodes)
            {
                XmlNode pNode = (XmlNode)__dummyForeachVar0;
                if (pNode.NodeType != XmlNodeType.Element || !StringSupport.equals(pNode.Name, "Parameter"))
                    continue;
                 
                rowValues[0] = _Draw.getElementAttribute(pNode,"Name","");
                rowValues[1] = _Draw.getElementValue(pNode,"Value","");
                _DataTable.Rows.Add(rowValues);
            }
         
        // Don't allow users to add their own rows
        //			DataView dv = new DataView(_DataTable);		// bad side effect
        //			dv.AllowNew = false;
        this.dgParms.DataSource = _DataTable;
        DataGridTableStyle ts = dgParms.TableStyles[0];
        ts.GridColumnStyles[0].Width = 140;
        ts.GridColumnStyles[0].ReadOnly = true;
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
        this.label1 = new System.Windows.Forms.Label();
        this.tbReportFile = new System.Windows.Forms.TextBox();
        this.bFile = new System.Windows.Forms.Button();
        this.tbNoRows = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.chkMergeTrans = new System.Windows.Forms.CheckBox();
        this.bRefreshParms = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).BeginInit();
        this.SuspendLayout();
        //
        // dgParms
        //
        this.dgParms.CaptionVisible = false;
        this.dgParms.DataMember = "";
        this.dgParms.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgParms.Location = new System.Drawing.Point(8, 112);
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(88, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Subreport name";
        //
        // tbReportFile
        //
        this.tbReportFile.Location = new System.Drawing.Point(104, 8);
        this.tbReportFile.Name = "tbReportFile";
        this.tbReportFile.Size = new System.Drawing.Size(312, 20);
        this.tbReportFile.TabIndex = 4;
        this.tbReportFile.Text = "";
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
        // tbNoRows
        //
        this.tbNoRows.Location = new System.Drawing.Point(104, 40);
        this.tbNoRows.Name = "tbNoRows";
        this.tbNoRows.Size = new System.Drawing.Size(312, 20);
        this.tbNoRows.TabIndex = 7;
        this.tbNoRows.Text = "";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(96, 23);
        this.label2.TabIndex = 8;
        this.label2.Text = "No rows message";
        //
        // chkMergeTrans
        //
        this.chkMergeTrans.Location = new System.Drawing.Point(8, 72);
        this.chkMergeTrans.Name = "chkMergeTrans";
        this.chkMergeTrans.Size = new System.Drawing.Size(376, 24);
        this.chkMergeTrans.TabIndex = 9;
        this.chkMergeTrans.Text = "Use same Data Source connections as parent report when possible";
        //
        // bRefreshParms
        //
        this.bRefreshParms.Location = new System.Drawing.Point(392, 120);
        this.bRefreshParms.Name = "bRefreshParms";
        this.bRefreshParms.Size = new System.Drawing.Size(56, 23);
        this.bRefreshParms.TabIndex = 10;
        this.bRefreshParms.Text = "Refresh";
        this.bRefreshParms.Click += new System.EventHandler(this.bRefreshParms_Click);
        //
        // SubreportCtl
        //
        this.Controls.Add(this.bRefreshParms);
        this.Controls.Add(this.chkMergeTrans);
        this.Controls.Add(this.tbNoRows);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.bFile);
        this.Controls.Add(this.tbReportFile);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.dgParms);
        this.Name = "SubreportCtl";
        this.Size = new System.Drawing.Size(464, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgParms)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        if (this.tbReportFile.Text.Length > 0)
            return true;
         
        MessageBox.Show("Subreport file must be specified.", "Subreport");
        return false;
    }

    public void apply() throws Exception {
        _Draw.SetElement(_Subreport, "ReportName", this.tbReportFile.Text);
        if (this.tbNoRows.Text.Trim().Length == 0)
            _Draw.removeElement(_Subreport,"NoRows");
        else
            _Draw.SetElement(_Subreport, "NoRows", tbNoRows.Text); 
        _Draw.setElement(_Subreport,"MergeTransactions",this.chkMergeTrans.Checked ? "true" : "false");
        // Remove the old filters
        XmlNode parms = _Draw.getCreateNamedChildNode(_Subreport,"Parameters");
        while (parms.FirstChild != null)
        {
            parms.RemoveChild(parms.FirstChild);
        }
        for (Object __dummyForeachVar1 : _DataTable.Rows)
        {
            // Loop thru and add all the filters
            DataRow dr = (DataRow)__dummyForeachVar1;
            if (dr[0] == DBNull.Value || dr[1] == DBNull.Value)
                continue;
             
            String name = (String)dr[0];
            String val = (String)dr[1];
            if (name.Length <= 0 || val.Length <= 0)
                continue;
             
            XmlNode pNode = _Draw.createElement(parms,"Parameter",null);
            _Draw.setElementAttribute(pNode,"Name",name);
            _Draw.setElement(pNode,"Value",val);
        }
        if (!parms.HasChildNodes)
            _Subreport.RemoveChild(parms);
         
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
        String filename = "";
        if (tbReportFile.Text.Length > 0)
            filename = tbReportFile.Text + ".rdl";
         
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
        String[] rowValues = new String[2];
        _DataTable.Rows.Clear();
        for (Object __dummyForeachVar2 : rps)
        {
            UserReportParameter rp = (UserReportParameter)__dummyForeachVar2;
            rowValues[0] = rp.getName();
            rowValues[1] = "";
            _DataTable.Rows.Add(rowValues);
        }
        this.dgParms.Refresh();
    }

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
                folder = Environment.CurrentDirectory;
             
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

}


