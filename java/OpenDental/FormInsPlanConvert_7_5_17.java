//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.YN;
import OpenDental.FormInsPlanConvert_7_5_17;

public class FormInsPlanConvert_7_5_17  extends Form 
{

    public YN InsPlanConverstion_7_5_17_AutoMergeYN = YN.Unknown;
    public FormInsPlanConvert_7_5_17() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formInsPlanConvert_7_5_17_Load(Object sender, EventArgs e) throws Exception {
        if (InsPlanConverstion_7_5_17_AutoMergeYN == YN.Yes)
        {
            radioMergeY.Checked = true;
        }
         
        if (InsPlanConverstion_7_5_17_AutoMergeYN == YN.No)
        {
            radioMergeN.Checked = true;
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!radioMergeY.Checked && !radioMergeN.Checked)
        {
            MessageBox.Show("One of the options must be selected before clicking OK.");
            return ;
        }
         
        if (radioMergeY.Checked)
        {
            InsPlanConverstion_7_5_17_AutoMergeYN = YN.Yes;
        }
         
        if (radioMergeN.Checked)
        {
            InsPlanConverstion_7_5_17_AutoMergeYN = YN.No;
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsPlanConvert_7_5_17.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.radioMergeY = new System.Windows.Forms.RadioButton();
        this.radioMergeN = new System.Windows.Forms.RadioButton();
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
        this.butOK.Location = new System.Drawing.Point(375, 271);
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
        this.butCancel.Location = new System.Drawing.Point(469, 271);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(545, 157);
        this.label1.TabIndex = 4;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // radioMergeY
        //
        this.radioMergeY.Location = new System.Drawing.Point(42, 169);
        this.radioMergeY.Name = "radioMergeY";
        this.radioMergeY.Size = new System.Drawing.Size(408, 24);
        this.radioMergeY.TabIndex = 5;
        this.radioMergeY.TabStop = true;
        this.radioMergeY.Text = "Combine identical plans. I will fix benefit information as needed.";
        this.radioMergeY.UseVisualStyleBackColor = true;
        //
        // radioMergeN
        //
        this.radioMergeN.Location = new System.Drawing.Point(42, 206);
        this.radioMergeN.Name = "radioMergeN";
        this.radioMergeN.Size = new System.Drawing.Size(408, 24);
        this.radioMergeN.TabIndex = 6;
        this.radioMergeN.TabStop = true;
        this.radioMergeN.Text = "Maintain separate plans.  I will combine them later as needed.";
        this.radioMergeN.UseVisualStyleBackColor = true;
        //
        // FormInsPlanConvert_7_5_17
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(569, 318);
        this.Controls.Add(this.radioMergeN);
        this.Controls.Add(this.radioMergeY);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormInsPlanConvert_7_5_17";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Ins Plan Conversion Strategy";
        this.Load += new System.EventHandler(this.FormInsPlanConvert_7_5_17_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.RadioButton radioMergeY = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioMergeN = new System.Windows.Forms.RadioButton();
}


