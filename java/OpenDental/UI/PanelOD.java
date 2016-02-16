//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;


public class PanelOD  extends System.Windows.Forms.Panel 
{

    private Color borderColor = new Color();
    public PanelOD() throws Exception {
        initializeComponent();
        borderColor = Color.Silver;
    }

    /**
    * The color of the border.
    */
    public Color getBorderColor() throws Exception {
        return borderColor;
    }

    public void setBorderColor(Color value) throws Exception {
        borderColor = value;
    }

    protected void onPaint(PaintEventArgs e) throws Exception {
        super.OnPaint(e);
        e.Graphics.FillRectangle(new SolidBrush(BackColor), 0, 0, Width, Height);
        e.Graphics.DrawRectangle(new Pen(borderColor), 0, 0, Width - 1, Height - 1);
    }

    protected void onResize(EventArgs eventargs) throws Exception {
        super.OnResize(eventargs);
        this.Invalidate();
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


