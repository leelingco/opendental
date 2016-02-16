//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDental.MapAreaDisplayLabelControl;
import OpenDental.Properties.Resources;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapAreas;

public class MapAreaPanel  extends Panel 
{

    private boolean __AllowDragging = new boolean();
    public boolean getAllowDragging() {
        return __AllowDragging;
    }

    public void setAllowDragging(boolean value) {
        __AllowDragging = value;
    }

    private boolean __AllowEditing = new boolean();
    public boolean getAllowEditing() {
        return __AllowEditing;
    }

    public void setAllowEditing(boolean value) {
        __AllowEditing = value;
    }

    private Font fontLabel = SystemFonts.DefaultFont;
    public Font getFontLabel() throws Exception {
        return fontLabel;
    }

    public void setFontLabel(Font value) throws Exception {
        fontLabel = value;
        resizeScrollbarsToFitContents();
        Invalidate(true);
    }

    private Font fontCubicle = SystemFonts.DefaultFont;
    public Font getFontCubicle() throws Exception {
        return fontCubicle;
    }

    public void setFontCubicle(Font value) throws Exception {
        fontCubicle = value;
        resizeScrollbarsToFitContents();
        Invalidate(true);
    }

    private Font fontCubicleHeader = SystemFonts.DefaultFont;
    public Font getFontCubicleHeader() throws Exception {
        return fontCubicleHeader;
    }

    public void setFontCubicleHeader(Font value) throws Exception {
        fontCubicleHeader = value;
        resizeScrollbarsToFitContents();
        Invalidate(true);
    }

    private int floorWidthFeet = 80;
    public int getFloorWidthFeet() throws Exception {
        return floorWidthFeet;
    }

    public void setFloorWidthFeet(int value) throws Exception {
        floorWidthFeet = value;
        resizeScrollbarsToFitContents();
        resizeCubicles();
        Invalidate(true);
    }

    private int floorHeightFeet = 80;
    public int getFloorHeightFeet() throws Exception {
        return floorHeightFeet;
    }

    public void setFloorHeightFeet(int value) throws Exception {
        floorHeightFeet = value;
        resizeScrollbarsToFitContents();
        resizeCubicles();
        Invalidate(true);
    }

    private int pixelsPerFoot = 10;
    public int getPixelsPerFoot() throws Exception {
        return pixelsPerFoot;
    }

    public void setPixelsPerFoot(int value) throws Exception {
        pixelsPerFoot = value;
        resizeScrollbarsToFitContents();
        resizeCubicles();
        Invalidate(true);
    }

    private boolean showGrid = false;
    public boolean getShowGrid() throws Exception {
        return showGrid;
    }

    public void setShowGrid(boolean value) throws Exception {
        showGrid = value;
        Invalidate();
    }

    private boolean showOutline = false;
    public boolean getShowOutline() throws Exception {
        return showOutline;
    }

    public void setShowOutline(boolean value) throws Exception {
        showOutline = value;
        Invalidate();
    }

    private Color gridColor = Color.DarkGray;
    public Color getGridColor() throws Exception {
        return gridColor;
    }

    public void setGridColor(Color value) throws Exception {
        gridColor = value;
        Invalidate();
    }

    private Color floorColor = Color.White;
    public Color getFloorColor() throws Exception {
        return floorColor;
    }

    public void setFloorColor(Color value) throws Exception {
        floorColor = value;
        for (int i = 0;i < this.Controls.Count;i++)
        {
            //This is effective the BackColor of this panel so set child controls BackColor to match.
            this.Controls[i].BackColor = floorColor;
        }
        Invalidate(true);
    }

    public EventHandler MapAreaChanged = new EventHandler();
    private static Random Rand = new Random();
    public int getRandomDimension() throws Exception {
        return Rand.Next(0, 2) == 1 ? 6 : 9;
    }

    public int getRandomXPos(int cubicleWidth) throws Exception {
        return Rand.Next(0, getFloorWidthFeet() - cubicleWidth);
    }

    public int getRandomYPos(int cubicleHeight) throws Exception {
        return Rand.Next(0, getFloorHeightFeet() - cubicleHeight);
    }

    public MapAreaPanel() throws Exception {
        initializeComponent();
        //prevent flickering
        this.DoubleBuffered = true;
    }

    /**
    * Clear the form. Optionally delete the records from the database. Use this option sparingly (if ever).
    */
    public void clear(boolean deleteFromDatabase) throws Exception {
        if (deleteFromDatabase)
        {
            for (int i = 0;i < this.Controls.Count;i++)
            {
                if (!(this.Controls[i] instanceof OpenDental.MapAreaRoomControl))
                {
                    return ;
                }
                 
                MapAreas.delete(((OpenDental.MapAreaRoomControl)this.Controls[i]).MapAreaItem.MapAreaNum);
            }
        }
         
        this.Controls.Clear();
    }

