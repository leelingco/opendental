//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRegistrationKey;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormRegistrationKey  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private TextBox textKey1 = new TextBox();
    private Label label2 = new Label();
    private CheckBox checkAgree = new CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private RichTextBox richTextAgreement = new RichTextBox();
    /**
    * 
    */
    public FormRegistrationKey() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRegistrationKey.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textKey1 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkAgree = new System.Windows.Forms.CheckBox();
        this.richTextAgreement = new System.Windows.Forms.RichTextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(164, 6);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(266, 19);
        this.label1.TabIndex = 2;
        this.label1.Text = "Registration Key";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textKey1
        //
        this.textKey1.Location = new System.Drawing.Point(164, 29);
        this.textKey1.Name = "textKey1";
        this.textKey1.Size = new System.Drawing.Size(243, 20);
        this.textKey1.TabIndex = 0;
        this.textKey1.WordWrap = false;
        this.textKey1.TextChanged += new System.EventHandler(this.textKey1_TextChanged);
        this.textKey1.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textKey1_KeyUp);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(26, 66);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(150, 13);
        this.label2.TabIndex = 6;
        this.label2.Text = "Licence Agreement";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkAgree
        //
        this.checkAgree.Location = new System.Drawing.Point(26, 559);
        this.checkAgree.Name = "checkAgree";
        this.checkAgree.Size = new System.Drawing.Size(373, 17);
        this.checkAgree.TabIndex = 7;
        this.checkAgree.Text = "I agree to the terms of the above license agreement in its entirety.";
        this.checkAgree.UseVisualStyleBackColor = true;
        this.checkAgree.CheckedChanged += new System.EventHandler(this.checkAgree_CheckedChanged);
        //
        // richTextAgreement
        //
        this.richTextAgreement.Location = new System.Drawing.Point(26, 82);
        this.richTextAgreement.Name = "richTextAgreement";
        this.richTextAgreement.Size = new System.Drawing.Size(675, 465);
        this.richTextAgreement.TabIndex = 8;
        this.richTextAgreement.Text = "";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Enabled = false;
        this.butOK.Location = new System.Drawing.Point(546, 557);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(627, 557);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormRegistrationKey
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(720, 597);
        this.Controls.Add(this.richTextAgreement);
        this.Controls.Add(this.checkAgree);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textKey1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRegistrationKey";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Registration Key";
        this.Load += new System.EventHandler(this.FormRegistrationKey_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRegistrationKey_Load(Object sender, EventArgs e) throws Exception {
        String key = PrefC.getString(PrefName.RegistrationKey);
        if (key != null && key.Length == 16)
        {
            key = key.Substring(0, 4) + "-" + key.Substring(4, 4) + "-" + key.Substring(8, 4) + "-" + key.Substring(12, 4);
        }
         
        textKey1.Text = key;
        richTextAgreement.Rtf = Resources.getCDT_Content_End_User_License();
        setOKEnabled();
    }

    /**
    * If using the foreign CDT.dll, it always returns true (valid), regardless of whether the box is blank or malformed.
    */
    public static boolean validateKey(String keystr) throws Exception {
        return CDT.Class1.ValidateKey(keystr);
    }

    private void textKey1_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        int cursor = textKey1.SelectionStart;
        textKey1.Text = textKey1.Text.ToUpper();
        int length = textKey1.Text.Length;
        if (Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{5}$"))
        {
            textKey1.Text = textKey1.Text.Substring(0, 4) + "-" + textKey1.Text.Substring(4);
        }
        else if (Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{4}-[A-Z0-9]{5}$"))
        {
            textKey1.Text = textKey1.Text.Substring(0, 9) + "-" + textKey1.Text.Substring(9);
        }
        else if (Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{5}$"))
        {
            textKey1.Text = textKey1.Text.Substring(0, 14) + "-" + textKey1.Text.Substring(14);
        }
           
        if (textKey1.Text.Length > length)
        {
            cursor++;
        }
         
        textKey1.SelectionStart = cursor;
    }

    private void textKey1_TextChanged(Object sender, EventArgs e) throws Exception {
        setOKEnabled();
    }

    private void checkAgree_CheckedChanged(Object sender, EventArgs e) throws Exception {
        setOKEnabled();
    }

    private void setOKEnabled() throws Exception {
        if (checkAgree.Checked || StringSupport.equals(textKey1.Text, ""))
        {
            butOK.Enabled = true;
        }
        else
        {
            butOK.Enabled = false;
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textKey1.Text, "") && !Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$") && !Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{16}$"))
        {
            MsgBox.show(this,"Invalid registration key format.");
            return ;
        }
         
        String regkey = "";
        if (Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$"))
        {
            regkey = textKey1.Text.Substring(0, 4) + textKey1.Text.Substring(5, 4) + textKey1.Text.Substring(10, 4) + textKey1.Text.Substring(15, 4);
        }
        else if (Regex.IsMatch(textKey1.Text, "^[A-Z0-9]{16}$"))
        {
            regkey = textKey1.Text;
        }
          
        if (!validateKey(regkey))
        {
            //a blank registration key will only be valid if the CDT.dll is foreign
            MsgBox.show(this,"Invalid registration key.");
            return ;
        }
         
        Prefs.updateString(PrefName.RegistrationKey,regkey);
        //prefs refresh automatically in the calling class anyway.
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


