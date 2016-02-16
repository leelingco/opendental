//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.ScheduleListComparer;
import OpenDental.ScheduleListComparer.SortBy;
import OpenDental.ScheduleStartComparer;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleType;


/**
* This control is comprised of an outer SplitContainer which houses the sorting group box on top and the graphing panel on bottom. The bottom graphing panel is also a SplitContrainer in itself. This is to accomodate scrolling of the graph region only. The x-axis region is in the bottom panel of the SplitContainer graphing panel and will not be scrolled.
*/
public class GraphScheduleDay  extends UserControl 
{

    private Color graphBackColor = Color.LightBlue;
    public Color getGraphBackColor() throws Exception {
        return graphBackColor;
    }

    public void setGraphBackColor(Color value) throws Exception {
        graphBackColor = value;
        Invalidate(true);
    }

    private Color xAxisBackColor = Color.LightBlue;
    public Color getXAxisBackColor() throws Exception {
        return xAxisBackColor;
    }

    public void setXAxisBackColor(Color value) throws Exception {
        xAxisBackColor = value;
        Invalidate(true);
    }

    private Color providerBarColor = Color.SkyBlue;
    public Color getProviderBarColor() throws Exception {
        return providerBarColor;
    }

    public void setProviderBarColor(Color value) throws Exception {
        providerBarColor = value;
        checkBoxProviders.BackColor = value;
        Invalidate(true);
    }

    private Color providerTextColor = Color.Black;
    public Color getProviderTextColor() throws Exception {
        return providerTextColor;
    }

    public void setProviderTextColor(Color value) throws Exception {
        providerTextColor = value;
        checkBoxProviders.ForeColor = value;
        Invalidate(true);
    }

    private Color employeeBarColor = Color.SkyBlue;
    public Color getEmployeeBarColor() throws Exception {
        return employeeBarColor;
    }

    public void setEmployeeBarColor(Color value) throws Exception {
        employeeBarColor = value;
        checkBoxEmployees.BackColor = value;
        Invalidate(true);
    }

    private Color employeeTextColor = Color.Black;
    public Color getEmployeeTextColor() throws Exception {
        return employeeTextColor;
    }

    public void setEmployeeTextColor(Color value) throws Exception {
        employeeTextColor = value;
        checkBoxEmployees.ForeColor = value;
        Invalidate(true);
    }

    private Color practiceTextColor = Color.White;
    public Color getPracticeTextColor() throws Exception {
        return practiceTextColor;
    }

    public void setPracticeTextColor(Color value) throws Exception {
        practiceTextColor = value;
        checkBoxNotes.ForeColor = value;
        Invalidate(true);
    }

    private Color practiceBarColor = Color.Red;
    public Color getPracticeBarColor() throws Exception {
        return practiceBarColor;
    }

    public void setPracticeBarColor(Color value) throws Exception {
        practiceBarColor = value;
        checkBoxNotes.BackColor = value;
        Invalidate(true);
    }

    private int startHour = 0;
    public int getStartHour() throws Exception {
        return startHour;
    }

    public void setStartHour(int value) throws Exception {
        startHour = value;
        numStartHour.Value = value;
        Invalidate(true);
    }

    private int endHour = 0;
    public int getEndHour() throws Exception {
        return endHour;
    }

    public void setEndHour(int value) throws Exception {
        endHour = value;
        numEndHour.Value = value;
        Invalidate(true);
    }

    private int exteriorPaddingPixels = 0;
    public int getExteriorPaddingPixels() throws Exception {
        return exteriorPaddingPixels;
    }

    public void setExteriorPaddingPixels(int value) throws Exception {
        exteriorPaddingPixels = value;
        Invalidate(true);
    }

    private int lineWidthPixels = 1;
    public int getLineWidthPixels() throws Exception {
        return lineWidthPixels;
    }

    public void setLineWidthPixels(int value) throws Exception {
        lineWidthPixels = value;
        Invalidate(true);
    }

    private int tickHeightPixels = 1;
    public int getTickHeightPixels() throws Exception {
        return tickHeightPixels;
    }

    public void setTickHeightPixels(int value) throws Exception {
        tickHeightPixels = value;
        Invalidate(true);
    }

