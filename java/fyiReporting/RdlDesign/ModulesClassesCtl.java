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
* Summary description for ModulesClassesCtl.
*/
public class ModulesClassesCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private DataTable _DTCM = new DataTable();
    private DataTable _DTCL = new DataTable();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bDeleteCM = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGrid dgCodeModules = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Button bDeleteClass = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGrid dgClasses = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DataGridTableStyle dgTableStyleCM = new System.Windows.Forms.DataGridTableStyle();
    private System.Windows.Forms.DataGridTableStyle dgTableStyleCL = new System.Windows.Forms.DataGridTableStyle();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public ModulesClassesCtl(DesignXmlDraw dxDraw) throws Exception {
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        buildCodeModules();
        buildClasses();
    }

    private void buildCodeModules() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        // Initialize the DataGrid columns
        DataGridTextBoxColumn dgtbCM = new DataGridTextBoxColumn();
        this.dgTableStyleCM.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ dgtbCM });
        //
        // dgtbGE
        //
        dgtbCM.HeaderText = "Code Module";
        dgtbCM.MappingName = "Code Module";
        dgtbCM.Width = 175;
        // Initialize the DataTable
        _DTCM = new DataTable();
        _DTCM.Columns.Add(new DataColumn("Code Module", String.class));
        String[] rowValues = new String[1];
        XmlNode cmsNode = _Draw.getNamedChildNode(rNode,"CodeModules");
        if (cmsNode != null)
            for (Object __dummyForeachVar0 : cmsNode.ChildNodes)
            {
                XmlNode cmNode = (XmlNode)__dummyForeachVar0;
                if (cmNode.NodeType != XmlNodeType.Element || !StringSupport.equals(cmNode.Name, "CodeModule"))
                    continue;
                 
                rowValues[0] = cmNode.InnerText;
                _DTCM.Rows.Add(rowValues);
            }
         
        this.dgCodeModules.DataSource = _DTCM;
        DataGridTableStyle ts = dgCodeModules.TableStyles[0];
        ts.GridColumnStyles[0].Width = 330;
    }

    private void buildClasses() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        // Initialize the DataGrid columns
        DataGridTextBoxColumn dgtbCL = new DataGridTextBoxColumn();
        DataGridTextBoxColumn dgtbIn = new DataGridTextBoxColumn();
        this.dgTableStyleCL.GridColumnStyles.AddRange(new DataGridColumnStyle[]{ dgtbCL, dgtbIn });
        dgtbCL.HeaderText = "Class Name";
        dgtbCL.MappingName = "Class Name";
        dgtbCL.Width = 80;
        dgtbIn.HeaderText = "Instance Name";
        dgtbIn.MappingName = "Instance Name";
        dgtbIn.Width = 80;
        // Initialize the DataTable
        _DTCL = new DataTable();
        _DTCL.Columns.Add(new DataColumn("Class Name", String.class));
        _DTCL.Columns.Add(new DataColumn("Instance Name", String.class));
        String[] rowValues = new String[2];
        XmlNode clsNode = _Draw.getNamedChildNode(rNode,"Classes");
        if (clsNode != null)
            for (Object __dummyForeachVar1 : clsNode.ChildNodes)
            {
                XmlNode clNode = (XmlNode)__dummyForeachVar1;
                if (clNode.NodeType != XmlNodeType.Element || !StringSupport.equals(clNode.Name, "Class"))
                    continue;
                 
                XmlNode node = _Draw.getNamedChildNode(clNode,"ClassName");
                if (node != null)
                    rowValues[0] = node.InnerText;
                 
                node = _Draw.getNamedChildNode(clNode,"InstanceName");
                if (node != null)
                    rowValues[1] = node.InnerText;
                 
                _DTCL.Rows.Add(rowValues);
            }
         
        this.dgClasses.DataSource = _DTCL;
        DataGridTableStyle ts = dgClasses.TableStyles[0];
        ts.GridColumnStyles[0].Width = 200;
        ts.GridColumnStyles[1].Width = 130;
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
        this.label1 = new System.Windows.Forms.Label();
        this.bDeleteCM = new System.Windows.Forms.Button();
        this.dgCodeModules = new System.Windows.Forms.DataGrid();
        this.dgTableStyleCM = new System.Windows.Forms.DataGridTableStyle();
        this.bDeleteClass = new System.Windows.Forms.Button();
        this.dgClasses = new System.Windows.Forms.DataGrid();
        this.dgTableStyleCL = new System.Windows.Forms.DataGridTableStyle();
        this.label2 = new System.Windows.Forms.Label();
        ((System.ComponentModel.ISupportInitialize)(this.dgCodeModules)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.dgClasses)).BeginInit();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(448, 23);
        this.label1.TabIndex = 0;
        this.label1.Text = "Enter the names of the code module for use in expressions (e.g. MyRoutines.dll)";
        //
        // bDeleteCM
        //
        this.bDeleteCM.Location = new System.Drawing.Point(400, 32);
        this.bDeleteCM.Name = "bDeleteCM";
        this.bDeleteCM.Size = new System.Drawing.Size(48, 20);
        this.bDeleteCM.TabIndex = 4;
        this.bDeleteCM.Text = "Delete";
        this.bDeleteCM.Click += new System.EventHandler(this.bDeleteCM_Click);
        //
        // dgCodeModules
        //
        this.dgCodeModules.CaptionVisible = false;
        this.dgCodeModules.DataMember = "";
        this.dgCodeModules.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgCodeModules.Location = new System.Drawing.Point(16, 32);
        this.dgCodeModules.Name = "dgCodeModules";
        this.dgCodeModules.Size = new System.Drawing.Size(376, 88);
        this.dgCodeModules.TabIndex = 3;
        this.dgCodeModules.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyleCM });
        //
        // dgTableStyleCM
        //
        this.dgTableStyleCM.DataGrid = this.dgCodeModules;
        this.dgTableStyleCM.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyleCM.MappingName = "";
        //
        // bDeleteClass
        //
        this.bDeleteClass.Location = new System.Drawing.Point(400, 160);
        this.bDeleteClass.Name = "bDeleteClass";
        this.bDeleteClass.Size = new System.Drawing.Size(48, 20);
        this.bDeleteClass.TabIndex = 7;
        this.bDeleteClass.Text = "Delete";
        this.bDeleteClass.Click += new System.EventHandler(this.bDeleteClass_Click);
        //
        // dgClasses
        //
        this.dgClasses.CaptionVisible = false;
        this.dgClasses.DataMember = "";
        this.dgClasses.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgClasses.Location = new System.Drawing.Point(16, 160);
        this.dgClasses.Name = "dgClasses";
        this.dgClasses.Size = new System.Drawing.Size(376, 88);
        this.dgClasses.TabIndex = 6;
        this.dgClasses.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[]{ this.dgTableStyleCL });
        //
        // dgTableStyleCL
        //
        this.dgTableStyleCL.DataGrid = this.dgClasses;
        this.dgTableStyleCL.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.dgTableStyleCL.MappingName = "";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 136);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(448, 23);
        this.label2.TabIndex = 5;
        this.label2.Text = "Enter the classes with names that will be instantiated for use in expressions";
        //
        // ModulesClassesCtl
        //
        this.Controls.Add(this.bDeleteClass);
        this.Controls.Add(this.dgClasses);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.bDeleteCM);
        this.Controls.Add(this.dgCodeModules);
        this.Controls.Add(this.label1);
        this.Name = "ModulesClassesCtl";
        this.Size = new System.Drawing.Size(472, 288);
        ((System.ComponentModel.ISupportInitialize)(this.dgCodeModules)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.dgClasses)).EndInit();
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        applyCodeModules();
        applyClasses();
    }

    private void applyCodeModules() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        _Draw.removeElement(rNode,"CodeModules");
        if (!hasRows(this._DTCM,1))
            return ;
         
        // Set the CodeModules
        XmlNode cms = _Draw.createElement(rNode,"CodeModules",null);
        for (Object __dummyForeachVar2 : _DTCM.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar2;
            if (dr[0] == DBNull.Value || dr[0].ToString().Trim().Length <= 0)
                continue;
             
            _Draw.CreateElement(cms, "CodeModule", dr[0].ToString());
        }
    }

    private void applyClasses() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        _Draw.removeElement(rNode,"Classes");
        if (!hasRows(this._DTCL,2))
            return ;
         
        // Set the classes
        XmlNode cs = _Draw.createElement(rNode,"Classes",null);
        for (Object __dummyForeachVar3 : _DTCL.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar3;
            if (dr[0] == DBNull.Value || dr[1] == DBNull.Value || dr[0].ToString().Trim().Length <= 0 || dr[1].ToString().Trim().Length <= 0)
                continue;
             
            XmlNode c = _Draw.createElement(cs,"Class",null);
            _Draw.CreateElement(c, "ClassName", dr[0].ToString());
            _Draw.CreateElement(c, "InstanceName", dr[1].ToString());
        }
    }

    private boolean hasRows(DataTable dt, int columns) throws Exception {
        for (Object __dummyForeachVar4 : dt.Rows)
        {
            DataRow dr = (DataRow)__dummyForeachVar4;
            boolean bCompleteRow = true;
            for (int i = 0;i < columns;i++)
            {
                if (dr[i] == DBNull.Value)
                {
                    bCompleteRow = false;
                    break;
                }
                 
                String ge = (String)dr[i];
                if (ge.Length <= 0)
                {
                    bCompleteRow = false;
                    break;
                }
                 
            }
            if (bCompleteRow)
                return true;
             
        }
        return false;
    }

    private void bDeleteCM_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = this.dgCodeModules.CurrentRowIndex;
        if (cr < 0)
            return ;
         
        // already at the top
        _DTCM.Rows.RemoveAt(cr);
    }

    private void bDeleteClass_Click(Object sender, System.EventArgs e) throws Exception {
        int cr = this.dgClasses.CurrentRowIndex;
        if (cr < 0)
            return ;
         
        // already at the top
        _DTCL.Rows.RemoveAt(cr);
    }

}


