//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormBackup;
import OpenDental.FormProgress;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PassProgressDelegate;
import OpenDental.PrefL;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.SecurityLogs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormBackup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRestore;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBox2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBox4 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butBackup;
    private OpenDental.UI.Button butBrowseTo;
    private OpenDental.UI.Button butBrowseFrom;
    private OpenDental.UI.Button butBrowseRestoreFrom;
    private System.Windows.Forms.TextBox textBox3 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butBrowseRestoreTo;
    private System.Windows.Forms.TextBox textBackupToPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBackupFromPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBackupRestoreFromPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBackupRestoreToPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBox5 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBackupRestoreAtoZToPath = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butBrowseRestoreAtoZTo;
    private OpenDental.UI.Button butSave;
    //Required designer variable.
    private System.ComponentModel.Container components = null;
    private FormProgress FormP;
    /**
    * Only used by one worker thread at a time. The value of the progressbar (in MB). Still passed in by delegate.
    */
    private double curVal = new double();
    private GroupBox groupBox2 = new GroupBox();
    private CheckBox checkExcludeImages = new CheckBox();
    //private bool usesInternalImages;
    /**
    * 
    */
    public FormBackup() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormBackup.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textBackupToPath = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textBox5 = new System.Windows.Forms.TextBox();
        this.butBrowseRestoreAtoZTo = new OpenDental.UI.Button();
        this.textBackupRestoreAtoZToPath = new System.Windows.Forms.TextBox();
        this.textBox3 = new System.Windows.Forms.TextBox();
        this.butBrowseRestoreTo = new OpenDental.UI.Button();
        this.textBackupRestoreToPath = new System.Windows.Forms.TextBox();
        this.textBox4 = new System.Windows.Forms.TextBox();
        this.butBrowseRestoreFrom = new OpenDental.UI.Button();
        this.textBackupRestoreFromPath = new System.Windows.Forms.TextBox();
        this.butRestore = new OpenDental.UI.Button();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.textBox2 = new System.Windows.Forms.TextBox();
        this.textBackupFromPath = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.checkExcludeImages = new System.Windows.Forms.CheckBox();
        this.butSave = new OpenDental.UI.Button();
        this.butBrowseFrom = new OpenDental.UI.Button();
        this.butBrowseTo = new OpenDental.UI.Button();
        this.butBackup = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 5);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(713, 28);
        this.label1.TabIndex = 2;
        this.label1.Text = "BACKUPS ARE USELESS UNLESS YOU REGULARLY VERIFY THEIR QUALITY BY TAKING A BACKUP " + "HOME AND RESTORING IT TO YOUR HOME COMPUTER.  We suggest an inexpensive USB flas" + "h drive for this purpose.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textBackupToPath
        //
        this.textBackupToPath.Location = new System.Drawing.Point(18, 212);
        this.textBackupToPath.Name = "textBackupToPath";
        this.textBackupToPath.Size = new System.Drawing.Size(481, 20);
        this.textBackupToPath.TabIndex = 4;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textBox5);
        this.groupBox1.Controls.Add(this.butBrowseRestoreAtoZTo);
        this.groupBox1.Controls.Add(this.textBackupRestoreAtoZToPath);
        this.groupBox1.Controls.Add(this.textBox3);
        this.groupBox1.Controls.Add(this.butBrowseRestoreTo);
        this.groupBox1.Controls.Add(this.textBackupRestoreToPath);
        this.groupBox1.Controls.Add(this.textBox4);
        this.groupBox1.Controls.Add(this.butBrowseRestoreFrom);
        this.groupBox1.Controls.Add(this.textBackupRestoreFromPath);
        this.groupBox1.Controls.Add(this.butRestore);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(12, 255);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(747, 213);
        this.groupBox1.TabIndex = 8;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Restore";
        //
        // textBox5
        //
        this.textBox5.BackColor = System.Drawing.SystemColors.Control;
        this.textBox5.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox5.Location = new System.Drawing.Point(7, 142);
        this.textBox5.Multiline = true;
        this.textBox5.Name = "textBox5";
        this.textBox5.Size = new System.Drawing.Size(396, 27);
        this.textBox5.TabIndex = 21;
        this.textBox5.Text = "Restore A-Z images to this folder: (example:)\r\nC:\\OpenDentImages\\";
        //
        // butBrowseRestoreAtoZTo
        //
        this.butBrowseRestoreAtoZTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseRestoreAtoZTo.setAutosize(true);
        this.butBrowseRestoreAtoZTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseRestoreAtoZTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseRestoreAtoZTo.setCornerRadius(4F);
        this.butBrowseRestoreAtoZTo.Location = new System.Drawing.Point(500, 170);
        this.butBrowseRestoreAtoZTo.Name = "butBrowseRestoreAtoZTo";
        this.butBrowseRestoreAtoZTo.Size = new System.Drawing.Size(86, 26);
        this.butBrowseRestoreAtoZTo.TabIndex = 20;
        this.butBrowseRestoreAtoZTo.Text = "Browse";
        this.butBrowseRestoreAtoZTo.Click += new System.EventHandler(this.butBrowseRestoreAtoZTo_Click);
        //
        // textBackupRestoreAtoZToPath
        //
        this.textBackupRestoreAtoZToPath.Location = new System.Drawing.Point(6, 173);
        this.textBackupRestoreAtoZToPath.Name = "textBackupRestoreAtoZToPath";
        this.textBackupRestoreAtoZToPath.Size = new System.Drawing.Size(481, 20);
        this.textBackupRestoreAtoZToPath.TabIndex = 19;
        //
        // textBox3
        //
        this.textBox3.BackColor = System.Drawing.SystemColors.Control;
        this.textBox3.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox3.Location = new System.Drawing.Point(7, 81);
        this.textBox3.Multiline = true;
        this.textBox3.Name = "textBox3";
        this.textBox3.Size = new System.Drawing.Size(247, 27);
        this.textBox3.TabIndex = 18;
        this.textBox3.Text = "Restore database TO this folder: (example:)\r\nC:\\mysql\\data\\";
        //
        // butBrowseRestoreTo
        //
        this.butBrowseRestoreTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseRestoreTo.setAutosize(true);
        this.butBrowseRestoreTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseRestoreTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseRestoreTo.setCornerRadius(4F);
        this.butBrowseRestoreTo.Location = new System.Drawing.Point(500, 109);
        this.butBrowseRestoreTo.Name = "butBrowseRestoreTo";
        this.butBrowseRestoreTo.Size = new System.Drawing.Size(86, 26);
        this.butBrowseRestoreTo.TabIndex = 17;
        this.butBrowseRestoreTo.Text = "Browse";
        this.butBrowseRestoreTo.Click += new System.EventHandler(this.butBrowseRestoreTo_Click);
        //
        // textBackupRestoreToPath
        //
        this.textBackupRestoreToPath.Location = new System.Drawing.Point(6, 112);
        this.textBackupRestoreToPath.Name = "textBackupRestoreToPath";
        this.textBackupRestoreToPath.Size = new System.Drawing.Size(481, 20);
        this.textBackupRestoreToPath.TabIndex = 16;
        //
        // textBox4
        //
        this.textBox4.BackColor = System.Drawing.SystemColors.Control;
        this.textBox4.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox4.Location = new System.Drawing.Point(7, 20);
        this.textBox4.Multiline = true;
        this.textBox4.Name = "textBox4";
        this.textBox4.Size = new System.Drawing.Size(280, 29);
        this.textBox4.TabIndex = 15;
        this.textBox4.Text = "Restore FROM this folder: (example:)\r\nD:\\";
        //
        // butBrowseRestoreFrom
        //
        this.butBrowseRestoreFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseRestoreFrom.setAutosize(true);
        this.butBrowseRestoreFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseRestoreFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseRestoreFrom.setCornerRadius(4F);
        this.butBrowseRestoreFrom.Location = new System.Drawing.Point(500, 47);
        this.butBrowseRestoreFrom.Name = "butBrowseRestoreFrom";
        this.butBrowseRestoreFrom.Size = new System.Drawing.Size(86, 26);
        this.butBrowseRestoreFrom.TabIndex = 14;
        this.butBrowseRestoreFrom.Text = "Browse";
        this.butBrowseRestoreFrom.Click += new System.EventHandler(this.butBrowseRestoreFrom_Click);
        //
        // textBackupRestoreFromPath
        //
        this.textBackupRestoreFromPath.Location = new System.Drawing.Point(6, 50);
        this.textBackupRestoreFromPath.Name = "textBackupRestoreFromPath";
        this.textBackupRestoreFromPath.Size = new System.Drawing.Size(481, 20);
        this.textBackupRestoreFromPath.TabIndex = 13;
        //
        // butRestore
        //
        this.butRestore.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRestore.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRestore.setAutosize(true);
        this.butRestore.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRestore.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRestore.setCornerRadius(4F);
        this.butRestore.Location = new System.Drawing.Point(648, 170);
        this.butRestore.Name = "butRestore";
        this.butRestore.Size = new System.Drawing.Size(86, 26);
        this.butRestore.TabIndex = 6;
        this.butRestore.Text = "Restore";
        this.butRestore.Click += new System.EventHandler(this.butRestore_Click);
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(19, 155);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(279, 55);
        this.textBox1.TabIndex = 9;
        this.textBox1.Text = "Backup TO this folder: (examples:)\r\nD:\\\r\nD:\\Backups\\\r\n\\\\frontdesk\\backups\\";
        //
        // textBox2
        //
        this.textBox2.BackColor = System.Drawing.SystemColors.Control;
        this.textBox2.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox2.Location = new System.Drawing.Point(19, 81);
        this.textBox2.Multiline = true;
        this.textBox2.Name = "textBox2";
        this.textBox2.Size = new System.Drawing.Size(240, 43);
        this.textBox2.TabIndex = 12;
        this.textBox2.Text = "Backup database FROM this folder: (examples:)\r\nC:\\mysql\\data\\\r\n\\\\server\\mysql\\dat" + "a\\";
        //
        // textBackupFromPath
        //
        this.textBackupFromPath.Location = new System.Drawing.Point(18, 126);
        this.textBackupFromPath.Name = "textBackupFromPath";
        this.textBackupFromPath.Size = new System.Drawing.Size(481, 20);
        this.textBackupFromPath.TabIndex = 10;
        //
        // groupBox2
        //
        this.groupBox2.Location = new System.Drawing.Point(12, 65);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(747, 184);
        this.groupBox2.TabIndex = 14;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Backup";
        //
        // checkExcludeImages
        //
        this.checkExcludeImages.AutoSize = true;
        this.checkExcludeImages.Location = new System.Drawing.Point(12, 42);
        this.checkExcludeImages.Name = "checkExcludeImages";
        this.checkExcludeImages.Size = new System.Drawing.Size(221, 17);
        this.checkExcludeImages.TabIndex = 15;
        this.checkExcludeImages.Text = "Exclude image folder in backup or restore";
        this.checkExcludeImages.UseVisualStyleBackColor = true;
        this.checkExcludeImages.Click += new System.EventHandler(this.checkExcludeImages_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(19, 501);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(86, 26);
        this.butSave.TabIndex = 13;
        this.butSave.Text = "Save Defaults";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butBrowseFrom
        //
        this.butBrowseFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseFrom.setAutosize(true);
        this.butBrowseFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseFrom.setCornerRadius(4F);
        this.butBrowseFrom.Location = new System.Drawing.Point(512, 123);
        this.butBrowseFrom.Name = "butBrowseFrom";
        this.butBrowseFrom.Size = new System.Drawing.Size(86, 26);
        this.butBrowseFrom.TabIndex = 11;
        this.butBrowseFrom.Text = "Browse";
        this.butBrowseFrom.Click += new System.EventHandler(this.butBrowseFrom_Click);
        //
        // butBrowseTo
        //
        this.butBrowseTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseTo.setAutosize(true);
        this.butBrowseTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseTo.setCornerRadius(4F);
        this.butBrowseTo.Location = new System.Drawing.Point(512, 209);
        this.butBrowseTo.Name = "butBrowseTo";
        this.butBrowseTo.Size = new System.Drawing.Size(86, 26);
        this.butBrowseTo.TabIndex = 5;
        this.butBrowseTo.Text = "Browse";
        this.butBrowseTo.Click += new System.EventHandler(this.butBrowseTo_Click);
        //
        // butBackup
        //
        this.butBackup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBackup.setAutosize(true);
        this.butBackup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBackup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBackup.setCornerRadius(4F);
        this.butBackup.Location = new System.Drawing.Point(665, 209);
        this.butBackup.Name = "butBackup";
        this.butBackup.Size = new System.Drawing.Size(86, 26);
        this.butBackup.TabIndex = 1;
        this.butBackup.Text = "Backup";
        this.butBackup.Click += new System.EventHandler(this.butBackup_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(660, 501);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(86, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormBackup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(777, 550);
        this.Controls.Add(this.checkExcludeImages);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.textBox2);
        this.Controls.Add(this.butBrowseFrom);
        this.Controls.Add(this.textBackupFromPath);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.butBrowseTo);
        this.Controls.Add(this.textBackupToPath);
        this.Controls.Add(this.butBackup);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormBackup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Backup";
        this.Load += new System.EventHandler(this.FormBackup_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formBackup_Load(Object sender, System.EventArgs e) throws Exception {
        //already include the \
        checkExcludeImages.Checked = PrefC.getBool(PrefName.BackupExcludeImageFolder);
        textBackupFromPath.Text = PrefC.getString(PrefName.BackupFromPath);
        textBackupToPath.Text = PrefC.getString(PrefName.BackupToPath);
        textBackupRestoreFromPath.Text = PrefC.getString(PrefName.BackupRestoreFromPath);
        textBackupRestoreToPath.Text = PrefC.getString(PrefName.BackupRestoreToPath);
        textBackupRestoreAtoZToPath.Text = PrefC.getString(PrefName.BackupRestoreAtoZToPath);
        //usesInternalImages=(PrefC.GetString(PrefName.ImageStore)=="OpenDental.Imaging.SqlStore");
        textBackupRestoreAtoZToPath.Enabled = shouldUseAtoZFolder();
        butBrowseRestoreAtoZTo.Enabled = shouldUseAtoZFolder();
    }

    private boolean shouldUseAtoZFolder() throws Exception {
        return (PrefC.getAtoZfolderUsed() && !checkExcludeImages.Checked);
    }

    //&& !usesInternalImages
    private void butBrowseFrom_Click(Object sender, System.EventArgs e) throws Exception {
        FolderBrowserDialog browserDlg = new FolderBrowserDialog();
        browserDlg.SelectedPath = textBackupFromPath.Text;
        if (browserDlg.ShowDialog() == DialogResult.Cancel)
        {
            return ;
        }
         
        textBackupFromPath.Text = CodeBase.ODFileUtils.CombinePaths(browserDlg.SelectedPath, "");
    }

    //Add trail slash.
    private void butBrowseTo_Click(Object sender, System.EventArgs e) throws Exception {
        FolderBrowserDialog browserDlg = new FolderBrowserDialog();
        browserDlg.SelectedPath = textBackupToPath.Text;
        if (browserDlg.ShowDialog() == DialogResult.Cancel)
        {
            return ;
        }
         
        textBackupToPath.Text = CodeBase.ODFileUtils.CombinePaths(browserDlg.SelectedPath, "");
    }

    //Add trail slash.
    private void butBrowseRestoreFrom_Click(Object sender, System.EventArgs e) throws Exception {
        FolderBrowserDialog browserDlg = new FolderBrowserDialog();
        browserDlg.SelectedPath = textBackupRestoreFromPath.Text;
        if (browserDlg.ShowDialog() == DialogResult.Cancel)
        {
            return ;
        }
         
        textBackupRestoreFromPath.Text = CodeBase.ODFileUtils.CombinePaths(browserDlg.SelectedPath, "");
    }

    //Add trail slash.
    private void butBrowseRestoreTo_Click(Object sender, System.EventArgs e) throws Exception {
        FolderBrowserDialog browserDlg = new FolderBrowserDialog();
        browserDlg.SelectedPath = textBackupRestoreToPath.Text;
        if (browserDlg.ShowDialog() == DialogResult.Cancel)
        {
            return ;
        }
         
        textBackupRestoreToPath.Text = CodeBase.ODFileUtils.CombinePaths(browserDlg.SelectedPath, "");
    }

    //Add trail slash.
    private void butBrowseRestoreAtoZTo_Click(Object sender, System.EventArgs e) throws Exception {
        FolderBrowserDialog browserDlg = new FolderBrowserDialog();
        browserDlg.SelectedPath = textBackupRestoreAtoZToPath.Text;
        if (browserDlg.ShowDialog() == DialogResult.Cancel)
        {
            return ;
        }
         
        textBackupRestoreAtoZToPath.Text = CodeBase.ODFileUtils.CombinePaths(browserDlg.SelectedPath, "");
    }

    //Add trail slash.
    private void butBackup_Click(Object sender, System.EventArgs e) throws Exception {
        //test for trailing slashes
        if (!StringSupport.equals(textBackupFromPath.Text, "") && !textBackupFromPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MsgBox.show(this,"Paths must end with " + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupToPath.Text, "") && !textBackupToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MsgBox.show(this,"Paths must end with " + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreFromPath.Text, "") && !textBackupRestoreFromPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MsgBox.show(this,"Paths must end with " + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreToPath.Text, "") && !textBackupRestoreToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MsgBox.show(this,"Paths must end with " + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreAtoZToPath.Text, "") && !textBackupRestoreAtoZToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MsgBox.show(this,"Paths must end with " + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        //Ensure that the backup from and backup to paths are different. This is to prevent the live database
        //from becoming corrupt.
        if (this.textBackupFromPath.Text.Trim().ToLower() == this.textBackupToPath.Text.Trim().ToLower())
        {
            MsgBox.show(this,"The backup from path and backup to path must be different.");
            return ;
        }
         
        //test saving defaults
        if (!StringSupport.equals(textBackupFromPath.Text, PrefC.getString(PrefName.BackupFromPath)) || !StringSupport.equals(textBackupToPath.Text, PrefC.getString(PrefName.BackupToPath)) || !StringSupport.equals(textBackupRestoreFromPath.Text, PrefC.getString(PrefName.BackupRestoreFromPath)) || !StringSupport.equals(textBackupRestoreToPath.Text, PrefC.getString(PrefName.BackupRestoreToPath)) || !StringSupport.equals(textBackupRestoreAtoZToPath.Text, PrefC.getString(PrefName.BackupRestoreAtoZToPath)))
        {
            if (MsgBox.show(this,true,"Set as default?"))
            {
                Prefs.UpdateString(PrefName.BackupFromPath, textBackupFromPath.Text);
                Prefs.UpdateString(PrefName.BackupToPath, textBackupToPath.Text);
                Prefs.UpdateString(PrefName.BackupRestoreFromPath, textBackupRestoreFromPath.Text);
                Prefs.UpdateString(PrefName.BackupRestoreToPath, textBackupRestoreToPath.Text);
                Prefs.UpdateString(PrefName.BackupRestoreAtoZToPath, textBackupRestoreAtoZToPath.Text);
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
        }
         
        String dbName = MiscData.getCurrentDatabase();
        if (!Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupFromPath.Text, dbName)))
        {
            // C:\mysql\data\opendental
            MsgBox.show(this,"Backup FROM path is invalid.");
            return ;
        }
         
        if (!Directory.Exists(textBackupToPath.Text))
        {
            // D:\
            MsgBox.show(this,"Backup TO path is invalid.");
            return ;
        }
         
        FormP = new FormProgress();
        FormP.MaxVal = 100;
        //We will be setting maxVal from worker thread.  (double)fileSize/1024;
        FormP.NumberMultiplication = 100;
        FormP.DisplayText = "";
        //We will set the text from the worker thread.
        FormP.NumberFormat = "N1";
        //start the thread that will perform the database copy
        Thread workerThread = new Thread(new ThreadStart(InstanceMethodBackup));
        workerThread.Start();
        //display the progress dialog to the user:
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            workerThread.Abort();
            return ;
        }
         
        SecurityLogs.MakeLogEntry(Permissions.Copy, 0, "Database backup created at " + PrefC.getString(PrefName.BackupToPath));
        MessageBox.Show(Lan.g(this,"Backup complete."));
        Close();
    }

    /**
    * This is the function that the worker thread uses to perform the backup.
    */
    private void instanceMethodBackup() throws Exception {
        curVal = 0;
        Invoke(new PassProgressDelegate() 
          { 
            public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
            }

            public List<PassProgressDelegate> getInvocationList() throws Exception {
                List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                ret.add(this);
                return ret;
            }
        
          }, new Object[]{ curVal, Lan.g(this,"Preparing to copy database"), 100, "" });
        //this happens very fast and probably won't be noticed.
        //max of 100 keeps dlg from closing
        String dbName = MiscData.getCurrentDatabase();
        double dbSize = GetFileSizes(textBackupFromPath.Text + dbName) / 1024;
        try
        {
            String dbtopath = CodeBase.ODFileUtils.CombinePaths(textBackupToPath.Text, dbName);
            if (Directory.Exists(dbtopath))
            {
                // D:\opendental
                int loopCount = 1;
                while (Directory.Exists(dbtopath + "backup_" + loopCount))
                {
                    loopCount++;
                }
                Directory.Move(dbtopath, dbtopath + "backup_" + loopCount);
            }
             
            String fromPath = CodeBase.ODFileUtils.CombinePaths(textBackupFromPath.Text, dbName);
            String toPath = textBackupToPath.Text;
            DirectoryInfo dirInfo = new DirectoryInfo(fromPath);
            //does not check to see if dir exists
            Directory.CreateDirectory(CodeBase.ODFileUtils.CombinePaths(toPath, dirInfo.Name));
            FileInfo[] files = dirInfo.GetFiles();
            curVal = 0;
            for (int i = 0;i < files.Length;i++)
            {
                //curVal gets increased
                String fromFile = files[i].FullName;
                String toFile = CodeBase.ODFileUtils.CombinePaths(new String[]{ toPath, dirInfo.Name, files[i].Name });
                if (File.Exists(toFile))
                {
                    if (files[i].LastWriteTime != File.GetLastWriteTime(toFile))
                    {
                        //if modification dates don't match
                        FileAttributes fa = File.GetAttributes(toFile);
                        boolean isReadOnly = ((fa & FileAttributes.ReadOnly) == FileAttributes.ReadOnly);
                        if (isReadOnly)
                        {
                            //If the destination file exists and is marked as read only, then we must mark it as a
                            //normal read/write file before it may be overwritten.
                            File.SetAttributes(toFile, FileAttributes.Normal);
                        }
                         
                        //Remove read only from the destination file.
                        File.Copy(fromFile, toFile, true);
                    }
                     
                }
                else
                {
                    //file doesn't exist, so just copy
                    File.Copy(fromFile, toFile);
                } 
                curVal += (double)files[i].Length / (double)1024 / (double)1024;
                if (curVal < dbSize)
                {
                    Invoke(new PassProgressDelegate() 
                      { 
                        //this avoids setting progress bar to max, which would close the dialog.
                        public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                            passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                        }

                        public List<PassProgressDelegate> getInvocationList() throws Exception {
                            List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                            ret.add(this);
                            return ret;
                        }
                    
                      }, new Object[]{ curVal, Lan.g(this,"Database: ?currentVal MB of ?maxVal MB copied"), dbSize, "" });
                }
                 
            }
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        //for instance, if abort.
        //A to Z folder------------------------------------------------------------------------------------
        if (shouldUseAtoZFolder())
        {
            String atozFull = CodeBase.ODFileUtils.removeTrailingSeparators(ImageStore.getPreferredAtoZpath());
            String atozDir = atozFull.Substring(atozFull.LastIndexOf(Path.DirectorySeparatorChar) + 1);
            Invoke(new PassProgressDelegate() 
              { 
                //OpenDentalData
                public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                    passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                }

                public List<PassProgressDelegate> getInvocationList() throws Exception {
                    List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                    ret.add(this);
                    return ret;
                }
            
              }, new Object[]{ 0, Lan.g(this,"Calculating size of files in A to Z folder."), 100, "" });
            //max of 100 keeps dlg from closing
            int atozSize = GetFileSizes(CodeBase.ODFileUtils.combinePaths(atozFull,""), CodeBase.ODFileUtils.CombinePaths(new String[]{ textBackupToPath.Text, atozDir, "" })) / 1024;
            if (!Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupToPath.Text, atozDir)))
            {
                // D:\OpenDentalData
                Directory.CreateDirectory(CodeBase.ODFileUtils.CombinePaths(textBackupToPath.Text, atozDir));
            }
             
            // D:\OpenDentalData
            curVal = 0;
            // C:\OpenDentalData\
            // D:\OpenDentalData\
            CopyDirectoryIncremental(CodeBase.ODFileUtils.combinePaths(atozFull,""), CodeBase.ODFileUtils.CombinePaths(new String[]{ textBackupToPath.Text, atozDir, "" }), atozSize);
        }
         
        Invoke(new PassProgressDelegate() 
          { 
            //force dialog to close even if no files copied or calculation was slightly off.
            public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
            }

            public List<PassProgressDelegate> getInvocationList() throws Exception {
                List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                ret.add(this);
                return ret;
            }
        
          }, new Object[]{ 0, "", 0, "" });
    }

    /**
    * This is the function that the worker thread uses to restore the A-Z folder.
    */
    private void instanceMethodRestore() throws Exception {
        curVal = 0;
        String atozFull = textBackupRestoreAtoZToPath.Text;
        // C:\OpenDentalData\
        //remove the trailing \
        atozFull = atozFull.Substring(0, atozFull.Length - 1);
        // C:\OpenDentalData
        String atozDir = atozFull.Substring(atozFull.LastIndexOf(Path.DirectorySeparatorChar) + 1);
        Invoke(new PassProgressDelegate() 
          { 
            // OpenDentalData
            public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
            }

            public List<PassProgressDelegate> getInvocationList() throws Exception {
                List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                ret.add(this);
                return ret;
            }
        
          }, new Object[]{ 0, Lan.g(this,"Database restored.\r\nCalculating size of files in A to Z folder."), 100, "" });
        //max of 100 keeps dlg from closing
        int atozSize = GetFileSizes(CodeBase.ODFileUtils.CombinePaths(new String[]{ textBackupRestoreFromPath.Text, atozDir, "" }), CodeBase.ODFileUtils.combinePaths(atozFull,"")) / 1024;
        // C:\OpenDentalData\
        if (!Directory.Exists(atozFull))
        {
            // C:\OpenDentalData\
            Directory.CreateDirectory(atozFull);
        }
         
        // C:\OpenDentalData\
        curVal = 0;
        // C:\OpenDentalData\
        CopyDirectoryIncremental(CodeBase.ODFileUtils.CombinePaths(new String[]{ textBackupRestoreFromPath.Text, atozDir, "" }), CodeBase.ODFileUtils.combinePaths(atozFull,""), atozSize);
        Invoke(new PassProgressDelegate() 
          { 
            //force dlg to close even if no files copied or calculation was slightly off.
            public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
            }

            public List<PassProgressDelegate> getInvocationList() throws Exception {
                List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                ret.add(this);
                return ret;
            }
        
          }, new Object[]{ 0, "", 0, "" });
    }

    /**
    * This function gets invoked from the worker threads.
    */
    private void passProgressToDialog(double currentVal, String displayText, double maxVal, String errorMessage) throws Exception {
        FormP.CurrentVal = currentVal;
        FormP.DisplayText = displayText;
        FormP.MaxVal = maxVal;
        FormP.ErrorMessage = errorMessage;
    }

    /**
    * Counts the total KB of all files that will need to be copied from one directory to another.  Recursive.  Only includes missing files, not changed files.  Used to display the progress bar.  Supplied paths must end in \. toPath might not exist.
    */
    private int getFileSizes(String fromPath, String toPath) throws Exception {
        int retVal = 0;
        DirectoryInfo dirInfo = new DirectoryInfo(fromPath);
        DirectoryInfo[] dirs = dirInfo.GetDirectories();
        for (int i = 0;i < dirs.Length;i++)
        {
            retVal += GetFileSizes(CodeBase.ODFileUtils.CombinePaths(dirs[i].FullName, ""), CodeBase.ODFileUtils.CombinePaths(new String[]{ toPath, dirs[i].Name, "" }));
        }
        FileInfo[] files = dirInfo.GetFiles();
        for (int i = 0;i < files.Length;i++)
        {
            //of fromPath
            if (!File.Exists(CodeBase.ODFileUtils.CombinePaths(toPath, files[i].Name)))
            {
                retVal += (int)(files[i].Length / 1024);
            }
             
        }
        return retVal;
    }

    /**
    * Counts the total KB of all files in the given directory.  Not recursive since it's just used for db files.  Used to display the progress bar.
    */
    private int getFileSizes(String fromPath) throws Exception {
        int retVal = 0;
        DirectoryInfo dirInfo = new DirectoryInfo(fromPath);
        FileInfo[] files = dirInfo.GetFiles();
        for (int i = 0;i < files.Length;i++)
        {
            retVal += (int)(files[i].Length / 1024);
        }
        return retVal;
    }

    /**
    * A recursive fuction that copies any new or changed files or folders from one directory to another.  An exception will be thrown if either directory does not already exist.  fromPath is the fully qualified path of the directory to copy.  toPath is the fully qualified path of the destination directory.  Both paths must include a trailing \.  The max size should be calculated ahead of time.  It's passed in for use in progress bar.
    */
    private void copyDirectoryIncremental(String fromPath, String toPath, double maxSize) throws Exception {
        if (!Directory.Exists(fromPath))
        {
            throw new Exception(fromPath + " does not exist.");
        }
         
        if (!Directory.Exists(toPath))
        {
            throw new Exception(toPath + " does not exist.");
        }
         
        DirectoryInfo dirInfo = new DirectoryInfo(fromPath);
        DirectoryInfo[] dirs = dirInfo.GetDirectories();
        for (int i = 0;i < dirs.Length;i++)
        {
            String destPath = CodeBase.ODFileUtils.CombinePaths(toPath, dirs[i].Name);
            if (!Directory.Exists(destPath))
            {
                Directory.CreateDirectory(destPath);
            }
             
            CopyDirectoryIncremental(CodeBase.ODFileUtils.CombinePaths(dirs[i].FullName, ""), CodeBase.ODFileUtils.combinePaths(destPath,""), maxSize);
        }
        FileInfo[] files = dirInfo.GetFiles();
        for (int i = 0;i < files.Length;i++)
        {
            //of fromPath
            String fromFile = files[i].FullName;
            String toFile = CodeBase.ODFileUtils.CombinePaths(toPath, files[i].Name);
            if (File.Exists(toFile))
            {
                if (files[i].LastWriteTime != File.GetLastWriteTime(toFile))
                {
                    //if modification dates don't match
                    FileAttributes fa = File.GetAttributes(toFile);
                    boolean isReadOnly = ((fa & FileAttributes.ReadOnly) == FileAttributes.ReadOnly);
                    if (isReadOnly)
                    {
                        //If the destination file exists and is marked as read only, then we must mark it as a
                        //normal read/write file before it may be overwritten.
                        File.SetAttributes(toFile, FileAttributes.Normal);
                    }
                     
                    //Remove read only from the destination file.
                    File.Copy(fromFile, toFile, true);
                }
                 
            }
            else
            {
                //file doesn't exist, so just copy
                File.Copy(fromFile, toFile);
            } 
            curVal += (double)files[i].Length / 1048576.0;
            //Number of megabytes.
            if (curVal < maxSize)
            {
                Invoke(new PassProgressDelegate() 
                  { 
                    //this avoids setting progress bar to max, which would close the dialog.
                    public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                        passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                    }

                    public List<PassProgressDelegate> getInvocationList() throws Exception {
                        List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                        ret.add(this);
                        return ret;
                    }
                
                  }, new Object[]{ curVal, Lan.g(this,"A to Z folder: ?currentVal MB of ?maxVal MB copied"), maxSize, "" });
            }
             
        }
    }

    private void butRestore_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textBackupRestoreFromPath.Text, "") && !textBackupRestoreFromPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreToPath.Text, "") && !textBackupRestoreToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (shouldUseAtoZFolder())
        {
            if (!StringSupport.equals(textBackupRestoreAtoZToPath.Text, "") && !textBackupRestoreAtoZToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
            {
                MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
                return ;
            }
             
        }
         
        if (Environment.OSVersion.Platform != PlatformID.Unix)
        {
            //dmg This check will not work on Linux, because mapped drives exist as regular (mounted) paths. Perhaps there
            //is another way to check for this on Linux.
            if (!StringSupport.equals(textBackupRestoreToPath.Text, "") && textBackupRestoreToPath.Text.StartsWith("" + Path.DirectorySeparatorChar))
            {
                MsgBox.show(this,"The restore database TO folder must be on this computer.");
                return ;
            }
             
        }
         
        //pointless to save defaults
        String dbName = MiscData.getCurrentDatabase();
        if (!Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreFromPath.Text, dbName)))
        {
            // D:\opendental
            MessageBox.Show(Lan.g(this,"Restore FROM path is invalid.  Unable to find folder named ") + dbName);
            return ;
        }
         
        if (!Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreToPath.Text, dbName)))
        {
            // C:\mysql\data\opendental
            MessageBox.Show(Lan.g(this,"Restore TO path is invalid.  Unable to find folder named ") + dbName);
            return ;
        }
         
        if (shouldUseAtoZFolder())
        {
            if (!Directory.Exists(textBackupRestoreAtoZToPath.Text))
            {
                // C:\OpenDentalData\
                MsgBox.show(this,"Restore A-Z images TO path is invalid.");
                return ;
            }
             
            String atozFull = textBackupRestoreAtoZToPath.Text;
            // C:\OpenDentalData\
            //remove the trailing \
            atozFull = atozFull.Substring(0, atozFull.Length - 1);
            // C:\OpenDentalData
            String atozDir = atozFull.Substring(atozFull.LastIndexOf(Path.DirectorySeparatorChar) + 1);
            // OpenDentalData
            if (!Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreFromPath.Text, atozDir)))
            {
                // D:\OpenDentalData
                MsgBox.show(this,"Restore A-Z images FROM path is invalid.");
                return ;
            }
             
        }
         
        String fromPath = CodeBase.ODFileUtils.CombinePaths(new String[]{ textBackupRestoreFromPath.Text, dbName, "" });
        // D:\opendental\
        DirectoryInfo dirInfo = new DirectoryInfo(fromPath);
        //does not check to see if dir exists
        if (MessageBox.Show(Lan.g(this,"Restore from backup created on") + "\r\n" + dirInfo.LastWriteTime.ToString("dddd") + "  " + dirInfo.LastWriteTime.ToString(), "", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) == DialogResult.Cancel)
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        //stop the service--------------------------------------------------------------------------------------
        ServiceController sc = new ServiceController("MySQL");
        try
        {
            sc.Stop();
            sc.WaitForStatus(ServiceControllerStatus.Stopped, new TimeSpan(0, 0, 10));
        }
        catch (Exception __dummyCatchVar1)
        {
            MsgBox.show(this,"Unable to stop MySQL service.");
            return ;
        }

        //rename the current database---------------------------------------------------------------------------
        //Get a name for the new directory
        String newDb = dbName + "backup_" + DateTime.Today.ToString("MM_dd_yyyy");
        if (Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreToPath.Text, newDb)))
        {
            //if the new database name already exists
            //find a unique one
            int uniqueID = 1;
            String originalNewDb = newDb;
            do
            {
                newDb = originalNewDb + "_" + uniqueID.ToString();
                uniqueID++;
            }
            while (Directory.Exists(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreToPath.Text, newDb)));
        }
         
        //move the current db (rename)
        // C:\mysql\data\opendental
        Directory.Move(CodeBase.ODFileUtils.CombinePaths(textBackupRestoreToPath.Text, dbName), CodeBase.ODFileUtils.CombinePaths(textBackupRestoreToPath.Text, newDb));
        // C:\mysql\data\opendentalbackup_03_25_2006
        //Restore----------------------------------------------------------------------------------------------
        String toPath = textBackupRestoreToPath.Text;
        // C:\mysql\data\
        Directory.CreateDirectory(CodeBase.ODFileUtils.CombinePaths(toPath, dirInfo.Name));
        FileInfo[] files = dirInfo.GetFiles();
        curVal = 0;
        for (int i = 0;i < files.Length;i++)
        {
            //curVal gets increased
            File.Copy(files[i].FullName, CodeBase.ODFileUtils.CombinePaths(new String[]{ toPath, dirInfo.Name, files[i].Name }));
        }
        try
        {
            //start the service--------------------------------------------------------------------------------------
            sc.Start();
            sc.WaitForStatus(ServiceControllerStatus.Running, new TimeSpan(0, 0, 10));
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        //do nothing
        Cursor = Cursors.Default;
        //restore A-Z folder, and give user a chance to cancel it.
        if (shouldUseAtoZFolder())
        {
            FormP = new FormProgress();
            FormP.MaxVal = 100;
            //We will be setting maxVal from worker thread.  (double)fileSize/1024;
            FormP.NumberMultiplication = 100;
            FormP.DisplayText = "";
            //We will set the text from the worker thread.
            FormP.NumberFormat = "N1";
            //start the thread that will perform the database copy
            Thread workerThread = new Thread(new ThreadStart(InstanceMethodRestore));
            workerThread.Start();
            //display the progress dialog to the user:
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.Cancel)
            {
                workerThread.Abort();
                return ;
            }
             
        }
         
        MsgBox.show(this,"Done");
        PrefL.checkProgramVersion();
        //this might force program to close if version mismatch could not be resolved.
        Close();
    }

    /*
    		private void FillTableWithData(StringBuilder tableData,string tempFile,string tableName){
    			using(StreamWriter sw=new StreamWriter(tempFile,false)){//new file each time
    				sw.Write(tableData.ToString());
    			}
    			string command="LOAD DATA INFILE '"+tempFile.Replace("\\","/")+"' INTO TABLE "+tableName;
    			Db.NonQ(command);
    		}*/
    private void butSave_Click(Object sender, System.EventArgs e) throws Exception {
        //test for trailing slashes
        if (!StringSupport.equals(textBackupFromPath.Text, "") && !textBackupFromPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupToPath.Text, "") && !textBackupToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreFromPath.Text, "") && !textBackupRestoreFromPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreToPath.Text, "") && !textBackupRestoreToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupRestoreAtoZToPath.Text, "") && !textBackupRestoreAtoZToPath.Text.EndsWith("" + Path.DirectorySeparatorChar))
        {
            MessageBox.Show(Lan.g(this,"Paths must end with ") + Path.DirectorySeparatorChar + ".");
            return ;
        }
         
        if (!StringSupport.equals(textBackupFromPath.Text, PrefC.getString(PrefName.BackupFromPath)) || !StringSupport.equals(textBackupToPath.Text, PrefC.getString(PrefName.BackupToPath)) || !StringSupport.equals(textBackupRestoreFromPath.Text, PrefC.getString(PrefName.BackupRestoreFromPath)) || !StringSupport.equals(textBackupRestoreToPath.Text, PrefC.getString(PrefName.BackupRestoreToPath)) || !StringSupport.equals(textBackupRestoreAtoZToPath.Text, PrefC.getString(PrefName.BackupRestoreAtoZToPath)) || checkExcludeImages.Checked != PrefC.getBool(PrefName.BackupExcludeImageFolder))
        {
            Prefs.UpdateString(PrefName.BackupFromPath, textBackupFromPath.Text);
            Prefs.UpdateString(PrefName.BackupToPath, textBackupToPath.Text);
            Prefs.UpdateString(PrefName.BackupRestoreFromPath, textBackupRestoreFromPath.Text);
            Prefs.UpdateString(PrefName.BackupRestoreToPath, textBackupRestoreToPath.Text);
            Prefs.UpdateString(PrefName.BackupRestoreAtoZToPath, textBackupRestoreAtoZToPath.Text);
            Prefs.UpdateBool(PrefName.BackupExcludeImageFolder, checkExcludeImages.Checked);
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void checkExcludeImages_Click(Object sender, EventArgs e) throws Exception {
        textBackupRestoreAtoZToPath.Enabled = shouldUseAtoZFolder();
        butBrowseRestoreAtoZTo.Enabled = shouldUseAtoZFolder();
    }

}


