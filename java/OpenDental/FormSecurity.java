//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:46 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormGroupPermEdit;
import OpenDental.FormSecurity;
import OpenDental.FormSecurityLock;
import OpenDental.FormUserEdit;
import OpenDental.FormUserGroups;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Employees;
import OpenDentBusiness.GroupPermission;
import OpenDentBusiness.GroupPermissionC;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.Security;
import OpenDentBusiness.UserGroups;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSecurity  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAddGroup;
    private OpenDental.UI.Button butAddUser;
    private System.Windows.Forms.TreeView treePermissions = new System.Windows.Forms.TreeView();
    private System.Windows.Forms.ImageList imageListPerm = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.Label labelPerm = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private long SelectedGroupNum = new long();
    private TreeNode clickedPermNode = new TreeNode();
    private System.Windows.Forms.CheckBox checkTimecardSecurityEnabled = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butSetAll;
    private OpenDental.UI.ODGrid gridMain;
    private boolean changed = new boolean();
    private ComboBox comboUsers = new ComboBox();
    private ComboBox comboSchoolClass = new ComboBox();
    private Label labelSchoolClass = new Label();
    private CheckBox checkCannotEditOwn = new CheckBox();
    private Label label1 = new Label();
    private TextBox textDateLock = new TextBox();
    private OpenDental.UI.Button butChange;
    private CheckBox checkPasswordsMustBeStrong = new CheckBox();
    private TextBox textDaysLock = new TextBox();
    private Label label2 = new Label();
    private CheckBox checkLogOffWindows = new CheckBox();
    private TextBox textLogOffAfterMinutes = new TextBox();
    private Label label3 = new Label();
    private Label label4 = new Label();
    //private DataTable table;
    private List<Userod> ListUser = new List<Userod>();
    /**
    * 
    */
    public FormSecurity() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSecurity.class);
        this.treePermissions = new System.Windows.Forms.TreeView();
        this.imageListPerm = new System.Windows.Forms.ImageList(this.components);
        this.labelPerm = new System.Windows.Forms.Label();
        this.checkTimecardSecurityEnabled = new System.Windows.Forms.CheckBox();
        this.comboUsers = new System.Windows.Forms.ComboBox();
        this.comboSchoolClass = new System.Windows.Forms.ComboBox();
        this.labelSchoolClass = new System.Windows.Forms.Label();
        this.checkCannotEditOwn = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textDateLock = new System.Windows.Forms.TextBox();
        this.checkPasswordsMustBeStrong = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textDaysLock = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkLogOffWindows = new System.Windows.Forms.CheckBox();
        this.textLogOffAfterMinutes = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butChange = new OpenDental.UI.Button();
        this.butSetAll = new OpenDental.UI.Button();
        this.butAddUser = new OpenDental.UI.Button();
        this.butAddGroup = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // treePermissions
        //
        this.treePermissions.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.treePermissions.HideSelection = false;
        this.treePermissions.ImageIndex = 0;
        this.treePermissions.ImageList = this.imageListPerm;
        this.treePermissions.ItemHeight = 15;
        this.treePermissions.Location = new System.Drawing.Point(525, 29);
        this.treePermissions.Name = "treePermissions";
        this.treePermissions.SelectedImageIndex = 0;
        this.treePermissions.ShowPlusMinus = false;
        this.treePermissions.ShowRootLines = false;
        this.treePermissions.Size = new System.Drawing.Size(362, 637);
        this.treePermissions.TabIndex = 6;
        this.treePermissions.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treePermissions_AfterSelect);
        this.treePermissions.DoubleClick += new System.EventHandler(this.treePermissions_DoubleClick);
        this.treePermissions.MouseDown += new System.Windows.Forms.MouseEventHandler(this.treePermissions_MouseDown);
        //
        // imageListPerm
        //
        this.imageListPerm.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListPerm.ImageStream")));
        this.imageListPerm.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListPerm.Images.SetKeyName(0, "grayBox.gif");
        this.imageListPerm.Images.SetKeyName(1, "checkBoxUnchecked.gif");
        this.imageListPerm.Images.SetKeyName(2, "checkBoxChecked.gif");
        //
        // labelPerm
        //
        this.labelPerm.Location = new System.Drawing.Point(522, 7);
        this.labelPerm.Name = "labelPerm";
        this.labelPerm.Size = new System.Drawing.Size(285, 19);
        this.labelPerm.TabIndex = 5;
        this.labelPerm.Text = "Permissions for group:";
        this.labelPerm.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkTimecardSecurityEnabled
        //
        this.checkTimecardSecurityEnabled.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkTimecardSecurityEnabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkTimecardSecurityEnabled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkTimecardSecurityEnabled.Location = new System.Drawing.Point(8, 606);
        this.checkTimecardSecurityEnabled.Name = "checkTimecardSecurityEnabled";
        this.checkTimecardSecurityEnabled.Size = new System.Drawing.Size(224, 16);
        this.checkTimecardSecurityEnabled.TabIndex = 57;
        this.checkTimecardSecurityEnabled.Text = "TimecardSecurityEnabled";
        this.checkTimecardSecurityEnabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkTimecardSecurityEnabled.Click += new System.EventHandler(this.checkTimecardSecurityEnabled_Click);
        //
        // comboUsers
        //
        this.comboUsers.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUsers.FormattingEnabled = true;
        this.comboUsers.Location = new System.Drawing.Point(8, 5);
        this.comboUsers.Name = "comboUsers";
        this.comboUsers.Size = new System.Drawing.Size(182, 21);
        this.comboUsers.TabIndex = 60;
        this.comboUsers.SelectionChangeCommitted += new System.EventHandler(this.comboUsers_SelectionChangeCommitted);
        //
        // comboSchoolClass
        //
        this.comboSchoolClass.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSchoolClass.Location = new System.Drawing.Point(276, 5);
        this.comboSchoolClass.MaxDropDownItems = 30;
        this.comboSchoolClass.Name = "comboSchoolClass";
        this.comboSchoolClass.Size = new System.Drawing.Size(168, 21);
        this.comboSchoolClass.TabIndex = 90;
        this.comboSchoolClass.SelectionChangeCommitted += new System.EventHandler(this.comboSchoolClass_SelectionChangeCommitted);
        //
        // labelSchoolClass
        //
        this.labelSchoolClass.Location = new System.Drawing.Point(203, 8);
        this.labelSchoolClass.Name = "labelSchoolClass";
        this.labelSchoolClass.Size = new System.Drawing.Size(72, 16);
        this.labelSchoolClass.TabIndex = 91;
        this.labelSchoolClass.Text = "Class";
        this.labelSchoolClass.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkCannotEditOwn
        //
        this.checkCannotEditOwn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkCannotEditOwn.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkCannotEditOwn.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkCannotEditOwn.Location = new System.Drawing.Point(8, 624);
        this.checkCannotEditOwn.Name = "checkCannotEditOwn";
        this.checkCannotEditOwn.Size = new System.Drawing.Size(224, 16);
        this.checkCannotEditOwn.TabIndex = 92;
        this.checkCannotEditOwn.Text = "Users cannot edit their own timecard";
        this.checkCannotEditOwn.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(95, 656);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(123, 18);
        this.label1.TabIndex = 93;
        this.label1.Text = "Lock Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateLock
        //
        this.textDateLock.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textDateLock.Location = new System.Drawing.Point(219, 656);
        this.textDateLock.Name = "textDateLock";
        this.textDateLock.ReadOnly = true;
        this.textDateLock.Size = new System.Drawing.Size(82, 20);
        this.textDateLock.TabIndex = 94;
        //
        // checkPasswordsMustBeStrong
        //
        this.checkPasswordsMustBeStrong.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkPasswordsMustBeStrong.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkPasswordsMustBeStrong.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPasswordsMustBeStrong.Location = new System.Drawing.Point(238, 624);
        this.checkPasswordsMustBeStrong.Name = "checkPasswordsMustBeStrong";
        this.checkPasswordsMustBeStrong.Size = new System.Drawing.Size(224, 16);
        this.checkPasswordsMustBeStrong.TabIndex = 96;
        this.checkPasswordsMustBeStrong.Text = "Passwords must be strong";
        this.checkPasswordsMustBeStrong.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkPasswordsMustBeStrong.Click += new System.EventHandler(this.checkPasswordsMustBeStrong_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(8, 29);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(511, 528);
        this.gridMain.TabIndex = 59;
        this.gridMain.setTitle("Users");
        this.gridMain.setTranslationName("TableSecurity");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textDaysLock
        //
        this.textDaysLock.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textDaysLock.Location = new System.Drawing.Point(219, 677);
        this.textDaysLock.Name = "textDaysLock";
        this.textDaysLock.ReadOnly = true;
        this.textDaysLock.Size = new System.Drawing.Size(82, 20);
        this.textDaysLock.TabIndex = 98;
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(95, 677);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(123, 18);
        this.label2.TabIndex = 97;
        this.label2.Text = "Lock Days";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkLogOffWindows
        //
        this.checkLogOffWindows.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkLogOffWindows.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkLogOffWindows.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkLogOffWindows.Location = new System.Drawing.Point(238, 606);
        this.checkLogOffWindows.Name = "checkLogOffWindows";
        this.checkLogOffWindows.Size = new System.Drawing.Size(224, 16);
        this.checkLogOffWindows.TabIndex = 99;
        this.checkLogOffWindows.Text = "Log off when Windows logs off";
        this.checkLogOffWindows.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLogOffAfterMinutes
        //
        this.textLogOffAfterMinutes.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textLogOffAfterMinutes.Location = new System.Drawing.Point(325, 581);
        this.textLogOffAfterMinutes.Name = "textLogOffAfterMinutes";
        this.textLogOffAfterMinutes.Size = new System.Drawing.Size(29, 20);
        this.textLogOffAfterMinutes.TabIndex = 100;
        this.textLogOffAfterMinutes.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label3.Location = new System.Drawing.Point(235, 581);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(90, 18);
        this.label3.TabIndex = 101;
        this.label3.Text = "Log off after ";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label4.Location = new System.Drawing.Point(358, 581);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(130, 18);
        this.label4.TabIndex = 102;
        this.label4.Text = "minutes.  0 to disable.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(304, 664);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(70, 24);
        this.butChange.TabIndex = 95;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // butSetAll
        //
        this.butSetAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSetAll.setAutosize(true);
        this.butSetAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetAll.setCornerRadius(4F);
        this.butSetAll.Location = new System.Drawing.Point(525, 672);
        this.butSetAll.Name = "butSetAll";
        this.butSetAll.Size = new System.Drawing.Size(79, 24);
        this.butSetAll.TabIndex = 58;
        this.butSetAll.Text = "Set All";
        this.butSetAll.Click += new System.EventHandler(this.butSetAll_Click);
        //
        // butAddUser
        //
        this.butAddUser.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddUser.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAddUser.setAutosize(true);
        this.butAddUser.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddUser.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddUser.setCornerRadius(4F);
        this.butAddUser.Location = new System.Drawing.Point(118, 565);
        this.butAddUser.Name = "butAddUser";
        this.butAddUser.Size = new System.Drawing.Size(75, 24);
        this.butAddUser.TabIndex = 0;
        this.butAddUser.Text = "Add User";
        this.butAddUser.Click += new System.EventHandler(this.butAddUser_Click);
        //
        // butAddGroup
        //
        this.butAddGroup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddGroup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAddGroup.setAutosize(true);
        this.butAddGroup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddGroup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddGroup.setCornerRadius(4F);
        this.butAddGroup.Location = new System.Drawing.Point(8, 565);
        this.butAddGroup.Name = "butAddGroup";
        this.butAddGroup.Size = new System.Drawing.Size(75, 24);
        this.butAddGroup.TabIndex = 1;
        this.butAddGroup.Text = "Edit Groups";
        this.butAddGroup.Click += new System.EventHandler(this.butEditGroups_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(812, 672);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormSecurity
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(894, 700);
        this.Controls.Add(this.textLogOffAfterMinutes);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.checkLogOffWindows);
        this.Controls.Add(this.textDaysLock);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.checkPasswordsMustBeStrong);
        this.Controls.Add(this.treePermissions);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.textDateLock);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkCannotEditOwn);
        this.Controls.Add(this.comboSchoolClass);
        this.Controls.Add(this.labelSchoolClass);
        this.Controls.Add(this.comboUsers);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butSetAll);
        this.Controls.Add(this.checkTimecardSecurityEnabled);
        this.Controls.Add(this.butAddUser);
        this.Controls.Add(this.butAddGroup);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.labelPerm);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSecurity";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Security";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormSecurity_FormClosing);
        this.Load += new System.EventHandler(this.FormSecurity_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSecurity_Load(Object sender, System.EventArgs e) throws Exception {
        comboUsers.Items.Add(Lan.g(this,"All Users"));
        comboUsers.Items.Add(Lan.g(this,"Providers"));
        comboUsers.Items.Add(Lan.g(this,"Employees"));
        comboUsers.Items.Add(Lan.g(this,"Other"));
        comboUsers.SelectedIndex = 0;
        if (PrefC.getBool(PrefName.EasyHideDentalSchools))
        {
            comboSchoolClass.Visible = false;
            labelSchoolClass.Visible = false;
        }
        else
        {
            comboSchoolClass.Items.Add(Lan.g(this,"All"));
            comboSchoolClass.SelectedIndex = 0;
            for (int i = 0;i < SchoolClasses.getList().Length;i++)
            {
                comboSchoolClass.Items.Add(SchoolClasses.GetDescript(SchoolClasses.getList()[i]));
            }
        } 
        fillTreePermissionsInitial();
        fillUsers();
        fillTreePerm();
        textLogOffAfterMinutes.Text = PrefC.getInt(PrefName.SecurityLogOffAfterMinutes).ToString();
        checkPasswordsMustBeStrong.Checked = PrefC.getBool(PrefName.PasswordsMustBeStrong);
        checkTimecardSecurityEnabled.Checked = PrefC.getBool(PrefName.TimecardSecurityEnabled);
        checkCannotEditOwn.Checked = PrefC.getBool(PrefName.TimecardUsersDontEditOwnCard);
        checkCannotEditOwn.Enabled = checkTimecardSecurityEnabled.Checked;
        checkLogOffWindows.Checked = PrefC.getBool(PrefName.SecurityLogOffWithWindows);
        if (PrefC.getInt(PrefName.SecurityLockDays) > 0)
        {
            textDaysLock.Text = PrefC.getInt(PrefName.SecurityLockDays).ToString();
        }
         
        if (PrefC.getDate(PrefName.SecurityLockDate).Year > 1880)
        {
            textDateLock.Text = PrefC.getDate(PrefName.SecurityLockDate).ToShortDateString();
        }
         
    }

    private void fillTreePermissionsInitial() throws Exception {
        TreeNode node = new TreeNode();
        TreeNode node2 = new TreeNode();
        //second level
        TreeNode node3 = new TreeNode();
        node = setNode("Main Menu");
        node2 = setNode(Permissions.Setup);
        node3 = setNode(Permissions.ProblemEdit);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.ChooseDatabase);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.SecurityAdmin);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Schedules);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Providers);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Blockouts);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.UserQuery);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Reports);
        node3 = setNode(Permissions.ReportDashboard);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.ReportProdInc);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.RefAttachAdd);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.RefAttachDelete);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.ReferralAdd);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.AutoNoteQuickNoteEdit);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.WikiListSetup);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode("Main Toolbar");
        node2 = setNode(Permissions.CommlogEdit);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.SheetEdit);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.TaskEdit);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.AppointmentsModule);
        node2 = setNode(Permissions.AppointmentCreate);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.AppointmentMove);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.AppointmentEdit);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.EcwAppointmentRevise);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.FamilyModule);
        node2 = setNode(Permissions.InsPlanChangeSubsc);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.CarrierCreate);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.AccountModule);
        node2 = setNode(Permissions.ClaimSentEdit);
        node.Nodes.Add(node2);
        node2 = setNode("Insurance Payment");
        node3 = setNode(Permissions.InsPayCreate);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.InsPayEdit);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode("Payment");
        node3 = setNode(Permissions.PaymentCreate);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.PaymentEdit);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode("Adjustment");
        node3 = setNode(Permissions.AdjustmentCreate);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.AdjustmentEdit);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.AdjustmentEditZero);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.TPModule);
        node2 = setNode(Permissions.TreatPlanEdit);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.ChartModule);
        node2 = setNode("Procedure");
        node3 = setNode(Permissions.ProcComplCreate);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.ProcComplEdit);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.ProcEditShowFee);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.ProcDelete);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.ProcedureNote);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.RxCreate);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.PerioEdit);
        node.Nodes.Add(node2);
        node2 = setNode("Anesthesia");
        node3 = setNode(Permissions.AnesthesiaIntakeMeds);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.AnesthesiaControlMeds);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.ImagesModule);
        node2 = setNode(Permissions.ImageDelete);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode(Permissions.ManageModule);
        node2 = setNode(Permissions.Accounting);
        node3 = setNode(Permissions.AccountingCreate);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.AccountingEdit);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Billing);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.DepositSlips);
        node.Nodes.Add(node2);
        node2 = setNode(Permissions.Backup);
        node.Nodes.Add(node2);
        node2 = setNode("Timecard");
        node3 = setNode(Permissions.TimecardsEditAll);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.TimecardDeleteEntry);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        node2 = setNode("Equipment");
        node3 = setNode(Permissions.EquipmentSetup);
        node2.Nodes.Add(node3);
        node3 = setNode(Permissions.EquipmentDelete);
        node2.Nodes.Add(node3);
        node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        node = setNode("EHR");
        node2 = setNode(Permissions.EhrEmergencyAccess);
        node.Nodes.Add(node2);
        //node2=SetNode(Permissions.EhrInfoButton);
        //	node.Nodes.Add(node2);
        //node2=SetNode(Permissions.EhrShowCDS);
        //	node.Nodes.Add(node2);
        treePermissions.Nodes.Add(node);
        treePermissions.ExpandAll();
    }

    /**
    * This just keeps FillTreePermissionsInitial looking cleaner.
    */
    private TreeNode setNode(Permissions perm) throws Exception {
        TreeNode retVal = new TreeNode();
        retVal.Text = GroupPermissions.getDesc(perm);
        retVal.Tag = perm;
        retVal.ImageIndex = 1;
        retVal.SelectedImageIndex = 1;
        return retVal;
    }

    /**
    * Only called from FillTreePermissionsInitial
    */
    private TreeNode setNode(String text) throws Exception {
        TreeNode retVal = new TreeNode();
        retVal.Text = Lan.g(this,text);
        retVal.Tag = Permissions.None;
        retVal.ImageIndex = 0;
        retVal.SelectedImageIndex = 0;
        return retVal;
    }

    private void fillUsers() throws Exception {
        UserGroups.refreshCache();
        Cache.refresh(InvalidType.Security);
        SelectedGroupNum = 0;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableSecurity","Username"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","Group"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","Employee"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","Provider"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","Clinic"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","ClinicRestricted"), 100, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSecurity","Strong"), 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String usertype = "all";
        if (comboUsers.SelectedIndex == 1)
        {
            usertype = "prov";
        }
         
        if (comboUsers.SelectedIndex == 2)
        {
            usertype = "emp";
        }
         
        if (comboUsers.SelectedIndex == 3)
        {
            usertype = "other";
        }
         
        long classNum = 0;
        if (comboSchoolClass.Visible && comboSchoolClass.SelectedIndex > 0)
        {
            classNum = SchoolClasses.getList()[comboSchoolClass.SelectedIndex - 1].SchoolClassNum;
        }
         
        ListUser = Userods.refreshSecurity(usertype,classNum);
        String userdesc = new String();
        for (int i = 0;i < ListUser.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            userdesc = ListUser[i].UserName;
            if (ListUser[i].IsHidden)
            {
                userdesc += Lan.g(this,"(hidden)");
            }
             
            row.getCells().add(userdesc);
            row.getCells().Add(UserGroups.GetGroup(ListUser[i].UserGroupNum).Description);
            row.getCells().Add(Employees.GetNameFL(ListUser[i].EmployeeNum));
            row.getCells().Add(Providers.GetLongDesc(ListUser[i].ProvNum));
            row.getCells().Add(Clinics.GetDesc(ListUser[i].ClinicNum));
            row.getCells().add(ListUser[i].ClinicIsRestricted ? "X" : "");
            row.getCells().add(ListUser[i].PasswordIsStrong ? "X" : "");
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedGroupNum = ListUser[e.getRow()].UserGroupNum;
        for (int i = 0;i < ListUser.Count;i++)
        {
            if (ListUser[i].UserGroupNum == SelectedGroupNum)
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Red);
            }
            else
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Black);
            } 
        }
        gridMain.Invalidate();
        fillTreePerm();
    }

    private void comboUsers_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillUsers();
        fillTreePerm();
    }

    private void comboSchoolClass_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillUsers();
        fillTreePerm();
    }

    private void butEditGroups_Click(Object sender, System.EventArgs e) throws Exception {
        FormUserGroups FormU = new FormUserGroups();
        FormU.ShowDialog();
        fillUsers();
        fillTreePerm();
        changed = true;
    }

    private void butAddUser_Click(Object sender, System.EventArgs e) throws Exception {
        Userod user = new Userod();
        user.UserGroupNum = SelectedGroupNum;
        FormUserEdit FormU = new FormUserEdit(user);
        FormU.IsNew = true;
        FormU.ShowDialog();
        if (FormU.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillUsers();
        fillTreePerm();
        changed = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Userod user = Userods.GetUser(ListUser[e.getRow()].UserNum);
        FormUserEdit FormU = new FormUserEdit(user);
        FormU.ShowDialog();
        if (FormU.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        if (Security.getCurUser().UserNum == user.UserNum)
        {
            Security.setCurUser(FormU.UserCur);
        }
         
        //if user changed their own password, this keeps the CurUser synched.  Needed for eCW bridge.
        fillUsers();
        for (int i = 0;i < ListUser.Count;i++)
        {
            if (ListUser[i].UserNum == FormU.UserCur.UserNum)
            {
                gridMain.setSelected(i,true);
                SelectedGroupNum = FormU.UserCur.UserGroupNum;
            }
             
        }
        fillTreePerm();
        changed = true;
    }

    private void fillTreePerm() throws Exception {
        GroupPermissions.refreshCache();
        if (SelectedGroupNum == 0)
        {
            labelPerm.Text = "";
            treePermissions.Enabled = false;
        }
        else
        {
            labelPerm.Text = Lan.g(this,"Permissions for group:") + "  " + UserGroups.getGroup(SelectedGroupNum).Description;
            treePermissions.Enabled = true;
        } 
        for (int i = 0;i < treePermissions.Nodes.Count;i++)
        {
            FillNodes(treePermissions.Nodes[i], SelectedGroupNum);
        }
    }

    /**
    * A recursive function that sets the checkbox for a node.  Also sets the text for the node.
    */
    private void fillNodes(TreeNode node, long userGroupNum) throws Exception {
        for (int i = 0;i < node.Nodes.Count;i++)
        {
            //first, any child nodes
            FillNodes(node.Nodes[i], userGroupNum);
        }
        //then this node
        if (node.ImageIndex == 0)
        {
            return ;
        }
         
        node.ImageIndex = 1;
        node.Text = GroupPermissions.getDesc((Permissions)node.Tag);
        for (int i = 0;i < GroupPermissionC.getList().Length;i++)
        {
            if (GroupPermissionC.getList()[i].UserGroupNum == userGroupNum && GroupPermissionC.getList()[i].PermType == (Permissions)node.Tag)
            {
                node.ImageIndex = 2;
                if (GroupPermissionC.getList()[i].NewerDate.Year > 1880)
                {
                    node.Text += " (" + Lan.g(this,"if date newer than") + " " + GroupPermissionC.getList()[i].NewerDate.ToShortDateString() + ")";
                }
                else if (GroupPermissionC.getList()[i].NewerDays > 0)
                {
                    node.Text += " (" + Lan.g(this,"if days newer than") + " " + GroupPermissionC.getList()[i].NewerDays.ToString() + ")";
                }
                  
            }
             
        }
    }

    private void treePermissions_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        clickedPermNode = treePermissions.GetNodeAt(e.X, e.Y);
        if (clickedPermNode == null)
        {
            return ;
        }
         
        //treePermissions.BeginUpdate();
        if (clickedPermNode.Parent == null)
        {
            //level 1
            if (e.X < 5 || e.X > 17)
            {
                return ;
            }
             
        }
        else if (clickedPermNode.Parent.Parent == null)
        {
            //level 2
            if (e.X < 24 || e.X > 36)
            {
                return ;
            }
             
        }
        else if (clickedPermNode.Parent.Parent.Parent == null)
        {
            //level 3
            if (e.X < 43 || e.X > 55)
            {
                return ;
            }
             
        }
           
        if (clickedPermNode.ImageIndex == 1)
        {
            //unchecked, so need to add a permission
            GroupPermission perm = new GroupPermission();
            perm.PermType = (Permissions)clickedPermNode.Tag;
            perm.UserGroupNum = SelectedGroupNum;
            if (GroupPermissions.permTakesDates(perm.PermType))
            {
                FormGroupPermEdit FormG = new FormGroupPermEdit(perm);
                FormG.IsNew = true;
                FormG.ShowDialog();
                if (FormG.DialogResult == DialogResult.Cancel)
                {
                    treePermissions.EndUpdate();
                    return ;
                }
                 
            }
            else
            {
                try
                {
                    GroupPermissions.insert(perm);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    return ;
                }
            
            } 
        }
        else if (clickedPermNode.ImageIndex == 2)
        {
            try
            {
                //checked, so need to delete the perm
                GroupPermissions.removePermission(SelectedGroupNum,(Permissions)clickedPermNode.Tag);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
          
        fillTreePerm();
        changed = true;
    }

    private void treePermissions_AfterSelect(Object sender, System.Windows.Forms.TreeViewEventArgs e) throws Exception {
        treePermissions.SelectedNode = null;
        treePermissions.EndUpdate();
    }

    private void treePermissions_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (clickedPermNode == null)
        {
            return ;
        }
         
        Permissions permType = (Permissions)clickedPermNode.Tag;
        if (!GroupPermissions.permTakesDates(permType))
        {
            return ;
        }
         
        GroupPermission perm = GroupPermissions.getPerm(SelectedGroupNum,(Permissions)clickedPermNode.Tag);
        if (perm == null)
        {
            return ;
        }
         
        FormGroupPermEdit FormG = new FormGroupPermEdit(perm);
        FormG.ShowDialog();
        if (FormG.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillTreePerm();
        changed = true;
    }

    private void butSetAll_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select user first.");
            return ;
        }
         
        GroupPermission perm;
        for (int i = 0;i < Enum.GetNames(Permissions.class).Length;i++)
        {
            if (i == ((Enum)Permissions.SecurityAdmin).ordinal() || i == ((Enum)Permissions.StartupMultiUserOld).ordinal() || i == ((Enum)Permissions.StartupSingleUserOld).ordinal() || i == ((Enum)Permissions.EhrKeyAdd).ordinal())
            {
                continue;
            }
             
            perm = GroupPermissions.getPerm(SelectedGroupNum,Permissions.values()[i]);
            if (perm == null)
            {
                perm = new GroupPermission();
                perm.PermType = Permissions.values()[i];
                perm.UserGroupNum = SelectedGroupNum;
                try
                {
                    GroupPermissions.insert(perm);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                changed = true;
            }
             
        }
        fillTreePerm();
    }

    private void checkTimecardSecurityEnabled_Click(Object sender, EventArgs e) throws Exception {
        checkCannotEditOwn.Enabled = checkTimecardSecurityEnabled.Checked;
    }

    private void checkPasswordsMustBeStrong_Click(Object sender, EventArgs e) throws Exception {
        if (checkPasswordsMustBeStrong.Checked)
        {
            Prefs.updateBool(PrefName.PasswordsMustBeStrong,true);
            DataValid.setInvalid(InvalidType.Prefs);
        }
        else
        {
            //unchecking the box
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning.  If this box is unchecked, the strong password flag on all users will be reset.  If strong passwords are again turned on later, then each users will have to edit their password in order to cause the strong password flag to be set again."))
            {
                checkPasswordsMustBeStrong.Checked = true;
                return ;
            }
             
            //recheck it.
            Userods.resetStrongPasswordFlags();
            Prefs.updateBool(PrefName.PasswordsMustBeStrong,false);
            DataValid.setInvalid(InvalidType.Security,InvalidType.Prefs);
            fillUsers();
            fillTreePerm();
        } 
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        FormSecurityLock FormS = new FormSecurityLock();
        FormS.ShowDialog();
        //prefs are set invalid within that form if needed.
        if (PrefC.getInt(PrefName.SecurityLockDays) > 0)
        {
            textDaysLock.Text = PrefC.getInt(PrefName.SecurityLockDays).ToString();
        }
        else
        {
            textDaysLock.Text = "";
        } 
        if (PrefC.getDate(PrefName.SecurityLockDate).Year > 1880)
        {
            textDateLock.Text = PrefC.getDate(PrefName.SecurityLockDate).ToShortDateString();
        }
        else
        {
            textDateLock.Text = "";
        } 
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formSecurity_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (!StringSupport.equals(textLogOffAfterMinutes.Text, ""))
        {
            try
            {
                Int32.Parse(textLogOffAfterMinutes.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Log off after minutes is invalid.");
                e.Cancel = true;
                return ;
            }
        
        }
         
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Security);
        }
         
        //Prefs.UpdateBool(PrefName.PasswordsMustBeStrong,checkPasswordsMustBeStrong.Checked) //handled when box clicked.
        if (Prefs.UpdateBool(PrefName.TimecardSecurityEnabled, checkTimecardSecurityEnabled.Checked) | Prefs.UpdateBool(PrefName.TimecardUsersDontEditOwnCard, checkCannotEditOwn.Checked) | Prefs.UpdateBool(PrefName.SecurityLogOffWithWindows, checkLogOffWindows.Checked) | Prefs.UpdateInt(PrefName.SecurityLogOffAfterMinutes, PIn.Int(textLogOffAfterMinutes.Text)))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

}


