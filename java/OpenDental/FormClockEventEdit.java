//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:53 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormClockEventEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TimeClockStatus;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClockEventEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textTimeEntered1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listStatus = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textTimeDisplayed1 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private GroupBox groupBox1 = new GroupBox();
    private GroupBox groupBox2 = new GroupBox();
    private TextBox textTimeEntered2 = new TextBox();
    private Label label5 = new Label();
    private Label label6 = new Label();
    private TextBox textTimeDisplayed2 = new TextBox();
    private OpenDental.UI.Button butNow2;
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.Button butNow1;
    private TextBox textOTimeHours = new TextBox();
    private Label label7 = new Label();
    private TextBox textClockedTime = new TextBox();
    private Label label8 = new Label();
    private TextBox textRegTime = new TextBox();
    private Label label9 = new Label();
    private GroupBox groupTimeSpans = new GroupBox();
    private TextBox textOTimeAuto = new TextBox();
    private TextBox textAdjust = new TextBox();
    private Label label12 = new Label();
    private Label label11 = new Label();
    private Label label10 = new Label();
    private TextBox textAdjustAuto = new TextBox();
    private TextBox textRate2Auto = new TextBox();
    private Label label13 = new Label();
    private TextBox textRate2Hours = new TextBox();
    private Label label14 = new Label();
    private TextBox textTotalHours = new TextBox();
    private TextBox textRate1Auto = new TextBox();
    private Label label15 = new Label();
    private Label label17 = new Label();
    private GroupBox groupRate2 = new GroupBox();
    private Label label18 = new Label();
    private ClockEvent ClockEventCur;
    /**
    * 
    */
    public FormClockEventEdit(ClockEvent clockEventCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        ClockEventCur = clockEventCur.copy();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClockEventEdit.class);
        this.textTimeEntered1 = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textTimeDisplayed1 = new System.Windows.Forms.TextBox();
        this.listStatus = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butNow1 = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textTimeEntered2 = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.butClear = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.butNow2 = new OpenDental.UI.Button();
        this.textTimeDisplayed2 = new System.Windows.Forms.TextBox();
        this.textOTimeHours = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textClockedTime = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textRegTime = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.groupTimeSpans = new System.Windows.Forms.GroupBox();
        this.textOTimeAuto = new System.Windows.Forms.TextBox();
        this.textAdjust = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textAdjustAuto = new System.Windows.Forms.TextBox();
        this.textRate2Auto = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textRate2Hours = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label14 = new System.Windows.Forms.Label();
        this.textTotalHours = new System.Windows.Forms.TextBox();
        this.textRate1Auto = new System.Windows.Forms.TextBox();
        this.label15 = new System.Windows.Forms.Label();
        this.label17 = new System.Windows.Forms.Label();
        this.groupRate2 = new System.Windows.Forms.GroupBox();
        this.label18 = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupTimeSpans.SuspendLayout();
        this.groupRate2.SuspendLayout();
        this.SuspendLayout();
        //
        // textTimeEntered1
        //
        this.textTimeEntered1.Location = new System.Drawing.Point(101, 19);
        this.textTimeEntered1.Name = "textTimeEntered1";
        this.textTimeEntered1.ReadOnly = true;
        this.textTimeEntered1.Size = new System.Drawing.Size(156, 20);
        this.textTimeEntered1.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(94, 16);
        this.label1.TabIndex = 3;
        this.label1.Text = "Entered";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 45);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(93, 16);
        this.label2.TabIndex = 5;
        this.label2.Text = "Displayed";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTimeDisplayed1
        //
        this.textTimeDisplayed1.Location = new System.Drawing.Point(101, 43);
        this.textTimeDisplayed1.Name = "textTimeDisplayed1";
        this.textTimeDisplayed1.Size = new System.Drawing.Size(156, 20);
        this.textTimeDisplayed1.TabIndex = 4;
        this.textTimeDisplayed1.TextChanged += new System.EventHandler(this.textTimeDisplayed1_TextChanged);
        //
        // listStatus
        //
        this.listStatus.Location = new System.Drawing.Point(179, 118);
        this.listStatus.Name = "listStatus";
        this.listStatus.Size = new System.Drawing.Size(120, 43);
        this.listStatus.TabIndex = 8;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(72, 118);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(105, 16);
        this.label3.TabIndex = 9;
        this.label3.Text = "Out Status";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(72, 309);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(105, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "Note";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(179, 308);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(317, 110);
        this.textNote.TabIndex = 11;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textTimeEntered1);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butNow1);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.textTimeDisplayed1);
        this.groupBox1.Location = new System.Drawing.Point(79, 12);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(267, 100);
        this.groupBox1.TabIndex = 13;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Clock In Date and Time";
        //
        // butNow1
        //
        this.butNow1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow1.setAutosize(true);
        this.butNow1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow1.setCornerRadius(4F);
        this.butNow1.Location = new System.Drawing.Point(101, 69);
        this.butNow1.Name = "butNow1";
        this.butNow1.Size = new System.Drawing.Size(70, 24);
        this.butNow1.TabIndex = 17;
        this.butNow1.Text = "Now";
        this.butNow1.Click += new System.EventHandler(this.butNow1_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textTimeEntered2);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.butClear);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.butNow2);
        this.groupBox2.Controls.Add(this.textTimeDisplayed2);
        this.groupBox2.Location = new System.Drawing.Point(363, 12);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(267, 100);
        this.groupBox2.TabIndex = 14;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Clock Out Date and Time";
        //
        // textTimeEntered2
        //
        this.textTimeEntered2.Location = new System.Drawing.Point(101, 19);
        this.textTimeEntered2.Name = "textTimeEntered2";
        this.textTimeEntered2.ReadOnly = true;
        this.textTimeEntered2.Size = new System.Drawing.Size(156, 20);
        this.textTimeEntered2.TabIndex = 2;
        this.textTimeEntered2.TextChanged += new System.EventHandler(this.textTimeEntered2_TextChanged);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 21);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(94, 16);
        this.label5.TabIndex = 3;
        this.label5.Text = "Entered";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Image = Resources.getdeleteX();
        this.butClear.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClear.Location = new System.Drawing.Point(177, 69);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(80, 24);
        this.butClear.TabIndex = 16;
        this.butClear.Text = "Clear";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(6, 45);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(93, 16);
        this.label6.TabIndex = 5;
        this.label6.Text = "Displayed";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butNow2
        //
        this.butNow2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow2.setAutosize(true);
        this.butNow2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow2.setCornerRadius(4F);
        this.butNow2.Location = new System.Drawing.Point(101, 69);
        this.butNow2.Name = "butNow2";
        this.butNow2.Size = new System.Drawing.Size(70, 24);
        this.butNow2.TabIndex = 15;
        this.butNow2.Text = "Now";
        this.butNow2.Click += new System.EventHandler(this.butNow2_Click);
        //
        // textTimeDisplayed2
        //
        this.textTimeDisplayed2.Location = new System.Drawing.Point(101, 43);
        this.textTimeDisplayed2.Name = "textTimeDisplayed2";
        this.textTimeDisplayed2.Size = new System.Drawing.Size(156, 20);
        this.textTimeDisplayed2.TabIndex = 4;
        this.textTimeDisplayed2.TextChanged += new System.EventHandler(this.textTimeDisplayed2_TextChanged);
        //
        // textOTimeHours
        //
        this.textOTimeHours.Location = new System.Drawing.Point(176, 79);
        this.textOTimeHours.Name = "textOTimeHours";
        this.textOTimeHours.Size = new System.Drawing.Size(68, 20);
        this.textOTimeHours.TabIndex = 7;
        this.textOTimeHours.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textOTimeHours.TextChanged += new System.EventHandler(this.textOvertime_TextChanged);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(8, 79);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(92, 18);
        this.label7.TabIndex = 10;
        this.label7.Text = "- Overtime";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textClockedTime
        //
        this.textClockedTime.Location = new System.Drawing.Point(100, 34);
        this.textClockedTime.Name = "textClockedTime";
        this.textClockedTime.ReadOnly = true;
        this.textClockedTime.Size = new System.Drawing.Size(68, 20);
        this.textClockedTime.TabIndex = 1;
        this.textClockedTime.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(8, 34);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(92, 18);
        this.label8.TabIndex = 8;
        this.label8.Text = "Clocked Time";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRegTime
        //
        this.textRegTime.Location = new System.Drawing.Point(100, 101);
        this.textRegTime.Name = "textRegTime";
        this.textRegTime.ReadOnly = true;
        this.textRegTime.Size = new System.Drawing.Size(68, 20);
        this.textRegTime.TabIndex = 4;
        this.textRegTime.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(8, 101);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(92, 18);
        this.label9.TabIndex = 11;
        this.label9.Text = "Regular Time";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupTimeSpans
        //
        this.groupTimeSpans.Controls.Add(this.textOTimeAuto);
        this.groupTimeSpans.Controls.Add(this.textAdjust);
        this.groupTimeSpans.Controls.Add(this.label12);
        this.groupTimeSpans.Controls.Add(this.label11);
        this.groupTimeSpans.Controls.Add(this.label10);
        this.groupTimeSpans.Controls.Add(this.textAdjustAuto);
        this.groupTimeSpans.Controls.Add(this.label8);
        this.groupTimeSpans.Controls.Add(this.textRegTime);
        this.groupTimeSpans.Controls.Add(this.label7);
        this.groupTimeSpans.Controls.Add(this.label9);
        this.groupTimeSpans.Controls.Add(this.textOTimeHours);
        this.groupTimeSpans.Controls.Add(this.textClockedTime);
        this.groupTimeSpans.Location = new System.Drawing.Point(79, 164);
        this.groupTimeSpans.Name = "groupTimeSpans";
        this.groupTimeSpans.Size = new System.Drawing.Size(267, 134);
        this.groupTimeSpans.TabIndex = 30;
        this.groupTimeSpans.TabStop = false;
        this.groupTimeSpans.Text = "Time Spans";
        //
        // textOTimeAuto
        //
        this.textOTimeAuto.Location = new System.Drawing.Point(100, 79);
        this.textOTimeAuto.Name = "textOTimeAuto";
        this.textOTimeAuto.ReadOnly = true;
        this.textOTimeAuto.Size = new System.Drawing.Size(68, 20);
        this.textOTimeAuto.TabIndex = 3;
        this.textOTimeAuto.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textAdjust
        //
        this.textAdjust.Location = new System.Drawing.Point(176, 56);
        this.textAdjust.Name = "textAdjust";
        this.textAdjust.Size = new System.Drawing.Size(68, 20);
        this.textAdjust.TabIndex = 6;
        this.textAdjust.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textAdjust.TextChanged += new System.EventHandler(this.textAdjust_TextChanged);
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(176, 13);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(68, 18);
        this.label12.TabIndex = 5;
        this.label12.Text = "Override";
        this.label12.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(100, 13);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(68, 18);
        this.label11.TabIndex = 0;
        this.label11.Text = "Calculated";
        this.label11.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(8, 56);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(92, 18);
        this.label10.TabIndex = 9;
        this.label10.Text = "+ Adj";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAdjustAuto
        //
        this.textAdjustAuto.Location = new System.Drawing.Point(100, 56);
        this.textAdjustAuto.Name = "textAdjustAuto";
        this.textAdjustAuto.ReadOnly = true;
        this.textAdjustAuto.Size = new System.Drawing.Size(68, 20);
        this.textAdjustAuto.TabIndex = 2;
        this.textAdjustAuto.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textRate2Auto
        //
        this.textRate2Auto.Location = new System.Drawing.Point(100, 57);
        this.textRate2Auto.Name = "textRate2Auto";
        this.textRate2Auto.ReadOnly = true;
        this.textRate2Auto.Size = new System.Drawing.Size(68, 20);
        this.textRate2Auto.TabIndex = 31;
        this.textRate2Auto.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(8, 57);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(92, 18);
        this.label13.TabIndex = 33;
        this.label13.Text = "- Rate 2 Time";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRate2Hours
        //
        this.textRate2Hours.Location = new System.Drawing.Point(176, 57);
        this.textRate2Hours.Name = "textRate2Hours";
        this.textRate2Hours.Size = new System.Drawing.Size(68, 20);
        this.textRate2Hours.TabIndex = 32;
        this.textRate2Hours.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textRate2Hours.TextChanged += new System.EventHandler(this.textRate2Hours_TextChanged);
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
        this.butDelete.Location = new System.Drawing.Point(31, 443);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 12;
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
        this.butOK.Location = new System.Drawing.Point(464, 442);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(555, 442);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(8, 24);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(92, 36);
        this.label14.TabIndex = 35;
        this.label14.Text = "Total Time\r\n(clock+adj)";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTotalHours
        //
        this.textTotalHours.Location = new System.Drawing.Point(100, 34);
        this.textTotalHours.Name = "textTotalHours";
        this.textTotalHours.ReadOnly = true;
        this.textTotalHours.Size = new System.Drawing.Size(68, 20);
        this.textTotalHours.TabIndex = 34;
        this.textTotalHours.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textRate1Auto
        //
        this.textRate1Auto.Location = new System.Drawing.Point(100, 80);
        this.textRate1Auto.Name = "textRate1Auto";
        this.textRate1Auto.ReadOnly = true;
        this.textRate1Auto.Size = new System.Drawing.Size(68, 20);
        this.textRate1Auto.TabIndex = 12;
        this.textRate1Auto.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(8, 80);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(92, 18);
        this.label15.TabIndex = 13;
        this.label15.Text = "Rate 1 Time";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(173, 13);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(68, 18);
        this.label17.TabIndex = 37;
        this.label17.Text = "Override";
        this.label17.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // groupRate2
        //
        this.groupRate2.Controls.Add(this.label18);
        this.groupRate2.Controls.Add(this.label17);
        this.groupRate2.Controls.Add(this.textRate2Hours);
        this.groupRate2.Controls.Add(this.label13);
        this.groupRate2.Controls.Add(this.textRate1Auto);
        this.groupRate2.Controls.Add(this.textRate2Auto);
        this.groupRate2.Controls.Add(this.label15);
        this.groupRate2.Controls.Add(this.textTotalHours);
        this.groupRate2.Controls.Add(this.label14);
        this.groupRate2.Location = new System.Drawing.Point(352, 164);
        this.groupRate2.Name = "groupRate2";
        this.groupRate2.Size = new System.Drawing.Size(267, 134);
        this.groupRate2.TabIndex = 31;
        this.groupRate2.TabStop = false;
        this.groupRate2.Text = "Rate 2";
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(100, 13);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(68, 18);
        this.label18.TabIndex = 38;
        this.label18.Text = "Calculated";
        this.label18.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // FormClockEventEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(669, 485);
        this.Controls.Add(this.groupRate2);
        this.Controls.Add(this.groupTimeSpans);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listStatus);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClockEventEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Clock Event";
        this.Load += new System.EventHandler(this.FormClockEventEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupTimeSpans.ResumeLayout(false);
        this.groupTimeSpans.PerformLayout();
        this.groupRate2.ResumeLayout(false);
        this.groupRate2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClockEventEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.TimecardDeleteEntry,ClockEventCur.TimeEntered1,true))
        {
            butDelete.Enabled = false;
            butClear.Enabled = false;
            butNow1.Enabled = false;
            butNow2.Enabled = false;
        }
         
        if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
        {
            groupBox1.Text = Lan.g(this,"Clock Out Date and Time");
            groupBox2.Text = Lan.g(this,"Clock In Date and Time");
            groupTimeSpans.Visible = false;
            groupRate2.Visible = false;
        }
         
        //Set Text Fields----------------
        fillInitialControlsHelper();
    }

    /**
    * Fills all controls based on the values of ClockEventCur, which is a copy of the object from the DB.
    */
    private void fillInitialControlsHelper() throws Exception {
        //Clock In/Out fields---------------------------------------------------------------------
        textTimeEntered1.Text = ClockEventCur.TimeEntered1.ToString();
        textTimeDisplayed1.Text = ClockEventCur.TimeDisplayed1.ToString();
        if (ClockEventCur.TimeEntered2.Year > 1880)
        {
            textTimeEntered2.Text = ClockEventCur.TimeEntered2.ToString();
        }
         
        if (ClockEventCur.TimeDisplayed2.Year > 1880)
        {
            textTimeDisplayed2.Text = ClockEventCur.TimeDisplayed2.ToString();
        }
         
        //Clock status (i.e. Home, Lunch, Break)--------------------------------------------------
        listStatus.Items.Clear();
        for (int i = 0;i < Enum.GetNames(TimeClockStatus.class).Length;i++)
        {
            listStatus.Items.Add(Lan.g("enumTimeClockStatus", Enum.GetNames(TimeClockStatus.class)[i]));
        }
        listStatus.SelectedIndex = ((Enum)ClockEventCur.ClockStatus).ordinal();
        //all clockevents have a status
        //Time Spans -----------------------------------------------------------------------------
        //Clocked time------------------------------
        TimeSpan clockedTime = TimeSpan.Zero;
        if (ClockEventCur.TimeDisplayed2.Year > 1880)
        {
            clockedTime = ClockEventCur.TimeDisplayed2 - ClockEventCur.TimeDisplayed1;
            textClockedTime.Text = ClockEvents.format(clockedTime);
        }
         
        //Adj ------------------------------------
        textAdjustAuto.Text = ClockEvents.format(ClockEventCur.AdjustAuto);
        if (ClockEventCur.AdjustIsOverridden)
        {
            if (ClockEventCur.Adjust == TimeSpan.Zero)
            {
                textAdjust.Text = "0";
            }
            else
            {
                textAdjust.Text = ClockEvents.format(ClockEventCur.Adjust);
            } 
        }
        else
        {
            textAdjust.Text = "";
        } 
        //Overtime --------------------------------
        textOTimeAuto.Text = ClockEvents.format(ClockEventCur.OTimeAuto);
        if (ClockEventCur.OTimeHours == TimeSpan.FromHours(-1))
        {
            //no override
            textOTimeHours.Text = "";
        }
        else
        {
            textOTimeHours.Text = ClockEvents.format(ClockEventCur.OTimeHours);
        } 
        //Regular Time -----------------------------
        if (clockedTime > TimeSpan.Zero)
        {
            TimeSpan regularTime = clockedTime + (ClockEventCur.AdjustIsOverridden ? ClockEventCur.Adjust : ClockEventCur.AdjustAuto) - (ClockEventCur.OTimeHours == TimeSpan.FromHours(-1) ? ClockEventCur.OTimeAuto : ClockEventCur.OTimeHours);
            textRegTime.Text = ClockEvents.format(regularTime);
        }
         
        //Rate 2 spans -----------------------------------------------------------------------------
        if (clockedTime > TimeSpan.Zero)
        {
            TimeSpan totalTime = clockedTime + (ClockEventCur.AdjustIsOverridden ? ClockEventCur.Adjust : ClockEventCur.AdjustAuto);
            //clockedTime+(Adj or AdjAuto)
            TimeSpan rate1Hours = totalTime - (ClockEventCur.Rate2Hours == TimeSpan.FromHours(-1) ? ClockEventCur.Rate2Auto : ClockEventCur.Rate2Hours);
            //totalTime-(Rate2 or Rate2Auto)
            textTotalHours.Text = ClockEvents.format(totalTime);
            textRate1Auto.Text = ClockEvents.format(rate1Hours);
        }
         
        //Rate 2 Time -----------------------------
        textRate2Auto.Text = ClockEvents.format(ClockEventCur.Rate2Auto);
        if (ClockEventCur.Rate2Hours == TimeSpan.FromHours(-1))
        {
            textRate2Hours.Text = "";
        }
        else
        {
            textRate2Hours.Text = ClockEvents.format(ClockEventCur.Rate2Hours);
        } 
        //notes ------------------------------------------------------------------------------------
        textNote.Text = ClockEventCur.Note;
    }

    /**
    * Fills all controls based on the values of ClockEventCur, which is a copy of the object from the DB.
    */
    private void fillAutoControlsHelper() throws Exception {
        //Clock In/Out fields---------------------------------------------------------------------
        textTimeEntered1.Text = ClockEventCur.TimeEntered1.ToString();
        if (ClockEventCur.TimeEntered2.Year > 1880)
        {
            textTimeEntered2.Text = ClockEventCur.TimeEntered2.ToString();
        }
         
        //Clocked time------------------------------
        TimeSpan clockedTime = TimeSpan.Zero;
        if (ClockEventCur.TimeDisplayed2.Year > 1880)
        {
            clockedTime = ClockEventCur.TimeDisplayed2 - ClockEventCur.TimeDisplayed1;
            textClockedTime.Text = ClockEvents.format(clockedTime);
        }
         
        //Adj ------------------------------------
        textAdjustAuto.Text = ClockEvents.format(ClockEventCur.AdjustAuto);
        //Overtime --------------------------------
        textOTimeAuto.Text = ClockEvents.format(ClockEventCur.OTimeAuto);
        //Regular Time -----------------------------
        if (clockedTime > TimeSpan.Zero)
        {
            TimeSpan regularTime = clockedTime + (ClockEventCur.AdjustIsOverridden ? ClockEventCur.Adjust : ClockEventCur.AdjustAuto) - (ClockEventCur.OTimeHours == TimeSpan.FromHours(-1) ? ClockEventCur.OTimeAuto : ClockEventCur.OTimeHours);
            textRegTime.Text = ClockEvents.format(regularTime);
        }
         
        //Rate 2 spans -----------------------------------------------------------------------------
        if (clockedTime > TimeSpan.Zero)
        {
            TimeSpan totalTime = clockedTime + (ClockEventCur.AdjustIsOverridden ? ClockEventCur.Adjust : ClockEventCur.AdjustAuto);
            //clockedTime+(Adj or AdjAuto)
            TimeSpan rate1Hours = totalTime - (ClockEventCur.Rate2Hours == TimeSpan.FromHours(-1) ? ClockEventCur.Rate2Auto : ClockEventCur.Rate2Hours);
            //totalTime-(Rate2 or Rate2Auto)
            textTotalHours.Text = ClockEvents.format(totalTime);
            textRate1Auto.Text = ClockEvents.format(rate1Hours);
        }
         
        //Rate 2 Time -----------------------------
        textRate2Auto.Text = ClockEvents.format(ClockEventCur.Rate2Auto);
        //notes ------------------------------------------------------------------------------------
        textNote.Text = ClockEventCur.Note;
    }

    /**
    * //Does not alter the overrides, but only the auto calc boxes.  Triggered by many things on this form.  It's better to have it be triggered too frequently than to miss something.
    */
    //private void FillTimeSpans() {
    //	if(ClockEventCur.ClockStatus==TimeClockStatus.Break){//TimeSpans not showing
    //		return;
    //	}
    //	if(textTimeEntered2.Text=="" || textTimeDisplayed2.Text=="") {
    //		textClockedTime.Text="";
    //		textRegTime.Text="";
    //		return;
    //	}
    //	try{
    //		DateTime.Parse(textTimeDisplayed1.Text);//because this must always be valid
    //		DateTime.Parse(textTimeDisplayed2.Text);//this must also be filled in order to calculate timespans
    //	}
    //	catch{//an invalid date/time.
    //		//textTotalHours.Text="";
    //		//textRegTime.Text="";
    //		return;
    //	}
    //	DateTime dt1=DateTime.Parse(textTimeDisplayed1.Text);
    //	DateTime dt2=DateTime.Parse(textTimeDisplayed2.Text);
    //	if(dt1 > dt2){
    //		return;
    //	}
    //	TimeSpan clockedTime=dt2-dt1;
    //	textClockedTime.Text=ClockEvents.Format(clockedTime);
    //	TimeSpan overtime=ClockEventCur.OTimeAuto;
    //	if(textOTimeHours.Text!="") {
    //		try {
    //			if(textOTimeHours.Text.Contains(":")) {
    //				overtime=TimeSpan.Parse(textOTimeHours.Text);
    //			}
    //			else {
    //				overtime=TimeSpan.FromHours(Double.Parse(textOTimeHours.Text));
    //			}
    //		}
    //		catch {
    //			return;
    //		}
    //	}
    //	TimeSpan adjust=ClockEventCur.AdjustAuto;
    //	if(textAdjust.Text!="") {
    //		try {
    //			if(textAdjust.Text.Contains(":")) {
    //				adjust=TimeSpan.Parse(textAdjust.Text);
    //			}
    //			else {
    //				adjust=TimeSpan.FromHours(Double.Parse(textAdjust.Text));
    //			}
    //		}
    //		catch {
    //			return;
    //		}
    //	}
    //	TimeSpan regTime=clockedTime-overtime+adjust;//adjust is typically a negative value
    //	if(regTime<TimeSpan.Zero) {
    //		textRegTime.Text="";
    //		return;
    //	}
    //	textRegTime.Text=ClockEvents.Format(regTime);
    //	//if(ClockEventCur.AmountBonusAuto==-1) {
    //	//  textAmountBonusAuto.Text="";
    //	//}
    //	//else {
    //	//  textAmountBonusAuto.Text=ClockEventCur.AmountBonusAuto.ToString("f");
    //	//}
    //	//if(ClockEventCur.AmountBonus==-1) {
    //	//  textAmountBonus.Text="";
    //	//}
    //	//else {
    //	//  textAmountBonus.Text=ClockEventCur.AmountBonus.ToString("f");
    //	//}
    //}
    private void textTimeDisplayed2_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            ClockEventCur.TimeDisplayed2 = DateTime.Parse(textTimeDisplayed2.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            clearAutoFieldsHelper();
            return ;
        }

        fillAutoControlsHelper();
    }

    private void textTimeDisplayed1_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            ClockEventCur.TimeDisplayed1 = DateTime.Parse(textTimeDisplayed1.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            clearAutoFieldsHelper();
            return ;
        }

        fillAutoControlsHelper();
    }

    private void textTimeEntered2_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textTimeEntered2.Text, ""))
            {
                ClockEventCur.TimeEntered2 = DateTime.MinValue;
            }
            else
            {
                ClockEventCur.TimeEntered2 = PIn.Date(textTimeEntered2.Text);
            } 
        }
        catch (Exception __dummyCatchVar2)
        {
            return ;
        }

        fillAutoControlsHelper();
    }

    private void textAdjust_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textAdjust.Text, ""))
            {
                ClockEventCur.AdjustIsOverridden = false;
                ClockEventCur.Adjust = TimeSpan.Zero;
            }
            else
            {
                ClockEventCur.AdjustIsOverridden = true;
                ClockEventCur.Adjust = TimeSpan.FromHours(Double.Parse(textAdjust.Text));
            } 
        }
        catch (Exception __dummyCatchVar3)
        {
            return ;
        }

        fillAutoControlsHelper();
    }

    private void textOvertime_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textOTimeHours.Text, ""))
            {
                ClockEventCur.OTimeHours = TimeSpan.FromHours(-1);
            }
            else
            {
                //ClockEventCur.OTimeHours=PIn.Time(textOTimeHours.Text);
                ClockEventCur.OTimeHours = TimeSpan.FromHours(Double.Parse(textOTimeHours.Text));
            } 
        }
        catch (Exception __dummyCatchVar4)
        {
            return ;
        }

        fillAutoControlsHelper();
    }

    private void textRate2Hours_TextChanged(Object sender, EventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textRate2Hours.Text, ""))
            {
                ClockEventCur.Rate2Hours = TimeSpan.Zero;
            }
            else
            {
                //ClockEventCur.Rate2Hours=PIn.Time(textRate2Hours.Text);
                ClockEventCur.Rate2Hours = TimeSpan.FromHours(Double.Parse(textRate2Hours.Text));
            } 
        }
        catch (Exception __dummyCatchVar5)
        {
            return ;
        }

        fillAutoControlsHelper();
    }

    private void butNow1_Click(Object sender, EventArgs e) throws Exception {
        textTimeDisplayed1.Text = DateTime.Now.ToString();
    }

    private void butNow2_Click(Object sender, EventArgs e) throws Exception {
        textTimeDisplayed2.Text = DateTime.Now.ToString();
        if (StringSupport.equals(textTimeEntered2.Text, ""))
        {
            //only set the time entered if it's blank
            textTimeEntered2.Text = MiscData.getNowDateTime().ToString();
            ClockEventCur.TimeEntered2 = MiscData.getNowDateTime();
        }
         
        //FillTimeSpans();//not really needed because of the TextChanged event, but might prevent a bug.
        fillAutoControlsHelper();
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        textTimeDisplayed2.Text = "";
        textTimeEntered2.Text = "";
        ClockEventCur.TimeEntered2 = DateTime.MinValue;
        clearAutoFieldsHelper();
        //FillTimeSpans();//not really needed because of the TextChanged event, but might prevent a bug.
        fillAutoControlsHelper();
    }

    private void clearAutoFieldsHelper() throws Exception {
        textClockedTime.Text = "";
        textTotalHours.Text = "";
        textRegTime.Text = "";
        textRate1Auto.Text = "";
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Delete this clock event?"))
        {
            return ;
        }
         
        ClockEvents.delete(ClockEventCur.ClockEventNum);
        SecurityLogs.MakeLogEntry(Permissions.TimecardDeleteEntry, 0, "Original entry: " + ClockEventCur.TimeEntered1.ToString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //if(textAmountBonus.errorProvider1.GetError(textAmountBonus)!="") {
        //  MsgBox.Show(this,"Please enter in a valid dollar amount for Bonus.");
        //  return;
        //}
        DateTime timeDisplayed1 = DateTime.MinValue;
        try
        {
            timeDisplayed1 = DateTime.Parse(textTimeDisplayed1.Text);
        }
        catch (Exception __dummyCatchVar6)
        {
            //because this must always be valid
            if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
            {
                MsgBox.show(this,"Please enter a valid clock-out date and time.");
            }
            else
            {
                MsgBox.show(this,"Please enter a valid clock-in date and time.");
            } 
            return ;
        }

        if (timeDisplayed1.Date > DateTime.Today)
        {
            if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
            {
                MsgBox.show(this,"Clock-out date cannot be a future date.");
            }
            else
            {
                MsgBox.show(this,"Clock-in date cannot be a future date.");
            } 
            return ;
        }
         
        DateTime timeDisplayed2 = DateTime.MinValue;
        if (!StringSupport.equals(textTimeDisplayed2.Text, ""))
        {
            try
            {
                //it can be empty
                timeDisplayed2 = DateTime.Parse(textTimeDisplayed2.Text);
            }
            catch (Exception __dummyCatchVar7)
            {
                if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
                {
                    MsgBox.show(this,"Please enter a valid clock-in date and time.");
                }
                else
                {
                    MsgBox.show(this,"Please enter a valid clock-out date and time.");
                } 
                return ;
            }
        
        }
         
        if (timeDisplayed2.Date > DateTime.Today)
        {
            if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
            {
                MsgBox.show(this,"Clock-in date cannot be a future date.");
            }
            else
            {
                MsgBox.show(this,"Clock-out date cannot be a future date.");
            } 
            return ;
        }
         
        if (!StringSupport.equals(textTimeDisplayed2.Text, "") && timeDisplayed1 > timeDisplayed2)
        {
            if (ClockEventCur.ClockStatus == TimeClockStatus.Break)
            {
                MsgBox.show(this,"Break end time cannot be earlier than break start time.");
                return ;
            }
            else
            {
                MsgBox.show(this,"Clock out time cannot be earlier than clock in time.");
                return ;
            } 
        }
         
        if (StringSupport.equals(textTimeDisplayed2.Text, "") && !StringSupport.equals(textTimeEntered2.Text, ""))
        {
            //user is trying to clear the time manually
            MsgBox.show(this,"A date and time must be entered in the second box, or use the Clear button.");
            return ;
        }
         
        TimeSpan overtime = TimeSpan.Zero;
        TimeSpan adjust = TimeSpan.Zero;
        if (ClockEventCur.ClockStatus != TimeClockStatus.Break)
        {
            if (!StringSupport.equals(textOTimeHours.Text, ""))
            {
                try
                {
                    if (textOTimeHours.Text.Contains(":"))
                    {
                        overtime = TimeSpan.Parse(textOTimeHours.Text);
                    }
                    else
                    {
                        overtime = TimeSpan.FromHours(Double.Parse(textOTimeHours.Text));
                    } 
                    if (overtime < TimeSpan.Zero)
                    {
                        MsgBox.show(this,"Overtime must be positive.");
                        return ;
                    }
                     
                }
                catch (Exception __dummyCatchVar8)
                {
                    MsgBox.show(this,"Please enter a valid overtime amount.");
                    return ;
                }
            
            }
             
            if (!StringSupport.equals(textAdjust.Text, ""))
            {
                try
                {
                    if (textAdjust.Text.Contains(":"))
                    {
                        adjust = TimeSpan.Parse(textAdjust.Text);
                    }
                    else
                    {
                        adjust = TimeSpan.FromHours(Double.Parse(textAdjust.Text));
                    } 
                }
                catch (Exception __dummyCatchVar9)
                {
                    MsgBox.show(this,"Please enter a valid adjustment amount.");
                    return ;
                }
            
            }
             
            if (StringSupport.equals(textRegTime.Text, ""))
            {
                //Must be invalid calc.
                if (StringSupport.equals(textTimeEntered2.Text, ""))
                {
                    //They haven't clocked out yet.	Invalid calc is expected.
                    if (!StringSupport.equals(textAdjust.Text.Trim(), "") || !StringSupport.equals(textOTimeHours.Text.Trim(), ""))
                    {
                        //They're entering in overtime or adjustments.
                        MsgBox.show(this,"Cannot enter overtime or adjustments while clocked in.");
                        return ;
                    }
                     
                }
                else
                {
                    //To this timespan is implied.
                    //They have clocked out.
                    MsgBox.show(this,"Overtime and adjustments cannot exceed the total time.");
                    return ;
                } 
            }
             
        }
         
        //timeEntered2 is largely taken care of, except for this one situation
        if (!StringSupport.equals(textTimeDisplayed2.Text, "") && StringSupport.equals(textTimeEntered2.Text, ""))
        {
            ClockEventCur.TimeEntered2 = MiscData.getNowDateTime();
        }
         
        ClockEventCur.TimeDisplayed1 = timeDisplayed1;
        ClockEventCur.TimeDisplayed2 = timeDisplayed2;
        ClockEventCur.ClockStatus = (TimeClockStatus)listStatus.SelectedIndex;
        if (StringSupport.equals(textAdjust.Text, ""))
        {
            //no override
            ClockEventCur.AdjustIsOverridden = false;
            ClockEventCur.Adjust = TimeSpan.Zero;
        }
        else
        {
            ClockEventCur.AdjustIsOverridden = true;
            ClockEventCur.Adjust = adjust;
        } 
        if (StringSupport.equals(textOTimeHours.Text, ""))
        {
            //no override
            ClockEventCur.OTimeHours = TimeSpan.FromHours(-1d);
        }
        else
        {
            ClockEventCur.OTimeHours = overtime;
        } 
        if (StringSupport.equals(textRate2Hours.Text, ""))
        {
            ClockEventCur.Rate2Hours = TimeSpan.FromHours(-1);
        }
         
        //if(textAmountBonus.Text=="") {
        //  ClockEventCur.AmountBonus=-1;
        //}
        //else {
        //  ClockEventCur.AmountBonus=PIn.Double(textAmountBonus.Text);
        //}
        //The two auto fields are only set externally.
        ClockEventCur.Note = textNote.Text;
        ClockEvents.update(ClockEventCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


