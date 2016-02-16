//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;

public class FormEhrPatientConfidentialPrefEdit  extends Form 
{

    public Patient PatCur;
    /**
    * A copy of the original patient object, as it was when this form was first opened.
    */
    private Patient PatOld;
    public FormEhrPatientConfidentialPrefEdit() throws Exception {
        initializeComponent();
    }

    private void formPatientConfidentialPrefEdit_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(ContactMethod.class).Length;i++)
        {
            comboConfidentialContact.Items.Add(Enum.GetNames(ContactMethod.class)[i]);
        }
        PatOld = PatCur.copy();
        comboConfidentialContact.SelectedIndex = ((Enum)PatCur.PreferContactConfidential).ordinal();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        PatCur.PreferContactConfidential = (ContactMethod)comboConfidentialContact.SelectedIndex;
        Patients.update(PatCur,PatOld);
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
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.comboConfidentialContact = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(345, 81);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 8;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(264, 81);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 9;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // comboConfidentialContact
        //
        this.comboConfidentialContact.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboConfidentialContact.FormattingEnabled = true;
        this.comboConfidentialContact.Location = new System.Drawing.Point(129, 33);
        this.comboConfidentialContact.Name = "comboConfidentialContact";
        this.comboConfidentialContact.Size = new System.Drawing.Size(213, 21);
        this.comboConfidentialContact.TabIndex = 13;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(19, 34);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(106, 17);
        this.label3.TabIndex = 14;
        this.label3.Text = "Confidential Contact";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormPatientConfidentialPrefEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(432, 112);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.comboConfidentialContact);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormPatientConfidentialPrefEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Confidential Pref Edit";
        this.Load += new System.EventHandler(this.FormPatientConfidentialPrefEdit_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.ComboBox comboConfidentialContact = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


