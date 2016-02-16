//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:42 PM
//

package OpenDental;

import OpenDental.FormAutoNoteEdit;
import OpenDental.FormAutoNotes;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AutoNote;
import OpenDentBusiness.AutoNotes;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormAutoNotes  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    //private List<AutoNote> AutoNoteList;
    public AutoNote AutoNoteCur;
    /**
    * 
    */
    public FormAutoNotes() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoNotes.class);
        this.listMain = new System.Windows.Forms.ListBox();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(18, 21);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(265, 641);
        this.listMain.TabIndex = 2;
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
        this.butClose.Location = new System.Drawing.Point(356, 637);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(79, 26);
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
        this.butAdd.Location = new System.Drawing.Point(356, 320);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormAutoNotes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(447, 675);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutoNotes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Auto Notes";
        this.Load += new System.EventHandler(this.FormAutoNotes_Load);
        this.ResumeLayout(false);
    }

    private void formAutoNotes_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        AutoNotes.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < AutoNotes.Listt.Count;i++)
        {
            listMain.Items.Add(AutoNotes.Listt[i].AutoNoteName);
        }
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormAutoNoteEdit form = new FormAutoNoteEdit();
        form.IsNew = false;
        form.AutoNoteCur = AutoNotes.Listt[listMain.SelectedIndex];
        form.ShowDialog();
        fillList();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AutoNoteQuickNoteEdit))
        {
            return ;
        }
         
        FormAutoNoteEdit FormA = new FormAutoNoteEdit();
        FormA.IsNew = true;
        FormA.AutoNoteCur = new AutoNote();
        FormA.ShowDialog();
        fillList();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


