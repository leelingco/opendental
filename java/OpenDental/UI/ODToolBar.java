//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.UI.ODToolBarButtonCollection;

public class ODToolBar  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    private ODToolBarButtonCollection buttons = new ODToolBarButtonCollection();
    private ImageList imageList = new ImageList();
    private boolean mouseIsDown = new boolean();
    private OpenDental.UI.ODToolBarButton hotButton;
    private ToolTip toolTip1 = new ToolTip();
    public OpenDental.UI.ODToolBarButtonClickEventHandler ButtonClick = null;
    public ODToolBar() throws Exception {
        initializeComponent();
        toolTip1 = new ToolTip();
        toolTip1.InitialDelay = 1100;
    }

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
        this.Name = "ODToolBar";
        this.Load += new System.EventHandler(this.ODToolBar_Load);
    }

    private void oDToolBar_Load(Object sender, System.EventArgs e) throws Exception {
        if (imageList != null)
        {
            Height = imageList.ImageSize.Height + 3;
        }
         
    }

    /**
    * Gets the collection of ODToolBarButton controls assigned to the toolbar control.
    */
    public ODToolBarButtonCollection getButtons() throws Exception {
        return buttons;
    }

    //set{
    //}
    /**
    * Gets or sets the collection of images available to the toolbar buttons.
    */
    public ImageList getImageList() throws Exception {
        return imageList;
    }

    public void setImageList(ImageList value) throws Exception {
        imageList = value;
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
            OpenDental.UI.ODToolBarButton button = HitTest(e.X, e.Y);
            //this is the button the mouse is over at the moment.
            //first handle the old hotbutton
            if (hotButton != null)
            {
                //if there is a previous hotbutton
                if (hotButton != button)
                {
                    //if we have moved to hover over a new button, or to hover over nothing
                    hotButton.setState(OpenDental.UI.ToolBarButtonState.Normal);
                    Invalidate(hotButton.getBounds());
                }
                 
            }
             
            //then, the new button
            if (button != null)
            {
                if (hotButton != button)
                {
                    //if we have moved to hover over a new button
                    toolTip1.SetToolTip(this, button.getToolTipText());
                    button.setState(OpenDental.UI.ToolBarButtonState.Hover);
                    Invalidate(button.getBounds());
                }
                else
                {
                } 
            }
            else
            {
                //Still hovering over the same button as before
                //do nothing.
                toolTip1.SetToolTip(this, "");
            } 
            hotButton = button;
        } 
    }

    //this might be null if hovering over nothing.
    //if there was no previous hotbutton, and there is not current hotbutton, then do nothing.
    /**
    * Returns the button that contains these coordinates, or null if no hit.
    */
    private OpenDental.UI.ODToolBarButton hitTest(int x, int y) throws Exception {
        for (Object __dummyForeachVar0 : buttons)
        {
            OpenDental.UI.ODToolBarButton button = (OpenDental.UI.ODToolBarButton)__dummyForeachVar0;
            if (button.getBounds().Contains(x, y))
                return button;
             
        }
        return null;
    }

    private boolean hitTestDrop(OpenDental.UI.ODToolBarButton button, int x, int y) throws Exception {
        Rectangle dropRect = new Rectangle(button.getBounds().X + button.getBounds().Width - 15, button.getBounds().Y, 15, button.getBounds().Height);
        if (dropRect.Contains(x, y))
        {
            return true;
        }
         
        return false;
    }

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
            if (hotButton != null)
            {
                //if there was a previous hotButton
                hotButton.setState(OpenDental.UI.ToolBarButtonState.Normal);
                Invalidate(hotButton.getBounds());
                hotButton = null;
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
        OpenDental.UI.ODToolBarButton button = HitTest(e.X, e.Y);
        if (button == null)
        {
            return ;
        }
         
        //if there is no current hover button
        //don't set a hotButton
        //if(!button.Enabled){
        //	return;//disabled buttons don't respond
        //}
        hotButton = button;
        if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.DropDownButton && HitTestDrop(button, e.X, e.Y))
        {
            button.setState(OpenDental.UI.ToolBarButtonState.DropPressed);
        }
        else
        {
            button.setState(OpenDental.UI.ToolBarButtonState.Pressed);
        } 
        Invalidate(button.getBounds());
    }

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
        OpenDental.UI.ODToolBarButton button = HitTest(e.X, e.Y);
        if (hotButton == null)
        {
        }
        else
        {
            //if there was not a previous hotButton
            //do nothing
            //there was a hotButton
            hotButton.setState(OpenDental.UI.ToolBarButtonState.Normal);
            //but can't set it null yet, because still need it for testing
            Invalidate(hotButton.getBounds());
            //invalidate the old button
            //CLICK:
            if (hotButton == button)
            {
                //if mouse was released over the same button as it was depressed
                if (!button.getEnabled())
                {
                }
                else //disabled buttons don't respond at all
                //if current button is dropdown
                //and we are in the dropdown area on the right
                if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.DropDownButton && HitTestDrop(button, e.X, e.Y) && button.getDropDownMenu() != null)
                {
                    //and there is a dropdown menu to display
                    hotButton = null;
                    button.setState(OpenDental.UI.ToolBarButtonState.Normal);
                    Invalidate(button.getBounds());
                    button.getDropDownMenu().GetContextMenu().Show(this, new Point(button.getBounds().X, button.getBounds().Y + button.getBounds().Height));
                }
                else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.ToggleButton)
                {
                    //if current button is a toggle button
                    if (button.getPushed())
                        button.setPushed(false);
                    else
                        button.setPushed(true); 
                    onButtonClicked(button);
                }
                else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Label)
                {
                }
                else
                {
                    //lables do not respond with click
                    onButtonClicked(button);
                }    
                return ;
            }
            else
            {
                //the button will not revert back to hover
                //end of click section
                //there was a hot button, but it did not turn into a click
                hotButton = null;
            } 
        } 
        if (button != null)
        {
            //no click, and now there is a hover button, not the same as original button.
            //this section could easily be deleted, since all the user has to do is move the mouse slightly.
            button.setState(OpenDental.UI.ToolBarButtonState.Hover);
            hotButton = button;
            //set the current hover button to be the new hotbutton
            Invalidate(button.getBounds());
        }
         
    }

    /**
    * 
    */
    protected void onButtonClicked(OpenDental.UI.ODToolBarButton myButton) throws Exception {
        OpenDental.UI.ODToolBarButtonClickEventArgs bArgs = new OpenDental.UI.ODToolBarButtonClickEventArgs(myButton);
        if (ButtonClick != null)
        {
            ButtonClick.invoke(this,bArgs);
        }
         
    }

    private void recalculateButtonsizes(Graphics g) throws Exception {
        int xPos = 0;
        int height = new int();
        int width = new int();
        if (imageList == null)
        {
            height = (int)g.MeasureString("anystring", Font).Height + 10;
        }
        else
        {
            height = imageList.ImageSize.Height + 2;
        } 
        for (Object __dummyForeachVar1 : buttons)
        {
            OpenDental.UI.ODToolBarButton button = (OpenDental.UI.ODToolBarButton)__dummyForeachVar1;
            if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Separator)
            {
                width = 4;
            }
            else if (imageList == null || button.getImageIndex() == -1)
            {
                width = (int)g.MeasureString(button.getText(), Font).Width + 10;
            }
            else
            {
                if (StringSupport.equals(button.getText(), ""))
                    width = imageList.ImageSize.Width + 7;
                else
                    //slightly wider than high for better 'feel'
                    width = imageList.ImageSize.Width + (int)g.MeasureString(button.getText(), Font).Width + 12; 
            }  
            if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.DropDownButton)
            {
                if (StringSupport.equals(button.getText(), ""))
                {
                    width -= 10;
                }
                 
                //this way the main button part can be zero width if we just want the dropdown and nothing else.
                width += 15;
            }
             
            button.setBounds(new Rectangle(new Point(xPos, 0), new Size(width, height)));
            xPos += button.getBounds().Width;
        }
    }

    /**
    * Runs any time the control is invalidated.
    */
    protected void onPaint(System.Windows.Forms.PaintEventArgs e) throws Exception {
        if (DesignMode)
        {
            e.Graphics.DrawRectangle(Pens.SlateGray, 0, 0, Width - 1, Height - 1);
            StringFormat format = new StringFormat();
            format.Alignment = StringAlignment.Center;
            format.LineAlignment = StringAlignment.Center;
            e.Graphics.DrawString(this.Name, Font, Brushes.DarkSlateGray, new Rectangle(0, 0, Width, Height), format);
            return ;
        }
         
        //e.ClipRectangle
        recalculateButtonsizes(e.Graphics);
        for (Object __dummyForeachVar2 : getButtons())
        {
            OpenDental.UI.ODToolBarButton button = (OpenDental.UI.ODToolBarButton)__dummyForeachVar2;
            //check to see if bound are in the clipping rectangle
            drawButton(e.Graphics,button);
        }
        e.Graphics.DrawLine(Pens.SlateGray, 0, Height - 1, Width - 1, Height - 1);
    }

    private void drawButton(Graphics g, OpenDental.UI.ODToolBarButton button) throws Exception {
        if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Separator)
        {
            //was 112,128,144
            //medium stripe
            g.DrawLine(new Pen(Color.FromArgb(190, 200, 210)), button.getBounds().Left, button.getBounds().Top + 1, button.getBounds().Left, button.getBounds().Bottom - 2);
            //dark stripe
            g.DrawLine(new Pen(Color.FromArgb(130, 140, 160)), button.getBounds().Left + 1, button.getBounds().Top, button.getBounds().Left + 1, button.getBounds().Bottom - 1);
            //white stripe
            g.DrawLine(new Pen(Color.FromArgb(255, 255, 255)), button.getBounds().Left + 2, button.getBounds().Top + 1, button.getBounds().Left + 2, button.getBounds().Bottom - 2);
            return ;
        }
         
        //draw background
        if (!button.getEnabled())
        {
            g.FillRectangle(new SolidBrush(SystemColors.Control), button.getBounds());
        }
        else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.ToggleButton && button.getPushed())
        {
            g.FillRectangle(new SolidBrush(Color.FromArgb(248, 248, 248)), button.getBounds());
        }
        else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Label)
        {
            g.FillRectangle(new SolidBrush(SystemColors.Control), button.getBounds());
        }
        else
            switch(button.getState())
            {
                case Normal: 
                    //Control is 224,223,227
                    g.FillRectangle(new SolidBrush(SystemColors.Control), button.getBounds());
                    break;
                case Hover: 
                    //this is lighter than control
                    g.FillRectangle(new SolidBrush(Color.FromArgb(240, 240, 240)), button.getBounds());
                    break;
                case Pressed: 
                    //slightly darker than control
                    g.FillRectangle(new SolidBrush(Color.FromArgb(210, 210, 210)), button.getBounds());
                    break;
                case DropPressed: 
                    //left half looks like hover:
                    g.FillRectangle(new SolidBrush(Color.FromArgb(240, 240, 240)), new Rectangle(button.getBounds().X, button.getBounds().Y, button.getBounds().Width - 15, button.getBounds().Height));
                    //right section looks like Pressed:
                    g.FillRectangle(new SolidBrush(Color.FromArgb(210, 210, 210)), new Rectangle(button.getBounds().X + button.getBounds().Width - 15, button.getBounds().Y, 15, button.getBounds().Height));
                    break;
            
            }   
        //draw image and/or text
        Color textColor = ForeColor;
        Rectangle textRect = new Rectangle();
        int textWidth = button.getBounds().Width;
        if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.DropDownButton)
        {
            textWidth -= 15;
        }
         
        if (!button.getEnabled())
        {
            textColor = SystemColors.GrayText;
        }
         
        if (imageList != null && button.getImageIndex() != -1 && button.getImageIndex() < imageList.Images.Count)
        {
            //draw image and text
            if (!button.getEnabled())
            {
                System.Windows.Forms.ControlPaint.DrawImageDisabled(g, imageList.Images[button.getImageIndex()], button.getBounds().X + 3, button.getBounds().Y + 1, SystemColors.Control);
                textRect = new Rectangle(button.getBounds().X + imageList.ImageSize.Width + 3, button.getBounds().Y, textWidth - imageList.ImageSize.Width - 3, button.getBounds().Height);
            }
            else if (button.getState() == OpenDental.UI.ToolBarButtonState.Pressed)
            {
                //draw slightly down and right
                g.DrawImage(imageList.Images[button.getImageIndex()], button.getBounds().X + 4, button.getBounds().Y + 2);
                textRect = new Rectangle(button.getBounds().X + 1 + imageList.ImageSize.Width + 3, button.getBounds().Y + 1, textWidth - imageList.ImageSize.Width - 3, button.getBounds().Height);
            }
            else
            {
                g.DrawImage(imageList.Images[button.getImageIndex()], button.getBounds().X + 3, button.getBounds().Y + 1);
                textRect = new Rectangle(button.getBounds().X + imageList.ImageSize.Width + 3, button.getBounds().Y, textWidth - imageList.ImageSize.Width - 3, button.getBounds().Height);
            }  
        }
        else
        {
            //only draw text
            if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Label)
            {
                textRect = new Rectangle(button.getBounds().X, button.getBounds().Y, textWidth, button.getBounds().Height);
            }
            else if (button.getState() == OpenDental.UI.ToolBarButtonState.Pressed)
            {
                //draw slightly down and right
                textRect = new Rectangle(button.getBounds().X + 1, button.getBounds().Y + 1, textWidth, button.getBounds().Height);
            }
            else
            {
                textRect = new Rectangle(button.getBounds().X, button.getBounds().Y, textWidth, button.getBounds().Height);
            }  
        } 
        StringFormat format = new StringFormat();
        if (imageList != null && button.getImageIndex() != -1)
        {
            //if there is an image
            //draw text very close to image
            format = new StringFormat();
            format.Alignment = StringAlignment.Near;
            format.LineAlignment = StringAlignment.Center;
            g.DrawString(button.getText(), Font, new SolidBrush(textColor), textRect, format);
        }
        else
        {
            format = new StringFormat();
            format.Alignment = StringAlignment.Center;
            format.LineAlignment = StringAlignment.Center;
            g.DrawString(button.getText(), Font, new SolidBrush(textColor), textRect, format);
        } 
        //draw outline
        Pen penR = new Pen(Color.FromArgb(180, 180, 180));
        if (!button.getEnabled())
        {
            //no outline
            g.DrawLine(penR, button.getBounds().Right - 1, button.getBounds().Top, button.getBounds().Right - 1, button.getBounds().Bottom - 1);
        }
        else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.ToggleButton && button.getPushed())
        {
            g.DrawRectangle(Pens.SlateGray, new Rectangle(button.getBounds().X, button.getBounds().Y, button.getBounds().Width - 1, button.getBounds().Height - 1));
        }
        else if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.Label)
        {
            //no outline
            g.DrawLine(penR, button.getBounds().Right - 1, button.getBounds().Top, button.getBounds().Right - 1, button.getBounds().Bottom - 1);
        }
        else
            switch(button.getState())
            {
                case Normal: 
                    //no outline
                    g.DrawLine(penR, button.getBounds().Right - 1, button.getBounds().Top, button.getBounds().Right - 1, button.getBounds().Bottom - 1);
                    break;
                case Hover: 
                    g.DrawRectangle(Pens.SlateGray, new Rectangle(button.getBounds().X, button.getBounds().Y, button.getBounds().Width - 1, button.getBounds().Height - 1));
                    break;
                case Pressed: 
                    g.DrawRectangle(Pens.SlateGray, new Rectangle(button.getBounds().X, button.getBounds().Y, button.getBounds().Width - 1, button.getBounds().Height - 1));
                    break;
                case DropPressed: 
                    g.DrawRectangle(Pens.SlateGray, new Rectangle(button.getBounds().X, button.getBounds().Y, button.getBounds().Width - 1, button.getBounds().Height - 1));
                    break;
            
            }   
        if (button.getStyle() == OpenDental.UI.ODToolBarButtonStyle.DropDownButton)
        {
            Point[] triangle = new Point[3];
            triangle[0] = new Point(button.getBounds().X + button.getBounds().Width - 11, button.getBounds().Y + button.getBounds().Height / 2 - 2);
            triangle[1] = new Point(button.getBounds().X + button.getBounds().Width - 4, button.getBounds().Y + button.getBounds().Height / 2 - 2);
            triangle[2] = new Point(button.getBounds().X + button.getBounds().Width - 8, button.getBounds().Y + button.getBounds().Height / 2 + 2);
            g.FillPolygon(new SolidBrush(textColor), triangle);
            if (button.getState() != OpenDental.UI.ToolBarButtonState.Normal && button.getEnabled())
            {
                g.DrawLine(Pens.SlateGray, button.getBounds().X + button.getBounds().Width - 15, button.getBounds().Y, button.getBounds().X + button.getBounds().Width - 15, button.getBounds().Y + button.getBounds().Height);
            }
             
        }
         
    }

}


