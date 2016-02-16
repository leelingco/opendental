//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Security;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

public class FormTaskSendUser  extends Form 
{

    private List<TaskList> FilteredList = new List<TaskList>();
    /**
    * If OK, then this will contain the selected TaskListNum
    */
    public long TaskListNum = new long();
    public FormTaskSendUser() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTaskSendUser_Load(Object sender, EventArgs e) throws Exception {
        List<Userod> UserList = Userods.getNotHidden();
        List<TaskList> TrunkList = TaskLists.refreshMainTrunk(Security.getCurUser().UserNum);
        FilteredList = new List<TaskList>();
        for (int i = 0;i < UserList.Count;i++)
        {
            if (UserList[i].TaskListInBox == 0)
            {
                continue;
            }
             
            for (int t = 0;t < TrunkList.Count;t++)
            {
                if (TrunkList[t].TaskListNum == UserList[i].TaskListInBox)
                {
                    FilteredList.Add(TrunkList[t]);
                    listMain.Items.Add(TrunkList[t].Descript);
                }
                 
            }
        }
    }

    private void listMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        TaskListNum = FilteredList[listMain.SelectedIndex].TaskListNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        TaskListNum = FilteredList[listMain.SelectedIndex].TaskListNum;
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
        this.label1 = new System.Windows.Forms.Label();
        this.listMain = new System.Windows.Forms.ListBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(234, 42);
        this.label1.TabIndex = 4;
        this.label1.Text = "Pick user to send to.  If the user is not in the list, then their inbox has not b" + "een setup yet.";
        //
        // listMain
        //
        this.listMain.FormattingEnabled = true;
        this.listMain.Location = new System.Drawing.Point(40, 69);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(120, 316);
        this.listMain.TabIndex = 6;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(210, 320);
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
        this.butCancel.Location = new System.Drawing.Point(210, 361);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormTaskSendUser
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(310, 412);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormTaskSendUser";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Task Send User";
        this.Load += new System.EventHandler(this.FormTaskSendUser_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
}


