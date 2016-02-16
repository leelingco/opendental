//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Schedules;

public class FormBlockoutDuplicatesFix  extends Form 
{

    public FormBlockoutDuplicatesFix() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formBlockoutDuplicatesFix_Load(Object sender, EventArgs e) throws Exception {
        fillLabels();
    }

    private void fillLabels() throws Exception {
        labelCount.Text = Schedules.getDuplicateBlockoutCount().ToString();
        if (StringSupport.equals(labelCount.Text, "0"))
        {
            labelInstructions.Text = "";
        }
        else
        {
            labelInstructions.Text = Lan.g(this,"Click the Clear button to fix the duplicates.");
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(labelCount.Text, "0"))
        {
            MsgBox.show(this,"There are no duplicates to clear.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Clear all duplicates?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        Schedules.clearDuplicates();
        Cursor = Cursors.Default;
        MsgBox.show(this,"Done.");
        fillLabels();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.label1 = new System.Windows.Forms.Label();
        this.labelCount = new System.Windows.Forms.Label();
        this.labelInstructions = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 45);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(175, 20);
        this.label1.TabIndex = 4;
        this.label1.Text = "Duplicate blockouts found:";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCount
        //
        this.labelCount.Location = new System.Drawing.Point(186, 45);
        this.labelCount.Name = "labelCount";
        this.labelCount.Size = new System.Drawing.Size(76, 20);
        this.labelCount.TabIndex = 5;
        this.labelCount.Text = "100000";
        this.labelCount.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelInstructions
        //
        this.labelInstructions.Location = new System.Drawing.Point(48, 82);
        this.labelInstructions.Name = "labelInstructions";
        this.labelInstructions.Size = new System.Drawing.Size(325, 41);
        this.labelInstructions.TabIndex = 6;
        this.labelInstructions.Text = "Click the Clear button to fix the duplicates.  You should not have to run this to" + "ol again in the future.";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(48, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(380, 20);
        this.label4.TabIndex = 7;
        this.label4.Text = "Duplicate blockouts can cause slowness in the Appointment module.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(255, 149);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "Clear";
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
        this.butCancel.Location = new System.Drawing.Point(354, 149);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormBlockoutDuplicatesFix
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(454, 196);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.labelInstructions);
        this.Controls.Add(this.labelCount);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormBlockoutDuplicatesFix";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clear Duplicate Blockouts";
        this.Load += new System.EventHandler(this.FormBlockoutDuplicatesFix_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCount = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelInstructions = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
}


