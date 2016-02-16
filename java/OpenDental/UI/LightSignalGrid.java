//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.UI.SignalButtonState;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefs;
import OpenDentBusiness.Signalod;

public class LightSignalGrid  extends Control 
{

    private SignalButtonState[] sigButStates = new SignalButtonState[]();
    private int buttonH = new int();
    private boolean mouseIsDown = new boolean();
    private int hotButton = new int();
    public OpenDental.UI.ODLightSignalGridClickEventHandler ButtonClick = null;
    public LightSignalGrid() throws Exception {
        initializeComponent();
        buttonH = 25;
        hotButton = -1;
    }

    protected Size getDefaultSize() throws Exception {
        return new Size(50, 300);
    }

    protected void onPaintBackground(PaintEventArgs pea) throws Exception {
    }

    protected void onPaint(PaintEventArgs e) throws Exception {
        if (Height == 0 || sigButStates == null)
        {
            e.Graphics.FillRectangle(new SolidBrush(Color.White), 0, 0, Width, Height);
            super.OnPaint(e);
            return ;
        }
         
        Bitmap doubleBuffer = new Bitmap(Width, Height, e.Graphics);
        Graphics g = Graphics.FromImage(doubleBuffer);
        //button backgrounds
        Color mixedC = new Color();
        Color baseC = new Color();
        int R = new int();
        int G = new int();
        int B = new int();
        for (int i = 0;i < sigButStates.Length;i++)
        {
            baseC = sigButStates[i].CurrentColor;
            State __dummyScrutVar0 = sigButStates[i].State;
            //Control is 224,223,227
            if (__dummyScrutVar0.equals(OpenDental.UI.ToolBarButtonState.Normal) || __dummyScrutVar0.equals(OpenDental.UI.ToolBarButtonState.Pressed))
            {
                g.FillRectangle(new SolidBrush(baseC), 0, i * buttonH, Width, buttonH);
            }
            else if (__dummyScrutVar0.equals(OpenDental.UI.ToolBarButtonState.Hover))
            {
                //this is darker
                //if(baseC.ToArgb()==Color.White.ToArgb()){
                //	mixedC=Color.FromArgb(220,220,220);
                //}
                //else{
                R = baseC.R - 40;
                if (R < 0)
                {
                    R = 0;
                }
                 
                G = baseC.G - 40;
                if (G < 0)
                {
                    G = 0;
                }
                 
                B = baseC.B - 40;
                if (B < 0)
                {
                    B = 0;
                }
                 
                mixedC = Color.FromArgb(R, G, B);
                //}
                g.FillRectangle(new SolidBrush(mixedC), 0, i * buttonH, Width, buttonH);
            }
              
        }
        /*case ToolBarButtonState.Pressed://darker
        						if(baseC.ToArgb()==Color.White.ToArgb()){
        							mixedC=Color.FromArgb(190,190,190);
        						}
        						else{
        							R=baseC.R+50;
        							if(R>255) {
        								R=255;
        							}
        							G=baseC.G+50;
        							if(G>255) {
        								G=255;
        							}
        							B=baseC.B+50;
        							if(B>255) {
        								B=255;
        							}
        							mixedC=Color.FromArgb(R,G,B);
        						}
        						g.FillRectangle(new SolidBrush(mixedC),0,i*buttonH,Width,buttonH);
        						break;*/
        //g.FillRectangle(new SolidBrush(sigButStates[i].CurrentColor),0,i*buttonH,Width,buttonH);
        //grid
        Pen gridPen = new Pen(Color.DarkGray);
        g.DrawRectangle(gridPen, 0, 0, Width - 1, Height - 1);
        for (int i = 0;i < sigButStates.Length;i++)
        {
            g.DrawLine(gridPen, 0, i * buttonH, Width, i * buttonH);
        }
        //button text
        RectangleF rect = new RectangleF();
        StringFormat format = new StringFormat();
        format.Alignment = StringAlignment.Center;
        format.LineAlignment = StringAlignment.Center;
        for (int i = 0;i < sigButStates.Length;i++)
        {
            rect = new RectangleF(-2, i * buttonH, Width + 4, buttonH);
            g.DrawString(sigButStates[i].Text, Font, Brushes.Black, rect, format);
        }
        e.Graphics.DrawImageUnscaled(doubleBuffer, 0, 0);
        g.Dispose();
        super.OnPaint(e);
    }

