//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DraggableControl;
import OpenDental.FormMapAreaEdit;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapItemType;

public class MapAreaRoomControl  extends DraggableControl 
{

    public MapArea MapAreaItem = new MapArea();
    private long __EmployeeNum = new long();
    public long getEmployeeNum() {
        return __EmployeeNum;
    }

    public void setEmployeeNum(long value) {
        __EmployeeNum = value;
    }

    private String __EmployeeName = new String();
    public String getEmployeeName() {
        return __EmployeeName;
    }

    public void setEmployeeName(String value) {
        __EmployeeName = value;
    }

    private String __Extension = new String();
    public String getExtension() {
        return __Extension;
    }

    public void setExtension(String value) {
        __Extension = value;
    }

    private String __Elapsed = new String();
    public String getElapsed() {
        return __Elapsed;
    }

    public void setElapsed(String value) {
        __Elapsed = value;
    }

    private String __Status = new String();
    public String getStatus() {
        return __Status;
    }

    public void setStatus(String value) {
        __Status = value;
    }

    private Image __PhoneImage = new Image();
    public Image getPhoneImage() {
        return __PhoneImage;
    }

    public void setPhoneImage(Image value) {
        __PhoneImage = value;
    }

    public String getText() throws Exception {
        return super.Text;
    }

    public void setText(String value) throws Exception {
        super.Text = value;
        Invalidate();
    }

    private int _borderThickness = 6;
    public int getBorderThickness() throws Exception {
        return _borderThickness;
    }

    public void setBorderThickness(int value) throws Exception {
        _borderThickness = value;
        Invalidate();
    }

    /**
    * Set when flashing starts so we know what inner color to go back to.
    */
    private Color _innerColorRestore = Color.FromArgb(128, Color.Red);
    private Color DefaultOuterColor = Color.Red;
    public Color getOuterColor() throws Exception {
        return DefaultOuterColor;
    }

    public void setOuterColor(Color value) throws Exception {
        DefaultOuterColor = value;
        Invalidate();
    }

    /**
    * Set when flashing starts so we know what outer color to go back to.
    */
    private Color _outerColorRestore = Color.Red;
    private Color DefaultInnerColor = Color.FromArgb(128, Color.Red);
    public Color getInnerColor() throws Exception {
        return DefaultInnerColor;
    }

    public void setInnerColor(Color value) throws Exception {
        DefaultInnerColor = value;
        Invalidate();
    }

    private boolean IsEmpty = false;
    public boolean getEmpty() throws Exception {
        return IsEmpty;
    }

    public void setEmpty(boolean value) throws Exception {
        IsEmpty = value;
        Invalidate();
    }

    private boolean _allowEdit = false;
    public boolean getAllowEdit() throws Exception {
        return _allowEdit;
    }

    public void setAllowEdit(boolean value) throws Exception {
        _allowEdit = value;
    }

    private Font _fontHeader = SystemFonts.DefaultFont;
    public Font getFontHeader() throws Exception {
        return _fontHeader;
    }

    public void setFontHeader(Font value) throws Exception {
        _fontHeader = value;
        Invalidate();
    }

    public boolean getIsFlashing() throws Exception {
        return timerFlash.Enabled;
    }

    public EventHandler MapAreaRoomChanged = new EventHandler();
    /**
    * Default. Must be called by all other ctors as we will call InitializeComponent here.
    */
    public MapAreaRoomControl() throws Exception {
        initializeComponent();
    }

    /**
    * Takes all required fields as input. Suggest using this version when adding a cubicle to a ClinicMapPanel.
    */
    public MapAreaRoomControl(MapArea cubicle, String elapsed, String employeeName, long employeeNum, String extension, String status, Font font, Font fontHeader, Color innerColor, Color outerColor, Color backColor, Point location, Size size, Image phoneImage, boolean allowDragging, boolean allowEdit) throws Exception {
        this();
        cubicle.ItemType = MapItemType.Room;
        MapAreaItem = cubicle;
        setElapsed(elapsed);
        setEmployeeName(employeeName);
        setEmployeeNum(employeeNum);
        setExtension(extension);
        setStatus(status);
        Font = font;
        setFontHeader(fontHeader);
        Location = location;
        Size = size;
        setInnerColor(innerColor);
        setOuterColor(outerColor);
        BackColor = backColor;
        setPhoneImage(phoneImage);
        setAllowDragging(allowDragging);
        setAllowEdit(allowEdit);
        Name = MapAreaItem.MapAreaNum.ToString();
    }

