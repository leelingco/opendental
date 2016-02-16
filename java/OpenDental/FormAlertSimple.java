//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.FormAlertSimple;

public class FormAlertSimple  extends Form 
{

    /**
    * If message translation is desired, then translate before passing in the message.
    * Always use Show() instead of ShowDialog() and make sure to programmatically call Close(), because the user will not be able to close (no buttons are visible).
    */
    public FormAlertSimple(String strMsgText) throws Exception {
        initializeComponent();
        Lan.F(this);
        labelMsg.Text = strMsgText;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAlertSimple.class);
        this.labelMsg = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // labelMsg
        //
        this.labelMsg.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.labelMsg.Location = new System.Drawing.Point(12, 9);
        this.labelMsg.Name = "labelMsg";
        this.labelMsg.Size = new System.Drawing.Size(276, 82);
        this.labelMsg.TabIndex = 0;
        this.labelMsg.Text = "Message";
        this.labelMsg.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // FormAlertSimple
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(300, 100);
        this.Controls.Add(this.labelMsg);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAlertSimple";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Label labelMsg = new System.Windows.Forms.Label();
}
//After Lan.F, because the message will probably contain numbers and/or punctuation which we do not want to include in translation.

//After Lan.F, because the message will probably contain numbers and/or punctuation which we do not want to include in translation.