//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.EhrAmendment;

public class FormImages  extends Form 
{

    /**
    * Right now, this form only supports claimpayment and amendment mode.   Others will be added later.
    */
    public long ClaimPaymentNum = new long();
    public EhrAmendment EhrAmendmentCur;
    public FormImages() throws Exception {
        initializeComponent();
        Lan.F(this);
        contrImagesMain.CloseClick += new EventHandler(contrImagesMain_CloseClick);
    }

    private void formImages_Load(Object sender, EventArgs e) throws Exception {
        contrImagesMain.initializeOnStartup();
        if (ClaimPaymentNum != 0)
        {
            contrImagesMain.moduleSelectedClaimPayment(ClaimPaymentNum);
        }
        else if (EhrAmendmentCur != null)
        {
            contrImagesMain.moduleSelectedAmendment(EhrAmendmentCur);
        }
          
    }

    void contrImagesMain_CloseClick(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.contrImagesMain = new OpenDental.ContrImages();
        this.SuspendLayout();
        //
        // contrImagesMain
        //
        this.contrImagesMain.Dock = System.Windows.Forms.DockStyle.Fill;
        this.contrImagesMain.Location = new System.Drawing.Point(0, 0);
        this.contrImagesMain.Name = "contrImagesMain";
        this.contrImagesMain.Size = new System.Drawing.Size(1166, 778);
        this.contrImagesMain.TabIndex = 0;
        //
        // FormImages
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1166, 778);
        this.Controls.Add(this.contrImagesMain);
        this.Name = "FormImages";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Images";
        this.Load += new System.EventHandler(this.FormImages_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.ContrImages contrImagesMain;
}


