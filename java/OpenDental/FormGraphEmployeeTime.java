//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.AppointmentL;
import OpenDental.FormPhoneGraphDateEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Employees.EmployeeComparer.SortBy;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PhoneGraphs;
import OpenDentBusiness.PhoneMetrics;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.Security;
import OpenDental.FormGraphEmployeeTime;
import OpenDental.Properties.Resources;

public class FormGraphEmployeeTime  extends Form 
{

    private List<PointF> listCalls = new List<PointF>();
    private float[] buckets = new float[]();
    //a bucket can hold partial people.
    private DateTime DateShowing = new DateTime();
    private int[] minutesBehind = new int[]();
    /**
    * Retrieved once when opening the form, then reused.
    */
    private List<PhoneEmpDefault> ListPED = new List<PhoneEmpDefault>();
    private List<Schedule> ListScheds = new List<Schedule>();
    private List<Region> ListRegions = new List<Region>();
    private int CurrentHoverRegionIdx = -1;
    /**
    * holds employee info gathered on paint and displayed on hover
    */
    /*key = the bucket index*/
    private Dictionary<int, List<Employee>> DictEmployeesPerBucket = new Dictionary<int, List<Employee>>();
    /*value = list of employees in this bucket*/
    public FormGraphEmployeeTime() throws Exception {
        initializeComponent();
        Lan.F(this);
        toolTip.ToolTipTitle = Lan.g(this,"Employees");
        ListRegions = new List<Region>();
    }

    private void formGraphEmployeeTime_Load(Object sender, EventArgs e) throws Exception {
        butEdit.Visible = Security.isAuthorized(Permissions.Schedules);
        ListPED = PhoneEmpDefaults.refresh();
        DateShowing = AppointmentL.DateSelected.Date;
        //fill in the missing PhoneGraph entries for today
        PhoneGraphs.addMissingEntriesForToday(ListPED);
        fillData();
    }

