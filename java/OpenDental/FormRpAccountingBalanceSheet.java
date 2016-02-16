//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:03 PM
//

package OpenDental;

import OpenDental.FormReportForRdl;
import OpenDental.FormRpAccountingBalanceSheet;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.Properties.Resources;

/**
* 
*/
public class FormRpAccountingBalanceSheet  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelTO = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    //private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpAccountingBalanceSheet() throws Exception {
        initializeComponent();
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpAccountingBalanceSheet.class);
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(31, 36);
        this.date1.MaxSelectionCount = 1;
        this.date1.Name = "date1";
        this.date1.TabIndex = 1;
        //
        // labelTO
        //
        this.labelTO.Location = new System.Drawing.Point(28, 9);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(72, 23);
        this.labelTO.TabIndex = 22;
        this.labelTO.Text = "As of";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
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
        this.butCancel.Location = new System.Drawing.Point(297, 219);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(297, 184);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRpAccountingBalanceSheet
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(412, 258);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpAccountingBalanceSheet";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Balance Sheet Report";
        this.Load += new System.EventHandler(this.FormRpAccountingBalanceSheet_Load);
        this.ResumeLayout(false);
    }

    private void formRpAccountingBalanceSheet_Load(Object sender, System.EventArgs e) throws Exception {
        if (DateTime.Today.Month > 6)
        {
            //default to this year
            date1.SelectionStart = new DateTime(DateTime.Today.Year, 12, 31);
        }
        else
        {
            //default to last year
            date1.SelectionStart = new DateTime(DateTime.Today.Year - 1, 12, 31);
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        FormReportForRdl FormR = new FormReportForRdl();
        String s = Resources.getReportAccountingBalanceSheet();
        s = s.Replace("12/31/2007", date1.SelectionStart.ToShortDateString());
        s = s.Replace("2007-12-31", POut.Date(date1.SelectionStart, false));
        FormR.setSourceRdlString(s);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


