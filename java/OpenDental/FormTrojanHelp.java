//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:56 PM
//

package OpenDental;

import OpenDental.FormTrojanHelp;
import OpenDental.Lan;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTrojanHelp  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private RichTextBox textMain = new RichTextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormTrojanHelp() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTrojanHelp.class);
        this.butCancel = new OpenDental.UI.Button();
        this.textMain = new System.Windows.Forms.RichTextBox();
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
        this.butCancel.Location = new System.Drawing.Point(530, 386);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textMain
        //
        this.textMain.Location = new System.Drawing.Point(12, 12);
        this.textMain.Name = "textMain";
        this.textMain.Size = new System.Drawing.Size(592, 350);
        this.textMain.TabIndex = 1;
        this.textMain.Text = resources.GetString("textMain.Text");
        //
        // FormTrojanHelp
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(617, 424);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTrojanHelp";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Help";
        this.Load += new System.EventHandler(this.FormTrojanHelp_Load);
        this.ResumeLayout(false);
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formTrojanHelp_Load(Object sender, EventArgs e) throws Exception {
        textMain.Select(0, 31);
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
        textMain.Select(323, 20);
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
        textMain.Select(571, 5);
        //skip
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
        textMain.Select(933, 18);
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
        textMain.Select(1302, 31);
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
        textMain.Select(1473, 10);
        textMain.SelectionFont = new Font(Font, FontStyle.Bold);
    }

}


