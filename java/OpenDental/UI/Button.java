//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.UI;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

/**
* Summary description for Button
*/
public class Button  extends System.Windows.Forms.Button 
{
    private ControlState butState = ControlState.Normal;
    private boolean bCanClick = false;
    private Point adjustImageLocation = new Point();
    private boolean autosize = true;
    private float cornerRadius = 4;
    private Color colorBorder = new Color();
    private Color colorDisabledFore = new Color();
    private Color colorShadow = new Color();
    private Color colorDarkest = new Color();
    private Color colorMain = new Color();
    private Color colorLightest = new Color();
    private Color colorDarkDefault = new Color();
    private Color colorHoverDark = new Color();
    //the outline when hovering
    private Color colorHoverLight = new Color();
    public enum ControlState
    {
        /**
        * button is in the normal state.
        */
        Normal,
        /**
        * button is in the hover state.
        */
        Hover,
        /**
        * button is in the pressed state.
        */
        Pressed,
        /**
        * button is in the default state.
        */
        Default,
        /**
        * button is in the disabled state.
        */
        Disabled
    }
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Initializes a new instance of the Button class.
    */
    public Button() throws Exception {
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        this.SetStyle(ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint | ControlStyles.DoubleBuffer, true);
        colorBorder = Color.FromArgb(28, 81, 128);
        //150,190,210);
        colorDisabledFore = Color.FromArgb(161, 161, 146);
        colorShadow = Color.FromArgb(180, 180, 180);
        colorDarkest = Color.FromArgb(157, 164, 196);
        //125,136,184);//87,102,166);//50,70,150);
        colorLightest = Color.FromArgb(255, 255, 255);
        colorMain = Color.FromArgb(200, 202, 220);
        colorDarkDefault = Color.FromArgb(50, 70, 230);
        colorHoverDark = Color.FromArgb(255, 190, 100);
        //(255,165,0) is pure orange
        colorHoverLight = Color.FromArgb(255, 210, 130);
    }

    //(255,223,163) is a fairly light orange
    /**
    * Clean up any resources being used
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
    * Required method for Designer support - do not modify the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        components = new System.ComponentModel.Container();
    }

    /**
    * Just for compatibility
    */
    public OpenDental.UI.enumType.BtnShape getBtnShape() throws Exception {
        return OpenDental.UI.enumType.BtnShape.Rectangle;
    }

    public void setBtnShape(OpenDental.UI.enumType.BtnShape value) throws Exception {
    }

    /**
    * 
    */
    //[DefaultValue("Silver"),System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)]
    public OpenDental.UI.enumType.XPStyle getBtnStyle() throws Exception {
        return OpenDental.UI.enumType.XPStyle.Silver;
    }

    public void setBtnStyle(OpenDental.UI.enumType.XPStyle value) throws Exception {
    }

    //m_btnStyle = value;
    //this.Invalidate();
    /**
    * 
    */
    public FlatStyle getFlatStyle() throws Exception {
        return super.FlatStyle;
    }

    public void setFlatStyle(FlatStyle value) throws Exception {
        //if user tries to change, they can't
        super.FlatStyle = getFlatStyle().Standard;
    }

    /**
    * 
    */
    public boolean getAutosize() throws Exception {
        return autosize;
    }

    public void setAutosize(boolean value) throws Exception {
        autosize = value;
    }

    /**
    * 
    */
    public Point getAdjustImageLocation() throws Exception {
        return adjustImageLocation;
    }

    public void setAdjustImageLocation(Point value) throws Exception {
        adjustImageLocation = value;
        this.Invalidate();
    }

    /**
    * Typically 4. This property is rarely used
    */
    public float getCornerRadius() throws Exception {
        return cornerRadius;
    }

    public void setCornerRadius(float value) throws Exception {
        cornerRadius = value;
        this.Invalidate();
    }

    /**
    * 
    */
    protected void onClick(EventArgs ea) throws Exception {
        this.Capture = false;
        bCanClick = false;
        if (this.ClientRectangle.Contains(this.PointToClient(Control.MousePosition)))
            butState = ControlState.Hover;
        else
            butState = ControlState.Normal; 
        this.Invalidate();
        super.OnClick(ea);
    }

