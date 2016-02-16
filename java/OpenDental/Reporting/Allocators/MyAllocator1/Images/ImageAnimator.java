//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1.Images;



/**
* Creates a simple txbox for animation of images.  Uses 100ms for animation speed.  If you want to change this
* just add it to the code to change the timer.  Size change of the control changes the size of the image box.
*/
public class ImageAnimator  extends UserControl 
{

    private Image[] _Images = null;
    private int _CurrentImageIndex = 0;
    public ImageAnimator() throws Exception {
        initializeComponent();
        this.ImageBox.Size = this.Size;
    }

    /**
    * If SET_IMAGES != NULL  animation starts using standard timer
    */
    public void startAnimation() throws Exception {
        if (_Images != null)
            this.LoopTimer.Start();
         
    }

    /**
    * Stops the timer that creates the animation.
    */
    public void stopAnimation() throws Exception {
        this.LoopTimer.Start();
    }

    private void loopTimer_Tick(Object sender, EventArgs e) throws Exception {
        if (_Images != null)
        {
            _CurrentImageIndex++;
            if (_CurrentImageIndex >= _Images.Length)
                _CurrentImageIndex = 0;
             
            if (_Images[_CurrentImageIndex] != null)
                this.ImageBox.Image = _Images[_CurrentImageIndex];
             
        }
         
    }

    private void imageAnimator_SizeChanged(Object sender, EventArgs e) throws Exception {
        this.ImageBox.Size = this.Size;
    }

    /**
    * Gets or Sets the Images for animating
    */
    public void setSET_IMAGES(Image[] value) throws Exception {
        this._Images = value;
        if (value == null)
            this.LoopTimer.Stop();
         
    }

    public Image[] getSET_IMAGES() throws Exception {
        return this._Images;
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
        this.LoopTimer = new System.Windows.Forms.Timer(this.components);
        this.ImageBox = new System.Windows.Forms.PictureBox();
        ((System.ComponentModel.ISupportInitialize)(this.ImageBox)).BeginInit();
        this.SuspendLayout();
        //
        // LoopTimer
        //
        this.LoopTimer.Tick += new System.EventHandler(this.LoopTimer_Tick);
        //
        // ImageBox
        //
        this.ImageBox.Location = new System.Drawing.Point(0, 0);
        this.ImageBox.Margin = new System.Windows.Forms.Padding(0);
        this.ImageBox.Name = "ImageBox";
        this.ImageBox.Size = new System.Drawing.Size(50, 50);
        this.ImageBox.TabIndex = 0;
        this.ImageBox.TabStop = false;
        //
        // ImageAnimator
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.ImageBox);
        this.Name = "ImageAnimator";
        this.Size = new System.Drawing.Size(50, 50);
        this.SizeChanged += new System.EventHandler(this.ImageAnimator_SizeChanged);
        ((System.ComponentModel.ISupportInitialize)(this.ImageBox)).EndInit();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Timer LoopTimer = new System.Windows.Forms.Timer();
    private System.Windows.Forms.PictureBox ImageBox = new System.Windows.Forms.PictureBox();
}


