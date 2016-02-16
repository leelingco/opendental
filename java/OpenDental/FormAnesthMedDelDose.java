//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.FormAnesthMedDelDose;
import OpenDental.Properties.Resources;

public class FormAnesthMedDelDose  extends Form 
{

    public AnestheticMedsGiven Med = new AnestheticMedsGiven();
    public int anestheticRecordNum = new int();
    public int anestheticMedNum = new int();
    public FormAnesthMedDelDose() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAnesthMedDelDose_Load(Object sender, EventArgs e) throws Exception {
        textAnesthMedName.Text = Med.AnesthMedName;
        textDose.Text = Med.QtyGiven;
        textDoseTimeStamp.Text = Med.DoseTimeStamp;
        textQtyWasted.Text = Med.QtyWasted;
        anestheticMedNum = Convert.ToInt32(Med.AnestheticMedNum);
        anestheticRecordNum = Convert.ToInt32(Med.AnestheticRecordNum);
    }

    private void textDate_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textAnesthMed_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        //Regular Expressions to validate the format of the entered value in the textAnesthDose and textQtyWasted
        Regex regexDose = new Regex("^\\d{1,4}(\\.\\d{1,2})?$");
        Regex regexQtyWasted = new Regex("^\\d{1,4}(\\.\\d{1,2})?$");
        if (!(regexDose.IsMatch(textDose.Text)) && !StringSupport.equals(textDose.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Dose should be xxx.xx format"));
            textQtyWasted.Focus();
        }
        else if (!(regexQtyWasted.IsMatch(textQtyWasted.Text)) && !StringSupport.equals(textQtyWasted.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Amount wasted should be xxx.xx format"));
            textQtyWasted.Focus();
        }
        else
        {
            double dose = Convert.ToDouble(textDose.Text);
            double amtWasted = Convert.ToDouble(textQtyWasted.Text);
            AnesthMeds.UpdateAMedDose(textAnesthMedName.Text, Convert.ToDouble(textDose.Text), Convert.ToDouble(amtWasted), textDoseTimeStamp.Text, anestheticMedNum, anestheticRecordNum);
            Close();
        }  
    }

    private void textDose_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void butDelAnesthMeds_Click(Object sender, EventArgs e) throws Exception {
        //Regular Expressions to validate the format of the entered value in the textAnesthDose and textQtyWasted
        Regex regexDose = new Regex("^\\d{1,4}(\\.\\d{1,2})?$");
        Regex regexQtyWasted = new Regex("^\\d{1,4}(\\.\\d{1,2})?$");
        if (!(regexDose.IsMatch(textDose.Text)) && !StringSupport.equals(textDose.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Dose should be xxx.xx format"));
            textQtyWasted.Focus();
        }
        else if (!(regexQtyWasted.IsMatch(textQtyWasted.Text)) && !StringSupport.equals(textQtyWasted.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Amount wasted should be xxx.xx format"));
            textQtyWasted.Focus();
        }
        else
        {
            AnesthMeds.DeleteAMedDose(textAnesthMedName.Text, Convert.ToDouble(textDose.Text), Convert.ToDouble(textQtyWasted.Text), textDoseTimeStamp.Text, anestheticRecordNum);
            DialogResult = DialogResult.OK;
        }  
    }

    private void groupBoxAnesthMedDelete_Enter(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnesthMedDelDose.class);
        this.textDoseTimeStamp = new System.Windows.Forms.TextBox();
        this.groupBoxAnesthMedDelete = new System.Windows.Forms.GroupBox();
        this.labelQtyWasted = new System.Windows.Forms.Label();
        this.textQtyWasted = new System.Windows.Forms.TextBox();
        this.labelDoseTimeStamp = new System.Windows.Forms.Label();
        this.textAnesthMedName = new System.Windows.Forms.TextBox();
        this.textDose = new System.Windows.Forms.TextBox();
        this.labelDose = new System.Windows.Forms.Label();
        this.labelDeleteInstrux = new System.Windows.Forms.Label();
        this.butDelAnesthMeds = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupDelMed = new System.Windows.Forms.GroupBox();
        this.groupBoxAnesthMedDelete.SuspendLayout();
        this.groupDelMed.SuspendLayout();
        this.SuspendLayout();
        //
        // textDoseTimeStamp
        //
        this.textDoseTimeStamp.Location = new System.Drawing.Point(15, 72);
        this.textDoseTimeStamp.Name = "textDoseTimeStamp";
        this.textDoseTimeStamp.Size = new System.Drawing.Size(137, 20);
        this.textDoseTimeStamp.TabIndex = 9;
        this.textDoseTimeStamp.TextChanged += new System.EventHandler(this.textDate_TextChanged);
        //
        // groupBoxAnesthMedDelete
        //
        this.groupBoxAnesthMedDelete.Controls.Add(this.labelQtyWasted);
        this.groupBoxAnesthMedDelete.Controls.Add(this.textQtyWasted);
        this.groupBoxAnesthMedDelete.Controls.Add(this.labelDoseTimeStamp);
        this.groupBoxAnesthMedDelete.Controls.Add(this.textAnesthMedName);
        this.groupBoxAnesthMedDelete.Controls.Add(this.textDose);
        this.groupBoxAnesthMedDelete.Controls.Add(this.labelDose);
        this.groupBoxAnesthMedDelete.Controls.Add(this.textDoseTimeStamp);
        this.groupBoxAnesthMedDelete.Location = new System.Drawing.Point(42, 33);
        this.groupBoxAnesthMedDelete.Name = "groupBoxAnesthMedDelete";
        this.groupBoxAnesthMedDelete.Size = new System.Drawing.Size(293, 118);
        this.groupBoxAnesthMedDelete.TabIndex = 139;
        this.groupBoxAnesthMedDelete.TabStop = false;
        this.groupBoxAnesthMedDelete.Text = "Anesthetic Medication";
        this.groupBoxAnesthMedDelete.Enter += new System.EventHandler(this.groupBoxAnesthMedDelete_Enter);
        //
        // labelQtyWasted
        //
        this.labelQtyWasted.AutoSize = true;
        this.labelQtyWasted.Location = new System.Drawing.Point(197, 53);
        this.labelQtyWasted.Name = "labelQtyWasted";
        this.labelQtyWasted.Size = new System.Drawing.Size(83, 13);
        this.labelQtyWasted.TabIndex = 143;
        this.labelQtyWasted.Text = "Qty wasted (mL)";
        //
        // textQtyWasted
        //
        this.textQtyWasted.Location = new System.Drawing.Point(203, 72);
        this.textQtyWasted.Name = "textQtyWasted";
        this.textQtyWasted.Size = new System.Drawing.Size(65, 20);
        this.textQtyWasted.TabIndex = 1;
        //
        // labelDoseTimeStamp
        //
        this.labelDoseTimeStamp.AutoSize = true;
        this.labelDoseTimeStamp.Location = new System.Drawing.Point(12, 53);
        this.labelDoseTimeStamp.Name = "labelDoseTimeStamp";
        this.labelDoseTimeStamp.Size = new System.Drawing.Size(63, 13);
        this.labelDoseTimeStamp.TabIndex = 141;
        this.labelDoseTimeStamp.Text = "Time Stamp";
        //
        // textAnesthMedName
        //
        this.textAnesthMedName.Location = new System.Drawing.Point(15, 20);
        this.textAnesthMedName.Name = "textAnesthMedName";
        this.textAnesthMedName.ReadOnly = true;
        this.textAnesthMedName.Size = new System.Drawing.Size(137, 20);
        this.textAnesthMedName.TabIndex = 5;
        //
        // textDose
        //
        this.textDose.Location = new System.Drawing.Point(203, 20);
        this.textDose.Name = "textDose";
        this.textDose.Size = new System.Drawing.Size(65, 20);
        this.textDose.TabIndex = 0;
        this.textDose.TextChanged += new System.EventHandler(this.textDose_TextChanged);
        //
        // labelDose
        //
        this.labelDose.AutoSize = true;
        this.labelDose.Location = new System.Drawing.Point(208, 0);
        this.labelDose.Name = "labelDose";
        this.labelDose.Size = new System.Drawing.Size(55, 13);
        this.labelDose.TabIndex = 4;
        this.labelDose.Text = "Dose (mL)";
        //
        // labelDeleteInstrux
        //
        this.labelDeleteInstrux.AutoSize = true;
        this.labelDeleteInstrux.Location = new System.Drawing.Point(16, 17);
        this.labelDeleteInstrux.Name = "labelDeleteInstrux";
        this.labelDeleteInstrux.Size = new System.Drawing.Size(362, 13);
        this.labelDeleteInstrux.TabIndex = 141;
        this.labelDeleteInstrux.Text = "Click \'Delete\' to delete this dose, or enter a quantity to waste and click \'OK\' ";
        //
        // butDelAnesthMeds
        //
        this.butDelAnesthMeds.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelAnesthMeds.setAutosize(true);
        this.butDelAnesthMeds.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelAnesthMeds.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelAnesthMeds.setCornerRadius(4F);
        this.butDelAnesthMeds.Image = Resources.getdeleteX();
        this.butDelAnesthMeds.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelAnesthMeds.Location = new System.Drawing.Point(47, 184);
        this.butDelAnesthMeds.Name = "butDelAnesthMeds";
        this.butDelAnesthMeds.Size = new System.Drawing.Size(82, 26);
        this.butDelAnesthMeds.TabIndex = 2;
        this.butDelAnesthMeds.Text = "Delete";
        this.butDelAnesthMeds.UseVisualStyleBackColor = true;
        this.butDelAnesthMeds.Click += new System.EventHandler(this.butDelAnesthMeds_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butOK.Location = new System.Drawing.Point(284, 184);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(72, 26);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butClose_Click);
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
        this.butCancel.Location = new System.Drawing.Point(209, 184);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(66, 26);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // groupDelMed
        //
        this.groupDelMed.Controls.Add(this.groupBoxAnesthMedDelete);
        this.groupDelMed.Location = new System.Drawing.Point(13, 17);
        this.groupDelMed.Name = "groupDelMed";
        this.groupDelMed.Size = new System.Drawing.Size(378, 209);
        this.groupDelMed.TabIndex = 142;
        this.groupDelMed.TabStop = false;
        this.groupDelMed.Text = "Click \'Delete\' to delete this dose, or enter a quantity to waste and click \'OK\' ";
        //
        // FormAnesthMedDelDose
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(423, 252);
        this.Controls.Add(this.labelDeleteInstrux);
        this.Controls.Add(this.butDelAnesthMeds);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.groupDelMed);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnesthMedDelDose";
        this.Text = "Delete/Waste Anesthetic Medication Dose";
        this.Load += new System.EventHandler(this.FormAnesthMedDelDose_Load);
        this.groupBoxAnesthMedDelete.ResumeLayout(false);
        this.groupBoxAnesthMedDelete.PerformLayout();
        this.groupDelMed.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textDoseTimeStamp = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBoxAnesthMedDelete = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textDose = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDose = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDoseTimeStamp = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAnesthMedName = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelAnesthMeds;
    private System.Windows.Forms.Label labelDeleteInstrux = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textQtyWasted = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelQtyWasted = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupDelMed = new System.Windows.Forms.GroupBox();
}