    /**
    * 
    */
    protected void onMouseEnter(EventArgs ea) throws Exception {
        super.OnMouseEnter(ea);
        butState = ControlState.Hover;
        this.Invalidate();
    }

    /**
    * 
    */
    protected void onMouseDown(MouseEventArgs mea) throws Exception {
        super.OnMouseDown(mea);
        if (mea.Button == MouseButtons.Left)
        {
            bCanClick = true;
            butState = ControlState.Pressed;
            this.Invalidate();
        }
         
    }

    /**
    * 
    */
    protected void onMouseMove(MouseEventArgs mea) throws Exception {
        super.OnMouseMove(mea);
        if (ClientRectangle.Contains(mea.X, mea.Y))
        {
            if (butState == ControlState.Hover && this.Capture && !bCanClick)
            {
                bCanClick = true;
                butState = ControlState.Pressed;
                this.Invalidate();
            }
             
        }
        else
        {
            if (butState == ControlState.Pressed)
            {
                bCanClick = false;
                butState = ControlState.Hover;
                this.Invalidate();
            }
             
        } 
    }

    /**
    * 
    */
    protected void onMouseLeave(EventArgs ea) throws Exception {
        super.OnMouseLeave(ea);
        butState = ControlState.Normal;
        this.Invalidate();
    }

    /**
    * 
    */
    protected void onEnabledChanged(EventArgs ea) throws Exception {
        super.OnEnabledChanged(ea);
        butState = ControlState.Normal;
        this.Invalidate();
    }

    /**
    * 
    */
    protected void onTextChanged(EventArgs e) throws Exception {
        super.OnTextChanged(e);
        if (autosize && !StringSupport.equals(Text, ""))
        {
            int buffer = 6;
            int textWidth = 0;
            Graphics g = this.CreateGraphics();
            try
            {
                {
                    textWidth = (int)g.MeasureString(Text, Font).Width;
                }
            }
            finally
            {
                if (g != null)
                    Disposable.mkDisposable(g).dispose();
                 
            }
            int oldWidth = Width;
            if (this.Image == null)
            {
                if (Width < textWidth + buffer)
                {
                    Width = textWidth + buffer;
                }
                 
            }
            else
            {
                if (Width < textWidth + Image.Size.Width + buffer)
                {
                    Width = textWidth + Image.Size.Width + buffer;
                }
                 
            } 
            if ((Anchor & AnchorStyles.Right) == AnchorStyles.Right)
            {
                //to be perfect, it would exclude if anchored left also
                //this works even if no change in width
                Left += oldWidth - Width;
            }
             
        }
         
    }

