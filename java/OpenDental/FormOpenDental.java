//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiButtonClickedEventHandler;
import OpenDental.__MultiModuleEventHandler;
import OpenDental.__MultiPatientSelectedEventHandler;
import OpenDental.__MultiValidEventHandler;
import OpenDental.AppointmentL;
import OpenDental.AutomationL;
import OpenDental.Bridges.ECW;
import OpenDental.Bridges.ICat;
import OpenDental.Bridges.PaperlessTechnology;
import OpenDental.Bridges.Trojan;
import OpenDental.ButtonClicked_EventArgs;
import OpenDental.ButtonClickedEventHandler;
import OpenDental.ContrAccount;
import OpenDental.ContrAppt;
import OpenDental.ContrApptSingle;
import OpenDental.ContrChart;
import OpenDental.ContrFamily;
import OpenDental.ContrFamilyEcw;
import OpenDental.ContrStaff;
import OpenDental.ContrTreat;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormAging;
import OpenDental.FormApptFieldDefs;
import OpenDental.FormApptRules;
import OpenDental.FormApptViews;
import OpenDental.FormAtoZFoldersCreate;
import OpenDental.FormAudit;
import OpenDental.FormAutoCode;
import OpenDental.FormAutomation;
import OpenDental.FormAutoNotes;
import OpenDental.FormBackupReminder;
import OpenDental.FormBlockoutDuplicatesFix;
import OpenDental.FormCarriers;
import OpenDental.FormChooseDatabase;
import OpenDental.FormClaimForms;
import OpenDental.FormClearinghouses;
import OpenDental.FormClinics;
import OpenDental.FormCommItem;
import OpenDental.FormComputers;
import OpenDental.FormContacts;
import OpenDental.FormCounties;
import OpenDental.FormCreditRecurringCharges;
import OpenDental.FormCustomerManagement;
import OpenDental.FormDatabaseMaintenance;
import OpenDental.FormDefinitions;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormDisplayFieldCategories;
import OpenDental.FormEhrSetup;
import OpenDental.FormEhrTimeSynch;
import OpenDental.FormElectIDs;
import OpenDental.FormEmailAddresses;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormEmployeeSelect;
import OpenDental.FormEmployers;
import OpenDental.FormFeatureRequest;
import OpenDental.FormFeeScheds;
import OpenDental.FormFinanceCharges;
import OpenDental.FormGraphics;
import OpenDental.FormHL7Defs;
import OpenDental.FormImagingSetup;
import OpenDental.FormImportXML;
import OpenDental.FormInsCatsSetup;
import OpenDental.FormInsFilingCodes;
import OpenDental.FormInsPlans;
import OpenDental.FormLabCases;
import OpenDental.FormLaboratories;
import OpenDental.FormLetterMerges;
import OpenDental.FormLetters;
import OpenDental.FormLogoffWarning;
import OpenDental.FormLogOn;
import OpenDental.FormMapHQ;
import OpenDental.FormMedications;
import OpenDental.FormMessagingButSetup;
import OpenDental.FormMessagingSetup;
import OpenDental.FormMisc;
import OpenDental.FormMobile;
import OpenDental.FormModuleSetup;
import OpenDental.FormNewCropBilling;
import OpenDental.FormOpenDental;
import OpenDental.FormOperatories;
import OpenDental.FormPatFieldDefs;
import OpenDental.FormPath;
import OpenDental.FormPatientForms;
import OpenDental.FormPatientMerge;
import OpenDental.FormPatientSelect;
import OpenDental.FormPharmacies;
import OpenDental.FormPhoneTiles;
import OpenDental.FormPopupDisplay;
import OpenDental.FormPopupsForFam;
import OpenDental.FormPractice;
import OpenDental.FormPrinterSetup;
import OpenDental.FormPrntScrn;
import OpenDental.FormProcButtons;
import OpenDental.FormProcCodes;
import OpenDental.FormProcLockTool;
import OpenDental.FormProgramLinks;
import OpenDental.FormProviderSelect;
import OpenDental.FormQuestionDefs;
import OpenDental.FormRecallSetup;
import OpenDental.FormRecallTypes;
import OpenDental.FormReferralSelect;
import OpenDental.FormReferralsPatient;
import OpenDental.FormRegistrationKey;
import OpenDental.FormRepeatChargesUpdate;
import OpenDental.FormReplicationSetup;
import OpenDental.FormReportCustom;
import OpenDental.FormReportsMore;
import OpenDental.FormReqNeededs;
import OpenDental.FormReqStudentOne;
import OpenDental.FormReqStudentsMany;
import OpenDental.FormResellers;
import OpenDental.FormRpOutstandingIns;
import OpenDental.FormRpTreatmentFinder;
import OpenDental.FormRxSetup;
import OpenDental.FormSchedule;
import OpenDental.FormSchoolClasses;
import OpenDental.FormSchoolCourses;
import OpenDental.FormScreenGroups;
import OpenDental.FormSecurity;
import OpenDental.FormSheetDefs;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormSheetPicker;
import OpenDental.FormShowFeatures;
import OpenDental.FormShutdown;
import OpenDental.FormSites;
import OpenDental.FormSpellCheck;
import OpenDental.FormSplash;
import OpenDental.FormTaskEdit;
import OpenDental.FormTaskListSelect;
import OpenDental.FormTelephone;
import OpenDental.FormTerminal;
import OpenDental.FormTerminalManager;
import OpenDental.FormTestLatency;
import OpenDental.FormTimeCardSetup;
import OpenDental.FormTranslationCat;
import OpenDental.FormTxtMsgEdit;
import OpenDental.FormUAppoint;
import OpenDental.FormUpdate;
import OpenDental.FormUserPassword;
import OpenDental.FormWebForms;
import OpenDental.FormWebMailMessageEdit;
import OpenDental.FormWiki;
import OpenDental.FormXChargeReconcile;
import OpenDental.FormZipCodes;
import OpenDental.GotoModule;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.ModuleEventArgs;
import OpenDental.ModuleEventHandler;
import OpenDental.MsgBox;
import OpenDental.OutlookBar;
import OpenDental.PatientL;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PhoneUI;
import OpenDental.PIn;
import OpenDental.PopupEvent;
import OpenDental.PrefL;
import OpenDental.ProgramL;
import OpenDental.Properties.Resources;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.ReportModalSelection;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODLightSignalGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODLightSignalGridClickEventArgs;
import OpenDental.UserControlPhoneSmall;
import OpenDental.ValidEventArgs;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.Computers;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.Db;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Family;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.LanguageForeigns;
import OpenDentBusiness.Lans;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.NTPv4;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.Popup;
import OpenDentBusiness.Popups;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.SigButDefElements;
import OpenDentBusiness.SigButDefs;
import OpenDentBusiness.SigElement;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.SigElements;
import OpenDentBusiness.SignalElementType;
import OpenDentBusiness.Signalod;
import OpenDentBusiness.Signalods;
import OpenDentBusiness.SignalType;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskNote;
import OpenDentBusiness.TaskNotes;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;
import OpenDentBusiness.YN;

