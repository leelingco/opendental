//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package CentralManager;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Userods;

public class FormCentralPasswordCheck  extends Form 
{

    public FormCentralPasswordCheck() throws Exception {
        initializeComponent();
    }

    private void formCentralPasswordCheck_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(PrefC.getString(PrefName.CentralManagerPassHash), Userods.EncryptPassword(textPassword.Text)))
        {
            MessageBox.Show("Bad password.");
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
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(202, 68);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(283, 68);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(126, 26);
        this.textPassword.Name = "textPassword";
        this.textPassword.PasswordChar = '*';
        this.textPassword.Size = new System.Drawing.Size(190, 20);
        this.textPassword.TabIndex = 0;
        //
        // label4
        //
        this.label4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label4.Location = new System.Drawing.Point(12, 29);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 17);
        this.label4.TabIndex = 207;
        this.label4.Text = "Enter Password";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormCentralPasswordCheck
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(370, 104);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCentralPasswordCheck";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Central Manager";
        this.Load += new System.EventHandler(this.FormCentralPasswordCheck_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
}


