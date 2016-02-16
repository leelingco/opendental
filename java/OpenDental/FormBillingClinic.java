//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.Clinics;

public class FormBillingClinic  extends Form 
{

    /**
    * 0 - all clinics.
    */
    public long ClinicNum = new long();
    public FormBillingClinic() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formBillingClinic_Load(Object sender, EventArgs e) throws Exception {
        listClinic.Items.Add("All");
        for (int i = 0;i < Clinics.getList().Length;i++)
        {
            listClinic.Items.Add(Clinics.getList()[i].Description);
        }
        listClinic.SelectedIndex = 0;
    }

    private void listClinic_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listClinic.SelectedIndex < 0)
        {
            return ;
        }
         
        ClinicNum = 0;
        if (listClinic.SelectedIndex > 0)
        {
            ClinicNum = Clinics.getList()[listClinic.SelectedIndex - 1].ClinicNum;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        ClinicNum = 0;
        if (listClinic.SelectedIndex > 0)
        {
            ClinicNum = Clinics.getList()[listClinic.SelectedIndex - 1].ClinicNum;
        }
         
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
        this.labelClinic = new System.Windows.Forms.Label();
        this.listClinic = new System.Windows.Forms.ListBox();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(21, 9);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(179, 21);
        this.labelClinic.TabIndex = 253;
        this.labelClinic.Text = "Select a clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listClinic
        //
        this.listClinic.FormattingEnabled = true;
        this.listClinic.Location = new System.Drawing.Point(24, 34);
        this.listClinic.Name = "listClinic";
        this.listClinic.Size = new System.Drawing.Size(176, 264);
        this.listClinic.TabIndex = 254;
        this.listClinic.DoubleClick += new System.EventHandler(this.listClinic_DoubleClick);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(187, 321);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormBillingClinic
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(274, 357);
        this.Controls.Add(this.listClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.butOK);
        this.Name = "FormBillingClinic";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Billing Clinics";
        this.Load += new System.EventHandler(this.FormBillingClinic_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listClinic = new System.Windows.Forms.ListBox();
}


