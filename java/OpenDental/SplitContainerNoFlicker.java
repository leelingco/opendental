//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;



/**
* This control prevents flicker in a SplitContainer by setting protected style of child panels.
*/
public class SplitContainerNoFlicker  extends SplitContainer 
{

    public SplitContainerNoFlicker() throws Exception {
        initializeComponent();
        MethodInfo mi = Control.class.GetMethod("SetStyle", BindingFlags.NonPublic | BindingFlags.Instance);
        Object[] args = new Object[]{ ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true };
        mi.Invoke(this.Panel1, args);
        mi.Invoke(this.Panel2, args);
        this.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
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
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
    }

}


