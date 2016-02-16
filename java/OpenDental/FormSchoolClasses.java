//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormSchoolClassEdit;
import OpenDental.FormSchoolClasses;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.SchoolClass;
import OpenDentBusiness.SchoolClasses;

/**
* 
*/
public class FormSchoolClasses  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormSchoolClasses() throws Exception {
        initializeComponent();
        //Providers.Selected=-1;
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSchoolClasses.class);
        this.listMain = new System.Windows.Forms.ListBox();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listMain.Location = new System.Drawing.Point(16, 12);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(265, 381);
        this.listMain.TabIndex = 4;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(209, 417);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butAdd.Location = new System.Drawing.Point(16, 416);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormSchoolClasses
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(300, 459);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.listMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSchoolClasses";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Dental School Classes";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormSchoolClasses_Closing);
        this.Load += new System.EventHandler(this.FormSchoolClasses_Load);
        this.ResumeLayout(false);
    }

    private void formSchoolClasses_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        long previousSelected = -1;
        if (listMain.SelectedIndex != -1)
        {
            previousSelected = SchoolClasses.getList()[listMain.SelectedIndex].SchoolClassNum;
        }
         
        SchoolClasses.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < SchoolClasses.getList().Length;i++)
        {
            listMain.Items.Add(SchoolClasses.getList()[i].GradYear.ToString() + " - " + SchoolClasses.getList()[i].Descript);
            if (SchoolClasses.getList()[i].SchoolClassNum == previousSelected)
            {
                listMain.SelectedIndex = i;
            }
             
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        SchoolClass cur = new SchoolClass();
        FormSchoolClassEdit FormS = new FormSchoolClassEdit(cur);
        FormS.IsNew = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillList();
        listMain.SelectedIndex = -1;
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
            return ;
         
        FormSchoolClassEdit FormS = new FormSchoolClassEdit(SchoolClasses.getList()[listMain.SelectedIndex]);
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillList();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formSchoolClasses_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.DentalSchools);
        }
         
    }

}


