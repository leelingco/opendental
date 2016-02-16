//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormLoincs;
import OpenDental.FormSnomeds;
import OpenDental.MsgBox;
import OpenDentBusiness.Ucum;
import OpenDentBusiness.Ucums;

public class FormCDSILabResult  extends Form 
{

    private List<Ucum> _listUCUM = new List<Ucum>();
    /**
    * CDSI Trigger formatted text. This is the form result.
    */
    public String ResultCDSITriggerText = new String();
    public FormCDSILabResult() throws Exception {
        initializeComponent();
    }

    private void formLabResultEdit_Load(Object sender, EventArgs e) throws Exception {
        fillCombos();
        checkAllResults.Checked = true;
    }

    private void fillCombos() throws Exception {
        comboComparator.Items.Add("=");
        comboComparator.Items.Add(">=");
        comboComparator.Items.Add(">");
        comboComparator.Items.Add("<");
        comboComparator.Items.Add("<=");
        comboComparator.SelectedIndex = 0;
        //not sure if this code works. Test it.
        _listUCUM = Ucums.getAll();
        if (_listUCUM.Count == 0)
        {
            MsgBox.show(this,"Units of measure have not been imported. Go to the code system importer window to import UCUM codes to continue.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        int _tempSelectedIndex = 0;
        for (int i = 0;i < _listUCUM.Count;i++)
        {
            comboUnits.Items.Add(_listUCUM[i].UcumCode);
            if (StringSupport.equals(_listUCUM[i].UcumCode, "mg/dL"))
            {
                //arbitrarily chosen common unit of measure.
                _tempSelectedIndex = i;
            }
             
        }
        comboUnits.SelectedIndex = _tempSelectedIndex;
    }

    private void butLoinc_Click(Object sender, EventArgs e) throws Exception {
        FormLoincs FormL = new FormLoincs();
        FormL.IsSelectionMode = true;
        FormL.ShowDialog();
        if (FormL.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textLoinc.Text = FormL.SelectedLoinc.LoincCode;
        textLoincDescription.Text = FormL.SelectedLoinc.NameLongCommon;
        //if(FormL.SelectedLoinc.UnitsUCUM!="") {
        comboUnits.Text = FormL.SelectedLoinc.UnitsUCUM;
    }

    //may be values that are not available otherwise. There are 270 units in the Loinc table that are not in the UCUM table.
    //}
    private void butSnomed_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        FormS.IsSelectionMode = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //Clear other options
        checkAllResults.Checked = false;
        textObsValue.Text = "";
        //Set Microbiology results
        textSnomed.Text = FormS.SelectedSnomed.SnomedCode;
        textSnomedDescription.Text = FormS.SelectedSnomed.Description;
    }

    private void textObsValue_TextChanged(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textObsValue.Text, "") && StringSupport.equals(textSnomed.Text, ""))
        {
            //cleared the text
            checkAllResults.Checked = true;
            return ;
        }
         
        //user tried unchecking box but nothing else is selected.
        checkAllResults.Checked = false;
        textSnomed.Text = "";
        textSnomedDescription.Text = "";
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        ResultCDSITriggerText = textLoinc.Text;
        /* +";";
        			if(!checkAllResults.Checked && textObsValue.Text=="" && textSnomed.Text=="") {
        				MsgBox.Show(this,"Please select a valid lab result comparison.");
        				return;//should never happen.  Somehow they have an invalid comparison set up.
        			}
        			else if(checkAllResults.Checked && textObsValue.Text=="" && textSnomed.Text=="") {
        				ResultCDSITriggerText+=";";//loinc comparison only.
        			}
        			else if(!checkAllResults.Checked && textObsValue.Text!="" && textSnomed.Text=="") {
        				ResultCDSITriggerText+=comboComparator.Text+textObsValue.Text+";"+comboUnits.Text;//Example:  >150;mg/dL
        			}
        			else if(!checkAllResults.Checked && textObsValue.Text=="" && textSnomed.Text!="") {
        				ResultCDSITriggerText+=textSnomed.Text+";";//leave units blank to signify snomed.
        			}
        			else {
        				MsgBox.Show(this,"Please select a valid lab result comparison.");
        				return;//should never happen.  Somehow they have an invalid comparison set up.
        			}*/
        DialogResult = DialogResult.OK;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.butCancel = new System.Windows.Forms.Button();
        this.butOk = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textLoinc = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textObsValue = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textLoincDescription = new System.Windows.Forms.TextBox();
        this.butLoinc = new System.Windows.Forms.Button();
        this.comboComparator = new System.Windows.Forms.ComboBox();
        this.comboUnits = new System.Windows.Forms.ComboBox();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textSnomedDescription = new System.Windows.Forms.TextBox();
        this.butSnomed = new System.Windows.Forms.Button();
        this.textSnomed = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkAllResults = new System.Windows.Forms.CheckBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.groupBox5.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(328, 323);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(247, 323);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 5;
        this.butOk.Text = "Ok";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 324);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 7;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        //
        // textLoinc
        //
        this.textLoinc.Location = new System.Drawing.Point(116, 12);
        this.textLoinc.Name = "textLoinc";
        this.textLoinc.ReadOnly = true;
        this.textLoinc.Size = new System.Drawing.Size(105, 20);
        this.textLoinc.TabIndex = 0;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(45, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(70, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "LOINC";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textObsValue
        //
        this.textObsValue.Location = new System.Drawing.Point(162, 20);
        this.textObsValue.Name = "textObsValue";
        this.textObsValue.Size = new System.Drawing.Size(142, 20);
        this.textObsValue.TabIndex = 3;
        this.textObsValue.TextChanged += new System.EventHandler(this.textObsValue_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(21, 38);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(93, 17);
        this.label3.TabIndex = 222;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLoincDescription
        //
        this.textLoincDescription.Location = new System.Drawing.Point(116, 38);
        this.textLoincDescription.Multiline = true;
        this.textLoincDescription.Name = "textLoincDescription";
        this.textLoincDescription.Size = new System.Drawing.Size(287, 49);
        this.textLoincDescription.TabIndex = 221;
        //
        // butLoinc
        //
        this.butLoinc.Location = new System.Drawing.Point(227, 11);
        this.butLoinc.Name = "butLoinc";
        this.butLoinc.Size = new System.Drawing.Size(24, 21);
        this.butLoinc.TabIndex = 223;
        this.butLoinc.Text = "...";
        this.butLoinc.UseVisualStyleBackColor = true;
        this.butLoinc.Click += new System.EventHandler(this.butLoinc_Click);
        //
        // comboComparator
        //
        this.comboComparator.FormattingEnabled = true;
        this.comboComparator.Location = new System.Drawing.Point(104, 19);
        this.comboComparator.Name = "comboComparator";
        this.comboComparator.Size = new System.Drawing.Size(52, 21);
        this.comboComparator.TabIndex = 224;
        //
        // comboUnits
        //
        this.comboUnits.FormattingEnabled = true;
        this.comboUnits.Location = new System.Drawing.Point(310, 19);
        this.comboUnits.Name = "comboUnits";
        this.comboUnits.Size = new System.Drawing.Size(75, 21);
        this.comboUnits.TabIndex = 225;
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.label5);
        this.groupBox5.Controls.Add(this.comboUnits);
        this.groupBox5.Controls.Add(this.textObsValue);
        this.groupBox5.Controls.Add(this.comboComparator);
        this.groupBox5.Location = new System.Drawing.Point(12, 150);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(391, 51);
        this.groupBox5.TabIndex = 226;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Numeric Results";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 21);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(93, 17);
        this.label5.TabIndex = 226;
        this.label5.Text = "Value";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.textSnomedDescription);
        this.groupBox1.Controls.Add(this.butSnomed);
        this.groupBox1.Controls.Add(this.textSnomed);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Location = new System.Drawing.Point(12, 207);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(391, 100);
        this.groupBox1.TabIndex = 227;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Microbiology Results";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(6, 45);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(96, 17);
        this.label4.TabIndex = 226;
        this.label4.Text = "Description";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSnomedDescription
        //
        this.textSnomedDescription.Location = new System.Drawing.Point(104, 45);
        this.textSnomedDescription.Multiline = true;
        this.textSnomedDescription.Name = "textSnomedDescription";
        this.textSnomedDescription.Size = new System.Drawing.Size(281, 49);
        this.textSnomedDescription.TabIndex = 225;
        //
        // butSnomed
        //
        this.butSnomed.Location = new System.Drawing.Point(310, 18);
        this.butSnomed.Name = "butSnomed";
        this.butSnomed.Size = new System.Drawing.Size(24, 21);
        this.butSnomed.TabIndex = 224;
        this.butSnomed.Text = "...";
        this.butSnomed.UseVisualStyleBackColor = true;
        this.butSnomed.Click += new System.EventHandler(this.butSnomed_Click);
        //
        // textSnomed
        //
        this.textSnomed.Location = new System.Drawing.Point(104, 19);
        this.textSnomed.Name = "textSnomed";
        this.textSnomed.ReadOnly = true;
        this.textSnomed.Size = new System.Drawing.Size(200, 20);
        this.textSnomed.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 20);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(97, 17);
        this.label2.TabIndex = 4;
        this.label2.Text = "SNOMED CT";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkAllResults
        //
        this.checkAllResults.Location = new System.Drawing.Point(104, 19);
        this.checkAllResults.Name = "checkAllResults";
        this.checkAllResults.Size = new System.Drawing.Size(281, 23);
        this.checkAllResults.TabIndex = 228;
        this.checkAllResults.Tag = "";
        this.checkAllResults.Text = "Trigger intervention reguardless of result value";
        this.checkAllResults.UseVisualStyleBackColor = true;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.checkAllResults);
        this.groupBox2.Location = new System.Drawing.Point(12, 93);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(391, 51);
        this.groupBox2.TabIndex = 227;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "All Results";
        //
        // FormCDSILabResult
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(415, 359);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.butLoinc);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textLoincDescription);
        this.Controls.Add(this.textLoinc);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCDSILabResult";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinical Decision Support Lab";
        this.Load += new System.EventHandler(this.FormLabResultEdit_Load);
        this.groupBox5.ResumeLayout(false);
        this.groupBox5.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textLoinc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textObsValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLoincDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butLoinc = new System.Windows.Forms.Button();
    private System.Windows.Forms.ComboBox comboComparator = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboUnits = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSnomedDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butSnomed = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textSnomed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkAllResults = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
}


