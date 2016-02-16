//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogListOfStrings;
import fyiReporting.RdlDesign.DialogValidValues;
import fyiReporting.RdlDesign.IProperty;
import fyiReporting.RdlDesign.ParameterValueItem;
import fyiReporting.RdlDesign.ReportParm;

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
public class ReportParameterCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private System.Windows.Forms.Button bParmDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bParmUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbParmValidValues = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox ckbParmAllowBlank = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox tbParmPrompt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label lParmPrompt = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbParmType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label lParmType = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbParmName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label lParmName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bRemove = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.ListBox lbParameters = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton rbDataSet = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbValues = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.ComboBox cbValidDataSets = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbValidFields = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbValidDisplayField = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbDefaultValueField = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbDefaultDataSets = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.RadioButton rbDefaultValues = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbDefaultDataSetName = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.CheckBox ckbParmAllowNull = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Button bDefaultValues = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbParmDefaultValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bValidValues = new System.Windows.Forms.Button();
    private CheckBox ckbParmMultiValue = new CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public ReportParameterCtl(DesignXmlDraw dxDraw) throws Exception {
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        // Populate datasets combos
        Object[] datasets = _Draw.getDataSetNames();
        this.cbDefaultDataSets.Items.AddRange(datasets);
        this.cbValidDataSets.Items.AddRange(datasets);
        XmlNode rNode = _Draw.getReportNode();
        XmlNode rpsNode = _Draw.getNamedChildNode(rNode,"ReportParameters");
        if (rpsNode == null)
            return ;
         
        for (Object __dummyForeachVar0 : rpsNode)
        {
            XmlNode repNode = (XmlNode)__dummyForeachVar0;
            XmlAttribute nAttr = repNode.Attributes["Name"];
            if (nAttr == null)
                continue;
             
            // shouldn't really happen
            ReportParm repParm = new ReportParm(nAttr.Value);
            repParm.setDataType(_Draw.getElementValue(repNode,"DataType","String"));
            // Get default values
            initDefaultValues(repNode,repParm);
            String nullable = _Draw.getElementValue(repNode,"Nullable","false");
            repParm.setAllowNull((StringSupport.equals(nullable.ToLower(), "true")));
            String allowBlank = _Draw.getElementValue(repNode,"AllowBlank","false");
            repParm.setAllowBlank((StringSupport.equals(allowBlank.ToLower(), "true")));
            String mvalue = _Draw.getElementValue(repNode,"MultiValue","false");
            repParm.setMultiValue((StringSupport.equals(mvalue.ToLower(), "true")));
            repParm.setPrompt(_Draw.getElementValue(repNode,"Prompt",""));
            initValidValues(repNode,repParm);
            this.lbParameters.Items.Add(repParm);
        }
        if (lbParameters.Items.Count > 0)
            lbParameters.SelectedIndex = 0;
         
    }

    void initDefaultValues(XmlNode reportParameterNode, ReportParm repParm) throws Exception {
        repParm.setDefault(true);
        XmlNode dfNode = _Draw.getNamedChildNode(reportParameterNode,"DefaultValue");
        if (dfNode == null)
            return ;
         
        XmlNode vNodes = _Draw.getNamedChildNode(dfNode,"Values");
        if (vNodes != null)
        {
            List<String> al = new List<String>();
            for (Object __dummyForeachVar1 : vNodes.ChildNodes)
            {
                XmlNode v = (XmlNode)__dummyForeachVar1;
                if (v.InnerText.Length <= 0)
                    continue;
                 
                al.Add(v.InnerText);
            }
            if (al.Count >= 1)
                repParm.setDefaultValue(al);
             
        }
         
        XmlNode dsNodes = _Draw.getNamedChildNode(dfNode,"DataSetReference");
        if (dsNodes != null)
        {
            repParm.setDefault(false);
            repParm.setDefaultDSRDataSetName(_Draw.getElementValue(dsNodes,"DataSetName",""));
            repParm.setDefaultDSRValueField(_Draw.getElementValue(dsNodes,"ValueField",""));
        }
         
    }

    void initValidValues(XmlNode reportParameterNode, ReportParm repParm) throws Exception {
        repParm.setValid(true);
        XmlNode vvsNode = _Draw.getNamedChildNode(reportParameterNode,"ValidValues");
        if (vvsNode == null)
            return ;
         
        XmlNode vNodes = _Draw.getNamedChildNode(vvsNode,"ParameterValues");
        if (vNodes != null)
        {
            List<ParameterValueItem> pvs = new List<ParameterValueItem>();
            for (Object __dummyForeachVar2 : vNodes.ChildNodes)
            {
                XmlNode v = (XmlNode)__dummyForeachVar2;
                if (!StringSupport.equals(v.Name, "ParameterValue"))
                    continue;
                 
                XmlNode pv = _Draw.getNamedChildNode(v,"Value");
                if (pv == null)
                    continue;
                 
                if (pv == null || pv.InnerText.Length <= 0)
                    continue;
                 
                ParameterValueItem pvi = new ParameterValueItem();
                pvi.Value = pv.InnerText;
                pvi.Label = _Draw.getElementValue(v,"Label",null);
                pvs.Add(pvi);
            }
            if (pvs.Count > 0)
            {
                repParm.setValidValues(pvs);
            }
             
        }
         
        XmlNode dsNodes = _Draw.getNamedChildNode(vvsNode,"DataSetReference");
        if (dsNodes != null)
        {
            repParm.setValid(false);
            repParm.setValidValuesDSRDataSetName(_Draw.getElementValue(dsNodes,"DataSetName",""));
            repParm.setValidValuesDSRValueField(_Draw.getElementValue(dsNodes,"ValueField",""));
            repParm.setValidValuesDSRLabelField(_Draw.getElementValue(dsNodes,"LabelField",""));
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
        this.bParmDown = new System.Windows.Forms.Button();
        this.bParmUp = new System.Windows.Forms.Button();
        this.tbParmValidValues = new System.Windows.Forms.TextBox();
        this.ckbParmAllowBlank = new System.Windows.Forms.CheckBox();
        this.tbParmPrompt = new System.Windows.Forms.TextBox();
        this.lParmPrompt = new System.Windows.Forms.Label();
        this.cbParmType = new System.Windows.Forms.ComboBox();
        this.lParmType = new System.Windows.Forms.Label();
        this.tbParmName = new System.Windows.Forms.TextBox();
        this.lParmName = new System.Windows.Forms.Label();
        this.bRemove = new System.Windows.Forms.Button();
        this.bAdd = new System.Windows.Forms.Button();
        this.lbParameters = new System.Windows.Forms.ListBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.bValidValues = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.cbValidDisplayField = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.cbValidFields = new System.Windows.Forms.ComboBox();
        this.cbValidDataSets = new System.Windows.Forms.ComboBox();
        this.rbValues = new System.Windows.Forms.RadioButton();
        this.rbDataSet = new System.Windows.Forms.RadioButton();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.tbParmDefaultValue = new System.Windows.Forms.TextBox();
        this.bDefaultValues = new System.Windows.Forms.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.cbDefaultValueField = new System.Windows.Forms.ComboBox();
        this.cbDefaultDataSets = new System.Windows.Forms.ComboBox();
        this.rbDefaultValues = new System.Windows.Forms.RadioButton();
        this.rbDefaultDataSetName = new System.Windows.Forms.RadioButton();
        this.ckbParmAllowNull = new System.Windows.Forms.CheckBox();
        this.ckbParmMultiValue = new System.Windows.Forms.CheckBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // bParmDown
        //
        this.bParmDown.Font = new System.Drawing.Font("Wingdings", 7.25F, System.Drawing.FontStyle.Bold);
        this.bParmDown.Location = new System.Drawing.Point(128, 40);
        this.bParmDown.Name = "bParmDown";
        this.bParmDown.Size = new System.Drawing.Size(20, 24);
        this.bParmDown.TabIndex = 4;
        this.bParmDown.Text = "ïƒª";
        this.bParmDown.Click += new System.EventHandler(this.bParmDown_Click);
        //
        // bParmUp
        //
        this.bParmUp.Font = new System.Drawing.Font("Wingdings", 7.25F, System.Drawing.FontStyle.Bold);
        this.bParmUp.Location = new System.Drawing.Point(128, 8);
        this.bParmUp.Name = "bParmUp";
        this.bParmUp.Size = new System.Drawing.Size(20, 24);
        this.bParmUp.TabIndex = 3;
        this.bParmUp.Text = "ïƒ©";
        this.bParmUp.Click += new System.EventHandler(this.bParmUp_Click);
        //
        // tbParmValidValues
        //
        this.tbParmValidValues.Location = new System.Drawing.Point(72, 16);
        this.tbParmValidValues.Name = "tbParmValidValues";
        this.tbParmValidValues.ReadOnly = true;
        this.tbParmValidValues.Size = new System.Drawing.Size(328, 20);
        this.tbParmValidValues.TabIndex = 1;
        //
        // ckbParmAllowBlank
        //
        this.ckbParmAllowBlank.Location = new System.Drawing.Point(222, 72);
        this.ckbParmAllowBlank.Name = "ckbParmAllowBlank";
        this.ckbParmAllowBlank.Size = new System.Drawing.Size(148, 24);
        this.ckbParmAllowBlank.TabIndex = 9;
        this.ckbParmAllowBlank.Text = "Allow blank (strings only)";
        this.ckbParmAllowBlank.CheckedChanged += new System.EventHandler(this.ckbParmAllowBlank_CheckedChanged);
        //
        // tbParmPrompt
        //
        this.tbParmPrompt.Location = new System.Drawing.Point(208, 40);
        this.tbParmPrompt.Name = "tbParmPrompt";
        this.tbParmPrompt.Size = new System.Drawing.Size(240, 20);
        this.tbParmPrompt.TabIndex = 7;
        this.tbParmPrompt.TextChanged += new System.EventHandler(this.tbParmPrompt_TextChanged);
        //
        // lParmPrompt
        //
        this.lParmPrompt.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.lParmPrompt.Location = new System.Drawing.Point(160, 40);
        this.lParmPrompt.Name = "lParmPrompt";
        this.lParmPrompt.Size = new System.Drawing.Size(48, 16);
        this.lParmPrompt.TabIndex = 23;
        this.lParmPrompt.Text = "Prompt";
        this.lParmPrompt.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // cbParmType
        //
        this.cbParmType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbParmType.Items.AddRange(new Object[]{ "Boolean", "DateTime", "Integer", "Float", "String" });
        this.cbParmType.Location = new System.Drawing.Point(368, 16);
        this.cbParmType.Name = "cbParmType";
        this.cbParmType.Size = new System.Drawing.Size(80, 21);
        this.cbParmType.TabIndex = 6;
        this.cbParmType.SelectedIndexChanged += new System.EventHandler(this.cbParmType_SelectedIndexChanged);
        //
        // lParmType
        //
        this.lParmType.Location = new System.Drawing.Point(304, 16);
        this.lParmType.Name = "lParmType";
        this.lParmType.Size = new System.Drawing.Size(56, 23);
        this.lParmType.TabIndex = 21;
        this.lParmType.Text = "Datatype";
        this.lParmType.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // tbParmName
        //
        this.tbParmName.Location = new System.Drawing.Point(208, 16);
        this.tbParmName.Name = "tbParmName";
        this.tbParmName.Size = new System.Drawing.Size(104, 20);
        this.tbParmName.TabIndex = 5;
        this.tbParmName.TextChanged += new System.EventHandler(this.tbParmName_TextChanged);
        //
        // lParmName
        //
        this.lParmName.Location = new System.Drawing.Point(160, 16);
        this.lParmName.Name = "lParmName";
        this.lParmName.Size = new System.Drawing.Size(40, 16);
        this.lParmName.TabIndex = 19;
        this.lParmName.Text = "Name";
        this.lParmName.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // bRemove
        //
        this.bRemove.Location = new System.Drawing.Point(68, 70);
        this.bRemove.Name = "bRemove";
        this.bRemove.Size = new System.Drawing.Size(54, 23);
        this.bRemove.TabIndex = 2;
        this.bRemove.Text = "Remove";
        this.bRemove.Click += new System.EventHandler(this.bRemove_Click);
        //
        // bAdd
        //
        this.bAdd.Location = new System.Drawing.Point(8, 70);
        this.bAdd.Name = "bAdd";
        this.bAdd.Size = new System.Drawing.Size(54, 23);
        this.bAdd.TabIndex = 1;
        this.bAdd.Text = "Add";
        this.bAdd.Click += new System.EventHandler(this.bAdd_Click);
        //
        // lbParameters
        //
        this.lbParameters.Location = new System.Drawing.Point(8, 8);
        this.lbParameters.Name = "lbParameters";
        this.lbParameters.Size = new System.Drawing.Size(112, 56);
        this.lbParameters.TabIndex = 0;
        this.lbParameters.SelectedIndexChanged += new System.EventHandler(this.lbParameters_SelectedIndexChanged);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.bValidValues);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.cbValidDisplayField);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.cbValidFields);
        this.groupBox1.Controls.Add(this.cbValidDataSets);
        this.groupBox1.Controls.Add(this.rbValues);
        this.groupBox1.Controls.Add(this.rbDataSet);
        this.groupBox1.Controls.Add(this.tbParmValidValues);
        this.groupBox1.Location = new System.Drawing.Point(8, 184);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(440, 96);
        this.groupBox1.TabIndex = 11;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Valid Values";
        //
        // bValidValues
        //
        this.bValidValues.Location = new System.Drawing.Point(408, 16);
        this.bValidValues.Name = "bValidValues";
        this.bValidValues.Size = new System.Drawing.Size(24, 23);
        this.bValidValues.TabIndex = 2;
        this.bValidValues.Text = "...";
        this.bValidValues.Click += new System.EventHandler(this.bValidValues_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(240, 72);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(72, 16);
        this.label2.TabIndex = 37;
        this.label2.Text = "Display Field";
        //
        // cbValidDisplayField
        //
        this.cbValidDisplayField.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbValidDisplayField.Location = new System.Drawing.Point(312, 72);
        this.cbValidDisplayField.Name = "cbValidDisplayField";
        this.cbValidDisplayField.Size = new System.Drawing.Size(112, 21);
        this.cbValidDisplayField.TabIndex = 6;
        this.cbValidDisplayField.SelectedIndexChanged += new System.EventHandler(this.cbValidDisplayField_SelectedIndexChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(240, 46);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(64, 16);
        this.label1.TabIndex = 35;
        this.label1.Text = "Value Field";
        //
        // cbValidFields
        //
        this.cbValidFields.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbValidFields.Location = new System.Drawing.Point(312, 40);
        this.cbValidFields.Name = "cbValidFields";
        this.cbValidFields.Size = new System.Drawing.Size(112, 21);
        this.cbValidFields.TabIndex = 5;
        this.cbValidFields.SelectedIndexChanged += new System.EventHandler(this.cbValidFields_SelectedIndexChanged);
        //
        // cbValidDataSets
        //
        this.cbValidDataSets.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbValidDataSets.Location = new System.Drawing.Point(112, 40);
        this.cbValidDataSets.Name = "cbValidDataSets";
        this.cbValidDataSets.Size = new System.Drawing.Size(112, 21);
        this.cbValidDataSets.TabIndex = 4;
        this.cbValidDataSets.SelectedIndexChanged += new System.EventHandler(this.cbValidDataSets_SelectedIndexChanged);
        //
        // rbValues
        //
        this.rbValues.Location = new System.Drawing.Point(8, 16);
        this.rbValues.Name = "rbValues";
        this.rbValues.Size = new System.Drawing.Size(64, 24);
        this.rbValues.TabIndex = 0;
        this.rbValues.Text = "Values";
        this.rbValues.CheckedChanged += new System.EventHandler(this.rbValues_CheckedChanged);
        //
        // rbDataSet
        //
        this.rbDataSet.Location = new System.Drawing.Point(8, 40);
        this.rbDataSet.Name = "rbDataSet";
        this.rbDataSet.Size = new System.Drawing.Size(104, 24);
        this.rbDataSet.TabIndex = 3;
        this.rbDataSet.Text = "Data Set Name";
        this.rbDataSet.CheckedChanged += new System.EventHandler(this.rbDataSet_CheckedChanged);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.tbParmDefaultValue);
        this.groupBox2.Controls.Add(this.bDefaultValues);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.cbDefaultValueField);
        this.groupBox2.Controls.Add(this.cbDefaultDataSets);
        this.groupBox2.Controls.Add(this.rbDefaultValues);
        this.groupBox2.Controls.Add(this.rbDefaultDataSetName);
        this.groupBox2.Location = new System.Drawing.Point(8, 104);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(440, 72);
        this.groupBox2.TabIndex = 10;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Default Values";
        //
        // tbParmDefaultValue
        //
        this.tbParmDefaultValue.Location = new System.Drawing.Point(72, 16);
        this.tbParmDefaultValue.Name = "tbParmDefaultValue";
        this.tbParmDefaultValue.ReadOnly = true;
        this.tbParmDefaultValue.Size = new System.Drawing.Size(328, 20);
        this.tbParmDefaultValue.TabIndex = 1;
        //
        // bDefaultValues
        //
        this.bDefaultValues.Location = new System.Drawing.Point(407, 16);
        this.bDefaultValues.Name = "bDefaultValues";
        this.bDefaultValues.Size = new System.Drawing.Size(23, 23);
        this.bDefaultValues.TabIndex = 2;
        this.bDefaultValues.Text = "...";
        this.bDefaultValues.Click += new System.EventHandler(this.bDefaultValues_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(240, 46);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(64, 23);
        this.label4.TabIndex = 35;
        this.label4.Text = "Value Field";
        //
        // cbDefaultValueField
        //
        this.cbDefaultValueField.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDefaultValueField.Location = new System.Drawing.Point(312, 40);
        this.cbDefaultValueField.Name = "cbDefaultValueField";
        this.cbDefaultValueField.Size = new System.Drawing.Size(112, 21);
        this.cbDefaultValueField.TabIndex = 5;
        this.cbDefaultValueField.SelectedIndexChanged += new System.EventHandler(this.cbDefaultValueField_SelectedIndexChanged);
        //
        // cbDefaultDataSets
        //
        this.cbDefaultDataSets.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDefaultDataSets.Location = new System.Drawing.Point(112, 40);
        this.cbDefaultDataSets.Name = "cbDefaultDataSets";
        this.cbDefaultDataSets.Size = new System.Drawing.Size(112, 21);
        this.cbDefaultDataSets.TabIndex = 4;
        this.cbDefaultDataSets.SelectedIndexChanged += new System.EventHandler(this.cbDefaultDataSets_SelectedIndexChanged);
        //
        // rbDefaultValues
        //
        this.rbDefaultValues.Location = new System.Drawing.Point(8, 16);
        this.rbDefaultValues.Name = "rbDefaultValues";
        this.rbDefaultValues.Size = new System.Drawing.Size(64, 24);
        this.rbDefaultValues.TabIndex = 0;
        this.rbDefaultValues.Text = "Values";
        this.rbDefaultValues.CheckedChanged += new System.EventHandler(this.rbDefaultValues_CheckedChanged);
        //
        // rbDefaultDataSetName
        //
        this.rbDefaultDataSetName.Location = new System.Drawing.Point(8, 40);
        this.rbDefaultDataSetName.Name = "rbDefaultDataSetName";
        this.rbDefaultDataSetName.Size = new System.Drawing.Size(104, 24);
        this.rbDefaultDataSetName.TabIndex = 3;
        this.rbDefaultDataSetName.Text = "Data Set Name";
        this.rbDefaultDataSetName.CheckedChanged += new System.EventHandler(this.rbDefaultDataSetName_CheckedChanged);
        //
        // ckbParmAllowNull
        //
        this.ckbParmAllowNull.Location = new System.Drawing.Point(150, 72);
        this.ckbParmAllowNull.Name = "ckbParmAllowNull";
        this.ckbParmAllowNull.Size = new System.Drawing.Size(72, 24);
        this.ckbParmAllowNull.TabIndex = 8;
        this.ckbParmAllowNull.Text = "Allow null";
        this.ckbParmAllowNull.CheckedChanged += new System.EventHandler(this.ckbParmAllowNull_CheckedChanged);
        //
        // ckbParmMultiValue
        //
        this.ckbParmMultiValue.Location = new System.Drawing.Point(376, 72);
        this.ckbParmMultiValue.Name = "ckbParmMultiValue";
        this.ckbParmMultiValue.Size = new System.Drawing.Size(79, 24);
        this.ckbParmMultiValue.TabIndex = 24;
        this.ckbParmMultiValue.Text = "MultiValue";
        this.ckbParmMultiValue.CheckedChanged += new System.EventHandler(this.ckbParmMultiValue_CheckedChanged);
        //
        // ReportParameterCtl
        //
        this.Controls.Add(this.ckbParmMultiValue);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.bParmDown);
        this.Controls.Add(this.bParmUp);
        this.Controls.Add(this.ckbParmAllowBlank);
        this.Controls.Add(this.ckbParmAllowNull);
        this.Controls.Add(this.tbParmPrompt);
        this.Controls.Add(this.lParmPrompt);
        this.Controls.Add(this.cbParmType);
        this.Controls.Add(this.lParmType);
        this.Controls.Add(this.tbParmName);
        this.Controls.Add(this.lParmName);
        this.Controls.Add(this.bRemove);
        this.Controls.Add(this.bAdd);
        this.Controls.Add(this.lbParameters);
        this.Controls.Add(this.groupBox2);
        this.Name = "ReportParameterCtl";
        this.Size = new System.Drawing.Size(456, 296);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        _Draw.removeElement(rNode,"ReportParameters");
        // remove old ReportParameters
        if (this.lbParameters.Items.Count <= 0)
            return ;
         
        // nothing in list?  all done
        XmlNode rpsNode = _Draw.setElement(rNode,"ReportParameters",null);
        for (Object __dummyForeachVar3 : lbParameters.Items)
        {
            ReportParm repParm = (ReportParm)__dummyForeachVar3;
            if (repParm.getName() == null || repParm.getName().Length <= 0)
                continue;
             
            // shouldn't really happen
            XmlNode repNode = _Draw.createElement(rpsNode,"ReportParameter",null);
            // Create the name attribute
            _Draw.setElementAttribute(repNode,"Name",repParm.getName());
            _Draw.setElement(repNode,"DataType",repParm.getDataType());
            // Handle default values
            applyDefaultValues(repNode,repParm);
            _Draw.setElement(repNode,"Nullable",repParm.getAllowNull() ? "true" : "false");
            _Draw.setElement(repNode,"AllowBlank",repParm.getAllowBlank() ? "true" : "false");
            _Draw.setElement(repNode,"MultiValue",repParm.getMultiValue() ? "true" : "false");
            _Draw.setElement(repNode,"Prompt",repParm.getPrompt());
            // Handle ValidValues
            applyValidValues(repNode,repParm);
        }
    }

    private void applyDefaultValues(XmlNode repNode, ReportParm repParm) throws Exception {
        _Draw.removeElement(repNode,"DefaultValue");
        if (repParm.getDefault())
        {
            if (repParm.getDefaultValue() == null || repParm.getDefaultValue().Count == 0)
                return ;
             
            XmlNode defNode = _Draw.getCreateNamedChildNode(repNode,"DefaultValue");
            XmlNode vNodes = _Draw.getCreateNamedChildNode(defNode,"Values");
            for (Object __dummyForeachVar4 : repParm.getDefaultValue())
            {
                String dv = (String)__dummyForeachVar4;
                _Draw.createElement(vNodes,"Value",dv);
            }
        }
        else
        {
            if (repParm.getDefaultDSRDataSetName() == null || repParm.getDefaultDSRDataSetName().Length == 0 || repParm.getDefaultDSRValueField() == null || repParm.getDefaultDSRValueField().Length == 0)
                return ;
             
            XmlNode defNode = _Draw.getCreateNamedChildNode(repNode,"DefaultValue");
            XmlNode dsrNode = _Draw.getCreateNamedChildNode(defNode,"DataSetReference");
            _Draw.createElement(dsrNode,"DataSetName",repParm.getDefaultDSRDataSetName());
            _Draw.createElement(dsrNode,"ValueField",repParm.getDefaultDSRValueField());
        } 
    }

    private void applyValidValues(XmlNode repNode, ReportParm repParm) throws Exception {
        _Draw.removeElement(repNode,"ValidValues");
        if (repParm.getValid())
        {
            if (repParm.getValidValues() == null || repParm.getValidValues().Count == 0)
                return ;
             
            XmlNode vvNode = _Draw.getCreateNamedChildNode(repNode,"ValidValues");
            XmlNode vNodes = _Draw.getCreateNamedChildNode(vvNode,"ParameterValues");
            for (Object __dummyForeachVar5 : repParm.getValidValues())
            {
                // put out the parameter values
                ParameterValueItem dvi = (ParameterValueItem)__dummyForeachVar5;
                XmlNode pvNode = _Draw.createElement(vNodes,"ParameterValue",null);
                _Draw.createElement(pvNode,"Value",dvi.Value);
                if (dvi.Label != null)
                    _Draw.createElement(pvNode,"Label",dvi.Label);
                 
            }
        }
        else
        {
            if (repParm.getValidValuesDSRDataSetName() == null || repParm.getValidValuesDSRDataSetName().Length == 0 || repParm.getValidValuesDSRValueField() == null || repParm.getValidValuesDSRValueField().Length == 0)
                return ;
             
            XmlNode defNode = _Draw.getCreateNamedChildNode(repNode,"ValidValues");
            XmlNode dsrNode = _Draw.getCreateNamedChildNode(defNode,"DataSetReference");
            _Draw.createElement(dsrNode,"DataSetName",repParm.getValidValuesDSRDataSetName());
            _Draw.createElement(dsrNode,"ValueField",repParm.getValidValuesDSRValueField());
            if (repParm.getValidValuesDSRLabelField() != null && repParm.getValidValuesDSRLabelField().Length > 0)
                _Draw.createElement(dsrNode,"LabelField",repParm.getValidValuesDSRLabelField());
             
        } 
    }

    private void bAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ReportParm rp = new ReportParm("newparm");
        int cur = this.lbParameters.Items.Add(rp);
        lbParameters.SelectedIndex = cur;
        this.tbParmName.Focus();
    }

    private void bRemove_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        lbParameters.Items.RemoveAt(cur);
        if (lbParameters.Items.Count <= 0)
            return ;
         
        cur--;
        if (cur < 0)
            cur = 0;
         
        lbParameters.SelectedIndex = cur;
    }

    private void lbParameters_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        tbParmName.Text = rp.getName();
        cbParmType.Text = rp.getDataType();
        tbParmPrompt.Text = rp.getPrompt();
        ckbParmAllowBlank.Checked = rp.getAllowBlank();
        ckbParmMultiValue.Checked = rp.getMultiValue();
        ckbParmAllowNull.Checked = rp.getAllowNull();
        // Handle default values
        if (rp.getDefault())
        {
            this.rbDefaultValues.Checked = true;
            tbParmDefaultValue.Text = rp.getDefaultValueDisplay();
            tbParmDefaultValue.Enabled = bDefaultValues.Enabled = true;
            this.cbDefaultDataSets.Enabled = false;
            this.cbDefaultValueField.Enabled = false;
            this.cbDefaultDataSets.SelectedIndex = -1;
            this.cbDefaultValueField.SelectedIndex = -1;
        }
        else
        {
            this.rbDefaultDataSetName.Checked = true;
            this.cbDefaultDataSets.Text = rp.getDefaultDSRDataSetName();
            this.cbDefaultValueField.Text = rp.getDefaultDSRValueField();
            tbParmDefaultValue.Enabled = bDefaultValues.Enabled = false;
            tbParmDefaultValue.Text = "";
            this.cbDefaultDataSets.Enabled = true;
            this.cbDefaultValueField.Enabled = true;
        } 
        // Handle Valid Values
        if (rp.getValid())
        {
            this.rbValues.Checked = true;
            tbParmValidValues.Text = rp.getValidValuesDisplay();
            tbParmValidValues.Enabled = bValidValues.Enabled = true;
            this.cbValidDataSets.Enabled = this.cbValidFields.Enabled = this.cbValidDisplayField.Enabled = false;
            this.cbValidDataSets.SelectedIndex = this.cbValidFields.SelectedIndex = this.cbValidDisplayField.SelectedIndex = -1;
        }
        else
        {
            this.rbDataSet.Checked = true;
            this.cbValidDataSets.Text = rp.getValidValuesDSRDataSetName();
            this.cbValidFields.Text = rp.getValidValuesDSRValueField();
            this.cbValidDisplayField.Text = rp.getValidValuesDSRLabelField() == null ? "" : rp.getValidValuesDSRLabelField();
            this.cbValidDataSets.Enabled = this.cbValidFields.Enabled = this.cbValidDisplayField.Enabled = true;
            tbParmValidValues.Enabled = bValidValues.Enabled = false;
            tbParmValidValues.Text = "";
        } 
    }

    private void lbParameters_MoveItem(int curloc, int newloc) throws Exception {
        ReportParm rp = lbParameters.Items[curloc] instanceof ReportParm ? (ReportParm)lbParameters.Items[curloc] : (ReportParm)null;
        if (rp == null)
            return ;
         
        lbParameters.BeginUpdate();
        lbParameters.Items.RemoveAt(curloc);
        lbParameters.Items.Insert(newloc, rp);
        lbParameters.SelectedIndex = newloc;
        lbParameters.EndUpdate();
    }

    private void tbParmName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        if (StringSupport.equals(rp.getName(), tbParmName.Text))
            return ;
         
        rp.setName(tbParmName.Text);
        // text doesn't change in listbox; force change by removing and re-adding item
        lbParameters_MoveItem(cur,cur);
    }

    private void cbParmType_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDataType(cbParmType.Text);
    }

    private void tbParmPrompt_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setPrompt(tbParmPrompt.Text);
    }

    private void ckbParmAllowNull_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setAllowNull(ckbParmAllowNull.Checked);
    }

    private void ckbParmAllowBlank_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setAllowBlank(ckbParmAllowBlank.Checked);
    }

    private void bParmUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur <= 0)
            return ;
         
        lbParameters_MoveItem(cur,cur - 1);
    }

    private void bParmDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur + 1 >= lbParameters.Items.Count)
            return ;
         
        lbParameters_MoveItem(cur,cur + 1);
    }

    private void cbDefaultDataSets_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDefaultDSRDataSetName(cbDefaultDataSets.Text);
        // Populate the fields from the selected dataset
        this.cbDefaultValueField.Items.Clear();
        String[] fields = _Draw.GetFields(cbDefaultDataSets.Text, false);
        this.cbDefaultValueField.Items.AddRange(fields);
    }

    private void cbValidDataSets_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setValidValuesDSRDataSetName(cbValidDataSets.Text);
        // Populate the fields from the selected dataset
        this.cbValidFields.Items.Clear();
        String[] fields = _Draw.GetFields(cbValidDataSets.Text, false);
        this.cbValidFields.Items.AddRange(fields);
        this.cbValidDisplayField.Items.Clear();
        this.cbValidDisplayField.Items.Add("");
        this.cbValidDisplayField.Items.AddRange(fields);
    }

    private void cbDefaultValueField_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDefaultDSRValueField(cbDefaultValueField.Text);
    }

    private void cbValidFields_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setValidValuesDSRValueField(cbValidFields.Text);
    }

    private void cbValidDisplayField_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setValidValuesDSRLabelField(cbValidDisplayField.Text);
    }

    private void rbDefaultValues_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDefault(rbDefaultValues.Checked);
        tbParmDefaultValue.Enabled = bDefaultValues.Enabled = rbDefaultValues.Checked;
        this.cbDefaultDataSets.Enabled = this.cbDefaultValueField.Enabled = !rbDefaultValues.Checked;
    }

    private void rbDefaultDataSetName_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDefault(!rbDefaultDataSetName.Checked);
        tbParmDefaultValue.Enabled = bDefaultValues.Enabled = !rbDefaultDataSetName.Checked;
        this.cbDefaultDataSets.Enabled = this.cbDefaultValueField.Enabled = rbDefaultDataSetName.Checked;
    }

    private void rbValues_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        this.tbParmValidValues.Enabled = bValidValues.Enabled = rbValues.Checked;
        rp.setValid(rbValues.Checked);
        this.cbValidDisplayField.Enabled = this.cbValidFields.Enabled = this.cbValidDataSets.Enabled = !rbValues.Checked;
    }

    private void rbDataSet_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setValid(!rbDataSet.Checked);
        this.tbParmValidValues.Enabled = bValidValues.Enabled = !rbDataSet.Checked;
        this.cbValidDisplayField.Enabled = this.cbValidFields.Enabled = this.cbValidDataSets.Enabled = rbDataSet.Checked;
    }

    private void bDefaultValues_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        DialogListOfStrings dlos = new DialogListOfStrings(rp.getDefaultValue());
        dlos.Text = "Default Values";
        if (dlos.ShowDialog() != DialogResult.OK)
            return ;
         
        rp.setDefaultValue(dlos.getListOfStrings());
        this.tbParmDefaultValue.Text = rp.getDefaultValueDisplay();
    }

    private void bValidValues_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        DialogValidValues dvv = new DialogValidValues(rp.getValidValues());
        if (dvv.ShowDialog() != DialogResult.OK)
            return ;
         
        rp.setValidValues(dvv.getValidValues());
        this.tbParmValidValues.Text = rp.getValidValuesDisplay();
    }

    private void ckbParmMultiValue_CheckedChanged(Object sender, EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setMultiValue(ckbParmMultiValue.Checked);
    }

}


