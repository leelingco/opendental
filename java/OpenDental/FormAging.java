//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:34 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormAging;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* 
*/
public class FormAging  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDate textDateLast;
    private OpenDental.ValidDate textDateCalc;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormAging() throws Exception {
        initializeComponent();
        Lan.f(this);
        Lan.C(this, new Control[]{ this.textBox1 });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAging.class);
        this.textDateLast = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.textDateCalc = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textDateLast
        //
        this.textDateLast.Location = new System.Drawing.Point(173, 79);
        this.textDateLast.Name = "textDateLast";
        this.textDateLast.ReadOnly = true;
        this.textDateLast.Size = new System.Drawing.Size(94, 20);
        this.textDateLast.TabIndex = 12;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 83);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(146, 16);
        this.label1.TabIndex = 13;
        this.label1.Text = "Last Calculated";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
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
        this.butCancel.Location = new System.Drawing.Point(440, 138);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
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
        this.butOK.Location = new System.Drawing.Point(440, 104);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(25, 12);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(476, 62);
        this.textBox1.TabIndex = 16;
        this.textBox1.Text = "If you use monthly billing instead of daily, then this is where you change the ag" + "ing date every month.  Otherwise, it\'s not necessary to manually run aging.  It\'" + "s all handled automatically.";
        //
        // textDateCalc
        //
        this.textDateCalc.Location = new System.Drawing.Point(173, 111);
        this.textDateCalc.Name = "textDateCalc";
        this.textDateCalc.Size = new System.Drawing.Size(94, 20);
        this.textDateCalc.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(23, 115);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(146, 16);
        this.label2.TabIndex = 18;
        this.label2.Text = "Calculate as of";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormAging
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(532, 180);
        this.Controls.Add(this.textDateCalc);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.textDateLast);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAging";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Calculate Aging";
        this.Load += new System.EventHandler(this.FormAging_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAging_Load(Object sender, System.EventArgs e) throws Exception {
        DateTime dateLastAging = PIn.Date(PrefC.getString(PrefName.DateLastAging));
        if (dateLastAging.Year < 1880)
        {
            textDateLast.Text = "";
        }
        else
        {
            textDateLast.Text = dateLastAging.ToShortDateString();
        } 
        if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily))
        {
            if (dateLastAging < DateTime.Today.AddDays(-15))
            {
                textDateCalc.Text = dateLastAging.AddMonths(1).ToShortDateString();
            }
            else
            {
                textDateCalc.Text = dateLastAging.ToShortDateString();
            } 
        }
        else
        {
            textDateCalc.Text = DateTime.Today.ToShortDateString();
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateCalc.errorProvider1.GetError(textDateCalc), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        Ledgers.ComputeAging(0, PIn.Date(textDateCalc.Text), false);
        if (Prefs.UpdateString(PrefName.DateLastAging, POut.Date(PIn.Date(textDateCalc.Text), false)))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        Cursor = Cursors.Default;
        MsgBox.show(this,"Aging Complete");
        DialogResult = DialogResult.OK;
    }

}


