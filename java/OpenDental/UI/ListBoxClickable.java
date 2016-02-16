//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;


public class ListBoxClickable  extends ListBox 
{

    /**
    * Will be -1 unless mouse is currently hovering over an item.
    */
    private int hotItem = -1;
    public ListBoxClickable() throws Exception {
        initializeComponent();
        this.DrawMode = DrawMode.OwnerDrawFixed;
        this.ItemHeight = 15;
        this.SelectionMode = SelectionMode.None;
    }

    protected void onMouseMove(MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        int hotPrevious = hotItem;
        hotItem = this.IndexFromPoint(e.Location);
        if (hotItem != hotPrevious)
        {
            if (hotPrevious != -1)
            {
                this.Invalidate(this.GetItemRectangle(hotPrevious));
            }
             
            if (hotItem != -1)
            {
                this.Invalidate(this.GetItemRectangle(hotItem));
            }
             
        }
         
    }

    protected void onMouseLeave(EventArgs e) throws Exception {
        super.OnMouseLeave(e);
        int hotPrevious = hotItem;
        hotItem = -1;
        if (hotItem != hotPrevious)
        {
            if (hotPrevious != -1)
            {
                this.Invalidate(this.GetItemRectangle(hotPrevious));
            }
             
            if (hotItem != -1)
            {
                this.Invalidate(this.GetItemRectangle(hotItem));
            }
             
        }
         
    }

    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
    }

    protected void onDrawItem(DrawItemEventArgs e) throws Exception {
        super.OnDrawItem(e);
        //can't seem to make font smooth.
        e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
        e.Graphics.TextRenderingHint = System.Drawing.Text.TextRenderingHint.ClearTypeGridFit;
        e.Graphics.FillRectangle(new SolidBrush(Color.White), e.Bounds);
        SolidBrush brush = new SolidBrush(Color.Black);
        if (hotItem == e.Index)
        {
            brush = new SolidBrush(Color.Firebrick);
        }
         
        Font font = new Font(e.Font, FontStyle.Underline);
        if (e.Index <= this.Items.Count - 1)
        {
            //prevents index of 0 from attempting to draw in designer.
            e.Graphics.DrawString(this.Items[e.Index].ToString(), font, brush, e.Bounds);
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
        this.components = new System.ComponentModel.Container();
    }

}


