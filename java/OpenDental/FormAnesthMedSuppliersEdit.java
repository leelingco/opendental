//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.FormAnesthMedSuppliersEdit;
import OpenDental.Properties.Resources;

public class FormAnesthMedSuppliersEdit  extends Form 
{

    public AnesthMedSupplier SupplCur = new AnesthMedSupplier();
    public FormAnesthMedSuppliersEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAnesthMedSuppliersEdit_Load(Object sender, EventArgs e) throws Exception {
        textSupplierName.Text = SupplCur.SupplierName;
        textPhone.Text = SupplCur.Phone;
        textPhoneExt.Text = SupplCur.PhoneExt;
        textFax.Text = SupplCur.Fax;
        textAddr1.Text = SupplCur.Addr1;
        textAddr2.Text = SupplCur.Addr2;
        textCity.Text = SupplCur.City;
        textState.Text = SupplCur.State;
        textZip.Text = SupplCur.Zip;
        textContact.Text = SupplCur.Contact;
        textWebSite.Text = SupplCur.WebSite;
        richTextNotes.Text = SupplCur.Notes;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        /* (textSupplierName.Text == "")
                    {
                        MessageBox.Show(Lan.g(this, "Supplier name cannot be blank."));
                        return;
                    }
                    if (CultureInfo.CurrentCulture.Name == "en-US")
                    {
                        if (textPhone.Text != "" && TelephoneNumbers.FormatNumbersExactTen(textPhone.Text) == "")
                        {
                            MessageBox.Show(Lan.g(this, "Phone number must be in a 10-digit format."));
                            return;
                        }
                        if (textFax.Text != "" && TelephoneNumbers.FormatNumbersExactTen(textFax.Text) == "")
                        {
                            MessageBox.Show(Lan.g(this, "Fax number must be in a 10-digit format."));
                            return;
                        }
                    }*/
        SupplCur.SupplierName = textSupplierName.Text;
        SupplCur.Phone = textPhone.Text;
        SupplCur.PhoneExt = textPhoneExt.Text;
        SupplCur.Fax = textFax.Text;
        SupplCur.Addr1 = textAddr1.Text;
        SupplCur.Addr2 = textAddr2.Text;
        SupplCur.City = textCity.Text;
        SupplCur.State = textState.Text;
        SupplCur.Zip = textZip.Text;
        SupplCur.Contact = textContact.Text;
        SupplCur.WebSite = textWebSite.Text;
        SupplCur.Notes = richTextNotes.Text;
        AnesthMedSuppliers.WriteObject(SupplCur);
        /*try
                    {
                        AnesthMedSuppliers.WriteObject(SupplCur);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                        return;
                    }*/
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void textPhone_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    /*int cursor = textPhone.SelectionStart;
    			int length = textPhone.Text.Length;
    			textPhone.Text = TelephoneNumbers.AutoFormat(textPhone.Text);
    			if (textPhone.Text.Length > length)
    				cursor++;
    			textPhone.SelectionStart = cursor;*/
    private void textFax_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    /*int cursor = textFax.SelectionStart;
                int length = textFax.Text.Length;
                textFax.Text = TelephoneNumbers.AutoFormat(textFax.Text);
                if (textFax.Text.Length > length)
                    cursor++;
                textFax.SelectionStart = cursor;*/
    private void textSupplierName_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    /*int cursor = textSupplierName.SelectionStart;
    			int length = textSupplierName.Text.Length;
    			textSupplierName.Text = textSupplierName.Text;
    			textSupplierName.SelectionStart = cursor;*/
    private void textCity_TextChanged(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnesthMedSuppliersEdit.class);
        this.groupBoxSupplier = new System.Windows.Forms.GroupBox();
        this.textWebSite = new System.Windows.Forms.TextBox();
        this.labelWebSite = new System.Windows.Forms.Label();
        this.textFax = new System.Windows.Forms.TextBox();
        this.labelFax = new System.Windows.Forms.Label();
        this.textContact = new System.Windows.Forms.TextBox();
        this.labelContact = new System.Windows.Forms.Label();
        this.textPhoneExt = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.labelSupplierNotes = new System.Windows.Forms.Label();
        this.richTextNotes = new System.Windows.Forms.RichTextBox();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textZip = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textState = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textCity = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textAddr1 = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textSupplierName = new System.Windows.Forms.TextBox();
        this.textAddr2 = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBoxSupplier.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBoxSupplier
        //
        this.groupBoxSupplier.Controls.Add(this.textWebSite);
        this.groupBoxSupplier.Controls.Add(this.labelWebSite);
        this.groupBoxSupplier.Controls.Add(this.textFax);
        this.groupBoxSupplier.Controls.Add(this.labelFax);
        this.groupBoxSupplier.Controls.Add(this.textContact);
        this.groupBoxSupplier.Controls.Add(this.labelContact);
        this.groupBoxSupplier.Controls.Add(this.textPhoneExt);
        this.groupBoxSupplier.Controls.Add(this.label8);
        this.groupBoxSupplier.Controls.Add(this.labelSupplierNotes);
        this.groupBoxSupplier.Controls.Add(this.richTextNotes);
        this.groupBoxSupplier.Controls.Add(this.textPhone);
        this.groupBoxSupplier.Controls.Add(this.label7);
        this.groupBoxSupplier.Controls.Add(this.label6);
        this.groupBoxSupplier.Controls.Add(this.textZip);
        this.groupBoxSupplier.Controls.Add(this.label5);
        this.groupBoxSupplier.Controls.Add(this.textState);
        this.groupBoxSupplier.Controls.Add(this.label4);
        this.groupBoxSupplier.Controls.Add(this.textCity);
        this.groupBoxSupplier.Controls.Add(this.label3);
        this.groupBoxSupplier.Controls.Add(this.label2);
        this.groupBoxSupplier.Controls.Add(this.textAddr1);
        this.groupBoxSupplier.Controls.Add(this.label1);
        this.groupBoxSupplier.Controls.Add(this.textSupplierName);
        this.groupBoxSupplier.Controls.Add(this.textAddr2);
        this.groupBoxSupplier.Location = new System.Drawing.Point(23, 26);
        this.groupBoxSupplier.Name = "groupBoxSupplier";
        this.groupBoxSupplier.Size = new System.Drawing.Size(579, 310);
        this.groupBoxSupplier.TabIndex = 4;
        this.groupBoxSupplier.TabStop = false;
        this.groupBoxSupplier.Text = "Suppliers";
        //
        // textWebSite
        //
        this.textWebSite.Location = new System.Drawing.Point(336, 176);
        this.textWebSite.Name = "textWebSite";
        this.textWebSite.Size = new System.Drawing.Size(223, 20);
        this.textWebSite.TabIndex = 10;
        //
        // labelWebSite
        //
        this.labelWebSite.AutoSize = true;
        this.labelWebSite.Location = new System.Drawing.Point(279, 179);
        this.labelWebSite.Name = "labelWebSite";
        this.labelWebSite.Size = new System.Drawing.Size(51, 13);
        this.labelWebSite.TabIndex = 114;
        this.labelWebSite.Text = "Web Site";
        //
        // textFax
        //
        this.textFax.Location = new System.Drawing.Point(307, 64);
        this.textFax.MaxLength = 13;
        this.textFax.Name = "textFax";
        this.textFax.Size = new System.Drawing.Size(80, 20);
        this.textFax.TabIndex = 3;
        //
        // labelFax
        //
        this.labelFax.AutoSize = true;
        this.labelFax.Location = new System.Drawing.Point(269, 67);
        this.labelFax.Name = "labelFax";
        this.labelFax.Size = new System.Drawing.Size(34, 13);
        this.labelFax.TabIndex = 112;
        this.labelFax.Text = "Fax #";
        //
        // textContact
        //
        this.textContact.Location = new System.Drawing.Point(440, 64);
        this.textContact.Name = "textContact";
        this.textContact.Size = new System.Drawing.Size(118, 20);
        this.textContact.TabIndex = 4;
        //
        // labelContact
        //
        this.labelContact.AutoSize = true;
        this.labelContact.Location = new System.Drawing.Point(393, 67);
        this.labelContact.Name = "labelContact";
        this.labelContact.Size = new System.Drawing.Size(44, 13);
        this.labelContact.TabIndex = 110;
        this.labelContact.Text = "Contact";
        //
        // textPhoneExt
        //
        this.textPhoneExt.Location = new System.Drawing.Point(224, 64);
        this.textPhoneExt.MaxLength = 5;
        this.textPhoneExt.Name = "textPhoneExt";
        this.textPhoneExt.Size = new System.Drawing.Size(39, 20);
        this.textPhoneExt.TabIndex = 2;
        //
        // label8
        //
        this.label8.AutoSize = true;
        this.label8.Location = new System.Drawing.Point(193, 67);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(25, 13);
        this.label8.TabIndex = 108;
        this.label8.Text = "Ext.";
        //
        // labelSupplierNotes
        //
        this.labelSupplierNotes.AutoSize = true;
        this.labelSupplierNotes.Location = new System.Drawing.Point(58, 209);
        this.labelSupplierNotes.Name = "labelSupplierNotes";
        this.labelSupplierNotes.Size = new System.Drawing.Size(35, 13);
        this.labelSupplierNotes.TabIndex = 105;
        this.labelSupplierNotes.Text = "Notes";
        //
        // richTextNotes
        //
        this.richTextNotes.Location = new System.Drawing.Point(105, 209);
        this.richTextNotes.Name = "richTextNotes";
        this.richTextNotes.Size = new System.Drawing.Size(454, 79);
        this.richTextNotes.TabIndex = 11;
        this.richTextNotes.Text = "";
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(105, 64);
        this.textPhone.MaxLength = 13;
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(80, 20);
        this.textPhone.TabIndex = 1;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // label7
        //
        this.label7.AutoSize = true;
        this.label7.Location = new System.Drawing.Point(49, 67);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(48, 13);
        this.label7.TabIndex = 83;
        this.label7.Text = "Phone #";
        //
        // label6
        //
        this.label6.AutoSize = true;
        this.label6.Location = new System.Drawing.Point(17, 179);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(82, 13);
        this.label6.TabIndex = 11;
        this.label6.Text = "Zip/postal code";
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(105, 176);
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(168, 20);
        this.textZip.TabIndex = 9;
        this.textZip.Tag = "";
        //
        // label5
        //
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(279, 153);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(79, 13);
        this.label5.TabIndex = 9;
        this.label5.Text = "State/Province";
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(364, 150);
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(195, 20);
        this.textState.TabIndex = 8;
        //
        // label4
        //
        this.label4.AutoSize = true;
        this.label4.Location = new System.Drawing.Point(69, 153);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(24, 13);
        this.label4.TabIndex = 7;
        this.label4.Text = "City";
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(105, 150);
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(168, 20);
        this.textCity.TabIndex = 7;
        this.textCity.TextChanged += new System.EventHandler(this.textCity_TextChanged);
        //
        // label3
        //
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(39, 127);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(54, 13);
        this.label3.TabIndex = 5;
        this.label3.Text = "Address 2";
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(39, 101);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(54, 13);
        this.label2.TabIndex = 3;
        this.label2.Text = "Address 1";
        //
        // textAddr1
        //
        this.textAddr1.Location = new System.Drawing.Point(105, 98);
        this.textAddr1.Name = "textAddr1";
        this.textAddr1.Size = new System.Drawing.Size(454, 20);
        this.textAddr1.TabIndex = 5;
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(23, 35);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(70, 13);
        this.label1.TabIndex = 1;
        this.label1.Text = "Vendor name";
        //
        // textSupplierName
        //
        this.textSupplierName.Location = new System.Drawing.Point(104, 33);
        this.textSupplierName.Name = "textSupplierName";
        this.textSupplierName.Size = new System.Drawing.Size(454, 20);
        this.textSupplierName.TabIndex = 0;
        this.textSupplierName.TextChanged += new System.EventHandler(this.textSupplierName_TextChanged);
        //
        // textAddr2
        //
        this.textAddr2.Location = new System.Drawing.Point(105, 124);
        this.textAddr2.Name = "textAddr2";
        this.textAddr2.Size = new System.Drawing.Size(454, 20);
        this.textAddr2.TabIndex = 6;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(false);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(499, 353);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(91, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "Save and Close";
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
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(416, 353);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAnesthMedSuppliersEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(625, 389);
        this.Controls.Add(this.groupBoxSupplier);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnesthMedSuppliersEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Anesthetic Medication Supplier";
        this.Load += new System.EventHandler(this.FormAnesthMedSuppliersEdit_Load);
        this.groupBoxSupplier.ResumeLayout(false);
        this.groupBoxSupplier.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.GroupBox groupBoxSupplier = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddr2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddr1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSupplierName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelSupplierNotes = new System.Windows.Forms.Label();
    private System.Windows.Forms.RichTextBox richTextNotes = new System.Windows.Forms.RichTextBox();
    private System.Windows.Forms.TextBox textPhoneExt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textContact = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelContact = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFax = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelFax = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textWebSite = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelWebSite = new System.Windows.Forms.Label();
}


