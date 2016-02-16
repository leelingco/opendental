//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:39 PM
//

package OpenDental;

import CodeBase.MiscUtils;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AppointmentL;
import OpenDental.AutomationL;
import OpenDental.ContrAppt;
import OpenDental.FormApptEdit;
import OpenDental.FormApptsOther;
import OpenDental.FormRecallsPat;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OtherResult;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.PlannedAppts;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.UI.ApptDrawing;

/**
* 
*/
public class FormApptsOther  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.CheckBox checkDone = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    /**
    * The result of the window.  In other words, which button was clicked to exit the window.
    */
    public OtherResult oResult = OtherResult.Cancel;
    private System.Windows.Forms.TextBox textApptModNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butGoTo;
    private OpenDental.UI.Button butPin;
    private OpenDental.UI.Button butNew;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * True if user double clicked on a blank area of appt module to get to this point.
    */
    public boolean InitialClick = new boolean();
    private System.Windows.Forms.ListView listFamily = new System.Windows.Forms.ListView();
    private System.Windows.Forms.ColumnHeader columnHeader1 = new System.Windows.Forms.ColumnHeader();
    private System.Windows.Forms.ColumnHeader columnHeader2 = new System.Windows.Forms.ColumnHeader();
    private System.Windows.Forms.ColumnHeader columnHeader4 = new System.Windows.Forms.ColumnHeader();
    private System.Windows.Forms.ColumnHeader columnHeader3 = new System.Windows.Forms.ColumnHeader();
    private System.Windows.Forms.ColumnHeader columnHeader5 = new System.Windows.Forms.ColumnHeader();
    private Appointment[] ApptList = new Appointment[]();
    private List<Recall> RecallList = new List<Recall>();
    private Patient PatCur;
    private OpenDental.UI.Button butOK;
    private Family FamCur;
    /**
    * Almost always false.  Only set to true from TaskList to allow selecting one appointment for a patient.
    */
    public boolean SelectOnly = new boolean();
    private OpenDental.UI.Button butRecall;
    //<summary>This will contain a selected appointment upon closing of the form in some situations.  Used when picking an appointment for task lists.  Also used if the GoTo or Create new buttons are clicked.</summary>
    //public int AptSelected;
    /**
    * After closing, this may contain aptNums of appointments that should be placed on the pinboard. Used when picking an appointment for task lists.  Also used if the GoTo, Create new, or Recall buttons are pushed.
    */
    public List<long> AptNumsSelected = new List<long>();
    /**
    * When this form closes, this will be the patNum of the last patient viewed.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    private TextBox textFinUrg = new TextBox();
    private Label label3 = new Label();
    private OpenDental.UI.Button butNote;
    private OpenDental.UI.Button butRecallFamily;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * If oResult=PinboardAndSearch, then when closing this form, this will contain the date to jump to when beginning the search.  If oResult=GoTo, then this will also contain the date.  Can't use DateTime type because C# complains about marshal by reference.
    */
    public String DateJumpToString = new String();
    /**
    * 
    */
    public FormApptsOther(long patNum) throws Exception {
        //Patient pat,Family fam){
        initializeComponent();
        FamCur = Patients.getFamily(patNum);
        PatCur = FamCur.getPatient(patNum);
        Lan.f(this);
        for (int i = 0;i < listFamily.Columns.Count;i++)
        {
            listFamily.Columns[i].Text = Lan.g(this, listFamily.Columns[i].Text);
        }
        AptNumsSelected = new List<long>();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptsOther.class);
        this.checkDone = new System.Windows.Forms.CheckBox();
        this.butCancel = new OpenDental.UI.Button();
        this.textApptModNote = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butGoTo = new OpenDental.UI.Button();
        this.butPin = new OpenDental.UI.Button();
        this.butNew = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.listFamily = new System.Windows.Forms.ListView();
        this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.columnHeader4 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.columnHeader5 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.butOK = new OpenDental.UI.Button();
        this.butRecall = new OpenDental.UI.Button();
        this.textFinUrg = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.butNote = new OpenDental.UI.Button();
        this.butRecallFamily = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // checkDone
        //
        this.checkDone.AutoCheck = false;
        this.checkDone.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDone.ImeMode = System.Windows.Forms.ImeMode.NoControl;
        this.checkDone.Location = new System.Drawing.Point(12, 145);
        this.checkDone.Name = "checkDone";
        this.checkDone.Size = new System.Drawing.Size(210, 16);
        this.checkDone.TabIndex = 1;
        this.checkDone.TabStop = false;
        this.checkDone.Text = "Planned Appt Done";
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.ImeMode = System.Windows.Forms.ImeMode.NoControl;
        this.butCancel.Location = new System.Drawing.Point(837, 620);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textApptModNote
        //
        this.textApptModNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textApptModNote.BackColor = System.Drawing.Color.White;
        this.textApptModNote.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textApptModNote.ForeColor = System.Drawing.Color.Red;
        this.textApptModNote.Location = new System.Drawing.Point(707, 36);
        this.textApptModNote.Multiline = true;
        this.textApptModNote.Name = "textApptModNote";
        this.textApptModNote.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textApptModNote.Size = new System.Drawing.Size(202, 36);
        this.textApptModNote.TabIndex = 44;
        this.textApptModNote.Leave += new System.EventHandler(this.textApptModNote_Leave);
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
        this.label1.Location = new System.Drawing.Point(542, 40);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(163, 21);
        this.label1.TabIndex = 45;
        this.label1.Text = "Appointment Module Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butGoTo
        //
        this.butGoTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGoTo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butGoTo.setAutosize(true);
        this.butGoTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGoTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGoTo.setCornerRadius(4F);
        this.butGoTo.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGoTo.Location = new System.Drawing.Point(50, 620);
        this.butGoTo.Name = "butGoTo";
        this.butGoTo.Size = new System.Drawing.Size(106, 24);
        this.butGoTo.TabIndex = 46;
        this.butGoTo.Text = "&Go To Appt";
        this.butGoTo.Click += new System.EventHandler(this.butGoTo_Click);
        //
        // butPin
        //
        this.butPin.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPin.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPin.setAutosize(true);
        this.butPin.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPin.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPin.setCornerRadius(4F);
        this.butPin.Image = Resources.getbutPin();
        this.butPin.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPin.Location = new System.Drawing.Point(165, 620);
        this.butPin.Name = "butPin";
        this.butPin.Size = new System.Drawing.Size(134, 24);
        this.butPin.TabIndex = 47;
        this.butPin.Text = "Copy To &Pinboard";
        this.butPin.Click += new System.EventHandler(this.butPin_Click);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Image = Resources.getAdd();
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(576, 620);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(125, 24);
        this.butNew.TabIndex = 48;
        this.butNew.Text = "Create &New Appt";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(12, 13);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(168, 17);
        this.label2.TabIndex = 57;
        this.label2.Text = "Recall for Family";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listFamily
        //
        this.listFamily.Columns.AddRange(new System.Windows.Forms.ColumnHeader[]{ this.columnHeader1, this.columnHeader2, this.columnHeader4, this.columnHeader3, this.columnHeader5 });
        this.listFamily.FullRowSelect = true;
        this.listFamily.GridLines = true;
        this.listFamily.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
        this.listFamily.Location = new System.Drawing.Point(12, 36);
        this.listFamily.Name = "listFamily";
        this.listFamily.Size = new System.Drawing.Size(384, 97);
        this.listFamily.TabIndex = 58;
        this.listFamily.UseCompatibleStateImageBehavior = false;
        this.listFamily.View = System.Windows.Forms.View.Details;
        this.listFamily.Click += new System.EventHandler(this.listFamily_Click);
        this.listFamily.DoubleClick += new System.EventHandler(this.listFamily_DoubleClick);
        //
        // columnHeader1
        //
        this.columnHeader1.Text = "Family Member";
        this.columnHeader1.Width = 120;
        //
        // columnHeader2
        //
        this.columnHeader2.Text = "Age";
        this.columnHeader2.Width = 40;
        //
        // columnHeader4
        //
        this.columnHeader4.Text = "Gender";
        this.columnHeader4.Width = 50;
        //
        // columnHeader3
        //
        this.columnHeader3.Text = "Due Date";
        this.columnHeader3.Width = 74;
        //
        // columnHeader5
        //
        this.columnHeader5.Text = "Scheduled";
        this.columnHeader5.Width = 74;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.ImeMode = System.Windows.Forms.ImeMode.NoControl;
        this.butOK.Location = new System.Drawing.Point(751, 620);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 59;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butRecall
        //
        this.butRecall.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRecall.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butRecall.setAutosize(true);
        this.butRecall.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRecall.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRecall.setCornerRadius(4F);
        this.butRecall.Image = Resources.getbutRecall();
        this.butRecall.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRecall.Location = new System.Drawing.Point(308, 620);
        this.butRecall.Name = "butRecall";
        this.butRecall.Size = new System.Drawing.Size(125, 24);
        this.butRecall.TabIndex = 60;
        this.butRecall.Text = "Schedule Recall";
        this.butRecall.Click += new System.EventHandler(this.butRecall_Click);
        //
        // textFinUrg
        //
        this.textFinUrg.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textFinUrg.BackColor = System.Drawing.Color.White;
        this.textFinUrg.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textFinUrg.ForeColor = System.Drawing.Color.Red;
        this.textFinUrg.Location = new System.Drawing.Point(707, 78);
        this.textFinUrg.Multiline = true;
        this.textFinUrg.Name = "textFinUrg";
        this.textFinUrg.ReadOnly = true;
        this.textFinUrg.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textFinUrg.Size = new System.Drawing.Size(202, 81);
        this.textFinUrg.TabIndex = 63;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label3.ImeMode = System.Windows.Forms.ImeMode.NoControl;
        this.label3.Location = new System.Drawing.Point(542, 81);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(163, 21);
        this.label3.TabIndex = 64;
        this.label3.Text = "Family Urgent Financial Notes";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butNote
        //
        this.butNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNote.setAutosize(true);
        this.butNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNote.setCornerRadius(4F);
        this.butNote.Image = ((System.Drawing.Image)(resources.GetObject("butNote.Image")));
        this.butNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNote.Location = new System.Drawing.Point(442, 620);
        this.butNote.Name = "butNote";
        this.butNote.Size = new System.Drawing.Size(125, 24);
        this.butNote.TabIndex = 65;
        this.butNote.Text = "NO&TE for Patient";
        this.butNote.Click += new System.EventHandler(this.butNote_Click);
        //
        // butRecallFamily
        //
        this.butRecallFamily.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRecallFamily.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butRecallFamily.setAutosize(true);
        this.butRecallFamily.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRecallFamily.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRecallFamily.setCornerRadius(4F);
        this.butRecallFamily.Image = Resources.getbutRecall();
        this.butRecallFamily.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRecallFamily.Location = new System.Drawing.Point(308, 588);
        this.butRecallFamily.Name = "butRecallFamily";
        this.butRecallFamily.Size = new System.Drawing.Size(125, 24);
        this.butRecallFamily.TabIndex = 66;
        this.butRecallFamily.Text = "Entire Family";
        this.butRecallFamily.Click += new System.EventHandler(this.butRecallFamily_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 168);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(897, 398);
        this.gridMain.TabIndex = 67;
        this.gridMain.setTitle("Appointments for Patient");
        this.gridMain.setTranslationName("FormDisplayFields");
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
        // FormApptsOther
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(924, 658);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butRecallFamily);
        this.Controls.Add(this.butNote);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textFinUrg);
        this.Controls.Add(this.butRecall);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.listFamily);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.butPin);
        this.Controls.Add(this.butGoTo);
        this.Controls.Add(this.textApptModNote);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkDone);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptsOther";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Other Appointments";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormApptsOther_Closing);
        this.Load += new System.EventHandler(this.FormApptsOther_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    /**
    * 
    */
    public OtherResult getOResult() throws Exception {
        return oResult;
    }

    private void formApptsOther_Load(Object sender, System.EventArgs e) throws Exception {
        Text = Lan.g(this,"Appointments for") + " " + PatCur.getNameLF();
        textApptModNote.Text = PatCur.ApptModNote;
        if (SelectOnly)
        {
            butGoTo.Visible = false;
            butPin.Visible = false;
            butNew.Visible = false;
            label2.Visible = false;
            listFamily.Visible = false;
        }
        else
        {
            //much more typical
            butOK.Visible = false;
        } 
        fillFamily();
        fillGrid();
        gridMain.scrollToEnd();
        checkStatus();
    }

    private void checkStatus() throws Exception {
        if (PatCur.PatStatus == PatientStatus.Inactive || PatCur.PatStatus == PatientStatus.Archived || PatCur.PatStatus == PatientStatus.Prospective)
        {
            MsgBox.show(this,"Warning. Patient is not active.");
        }
         
        if (PatCur.PatStatus == PatientStatus.Deceased)
        {
            MsgBox.show(this,"Warning. Patient is deceased.");
        }
         
    }

    private void fillGrid() throws Exception {
        ApptList = Appointments.getForPat(PatCur.PatNum);
        List<PlannedAppt> plannedList = PlannedAppts.refresh(PatCur.PatNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormApptsOther","Appt Status"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormApptsOther","Prov"),50);
        gridMain.getColumns().add(col);
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            col = new ODGridColumn(Lan.g("FormApptsOther","Clinic"),80);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g("FormApptsOther","Date"),70);
        //If the order changes, reflect the change for dateIndex below.
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormApptsOther","Time"),70);
        //Must immediately follow Date column.
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormApptsOther","Min"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormApptsOther","Procedures"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormApptsOther","Notes"),320);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        int dateIndex = 3;
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            dateIndex = 2;
        }
         
        for (int i = 0;i < ApptList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ApptList[i].AptStatus.ToString());
            row.getCells().Add(Providers.GetAbbr(ApptList[i].ProvNum));
            if (!PrefC.getBool(PrefName.EasyNoClinics))
            {
                row.getCells().Add(Clinics.GetDesc(ApptList[i].ClinicNum));
            }
             
            row.getCells().add("");
            //Date
            row.getCells().add("");
            //Time
            if (ApptList[i].AptDateTime.Year > 1880)
            {
                //only regular still scheduled appts
                if (ApptList[i].AptStatus != ApptStatus.Planned && ApptList[i].AptStatus != ApptStatus.PtNote && ApptList[i].AptStatus != ApptStatus.PtNoteCompleted && ApptList[i].AptStatus != ApptStatus.UnschedList && ApptList[i].AptStatus != ApptStatus.Broken)
                {
                    row.getCells()[dateIndex].Text = ApptList[i].AptDateTime.ToString("d");
                    row.getCells()[dateIndex + 1].Text = ApptList[i].AptDateTime.ToString("t");
                    if (ApptList[i].AptDateTime < DateTime.Today)
                    {
                        //Past
                        row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][11].ItemColor);
                        row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][10].ItemColor);
                    }
                    else if (ApptList[i].AptDateTime.Date == DateTime.Today.Date)
                    {
                        //Today
                        row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][9].ItemColor);
                        row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][8].ItemColor);
                        row.getCells()[0].Text = Lan.g(this,"Today");
                    }
                    else if (ApptList[i].AptDateTime > DateTime.Today)
                    {
                        //Future
                        row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][13].ItemColor);
                        row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][12].ItemColor);
                    }
                       
                }
                else if (ApptList[i].AptStatus == ApptStatus.Planned)
                {
                    //show line for planned appt
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][17].ItemColor);
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][16].ItemColor);
                    String txt = Lan.g("enumApptStatus","Planned") + " ";
                    for (int p = 0;p < plannedList.Count;p++)
                    {
                        if (plannedList[p].AptNum == ApptList[i].AptNum)
                        {
                            txt += "#" + plannedList[p].ItemOrder.ToString();
                        }
                         
                    }
                    row.getCells()[0].Text = txt;
                }
                else if (ApptList[i].AptStatus == ApptStatus.PtNote)
                {
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][19].ItemColor);
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][18].ItemColor);
                    row.getCells()[0].Text = Lan.g("enumApptStatus","PtNote");
                }
                else if (ApptList[i].AptStatus == ApptStatus.PtNoteCompleted)
                {
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][21].ItemColor);
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][20].ItemColor);
                    row.getCells()[0].Text = Lan.g("enumApptStatus","PtNoteCompleted");
                }
                else if (ApptList[i].AptStatus == ApptStatus.Broken)
                {
                    row.getCells()[0].Text = Lan.g("enumApptStatus","Broken");
                    row.getCells()[dateIndex].Text = ApptList[i].AptDateTime.ToString("d");
                    row.getCells()[dateIndex + 1].Text = ApptList[i].AptDateTime.ToString("t");
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][15].ItemColor);
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][14].ItemColor);
                }
                else if (ApptList[i].AptStatus == ApptStatus.UnschedList)
                {
                    row.getCells()[0].Text = Lan.g("enumApptStatus","UnschedList");
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][15].ItemColor);
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][14].ItemColor);
                }
                      
            }
             
            row.getCells().Add((ApptList[i].Pattern.Length * 5).ToString());
            row.getCells().Add(ApptList[i].ProcDescript);
            row.getCells().Add(ApptList[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillFamily() throws Exception {
        SelectedPatNum = PatCur.PatNum;
        //just in case user has selected a different family member
        RecallList = Recalls.getList(MiscUtils.arrayToList(FamCur.ListPats));
        //Appointment[] aptsOnePat;
        listFamily.Items.Clear();
        ListViewItem item = new ListViewItem();
        DateTime dateDue = new DateTime();
        DateTime dateSched = new DateTime();
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            item = new ListViewItem(FamCur.getNameInFamFLI(i));
            if (FamCur.ListPats[i].PatNum == PatCur.PatNum)
            {
                item.BackColor = Color.Silver;
            }
             
            item.SubItems.Add(FamCur.ListPats[i].Age.ToString());
            item.SubItems.Add(FamCur.ListPats[i].Gender.ToString());
            dateDue = DateTime.MinValue;
            dateSched = DateTime.MinValue;
            boolean isdisabled = false;
            for (int j = 0;j < RecallList.Count;j++)
            {
                if (RecallList[j].PatNum == FamCur.ListPats[i].PatNum && (RecallList[j].RecallTypeNum == RecallTypes.getPerioType() || RecallList[j].RecallTypeNum == RecallTypes.getProphyType()))
                {
                    dateDue = RecallList[j].DateDue;
                    dateSched = RecallList[j].DateScheduled;
                    isdisabled = RecallList[j].IsDisabled;
                }
                 
            }
            if (isdisabled)
            {
                item.SubItems.Add(Lan.g(this,"disabled"));
            }
            else if (dateDue.Year < 1880)
            {
                item.SubItems.Add("");
            }
            else
            {
                item.SubItems.Add(dateDue.ToShortDateString());
            }  
            if (dateDue <= DateTime.Today)
            {
                item.ForeColor = Color.Red;
            }
             
            if (dateSched.Year < 1880)
            {
                item.SubItems.Add("");
            }
            else
            {
                item.SubItems.Add(dateSched.ToShortDateString());
            } 
            listFamily.Items.Add(item);
        }
        checkDone.Checked = PatCur.PlannedIsDone;
        textFinUrg.Text = FamCur.ListPats[0].FamFinUrgNote;
    }

    private void listFamily_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listFamily.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        FormRecallsPat FormR = new FormRecallsPat();
        FormR.PatNum = PatCur.PatNum;
        FormR.ShowDialog();
    }

    /*
    			int originalPatNum=PatCur.PatNum;
    			Recall recallCur=null;
    			for(int i=0;i<RecallList.Count;i++){
    				if(RecallList[i].PatNum==FamCur.List[listFamily.SelectedIndices[0]].PatNum){
    					recallCur=RecallList[i];
    				}
    			}
    			if(recallCur==null){
    				recallCur=new Recall();
    				recallCur.PatNum=FamCur.List[listFamily.SelectedIndices[0]].PatNum;
    				recallCur.RecallInterval=new Interval(0,0,6,0);
    			}
    			FormRecallListEdit FormRLE=new FormRecallListEdit(recallCur);
    			FormRLE.ShowDialog();
    			if(FormRLE.PinClicked){
    				oResult=OtherResult.CopyToPinBoard;
    				AptNumsSelected.Add(FormRLE.AptSelected);
    				DialogResult=DialogResult.OK;
    			}
    			else{
    				FamCur=Patients.GetFamily(originalPatNum);
    				PatCur=FamCur.GetPatient(originalPatNum);
    				Filltb();
    			}*/
    private void butRecall_Click(Object sender, System.EventArgs e) throws Exception {
        makeRecallAppointment();
    }

    public void makeRecallAppointment() throws Exception {
        List<Procedure> procList = Procedures.refresh(PatCur.PatNum);
        //List<Recall> recallList=Recalls.GetList(PatCur.PatNum);//get the recall for this pt
        //if(recallList.Count==0){
        //	MsgBox.Show(this,"This patient does not have any recall due.");
        //	return;
        //}
        //Recall recallCur=recallList[0];
        List<InsSub> subList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        Appointment apt = null;
        try
        {
            apt = AppointmentL.CreateRecallApt(PatCur, procList, planList, -1, subList);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        AptNumsSelected.Add(apt.AptNum);
        if (this.InitialClick)
        {
            Appointment oldApt = apt.clone();
            DateTime d = new DateTime();
            if (ApptDrawing.IsWeeklyView)
            {
                d = ContrAppt.WeekStartDate.AddDays(ContrAppt.SheetClickedonDay);
            }
            else
            {
                d = AppointmentL.DateSelected;
            } 
            int minutes = (int)(ContrAppt.SheetClickedonMin / ApptDrawing.MinPerIncr) * ApptDrawing.MinPerIncr;
            apt.AptDateTime = new DateTime(d.Year, d.Month, d.Day, ContrAppt.SheetClickedonHour, minutes, 0);
            if (PatCur.AskToArriveEarly > 0)
            {
                apt.DateTimeAskedToArrive = apt.AptDateTime.AddMinutes(-PatCur.AskToArriveEarly);
                MessageBox.Show(Lan.g(this,"Ask patient to arrive") + " " + PatCur.AskToArriveEarly + " " + Lan.g(this,"minutes early at") + " " + apt.DateTimeAskedToArrive.ToShortTimeString() + ".");
            }
             
            apt.AptStatus = ApptStatus.Scheduled;
            apt.Op = ContrAppt.SheetClickedonOp;
            Operatory curOp = Operatories.getOperatory(apt.Op);
            if (curOp.ProvDentist != 0)
            {
                apt.ProvNum = curOp.ProvDentist;
            }
             
            apt.ProvHyg = curOp.ProvHygienist;
            apt.IsHygiene = curOp.IsHygiene;
            if (curOp.ClinicNum == 0)
            {
                apt.ClinicNum = PatCur.ClinicNum;
            }
            else
            {
                apt.ClinicNum = curOp.ClinicNum;
            } 
            Appointments.update(apt,oldApt);
            oResult = OtherResult.CreateNew;
            SecurityLogs.MakeLogEntry(Permissions.AppointmentCreate, apt.PatNum, apt.AptDateTime.ToString(), apt.AptNum);
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //not initialClick
        oResult = OtherResult.PinboardAndSearch;
        Recall recall = Recalls.getRecallProphyOrPerio(PatCur.PatNum);
        //shouldn't return null.
        if (recall.DateDue < DateTime.Today)
        {
            DateJumpToString = DateTime.Today.ToShortDateString();
        }
        else
        {
            //they are overdue
            DateJumpToString = recall.DateDue.ToShortDateString();
        } 
        //no securitylog entry needed here.  That will happen when it's dragged off pinboard.
        DialogResult = DialogResult.OK;
    }

    private void butRecallFamily_Click(Object sender, EventArgs e) throws Exception {
        makeRecallFamily();
    }

    public void makeRecallFamily() throws Exception {
        List<Procedure> procList = new List<Procedure>();
        List<Recall> recallList = new List<Recall>();
        //=Recalls.GetList(FamCur.ListPats
        List<InsPlan> planList = new List<InsPlan>();
        List<InsSub> subList = new List<InsSub>();
        Appointment apt = null;
        Recall recall;
        int alreadySched = 0;
        int noRecalls = 0;
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            procList = Procedures.Refresh(FamCur.ListPats[i].PatNum);
            recallList = Recalls.GetList(FamCur.ListPats[i].PatNum);
            //get the recall for this pt
            if (recallList.Count == 0)
            {
                noRecalls++;
                continue;
            }
             
            //MsgBox.Show(this,"This patient does not have any recall due.");
            if (recallList[0].IsDisabled)
            {
                noRecalls++;
                continue;
            }
             
            if (recallList[0].DateScheduled.Year > 1880)
            {
                alreadySched++;
                continue;
            }
             
            subList = InsSubs.refreshForFam(FamCur);
            planList = InsPlans.RefreshForSubList(subList);
            try
            {
                apt = AppointmentL.CreateRecallApt(FamCur.ListPats[i], procList, planList, -1, subList);
            }
            catch (Exception __dummyCatchVar0)
            {
                continue;
            }

            //MessageBox.Show(ex.Message);
            AptNumsSelected.Add(apt.AptNum);
            oResult = OtherResult.PinboardAndSearch;
            recall = Recalls.GetRecallProphyOrPerio(FamCur.ListPats[i].PatNum);
            //should not return null
            if (recall.DateDue < DateTime.Today)
            {
                DateJumpToString = DateTime.Today.ToShortDateString();
            }
            else
            {
                //they are overdue
                DateJumpToString = recall.DateDue.ToShortDateString();
            } 
            SecurityLogs.MakeLogEntry(Permissions.AppointmentCreate, apt.PatNum, apt.AptDateTime.ToString(), apt.AptNum);
        }
        String userMsg = "";
        if (noRecalls > 0)
        {
            userMsg += Lan.g(this,"Family members skipped because recall disabled: ") + noRecalls.ToString();
        }
         
        if (alreadySched > 0)
        {
            if (!StringSupport.equals(userMsg, ""))
            {
                userMsg += "\r\n";
            }
             
            userMsg += Lan.g(this,"Family members skipped because already scheduled: ") + alreadySched.ToString();
        }
         
        if (AptNumsSelected.Count == 0)
        {
            if (!StringSupport.equals(userMsg, ""))
            {
                userMsg += "\r\n";
            }
             
            userMsg += Lan.g(this,"There are no recall appointments to schedule.");
            MessageBox.Show(userMsg);
            return ;
        }
         
        if (!StringSupport.equals(userMsg, ""))
        {
            MessageBox.Show(userMsg);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butNote_Click(Object sender, EventArgs e) throws Exception {
        Appointment AptCur = new Appointment();
        AptCur.PatNum = PatCur.PatNum;
        if (PatCur.DateFirstVisit.Year < 1880 && !Procedures.areAnyComplete(PatCur.PatNum))
        {
            //this only runs if firstVisit blank
            AptCur.IsNewPatient = true;
        }
         
        AptCur.Pattern = "/X/";
        if (PatCur.PriProv == 0)
        {
            AptCur.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        }
        else
        {
            AptCur.ProvNum = PatCur.PriProv;
        } 
        AptCur.ProvHyg = PatCur.SecProv;
        AptCur.AptStatus = ApptStatus.PtNote;
        AptCur.ClinicNum = PatCur.ClinicNum;
        AptCur.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
        if (InitialClick)
        {
            //initially double clicked on appt module
            DateTime d = new DateTime();
            if (ApptDrawing.IsWeeklyView)
            {
                d = ContrAppt.WeekStartDate.AddDays(ContrAppt.SheetClickedonDay);
            }
            else
            {
                d = AppointmentL.DateSelected;
            } 
            int minutes = (int)(ContrAppt.SheetClickedonMin / ApptDrawing.MinPerIncr) * ApptDrawing.MinPerIncr;
            AptCur.AptDateTime = new DateTime(d.Year, d.Month, d.Day, ContrAppt.SheetClickedonHour, minutes, 0);
            AptCur.Op = ContrAppt.SheetClickedonOp;
        }
        else
        {
        } 
        try
        {
            //new appt will be placed on pinboard instead of specific time
            Appointments.insert(AptCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        FormApptEdit FormApptEdit2 = new FormApptEdit(AptCur.AptNum);
        FormApptEdit2.IsNew = true;
        FormApptEdit2.ShowDialog();
        if (FormApptEdit2.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        AptNumsSelected.Add(AptCur.AptNum);
        if (InitialClick)
        {
            oResult = OtherResult.CreateNew;
        }
        else
        {
            oResult = OtherResult.NewToPinBoard;
        } 
        DialogResult = DialogResult.OK;
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        makeAppointment();
    }

    public void makeAppointment() throws Exception {
        Appointment AptCur = new Appointment();
        AptCur.PatNum = PatCur.PatNum;
        if (PatCur.DateFirstVisit.Year < 1880 && !Procedures.areAnyComplete(PatCur.PatNum))
        {
            //this only runs if firstVisit blank
            AptCur.IsNewPatient = true;
        }
         
        AptCur.Pattern = "/X/";
        if (PatCur.PriProv == 0)
        {
            AptCur.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        }
        else
        {
            AptCur.ProvNum = PatCur.PriProv;
        } 
        AptCur.ProvHyg = PatCur.SecProv;
        AptCur.AptDateTime = DateTime.MinValue;
        //(was .Now) This is what triggers automatic deletion from db when clear pinboard is clicked.
        AptCur.ClinicNum = PatCur.ClinicNum;
        AptCur.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
        if (InitialClick)
        {
            //initially double clicked on appt module
            DateTime d = new DateTime();
            if (ApptDrawing.IsWeeklyView)
            {
                d = ContrAppt.WeekStartDate.AddDays(ContrAppt.SheetClickedonDay);
            }
            else
            {
                d = AppointmentL.DateSelected;
            } 
            int minutes = (int)(ContrAppt.SheetClickedonMin / ApptDrawing.MinPerIncr) * ApptDrawing.MinPerIncr;
            AptCur.AptDateTime = new DateTime(d.Year, d.Month, d.Day, ContrAppt.SheetClickedonHour, minutes, 0);
            if (PatCur.AskToArriveEarly > 0)
            {
                AptCur.DateTimeAskedToArrive = AptCur.AptDateTime.AddMinutes(-PatCur.AskToArriveEarly);
                MessageBox.Show(Lan.g(this,"Ask patient to arrive") + " " + PatCur.AskToArriveEarly + " " + Lan.g(this,"minutes early at") + " " + AptCur.DateTimeAskedToArrive.ToShortTimeString() + ".");
            }
             
            AptCur.Op = ContrAppt.SheetClickedonOp;
            Operatory curOp = Operatories.getOperatory(AptCur.Op);
            //if(curOp.ProvDentist!=0) {
            //  AptCur.ProvNum=curOp.ProvDentist;
            //}
            //AptCur.ProvHyg=curOp.ProvHygienist;
            List<Schedule> schedListPeriod = Schedules.refreshDayEdit(AptCur.AptDateTime);
            long assignedDent = Schedules.GetAssignedProvNumForSpot(schedListPeriod, curOp, false, AptCur.AptDateTime);
            long assignedHyg = Schedules.GetAssignedProvNumForSpot(schedListPeriod, curOp, true, AptCur.AptDateTime);
            //the section below regarding providers is overly wordy because it's copied from ContrAppt.pinBoard_MouseUp to make maint easier.
            if (assignedDent != 0)
            {
                AptCur.ProvNum = assignedDent;
            }
             
            if (assignedHyg != 0)
            {
                //the hygienist will only be changed if the spot has a hygienist.
                AptCur.ProvHyg = assignedHyg;
            }
             
            if (curOp.IsHygiene)
            {
                AptCur.IsHygiene = true;
            }
            else
            {
                //op not marked as hygiene op
                if (assignedDent == 0)
                {
                    //no dentist assigned
                    if (assignedHyg != 0)
                    {
                        //hyg is assigned (we don't really have to test for this)
                        AptCur.IsHygiene = true;
                    }
                     
                }
                else
                {
                    //dentist is assigned
                    if (assignedHyg == 0)
                    {
                        //hyg is not assigned
                        AptCur.IsHygiene = false;
                    }
                     
                    //if both dentist and hyg are assigned, it's tricky
                    //only explicitly set it if user has a dentist assigned to the op
                    if (curOp.ProvDentist != 0)
                    {
                        AptCur.IsHygiene = false;
                    }
                     
                } 
            } 
            if (curOp.ClinicNum != 0)
            {
                AptCur.ClinicNum = curOp.ClinicNum;
            }
             
            AptCur.AptStatus = ApptStatus.Scheduled;
        }
        else
        {
            //new appt will be placed on pinboard instead of specific time
            AptCur.AptStatus = ApptStatus.UnschedList;
        } 
        try
        {
            //This is so that if it's on the pinboard when use shuts down OD, no db inconsistency.
            Appointments.insert(AptCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        SecurityLogs.MakeLogEntry(Permissions.AppointmentCreate, AptCur.PatNum, AptCur.AptDateTime.ToString() + ", " + AptCur.ProcDescript, AptCur.AptNum);
        FormApptEdit FormApptEdit2 = new FormApptEdit(AptCur.AptNum);
        FormApptEdit2.IsNew = true;
        FormApptEdit2.ShowDialog();
        if (FormApptEdit2.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (InitialClick)
        {
            //Change PatStatus to Prospective or from Prospective.
            Operatory opCur = Operatories.getOperatory(AptCur.Op);
            if (opCur.SetProspective && PatCur.PatStatus != PatientStatus.Prospective)
            {
                //Don't need to prompt if patient is already prospective.
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will be set to Prospective."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Prospective;
                    Patients.update(PatCur,patOld);
                }
                 
            }
            else if (!opCur.SetProspective && PatCur.PatStatus == PatientStatus.Prospective)
            {
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will change from Prospective to Patient."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Patient;
                    Patients.update(PatCur,patOld);
                }
                 
            }
              
        }
         
        AptNumsSelected.Add(AptCur.AptNum);
        if (InitialClick)
        {
            oResult = OtherResult.CreateNew;
        }
        else
        {
            oResult = OtherResult.NewToPinBoard;
        } 
        if (AptCur.IsNewPatient)
        {
            AutomationL.trigger(AutomationTrigger.CreateApptNewPat,null,AptCur.PatNum);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butPin_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select appointment first."));
            return ;
        }
         
        if (!OKtoSendToPinboard(ApptList[gridMain.getSelectedIndex()]))
        {
            return ;
        }
         
        AptNumsSelected.Add(ApptList[gridMain.getSelectedIndex()].AptNum);
        oResult = OtherResult.CopyToPinBoard;
        DialogResult = DialogResult.OK;
    }

    /**
    * Tests the appointment to see if it is acceptable to send it to the pinboard.  Also asks user appropriate questions to verify that's what they want to do.  Returns false if it will not be going to pinboard after all.
    */
    private boolean oKtoSendToPinboard(Appointment AptCur) throws Exception {
        if (AptCur.AptStatus == ApptStatus.Planned)
        {
            //if is a Planned appointment
            boolean PlannedIsSched = false;
            for (int i = 0;i < ApptList.Length;i++)
            {
                if (ApptList[i].NextAptNum == AptCur.AptNum)
                {
                    //if the planned appointment is already sched
                    PlannedIsSched = true;
                }
                 
            }
            if (PlannedIsSched)
            {
                if (MessageBox.Show(Lan.g(this,"The Planned appointment is already scheduled.  Do you wish to continue?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
                {
                    return false;
                }
                 
            }
             
        }
        else
        {
            //if appointment is not Planned
            switch(AptCur.AptStatus)
            {
                case Complete: 
                    MessageBox.Show(Lan.g(this,"Not allowed to move a completed appointment from here."));
                    return false;
                case ASAP: 
                case Scheduled: 
                    if (MessageBox.Show(Lan.g(this,"Do you really want to move a previously scheduled appointment?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
                    {
                        return false;
                    }
                     
                    break;
                case Broken: 
                case None: 
                case UnschedList: 
                    break;
            
            }
        } 
        return true;
    }

    //status gets changed after dragging off pinboard.
    //status gets changed after dragging off pinboard.
    //if it's a planned appointment, the planned appointment will end up on the pinboard.  The copy will be made after dragging it off the pinboard.
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int currentSelection = e.getRow();
        int currentScroll = gridMain.getScrollValue();
        FormApptEdit FormAE = new FormApptEdit(ApptList[e.getRow()].AptNum);
        FormAE.PinIsVisible = true;
        FormAE.ShowDialog();
        if (FormAE.DialogResult != DialogResult.OK)
            return ;
         
        if (FormAE.PinClicked)
        {
            if (!OKtoSendToPinboard(ApptList[e.getRow()]))
            {
                return ;
            }
             
            AptNumsSelected.Add(ApptList[e.getRow()].AptNum);
            oResult = OtherResult.CopyToPinBoard;
            DialogResult = DialogResult.OK;
        }
        else
        {
            fillFamily();
            fillGrid();
            gridMain.setSelected(currentSelection,true);
            gridMain.setScrollValue(currentScroll);
        } 
    }

    private void listFamily_Click(Object sender, EventArgs e) throws Exception {
        //Changes the patient to whoever was clicked in the list
        long oldPatNum = PatCur.PatNum;
        long newPatNum = FamCur.ListPats[listFamily.SelectedIndices[0]].PatNum;
        if (newPatNum == oldPatNum)
        {
            return ;
        }
         
        PatCur = FamCur.getPatient(newPatNum);
        Text = Lan.g(this,"Appointments for") + " " + PatCur.getNameLF();
        textApptModNote.Text = PatCur.ApptModNote;
        fillFamily();
        fillGrid();
        checkStatus();
    }

    private void textApptModNote_Leave(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textApptModNote.Text, PatCur.ApptModNote))
        {
            Patient PatOld = PatCur.copy();
            PatCur.ApptModNote = textApptModNote.Text;
            Patients.update(PatCur,PatOld);
        }
         
    }

    private void butGoTo_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select appointment first."));
            return ;
        }
         
        if (ApptList[gridMain.getSelectedIndex()].AptDateTime.Year < 1880)
        {
            MessageBox.Show(Lan.g(this,"Unable to go to unscheduled appointment."));
            return ;
        }
         
        AptNumsSelected.Add(ApptList[gridMain.getSelectedIndex()].AptNum);
        DateJumpToString = ApptList[gridMain.getSelectedIndex()].AptDateTime.Date.ToShortDateString();
        oResult = OtherResult.GoTo;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //only used when selecting from TaskList. oResult is completely ignored in this case.
        //I didn't bother enabling double click. Maybe later.
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select appointment first."));
            return ;
        }
         
        AptNumsSelected.Add(ApptList[gridMain.getSelectedIndex()].AptNum);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formApptsOther_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        oResult = OtherResult.Cancel;
    }

}


