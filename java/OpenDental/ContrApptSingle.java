//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDental.ApptViewItemL;
import OpenDental.PIn;
import OpenDentBusiness.UI.ApptDrawing;
import OpenDentBusiness.UI.ApptSingleDrawing;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrApptSingle  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * Set on mouse down or from Appt module
    */
    public static long ClickedAptNum = new long();
    /**
    * This is not the best place for this, but changing it now would cause bugs.  Set manually
    */
    public static long SelectedAptNum = new long();
    /**
    * True if this control is on the pinboard
    */
    public boolean ThisIsPinBoard = new boolean();
    /**
    * //Stores the shading info for the provider bars on the left of the appointments module
    */
    //public static int[][] ProvBar;
    /**
    * Stores the background bitmap for this control
    */
    public Bitmap Shadow = new Bitmap();
    private Font baseFont = new Font("Arial", 8);
    private Font boldFont = new Font("Arial", 8, FontStyle.Bold);
    /**
    * The actual slashes and Xs showing for the current view.
    */
    public String PatternShowing = new String();
    /**
    * This is a datarow that stores most of the info necessary to draw appt.  It comes from the table obtained in Appointments.GetPeriodApptsTable().
    */
    public DataRow DataRoww = new DataRow();
    /**
    * This table contains all appointment fields for all appointments in the period. It's obtained in Appointments.GetApptFields().
    */
    public DataTable TableApptFields = new DataTable();
    /**
    * This table contains all appointment fields for all appointments in the period. It's obtained in Appointments.GetApptFields().
    */
    public DataTable TablePatFields = new DataTable();
    /**
    * Indicator that account has procedures with no ins claim
    */
    public boolean FamHasInsNotSent = new boolean();
    /**
    * Will show the highlight around the edges.  For now, this is only used for pinboard.  The ordinary selected appt is set with SelectedAptNum.
    */
    public boolean IsSelected = new boolean();
    /**
    * 
    */
    public ContrApptSingle() throws Exception {
        initializeComponent();
    }

    // This call is required by the Windows.Forms Form Designer.
    //Info=new InfoApt();
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
            }
             
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        //
        // ContrApptSingle
        //
        this.Name = "ContrApptSingle";
        this.Size = new System.Drawing.Size(85, 72);
        this.Load += new System.EventHandler(this.ContrApptSingle_Load);
        this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.ContrApptSingle_MouseDown);
    }

    /**
    * 
    */
    protected void onPaint(PaintEventArgs pea) throws Exception {
    }

    /**
    * It is planned to move some of this logic to OnPaint and use a true double buffer.
    */
    public void createShadow() throws Exception {
        if (this.Parent instanceof OpenDental.ContrApptSheet)
        {
            boolean isVisible = false;
            for (int j = 0;j < ApptDrawing.VisOps.Count;j++)
            {
                if (DataRoww["Op"].ToString() == ApptDrawing.VisOps[j].OperatoryNum.ToString())
                {
                    isVisible = true;
                }
                 
            }
            if (!isVisible)
            {
                return ;
            }
             
        }
         
        if (Shadow != null)
        {
            Shadow.Dispose();
            Shadow = null;
        }
         
        if (Width < 4)
        {
            return ;
        }
         
        if (Height < 4)
        {
            return ;
        }
         
        Shadow = new Bitmap(Width, Height);
        if (PatternShowing == null)
        {
            PatternShowing = "";
        }
         
        Graphics g = Graphics.FromImage(Shadow);
        try
        {
            {
                ApptSingleDrawing.DrawEntireAppt(g, DataRoww, PatternShowing, Width, Height, IsSelected, ThisIsPinBoard, SelectedAptNum, ApptViewItemL.ApptRows, ApptViewItemL.ApptViewCur, TableApptFields, TablePatFields, 8, false);
                this.BackgroundImage = Shadow;
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    private void contrApptSingle_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void contrApptSingle_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        ClickedAptNum = PIn.Long(DataRoww["AptNum"].ToString());
    }

}


//end class
//end namespace