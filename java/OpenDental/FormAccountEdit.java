//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAccountEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Account;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAccountEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label3 = new Label();
    private OpenDental.UI.Button butDelete;
    private TextBox textDescription = new TextBox();
    private TextBox textBankNumber = new TextBox();
    private ListBox listAcctType = new ListBox();
    private CheckBox checkInactive = new CheckBox();
    private Button butColor = new Button();
    private Label label4 = new Label();
    private Account AccountCur;
    /**
    * 
    */
    public FormAccountEdit(Account accountCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        AccountCur = accountCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAccountEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textBankNumber = new System.Windows.Forms.TextBox();
        this.listAcctType = new System.Windows.Forms.ListBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkInactive = new System.Windows.Forms.CheckBox();
        this.butColor = new System.Windows.Forms.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(102, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 20);
        this.label1.TabIndex = 11;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(102, 49);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 20);
        this.label2.TabIndex = 13;
        this.label2.Text = "Type";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 124);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(198, 21);
        this.label3.TabIndex = 15;
        this.label3.Text = "Bank Number (for deposit slips)";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(206, 24);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(282, 20);
        this.textDescription.TabIndex = 17;
        //
        // textBankNumber
        //
        this.textBankNumber.Location = new System.Drawing.Point(206, 125);
        this.textBankNumber.Name = "textBankNumber";
        this.textBankNumber.Size = new System.Drawing.Size(282, 20);
        this.textBankNumber.TabIndex = 18;
        //
        // listAcctType
        //
        this.listAcctType.FormattingEnabled = true;
        this.listAcctType.Location = new System.Drawing.Point(206, 50);
        this.listAcctType.Name = "listAcctType";
        this.listAcctType.Size = new System.Drawing.Size(120, 69);
        this.listAcctType.TabIndex = 19;
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
        this.butDelete.Location = new System.Drawing.Point(28, 244);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 26);
        this.butDelete.TabIndex = 16;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(488, 212);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 8;
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
        this.butCancel.Location = new System.Drawing.Point(488, 244);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkInactive
        //
        this.checkInactive.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkInactive.Location = new System.Drawing.Point(28, 151);
        this.checkInactive.Name = "checkInactive";
        this.checkInactive.Size = new System.Drawing.Size(192, 20);
        this.checkInactive.TabIndex = 20;
        this.checkInactive.Text = "Inactive";
        this.checkInactive.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkInactive.UseVisualStyleBackColor = true;
        //
        // butColor
        //
        this.butColor.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColor.Location = new System.Drawing.Point(206, 176);
        this.butColor.Name = "butColor";
        this.butColor.Size = new System.Drawing.Size(30, 20);
        this.butColor.TabIndex = 21;
        this.butColor.Click += new System.EventHandler(this.butColor_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(96, 175);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(106, 21);
        this.label4.TabIndex = 22;
        this.label4.Text = "Row Color";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormAccountEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(589, 288);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butColor);
        this.Controls.Add(this.checkInactive);
        this.Controls.Add(this.listAcctType);
        this.Controls.Add(this.textBankNumber);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAccountEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Account";
        this.Load += new System.EventHandler(this.FormAccountEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAccountEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = AccountCur.Description;
        for (int i = 0;i < Enum.GetNames(AccountType.class).Length;i++)
        {
            listAcctType.Items.Add(Lan.g("enumAccountType", Enum.GetNames(AccountType.class)[i]));
            if (((Enum)AccountCur.AcctType).ordinal() == i)
            {
                listAcctType.SelectedIndex = i;
            }
             
        }
        textBankNumber.Text = AccountCur.BankNumber;
        checkInactive.Checked = AccountCur.Inactive;
        butColor.BackColor = AccountCur.AccountColor;
    }

    private void butColor_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog colorDialog1 = new ColorDialog();
        colorDialog1.Color = butColor.BackColor;
        colorDialog1.ShowDialog();
        butColor.BackColor = colorDialog1.Color;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        try
        {
            Accounts.delete(AccountCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Description is required.");
            return ;
        }
         
        AccountCur.Description = textDescription.Text;
        AccountCur.AcctType = (AccountType)listAcctType.SelectedIndex;
        AccountCur.BankNumber = textBankNumber.Text;
        AccountCur.Inactive = checkInactive.Checked;
        AccountCur.AccountColor = butColor.BackColor;
        if (IsNew)
        {
            Accounts.insert(AccountCur);
        }
        else
        {
            Accounts.update(AccountCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


