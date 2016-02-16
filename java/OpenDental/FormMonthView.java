//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;

public class FormMonthView  extends Form 
{

    public FormMonthView() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formMonthView_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
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
        this.butClose = new OpenDental.UI.Button();
        this.odGrid1 = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(844, 585);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // odGrid1
        //
        this.odGrid1.setHScrollVisible(false);
        this.odGrid1.Location = new System.Drawing.Point(12, 12);
        this.odGrid1.Name = "odGrid1";
        this.odGrid1.setScrollValue(0);
        this.odGrid1.Size = new System.Drawing.Size(907, 564);
        this.odGrid1.TabIndex = 3;
        this.odGrid1.setTitle(null);
        this.odGrid1.setTranslationName(null);
        //
        // FormMonthView
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(931, 618);
        this.Controls.Add(this.odGrid1);
        this.Controls.Add(this.butClose);
        this.Name = "FormMonthView";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Month View";
        this.Load += new System.EventHandler(this.FormMonthView_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid odGrid1;
}


