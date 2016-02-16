//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.Lan;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;

public class FormWikiSetup  extends Form 
{

    public FormWikiSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiSetup_Load(Object sender, EventArgs e) throws Exception {
        textMaster.Text = WikiPages.getMasterPage().PageContent;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        WikiPage masterPage = WikiPages.getMasterPage();
        masterPage.PageContent = textMaster.Text;
        masterPage.UserNum = Security.getCurUser().UserNum;
        WikiPages.insertAndArchive(masterPage);
        DataValid.setInvalid(InvalidType.Wiki);
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
        this.textMaster = new System.Windows.Forms.TextBox();
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
        this.butOK.Location = new System.Drawing.Point(776, 717);
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
        this.butCancel.Location = new System.Drawing.Point(857, 717);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textMaster
        //
        this.textMaster.AcceptsTab = true;
        this.textMaster.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textMaster.Font = new System.Drawing.Font("Courier New", 9.5F);
        this.textMaster.Location = new System.Drawing.Point(12, 28);
        this.textMaster.Multiline = true;
        this.textMaster.Name = "textMaster";
        this.textMaster.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textMaster.Size = new System.Drawing.Size(920, 683);
        this.textMaster.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 16);
        this.label1.TabIndex = 59;
        this.label1.Text = "Master Page:";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormWikiSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(944, 753);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textMaster);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Wiki Setup";
        this.Load += new System.EventHandler(this.FormWikiSetup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textMaster = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