    /**
    * 
    */
    protected void onPaint(PaintEventArgs p) throws Exception {
        this.OnPaintBackground(p);
        Graphics g = p.Graphics;
        RectangleF recOutline = new RectangleF(0, 0, ClientRectangle.Width - 1, ClientRectangle.Height - 1);
        float radius = cornerRadius;
        switch(butState)
        {
            case Normal: 
                if (Enabled)
                {
                    radius = cornerRadius;
                    if (Focused || IsDefault)
                    {
                        drawBackground(g,recOutline,radius,colorDarkDefault,colorMain,colorLightest);
                    }
                    else
                    {
                        drawBackground(g,recOutline,radius,colorDarkest,colorMain,colorLightest);
                    } 
                }
                else
                {
                    radius = cornerRadius;
                } 
                break;
            case Hover: 
                //DrawBackground(g,recOutline,radius,colorDarkest,colorMain,colorLightest);
                radius = cornerRadius;
                drawBackground(g,recOutline,radius,colorHoverDark,colorMain,colorHoverLight);
                break;
            case Pressed: 
                if (radius > 3)
                {
                    radius = cornerRadius - 3;
                }
                 
                drawBackground(g,recOutline,radius,colorDarkest,colorMain,colorLightest);
                break;
        
        }
        // enmState will never be == ControlState.Default
        // When (IsDefault == true), enmState will be == ControlState.Normal
        // So when (IsDefault == true), pass ControlState.Default instead of enmState
        //g.DrawLine(Pens.Red,new PointF(recOutline.X,recOutline.Y),
        //	new PointF(recOutline.Right,recOutline.Bottom));
        float diagonalLength = (float)Math.Sqrt(recOutline.Height * recOutline.Height + recOutline.Width * recOutline.Width);
        //unit vector representing direction of diagonal
        float unitvectorx = recOutline.Width / diagonalLength;
        float unitvectory = -recOutline.Height / diagonalLength;
        //g.DrawLine(Pens.Red,0,recOutline.Bottom,unitvectorx*800,recOutline.Bottom+unitvectory*800);
        //unit vector rotated 90 degrees:
        float unitvector90x = -unitvectory;
        float unitvector90y = unitvectorx;
        //g.DrawLine(Pens.Red,0,0,unitvector90x*200,unitvector90y*200);
        //compute the location where the two vectors intersect.
        //solve for x,y,
        //solve for x
        //x=recOutline.X+unitvectorx*length;
        //x=recOutline.X+unitvector90x*length90;
        //So   length=(unitvector90x*length90)/unitvectorx
        //y=recOutline.Height+unitvectory*length;
        //y=unitvector90y*length90;
        //So   length=(unitvector90y*length90-recOutline.Height)/unitvectory;
        //Combine the two equations:
        //(unitvector90x*length90)/unitvectorx=(unitvector90y*length90-recOutline.Height)/unitvectory;
        //Solve for length90
        //(unitvector90x*length90)*unitvectory=unitvectorx*(unitvector90y*length90-recOutline.Height);
        //unitvector90x*length90*unitvectory=unitvectorx*unitvector90y*length90-unitvectorx*recOutline.Height;
        //unitvectorx*unitvector90y*length90-unitvector90x*length90*unitvectory=unitvectorx*recOutline.Height;
        //length90(unitvectorx*unitvector90y-unitvector90x*unitvectory)=unitvectorx*recOutline.Height;
        float length90 = unitvectorx * recOutline.Height / (unitvectorx * unitvector90y - unitvector90x * unitvectory);
        //g.DrawEllipse(Pens.Red,unitvector90x*length90-1,unitvector90y*length90-1,2,2);
        /*LinearGradientBrush brush=new LinearGradientBrush(new PointF(recOutline.X,recOutline.Y),
        				new PointF(unitvector90x*length90*2,unitvector90y*length90*2),
        				colorBorder,colorDarkest);
        			if(IsDefault){
        				DrawRoundedRectangle(g,new Pen(colorDarkDefault),recOutline,radius);
        			}
        			else{
        				DrawRoundedRectangle(g,new Pen(brush),recOutline,radius);
        			}*/
        drawRoundedRectangle(g,new Pen(colorBorder),recOutline,radius);
        drawTextAndImage(g);
        drawReflection(g,recOutline,radius);
    }

    /**
    * Draws a rectangle with rounded edges.
    */
    public static void drawRoundedRectangle(Graphics grfx, Pen pen, RectangleF rect, float round) throws Exception {
        SmoothingMode oldSmoothingMode = grfx.SmoothingMode;
        grfx.SmoothingMode = SmoothingMode.AntiAlias;
        //top
        grfx.DrawLine(pen, rect.Left + round, rect.Top, rect.Right - round, rect.Top);
        grfx.DrawArc(pen, rect.Right - round * 2, rect.Top, round * 2, round * 2, 270, 90);
        //
        grfx.DrawLine(pen, rect.Right, rect.Top + round, rect.Right, rect.Bottom - round);
        grfx.DrawArc(pen, rect.Right - round * 2, rect.Bottom - round * 2, round * 2, round * 2, 0, 90);
        //
        grfx.DrawLine(pen, rect.Right - round, rect.Bottom, rect.Left + round, rect.Bottom);
        grfx.DrawArc(pen, rect.Left, rect.Bottom - round * 2, round * 2, round * 2, 90, 90);
        //
        grfx.DrawLine(pen, rect.Left, rect.Bottom - round, rect.Left, rect.Top + round);
        grfx.DrawArc(pen, rect.Left, rect.Top, round * 2, round * 2, 180, 90);
        //
        grfx.SmoothingMode = oldSmoothingMode;
    }

