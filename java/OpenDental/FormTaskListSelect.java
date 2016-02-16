//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import OpenDental.FormTaskListSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.TaskObjectType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTaskListSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private TaskObjectType OType = TaskObjectType.None;
    /**
    * If dialog result=ok, this will contain the TaskListNum that was selected.
    */
    private TaskList[] TaskListList = new TaskList[]();
    public long SelectedTaskListNum = new long();
    /**
    * 
    */
    public FormTaskListSelect(TaskObjectType oType) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        OType = oType;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTaskListSelect.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listMain = new System.Windows.Forms.ListBox();
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
        this.butCancel.Location = new System.Drawing.Point(257, 407);
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
        this.butOK.Location = new System.Drawing.Point(257, 366);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(12, 12);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(225, 420);
        this.listMain.TabIndex = 2;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // FormTaskListSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(349, 446);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTaskListSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Task List";
        this.Load += new System.EventHandler(this.FormTaskListSelect_Load);
        this.ResumeLayout(false);
    }

    private void formTaskListSelect_Load(Object sender, System.EventArgs e) throws Exception {
        TaskListList = TaskLists.getForObjectType(OType);
        for (int i = 0;i < TaskListList.Length;i++)
        {
            listMain.Items.Add(TaskListList[i].Descript);
        }
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        SelectedTaskListNum = TaskListList[listMain.SelectedIndex].TaskListNum;
        /*
        			Task task=new Task();
        			task.TaskListNum=-1;//don't show it in any list yet.
        			Tasks.Insert(task);
        			task.KeyNum=KeyNum;
        			task.ObjectType=OType;
        			task.TaskListNum=TaskListList[listMain.SelectedIndex].TaskListNum;
        			FormTaskEdit FormT=new FormTaskEdit(task);
        			FormT.IsNew=true;
        			FormT.Show();*/
        DialogResult = DialogResult.OK;
    }

    //Close();
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a task list first.");
            return ;
        }
         
        SelectedTaskListNum = TaskListList[listMain.SelectedIndex].TaskListNum;
        /*
        			Task task=new Task();
        			task.TaskListNum=-1;//don't show it in any list yet.
        			Tasks.Insert(task);
        			task.KeyNum=KeyNum;
        			task.ObjectType=OType;
        			task.TaskListNum=TaskListList[listMain.SelectedIndex].TaskListNum;
        			FormTaskEdit FormT=new FormTaskEdit(task);
        			FormT.IsNew=true;
        			FormT.Show();*/
        DialogResult = DialogResult.OK;
    }

    //Close();
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


//Close();