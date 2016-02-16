//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;

public class FormSheetOutputFormat  extends Form 
{

    public int PaperCopies = new int();
    public boolean EmailPatOrLab = new boolean();
    public String EmailPatOrLabAddress = new String();
    public boolean Email2 = new boolean();
    public String Email2Address = new String();
    public boolean Email2Visible = new boolean();
    public boolean IsForLab = new boolean();
    public FormSheetOutputFormat() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetOutputFormat_Load(Object sender, EventArgs e) throws Exception {
        textPaperCopies.Text = PaperCopies.ToString();
        checkEmailPat.Checked = EmailPatOrLab;
        if (IsForLab)
        {
            checkEmailPat.Text = Lan.g(this,"E-mail to Lab:");
        }
         
        textEmailPat.Text = EmailPatOrLabAddress;
        if (Email2Visible)
        {
            checkEmail2.Checked = Email2;
            textEmail2.Text = Email2Address;
        }
        else
        {
            checkEmail2.Visible = false;
            textEmail2.Visible = false;
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textPaperCopies.errorProvider1.GetError(textPaperCopies), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (checkEmailPat.Checked && StringSupport.equals(textEmailPat.Text, ""))
        {
            MsgBox.show(this,"Please enter an email address or uncheck the email box.");
            return ;
        }
         
        if (Email2Visible)
        {
            if (checkEmail2.Checked && StringSupport.equals(textEmail2.Text, ""))
            {
                MsgBox.show(this,"Please enter an email address or uncheck the email box.");
                return ;
            }
             
        }
         
        if (PIn.Long(textPaperCopies.Text) == 0 && !checkEmailPat.Checked && !checkEmail2.Checked)
        {
            MsgBox.show(this,"There are no output methods selected.");
            return ;
        }
         
        PaperCopies = PIn.Int(textPaperCopies.Text);
        EmailPatOrLab = checkEmailPat.Checked;
        EmailPatOrLabAddress = textEmailPat.Text;
        if (Email2Visible)
        {
            Email2 = checkEmail2.Checked;
            Email2Address = textEmail2.Text;
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
        this.label1 = new System.Windows.Forms.Label();
        this.checkEmailPat = new System.Windows.Forms.CheckBox();
        this.textEmailPat = new System.Windows.Forms.TextBox();
        this.textEmail2 = new System.Windows.Forms.TextBox();
        this.checkEmail2 = new System.Windows.Forms.CheckBox();
        this.textPaperCopies = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(78, 25);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(134, 16);
        this.label1.TabIndex = 83;
        this.label1.Text = "Paper copies";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkEmailPat
        //
        this.checkEmailPat.Location = new System.Drawing.Point(62, 65);
        this.checkEmailPat.Name = "checkEmailPat";
        this.checkEmailPat.Size = new System.Drawing.Size(314, 20);
        this.checkEmailPat.TabIndex = 85;
        this.checkEmailPat.Text = "E-mail to patient (not recommended for sensitive information):";
        this.checkEmailPat.UseVisualStyleBackColor = true;
        //
        // textEmailPat
        //
        this.textEmailPat.Location = new System.Drawing.Point(81, 84);
        this.textEmailPat.Name = "textEmailPat";
        this.textEmailPat.Size = new System.Drawing.Size(295, 20);
        this.textEmailPat.TabIndex = 86;
        //
        // textEmail2
        //
        this.textEmail2.Location = new System.Drawing.Point(81, 140);
        this.textEmail2.Name = "textEmail2";
        this.textEmail2.Size = new System.Drawing.Size(295, 20);
        this.textEmail2.TabIndex = 88;
        //
        // checkEmail2
        //
        this.checkEmail2.Location = new System.Drawing.Point(62, 121);
        this.checkEmail2.Name = "checkEmail2";
        this.checkEmail2.Size = new System.Drawing.Size(314, 20);
        this.checkEmail2.TabIndex = 87;
        this.checkEmail2.Text = "E-mail to referral (not recommended for sensitive information):";
        this.checkEmail2.UseVisualStyleBackColor = true;
        //
        // textPaperCopies
        //
        this.textPaperCopies.Location = new System.Drawing.Point(30, 24);
        this.textPaperCopies.setMaxVal(255);
        this.textPaperCopies.setMinVal(0);
        this.textPaperCopies.Name = "textPaperCopies";
        this.textPaperCopies.Size = new System.Drawing.Size(45, 20);
        this.textPaperCopies.TabIndex = 84;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(231, 196);
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
        this.butCancel.Location = new System.Drawing.Point(343, 196);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetOutputFormat
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(443, 243);
        this.Controls.Add(this.textEmail2);
        this.Controls.Add(this.checkEmail2);
        this.Controls.Add(this.textEmailPat);
        this.Controls.Add(this.checkEmailPat);
        this.Controls.Add(this.textPaperCopies);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetOutputFormat";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Sheet Output";
        this.Load += new System.EventHandler(this.FormSheetOutputFormat_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textPaperCopies;
    private System.Windows.Forms.CheckBox checkEmailPat = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textEmailPat = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textEmail2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkEmail2 = new System.Windows.Forms.CheckBox();
}