    private void drawReflection(Graphics g, RectangleF rect, float radius) throws Exception {
        //lower--------------------------------------------------------------------
        Color clrDarkOverlay = Color.FromArgb(50, 125, 125, 125);
        LinearGradientBrush brush = new LinearGradientBrush(new PointF(rect.Left, rect.Bottom), new PointF(rect.Left, rect.Top + rect.Height / 2 - radius * 2f), Color.FromArgb(0, 0, 0, 0), Color.FromArgb(50, 0, 0, 0));
        GraphicsPath path = new GraphicsPath();
        path.AddLine(rect.Left + radius, rect.Top + rect.Height / 2f, rect.Right - radius * 2f, rect.Top + rect.Height / 2f);
        path.AddArc(new RectangleF(rect.Right - (radius * 4f), rect.Top + rect.Height / 2f - radius * 4f, radius * 4f, radius * 4f), 90, -90);
        path.AddLine(rect.Right, rect.Top + rect.Height / 2f - radius, rect.Right, rect.Bottom);
        path.AddLine(rect.Right, rect.Bottom, rect.Left, rect.Bottom);
        path.AddLine(rect.Left, rect.Bottom, rect.Left, rect.Top + rect.Height / 2f - radius / 2f);
        path.AddArc(new RectangleF(rect.Left, rect.Top + rect.Height / 2f - radius, radius * 2f, radius), 180, -90);
        //g.DrawPath(Pens.Red,path);
        g.FillPath(brush, path);
    }

