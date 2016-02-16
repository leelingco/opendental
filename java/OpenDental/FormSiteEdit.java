//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:50 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSiteEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Site;
import OpenDentBusiness.Sites;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSiteEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    //<summary></summary>
    //public bool IsNew;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    public Site SiteCur;
    /**
    * 
    */
    public FormSiteEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSiteEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(478, 225);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
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
        this.butOK.Location = new System.Drawing.Point(387, 225);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 8;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(160, 20);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(241, 20);
        this.textDescription.TabIndex = 0;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(27, 225);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(160, 45);
        this.textNote.MaxLength = 255;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(334, 144);
        this.textNote.TabIndex = 2;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 47);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(151, 17);
        this.label3.TabIndex = 101;
        this.label3.Text = "Note";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormSiteEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(579, 269);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSiteEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Site";
        this.Load += new System.EventHandler(this.FormSiteEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSiteEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = SiteCur.Description;
        textNote.Text = SiteCur.Note;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (SiteCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete Site?"))
        {
            return ;
        }
         
        try
        {
            Sites.deleteObject(SiteCur.SiteNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Description cannot be blank."));
            return ;
        }
         
        SiteCur.Description = textDescription.Text;
        SiteCur.Note = textNote.Text;
        try
        {
            if (SiteCur.getIsNew())
            {
                Sites.insert(SiteCur);
            }
            else
            {
                Sites.update(SiteCur);
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


