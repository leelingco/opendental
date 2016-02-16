//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:49 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCarriers;
import OpenDental.FormClaimPayEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/**
* 
*/
public class FormClaimPayEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.ValidDouble textAmount;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.TextBox textBankBranch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCheckNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCarrierName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private ClaimPayment ClaimPaymentCur;
    private Label labelDateIssued = new Label();
    private OpenDental.ValidDate textDateIssued;
    private OpenDental.UI.Button butCarrierSelect;
    private Label label8 = new Label();
    private Label label1 = new Label();
    /**
    * 
    */
    public FormClaimPayEdit(ClaimPayment claimPaymentCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        ClaimPaymentCur = claimPaymentCur;
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimPayEdit.class);
        this.textAmount = new OpenDental.ValidDouble();
        this.textDate = new OpenDental.ValidDate();
        this.textBankBranch = new System.Windows.Forms.TextBox();
        this.textCheckNum = new System.Windows.Forms.TextBox();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.textCarrierName = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.labelDateIssued = new System.Windows.Forms.Label();
        this.textDateIssued = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.butCarrierSelect = new OpenDental.UI.Button();
        this.label8 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(134, 86);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(68, 20);
        this.textAmount.TabIndex = 0;
        this.textAmount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(134, 44);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(68, 20);
        this.textDate.TabIndex = 6;
        this.textDate.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textBankBranch
        //
        this.textBankBranch.Location = new System.Drawing.Point(134, 128);
        this.textBankBranch.MaxLength = 25;
        this.textBankBranch.Name = "textBankBranch";
        this.textBankBranch.Size = new System.Drawing.Size(100, 20);
        this.textBankBranch.TabIndex = 2;
        //
        // textCheckNum
        //
        this.textCheckNum.Location = new System.Drawing.Point(134, 107);
        this.textCheckNum.MaxLength = 25;
        this.textCheckNum.Name = "textCheckNum";
        this.textCheckNum.Size = new System.Drawing.Size(100, 20);
        this.textCheckNum.TabIndex = 1;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(134, 198);
        this.textNote.MaxLength = 255;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(324, 70);
        this.textNote.TabIndex = 4;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(35, 48);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(96, 16);
        this.label6.TabIndex = 37;
        this.label6.Text = "Payment Date";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(37, 90);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(95, 16);
        this.label5.TabIndex = 36;
        this.label5.Text = "Amount";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(41, 109);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(90, 16);
        this.label4.TabIndex = 35;
        this.label4.Text = "Check #";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(42, 131);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(91, 16);
        this.label3.TabIndex = 34;
        this.label3.Text = "Bank-Branch";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 199);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(120, 16);
        this.label2.TabIndex = 33;
        this.label2.Text = "Note";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(430, 302);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
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
        this.butOK.Location = new System.Drawing.Point(339, 302);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(134, 21);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(209, 21);
        this.comboClinic.TabIndex = 0;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(44, 25);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(86, 14);
        this.labelClinic.TabIndex = 91;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCarrierName
        //
        this.textCarrierName.Location = new System.Drawing.Point(134, 149);
        this.textCarrierName.MaxLength = 25;
        this.textCarrierName.Name = "textCarrierName";
        this.textCarrierName.Size = new System.Drawing.Size(252, 20);
        this.textCarrierName.TabIndex = 3;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(24, 152);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(109, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Carrier Name";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelDateIssued
        //
        this.labelDateIssued.Location = new System.Drawing.Point(12, 69);
        this.labelDateIssued.Name = "labelDateIssued";
        this.labelDateIssued.Size = new System.Drawing.Size(120, 16);
        this.labelDateIssued.TabIndex = 37;
        this.labelDateIssued.Text = "Date Check Issued";
        this.labelDateIssued.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateIssued
        //
        this.textDateIssued.Location = new System.Drawing.Point(134, 65);
        this.textDateIssued.Name = "textDateIssued";
        this.textDateIssued.Size = new System.Drawing.Size(68, 20);
        this.textDateIssued.TabIndex = 7;
        this.textDateIssued.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(203, 66);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(94, 16);
        this.label1.TabIndex = 95;
        this.label1.Text = "(optional)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butCarrierSelect
        //
        this.butCarrierSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCarrierSelect.setAutosize(true);
        this.butCarrierSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCarrierSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCarrierSelect.setCornerRadius(4F);
        this.butCarrierSelect.Location = new System.Drawing.Point(389, 147);
        this.butCarrierSelect.Name = "butCarrierSelect";
        this.butCarrierSelect.Size = new System.Drawing.Size(69, 23);
        this.butCarrierSelect.TabIndex = 8;
        this.butCarrierSelect.Text = "Pick";
        this.butCarrierSelect.Click += new System.EventHandler(this.butCarrierSelect_Click);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(131, 172);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(200, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "(does not need to be exact)";
        //
        // FormClaimPayEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(525, 346);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCarrierName);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.textDateIssued);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.textBankBranch);
        this.Controls.Add(this.textCheckNum);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.labelDateIssued);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butCarrierSelect);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimPayEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Insurance Payment";
        this.Load += new System.EventHandler(this.FormClaimPayEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimPayEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //ClaimPayment gets inserted into db when OK in this form if new
        if (IsNew)
        {
        }
        else
        {
            //security already checked before this form opens
            textCheckNum.Select();
            //If new, then the amount would have been selected.
            if (!Security.isAuthorized(Permissions.InsPayEdit,ClaimPaymentCur.CheckDate))
            {
                butOK.Enabled = false;
            }
             
        } 
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("None");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == ClaimPaymentCur.ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        if (ClaimPaymentCur.CheckDate.Year > 1880)
        {
            textDate.Text = ClaimPaymentCur.CheckDate.ToShortDateString();
        }
         
        if (ClaimPaymentCur.DateIssued.Year > 1880)
        {
            textDateIssued.Text = ClaimPaymentCur.DateIssued.ToShortDateString();
        }
         
        textCheckNum.Text = ClaimPaymentCur.CheckNum;
        textBankBranch.Text = ClaimPaymentCur.BankBranch;
        textAmount.Text = ClaimPaymentCur.CheckAmt.ToString("F");
        textCarrierName.Text = ClaimPaymentCur.CarrierName;
        textNote.Text = ClaimPaymentCur.Note;
    }

    private void butCarrierSelect_Click(Object sender, EventArgs e) throws Exception {
        FormCarriers formC = new FormCarriers();
        formC.IsSelectMode = true;
        formC.ShowDialog();
        if (formC.DialogResult == DialogResult.OK)
        {
            textCarrierName.Text = formC.SelectedCarrier.CarrierName;
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDate.Text, ""))
        {
            MsgBox.show(this,"Please enter a date.");
            return ;
        }
         
        if (StringSupport.equals(textCarrierName.Text, ""))
        {
            MsgBox.show(this,"Please enter a carrier.");
            return ;
        }
         
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), "") || !StringSupport.equals(textDateIssued.errorProvider1.GetError(textDateIssued), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textAmount.Text, ""))
        {
            MsgBox.show(this,"Please enter an amount.");
            return ;
        }
         
        if (IsNew)
        {
            //prevents backdating of initial check
            if (!Security.IsAuthorized(Permissions.InsPayCreate, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        }
        else
        {
            //prevents attaching claimprocs with a date that is older than allowed by security.
            //Editing an old entry will already be blocked if the date was too old, and user will not be able to click OK button.
            //This catches it if user changed the date to be older.
            if (!Security.IsAuthorized(Permissions.InsPayEdit, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        } 
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            if (comboClinic.SelectedIndex == 0)
            {
                ClaimPaymentCur.ClinicNum = 0;
            }
            else
            {
                ClaimPaymentCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
            } 
        }
         
        ClaimPaymentCur.CheckDate = PIn.Date(textDate.Text);
        ClaimPaymentCur.DateIssued = PIn.Date(textDateIssued.Text);
        ClaimPaymentCur.CheckAmt = PIn.Double(textAmount.Text);
        ClaimPaymentCur.CheckNum = textCheckNum.Text;
        ClaimPaymentCur.BankBranch = textBankBranch.Text;
        ClaimPaymentCur.CarrierName = textCarrierName.Text;
        ClaimPaymentCur.Note = textNote.Text;
        try
        {
            if (IsNew)
            {
                ClaimPayments.insert(ClaimPaymentCur);
                //error thrown if trying to change amount and already attached to a deposit.
                //Date the check is entered in the system (i.e. today)
                SecurityLogs.MakeLogEntry(Permissions.InsPayCreate, 0, Lan.g(this,"Carrier Name: ") + ClaimPaymentCur.CarrierName + ", " + Lan.g(this,"Total Amount: ") + ClaimPaymentCur.CheckAmt.ToString("c") + ", " + Lan.g(this,"Check Date: ") + ClaimPaymentCur.CheckDate.ToShortDateString() + ", " + "ClaimPaymentNum: " + ClaimPaymentCur.ClaimPaymentNum);
            }
            else
            {
                //Column name, not translated.
                ClaimPayments.update(ClaimPaymentCur);
                //error thrown if trying to change amount and already attached to a deposit.
                //Date the check is entered in the system
                SecurityLogs.MakeLogEntry(Permissions.InsPayEdit, 0, Lan.g(this,"Carrier Name: ") + ClaimPaymentCur.CarrierName + ", " + Lan.g(this,"Total Amount: ") + ClaimPaymentCur.CheckAmt.ToString("c") + ", " + Lan.g(this,"Check Date: ") + ClaimPaymentCur.CheckDate.ToShortDateString() + ", " + "ClaimPaymentNum: " + ClaimPaymentCur.ClaimPaymentNum);
            } 
        }
        catch (ApplicationException ex)
        {
            //Column name, not translated.
            MessageBox.Show(ex.Message);
            return ;
        }

        ClaimProcs.synchDateCP(ClaimPaymentCur.ClaimPaymentNum,ClaimPaymentCur.CheckDate);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


