//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import OpenDental.FormRpPrintPreview;
import OpenDental.Lan;

/**
* 
*/
public class FormRpPrintPreview  extends System.Windows.Forms.Form 
{
    /**
    * 
    */
    public System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    private OpenDental.UI.Button button1;
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpPrintPreview() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPrintPreview.class);
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.button1 = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // printPreviewControl2
        //
        this.printPreviewControl2.AutoZoom = false;
        this.printPreviewControl2.Location = new System.Drawing.Point(0, 0);
        this.printPreviewControl2.Name = "printPreviewControl2";
        this.printPreviewControl2.Size = new System.Drawing.Size(842, 538);
        this.printPreviewControl2.TabIndex = 7;
        this.printPreviewControl2.Zoom = 1;
        //
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.setCornerRadius(4F);
        this.button1.Location = new System.Drawing.Point(323, 709);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(75, 23);
        this.button1.TabIndex = 8;
        this.button1.Text = "next page";
        this.button1.Click += new System.EventHandler(this.button1_Click);
        //
        // FormRpPrintPreview
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.AutoScroll = true;
        this.ClientSize = new System.Drawing.Size(842, 746);
        this.Controls.Add(this.button1);
        this.Controls.Add(this.printPreviewControl2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpPrintPreview";
        this.ShowInTaskbar = false;
        this.Text = "FormRpPrintPreview";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormRpPrintPreview_Load);
        this.ResumeLayout(false);
    }

    private void button1_Click(Object sender, System.EventArgs e) throws Exception {
        printPreviewControl2.StartPage++;
    }

    private void formRpPrintPreview_Load(Object sender, System.EventArgs e) throws Exception {
        button1.Location = new Point(this.ClientRectangle.Width - 100, this.ClientRectangle.Height - 30);
        printPreviewControl2.Height = this.ClientRectangle.Height - 40;
        printPreviewControl2.Width = this.ClientRectangle.Width;
        printPreviewControl2.Zoom = (double)printPreviewControl2.ClientSize.Height / 1100;
    }

}


