//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormScheduleDayEdit;
import OpenDental.FormScheduleEdit;
import OpenDental.GraphScheduleDay;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Employees;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SchedStatus;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.ScheduleType;

/**
* 
*/
public class FormScheduleDayEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAddTime;
    private OpenDental.UI.Button butCloseOffice;
    //private ArrayList ALdefaults;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    //private Schedule[] SchedListDay;
    private DateTime SchedCurDate = new DateTime();
    //private ScheduleType SchedType;
    private OpenDental.UI.ODGrid gridMain;
    private Label labelDate = new Label();
    private GroupBox groupBox3 = new GroupBox();
    private Label label1 = new Label();
    private ListBox listProv = new ListBox();
    private OpenDental.UI.Button butOK;
    private Label label2 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butNote;
    private OpenDental.UI.Button butHoliday;
    private OpenDental.UI.Button butProvNote;
    private ListBox listEmp = new ListBox();
    private ComboBox comboProv = new ComboBox();
    private TabControl tabControl1 = new TabControl();
    private TabPage tabPage1 = new TabPage();
    private TabPage tabPage2 = new TabPage();
    private GraphScheduleDay graphScheduleDay;
    //private int ProvNum;
    private List<Schedule> SchedList = new List<Schedule>();
    /**
    * 
    */
    public FormScheduleDayEdit(DateTime schedCurDate) throws Exception {
        initializeComponent();
        SchedCurDate = schedCurDate;
        //SchedType=schedType;
        //ProvNum=provNum;
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormScheduleDayEdit.class);
        this.labelDate = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.listEmp = new System.Windows.Forms.ListBox();
        this.butProvNote = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.listProv = new System.Windows.Forms.ListBox();
        this.butAddTime = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butHoliday = new OpenDental.UI.Button();
        this.butNote = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.tabControl1 = new System.Windows.Forms.TabControl();
        this.tabPage1 = new System.Windows.Forms.TabPage();
        this.tabPage2 = new System.Windows.Forms.TabPage();
        this.graphScheduleDay = new OpenDental.GraphScheduleDay();
        this.butOK = new OpenDental.UI.Button();
        this.butCloseOffice = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox3.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.tabControl1.SuspendLayout();
        this.tabPage1.SuspendLayout();
        this.tabPage2.SuspendLayout();
        this.SuspendLayout();
        //
        // labelDate
        //
        this.labelDate.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDate.Location = new System.Drawing.Point(10, 12);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(263, 23);
        this.labelDate.TabIndex = 9;
        this.labelDate.Text = "labelDate";
        //
        // groupBox3
        //
        this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox3.Controls.Add(this.listEmp);
        this.groupBox3.Controls.Add(this.butProvNote);
        this.groupBox3.Controls.Add(this.label1);
        this.groupBox3.Controls.Add(this.listProv);
        this.groupBox3.Controls.Add(this.butAddTime);
        this.groupBox3.Location = new System.Drawing.Point(819, 45);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(179, 463);
        this.groupBox3.TabIndex = 12;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Add Time Block";
        //
        // listEmp
        //
        this.listEmp.FormattingEnabled = true;
        this.listEmp.Location = new System.Drawing.Point(93, 49);
        this.listEmp.Name = "listEmp";
        this.listEmp.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listEmp.Size = new System.Drawing.Size(80, 329);
        this.listEmp.TabIndex = 6;
        //
        // butProvNote
        //
        this.butProvNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvNote.setAutosize(true);
        this.butProvNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvNote.setCornerRadius(4F);
        this.butProvNote.Location = new System.Drawing.Point(47, 424);
        this.butProvNote.Name = "butProvNote";
        this.butProvNote.Size = new System.Drawing.Size(80, 24);
        this.butProvNote.TabIndex = 15;
        this.butProvNote.Text = "Note";
        this.butProvNote.Click += new System.EventHandler(this.butProvNote_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 17);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(170, 30);
        this.label1.TabIndex = 7;
        this.label1.Text = "Select One or More Providers or Employees";
        //
        // listProv
        //
        this.listProv.FormattingEnabled = true;
        this.listProv.Location = new System.Drawing.Point(7, 49);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(80, 329);
        this.listProv.TabIndex = 6;
        //
        // butAddTime
        //
        this.butAddTime.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddTime.setAutosize(true);
        this.butAddTime.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddTime.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddTime.setCornerRadius(4F);
        this.butAddTime.Image = Resources.getAdd();
        this.butAddTime.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddTime.Location = new System.Drawing.Point(47, 391);
        this.butAddTime.Name = "butAddTime";
        this.butAddTime.Size = new System.Drawing.Size(80, 24);
        this.butAddTime.TabIndex = 4;
        this.butAddTime.Text = "&Add";
        this.butAddTime.Click += new System.EventHandler(this.butAddTime_Click);
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(12, 642);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(518, 44);
        this.label2.TabIndex = 14;
        this.label2.Text = resources.GetString("label2.Text");
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.butHoliday);
        this.groupBox1.Controls.Add(this.butNote);
        this.groupBox1.Location = new System.Drawing.Point(854, 551);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(110, 89);
        this.groupBox1.TabIndex = 15;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Practice";
        //
        // butHoliday
        //
        this.butHoliday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHoliday.setAutosize(true);
        this.butHoliday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHoliday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHoliday.setCornerRadius(4F);
        this.butHoliday.Location = new System.Drawing.Point(14, 53);
        this.butHoliday.Name = "butHoliday";
        this.butHoliday.Size = new System.Drawing.Size(80, 24);
        this.butHoliday.TabIndex = 15;
        this.butHoliday.Text = "Holiday";
        this.butHoliday.Click += new System.EventHandler(this.butHoliday_Click);
        //
        // butNote
        //
        this.butNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNote.setAutosize(true);
        this.butNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNote.setCornerRadius(4F);
        this.butNote.Location = new System.Drawing.Point(14, 20);
        this.butNote.Name = "butNote";
        this.butNote.Size = new System.Drawing.Size(80, 24);
        this.butNote.TabIndex = 14;
        this.butNote.Text = "Note";
        this.butNote.Click += new System.EventHandler(this.butNote_Click);
        //
        // gridMain
        //
        this.gridMain.Dock = System.Windows.Forms.DockStyle.Fill;
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(3, 3);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(787, 575);
        this.gridMain.TabIndex = 8;
        this.gridMain.setTitle("Edit Day");
        this.gridMain.setTranslationName(null);
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
        // comboProv
        //
        this.comboProv.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(12, 685);
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(121, 21);
        this.comboProv.TabIndex = 16;
        //
        // tabControl1
        //
        this.tabControl1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.tabControl1.Controls.Add(this.tabPage1);
        this.tabControl1.Controls.Add(this.tabPage2);
        this.tabControl1.Location = new System.Drawing.Point(12, 32);
        this.tabControl1.Name = "tabControl1";
        this.tabControl1.SelectedIndex = 0;
        this.tabControl1.Size = new System.Drawing.Size(801, 607);
        this.tabControl1.TabIndex = 17;
        //
        // tabPage1
        //
        this.tabPage1.Controls.Add(this.gridMain);
        this.tabPage1.Location = new System.Drawing.Point(4, 22);
        this.tabPage1.Name = "tabPage1";
        this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
        this.tabPage1.Size = new System.Drawing.Size(793, 581);
        this.tabPage1.TabIndex = 0;
        this.tabPage1.Text = "List";
        this.tabPage1.UseVisualStyleBackColor = true;
        //
        // tabPage2
        //
        this.tabPage2.Controls.Add(this.graphScheduleDay);
        this.tabPage2.Location = new System.Drawing.Point(4, 22);
        this.tabPage2.Name = "tabPage2";
        this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
        this.tabPage2.Size = new System.Drawing.Size(793, 581);
        this.tabPage2.TabIndex = 1;
        this.tabPage2.Text = "Graph";
        this.tabPage2.UseVisualStyleBackColor = true;
        //
        // graphScheduleDay
        //
        this.graphScheduleDay.setBarHeightPixels(17);
        this.graphScheduleDay.setBarSpacingPixels(3);
        this.graphScheduleDay.Dock = System.Windows.Forms.DockStyle.Fill;
        this.graphScheduleDay.setEmployeeBarColor(System.Drawing.Color.LightSkyBlue);
        this.graphScheduleDay.setEmployeeTextColor(System.Drawing.Color.Black);
        this.graphScheduleDay.setEndHour(19);
        this.graphScheduleDay.setExteriorPaddingPixels(11);
        this.graphScheduleDay.setGraphBackColor(System.Drawing.Color.White);
        this.graphScheduleDay.setLineWidthPixels(1);
        this.graphScheduleDay.Location = new System.Drawing.Point(3, 3);
        this.graphScheduleDay.Name = "graphScheduleDay";
        this.graphScheduleDay.setPracticeBarColor(System.Drawing.Color.Salmon);
        this.graphScheduleDay.setPracticeTextColor(System.Drawing.Color.Black);
        this.graphScheduleDay.setProviderBarColor(System.Drawing.Color.LightGreen);
        this.graphScheduleDay.setProviderTextColor(System.Drawing.Color.Black);
        this.graphScheduleDay.Size = new System.Drawing.Size(787, 575);
        this.graphScheduleDay.setStartHour(4);
        this.graphScheduleDay.TabIndex = 0;
        this.graphScheduleDay.setTickHeightPixels(5);
        this.graphScheduleDay.setXAxisBackColor(System.Drawing.Color.White);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(819, 680);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 13;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCloseOffice
        //
        this.butCloseOffice.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCloseOffice.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCloseOffice.setAutosize(true);
        this.butCloseOffice.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCloseOffice.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCloseOffice.setCornerRadius(4F);
        this.butCloseOffice.Image = Resources.getdeleteX();
        this.butCloseOffice.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCloseOffice.Location = new System.Drawing.Point(866, 521);
        this.butCloseOffice.Name = "butCloseOffice";
        this.butCloseOffice.Size = new System.Drawing.Size(80, 24);
        this.butCloseOffice.TabIndex = 5;
        this.butCloseOffice.Text = "Delete";
        this.butCloseOffice.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(906, 680);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormScheduleDayEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(1003, 720);
        this.Controls.Add(this.tabControl1);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.labelDate);
        this.Controls.Add(this.butCloseOffice);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormScheduleDayEdit";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Day";
        this.Load += new System.EventHandler(this.FormScheduleDay_Load);
        this.groupBox3.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.tabControl1.ResumeLayout(false);
        this.tabPage1.ResumeLayout(false);
        this.tabPage2.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formScheduleDay_Load(Object sender, System.EventArgs e) throws Exception {
        labelDate.Text = SchedCurDate.ToString("dddd") + " " + SchedCurDate.ToShortDateString();
        SchedList = Schedules.refreshDayEdit(SchedCurDate);
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            //only does this on startup
            //listProv
            listProv.Items.Add(ProviderC.getListShort()[i].Abbr);
        }
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            //listProv.SetSelected(i,true);
            listEmp.Items.Add(Employees.getListShort()[i].FName);
        }
        //listEmp.SetSelected(i,true);
        fillGrid();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == PrefC.getLong(PrefName.ScheduleProvUnassigned))
            {
                comboProv.SelectedIndex = i;
            }
             
        }
    }

    private void fillGrid() throws Exception {
        //do not refresh from db
        SchedList.Sort(CompareSchedule);
        graphScheduleDay.setSchedules(SchedList);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableSchedDay","Provider"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedDay","Employee"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedDay","Start Time"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedDay","Stop Time"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedDay","Ops"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedDay","Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String note = new String();
        String opdesc = new String();
        for (int i = 0;i < SchedList.Count;i++)
        {
            //string opstr;
            //string[] oparray;
            row = new OpenDental.UI.ODGridRow();
            //Prov
            if (SchedList[i].ProvNum != 0)
            {
                row.getCells().Add(Providers.GetAbbr(SchedList[i].ProvNum));
            }
            else
            {
                row.getCells().add("");
            } 
            //Employee
            if (SchedList[i].EmployeeNum == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(Employees.GetEmp(SchedList[i].EmployeeNum).FName);
            } 
            //times
            if (SchedList[i].StartTime == TimeSpan.Zero && SchedList[i].StopTime == TimeSpan.Zero)
            {
                //SchedList[i].SchedType==ScheduleType.Practice){
                row.getCells().add("");
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(SchedList[i].StartTime.ToShortTimeString());
                row.getCells().Add(SchedList[i].StopTime.ToShortTimeString());
            } 
            //ops
            opdesc = "";
            for (int o = 0;o < SchedList[i].Ops.Count;o++)
            {
                Operatory op = Operatories.GetOperatory(SchedList[i].Ops[o]);
                if (op.IsHidden)
                {
                    continue;
                }
                 
                //Skip hidden operatories because it just confuses users.
                if (!StringSupport.equals(opdesc, ""))
                {
                    opdesc += ",";
                }
                 
                opdesc += op.Abbrev;
            }
            row.getCells().add(opdesc);
            //note
            note = "";
            if (SchedList[i].Status == SchedStatus.Holiday)
            {
                note += Lan.g(this,"Holiday: ");
            }
             
            note += SchedList[i].Note;
            row.getCells().add(note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private int compareSchedule(Schedule x, Schedule y) throws Exception {
        if (x == y)
        {
            return 0;
        }
         
        if (x == null && y == null)
        {
            return 0;
        }
         
        if (y == null)
        {
            return 1;
        }
         
        if (x == null)
        {
            return -1;
        }
         
        if (x.SchedType != y.SchedType)
        {
            return x.SchedType.CompareTo(y.SchedType);
        }
         
        if (x.ProvNum != y.ProvNum)
        {
            return Providers.getProv(x.ProvNum).ItemOrder.CompareTo(Providers.getProv(y.ProvNum).ItemOrder);
        }
         
        if (x.EmployeeNum != y.EmployeeNum)
        {
            return Employees.getEmp(x.EmployeeNum).FName.CompareTo(Employees.getEmp(y.EmployeeNum).FName);
        }
         
        return x.StartTime.CompareTo(y.StartTime);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Schedule schedCur = SchedList[e.getRow()];
        //remember the clicked row
        FormScheduleEdit FormS = new FormScheduleEdit();
        FormS.SchedCur = SchedList[e.getRow()];
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
        for (int i = 0;i < SchedList.Count;i++)
        {
            if (SchedList[i] == schedCur)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    //private void butAll_Click(object sender,EventArgs e) {
    //	for(int i=0;i<listProv.Items.Count;i++){
    //		listProv.SetSelected(i,true);
    //	}
    //}
    private void butAddTime_Click(Object sender, System.EventArgs e) throws Exception {
        Schedule SchedCur = new Schedule();
        SchedCur.SchedDate = SchedCurDate;
        SchedCur.Status = SchedStatus.Open;
        SchedCur.StartTime = new TimeSpan(8, 0, 0);
        //8am
        SchedCur.StopTime = new TimeSpan(17, 0, 0);
        //5pm
        //schedtype, provNum, and empnum will be set down below
        FormScheduleEdit FormS = new FormScheduleEdit();
        FormS.SchedCur = SchedCur;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Schedule schedTemp;
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            schedTemp = new Schedule();
            schedTemp = SchedCur.copy();
            schedTemp.SchedType = ScheduleType.Provider;
            schedTemp.ProvNum = ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum;
            SchedList.Add(schedTemp);
        }
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            schedTemp = new Schedule();
            schedTemp = SchedCur.copy();
            schedTemp.SchedType = ScheduleType.Employee;
            schedTemp.EmployeeNum = Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum;
            SchedList.Add(schedTemp);
        }
        fillGrid();
    }

    private void butProvNote_Click(Object sender, EventArgs e) throws Exception {
        Schedule SchedCur = new Schedule();
        SchedCur.SchedDate = SchedCurDate;
        SchedCur.Status = SchedStatus.Open;
        //schedtype, provNum, and empnum will be set down below
        FormScheduleEdit FormS = new FormScheduleEdit();
        FormS.SchedCur = SchedCur;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Schedule schedTemp;
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            schedTemp = new Schedule();
            schedTemp = SchedCur.copy();
            schedTemp.SchedType = ScheduleType.Provider;
            schedTemp.ProvNum = ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum;
            SchedList.Add(schedTemp);
        }
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            schedTemp = new Schedule();
            schedTemp = SchedCur.copy();
            schedTemp.SchedType = ScheduleType.Employee;
            schedTemp.EmployeeNum = Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum;
            SchedList.Add(schedTemp);
        }
        fillGrid();
    }

    private void butNote_Click(Object sender, EventArgs e) throws Exception {
        Schedule SchedCur = new Schedule();
        SchedCur.SchedDate = SchedCurDate;
        SchedCur.Status = SchedStatus.Open;
        SchedCur.SchedType = ScheduleType.Practice;
        FormScheduleEdit FormS = new FormScheduleEdit();
        FormS.SchedCur = SchedCur;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SchedList.Add(SchedCur);
        fillGrid();
    }

    private void butHoliday_Click(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < SchedList.Count;i++)
        {
            if (SchedList[i].SchedType == ScheduleType.Practice && SchedList[i].Status == SchedStatus.Holiday)
            {
                MsgBox.show(this,"Day is already a Holiday.");
                return ;
            }
             
        }
        Schedule SchedCur = new Schedule();
        SchedCur.SchedDate = SchedCurDate;
        SchedCur.Status = SchedStatus.Holiday;
        SchedCur.SchedType = ScheduleType.Practice;
        FormScheduleEdit FormS = new FormScheduleEdit();
        FormS.SchedCur = SchedCur;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SchedList.Add(SchedCur);
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            SchedList.Clear();
            fillGrid();
            return ;
        }
         
        for (int i = gridMain.getSelectedIndices().Length - 1;i >= 0;i--)
        {
            //loop backwards:
            SchedList.RemoveAt(gridMain.getSelectedIndices()[i]);
        }
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            Schedules.setForDay(SchedList,SchedCurDate);
        }
        catch (Exception ex)
        {
            MsgBox.Show(this, ex.Message);
            return ;
        }

        if (comboProv.SelectedIndex != -1 && Prefs.UpdateLong(PrefName.ScheduleProvUnassigned, ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


