//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DataSetValues;
import fyiReporting.RdlDesign.DesignerUtility;
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
* Control supports the properties for DataSet/Rows elements.  This is an extension to
* the RDL specification allowing data to be defined within a report.
*/
public class DataSetRowsCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private DataSetValues _dsv;
    private XmlNode _dsNode = new XmlNode();
    private DataTable _DataTable = new DataTable();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGridTableStyle dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.Button bUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.CheckBox chkRowsFile = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Button bRowsFile = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGrid dgRows = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.TextBox tbRowsFile = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bLoad = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bClear = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DataSetRowsCtl(DesignXmlDraw dxDraw, XmlNode dsNode, DataSetValues dsv) throws Exception {
        _Draw = dxDraw;
        _dsv = dsv;
        _dsNode = dsNode;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        createDataTable();
        // create data table based on the existing fields
        XmlNode rows = _Draw.getNamedChildNode(_dsNode,"Rows");
        if (rows == null)
            rows = _Draw.getNamedChildNode(_dsNode,"fyi:Rows");
         
        String file = null;
        if (rows != null)
        {
            file = _Draw.getElementAttribute(rows,"File",null);
            populateRows(rows);
        }
         
        this.dgRows.DataSource = _DataTable;
        if (file != null)
        {
            tbRowsFile.Text = file;
            this.chkRowsFile.Checked = true;
        }
         
        chkRowsFile_CheckedChanged(this, new EventArgs());
    }

    private void createDataTable() throws Exception {
        _DataTable = new DataTable();
        dgTableStyle.GridColumnStyles.Clear();
        for (Object __dummyForeachVar0 : _dsv.getFields().Rows)
        {
            // reset the grid column styles
            DataRow dr = (DataRow)__dummyForeachVar0;
            if (dr[0] == DBNull.Value)
                continue;
             
            if (dr[2] == DBNull.Value)
            {
            }
            else if (((String)dr[2]).Length > 0)
                continue;
              
            String name = (String)(dr[1] == DBNull.Value ? dr[0] : dr[1]);
            DataGridTextBoxColumn dgc = new DataGridTextBoxColumn();
            dgTableStyle.GridColumnStyles.Add(dgc);
            dgc.HeaderText = name;
            dgc.MappingName = name;
            dgc.Width = 75;
            _DataTable.Columns.Add(new DataColumn(name, String.class));
        }
    }

    private void populateRows(XmlNode rows) throws Exception {
        String[] rowValues = new String[_DataTable.Columns.Count];
        for (Object __dummyForeachVar2 : rows.ChildNodes)
        {
            XmlNode rNode = (XmlNode)__dummyForeachVar2;
            if (!StringSupport.equals(rNode.Name, "Row"))
                continue;
             
            int col = 0;
            boolean bBuiltRow = false;
            for (Object __dummyForeachVar1 : _DataTable.Columns)
            {
                // if all columns will be null we won't add the row
                DataColumn dc = (DataColumn)__dummyForeachVar1;
                XmlNode dNode = _Draw.GetNamedChildNode(rNode, dc.ColumnName);
                if (dNode != null)
                    bBuiltRow = true;
                 
                rowValues[col] = dNode != null ? dNode.InnerText : null;
                col++;
            }
            if (bBuiltRow)
                _DataTable.Rows.Add(rowValues);
             
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
        this.dgRows = new System.Windows.Forms.DataGrid();
        this.dgTableStyle = new System.Windows.Forms.DataGridTableStyle();
        this.bDelete = new System.Windows.Forms.Button();
        this.bUp = new System.Windows.Forms.Button();
        this.bDown = new System.Windows.Forms.Button();
        this.chkRowsFile = new System.Windows.Forms.CheckBox();
        this.tbRowsFile = new System.Windows.Forms.TextBox();
        this.bRowsFile = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.bLoad = new System.Windows.Forms.Button();
        this.bClear = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dgRows)).BeginInit();
        this.SuspendLayout();
        //
        // dgRows
        //
        this.dgRows.CaptionVisible = false;
        this.dgRows.DataMember = "";
        this.dgRows.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgRows.Location = new System.Drawing.Point(8, 48);
        this.dgRows.Name = "dgRows";
        this.dgRows.Size = new System.Drawing.Size(376, 200);
        this.dgRows.TabIndex = 2;
        this.dgRows.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyle });
        //
        // dgTableStyle
        //
        this.dgTableStyle.AllowSorting = false;
        this.dgTableStyle.DataGrid = this.dgRows;
        this.dgTableStyle.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyle.MappingName = "";
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(392, 48);
        this.bDelete.Name = "bDelete";
        this.bDelete.Size = new System.Drawing.Size(48, 23);
        this.bDelete.TabIndex = 1;
        this.bDelete.Text = "Delete";
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // bUp
        //
        this.bUp.Location = new System.Drawing.Point(392, 80);
        this.bUp.Name = "bUp";
        this.bUp.Size = new System.Drawing.Size(48, 23);
        this.bUp.TabIndex = 3;
        this.bUp.Text = "Up";
        this.bUp.Click += new System.EventHandler(this.bUp_Click);
        //
        // bDown
        //
        this.bDown.Location = new System.Drawing.Point(392, 112);
        this.bDown.Name = "bDown";
        this.bDown.Size = new System.Drawing.Size(48, 23);
        this.bDown.TabIndex = 4;
        this.bDown.Text = "Down";
        this.bDown.Click += new System.EventHandler(this.bDown_Click);
        //
        // chkRowsFile
        //
        this.chkRowsFile.Location = new System.Drawing.Point(8, 8);
        this.chkRowsFile.Name = "chkRowsFile";
        this.chkRowsFile.Size = new System.Drawing.Size(136, 24);
        this.chkRowsFile.TabIndex = 5;
        this.chkRowsFile.Text = "Use XML file for data";
        this.chkRowsFile.CheckedChanged += new System.EventHandler(this.chkRowsFile_CheckedChanged);
        //
        // tbRowsFile
        //
        this.tbRowsFile.Location = new System.Drawing.Point(144, 8);
        this.tbRowsFile.Name = "tbRowsFile";
        this.tbRowsFile.Size = new System.Drawing.Size(240, 20);
        this.tbRowsFile.TabIndex = 6;
        this.tbRowsFile.Text = "";
        //
        // bRowsFile
        //
        this.bRowsFile.Location = new System.Drawing.Point(392, 8);
        this.bRowsFile.Name = "bRowsFile";
        this.bRowsFile.Size = new System.Drawing.Size(24, 23);
        this.bRowsFile.TabIndex = 7;
        this.bRowsFile.Text = "...";
        this.bRowsFile.Click += new System.EventHandler(this.bRowsFile_Click);
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 6F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.label1.Location = new System.Drawing.Point(16, 256);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(368, 23);
        this.label1.TabIndex = 8;
        this.label1.Text = "Warning: this panel supports an extension to the RDL specification.  This informa" + "tion will be ignored in RDL processors other than in fyiReporting.";
        //
        // bLoad
        //
        this.bLoad.Location = new System.Drawing.Point(392, 184);
        this.bLoad.Name = "bLoad";
        this.bLoad.Size = new System.Drawing.Size(48, 48);
        this.bLoad.TabIndex = 9;
        this.bLoad.Text = "Load From SQL";
        this.bLoad.Click += new System.EventHandler(this.bLoad_Click);
        //
        // bClear
        //
        this.bClear.Location = new System.Drawing.Point(392, 141);
        this.bClear.Name = "bClear";
        this.bClear.Size = new System.Drawing.Size(48, 23);
        this.bClear.TabIndex = 10;
        this.bClear.Text = "Clear";
        this.bClear.Click += new System.EventHandler(this.bClear_Click);
        //
        // DataSetRowsCtl
        //
        this.Controls.Add(this.bClear);
        this.Controls.Add(this.bLoad);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.bRowsFile);
        this.Controls.Add(this.tbRowsFile);
        this.Controls.Add(this.chkRowsFile);
        this.Controls.Add(this.bDown);
        this.Controls.Add(this.bUp);
        this.Controls.Add(this.bDelete);
        this.Controls.Add(this.dgRows);
        this.Name = "DataSetRowsCtl";
        this.Size = new System.Drawing.Size(488, 304);
        this.VisibleChanged += new System.EventHandler(this.DataSetRowsCtl_VisibleChanged);
        ((System.ComponentModel.ISupportInitialize)(this.dgRows)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        if (this.chkRowsFile.Checked && this.tbRowsFile.Text.Length == 0)
        {
            MessageBox.Show("File name required when 'Use XML file for data checked'");
            return false;
        }
         
        return true;
    }

    public void apply() throws Exception {
        // Remove the old row
        XmlNode rows = _Draw.getNamedChildNode(this._dsNode,"Rows");
        if (rows == null)
            rows = _Draw.getNamedChildNode(this._dsNode,"fyi:Rows");
         
        if (rows != null)
            _dsNode.RemoveChild(rows);
         
        // different result if we just want the file
        if (this.chkRowsFile.Checked)
        {
            rows = _Draw.getCreateNamedChildNode(_dsNode,"fyi:Rows");
            _Draw.SetElementAttribute(rows, "File", this.tbRowsFile.Text);
        }
        else
        {
            rows = getXmlData();
            if (rows.HasChildNodes)
                _dsNode.AppendChild(rows);
             
        } 
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        this._DataTable.Rows.RemoveAt(this.dgRows.CurrentRowIndex);
    }

    private void bUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgRows.CurrentRowIndex;
        if (cr <= 0)
            return ;
         
        // already at the top
        SwapRow(_DataTable.Rows[cr - 1], _DataTable.Rows[cr]);
        dgRows.CurrentRowIndex = cr - 1;
    }

    private void bDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = dgRows.CurrentRowIndex;
        if (cr < 0)
            return ;
         
        // invalid index
        if (cr + 1 >= _DataTable.Rows.Count)
            return ;
         
        // already at end
        SwapRow(_DataTable.Rows[cr + 1], _DataTable.Rows[cr]);
        dgRows.CurrentRowIndex = cr + 1;
    }

    private void swapRow(DataRow tdr, DataRow fdr) throws Exception {
        for (int ci = 0;ci < _DataTable.Columns.Count;ci++)
        {
            // Loop thru all the columns in a row and swap the data
            Object save = tdr[ci];
            tdr[ci] = fdr[ci];
            fdr[ci] = save;
        }
        return ;
    }

    private void chkRowsFile_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        this.tbRowsFile.Enabled = chkRowsFile.Checked;
        this.bRowsFile.Enabled = chkRowsFile.Checked;
        this.bDelete.Enabled = !chkRowsFile.Checked;
        this.bUp.Enabled = !chkRowsFile.Checked;
        this.bDown.Enabled = !chkRowsFile.Checked;
        this.dgRows.Enabled = !chkRowsFile.Checked;
    }

    private void bRowsFile_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "XML files (*.xml)|*.xml" + "All files (*.*)|*.*|";
        ofd.FilterIndex = 1;
        ofd.FileName = "*.xml";
        ofd.Title = "Specify XML File Name";
        ofd.DefaultExt = "xml";
        ofd.AddExtension = true;
        if (ofd.ShowDialog() == DialogResult.OK)
        {
            String file = Path.GetFileName(ofd.FileName);
            this.tbRowsFile.Text = file;
        }
         
    }

    private boolean didFieldsChange() throws Exception {
        int col = 0;
        for (Object __dummyForeachVar3 : _dsv.getFields().Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar3;
            if (col >= _DataTable.Columns.Count)
                return true;
             
            if (dr[0] == DBNull.Value)
                continue;
             
            if (dr[2] == DBNull.Value)
            {
            }
            else if (((String)dr[2]).Length > 0)
                continue;
              
            String name = (String)(dr[1] == DBNull.Value ? dr[0] : dr[1]);
            if (!StringSupport.equals(_DataTable.Columns[col].ColumnName, name))
                return true;
             
            col++;
        }
        if (col == _DataTable.Columns.Count)
            return false;
        else
            return true; 
    }

    private XmlNode getXmlData() throws Exception {
        XmlDocumentFragment fDoc = _Draw.getReportDocument().CreateDocumentFragment();
        XmlNode rows = _Draw.CreateElement(fDoc, "fyi:Rows", null);
        for (Object __dummyForeachVar5 : _DataTable.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar5;
            XmlNode row = _Draw.createElement(rows,"Row",null);
            boolean bRowBuilt = false;
            for (Object __dummyForeachVar4 : _DataTable.Columns)
            {
                DataColumn dc = (DataColumn)__dummyForeachVar4;
                if (dr[dc] == DBNull.Value)
                    continue;
                 
                String val = dr[dc].ToString();
                if (val == null)
                    continue;
                 
                _Draw.CreateElement(row, dc.ColumnName, val);
                bRowBuilt = true;
            }
            // we've populated at least one column; so keep row
            if (!bRowBuilt)
                rows.RemoveChild(row);
             
        }
        return rows;
    }

    private void dataSetRowsCtl_VisibleChanged(Object sender, System.EventArgs e) throws Exception {
        if (!didFieldsChange())
            return ;
         
        // did the structure of the fields change
        // Need to reset the data; this assumes that some of the data rows are similar
        XmlNode rows = getXmlData();
        // get old data
        createDataTable();
        // this recreates the datatable
        populateRows(rows);
        // repopulate the datatable
        this.dgRows.DataSource = _DataTable;
    }

    // this recreates the datatable so reset grid
    private void bClear_Click(Object sender, System.EventArgs e) throws Exception {
        this._DataTable.Rows.Clear();
    }

    private void bLoad_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            // Load the data from the SQL; we append the data to what already exists
            // Obtain the connection information
            XmlNode rNode = _Draw.getReportNode();
            XmlNode dsNode = _Draw.getNamedChildNode(rNode,"DataSources");
            if (dsNode == null)
                return ;
             
            XmlNode datasource = null;
            for (Object __dummyForeachVar6 : dsNode)
            {
                XmlNode dNode = (XmlNode)__dummyForeachVar6;
                if (!StringSupport.equals(dNode.Name, "DataSource"))
                    continue;
                 
                XmlAttribute nAttr = dNode.Attributes["Name"];
                if (nAttr == null)
                    continue;
                 
                // shouldn't really happen
                if (!StringSupport.equals(nAttr.Value, _dsv.getDataSourceName()))
                    continue;
                 
                datasource = dNode;
                break;
            }
            if (datasource == null)
            {
                MessageBox.Show(String.Format("Datasource '{0}' not found.", _dsv.getDataSourceName()), "Load Failed");
                return ;
            }
             
            String dataSourceReference = _Draw.getElementValue(datasource,"DataSourceReference",null);
            if (dataSourceReference != null)
            {
                // todo: should support datasourcereference here as well
                MessageBox.Show(String.Format("Datasource '{0}' contains a DataSourceReference.   This is not currently supported.", _dsv.getDataSourceName()), "Load Failed");
                return ;
            }
             
            XmlNode cpNode = DesignXmlDraw.findNextInHierarchy(datasource,"ConnectionProperties","ConnectString");
            String connection = cpNode == null ? "" : cpNode.InnerText;
            XmlNode datap = DesignXmlDraw.findNextInHierarchy(datasource,"ConnectionProperties","DataProvider");
            String dataProvider = datap == null ? "" : datap.InnerText;
            // Populate the data table
            DesignerUtility.getSqlData(dataProvider,connection,_dsv.getCommandText(),null,_DataTable);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Load Failed");
        }
    
    }

}