    private int barHeightPixels = 10;
    public int getBarHeightPixels() throws Exception {
        return barHeightPixels;
    }

    public void setBarHeightPixels(int value) throws Exception {
        barHeightPixels = value;
        Invalidate(true);
    }

    private int barSpacingPixels = 10;
    public int getBarSpacingPixels() throws Exception {
        return barSpacingPixels;
    }

    public void setBarSpacingPixels(int value) throws Exception {
        barSpacingPixels = value;
        Invalidate(true);
    }

    /**
    * Derived from StartHour and EndHour
    */
    public TimeSpan getTotalTime() throws Exception {
        return TimeSpan.FromHours(Math.Max(getEndHour() - getStartHour(), 1));
    }

    /**
    * Derived from StartHour
    */
    public TimeSpan getStartTime() throws Exception {
        return TimeSpan.FromHours(getStartHour());
    }

    /**
    * Derived from EndHour
    */
    public TimeSpan getEndTime() throws Exception {
        return TimeSpan.FromHours(getEndHour());
    }

    /**
    * Current sort choice
    */
    private ScheduleListComparer _compareScheduleLists = new ScheduleListComparer();
    /**
    * Sorted and filtered list of schedules to display
    */
    private List<List<Schedule>> _schedulesList = new List<List<Schedule>>();
    /**
    * Raw list of schedules from the database. These will be sorted and filtered in various ways for display purposes.
    */
    private List<Schedule> _schedules = new List<Schedule>();
    public GraphScheduleDay() throws Exception {
        initializeComponent();
        //set styles so that this panel draws itself
        SetStyle(ControlStyles.ResizeRedraw, true);
        SetStyle(ControlStyles.AllPaintingInWmPaint, true);
        SetStyle(ControlStyles.UserPaint, true);
        SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
    }

    public void setSchedules(List<Schedule> schedules) throws Exception {
        //set the raw list
        _schedules = new List<Schedule>(schedules);
        //clear the existing list of lists and repopulate
        _schedulesList.Clear();
        for (int schedIndex = 0;schedIndex < schedules.Count;schedIndex++)
        {
            //single entities (employees or providers) can have more than 1 schedule linked to them
            //loop through raw schedules and build a list of lists
            //each out list will account for 1 row on the graph and represent 1 employee or provider
            Schedule schedule = schedules[schedIndex];
            if (!checkBoxNotes.Checked && (schedule.SchedType == ScheduleType.Blockout || schedule.SchedType == ScheduleType.Practice))
            {
                continue;
            }
             
            //apply filter
            if (!checkBoxEmployees.Checked && schedule.SchedType == ScheduleType.Employee)
            {
                continue;
            }
             
            //apply filter
            if (!checkBoxProviders.Checked && schedule.SchedType == ScheduleType.Provider)
            {
                continue;
            }
             
            //apply filter
            switch(schedule.SchedType)
            {
                case Blockout: 
                case Practice: 
                    if (!checkBoxNotes.Checked)
                    {
                        continue;
                    }
                     
                    //these types always get their own separate line
                    _schedulesList.Add(new List<Schedule>(new Schedule[]{ schedule }));
                    break;
                case Provider: 
                case Employee: 
                    //these types can have multiple schedules per line
                    //loop through our exsiting lists and try to find a match
                    boolean isFound = false;
                    for (int masterIndex = 0;masterIndex < _schedulesList.Count;masterIndex++)
                    {
                        List<Schedule> existingSchedules = _schedulesList[masterIndex];
                        Schedule existingSchedule = existingSchedules[0];
                        if (existingSchedule.SchedType != schedule.SchedType)
                        {
                            continue;
                        }
                         
                        if (existingSchedule.SchedType == ScheduleType.Provider)
                        {
                            if (existingSchedule.ProvNum == schedule.ProvNum)
                            {
                                existingSchedules.Add(schedule);
                                isFound = true;
                            }
                             
                        }
                        else if (existingSchedule.SchedType == ScheduleType.Employee)
                        {
                            if (existingSchedule.EmployeeNum == schedule.EmployeeNum)
                            {
                                existingSchedules.Add(schedule);
                                isFound = true;
                            }
                             
                        }
                          
                    }
                    if (!isFound)
                    {
                        //we didn't find a match so add a new collection
                        _schedulesList.Add(new List<Schedule>(new Schedule[]{ schedule }));
                    }
                     
                    break;
            
            }
        }
        //now that the list is of lists is built, sort each individual collection by start time
        ScheduleStartComparer sortStartTime = new ScheduleStartComparer();
        for (int i = 0;i < _schedulesList.Count;i++)
        {
            _schedulesList[i].Sort(sortStartTime);
        }
        //now that the collections are individually sorted, sort the master list of lists according to user preference
        _schedulesList.Sort(_compareScheduleLists);
        //force redraw
        this.Invalidate(true);
    }

