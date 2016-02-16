//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:19 PM
//

package OpenDental;

import OpenDental.FormLogoffWarning;
import OpenDental.Lan;

/**
* 
*/
public class FormLogoffWarning  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Timer timer = new System.Windows.Forms.Timer();
    private OpenDental.UI.Button butCancel;
    private Label label1 = new Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    /**
    * 
    */
    public FormLogoffWarning() throws Exception {
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLogoffWarning.class);
        this.timer = new System.Windows.Forms.Timer(this.components);
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // timer
        //
        this.timer.Enabled = true;
        this.timer.Interval = 10000;
        this.timer.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(293, 73);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 34);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(385, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Open Dental will log off from this workstation in 10 seconds due to inactivity.";
        //
        // FormLogoffWarning
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(397, 115);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLogoffWarning";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Logoff Warning";
        this.TopMost = true;
        this.ResumeLayout(false);
    }

    private void formLogoff_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


