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
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;

public class FormCanadaOutstandingTransactions  extends Form 
{

    List<Carrier> carriers = new List<Carrier>();
    public FormCanadaOutstandingTransactions() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formCanadaOutstandingTransactions_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Carriers.getListt().Length;i++)
        {
            if ((Carriers.getListt()[i].CanadianSupportedTypes & CanSupTransTypes.RequestForOutstandingTrans_04) == CanSupTransTypes.RequestForOutstandingTrans_04)
            {
                carriers.Add(Carriers.getListt()[i]);
                listCarriers.Items.Add(Carriers.getListt()[i].CarrierName);
            }
             
        }
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (!ProviderC.getListShort()[i].IsCDAnet || StringSupport.equals(ProviderC.getListShort()[i].NationalProvID, "") || StringSupport.equals(ProviderC.getListShort()[i].CanadianOfficeNum, ""))
            {
                continue;
            }
             
            if (!listOfficeNumbers.Items.Contains(ProviderC.getListShort()[i].CanadianOfficeNum))
            {
                listOfficeNumbers.Items.Add(ProviderC.getListShort()[i].CanadianOfficeNum);
            }
             
        }
        if (listOfficeNumbers.Items.Count < 1)
        {
            MsgBox.show(this,"At least one unhidden provider must have a CDA Number and an Office Number set before running a Request for Outstanding Transactions.");
            Close();
        }
         
    }

    private void radioVersion2_Click(Object sender, EventArgs e) throws Exception {
        radioVersion2.Checked = true;
        radioVersion4Itrans.Checked = false;
        radioVersion4ToCarrier.Checked = false;
        groupCarrier.Enabled = false;
    }

    private void radioVersion4Itrans_Click(Object sender, EventArgs e) throws Exception {
        radioVersion2.Checked = false;
        radioVersion4Itrans.Checked = true;
        radioVersion4ToCarrier.Checked = false;
        groupCarrier.Enabled = false;
    }

    private void radioVersion4ToCarrier_Click(Object sender, EventArgs e) throws Exception {
        radioVersion2.Checked = false;
        radioVersion4Itrans.Checked = false;
        radioVersion4ToCarrier.Checked = true;
        groupCarrier.Enabled = true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (radioVersion4ToCarrier.Checked)
        {
            if (listCarriers.SelectedIndex < 0)
            {
                MsgBox.show(this,"You must first select a carrier to use.");
                return ;
            }
             
        }
         
        if (listOfficeNumbers.SelectedIndex < 0)
        {
            MsgBox.show(this,"You must first select an Office Number to use.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        Provider prov = null;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ProviderC.getListShort()[i].CanadianOfficeNum == listOfficeNumbers.Items[listOfficeNumbers.SelectedIndex].ToString() && !StringSupport.equals(ProviderC.getListShort()[i].NationalProvID, "") && ProviderC.getListShort()[i].IsCDAnet)
            {
                prov = ProviderC.getListShort()[i];
                break;
            }
             
        }
        try
        {
            if (radioVersion2.Checked)
            {
                CanadianOutput.getOutstandingTransactions(true,false,null,prov);
            }
            else if (radioVersion4Itrans.Checked)
            {
                CanadianOutput.getOutstandingTransactions(false,true,null,prov);
            }
            else if (radioVersion4ToCarrier.Checked)
            {
                CanadianOutput.GetOutstandingTransactions(false, false, carriers[listCarriers.SelectedIndex], prov);
            }
               
            Cursor = Cursors.Default;
            MsgBox.show(this,"Done.");
        }
        catch (ApplicationException aex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(Lan.g(this,"Request failed: ") + aex.Message);
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(Lan.g(this,"Request failed: ") + ex.ToString());
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
        this.listCarriers = new System.Windows.Forms.ListBox();
        this.groupCarrier = new System.Windows.Forms.GroupBox();
        this.radioVersion2 = new System.Windows.Forms.RadioButton();
        this.radioVersion4Itrans = new System.Windows.Forms.RadioButton();
        this.radioVersion4ToCarrier = new System.Windows.Forms.RadioButton();
        this.groupOfficeNumber = new System.Windows.Forms.GroupBox();
        this.listOfficeNumbers = new System.Windows.Forms.ListBox();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupCarrier.SuspendLayout();
        this.groupOfficeNumber.SuspendLayout();
        this.SuspendLayout();
        //
        // listCarriers
        //
        this.listCarriers.FormattingEnabled = true;
        this.listCarriers.Location = new System.Drawing.Point(6, 19);
        this.listCarriers.Name = "listCarriers";
        this.listCarriers.Size = new System.Drawing.Size(307, 95);
        this.listCarriers.TabIndex = 107;
        //
        // groupCarrier
        //
        this.groupCarrier.Controls.Add(this.listCarriers);
        this.groupCarrier.Enabled = false;
        this.groupCarrier.Location = new System.Drawing.Point(29, 117);
        this.groupCarrier.Name = "groupCarrier";
        this.groupCarrier.Size = new System.Drawing.Size(319, 120);
        this.groupCarrier.TabIndex = 109;
        this.groupCarrier.TabStop = false;
        this.groupCarrier.Text = "Carrier";
        //
        // radioVersion2
        //
        this.radioVersion2.AutoSize = true;
        this.radioVersion2.Location = new System.Drawing.Point(12, 48);
        this.radioVersion2.Name = "radioVersion2";
        this.radioVersion2.Size = new System.Drawing.Size(69, 17);
        this.radioVersion2.TabIndex = 110;
        this.radioVersion2.Text = "Version 2";
        this.radioVersion2.UseVisualStyleBackColor = true;
        this.radioVersion2.Click += new System.EventHandler(this.radioVersion2_Click);
        //
        // radioVersion4Itrans
        //
        this.radioVersion4Itrans.AutoSize = true;
        this.radioVersion4Itrans.Checked = true;
        this.radioVersion4Itrans.Location = new System.Drawing.Point(12, 71);
        this.radioVersion4Itrans.Name = "radioVersion4Itrans";
        this.radioVersion4Itrans.Size = new System.Drawing.Size(171, 17);
        this.radioVersion4Itrans.TabIndex = 111;
        this.radioVersion4Itrans.TabStop = true;
        this.radioVersion4Itrans.Text = "Version 4 To ITRANS Network";
        this.radioVersion4Itrans.UseVisualStyleBackColor = true;
        this.radioVersion4Itrans.Click += new System.EventHandler(this.radioVersion4Itrans_Click);
        //
        // radioVersion4ToCarrier
        //
        this.radioVersion4ToCarrier.AutoSize = true;
        this.radioVersion4ToCarrier.Location = new System.Drawing.Point(12, 94);
        this.radioVersion4ToCarrier.Name = "radioVersion4ToCarrier";
        this.radioVersion4ToCarrier.Size = new System.Drawing.Size(259, 17);
        this.radioVersion4ToCarrier.TabIndex = 112;
        this.radioVersion4ToCarrier.TabStop = true;
        this.radioVersion4ToCarrier.Text = "Version 4 To Specific Carrier (not commonly used)";
        this.radioVersion4ToCarrier.UseVisualStyleBackColor = true;
        this.radioVersion4ToCarrier.Click += new System.EventHandler(this.radioVersion4ToCarrier_Click);
        //
        // groupOfficeNumber
        //
        this.groupOfficeNumber.Controls.Add(this.listOfficeNumbers);
        this.groupOfficeNumber.Location = new System.Drawing.Point(29, 237);
        this.groupOfficeNumber.Name = "groupOfficeNumber";
        this.groupOfficeNumber.Size = new System.Drawing.Size(319, 70);
        this.groupOfficeNumber.TabIndex = 113;
        this.groupOfficeNumber.TabStop = false;
        this.groupOfficeNumber.Text = "Office Number";
        //
        // listOfficeNumbers
        //
        this.listOfficeNumbers.FormattingEnabled = true;
        this.listOfficeNumbers.Location = new System.Drawing.Point(6, 19);
        this.listOfficeNumbers.Name = "listOfficeNumbers";
        this.listOfficeNumbers.Size = new System.Drawing.Size(307, 43);
        this.listOfficeNumbers.TabIndex = 0;
        //
        // textBox1
        //
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textBox1.Location = new System.Drawing.Point(12, 12);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.ReadOnly = true;
        this.textBox1.Size = new System.Drawing.Size(336, 32);
        this.textBox1.TabIndex = 114;
        this.textBox1.Text = "The outstanding transactions request should be run for both version 2 and version" + " 4.";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(192, 312);
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
        this.butCancel.Location = new System.Drawing.Point(273, 312);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormCanadaOutstandingTransactions
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(360, 352);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.groupOfficeNumber);
        this.Controls.Add(this.radioVersion4ToCarrier);
        this.Controls.Add(this.radioVersion4Itrans);
        this.Controls.Add(this.radioVersion2);
        this.Controls.Add(this.groupCarrier);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Name = "FormCanadaOutstandingTransactions";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Outstanding Transactions Request (ROT)";
        this.Load += new System.EventHandler(this.FormCanadaOutstandingTransactions_Load);
        this.groupCarrier.ResumeLayout(false);
        this.groupOfficeNumber.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listCarriers = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupCarrier = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioVersion2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioVersion4Itrans = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioVersion4ToCarrier = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupOfficeNumber = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ListBox listOfficeNumbers = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
}


