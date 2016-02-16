//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;


/**
* Cannot return OK without a proper date.
*/
public class FormOrthoChartAddDate  extends Form 
{

    public DateTime SelectedDate = new DateTime();
    public FormOrthoChartAddDate() throws Exception {
        initializeComponent();
        SelectedDate = new DateTime();
        Lan.F(this);
    }

    private void formOrthoChartAddDate_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        RefSupport refVar___0 = new RefSupport();
        boolean boolVar___0 = !DateTime.TryParse(textNewDate.Text, refVar___0);
        SelectedDate = refVar___0.getValue();
        if (boolVar___0)
        {
            MsgBox.show(this,"Please fix date entry.");
            return ;
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textNewDate = new System.Windows.Forms.TextBox();
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
        this.butOK.Location = new System.Drawing.Point(159, 94);
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
        this.butCancel.Location = new System.Drawing.Point(240, 94);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textNewDate
        //
        this.textNewDate.Location = new System.Drawing.Point(114, 39);
        this.textNewDate.Name = "textNewDate";
        this.textNewDate.Size = new System.Drawing.Size(163, 20);
        this.textNewDate.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 40);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 5;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormOrthoChartAddDate
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(327, 130);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textNewDate);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormOrthoChartAddDate";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Add New Ortho Chart Date";
        this.Load += new System.EventHandler(this.FormOrthoChartAddDate_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textNewDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


