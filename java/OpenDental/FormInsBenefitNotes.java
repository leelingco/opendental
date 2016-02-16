//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:14 PM
//

package OpenDental;

import OpenDental.FormInsBenefitNotes;
import OpenDental.Lan;

/**
* Summary description for FormBasicTemplate.
*/
public class FormInsBenefitNotes  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textBenefitNotes = new System.Windows.Forms.TextBox();
    /**
    * 
    */
    public String BenefitNotes = new String();
    /**
    * 
    */
    public FormInsBenefitNotes() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsBenefitNotes.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textBenefitNotes = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(474, 619);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(474, 578);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textBenefitNotes
        //
        this.textBenefitNotes.AcceptsReturn = true;
        this.textBenefitNotes.AcceptsTab = true;
        this.textBenefitNotes.Location = new System.Drawing.Point(10, 12);
        this.textBenefitNotes.Multiline = true;
        this.textBenefitNotes.Name = "textBenefitNotes";
        this.textBenefitNotes.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.textBenefitNotes.Size = new System.Drawing.Size(450, 632);
        this.textBenefitNotes.TabIndex = 2;
        //
        // FormInsBenefitNotes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(561, 660);
        this.Controls.Add(this.textBenefitNotes);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsBenefitNotes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Benefit Notes";
        this.Load += new System.EventHandler(this.FormInsBenefitNotes_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsBenefitNotes_Load(Object sender, System.EventArgs e) throws Exception {
        textBenefitNotes.Text = BenefitNotes;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        BenefitNotes = textBenefitNotes.Text;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


