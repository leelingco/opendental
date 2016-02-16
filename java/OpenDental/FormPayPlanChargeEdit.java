//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:28 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPayPlanChargeEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlanCharges;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Providers;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormPayPlanChargeEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.ODtextBox textNote;
    private PayPlanCharge PayPlanChargeCur;
    private OpenDental.ValidDouble textPrincipal;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDouble textInterest;
    private Label label9 = new Label();
    private TextBox textProv = new TextBox();
    private TextBox textClinic = new TextBox();
    private Label labelClinic = new Label();
    private OpenDental.ValidDate textDate;
    /**
    * 
    */
    public FormPayPlanChargeEdit(PayPlanCharge payPlanCharge) throws Exception {
        initializeComponent();
        PayPlanChargeCur = payPlanCharge.copy();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayPlanChargeEdit.class);
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.textPrincipal = new OpenDental.ValidDouble();
        this.textNote = new OpenDental.ODtextBox();
        this.textInterest = new OpenDental.ValidDouble();
        this.label1 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.textProv = new System.Windows.Forms.TextBox();
        this.textClinic = new System.Windows.Forms.TextBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 35);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 16);
        this.label4.TabIndex = 3;
        this.label4.Text = "Note";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 96);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 4;
        this.label5.Text = "Principal";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(437, 207);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 6;
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(437, 245);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(24, 243);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(78, 26);
        this.butDelete.TabIndex = 17;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textPrincipal
        //
        this.textPrincipal.Location = new System.Drawing.Point(108, 93);
        this.textPrincipal.Name = "textPrincipal";
        this.textPrincipal.Size = new System.Drawing.Size(100, 20);
        this.textPrincipal.TabIndex = 1;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(108, 33);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Adjustment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(245, 55);
        this.textNote.TabIndex = 0;
        //
        // textInterest
        //
        this.textInterest.Location = new System.Drawing.Point(108, 118);
        this.textInterest.Name = "textInterest";
        this.textInterest.Size = new System.Drawing.Size(100, 20);
        this.textInterest.TabIndex = 20;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 120);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 16);
        this.label1.TabIndex = 21;
        this.label1.Text = "Interest";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(108, 8);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 22;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 12);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 16);
        this.label2.TabIndex = 23;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(7, 146);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(100, 14);
        this.label9.TabIndex = 101;
        this.label9.Text = "Provider";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProv
        //
        this.textProv.Location = new System.Drawing.Point(108, 143);
        this.textProv.Name = "textProv";
        this.textProv.Size = new System.Drawing.Size(201, 20);
        this.textProv.TabIndex = 102;
        //
        // textClinic
        //
        this.textClinic.Location = new System.Drawing.Point(108, 168);
        this.textClinic.Name = "textClinic";
        this.textClinic.Size = new System.Drawing.Size(201, 20);
        this.textClinic.TabIndex = 104;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(7, 171);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(100, 14);
        this.labelClinic.TabIndex = 103;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormPayPlanChargeEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(541, 293);
        this.Controls.Add(this.textClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textProv);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.textInterest);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.textPrincipal);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPayPlanChargeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Payment Plan Charge";
        this.Load += new System.EventHandler(this.FormPayPlanCharge_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPayPlanCharge_Load(Object sender, System.EventArgs e) throws Exception {
        textDate.Text = PayPlanChargeCur.ChargeDate.ToShortDateString();
        //comboProvNum.Items.Clear();
        //for(int i=0;i<ProviderC.List.Length;i++) {
        //	comboProvNum.Items.Add(ProviderC.List[i].Abbr);
        //	if(ProviderC.List[i].ProvNum==PayPlanChargeCur.ProvNum)
        //		comboProvNum.SelectedIndex=i;
        //}
        textPrincipal.Text = PayPlanChargeCur.Principal.ToString("n");
        textInterest.Text = PayPlanChargeCur.Interest.ToString("n");
        textNote.Text = PayPlanChargeCur.Note;
        textProv.Text = Providers.getAbbr(PayPlanChargeCur.ProvNum);
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            labelClinic.Visible = false;
            textClinic.Visible = false;
        }
        else
        {
            textClinic.Text = Clinics.getDesc(PayPlanChargeCur.ClinicNum);
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textPrincipal.errorProvider1.GetError(textPrincipal), "") || !StringSupport.equals(textInterest.errorProvider1.GetError(textInterest), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        //if(comboProvNum.SelectedIndex==-1){
        //	MsgBox.Show(this,"Please select a provider first.");
        //	return;
        //}
        if (StringSupport.equals(textPrincipal.Text, ""))
        {
            textPrincipal.Text = "0";
        }
         
        if (StringSupport.equals(textInterest.Text, ""))
        {
            textInterest.Text = "0";
        }
         
        //todo: test dates?  The day of the month should be the same as all others
        PayPlanChargeCur.ChargeDate = PIn.Date(textDate.Text);
        PayPlanChargeCur.Principal = PIn.Double(textPrincipal.Text);
        PayPlanChargeCur.Interest = PIn.Double(textInterest.Text);
        PayPlanChargeCur.Note = textNote.Text;
        //not allowed to change provnum or clinicNum here.
        if (IsNew)
        {
            PayPlanCharges.insert(PayPlanChargeCur);
        }
        else
        {
            PayPlanCharges.update(PayPlanChargeCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            PayPlanCharges.delete(PayPlanChargeCur);
            DialogResult = DialogResult.OK;
        } 
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


