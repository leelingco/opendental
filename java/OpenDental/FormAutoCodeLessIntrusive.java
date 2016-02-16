//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:41 PM
//

package OpenDental;

import OpenDental.FormAutoCodeLessIntrusive;
import OpenDental.Lan;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAutoCodeLessIntrusive  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelMain = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butNo;
    private OpenDental.UI.Button butYes;
    private Label labelPrompt = new Label();
    /**
    * The text to display in this dialog
    */
    public String mainText = new String();
    /**
    * 
    */
    public FormAutoCodeLessIntrusive() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.F(this, new Control[]{ labelMain });
    }

    //labelMain is translated from calling Form (FormProcEdit)
    /**
    * Clean up any resources being used.
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoCodeLessIntrusive.class);
        this.butNo = new OpenDental.UI.Button();
        this.butYes = new OpenDental.UI.Button();
        this.labelMain = new System.Windows.Forms.Label();
        this.labelPrompt = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butNo
        //
        this.butNo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNo.setAutosize(true);
        this.butNo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNo.setCornerRadius(4F);
        this.butNo.Location = new System.Drawing.Point(406, 169);
        this.butNo.Name = "butNo";
        this.butNo.Size = new System.Drawing.Size(75, 26);
        this.butNo.TabIndex = 0;
        this.butNo.Text = "&No";
        this.butNo.Click += new System.EventHandler(this.butNo_Click);
        //
        // butYes
        //
        this.butYes.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butYes.setAutosize(true);
        this.butYes.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butYes.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butYes.setCornerRadius(4F);
        this.butYes.Location = new System.Drawing.Point(406, 128);
        this.butYes.Name = "butYes";
        this.butYes.Size = new System.Drawing.Size(75, 26);
        this.butYes.TabIndex = 1;
        this.butYes.Text = "&Yes";
        this.butYes.Click += new System.EventHandler(this.butYes_Click);
        //
        // labelMain
        //
        this.labelMain.Location = new System.Drawing.Point(35, 32);
        this.labelMain.Name = "labelMain";
        this.labelMain.Size = new System.Drawing.Size(438, 73);
        this.labelMain.TabIndex = 3;
        this.labelMain.Text = "labelMain";
        this.labelMain.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelPrompt
        //
        this.labelPrompt.Location = new System.Drawing.Point(12, 152);
        this.labelPrompt.Name = "labelPrompt";
        this.labelPrompt.Size = new System.Drawing.Size(388, 43);
        this.labelPrompt.TabIndex = 4;
        this.labelPrompt.Text = "If you don\'t want to be prompted to change this type of procedure in the future, " + "then edit this Auto Code and check the box for \"Do not check codes...\"";
        //
        // FormAutoCodeLessIntrusive
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(511, 211);
        this.Controls.Add(this.labelPrompt);
        this.Controls.Add(this.labelMain);
        this.Controls.Add(this.butYes);
        this.Controls.Add(this.butNo);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutoCodeLessIntrusive";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Change Code?";
        this.Load += new System.EventHandler(this.FormAutoCodeLessIntrusive_Load);
        this.ResumeLayout(false);
    }

    private void formAutoCodeLessIntrusive_Load(Object sender, System.EventArgs e) throws Exception {
        labelMain.Text = mainText;
    }

    private void butYes_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butNo_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


