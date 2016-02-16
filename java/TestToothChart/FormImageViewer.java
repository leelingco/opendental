//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestToothChart;


public class FormImageViewer  extends Form 
{

    public Bitmap Bmp = new Bitmap();
    public FormImageViewer() throws Exception {
        initializeComponent();
    }

    private void formImageViewer_Load(Object sender, EventArgs e) throws Exception {
        Width = Bmp.Width + 16;
        Height = Bmp.Height + 36;
        pictureBox1.Image = Bmp;
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
        this.pictureBox1 = new System.Windows.Forms.PictureBox();
        ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
        this.SuspendLayout();
        //
        // pictureBox1
        //
        this.pictureBox1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.pictureBox1.Location = new System.Drawing.Point(0, 0);
        this.pictureBox1.Name = "pictureBox1";
        this.pictureBox1.Size = new System.Drawing.Size(284, 264);
        this.pictureBox1.TabIndex = 0;
        this.pictureBox1.TabStop = false;
        //
        // FormImageViewer
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(284, 264);
        this.Controls.Add(this.pictureBox1);
        this.Name = "FormImageViewer";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormImageViewer";
        this.Load += new System.EventHandler(this.FormImageViewer_Load);
        ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.PictureBox pictureBox1 = new System.Windows.Forms.PictureBox();
}


