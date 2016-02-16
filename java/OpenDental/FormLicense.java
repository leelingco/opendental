//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:19 PM
//

package OpenDental;

import OpenDental.FormLicense;
import OpenDental.Lan;
import OpenDental.Properties.Resources;

/**
* 
*/
public class FormLicense  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private RichTextBox richTextAgreement = new RichTextBox();
    private RichTextBox textGPL = new RichTextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormLicense() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLicense.class);
        this.butClose = new OpenDental.UI.Button();
        this.richTextAgreement = new System.Windows.Forms.RichTextBox();
        this.textGPL = new System.Windows.Forms.RichTextBox();
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
        this.butClose.Location = new System.Drawing.Point(764, 618);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // richTextAgreement
        //
        this.richTextAgreement.Location = new System.Drawing.Point(12, 5);
        this.richTextAgreement.Name = "richTextAgreement";
        this.richTextAgreement.Size = new System.Drawing.Size(827, 300);
        this.richTextAgreement.TabIndex = 9;
        this.richTextAgreement.Text = "";
        //
        // textGPL
        //
        this.textGPL.Location = new System.Drawing.Point(12, 311);
        this.textGPL.Name = "textGPL";
        this.textGPL.Size = new System.Drawing.Size(827, 300);
        this.textGPL.TabIndex = 10;
        this.textGPL.Text = "";
        //
        // FormLicense
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(851, 656);
        this.Controls.Add(this.textGPL);
        this.Controls.Add(this.richTextAgreement);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLicense";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Licenses";
        this.Load += new System.EventHandler(this.FormLicense_Load);
        this.ResumeLayout(false);
    }

    private void formLicense_Load(Object sender, EventArgs e) throws Exception {
        richTextAgreement.Rtf = Resources.getCDT_Content_End_User_License();
        textGPL.Text = Resources.getGPL();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

}


