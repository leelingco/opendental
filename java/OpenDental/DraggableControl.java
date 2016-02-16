//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;



/**
* Derive from this class when you want to drag a control around it's parent.
*/
public class DraggableControl  extends UserControl 
{

    private boolean __AllowDragging = new boolean();
    public boolean getAllowDragging() {
        return __AllowDragging;
    }

    public void setAllowDragging(boolean value) {
        __AllowDragging = value;
    }

    /**
    * Indicates if this control is currently being dragged
    */
    private boolean IsDragging = new boolean();
    /**
    * Indicates if mouse is currently down on this control.
    */
    private boolean IsMouseDown = new boolean();
    /**
    * The current location of the mouse when control is being dragged
    */
    private Point MouseLocationOnMouseDownStart = new Point();
    /**
    * Event thrown when a drag event is complete. User was dragging but then picked the left mouse up or mouse left the control.
    */
    public EventHandler DragDone = new EventHandler();
    public DraggableControl() throws Exception {
        initializeComponent();
    }

    private void userControlDraggable_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if (!getAllowDragging())
        {
            return ;
        }
         
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        //set the mouse down flags
        MouseLocationOnMouseDownStart = e.Location;
        IsMouseDown = true;
    }

    private void userControlDraggable_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (!getAllowDragging())
        {
            return ;
        }
         
        if (sender == null)
        {
            return ;
        }
         
        Control control = (Control)sender;
        if (IsMouseDown)
        {
            //move the control as far as we have moved the mouse
            IsDragging = true;
            //indicate that we are now dragging
            //move this control the same distance as we just moved the mouse
            control.Left += (e.X - MouseLocationOnMouseDownStart.X);
            control.Top += (e.Y - MouseLocationOnMouseDownStart.Y);
        }
         
    }

    private void userControlDraggable_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (!getAllowDragging())
        {
            return ;
        }
         
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        if (IsDragging && DragDone != null)
        {
            //send the event if we just finished dragging
            DragDone(this, new EventArgs());
        }
         
        //reset the flags
        IsDragging = false;
        IsMouseDown = false;
    }

    private void userControlDraggable_MouseLeave(Object sender, EventArgs e) throws Exception {
        if (!getAllowDragging())
        {
            return ;
        }
         
        if (IsDragging && DragDone != null)
        {
            //send the event if we were dragging when the mouse left
            DragDone(this, new EventArgs());
        }
         
        //reset the flags
        IsDragging = false;
        IsMouseDown = false;
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
        this.SuspendLayout();
        //
        // UserControlDraggable
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Name = "UserControlDraggable";
        this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.UserControlDraggable_MouseDown);
        this.MouseLeave += new System.EventHandler(this.UserControlDraggable_MouseLeave);
        this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.UserControlDraggable_MouseMove);
        this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.UserControlDraggable_MouseUp);
        this.ResumeLayout(false);
    }

}


