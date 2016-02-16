//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;

public class FormWikiRename  extends Form 
{

    public String PageTitle = new String();
    public FormWikiRename() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiRename_Load(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(PageTitle, "") && PageTitle != null)
        {
            textPageTitle.Text = PageTitle;
            Text = "Page Title - " + PageTitle;
            textPageTitle.Text = PageTitle;
        }
         
        if (StringSupport.equals(textPageTitle.Text, "Home"))
        {
            //TODO later:replace this with a dynamic "Home" pagename lookup like: PrefC.GetString(PrefName.WikiHomePage);
            MsgBox.show(this,"Cannot rename the default homepage.");
            //butOK.Enabled=false;
            //textPageTitle.Enabled=false;
            DialogResult = DialogResult.Cancel;
        }
         
    }

    //user doesn't need to see this form
    private boolean validateTitle() throws Exception {
        if (StringSupport.equals(textPageTitle.Text, ""))
        {
            MsgBox.show(this,"Page title cannot be empty.");
            return false;
        }
         
        if (StringSupport.equals(textPageTitle.Text, PageTitle))
        {
            //"rename" to the same thing.
            DialogResult = DialogResult.Cancel;
            return false;
        }
         
        if (textPageTitle.Text.StartsWith("_"))
        {
            MsgBox.show(this,"Page title cannot start with the underscore character.");
            return false;
        }
         
        if (textPageTitle.Text.Contains("\""))
        {
            MsgBox.show(this,"Page title cannot contain double quotes.");
            return false;
        }
         
        if (textPageTitle.Text.Contains("\r") || textPageTitle.Text.Contains("\n"))
        {
            MsgBox.show(this,"Page title cannot contain carriage return.");
            return false;
        }
         
        //there is also no way to enter one.
        if (PageTitle != null && textPageTitle.Text.ToLower() == PageTitle.ToLower())
        {
            return true;
        }
         
        //the user is just trying to change the capitalization, which is allowed
        WikiPage wp = WikiPages.GetByTitle(textPageTitle.Text);
        //this is case insensitive, so it won't let you name it the same as another page even if capitalization is different.
        if (wp != null)
        {
            MsgBox.show(this,"Page title already exists.");
            return false;
        }
         
        return true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!validateTitle())
        {
            return ;
        }
         
        PageTitle = textPageTitle.Text;
        //do not save here. Save is handled where this form is called from.
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
        this.textPageTitle = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textPageTitle
        //
        this.textPageTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textPageTitle.Location = new System.Drawing.Point(106, 33);
        this.textPageTitle.MaxLength = 255;
        this.textPageTitle.Name = "textPageTitle";
        this.textPageTitle.Size = new System.Drawing.Size(229, 20);
        this.textPageTitle.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(1, 33);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(101, 20);
        this.label2.TabIndex = 77;
        this.label2.Text = "Page Title";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(201, 81);
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
        this.butCancel.Location = new System.Drawing.Point(282, 81);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormWikiRename
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(369, 117);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textPageTitle);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiRename";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Page Title";
        this.Load += new System.EventHandler(this.FormWikiRename_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textPageTitle = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