    private void drawBackground(Graphics g, RectangleF rect, float radius, Color clrDark, Color clrMain, Color clrLight) throws Exception {
        if (radius < 0)
        {
            radius = 0;
        }
         
        LinearGradientBrush brush = new LinearGradientBrush();
        SolidBrush brushS = new SolidBrush(clrMain);
        g.SmoothingMode = SmoothingMode.HighQuality;
        //sin(45)=.85. But experimentally, .7 works much better.
        //1/.85=1.18 But experimentally, 1.37 works better. What gives?
        //top
        brush = new LinearGradientBrush(new PointF(rect.Left + radius, rect.Top + radius), new PointF(rect.Left + radius, rect.Top), clrMain, clrLight);
        g.FillRectangle(brushS, rect.Left + radius, rect.Top, rect.Width - (radius * 2), radius);
        //UR
        //2 pies of 45 each.
        brush = new LinearGradientBrush(new PointF(rect.Right - radius, rect.Top), new PointF(rect.Right - (radius / 2f), rect.Top + (radius / 2f)), clrLight, clrMain);
        g.FillPie(brushS, rect.Right - (radius * 2), rect.Top, radius * 2, radius * 2, 270, 45);
        brush = new LinearGradientBrush(new PointF(rect.Right - (radius / 2f) - .5f, rect.Top + (radius / 2f) - .5f), new PointF(rect.Right, rect.Top + radius), clrMain, clrDark);
        g.FillPie(brush, rect.Right - (radius * 2), rect.Top, radius * 2, radius * 2, 315, 45);
        //right
        brush = new LinearGradientBrush(new PointF(rect.Right - radius, rect.Top + radius), new PointF(rect.Right, rect.Top + radius), clrMain, clrDark);
        g.FillRectangle(brush, rect.Right - radius, rect.Top + radius - .5f, radius, rect.Height - (radius * 2) + 1f);
        //LR
        g.FillPie(new SolidBrush(clrDark), rect.Right - (radius * 2), rect.Bottom - (radius * 2), radius * 2, radius * 2, 0, 90);
        brush = new LinearGradientBrush(new PointF(rect.Right - radius, rect.Bottom - radius), new PointF(rect.Right - (radius * .5f) + .5f, rect.Bottom - (radius * .5f) + .5f), clrMain, clrDark);
        g.FillPolygon(brush, new PointF[]{ new PointF(rect.Right - radius, rect.Bottom - radius), new PointF(rect.Right, rect.Bottom - radius), new PointF(rect.Right - radius, rect.Bottom) });
        //bottom
        brush = new LinearGradientBrush(new PointF(rect.Left + radius, rect.Bottom - radius), new PointF(rect.Left + radius, rect.Bottom), clrMain, clrDark);
        g.FillRectangle(brush, rect.Left + radius - .5f, rect.Bottom - radius, rect.Width - (radius * 2) + 1f, radius);
        //LL
        //2 pies of 45 each.
        brush = new LinearGradientBrush(new PointF(rect.Left + (radius / 2f), rect.Bottom - (radius / 2f)), new PointF(rect.Left + radius, rect.Bottom), clrMain, clrDark);
        g.FillPie(brush, rect.Left, rect.Bottom - (radius * 2), radius * 2, radius * 2, 90, 45);
        brush = new LinearGradientBrush(new PointF(rect.Left + (radius / 2f), rect.Bottom - (radius / 2f)), new PointF(rect.Left, rect.Bottom - radius), clrMain, clrLight);
        g.FillPie(brushS, rect.Left, rect.Bottom - (radius * 2), radius * 2, radius * 2, 135, 45);
        //left
        brush = new LinearGradientBrush(new PointF(rect.Left + radius, rect.Top), new PointF(rect.Left, rect.Top), clrMain, clrLight);
        g.FillRectangle(brushS, rect.Left, rect.Top + radius, radius, rect.Height - (radius * 2));
        //UL
        //new SolidBrush(clrLight)
        g.FillPie(brushS, rect.Left, rect.Top, radius * 2, radius * 2, 180, 90);
        brush = new LinearGradientBrush(new PointF(rect.Left + radius, rect.Top + radius), new PointF(rect.Left + (radius / 2f), rect.Top + (radius / 2f)), clrMain, clrLight);
        //center
        GraphicsPath path = new GraphicsPath();
        path.AddEllipse(rect.Left - rect.Width / 8f, rect.Top - rect.Height / 2f, rect.Width, rect.Height * 3f / 2f);
        PathGradientBrush pathBrush = new PathGradientBrush(path);
        pathBrush.CenterColor = Color.FromArgb(255, 255, 255, 255);
        pathBrush.SurroundColors = new Color[]{ Color.FromArgb(0, 255, 255, 255) };
        g.FillRectangle(new SolidBrush(clrMain), rect.Left + radius - .5f, rect.Top + radius - .5f, rect.Width - (radius * 2) + 1f, rect.Height - (radius * 2) + 1f);
        g.FillRectangle(pathBrush, rect.Left + radius - .5f, rect.Top + radius - .5f, rect.Width - (radius * 2) + 1f, rect.Height - (radius * 2) + 1f);
        //highlights
        brush = new LinearGradientBrush(new PointF(rect.Left + radius, rect.Top), new PointF(rect.Left + radius + rect.Width * 2f / 3f, rect.Top), clrLight, clrMain);
        g.FillRectangle(brush, rect.Left + radius, rect.Y + radius * 3f / 8f, rect.Width / 2f, radius / 4f);
        path = new GraphicsPath();
        path.AddLine(rect.Left + radius, rect.Top + radius * 3 / 8, rect.Left + radius, rect.Top + radius * 5 / 8);
        path.AddArc(new RectangleF(rect.Left + radius * 5 / 8, rect.Top + radius * 5 / 8, radius * 3 / 4, radius * 3 / 4), 270, -90);
        path.AddArc(new RectangleF(rect.Left + radius * 3 / 8, rect.Top + radius * 7 / 8, radius * 1 / 4, radius * 1 / 4), 0, 180);
        path.AddArc(new RectangleF(rect.Left + radius * 3 / 8, rect.Top + radius * 3 / 8, radius * 5 / 4, radius * 5 / 4), 180, 90);
        //g.DrawPath(Pens.Red,path);
        g.FillPath(new SolidBrush(clrLight), path);
    }

