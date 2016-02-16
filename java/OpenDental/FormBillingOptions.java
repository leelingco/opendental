//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:43 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormAging;
import OpenDental.FormBillingDefaults;
import OpenDental.FormBillingOptions;
import OpenDental.FormDunningEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Dunning;
import OpenDentBusiness.Dunnings;
import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.InstallmentPlans;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.PatAging;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Statements;
import OpenDentBusiness.YN;

/**
* 
*/
public class FormBillingOptions  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butCancel;
    //private FormQuery FormQuery2;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSaveDefault;
    private OpenDental.ValidDouble textExcludeLessThan;
    private System.Windows.Forms.CheckBox checkExcludeInactive = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridDun;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.CheckBox checkBadAddress = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkExcludeNegative = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIncludeChanged = new System.Windows.Forms.CheckBox();
    private OpenDental.ValidDate textLastStatement;
    private OpenDental.UI.Button butCreate;
    private CheckBox checkExcludeInsPending = new CheckBox();
    private GroupBox groupDateRange = new GroupBox();
    private OpenDental.ValidDate textDateStart;
    private Label labelStartDate = new Label();
    private Label labelEndDate = new Label();
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.UI.Button but45days;
    private OpenDental.UI.Button but90days;
    private OpenDental.UI.Button butDatesAll;
    private CheckBox checkIntermingled = new CheckBox();
    private OpenDental.UI.Button butDefaults;
    private OpenDental.UI.Button but30days;
    private ComboBox comboAge = new ComboBox();
    private Label label6 = new Label();
    private Label label7 = new Label();
    private ListBox listBillType = new ListBox();
    private Label labelSaveDefaults = new Label();
    private OpenDental.UI.Button butUndo;
    private CheckBox checkIgnoreInPerson = new CheckBox();
    private CheckBox checkExcludeIfProcs = new CheckBox();
    private Label labelClinic = new Label();
    private ComboBox comboClinic = new ComboBox();
    private Dunning[] dunningList = new Dunning[]();
    public long ClinicNum = new long();
    /**
    * 
    */
    public FormBillingOptions() throws Exception {
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormBillingOptions.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butCreate = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butSaveDefault = new OpenDental.UI.Button();
        this.textExcludeLessThan = new OpenDental.ValidDouble();
        this.checkExcludeInactive = new System.Windows.Forms.CheckBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.checkExcludeIfProcs = new System.Windows.Forms.CheckBox();
        this.checkIgnoreInPerson = new System.Windows.Forms.CheckBox();
        this.labelSaveDefaults = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.comboAge = new System.Windows.Forms.ComboBox();
        this.checkExcludeInsPending = new System.Windows.Forms.CheckBox();
        this.checkIncludeChanged = new System.Windows.Forms.CheckBox();
        this.textLastStatement = new OpenDental.ValidDate();
        this.label5 = new System.Windows.Forms.Label();
        this.checkExcludeNegative = new System.Windows.Forms.CheckBox();
        this.checkBadAddress = new System.Windows.Forms.CheckBox();
        this.listBillType = new System.Windows.Forms.ListBox();
        this.gridDun = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.groupDateRange = new System.Windows.Forms.GroupBox();
        this.but30days = new OpenDental.UI.Button();
        this.textDateStart = new OpenDental.ValidDate();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.labelEndDate = new System.Windows.Forms.Label();
        this.textDateEnd = new OpenDental.ValidDate();
        this.but45days = new OpenDental.UI.Button();
        this.but90days = new OpenDental.UI.Button();
        this.butDatesAll = new OpenDental.UI.Button();
        this.checkIntermingled = new System.Windows.Forms.CheckBox();
        this.butDefaults = new OpenDental.UI.Button();
        this.butUndo = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.groupDateRange.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(806, 631);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(79, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butCreate
        //
        this.butCreate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCreate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCreate.setAutosize(true);
        this.butCreate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCreate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCreate.setCornerRadius(4F);
        this.butCreate.Location = new System.Drawing.Point(693, 631);
        this.butCreate.Name = "butCreate";
        this.butCreate.Size = new System.Drawing.Size(92, 24);
        this.butCreate.TabIndex = 3;
        this.butCreate.Text = "Create &List";
        this.butCreate.Click += new System.EventHandler(this.butCreate_Click);
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(5, 206);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(192, 16);
        this.label1.TabIndex = 18;
        this.label1.Text = "Exclude if Balance is less than";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSaveDefault
        //
        this.butSaveDefault.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSaveDefault.setAutosize(true);
        this.butSaveDefault.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSaveDefault.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSaveDefault.setCornerRadius(4F);
        this.butSaveDefault.Location = new System.Drawing.Point(168, 561);
        this.butSaveDefault.Name = "butSaveDefault";
        this.butSaveDefault.Size = new System.Drawing.Size(108, 24);
        this.butSaveDefault.TabIndex = 20;
        this.butSaveDefault.Text = "&Save As Default";
        this.butSaveDefault.Click += new System.EventHandler(this.butSaveDefault_Click);
        //
        // textExcludeLessThan
        //
        this.textExcludeLessThan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textExcludeLessThan.Location = new System.Drawing.Point(199, 205);
        this.textExcludeLessThan.Name = "textExcludeLessThan";
        this.textExcludeLessThan.Size = new System.Drawing.Size(77, 20);
        this.textExcludeLessThan.TabIndex = 22;
        //
        // checkExcludeInactive
        //
        this.checkExcludeInactive.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkExcludeInactive.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkExcludeInactive.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExcludeInactive.Location = new System.Drawing.Point(45, 122);
        this.checkExcludeInactive.Name = "checkExcludeInactive";
        this.checkExcludeInactive.Size = new System.Drawing.Size(231, 18);
        this.checkExcludeInactive.TabIndex = 23;
        this.checkExcludeInactive.Text = "Exclude inactive families";
        this.checkExcludeInactive.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.labelClinic);
        this.groupBox2.Controls.Add(this.comboClinic);
        this.groupBox2.Controls.Add(this.checkExcludeIfProcs);
        this.groupBox2.Controls.Add(this.checkIgnoreInPerson);
        this.groupBox2.Controls.Add(this.labelSaveDefaults);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.comboAge);
        this.groupBox2.Controls.Add(this.checkExcludeInsPending);
        this.groupBox2.Controls.Add(this.checkIncludeChanged);
        this.groupBox2.Controls.Add(this.textLastStatement);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.checkExcludeNegative);
        this.groupBox2.Controls.Add(this.checkBadAddress);
        this.groupBox2.Controls.Add(this.checkExcludeInactive);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.textExcludeLessThan);
        this.groupBox2.Controls.Add(this.butSaveDefault);
        this.groupBox2.Controls.Add(this.listBillType);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(7, 12);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(283, 609);
        this.groupBox2.TabIndex = 24;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Filter";
        //
        // labelClinic
        //
        this.labelClinic.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelClinic.Location = new System.Drawing.Point(3, 492);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(128, 16);
        this.labelClinic.TabIndex = 250;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelClinic.Visible = false;
        //
        // comboClinic
        //
        this.comboClinic.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.FormattingEnabled = true;
        this.comboClinic.Location = new System.Drawing.Point(132, 491);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(145, 21);
        this.comboClinic.TabIndex = 249;
        this.comboClinic.Visible = false;
        //
        // checkExcludeIfProcs
        //
        this.checkExcludeIfProcs.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkExcludeIfProcs.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkExcludeIfProcs.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExcludeIfProcs.Location = new System.Drawing.Point(45, 182);
        this.checkExcludeIfProcs.Name = "checkExcludeIfProcs";
        this.checkExcludeIfProcs.Size = new System.Drawing.Size(231, 18);
        this.checkExcludeIfProcs.TabIndex = 248;
        this.checkExcludeIfProcs.Text = "Exclude if unsent procedures";
        this.checkExcludeIfProcs.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIgnoreInPerson
        //
        this.checkIgnoreInPerson.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkIgnoreInPerson.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIgnoreInPerson.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIgnoreInPerson.Location = new System.Drawing.Point(2, 233);
        this.checkIgnoreInPerson.Name = "checkIgnoreInPerson";
        this.checkIgnoreInPerson.Size = new System.Drawing.Size(274, 18);
        this.checkIgnoreInPerson.TabIndex = 247;
        this.checkIgnoreInPerson.Text = "Ignore walkout (InPerson) statements";
        this.checkIgnoreInPerson.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelSaveDefaults
        //
        this.labelSaveDefaults.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelSaveDefaults.Location = new System.Drawing.Point(6, 590);
        this.labelSaveDefaults.Name = "labelSaveDefaults";
        this.labelSaveDefaults.Size = new System.Drawing.Size(270, 16);
        this.labelSaveDefaults.TabIndex = 246;
        this.labelSaveDefaults.Text = "(except the date at the top)";
        this.labelSaveDefaults.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label7.Location = new System.Drawing.Point(5, 258);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(111, 16);
        this.label7.TabIndex = 245;
        this.label7.Text = "Billing Types";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label6.Location = new System.Drawing.Point(3, 75);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(128, 16);
        this.label6.TabIndex = 243;
        this.label6.Text = "Age of Account";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboAge
        //
        this.comboAge.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.comboAge.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAge.FormattingEnabled = true;
        this.comboAge.Location = new System.Drawing.Point(132, 73);
        this.comboAge.Name = "comboAge";
        this.comboAge.Size = new System.Drawing.Size(145, 21);
        this.comboAge.TabIndex = 242;
        //
        // checkExcludeInsPending
        //
        this.checkExcludeInsPending.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkExcludeInsPending.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkExcludeInsPending.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExcludeInsPending.Location = new System.Drawing.Point(45, 162);
        this.checkExcludeInsPending.Name = "checkExcludeInsPending";
        this.checkExcludeInsPending.Size = new System.Drawing.Size(231, 18);
        this.checkExcludeInsPending.TabIndex = 27;
        this.checkExcludeInsPending.Text = "Exclude if insurance pending";
        this.checkExcludeInsPending.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIncludeChanged
        //
        this.checkIncludeChanged.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkIncludeChanged.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludeChanged.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIncludeChanged.Location = new System.Drawing.Point(3, 39);
        this.checkIncludeChanged.Name = "checkIncludeChanged";
        this.checkIncludeChanged.Size = new System.Drawing.Size(273, 28);
        this.checkIncludeChanged.TabIndex = 26;
        this.checkIncludeChanged.Text = "Include any accounts with insurance payments or procedures since the last bill";
        this.checkIncludeChanged.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLastStatement
        //
        this.textLastStatement.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textLastStatement.Location = new System.Drawing.Point(183, 13);
        this.textLastStatement.Name = "textLastStatement";
        this.textLastStatement.Size = new System.Drawing.Size(94, 20);
        this.textLastStatement.TabIndex = 25;
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label5.Location = new System.Drawing.Point(6, 15);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(176, 16);
        this.label5.TabIndex = 24;
        this.label5.Text = "Include anyone not billed since";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkExcludeNegative
        //
        this.checkExcludeNegative.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkExcludeNegative.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkExcludeNegative.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExcludeNegative.Location = new System.Drawing.Point(45, 142);
        this.checkExcludeNegative.Name = "checkExcludeNegative";
        this.checkExcludeNegative.Size = new System.Drawing.Size(231, 18);
        this.checkExcludeNegative.TabIndex = 17;
        this.checkExcludeNegative.Text = "Exclude negative balances (credits)";
        this.checkExcludeNegative.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkBadAddress
        //
        this.checkBadAddress.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkBadAddress.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBadAddress.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkBadAddress.Location = new System.Drawing.Point(45, 102);
        this.checkBadAddress.Name = "checkBadAddress";
        this.checkBadAddress.Size = new System.Drawing.Size(231, 18);
        this.checkBadAddress.TabIndex = 16;
        this.checkBadAddress.Text = "Exclude bad addresses (no zipcode)";
        this.checkBadAddress.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listBillType
        //
        this.listBillType.Location = new System.Drawing.Point(118, 258);
        this.listBillType.Name = "listBillType";
        this.listBillType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listBillType.Size = new System.Drawing.Size(158, 225);
        this.listBillType.TabIndex = 2;
        //
        // gridDun
        //
        this.gridDun.setHScrollVisible(false);
        this.gridDun.Location = new System.Drawing.Point(331, 31);
        this.gridDun.Name = "gridDun";
        this.gridDun.setScrollValue(0);
        this.gridDun.Size = new System.Drawing.Size(561, 366);
        this.gridDun.TabIndex = 0;
        this.gridDun.setTitle("Dunning Messages");
        this.gridDun.setTranslationName("TableBillingMessages");
        this.gridDun.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridDun.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridDun_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(768, 403);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(124, 24);
        this.butAdd.TabIndex = 5;
        this.butAdd.Text = "Add Dunning Msg";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(328, 9);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(564, 16);
        this.label3.TabIndex = 25;
        this.label3.Text = "Items higher in the list are more general.  Items lower in the list take preceden" + "ce .";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(328, 498);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(564, 16);
        this.label4.TabIndex = 26;
        this.label4.Text = "General Message (in addition to any dunning messages and appointment reminders, [" + "InstallmentPlanTerms] allowed)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(331, 518);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Statement);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(561, 102);
        this.textNote.TabIndex = 28;
        this.textNote.Text = "";
        //
        // groupDateRange
        //
        this.groupDateRange.Controls.Add(this.but30days);
        this.groupDateRange.Controls.Add(this.textDateStart);
        this.groupDateRange.Controls.Add(this.labelStartDate);
        this.groupDateRange.Controls.Add(this.labelEndDate);
        this.groupDateRange.Controls.Add(this.textDateEnd);
        this.groupDateRange.Controls.Add(this.but45days);
        this.groupDateRange.Controls.Add(this.but90days);
        this.groupDateRange.Controls.Add(this.butDatesAll);
        this.groupDateRange.Location = new System.Drawing.Point(331, 403);
        this.groupDateRange.Name = "groupDateRange";
        this.groupDateRange.Size = new System.Drawing.Size(319, 69);
        this.groupDateRange.TabIndex = 237;
        this.groupDateRange.TabStop = false;
        this.groupDateRange.Text = "Date Range";
        //
        // but30days
        //
        this.but30days.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but30days.setAutosize(true);
        this.but30days.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but30days.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but30days.setCornerRadius(4F);
        this.but30days.Location = new System.Drawing.Point(154, 13);
        this.but30days.Name = "but30days";
        this.but30days.Size = new System.Drawing.Size(77, 24);
        this.but30days.TabIndex = 242;
        this.but30days.Text = "Last 30 Days";
        this.but30days.Click += new System.EventHandler(this.but30days_Click);
        //
        // textDateStart
        //
        this.textDateStart.BackColor = System.Drawing.SystemColors.Window;
        this.textDateStart.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textDateStart.Location = new System.Drawing.Point(75, 16);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 223;
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(6, 19);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(69, 14);
        this.labelStartDate.TabIndex = 221;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelEndDate
        //
        this.labelEndDate.Location = new System.Drawing.Point(6, 42);
        this.labelEndDate.Name = "labelEndDate";
        this.labelEndDate.Size = new System.Drawing.Size(69, 14);
        this.labelEndDate.TabIndex = 222;
        this.labelEndDate.Text = "End Date";
        this.labelEndDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(75, 39);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 224;
        //
        // but45days
        //
        this.but45days.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but45days.setAutosize(true);
        this.but45days.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but45days.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but45days.setCornerRadius(4F);
        this.but45days.Location = new System.Drawing.Point(154, 37);
        this.but45days.Name = "but45days";
        this.but45days.Size = new System.Drawing.Size(77, 24);
        this.but45days.TabIndex = 226;
        this.but45days.Text = "Last 45 Days";
        this.but45days.Click += new System.EventHandler(this.but45days_Click);
        //
        // but90days
        //
        this.but90days.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but90days.setAutosize(true);
        this.but90days.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but90days.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but90days.setCornerRadius(4F);
        this.but90days.Location = new System.Drawing.Point(233, 37);
        this.but90days.Name = "but90days";
        this.but90days.Size = new System.Drawing.Size(77, 24);
        this.but90days.TabIndex = 227;
        this.but90days.Text = "Last 90 Days";
        this.but90days.Click += new System.EventHandler(this.but90days_Click);
        //
        // butDatesAll
        //
        this.butDatesAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDatesAll.setAutosize(true);
        this.butDatesAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDatesAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDatesAll.setCornerRadius(4F);
        this.butDatesAll.Location = new System.Drawing.Point(233, 13);
        this.butDatesAll.Name = "butDatesAll";
        this.butDatesAll.Size = new System.Drawing.Size(77, 24);
        this.butDatesAll.TabIndex = 228;
        this.butDatesAll.Text = "All Dates";
        this.butDatesAll.Click += new System.EventHandler(this.butDatesAll_Click);
        //
        // checkIntermingled
        //
        this.checkIntermingled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIntermingled.Location = new System.Drawing.Point(331, 475);
        this.checkIntermingled.Name = "checkIntermingled";
        this.checkIntermingled.Size = new System.Drawing.Size(150, 20);
        this.checkIntermingled.TabIndex = 239;
        this.checkIntermingled.Text = "Intermingle family members";
        //
        // butDefaults
        //
        this.butDefaults.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDefaults.setAutosize(true);
        this.butDefaults.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDefaults.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDefaults.setCornerRadius(4F);
        this.butDefaults.Location = new System.Drawing.Point(656, 440);
        this.butDefaults.Name = "butDefaults";
        this.butDefaults.Size = new System.Drawing.Size(76, 24);
        this.butDefaults.TabIndex = 241;
        this.butDefaults.Text = "Defaults";
        this.butDefaults.Click += new System.EventHandler(this.butDefaults_Click);
        //
        // butUndo
        //
        this.butUndo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUndo.setAutosize(true);
        this.butUndo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUndo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUndo.setCornerRadius(4F);
        this.butUndo.Location = new System.Drawing.Point(7, 631);
        this.butUndo.Name = "butUndo";
        this.butUndo.Size = new System.Drawing.Size(88, 24);
        this.butUndo.TabIndex = 243;
        this.butUndo.Text = "Undo Billing";
        this.butUndo.Click += new System.EventHandler(this.butUndo_Click);
        //
        // FormBillingOptions
        //
        this.AcceptButton = this.butCreate;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(898, 666);
        this.Controls.Add(this.butUndo);
        this.Controls.Add(this.butDefaults);
        this.Controls.Add(this.checkIntermingled);
        this.Controls.Add(this.groupDateRange);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butCreate);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridDun);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormBillingOptions";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Billing Options";
        this.Load += new System.EventHandler(this.FormBillingOptions_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupDateRange.ResumeLayout(false);
        this.groupDateRange.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formBillingOptions_Load(Object sender, System.EventArgs e) throws Exception {
        textLastStatement.Text = DateTime.Today.AddMonths(-1).ToShortDateString();
        checkIncludeChanged.Checked = PrefC.getBool(PrefName.BillingIncludeChanged);
        comboAge.Items.Add(Lan.g(this,"Any Balance"));
        comboAge.Items.Add(Lan.g(this,"Over 30 Days"));
        comboAge.Items.Add(Lan.g(this,"Over 60 Days"));
        comboAge.Items.Add(Lan.g(this,"Over 90 Days"));
        System.String __dummyScrutVar0 = PrefC.getString(PrefName.BillingAgeOfAccount);
        if (__dummyScrutVar0.equals("30"))
        {
            comboAge.SelectedIndex = 1;
        }
        else if (__dummyScrutVar0.equals("60"))
        {
            comboAge.SelectedIndex = 2;
        }
        else if (__dummyScrutVar0.equals("90"))
        {
            comboAge.SelectedIndex = 3;
        }
        else
        {
            comboAge.SelectedIndex = 0;
        }   
        checkBadAddress.Checked = PrefC.getBool(PrefName.BillingExcludeBadAddresses);
        checkExcludeInactive.Checked = PrefC.getBool(PrefName.BillingExcludeInactive);
        checkExcludeNegative.Checked = PrefC.getBool(PrefName.BillingExcludeNegative);
        checkExcludeInsPending.Checked = PrefC.getBool(PrefName.BillingExcludeInsPending);
        checkExcludeIfProcs.Checked = PrefC.getBool(PrefName.BillingExcludeIfUnsentProcs);
        textExcludeLessThan.Text = PrefC.getString(PrefName.BillingExcludeLessThan);
        checkIgnoreInPerson.Checked = PrefC.getBool(PrefName.BillingIgnoreInPerson);
        listBillType.Items.Add(Lan.g(this,"(all)"));
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            listBillType.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
        }
        String[] selectedBillTypes = PrefC.getString(PrefName.BillingSelectBillingTypes).Split(',');
        for (int i = 0;i < selectedBillTypes.Length;i++)
        {
            //might be blank
            try
            {
                int order = DefC.GetOrder(DefCat.BillingTypes, Convert.ToInt32(selectedBillTypes[i]));
                if (order != -1)
                {
                    listBillType.SetSelected(order + 1, true);
                }
                 
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        if (listBillType.SelectedIndices.Count == 0)
        {
            listBillType.SelectedIndex = 0;
        }
         
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            //Using clinics.
            labelSaveDefaults.Text = "(except the date at the top and clinic at the bottom)";
            labelClinic.Visible = true;
            comboClinic.Visible = true;
            comboClinic.Items.Add("All");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        }
         
        //blank is allowed
        fillDunning();
        setDefaults();
    }

    //private void butAll_Click(object sender, System.EventArgs e) {
    //	for(int i=0;i<listBillType.Items.Count;i++){
    //		listBillType.SetSelected(i,true);
    //	}
    //}
    private void butSaveDefault_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textExcludeLessThan.errorProvider1.GetError(textExcludeLessThan), "") || !StringSupport.equals(textLastStatement.errorProvider1.GetError(textLastStatement), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (listBillType.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"Please select at least one billing type first.");
            return ;
        }
         
        String selectedBillingTypes = "";
        for (int i = 0;i < listBillType.SelectedIndices.Count;i++)
        {
            //indicates all.
            //will always be at least 1
            if (listBillType.SelectedIndices[i] == 0)
            {
                //all
                //it ignores any other selections.
                selectedBillingTypes = "";
                break;
            }
             
            if (i > 0)
            {
                selectedBillingTypes += ",";
            }
             
            selectedBillingTypes += DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillType.SelectedIndices[i] - 1].DefNum.ToString();
        }
        String ageOfAccount = "";
        if (comboAge.SelectedIndex == 0)
        {
            ageOfAccount = "";
        }
        else //the default
        if (comboAge.SelectedIndex == 1)
        {
            ageOfAccount = "30";
        }
        else if (comboAge.SelectedIndex == 2)
        {
            ageOfAccount = "60";
        }
        else if (comboAge.SelectedIndex == 3)
        {
            ageOfAccount = "90";
        }
            
        if (Prefs.UpdateBool(PrefName.BillingIncludeChanged, checkIncludeChanged.Checked) | Prefs.updateString(PrefName.BillingSelectBillingTypes,selectedBillingTypes) | Prefs.updateString(PrefName.BillingAgeOfAccount,ageOfAccount) | Prefs.UpdateBool(PrefName.BillingExcludeBadAddresses, checkBadAddress.Checked) | Prefs.UpdateBool(PrefName.BillingExcludeInactive, checkExcludeInactive.Checked) | Prefs.UpdateBool(PrefName.BillingExcludeNegative, checkExcludeNegative.Checked) | Prefs.UpdateBool(PrefName.BillingExcludeInsPending, checkExcludeInsPending.Checked) | Prefs.UpdateBool(PrefName.BillingExcludeIfUnsentProcs, checkExcludeIfProcs.Checked) | Prefs.UpdateString(PrefName.BillingExcludeLessThan, textExcludeLessThan.Text) | Prefs.UpdateBool(PrefName.BillingIgnoreInPerson, checkIgnoreInPerson.Checked))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    private void fillDunning() throws Exception {
        dunningList = Dunnings.refresh();
        gridDun.beginUpdate();
        gridDun.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Billing Type",80);
        gridDun.getColumns().add(col);
        col = new ODGridColumn("Aging",70);
        gridDun.getColumns().add(col);
        col = new ODGridColumn("Ins",40);
        gridDun.getColumns().add(col);
        col = new ODGridColumn("Message",150);
        gridDun.getColumns().add(col);
        col = new ODGridColumn("Bold Message",150);
        gridDun.getColumns().add(col);
        col = new ODGridColumn("Email", 30, HorizontalAlignment.Center);
        gridDun.getColumns().add(col);
        gridDun.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //string text;
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < dunningList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (dunningList[i].BillingType == 0)
            {
                row.getCells().add(Lan.g(this,"all"));
            }
            else
            {
                row.getCells().Add(DefC.GetName(DefCat.BillingTypes, dunningList[i].BillingType));
            } 
            if (dunningList[i].AgeAccount == 0)
            {
                row.getCells().add(Lan.g(this,"any"));
            }
            else
            {
                row.getCells().add(Lan.g(this,"Over ") + dunningList[i].AgeAccount.ToString());
            } 
            if (dunningList[i].InsIsPending == YN.Unknown)
            {
                row.getCells().add(Lan.g(this,"any"));
            }
            else if (dunningList[i].InsIsPending == YN.Yes)
            {
                row.getCells().add(Lan.g(this,"Y"));
            }
            else if (dunningList[i].InsIsPending == YN.No)
            {
                row.getCells().add(Lan.g(this,"N"));
            }
               
            row.getCells().Add(dunningList[i].DunMessage);
            cell = new OpenDental.UI.ODGridCell(dunningList[i].MessageBold);
            cell.setBold(YN.Yes);
            cell.setColorText(Color.DarkRed);
            row.getCells().Add(cell);
            if (!StringSupport.equals(dunningList[i].EmailBody, "") || !StringSupport.equals(dunningList[i].EmailSubject, ""))
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridDun.getRows().add(row);
        }
        gridDun.endUpdate();
    }

    private void gridDun_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormDunningEdit formD = new FormDunningEdit(dunningList[e.getRow()]);
        formD.ShowDialog();
        fillDunning();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Dunning dun = new Dunning();
        FormDunningEdit FormD = new FormDunningEdit(dun);
        FormD.IsNew = true;
        FormD.ShowDialog();
        if (FormD.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillDunning();
    }

    private void butDefaults_Click(Object sender, EventArgs e) throws Exception {
        FormBillingDefaults FormB = new FormBillingDefaults();
        FormB.ShowDialog();
        if (FormB.DialogResult == DialogResult.OK)
        {
            setDefaults();
        }
         
    }

    private void setDefaults() throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-PrefC.getLong(PrefName.BillingDefaultsLastDays)).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        checkIntermingled.Checked = PrefC.getBool(PrefName.BillingDefaultsIntermingle);
        textNote.Text = PrefC.getString(PrefName.BillingDefaultsNote);
    }

    private void but30days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-30).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void but45days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-45).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void but90days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-90).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void butDatesAll_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = "";
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void butUndo_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"When the billing list comes up, use the radio button at the top to show the 'Sent' bills.\r\nThen, change their status back to unsent.\r\nYou can edit them as a group using the button at the right.");
        DialogResult = DialogResult.OK;
    }

    //will trigger ContrStaff to bring up the billing window
    private void butCreate_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textExcludeLessThan.errorProvider1.GetError(textExcludeLessThan), "") || !StringSupport.equals(textLastStatement.errorProvider1.GetError(textLastStatement), "") || !StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        Ledgers.runAging();
        if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily) && PrefC.getDate(PrefName.DateLastAging) < DateTime.Today.AddDays(-15))
        {
            MsgBox.show(this,"Last aging date seems old, so you will now be given a chance to update it.  The billing process will continue whether or not aging gets updated.");
            FormAging FormA = new FormAging();
            FormA.ShowDialog();
        }
         
        DateTime lastStatement = PIn.Date(textLastStatement.Text);
        if (StringSupport.equals(textLastStatement.Text, ""))
        {
            lastStatement = DateTimeOD.getToday();
        }
         
        String getAge = "";
        if (comboAge.SelectedIndex == 1)
            getAge = "30";
        else if (comboAge.SelectedIndex == 2)
            getAge = "60";
        else if (comboAge.SelectedIndex == 3)
            getAge = "90";
           
        List<long> billingNums = new List<long>();
        for (int i = 0;i < listBillType.SelectedIndices.Count;i++)
        {
            //[listBillType.SelectedIndices.Count];
            if (listBillType.SelectedIndices[i] == 0)
            {
                //if (all) is selected, then ignore any other selections
                billingNums.Clear();
                break;
            }
             
            billingNums.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillType.SelectedIndices[i] - 1].DefNum);
        }
        ClinicNum = 0;
        if (comboClinic.SelectedIndex > 0)
        {
            ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        Cursor = Cursors.WaitCursor;
        List<PatAging> agingList = Patients.GetAgingList(getAge, lastStatement, billingNums, checkBadAddress.Checked, checkExcludeNegative.Checked, PIn.Double(textExcludeLessThan.Text), checkExcludeInactive.Checked, checkIncludeChanged.Checked, checkExcludeInsPending.Checked, checkExcludeIfProcs.Checked, checkIgnoreInPerson.Checked, ClinicNum);
        DateTime dateRangeFrom = DateTime.MinValue;
        DateTime dateRangeTo = DateTimeOD.getToday();
        //Needed for payplan accuracy.//new DateTime(2200,1,1);
        if (!StringSupport.equals(textDateStart.Text, ""))
        {
            dateRangeFrom = PIn.Date(textDateStart.Text);
        }
         
        if (!StringSupport.equals(textDateEnd.Text, ""))
        {
            dateRangeTo = PIn.Date(textDateEnd.Text);
        }
         
        if (agingList.Count == 0)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"List of created bills is empty.");
            return ;
        }
         
        //if(agingList!=null){
        Statement stmt;
        int ageAccount = 0;
        YN insIsPending = YN.Unknown;
        Dunning dunning;
        Dunning[] dunList = Dunnings.refresh();
        for (int i = 0;i < agingList.Count;i++)
        {
            stmt = new Statement();
            stmt.DateRangeFrom = dateRangeFrom;
            stmt.DateRangeTo = dateRangeTo;
            stmt.DateSent = DateTimeOD.getToday();
            stmt.DocNum = 0;
            stmt.HidePayment = false;
            stmt.Intermingled = checkIntermingled.Checked;
            stmt.IsSent = false;
            if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "1") || StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "2") || StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "3"))
            {
                stmt.Mode_ = StatementMode.Electronic;
                stmt.Intermingled = true;
            }
            else
            {
                stmt.Mode_ = StatementMode.Mail;
            } 
            if (StringSupport.equals(DefC.GetDef(DefCat.BillingTypes, agingList[i].BillingType).ItemValue, "E"))
            {
                stmt.Mode_ = StatementMode.Email;
            }
             
            InstallmentPlan installPlan = InstallmentPlans.GetOneForFam(agingList[i].PatNum);
            if (installPlan == null)
            {
                stmt.Note = textNote.Text;
            }
            else
            {
                stmt.Note = textNote.Text.Replace("[InstallmentPlanTerms]", "Installment Plan\r\n" + "Date First Payment: " + installPlan.DateFirstPayment.ToShortDateString() + "\r\n" + "Monthly Payment: " + installPlan.MonthlyPayment.ToString("c") + "\r\n" + "APR: " + (installPlan.APR / (float)100).ToString("P") + "\r\n" + "Note: " + installPlan.Note);
            } 
            stmt.NoteBold = "";
            //appointment reminders are not handled here since it would be too slow.
            //set dunning messages here
            if (agingList[i].BalOver90 > 0)
            {
                ageAccount = 90;
            }
            else if (agingList[i].Bal_61_90 > 0)
            {
                ageAccount = 60;
            }
            else if (agingList[i].Bal_31_60 > 0)
            {
                ageAccount = 30;
            }
            else
            {
                ageAccount = 0;
            }   
            if (agingList[i].InsEst > 0)
            {
                insIsPending = YN.Yes;
            }
            else
            {
                insIsPending = YN.No;
            } 
            dunning = Dunnings.GetDunning(dunList, agingList[i].BillingType, ageAccount, insIsPending);
            if (dunning != null)
            {
                if (!StringSupport.equals(stmt.Note, ""))
                {
                    stmt.Note += "\r\n\r\n";
                }
                 
                //leave one empty line
                stmt.Note += dunning.DunMessage;
                //if(stmt.Note!=""){//there will never be anything in NoteBold already
                //	stmt.Note+="\r\n\r\n";//leave one empty line
                //}
                stmt.NoteBold += dunning.MessageBold;
                stmt.EmailSubject = dunning.EmailSubject;
                stmt.EmailBody = dunning.EmailBody;
            }
             
            stmt.PatNum = agingList[i].PatNum;
            stmt.SinglePatient = false;
            Statements.insert(stmt);
        }
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


