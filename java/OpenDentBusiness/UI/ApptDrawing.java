//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness.UI;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SchedStatus;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.UI.ApptDrawing;
import OpenDentBusiness.UI.ApptSingleDrawing;

public class ApptDrawing   
{
    /**
    * Stores the shading info for the provider bars on the left of the appointments module
    */
    public static int[][] ProvBar = new int[][]();
    /**
    * The width of each operatory.
    */
    public static float ColWidth = new float();
    /**
    * 
    */
    public static float TimeWidth = 37;
    /**
    * 
    */
    public static float ProvWidth = 8;
    /**
    * Line height.  This is currently treated like a constant that the user has no control over.
    */
    public static int LineH = 12;
    /**
    * The number of columns.  Stays consistent even if weekly view.  The number of colums showing for one day.
    */
    public static int ColCount = new int();
    /**
    * 
    */
    public static int ProvCount = new int();
    /**
    * Based on the view.  If no view, then it is set to 1. Different computers can be showing different views.
    */
    public static int RowsPerIncr = new int();
    /**
    * Pulled from Prefs AppointmentTimeIncrement.  Either 5, 10, or 15. An increment can be one or more rows.
    */
    public static int MinPerIncr = new int();
    /**
    * Typical values would be 10,15,5,or 7.5.
    */
    public static int MinPerRow = new int();
    /**
    * Rows per hour, based on RowsPerIncr and MinPerIncr
    */
    public static int RowsPerHr = new int();
    /**
    * This gets set externally each time the module is selected.  It is the background schedule for the entire period.  Includes all types.
    */
    public static List<Schedule> SchedListPeriod = new List<Schedule>();
    /**
    * 
    */
    public static boolean IsWeeklyView = new boolean();
    /**
    * Typically 5 or 7. Only used with weekview.
    */
    public static int NumOfWeekDaysToDisplay = 7;
    /**
    * The width of an entire day if using week view.
    */
    public static float ColDayWidth = new float();
    /**
    * Only used with weekview. The width of individual appointments within each day.  There might be rounding errors for now.
    */
    public static float ColAptWidth = new float();
    //these two are subsets of provs and ops. You can't include hidden prov or op in this list.
    /**
    * Visible provider bars in appt module.  This is a subset of the available provs.  You can't include a hidden prov in this list.
    */
    public static List<Provider> VisProvs = new List<Provider>();
    /**
    * Visible ops in appt module.  List of visible operatories.  This is a subset of the available ops.  You can't include a hidden op in this list.  If user has set View.OnlyScheduledProvs, and not isWeekly, then the only ops to show will be for providers that have schedules for the day and ops with no provs assigned.
    */
    public static List<Operatory> VisOps = new List<Operatory>();
    /**
    * 
    */
    public static float ApptSheetHeight = new float();
    /**
    * 
    */
    public static float ApptSheetWidth = new float();
    /**
    * Draws the entire Appt background.  Used for main Appt module, for printing, and for mobile app.  Pass start and stop times of 12AM for 24 hours.  Set colsPerPage to VisOps.Count unless printing.  Set pageColumn to 0 unless printing.  Default fontSize is 8 unless printing.
    */
    public static void drawAllButAppts(Graphics g, boolean showRedTimeLine, DateTime startTime, DateTime stopTime, int colsPerPage, int pageColumn, int fontSize, boolean isPrinting) throws Exception {
        //This will clear up the screen if the user clicked on a day where no providers are scheduled or any other scenario in which ColWidth will be 0.
        g.FillRectangle(new SolidBrush(SystemColors.Control), 0, 0, ApptSheetWidth, ApptSheetHeight);
        g.FillRectangle(new SolidBrush(Color.LightGray), 0, 0, TimeWidth, ApptSheetHeight);
        //L time bar
        g.FillRectangle(new SolidBrush(Color.LightGray), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, 0, TimeWidth, ApptSheetHeight);
        //R time bar
        drawMainBackground(g,startTime,stopTime,colsPerPage,pageColumn);
        drawBlockouts(g,startTime,stopTime,colsPerPage,pageColumn,fontSize,isPrinting);
        if (!IsWeeklyView)
        {
            drawProvScheds(g,startTime,stopTime);
            drawProvBars(g,startTime,stopTime);
        }
         
        drawGridLines(g);
        if (showRedTimeLine)
        {
            drawRedTimeIndicator(g);
        }
         
        drawMinutes(g,startTime,stopTime);
    }

