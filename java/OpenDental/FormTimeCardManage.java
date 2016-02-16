//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.FormQuery;
import OpenDental.FormTimeCard;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.ObjectDateComparer;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.ReportSimpleGrid;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Security;
import OpenDentBusiness.TimeAdjust;
import OpenDentBusiness.TimeAdjusts;
import OpenDentBusiness.TimeCardRules;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormTimeCardManage  extends Form 
{

    private int SelectedPayPeriod = new int();
    private DateTime DateStart = new DateTime();
    private DateTime DateStop = new DateTime();
    private DataTable MainTable = new DataTable();
    private int pagesPrinted = new int();
    private String totalTime = new String();
    private String overTime = new String();
    private String rate2Time = new String();
    private String totalTime2 = new String();
    private String overTime2 = new String();
    private String rate2Time2 = new String();
    private int PagesPrinted = new int();
    private boolean HeadingPrinted = new boolean();
    public FormTimeCardManage() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTimeCardManage_Load(Object sender, EventArgs e) throws Exception {
        SelectedPayPeriod = PayPeriods.getForDate(DateTimeOD.getToday());
        if (SelectedPayPeriod == -1)
        {
            MsgBox.show(this,"At least one pay period needs to exist before you can manage time cards.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        fillPayPeriod();
        fillMain();
    }

    //butCompute.Visible=false;			//only until unit tests are complete. exceed
    //butDaily.Visible=false;			//only until unit tests are complete.
    private void fillMain() throws Exception {
        MainTable = ClockEvents.getTimeCardManage(DateStart,DateStop);
        //,false);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Employee"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Total Hrs"),75);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate1"),75);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate1 OT"),75);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate2"),75);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate2 OT"),75);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g(this,"Auto Adj"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g(this,"Reg Adj"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g(this,"OT Adj"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g(this,"Diff Adj"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g(this,"Diff Auto"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g(this,"Breaks"),64);
        //col.TextAlign=HorizontalAlignment.Right;
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g(this,"Notes"),0);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < MainTable.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //row.Cells.Add(Employees.GetNameFL(PIn.Long(MainTable.Rows[i]["EmployeeNum"].ToString())));
            row.getCells().Add(MainTable.Rows[i]["lastName"] + ", " + MainTable.Rows[i]["firstName"]);
            if (PrefC.getBool(PrefName.TimeCardsUseDecimalInsteadOfColon))
            {
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["totalHours"].ToString()).TotalHours.ToString("n"));
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate1Hours"].ToString()).TotalHours.ToString("n"));
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate1OTHours"].ToString()).TotalHours.ToString("n"));
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate2Hours"].ToString()).TotalHours.ToString("n"));
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate2OTHours"].ToString()).TotalHours.ToString("n"));
            }
            else
            {
                //row.Cells.Add(PIn.Time(MainTable.Rows[i]["BreakTime"].ToString()).TotalHours.ToString("n"));
                //row.Cells.Add(PIn.Time(MainTable.Rows[i]["ClockEventRegAdj"].ToString()).TotalHours.ToString("n"));
                //row.Cells.Add(PIn.Time(MainTable.Rows[i]["TimeAdjustRegAdj"].ToString()).TotalHours.ToString("n"));
                //row.Cells.Add(PIn.Time(MainTable.Rows[i]["TimeAdjustOTAdj"].ToString()).TotalHours.ToString("n"));
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["totalHours"].ToString()).ToStringHmm());
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate1Hours"].ToString()).ToStringHmm());
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate1OTHours"].ToString()).ToStringHmm());
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate2Hours"].ToString()).ToStringHmm());
                row.getCells().Add(PIn.Time(MainTable.Rows[i]["rate2OTHours"].ToString()).ToStringHmm());
            } 
            //row.Cells.Add(PIn.Time(MainTable.Rows[i]["BreakTime"].ToString()).ToStringHmm());
            //row.Cells.Add(PIn.Time(MainTable.Rows[i]["ClockEventRegAdj"].ToString()).ToStringHmm());
            //row.Cells.Add(PIn.Time(MainTable.Rows[i]["TimeAdjustRegAdj"].ToString()).ToStringHmm());
            //row.Cells.Add(PIn.Time(MainTable.Rows[i]["TimeAdjustOTAdj"].ToString()).ToStringHmm());
            row.getCells().Add(MainTable.Rows[i]["Note"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    /**
    * SelectedPayPeriod should already be set.  This simply fills the screen with that data.
    */
    private void fillPayPeriod() throws Exception {
        DateStart = PayPeriods.getList()[SelectedPayPeriod].DateStart;
        DateStop = PayPeriods.getList()[SelectedPayPeriod].DateStop;
        textDateStart.Text = DateStart.ToShortDateString();
        textDateStop.Text = DateStop.ToShortDateString();
        if (PayPeriods.getList()[SelectedPayPeriod].DatePaycheck.Year < 1880)
        {
            textDatePaycheck.Text = "";
        }
        else
        {
            textDatePaycheck.Text = PayPeriods.getList()[SelectedPayPeriod].DatePaycheck.ToShortDateString();
        } 
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormTimeCard FormTC = new FormTimeCard();
        FormTC.EmployeeCur = Employees.GetEmp(PIn.Long(MainTable.Rows[e.getRow()]["EmployeeNum"].ToString()));
        FormTC.SelectedPayPeriod = SelectedPayPeriod;
        FormTC.ShowDialog();
        fillMain();
    }

    /**
    * This is a modified version of FormTimeCard.FillMain().  It fills one time card per employee.
    */
    private OpenDental.UI.ODGrid getGridForPrinting(Employee emp) throws Exception {
        OpenDental.UI.ODGrid gridTimeCard = new OpenDental.UI.ODGrid();
        List<ClockEvent> clockEventList = ClockEvents.Refresh(emp.EmployeeNum, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text), false);
        List<TimeAdjust> timeAdjustList = TimeAdjusts.Refresh(emp.EmployeeNum, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
        ArrayList mergedAL = new ArrayList();
        for (int i = 0;i < clockEventList.Count;i++)
        {
            mergedAL.Add(clockEventList[i]);
        }
        for (int i = 0;i < timeAdjustList.Count;i++)
        {
            mergedAL.Add(timeAdjustList[i]);
        }
        IComparer myComparer = new ObjectDateComparer();
        mergedAL.Sort(myComparer);
        gridTimeCard.beginUpdate();
        gridTimeCard.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Weekday"),70);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"In"), 60, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Out"), 60, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Total"), 50, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Adjust"), 55, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate2"), 55, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Overtime"), 55, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Daily"), 50, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Weekly"), 50, HorizontalAlignment.Right);
        gridTimeCard.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),5);
        gridTimeCard.getColumns().add(col);
        gridTimeCard.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        TimeSpan[] weeklyTotals = new TimeSpan[mergedAL.Count];
        TimeSpan alteredSpan = new TimeSpan(0);
        //used to display altered times
        TimeSpan oneSpan = new TimeSpan(0);
        //used to sum one pair of clock-in/clock-out
        TimeSpan oneAdj = new TimeSpan();
        TimeSpan oneOT = new TimeSpan();
        TimeSpan daySpan = new TimeSpan(0);
        //used for daily totals.
        TimeSpan weekSpan = new TimeSpan(0);
        //used for weekly totals.
        if (mergedAL.Count > 0)
        {
            weekSpan = ClockEvents.getWeekTotal(emp.EmployeeNum,getDateForRow(0,mergedAL));
        }
         
        TimeSpan periodSpan = new TimeSpan(0);
        //used to add up totals for entire page.
        TimeSpan otspan = new TimeSpan(0);
        //overtime for the entire period
        TimeSpan rate2span = new TimeSpan(0);
        //rate2 hours total
        Calendar cal = CultureInfo.CurrentCulture.Calendar;
        CalendarWeekRule rule = CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule;
        DateTime curDate = DateTime.MinValue;
        DateTime previousDate = DateTime.MinValue;
        Type type = new Type();
        ClockEvent clock;
        TimeAdjust adjust;
        for (int i = 0;i < mergedAL.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            type = mergedAL[i].GetType();
            row.setTag(mergedAL[i]);
            previousDate = curDate;
            //clock event row---------------------------------------------------------------------------------------------
            if (type == ClockEvent.class)
            {
                clock = (ClockEvent)mergedAL[i];
                curDate = clock.TimeDisplayed1.Date;
                if (curDate == previousDate)
                {
                    row.getCells().add("");
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(curDate.ToShortDateString());
                    row.getCells().Add(curDate.DayOfWeek.ToString());
                } 
                //altered--------------------------------------
                //deprecated
                //status--------------------------------------
                //row.Cells.Add(clock.ClockStatus.ToString());
                //in------------------------------------------
                row.getCells().Add(clock.TimeDisplayed1.ToShortTimeString());
                if (clock.TimeEntered1 != clock.TimeDisplayed1)
                {
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //out-----------------------------
                if (clock.TimeDisplayed2.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    //not clocked out yet
                    row.getCells().Add(clock.TimeDisplayed2.ToShortTimeString());
                    if (clock.TimeEntered2 != clock.TimeDisplayed2)
                    {
                        row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                    }
                     
                } 
                //total-------------------------------
                if (clock.TimeDisplayed2.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    oneSpan = clock.TimeDisplayed2 - clock.TimeDisplayed1;
                    row.getCells().add(ClockEvents.format(oneSpan));
                    daySpan += oneSpan;
                    weekSpan += oneSpan;
                    periodSpan += oneSpan;
                } 
                //Adjust---------------------------------
                oneAdj = TimeSpan.Zero;
                if (clock.AdjustIsOverridden)
                {
                    oneAdj += clock.Adjust;
                }
                else
                {
                    oneAdj += clock.AdjustAuto;
                } 
                //typically zero
                daySpan += oneAdj;
                weekSpan += oneAdj;
                periodSpan += oneAdj;
                row.getCells().add(ClockEvents.format(oneAdj));
                if (clock.AdjustIsOverridden)
                {
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //Rate2---------------------------------
                if (clock.Rate2Hours != TimeSpan.FromHours(-1))
                {
                    rate2span += clock.Rate2Hours;
                    row.getCells().add(ClockEvents.format(clock.Rate2Hours));
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                else
                {
                    rate2span += clock.Rate2Auto;
                    row.getCells().add(ClockEvents.format(clock.Rate2Auto));
                } 
                //Overtime------------------------------
                oneOT = TimeSpan.Zero;
                if (clock.OTimeHours != TimeSpan.FromHours(-1))
                {
                    //overridden
                    oneOT = clock.OTimeHours;
                }
                else
                {
                    oneOT = clock.OTimeAuto;
                } 
                //typically zero
                otspan += oneOT;
                daySpan -= oneOT;
                weekSpan -= oneOT;
                periodSpan -= oneOT;
                row.getCells().add(ClockEvents.format(oneOT));
                if (clock.OTimeHours != TimeSpan.FromHours(-1))
                {
                    //overridden
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRow(i + 1,mergedAL) != curDate)
                {
                    //or the next row is a different date
                    row.getCells().add(ClockEvents.format(daySpan));
                    daySpan = new TimeSpan(0);
                }
                else
                {
                    //not the last entry for the day
                    row.getCells().add("");
                } 
                //Weekly-------------------------------------
                weeklyTotals[i] = weekSpan;
                //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRow(i + 1,mergedAL), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(clock.TimeDisplayed1.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    row.getCells().add(ClockEvents.format(weekSpan));
                    weekSpan = new TimeSpan(0);
                }
                else
                {
                    //row.Cells.Add(ClockEvents.Format(weekSpan));
                    row.getCells().add("");
                } 
                //Note-----------------------------------------
                row.getCells().add(clock.Note);
            }
            else //adjustment row--------------------------------------------------------------------------------------
            if (type == TimeAdjust.class)
            {
                adjust = (TimeAdjust)mergedAL[i];
                curDate = adjust.TimeEntry.Date;
                if (curDate == previousDate)
                {
                    row.getCells().add("");
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(curDate.ToShortDateString());
                    row.getCells().Add(curDate.DayOfWeek.ToString());
                } 
                //altered--------------------------------------
                //Deprecated
                //status--------------------------------------
                //row.Cells.Add("");//3
                //in/out------------------------------------------
                row.getCells().add("");
                //4
                //time-----------------------------
                row.getCells().Add(adjust.TimeEntry.ToShortTimeString());
                //5
                //total-------------------------------
                row.getCells().add("");
                //
                //Adjust------------------------------
                daySpan += adjust.RegHours;
                //might be negative
                weekSpan += adjust.RegHours;
                periodSpan += adjust.RegHours;
                row.getCells().add(ClockEvents.format(adjust.RegHours));
                //6
                //Rate2-------------------------------
                row.getCells().add("");
                //
                //Overtime------------------------------
                otspan += adjust.OTimeHours;
                row.getCells().add(ClockEvents.format(adjust.OTimeHours));
                //7
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRow(i + 1,mergedAL) != curDate)
                {
                    //or the next row is a different date
                    row.getCells().add(ClockEvents.format(daySpan));
                    //
                    daySpan = new TimeSpan(0);
                }
                else
                {
                    row.getCells().add("");
                } 
                //Weekly-------------------------------------
                weeklyTotals[i] = weekSpan;
                //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRow(i + 1,mergedAL), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(adjust.TimeEntry.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    OpenDental.UI.ODGridCell cell = new OpenDental.UI.ODGridCell(ClockEvents.format(weekSpan));
                    cell.setColorText(Color.Black);
                    row.getCells().Add(cell);
                    weekSpan = new TimeSpan(0);
                }
                else
                {
                    row.getCells().add("");
                } 
                //Note-----------------------------------------
                row.getCells().add("(Adjust)" + adjust.Note);
                //used to indicate adjust rows.
                row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
            }
              
            gridTimeCard.getRows().add(row);
        }
        gridTimeCard.endUpdate();
        totalTime = periodSpan.ToStringHmm();
        overTime = otspan.ToStringHmm();
        rate2Time = rate2span.ToStringHmm();
        totalTime2 = periodSpan.TotalHours.ToString("n");
        overTime2 = otspan.TotalHours.ToString("n");
        rate2Time2 = rate2span.TotalHours.ToString("n");
        return gridTimeCard;
    }

    private DateTime getDateForRow(int i, ArrayList mergedAL) throws Exception {
        if (mergedAL[i].GetType() == ClockEvent.class)
        {
            return ((ClockEvent)mergedAL[i]).TimeDisplayed1.Date;
        }
        else if (mergedAL[i].GetType() == TimeAdjust.class)
        {
            return ((TimeAdjust)mergedAL[i]).TimeEntry.Date;
        }
          
        return DateTime.MinValue;
    }

    //Prints one timecard for each employee.
    private void butPrintAll_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        OpenDental.UI.FormPrintPreview pView = new OpenDental.UI.FormPrintPreview(PrintSituation.Default, pd, gridMain.getRows().Count, 0, "Employee timecards printed");
        pView.ShowDialog();
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        printEveryTimeCard(sender,e);
    }

    private void printEveryTimeCard(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        //A preview of every single emp on their own page will show up. User will print from there.
        Graphics g = e.Graphics;
        Employee employeeCur = Employees.GetEmp(PIn.Long(MainTable.Rows[pagesPrinted]["EmployeeNum"].ToString()));
        OpenDental.UI.ODGrid timeCardGrid = getGridForPrinting(employeeCur);
        int linesPrinted = 0;
        //Create a timecardgrid for this employee?
        float yPos = 75;
        float xPos = 55;
        String str = new String();
        Font font = new Font(FontFamily.GenericSansSerif, 8);
        Font fontTitle = new Font(FontFamily.GenericSansSerif, 11, FontStyle.Bold);
        Font fontHeader = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Bold);
        SolidBrush brush = new SolidBrush(Color.Black);
        Pen pen = new Pen(Color.Black);
        //Title
        str = employeeCur.FName + " " + employeeCur.LName;
        g.DrawString(str, fontTitle, brush, xPos, yPos);
        yPos += 30;
        //define columns
        int[] colW = new int[11];
        colW[0] = 70;
        //date
        colW[1] = 70;
        //weekday
        //colW[2]=50;//altered
        colW[2] = 60;
        //in
        colW[3] = 60;
        //out
        colW[4] = 50;
        //total
        colW[5] = 50;
        //adjust
        colW[6] = 50;
        //Rate2 //added
        colW[7] = 55;
        //overtime
        colW[8] = 50;
        //daily
        colW[9] = 50;
        //weekly
        colW[10] = 160;
        //note
        int[] colPos = new int[colW.Length + 1];
        colPos[0] = 45;
        for (int i = 1;i < colPos.Length;i++)
        {
            colPos[i] = colPos[i - 1] + colW[i - 1];
        }
        String[] ColCaption = new String[11];
        ColCaption[0] = Lan.g(this,"Date");
        ColCaption[1] = Lan.g(this,"Weekday");
        //ColCaption[2]=Lan.g(this,"Altered");
        ColCaption[2] = Lan.g(this,"In");
        ColCaption[3] = Lan.g(this,"Out");
        ColCaption[4] = Lan.g(this,"Total");
        ColCaption[5] = Lan.g(this,"Adjust");
        ColCaption[6] = Lan.g(this,"Rate 2");
        ColCaption[7] = Lan.g(this,"Overtime");
        ColCaption[8] = Lan.g(this,"Daily");
        ColCaption[9] = Lan.g(this,"Weekly");
        ColCaption[10] = Lan.g(this,"Note");
        //column headers-----------------------------------------------------------------------------------------
        e.Graphics.FillRectangle(Brushes.LightGray, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        e.Graphics.DrawRectangle(pen, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        for (int i = 1;i < colPos.Length;i++)
        {
            e.Graphics.DrawLine(new Pen(Color.Black), colPos[i], yPos, colPos[i], yPos + 18);
        }
        for (int i = 0;i < ColCaption.Length;i++)
        {
            //Prints the Column Titles
            e.Graphics.DrawString(ColCaption[i], fontHeader, brush, colPos[i] + 2, yPos + 1);
        }
        yPos += 18;
        while (yPos < e.PageBounds.Height - 75 - 50 - 32 - 16 && linesPrinted < timeCardGrid.getRows().Count)
        {
            for (int i = 0;i < colPos.Length - 1;i++)
            {
                e.Graphics.DrawString(timeCardGrid.getRows().get___idx(linesPrinted).getCells()[i].Text, font, brush, new RectangleF(colPos[i] + 2, yPos, colPos[i + 1] - colPos[i] - 5, font.GetHeight(e.Graphics)));
            }
            for (int i = 0;i < colPos.Length;i++)
            {
                //Column lines
                e.Graphics.DrawLine(Pens.Gray, colPos[i], yPos + 16, colPos[i], yPos);
            }
            linesPrinted++;
            yPos += 16;
            e.Graphics.DrawLine(new Pen(Color.Gray), colPos[0], yPos, colPos[colPos.Length - 1], yPos);
        }
        //totals will print on every page for simplicity
        yPos += 10;
        g.DrawString(Lan.g(this,"Regular Time") + ": " + totalTime + " (" + totalTime2 + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Overtime") + ": " + overTime + " (" + overTime2 + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Rate 2 Time") + ": " + rate2Time + " (" + rate2Time2 + ")", fontHeader, brush, xPos, yPos);
        pagesPrinted++;
        if (gridMain.getRows().Count == pagesPrinted)
        {
            pagesPrinted = 0;
            e.HasMorePages = false;
        }
        else
        {
            e.HasMorePages = true;
        } 
    }

    /**
    * Print timecards for selected employees only.
    */
    private void butPrintSelected_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd2_PrintPageSelective);
        OpenDental.UI.FormPrintPreview pView = new OpenDental.UI.FormPrintPreview(PrintSituation.Default, pd, gridMain.getSelectedIndices().Length, 0, "Employee timecards printed");
        pView.ShowDialog();
    }

    /**
    * Similar to pd2_PrintPage except it iterates through selected indices instead of all indices.
    */
    private void pd2_PrintPageSelective(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        printEmployeeTimeCard(sender,e);
    }

    private void printEmployeeTimeCard(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        //A preview of every single emp on their own page will show up. User will print from there.
        Graphics g = e.Graphics;
        Employee employeeCur = Employees.GetEmp(PIn.Long(MainTable.Rows[gridMain.getSelectedIndices()[pagesPrinted]]["EmployeeNum"].ToString()));
        OpenDental.UI.ODGrid timeCardGrid = getGridForPrinting(employeeCur);
        int linesPrinted = 0;
        //Create a timecardgrid for this employee?
        float yPos = 75;
        float xPos = 55;
        String str = new String();
        Font font = new Font(FontFamily.GenericSansSerif, 8);
        Font fontTitle = new Font(FontFamily.GenericSansSerif, 11, FontStyle.Bold);
        Font fontHeader = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Bold);
        SolidBrush brush = new SolidBrush(Color.Black);
        Pen pen = new Pen(Color.Black);
        //Title
        str = employeeCur.FName + " " + employeeCur.LName;
        g.DrawString(str, fontTitle, brush, xPos, yPos);
        yPos += 30;
        //define columns
        int[] colW = new int[11];
        colW[0] = 70;
        //date
        colW[1] = 70;
        //weekday
        //colW[2]=50;//altered
        colW[2] = 60;
        //in
        colW[3] = 60;
        //out
        colW[4] = 50;
        //total
        colW[5] = 50;
        //adjust
        colW[6] = 50;
        //Rate2 //added
        colW[7] = 55;
        //overtime
        colW[8] = 50;
        //daily
        colW[9] = 50;
        //weekly
        colW[10] = 160;
        //note
        int[] colPos = new int[colW.Length + 1];
        colPos[0] = 45;
        for (int i = 1;i < colPos.Length;i++)
        {
            colPos[i] = colPos[i - 1] + colW[i - 1];
        }
        String[] ColCaption = new String[11];
        ColCaption[0] = Lan.g(this,"Date");
        ColCaption[1] = Lan.g(this,"Weekday");
        //ColCaption[2]=Lan.g(this,"Altered");
        ColCaption[2] = Lan.g(this,"In");
        ColCaption[3] = Lan.g(this,"Out");
        ColCaption[4] = Lan.g(this,"Total");
        ColCaption[5] = Lan.g(this,"Adjust");
        ColCaption[6] = Lan.g(this,"Rate 2");
        ColCaption[7] = Lan.g(this,"Overtime");
        ColCaption[8] = Lan.g(this,"Daily");
        ColCaption[9] = Lan.g(this,"Weekly");
        ColCaption[10] = Lan.g(this,"Note");
        //column headers-----------------------------------------------------------------------------------------
        e.Graphics.FillRectangle(Brushes.LightGray, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        e.Graphics.DrawRectangle(pen, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        for (int i = 1;i < colPos.Length;i++)
        {
            e.Graphics.DrawLine(new Pen(Color.Black), colPos[i], yPos, colPos[i], yPos + 18);
        }
        for (int i = 0;i < ColCaption.Length;i++)
        {
            //Prints the Column Titles
            e.Graphics.DrawString(ColCaption[i], fontHeader, brush, colPos[i] + 2, yPos + 1);
        }
        yPos += 18;
        while (yPos < e.PageBounds.Height - 75 - 50 - 32 - 16 && linesPrinted < timeCardGrid.getRows().Count)
        {
            for (int i = 0;i < colPos.Length - 1;i++)
            {
                e.Graphics.DrawString(timeCardGrid.getRows().get___idx(linesPrinted).getCells()[i].Text, font, brush, new RectangleF(colPos[i] + 2, yPos, colPos[i + 1] - colPos[i] - 5, font.GetHeight(e.Graphics)));
            }
            for (int i = 0;i < colPos.Length;i++)
            {
                //Column lines
                e.Graphics.DrawLine(Pens.Gray, colPos[i], yPos + 16, colPos[i], yPos);
            }
            linesPrinted++;
            yPos += 16;
            e.Graphics.DrawLine(new Pen(Color.Gray), colPos[0], yPos, colPos[colPos.Length - 1], yPos);
        }
        //totals will print on every page for simplicity
        yPos += 10;
        g.DrawString(Lan.g(this,"Regular Time") + ": " + totalTime + " (" + totalTime2 + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Overtime") + ": " + overTime + " (" + overTime2 + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Rate 2 Time") + ": " + rate2Time + " (" + rate2Time2 + ")", fontHeader, brush, xPos, yPos);
        pagesPrinted++;
        if (gridMain.getSelectedIndices().Length == pagesPrinted)
        {
            pagesPrinted = 0;
            e.HasMorePages = false;
        }
        else
        {
            e.HasMorePages = true;
        } 
    }

    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
        if (SelectedPayPeriod == 0)
        {
            return ;
        }
         
        SelectedPayPeriod--;
        fillPayPeriod();
        fillMain();
    }

    private void butRight_Click(Object sender, EventArgs e) throws Exception {
        if (SelectedPayPeriod == PayPeriods.getList().Length - 1)
        {
            return ;
        }
         
        SelectedPayPeriod++;
        fillPayPeriod();
        fillMain();
    }

    private void butReport_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.UserQuery))
        {
            return ;
        }
         
        //Basically a preview of gridMain (every employee on one page), allow user to export as excel sheet or print it.
        String query = ClockEvents.getTimeCardManageCommand(DateStart,DateStop,true);
        //true to get extra columns for printing.
        ReportSimpleGrid rsg = new ReportSimpleGrid();
        rsg.Query = query;
        FormQuery FormQ = new FormQuery(rsg);
        FormQ.textQuery.Text = query;
        FormQ.submitQuery();
        FormQ.ShowDialog();
    }

    private void butDaily_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.TimecardsEditAll))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"No employees selected. Would you like to run calculations for all employees?"))
            {
                return ;
            }
             
            gridMain.setSelected(true);
        }
         
        Cursor = Cursors.WaitCursor;
        String aggregateErrors = "";
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            try
            {
                TimeCardRules.CalculateDailyOvertime(Employees.GetEmp(PIn.Long(MainTable.Rows[gridMain.getSelectedIndices()[i]]["EmployeeNum"].ToString())), PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
            }
            catch (Exception ex)
            {
                aggregateErrors += ex.Message + "\r\n";
            }
        
        }
        Cursor = Cursors.Default;
        //Cache selected indicies, fill grid, reselect indicies.
        List<int> listSelectedIndexCach = new List<int>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            listSelectedIndexCach.Add(gridMain.getSelectedIndices()[i]);
        }
        fillMain();
        for (int i = 0;i < listSelectedIndexCach.Count;i++)
        {
            gridMain.SetSelected(listSelectedIndexCach[i], true);
        }
        if (StringSupport.equals(aggregateErrors, ""))
        {
            MsgBox.show(this,"Done.");
        }
        else
        {
            MessageBox.Show(this, Lan.g(this,"Timecards were not calculated for some Employees for the following reasons") + ":\r\n" + aggregateErrors);
        } 
    }

    private void butWeekly_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.TimecardsEditAll))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"No employees selected. Would you like to run calculations for all employees?"))
            {
                return ;
            }
             
            gridMain.setSelected(true);
        }
         
        Cursor = Cursors.WaitCursor;
        String aggregateErrors = "";
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            try
            {
                TimeCardRules.CalculateWeeklyOvertime(Employees.GetEmp(PIn.Long(MainTable.Rows[gridMain.getSelectedIndices()[i]]["EmployeeNum"].ToString())), PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
            }
            catch (Exception ex)
            {
                aggregateErrors += ex.Message + "\r\n";
            }
        
        }
        Cursor = Cursors.Default;
        //Cache selected indices, fill grid, reselect indices.
        List<int> listSelectedIndexCach = new List<int>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            listSelectedIndexCach.Add(gridMain.getSelectedIndices()[i]);
        }
        fillMain();
        for (int i = 0;i < listSelectedIndexCach.Count;i++)
        {
            gridMain.SetSelected(listSelectedIndexCach[i], true);
        }
        //Done or Error messages.
        if (StringSupport.equals(aggregateErrors, ""))
        {
            MsgBox.show(this,"Done.");
        }
        else
        {
            MessageBox.Show(this, Lan.g(this,"Timecards were not calculated for some Employees for the following reasons") + ":\r\n" + aggregateErrors);
        } 
    }

    private void butClearManual_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"This cannot be undone. Would you like to continue?"))
        {
            return ;
        }
         
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //List<Employee> employeesList = new List<Employee>();
            try
            {
                TimeCardRules.ClearManual(PIn.Long(MainTable.Rows[gridMain.getSelectedIndices()[i]]["EmployeeNum"].ToString()), PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        
        }
        //Cach selected indicies, fill grid, reselect indicies.
        List<int> listSelectedIndexCach = new List<int>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            listSelectedIndexCach.Add(gridMain.getSelectedIndices()[i]);
        }
        fillMain();
        for (int i = 0;i < listSelectedIndexCach.Count;i++)
        {
            gridMain.SetSelected(listSelectedIndexCach[i], true);
        }
    }

    private void butClearAuto_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This cannot be undone, but you can run the Calc buttons again later.  Would you like to continue?"))
        {
            return ;
        }
         
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            try
            {
                TimeCardRules.ClearAuto(PIn.Long(MainTable.Rows[gridMain.getSelectedIndices()[i]]["EmployeeNum"].ToString()), PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        
        }
        //Cach selected indicies, fill grid, reselect indicies.
        List<int> listSelectedIndexCach = new List<int>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            listSelectedIndexCach.Add(gridMain.getSelectedIndices()[i]);
        }
        fillMain();
        for (int i = 0;i < listSelectedIndexCach.Count;i++)
        {
            gridMain.SetSelected(listSelectedIndexCach[i], true);
        }
    }

    /**
    * Print exactly what is showing in gridMain. (Including rows that do not fit in the UI.)
    */
    private void butPrintGrid_Click(Object sender, EventArgs e) throws Exception {
        PagesPrinted = 0;
        HeadingPrinted = false;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        //landscape
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Printed employee time card grid."))
        {
            return ;
        }
         
        try
        {
            pd.Print();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void pd_PrintPage(Object sender, PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        int y = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        int headingPrintH = 0;
        if (!HeadingPrinted)
        {
            text = Lan.g(this,"Heading Text");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, y);
            y += 25;
            HeadingPrinted = true;
            headingPrintH = y;
        }
         
        y = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        PagesPrinted++;
        if (y == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    /**
    * Exports MainTable (a data table) not the actual OD Grid. This allows for EmployeeNum and ADPNum without having to perform any lookups.
    */
    private void butExportGrid_Click(Object sender, EventArgs e) throws Exception {
        FolderBrowserDialog fbd = new FolderBrowserDialog();
        if (fbd.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        StringBuilder strb = new StringBuilder();
        String headers = "";
        for (int i = 0;i < MainTable.Columns.Count;i++)
        {
            headers += (i > 0 ? "\t" : "") + MainTable.Columns[i].ColumnName;
        }
        strb.AppendLine(headers);
        for (int i = 0;i < MainTable.Rows.Count;i++)
        {
            String row = "";
            for (int c = 0;c < MainTable.Columns.Count;c++)
            {
                if (c > 0)
                {
                    row += "\t";
                }
                 
                ColumnName __dummyScrutVar0 = MainTable.Columns[c].ColumnName;
                if (__dummyScrutVar0.equals("PayrollID") || __dummyScrutVar0.equals("EmployeeNum") || __dummyScrutVar0.equals("firstName") || __dummyScrutVar0.equals("lastName") || __dummyScrutVar0.equals("Note"))
                {
                    row += MainTable.Rows[i][c].ToString().Replace("\t", "").Replace("\r\n", ";  ");
                }
                else if (__dummyScrutVar0.equals("totalHours") || __dummyScrutVar0.equals("rate1Hours") || __dummyScrutVar0.equals("rate1OTHours") || __dummyScrutVar0.equals("rate2Hours") || __dummyScrutVar0.equals("rate2OTHours"))
                {
                    //Time must me formatted differently.
                    if (PrefC.getBool(PrefName.TimeCardsUseDecimalInsteadOfColon))
                    {
                        row += PIn.Time(MainTable.Rows[i][c].ToString()).TotalHours.ToString("n");
                    }
                    else
                    {
                        row += PIn.Time(MainTable.Rows[i][c].ToString()).ToStringHmm();
                    } 
                }
                else
                {
                    throw new Exception("Unexpected column found in payroll table : " + MainTable.Columns[c].ColumnName);
                }  
            }
            //should never happen.
            //end switch
            //end columns
            strb.AppendLine(row);
        }
        String fileName = "ODPayroll" + DateTime.Now.ToString("yyyyMMdd_hhmmss") + ".TXT";
        try
        {
            System.IO.File.WriteAllText(fbd.SelectedPath + "\\" + fileName, strb.ToString());
            MessageBox.Show(this, Lan.g(this,"File created") + " : " + fbd.SelectedPath + "\\" + fileName);
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, "File not created:\r\n" + ex.Message);
        }
    
    }

    /**
    * Validates format and values and provides aggregate error and warning messages. Will save malformed files anyways.
    */
    private void butExportADP_Click(Object sender, EventArgs e) throws Exception {
        StringBuilder strb = new StringBuilder();
        String errors = "";
        String warnings = "";
        String errorIndent = "  ";
        strb.AppendLine("Co Code,Batch ID,File #" + (PrefC.getBool(PrefName.TimeCardADPExportIncludesName) ? ",Employee Name" : "") + ",Rate Code,Reg Hours,O/T Hours");
        String coCode = PrefC.getString(PrefName.ADPCompanyCode);
        String batchID = DateStop.ToString("yyyyMMdd");
        //max 8 characters
        if (coCode.Length < 2 || coCode.Length > 3)
        {
            errors += errorIndent + "Company code must be two to three alpha numeric characters long.  Go to Setup>TimeCards to edit.\r\n";
        }
         
        coCode = coCode.PadRight(3, '_');
        for (int i = 0;i < MainTable.Rows.Count;i++)
        {
            //for two digit company codes.
            String errorsForEmployee = "";
            String warningsForEmployee = "";
            String fileNum = "";
            String employeeName = "";
            fileNum = MainTable.Rows[i]["PayrollID"].ToString();
            try
            {
                if (PIn.Int(fileNum) < 51 || PIn.Int(fileNum) > 999999)
                {
                    errorsForEmployee += errorIndent + "Payroll ID not between 51 and 999999.\r\n";
                }
                 
            }
            catch (Exception ex)
            {
                //same error message as above.
                errorsForEmployee += errorIndent + "Payroll ID not between 51 and 999999.\r\n";
            }

            if (fileNum.Length > 6)
            {
                errorsForEmployee += errorIndent + "Payroll ID must be less than 6 digits long.\r\n";
            }
            else
            {
                //pad payrollIDs that are too short. No effect if payroll ID is 6 digits long.
                fileNum = fileNum.PadLeft(6, '0');
            } 
            try
            {
                employeeName = Employees.GetNameFL(Employees.GetEmp(PIn.Long(MainTable.Rows[i]["EmployeeNum"].ToString())));
            }
            catch (Exception __dummyCatchVar0)
            {
                employeeName = "Error";
            }

            String r1hours = (PIn.TSpan(MainTable.Rows[i]["rate1Hours"].ToString())).TotalHours.ToString("F2");
            //adp allows 2 digit precision
            if (StringSupport.equals(r1hours, "0.00"))
            {
                //Was changing Exactly 80.00 hours with 8 hours.
                r1hours = "";
            }
             
            String r1OThours = (PIn.TSpan(MainTable.Rows[i]["rate1OTHours"].ToString())).TotalHours.ToString("F2");
            //adp allows 2 digit precision
            if (StringSupport.equals(r1OThours, "0.00"))
            {
                r1OThours = "";
            }
             
            String r2hours = (PIn.TSpan(MainTable.Rows[i]["rate2Hours"].ToString())).TotalHours.ToString("F2");
            //adp allows 2 digit precision
            if (StringSupport.equals(r2hours, "0.00"))
            {
                r2hours = "";
            }
             
            String r2OThours = (PIn.TSpan(MainTable.Rows[i]["rate2OTHours"].ToString())).TotalHours.ToString("F2");
            //adp allows 2 digit precision
            if (StringSupport.equals(r2OThours, "0.00"))
            {
                r2OThours = "";
            }
             
            String textToAdd = "";
            if (!StringSupport.equals(r1hours, "") || !StringSupport.equals(r1OThours, ""))
            {
                //no entry should be made unless there are actually hours for this employee.
                textToAdd += coCode + "," + batchID + "," + fileNum + (PrefC.getBool(PrefName.TimeCardADPExportIncludesName) ? "," + employeeName : "") + ",," + r1hours + "," + r1OThours + "\r\n";
            }
             
            if (!StringSupport.equals(r2hours, "") || !StringSupport.equals(r2OThours, ""))
            {
                //no entry should be made unless there are actually hours for this employee.
                textToAdd += coCode + "," + batchID + "," + fileNum + (PrefC.getBool(PrefName.TimeCardADPExportIncludesName) ? "," + employeeName : "") + ",2," + r2hours + "," + r2OThours + "\r\n";
            }
             
            if (StringSupport.equals(textToAdd, ""))
            {
                warningsForEmployee += errorIndent + "No clocked hours.\r\n";
            }
            else
            {
                // for "+Employees.GetNameFL(Employees.GetEmp(PIn.Long(MainTable.Rows[i]["EmployeeNum"].ToString())))+"\r\n";
                strb.Append(textToAdd);
            } 
            for (int j = 0;j < textToAdd.Length;j++)
            {
                //validate characters in text.  Allowed values are 32 to 91 and 93 to 122----------------------------------------------------------------
                int charAsInt = (int)textToAdd[j];
                //these are the characters explicitly allowed by ADP per thier documentation.
                if (charAsInt >= 32 && charAsInt <= 122 && charAsInt != 92)
                {
                    continue;
                }
                 
                //
                //valid character
                if (charAsInt == 10 || charAsInt == 13)
                {
                    continue;
                }
                 
                //CR LF, not allowed as values but allowed to deliniate rows.
                //valid character
                errorsForEmployee += "Invalid character found (ASCII=" + charAsInt + "): " + textToAdd.Substring(j, 1) + ".\r\n";
            }
            //Aggregate employee errors into aggregate error messages.--------------------------------------------------------------------------------
            if (!StringSupport.equals(errorsForEmployee, ""))
            {
                errors += Employees.GetNameFL(Employees.GetEmp(PIn.Long(MainTable.Rows[i]["EmployeeNum"].ToString()))) + ":\r\n" + errorsForEmployee + "\r\n";
            }
             
            if (!StringSupport.equals(warningsForEmployee, ""))
            {
                warnings += Employees.GetNameFL(Employees.GetEmp(PIn.Long(MainTable.Rows[i]["EmployeeNum"].ToString()))) + ":\r\n" + warningsForEmployee + "\r\n";
            }
             
        }
        FolderBrowserDialog fbd = new FolderBrowserDialog();
        if (fbd.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        String fileSuffix = "";
        for (int i = 0;i <= 1297;i++)
        {
            //1296=36*36 to represent all acceptable suffixes for file name consisting of two alphanumeric digits; +1 to catch error. (A-Z, 0-9)
            fileSuffix = "";
            //generate suffix from i
            if (i == 1297)
            {
                //could not find acceptable file name.
                fileSuffix = "NamingError";
                break;
            }
             
            if (i / 36 < 10)
            {
                fileSuffix += (i / 36);
            }
            else
            {
                //truncated to int on purpose.  (0 to 9)
                fileSuffix += (Char)((i / 36) - 10 + 65);
            } 
            //65='A' in ASCII.  (A to Z)
            if (i % 36 < 10)
            {
                fileSuffix += (i % 36);
            }
            else
            {
                //(0 to 9)
                fileSuffix += (Char)((i % 36) - 10 + 65);
            } 
            //65='A' in ASCII.  (A to Z)
            //File suffix is now a a two digit alphanumeric string.
            if (!System.IO.File.Exists(fbd.SelectedPath + "\\EPI" + coCode + fileSuffix + ".CSV"))
            {
                break;
            }
             
        }
        try
        {
            System.IO.File.WriteAllText(fbd.SelectedPath + "\\EPI" + coCode + fileSuffix + ".CSV", strb.ToString());
            if (!StringSupport.equals(errors, ""))
            {
                MessageBox.Show("The following errors will prevent ADP from properly processing this export:\r\n" + errors);
            }
             
            if (!StringSupport.equals(warnings, ""))
            {
                MessageBox.Show("The following warnings were detected:\r\n" + warnings);
            }
             
            MessageBox.Show(this, Lan.g(this,"File created") + " : " + fbd.SelectedPath + "\\EPI" + coCode + fileSuffix + ".CSV");
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, "File not created:\r\n" + ex.Message);
        }
    
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textDatePaycheck = new System.Windows.Forms.TextBox();
        this.textDateStop = new System.Windows.Forms.TextBox();
        this.textDateStart = new System.Windows.Forms.TextBox();
        this.butRight = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butDaily = new OpenDental.UI.Button();
        this.butCompute = new OpenDental.UI.Button();
        this.butPrintAll = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butPrintGrid = new OpenDental.UI.Button();
        this.butExportADP = new OpenDental.UI.Button();
        this.butClearAuto = new OpenDental.UI.Button();
        this.butClearManual = new OpenDental.UI.Button();
        this.butPrintSelected = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.butExportGrid = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textDatePaycheck);
        this.groupBox1.Controls.Add(this.textDateStop);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.butRight);
        this.groupBox1.Controls.Add(this.butLeft);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(12, 9);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(659, 51);
        this.groupBox1.TabIndex = 15;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Pay Period";
        //
        // textDatePaycheck
        //
        this.textDatePaycheck.Location = new System.Drawing.Point(473, 19);
        this.textDatePaycheck.Name = "textDatePaycheck";
        this.textDatePaycheck.ReadOnly = true;
        this.textDatePaycheck.Size = new System.Drawing.Size(100, 20);
        this.textDatePaycheck.TabIndex = 14;
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(244, 28);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.ReadOnly = true;
        this.textDateStop.Size = new System.Drawing.Size(100, 20);
        this.textDateStop.TabIndex = 13;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(244, 8);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.ReadOnly = true;
        this.textDateStart.Size = new System.Drawing.Size(100, 20);
        this.textDateStart.TabIndex = 12;
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(63, 18);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(39, 24);
        this.butRight.TabIndex = 11;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(13, 18);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(39, 24);
        this.butLeft.TabIndex = 10;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(354, 19);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(117, 18);
        this.label4.TabIndex = 9;
        this.label4.Text = "Paycheck Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(146, 8);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(96, 18);
        this.label2.TabIndex = 6;
        this.label2.Text = "Start Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(143, 28);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(99, 18);
        this.label3.TabIndex = 8;
        this.label3.Text = "End Date";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 66);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(931, 562);
        this.gridMain.TabIndex = 16;
        this.gridMain.setTitle("Employee Time Cards");
        this.gridMain.setTranslationName("TableTimeCard");
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
        // butDaily
        //
        this.butDaily.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDaily.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDaily.setAutosize(true);
        this.butDaily.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDaily.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDaily.setCornerRadius(4F);
        this.butDaily.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDaily.Location = new System.Drawing.Point(6, 18);
        this.butDaily.Name = "butDaily";
        this.butDaily.Size = new System.Drawing.Size(69, 24);
        this.butDaily.TabIndex = 119;
        this.butDaily.Text = "Daily";
        this.butDaily.Click += new System.EventHandler(this.butDaily_Click);
        //
        // butCompute
        //
        this.butCompute.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCompute.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCompute.setAutosize(true);
        this.butCompute.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCompute.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCompute.setCornerRadius(4F);
        this.butCompute.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCompute.Location = new System.Drawing.Point(81, 18);
        this.butCompute.Name = "butCompute";
        this.butCompute.Size = new System.Drawing.Size(72, 24);
        this.butCompute.TabIndex = 118;
        this.butCompute.Text = "Weekly";
        this.butCompute.Click += new System.EventHandler(this.butWeekly_Click);
        //
        // butPrintAll
        //
        this.butPrintAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrintAll.setAutosize(true);
        this.butPrintAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintAll.setCornerRadius(4F);
        this.butPrintAll.Image = Resources.getbutPrint();
        this.butPrintAll.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintAll.Location = new System.Drawing.Point(6, 18);
        this.butPrintAll.Name = "butPrintAll";
        this.butPrintAll.Size = new System.Drawing.Size(87, 24);
        this.butPrintAll.TabIndex = 116;
        this.butPrintAll.Text = "&Print All";
        this.butPrintAll.Click += new System.EventHandler(this.butPrintAll_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(868, 652);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butPrintGrid
        //
        this.butPrintGrid.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrintGrid.setAutosize(true);
        this.butPrintGrid.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintGrid.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintGrid.setCornerRadius(4F);
        this.butPrintGrid.Image = Resources.getbutPrint();
        this.butPrintGrid.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintGrid.Location = new System.Drawing.Point(6, 18);
        this.butPrintGrid.Name = "butPrintGrid";
        this.butPrintGrid.Size = new System.Drawing.Size(96, 24);
        this.butPrintGrid.TabIndex = 120;
        this.butPrintGrid.Text = "Print Grid";
        this.butPrintGrid.Click += new System.EventHandler(this.butPrintGrid_Click);
        //
        // butExportADP
        //
        this.butExportADP.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExportADP.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExportADP.setAutosize(true);
        this.butExportADP.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExportADP.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExportADP.setCornerRadius(4F);
        this.butExportADP.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExportADP.Location = new System.Drawing.Point(196, 18);
        this.butExportADP.Name = "butExportADP";
        this.butExportADP.Size = new System.Drawing.Size(79, 24);
        this.butExportADP.TabIndex = 121;
        this.butExportADP.Text = "Export ADP";
        this.butExportADP.Click += new System.EventHandler(this.butExportADP_Click);
        //
        // butClearAuto
        //
        this.butClearAuto.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearAuto.setAutosize(true);
        this.butClearAuto.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearAuto.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearAuto.setCornerRadius(4F);
        this.butClearAuto.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClearAuto.Location = new System.Drawing.Point(677, 36);
        this.butClearAuto.Name = "butClearAuto";
        this.butClearAuto.Size = new System.Drawing.Size(117, 24);
        this.butClearAuto.TabIndex = 122;
        this.butClearAuto.Text = "Clear Auto Adjusts";
        this.butClearAuto.Click += new System.EventHandler(this.butClearAuto_Click);
        //
        // butClearManual
        //
        this.butClearManual.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearManual.setAutosize(true);
        this.butClearManual.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearManual.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearManual.setCornerRadius(4F);
        this.butClearManual.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClearManual.Location = new System.Drawing.Point(677, 9);
        this.butClearManual.Name = "butClearManual";
        this.butClearManual.Size = new System.Drawing.Size(117, 24);
        this.butClearManual.TabIndex = 123;
        this.butClearManual.Text = "Clear Manual Adjusts";
        this.butClearManual.Click += new System.EventHandler(this.butClearManual_Click);
        //
        // butPrintSelected
        //
        this.butPrintSelected.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintSelected.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrintSelected.setAutosize(true);
        this.butPrintSelected.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintSelected.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintSelected.setCornerRadius(4F);
        this.butPrintSelected.Image = Resources.getbutPrint();
        this.butPrintSelected.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintSelected.Location = new System.Drawing.Point(99, 18);
        this.butPrintSelected.Name = "butPrintSelected";
        this.butPrintSelected.Size = new System.Drawing.Size(109, 24);
        this.butPrintSelected.TabIndex = 124;
        this.butPrintSelected.Text = "Print Selected";
        this.butPrintSelected.Click += new System.EventHandler(this.butPrintSelected_Click);
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox2.Controls.Add(this.butDaily);
        this.groupBox2.Controls.Add(this.butCompute);
        this.groupBox2.Location = new System.Drawing.Point(12, 634);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(160, 48);
        this.groupBox2.TabIndex = 16;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Calculations";
        //
        // groupBox3
        //
        this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox3.Controls.Add(this.butPrintAll);
        this.groupBox3.Controls.Add(this.butPrintSelected);
        this.groupBox3.Location = new System.Drawing.Point(178, 634);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(215, 48);
        this.groupBox3.TabIndex = 125;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Time Cards";
        //
        // groupBox4
        //
        this.groupBox4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox4.Controls.Add(this.butExportGrid);
        this.groupBox4.Controls.Add(this.butPrintGrid);
        this.groupBox4.Controls.Add(this.butExportADP);
        this.groupBox4.Location = new System.Drawing.Point(497, 634);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(282, 48);
        this.groupBox4.TabIndex = 126;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Payroll Reports";
        //
        // butExportGrid
        //
        this.butExportGrid.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExportGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExportGrid.setAutosize(true);
        this.butExportGrid.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExportGrid.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExportGrid.setCornerRadius(4F);
        this.butExportGrid.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExportGrid.Location = new System.Drawing.Point(108, 18);
        this.butExportGrid.Name = "butExportGrid";
        this.butExportGrid.Size = new System.Drawing.Size(82, 24);
        this.butExportGrid.TabIndex = 127;
        this.butExportGrid.Text = "Export Grid";
        this.butExportGrid.Click += new System.EventHandler(this.butExportGrid_Click);
        //
        // FormTimeCardManage
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(955, 692);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butClearManual);
        this.Controls.Add(this.butClearAuto);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butClose);
        this.Name = "FormTimeCardManage";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Time Card Manage";
        this.Load += new System.EventHandler(this.FormTimeCardManage_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.groupBox4.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textDatePaycheck = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateStop = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateStart = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butLeft;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butPrintAll;
    private OpenDental.UI.Button butDaily;
    private OpenDental.UI.Button butCompute;
    private OpenDental.UI.Button butPrintGrid;
    private OpenDental.UI.Button butExportADP;
    private OpenDental.UI.Button butClearAuto;
    private OpenDental.UI.Button butClearManual;
    private OpenDental.UI.Button butPrintSelected;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butExportGrid;
}