    private void fillData() throws Exception {
        DictEmployeesPerBucket = new Dictionary<int, List<Employee>>();
        labelDate.Text = DateShowing.ToString("dddd, MMMM d");
        butEdit.Enabled = DateShowing.Date >= DateTime.Today;
        //do not allow editing of past dates
        listCalls = new List<PointF>();
        if (DateShowing.DayOfWeek == DayOfWeek.Friday)
        {
            listCalls.Add(new PointF(5f, 0));
            listCalls.Add(new PointF(5.5f, 50));
            //5-6am
            listCalls.Add(new PointF(6.5f, 133));
            listCalls.Add(new PointF(7.5f, 210));
            listCalls.Add(new PointF(8.5f, 332));
            listCalls.Add(new PointF(9f, 360));
            //-
            listCalls.Add(new PointF(9.5f, 370));
            //was 380
            listCalls.Add(new PointF(10f, 360));
            //-
            listCalls.Add(new PointF(10.5f, 352));
            //was 348
            listCalls.Add(new PointF(11.5f, 352));
            listCalls.Add(new PointF(12.5f, 340));
            //was 313
            listCalls.Add(new PointF(13.5f, 325));
            //was 363
            listCalls.Add(new PointF(14.5f, 277));
            listCalls.Add(new PointF(15.5f, 185));
            listCalls.Add(new PointF(16.5f, 141));
            listCalls.Add(new PointF(17f, 50));
            listCalls.Add(new PointF(17.0f, 0));
        }
        else
        {
            listCalls.Add(new PointF(5f, 0));
            listCalls.Add(new PointF(5.5f, 284));
            //5-6am
            listCalls.Add(new PointF(6.5f, 767));
            listCalls.Add(new PointF(7.5f, 1246));
            listCalls.Add(new PointF(8.5f, 1753));
            listCalls.Add(new PointF(9f, 1920));
            //-
            listCalls.Add(new PointF(9.5f, 2000));
            //was 2029
            listCalls.Add(new PointF(10f, 1950));
            //-
            listCalls.Add(new PointF(10.5f, 1875));
            //1846
            listCalls.Add(new PointF(11.5f, 1890));
            //1899
            listCalls.Add(new PointF(12.5f, 1820));
            listCalls.Add(new PointF(13.5f, 1807));
            listCalls.Add(new PointF(14.5f, 1565));
            listCalls.Add(new PointF(15.5f, 1178));
            listCalls.Add(new PointF(16.5f, 733));
            listCalls.Add(new PointF(17.5f, 226));
            listCalls.Add(new PointF(17.5f, 0));
        } 
        buckets = new float[56];
        //Number of total bucket. 4 buckets per hour * 14 hours = 56 buckets.
        ListScheds = Schedules.getDayList(DateShowing);
        //PhoneGraph exceptions will take precedence over employee default
        List<PhoneGraph> listPhoneGraphs = PhoneGraphs.getAllForDate(DateShowing);
        TimeSpan time1 = new TimeSpan();
        TimeSpan time2 = new TimeSpan();
        TimeSpan delta = new TimeSpan();
        for (int i = 0;i < ListScheds.Count;i++)
        {
            if (ListScheds[i].SchedType != ScheduleType.Employee)
            {
                continue;
            }
             
            //get this employee
            Employee employee = Employees.GetEmp(ListScheds[i].EmployeeNum);
            if (employee == null)
            {
                continue;
            }
             
            //employees will NEVER be deleted. even after they cease to work here. but just in case.
            boolean hasPhoneGraphEntry = false;
            boolean isGraphed = false;
            for (int iPG = 0;iPG < listPhoneGraphs.Count;iPG++)
            {
                //PhoneGraph entries will take priority over the default employee graph state
                if (listPhoneGraphs[iPG].EmployeeNum == employee.EmployeeNum)
                {
                    isGraphed = listPhoneGraphs[iPG].IsGraphed;
                    hasPhoneGraphEntry = true;
                    break;
                }
                 
            }
            if (!hasPhoneGraphEntry)
            {
                //no phone graph entry found (likely for a future date which does not have entries created yet OR past date where current employee didn't work here yet)
                if (DateShowing <= DateTime.Today)
                {
                    continue;
                }
                 
                //no phone graph entry and we are on a past OR current date. if it's not already created then don't graph this employee for this date
                //we are on a future date AND we don't have a PhoneGraph entry explicitly set so use the default for this employee
                PhoneEmpDefault ped = PhoneEmpDefaults.GetEmpDefaultFromList(ListScheds[i].EmployeeNum, ListPED);
                if (ped == null)
                {
                    continue;
                }
                 
                //we will default to PhoneEmpDefault.IsGraphed so make sure the deafult exists
                //no entry in PhoneGraph for the employee on this date so use the default
                isGraphed = ped.IsGraphed;
            }
             
            if (!isGraphed)
            {
                continue;
            }
             
            for (int b = 0;b < buckets.Length;b++)
            {
                //only care about employees that are being graphed
                //minutes field multiplier is a function of buckets per hour. answers the question, "how many minutes long is each bucket?"
                time1 = new TimeSpan(5, 0, 0) + new TimeSpan(0, b * 15, 0);
                time2 = new TimeSpan(5, 15, 0) + new TimeSpan(0, b * 15, 0);
                //situation 1: this bucket is completely within the start and stop times.
                if (ListScheds[i].StartTime <= time1 && ListScheds[i].StopTime >= time2)
                {
                    addEmployeeToBucket(b,employee);
                }
                else //situation 2: the start time is after this bucket
                if (ListScheds[i].StartTime >= time2)
                {
                    continue;
                }
                else //situation 3: the stop time is before this bucket
                if (ListScheds[i].StopTime <= time1)
                {
                    continue;
                }
                   
                //situation 4: start time falls within this bucket
                if (ListScheds[i].StartTime > time1)
                {
                    delta = ListScheds[i].StartTime - time1;
                    //7.5 minutes is half of one bucket.
                    if (delta.TotalMinutes > 7.5)
                    {
                        //has to work more than 15 minutes to be considered *in* this bucket
                        addEmployeeToBucket(b,employee);
                    }
                     
                }
                 
                //situation 5: stop time falls within this bucket
                if (ListScheds[i].StopTime < time2)
                {
                    delta = time2 - ListScheds[i].StopTime;
                    if (delta.TotalMinutes > 7.5)
                    {
                        //has to work more than 15 minutes to be considered *in* this bucket
                        addEmployeeToBucket(b,employee);
                    }
                     
                }
                 
            }
        }
        //break;//just show one sched for debugging.
        //missed calls
        //missedCalls=new int[28];
        //List<DateTime> callTimes=PhoneAsterisks.GetMissedCalls(dateShowing);
        //for(int i=0;i<callTimes.Count;i++) {
        //  for(int b=0;b<missedCalls.Length;b++) {
        //    time1=new TimeSpan(5,0,0) + new TimeSpan(0,b*30,0);
        //    time2=new TimeSpan(5,30,0) + new TimeSpan(0,b*30,0);
        //    if(callTimes[i].TimeOfDay >= time1 && callTimes[i].TimeOfDay < time2) {
        //      missedCalls[b]++;
        //    }
        //  }
        //}
        //Minutes Behind
        minutesBehind = PhoneMetrics.averageMinutesBehind(DateShowing);
        this.Invalidate();
    }

