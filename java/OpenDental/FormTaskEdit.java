//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormApptsOther;
import OpenDental.FormPatientSelect;
import OpenDental.FormTaskEdit;
import OpenDental.FormTaskListSelect;
import OpenDental.FormTaskNoteEdit;
import OpenDental.FormTaskNoteEdit.DelegateEditComplete;
import OpenDental.FormTaskSendUser;
import OpenDental.FormUserPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.TaskNote;
import OpenDentBusiness.TaskNotes;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.TaskStatusEnum;
import OpenDentBusiness.TaskUnreads;
import OpenDentBusiness.Userods;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTaskEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textDescript;
    private Task TaskCur;
    private Task TaskOld;
    private OpenDental.ValidDate textDateTask;
    private OpenDental.UI.Button butChange;
    private OpenDental.UI.Button butGoto;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.CheckBox checkFromNum = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelObjectDesc = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textObjectDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listObjectType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Panel panelObject = new System.Windows.Forms.Panel();
    /**
    * After closing, if this is not zero, then it will jump to the object specified in GotoKeyNum.
    */
    public TaskObjectType GotoType = TaskObjectType.None;
    private Label label5 = new Label();
    private TextBox textDateTimeEntry = new TextBox();
    private OpenDental.UI.Button butNow;
    private OpenDental.UI.Button butDelete;
    private TextBox textUser = new TextBox();
    private Label label16 = new Label();
    private OpenDental.UI.Button butNowFinished;
    private TextBox textDateTimeFinished = new TextBox();
    private Label label7 = new Label();
    private TextBox textTaskNum = new TextBox();
    private Label labelTaskNum = new Label();
    /**
    * After closing, if this is not zero, then it will jump to the specified patient.
    */
    public long GotoKeyNum = new long();
    private Label labelReply = new Label();
    private OpenDental.UI.Button butReply;
    private OpenDental.UI.Button butSend;
    private TextBox textTaskList = new TextBox();
    private Label label10 = new Label();
    private ComboBox comboDateType = new ComboBox();
    private TaskList TaskListCur;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * Will be set to true if any note was added or an existing note changed. Does not track changes in the description.
    */
    private boolean notesChanged = new boolean();
    private OpenDental.UI.Button butAddNote;
    private OpenDental.UI.Button butChangeUser;
    private List<TaskNote> NoteList = new List<TaskNote>();
    private CheckBox checkNew = new CheckBox();
    private CheckBox checkDone = new CheckBox();
    private Label labelDoneAffectsAll = new Label();
    /**
    * If the reply button is visible, this stores who to reply to.  It's determined when loading the form.
    */
    private long ReplyToUserNum = new long();
    /**
    * Gets set to true externally if this window popped up without user interaction.  It will behave slightly differently.  Specifically, the New checkbox will be unchecked so that if user clicks OK, the task will be marked as read.
    */
    public boolean IsPopup = new boolean();
    /**
    * When tracking status by user, this tracks whether it has changed.  This is so that if it has changed, a signal can be sent for a refresh of lists.
    */
    private boolean StatusChanged = new boolean();
    /**
    * If this task starts out 'unread', then this starts out true.  If the user changes the description or changes a note, then the task gets set to read.  But the user can manually change it back and this variable gets set to false.  From then on, any changes to description or note do not trigger the task to get set to read.  In other words, the automation only happens once.
    */
    private boolean MightNeedSetRead = new boolean();
    private OpenDental.UI.Button butCopy;
    private TextBox textBox1 = new TextBox();
    private OpenDental.UI.Button butRed;
    private OpenDental.UI.Button butBlue;
    /**
    * When this window is first opened, if this task is in someone else's inbox, then the "new" status is meaningless and will not show.  In that case, this variable is set to true.  Only used when tracking new status by user.
    */
    private boolean StartedInOthersInbox = new boolean();
    /**
    * Task gets inserted ahead of time, then frequently altered before passing in here.  The taskOld that is passed in should be the task as it is in the database.  When saving, taskOld will be compared with db to make sure no changes.
    */
    public FormTaskEdit(Task taskCur, Task taskOld) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        TaskCur = taskCur;
        TaskOld = taskOld;
        TaskListCur = TaskLists.getOne(taskCur.TaskListNum);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTaskEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.checkFromNum = new System.Windows.Forms.CheckBox();
        this.labelObjectDesc = new System.Windows.Forms.Label();
        this.textObjectDesc = new System.Windows.Forms.TextBox();
        this.listObjectType = new System.Windows.Forms.ListBox();
        this.label6 = new System.Windows.Forms.Label();
        this.panelObject = new System.Windows.Forms.Panel();
        this.butGoto = new OpenDental.UI.Button();
        this.butChange = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.textDateTimeEntry = new System.Windows.Forms.TextBox();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textDateTimeFinished = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textTaskNum = new System.Windows.Forms.TextBox();
        this.labelTaskNum = new System.Windows.Forms.Label();
        this.labelReply = new System.Windows.Forms.Label();
        this.textTaskList = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.comboDateType = new System.Windows.Forms.ComboBox();
        this.checkNew = new System.Windows.Forms.CheckBox();
        this.checkDone = new System.Windows.Forms.CheckBox();
        this.labelDoneAffectsAll = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butRed = new OpenDental.UI.Button();
        this.butChangeUser = new OpenDental.UI.Button();
        this.butAddNote = new OpenDental.UI.Button();
        this.butSend = new OpenDental.UI.Button();
        this.butReply = new OpenDental.UI.Button();
        this.butNowFinished = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butNow = new OpenDental.UI.Button();
        this.textDateTask = new OpenDental.ValidDate();
        this.butCopy = new OpenDental.UI.Button();
        this.textDescript = new OpenDental.ODtextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butBlue = new OpenDental.UI.Button();
        this.panelObject.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 73);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(116, 19);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(9, 538);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(116, 19);
        this.label2.TabIndex = 4;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label3.Location = new System.Drawing.Point(218, 535);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(185, 32);
        this.label3.TabIndex = 6;
        this.label3.Text = "Leave blank unless you want this task to show on a dated list";
        //
        // label4
        //
        this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label4.Location = new System.Drawing.Point(9, 564);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(116, 19);
        this.label4.TabIndex = 7;
        this.label4.Text = "Date Type";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkFromNum
        //
        this.checkFromNum.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkFromNum.CheckAlign = System.Drawing.ContentAlignment.TopRight;
        this.checkFromNum.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkFromNum.Location = new System.Drawing.Point(8, 590);
        this.checkFromNum.Name = "checkFromNum";
        this.checkFromNum.Size = new System.Drawing.Size(133, 18);
        this.checkFromNum.TabIndex = 3;
        this.checkFromNum.Text = "Is From Repeating";
        this.checkFromNum.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelObjectDesc
        //
        this.labelObjectDesc.Location = new System.Drawing.Point(5, 1);
        this.labelObjectDesc.Name = "labelObjectDesc";
        this.labelObjectDesc.Size = new System.Drawing.Size(116, 19);
        this.labelObjectDesc.TabIndex = 8;
        this.labelObjectDesc.Text = "ObjectDesc";
        this.labelObjectDesc.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textObjectDesc
        //
        this.textObjectDesc.Location = new System.Drawing.Point(124, 0);
        this.textObjectDesc.Multiline = true;
        this.textObjectDesc.Name = "textObjectDesc";
        this.textObjectDesc.Size = new System.Drawing.Size(241, 34);
        this.textObjectDesc.TabIndex = 0;
        this.textObjectDesc.Text = "line 1\r\nline 2";
        //
        // listObjectType
        //
        this.listObjectType.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.listObjectType.Location = new System.Drawing.Point(388, 565);
        this.listObjectType.Name = "listObjectType";
        this.listObjectType.Size = new System.Drawing.Size(120, 43);
        this.listObjectType.TabIndex = 13;
        this.listObjectType.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listObjectType_MouseDown);
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label6.Location = new System.Drawing.Point(269, 564);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(116, 19);
        this.label6.TabIndex = 14;
        this.label6.Text = "Object Type";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelObject
        //
        this.panelObject.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.panelObject.Controls.Add(this.textObjectDesc);
        this.panelObject.Controls.Add(this.labelObjectDesc);
        this.panelObject.Controls.Add(this.butGoto);
        this.panelObject.Controls.Add(this.butChange);
        this.panelObject.Location = new System.Drawing.Point(3, 609);
        this.panelObject.Name = "panelObject";
        this.panelObject.Size = new System.Drawing.Size(550, 34);
        this.panelObject.TabIndex = 15;
        //
        // butGoto
        //
        this.butGoto.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGoto.setAutosize(true);
        this.butGoto.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGoto.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGoto.setCornerRadius(4F);
        this.butGoto.Location = new System.Drawing.Point(449, 6);
        this.butGoto.Name = "butGoto";
        this.butGoto.Size = new System.Drawing.Size(75, 22);
        this.butGoto.TabIndex = 12;
        this.butGoto.Text = "Go To";
        this.butGoto.Click += new System.EventHandler(this.butGoto_Click);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(369, 6);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(75, 22);
        this.butChange.TabIndex = 10;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 25);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(116, 19);
        this.label5.TabIndex = 17;
        this.label5.Text = "Date/Time Entry";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTimeEntry
        //
        this.textDateTimeEntry.Location = new System.Drawing.Point(127, 24);
        this.textDateTimeEntry.Name = "textDateTimeEntry";
        this.textDateTimeEntry.Size = new System.Drawing.Size(151, 20);
        this.textDateTimeEntry.TabIndex = 18;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(594, 16);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(134, 20);
        this.textUser.TabIndex = 0;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(499, 18);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(94, 16);
        this.label16.TabIndex = 125;
        this.label16.Text = "From User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTimeFinished
        //
        this.textDateTimeFinished.Location = new System.Drawing.Point(127, 49);
        this.textDateTimeFinished.Name = "textDateTimeFinished";
        this.textDateTimeFinished.Size = new System.Drawing.Size(151, 20);
        this.textDateTimeFinished.TabIndex = 131;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(9, 50);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(116, 19);
        this.label7.TabIndex = 130;
        this.label7.Text = "Date/Time Finished";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTaskNum
        //
        this.textTaskNum.Location = new System.Drawing.Point(454, 37);
        this.textTaskNum.Name = "textTaskNum";
        this.textTaskNum.ReadOnly = true;
        this.textTaskNum.Size = new System.Drawing.Size(54, 20);
        this.textTaskNum.TabIndex = 134;
        this.textTaskNum.Visible = false;
        //
        // labelTaskNum
        //
        this.labelTaskNum.Location = new System.Drawing.Point(379, 38);
        this.labelTaskNum.Name = "labelTaskNum";
        this.labelTaskNum.Size = new System.Drawing.Size(73, 16);
        this.labelTaskNum.TabIndex = 133;
        this.labelTaskNum.Text = "TaskNum";
        this.labelTaskNum.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelTaskNum.Visible = false;
        //
        // labelReply
        //
        this.labelReply.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelReply.Location = new System.Drawing.Point(312, 652);
        this.labelReply.Name = "labelReply";
        this.labelReply.Size = new System.Drawing.Size(162, 19);
        this.labelReply.TabIndex = 141;
        this.labelReply.Text = "(Send to author)";
        this.labelReply.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textTaskList
        //
        this.textTaskList.Location = new System.Drawing.Point(594, 39);
        this.textTaskList.Name = "textTaskList";
        this.textTaskList.ReadOnly = true;
        this.textTaskList.Size = new System.Drawing.Size(134, 20);
        this.textTaskList.TabIndex = 146;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(499, 41);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(94, 16);
        this.label10.TabIndex = 147;
        this.label10.Text = "Task List";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboDateType
        //
        this.comboDateType.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.comboDateType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDateType.FormattingEnabled = true;
        this.comboDateType.Location = new System.Drawing.Point(127, 564);
        this.comboDateType.Name = "comboDateType";
        this.comboDateType.Size = new System.Drawing.Size(145, 21);
        this.comboDateType.TabIndex = 148;
        //
        // checkNew
        //
        this.checkNew.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkNew.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNew.Location = new System.Drawing.Point(47, 5);
        this.checkNew.Name = "checkNew";
        this.checkNew.Size = new System.Drawing.Size(94, 17);
        this.checkNew.TabIndex = 152;
        this.checkNew.Text = "New";
        this.checkNew.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkNew.Click += new System.EventHandler(this.checkNew_Click);
        //
        // checkDone
        //
        this.checkDone.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkDone.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDone.Location = new System.Drawing.Point(147, 5);
        this.checkDone.Name = "checkDone";
        this.checkDone.Size = new System.Drawing.Size(94, 17);
        this.checkDone.TabIndex = 153;
        this.checkDone.Text = "Done";
        this.checkDone.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkDone.Click += new System.EventHandler(this.checkDone_Click);
        //
        // labelDoneAffectsAll
        //
        this.labelDoneAffectsAll.Location = new System.Drawing.Point(241, 5);
        this.labelDoneAffectsAll.Name = "labelDoneAffectsAll";
        this.labelDoneAffectsAll.Size = new System.Drawing.Size(167, 16);
        this.labelDoneAffectsAll.TabIndex = 154;
        this.labelDoneAffectsAll.Text = "(affects all users)";
        this.labelDoneAffectsAll.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 196);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(798, 338);
        this.gridMain.TabIndex = 149;
        this.gridMain.setTitle("Notes");
        this.gridMain.setTranslationName("FormTaskEdit");
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
        //
        // textBox1
        //
        this.textBox1.Location = new System.Drawing.Point(454, -72);
        this.textBox1.Name = "textBox1";
        this.textBox1.ReadOnly = true;
        this.textBox1.Size = new System.Drawing.Size(54, 20);
        this.textBox1.TabIndex = 134;
        this.textBox1.Visible = false;
        //
        // butRed
        //
        this.butRed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRed.setAutosize(true);
        this.butRed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRed.setCornerRadius(4F);
        this.butRed.Location = new System.Drawing.Point(82, 169);
        this.butRed.Name = "butRed";
        this.butRed.Size = new System.Drawing.Size(43, 24);
        this.butRed.TabIndex = 155;
        this.butRed.Text = "Red";
        this.butRed.Click += new System.EventHandler(this.butRed_Click);
        //
        // butChangeUser
        //
        this.butChangeUser.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangeUser.setAutosize(true);
        this.butChangeUser.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangeUser.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangeUser.setCornerRadius(4F);
        this.butChangeUser.Location = new System.Drawing.Point(731, 14);
        this.butChangeUser.Name = "butChangeUser";
        this.butChangeUser.Size = new System.Drawing.Size(24, 22);
        this.butChangeUser.TabIndex = 151;
        this.butChangeUser.Text = "...";
        this.butChangeUser.Click += new System.EventHandler(this.butChangeUser_Click);
        //
        // butAddNote
        //
        this.butAddNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddNote.setAutosize(true);
        this.butAddNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNote.setCornerRadius(4F);
        this.butAddNote.Image = Resources.getAdd();
        this.butAddNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNote.Location = new System.Drawing.Point(735, 540);
        this.butAddNote.Name = "butAddNote";
        this.butAddNote.Size = new System.Drawing.Size(75, 24);
        this.butAddNote.TabIndex = 150;
        this.butAddNote.Text = "Add";
        this.butAddNote.Click += new System.EventHandler(this.butAddNote_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(478, 649);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 24);
        this.butSend.TabIndex = 142;
        this.butSend.Text = "Send To...";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butReply
        //
        this.butReply.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReply.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butReply.setAutosize(true);
        this.butReply.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReply.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReply.setCornerRadius(4F);
        this.butReply.Location = new System.Drawing.Point(233, 649);
        this.butReply.Name = "butReply";
        this.butReply.Size = new System.Drawing.Size(75, 24);
        this.butReply.TabIndex = 140;
        this.butReply.Text = "Reply";
        this.butReply.Click += new System.EventHandler(this.butReply_Click);
        //
        // butNowFinished
        //
        this.butNowFinished.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNowFinished.setAutosize(true);
        this.butNowFinished.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNowFinished.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNowFinished.setCornerRadius(4F);
        this.butNowFinished.Location = new System.Drawing.Point(284, 47);
        this.butNowFinished.Name = "butNowFinished";
        this.butNowFinished.Size = new System.Drawing.Size(62, 24);
        this.butNowFinished.TabIndex = 132;
        this.butNowFinished.Text = "Now";
        this.butNowFinished.Click += new System.EventHandler(this.butNowFinished_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(21, 649);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(80, 24);
        this.butDelete.TabIndex = 124;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butNow
        //
        this.butNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow.setAutosize(true);
        this.butNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow.setCornerRadius(4F);
        this.butNow.Location = new System.Drawing.Point(284, 22);
        this.butNow.Name = "butNow";
        this.butNow.Size = new System.Drawing.Size(62, 24);
        this.butNow.TabIndex = 19;
        this.butNow.Text = "Now";
        this.butNow.Click += new System.EventHandler(this.butNow_Click);
        //
        // textDateTask
        //
        this.textDateTask.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textDateTask.Location = new System.Drawing.Point(127, 538);
        this.textDateTask.Name = "textDateTask";
        this.textDateTask.Size = new System.Drawing.Size(87, 20);
        this.textDateTask.TabIndex = 2;
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Image = Resources.getbutCopy();
        this.butCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy.Location = new System.Drawing.Point(653, 540);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(75, 24);
        this.butCopy.TabIndex = 4;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // textDescript
        //
        this.textDescript.AcceptsTab = true;
        this.textDescript.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textDescript.DetectUrls = false;
        this.textDescript.Location = new System.Drawing.Point(127, 73);
        this.textDescript.Name = "textDescript";
        this.textDescript.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textDescript.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textDescript.Size = new System.Drawing.Size(683, 121);
        this.textDescript.TabIndex = 1;
        this.textDescript.Text = "";
        this.textDescript.TextChanged += new System.EventHandler(this.textDescript_TextChanged);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(653, 649);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(735, 649);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butBlue
        //
        this.butBlue.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBlue.setAutosize(true);
        this.butBlue.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBlue.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBlue.setCornerRadius(4F);
        this.butBlue.Location = new System.Drawing.Point(82, 139);
        this.butBlue.Name = "butBlue";
        this.butBlue.Size = new System.Drawing.Size(43, 24);
        this.butBlue.TabIndex = 156;
        this.butBlue.Text = "Blue";
        this.butBlue.Click += new System.EventHandler(this.butBlue_Click);
        //
        // FormTaskEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(836, 676);
        this.Controls.Add(this.butBlue);
        this.Controls.Add(this.butRed);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.textTaskNum);
        this.Controls.Add(this.labelTaskNum);
        this.Controls.Add(this.checkDone);
        this.Controls.Add(this.labelDoneAffectsAll);
        this.Controls.Add(this.checkNew);
        this.Controls.Add(this.butChangeUser);
        this.Controls.Add(this.butAddNote);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.comboDateType);
        this.Controls.Add(this.textTaskList);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.labelReply);
        this.Controls.Add(this.butReply);
        this.Controls.Add(this.butNowFinished);
        this.Controls.Add(this.textDateTimeFinished);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butNow);
        this.Controls.Add(this.textDateTimeEntry);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.panelObject);
        this.Controls.Add(this.listObjectType);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textDateTask);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.checkFromNum);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.Name = "FormTaskEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Task";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormTaskEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormTaskListEdit_Load);
        this.panelObject.ResumeLayout(false);
        this.panelObject.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTaskListEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
        }
        else
        {
            //butDelete.Enabled always stays true
            //textDescript always editable
            //trying to edit an existing task, so need to block some things
            if (!Security.isAuthorized(Permissions.TaskEdit,true))
            {
                //but only need to block them if they don't have TaskEdit permission
                if (TaskCur.UserNum != Security.getCurUser().UserNum)
                {
                    //current user didn't write this task, so block them.
                    butDelete.Enabled = false;
                    textDescript.ReadOnly = true;
                    textDescript.BackColor = System.Drawing.SystemColors.Window;
                }
                 
                if (TaskCur.TaskListNum != Security.getCurUser().TaskListInBox)
                {
                    //the task is not in the logged-in user's inbox
                    butDelete.Enabled = false;
                    textDescript.ReadOnly = true;
                    textDescript.BackColor = System.Drawing.SystemColors.Window;
                }
                 
                if (butDelete.Enabled)
                {
                    //this just allows getting the NoteList less often
                    NoteList = TaskNotes.getForTask(TaskCur.TaskNum);
                    for (int i = 0;i < NoteList.Count;i++)
                    {
                        //so we can check so see if other users have added notes
                        if (Security.getCurUser().UserNum != NoteList[i].UserNum)
                        {
                            butDelete.Enabled = false;
                            textDescript.ReadOnly = true;
                            textDescript.BackColor = System.Drawing.SystemColors.Window;
                            break;
                        }
                         
                    }
                }
                 
            }
             
        } 
        textUser.Text = Userods.getName(TaskCur.UserNum);
        //might be blank.
        if (TaskListCur != null)
        {
            textTaskList.Text = TaskListCur.Descript;
        }
         
        if (TaskCur.DateTimeEntry.Year < 1880)
        {
            textDateTimeEntry.Text = DateTime.Now.ToString();
        }
        else
        {
            textDateTimeEntry.Text = TaskCur.DateTimeEntry.ToString();
        } 
        if (TaskCur.DateTimeFinished.Year < 1880)
        {
            textDateTimeFinished.Text = "";
        }
        else
        {
            //DateTime.Now.ToString();
            textDateTimeFinished.Text = TaskCur.DateTimeFinished.ToString();
        } 
        textDescript.Text = TaskCur.Descript;
        if (!IsPopup)
        {
            //otherwise, TextUser is selected, and it cannot accept input.  This prevents a popup from accepting using input accidentally.
            textDescript.Select();
            //Focus does not work for some reason.
            textDescript.Select(TaskCur.Descript.Length, 0);
        }
         
        //Place the cursor at the end of the description box.
        if (PrefC.getBool(PrefName.TasksNewTrackedByUser) && TaskCur.TaskListNum != 0)
        {
            long mailboxUserNum = TaskLists.getMailboxUserNum(TaskCur.TaskListNum);
            if (mailboxUserNum != 0 && mailboxUserNum != Security.getCurUser().UserNum)
            {
                StartedInOthersInbox = true;
                checkNew.Checked = false;
                checkNew.Enabled = false;
            }
             
        }
         
        //this section must come after textDescript is set:
        if (TaskCur.TaskStatus == TaskStatusEnum.Done)
        {
            //global even if new status is tracked by user
            checkDone.Checked = true;
        }
        else
        {
            //because it can't be both new and done.
            if (IsPopup)
            {
                //It clearly is Unread, but we don't want to leave it that way upon close OK.
                checkNew.Checked = false;
                StatusChanged = true;
            }
            else if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
            {
                if (!StartedInOthersInbox && TaskUnreads.isUnread(Security.getCurUser().UserNum,TaskCur.TaskNum))
                {
                    checkNew.Checked = true;
                    MightNeedSetRead = true;
                }
                 
            }
            else
            {
                //tracked globally, the old way
                if (TaskCur.TaskStatus == TaskStatusEnum.New)
                {
                    checkNew.Checked = true;
                }
                 
            }  
        } 
        if (TaskCur.DateTask.Year > 1880)
        {
            textDateTask.Text = TaskCur.DateTask.ToShortDateString();
        }
         
        if (TaskCur.IsRepeating)
        {
            checkNew.Enabled = false;
            checkDone.Enabled = false;
            textDateTask.Enabled = false;
            listObjectType.Enabled = false;
            if (TaskCur.TaskListNum != 0)
            {
                //not a main parent
                comboDateType.Enabled = false;
            }
             
        }
         
        for (int i = 0;i < Enum.GetNames(TaskDateType.class).Length;i++)
        {
            comboDateType.Items.Add(Lan.g("enumTaskDateType", Enum.GetNames(TaskDateType.class)[i]));
            if (((Enum)TaskCur.DateType).ordinal() == i)
            {
                comboDateType.SelectedIndex = i;
            }
             
        }
        if (TaskCur.FromNum == 0)
        {
            checkFromNum.Checked = false;
            checkFromNum.Enabled = false;
        }
        else
        {
            checkFromNum.Checked = true;
        } 
        for (int i = 0;i < Enum.GetNames(TaskObjectType.class).Length;i++)
        {
            listObjectType.Items.Add(Lan.g("enumTaskObjectType", Enum.GetNames(TaskObjectType.class)[i]));
        }
        fillObject();
        fillGrid();
        //Need this in order to pick ReplyToUserNum next.
        if (IsNew)
        {
            labelReply.Visible = false;
            butReply.Visible = false;
        }
        else if (TaskListCur == null)
        {
            //|| TaskListCur.TaskListNum!=Security.CurUser.TaskListInBox) {//if this task is not in my inbox
            labelReply.Visible = false;
            butReply.Visible = false;
        }
        else if (NoteList.Count == 0 && TaskCur.UserNum == Security.getCurUser().UserNum)
        {
            //if this is my task
            labelReply.Visible = false;
            butReply.Visible = false;
        }
        else
        {
            //reply button will be visible
            if (NoteList.Count == 0)
            {
                //no notes, so reply to owner
                ReplyToUserNum = TaskCur.UserNum;
            }
            else
            {
                for (int i = NoteList.Count - 1;i >= 0;i--)
                {
                    //reply to most recent author who is not me
                    //loop backward through the notes to find who to reply to
                    if (NoteList[i].UserNum != Security.getCurUser().UserNum)
                    {
                        ReplyToUserNum = NoteList[i].UserNum;
                        break;
                    }
                     
                }
                if (ReplyToUserNum == 0)
                {
                    //can't figure out who to reply to.
                    labelReply.Visible = false;
                    butReply.Visible = false;
                }
                 
            } 
            labelReply.Text = Lan.g(this,"(Send to ") + Userods.getName(ReplyToUserNum) + ")";
        }   
        if (IsNew || !PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            butRed.Visible = false;
            butBlue.Visible = false;
        }
         
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date Time"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"User"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),400);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        NoteList = TaskNotes.getForTask(TaskCur.TaskNum);
        for (int i = 0;i < NoteList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(NoteList[i].DateTimeNote.ToShortDateString() + " " + NoteList[i].DateTimeNote.ToShortTimeString());
            row.getCells().Add(Userods.GetName(NoteList[i].UserNum));
            row.getCells().Add(NoteList[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.scrollToEnd();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        FormTaskNoteEdit form = new FormTaskNoteEdit();
        form.TaskNoteCur = NoteList[e.getRow()];
        form.EditComplete = new DelegateEditComplete() 
          { 
            public System.Void invoke(System.Object sender) throws Exception {
                onNoteEditComplete_CellDoubleClick(sender);
            }

            public List<DelegateEditComplete> getInvocationList() throws Exception {
                List<DelegateEditComplete> ret = new ArrayList<DelegateEditComplete>();
                ret.add(this);
                return ret;
            }
        
          };
        form.Show(this);
    }

    //non-modal subwindow, but if the parent is closed by the user when the child is open, then the child is forced closed along with the parent and after the parent.
    private void onNoteEditComplete_CellDoubleClick(Object sender) throws Exception {
        notesChanged = true;
        fillGrid();
    }

    private void butAddNote_Click(Object sender, EventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        FormTaskNoteEdit form = new FormTaskNoteEdit();
        form.TaskNoteCur = new TaskNote();
        form.TaskNoteCur.TaskNum = TaskCur.TaskNum;
        form.TaskNoteCur.DateTimeNote = DateTime.Now;
        //Will be slightly adjusted at server.
        form.TaskNoteCur.UserNum = Security.getCurUser().UserNum;
        form.TaskNoteCur.setIsNew(true);
        form.EditComplete = new DelegateEditComplete() 
          { 
            public System.Void invoke(System.Object sender) throws Exception {
                onNoteEditComplete_Add(sender);
            }

            public List<DelegateEditComplete> getInvocationList() throws Exception {
                List<DelegateEditComplete> ret = new ArrayList<DelegateEditComplete>();
                ret.add(this);
                return ret;
            }
        
          };
        form.Show(this);
    }

    //non-modal subwindow, but if the parent is closed by the user when the child is open, then the child is forced closed along with the parent and after the parent.
    private void onNoteEditComplete_Add(Object sender) throws Exception {
        notesChanged = true;
        fillGrid();
        if (MightNeedSetRead)
        {
            //'new' box is checked
            checkNew.Checked = false;
            StatusChanged = true;
            MightNeedSetRead = false;
        }
         
    }

    //so that the automation won't happen again
    private void checkNew_Click(Object sender, EventArgs e) throws Exception {
        if (checkNew.Checked && checkDone.Checked)
        {
            checkDone.Checked = false;
        }
         
        StatusChanged = true;
        MightNeedSetRead = false;
    }

    //don't override user's intent
    private void checkDone_Click(Object sender, EventArgs e) throws Exception {
        if (checkNew.Checked && checkDone.Checked)
        {
            checkNew.Checked = false;
        }
         
        MightNeedSetRead = false;
    }

    //don't override user's intent
    private void fillObject() throws Exception {
        if (TaskCur.ObjectType == TaskObjectType.None)
        {
            listObjectType.SelectedIndex = 0;
            panelObject.Visible = false;
        }
        else if (TaskCur.ObjectType == TaskObjectType.Patient)
        {
            listObjectType.SelectedIndex = 1;
            panelObject.Visible = true;
            labelObjectDesc.Text = Lan.g(this,"Patient Name");
            if (TaskCur.KeyNum > 0)
            {
                textObjectDesc.Text = Patients.getPat(TaskCur.KeyNum).getNameLF();
            }
            else
            {
                textObjectDesc.Text = "";
            } 
        }
        else if (TaskCur.ObjectType == TaskObjectType.Appointment)
        {
            listObjectType.SelectedIndex = 2;
            panelObject.Visible = true;
            labelObjectDesc.Text = Lan.g(this,"Appointment Desc");
            if (TaskCur.KeyNum > 0)
            {
                Appointment AptCur = Appointments.getOneApt(TaskCur.KeyNum);
                if (AptCur == null)
                {
                    textObjectDesc.Text = Lan.g(this,"(appointment deleted)");
                }
                else
                {
                    textObjectDesc.Text = Patients.getPat(AptCur.PatNum).getNameLF() + "  " + AptCur.AptDateTime.ToString() + "  " + AptCur.ProcDescript + "  " + AptCur.Note;
                } 
            }
            else
            {
                textObjectDesc.Text = "";
            } 
        }
           
    }

    private void butNow_Click(Object sender, EventArgs e) throws Exception {
        textDateTimeEntry.Text = MiscData.getNowDateTime().ToString();
    }

    private void butNowFinished_Click(Object sender, EventArgs e) throws Exception {
        textDateTimeFinished.Text = MiscData.getNowDateTime().ToString();
    }

    private void butBlue_Click(Object sender, EventArgs e) throws Exception {
        textDescriptColor(butBlue.Name);
    }

    private void butRed_Click(Object sender, EventArgs e) throws Exception {
        textDescriptColor(butRed.Name);
    }

    /**
    * Handles changing a triage task from red or blue depending on which color button was pushed.
    */
    private void textDescriptColor(String color) throws Exception {
        //Handles the blue color
        if (textDescript.Text.Contains("@@"))
        {
            textDescript.Text = textDescript.Text.Replace("@@", "@");
        }
        else if (StringSupport.equals(color, butBlue.Name))
        {
            textDescript.Text += " @@";
        }
          
        //Handles the red color
        if (textDescript.Text.Contains("CUSTOMER") || textDescript.Text.Contains("DOWN") || textDescript.Text.Contains("URGENT") || textDescript.Text.Contains("CONFERENCE") || textDescript.Text.Contains("!!"))
        {
            textDescript.Text = textDescript.Text.Replace("!!", "!");
            textDescript.Text = textDescript.Text.Replace("CUSTOMER", "customer");
            textDescript.Text = textDescript.Text.Replace("DOWN", "down");
            textDescript.Text = textDescript.Text.Replace("URGENT", "urgent");
            textDescript.Text = textDescript.Text.Replace("CONFERENCE", "conference");
        }
        else if (StringSupport.equals(color, butRed.Name))
        {
            textDescript.Text += " !!";
        }
          
    }

    private void listObjectType_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (TaskCur.KeyNum > 0)
        {
            if (!MsgBox.show(this,true,"The linked object will no longer be attached.  Continue?"))
            {
                fillObject();
                return ;
            }
             
        }
         
        TaskCur.KeyNum = 0;
        TaskCur.ObjectType = (TaskObjectType)listObjectType.SelectedIndex;
        fillObject();
    }

    private void butChange_Click(Object sender, System.EventArgs e) throws Exception {
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.SelectionModeOnly = true;
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (TaskCur.ObjectType == TaskObjectType.Patient)
        {
            TaskCur.KeyNum = FormPS.SelectedPatNum;
        }
         
        if (TaskCur.ObjectType == TaskObjectType.Appointment)
        {
            FormApptsOther FormA = new FormApptsOther(FormPS.SelectedPatNum);
            FormA.SelectOnly = true;
            FormA.ShowDialog();
            if (FormA.DialogResult == DialogResult.Cancel)
            {
                return ;
            }
             
            TaskCur.KeyNum = FormA.AptNumsSelected[0];
        }
         
        fillObject();
    }

    private void butGoto_Click(Object sender, System.EventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        if (!saveCur())
        {
            return ;
        }
         
        GotoType = TaskCur.ObjectType;
        GotoKeyNum = TaskCur.KeyNum;
        DialogResult = DialogResult.OK;
        Close();
    }

    private void butChangeUser_Click(Object sender, EventArgs e) throws Exception {
        FormUserPick FormP = new FormUserPick();
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.OK)
        {
            TaskCur.UserNum = FormP.SelectedUserNum;
            textUser.Text = Userods.getName(TaskCur.UserNum);
        }
         
    }

    private void textDescript_TextChanged(Object sender, EventArgs e) throws Exception {
        if (MightNeedSetRead)
        {
            //'new' box is checked
            checkNew.Checked = false;
            StatusChanged = true;
            MightNeedSetRead = false;
        }
         
    }

    //so that the automation won't happen again
    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        String taskText = TaskCur.DateTimeEntry.ToShortDateString() + " " + TaskCur.DateTimeEntry.ToShortTimeString() + (textObjectDesc.Visible ? " - " + textObjectDesc.Text : "") + " - " + textUser.Text + " - " + textDescript.Text;
        for (int i = 0;i < NoteList.Count;i++)
        {
            taskText += "\r\n--------------------------------------------------\r\n";
            taskText += "==" + Userods.GetName(NoteList[i].UserNum) + " - " + NoteList[i].DateTimeNote.ToShortDateString() + " " + NoteList[i].DateTimeNote.ToShortTimeString() + " - " + NoteList[i].Note;
        }
        System.Windows.Forms.Clipboard.SetText(taskText);
    }

    private boolean saveCur() throws Exception {
        if (!StringSupport.equals(textDateTask.errorProvider1.GetError(textDateTask), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (!StringSupport.equals(textDateTimeEntry.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateTimeEntry.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Please fix Date/Time Entry.");
                return false;
            }
        
        }
         
        if (!StringSupport.equals(textDateTimeFinished.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateTimeFinished.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"Please fix Date/Time Finished.");
                return false;
            }
        
        }
         
        if (TaskCur.TaskListNum == -1)
        {
            MsgBox.show(this,"Since no task list is selected, the Send To button must be used.");
            return false;
        }
         
        if (StringSupport.equals(textDescript.Text, ""))
        {
            MsgBox.show(this,"Please enter a description.");
            return false;
        }
         
        if (checkDone.Checked)
        {
            TaskCur.TaskStatus = TaskStatusEnum.Done;
            //global even if new status is tracked by user
            TaskUnreads.deleteForTask(TaskCur.TaskNum);
        }
        else
        {
            //clear out taskunreads. We have too many tasks to read the done ones.
            //because it can't be both new and done.
            if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
            {
                if (TaskCur.TaskStatus == TaskStatusEnum.Done)
                {
                    TaskCur.TaskStatus = TaskStatusEnum.Viewed;
                }
                 
                //This is done explicitly instead of automatically like it was the old way
                if (!StartedInOthersInbox)
                {
                    if (checkNew.Checked)
                    {
                        TaskUnreads.setUnread(Security.getCurUser().UserNum,TaskCur.TaskNum);
                    }
                    else
                    {
                        TaskUnreads.setRead(Security.getCurUser().UserNum,TaskCur.TaskNum);
                    } 
                }
                 
            }
            else
            {
                //tracked globally, the old way
                if (checkNew.Checked)
                {
                    TaskCur.TaskStatus = TaskStatusEnum.New;
                }
                else
                {
                    TaskCur.TaskStatus = TaskStatusEnum.Viewed;
                } 
            } 
        } 
        //UserNum no longer allowed to change automatically
        //if(resetUser && TaskCur.Descript!=textDescript.Text){
        //	TaskCur.UserNum=Security.CurUser.UserNum;
        //}
        TaskCur.DateTimeEntry = PIn.DateT(textDateTimeEntry.Text);
        if (TaskCur.TaskStatus == TaskStatusEnum.Done && StringSupport.equals(textDateTimeFinished.Text, ""))
        {
            TaskCur.DateTimeFinished = DateTime.Now;
        }
        else
        {
            TaskCur.DateTimeFinished = PIn.DateT(textDateTimeFinished.Text);
        } 
        TaskCur.Descript = textDescript.Text;
        TaskCur.DateTask = PIn.Date(textDateTask.Text);
        TaskCur.DateType = (TaskDateType)comboDateType.SelectedIndex;
        if (!checkFromNum.Checked)
        {
            //user unchecked the box. Never allowed to check if initially unchecked
            TaskCur.FromNum = 0;
        }
         
        try
        {
            //ObjectType already handled
            //Cur.KeyNum already handled
            if (IsNew)
            {
                Tasks.update(TaskCur,TaskOld);
            }
            else
            {
                if (!TaskCur.equals(TaskOld))
                {
                    //If user clicks OK without making any changes, then skip.
                    Tasks.update(TaskCur,TaskOld);
                }
                 
            } 
        }
        catch (Exception ex)
        {
            //if task has already been altered, then this is where it will fail.
            MessageBox.Show(ex.Message);
            return false;
        }

        return true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        if (!IsNew)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
            {
                return ;
            }
             
        }
         
        Tasks.delete(TaskCur.TaskNum);
        //always do it this way to clean up all three tables
        DataValid.setInvalidTask(TaskCur.TaskNum,false);
        //no popup
        DialogResult = DialogResult.OK;
        Close();
    }

    private void butReply_Click(Object sender, EventArgs e) throws Exception {
        //This can't happen if IsNew
        //This also can't happen if the task is mine with no replies.
        //Button not visible unless a ReplyToUserNum has been calculated successfully.
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        long inbox = Userods.getInbox(ReplyToUserNum);
        if (inbox == 0)
        {
            MsgBox.show(this,"No inbox has been set up for this user yet.");
            return ;
        }
         
        if (!notesChanged && StringSupport.equals(textDescript.Text, TaskCur.Descript))
        {
            //nothing changed
            FormTaskNoteEdit form = new FormTaskNoteEdit();
            form.TaskNoteCur = new TaskNote();
            form.TaskNoteCur.TaskNum = TaskCur.TaskNum;
            form.TaskNoteCur.DateTimeNote = DateTime.Now;
            //Will be slightly adjusted at server.
            form.TaskNoteCur.UserNum = Security.getCurUser().UserNum;
            form.TaskNoteCur.setIsNew(true);
            form.EditComplete = new DelegateEditComplete() 
              { 
                public System.Void invoke(System.Object sender) throws Exception {
                    onNoteEditComplete_Reply(sender);
                }

                public List<DelegateEditComplete> getInvocationList() throws Exception {
                    List<DelegateEditComplete> ret = new ArrayList<DelegateEditComplete>();
                    ret.add(this);
                    return ret;
                }
            
              };
            form.Show(this);
            return ;
        }
         
        //non-modal subwindow, but if the parent is closed by the user when the child is open, then the child is forced closed along with the parent and after the parent.
        TaskCur.TaskListNum = inbox;
        if (!saveCur())
        {
            return ;
        }
         
        DataValid.setInvalidTask(TaskCur.TaskNum,true);
        //popup
        DialogResult = DialogResult.OK;
        Close();
    }

    private void onNoteEditComplete_Reply(Object sender) throws Exception {
        if (MightNeedSetRead)
        {
            //'new' box is checked
            checkNew.Checked = false;
            StatusChanged = true;
            MightNeedSetRead = false;
        }
         
        //so that the automation won't happen again
        TaskCur.TaskListNum = Userods.getInbox(ReplyToUserNum);
        if (!saveCur())
        {
            return ;
        }
         
        DataValid.setInvalidTask(TaskCur.TaskNum,true);
        //popup
        DialogResult = DialogResult.OK;
        Close();
    }

    /**
    * Send to another user.
    */
    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        //This button is always present.
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        if (listObjectType.SelectedIndex == ((Enum)TaskObjectType.Patient).ordinal())
        {
            FormTaskListSelect FormT = new FormTaskListSelect(TaskObjectType.Patient);
            FormT.ShowDialog();
            if (FormT.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            TaskCur.TaskListNum = FormT.SelectedTaskListNum;
            TaskListCur = TaskLists.getOne(TaskCur.TaskListNum);
            textTaskList.Text = TaskListCur.Descript;
            if (!saveCur())
            {
                return ;
            }
             
            DataValid.setInvalidTask(TaskCur.TaskNum,true);
        }
        else
        {
            //popup
            //to an in-box
            FormTaskSendUser FormT = new FormTaskSendUser();
            FormT.ShowDialog();
            if (FormT.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            TaskCur.TaskListNum = FormT.TaskListNum;
            TaskListCur = TaskLists.getOne(TaskCur.TaskListNum);
            textTaskList.Text = TaskListCur.Descript;
            if (!saveCur())
            {
                return ;
            }
             
            DataValid.setInvalidTask(TaskCur.TaskNum,true);
        } 
        //popup
        DialogResult = DialogResult.OK;
        Close();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        if (!saveCur())
        {
            return ;
        }
         
        //If user clicked OK without changing anything, then this will have no effect.
        if (!notesChanged && TaskCur.equals(TaskOld) && !StatusChanged)
        {
            //if there were no changes, then don't bother with the signal
            DialogResult = DialogResult.OK;
            Close();
            return ;
        }
         
        if (IsNew)
        {
            DataValid.setInvalidTask(TaskCur.TaskNum,true);
        }
        else //popup
        if (notesChanged || !StringSupport.equals(textDescript.Text, TaskCur.Descript))
        {
            //notes or descript changed
            DataValid.setInvalidTask(TaskCur.TaskNum,true);
        }
        else
        {
            //popup
            DataValid.setInvalidTask(TaskCur.TaskNum,false);
        }  
        //no popup
        DialogResult = DialogResult.OK;
        Close();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (OwnedForms.Length > 0)
        {
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            return ;
        }
         
        DialogResult = DialogResult.Cancel;
        Close();
    }

    private void formTaskEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.None && OwnedForms.Length > 0)
        {
            //This can only happen if the user closes the window using the X in the upper right.
            MsgBox.show(this,"One or more task note edit windows are open and must be closed.");
            e.Cancel = true;
            return ;
        }
         
        if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
        }
        else
        {
            //No more automation here
            if (Security.getCurUser() != null)
            {
                //Because tasks are modal, user may log off and this form may close with no user.
                TaskUnreads.setRead(Security.getCurUser().UserNum,TaskCur.TaskNum);
            }
             
        } 
        //no matter why it was closed
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            Tasks.delete(TaskCur.TaskNum);
        }
         
    }

}


