//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;

import OpenDental.ContrApptSingle;
import OpenDental.Lan;
import OpenDentBusiness.UI.ApptSingleDrawing;

public class PinBoard  extends Control 
{

    private List<ContrApptSingle> apptList = new List<ContrApptSingle>();
    private int selectedIndex = new int();
    //private bool MouseIsDown;
    /**
    * 
    */
    public EventHandler SelectedIndexChanged = null;
    public PinBoard() throws Exception {
        initializeComponent();
        apptList = new List<ContrApptSingle>();
    }

    /**
    * Do not make changes here to the list.  This is for read-only purposes.
    */
    public List<ContrApptSingle> getApptList() throws Exception {
        return apptList;
    }

    public ContrApptSingle getSelectedAppt() throws Exception {
        if (selectedIndex == -1)
        {
            return null;
        }
         
        return apptList[selectedIndex];
    }

    /**
    * Gets or sets the selected
    */
    public int getSelectedIndex() throws Exception {
        return selectedIndex;
    }

    public void setSelectedIndex(int value) throws Exception {
        if (selectedIndex > apptList.Count - 1)
        {
            selectedIndex = -1;
        }
        else
        {
            selectedIndex = value;
        } 
        for (int i = 0;i < apptList.Count;i++)
        {
            if (i == value)
            {
                apptList[i].IsSelected = true;
            }
            else
            {
                apptList[i].IsSelected = false;
            } 
        }
        Invalidate();
    }

    protected void onSelectedIndexChanged() throws Exception {
        if (SelectedIndexChanged != null)
        {
            SelectedIndexChanged(this, new EventArgs());
        }
         
    }

    /**
    * Supply a datarow that contains all the database values needed for the appointment that is being added.
    */
    public void addAppointment(DataRow row, DataTable tableApptFields, DataTable tablePatFields) throws Exception {
        for (int i = 0;i < apptList.Count;i++)
        {
            //if appointment is already on the pinboard, just select it.
            if (apptList[i].DataRoww["AptNum"].ToString() == row["AptNum"].ToString())
            {
                //Highlight it
                selectedIndex = i;
                apptList[i].IsSelected = true;
                Invalidate();
            }
             
        }
        //throw new ApplicationException(Lan.g(this,"Appointment is already on the pinboard."));
        ContrApptSingle PinApptSingle = new ContrApptSingle();
        PinApptSingle.ThisIsPinBoard = true;
        PinApptSingle.DataRoww = row;
        PinApptSingle.TableApptFields = tableApptFields;
        PinApptSingle.TablePatFields = tablePatFields;
        PinApptSingle.Size = ApptSingleDrawing.setSize(row);
        PinApptSingle.PatternShowing = ApptSingleDrawing.GetPatternShowing(row["Pattern"].ToString());
        PinApptSingle.Width = Width - 2;
        PinApptSingle.IsSelected = true;
        PinApptSingle.Location = new Point(0, 13 * apptList.Count);
        apptList.Add(PinApptSingle);
        selectedIndex = apptList.Count - 1;
        Invalidate();
    }

    public void clearSelected() throws Exception {
        if (selectedIndex == -1)
        {
            return ;
        }
         
        apptList.RemoveAt(selectedIndex);
        if (apptList.Count >= selectedIndex + 1)
        {
            //no change to selectedIndex
            apptList[selectedIndex].IsSelected = true;
        }
        else if (apptList.Count > 0)
        {
            //select the last one
            selectedIndex = apptList.Count - 1;
            apptList[selectedIndex].IsSelected = true;
        }
        else
        {
            selectedIndex = -1;
        }  
        for (int i = 0;i < apptList.Count;i++)
        {
            //reset locations
            apptList[i].Location = new Point(0, 13 * i);
        }
        Invalidate();
    }

    /**
    * If an appt is already on the pinboard, and the information in it is change externally, this 'refreshes' the data.
    */
    public void resetData(DataRow row) throws Exception {
        for (int i = 0;i < apptList.Count;i++)
        {
            if (apptList[i].DataRoww["AptNum"].ToString() == row["AptNum"].ToString())
            {
                apptList[i].DataRoww = row;
                apptList[i].Size = ApptSingleDrawing.setSize(row);
                apptList[i].Width = Width - 2;
            }
             
        }
        //PinApptSingle.IsSelected=true;
        //PinApptSingle.Location=new Point(0,13*apptList.Count);
        Invalidate();
    }

    /*
    		///<Summary>Sets all appointments to specified value.</Summary>
    		public void SetSelected(bool setValue){
    			selectedIndices.Clear();
    			if(setValue) {//select all
    				for(int i=0;i<apptList.Count;i++) {
    					selectedIndices.Add(i);
    					apptList[i].IsSelected=true;
    				}
    			}
    			else{//deselect all
    			}
    			Invalidate();
    		}*/
    protected void onPaint(PaintEventArgs pe) throws Exception {
        Graphics g = pe.Graphics;
        try
        {
            g.Clear(Color.White);
            g.DrawRectangle(Pens.Black, 0, 0, Width - 1, Height - 1);
            for (int i = 0;i < apptList.Count;i++)
            {
                apptList[i].CreateShadow();
                g.DrawImage(apptList[i].Shadow, 0, i * 13);
            }
            if (apptList.Count == 0)
            {
                StringFormat format = new StringFormat();
                format.Alignment = StringAlignment.Center;
                format.LineAlignment = StringAlignment.Center;
                g.DrawString(Lan.g(this,"Drag Appointments to this PinBoard"), Font, Brushes.Gray, new RectangleF(0, 0, Width, Height - 20), format);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //g.Dispose();
    //base.OnPaint(pe);
    protected void onMouseDown(MouseEventArgs e) throws Exception {
        //figure out which appt mouse is on.  Start at end and work backwards
        int index = -1;
        for (int i = apptList.Count - 1;i >= 0;i--)
        {
            if (e.Y < apptList[i].Top || e.Y > apptList[i].Bottom)
            {
                continue;
            }
             
            index = i;
            break;
        }
        if (index == -1)
        {
            super.OnMouseDown(e);
            return ;
        }
         
        if (index == selectedIndex)
        {
            //no change
            super.OnMouseDown(e);
            return ;
        }
         
        //for now.
        selectedIndex = index;
        for (int i = 0;i < apptList.Count;i++)
        {
            if (i == selectedIndex)
            {
                apptList[i].IsSelected = true;
            }
            else
            {
                apptList[i].IsSelected = false;
            } 
        }
        Invalidate();
        onSelectedIndexChanged();
        super.OnMouseDown(e);
    }

    protected void onMouseLeave(EventArgs e) throws Exception {
        //MouseIsDown=false;
        super.OnMouseLeave(e);
    }

    protected void onMouseUp(MouseEventArgs e) throws Exception {
        //MouseIsDown=false;
        super.OnMouseUp(e);
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
        components = new System.ComponentModel.Container();
    }

}


