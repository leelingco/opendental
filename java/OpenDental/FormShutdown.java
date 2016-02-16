//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Computers;

public class FormShutdown  extends Form 
{

    /**
    * Set to true if part of the update process.  Makes it behave more discretely to avoid worrying people.
    */
    public boolean IsUpdate = new boolean();
    public FormShutdown() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formShutdown_Load(Object sender, EventArgs e) throws Exception {
        List<String> runningComps = Computers.getRunningComputers();
        for (int i = 0;i < runningComps.Count;i++)
        {
            listMain.Items.Add(runningComps[i]);
        }
        if (IsUpdate)
        {
            butShutdown.Text = Lan.g(this,"Continue");
            butCancel.Text = Lan.g(this,"Skip");
        }
         
    }

    //used to say "cancel" but still continues with the update, just skips the "shutdown all workstations" step.
    private void butShutdown_Click(Object sender, EventArgs e) throws Exception {
        if (IsUpdate)
        {
            DialogResult = DialogResult.OK;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Shutdown this program on all workstations except this one?  Users will be given a 15 second warning to save data."))
        {
            return ;
        }
         
        //happens outside this form
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.butCancel = new OpenDental.UI.Button();
        this.listMain = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butShutdown = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(326, 490);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listMain
        //
        this.listMain.FormattingEnabled = true;
        this.listMain.Location = new System.Drawing.Point(12, 29);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(278, 485);
        this.listMain.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(214, 16);
        this.label1.TabIndex = 5;
        this.label1.Text = "Workstations that might be running";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butShutdown
        //
        this.butShutdown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShutdown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butShutdown.setAutosize(true);
        this.butShutdown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShutdown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShutdown.setCornerRadius(4F);
        this.butShutdown.Location = new System.Drawing.Point(326, 450);
        this.butShutdown.Name = "butShutdown";
        this.butShutdown.Size = new System.Drawing.Size(75, 24);
        this.butShutdown.TabIndex = 6;
        this.butShutdown.Text = "Shutdown";
        this.butShutdown.Click += new System.EventHandler(this.butShutdown_Click);
        //
        // FormShutdown
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(422, 534);
        this.Controls.Add(this.butShutdown);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butCancel);
        this.Name = "FormShutdown";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Shutdown Workstations";
        this.Load += new System.EventHandler(this.FormShutdown_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butShutdown;
}


