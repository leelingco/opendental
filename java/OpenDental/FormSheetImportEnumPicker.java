//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;

public class FormSheetImportEnumPicker  extends Form 
{

    public boolean ShowClearButton = new boolean();
    public FormSheetImportEnumPicker(String prompt) throws Exception {
        initializeComponent();
        Lan.F(this);
        labelPrompt.Text = prompt;
    }

    private void formSheetImportEnumPicker_Load(Object sender, EventArgs e) throws Exception {
        if (!ShowClearButton)
        {
            butClear.Visible = false;
        }
         
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        listResult.SelectedIndex = -1;
        DialogResult = DialogResult.OK;
    }

    private void listResult_DoubleClick(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
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
        this.labelPrompt = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butClear = new OpenDental.UI.Button();
        this.listResult = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
        //
        // labelPrompt
        //
        this.labelPrompt.Location = new System.Drawing.Point(31, 10);
        this.labelPrompt.Name = "labelPrompt";
        this.labelPrompt.Size = new System.Drawing.Size(317, 36);
        this.labelPrompt.TabIndex = 5;
        this.labelPrompt.Text = "labelPrompt";
        this.labelPrompt.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(273, 274);
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
        this.butCancel.Location = new System.Drawing.Point(273, 304);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Location = new System.Drawing.Point(273, 49);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(75, 24);
        this.butClear.TabIndex = 6;
        this.butClear.Text = "Clear Val";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // listResult
        //
        this.listResult.FormattingEnabled = true;
        this.listResult.Location = new System.Drawing.Point(34, 49);
        this.listResult.Name = "listResult";
        this.listResult.Size = new System.Drawing.Size(179, 277);
        this.listResult.TabIndex = 7;
        this.listResult.DoubleClick += new System.EventHandler(this.listResult_DoubleClick);
        //
        // FormSheetImportEnumPicker
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(360, 340);
        this.Controls.Add(this.listResult);
        this.Controls.Add(this.butClear);
        this.Controls.Add(this.labelPrompt);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetImportEnumPicker";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormSheetImportEnumPicker_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    public System.Windows.Forms.Label labelPrompt = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butClear;
    public System.Windows.Forms.ListBox listResult = new System.Windows.Forms.ListBox();
}