    /**
    * For testing only. Create a random cubicle and add it to the panel.
    */
    public MapArea addRandomCubicle() throws Exception {
        MapArea cubicle = new MapArea();
        cubicle.MapAreaNum = Rand.Next(1, 1000000);
        cubicle.Description = "";
        cubicle.Extension = Rand.Next(100, 200);
        cubicle.Width = getRandomDimension();
        cubicle.Height = getRandomDimension();
        cubicle.XPos = getRandomXPos((int)cubicle.Width);
        cubicle.YPos = getRandomYPos((int)cubicle.Height);
        addCubicle(cubicle);
        return cubicle;
    }

    /**
    * Add a cubicle to the panel.
    */
    public void addCubicle(MapArea cubicle) throws Exception {
        OpenDental.MapAreaRoomControl phone = new OpenDental.MapAreaRoomControl(cubicle, TimeSpan.FromSeconds(Rand.Next(60, 1200)).ToString(), "Emp: " + this.Controls.Count.ToString(), this.Controls.Count, cubicle.Extension.ToString(), "Status", this.getFontCubicle(), this.getFontCubicleHeader(), Color.FromArgb(40, Color.Red), Color.Red, this.getFloorColor(), getScreenLocation(cubicle.XPos,cubicle.YPos,this.getPixelsPerFoot()), getScreenSize(cubicle.Width,cubicle.Height,this.getPixelsPerFoot()), Resources.getphoneInUse(), this.getAllowDragging(), this.getAllowEditing());
        phone.DragDone += mapAreaControl_DragDone;
        phone.MapAreaRoomChanged += mapAreaControl_Changed;
        this.Controls.Add(phone);
    }

    /**
    * Add a display label to the panel.
    */
    public void addDisplayLabel(MapArea displayLabel) throws Exception {
        //This is effective the BackColor of this panel so set DisplayLabel controls BackColor to match.
        MapAreaDisplayLabelControl label = new MapAreaDisplayLabelControl(displayLabel, this.getFontLabel(), this.ForeColor, this.getFloorColor(), getScreenLocation(displayLabel.XPos,displayLabel.YPos,this.getPixelsPerFoot()), this.getPixelsPerFoot(), this.getAllowDragging(), this.getAllowEditing());
        label.DragDone += mapAreaControl_DragDone;
        label.MapAreaDisplayLabelChanged += mapAreaControl_Changed;
        this.Controls.Add(label);
    }

    /**
    * Alert parent that something has changed
    */
    void mapAreaControl_Changed(Object sender, EventArgs e) throws Exception {
        if (MapAreaChanged != null)
        {
            MapAreaChanged(sender, new EventArgs());
        }
         
    }

    /**
    * Handle the Cubicle.DragDone event
    */
    void mapAreaControl_DragDone(Object sender, EventArgs e) throws Exception {
        if (sender == null)
        {
            return ;
        }
         
        Control asControl = null;
        MapArea clinicMapItem = null;
        if (sender instanceof OpenDental.MapAreaRoomControl)
        {
            asControl = (Control)sender;
            clinicMapItem = ((OpenDental.MapAreaRoomControl)sender).MapAreaItem;
        }
        else if (sender instanceof MapAreaDisplayLabelControl)
        {
            asControl = (Control)sender;
            clinicMapItem = ((MapAreaDisplayLabelControl)sender).MapAreaItem;
        }
        else
        {
            return ;
        }  
        //recalculate XPos and YPos based on new location in the panel
        PointF xy = ConvertScreenLocationToXY(asControl.Location, getPixelsPerFoot());
        clinicMapItem.XPos = Math.Round(xy.X, 3);
        clinicMapItem.YPos = Math.Round(xy.Y, 3);
        //save new cubicle location to db
        MapAreas.update(clinicMapItem);
        //alert the parent
        mapAreaControl_Changed(sender,new EventArgs());
    }

    /**
    * Call this BEFORE calling ResizeCubicles.
    */
    public void resizeScrollbarsToFitContents() throws Exception {
        Size sizeControl = new Size(this.getFloorWidthFeet() * this.getPixelsPerFoot(), this.getFloorHeightFeet() * this.getPixelsPerFoot());
        if (this.AutoScrollMinSize != sizeControl)
        {
            this.AutoScrollMinSize = sizeControl;
            this.AutoScrollPosition = new Point(0, 0);
        }
         
    }

