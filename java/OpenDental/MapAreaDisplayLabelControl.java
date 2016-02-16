//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DraggableControl;
import OpenDental.FormMapAreaEdit;
import OpenDental.MapAreaDisplayLabelControl;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapItemType;

public class MapAreaDisplayLabelControl  extends DraggableControl 
{

    public MapArea MapAreaItem = new MapArea();
    private boolean _allowEdit = false;
    public boolean getAllowEdit() throws Exception {
        return _allowEdit;
    }

    public void setAllowEdit(boolean value) throws Exception {
        _allowEdit = value;
    }

    public EventHandler MapAreaDisplayLabelChanged = new EventHandler();
    public MapAreaDisplayLabelControl() throws Exception {
        initializeComponent();
    }

    public MapAreaDisplayLabelControl(MapArea displayLabel, Font font, Color foreColor, Color backColor, Point location, int pixelsPerFoot, boolean allowDragging, boolean allowEdit) throws Exception {
        this();
        displayLabel.ItemType = MapItemType.DisplayLabel;
        MapAreaItem = displayLabel;
        Font = font;
        ForeColor = foreColor;
        BackColor = backColor;
        Location = location;
        Size = getDrawingSize(this,pixelsPerFoot);
        setAllowDragging(allowDragging);
        setAllowEdit(allowEdit);
        Name = MapAreaItem.MapAreaNum.ToString();
    }

    /**
    * Make the control just tall enough to fit the font and the lesser of the user defined width vs the actual width.
    */
    public static Size getDrawingSize(MapAreaDisplayLabelControl displayLabel, int pixelsPerFoot) throws Exception {
        Size controlSize = OpenDental.MapAreaPanel.getScreenSize(displayLabel.MapAreaItem.Width,displayLabel.MapAreaItem.Height,pixelsPerFoot);
        Size textSize = TextRenderer.MeasureText(displayLabel.MapAreaItem.Description, displayLabel.Font);
        return new Size(Math.Min(controlSize.Width, textSize.Width), textSize.Height);
    }

    private void mapAreaDisplayLabelControl_Paint(Object sender, PaintEventArgs e) throws Exception {
        Brush brushBack = new SolidBrush(BackColor);
        Brush brushFore = new SolidBrush(ForeColor);
        try
        {
            e.Graphics.FillRectangle(brushBack, this.ClientRectangle);
            StringFormat stringFormat = new StringFormat(StringFormatFlags.NoWrap);
            stringFormat.Alignment = StringAlignment.Near;
            stringFormat.LineAlignment = StringAlignment.Center;
            Rectangle rcOuter = this.ClientRectangle;
            e.Graphics.DrawString(MapAreaItem.Description, Font, brushFore, rcOuter, stringFormat);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            brushBack.Dispose();
            brushFore.Dispose();
        }
    }

    private void mapAreaDisplayLabelControl_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (!getAllowEdit())
        {
            return ;
        }
         
        //edit this display label
        FormMapAreaEdit FormEP = new FormMapAreaEdit();
        FormEP.MapItem = this.MapAreaItem;
        if (FormEP.ShowDialog(this) != DialogResult.OK)
        {
            return ;
        }
         
        if (MapAreaDisplayLabelChanged != null)
        {
            //let anyone interested know that this display label was edited
            MapAreaDisplayLabelChanged(this, new EventArgs());
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
        this.SuspendLayout();
        //
        // DisplayLabelMapItemControl
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.DoubleBuffered = true;
        this.Name = "DisplayLabelMapItemControl";
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.MapAreaDisplayLabelControl_Paint);
        this.DoubleClick += new System.EventHandler(this.MapAreaDisplayLabelControl_DoubleClick);
        this.ResumeLayout(false);
    }

}


