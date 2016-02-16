//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.CanadianOutput;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;

public class FormCanadaPaymentReconciliation  extends Form 
{

    List<Carrier> carriers = new List<Carrier>();
    public FormCanadaPaymentReconciliation() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formCanadaPaymentReconciliation_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Carriers.getListt().Length;i++)
        {
            //This transaction does not exist in version 02.
            if (!StringSupport.equals(Carriers.getListt()[i].CDAnetVersion, "02") && (Carriers.getListt()[i].CanadianSupportedTypes & CanSupTransTypes.RequestForPaymentReconciliation_06) == CanSupTransTypes.RequestForPaymentReconciliation_06)
            {
                carriers.Add(Carriers.getListt()[i]);
                listCarriers.Items.Add(Carriers.getListt()[i].CarrierName);
            }
             
        }
        long defaultProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ProviderC.getListShort()[i].IsCDAnet)
            {
                listBillingProvider.Items.Add(ProviderC.getListShort()[i].Abbr);
                listTreatingProvider.Items.Add(ProviderC.getListShort()[i].Abbr);
                if (ProviderC.getListShort()[i].ProvNum == defaultProvNum)
                {
                    listBillingProvider.SelectedIndex = i;
                    textBillingOfficeNumber.Text = ProviderC.getListShort()[i].CanadianOfficeNum;
                    listTreatingProvider.SelectedIndex = i;
                    textTreatingOfficeNumber.Text = ProviderC.getListShort()[i].CanadianOfficeNum;
                }
                 
            }
             
        }
        textDateReconciliation.Text = DateTime.Today.ToShortDateString();
    }

    private void listBillingProvider_Click(Object sender, EventArgs e) throws Exception {
        textBillingOfficeNumber.Text = ProviderC.getListShort()[listBillingProvider.SelectedIndex].CanadianOfficeNum;
    }

    private void listTreatingProvider_Click(Object sender, EventArgs e) throws Exception {
        textTreatingOfficeNumber.Text = ProviderC.getListShort()[listTreatingProvider.SelectedIndex].CanadianOfficeNum;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listCarriers.SelectedIndex < 0)
        {
            MsgBox.show(this,"You must first choose a carrier.");
            return ;
        }
         
        if (listBillingProvider.SelectedIndex < 0)
        {
            MsgBox.show(this,"You must first choose a billing provider.");
            return ;
        }
         
        if (listTreatingProvider.SelectedIndex < 0)
        {
            MsgBox.show(this,"You must first choose a treating provider.");
            return ;
        }
         
        DateTime reconciliationDate = new DateTime();
        try
        {
            reconciliationDate = DateTime.Parse(textDateReconciliation.Text).Date;
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Reconciliation date invalid.");
            return ;
        }

        Cursor = Cursors.WaitCursor;
        try
        {
            CanadianOutput.GetPaymentReconciliations(carriers[listCarriers.SelectedIndex], ProviderC.getListShort()[listTreatingProvider.SelectedIndex], ProviderC.getListShort()[listBillingProvider.SelectedIndex], reconciliationDate);
            Cursor = Cursors.Default;
            MsgBox.show(this,"Done.");
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(Lan.g(this,"Request failed: ") + ex.Message);
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
        this.listCarriers = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.listBillingProvider = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.listTreatingProvider = new System.Windows.Forms.ListBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textDateReconciliation = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textBillingOfficeNumber = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textTreatingOfficeNumber = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 6);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(278, 17);
        this.label1.TabIndex = 106;
        this.label1.Text = "Carrier";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // listCarriers
        //
        this.listCarriers.FormattingEnabled = true;
        this.listCarriers.Location = new System.Drawing.Point(15, 26);
        this.listCarriers.Name = "listCarriers";
        this.listCarriers.Size = new System.Drawing.Size(275, 43);
        this.listCarriers.TabIndex = 107;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 72);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(278, 17);
        this.label2.TabIndex = 109;
        this.label2.Text = "Billing Provider";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // listBillingProvider
        //
        this.listBillingProvider.FormattingEnabled = true;
        this.listBillingProvider.Location = new System.Drawing.Point(15, 92);
        this.listBillingProvider.Name = "listBillingProvider";
        this.listBillingProvider.Size = new System.Drawing.Size(275, 43);
        this.listBillingProvider.TabIndex = 110;
        this.listBillingProvider.Click += new System.EventHandler(this.listBillingProvider_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 182);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(278, 17);
        this.label3.TabIndex = 111;
        this.label3.Text = "Treating Provider";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // listTreatingProvider
        //
        this.listTreatingProvider.FormattingEnabled = true;
        this.listTreatingProvider.Location = new System.Drawing.Point(15, 202);
        this.listTreatingProvider.Name = "listTreatingProvider";
        this.listTreatingProvider.Size = new System.Drawing.Size(276, 43);
        this.listTreatingProvider.TabIndex = 112;
        this.listTreatingProvider.Click += new System.EventHandler(this.listTreatingProvider_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(187, 248);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(103, 17);
        this.label4.TabIndex = 113;
        this.label4.Text = "Reconciliation Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textDateReconciliation
        //
        this.textDateReconciliation.Location = new System.Drawing.Point(190, 268);
        this.textDateReconciliation.Name = "textDateReconciliation";
        this.textDateReconciliation.Size = new System.Drawing.Size(100, 20);
        this.textDateReconciliation.TabIndex = 114;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(134, 300);
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
        this.butCancel.Location = new System.Drawing.Point(215, 300);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textBillingOfficeNumber
        //
        this.textBillingOfficeNumber.Location = new System.Drawing.Point(15, 158);
        this.textBillingOfficeNumber.Name = "textBillingOfficeNumber";
        this.textBillingOfficeNumber.ReadOnly = true;
        this.textBillingOfficeNumber.Size = new System.Drawing.Size(102, 20);
        this.textBillingOfficeNumber.TabIndex = 114;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(12, 138);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(278, 17);
        this.label5.TabIndex = 115;
        this.label5.Text = "Billing Office Number";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(12, 248);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(151, 17);
        this.label6.TabIndex = 117;
        this.label6.Text = "Treating Office Number";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textTreatingOfficeNumber
        //
        this.textTreatingOfficeNumber.Location = new System.Drawing.Point(15, 268);
        this.textTreatingOfficeNumber.Name = "textTreatingOfficeNumber";
        this.textTreatingOfficeNumber.ReadOnly = true;
        this.textTreatingOfficeNumber.Size = new System.Drawing.Size(102, 20);
        this.textTreatingOfficeNumber.TabIndex = 116;
        //
        // FormCanadaPaymentReconciliation
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(306, 336);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textTreatingOfficeNumber);
        this.Controls.Add(this.listCarriers);
        this.Controls.Add(this.textDateReconciliation);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textBillingOfficeNumber);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.listTreatingProvider);
        this.Controls.Add(this.listBillingProvider);
        this.Controls.Add(this.label3);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Name = "FormCanadaPaymentReconciliation";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payment Reconciliation Request";
        this.Load += new System.EventHandler(this.FormCanadaPaymentReconciliation_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listCarriers = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listBillingProvider = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listTreatingProvider = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateReconciliation = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBillingOfficeNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTreatingOfficeNumber = new System.Windows.Forms.TextBox();
}


