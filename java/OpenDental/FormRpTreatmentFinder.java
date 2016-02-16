//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:09 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormRpTreatmentFinder;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormSheetPicker;
import OpenDental.GotoModule;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.SheetFiller;
import OpenDental.SheetPrinting;
import OpenDental.SheetUtil;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Security;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class FormRpTreatmentFinder  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    private Label label1 = new Label();
    private CheckBox checkIncludeNoIns = new CheckBox();
    private OpenDental.UI.ODGrid gridMain;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butRefresh;
    private DataTable table = new DataTable();
    private PrintDocument pd = new PrintDocument();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private ContextMenu contextRightClick = new ContextMenu();
    private MenuItem menuItemFamily = new MenuItem();
    private MenuItem menuItemAccount = new MenuItem();
    private OpenDental.UI.Button butGotoFamily;
    private OpenDental.UI.Button butGotoAccount;
    private OpenDental.ValidDate textDateStart;
    private Label label2 = new Label();
    private Label label4 = new Label();
    private Label label3 = new Label();
    private Label label5 = new Label();
    private TextBox textCodeRange = new TextBox();
    private Label label6 = new Label();
    private OpenDental.UI.Button butLabelSingle;
    private OpenDental.UI.Button butLabelPreview;
    private Label label7 = new Label();
    private OpenDental.UI.Button butLettersPreview;
    private OpenDental.UI.Button buttonExport;
    private Label label8 = new Label();
    private ComboBox comboMonthStart = new ComboBox();
    private OpenDental.ValidDouble textOverAmount;
    private OpenDental.UI.ComboBoxMulti comboBoxMultiProv;
    private OpenDental.UI.ComboBoxMulti comboBoxMultiBilling;
    private int pagesPrinted = new int();
    private CheckBox checkIncludePatsWithApts = new CheckBox();
    private int patientsPrinted = new int();
    /**
    * 
    */
    public FormRpTreatmentFinder() throws Exception {
        initializeComponent();
        Lan.f(this);
        gridMain.ContextMenu = contextRightClick;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpTreatmentFinder.class);
        this.label1 = new System.Windows.Forms.Label();
        this.checkIncludeNoIns = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.comboMonthStart = new System.Windows.Forms.ComboBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textCodeRange = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.contextRightClick = new System.Windows.Forms.ContextMenu();
        this.menuItemFamily = new System.Windows.Forms.MenuItem();
        this.menuItemAccount = new System.Windows.Forms.MenuItem();
        this.checkIncludePatsWithApts = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.buttonExport = new OpenDental.UI.Button();
        this.butLettersPreview = new OpenDental.UI.Button();
        this.butLabelSingle = new OpenDental.UI.Button();
        this.butLabelPreview = new OpenDental.UI.Button();
        this.butGotoAccount = new OpenDental.UI.Button();
        this.butGotoFamily = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.comboBoxMultiBilling = new OpenDental.UI.ComboBoxMulti();
        this.comboBoxMultiProv = new OpenDental.UI.ComboBoxMulti();
        this.textOverAmount = new OpenDental.ValidDouble();
        this.textDateStart = new OpenDental.ValidDate();
        this.butRefresh = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(872, 29);
        this.label1.TabIndex = 29;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // checkIncludeNoIns
        //
        this.checkIncludeNoIns.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludeNoIns.Location = new System.Drawing.Point(31, 14);
        this.checkIncludeNoIns.Name = "checkIncludeNoIns";
        this.checkIncludeNoIns.Size = new System.Drawing.Size(214, 18);
        this.checkIncludeNoIns.TabIndex = 30;
        this.checkIncludeNoIns.Text = "Include patients without insurance";
        this.checkIncludeNoIns.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludeNoIns.UseVisualStyleBackColor = true;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.comboBoxMultiBilling);
        this.groupBox1.Controls.Add(this.comboBoxMultiProv);
        this.groupBox1.Controls.Add(this.textOverAmount);
        this.groupBox1.Controls.Add(this.comboMonthStart);
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textCodeRange);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.butRefresh);
        this.groupBox1.Controls.Add(this.checkIncludePatsWithApts);
        this.groupBox1.Controls.Add(this.checkIncludeNoIns);
        this.groupBox1.Location = new System.Drawing.Point(3, 41);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(916, 83);
        this.groupBox1.TabIndex = 33;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "View";
        //
        // comboMonthStart
        //
        this.comboMonthStart.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboMonthStart.Items.AddRange(new Object[]{ "Calendar Year", "01 - January", "02 - February", "03 - March", "04 - April", "05 - May", "06 - June", "07 - July", "08 - August", "09 - September", "10 - October", "11 - November", "12 - December" });
        this.comboMonthStart.Location = new System.Drawing.Point(346, 32);
        this.comboMonthStart.MaxDropDownItems = 40;
        this.comboMonthStart.Name = "comboMonthStart";
        this.comboMonthStart.Size = new System.Drawing.Size(98, 21);
        this.comboMonthStart.TabIndex = 47;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(31, 58);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(140, 14);
        this.label8.TabIndex = 46;
        this.label8.Text = "Amount remaining over";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(445, 35);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(70, 14);
        this.label7.TabIndex = 43;
        this.label7.Text = "Billing Type";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(758, 34);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(150, 13);
        this.label5.TabIndex = 41;
        this.label5.Text = "Ex: D1050-D1060";
        //
        // textCodeRange
        //
        this.textCodeRange.Location = new System.Drawing.Point(758, 12);
        this.textCodeRange.Name = "textCodeRange";
        this.textCodeRange.Size = new System.Drawing.Size(150, 20);
        this.textCodeRange.TabIndex = 39;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(249, 36);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(93, 14);
        this.label3.TabIndex = 37;
        this.label3.Text = "Ins Month Start";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(679, 12);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(77, 17);
        this.label6.TabIndex = 40;
        this.label6.Text = "Code Range";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(445, 14);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(70, 14);
        this.label4.TabIndex = 35;
        this.label4.Text = "Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(246, 14);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(119, 14);
        this.label2.TabIndex = 33;
        this.label2.Text = "TP Date Since";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // contextRightClick
        //
        this.contextRightClick.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemFamily, this.menuItemAccount });
        //
        // menuItemFamily
        //
        this.menuItemFamily.Index = 0;
        this.menuItemFamily.Text = "See Family";
        this.menuItemFamily.Click += new System.EventHandler(this.menuItemFamily_Click);
        //
        // menuItemAccount
        //
        this.menuItemAccount.Index = 1;
        this.menuItemAccount.Text = "See Account";
        this.menuItemAccount.Click += new System.EventHandler(this.menuItemAccount_Click);
        //
        // checkIncludePatsWithApts
        //
        this.checkIncludePatsWithApts.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludePatsWithApts.Location = new System.Drawing.Point(6, 33);
        this.checkIncludePatsWithApts.Name = "checkIncludePatsWithApts";
        this.checkIncludePatsWithApts.Size = new System.Drawing.Size(239, 18);
        this.checkIncludePatsWithApts.TabIndex = 30;
        this.checkIncludePatsWithApts.Text = "Include patients with upcoming appointments";
        this.checkIncludePatsWithApts.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludePatsWithApts.UseVisualStyleBackColor = true;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(3, 130);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(917, 453);
        this.gridMain.TabIndex = 31;
        this.gridMain.setTitle("Treatment Finder");
        this.gridMain.setTranslationName("TableTreatmentFinder");
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
        // buttonExport
        //
        this.buttonExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.buttonExport.setAutosize(true);
        this.buttonExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonExport.setCornerRadius(4F);
        this.buttonExport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.buttonExport.Location = new System.Drawing.Point(3, 613);
        this.buttonExport.Name = "buttonExport";
        this.buttonExport.Size = new System.Drawing.Size(119, 24);
        this.buttonExport.TabIndex = 72;
        this.buttonExport.Text = "Export to File";
        this.buttonExport.Click += new System.EventHandler(this.buttonExport_Click);
        //
        // butLettersPreview
        //
        this.butLettersPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLettersPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLettersPreview.setAutosize(true);
        this.butLettersPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLettersPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLettersPreview.setCornerRadius(4F);
        this.butLettersPreview.Image = Resources.getbutPreview();
        this.butLettersPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLettersPreview.Location = new System.Drawing.Point(3, 587);
        this.butLettersPreview.Name = "butLettersPreview";
        this.butLettersPreview.Size = new System.Drawing.Size(119, 24);
        this.butLettersPreview.TabIndex = 71;
        this.butLettersPreview.Text = "Letters Preview";
        this.butLettersPreview.Click += new System.EventHandler(this.butLettersPreview_Click);
        //
        // butLabelSingle
        //
        this.butLabelSingle.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabelSingle.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabelSingle.setAutosize(true);
        this.butLabelSingle.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabelSingle.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabelSingle.setCornerRadius(4F);
        this.butLabelSingle.Image = Resources.getbutLabel();
        this.butLabelSingle.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabelSingle.Location = new System.Drawing.Point(128, 587);
        this.butLabelSingle.Name = "butLabelSingle";
        this.butLabelSingle.Size = new System.Drawing.Size(119, 24);
        this.butLabelSingle.TabIndex = 70;
        this.butLabelSingle.Text = "Single Labels";
        this.butLabelSingle.Click += new System.EventHandler(this.butLabelSingle_Click);
        //
        // butLabelPreview
        //
        this.butLabelPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabelPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabelPreview.setAutosize(true);
        this.butLabelPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabelPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabelPreview.setCornerRadius(4F);
        this.butLabelPreview.Image = Resources.getbutLabel();
        this.butLabelPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabelPreview.Location = new System.Drawing.Point(128, 613);
        this.butLabelPreview.Name = "butLabelPreview";
        this.butLabelPreview.Size = new System.Drawing.Size(119, 24);
        this.butLabelPreview.TabIndex = 69;
        this.butLabelPreview.Text = "Label Preview";
        this.butLabelPreview.Click += new System.EventHandler(this.butLabelPreview_Click);
        //
        // butGotoAccount
        //
        this.butGotoAccount.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGotoAccount.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butGotoAccount.setAutosize(true);
        this.butGotoAccount.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGotoAccount.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGotoAccount.setCornerRadius(4F);
        this.butGotoAccount.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGotoAccount.Location = new System.Drawing.Point(661, 613);
        this.butGotoAccount.Name = "butGotoAccount";
        this.butGotoAccount.Size = new System.Drawing.Size(96, 24);
        this.butGotoAccount.TabIndex = 68;
        this.butGotoAccount.Text = "Go to Account";
        this.butGotoAccount.Click += new System.EventHandler(this.butGotoAccount_Click);
        //
        // butGotoFamily
        //
        this.butGotoFamily.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGotoFamily.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butGotoFamily.setAutosize(true);
        this.butGotoFamily.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGotoFamily.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGotoFamily.setCornerRadius(4F);
        this.butGotoFamily.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGotoFamily.Location = new System.Drawing.Point(661, 587);
        this.butGotoFamily.Name = "butGotoFamily";
        this.butGotoFamily.Size = new System.Drawing.Size(96, 24);
        this.butGotoFamily.TabIndex = 67;
        this.butGotoFamily.Text = "Go to Family";
        this.butGotoFamily.Click += new System.EventHandler(this.butGotoFamily_Click);
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
        this.butPrint.Location = new System.Drawing.Point(418, 613);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 24);
        this.butPrint.TabIndex = 34;
        this.butPrint.Text = "Print List";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // comboBoxMultiBilling
        //
        this.comboBoxMultiBilling.BackColor = System.Drawing.SystemColors.Window;
        this.comboBoxMultiBilling.setDroppedDown(false);
        this.comboBoxMultiBilling.setItems(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiBilling.Items"))));
        this.comboBoxMultiBilling.Location = new System.Drawing.Point(515, 32);
        this.comboBoxMultiBilling.Name = "comboBoxMultiBilling";
        this.comboBoxMultiBilling.setSelectedIndices(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiBilling.SelectedIndices"))));
        this.comboBoxMultiBilling.Size = new System.Drawing.Size(160, 21);
        this.comboBoxMultiBilling.TabIndex = 50;
        this.comboBoxMultiBilling.setUseCommas(true);
        this.comboBoxMultiBilling.Leave += new System.EventHandler(this.comboBoxMultiBilling_Leave);
        //
        // comboBoxMultiProv
        //
        this.comboBoxMultiProv.BackColor = System.Drawing.SystemColors.Window;
        this.comboBoxMultiProv.setDroppedDown(false);
        this.comboBoxMultiProv.setItems(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiProv.Items"))));
        this.comboBoxMultiProv.Location = new System.Drawing.Point(515, 10);
        this.comboBoxMultiProv.Name = "comboBoxMultiProv";
        this.comboBoxMultiProv.setSelectedIndices(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiProv.SelectedIndices"))));
        this.comboBoxMultiProv.Size = new System.Drawing.Size(160, 21);
        this.comboBoxMultiProv.TabIndex = 49;
        this.comboBoxMultiProv.setUseCommas(true);
        this.comboBoxMultiProv.Leave += new System.EventHandler(this.comboBoxMultiProv_Leave);
        //
        // textOverAmount
        //
        this.textOverAmount.Location = new System.Drawing.Point(177, 55);
        this.textOverAmount.Name = "textOverAmount";
        this.textOverAmount.Size = new System.Drawing.Size(68, 20);
        this.textOverAmount.TabIndex = 48;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(367, 11);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 34;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(346, 55);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(98, 24);
        this.butRefresh.TabIndex = 32;
        this.butRefresh.Text = "&Refresh List";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
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
        this.butCancel.Location = new System.Drawing.Point(844, 613);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormRpTreatmentFinder
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(923, 641);
        this.Controls.Add(this.buttonExport);
        this.Controls.Add(this.butLettersPreview);
        this.Controls.Add(this.butLabelSingle);
        this.Controls.Add(this.butLabelPreview);
        this.Controls.Add(this.butGotoAccount);
        this.Controls.Add(this.butGotoFamily);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpTreatmentFinder";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Treatment Finder";
        this.Load += new System.EventHandler(this.FormRpTreatmentFinder_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formRpTreatmentFinder_Load(Object sender, System.EventArgs e) throws Exception {
        //DateTime today=DateTime.Today;
        //will start out 1st through 30th of previous month
        //date1.SelectionStart=new DateTime(today.Year,today.Month,1).AddMonths(-1);
        //date2.SelectionStart=new DateTime(today.Year,today.Month,1).AddDays(-1);
        comboBoxMultiProv.getItems().Add("All");
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboBoxMultiProv.getItems().Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        comboBoxMultiProv.setSelected(0,true);
        comboBoxMultiProv.refreshText();
        comboBoxMultiBilling.getItems().Add("All");
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            comboBoxMultiBilling.getItems().Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
        }
        comboBoxMultiBilling.setSelected(0,true);
        comboBoxMultiBilling.refreshText();
        comboMonthStart.SelectedIndex = 0;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), ""))
        {
            return ;
        }
         
        DateTime dateSince = new DateTime();
        if (StringSupport.equals(textDateStart.Text.Trim(), ""))
        {
            dateSince = DateTime.MinValue;
        }
        else
        {
            dateSince = PIn.Date(textDateStart.Text);
        } 
        int monthStart = comboMonthStart.SelectedIndex;
        double aboveAmount = new double();
        if (!StringSupport.equals(textOverAmount.errorProvider1.GetError(textOverAmount), ""))
        {
            return ;
        }
         
        if (!StringSupport.equals(textOverAmount.Text.Trim(), ""))
        {
            aboveAmount = PIn.Double(textOverAmount.Text);
        }
        else
        {
            aboveAmount = 0;
        } 
        ArrayList provFilter = new ArrayList();
        ArrayList billFilter = new ArrayList();
        if (!StringSupport.equals(comboBoxMultiProv.getSelectedIndices()[0].ToString(), "0"))
        {
            provFilter = comboBoxMultiProv.getSelectedIndices();
        }
         
        if (!StringSupport.equals(comboBoxMultiBilling.getSelectedIndices()[0].ToString(), "0"))
        {
            billFilter = comboBoxMultiBilling.getSelectedIndices();
        }
         
        String code1 = "";
        String code2 = "";
        if (!StringSupport.equals(textCodeRange.Text.Trim(), ""))
        {
            if (textCodeRange.Text.Contains("-"))
            {
                String[] codeSplit = textCodeRange.Text.Split('-');
                code1 = codeSplit[0].Trim();
                code2 = codeSplit[1].Trim();
            }
            else
            {
                code1 = textCodeRange.Text.Trim();
                code2 = textCodeRange.Text.Trim();
            } 
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        //0=PatNum
        ODGridColumn col = new ODGridColumn(Lan.g("TableTreatmentFinder","LName"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","FName"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Contact"),120);
        gridMain.getColumns().add(col);
        //4=address
        //5=cityStateZip
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Annual Max"),70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Amt Used"),70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Amt Pend"),70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Amt Rem"),70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Treat Plan"),70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableTreatmentFinder","Insurance Carrier"),225);
        col.setTextAlign(HorizontalAlignment.Left);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        Cursor = Cursors.WaitCursor;
        table = Patients.GetTreatmentFinderList(checkIncludeNoIns.Checked, checkIncludePatsWithApts.Checked, monthStart, dateSince, aboveAmount, provFilter, billFilter, code1, code2);
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int j = 0;j < table.Columns.Count;j++)
            {
                //Temporary filter just showing columns wanted. Probable it will become user defined.
                if (j == 0 || j == 4 || j == 5 || j == 6 || j == 7)
                {
                    continue;
                }
                 
                //PatNum,address,city,State,Zip are just for the export report.
                if (j == 8 && PIn.Double(table.Rows[i][j].ToString()) == 0)
                {
                    row.getCells().add("");
                    continue;
                }
                 
                //don't show annual max for patients without ins or patients without annual max
                if (j == 9 && PIn.Double(table.Rows[i][8].ToString()) == 0)
                {
                    //if annualmax is 0
                    row.getCells().add("");
                    continue;
                }
                 
                //don't show amount remaining if no annual max
                row.getCells().Add(table.Rows[i][j].ToString());
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        Cursor = Cursors.Default;
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        GotoModule.GotoChart(PIn.Long(table.Rows[e.getRow()]["PatNum"].ToString()));
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //Might not need cellDoubleClick
    private void comboBoxMultiProv_Leave(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < comboBoxMultiProv.getSelectedIndices().Count;i++)
        {
            if (StringSupport.equals(comboBoxMultiProv.getSelectedIndices()[i].ToString(), "0"))
            {
                comboBoxMultiProv.getSelectedIndices().Clear();
                comboBoxMultiProv.setSelected(0,true);
                comboBoxMultiProv.refreshText();
            }
             
        }
        if (comboBoxMultiProv.getSelectedIndices().Count == 0)
        {
            comboBoxMultiProv.getSelectedIndices().Clear();
            comboBoxMultiProv.setSelected(0,true);
            comboBoxMultiProv.refreshText();
        }
         
    }

    private void comboBoxMultiBilling_Leave(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < comboBoxMultiBilling.getSelectedIndices().Count;i++)
        {
            if (StringSupport.equals(comboBoxMultiBilling.getSelectedIndices()[i].ToString(), "0"))
            {
                comboBoxMultiBilling.getSelectedIndices().Clear();
                comboBoxMultiBilling.setSelected(0,true);
                comboBoxMultiBilling.refreshText();
            }
             
        }
        if (comboBoxMultiBilling.getSelectedIndices().Count == 0)
        {
            comboBoxMultiBilling.getSelectedIndices().Clear();
            comboBoxMultiBilling.setSelected(0,true);
            comboBoxMultiBilling.refreshText();
        }
         
    }

    private void butLettersPreview_Click(Object sender, EventArgs e) throws Exception {
        //Create letters. loop through each row and insert information into sheets,
        //take all the sheets and add to one giant pdf for preview.
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select patient(s) first.");
            return ;
        }
         
        FormSheetPicker FormS = new FormSheetPicker();
        FormS.SheetType = SheetTypeEnum.PatientLetter;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDef sheetDef;
        Sheet sheet = null;
        for (int i = 0;i < FormS.SelectedSheetDefs.Count;i++)
        {
            PdfDocument document = new PdfDocument();
            FormSheetFillEdit FormSF = null;
            PdfPage page = new PdfPage();
            String filePathAndName = "";
            for (int j = 0;j < gridMain.getSelectedIndices().Length;j++)
            {
                page = document.AddPage();
                sheetDef = FormS.SelectedSheetDefs[i];
                sheet = SheetUtil.CreateSheet(sheetDef, PIn.Long(table.Rows[gridMain.getSelectedIndices()[j]]["PatNum"].ToString()));
                SheetParameter.setParameter(sheet,"PatNum",PIn.Long(table.Rows[gridMain.getSelectedIndices()[j]]["PatNum"].ToString()));
                SheetFiller.fillFields(sheet);
                SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
                FormSF = new FormSheetFillEdit(sheet);
                SheetPrinting.createPdfPage(sheet,page);
            }
            filePathAndName = Path.ChangeExtension(Path.GetTempFileName(), ".pdf");
            document.Save(filePathAndName);
            Process.Start(filePathAndName);
            DialogResult = DialogResult.OK;
        }
    }

    private void butLabelSingle_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select patient(s) first.");
            return ;
        }
         
        int patientsPrinted = 0;
        String text = new String();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            text = "";
            //print single label
            text = table.Rows[gridMain.getSelectedIndices()[i]]["FName"].ToString() + " " + table.Rows[gridMain.getSelectedIndices()[i]]["LName"].ToString() + "\r\n";
            text += table.Rows[gridMain.getSelectedIndices()[i]]["address"].ToString() + "\r\n";
            text += table.Rows[gridMain.getSelectedIndices()[i]]["City"].ToString() + ", " + table.Rows[gridMain.getSelectedIndices()[i]]["State"].ToString() + " " + table.Rows[gridMain.getSelectedIndices()[i]]["Zip"].ToString() + "\r\n";
            LabelSingle.PrintText(0, text);
            patientsPrinted++;
        }
    }

    private void butLabelPreview_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select patient(s) first.");
            return ;
        }
         
        pagesPrinted = 0;
        patientsPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdLabels_PrintPage);
        pd.OriginAtMargins = true;
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        OpenDental.UI.FormPrintPreview printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.LabelSheet, pd, (int)Math.Ceiling((double)gridMain.getSelectedIndices().Length / 30), 0, "Treatment finder labels printed");
        printPreview.ShowDialog();
    }

    private void pdLabels_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        int totalPages = (int)Math.Ceiling((double)gridMain.getSelectedIndices().Length / 30);
        Graphics g = ev.Graphics;
        float yPos = 63;
        float xPos = 50;
        String text = "";
        while (yPos < 1000 && patientsPrinted < gridMain.getSelectedIndices().Length)
        {
            text = "";
            text = table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["FName"].ToString() + " " + table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["LName"].ToString() + "\r\n";
            text += table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["address"].ToString() + "\r\n";
            text += table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["City"].ToString() + ", " + table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["State"].ToString() + " " + table.Rows[gridMain.getSelectedIndices()[patientsPrinted]]["Zip"].ToString() + "\r\n";
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

    private void menuItemFamily_Click(Object sender, EventArgs e) throws Exception {
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

    private void menuItemAccount_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
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

    private void buttonExport_Click(Object sender, EventArgs e) throws Exception {
        SaveFileDialog saveFileDialog2 = new SaveFileDialog();
        saveFileDialog2.AddExtension = true;
        saveFileDialog2.Title = Lan.g(this,"Treatment Finder");
        saveFileDialog2.FileName = Lan.g(this,"Treatment Finder");
        if (!Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            try
            {
                Directory.CreateDirectory(PrefC.getString(PrefName.ExportPath));
                saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        else
            //initialDirectory will be blank
            saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath); 
        saveFileDialog2.Filter = "Text files(*.txt)|*.txt|Excel Files(*.xls)|*.xls|All files(*.*)|*.*";
        saveFileDialog2.FilterIndex = 0;
        if (saveFileDialog2.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            StreamWriter sw = new StreamWriter(saveFileDialog2.FileName, false);
            try
            {
                {
                    String line = "";
                    for (int i = 0;i < table.Columns.Count;i++)
                    {
                        if (i > 0)
                        {
                            line += "\t";
                        }
                         
                        line += table.Columns[i].ColumnName;
                    }
                    //Not translated with current code.
                    sw.WriteLine(line);
                    String cell = new String();
                    for (int i = 0;i < table.Rows.Count;i++)
                    {
                        line = "";
                        for (int j = 0;j < table.Columns.Count;j++)
                        {
                            if (j > 0)
                            {
                                line += "\t";
                            }
                             
                            cell = table.Rows[i][j].ToString();
                            cell = cell.Replace("\r", "");
                            cell = cell.Replace("\n", "");
                            cell = cell.Replace("\t", "");
                            cell = cell.Replace("\"", "");
                            line += cell;
                        }
                        sw.WriteLine(line);
                    }
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
            return ;
        }

        MessageBox.Show(Lan.g(this,"File created successfully"));
    }

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
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Treatment finder list printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar2)
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
            text = Lan.g(this,"Treatment Finder");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            if (checkIncludeNoIns.Checked)
            {
                text = "Include patients without insurance";
            }
            else
            {
                text = "Only patients with insurance";
            } 
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

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


