//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:57 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormLicense;
import OpenDental.FormOpenDental;
import OpenDental.FormProgress;
import OpenDental.FormShutdown;
import OpenDental.FormUpdate;
import OpenDental.FormUpdateInstallMsg;
import OpenDental.FormUpdateSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Computers;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.Security;
import OpenDentBusiness.Signalod;
import OpenDentBusiness.Signalods;
import OpenDentBusiness.SignalType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormUpdate  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label labelVersion = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDownload;
    private OpenDental.UI.Button butCheck;
    private System.Windows.Forms.TextBox textUpdateCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textWebsitePath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private IContainer components = new IContainer();
    private TextBox textResult = new TextBox();
    private TextBox textResult2 = new TextBox();
    private Label label4 = new Label();
    private Label label6 = new Label();
    private Panel panel1 = new Panel();
    private Label label9 = new Label();
    private Label label10 = new Label();
    private Label label7 = new Label();
    private Label label8 = new Label();
    private MainMenu mainMenu1 = new MainMenu();
    private MenuItem menuItemSetup = new MenuItem();
    private Panel panelClassic = new Panel();
    private OpenDental.UI.Button butLicense;
    private TextBox textConnectionMessage = new TextBox();
    private GroupBox groupBuild = new GroupBox();
    private Label label2 = new Label();
    private TextBox textBuild = new TextBox();
    private OpenDental.UI.Button butInstallBuild;
    private GroupBox groupBeta = new GroupBox();
    private TextBox textBeta = new TextBox();
    private OpenDental.UI.Button butInstallBeta;
    private Label label5 = new Label();
    private GroupBox groupStable = new GroupBox();
    private TextBox textStable = new TextBox();
    private OpenDental.UI.Button butInstallStable;
    private Label label11 = new Label();
    private OpenDental.UI.Button butCheck2;
    //OD1
    //<summary>Includes path</summary>
    //string WriteToFile;
    private static String buildAvailable = new String();
    private static String buildAvailableCode = new String();
    private static String buildAvailableDisplay = new String();
    private static String stableAvailable = new String();
    private static String stableAvailableCode = new String();
    private static String stableAvailableDisplay = new String();
    private static String betaAvailable = new String();
    private static String betaAvailableCode = new String();
    private OpenDental.UI.Button butDownloadMsiBuild;
    private OpenDental.UI.Button butDownloadMsiBeta;
    private OpenDental.UI.Button butDownloadMsiStable;
    private static String betaAvailableDisplay = new String();
    /**
    * 
    */
    public FormUpdate() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUpdate.class);
        this.labelVersion = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textUpdateCode = new System.Windows.Forms.TextBox();
        this.textWebsitePath = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textResult = new System.Windows.Forms.TextBox();
        this.textResult2 = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.label9 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.panelClassic = new System.Windows.Forms.Panel();
        this.butCheck = new OpenDental.UI.Button();
        this.butDownload = new OpenDental.UI.Button();
        this.textConnectionMessage = new System.Windows.Forms.TextBox();
        this.groupBuild = new System.Windows.Forms.GroupBox();
        this.butDownloadMsiBuild = new OpenDental.UI.Button();
        this.textBuild = new System.Windows.Forms.TextBox();
        this.butInstallBuild = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBeta = new System.Windows.Forms.GroupBox();
        this.butDownloadMsiBeta = new OpenDental.UI.Button();
        this.textBeta = new System.Windows.Forms.TextBox();
        this.butInstallBeta = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.groupStable = new System.Windows.Forms.GroupBox();
        this.butDownloadMsiStable = new OpenDental.UI.Button();
        this.textStable = new System.Windows.Forms.TextBox();
        this.butInstallStable = new OpenDental.UI.Button();
        this.label11 = new System.Windows.Forms.Label();
        this.butCheck2 = new OpenDental.UI.Button();
        this.butLicense = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.panelClassic.SuspendLayout();
        this.groupBuild.SuspendLayout();
        this.groupBeta.SuspendLayout();
        this.groupStable.SuspendLayout();
        this.SuspendLayout();
        //
        // labelVersion
        //
        this.labelVersion.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelVersion.Location = new System.Drawing.Point(74, 9);
        this.labelVersion.Name = "labelVersion";
        this.labelVersion.Size = new System.Drawing.Size(176, 20);
        this.labelVersion.TabIndex = 10;
        this.labelVersion.Text = "Using Version ";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(0, 0);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 23);
        this.label1.TabIndex = 0;
        //
        // textUpdateCode
        //
        this.textUpdateCode.Location = new System.Drawing.Point(129, 100);
        this.textUpdateCode.Name = "textUpdateCode";
        this.textUpdateCode.Size = new System.Drawing.Size(113, 20);
        this.textUpdateCode.TabIndex = 19;
        //
        // textWebsitePath
        //
        this.textWebsitePath.Location = new System.Drawing.Point(129, 77);
        this.textWebsitePath.Name = "textWebsitePath";
        this.textWebsitePath.Size = new System.Drawing.Size(388, 20);
        this.textWebsitePath.TabIndex = 24;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(24, 78);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(105, 19);
        this.label3.TabIndex = 26;
        this.label3.Text = "Website Path";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textResult
        //
        this.textResult.AcceptsReturn = true;
        this.textResult.BackColor = System.Drawing.SystemColors.Window;
        this.textResult.Location = new System.Drawing.Point(129, 156);
        this.textResult.Name = "textResult";
        this.textResult.ReadOnly = true;
        this.textResult.Size = new System.Drawing.Size(388, 20);
        this.textResult.TabIndex = 34;
        //
        // textResult2
        //
        this.textResult2.AcceptsReturn = true;
        this.textResult2.BackColor = System.Drawing.SystemColors.Window;
        this.textResult2.Location = new System.Drawing.Point(129, 179);
        this.textResult2.Multiline = true;
        this.textResult2.Name = "textResult2";
        this.textResult2.ReadOnly = true;
        this.textResult2.Size = new System.Drawing.Size(388, 66);
        this.textResult2.TabIndex = 35;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(6, 100);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(120, 19);
        this.label4.TabIndex = 34;
        this.label4.Text = "Update Code";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(10, 8);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(555, 58);
        this.label6.TabIndex = 40;
        this.label6.Text = resources.GetString("label6.Text");
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panel1.Location = new System.Drawing.Point(5, 529);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(630, 4);
        this.panel1.TabIndex = 42;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(12, 579);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(433, 23);
        this.label9.TabIndex = 47;
        this.label9.Text = "All CDT codes are Copyrighted by the ADA.";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(12, 557);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(550, 23);
        this.label10.TabIndex = 44;
        this.label10.Text = "This program Copyright 2003-2007, Jordan S. Sparks, D.M.D.";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(12, 538);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(549, 20);
        this.label7.TabIndex = 46;
        this.label7.Text = "This software is licensed under the GPL, www.opensource.org/licenses/gpl-license." + "php";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(12, 601);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(433, 20);
        this.label8.TabIndex = 45;
        this.label8.Text = "MySQL - Copyright 1995-2007, www.mysql.com";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // panelClassic
        //
        this.panelClassic.Controls.Add(this.textWebsitePath);
        this.panelClassic.Controls.Add(this.textUpdateCode);
        this.panelClassic.Controls.Add(this.butCheck);
        this.panelClassic.Controls.Add(this.label3);
        this.panelClassic.Controls.Add(this.textResult);
        this.panelClassic.Controls.Add(this.label4);
        this.panelClassic.Controls.Add(this.label6);
        this.panelClassic.Controls.Add(this.textResult2);
        this.panelClassic.Controls.Add(this.butDownload);
        this.panelClassic.Location = new System.Drawing.Point(471, 12);
        this.panelClassic.Name = "panelClassic";
        this.panelClassic.Size = new System.Drawing.Size(568, 494);
        this.panelClassic.TabIndex = 48;
        this.panelClassic.Visible = false;
        //
        // butCheck
        //
        this.butCheck.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheck.setAutosize(true);
        this.butCheck.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheck.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheck.setCornerRadius(4F);
        this.butCheck.Location = new System.Drawing.Point(129, 125);
        this.butCheck.Name = "butCheck";
        this.butCheck.Size = new System.Drawing.Size(117, 25);
        this.butCheck.TabIndex = 21;
        this.butCheck.Text = "Check for Updates";
        this.butCheck.Click += new System.EventHandler(this.butCheck_Click);
        //
        // butDownload
        //
        this.butDownload.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownload.setAutosize(true);
        this.butDownload.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownload.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownload.setCornerRadius(4F);
        this.butDownload.Location = new System.Drawing.Point(129, 251);
        this.butDownload.Name = "butDownload";
        this.butDownload.Size = new System.Drawing.Size(83, 25);
        this.butDownload.TabIndex = 20;
        this.butDownload.Text = "Download";
        this.butDownload.Click += new System.EventHandler(this.butDownload_Click);
        //
        // textConnectionMessage
        //
        this.textConnectionMessage.AcceptsReturn = true;
        this.textConnectionMessage.BackColor = System.Drawing.SystemColors.Window;
        this.textConnectionMessage.Location = new System.Drawing.Point(77, 62);
        this.textConnectionMessage.Multiline = true;
        this.textConnectionMessage.Name = "textConnectionMessage";
        this.textConnectionMessage.ReadOnly = true;
        this.textConnectionMessage.Size = new System.Drawing.Size(388, 66);
        this.textConnectionMessage.TabIndex = 50;
        //
        // groupBuild
        //
        this.groupBuild.Controls.Add(this.butDownloadMsiBuild);
        this.groupBuild.Controls.Add(this.textBuild);
        this.groupBuild.Controls.Add(this.butInstallBuild);
        this.groupBuild.Controls.Add(this.label2);
        this.groupBuild.Location = new System.Drawing.Point(77, 141);
        this.groupBuild.Name = "groupBuild";
        this.groupBuild.Size = new System.Drawing.Size(388, 111);
        this.groupBuild.TabIndex = 51;
        this.groupBuild.TabStop = false;
        this.groupBuild.Text = "A new build is available for the current version";
        this.groupBuild.Visible = false;
        //
        // butDownloadMsiBuild
        //
        this.butDownloadMsiBuild.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownloadMsiBuild.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDownloadMsiBuild.setAutosize(true);
        this.butDownloadMsiBuild.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownloadMsiBuild.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownloadMsiBuild.setCornerRadius(4F);
        this.butDownloadMsiBuild.Location = new System.Drawing.Point(220, 80);
        this.butDownloadMsiBuild.Name = "butDownloadMsiBuild";
        this.butDownloadMsiBuild.Size = new System.Drawing.Size(83, 25);
        this.butDownloadMsiBuild.TabIndex = 52;
        this.butDownloadMsiBuild.Text = "Download msi";
        this.butDownloadMsiBuild.Click += new System.EventHandler(this.butDownMsiBuild_Click);
        //
        // textBuild
        //
        this.textBuild.AcceptsReturn = true;
        this.textBuild.BackColor = System.Drawing.SystemColors.Window;
        this.textBuild.Location = new System.Drawing.Point(6, 54);
        this.textBuild.Name = "textBuild";
        this.textBuild.ReadOnly = true;
        this.textBuild.Size = new System.Drawing.Size(376, 20);
        this.textBuild.TabIndex = 51;
        //
        // butInstallBuild
        //
        this.butInstallBuild.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInstallBuild.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butInstallBuild.setAutosize(true);
        this.butInstallBuild.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInstallBuild.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInstallBuild.setCornerRadius(4F);
        this.butInstallBuild.Location = new System.Drawing.Point(309, 80);
        this.butInstallBuild.Name = "butInstallBuild";
        this.butInstallBuild.Size = new System.Drawing.Size(73, 25);
        this.butInstallBuild.TabIndex = 28;
        this.butInstallBuild.Text = "Install";
        this.butInstallBuild.Click += new System.EventHandler(this.butInstallBuild_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(374, 29);
        this.label2.TabIndex = 27;
        this.label2.Text = "These are typically bug fixes.  It is strongly recommended to install any availab" + "le fixes.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBeta
        //
        this.groupBeta.Controls.Add(this.butDownloadMsiBeta);
        this.groupBeta.Controls.Add(this.textBeta);
        this.groupBeta.Controls.Add(this.butInstallBeta);
        this.groupBeta.Controls.Add(this.label5);
        this.groupBeta.Location = new System.Drawing.Point(77, 393);
        this.groupBeta.Name = "groupBeta";
        this.groupBeta.Size = new System.Drawing.Size(388, 119);
        this.groupBeta.TabIndex = 52;
        this.groupBeta.TabStop = false;
        this.groupBeta.Text = "A new beta version is available";
        this.groupBeta.Visible = false;
        //
        // butDownloadMsiBeta
        //
        this.butDownloadMsiBeta.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownloadMsiBeta.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDownloadMsiBeta.setAutosize(true);
        this.butDownloadMsiBeta.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownloadMsiBeta.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownloadMsiBeta.setCornerRadius(4F);
        this.butDownloadMsiBeta.Location = new System.Drawing.Point(220, 88);
        this.butDownloadMsiBeta.Name = "butDownloadMsiBeta";
        this.butDownloadMsiBeta.Size = new System.Drawing.Size(83, 25);
        this.butDownloadMsiBeta.TabIndex = 53;
        this.butDownloadMsiBeta.Text = "Download msi";
        this.butDownloadMsiBeta.Click += new System.EventHandler(this.butDownloadMsiBeta_Click);
        //
        // textBeta
        //
        this.textBeta.AcceptsReturn = true;
        this.textBeta.BackColor = System.Drawing.SystemColors.Window;
        this.textBeta.Location = new System.Drawing.Point(6, 62);
        this.textBeta.Name = "textBeta";
        this.textBeta.ReadOnly = true;
        this.textBeta.Size = new System.Drawing.Size(376, 20);
        this.textBeta.TabIndex = 51;
        //
        // butInstallBeta
        //
        this.butInstallBeta.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInstallBeta.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butInstallBeta.setAutosize(true);
        this.butInstallBeta.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInstallBeta.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInstallBeta.setCornerRadius(4F);
        this.butInstallBeta.Location = new System.Drawing.Point(309, 88);
        this.butInstallBeta.Name = "butInstallBeta";
        this.butInstallBeta.Size = new System.Drawing.Size(73, 25);
        this.butInstallBeta.TabIndex = 28;
        this.butInstallBeta.Text = "Install";
        this.butInstallBeta.Click += new System.EventHandler(this.butInstallBeta_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 13);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(374, 46);
        this.label5.TabIndex = 27;
        this.label5.Text = "This beta version will be very functional, but will have some bugs.  Use a beta v" + "ersion only if you demand the latest features.  Be sure to update regularly.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupStable
        //
        this.groupStable.Controls.Add(this.butDownloadMsiStable);
        this.groupStable.Controls.Add(this.textStable);
        this.groupStable.Controls.Add(this.butInstallStable);
        this.groupStable.Controls.Add(this.label11);
        this.groupStable.Location = new System.Drawing.Point(77, 267);
        this.groupStable.Name = "groupStable";
        this.groupStable.Size = new System.Drawing.Size(388, 111);
        this.groupStable.TabIndex = 53;
        this.groupStable.TabStop = false;
        this.groupStable.Text = "A new stable version is available";
        this.groupStable.Visible = false;
        //
        // butDownloadMsiStable
        //
        this.butDownloadMsiStable.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownloadMsiStable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDownloadMsiStable.setAutosize(true);
        this.butDownloadMsiStable.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownloadMsiStable.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownloadMsiStable.setCornerRadius(4F);
        this.butDownloadMsiStable.Location = new System.Drawing.Point(220, 80);
        this.butDownloadMsiStable.Name = "butDownloadMsiStable";
        this.butDownloadMsiStable.Size = new System.Drawing.Size(83, 25);
        this.butDownloadMsiStable.TabIndex = 53;
        this.butDownloadMsiStable.Text = "Download msi";
        this.butDownloadMsiStable.Click += new System.EventHandler(this.butDownloadMsiStable_Click);
        //
        // textStable
        //
        this.textStable.AcceptsReturn = true;
        this.textStable.BackColor = System.Drawing.SystemColors.Window;
        this.textStable.Location = new System.Drawing.Point(6, 54);
        this.textStable.Name = "textStable";
        this.textStable.ReadOnly = true;
        this.textStable.Size = new System.Drawing.Size(376, 20);
        this.textStable.TabIndex = 51;
        //
        // butInstallStable
        //
        this.butInstallStable.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInstallStable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butInstallStable.setAutosize(true);
        this.butInstallStable.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInstallStable.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInstallStable.setCornerRadius(4F);
        this.butInstallStable.Location = new System.Drawing.Point(309, 80);
        this.butInstallStable.Name = "butInstallStable";
        this.butInstallStable.Size = new System.Drawing.Size(73, 25);
        this.butInstallStable.TabIndex = 28;
        this.butInstallStable.Text = "Install";
        this.butInstallStable.Click += new System.EventHandler(this.butInstallStable_Click);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(6, 22);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(374, 29);
        this.label11.TabIndex = 27;
        this.label11.Text = "Will have nearly zero bugs.  Will provide many useful enhanced features.";
        this.label11.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butCheck2
        //
        this.butCheck2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheck2.setAutosize(true);
        this.butCheck2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheck2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheck2.setCornerRadius(4F);
        this.butCheck2.Location = new System.Drawing.Point(77, 31);
        this.butCheck2.Name = "butCheck2";
        this.butCheck2.Size = new System.Drawing.Size(117, 25);
        this.butCheck2.TabIndex = 54;
        this.butCheck2.Text = "Check for Updates";
        this.butCheck2.Visible = false;
        this.butCheck2.Click += new System.EventHandler(this.butCheck2_Click);
        //
        // butLicense
        //
        this.butLicense.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLicense.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLicense.setAutosize(true);
        this.butLicense.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLicense.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLicense.setCornerRadius(4F);
        this.butLicense.Location = new System.Drawing.Point(466, 595);
        this.butLicense.Name = "butLicense";
        this.butLicense.Size = new System.Drawing.Size(88, 25);
        this.butLicense.TabIndex = 49;
        this.butLicense.Text = "View Licenses";
        this.butLicense.Click += new System.EventHandler(this.butLicense_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(560, 595);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 25);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormUpdate
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(647, 635);
        this.Controls.Add(this.panelClassic);
        this.Controls.Add(this.butCheck2);
        this.Controls.Add(this.groupStable);
        this.Controls.Add(this.groupBeta);
        this.Controls.Add(this.groupBuild);
        this.Controls.Add(this.textConnectionMessage);
        this.Controls.Add(this.butLicense);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.labelVersion);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.Menu = this.mainMenu1;
        this.MinimizeBox = false;
        this.Name = "FormUpdate";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Update";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormUpdate_FormClosing);
        this.Load += new System.EventHandler(this.FormUpdate_Load);
        this.panelClassic.ResumeLayout(false);
        this.panelClassic.PerformLayout();
        this.groupBuild.ResumeLayout(false);
        this.groupBuild.PerformLayout();
        this.groupBeta.ResumeLayout(false);
        this.groupBeta.PerformLayout();
        this.groupStable.ResumeLayout(false);
        this.groupStable.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formUpdate_Load(Object sender, System.EventArgs e) throws Exception {
        setMsiVisibility();
        labelVersion.Text = Lan.g(this,"Using Version:") + " " + Application.ProductVersion;
        //keeps the trailing year up to date
        this.label10.Text = Lan.g(this,"This program Copyright 2003-") + DateTime.Now.ToString("yyyy") + Lan.g(this,", Jordan S. Sparks, D.M.D.");
        this.label8.Text = Lan.g(this,"MySQL - Copyright 1995-") + DateTime.Now.ToString("yyyy") + Lan.g(this,", www.mysql.com");
        if (PrefC.getBool(PrefName.UpdateWindowShowsClassicView))
        {
            panelClassic.Visible = true;
            panelClassic.Location = new Point(67, 29);
            textUpdateCode.Text = PrefC.getString(PrefName.UpdateCode);
            textWebsitePath.Text = PrefC.getString(PrefName.UpdateWebsitePath);
            //should include trailing /
            butDownload.Enabled = false;
            if (!Security.isAuthorized(Permissions.Setup))
            {
                //gives a message box if no permission
                butCheck.Enabled = false;
            }
             
        }
        else
        {
            if (Security.isAuthorized(Permissions.Setup,true))
            {
                butCheck2.Visible = true;
            }
            else
            {
                textConnectionMessage.Text = Lan.g(this,"Not authorized for") + " " + GroupPermissions.getDesc(Permissions.Setup);
            } 
        } 
    }

    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.UpdateWindowShowsClassicView))
        {
            return ;
        }
         
        FormUpdateSetup FormU = new FormUpdateSetup();
        FormU.ShowDialog();
        setMsiVisibility();
    }

    private void setMsiVisibility() throws Exception {
        butDownloadMsiBuild.Visible = PrefC.getBool(PrefName.UpdateShowMsiButtons);
        butDownloadMsiStable.Visible = PrefC.getBool(PrefName.UpdateShowMsiButtons);
        butDownloadMsiBeta.Visible = PrefC.getBool(PrefName.UpdateShowMsiButtons);
    }

    private void butCheck2_Click(Object sender, EventArgs e) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ServerWeb)
        {
            MsgBox.show(this,"Updates are only allowed from the web server");
            return ;
        }
         
        //using web service
        if (!StringSupport.equals(PrefC.getString(PrefName.WebServiceServerName), "") && !CodeBase.ODEnvironment.IdIsThisComputer(PrefC.getString(PrefName.WebServiceServerName).ToLower()))
        {
            //and not on web server
            MessageBox.Show(Lan.g(this,"Updates are only allowed from the web server: ") + PrefC.getString(PrefName.WebServiceServerName));
            return ;
        }
         
        if (ReplicationServers.serverIsBlocked())
        {
            MsgBox.show(this,"Updates are not allowed on this replication server");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        groupBuild.Visible = false;
        groupStable.Visible = false;
        groupBeta.Visible = false;
        textConnectionMessage.Text = Lan.g(this,"Attempting to connect to web service......");
        Application.DoEvents();
        String result = "";
        try
        {
            result = sendAndReceiveXml();
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show("Error: " + ex.Message);
            return ;
        }

        textConnectionMessage.Text = Lan.g(this,"Connection successful.");
        Cursor = Cursors.Default;
        try
        {
            //MessageBox.Show(result);
            parseXml(result);
        }
        catch (Exception ex)
        {
            //fills the six static variables with values.
            textConnectionMessage.Text = ex.Message;
            MessageBox.Show(ex.Message, "Error");
            return ;
        }

        if (!StringSupport.equals(buildAvailableDisplay, ""))
        {
            groupBuild.Visible = true;
            textBuild.Text = buildAvailableDisplay;
        }
         
        if (!StringSupport.equals(stableAvailableDisplay, ""))
        {
            groupStable.Visible = true;
            textStable.Text = stableAvailableDisplay;
        }
         
        if (!StringSupport.equals(betaAvailableDisplay, ""))
        {
            groupBeta.Visible = true;
            textBeta.Text = betaAvailableDisplay;
        }
         
        if (StringSupport.equals(betaAvailable, "") && StringSupport.equals(stableAvailable, "") && StringSupport.equals(buildAvailable, ""))
        {
            textConnectionMessage.Text += Lan.g(this,"  There are no downloads available.");
        }
        else
        {
            textConnectionMessage.Text += Lan.g(this,"  The following downloads are available.  Be sure to stop the program on all other computers in the office before installing.");
        } 
    }

    /**
    * Parses the xml result from the server and uses it to fill the 9 static variables.  Or can throw an exception if some sort of error.
    */
    private static void parseXml(String result) throws Exception {
        XmlDocument doc = new XmlDocument();
        doc.LoadXml(result);
        XmlNode node = doc.SelectSingleNode("//Error");
        if (node != null)
        {
            throw new Exception(node.InnerText);
        }
         
        //textConnectionMessage.Text=node.InnerText;
        //MessageBox.Show(node.InnerText,"Error");
        //return;
        node = doc.SelectSingleNode("//KeyDisabled");
        if (node == null)
        {
            //no error, and no disabled message
            if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,false))
            {
                //this is one of two places in the program where this happens.
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
        }
        else
        {
            //textConnectionMessage.Text=node.InnerText;
            //MessageBox.Show(node.InnerText);
            if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,true))
            {
                //this is one of two places in the program where this happens.
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
            throw new Exception(node.InnerText);
        } 
        //return;
        node = doc.SelectSingleNode("//BuildAvailable");
        buildAvailable = "";
        buildAvailableCode = "";
        buildAvailableDisplay = "";
        if (node != null)
        {
            node = doc.SelectSingleNode("//BuildAvailable/Display");
            if (node != null)
            {
                buildAvailableDisplay = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//BuildAvailable/MajMinBuildF");
            if (node != null)
            {
                buildAvailable = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//BuildAvailable/UpdateCode");
            if (node != null)
            {
                buildAvailableCode = node.InnerText;
            }
             
        }
         
        node = doc.SelectSingleNode("//StableAvailable");
        stableAvailable = "";
        stableAvailableCode = "";
        stableAvailableDisplay = "";
        if (node != null)
        {
            node = doc.SelectSingleNode("//StableAvailable/Display");
            if (node != null)
            {
                stableAvailableDisplay = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//StableAvailable/MajMinBuildF");
            if (node != null)
            {
                stableAvailable = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//StableAvailable/UpdateCode");
            if (node != null)
            {
                stableAvailableCode = node.InnerText;
            }
             
        }
         
        node = doc.SelectSingleNode("//BetaAvailable");
        betaAvailable = "";
        betaAvailableCode = "";
        betaAvailableDisplay = "";
        if (node != null)
        {
            node = doc.SelectSingleNode("//BetaAvailable/Display");
            if (node != null)
            {
                betaAvailableDisplay = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//BetaAvailable/MajMinBuildF");
            if (node != null)
            {
                betaAvailable = node.InnerText;
            }
             
            node = doc.SelectSingleNode("//BetaAvailable/UpdateCode");
            if (node != null)
            {
                betaAvailableCode = node.InnerText;
            }
             
        }
         
    }

    private static String sendAndReceiveXml() throws Exception {
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("UpdateRequest");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticeTitle");
                writer.WriteString(PrefC.getString(PrefName.PracticeTitle));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticePhone");
                writer.WriteString(PrefC.getString(PrefName.PracticePhone));
                writer.WriteEndElement();
                writer.WriteStartElement("ProgramVersion");
                writer.WriteString(PrefC.getString(PrefName.ProgramVersion));
                writer.WriteEndElement();
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
        {
            IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
            ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
            proxy.Credentials = cred;
            updateService.Proxy = proxy;
        }
         
        String result = "";
        //try {
        result = updateService.RequestUpdate(strbuild.ToString());
        return result;
    }

    //may throw error
    //}
    //catch(Exception ex) {
    //	Cursor=Cursors.Default;
    //	MessageBox.Show("Error: "+ex.Message);
    //	return;
    //}
    /**
    * Ryan is working on implementing this. It has been copied from SendAndReceiveXml() above.
    */
    private static String requestSNOMEDDownload() throws Exception {
        throw new Exception("Ryan is working on implementing this. It has been copied from SendAndReceiveXml() above.");
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("UpdateRequest");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticeTitle");
                //now also includes address for requesting SNOMED
                writer.WriteString(PrefC.getString(PrefName.PracticeTitle) + PrefC.getString(PrefName.PracticeAddress));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticePhone");
                writer.WriteString(PrefC.getString(PrefName.PracticePhone));
                writer.WriteEndElement();
                writer.WriteStartElement("ProgramVersion");
                writer.WriteString(PrefC.getString(PrefName.ProgramVersion));
                writer.WriteEndElement();
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
        {
            IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
            ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
            proxy.Credentials = cred;
            updateService.Proxy = proxy;
        }
         
        String result = "";
        //try {
        result = updateService.RequestUpdate(strbuild.ToString());
        return result;
    }

    //may throw error
    //}
    //catch(Exception ex) {
    //	Cursor=Cursors.Default;
    //	MessageBox.Show("Error: "+ex.Message);
    //	return;
    //}
    /**
    * Used if we already have the correct version of the program installed, but we need the UpdateCode in order to download the Setup.exe again.  Like when using multiple databases.
    */
    public static String getUpdateCodeForThisVersion() throws Exception {
        String result = result = sendAndReceiveXml();
        //exception bubbles up.
        parseXml(result);
        //see if any of the three versions exactly matches this current version.
        Version thisVersion = new Version(Application.ProductVersion);
        String thisVersStr = thisVersion.ToString(3);
        String testVers = new String();
        testVers = buildAvailable.TrimEnd('f');
        if (StringSupport.equals(testVers, thisVersStr))
        {
            return buildAvailableCode;
        }
         
        testVers = stableAvailable.TrimEnd('f');
        if (StringSupport.equals(testVers, thisVersStr))
        {
            return stableAvailableCode;
        }
         
        testVers = betaAvailable.TrimEnd('f');
        if (StringSupport.equals(testVers, thisVersStr))
        {
            return betaAvailableCode;
        }
         
        return "";
    }

    private void butInstallBuild_Click(Object sender, EventArgs e) throws Exception {
        String patchName = "Setup.exe";
        String fileNameWithVers = buildAvailable;
        //6.9.23F
        fileNameWithVers = fileNameWithVers.Replace("F", "");
        //6.9.23
        fileNameWithVers = fileNameWithVers.Replace(".", "_");
        //6_9_23
        fileNameWithVers = "Setup_" + fileNameWithVers + ".exe";
        //Setup_6_9_23.exe
        String destDir = ImageStore.getPreferredAtoZpath();
        String destPath2 = null;
        if (destDir == null)
        {
            //Not using A to Z folders?
            destDir = Path.GetTempPath();
        }
        else
        {
            //destDir2=null;//already null
            //using A to Z folders.
            destPath2 = CodeBase.ODFileUtils.combinePaths(destDir,"SetupFiles");
            if (!Directory.Exists(destPath2))
            {
                Directory.CreateDirectory(destPath2);
            }
             
            destPath2 = CodeBase.ODFileUtils.combinePaths(destPath2,fileNameWithVers);
        } 
        //Source URI
        //Local destination file.
        downloadInstallPatchFromURI(PrefC.getString(PrefName.UpdateWebsitePath) + buildAvailableCode + "/" + patchName,CodeBase.ODFileUtils.combinePaths(destDir,patchName),true,true,destPath2);
    }

    //second destination file.  Might be null.
    private void butInstallStable_Click(Object sender, EventArgs e) throws Exception {
        FormUpdateInstallMsg FormUIM = new FormUpdateInstallMsg();
        FormUIM.ShowDialog();
        if (FormUIM.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        String patchName = "Setup.exe";
        String fileNameWithVers = stableAvailable;
        fileNameWithVers = fileNameWithVers.Replace("F", "");
        fileNameWithVers = fileNameWithVers.Replace(".", "_");
        fileNameWithVers = "Setup_" + fileNameWithVers + ".exe";
        String destDir = ImageStore.getPreferredAtoZpath();
        String destPath2 = null;
        if (destDir == null)
        {
            //Not using A to Z folders?
            destDir = Path.GetTempPath();
        }
        else
        {
            destPath2 = CodeBase.ODFileUtils.combinePaths(destDir,"SetupFiles");
            if (!Directory.Exists(destPath2))
            {
                Directory.CreateDirectory(destPath2);
            }
             
            destPath2 = CodeBase.ODFileUtils.combinePaths(destPath2,fileNameWithVers);
        } 
        //Source URI
        downloadInstallPatchFromURI(PrefC.getString(PrefName.UpdateWebsitePath) + stableAvailableCode + "/" + patchName,CodeBase.ODFileUtils.combinePaths(destDir,patchName),true,true,destPath2);
    }

    private void butInstallBeta_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Are you sure you really want to install a beta version?  Do NOT do this unless you are OK with some bugs.  Continue?"))
        {
            return ;
        }
         
        FormUpdateInstallMsg FormUIM = new FormUpdateInstallMsg();
        FormUIM.ShowDialog();
        if (FormUIM.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        String patchName = "Setup.exe";
        String fileNameWithVers = betaAvailable;
        fileNameWithVers = fileNameWithVers.Replace("F", "");
        fileNameWithVers = fileNameWithVers.Replace(".", "_");
        fileNameWithVers = "Setup_" + fileNameWithVers + ".exe";
        String destDir = ImageStore.getPreferredAtoZpath();
        String destPath2 = null;
        if (destDir == null)
        {
            //Not using A to Z folders?
            destDir = Path.GetTempPath();
        }
        else
        {
            destPath2 = CodeBase.ODFileUtils.combinePaths(destDir,"SetupFiles");
            if (!Directory.Exists(destPath2))
            {
                Directory.CreateDirectory(destPath2);
            }
             
            destPath2 = CodeBase.ODFileUtils.combinePaths(destPath2,fileNameWithVers);
        } 
        //Source URI
        downloadInstallPatchFromURI(PrefC.getString(PrefName.UpdateWebsitePath) + betaAvailableCode + "/" + patchName,CodeBase.ODFileUtils.combinePaths(destDir,patchName),true,true,destPath2);
    }

    private void butDownMsiBuild_Click(Object sender, EventArgs e) throws Exception {
        String fileName = PrefC.getString(PrefName.UpdateWebsitePath) + buildAvailableCode + "/OpenDental.msi";
        Process.Start(fileName);
    }

    private void butDownloadMsiStable_Click(Object sender, EventArgs e) throws Exception {
        String fileName = PrefC.getString(PrefName.UpdateWebsitePath) + stableAvailableCode + "/OpenDental.msi";
        Process.Start(fileName);
    }

    private void butDownloadMsiBeta_Click(Object sender, EventArgs e) throws Exception {
        String fileName = PrefC.getString(PrefName.UpdateWebsitePath) + betaAvailableCode + "/OpenDental.msi";
        Process.Start(fileName);
    }

    private void butCheck_Click(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        savePrefs();
        checkMain();
        //CheckClaimForm();
        Cursor = Cursors.Default;
    }

    private void checkMain() throws Exception {
        butDownload.Enabled = false;
        textResult.Text = "";
        textResult2.Text = "";
        if (textUpdateCode.Text.Length == 0)
        {
            textResult.Text += Lan.g(this,"Registration number not valid.");
            return ;
        }
         
        String updateInfoMajor = "";
        String updateInfoMinor = "";
        RefSupport<String> refVar___0 = new RefSupport<String>();
        RefSupport<String> refVar___1 = new RefSupport<String>();
        butDownload.Enabled = ShouldDownloadUpdate(textWebsitePath.Text, textUpdateCode.Text, refVar___0, refVar___1);
        updateInfoMajor = refVar___0.getValue();
        updateInfoMinor = refVar___1.getValue();
        textResult.Text = updateInfoMajor;
        textResult2.Text = updateInfoMinor;
    }

    /**
    * Returns true if the download at the specified remoteUri with the given registration code should be downloaded and installed as an update, and false is returned otherwise. Also, information about the decision making process is stored in the updateInfoMajor and updateInfoMinor strings, but only holds significance to a human user.
    */
    public static boolean shouldDownloadUpdate(String remoteUri, String updateCode, RefSupport<String> updateInfoMajor, RefSupport<String> updateInfoMinor) throws Exception {
        updateInfoMajor.setValue("");
        updateInfoMinor.setValue("");
        boolean shouldDownload = false;
        String fileName = "Manifest.txt";
        WebClient myWebClient = new WebClient();
        String myStringWebResource = remoteUri + updateCode + "/" + fileName;
        Version versionNewBuild = null;
        String strNewVersion = "";
        String newBuild = "";
        boolean buildIsBeta = false;
        boolean versionIsBeta = false;
        try
        {
            StreamReader sr = new StreamReader(myWebClient.OpenRead(myStringWebResource));
            try
            {
                {
                    newBuild = sr.ReadLine();
                    //must be be 3 or 4 components (revision is optional)
                    strNewVersion = sr.ReadLine();
                }
            }
            finally
            {
                if (sr != null)
                    Disposable.mkDisposable(sr).dispose();
                 
            }
            //returns null if no second line
            if (newBuild.EndsWith("b"))
            {
                buildIsBeta = true;
                newBuild = newBuild.Replace("b", "");
            }
             
            versionNewBuild = new Version(newBuild);
            if (versionNewBuild.Revision == -1)
            {
                versionNewBuild = new Version(versionNewBuild.Major, versionNewBuild.Minor, versionNewBuild.Build, 0);
            }
             
            if (strNewVersion != null && strNewVersion.EndsWith("b"))
            {
                versionIsBeta = true;
                strNewVersion = strNewVersion.Replace("b", "");
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            updateInfoMajor.setValue(updateInfoMajor.getValue() + Lan.g("FormUpdate","Registration number not valid, or internet connection failed.  "));
            return false;
        }

        if (versionNewBuild == new Version(Application.ProductVersion))
        {
            updateInfoMajor.setValue(updateInfoMajor.getValue() + Lan.g("FormUpdate","You are using the most current build of this version.  "));
        }
        else
        {
            //this also allows users to install previous versions.
            updateInfoMajor.setValue(updateInfoMajor.getValue() + (Lan.g("FormUpdate","A new build of this version is available for download:  ") + versionNewBuild.ToString()));
            if (buildIsBeta)
            {
                updateInfoMajor.setValue(updateInfoMajor.getValue() + Lan.g("FormUpdate","(beta)  "));
            }
             
            shouldDownload = true;
        } 
        //Whether or not build is current, we want to inform user about the next minor version
        if (strNewVersion != null)
        {
            //we don't really care what it is.
            updateInfoMinor.setValue(updateInfoMinor.getValue() + Lan.g("FormUpdate","A newer version is also available.  "));
            if (versionIsBeta)
            {
                //(checkNewBuild.Checked || checkNewVersion.Checked) && versionIsBeta){
                updateInfoMinor.setValue(updateInfoMinor.getValue() + Lan.g("FormUpdate","It is beta (test), so it has some bugs and " + "you will need to update it frequently.  "));
            }
             
            updateInfoMinor.setValue(updateInfoMinor.getValue() + Lan.g("FormUpdate","Contact us for a new Registration number if you wish to use it.  "));
        }
         
        return shouldDownload;
    }

    private void butDownload_Click(Object sender, System.EventArgs e) throws Exception {
        String patchName = "Setup.exe";
        String destDir = ImageStore.getPreferredAtoZpath();
        if (destDir == null)
        {
            //Not using A to Z folders?
            destDir = Path.GetTempPath();
        }
         
        //Source URI
        DownloadInstallPatchFromURI(textWebsitePath.Text + textUpdateCode.Text + "/" + patchName, CodeBase.ODFileUtils.combinePaths(destDir,patchName), true, false, null);
    }

    //Local destination file.
    /**
    * destinationPath includes filename (Setup.exe).  destinationPath2 will create a second copy at the specified path/filename, or it will be skipped if null or empty.
    */
    public static void downloadInstallPatchFromURI(String downloadUri, String destinationPath, boolean runSetupAfterDownload, boolean showShutdownWindow, String destinationPath2) throws Exception {
        String[] dblist = PrefC.getString(PrefName.UpdateMultipleDatabases).Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries);
        if (showShutdownWindow)
        {
            //Even if updating multiple databases, extra shutdown signals are not needed.
            FormShutdown FormSD = new FormShutdown();
            FormSD.IsUpdate = true;
            FormSD.ShowDialog();
            if (FormSD.DialogResult == DialogResult.OK)
            {
                //turn off signal reception for 5 seconds so this workstation will not shut down.
                FormOpenDental.signalLastRefreshed = MiscData.getNowDateTime().AddSeconds(5);
                Signalod sig = new Signalod();
                sig.ITypes = (((Enum)InvalidType.ShutDownNow).ordinal()).ToString();
                sig.SigType = SignalType.Invalid;
                Signalods.insert(sig);
                Computers.ClearAllHeartBeats(Environment.MachineName);
            }
             
            //always assume success
            //SecurityLogs.MakeLogEntry(Permissions.Setup,0,"Shutdown all workstations.");//can't do this because sometimes no user.
            //continue on even if user clicked cancel
            //no other workstation will be able to start up until this value is reset.
            Prefs.UpdateString(PrefName.UpdateInProgressOnComputerName, Environment.MachineName);
        }
         
        MiscData.LockWorkstationsForDbs(dblist);
        //lock workstations for other db's.
        File.Delete(destinationPath);
        WebRequest wr = WebRequest.Create(downloadUri);
        WebResponse webResp = null;
        try
        {
            webResp = wr.GetResponse();
        }
        catch (Exception ex)
        {
            CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(ex.Message + "\r\nUri: " + downloadUri);
            msgbox.ShowDialog();
            MiscData.UnlockWorkstationsForDbs(dblist);
            return ;
        }

        //unlock workstations since nothing was actually done.
        int fileSize = (int)webResp.ContentLength / 1024;
        FormProgress FormP = new FormProgress();
        System.Threading.ThreadStart downloadDelegate;
        Thread workerThread = new System.Threading.Thread(downloadDelegate);
        workerThread.Start();
        //display the progress dialog to the user:
        FormP.MaxVal = (double)fileSize / 1024;
        FormP.NumberMultiplication = 100;
        FormP.DisplayText = "?currentVal MB of ?maxVal MB copied";
        FormP.NumberFormat = "F";
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            workerThread.Abort();
            MiscData.UnlockWorkstationsForDbs(dblist);
            return ;
        }
         
        //unlock workstations since nothing was actually done.
        //copy to second destination directory
        if (destinationPath2 != null && !StringSupport.equals(destinationPath2, ""))
        {
            if (File.Exists(destinationPath2))
            {
                File.Delete(destinationPath2);
            }
             
            File.Copy(destinationPath, destinationPath2);
        }
         
        //copy the Setup.exe to the AtoZ folders for the other db's.
        List<String> atozNameList = MiscData.GetAtoZforDb(dblist);
        for (int i = 0;i < atozNameList.Count;i++)
        {
            if (StringSupport.equals(destinationPath, Path.Combine(atozNameList[i], "Setup.exe")))
            {
                continue;
            }
             
            //if they are sharing an AtoZ folder.
            if (Directory.Exists(atozNameList[i]))
            {
                //copy the Setup.exe that was just downloaded to this AtoZ folder
                File.Copy(destinationPath, Path.Combine(atozNameList[i], "Setup.exe"), true);
            }
             
        }
        //to the other atozFolder
        //overwrite
        if (!runSetupAfterDownload)
        {
            return ;
        }
         
        String msg = Lan.g("FormUpdate","Download succeeded.  Setup program will now begin.  When done, restart the program on this computer, then on the other computers.");
        if (dblist.Length > 0)
        {
            msg = "Download succeeded.  Setup file probably copied to other AtoZ folders as well.  Setup program will now begin.  When done, restart the program for each database on this computer, then on the other computers.";
        }
         
        if (MessageBox.Show(msg, "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            //Clicking cancel gives the user a chance to avoid running the setup program,
            Prefs.updateString(PrefName.UpdateInProgressOnComputerName,"");
            return ;
        }
         
        try
        {
            //unlock workstations, since nothing was actually done.
            Process.Start(destinationPath);
            Application.Exit();
        }
        catch (Exception __dummyCatchVar1)
        {
            Prefs.updateString(PrefName.UpdateInProgressOnComputerName,"");
            //unlock workstations, since nothing was actually done.
            MsgBox.show(FormP,"Could not launch setup");
        }
    
    }

    /**
    * This is the function that the worker thread uses to actually perform the download.  Can also call this method in the ordinary way if the file to be transferred is short.
    */
    private static void downloadInstallPatchWorker(String downloadUri, String destinationPath, RefSupport<FormProgress> progressIndicator) throws Exception {
        int chunk = 10;
        //KB
        byte[] buffer = new byte[]();
        int i = 0;
        WebClient myWebClient = new WebClient();
        Stream readStream = myWebClient.OpenRead(downloadUri);
        BinaryReader br = new BinaryReader(readStream);
        FileStream writeStream = new FileStream(destinationPath, FileMode.Create);
        BinaryWriter bw = new BinaryWriter(writeStream);
        try
        {
            while (true)
            {
                buffer = br.ReadBytes(chunk * 1024);
                if (buffer.Length == 0)
                {
                    break;
                }
                 
                double curVal = ((double)(chunk * i) + ((double)buffer.Length / 1024)) / 1024;
                progressIndicator.getValue().CurrentVal = curVal;
                bw.Write(buffer);
                i++;
            }
        }
        catch (Exception __dummyCatchVar2)
        {
            //for instance, if abort.
            br.Close();
            bw.Close();
            File.Delete(destinationPath);
        }
        finally
        {
            br.Close();
            bw.Close();
        }
    }

    //myWebClient.DownloadFile(downloadUri,ODFileUtils.CombinePaths(FormPath.GetPreferredImagePath(),"Setup.exe"));
    private void savePrefs() throws Exception {
        boolean changed = false;
        if (Prefs.UpdateString(PrefName.UpdateCode, textUpdateCode.Text))
        {
            changed = true;
        }
         
        if (Prefs.UpdateString(PrefName.UpdateWebsitePath, textWebsitePath.Text))
        {
            changed = true;
        }
         
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    private void butLicense_Click(Object sender, EventArgs e) throws Exception {
        FormLicense FormL = new FormLicense();
        FormL.ShowDialog();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formUpdate_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (Security.IsAuthorized(Permissions.Setup, DateTime.Now, true) && PrefC.getBool(PrefName.UpdateWindowShowsClassicView))
        {
            savePrefs();
        }
         
    }

}


