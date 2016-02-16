//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.FormProcCodes;
import OpenDental.FormRepeatChargeEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RepeatCharge;
import OpenDentBusiness.RepeatCharges;
import OpenDentBusiness.Security;
import OpenDentBusiness.TreatmentArea;

/**
* Summary description for FormBasicTemplate.
*/
public class FormRepeatChargeEdit  extends System.Windows.Forms.Form 
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
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDouble textChargeAmt;
    private OpenDental.ValidDate textDateStart;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateStop;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private CheckBox checkCopyNoteToProc = new CheckBox();
    private Label label8 = new Label();
    private Label label9 = new Label();
    private OpenDental.UI.Button butMaunal;
    private CheckBox checkCreatesClaim = new CheckBox();
    private CheckBox checkIsEnabled = new CheckBox();
    private TextBox textTotalAmount = new TextBox();
    private TextBox textNumOfCharges = new TextBox();
    private Label label10 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butCalculate;
    private RepeatCharge RepeatCur;
    /**
    * 
    */
    public FormRepeatChargeEdit(RepeatCharge repeatCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        RepeatCur = repeatCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRepeatChargeEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textCode = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textDesc = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.checkCopyNoteToProc = new System.Windows.Forms.CheckBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.checkCreatesClaim = new System.Windows.Forms.CheckBox();
        this.checkIsEnabled = new System.Windows.Forms.CheckBox();
        this.textTotalAmount = new System.Windows.Forms.TextBox();
        this.textNumOfCharges = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butCalculate = new OpenDental.UI.Button();
        this.butMaunal = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.textDateStop = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.textChargeAmt = new OpenDental.ValidDouble();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(4, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(156, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "Code";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(162, 17);
        this.textCode.MaxLength = 15;
        this.textCode.Name = "textCode";
        this.textCode.ReadOnly = true;
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(4, 139);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(156, 16);
        this.label2.TabIndex = 4;
        this.label2.Text = "Charge Amount";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 168);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(156, 16);
        this.label3.TabIndex = 7;
        this.label3.Text = "Date Start";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(4, 196);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(156, 16);
        this.label4.TabIndex = 9;
        this.label4.Text = "Date Stop";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(162, 260);
        this.textNote.MaxLength = 10000;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(424, 114);
        this.textNote.TabIndex = 11;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(4, 263);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(156, 16);
        this.label5.TabIndex = 10;
        this.label5.Text = "Note";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDesc
        //
        this.textDesc.BackColor = System.Drawing.SystemColors.Control;
        this.textDesc.Location = new System.Drawing.Point(267, 17);
        this.textDesc.Name = "textDesc";
        this.textDesc.Size = new System.Drawing.Size(241, 20);
        this.textDesc.TabIndex = 40;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(265, 1);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(224, 16);
        this.label6.TabIndex = 39;
        this.label6.Text = "Procedure Description:";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label7.Location = new System.Drawing.Point(128, 491);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(238, 29);
        this.label7.TabIndex = 42;
        this.label7.Text = "It\'s OK to delete an obsolete repeating charge.   It does not affect any charges " + "already billed.";
        this.label7.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkCopyNoteToProc
        //
        this.checkCopyNoteToProc.Location = new System.Drawing.Point(162, 378);
        this.checkCopyNoteToProc.Name = "checkCopyNoteToProc";
        this.checkCopyNoteToProc.Size = new System.Drawing.Size(250, 18);
        this.checkCopyNoteToProc.TabIndex = 43;
        this.checkCopyNoteToProc.Text = "Copy note to procedure billing note.";
        this.checkCopyNoteToProc.UseVisualStyleBackColor = true;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(17, 22);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(136, 16);
        this.label8.TabIndex = 44;
        this.label8.Text = "Total Amount";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(17, 48);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(136, 16);
        this.label9.TabIndex = 46;
        this.label9.Text = "Number of Charges";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkCreatesClaim
        //
        this.checkCreatesClaim.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkCreatesClaim.Location = new System.Drawing.Point(7, 218);
        this.checkCreatesClaim.Name = "checkCreatesClaim";
        this.checkCreatesClaim.Size = new System.Drawing.Size(169, 18);
        this.checkCreatesClaim.TabIndex = 49;
        this.checkCreatesClaim.Text = "Creates Claim";
        this.checkCreatesClaim.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkCreatesClaim.UseVisualStyleBackColor = true;
        //
        // checkIsEnabled
        //
        this.checkIsEnabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsEnabled.Location = new System.Drawing.Point(7, 239);
        this.checkIsEnabled.Name = "checkIsEnabled";
        this.checkIsEnabled.Size = new System.Drawing.Size(169, 18);
        this.checkIsEnabled.TabIndex = 50;
        this.checkIsEnabled.Text = "Enabled";
        this.checkIsEnabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsEnabled.UseVisualStyleBackColor = true;
        //
        // textTotalAmount
        //
        this.textTotalAmount.Location = new System.Drawing.Point(155, 19);
        this.textTotalAmount.Name = "textTotalAmount";
        this.textTotalAmount.Size = new System.Drawing.Size(100, 20);
        this.textTotalAmount.TabIndex = 51;
        //
        // textNumOfCharges
        //
        this.textNumOfCharges.Location = new System.Drawing.Point(155, 45);
        this.textNumOfCharges.Name = "textNumOfCharges";
        this.textNumOfCharges.Size = new System.Drawing.Size(100, 20);
        this.textNumOfCharges.TabIndex = 52;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(245, 419);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(225, 29);
        this.label10.TabIndex = 53;
        this.label10.Text = "This will add a completed procedure of the code listed above to this patient\'s ac" + "count.";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butCalculate);
        this.groupBox1.Controls.Add(this.textTotalAmount);
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.textNumOfCharges);
        this.groupBox1.Controls.Add(this.label9);
        this.groupBox1.Location = new System.Drawing.Point(7, 48);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(359, 79);
        this.groupBox1.TabIndex = 54;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Calculate Charge Amount (optional)";
        //
        // butCalculate
        //
        this.butCalculate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCalculate.setAutosize(true);
        this.butCalculate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCalculate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCalculate.setCornerRadius(4F);
        this.butCalculate.Location = new System.Drawing.Point(261, 44);
        this.butCalculate.Name = "butCalculate";
        this.butCalculate.Size = new System.Drawing.Size(75, 24);
        this.butCalculate.TabIndex = 53;
        this.butCalculate.Text = "Calculate";
        this.butCalculate.Click += new System.EventHandler(this.butCalculate_Click);
        //
        // butMaunal
        //
        this.butMaunal.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMaunal.setAutosize(true);
        this.butMaunal.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMaunal.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMaunal.setCornerRadius(4F);
        this.butMaunal.Location = new System.Drawing.Point(162, 421);
        this.butMaunal.Name = "butMaunal";
        this.butMaunal.Size = new System.Drawing.Size(75, 24);
        this.butMaunal.TabIndex = 48;
        this.butMaunal.Text = "Manual";
        this.butMaunal.Click += new System.EventHandler(this.butMaunal_Click);
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
        this.butDelete.Location = new System.Drawing.Point(35, 493);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(83, 26);
        this.butDelete.TabIndex = 41;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(162, 194);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.Size = new System.Drawing.Size(100, 20);
        this.textDateStop.TabIndex = 8;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(162, 165);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(100, 20);
        this.textDateStart.TabIndex = 6;
        //
        // textChargeAmt
        //
        this.textChargeAmt.Location = new System.Drawing.Point(162, 136);
        this.textChargeAmt.Name = "textChargeAmt";
        this.textChargeAmt.Size = new System.Drawing.Size(100, 20);
        this.textChargeAmt.TabIndex = 5;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(595, 450);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(595, 491);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormRepeatChargeEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(705, 545);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.checkIsEnabled);
        this.Controls.Add(this.checkCreatesClaim);
        this.Controls.Add(this.butMaunal);
        this.Controls.Add(this.checkCopyNoteToProc);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDesc);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textDateStop);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.textChargeAmt);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRepeatChargeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Repeat Charge";
        this.Load += new System.EventHandler(this.FormRepeatChargeEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRepeatChargeEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //Set the title bar to show the patient's name much like the main screen does.
        this.Text += " - " + Patients.getLim(RepeatCur.PatNum).getNameLF();
        if (IsNew)
        {
            FormProcCodes FormP = new FormProcCodes();
            FormP.IsSelectionMode = true;
            FormP.ShowDialog();
            if (FormP.DialogResult != DialogResult.OK)
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            ProcedureCode procCode = ProcedureCodes.getProcCode(FormP.SelectedCodeNum);
            if (procCode.TreatArea != TreatmentArea.Mouth)
            {
                MsgBox.show(this,"Procedure codes that require tooth numbers are not allowed.");
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            RepeatCur.ProcCode = ProcedureCodes.getStringProcCode(FormP.SelectedCodeNum);
            RepeatCur.IsEnabled = true;
            RepeatCur.CreatesClaim = false;
        }
         
        textCode.Text = RepeatCur.ProcCode;
        textDesc.Text = ProcedureCodes.getProcCode(RepeatCur.ProcCode).Descript;
        textChargeAmt.Text = RepeatCur.ChargeAmt.ToString("F");
        if (RepeatCur.DateStart.Year > 1880)
        {
            textDateStart.Text = RepeatCur.DateStart.ToShortDateString();
        }
         
        if (RepeatCur.DateStop.Year > 1880)
        {
            textDateStop.Text = RepeatCur.DateStop.ToShortDateString();
        }
         
        textNote.Text = RepeatCur.Note;
        checkCopyNoteToProc.Checked = RepeatCur.CopyNoteToProc;
        checkCreatesClaim.Checked = RepeatCur.CreatesClaim;
        checkIsEnabled.Checked = RepeatCur.IsEnabled;
        if (PrefC.getBool(PrefName.DistributorKey))
        {
            //OD HQ disable the IsEnabled and CreatesClaim checkboxes
            checkCreatesClaim.Enabled = false;
            checkIsEnabled.Enabled = false;
        }
         
    }

    private void butMaunal_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.ProcComplCreate))
        {
            return ;
        }
         
        if (textChargeAmt.Text == null || StringSupport.equals(textChargeAmt.Text, ""))
        {
            MsgBox.show(this,"You must first enter a charge amount.");
            return ;
        }
         
        double procFee = new double();
        try
        {
            procFee = Double.Parse(textChargeAmt.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Invalid charge amount.");
            return ;
        }

        Procedures.SetDateFirstVisit(DateTime.Today, 1, Patients.getPat(RepeatCur.PatNum));
        Procedure proc = new Procedure();
        proc.CodeNum = ProcedureCodes.GetCodeNum(textCode.Text);
        proc.DateEntryC = DateTimeOD.getToday();
        proc.PatNum = RepeatCur.PatNum;
        proc.ProcDate = DateTimeOD.getToday();
        proc.DateTP = DateTimeOD.getToday();
        proc.ProcFee = procFee;
        proc.ProcStatus = ProcStat.C;
        proc.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        proc.MedicalCode = ProcedureCodes.getProcCode(proc.CodeNum).MedicalCode;
        proc.BaseUnits = ProcedureCodes.getProcCode(proc.CodeNum).BaseUnits;
        proc.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
        //Check if the repeating charge has been flagged to copy it's note into the billing note of the procedure.
        if (RepeatCur.CopyNoteToProc)
        {
            proc.BillingNote = RepeatCur.Note;
        }
         
        Procedures.insert(proc);
        Recalls.synch(RepeatCur.PatNum);
        MsgBox.show(this,"Procedure added.");
    }

    private void butCalculate_Click(Object sender, EventArgs e) throws Exception {
        if (PIn.Double(textNumOfCharges.Text) == 0 || PIn.Double(textTotalAmount.Text) == 0)
        {
            textChargeAmt.Text = RepeatCur.ChargeAmt.ToString("F");
            return ;
        }
         
        textChargeAmt.Text = (PIn.Double(textTotalAmount.Text) / PIn.Double(textNumOfCharges.Text)).ToString("F");
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        RepeatCharges.delete(RepeatCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textChargeAmt.errorProvider1.GetError(textChargeAmt), "") || !StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDateStart.Text, ""))
        {
            MsgBox.show(this,"Start date cannot be left blank.");
            return ;
        }
         
        if (PIn.Date(textDateStart.Text) != RepeatCur.DateStart)
        {
            //if the user changed the date
            if (PIn.Date(textDateStart.Text) < DateTime.Today.AddDays(-3))
            {
                //and if the date the user entered is more than three days in the past
                MsgBox.show(this,"Start date cannot be more than three days in the past.  You should enter previous charges manually in the account.");
                return ;
            }
             
        }
         
        RepeatCur.ProcCode = textCode.Text;
        RepeatCur.ChargeAmt = PIn.Double(textChargeAmt.Text);
        RepeatCur.DateStart = PIn.Date(textDateStart.Text);
        RepeatCur.DateStop = PIn.Date(textDateStop.Text);
        RepeatCur.Note = textNote.Text;
        RepeatCur.CopyNoteToProc = checkCopyNoteToProc.Checked;
        RepeatCur.IsEnabled = checkIsEnabled.Checked;
        RepeatCur.CreatesClaim = checkCreatesClaim.Checked;
        if (IsNew)
        {
            RepeatCharges.insert(RepeatCur);
        }
        else
        {
            RepeatCharges.update(RepeatCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


