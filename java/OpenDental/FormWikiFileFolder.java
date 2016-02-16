//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;

public class FormWikiFileFolder  extends Form 
{

    public boolean IsFolderMode = new boolean();
    public String SelectedLink = new String();
    public FormWikiFileFolder() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiFileFolder_Load(Object sender, EventArgs e) throws Exception {
        if (IsFolderMode)
        {
            Text = Lan.g(this,"Insert Folder Link");
        }
         
    }

    private void butBrowse_Click(Object sender, EventArgs e) throws Exception {
        if (IsFolderMode)
        {
            FolderBrowserDialog folderBD = new FolderBrowserDialog();
            if (folderBD.ShowDialog() != DialogResult.OK)
            {
                return ;
            }
             
            textLink.Text = folderBD.SelectedPath;
            return ;
        }
         
        OpenFileDialog openFileD = new OpenFileDialog();
        if (openFileD.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        textLink.Text = openFileD.FileName;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (IsFolderMode)
        {
            if (!Directory.Exists(textLink.Text))
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Folder does not exist. Continue anyway?"))
                {
                    return ;
                }
                 
            }
             
        }
        else
        {
            /*try {
            						Directory.CreateDirectory(textLink.Text);
            					}
            					catch(Exception ex) {
            						MessageBox.Show(this,ex.Message);
            						return;
            					}*/
            //file mode
            if (!File.Exists(textLink.Text))
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"File does not exist. Continue anyway?"))
                {
                    return ;
                }
                 
            }
             
        } 
        SelectedLink = textLink.Text;
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
        this.label1 = new System.Windows.Forms.Label();
        this.textLink = new System.Windows.Forms.TextBox();
        this.butBrowse = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(76, 18);
        this.label1.TabIndex = 15;
        this.label1.Text = "Path";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLink
        //
        this.textLink.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textLink.Location = new System.Drawing.Point(88, 23);
        this.textLink.Name = "textLink";
        this.textLink.Size = new System.Drawing.Size(382, 20);
        this.textLink.TabIndex = 14;
        //
        // butBrowse
        //
        this.butBrowse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowse.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butBrowse.setAutosize(true);
        this.butBrowse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowse.setCornerRadius(4F);
        this.butBrowse.Location = new System.Drawing.Point(476, 23);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(23, 20);
        this.butBrowse.TabIndex = 18;
        this.butBrowse.Text = "...";
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(478, 87);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 17;
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
        this.butCancel.Location = new System.Drawing.Point(559, 87);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 16;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormWikiFileFolder
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(646, 123);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textLink);
        this.Name = "FormWikiFileFolder";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insert File Link";
        this.Load += new System.EventHandler(this.FormWikiFileFolder_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLink = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butBrowse;
}


