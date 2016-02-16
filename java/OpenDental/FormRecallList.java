//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:38 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AppointmentL;
import OpenDental.DataValid;
import OpenDental.DivvyConnect.PostcardServiceClient;
import OpenDental.FormCommItem;
import OpenDental.FormProgramLinkEdit;
import OpenDental.FormRecallEdit;
import OpenDental.FormRecallList;
import OpenDental.FormRecallListUndo;
import OpenDental.FormRpRecall;
import OpenDental.GotoModule;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Recall;
import OpenDentBusiness.RecallListShowNumberReminders;
import OpenDentBusiness.RecallListSort;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Security;
import OpenDentBusiness.SiteC;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormRecallList  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    //private ArrayList MainAL;
    //<summary>Will be set to true when form closes if user click Send to Pinboard.</summary>
    //public bool PinClicked=false;
    private OpenDental.UI.Button butReport;
    private int pagesPrinted = new int();
    private DataTable addrTable = new DataTable();
    private int patientsPrinted = new int();
    private OpenDental.UI.FormPrintPreview printPreview;
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butSetStatus;
    private System.Windows.Forms.ComboBox comboStatus = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butLabels;
    private OpenDental.UI.Button butPostcards;
    private PrintDocument pd = new PrintDocument();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.ValidDate textDateStart;
    private CheckBox checkGroupFamilies = new CheckBox();
    /**
    * This is the patNum of the current (or last) patient selected.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    private OpenDental.UI.Button butPrint;
    DataTable table = new DataTable();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private ComboBox comboProv = new ComboBox();
    private Label label4 = new Label();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private OpenDental.UI.Button butSchedPat;
    private OpenDental.UI.Button butSchedFam;
    private ComboBox comboSite = new ComboBox();
    private Label labelSite = new Label();
    private OpenDental.UI.Button butEmail;
    private Label labelPatientCount = new Label();
    private ComboBox comboSort = new ComboBox();
    private Label label5 = new Label();
    private OpenDental.UI.Button butLabelOne;
    private ComboBox comboNumberReminders = new ComboBox();
    private Label label3 = new Label();
    private ContextMenu menuRightClick = new ContextMenu();
    private OpenDental.UI.Button butGotoAccount;
    private OpenDental.UI.Button butCommlog;
    private MenuItem menuItemSeeFamily = new MenuItem();
    private OpenDental.UI.Button butGotoFamily;
    private OpenDental.UI.Button butUndo;
    private OpenDental.UI.Button butECards;
    private MenuItem menuItemSeeAccount = new MenuItem();
    //<summary>Only used if PinClicked=true</summary>
    //public List<long> AptNumsSelected;
    /**
    * 
    */
    public FormRecallList() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Lan.f(this);
        //Lan.C(this,new Control[]
        //	{
        //		textBox1
        //	});
        gridMain.ContextMenu = menuRightClick;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRecallList.class);
        this.labelClinic = new System.Windows.Forms.Label();
        this.butClose = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.comboNumberReminders = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.comboSort = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkGroupFamilies = new System.Windows.Forms.CheckBox();
        this.textDateEnd = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.butReport = new OpenDental.UI.Button();
        this.butLabels = new OpenDental.UI.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.comboStatus = new System.Windows.Forms.ComboBox();
        this.butSetStatus = new OpenDental.UI.Button();
        this.butPostcards = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.butSchedPat = new OpenDental.UI.Button();
        this.butSchedFam = new OpenDental.UI.Button();
        this.butEmail = new OpenDental.UI.Button();
        this.labelPatientCount = new System.Windows.Forms.Label();
        this.butLabelOne = new OpenDental.UI.Button();
        this.menuRightClick = new System.Windows.Forms.ContextMenu();
        this.menuItemSeeFamily = new System.Windows.Forms.MenuItem();
        this.menuItemSeeAccount = new System.Windows.Forms.MenuItem();
        this.butGotoAccount = new OpenDental.UI.Button();
        this.butCommlog = new OpenDental.UI.Button();
        this.butGotoFamily = new OpenDental.UI.Button();
        this.butUndo = new OpenDental.UI.Button();
        this.butECards = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(386, 37);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(70, 14);
        this.labelClinic.TabIndex = 22;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(896, 663);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(255, 55);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(98, 24);
        this.butRefresh.TabIndex = 2;
        this.butRefresh.Text = "&Refresh List";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.comboNumberReminders);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.comboSort);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.comboSite);
        this.groupBox1.Controls.Add(this.labelSite);
        this.groupBox1.Controls.Add(this.comboClinic);
        this.groupBox1.Controls.Add(this.labelClinic);
        this.groupBox1.Controls.Add(this.comboProv);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.checkGroupFamilies);
        this.groupBox1.Controls.Add(this.textDateEnd);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butRefresh);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(6, 2);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(641, 83);
        this.groupBox1.TabIndex = 1;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "View";
        //
        // comboNumberReminders
        //
        this.comboNumberReminders.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboNumberReminders.Location = new System.Drawing.Point(103, 57);
        this.comboNumberReminders.MaxDropDownItems = 40;
        this.comboNumberReminders.Name = "comboNumberReminders";
        this.comboNumberReminders.Size = new System.Drawing.Size(82, 21);
        this.comboNumberReminders.TabIndex = 39;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 60);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(99, 14);
        this.label3.TabIndex = 38;
        this.label3.Text = "Show Reminders";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSort
        //
        this.comboSort.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSort.Location = new System.Drawing.Point(67, 32);
        this.comboSort.MaxDropDownItems = 40;
        this.comboSort.Name = "comboSort";
        this.comboSort.Size = new System.Drawing.Size(118, 21);
        this.comboSort.TabIndex = 37;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(11, 35);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(55, 14);
        this.label5.TabIndex = 36;
        this.label5.Text = "Sort";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSite
        //
        this.comboSite.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSite.Location = new System.Drawing.Point(458, 57);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(160, 21);
        this.comboSite.TabIndex = 25;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(386, 60);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(70, 14);
        this.labelSite.TabIndex = 24;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(458, 34);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(160, 21);
        this.comboClinic.TabIndex = 23;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(458, 11);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(160, 21);
        this.comboProv.TabIndex = 21;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(386, 14);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(70, 14);
        this.label4.TabIndex = 20;
        this.label4.Text = "Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkGroupFamilies
        //
        this.checkGroupFamilies.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkGroupFamilies.Location = new System.Drawing.Point(77, 12);
        this.checkGroupFamilies.Name = "checkGroupFamilies";
        this.checkGroupFamilies.Size = new System.Drawing.Size(108, 18);
        this.checkGroupFamilies.TabIndex = 19;
        this.checkGroupFamilies.Text = "Group Families";
        this.checkGroupFamilies.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkGroupFamilies.UseVisualStyleBackColor = true;
        this.checkGroupFamilies.Click += new System.EventHandler(this.checkGroupFamilies_Click);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(276, 34);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 18;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(276, 13);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 17;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(191, 37);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(82, 14);
        this.label2.TabIndex = 12;
        this.label2.Text = "End Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(191, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 14);
        this.label1.TabIndex = 11;
        this.label1.Text = "Start Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butReport
        //
        this.butReport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butReport.setAutosize(true);
        this.butReport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReport.setCornerRadius(4F);
        this.butReport.Location = new System.Drawing.Point(353, 637);
        this.butReport.Name = "butReport";
        this.butReport.Size = new System.Drawing.Size(87, 24);
        this.butReport.TabIndex = 13;
        this.butReport.Text = "R&un Report";
        this.butReport.Click += new System.EventHandler(this.butReport_Click);
        //
        // butLabels
        //
        this.butLabels.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabels.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabels.setAutosize(true);
        this.butLabels.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabels.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabels.setCornerRadius(4F);
        this.butLabels.Image = Resources.getbutLabel();
        this.butLabels.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabels.Location = new System.Drawing.Point(131, 663);
        this.butLabels.Name = "butLabels";
        this.butLabels.Size = new System.Drawing.Size(119, 24);
        this.butLabels.TabIndex = 14;
        this.butLabels.Text = "Label Preview";
        this.butLabels.Click += new System.EventHandler(this.butLabels_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.comboStatus);
        this.groupBox3.Controls.Add(this.butSetStatus);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(654, 2);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(188, 83);
        this.groupBox3.TabIndex = 15;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Set Status";
        //
        // comboStatus
        //
        this.comboStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatus.Location = new System.Drawing.Point(17, 19);
        this.comboStatus.MaxDropDownItems = 40;
        this.comboStatus.Name = "comboStatus";
        this.comboStatus.Size = new System.Drawing.Size(160, 21);
        this.comboStatus.TabIndex = 15;
        //
        // butSetStatus
        //
        this.butSetStatus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetStatus.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSetStatus.setAutosize(true);
        this.butSetStatus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetStatus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetStatus.setCornerRadius(4F);
        this.butSetStatus.Location = new System.Drawing.Point(110, 47);
        this.butSetStatus.Name = "butSetStatus";
        this.butSetStatus.Size = new System.Drawing.Size(67, 24);
        this.butSetStatus.TabIndex = 14;
        this.butSetStatus.Text = "Set";
        this.butSetStatus.Click += new System.EventHandler(this.butSetStatus_Click);
        //
        // butPostcards
        //
        this.butPostcards.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPostcards.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPostcards.setAutosize(true);
        this.butPostcards.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPostcards.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPostcards.setCornerRadius(4F);
        this.butPostcards.Image = Resources.getbutPreview();
        this.butPostcards.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPostcards.Location = new System.Drawing.Point(6, 637);
        this.butPostcards.Name = "butPostcards";
        this.butPostcards.Size = new System.Drawing.Size(119, 24);
        this.butPostcards.TabIndex = 16;
        this.butPostcards.Text = "Postcard Preview";
        this.butPostcards.Click += new System.EventHandler(this.butPostcards_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(6, 88);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(965, 544);
        this.gridMain.TabIndex = 18;
        this.gridMain.setTitle("Recall List");
        this.gridMain.setTranslationName("TableRecallList");
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
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(353, 663);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 24);
        this.butPrint.TabIndex = 19;
        this.butPrint.Text = "Print List";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butSchedPat
        //
        this.butSchedPat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSchedPat.setAutosize(true);
        this.butSchedPat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSchedPat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSchedPat.setCornerRadius(4F);
        this.butSchedPat.Image = Resources.getbutPin();
        this.butSchedPat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSchedPat.Location = new System.Drawing.Point(642, 637);
        this.butSchedPat.Name = "butSchedPat";
        this.butSchedPat.Size = new System.Drawing.Size(114, 24);
        this.butSchedPat.TabIndex = 58;
        this.butSchedPat.Text = "Sched Patient";
        this.butSchedPat.Click += new System.EventHandler(this.butSchedPat_Click);
        //
        // butSchedFam
        //
        this.butSchedFam.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSchedFam.setAutosize(true);
        this.butSchedFam.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSchedFam.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSchedFam.setCornerRadius(4F);
        this.butSchedFam.Image = Resources.getbutPin();
        this.butSchedFam.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSchedFam.Location = new System.Drawing.Point(642, 663);
        this.butSchedFam.Name = "butSchedFam";
        this.butSchedFam.Size = new System.Drawing.Size(114, 24);
        this.butSchedFam.TabIndex = 59;
        this.butSchedFam.Text = "Sched Family";
        this.butSchedFam.Click += new System.EventHandler(this.butSchedFam_Click);
        //
        // butEmail
        //
        this.butEmail.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEmail.setAutosize(true);
        this.butEmail.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEmail.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEmail.setCornerRadius(4F);
        this.butEmail.Image = Resources.getemail1();
        this.butEmail.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEmail.Location = new System.Drawing.Point(256, 663);
        this.butEmail.Name = "butEmail";
        this.butEmail.Size = new System.Drawing.Size(91, 24);
        this.butEmail.TabIndex = 60;
        this.butEmail.Text = "E-Mail";
        this.butEmail.Click += new System.EventHandler(this.butEmail_Click);
        //
        // labelPatientCount
        //
        this.labelPatientCount.Location = new System.Drawing.Point(759, 669);
        this.labelPatientCount.Name = "labelPatientCount";
        this.labelPatientCount.Size = new System.Drawing.Size(114, 14);
        this.labelPatientCount.TabIndex = 61;
        this.labelPatientCount.Text = "Patient Count:";
        this.labelPatientCount.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butLabelOne
        //
        this.butLabelOne.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabelOne.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butLabelOne.setAutosize(true);
        this.butLabelOne.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabelOne.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabelOne.setCornerRadius(4F);
        this.butLabelOne.Image = Resources.getbutLabel();
        this.butLabelOne.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabelOne.Location = new System.Drawing.Point(131, 637);
        this.butLabelOne.Name = "butLabelOne";
        this.butLabelOne.Size = new System.Drawing.Size(119, 24);
        this.butLabelOne.TabIndex = 63;
        this.butLabelOne.Text = "Single Labels";
        this.butLabelOne.Click += new System.EventHandler(this.butLabelOne_Click);
        //
        // menuRightClick
        //
        this.menuRightClick.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSeeFamily, this.menuItemSeeAccount });
        //
        // menuItemSeeFamily
        //
        this.menuItemSeeFamily.Index = 0;
        this.menuItemSeeFamily.Text = "See Family";
        this.menuItemSeeFamily.Click += new System.EventHandler(this.menuItemSeeFamily_Click);
        //
        // menuItemSeeAccount
        //
        this.menuItemSeeAccount.Index = 1;
        this.menuItemSeeAccount.Text = "See Account";
        this.menuItemSeeAccount.Click += new System.EventHandler(this.menuItemSeeAccount_Click);
        //
        // butGotoAccount
        //
        this.butGotoAccount.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGotoAccount.setAutosize(true);
        this.butGotoAccount.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGotoAccount.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGotoAccount.setCornerRadius(4F);
        this.butGotoAccount.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGotoAccount.Location = new System.Drawing.Point(446, 663);
        this.butGotoAccount.Name = "butGotoAccount";
        this.butGotoAccount.Size = new System.Drawing.Size(96, 24);
        this.butGotoAccount.TabIndex = 64;
        this.butGotoAccount.Text = "Go to Account";
        this.butGotoAccount.Click += new System.EventHandler(this.butGotoAccount_Click);
        //
        // butCommlog
        //
        this.butCommlog.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCommlog.setAutosize(true);
        this.butCommlog.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCommlog.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCommlog.setCornerRadius(4F);
        this.butCommlog.Image = Resources.getcommlog();
        this.butCommlog.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCommlog.Location = new System.Drawing.Point(548, 663);
        this.butCommlog.Name = "butCommlog";
        this.butCommlog.Size = new System.Drawing.Size(88, 24);
        this.butCommlog.TabIndex = 65;
        this.butCommlog.Text = "Comm";
        this.butCommlog.Click += new System.EventHandler(this.butCommlog_Click);
        //
        // butGotoFamily
        //
        this.butGotoFamily.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGotoFamily.setAutosize(true);
        this.butGotoFamily.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGotoFamily.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGotoFamily.setCornerRadius(4F);
        this.butGotoFamily.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGotoFamily.Location = new System.Drawing.Point(446, 637);
        this.butGotoFamily.Name = "butGotoFamily";
        this.butGotoFamily.Size = new System.Drawing.Size(96, 24);
        this.butGotoFamily.TabIndex = 66;
        this.butGotoFamily.Text = "Go to Family";
        this.butGotoFamily.Click += new System.EventHandler(this.butGotoFamily_Click);
        //
        // butUndo
        //
        this.butUndo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUndo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butUndo.setAutosize(true);
        this.butUndo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUndo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUndo.setCornerRadius(4F);
        this.butUndo.Location = new System.Drawing.Point(6, 663);
        this.butUndo.Name = "butUndo";
        this.butUndo.Size = new System.Drawing.Size(119, 24);
        this.butUndo.TabIndex = 67;
        this.butUndo.Text = "Undo";
        this.butUndo.Click += new System.EventHandler(this.butUndo_Click);
        //
        // butECards
        //
        this.butECards.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butECards.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butECards.setAutosize(true);
        this.butECards.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butECards.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butECards.setCornerRadius(4F);
        this.butECards.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butECards.Location = new System.Drawing.Point(256, 637);
        this.butECards.Name = "butECards";
        this.butECards.Size = new System.Drawing.Size(91, 24);
        this.butECards.TabIndex = 60;
        this.butECards.Text = "eCards";
        this.butECards.Visible = false;
        this.butECards.Click += new System.EventHandler(this.butECards_Click);
        //
        // FormRecallList
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(975, 691);
        this.Controls.Add(this.butUndo);
        this.Controls.Add(this.butGotoFamily);
        this.Controls.Add(this.butCommlog);
        this.Controls.Add(this.butGotoAccount);
        this.Controls.Add(this.butSchedFam);
        this.Controls.Add(this.butLabelOne);
        this.Controls.Add(this.butSchedPat);
        this.Controls.Add(this.labelPatientCount);
        this.Controls.Add(this.butECards);
        this.Controls.Add(this.butEmail);
        this.Controls.Add(this.butPostcards);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.butLabels);
        this.Controls.Add(this.butReport);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.Name = "FormRecallList";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Recall List";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormRecallList_FormClosing);
        this.Load += new System.EventHandler(this.FormRecallList_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formRecallList_Load(Object sender, System.EventArgs e) throws Exception {
        //AptNumsSelected=new List<long>();
        checkGroupFamilies.Checked = PrefC.getBool(PrefName.RecallGroupByFamily);
        for (int i = 0;i < Enum.GetNames(RecallListSort.class).Length;i++)
        {
            comboSort.Items.Add(Lan.g("enumRecallListSort", Enum.GetNames(RecallListSort.class)[i]));
        }
        comboSort.SelectedIndex = 0;
        comboNumberReminders.Items.Add(Lan.g(this,"all"));
        comboNumberReminders.Items.Add("0");
        comboNumberReminders.Items.Add("1");
        comboNumberReminders.Items.Add("2");
        comboNumberReminders.Items.Add("3");
        comboNumberReminders.Items.Add("4");
        comboNumberReminders.Items.Add("5");
        comboNumberReminders.Items.Add("6+");
        comboNumberReminders.SelectedIndex = 0;
        int daysPast = PrefC.getInt(PrefName.RecallDaysPast);
        int daysFuture = PrefC.getInt(PrefName.RecallDaysFuture);
        if (daysPast == -1)
        {
            textDateStart.Text = "";
        }
        else
        {
            textDateStart.Text = DateTime.Today.AddDays(-daysPast).ToShortDateString();
        } 
        if (daysFuture == -1)
        {
            textDateEnd.Text = "";
        }
        else
        {
            textDateEnd.Text = DateTime.Today.AddDays(daysFuture).ToShortDateString();
        } 
        comboProv.Items.Add(Lan.g(this,"All"));
        comboProv.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add(Lan.g(this,"All"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
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
        comboStatus.Items.Clear();
        comboStatus.Items.Add(Lan.g(this,"none"));
        comboStatus.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()].Length;i++)
        {
            comboStatus.Items.Add(DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].ItemName);
        }
        fillMain(null);
        Plugins.hookAddCode(this,"FormRecallList.Load_End",table);
    }

    /**
    * OK to pass in null for excludePatNums.
    */
    private void fillMain(List<long> excludePatNums) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            return ;
        }
         
        //remember which recallnums were selected
        List<String> recallNums = new List<String>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString());
        }
        DateTime fromDate = new DateTime();
        DateTime toDate = new DateTime();
        if (StringSupport.equals(textDateStart.Text, ""))
        {
            fromDate = DateTime.MinValue;
        }
        else
        {
            fromDate = PIn.Date(textDateStart.Text);
        } 
        if (StringSupport.equals(textDateEnd.Text, ""))
        {
            toDate = DateTime.MaxValue;
        }
        else
        {
            toDate = PIn.Date(textDateEnd.Text);
        } 
        long provNum = 0;
        if (comboProv.SelectedIndex != 0)
        {
            provNum = ProviderC.getListShort()[comboProv.SelectedIndex - 1].ProvNum;
        }
         
        long clinicNum = 0;
        if (!PrefC.getBool(PrefName.EasyNoClinics) && comboClinic.SelectedIndex != 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        long siteNum = 0;
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth) && comboSite.SelectedIndex != 0)
        {
            siteNum = SiteC.getList()[comboSite.SelectedIndex - 1].SiteNum;
        }
         
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        RecallListShowNumberReminders showReminders = (RecallListShowNumberReminders)comboNumberReminders.SelectedIndex;
        if (excludePatNums == null)
        {
            excludePatNums = new List<long>();
        }
         
        table = Recalls.GetRecallList(fromDate, toDate, checkGroupFamilies.Checked, provNum, clinicNum, siteNum, sortBy, showReminders, excludePatNums);
        int scrollval = gridMain.getScrollValue();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.RecallList);
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
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int f = 0;f < fields.Count;f++)
            {
                InternalName __dummyScrutVar0 = fields[f].InternalName;
                if (__dummyScrutVar0.equals("Due Date"))
                {
                    row.getCells().Add(table.Rows[i]["dueDate"].ToString());
                }
                else if (__dummyScrutVar0.equals("Patient"))
                {
                    row.getCells().Add(table.Rows[i]["patientName"].ToString());
                }
                else if (__dummyScrutVar0.equals("Age"))
                {
                    row.getCells().Add(table.Rows[i]["age"].ToString());
                }
                else if (__dummyScrutVar0.equals("Type"))
                {
                    row.getCells().Add(table.Rows[i]["recallType"].ToString());
                }
                else if (__dummyScrutVar0.equals("Interval"))
                {
                    row.getCells().Add(table.Rows[i]["recallInterval"].ToString());
                }
                else if (__dummyScrutVar0.equals("#Remind"))
                {
                    row.getCells().Add(table.Rows[i]["numberOfReminders"].ToString());
                }
                else if (__dummyScrutVar0.equals("LastRemind"))
                {
                    row.getCells().Add(table.Rows[i]["dateLastReminder"].ToString());
                }
                else if (__dummyScrutVar0.equals("Contact"))
                {
                    row.getCells().Add(table.Rows[i]["contactMethod"].ToString());
                }
                else if (__dummyScrutVar0.equals("Status"))
                {
                    row.getCells().Add(table.Rows[i]["status"].ToString());
                }
                else if (__dummyScrutVar0.equals("Note"))
                {
                    row.getCells().Add(table.Rows[i]["Note"].ToString());
                }
                else if (__dummyScrutVar0.equals("BillingType"))
                {
                    row.getCells().Add(table.Rows[i]["billingType"].ToString());
                }
                           
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //reselect original items
            if (recallNums.Contains(table.Rows[i]["RecallNum"].ToString()))
            {
                gridMain.setSelected(i,true);
            }
             
        }
        labelPatientCount.Text = Lan.g(this,"Patient Count:") + " " + table.Rows.Count.ToString();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //row selected before this event triggered
        setFamilyColors();
    }

    //comboStatus.SelectedIndex=-1;//mess with this later
    //SelectedPatNum=PIn.PLong(table.Rows[e.Row]["PatNum"].ToString());
    private void setFamilyColors() throws Exception {
        if (gridMain.getSelectedIndices().Length != 1)
        {
            for (int i = 0;i < gridMain.getRows().Count;i++)
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Black);
            }
            gridMain.Invalidate();
            return ;
        }
         
        long guar = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["Guarantor"].ToString());
        int famCount = 0;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (PIn.Long(table.Rows[i]["Guarantor"].ToString()) == guar)
            {
                famCount++;
                gridMain.getRows().get___idx(i).setColorText(Color.Red);
            }
            else
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Black);
            } 
        }
        if (famCount == 1)
        {
            //only the highlighted patient is red at this point
            gridMain.getRows()[gridMain.getSelectedIndices()[0]].ColorText = Color.Black;
        }
         
        gridMain.Invalidate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedPatNum = PIn.Long(table.Rows[e.getRow()]["PatNum"].ToString());
        Recall recall = Recalls.GetRecall(PIn.Long(table.Rows[e.getRow()]["RecallNum"].ToString()));
        FormRecallEdit FormR = new FormRecallEdit();
        FormR.RecallCur = recall.copy();
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //if the status has changed
        //or any of the three disabled fields was changed
        if (recall.RecallStatus != FormR.RecallCur.RecallStatus || (recall.IsDisabled != FormR.RecallCur.IsDisabled) || (recall.DisableUntilDate != FormR.RecallCur.DisableUntilDate) || (recall.DisableUntilBalance != FormR.RecallCur.DisableUntilBalance) || (!StringSupport.equals(recall.Note, FormR.RecallCur.Note)))
        {
            //or a note was added
            //make a commlog entry
            //unless there is an existing recall commlog entry for today
            boolean recallEntryToday = false;
            List<Commlog> CommlogList = Commlogs.refresh(SelectedPatNum);
            for (int i = 0;i < CommlogList.Count;i++)
            {
                if (CommlogList[i].CommDateTime.Date == DateTime.Today && CommlogList[i].CommType == Commlogs.getTypeAuto(CommItemTypeAuto.RECALL))
                {
                    recallEntryToday = true;
                }
                 
            }
            if (!recallEntryToday)
            {
                Commlog CommlogCur = new Commlog();
                CommlogCur.CommDateTime = DateTime.Now;
                CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.RECALL);
                CommlogCur.PatNum = SelectedPatNum;
                CommlogCur.Note = "";
                if (recall.RecallStatus != FormR.RecallCur.RecallStatus)
                {
                    if (FormR.RecallCur.RecallStatus == 0)
                    {
                        CommlogCur.Note += Lan.g(this,"Status None");
                    }
                    else
                    {
                        CommlogCur.Note += DefC.getName(DefCat.RecallUnschedStatus,FormR.RecallCur.RecallStatus);
                    } 
                }
                 
                if (recall.DisableUntilDate != FormR.RecallCur.DisableUntilDate && FormR.RecallCur.DisableUntilDate.Year > 1880)
                {
                    if (!StringSupport.equals(CommlogCur.Note, ""))
                    {
                        CommlogCur.Note += ",  ";
                    }
                     
                    CommlogCur.Note += Lan.g(this,"Disabled until ") + FormR.RecallCur.DisableUntilDate.ToShortDateString();
                }
                 
                if (recall.DisableUntilBalance != FormR.RecallCur.DisableUntilBalance && FormR.RecallCur.DisableUntilBalance > 0)
                {
                    if (!StringSupport.equals(CommlogCur.Note, ""))
                    {
                        CommlogCur.Note += ",  ";
                    }
                     
                    CommlogCur.Note += Lan.g(this,"Disabled until balance below ") + FormR.RecallCur.DisableUntilBalance.ToString("c");
                }
                 
                if (!StringSupport.equals(recall.Note, FormR.RecallCur.Note))
                {
                    if (!StringSupport.equals(CommlogCur.Note, ""))
                    {
                        CommlogCur.Note += ",  ";
                    }
                     
                    CommlogCur.Note += FormR.RecallCur.Note;
                }
                 
                CommlogCur.Note += ".  ";
                CommlogCur.UserNum = Security.getCurUser().UserNum;
                FormCommItem FormCI = new FormCommItem(CommlogCur);
                FormCI.IsNew = true;
                //forces user to at least consider a commlog entry
                FormCI.ShowDialog();
            }
             
        }
         
        //typically saved in this window.
        fillMain(null);
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (PIn.Long(table.Rows[i]["PatNum"].ToString()) == SelectedPatNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
        setFamilyColors();
    }

    private void butSchedPat_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 1)
        {
            MsgBox.show(this,"Please select only one patient first.");
            return ;
        }
         
        SelectedPatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        Family fam = Patients.getFamily(SelectedPatNum);
        Patient pat = fam.getPatient(SelectedPatNum);
        List<Procedure> procList = new List<Procedure>();
        Appointment apt = null;
        procList = Procedures.refresh(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        long recallNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["RecallNum"].ToString());
        try
        {
            apt = AppointmentL.CreateRecallApt(pat, procList, planList, recallNum, subList);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //PinClicked=true;
        //AptNumsSelected.Add(apt.AptNum);
        WindowState = FormWindowState.Minimized;
        List<long> pinAptNums = new List<long>();
        pinAptNums.Add(apt.AptNum);
        GotoModule.PinToAppt(pinAptNums, SelectedPatNum);
        //no securitylog entry.  It will happen when they drag off pinboard.
        gridMain.setSelected(false);
        List<long> excludePatNums = new List<long>();
        excludePatNums.Add(SelectedPatNum);
        FillMain(excludePatNums);
    }

    private void butSchedFam_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 1)
        {
            MsgBox.show(this,"Please select only one patient first.");
            return ;
        }
         
        SelectedPatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        Family fam = Patients.getFamily(SelectedPatNum);
        List<Procedure> procList = new List<Procedure>();
        List<InsPlan> planList = new List<InsPlan>();
        List<InsSub> subList = new List<InsSub>();
        Appointment apt;
        //List<long> patNums;
        List<long> pinAptNums = new List<long>();
        List<long> excludePatNums = new List<long>();
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            procList = Procedures.Refresh(fam.ListPats[i].PatNum);
            subList = InsSubs.refreshForFam(fam);
            planList = InsPlans.RefreshForSubList(subList);
            try
            {
                apt = AppointmentL.CreateRecallApt(fam.ListPats[i], procList, planList, -1, subList);
            }
            catch (Exception __dummyCatchVar0)
            {
                continue;
            }

            //(Exception ex){
            pinAptNums.Add(apt.AptNum);
            excludePatNums.Add(apt.PatNum);
        }
        if (pinAptNums.Count == 0)
        {
            MsgBox.show(this,"No recall is due.");
            return ;
        }
         
        WindowState = FormWindowState.Minimized;
        GotoModule.PinToAppt(pinAptNums, SelectedPatNum);
        //no securitylog entry needed.  It will be made as each appt is dragged off pinboard.
        gridMain.setSelected(false);
        FillMain(excludePatNums);
    }

    private void checkGroupFamilies_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        fillMain(null);
        Cursor = Cursors.Default;
    }

    private void butReport_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.UserQuery))
        {
            return ;
        }
         
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lan.g(this,"There are no Patients in the Recall table.  Must have at least one to run report."));
            return ;
        }
         
        List<long> recallNums = new List<long>();
        if (gridMain.getSelectedIndices().Length < 1)
        {
            for (int i = 0;i < gridMain.getRows().Count;i++)
            {
                recallNums.Add(PIn.Long(table.Rows[i]["RecallNum"].ToString()));
            }
        }
        else
        {
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
            }
        } 
        FormRpRecall FormRPR = new FormRpRecall(recallNums);
        FormRPR.ShowDialog();
    }

    private void butLabels_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lan.g(this,"There are no Patients in the Recall table.  Must have at least one to print."));
            return ;
        }
         
        if (PrefC.getLong(PrefName.RecallStatusMailed) == 0)
        {
            MsgBox.show(this,"You need to set a status first in the Recall Setup window.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (!StringSupport.equals(table.Rows[i]["status"].ToString(), ""))
                {
                    continue;
                }
                 
                //we only want rows without a status
                cmeth = (ContactMethod)PIn.Long(table.Rows[i]["PreferRecallMethod"].ToString());
                if (cmeth != ContactMethod.Mail && cmeth != ContactMethod.None)
                {
                    continue;
                }
                 
                gridMain.setSelected(i,true);
            }
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"No patients of mail type.");
                return ;
            }
             
            if (!MsgBox.show(this,true,"Preview labels for all of the selected patients?"))
            {
                return ;
            }
             
        }
         
        List<long> recallNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
        }
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        addrTable = Recalls.GetAddrTable(recallNums, checkGroupFamilies.Checked, sortBy);
        pagesPrinted = 0;
        patientsPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdLabels_PrintPage);
        pd.OriginAtMargins = true;
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.LabelSheet, pd, (int)Math.Ceiling((double)addrTable.Rows.Count / 30), 0, "Recall list labels printed");
        //printPreview.Document=pd;
        //printPreview.TotalPages=;
        printPreview.ShowDialog();
        if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change statuses and make commlog entries for all of the selected patients?"))
        {
            Cursor = Cursors.WaitCursor;
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //make commlog entries for each patient
                Commlogs.InsertForRecall(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString()), CommItemMode.Mail, PIn.Int(table.Rows[gridMain.getSelectedIndices()[i]]["numberOfReminders"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                Recalls.UpdateStatus(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
        }
         
        fillMain(null);
        Cursor = Cursors.Default;
    }

    private void butLabelOne_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select patient(s) first.");
            return ;
        }
         
        List<long> recallNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
        }
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        addrTable = Recalls.GetAddrTable(recallNums, checkGroupFamilies.Checked, sortBy);
        patientsPrinted = 0;
        String text = new String();
        while (patientsPrinted < addrTable.Rows.Count)
        {
            text = "";
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[patientsPrinted]["famList"].ToString(), ""))
            {
                //print family label
                text = addrTable.Rows[patientsPrinted]["guarLName"].ToString() + " " + Lan.g(this,"Household") + "\r\n";
            }
            else
            {
                //print single label
                text = addrTable.Rows[patientsPrinted]["patientNameFL"].ToString() + "\r\n";
            } 
            text += addrTable.Rows[patientsPrinted]["address"].ToString() + "\r\n";
            text += addrTable.Rows[patientsPrinted]["City"].ToString() + ", " + addrTable.Rows[patientsPrinted]["State"].ToString() + " " + addrTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
            LabelSingle.PrintText(0, text);
            patientsPrinted++;
        }
        if (MsgBox.show(this,MsgBoxButtons.YesNo,"Did all the labels finish printing correctly?  Statuses will be changed and commlog entries made for all of the selected patients.  Click Yes only if labels printed successfully."))
        {
            Cursor = Cursors.WaitCursor;
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //make commlog entries for each patient
                Commlogs.InsertForRecall(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString()), CommItemMode.Mail, PIn.Int(table.Rows[gridMain.getSelectedIndices()[i]]["numberOfReminders"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                Recalls.UpdateStatus(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
        }
         
        fillMain(null);
        Cursor = Cursors.Default;
    }

    /**
    * Changes made to printing recall postcards need to be made in FormConfirmList.butPostcards_Click() as well.
    */
    private void butPostcards_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lan.g(this,"There are no Patients in the Recall table.  Must have at least one to print."));
            return ;
        }
         
        if (PrefC.getLong(PrefName.RecallStatusMailed) == 0)
        {
            MsgBox.show(this,"You need to set a status first in the Recall Setup window.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //if(table.Rows[i]["status"].ToString()!=""){//we only want rows without a status
                //	continue;
                //}
                cmeth = (ContactMethod)PIn.Long(table.Rows[i]["PreferRecallMethod"].ToString());
                if (cmeth != ContactMethod.Mail && cmeth != ContactMethod.None)
                {
                    continue;
                }
                 
                gridMain.setSelected(i,true);
            }
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"No patients of mail type.");
                return ;
            }
             
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Preview postcards for all of the selected patients?"))
            {
                return ;
            }
             
        }
         
        List<long> recallNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
        }
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        addrTable = Recalls.GetAddrTable(recallNums, checkGroupFamilies.Checked, sortBy);
        pagesPrinted = 0;
        patientsPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdCards_PrintPage);
        pd.OriginAtMargins = true;
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 1)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 500, 700);
            pd.DefaultPageSettings.Landscape = true;
        }
        else if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 3)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 850, 1100);
        }
        else
        {
            //4
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 850, 1100);
            pd.DefaultPageSettings.Landscape = true;
        }  
        int totalPages = (int)Math.Ceiling((double)addrTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet));
        printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.Postcard, pd, totalPages, 0, "Recall list postcards printed");
        printPreview.ShowDialog();
        if (MsgBox.show(this,MsgBoxButtons.YesNo,"Did all the postcards finish printing correctly?  Statuses will be changed and commlog entries made for all of the selected patients.  Click Yes only if postcards printed successfully."))
        {
            Cursor = Cursors.WaitCursor;
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //make commlog entries for each patient
                Commlogs.InsertForRecall(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString()), CommItemMode.Mail, PIn.Int(table.Rows[gridMain.getSelectedIndices()[i]]["numberOfReminders"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                Recalls.UpdateStatus(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
        }
         
        fillMain(null);
        Cursor = Cursors.Default;
    }

    private void butUndo_Click(Object sender, EventArgs e) throws Exception {
        FormRecallListUndo form = new FormRecallListUndo();
        form.ShowDialog();
        if (form.DialogResult == DialogResult.OK)
        {
            fillMain(null);
        }
         
    }

    private void butECards_Click(Object sender, EventArgs e) throws Exception {
        if (!Programs.isEnabled(ProgramName.Divvy))
        {
            if (MsgBox.show(this,MsgBoxButtons.OKCancel,"The Divvy Program Link is not enabled. Would you like to enable it now?"))
            {
                FormProgramLinkEdit FormPE = new FormProgramLinkEdit();
                FormPE.ProgramCur = Programs.getCur(ProgramName.Divvy);
                FormPE.ShowDialog();
                DataValid.setInvalid(InvalidType.Programs);
            }
             
            if (!Programs.isEnabled(ProgramName.Divvy))
            {
                return ;
            }
             
        }
         
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lan.g(this,"There are no Patients in the Recall table.  Must have at least one to send."));
            return ;
        }
         
        if (PrefC.getLong(PrefName.RecallStatusMailed) == 0)
        {
            MsgBox.show(this,"You need to set a status first in the Recall Setup window.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                cmeth = (ContactMethod)PIn.Long(table.Rows[i]["PreferRecallMethod"].ToString());
                if (cmeth != ContactMethod.Mail && cmeth != ContactMethod.None)
                {
                    continue;
                }
                 
                gridMain.setSelected(i,true);
            }
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"No patients of mail type.");
                return ;
            }
             
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Send postcards for all of the selected patients?"))
        {
            return ;
        }
         
        Guid guid = new Guid();
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        List<long> recallNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
        }
        addrTable = Recalls.GetAddrTable(recallNums, checkGroupFamilies.Checked, sortBy);
        OpenDental.DivvyConnect.Postcard postcard;
        OpenDental.DivvyConnect.Recipient recipient;
        OpenDental.DivvyConnect.Postcard[] listPostcards = new OpenDental.DivvyConnect.Postcard[gridMain.getSelectedIndices().Length];
        String message = new String();
        long clinicNum = new long();
        Clinic clinic;
        String phone = new String();
        for (int i = 0;i < addrTable.Rows.Count;i++)
        {
            postcard = new OpenDental.DivvyConnect.Postcard();
            recipient = new OpenDental.DivvyConnect.Recipient();
            recipient.setName(addrTable.Rows[i]["patientNameFL"].ToString());
            recipient.setExternalRecipientID(addrTable.Rows[i]["patNums"].ToString());
            recipient.setAddress1(addrTable.Rows[i]["Address"].ToString());
            //Includes Address2
            recipient.setCity(addrTable.Rows[i]["City"].ToString());
            recipient.setState(addrTable.Rows[i]["State"].ToString());
            recipient.setZip(addrTable.Rows[i]["Zip"].ToString());
            postcard.setAppointmentDateTime(PIn.Date(addrTable.Rows[i]["dateDue"].ToString()));
            //js I don't know why they would ask for this.  We put this in our message.
            //Body text, family card ------------------------------------------------------------------
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[i]["famList"].ToString(), ""))
            {
                if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "0"))
                {
                    message = PrefC.getString(PrefName.RecallPostcardFamMsg);
                }
                else if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "1"))
                {
                    message = PrefC.getString(PrefName.RecallPostcardFamMsg2);
                }
                else
                {
                    message = PrefC.getString(PrefName.RecallPostcardFamMsg3);
                }  
                message = message.Replace("[FamilyList]", addrTable.Rows[i]["famList"].ToString());
            }
            else
            {
                //Body text, single card-------------------------------------------------------------------
                if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "0"))
                {
                    message = PrefC.getString(PrefName.RecallPostcardMessage);
                }
                else if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "1"))
                {
                    message = PrefC.getString(PrefName.RecallPostcardMessage2);
                }
                else
                {
                    message = PrefC.getString(PrefName.RecallPostcardMessage3);
                }  
                message = message.Replace("[DueDate]", addrTable.Rows[i]["dateDue"].ToString());
                message = message.Replace("[NameF]", "");
            } 
            //Name is included
            postcard.setMessage(message);
            postcard.setRecipient(recipient);
            postcard.setDesignID(PIn.Int(ProgramProperties.getPropVal(ProgramName.Divvy,"DesignID for Recall Cards")));
            listPostcards[i] = postcard;
        }
        OpenDental.DivvyConnect.Practice practice = new OpenDental.DivvyConnect.Practice();
        clinicNum = PIn.Long(addrTable.Rows[patientsPrinted]["ClinicNum"].ToString());
        //if using clinics
        if (!PrefC.getBool(PrefName.EasyNoClinics) && Clinics.getList().Length > 0 && Clinics.getClinic(clinicNum) != null)
        {
            //and this patient assigned to a clinic
            clinic = Clinics.getClinic(clinicNum);
            practice.setCompany(clinic.Description);
            practice.setAddress1(clinic.Address);
            practice.setAddress2(clinic.Address2);
            practice.setCity(clinic.City);
            practice.setState(clinic.State);
            practice.setZip(clinic.Zip);
            phone = clinic.Phone;
        }
        else
        {
            practice.setCompany(PrefC.getString(PrefName.PracticeTitle));
            practice.setAddress1(PrefC.getString(PrefName.PracticeAddress));
            practice.setAddress2(PrefC.getString(PrefName.PracticeAddress2));
            practice.setCity(PrefC.getString(PrefName.PracticeCity));
            practice.setState(PrefC.getString(PrefName.PracticeST));
            practice.setZip(PrefC.getString(PrefName.PracticeZip));
            phone = PrefC.getString(PrefName.PracticePhone);
        } 
        if (phone.Length == 10 && (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") || CultureInfo.CurrentCulture.Name.EndsWith("CA")))
        {
            //Canadian. en-CA or fr-CA
            practice.setPhone("(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6));
        }
        else
        {
            practice.setPhone(phone);
        } 
        PostcardServiceClient client = new PostcardServiceClient();
        OpenDental.DivvyConnect.PostcardReturnMessage returnMessage = new OpenDental.DivvyConnect.PostcardReturnMessage();
        String messages = "";
        Cursor = Cursors.WaitCursor;
        try
        {
            returnMessage = client.SendPostcards(Guid.Parse(ProgramProperties.getPropVal(ProgramName.Divvy,"API Key")), ProgramProperties.getPropVal(ProgramName.Divvy,"Username"), ProgramProperties.getPropVal(ProgramName.Divvy,"Password"), listPostcards, practice);
        }
        catch (Exception ex)
        {
            messages += "Exception: " + ex.Message + "\r\nData: " + ex.Data + "\r\n";
        }

        messages += "MessageCode: " + returnMessage.getMessageCode().ToString();
        //MessageCode enum. 0=CompletedSuccessfully, 1=CompletedWithErrors, 2=Failure
        MsgBox.show(this,"Return Messages: " + returnMessage.getMessage() + "\r\n" + messages);
        if (returnMessage.getMessageCode() == OpenDental.DivvyConnect.MessageCode.CompletedSucessfully)
        {
            Cursor = Cursors.WaitCursor;
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //make commlog entries for each patient
                //Commlogs.InsertForRecall(PIn.Long(table.Rows[gridMain.SelectedIndices[i]]["PatNum"].ToString()),CommItemMode.Mail,
                //  PIn.Int(table.Rows[gridMain.SelectedIndices[i]]["numberOfReminders"].ToString()),PrefC.GetLong(PrefName.RecallStatusMailed));
                Commlogs.InsertForRecall(1, CommItemMode.Mail, PIn.Int(table.Rows[gridMain.getSelectedIndices()[i]]["numberOfReminders"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                Recalls.UpdateStatus(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()), PrefC.getLong(PrefName.RecallStatusMailed));
            }
        }
        else if (returnMessage.getMessageCode() == OpenDental.DivvyConnect.MessageCode.CompletedWithErrors)
        {
            for (int i = 0;i < returnMessage.getPostcardMessages().Length;i++)
            {
            }
        }
          
        //todo: process return messages. Update commlog and change recall statuses for postcards that were sent.
        fillMain(null);
        Cursor = Cursors.Default;
    }

    private void butEmail_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lan.g(this,"There are no Patients in the Recall table.  Must have at least one."));
            return ;
        }
         
        if (!EmailAddresses.existsValidEmail())
        {
            MsgBox.show(this,"You need to enter an SMTP server name in e-mail setup before you can send e-mail.");
            return ;
        }
         
        if (PrefC.getLong(PrefName.RecallStatusEmailed) == 0)
        {
            MsgBox.show(this,"You need to set a status first in the Recall Setup window.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                cmeth = (ContactMethod)PIn.Long(table.Rows[i]["PreferRecallMethod"].ToString());
                if (cmeth != ContactMethod.Email)
                {
                    continue;
                }
                 
                if (StringSupport.equals(table.Rows[i]["Email"].ToString(), ""))
                {
                    continue;
                }
                 
                gridMain.setSelected(i,true);
            }
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"No patients of email type.");
                return ;
            }
             
        }
        else
        {
            //deselect the ones that do not have email addresses specified
            int skipped = 0;
            for (int i = gridMain.getSelectedIndices().Length - 1;i >= 0;i--)
            {
                if (StringSupport.equals(table.Rows[gridMain.getSelectedIndices()[i]]["Email"].ToString(), ""))
                {
                    skipped++;
                    gridMain.SetSelected(gridMain.getSelectedIndices()[i], false);
                }
                 
            }
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"None of the selected patients had email addresses entered.");
                return ;
            }
             
            if (skipped > 0)
            {
                MessageBox.Show(Lan.g(this,"Selected patients skipped due to missing email addresses: ") + skipped.ToString());
            }
             
        } 
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Send email to all of the selected patients?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        List<long> recallNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            recallNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()));
        }
        RecallListSort sortBy = (RecallListSort)comboSort.SelectedIndex;
        addrTable = Recalls.GetAddrTable(recallNums, checkGroupFamilies.Checked, sortBy);
        EmailMessage message;
        String str = "";
        String[] recallNumArray = new String[]();
        String[] patNumArray = new String[]();
        EmailAddress emailAddress;
        for (int i = 0;i < addrTable.Rows.Count;i++)
        {
            message = new EmailMessage();
            message.PatNum = PIn.Long(addrTable.Rows[i]["emailPatNum"].ToString());
            message.ToAddress = PIn.String(addrTable.Rows[i]["email"].ToString());
            //might be guarantor email
            emailAddress = EmailAddresses.GetByClinic(PIn.Long(addrTable.Rows[i]["ClinicNum"].ToString()));
            message.FromAddress = emailAddress.SenderAddress;
            if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "0"))
            {
                message.Subject = PrefC.getString(PrefName.RecallEmailSubject);
            }
            else if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "1"))
            {
                message.Subject = PrefC.getString(PrefName.RecallEmailSubject2);
            }
            else
            {
                message.Subject = PrefC.getString(PrefName.RecallEmailSubject3);
            }  
            //family
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[i]["famList"].ToString(), ""))
            {
                if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "0"))
                {
                    str = PrefC.getString(PrefName.RecallEmailFamMsg);
                }
                else if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "1"))
                {
                    str = PrefC.getString(PrefName.RecallEmailFamMsg2);
                }
                else
                {
                    str = PrefC.getString(PrefName.RecallEmailFamMsg3);
                }  
                str = str.Replace("[FamilyList]", addrTable.Rows[i]["famList"].ToString());
            }
            else
            {
                //single
                if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "0"))
                {
                    str = PrefC.getString(PrefName.RecallEmailMessage);
                }
                else if (StringSupport.equals(addrTable.Rows[i]["numberOfReminders"].ToString(), "1"))
                {
                    str = PrefC.getString(PrefName.RecallEmailMessage2);
                }
                else
                {
                    str = PrefC.getString(PrefName.RecallEmailMessage3);
                }  
                str = str.Replace("[DueDate]", PIn.Date(addrTable.Rows[i]["dateDue"].ToString()).ToShortDateString());
                str = str.Replace("[NameF]", addrTable.Rows[i]["patientNameF"].ToString());
                str = str.Replace("[NameFL]", addrTable.Rows[i]["patientNameFL"].ToString());
            } 
            message.BodyText = str;
            try
            {
                EmailMessages.sendEmailUnsecure(message,emailAddress);
            }
            catch (Exception ex)
            {
                Cursor = Cursors.Default;
                str = ex.Message + "\r\n";
                if (ex.GetType() == System.ArgumentException.class)
                {
                    str += "Go to Setup, Recall.  The subject for an email may not be multiple lines.\r\n";
                }
                 
                MessageBox.Show(str + "Patient:" + addrTable.Rows[i]["patientNameFL"].ToString());
                break;
            }

            message.MsgDateTime = DateTime.Now;
            message.SentOrReceived = EmailSentOrReceived.Sent;
            EmailMessages.insert(message);
            recallNumArray = addrTable.Rows[i]["recallNums"].ToString().Split(',');
            patNumArray = addrTable.Rows[i]["patNums"].ToString().Split(',');
            for (int r = 0;r < recallNumArray.Length;r++)
            {
                Commlogs.InsertForRecall(PIn.Long(patNumArray[r]), CommItemMode.Email, PIn.Int(addrTable.Rows[i]["numberOfReminders"].ToString()), PrefC.getLong(PrefName.RecallStatusEmailed));
                Recalls.UpdateStatus(PIn.Long(recallNumArray[r]), PrefC.getLong(PrefName.RecallStatusEmailed));
            }
        }
        fillMain(null);
        Cursor = Cursors.Default;
    }

    /**
    * raised for each page to be printed.
    */
    private void pdLabels_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        int totalPages = (int)Math.Ceiling((double)addrTable.Rows.Count / 30);
        Graphics g = ev.Graphics;
        float yPos = 63;
        //75;
        float xPos = 50;
        String text = "";
        while (yPos < 1000 && patientsPrinted < addrTable.Rows.Count)
        {
            text = "";
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[patientsPrinted]["famList"].ToString(), ""))
            {
                //print family label
                text = addrTable.Rows[patientsPrinted]["guarLName"].ToString() + " " + Lan.g(this,"Household") + "\r\n";
            }
            else
            {
                //print single label
                text = addrTable.Rows[patientsPrinted]["patientNameFL"].ToString() + "\r\n";
            } 
            text += addrTable.Rows[patientsPrinted]["address"].ToString() + "\r\n";
            text += addrTable.Rows[patientsPrinted]["City"].ToString() + ", " + addrTable.Rows[patientsPrinted]["State"].ToString() + " " + addrTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
            g.DrawString(text, new Font(FontFamily.GenericSansSerif, 11), Brushes.Black, xPos, yPos);
            //reposition for next label
            xPos += 275;
            if (xPos > 850)
            {
                //drop a line
                xPos = 50;
                yPos += 100;
            }
             
            patientsPrinted++;
        }
        pagesPrinted++;
        if (pagesPrinted == totalPages)
        {
            ev.HasMorePages = false;
            pagesPrinted = 0;
            //because it has to print again from the print preview
            patientsPrinted = 0;
        }
        else
        {
            ev.HasMorePages = true;
        } 
    }

    /**
    * raised for each page to be printed.
    */
    private void pdCards_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        int totalPages = (int)Math.Ceiling((double)addrTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet));
        Graphics g = ev.Graphics;
        int yAdj = (int)(PrefC.getDouble(PrefName.RecallAdjustDown) * 100);
        int xAdj = (int)(PrefC.getDouble(PrefName.RecallAdjustRight) * 100);
        float yPos = 0 + yAdj;
        //these refer to the upper left origin of each postcard
        float xPos = 0 + xAdj;
        long clinicNum = new long();
        Clinic clinic;
        String str = new String();
        while (yPos < ev.PageBounds.Height - 100 && patientsPrinted < addrTable.Rows.Count)
        {
            //Return Address--------------------------------------------------------------------------
            clinicNum = PIn.Long(addrTable.Rows[patientsPrinted]["ClinicNum"].ToString());
            if (PrefC.getBool(PrefName.RecallCardsShowReturnAdd))
            {
                //if using clinics
                if (!PrefC.getBool(PrefName.EasyNoClinics) && Clinics.getList().Length > 0 && Clinics.getClinic(clinicNum) != null)
                {
                    //and this patient assigned to a clinic
                    clinic = Clinics.getClinic(clinicNum);
                    str = clinic.Description + "\r\n";
                    g.DrawString(str, new Font(FontFamily.GenericSansSerif, 9, FontStyle.Bold), Brushes.Black, xPos + 45, yPos + 60);
                    str = clinic.Address + "\r\n";
                    if (!StringSupport.equals(clinic.Address2, ""))
                    {
                        str += clinic.Address2 + "\r\n";
                    }
                     
                    str += clinic.City + ",  " + clinic.State + "  " + clinic.Zip + "\r\n";
                    String phone = clinic.Phone;
                    if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && phone.Length == 10)
                    {
                        str += "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
                    }
                    else
                    {
                        //any other phone format
                        str += phone;
                    } 
                }
                else
                {
                    str = PrefC.getString(PrefName.PracticeTitle) + "\r\n";
                    g.DrawString(str, new Font(FontFamily.GenericSansSerif, 9, FontStyle.Bold), Brushes.Black, xPos + 45, yPos + 60);
                    str = PrefC.getString(PrefName.PracticeAddress) + "\r\n";
                    if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                    {
                        str += PrefC.getString(PrefName.PracticeAddress2) + "\r\n";
                    }
                     
                    str += PrefC.getString(PrefName.PracticeCity) + ",  " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip) + "\r\n";
                    String phone = PrefC.getString(PrefName.PracticePhone);
                    if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && phone.Length == 10)
                    {
                        str += "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
                    }
                    else
                    {
                        //any other phone format
                        str += phone;
                    } 
                } 
                g.DrawString(str, new Font(FontFamily.GenericSansSerif, 8), Brushes.Black, xPos + 45, yPos + 75);
            }
             
            //Body text, family card ------------------------------------------------------------------
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[patientsPrinted]["famList"].ToString(), ""))
            {
                if (StringSupport.equals(addrTable.Rows[patientsPrinted]["numberOfReminders"].ToString(), "0"))
                {
                    str = PrefC.getString(PrefName.RecallPostcardFamMsg);
                }
                else if (StringSupport.equals(addrTable.Rows[patientsPrinted]["numberOfReminders"].ToString(), "1"))
                {
                    str = PrefC.getString(PrefName.RecallPostcardFamMsg2);
                }
                else
                {
                    str = PrefC.getString(PrefName.RecallPostcardFamMsg3);
                }  
                str = str.Replace("[FamilyList]", addrTable.Rows[patientsPrinted]["famList"].ToString());
            }
            else
            {
                //Body text, single card-------------------------------------------------------------------
                if (StringSupport.equals(addrTable.Rows[patientsPrinted]["numberOfReminders"].ToString(), "0"))
                {
                    str = PrefC.getString(PrefName.RecallPostcardMessage);
                }
                else if (StringSupport.equals(addrTable.Rows[patientsPrinted]["numberOfReminders"].ToString(), "1"))
                {
                    str = PrefC.getString(PrefName.RecallPostcardMessage2);
                }
                else
                {
                    str = PrefC.getString(PrefName.RecallPostcardMessage3);
                }  
                str = str.Replace("[DueDate]", addrTable.Rows[patientsPrinted]["dateDue"].ToString());
                str = str.Replace("[NameF]", addrTable.Rows[patientsPrinted]["patientNameF"].ToString());
                str = str.Replace("[NameFL]", addrTable.Rows[patientsPrinted]["patientNameFL"].ToString());
            } 
            g.DrawString(str, new Font(FontFamily.GenericSansSerif, 10), Brushes.Black, new RectangleF(xPos + 45, yPos + 180, 250, 190));
            //Patient's Address-----------------------------------------------------------------------
            if (checkGroupFamilies.Checked && !StringSupport.equals(addrTable.Rows[patientsPrinted]["famList"].ToString(), ""))
            {
                //print family card
                str = addrTable.Rows[patientsPrinted]["guarLName"].ToString() + " " + Lan.g(this,"Household") + "\r\n";
            }
            else
            {
                //print single card
                str = addrTable.Rows[patientsPrinted]["patientNameFL"].ToString() + "\r\n";
            } 
            str += addrTable.Rows[patientsPrinted]["address"].ToString() + "\r\n";
            str += addrTable.Rows[patientsPrinted]["City"].ToString() + ", " + addrTable.Rows[patientsPrinted]["State"].ToString() + " " + addrTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
            g.DrawString(str, new Font(FontFamily.GenericSansSerif, 11), Brushes.Black, xPos + 320, yPos + 240);
            if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 1)
            {
                yPos += 400;
            }
            else if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 3)
            {
                yPos += 366;
            }
            else
            {
                //4
                xPos += 550;
                if (xPos > 1000)
                {
                    xPos = 0 + xAdj;
                    yPos += 425;
                }
                 
            }  
            patientsPrinted++;
        }
        //while
        pagesPrinted++;
        if (pagesPrinted == totalPages)
        {
            ev.HasMorePages = false;
            pagesPrinted = 0;
            patientsPrinted = 0;
        }
        else
        {
            ev.HasMorePages = true;
        } 
    }

    private void butRefresh_Click(Object sender, System.EventArgs e) throws Exception {
        gridMain.setSelected(false);
        fillMain(null);
    }

    private void butSetStatus_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        //bool makeCommEntries=MsgBox.Show(this,MsgBoxButtons.OKCancel,"Add Commlog (reminder) entries for each patient?");
        long newStatus = 0;
        if (comboStatus.SelectedIndex > 0)
        {
            newStatus = DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][comboStatus.SelectedIndex - 1].DefNum;
        }
         
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            Recalls.UpdateStatus(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["RecallNum"].ToString()), newStatus);
        }
        //show the first one, and then make all the others very similar
        Commlog CommlogCur = new Commlog();
        CommlogCur.PatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.SentOrReceived = CommSentOrReceived.Sent;
        CommlogCur.Mode_ = CommItemMode.Phone;
        //user can change this, of course.
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.RECALL);
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        CommlogCur.Note = Lan.g(this,"Recall reminder.");
        if (comboStatus.SelectedIndex > 0)
        {
            CommlogCur.Note += "  " + DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][comboStatus.SelectedIndex - 1].ItemName;
        }
        else
        {
            CommlogCur.Note += "  " + Lan.g(this,"Status None");
        } 
        FormCommItem FormCI = new FormCommItem(CommlogCur);
        FormCI.IsNew = true;
        FormCI.ShowDialog();
        if (FormCI.DialogResult != DialogResult.OK)
        {
            //if user cancels, then the other comm entries won't go in either
            fillMain(null);
            return ;
        }
         
        for (int i = 1;i < gridMain.getSelectedIndices().Length;i++)
        {
            CommlogCur.PatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString());
            Commlogs.insert(CommlogCur);
        }
        fillMain(null);
    }

    private void menuItemSeeFamily_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.FamilyModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        GotoModule.gotoFamily(patNum);
    }

    private void menuItemSeeAccount_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        //WindowState=FormWindowState.Minimized;
        long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        GotoModule.gotoAccount(patNum);
    }

    private void butGotoFamily_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.FamilyModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        WindowState = FormWindowState.Minimized;
        long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        GotoModule.gotoFamily(patNum);
    }

    private void butGotoAccount_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        WindowState = FormWindowState.Minimized;
        long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        GotoModule.gotoAccount(patNum);
    }

    private void butCommlog_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        //show the first one, and then make all the others very similar
        Commlog CommlogCur = new Commlog();
        CommlogCur.PatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.SentOrReceived = CommSentOrReceived.Sent;
        CommlogCur.Mode_ = CommItemMode.Phone;
        //user can change this, of course.
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.RECALL);
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        FormCommItem FormCI = new FormCommItem(CommlogCur);
        FormCI.IsNew = true;
        FormCI.ShowDialog();
        if (FormCI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 1;i < gridMain.getSelectedIndices().Length;i++)
        {
            CommlogCur.PatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString());
            Commlogs.insert(CommlogCur);
        }
        fillMain(null);
    }

    /*
    		private void MakeCommlogNoDisplay(long patNum,long newStatus) {
    			//unless there is an existing recall commlog entry for today
    			Commlog[] CommlogList=Commlogs.Refresh(patNum);
    			for(int i=0;i<CommlogList.Length;i++){
    				if(CommlogList[i].CommDateTime.Date==DateTime.Today	
    					&& CommlogList[i].CommType==Commlogs.GetTypeAuto(CommItemTypeAuto.RECALL))
    				{
    					return;
    				}
    			}
    			Commlog CommlogCur=new Commlog();
    			CommlogCur.CommDateTime=DateTime.Now;
    			CommlogCur.CommType=Commlogs.GetTypeAuto(CommItemTypeAuto.RECALL);
    			CommlogCur.PatNum=patNum;
    			//if(newStatus!=RecallCur.RecallStatus){
    			//Commlogs.Cur.Note+=Lan.g(this,"Status changed to")+" ";
    			if(newStatus==0) {
    				CommlogCur.Note=Lan.g(this,"Status None");
    			}
    			else {
    				CommlogCur.Note=DefC.GetName(DefCat.RecallUnschedStatus,newStatus);
    			}
    			CommlogCur.UserNum=Security.CurUser.UserNum;
    			//FormCommItem FormCI=new FormCommItem(CommlogCur);
    			//FormCI.IsNew=true;
    			//forces user to at least consider a commlog entry
    			//FormCI.ShowDialog();//typically saved in this window.
    			Commlogs.InsertForRecall(			
    		}*/
    /*private void butSave_Click(object sender, System.EventArgs e) {
    			if(  textDateStart.errorProvider1.GetError(textDateStart)!=""
    				|| textDateEnd.errorProvider1.GetError(textDateEnd)!="")
    			{
    				MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
    				return;
    			}
    			int daysPast=((TimeSpan)(DateTime.Today-PIn.PDate(textDateStart.Text))).Days;//can be neg
    			int daysFuture=((TimeSpan)(PIn.PDate(textDateEnd.Text)-DateTime.Today)).Days;//can be neg
    			if(Prefs.UpdateBool(PrefName.RecallGroupByFamily",checkGroupFamilies.Checked)
    				| Prefs.UpdateLong(PrefName.RecallDaysPast",daysPast)
    				| Prefs.UpdateLong(PrefName.RecallDaysFuture",daysFuture))
    			{
    				DataValid.SetInvalid(InvalidTypes.Prefs);
    			}
    		}*/
    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        pd.DefaultPageSettings.Landscape = true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Recall list printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Recall List");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = textDateStart.Text + " " + Lan.g(this,"to") + " " + textDateEnd.Text;
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formRecallList_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 1)
        {
            SelectedPatNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        }
         
    }

}


/*
	///<summary>Mostly used just to display the recall list.</summary>
	public class RecallItem{
		///<summary></summary>
		public DateTime DueDate;
		///<summary></summary>
		public string PatientName;
		//<summary></summary>
		//public DateTime BirthDate;
		///<summary></summary>
		public Interval RecallInterval;
		///<summary></summary>
		public int RecallStatus;
		///<summary></summary>
		public int PatNum;
		///<summary>Stored as a string because it might be blank.</summary>
		public string Age;
		///<summary></summary>
		public string Note;
		///<summary></summary>
		public int RecallNum;
		///<summary></summary>
		public int Guarantor;
	}*/