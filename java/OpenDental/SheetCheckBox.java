//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;


public class SheetCheckBox  extends Control 
{

    private boolean isChecked = new boolean();
    private Pen pen = new Pen();
    private boolean isHovering = new boolean();
    private PathGradientBrush hoverBrush = new PathGradientBrush();
    private Color surroundColor = new Color();
    public boolean getIsChecked() throws Exception {
        return isChecked;
    }

    public void setIsChecked(boolean value) throws Exception {
        isChecked = value;
        Invalidate();
    }

    public SheetCheckBox() throws Exception {
        initializeComponent();
        setBrushes();
    }

    protected void onSizeChanged(EventArgs e) throws Exception {
        super.OnSizeChanged(e);
        setBrushes();
    }

    private void setBrushes() throws Exception {
        pen = new Pen(Color.Black, 1.6f);
        hoverBrush = new PathGradientBrush(new Point[]{ new Point(0, 0), new Point(Width - 1, 0), new Point(Width - 1, Height - 1), new Point(0, Height - 1) });
        hoverBrush.CenterColor = Color.White;
        surroundColor = Color.FromArgb(249, 187, 67);
        hoverBrush.SurroundColors = new Color[]{ surroundColor, surroundColor, surroundColor, surroundColor };
        Blend blend = new Blend();
        float[] myFactors;
        float[] myPositions;
        blend.Factors = myFactors;
        blend.Positions = myPositions;
        hoverBrush.Blend = blend;
    }

    protected void onPaint(PaintEventArgs pe) throws Exception {
        super.OnPaint(pe);
        Graphics g = pe.Graphics;
        g.SmoothingMode = SmoothingMode.HighQuality;
        g.CompositingQuality = CompositingQuality.HighQuality;
        if (isHovering)
        {
            g.FillRectangle(hoverBrush, 0, 0, Width - 1, Height - 1);
            g.DrawRectangle(new Pen(surroundColor), 0, 0, Width - 1, Height - 1);
        }
         
        if (isChecked)
        {
            g.DrawLine(pen, 0, 0, Width - 1, Height - 1);
            g.DrawLine(pen, Width - 1, 0, 0, Height - 1);
        }
         
        g.Dispose();
    }

    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        setIsChecked(!getIsChecked());
    }

    protected void onMouseMove(MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        isHovering = true;
        Invalidate();
    }

    protected void onMouseLeave(EventArgs e) throws Exception {
        super.OnMouseLeave(e);
        isHovering = false;
        Invalidate();
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


