//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.Bridges;

import OpenDental.Bridges.NewPatientForm;

public class NewPatientForm   
{

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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(NewPatientForm.class);
        this.txtResults = new System.Windows.Forms.TextBox();
        this.btnClose = new OpenDental.UI.Button();
        this.btnImportForms = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // txtResults
        //
        this.txtResults.Location = new System.Drawing.Point(1, 3);
        this.txtResults.Multiline = true;
        this.txtResults.Name = "txtResults";
        this.txtResults.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.txtResults.Size = new System.Drawing.Size(276, 284);
        this.txtResults.TabIndex = 0;
        //
        // btnClose
        //
        this.btnClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.btnClose.setAutosize(true);
        this.btnClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.btnClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.btnClose.setCornerRadius(4F);
        this.btnClose.Location = new System.Drawing.Point(144, 294);
        this.btnClose.Name = "btnClose";
        this.btnClose.Size = new System.Drawing.Size(77, 21);
        this.btnClose.TabIndex = 3;
        this.btnClose.Text = "Close";
        this.btnClose.UseVisualStyleBackColor = true;
        this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
        //
        // btnImportForms
        //
        this.btnImportForms.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.btnImportForms.setAutosize(true);
        this.btnImportForms.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.btnImportForms.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.btnImportForms.setCornerRadius(4F);
        this.btnImportForms.Location = new System.Drawing.Point(38, 293);
        this.btnImportForms.Name = "btnImportForms";
        this.btnImportForms.Size = new System.Drawing.Size(75, 23);
        this.btnImportForms.TabIndex = 2;
        this.btnImportForms.Text = "Import";
        this.btnImportForms.UseVisualStyleBackColor = true;
        this.btnImportForms.Click += new System.EventHandler(this.btnImportForms_Click);
        //
        // NewPatientForm
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(277, 320);
        this.Controls.Add(this.btnClose);
        this.Controls.Add(this.btnImportForms);
        this.Controls.Add(this.txtResults);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "NewPatientForm";
        this.Text = "NewPatientForm.com";
        this.Load += new System.EventHandler(this.NewPatientForm_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox txtResults = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button btnImportForms;
    private OpenDental.UI.Button btnClose;
}


