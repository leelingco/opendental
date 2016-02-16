//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:02 PM
//

package OpenDental;

import OpenDental.InputBox;
import OpenDental.Lan;

/**
* A quick entry form for various purposes.  Pull the result from textResult.Text.
*/
public class InputBox  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * 
    */
    public Label labelPrompt = new Label();
    /**
    * 
    */
    public TextBox textResult = new TextBox();
    public ComboBox comboSelection = new ComboBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public InputBox(String prompt) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        labelPrompt.Text = prompt;
        Lan.f(this);
    }

    /**
    * This constructor allows a list of strings to be sent in and fill a comboBox for users to select from.  The comboBox is in place of the textResult text box.
    */
    public InputBox(String prompt, List<String> listSelections) throws Exception {
        initializeComponent();
        labelPrompt.Text = prompt;
        comboSelection.Location = textResult.Location;
        textResult.Visible = false;
        comboSelection.Visible = true;
        for (int i = 0;i < listSelections.Count;i++)
        {
            comboSelection.Items.Add(listSelections[i]);
        }
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(InputBox.class);
        this.labelPrompt = new System.Windows.Forms.Label();
        this.textResult = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.comboSelection = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // labelPrompt
        //
        this.labelPrompt.Location = new System.Drawing.Point(31, 10);
        this.labelPrompt.Name = "labelPrompt";
        this.labelPrompt.Size = new System.Drawing.Size(387, 36);
        this.labelPrompt.TabIndex = 2;
        this.labelPrompt.Text = "labelPrompt";
        this.labelPrompt.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textResult
        //
        this.textResult.Location = new System.Drawing.Point(32, 51);
        this.textResult.Name = "textResult";
        this.textResult.Size = new System.Drawing.Size(385, 20);
        this.textResult.TabIndex = 0;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(262, 107);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(343, 107);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // comboSelection
        //
        this.comboSelection.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSelection.DropDownWidth = 395;
        this.comboSelection.Location = new System.Drawing.Point(32, 77);
        this.comboSelection.MaxDropDownItems = 30;
        this.comboSelection.Name = "comboSelection";
        this.comboSelection.Size = new System.Drawing.Size(386, 21);
        this.comboSelection.TabIndex = 3;
        this.comboSelection.Visible = false;
        //
        // InputBox
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(430, 145);
        this.Controls.Add(this.comboSelection);
        this.Controls.Add(this.textResult);
        this.Controls.Add(this.labelPrompt);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "InputBox";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Input";
        this.Load += new System.EventHandler(this.InputBox_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void inputBox_Load(Object sender, EventArgs e) throws Exception {
    }

}


