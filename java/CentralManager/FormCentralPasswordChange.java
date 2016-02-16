//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package CentralManager;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Userods;

public class FormCentralPasswordChange  extends Form 
{

    public FormCentralPasswordChange() throws Exception {
        initializeComponent();
    }

    private void formCentralPasswordChange_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textAccessCode.Text, "I'm admin"))
        {
            MessageBox.Show("Access code is incorrect.");
            return ;
        }
         
        Prefs.UpdateString(PrefName.CentralManagerPassHash, Userods.EncryptPassword(textPassword.Text));
        Prefs.refreshCache();
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
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textAccessCode = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(280, 177);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(361, 177);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(132, 112);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(190, 20);
        this.textPassword.TabIndex = 0;
        //
        // label4
        //
        this.label4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label4.Location = new System.Drawing.Point(18, 115);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 17);
        this.label4.TabIndex = 207;
        this.label4.Text = "New Password";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label1.Location = new System.Drawing.Point(30, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(380, 44);
        this.label1.TabIndex = 208;
        this.label1.Text = "Only the administrator should change the password.  If you are not the administra" + "tor, click Cancel.  If you are the administrator, you will need the access code " + "that\'s posted on our website.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(18, 86);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 17);
        this.label2.TabIndex = 210;
        this.label2.Text = "Access Code";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAccessCode
        //
        this.textAccessCode.Location = new System.Drawing.Point(132, 83);
        this.textAccessCode.Name = "textAccessCode";
        this.textAccessCode.Size = new System.Drawing.Size(190, 20);
        this.textAccessCode.TabIndex = 209;
        //
        // FormCentralPasswordChange
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(448, 213);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textAccessCode);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCentralPasswordChange";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Change Password";
        this.Load += new System.EventHandler(this.FormCentralPasswordChange_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAccessCode = new System.Windows.Forms.TextBox();
}


