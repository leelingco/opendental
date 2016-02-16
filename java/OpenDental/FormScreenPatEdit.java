//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.ScreenGroup;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ScreenPat;
import OpenDentBusiness.ScreenPats;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;

public class FormScreenPatEdit  extends Form 
{

    public ScreenPat ScreenPatCur;
    private Patient PatCur;
    public ScreenGroup ScreenGroupCur = new ScreenGroup();
    private SheetDef ExamSheetDefCur;
    public boolean IsNew = new boolean();
    public FormScreenPatEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formScreenPatEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            ScreenPatCur.SheetNum = PrefC.getLong(PrefName.PublicHealthScreeningSheet);
        }
         
        PatCur = Patients.getPat(ScreenPatCur.PatNum);
        if (PatCur != null)
        {
            textPatient.Text = PatCur.getNameLF();
        }
         
        if (ScreenGroupCur != null)
        {
            textScreenGroup.Text = ScreenGroupCur.Description;
        }
         
        ExamSheetDefCur = SheetDefs.getSheetDef(ScreenPatCur.SheetNum);
        if (ExamSheetDefCur != null)
        {
            textSheet.Text = ExamSheetDefCur.Description;
        }
         
    }

    /*private void butPatSelect_Click(object sender,EventArgs e) {
    			FormPatientSelect FormPS=new FormPatientSelect();
    			FormPS.ShowDialog();
    			if(FormPS.DialogResult!=DialogResult.OK) {
    				return;
    			}
    			PatCur=Patients.GetPat(FormPS.SelectedPatNum);
    			ScreenPatCur.PatNum=PatCur.PatNum;
    			textPatient.Text=PatCur.GetNameLF();
    		}*/
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            ScreenPats.insert(ScreenPatCur);
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
        this.textPatient = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textScreenGroup = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textSheet = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(130, 80);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(190, 20);
        this.textPatient.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(58, 82);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(66, 14);
        this.label1.TabIndex = 5;
        this.label1.Text = "Patient";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textScreenGroup
        //
        this.textScreenGroup.Location = new System.Drawing.Point(130, 28);
        this.textScreenGroup.Name = "textScreenGroup";
        this.textScreenGroup.ReadOnly = true;
        this.textScreenGroup.Size = new System.Drawing.Size(190, 20);
        this.textScreenGroup.TabIndex = 4;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 31);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 14);
        this.label2.TabIndex = 5;
        this.label2.Text = "Screening Group";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(13, 57);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(111, 14);
        this.label3.TabIndex = 5;
        this.label3.Text = "Screening Sheet";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSheet
        //
        this.textSheet.Location = new System.Drawing.Point(130, 54);
        this.textSheet.Name = "textSheet";
        this.textSheet.ReadOnly = true;
        this.textSheet.Size = new System.Drawing.Size(190, 20);
        this.textSheet.TabIndex = 4;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(233, 125);
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
        this.butCancel.Location = new System.Drawing.Point(323, 125);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormScreenPatEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(421, 170);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSheet);
        this.Controls.Add(this.textScreenGroup);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormScreenPatEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Screening Patient Edit";
        this.Load += new System.EventHandler(this.FormScreenPatEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textScreenGroup = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSheet = new System.Windows.Forms.TextBox();
}


