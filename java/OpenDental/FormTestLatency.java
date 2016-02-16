//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Prefs;

public class FormTestLatency  extends Form 
{

    public FormTestLatency() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTestLatency_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butLatency_Click(Object sender, EventArgs e) throws Exception {
        Stopwatch watch = new Stopwatch();
        Cursor = Cursors.WaitCursor;
        watch.Start();
        MiscData.getMySqlVersion();
        //a nice short query and small dataset.
        watch.Stop();
        textLatency.Text = watch.ElapsedMilliseconds.ToString();
        Cursor = Cursors.Default;
    }

    private void butSpeed_Click(Object sender, EventArgs e) throws Exception {
        Stopwatch watch = new Stopwatch();
        Cursor = Cursors.WaitCursor;
        watch.Start();
        MiscData.getMySqlVersion();
        //a nice short query and small dataset.
        watch.Stop();
        long latency = watch.ElapsedMilliseconds;
        watch.Restart();
        Prefs.refreshCache();
        watch.Stop();
        long speed = watch.ElapsedMilliseconds - latency;
        textSpeed.Text = speed.ToString();
        Cursor = Cursors.Default;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.label1 = new System.Windows.Forms.Label();
        this.textLatency = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butLatency = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.label3 = new System.Windows.Forms.Label();
        this.butSpeed = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.textSpeed = new System.Windows.Forms.TextBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(132, 47);
        this.label1.TabIndex = 5;
        this.label1.Text = "Roundtrip time for a very small query to the database.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textLatency
        //
        this.textLatency.Location = new System.Drawing.Point(9, 71);
        this.textLatency.Name = "textLatency";
        this.textLatency.ReadOnly = true;
        this.textLatency.Size = new System.Drawing.Size(55, 20);
        this.textLatency.TabIndex = 6;
        this.textLatency.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(66, 72);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(53, 17);
        this.label2.TabIndex = 7;
        this.label2.Text = "ms.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butLatency
        //
        this.butLatency.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLatency.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butLatency.setAutosize(true);
        this.butLatency.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLatency.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLatency.setCornerRadius(4F);
        this.butLatency.Location = new System.Drawing.Point(8, 100);
        this.butLatency.Name = "butLatency";
        this.butLatency.Size = new System.Drawing.Size(70, 24);
        this.butLatency.TabIndex = 4;
        this.butLatency.Text = "Test";
        this.butLatency.Click += new System.EventHandler(this.butLatency_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(314, 162);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butLatency);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.textLatency);
        this.groupBox1.Location = new System.Drawing.Point(12, 12);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(157, 132);
        this.groupBox1.TabIndex = 8;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Latency";
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.butSpeed);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.textSpeed);
        this.groupBox2.Location = new System.Drawing.Point(193, 12);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(157, 132);
        this.groupBox2.TabIndex = 9;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Speed";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(6, 19);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(132, 42);
        this.label3.TabIndex = 5;
        this.label3.Text = "For a larger database query.  The latency time is already subtracted.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSpeed
        //
        this.butSpeed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSpeed.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSpeed.setAutosize(true);
        this.butSpeed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSpeed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSpeed.setCornerRadius(4F);
        this.butSpeed.Location = new System.Drawing.Point(8, 100);
        this.butSpeed.Name = "butSpeed";
        this.butSpeed.Size = new System.Drawing.Size(70, 24);
        this.butSpeed.TabIndex = 4;
        this.butSpeed.Text = "Test";
        this.butSpeed.Click += new System.EventHandler(this.butSpeed_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(66, 72);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(53, 17);
        this.label4.TabIndex = 7;
        this.label4.Text = "Mbps";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textSpeed
        //
        this.textSpeed.Location = new System.Drawing.Point(9, 71);
        this.textSpeed.Name = "textSpeed";
        this.textSpeed.ReadOnly = true;
        this.textSpeed.Size = new System.Drawing.Size(55, 20);
        this.textSpeed.TabIndex = 6;
        this.textSpeed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // FormTestLatency
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(401, 198);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butClose);
        this.Name = "FormTestLatency";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Roundtrip latency";
        this.Load += new System.EventHandler(this.FormTestLatency_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butLatency;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLatency = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSpeed;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpeed = new System.Windows.Forms.TextBox();
}


