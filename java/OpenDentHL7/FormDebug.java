//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentHL7;

import OpenDentHL7.ServiceHL7;


//WARNING!!  When run in debug from VS, must be run as admin.
public class FormDebug  extends Form 
{

    String ServiceName = new String();
    //just for debug
    public FormDebug(String serviceName) throws Exception {
        ServiceName = serviceName;
        initializeComponent();
    }

    private void formDebug_Load(Object sender, EventArgs e) throws Exception {
        ServiceHL7 service = new ServiceHL7();
        service.ServiceName = ServiceName;
        try
        {
            service.startManually();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
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
        this.SuspendLayout();
        //
        // FormDebug
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(284, 264);
        this.Name = "FormDebug";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormDebug";
        this.Load += new System.EventHandler(this.FormDebug_Load);
        this.ResumeLayout(false);
    }

}


