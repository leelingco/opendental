//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

public class FormXchargeTrans  extends Form 
{

    public int TransactionType = new int();
    public double CashBackAmount = new double();
    public boolean SaveToken = new boolean();
    public FormXchargeTrans() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formXchargeTrans_Load(Object sender, EventArgs e) throws Exception {
        CashBackAmount = 0;
        textCashBackAmt.Text = CashBackAmount.ToString("F2");
        listTransType.Items.Clear();
        listTransType.Items.Add("Purchase");
        listTransType.Items.Add("Return");
        listTransType.Items.Add("Debit Purchase");
        listTransType.Items.Add("Debit Return");
        listTransType.Items.Add("Force");
        listTransType.Items.Add("Pre-Authorization");
        listTransType.Items.Add("Adjustment");
        listTransType.Items.Add("Void");
        listTransType.SelectedIndex = 0;
        checkSaveToken.Checked = PrefC.getBool(PrefName.StoreCCtokens);
    }

    private void listTransType_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        if (listTransType.IndexFromPoint(e.Location) != -1)
        {
            textCashBackAmt.Visible = false;
            labelCashBackAmt.Visible = false;
            if (listTransType.SelectedIndex == 2)
            {
                //Debit Purchase
                textCashBackAmt.Visible = true;
                labelCashBackAmt.Visible = true;
            }
             
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listTransType.SelectedIndex == 2)
        {
            //Debit Purchase
            if (!StringSupport.equals(textCashBackAmt.errorProvider1.GetError(textCashBackAmt), ""))
            {
                MsgBox.show(this,"Please fix data entry errors first.");
                return ;
            }
             
            CashBackAmount = PIn.Decimal(textCashBackAmt.Text);
        }
         
        TransactionType = listTransType.SelectedIndex;
        SaveToken = checkSaveToken.Checked;
        DialogResult = DialogResult.OK;
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
        this.butOK = new OpenDental.UI.Button();
        this.labelCashBackAmt = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.button1 = new OpenDental.UI.Button();
        this.textCashBackAmt = new ODR.ValidDouble();
        this.listTransType = new System.Windows.Forms.ListBox();
        this.checkSaveToken = new System.Windows.Forms.CheckBox();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(219, 197);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelCashBackAmt
        //
        this.labelCashBackAmt.Location = new System.Drawing.Point(50, 158);
        this.labelCashBackAmt.Name = "labelCashBackAmt";
        this.labelCashBackAmt.Size = new System.Drawing.Size(154, 16);
        this.labelCashBackAmt.TabIndex = 60;
        this.labelCashBackAmt.Text = "Cash Back Amount";
        this.labelCashBackAmt.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelCashBackAmt.Visible = false;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(50, 28);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 16);
        this.label1.TabIndex = 58;
        this.label1.Text = "Transaction Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.setCornerRadius(4F);
        this.button1.Location = new System.Drawing.Point(-226, -136);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(75, 26);
        this.button1.TabIndex = 56;
        this.button1.Text = "&OK";
        //
        // textCashBackAmt
        //
        this.textCashBackAmt.Location = new System.Drawing.Point(52, 177);
        this.textCashBackAmt.Name = "textCashBackAmt";
        this.textCashBackAmt.Size = new System.Drawing.Size(100, 20);
        this.textCashBackAmt.TabIndex = 61;
        this.textCashBackAmt.Visible = false;
        //
        // listTransType
        //
        this.listTransType.FormattingEnabled = true;
        this.listTransType.Location = new System.Drawing.Point(53, 47);
        this.listTransType.Name = "listTransType";
        this.listTransType.Size = new System.Drawing.Size(202, 108);
        this.listTransType.TabIndex = 62;
        this.listTransType.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listTransType_MouseClick);
        //
        // checkSaveToken
        //
        this.checkSaveToken.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkSaveToken.Location = new System.Drawing.Point(52, 203);
        this.checkSaveToken.Name = "checkSaveToken";
        this.checkSaveToken.Size = new System.Drawing.Size(161, 17);
        this.checkSaveToken.TabIndex = 63;
        this.checkSaveToken.Text = "Save Token";
        //
        // FormXchargeTrans
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(321, 246);
        this.Controls.Add(this.checkSaveToken);
        this.Controls.Add(this.listTransType);
        this.Controls.Add(this.textCashBackAmt);
        this.Controls.Add(this.labelCashBackAmt);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.button1);
        this.Controls.Add(this.butOK);
        this.Name = "FormXchargeTrans";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "X-Charge Transaction Types";
        this.Load += new System.EventHandler(this.FormXchargeTrans_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label labelCashBackAmt = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button button1;
    private ODR.ValidDouble textCashBackAmt;
    private System.Windows.Forms.ListBox listTransType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.CheckBox checkSaveToken = new System.Windows.Forms.CheckBox();
}


