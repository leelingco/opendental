//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Security;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;
import OpenDental.FormTaskSetup;
import OpenDental.Properties.Resources;

public class FormTaskSetup  extends Form 
{

    private List<Userod> UserList = new List<Userod>();
    private List<Userod> UserListOld = new List<Userod>();
    private List<TaskList> TrunkList = new List<TaskList>();
    public FormTaskSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTaskSetup_Load(Object sender, EventArgs e) throws Exception {
        UserList = Userods.getNotHidden();
        UserListOld = Userods.getNotHidden();
        TrunkList = TaskLists.refreshMainTrunk(Security.getCurUser().UserNum);
        listMain.Items.Add(Lan.g(this,"none"));
        for (int i = 0;i < TrunkList.Count;i++)
        {
            listMain.Items.Add(TrunkList[i].Descript);
        }
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //doesn't refresh from db because nothing actually gets saved until we hit the OK button.
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableTaskSetup","User"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTaskSetup","Inbox"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < UserList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(UserList[i].UserName);
            row.getCells().Add(GetDescription(UserList[i].TaskListInBox));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private String getDescription(long taskListNum) throws Exception {
        if (taskListNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < TrunkList.Count;i++)
        {
            if (TrunkList[i].TaskListNum == taskListNum)
            {
                return TrunkList[i].Descript;
            }
             
        }
        return "";
    }

    private void butSet_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a user first.");
            return ;
        }
         
        if (listMain.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item from the list first.");
            return ;
        }
         
        if (listMain.SelectedIndex == 0)
        {
            UserList[gridMain.getSelectedIndex()].TaskListInBox = 0;
        }
        else
        {
            UserList[gridMain.getSelectedIndex()].TaskListInBox = TrunkList[listMain.SelectedIndex - 1].TaskListNum;
        } 
        fillGrid();
        listMain.SelectedIndex = -1;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        boolean changed = false;
        for (int i = 0;i < UserList.Count;i++)
        {
            if (UserList[i].TaskListInBox != UserListOld[i].TaskListInBox)
            {
                Userods.Update(UserList[i]);
                changed = true;
            }
             
        }
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Security);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTaskSetup.class);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.listMain = new System.Windows.Forms.ListBox();
        this.butSet = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(645, 113);
        this.label1.TabIndex = 4;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(116, 114);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(231, 393);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle(null);
        this.gridMain.setTranslationName(null);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(625, 442);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(625, 483);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listMain
        //
        this.listMain.FormattingEnabled = true;
        this.listMain.Location = new System.Drawing.Point(495, 146);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(120, 264);
        this.listMain.TabIndex = 6;
        //
        // butSet
        //
        this.butSet.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSet.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSet.setAutosize(true);
        this.butSet.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSet.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSet.setCornerRadius(4F);
        this.butSet.Image = Resources.getLeft();
        this.butSet.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSet.Location = new System.Drawing.Point(414, 228);
        this.butSet.Name = "butSet";
        this.butSet.Size = new System.Drawing.Size(75, 24);
        this.butSet.TabIndex = 7;
        this.butSet.Text = "Set";
        this.butSet.Click += new System.EventHandler(this.butSet_Click);
        //
        // FormTaskSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.butSet);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormTaskSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Task Setup";
        this.Load += new System.EventHandler(this.FormTaskSetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butSet;
}