    private void addEmployeeToBucket(int bucketIndex, Employee employee) throws Exception {
        buckets[bucketIndex] += 1;
        List<Employee> employees = null;
        RefSupport<List<Employee>> refVar___0 = new RefSupport<List<Employee>>();
        boolean boolVar___0 = !DictEmployeesPerBucket.TryGetValue(bucketIndex, refVar___0);
        employees = refVar___0.getValue();
        if (boolVar___0)
        {
            employees = new List<Employee>();
        }
         
        if (employee != null)
        {
            employees.Add(employee);
        }
         
        DictEmployeesPerBucket[bucketIndex] = employees;
    }

    private void formGraphEmployeeTime_Paint(Object sender, PaintEventArgs e) throws Exception {
        ListRegions.Clear();
        e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
        RectangleF rec = new RectangleF(panel1.Left, panel1.Top, panel1.Width, panel1.Height);
        e.Graphics.FillRectangle(Brushes.White, rec);
        if (listCalls == null)
        {
            return ;
        }
         
        float highcall = 0;
        for (int i = 0;i < listCalls.Count;i++)
        {
            if (listCalls[i].Y > highcall)
            {
                highcall = listCalls[i].Y;
            }
             
        }
        float totalhrs = 14f;
        float x1 = new float();
        float y1 = new float();
        float x2 = new float();
        float y2 = 0;
        for (int i = 1;i < (int)totalhrs;i++)
        {
            //draw grid
            //vertical
            x1 = rec.X + ((float)i * rec.Width / totalhrs);
            y1 = rec.Y + rec.Height;
            x2 = rec.X + ((float)i * rec.Width / totalhrs);
            y2 = rec.Y;
            e.Graphics.DrawLine(Pens.Black, x1, y1, x2, y2);
        }
        //draw numbers
        //x-axis
        String str = new String();
        float strW = new float();
        for (int i = 0;i < (int)totalhrs + 1;i++)
        {
            if (i < 8)
            {
                str = (i + 5).ToString();
            }
            else
            {
                str = (i - 7).ToString();
            } 
            strW = e.Graphics.MeasureString(str, Font).Width;
            x1 = rec.X + ((float)i * rec.Width / totalhrs) - strW / 2f;
            y1 = rec.Y + rec.Height + 3;
            e.Graphics.DrawString(str, Font, Brushes.Black, x1, y1);
        }
        //find the biggest bar
        float peak = PIn.Int(PrefC.getRaw("GraphEmployeeTimesPeak"));
        //The ideal peak.  Each day should look the same, except Friday.
        if (DateShowing.DayOfWeek == DayOfWeek.Friday)
        {
            peak = peak * 0.8f;
        }
         
        //The Friday graph is actually smaller than the other graphs.
        float superPeak = PIn.Int(PrefC.getRaw("GraphEmployeeTimesSuperPeak"));
        //the most staff possible to schedule
        /*for(int i=0;i<buckets.Length;i++){
        				if(buckets[i]>biggest){
        					biggest=buckets[i];
        				}
        			}*/
        float hOne = rec.Height / superPeak;
        //draw bars
        float x = new float();
        float y = new float();
        float w = new float();
        float h = new float();
        float barspacing = (rec.Width / totalhrs) / 4f;
        //4f means number of buckets per hours.  EG... 10 minute granularity = 6f;
        float firstbar = barspacing / 2f;
        float barW = barspacing / 1.5f;
        //increase denominator in order to increase spacing between bars. 1f = no space... 2f = full bar space. 1.5f = half bar space.
        SolidBrush blueBrush = new SolidBrush(Color.FromArgb(162, 193, 222));
        for (int i = 0;i < buckets.Length;i++)
        {
            h = (float)buckets[i] * rec.Height / superPeak;
            x = rec.X + firstbar + (float)i * barspacing - barW / 2f;
            y = rec.Y + rec.Height - h;
            w = barW;
            RectangleF rc = new RectangleF(x, y, w, h);
            e.Graphics.FillRectangle(Brushes.LightBlue, rc);
            ListRegions.Add(new System.Drawing.Region(rc));
            for (int o = 1;o < buckets[i];o++)
            {
                //save this bucket for hover tooltip event
                //draw bar increments
                x1 = x;
                y1 = rec.Y + rec.Height - (o * hOne);
                x2 = x + barW;
                y2 = rec.Y + rec.Height - (o * hOne);
                e.Graphics.DrawLine(Pens.Black, x1, y1, x2, y2);
            }
            //draw the number of employees in this bucket
            SizeF sf = e.Graphics.MeasureString(buckets[i].ToString(), SystemFonts.DefaultFont);
        }
        //removing for now. number of employees now included in hover text.
        //e.Graphics.DrawString(buckets[i].ToString(),SystemFonts.DefaultFont,Brushes.Blue,x+(barW-sf.Width)/2,y-(sf.Height+1));
        //Line graph in red
        float peakH = rec.Height * peak / superPeak;
        Pen redPen = new Pen(Brushes.Red, 2f);
        for (int i = 0;i < listCalls.Count - 1;i++)
        {
            x1 = rec.X + ((listCalls[i].X - 5f) * rec.Width / totalhrs);
            y1 = rec.Y + rec.Height - (listCalls[i].Y / highcall * peakH);
            x2 = rec.X + ((listCalls[i + 1].X - 5f) * rec.Width / totalhrs);
            y2 = rec.Y + rec.Height - (listCalls[i + 1].Y / highcall * peakH);
            e.Graphics.DrawLine(redPen, x1, y1, x2, y2);
        }
        for (int i = 0;i < minutesBehind.Length;i++)
        {
            //Missed call numbers
            if (minutesBehind[i] == 0)
            {
                continue;
            }
             
            str = minutesBehind[i].ToString();
            strW = e.Graphics.MeasureString(str, Font).Width;
            x1 = rec.X + barW + ((float)i * rec.Width / totalhrs / 2) - strW / 2f;
            y1 = rec.Y + rec.Height - (17 * 2);
            e.Graphics.DrawString(str, Font, Brushes.Red, x1, y1);
        }
        //Vertical red line for current time
        if (DateTime.Today.Date == DateShowing.Date)
        {
            TimeSpan now = DateTime.Now.AddHours(-5).TimeOfDay;
            float shift = (float)now.TotalHours * rec.Width / totalhrs;
            x1 = rec.X + shift;
            y1 = rec.Y + rec.Height;
            x2 = rec.X + shift;
            y2 = rec.Y;
            e.Graphics.DrawLine(Pens.Red, x1, y1, x2, y2);
        }
         
        redPen.Dispose();
        blueBrush.Dispose();
    }