    public void startFlashing() throws Exception {
        if (getIsFlashing())
        {
            return ;
        }
         
        //already on
        //save the colors
        _outerColorRestore = getOuterColor();
        _innerColorRestore = getInnerColor();
        timerFlash.Start();
    }

    public void stopFlashing() throws Exception {
        if (!getIsFlashing())
        {
            return ;
        }
         
        //already off
        timerFlash.Stop();
        setOuterColor(_outerColorRestore);
        setInnerColor(_innerColorRestore);
    }

    private void timerFlash_Tick(Object sender, EventArgs e) throws Exception {
        //flip inner and outer colors
        if (getOuterColor() == _outerColorRestore)
        {
            setOuterColor(_innerColorRestore);
            setInnerColor(_outerColorRestore);
        }
        else
        {
            setOuterColor(_outerColorRestore);
            setInnerColor(_innerColorRestore);
        } 
    }

    private void mapAreaRoomControl_Paint(Object sender, PaintEventArgs e) throws Exception {
        Brush brushInner = new SolidBrush(getEmpty() ? Color.FromArgb(20, Color.Gray) : getInnerColor());
        Brush brushText = new SolidBrush(getEmpty() ? Color.FromArgb(128, Color.Gray) : ForeColor);
        Pen penOuter = new Pen(getEmpty() ? Color.FromArgb(128, Color.Gray) : getOuterColor(), getBorderThickness());
        try
        {
            RectangleF rcOuter = this.ClientRectangle;
            //clear control canvas
            e.Graphics.Clear(this.BackColor);
            float halfPenThickness = getBorderThickness() / (float)2;
            //deflate for border
            rcOuter.Inflate(-halfPenThickness, -halfPenThickness);
            //draw border
            e.Graphics.DrawRectangle(penOuter, rcOuter.X, rcOuter.Y, rcOuter.Width, rcOuter.Height);
            //deflate to drawable region
            rcOuter.Inflate(-halfPenThickness, -halfPenThickness);
            //fill interior
            e.Graphics.FillRectangle(brushInner, rcOuter);
            StringFormat stringFormat = new StringFormat(StringFormatFlags.NoWrap);
            stringFormat.Alignment = StringAlignment.Center;
            stringFormat.LineAlignment = StringAlignment.Center;
            if (this.getEmpty())
            {
                //empty room so gray out and return
                e.Graphics.DrawString("EMPTY", Font, brushText, rcOuter, stringFormat);
                return ;
            }
            else if (!StringSupport.equals(this.getText(), ""))
            {
                //using as a label so just draw the string
                fitText(this.getText(),Font,brushText,new RectangleF(rcOuter.Left, rcOuter.Top + 2, rcOuter.Width, rcOuter.Height),stringFormat,e.Graphics);
                return ;
            }
              
            //4 rows of data
            int rowsLowestCommonDenominator = 9;
            float typicalRowHeight = rcOuter.Height / (float)rowsLowestCommonDenominator;
            //row 1 - employee name
            fitText(getEmployeeName(),getFontHeader(),brushText,new RectangleF(rcOuter.X, rcOuter.Y, rcOuter.Width, typicalRowHeight * 3),stringFormat,e.Graphics);
            float yPosBottom = typicalRowHeight * 3;
            //row 1 is 3/9 tall
            //row 2 (left) - employee extension
            fitText(getExtension(),Font,brushText,new RectangleF(rcOuter.X, rcOuter.Y + yPosBottom, rcOuter.Width / 2, typicalRowHeight * 2),stringFormat,e.Graphics);
            //row 2 (right) - phone icon
            if (getPhoneImage() != null)
            {
                Bitmap bitmap = new Bitmap(getPhoneImage());
                try
                {
                    {
                        //center the image on the right-hand side of row 2
                        RectangleF rectImage = new RectangleF(rcOuter.X + (rcOuter.Width / 2), rcOuter.Y + yPosBottom, rcOuter.Width / 2, typicalRowHeight * 2);
                        if (bitmap.Height < rectImage.Height)
                        {
                            rectImage.Y -= (bitmap.Height - rectImage.Height) / 2;
                        }
                         
                        if (bitmap.Width < rectImage.Width)
                        {
                            rectImage.X -= (bitmap.Width - rectImage.Width) / 2;
                        }
                         
                        e.Graphics.DrawImageUnscaled(getPhoneImage(), Rectangle.Round(rectImage));
                    }
                }
                finally
                {
                    if (bitmap != null)
                        Disposable.mkDisposable(bitmap).dispose();
                     
                }
            }
             
            yPosBottom += typicalRowHeight * 2;
            //row 2 is 2/9 tall
            //row 3 - elapsed time
            fitText(getElapsed(),Font,brushText,new RectangleF(rcOuter.X, rcOuter.Y + yPosBottom, rcOuter.Width, typicalRowHeight * 2),stringFormat,e.Graphics);
            yPosBottom += typicalRowHeight * 2;
            //row 3 is 2/9 tall
            //row 4 - employee status
            fitText(getStatus(),Font,brushText,new RectangleF(rcOuter.X, rcOuter.Y + yPosBottom, rcOuter.Width, typicalRowHeight * 2),stringFormat,e.Graphics);
            yPosBottom += typicalRowHeight * 2;
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            //row 4 is 2/9 tall
            brushInner.Dispose();
            brushText.Dispose();
            penOuter.Dispose();
        }
    }

