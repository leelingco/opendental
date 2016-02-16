//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

public class FormBillingDefaults  extends Form 
{

    public FormBillingDefaults() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formBillingDefaults_Load(Object sender, EventArgs e) throws Exception {
        textDays.Text = PrefC.getLong(PrefName.BillingDefaultsLastDays).ToString();
        checkIntermingled.Checked = PrefC.getBool(PrefName.BillingDefaultsIntermingle);
        textNote.Text = PrefC.getString(PrefName.BillingDefaultsNote);
        listElectBilling.SelectedIndex = 0;
        if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "1"))
        {
            listElectBilling.SelectedIndex = 1;
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "2"))
        {
            listElectBilling.SelectedIndex = 2;
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "3"))
        {
            listElectBilling.SelectedIndex = 3;
        }
         
        textVendorId.Text = PrefC.getString(PrefName.BillingElectVendorId);
        textVendorPMScode.Text = PrefC.getString(PrefName.BillingElectVendorPMSCode);
        String cc = PrefC.getString(PrefName.BillingElectCreditCardChoices);
        if (cc.Contains("MC"))
        {
            checkMC.Checked = true;
        }
         
        if (cc.Contains("V"))
        {
            checkV.Checked = true;
        }
         
        if (cc.Contains("D"))
        {
            checkD.Checked = true;
        }
         
        if (cc.Contains("A"))
        {
            checkAmEx.Checked = true;
        }
         
        textClientAcctNumber.Text = PrefC.getString(PrefName.BillingElectClientAcctNumber);
        textUserName.Text = PrefC.getString(PrefName.BillingElectUserName);
        textPassword.Text = PrefC.getString(PrefName.BillingElectPassword);
        //email
        textBillingEmailSubject.Text = PrefC.getString(PrefName.BillingEmailSubject);
        textBillingEmailBody.Text = PrefC.getString(PrefName.BillingEmailBodyText);
        textInvoiceNote.Text = PrefC.getString(PrefName.BillingDefaultsInvoiceNote);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDays.errorProvider1.GetError(textDays), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        String cc = "";
        if (checkMC.Checked)
        {
            cc = "MC";
        }
         
        if (checkV.Checked)
        {
            if (!StringSupport.equals(cc, ""))
            {
                cc += ",";
            }
             
            cc += "V";
        }
         
        if (checkD.Checked)
        {
            if (!StringSupport.equals(cc, ""))
            {
                cc += ",";
            }
             
            cc += "D";
        }
         
        if (checkAmEx.Checked)
        {
            if (!StringSupport.equals(cc, ""))
            {
                cc += ",";
            }
             
            cc += "A";
        }
         
        String billingUseElectronic = listElectBilling.SelectedIndex.ToString();
        if (Prefs.UpdateLong(PrefName.BillingDefaultsLastDays, PIn.Long(textDays.Text)) | Prefs.UpdateBool(PrefName.BillingDefaultsIntermingle, checkIntermingled.Checked) | Prefs.UpdateString(PrefName.BillingDefaultsNote, textNote.Text) | Prefs.updateString(PrefName.BillingUseElectronic,billingUseElectronic) | Prefs.UpdateString(PrefName.BillingEmailSubject, textBillingEmailSubject.Text) | Prefs.UpdateString(PrefName.BillingEmailBodyText, textBillingEmailBody.Text) | Prefs.UpdateString(PrefName.BillingElectVendorId, textVendorId.Text) | Prefs.UpdateString(PrefName.BillingElectVendorPMSCode, textVendorPMScode.Text) | Prefs.updateString(PrefName.BillingElectCreditCardChoices,cc) | Prefs.UpdateString(PrefName.BillingElectClientAcctNumber, textClientAcctNumber.Text) | Prefs.UpdateString(PrefName.BillingElectUserName, textUserName.Text) | Prefs.UpdateString(PrefName.BillingElectPassword, textPassword.Text) | Prefs.UpdateString(PrefName.BillingDefaultsInvoiceNote, textInvoiceNote.Text))
        {
            DataValid.setInvalid(InvalidType.Prefs);
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
        this.checkIntermingled = new System.Windows.Forms.CheckBox();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.listElectBilling = new System.Windows.Forms.ListBox();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textUserName = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.checkAmEx = new System.Windows.Forms.CheckBox();
        this.checkD = new System.Windows.Forms.CheckBox();
        this.checkV = new System.Windows.Forms.CheckBox();
        this.checkMC = new System.Windows.Forms.CheckBox();
        this.textClientAcctNumber = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textVendorPMScode = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textVendorId = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textBillingEmailSubject = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.textBillingEmailBody = new OpenDental.ODtextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textInvoiceNote = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.textDays = new OpenDental.ValidNum();
        this.textNote = new OpenDental.ODtextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // checkIntermingled
        //
        this.checkIntermingled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIntermingled.Location = new System.Drawing.Point(25, 33);
        this.checkIntermingled.Name = "checkIntermingled";
        this.checkIntermingled.Size = new System.Drawing.Size(150, 20);
        this.checkIntermingled.TabIndex = 1;
        this.checkIntermingled.Text = "Intermingle family members";
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(16, 9);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(147, 14);
        this.labelStartDate.TabIndex = 221;
        this.labelStartDate.Text = "Start Date Last";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(22, 55);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(575, 32);
        this.label4.TabIndex = 240;
        this.label4.Text = "General Message (in addition to any dunning messages and appointment messages).\r\n" + "You may use the variable [InstallmentPlanTerms] below to show the terms of the i" + "nstallment plan.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(211, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(65, 14);
        this.label1.TabIndex = 245;
        this.label1.Text = "Days";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.listElectBilling);
        this.groupBox1.Controls.Add(this.textPassword);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.textUserName);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.groupBox2);
        this.groupBox1.Controls.Add(this.textClientAcctNumber);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textVendorPMScode);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.textVendorId);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Location = new System.Drawing.Point(12, 204);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(635, 132);
        this.groupBox1.TabIndex = 247;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Electronic Billing";
        //
        // listElectBilling
        //
        this.listElectBilling.FormattingEnabled = true;
        this.listElectBilling.Items.AddRange(new Object[]{ "No electronic billing", "Dental X Change", "Output to file", "ClaimX / ExtraDent" });
        this.listElectBilling.Location = new System.Drawing.Point(12, 19);
        this.listElectBilling.Name = "listElectBilling";
        this.listElectBilling.Size = new System.Drawing.Size(120, 56);
        this.listElectBilling.TabIndex = 0;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(274, 104);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(158, 20);
        this.textPassword.TabIndex = 5;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(197, 106);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(75, 16);
        this.label6.TabIndex = 254;
        this.label6.Text = "Password";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUserName
        //
        this.textUserName.Location = new System.Drawing.Point(274, 83);
        this.textUserName.Name = "textUserName";
        this.textUserName.Size = new System.Drawing.Size(158, 20);
        this.textUserName.TabIndex = 4;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(200, 85);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(72, 16);
        this.label7.TabIndex = 252;
        this.label7.Text = "User Name";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.checkAmEx);
        this.groupBox2.Controls.Add(this.checkD);
        this.groupBox2.Controls.Add(this.checkV);
        this.groupBox2.Controls.Add(this.checkMC);
        this.groupBox2.Location = new System.Drawing.Point(470, 19);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(153, 85);
        this.groupBox2.TabIndex = 251;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Credit Card Choices";
        //
        // checkAmEx
        //
        this.checkAmEx.Location = new System.Drawing.Point(9, 66);
        this.checkAmEx.Name = "checkAmEx";
        this.checkAmEx.Size = new System.Drawing.Size(132, 16);
        this.checkAmEx.TabIndex = 3;
        this.checkAmEx.Text = "American Express";
        this.checkAmEx.UseVisualStyleBackColor = true;
        //
        // checkD
        //
        this.checkD.Location = new System.Drawing.Point(9, 50);
        this.checkD.Name = "checkD";
        this.checkD.Size = new System.Drawing.Size(132, 16);
        this.checkD.TabIndex = 2;
        this.checkD.Text = "Discover";
        this.checkD.UseVisualStyleBackColor = true;
        //
        // checkV
        //
        this.checkV.Location = new System.Drawing.Point(9, 34);
        this.checkV.Name = "checkV";
        this.checkV.Size = new System.Drawing.Size(132, 16);
        this.checkV.TabIndex = 1;
        this.checkV.Text = "Visa";
        this.checkV.UseVisualStyleBackColor = true;
        //
        // checkMC
        //
        this.checkMC.Location = new System.Drawing.Point(9, 18);
        this.checkMC.Name = "checkMC";
        this.checkMC.Size = new System.Drawing.Size(132, 16);
        this.checkMC.TabIndex = 0;
        this.checkMC.Text = "Master Card";
        this.checkMC.UseVisualStyleBackColor = true;
        //
        // textClientAcctNumber
        //
        this.textClientAcctNumber.Location = new System.Drawing.Point(274, 62);
        this.textClientAcctNumber.Name = "textClientAcctNumber";
        this.textClientAcctNumber.Size = new System.Drawing.Size(100, 20);
        this.textClientAcctNumber.TabIndex = 3;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(128, 64);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(144, 16);
        this.label5.TabIndex = 249;
        this.label5.Text = "Client Account Number";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textVendorPMScode
        //
        this.textVendorPMScode.Location = new System.Drawing.Point(274, 41);
        this.textVendorPMScode.Name = "textVendorPMScode";
        this.textVendorPMScode.Size = new System.Drawing.Size(100, 20);
        this.textVendorPMScode.TabIndex = 2;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(172, 43);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 16);
        this.label3.TabIndex = 247;
        this.label3.Text = "Vendor PMS Code";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textVendorId
        //
        this.textVendorId.Location = new System.Drawing.Point(274, 20);
        this.textVendorId.Name = "textVendorId";
        this.textVendorId.Size = new System.Drawing.Size(100, 20);
        this.textVendorId.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(169, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(103, 16);
        this.label2.TabIndex = 245;
        this.label2.Text = "Vendor ID";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBillingEmailSubject
        //
        this.textBillingEmailSubject.Location = new System.Drawing.Point(12, 70);
        this.textBillingEmailSubject.Name = "textBillingEmailSubject";
        this.textBillingEmailSubject.Size = new System.Drawing.Size(616, 20);
        this.textBillingEmailSubject.TabIndex = 0;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(11, 52);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(218, 16);
        this.label9.TabIndex = 240;
        this.label9.Text = "Subject";
        this.label9.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(11, 91);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(87, 16);
        this.label8.TabIndex = 240;
        this.label8.Text = "Body";
        this.label8.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.textBillingEmailBody);
        this.groupBox3.Controls.Add(this.label8);
        this.groupBox3.Controls.Add(this.label9);
        this.groupBox3.Controls.Add(this.textBillingEmailSubject);
        this.groupBox3.Controls.Add(this.label10);
        this.groupBox3.Location = new System.Drawing.Point(12, 354);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(635, 218);
        this.groupBox3.TabIndex = 248;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Email Statements";
        //
        // textBillingEmailBody
        //
        this.textBillingEmailBody.AcceptsTab = true;
        this.textBillingEmailBody.DetectUrls = false;
        this.textBillingEmailBody.Location = new System.Drawing.Point(12, 109);
        this.textBillingEmailBody.Name = "textBillingEmailBody";
        this.textBillingEmailBody.setQuickPasteType(OpenDentBusiness.QuickPasteType.Statement);
        this.textBillingEmailBody.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textBillingEmailBody.Size = new System.Drawing.Size(616, 102);
        this.textBillingEmailBody.TabIndex = 1;
        this.textBillingEmailBody.Text = "";
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(11, 23);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(612, 29);
        this.label10.TabIndex = 249;
        this.label10.Text = "These variables may be used in either box: [monthlyCardsOnFile], [nameF], [nameFL" + "], [nameFLnoPref], [namePref], [PatNum], and [currentMonth].";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textInvoiceNote
        //
        this.textInvoiceNote.Location = new System.Drawing.Point(24, 596);
        this.textInvoiceNote.Name = "textInvoiceNote";
        this.textInvoiceNote.Size = new System.Drawing.Size(616, 20);
        this.textInvoiceNote.TabIndex = 249;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(23, 578);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(152, 16);
        this.label11.TabIndex = 250;
        this.label11.Text = "Invoice Note";
        this.label11.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textDays
        //
        this.textDays.Location = new System.Drawing.Point(165, 7);
        this.textDays.setMaxVal(255);
        this.textDays.setMinVal(0);
        this.textDays.Name = "textDays";
        this.textDays.Size = new System.Drawing.Size(44, 20);
        this.textDays.TabIndex = 0;
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(24, 90);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Statement);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(616, 102);
        this.textNote.TabIndex = 2;
        this.textNote.Text = "";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(482, 630);
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
        this.butCancel.Location = new System.Drawing.Point(563, 630);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormBillingDefaults
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(662, 666);
        this.Controls.Add(this.textInvoiceNote);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDays);
        this.Controls.Add(this.checkIntermingled);
        this.Controls.Add(this.labelStartDate);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormBillingDefaults";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Billing Defaults";
        this.Load += new System.EventHandler(this.FormBillingDefaults_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.CheckBox checkIntermingled = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelStartDate = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textDays;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBillingEmailSubject = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textUserName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkAmEx = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkD = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkV = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkMC = new System.Windows.Forms.CheckBox();
    private OpenDental.ODtextBox textBillingEmailBody;
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textClientAcctNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textVendorPMScode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textVendorId = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listElectBilling = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textInvoiceNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
}