    private void panel1_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        for (int i = 0;i < ListRegions.Count;i++)
        {
            //there is 1 region per bucket (synced in paint event), loop through the regions and see if we are hovering over one of them
            if (!ListRegions[i].IsVisible(new Point(e.X, e.Y)))
            {
                continue;
            }
             
            //we are hovering over this bucket
            if (i == CurrentHoverRegionIdx)
            {
                return ;
            }
             
            //only activate this bucket once (prevents flicker)
            //build the display string for this hover bucket
            List<Employee> listEmps = null;
            TimeSpan tsStart = new TimeSpan(5, (i * 15), 0);
            toolTip.ToolTipTitle = tsStart.ToShortTimeString() + " - " + tsStart.Add(TimeSpan.FromMinutes(15)).ToShortTimeString();
            String employees = "";
            RefSupport<List<Employee>> refVar___1 = new RefSupport<List<Employee>>();
            boolean boolVar___1 = DictEmployeesPerBucket.TryGetValue(i, refVar___1);
            listEmps = refVar___1.getValue();
            if (boolVar___1)
            {
                toolTip.ToolTipTitle = toolTip.ToolTipTitle + " (" + listEmps.Count.ToString() + " Techs)";
                listEmps.Sort(new OpenDentBusiness.Employees.EmployeeComparer(SortBy.firstName));
                for (int p = 0;p < listEmps.Count;p++)
                {
                    List<Schedule> sch = Schedules.GetForEmployee(ListScheds, listEmps[p].EmployeeNum);
                    employees += listEmps[p].FName;
                    employees += Schedules.GetCommaDelimStringForScheds(sch);
                    employees += "\r\n";
                }
            }
             
            //activate and show this bucket's tooltip
            toolTip.Active = true;
            toolTip.SetToolTip(this, employees);
            //save this region as current so we only activate it once
            CurrentHoverRegionIdx = i;
            return ;
        }
        //not hovering over a bucket so kill the tooltip
        toolTip.Active = false;
        CurrentHoverRegionIdx = -1;
    }

    private void buttonLeft_Click(Object sender, EventArgs e) throws Exception {
        if (DateShowing.DayOfWeek == DayOfWeek.Monday)
        {
            DateShowing = DateShowing.AddDays(-3);
        }
        else
        {
            DateShowing = DateShowing.AddDays(-1);
        } 
        fillData();
    }

    private void buttonRight_Click(Object sender, EventArgs e) throws Exception {
        if (DateShowing.DayOfWeek == DayOfWeek.Friday)
        {
            DateShowing = DateShowing.AddDays(+3);
        }
        else
        {
            DateShowing = DateShowing.AddDays(+1);
        } 
        fillData();
    }

    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        FormPhoneGraphDateEdit FormPGDE = new FormPhoneGraphDateEdit(DateShowing);
        FormPGDE.ShowDialog();
        fillData();
    }

    //always refill, we may have new entries regardless of form dialog result
    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Employee time graph printed"))
        {
            pd.Print();
        }
         
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        e.Graphics.DrawString(labelDate.Text, labelDate.Font, Brushes.Black, 350, 120);
        Bitmap bitmap = new Bitmap(this.ClientSize.Width, this.ClientSize.Height);
        this.DrawToBitmap(bitmap, new Rectangle(0, 0, bitmap.Width, bitmap.Height));
        e.Graphics.DrawImage(bitmap, 50, 200);
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormGraphEmployeeTime.class);
        this.panel1 = new System.Windows.Forms.Panel();
        this.labelDate = new System.Windows.Forms.Label();
        this.buttonLeft = new OpenDental.UI.Button();
        this.buttonRight = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butEdit = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.toolTip = new System.Windows.Forms.ToolTip(this.components);
        this.SuspendLayout();
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.Location = new System.Drawing.Point(34, 53);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(1167, 534);
        this.panel1.TabIndex = 5;
        this.panel1.Visible = false;
        //
        // labelDate
        //
        this.labelDate.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDate.Location = new System.Drawing.Point(512, 21);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(203, 23);
        this.labelDate.TabIndex = 6;
        this.labelDate.Text = "Monday, January 1st";
        this.labelDate.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // buttonLeft
        //
        this.buttonLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonLeft.setAutosize(true);
        this.buttonLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonLeft.setCornerRadius(4F);
        this.buttonLeft.Image = Resources.getLeft();
        this.buttonLeft.Location = new System.Drawing.Point(484, 22);
        this.buttonLeft.Name = "buttonLeft";
        this.buttonLeft.Size = new System.Drawing.Size(22, 22);
        this.buttonLeft.TabIndex = 8;
        this.buttonLeft.UseVisualStyleBackColor = true;
        this.buttonLeft.Click += new System.EventHandler(this.buttonLeft_Click);
        //
        // buttonRight
        //
        this.buttonRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonRight.setAutosize(true);
        this.buttonRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonRight.setCornerRadius(4F);
        this.buttonRight.Image = Resources.getRight();
        this.buttonRight.Location = new System.Drawing.Point(721, 21);
        this.buttonRight.Name = "buttonRight";
        this.buttonRight.Size = new System.Drawing.Size(22, 22);
        this.buttonRight.TabIndex = 7;
        this.buttonRight.UseVisualStyleBackColor = true;
        this.buttonRight.Click += new System.EventHandler(this.buttonRight_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(1126, 637);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(877, 637);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 24);
        this.butPrint.TabIndex = 9;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.ForeColor = System.Drawing.Color.Red;
        this.label1.Location = new System.Drawing.Point(213, 611);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(126, 23);
        this.label1.TabIndex = 10;
        this.label1.Text = "# Minutes Behind";
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Image = Resources.geteditPencil();
        this.butEdit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEdit.Location = new System.Drawing.Point(34, 637);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(75, 24);
        this.butEdit.TabIndex = 11;
        this.butEdit.Text = "Edit";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.ForeColor = System.Drawing.Color.Blue;
        this.label2.Location = new System.Drawing.Point(347, 611);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(140, 23);
        this.label2.TabIndex = 12;
        this.label2.Text = "# Techs Available";
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label3.ForeColor = System.Drawing.Color.Red;
        this.label3.Location = new System.Drawing.Point(31, 611);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(174, 23);
        this.label3.TabIndex = 13;
        this.label3.Text = "------- Expected Call Volume";
        //
        // toolTip
        //
        this.toolTip.AutoPopDelay = 30000;
        this.toolTip.InitialDelay = 50;
        this.toolTip.IsBalloon = true;
        this.toolTip.ReshowDelay = 50;
        this.toolTip.ToolTipTitle = "Employees";
        //
        // FormGraphEmployeeTime
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1226, 673);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.buttonLeft);
        this.Controls.Add(this.buttonRight);
        this.Controls.Add(this.labelDate);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormGraphEmployeeTime";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Graph Employee Time";
        this.Load += new System.EventHandler(this.FormGraphEmployeeTime_Load);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.FormGraphEmployeeTime_Paint);
        this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panel1_MouseMove);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
    private OpenDental.UI.Button buttonRight;
    private OpenDental.UI.Button buttonLeft;
    private OpenDental.UI.Button butPrint;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEdit;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ToolTip toolTip = new System.Windows.Forms.ToolTip();
}


