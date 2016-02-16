//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DataSetValues;
import fyiReporting.RdlDesign.DesignerUtility;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.IProperty;
import fyiReporting.RdlDesign.SqlColumn;
import fyiReporting.RdlDesign.SQLCtl;

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
* Summary description for StyleCtl.
*/
public class DataSetsCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private boolean _UseTypenameQualified = false;
    private DesignXmlDraw _Draw;
    private XmlNode _dsNode = new XmlNode();
    private DataSetValues _dsv;
    private System.Windows.Forms.ComboBox cbDataSource = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox tbDSName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbSQL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DataGridTableStyle dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Button bDeleteField = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bEditSQL = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label lDataSource = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lDataSetName = new System.Windows.Forms.Label();
    private System.Windows.Forms.DataGrid dgFields = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bRefresh = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown tbTimeout = new System.Windows.Forms.NumericUpDown();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DataSetsCtl(DesignXmlDraw dxDraw, XmlNode dsNode) throws Exception {
        _Draw = dxDraw;
        _dsNode = dsNode;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    public DataSetValues getDSV() throws Exception {
        return _dsv;
    }

    private void initValues() throws Exception {
        // Initialize the DataGrid columns
        DataGridTextBoxColumn dgtbName = new DataGridTextBoxColumn();
        DataGridTextBoxColumn dgtbQueryName = new DataGridTextBoxColumn();
        DataGridTextBoxColumn dgtbValue = new DataGridTextBoxColumn();
        DataGridTextBoxColumn dgtbTypeName = new DataGridTextBoxColumn();
        this.dataGridTableStyle1.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ dgtbName, dgtbQueryName, dgtbValue, dgtbTypeName });
        // dgtbName
        dgtbName.Format = "";
        dgtbName.FormatInfo = null;
        dgtbName.HeaderText = "Name";
        dgtbName.MappingName = "Name";
        dgtbName.Width = 75;
        // dgtbQueryName
        dgtbQueryName.Format = "";
        dgtbQueryName.FormatInfo = null;
        dgtbQueryName.HeaderText = "Query Column Name";
        dgtbQueryName.MappingName = "QueryName";
        dgtbQueryName.Width = 80;
        // dgtbValue
        //
        dgtbValue.Format = "";
        dgtbValue.FormatInfo = null;
        dgtbValue.HeaderText = "Value";
        dgtbValue.MappingName = "Value";
        dgtbValue.Width = 175;
        // dgtbTypeName
        dgtbTypeName.Format = "";
        dgtbTypeName.FormatInfo = null;
        dgtbTypeName.HeaderText = "TypeName";
        dgtbTypeName.MappingName = "TypeName";
        dgtbTypeName.Width = 150;
        // cbDataSource
        cbDataSource.Items.AddRange(_Draw.getDataSourceNames());
        //
        // Obtain the existing DataSet info
        //
        XmlNode dNode = this._dsNode;
        XmlAttribute nAttr = dNode.Attributes["Name"];
        _dsv = new DataSetValues(nAttr == null ? "" : nAttr.Value);
        _dsv.setNode(dNode);
        XmlNode ctNode = DesignXmlDraw.findNextInHierarchy(dNode,"Query","CommandText");
        _dsv.setCommandText(ctNode == null ? "" : ctNode.InnerText);
        XmlNode datasource = DesignXmlDraw.findNextInHierarchy(dNode,"Query","DataSourceName");
        _dsv.setDataSourceName(datasource == null ? "" : datasource.InnerText);
        XmlNode timeout = DesignXmlDraw.findNextInHierarchy(dNode,"Query","Timeout");
        try
        {
            _dsv.setTimeout(timeout == null ? 0 : Convert.ToInt32(timeout.InnerText));
        }
        catch (Exception __dummyCatchVar0)
        {
            // we don't stop just because timeout isn't convertable
            _dsv.setTimeout(0);
        }

        // Get QueryParameters; they are loaded here but used by the QueryParametersCtl
        _dsv.setQueryParameters(new DataTable());
        _dsv.getQueryParameters().Columns.Add(new DataColumn("Name", String.class));
        _dsv.getQueryParameters().Columns.Add(new DataColumn("Value", String.class));
        XmlNode qpNode = DesignXmlDraw.findNextInHierarchy(dNode,"Query","QueryParameters");
        if (qpNode != null)
        {
            String[] rowValues = new String[2];
            for (Object __dummyForeachVar0 : qpNode.ChildNodes)
            {
                XmlNode qNode = (XmlNode)__dummyForeachVar0;
                if (!StringSupport.equals(qNode.Name, "QueryParameter"))
                    continue;
                 
                XmlAttribute xAttr = qNode.Attributes["Name"];
                if (xAttr == null)
                    continue;
                 
                rowValues[0] = xAttr.Value;
                rowValues[1] = _Draw.getElementValue(qNode,"Value","");
                _dsv.getQueryParameters().Rows.Add(rowValues);
            }
        }
         
        // Get Fields
        _dsv.setFields(new DataTable());
        _dsv.getFields().Columns.Add(new DataColumn("Name", String.class));
        _dsv.getFields().Columns.Add(new DataColumn("QueryName", String.class));
        _dsv.getFields().Columns.Add(new DataColumn("Value", String.class));
        _dsv.getFields().Columns.Add(new DataColumn("TypeName", String.class));
        XmlNode fsNode = _Draw.getNamedChildNode(dNode,"Fields");
        if (fsNode != null)
        {
            String[] rowValues = new String[4];
            for (Object __dummyForeachVar1 : fsNode.ChildNodes)
            {
                XmlNode fNode = (XmlNode)__dummyForeachVar1;
                if (!StringSupport.equals(fNode.Name, "Field"))
                    continue;
                 
                XmlAttribute xAttr = fNode.Attributes["Name"];
                if (xAttr == null)
                    continue;
                 
                rowValues[0] = xAttr.Value;
                rowValues[1] = _Draw.getElementValue(fNode,"DataField","");
                rowValues[2] = _Draw.getElementValue(fNode,"Value","");
                String typename = null;
                typename = _Draw.getElementValue(fNode,"TypeName",null);
                if (typename == null)
                {
                    typename = _Draw.getElementValue(fNode,"rd:TypeName",null);
                    if (typename != null)
                        _UseTypenameQualified = true;
                     
                }
                 
                // we got it qualified so we'll generate qualified
                rowValues[3] = typename == null ? "" : typename;
                _dsv.getFields().Rows.Add(rowValues);
            }
        }
         
        this.tbDSName.Text = _dsv.getName();
        this.tbSQL.Text = _dsv.getCommandText();
        this.cbDataSource.Text = _dsv.getDataSourceName();
        dgFields.DataSource = _dsv.getFields();
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
        this.cbDataSource = new System.Windows.Forms.ComboBox();
        this.lDataSource = new System.Windows.Forms.Label();
        this.tbDSName = new System.Windows.Forms.TextBox();
        this.lDataSetName = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.tbSQL = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.dgFields = new System.Windows.Forms.DataGrid();
        this.dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
        this.bDeleteField = new System.Windows.Forms.Button();
        this.bEditSQL = new System.Windows.Forms.Button();
        this.bRefresh = new System.Windows.Forms.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.tbTimeout = new System.Windows.Forms.NumericUpDown();
        ((System.ComponentModel.ISupportInitialize)(this.dgFields)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.tbTimeout)).BeginInit();
        this.SuspendLayout();
        //
        // cbDataSource
        //
        this.cbDataSource.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataSource.Location = new System.Drawing.Point(296, 8);
        this.cbDataSource.Name = "cbDataSource";
        this.cbDataSource.Size = new System.Drawing.Size(144, 21);
        this.cbDataSource.TabIndex = 1;
        this.cbDataSource.SelectedIndexChanged += new System.EventHandler(this.cbDataSource_SelectedIndexChanged);
        //
        // lDataSource
        //
        this.lDataSource.Location = new System.Drawing.Point(224, 8);
        this.lDataSource.Name = "lDataSource";
        this.lDataSource.Size = new System.Drawing.Size(72, 23);
        this.lDataSource.TabIndex = 21;
        this.lDataSource.Text = "Data Source";
        this.lDataSource.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // tbDSName
        //
        this.tbDSName.Location = new System.Drawing.Point(64, 8);
        this.tbDSName.Name = "tbDSName";
        this.tbDSName.Size = new System.Drawing.Size(144, 20);
        this.tbDSName.TabIndex = 0;
        this.tbDSName.Text = "";
        this.tbDSName.TextChanged += new System.EventHandler(this.tbDSName_TextChanged);
        //
        // lDataSetName
        //
        this.lDataSetName.Location = new System.Drawing.Point(8, 8);
        this.lDataSetName.Name = "lDataSetName";
        this.lDataSetName.Size = new System.Drawing.Size(48, 16);
        this.lDataSetName.TabIndex = 19;
        this.lDataSetName.Text = "Name";
        this.lDataSetName.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 40);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 16);
        this.label1.TabIndex = 23;
        this.label1.Text = "SQL Select";
        //
        // tbSQL
        //
        this.tbSQL.Location = new System.Drawing.Point(8, 56);
        this.tbSQL.Multiline = true;
        this.tbSQL.Name = "tbSQL";
        this.tbSQL.Size = new System.Drawing.Size(376, 80);
        this.tbSQL.TabIndex = 5;
        this.tbSQL.Text = "textBox1";
        this.tbSQL.TextChanged += new System.EventHandler(this.tbSQL_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 136);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 16);
        this.label2.TabIndex = 25;
        this.label2.Text = "Fields";
        //
        // dgFields
        //
        this.dgFields.CaptionVisible = false;
        this.dgFields.DataMember = "";
        this.dgFields.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgFields.Location = new System.Drawing.Point(8, 152);
        this.dgFields.Name = "dgFields";
        this.dgFields.Size = new System.Drawing.Size(376, 104);
        this.dgFields.TabIndex = 8;
        this.dgFields.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dataGridTableStyle1 });
        //
        // dataGridTableStyle1
        //
        this.dataGridTableStyle1.DataGrid = this.dgFields;
        this.dataGridTableStyle1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dataGridTableStyle1.MappingName = "";
        //
        // bDeleteField
        //
        this.bDeleteField.Location = new System.Drawing.Point(392, 168);
        this.bDeleteField.Name = "bDeleteField";
        this.bDeleteField.Size = new System.Drawing.Size(48, 23);
        this.bDeleteField.TabIndex = 9;
        this.bDeleteField.Text = "Delete";
        this.bDeleteField.Click += new System.EventHandler(this.bDeleteField_Click);
        //
        // bEditSQL
        //
        this.bEditSQL.Location = new System.Drawing.Point(392, 64);
        this.bEditSQL.Name = "bEditSQL";
        this.bEditSQL.Size = new System.Drawing.Size(48, 23);
        this.bEditSQL.TabIndex = 6;
        this.bEditSQL.Text = "SQL...";
        this.bEditSQL.Click += new System.EventHandler(this.bEditSQL_Click);
        //
        // bRefresh
        //
        this.bRefresh.Location = new System.Drawing.Point(392, 96);
        this.bRefresh.Name = "bRefresh";
        this.bRefresh.Size = new System.Drawing.Size(52, 32);
        this.bRefresh.TabIndex = 7;
        this.bRefresh.Text = "Refresh Fields";
        this.bRefresh.Click += new System.EventHandler(this.bRefresh_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(224, 40);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(56, 16);
        this.label3.TabIndex = 27;
        this.label3.Text = "Timeout";
        //
        // tbTimeout
        //
        this.tbTimeout.Location = new System.Drawing.Point(296, 32);
        this.tbTimeout.Maximum = new System.Decimal(new int[]{ 2147483647, 0, 0, 0 });
        this.tbTimeout.Name = "tbTimeout";
        this.tbTimeout.Size = new System.Drawing.Size(104, 20);
        this.tbTimeout.TabIndex = 2;
        this.tbTimeout.ThousandsSeparator = true;
        this.tbTimeout.ValueChanged += new System.EventHandler(this.tbTimeout_ValueChanged);
        //
        // DataSetsCtl
        //
        this.Controls.Add(this.tbTimeout);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.bRefresh);
        this.Controls.Add(this.bEditSQL);
        this.Controls.Add(this.bDeleteField);
        this.Controls.Add(this.dgFields);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.tbSQL);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.cbDataSource);
        this.Controls.Add(this.lDataSource);
        this.Controls.Add(this.tbDSName);
        this.Controls.Add(this.lDataSetName);
        this.Name = "DataSetsCtl";
        this.Size = new System.Drawing.Size(448, 304);
        ((System.ComponentModel.ISupportInitialize)(this.dgFields)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.tbTimeout)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        String nerr = _Draw.NameError(this._dsNode, this.tbDSName.Text);
        if (nerr != null)
        {
            MessageBox.Show(nerr, "Name");
            return false;
        }
         
        return true;
    }

    public void apply() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        XmlNode dsNode = _Draw.getNamedChildNode(rNode,"DataSets");
        XmlNode dNode = this._dsNode;
        // Create the name attribute
        _Draw.setElementAttribute(dNode,"Name",_dsv.getName());
        _Draw.removeElement(dNode,"Query");
        // get rid of old query
        XmlNode qNode = _Draw.createElement(dNode,"Query",null);
        _Draw.setElement(qNode,"DataSourceName",_dsv.getDataSourceName());
        if (_dsv.getTimeout() > 0)
            _Draw.SetElement(qNode, "Timeout", _dsv.getTimeout().ToString());
         
        _Draw.setElement(qNode,"CommandText",_dsv.getCommandText());
        // Handle QueryParameters
        _Draw.removeElement(qNode,"QueryParameters");
        // get rid of old QueryParameters
        XmlNode qpsNode = _Draw.createElement(qNode,"QueryParameters",null);
        for (Object __dummyForeachVar2 : _dsv.getQueryParameters().Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar2;
            if (dr[0] == DBNull.Value || dr[1] == null)
                continue;
             
            String name = (String)dr[0];
            if (name.Length <= 0)
                continue;
             
            XmlNode qpNode = _Draw.createElement(qpsNode,"QueryParameter",null);
            _Draw.setElementAttribute(qpNode,"Name",name);
            _Draw.setElement(qpNode,"Value",(String)dr[1]);
        }
        if (!qpsNode.HasChildNodes)
            // if no parameters we don't need to define them
            _Draw.removeElement(qNode,"QueryParameters");
         
        // Handle Fields
        _Draw.removeElement(dNode,"Fields");
        // get rid of old Fields
        XmlNode fsNode = _Draw.createElement(dNode,"Fields",null);
        for (Object __dummyForeachVar3 : _dsv.getFields().Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar3;
            if (dr[0] == DBNull.Value)
                continue;
             
            if (dr[1] == DBNull.Value && dr[2] == DBNull.Value)
                continue;
             
            XmlNode fNode = _Draw.createElement(fsNode,"Field",null);
            _Draw.setElementAttribute(fNode,"Name",(String)dr[0]);
            if (dr[1] != DBNull.Value && dr[1] instanceof String && !StringSupport.equals((String)dr[1], String.Empty))
                _Draw.setElement(fNode,"DataField",(String)dr[1]);
            else if (dr[2] != DBNull.Value && dr[2] instanceof String && !StringSupport.equals((String)dr[2], String.Empty))
                _Draw.setElement(fNode,"Value",(String)dr[2]);
            else
                _Draw.setElement(fNode,"DataField",(String)dr[0]);  
            // make datafield same as name
            // Handle typename if any
            if (dr[3] != DBNull.Value && dr[3] instanceof String && !StringSupport.equals((String)dr[3], String.Empty))
            {
                _Draw.setElement(fNode,_UseTypenameQualified ? "rd:TypeName" : "TypeName",(String)dr[3]);
            }
             
        }
    }

    private void tbDSName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        _dsv.setName(tbDSName.Text);
    }

    private void cbDataSource_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        _dsv.setDataSourceName(cbDataSource.Text);
    }

    private void tbSQL_TextChanged(Object sender, System.EventArgs e) throws Exception {
        _dsv.setCommandText(tbSQL.Text);
    }

    private void bDeleteField_Click(Object sender, System.EventArgs e) throws Exception {
        _dsv.getFields().Rows.RemoveAt(this.dgFields.CurrentRowIndex);
    }

    private void bRefresh_Click(Object sender, System.EventArgs e) throws Exception {
        // Need to clear all the fields and then replace with the columns
        //   of the SQL statement
        List<SqlColumn> cols = DesignerUtility.GetSqlColumns(_Draw, cbDataSource.Text, tbSQL.Text);
        if (cols == null || cols.Count <= 0)
            return ;
         
        // something didn't work right
        _dsv.getFields().Rows.Clear();
        String[] rowValues = new String[3];
        for (Object __dummyForeachVar4 : cols)
        {
            SqlColumn sc = (SqlColumn)__dummyForeachVar4;
            rowValues[0] = sc.getName();
            rowValues[1] = sc.getName();
            rowValues[2] = "";
            _dsv.getFields().Rows.Add(rowValues);
        }
    }

    private void bEditSQL_Click(Object sender, System.EventArgs e) throws Exception {
        SQLCtl sc = new SQLCtl(_Draw, cbDataSource.Text, this.tbSQL.Text, _dsv.getQueryParameters());
        DialogResult dr = sc.ShowDialog(this);
        if (dr == DialogResult.OK)
        {
            tbSQL.Text = sc.getSQL();
        }
         
    }

    private void tbTimeout_ValueChanged(Object sender, System.EventArgs e) throws Exception {
        _dsv.setTimeout(Convert.ToInt32(tbTimeout.Value));
    }

}


