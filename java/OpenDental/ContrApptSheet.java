//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.UI.ApptDrawing;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrApptSheet  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public Bitmap Shadow = new Bitmap();
    /**
    * 
    */
    public boolean IsScrolling = false;
    /**
    * This gets set externally each time the module is selected.  It is the background schedule for the entire period.  Includes all types.
    */
    public List<Schedule> SchedListPeriod = new List<Schedule>();
    /**
    * 
    */
    public ContrApptSheet() throws Exception {
        initializeComponent();
    }

    // This call is required by the Windows.Forms Form Designer.
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
             
            if (Shadow != null)
            {
                Shadow.Dispose();
                Shadow = null;
            }
             
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ContrApptSheet
        //
        this.Name = "ContrApptSheet";
        this.Size = new System.Drawing.Size(482, 540);
        this.Load += new System.EventHandler(this.ContrApptSheet_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrApptSheet_Layout);
        this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.ContrApptSheet_MouseMove);
        this.ResumeLayout(false);
    }

    private void contrApptSheet_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void contrApptSheet_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
    }

    //Height=ApptDrawing.LineH*24*ApptDrawing.RowsPerHr;
    //Width=(int)(ApptDrawing.TimeWidth*2+ApptDrawing.ProvWidth*ApptDrawing.ProvCount+ApptDrawing.ColWidth*ApptDrawing.ColCount);
    /**
    * 
    */
    protected void onPaint(PaintEventArgs pea) throws Exception {
        drawShadow();
    }

    /**
    * 
    */
    public void createShadow() throws Exception {
        if (Shadow != null)
        {
            Shadow.Dispose();
            Shadow = null;
        }
         
        Height = ApptDrawing.LineH * 24 * ApptDrawing.RowsPerHr;
        Width = (int)(ApptDrawing.TimeWidth * 2 + ApptDrawing.ProvWidth * ApptDrawing.ProvCount + ApptDrawing.ColWidth * ApptDrawing.ColCount);
        if (Width < 2)
        {
            return ;
        }
         
        Shadow = new Bitmap(Width, Height);
        if (ApptDrawing.RowsPerIncr == 0)
            ApptDrawing.RowsPerIncr = 1;
         
        if (ApptDrawing.SchedListPeriod == null)
        {
            return ;
        }
         
        //not sure if this is necessary
        Graphics g = Graphics.FromImage(Shadow);
        try
        {
            {
                ApptDrawing.ApptSheetWidth = Width;
                ApptDrawing.ApptSheetHeight = Height;
                ApptDrawing.DrawAllButAppts(g, true, new DateTime(2011, 1, 1, 0, 0, 0), new DateTime(2011, 1, 1, 0, 0, 0), ApptDrawing.VisOps.Count, 0, 8, false);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    /**
    * 
    */
    public void drawShadow() throws Exception {
        if (Shadow != null)
        {
            Graphics grfx2 = this.CreateGraphics();
            grfx2.DrawImage(Shadow, 0, 0);
            grfx2.Dispose();
        }
         
    }

    private void contrApptSheet_MouseMove(Object sender, MouseEventArgs e) throws Exception {
    }

}


//Debug.WriteLine("ContrApptSheet_MouseMove:"+DateTime.Now.ToLongTimeString()+", loc:"+e.Location.ToString());