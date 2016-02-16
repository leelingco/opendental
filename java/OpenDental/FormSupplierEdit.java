//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Supplier;
import OpenDentBusiness.Suppliers;
import OpenDental.Properties.Resources;

public class FormSupplierEdit  extends Form 
{

    public Supplier Supp;
    public FormSupplierEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplierEdit_Load(Object sender, EventArgs e) throws Exception {
        textName.Text = Supp.Name;
        textPhone.Text = Supp.Phone;
        textCustomerId.Text = Supp.CustomerId;
        textWebsite.Text = Supp.Website;
        textUserName.Text = Supp.UserName;
        textPassword.Text = Supp.Password;
        textNote.Text = Supp.Note;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (Supp.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        try
        {
            Suppliers.deleteObject(Supp);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //if(textDate.errorProvider1.GetError(textDate)!=""){
        //	MsgBox.Show(this,"Please fix data entry errors first.");
        //	return;
        //}
        if (StringSupport.equals(textName.Text, ""))
        {
            MsgBox.show(this,"Please enter a name.");
            return ;
        }
         
        Supp.Name = textName.Text;
        Supp.Phone = textPhone.Text;
        Supp.CustomerId = textCustomerId.Text;
        Supp.Website = textWebsite.Text;
        Supp.UserName = textUserName.Text;
        Supp.Password = textPassword.Text;
        Supp.Note = textNote.Text;
        if (Supp.getIsNew())
        {
            Suppliers.insert(Supp);
        }
        else
        {
            Suppliers.update(Supp);
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
        this.textName = new System.Windows.Forms.TextBox();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textCustomerId = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textWebsite = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textUserName = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(3, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(132, 18);
        this.label1.TabIndex = 4;
        this.label1.Text = "Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(136, 8);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(401, 20);
        this.textName.TabIndex = 0;
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(136, 34);
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(144, 20);
        this.textPhone.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(3, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(132, 18);
        this.label2.TabIndex = 8;
        this.label2.Text = "Phone";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCustomerId
        //
        this.textCustomerId.Location = new System.Drawing.Point(136, 60);
        this.textCustomerId.Name = "textCustomerId";
        this.textCustomerId.Size = new System.Drawing.Size(144, 20);
        this.textCustomerId.TabIndex = 2;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 61);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(132, 18);
        this.label3.TabIndex = 10;
        this.label3.Text = "Customer Id";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textWebsite
        //
        this.textWebsite.Location = new System.Drawing.Point(136, 86);
        this.textWebsite.Name = "textWebsite";
        this.textWebsite.Size = new System.Drawing.Size(401, 20);
        this.textWebsite.TabIndex = 3;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(3, 87);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(132, 18);
        this.label4.TabIndex = 12;
        this.label4.Text = "Website";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUserName
        //
        this.textUserName.Location = new System.Drawing.Point(136, 112);
        this.textUserName.Name = "textUserName";
        this.textUserName.Size = new System.Drawing.Size(144, 20);
        this.textUserName.TabIndex = 4;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(3, 113);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(132, 18);
        this.label5.TabIndex = 14;
        this.label5.Text = "User Name";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(136, 138);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(144, 20);
        this.textPassword.TabIndex = 5;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 139);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(132, 18);
        this.label6.TabIndex = 16;
        this.label6.Text = "Password";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(136, 164);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(401, 60);
        this.textNote.TabIndex = 6;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(3, 165);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(132, 18);
        this.label7.TabIndex = 18;
        this.label7.Text = "Note";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butDelete.Location = new System.Drawing.Point(27, 302);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 6;
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
        this.butOK.Location = new System.Drawing.Point(578, 261);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 7;
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
        this.butCancel.Location = new System.Drawing.Point(578, 302);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSupplierEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(678, 353);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textUserName);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textWebsite);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCustomerId);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textPhone);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSupplierEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supplier";
        this.Load += new System.EventHandler(this.FormSupplierEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCustomerId = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textWebsite = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUserName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
}


