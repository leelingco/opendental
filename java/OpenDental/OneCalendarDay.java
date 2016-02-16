//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;

import OpenDental.OneCalendarDay;

/*
			//horizontal lines:  
			for(int i=1;i<=RowCount;i++) {
				g.DrawLine(LinePen,0,HeaderHeight+DayHeadHeight+RowHeight*i,Width,HeaderHeight+DayHeadHeight+RowHeight*i);
			}
			//vertical lines
			for(int i=0;i<=ColCount;i++) {
				g.DrawLine(LinePen,ColWidth*i,HeaderHeight,ColWidth*i,Height-HeaderHeight);
			}*/
/*private void ContrCalendar_MouseDown(object sender,System.Windows.Forms.MouseEventArgs e) {
			//Graphics g=this.CreateGraphics();
			int OldSelected=SelectedDay;
			for(int i=1;i<=DaysInMonth;i++) {
				if(e.X >= ListDays[i].Bounds.X && e.X <= ListDays[i].Bounds.X+ListDays[i].Bounds.Width 
					&& e.Y >= ListDays[i].Bounds.Y && e.Y <= ListDays[i].Bounds.Y+ListDays[i].Bounds.Height) {
					SelectedDay=ListDays[i].Date.Day;
					selectedDate=ListDays[i].Date;
					ListDays[i].IsSelected=true;
					ListDays[OldSelected].IsSelected=false;
					/*
					//unpainting selected and repainting Windows color
					if(List[OldSelected].color.Equals(Color.Empty)){
						grfx.FillRectangle(new SolidBrush(DayOpenColor),List[OldSelected].Rec);
						grfx.DrawString(List[OldSelected].Day.ToString()
							,FontText,Brushes.Black,List[OldSelected].xPos,List[OldSelected].yPos);
						DrawRowsOfText(OldSelected,grfx); 
					}
					else{
						grfx.FillRectangle(new SolidBrush(List[OldSelected].color),List[OldSelected].Rec);
						grfx.DrawString(List[OldSelected].Day.ToString()
							,FontText,Brushes.Black,List[OldSelected].xPos,List[OldSelected].yPos);
						DrawRowsOfText(OldSelected,grfx);            
					}
					//painting Selected   
					grfx.FillRectangle(new SolidBrush(SelectedDayColor),List[i].Rec); 
					grfx.DrawString(List[i].Day.ToString()
						,FontText,Brushes.Black,List[i].xPos,List[i].yPos);
					DrawRowsOfText(i,grfx);
					DrawMonthGrid(grfx); 
					MarkTodayDate(grfx); 
				}
			}
			//g.Dispose();
		}*/
//<summary></summary>
//public void ChangeColor(int day,Color color) {
//	ListDays[day].color=color;
//}
//<summary></summary>
//public void AddText(int day,string s) {
/*if(ListDays[day].NumRowsText!=MaxRowsText) {
				for(int i=0;i<ListDays[day].RowsOfText.Count;i++) {
					if(ListDays[day].RowsOfText[i]=="" || ListDays[day].RowsOfText[i]==null) {
						ListDays[day].RowsOfText[i]=s;
						ListDays[day].NumRowsText++;
						return;
					}
				}
			}*/
//else{
//MessageBox.Show(Lan.g(this,"Too Many Rows of Text.  Can Only Have "+MaxRowsText.ToString()));
//}
//}
/*//<summary></summary>
		public void ResetList() {
			for(int i=1;i<ListDays.Count;i++) {
				ListDays[i].color=Color.Empty;
				ListDays[i].RowsOfText=new List<string>();
				ListDays[i].NumRowsText=0;
			}
		}*/
/*private void butPrevious_Click(object sender,System.EventArgs e) {
			//Graphics g=this.CreateGraphics();
			selectedDate=selectedDate.AddMonths(-1);
			//DisplayDaysInMonth(g);
			OnChangeMonth(e);
			//grfx.Dispose();
			this.Invalidate();
			//this.OnPaint(new PaintEventArgs(this.CreateGraphics(),this.ClientRectangle)); 
		}
		private void butNext_Click(object sender,System.EventArgs e) {
			//Graphics grfx=this.CreateGraphics();
			selectedDate=selectedDate.AddMonths(1);
			//DisplayDaysInMonth(grfx);
			//grfx.Dispose();
			OnChangeMonth(e);
			this.Invalidate();
			//this.OnPaint(new PaintEventArgs(this.CreateGraphics(),this.ClientRectangle)); 	
		}*/
/*
		///<summary></summary>
		public delegate void CellEventHandler(object sender,CellEventArgs e);
		///<summary></summary>
		public delegate void EventHandler(object sender,EventArgs e);
		///<summary></summary>
		public event CellEventHandler CellClicked;
		///<summary></summary>
		public event CellEventHandler CellDoubleClicked;
		///<summary></summary>
		public event EventHandler ChangeMonth;
		///<summary></summary>
		protected virtual void OnCellClicked(CellEventArgs e) {
			if(CellClicked !=null) {
				CellClicked(this,e);
			}
		}
		///<summary></summary>
		protected virtual void OnCellDoubleClicked(CellEventArgs e) {
			if(CellDoubleClicked !=null) {
				CellDoubleClicked(this,e);
			}
		}
		///<summary></summary>
		protected virtual void OnChangeMonth(EventArgs e) {
			if(ChangeMonth !=null) {
				ChangeMonth(this,e);
			}
		}*/
//Object keeps track of drawing coords,text, and date.  each day is stored in List to draw each month
/**
* 
*/
public class OneCalendarDay   
{
    /**
    * 
    */
    public Rectangle Bounds = new Rectangle();
    /**
    * 
    */
    public DateTime Date = new DateTime();
    /**
    * 
    */
    public Color color = new Color();
    //<summary></summary>
    //public bool IsSelected;
    /**
    * 
    */
    public String RowsOfText = new String();
    //<summary></summary>
    //public int NumRowsText;
    //public OneCalendarDay() {
    //	RowsOfText=new List<string>();
    //}
    public OneCalendarDay copy() throws Exception {
        return (OneCalendarDay)this.MemberwiseClone();
    }

}


