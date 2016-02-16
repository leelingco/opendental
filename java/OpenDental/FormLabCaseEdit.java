//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:17 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormSheetFillEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Laboratories;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.LabTurnaround;
import OpenDentBusiness.LabTurnarounds;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patients;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetsInternal;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLabCaseEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textInstructions = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    public LabCase CaseCur;
    private TextBox textAppointment = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butDetach;
    private OpenDental.UI.Button butDetachPlanned;
    private TextBox textPlanned = new TextBox();
    private Label label5 = new Label();
    private Label label6 = new Label();
    private TextBox textDateCreated = new TextBox();
    private TextBox textDateSent = new TextBox();
    private Label label7 = new Label();
    private TextBox textDateRecd = new TextBox();
    private Label label8 = new Label();
    private TextBox textDateChecked = new TextBox();
    private Label label9 = new Label();
    private TextBox textDateDue = new TextBox();
    private Label label10 = new Label();
    private ComboBox comboProv = new ComboBox();
    private Label label11 = new Label();
    private ListBox listLab = new ListBox();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butCheckedNow;
    private OpenDental.UI.Button butRecdNow;
    private OpenDental.UI.Button butSentNow;
    private OpenDental.UI.Button butCreatedNow;
    private ListBox listTurnaround = new ListBox();
    private Label label12 = new Label();
    private List<Laboratory> ListLabs = new List<Laboratory>();
    private TextBox textWeekday = new TextBox();
    private OpenDental.UI.Button butSlip;
    private List<LabTurnaround> turnaroundList = new List<LabTurnaround>();
    /**
    * The lab slip, if one exists.
    */
    private Sheet sheet;
    /**
    * 
    */
    public FormLabCaseEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLabCaseEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.textInstructions = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textAppointment = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPlanned = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textDateCreated = new System.Windows.Forms.TextBox();
        this.textDateSent = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textDateRecd = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textDateChecked = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textDateDue = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label11 = new System.Windows.Forms.Label();
        this.listLab = new System.Windows.Forms.ListBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.listTurnaround = new System.Windows.Forms.ListBox();
        this.label12 = new System.Windows.Forms.Label();
        this.textWeekday = new System.Windows.Forms.TextBox();
        this.butSlip = new OpenDental.UI.Button();
        this.butCheckedNow = new OpenDental.UI.Button();
        this.butRecdNow = new OpenDental.UI.Button();
        this.butSentNow = new OpenDental.UI.Button();
        this.butCreatedNow = new OpenDental.UI.Button();
        this.butDetachPlanned = new OpenDental.UI.Button();
        this.butDetach = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(89, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Patient";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(94, 15);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(319, 20);
        this.textPatient.TabIndex = 0;
        //
        // textInstructions
        //
        this.textInstructions.Location = new System.Drawing.Point(94, 285);
        this.textInstructions.MaxLength = 10000;
        this.textInstructions.Multiline = true;
        this.textInstructions.Name = "textInstructions";
        this.textInstructions.Size = new System.Drawing.Size(400, 127);
        this.textInstructions.TabIndex = 1;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 287);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 17);
        this.label3.TabIndex = 101;
        this.label3.Text = "Instructions";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(91, 116);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(89, 17);
        this.label2.TabIndex = 99;
        this.label2.Text = "Lab";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textAppointment
        //
        this.textAppointment.Location = new System.Drawing.Point(94, 40);
        this.textAppointment.Name = "textAppointment";
        this.textAppointment.ReadOnly = true;
        this.textAppointment.Size = new System.Drawing.Size(319, 20);
        this.textAppointment.TabIndex = 103;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 41);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(89, 17);
        this.label4.TabIndex = 104;
        this.label4.Text = "Appointment";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlanned
        //
        this.textPlanned.Location = new System.Drawing.Point(94, 65);
        this.textPlanned.Name = "textPlanned";
        this.textPlanned.ReadOnly = true;
        this.textPlanned.Size = new System.Drawing.Size(319, 20);
        this.textPlanned.TabIndex = 106;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(5, 66);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(89, 17);
        this.label5.TabIndex = 107;
        this.label5.Text = "Planned Appt";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(17, 20);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(81, 17);
        this.label6.TabIndex = 110;
        this.label6.Text = "Created";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateCreated
        //
        this.textDateCreated.Location = new System.Drawing.Point(100, 19);
        this.textDateCreated.Name = "textDateCreated";
        this.textDateCreated.Size = new System.Drawing.Size(147, 20);
        this.textDateCreated.TabIndex = 111;
        //
        // textDateSent
        //
        this.textDateSent.Location = new System.Drawing.Point(100, 44);
        this.textDateSent.Name = "textDateSent";
        this.textDateSent.Size = new System.Drawing.Size(147, 20);
        this.textDateSent.TabIndex = 113;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(17, 45);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(81, 17);
        this.label7.TabIndex = 112;
        this.label7.Text = "Sent";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateRecd
        //
        this.textDateRecd.Location = new System.Drawing.Point(100, 69);
        this.textDateRecd.Name = "textDateRecd";
        this.textDateRecd.Size = new System.Drawing.Size(147, 20);
        this.textDateRecd.TabIndex = 115;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(17, 70);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(81, 17);
        this.label8.TabIndex = 114;
        this.label8.Text = "Received";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateChecked
        //
        this.textDateChecked.Location = new System.Drawing.Point(100, 94);
        this.textDateChecked.Name = "textDateChecked";
        this.textDateChecked.Size = new System.Drawing.Size(147, 20);
        this.textDateChecked.TabIndex = 117;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(2, 95);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(96, 17);
        this.label9.TabIndex = 116;
        this.label9.Text = "Quality Checked";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateDue
        //
        this.textDateDue.Location = new System.Drawing.Point(336, 261);
        this.textDateDue.Name = "textDateDue";
        this.textDateDue.Size = new System.Drawing.Size(158, 20);
        this.textDateDue.TabIndex = 119;
        this.textDateDue.TextChanged += new System.EventHandler(this.textDateDue_TextChanged);
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(207, 262);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(89, 17);
        this.label10.TabIndex = 118;
        this.label10.Text = "Date Time Due";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.FormattingEnabled = true;
        this.comboProv.Location = new System.Drawing.Point(94, 90);
        this.comboProv.MaxDropDownItems = 25;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(158, 21);
        this.comboProv.TabIndex = 121;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(5, 93);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(89, 17);
        this.label11.TabIndex = 120;
        this.label11.Text = "Provider";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listLab
        //
        this.listLab.FormattingEnabled = true;
        this.listLab.Location = new System.Drawing.Point(94, 136);
        this.listLab.Name = "listLab";
        this.listLab.Size = new System.Drawing.Size(198, 121);
        this.listLab.TabIndex = 0;
        this.listLab.SelectedIndexChanged += new System.EventHandler(this.listLab_SelectedIndexChanged);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butCheckedNow);
        this.groupBox1.Controls.Add(this.butRecdNow);
        this.groupBox1.Controls.Add(this.butSentNow);
        this.groupBox1.Controls.Add(this.butCreatedNow);
        this.groupBox1.Controls.Add(this.textDateCreated);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.textDateSent);
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.textDateRecd);
        this.groupBox1.Controls.Add(this.textDateChecked);
        this.groupBox1.Controls.Add(this.label9);
        this.groupBox1.Location = new System.Drawing.Point(523, 10);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(329, 123);
        this.groupBox1.TabIndex = 122;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Tracking";
        //
        // listTurnaround
        //
        this.listTurnaround.FormattingEnabled = true;
        this.listTurnaround.Location = new System.Drawing.Point(296, 136);
        this.listTurnaround.Name = "listTurnaround";
        this.listTurnaround.Size = new System.Drawing.Size(198, 121);
        this.listTurnaround.TabIndex = 124;
        this.listTurnaround.Click += new System.EventHandler(this.listTurnaround_Click);
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(293, 116);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(89, 17);
        this.label12.TabIndex = 125;
        this.label12.Text = "Set Due Date";
        this.label12.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textWeekday
        //
        this.textWeekday.BackColor = System.Drawing.Color.White;
        this.textWeekday.Location = new System.Drawing.Point(296, 261);
        this.textWeekday.Name = "textWeekday";
        this.textWeekday.ReadOnly = true;
        this.textWeekday.Size = new System.Drawing.Size(40, 20);
        this.textWeekday.TabIndex = 126;
        //
        // butSlip
        //
        this.butSlip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSlip.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSlip.setAutosize(true);
        this.butSlip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSlip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSlip.setCornerRadius(4F);
        this.butSlip.Location = new System.Drawing.Point(433, 443);
        this.butSlip.Name = "butSlip";
        this.butSlip.Size = new System.Drawing.Size(85, 24);
        this.butSlip.TabIndex = 127;
        this.butSlip.Text = "New Slip";
        this.butSlip.Click += new System.EventHandler(this.butSlip_Click);
        //
        // butCheckedNow
        //
        this.butCheckedNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheckedNow.setAutosize(true);
        this.butCheckedNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheckedNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheckedNow.setCornerRadius(4F);
        this.butCheckedNow.Location = new System.Drawing.Point(248, 92);
        this.butCheckedNow.Name = "butCheckedNow";
        this.butCheckedNow.Size = new System.Drawing.Size(75, 24);
        this.butCheckedNow.TabIndex = 121;
        this.butCheckedNow.Text = "Now";
        this.butCheckedNow.Click += new System.EventHandler(this.butCheckedNow_Click);
        //
        // butRecdNow
        //
        this.butRecdNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRecdNow.setAutosize(true);
        this.butRecdNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRecdNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRecdNow.setCornerRadius(4F);
        this.butRecdNow.Location = new System.Drawing.Point(248, 67);
        this.butRecdNow.Name = "butRecdNow";
        this.butRecdNow.Size = new System.Drawing.Size(75, 24);
        this.butRecdNow.TabIndex = 120;
        this.butRecdNow.Text = "Now";
        this.butRecdNow.Click += new System.EventHandler(this.butRecdNow_Click);
        //
        // butSentNow
        //
        this.butSentNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSentNow.setAutosize(true);
        this.butSentNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSentNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSentNow.setCornerRadius(4F);
        this.butSentNow.Location = new System.Drawing.Point(248, 42);
        this.butSentNow.Name = "butSentNow";
        this.butSentNow.Size = new System.Drawing.Size(75, 24);
        this.butSentNow.TabIndex = 119;
        this.butSentNow.Text = "Now";
        this.butSentNow.Click += new System.EventHandler(this.butSentNow_Click);
        //
        // butCreatedNow
        //
        this.butCreatedNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCreatedNow.setAutosize(true);
        this.butCreatedNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCreatedNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCreatedNow.setCornerRadius(4F);
        this.butCreatedNow.Location = new System.Drawing.Point(248, 17);
        this.butCreatedNow.Name = "butCreatedNow";
        this.butCreatedNow.Size = new System.Drawing.Size(75, 24);
        this.butCreatedNow.TabIndex = 118;
        this.butCreatedNow.Text = "Now";
        this.butCreatedNow.Click += new System.EventHandler(this.butCreatedNow_Click);
        //
        // butDetachPlanned
        //
        this.butDetachPlanned.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetachPlanned.setAutosize(true);
        this.butDetachPlanned.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetachPlanned.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetachPlanned.setCornerRadius(4F);
        this.butDetachPlanned.Location = new System.Drawing.Point(419, 62);
        this.butDetachPlanned.Name = "butDetachPlanned";
        this.butDetachPlanned.Size = new System.Drawing.Size(75, 24);
        this.butDetachPlanned.TabIndex = 108;
        this.butDetachPlanned.Text = "Detach";
        this.butDetachPlanned.Click += new System.EventHandler(this.butDetachPlanned_Click);
        //
        // butDetach
        //
        this.butDetach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetach.setAutosize(true);
        this.butDetach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetach.setCornerRadius(4F);
        this.butDetach.Location = new System.Drawing.Point(419, 37);
        this.butDetach.Name = "butDetach";
        this.butDetach.Size = new System.Drawing.Size(75, 24);
        this.butDetach.TabIndex = 105;
        this.butDetach.Text = "Detach";
        this.butDetach.Click += new System.EventHandler(this.butDetach_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(27, 443);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(686, 443);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(777, 443);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormLabCaseEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(878, 487);
        this.Controls.Add(this.butSlip);
        this.Controls.Add(this.textWeekday);
        this.Controls.Add(this.listTurnaround);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.listLab);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textDateDue);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.butDetachPlanned);
        this.Controls.Add(this.textPlanned);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butDetach);
        this.Controls.Add(this.textAppointment);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textInstructions);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLabCaseEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Lab Case";
        this.Load += new System.EventHandler(this.FormLabCaseEdit_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormLabCaseEdit_FormClosing);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLabCaseEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textPatient.Text = Patients.getPat(CaseCur.PatNum).getNameFL();
        ListLabs = Laboratories.refresh();
        for (int i = 0;i < ListLabs.Count;i++)
        {
            listLab.Items.Add(ListLabs[i].Description + " " + ListLabs[i].Phone);
            if (ListLabs[i].LaboratoryNum == CaseCur.LaboratoryNum)
            {
                listLab.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == CaseCur.ProvNum)
            {
                comboProv.SelectedIndex = i;
            }
             
        }
        Appointment apt = Appointments.getOneApt(CaseCur.AptNum);
        if (apt != null)
        {
            if (apt.AptStatus == ApptStatus.UnschedList)
            {
                textAppointment.Text = Lan.g(this,"Unscheduled");
            }
            else
            {
                textAppointment.Text = apt.AptDateTime.ToShortDateString() + " " + apt.AptDateTime.ToShortTimeString();
            } 
            textAppointment.Text += ", " + apt.ProcDescript;
        }
         
        apt = Appointments.getOneApt(CaseCur.PlannedAptNum);
        if (apt != null)
        {
            textPlanned.Text = apt.ProcDescript;
            if (StringSupport.equals(textPlanned.Text, ""))
            {
                textPlanned.Text = Lan.g(this,"Attached");
            }
             
        }
         
        if (CaseCur.DateTimeCreated.Year > 1880)
        {
            textDateCreated.Text = CaseCur.DateTimeCreated.ToString();
        }
         
        if (CaseCur.DateTimeSent.Year > 1880)
        {
            textDateSent.Text = CaseCur.DateTimeSent.ToString();
        }
         
        if (CaseCur.DateTimeRecd.Year > 1880)
        {
            textDateRecd.Text = CaseCur.DateTimeRecd.ToString();
        }
         
        if (CaseCur.DateTimeChecked.Year > 1880)
        {
            textDateChecked.Text = CaseCur.DateTimeChecked.ToString();
        }
         
        if (CaseCur.DateTimeDue.Year > 1880)
        {
            textDateDue.Text = CaseCur.DateTimeDue.ToShortDateString() + " " + CaseCur.DateTimeDue.ToShortTimeString();
        }
         
        textInstructions.Text = CaseCur.Instructions;
        sheet = Sheets.getLabSlip(CaseCur.PatNum,CaseCur.LabCaseNum);
        if (sheet == null)
        {
            butSlip.Text = Lan.g(this,"New Slip");
        }
        else
        {
            butSlip.Text = Lan.g(this,"Edit Slip");
        } 
    }

    private void textDateDue_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            DateTime date = DateTime.Parse(textDateDue.Text);
            textWeekday.Text = date.ToString("ddd");
        }
        catch (Exception __dummyCatchVar0)
        {
            textWeekday.Text = "";
        }
    
    }

    private void butDetach_Click(Object sender, EventArgs e) throws Exception {
        CaseCur.AptNum = 0;
        textAppointment.Text = "";
    }

    private void butDetachPlanned_Click(Object sender, EventArgs e) throws Exception {
        CaseCur.PlannedAptNum = 0;
        textPlanned.Text = "";
    }

    private void listLab_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (listLab.SelectedIndex == -1)
        {
            return ;
        }
         
        turnaroundList = LabTurnarounds.GetForLab(ListLabs[listLab.SelectedIndex].LaboratoryNum);
        listTurnaround.Items.Clear();
        for (int i = 0;i < turnaroundList.Count;i++)
        {
            listTurnaround.Items.Add(turnaroundList[i].Description + ", " + turnaroundList[i].DaysActual.ToString());
        }
    }

    private void listTurnaround_Click(Object sender, EventArgs e) throws Exception {
        if (listTurnaround.SelectedIndex == -1)
        {
            return ;
        }
         
        DateTime duedate = LabTurnarounds.ComputeDueDate(MiscData.getNowDateTime().Date, turnaroundList[listTurnaround.SelectedIndex].DaysActual);
        textDateDue.Text = duedate.ToShortDateString() + " " + duedate.ToShortTimeString();
        listTurnaround.SelectedIndex = -1;
    }

    private void butCreatedNow_Click(Object sender, EventArgs e) throws Exception {
        textDateCreated.Text = MiscData.getNowDateTime().ToString();
    }

    private void butSentNow_Click(Object sender, EventArgs e) throws Exception {
        textDateSent.Text = MiscData.getNowDateTime().ToString();
    }

    private void butRecdNow_Click(Object sender, EventArgs e) throws Exception {
        textDateRecd.Text = MiscData.getNowDateTime().ToString();
    }

    private void butCheckedNow_Click(Object sender, EventArgs e) throws Exception {
        textDateChecked.Text = MiscData.getNowDateTime().ToString();
    }

    /*private void buttonEmail_Click(object sender,EventArgs e) {
    			int CurPatNum=CaseCur.PatNum;
    			EmailMessage message=new EmailMessage();
    			message.PatNum=CurPatNum;
    			Patient pat=Patients.GetPat(CurPatNum);
    			message.ToAddress="";//pat.Email;
    			message.FromAddress=PrefC.GetString(PrefName.EmailSenderAddress");
    			message.Subject=Lan.g(this,"RE: ")+pat.GetNameFL();
    			FormEmailMessageEdit FormE=new FormEmailMessageEdit(message);
    			FormE.IsNew=true;
    			FormE.ShowDialog();
    		}*/
    private void butSlip_Click(Object sender, EventArgs e) throws Exception {
        if (sheet == null)
        {
            //create new
            if (!saveToDb())
            {
                return ;
            }
             
            Laboratory lab = ListLabs[listLab.SelectedIndex];
            SheetDef sheetDef;
            if (lab.Slip == 0)
            {
                sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabSlip);
            }
            else
            {
                sheetDef = SheetDefs.getSheetDef(lab.Slip);
            } 
            sheet = SheetUtil.createSheet(sheetDef,CaseCur.PatNum);
            SheetParameter.setParameter(sheet,"PatNum",CaseCur.PatNum);
            SheetParameter.setParameter(sheet,"LabCaseNum",CaseCur.LabCaseNum);
            SheetFiller.fillFields(sheet);
            SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
            FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
            FormS.ShowDialog();
        }
        else
        {
            //edit existing
            SheetFields.getFieldsAndParameters(sheet);
            FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
            FormS.ShowDialog();
        } 
        //refresh
        sheet = Sheets.getLabSlip(CaseCur.PatNum,CaseCur.LabCaseNum);
        if (sheet == null)
        {
            butSlip.Text = Lan.g(this,"New Slip");
        }
        else
        {
            butSlip.Text = Lan.g(this,"Edit Slip");
            butCancel.Enabled = false;
        } 
    }

    //user can still click X to close window, but we do handle that as well.
    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        //whether new or not
        if (!MsgBox.show(this,true,"Delete Lab Case?"))
        {
            return ;
        }
         
        try
        {
            LabCases.delete(CaseCur.LabCaseNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Returns false if not able to save.
    */
    private boolean saveToDb() throws Exception {
        if (listLab.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a lab first.");
            return false;
        }
         
        if (comboProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a provider first.");
            return false;
        }
         
        if (!StringSupport.equals(textDateCreated.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateCreated.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"Date Time Created is invalid.");
                return false;
            }
        
        }
         
        if (!StringSupport.equals(textDateSent.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateSent.Text);
            }
            catch (Exception __dummyCatchVar2)
            {
                MsgBox.show(this,"Date Time Sent is invalid.");
                return false;
            }
        
        }
         
        if (!StringSupport.equals(textDateRecd.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateRecd.Text);
            }
            catch (Exception __dummyCatchVar3)
            {
                MsgBox.show(this,"Date Time Received is invalid.");
                return false;
            }
        
        }
         
        if (!StringSupport.equals(textDateChecked.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateChecked.Text);
            }
            catch (Exception __dummyCatchVar4)
            {
                MsgBox.show(this,"Date Time Checked is invalid.");
                return false;
            }
        
        }
         
        if (!StringSupport.equals(textDateDue.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateDue.Text);
            }
            catch (Exception __dummyCatchVar5)
            {
                MsgBox.show(this,"Date Time Due is invalid.");
                return false;
            }
        
        }
         
        CaseCur.LaboratoryNum = ListLabs[listLab.SelectedIndex].LaboratoryNum;
        //AptNum
        //PlannedAptNum
        CaseCur.ProvNum = ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum;
        if (StringSupport.equals(textDateCreated.Text, ""))
        {
            CaseCur.DateTimeCreated = DateTime.MinValue;
        }
        else
        {
            CaseCur.DateTimeCreated = DateTime.Parse(textDateCreated.Text);
        } 
        if (StringSupport.equals(textDateSent.Text, ""))
        {
            CaseCur.DateTimeSent = DateTime.MinValue;
        }
        else
        {
            CaseCur.DateTimeSent = DateTime.Parse(textDateSent.Text);
        } 
        if (StringSupport.equals(textDateRecd.Text, ""))
        {
            CaseCur.DateTimeRecd = DateTime.MinValue;
        }
        else
        {
            CaseCur.DateTimeRecd = DateTime.Parse(textDateRecd.Text);
        } 
        if (StringSupport.equals(textDateChecked.Text, ""))
        {
            CaseCur.DateTimeChecked = DateTime.MinValue;
        }
        else
        {
            CaseCur.DateTimeChecked = DateTime.Parse(textDateChecked.Text);
        } 
        if (StringSupport.equals(textDateDue.Text, ""))
        {
            CaseCur.DateTimeDue = DateTime.MinValue;
        }
        else
        {
            CaseCur.DateTimeDue = DateTime.Parse(textDateDue.Text);
        } 
        CaseCur.Instructions = textInstructions.Text;
        try
        {
            //if(IsNew){//No.  Always created ahead of time
            LabCases.update(CaseCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }

        return true;
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

    private void formLabCaseEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            if (sheet == null)
            {
                LabCases.delete(CaseCur.LabCaseNum);
            }
            else
            {
            } 
        }
         
    }

}


//user created and possibly printed a lab slip.  We can't let them delete this lab case
//lab cases are always created ahead of time, so no need to save here