    //private void DrawBackground(Graphics g){
    //	g.DrawRectangle()
    //}
    /**
    * This will clear the buttons, reset buttons to the specified list, reset the buttonstates, layout the rows, and invalidate for repaint.
    */
    public void setButtons(SigButDef[] butDefs) throws Exception {
        if (butDefs.Length == 0)
        {
            sigButStates = new SignalButtonState[0];
            layoutButtons();
            Invalidate();
            return ;
        }
         
        //since defs are ordered by buttonIndex, the last def will contain the max number of buttons
        sigButStates = new SignalButtonState[butDefs[butDefs.Length - 1].ButtonIndex + 1];
        for (int i = 0;i < sigButStates.Length;i++)
        {
            sigButStates[i] = new SignalButtonState();
            sigButStates[i].ButDef = SigButDefs.GetByIndex(i, butDefs);
            //might be null
            if (sigButStates[i].ButDef != null)
            {
                sigButStates[i].Text = sigButStates[i].ButDef.ButtonText;
            }
             
            sigButStates[i].CurrentColor = Color.White;
        }
        layoutButtons();
        Invalidate();
    }

    private void layoutButtons() throws Exception {
        if (sigButStates.Length == 0)
        {
            Height = 0;
            return ;
        }
         
        Height = sigButStates.Length * buttonH + 1;
    }

    /**
    * Sets the specified buttonIndex to a color and attaches the signal responsible.  This is also used for the manual ack to increase responsiveness.  buttonIndex is 0-based.
    */
    public void setButtonActive(int buttonIndex, Color color, Signalod activeSignal) throws Exception {
        if (buttonIndex >= sigButStates.Length)
        {
            return ;
        }
         
        //no button to light up.
        sigButStates[buttonIndex].CurrentColor = color;
        if (activeSignal == null)
        {
            sigButStates[buttonIndex].ActiveSignal = null;
        }
        else
        {
            sigButStates[buttonIndex].ActiveSignal = activeSignal.copy();
        } 
        Invalidate();
    }

    /**
    * An ack coming from the database.  If it applies to any lights currently showing, then those lights will be turned off.  Returns the 0-based index of the light acked, or -1.
    */
    public int processAck(long signalNum) throws Exception {
        for (int i = 0;i < sigButStates.Length;i++)
        {
            if (sigButStates[i].ActiveSignal == null)
            {
                continue;
            }
             
            if (sigButStates[i].ActiveSignal.SignalNum == signalNum)
            {
                sigButStates[i].CurrentColor = Color.White;
                sigButStates[i].ActiveSignal = null;
                Invalidate();
                return i;
            }
             
        }
        return -1;
    }

    /**
    * This should only happen when mouse enters. Only causes a repaint if needed.
    */
    protected void onMouseMove(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        if (mouseIsDown)
        {
        }
        else
        {
            //regardless of whether a button is hot, nothing changes until the mouse is released.
            //a hot(pressed) button remains so, and no buttons are hot when hover
            //,so do nothing
            //mouse is not down
            int button = HitTest(e.X, e.Y);
            //this is the button the mouse is over at the moment.
            //first handle the old hotbutton
            if (hotButton != -1)
            {
                //if there is a previous hotbutton
                if (hotButton != button)
                {
                    //if we have moved to hover over a new button, or to hover over nothing
                    sigButStates[hotButton].State = OpenDental.UI.ToolBarButtonState.Normal;
                    Invalidate();
                }
                 
            }
             
            //hotButton.Bounds);
            //then, the new button
            if (button != -1)
            {
                if (hotButton != button)
                {
                    //if we have moved to hover over a new button
                    //toolTip1.SetToolTip(this,button.ToolTipText);
                    sigButStates[button].State = OpenDental.UI.ToolBarButtonState.Hover;
                    Invalidate();
                }
                else
                {
                } 
            }
            else
            {
            } 
            //button.Bounds);
            //Still hovering over the same button as before
            //do nothing.
            //toolTip1.SetToolTip(this,"");
            hotButton = button;
        } 
    }

