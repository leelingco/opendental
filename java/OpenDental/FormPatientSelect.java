//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:26 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPatientAddAll;
import OpenDental.FormPatientEdit;
import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDental.User_Controls.__MultiKeyboardClickEventHandler;
import OpenDental.User_Controls.KeyboardClickEventArgs;
import OpenDentBusiness.Cache;
import OpenDentBusiness.CustReference;
import OpenDentBusiness.CustReferences;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Family;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SiteC;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
//#define TRIALONLY //Do not set here because ContrChart.ProcButtonClicked and FormOpenDental also need to test this value.
/**
* All this dialog does is set the patnum and it is up to the calling form to do an immediate refresh, or possibly just change the patnum back to what it was.  So the other patient fields must remain intact during all logic in this form, especially if SelectionModeOnly.
*/
public class FormPatientSelect  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private Patients Patients;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butAddPt;
    /**
    * Use when you want to specify a patient without changing the current patient.  If true, then the Add Patient button will not be visible.
    */
    public boolean SelectionModeOnly = new boolean();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textHmPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkHideInactive = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupAddPt = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkGuarantors = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textChartNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSSN = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butSearch;
    /**
    * When closing the form, this indicates whether a new patient was added from within this form.
    */
    public boolean NewPatientAdded = new boolean();
    /**
    * Only used when double clicking blank area in Appts. Sets this value to the currently selected pt.  That patient will come up on the screen already selected and user just has to click OK. Or they can select a different pt or add a new pt.  If 0, then no initial patient is selected.
    */
    public long InitialPatNum = new long();
    private DataTable PtDataTable = new DataTable();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.User_Controls.ContrKeyboard contrKeyboard1;
    /**
    * When closing the form, this will hold the value of the newly selected PatNum.
    */
    public long SelectedPatNum = new long();
    private CheckBox checkShowArchived = new CheckBox();
    private TextBox textBirthdate = new TextBox();
    private Label label2 = new Label();
    private ComboBox comboBillingType = new ComboBox();
    private OpenDental.UI.Button butGetAll;
    private CheckBox checkRefresh = new CheckBox();
    private OpenDental.UI.Button butAddAll;
    private ComboBox comboSite = new ComboBox();
    private Label labelSite = new Label();
    private TextBox selectedTxtBox = new TextBox();
    private TextBox textSubscriberID = new TextBox();
    private Label label13 = new Label();
    private TextBox textEmail = new TextBox();
    private Label labelEmail = new Label();
    private List<DisplayField> fields = new List<DisplayField>();
    /**
    * 
    */
    public FormPatientSelect() throws Exception {
        initializeComponent();
        //required first
        //tb2.CellClicked += new OpenDental.ContrTable.CellEventHandler(tb2_CellClicked);
        //tb2.CellDoubleClicked += new OpenDental.ContrTable.CellEventHandler(tb2_CellDoubleClicked);
        Patients = new Patients();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPatientSelect.class);
        this.textLName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupAddPt = new System.Windows.Forms.GroupBox();
        this.butAddAll = new OpenDental.UI.Button();
        this.butAddPt = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textEmail = new System.Windows.Forms.TextBox();
        this.labelEmail = new System.Windows.Forms.Label();
        this.textSubscriberID = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.comboBillingType = new System.Windows.Forms.ComboBox();
        this.textBirthdate = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkShowArchived = new System.Windows.Forms.CheckBox();
        this.textChartNumber = new System.Windows.Forms.TextBox();
        this.textSSN = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textPatNum = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textState = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textCity = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.checkGuarantors = new System.Windows.Forms.CheckBox();
        this.checkHideInactive = new System.Windows.Forms.CheckBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textHmPhone = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textFName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkRefresh = new System.Windows.Forms.CheckBox();
        this.butGetAll = new OpenDental.UI.Button();
        this.butSearch = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.contrKeyboard1 = new OpenDental.User_Controls.ContrKeyboard();
        this.groupAddPt.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // textLName
        //
        this.textLName.Location = new System.Drawing.Point(166, 34);
        this.textLName.Name = "textLName";
        this.textLName.Size = new System.Drawing.Size(90, 20);
        this.textLName.TabIndex = 0;
        this.textLName.TextChanged += new System.EventHandler(this.textLName_TextChanged);
        this.textLName.Enter += new System.EventHandler(this.textBox_Enter);
        this.textLName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textLName_KeyDown);
        this.textLName.Leave += new System.EventHandler(this.textBox_Leave);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 37);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 12);
        this.label1.TabIndex = 3;
        this.label1.Text = "Last Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupAddPt
        //
        this.groupAddPt.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupAddPt.Controls.Add(this.butAddAll);
        this.groupAddPt.Controls.Add(this.butAddPt);
        this.groupAddPt.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupAddPt.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.groupAddPt.Location = new System.Drawing.Point(674, 585);
        this.groupAddPt.Name = "groupAddPt";
        this.groupAddPt.Size = new System.Drawing.Size(262, 53);
        this.groupAddPt.TabIndex = 1;
        this.groupAddPt.TabStop = false;
        this.groupAddPt.Text = "Add New Family:";
        //
        // butAddAll
        //
        this.butAddAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddAll.setAutosize(true);
        this.butAddAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddAll.setCornerRadius(4F);
        this.butAddAll.Location = new System.Drawing.Point(148, 21);
        this.butAddAll.Name = "butAddAll";
        this.butAddAll.Size = new System.Drawing.Size(75, 23);
        this.butAddAll.TabIndex = 43;
        this.butAddAll.Text = "Add Many";
        this.butAddAll.Click += new System.EventHandler(this.butAddAll_Click);
        //
        // butAddPt
        //
        this.butAddPt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddPt.setAutosize(true);
        this.butAddPt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddPt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddPt.setCornerRadius(4F);
        this.butAddPt.Location = new System.Drawing.Point(42, 21);
        this.butAddPt.Name = "butAddPt";
        this.butAddPt.Size = new System.Drawing.Size(75, 23);
        this.butAddPt.TabIndex = 72;
        this.butAddPt.Text = "&Add Pt";
        this.butAddPt.Click += new System.EventHandler(this.butAddPt_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(775, 647);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 26);
        this.butOK.TabIndex = 20;
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
        this.butCancel.Location = new System.Drawing.Point(857, 647);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(76, 26);
        this.butCancel.TabIndex = 21;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox2.Controls.Add(this.textEmail);
        this.groupBox2.Controls.Add(this.labelEmail);
        this.groupBox2.Controls.Add(this.textSubscriberID);
        this.groupBox2.Controls.Add(this.label13);
        this.groupBox2.Controls.Add(this.comboSite);
        this.groupBox2.Controls.Add(this.labelSite);
        this.groupBox2.Controls.Add(this.comboBillingType);
        this.groupBox2.Controls.Add(this.textBirthdate);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.checkShowArchived);
        this.groupBox2.Controls.Add(this.textChartNumber);
        this.groupBox2.Controls.Add(this.textSSN);
        this.groupBox2.Controls.Add(this.label12);
        this.groupBox2.Controls.Add(this.label11);
        this.groupBox2.Controls.Add(this.label10);
        this.groupBox2.Controls.Add(this.textPatNum);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.Controls.Add(this.textState);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.textCity);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.Controls.Add(this.checkGuarantors);
        this.groupBox2.Controls.Add(this.checkHideInactive);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.textAddress);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.textHmPhone);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.textFName);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.textLName);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(674, 107);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(262, 396);
        this.groupBox2.TabIndex = 0;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Search by:";
        //
        // textEmail
        //
        this.textEmail.Location = new System.Drawing.Point(166, 254);
        this.textEmail.Name = "textEmail";
        this.textEmail.Size = new System.Drawing.Size(90, 20);
        this.textEmail.TabIndex = 11;
        this.textEmail.TextChanged += new System.EventHandler(this.textEmail_TextChanged);
        //
        // labelEmail
        //
        this.labelEmail.Location = new System.Drawing.Point(11, 258);
        this.labelEmail.Name = "labelEmail";
        this.labelEmail.Size = new System.Drawing.Size(156, 12);
        this.labelEmail.TabIndex = 43;
        this.labelEmail.Text = "E-mail";
        this.labelEmail.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubscriberID
        //
        this.textSubscriberID.Location = new System.Drawing.Point(166, 234);
        this.textSubscriberID.Name = "textSubscriberID";
        this.textSubscriberID.Size = new System.Drawing.Size(90, 20);
        this.textSubscriberID.TabIndex = 10;
        this.textSubscriberID.TextChanged += new System.EventHandler(this.textSubscriberID_TextChanged);
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(11, 238);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(156, 12);
        this.label13.TabIndex = 41;
        this.label13.Text = "Subscriber ID";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSite
        //
        this.comboSite.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSite.Location = new System.Drawing.Point(118, 303);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(138, 21);
        this.comboSite.TabIndex = 39;
        this.comboSite.SelectionChangeCommitted += new System.EventHandler(this.comboSite_SelectionChangeCommitted);
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(11, 307);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(106, 14);
        this.labelSite.TabIndex = 38;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboBillingType
        //
        this.comboBillingType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboBillingType.FormattingEnabled = true;
        this.comboBillingType.Location = new System.Drawing.Point(118, 280);
        this.comboBillingType.Name = "comboBillingType";
        this.comboBillingType.Size = new System.Drawing.Size(138, 21);
        this.comboBillingType.TabIndex = 28;
        this.comboBillingType.SelectionChangeCommitted += new System.EventHandler(this.comboBillingType_SelectionChangeCommitted);
        //
        // textBirthdate
        //
        this.textBirthdate.Location = new System.Drawing.Point(166, 214);
        this.textBirthdate.Name = "textBirthdate";
        this.textBirthdate.Size = new System.Drawing.Size(90, 20);
        this.textBirthdate.TabIndex = 9;
        this.textBirthdate.TextChanged += new System.EventHandler(this.textBirthdate_TextChanged);
        this.textBirthdate.Enter += new System.EventHandler(this.textBox_Enter);
        this.textBirthdate.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBirthdate_KeyDown);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(11, 218);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(156, 12);
        this.label2.TabIndex = 27;
        this.label2.Text = "Birthdate";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowArchived
        //
        this.checkShowArchived.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowArchived.Location = new System.Drawing.Point(11, 366);
        this.checkShowArchived.Name = "checkShowArchived";
        this.checkShowArchived.Size = new System.Drawing.Size(245, 17);
        this.checkShowArchived.TabIndex = 25;
        this.checkShowArchived.Text = "Show Archived/Deceased";
        this.checkShowArchived.CheckedChanged += new System.EventHandler(this.checkShowArchived_CheckedChanged);
        //
        // textChartNumber
        //
        this.textChartNumber.Location = new System.Drawing.Point(166, 194);
        this.textChartNumber.Name = "textChartNumber";
        this.textChartNumber.Size = new System.Drawing.Size(90, 20);
        this.textChartNumber.TabIndex = 8;
        this.textChartNumber.TextChanged += new System.EventHandler(this.textChartNumber_TextChanged);
        this.textChartNumber.Enter += new System.EventHandler(this.textBox_Enter);
        this.textChartNumber.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textChartNumber_KeyDown);
        //
        // textSSN
        //
        this.textSSN.Location = new System.Drawing.Point(166, 154);
        this.textSSN.Name = "textSSN";
        this.textSSN.Size = new System.Drawing.Size(90, 20);
        this.textSSN.TabIndex = 6;
        this.textSSN.TextChanged += new System.EventHandler(this.textSSN_TextChanged);
        this.textSSN.Enter += new System.EventHandler(this.textBox_Enter);
        this.textSSN.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textSSN_KeyDown);
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(11, 158);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(155, 12);
        this.label12.TabIndex = 24;
        this.label12.Text = "SSN";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(11, 281);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(107, 18);
        this.label11.TabIndex = 21;
        this.label11.Text = "Billing Type";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(11, 198);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(156, 12);
        this.label10.TabIndex = 20;
        this.label10.Text = "Chart Number";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatNum
        //
        this.textPatNum.Location = new System.Drawing.Point(166, 174);
        this.textPatNum.Name = "textPatNum";
        this.textPatNum.Size = new System.Drawing.Size(90, 20);
        this.textPatNum.TabIndex = 7;
        this.textPatNum.TextChanged += new System.EventHandler(this.textPatNum_TextChanged);
        this.textPatNum.Enter += new System.EventHandler(this.textBox_Enter);
        this.textPatNum.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textPatNum_KeyDown);
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(11, 178);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(156, 12);
        this.label9.TabIndex = 18;
        this.label9.Text = "Patient Number";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(166, 134);
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(90, 20);
        this.textState.TabIndex = 5;
        this.textState.TextChanged += new System.EventHandler(this.textState_TextChanged);
        this.textState.Enter += new System.EventHandler(this.textBox_Enter);
        this.textState.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textState_KeyDown);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(11, 138);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(154, 12);
        this.label8.TabIndex = 16;
        this.label8.Text = "State";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(166, 114);
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(90, 20);
        this.textCity.TabIndex = 4;
        this.textCity.TextChanged += new System.EventHandler(this.textCity_TextChanged);
        this.textCity.Enter += new System.EventHandler(this.textBox_Enter);
        this.textCity.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textCity_KeyDown);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(11, 116);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(152, 14);
        this.label7.TabIndex = 14;
        this.label7.Text = "City";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkGuarantors
        //
        this.checkGuarantors.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkGuarantors.Location = new System.Drawing.Point(11, 330);
        this.checkGuarantors.Name = "checkGuarantors";
        this.checkGuarantors.Size = new System.Drawing.Size(245, 17);
        this.checkGuarantors.TabIndex = 12;
        this.checkGuarantors.Text = "Guarantors Only";
        this.checkGuarantors.CheckedChanged += new System.EventHandler(this.checkGuarantors_CheckedChanged);
        //
        // checkHideInactive
        //
        this.checkHideInactive.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHideInactive.Location = new System.Drawing.Point(11, 348);
        this.checkHideInactive.Name = "checkHideInactive";
        this.checkHideInactive.Size = new System.Drawing.Size(245, 17);
        this.checkHideInactive.TabIndex = 44;
        this.checkHideInactive.Text = "Hide Inactive Patients";
        this.checkHideInactive.CheckedChanged += new System.EventHandler(this.checkHideInactive_CheckedChanged);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(11, 14);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(245, 16);
        this.label6.TabIndex = 10;
        this.label6.Text = "Hint: enter values in multiple boxes.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(166, 94);
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(90, 20);
        this.textAddress.TabIndex = 3;
        this.textAddress.TextChanged += new System.EventHandler(this.textAddress_TextChanged);
        this.textAddress.Enter += new System.EventHandler(this.textBox_Enter);
        this.textAddress.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textAddress_KeyDown);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(11, 97);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(154, 12);
        this.label5.TabIndex = 9;
        this.label5.Text = "Address";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHmPhone
        //
        this.textHmPhone.Location = new System.Drawing.Point(166, 74);
        this.textHmPhone.Name = "textHmPhone";
        this.textHmPhone.Size = new System.Drawing.Size(90, 20);
        this.textHmPhone.TabIndex = 2;
        this.textHmPhone.TextChanged += new System.EventHandler(this.textHmPhone_TextChanged);
        this.textHmPhone.Enter += new System.EventHandler(this.textBox_Enter);
        this.textHmPhone.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textHmPhone_KeyDown);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(11, 76);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(155, 16);
        this.label4.TabIndex = 7;
        this.label4.Text = "Phone (any)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFName
        //
        this.textFName.Location = new System.Drawing.Point(166, 54);
        this.textFName.Name = "textFName";
        this.textFName.Size = new System.Drawing.Size(90, 20);
        this.textFName.TabIndex = 1;
        this.textFName.TextChanged += new System.EventHandler(this.textFName_TextChanged);
        this.textFName.Enter += new System.EventHandler(this.textBox_Enter);
        this.textFName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textFName_KeyDown);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(11, 58);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(154, 12);
        this.label3.TabIndex = 5;
        this.label3.Text = "First Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.checkRefresh);
        this.groupBox1.Controls.Add(this.butGetAll);
        this.groupBox1.Controls.Add(this.butSearch);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(674, 506);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(262, 76);
        this.groupBox1.TabIndex = 7;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Search";
        //
        // checkRefresh
        //
        this.checkRefresh.Location = new System.Drawing.Point(11, 52);
        this.checkRefresh.Name = "checkRefresh";
        this.checkRefresh.Size = new System.Drawing.Size(245, 18);
        this.checkRefresh.TabIndex = 71;
        this.checkRefresh.Text = "Refresh while typing";
        this.checkRefresh.UseVisualStyleBackColor = true;
        this.checkRefresh.Click += new System.EventHandler(this.checkRefresh_Click);
        //
        // butGetAll
        //
        this.butGetAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGetAll.setAutosize(true);
        this.butGetAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGetAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGetAll.setCornerRadius(4F);
        this.butGetAll.Location = new System.Drawing.Point(148, 21);
        this.butGetAll.Name = "butGetAll";
        this.butGetAll.Size = new System.Drawing.Size(75, 23);
        this.butGetAll.TabIndex = 35;
        this.butGetAll.Text = "Get All";
        this.butGetAll.Click += new System.EventHandler(this.butGetAll_Click);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(42, 21);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 23);
        this.butSearch.TabIndex = 33;
        this.butSearch.Text = "&Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(3, 2);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(665, 675);
        this.gridMain.TabIndex = 9;
        this.gridMain.setTitle("Select Patient");
        this.gridMain.setTranslationName("FormPatientSelect");
        this.gridMain.setWrapText(false);
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
        this.gridMain.KeyDown += new System.Windows.Forms.KeyEventHandler(this.gridMain_KeyDown);
        //
        // contrKeyboard1
        //
        this.contrKeyboard1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.contrKeyboard1.Location = new System.Drawing.Point(669, 0);
        this.contrKeyboard1.Name = "contrKeyboard1";
        this.contrKeyboard1.Size = new System.Drawing.Size(275, 100);
        this.contrKeyboard1.TabIndex = 10;
        this.contrKeyboard1.KeyClick = __MultiKeyboardClickEventHandler.combine(this.contrKeyboard1.KeyClick,new OpenDental.User_Controls.KeyboardClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, KeyboardClickEventArgs e) throws Exception {
                this.contrKeyboard1_KeyClick(sender, e);
            }

            public List<OpenDental.User_Controls.KeyboardClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.User_Controls.KeyboardClickEventHandler> ret = new ArrayList<OpenDental.User_Controls.KeyboardClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.contrKeyboard1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.contrKeyboard1_MouseDown);
        //
        // FormPatientSelect
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(944, 684);
        this.Controls.Add(this.contrKeyboard1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.groupAddPt);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.Name = "FormPatientSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Patient";
        this.Load += new System.EventHandler(this.FormSelectPatient_Load);
        this.groupAddPt.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private Patient preselectedPatient;
    public Patient getPreselectedPatient() throws Exception {
        return preselectedPatient;
    }

    public void preselectPatient(Patient value) throws Exception {
        preselectedPatient = value;
        textLName.Text = value.LName;
        textFName.Text = value.FName;
        textCity.Text = value.City;
        butSearch_Click(this, EventArgs.Empty);
    }

    /**
    * 
    */
    public void formSelectPatient_Load(Object sender, System.EventArgs e) throws Exception {
        if (SelectionModeOnly)
        {
            groupAddPt.Visible = false;
        }
         
        //Cannot add new patients from OD select patient interface.  Patient must be added from HL7 message.
        if (HL7Defs.isExistingHL7Enabled())
        {
            HL7Def def = HL7Defs.getOneDeepEnabled();
            if (def.ShowDemographics != HL7ShowDemographics.ChangeAndAdd)
            {
                groupAddPt.Visible = false;
            }
             
        }
        else
        {
            if (Programs.usingEcwTightOrFullMode())
            {
                groupAddPt.Visible = false;
            }
             
        } 
        comboBillingType.Items.Add(Lan.g(this,"All"));
        comboBillingType.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            comboBillingType.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
        }
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            comboSite.Visible = false;
            labelSite.Visible = false;
        }
        else
        {
            comboSite.Items.Add(Lan.g(this,"All"));
            comboSite.SelectedIndex = 0;
            for (int i = 0;i < SiteC.getList().Length;i++)
            {
                comboSite.Items.Add(SiteC.getList()[i].Description);
            }
        } 
        fillSearchOption();
        setGridCols();
        if (InitialPatNum != 0)
        {
            Patient iPatient = Patients.getLim(InitialPatNum);
            textLName.Text = iPatient.LName;
            fillGrid(false);
            /*if(grid2.CurrentRowIndex>-1){
            					grid2.UnSelect(grid2.CurrentRowIndex);
            				}
            				for(int i=0;i<PtDataTable.Rows.Count;i++){
            					if(PIn.PInt(PtDataTable.Rows[i][0].ToString())==InitialPatNum){
            						grid2.CurrentRowIndex=i;
            						grid2.Select(i);
            						break;
            					}
            				}*/
            gridMain.setSelected(false);
            for (int i = 0;i < PtDataTable.Rows.Count;i++)
            {
                if (PIn.Long(PtDataTable.Rows[i][0].ToString()) == InitialPatNum)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
            return ;
        }
         
        if (checkRefresh.Checked)
        {
            fillGrid(true);
        }
         
    }

    private void fillSearchOption() throws Exception {
        if (PrefC.getBool(PrefName.PatientSelectUsesSearchButton))
        {
            checkRefresh.Checked = false;
        }
        else
        {
            checkRefresh.Checked = true;
        } 
    }

    private void setGridCols() throws Exception {
        //This pattern is wrong.
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        fields = DisplayFields.getForCategory(DisplayFieldCategory.PatientSelect);
        for (int i = 0;i < fields.Count;i++)
        {
            if (StringSupport.equals(fields[i].Description, ""))
            {
                col = new ODGridColumn(fields[i].InternalName, fields[i].ColumnWidth);
            }
            else
            {
                col = new ODGridColumn(fields[i].Description, fields[i].ColumnWidth);
            } 
            gridMain.getColumns().add(col);
        }
        gridMain.endUpdate();
    }

    private void textBox_Enter(Object sender, EventArgs e) throws Exception {
        selectedTxtBox = (TextBox)sender;
    }

    private void textBox_Leave(Object sender, EventArgs e) throws Exception {
    }

    //selectedTxtBox=null;
    private void contrKeyboard1_MouseDown(Object sender, MouseEventArgs e) throws Exception {
    }

    //this happens before contrKeyboard gets focus
    /*foreach(Control control in this.Controls) {
    				if(control.){
    					if(control.GetType()==typeof(TextBox)) {
    						selectedTxtBox=(TextBox)control;
    					}
    				}
    			}*/
    private void contrKeyboard1_KeyClick(Object sender, OpenDental.User_Controls.KeyboardClickEventArgs e) throws Exception {
        //MessageBox.Show(contrKeyboard1.CanFocus.ToString());
        //get the control with focus
        /*Control ctrl=null;
        			foreach(Control control in this.Controls) {
        				if(control.Focused) {
        					ctrl=control;
        				}
        			}*/
        if (selectedTxtBox == null)
        {
            return ;
        }
         
        //if(ctrl.GetType()!=typeof(TextBox)) {
        //	return;
        //}
        //this is all quick and dirty, totally ignoring the cursor position
        if (e.getKeyData() == Keys.Back)
        {
            if (selectedTxtBox.Text.Length > 0)
            {
                selectedTxtBox.Text = selectedTxtBox.Text.Remove(selectedTxtBox.Text.Length - 1);
            }
             
        }
        else
        {
            if (selectedTxtBox.Text.Length == 0)
            {
                selectedTxtBox.Text = selectedTxtBox.Text + e.getTxt();
            }
            else
            {
                selectedTxtBox.Text = selectedTxtBox.Text + e.getTxt().ToLower();
            } 
        } 
        selectedTxtBox.Focus();
        selectedTxtBox.Select(selectedTxtBox.Text.Length, 0);
    }

    //the end
    private void textLName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textFName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textHmPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textWkPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textAddress_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textCity_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textState_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textSSN_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textPatNum_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textChartNumber_TextChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textBirthdate_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textSubscriberID_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textEmail_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void comboBillingType_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void comboSite_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void checkGuarantors_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void checkHideInactive_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        onDataEntered();
    }

    private void checkShowArchived_CheckedChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textLName_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textFName_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void gridMain_KeyDown(Object sender, KeyEventArgs e) throws Exception {
    }

    private void textHmPhone_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textAddress_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textCity_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textState_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textSSN_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textPatNum_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textChartNumber_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textBirthdate_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void checkRefresh_Click(Object sender, EventArgs e) throws Exception {
        Prefs.UpdateBool(PrefName.PatientSelectUsesSearchButton, !checkRefresh.Checked);
        Cache.refresh(InvalidType.Prefs);
        //simply not important enough to send an update to the other computers.
        fillSearchOption();
        if (checkRefresh.Checked)
        {
            fillGrid(true);
        }
         
    }

    private void butSearch_Click(Object sender, System.EventArgs e) throws Exception {
        fillGrid(true);
    }

    private void butGetAll_Click(Object sender, EventArgs e) throws Exception {
        fillGrid(false);
    }

    private void onDataEntered() throws Exception {
        if (checkRefresh.Checked)
        {
            fillGrid(true);
        }
         
    }

    private void fillGrid(boolean limit) throws Exception {
        long billingType = 0;
        if (comboBillingType.SelectedIndex != 0)
        {
            billingType = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][comboBillingType.SelectedIndex - 1].DefNum;
        }
         
        long siteNum = 0;
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth) && comboSite.SelectedIndex != 0)
        {
            siteNum = SiteC.getList()[comboSite.SelectedIndex - 1].SiteNum;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate.Text);
        //this will frequently be minval.
        long clinicNum = 0;
        //all clinics
        if (Security.getCurUser().ClinicNum != 0 && Security.getCurUser().ClinicIsRestricted)
        {
            clinicNum = Security.getCurUser().ClinicNum;
        }
         
        //checkShowProspectiveOnly.Checked,
        PtDataTable = Patients.GetPtDataTable(limit, textLName.Text, textFName.Text, textHmPhone.Text, textAddress.Text, checkHideInactive.Checked, textCity.Text, textState.Text, textSSN.Text, textPatNum.Text, textChartNumber.Text, billingType, checkGuarantors.Checked, checkShowArchived.Checked, clinicNum, birthdate, siteNum, textSubscriberID.Text, textEmail.Text);
        gridMain.beginUpdate();
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < PtDataTable.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int f = 0;f < fields.Count;f++)
            {
                InternalName __dummyScrutVar0 = fields[f].InternalName;
                if (__dummyScrutVar0.equals("LastName"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["LName"].ToString());
                }
                else if (__dummyScrutVar0.equals("First Name"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["FName"].ToString());
                }
                else if (__dummyScrutVar0.equals("MI"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["MiddleI"].ToString());
                }
                else if (__dummyScrutVar0.equals("Pref Name"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["Preferred"].ToString());
                }
                else if (__dummyScrutVar0.equals("Age"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["age"].ToString());
                }
                else if (__dummyScrutVar0.equals("SSN"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["SSN"].ToString());
                }
                else if (__dummyScrutVar0.equals("Hm Phone"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["HmPhone"].ToString());
                }
                else if (__dummyScrutVar0.equals("Wk Phone"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["WkPhone"].ToString());
                }
                else if (__dummyScrutVar0.equals("PatNum"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["PatNum"].ToString());
                }
                else if (__dummyScrutVar0.equals("ChartNum"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["ChartNumber"].ToString());
                }
                else if (__dummyScrutVar0.equals("Address"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["Address"].ToString());
                }
                else if (__dummyScrutVar0.equals("Status"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["PatStatus"].ToString());
                }
                else if (__dummyScrutVar0.equals("Bill Type"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["BillingType"].ToString());
                }
                else if (__dummyScrutVar0.equals("City"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["City"].ToString());
                }
                else if (__dummyScrutVar0.equals("State"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["State"].ToString());
                }
                else if (__dummyScrutVar0.equals("Pri Prov"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["PriProv"].ToString());
                }
                else if (__dummyScrutVar0.equals("Birthdate"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["Birthdate"].ToString());
                }
                else if (__dummyScrutVar0.equals("Site"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["site"].ToString());
                }
                else if (__dummyScrutVar0.equals("Email"))
                {
                    row.getCells().Add(PtDataTable.Rows[i]["Email"].ToString());
                }
                else if (__dummyScrutVar0.equals("OtherPhone"))
                {
                    //will only be available if OD HQ
                    row.getCells().Add(PtDataTable.Rows[i]["OtherPhone"].ToString());
                }
                                    
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setSelected(0,true);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        patSelected();
    }

    private void onArrowsUpDown(System.Windows.Forms.KeyEventArgs e) throws Exception {
        //I don't know if this is doing anything.
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(this, e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void formSelectPatient_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        onArrowsUpDown(e);
    }

    /**
    * Remember, this button is not even visible if SelectionModeOnly.
    */
    private void butAddPt_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textLName.Text, "") && StringSupport.equals(textFName.Text, "") && StringSupport.equals(textChartNumber.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Not allowed to add a new patient until you have done a search to see if that patient already exists. Hint: just type a few letters into the Last Name box above."));
            return ;
        }
         
        Patient PatCur = new Patient();
        if (textLName.Text.Length > 1)
        {
            //eg Sp
            PatCur.LName = textLName.Text.Substring(0, 1).ToUpper() + textLName.Text.Substring(1);
        }
         
        if (textFName.Text.Length > 1)
        {
            PatCur.FName = textFName.Text.Substring(0, 1).ToUpper() + textFName.Text.Substring(1);
        }
         
        PatCur.PatStatus = PatientStatus.Patient;
        PatCur.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        PatCur.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            PatCur.Gender = PatientGender.Unknown;
        }
         
        PatCur.ClinicNum = Security.getCurUser().ClinicNum;
        Patients.insert(PatCur,false);
        CustReference custRef = new CustReference();
        custRef.PatNum = PatCur.PatNum;
        CustReferences.insert(custRef);
        Patient PatOld = PatCur.copy();
        PatCur.Guarantor = PatCur.PatNum;
        Patients.update(PatCur,PatOld);
        Family FamCur = Patients.getFamily(PatCur.PatNum);
        FormPatientEdit FormPE = new FormPatientEdit(PatCur,FamCur);
        FormPE.IsNew = true;
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.OK)
        {
            NewPatientAdded = true;
            SelectedPatNum = PatCur.PatNum;
            DialogResult = DialogResult.OK;
        }
         
    }

    private void butAddAll_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textLName.Text, "") && StringSupport.equals(textFName.Text, "") && StringSupport.equals(textChartNumber.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Not allowed to add a new patient until you have done a search to see if that patient already exists. Hint: just type a few letters into the Last Name box above."));
            return ;
        }
         
        FormPatientAddAll FormP = new FormPatientAddAll();
        if (textLName.Text.Length > 1)
        {
            //eg Sp
            FormP.LName = textLName.Text.Substring(0, 1).ToUpper() + textLName.Text.Substring(1);
        }
         
        if (textFName.Text.Length > 1)
        {
            FormP.FName = textFName.Text.Substring(0, 1).ToUpper() + textFName.Text.Substring(1);
        }
         
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        NewPatientAdded = true;
        SelectedPatNum = FormP.SelectedPatNum;
        DialogResult = DialogResult.OK;
    }

    /*
    			Patient PatCur=new Patient();
    			if(textLName.Text.Length>1){//eg Sp
    				PatCur.LName=textLName.Text.Substring(0,1).ToUpper()+textLName.Text.Substring(1);
    			}
    			if(textFName.Text.Length>1){
    				PatCur.FName=textFName.Text.Substring(0,1).ToUpper()+textFName.Text.Substring(1);
    			}
    			PatCur.PatStatus=PatientStatus.Patient;
    			Patients.Insert(PatCur,false);
    			Patient PatOld=PatCur.Clone();
    			PatCur.Guarantor=PatCur.PatNum;
    			Patients.Update(PatCur,PatOld);
    			Family FamCur=Patients.GetFamily(PatCur.PatNum);
    			FormPatientEdit FormPE=new FormPatientEdit(PatCur,FamCur);
    			FormPE.IsNew=true;
    			FormPE.ShowDialog();
    			if(FormPE.DialogResult==DialogResult.OK){
    				NewPatientAdded=true;
    				SelectedPatNum=PatCur.PatNum;
    				DialogResult=DialogResult.OK;
    			}*/
    private void patSelected() throws Exception {
        //SelectedPatNum=PIn.PInt(PtDataTable.Rows[grid2.CurrentRowIndex][0].ToString());
        SelectedPatNum = PIn.Long(PtDataTable.Rows[gridMain.getSelectedIndex()][0].ToString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        //if(grid2.CurrentRowIndex!=-1){
        patSelected();
    }

    //}
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


