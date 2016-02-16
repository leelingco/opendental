//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:53 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormClinicEdit;
import OpenDental.FormClinics;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.InvalidType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClinics  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormClinics() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClinics.class);
        this.butClose = new OpenDental.UI.Button();
        this.listMain = new System.Windows.Forms.ListBox();
        this.butAdd = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(257, 311);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(19, 56);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(131, 238);
        this.listMain.TabIndex = 2;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(17, 311);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(321, 38);
        this.label1.TabIndex = 11;
        this.label1.Text = "This is usually only used if you have multiple locations";
        //
        // FormClinics
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(360, 358);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.listMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClinics";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinics";
        this.Load += new System.EventHandler(this.FormClinics_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormClinics_Closing);
        this.ResumeLayout(false);
    }

    private void formClinics_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        Clinics.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < Clinics.getList().Length;i++)
        {
            listMain.Items.Add(Clinics.getList()[i].Description);
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Clinic ClinicCur = new Clinic();
        FormClinicEdit FormCE = new FormClinicEdit(ClinicCur);
        FormCE.IsNew = true;
        FormCE.ShowDialog();
        fillList();
        changed = true;
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormClinicEdit FormCE = new FormClinicEdit(Clinics.getList()[listMain.SelectedIndex]);
        FormCE.ShowDialog();
        fillList();
        changed = true;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formClinics_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Providers);
        }
         
    }

}


