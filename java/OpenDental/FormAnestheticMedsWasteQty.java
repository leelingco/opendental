//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.FormAnestheticMedsWasteQty;
import OpenDental.Properties.Resources;

public class FormAnestheticMedsWasteQty  extends Form 
{

    public FormAnestheticMedsWasteQty() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAnestheticMedsWasteQty_Load(Object sender, EventArgs e) throws Exception {
    }

    private void textDate_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textAnesthMed_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textDate_TextChanged_1(Object sender, EventArgs e) throws Exception {
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void groupBox1_Enter(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnestheticMedsWasteQty.class);
        this.textDate = new System.Windows.Forms.TextBox();
        this.textBoxPatID = new System.Windows.Forms.TextBox();
        this.labelPatID = new System.Windows.Forms.Label();
        this.textBoxPatient = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBoxAnesthMedWasteQty = new System.Windows.Forms.GroupBox();
        this.label2 = new System.Windows.Forms.Label();
        this.labelNotes = new System.Windows.Forms.Label();
        this.richTextBox1 = new System.Windows.Forms.RichTextBox();
        this.comboBoxAnesthMed = new System.Windows.Forms.ComboBox();
        this.textQtyWasted = new System.Windows.Forms.TextBox();
        this.labelQty = new System.Windows.Forms.Label();
        this.labelFormInfo = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelDate = new System.Windows.Forms.Label();
        this.butClose = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBoxAnesthMedWasteQty.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(40, 37);
        this.textDate.Name = "textDate";
        this.textDate.ReadOnly = true;
        this.textDate.Size = new System.Drawing.Size(115, 20);
        this.textDate.TabIndex = 9;
        this.textDate.TextChanged += new System.EventHandler(this.textDate_TextChanged_1);
        //
        // textBoxPatID
        //
        this.textBoxPatID.Location = new System.Drawing.Point(282, 88);
        this.textBoxPatID.Name = "textBoxPatID";
        this.textBoxPatID.ReadOnly = true;
        this.textBoxPatID.Size = new System.Drawing.Size(113, 20);
        this.textBoxPatID.TabIndex = 108;
        //
        // labelPatID
        //
        this.labelPatID.AutoSize = true;
        this.labelPatID.Location = new System.Drawing.Point(284, 71);
        this.labelPatID.Name = "labelPatID";
        this.labelPatID.Size = new System.Drawing.Size(38, 13);
        this.labelPatID.TabIndex = 107;
        this.labelPatID.Text = "ID No.";
        //
        // textBoxPatient
        //
        this.textBoxPatient.Location = new System.Drawing.Point(40, 88);
        this.textBoxPatient.Name = "textBoxPatient";
        this.textBoxPatient.ReadOnly = true;
        this.textBoxPatient.Size = new System.Drawing.Size(231, 20);
        this.textBoxPatient.TabIndex = 106;
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(37, 71);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(69, 13);
        this.label1.TabIndex = 138;
        this.label1.Text = "Patient name";
        //
        // groupBoxAnesthMedWasteQty
        //
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.label2);
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.labelNotes);
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.richTextBox1);
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.comboBoxAnesthMed);
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.textQtyWasted);
        this.groupBoxAnesthMedWasteQty.Controls.Add(this.labelQty);
        this.groupBoxAnesthMedWasteQty.Location = new System.Drawing.Point(25, 117);
        this.groupBoxAnesthMedWasteQty.Name = "groupBoxAnesthMedWasteQty";
        this.groupBoxAnesthMedWasteQty.Size = new System.Drawing.Size(487, 150);
        this.groupBoxAnesthMedWasteQty.TabIndex = 139;
        this.groupBoxAnesthMedWasteQty.TabStop = false;
        this.groupBoxAnesthMedWasteQty.Text = "Anesthetic Medication";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(259, 50);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(199, 52);
        this.label2.TabIndex = 12;
        this.label2.Text = "NOTE: Wasted quantities must be in mL to be properly subtracted from inventory";
        //
        // labelNotes
        //
        this.labelNotes.AutoSize = true;
        this.labelNotes.Location = new System.Drawing.Point(15, 50);
        this.labelNotes.Name = "labelNotes";
        this.labelNotes.Size = new System.Drawing.Size(35, 13);
        this.labelNotes.TabIndex = 11;
        this.labelNotes.Text = "Notes";
        //
        // richTextBox1
        //
        this.richTextBox1.Location = new System.Drawing.Point(15, 69);
        this.richTextBox1.Name = "richTextBox1";
        this.richTextBox1.Size = new System.Drawing.Size(231, 66);
        this.richTextBox1.TabIndex = 10;
        this.richTextBox1.Text = "";
        //
        // comboBoxAnesthMed
        //
        this.comboBoxAnesthMed.FormattingEnabled = true;
        this.comboBoxAnesthMed.Location = new System.Drawing.Point(15, 19);
        this.comboBoxAnesthMed.Name = "comboBoxAnesthMed";
        this.comboBoxAnesthMed.Size = new System.Drawing.Size(231, 21);
        this.comboBoxAnesthMed.TabIndex = 0;
        //
        // textQtyWasted
        //
        this.textQtyWasted.Location = new System.Drawing.Point(257, 20);
        this.textQtyWasted.Name = "textQtyWasted";
        this.textQtyWasted.Size = new System.Drawing.Size(219, 20);
        this.textQtyWasted.TabIndex = 2;
        //
        // labelQty
        //
        this.labelQty.AutoSize = true;
        this.labelQty.Location = new System.Drawing.Point(254, 0);
        this.labelQty.Name = "labelQty";
        this.labelQty.Size = new System.Drawing.Size(106, 13);
        this.labelQty.TabIndex = 4;
        this.labelQty.Text = "Quantity wasted (mL)";
        //
        // labelFormInfo
        //
        this.labelFormInfo.ImageAlign = System.Drawing.ContentAlignment.TopLeft;
        this.labelFormInfo.Location = new System.Drawing.Point(154, 12);
        this.labelFormInfo.Name = "labelFormInfo";
        this.labelFormInfo.Size = new System.Drawing.Size(322, 40);
        this.labelFormInfo.TabIndex = 140;
        this.labelFormInfo.Text = "This form should be used to waste quantities of anesthetic medications that have " + "been drawn up but not given to the patient so that inventory has the correct cou" + "nt.";
        this.labelFormInfo.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelDate);
        this.groupBox1.Controls.Add(this.labelFormInfo);
        this.groupBox1.Location = new System.Drawing.Point(25, 3);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(487, 120);
        this.groupBox1.TabIndex = 141;
        this.groupBox1.TabStop = false;
        //
        // labelDate
        //
        this.labelDate.AutoSize = true;
        this.labelDate.Location = new System.Drawing.Point(15, 11);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(30, 13);
        this.labelDate.TabIndex = 141;
        this.labelDate.Text = "Date";
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClose.Location = new System.Drawing.Point(422, 277);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(90, 26);
        this.butClose.TabIndex = 137;
        this.butClose.Text = "Save and Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(350, 277);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(66, 26);
        this.butCancel.TabIndex = 54;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAnestheticMedsWasteQty
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(535, 319);
        this.Controls.Add(this.groupBoxAnesthMedWasteQty);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textBoxPatID);
        this.Controls.Add(this.labelPatID);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.textBoxPatient);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.groupBox1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnestheticMedsWasteQty";
        this.Text = "Anesthetic Medication Waste Form";
        this.groupBoxAnesthMedWasteQty.ResumeLayout(false);
        this.groupBoxAnesthMedWasteQty.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textBoxPatID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelPatID = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBoxPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxAnesthMedWasteQty = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelNotes = new System.Windows.Forms.Label();
    private System.Windows.Forms.RichTextBox richTextBox1 = new System.Windows.Forms.RichTextBox();
    private System.Windows.Forms.ComboBox comboBoxAnesthMed = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textQtyWasted = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelQty = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelFormInfo = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
}
//private DataSet DataSetAnesthMedAdjQty;

//private DataSet DataSetAnesthMedAdjQty;