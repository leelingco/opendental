//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Programs;
import OpenDental.FormPatientMerge;

public class FormPatientMerge  extends Form 
{

    public FormPatientMerge() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPatientMerge_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butChangePatientInto_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect fps = new FormPatientSelect();
        if (fps.ShowDialog() == DialogResult.OK)
        {
            long selectedPatNum = fps.SelectedPatNum;
            //to prevent warning about marshal-by-reference
            this.textPatientIDInto.Text = selectedPatNum.ToString();
            Patient pat = Patients.getPat(selectedPatNum);
            this.textPatientNameInto.Text = pat.getNameFLFormal();
            this.textPatToBirthdate.Text = pat.Birthdate.ToShortDateString();
        }
         
        checkUIState();
    }

    private void butChangePatientFrom_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect fps = new FormPatientSelect();
        if (fps.ShowDialog() == DialogResult.OK)
        {
            long selectedPatNum = fps.SelectedPatNum;
            //to prevent warning about marshal-by-reference
            this.textPatientIDFrom.Text = selectedPatNum.ToString();
            Patient pat = Patients.getPat(selectedPatNum);
            this.textPatientNameFrom.Text = pat.getNameFLFormal();
            this.textPatFromBirthdate.Text = pat.Birthdate.ToShortDateString();
        }
         
        checkUIState();
    }

    private void checkUIState() throws Exception {
        this.butMerge.Enabled = (!StringSupport.equals(this.textPatientIDInto.Text.Trim(), "") && !StringSupport.equals(this.textPatientIDFrom.Text.Trim(), ""));
    }

    private void butMerge_Click(Object sender, EventArgs e) throws Exception {
        long patTo = Convert.ToInt64(this.textPatientIDInto.Text.Trim());
        long patFrom = Convert.ToInt64(this.textPatientIDFrom.Text.Trim());
        if (patTo == patFrom)
        {
            MsgBox.show(this,"Cannot merge a patient account into itself. Please select a different patient to merge from.");
            return ;
        }
         
        Patient patientFrom = Patients.getPat(patFrom);
        Patient patientTo = Patients.getPat(patTo);
        if (patientFrom.FName.Trim().ToLower() != patientTo.FName.Trim().ToLower() || patientFrom.LName.Trim().ToLower() != patientTo.LName.Trim().ToLower() || patientFrom.Birthdate != patientTo.Birthdate)
        {
            //mismatch
            if (Programs.usingEcwTightOrFullMode())
            {
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"The two selected patients do not have the same first name, last name, and date of birth.  The patients must first be merged from within eCW, then immediately merged in the same order in Open Dental.  If the patients are not merged in this manner, some information may not properly bridge between eCW and Open Dental.\r\n" + 
                "Into patient name: " + patientTo.FName + " " + patientTo.LName + ", Into patient birthdate: " + patientTo.Birthdate.ToShortDateString() + ".\r\n" + 
                "From patient name: " + patientFrom.FName + " " + patientFrom.LName + ", From paient birthdate: " + patientFrom.Birthdate.ToShortDateString() + ".\r\n" + 
                "Merge the patient at the bottom into the patient shown at the top?"))
                {
                    return ;
                }
                 
            }
            else
            {
                //The user chose not to merge
                //not eCW
                MsgBox.show(this,"The two selected patients do not have the same first name, last name, and date of birth.  You must set all of those the same before merge is allowed.");
                return ;
            } 
        }
        else
        {
            //Do not merge.
            //name and bd match
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Merge the patient at the bottom into the patient shown at the top?"))
            {
                return ;
            }
             
        } 
        //The user chose not to merge.
        this.Cursor = Cursors.WaitCursor;
        if (patientFrom.PatNum == patientFrom.Guarantor)
        {
            Family fam = Patients.getFamily(patFrom);
            if (fam.ListPats.Length > 1)
            {
                this.Cursor = Cursors.Default;
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"The patient you have chosen to merge from is a guarantor. Merging this patient into another account will " + "cause all familiy members of the patient being merged from to be moved into the same family as the " + "patient account being merged into. Do you wish to continue with the merge?"))
                {
                    return ;
                }
                 
                //The user chose not to merge.
                this.Cursor = Cursors.WaitCursor;
            }
             
        }
         
        if (Patients.mergeTwoPatients(patTo,patFrom,ImageStore.getPreferredAtoZpath()))
        {
            this.textPatientIDFrom.Text = "";
            this.textPatientNameFrom.Text = "";
            this.textPatFromBirthdate.Text = "";
            checkUIState();
            MsgBox.show(this,"Patients merged successfully.");
        }
         
        this.Cursor = Cursors.Default;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPatientMerge.class);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textPatientNameInto = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textPatientIDInto = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textPatientNameFrom = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textPatientIDFrom = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textPatToBirthdate = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textPatFromBirthdate = new System.Windows.Forms.TextBox();
        this.butChangePatientFrom = new OpenDental.UI.Button();
        this.butChangePatientInto = new OpenDental.UI.Button();
        this.butMerge = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textPatToBirthdate);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.butChangePatientInto);
        this.groupBox1.Controls.Add(this.textPatientNameInto);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.textPatientIDInto);
        this.groupBox1.Location = new System.Drawing.Point(12, 12);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(638, 70);
        this.groupBox1.TabIndex = 4;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Patient to merge into. The patient chosen below will be merged into this account." + "";
        //
        // textPatientNameInto
        //
        this.textPatientNameInto.Location = new System.Drawing.Point(153, 37);
        this.textPatientNameInto.Name = "textPatientNameInto";
        this.textPatientNameInto.ReadOnly = true;
        this.textPatientNameInto.Size = new System.Drawing.Size(237, 20);
        this.textPatientNameInto.TabIndex = 3;
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(150, 18);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(71, 13);
        this.label2.TabIndex = 2;
        this.label2.Text = "Patient Name";
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(7, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(54, 13);
        this.label1.TabIndex = 1;
        this.label1.Text = "Patient ID";
        //
        // textPatientIDInto
        //
        this.textPatientIDInto.Location = new System.Drawing.Point(6, 37);
        this.textPatientIDInto.Name = "textPatientIDInto";
        this.textPatientIDInto.ReadOnly = true;
        this.textPatientIDInto.Size = new System.Drawing.Size(141, 20);
        this.textPatientIDInto.TabIndex = 0;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textPatFromBirthdate);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.butChangePatientFrom);
        this.groupBox2.Controls.Add(this.textPatientNameFrom);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.textPatientIDFrom);
        this.groupBox2.Location = new System.Drawing.Point(12, 88);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(638, 76);
        this.groupBox2.TabIndex = 5;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Patient to merge from. This account will be merged into the account above. This a" + "ccount will be archived if not marked deceased.";
        //
        // textPatientNameFrom
        //
        this.textPatientNameFrom.Location = new System.Drawing.Point(153, 37);
        this.textPatientNameFrom.Name = "textPatientNameFrom";
        this.textPatientNameFrom.ReadOnly = true;
        this.textPatientNameFrom.Size = new System.Drawing.Size(237, 20);
        this.textPatientNameFrom.TabIndex = 8;
        //
        // label3
        //
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(150, 18);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(71, 13);
        this.label3.TabIndex = 7;
        this.label3.Text = "Patient Name";
        //
        // label4
        //
        this.label4.AutoSize = true;
        this.label4.Location = new System.Drawing.Point(7, 18);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(54, 13);
        this.label4.TabIndex = 6;
        this.label4.Text = "Patient ID";
        //
        // textPatientIDFrom
        //
        this.textPatientIDFrom.Location = new System.Drawing.Point(6, 37);
        this.textPatientIDFrom.Name = "textPatientIDFrom";
        this.textPatientIDFrom.ReadOnly = true;
        this.textPatientIDFrom.Size = new System.Drawing.Size(141, 20);
        this.textPatientIDFrom.TabIndex = 5;
        //
        // label5
        //
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(393, 18);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(85, 13);
        this.label5.TabIndex = 5;
        this.label5.Text = "Patient Birthdate";
        //
        // textPatToBirthdate
        //
        this.textPatToBirthdate.Location = new System.Drawing.Point(396, 37);
        this.textPatToBirthdate.Name = "textPatToBirthdate";
        this.textPatToBirthdate.ReadOnly = true;
        this.textPatToBirthdate.Size = new System.Drawing.Size(126, 20);
        this.textPatToBirthdate.TabIndex = 6;
        //
        // label6
        //
        this.label6.AutoSize = true;
        this.label6.Location = new System.Drawing.Point(396, 20);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(85, 13);
        this.label6.TabIndex = 10;
        this.label6.Text = "Patient Birthdate";
        //
        // textPatFromBirthdate
        //
        this.textPatFromBirthdate.Location = new System.Drawing.Point(396, 37);
        this.textPatFromBirthdate.Name = "textPatFromBirthdate";
        this.textPatFromBirthdate.ReadOnly = true;
        this.textPatFromBirthdate.Size = new System.Drawing.Size(126, 20);
        this.textPatFromBirthdate.TabIndex = 11;
        //
        // butChangePatientFrom
        //
        this.butChangePatientFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangePatientFrom.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butChangePatientFrom.setAutosize(true);
        this.butChangePatientFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangePatientFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangePatientFrom.setCornerRadius(4F);
        this.butChangePatientFrom.Location = new System.Drawing.Point(550, 34);
        this.butChangePatientFrom.Name = "butChangePatientFrom";
        this.butChangePatientFrom.Size = new System.Drawing.Size(75, 24);
        this.butChangePatientFrom.TabIndex = 9;
        this.butChangePatientFrom.Text = "Change";
        this.butChangePatientFrom.Click += new System.EventHandler(this.butChangePatientFrom_Click);
        //
        // butChangePatientInto
        //
        this.butChangePatientInto.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangePatientInto.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butChangePatientInto.setAutosize(true);
        this.butChangePatientInto.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangePatientInto.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangePatientInto.setCornerRadius(4F);
        this.butChangePatientInto.Location = new System.Drawing.Point(550, 34);
        this.butChangePatientInto.Name = "butChangePatientInto";
        this.butChangePatientInto.Size = new System.Drawing.Size(75, 24);
        this.butChangePatientInto.TabIndex = 4;
        this.butChangePatientInto.Text = "Change";
        this.butChangePatientInto.Click += new System.EventHandler(this.butChangePatientInto_Click);
        //
        // butMerge
        //
        this.butMerge.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMerge.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butMerge.setAutosize(true);
        this.butMerge.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMerge.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMerge.setCornerRadius(4F);
        this.butMerge.Enabled = false;
        this.butMerge.Location = new System.Drawing.Point(562, 170);
        this.butMerge.Name = "butMerge";
        this.butMerge.Size = new System.Drawing.Size(75, 24);
        this.butMerge.TabIndex = 3;
        this.butMerge.Text = "Merge";
        this.butMerge.Click += new System.EventHandler(this.butMerge_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(562, 211);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPatientMerge
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(663, 265);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butMerge);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPatientMerge";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Merge Patients";
        this.Load += new System.EventHandler(this.FormPatientMerge_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butMerge;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textPatientNameInto = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatientIDInto = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butChangePatientInto;
    private OpenDental.UI.Button butChangePatientFrom;
    private System.Windows.Forms.TextBox textPatientNameFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatientIDFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPatToBirthdate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatFromBirthdate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
}


