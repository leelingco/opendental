//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.ButtonClickedEventHandler;
import OpenDental.OutlookButton;

public class OutlookBar  extends System.Windows.Forms.Control 
{
    private System.ComponentModel.Container components = null;
    public OutlookButton[] Buttons = new OutlookButton[]();
    private ImageList imageList = new ImageList();
    public int SelectedIndex = -1;
    private int currentHot = -1;
    private Font textFont = new Font("Arial", 8);
    public ButtonClickedEventHandler ButtonClicked = null;
    private int previousSelected = new int();
    public OutlookBar() throws Exception {
        initializeComponent();
        Buttons = new OutlookButton[7];
        Buttons[0] = new OutlookButton("Appts",0);
        Buttons[1] = new OutlookButton("Family",1);
        Buttons[2] = new OutlookButton("Account",2);
        Buttons[3] = new OutlookButton("Treat' Plan",3);
        Buttons[4] = new OutlookButton("Chart",4);
        Buttons[5] = new OutlookButton("Images",5);
        Buttons[6] = new OutlookButton("Manage",6);
        updateAll();
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
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

    /**
    * 
    */
    public ImageList getImageList() throws Exception {
        return imageList;
    }

    public void setImageList(ImageList value) throws Exception {
        imageList = value;
    }

    /**
    * Triggered every time the control decides to repaint itself.
    *  @param pe
    */
    protected void onPaint(PaintEventArgs pe) throws Exception {
        calculateButtonInfo();
        boolean isHot = new boolean();
        boolean isSelected = new boolean();
        boolean isPressed = new boolean();
        pe.Graphics.DrawLine(Pens.Gray, Width - 1, 0, Width - 1, Height - 1);
        for (int i = 0;i < Buttons.Length;i++)
        {
            Point mouseLoc = this.PointToClient(Control.MousePosition);
            isHot = Buttons[i].Bounds.Contains(mouseLoc);
            if (Control.MouseButtons == MouseButtons.Left && isHot)
                isPressed = true;
            else
                isPressed = false; 
            if (i == SelectedIndex)
                isSelected = true;
            else
                isSelected = false; 
            DrawButton(Buttons[i], isHot, isPressed, isSelected);
        }
        // Calling the base class OnPaint
        super.OnPaint(pe);
    }

    /**
    * Draws one button using the info specified.
    *  @param myButton Contains caption, image and bounds info.
    *  @param isHot Is the mouse currently hovering over this button.
    *  @param isPressed Is the left mouse button currently down on this button.
    *  @param isSelected Is this the currently selected button
    */
    private void drawButton(OutlookButton myButton, boolean isHot, boolean isPressed, boolean isSelected) throws Exception {
        Graphics g = this.CreateGraphics();
        Color hotColor = Color.FromArgb(235, 235, 235);
        //.FromArgb(210,218,232);
        Color pressedColor = Color.FromArgb(210, 210, 210);
        //(182,193,214);
        Color selectedColor = Color.White;
        Color outlineColor = Color.FromArgb(28, 81, 128);
        //Color.Gray;
        SolidBrush textBrush = new SolidBrush(Color.Black);
        SolidBrush backgBrush = new SolidBrush(SystemColors.Control);
        StringFormat format = new StringFormat();
        format.Alignment = StringAlignment.Center;
        if (!myButton.Visible)
        {
            g.FillRectangle(backgBrush, myButton.Bounds.X, myButton.Bounds.Y, myButton.Bounds.Width + 1, myButton.Bounds.Height + 1);
            g.Dispose();
            return ;
        }
         
        if (isPressed)
        {
            g.FillRectangle(new SolidBrush(pressedColor), myButton.Bounds.X, myButton.Bounds.Y, myButton.Bounds.Width + 1, myButton.Bounds.Height + 1);
        }
        else if (isSelected)
        {
            g.FillRectangle(new SolidBrush(Color.White), myButton.Bounds.X, myButton.Bounds.Y, myButton.Bounds.Width + 1, myButton.Bounds.Height + 1);
            Rectangle gradientRect = new Rectangle(myButton.Bounds.X, myButton.Bounds.Y + myButton.Bounds.Height - 10, myButton.Bounds.Width, 10);
            LinearGradientBrush hotBrush = new LinearGradientBrush(gradientRect, Color.White, pressedColor, LinearGradientMode.Vertical);
            g.FillRectangle(hotBrush, myButton.Bounds.X, myButton.Bounds.Y + myButton.Bounds.Height - 10, myButton.Bounds.Width + 1, 10);
        }
        else if (isHot)
        {
            g.FillRectangle(new SolidBrush(hotColor), myButton.Bounds.X, myButton.Bounds.Y, myButton.Bounds.Width + 1, myButton.Bounds.Height + 1);
        }
        else
        {
            g.FillRectangle(new SolidBrush(SystemColors.Control), myButton.Bounds.X, myButton.Bounds.Y, myButton.Bounds.Width + 1, myButton.Bounds.Height + 1);
        }   
        //outline
        if (isPressed || isSelected || isHot)
        {
            //block out the corners so they won't show.  This can be improved later.
            g.FillPolygon(backgBrush, new Point[]{ new Point(myButton.Bounds.X, myButton.Bounds.Y), new Point(myButton.Bounds.X + 3, myButton.Bounds.Y), new Point(myButton.Bounds.X, myButton.Bounds.Y + 3) });
            //it's one pixel to the right because of the way rect drawn.
            g.FillPolygon(backgBrush, new Point[]{ new Point(myButton.Bounds.X + myButton.Bounds.Width - 2, myButton.Bounds.Y), new Point(myButton.Bounds.X + myButton.Bounds.Width + 1, myButton.Bounds.Y), new Point(myButton.Bounds.X + myButton.Bounds.Width + 1, myButton.Bounds.Y + 3) });
            //it's one pixel down and right.
            g.FillPolygon(backgBrush, new Point[]{ new Point(myButton.Bounds.X + myButton.Bounds.Width + 1, myButton.Bounds.Y + myButton.Bounds.Height - 3), new Point(myButton.Bounds.X + myButton.Bounds.Width + 1, myButton.Bounds.Y + myButton.Bounds.Height + 1), new Point(myButton.Bounds.X + myButton.Bounds.Width - 3, myButton.Bounds.Y + myButton.Bounds.Height + 1) });
            //it's one pixel down
            g.FillPolygon(backgBrush, new Point[]{ new Point(myButton.Bounds.X, myButton.Bounds.Y + myButton.Bounds.Height - 3), new Point(myButton.Bounds.X + 3, myButton.Bounds.Y + myButton.Bounds.Height + 1), new Point(myButton.Bounds.X, myButton.Bounds.Y + myButton.Bounds.Height + 1) });
            //g.FillRectangle(backgBrush,myButton.Bounds.X,myButton.Bounds.Y,2,2);
            //g.FillRectangle(backgBrush,myButton.Bounds.X+,myButton.Bounds.Y,2,2);
            //then draw outline
            drawRoundedRectangle(g,new Pen(outlineColor, 1),myButton.Bounds,4);
        }
         
        //Image
        Rectangle imgRect = new Rectangle((this.Width - 32) / 2, myButton.Bounds.Y + 3, 32, 32);
        if (myButton.ImageIndex > -1 && this.getImageList() != null && myButton.ImageIndex < this.getImageList().Images.Count)
        {
            g.DrawImage(getImageList().Images[myButton.ImageIndex], imgRect);
        }
         
        //Text
        Rectangle textRect = new Rectangle(myButton.Bounds.X - 1, imgRect.Bottom + 3, myButton.Bounds.Width + 2, myButton.Bounds.Bottom - imgRect.Bottom + 3);
        g.DrawString(myButton.Caption, textFont, textBrush, textRect, format);
        g.Dispose();
    }

    /**
    * Draws a rectangle with rounded edges.
    *  @param grfx The System.Drawing.Graphics object to be used to draw the rectangle.
    *  @param pen A System.Drawing.Pen object that determines the color, width, and style of the rectangle.
    *  @param rect A System.Drawing.Rectangle structure that represents the rectangle to draw.
    *  @param round Determines the radius of the corners.
    */
    public static void drawRoundedRectangle(Graphics grfx, Pen pen, Rectangle rect, int round) throws Exception {
        SmoothingMode oldSmoothingMode = grfx.SmoothingMode;
        grfx.SmoothingMode = SmoothingMode.AntiAlias;
        //top,right
        grfx.DrawLine(pen, rect.Left + round, rect.Top, rect.Right - round, rect.Top);
        grfx.DrawArc(pen, rect.Right - round * 2, rect.Top, round * 2, round * 2, 270, 90);
        //right,bottom
        grfx.DrawLine(pen, rect.Right, rect.Top + round, rect.Right, rect.Bottom - round);
        grfx.DrawArc(pen, rect.Right - round * 2, rect.Bottom - round * 2, round * 2, round * 2, 0, 90);
        //bottom,left
        grfx.DrawLine(pen, rect.Right - round, rect.Bottom, rect.Left + round, rect.Bottom);
        grfx.DrawArc(pen, rect.Left, rect.Bottom - round * 2, round * 2, round * 2, 90, 90);
        //left,top
        grfx.DrawLine(pen, rect.Left, rect.Bottom - round, rect.Left, rect.Top + round);
        grfx.DrawArc(pen, rect.Left, rect.Top, round * 2, round * 2, 180, 90);
        //
        grfx.SmoothingMode = oldSmoothingMode;
    }

    public void updateAll() throws Exception {
        // Calculates Button info and redraws all.
        //if(!m_BeginUpdate){
        calculateButtonInfo();
        this.Invalidate();
    }

    //}
    private void calculateButtonInfo() throws Exception {
        // Calculates button sizes and maybe more later
        //int barTop = 1;
        Graphics g = this.CreateGraphics();
        try
        {
            {
                int top = 0;
                int width = this.Width - 2;
                int textHeight = 0;
                for (int i = 0;i < Buttons.Length;i++)
                {
                    //--- Look if multiline text, if is add extra Height to button.
                    SizeF textSize = g.MeasureString(Buttons[i].Caption, textFont, width + 2);
                    textHeight = (int)(Math.Ceiling(textSize.Height));
                    if (textHeight < 26)
                        textHeight = 26;
                     
                    //default to height of 2 lines of text for uniformity.
                    Buttons[i].Bounds = new Rectangle(0, top, width, 39 + textHeight);
                    top += 39 + textHeight + 1;
                }
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    //for
    //using
    /**
    * 
    */
    protected void onMouseMove(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        //Graphics g=this.CreateGraphics();
        int hotBut = getButtonI(new Point(e.X, e.Y));
        if (hotBut != currentHot)
        {
            if (currentHot != -1)
            {
                //undraw previous button
                DrawButton(Buttons[currentHot], false, false, currentHot == SelectedIndex);
            }
             
            if (hotBut != -1)
            {
                //then draw current hot
                DrawButton(Buttons[hotBut], true, Control.MouseButtons == MouseButtons.Left, hotBut == SelectedIndex);
            }
             
            currentHot = hotBut;
        }
         
    }

    /**
    * 
    */
    protected void onMouseLeave(System.EventArgs e) throws Exception {
        super.OnMouseLeave(e);
        //Graphics g=this.CreateGraphics();
        if (currentHot != -1)
        {
            //undraw previous button
            DrawButton(Buttons[currentHot], false, false, currentHot == SelectedIndex);
        }
         
        currentHot = -1;
    }

    /**
    * 
    */
    protected void onSizeChanged(System.EventArgs e) throws Exception {
        super.OnSizeChanged(e);
        this.updateAll();
    }

    private int getButtonI(Point myPoint) throws Exception {
        for (int i = 0;i < Buttons.Length;i++)
        {
            //Item item = activeBar.Items[it];
            if (Buttons[i].Bounds.Contains(myPoint))
            {
                return i;
            }
             
        }
        return -1;
    }

    //for
    /**
    * 
    */
    protected void onMouseDown(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        //Graphics g=this.CreateGraphics();
        if (currentHot != -1)
        {
            //redraw current button to give feedback on mouse down.
            DrawButton(Buttons[currentHot], false, true, currentHot == SelectedIndex);
        }
         
    }

    /**
    * 
    */
    protected void onMouseUp(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseUp(e);
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        int selectedBut = getButtonI(new Point(e.X, e.Y));
        if (selectedBut == -1)
        {
            return ;
        }
         
        if (!Buttons[selectedBut].Visible)
        {
            return ;
        }
         
        int oldSelected = SelectedIndex;
        this.previousSelected = SelectedIndex;
        SelectedIndex = selectedBut;
        if (SelectedIndex != oldSelected && oldSelected != -1)
        {
            //undraw old selected
            DrawButton(Buttons[oldSelected], false, false, false);
        }
         
        DrawButton(Buttons[SelectedIndex], true, false, true);
        OnButtonClicked(Buttons[SelectedIndex], false);
    }

    /**
    * 
    */
    protected void onButtonClicked(OutlookButton myButton, boolean myCancel) throws Exception {
        if (this.ButtonClicked != null)
        {
            //previousSelected=SelectedIndex;
            OpenDental.ButtonClicked_EventArgs oArgs = new OpenDental.ButtonClicked_EventArgs(myButton,myCancel);
            this.ButtonClicked.invoke(this,oArgs);
            if (oArgs.getCancel())
            {
                SelectedIndex = previousSelected;
                Invalidate();
            }
             
        }
         
    }

}


