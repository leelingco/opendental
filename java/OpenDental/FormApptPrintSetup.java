//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

public class FormApptPrintSetup  extends Form 
{

    public DateTime ApptPrintStartTime = new DateTime();
    public DateTime ApptPrintStopTime = new DateTime();
    public int ApptPrintFontSize = new int();
    public int ApptPrintColsPerPage = new int();
    public FormApptPrintSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formApptPrintSetup_Load(Object sender, EventArgs e) throws Exception {
        textStartTime.Text = PrefC.getDateT(PrefName.ApptPrintTimeStart).ToShortTimeString();
        textStopTime.Text = PrefC.getDateT(PrefName.ApptPrintTimeStop).ToShortTimeString();
        textFontSize.Text = PrefC.getString(PrefName.ApptPrintFontSize);
        textColumnsPerPage.Text = PrefC.getInt(PrefName.ApptPrintColumnsPerPage).ToString();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        if (!validEntries())
        {
            return ;
        }
         
        saveChanges(false);
    }

    private boolean validEntries() throws Exception {
        DateTime start = PIn.DateT(textStartTime.Text);
        DateTime stop = PIn.DateT(textStopTime.Text);
        if (start.Minute > 0 || stop.Minute > 0)
        {
            MsgBox.show(this,"Please use hours only, no minutes.");
            return false;
        }
         
        if (stop.Hour == start.Hour)
        {
            //If stop time is the same as start time.
            MsgBox.show(this,"Start time must be different than stop time.");
            return false;
        }
         
        if (stop.Hour != 0 && stop.Hour < start.Hour)
        {
            //If stop time is earlier than start time.
            MsgBox.show(this,"Start time cannot exceed stop time.");
            return false;
        }
         
        if (start == DateTime.MinValue)
        {
            MsgBox.show(this,"Please enter a valid start time.");
            return false;
        }
         
        if (stop == DateTime.MinValue)
        {
            MsgBox.show(this,"Please enter a valid stop time.");
            return false;
        }
         
        if (!StringSupport.equals(textColumnsPerPage.errorProvider1.GetError(textColumnsPerPage), "") || !StringSupport.equals(textFontSize.errorProvider1.GetError(textFontSize), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (PIn.Int(textColumnsPerPage.Text) < 1)
        {
            MsgBox.show(this,"Columns per page cannot be 0 or less.");
            return false;
        }
         
        return true;
    }

    private void saveChanges(boolean suppressMessage) throws Exception {
        if (validEntries())
        {
            Prefs.UpdateDateT(PrefName.ApptPrintTimeStart, PIn.DateT(textStartTime.Text));
            Prefs.UpdateDateT(PrefName.ApptPrintTimeStop, PIn.DateT(textStopTime.Text));
            Prefs.UpdateString(PrefName.ApptPrintFontSize, textFontSize.Text);
            Prefs.UpdateInt(PrefName.ApptPrintColumnsPerPage, PIn.Int(textColumnsPerPage.Text));
            if (!suppressMessage)
            {
                MsgBox.show(this,"Settings saved.");
            }
             
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        boolean changed = false;
        if (!validEntries())
        {
            return ;
        }
         
        if (PIn.DateT(textStartTime.Text).Hour != PrefC.getDateT(PrefName.ApptPrintTimeStart).Hour || PIn.DateT(textStopTime.Text).Hour != PrefC.getDateT(PrefName.ApptPrintTimeStop).Hour || !StringSupport.equals(textFontSize.Text, PrefC.getString(PrefName.ApptPrintFontSize)) || textColumnsPerPage.Text != PrefC.getInt(PrefName.ApptPrintColumnsPerPage).ToString())
        {
            changed = true;
        }
         
        if (changed)
        {
            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Save the changes that were made?"))
            {
                saveChanges(true);
            }
             
        }
         
        ApptPrintStartTime = PIn.DateT(textStartTime.Text);
        ApptPrintStopTime = PIn.DateT(textStopTime.Text);
        ApptPrintFontSize = PIn.Int(textFontSize.Text);
        ApptPrintColsPerPage = PIn.Int(textColumnsPerPage.Text);
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
        this.labelColumnsPerPage = new System.Windows.Forms.Label();
        this.labelFontSize = new System.Windows.Forms.Label();
        this.labelStartTime = new System.Windows.Forms.Label();
        this.labelStopTime = new System.Windows.Forms.Label();
        this.textStopTime = new System.Windows.Forms.TextBox();
        this.textStartTime = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textFontSize = new OpenDental.ValidNum();
        this.butSave = new OpenDental.UI.Button();
        this.textColumnsPerPage = new OpenDental.ValidNumber();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // labelColumnsPerPage
        //
        this.labelColumnsPerPage.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelColumnsPerPage.Location = new System.Drawing.Point(12, 85);
        this.labelColumnsPerPage.Name = "labelColumnsPerPage";
        this.labelColumnsPerPage.Size = new System.Drawing.Size(128, 15);
        this.labelColumnsPerPage.TabIndex = 72;
        this.labelColumnsPerPage.Text = "Operatories per page";
        this.labelColumnsPerPage.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelFontSize
        //
        this.labelFontSize.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelFontSize.Location = new System.Drawing.Point(45, 111);
        this.labelFontSize.Name = "labelFontSize";
        this.labelFontSize.Size = new System.Drawing.Size(95, 15);
        this.labelFontSize.TabIndex = 74;
        this.labelFontSize.Text = "Font size";
        this.labelFontSize.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelStartTime
        //
        this.labelStartTime.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelStartTime.Location = new System.Drawing.Point(45, 33);
        this.labelStartTime.Name = "labelStartTime";
        this.labelStartTime.Size = new System.Drawing.Size(95, 15);
        this.labelStartTime.TabIndex = 76;
        this.labelStartTime.Text = "Start time";
        this.labelStartTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelStopTime
        //
        this.labelStopTime.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelStopTime.Location = new System.Drawing.Point(45, 59);
        this.labelStopTime.Name = "labelStopTime";
        this.labelStopTime.Size = new System.Drawing.Size(95, 15);
        this.labelStopTime.TabIndex = 78;
        this.labelStopTime.Text = "Stop time";
        this.labelStopTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textStopTime
        //
        this.textStopTime.Location = new System.Drawing.Point(146, 56);
        this.textStopTime.Name = "textStopTime";
        this.textStopTime.Size = new System.Drawing.Size(75, 20);
        this.textStopTime.TabIndex = 83;
        //
        // textStartTime
        //
        this.textStartTime.Location = new System.Drawing.Point(146, 30);
        this.textStartTime.Name = "textStartTime";
        this.textStartTime.Size = new System.Drawing.Size(75, 20);
        this.textStartTime.TabIndex = 84;
        //
        // label1
        //
        this.label1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label1.Location = new System.Drawing.Point(227, 33);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(128, 15);
        this.label1.TabIndex = 86;
        this.label1.Text = "Example: 5:00 AM";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(227, 59);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(128, 15);
        this.label2.TabIndex = 87;
        this.label2.Text = "Example: 8:00 PM";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textFontSize
        //
        this.textFontSize.Location = new System.Drawing.Point(146, 108);
        this.textFontSize.setMaxVal(50);
        this.textFontSize.setMinVal(2);
        this.textFontSize.Name = "textFontSize";
        this.textFontSize.Size = new System.Drawing.Size(50, 20);
        this.textFontSize.TabIndex = 88;
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(12, 168);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(75, 24);
        this.butSave.TabIndex = 82;
        this.butSave.Text = "Save";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // textColumnsPerPage
        //
        this.textColumnsPerPage.Location = new System.Drawing.Point(146, 82);
        this.textColumnsPerPage.setMaxVal(255);
        this.textColumnsPerPage.setMinVal(0);
        this.textColumnsPerPage.Name = "textColumnsPerPage";
        this.textColumnsPerPage.Size = new System.Drawing.Size(50, 20);
        this.textColumnsPerPage.TabIndex = 73;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(197, 168);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(280, 168);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label3
        //
        this.label3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label3.Location = new System.Drawing.Point(202, 112);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(128, 15);
        this.label3.TabIndex = 89;
        this.label3.Text = "Between 2 and 50";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormApptPrintSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(367, 204);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textFontSize);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textStartTime);
        this.Controls.Add(this.textStopTime);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.labelStopTime);
        this.Controls.Add(this.labelStartTime);
        this.Controls.Add(this.labelFontSize);
        this.Controls.Add(this.textColumnsPerPage);
        this.Controls.Add(this.labelColumnsPerPage);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormApptPrintSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Form Appt Print Setup";
        this.Load += new System.EventHandler(this.FormApptPrintSetup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelColumnsPerPage = new System.Windows.Forms.Label();
    private OpenDental.ValidNumber textColumnsPerPage;
    private System.Windows.Forms.Label labelFontSize = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelStartTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelStopTime = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSave;
    private System.Windows.Forms.TextBox textStopTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textStartTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textFontSize;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


