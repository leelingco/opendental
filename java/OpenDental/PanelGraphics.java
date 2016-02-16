//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;



/**
* A panel that does not paint it's background.  This allows for better double buffering and for a true transparent panel.
*/
public class PanelGraphics  extends System.Windows.Forms.Panel 
{

    public PanelGraphics() throws Exception {
        initializeComponent();
    }

    protected void onPaintBackground(PaintEventArgs e) throws Exception {
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
//base.OnPaintBackground(e);//this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;

//base.OnPaintBackground(e);//this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;