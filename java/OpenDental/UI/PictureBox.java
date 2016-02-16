//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;


/**
* Better and simpler than the MS picturebox.  Always resizes the image to fit in the box.  Never crops or stretches.
*/
public class PictureBox  extends System.Windows.Forms.Control 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Image image = new Image();
    private String textNullImage = new String();
    /**
    * 
    */
    public PictureBox() throws Exception {
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
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

    /**
    * 
    */
    public void setImage(Image value) throws Exception {
        image = value;
        Invalidate();
    }

    public Image getImage() throws Exception {
        return image;
    }

    /**
    * 
    */
    public void setTextNullImage(String value) throws Exception {
        textNullImage = value;
        Invalidate();
    }

    public String getTextNullImage() throws Exception {
        return textNullImage;
    }

    /**
    * 
    */
    protected void onPaint(PaintEventArgs e) throws Exception {
        super.OnPaint(e);
        Graphics g = e.Graphics;
        g.InterpolationMode = InterpolationMode.High;
        g.DrawRectangle(Pens.Gray, 0, 0, Width - 1, Height - 1);
        if (image == null)
        {
            StringFormat format = new StringFormat();
            format.Alignment = StringAlignment.Center;
            format.LineAlignment = StringAlignment.Center;
            g.DrawString(textNullImage, this.Font, new SolidBrush(Color.Gray), new RectangleF(0, 0, Width, Height), format);
        }
        else
        {
            float ratio = new float();
            //Debug.WriteLine("Hratio:"+(float)image.Height/(float)Height+"Wratio:"+(float)image.Width/(float)Width);
            if ((float)image.Height / (float)Height > (float)image.Width / (float)Width)
            {
                //Image is proportionally taller
                ratio = (float)Height / (float)image.Height;
                g.DrawImage(image, new RectangleF(Width / 2 - ((float)image.Width * ratio) / 2, 0, (float)image.Width * ratio, Height));
            }
            else
            {
                //image proportionally wider
                ratio = (float)Width / (float)image.Width;
                g.DrawImage(image, new RectangleF(0, (float)Height / 2 - ((float)image.Height * ratio) / 2, Width, (float)image.Height * ratio));
            } 
        } 
    }

    /**
    * 
    */
    protected void onResize(EventArgs e) throws Exception {
        super.OnResize(e);
        Invalidate();
    }

}


