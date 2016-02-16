//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import OpenDental.FormAtoZFoldersCreate;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Cache;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAtoZFoldersCreate  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private TextBox textLocation = new TextBox();
    private TextBox textName = new TextBox();
    private Label label3 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormAtoZFoldersCreate() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAtoZFoldersCreate.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textLocation = new System.Windows.Forms.TextBox();
        this.textName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(592, 222);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(592, 181);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(578, 76);
        this.label1.TabIndex = 2;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(26, 115);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(184, 18);
        this.label2.TabIndex = 3;
        this.label2.Text = "Location of new folder";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textLocation
        //
        this.textLocation.Location = new System.Drawing.Point(214, 112);
        this.textLocation.Name = "textLocation";
        this.textLocation.Size = new System.Drawing.Size(323, 20);
        this.textLocation.TabIndex = 4;
        this.textLocation.Text = "C:\\";
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(214, 148);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(206, 20);
        this.textName.TabIndex = 6;
        this.textName.Text = "OpenDentImages";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(26, 151);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(184, 18);
        this.label3.TabIndex = 5;
        this.label3.Text = "Folder name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormAtoZFoldersCreate
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(719, 273);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textLocation);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAtoZFoldersCreate";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Create AtoZ Folder";
        this.Load += new System.EventHandler(this.FormAtoZFoldersCreate_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAtoZFoldersCreate_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Directory.Exists(textLocation.Text))
        {
            MsgBox.show(this,"Location does not exist.");
            return ;
        }
         
        if (Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textLocation.Text, textName.Text)))
        {
            MsgBox.show(this,"Folder already exists.");
            return ;
        }
         
        try
        {
            FileSystemAccessRule fsar = new FileSystemAccessRule("everyone", FileSystemRights.FullControl, AccessControlType.Allow);
            DirectorySecurity ds = new DirectorySecurity();
            ds.AddAccessRule(fsar);
            String requestDir = textLocation.Text;
            String rootFolderName = textName.Text;
            String rootDir = CodeBase.ODFileUtils.combinePaths(requestDir,rootFolderName);
            //Enable file sharing for the A to Z folder.
            if (Environment.OSVersion.Platform == PlatformID.Unix)
            {
            }
            else
            {
                //Process.Start("net","usershare add OpenDentImages \""+rootDir+"\"");//for future use.
                //Windows
                Process.Start("NET", "SHARE OpenDentImages=\"" + rootDir + "\"");
            } 
            //All folder names to be created should be put in this list, so that each folder is created exactly
            //the same way.
            String[] aToZFolderNames = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "EmailAttachments", "Forms", "Reports", "Sounds" };
            for (int i = 0;i < aToZFolderNames.Length;i++)
            {
                //Create A to Z folders in root folder.
                String pathToCreate = CodeBase.ODFileUtils.CombinePaths(rootDir, aToZFolderNames[i]);
                if (!Directory.Exists(pathToCreate))
                {
                    // Mono does support Directory.CreateDirectory(string, DirectorySecurity)
                    Directory.CreateDirectory(pathToCreate, ds);
                }
                 
            }
            //Save new image path into the DocPath and
            //set "use A to Z folders" check-box to checked.
            Prefs.updateString(PrefName.DocPath,rootDir);
            Prefs.updateString(PrefName.AtoZfolderUsed,"1");
            Cache.refresh(InvalidType.Prefs);
        }
        catch (Exception ex)
        {
            //Prefs_client.RefreshClient();
            Logger.openlog.logMB("Failed to create A to Z folders: " + ex.ToString(),Severity.ERROR);
        }

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


