//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:20 PM
//

package OpenDental;

import OpenDental.FormMessageText;
import OpenDental.Lan;

/**
* 
*/
public class FormMessageText  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Timer timer2 = new System.Windows.Forms.Timer();
    /**
    * 
    */
    public System.Windows.Forms.TextBox Text2 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    /**
    * Not used anywhere.
    */
    public FormMessageText() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMessageText.class);
        this.Text2 = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.timer2 = new System.Windows.Forms.Timer(this.components);
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // Text2
        //
        this.Text2.AcceptsReturn = true;
        this.Text2.Location = new System.Drawing.Point(42, 65);
        this.Text2.Multiline = true;
        this.Text2.Name = "Text2";
        this.Text2.Size = new System.Drawing.Size(351, 84);
        this.Text2.TabIndex = 0;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(319, 197);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // timer2
        //
        this.timer2.Enabled = true;
        this.timer2.Interval = 6000;
        this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
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
        this.butCancel.Location = new System.Drawing.Point(319, 234);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormMessageText
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(438, 276);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.Text2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMessageText";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Message";
        this.Load += new System.EventHandler(this.FormMessageText_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formMessageText_Load(Object sender, System.EventArgs e) throws Exception {
        timer2.Start();
        Text2.SelectionLength = 0;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void timer2_Tick(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

}


