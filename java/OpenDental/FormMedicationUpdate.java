//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;

public class FormMedicationUpdate  extends Form 
{

    public FormMedicationUpdate() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.radioYes = new System.Windows.Forms.RadioButton();
        this.radioButton1 = new System.Windows.Forms.RadioButton();
        this.radioButton2 = new System.Windows.Forms.RadioButton();
        this.radioButton3 = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(333, 176);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(333, 217);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(43, 26);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(203, 20);
        this.label2.TabIndex = 78;
        this.label2.Text = "Were all medications documented?";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // radioYes
        //
        this.radioYes.Location = new System.Drawing.Point(46, 62);
        this.radioYes.Name = "radioYes";
        this.radioYes.Size = new System.Drawing.Size(137, 18);
        this.radioYes.TabIndex = 79;
        this.radioYes.Text = "Yes";
        this.radioYes.UseVisualStyleBackColor = true;
        //
        // radioButton1
        //
        this.radioButton1.Location = new System.Drawing.Point(46, 86);
        this.radioButton1.Name = "radioButton1";
        this.radioButton1.Size = new System.Drawing.Size(183, 18);
        this.radioButton1.TabIndex = 80;
        this.radioButton1.Text = "No: reason1 (SNOMED) why not";
        this.radioButton1.UseVisualStyleBackColor = true;
        //
        // radioButton2
        //
        this.radioButton2.Location = new System.Drawing.Point(46, 110);
        this.radioButton2.Name = "radioButton2";
        this.radioButton2.Size = new System.Drawing.Size(183, 18);
        this.radioButton2.TabIndex = 81;
        this.radioButton2.Text = "No: reason2 (SNOMED) why not";
        this.radioButton2.UseVisualStyleBackColor = true;
        //
        // radioButton3
        //
        this.radioButton3.Location = new System.Drawing.Point(46, 134);
        this.radioButton3.Name = "radioButton3";
        this.radioButton3.Size = new System.Drawing.Size(183, 18);
        this.radioButton3.TabIndex = 82;
        this.radioButton3.Text = "No: reason3 (SNOMED) why not";
        this.radioButton3.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(43, 166);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(244, 65);
        this.label1.TabIndex = 83;
        this.label1.Text = "Explain in this window how to delete or edit the two different types of entries i" + "n this label";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormMedicationUpdate
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(433, 268);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.radioButton3);
        this.Controls.Add(this.radioButton2);
        this.Controls.Add(this.radioButton1);
        this.Controls.Add(this.radioYes);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormMedicationUpdate";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Medication Update";
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.RadioButton radioYes = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButton1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButton2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButton3 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


