//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPharmacyEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.TelephoneNumbers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPharmacyEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textStoreName = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
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
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private TextBox textFax = new TextBox();
    private Label label5 = new Label();
    private Label label7 = new Label();
    private TextBox textNote = new TextBox();
    private Label label8 = new Label();
    public Pharmacy PharmCur;
    /**
    * 
    */
    public FormPharmacyEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPharmacyEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textStoreName = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
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
        this.label6 = new System.Windows.Forms.Label();
        this.textFax = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(456, 291);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 10;
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
        this.butOK.Location = new System.Drawing.Point(365, 291);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 9;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Store Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textStoreName
        //
        this.textStoreName.Location = new System.Drawing.Point(160, 20);
        this.textStoreName.Name = "textStoreName";
        this.textStoreName.Size = new System.Drawing.Size(291, 20);
        this.textStoreName.TabIndex = 0;
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
        this.butDelete.Location = new System.Drawing.Point(27, 291);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(160, 120);
        this.textCity.MaxLength = 255;
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(155, 20);
        this.textCity.TabIndex = 5;
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(315, 120);
        this.textState.MaxLength = 255;
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(65, 20);
        this.textState.TabIndex = 6;
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(380, 120);
        this.textZip.MaxLength = 255;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(71, 20);
        this.textZip.TabIndex = 7;
        //
        // textAddress2
        //
        this.textAddress2.Location = new System.Drawing.Point(160, 100);
        this.textAddress2.MaxLength = 255;
        this.textAddress2.Name = "textAddress2";
        this.textAddress2.Size = new System.Drawing.Size(291, 20);
        this.textAddress2.TabIndex = 4;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(160, 80);
        this.textAddress.MaxLength = 255;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(291, 20);
        this.textAddress.TabIndex = 3;
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(160, 40);
        this.textPhone.MaxLength = 255;
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(157, 20);
        this.textPhone.TabIndex = 1;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(-5, 124);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(163, 15);
        this.label11.TabIndex = 105;
        this.label11.Text = "City, ST, Zip";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(7, 104);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(151, 17);
        this.label4.TabIndex = 103;
        this.label4.Text = "Address2";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 82);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(151, 17);
        this.label3.TabIndex = 101;
        this.label3.Text = "Address";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 43);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(151, 17);
        this.label2.TabIndex = 99;
        this.label2.Text = "Phone";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(320, 42);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(144, 18);
        this.label6.TabIndex = 110;
        this.label6.Text = "(###)###-####";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textFax
        //
        this.textFax.Location = new System.Drawing.Point(160, 60);
        this.textFax.MaxLength = 255;
        this.textFax.Name = "textFax";
        this.textFax.Size = new System.Drawing.Size(157, 20);
        this.textFax.TabIndex = 2;
        this.textFax.TextChanged += new System.EventHandler(this.textFax_TextChanged);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(8, 63);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(151, 17);
        this.label5.TabIndex = 112;
        this.label5.Text = "Fax";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(320, 62);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(144, 18);
        this.label7.TabIndex = 113;
        this.label7.Text = "(###)###-####";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(160, 146);
        this.textNote.MaxLength = 255;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(291, 107);
        this.textNote.TabIndex = 8;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(7, 150);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(151, 17);
        this.label8.TabIndex = 115;
        this.label8.Text = "Note";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormPharmacyEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(557, 335);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textFax);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label7);
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
        this.Controls.Add(this.textStoreName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label6);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPharmacyEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Pharmacy";
        this.Load += new System.EventHandler(this.FormPharmacyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPharmacyEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textStoreName.Text = PharmCur.StoreName;
        textPhone.Text = PharmCur.Phone;
        textFax.Text = PharmCur.Fax;
        textAddress.Text = PharmCur.Address;
        textAddress2.Text = PharmCur.Address2;
        textCity.Text = PharmCur.City;
        textState.Text = PharmCur.State;
        textZip.Text = PharmCur.Zip;
        textNote.Text = PharmCur.Note;
    }

    private void textPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textPhone.SelectionStart;
        int length = textPhone.Text.Length;
        textPhone.Text = TelephoneNumbers.AutoFormat(textPhone.Text);
        if (textPhone.Text.Length > length)
        {
            cursor++;
        }
         
        textPhone.SelectionStart = cursor;
    }

    private void textFax_TextChanged(Object sender, EventArgs e) throws Exception {
        int cursor = textFax.SelectionStart;
        int length = textFax.Text.Length;
        textFax.Text = TelephoneNumbers.AutoFormat(textFax.Text);
        if (textFax.Text.Length > length)
        {
            cursor++;
        }
         
        textFax.SelectionStart = cursor;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (PharmCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete this Pharmacy?"))
        {
            return ;
        }
         
        try
        {
            Pharmacies.deleteObject(PharmCur.PharmacyNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textStoreName.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Store name cannot be blank."));
            return ;
        }
         
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            if (!StringSupport.equals(textPhone.Text, "") && StringSupport.equals(TelephoneNumbers.FormatNumbersExactTen(textPhone.Text), ""))
            {
                MessageBox.Show(Lan.g(this,"Phone number must be in a 10-digit format."));
                return ;
            }
             
            if (!StringSupport.equals(textFax.Text, "") && StringSupport.equals(TelephoneNumbers.FormatNumbersExactTen(textFax.Text), ""))
            {
                MessageBox.Show(Lan.g(this,"Fax number must be in a 10-digit format."));
                return ;
            }
             
        }
         
        PharmCur.StoreName = textStoreName.Text;
        PharmCur.PharmID = "";
        PharmCur.Phone = textPhone.Text;
        PharmCur.Fax = textFax.Text;
        PharmCur.Address = textAddress.Text;
        PharmCur.Address2 = textAddress2.Text;
        PharmCur.City = textCity.Text;
        PharmCur.State = textState.Text;
        PharmCur.Zip = textZip.Text;
        PharmCur.Note = textNote.Text;
        try
        {
            if (PharmCur.getIsNew())
            {
                Pharmacies.insert(PharmCur);
            }
            else
            {
                Pharmacies.update(PharmCur);
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


