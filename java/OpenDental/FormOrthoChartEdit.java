//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.OrthoChart;

public class FormOrthoChartEdit  extends Form 
{

    public OrthoChart OrthoCur;
    public boolean IsNew = new boolean();
    public FormOrthoChartEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
        OrthoCur = new OrthoChart();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formOrthoChartEdit_Load(Object sender, EventArgs e) throws Exception {
        textDateService.Text = OrthoCur.DateService.ToShortDateString();
        textFieldName.Text = OrthoCur.FieldName;
        textFieldValue.Text = OrthoCur.FieldValue;
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDateService = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textFieldName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textFieldValue = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(228, 260);
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
        this.butCancel.Location = new System.Drawing.Point(309, 260);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDateService
        //
        this.textDateService.Location = new System.Drawing.Point(125, 12);
        this.textDateService.Name = "textDateService";
        this.textDateService.ReadOnly = true;
        this.textDateService.Size = new System.Drawing.Size(259, 20);
        this.textDateService.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(16, 12);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(107, 20);
        this.label1.TabIndex = 5;
        this.label1.Text = "Date Service";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(16, 38);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(107, 20);
        this.label2.TabIndex = 7;
        this.label2.Text = "Field Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFieldName
        //
        this.textFieldName.Location = new System.Drawing.Point(125, 38);
        this.textFieldName.Name = "textFieldName";
        this.textFieldName.ReadOnly = true;
        this.textFieldName.Size = new System.Drawing.Size(259, 20);
        this.textFieldName.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 64);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(107, 20);
        this.label3.TabIndex = 9;
        this.label3.Text = "Field Value";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFieldValue
        //
        this.textFieldValue.Location = new System.Drawing.Point(125, 64);
        this.textFieldValue.Multiline = true;
        this.textFieldValue.Name = "textFieldValue";
        this.textFieldValue.Size = new System.Drawing.Size(259, 190);
        this.textFieldValue.TabIndex = 8;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(12, 260);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 10;
        this.butDelete.Text = "Delete";
        //
        // FormOrthoChartEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(396, 296);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textFieldValue);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textFieldName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDateService);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormOrthoChartEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormOrthoChartEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textDateService = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFieldName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFieldValue = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
}