    /**
    * Call this BEFORE calling Invalidate(true).
    */
    private void resizeCubicles() throws Exception {
        if (this.Controls == null || this.Controls.Count <= 0)
        {
            return ;
        }
         
        for (int i = 0;i < this.Controls.Count;i++)
        {
            if (this.Controls[i] == null)
            {
                continue;
            }
            else if (this.Controls[i] instanceof OpenDental.MapAreaRoomControl)
            {
                OpenDental.MapAreaRoomControl cubicle = (OpenDental.MapAreaRoomControl)this.Controls[i];
                cubicle.Location = getScreenLocation(cubicle.MapAreaItem.XPos,cubicle.MapAreaItem.YPos,this.getPixelsPerFoot());
                cubicle.Size = getScreenSize(cubicle.MapAreaItem.Width,cubicle.MapAreaItem.Height,this.getPixelsPerFoot());
            }
            else if (this.Controls[i] instanceof MapAreaDisplayLabelControl)
            {
                MapAreaDisplayLabelControl displayLabel = (MapAreaDisplayLabelControl)this.Controls[i];
                displayLabel.Location = getScreenLocation(displayLabel.MapAreaItem.XPos,displayLabel.MapAreaItem.YPos,this.getPixelsPerFoot());
                displayLabel.Size = MapAreaDisplayLabelControl.getDrawingSize(displayLabel,this.getPixelsPerFoot());
                //draw labels on top of all other controls
                displayLabel.BringToFront();
            }
               
        }
    }

    private void mapAreaPanel_Paint(Object sender, PaintEventArgs e) throws Exception {
        //draw the floor color as the background
        Brush brushFloor = new SolidBrush(this.getFloorColor());
        try
        {
            {
                e.Graphics.FillRectangle(brushFloor, 0, 0, (this.getFloorWidthFeet() * this.getPixelsPerFoot()), (this.getFloorHeightFeet() * this.getPixelsPerFoot()));
            }
        }
        finally
        {
            if (brushFloor != null)
                Disposable.mkDisposable(brushFloor).dispose();
             
        }
        if (getShowGrid())
        {
            drawGrid(e.Graphics);
        }
         
        if (getShowOutline())
        {
            drawOutline(e.Graphics);
        }
         
    }

    private void drawGrid(Graphics graphics) throws Exception {
        Pen pen = new Pen(this.getGridColor(), 1F);
        try
        {
            graphics.TranslateTransform(this.AutoScrollPosition.X, this.AutoScrollPosition.Y);
            //draw vertical vertical lines
            int x = 0;
            while (x <= this.getFloorWidthFeet())
            {
                Point top = new Point(x * getPixelsPerFoot(), 0);
                Point bottom = new Point(x * getPixelsPerFoot(), this.getFloorHeightFeet() * getPixelsPerFoot());
                graphics.DrawLine(pen, top, bottom);
                x++;
            }
            //draw horizontal lines
            int y = 0;
            while (y <= this.getFloorHeightFeet())
            {
                Point left = new Point(0, y * getPixelsPerFoot());
                Point right = new Point(this.getFloorWidthFeet() * getPixelsPerFoot(), y * getPixelsPerFoot());
                graphics.DrawLine(pen, left, right);
                y++;
            }
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            pen.Dispose();
        }
    }

    private void drawOutline(Graphics graphics) throws Exception {
        //draw the oultine around the entire panel
        Pen penOutline = new Pen(Color.FromArgb(128, Color.Black), 3);
        try
        {
            {
                float halfPenWidth = (float)penOutline.Width / 2;
                graphics.DrawRectangle(penOutline, halfPenWidth, halfPenWidth, (this.getFloorWidthFeet() * this.getPixelsPerFoot()) - halfPenWidth, (this.getFloorHeightFeet() * this.getPixelsPerFoot()) - halfPenWidth);
            }
        }
        finally
        {
            if (penOutline != null)
                Disposable.mkDisposable(penOutline).dispose();
             
        }
    }

    /**
    * Calculate the screen location of this cubicle based on x, y, and pixel scaling.
    */
    public static Point getScreenLocation(double xPos, double yPos, int pixelsPerFoot) throws Exception {
        return new Point((int)(xPos * pixelsPerFoot), (int)(yPos * pixelsPerFoot));
    }

    /**
    * Calculate the XPos and YPos of this cubicle base on screen location and pixel scaling.
    */
    public static PointF convertScreenLocationToXY(Point screenLocation, int pixelsPerFoot) throws Exception {
        return new PointF((float)screenLocation.X / pixelsPerFoot, (float)screenLocation.Y / pixelsPerFoot);
    }

    /**
    * Calculate the screen (drawing) size of this cubicle based on width, height, and pixel scaling.
    */
    public static Size getScreenSize(double width, double height, int pixelsPerFoot) throws Exception {
        return new System.Drawing.Size((int)(width * pixelsPerFoot), (int)(height * pixelsPerFoot));
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
        // UserControlCubicleFarmPanel
        //
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.MapAreaPanel_Paint);
        this.ResumeLayout(false);
    }

}


