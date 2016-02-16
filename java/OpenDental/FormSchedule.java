//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:44 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormSchedule;
import OpenDental.FormScheduleDayEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSchedule  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butRefresh;
    private OpenDental.ValidDate textDateTo;
    private Label label2 = new Label();
    private OpenDental.ValidDate textDateFrom;
    private Label label1 = new Label();
    private ListBox listProv = new ListBox();
    private Label labelProv = new Label();
    private CheckBox checkWeekend = new CheckBox();
    private GroupBox groupCopy = new GroupBox();
    private OpenDental.UI.Button butCopyDay;
    private TextBox textClipboard = new TextBox();
    private Label label3 = new Label();
    private OpenDental.UI.Button butRepeat;
    private Label label4 = new Label();
    private TextBox textRepeat = new TextBox();
    private OpenDental.UI.Button butPaste;
    private OpenDental.UI.Button butCopyWeek;
    private OpenDental.UI.Button butPrint;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private DateTime DateCopyStart = new DateTime();
    private OpenDental.UI.Button butDelete;
    private DateTime DateCopyEnd = new DateTime();
    private CheckBox checkPractice = new CheckBox();
    private ListBox listEmp = new ListBox();
    private Label label5 = new Label();
    private CheckBox checkReplace = new CheckBox();
    private GroupBox groupPaste = new GroupBox();
    /**
    * This tracks whether the provList or empList has been click on since the last refresh.  Forces user to refresh before deleting or pasting so that the list exactly matches the grid.
    */
    private boolean ProvsChanged = new boolean();
    private PrintDocument pd = new PrintDocument();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    private int headingPrintH = new int();
    boolean changed = new boolean();
    /**
    * 
    */
    public FormSchedule() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSchedule.class);
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.listProv = new System.Windows.Forms.ListBox();
        this.labelProv = new System.Windows.Forms.Label();
        this.checkWeekend = new System.Windows.Forms.CheckBox();
        this.groupCopy = new System.Windows.Forms.GroupBox();
        this.butCopyWeek = new OpenDental.UI.Button();
        this.butCopyDay = new OpenDental.UI.Button();
        this.textClipboard = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textRepeat = new System.Windows.Forms.TextBox();
        this.checkPractice = new System.Windows.Forms.CheckBox();
        this.listEmp = new System.Windows.Forms.ListBox();
        this.label5 = new System.Windows.Forms.Label();
        this.checkReplace = new System.Windows.Forms.CheckBox();
        this.groupPaste = new System.Windows.Forms.GroupBox();
        this.butRepeat = new OpenDental.UI.Button();
        this.butPaste = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.textDateTo = new OpenDental.ValidDate();
        this.textDateFrom = new OpenDental.ValidDate();
        this.butRefresh = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupCopy.SuspendLayout();
        this.groupPaste.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(101, 28);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(85, 18);
        this.label2.TabIndex = 9;
        this.label2.Text = "To Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 28);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(86, 18);
        this.label1.TabIndex = 7;
        this.label1.Text = "From Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(17, 100);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(82, 290);
        this.listProv.TabIndex = 23;
        this.listProv.Click += new System.EventHandler(this.listProv_Click);
        //
        // labelProv
        //
        this.labelProv.Location = new System.Drawing.Point(14, 80);
        this.labelProv.Name = "labelProv";
        this.labelProv.Size = new System.Drawing.Size(87, 18);
        this.labelProv.TabIndex = 22;
        this.labelProv.Text = "Providers";
        this.labelProv.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkWeekend
        //
        this.checkWeekend.Location = new System.Drawing.Point(28, 392);
        this.checkWeekend.Name = "checkWeekend";
        this.checkWeekend.Size = new System.Drawing.Size(143, 18);
        this.checkWeekend.TabIndex = 24;
        this.checkWeekend.Text = "Show Weekends";
        this.checkWeekend.UseVisualStyleBackColor = true;
        this.checkWeekend.Click += new System.EventHandler(this.checkWeekend_Click);
        //
        // groupCopy
        //
        this.groupCopy.Controls.Add(this.butCopyWeek);
        this.groupCopy.Controls.Add(this.butCopyDay);
        this.groupCopy.Controls.Add(this.textClipboard);
        this.groupCopy.Controls.Add(this.label3);
        this.groupCopy.Location = new System.Drawing.Point(22, 440);
        this.groupCopy.Name = "groupCopy";
        this.groupCopy.Size = new System.Drawing.Size(158, 111);
        this.groupCopy.TabIndex = 25;
        this.groupCopy.TabStop = false;
        this.groupCopy.Text = "Copy";
        //
        // butCopyWeek
        //
        this.butCopyWeek.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopyWeek.setAutosize(true);
        this.butCopyWeek.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopyWeek.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopyWeek.setCornerRadius(4F);
        this.butCopyWeek.Location = new System.Drawing.Point(6, 83);
        this.butCopyWeek.Name = "butCopyWeek";
        this.butCopyWeek.Size = new System.Drawing.Size(75, 24);
        this.butCopyWeek.TabIndex = 28;
        this.butCopyWeek.Text = "Copy Week";
        this.butCopyWeek.Click += new System.EventHandler(this.butCopyWeek_Click);
        //
        // butCopyDay
        //
        this.butCopyDay.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopyDay.setAutosize(true);
        this.butCopyDay.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopyDay.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopyDay.setCornerRadius(4F);
        this.butCopyDay.Location = new System.Drawing.Point(6, 56);
        this.butCopyDay.Name = "butCopyDay";
        this.butCopyDay.Size = new System.Drawing.Size(75, 24);
        this.butCopyDay.TabIndex = 27;
        this.butCopyDay.Text = "Copy Day";
        this.butCopyDay.Click += new System.EventHandler(this.butCopyDay_Click);
        //
        // textClipboard
        //
        this.textClipboard.Location = new System.Drawing.Point(6, 33);
        this.textClipboard.Name = "textClipboard";
        this.textClipboard.ReadOnly = true;
        this.textClipboard.Size = new System.Drawing.Size(146, 20);
        this.textClipboard.TabIndex = 26;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(146, 14);
        this.label3.TabIndex = 8;
        this.label3.Text = "Clipboard Contents";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(70, 65);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(37, 14);
        this.label4.TabIndex = 32;
        this.label4.Text = "#";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textRepeat
        //
        this.textRepeat.Location = new System.Drawing.Point(110, 62);
        this.textRepeat.Name = "textRepeat";
        this.textRepeat.Size = new System.Drawing.Size(39, 20);
        this.textRepeat.TabIndex = 31;
        this.textRepeat.Text = "1";
        //
        // checkPractice
        //
        this.checkPractice.Checked = true;
        this.checkPractice.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkPractice.Location = new System.Drawing.Point(17, 67);
        this.checkPractice.Name = "checkPractice";
        this.checkPractice.Size = new System.Drawing.Size(169, 18);
        this.checkPractice.TabIndex = 28;
        this.checkPractice.Text = "Show Practice Notes";
        this.checkPractice.UseVisualStyleBackColor = true;
        this.checkPractice.Click += new System.EventHandler(this.checkPractice_Click);
        //
        // listEmp
        //
        this.listEmp.Location = new System.Drawing.Point(105, 100);
        this.listEmp.Name = "listEmp";
        this.listEmp.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listEmp.Size = new System.Drawing.Size(82, 290);
        this.listEmp.TabIndex = 30;
        this.listEmp.Click += new System.EventHandler(this.listEmp_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(102, 80);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(87, 18);
        this.label5.TabIndex = 29;
        this.label5.Text = "Employees";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkReplace
        //
        this.checkReplace.Checked = true;
        this.checkReplace.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkReplace.Location = new System.Drawing.Point(6, 14);
        this.checkReplace.Name = "checkReplace";
        this.checkReplace.Size = new System.Drawing.Size(146, 18);
        this.checkReplace.TabIndex = 31;
        this.checkReplace.Text = "Replace Existing";
        this.checkReplace.UseVisualStyleBackColor = true;
        //
        // groupPaste
        //
        this.groupPaste.Controls.Add(this.butRepeat);
        this.groupPaste.Controls.Add(this.label4);
        this.groupPaste.Controls.Add(this.checkReplace);
        this.groupPaste.Controls.Add(this.textRepeat);
        this.groupPaste.Controls.Add(this.butPaste);
        this.groupPaste.Location = new System.Drawing.Point(22, 552);
        this.groupPaste.Name = "groupPaste";
        this.groupPaste.Size = new System.Drawing.Size(158, 87);
        this.groupPaste.TabIndex = 32;
        this.groupPaste.TabStop = false;
        this.groupPaste.Text = "Paste";
        //
        // butRepeat
        //
        this.butRepeat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRepeat.setAutosize(true);
        this.butRepeat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRepeat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRepeat.setCornerRadius(4F);
        this.butRepeat.Location = new System.Drawing.Point(6, 59);
        this.butRepeat.Name = "butRepeat";
        this.butRepeat.Size = new System.Drawing.Size(75, 24);
        this.butRepeat.TabIndex = 30;
        this.butRepeat.Text = "Repeat";
        this.butRepeat.Click += new System.EventHandler(this.butRepeat_Click);
        //
        // butPaste
        //
        this.butPaste.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPaste.setAutosize(true);
        this.butPaste.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPaste.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPaste.setCornerRadius(4F);
        this.butPaste.Location = new System.Drawing.Point(6, 32);
        this.butPaste.Name = "butPaste";
        this.butPaste.Size = new System.Drawing.Size(75, 24);
        this.butPaste.TabIndex = 29;
        this.butPaste.Text = "Paste";
        this.butPaste.Click += new System.EventHandler(this.butPaste_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(28, 410);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(103, 24);
        this.butDelete.TabIndex = 27;
        this.butDelete.Text = "Clear Week";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(28, 662);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(90, 24);
        this.butPrint.TabIndex = 26;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(104, 47);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(82, 20);
        this.textDateTo.TabIndex = 10;
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(17, 47);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(82, 20);
        this.textDateFrom.TabIndex = 8;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(64, 4);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 11;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(207, 8);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(765, 680);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Schedule");
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
        // FormSchedule
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(982, 700);
        this.Controls.Add(this.groupCopy);
        this.Controls.Add(this.groupPaste);
        this.Controls.Add(this.listEmp);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.checkPractice);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.checkWeekend);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.labelProv);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSchedule";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Schedule";
        this.Load += new System.EventHandler(this.FormSchedule_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormSchedule_FormClosing);
        this.groupCopy.ResumeLayout(false);
        this.groupCopy.PerformLayout();
        this.groupPaste.ResumeLayout(false);
        this.groupPaste.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSchedule_Load(Object sender, EventArgs e) throws Exception {
        if (!Security.IsAuthorized(Permissions.Schedules, DateTime.MinValue, true))
        {
            //gridMain.Enabled=false;
            butDelete.Enabled = false;
            groupCopy.Enabled = false;
            groupPaste.Enabled = false;
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                //if this is OD HQ
                checkPractice.Checked = false;
                checkPractice.Enabled = false;
            }
             
        }
         
        DateTime dateFrom = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
        textDateFrom.Text = dateFrom.ToShortDateString();
        textDateTo.Text = dateFrom.AddMonths(12).AddDays(-1).ToShortDateString();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            //DateTime dateFrom=new DateTime(2007,5,6);
            //textDateFrom.Text=dateFrom.ToShortDateString();
            //textDateTo.Text=dateFrom.AddDays(6).ToShortDateString();
            //listProv.Items.Add(Lan.g(this,"(Practice)"));
            //listProv.SetSelected(0,true);
            listProv.Items.Add(ProviderC.getListShort()[i].Abbr);
            listProv.SetSelected(i, true);
        }
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            listEmp.Items.Add(Employees.getListShort()[i].FName);
            listEmp.SetSelected(i, true);
        }
        fillGrid();
    }

    private void fillGrid() throws Exception {
        DateCopyStart = DateTime.MinValue;
        DateCopyEnd = DateTime.MinValue;
        textClipboard.Text = "";
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            return ;
        }
         
        //MsgBox.Show(this,"Please fix errors first.");
        ProvsChanged = false;
        List<long> provNums = new List<long>();
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            provNums.Add(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum);
        }
        List<long> empNums = new List<long>();
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            empNums.Add(Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum);
        }
        DataTable table = Schedules.GetPeriod(PIn.Date(textDateFrom.Text), PIn.Date(textDateTo.Text), provNums, empNums, checkPractice.Checked);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        int colW = new int();
        if (checkWeekend.Checked)
        {
            colW = (gridMain.Width - 20) / 7;
        }
        else
        {
            colW = (gridMain.Width - 20) / 5;
        } 
        ODGridColumn col;
        if (checkWeekend.Checked)
        {
            col = new ODGridColumn(Lan.g("TableSchedule","Sunday"),colW);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g("TableSchedule","Monday"),colW);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedule","Tuesday"),colW);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedule","Wednesday"),colW);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedule","Thursday"),colW);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSchedule","Friday"),colW);
        gridMain.getColumns().add(col);
        if (checkWeekend.Checked)
        {
            col = new ODGridColumn(Lan.g("TableSchedule","Saturday"),colW);
            gridMain.getColumns().add(col);
        }
         
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (checkWeekend.Checked)
            {
                row.getCells().Add(table.Rows[i][0].ToString());
            }
             
            row.getCells().Add(table.Rows[i][1].ToString());
            row.getCells().Add(table.Rows[i][2].ToString());
            row.getCells().Add(table.Rows[i][3].ToString());
            row.getCells().Add(table.Rows[i][4].ToString());
            row.getCells().Add(table.Rows[i][5].ToString());
            if (checkWeekend.Checked)
            {
                row.getCells().Add(table.Rows[i][6].ToString());
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        //Set today red
        if (!checkWeekend.Checked && (DateTime.Today.DayOfWeek == DayOfWeek.Sunday || DateTime.Today.DayOfWeek == DayOfWeek.Saturday))
        {
            return ;
        }
         
        if (DateTime.Today > PIn.Date(textDateTo.Text) || DateTime.Today < PIn.Date(textDateFrom.Text))
        {
            return ;
        }
         
        int colI = (int)DateTime.Today.DayOfWeek;
        if (!checkWeekend.Checked)
        {
            colI--;
        }
         
        gridMain.getRows()[Schedules.GetRowCal(PIn.Date(textDateFrom.Text), DateTime.Today)].Cells[colI].ColorText = Color.Red;
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        fillGrid();
    }

    private void checkWeekend_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkPractice_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!Security.IsAuthorized(Permissions.Schedules, DateTime.MinValue))
        {
            return ;
        }
         
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        int clickedCol = e.getCol();
        if (!checkWeekend.Checked)
        {
            clickedCol++;
        }
         
        //the "clickedCell" is in terms of the entire 7 col layout.
        Point clickedCell = new Point(clickedCol, e.getRow());
        DateTime selectedDate = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), e.getRow(), clickedCol);
        if (selectedDate < PIn.Date(textDateFrom.Text) || selectedDate > PIn.Date(textDateTo.Text))
        {
            return ;
        }
         
        //MessageBox.Show(selectedDate.ToShortDateString());
        FormScheduleDayEdit FormS = new FormScheduleDayEdit(selectedDate);
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
        if (checkWeekend.Checked)
        {
            gridMain.setSelected(clickedCell);
        }
        else
        {
            gridMain.setSelected(new Point(clickedCell.X - 1, clickedCell.Y));
        } 
        changed = true;
    }

    private void listProv_Click(Object sender, EventArgs e) throws Exception {
        ProvsChanged = true;
    }

    private void listEmp_Click(Object sender, EventArgs e) throws Exception {
        ProvsChanged = true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a date first.");
            return ;
        }
         
        if (ProvsChanged)
        {
            MsgBox.show(this,"Provider or Employee selection has been changed.  Please refresh first.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete all displayed entries for the entire selected week?"))
        {
            return ;
        }
         
        int startI = 1;
        if (checkWeekend.Checked)
        {
            startI = 0;
        }
         
        DateTime dateSelectedStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, startI);
        DateTime dateSelectedEnd = new DateTime();
        if (checkWeekend.Checked)
        {
            dateSelectedEnd = dateSelectedStart.AddDays(6);
        }
        else
        {
            dateSelectedEnd = dateSelectedStart.AddDays(4);
        } 
        List<long> provNums = new List<long>();
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            provNums.Add(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum);
        }
        List<long> empNums = new List<long>();
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            empNums.Add(Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum);
        }
        Schedules.Clear(dateSelectedStart, dateSelectedEnd, provNums, empNums, checkPractice.Checked);
        fillGrid();
        changed = true;
    }

    private void butCopyDay_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a date first.");
            return ;
        }
         
        int selectedCol = gridMain.getSelectedCell().X;
        if (!checkWeekend.Checked)
        {
            selectedCol++;
        }
         
        DateCopyStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, selectedCol);
        DateCopyEnd = DateCopyStart;
        textClipboard.Text = DateCopyStart.ToShortDateString();
    }

    private void butCopyWeek_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a date first.");
            return ;
        }
         
        int startI = 1;
        if (checkWeekend.Checked)
        {
            startI = 0;
        }
         
        DateCopyStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, startI);
        if (checkWeekend.Checked)
        {
            DateCopyEnd = DateCopyStart.AddDays(6);
        }
        else
        {
            DateCopyEnd = DateCopyStart.AddDays(4);
        } 
        textClipboard.Text = DateCopyStart.ToShortDateString() + "-" + DateCopyEnd.ToShortDateString();
    }

    private void butPaste_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a date first.");
            return ;
        }
         
        if (DateCopyStart.Year < 1880)
        {
            MsgBox.show(this,"Please copy a selection to the clipboard first.");
            return ;
        }
         
        if (ProvsChanged)
        {
            MsgBox.show(this,"Provider or Employee selection has been changed.  Please refresh first.");
            return ;
        }
         
        //calculate which day or week is currently selected.
        DateTime dateSelectedStart = new DateTime();
        DateTime dateSelectedEnd = new DateTime();
        boolean isWeek = DateCopyStart != DateCopyEnd;
        if (isWeek)
        {
            int startI = 1;
            if (checkWeekend.Checked)
            {
                startI = 0;
            }
             
            dateSelectedStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, startI);
            if (checkWeekend.Checked)
            {
                dateSelectedEnd = dateSelectedStart.AddDays(6);
            }
            else
            {
                dateSelectedEnd = dateSelectedStart.AddDays(4);
            } 
        }
        else
        {
            int selectedCol = gridMain.getSelectedCell().X;
            if (!checkWeekend.Checked)
            {
                selectedCol++;
            }
             
            dateSelectedStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, selectedCol);
            dateSelectedEnd = dateSelectedStart;
        } 
        //it's not allowed to paste back over the same day or week.
        if (dateSelectedStart == DateCopyStart)
        {
            MsgBox.show(this,"Not allowed to paste back onto the same date as is on the clipboard.");
            return ;
        }
         
        List<long> provNums = new List<long>();
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            provNums.Add(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum);
        }
        List<long> empNums = new List<long>();
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            empNums.Add(Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum);
        }
        if (checkReplace.Checked)
        {
            Schedules.Clear(dateSelectedStart, dateSelectedEnd, provNums, empNums, checkPractice.Checked);
        }
         
        List<Schedule> SchedList = Schedules.RefreshPeriod(DateCopyStart, DateCopyEnd, provNums, empNums, checkPractice.Checked);
        Schedule sched;
        int weekDelta = 0;
        if (isWeek)
        {
            TimeSpan span = dateSelectedStart - DateCopyStart;
            weekDelta = span.Days / 7;
        }
         
        for (int i = 0;i < SchedList.Count;i++)
        {
            //usually a positive # representing a future paste, but can be negative
            sched = SchedList[i];
            if (isWeek)
            {
                sched.SchedDate = sched.SchedDate.AddDays(weekDelta * 7);
            }
            else
            {
                sched.SchedDate = dateSelectedStart;
            } 
            Schedules.insert(sched,false);
        }
        DateTime rememberDateStart = DateCopyStart;
        DateTime rememberDateEnd = DateCopyEnd;
        fillGrid();
        DateCopyStart = rememberDateStart;
        DateCopyEnd = rememberDateEnd;
        if (isWeek)
        {
            textClipboard.Text = DateCopyStart.ToShortDateString() + "-" + DateCopyEnd.ToShortDateString();
        }
        else
        {
            textClipboard.Text = DateCopyStart.ToShortDateString();
        } 
        changed = true;
    }

    private void butRepeat_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        try
        {
            int.Parse(textRepeat.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix number box first.");
            return ;
        }

        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a date first.");
            return ;
        }
         
        if (DateCopyStart.Year < 1880)
        {
            MsgBox.show(this,"Please copy a selection to the clipboard first.");
            return ;
        }
         
        if (ProvsChanged)
        {
            MsgBox.show(this,"Provider or Employee selection has been changed.  Please refresh first.");
            return ;
        }
         
        //calculate which day or week is currently selected.
        DateTime dateSelectedStart = new DateTime();
        DateTime dateSelectedEnd = new DateTime();
        boolean isWeek = DateCopyStart != DateCopyEnd;
        if (isWeek)
        {
            int startI = 1;
            if (checkWeekend.Checked)
            {
                startI = 0;
            }
             
            dateSelectedStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, startI);
            if (checkWeekend.Checked)
            {
                dateSelectedEnd = dateSelectedStart.AddDays(6);
            }
            else
            {
                dateSelectedEnd = dateSelectedStart.AddDays(4);
            } 
        }
        else
        {
            int selectedCol = gridMain.getSelectedCell().X;
            if (!checkWeekend.Checked)
            {
                selectedCol++;
            }
             
            dateSelectedStart = Schedules.GetDateCal(PIn.Date(textDateFrom.Text), gridMain.getSelectedCell().Y, selectedCol);
            dateSelectedEnd = dateSelectedStart;
        } 
        //it is allowed to paste back over the same day or week.
        //if(dateSelectedStart==DateCopyStart) {
        //	MsgBox.Show(this,"Not allowed to paste back onto the same date as is on the clipboard.");
        //	return;
        //}
        List<long> provNums = new List<long>();
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            provNums.Add(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum);
        }
        List<long> empNums = new List<long>();
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            empNums.Add(Employees.getListShort()[listEmp.SelectedIndices[i]].EmployeeNum);
        }
        List<Schedule> SchedList = Schedules.RefreshPeriod(DateCopyStart, DateCopyEnd, provNums, empNums, checkPractice.Checked);
        Schedule sched;
        int weekDelta = 0;
        TimeSpan span = new TimeSpan();
        if (isWeek)
        {
            span = dateSelectedStart - DateCopyStart;
            weekDelta = span.Days / 7;
        }
         
        //usually a positive # representing a future paste, but can be negative
        int dayDelta = 0;
        for (int r = 0;r < PIn.Long(textRepeat.Text);r++)
        {
            //this is needed when repeat pasting days in order to calculate skipping weekends.
            //dayDelta will start out zero and increment separately from r.
            //for example, user wants to repeat 3 times.
            if (checkReplace.Checked)
            {
                if (isWeek)
                {
                    Schedules.Clear(dateSelectedStart.AddDays(r * 7), dateSelectedEnd.AddDays(r * 7), provNums, empNums, checkPractice.Checked);
                }
                else
                {
                    Schedules.Clear(dateSelectedStart.AddDays(dayDelta), dateSelectedEnd.AddDays(dayDelta), provNums, empNums, checkPractice.Checked);
                } 
            }
             
            for (int i = 0;i < SchedList.Count;i++)
            {
                //For example, if 3 weeks for one provider, then about 30 loops.
                sched = SchedList[i].Copy();
                if (isWeek)
                {
                    sched.SchedDate = sched.SchedDate.AddDays((weekDelta + r) * 7).AddHours(1).Date;
                }
                else
                {
                    sched.SchedDate = dateSelectedStart.AddDays(dayDelta);
                } 
                Schedules.insert(sched,false);
            }
            //if there is one provider in 4 ops, then this does 5 inserts per loop.
            if (!checkWeekend.Checked && dateSelectedStart.AddDays(dayDelta).DayOfWeek == DayOfWeek.Friday)
            {
                dayDelta += 3;
            }
            else
            {
                dayDelta++;
            } 
        }
        DateTime rememberDateStart = DateCopyStart;
        DateTime rememberDateEnd = DateCopyEnd;
        fillGrid();
        DateCopyStart = rememberDateStart;
        DateCopyEnd = rememberDateEnd;
        if (isWeek)
        {
            textClipboard.Text = DateCopyStart.ToShortDateString() + "-" + DateCopyEnd.ToShortDateString();
        }
        else
        {
            textClipboard.Text = DateCopyStart.ToShortDateString();
        } 
        changed = true;
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Staff schedule printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Schedule");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
            //text=textDateFrom.Text+" "+Lan.g(this,"to")+" "+textDateTo.Text;
            //g.DrawString(text,subHeadingFont,Brushes.Black,center-g.MeasureString(text,subHeadingFont).Width/2,yPos);
            yPos += 25;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    private void formSchedule_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            SecurityLogs.MakeLogEntry(Permissions.Schedules, 0, "");
        }
         
    }

}


