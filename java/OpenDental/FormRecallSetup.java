//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:38 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormRecallMessageEdit;
import OpenDental.FormRecallSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.RecallTypeC;

/**
* 
*/
public class FormRecallSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPostcardsPerSheet = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkReturnAdd = new System.Windows.Forms.CheckBox();
    private GroupBox groupBox2 = new GroupBox();
    private OpenDental.ValidDouble textDown;
    private Label label12 = new Label();
    private OpenDental.ValidDouble textRight;
    private Label label13 = new Label();
    private CheckBox checkGroupFamilies = new CheckBox();
    private Label label14 = new Label();
    private Label label15 = new Label();
    private GroupBox groupBox3 = new GroupBox();
    private Label label25 = new Label();
    private ComboBox comboStatusMailedRecall = new ComboBox();
    private ComboBox comboStatusEmailedRecall = new ComboBox();
    private Label label26 = new Label();
    private ListBox listTypes = new ListBox();
    private Label label1 = new Label();
    private OpenDental.ValidNumber textDaysPast;
    private OpenDental.ValidNumber textDaysFuture;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.ValidNumber textDaysSecondReminder;
    private OpenDental.ValidNumber textDaysFirstReminder;
    private Label label2 = new Label();
    private Label label3 = new Label();
    private OpenDental.UI.ODGrid gridMain;
    private System.ComponentModel.Container components = null;
    private OpenDental.ValidNumber textMaxReminders;
    private Label label4 = new Label();
    private ComboBox comboStatusEmailedConfirm = new ComboBox();
    private Label label5 = new Label();
    private GroupBox groupBox4 = new GroupBox();
    private RadioButton radioUseEmailFalse = new RadioButton();
    private RadioButton radioUseEmailTrue = new RadioButton();
    private Label label6 = new Label();
    private ComboBox comboStatusTextMessagedConfirm = new ComboBox();
    private RadioButton radioExcludeFutureNo = new RadioButton();
    private RadioButton radioExcludeFutureYes = new RadioButton();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormRecallSetup() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    //Lan.C(this, new System.Windows.Forms.Control[] {
    //textBox1,
    //textBox6
    //});
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRecallSetup.class);
        this.textPostcardsPerSheet = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.checkReturnAdd = new System.Windows.Forms.CheckBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textDown = new OpenDental.ValidDouble();
        this.label12 = new System.Windows.Forms.Label();
        this.textRight = new OpenDental.ValidDouble();
        this.label13 = new System.Windows.Forms.Label();
        this.checkGroupFamilies = new System.Windows.Forms.CheckBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.textDaysFuture = new OpenDental.ValidNumber();
        this.textDaysPast = new OpenDental.ValidNumber();
        this.label25 = new System.Windows.Forms.Label();
        this.comboStatusMailedRecall = new System.Windows.Forms.ComboBox();
        this.comboStatusEmailedRecall = new System.Windows.Forms.ComboBox();
        this.label26 = new System.Windows.Forms.Label();
        this.listTypes = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textMaxReminders = new OpenDental.ValidNumber();
        this.label4 = new System.Windows.Forms.Label();
        this.textDaysSecondReminder = new OpenDental.ValidNumber();
        this.textDaysFirstReminder = new OpenDental.ValidNumber();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.comboStatusEmailedConfirm = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.radioUseEmailFalse = new System.Windows.Forms.RadioButton();
        this.radioUseEmailTrue = new System.Windows.Forms.RadioButton();
        this.label6 = new System.Windows.Forms.Label();
        this.comboStatusTextMessagedConfirm = new System.Windows.Forms.ComboBox();
        this.radioExcludeFutureNo = new System.Windows.Forms.RadioButton();
        this.radioExcludeFutureYes = new System.Windows.Forms.RadioButton();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.SuspendLayout();
        //
        // textPostcardsPerSheet
        //
        this.textPostcardsPerSheet.Location = new System.Drawing.Point(194, 513);
        this.textPostcardsPerSheet.Name = "textPostcardsPerSheet";
        this.textPostcardsPerSheet.Size = new System.Drawing.Size(34, 20);
        this.textPostcardsPerSheet.TabIndex = 18;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(18, 516);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(176, 16);
        this.label8.TabIndex = 19;
        this.label8.Text = "Postcards per sheet (1,3,or 4)";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkReturnAdd
        //
        this.checkReturnAdd.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkReturnAdd.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkReturnAdd.Location = new System.Drawing.Point(60, 534);
        this.checkReturnAdd.Name = "checkReturnAdd";
        this.checkReturnAdd.Size = new System.Drawing.Size(147, 19);
        this.checkReturnAdd.TabIndex = 43;
        this.checkReturnAdd.Text = "Show return address";
        this.checkReturnAdd.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textDown);
        this.groupBox2.Controls.Add(this.label12);
        this.groupBox2.Controls.Add(this.textRight);
        this.groupBox2.Controls.Add(this.label13);
        this.groupBox2.Location = new System.Drawing.Point(687, 428);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(191, 67);
        this.groupBox2.TabIndex = 48;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Adjust Postcard Position in Inches";
        //
        // textDown
        //
        this.textDown.Location = new System.Drawing.Point(110, 43);
        this.textDown.Name = "textDown";
        this.textDown.Size = new System.Drawing.Size(73, 20);
        this.textDown.TabIndex = 6;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(48, 42);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(60, 20);
        this.label12.TabIndex = 5;
        this.label12.Text = "Down";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRight
        //
        this.textRight.Location = new System.Drawing.Point(110, 18);
        this.textRight.Name = "textRight";
        this.textRight.Size = new System.Drawing.Size(73, 20);
        this.textRight.TabIndex = 4;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(48, 17);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(60, 20);
        this.label13.TabIndex = 4;
        this.label13.Text = "Right";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkGroupFamilies
        //
        this.checkGroupFamilies.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkGroupFamilies.Location = new System.Drawing.Point(85, 15);
        this.checkGroupFamilies.Name = "checkGroupFamilies";
        this.checkGroupFamilies.Size = new System.Drawing.Size(121, 18);
        this.checkGroupFamilies.TabIndex = 49;
        this.checkGroupFamilies.Text = "Group Families";
        this.checkGroupFamilies.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkGroupFamilies.UseVisualStyleBackColor = true;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(6, 32);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(184, 20);
        this.label14.TabIndex = 50;
        this.label14.Text = "Days Past (e.g. 1095, blank, etc)";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(9, 53);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(181, 20);
        this.label15.TabIndex = 52;
        this.label15.Text = "Days Future (e.g. 7)";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.textDaysFuture);
        this.groupBox3.Controls.Add(this.textDaysPast);
        this.groupBox3.Controls.Add(this.checkGroupFamilies);
        this.groupBox3.Controls.Add(this.label14);
        this.groupBox3.Controls.Add(this.label15);
        this.groupBox3.Location = new System.Drawing.Point(415, 428);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(253, 78);
        this.groupBox3.TabIndex = 54;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Recall List Default View";
        //
        // textDaysFuture
        //
        this.textDaysFuture.Location = new System.Drawing.Point(192, 54);
        this.textDaysFuture.setMaxVal(10000);
        this.textDaysFuture.setMinVal(0);
        this.textDaysFuture.Name = "textDaysFuture";
        this.textDaysFuture.Size = new System.Drawing.Size(53, 20);
        this.textDaysFuture.TabIndex = 66;
        //
        // textDaysPast
        //
        this.textDaysPast.Location = new System.Drawing.Point(192, 32);
        this.textDaysPast.setMaxVal(10000);
        this.textDaysPast.setMinVal(0);
        this.textDaysPast.Name = "textDaysPast";
        this.textDaysPast.Size = new System.Drawing.Size(53, 20);
        this.textDaysPast.TabIndex = 65;
        //
        // label25
        //
        this.label25.Location = new System.Drawing.Point(35, 427);
        this.label25.Name = "label25";
        this.label25.Size = new System.Drawing.Size(157, 16);
        this.label25.TabIndex = 57;
        this.label25.Text = "Status for mailed recall";
        this.label25.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboStatusMailedRecall
        //
        this.comboStatusMailedRecall.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatusMailedRecall.FormattingEnabled = true;
        this.comboStatusMailedRecall.Location = new System.Drawing.Point(194, 423);
        this.comboStatusMailedRecall.MaxDropDownItems = 20;
        this.comboStatusMailedRecall.Name = "comboStatusMailedRecall";
        this.comboStatusMailedRecall.Size = new System.Drawing.Size(206, 21);
        this.comboStatusMailedRecall.TabIndex = 58;
        //
        // comboStatusEmailedRecall
        //
        this.comboStatusEmailedRecall.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatusEmailedRecall.FormattingEnabled = true;
        this.comboStatusEmailedRecall.Location = new System.Drawing.Point(194, 445);
        this.comboStatusEmailedRecall.MaxDropDownItems = 20;
        this.comboStatusEmailedRecall.Name = "comboStatusEmailedRecall";
        this.comboStatusEmailedRecall.Size = new System.Drawing.Size(206, 21);
        this.comboStatusEmailedRecall.TabIndex = 60;
        //
        // label26
        //
        this.label26.Location = new System.Drawing.Point(35, 449);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(157, 16);
        this.label26.TabIndex = 59;
        this.label26.Text = "Status for e-mailed recall";
        this.label26.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listTypes
        //
        this.listTypes.FormattingEnabled = true;
        this.listTypes.Location = new System.Drawing.Point(194, 552);
        this.listTypes.Name = "listTypes";
        this.listTypes.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listTypes.Size = new System.Drawing.Size(120, 82);
        this.listTypes.TabIndex = 64;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(37, 552);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(157, 65);
        this.label1.TabIndex = 63;
        this.label1.Text = "Types to show in recall list (typically just prophy, perio, and user-added types)" + "";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textMaxReminders);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.textDaysSecondReminder);
        this.groupBox1.Controls.Add(this.textDaysFirstReminder);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(415, 546);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(253, 86);
        this.groupBox1.TabIndex = 65;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Also show in list if # of days since";
        //
        // textMaxReminders
        //
        this.textMaxReminders.Location = new System.Drawing.Point(192, 60);
        this.textMaxReminders.setMaxVal(10000);
        this.textMaxReminders.setMinVal(0);
        this.textMaxReminders.Name = "textMaxReminders";
        this.textMaxReminders.Size = new System.Drawing.Size(53, 20);
        this.textMaxReminders.TabIndex = 68;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(44, 59);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(146, 20);
        this.label4.TabIndex = 67;
        this.label4.Text = "Max # Reminders (e.g. 4)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDaysSecondReminder
        //
        this.textDaysSecondReminder.Location = new System.Drawing.Point(192, 38);
        this.textDaysSecondReminder.setMaxVal(10000);
        this.textDaysSecondReminder.setMinVal(0);
        this.textDaysSecondReminder.Name = "textDaysSecondReminder";
        this.textDaysSecondReminder.Size = new System.Drawing.Size(53, 20);
        this.textDaysSecondReminder.TabIndex = 66;
        //
        // textDaysFirstReminder
        //
        this.textDaysFirstReminder.Location = new System.Drawing.Point(192, 16);
        this.textDaysFirstReminder.setMaxVal(10000);
        this.textDaysFirstReminder.setMinVal(0);
        this.textDaysFirstReminder.Name = "textDaysFirstReminder";
        this.textDaysFirstReminder.Size = new System.Drawing.Size(53, 20);
        this.textDaysFirstReminder.TabIndex = 65;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(89, 15);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(101, 20);
        this.label2.TabIndex = 50;
        this.label2.Text = "Initial Reminder";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(44, 37);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(146, 20);
        this.label3.TabIndex = 52;
        this.label3.Text = "Second (or more) Reminder";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(9, 8);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(869, 411);
        this.gridMain.TabIndex = 67;
        this.gridMain.setTitle("Messages");
        this.gridMain.setTranslationName("TableRecallMsgs");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(787, 570);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(787, 608);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // comboStatusEmailedConfirm
        //
        this.comboStatusEmailedConfirm.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatusEmailedConfirm.FormattingEnabled = true;
        this.comboStatusEmailedConfirm.Location = new System.Drawing.Point(194, 467);
        this.comboStatusEmailedConfirm.MaxDropDownItems = 20;
        this.comboStatusEmailedConfirm.Name = "comboStatusEmailedConfirm";
        this.comboStatusEmailedConfirm.Size = new System.Drawing.Size(206, 21);
        this.comboStatusEmailedConfirm.TabIndex = 69;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(3, 471);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(189, 16);
        this.label5.TabIndex = 68;
        this.label5.Text = "Status for e-mailed confirmation";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.radioUseEmailFalse);
        this.groupBox4.Controls.Add(this.radioUseEmailTrue);
        this.groupBox4.Location = new System.Drawing.Point(687, 501);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(191, 57);
        this.groupBox4.TabIndex = 70;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Use e-mail if";
        //
        // radioUseEmailFalse
        //
        this.radioUseEmailFalse.Location = new System.Drawing.Point(7, 34);
        this.radioUseEmailFalse.Name = "radioUseEmailFalse";
        this.radioUseEmailFalse.Size = new System.Drawing.Size(181, 18);
        this.radioUseEmailFalse.TabIndex = 1;
        this.radioUseEmailFalse.Text = "E-mail is preferred recall method";
        this.radioUseEmailFalse.UseVisualStyleBackColor = true;
        //
        // radioUseEmailTrue
        //
        this.radioUseEmailTrue.Location = new System.Drawing.Point(7, 17);
        this.radioUseEmailTrue.Name = "radioUseEmailTrue";
        this.radioUseEmailTrue.Size = new System.Drawing.Size(181, 18);
        this.radioUseEmailTrue.TabIndex = 0;
        this.radioUseEmailTrue.Text = "Has e-mail address";
        this.radioUseEmailTrue.UseVisualStyleBackColor = true;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 493);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(189, 16);
        this.label6.TabIndex = 68;
        this.label6.Text = "Status for text messaged confirmation";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboStatusTextMessagedConfirm
        //
        this.comboStatusTextMessagedConfirm.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatusTextMessagedConfirm.FormattingEnabled = true;
        this.comboStatusTextMessagedConfirm.Location = new System.Drawing.Point(194, 489);
        this.comboStatusTextMessagedConfirm.MaxDropDownItems = 20;
        this.comboStatusTextMessagedConfirm.Name = "comboStatusTextMessagedConfirm";
        this.comboStatusTextMessagedConfirm.Size = new System.Drawing.Size(206, 21);
        this.comboStatusTextMessagedConfirm.TabIndex = 69;
        //
        // radioExcludeFutureNo
        //
        this.radioExcludeFutureNo.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioExcludeFutureNo.Location = new System.Drawing.Point(404, 508);
        this.radioExcludeFutureNo.Name = "radioExcludeFutureNo";
        this.radioExcludeFutureNo.Size = new System.Drawing.Size(217, 18);
        this.radioExcludeFutureNo.TabIndex = 71;
        this.radioExcludeFutureNo.Text = "Exclude from list if recall scheduled";
        this.radioExcludeFutureNo.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioExcludeFutureNo.UseVisualStyleBackColor = true;
        //
        // radioExcludeFutureYes
        //
        this.radioExcludeFutureYes.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioExcludeFutureYes.Location = new System.Drawing.Point(404, 526);
        this.radioExcludeFutureYes.Name = "radioExcludeFutureYes";
        this.radioExcludeFutureYes.Size = new System.Drawing.Size(217, 18);
        this.radioExcludeFutureYes.TabIndex = 72;
        this.radioExcludeFutureYes.Text = "Exclude from list if any future appt";
        this.radioExcludeFutureYes.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioExcludeFutureYes.UseVisualStyleBackColor = true;
        //
        // FormRecallSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(886, 649);
        this.Controls.Add(this.radioExcludeFutureYes);
        this.Controls.Add(this.radioExcludeFutureNo);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.comboStatusTextMessagedConfirm);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.comboStatusEmailedConfirm);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.listTypes);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboStatusEmailedRecall);
        this.Controls.Add(this.label26);
        this.Controls.Add(this.comboStatusMailedRecall);
        this.Controls.Add(this.label25);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.checkReturnAdd);
        this.Controls.Add(this.textPostcardsPerSheet);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRecallSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Recall and Confirmation";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormRecallSetup_FormClosing);
        this.Load += new System.EventHandler(this.FormRecallSetup_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox4.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRecallSetup_Load(Object sender, System.EventArgs e) throws Exception {
        checkGroupFamilies.Checked = PrefC.getBool(PrefName.RecallGroupByFamily);
        textPostcardsPerSheet.Text = PrefC.getLong(PrefName.RecallPostcardsPerSheet).ToString();
        checkReturnAdd.Checked = PrefC.getBool(PrefName.RecallCardsShowReturnAdd);
        checkGroupFamilies.Checked = PrefC.getBool(PrefName.RecallGroupByFamily);
        if (PrefC.getLong(PrefName.RecallDaysPast) == -1)
        {
            textDaysPast.Text = "";
        }
        else
        {
            textDaysPast.Text = PrefC.getLong(PrefName.RecallDaysPast).ToString();
        } 
        if (PrefC.getLong(PrefName.RecallDaysFuture) == -1)
        {
            textDaysFuture.Text = "";
        }
        else
        {
            textDaysFuture.Text = PrefC.getLong(PrefName.RecallDaysFuture).ToString();
        } 
        if (PrefC.getBool(PrefName.RecallExcludeIfAnyFutureAppt))
        {
            radioExcludeFutureYes.Checked = true;
        }
        else
        {
            radioExcludeFutureNo.Checked = true;
        } 
        textRight.Text = PrefC.getDouble(PrefName.RecallAdjustRight).ToString();
        textDown.Text = PrefC.getDouble(PrefName.RecallAdjustDown).ToString();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()].Length;i++)
        {
            //comboStatusMailedRecall.Items.Clear();
            comboStatusMailedRecall.Items.Add(DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].ItemName);
            comboStatusEmailedRecall.Items.Add(DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].DefNum == PrefC.getLong(PrefName.RecallStatusMailed))
            {
                comboStatusMailedRecall.SelectedIndex = i;
            }
             
            if (DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].DefNum == PrefC.getLong(PrefName.RecallStatusEmailed))
            {
                comboStatusEmailedRecall.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()].Length;i++)
        {
            comboStatusEmailedConfirm.Items.Add(DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].DefNum == PrefC.getLong(PrefName.ConfirmStatusEmailed))
            {
                comboStatusEmailedConfirm.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()].Length;i++)
        {
            comboStatusTextMessagedConfirm.Items.Add(DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].DefNum == PrefC.getLong(PrefName.ConfirmStatusTextMessaged))
            {
                comboStatusTextMessagedConfirm.SelectedIndex = i;
            }
             
        }
        List<long> recalltypes = new List<long>();
        String[] typearray = PrefC.getString(PrefName.RecallTypesShowingInList).Split(',');
        if (typearray.Length > 0)
        {
            for (int i = 0;i < typearray.Length;i++)
            {
                recalltypes.Add(PIn.Long(typearray[i]));
            }
        }
         
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            listTypes.Items.Add(RecallTypeC.getListt()[i].Description);
            if (recalltypes.Contains(RecallTypeC.getListt()[i].RecallTypeNum))
            {
                listTypes.SetSelected(i, true);
            }
             
        }
        if (PrefC.getLong(PrefName.RecallShowIfDaysFirstReminder) == -1)
        {
            textDaysFirstReminder.Text = "";
        }
        else
        {
            textDaysFirstReminder.Text = PrefC.getLong(PrefName.RecallShowIfDaysFirstReminder).ToString();
        } 
        if (PrefC.getLong(PrefName.RecallShowIfDaysSecondReminder) == -1)
        {
            textDaysSecondReminder.Text = "";
        }
        else
        {
            textDaysSecondReminder.Text = PrefC.getLong(PrefName.RecallShowIfDaysSecondReminder).ToString();
        } 
        if (PrefC.getLong(PrefName.RecallMaxNumberReminders) == -1)
        {
            textMaxReminders.Text = "";
        }
        else
        {
            textMaxReminders.Text = PrefC.getLong(PrefName.RecallMaxNumberReminders).ToString();
        } 
        if (PrefC.getBool(PrefName.RecallUseEmailIfHasEmailAddress))
        {
            radioUseEmailTrue.Checked = true;
        }
        else
        {
            radioUseEmailFalse.Checked = true;
        } 
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TableRecallMsgs","Remind#"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallMsgs","Mode"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("",300);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallMsgs","Message"),500);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("1");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Subject line"));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailSubject));
        //old
        row.setTag("RecallEmailSubject");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("1");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailMessage));
        row.setTag("RecallEmailMessage");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("1");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList] where the list of family members should show."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailFamMsg));
        row.setTag("RecallEmailFamMsg");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("1");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardMessage));
        //old
        row.setTag("RecallPostcardMessage");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("1");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList] where the list of family members should show."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardFamMsg));
        //old
        row.setTag("RecallPostcardFamMsg");
        gridMain.getRows().add(row);
        //2---------------------------------------------------------------------------------------------
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("2");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Subject line"));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailSubject2));
        row.setTag("RecallEmailSubject2");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("2");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailMessage2));
        row.setTag("RecallEmailMessage2");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("2");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList]."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailFamMsg2));
        row.setTag("RecallEmailFamMsg2");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("2");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardMessage2));
        row.setTag("RecallPostcardMessage2");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("2");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList]."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardFamMsg2));
        row.setTag("RecallPostcardFamMsg2");
        gridMain.getRows().add(row);
        //3---------------------------------------------------------------------------------------------
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("3");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Subject line"));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailSubject3));
        row.setTag("RecallEmailSubject3");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("3");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailMessage3));
        row.setTag("RecallEmailMessage3");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("3");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList]."));
        row.getCells().add(PrefC.getString(PrefName.RecallEmailFamMsg3));
        row.setTag("RecallEmailFamMsg3");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("3");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"Available variables: [DueDate], [NameFL], [NameF]."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardMessage3));
        row.setTag("RecallPostcardMessage3");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("3");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"For multiple patients in one family.  Use [FamilyList]."));
        row.getCells().add(PrefC.getString(PrefName.RecallPostcardFamMsg3));
        row.setTag("RecallPostcardFamMsg3");
        gridMain.getRows().add(row);
        //Confirmation---------------------------------------------------------------------------------------------
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("");
        row.getCells().add(Lan.g(this,"Postcard"));
        row.getCells().add(Lan.g(this,"Confirmation message.  Use [date]  and [time] where you want those values to be inserted"));
        row.getCells().add(PrefC.getString(PrefName.ConfirmPostcardMessage));
        row.setTag("ConfirmPostcardMessage");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Confirmation subject line."));
        row.getCells().add(PrefC.getString(PrefName.ConfirmEmailSubject));
        row.setTag("ConfirmEmailSubject");
        gridMain.getRows().add(row);
        //
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("");
        row.getCells().add(Lan.g(this,"E-mail"));
        row.getCells().add(Lan.g(this,"Confirmation message.  Available variables: [NameF], [NameFL], [date], and [time]."));
        row.getCells().add(PrefC.getString(PrefName.ConfirmEmailMessage));
        row.setTag("ConfirmEmailMessage");
        gridMain.getRows().add(row);
        //Text Messaging----------------------------------------------------------------------------------------------
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("");
        row.getCells().add(Lan.g(this,"Text"));
        row.getCells().add(Lan.g(this,"Confirmation message.  Available variables: [NameF], [NameFL], [date], and [time]."));
        row.getCells().add(PrefC.getString(PrefName.ConfirmTextMessage));
        row.setTag("ConfirmTextMessage");
        gridMain.getRows().add(row);
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        PrefName prefName = (PrefName)Enum.Parse(PrefName.class, gridMain.getRows().get___idx(e.getRow()).getTag().ToString());
        FormRecallMessageEdit FormR = new FormRecallMessageEdit();
        FormR.MessageVal = PrefC.getString(prefName);
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Prefs.updateString(prefName,FormR.MessageVal);
        //Prefs.RefreshCache();//above line handles it.
        fillGrid();
        changed = true;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textRight.errorProvider1.GetError(textRight), "") || !StringSupport.equals(textDown.errorProvider1.GetError(textDown), "") || !StringSupport.equals(textDaysPast.errorProvider1.GetError(textDaysPast), "") || !StringSupport.equals(textDaysFuture.errorProvider1.GetError(textDaysFuture), "") || !StringSupport.equals(textDaysFirstReminder.errorProvider1.GetError(textDaysFirstReminder), "") || !StringSupport.equals(textDaysSecondReminder.errorProvider1.GetError(textDaysSecondReminder), "") || !StringSupport.equals(textMaxReminders.errorProvider1.GetError(textMaxReminders), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDaysFirstReminder.Text, ""))
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Initial Reminder box should not be blank, or the recall list will be blank."))
            {
                return ;
            }
             
        }
         
        if (!StringSupport.equals(textPostcardsPerSheet.Text, "1") && !StringSupport.equals(textPostcardsPerSheet.Text, "3") && !StringSupport.equals(textPostcardsPerSheet.Text, "4"))
        {
            MsgBox.show(this,"The value in postcards per sheet must be 1, 3, or 4");
            return ;
        }
         
        if (comboStatusMailedRecall.SelectedIndex == -1 || comboStatusMailedRecall.SelectedIndex == -1)
        {
            MsgBox.show(this,"Both status options at the bottom must be set.");
            return ;
        }
         
        //End of Validation
        if (Prefs.UpdateString(PrefName.RecallPostcardsPerSheet, textPostcardsPerSheet.Text))
        {
            if (StringSupport.equals(textPostcardsPerSheet.Text, "1"))
            {
                MsgBox.show(this,"If using 1 postcard per sheet, you must adjust the position, and also the preview will not work");
            }
             
        }
         
        Prefs.UpdateBool(PrefName.RecallCardsShowReturnAdd, checkReturnAdd.Checked);
        Prefs.UpdateBool(PrefName.RecallGroupByFamily, checkGroupFamilies.Checked);
        if (StringSupport.equals(textDaysPast.Text, ""))
        {
            Prefs.UpdateLong(PrefName.RecallDaysPast, -1);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallDaysPast, PIn.Long(textDaysPast.Text));
        } 
        if (StringSupport.equals(textDaysFuture.Text, ""))
        {
            Prefs.UpdateLong(PrefName.RecallDaysFuture, -1);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallDaysFuture, PIn.Long(textDaysFuture.Text));
        } 
        Prefs.UpdateBool(PrefName.RecallExcludeIfAnyFutureAppt, radioExcludeFutureYes.Checked);
        Prefs.UpdateDouble(PrefName.RecallAdjustRight, PIn.Double(textRight.Text));
        Prefs.UpdateDouble(PrefName.RecallAdjustDown, PIn.Double(textDown.Text));
        if (comboStatusEmailedRecall.SelectedIndex == -1)
        {
            Prefs.UpdateLong(PrefName.RecallStatusEmailed, 0);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallStatusEmailed, DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][comboStatusEmailedRecall.SelectedIndex].DefNum);
        } 
        if (comboStatusMailedRecall.SelectedIndex == -1)
        {
            Prefs.UpdateLong(PrefName.RecallStatusMailed, 0);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallStatusMailed, DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][comboStatusMailedRecall.SelectedIndex].DefNum);
        } 
        if (comboStatusEmailedConfirm.SelectedIndex == -1)
        {
            Prefs.UpdateLong(PrefName.ConfirmStatusEmailed, 0);
        }
        else
        {
            Prefs.UpdateLong(PrefName.ConfirmStatusEmailed, DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][comboStatusEmailedConfirm.SelectedIndex].DefNum);
        } 
        if (comboStatusTextMessagedConfirm.SelectedIndex == -1)
        {
            Prefs.UpdateLong(PrefName.ConfirmStatusTextMessaged, 0);
        }
        else
        {
            Prefs.UpdateLong(PrefName.ConfirmStatusTextMessaged, DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][comboStatusTextMessagedConfirm.SelectedIndex].DefNum);
        } 
        String recalltypes = "";
        for (int i = 0;i < listTypes.SelectedIndices.Count;i++)
        {
            if (i > 0)
            {
                recalltypes += ",";
            }
             
            recalltypes += RecallTypeC.getListt()[listTypes.SelectedIndices[i]].RecallTypeNum.ToString();
        }
        Prefs.updateString(PrefName.RecallTypesShowingInList,recalltypes);
        if (StringSupport.equals(textDaysFirstReminder.Text, ""))
        {
            Prefs.UpdateLong(PrefName.RecallShowIfDaysFirstReminder, -1);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallShowIfDaysFirstReminder, PIn.Long(textDaysFirstReminder.Text));
        } 
        if (StringSupport.equals(textDaysSecondReminder.Text, ""))
        {
            Prefs.UpdateLong(PrefName.RecallShowIfDaysSecondReminder, -1);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallShowIfDaysSecondReminder, PIn.Long(textDaysSecondReminder.Text));
        } 
        if (StringSupport.equals(textMaxReminders.Text, ""))
        {
            Prefs.UpdateLong(PrefName.RecallMaxNumberReminders, -1);
        }
        else
        {
            Prefs.UpdateLong(PrefName.RecallMaxNumberReminders, PIn.Long(textMaxReminders.Text));
        } 
        if (radioUseEmailTrue.Checked)
        {
            Prefs.updateBool(PrefName.RecallUseEmailIfHasEmailAddress,true);
        }
        else
        {
            Prefs.updateBool(PrefName.RecallUseEmailIfHasEmailAddress,false);
        } 
        changed = true;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formRecallSetup_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

}