    /**
    * Replaces Graphics.DrawString. Finds a suitable font size to fit the text to the bounding rectangle.
    */
    public static void fitText(String text, Font font, Brush brush, RectangleF rectF, StringFormat stringFormat, Graphics graphics) throws Exception {
        float emSize = font.Size;
        while (true)
        {
            Font newFont = new Font(font.FontFamily, emSize, font.Style);
            try
            {
                {
                    Size size = TextRenderer.MeasureText(text, newFont);
                    if (size.Width < rectF.Width || emSize < 2)
                    {
                        //does our new font fit? only allow smallest of 2 point font.
                        graphics.DrawString(text, newFont, brush, rectF, stringFormat);
                        return ;
                    }
                     
                }
            }
            finally
            {
                if (newFont != null)
                    Disposable.mkDisposable(newFont).dispose();
                 
            }
            //text didn't fit so decrement the font size and try again
            emSize -= .1F;
        }
    }

    private void mapAreaRoomControl_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (!getAllowEdit())
        {
            return ;
        }
         
        //edit this room
        FormMapAreaEdit FormEP = new FormMapAreaEdit();
        FormEP.MapItem = this.MapAreaItem;
        if (FormEP.ShowDialog(this) != DialogResult.OK)
        {
            return ;
        }
         
        if (MapAreaRoomChanged != null)
        {
            //let anyone interested know that this cubicle was edited
            MapAreaRoomChanged(this, new EventArgs());
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
         
        super.dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        this.timerFlash = new System.Windows.Forms.Timer(this.components);
        this.SuspendLayout();
        //
        // timerFlash
        //
        this.timerFlash.Interval = 300;
        this.timerFlash.Tick += new System.EventHandler(this.timerFlash_Tick);
        //
        // MapAreaRoomControl
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.DoubleBuffered = true;
        this.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.Name = "MapAreaRoomControl";
        this.Size = new System.Drawing.Size(180, 163);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.MapAreaRoomControl_Paint);
        this.DoubleClick += new System.EventHandler(this.MapAreaRoomControl_DoubleClick);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Timer timerFlash = new System.Windows.Forms.Timer();
}