/*=============================================================================================================
Open Dental is a dental practice management program.
Copyright (C) 2003-2013  Jordan Sparks, DMD.  http://www.opendental.com
This program is free software; you can redistribute it and/or modify it under the terms of the
GNU Db Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.
This program is distributed in the hope that it will be useful, but without any warranty. See the GNU Db Public License
for more details, available at http://www.opensource.org/licenses/gpl-license.php
Any changes to this program must follow the guidelines of the GPL license if a modified version is to be
redistributed.
===============================================================================================================*/
//#define ORA_DB
//using OpenDental.SmartCards;
//#if(ORA_DB)
//using OD_CRYPTO;
//#endif
/**
* 
*/
public class FormOpenDental  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    //private bool[,] buttonDown=new bool[2,6];
    private System.Windows.Forms.Timer timerTimeIndic = new System.Windows.Forms.Timer();
    private System.Windows.Forms.MainMenu mainMenu = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSettings = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemReports = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemPrinter = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemImaging = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemDataPath = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemConfig = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemAutoCodes = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemDefinitions = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemInsCats = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemLinks = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemRecall = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemEmployees = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemPractice = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemPrescriptions = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemProviders = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemProcCodes = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemPrintScreen = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemFinanceCharge = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemAging = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSched = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem5 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem6 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemTranslation = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemFile = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem7 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemLists = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemTools = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemReferrals = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemExit = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemDatabaseMaintenance = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemProcedureButtons = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemZipCodes = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem1 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuTelephone = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem9 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemHelpIndex = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemClaimForms = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemContacts = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemMedications = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.ImageList imageList32 = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.MenuItem menuItemApptViews = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemComputers = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemEmployers = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemEasy = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemCarriers = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSchools = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemCounties = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemScreening = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemEmail = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemHelpContents = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemHelp = new System.Windows.Forms.MenuItem();
    /**
    * The only reason this is public static is so that it can be seen from the terminal manager.  Otherwise, it's passed around properly.  I also used it in UserControlPhonePanel for simplicity
    */
    public static long CurPatNum = new long();
    private System.Windows.Forms.MenuItem menuItemClearinghouses = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemUpdate = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemHelpWindows = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemMisc = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemRemote = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSchoolClass = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSchoolCourses = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSecurity = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemLogOff = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemInsPlans = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemClinics = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemOperatories = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.Timer timerSignals = new System.Windows.Forms.Timer();
    /**
    * When user logs out, this keeps track of where they were for when they log back in.
    */
    private int LastModule = new int();
    private System.Windows.Forms.MenuItem menuItemRepeatingCharges = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemImportXML = new System.Windows.Forms.MenuItem();
    private MenuItem menuItemTimeCards = new MenuItem();
    private MenuItem menuItemApptRules = new MenuItem();
    private MenuItem menuItemAuditTrail = new MenuItem();
    private MenuItem menuItemPatFieldDefs = new MenuItem();
    private MenuItem menuItemProblems = new MenuItem();
    private MenuItem menuItemTerminal = new MenuItem();
    private MenuItem menuItemTerminalManager = new MenuItem();
    private MenuItem menuItemQuestions = new MenuItem();
    private MenuItem menuItemCustomReports = new MenuItem();
    private MenuItem menuItemMessaging = new MenuItem();
    private OpenDental.UI.LightSignalGrid lightSignalGrid1;
    private MenuItem menuItemMessagingButs = new MenuItem();
    /**
    * This is not the actual date/time last refreshed.  It is really the date/time of the last item in the database retrieved on previous refreshes.  That way, the local workstation time is irrelevant.
    */
    public static DateTime signalLastRefreshed = new DateTime();
    private FormSplash Splash;
    private Bitmap bitmapIcon = new Bitmap();
    private MenuItem menuItemCreateAtoZFolders = new MenuItem();
    private MenuItem menuItemLaboratories = new MenuItem();
    /**
    * A list of button definitions for this computer.
    */
    private SigButDef[] SigButDefList = new SigButDef[]();
    /**
    * Added these 3 fields for Oracle encrypted connection string
    */
    private String connStr = new String();
    private String key = new String();
    private MenuItem menuItemGraphics = new MenuItem();
    private MenuItem menuItemLabCases = new MenuItem();
    private MenuItem menuItemRequirementsNeeded = new MenuItem();
    private MenuItem menuItemReqStudents = new MenuItem();
    private MenuItem menuItemAutoNotes = new MenuItem();
    private MenuItem menuItemDisplayFields = new MenuItem();
    private Panel panelSplitter = new Panel();
    //private string dconnStr;
    private boolean MouseIsDownOnSplitter = new boolean();
    private Point SplitterOriginalLocation = new Point();
    private ContextMenu menuSplitter = new ContextMenu();
    private MenuItem menuItemDockBottom = new MenuItem();
    private MenuItem menuItemDockRight = new MenuItem();
    private ImageList imageListMain = new ImageList();
    private ContextMenu menuPatient = new ContextMenu();
    private ContextMenu menuLabel = new ContextMenu();
    private ContextMenu menuEmail = new ContextMenu();
    private ContextMenu menuLetter = new ContextMenu();
    private Point OriginalMousePos = new Point();
    private MenuItem menuItemCustomerManage = new MenuItem();
    private System.Windows.Forms.Timer timerDisabledKey = new System.Windows.Forms.Timer();
    /**
    * This list will only contain events for this computer where the users clicked to disable a popup for a specified period of time.  So it won't typically have many items in it.
    */
    private List<PopupEvent> PopupEventList = new List<PopupEvent>();
    private MenuItem menuItemPharmacies = new MenuItem();
    private MenuItem menuItemSheets = new MenuItem();
    private MenuItem menuItemRequestFeatures = new MenuItem();
    private MenuItem menuItemModules = new MenuItem();
    private MenuItem menuItemRecallTypes = new MenuItem();
    private MenuItem menuItemFeeScheds = new MenuItem();
    private MenuItem menuItemMobileSetup = new MenuItem();
    private MenuItem menuItemLetters = new MenuItem();
    //private UserControlPhonePanel phonePanel;
    /**
    * Command line args passed in when program starts.
    */
    public String[] CommandLineArgs = new String[]();
    private Thread ThreadCommandLine = new Thread();
    /**
    * Listens for commands coming from other instances of Open Dental on this computer.
    */
    private TcpListener TcpListenerCommandLine = new TcpListener();
    /**
    * True if there is already a different instance of OD running.  This prevents attempting to start the listener.
    */
    public boolean IsSecondInstance = new boolean();
    private OpenDental.UserControlTasks userControlTasks1;
    private ContrAppt ContrAppt2;
    private ContrFamily ContrFamily2;
    private ContrFamilyEcw ContrFamily2Ecw;
    private ContrAccount ContrAccount2;
    private ContrTreat ContrTreat2;
    private ContrChart ContrChart2;
    private OpenDental.ContrImages ContrImages2;
    private ContrStaff ContrManage2;
    private OutlookBar myOutlookBar;
    private MenuItem menuItemShutdown = new MenuItem();
    private System.Windows.Forms.Timer timerHeartBeat = new System.Windows.Forms.Timer();
    private MenuItem menuItemInsFilingCodes = new MenuItem();
    private MenuItem menuItemReplication = new MenuItem();
    private MenuItem menuItemAutomation = new MenuItem();
    private MenuItem menuItemMergePatients = new MenuItem();
    private MenuItem menuItemDuplicateBlockouts = new MenuItem();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private MenuItem menuItemPassword = new MenuItem();
    private MenuItem menuItem3 = new MenuItem();
    private MenuItem menuApptFieldDefs = new MenuItem();
    private MenuItem menuItemWebForms = new MenuItem();
    private System.Windows.Forms.Timer timerPhoneWebCam = new System.Windows.Forms.Timer();
    private FormTerminalManager formTerminalManager;
    private FormPhoneTiles formPhoneTiles;
    private FormMapHQ formMapHQ;
    private System.Windows.Forms.Timer timerWebHostSynch = new System.Windows.Forms.Timer();
    private MenuItem menuItemCCRecurring = new MenuItem();
    private UserControlPhoneSmall phoneSmall;
    /**
    * //This will be null if EHR didn't load up.  EHRTEST conditional compilation constant is used because the EHR project is only part of the solution here at HQ.  We need to use late binding in a few places so that it will still compile for people who download our sourcecode.  But late binding prevents us from stepping through for debugging, so the EHRTEST lets us switch to early binding.
    */
    //public static object ObjFormEhrMeasures;
    private MenuItem menuItemEHR = new MenuItem();
    private System.Windows.Forms.Timer timerLogoff = new System.Windows.Forms.Timer();
    //<summary>This will be null if EHR didn't load up.</summary>
    //public static Assembly AssemblyEHR;
    /**
    * The last local datetime that there was any mouse or keyboard activity.  Used for auto logoff comparison.
    */
    private DateTime dateTimeLastActivity = new DateTime();
    private Form FormRecentlyOpenForLogoff = new Form();
    private Point locationMouseForLogoff = new Point();
    private MenuItem menuItemPayerIDs = new MenuItem();
    private MenuItem menuItemTestLatency = new MenuItem();
    private FormLogOn FormLogOn_;
    private System.Windows.Forms.Timer timerReplicationMonitor = new System.Windows.Forms.Timer();
    /**
    * When auto log off is in use, we don't want to log off user if they are in the FormLogOn window.  Mostly a problem when using web service because CurUser is not null.
    */
    private boolean IsFormLogOnLastActive = new boolean();
    private Panel panelPhoneSmall = new Panel();
    private OpenDental.UI.Button butTriage;
    private OpenDental.UI.Button butBigPhones;
    private Label labelWaitTime = new Label();
    private Label labelTriage = new Label();
    private Label labelMsg = new Label();
    /**
    * This thread fills labelMsg
    */
    private Thread ThreadVM = new Thread();
    private MenuItem menuItemWiki = new MenuItem();
    private MenuItem menuItemProcLockTool = new MenuItem();
    private MenuItem menuItemHL7 = new MenuItem();
    private MenuItem menuItemNewCropBilling = new MenuItem();
    private MenuItem menuItemSpellCheck = new MenuItem();
    private FormWiki FormMyWiki;
    private MenuItem menuItemResellers = new MenuItem();
    private MenuItem menuItemXChargeReconcile = new MenuItem();
    private FormCreditRecurringCharges FormCRC;
    private OpenDental.UI.Button butMapPhones;
    private ComboBox comboTriageCoordinator = new ComboBox();
    private Label labelFieldType = new Label();
    /**
    * If the local computer is the computer where incoming email is fetched, then this thread runs in the background and checks for new messages
    * every x number of minutes (1 to 60) based on preference value.
    */
    private Thread ThreadEmailInbox = new Thread();
    private boolean _isEmailThreadRunning = false;
    private AutoResetEvent _emailSleep = new AutoResetEvent(false);
    /**
    * If OpenDental is running on the same machine as the mysql server, then a thread is runs in the background to update the local machine's time
    * using NTPv4 from the NIST time server set in the NistTimeServerUrl pref.///
    */
    private Thread _threadTimeSynch = new Thread();
    private boolean _isTimeSynchThreadRunning = false;
    private AutoResetEvent _timeSynchSleep = new AutoResetEvent(false);
    /**
    * 
    */
    public FormOpenDental(String[] cla) throws Exception {
        Logger.openlog.log("Initializing Open Dental...",Severity.INFO);
        CommandLineArgs = cla;
        Splash = new FormSplash();
        if (CommandLineArgs.Length == 0)
        {
            Splash.Show();
        }
         
        initializeComponent();
        SystemEvents.SessionSwitch += new SessionSwitchEventHandler(SystemEvents_SessionSwitch);
        //toolbar
        ToolBarMain = new OpenDental.UI.ODToolBar();
        ToolBarMain.Location = new Point(51, 0);
        ToolBarMain.Size = new Size(931, 25);
        ToolBarMain.Dock = DockStyle.Top;
        ToolBarMain.setImageList(imageListMain);
        ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                toolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ToolBarMain);
        //outlook bar
        myOutlookBar = new OutlookBar();
        myOutlookBar.Location = new Point(0, 0);
        myOutlookBar.Size = new Size(51, 626);
        myOutlookBar.setImageList(imageList32);
        myOutlookBar.Dock = DockStyle.Left;
        myOutlookBar.ButtonClicked = __MultiButtonClickedEventHandler.combine(myOutlookBar.ButtonClicked,new ButtonClickedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ButtonClicked_EventArgs e) throws Exception {
                myOutlookBar_ButtonClicked(sender, e);
            }

            public List<ButtonClickedEventHandler> getInvocationList() throws Exception {
                List<ButtonClickedEventHandler> ret = new ArrayList<ButtonClickedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(myOutlookBar);
        //contrAppt
        ContrAppt2 = new ContrAppt();
        ContrAppt2.Visible = false;
        ContrAppt2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrAppt2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrAppt2);
        //contrFamily
        ContrFamily2 = new ContrFamily();
        ContrFamily2.Visible = false;
        ContrFamily2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrFamily2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrFamily2);
        //contrFamilyEcw
        ContrFamily2Ecw = new ContrFamilyEcw();
        ContrFamily2Ecw.Visible = false;
        //ContrFamily2Ecw.PatientSelected+=new PatientSelectedEventHandler(Contr_PatientSelected);
        this.Controls.Add(ContrFamily2Ecw);
        //contrAccount
        ContrAccount2 = new ContrAccount();
        ContrAccount2.Visible = false;
        ContrAccount2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrAccount2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrAccount2);
        //contrTreat
        ContrTreat2 = new ContrTreat();
        ContrTreat2.Visible = false;
        ContrTreat2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrTreat2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrTreat2);
        //contrChart
        ContrChart2 = new ContrChart();
        ContrChart2.Visible = false;
        ContrChart2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrChart2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrChart2);
        //contrImages
        ContrImages2 = new OpenDental.ContrImages();
        ContrImages2.Visible = false;
        ContrImages2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrImages2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrImages2);
        //contrManage
        ContrManage2 = new ContrStaff();
        ContrManage2.Visible = false;
        ContrManage2.PatientSelected = __MultiPatientSelectedEventHandler.combine(ContrManage2.PatientSelected,new PatientSelectedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                contr_PatientSelected(sender, e);
            }

            public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(ContrManage2);
        //userControlTasks
        userControlTasks1 = new OpenDental.UserControlTasks();
        userControlTasks1.Visible = false;
        userControlTasks1.GoToChanged += new EventHandler(userControlTasks1_GoToChanged);
        GotoModule.ModuleSelected = __MultiModuleEventHandler.combine(GotoModule.ModuleSelected,new ModuleEventHandler() 
          { 
            public System.Void invoke(ModuleEventArgs e) throws Exception {
                gotoModule_ModuleSelected(e);
            }

            public List<ModuleEventHandler> getInvocationList() throws Exception {
                List<ModuleEventHandler> ret = new ArrayList<ModuleEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.Controls.Add(userControlTasks1);
        panelSplitter.ContextMenu = menuSplitter;
        menuItemDockBottom.Checked = true;
        phoneSmall = new UserControlPhoneSmall();
        phoneSmall.GoToChanged += new System.EventHandler(this.phoneSmall_GoToChanged);
        //phoneSmall.Visible=false;
        //this.Controls.Add(phoneSmall);
        panelPhoneSmall.Controls.Add(phoneSmall);
        panelPhoneSmall.Visible = false;
        //phonePanel=new UserControlPhonePanel();
        //phonePanel.Visible=false;
        //this.Controls.Add(phonePanel);
        //phonePanel.GoToChanged += new System.EventHandler(this.phonePanel_GoToChanged);
        Logger.openlog.log("Open Dental initialization complete.",Severity.INFO);
    }

    //Plugins.HookAddCode(this,"FormOpenDental.Constructor_end");//Can't do this because no plugins loaded.
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
             
            if (FormCRC != null)
            {
                FormCRC.Dispose();
            }
             
            if (FormMyWiki != null)
            {
                FormMyWiki.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormOpenDental.class);
        this.timerTimeIndic = new System.Windows.Forms.Timer(this.components);
        this.mainMenu = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemLogOff = new System.Windows.Forms.MenuItem();
        this.menuItemFile = new System.Windows.Forms.MenuItem();
        this.menuItemPassword = new System.Windows.Forms.MenuItem();
        this.menuItem3 = new System.Windows.Forms.MenuItem();
        this.menuItemPrinter = new System.Windows.Forms.MenuItem();
        this.menuItemGraphics = new System.Windows.Forms.MenuItem();
        this.menuItem6 = new System.Windows.Forms.MenuItem();
        this.menuItemConfig = new System.Windows.Forms.MenuItem();
        this.menuItem7 = new System.Windows.Forms.MenuItem();
        this.menuItemExit = new System.Windows.Forms.MenuItem();
        this.menuItemSettings = new System.Windows.Forms.MenuItem();
        this.menuApptFieldDefs = new System.Windows.Forms.MenuItem();
        this.menuItemApptRules = new System.Windows.Forms.MenuItem();
        this.menuItemApptViews = new System.Windows.Forms.MenuItem();
        this.menuItemAutoCodes = new System.Windows.Forms.MenuItem();
        this.menuItemAutomation = new System.Windows.Forms.MenuItem();
        this.menuItemAutoNotes = new System.Windows.Forms.MenuItem();
        this.menuItemClaimForms = new System.Windows.Forms.MenuItem();
        this.menuItemClearinghouses = new System.Windows.Forms.MenuItem();
        this.menuItemComputers = new System.Windows.Forms.MenuItem();
        this.menuItemDataPath = new System.Windows.Forms.MenuItem();
        this.menuItemDefinitions = new System.Windows.Forms.MenuItem();
        this.menuItemDisplayFields = new System.Windows.Forms.MenuItem();
        this.menuItemEmail = new System.Windows.Forms.MenuItem();
        this.menuItemEHR = new System.Windows.Forms.MenuItem();
        this.menuItemFeeScheds = new System.Windows.Forms.MenuItem();
        this.menuItemHL7 = new System.Windows.Forms.MenuItem();
        this.menuItemImaging = new System.Windows.Forms.MenuItem();
        this.menuItemInsCats = new System.Windows.Forms.MenuItem();
        this.menuItemInsFilingCodes = new System.Windows.Forms.MenuItem();
        this.menuItemLaboratories = new System.Windows.Forms.MenuItem();
        this.menuItemLetters = new System.Windows.Forms.MenuItem();
        this.menuItemMessaging = new System.Windows.Forms.MenuItem();
        this.menuItemMessagingButs = new System.Windows.Forms.MenuItem();
        this.menuItemMisc = new System.Windows.Forms.MenuItem();
        this.menuItemModules = new System.Windows.Forms.MenuItem();
        this.menuItemOperatories = new System.Windows.Forms.MenuItem();
        this.menuItemPatFieldDefs = new System.Windows.Forms.MenuItem();
        this.menuItemPayerIDs = new System.Windows.Forms.MenuItem();
        this.menuItemPractice = new System.Windows.Forms.MenuItem();
        this.menuItemProblems = new System.Windows.Forms.MenuItem();
        this.menuItemProcedureButtons = new System.Windows.Forms.MenuItem();
        this.menuItemLinks = new System.Windows.Forms.MenuItem();
        this.menuItemQuestions = new System.Windows.Forms.MenuItem();
        this.menuItemRecall = new System.Windows.Forms.MenuItem();
        this.menuItemRecallTypes = new System.Windows.Forms.MenuItem();
        this.menuItemReplication = new System.Windows.Forms.MenuItem();
        this.menuItemRequirementsNeeded = new System.Windows.Forms.MenuItem();
        this.menuItemSched = new System.Windows.Forms.MenuItem();
        this.menuItemSecurity = new System.Windows.Forms.MenuItem();
        this.menuItemSheets = new System.Windows.Forms.MenuItem();
        this.menuItemEasy = new System.Windows.Forms.MenuItem();
        this.menuItemSpellCheck = new System.Windows.Forms.MenuItem();
        this.menuItemTimeCards = new System.Windows.Forms.MenuItem();
        this.menuItemLists = new System.Windows.Forms.MenuItem();
        this.menuItemProcCodes = new System.Windows.Forms.MenuItem();
        this.menuItem5 = new System.Windows.Forms.MenuItem();
        this.menuItemClinics = new System.Windows.Forms.MenuItem();
        this.menuItemContacts = new System.Windows.Forms.MenuItem();
        this.menuItemCounties = new System.Windows.Forms.MenuItem();
        this.menuItemSchoolClass = new System.Windows.Forms.MenuItem();
        this.menuItemSchoolCourses = new System.Windows.Forms.MenuItem();
        this.menuItemEmployees = new System.Windows.Forms.MenuItem();
        this.menuItemEmployers = new System.Windows.Forms.MenuItem();
        this.menuItemCarriers = new System.Windows.Forms.MenuItem();
        this.menuItemInsPlans = new System.Windows.Forms.MenuItem();
        this.menuItemLabCases = new System.Windows.Forms.MenuItem();
        this.menuItemMedications = new System.Windows.Forms.MenuItem();
        this.menuItemPharmacies = new System.Windows.Forms.MenuItem();
        this.menuItemProviders = new System.Windows.Forms.MenuItem();
        this.menuItemPrescriptions = new System.Windows.Forms.MenuItem();
        this.menuItemReferrals = new System.Windows.Forms.MenuItem();
        this.menuItemSchools = new System.Windows.Forms.MenuItem();
        this.menuItemZipCodes = new System.Windows.Forms.MenuItem();
        this.menuItemReports = new System.Windows.Forms.MenuItem();
        this.menuItemCustomReports = new System.Windows.Forms.MenuItem();
        this.menuItemTools = new System.Windows.Forms.MenuItem();
        this.menuItemPrintScreen = new System.Windows.Forms.MenuItem();
        this.menuItem1 = new System.Windows.Forms.MenuItem();
        this.menuItemDuplicateBlockouts = new System.Windows.Forms.MenuItem();
        this.menuItemCreateAtoZFolders = new System.Windows.Forms.MenuItem();
        this.menuItemImportXML = new System.Windows.Forms.MenuItem();
        this.menuItemMergePatients = new System.Windows.Forms.MenuItem();
        this.menuItemProcLockTool = new System.Windows.Forms.MenuItem();
        this.menuItemShutdown = new System.Windows.Forms.MenuItem();
        this.menuTelephone = new System.Windows.Forms.MenuItem();
        this.menuItemTestLatency = new System.Windows.Forms.MenuItem();
        this.menuItemXChargeReconcile = new System.Windows.Forms.MenuItem();
        this.menuItem9 = new System.Windows.Forms.MenuItem();
        this.menuItemAging = new System.Windows.Forms.MenuItem();
        this.menuItemAuditTrail = new System.Windows.Forms.MenuItem();
        this.menuItemFinanceCharge = new System.Windows.Forms.MenuItem();
        this.menuItemCCRecurring = new System.Windows.Forms.MenuItem();
        this.menuItemCustomerManage = new System.Windows.Forms.MenuItem();
        this.menuItemDatabaseMaintenance = new System.Windows.Forms.MenuItem();
        this.menuItemTerminal = new System.Windows.Forms.MenuItem();
        this.menuItemTerminalManager = new System.Windows.Forms.MenuItem();
        this.menuItemTranslation = new System.Windows.Forms.MenuItem();
        this.menuItemMobileSetup = new System.Windows.Forms.MenuItem();
        this.menuItemNewCropBilling = new System.Windows.Forms.MenuItem();
        this.menuItemScreening = new System.Windows.Forms.MenuItem();
        this.menuItemRepeatingCharges = new System.Windows.Forms.MenuItem();
        this.menuItemResellers = new System.Windows.Forms.MenuItem();
        this.menuItemReqStudents = new System.Windows.Forms.MenuItem();
        this.menuItemWebForms = new System.Windows.Forms.MenuItem();
        this.menuItemWiki = new System.Windows.Forms.MenuItem();
        this.menuItemHelp = new System.Windows.Forms.MenuItem();
        this.menuItemRemote = new System.Windows.Forms.MenuItem();
        this.menuItemHelpWindows = new System.Windows.Forms.MenuItem();
        this.menuItemHelpContents = new System.Windows.Forms.MenuItem();
        this.menuItemHelpIndex = new System.Windows.Forms.MenuItem();
        this.menuItemRequestFeatures = new System.Windows.Forms.MenuItem();
        this.menuItemUpdate = new System.Windows.Forms.MenuItem();
        this.imageList32 = new System.Windows.Forms.ImageList(this.components);
        this.timerSignals = new System.Windows.Forms.Timer(this.components);
        this.panelSplitter = new System.Windows.Forms.Panel();
        this.menuSplitter = new System.Windows.Forms.ContextMenu();
        this.menuItemDockBottom = new System.Windows.Forms.MenuItem();
        this.menuItemDockRight = new System.Windows.Forms.MenuItem();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.menuPatient = new System.Windows.Forms.ContextMenu();
        this.menuLabel = new System.Windows.Forms.ContextMenu();
        this.menuEmail = new System.Windows.Forms.ContextMenu();
        this.menuLetter = new System.Windows.Forms.ContextMenu();
        this.timerDisabledKey = new System.Windows.Forms.Timer(this.components);
        this.timerHeartBeat = new System.Windows.Forms.Timer(this.components);
        this.timerPhoneWebCam = new System.Windows.Forms.Timer(this.components);
        this.timerWebHostSynch = new System.Windows.Forms.Timer(this.components);
        this.timerLogoff = new System.Windows.Forms.Timer(this.components);
        this.timerReplicationMonitor = new System.Windows.Forms.Timer(this.components);
        this.panelPhoneSmall = new System.Windows.Forms.Panel();
        this.labelFieldType = new System.Windows.Forms.Label();
        this.comboTriageCoordinator = new System.Windows.Forms.ComboBox();
        this.labelMsg = new System.Windows.Forms.Label();
        this.labelWaitTime = new System.Windows.Forms.Label();
        this.labelTriage = new System.Windows.Forms.Label();
        this.butMapPhones = new OpenDental.UI.Button();
        this.butTriage = new OpenDental.UI.Button();
        this.butBigPhones = new OpenDental.UI.Button();
        this.lightSignalGrid1 = new OpenDental.UI.LightSignalGrid();
        this.panelPhoneSmall.SuspendLayout();
        this.SuspendLayout();
        //
        // timerTimeIndic
        //
        this.timerTimeIndic.Interval = 60000;
        this.timerTimeIndic.Tick += new System.EventHandler(this.timerTimeIndic_Tick);
        //
        // mainMenu
        //
        this.mainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemLogOff, this.menuItemFile, this.menuItemSettings, this.menuItemLists, this.menuItemReports, this.menuItemCustomReports, this.menuItemTools, this.menuItemHelp });
        //
        // menuItemLogOff
        //
        this.menuItemLogOff.Index = 0;
        this.menuItemLogOff.Text = "Log &Off";
        this.menuItemLogOff.Click += new System.EventHandler(this.menuItemLogOff_Click);
        //
        // menuItemFile
        //
        this.menuItemFile.Index = 1;
        this.menuItemFile.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemPassword, this.menuItem3, this.menuItemPrinter, this.menuItemGraphics, this.menuItem6, this.menuItemConfig, this.menuItem7, this.menuItemExit });
        this.menuItemFile.Shortcut = System.Windows.Forms.Shortcut.CtrlC;
        this.menuItemFile.Text = "&File";
        //
        // menuItemPassword
        //
        this.menuItemPassword.Index = 0;
        this.menuItemPassword.Text = "Change Password";
        this.menuItemPassword.Click += new System.EventHandler(this.menuItemPassword_Click);
        //
        // menuItem3
        //
        this.menuItem3.Index = 1;
        this.menuItem3.Text = "-";
        //
        // menuItemPrinter
        //
        this.menuItemPrinter.Index = 2;
        this.menuItemPrinter.Text = "&Printers";
        this.menuItemPrinter.Click += new System.EventHandler(this.menuItemPrinter_Click);
        //
        // menuItemGraphics
        //
        this.menuItemGraphics.Index = 3;
        this.menuItemGraphics.Text = "Graphics";
        this.menuItemGraphics.Click += new System.EventHandler(this.menuItemGraphics_Click);
        //
        // menuItem6
        //
        this.menuItem6.Index = 4;
        this.menuItem6.Text = "-";
        //
        // menuItemConfig
        //
        this.menuItemConfig.Index = 5;
        this.menuItemConfig.Text = "&Choose Database";
        this.menuItemConfig.Click += new System.EventHandler(this.menuItemConfig_Click);
        //
        // menuItem7
        //
        this.menuItem7.Index = 6;
        this.menuItem7.Text = "-";
        //
        // menuItemExit
        //
        this.menuItemExit.Index = 7;
        this.menuItemExit.ShowShortcut = false;
        this.menuItemExit.Text = "E&xit";
        this.menuItemExit.Click += new System.EventHandler(this.menuItemExit_Click);
        //
        // menuItemSettings
        //
        this.menuItemSettings.Index = 2;
        this.menuItemSettings.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuApptFieldDefs, this.menuItemApptRules, this.menuItemApptViews, this.menuItemAutoCodes, this.menuItemAutomation, this.menuItemAutoNotes, this.menuItemClaimForms, this.menuItemClearinghouses, this.menuItemComputers, this.menuItemDataPath, this.menuItemDefinitions, this.menuItemDisplayFields, this.menuItemEmail, this.menuItemEHR, this.menuItemFeeScheds, this.menuItemHL7, this.menuItemImaging, this.menuItemInsCats, this.menuItemInsFilingCodes, this.menuItemLaboratories, this.menuItemLetters, this.menuItemMessaging, this.menuItemMessagingButs, this.menuItemMisc, this.menuItemModules, this.menuItemOperatories, this.menuItemPatFieldDefs, this.menuItemPayerIDs, this.menuItemPractice, this.menuItemProblems, this.menuItemProcedureButtons, this.menuItemLinks, this.menuItemQuestions, this.menuItemRecall, this.menuItemRecallTypes, this.menuItemReplication, this.menuItemRequirementsNeeded, this.menuItemSched, this.menuItemSecurity, this.menuItemSheets, this.menuItemEasy, this.menuItemSpellCheck, this.menuItemTimeCards });
        this.menuItemSettings.Shortcut = System.Windows.Forms.Shortcut.CtrlS;
        this.menuItemSettings.Text = "&Setup";
        //
        // menuApptFieldDefs
        //
        this.menuApptFieldDefs.Index = 0;
        this.menuApptFieldDefs.Text = "Appointment Field Defs";
        this.menuApptFieldDefs.Click += new System.EventHandler(this.menuItemApptFieldDefs_Click);
        //
        // menuItemApptRules
        //
        this.menuItemApptRules.Index = 1;
        this.menuItemApptRules.Text = "Appointment Rules";
        this.menuItemApptRules.Click += new System.EventHandler(this.menuItemApptRules_Click);
        //
        // menuItemApptViews
        //
        this.menuItemApptViews.Index = 2;
        this.menuItemApptViews.Text = "Appointment Views";
        this.menuItemApptViews.Click += new System.EventHandler(this.menuItemApptViews_Click);
        //
        // menuItemAutoCodes
        //
        this.menuItemAutoCodes.Index = 3;
        this.menuItemAutoCodes.Text = "Auto Codes";
        this.menuItemAutoCodes.Click += new System.EventHandler(this.menuItemAutoCodes_Click);
        //
        // menuItemAutomation
        //
        this.menuItemAutomation.Index = 4;
        this.menuItemAutomation.Text = "Automation";
        this.menuItemAutomation.Click += new System.EventHandler(this.menuItemAutomation_Click);
        //
        // menuItemAutoNotes
        //
        this.menuItemAutoNotes.Index = 5;
        this.menuItemAutoNotes.Text = "Auto Notes";
        this.menuItemAutoNotes.Click += new System.EventHandler(this.menuItemAutoNotes_Click);
        //
        // menuItemClaimForms
        //
        this.menuItemClaimForms.Index = 6;
        this.menuItemClaimForms.Text = "Claim Forms";
        this.menuItemClaimForms.Click += new System.EventHandler(this.menuItemClaimForms_Click);
        //
        // menuItemClearinghouses
        //
        this.menuItemClearinghouses.Index = 7;
        this.menuItemClearinghouses.Text = "Clearinghouses";
        this.menuItemClearinghouses.Click += new System.EventHandler(this.menuItemClearinghouses_Click);
        //
        // menuItemComputers
        //
        this.menuItemComputers.Index = 8;
        this.menuItemComputers.Text = "Computers";
        this.menuItemComputers.Click += new System.EventHandler(this.menuItemComputers_Click);
        //
        // menuItemDataPath
        //
        this.menuItemDataPath.Index = 9;
        this.menuItemDataPath.Text = "Data Paths";
        this.menuItemDataPath.Click += new System.EventHandler(this.menuItemDataPath_Click);
        //
        // menuItemDefinitions
        //
        this.menuItemDefinitions.Index = 10;
        this.menuItemDefinitions.Text = "Definitions";
        this.menuItemDefinitions.Click += new System.EventHandler(this.menuItemDefinitions_Click);
        //
        // menuItemDisplayFields
        //
        this.menuItemDisplayFields.Index = 11;
        this.menuItemDisplayFields.Text = "Display Fields";
        this.menuItemDisplayFields.Click += new System.EventHandler(this.menuItemDisplayFields_Click);
        //
        // menuItemEmail
        //
        this.menuItemEmail.Index = 12;
        this.menuItemEmail.Text = "E-mail";
        this.menuItemEmail.Click += new System.EventHandler(this.menuItemEmail_Click);
        //
        // menuItemEHR
        //
        this.menuItemEHR.Index = 13;
        this.menuItemEHR.Text = "EHR";
        this.menuItemEHR.Click += new System.EventHandler(this.menuItemEHR_Click);
        //
        // menuItemFeeScheds
        //
        this.menuItemFeeScheds.Index = 14;
        this.menuItemFeeScheds.Text = "Fee Schedules";
        this.menuItemFeeScheds.Click += new System.EventHandler(this.menuItemFeeScheds_Click);
        //
        // menuItemHL7
        //
        this.menuItemHL7.Index = 15;
        this.menuItemHL7.Text = "HL7";
        this.menuItemHL7.Click += new System.EventHandler(this.menuItemHL7_Click);
        //
        // menuItemImaging
        //
        this.menuItemImaging.Index = 16;
        this.menuItemImaging.Text = "Imaging";
        this.menuItemImaging.Click += new System.EventHandler(this.menuItemImaging_Click);
        //
        // menuItemInsCats
        //
        this.menuItemInsCats.Index = 17;
        this.menuItemInsCats.Text = "Insurance Categories";
        this.menuItemInsCats.Click += new System.EventHandler(this.menuItemInsCats_Click);
        //
        // menuItemInsFilingCodes
        //
        this.menuItemInsFilingCodes.Index = 18;
        this.menuItemInsFilingCodes.Text = "Insurance Filing Codes";
        this.menuItemInsFilingCodes.Click += new System.EventHandler(this.menuItemInsFilingCodes_Click);
        //
        // menuItemLaboratories
        //
        this.menuItemLaboratories.Index = 19;
        this.menuItemLaboratories.Text = "Laboratories";
        this.menuItemLaboratories.Click += new System.EventHandler(this.menuItemLaboratories_Click);
        //
        // menuItemLetters
        //
        this.menuItemLetters.Index = 20;
        this.menuItemLetters.Text = "Letters";
        this.menuItemLetters.Click += new System.EventHandler(this.menuItemLetters_Click);
        //
        // menuItemMessaging
        //
        this.menuItemMessaging.Index = 21;
        this.menuItemMessaging.Text = "Messaging";
        this.menuItemMessaging.Click += new System.EventHandler(this.menuItemMessaging_Click);
        //
        // menuItemMessagingButs
        //
        this.menuItemMessagingButs.Index = 22;
        this.menuItemMessagingButs.Text = "Messaging Buttons";
        this.menuItemMessagingButs.Click += new System.EventHandler(this.menuItemMessagingButs_Click);
        //
        // menuItemMisc
        //
        this.menuItemMisc.Index = 23;
        this.menuItemMisc.Text = "Miscellaneous";
        this.menuItemMisc.Click += new System.EventHandler(this.menuItemMisc_Click);
        //
        // menuItemModules
        //
        this.menuItemModules.Index = 24;
        this.menuItemModules.Text = "Modules";
        this.menuItemModules.Click += new System.EventHandler(this.menuItemModules_Click);
        //
        // menuItemOperatories
        //
        this.menuItemOperatories.Index = 25;
        this.menuItemOperatories.Text = "Operatories";
        this.menuItemOperatories.Click += new System.EventHandler(this.menuItemOperatories_Click);
        //
        // menuItemPatFieldDefs
        //
        this.menuItemPatFieldDefs.Index = 26;
        this.menuItemPatFieldDefs.Text = "Patient Field Defs";
        this.menuItemPatFieldDefs.Click += new System.EventHandler(this.menuItemPatFieldDefs_Click);
        //
        // menuItemPayerIDs
        //
        this.menuItemPayerIDs.Index = 27;
        this.menuItemPayerIDs.Text = "Payer IDs";
        this.menuItemPayerIDs.Click += new System.EventHandler(this.menuItemPayerIDs_Click);
        //
        // menuItemPractice
        //
        this.menuItemPractice.Index = 28;
        this.menuItemPractice.Text = "Practice";
        this.menuItemPractice.Click += new System.EventHandler(this.menuItemPractice_Click);
        //
        // menuItemProblems
        //
        this.menuItemProblems.Index = 29;
        this.menuItemProblems.Text = "Problems";
        this.menuItemProblems.Click += new System.EventHandler(this.menuItemProblems_Click);
        //
        // menuItemProcedureButtons
        //
        this.menuItemProcedureButtons.Index = 30;
        this.menuItemProcedureButtons.Text = "Procedure Buttons";
        this.menuItemProcedureButtons.Click += new System.EventHandler(this.menuItemProcedureButtons_Click);
        //
        // menuItemLinks
        //
        this.menuItemLinks.Index = 31;
        this.menuItemLinks.Text = "Program Links";
        this.menuItemLinks.Click += new System.EventHandler(this.menuItemLinks_Click);
        //
        // menuItemQuestions
        //
        this.menuItemQuestions.Index = 32;
        this.menuItemQuestions.Text = "Questionnaire";
        this.menuItemQuestions.Click += new System.EventHandler(this.menuItemQuestions_Click);
        //
        // menuItemRecall
        //
        this.menuItemRecall.Index = 33;
        this.menuItemRecall.Text = "Recall";
        this.menuItemRecall.Click += new System.EventHandler(this.menuItemRecall_Click);
        //
        // menuItemRecallTypes
        //
        this.menuItemRecallTypes.Index = 34;
        this.menuItemRecallTypes.Text = "RecallTypes";
        this.menuItemRecallTypes.Click += new System.EventHandler(this.menuItemRecallTypes_Click);
        //
        // menuItemReplication
        //
        this.menuItemReplication.Index = 35;
        this.menuItemReplication.Text = "Replication";
        this.menuItemReplication.Click += new System.EventHandler(this.menuItemReplication_Click);
        //
        // menuItemRequirementsNeeded
        //
        this.menuItemRequirementsNeeded.Index = 36;
        this.menuItemRequirementsNeeded.Text = "Requirements Needed";
        this.menuItemRequirementsNeeded.Click += new System.EventHandler(this.menuItemRequirementsNeeded_Click);
        //
        // menuItemSched
        //
        this.menuItemSched.Index = 37;
        this.menuItemSched.Text = "Schedules";
        this.menuItemSched.Click += new System.EventHandler(this.menuItemSched_Click);
        //
        // menuItemSecurity
        //
        this.menuItemSecurity.Index = 38;
        this.menuItemSecurity.Text = "Security";
        this.menuItemSecurity.Click += new System.EventHandler(this.menuItemSecurity_Click);
        //
        // menuItemSheets
        //
        this.menuItemSheets.Index = 39;
        this.menuItemSheets.Text = "Sheets";
        this.menuItemSheets.Click += new System.EventHandler(this.menuItemSheets_Click);
        //
        // menuItemEasy
        //
        this.menuItemEasy.Index = 40;
        this.menuItemEasy.Text = "Show Features";
        this.menuItemEasy.Click += new System.EventHandler(this.menuItemEasy_Click);
        //
        // menuItemSpellCheck
        //
        this.menuItemSpellCheck.Index = 41;
        this.menuItemSpellCheck.Text = "Spell Check";
        this.menuItemSpellCheck.Click += new System.EventHandler(this.menuItemSpellCheck_Click);
        //
        // menuItemTimeCards
        //
        this.menuItemTimeCards.Index = 42;
        this.menuItemTimeCards.Text = "Time Cards";
        this.menuItemTimeCards.Click += new System.EventHandler(this.menuItemTimeCards_Click);
        //
        // menuItemLists
        //
        this.menuItemLists.Index = 3;
        this.menuItemLists.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemProcCodes, this.menuItem5, this.menuItemClinics, this.menuItemContacts, this.menuItemCounties, this.menuItemSchoolClass, this.menuItemSchoolCourses, this.menuItemEmployees, this.menuItemEmployers, this.menuItemCarriers, this.menuItemInsPlans, this.menuItemLabCases, this.menuItemMedications, this.menuItemPharmacies, this.menuItemProviders, this.menuItemPrescriptions, this.menuItemReferrals, this.menuItemSchools, this.menuItemZipCodes });
        this.menuItemLists.Shortcut = System.Windows.Forms.Shortcut.CtrlI;
        this.menuItemLists.Text = "&Lists";
        //
        // menuItemProcCodes
        //
        this.menuItemProcCodes.Index = 0;
        this.menuItemProcCodes.Shortcut = System.Windows.Forms.Shortcut.CtrlShiftF;
        this.menuItemProcCodes.Text = "&Procedure Codes";
        this.menuItemProcCodes.Click += new System.EventHandler(this.menuItemProcCodes_Click);
        //
        // menuItem5
        //
        this.menuItem5.Index = 1;
        this.menuItem5.Text = "-";
        //
        // menuItemClinics
        //
        this.menuItemClinics.Index = 2;
        this.menuItemClinics.Text = "Clinics";
        this.menuItemClinics.Click += new System.EventHandler(this.menuItemClinics_Click);
        //
        // menuItemContacts
        //
        this.menuItemContacts.Index = 3;
        this.menuItemContacts.Shortcut = System.Windows.Forms.Shortcut.CtrlShiftC;
        this.menuItemContacts.Text = "&Contacts";
        this.menuItemContacts.Click += new System.EventHandler(this.menuItemContacts_Click);
        //
        // menuItemCounties
        //
        this.menuItemCounties.Index = 4;
        this.menuItemCounties.Text = "Counties";
        this.menuItemCounties.Click += new System.EventHandler(this.menuItemCounties_Click);
        //
        // menuItemSchoolClass
        //
        this.menuItemSchoolClass.Index = 5;
        this.menuItemSchoolClass.Text = "Dental School Classes";
        this.menuItemSchoolClass.Click += new System.EventHandler(this.menuItemSchoolClass_Click);
        //
        // menuItemSchoolCourses
        //
        this.menuItemSchoolCourses.Index = 6;
        this.menuItemSchoolCourses.Text = "Dental School Courses";
        this.menuItemSchoolCourses.Click += new System.EventHandler(this.menuItemSchoolCourses_Click);
        //
        // menuItemEmployees
        //
        this.menuItemEmployees.Index = 7;
        this.menuItemEmployees.Text = "&Employees";
        this.menuItemEmployees.Click += new System.EventHandler(this.menuItemEmployees_Click);
        //
        // menuItemEmployers
        //
        this.menuItemEmployers.Index = 8;
        this.menuItemEmployers.Text = "Employers";
        this.menuItemEmployers.Click += new System.EventHandler(this.menuItemEmployers_Click);
        //
        // menuItemCarriers
        //
        this.menuItemCarriers.Index = 9;
        this.menuItemCarriers.Text = "Insurance Carriers";
        this.menuItemCarriers.Click += new System.EventHandler(this.menuItemCarriers_Click);
        //
        // menuItemInsPlans
        //
        this.menuItemInsPlans.Index = 10;
        this.menuItemInsPlans.Text = "&Insurance Plans";
        this.menuItemInsPlans.Click += new System.EventHandler(this.menuItemInsPlans_Click);
        //
        // menuItemLabCases
        //
        this.menuItemLabCases.Index = 11;
        this.menuItemLabCases.Text = "Lab Cases";
        this.menuItemLabCases.Click += new System.EventHandler(this.menuItemLabCases_Click);
        //
        // menuItemMedications
        //
        this.menuItemMedications.Index = 12;
        this.menuItemMedications.Text = "&Medications";
        this.menuItemMedications.Click += new System.EventHandler(this.menuItemMedications_Click);
        //
        // menuItemPharmacies
        //
        this.menuItemPharmacies.Index = 13;
        this.menuItemPharmacies.Text = "Pharmacies";
        this.menuItemPharmacies.Click += new System.EventHandler(this.menuItemPharmacies_Click);
        //
        // menuItemProviders
        //
        this.menuItemProviders.Index = 14;
        this.menuItemProviders.Text = "Providers";
        this.menuItemProviders.Click += new System.EventHandler(this.menuItemProviders_Click);
        //
        // menuItemPrescriptions
        //
        this.menuItemPrescriptions.Index = 15;
        this.menuItemPrescriptions.Text = "Pre&scriptions";
        this.menuItemPrescriptions.Click += new System.EventHandler(this.menuItemPrescriptions_Click);
        //
        // menuItemReferrals
        //
        this.menuItemReferrals.Index = 16;
        this.menuItemReferrals.Text = "&Referrals";
        this.menuItemReferrals.Click += new System.EventHandler(this.menuItemReferrals_Click);
        //
        // menuItemSchools
        //
        this.menuItemSchools.Index = 17;
        this.menuItemSchools.Text = "Sites";
        this.menuItemSchools.Click += new System.EventHandler(this.menuItemSites_Click);
        //
        // menuItemZipCodes
        //
        this.menuItemZipCodes.Index = 18;
        this.menuItemZipCodes.Text = "&Zip Codes";
        this.menuItemZipCodes.Click += new System.EventHandler(this.menuItemZipCodes_Click);
        //
        // menuItemReports
        //
        this.menuItemReports.Index = 4;
        this.menuItemReports.Shortcut = System.Windows.Forms.Shortcut.CtrlR;
        this.menuItemReports.Text = "&Reports";
        this.menuItemReports.Click += new System.EventHandler(this.menuItemReports_Click);
        //
        // menuItemCustomReports
        //
        this.menuItemCustomReports.Index = 5;
        this.menuItemCustomReports.Text = "Custom Reports";
        //
        // menuItemTools
        //
        this.menuItemTools.Index = 6;
        this.menuItemTools.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemPrintScreen, this.menuItem1, this.menuItem9, this.menuItemAging, this.menuItemAuditTrail, this.menuItemFinanceCharge, this.menuItemCCRecurring, this.menuItemCustomerManage, this.menuItemDatabaseMaintenance, this.menuItemTerminal, this.menuItemTerminalManager, this.menuItemTranslation, this.menuItemMobileSetup, this.menuItemNewCropBilling, this.menuItemScreening, this.menuItemRepeatingCharges, this.menuItemResellers, this.menuItemReqStudents, this.menuItemWebForms, this.menuItemWiki });
        this.menuItemTools.Shortcut = System.Windows.Forms.Shortcut.CtrlU;
        this.menuItemTools.Text = "&Tools";
        //
        // menuItemPrintScreen
        //
        this.menuItemPrintScreen.Index = 0;
        this.menuItemPrintScreen.Text = "&Print Screen Tool";
        this.menuItemPrintScreen.Click += new System.EventHandler(this.menuItemPrintScreen_Click);
        //
        // menuItem1
        //
        this.menuItem1.Index = 1;
        this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemDuplicateBlockouts, this.menuItemCreateAtoZFolders, this.menuItemImportXML, this.menuItemMergePatients, this.menuItemProcLockTool, this.menuItemShutdown, this.menuTelephone, this.menuItemTestLatency, this.menuItemXChargeReconcile });
        this.menuItem1.Text = "Misc Tools";
        //
        // menuItemDuplicateBlockouts
        //
        this.menuItemDuplicateBlockouts.Index = 0;
        this.menuItemDuplicateBlockouts.Text = "Clear Duplicate Blockouts";
        this.menuItemDuplicateBlockouts.Click += new System.EventHandler(this.menuItemDuplicateBlockouts_Click);
        //
        // menuItemCreateAtoZFolders
        //
        this.menuItemCreateAtoZFolders.Index = 1;
        this.menuItemCreateAtoZFolders.Text = "Create A to Z Folders";
        this.menuItemCreateAtoZFolders.Click += new System.EventHandler(this.menuItemCreateAtoZFolders_Click);
        //
        // menuItemImportXML
        //
        this.menuItemImportXML.Index = 2;
        this.menuItemImportXML.Text = "Import Patient XML";
        this.menuItemImportXML.Click += new System.EventHandler(this.menuItemImportXML_Click);
        //
        // menuItemMergePatients
        //
        this.menuItemMergePatients.Index = 3;
        this.menuItemMergePatients.Text = "Merge Patients";
        this.menuItemMergePatients.Click += new System.EventHandler(this.menuItemMergePatients_Click);
        //
        // menuItemProcLockTool
        //
        this.menuItemProcLockTool.Index = 4;
        this.menuItemProcLockTool.Text = "Procedure Lock Tool";
        this.menuItemProcLockTool.Click += new System.EventHandler(this.menuItemProcLockTool_Click);
        //
        // menuItemShutdown
        //
        this.menuItemShutdown.Index = 5;
        this.menuItemShutdown.Text = "Shutdown All Workstations";
        this.menuItemShutdown.Click += new System.EventHandler(this.menuItemShutdown_Click);
        //
        // menuTelephone
        //
        this.menuTelephone.Index = 6;
        this.menuTelephone.Text = "Telephone Numbers";
        this.menuTelephone.Click += new System.EventHandler(this.menuTelephone_Click);
        //
        // menuItemTestLatency
        //
        this.menuItemTestLatency.Index = 7;
        this.menuItemTestLatency.Text = "Test Latency";
        this.menuItemTestLatency.Click += new System.EventHandler(this.menuItemTestLatency_Click);
        //
        // menuItemXChargeReconcile
        //
        this.menuItemXChargeReconcile.Index = 8;
        this.menuItemXChargeReconcile.Text = "X-Charge Reconcile";
        this.menuItemXChargeReconcile.Click += new System.EventHandler(this.menuItemXChargeReconcile_Click);
        //
        // menuItem9
        //
        this.menuItem9.Index = 2;
        this.menuItem9.Text = "-";
        //
        // menuItemAging
        //
        this.menuItemAging.Index = 3;
        this.menuItemAging.Text = "&Aging";
        this.menuItemAging.Click += new System.EventHandler(this.menuItemAging_Click);
        //
        // menuItemAuditTrail
        //
        this.menuItemAuditTrail.Index = 4;
        this.menuItemAuditTrail.Text = "Audit Trail";
        this.menuItemAuditTrail.Click += new System.EventHandler(this.menuItemAuditTrail_Click);
        //
        // menuItemFinanceCharge
        //
        this.menuItemFinanceCharge.Index = 5;
        this.menuItemFinanceCharge.Text = "Billing/&Finance Charges";
        this.menuItemFinanceCharge.Click += new System.EventHandler(this.menuItemFinanceCharge_Click);
        //
        // menuItemCCRecurring
        //
        this.menuItemCCRecurring.Index = 6;
        this.menuItemCCRecurring.Text = "CC Recurring Charges";
        this.menuItemCCRecurring.Click += new System.EventHandler(this.menuItemCCRecurring_Click);
        //
        // menuItemCustomerManage
        //
        this.menuItemCustomerManage.Index = 7;
        this.menuItemCustomerManage.Text = "Customer Management";
        this.menuItemCustomerManage.Click += new System.EventHandler(this.menuItemCustomerManage_Click);
        //
        // menuItemDatabaseMaintenance
        //
        this.menuItemDatabaseMaintenance.Index = 8;
        this.menuItemDatabaseMaintenance.Text = "Database Maintenance";
        this.menuItemDatabaseMaintenance.Click += new System.EventHandler(this.menuItemDatabaseMaintenance_Click);
        //
        // menuItemTerminal
        //
        this.menuItemTerminal.Index = 9;
        this.menuItemTerminal.Text = "Kiosk";
        this.menuItemTerminal.Click += new System.EventHandler(this.menuItemTerminal_Click);
        //
        // menuItemTerminalManager
        //
        this.menuItemTerminalManager.Index = 10;
        this.menuItemTerminalManager.Text = "Kiosk Manager";
        this.menuItemTerminalManager.Click += new System.EventHandler(this.menuItemTerminalManager_Click);
        //
        // menuItemTranslation
        //
        this.menuItemTranslation.Index = 11;
        this.menuItemTranslation.Text = "Language Translation";
        this.menuItemTranslation.Click += new System.EventHandler(this.menuItemTranslation_Click);
        //
        // menuItemMobileSetup
        //
        this.menuItemMobileSetup.Index = 12;
        this.menuItemMobileSetup.Text = "Mobile Synch";
        this.menuItemMobileSetup.Click += new System.EventHandler(this.menuItemMobileSetup_Click);
        //
        // menuItemNewCropBilling
        //
        this.menuItemNewCropBilling.Index = 13;
        this.menuItemNewCropBilling.Text = "NewCrop Billing";
        this.menuItemNewCropBilling.Click += new System.EventHandler(this.menuItemNewCropBilling_Click);
        //
        // menuItemScreening
        //
        this.menuItemScreening.Index = 14;
        this.menuItemScreening.Text = "Public Health Screening";
        this.menuItemScreening.Click += new System.EventHandler(this.menuItemScreening_Click);
        //
        // menuItemRepeatingCharges
        //
        this.menuItemRepeatingCharges.Index = 15;
        this.menuItemRepeatingCharges.Text = "Repeating Charges";
        this.menuItemRepeatingCharges.Click += new System.EventHandler(this.menuItemRepeatingCharges_Click);
        //
        // menuItemResellers
        //
        this.menuItemResellers.Index = 16;
        this.menuItemResellers.Text = "Resellers";
        this.menuItemResellers.Visible = false;
        this.menuItemResellers.Click += new System.EventHandler(this.menuItemResellers_Click);
        //
        // menuItemReqStudents
        //
        this.menuItemReqStudents.Index = 17;
        this.menuItemReqStudents.Text = "Student Requirements";
        this.menuItemReqStudents.Click += new System.EventHandler(this.menuItemReqStudents_Click);
        //
        // menuItemWebForms
        //
        this.menuItemWebForms.Index = 18;
        this.menuItemWebForms.Text = "WebForms";
        this.menuItemWebForms.Click += new System.EventHandler(this.menuItemWebForms_Click);
        //
        // menuItemWiki
        //
        this.menuItemWiki.Index = 19;
        this.menuItemWiki.Text = "Wiki";
        this.menuItemWiki.Click += new System.EventHandler(this.menuItemWiki_Click);
        //
        // menuItemHelp
        //
        this.menuItemHelp.Index = 7;
        this.menuItemHelp.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemRemote, this.menuItemHelpWindows, this.menuItemHelpContents, this.menuItemHelpIndex, this.menuItemRequestFeatures, this.menuItemUpdate });
        this.menuItemHelp.Text = "&Help";
        //
        // menuItemRemote
        //
        this.menuItemRemote.Index = 0;
        this.menuItemRemote.Text = "Online Support";
        this.menuItemRemote.Click += new System.EventHandler(this.menuItemRemote_Click);
        //
        // menuItemHelpWindows
        //
        this.menuItemHelpWindows.Index = 1;
        this.menuItemHelpWindows.Text = "Local Help-Windows";
        this.menuItemHelpWindows.Click += new System.EventHandler(this.menuItemHelpWindows_Click);
        //
        // menuItemHelpContents
        //
        this.menuItemHelpContents.Index = 2;
        this.menuItemHelpContents.Text = "Online Help - Contents";
        this.menuItemHelpContents.Click += new System.EventHandler(this.menuItemHelpContents_Click);
        //
        // menuItemHelpIndex
        //
        this.menuItemHelpIndex.Index = 3;
        this.menuItemHelpIndex.Shortcut = System.Windows.Forms.Shortcut.ShiftF1;
        this.menuItemHelpIndex.Text = "Online Help - Index";
        this.menuItemHelpIndex.Click += new System.EventHandler(this.menuItemHelpIndex_Click);
        //
        // menuItemRequestFeatures
        //
        this.menuItemRequestFeatures.Index = 4;
        this.menuItemRequestFeatures.Text = "Request Features";
        this.menuItemRequestFeatures.Click += new System.EventHandler(this.menuItemRequestFeatures_Click);
        //
        // menuItemUpdate
        //
        this.menuItemUpdate.Index = 5;
        this.menuItemUpdate.Text = "&Update";
        this.menuItemUpdate.Click += new System.EventHandler(this.menuItemUpdate_Click);
        //
        // imageList32
        //
        this.imageList32.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList32.ImageStream")));
        this.imageList32.TransparentColor = System.Drawing.Color.Transparent;
        this.imageList32.Images.SetKeyName(0, "Appt32.gif");
        this.imageList32.Images.SetKeyName(1, "Family32b.gif");
        this.imageList32.Images.SetKeyName(2, "Account32b.gif");
        this.imageList32.Images.SetKeyName(3, "TreatPlan3D.gif");
        this.imageList32.Images.SetKeyName(4, "chart32.gif");
        this.imageList32.Images.SetKeyName(5, "Images32.gif");
        this.imageList32.Images.SetKeyName(6, "Manage32.gif");
        //
        // timerSignals
        //
        this.timerSignals.Tick += new System.EventHandler(this.timerSignals_Tick);
        //
        // panelSplitter
        //
        this.panelSplitter.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelSplitter.Cursor = System.Windows.Forms.Cursors.HSplit;
        this.panelSplitter.Location = new System.Drawing.Point(71, 542);
        this.panelSplitter.Name = "panelSplitter";
        this.panelSplitter.Size = new System.Drawing.Size(769, 7);
        this.panelSplitter.TabIndex = 50;
        this.panelSplitter.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseDown);
        this.panelSplitter.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseMove);
        this.panelSplitter.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseUp);
        //
        // menuSplitter
        //
        this.menuSplitter.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemDockBottom, this.menuItemDockRight });
        //
        // menuItemDockBottom
        //
        this.menuItemDockBottom.Index = 0;
        this.menuItemDockBottom.Text = "Dock to Bottom";
        this.menuItemDockBottom.Click += new System.EventHandler(this.menuItemDockBottom_Click);
        //
        // menuItemDockRight
        //
        this.menuItemDockRight.Index = 1;
        this.menuItemDockRight.Text = "Dock to Right";
        this.menuItemDockRight.Click += new System.EventHandler(this.menuItemDockRight_Click);
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Pat.gif");
        this.imageListMain.Images.SetKeyName(1, "commlog.gif");
        this.imageListMain.Images.SetKeyName(2, "email.gif");
        this.imageListMain.Images.SetKeyName(3, "tasksNicer.gif");
        this.imageListMain.Images.SetKeyName(4, "label.gif");
        this.imageListMain.Images.SetKeyName(5, "Text.gif");
        //
        // menuPatient
        //
        this.menuPatient.Popup += new System.EventHandler(this.menuPatient_Popup);
        //
        // menuLabel
        //
        this.menuLabel.Popup += new System.EventHandler(this.menuLabel_Popup);
        //
        // menuEmail
        //
        this.menuEmail.Popup += new System.EventHandler(this.menuEmail_Popup);
        //
        // menuLetter
        //
        this.menuLetter.Popup += new System.EventHandler(this.menuLetter_Popup);
        //
        // timerDisabledKey
        //
        this.timerDisabledKey.Enabled = true;
        this.timerDisabledKey.Interval = 600000;
        this.timerDisabledKey.Tick += new System.EventHandler(this.timerDisabledKey_Tick);
        //
        // timerHeartBeat
        //
        this.timerHeartBeat.Enabled = true;
        this.timerHeartBeat.Interval = 180000;
        this.timerHeartBeat.Tick += new System.EventHandler(this.timerHeartBeat_Tick);
        //
        // timerPhoneWebCam
        //
        this.timerPhoneWebCam.Interval = 1600;
        this.timerPhoneWebCam.Tick += new System.EventHandler(this.timerPhoneWebCam_Tick);
        //
        // timerWebHostSynch
        //
        this.timerWebHostSynch.Enabled = true;
        this.timerWebHostSynch.Interval = 30000;
        this.timerWebHostSynch.Tick += new System.EventHandler(this.timerWebHostSynch_Tick);
        //
        // timerLogoff
        //
        this.timerLogoff.Interval = 15000;
        this.timerLogoff.Tick += new System.EventHandler(this.timerLogoff_Tick);
        //
        // timerReplicationMonitor
        //
        this.timerReplicationMonitor.Interval = 10000;
        this.timerReplicationMonitor.Tick += new System.EventHandler(this.timerReplicationMonitor_Tick);
        //
        // panelPhoneSmall
        //
        this.panelPhoneSmall.Controls.Add(this.labelFieldType);
        this.panelPhoneSmall.Controls.Add(this.comboTriageCoordinator);
        this.panelPhoneSmall.Controls.Add(this.labelMsg);
        this.panelPhoneSmall.Controls.Add(this.butMapPhones);
        this.panelPhoneSmall.Controls.Add(this.butTriage);
        this.panelPhoneSmall.Controls.Add(this.butBigPhones);
        this.panelPhoneSmall.Controls.Add(this.labelWaitTime);
        this.panelPhoneSmall.Controls.Add(this.labelTriage);
        this.panelPhoneSmall.Location = new System.Drawing.Point(71, 333);
        this.panelPhoneSmall.Name = "panelPhoneSmall";
        this.panelPhoneSmall.Size = new System.Drawing.Size(150, 321);
        this.panelPhoneSmall.TabIndex = 56;
        //
        // labelFieldType
        //
        this.labelFieldType.Location = new System.Drawing.Point(4, 25);
        this.labelFieldType.Name = "labelFieldType";
        this.labelFieldType.Size = new System.Drawing.Size(143, 15);
        this.labelFieldType.TabIndex = 88;
        this.labelFieldType.Text = "Triage Coordinator";
        this.labelFieldType.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboTriageCoordinator
        //
        this.comboTriageCoordinator.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboTriageCoordinator.FormattingEnabled = true;
        this.comboTriageCoordinator.Location = new System.Drawing.Point(0, 42);
        this.comboTriageCoordinator.MaxDropDownItems = 10;
        this.comboTriageCoordinator.Name = "comboTriageCoordinator";
        this.comboTriageCoordinator.Size = new System.Drawing.Size(150, 21);
        this.comboTriageCoordinator.TabIndex = 87;
        this.comboTriageCoordinator.SelectionChangeCommitted += new System.EventHandler(this.comboTriageCoordinator_SelectionChangeCommitted);
        //
        // labelMsg
        //
        this.labelMsg.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelMsg.ForeColor = System.Drawing.Color.Firebrick;
        this.labelMsg.Location = new System.Drawing.Point(-1, 2);
        this.labelMsg.Name = "labelMsg";
        this.labelMsg.Size = new System.Drawing.Size(35, 20);
        this.labelMsg.TabIndex = 53;
        this.labelMsg.Text = "V:00";
        this.labelMsg.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelWaitTime
        //
        this.labelWaitTime.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelWaitTime.ForeColor = System.Drawing.Color.Black;
        this.labelWaitTime.Location = new System.Drawing.Point(67, 2);
        this.labelWaitTime.Name = "labelWaitTime";
        this.labelWaitTime.Size = new System.Drawing.Size(30, 20);
        this.labelWaitTime.TabIndex = 53;
        this.labelWaitTime.Text = "00m";
        this.labelWaitTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelTriage
        //
        this.labelTriage.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriage.ForeColor = System.Drawing.Color.Black;
        this.labelTriage.Location = new System.Drawing.Point(30, 2);
        this.labelTriage.Name = "labelTriage";
        this.labelTriage.Size = new System.Drawing.Size(41, 20);
        this.labelTriage.TabIndex = 53;
        this.labelTriage.Text = "T:000";
        this.labelTriage.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butMapPhones
        //
        this.butMapPhones.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMapPhones.setAutosize(true);
        this.butMapPhones.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMapPhones.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMapPhones.setCornerRadius(4F);
        this.butMapPhones.Location = new System.Drawing.Point(132, 0);
        this.butMapPhones.Name = "butMapPhones";
        this.butMapPhones.Size = new System.Drawing.Size(18, 24);
        this.butMapPhones.TabIndex = 54;
        this.butMapPhones.Text = "P";
        this.butMapPhones.Click += new System.EventHandler(this.butMapPhones_Click);
        //
        // butTriage
        //
        this.butTriage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTriage.setAutosize(true);
        this.butTriage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTriage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTriage.setCornerRadius(4F);
        this.butTriage.Location = new System.Drawing.Point(94, 0);
        this.butTriage.Name = "butTriage";
        this.butTriage.Size = new System.Drawing.Size(18, 24);
        this.butTriage.TabIndex = 52;
        this.butTriage.Text = "T";
        this.butTriage.Click += new System.EventHandler(this.butTriage_Click);
        //
        // butBigPhones
        //
        this.butBigPhones.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBigPhones.setAutosize(true);
        this.butBigPhones.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBigPhones.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBigPhones.setCornerRadius(4F);
        this.butBigPhones.Location = new System.Drawing.Point(113, 0);
        this.butBigPhones.Name = "butBigPhones";
        this.butBigPhones.Size = new System.Drawing.Size(18, 24);
        this.butBigPhones.TabIndex = 52;
        this.butBigPhones.Text = "B";
        this.butBigPhones.Click += new System.EventHandler(this.butBigPhones_Click);
        //
        // lightSignalGrid1
        //
        this.lightSignalGrid1.Location = new System.Drawing.Point(0, 463);
        this.lightSignalGrid1.Name = "lightSignalGrid1";
        this.lightSignalGrid1.Size = new System.Drawing.Size(50, 206);
        this.lightSignalGrid1.TabIndex = 20;
        this.lightSignalGrid1.Text = "lightSignalGrid1";
        this.lightSignalGrid1.ButtonClick = __MultiODLightSignalGridClickEventHandler.combine(this.lightSignalGrid1.ButtonClick,new OpenDental.UI.ODLightSignalGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODLightSignalGridClickEventArgs e) throws Exception {
                this.lightSignalGrid1_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODLightSignalGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODLightSignalGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODLightSignalGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormOpenDental
        //
        this.ClientSize = new System.Drawing.Size(982, 550);
        this.Controls.Add(this.panelPhoneSmall);
        this.Controls.Add(this.panelSplitter);
        this.Controls.Add(this.lightSignalGrid1);
        this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.Menu = this.mainMenu;
        this.Name = "FormOpenDental";
        this.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.Text = "Open Dental";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormOpenDental_FormClosing);
        this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.FormOpenDental_FormClosed);
        this.Load += new System.EventHandler(this.FormOpenDental_Load);
        this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.FormOpenDental_KeyDown);
        this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.FormOpenDental_MouseMove);
        this.Resize += new System.EventHandler(this.FormOpenDental_Resize);
        this.panelPhoneSmall.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formOpenDental_Load(Object sender, System.EventArgs e) throws Exception {
        Splash.Dispose();
        Version versionOd = Assembly.GetAssembly(FormOpenDental.class).GetName().Version;
        Version versionObBus = Assembly.GetAssembly(Db.class).GetName().Version;
        if (versionOd != versionObBus)
        {
            MessageBox.Show("Mismatched program file versions. Please run the Open Dental setup file again on this computer.");
            //No MsgBox or Lan.g() here, because we don't want to access the database if there is a version conflict.
            Application.Exit();
            return ;
        }
         
        allNeutral();
        String odUser = "";
        String odPassHash = "";
        String webServiceUri = "";
        YN webServiceIsEcw = YN.Unknown;
        String odPassword = "";
        String serverName = "";
        String databaseName = "";
        String mySqlUser = "";
        String mySqlPassword = "";
        if (CommandLineArgs.Length != 0)
        {
            for (int i = 0;i < CommandLineArgs.Length;i++)
            {
                if (CommandLineArgs[i].StartsWith("UserName=") && CommandLineArgs[i].Length > 9)
                {
                    odUser = CommandLineArgs[i].Substring(9).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("PassHash=") && CommandLineArgs[i].Length > 9)
                {
                    odPassHash = CommandLineArgs[i].Substring(9).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("WebServiceUri=") && CommandLineArgs[i].Length > 14)
                {
                    webServiceUri = CommandLineArgs[i].Substring(14).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("WebServiceIsEcw=") && CommandLineArgs[i].Length > 16)
                {
                    if (StringSupport.equals(CommandLineArgs[i].Substring(16).Trim('"'), "True"))
                    {
                        webServiceIsEcw = YN.Yes;
                    }
                    else
                    {
                        webServiceIsEcw = YN.No;
                    } 
                }
                 
                if (CommandLineArgs[i].StartsWith("OdPassword=") && CommandLineArgs[i].Length > 11)
                {
                    odPassword = CommandLineArgs[i].Substring(11).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("ServerName=") && CommandLineArgs[i].Length > 11)
                {
                    serverName = CommandLineArgs[i].Substring(11).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("DatabaseName=") && CommandLineArgs[i].Length > 13)
                {
                    databaseName = CommandLineArgs[i].Substring(13).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("MySqlUser=") && CommandLineArgs[i].Length > 10)
                {
                    mySqlUser = CommandLineArgs[i].Substring(10).Trim('"');
                }
                 
                if (CommandLineArgs[i].StartsWith("MySqlPassword=") && CommandLineArgs[i].Length > 14)
                {
                    mySqlPassword = CommandLineArgs[i].Substring(14).Trim('"');
                }
                 
            }
        }
         
        YN noShow = YN.Unknown;
        if (!StringSupport.equals(webServiceUri, ""))
        {
            //a web service was specified
            if (!StringSupport.equals(odUser, "") && !StringSupport.equals(odPassword, ""))
            {
                //and both a username and password were specified
                noShow = YN.Yes;
            }
             
        }
        else if (!StringSupport.equals(databaseName, ""))
        {
            noShow = YN.Yes;
        }
          
        FormChooseDatabase formChooseDb = new FormChooseDatabase();
        formChooseDb.OdUser = odUser;
        formChooseDb.OdPassHash = odPassHash;
        formChooseDb.WebServiceUri = webServiceUri;
        formChooseDb.WebServiceIsEcw = webServiceIsEcw;
        formChooseDb.OdPassword = odPassword;
        formChooseDb.ServerName = serverName;
        formChooseDb.DatabaseName = databaseName;
        formChooseDb.MySqlUser = mySqlUser;
        formChooseDb.MySqlPassword = mySqlPassword;
        formChooseDb.NoShow = noShow;
        //either unknown or yes
        formChooseDb.getConfig();
        formChooseDb.getCmdLine();
        while (true)
        {
            //Most users will loop through once.  If user tries to connect to a db with replication failure, they will loop through again.
            if (formChooseDb.NoShow == YN.Yes)
            {
                if (!formChooseDb.tryToConnect())
                {
                    formChooseDb.ShowDialog();
                    if (formChooseDb.DialogResult == DialogResult.Cancel)
                    {
                        Application.Exit();
                        return ;
                    }
                     
                }
                 
            }
            else
            {
                formChooseDb.ShowDialog();
                if (formChooseDb.DialogResult == DialogResult.Cancel)
                {
                    Application.Exit();
                    return ;
                }
                 
            } 
            Cursor = Cursors.WaitCursor;
            Plugins.LoadAllPlugins(this);
            //moved up from near RefreshLocalData(invalidTypes). New position might cause problems.
            Splash = new FormSplash();
            if (CommandLineArgs.Length == 0)
            {
                Splash.Show();
            }
             
            if (!prefsStartup())
            {
                //looks for the AtoZ folder here, but would like to eventually move that down to after login
                Cursor = Cursors.Default;
                Splash.Dispose();
                Application.Exit();
                return ;
            }
             
            if (ReplicationServers.getServer_id() != 0 && ReplicationServers.getServer_id() == PrefC.getInt(PrefName.ReplicationFailureAtServer_id))
            {
                MsgBox.show(this,"This database is temporarily unavailable.  Please connect instead to your alternate database at the other location.");
                formChooseDb.NoShow = YN.No;
                //This ensures they will get a choose db window next time through the loop.
                ReplicationServers.setServer_id(-1);
                continue;
            }
             
            break;
        }
        if (Programs.usingEcwTightOrFullMode())
        {
            Splash.Dispose();
        }
         
        //We don't show splash screen when bridging to eCW.
        //We no longer do this shotgun approach because it can slow the loading time.
        //RefreshLocalData(InvalidType.AllLocal);
        List<InvalidType> invalidTypes = new List<InvalidType>();
        invalidTypes.Add(InvalidType.Prefs);
        invalidTypes.Add(InvalidType.Defs);
        invalidTypes.Add(InvalidType.Providers);
        //obviously heavily used
        invalidTypes.Add(InvalidType.Programs);
        //already done above, but needs to be done explicitly to trigger the PostCleanup
        invalidTypes.Add(InvalidType.ToolBut);
        //so program buttons will show in all the toolbars
        if (Programs.usingEcwTightMode())
        {
            lightSignalGrid1.Visible = false;
        }
        else
        {
            invalidTypes.Add(InvalidType.Signals);
        } 
        //so when mouse moves over light buttons, it won't crash
        //Plugins.LoadAllPlugins(this);//moved up from right after optimizing tooth chart graphics.  New position might cause problems.
        //It was moved because RefreshLocalData()=>RefreshLocalDataPostCleanup()=>ContrChart2.InitializeLocalData()=>LayoutToolBar() has a hook.
        //Moved it up again on 10/3/13
        RefreshLocalData(invalidTypes.ToArray());
        fillSignalButtons(null);
        ContrManage2.initializeOnStartup();
        DataValid.BecameInvalid = __MultiValidEventHandler.combine(DataValid.BecameInvalid,new OpenDental.ValidEventHandler() 
          { 
            //so that when a signal is received, it can handle it.
            //Lan.Refresh();//automatically skips if current culture is en-US
            //LanguageForeigns.Refresh(CultureInfo.CurrentCulture);//automatically skips if current culture is en-US
            public System.Void invoke(ValidEventArgs e) throws Exception {
                dataValid_BecameInvalid(e);
            }

            public List<OpenDental.ValidEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ValidEventHandler> ret = new ArrayList<OpenDental.ValidEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        signalLastRefreshed = MiscData.getNowDateTime();
        if (PrefC.getInt(PrefName.ProcessSigsIntervalInSecs) == 0)
        {
            timerSignals.Enabled = false;
        }
        else
        {
            timerSignals.Interval = PrefC.getInt(PrefName.ProcessSigsIntervalInSecs) * 1000;
            timerSignals.Enabled = true;
        } 
        timerTimeIndic.Enabled = true;
        myOutlookBar.Buttons[0].Caption = Lan.g(this,"Appts");
        myOutlookBar.Buttons[1].Caption = Lan.g(this,"Family");
        myOutlookBar.Buttons[2].Caption = Lan.g(this,"Account");
        myOutlookBar.Buttons[3].Caption = Lan.g(this,"Treat' Plan");
        //myOutlookBar.Buttons[4].Caption=Lan.g(this,"Chart");//??done in RefreshLocalData
        myOutlookBar.Buttons[5].Caption = Lan.g(this,"Images");
        myOutlookBar.Buttons[6].Caption = Lan.g(this,"Manage");
        for (Object __dummyForeachVar0 : mainMenu.MenuItems)
        {
            MenuItem menuItem = (MenuItem)__dummyForeachVar0;
            translateMenuItem(menuItem);
        }
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            //for the business layer, this functionality is duplicated in the Lan class.  Need for SL.
            CultureInfo cInfo = (CultureInfo)CultureInfo.CurrentCulture.Clone();
            cInfo.DateTimeFormat.ShortDatePattern = "MM/dd/yyyy";
            Application.CurrentCulture = cInfo;
            //
            //if(CultureInfo.CurrentCulture.TwoLetterISOLanguageName=="en"){
            menuItemTranslation.Visible = false;
        }
        else
        {
            //not en-US
            CultureInfo cInfo = (CultureInfo)CultureInfo.CurrentCulture.Clone();
            String dateFormatCur = cInfo.DateTimeFormat.ShortDatePattern;
            //The carrot indicates the beginning of a word.  {2} means that there has to be exactly 2 y's.  [^a-z] means any character except a through z.  $ means end of word.
            if (Regex.IsMatch(dateFormatCur, "^y{2}[^a-z]") || Regex.IsMatch(dateFormatCur, "[^a-z]y{2}$"))
            {
                //We know there are only two y's in the format.  Force there to be four.
                cInfo.DateTimeFormat.ShortDatePattern = dateFormatCur.Replace("yy", "yyyy");
                Application.CurrentCulture = cInfo;
            }
             
        } 
        if (!File.Exists("Help.chm"))
        {
            menuItemHelpWindows.Visible = false;
        }
         
        if (Environment.OSVersion.Platform == PlatformID.Unix)
        {
            //Create A to Z unsupported on Unix for now.
            menuItemCreateAtoZFolders.Visible = false;
        }
         
        if (!PrefC.getBool(PrefName.ProcLockingIsAllowed))
        {
            menuItemProcLockTool.Visible = false;
        }
         
        if (!PrefC.getBool(PrefName.ADAdescriptionsReset))
        {
            ProcedureCodes.resetADAdescriptions();
            Prefs.updateBool(PrefName.ADAdescriptionsReset,true);
        }
         
        Splash.Dispose();
        //Choose a default DirectX format when no DirectX format has been specified and running in DirectX tooth chart mode.
        //ComputerPref localComputerPrefs=ComputerPrefs.GetForLocalComputer();
        if (ComputerPrefs.getLocalComputer().GraphicsSimple == OpenDentBusiness.DrawingMode.DirectX && StringSupport.equals(ComputerPrefs.getLocalComputer().DirectXFormat, ""))
        {
            //MessageBox.Show(Lan.g(this,"Optimizing tooth chart graphics. This may take a few minutes. You will be notified when the process is complete."));
            ComputerPrefs.getLocalComputer().DirectXFormat = FormGraphics.GetPreferredDirectXFormat(this);
            if (StringSupport.equals(ComputerPrefs.getLocalComputer().DirectXFormat, "invalid"))
            {
                //No valid local DirectX format could be found.
                //Simply revert to OpenGL.
                ComputerPrefs.getLocalComputer().GraphicsSimple = OpenDentBusiness.DrawingMode.Simple2D;
            }
             
            ComputerPrefs.update(ComputerPrefs.getLocalComputer());
            ContrChart2.initializeOnStartup();
        }
         
        //MsgBox.Show(this,"Done optimizing tooth chart graphics.");
        if (CodeBase.ODEnvironment.isRunningOnDbServer(MiscData.getODServer()) && PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            //OpenDental has EHR enabled and is running on the same machine as the mysql server it is connected to.*/
            _threadTimeSynch = new Thread(new ThreadStart(ThreadTimeSynch_Synch));
            _threadTimeSynch.Start();
        }
         
        if (Security.getCurUser() == null)
        {
            //It could already be set if using web service because login from ChooseDatabase window.
            if (Programs.usingEcwTightOrFullMode() && !StringSupport.equals(odUser, ""))
            {
            }
            else
            {
                //only leave it null if a user was passed in on the commandline.  If starting OD manually, it will jump into the else.
                //leave user as null
                if (!StringSupport.equals(odUser, "") && !StringSupport.equals(odPassword, ""))
                {
                    //if a username and password were passed in
                    //Userod user=Userods.GetUserByName(odUser,Programs.UsingEcwTight());
                    Userod user = Userods.getUserByName(odUser,Programs.usingEcwTightOrFullMode());
                    if (user != null)
                    {
                        if (Userods.checkTypedPassword(odPassword,user.Password))
                        {
                            //password matches
                            Security.setCurUser(user.copy());
                            if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
                            {
                                String pw = odPassword;
                                //if(Programs.UsingEcwTight()) {//ecw requires hash, but non-ecw requires actual password
                                if (Programs.usingEcwTightOrFullMode())
                                {
                                    //ecw requires hash, but non-ecw requires actual password
                                    pw = Userods.encryptPassword(pw,true);
                                }
                                 
                                Security.PasswordTyped = pw;
                            }
                             
                        }
                         
                    }
                     
                }
                 
                //TasksCheckOnStartup is one thing that doesn't get done because login window wasn't used
                if (Security.getCurUser() == null)
                {
                    //normal manual log in process
                    Userod adminUser = Userods.getAdminUser();
                    if (StringSupport.equals(adminUser.Password, ""))
                    {
                        Security.setCurUser(adminUser.copy());
                    }
                    else
                    {
                        FormLogOn_ = new FormLogOn();
                        FormLogOn_.ShowDialog(this);
                        if (FormLogOn_.DialogResult == DialogResult.Cancel)
                        {
                            Cursor = Cursors.Default;
                            Application.Exit();
                            return ;
                        }
                         
                    } 
                }
                 
            } 
        }
         
        if (userControlTasks1.Visible)
        {
            userControlTasks1.initializeOnStartup();
        }
         
        myOutlookBar.SelectedIndex = Security.getModule(0);
        //for eCW, this fails silently.
        //if(Programs.UsingEcwTight()) {
        if (Programs.usingEcwTightOrFullMode() || (HL7Defs.isExistingHL7Enabled() && !HL7Defs.getOneDeepEnabled().ShowAppts))
        {
            myOutlookBar.SelectedIndex = 4;
            //Chart module
            //ToolBarMain.Height=0;//this should force the modules further up on the screen
            //ToolBarMain.Visible=false;
            layoutControls();
        }
         
        if (Programs.getUsingOrion())
        {
            myOutlookBar.SelectedIndex = 1;
        }
         
        //Family module
        myOutlookBar.Invalidate();
        layoutToolBar();
        setModuleSelected();
        Cursor = Cursors.Default;
        if (myOutlookBar.SelectedIndex == -1)
        {
            MsgBox.show(this,"You do not have permission to use any modules.");
        }
         
        Trojan.startupCheck();
        FormUAppoint.startThreadIfEnabled();
        ICat.startFileWatcher();
        if (PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            menuItemResellers.Visible = true;
            if (Process.GetProcessesByName("WebCamOD").Length == 0)
            {
                try
                {
                    Process.Start("WebCamOD.exe");
                }
                catch (Exception __dummyCatchVar0)
                {
                }
            
            }
             
            //for example, if working from home.
            ThreadVM = new Thread(new ThreadStart(this.ThreadVM_SetLabelMsg));
            ThreadVM.Start();
            //It's done this way because the file activity tends to lock the UI on slow connections.
            fillTriageLabels();
        }
         
        if (PrefC.getDate(PrefName.BackupReminderLastDateRun).AddMonths(1) < DateTime.Today)
        {
            FormBackupReminder FormBR = new FormBackupReminder();
            FormBR.ShowDialog();
            if (FormBR.DialogResult == DialogResult.OK)
            {
                Prefs.updateDateT(PrefName.BackupReminderLastDateRun,DateTimeOD.getToday());
            }
            else
            {
                Application.Exit();
                return ;
            } 
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureEhr) && !_isTimeSynchThreadRunning)
        {
            FormEhrTimeSynch FormEhrTS = new FormEhrTimeSynch();
            FormEhrTS.IsAutoLaunch = true;
            if (!FormEhrTS.timesInSynchFast())
            {
                FormEhrTS.ShowDialog();
            }
             
        }
         
        fillPatientButton(null);
        ThreadCommandLine = new Thread(new ThreadStart(Listen));
        if (!IsSecondInstance)
        {
        }
         
        //can't use a port that's already in use.
        //js We can't do this yet.  I tried it already, and it consumes nearly 100% CPU.  Not while testing, but later.
        //So until we really need to do it, it's easiest no just not start the thread for now.
        //ThreadCommandLine.Start();
        //if(CommandLineArgs.Length>0) {
        processCommandLine(CommandLineArgs);
        try
        {
            //}
            Computers.UpdateHeartBeat(Environment.MachineName, true);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        //string dllPathEHR=ODFileUtils.CombinePaths(Application.StartupPath,"EHR.dll");
        //if(PrefC.GetBoolSilent(PrefName.ShowFeatureEhr,false)) {
        //	#if EHRTEST
        //		FormEHR=new FormEHR();
        //		ContrChart2.InitializeLocalData();//because toolbar is now missing the EHR button.  Only a problem if a db conversion is done when opening the program.
        //	#else
        //		ObjFormEhrMeasures=null;
        //		AssemblyEHR=null;
        //		if(File.Exists(dllPathEHR)) {//EHR.dll is available, so load it up
        //			AssemblyEHR=Assembly.LoadFile(dllPathEHR);
        //			Type type=AssemblyEHR.GetType("EHR.FormEHR");//namespace.class
        //			ObjFormEhrMeasures=Activator.CreateInstance(type);
        //		}
        //	#endif
        //}
        dateTimeLastActivity = DateTime.Now;
        timerLogoff.Enabled = true;
        timerReplicationMonitor.Enabled = true;
        ThreadEmailInbox = new Thread(new ThreadStart(ThreadEmailInbox_Receive));
        ThreadEmailInbox.Start();
        Plugins.hookAddCode(this,"FormOpenDental.Load_end");
    }

    private void comboTriageCoordinator_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboTriageCoordinator.SelectedIndex < 0)
        {
            return ;
        }
         
        Employee employee = Employees.GetEmp((String)comboTriageCoordinator.Items[comboTriageCoordinator.SelectedIndex], new List<Employee>(Employees.getListShort()));
        if (employee == null)
        {
            return ;
        }
         
        if (Prefs.updateLong(PrefName.HQTriageCoordinator,employee.EmployeeNum))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    /**
    * Returns false if it can't complete a conversion, find datapath, or validate registration key.
    */
    private boolean prefsStartup() throws Exception {
        Cache.refresh(InvalidType.Prefs);
        if (!PrefL.checkMySqlVersion())
        {
            return false;
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            try
            {
                MiscData.setSqlMode();
            }
            catch (Exception __dummyCatchVar2)
            {
                MessageBox.Show("Unable to set global sql mode.  User probably does not have enough permission.");
                return false;
            }

            String updateComputerName = PrefC.getStringSilent(PrefName.UpdateInProgressOnComputerName);
            if (!StringSupport.equals(updateComputerName, "") && !StringSupport.equals(Environment.MachineName, updateComputerName))
            {
                DialogResult result = MessageBox.Show("An update is in progress on " + updateComputerName + ".  Not allowed to start up until that update is complete.\r\n\r\nIf you are the person who started the update and you wish to override this message because an update is not in progress, click Retry.\r\n\r\nDo not click Retry unless you started the update.", "", MessageBoxButtons.RetryCancel);
                if (result == DialogResult.Retry)
                {
                    Prefs.updateString(PrefName.UpdateInProgressOnComputerName,"");
                    MsgBox.show(this,"You will be allowed access when you restart.");
                }
                 
                return false;
            }
             
        }
         
        //if RemotingRole.ClientWeb, version will have already been checked at login, so no danger here.
        //ClientWeb version can be older than this version, but that will be caught in a moment.
        if (!PrefL.convertDB())
        {
            return false;
        }
         
        PrefL.mySqlVersion55Remind();
        if (PrefC.getAtoZfolderUsed())
        {
            String prefImagePath = ImageStore.getPreferredAtoZpath();
            if (prefImagePath == null || !Directory.Exists(prefImagePath))
            {
                //AtoZ folder not found
                Cache.refresh(InvalidType.Security);
                FormPath FormP = new FormPath();
                FormP.IsStartingUp = true;
                FormP.ShowDialog();
                if (FormP.DialogResult != DialogResult.OK)
                {
                    MsgBox.show(this,"Invalid A to Z path.  Closing program.");
                    Application.Exit();
                    return false;
                }
                 
                Cache.refresh(InvalidType.Prefs);
            }
             
        }
         
        //because listening thread not started yet.
        if (!PrefL.checkProgramVersion())
        {
            return false;
        }
         
        if (!FormRegistrationKey.validateKey(PrefC.getString(PrefName.RegistrationKey)))
        {
            //true){
            FormRegistrationKey FormR = new FormRegistrationKey();
            FormR.ShowDialog();
            if (FormR.DialogResult != DialogResult.OK)
            {
                Application.Exit();
                return false;
            }
             
            Cache.refresh(InvalidType.Prefs);
        }
         
        Lans.refreshCache();
        //automatically skips if current culture is en-US
        LanguageForeigns.Refresh(CultureInfo.CurrentCulture.Name, CultureInfo.CurrentCulture.TwoLetterISOLanguageName);
        return true;
    }

    //automatically skips if current culture is en-US
    //menuItemMergeDatabases.Visible=PrefC.GetBool(PrefName.RandomPrimaryKeys");
    /**
    * Refreshes certain rarely used data from database.  Must supply the types of data to refresh as flags.  Also performs a few other tasks that must be done when local data is changed.
    */
    private void refreshLocalData(InvalidType... itypes) throws Exception {
        List<int> itypeList = new List<int>();
        for (int i = 0;i < itypes.Length;i++)
        {
            itypeList.Add((int)itypes[i]);
        }
        String itypesStr = "";
        boolean isAll = false;
        for (int i = 0;i < itypes.Length;i++)
        {
            if (i > 0)
            {
                itypesStr += ",";
            }
             
            itypesStr += ((int)itypes[i]).ToString();
            if (itypes[i] == InvalidType.AllLocal)
            {
                isAll = true;
            }
             
        }
        Cache.refreshCache(itypesStr);
        RefreshLocalDataPostCleanup(itypeList, isAll, itypes);
    }

    /**
    * Performs a few tasks that must be done when local data is changed.
    */
    private void refreshLocalDataPostCleanup(List<int> itypeList, boolean isAll, InvalidType... itypes) throws Exception {
        if (itypeList.Contains(((Enum)InvalidType.Prefs).ordinal()) || isAll)
        {
            if (PrefC.getBool(PrefName.EasyHidePublicHealth))
            {
                menuItemSchools.Visible = false;
                menuItemCounties.Visible = false;
                menuItemScreening.Visible = false;
            }
            else
            {
                menuItemSchools.Visible = true;
                menuItemCounties.Visible = true;
                menuItemScreening.Visible = true;
            } 
            if (PrefC.getBool(PrefName.EasyNoClinics))
            {
                menuItemClinics.Visible = false;
            }
            else
            {
                menuItemClinics.Visible = true;
            } 
            if (PrefC.getBool(PrefName.EasyHideClinical))
            {
                myOutlookBar.Buttons[4].Caption = Lan.g(this,"Procs");
            }
            else
            {
                myOutlookBar.Buttons[4].Caption = Lan.g(this,"Chart");
            } 
            if (PrefC.getBool(PrefName.EasyHideDentalSchools))
            {
                menuItemSchoolClass.Visible = false;
                menuItemSchoolCourses.Visible = false;
                menuItemRequirementsNeeded.Visible = false;
                menuItemReqStudents.Visible = false;
            }
            else
            {
                menuItemSchoolClass.Visible = true;
                menuItemSchoolCourses.Visible = true;
                menuItemRequirementsNeeded.Visible = true;
                menuItemReqStudents.Visible = true;
            } 
            if (PrefC.getBool(PrefName.EasyHideRepeatCharges))
            {
                menuItemRepeatingCharges.Visible = false;
            }
            else
            {
                menuItemRepeatingCharges.Visible = true;
            } 
            if (StringSupport.equals(PrefC.getString(PrefName.DistributorKey), ""))
            {
                menuItemCustomerManage.Visible = false;
                menuItemNewCropBilling.Visible = false;
            }
            else
            {
                menuItemCustomerManage.Visible = true;
                menuItemNewCropBilling.Visible = true;
            } 
            checkCustomReports();
            ContrChart2.initializeLocalData();
            if (PrefC.getBool(PrefName.TaskListAlwaysShowsAtBottom))
            {
                //separate if statement to prevent database call if not showing task list at bottom to begin with
                //ComputerPref computerPref = ComputerPrefs.GetForLocalComputer();
                if (ComputerPrefs.getLocalComputer().TaskKeepListHidden)
                {
                    userControlTasks1.Visible = false;
                }
                else if (this.WindowState != FormWindowState.Minimized)
                {
                    //task list show and window is not minimized.
                    userControlTasks1.Visible = true;
                    userControlTasks1.initializeOnStartup();
                    if (ComputerPrefs.getLocalComputer().TaskDock == 0)
                    {
                        //bottom
                        menuItemDockBottom.Checked = true;
                        menuItemDockRight.Checked = false;
                        panelSplitter.Cursor = Cursors.HSplit;
                        panelSplitter.Height = 7;
                        int splitterNewY = 540;
                        if (ComputerPrefs.getLocalComputer().TaskY != 0)
                        {
                            splitterNewY = ComputerPrefs.getLocalComputer().TaskY;
                            if (splitterNewY < 300)
                            {
                                splitterNewY = 300;
                            }
                             
                            //keeps it from going too high
                            if (splitterNewY > ClientSize.Height - 50)
                            {
                                splitterNewY = ClientSize.Height - panelSplitter.Height - 50;
                            }
                             
                        }
                         
                        //keeps it from going off the bottom edge
                        panelSplitter.Location = new Point(myOutlookBar.Width, splitterNewY);
                    }
                    else
                    {
                        //right
                        menuItemDockRight.Checked = true;
                        menuItemDockBottom.Checked = false;
                        panelSplitter.Cursor = Cursors.VSplit;
                        panelSplitter.Width = 7;
                        int splitterNewX = 900;
                        if (ComputerPrefs.getLocalComputer().TaskX != 0)
                        {
                            splitterNewX = ComputerPrefs.getLocalComputer().TaskX;
                            if (splitterNewX < 300)
                            {
                                splitterNewX = 300;
                            }
                             
                            //keeps it from going too far to the left
                            if (splitterNewX > ClientSize.Width - 50)
                            {
                                splitterNewX = ClientSize.Width - panelSplitter.Width - 50;
                            }
                             
                        }
                         
                        //keeps it from going off the right edge
                        panelSplitter.Location = new Point(splitterNewX, ToolBarMain.Height);
                    } 
                }
                  
            }
            else
            {
                userControlTasks1.Visible = false;
            } 
            layoutControls();
        }
         
        if (itypeList.Contains(((Enum)InvalidType.Signals).ordinal()) || isAll)
        {
            fillSignalButtons(null);
        }
         
        if (itypeList.Contains(((Enum)InvalidType.Programs).ordinal()) || isAll)
        {
            if (Programs.getCur(ProgramName.PT).Enabled)
            {
                PaperlessTechnology.initializeFileWatcher();
            }
             
        }
         
        if (itypeList.Contains(((Enum)InvalidType.Programs).ordinal()) || itypeList.Contains(((Enum)InvalidType.Prefs).ordinal()) || isAll)
        {
            if (PrefC.getBool(PrefName.EasyBasicModules))
            {
                myOutlookBar.Buttons[3].Visible = false;
                myOutlookBar.Buttons[5].Visible = false;
                myOutlookBar.Buttons[6].Visible = false;
            }
            else
            {
                myOutlookBar.Buttons[3].Visible = true;
                myOutlookBar.Buttons[5].Visible = true;
                myOutlookBar.Buttons[6].Visible = true;
            } 
            if (Programs.usingEcwTightOrFullMode())
            {
                //has nothing to do with HL7
                if (StringSupport.equals(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"ShowImagesModule"), "1"))
                {
                    myOutlookBar.Buttons[5].Visible = true;
                }
                else
                {
                    myOutlookBar.Buttons[5].Visible = false;
                } 
            }
             
            if (Programs.usingEcwTightMode())
            {
                //has nothing to do with HL7
                myOutlookBar.Buttons[6].Visible = false;
            }
             
            if (!Programs.usingEcwTightOrFullMode() && HL7Defs.isExistingHL7Enabled())
            {
                //There may be a def enabled as well as the old program link enabled. In this case, do not look at the def for whether or not to show the appt and account modules, instead go by the eCW interface enabled.
                HL7Def def = HL7Defs.getOneDeepEnabled();
                myOutlookBar.Buttons[0].Visible = def.ShowAppts;
                //Appt
                myOutlookBar.Buttons[2].Visible = def.ShowAccount;
            }
            else
            {
                //Account
                //old eCW interfaces
                if (Programs.usingEcwTightMode())
                {
                    myOutlookBar.Buttons[0].Visible = false;
                    //Appt
                    myOutlookBar.Buttons[2].Visible = false;
                }
                else //Account
                if (Programs.usingEcwFullMode())
                {
                    //We might create a special Appt module for eCW full users so they can access Recall.
                    myOutlookBar.Buttons[0].Visible = false;
                }
                  
            } 
            //Appt
            if (Programs.getUsingOrion())
            {
                myOutlookBar.Buttons[0].Visible = false;
                //Appt module
                myOutlookBar.Buttons[2].Visible = false;
                //Account module
                myOutlookBar.Buttons[3].Visible = false;
            }
             
            //TP module
            myOutlookBar.Invalidate();
        }
         
        if (itypeList.Contains(((Enum)InvalidType.ToolBut).ordinal()) || isAll)
        {
            ContrAccount2.layoutToolBar();
            ContrAppt2.layoutToolBar();
            ContrChart2.layoutToolBar();
            ContrImages2.layoutToolBar();
            ContrFamily2.layoutToolBar();
        }
         
        if (itypeList.Contains(((Enum)InvalidType.Views).ordinal()) || isAll)
        {
            ContrAppt2.fillViews();
        }
         
        if (PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            if (itypeList.Contains(((Enum)InvalidType.Employees).ordinal()) || itypeList.Contains(((Enum)InvalidType.Prefs).ordinal()) || isAll)
            {
                //refill the triage coordinator combo box
                comboTriageCoordinator.Items.Clear();
                Employee currentTriageEmployee = null;
                try
                {
                    currentTriageEmployee = Employees.getEmp(PrefC.getLong(PrefName.HQTriageCoordinator));
                }
                catch (Exception __dummyCatchVar3)
                {
                }

                //swallow the case where PrefName.HQTriageCoordinator not found
                int iSelItem = -1;
                for (int i = 0;i < Employees.getListShort().Length;i++)
                {
                    int iNewItem = comboTriageCoordinator.Items.Add(Employees.GetNameFL(Employees.getListShort()[i]));
                    if (currentTriageEmployee != null && currentTriageEmployee.EmployeeNum == Employees.getListShort()[i].EmployeeNum)
                    {
                        iSelItem = iNewItem;
                    }
                     
                }
                if (iSelItem >= 0)
                {
                    comboTriageCoordinator.SelectedIndex = iSelItem;
                }
                 
            }
             
        }
         
        ContrTreat2.initializeLocalData();
    }

    //easier to leave this here for now than to split it.
    /**
    * Sets up the custom reports list in the main menu when certain requirements are met, or disables the custom reports menu item when those same conditions are not met. This function is called during initialization, and on the event that the A to Z folder usage has changed.
    */
    private void checkCustomReports() throws Exception {
        menuItemCustomReports.MenuItems.Clear();
        //Try to load custom reports, but only if using the A to Z folders.
        if (PrefC.getAtoZfolderUsed())
        {
            String imagePath = ImageStore.getPreferredAtoZpath();
            String reportFolderName = PrefC.getString(PrefName.ReportFolderName);
            String reportDir = CodeBase.ODFileUtils.combinePaths(imagePath,reportFolderName);
            if (Directory.Exists(reportDir))
            {
                DirectoryInfo infoDir = new DirectoryInfo(reportDir);
                FileInfo[] filesRdl = infoDir.GetFiles("*.rdl");
                for (int i = 0;i < filesRdl.Length;i++)
                {
                    String itemName = Path.GetFileNameWithoutExtension(filesRdl[i].Name);
                    menuItemCustomReports.MenuItems.Add(itemName, new System.EventHandler(this.menuItemRDLReport_Click));
                }
            }
             
        }
         
        if (menuItemCustomReports.MenuItems.Count == 0)
        {
            menuItemCustomReports.Visible = false;
        }
        else
        {
            menuItemCustomReports.Visible = true;
        } 
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Select Patient"), 0, "", "Patient");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(menuPatient);
        ToolBarMain.getButtons().add(button);
        if (!Programs.usingEcwTightMode())
        {
            //eCW tight only gets Patient Select and Popups toolbar buttons
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Commlog"), 1, Lan.g(this,"New Commlog Entry"), "Commlog"));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"E-mail"), 2, Lan.g(this,"Send E-mail"), "Email");
            ToolBarMain.getButtons().add(button);
            button = new OpenDental.UI.ODToolBarButton("", -1, "", "EmailDropdown");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            button.setDropDownMenu(menuEmail);
            ToolBarMain.getButtons().add(button);
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Text"), 5, Lan.g(this,"Send Text Message"), "Text"));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Letter"), -1, Lan.g(this,"Quick Letter"), "Letter");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            button.setDropDownMenu(menuLetter);
            ToolBarMain.getButtons().add(button);
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Forms"), -1, "", "Form");
            //button.Style=ODToolBarButtonStyle.DropDownButton;
            //button.DropDownMenu=menuForm;
            ToolBarMain.getButtons().add(button);
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"To Task List"), 3, Lan.g(this,"Send to Task List"), "Tasklist"));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Label"), 4, Lan.g(this,"Print Label"), "Label");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            button.setDropDownMenu(menuLabel);
            ToolBarMain.getButtons().add(button);
        }
         
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Popups"), -1, Lan.g(this,"Edit popups for this patient"), "Popups"));
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.AllModules);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            //ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        Plugins.hookAddCode(this,"FormOpenDental.LayoutToolBar_end");
        ToolBarMain.Invalidate();
    }

    private void menuPatient_Popup(Object sender, EventArgs e) throws Exception {
        if (CurPatNum == 0)
        {
            return ;
        }
         
        Family fam = Patients.getFamily(CurPatNum);
        PatientL.addFamilyToMenu(menuPatient,new EventHandler(menuPatient_Click),CurPatNum,fam);
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
            if (__dummyScrutVar0.equals("Patient"))
            {
                onPatient_Click();
            }
            else if (__dummyScrutVar0.equals("Commlog"))
            {
                onCommlog_Click();
            }
            else if (__dummyScrutVar0.equals("Email"))
            {
                onEmail_Click();
            }
            else if (__dummyScrutVar0.equals("Text"))
            {
                onTxtMsg_Click();
            }
            else if (__dummyScrutVar0.equals("Letter"))
            {
                onLetter_Click();
            }
            else if (__dummyScrutVar0.equals("Form"))
            {
                onForm_Click();
            }
            else if (__dummyScrutVar0.equals("Tasklist"))
            {
                onTasklist_Click();
            }
            else if (__dummyScrutVar0.equals("Label"))
            {
                onLabel_Click();
            }
            else if (__dummyScrutVar0.equals("Popups"))
            {
                onPopups_Click();
            }
                     
        }
        else if (e.getButton().getTag().GetType() == long.class)
        {
            ProgramL.execute((long)e.getButton().getTag(),Patients.getPat(CurPatNum));
        }
          
    }

    private void onPatient_Click() throws Exception {
        FormPatientSelect formPS = new FormPatientSelect();
        formPS.ShowDialog();
        if (formPS.DialogResult == DialogResult.OK)
        {
            CurPatNum = formPS.SelectedPatNum;
            Patient pat = Patients.getPat(CurPatNum);
            if (ContrChart2.Visible)
            {
                //If currently in the chart module, then also refresh NewCrop prescription information on top of a regular refresh.
                ContrChart2.moduleSelectedNewCrop(CurPatNum);
            }
            else
            {
                refreshCurrentModule();
            } 
            fillPatientButton(pat);
            Plugins.hookAddCode(this,"FormOpenDental.OnPatient_Click_end");
        }
         
    }

    private void menuPatient_Click(Object sender, System.EventArgs e) throws Exception {
        Family fam = Patients.getFamily(CurPatNum);
        CurPatNum = PatientL.buttonSelect(menuPatient,sender,fam);
        //new family now
        Patient pat = Patients.getPat(CurPatNum);
        refreshCurrentModule();
        fillPatientButton(pat);
    }

    /**
    * Happens when any of the modules changes the current patient or when this main form changes the patient.  The calling module should refresh itself.  The current patNum is stored here in the parent form so that when switching modules, the parent form knows which patient to call up for that module.
    */
    private void contr_PatientSelected(Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
        CurPatNum = e.getPat().PatNum;
        fillPatientButton(e.getPat());
    }

    /**
    * Serves four functions.  1. Sends the new patient to the dropdown menu for select patient.  2. Changes which toolbar buttons are enabled.  3. Sets main form text. 4. Displays any popup.
    */
    private void fillPatientButton(Patient pat) throws Exception {
        if (pat == null)
        {
            pat = new Patient();
        }
         
        boolean patChanged = PatientL.addPatsToMenu(menuPatient,new EventHandler(menuPatient_Click),pat.getNameLF(),pat.PatNum);
        if (patChanged)
        {
            if (AutomationL.trigger(AutomationTrigger.OpenPatient,null,pat.PatNum))
            {
                //if a trigger happened
                if (ContrAppt2.Visible)
                {
                    ContrAppt2.mouseUpForced();
                }
                 
            }
             
        }
         
        if (ToolBarMain.getButtons() == null || ToolBarMain.getButtons().Count < 2)
        {
            return ;
        }
         
        //on startup.  js Not sure why it's checking count.
        if (CurPatNum == 0)
        {
            //Only on startup, I think.
            if (!Programs.usingEcwTightMode())
            {
                //eCW tight only gets Patient Select and Popups toolbar buttons
                ToolBarMain.getButtons().get___idx("Email").setEnabled(false);
                ToolBarMain.getButtons().get___idx("EmailDropdown").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Text").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Commlog").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Letter").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Form").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Tasklist").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Label").setEnabled(false);
            }
             
            ToolBarMain.getButtons().get___idx("Popups").setEnabled(false);
        }
        else
        {
            if (!Programs.usingEcwTightMode())
            {
                ToolBarMain.getButtons().get___idx("Commlog").setEnabled(true);
                if (!StringSupport.equals(pat.Email, ""))
                {
                    ToolBarMain.getButtons().get___idx("Email").setEnabled(true);
                }
                else
                {
                    ToolBarMain.getButtons().get___idx("Email").setEnabled(false);
                } 
                if (StringSupport.equals(pat.WirelessPhone, "") || !Programs.isEnabled(ProgramName.CallFire))
                {
                    ToolBarMain.getButtons().get___idx("Text").setEnabled(false);
                }
                else
                {
                    //Pat has a wireless phone number and CallFire is enabled
                    if (pat.TxtMsgOk == YN.Unknown)
                    {
                        ToolBarMain.getButtons().get___idx("Text").setEnabled(!PrefC.getBool(PrefName.TextMsgOkStatusTreatAsNo));
                    }
                    else
                    {
                        //Not enabled since TxtMsgOk is ?? and "Treat ?? As No" is true.
                        ToolBarMain.getButtons().get___idx("Text").setEnabled((pat.TxtMsgOk == YN.Yes));
                    } 
                } 
                ToolBarMain.getButtons().get___idx("EmailDropdown").setEnabled(true);
                ToolBarMain.getButtons().get___idx("Letter").setEnabled(true);
                ToolBarMain.getButtons().get___idx("Form").setEnabled(true);
                ToolBarMain.getButtons().get___idx("Tasklist").setEnabled(true);
                ToolBarMain.getButtons().get___idx("Label").setEnabled(true);
            }
             
            ToolBarMain.getButtons().get___idx("Popups").setEnabled(true);
        } 
        ToolBarMain.Invalidate();
        Text = PatientL.getMainTitle(pat);
        if (PopupEventList == null)
        {
            PopupEventList = new List<PopupEvent>();
        }
         
        if (Plugins.hookMethod(this,"FormOpenDental.FillPatientButton_popups",pat,PopupEventList,patChanged))
        {
            return ;
        }
         
        if (!patChanged)
        {
            return ;
        }
         
        for (int i = PopupEventList.Count - 1;i >= 0;i--)
        {
            //New patient selected.  Everything below here is for popups.
            //First, remove all expired popups from the event list.
            //go backwards
            if (PopupEventList[i].DisableUntil < DateTime.Now)
            {
                //expired
                PopupEventList.RemoveAt(i);
            }
             
        }
        //Now, loop through all popups for the patient.
        List<Popup> popList = Popups.getForPatient(pat.PatNum);
        for (int i = 0;i < popList.Count;i++)
        {
            //get all possible
            //skip any popups that are disabled because they are on the event list
            boolean popupIsDisabled = false;
            for (int e = 0;e < PopupEventList.Count;e++)
            {
                if (popList[i].PopupNum == PopupEventList[e].PopupNum)
                {
                    popupIsDisabled = true;
                    break;
                }
                 
            }
            if (popupIsDisabled)
            {
                continue;
            }
             
            //This popup is not disabled, so show it.
            //A future improvement would be to assemble all the popups that are to be shown and then show them all in one large window.
            //But for now, they will show in sequence.
            if (ContrAppt2.Visible)
            {
                ContrAppt2.mouseUpForced();
            }
             
            FormPopupDisplay FormP = new FormPopupDisplay();
            FormP.PopupCur = popList[i];
            FormP.ShowDialog();
            if (FormP.MinutesDisabled > 0)
            {
                PopupEvent popevent = new PopupEvent();
                popevent.PopupNum = popList[i].PopupNum;
                popevent.DisableUntil = DateTime.Now + TimeSpan.FromMinutes(FormP.MinutesDisabled);
                PopupEventList.Add(popevent);
                PopupEventList.Sort();
            }
             
        }
    }

    private void onEmail_Click() throws Exception {
        //this button item will be disabled if pat does not have email address
        EmailMessage message = new EmailMessage();
        message.PatNum = CurPatNum;
        Patient pat = Patients.getPat(CurPatNum);
        message.ToAddress = pat.Email;
        message.FromAddress = EmailAddresses.getByClinic(pat.ClinicNum).SenderAddress;
        FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult == DialogResult.OK)
        {
            refreshCurrentModule();
        }
         
    }

    private void menuEmail_Popup(Object sender, EventArgs e) throws Exception {
        menuEmail.MenuItems.Clear();
        menuEmail.MenuItems.Add(new MenuItem(Lan.g(this,"Secure Web Mail"), menuWebMail_Click));
        MenuItem menuItem = new MenuItem();
        menuItem = new MenuItem(Lan.g(this,"Referrals:"));
        menuItem.Tag = null;
        menuEmail.MenuItems.Add(menuItem);
        List<RefAttach> refAttaches = RefAttaches.Refresh(CurPatNum);
        Referral refer;
        String str = new String();
        for (int i = 0;i < refAttaches.Count;i++)
        {
            refer = Referrals.GetReferral(refAttaches[i].ReferralNum);
            if (refAttaches[i].IsFrom)
            {
                str = Lan.g(this,"From ");
            }
            else
            {
                str = Lan.g(this,"To ");
            } 
            str += Referrals.getNameFL(refer.ReferralNum) + " <";
            if (StringSupport.equals(refer.EMail, ""))
            {
                str += Lan.g(this,"no email");
            }
            else
            {
                str += refer.EMail;
            } 
            str += ">";
            menuItem = new MenuItem(str, menuEmail_Click);
            menuItem.Tag = refer;
            menuEmail.MenuItems.Add(menuItem);
        }
    }

    private void menuWebMail_Click(Object sender, System.EventArgs e) throws Exception {
        FormWebMailMessageEdit FormWMME = new FormWebMailMessageEdit(CurPatNum);
        FormWMME.ShowDialog();
    }

    private void menuEmail_Click(Object sender, System.EventArgs e) throws Exception {
        if (((MenuItem)sender).Tag == null)
        {
            return ;
        }
         
        LabelSingle label = new LabelSingle();
        if (((MenuItem)sender).Tag.GetType() == Referral.class)
        {
            Referral refer = (Referral)((MenuItem)sender).Tag;
            if (StringSupport.equals(refer.EMail, ""))
            {
                return ;
            }
             
            //MsgBox.Show(this,"");
            EmailMessage message = new EmailMessage();
            message.PatNum = CurPatNum;
            Patient pat = Patients.getPat(CurPatNum);
            message.ToAddress = refer.EMail;
            //pat.Email;
            message.FromAddress = EmailAddresses.getByClinic(pat.ClinicNum).SenderAddress;
            message.Subject = Lan.g(this,"RE: ") + pat.getNameFL();
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
            FormE.IsNew = true;
            FormE.ShowDialog();
            if (FormE.DialogResult == DialogResult.OK)
            {
                refreshCurrentModule();
            }
             
        }
         
    }

    private void onCommlog_Click() throws Exception {
        Commlog CommlogCur = new Commlog();
        CommlogCur.PatNum = CurPatNum;
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        if (PrefC.getBool(PrefName.DistributorKey))
        {
            //for OD HQ
            CommlogCur.Mode_ = CommItemMode.None;
            CommlogCur.SentOrReceived = CommSentOrReceived.Neither;
        }
        else
        {
            CommlogCur.Mode_ = CommItemMode.Phone;
            CommlogCur.SentOrReceived = CommSentOrReceived.Received;
        } 
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        FormCommItem FormCI = new FormCommItem(CommlogCur);
        FormCI.IsNew = true;
        FormCI.ShowDialog();
        if (FormCI.DialogResult == DialogResult.OK)
        {
            refreshCurrentModule();
        }
         
    }

    private void onLetter_Click() throws Exception {
        FormSheetPicker FormS = new FormSheetPicker();
        FormS.SheetType = SheetTypeEnum.PatientLetter;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDef sheetDef = FormS.SelectedSheetDefs[0];
        Sheet sheet = SheetUtil.createSheet(sheetDef,CurPatNum);
        SheetParameter.setParameter(sheet,"PatNum",CurPatNum);
        //SheetParameter.SetParameter(sheet,"ReferralNum",referral.ReferralNum);
        SheetFiller.fillFields(sheet);
        SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        if (FormSF.DialogResult == DialogResult.OK)
        {
            refreshCurrentModule();
        }
         
    }

    //Patient pat=Patients.GetPat(CurPatNum);
    //FormLetters FormL=new FormLetters(pat);
    //FormL.ShowDialog();
    private void menuLetter_Popup(Object sender, EventArgs e) throws Exception {
        menuLetter.MenuItems.Clear();
        MenuItem menuItem = new MenuItem();
        menuItem = new MenuItem(Lan.g(this,"Merge"), menuLetter_Click);
        menuItem.Tag = "Merge";
        menuLetter.MenuItems.Add(menuItem);
        //menuItem=new MenuItem(Lan.g(this,"Stationery"),menuLetter_Click);
        //menuItem.Tag="Stationery";
        //menuLetter.MenuItems.Add(menuItem);
        menuLetter.MenuItems.Add("-");
        //Referrals---------------------------------------------------------------------------------------
        menuItem = new MenuItem(Lan.g(this,"Referrals:"));
        menuItem.Tag = null;
        menuLetter.MenuItems.Add(menuItem);
        List<RefAttach> refAttaches = RefAttaches.Refresh(CurPatNum);
        Referral refer;
        String str = new String();
        for (int i = 0;i < refAttaches.Count;i++)
        {
            refer = Referrals.GetReferral(refAttaches[i].ReferralNum);
            if (refAttaches[i].IsFrom)
            {
                str = Lan.g(this,"From ");
            }
            else
            {
                str = Lan.g(this,"To ");
            } 
            str += Referrals.getNameFL(refer.ReferralNum);
            menuItem = new MenuItem(str, menuLetter_Click);
            menuItem.Tag = refer;
            menuLetter.MenuItems.Add(menuItem);
        }
    }

    private void menuLetter_Click(Object sender, System.EventArgs e) throws Exception {
        if (((MenuItem)sender).Tag == null)
        {
            return ;
        }
         
        Patient pat = Patients.getPat(CurPatNum);
        if (((MenuItem)sender).Tag.GetType() == String.class)
        {
            if (StringSupport.equals(((MenuItem)sender).Tag.ToString(), "Merge"))
            {
                FormLetterMerges FormL = new FormLetterMerges(pat);
                FormL.ShowDialog();
            }
             
        }
         
        //if(((MenuItem)sender).Tag.ToString()=="Stationery") {
        //	FormCommunications.PrintStationery(pat);
        //}
        if (((MenuItem)sender).Tag.GetType() == Referral.class)
        {
            Referral refer = (Referral)((MenuItem)sender).Tag;
            FormSheetPicker FormS = new FormSheetPicker();
            FormS.SheetType = SheetTypeEnum.ReferralLetter;
            FormS.ShowDialog();
            if (FormS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            SheetDef sheetDef = FormS.SelectedSheetDefs[0];
            Sheet sheet = SheetUtil.createSheet(sheetDef,CurPatNum);
            SheetParameter.setParameter(sheet,"PatNum",CurPatNum);
            SheetParameter.setParameter(sheet,"ReferralNum",refer.ReferralNum);
            SheetFiller.fillFields(sheet);
            SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
            FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
            FormSF.ShowDialog();
            if (FormSF.DialogResult == DialogResult.OK)
            {
                refreshCurrentModule();
            }
             
        }
         
    }

    //FormLetters FormL=new FormLetters(pat);
    //FormL.ReferralCur=refer;
    //FormL.ShowDialog();
    private void onForm_Click() throws Exception {
        FormPatientForms formP = new FormPatientForms();
        formP.PatNum = CurPatNum;
        formP.ShowDialog();
        //if(ContrAccount2.Visible || ContrChart2.Visible//The only two modules where a new form would show.
        //	|| ContrFamily2.Visible){//patient info
        //always refresh, especially to get the titlebar right after an import.
        Patient pat = Patients.getPat(CurPatNum);
        refreshCurrentModule();
        fillPatientButton(pat);
    }

    //}
    private void onTasklist_Click() throws Exception {
        FormTaskListSelect FormT = new FormTaskListSelect(TaskObjectType.Patient);
        //,CurPatNum);
        FormT.Location = new Point(50, 50);
        FormT.ShowDialog();
        if (FormT.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Task task = new Task();
        task.TaskListNum = -1;
        //don't show it in any list yet.
        Tasks.insert(task);
        Task taskOld = task.copy();
        task.KeyNum = CurPatNum;
        task.ObjectType = TaskObjectType.Patient;
        task.TaskListNum = FormT.SelectedTaskListNum;
        task.UserNum = Security.getCurUser().UserNum;
        FormTaskEdit FormTE = new FormTaskEdit(task,taskOld);
        FormTE.IsNew = true;
        FormTE.Show();
    }

    private void onLabel_Click() throws Exception {
        //LabelSingle label=new LabelSingle();
        LabelSingle.printPat(CurPatNum);
    }

    private void menuLabel_Popup(Object sender, EventArgs e) throws Exception {
        menuLabel.MenuItems.Clear();
        MenuItem menuItem = new MenuItem();
        List<SheetDef> LabelList = SheetDefs.getCustomForType(SheetTypeEnum.LabelPatient);
        if (LabelList.Count == 0)
        {
            menuItem = new MenuItem(Lan.g(this,"LName, FName, Address"), menuLabel_Click);
            menuItem.Tag = "PatientLFAddress";
            menuLabel.MenuItems.Add(menuItem);
            menuItem = new MenuItem(Lan.g(this,"Name, ChartNumber"), menuLabel_Click);
            menuItem.Tag = "PatientLFChartNumber";
            menuLabel.MenuItems.Add(menuItem);
            menuItem = new MenuItem(Lan.g(this,"Name, PatNum"), menuLabel_Click);
            menuItem.Tag = "PatientLFPatNum";
            menuLabel.MenuItems.Add(menuItem);
            menuItem = new MenuItem(Lan.g(this,"Radiograph"), menuLabel_Click);
            menuItem.Tag = "PatRadiograph";
            menuLabel.MenuItems.Add(menuItem);
        }
        else
        {
            for (int i = 0;i < LabelList.Count;i++)
            {
                menuItem = new MenuItem(LabelList[i].Description, menuLabel_Click);
                menuItem.Tag = LabelList[i];
                menuLabel.MenuItems.Add(menuItem);
            }
        } 
        menuLabel.MenuItems.Add("-");
        //Carriers---------------------------------------------------------------------------------------
        Family fam = Patients.getFamily(CurPatNum);
        List<PatPlan> PatPlanList = PatPlans.refresh(CurPatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> PlanList = InsPlans.RefreshForSubList(subList);
        Carrier carrier;
        InsPlan plan;
        InsSub sub;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            sub = InsSubs.GetSub(PatPlanList[i].InsSubNum, subList);
            plan = InsPlans.GetPlan(sub.PlanNum, PlanList);
            carrier = Carriers.getCarrier(plan.CarrierNum);
            menuItem = new MenuItem(carrier.CarrierName, menuLabel_Click);
            menuItem.Tag = carrier;
            menuLabel.MenuItems.Add(menuItem);
        }
        menuLabel.MenuItems.Add("-");
        //Referrals---------------------------------------------------------------------------------------
        menuItem = new MenuItem(Lan.g(this,"Referrals:"));
        menuItem.Tag = null;
        menuLabel.MenuItems.Add(menuItem);
        List<RefAttach> refAttaches = RefAttaches.Refresh(CurPatNum);
        Referral refer;
        String str = new String();
        for (int i = 0;i < refAttaches.Count;i++)
        {
            refer = Referrals.GetReferral(refAttaches[i].ReferralNum);
            if (refAttaches[i].IsFrom)
            {
                str = Lan.g(this,"From ");
            }
            else
            {
                str = Lan.g(this,"To ");
            } 
            str += Referrals.getNameFL(refer.ReferralNum);
            menuItem = new MenuItem(str, menuLabel_Click);
            menuItem.Tag = refer;
            menuLabel.MenuItems.Add(menuItem);
        }
    }

    private void menuLabel_Click(Object sender, System.EventArgs e) throws Exception {
        if (((MenuItem)sender).Tag == null)
        {
            return ;
        }
         
        //LabelSingle label=new LabelSingle();
        if (((MenuItem)sender).Tag.GetType() == String.class)
        {
            if (StringSupport.equals(((MenuItem)sender).Tag.ToString(), "PatientLFAddress"))
            {
                LabelSingle.printPatientLFAddress(CurPatNum);
            }
             
            if (StringSupport.equals(((MenuItem)sender).Tag.ToString(), "PatientLFChartNumber"))
            {
                LabelSingle.printPatientLFChartNumber(CurPatNum);
            }
             
            if (StringSupport.equals(((MenuItem)sender).Tag.ToString(), "PatientLFPatNum"))
            {
                LabelSingle.printPatientLFPatNum(CurPatNum);
            }
             
            if (StringSupport.equals(((MenuItem)sender).Tag.ToString(), "PatRadiograph"))
            {
                LabelSingle.printPatRadiograph(CurPatNum);
            }
             
        }
        else if (((MenuItem)sender).Tag.GetType() == SheetDef.class)
        {
            LabelSingle.printCustomPatient(CurPatNum,(SheetDef)((MenuItem)sender).Tag);
        }
        else if (((MenuItem)sender).Tag.GetType() == Carrier.class)
        {
            Carrier carrier = (Carrier)((MenuItem)sender).Tag;
            LabelSingle.printCarrier(carrier.CarrierNum);
        }
        else if (((MenuItem)sender).Tag.GetType() == Referral.class)
        {
            Referral refer = (Referral)((MenuItem)sender).Tag;
            LabelSingle.printReferral(refer.ReferralNum);
        }
            
    }

    private void onPopups_Click() throws Exception {
        FormPopupsForFam FormPFF = new FormPopupsForFam();
        FormPFF.PatCur = Patients.getPat(CurPatNum);
        FormPFF.ShowDialog();
    }

    private void onTxtMsg_Click() throws Exception {
        FormTxtMsgEdit FormTME = new FormTxtMsgEdit();
        Patient pat = Patients.getPat(CurPatNum);
        FormTME.PatNum = CurPatNum;
        FormTME.WirelessPhone = pat.WirelessPhone;
        FormTME.TxtMsgOk = pat.TxtMsgOk;
        FormTME.ShowDialog();
        if (FormTME.DialogResult == DialogResult.OK)
        {
            refreshCurrentModule();
        }
         
    }

    private void formOpenDental_Resize(Object sender, EventArgs e) throws Exception {
        layoutControls();
        if (Plugins.getPluginsAreLoaded())
        {
            Plugins.hookAddCode(this,"FormOpenDental.FormOpenDental_Resize_end");
        }
         
    }

    /**
    * This used to be called much more frequently when it was an actual layout event.
    */
    private void layoutControls() throws Exception {
        //Debug.WriteLine("layout");
        if (this.WindowState == FormWindowState.Minimized)
        {
            return ;
        }
         
        if (Width < 200)
        {
            Width = 200;
        }
         
        Point position = new Point(myOutlookBar.Width, ToolBarMain.Height);
        int width = this.ClientSize.Width - position.X;
        int height = this.ClientSize.Height - position.Y;
        if (userControlTasks1.Visible)
        {
            if (menuItemDockBottom.Checked)
            {
                if (panelSplitter.Height > 8)
                {
                    //docking needs to be changed
                    panelSplitter.Height = 7;
                    panelSplitter.Location = new Point(position.X, 540);
                }
                 
                panelSplitter.Location = new Point(position.X, panelSplitter.Location.Y);
                panelSplitter.Width = width;
                panelSplitter.Visible = true;
                if (PrefC.getBool(PrefName.DockPhonePanelShow))
                {
                    timerPhoneWebCam.Enabled = true;
                    //the only place this happens
                    //phoneSmall.Visible=true;
                    //phoneSmall.Location=new Point(position.X,panelSplitter.Bottom+butBigPhones.Height);
                    phoneSmall.Location = new Point(0, comboTriageCoordinator.Bottom);
                    userControlTasks1.Location = new Point(position.X + phoneSmall.Width, panelSplitter.Bottom);
                    userControlTasks1.Width = width - phoneSmall.Width;
                    //butBigPhones.Visible=true;
                    //butBigPhones.Location=new Point(position.X+phoneSmall.Width-butBigPhones.Width,panelSplitter.Bottom);
                    //butBigPhones.BringToFront();
                    //labelMsg.Visible=true;
                    //labelMsg.Location=new Point(position.X+phoneSmall.Width-butBigPhones.Width-labelMsg.Width,panelSplitter.Bottom);
                    //labelMsg.BringToFront();
                    panelPhoneSmall.Visible = true;
                    panelPhoneSmall.Location = new Point(position.X, panelSplitter.Bottom);
                    panelPhoneSmall.BringToFront();
                }
                else
                {
                    //phoneSmall.Visible=false;
                    //phonePanel.Visible=false;
                    //butBigPhones.Visible=false;
                    //labelMsg.Visible=false;
                    panelPhoneSmall.Visible = false;
                    userControlTasks1.Location = new Point(position.X, panelSplitter.Bottom);
                    userControlTasks1.Width = width;
                } 
                userControlTasks1.Height = this.ClientSize.Height - userControlTasks1.Top;
                height = ClientSize.Height - panelSplitter.Height - userControlTasks1.Height - ToolBarMain.Height;
            }
            else
            {
                //docked Right
                //phoneSmall.Visible=false;
                //phonePanel.Visible=false;
                //butBigPhones.Visible=false;
                //labelMsg.Visible=false;
                panelPhoneSmall.Visible = false;
                if (panelSplitter.Width > 8)
                {
                    //docking needs to be changed
                    panelSplitter.Width = 7;
                    panelSplitter.Location = new Point(900, position.Y);
                }
                 
                panelSplitter.Location = new Point(panelSplitter.Location.X, position.Y);
                panelSplitter.Height = height;
                panelSplitter.Visible = true;
                userControlTasks1.Location = new Point(panelSplitter.Right, position.Y);
                userControlTasks1.Height = height;
                userControlTasks1.Width = this.ClientSize.Width - userControlTasks1.Left;
                width = ClientSize.Width - panelSplitter.Width - userControlTasks1.Width - position.X;
            } 
            panelSplitter.BringToFront();
            panelSplitter.Invalidate();
        }
        else
        {
            //phoneSmall.Visible=false;
            //phonePanel.Visible=false;
            //butBigPhones.Visible=false;
            //labelMsg.Visible=false;
            panelPhoneSmall.Visible = false;
            panelSplitter.Visible = false;
        } 
        ContrAccount2.Location = position;
        ContrAccount2.Width = width;
        ContrAccount2.Height = height;
        ContrAppt2.Location = position;
        ContrAppt2.Width = width;
        ContrAppt2.Height = height;
        ContrChart2.Location = position;
        ContrChart2.Width = width;
        ContrChart2.Height = height;
        ContrImages2.Location = position;
        ContrImages2.Width = width;
        ContrImages2.Height = height;
        ContrFamily2.Location = position;
        ContrFamily2.Width = width;
        ContrFamily2.Height = height;
        ContrFamily2Ecw.Location = position;
        ContrFamily2Ecw.Width = width;
        ContrFamily2Ecw.Height = height;
        ContrManage2.Location = position;
        ContrManage2.Width = width;
        ContrManage2.Height = height;
        ContrTreat2.Location = position;
        ContrTreat2.Width = width;
        ContrTreat2.Height = height;
    }

    private void panelSplitter_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = true;
        SplitterOriginalLocation = panelSplitter.Location;
        OriginalMousePos = new Point(panelSplitter.Left + e.X, panelSplitter.Top + e.Y);
    }

    private void panelSplitter_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnSplitter)
        {
            return ;
        }
         
        if (menuItemDockBottom.Checked)
        {
            int splitterNewY = SplitterOriginalLocation.Y + (panelSplitter.Top + e.Y) - OriginalMousePos.Y;
            if (splitterNewY < 300)
            {
                splitterNewY = 300;
            }
             
            //keeps it from going too high
            if (splitterNewY > ClientSize.Height - 50)
            {
                splitterNewY = ClientSize.Height - panelSplitter.Height - 50;
            }
             
            //keeps it from going off the bottom edge
            panelSplitter.Top = splitterNewY;
        }
        else
        {
            //docked right
            int splitterNewX = SplitterOriginalLocation.X + (panelSplitter.Left + e.X) - OriginalMousePos.X;
            if (splitterNewX < 300)
            {
                splitterNewX = 300;
            }
             
            //keeps it from going too far to the left
            if (splitterNewX > ClientSize.Width - 50)
            {
                splitterNewX = ClientSize.Width - panelSplitter.Width - 50;
            }
             
            //keeps it from going off the right edge
            panelSplitter.Left = splitterNewX;
        } 
        layoutControls();
    }

    private void panelSplitter_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = false;
        taskDockSavePos();
    }

    private void menuItemDockBottom_Click(Object sender, EventArgs e) throws Exception {
        menuItemDockBottom.Checked = true;
        menuItemDockRight.Checked = false;
        panelSplitter.Cursor = Cursors.HSplit;
        taskDockSavePos();
        layoutControls();
    }

    private void menuItemDockRight_Click(Object sender, EventArgs e) throws Exception {
        menuItemDockBottom.Checked = false;
        menuItemDockRight.Checked = true;
        //included now with layoutcontrols
        panelSplitter.Cursor = Cursors.VSplit;
        taskDockSavePos();
        layoutControls();
    }

    /**
    * Every time user changes doc position, it will save automatically.
    */
    private void taskDockSavePos() throws Exception {
        //ComputerPref computerPref = ComputerPrefs.GetForLocalComputer();
        if (menuItemDockBottom.Checked)
        {
            ComputerPrefs.getLocalComputer().TaskY = panelSplitter.Top;
            ComputerPrefs.getLocalComputer().TaskDock = 0;
        }
        else
        {
            ComputerPrefs.getLocalComputer().TaskX = panelSplitter.Left;
            ComputerPrefs.getLocalComputer().TaskDock = 1;
        } 
        ComputerPrefs.update(ComputerPrefs.getLocalComputer());
    }

    /**
    * This is called when any local data becomes outdated.  It's purpose is to tell the other computers to update certain local data.
    */
    private void dataValid_BecameInvalid(OpenDental.ValidEventArgs e) throws Exception {
        if (e.getOnlyLocal())
        {
            //This is deprecated and doesn't seem to be used at all anymore.
            if (!prefsStartup())
            {
                return ;
            }
             
            //??
            refreshLocalData(InvalidType.AllLocal);
            return ;
        }
         
        //does local computer only
        //local refresh for dates is handled within ContrAppt, not here
        //Tasks are not "cached" data.
        if (!e.getITypes().Contains(((Enum)InvalidType.Date).ordinal()) && !e.getITypes().Contains(((Enum)InvalidType.Task).ordinal()) && !e.getITypes().Contains(((Enum)InvalidType.TaskPopup).ordinal()))
        {
            InvalidType[] itypeArray = new InvalidType[e.getITypes().Count];
            for (int i = 0;i < itypeArray.Length;i++)
            {
                itypeArray[i] = (InvalidType)e.getITypes()[i];
            }
            RefreshLocalData(itypeArray);
        }
         
        //does local computer
        if (e.getITypes().Contains(((Enum)InvalidType.Task).ordinal()) || e.getITypes().Contains(((Enum)InvalidType.TaskPopup).ordinal()))
        {
            //One of the two task lists needs to be refreshed on this instance of OD
            if (userControlTasks1.Visible)
            {
                userControlTasks1.refreshTasks();
            }
             
            //See if FormTasks is currently open.
            if (ContrManage2 != null && ContrManage2.FormT != null && !ContrManage2.FormT.IsDisposed)
            {
                ContrManage2.FormT.refreshUserControlTasks();
            }
             
        }
         
        String itypeString = "";
        for (int i = 0;i < e.getITypes().Count;i++)
        {
            if (i > 0)
            {
                itypeString += ",";
            }
             
            itypeString += e.getITypes()[i].ToString();
        }
        Signalod sig = new Signalod();
        sig.ITypes = itypeString;
        if (e.getITypes().Contains(((Enum)InvalidType.Date).ordinal()))
        {
            sig.DateViewing = e.getDateViewing();
        }
        else
        {
            sig.DateViewing = DateTime.MinValue;
        } 
        sig.SigType = SignalType.Invalid;
        if (e.getITypes().Contains(((Enum)InvalidType.Task).ordinal()) || e.getITypes().Contains(((Enum)InvalidType.TaskPopup).ordinal()))
        {
            sig.TaskNum = e.getTaskNum();
        }
         
        Signalods.insert(sig);
    }

    private void fillTriageLabels() throws Exception {
        DataTable phoneMetrics = Phones.getTriageMetrics();
        int countTasksWithoutNotes = PIn.Int(phoneMetrics.Rows[0]["CountTasksWithoutNotes"].ToString());
        int countTasksWithNotes = PIn.Int(phoneMetrics.Rows[0]["CountTasksWithNotes"].ToString());
        int countUrgentTasks = PIn.Int(phoneMetrics.Rows[0]["CountUrgentTasks"].ToString());
        DateTime timeOfOldestTaskWithoutNotes = PIn.Date(phoneMetrics.Rows[0]["TimeOfOldestTaskWithoutNotes"].ToString());
        DateTime timeOfOldestUrgentTaskNote = PIn.Date(phoneMetrics.Rows[0]["TimeOfOldestUrgentTaskNote"].ToString());
        TimeSpan triageBehind = new TimeSpan(0);
        if (timeOfOldestTaskWithoutNotes.Year > 1880)
        {
            triageBehind = DateTime.Now - timeOfOldestTaskWithoutNotes;
        }
         
        String countStr = "0";
        if (countTasksWithoutNotes > 0)
        {
            //Triage show red so users notice more.
            countStr = countTasksWithoutNotes.ToString();
            labelTriage.ForeColor = Color.Firebrick;
        }
        else
        {
            if (countTasksWithNotes > 0)
            {
                countStr = "(" + countTasksWithNotes.ToString() + ")";
            }
             
            labelTriage.ForeColor = Color.Black;
        } 
        labelTriage.Text = "T:" + countStr;
        labelWaitTime.Text = ((int)triageBehind.TotalMinutes).ToString() + "m";
        if (formMapHQ != null && !formMapHQ.IsDisposed)
        {
            formMapHQ.setTriageNormal(countTasksWithNotes,countTasksWithoutNotes,triageBehind);
            TimeSpan urgentTriageBehind = new TimeSpan(0);
            if (timeOfOldestUrgentTaskNote.Year > 1880)
            {
                urgentTriageBehind = DateTime.Now - timeOfOldestUrgentTaskNote;
            }
             
            formMapHQ.setTriageUrgent(countUrgentTasks,urgentTriageBehind);
        }
         
    }

    private void gotoModule_ModuleSelected(ModuleEventArgs e) throws Exception {
        if (e.getDateSelected().Year > 1880)
        {
            AppointmentL.DateSelected = e.getDateSelected();
        }
         
        if (e.getSelectedAptNum() > 0)
        {
            ContrApptSingle.SelectedAptNum = e.getSelectedAptNum();
        }
         
        //patient can also be set separately ahead of time instead of doing it this way:
        if (e.getPatNum() != 0)
        {
            CurPatNum = e.getPatNum();
            Patient pat = Patients.getPat(CurPatNum);
            fillPatientButton(pat);
        }
         
        unselectActive();
        allNeutral();
        if (e.getClaimNum() > 0)
        {
            myOutlookBar.SelectedIndex = e.getIModule();
            ContrAccount2.Visible = true;
            this.ActiveControl = this.ContrAccount2;
            ContrAccount2.moduleSelected(CurPatNum,e.getClaimNum());
        }
        else if (e.getPinAppts().Count != 0)
        {
            myOutlookBar.SelectedIndex = e.getIModule();
            ContrAppt2.Visible = true;
            this.ActiveControl = this.ContrAppt2;
            ContrAppt2.moduleSelected(CurPatNum,e.getPinAppts());
        }
        else if (e.getDocNum() > 0)
        {
            myOutlookBar.SelectedIndex = e.getIModule();
            ContrImages2.Visible = true;
            this.ActiveControl = this.ContrImages2;
            ContrImages2.moduleSelected(CurPatNum,e.getDocNum());
        }
        else if (e.getIModule() != -1)
        {
            myOutlookBar.SelectedIndex = e.getIModule();
            setModuleSelected();
        }
            
        myOutlookBar.Invalidate();
    }

    /**
    * If this is initial call when opening program, then set sigListButs=null.  This will cause a call to be made to database to get current status of buttons.  Otherwise, it adds the signals passed in to the current state, then paints.
    */
    private void fillSignalButtons(List<Signalod> sigListButs) throws Exception {
        if (!lightSignalGrid1.Visible)
        {
            return ;
        }
         
        //for faster eCW loading
        if (sigListButs == null)
        {
            SigButDefList = SigButDefs.GetByComputer(SystemInformation.ComputerName);
            lightSignalGrid1.setButtons(SigButDefList);
            sigListButs = Signalods.refreshCurrentButState();
        }
         
        SigElementDef element;
        SigButDef butDef;
        int row = new int();
        Color color = new Color();
        for (int i = 0;i < sigListButs.Count;i++)
        {
            if (sigListButs[i].AckTime.Year > 1880)
            {
                //process ack
                int rowAck = lightSignalGrid1.ProcessAck(sigListButs[i].SignalNum);
                if (rowAck != -1)
                {
                    butDef = SigButDefs.getByIndex(rowAck,SigButDefList);
                    if (butDef != null)
                    {
                        PaintOnIcon(butDef.SynchIcon, Color.White);
                    }
                     
                }
                 
            }
            else
            {
                //process normal message
                row = 0;
                color = Color.White;
                for (int e = 0;e < sigListButs[i].ElementList.Length;e++)
                {
                    element = SigElementDefs.GetElement(sigListButs[i].ElementList[e].SigElementDefNum);
                    if (element.LightRow != 0)
                    {
                        row = element.LightRow;
                    }
                     
                    if (element.LightColor.ToArgb() != Color.White.ToArgb())
                    {
                        color = element.LightColor;
                    }
                     
                }
                if (row != 0 && color != Color.White)
                {
                    lightSignalGrid1.SetButtonActive(row - 1, color, sigListButs[i]);
                    butDef = SigButDefs.getByIndex(row - 1,SigButDefList);
                    if (butDef != null)
                    {
                        try
                        {
                            PaintOnIcon(butDef.SynchIcon, color);
                        }
                        catch (Exception __dummyCatchVar4)
                        {
                            MessageBox.Show("Error painting on program icon.  Probably too many non-ack'd messages.");
                        }
                    
                    }
                     
                }
                 
            } 
        }
    }

    /**
    * Pass in the cellNum as 1-based.
    */
    private void paintOnIcon(int cellNum, Color color) throws Exception {
        Graphics g = new Graphics();
        if (bitmapIcon == null)
        {
            bitmapIcon = new Bitmap(16, 16);
            g = Graphics.FromImage(bitmapIcon);
            g.FillRectangle(new SolidBrush(Color.White), 0, 0, 15, 15);
            //horizontal
            g.DrawLine(Pens.Black, 0, 0, 15, 0);
            g.DrawLine(Pens.Black, 0, 5, 15, 5);
            g.DrawLine(Pens.Black, 0, 10, 15, 10);
            g.DrawLine(Pens.Black, 0, 15, 15, 15);
            //vertical
            g.DrawLine(Pens.Black, 0, 0, 0, 15);
            g.DrawLine(Pens.Black, 5, 0, 5, 15);
            g.DrawLine(Pens.Black, 10, 0, 10, 15);
            g.DrawLine(Pens.Black, 15, 0, 15, 15);
            g.Dispose();
        }
         
        if (cellNum == 0)
        {
            return ;
        }
         
        g = Graphics.FromImage(bitmapIcon);
        int x = 0;
        int y = 0;
        switch(cellNum)
        {
            case 1: 
                x = 1;
                y = 1;
                break;
            case 2: 
                x = 6;
                y = 1;
                break;
            case 3: 
                x = 11;
                y = 1;
                break;
            case 4: 
                x = 1;
                y = 6;
                break;
            case 5: 
                x = 6;
                y = 6;
                break;
            case 6: 
                x = 11;
                y = 6;
                break;
            case 7: 
                x = 1;
                y = 11;
                break;
            case 8: 
                x = 6;
                y = 11;
                break;
            case 9: 
                x = 11;
                y = 11;
                break;
        
        }
        g.FillRectangle(new SolidBrush(color), x, y, 4, 4);
        Icon = Icon.FromHandle(bitmapIcon.GetHicon());
        g.Dispose();
    }

    private void lightSignalGrid1_ButtonClick(Object sender, OpenDental.UI.ODLightSignalGridClickEventArgs e) throws Exception {
        if (e.getActiveSignal() != null)
        {
            //user trying to ack an existing light signal
            Signalods.ackButton(e.getButtonIndex() + 1,signalLastRefreshed);
            //then, manually ack the light on this computer.  The second ack in a few seconds will be ignored.
            lightSignalGrid1.SetButtonActive(e.getButtonIndex(), Color.White, null);
            SigButDef butDef = SigButDefs.getByIndex(e.getButtonIndex(),SigButDefList);
            if (butDef != null)
            {
                PaintOnIcon(butDef.SynchIcon, Color.White);
            }
             
            return ;
        }
         
        if (e.getButtonDef() == null || e.getButtonDef().ElementList.Length == 0)
        {
            return ;
        }
         
        //there is no signal to send
        //user trying to send a signal
        Signalod sig = new Signalod();
        sig.SigType = SignalType.Button;
        //sig.ToUser=sigElementDefUser[listTo.SelectedIndex].SigText;
        //sig.FromUser=sigElementDefUser[listFrom.SelectedIndex].SigText;
        //need to do this all as a transaction?
        Signalods.insert(sig);
        int row = 0;
        Color color = Color.White;
        SigElementDef def;
        SigElement element;
        SigButDefElement[] butElements = SigButDefElements.getForButton(e.getButtonDef().SigButDefNum);
        for (int i = 0;i < butElements.Length;i++)
        {
            element = new SigElement();
            element.SigElementDefNum = butElements[i].SigElementDefNum;
            element.SignalNum = sig.SignalNum;
            SigElements.insert(element);
            if (SigElementDefs.getElement(element.SigElementDefNum).SigElementType == SignalElementType.User)
            {
                sig.ToUser = SigElementDefs.getElement(element.SigElementDefNum).SigText;
                Signalods.update(sig);
            }
             
            def = SigElementDefs.getElement(element.SigElementDefNum);
            if (def.LightRow != 0)
            {
                row = def.LightRow;
            }
             
            if (def.LightColor.ToArgb() != Color.White.ToArgb())
            {
                color = def.LightColor;
            }
             
        }
        sig.ElementList = new SigElement[0];
        //we don't really care about these
        if (row != 0 && color != Color.White)
        {
            lightSignalGrid1.setButtonActive(row - 1,color,sig);
        }
         
    }

    //this just makes it seem more responsive.
    //we can skip painting on the icon
    /**
    * Called every time timerSignals_Tick fires.  Usually about every 5-10 seconds.
    */
    public void processSignals() throws Exception {
        if (Security.getCurUser() == null)
        {
            return ;
        }
         
        try
        {
            //User must be at the log in screen, so no need to process signals.  We will need to look for shutdown signals since the last refreshed time when the user attempts to log in.
            List<Signalod> sigList = Signalods.refreshTimed(signalLastRefreshed);
            //this also attaches all elements to their sigs
            if (PrefC.getBool(PrefName.DockPhonePanelShow))
            {
                fillTriageLabels();
            }
             
            if (sigList.Count == 0)
            {
                return ;
            }
             
            for (int i = 0;i < sigList.Count;i++)
            {
                //look for shutdown signal
                if (sigList[i].ITypes == (((Enum)InvalidType.ShutDownNow).ordinal()).ToString())
                {
                    timerSignals.Enabled = false;
                    //quit receiving signals.
                    //close the webcam if present so that it can be updated too.
                    if (PrefC.getBool(PrefName.DockPhonePanelShow))
                    {
                        Process[] processes = Process.GetProcessesByName("WebCamOD");
                        for (int p = 0;p < processes.Length;p++)
                        {
                            processes[p].Kill();
                        }
                    }
                     
                    //start the thread that will kill the application
                    Thread killThread = new Thread(new ThreadStart(KillThread));
                    killThread.Start();
                    String msg = "";
                    if (StringSupport.equals(Process.GetCurrentProcess().ProcessName, "OpenDental"))
                    {
                        msg += "All copies of Open Dental ";
                    }
                    else
                    {
                        msg += Process.GetCurrentProcess().ProcessName + " ";
                    } 
                    msg += Lan.g(this,"will shut down in 15 seconds.  Quickly click OK on any open windows with unsaved data.");
                    CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(msg);
                    msgbox.Size = new Size(300, 300);
                    msgbox.TopMost = true;
                    msgbox.ShowDialog();
                    return ;
                }
                 
            }
            if (sigList[sigList.Count - 1].AckTime.Year > 1880)
            {
                signalLastRefreshed = sigList[sigList.Count - 1].AckTime;
            }
            else
            {
                signalLastRefreshed = sigList[sigList.Count - 1].SigDateTime;
            } 
            if (ContrAppt2.Visible && Signalods.ApptNeedsRefresh(sigList, AppointmentL.DateSelected.Date))
            {
                ContrAppt2.refreshPeriod();
            }
             
            boolean areAnySignalsTasks = false;
            for (int i = 0;i < sigList.Count;i++)
            {
                if (sigList[i].ITypes == (((Enum)InvalidType.Task).ordinal()).ToString() || sigList[i].ITypes == (((Enum)InvalidType.TaskPopup).ordinal()).ToString())
                {
                    areAnySignalsTasks = true;
                }
                 
            }
            List<Task> tasksPopup = Signalods.GetNewTaskPopupsThisUser(sigList, Security.getCurUser().UserNum);
            if (tasksPopup.Count > 0)
            {
                for (int i = 0;i < tasksPopup.Count;i++)
                {
                    //Even though this is triggered to popup, if this is my own task, then do not popup.
                    List<TaskNote> notesForThisTask = TaskNotes.GetForTask(tasksPopup[i].TaskNum);
                    if (notesForThisTask.Count == 0)
                    {
                        //'sender' is the usernum on the task
                        if (tasksPopup[i].UserNum == Security.getCurUser().UserNum)
                        {
                            continue;
                        }
                         
                    }
                    else
                    {
                        //'sender' is the user on the last added note
                        if (notesForThisTask[notesForThisTask.Count - 1].UserNum == Security.getCurUser().UserNum)
                        {
                            continue;
                        }
                         
                    } 
                    //if not my inbox
                    if (tasksPopup[i].TaskListNum != Security.getCurUser().TaskListInBox && Security.getCurUser().DefaultHidePopups)
                    {
                        continue;
                    }
                     
                    //and popups blocked
                    //no sound or popup
                    //in other words, popups will always show for my inbox even if popups blocked.
                    System.Media.SoundPlayer soundplay = new SoundPlayer(Resources.getnotify());
                    soundplay.Play();
                    this.BringToFront();
                    //don't know if this is doing anything.
                    FormTaskEdit FormT = new FormTaskEdit(tasksPopup[i], tasksPopup[i].Copy());
                    FormT.IsPopup = true;
                    FormT.Closing += new CancelEventHandler(TaskGoToEvent);
                    FormT.Show();
                }
            }
             
            //non-modal
            if (areAnySignalsTasks || tasksPopup.Count > 0)
            {
                if (userControlTasks1.Visible)
                {
                    userControlTasks1.refreshTasks();
                }
                 
                //See if FormTasks is currently open.
                if (ContrManage2 != null && ContrManage2.FormT != null && !ContrManage2.FormT.IsDisposed)
                {
                    ContrManage2.FormT.refreshUserControlTasks();
                }
                 
            }
             
            List<int> itypes = Signalods.GetInvalidTypes(sigList);
            InvalidType[] itypeArray = new InvalidType[itypes.Count];
            for (int i = 0;i < itypeArray.Length;i++)
            {
                itypeArray[i] = (InvalidType)itypes[i];
            }
            //InvalidTypes invalidTypes=Signalods.GetInvalidTypes(sigList);
            if (itypes.Count > 0)
            {
                //invalidTypes!=0){
                RefreshLocalData(itypeArray);
            }
             
            List<Signalod> sigListButs = Signalods.GetButtonSigs(sigList);
            ContrManage2.LogMsgs(sigListButs);
            FillSignalButtons(sigListButs);
            //Need to add a test to this: do not play messages that are over 2 minutes old.
            Thread newThread = new Thread(new ParameterizedThreadStart(PlaySounds));
            newThread.Start(sigListButs);
        }
        catch (Exception __dummyCatchVar5)
        {
            signalLastRefreshed = DateTime.Now;
        }
    
    }

    public void taskGoToEvent(Object sender, CancelEventArgs e) throws Exception {
        FormTaskEdit FormT = (FormTaskEdit)sender;
        taskGoTo(FormT.GotoType,FormT.GotoKeyNum);
    }

    /**
    * Gives users 15 seconds to finish what they were doing before the program shuts down.
    */
    private void killThread() throws Exception {
        //Application.DoEvents();
        DateTime now = DateTime.Now;
        while (DateTime.Now < now.AddSeconds(15))
        {
            Application.DoEvents();
        }
        //Thread.Sleep(30000);//30 sec
        Application.Exit();
    }

    private void playSounds(Object objSignalList) throws Exception {
        List<Signalod> signalList = (List<Signalod>)objSignalList;
        String strSound = new String();
        byte[] rawData = new byte[]();
        MemoryStream stream = null;
        SoundPlayer simpleSound = null;
        try
        {
            for (int s = 0;s < signalList.Count;s++)
            {
                //loop through each signal
                if (signalList[s].AckTime.Year > 1880)
                {
                    continue;
                }
                 
                //don't play any sounds for acks.
                if (s > 0)
                {
                    Thread.Sleep(1000);
                }
                 
                for (int e = 0;e < signalList[s].ElementList.Length;e++)
                {
                    //pause 1 second between signals.
                    //play all the sounds.
                    strSound = SigElementDefs.GetElement(signalList[s].ElementList[e].SigElementDefNum).Sound;
                    if (StringSupport.equals(strSound, ""))
                    {
                        continue;
                    }
                     
                    try
                    {
                        rawData = Convert.FromBase64String(strSound);
                        stream = new MemoryStream(rawData);
                        simpleSound = new SoundPlayer(stream);
                        simpleSound.PlaySync();
                    }
                    catch (Exception __dummyCatchVar6)
                    {
                    }
                
                }
            }
        }
        finally
        {
            //sound will finish playing before thread continues
            //do nothing
            if (stream != null)
            {
                stream.Dispose();
            }
             
            if (simpleSound != null)
            {
                simpleSound.Dispose();
            }
             
        }
    }

    private void myOutlookBar_ButtonClicked(Object sender, OpenDental.ButtonClicked_EventArgs e) throws Exception {
        switch(myOutlookBar.SelectedIndex)
        {
            case 0: 
                if (!Security.isAuthorized(Permissions.AppointmentsModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                break;
            case 1: 
                if (PrefC.getBool(PrefName.EhrEmergencyNow))
                {
                    //if red emergency button is on
                    if (Security.isAuthorized(Permissions.EhrEmergencyAccess,true))
                    {
                        break;
                    }
                     
                }
                 
                //No need to check other permissions.
                //Whether or not they were authorized by the special situation above,
                //they can get into the Family module with the ordinary permissions.
                if (!Security.isAuthorized(Permissions.FamilyModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                SecurityLogs.makeLogEntry(Permissions.FamilyModule,CurPatNum,"");
                break;
            case 2: 
                if (!Security.isAuthorized(Permissions.AccountModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                SecurityLogs.makeLogEntry(Permissions.AccountModule,CurPatNum,"");
                break;
            case 3: 
                if (!Security.isAuthorized(Permissions.TPModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                SecurityLogs.makeLogEntry(Permissions.TPModule,CurPatNum,"");
                break;
            case 4: 
                if (!Security.isAuthorized(Permissions.ChartModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                SecurityLogs.makeLogEntry(Permissions.ChartModule,CurPatNum,"");
                break;
            case 5: 
                if (!Security.isAuthorized(Permissions.ImagesModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                SecurityLogs.makeLogEntry(Permissions.ImagesModule,CurPatNum,"");
                break;
            case 6: 
                if (!Security.isAuthorized(Permissions.ManageModule))
                {
                    e.setCancel(true);
                    return ;
                }
                 
                break;
        
        }
        unselectActive();
        allNeutral();
        setModuleSelected(true);
    }

    /**
    * Sets the currently selected module based on the selectedIndex of the outlook bar. If selectedIndex is -1, which might happen if user does not have permission to any module, then this does nothing.
    */
    private void setModuleSelected() throws Exception {
        setModuleSelected(false);
    }

    /**
    * Sets the currently selected module based on the selectedIndex of the outlook bar. If selectedIndex is -1, which might happen if user does not have permission to any module, then this does nothing. The menuBarClicked variable should be set to true when a module button is clicked, and should be false when called for refresh purposes.
    */
    private void setModuleSelected(boolean menuBarClicked) throws Exception {
        switch(myOutlookBar.SelectedIndex)
        {
            case 0: 
                ContrAppt2.initializeOnStartup();
                ContrAppt2.Visible = true;
                this.ActiveControl = this.ContrAppt2;
                ContrAppt2.moduleSelected(CurPatNum);
                break;
            case 1: 
                if (HL7Defs.isExistingHL7Enabled())
                {
                    HL7Def def = HL7Defs.getOneDeepEnabled();
                    if (def.ShowDemographics == HL7ShowDemographics.Hide)
                    {
                        ContrFamily2Ecw.Visible = true;
                        this.ActiveControl = this.ContrFamily2Ecw;
                        ContrFamily2Ecw.moduleSelected(CurPatNum);
                    }
                    else
                    {
                        ContrFamily2.initializeOnStartup();
                        ContrFamily2.Visible = true;
                        this.ActiveControl = this.ContrFamily2;
                        ContrFamily2.moduleSelected(CurPatNum);
                    } 
                }
                else
                {
                    if (Programs.usingEcwTightMode())
                    {
                        ContrFamily2Ecw.Visible = true;
                        this.ActiveControl = this.ContrFamily2Ecw;
                        ContrFamily2Ecw.moduleSelected(CurPatNum);
                    }
                    else
                    {
                        ContrFamily2.initializeOnStartup();
                        ContrFamily2.Visible = true;
                        this.ActiveControl = this.ContrFamily2;
                        ContrFamily2.moduleSelected(CurPatNum);
                    } 
                } 
                break;
            case 2: 
                ContrAccount2.initializeOnStartup();
                ContrAccount2.Visible = true;
                this.ActiveControl = this.ContrAccount2;
                ContrAccount2.moduleSelected(CurPatNum);
                break;
            case 3: 
                ContrTreat2.initializeOnStartup();
                ContrTreat2.Visible = true;
                this.ActiveControl = this.ContrTreat2;
                ContrTreat2.moduleSelected(CurPatNum);
                break;
            case 4: 
                ContrChart2.initializeOnStartup();
                ContrChart2.Visible = true;
                this.ActiveControl = this.ContrChart2;
                if (menuBarClicked)
                {
                    ContrChart2.moduleSelectedNewCrop(CurPatNum);
                }
                else
                {
                    //Special refresh that also queries the NewCrop web service for prescription information.
                    ContrChart2.moduleSelected(CurPatNum);
                } 
                break;
            case 5: 
                ContrImages2.initializeOnStartup();
                ContrImages2.Visible = true;
                this.ActiveControl = this.ContrImages2;
                ContrImages2.moduleSelected(CurPatNum);
                break;
            case 6: 
                //ContrManage2.InitializeOnStartup();//This gets done earlier.
                ContrManage2.Visible = true;
                this.ActiveControl = this.ContrManage2;
                ContrManage2.moduleSelected(CurPatNum);
                break;
        
        }
    }

    private void allNeutral() throws Exception {
        ContrAppt2.Visible = false;
        ContrFamily2.Visible = false;
        ContrFamily2Ecw.Visible = false;
        ContrAccount2.Visible = false;
        ContrTreat2.Visible = false;
        ContrChart2.Visible = false;
        ContrImages2.Visible = false;
        ContrManage2.Visible = false;
    }

    private void unselectActive() throws Exception {
        if (ContrAppt2.Visible)
        {
            ContrAppt2.moduleUnselected();
        }
         
        if (ContrFamily2.Visible)
        {
            ContrFamily2.moduleUnselected();
        }
         
        if (ContrFamily2Ecw.Visible)
        {
        }
         
        //ContrFamily2Ecw.ModuleUnselected();
        if (ContrAccount2.Visible)
        {
            ContrAccount2.moduleUnselected();
        }
         
        if (ContrTreat2.Visible)
        {
            ContrTreat2.moduleUnselected();
        }
         
        if (ContrChart2.Visible)
        {
            ContrChart2.moduleUnselected();
        }
         
        if (ContrImages2.Visible)
        {
            ContrImages2.moduleUnselected();
        }
         
    }

    /**
    * This also passes CurPatNum down to the currently selected module (except the Manage module).
    */
    private void refreshCurrentModule() throws Exception {
        if (ContrAppt2.Visible)
        {
            ContrAppt2.moduleSelected(CurPatNum);
        }
         
        if (ContrFamily2.Visible)
        {
            ContrFamily2.moduleSelected(CurPatNum);
        }
         
        if (ContrFamily2Ecw.Visible)
        {
            ContrFamily2Ecw.moduleSelected(CurPatNum);
        }
         
        if (ContrAccount2.Visible)
        {
            ContrAccount2.moduleSelected(CurPatNum);
        }
         
        if (ContrTreat2.Visible)
        {
            ContrTreat2.moduleSelected(CurPatNum);
        }
         
        if (ContrChart2.Visible)
        {
            ContrChart2.moduleSelected(CurPatNum);
        }
         
        if (ContrImages2.Visible)
        {
            ContrImages2.moduleSelected(CurPatNum);
        }
         
        if (ContrManage2.Visible)
        {
            ContrManage2.moduleSelected(CurPatNum);
        }
         
    }

    /**
    * sends function key presses to the appointment module and chart module
    */
    private void formOpenDental_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (ContrAppt2.Visible && e.KeyCode >= Keys.F1 && e.KeyCode <= Keys.F12)
        {
            ContrAppt2.FunctionKeyPress(e.KeyCode);
        }
         
        if (ContrChart2.Visible && e.KeyCode >= Keys.F1 && e.KeyCode <= Keys.F12)
        {
            ContrChart2.FunctionKeyPressContrChart(e.KeyCode);
        }
         
        Keys keys = e.KeyCode;
        //Ctrl-Alt-R is supposed to show referral window, but it doesn't work on some computers.
        if ((e.Modifiers & Keys.Alt) == Keys.Alt && (e.Modifiers & Keys.Control) == Keys.Control && (e.KeyCode & Keys.R) == Keys.R && CurPatNum != 0)
        {
            FormReferralsPatient FormRE = new FormReferralsPatient();
            FormRE.PatNum = CurPatNum;
            FormRE.ShowDialog();
        }
         
        //so we're also going to use Ctrl-X to show the referral window.
        if ((e.Modifiers & Keys.Control) == Keys.Control && (e.KeyCode & Keys.X) == Keys.X && CurPatNum != 0)
        {
            FormReferralsPatient FormRE = new FormReferralsPatient();
            FormRE.PatNum = CurPatNum;
            FormRE.ShowDialog();
        }
         
    }

    private void timerTimeIndic_Tick(Object sender, System.EventArgs e) throws Exception {
        //every minute:
        if (WindowState != FormWindowState.Minimized && ContrAppt2.Visible)
        {
            ContrAppt2.tickRefresh();
        }
         
    }

    private void timerSignals_Tick(Object sender, System.EventArgs e) throws Exception {
        //typically every 4 seconds:
        processSignals();
    }

    private void timerDisabledKey_Tick(Object sender, EventArgs e) throws Exception {
        //every 10 minutes:
        if (PrefC.getBoolSilent(PrefName.RegistrationKeyIsDisabled,false))
        {
            MessageBox.Show("Registration key has been disabled.  You are using an unauthorized version of this program.", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }
         
    }

    private void timerHeartBeat_Tick(Object sender, EventArgs e) throws Exception {
        try
        {
            //every 3 minutes:
            Computers.UpdateHeartBeat(Environment.MachineName, false);
        }
        catch (Exception __dummyCatchVar7)
        {
        }
    
    }

    private void threadEmailInbox_Receive() throws Exception {
        //The EmailInboxCheckInterval preference defines the number of minutes between automatic inbox receiving.
        //Do not perform the first email inbox receive within the first EmailInboxCheckInterval minutes of program startup (thread startup).
        _isEmailThreadRunning = true;
        while (_isEmailThreadRunning)
        {
            _emailSleep.WaitOne(TimeSpan.FromMinutes(PrefC.getInt(PrefName.EmailInboxCheckInterval)));
            if (!_isEmailThreadRunning)
            {
                break;
            }
             
            EmailAddress Address = EmailAddresses.GetByClinic(0);
            //Default for clinic/practice.
            if (StringSupport.equals(Address.Pop3ServerIncoming, ""))
            {
                continue;
            }
             
            //Email address not setup.
            if (!CodeBase.ODEnvironment.idIsThisComputer(PrefC.getString(PrefName.EmailInboxComputerName)))
            {
                continue;
            }
             
            try
            {
                //If the email inbox computer name is not setup, or if the name does not match this computer, then do not get email from this computer.
                EmailMessages.receiveFromInbox(0,Address);
            }
            catch (Exception ex)
            {
            }
        
        }
    }

    //Do not tell the user, because it would be annoying to see an error every 30 seconds (if the server was down for instance).
    //Maybe we could log to the system EventViewer Application log someday if users complain. Keep in mind that the user can always manually go to Manage | Email Inbox to see the error message.
    private void threadTimeSynch_Synch() throws Exception {
        _isTimeSynchThreadRunning = true;
        while (_isTimeSynchThreadRunning)
        {
            if (!_isTimeSynchThreadRunning)
            {
                break;
            }
             
            NTPv4 ntp = new NTPv4();
            double nistOffset = double.MaxValue;
            try
            {
                nistOffset = ntp.getTime(PrefC.getString(PrefName.NistTimeServerUrl));
            }
            catch (Exception __dummyCatchVar8)
            {
            }

            //Invalid NIST Server URL
            if (nistOffset != double.MaxValue)
            {
                try
                {
                    //Did not timeout, or have invalid NIST server URL
                    WindowsTime.SetTime(DateTime.Now.AddMilliseconds(nistOffset));
                }
                catch (Exception __dummyCatchVar9)
                {
                }
            
            }
             
            //Sets local machine time
            //Error setting local machine time
            _timeSynchSleep.WaitOne(TimeSpan.FromMinutes(240));
        }
    }

    /**
    * Gets the encrypted connection string for the Oracle database from a config file.
    */
    public boolean getOraConfig() throws Exception {
        if (!File.Exists("ODOraConfig.xml"))
        {
            return false;
        }
         
        try
        {
            XmlDocument document = new XmlDocument();
            document.Load("ODOraConfig.xml");
            XPathNavigator Navigator = document.CreateNavigator();
            XPathNavigator nav = new XPathNavigator();
            nav = Navigator.SelectSingleNode("//DatabaseConnection");
            if (nav != null)
            {
                connStr = nav.SelectSingleNode("ConnectionString").Value;
                key = nav.SelectSingleNode("Key").Value;
            }
             
            OpenDentBusiness.DataConnection.DBtype = DatabaseType.Oracle;
            return true;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }
    
    }

    /**
    * Decrypt the connection string and try to connect to the database directly. Only called if using a connection string and ChooseDatabase is not to be shown. Must call GetOraConfig first.
    */
    public boolean tryWithConnStr() throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        try
        {
            if (connStr != null)
            {
            }
             
            //a direct connection does not utilize lower privileges.
            RemotingClient.RemotingRole = RemotingRole.ClientDirect;
            return true;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }
    
    }

    private void userControlTasks1_GoToChanged(Object sender, EventArgs e) throws Exception {
        taskGoTo(userControlTasks1.GotoType,userControlTasks1.GotoKeyNum);
    }

    private void taskGoTo(TaskObjectType taskOT, long keyNum) throws Exception {
        if (taskOT == TaskObjectType.None)
        {
            return ;
        }
         
        if (taskOT == TaskObjectType.Patient)
        {
            if (keyNum != 0)
            {
                CurPatNum = keyNum;
                Patient pat = Patients.getPat(CurPatNum);
                refreshCurrentModule();
                fillPatientButton(pat);
            }
             
        }
         
        if (taskOT == TaskObjectType.Appointment)
        {
            if (keyNum != 0)
            {
                Appointment apt = Appointments.getOneApt(keyNum);
                if (apt == null)
                {
                    MsgBox.show(this,"Appointment has been deleted, so it's not available.");
                    return ;
                }
                 
                DateTime dateSelected = DateTime.MinValue;
                if (apt.AptStatus == ApptStatus.Planned || apt.AptStatus == ApptStatus.UnschedList)
                {
                    //I did not add feature to put planned or unsched apt on pinboard.
                    MsgBox.show(this,"Cannot navigate to appointment.  Use the Other Appointments button.");
                }
                else
                {
                    //return;
                    dateSelected = apt.AptDateTime;
                } 
                CurPatNum = apt.PatNum;
                //OnPatientSelected(apt.PatNum);
                GotoModule.gotoAppointment(dateSelected,apt.AptNum);
            }
             
        }
         
    }

    private void butBigPhones_Click(Object sender, EventArgs e) throws Exception {
        if (formPhoneTiles == null || formPhoneTiles.IsDisposed)
        {
            formPhoneTiles = new FormPhoneTiles();
            formPhoneTiles.GoToChanged += new System.EventHandler(this.phonePanel_GoToChanged);
            formPhoneTiles.Show();
            Rectangle rect = System.Windows.Forms.Screen.FromControl(this).Bounds;
            formPhoneTiles.Location = new Point((rect.Width - formPhoneTiles.Width) / 2 + rect.X, 10);
            formPhoneTiles.BringToFront();
        }
        else
        {
            formPhoneTiles.Show();
            formPhoneTiles.BringToFront();
        } 
    }

    private void butMapPhones_Click(Object sender, EventArgs e) throws Exception {
        if (formMapHQ == null || formMapHQ.IsDisposed)
        {
            formMapHQ = new FormMapHQ();
            formMapHQ.Show();
            formMapHQ.BringToFront();
        }
        else
        {
            formMapHQ.Show();
            formMapHQ.BringToFront();
        } 
    }

    private void butTriage_Click(Object sender, EventArgs e) throws Exception {
        ContrManage2.jumpToTriageTaskWindow();
    }

    private void phonePanel_GoToChanged(Object sender, EventArgs e) throws Exception {
        if (formPhoneTiles.GotoPatNum != 0)
        {
            CurPatNum = formPhoneTiles.GotoPatNum;
            Patient pat = Patients.getPat(CurPatNum);
            refreshCurrentModule();
            fillPatientButton(pat);
        }
         
    }

    private void phoneSmall_GoToChanged(Object sender, EventArgs e) throws Exception {
        if (phoneSmall.GotoPatNum == 0)
        {
            return ;
        }
         
        CurPatNum = phoneSmall.GotoPatNum;
        Patient pat = Patients.getPat(CurPatNum);
        refreshCurrentModule();
        fillPatientButton(pat);
        Commlog commlog = Commlogs.getIncompleteEntry(Security.getCurUser().UserNum,CurPatNum);
        PhoneEmpDefault ped = PhoneEmpDefaults.getByExtAndEmp(phoneSmall.Extension,Security.getCurUser().EmployeeNum);
        if (ped != null && ped.IsTriageOperator)
        {
            Task task = new Task();
            task.TaskListNum = -1;
            //don't show it in any list yet.
            Tasks.insert(task);
            Task taskOld = task.copy();
            task.KeyNum = CurPatNum;
            task.ObjectType = TaskObjectType.Patient;
            task.TaskListNum = 1697;
            //Hardcoded for internal Triage task list.
            task.UserNum = Security.getCurUser().UserNum;
            task.Descript = Phones.getPhoneForExtension(Phones.getPhoneList(),phoneSmall.Extension).CustomerNumberRaw + " ";
            //Prefill description with customers number.
            FormTaskEdit FormTE = new FormTaskEdit(task,taskOld);
            FormTE.IsNew = true;
            FormTE.Show();
        }
        else
        {
            //Not a triage operator.
            if (commlog == null)
            {
                commlog = new Commlog();
                commlog.PatNum = CurPatNum;
                commlog.CommDateTime = DateTime.Now;
                commlog.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
                commlog.Mode_ = CommItemMode.Phone;
                commlog.SentOrReceived = CommSentOrReceived.Received;
                commlog.UserNum = Security.getCurUser().UserNum;
                FormCommItem FormCI = new FormCommItem(commlog);
                FormCI.IsNew = true;
                FormCI.ShowDialog();
                if (FormCI.DialogResult == DialogResult.OK)
                {
                    refreshCurrentModule();
                }
                 
            }
            else
            {
                FormCommItem FormCI = new FormCommItem(commlog);
                FormCI.ShowDialog();
                if (FormCI.DialogResult == DialogResult.OK)
                {
                    refreshCurrentModule();
                }
                 
            } 
        } 
    }

    private static class __MultiDelegateSetString   implements DelegateSetString
    {
        public void invoke(boolean hasError, int voiceMailCount, TimeSpan ageOfOldestVoicemail) throws Exception {
            IList<DelegateSetString> copy = new IList<DelegateSetString>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<DelegateSetString>(members);
            }
            for (Object __dummyForeachVar1 : copy)
            {
                DelegateSetString d = (DelegateSetString)__dummyForeachVar1;
                d.invoke(hasError, voiceMailCount, ageOfOldestVoicemail);
            }
        }

        private System.Collections.Generic.IList<DelegateSetString> _invocationList = new ArrayList<DelegateSetString>();
        public static DelegateSetString combine(DelegateSetString a, DelegateSetString b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiDelegateSetString ret = new __MultiDelegateSetString();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static DelegateSetString remove(DelegateSetString a, DelegateSetString b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<DelegateSetString> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<DelegateSetString> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiDelegateSetString ret = new __MultiDelegateSetString();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<DelegateSetString> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    private static interface DelegateSetString   
    {
        void invoke(boolean hasError, int voiceMailCount, TimeSpan ageOfOldestVoicemail) throws Exception ;

        System.Collections.Generic.IList<DelegateSetString> getInvocationList() throws Exception ;
    
    }

    //typically at namespace level rather than class level
    /**
    * Always called using ThreadVM.
    */
    private void threadVM_SetLabelMsg() throws Exception {
        while (true)
        {
            try
            {
                DirectoryInfo di = new DirectoryInfo(PhoneUI.PathPhoneMsg);
                //use this directory if you want to test with real files ===> @"\\10.10.1.197\Voicemail\default\998\Deleted"
                if (!di.Exists)
                {
                    this.Invoke(new DelegateSetString() 
                      { 
                        public System.Void invoke(System.Boolean hasError, System.Int32 voiceMailCount, TimeSpan ageOfOldestVoicemail) throws Exception {
                            setVoicemailMetrics(hasError, voiceMailCount, ageOfOldestVoicemail);
                        }

                        public List<DelegateSetString> getInvocationList() throws Exception {
                            List<DelegateSetString> ret = new ArrayList<DelegateSetString>();
                            ret.add(this);
                            return ret;
                        }
                    
                      }, new Object[]{ true, 0, new TimeSpan(0) });
                    Thread.Sleep(240000);
                    continue;
                }
                 
                //4 minutes
                DateTime oldestVoicemail = DateTime.MaxValue;
                FileInfo[] fileInfos = di.GetFiles("*.txt");
                for (int i = 0;i < fileInfos.Length;i++)
                {
                    if (fileInfos[i].CreationTime < oldestVoicemail)
                    {
                        oldestVoicemail = fileInfos[i].CreationTime;
                    }
                     
                }
                TimeSpan ageOfOldestVoicemail = new TimeSpan(0);
                if (oldestVoicemail != DateTime.MaxValue)
                {
                    ageOfOldestVoicemail = DateTime.Now - oldestVoicemail;
                }
                 
                this.Invoke(new DelegateSetString() 
                  { 
                    public System.Void invoke(System.Boolean hasError, System.Int32 voiceMailCount, TimeSpan ageOfOldestVoicemail) throws Exception {
                        setVoicemailMetrics(hasError, voiceMailCount, ageOfOldestVoicemail);
                    }

                    public List<DelegateSetString> getInvocationList() throws Exception {
                        List<DelegateSetString> ret = new ArrayList<DelegateSetString>();
                        ret.add(this);
                        return ret;
                    }
                
                  }, new Object[]{ false, fileInfos.Length, ageOfOldestVoicemail });
            }
            catch (ThreadAbortException __dummyCatchVar10)
            {
                return ;
            }
            catch (Exception ex)
            {
                throw ex;
            }

            //OnClosing will abort the thread.
            //Exits the loop.
            Thread.Sleep(3000);
        }
    }

    /**
    * Called from worker thread using delegate and Control.Invoke
    */
    private void setVoicemailMetrics(boolean hasError, int voiceMailCount, TimeSpan ageOfOldestVoicemail) throws Exception {
        if (hasError)
        {
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 8.25f, FontStyle.Bold);
            labelMsg.Text = "error";
            labelMsg.ForeColor = Color.Firebrick;
            return ;
        }
         
        labelMsg.Text = "V:" + voiceMailCount.ToString();
        if (voiceMailCount == 0)
        {
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 7.75f, FontStyle.Regular);
            labelMsg.ForeColor = Color.Black;
        }
        else
        {
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 7.75f, FontStyle.Bold);
            labelMsg.ForeColor = Color.Firebrick;
        } 
        if (formMapHQ != null && !formMapHQ.IsDisposed)
        {
            formMapHQ.setVoicemailRed(voiceMailCount,ageOfOldestVoicemail);
        }
         
        if (formPhoneTiles != null && !formPhoneTiles.IsDisposed)
        {
            formPhoneTiles.setVoicemailCount(voiceMailCount);
        }
         
    }

    /*private void moduleStaffBilling_GoToChanged(object sender,GoToEventArgs e) {
    			if(e.PatNum==0) {
    				return;
    			}
    			CurPatNum=e.PatNum;
    			Patient pat=Patients.GetPat(CurPatNum);
    			RefreshCurrentModule();
    			FillPatientButton(CurPatNum,pat.GetNameLF(),pat.Email!="",pat.ChartNumber);
    		}*/
    /**
    * This is recursive
    */
    private void translateMenuItem(MenuItem menuItem) throws Exception {
        for (Object __dummyForeachVar2 : menuItem.MenuItems)
        {
            //first translate any child menuItems
            MenuItem mi = (MenuItem)__dummyForeachVar2;
            translateMenuItem(mi);
        }
        //then this menuitem
        Lan.C("MainMenu", menuItem);
    }

    private void menuItemLogOff_Click(Object sender, System.EventArgs e) throws Exception {
        logOffNow();
    }

    //File
    private void menuItemPassword_Click(Object sender, EventArgs e) throws Exception {
        //no security blocking because everyone is allowed to change their own password.
        FormUserPassword FormU = new FormUserPassword(false,Security.getCurUser().UserName);
        FormU.ShowDialog();
        if (FormU.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        Security.getCurUser().Password = FormU.hashedResult;
        if (PrefC.getBool(PrefName.PasswordsMustBeStrong))
        {
            Security.getCurUser().PasswordIsStrong = true;
        }
         
        try
        {
            Userods.updatePassword(Security.getCurUser());
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DataValid.setInvalid(InvalidType.Security);
    }

    private void menuItemPrinter_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormPrinterSetup FormPS = new FormPrinterSetup();
        FormPS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Printers");
    }

    private void menuItemGraphics_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        FormGraphics fg = new FormGraphics();
        fg.ShowDialog();
        Cursor = Cursors.Default;
        if (fg.DialogResult == DialogResult.OK)
        {
            ContrChart2.initializeLocalData();
            refreshCurrentModule();
        }
         
    }

    private void menuItemConfig_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.ChooseDatabase))
        {
            return ;
        }
         
        SecurityLogs.MakeLogEntry(Permissions.ChooseDatabase, 0, "");
        //make the entry before switching databases.
        FormChooseDatabase FormC = new FormChooseDatabase();
        //Choose Database is read only from this menu.
        FormC.IsAccessedFromMainMenu = true;
        FormC.ShowDialog();
        if (FormC.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        CurPatNum = 0;
        refreshCurrentModule();
        //clumsy but necessary. Sets child PatNums to 0.
        if (!prefsStartup())
        {
            return ;
        }
         
        refreshLocalData(InvalidType.AllLocal);
        //RefreshCurrentModule();
        menuItemLogOff_Click(this,e);
    }

    //this is a quick shortcut.
    private void menuItemExit_Click(Object sender, System.EventArgs e) throws Exception {
        //Thread2.Abort();
        //if(this.TcpListener2!=null){
        //	this.TcpListener2.Stop();
        //}
        Application.Exit();
    }

    //FormBackupJobsSelect FormBJS=new FormBackupJobsSelect();
    //FormBJS.ShowDialog();
    //Setup
    private void menuItemApptFieldDefs_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormApptFieldDefs FormA = new FormApptFieldDefs();
        FormA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Appointment Field Defs");
    }

    private void menuItemApptRules_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormApptRules FormA = new FormApptRules();
        FormA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Appointment Rules");
    }

    private void menuItemApptViews_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormApptViews FormAV = new FormApptViews();
        FormAV.ShowDialog();
        refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Appointment Views");
    }

    private void menuItemAutoCodes_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormAutoCode FormAC = new FormAutoCode();
        FormAC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Auto Codes");
    }

    private void menuItemAutomation_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormAutomation FormA = new FormAutomation();
        FormA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Automation");
    }

    private void menuItemAutoNotes_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormAutoNotes FormA = new FormAutoNotes();
        FormA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Auto Notes");
    }

    private void menuItemClaimForms_Click(Object sender, System.EventArgs e) throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Claim Forms feature is unavailable when data path A to Z folder is disabled.");
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormClaimForms FormCF = new FormClaimForms();
        FormCF.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Claim Forms");
    }

    private void menuItemClearinghouses_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormClearinghouses FormC = new FormClearinghouses();
        FormC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Clearinghouses");
    }

    private void menuItemComputers_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormComputers FormC = new FormComputers();
        FormC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Computers");
    }

    private void menuItemDataPath_Click(Object sender, System.EventArgs e) throws Exception {
        //security is handled from within the form.
        FormPath FormP = new FormPath();
        FormP.ShowDialog();
        checkCustomReports();
        this.refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Data Path");
    }

    private void menuItemDefinitions_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions FormD = new FormDefinitions(DefCat.AccountColors);
        //just the first cat.
        FormD.ShowDialog();
        refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Definitions");
    }

    private void menuItemDisplayFields_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDisplayFieldCategories FormD = new FormDisplayFieldCategories();
        FormD.ShowDialog();
        refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Display Fields");
    }

    private void menuItemEmail_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormEmailAddresses FormEA = new FormEmailAddresses();
        FormEA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Email");
    }

    private void menuItemEHR_Click(Object sender, EventArgs e) throws Exception {
        //if(!Security.IsAuthorized(Permissions.Setup)) {
        //  return;
        //}
        FormEhrSetup FormE = new FormEhrSetup();
        FormE.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "EHR");
    }

    private void menuItemFeeScheds_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormFeeScheds FormF = new FormFeeScheds();
        FormF.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Fee Schedules");
    }

    private void menuItemHL7_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormHL7Defs FormH = new FormHL7Defs();
        FormH.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "HL7");
    }

    private void menuItemImaging_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormImagingSetup FormI = new FormImagingSetup();
        FormI.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Imaging");
    }

    private void menuItemInsCats_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormInsCatsSetup FormE = new FormInsCatsSetup();
        FormE.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Insurance Categories");
    }

    private void menuItemInsFilingCodes_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormInsFilingCodes FormF = new FormInsFilingCodes();
        FormF.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Insurance Filing Codes");
    }

    private void menuItemLaboratories_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormLaboratories FormL = new FormLaboratories();
        FormL.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Laboratories");
    }

    private void menuItemLetters_Click(Object sender, EventArgs e) throws Exception {
        FormLetters FormL = new FormLetters();
        FormL.ShowDialog();
    }

    private void menuItemMessaging_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormMessagingSetup FormM = new FormMessagingSetup();
        FormM.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Messaging");
    }

    private void menuItemMessagingButs_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormMessagingButSetup FormM = new FormMessagingButSetup();
        FormM.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Messaging");
    }

    private void menuItemMisc_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormMisc FormM = new FormMisc();
        if (FormM.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (userControlTasks1.Visible)
        {
            userControlTasks1.initializeOnStartup();
        }
         
        //menuItemMergeDatabases.Visible=PrefC.GetBool(PrefName.RandomPrimaryKeys");
        //if(timerSignals.Interval==0){
        if (PrefC.getInt(PrefName.ProcessSigsIntervalInSecs) == 0)
        {
            timerSignals.Enabled = false;
        }
        else
        {
            timerSignals.Interval = PrefC.getInt(PrefName.ProcessSigsIntervalInSecs) * 1000;
            timerSignals.Enabled = true;
        } 
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Misc");
    }

    private void menuItemModules_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormModuleSetup FormM = new FormModuleSetup();
        if (FormM.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        fillPatientButton(Patients.getPat(CurPatNum));
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Modules");
    }

    private void menuItemOperatories_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormOperatories FormO = new FormOperatories();
        FormO.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Operatories");
    }

    private void menuItemPatFieldDefs_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormPatFieldDefs FormP = new FormPatFieldDefs();
        FormP.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Patient Field Defs");
    }

    private void menuItemPayerIDs_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormElectIDs FormE = new FormElectIDs();
        FormE.IsSelectMode = false;
        FormE.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Payer IDs");
    }

    private void menuItemPractice_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormPractice FormPr = new FormPractice();
        FormPr.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Practice Info");
    }

    private void menuItemProblems_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDiseaseDefs FormD = new FormDiseaseDefs();
        FormD.ShowDialog();
        //RefreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Disease Defs");
    }

    private void menuItemProcedureButtons_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormProcButtons FormPB = new FormProcButtons();
        FormPB.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Procedure Buttons");
    }

    private void menuItemLinks_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormProgramLinks FormPL = new FormProgramLinks();
        FormPL.ShowDialog();
        ContrChart2.initializeLocalData();
        //for eCW
        layoutToolBar();
        if (CurPatNum > 0)
        {
            Patient pat = Patients.getPat(CurPatNum);
            fillPatientButton(pat);
        }
         
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Program Links");
    }

    /*
    		private void menuItem_ProviderAllocatorSetup_Click(object sender,EventArgs e) {
    			// Check Permissions
    			if(!Security.IsAuthorized(Permissions.Setup)) {
    				// Failed security prompts message box. Consider adding overload to not show message.
    				//MessageBox.Show("Not Authorized to Run Setup for Provider Allocation Tool");
    				return;
    			}
    			Reporting.Allocators.MyAllocator1.FormInstallAllocator_Provider fap = new OpenDental.Reporting.Allocators.MyAllocator1.FormInstallAllocator_Provider();
    			fap.ShowDialog();
    		}*/
    private void menuItemQuestions_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormQuestionDefs FormQ = new FormQuestionDefs();
        FormQ.ShowDialog();
        //RefreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Questionnaire");
    }

    private void menuItemRecall_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormRecallSetup FormRS = new FormRecallSetup();
        FormRS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Recall");
    }

    private void menuItemRecallTypes_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormRecallTypes FormRT = new FormRecallTypes();
        FormRT.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Recall Types");
    }

    private void menuItemReplication_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        FormReplicationSetup FormRS = new FormReplicationSetup();
        FormRS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, "Replication setup.");
    }

    private void menuItemRequirementsNeeded_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormReqNeededs FormR = new FormReqNeededs();
        FormR.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Requirements Needed");
    }

    private void menuItemSched_Click(Object sender, EventArgs e) throws Exception {
        //anyone should be able to view. Security must be inside schedule window.
        //if(!Security.IsAuthorized(Permissions.Schedules)) {
        //	return;
        //}
        FormSchedule FormS = new FormSchedule();
        FormS.ShowDialog();
    }

    //SecurityLogs.MakeLogEntry(Permissions.Schedules,0,"");
    /*private void menuItemBlockoutDefault_Click(object sender,System.EventArgs e) {
    			if(!Security.IsAuthorized(Permissions.Blockouts)) {
    				return;
    			}
    			FormSchedDefault FormSD=new FormSchedDefault(ScheduleType.Blockout);
    			FormSD.ShowDialog();
    			SecurityLogs.MakeLogEntry(Permissions.Blockouts,0,"Default");
    		}*/
    private void menuItemSecurity_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        FormSecurity FormS = new FormSecurity();
        FormS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, "");
    }

    private void menuItemSheets_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSheetDefs FormSD = new FormSheetDefs();
        FormSD.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Sheets");
    }

    //This shows as "Show Features"
    private void menuItemEasy_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormShowFeatures FormE = new FormShowFeatures();
        FormE.ShowDialog();
        ContrAccount2.layoutToolBar();
        //for repeating charges
        refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Show Features");
    }

    private void menuItemSpellCheck_Click(Object sender, EventArgs e) throws Exception {
        FormSpellCheck FormD = new FormSpellCheck();
        FormD.ShowDialog();
    }

    private void menuItemTimeCards_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormTimeCardSetup FormTCS = new FormTimeCardSetup();
        FormTCS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Time Card Setup");
    }

    //Lists
    private void menuItemProcCodes_Click(Object sender, System.EventArgs e) throws Exception {
        //security handled within form
        FormProcCodes FormP = new FormProcCodes();
        FormP.ShowDialog();
    }

    private void menuItemClinics_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormClinics FormC = new FormClinics();
        FormC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Clinics");
    }

    private void menuItemContacts_Click(Object sender, System.EventArgs e) throws Exception {
        FormContacts FormC = new FormContacts();
        FormC.ShowDialog();
    }

    private void menuItemCounties_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormCounties FormC = new FormCounties();
        FormC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Counties");
    }

    private void menuItemSchoolClass_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSchoolClasses FormS = new FormSchoolClasses();
        FormS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Dental School Classes");
    }

    private void menuItemSchoolCourses_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSchoolCourses FormS = new FormSchoolCourses();
        FormS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Dental School Courses");
    }

    private void menuItemEmployees_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormEmployeeSelect FormEmp = new FormEmployeeSelect();
        FormEmp.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Employees");
    }

    private void menuItemEmployers_Click(Object sender, System.EventArgs e) throws Exception {
        FormEmployers FormE = new FormEmployers();
        FormE.ShowDialog();
    }

    private void menuItemInstructors_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /*if(!Security.IsAuthorized(Permissions.Setup)){
    				return;
    			}
    			FormInstructors FormI=new FormInstructors();
    			FormI.ShowDialog();
    			SecurityLogs.MakeLogEntry(Permissions.Setup,0,"Dental School Instructors");*/
    private void menuItemCarriers_Click(Object sender, System.EventArgs e) throws Exception {
        FormCarriers FormC = new FormCarriers();
        FormC.ShowDialog();
        refreshCurrentModule();
    }

    private void menuItemInsPlans_Click(Object sender, System.EventArgs e) throws Exception {
        FormInsPlans FormIP = new FormInsPlans();
        FormIP.ShowDialog();
        refreshCurrentModule();
    }

    private void menuItemLabCases_Click(Object sender, EventArgs e) throws Exception {
        FormLabCases FormL = new FormLabCases();
        FormL.ShowDialog();
        if (FormL.GoToAptNum != 0)
        {
            Appointment apt = Appointments.getOneApt(FormL.GoToAptNum);
            Patient pat = Patients.getPat(apt.PatNum);
            OpenDental.PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
            //if(PatientSelected!=null){
            //	PatientSelected(this,eArgs);
            //}
            contr_PatientSelected(this,eArgs);
            //OnPatientSelected(pat.PatNum,pat.GetNameLF(),pat.Email!="",pat.ChartNumber);
            GotoModule.gotoAppointment(apt.AptDateTime,apt.AptNum);
        }
         
    }

    private void menuItemMedications_Click(Object sender, System.EventArgs e) throws Exception {
        FormMedications FormM = new FormMedications();
        FormM.ShowDialog();
    }

    private void menuItemPharmacies_Click(Object sender, EventArgs e) throws Exception {
        FormPharmacies FormP = new FormPharmacies();
        FormP.ShowDialog();
    }

    private void menuItemProviders_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Providers))
        {
            return ;
        }
         
        //formerly used Setup permission
        FormProviderSelect FormPS = new FormProviderSelect();
        FormPS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Providers");
    }

    private void menuItemPrescriptions_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormRxSetup FormRxSetup2 = new FormRxSetup();
        FormRxSetup2.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Rx");
    }

    private void menuItemReferrals_Click(Object sender, System.EventArgs e) throws Exception {
        FormReferralSelect FormRS = new FormReferralSelect();
        FormRS.ShowDialog();
    }

    private void menuItemSites_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSites FormS = new FormSites();
        FormS.ShowDialog();
        refreshCurrentModule();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Sites");
    }

    private void menuItemZipCodes_Click(Object sender, System.EventArgs e) throws Exception {
        //if(!Security.IsAuthorized(Permissions.Setup)){
        //	return;
        //}
        FormZipCodes FormZ = new FormZipCodes();
        FormZ.ShowDialog();
    }

    //SecurityLogs.MakeLogEntry(Permissions.Setup,"Zip Codes");
    private void menuItemReports_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Reports))
        {
            return ;
        }
         
        FormReportsMore FormR = new FormReportsMore();
        FormR.ShowDialog();
        if (FormR.RpModalSelection == ReportModalSelection.TreatmentFinder)
        {
            FormRpTreatmentFinder FormT = new FormRpTreatmentFinder();
            FormT.Show();
        }
         
        if (FormR.RpModalSelection == ReportModalSelection.OutstandingIns)
        {
            FormRpOutstandingIns FormOI = new FormRpOutstandingIns();
            FormOI.Show();
        }
         
    }

    //Custom Reports
    private void menuItemRDLReport_Click(Object sender, System.EventArgs e) throws Exception {
        //This point in the code is only reached if the A to Z folders are enabled, thus
        //the image path should exist.
        FormReportCustom FormR = new FormReportCustom();
        FormR.setSourceFilePath(CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), PrefC.getString(PrefName.ReportFolderName), ((MenuItem)sender).Text + ".rdl"));
        FormR.ShowDialog();
    }

    //Tools
    private void menuItemPrintScreen_Click(Object sender, System.EventArgs e) throws Exception {
        FormPrntScrn FormPS = new FormPrntScrn();
        FormPS.ShowDialog();
    }

    //MiscTools
    private void menuItemDuplicateBlockouts_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormBlockoutDuplicatesFix form = new FormBlockoutDuplicatesFix();
        form.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Clear duplicate blockouts.");
    }

    private void menuItemCreateAtoZFolders_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormAtoZFoldersCreate FormA = new FormAtoZFoldersCreate();
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.OK)
        {
            SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Created AtoZ Folder");
        }
         
    }

    private void menuItemImportXML_Click(Object sender, System.EventArgs e) throws Exception {
        FormImportXML FormI = new FormImportXML();
        FormI.ShowDialog();
    }

    private void menuItemMergePatients_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormPatientMerge fpm = new FormPatientMerge();
        fpm.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Merge Patients");
    }

    private void menuItemProcLockTool_Click(Object sender, EventArgs e) throws Exception {
        FormProcLockTool FormT = new FormProcLockTool();
        FormT.ShowDialog();
    }

    //security entries made inside the form
    //SecurityLogs.MakeLogEntry(Permissions.Setup,0,"Proc Lock Tool");
    private void menuItemShutdown_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormShutdown FormS = new FormShutdown();
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //turn off signal reception for 5 seconds so this workstation will not shut down.
        signalLastRefreshed = MiscData.getNowDateTime().AddSeconds(5);
        Signalod sig = new Signalod();
        sig.ITypes = (((Enum)InvalidType.ShutDownNow).ordinal()).ToString();
        sig.SigType = SignalType.Invalid;
        Signalods.insert(sig);
        Computers.ClearAllHeartBeats(Environment.MachineName);
        //always assume success
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Shutdown all workstations.");
    }

    private void menuTelephone_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormTelephone FormT = new FormTelephone();
        FormT.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Telephone");
    }

    private void menuItemTestLatency_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormTestLatency formTL = new FormTestLatency();
        formTL.ShowDialog();
    }

    private void menuItemXChargeReconcile_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Accounting))
        {
            return ;
        }
         
        FormXChargeReconcile FormXCR = new FormXChargeReconcile();
        FormXCR.ShowDialog();
    }

    //End of MiscTools, resume Tools.
    private void menuItemAging_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormAging FormAge = new FormAging();
        FormAge.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Aging Update");
    }

    private void menuItemAuditTrail_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        FormAudit FormA = new FormAudit();
        FormA.CurPatNum = CurPatNum;
        FormA.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, "Audit Trail");
    }

    private void menuItemFinanceCharge_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormFinanceCharges FormFC = new FormFinanceCharges();
        FormFC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Run Finance Charges");
    }

    private void menuItemCCRecurring_Click(Object sender, EventArgs e) throws Exception {
        if (FormCRC == null || FormCRC.IsDisposed)
        {
            FormCRC = new FormCreditRecurringCharges();
        }
         
        FormCRC.Show();
        if (FormCRC.WindowState == FormWindowState.Minimized)
        {
            FormCRC.WindowState = FormWindowState.Normal;
        }
         
        FormCRC.BringToFront();
    }

    private void menuItemCustomerManage_Click(Object sender, EventArgs e) throws Exception {
        FormCustomerManagement FormC = new FormCustomerManagement();
        FormC.ShowDialog();
        if (FormC.SelectedPatNum != 0)
        {
            CurPatNum = FormC.SelectedPatNum;
            Patient pat = Patients.getPat(CurPatNum);
            refreshCurrentModule();
            fillPatientButton(pat);
        }
         
    }

    private void menuItemDatabaseMaintenance_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDatabaseMaintenance FormDM = new FormDatabaseMaintenance();
        FormDM.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Database Maintenance");
    }

    private void menuItemTerminal_Click(Object sender, EventArgs e) throws Exception {
        FormTerminal FormT = new FormTerminal();
        FormT.ShowDialog();
        Application.Exit();
    }

    //always close after coming out of terminal mode as a safety precaution.*/
    private void menuItemTerminalManager_Click(Object sender, EventArgs e) throws Exception {
        if (formTerminalManager == null || formTerminalManager.IsDisposed)
        {
            formTerminalManager = new FormTerminalManager();
        }
         
        formTerminalManager.Show();
        formTerminalManager.BringToFront();
    }

    private void menuItemTranslation_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormTranslationCat FormTC = new FormTranslationCat();
        FormTC.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Translations");
    }

    private void menuItemMobileSetup_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        //MessageBox.Show("Not yet functional.");
        FormMobile FormM = new FormMobile();
        FormM.ShowDialog();
    }

    //SecurityLogs.MakeLogEntry(Permissions.Setup,0,"Mobile Sync");
    private void menuItemNewCropBilling_Click(Object sender, EventArgs e) throws Exception {
        FormNewCropBilling FormN = new FormNewCropBilling();
        FormN.ShowDialog();
    }

    private void menuItemRepeatingCharges_Click(Object sender, System.EventArgs e) throws Exception {
        FormRepeatChargesUpdate FormR = new FormRepeatChargesUpdate();
        FormR.ShowDialog();
    }

    private void menuItemResellers_Click(Object sender, EventArgs e) throws Exception {
        FormResellers FormR = new FormResellers();
        FormR.ShowDialog();
    }

    private void menuItemScreening_Click(Object sender, System.EventArgs e) throws Exception {
        FormScreenGroups FormS = new FormScreenGroups();
        FormS.ShowDialog();
    }

    private void menuItemReqStudents_Click(Object sender, EventArgs e) throws Exception {
        Provider prov = Providers.getProv(Security.getCurUser().ProvNum);
        if (prov != null && prov.SchoolClassNum != 0)
        {
            //if a student is logged in
            //the student always has permission to view their own requirements
            FormReqStudentOne FormO = new FormReqStudentOne();
            FormO.ProvNum = prov.ProvNum;
            FormO.ShowDialog();
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormReqStudentsMany FormM = new FormReqStudentsMany();
        FormM.ShowDialog();
    }

    private void menuItemWebForms_Click(Object sender, EventArgs e) throws Exception {
        FormWebForms FormWF = new FormWebForms();
        FormWF.Show();
    }

    private void menuItemWiki_Click(Object sender, EventArgs e) throws Exception {
        if (FormMyWiki == null || FormMyWiki.IsDisposed)
        {
            FormMyWiki = new FormWiki();
        }
         
        FormMyWiki.Show();
        if (FormMyWiki.WindowState == FormWindowState.Minimized)
        {
            FormMyWiki.WindowState = FormWindowState.Normal;
        }
         
        FormMyWiki.BringToFront();
    }

    //Help
    private void menuItemRemote_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            Process.Start("http://www.opendental.com/contact.html");
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Could not find") + " http://www.opendental.com/contact.html" + "\r\n" + "Please set up a default web browser.");
        }
    
    }

    /*
    			if(!MsgBox.Show(this,true,"A remote connection will now be attempted. Do NOT continue unless you are already on the phone with us.  Do you want to continue?"))
    			{
    				return;
    			}
    			try{
    				Process.Start("remoteclient.exe");//Network streaming remote client or any other similar client
    			}
    			catch{
    				MsgBox.Show(this,"Could not find file.");
    			}*/
    private void menuItemHelpWindows_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            Process.Start("Help.chm");
        }
        catch (Exception __dummyCatchVar11)
        {
            MsgBox.show(this,"Could not find file.");
        }
    
    }

    private void menuItemHelpContents_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            Process.Start("http://www.opendental.com/manual/toc.html");
        }
        catch (Exception __dummyCatchVar12)
        {
            MsgBox.show(this,"Could not find file.");
        }
    
    }

    private void menuItemHelpIndex_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            Process.Start("http://www.opendental.com/manual/alphabetical.html");
        }
        catch (Exception __dummyCatchVar13)
        {
            MessageBox.Show("Could not find file.");
        }
    
    }

    private void menuItemRequestFeatures_Click(Object sender, EventArgs e) throws Exception {
        FormFeatureRequest FormF = new FormFeatureRequest();
        FormF.ShowDialog();
    }

    private void menuItemUpdate_Click(Object sender, System.EventArgs e) throws Exception {
        //If A to Z folders are disabled, this menu option is unavailable, since
        //updates are handled more automatically.
        FormUpdate FormU = new FormUpdate();
        FormU.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Update Version");
    }

    /*private void menuItemDaily_DrawItem(object sender, System.Windows.Forms.DrawItemEventArgs e) {
    			//MessageBox.Show(e.Bounds.ToString());
    			e.Graphics.DrawString("Dailyyyy",new Font("Microsoft Sans Serif",8),Brushes.Black,e.Bounds.X,e.Bounds.Y);
    		}
    		private void menuItemDaily_Click(object sender, System.EventArgs e) {
    		
    		}*/
    //private void OnPatientCardInserted(object sender, PatientCardInsertedEventArgs e) {
    //  if (InvokeRequired) {
    //    Invoke(new PatientCardInsertedEventHandler(OnPatientCardInserted), new object[] { sender, e });
    //    return;
    //  }
    //  if (MessageBox.Show(this, string.Format(Lan.g(this, "A card belonging to {0} has been inserted. Do you wish to search for this patient now?"), e.Patient.GetNameFL()), "Open Dental", MessageBoxButtons.YesNo) != DialogResult.Yes)
    //  {
    //    return;
    //  }
    //  using (FormPatientSelect formPS = new FormPatientSelect()) {
    //    formPS.PreselectPatient(e.Patient);
    //    if(formPS.ShowDialog() == DialogResult.OK) {
    //      // OnPatientSelected(formPS.SelectedPatNum);
    //      // ModuleSelected(formPS.SelectedPatNum);
    //    }
    //  }
    //}
    /**
    * separate thread
    */
    public void listen() throws Exception {
        IPAddress ipAddress = Dns.GetHostAddresses("localhost")[0];
        TcpListenerCommandLine = new TcpListener(ipAddress, 2123);
        TcpListenerCommandLine.Start();
        while (true)
        {
            if (!TcpListenerCommandLine.Pending())
            {
                continue;
            }
             
            //Thread.Sleep(1000);//for 1 second
            TcpClient TcpClientRec = TcpListenerCommandLine.AcceptTcpClient();
            NetworkStream ns = TcpClientRec.GetStream();
            XmlSerializer serializer = new XmlSerializer(String[].class);
            String[] args = (String[])serializer.Deserialize(ns);
            Invoke(new ProcessCommandLineDelegate() 
              { 
                public System.Void invoke(System.Array'1 args) throws Exception {
                    processCommandLine(args);
                }

                public List<ProcessCommandLineDelegate> getInvocationList() throws Exception {
                    List<ProcessCommandLineDelegate> ret = new ArrayList<ProcessCommandLineDelegate>();
                    ret.add(this);
                    return ret;
                }
            
              }, new Object[]{ args });
            ns.Close();
            TcpClientRec.Close();
        }
    }

    /**
    * 
    */
    protected static class __MultiProcessCommandLineDelegate   implements ProcessCommandLineDelegate
    {
        public void invoke(String[] args) throws Exception {
            IList<ProcessCommandLineDelegate> copy = new IList<ProcessCommandLineDelegate>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<ProcessCommandLineDelegate>(members);
            }
            for (Object __dummyForeachVar3 : copy)
            {
                ProcessCommandLineDelegate d = (ProcessCommandLineDelegate)__dummyForeachVar3;
                d.invoke(args);
            }
        }

        private System.Collections.Generic.IList<ProcessCommandLineDelegate> _invocationList = new ArrayList<ProcessCommandLineDelegate>();
        public static ProcessCommandLineDelegate combine(ProcessCommandLineDelegate a, ProcessCommandLineDelegate b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiProcessCommandLineDelegate ret = new __MultiProcessCommandLineDelegate();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static ProcessCommandLineDelegate remove(ProcessCommandLineDelegate a, ProcessCommandLineDelegate b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<ProcessCommandLineDelegate> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<ProcessCommandLineDelegate> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiProcessCommandLineDelegate ret = new __MultiProcessCommandLineDelegate();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<ProcessCommandLineDelegate> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    protected static interface ProcessCommandLineDelegate   
    {
        void invoke(String[] args) throws Exception ;

        System.Collections.Generic.IList<ProcessCommandLineDelegate> getInvocationList() throws Exception ;
    
    }

    /**
    * 
    */
    public void processCommandLine(String[] args) throws Exception {
        //if(!Programs.UsingEcwTight() && args.Length==0){
        if (!Programs.usingEcwTightOrFullMode() && args.Length == 0)
        {
            return ;
        }
         
        //May have to modify to accept from other sw.
        /*string descript="";
        			for(int i=0;i<args.Length;i++) {
        				if(i>0) {
        					descript+="\r\n";
        				}
        				descript+=args[i];
        			}
        			MessageBox.Show(descript);*/
        /*
        			PatNum (the integer primary key)
        			ChartNumber (alphanumeric)
        			SSN (exactly nine digits. If required, we can gracefully handle dashes, but that is not yet implemented)
        			UserName
        			Password*/
        int patNum = 0;
        String chartNumber = "";
        String ssn = "";
        String userName = "";
        String passHash = "";
        String aptNum = "";
        String ecwConfigPath = "";
        int userId = 0;
        String jSessionId = "";
        String jSessionIdSSO = "";
        String lbSessionId = "";
        for (int i = 0;i < args.Length;i++)
        {
            if (args[i].StartsWith("PatNum=") && args[i].Length > 7)
            {
                String patNumStr = args[i].Substring(7).Trim('"');
                try
                {
                    patNum = Convert.ToInt32(patNumStr);
                }
                catch (Exception __dummyCatchVar14)
                {
                }
            
            }
             
            if (args[i].StartsWith("ChartNumber=") && args[i].Length > 12)
            {
                chartNumber = args[i].Substring(12).Trim('"');
            }
             
            if (args[i].StartsWith("SSN=") && args[i].Length > 4)
            {
                ssn = args[i].Substring(4).Trim('"');
            }
             
            if (args[i].StartsWith("UserName=") && args[i].Length > 9)
            {
                userName = args[i].Substring(9).Trim('"');
            }
             
            if (args[i].StartsWith("PassHash=") && args[i].Length > 9)
            {
                passHash = args[i].Substring(9).Trim('"');
            }
             
            if (args[i].StartsWith("AptNum=") && args[i].Length > 7)
            {
                aptNum = args[i].Substring(7).Trim('"');
            }
             
            if (args[i].StartsWith("EcwConfigPath=") && args[i].Length > 14)
            {
                ecwConfigPath = args[i].Substring(14).Trim('"');
            }
             
            if (args[i].StartsWith("UserId=") && args[i].Length > 7)
            {
                String userIdStr = args[i].Substring(7).Trim('"');
                try
                {
                    userId = Convert.ToInt32(userIdStr);
                }
                catch (Exception __dummyCatchVar15)
                {
                }
            
            }
             
            if (args[i].StartsWith("JSESSIONID=") && args[i].Length > 11)
            {
                jSessionId = args[i].Substring(11).Trim('"');
            }
             
            if (args[i].StartsWith("JSESSIONIDSSO=") && args[i].Length > 14)
            {
                jSessionIdSSO = args[i].Substring(14).Trim('"');
            }
             
            if (args[i].StartsWith("LBSESSIOINID=") && args[i].Length > 12)
            {
                lbSessionId = args[i].Substring(12).Trim('"');
            }
             
        }
        //eCW bridge values-------------------------------------------------------------
        ECW.AptNum = PIn.Long(aptNum);
        ECW.EcwConfigPath = ecwConfigPath;
        ECW.UserId = userId;
        ECW.JSessionId = jSessionId;
        ECW.JSessionIdSSO = jSessionIdSSO;
        ECW.LBSessionId = lbSessionId;
        //Username and password-----------------------------------------------------
        //users are allowed to use ecw tight integration without command line.  They can manually launch Open Dental.
        //if((Programs.UsingEcwTight() && Security.CurUser==null)//We always want to trigger login window for eCW tight, even if no username was passed in.
        //We always want to trigger login window for eCW tight, even if no username was passed in.
        //if a username was passed in, but not in tight eCW mode
        if ((Programs.usingEcwTightOrFullMode() && Security.getCurUser() == null) || (!StringSupport.equals(userName, "") && (Security.getCurUser() == null || !StringSupport.equals(Security.getCurUser().UserName, userName))))
        {
            //and it's different from the current user
            //The purpose of this loop is to use the username and password that were passed in to determine which user to log in
            //log out------------------------------------
            LastModule = myOutlookBar.SelectedIndex;
            myOutlookBar.SelectedIndex = -1;
            myOutlookBar.Invalidate();
            unselectActive();
            allNeutral();
            Userod user = Userods.getUserByName(userName,true);
            if (user == null)
            {
                //if(Programs.UsingEcwTight() && userName!="") {
                if (Programs.usingEcwTightOrFullMode() && !StringSupport.equals(userName, ""))
                {
                    user = new Userod();
                    user.UserName = userName;
                    user.UserGroupNum = PIn.Long(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"DefaultUserGroup"));
                    if (StringSupport.equals(passHash, ""))
                    {
                        user.Password = "";
                    }
                    else
                    {
                        user.Password = passHash;
                    } 
                    Userods.insert(user);
                    //This can fail if duplicate username because of capitalization differences.
                    DataValid.setInvalid(InvalidType.Security);
                }
                else
                {
                    //not using eCW in tight integration mode
                    //So present logon screen
                    FormLogOn_ = new FormLogOn();
                    FormLogOn_.ShowDialog(this);
                    if (FormLogOn_.DialogResult == DialogResult.Cancel)
                    {
                        Application.Exit();
                        return ;
                    }
                     
                    user = Security.getCurUser().copy();
                } 
            }
             
            //Can't use Userods.CheckPassword, because we only have the hashed password.
            //if(passHash!=user.Password || !Programs.UsingEcwTight())//password not accepted or not using eCW
            if (!StringSupport.equals(passHash, user.Password) || !Programs.usingEcwTightOrFullMode())
            {
                //password not accepted or not using eCW
                //So present logon screen
                FormLogOn_ = new FormLogOn();
                FormLogOn_.ShowDialog(this);
                if (FormLogOn_.DialogResult == DialogResult.Cancel)
                {
                    Application.Exit();
                    return ;
                }
                 
            }
            else
            {
                //password accepted and using eCW tight.
                //this part usually happens in the logon window
                Security.setCurUser(user.copy());
            } 
            //let's skip tasks for now
            //if(PrefC.GetBool(PrefName.TasksCheckOnStartup")){
            //	int taskcount=Tasks.UserTasksCount(Security.CurUser.UserNum);
            //	if(taskcount>0){
            //		MessageBox.Show(Lan.g(this,"There are ")+taskcount+Lan.g(this," unfinished tasks on your tasklists."));
            //	}
            //}
            myOutlookBar.SelectedIndex = Security.getModule(LastModule);
            myOutlookBar.Invalidate();
            setModuleSelected();
            if (CurPatNum == 0)
            {
                Text = PatientL.getMainTitle(null);
            }
            else
            {
                Patient pat = Patients.getPat(CurPatNum);
                Text = PatientL.getMainTitle(pat);
            } 
            if (userControlTasks1.Visible)
            {
                userControlTasks1.initializeOnStartup();
            }
             
            if (myOutlookBar.SelectedIndex == -1)
            {
                MsgBox.show(this,"You do not have permission to use any modules.");
            }
             
        }
         
        //patient id----------------------------------------------------------------
        if (patNum != 0)
        {
            Patient pat = Patients.GetPat(patNum);
            if (pat == null)
            {
                CurPatNum = 0;
                refreshCurrentModule();
                fillPatientButton(null);
            }
            else
            {
                CurPatNum = patNum;
                refreshCurrentModule();
                fillPatientButton(pat);
            } 
        }
        else if (!StringSupport.equals(chartNumber, ""))
        {
            Patient pat = Patients.getPatByChartNumber(chartNumber);
            if (pat == null)
            {
                //todo: decide action
                CurPatNum = 0;
                refreshCurrentModule();
                fillPatientButton(null);
            }
            else
            {
                CurPatNum = pat.PatNum;
                refreshCurrentModule();
                fillPatientButton(pat);
            } 
        }
        else if (!StringSupport.equals(ssn, ""))
        {
            Patient pat = Patients.getPatBySSN(ssn);
            if (pat == null)
            {
                //todo: decide action
                CurPatNum = 0;
                refreshCurrentModule();
                fillPatientButton(null);
            }
            else
            {
                CurPatNum = pat.PatNum;
                refreshCurrentModule();
                fillPatientButton(pat);
            } 
        }
           
    }

    private void timerPhoneWebCam_Tick(Object sender, EventArgs e) throws Exception {
        //every 1.6s.  Performance was not very good when it was synchronous.
        //This won't even happen unless PrefName.DockPhonePanelShow==true
        Thread workerThread = new Thread(new ThreadStart(PhoneWebCamTickWorkerThread));
        workerThread.Start();
    }

    private void phoneWebCamTickWorkerThread() throws Exception {
        List<Phone> phoneList = Phones.getPhoneList();
        List<PhoneEmpDefault> listPED = PhoneEmpDefaults.refresh();
        String ipaddressStr = "";
        IPHostEntry iphostentry = Dns.GetHostEntry(Environment.MachineName);
        for (Object __dummyForeachVar4 : iphostentry.AddressList)
        {
            IPAddress ipaddress = (IPAddress)__dummyForeachVar4;
            //if(ipaddress.ToString().Contains("192.168.0.2")) {
            if (ipaddress.ToString().Contains("10.10.1.2"))
            {
                ipaddressStr = ipaddress.ToString();
            }
             
        }
        //Get the extension linked to this machine or ip. Set in FormPhoneEmpDefaultEdit.
        int extension = PhoneEmpDefaults.GetPhoneExtension(ipaddressStr, Environment.MachineName, listPED);
        //Now get the Phone object for this extension. Phone table matches PhoneEmpDefault table more or less 1:1.
        //Phone fields represent current state of the PhoneEmpDefault table and will be modified by the phone tracking server anytime a phone state changes for a given extension
        //(EG... incoming call, outgoing call, hangup, etc).
        Phone phone = Phones.GetPhoneForExtension(phoneList, extension);
        boolean isTriageOperator = PhoneEmpDefaults.IsTriageOperatorForExtension(extension, listPED);
        try
        {
            //send the results back to the UI layer for action.
            if (!this.IsDisposed)
            {
                Invoke(new PhoneWebCamTickDisplayDelegate() 
                  { 
                    public System.Void invoke(List phoneEmpDefaultList, List phoneList, Phone phone, System.Boolean isTriageOperator) throws Exception {
                        phoneWebCamTickDisplay(phoneEmpDefaultList, phoneList, phone, isTriageOperator);
                    }

                    public List<PhoneWebCamTickDisplayDelegate> getInvocationList() throws Exception {
                        List<PhoneWebCamTickDisplayDelegate> ret = new ArrayList<PhoneWebCamTickDisplayDelegate>();
                        ret.add(this);
                        return ret;
                    }
                
                  }, new Object[]{ listPED, phoneList, phone, isTriageOperator });
            }
             
        }
        catch (Exception __dummyCatchVar16)
        {
        }
    
    }

    //prevents crash on closing if FormOpenDental has already been disposed.
    /**
    * 
    */
    protected static class __MultiPhoneWebCamTickDisplayDelegate   implements PhoneWebCamTickDisplayDelegate
    {
        public void invoke(List<PhoneEmpDefault> phoneEmpDefaultList, List<Phone> phoneList, Phone phone, boolean isTriageOperator) throws Exception {
            IList<PhoneWebCamTickDisplayDelegate> copy = new IList<PhoneWebCamTickDisplayDelegate>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<PhoneWebCamTickDisplayDelegate>(members);
            }
            for (Object __dummyForeachVar5 : copy)
            {
                PhoneWebCamTickDisplayDelegate d = (PhoneWebCamTickDisplayDelegate)__dummyForeachVar5;
                d.invoke(phoneEmpDefaultList, phoneList, phone, isTriageOperator);
            }
        }

        private System.Collections.Generic.IList<PhoneWebCamTickDisplayDelegate> _invocationList = new ArrayList<PhoneWebCamTickDisplayDelegate>();
        public static PhoneWebCamTickDisplayDelegate combine(PhoneWebCamTickDisplayDelegate a, PhoneWebCamTickDisplayDelegate b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiPhoneWebCamTickDisplayDelegate ret = new __MultiPhoneWebCamTickDisplayDelegate();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static PhoneWebCamTickDisplayDelegate remove(PhoneWebCamTickDisplayDelegate a, PhoneWebCamTickDisplayDelegate b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<PhoneWebCamTickDisplayDelegate> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<PhoneWebCamTickDisplayDelegate> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiPhoneWebCamTickDisplayDelegate ret = new __MultiPhoneWebCamTickDisplayDelegate();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<PhoneWebCamTickDisplayDelegate> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    protected static interface PhoneWebCamTickDisplayDelegate   
    {
        void invoke(List<PhoneEmpDefault> phoneEmpDefaultList, List<Phone> phoneList, Phone phone, boolean isTriageOperator) throws Exception ;

        System.Collections.Generic.IList<PhoneWebCamTickDisplayDelegate> getInvocationList() throws Exception ;
    
    }

    /**
    * phoneList is the list of all phone rows just pulled from the database.  phone is the one that we should display here, and it can be null.
    */
    public void phoneWebCamTickDisplay(List<PhoneEmpDefault> phoneEmpDefaultList, List<Phone> phoneList, Phone phone, boolean isTriageOperator) throws Exception {
        try
        {
            //Send the phoneList to the 2 places where it's needed.
            //1) Send to the small display in the main OD form (quick-glance).
            phoneSmall.SetPhoneList(phoneEmpDefaultList, phoneList);
            if (formPhoneTiles != null && !formPhoneTiles.IsDisposed)
            {
                //2) Send to the big phones panel if it is open.
                formPhoneTiles.SetPhoneList(phoneEmpDefaultList, phoneList);
            }
             
            if (formMapHQ != null && !formMapHQ.IsDisposed)
            {
                //3) Send to the map hq if it is open.
                formMapHQ.SetPhoneList(phoneEmpDefaultList, phoneList);
            }
             
            //Now set the small display's current phone extension info.
            if (phone == null)
            {
                phoneSmall.Extension = 0;
            }
            else
            {
                phoneSmall.Extension = phone.Extension;
            } 
            phoneSmall.SetPhone(phone, PhoneEmpDefaults.GetEmpDefaultFromList(phone.EmployeeNum, phoneEmpDefaultList), isTriageOperator);
        }
        catch (Exception __dummyCatchVar17)
        {
        }
    
    }

    //HQ users are complaining of unhandled exception when they close OD.
    //Suspect it could be caused here if the thread tries to access a control that has been disposed.
    /**
    * This is set to 30 seconds
    */
    private void timerWebHostSynch_Tick(Object sender, EventArgs e) throws Exception {
        String interval = PrefC.getStringSilent(PrefName.MobileSyncIntervalMinutes);
        if (StringSupport.equals(interval, "") || StringSupport.equals(interval, "0"))
        {
            return ;
        }
         
        //not a paid customer or chooses not to synch
        if (System.Environment.MachineName.ToUpper() != PrefC.getStringSilent(PrefName.MobileSyncWorkstationName).ToUpper())
        {
            return ;
        }
         
        //Since GetStringSilent returns "" before OD is connected to db, this gracefully loops out
        if (PrefC.getDate(PrefName.MobileExcludeApptsBeforeDate).Year < 1880)
        {
            return ;
        }
         
        //full synch never run
        FormMobile.synchFromMain(false);
    }

    private void timerReplicationMonitor_Tick(Object sender, EventArgs e) throws Exception {
        //this timer doesn't get turned on until after user successfully logs in.
        if (ReplicationServers.getListt().Count == 0)
        {
            return ;
        }
         
        //Listt will be automatically refreshed if null.
        //user must not be using any replication
        boolean isSlaveMonitor = false;
        for (int i = 0;i < ReplicationServers.getListt().Count;i++)
        {
            if (ReplicationServers.getListt()[i].SlaveMonitor.ToString() != Dns.GetHostName())
            {
                isSlaveMonitor = true;
            }
             
        }
        if (!isSlaveMonitor)
        {
            return ;
        }
         
        DataTable table = ReplicationServers.getSlaveStatus();
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        if (!StringSupport.equals(table.Rows[0]["Replicate_Do_Db"].ToString(), OpenDentBusiness.DataConnection.getDatabaseName()))
        {
            return ;
        }
         
        //if the database we're connected to is not even involved in replication
        String status = table.Rows[0]["Slave_SQL_Running"].ToString();
        if (StringSupport.equals(status, "Yes"))
        {
            return ;
        }
         
        //Shut down all copies of OD and set ReplicationFailureAtServer_id to this server_id
        //No workstations will be able to connect to this single server while this flag is set.
        Prefs.updateInt(PrefName.ReplicationFailureAtServer_id,ReplicationServers.getServer_id());
        //shut down all workstations on all servers
        FormOpenDental.signalLastRefreshed = MiscData.getNowDateTime().AddSeconds(5);
        Signalod sig = new Signalod();
        sig.ITypes = (((Enum)InvalidType.ShutDownNow).ordinal()).ToString();
        sig.SigType = SignalType.Invalid;
        Signalods.insert(sig);
        Computers.ClearAllHeartBeats(Environment.MachineName);
        //always assume success
        timerReplicationMonitor.Enabled = false;
        MsgBox.show(this,"This database is temporarily unavailable.  Please connect instead to your alternate database at the other location.");
        Application.Exit();
    }

    /**
    * This is set to 15 seconds.  This interval must be longer than the interval of the timer in FormLogoffWarning (10s), or it will go into a loop.
    */
    private void timerLogoff_Tick(Object sender, EventArgs e) throws Exception {
        if (PrefC.getInt(PrefName.SecurityLogOffAfterMinutes) == 0)
        {
            return ;
        }
         
        //Warning.  When debugging this, the ActiveForm will be impossible to determine by setting breakpoints.
        //string activeFormText=Form.ActiveForm.Text;
        //If a breakpoint is set below here, ActiveForm will erroneously show as null.
        if (Form.ActiveForm == null)
        {
            //some other program has focus
            FormRecentlyOpenForLogoff = null;
        }
        else //Do not alter IsFormLogOnLastActive because it could still be active in background.
        if (Form.ActiveForm == this)
        {
            //main form active
            FormRecentlyOpenForLogoff = null;
            //User must have logged back in so IsFormLogOnLastActive should be false.
            IsFormLogOnLastActive = false;
        }
        else
        {
            //Some Open Dental dialog is active.
            if (Form.ActiveForm == FormRecentlyOpenForLogoff)
            {
            }
            else
            {
                //The same form is active as last time, so don't add events again.
                //The active form will now be constantly resetting the dateTimeLastActivity.
                //this is the first time this form has been encountered, so attach events and don't do anything else
                AttachMouseMoveToForm(Form.ActiveForm);
                FormRecentlyOpenForLogoff = Form.ActiveForm;
                dateTimeLastActivity = DateTime.Now;
                //Flag FormLogOn as the active form so that OD doesn't continue trying to log the user off when using the web service.
                if (Form.ActiveForm.GetType() == FormLogOn.class)
                {
                    IsFormLogOnLastActive = true;
                }
                else
                {
                    IsFormLogOnLastActive = false;
                } 
                return ;
            } 
        }  
        DateTime dtDeadline = dateTimeLastActivity + TimeSpan.FromMinutes((double)PrefC.getInt(PrefName.SecurityLogOffAfterMinutes));
        //Debug.WriteLine("Now:"+DateTime.Now.ToLongTimeString()+", Deadline:"+dtDeadline.ToLongTimeString());
        if (DateTime.Now < dtDeadline)
        {
            return ;
        }
         
        if (Security.getCurUser() == null)
        {
            return ;
        }
         
        //nobody logged on
        //The above check works unless using web service.  With web service, CurUser is not set to null when FormLogOn is shown.
        if (IsFormLogOnLastActive)
        {
            return ;
        }
         
        //Don't try to log off a user that is already logged off.
        FormLogoffWarning formW = new FormLogoffWarning();
        formW.ShowDialog();
        //User could be working outside of OD and the Log On window will never become "active" so we set it here for a fail safe.
        IsFormLogOnLastActive = true;
        if (formW.DialogResult != DialogResult.OK)
        {
            dateTimeLastActivity = DateTime.Now;
            return ;
        }
         
        try
        {
            //user hit cancel, so don't log off
            logOffNow();
        }
        catch (Exception __dummyCatchVar18)
        {
        }
    
    }

    private void formOpenDental_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        //This has a few extra lines because my mouse during testing was firing a MouseMove every second,
        //even if no movement.  So this tests to see if there was actually movement.
        if (locationMouseForLogoff == e.Location)
        {
            return ;
        }
         
        //mouse hasn't moved
        locationMouseForLogoff = e.Location;
        dateTimeLastActivity = DateTime.Now;
    }

    private void logOffNow() throws Exception {
        for (int f = Application.OpenForms.Count - 1;f >= 0;f--)
        {
            //Loop backwards so we don't get an array out of bounds error
            if (Application.OpenForms[f] == this)
            {
                continue;
            }
             
            // main form
            Form openForm = Application.OpenForms[f];
            //Copy so we have a reference to it after we close it.
            openForm.Hide();
            //Currently there is no way to tell if LogOffNow got called from a user initiating the log off, or if this is the auto log off feature.
            //Therefore, when the auto log off feature is enabled, we will forcefully close all forms because some forms might have pop ups within FormClosing prevent the form from closing.
            //That introduces the possibility of sensitive information staying visible in the background while the program waits for user input.
            if (PrefC.getLong(PrefName.SecurityLogOffAfterMinutes) > 0)
            {
                //If the auto-log off feature is enabled, force close all forms.
                openForm.Dispose();
            }
            else
            {
                //Gracefully close each window.  If a window requesting attention causes the form to stay open.  Stop the log off event because the user chose to.
                openForm.Close();
                //Attempt to close the form
                if (openForm.IsDisposed == false)
                {
                    //If the form was not closed.
                    //E.g. The wiki edit window will ask users if they want to lose their work or continue working.  This will get hit if they chose to continue working.
                    openForm.Show();
                    return ;
                }
                 
            } 
        }
        //Show that form again
        //Stop logging off
        LastModule = myOutlookBar.SelectedIndex;
        myOutlookBar.SelectedIndex = -1;
        myOutlookBar.Invalidate();
        unselectActive();
        allNeutral();
        if (userControlTasks1.Visible)
        {
            userControlTasks1.clearLogOff();
        }
         
        Userod oldUser = Security.getCurUser();
        Security.setCurUser(null);
        Text = PatientL.getMainTitle(null);
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Security.setCurUser(oldUser);
        }
         
        //so that the queries in FormLogOn() will work for the web service, since the web service requires a valid user to run queries.
        FormLogOn_ = new FormLogOn();
        FormLogOn_.ShowDialog(this);
        if (FormLogOn_.DialogResult == DialogResult.Cancel)
        {
            Application.Exit();
            return ;
        }
         
        myOutlookBar.SelectedIndex = Security.getModule(LastModule);
        myOutlookBar.Invalidate();
        setModuleSelected();
        if (CurPatNum == 0)
        {
            Text = PatientL.getMainTitle(null);
        }
        else
        {
            Patient pat = Patients.getPat(CurPatNum);
            Text = PatientL.getMainTitle(pat);
        } 
        if (userControlTasks1.Visible)
        {
            userControlTasks1.initializeOnStartup();
        }
         
        //User logged back in so log on form is no longer the active window.
        IsFormLogOnLastActive = false;
        dateTimeLastActivity = DateTime.Now;
        if (myOutlookBar.SelectedIndex == -1)
        {
            MsgBox.show(this,"You do not have permission to use any modules.");
        }
         
    }

    //The purpose of the 11 methods below is to have every child control and child form report MouseMove events to this parent form.
    //It must be dynamic and recursive.
    protected void onControlAdded(ControlEventArgs e) throws Exception {
        attachMouseMoveToControl(e.Control);
        super.OnControlAdded(e);
    }

    protected void onControlRemoved(ControlEventArgs e) throws Exception {
        detachMouseMoveFromControl(e.Control);
        super.OnControlRemoved(e);
    }

    private void attachMouseMoveToForm(Form form) throws Exception {
        form.MouseMove += new MouseEventHandler(ChildForm_MouseMove);
        AttachMouseMoveToChildren(form);
    }

    private void attachMouseMoveToControl(Control control) throws Exception {
        control.MouseMove += new MouseEventHandler(Child_MouseMove);
        control.ControlAdded += new ControlEventHandler(Child_ControlAdded);
        control.ControlRemoved += new ControlEventHandler(Child_ControlRemoved);
        attachMouseMoveToChildren(control);
    }

    private void attachMouseMoveToChildren(Control parent) throws Exception {
        for (Object __dummyForeachVar6 : parent.Controls)
        {
            Control child = (Control)__dummyForeachVar6;
            attachMouseMoveToControl(child);
        }
    }

    private void detachMouseMoveFromControl(Control control) throws Exception {
        detachMouseMoveFromChildren(control);
        control.MouseMove -= new MouseEventHandler(Child_MouseMove);
        control.ControlAdded -= new ControlEventHandler(Child_ControlAdded);
        control.ControlRemoved -= new ControlEventHandler(Child_ControlRemoved);
    }

    private void detachMouseMoveFromChildren(Control parent) throws Exception {
        for (Object __dummyForeachVar7 : parent.Controls)
        {
            Control child = (Control)__dummyForeachVar7;
            detachMouseMoveFromControl(child);
        }
    }

    private void child_ControlAdded(Object sender, ControlEventArgs e) throws Exception {
        attachMouseMoveToControl(e.Control);
    }

    private void child_ControlRemoved(Object sender, ControlEventArgs e) throws Exception {
        detachMouseMoveFromControl(e.Control);
    }

    private void child_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        Point loc = e.Location;
        Control child = (Control)sender;
        do
        {
            //Debug.WriteLine("Child_MouseMove:"+DateTime.Now.ToLongTimeString()+", sender:"+child.Name);
            loc.Offset(child.Left, child.Top);
            child = child.Parent;
        }
        while (child.Parent != null);
        //When it hits a form, either this one or any other active form.
        MouseEventArgs newArgs = new MouseEventArgs(e.Button, e.Clicks, loc.X, loc.Y, e.Delta);
        OnMouseMove(newArgs);
    }

    private void childForm_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        Point loc = e.Location;
        Form childForm = (Form)sender;
        loc.Offset(childForm.Left, childForm.Top);
        MouseEventArgs newArgs = new MouseEventArgs(e.Button, e.Clicks, loc.X, loc.Y, e.Delta);
        OnMouseMove(newArgs);
    }

    private void systemEvents_SessionSwitch(Object sender, SessionSwitchEventArgs e) throws Exception {
        if (e.Reason != SessionSwitchReason.SessionLock)
        {
            return ;
        }
         
        if (!PrefC.getBool(PrefName.SecurityLogOffWithWindows))
        {
            return ;
        }
         
        if (Security.getCurUser() == null)
        {
            return ;
        }
         
        //not sure if this is a good test.
        //simply copied and pasted code from logoff menu click for testing.
        LastModule = myOutlookBar.SelectedIndex;
        myOutlookBar.SelectedIndex = -1;
        myOutlookBar.Invalidate();
        unselectActive();
        allNeutral();
        if (FormLogOn_ != null)
        {
            //To prevent multiple log on screens from showing.
            FormLogOn_.Dispose();
        }
         
        FormLogOn_ = new FormLogOn();
        FormLogOn_.ShowDialog(this);
        //Passing "this" brings FormL to the front when user logs back in.
        if (FormLogOn_.DialogResult == DialogResult.Cancel)
        {
            Application.Exit();
            return ;
        }
         
        myOutlookBar.SelectedIndex = Security.getModule(LastModule);
        myOutlookBar.Invalidate();
        setModuleSelected();
        if (CurPatNum == 0)
        {
            Text = PatientL.getMainTitle(null);
        }
        else
        {
            Patient pat = Patients.getPat(CurPatNum);
            Text = PatientL.getMainTitle(pat);
        } 
        if (userControlTasks1.Visible)
        {
            userControlTasks1.initializeOnStartup();
        }
         
        if (myOutlookBar.SelectedIndex == -1)
        {
            MsgBox.show(this,"You do not have permission to use any modules.");
        }
         
    }

    private void formOpenDental_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        try
        {
            Programs.scrubExportedPatientData();
        }
        catch (Exception __dummyCatchVar19)
        {
        }

        //Required for EHR module d.7.
        try
        {
            //Can happen if cancel is clicked in Choose Database window.
            Computers.ClearHeartBeat(Environment.MachineName);
        }
        catch (Exception __dummyCatchVar20)
        {
        }

        FormUAppoint.abortThread();
        //ICat.AbortThread
        //earlier, this wasn't working.  But I haven't tested it since moving it from Closing to FormClosing.
        if (ThreadCommandLine != null)
        {
            ThreadCommandLine.Abort();
        }
         
        if (ThreadVM != null)
        {
            ThreadVM.Abort();
            ThreadVM.Join();
            ThreadVM = null;
        }
         
        if (_isEmailThreadRunning)
        {
            _isEmailThreadRunning = false;
            _emailSleep.Set();
            //If the thread is just sitting around waiting for the next email check interval, then this will cause the thread to exit immediately.
            //We used to show a message on exit because our email inbox used to delete messages after downloading. We only copy messages now, so we can kill the thread at any time.
            //FormAlertSimple formAS=new FormAlertSimple(Lan.g(this,"Shutting down.\r\nPlease wait while email finishes downloading.\r\nMay take up to 3 minutes to complete."));
            //formAS.Show();//Non-modal, so the program will not wait for user input before closing.
            ThreadEmailInbox.Abort();
            ThreadEmailInbox.Join();
            //We must wait for the thread to die, so that the .exe is freed up if the user is trying to update OD.
            ThreadEmailInbox = null;
        }
         
        if (_isTimeSynchThreadRunning)
        {
            _isTimeSynchThreadRunning = false;
            _timeSynchSleep.Set();
            _threadTimeSynch.Abort();
            _threadTimeSynch.Join();
            _threadTimeSynch = null;
        }
         
    }

    //if(PrefC.GetBool(PrefName.DistributorKey)) {//for OD HQ
    //  for(int f=Application.OpenForms.Count-1;f>=0;f--) {
    //    if(Application.OpenForms[f]==this) {// main form
    //      continue;
    //    }
    //    Application.OpenForms[f].Close();
    //  }
    //}
    private void formOpenDental_FormClosed(Object sender, FormClosedEventArgs e) throws Exception {
        //Cleanup all resources related to the program which have their Dispose methods properly defined.
        //This helps ensure that the chart module and its tooth chart wrapper are properly disposed of in particular.
        //This step is necessary so that graphics memory does not fill up.
        Dispose();
    }

}


