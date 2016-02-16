//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:38 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormApptFieldDefEdit;
import OpenDental.FormApptFieldDefs;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ApptFieldDef;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.InvalidType;

/**
* 
*/
public class FormApptFieldDefs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private Label label1 = new Label();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    /**
    * 
    */
    public FormApptFieldDefs() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptFieldDefs.class);
        this.listMain = new System.Windows.Forms.ListBox();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.label1 = new System.Windows.Forms.Label();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(18, 77);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(265, 173);
        this.listMain.TabIndex = 2;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(373, 51);
        this.label1.TabIndex = 8;
        this.label1.Text = "This is only for advanced users.  This is a list of extra fields that you can set" + " up for appointments.  After adding fields to this list, you can set the values " + "in an appointment edit window.";
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(349, 271);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(79, 24);
        this.butClose.TabIndex = 1;
        this.butClose.Text = "Close";
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
        this.butAdd.Location = new System.Drawing.Point(18, 271);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormApptFieldDefs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(447, 309);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptFieldDefs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment Field Defs";
        this.Load += new System.EventHandler(this.FormApptFieldDefs_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormApptFieldDefs_FormClosing_1);
        this.ResumeLayout(false);
    }

    private void formApptFieldDefs_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ApptFieldDefs.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < ApptFieldDefs.getListt().Count;i++)
        {
            listMain.Items.Add(ApptFieldDefs.getListt()[i].FieldName);
        }
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormApptFieldDefEdit FormP = new FormApptFieldDefEdit(ApptFieldDefs.getListt()[listMain.SelectedIndex]);
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
            return ;
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ApptFieldDef def = new ApptFieldDef();
        FormApptFieldDefEdit FormP = new FormApptFieldDefEdit(def);
        FormP.IsNew = true;
        FormP.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formApptFieldDefs_FormClosing_1(Object sender, FormClosingEventArgs e) throws Exception {
        DataValid.setInvalid(InvalidType.PatFields);
    }

}


