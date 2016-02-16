//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.FormPaymentPlanOptions;

public class FormPaymentPlanOptions  extends Form 
{

    public FormPaymentPlanOptions() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPaymentPlanOptions.class);
        this.butClose = new OpenDental.UI.Button();
        this.label8 = new System.Windows.Forms.Label();
        this.radioMonthly = new System.Windows.Forms.RadioButton();
        this.radioQuarterly = new System.Windows.Forms.RadioButton();
        this.radioWeekly = new System.Windows.Forms.RadioButton();
        this.radioEveryOtherWeek = new System.Windows.Forms.RadioButton();
        this.radioOrdinalWeekday = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(365, 274);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label8
        //
        this.label8.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.label8.Location = new System.Drawing.Point(39, 10);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(391, 62);
        this.label8.TabIndex = 20;
        this.label8.Text = resources.GetString("label8.Text");
        //
        // radioMonthly
        //
        this.radioMonthly.Checked = true;
        this.radioMonthly.Location = new System.Drawing.Point(42, 140);
        this.radioMonthly.Name = "radioMonthly";
        this.radioMonthly.Size = new System.Drawing.Size(104, 18);
        this.radioMonthly.TabIndex = 21;
        this.radioMonthly.TabStop = true;
        this.radioMonthly.Text = "Monthly";
        this.radioMonthly.UseVisualStyleBackColor = true;
        //
        // radioQuarterly
        //
        this.radioQuarterly.Location = new System.Drawing.Point(42, 160);
        this.radioQuarterly.Name = "radioQuarterly";
        this.radioQuarterly.Size = new System.Drawing.Size(104, 18);
        this.radioQuarterly.TabIndex = 22;
        this.radioQuarterly.TabStop = true;
        this.radioQuarterly.Text = "Quarterly";
        this.radioQuarterly.UseVisualStyleBackColor = true;
        //
        // radioWeekly
        //
        this.radioWeekly.Location = new System.Drawing.Point(42, 78);
        this.radioWeekly.Name = "radioWeekly";
        this.radioWeekly.Size = new System.Drawing.Size(104, 18);
        this.radioWeekly.TabIndex = 23;
        this.radioWeekly.TabStop = true;
        this.radioWeekly.Text = "Weekly";
        this.radioWeekly.UseVisualStyleBackColor = true;
        //
        // radioEveryOtherWeek
        //
        this.radioEveryOtherWeek.Location = new System.Drawing.Point(42, 99);
        this.radioEveryOtherWeek.Name = "radioEveryOtherWeek";
        this.radioEveryOtherWeek.Size = new System.Drawing.Size(156, 18);
        this.radioEveryOtherWeek.TabIndex = 24;
        this.radioEveryOtherWeek.TabStop = true;
        this.radioEveryOtherWeek.Text = "Every other week";
        this.radioEveryOtherWeek.UseVisualStyleBackColor = true;
        //
        // radioOrdinalWeekday
        //
        this.radioOrdinalWeekday.Location = new System.Drawing.Point(42, 119);
        this.radioOrdinalWeekday.Name = "radioOrdinalWeekday";
        this.radioOrdinalWeekday.Size = new System.Drawing.Size(264, 18);
        this.radioOrdinalWeekday.TabIndex = 25;
        this.radioOrdinalWeekday.TabStop = true;
        this.radioOrdinalWeekday.Text = "First/second/etc Mon/Tue/etc of month";
        this.radioOrdinalWeekday.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(39, 192);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(391, 66);
        this.label1.TabIndex = 26;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // FormPaymentPlanOptions
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(455, 312);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.radioOrdinalWeekday);
        this.Controls.Add(this.radioEveryOtherWeek);
        this.Controls.Add(this.radioWeekly);
        this.Controls.Add(this.radioQuarterly);
        this.Controls.Add(this.radioMonthly);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.butClose);
        this.Name = "FormPaymentPlanOptions";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payment Plan Options";
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    public System.Windows.Forms.RadioButton radioMonthly = new System.Windows.Forms.RadioButton();
    public System.Windows.Forms.RadioButton radioQuarterly = new System.Windows.Forms.RadioButton();
    public System.Windows.Forms.RadioButton radioWeekly = new System.Windows.Forms.RadioButton();
    public System.Windows.Forms.RadioButton radioEveryOtherWeek = new System.Windows.Forms.RadioButton();
    public System.Windows.Forms.RadioButton radioOrdinalWeekday = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


