//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:16 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormInsSelectSubscr;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.PatPlans;

/**
* For a given subscriber, this list all their plans.  User then selects one plan from the list or creates a blank plan.
*/
public class FormInsSelectSubscr  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listPlans = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butNew;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private long Subscriber = new long();
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * When dialogResult=OK, this will contain the InsSubNum of the selected plan.  If this is 0, then user has selected the 'New' option.
    */
    public long SelectedInsSubNum = new long();
    /**
    * 
    */
    public FormInsSelectSubscr(long subscriber) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        Subscriber = subscriber;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsSelectSubscr.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listPlans = new System.Windows.Forms.ListBox();
        this.butNew = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(317, 211);
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
        this.butOK.Location = new System.Drawing.Point(226, 211);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listPlans
        //
        this.listPlans.Location = new System.Drawing.Point(24, 21);
        this.listPlans.Name = "listPlans";
        this.listPlans.Size = new System.Drawing.Size(368, 160);
        this.listPlans.TabIndex = 2;
        this.listPlans.DoubleClick += new System.EventHandler(this.listPlans_DoubleClick);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Location = new System.Drawing.Point(26, 211);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(85, 26);
        this.butNew.TabIndex = 3;
        this.butNew.Text = "New Plan";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // FormInsSelectSubscr
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(420, 263);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.listPlans);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsSelectSubscr";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Insurance Plan";
        this.Load += new System.EventHandler(this.FormInsSelectSubscr_Load);
        this.ResumeLayout(false);
    }

    private void formInsSelectSubscr_Load(Object sender, System.EventArgs e) throws Exception {
        SubList = InsSubs.getListForSubscriber(Subscriber);
        List<InsPlan> planList = InsPlans.refreshForSubList(SubList);
        //PatPlan[] patPlanArray;
        String str = new String();
        InsPlan plan;
        for (int i = 0;i < SubList.Count;i++)
        {
            plan = InsPlans.GetPlan(SubList[i].PlanNum, planList);
            str = InsPlans.GetCarrierName(SubList[i].PlanNum, planList);
            if (!StringSupport.equals(plan.GroupNum, ""))
            {
                str += Lan.g(this," group:") + plan.GroupNum;
            }
             
            int countPatPlans = PatPlans.GetCountBySubNum(SubList[i].InsSubNum);
            if (countPatPlans == 0)
            {
                str += " " + Lan.g(this,"(not in use)");
            }
             
            listPlans.Items.Add(str);
        }
    }

    private void listPlans_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listPlans.SelectedIndex == -1)
        {
            return ;
        }
         
        SelectedInsSubNum = SubList[listPlans.SelectedIndex].InsSubNum;
        DialogResult = DialogResult.OK;
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        SelectedInsSubNum = 0;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listPlans.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a plan first.");
            return ;
        }
         
        SelectedInsSubNum = SubList[listPlans.SelectedIndex].InsSubNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


