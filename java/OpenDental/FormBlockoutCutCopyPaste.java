//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:44 PM
//

package OpenDental;

import OpenDental.FormBlockoutCutCopyPaste;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.ApptViewItems;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;

/**
* Summary description for FormBasicTemplate.
*/
public class FormBlockoutCutCopyPaste  extends System.Windows.Forms.Form 
{
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butCopyWeek;
    private OpenDental.UI.Button butCopyDay;
    private GroupBox groupBox2 = new GroupBox();
    private OpenDental.UI.Button butRepeat;
    private Label label4 = new Label();
    private CheckBox checkReplace = new CheckBox();
    private TextBox textRepeat = new TextBox();
    private OpenDental.UI.Button butPaste;
    private CheckBox checkWeekend = new CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private TextBox textClipboard = new TextBox();
    private Label label3 = new Label();
    private Label label1 = new Label();
    private static DateTime DateCopyStart = DateTime.MinValue;
    private static DateTime DateCopyEnd = DateTime.MinValue;
    public long ApptViewNumCur = new long();
    private static long ApptViewNumPrevious = new long();
    private OpenDental.UI.Button butClearDay;
    public DateTime DateSelected = new DateTime();
    /**
    * 
    */
    public FormBlockoutCutCopyPaste() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormBlockoutCutCopyPaste.class);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textClipboard = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.butCopyWeek = new OpenDental.UI.Button();
        this.butCopyDay = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butRepeat = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.checkReplace = new System.Windows.Forms.CheckBox();
        this.textRepeat = new System.Windows.Forms.TextBox();
        this.butPaste = new OpenDental.UI.Button();
        this.checkWeekend = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butClearDay = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textClipboard);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.butCopyWeek);
        this.groupBox1.Controls.Add(this.butCopyDay);
        this.groupBox1.Location = new System.Drawing.Point(26, 137);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(158, 118);
        this.groupBox1.TabIndex = 40;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Copy";
        //
        // textClipboard
        //
        this.textClipboard.Location = new System.Drawing.Point(6, 33);
        this.textClipboard.Name = "textClipboard";
        this.textClipboard.ReadOnly = true;
        this.textClipboard.Size = new System.Drawing.Size(146, 20);
        this.textClipboard.TabIndex = 30;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(146, 14);
        this.label3.TabIndex = 29;
        this.label3.Text = "Clipboard Contents";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butCopyWeek
        //
        this.butCopyWeek.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopyWeek.setAutosize(true);
        this.butCopyWeek.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopyWeek.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopyWeek.setCornerRadius(4F);
        this.butCopyWeek.Location = new System.Drawing.Point(6, 85);
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
        this.butCopyDay.Location = new System.Drawing.Point(6, 58);
        this.butCopyDay.Name = "butCopyDay";
        this.butCopyDay.Size = new System.Drawing.Size(75, 24);
        this.butCopyDay.TabIndex = 27;
        this.butCopyDay.Text = "Copy Day";
        this.butCopyDay.Click += new System.EventHandler(this.butCopyDay_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butRepeat);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.checkReplace);
        this.groupBox2.Controls.Add(this.textRepeat);
        this.groupBox2.Controls.Add(this.butPaste);
        this.groupBox2.Location = new System.Drawing.Point(26, 263);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(158, 97);
        this.groupBox2.TabIndex = 45;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Paste";
        //
        // butRepeat
        //
        this.butRepeat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRepeat.setAutosize(true);
        this.butRepeat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRepeat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRepeat.setCornerRadius(4F);
        this.butRepeat.Location = new System.Drawing.Point(6, 64);
        this.butRepeat.Name = "butRepeat";
        this.butRepeat.Size = new System.Drawing.Size(75, 24);
        this.butRepeat.TabIndex = 30;
        this.butRepeat.Text = "Repeat";
        this.butRepeat.Click += new System.EventHandler(this.butRepeat_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(70, 70);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(37, 14);
        this.label4.TabIndex = 32;
        this.label4.Text = "#";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomRight;
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
        // textRepeat
        //
        this.textRepeat.Location = new System.Drawing.Point(110, 67);
        this.textRepeat.Name = "textRepeat";
        this.textRepeat.Size = new System.Drawing.Size(39, 20);
        this.textRepeat.TabIndex = 31;
        this.textRepeat.Text = "1";
        //
        // butPaste
        //
        this.butPaste.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPaste.setAutosize(true);
        this.butPaste.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPaste.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPaste.setCornerRadius(4F);
        this.butPaste.Location = new System.Drawing.Point(6, 37);
        this.butPaste.Name = "butPaste";
        this.butPaste.Size = new System.Drawing.Size(75, 24);
        this.butPaste.TabIndex = 29;
        this.butPaste.Text = "Paste";
        this.butPaste.Click += new System.EventHandler(this.butPaste_Click);
        //
        // checkWeekend
        //
        this.checkWeekend.Location = new System.Drawing.Point(32, 113);
        this.checkWeekend.Name = "checkWeekend";
        this.checkWeekend.Size = new System.Drawing.Size(143, 18);
        this.checkWeekend.TabIndex = 46;
        this.checkWeekend.Text = "Include Weekends";
        this.checkWeekend.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(29, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(245, 59);
        this.label1.TabIndex = 47;
        this.label1.Text = "Remember that this tool only applies to the visible operatories for the current a" + "ppointment view.  It also does not copy to a different operatory.";
        //
        // butClearDay
        //
        this.butClearDay.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearDay.setAutosize(true);
        this.butClearDay.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearDay.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearDay.setCornerRadius(4F);
        this.butClearDay.Location = new System.Drawing.Point(32, 78);
        this.butClearDay.Name = "butClearDay";
        this.butClearDay.Size = new System.Drawing.Size(75, 24);
        this.butClearDay.TabIndex = 48;
        this.butClearDay.Text = "Clear Day";
        this.butClearDay.Click += new System.EventHandler(this.butClearDay_Click);
        //
        // FormBlockoutCutCopyPaste
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(290, 383);
        this.Controls.Add(this.butClearDay);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkWeekend);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupBox2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormBlockoutCutCopyPaste";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Blockout Cut-Copy-Paste";
        this.Load += new System.EventHandler(this.FormBlockoutCutCopyPaste_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formBlockoutCutCopyPaste_Load(Object sender, EventArgs e) throws Exception {
        if (ApptViewNumCur != ApptViewNumPrevious)
        {
            DateCopyStart = DateTime.MinValue;
            DateCopyEnd = DateTime.MinValue;
        }
         
        fillClipboard();
        ApptViewNumPrevious = ApptViewNumCur;
    }

    //remember the appt view for next time.
    private void butClearDay_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Clear all blockouts for day?"))
        {
            return ;
        }
         
        Schedules.clearBlockoutsForDay(DateSelected);
        //works for daily or weekly
        Close();
    }

    private void fillClipboard() throws Exception {
        if (DateCopyStart.Year < 1880)
        {
            textClipboard.Text = "";
        }
        else if (DateCopyStart == DateCopyEnd)
        {
            textClipboard.Text = DateCopyStart.ToShortDateString();
        }
        else
        {
            textClipboard.Text = DateCopyStart.ToShortDateString() + "-" + DateCopyEnd.ToShortDateString();
        }  
    }

    private void butCopyDay_Click(Object sender, EventArgs e) throws Exception {
        DateCopyStart = DateSelected;
        DateCopyEnd = DateSelected;
        //FillClipboard();
        Close();
    }

    private void butCopyWeek_Click(Object sender, EventArgs e) throws Exception {
        if (checkWeekend.Checked)
        {
            DateCopyStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek));
            //eg Wed-3=Sun.
            DateCopyEnd = DateCopyStart.AddDays(6);
        }
        else
        {
            DateCopyStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek) + 1);
            //eg Wed-3+1=Mon.
            DateCopyEnd = DateCopyStart.AddDays(4);
        } 
        //FillClipboard();
        Close();
    }

    private void butPaste_Click(Object sender, EventArgs e) throws Exception {
        if (DateCopyStart.Year < 1880)
        {
            MsgBox.show(this,"Please copy a selection to the clipboard first.");
            return ;
        }
         
        //calculate which day or week is currently selected.
        DateTime dateSelectedStart = new DateTime();
        DateTime dateSelectedEnd = new DateTime();
        boolean isWeek = DateCopyStart != DateCopyEnd;
        if (isWeek)
        {
            if (checkWeekend.Checked)
            {
                dateSelectedStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek));
                dateSelectedEnd = dateSelectedStart.AddDays(6);
            }
            else
            {
                dateSelectedStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek) + 1);
                dateSelectedEnd = dateSelectedStart.AddDays(4);
            } 
        }
        else
        {
            dateSelectedStart = DateSelected;
            dateSelectedEnd = DateSelected;
        } 
        //it's not allowed to paste back over the same day or week.
        if (dateSelectedStart == DateCopyStart)
        {
            MsgBox.show(this,"Not allowed to paste back onto the same date as is on the clipboard.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        List<long> opNums = ApptViewItems.getOpsForView(ApptViewNumCur);
        List<Schedule> SchedList = Schedules.RefreshPeriodBlockouts(DateCopyStart, DateCopyEnd, opNums);
        if (checkReplace.Checked)
        {
            Schedules.ClearBlockouts(dateSelectedStart, dateSelectedEnd, opNums);
        }
         
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
            sched.ScheduleNum = 0;
            //So that overlap logic works.
            if (isWeek)
            {
                sched.SchedDate = sched.SchedDate.AddDays(weekDelta * 7);
            }
            else
            {
                sched.SchedDate = dateSelectedStart;
            } 
            Schedules.insert(sched,true);
        }
        //Doing it this way makes use of validation to prevent overlaps
        Cursor = Cursors.Default;
        Close();
    }

    private void butRepeat_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            int.Parse(textRepeat.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix number box first.");
            return ;
        }

        if (DateCopyStart.Year < 1880)
        {
            MsgBox.show(this,"Please copy a selection to the clipboard first.");
            return ;
        }
         
        //calculate which day or week is currently selected.
        DateTime dateSelectedStart = new DateTime();
        DateTime dateSelectedEnd = new DateTime();
        boolean isWeek = DateCopyStart != DateCopyEnd;
        if (isWeek)
        {
            if (checkWeekend.Checked)
            {
                dateSelectedStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek));
                dateSelectedEnd = dateSelectedStart.AddDays(6);
            }
            else
            {
                dateSelectedStart = DateSelected.AddDays(-((int)DateSelected.DayOfWeek) + 1);
                dateSelectedEnd = dateSelectedStart.AddDays(4);
            } 
        }
        else
        {
            dateSelectedStart = DateSelected;
            dateSelectedEnd = DateSelected;
        } 
        Cursor = Cursors.WaitCursor;
        //it is allowed to paste back over the same day or week.
        List<long> opNums = ApptViewItems.getOpsForView(ApptViewNumCur);
        List<Schedule> SchedList = Schedules.RefreshPeriodBlockouts(DateCopyStart, DateCopyEnd, opNums);
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
            if (checkReplace.Checked)
            {
                if (isWeek)
                {
                    Schedules.ClearBlockouts(dateSelectedStart.AddDays(r * 7), dateSelectedEnd.AddDays(r * 7), opNums);
                }
                else
                {
                    Schedules.ClearBlockouts(dateSelectedStart.AddDays(dayDelta), dateSelectedEnd.AddDays(dayDelta), opNums);
                } 
            }
             
            for (int i = 0;i < SchedList.Count;i++)
            {
                sched = SchedList[i].Copy();
                sched.ScheduleNum = 0;
                //So that overlap logic works.
                if (isWeek)
                {
                    sched.SchedDate = sched.SchedDate.AddDays((weekDelta + r) * 7);
                }
                else
                {
                    sched.SchedDate = dateSelectedStart.AddDays(dayDelta);
                } 
                Schedules.insert(sched,true);
            }
            if (!checkWeekend.Checked && dateSelectedStart.AddDays(dayDelta).DayOfWeek == DayOfWeek.Friday)
            {
                dayDelta += 3;
            }
            else
            {
                dayDelta++;
            } 
        }
        Cursor = Cursors.Default;
        Close();
    }

}