    /**
    * Including the practice schedule.
    */
    public static void drawMainBackground(Graphics g, DateTime startTime, DateTime stopTime, int colsPerPage, int pageColumn) throws Exception {
        Brush openBrush = new Brush();
        Brush closedBrush = new Brush();
        Brush holidayBrush = new Brush();
        try
        {
            openBrush = new SolidBrush(DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][0].ItemColor);
            closedBrush = new SolidBrush(DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][1].ItemColor);
            holidayBrush = new SolidBrush(DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][3].ItemColor);
        }
        catch (Exception __dummyCatchVar0)
        {
            //this is just for design-time
            openBrush = new SolidBrush(Color.White);
            closedBrush = new SolidBrush(Color.LightGray);
            holidayBrush = new SolidBrush(Color.FromArgb(255, 128, 128));
        }

        List<Schedule> schedsForOp = new List<Schedule>();
        //one giant rectangle for everything closed
        g.FillRectangle(closedBrush, TimeWidth, 0, ColWidth * ColCount + ProvWidth * ProvCount, ApptSheetHeight);
        //then, loop through each day and operatory
        boolean isHoliday = new boolean();
        int startHour = startTime.Hour;
        int stopHour = stopTime.Hour;
        if (stopHour == 0)
        {
            stopHour = 24;
        }
         
        if (IsWeeklyView)
        {
            for (int d = 0;d < NumOfWeekDaysToDisplay;d++)
            {
                isHoliday = false;
                for (int i = 0;i < SchedListPeriod.Count;i++)
                {
                    if (SchedListPeriod[i].SchedType != ScheduleType.Practice)
                    {
                        continue;
                    }
                     
                    if (SchedListPeriod[i].Status != SchedStatus.Holiday)
                    {
                        continue;
                    }
                     
                    if ((int)SchedListPeriod[i].SchedDate.DayOfWeek != d + 1)
                    {
                        continue;
                    }
                     
                    isHoliday = true;
                    break;
                }
                if (isHoliday)
                {
                    g.FillRectangle(holidayBrush, TimeWidth + 1 + d * ColDayWidth, 0, ColDayWidth, ApptSheetHeight);
                }
                 
                //this is a workaround because we start on Monday:
                DayOfWeek dayofweek = new DayOfWeek();
                if (d == 6)
                {
                    dayofweek = (DayOfWeek)(0);
                }
                else
                {
                    dayofweek = (DayOfWeek)(d + 1);
                } 
                for (int i = 0;i < ColCount;i++)
                {
                    schedsForOp = Schedules.GetSchedsForOp(SchedListPeriod, dayofweek, VisOps[i]);
                    for (int j = 0;j < schedsForOp.Count;j++)
                    {
                        //OperatoryC.ListShort[ApptViewItemL.VisOps[j]]);
                        stopHour = stopTime.Hour;
                        //Reset stopHour every time.
                        if (stopHour == 0)
                        {
                            stopHour = 24;
                        }
                         
                        //6RowsPerHr 10MinPerRow
                        //6
                        g.FillRectangle(openBrush, TimeWidth + 1 + d * ColDayWidth + (float)i * ColAptWidth, (schedsForOp[j].StartTime.Hours - startHour) * LineH * RowsPerHr + (int)schedsForOp[j].StartTime.Minutes * LineH / MinPerRow, ColAptWidth, (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Hours * LineH * RowsPerHr + (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Minutes * LineH / MinPerRow);
                    }
                }
            }
        }
        else
        {
            //10
            //only one day showing
            isHoliday = false;
            for (int i = 0;i < SchedListPeriod.Count;i++)
            {
                //Schedule[] schedForType;
                if (SchedListPeriod[i].SchedType != ScheduleType.Practice)
                {
                    continue;
                }
                 
                if (SchedListPeriod[i].Status != SchedStatus.Holiday)
                {
                    continue;
                }
                 
                isHoliday = true;
                break;
            }
            if (isHoliday)
            {
                g.FillRectangle(holidayBrush, TimeWidth + 1, 0, ColWidth * ColCount + ProvWidth * ProvCount, ApptSheetHeight);
            }
             
            for (int i = 0;i < colsPerPage;i++)
            {
                if (i == ApptDrawing.VisOps.Count)
                {
                    break;
                }
                 
                int k = colsPerPage * pageColumn + i;
                if (k >= ApptDrawing.VisOps.Count)
                {
                    break;
                }
                 
                schedsForOp = Schedules.GetSchedsForOp(SchedListPeriod, VisOps[k]);
                for (int j = 0;j < schedsForOp.Count;j++)
                {
                    //OperatoryC.ListShort[ApptViewItemL.VisOps[j]]);
                    stopHour = stopTime.Hour;
                    //Reset stopHour every time.
                    if (stopHour == 0)
                    {
                        stopHour = 24;
                    }
                     
                    if (schedsForOp[j].StartTime.Hours >= stopHour)
                    {
                        continue;
                    }
                     
                    if (schedsForOp[j].StopTime.Hours <= stopHour)
                    {
                        stopHour = schedsForOp[j].StopTime.Hours;
                    }
                     
                    //6RowsPerHr 10MinPerRow
                    //6
                    g.FillRectangle(openBrush, TimeWidth + ProvWidth * ProvCount + i * ColWidth, (schedsForOp[j].StartTime.Hours - startHour) * LineH * RowsPerHr + (int)schedsForOp[j].StartTime.Minutes * LineH / MinPerRow, ColWidth, (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Hours * LineH * RowsPerHr + (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Minutes * LineH / MinPerRow);
                }
                for (int j = 0;j < schedsForOp.Count;j++)
                {
                    //10
                    //now, fill up to 2 timebars along the left side of each rectangle.
                    if (schedsForOp[j].Ops.Count == 0)
                    {
                        continue;
                    }
                     
                    //if this schedule is not assigned to specific ops, skip
                    stopHour = stopTime.Hour;
                    //Reset stopHour every time.
                    if (stopHour == 0)
                    {
                        stopHour = 24;
                    }
                     
                    if (!Providers.GetIsSec(schedsForOp[j].ProvNum))
                    {
                        //if the provider is a dentist
                        //6RowsPerHr 10MinPerRow
                        //6
                        g.FillRectangle(new SolidBrush(Providers.GetColor(schedsForOp[j].ProvNum)), TimeWidth + ProvWidth * ProvCount + i * ColWidth, (schedsForOp[j].StartTime.Hours - startHour) * LineH * RowsPerHr + (int)schedsForOp[j].StartTime.Minutes * LineH / MinPerRow, ProvWidth, (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Hours * LineH * RowsPerHr + (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Minutes * LineH / MinPerRow);
                    }
                    else
                    {
                        //10
                        //hygienist
                        //6RowsPerHr 10MinPerRow
                        //6
                        g.FillRectangle(new SolidBrush(Providers.GetColor(schedsForOp[j].ProvNum)), TimeWidth + ProvWidth * ProvCount + i * ColWidth + ProvWidth, (schedsForOp[j].StartTime.Hours - startHour) * LineH * RowsPerHr + (int)schedsForOp[j].StartTime.Minutes * LineH / MinPerRow, ProvWidth, (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Hours * LineH * RowsPerHr + (schedsForOp[j].StopTime - schedsForOp[j].StartTime).Minutes * LineH / MinPerRow);
                    } 
                }
            }
        } 
        //10
        openBrush.Dispose();
        closedBrush.Dispose();
        holidayBrush.Dispose();
    }

    /**
    * Draws all the blockouts for the entire period.
    */
    public static void drawBlockouts(Graphics g, DateTime startTime, DateTime stopTime, int colsPerPage, int pageColumn, int fontSize, boolean isPrinting) throws Exception {
        Schedule[] schedForType = Schedules.GetForType(SchedListPeriod, ScheduleType.Blockout, 0);
        SolidBrush blockBrush = new SolidBrush();
        Pen blockOutlinePen = new Pen(Color.Black, 1);
        Pen penOutline = new Pen();
        Font blockFont = new Font("Arial", fontSize);
        String blockText = new String();
        RectangleF rect = new RectangleF();
        for (int i = 0;i < schedForType.Length;i++)
        {
            blockBrush = new SolidBrush(DefC.GetColor(DefCat.BlockoutTypes, schedForType[i].BlockoutType));
            penOutline = new Pen(DefC.GetColor(DefCat.BlockoutTypes, schedForType[i].BlockoutType), 2);
            blockText = DefC.GetName(DefCat.BlockoutTypes, schedForType[i].BlockoutType) + "\r\n" + schedForType[i].Note;
            for (int o = 0;o < schedForType[i].Ops.Count;o++)
            {
                int startHour = startTime.Hour;
                if (isPrinting)
                {
                    //Filtering logic for printing.
                    int stopHour = stopTime.Hour;
                    if (stopHour == 0)
                    {
                        stopHour = 24;
                    }
                     
                    if (schedForType[i].StartTime.Hours >= stopHour)
                    {
                        continue;
                    }
                     
                    //Blockout starts after the current time frame.
                    if (schedForType[i].StopTime.Hours <= stopHour)
                    {
                        stopHour = schedForType[i].StopTime.Hours;
                    }
                     
                    if (GetIndexOp(schedForType[i].Ops[o]) >= (colsPerPage * pageColumn + colsPerPage) || GetIndexOp(schedForType[i].Ops[o]) < colsPerPage * pageColumn)
                    {
                        continue;
                    }
                     
                }
                 
                //Blockout not on current page.
                if (IsWeeklyView)
                {
                    if (GetIndexOp(schedForType[i].Ops[o]) == -1)
                    {
                        continue;
                    }
                     
                    //don't display if op not visible
                    //this is a workaround because we start on Monday:
                    int dayofweek = (int)schedForType[i].SchedDate.DayOfWeek - 1;
                    if (dayofweek == -1)
                    {
                        dayofweek = 6;
                    }
                     
                    rect = new RectangleF(TimeWidth + 1 + (dayofweek)/* [UNSUPPORTED] the pointer indirection operator is not supported "*ColDayWidth" */ + ColAptWidth * (GetIndexOp(schedForType[i].Ops[o], VisOps) - (colsPerPage * pageColumn)), (schedForType[i].StartTime.Hours - startHour) * LineH * RowsPerHr + schedForType[i].StartTime.Minutes * LineH / MinPerRow, ColAptWidth - 1, (schedForType[i].StopTime - schedForType[i].StartTime).Hours * LineH * RowsPerHr + (schedForType[i].StopTime - schedForType[i].StartTime).Minutes * LineH / MinPerRow);
                }
                else
                {
                    if (GetIndexOp(schedForType[i].Ops[o]) == -1)
                    {
                        continue;
                    }
                     
                    //don't display if op not visible
                    //so they don't overlap prov bars
                    rect = new RectangleF(TimeWidth + ProvWidth * ProvCount + ColWidth * (GetIndexOp(schedForType[i].Ops[o], VisOps) - (colsPerPage * pageColumn)) + ProvWidth * 2, (schedForType[i].StartTime.Hours - startHour) * LineH * RowsPerHr + schedForType[i].StartTime.Minutes * LineH / MinPerRow, ColWidth - 1 - ProvWidth * 2, (schedForType[i].StopTime - schedForType[i].StartTime).Hours * LineH * RowsPerHr + (schedForType[i].StopTime - schedForType[i].StartTime).Minutes * LineH / MinPerRow);
                } 
                //paint either solid block or outline
                if (PrefC.getBool(PrefName.SolidBlockouts))
                {
                    g.FillRectangle(blockBrush, rect);
                    g.DrawLine(blockOutlinePen, rect.X, rect.Y + 1, rect.Right - 1, rect.Y + 1);
                }
                else
                {
                    g.DrawRectangle(penOutline, rect.X + 1, rect.Y + 2, rect.Width - 2, rect.Height - 3);
                } 
                g.DrawString(blockText, blockFont, new SolidBrush(DefC.getShort()[((Enum)DefCat.AppointmentColors).ordinal()][4].ItemColor), rect);
            }
            blockBrush.Dispose();
            penOutline.Dispose();
        }
        blockOutlinePen.Dispose();
    }

    /**
    * Returns the index of the opNum within VisOps.  Returns -1 if not in VisOps.
    */
    public static int getIndexOp(long opNum, List<Operatory> VisOps) throws Exception {
        for (int i = 0;i < VisOps.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (VisOps[i].OperatoryNum == opNum)
                return i;
             
        }
        return -1;
    }

    /**
    * The background provider schedules for the provider bars on the left.
    */
    public static void drawProvScheds(Graphics g, DateTime startTime, DateTime stopTime) throws Exception {
        Brush openBrush = new Brush();
        try
        {
            openBrush = new SolidBrush(DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][0].ItemColor);
        }
        catch (Exception __dummyCatchVar1)
        {
            //this is just for design-time
            openBrush = new SolidBrush(Color.White);
        }

        Provider provCur;
        Schedule[] schedForType = new Schedule[]();
        int startHour = startTime.Hour;
        int stopHour = stopTime.Hour;
        if (stopHour == 0)
        {
            stopHour = 24;
        }
         
        for (int j = 0;j < VisProvs.Count;j++)
        {
            provCur = VisProvs[j];
            schedForType = Schedules.getForType(SchedListPeriod,ScheduleType.Provider,provCur.ProvNum);
            for (int i = 0;i < schedForType.Length;i++)
            {
                stopHour = stopTime.Hour;
                //Reset stopHour every time.
                if (stopHour == 0)
                {
                    stopHour = 24;
                }
                 
                if (schedForType[i].StartTime.Hours >= stopHour)
                {
                    continue;
                }
                 
                if (schedForType[i].StopTime.Hours <= stopHour)
                {
                    stopHour = schedForType[i].StopTime.Hours;
                }
                 
                //6
                //10
                //6
                g.FillRectangle(openBrush, TimeWidth + ProvWidth * j, (schedForType[i].StartTime.Hours - startHour) * LineH * RowsPerHr + (int)schedForType[i].StartTime.Minutes * LineH / MinPerRow, ProvWidth, (schedForType[i].StopTime - schedForType[i].StartTime).Hours * LineH * RowsPerHr + (schedForType[i].StopTime - schedForType[i].StartTime).Minutes * LineH / MinPerRow);
            }
        }
        //10
        openBrush.Dispose();
    }

    /**
    * Not the schedule, but just the indicators of scheduling.
    */
    public static void drawProvBars(Graphics g, DateTime startTime, DateTime stopTime) throws Exception {
        int startingPoint = startTime.Hour * RowsPerHr;
        int stopHour = stopTime.Hour;
        if (stopHour == 0)
        {
            stopHour = 24;
        }
         
        int endingPoint = stopHour * RowsPerHr;
        for (int j = 0;j < ProvBar.Length;j++)
        {
            for (int i = 0;i < 24 * RowsPerHr;i++)
            {
                if (i < startingPoint)
                {
                    continue;
                }
                 
                if (i >= endingPoint)
                {
                    break;
                }
                 
                System.Array'1.INDEXER.INDEXER __dummyScrutVar0 = ProvBar[j][i];
                if (__dummyScrutVar0.equals(0))
                {
                }
                else if (__dummyScrutVar0.equals(1))
                {
                    try
                    {
                        g.FillRectangle(new SolidBrush(VisProvs[j].ProvColor), TimeWidth + ProvWidth * j + 1, ((i - startingPoint) * LineH) + 1, ProvWidth - 1, LineH - 1);
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                        //design-time
                        g.FillRectangle(new SolidBrush(Color.White), TimeWidth + ProvWidth * j + 1, ((i - startingPoint) * LineH) + 1, ProvWidth - 1, LineH - 1);
                    }
                
                }
                else if (__dummyScrutVar0.equals(2))
                {
                    g.FillRectangle(new HatchBrush(HatchStyle.DarkUpwardDiagonal, Color.Black, VisProvs[j].ProvColor), TimeWidth + ProvWidth * j + 1, ((i - startingPoint) * LineH) + 1, ProvWidth - 1, LineH - 1);
                }
                else
                {
                    //more than 2
                    g.FillRectangle(new SolidBrush(Color.Black), TimeWidth + ProvWidth * j + 1, ((i - startingPoint) * LineH) + 1, ProvWidth - 1, LineH - 1);
                }   
            }
        }
    }

    /**
    * 
    */
    public static void drawGridLines(Graphics g) throws Exception {
        //Vert
        if (IsWeeklyView)
        {
            g.DrawLine(new Pen(Color.DarkGray), 0, 0, 0, ApptSheetHeight);
            g.DrawLine(new Pen(Color.White), TimeWidth - 1, 0, TimeWidth - 1, ApptSheetHeight);
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth, 0, TimeWidth, ApptSheetHeight);
            for (int d = 0;d < NumOfWeekDaysToDisplay;d++)
            {
                g.DrawLine(new Pen(Color.DarkGray), TimeWidth + ColDayWidth * d, 0, TimeWidth + ColDayWidth * d, ApptSheetHeight);
            }
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth + ColDayWidth * NumOfWeekDaysToDisplay, 0, TimeWidth + 1 + ColDayWidth * NumOfWeekDaysToDisplay, ApptSheetHeight);
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth * 2 + ColDayWidth * NumOfWeekDaysToDisplay, 0, TimeWidth * 2 + 1 + ColDayWidth * NumOfWeekDaysToDisplay, ApptSheetHeight);
        }
        else
        {
            g.DrawLine(new Pen(Color.DarkGray), 0, 0, 0, ApptSheetHeight);
            g.DrawLine(new Pen(Color.White), TimeWidth - 2, 0, TimeWidth - 2, ApptSheetHeight);
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth - 1, 0, TimeWidth - 1, ApptSheetHeight);
            for (int i = 0;i < ProvCount;i++)
            {
                g.DrawLine(new Pen(Color.DarkGray), TimeWidth + ProvWidth * i, 0, TimeWidth + ProvWidth * i, ApptSheetHeight);
            }
            for (int i = 0;i < ColCount;i++)
            {
                g.DrawLine(new Pen(Color.DarkGray), TimeWidth + ProvWidth * ProvCount + ColWidth * i, 0, TimeWidth + ProvWidth * ProvCount + ColWidth * i, ApptSheetHeight);
            }
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth + ProvWidth * ProvCount + ColWidth * ColCount, 0, TimeWidth + ProvWidth * ProvCount + ColWidth * ColCount, ApptSheetHeight);
            g.DrawLine(new Pen(Color.DarkGray), TimeWidth * 2 + ProvWidth * ProvCount + ColWidth * ColCount, 0, TimeWidth * 2 + ProvWidth * ProvCount + ColWidth * ColCount, ApptSheetHeight);
        } 
        for (int i = 0;i < (ApptSheetHeight);i += LineH * RowsPerIncr)
        {
            //horiz gray
            g.DrawLine(new Pen(Color.LightGray), TimeWidth, i, TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, i);
        }
        for (int i = 0;i < ApptSheetHeight;i += LineH * RowsPerHr)
        {
            //horiz Hour lines
            //was white
            g.DrawLine(new Pen(Color.LightGray), 0, i - 1, TimeWidth * 2 + ColWidth * ColCount + ProvWidth * ProvCount, i - 1);
            g.DrawLine(new Pen(Color.DarkSlateGray), 0, i, TimeWidth, i);
            g.DrawLine(new Pen(Color.Black), TimeWidth, i, TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, i);
            g.DrawLine(new Pen(Color.DarkSlateGray), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, i, TimeWidth * 2 + ColWidth * ColCount + ProvWidth * ProvCount, i);
        }
    }

    /**
    * 
    */
    public static void drawRedTimeIndicator(Graphics g) throws Exception {
        int curTimeY = (int)(DateTime.Now.Hour * LineH * RowsPerHr + DateTime.Now.Minute / 60f * (float)LineH * RowsPerHr);
        g.DrawLine(new Pen(Color.Red), 0, curTimeY, TimeWidth * 2 + ProvWidth * ProvCount + ColWidth * ColCount, curTimeY);
        g.DrawLine(new Pen(Color.Red), 0, curTimeY + 1, TimeWidth * 2 + ProvWidth * ProvCount + ColWidth * ColCount, curTimeY + 1);
    }

    /**
    * 
    */
    public static void drawMinutes(Graphics g, DateTime startTime, DateTime stopTime) throws Exception {
        Font font = new Font(FontFamily.GenericSansSerif, 8);
        //was msSans
        Font bfont = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Bold);
        //was Arial
        g.TextRenderingHint = TextRenderingHint.SingleBitPerPixelGridFit;
        //to make printing clearer
        DateTime hour = new DateTime();
        CultureInfo ci = (CultureInfo)CultureInfo.CurrentCulture.Clone();
        String hFormat = Lans.getShortTimeFormat(ci);
        String sTime = new String();
        int stop = stopTime.Hour;
        if (stop == 0)
        {
            //12AM, but we want to end on the next day so set to 24
            stop = 24;
        }
         
        int index = 0;
        for (int i = startTime.Hour;i < stop;i++)
        {
            //This will cause drawing times to always start at the top.
            hour = new DateTime(2000, 1, 1, i, 0, 0);
            //hour is the only important part of this time.
            sTime = hour.ToString(hFormat, ci);
            SizeF sizef = g.MeasureString(sTime, bfont);
            g.DrawString(sTime, bfont, new SolidBrush(Color.Black), TimeWidth - sizef.Width - 2, index * LineH * RowsPerHr + 1);
            g.DrawString(sTime, bfont, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + 1);
            if (MinPerIncr == 5)
            {
                g.DrawString(":15", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 6);
                g.DrawString(":45", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 9);
                g.DrawString(":15", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 6);
                g.DrawString(":45", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 9);
            }
            else if (MinPerIncr == 10)
            {
                g.DrawString(":10", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr);
                g.DrawString(":20", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 2);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
                g.DrawString(":40", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 4);
                g.DrawString(":50", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 5);
                g.DrawString(":10", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr);
                g.DrawString(":20", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 2);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
                g.DrawString(":40", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 4);
                g.DrawString(":50", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 5);
            }
            else
            {
                //15
                g.DrawString(":15", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 2);
                g.DrawString(":45", font, new SolidBrush(Color.Black), TimeWidth - 19, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
                g.DrawString(":15", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr);
                g.DrawString(":30", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 2);
                g.DrawString(":45", font, new SolidBrush(Color.Black), TimeWidth + ColWidth * ColCount + ProvWidth * ProvCount, index * LineH * RowsPerHr + LineH * RowsPerIncr * 3);
            }  
            index++;
        }
    }

    /**
    * 
    */
    public static void computeColDayWidth() throws Exception {
        ColDayWidth = (ApptSheetWidth - TimeWidth * 2) / NumOfWeekDaysToDisplay;
    }

    /**
    * 
    */
    public static void computeColAptWidth() throws Exception {
        ColAptWidth = (float)(ColDayWidth - 1) / (float)ColCount;
    }

    /**
    * 
    */
    public static void setLineHeight(int fontSize) throws Exception {
        LineH = (new Font("Arial", fontSize)).Height;
    }

    /**
    * 
    */
    public static int xPosToOpIdx(int xPos) throws Exception {
        int retVal = new int();
        if (IsWeeklyView)
        {
            int day = xPosToDay(xPos);
            retVal = (int)Math.Floor((double)(xPos - TimeWidth - day * ColDayWidth) / ColAptWidth);
        }
        else
        {
            retVal = (int)Math.Floor((double)(xPos - TimeWidth - ProvWidth * ProvCount) / ColWidth);
        } 
        if (retVal > ColCount - 1)
            retVal = ColCount - 1;
         
        if (retVal < 0)
            retVal = 0;
         
        return retVal;
    }

    /**
    * If not weekview, then it always returns 0.  If weekview, then it gives the dayofweek as int. Always based on current view, so 0 will be first day showing.
    */
    public static int xPosToDay(int xPos) throws Exception {
        if (!IsWeeklyView)
        {
            return 0;
        }
         
        int retVal = (int)Math.Floor((double)(xPos - TimeWidth) / ColDayWidth);
        if (retVal > NumOfWeekDaysToDisplay - 1)
            retVal = NumOfWeekDaysToDisplay - 1;
         
        if (retVal < 0)
            retVal = 0;
         
        return retVal;
    }

    /**
    * Called when mouse down anywhere on apptSheet. Automatically rounds down.
    */
    public static int yPosToHour(int yPos) throws Exception {
        int retVal = yPos / LineH / RowsPerHr;
        return retVal;
    }

    //newY/LineH/6;
    /**
    * Called when mouse down anywhere on apptSheet. This will give very precise minutes. It is not rounded for accuracy.
    */
    public static int yPosToMin(int yPos) throws Exception {
        int hourPortion = yPosToHour(yPos) * LineH * RowsPerHr;
        float MinPerPixel = 60 / (float)LineH / (float)RowsPerHr;
        int minutes = (int)((yPos - hourPortion) * MinPerPixel);
        return minutes;
    }

    /**
    * Used when dropping an appointment to a new location.  Converts x-coordinate to operatory index of ApptCatItems.VisOps, rounding to the nearest.  In this respect it is very different from XPosToOp.
    */
    public static int convertToOp(int newX) throws Exception {
        int retVal = 0;
        if (IsWeeklyView)
        {
            int dayI = xPosToDay(newX);
            //does not round
            int deltaDay = dayI * (int)ColDayWidth;
            int adjustedX = newX - (int)TimeWidth - deltaDay;
            retVal = (int)Math.Round((double)(adjustedX) / ColAptWidth);
            //when there are multiple days, special situation where x is within the last op for the day, so it goes to next day.
            if (retVal > VisOps.Count - 1 && dayI < NumOfWeekDaysToDisplay - 1)
            {
                retVal = 0;
            }
             
        }
        else
        {
            retVal = (int)Math.Round((double)(newX - TimeWidth - ProvWidth * ProvCount) / ColWidth);
        } 
        //make sure it's not outside bounds of array:
        if (retVal > VisOps.Count - 1)
            retVal = VisOps.Count - 1;
         
        if (retVal < 0)
            retVal = 0;
         
        return retVal;
    }

    /**
    * Used when dropping an appointment to a new location.  Converts x-coordinate to day index.  Only used in weekly view.
    */
    public static int convertToDay(int newX) throws Exception {
        int retVal = (int)Math.Floor((double)(newX - TimeWidth) / (double)ColDayWidth);
        //the above works for every situation except when in the right half of the last op for a day. Test for that situation:
        if (newX - TimeWidth > (retVal + 1) * ColDayWidth - ColAptWidth / 2)
        {
            retVal++;
        }
         
        //make sure it's not outside bounds of array:
        if (retVal > NumOfWeekDaysToDisplay - 1)
            retVal = NumOfWeekDaysToDisplay - 1;
         
        if (retVal < 0)
            retVal = 0;
         
        return retVal;
    }

    /**
    * Used when dropping an appointment to a new location. Rounds to the nearest increment.
    */
    public static int convertToHour(int newY) throws Exception {
        return (int)(((double)newY + (double)LineH * (double)RowsPerIncr / 2) / (double)RowsPerHr / (double)LineH);
    }

    //return (int)((newY+LineH/2)/6/LineH);
    /**
    * Used when dropping an appointment to a new location. Rounds to the nearest increment.
    */
    public static int convertToMin(int newY) throws Exception {
        //int retVal=(int)(Decimal.Remainder(newY,6*LineH)/LineH)*10;
        //first, add pixels equivalent to 1/2 increment: newY+LineH*RowsPerIncr/2
        //Yloc     Height     Rows      1
        //---- + ( ------ x --------- x - )
        //  1       Row     Increment   2
        //then divide by height per hour: RowsPerHr*LineH
        //Rows   Height
        //---- * ------
        //Hour    Row
        int pixels = (int)Decimal.Remainder((double)newY + (double)LineH * (double)RowsPerIncr / 2, (double)RowsPerHr * (double)LineH);
        //We are only interested in the remainder, and this is called pixels.
        //Convert pixels to increments. Round down to nearest increment when converting to int.
        //pixels/LineH/RowsPerIncr:
        //pixels    Rows    Increment
        //------ x ------ x ---------
        //  1      pixels     Rows
        int increments = (int)((double)pixels / (double)LineH / (double)RowsPerIncr);
        //Convert increments to minutes: increments*MinPerIncr
        int retVal = increments * MinPerIncr;
        if (retVal == 60)
            return 0;
         
        return retVal;
    }

    /**
    * Called from ContrAppt.comboView_SelectedIndexChanged and ContrAppt.RefreshVisops. Set colCountOverride to 0 unless printing.
    */
    public static void computeColWidth(int colCountOverride) throws Exception {
        if (VisOps == null || VisProvs == null)
        {
            return ;
        }
         
        try
        {
            if (RowsPerIncr == 0)
            {
                RowsPerIncr = 1;
            }
             
            //Allow user to choose how many columns print per page.
            if (colCountOverride > 0)
            {
                ColCount = colCountOverride;
            }
            else
            {
                ColCount = VisOps.Count;
            } 
            if (IsWeeklyView)
            {
                ColCount = VisOps.Count;
                ProvCount = 0;
            }
            else
            {
                ProvCount = VisProvs.Count;
            } 
            if (ColCount == 0)
            {
                ColWidth = 0;
            }
            else
            {
                if (IsWeeklyView)
                {
                    ColDayWidth = (ApptSheetWidth - TimeWidth * 2) / NumOfWeekDaysToDisplay;
                    ColAptWidth = (float)(ColDayWidth - 1) / (float)ColCount;
                    ColWidth = (ApptSheetWidth - TimeWidth * 2 - ProvWidth * ProvCount) / ColCount;
                }
                else
                {
                    ColWidth = (ApptSheetWidth - TimeWidth * 2 - ProvWidth * ProvCount) / ColCount;
                } 
            } 
            MinPerIncr = PrefC.getInt(PrefName.AppointmentTimeIncrement);
            MinPerRow = MinPerIncr / RowsPerIncr;
            RowsPerHr = 60 / MinPerIncr * RowsPerIncr;
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show("error computing width");
        }
    
    }

    /**
    * Returns the index of the opNum within VisOps.  Returns -1 if not in VisOps.
    */
    public static int getIndexOp(long opNum) throws Exception {
        for (int i = 0;i < VisOps.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (VisOps[i].OperatoryNum == opNum)
                return i;
             
        }
        return -1;
    }

    /**
    * Returns the index of the provNum within VisProvs.
    */
    public static int getIndexProv(long provNum) throws Exception {
        for (int i = 0;i < VisProvs.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (VisProvs[i].ProvNum == provNum)
                return i;
             
        }
        return -1;
    }

    public static void provBarShading(DataRow row) throws Exception {
        String patternShowing = ApptSingleDrawing.GetPatternShowing(row["Pattern"].ToString());
        int indexProv = -1;
        if (StringSupport.equals(row["IsHygiene"].ToString(), "1"))
        {
            indexProv = GetIndexProv(PIn.Long(row["ProvHyg"].ToString()));
        }
        else
        {
            indexProv = GetIndexProv(PIn.Long(row["ProvNum"].ToString()));
        } 
        if (indexProv != -1 && row["AptStatus"].ToString() != (((Enum)ApptStatus.Broken).ordinal()).ToString())
        {
            int startIndex = ApptSingleDrawing.convertToY(row,0) / LineH;
            for (int k = 0;k < patternShowing.Length;k++)
            {
                //rounds down
                if (StringSupport.equals(patternShowing.Substring(k, 1), "X"))
                {
                    try
                    {
                        ProvBar[indexProv][startIndex + k]++;
                    }
                    catch (Exception __dummyCatchVar4)
                    {
                    }
                
                }
                 
            }
        }
         
    }

}


//appointment must extend past midnight.  Very rare