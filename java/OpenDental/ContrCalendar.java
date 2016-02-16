//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;

import OpenDental.OneCalendarDay;

/**
* 
*/
public class ContrCalendar  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    //private int RowCount;
    //private int ColCount;
    //private int RowHeight;
    //private int ColWidth;
    //private int HeaderHeight;
    //private int DayHeadHeight;
    private DateTime selectedDate = new DateTime();
    /**
    * 
    */
    public List<OneCalendarDay> ListDays = new List<OneCalendarDay>();
    //<summary></summary>
    //public int SelectedDay;
    //<summary>Was called CurrentDay</summary>
    //public OneCalendarDay Today;
    //<summary></summary>
    //public int MaxRowsText;
    //private int count=0;
    /**
    * 
    */
    public ContrCalendar() throws Exception {
        initializeComponent();
    }

    //RowCount=6;
    //ColCount=7;
    //HeaderHeight=25;
    //DayHeadHeight=15;//(int)FontText.GetHeight()+6;
    //colorBG=SystemColors.Control;
    //HeadColor=SystemColors.Control;
    //FootColor=SystemColors.Control;
    //DayHeadColor=SystemColors.Window;
    //TextColor=Color.Black;//SystemColors.ControlText;
    //DayOpenColor=Color.White;//SystemColors.Window; Was ActiveDayColor
    //SelectedDayColor=Color.White;//SystemColors.Highlight;
    //LinePen=new Pen(Color.SlateGray,1);
    //FontText=new Font("Microsoft Sans Serif",8,FontStyle.Bold);
    //FontText2=new Font("Microsoft Sans Serif",8,FontStyle.Regular);
    //FontHeader = new Font("Microsoft Sans Serif",9,FontStyle.Bold);
    //MaxRowsText=5;
    //ListDays=new List<OneCalendarDay>();
    //for(int i=0;i<List.Length;i++){
    //	List[i]=new OneCalendarDay();
    //  List[i].RowsOfText=new string[MaxRowsText];
    //}
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
        this.SuspendLayout();
        //
        // ContrCalendar
        //
        this.BackColor = System.Drawing.SystemColors.Control;
        this.DoubleBuffered = true;
        this.Name = "ContrCalendar";
        this.Size = new System.Drawing.Size(535, 374);
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    //[Category("AAA Custom"),
    //	Description("SelectedDate. Keeps track of the date selected")
    //]
    public DateTime getSelectedDate() throws Exception {
        return selectedDate;
    }

    public void setSelectedDate(DateTime value) throws Exception {
        selectedDate = value;
    }

    /*private void ContrCalendar_Load(object sender,System.EventArgs e) {
    			selectedDate=DateTime.Today;
    			SelectedDay=SelectedDate.Day;
    			//this needs work.  should be a simple invalidate instead:
    			//Graphics g=this.CreateGraphics();
    			//this.OnPaint(new PaintEventArgs(g,this.ClientRectangle));
    			//g.Dispose();
    			//FillDaysInMonth();
    			//this.Invalidate();
    		}*/
    /*protected override void OnResize(EventArgs e) {
    			base.OnResize(e);
    			RecHead=new Rectangle(0,0,Width,HeaderHeight);
    			RecFoot=new Rectangle(0,Height-HeaderHeight,Width,HeaderHeight);
    			RecDayHead=new Rectangle(0,HeaderHeight,Width,DayHeadHeight);
    			RowHeight=(Height-(HeaderHeight*2)-DayHeadHeight)/RowCount;
    			ColWidth=Width/ColCount;
    			//Graphics g=this.CreateGraphics();
    			//g.FillRectangle(new SolidBrush(Color.BlueViolet),new Rectangle(0,0,Width,Height));
    			//g.Dispose();
    			//this.Invalidate();
    			//this.OnPaint(new PaintEventArgs(this.CreateGraphics(),this.ClientRectangle)); 
    		}*/
    //private void ContrCalendar_Resize(object sender,System.EventArgs e) {
    //}
    protected void onPaint(PaintEventArgs e) throws Exception {
        super.OnPaint(e);
        Graphics g = e.Graphics;
        //this.CreateGraphics();//e.Graphics doesn't work for some reason
        g.FillRectangle(new SolidBrush(SystemColors.Control), new Rectangle(0, 0, Width, Height));
        g.DrawRectangle(Pens.SlateGray, new Rectangle(0, 0, Width - 1, Height - 1));
    }

    //DrawHeader(g);
    //FillDaysInMonth();
    //DrawDays(g);
    //DrawDayHeaders(g);
    //PositionButtons();
    //DrawFooter(g);
    //MarkTodayDate(g);
    //g.Dispose();
    //private void ContrCalendar_Paint(object sender,System.Windows.Forms.PaintEventArgs e) {
    //base.OnPaint(e);//this causes VS to crash
    //background:
    //Graphics g=this.CreateGraphics();//e.Graphics doesn't work for some reason
    //g.FillRectangle(new SolidBrush(colorBG),new Rectangle(0,0,Width,Height));
    //g.DrawRectangle(LinePen,new Rectangle(0,0,Width-1,Height-1));
    //DrawHeader(g);
    //FillDaysInMonth();
    //DrawDays(g);
    //DrawDayHeaders(g);
    //PositionButtons();
    //DrawFooter(g);
    //MarkTodayDate(g);
    //g.Dispose();
    //}
    private void drawHeader(Graphics g) throws Exception {
    }

    /*g.FillRectangle(new SolidBrush(HeadColor),RecHead);
    			g.DrawRectangle(LinePen,RecHead);
    			Header=selectedDate.ToString("MMMM, yyyy");
    			int xPos=Width/2-(int)(g.MeasureString(Header,FontHeader).Width/2f);
    			int yPos=5;
    			g.DrawString(Header,FontHeader,Brushes.Black,xPos,yPos);*/
    private void fillDaysInMonth() throws Exception {
    }

    /*int row=0;
    			int column=(int)(new DateTime(selectedDate.Year,selectedDate.Month,1).DayOfWeek)-1;
    			DaysInMonth=DateTime.DaysInMonth(selectedDate.Year,selectedDate.Month);
    			OneCalendarDay oneday;
    			for(int i=1;i<=DaysInMonth;i++) {
    				oneday=new OneCalendarDay();
    				oneday.Bounds=new Rectangle(ColWidth*column,HeaderHeight+DayHeadHeight+RowHeight*row,ColWidth,RowHeight);
    				oneday.Date=new DateTime(selectedDate.Year,selectedDate.Month,i);
    				if(i==DateTime.Today.Day
    					&& selectedDate.Month==DateTime.Today.Month 
    					&& selectedDate.Year==DateTime.Today.Year) 
    				{
    					Today=oneday.Clone();
    					Today.Bounds.X+=1;
    					Today.Bounds.Y+=1;
    					Today.Bounds.Width-=2;
    					Today.Bounds.Height-=2;
    				}
    				if(i==SelectedDay) {
    					oneday.IsSelected=true;
    				}
    				ListDays.Add(oneday);
    				if(column==6) {
    					row++;
    					column=0;
    				}
    				else {
    					column++;
    				}
    			}*/
    /**
    * 
    */
    public void drawDays(Graphics g) throws Exception {
    }

    /*StringFormat format=new StringFormat();
    			format.LineAlignment=StringAlignment.Far;//right
    			format.Alignment=StringAlignment.Near;//top
    			RectangleF rectF;
    			for(int i=0;i<ListDays.Count;i++) {
    				//if(i==SelectedDay){
    				//	g.FillRectangle(new SolidBrush(SelectedDayColor),List[i].Rec);
    				//	g.DrawString(List[i].Day.ToString()
    				//		,FontText,Brushes.Black,List[i].xPos,List[i].yPos);
    				//}
    				//else{
    				if(ListDays[i].color.Equals(Color.Empty)) {
    					g.FillRectangle(new SolidBrush(DayOpenColor),ListDays[i].Bounds);
    				}
    				else {
    					g.FillRectangle(new SolidBrush(ListDays[i].color),ListDays[i].Bounds);
    				}
    				rectF=new RectangleF(ListDays[i].Bounds.X,ListDays[i].Bounds.Y,ListDays[i].Bounds.Width,ListDays[i].Bounds.Height);
    				g.DrawString(ListDays[i].Date.Day.ToString(),FontText,Brushes.Black,rectF,format);
    				//}
    				DrawRowsOfText(i,g);
    			}
    			DrawMonthGrid(g);
    			MarkTodayDate(g);*/
    /**
    * Draws the names of the days of the week
    */
    private void drawDayHeaders(Graphics g) throws Exception {
    }

    /*g.FillRectangle(new SolidBrush(DayHeadColor),RecDayHead);
    			g.DrawRectangle(LinePen,RecDayHead);
    			for(int i=0;i<=ColCount;i++) {
    				g.DrawLine(LinePen,ColWidth*i,HeaderHeight,ColWidth*i,HeaderHeight+DayHeadHeight);
    			}
    			string[] daysOfWeek=CultureInfo.CurrentCulture.DateTimeFormat.DayNames;//already translated
    			for(int i=0;i<daysOfWeek.Length;i++) {
    				int xPos=i*ColWidth+ColWidth/2-(int)(g.MeasureString(daysOfWeek[i],FontText).Width/2f);
    				int yPos=HeaderHeight+DayHeadHeight-(int)(FontText.GetHeight()/2f);
    				g.DrawString(daysOfWeek[i],FontText,Brushes.Black,xPos,yPos);
    			}*/
    /*private void PositionButtons() {
    			butPrevious.Location=new Point(2,2);
    			butNext.Location=new Point(Width-butNext.Size.Width-1,2);
    			butPrevious.BackColor=SystemColors.Control;
    			butNext.BackColor=SystemColors.Control;
    		}*/
    private void drawFooter(Graphics g) throws Exception {
    }

    /*g.FillRectangle(new SolidBrush(FootColor),RecFoot);
    			g.DrawRectangle(LinePen,RecFoot);
    			Footer="Today: "+DateTime.Today.ToShortDateString();
    			int xPos=Width/2-(int)(g.MeasureString(Footer,FontText).Width/2f);
    			int yPos=Height-(int)(FontText.GetHeight()/2f);
    			g.DrawString(Lan.g(this,Footer),FontText,Brushes.Black,xPos,yPos);*/
    private void markTodayDate(Graphics g) throws Exception {
    }

    //if(ListDays[SelectedDay].Date.Month==DateTime.Today.Month) {
    //	g.DrawRectangle(new Pen(Color.Red),Today.Bounds);
    //}
    private void drawRowsOfText(int day, Graphics g) throws Exception {
    }

    /*float extra=2;
    			float xCoord=(int)(ListDays[day].Bounds.X+extra);
    			float yCoord=(float)(ListDays[day].Bounds.Y+(FontText2.GetHeight()*1.5));
    			RectangleF r=new RectangleF(xCoord,yCoord,ListDays[day].Bounds.Width-(2*extra)
    				,(float)(FontText2.GetHeight()));
    			if(ListDays[day].RowsOfText.Count>0) {
    				g.DrawString(ListDays[day].RowsOfText[0],FontText2,Brushes.Black,r);
    			}
    			for(int i=0;i<ListDays[day].RowsOfText.Count;i++) {
    				if(ListDays[day].RowsOfText[i]=="") {
    				}
    				else {
    					g.DrawString(ListDays[day].RowsOfText[i],FontText2,Brushes.Black
    						,new RectangleF(xCoord,yCoord,ListDays[day].Bounds.Width-(2*extra),FontText2.GetHeight()));
    					yCoord+=FontText2.GetHeight();
    				}
    			}*/
    private void drawMonthGrid(Graphics g) throws Exception {
    }

}


