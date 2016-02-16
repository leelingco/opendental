//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:32 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormDefinitions;
import OpenDental.FormFeeEdit;
import OpenDental.FormFeeScheds;
import OpenDental.FormFeeSchedTools;
import OpenDental.FormProcCodeEdit;
import OpenDental.FormProcCodeNew;
import OpenDental.FormProcCodes;
import OpenDental.FormProcTools;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormProcCodes  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    /**
    * If IsSelectionMode=true and DialogResult=OK, then this will contain the selected CodeNum.
    */
    public long SelectedCodeNum = new long();
    //public string SelectedADA;
    private System.Windows.Forms.ListBox listFeeSched = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelFeeSched = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butNew;
    private OpenDental.UI.Button butEditFeeSched;
    private OpenDental.UI.Button butTools;
    private System.Windows.Forms.GroupBox groupFeeScheds = new System.Windows.Forms.GroupBox();
    private boolean changed = new boolean();
    private ListBox listCategories = new ListBox();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private TextBox textDescription = new TextBox();
    private OpenDental.UI.Button butEditCategories;
    private TextBox textCode = new TextBox();
    private Label label3 = new Label();
    private OpenDental.UI.ODGrid gridMain;
    private CheckBox checkShowHidden = new CheckBox();
    private DataTable ProcTable = new DataTable();
    private TextBox textAbbreviation = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butAll;
    /**
    * Set to true externally in order to let user select one procedure code.
    */
    public boolean IsSelectionMode = new boolean();
    private ComboBox comboCompare1 = new ComboBox();
    private Label label5 = new Label();
    private ComboBox comboCompare2 = new ComboBox();
    private OpenDental.UI.Button butImport;
    private OpenDental.UI.Button butExport;
    private GroupBox groupProcCodeSetup = new GroupBox();
    private OpenDental.UI.Button butProcTools;
    private OpenDental.UI.Button butShowHiddenDefault;
    /**
    * The list of definitions that is currently showing in the category list.
    */
    private Def[] CatList = new Def[]();
    /**
    * 
    */
    public FormProcCodes() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcCodes.class);
        this.listFeeSched = new System.Windows.Forms.ListBox();
        this.labelFeeSched = new System.Windows.Forms.Label();
        this.groupFeeScheds = new System.Windows.Forms.GroupBox();
        this.listCategories = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textAbbreviation = new System.Windows.Forms.TextBox();
        this.textCode = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkShowHidden = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.comboCompare1 = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.comboCompare2 = new System.Windows.Forms.ComboBox();
        this.groupProcCodeSetup = new System.Windows.Forms.GroupBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butProcTools = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.butNew = new OpenDental.UI.Button();
        this.butShowHiddenDefault = new OpenDental.UI.Button();
        this.butAll = new OpenDental.UI.Button();
        this.butEditCategories = new OpenDental.UI.Button();
        this.butTools = new OpenDental.UI.Button();
        this.butEditFeeSched = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupFeeScheds.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.groupProcCodeSetup.SuspendLayout();
        this.SuspendLayout();
        //
        // listFeeSched
        //
        this.listFeeSched.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.listFeeSched.Location = new System.Drawing.Point(778, 24);
        this.listFeeSched.Name = "listFeeSched";
        this.listFeeSched.Size = new System.Drawing.Size(200, 511);
        this.listFeeSched.TabIndex = 6;
        this.listFeeSched.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listFeeSched_MouseDown);
        //
        // labelFeeSched
        //
        this.labelFeeSched.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelFeeSched.Location = new System.Drawing.Point(774, 4);
        this.labelFeeSched.Name = "labelFeeSched";
        this.labelFeeSched.Size = new System.Drawing.Size(132, 17);
        this.labelFeeSched.TabIndex = 12;
        this.labelFeeSched.Text = "View Fee Sched";
        this.labelFeeSched.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupFeeScheds
        //
        this.groupFeeScheds.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.groupFeeScheds.Controls.Add(this.butTools);
        this.groupFeeScheds.Controls.Add(this.butEditFeeSched);
        this.groupFeeScheds.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupFeeScheds.Location = new System.Drawing.Point(778, 603);
        this.groupFeeScheds.Name = "groupFeeScheds";
        this.groupFeeScheds.Size = new System.Drawing.Size(200, 51);
        this.groupFeeScheds.TabIndex = 14;
        this.groupFeeScheds.TabStop = false;
        //
        // listCategories
        //
        this.listCategories.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listCategories.FormattingEnabled = true;
        this.listCategories.Location = new System.Drawing.Point(10, 126);
        this.listCategories.Name = "listCategories";
        this.listCategories.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listCategories.Size = new System.Drawing.Size(145, 368);
        this.listCategories.TabIndex = 15;
        this.listCategories.MouseUp += new System.Windows.Forms.MouseEventHandler(this.listCategories_MouseUp);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 100);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(80, 23);
        this.label1.TabIndex = 16;
        this.label1.Text = "By Category";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(3, 42);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(79, 20);
        this.label2.TabIndex = 17;
        this.label2.Text = "By Descript";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox1.Controls.Add(this.butShowHiddenDefault);
        this.groupBox1.Controls.Add(this.textDescription);
        this.groupBox1.Controls.Add(this.textAbbreviation);
        this.groupBox1.Controls.Add(this.textCode);
        this.groupBox1.Controls.Add(this.butAll);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.checkShowHidden);
        this.groupBox1.Controls.Add(this.butEditCategories);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.listCategories);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Location = new System.Drawing.Point(2, 16);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(165, 568);
        this.groupBox1.TabIndex = 0;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Search";
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(82, 43);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(73, 20);
        this.textDescription.TabIndex = 0;
        this.textDescription.TextChanged += new System.EventHandler(this.textDescription_TextChanged);
        //
        // textAbbreviation
        //
        this.textAbbreviation.Location = new System.Drawing.Point(82, 17);
        this.textAbbreviation.Name = "textAbbreviation";
        this.textAbbreviation.Size = new System.Drawing.Size(73, 20);
        this.textAbbreviation.TabIndex = 3;
        this.textAbbreviation.TextChanged += new System.EventHandler(this.textAbbreviation_TextChanged);
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(82, 69);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(73, 20);
        this.textCode.TabIndex = 1;
        this.textCode.TextChanged += new System.EventHandler(this.textCode_TextChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(3, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(79, 20);
        this.label4.TabIndex = 22;
        this.label4.Text = "By Abbrev";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowHidden
        //
        this.checkShowHidden.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.checkShowHidden.Location = new System.Drawing.Point(10, 545);
        this.checkShowHidden.Name = "checkShowHidden";
        this.checkShowHidden.Size = new System.Drawing.Size(90, 17);
        this.checkShowHidden.TabIndex = 20;
        this.checkShowHidden.Text = "Show Hidden";
        this.checkShowHidden.UseVisualStyleBackColor = true;
        this.checkShowHidden.Click += new System.EventHandler(this.checkShowHidden_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 68);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(79, 20);
        this.label3.TabIndex = 19;
        this.label3.Text = "By Code";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCompare1
        //
        this.comboCompare1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.comboCompare1.FormattingEnabled = true;
        this.comboCompare1.Location = new System.Drawing.Point(778, 554);
        this.comboCompare1.Name = "comboCompare1";
        this.comboCompare1.Size = new System.Drawing.Size(200, 21);
        this.comboCompare1.TabIndex = 20;
        this.comboCompare1.SelectionChangeCommitted += new System.EventHandler(this.comboCompare1_SelectionChangeCommitted);
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label5.Location = new System.Drawing.Point(776, 534);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(199, 17);
        this.label5.TabIndex = 21;
        this.label5.Text = "Compare Fee Schedules";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboCompare2
        //
        this.comboCompare2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.comboCompare2.FormattingEnabled = true;
        this.comboCompare2.Location = new System.Drawing.Point(778, 577);
        this.comboCompare2.Name = "comboCompare2";
        this.comboCompare2.Size = new System.Drawing.Size(200, 21);
        this.comboCompare2.TabIndex = 22;
        this.comboCompare2.SelectionChangeCommitted += new System.EventHandler(this.comboCompare2_SelectionChangeCommitted);
        //
        // groupProcCodeSetup
        //
        this.groupProcCodeSetup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupProcCodeSetup.Controls.Add(this.butProcTools);
        this.groupProcCodeSetup.Controls.Add(this.butImport);
        this.groupProcCodeSetup.Controls.Add(this.butExport);
        this.groupProcCodeSetup.Controls.Add(this.butNew);
        this.groupProcCodeSetup.Location = new System.Drawing.Point(2, 603);
        this.groupProcCodeSetup.Name = "groupProcCodeSetup";
        this.groupProcCodeSetup.Size = new System.Drawing.Size(165, 91);
        this.groupProcCodeSetup.TabIndex = 26;
        this.groupProcCodeSetup.TabStop = false;
        this.groupProcCodeSetup.Text = "Procedure Codes";
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(170, 8);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(604, 686);
        this.gridMain.TabIndex = 19;
        this.gridMain.setTitle("Procedures");
        this.gridMain.setTranslationName("TableProcedures");
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
        this.gridMain.CellLeave = __MultiODGridClickEventHandler.combine(this.gridMain.CellLeave,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellLeave(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butProcTools
        //
        this.butProcTools.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProcTools.setAutosize(true);
        this.butProcTools.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProcTools.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProcTools.setCornerRadius(4F);
        this.butProcTools.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProcTools.Location = new System.Drawing.Point(6, 57);
        this.butProcTools.Name = "butProcTools";
        this.butProcTools.Size = new System.Drawing.Size(75, 26);
        this.butProcTools.TabIndex = 25;
        this.butProcTools.Text = "Tools";
        this.butProcTools.Click += new System.EventHandler(this.butProcTools_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butImport.Location = new System.Drawing.Point(6, 19);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 26);
        this.butImport.TabIndex = 23;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExport.Location = new System.Drawing.Point(85, 19);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(75, 26);
        this.butExport.TabIndex = 24;
        this.butExport.Text = "Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Image = Resources.getAdd();
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(85, 57);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(75, 26);
        this.butNew.TabIndex = 22;
        this.butNew.Text = "&New";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // butShowHiddenDefault
        //
        this.butShowHiddenDefault.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowHiddenDefault.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butShowHiddenDefault.setAutosize(true);
        this.butShowHiddenDefault.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowHiddenDefault.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowHiddenDefault.setCornerRadius(4F);
        this.butShowHiddenDefault.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butShowHiddenDefault.Location = new System.Drawing.Point(100, 542);
        this.butShowHiddenDefault.Name = "butShowHiddenDefault";
        this.butShowHiddenDefault.Size = new System.Drawing.Size(56, 20);
        this.butShowHiddenDefault.TabIndex = 25;
        this.butShowHiddenDefault.Text = "default";
        this.butShowHiddenDefault.Click += new System.EventHandler(this.butShowHiddenDefault_Click);
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(93, 100);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(62, 25);
        this.butAll.TabIndex = 7;
        this.butAll.Text = "All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // butEditCategories
        //
        this.butEditCategories.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditCategories.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEditCategories.setAutosize(true);
        this.butEditCategories.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditCategories.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditCategories.setCornerRadius(4F);
        this.butEditCategories.Location = new System.Drawing.Point(10, 513);
        this.butEditCategories.Name = "butEditCategories";
        this.butEditCategories.Size = new System.Drawing.Size(94, 26);
        this.butEditCategories.TabIndex = 21;
        this.butEditCategories.Text = "Edit Categories";
        this.butEditCategories.Click += new System.EventHandler(this.butEditCategories_Click);
        //
        // butTools
        //
        this.butTools.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTools.setAutosize(true);
        this.butTools.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTools.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTools.setCornerRadius(4F);
        this.butTools.Location = new System.Drawing.Point(109, 16);
        this.butTools.Name = "butTools";
        this.butTools.Size = new System.Drawing.Size(81, 26);
        this.butTools.TabIndex = 14;
        this.butTools.Text = "Fee Tools";
        this.butTools.Click += new System.EventHandler(this.butTools_Click);
        //
        // butEditFeeSched
        //
        this.butEditFeeSched.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditFeeSched.setAutosize(true);
        this.butEditFeeSched.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditFeeSched.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditFeeSched.setCornerRadius(4F);
        this.butEditFeeSched.Location = new System.Drawing.Point(12, 16);
        this.butEditFeeSched.Name = "butEditFeeSched";
        this.butEditFeeSched.Size = new System.Drawing.Size(81, 26);
        this.butEditFeeSched.TabIndex = 13;
        this.butEditFeeSched.Text = "Fee Scheds";
        this.butEditFeeSched.Click += new System.EventHandler(this.butEditFeeSched_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(889, 668);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 5;
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
        this.butOK.Location = new System.Drawing.Point(794, 668);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormProcCodes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(982, 707);
        this.Controls.Add(this.groupProcCodeSetup);
        this.Controls.Add(this.listFeeSched);
        this.Controls.Add(this.comboCompare2);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboCompare1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupFeeScheds);
        this.Controls.Add(this.labelFeeSched);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(990, 734);
        this.Name = "FormProcCodes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Codes";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProcedures_Closing);
        this.Load += new System.EventHandler(this.FormProcCodes_Load);
        this.groupFeeScheds.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupProcCodeSetup.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formProcCodes_Load(Object sender, System.EventArgs e) throws Exception {
        checkShowHidden.Checked = PrefC.getBool(PrefName.ProcCodeListShowHidden);
        if (!Security.IsAuthorized(Permissions.Setup, DateTime.MinValue, true))
        {
            groupFeeScheds.Visible = false;
            butEditCategories.Visible = false;
            groupProcCodeSetup.Visible = false;
        }
         
        if (!Security.IsAuthorized(Permissions.SecurityAdmin, DateTime.MinValue, true))
        {
            butProcTools.Visible = false;
        }
         
        if (!IsSelectionMode)
        {
            butOK.Visible = false;
            butCancel.Text = Lan.g(this,"Close");
        }
         
        fillCats();
        for (int i = 0;i < listCategories.Items.Count;i++)
        {
            listCategories.SetSelected(i, true);
        }
        fillFeeSchedules();
        fillGrid();
    }

    //this.textDescription.Focus();
    private void fillFeeSchedules() throws Exception {
        listFeeSched.Items.Clear();
        String str = new String();
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            str = FeeSchedC.getListShort()[i].Description;
            if (FeeSchedC.getListShort()[i].FeeSchedType != FeeScheduleType.Normal)
            {
                str += " (" + FeeSchedC.getListShort()[i].FeeSchedType.ToString() + ")";
            }
             
            listFeeSched.Items.Add(str);
        }
        if (listFeeSched.Items.Count > 0)
        {
            listFeeSched.SelectedIndex = 0;
        }
         
        comboCompare1.Items.Clear();
        comboCompare1.Items.Add(Lan.g(this,"none"));
        comboCompare1.SelectedIndex = 0;
        comboCompare2.Items.Clear();
        comboCompare2.Items.Add(Lan.g(this,"none"));
        comboCompare2.SelectedIndex = 0;
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            str = FeeSchedC.getListShort()[i].Description;
            if (FeeSchedC.getListShort()[i].FeeSchedType != FeeScheduleType.Normal)
            {
                str += " (" + FeeSchedC.getListShort()[i].FeeSchedType.ToString() + ")";
            }
             
            comboCompare1.Items.Add(str);
            comboCompare2.Items.Add(str);
        }
    }

    private void fillCats() throws Exception {
        ArrayList selected = new ArrayList();
        for (int i = 0;i < listCategories.SelectedIndices.Count;i++)
        {
            selected.Add(CatList[listCategories.SelectedIndices[i]].DefNum);
        }
        if (checkShowHidden.Checked)
        {
            CatList = DefC.getLong()[((Enum)DefCat.ProcCodeCats).ordinal()];
        }
        else
        {
            CatList = DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()];
        } 
        listCategories.Items.Clear();
        for (int i = 0;i < CatList.Length;i++)
        {
            listCategories.Items.Add(CatList[i].ItemName);
            if (selected.Contains(CatList[i].DefNum))
            {
                listCategories.SetSelected(i, true);
            }
             
        }
    }

    private void fillGrid() throws Exception {
        if (listFeeSched.Items.Count == 0)
        {
            gridMain.beginUpdate();
            gridMain.getRows().Clear();
            gridMain.endUpdate();
            MsgBox.show(this,"You must have at least one fee schedule created.");
            return ;
        }
         
        String selected = "";
        if (gridMain.getSelectedIndex() != -1)
        {
            selected = ProcTable.Rows[gridMain.getSelectedIndex()][3].ToString();
        }
         
        int scroll = gridMain.getScrollValue();
        List<long> cats = new List<long>();
        for (int i = 0;i < listCategories.SelectedIndices.Count;i++)
        {
            cats.Add(CatList[listCategories.SelectedIndices[i]].DefNum);
        }
        long feeSched = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum;
        long feeSchedComp1 = 0;
        if (comboCompare1.SelectedIndex != 0)
        {
            feeSchedComp1 = FeeSchedC.getListShort()[comboCompare1.SelectedIndex - 1].FeeSchedNum;
        }
         
        long feeSchedComp2 = 0;
        if (comboCompare2.SelectedIndex != 0)
        {
            feeSchedComp2 = FeeSchedC.getListShort()[comboCompare2.SelectedIndex - 1].FeeSchedNum;
        }
         
        ProcTable = ProcedureCodes.GetProcTable(textAbbreviation.Text, textDescription.Text, textCode.Text, cats, feeSched, feeSchedComp1, feeSchedComp2);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProcedures","Category"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcedures","Description"),206);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcedures","Abbr"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcedures","Code"),50);
        gridMain.getColumns().add(col);
        String heading = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].Description;
        if (heading.Length > 8)
        {
            heading = heading.Substring(0, 8);
        }
         
        col = new ODGridColumn(heading, 50, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        heading = "";
        if (comboCompare1.SelectedIndex != 0)
        {
            heading = FeeSchedC.getListShort()[comboCompare1.SelectedIndex - 1].Description;
        }
         
        if (heading.Length > 8)
        {
            heading = heading.Substring(0, 8);
        }
         
        col = new ODGridColumn(heading, 50, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        heading = "";
        if (comboCompare2.SelectedIndex != 0)
        {
            heading = FeeSchedC.getListShort()[comboCompare2.SelectedIndex - 1].Description;
        }
         
        if (heading.Length > 8)
        {
            heading = heading.Substring(0, 8);
        }
         
        col = new ODGridColumn(heading, 50, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ProcTable.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (i == 0 || ProcTable.Rows[i - 1]["ProcCat"].ToString() != ProcTable.Rows[i]["ProcCat"].ToString())
            {
                row.getCells().Add(DefC.GetName(DefCat.ProcCodeCats, PIn.Long(ProcTable.Rows[i]["ProcCat"].ToString())));
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ProcTable.Rows[i]["Descript"].ToString());
            row.getCells().Add(ProcTable.Rows[i]["AbbrDesc"].ToString());
            row.getCells().Add(ProcTable.Rows[i]["ProcCode"].ToString());
            if (StringSupport.equals(ProcTable.Rows[i]["FeeAmt1"].ToString(), "-1"))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PIn.Double(ProcTable.Rows[i]["FeeAmt1"].ToString()).ToString("n"));
            } 
            if (StringSupport.equals(ProcTable.Rows[i]["FeeAmt2"].ToString(), "-1"))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PIn.Double(ProcTable.Rows[i]["FeeAmt2"].ToString()).ToString("n"));
            } 
            if (StringSupport.equals(ProcTable.Rows[i]["FeeAmt3"].ToString(), "-1"))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PIn.Double(ProcTable.Rows[i]["FeeAmt3"].ToString()).ToString("n"));
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setScrollValue(scroll);
        if (!StringSupport.equals(selected, ""))
        {
            for (int i = 0;i < ProcTable.Rows.Count;i++)
            {
                //if a row was previously selected
                if (StringSupport.equals(ProcTable.Rows[i][3].ToString(), selected))
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void butAll_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < listCategories.Items.Count;i++)
        {
            listCategories.SetSelected(i, true);
        }
        fillGrid();
    }

    private void butEditCategories_Click(Object sender, EventArgs e) throws Exception {
        //won't even be visible if no permission
        ArrayList selected = new ArrayList();
        for (int i = 0;i < listCategories.SelectedIndices.Count;i++)
        {
            selected.Add(CatList[listCategories.SelectedIndices[i]].DefNum);
        }
        FormDefinitions FormD = new FormDefinitions(DefCat.ProcCodeCats);
        FormD.ShowDialog();
        DataValid.setInvalid(InvalidType.Defs);
        changed = true;
        fillCats();
        for (int i = 0;i < CatList.Length;i++)
        {
            if (selected.Contains(CatList[i].DefNum))
            {
                listCategories.SetSelected(i, true);
            }
             
        }
        //we need to move security log to within the definition window for more complete tracking
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Definitions");
        fillGrid();
    }

    private void textAbbreviation_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textDescription_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textCode_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void listCategories_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        fillGrid();
    }

    private void checkShowHidden_Click(Object sender, EventArgs e) throws Exception {
        fillCats();
        fillGrid();
    }

    private void butShowHiddenDefault_Click(Object sender, EventArgs e) throws Exception {
        Prefs.UpdateBool(PrefName.ProcCodeListShowHidden, checkShowHidden.Checked);
        String hiddenStatus = "";
        if (checkShowHidden.Checked)
        {
            hiddenStatus = "checked.";
        }
        else
        {
            hiddenStatus = "unchecked.";
        } 
        MessageBox.Show(Lan.g(this,"Show Hidden will default to ") + hiddenStatus);
    }

    private void listFeeSched_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        fillGrid();
    }

    private void comboCompare1_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboCompare2_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butEditFeeSched_Click(Object sender, System.EventArgs e) throws Exception {
        //won't even be visible if no permission
        long selectedSched = 0;
        if (listFeeSched.SelectedIndex != -1)
        {
            selectedSched = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum;
        }
         
        FormFeeScheds FormF = new FormFeeScheds();
        FormF.ShowDialog();
        DataValid.setInvalid(InvalidType.FeeScheds,InvalidType.Fees,InvalidType.ProcCodes);
        //Fees.Refresh();
        //ProcedureCodes.RefreshCache();
        changed = true;
        fillFeeSchedules();
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            if (FeeSchedC.getListShort()[i].FeeSchedNum == selectedSched)
            {
                listFeeSched.SelectedIndex = i;
            }
             
        }
        fillGrid();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Fee Schedules");
    }

    //FillGrid();//will be done automatically because of lines above
    private void butTools_Click(Object sender, System.EventArgs e) throws Exception {
        FormFeeSchedTools FormF = new FormFeeSchedTools(FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum);
        FormF.ShowDialog();
        if (FormF.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        Fees.refreshCache();
        ProcedureCodes.refreshCache();
        changed = true;
        if (Programs.isEnabled(ProgramName.eClinicalWorks))
        {
            fillFeeSchedules();
        }
         
        //To show possible added fee schedule.
        fillGrid();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Fee Schedule Tools");
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        if (ProcTable.Rows.Count == 0)
        {
            MsgBox.show(this,"No procedurecodes are displayed for export.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Only the codes showing in this list will be exported.  Continue?"))
        {
            return ;
        }
         
        List<ProcedureCode> listCodes = new List<ProcedureCode>();
        for (int i = 0;i < ProcTable.Rows.Count;i++)
        {
            if (StringSupport.equals(ProcTable.Rows[i]["ProcCode"].ToString(), ""))
            {
                continue;
            }
             
            listCodes.Add(ProcedureCodes.GetProcCode(ProcTable.Rows[i]["ProcCode"].ToString()));
        }
        //ClaimForm ClaimFormCur=ClaimForms.ListLong[listClaimForms.SelectedIndex];
        SaveFileDialog saveDlg = new SaveFileDialog();
        String filename = "ProcCodes.xml";
        saveDlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        saveDlg.FileName = filename;
        if (saveDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        //MessageBox.Show(saveDlg.FileName);
        XmlSerializer serializer = new XmlSerializer(List<ProcedureCode>.class);
        TextWriter writer = new StreamWriter(saveDlg.FileName);
        serializer.Serialize(writer, listCodes);
        writer.Close();
        MessageBox.Show("Exported");
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog openDlg = new OpenFileDialog();
        openDlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        if (openDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        int rowsInserted = 0;
        try
        {
            rowsInserted = ImportProcCodes(openDlg.FileName, null, "");
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            fillGrid();
            return ;
        }

        MessageBox.Show("Procedure codes inserted: " + rowsInserted);
        DataValid.setInvalid(InvalidType.Defs,InvalidType.ProcCodes,InvalidType.Fees);
        changed = true;
        fillCats();
        fillGrid();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Imported Procedure Codes");
    }

    /**
    * Can be called externally.  Surround with try catch.  Returns number of codes inserted.  Supply path to file to import or a list of procedure codes, or an xml string.  Make sure to set the other two values blank or null.
    */
    public static int importProcCodes(String path, List<ProcedureCode> listCodes, String xmlData) throws Exception {
        if (listCodes == null)
        {
            listCodes = new List<ProcedureCode>();
        }
         
        //xmlData should already be tested ahead of time to make sure it's not blank.
        XmlSerializer serializer = new XmlSerializer(List<ProcedureCode>.class);
        if (!StringSupport.equals(path, ""))
        {
            if (!File.Exists(path))
            {
                throw new ApplicationException(Lan.g("FormProcCodes","File does not exist."));
            }
             
            try
            {
                TextReader reader = new StreamReader(path);
                try
                {
                    {
                        listCodes = (List<ProcedureCode>)serializer.Deserialize(reader);
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new ApplicationException(Lan.g("FormProcCodes","Invalid file format"));
            }
        
        }
        else if (!StringSupport.equals(xmlData, ""))
        {
            try
            {
                TextReader reader = new StringReader(xmlData);
                try
                {
                    {
                        listCodes = (List<ProcedureCode>)serializer.Deserialize(reader);
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
            catch (Exception __dummyCatchVar1)
            {
                throw new ApplicationException(Lan.g("FormProcCodes","xml format"));
            }
        
        }
          
        int retVal = 0;
        for (int i = 0;i < listCodes.Count;i++)
        {
            if (ProcedureCodeC.getHList().ContainsKey(listCodes[i].ProcCode))
            {
                continue;
            }
             
            //don't import duplicates.
            listCodes[i].ProcCat = DefC.GetByExactName(DefCat.ProcCodeCats, listCodes[i].ProcCatDescript);
            if (listCodes[i].ProcCat == 0)
            {
                //no category exists with that name
                Def def = new Def();
                def.Category = DefCat.ProcCodeCats;
                def.ItemName = listCodes[i].ProcCatDescript;
                def.ItemOrder = DefC.getLong()[((Enum)DefCat.ProcCodeCats).ordinal()].Length;
                Defs.insert(def);
                Cache.refresh(InvalidType.Defs);
                listCodes[i].ProcCat = def.DefNum;
            }
             
            ProcedureCodes.Insert(listCodes[i]);
            retVal++;
        }
        return retVal;
    }

    //don't forget to refresh procedurecodes
    private void butProcTools_Click(Object sender, EventArgs e) throws Exception {
        FormProcTools FormP = new FormProcTools();
        FormP.ShowDialog();
        if (FormP.Changed)
        {
            changed = true;
            fillCats();
            fillGrid();
        }
         
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        //won't be visible if no permission
        FormProcCodeNew FormPCN = new FormProcCodeNew();
        FormPCN.ShowDialog();
        if (FormPCN.Changed)
        {
            changed = true;
            ProcedureCodes.refreshCache();
            fillGrid();
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedCodeNum = PIn.Long(ProcTable.Rows[e.getRow()]["CodeNum"].ToString());
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //else not selecting a code
        if (!Security.IsAuthorized(Permissions.Setup, DateTime.MinValue, true))
        {
            return ;
        }
         
        long codeNum = PIn.Long(ProcTable.Rows[e.getRow()]["CodeNum"].ToString());
        //string =ProcTable.Rows[e.Row]["ProcCode"].ToString();
        if (e.getCol() > 3)
        {
            //if double clicked on a fee
            Fee FeeCur = null;
            long feesched = 0;
            if (e.getCol() == 4)
            {
                feesched = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum;
                FeeCur = Fees.getFee(codeNum,feesched);
            }
             
            if (e.getCol() == 5)
            {
                if (comboCompare1.SelectedIndex == 0)
                {
                    return ;
                }
                 
                feesched = FeeSchedC.getListShort()[comboCompare1.SelectedIndex - 1].FeeSchedNum;
                FeeCur = Fees.getFee(codeNum,feesched);
            }
             
            if (e.getCol() == 6)
            {
                if (comboCompare2.SelectedIndex == 0)
                {
                    return ;
                }
                 
                feesched = FeeSchedC.getListShort()[comboCompare2.SelectedIndex - 1].FeeSchedNum;
                FeeCur = Fees.getFee(codeNum,feesched);
            }
             
            FormFeeEdit FormFE = new FormFeeEdit();
            if (FeeCur == null)
            {
                FeeCur = new Fee();
                FeeCur.FeeSched = feesched;
                FeeCur.CodeNum = codeNum;
                Fees.insert(FeeCur);
                //SecurityLog is updated in FormFeeEdit.
                FormFE.IsNew = true;
            }
             
            FormFE.FeeCur = FeeCur;
            FormFE.ShowDialog();
            if (FormFE.DialogResult == DialogResult.OK)
            {
                Fees.refreshCache();
                changed = true;
                fillGrid();
            }
             
        }
        else
        {
            //not on a fee: Edit code instead
            FormProcCodeEdit FormPCE = new FormProcCodeEdit(ProcedureCodes.getProcCodeFromDb(codeNum));
            FormPCE.IsNew = false;
            FormPCE.ShowDialog();
            if (FormPCE.DialogResult == DialogResult.OK)
            {
                //ProcedureCodes.Refresh();
                changed = true;
                //Fees.Refresh();//fees were already refreshed within procCodeEdit
                fillGrid();
            }
             
        } 
    }

    private void gridMain_CellLeave(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //logic only works for column 4.
        long codeNum = PIn.Long(ProcTable.Rows[e.getRow()]["CodeNum"].ToString());
        long feesched = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum;
        Fee fee = Fees.getFee(codeNum,feesched);
        String strOld = "";
        if (fee != null)
        {
            strOld = fee.Amount.ToString("n");
        }
         
        String strNew = gridMain.getRows().get___idx(e.getRow()).getCells()[e.getCol()].Text;
        if (StringSupport.equals(strOld, strNew))
        {
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.Setup))
        {
            //includes dialog
            gridMain.getRows().get___idx(e.getRow()).getCells()[e.getCol()].Text = strOld;
            gridMain.Invalidate();
            return ;
        }
         
        double dNew = -1;
        if (!StringSupport.equals(strNew, ""))
        {
            try
            {
                dNew = PIn.Double(strNew);
            }
            catch (Exception __dummyCatchVar2)
            {
                gridMain.setSelected(new Point(e.getCol(), e.getRow()));
                MessageBox.Show(Lan.g(this,"Please fix data entry error first."));
                return ;
            }

            gridMain.getRows().get___idx(e.getRow()).getCells()[e.getCol()].Text = dNew.ToString("n");
        }
         
        //to standardize formatting.  They probably didn't type .00
        //invalidate doesn't seem to be necessary here
        if (StringSupport.equals(strOld, ""))
        {
            //if no fee was originally entered and since it's no longer empty, then we need to insert a fee.
            //Somehow duplicate fees were being inserted so double check that this fee does not already exist.
            Fee tmpFee = Fees.getFee(codeNum,feesched);
            //Looks in cache.
            if (tmpFee != null)
            {
                return ;
            }
             
            //Fee exists. Must be unknown bug.
            fee = new Fee();
            fee.FeeSched = feesched;
            fee.CodeNum = codeNum;
            fee.Amount = dNew;
            Fees.insert(fee);
            Fees.getListt().Add(fee);
        }
        else
        {
            //if fee existed
            if (StringSupport.equals(strNew, ""))
            {
                //delete old fee
                Fees.delete(fee);
                Fees.getListt().Remove(fee);
            }
            else
            {
                //change fee
                fee.Amount = dNew;
                Fees.update(fee);
                Fees.getListt()[Fees.getListt().IndexOf(fee)].Amount = dNew;
            } 
        } 
        SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + ProcedureCodes.getStringProcCode(fee.CodeNum) + ", " + Lan.g(this,"Fee: ") + "" + fee.Amount.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + ": " + FeeScheds.getDescription(fee.FeeSched) + ". " + Lan.g(this,"Manual edit in grid from Procedure Codes list."), fee.CodeNum);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().Y == -1)
        {
            MsgBox.show(this,"Please select a procedure code first.");
            return ;
        }
         
        SelectedCodeNum = PIn.Long(ProcTable.Rows[gridMain.getSelectedCell().Y]["CodeNum"].ToString());
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formProcedures_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ProcCodes,InvalidType.Fees);
        }
         
    }

}


