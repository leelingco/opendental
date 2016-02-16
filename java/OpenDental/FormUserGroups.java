//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:57 PM
//

package OpenDental;

import OpenDental.FormUserGroupEdit;
import OpenDental.FormUserGroups;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.UserGroup;
import OpenDentBusiness.UserGroups;

/**
* Summary description for FormBasicTemplate.
*/
public class FormUserGroups  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private ListBox listGroups = new ListBox();
    private OpenDental.UI.Button butAddGroup;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormUserGroups() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUserGroups.class);
        this.butClose = new OpenDental.UI.Button();
        this.listGroups = new System.Windows.Forms.ListBox();
        this.butAddGroup = new OpenDental.UI.Button();
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
        this.butClose.Location = new System.Drawing.Point(295, 369);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(80, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // listGroups
        //
        this.listGroups.FormattingEnabled = true;
        this.listGroups.Location = new System.Drawing.Point(36, 39);
        this.listGroups.Name = "listGroups";
        this.listGroups.Size = new System.Drawing.Size(183, 355);
        this.listGroups.TabIndex = 1;
        this.listGroups.DoubleClick += new System.EventHandler(this.listGroups_DoubleClick);
        //
        // butAddGroup
        //
        this.butAddGroup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddGroup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddGroup.setAutosize(true);
        this.butAddGroup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddGroup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddGroup.setCornerRadius(4F);
        this.butAddGroup.Image = Resources.getAdd();
        this.butAddGroup.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddGroup.Location = new System.Drawing.Point(295, 248);
        this.butAddGroup.Name = "butAddGroup";
        this.butAddGroup.Size = new System.Drawing.Size(80, 25);
        this.butAddGroup.TabIndex = 2;
        this.butAddGroup.Text = "Add";
        this.butAddGroup.Click += new System.EventHandler(this.butAddGroup_Click);
        //
        // FormUserGroups
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(404, 420);
        this.Controls.Add(this.butAddGroup);
        this.Controls.Add(this.listGroups);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormUserGroups";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "User Groups";
        this.Load += new System.EventHandler(this.FormUserGroups_Load);
        this.ResumeLayout(false);
    }

    private void formUserGroups_Load(Object sender, EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        UserGroups.refreshCache();
        listGroups.Items.Clear();
        for (int i = 0;i < UserGroups.getList().Length;i++)
        {
            listGroups.Items.Add(UserGroups.getList()[i].Description);
        }
    }

    private void butAddGroup_Click(Object sender, EventArgs e) throws Exception {
        UserGroup group = new UserGroup();
        FormUserGroupEdit FormU = new FormUserGroupEdit(group);
        FormU.IsNew = true;
        FormU.ShowDialog();
        if (FormU.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillList();
    }

    private void listGroups_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listGroups.SelectedIndex == -1)
        {
            return ;
        }
         
        UserGroup group = UserGroups.getList()[listGroups.SelectedIndex];
        FormUserGroupEdit FormU = new FormUserGroupEdit(group);
        FormU.ShowDialog();
        if (FormU.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillList();
    }

    //reselect group
    /*for(int i=0;i<treeUsers.Nodes.Count;i++) {
    				if((int)treeUsers.Nodes[i].Tag==group.UserGroupNum) {
    					treeUsers.SelectedNode=treeUsers.Nodes[i];
    					SelectedGroupNum=group.UserGroupNum;
    				}
    			}*/
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


