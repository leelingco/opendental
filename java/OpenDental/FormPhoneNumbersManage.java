//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PhoneNumber;
import OpenDentBusiness.PhoneNumbers;
import OpenDentBusiness.TelephoneNumbers;
import OpenDental.Properties.Resources;

public class FormPhoneNumbersManage  extends Form 
{

    public long PatNum = new long();
    private Patient Pat;
    private List<PhoneNumber> otherList = new List<PhoneNumber>();
    public FormPhoneNumbersManage() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPhoneNumbersManage_Load(Object sender, EventArgs e) throws Exception {
        Pat = Patients.getPat(PatNum);
        textName.Text = Pat.LName + ", " + Pat.FName;
        textWkPhone.Text = Pat.WkPhone;
        textHmPhone.Text = Pat.HmPhone;
        textWirelessPhone.Text = Pat.WirelessPhone;
        fillList();
    }

    private void fillList() throws Exception {
        listOther.Items.Clear();
        otherList = PhoneNumbers.getPhoneNumbers(PatNum);
        for (int i = 0;i < otherList.Count;i++)
        {
            listOther.Items.Add(otherList[i].PhoneNumberVal);
        }
    }

    private void textWirelessPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textWirelessPhone.SelectionStart;
        int length = textWirelessPhone.Text.Length;
        textWirelessPhone.Text = TelephoneNumbers.AutoFormat(textWirelessPhone.Text);
        if (textWirelessPhone.Text.Length > length)
            cursor++;
         
        textWirelessPhone.SelectionStart = cursor;
    }

    private void textWkPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textWkPhone.SelectionStart;
        int length = textWkPhone.Text.Length;
        textWkPhone.Text = TelephoneNumbers.AutoFormat(textWkPhone.Text);
        if (textWkPhone.Text.Length > length)
            cursor++;
         
        textWkPhone.SelectionStart = cursor;
    }

    private void textHmPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textHmPhone.SelectionStart;
        int length = textHmPhone.Text.Length;
        textHmPhone.Text = TelephoneNumbers.AutoFormat(textHmPhone.Text);
        if (textHmPhone.Text.Length > length)
            cursor++;
         
        textHmPhone.SelectionStart = cursor;
    }

    private void listOther_DoubleClick(Object sender, EventArgs e) throws Exception {
        int index = listOther.SelectedIndex;
        if (index == -1)
        {
            return ;
        }
         
        InputBox input = new InputBox("Phone Number");
        input.textResult.Text = otherList[index].PhoneNumberVal;
        input.ShowDialog();
        if (input.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        otherList[index].PhoneNumberVal = input.textResult.Text;
        PhoneNumbers.Update(otherList[index]);
        fillList();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        InputBox input = new InputBox("Phone Number");
        input.ShowDialog();
        if (input.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.PatNum = PatNum;
        phoneNumber.PhoneNumberVal = input.textResult.Text;
        PhoneNumbers.insert(phoneNumber);
        fillList();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (listOther.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a phone number first.");
            return ;
        }
         
        PhoneNumbers.DeleteObject(otherList[listOther.SelectedIndex].PhoneNumberNum);
        fillList();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Patient PatOld = Pat.copy();
        Pat.WkPhone = textWkPhone.Text;
        Pat.HmPhone = textHmPhone.Text;
        Pat.WirelessPhone = textWirelessPhone.Text;
        Pat.AddrNote = textAddrNotes.Text;
        Patients.update(Pat,PatOld);
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
        this.textName = new System.Windows.Forms.TextBox();
        this.textWkPhone = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textHmPhone = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textWirelessPhone = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.listOther = new System.Windows.Forms.ListBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.textAddrNotes = new OpenDental.ODtextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(26, 22);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(123, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Office Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(151, 20);
        this.textName.Name = "textName";
        this.textName.ReadOnly = true;
        this.textName.Size = new System.Drawing.Size(425, 20);
        this.textName.TabIndex = 5;
        //
        // textWkPhone
        //
        this.textWkPhone.Location = new System.Drawing.Point(151, 46);
        this.textWkPhone.Name = "textWkPhone";
        this.textWkPhone.Size = new System.Drawing.Size(224, 20);
        this.textWkPhone.TabIndex = 7;
        this.textWkPhone.TextChanged += new System.EventHandler(this.textWkPhone_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(26, 48);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(123, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "Work Phone";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHmPhone
        //
        this.textHmPhone.Location = new System.Drawing.Point(151, 72);
        this.textHmPhone.Name = "textHmPhone";
        this.textHmPhone.Size = new System.Drawing.Size(224, 20);
        this.textHmPhone.TabIndex = 9;
        this.textHmPhone.TextChanged += new System.EventHandler(this.textHmPhone_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(26, 74);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(123, 16);
        this.label3.TabIndex = 8;
        this.label3.Text = "Home Phone";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textWirelessPhone
        //
        this.textWirelessPhone.Location = new System.Drawing.Point(151, 98);
        this.textWirelessPhone.Name = "textWirelessPhone";
        this.textWirelessPhone.Size = new System.Drawing.Size(224, 20);
        this.textWirelessPhone.TabIndex = 11;
        this.textWirelessPhone.TextChanged += new System.EventHandler(this.textWirelessPhone_TextChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(26, 100);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(123, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "Wireless Phone";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(42, 191);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(107, 16);
        this.label5.TabIndex = 12;
        this.label5.Text = "Other Numbers";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listOther
        //
        this.listOther.FormattingEnabled = true;
        this.listOther.Location = new System.Drawing.Point(151, 189);
        this.listOther.Name = "listOther";
        this.listOther.Size = new System.Drawing.Size(178, 186);
        this.listOther.TabIndex = 13;
        this.listOther.DoubleClick += new System.EventHandler(this.listOther_DoubleClick);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(148, 408);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(225, 55);
        this.label6.TabIndex = 15;
        this.label6.Text = "The change will not show immediately in the phone grid, but will instead show the" + " next time the patient calls.";
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(2, 125);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(147, 28);
        this.label7.TabIndex = 17;
        this.label7.Text = "Address and Phone Notes";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textAddrNotes
        //
        this.textAddrNotes.Location = new System.Drawing.Point(151, 124);
        this.textAddrNotes.Multiline = true;
        this.textAddrNotes.Name = "textAddrNotes";
        this.textAddrNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.PatAddressNote);
        this.textAddrNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textAddrNotes.Size = new System.Drawing.Size(224, 59);
        this.textAddrNotes.TabIndex = 16;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(151, 381);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 14;
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
        this.butOK.Location = new System.Drawing.Point(533, 395);
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
        this.butCancel.Location = new System.Drawing.Point(533, 433);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(250, 381);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 14;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormPhoneNumbersManage
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(633, 484);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textAddrNotes);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.listOther);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textWirelessPhone);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textHmPhone);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textWkPhone);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormPhoneNumbersManage";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Phone Numbers";
        this.Load += new System.EventHandler(this.FormPhoneNumbersManage_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textWkPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textHmPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textWirelessPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listOther = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textAddrNotes;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
}


