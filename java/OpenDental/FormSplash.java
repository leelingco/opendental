//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Properties.Resources;
import OpenDentBusiness.Plugins;
import OpenDental.FormSplash;


/**
* 
*/
public class FormSplash  extends Form 
{

    /**
    * 
    */
    public FormSplash() throws Exception {
        initializeComponent();
    }

    private void formSplash_Load(Object sender, EventArgs e) throws Exception {
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            BackgroundImage = Resources.getsplashCanada();
        }
         
        if (File.Exists(Directory.GetCurrentDirectory() + "\\Splash.jpg"))
        {
            BackgroundImage = new Bitmap(Directory.GetCurrentDirectory() + "\\Splash.jpg");
        }
         
        if (Plugins.getPluginsAreLoaded())
        {
            Plugins.hookAddCode(this,"FormSplash.FormSplash_Load_end");
        }
         
    }

    /**
    * Required designer variable.
    */
    //private System.ComponentModel.IContainer components = null;
    /// </summary>
    /// Clean up any resources being used.
    /// </summary>
    /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
    //protected override void Dispose(bool disposing)
    //{
    //    if (disposing && (components != null))
    //    {
    //        components.Dispose();
    //    }
    //    base.Dispose(disposing);
    //}
    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSplash.class);
        this.SuspendLayout();
        //
        // FormSplash
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.AutoSize = true;
        this.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
        this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
        this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
        this.ClientSize = new System.Drawing.Size(500, 300);
        this.ControlBox = false;
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSplash";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Welcome to Open Dental";
        this.Load += new System.EventHandler(this.FormSplash_Load);
        this.ResumeLayout(false);
    }

}


