//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormScheduleDayEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneEmpDefaults.PhoneEmpDefaultComparer.SortBy;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PhoneGraphs;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.SecurityLogs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPhoneGraphDateEdit;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPhoneGraphDateEdit  extends Form 
{

    public DateTime DateEdit = new DateTime();
    private List<PhoneGraph> ListPhoneGraphsForDate = new List<PhoneGraph>();
    private List<PhoneEmpDefault> ListPhoneEmpDefaults = new List<PhoneEmpDefault>();
    private List<Schedule> ListSchedulesForDate = new List<Schedule>();
    public FormPhoneGraphDateEdit(DateTime dateEdit) throws Exception {
        initializeComponent();
        Lan.F(this);
        DateEdit = dateEdit;
    }

    private void formPhoneGraphDateEdit_Load(Object sender, EventArgs e) throws Exception {
        gridGraph.setTitle(DateEdit.ToShortDateString());
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //get PhoneGraph entries for this date
        ListPhoneGraphsForDate = PhoneGraphs.getAllForDate(DateEdit);
        //get current employee defaults
        ListPhoneEmpDefaults = PhoneEmpDefaults.refresh();
        ListPhoneEmpDefaults.Sort(new OpenDentBusiness.PhoneEmpDefaults.PhoneEmpDefaultComparer(SortBy.name));
        //get schedules
        ListSchedulesForDate = Schedules.getDayList(DateEdit);
        long selectedEmployeeNum = -1;
        if (gridGraph.getRows().Count >= 1 && gridGraph.getSelectedIndex() >= 0 && gridGraph.getRows().get___idx(gridGraph.getSelectedIndex()).getTag() != null && gridGraph.getRows().get___idx(gridGraph.getSelectedIndex()).getTag() instanceof PhoneEmpDefault)
        {
            selectedEmployeeNum = ((PhoneEmpDefault)gridGraph.getRows().get___idx(gridGraph.getSelectedIndex()).getTag()).EmployeeNum;
        }
         
        gridGraph.beginUpdate();
        gridGraph.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePhoneGraphDate","Employee"),80);
        gridGraph.getColumns().add(col);
        //column 0 (name - not clickable)
        col = new ODGridColumn(Lan.g("TablePhoneGraphDate","Schedule"),130);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        //column 1 (schedule - clickable)
        col = new ODGridColumn(Lan.g("TablePhoneGraphDate","Graph Default"),90);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        //column 2 (default - not clickable)
        col = new ODGridColumn(Lan.g("TablePhoneGraphDate","Set Graph Status"),110);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        //column 3 (set graph status - clickable)
        col = new ODGridColumn(Lan.g("TablePhoneGraphDate","Is Overridden?"),80);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        //column 4 (is value an overridde of default? - not clickable)
        gridGraph.getRows().Clear();
        int selectedRow = -1;
        for (int iPED = 0;iPED < ListPhoneEmpDefaults.Count;iPED++)
        {
            //loop through all employee defaults and create 1 row per employee
            PhoneEmpDefault phoneEmpDefault = ListPhoneEmpDefaults[iPED];
            Employee employee = Employees.getEmp(phoneEmpDefault.EmployeeNum);
            if (employee == null || employee.IsHidden)
            {
                continue;
            }
             
            //only deal with current employees
            List<Schedule> scheduleForEmployee = Schedules.getForEmployee(ListSchedulesForDate,phoneEmpDefault.EmployeeNum);
            boolean isGraphed = phoneEmpDefault.IsGraphed;
            //set default
            boolean hasOverride = false;
            for (int iPG = 0;iPG < ListPhoneGraphsForDate.Count;iPG++)
            {
                //we have a default, now loop through all known exceptions and find a match
                PhoneGraph phoneGraph = ListPhoneGraphsForDate[iPG];
                if (phoneEmpDefault.EmployeeNum == ListPhoneGraphsForDate[iPG].EmployeeNum)
                {
                    //found a match so no op necessary for this employee
                    isGraphed = phoneGraph.IsGraphed;
                    hasOverride = true;
                    break;
                }
                 
            }
            OpenDental.UI.ODGridRow row;
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(phoneEmpDefault.EmpName);
            //column 0 (name - not clickable)
            row.getCells().Add(Schedules.GetCommaDelimStringForScheds(scheduleForEmployee).Replace(", ", "\r\n"));
            //column 1 (shift times - not clickable)
            row.getCells().add(phoneEmpDefault.IsGraphed ? "X" : "");
            //column 2 (default - not clickable)
            row.getCells().add(isGraphed ? "X" : "");
            //column 3 (set graph status - clickable)
            row.getCells().add(hasOverride && isGraphed != phoneEmpDefault.IsGraphed ? "X" : "");
            //column 4 (is overridden to IsGraphed? - not clickable)
            row.setTag(phoneEmpDefault);
            //store the employee for click events
            int rowIndex = gridGraph.getRows().add(row);
            if (selectedEmployeeNum == phoneEmpDefault.EmployeeNum)
            {
                selectedRow = rowIndex;
            }
             
        }
        gridGraph.endUpdate();
        if (selectedRow >= 0)
        {
            gridGraph.setSelected(selectedRow,true);
        }
         
    }

    private void gridGraph_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //only allow clicking on the 'Desired Graph Status' column
        if (e.getCol() != 3 || gridGraph.getRows().get___idx(e.getRow()).getTag() == null || !(gridGraph.getRows().get___idx(e.getRow()).getTag() instanceof PhoneEmpDefault))
        {
            return ;
        }
         
        PhoneEmpDefault phoneEmpDefault = (PhoneEmpDefault)gridGraph.getRows().get___idx(e.getRow()).getTag();
        boolean uiGraphStatus = StringSupport.equals(gridGraph.getRows().get___idx(e.getRow()).getCells()[e.getCol()].Text.ToLower(), "x");
        boolean dbGraphStatus = PhoneEmpDefaults.getGraphedStatusForEmployeeDate(phoneEmpDefault.EmployeeNum,DateEdit);
        if (uiGraphStatus != dbGraphStatus)
        {
            MessageBox.Show(Lan.g(this,"Graph status has changed unexpectedly for employee: ") + phoneEmpDefault.EmpName + Lan.g(this,". Exit and reopen this form, and try again."));
            return ;
        }
         
        //flip the bit in the db and reload the grid
        PhoneGraph pg = new PhoneGraph();
        pg.EmployeeNum = phoneEmpDefault.EmployeeNum;
        pg.DateEntry = DateEdit;
        pg.IsGraphed = !uiGraphStatus;
        //flip the bit
        PhoneGraphs.insertOrUpdate(pg);
        //update the db
        fillGrid();
    }

    private void butEditSchedule_Click(Object sender, EventArgs e) throws Exception {
        //allow user to edit this day's schedule
        FormScheduleDayEdit FormS = new FormScheduleDayEdit(DateEdit);
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //schedules may have changed to make a log entry
        SecurityLogs.MakeLogEntry(Permissions.Schedules, 0, "In 'FormPhoneGraphDateEdit', user edited daily schedule for " + DateEdit.ToShortDateString());
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPhoneGraphDateEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.gridGraph = new OpenDental.UI.ODGrid();
        this.label11 = new System.Windows.Forms.Label();
        this.butEditSchedule = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(483, 556);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridGraph
        //
        this.gridGraph.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridGraph.setHScrollVisible(false);
        this.gridGraph.Location = new System.Drawing.Point(12, 31);
        this.gridGraph.Name = "gridGraph";
        this.gridGraph.setScrollValue(0);
        this.gridGraph.Size = new System.Drawing.Size(546, 519);
        this.gridGraph.TabIndex = 48;
        this.gridGraph.setTitle("");
        this.gridGraph.setTranslationName("TablePhoneGraphDate");
        this.gridGraph.CellClick = __MultiODGridClickEventHandler.combine(this.gridGraph.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridGraph_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(12, 9);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(546, 19);
        this.label11.TabIndex = 49;
        this.label11.Text = "Click \'Set Graph Status\' column for a given employee to create an override for th" + "is date.";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butEditSchedule
        //
        this.butEditSchedule.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditSchedule.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEditSchedule.setAutosize(true);
        this.butEditSchedule.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditSchedule.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditSchedule.setCornerRadius(4F);
        this.butEditSchedule.Location = new System.Drawing.Point(12, 556);
        this.butEditSchedule.Name = "butEditSchedule";
        this.butEditSchedule.Size = new System.Drawing.Size(92, 24);
        this.butEditSchedule.TabIndex = 50;
        this.butEditSchedule.Text = "&Edit Schedule";
        this.butEditSchedule.Click += new System.EventHandler(this.butEditSchedule_Click);
        //
        // FormPhoneGraphDateEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(570, 592);
        this.Controls.Add(this.butEditSchedule);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.gridGraph);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPhoneGraphDateEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Phone Graph Edits";
        this.Load += new System.EventHandler(this.FormPhoneGraphDateEdit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridGraph;
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEditSchedule;
}