    //this might be -1 if hovering over nothing.
    //if there was no previous hotbutton, and there is not current hotbutton, then do nothing.
    /**
    * Returns the 0-based button index that contains these coordinates, or -1 if no hit.
    */
    private int hitTest(int x, int y) throws Exception {
        for (int i = 0;i < 20;i++)
        {
            if (y < buttonH * i)
            {
                continue;
            }
             
            if (y > buttonH * (i + 1))
            {
                continue;
            }
             
            if (i > sigButStates.Length - 1)
            {
                return -1;
            }
             
            return i;
        }
        return -1;
    }

    //button not visible
    /**
    * Resets button appearance. This will also deactivate the button if it has been pressed but not released. A pressed button will still be hot, however, so that if the mouse enters again, it will behave properly.  Repaints only if necessary.
    */
    protected void onMouseLeave(System.EventArgs e) throws Exception {
        super.OnMouseLeave(e);
        if (mouseIsDown)
        {
        }
        else
        {
            //mouse is down
            //if a button is hot, it will remain so, even if leave.  As long as mouse is down.
            //,so do nothing.
            //Also, if a button is not hot, nothing will happen when leave
            //,so do nothing.
            //mouse is not down
            if (hotButton != -1)
            {
                //if there was a previous hotButton
                sigButStates[hotButton].State = OpenDental.UI.ToolBarButtonState.Normal;
                Invalidate();
                //hotButton.Bounds);
                hotButton = -1;
            }
             
        } 
    }

    /**
    * Change the button to a pressed state.
    */
    protected void onMouseDown(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        if ((e.Button & MouseButtons.Left) != MouseButtons.Left)
        {
            return ;
        }
         
        mouseIsDown = true;
        int button = HitTest(e.X, e.Y);
        if (button == -1)
        {
            return ;
        }
         
        //if there is no current hover button
        //don't set a hotButton
        //if(!button.Enabled){
        //	return;//disabled buttons don't respond
        //}
        hotButton = button;
        //if(button.Style==ODToolBarButtonStyle.DropDownButton
        //	&& HitTestDrop(button,e.X,e.Y)) {
        //	button.State=ToolBarButtonState.DropPressed;
        //}
        //else {
        sigButStates[button].State = OpenDental.UI.ToolBarButtonState.Pressed;
        //}
        Invalidate();
    }

    //button.Bounds);
    /**
    * Change button to hover state and repaint if needed.
    */
    protected void onMouseUp(System.Windows.Forms.MouseEventArgs e) throws Exception {
        super.OnMouseUp(e);
        if ((e.Button & MouseButtons.Left) != MouseButtons.Left)
        {
            return ;
        }
         
        mouseIsDown = false;
        int button = HitTest(e.X, e.Y);
        if (hotButton == -1)
        {
        }
        else
        {
            //if there was not a previous hotButton
            //do nothing
            //there was a hotButton
            sigButStates[hotButton].State = OpenDental.UI.ToolBarButtonState.Normal;
            //but can't set it null yet, because still need it for testing
            Invalidate();
            //hotButton.Bounds);//invalidate the old button
            //CLICK:
            if (hotButton == button)
            {
                //if mouse was released over the same button as it was depressed
                OnButtonClicked(button, sigButStates[button].ButDef, sigButStates[button].ActiveSignal);
                return ;
            }
            else
            {
                //the button will not revert back to hover
                //end of click section
                //there was a hot button, but it did not turn into a click
                hotButton = -1;
            } 
        } 
        if (button != -1)
        {
            //no click, and now there is a hover button, not the same as original button.
            //this section could easily be deleted, since all the user has to do is move the mouse slightly.
            sigButStates[button].State = OpenDental.UI.ToolBarButtonState.Hover;
            hotButton = button;
            //set the current hover button to be the new hotbutton
            Invalidate();
        }
         
    }

    //button.Bounds);
    /**
    * 
    */
    protected void onButtonClicked(int myButton, SigButDef myDef, Signalod mySignal) throws Exception {
        OpenDental.UI.ODLightSignalGridClickEventArgs bArgs = new OpenDental.UI.ODLightSignalGridClickEventArgs(myButton,myDef,mySignal);
        if (ButtonClick != null)
            ButtonClick.invoke(this,bArgs);
         
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


