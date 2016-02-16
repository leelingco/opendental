//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;


public class ContrWindowingSlider  extends Control 
{

    private int minVal = 64;
    private int maxVal = 192;
    private float endW = 7;
    //the width of the end sliders
    private float tick = new float();
    private OpenDental.UI.Button.ControlState butStateM = OpenDental.UI.Button.ControlState.Normal;
    private OpenDental.UI.Button.ControlState butStateL = OpenDental.UI.Button.ControlState.Normal;
    private OpenDental.UI.Button.ControlState butStateR = OpenDental.UI.Button.ControlState.Normal;
    private boolean mouseIsDown = new boolean();
    private int mouseDownX = new int();
    /**
    * The original pixel position of the button in question. The pointy part.
    */
    private float originalPixL = new float();
    private float originalPixR = new float();
    public EventHandler Scroll = null;
    public EventHandler ScrollComplete = null;
    public ContrWindowingSlider() throws Exception {
        initializeComponent();
        this.DoubleBuffered = true;
    }

    /**
    * 
    */
    public int getMinVal() throws Exception {
        if (!Enabled)
        {
            return 0;
        }
         
        return minVal;
    }

    public void setMinVal(int value) throws Exception {
        minVal = value;
        Invalidate();
    }

    /**
    * 
    */
    public int getMaxVal() throws Exception {
        if (!Enabled)
        {
            return 255;
        }
         
        return maxVal;
    }

    public void setMaxVal(int value) throws Exception {
        maxVal = value;
        Invalidate();
    }

    protected Size getDefaultSize() throws Exception {
        return new Size(194, 14);
    }

    protected void onPaint(PaintEventArgs pe) throws Exception {
        Graphics g = pe.Graphics;
        g.SmoothingMode = SmoothingMode.HighQuality;
        SolidBrush brush = new SolidBrush(SystemColors.Control);
        g.FillRectangle(brush, 0, 0, Width, Height);
        tick = (float)(Width - endW - 1) / 255f;
        //gets set in mousemove also
        if (butStateM == OpenDental.UI.Button.ControlState.Hover)
        {
            g.FillRectangle(Brushes.White, getRectMiddle());
        }
         
        g.DrawRectangle(new Pen(Color.FromArgb(28, 81, 128)), getRectMiddle());
        //gradient bars
        Rectangle rect = new Rectangle((int)(endW / 2), 2, getRectMiddle().Left - (int)(endW / 2), Height - 5);
        g.FillRectangle(Brushes.Black, rect);
        if (Enabled)
        {
            rect = new Rectangle(getRectMiddle().Left, 2, getRectMiddle().Width, Height - 5);
            LinearGradientBrush gradientBrush = new LinearGradientBrush(new Point(getRectMiddle().X + (int)(endW / 2), 0), new Point(getRectMiddle().Right - (int)(endW / 2) + 1, 0), Color.Black, Color.White);
            //new Point(0,0),new Point(rect.Right+1,0),
            g.FillRectangle(gradientBrush, rect);
        }
         
        //this one is just to eliminate a rounding artifact
        rect = new Rectangle(getRectMiddle().Right - (int)(endW / 2) + 1, 2, 2, Height - 5);
        g.FillRectangle(Brushes.White, rect);
        rect = new Rectangle(getRectMiddle().Right, 2, Width - (int)(endW / 2) - getRectMiddle().Right, Height - 5);
        g.FillRectangle(Brushes.White, rect);
        drawButton(g,getPathLeft(),butStateL);
        drawButton(g,getPathRight(),butStateR);
        super.OnPaint(pe);
    }

    /**
    * Gets the outline path of the middle button that connects the two ends. But it's partly tucked under the end buttons.
    */
    private Rectangle getRectMiddle() throws Exception {
        return new Rectangle((int)(endW / 2f + getMinVal() * tick), 0, (int)((getMaxVal() - getMinVal()) * tick), Height - 1);
    }

    //GraphicsPath path=new GraphicsPath();
    //path.AddRectangle(rect);
    //return path;
    /**
    * Gets the outline path of the left end button.
    */
    private GraphicsPath getPathLeft() throws Exception {
        GraphicsPath path = new GraphicsPath();
        //start at lower left, work clockwise
        path.AddLines(new PointF[]{ new PointF(getMinVal() * tick, Height - 4), new PointF(getMinVal() * tick, 3), new PointF(getMinVal() * tick + endW / 2f, 0), new PointF(getMinVal() * tick + endW, 3), new PointF(getMinVal() * tick + endW, Height - 4), new PointF(getMinVal() * tick + endW / 2f, Height - 1), new PointF(getMinVal() * tick, Height - 4) });
        return path;
    }

