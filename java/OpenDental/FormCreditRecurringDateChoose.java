//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.CreditCard;

public class FormCreditRecurringDateChoose  extends Form 
{

    private CreditCard CreditCardCur;
    private DateTime lastMonth = new DateTime();
    private DateTime thisMonth = new DateTime();
    public DateTime PayDate = new DateTime();
    public FormCreditRecurringDateChoose(CreditCard creditCard) throws Exception {
        initializeComponent();
        CreditCardCur = creditCard;
        lastMonth = GetValidPayDate(DateTime.Now.AddMonths(-1));
        thisMonth = GetValidPayDate(DateTime.Now);
        Lan.F(this);
    }

    private void formCreditRecurringDateChoose_Load(Object sender, EventArgs e) throws Exception {
        labelLastMonth.Text += " " + lastMonth.ToShortDateString();
        labelThisMonth.Text += " " + thisMonth.ToShortDateString();
        //If the recurring pay date is in the future do not let them choose that option.
        if (thisMonth > DateTime.Now)
        {
            butThisMonth.Enabled = false;
            labelThisMonth.Text = "Cannot make payment for future date: " + thisMonth.ToShortDateString();
        }
         
    }

    /**
    * Returns a valid date based on the Month and Year taken from the date passed in and the Day that is set for the recurring charges.
    */
    private DateTime getValidPayDate(DateTime date) throws Exception {
        DateTime newDate = new DateTime();
        try
        {
            newDate = new DateTime(date.Year, date.Month, CreditCardCur.DateStart.Day);
        }
        catch (Exception __dummyCatchVar0)
        {
            //Not a valid date, so use the max day in that month.
            newDate = new DateTime(date.Year, date.Month, DateTime.DaysInMonth(date.Year, date.Month));
        }

        return newDate;
    }

    private void butLastMonth_Click(Object sender, EventArgs e) throws Exception {
        PayDate = lastMonth;
        DialogResult = DialogResult.OK;
    }

    private void butThisMonth_Click(Object sender, EventArgs e) throws Exception {
        PayDate = thisMonth;
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
        this.butCancel = new OpenDental.UI.Button();
        this.butLastMonth = new OpenDental.UI.Button();
        this.butThisMonth = new OpenDental.UI.Button();
        this.labelMessage = new System.Windows.Forms.Label();
        this.labelLastMonth = new System.Windows.Forms.Label();
        this.labelThisMonth = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(301, 159);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butLastMonth
        //
        this.butLastMonth.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLastMonth.setAutosize(true);
        this.butLastMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLastMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLastMonth.setCornerRadius(4F);
        this.butLastMonth.Location = new System.Drawing.Point(17, 62);
        this.butLastMonth.Name = "butLastMonth";
        this.butLastMonth.Size = new System.Drawing.Size(96, 24);
        this.butLastMonth.TabIndex = 4;
        this.butLastMonth.Text = "Last Month";
        this.butLastMonth.Click += new System.EventHandler(this.butLastMonth_Click);
        //
        // butThisMonth
        //
        this.butThisMonth.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butThisMonth.setAutosize(true);
        this.butThisMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butThisMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butThisMonth.setCornerRadius(4F);
        this.butThisMonth.Location = new System.Drawing.Point(17, 103);
        this.butThisMonth.Name = "butThisMonth";
        this.butThisMonth.Size = new System.Drawing.Size(96, 24);
        this.butThisMonth.TabIndex = 5;
        this.butThisMonth.Text = "This Month";
        this.butThisMonth.Click += new System.EventHandler(this.butThisMonth_Click);
        //
        // labelMessage
        //
        this.labelMessage.Location = new System.Drawing.Point(14, 27);
        this.labelMessage.Name = "labelMessage";
        this.labelMessage.Size = new System.Drawing.Size(362, 23);
        this.labelMessage.TabIndex = 8;
        this.labelMessage.Text = "Which month should this payment be applied to?";
        //
        // labelLastMonth
        //
        this.labelLastMonth.Location = new System.Drawing.Point(119, 62);
        this.labelLastMonth.Name = "labelLastMonth";
        this.labelLastMonth.Size = new System.Drawing.Size(267, 24);
        this.labelLastMonth.TabIndex = 9;
        this.labelLastMonth.Text = "Payment date will be:";
        this.labelLastMonth.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelThisMonth
        //
        this.labelThisMonth.Location = new System.Drawing.Point(119, 103);
        this.labelThisMonth.Name = "labelThisMonth";
        this.labelThisMonth.Size = new System.Drawing.Size(267, 24);
        this.labelThisMonth.TabIndex = 10;
        this.labelThisMonth.Text = "Payment date will be:";
        this.labelThisMonth.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormCreditRecurringDateChoose
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(388, 195);
        this.Controls.Add(this.labelThisMonth);
        this.Controls.Add(this.labelLastMonth);
        this.Controls.Add(this.labelMessage);
        this.Controls.Add(this.butThisMonth);
        this.Controls.Add(this.butLastMonth);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCreditRecurringDateChoose";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormCreditRecurringDateChoose_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butLastMonth;
    private OpenDental.UI.Button butThisMonth;
    private System.Windows.Forms.Label labelMessage = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelLastMonth = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelThisMonth = new System.Windows.Forms.Label();
}


