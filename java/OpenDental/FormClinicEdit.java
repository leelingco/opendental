//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormClinicEdit;
import OpenDental.FormEmailAddresses;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.TelephoneNumbers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClinicEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAddress2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBankNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPlaceService = new System.Windows.Forms.ComboBox();
    private GroupBox groupBox4 = new GroupBox();
    private ComboBox comboInsBillingProv = new ComboBox();
    private RadioButton radioInsBillingProvSpecific = new RadioButton();
    private RadioButton radioInsBillingProvTreat = new RadioButton();
    private RadioButton radioInsBillingProvDefault = new RadioButton();
    private TextBox textFax = new TextBox();
    private Label label8 = new Label();
    private Label label9 = new Label();
    private Label label10 = new Label();
    private TextBox textEmail = new TextBox();
    private Clinic ClinicCur;
    private OpenDental.UI.Button butEmail;
    /**
    * 
    */
    public FormClinicEdit(Clinic clinicCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        ClinicCur = clinicCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClinicEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textCity = new System.Windows.Forms.TextBox();
        this.textState = new System.Windows.Forms.TextBox();
        this.textZip = new System.Windows.Forms.TextBox();
        this.textAddress2 = new System.Windows.Forms.TextBox();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textBankNumber = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.comboPlaceService = new System.Windows.Forms.ComboBox();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.comboInsBillingProv = new System.Windows.Forms.ComboBox();
        this.radioInsBillingProvSpecific = new System.Windows.Forms.RadioButton();
        this.radioInsBillingProvTreat = new System.Windows.Forms.RadioButton();
        this.radioInsBillingProvDefault = new System.Windows.Forms.RadioButton();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textFax = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textEmail = new System.Windows.Forms.TextBox();
        this.butEmail = new OpenDental.UI.Button();
        this.groupBox4.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Clinic Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(169, 20);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(241, 20);
        this.textDescription.TabIndex = 0;
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(169, 121);
        this.textCity.MaxLength = 255;
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(155, 20);
        this.textCity.TabIndex = 4;
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(324, 121);
        this.textState.MaxLength = 255;
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(65, 20);
        this.textState.TabIndex = 5;
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(389, 121);
        this.textZip.MaxLength = 255;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(71, 20);
        this.textZip.TabIndex = 6;
        //
        // textAddress2
        //
        this.textAddress2.Location = new System.Drawing.Point(169, 100);
        this.textAddress2.MaxLength = 255;
        this.textAddress2.Name = "textAddress2";
        this.textAddress2.Size = new System.Drawing.Size(291, 20);
        this.textAddress2.TabIndex = 3;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(169, 80);
        this.textAddress.MaxLength = 255;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(291, 20);
        this.textAddress.TabIndex = 2;
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(169, 40);
        this.textPhone.MaxLength = 255;
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(157, 20);
        this.textPhone.TabIndex = 1;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(4, 125);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(163, 15);
        this.label11.TabIndex = 105;
        this.label11.Text = "City, ST, Zip";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(16, 104);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(151, 17);
        this.label4.TabIndex = 103;
        this.label4.Text = "Address2";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 82);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(151, 17);
        this.label3.TabIndex = 101;
        this.label3.Text = "Address";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(17, 43);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(151, 17);
        this.label2.TabIndex = 99;
        this.label2.Text = "Phone";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textBankNumber
        //
        this.textBankNumber.Location = new System.Drawing.Point(169, 167);
        this.textBankNumber.MaxLength = 255;
        this.textBankNumber.Name = "textBankNumber";
        this.textBankNumber.Size = new System.Drawing.Size(291, 20);
        this.textBankNumber.TabIndex = 7;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(16, 171);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(151, 17);
        this.label5.TabIndex = 109;
        this.label5.Text = "Bank Account Number";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(329, 42);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(144, 18);
        this.label6.TabIndex = 110;
        this.label6.Text = "(###)###-####";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(-2, 214);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(168, 17);
        this.label7.TabIndex = 111;
        this.label7.Text = "Default Proc Place of Service";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboPlaceService
        //
        this.comboPlaceService.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPlaceService.Location = new System.Drawing.Point(169, 211);
        this.comboPlaceService.MaxDropDownItems = 30;
        this.comboPlaceService.Name = "comboPlaceService";
        this.comboPlaceService.Size = new System.Drawing.Size(177, 21);
        this.comboPlaceService.TabIndex = 8;
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.comboInsBillingProv);
        this.groupBox4.Controls.Add(this.radioInsBillingProvSpecific);
        this.groupBox4.Controls.Add(this.radioInsBillingProvTreat);
        this.groupBox4.Controls.Add(this.radioInsBillingProvDefault);
        this.groupBox4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox4.Location = new System.Drawing.Point(168, 250);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(235, 104);
        this.groupBox4.TabIndex = 9;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Default Insurance Billing Dentist";
        //
        // comboInsBillingProv
        //
        this.comboInsBillingProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboInsBillingProv.Location = new System.Drawing.Point(17, 73);
        this.comboInsBillingProv.Name = "comboInsBillingProv";
        this.comboInsBillingProv.Size = new System.Drawing.Size(212, 21);
        this.comboInsBillingProv.TabIndex = 3;
        //
        // radioInsBillingProvSpecific
        //
        this.radioInsBillingProvSpecific.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioInsBillingProvSpecific.Location = new System.Drawing.Point(17, 53);
        this.radioInsBillingProvSpecific.Name = "radioInsBillingProvSpecific";
        this.radioInsBillingProvSpecific.Size = new System.Drawing.Size(186, 19);
        this.radioInsBillingProvSpecific.TabIndex = 2;
        this.radioInsBillingProvSpecific.Text = "Specific Provider:";
        //
        // radioInsBillingProvTreat
        //
        this.radioInsBillingProvTreat.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioInsBillingProvTreat.Location = new System.Drawing.Point(17, 34);
        this.radioInsBillingProvTreat.Name = "radioInsBillingProvTreat";
        this.radioInsBillingProvTreat.Size = new System.Drawing.Size(186, 19);
        this.radioInsBillingProvTreat.TabIndex = 1;
        this.radioInsBillingProvTreat.Text = "Treating Provider";
        //
        // radioInsBillingProvDefault
        //
        this.radioInsBillingProvDefault.Checked = true;
        this.radioInsBillingProvDefault.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioInsBillingProvDefault.Location = new System.Drawing.Point(17, 16);
        this.radioInsBillingProvDefault.Name = "radioInsBillingProvDefault";
        this.radioInsBillingProvDefault.Size = new System.Drawing.Size(186, 19);
        this.radioInsBillingProvDefault.TabIndex = 0;
        this.radioInsBillingProvDefault.TabStop = true;
        this.radioInsBillingProvDefault.Text = "Default Practice Provider";
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
        this.butDelete.Location = new System.Drawing.Point(27, 424);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 10;
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
        this.butOK.Location = new System.Drawing.Point(458, 424);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 11;
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
        this.butCancel.Location = new System.Drawing.Point(549, 424);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 12;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textFax
        //
        this.textFax.Location = new System.Drawing.Point(169, 60);
        this.textFax.MaxLength = 255;
        this.textFax.Name = "textFax";
        this.textFax.Size = new System.Drawing.Size(157, 20);
        this.textFax.TabIndex = 112;
        this.textFax.TextChanged += new System.EventHandler(this.textFax_TextChanged);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(17, 63);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(151, 17);
        this.label8.TabIndex = 113;
        this.label8.Text = "Fax";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(329, 62);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(144, 18);
        this.label9.TabIndex = 114;
        this.label9.Text = "(###)###-####";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(-2, 379);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(168, 17);
        this.label10.TabIndex = 111;
        this.label10.Text = "Clinic Email Address";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textEmail
        //
        this.textEmail.BackColor = System.Drawing.SystemColors.Window;
        this.textEmail.Location = new System.Drawing.Point(169, 376);
        this.textEmail.MaxLength = 255;
        this.textEmail.Name = "textEmail";
        this.textEmail.ReadOnly = true;
        this.textEmail.Size = new System.Drawing.Size(261, 20);
        this.textEmail.TabIndex = 7;
        //
        // butEmail
        //
        this.butEmail.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEmail.setAutosize(true);
        this.butEmail.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEmail.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEmail.setCornerRadius(4F);
        this.butEmail.Location = new System.Drawing.Point(436, 375);
        this.butEmail.Name = "butEmail";
        this.butEmail.Size = new System.Drawing.Size(24, 21);
        this.butEmail.TabIndex = 11;
        this.butEmail.Text = "...";
        this.butEmail.Click += new System.EventHandler(this.butEmail_Click);
        //
        // FormClinicEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(650, 468);
        this.Controls.Add(this.textFax);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.comboPlaceService);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textEmail);
        this.Controls.Add(this.textBankNumber);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textCity);
        this.Controls.Add(this.textState);
        this.Controls.Add(this.textZip);
        this.Controls.Add(this.textAddress2);
        this.Controls.Add(this.textAddress);
        this.Controls.Add(this.textPhone);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butEmail);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label6);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClinicEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Clinic";
        this.Load += new System.EventHandler(this.FormClinicEdit_Load);
        this.groupBox4.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClinicEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = ClinicCur.Description;
        String phone = ClinicCur.Phone;
        if (phone != null && phone.Length == 10 && StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            textPhone.Text = "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
        }
        else
        {
            textPhone.Text = phone;
        } 
        String fax = ClinicCur.Fax;
        if (fax != null && fax.Length == 10 && StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            textFax.Text = "(" + fax.Substring(0, 3) + ")" + fax.Substring(3, 3) + "-" + fax.Substring(6);
        }
        else
        {
            textFax.Text = fax;
        } 
        textAddress.Text = ClinicCur.Address;
        textAddress2.Text = ClinicCur.Address2;
        textCity.Text = ClinicCur.City;
        textState.Text = ClinicCur.State;
        textZip.Text = ClinicCur.Zip;
        textBankNumber.Text = ClinicCur.BankNumber;
        comboPlaceService.Items.Clear();
        comboPlaceService.Items.AddRange(Enum.GetNames(PlaceOfService.class));
        comboPlaceService.SelectedIndex = ((Enum)ClinicCur.DefaultPlaceService).ordinal();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboInsBillingProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        if (ClinicCur.InsBillingProv == 0)
        {
            radioInsBillingProvDefault.Checked = true;
        }
        else //default=0
        if (ClinicCur.InsBillingProv == -1)
        {
            radioInsBillingProvTreat.Checked = true;
        }
        else
        {
            //treat=-1
            radioInsBillingProvSpecific.Checked = true;
            //specific=any number >0. Foreign key to ProvNum
            comboInsBillingProv.SelectedIndex = Providers.getIndex(ClinicCur.InsBillingProv);
        }  
        EmailAddress emailAddress = EmailAddresses.getOne(ClinicCur.EmailAddressNum);
        if (emailAddress != null)
        {
            textEmail.Text = emailAddress.EmailUsername;
        }
         
    }

    private void textPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textPhone.SelectionStart;
        int length = textPhone.Text.Length;
        textPhone.Text = TelephoneNumbers.AutoFormat(textPhone.Text);
        if (textPhone.Text.Length > length)
            cursor++;
         
        textPhone.SelectionStart = cursor;
    }

    private void textFax_TextChanged(Object sender, EventArgs e) throws Exception {
        int cursor = textFax.SelectionStart;
        int length = textFax.Text.Length;
        textFax.Text = TelephoneNumbers.AutoFormat(textFax.Text);
        if (textFax.Text.Length > length)
            cursor++;
         
        textFax.SelectionStart = cursor;
    }

    private void butEmail_Click(Object sender, EventArgs e) throws Exception {
        FormEmailAddresses FormEA = new FormEmailAddresses();
        FormEA.IsSelectionMode = true;
        FormEA.ShowDialog();
        if (FormEA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ClinicCur.EmailAddressNum = FormEA.EmailAddressNum;
        textEmail.Text = EmailAddresses.getOne(FormEA.EmailAddressNum).EmailUsername;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete Clinic?"))
        {
            return ;
        }
         
        try
        {
            Clinics.delete(ClinicCur);
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
         
        if (radioInsBillingProvSpecific.Checked && comboInsBillingProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"You must select a provider.");
            return ;
        }
         
        String phone = textPhone.Text;
        if (StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            phone = phone.Replace("(", "");
            phone = phone.Replace(")", "");
            phone = phone.Replace(" ", "");
            phone = phone.Replace("-", "");
            if (phone.Length != 0 && phone.Length != 10)
            {
                MessageBox.Show("Invalid phone");
                return ;
            }
             
        }
         
        String fax = textFax.Text;
        if (StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            fax = fax.Replace("(", "");
            fax = fax.Replace(")", "");
            fax = fax.Replace(" ", "");
            fax = fax.Replace("-", "");
            if (fax.Length != 0 && fax.Length != 10)
            {
                MessageBox.Show("Invalid fax");
                return ;
            }
             
        }
         
        ClinicCur.Description = textDescription.Text;
        ClinicCur.Phone = phone;
        ClinicCur.Fax = fax;
        ClinicCur.Address = textAddress.Text;
        ClinicCur.Address2 = textAddress2.Text;
        ClinicCur.City = textCity.Text;
        ClinicCur.State = textState.Text;
        ClinicCur.Zip = textZip.Text;
        ClinicCur.BankNumber = textBankNumber.Text;
        ClinicCur.DefaultPlaceService = (PlaceOfService)comboPlaceService.SelectedIndex;
        if (radioInsBillingProvDefault.Checked)
        {
            //default=0
            ClinicCur.InsBillingProv = 0;
        }
        else if (radioInsBillingProvTreat.Checked)
        {
            //treat=-1
            ClinicCur.InsBillingProv = -1;
        }
        else
        {
            ClinicCur.InsBillingProv = ProviderC.getListShort()[comboInsBillingProv.SelectedIndex].ProvNum;
        }  
        if (IsNew)
        {
            Clinics.insert(ClinicCur);
        }
        else
        {
            Clinics.update(ClinicCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