    /**
    * Gets the outline path of the right end button.
    */
    private GraphicsPath getPathRight() throws Exception {
        GraphicsPath path = new GraphicsPath();
        //start at lower left, work clockwise
        path.AddLines(new PointF[]{ new PointF(getMaxVal() * tick, Height - 4), new PointF(getMaxVal() * tick, 3), new PointF(getMaxVal() * tick + endW / 2f, 0), new PointF(getMaxVal() * tick + endW, 3), new PointF(getMaxVal() * tick + endW, Height - 4), new PointF(getMaxVal() * tick + endW / 2f, Height - 1), new PointF(getMaxVal() * tick, Height - 4) });
        return path;
    }

    private void drawButton(Graphics g, GraphicsPath pathmain, OpenDental.UI.Button.ControlState state) throws Exception {
        Color clrMain = Color.FromArgb(200, 202, 220);
        if (state == OpenDental.UI.Button.ControlState.Hover)
        {
            clrMain = Color.FromArgb(240, 240, 255);
        }
        else if (state == OpenDental.UI.Button.ControlState.Pressed)
        {
            clrMain = Color.FromArgb(150, 150, 160);
        }
          
        GraphicsPath pathsub = new GraphicsPath();
        RectangleF rect = pathmain.GetBounds();
        pathsub.AddEllipse(rect.Left - rect.Width / 8f, rect.Top - rect.Height / 2f, rect.Width, rect.Height * 3f / 2f);
        PathGradientBrush pathBrush = new PathGradientBrush(pathsub);
        pathBrush.CenterColor = Color.FromArgb(255, 255, 255, 255);
        pathBrush.SurroundColors = new Color[]{ Color.FromArgb(0, 255, 255, 255) };
        g.FillPath(new SolidBrush(clrMain), pathmain);
        g.FillPath(pathBrush, pathmain);
        Color clrDarkOverlay = Color.FromArgb(50, 125, 125, 125);
        LinearGradientBrush brush = new LinearGradientBrush(new PointF(rect.Left, rect.Bottom), new PointF(rect.Left, rect.Top + rect.Height / 2), Color.FromArgb(0, 0, 0, 0), Color.FromArgb(50, 0, 0, 0));
        g.FillPath(brush, pathmain);
        Pen outline = new Pen(Color.FromArgb(28, 81, 128));
        g.DrawPath(outline, pathmain);
    }

    /**
    * 
    */
    protected void onMouseMove(System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        super.OnMouseMove(e);
        tick = (float)(Width - endW - 1) / 255f;
        if (mouseIsDown)
        {
            int minAllowedL = 0;
            int maxAllowedL = new int();
            int minAllowedR = new int();
            int maxAllowedR = 255;
            float deltaPix = mouseDownX - e.X;
            if (butStateL == OpenDental.UI.Button.ControlState.Pressed)
            {
                setMinVal((int)((originalPixL - deltaPix - endW / 2f) / tick));
                maxAllowedL = getMaxVal() - (int)(endW / tick);
                if (getMinVal() < minAllowedL)
                {
                    setMinVal(minAllowedL);
                }
                else if (getMinVal() > maxAllowedL)
                {
                    setMinVal(maxAllowedL);
                }
                  
                onScroll();
            }
            else if (butStateR == OpenDental.UI.Button.ControlState.Pressed)
            {
                setMaxVal((int)((originalPixR - deltaPix - endW / 2f) / tick));
                minAllowedR = getMinVal() + (int)(endW / tick);
                if (getMaxVal() < minAllowedR)
                {
                    setMaxVal(minAllowedR);
                }
                else if (getMaxVal() > maxAllowedR)
                {
                    setMaxVal(maxAllowedR);
                }
                  
                onScroll();
            }
            else if (butStateM == OpenDental.UI.Button.ControlState.Pressed)
            {
                setMinVal((int)((originalPixL - deltaPix - endW / 2f) / tick));
                setMaxVal((int)((originalPixR - deltaPix - endW / 2f) / tick));
                int originalValSpan = (int)((originalPixR - originalPixL) / tick);
                maxAllowedL = maxAllowedR - originalValSpan;
                minAllowedR = minAllowedL + originalValSpan;
                if (getMinVal() < minAllowedL)
                {
                    setMinVal(minAllowedL);
                }
                else if (getMinVal() > maxAllowedL)
                {
                    setMinVal(maxAllowedL);
                }
                  
                if (getMaxVal() < minAllowedR)
                {
                    setMaxVal(minAllowedR);
                }
                else if (getMaxVal() > maxAllowedR)
                {
                    setMaxVal(maxAllowedR);
                }
                  
                onScroll();
            }
               
        }
        else
        {
            //mouse is not down
            butStateL = OpenDental.UI.Button.ControlState.Normal;
            butStateR = OpenDental.UI.Button.ControlState.Normal;
            butStateM = OpenDental.UI.Button.ControlState.Normal;
            if (getPathLeft().IsVisible(e.Location))
            {
                butStateL = OpenDental.UI.Button.ControlState.Hover;
            }
            else if (getPathRight().IsVisible(e.Location))
            {
                butStateR = OpenDental.UI.Button.ControlState.Hover;
            }
            else if (getRectMiddle().Contains(e.Location))
            {
                butStateM = OpenDental.UI.Button.ControlState.Hover;
            }
               
        } 
        Invalidate();
    }

