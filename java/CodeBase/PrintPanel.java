//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package CodeBase;


public class PrintPanel  extends UserControl 
{

    private Bitmap backImage = new Bitmap();
    /**
    * Draw to this back buffer, then the window display will take care of the rest.
    */
    public Graphics backBuffer = new Graphics();
    public PrintPanel() throws Exception {
        initializeComponent();
        backImage = new Bitmap(this.Width, this.Height);
        backBuffer = Graphics.FromImage(backImage);
    }

    public Point getOrigin() throws Exception {
        return new Point((int)backBuffer.Transform.OffsetX, (int)backBuffer.Transform.OffsetY);
    }

    public void setOrigin(Point value) throws Exception {
        backBuffer.TranslateTransform(value.X - backBuffer.Transform.OffsetX, value.Y - backBuffer.Transform.OffsetY);
    }

    public void clear() throws Exception {
        backBuffer.Clear(this.BackColor);
    }

    private void printPanel_SizeChanged(Object sender, EventArgs e) throws Exception {
        //This is required so that panelSurface_SizeChanged is called and so that the print area
        //remains the same as for the entire printpanel control (so no background is visible).
        panelSurface.Size = this.Size;
    }

    private void panelSurface_Paint(Object sender, PaintEventArgs e) throws Exception {
        e.Graphics.DrawImageUnscaled(backImage, 0, 0);
    }

    private void panelSurface_SizeChanged(Object sender, EventArgs e) throws Exception {
        //We must resize the back buffer image if the size of the control increases so that anything drawn on
        //this panel will be saved in the back buffer. If the size of the control shrinks, we do nothing,
        //so that we can preserve any printed information.
        Bitmap newBuffer = null;
        if (this.Width > backImage.Width)
        {
            if (this.Height >= backImage.Height)
            {
                newBuffer = new Bitmap(this.Width, this.Height);
            }
            else
            {
                newBuffer = new Bitmap(this.Width, backImage.Height);
            } 
        }
        else if (this.Height > backImage.Height)
        {
            newBuffer = new Bitmap(backImage.Width, this.Height);
        }
          
        if (newBuffer != null)
        {
            //The buffer was changed
            Graphics g = Graphics.FromImage(newBuffer);
            g.DrawImageUnscaled(backImage, new Point(0, 0));
            backBuffer.Dispose();
            backImage.Dispose();
            backImage = newBuffer;
            backBuffer = g;
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
        this.panelSurface = new System.Windows.Forms.Panel();
        this.SuspendLayout();
        //
        // panelSurface
        //
        this.panelSurface.Location = new System.Drawing.Point(0, 0);
        this.panelSurface.Name = "panelSurface";
        this.panelSurface.Size = new System.Drawing.Size(850, 1100);
        this.panelSurface.TabIndex = 0;
        this.panelSurface.Paint += new System.Windows.Forms.PaintEventHandler(this.panelSurface_Paint);
        this.panelSurface.SizeChanged += new System.EventHandler(this.panelSurface_SizeChanged);
        //
        // PrintPanel
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.BackColor = System.Drawing.Color.White;
        this.Controls.Add(this.panelSurface);
        this.DoubleBuffered = true;
        this.Name = "PrintPanel";
        this.Size = new System.Drawing.Size(850, 1100);
        this.SizeChanged += new System.EventHandler(this.PrintPanel_SizeChanged);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Panel panelSurface = new System.Windows.Forms.Panel();
}