    /**
    * Draws the text and image
    */
    private void drawTextAndImage(Graphics g) throws Exception {
        g.SmoothingMode = SmoothingMode.HighQuality;
        SolidBrush brushText = new SolidBrush();
        SolidBrush brushGlow = null;
        if (Enabled)
        {
            brushText = new SolidBrush(ForeColor);
            brushGlow = new SolidBrush(Color.White);
        }
        else
        {
            brushText = new SolidBrush(colorDisabledFore);
        } 
        StringFormat sf = GetStringFormat(this.TextAlign);
        if (ShowKeyboardCues)
        {
            sf.HotkeyPrefix = System.Drawing.Text.HotkeyPrefix.Show;
        }
        else
        {
            sf.HotkeyPrefix = System.Drawing.Text.HotkeyPrefix.Hide;
        } 
        RectangleF recGlow1 = new RectangleF();
        if (this.Image != null)
        {
            Rectangle recTxt = new Rectangle();
            Point ImagePoint = new Point(6, 4);
            ImageAlign __dummyScrutVar1 = this.ImageAlign;
            if (__dummyScrutVar1.equals(ContentAlignment.MiddleLeft))
            {
                ImagePoint.X = 6;
                ImagePoint.Y = this.ClientRectangle.Height / 2 - Image.Height / 2;
                recTxt.Width = this.ClientRectangle.Width - this.Image.Width;
                recTxt.Height = this.ClientRectangle.Height;
                recTxt.X = this.Image.Width;
                recTxt.Y = 0;
            }
            else if (__dummyScrutVar1.equals(ContentAlignment.MiddleRight))
            {
                recTxt.Width = this.ClientRectangle.Width - this.Image.Width - 8;
                recTxt.Height = this.ClientRectangle.Height;
                recTxt.X = 0;
                recTxt.Y = 0;
                ImagePoint.X = recTxt.Width;
                recTxt.Width += this.adjustImageLocation.X;
                ImagePoint.Y = this.ClientRectangle.Height / 2 - Image.Height / 2;
            }
            else if (__dummyScrutVar1.equals(ContentAlignment.MiddleCenter))
            {
                // no text in this alignment
                ImagePoint.X = (this.ClientRectangle.Width - this.Image.Width) / 2;
                ImagePoint.Y = (this.ClientRectangle.Height - this.Image.Height) / 2;
                recTxt.Width = 0;
                recTxt.Height = 0;
                recTxt.X = this.ClientRectangle.Width;
                recTxt.Y = this.ClientRectangle.Height;
            }
               
            ImagePoint.X += adjustImageLocation.X;
            ImagePoint.Y += adjustImageLocation.Y;
            if (this.Enabled)
            {
                g.DrawImage(this.Image, ImagePoint);
            }
            else
            {
                System.Windows.Forms.ControlPaint.DrawImageDisabled(g, this.Image, ImagePoint.X, ImagePoint.Y, BackColor);
            } 
            recGlow1 = new RectangleF(recTxt.X + .5f, recTxt.Y + .5f, recTxt.Width, recTxt.Height);
            if (this.ImageAlign != ContentAlignment.MiddleCenter)
            {
                if (Enabled)
                {
                    //first draw white text slightly off center
                    g.DrawString(this.Text, this.Font, brushGlow, recGlow1, sf);
                }
                 
                //then, the black text
                g.DrawString(this.Text, this.Font, brushText, recTxt, sf);
            }
             
        }
        else
        {
            //image is null
            recGlow1 = new RectangleF(ClientRectangle.X + .5f, ClientRectangle.Y + .5f, ClientRectangle.Width, ClientRectangle.Height);
            if (this.Enabled)
            {
                g.DrawString(this.Text, this.Font, brushGlow, recGlow1, sf);
            }
             
            g.DrawString(this.Text, this.Font, brushText, this.ClientRectangle, sf);
        } 
        brushText.Dispose();
        sf.Dispose();
    }

    private StringFormat getStringFormat(ContentAlignment contentAlignment) throws Exception {
        if (!Enum.IsDefined(ContentAlignment.class, (int)contentAlignment))
            throw new System.ComponentModel.InvalidEnumArgumentException("contentAlignment", (int)contentAlignment, ContentAlignment.class);
         
        StringFormat stringFormat = new StringFormat();
        ContentAlignment __dummyScrutVar2 = contentAlignment;
        if (__dummyScrutVar2.equals(ContentAlignment.MiddleCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.MiddleLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.MiddleRight))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Far;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.TopCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.TopLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.TopRight))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Far;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.BottomCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.BottomLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar2.equals(ContentAlignment.BottomRight))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Far;
        }
                 
        return stringFormat;
    }

}