    /**
    * Resets button appearance.  Repaints only if necessary.
    */
    protected void onMouseLeave(System.EventArgs e) throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        super.OnMouseLeave(e);
        if (mouseIsDown)
        {
        }
        else
        {
            //mouse is down
            //if a button is pressed, it will remain so, even if leave.  As long as mouse is down.
            //,so do nothing.
            //Also, if a button is not pressed, nothing will happen when leave
            //,so do nothing.
            //mouse is not down
            butStateL = OpenDental.UI.Button.ControlState.Normal;
            butStateR = OpenDental.UI.Button.ControlState.Normal;
            butStateM = OpenDental.UI.Button.ControlState.Normal;
            Invalidate();
        } 
    }

    /**
    * Change the button to a pressed state.
    */
    protected void onMouseDown(System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        super.OnMouseDown(e);
        if ((e.Button & MouseButtons.Left) != MouseButtons.Left)
        {
            return ;
        }
         
        mouseIsDown = true;
        mouseDownX = e.X;
        butStateL = OpenDental.UI.Button.ControlState.Normal;
        butStateR = OpenDental.UI.Button.ControlState.Normal;
        butStateM = OpenDental.UI.Button.ControlState.Normal;
        if (getPathLeft().IsVisible(e.Location))
        {
            //if mouse pressed within the left button
            butStateL = OpenDental.UI.Button.ControlState.Pressed;
            originalPixL = (float)getMinVal() * tick + endW / 2f;
        }
        else if (getPathRight().IsVisible(e.Location))
        {
            butStateR = OpenDental.UI.Button.ControlState.Pressed;
            originalPixR = (float)getMaxVal() * tick + endW / 2f;
        }
        else if (getRectMiddle().Contains(e.Location))
        {
            butStateM = OpenDental.UI.Button.ControlState.Pressed;
            originalPixL = (float)getMinVal() * tick + endW / 2f;
            originalPixR = (float)getMaxVal() * tick + endW / 2f;
        }
           
        Invalidate();
    }

    /**
    * Change button to hover state and repaint if needed.
    */
    protected void onMouseUp(System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        super.OnMouseUp(e);
        if ((e.Button & MouseButtons.Left) != MouseButtons.Left)
        {
            return ;
        }
         
        mouseIsDown = false;
        if (butStateL == OpenDental.UI.Button.ControlState.Pressed || butStateR == OpenDental.UI.Button.ControlState.Pressed || butStateM == OpenDental.UI.Button.ControlState.Pressed)
        {
            onScrollComplete();
        }
         
        butStateL = OpenDental.UI.Button.ControlState.Normal;
        butStateR = OpenDental.UI.Button.ControlState.Normal;
        butStateM = OpenDental.UI.Button.ControlState.Normal;
        if (getPathLeft().IsVisible(e.Location))
        {
            butStateL = OpenDental.UI.Button.ControlState.Hover;
        }
        else if (getPathRight().IsVisible(e.Location))
        {
            butStateR = OpenDental.UI.Button.ControlState.Hover;
        }
        else if (getRectMiddle().Contains(e.Location))
        {
            butStateM = OpenDental.UI.Button.ControlState.Hover;
        }
           
        Invalidate();
    }

    /**
    * 
    */
    protected void onScroll() throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        EventArgs ea = new EventArgs();
        if (Scroll != null)
        {
            Scroll(this, ea);
        }
         
    }

    /**
    * 
    */
    protected void onScrollComplete() throws Exception {
        if (!Enabled)
        {
            return ;
        }
         
        EventArgs ea = new EventArgs();
        if (ScrollComplete != null)
        {
            ScrollComplete(this, ea);
        }
         
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
        // ContrWindowingSlider
        //
        this.Size = new System.Drawing.Size(100, 50);
        this.ResumeLayout(false);
    }

}


