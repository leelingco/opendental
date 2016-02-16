//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:09 PM
//

package OpenDental;

import OpenDental.FormFamilyMemberSelect;
import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Family;

/**
* For a given subscriber, this list all their plans.  User then selects one plan from the list or creates a blank plan.
*/
public class FormFamilyMemberSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listPats = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butOther;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Family Fam;
    /**
    * When dialogResult=OK, this will contain the PatNum of the selected pat.
    */
    public long SelectedPatNum = new long();
    /**
    * 
    */
    public FormFamilyMemberSelect(Family fam) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        Fam = fam;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFamilyMemberSelect.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listPats = new System.Windows.Forms.ListBox();
        this.butOther = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(318, 227);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
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
        this.butOK.Location = new System.Drawing.Point(227, 227);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listPats
        //
        this.listPats.Location = new System.Drawing.Point(24, 23);
        this.listPats.Name = "listPats";
        this.listPats.Size = new System.Drawing.Size(271, 160);
        this.listPats.TabIndex = 2;
        this.listPats.DoubleClick += new System.EventHandler(this.listPats_DoubleClick);
        //
        // butOther
        //
        this.butOther.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOther.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butOther.setAutosize(true);
        this.butOther.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOther.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOther.setCornerRadius(4F);
        this.butOther.Location = new System.Drawing.Point(22, 227);
        this.butOther.Name = "butOther";
        this.butOther.Size = new System.Drawing.Size(76, 24);
        this.butOther.TabIndex = 3;
        this.butOther.Text = "Other";
        this.butOther.Click += new System.EventHandler(this.butOther_Click);
        //
        // FormFamilyMemberSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(405, 263);
        this.Controls.Add(this.butOther);
        this.Controls.Add(this.listPats);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFamilyMemberSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Family Member";
        this.Load += new System.EventHandler(this.FormFamilyMemberSelect_Load);
        this.ResumeLayout(false);
    }

    private void formFamilyMemberSelect_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < Fam.ListPats.Length;i++)
        {
            listPats.Items.Add(Fam.ListPats[i].GetNameFL());
        }
    }

    private void listPats_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listPats.SelectedIndex == -1)
        {
            return ;
        }
         
        SelectedPatNum = Fam.ListPats[listPats.SelectedIndex].PatNum;
        DialogResult = DialogResult.OK;
    }

    private void butOther_Click(Object sender, System.EventArgs e) throws Exception {
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.SelectionModeOnly = true;
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SelectedPatNum = FormPS.SelectedPatNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listPats.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        SelectedPatNum = Fam.ListPats[listPats.SelectedIndex].PatNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


