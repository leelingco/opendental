//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:57 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormEClinicalWorks;
import OpenDental.FormEcwDiag;
import OpenDental.Lan;
import OpenDental.localhost.Service1;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDentBusiness.UserGroups;

/**
* 
*/
public class FormEClinicalWorks  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.CheckBox checkEnabled = new System.Windows.Forms.CheckBox();
    private IContainer components = new IContainer();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProgName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProgDesc = new System.Windows.Forms.TextBox();
    // Required designer variable.
    /**
    * This Program link is new.
    */
    public boolean IsNew = new boolean();
    public Program ProgramCur;
    private List<ProgramProperty> PropertyList = new List<ProgramProperty>();
    //private static Thread thread;
    private TextBox textHL7FolderIn = new TextBox();
    private TextBox textHL7FolderOut = new TextBox();
    private Label labelHL7FolderOut = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private Label label5 = new Label();
    private Label labelDefaultUserGroup = new Label();
    private ComboBox comboDefaultUserGroup = new ComboBox();
    private CheckBox checkShowImages = new CheckBox();
    private RadioButton radioModeTight = new RadioButton();
    private RadioButton radioModeStandalone = new RadioButton();
    private CheckBox checkFeeSchedules = new CheckBox();
    private Service1 service11;
    private Label labelHL7Warning = new Label();
    private RadioButton radioModeFull = new RadioButton();
    private OpenDental.UI.Button butDiagnostic;
    private TextBox textECWServer = new TextBox();
    private Label label3 = new Label();
    private TextBox textODServer = new TextBox();
    private Label label6 = new Label();
    private TextBox textHL7ServiceName = new TextBox();
    private Label label4 = new Label();
    private TextBox textHL7Server = new TextBox();
    private Label label7 = new Label();
    private Label label8 = new Label();
    private Label label9 = new Label();
    private Label labelDefEnabledWarning = new Label();
    private Label label10 = new Label();
    private TextBox textMedPanelURL = new TextBox();
    private Label label11 = new Label();
    private Label labelHL7FolderIn = new Label();
    /**
    * 
    */
    public FormEClinicalWorks() throws Exception {
        components = new System.ComponentModel.Container();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEClinicalWorks.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.checkEnabled = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textProgName = new System.Windows.Forms.TextBox();
        this.textProgDesc = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textHL7FolderIn = new System.Windows.Forms.TextBox();
        this.labelHL7FolderIn = new System.Windows.Forms.Label();
        this.textHL7FolderOut = new System.Windows.Forms.TextBox();
        this.labelHL7FolderOut = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.labelDefaultUserGroup = new System.Windows.Forms.Label();
        this.comboDefaultUserGroup = new System.Windows.Forms.ComboBox();
        this.checkShowImages = new System.Windows.Forms.CheckBox();
        this.radioModeTight = new System.Windows.Forms.RadioButton();
        this.radioModeStandalone = new System.Windows.Forms.RadioButton();
        this.checkFeeSchedules = new System.Windows.Forms.CheckBox();
        this.labelHL7Warning = new System.Windows.Forms.Label();
        this.radioModeFull = new System.Windows.Forms.RadioButton();
        this.butDiagnostic = new OpenDental.UI.Button();
        this.textECWServer = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textODServer = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textHL7ServiceName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textHL7Server = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.service11 = new OpenDental.localhost.Service1();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.labelDefEnabledWarning = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textMedPanelURL = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(563, 506);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
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
        this.butOK.Location = new System.Drawing.Point(482, 506);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // checkEnabled
        //
        this.checkEnabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkEnabled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkEnabled.Location = new System.Drawing.Point(124, 60);
        this.checkEnabled.Name = "checkEnabled";
        this.checkEnabled.Size = new System.Drawing.Size(98, 18);
        this.checkEnabled.TabIndex = 41;
        this.checkEnabled.Text = "Enabled";
        this.checkEnabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkEnabled.Click += new System.EventHandler(this.checkEnabled_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(187, 18);
        this.label1.TabIndex = 44;
        this.label1.Text = "Internal Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProgName
        //
        this.textProgName.Location = new System.Drawing.Point(209, 9);
        this.textProgName.Name = "textProgName";
        this.textProgName.ReadOnly = true;
        this.textProgName.Size = new System.Drawing.Size(275, 20);
        this.textProgName.TabIndex = 45;
        //
        // textProgDesc
        //
        this.textProgDesc.Location = new System.Drawing.Point(209, 34);
        this.textProgDesc.Name = "textProgDesc";
        this.textProgDesc.Size = new System.Drawing.Size(275, 20);
        this.textProgDesc.TabIndex = 47;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(20, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(187, 18);
        this.label2.TabIndex = 46;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7FolderIn
        //
        this.textHL7FolderIn.Location = new System.Drawing.Point(197, 43);
        this.textHL7FolderIn.Name = "textHL7FolderIn";
        this.textHL7FolderIn.Size = new System.Drawing.Size(275, 20);
        this.textHL7FolderIn.TabIndex = 49;
        //
        // labelHL7FolderIn
        //
        this.labelHL7FolderIn.Location = new System.Drawing.Point(9, 44);
        this.labelHL7FolderIn.Name = "labelHL7FolderIn";
        this.labelHL7FolderIn.Size = new System.Drawing.Size(186, 18);
        this.labelHL7FolderIn.TabIndex = 48;
        this.labelHL7FolderIn.Text = "In to eClinicalWorks";
        this.labelHL7FolderIn.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7FolderOut
        //
        this.textHL7FolderOut.Location = new System.Drawing.Point(197, 69);
        this.textHL7FolderOut.Name = "textHL7FolderOut";
        this.textHL7FolderOut.Size = new System.Drawing.Size(275, 20);
        this.textHL7FolderOut.TabIndex = 51;
        //
        // labelHL7FolderOut
        //
        this.labelHL7FolderOut.Location = new System.Drawing.Point(9, 70);
        this.labelHL7FolderOut.Name = "labelHL7FolderOut";
        this.labelHL7FolderOut.Size = new System.Drawing.Size(186, 18);
        this.labelHL7FolderOut.TabIndex = 50;
        this.labelHL7FolderOut.Text = "Out from eClinicalWorks";
        this.labelHL7FolderOut.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textHL7FolderOut);
        this.groupBox1.Controls.Add(this.textHL7FolderIn);
        this.groupBox1.Controls.Add(this.labelHL7FolderOut);
        this.groupBox1.Controls.Add(this.labelHL7FolderIn);
        this.groupBox1.Location = new System.Drawing.Point(12, 312);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(538, 101);
        this.groupBox1.TabIndex = 52;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "HL7 Synch Folders";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 19);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(478, 18);
        this.label5.TabIndex = 45;
        this.label5.Text = "Folder locations must be valid on the computer where the HL7 process is running";
        //
        // labelDefaultUserGroup
        //
        this.labelDefaultUserGroup.Location = new System.Drawing.Point(21, 428);
        this.labelDefaultUserGroup.Name = "labelDefaultUserGroup";
        this.labelDefaultUserGroup.Size = new System.Drawing.Size(186, 18);
        this.labelDefaultUserGroup.TabIndex = 53;
        this.labelDefaultUserGroup.Text = "Default User Group for new users";
        this.labelDefaultUserGroup.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboDefaultUserGroup
        //
        this.comboDefaultUserGroup.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDefaultUserGroup.FormattingEnabled = true;
        this.comboDefaultUserGroup.Location = new System.Drawing.Point(209, 428);
        this.comboDefaultUserGroup.Name = "comboDefaultUserGroup";
        this.comboDefaultUserGroup.Size = new System.Drawing.Size(215, 21);
        this.comboDefaultUserGroup.TabIndex = 54;
        //
        // checkShowImages
        //
        this.checkShowImages.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowImages.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowImages.Location = new System.Drawing.Point(23, 455);
        this.checkShowImages.Name = "checkShowImages";
        this.checkShowImages.Size = new System.Drawing.Size(199, 18);
        this.checkShowImages.TabIndex = 55;
        this.checkShowImages.Text = "Show Images Module";
        this.checkShowImages.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowImages.Click += new System.EventHandler(this.checkShowImages_Click);
        //
        // radioModeTight
        //
        this.radioModeTight.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeTight.Location = new System.Drawing.Point(40, 84);
        this.radioModeTight.Name = "radioModeTight";
        this.radioModeTight.Size = new System.Drawing.Size(182, 18);
        this.radioModeTight.TabIndex = 56;
        this.radioModeTight.TabStop = true;
        this.radioModeTight.Text = "Tight Integration";
        this.radioModeTight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeTight.UseVisualStyleBackColor = true;
        this.radioModeTight.Click += new System.EventHandler(this.radioModeTight_Click);
        //
        // radioModeStandalone
        //
        this.radioModeStandalone.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeStandalone.Location = new System.Drawing.Point(40, 104);
        this.radioModeStandalone.Name = "radioModeStandalone";
        this.radioModeStandalone.Size = new System.Drawing.Size(182, 18);
        this.radioModeStandalone.TabIndex = 57;
        this.radioModeStandalone.TabStop = true;
        this.radioModeStandalone.Text = "Standalone";
        this.radioModeStandalone.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeStandalone.UseVisualStyleBackColor = true;
        this.radioModeStandalone.Click += new System.EventHandler(this.radioModeStandalone_Click);
        //
        // checkFeeSchedules
        //
        this.checkFeeSchedules.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkFeeSchedules.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkFeeSchedules.Location = new System.Drawing.Point(23, 476);
        this.checkFeeSchedules.Name = "checkFeeSchedules";
        this.checkFeeSchedules.Size = new System.Drawing.Size(199, 18);
        this.checkFeeSchedules.TabIndex = 58;
        this.checkFeeSchedules.Text = "Patient Fee Schedules Set Manually";
        this.checkFeeSchedules.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelHL7Warning
        //
        this.labelHL7Warning.Location = new System.Drawing.Point(227, 475);
        this.labelHL7Warning.Name = "labelHL7Warning";
        this.labelHL7Warning.Size = new System.Drawing.Size(170, 18);
        this.labelHL7Warning.TabIndex = 59;
        this.labelHL7Warning.Text = "(instead of importing from HL7)";
        this.labelHL7Warning.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // radioModeFull
        //
        this.radioModeFull.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeFull.Location = new System.Drawing.Point(40, 124);
        this.radioModeFull.Name = "radioModeFull";
        this.radioModeFull.Size = new System.Drawing.Size(182, 18);
        this.radioModeFull.TabIndex = 60;
        this.radioModeFull.TabStop = true;
        this.radioModeFull.Text = "Full";
        this.radioModeFull.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioModeFull.UseVisualStyleBackColor = true;
        this.radioModeFull.Click += new System.EventHandler(this.radioModeFull_Click);
        //
        // butDiagnostic
        //
        this.butDiagnostic.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDiagnostic.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDiagnostic.setAutosize(true);
        this.butDiagnostic.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDiagnostic.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDiagnostic.setCornerRadius(4F);
        this.butDiagnostic.Location = new System.Drawing.Point(299, 506);
        this.butDiagnostic.Name = "butDiagnostic";
        this.butDiagnostic.Size = new System.Drawing.Size(90, 24);
        this.butDiagnostic.TabIndex = 61;
        this.butDiagnostic.Text = "Diagnostic Tool";
        this.butDiagnostic.Click += new System.EventHandler(this.butDiagnostic_Click);
        //
        // textECWServer
        //
        this.textECWServer.Location = new System.Drawing.Point(209, 148);
        this.textECWServer.Name = "textECWServer";
        this.textECWServer.Size = new System.Drawing.Size(181, 20);
        this.textECWServer.TabIndex = 53;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(24, 148);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(179, 18);
        this.label3.TabIndex = 52;
        this.label3.Text = "eCW Database Server";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textODServer
        //
        this.textODServer.BackColor = System.Drawing.SystemColors.Window;
        this.textODServer.Location = new System.Drawing.Point(209, 174);
        this.textODServer.Name = "textODServer";
        this.textODServer.ReadOnly = true;
        this.textODServer.Size = new System.Drawing.Size(181, 20);
        this.textODServer.TabIndex = 65;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(24, 174);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(179, 18);
        this.label6.TabIndex = 64;
        this.label6.Text = "OpenDental Database Server";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7ServiceName
        //
        this.textHL7ServiceName.Location = new System.Drawing.Point(209, 226);
        this.textHL7ServiceName.Name = "textHL7ServiceName";
        this.textHL7ServiceName.Size = new System.Drawing.Size(181, 20);
        this.textHL7ServiceName.TabIndex = 69;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(24, 226);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(179, 18);
        this.label4.TabIndex = 68;
        this.label4.Text = "HL7 Service Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7Server
        //
        this.textHL7Server.Location = new System.Drawing.Point(209, 200);
        this.textHL7Server.Name = "textHL7Server";
        this.textHL7Server.Size = new System.Drawing.Size(181, 20);
        this.textHL7Server.TabIndex = 67;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(24, 200);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(179, 18);
        this.label7.TabIndex = 66;
        this.label7.Text = "OpenDental HL7 Server";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // service11
        //
        this.service11.setUrl("http://localhost:3824/Service1.asmx");
        this.service11.setUseDefaultCredentials(true);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(396, 196);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(200, 28);
        this.label8.TabIndex = 70;
        this.label8.Text = "The computer name (not IP) where the HL7 Service is running.";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(396, 226);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(200, 18);
        this.label9.TabIndex = 71;
        this.label9.Text = "Typically OpenDentalHL7.";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelDefEnabledWarning
        //
        this.labelDefEnabledWarning.ForeColor = System.Drawing.Color.Red;
        this.labelDefEnabledWarning.Location = new System.Drawing.Point(40, 277);
        this.labelDefEnabledWarning.Name = "labelDefEnabledWarning";
        this.labelDefEnabledWarning.Size = new System.Drawing.Size(569, 32);
        this.labelDefEnabledWarning.TabIndex = 72;
        this.labelDefEnabledWarning.Text = "An HL7Def is enabled.  The HL7 Server, HL7 Service Name, and HL7 Synch Folders ar" + "e set in that definition.  Message processing is also handled using the specific" + "ations in that definition.";
        this.labelDefEnabledWarning.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelDefEnabledWarning.Visible = false;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(502, 252);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(94, 18);
        this.label10.TabIndex = 75;
        this.label10.Text = "Typically blank.";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textMedPanelURL
        //
        this.textMedPanelURL.Location = new System.Drawing.Point(209, 252);
        this.textMedPanelURL.Name = "textMedPanelURL";
        this.textMedPanelURL.Size = new System.Drawing.Size(287, 20);
        this.textMedPanelURL.TabIndex = 74;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(24, 252);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(179, 18);
        this.label11.TabIndex = 73;
        this.label11.Text = "Medical Panel URL";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormEClinicalWorks
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(650, 542);
        this.Controls.Add(this.textMedPanelURL);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.labelDefEnabledWarning);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textHL7ServiceName);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textHL7Server);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textODServer);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textECWServer);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butDiagnostic);
        this.Controls.Add(this.radioModeFull);
        this.Controls.Add(this.labelHL7Warning);
        this.Controls.Add(this.checkFeeSchedules);
        this.Controls.Add(this.radioModeStandalone);
        this.Controls.Add(this.radioModeTight);
        this.Controls.Add(this.checkShowImages);
        this.Controls.Add(this.comboDefaultUserGroup);
        this.Controls.Add(this.labelDefaultUserGroup);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.textProgDesc);
        this.Controls.Add(this.textProgName);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkEnabled);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label10);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEClinicalWorks";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "eClinicalWorks Setup";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProgramLinkEdit_Closing);
        this.Load += new System.EventHandler(this.FormEClinicalWorks_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEClinicalWorks_Load(Object sender, System.EventArgs e) throws Exception {
        fillForm();
        if (HL7Defs.isExistingHL7Enabled())
        {
            //Instead of using these, we will use the ones that are part of the HL7Def
            //These will be filled with those values.
            textHL7Server.ReadOnly = true;
            textHL7ServiceName.ReadOnly = true;
            textHL7FolderIn.ReadOnly = true;
            textHL7FolderOut.ReadOnly = true;
            labelDefEnabledWarning.Visible = true;
        }
         
    }

    private void fillForm() throws Exception {
        ProgramProperties.refreshCache();
        PropertyList = ProgramProperties.getListForProgram(ProgramCur.ProgramNum);
        textProgName.Text = ProgramCur.ProgName;
        textProgDesc.Text = ProgramCur.ProgDesc;
        checkEnabled.Checked = ProgramCur.Enabled;
        setModeRadioButtons(getProp("eClinicalWorksMode"));
        setModeVisibilities();
        textECWServer.Text = getProp("eCWServer");
        if (HL7Defs.isExistingHL7Enabled())
        {
            HL7Def def = HL7Defs.getOneDeepEnabled();
            textHL7Server.Text = def.HL7Server;
            textHL7ServiceName.Text = def.HL7ServiceName;
            textHL7FolderIn.Text = def.OutgoingFolder;
            //because these are the opposite of the way they are in the HL7Def
            textHL7FolderOut.Text = def.IncomingFolder;
        }
        else
        {
            textHL7Server.Text = getProp("HL7Server");
            textHL7ServiceName.Text = getProp("HL7ServiceName");
            textHL7FolderIn.Text = PrefC.getString(PrefName.HL7FolderIn);
            textHL7FolderOut.Text = PrefC.getString(PrefName.HL7FolderOut);
        } 
        textODServer.Text = MiscData.getODServer();
        comboDefaultUserGroup.Items.Clear();
        for (int i = 0;i < UserGroups.getList().Length;i++)
        {
            comboDefaultUserGroup.Items.Add(UserGroups.getList()[i].Description);
            if (StringSupport.equals(getProp("DefaultUserGroup"), UserGroups.getList()[i].UserGroupNum.ToString()))
            {
                comboDefaultUserGroup.SelectedIndex = i;
            }
             
        }
        checkShowImages.Checked = StringSupport.equals(getProp("ShowImagesModule"), "1");
        checkFeeSchedules.Checked = StringSupport.equals(getProp("FeeSchedulesSetManually"), "1");
        textMedPanelURL.Text = getProp("MedicalPanelUrl");
    }

    private String getProp(String desc) throws Exception {
        for (int i = 0;i < PropertyList.Count;i++)
        {
            if (StringSupport.equals(PropertyList[i].PropertyDesc, desc))
            {
                return PropertyList[i].PropertyValue;
            }
             
        }
        throw new ApplicationException("Property not found: " + desc);
    }

    private void checkEnabled_Click(Object sender, EventArgs e) throws Exception {
        //security already checked when launching this form.  Only admin allowed.
        if (!StringSupport.equals(Security.getCurUser().Password, ""))
        {
            MsgBox.show(this,"Admin password must be blank before this change can be made.");
            checkEnabled.Checked = !checkEnabled.Checked;
            return ;
        }
         
        boolean isHL7Enabled = HL7Defs.isExistingHL7Enabled();
        if (isHL7Enabled && checkEnabled.Checked)
        {
            MsgBox.show(this,"An HL7Def is enabled.  The enabled HL7 definition will control the HL7 messages not this program link.");
            textHL7Server.ReadOnly = true;
            textHL7ServiceName.ReadOnly = true;
            textHL7FolderIn.ReadOnly = true;
            textHL7FolderOut.ReadOnly = true;
            labelDefEnabledWarning.Visible = true;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning!  Read the manual carefully before turning this bridge on or off.  Make sure you understand the difference between the bridging modes and how it will affect patient accounts.  Continue anyway?"))
        {
            checkEnabled.Checked = !checkEnabled.Checked;
            return ;
        }
         
        MsgBox.show(this,"You will need to restart Open Dental to see the effects.");
    }

    private void radioModeTight_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning!  Read the manual carefully before changing this option.  Make sure you understand the difference between the bridging modes and how it will affect patient accounts.  Continue anyway?"))
        {
            //set radio buttons according to what they already are in the db
            setModeRadioButtons(getProp("eClinicalWorksMode"));
            setModeVisibilities();
            return ;
        }
         
        setModeVisibilities();
    }

    private void radioModeStandalone_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning!  Read the manual carefully before changing this option.  Make sure you understand the difference between the bridging modes and how it will affect patient accounts.  Continue anyway?"))
        {
            //set radio buttons according to what they already are in the db
            setModeRadioButtons(getProp("eClinicalWorksMode"));
            setModeVisibilities();
            return ;
        }
         
        setModeVisibilities();
    }

    private void radioModeFull_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning!  Read the manual carefully before changing this option.  Make sure you understand the difference between the bridging modes and how it will affect patient accounts.  Continue anyway?"))
        {
            //set radio buttons according to what they already are in the db
            setModeRadioButtons(getProp("eClinicalWorksMode"));
            setModeVisibilities();
            return ;
        }
         
        setModeVisibilities();
    }

    private void setModeVisibilities() throws Exception {
        if (radioModeTight.Checked || radioModeFull.Checked)
        {
            labelHL7FolderIn.Visible = true;
            textHL7FolderIn.Visible = true;
            labelDefaultUserGroup.Visible = true;
            comboDefaultUserGroup.Visible = true;
            checkShowImages.Visible = true;
            checkFeeSchedules.Visible = true;
            labelHL7Warning.Visible = true;
        }
        else if (radioModeStandalone.Checked)
        {
            labelHL7FolderIn.Visible = false;
            textHL7FolderIn.Visible = false;
            labelDefaultUserGroup.Visible = false;
            comboDefaultUserGroup.Visible = false;
            checkShowImages.Visible = false;
            checkFeeSchedules.Visible = false;
            labelHL7Warning.Visible = false;
        }
          
    }

    /**
    * Pass in the desired eCW mode.  0=Tight,1=Standalone,2=Full.  Defaults to Tight.
    */
    private void setModeRadioButtons(String eClinicalWorksMode) throws Exception {
        System.String __dummyScrutVar0 = eClinicalWorksMode;
        if (__dummyScrutVar0.equals("0"))
        {
            radioModeTight.Checked = true;
        }
        else if (__dummyScrutVar0.equals("1"))
        {
            radioModeStandalone.Checked = true;
        }
        else if (__dummyScrutVar0.equals("2"))
        {
            radioModeFull.Checked = true;
        }
        else
        {
            radioModeTight.Checked = true;
        }   
    }

    private void checkShowImages_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"You will need to restart Open Dental to see the effects.");
    }

    private boolean saveToDb() throws Exception {
        if ((radioModeTight.Checked || radioModeFull.Checked) && comboDefaultUserGroup.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a default user group first.");
            return false;
        }
         
        if (checkEnabled.Checked)
        {
            if (StringSupport.equals(textProgDesc.Text, ""))
            {
                MsgBox.show(this,"Description may not be blank.");
                return false;
            }
             
            if (!HL7Defs.isExistingHL7Enabled())
            {
                if ((radioModeTight.Checked || radioModeFull.Checked) && StringSupport.equals(textHL7FolderIn.Text, ""))
                {
                    MsgBox.show(this,"HL7 in folder may not be blank.");
                    return false;
                }
                 
                if (StringSupport.equals(textHL7FolderOut.Text, ""))
                {
                    MsgBox.show(this,"HL7 out folder may not be blank.");
                    return false;
                }
                 
                if (StringSupport.equals(textHL7Server.Text, ""))
                {
                    MsgBox.show(this,"HL7 Server may not be blank.");
                    return false;
                }
                 
                if (StringSupport.equals(textHL7ServiceName.Text, ""))
                {
                    MsgBox.show(this,"HL7 Service Name may not be blank.");
                    return false;
                }
                 
            }
             
        }
         
        ProgramCur.ProgDesc = textProgDesc.Text;
        ProgramCur.Enabled = checkEnabled.Checked;
        Programs.update(ProgramCur);
        Prefs.UpdateString(PrefName.HL7FolderOut, textHL7FolderOut.Text);
        ProgramProperties.SetProperty(ProgramCur.ProgramNum, "HL7Server", textHL7Server.Text);
        ProgramProperties.SetProperty(ProgramCur.ProgramNum, "HL7ServiceName", textHL7ServiceName.Text);
        ProgramProperties.SetProperty(ProgramCur.ProgramNum, "MedicalPanelUrl", textMedPanelURL.Text);
        if (radioModeTight.Checked || radioModeFull.Checked)
        {
            if (radioModeTight.Checked)
            {
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"eClinicalWorksMode","0");
            }
            else
            {
                //Tight
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"eClinicalWorksMode","2");
            } 
            //Full
            ProgramProperties.SetProperty(ProgramCur.ProgramNum, "eCWServer", textECWServer.Text);
            Prefs.UpdateString(PrefName.HL7FolderIn, textHL7FolderIn.Text);
            ProgramProperties.SetProperty(ProgramCur.ProgramNum, "DefaultUserGroup", UserGroups.getList()[comboDefaultUserGroup.SelectedIndex].UserGroupNum.ToString());
            if (checkShowImages.Checked)
            {
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"ShowImagesModule","1");
            }
            else
            {
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"ShowImagesModule","0");
            } 
            if (this.checkFeeSchedules.Checked)
            {
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"FeeSchedulesSetManually","1");
            }
            else
            {
                ProgramProperties.setProperty(ProgramCur.ProgramNum,"FeeSchedulesSetManually","0");
            } 
        }
        else if (radioModeStandalone.Checked)
        {
            ProgramProperties.setProperty(ProgramCur.ProgramNum,"eClinicalWorksMode","1");
            Prefs.updateString(PrefName.HL7FolderIn,"");
            ProgramProperties.setProperty(ProgramCur.ProgramNum,"DefaultUserGroup","0");
            ProgramProperties.setProperty(ProgramCur.ProgramNum,"ShowImagesModule","1");
            ProgramProperties.setProperty(ProgramCur.ProgramNum,"FeeSchedulesSetManually","0");
        }
          
        DataValid.setInvalid(InvalidType.Programs,InvalidType.Prefs);
        return true;
    }

    private void butDiagnostic_Click(Object sender, EventArgs e) throws Exception {
        //no need to validate all the other fields on the page.
        ProgramProperties.SetProperty(ProgramCur.ProgramNum, "eCWServer", textECWServer.Text);
        DataValid.setInvalid(InvalidType.Programs);
        FormEcwDiag FormECWD = new FormEcwDiag();
        FormECWD.ShowDialog();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveToDb())
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formProgramLinkEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