    private int getPanel1GraphHeight() throws Exception {
        return Math.Max(this.splitContainerBottom.Panel1.ClientRectangle.Height, _schedulesList.Count * (getBarSpacingPixels() + getBarHeightPixels()));
    }

    private float getFontHeight() throws Exception {
        float emSize = 1;
        while (true)
        {
            Font newFont = new Font(Font.FontFamily, emSize, Font.Style);
            try
            {
                {
                    SizeF size = TextRenderer.MeasureText("0", newFont);
                    if ((size.Height + .2f) < getBarHeightPixels())
                    {
                        //still too samll so increment the font size and try again
                        emSize += .1F;
                        continue;
                    }
                     
                    return emSize;
                }
            }
            finally
            {
                if (newFont != null)
                    Disposable.mkDisposable(newFont).dispose();
                 
            }
        }
    }

    /**
    * Draw the graph region
    */
    private void splitContainer_Panel1_Paint(Object sender, PaintEventArgs e) throws Exception {
        Pen penLine = new Pen(Color.Black, getLineWidthPixels());
        Font font = new Font(this.Font.FontFamily, getFontHeight(), this.Font.Style);
        try
        {
            //offset the drawing region to current scroll location
            e.Graphics.TranslateTransform(this.splitContainerBottom.Panel1.AutoScrollPosition.X, this.splitContainerBottom.Panel1.AutoScrollPosition.Y);
            //size the drawable region
            RectangleF rcBounds = new RectangleF(penLine.Width / 2, penLine.Width / 2, this.splitContainerBottom.Panel1.ClientRectangle.Width - penLine.Width, getPanel1GraphHeight() + penLine.Width);
            //fill the background
            Brush brushBack = new SolidBrush(getGraphBackColor());
            try
            {
                {
                    e.Graphics.FillRectangle(brushBack, rcBounds.Left, rcBounds.Top, rcBounds.Width, rcBounds.Height);
                }
            }
            finally
            {
                if (brushBack != null)
                    Disposable.mkDisposable(brushBack).dispose();
                 
            }
            //draw the outline
            e.Graphics.DrawRectangle(penLine, rcBounds.Left, rcBounds.Top, rcBounds.Width, rcBounds.Height);
            //shrink the remaining drawable region to fit inside the rest of the pen width
            rcBounds.Inflate(-penLine.Width / 2, -penLine.Width / 2);
            //squish the drawable region horizontally so we have some outer edge buffer
            rcBounds.Inflate(-getExteriorPaddingPixels(), 0);
            //each minute gets this many pixels
            float pixelsPerMinute = rcBounds.Width / (float)getTotalTime().TotalMinutes;
            //get the pixel width of each hour
            float hourWidth = rcBounds.Width / (float)getTotalTime().TotalHours;
            for (int hour = 0;hour <= (int)getTotalTime().TotalHours;hour++)
            {
                float xPos = rcBounds.Left + (hour * hourWidth);
                for (int quarter = 0;quarter <= 3;quarter++)
                {
                    //draw 15 minute dashed lines
                    Pen penDash = new Pen(quarter == 0 ? Color.Black : Color.LightGray, getLineWidthPixels());
                    try
                    {
                        {
                            e.Graphics.DrawLine(penDash, xPos, rcBounds.Bottom, xPos, rcBounds.Top);
                            xPos += (hourWidth / 4);
                        }
                    }
                    finally
                    {
                        if (penDash != null)
                            Disposable.mkDisposable(penDash).dispose();
                         
                    }
                }
            }
            if (_schedulesList == null || _schedulesList.Count <= 0)
            {
                return ;
            }
             
            //draw the bars for each schedule
            //the combined height of the followin for loop must match EXACTLY the value returned by GetPanel1GraphHeight().
            //each schedule get 1 BarSpacingPixels plus 1 BarHeightPixels
            //any change to that rule must have a corresponding change to the GetPanel1GraphHeight() function
            float yPosStart = rcBounds.Top;
            for (int i = 0;i < _schedulesList.Count;i++)
            {
                yPosStart += getBarSpacingPixels();
                List<Schedule> schedules = _schedulesList[i];
                for (int j = 0;j < schedules.Count;j++)
                {
                    Schedule schedule = schedules[j];
                    float xPosStart = Math.Max(rcBounds.Left, (rcBounds.Left + (float)schedule.StartTime.Subtract(this.getStartTime()).TotalMinutes * pixelsPerMinute));
                    float xWidth = Math.Min(rcBounds.Right, (float)(schedule.StopTime.Subtract(schedule.StartTime).TotalMinutes * pixelsPerMinute));
                    String desc = "";
                    Color barColor = getEmployeeBarColor();
                    Color textColor = getEmployeeTextColor();
                    if (schedule.SchedType == ScheduleType.Practice)
                    {
                        //this is just used to create a date-level note
                        xPosStart = rcBounds.Left;
                        xWidth = rcBounds.Width;
                        barColor = getPracticeBarColor();
                        textColor = getPracticeTextColor();
                    }
                    else if (schedule.ProvNum != 0)
                    {
                        //Prov
                        desc = Providers.getAbbr(schedule.ProvNum);
                        barColor = getProviderBarColor();
                        textColor = getProviderTextColor();
                    }
                    else if (schedule.EmployeeNum != 0)
                    {
                        //Employee
                        desc = Employees.getEmp(schedule.EmployeeNum).FName;
                        barColor = getEmployeeBarColor();
                        textColor = getEmployeeTextColor();
                    }
                       
                    if (!StringSupport.equals(schedule.Note, ""))
                    {
                        desc += " -- " + schedule.Note;
                    }
                     
                    Brush brushBar = new SolidBrush(barColor);
                    try
                    {
                        {
                            e.Graphics.FillRectangle(brushBar, xPosStart, yPosStart, xWidth, getBarHeightPixels());
                        }
                    }
                    finally
                    {
                        if (brushBar != null)
                            Disposable.mkDisposable(brushBar).dispose();
                         
                    }
                    Brush brushNoteText = new SolidBrush(textColor);
                    try
                    {
                        {
                            e.Graphics.DrawString(desc, font, brushNoteText, xPosStart + 2, yPosStart);
                        }
                    }
                    finally
                    {
                        if (brushNoteText != null)
                            Disposable.mkDisposable(brushNoteText).dispose();
                         
                    }
                }
                yPosStart += getBarHeightPixels();
            }
            //draw a faint line indicating what time of day it is right now
            if (DateTime.Now >= DateTime.Today.AddHours(getStartHour()) && DateTime.Now <= DateTime.Today.AddHours(getEndHour()))
            {
                Pen penNow = new Pen(Color.FromArgb(128, Color.Red), getLineWidthPixels() * 2);
                try
                {
                    {
                        int minutes = (int)DateTime.Now.TimeOfDay.Subtract(TimeSpan.FromHours(getStartHour())).TotalMinutes;
                        float xPos = rcBounds.Left + (minutes * pixelsPerMinute);
                        e.Graphics.DrawLine(penNow, xPos, rcBounds.Bottom, xPos, rcBounds.Top);
                    }
                }
                finally
                {
                    if (penNow != null)
                        Disposable.mkDisposable(penNow).dispose();
                     
                }
            }
             
            if (this.splitContainerBottom.Panel1.AutoScrollMinSize.Height != (int)yPosStart)
            {
                //create vertical scrollbar only
                this.splitContainerBottom.Panel1.AutoScrollMinSize = new Size(0, (int)yPosStart);
                this.splitContainerBottom.Invalidate(true);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            if (penLine != null)
            {
                penLine.Dispose();
            }
             
            if (font != null)
            {
                font.Dispose();
            }
             
        }
    }

    /**
    * Draw the x-axis region
    */
    private void splitContainer_Panel2_Paint(Object sender, PaintEventArgs e) throws Exception {
        Pen penLine = new Pen(Color.Black, getLineWidthPixels());
        Brush brushForeColor = new SolidBrush(this.ForeColor);
        Font font = new Font(this.Font, this.Font.Style);
        try
        {
            //draw the back color as the background
            Brush brushBack = new SolidBrush(this.BackColor);
            try
            {
                {
                    e.Graphics.FillRectangle(brushBack, this.splitContainerBottom.Panel2.ClientRectangle);
                }
            }
            finally
            {
                if (brushBack != null)
                    Disposable.mkDisposable(brushBack).dispose();
                 
            }
            //shrink the drawable region to accomodate half the pen width
            RectangleF rcBounds = new RectangleF(penLine.Width / 2, penLine.Width / 2, this.splitContainerBottom.Panel2.ClientRectangle.Width - penLine.Width, this.splitContainerBottom.Panel2.ClientRectangle.Height - penLine.Width);
            if (splitContainerBottom.Panel1.VerticalScroll.Visible)
            {
                rcBounds.Inflate(-((float)SystemInformation.VerticalScrollBarWidth / 2), 0);
                rcBounds.Offset(-((float)SystemInformation.VerticalScrollBarWidth / 2), 0);
                //fill whitespace left underneath the vertical scrollbar from Panel1
                e.Graphics.FillRectangle(SystemBrushes.Control, rcBounds.Right, this.splitContainerBottom.Panel2.ClientRectangle.Top, SystemInformation.VerticalScrollBarWidth, this.splitContainerBottom.Panel2.ClientRectangle.Height);
            }
             
            //fill the background
            Brush brushBack = new SolidBrush(getXAxisBackColor());
            try
            {
                {
                    e.Graphics.FillRectangle(brushBack, rcBounds.Left, rcBounds.Top, rcBounds.Width, rcBounds.Height);
                }
            }
            finally
            {
                if (brushBack != null)
                    Disposable.mkDisposable(brushBack).dispose();
                 
            }
            //draw the outline
            e.Graphics.DrawRectangle(penLine, rcBounds.Left, rcBounds.Top, rcBounds.Width, rcBounds.Height);
            //shrink the drawable region to fit inside the rest of the pen width
            rcBounds.Inflate(-penLine.Width / 2, -penLine.Width / 2);
            //squish the drawable region horizontally so we have some outer edge buffer
            rcBounds.Inflate(-getExteriorPaddingPixels(), 0);
            //get the pixel width of each hour
            float hourWidth = rcBounds.Width / (float)getTotalTime().TotalHours;
            for (int hour = 0;hour <= (int)getTotalTime().TotalHours;hour++)
            {
                DateTime dt = DateTime.Today.AddHours(getStartHour() + hour);
                String stringHour = dt.ToString("htt").ToLower();
                SizeF szHour = e.Graphics.MeasureString(stringHour, font);
                float xPos = rcBounds.Left + (hour * hourWidth);
                float yPos = rcBounds.Top;
                //draw the tick
                e.Graphics.DrawLine(penLine, xPos, yPos, xPos, yPos + getTickHeightPixels());
                yPos += getTickHeightPixels();
                //draw the hour
                e.Graphics.DrawString(stringHour, font, brushForeColor, xPos - (szHour.Width / 2), yPos);
            }
        }
        catch (Exception __dummyCatchVar1)
        {
        }
        finally
        {
            if (penLine != null)
            {
                penLine.Dispose();
            }
             
            if (brushForeColor != null)
            {
                brushForeColor.Dispose();
            }
             
            if (font != null)
            {
                font.Dispose();
            }
             
        }
    }

    private void radioName_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (!radioName.Checked)
        {
            return ;
        }
         
        _compareScheduleLists.Sort = SortBy.Name;
        _schedulesList.Sort(_compareScheduleLists);
        this.Invalidate(true);
    }

