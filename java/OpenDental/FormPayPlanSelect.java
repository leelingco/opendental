//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:28 PM
//

package OpenDental;

import OpenDental.FormPayPlanSelect;
import OpenDental.Lan;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlans;

/**
* Lets the user choose which payment plan to attach a payment to if there are more than one available.
*/
public class FormPayPlanSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * A list of plans passed to this form which are to be displayed.
    */
    private List<PayPlan> ValidPlans = new List<PayPlan>();
    /**
    * A list of payPlanCharges passed to this form used to calculate princ for each payplan.
    */
    private List<PayPlanCharge> ChargeList = new List<PayPlanCharge>();
    private System.Windows.Forms.ListBox listPayPlans = new System.Windows.Forms.ListBox();
    /**
    * The index of the plan selected.
    */
    public int IndexSelected = new int();
    /**
    * 
    */
    public FormPayPlanSelect(List<PayPlan> validPlans, List<PayPlanCharge> chargeList) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        ValidPlans = validPlans;
        ChargeList = chargeList;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayPlanSelect.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listPayPlans = new System.Windows.Forms.ListBox();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(256, 185);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(160, 185);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listPayPlans
        //
        this.listPayPlans.Location = new System.Drawing.Point(32, 51);
        this.listPayPlans.Name = "listPayPlans";
        this.listPayPlans.Size = new System.Drawing.Size(300, 95);
        this.listPayPlans.TabIndex = 2;
        this.listPayPlans.DoubleClick += new System.EventHandler(this.listPayPlans_DoubleClick);
        //
        // FormPayPlanSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(356, 232);
        this.Controls.Add(this.listPayPlans);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPayPlanSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Payment Plan";
        this.Load += new System.EventHandler(this.FormPayPlanSelect_Load);
        this.ResumeLayout(false);
    }

    private void formPayPlanSelect_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < ValidPlans.Count;i++)
        {
            listPayPlans.Items.Add(ValidPlans[i].PayPlanDate.ToShortDateString() + "  " + PayPlans.GetTotalPrinc(ValidPlans[i].PayPlanNum, ChargeList).ToString("F") + "  " + Patients.GetPat(ValidPlans[i].PatNum).GetNameFL());
        }
    }

    private void listPayPlans_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listPayPlans.SelectedIndex == -1)
        {
            return ;
        }
         
        IndexSelected = listPayPlans.SelectedIndex;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listPayPlans.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a payment plan first."));
            return ;
        }
         
        IndexSelected = listPayPlans.SelectedIndex;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


