//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormAutoNotePromptPreview;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;

public class FormAutoNotePromptText  extends Form 
{

    /**
    * Set this value externally.
    */
    public String PromptText = new String();
    /**
    * What the user entered.  This can be set externally to the default value.
    */
    public String ResultText = new String();
    public FormAutoNotePromptText() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAutoNotePromptText_Load(Object sender, EventArgs e) throws Exception {
        Location = new Point(Left, Top + 150);
        labelPrompt.Text = PromptText;
        textMain.Text = ResultText;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        ResultText = textMain.Text;
        DialogResult = DialogResult.OK;
    }

    private void butSkip_Click(Object sender, EventArgs e) throws Exception {
        ResultText = "";
        DialogResult = DialogResult.OK;
    }

    private void butPreview_Click(Object sender, EventArgs e) throws Exception {
        ResultText = textMain.Text;
        FormAutoNotePromptPreview FormP = new FormAutoNotePromptPreview();
        FormP.ResultText = ResultText;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.OK)
        {
            ResultText = FormP.ResultText;
            DialogResult = DialogResult.OK;
        }
         
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Abort autonote entry?"))
        {
            return ;
        }
         
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
        this.textMain = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butSkip = new OpenDental.UI.Button();
        this.butPreview = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelPrompt
        //
        this.labelPrompt.Location = new System.Drawing.Point(12, 3);
        this.labelPrompt.Name = "labelPrompt";
        this.labelPrompt.Size = new System.Drawing.Size(399, 56);
        this.labelPrompt.TabIndex = 114;
        this.labelPrompt.Text = "Prompt";
        this.labelPrompt.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textMain
        //
        this.textMain.AcceptsReturn = true;
        this.textMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textMain.HideSelection = false;
        this.textMain.Location = new System.Drawing.Point(12, 62);
        this.textMain.Multiline = true;
        this.textMain.Name = "textMain";
        this.textMain.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textMain.Size = new System.Drawing.Size(385, 212);
        this.textMain.TabIndex = 113;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(79, 292);
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
        this.butCancel.Location = new System.Drawing.Point(322, 292);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butSkip
        //
        this.butSkip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSkip.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSkip.setAutosize(true);
        this.butSkip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSkip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSkip.setCornerRadius(4F);
        this.butSkip.Location = new System.Drawing.Point(160, 292);
        this.butSkip.Name = "butSkip";
        this.butSkip.Size = new System.Drawing.Size(75, 24);
        this.butSkip.TabIndex = 117;
        this.butSkip.Text = "Skip";
        this.butSkip.Click += new System.EventHandler(this.butSkip_Click);
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.Location = new System.Drawing.Point(241, 292);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(75, 24);
        this.butPreview.TabIndex = 118;
        this.butPreview.Text = "Preview";
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // FormAutoNotePromptText
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(409, 328);
        this.Controls.Add(this.butPreview);
        this.Controls.Add(this.butSkip);
        this.Controls.Add(this.labelPrompt);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormAutoNotePromptText";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Text Prompt";
        this.Load += new System.EventHandler(this.FormAutoNotePromptText_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelPrompt = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMain = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butSkip;
    private OpenDental.UI.Button butPreview;
}


