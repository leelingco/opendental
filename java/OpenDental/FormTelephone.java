//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormTelephone;
import OpenDental.Lan;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patients;

/**
* 
*/
public class FormTelephone  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butReformat;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormTelephone() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTelephone.class);
        this.butClose = new OpenDental.UI.Button();
        this.butReformat = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(509, 266);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butReformat
        //
        this.butReformat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReformat.setAutosize(true);
        this.butReformat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReformat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReformat.setCornerRadius(4F);
        this.butReformat.Location = new System.Drawing.Point(17, 31);
        this.butReformat.Name = "butReformat";
        this.butReformat.Size = new System.Drawing.Size(108, 26);
        this.butReformat.TabIndex = 1;
        this.butReformat.Text = "&Reformat";
        this.butReformat.Click += new System.EventHandler(this.butReformat_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(137, 33);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(478, 57);
        this.label1.TabIndex = 2;
        this.label1.Text = "Reformat all phone numbers in the database to (###)###-####.  Only certain matche" + "s will be reformatted.  No numbers will be lost, and no trailing comments will b" + "e affected.";
        //
        // FormTelephone
        //
        this.AcceptButton = this.butClose;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(642, 313);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butReformat);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTelephone";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Telephone Tools";
        this.Load += new System.EventHandler(this.FormTelephone_Load);
        this.ResumeLayout(false);
    }

    private void formTelephone_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void butReformat_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            if (MessageBox.Show(Lan.g(this,"Are you sure?  The phone number formatting is only meant for the United States?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        Patients.reformatAllPhoneNumbers();
        //refresh carriers:
        DataValid.setInvalid(InvalidType.Carriers);
        MessageBox.Show(Lan.g(this,"Telephone numbers reformatted."));
    }

}


