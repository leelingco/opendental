//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:08 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEmailTemplateEdit;
import OpenDental.Lan;
import OpenDentBusiness.EmailTemplate;
import OpenDentBusiness.EmailTemplates;

/**
* Summary description for FormBasicTemplate.
*/
public class FormEmailTemplateEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubject = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textBodyText;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public EmailTemplate ETcur;
    /**
    * 
    */
    public FormEmailTemplateEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailTemplateEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.textSubject = new System.Windows.Forms.TextBox();
        this.textBodyText = new OpenDental.ODtextBox();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(755, 505);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 3;
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
        this.butOK.Location = new System.Drawing.Point(755, 472);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(7, 12);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(88, 14);
        this.label2.TabIndex = 3;
        this.label2.Text = "Subject:";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubject
        //
        this.textSubject.Location = new System.Drawing.Point(99, 7);
        this.textSubject.Name = "textSubject";
        this.textSubject.Size = new System.Drawing.Size(741, 20);
        this.textSubject.TabIndex = 0;
        //
        // textBodyText
        //
        this.textBodyText.Location = new System.Drawing.Point(99, 32);
        this.textBodyText.Multiline = true;
        this.textBodyText.Name = "textBodyText";
        this.textBodyText.setQuickPasteType(OpenDentBusiness.QuickPasteType.Email);
        this.textBodyText.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textBodyText.Size = new System.Drawing.Size(741, 426);
        this.textBodyText.TabIndex = 4;
        //
        // FormEmailTemplateEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(844, 544);
        this.Controls.Add(this.textBodyText);
        this.Controls.Add(this.textSubject);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmailTemplateEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit E-mail Template";
        this.Load += new System.EventHandler(this.FormEmailTemplateEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEmailTemplateEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textSubject.Text = ETcur.Subject;
        textBodyText.Text = ETcur.BodyText;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textSubject.Text, "") && StringSupport.equals(textBodyText.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Both can not be left blank."));
            return ;
        }
         
        ETcur.Subject = textSubject.Text;
        ETcur.BodyText = textBodyText.Text;
        if (IsNew)
        {
            EmailTemplates.insert(ETcur);
        }
        else
        {
            EmailTemplates.update(ETcur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