    private void radioStartTime_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (!radioStartTime.Checked)
        {
            return ;
        }
         
        _compareScheduleLists.Sort = SortBy.StartTime;
        _schedulesList.Sort(_compareScheduleLists);
        this.Invalidate(true);
    }

    private void radioStopTime_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (!radioStopTime.Checked)
        {
            return ;
        }
         
        _compareScheduleLists.Sort = SortBy.StopTime;
        _schedulesList.Sort(_compareScheduleLists);
        this.Invalidate(true);
    }

    /**
    * Filter our dispaly and remove Practice notes
    */
    private void checkBoxNotes_CheckedChanged(Object sender, EventArgs e) throws Exception {
        setSchedules(_schedules);
    }

    /**
    * Filter our dispaly and remove Providers
    */
    private void checkBoxProviders_CheckedChanged(Object sender, EventArgs e) throws Exception {
        setSchedules(_schedules);
    }

    /**
    * Filter our dispaly and remove Employees
    */
    private void checkBoxEmployees_CheckedChanged(Object sender, EventArgs e) throws Exception {
        setSchedules(_schedules);
    }

    private void numStartHour_ValueChanged(Object sender, EventArgs e) throws Exception {
        setStartHour((int)numStartHour.Value);
    }

    private void numEndHour_ValueChanged(Object sender, EventArgs e) throws Exception {
        setEndHour((int)numEndHour.Value);
    }

    /**
    * Redraw the graph so the current time bar gets moved
    */
    private void timerRefresh_Tick(Object sender, EventArgs e) throws Exception {
        Invalidate(true);
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
        this.components = new System.ComponentModel.Container();
        this.timerRefresh = new System.Windows.Forms.Timer(this.components);
        this.splitContainerMaster = new OpenDental.SplitContainerNoFlicker();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.numEndHour = new System.Windows.Forms.NumericUpDown();
        this.label3 = new System.Windows.Forms.Label();
        this.numStartHour = new System.Windows.Forms.NumericUpDown();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.checkBoxEmployees = new System.Windows.Forms.CheckBox();
        this.checkBoxProviders = new System.Windows.Forms.CheckBox();
        this.checkBoxNotes = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioStopTime = new System.Windows.Forms.RadioButton();
        this.radioStartTime = new System.Windows.Forms.RadioButton();
        this.radioName = new System.Windows.Forms.RadioButton();
        this.splitContainerBottom = new OpenDental.SplitContainerNoFlicker();
        ((System.ComponentModel.ISupportInitialize)(this.splitContainerMaster)).BeginInit();
        this.splitContainerMaster.Panel1.SuspendLayout();
        this.splitContainerMaster.Panel2.SuspendLayout();
        this.splitContainerMaster.SuspendLayout();
        this.groupBox3.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.numEndHour)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.numStartHour)).BeginInit();
        this.groupBox2.SuspendLayout();
        this.groupBox1.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.splitContainerBottom)).BeginInit();
        this.splitContainerBottom.SuspendLayout();
        this.SuspendLayout();
        //
        // timerRefresh
        //
        this.timerRefresh.Enabled = true;
        this.timerRefresh.Interval = 5000;
        this.timerRefresh.Tick += new System.EventHandler(this.timerRefresh_Tick);
        //
        // splitContainerMaster
        //
        this.splitContainerMaster.Dock = System.Windows.Forms.DockStyle.Fill;
        this.splitContainerMaster.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
        this.splitContainerMaster.IsSplitterFixed = true;
        this.splitContainerMaster.Location = new System.Drawing.Point(0, 0);
        this.splitContainerMaster.Name = "splitContainerMaster";
        this.splitContainerMaster.Orientation = System.Windows.Forms.Orientation.Horizontal;
        //
        // splitContainerMaster.Panel1
        //
        this.splitContainerMaster.Panel1.Controls.Add(this.groupBox3);
        this.splitContainerMaster.Panel1.Controls.Add(this.groupBox2);
        this.splitContainerMaster.Panel1.Controls.Add(this.groupBox1);
        //
        // splitContainerMaster.Panel2
        //
        this.splitContainerMaster.Panel2.Controls.Add(this.splitContainerBottom);
        this.splitContainerMaster.Size = new System.Drawing.Size(738, 462);
        this.splitContainerMaster.SplitterDistance = 52;
        this.splitContainerMaster.TabIndex = 2;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.numEndHour);
        this.groupBox3.Controls.Add(this.label3);
        this.groupBox3.Controls.Add(this.numStartHour);
        this.groupBox3.Controls.Add(this.label2);
        this.groupBox3.Location = new System.Drawing.Point(489, 3);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(243, 43);
        this.groupBox3.TabIndex = 3;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Scale (0-24)";
        //
        // numEndHour
        //
        this.numEndHour.Location = new System.Drawing.Point(183, 16);
        this.numEndHour.Margin = new System.Windows.Forms.Padding(2);
        this.numEndHour.Maximum = new double(new int[]{ 24, 0, 0, 0 });
        this.numEndHour.Name = "numEndHour";
        this.numEndHour.Size = new System.Drawing.Size(44, 20);
        this.numEndHour.TabIndex = 8;
        this.numEndHour.ValueChanged += new System.EventHandler(this.numEndHour_ValueChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(122, 17);
        this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(56, 19);
        this.label3.TabIndex = 9;
        this.label3.Text = "End Hour";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // numStartHour
        //
        this.numStartHour.Location = new System.Drawing.Point(74, 16);
        this.numStartHour.Margin = new System.Windows.Forms.Padding(2);
        this.numStartHour.Maximum = new double(new int[]{ 24, 0, 0, 0 });
        this.numStartHour.Name = "numStartHour";
        this.numStartHour.Size = new System.Drawing.Size(44, 20);
        this.numStartHour.TabIndex = 6;
        this.numStartHour.ValueChanged += new System.EventHandler(this.numStartHour_ValueChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 17);
        this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(64, 19);
        this.label2.TabIndex = 7;
        this.label2.Text = "Start Hour";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.checkBoxEmployees);
        this.groupBox2.Controls.Add(this.checkBoxProviders);
        this.groupBox2.Controls.Add(this.checkBoxNotes);
        this.groupBox2.Location = new System.Drawing.Point(251, 3);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(232, 43);
        this.groupBox2.TabIndex = 2;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Filter";
        //
        // checkBoxEmployees
        //
        this.checkBoxEmployees.BackColor = System.Drawing.SystemColors.Control;
        this.checkBoxEmployees.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBoxEmployees.Checked = true;
        this.checkBoxEmployees.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkBoxEmployees.Location = new System.Drawing.Point(145, 18);
        this.checkBoxEmployees.Name = "checkBoxEmployees";
        this.checkBoxEmployees.Size = new System.Drawing.Size(77, 20);
        this.checkBoxEmployees.TabIndex = 4;
        this.checkBoxEmployees.Text = "Employees";
        this.checkBoxEmployees.UseVisualStyleBackColor = false;
        this.checkBoxEmployees.CheckedChanged += new System.EventHandler(this.checkBoxEmployees_CheckedChanged);
        //
        // checkBoxProviders
        //
        this.checkBoxProviders.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBoxProviders.Checked = true;
        this.checkBoxProviders.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkBoxProviders.Location = new System.Drawing.Point(69, 18);
        this.checkBoxProviders.Name = "checkBoxProviders";
        this.checkBoxProviders.Size = new System.Drawing.Size(70, 20);
        this.checkBoxProviders.TabIndex = 3;
        this.checkBoxProviders.Text = "Providers";
        this.checkBoxProviders.UseVisualStyleBackColor = true;
        this.checkBoxProviders.CheckedChanged += new System.EventHandler(this.checkBoxProviders_CheckedChanged);
        //
        // checkBoxNotes
        //
        this.checkBoxNotes.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBoxNotes.Checked = true;
        this.checkBoxNotes.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkBoxNotes.Location = new System.Drawing.Point(9, 18);
        this.checkBoxNotes.Name = "checkBoxNotes";
        this.checkBoxNotes.Size = new System.Drawing.Size(54, 20);
        this.checkBoxNotes.TabIndex = 2;
        this.checkBoxNotes.Text = "Notes";
        this.checkBoxNotes.UseVisualStyleBackColor = true;
        this.checkBoxNotes.CheckedChanged += new System.EventHandler(this.checkBoxNotes_CheckedChanged);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioStopTime);
        this.groupBox1.Controls.Add(this.radioStartTime);
        this.groupBox1.Controls.Add(this.radioName);
        this.groupBox1.Location = new System.Drawing.Point(0, 3);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(245, 43);
        this.groupBox1.TabIndex = 1;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Sort";
        //
        // radioStopTime
        //
        this.radioStopTime.AutoSize = true;
        this.radioStopTime.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioStopTime.Checked = true;
        this.radioStopTime.Location = new System.Drawing.Point(95, 20);
        this.radioStopTime.Name = "radioStopTime";
        this.radioStopTime.Size = new System.Drawing.Size(73, 17);
        this.radioStopTime.TabIndex = 2;
        this.radioStopTime.TabStop = true;
        this.radioStopTime.Text = "Stop Time";
        this.radioStopTime.UseVisualStyleBackColor = true;
        this.radioStopTime.CheckedChanged += new System.EventHandler(this.radioStopTime_CheckedChanged);
        //
        // radioStartTime
        //
        this.radioStartTime.AutoSize = true;
        this.radioStartTime.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioStartTime.Location = new System.Drawing.Point(5, 20);
        this.radioStartTime.Name = "radioStartTime";
        this.radioStartTime.Size = new System.Drawing.Size(73, 17);
        this.radioStartTime.TabIndex = 1;
        this.radioStartTime.Text = "Start Time";
        this.radioStartTime.UseVisualStyleBackColor = true;
        this.radioStartTime.CheckedChanged += new System.EventHandler(this.radioStartTime_CheckedChanged);
        //
        // radioName
        //
        this.radioName.AutoSize = true;
        this.radioName.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioName.Location = new System.Drawing.Point(185, 20);
        this.radioName.Name = "radioName";
        this.radioName.Size = new System.Drawing.Size(53, 17);
        this.radioName.TabIndex = 0;
        this.radioName.Text = "Name";
        this.radioName.UseVisualStyleBackColor = true;
        this.radioName.CheckedChanged += new System.EventHandler(this.radioName_CheckedChanged);
        //
        // splitContainerBottom
        //
        this.splitContainerBottom.Dock = System.Windows.Forms.DockStyle.Fill;
        this.splitContainerBottom.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
        this.splitContainerBottom.IsSplitterFixed = true;
        this.splitContainerBottom.Location = new System.Drawing.Point(0, 0);
        this.splitContainerBottom.Name = "splitContainerBottom";
        this.splitContainerBottom.Orientation = System.Windows.Forms.Orientation.Horizontal;
        //
        // splitContainerBottom.Panel1
        //
        this.splitContainerBottom.Panel1.AutoScroll = true;
        this.splitContainerBottom.Panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.splitContainer_Panel1_Paint);
        //
        // splitContainerBottom.Panel2
        //
        this.splitContainerBottom.Panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.splitContainer_Panel2_Paint);
        this.splitContainerBottom.Size = new System.Drawing.Size(738, 406);
        this.splitContainerBottom.SplitterDistance = 380;
        this.splitContainerBottom.SplitterWidth = 1;
        this.splitContainerBottom.TabIndex = 0;
        //
        // GraphScheduleDay
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.splitContainerMaster);
        this.DoubleBuffered = true;
        this.Name = "GraphScheduleDay";
        this.Size = new System.Drawing.Size(738, 462);
        this.splitContainerMaster.Panel1.ResumeLayout(false);
        this.splitContainerMaster.Panel2.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.splitContainerMaster)).EndInit();
        this.splitContainerMaster.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.numEndHour)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.numStartHour)).EndInit();
        this.groupBox2.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        ((System.ComponentModel.ISupportInitialize)(this.splitContainerBottom)).EndInit();
        this.splitContainerBottom.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.SplitContainerNoFlicker splitContainerBottom;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioStopTime = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioStartTime = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioName = new System.Windows.Forms.RadioButton();
    private OpenDental.SplitContainerNoFlicker splitContainerMaster;
    private System.Windows.Forms.CheckBox checkBoxEmployees = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkBoxProviders = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkBoxNotes = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Timer timerRefresh = new System.Windows.Forms.Timer();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.NumericUpDown numStartHour = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown numEndHour = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


