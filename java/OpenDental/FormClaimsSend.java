//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ClearinghouseL;
import OpenDental.Eclaims.Eclaims;
import OpenDental.Eclaims.FormCCDPrint;
import OpenDental.FormCanadaOutstandingTransactions;
import OpenDental.FormCanadaPaymentReconciliation;
import OpenDental.FormCanadaSummaryReconciliation;
import OpenDental.FormClaimPrint;
import OpenDental.FormClaimReports;
import OpenDental.FormClaimsSend;
import OpenDental.FormEtrans277Edit;
import OpenDental.FormEtransEdit;
import OpenDental.FormRpPrintPreview;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODToolBarButton;
import OpenDental.UI.ODToolBarButtonStyle;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;

/**
* 
*/
public class FormClaimsSend  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.ImageList imageList1 = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.ContextMenu contextMenuStatus = new System.Windows.Forms.ContextMenu();
    private OpenDental.UI.ODToolBar ToolBarMain;
    /**
    * final list of eclaims(as Claim.ClaimNum) to send
    */
    public static ArrayList eClaimList = new ArrayList();
    private OpenDental.UI.ODGrid gridMain;
    private ClaimSendQueueItem[] listQueue = new ClaimSendQueueItem[]();
    /**
    * 
    */
    public long GotoPatNum = new long();
    private OpenDental.UI.ODGrid gridHistory;
    private MonthCalendar calendarTo = new MonthCalendar();
    private OpenDental.UI.Button butDropTo;
    private OpenDental.UI.Button butDropFrom;
    private MonthCalendar calendarFrom = new MonthCalendar();
    private OpenDental.ValidDate textDateTo;
    private Label label2 = new Label();
    private OpenDental.ValidDate textDateFrom;
    private Label label1 = new Label();
    /**
    * 
    */
    public long GotoClaimNum = new long();
    private OpenDental.UI.ODToolBar ToolBarHistory;
    private DataTable tableHistory = new DataTable();
    private int pagesPrinted = new int();
    private Panel panelSplitter = new Panel();
    private Panel panelHistory = new Panel();
    private Panel panel1 = new Panel();
    private PrintDocument pd2 = new PrintDocument();
    boolean MouseIsDownOnSplitter = new boolean();
    int SplitterOriginalY = new int();
    int OriginalMouseY = new int();
    boolean headingPrinted = new boolean();
    int headingPrintH = new int();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private ComboBox comboCustomTracking = new ComboBox();
    private Label labelCustomTracking = new Label();
    private ContextMenu contextMenuEclaims = new ContextMenu();
    //private ContextMenu contextMenuHist;
    /**
    * 
    */
    public FormClaimsSend() throws Exception {
        initializeComponent();
        //tbQueue.CellDoubleClicked += new OpenDental.ContrTable.CellEventHandler(tbQueue_CellDoubleClicked);
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimsSend.class);
        this.label6 = new System.Windows.Forms.Label();
        this.contextMenuStatus = new System.Windows.Forms.ContextMenu();
        this.imageList1 = new System.Windows.Forms.ImageList(this.components);
        this.calendarTo = new System.Windows.Forms.MonthCalendar();
        this.calendarFrom = new System.Windows.Forms.MonthCalendar();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.panelSplitter = new System.Windows.Forms.Panel();
        this.panelHistory = new System.Windows.Forms.Panel();
        this.gridHistory = new OpenDental.UI.ODGrid();
        this.panel1 = new System.Windows.Forms.Panel();
        this.ToolBarHistory = new OpenDental.UI.ODToolBar();
        this.butDropTo = new OpenDental.UI.Button();
        this.butDropFrom = new OpenDental.UI.Button();
        this.textDateFrom = new OpenDental.ValidDate();
        this.textDateTo = new OpenDental.ValidDate();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.contextMenuEclaims = new System.Windows.Forms.ContextMenu();
        this.comboCustomTracking = new System.Windows.Forms.ComboBox();
        this.labelCustomTracking = new System.Windows.Forms.Label();
        this.panelHistory.SuspendLayout();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // label6
        //
        this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label6.Location = new System.Drawing.Point(107, -44);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(112, 44);
        this.label6.TabIndex = 21;
        this.label6.Text = "Insurance Claims";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // imageList1
        //
        this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
        this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
        this.imageList1.Images.SetKeyName(0, "");
        this.imageList1.Images.SetKeyName(1, "");
        this.imageList1.Images.SetKeyName(2, "");
        this.imageList1.Images.SetKeyName(3, "");
        this.imageList1.Images.SetKeyName(4, "");
        this.imageList1.Images.SetKeyName(5, "");
        this.imageList1.Images.SetKeyName(6, "");
        //
        // calendarTo
        //
        this.calendarTo.Location = new System.Drawing.Point(196, 29);
        this.calendarTo.MaxSelectionCount = 1;
        this.calendarTo.Name = "calendarTo";
        this.calendarTo.TabIndex = 42;
        this.calendarTo.Visible = false;
        this.calendarTo.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.calendarTo_DateSelected);
        //
        // calendarFrom
        //
        this.calendarFrom.Location = new System.Drawing.Point(6, 29);
        this.calendarFrom.MaxSelectionCount = 1;
        this.calendarFrom.Name = "calendarFrom";
        this.calendarFrom.TabIndex = 39;
        this.calendarFrom.Visible = false;
        this.calendarFrom.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.calendarFrom_DateSelected);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(196, 5);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(72, 18);
        this.label2.TabIndex = 36;
        this.label2.Text = "To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(1, 5);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(75, 18);
        this.label1.TabIndex = 34;
        this.label1.Text = "From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // panelSplitter
        //
        this.panelSplitter.Cursor = System.Windows.Forms.Cursors.SizeNS;
        this.panelSplitter.Location = new System.Drawing.Point(2, 398);
        this.panelSplitter.Name = "panelSplitter";
        this.panelSplitter.Size = new System.Drawing.Size(961, 6);
        this.panelSplitter.TabIndex = 50;
        this.panelSplitter.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseDown);
        this.panelSplitter.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseMove);
        this.panelSplitter.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseUp);
        //
        // panelHistory
        //
        this.panelHistory.Controls.Add(this.calendarFrom);
        this.panelHistory.Controls.Add(this.label1);
        this.panelHistory.Controls.Add(this.calendarTo);
        this.panelHistory.Controls.Add(this.gridHistory);
        this.panelHistory.Controls.Add(this.panel1);
        this.panelHistory.Controls.Add(this.butDropTo);
        this.panelHistory.Controls.Add(this.butDropFrom);
        this.panelHistory.Controls.Add(this.textDateFrom);
        this.panelHistory.Controls.Add(this.label2);
        this.panelHistory.Controls.Add(this.textDateTo);
        this.panelHistory.Location = new System.Drawing.Point(0, 403);
        this.panelHistory.Name = "panelHistory";
        this.panelHistory.Size = new System.Drawing.Size(972, 286);
        this.panelHistory.TabIndex = 51;
        //
        // gridHistory
        //
        this.gridHistory.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridHistory.setHScrollVisible(false);
        this.gridHistory.Location = new System.Drawing.Point(4, 31);
        this.gridHistory.Name = "gridHistory";
        this.gridHistory.setScrollValue(0);
        this.gridHistory.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridHistory.Size = new System.Drawing.Size(959, 252);
        this.gridHistory.TabIndex = 33;
        this.gridHistory.setTitle("History");
        this.gridHistory.setTranslationName("TableClaimHistory");
        this.gridHistory.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridHistory.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridHistory_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // panel1
        //
        this.panel1.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panel1.Controls.Add(this.ToolBarHistory);
        this.panel1.Location = new System.Drawing.Point(387, 0);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(576, 27);
        this.panel1.TabIndex = 44;
        //
        // ToolBarHistory
        //
        this.ToolBarHistory.BackColor = System.Drawing.SystemColors.Control;
        this.ToolBarHistory.setImageList(this.imageList1);
        this.ToolBarHistory.Location = new System.Drawing.Point(1, 1);
        this.ToolBarHistory.Name = "ToolBarHistory";
        this.ToolBarHistory.Size = new System.Drawing.Size(575, 25);
        this.ToolBarHistory.TabIndex = 43;
        this.ToolBarHistory.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarHistory.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarHistory_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butDropTo
        //
        this.butDropTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDropTo.setAutosize(true);
        this.butDropTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDropTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDropTo.setCornerRadius(4F);
        this.butDropTo.Location = new System.Drawing.Point(352, 4);
        this.butDropTo.Name = "butDropTo";
        this.butDropTo.Size = new System.Drawing.Size(22, 23);
        this.butDropTo.TabIndex = 41;
        this.butDropTo.Text = "V";
        this.butDropTo.UseVisualStyleBackColor = true;
        this.butDropTo.Click += new System.EventHandler(this.butDropTo_Click);
        //
        // butDropFrom
        //
        this.butDropFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDropFrom.setAutosize(true);
        this.butDropFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDropFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDropFrom.setCornerRadius(4F);
        this.butDropFrom.Location = new System.Drawing.Point(162, 4);
        this.butDropFrom.Name = "butDropFrom";
        this.butDropFrom.Size = new System.Drawing.Size(22, 23);
        this.butDropFrom.TabIndex = 40;
        this.butDropFrom.Text = "V";
        this.butDropFrom.UseVisualStyleBackColor = true;
        this.butDropFrom.Click += new System.EventHandler(this.butDropFrom_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(79, 6);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(81, 20);
        this.textDateFrom.TabIndex = 35;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(269, 6);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(81, 20);
        this.textDateTo.TabIndex = 37;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(74, 26);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(160, 21);
        this.comboClinic.TabIndex = 53;
        this.comboClinic.SelectionChangeCommitted += new System.EventHandler(this.comboClinic_SelectionChangeCommitted);
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(7, 29);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(65, 14);
        this.labelClinic.TabIndex = 52;
        this.labelClinic.Text = "Clinic Filter";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(4, 49);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(959, 350);
        this.gridMain.TabIndex = 32;
        this.gridMain.setTitle("Claims Waiting to Send");
        this.gridMain.setTranslationName("TableQueue");
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
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageList1);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(971, 25);
        this.ToolBarMain.TabIndex = 31;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // comboCustomTracking
        //
        this.comboCustomTracking.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCustomTracking.Location = new System.Drawing.Point(384, 26);
        this.comboCustomTracking.MaxDropDownItems = 40;
        this.comboCustomTracking.Name = "comboCustomTracking";
        this.comboCustomTracking.Size = new System.Drawing.Size(160, 21);
        this.comboCustomTracking.TabIndex = 55;
        this.comboCustomTracking.SelectionChangeCommitted += new System.EventHandler(this.comboCustomTracking_SelectionChangeCommitted);
        //
        // labelCustomTracking
        //
        this.labelCustomTracking.Location = new System.Drawing.Point(240, 29);
        this.labelCustomTracking.Name = "labelCustomTracking";
        this.labelCustomTracking.Size = new System.Drawing.Size(142, 14);
        this.labelCustomTracking.TabIndex = 54;
        this.labelCustomTracking.Text = "Custom Tracking Filter";
        this.labelCustomTracking.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // FormClaimsSend
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(971, 691);
        this.Controls.Add(this.comboCustomTracking);
        this.Controls.Add(this.labelCustomTracking);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.panelHistory);
        this.Controls.Add(this.panelSplitter);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.label6);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimsSend";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Send Claims";
        this.Load += new System.EventHandler(this.FormClaimsSend_Load);
        this.panelHistory.ResumeLayout(false);
        this.panelHistory.PerformLayout();
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formClaimsSend_Load(Object sender, System.EventArgs e) throws Exception {
        adjustPanelSplit();
        contextMenuStatus.MenuItems.Add(Lan.g(this,"Go to Account"), new EventHandler(GotoAccount_Clicked));
        gridMain.ContextMenu = contextMenuStatus;
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            contextMenuEclaims.MenuItems.Add(Clearinghouses.getListt()[i].Description, new EventHandler(menuItemClearinghouse_Click));
        }
        layoutToolBars();
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add(Lan.g(this,"all"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
        } 
        comboCustomTracking.Items.Add(Lan.g(this,"all"));
        comboCustomTracking.SelectedIndex = 0;
        if (DefC.getLong()[((Enum)DefCat.ClaimCustomTracking).ordinal()].Length == 0)
        {
            labelCustomTracking.Visible = false;
            comboCustomTracking.Visible = false;
        }
        else
        {
            for (int i = 0;i < DefC.getLong()[((Enum)DefCat.ClaimCustomTracking).ordinal()].Length;i++)
            {
                comboCustomTracking.Items.Add(DefC.getLong()[((Enum)DefCat.ClaimCustomTracking).ordinal()][i].ItemName);
            }
        } 
        if (PrefC.getRandomKeys() && !PrefC.getBool(PrefName.EasyNoClinics))
        {
        }
        else
        {
            //using random keys and clinics
            //Does not pull in reports automatically, because they could easily get assigned to the wrong clearinghouse
            FormClaimReports FormC = new FormClaimReports();
            FormC.AutomaticMode = true;
            FormC.ShowDialog();
        } 
        fillGrid();
        textDateFrom.Text = DateTime.Today.AddDays(-7).ToShortDateString();
        textDateTo.Text = DateTime.Today.ToShortDateString();
        fillHistory();
    }

    /**
    * 
    */
    public void layoutToolBars() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Preview"), 0, Lan.g(this,"Preview the Selected Claim"), "Preview"));
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Blank"), 1, Lan.g(this,"Print a Blank Claim Form"), "Blank"));
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Print"), 2, Lan.g(this,"Print Selected Claims"), "Print"));
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Labels"), 6, Lan.g(this,"Print Single Labels"), "Labels"));
        /*ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        			ODToolBarButton button=new ODToolBarButton(Lan.g(this,"Change Status"),-1,Lan.g(this,"Changes Status of Selected Claims"),"Status");
        			button.Style=ODToolBarButtonStyle.DropDownButton;
        			button.DropDownMenu=contextMenuStatus;
        			ToolBarMain.Buttons.Add(button);*/
        ToolBarMain.getButtons().add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ODToolBarButton button = new ODToolBarButton(Lan.g(this,"Send E-Claims"), 4, Lan.g(this,"Send claims Electronically"), "Eclaims");
        button.setStyle(ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(contextMenuEclaims);
        ToolBarMain.getButtons().add(button);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Outstanding"), -1, Lan.g(this,"Get Outstanding Transactions"), "Outstanding"));
            ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Payment Rec"), -1, Lan.g(this,"Get Payment Reconciliation Transactions"), "PayRec"));
        }
        else
        {
            //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Summary Rec"),-1,Lan.g(this,"Get Summary Reconciliation Transactions"),"SummaryRec"));
            ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Get Reports"), 5, Lan.g(this,"Get Reports from Other Clearinghouses"), "Reports"));
        } 
        ToolBarMain.getButtons().add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Close"), -1, "", "Close"));
        /*ArrayList toolButItems=ToolButItems.GetForToolBar(ToolBarsAvail.ClaimsSend);
        			for(int i=0;i<toolButItems.Count;i++){
        				ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        				ToolBarMain.Buttons.Add(new ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText
        					,-1,"",((ToolButItem)toolButItems[i]).ProgramNum));
        			}*/
        ToolBarMain.Invalidate();
        ToolBarHistory.getButtons().Clear();
        ToolBarHistory.getButtons().add(new ODToolBarButton(Lan.g(this,"Refresh"), -1, Lan.g(this,"Refresh this list."), "Refresh"));
        ToolBarHistory.getButtons().add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ToolBarHistory.getButtons().add(new ODToolBarButton(Lan.g(this,"Undo"), -1, Lan.g(this,"Change the status of claims back to 'Waiting'."), "Undo"));
        ToolBarHistory.getButtons().add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ToolBarHistory.getButtons().add(new ODToolBarButton(Lan.g(this,"Print List"), 2, Lan.g(this,"Print history list."), "PrintList"));
        /*#if DEBUG
        			ToolBarHistory.Buttons.Add(new ODToolBarButton(Lan.g(this,"Print Item"),2,
        				Lan.g(this,"For debugging, this will simply display the first item in the list."),"PrintItem"));
        			#else
        			ToolBarHistory.Buttons.Add(new ODToolBarButton(Lan.g(this,"Print Item"),2,
        				Lan.g(this,"Print one item from the list."),"PrintItem"));
        			#endif*/
        ToolBarHistory.Invalidate();
    }

    private void gotoAccount_Clicked(Object sender, System.EventArgs e) throws Exception {
        //accessed by right clicking
        if (gridMain.getSelectedIndices().Length != 1)
        {
            MsgBox.show(this,"Please select exactly one item first.");
            return ;
        }
         
        GotoPatNum = listQueue[gridMain.getSelectedIndices()[0]].PatNum;
        GotoClaimNum = listQueue[gridMain.getSelectedIndices()[0]].ClaimNum;
        DialogResult = DialogResult.OK;
    }

    private void menuItemClearinghouse_Click(Object sender, System.EventArgs e) throws Exception {
        MenuItem menuitem = (MenuItem)sender;
        SendEclaimsToClearinghouse(Clearinghouses.getListt()[menuitem.Index].ClearinghouseNum);
    }

    private void fillGrid() throws Exception {
        long clinicNum = 0;
        long customTracking = 0;
        if (!PrefC.getBool(PrefName.EasyNoClinics) && comboClinic.SelectedIndex != 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        if (comboCustomTracking.SelectedIndex != 0)
        {
            customTracking = DefC.getLong()[((Enum)DefCat.ClaimCustomTracking).ordinal()][comboCustomTracking.SelectedIndex - 1].DefNum;
        }
         
        listQueue = Claims.GetQueueList(0, clinicNum, customTracking);
        //The next 13 lines delete any claims that exist without any claim procs.  The db maint does the same thing.
        //We will eventually prevent it, both when deleting a claimproc from inside a claim and from inside a procedure.
        List<ClaimSendQueueItem> listQueueShort = new List<ClaimSendQueueItem>();
        for (int i = 0;i < listQueue.Length;i++)
        {
            List<ClaimProc> claimProcList = ClaimProcs.RefreshForClaim(listQueue[i].ClaimNum);
            List<ClaimProc> claimProcs = ClaimProcs.GetForSendClaim(claimProcList, listQueue[i].ClaimNum);
            if (claimProcs.Count == 0)
            {
                Claim claimWithNoProcs = Claims.GetClaim(listQueue[i].ClaimNum);
                Claims.delete(claimWithNoProcs);
            }
            else
            {
                listQueueShort.Add(listQueue[i]);
            } 
        }
        listQueue = listQueueShort.ToArray();
        for (int i = 0;i < listQueue.Length;i++)
        {
            Eclaims.GetMissingData(listQueue[i]);
        }
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableQueue","Date Service"),80);
        //new column
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Patient Name"),120);
        //was 190
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Carrier Name"),220);
        //was 100
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Clinic"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","M/D"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Clearinghouse"),80);
        //5. This is position critical. See SendEclaimsToClearinghouse().
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Warnings"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQueue","Missing Info"),300);
        //was 400
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listQueue.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listQueue[i].DateService.ToShortDateString());
            row.getCells().Add(listQueue[i].PatName);
            row.getCells().Add(listQueue[i].Carrier);
            row.getCells().Add(Clinics.GetDesc(listQueue[i].ClinicNum));
            MedType __dummyScrutVar0 = listQueue[i].MedType;
            if (__dummyScrutVar0.equals(EnumClaimMedType.Dental))
            {
                row.getCells().add("Dent");
            }
            else if (__dummyScrutVar0.equals(EnumClaimMedType.Medical))
            {
                row.getCells().add("Med");
            }
            else if (__dummyScrutVar0.equals(EnumClaimMedType.Institutional))
            {
                row.getCells().add("Inst");
            }
               
            if (listQueue[i].NoSendElect)
            {
                row.getCells().add("Paper");
                row.getCells().add("");
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ClearinghouseL.GetDescript(listQueue[i].ClearinghouseNum));
                row.getCells().Add(listQueue[i].Warnings);
                row.getCells().Add(listQueue[i].MissingData);
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int selected = e.getRow();
        FormClaimPrint FormCP;
        FormCP = new FormClaimPrint();
        FormCP.PatNumCur = listQueue[e.getRow()].PatNum;
        FormCP.ClaimNumCur = listQueue[e.getRow()].ClaimNum;
        FormCP.PrintImmediately = false;
        FormCP.ShowDialog();
        fillGrid();
        gridMain.setSelected(selected,true);
        fillHistory();
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar1 = e.getButton().getTag().ToString();
        if (__dummyScrutVar1.equals("Preview"))
        {
            toolBarButPreview_Click();
        }
        else if (__dummyScrutVar1.equals("Blank"))
        {
            toolBarButBlank_Click();
        }
        else if (__dummyScrutVar1.equals("Print"))
        {
            toolBarButPrint_Click();
        }
        else if (__dummyScrutVar1.equals("Labels"))
        {
            toolBarButLabels_Click();
        }
        else if (__dummyScrutVar1.equals("Eclaims"))
        {
            SendEclaimsToClearinghouse(0);
        }
        else if (__dummyScrutVar1.equals("Reports"))
        {
            toolBarButReports_Click();
        }
        else if (__dummyScrutVar1.equals("Outstanding"))
        {
            toolBarButOutstanding_Click();
        }
        else if (__dummyScrutVar1.equals("PayRec"))
        {
            toolBarButPayRec_Click();
        }
        else if (__dummyScrutVar1.equals("SummaryRec"))
        {
            toolBarButSummaryRec_Click();
        }
        else if (__dummyScrutVar1.equals("Close"))
        {
            Close();
        }
                  
    }

    private void toolBarButPreview_Click() throws Exception {
        FormClaimPrint FormCP;
        FormCP = new FormClaimPrint();
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select a claim first."));
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 1)
        {
            MessageBox.Show(Lan.g(this,"Please select only one claim."));
            return ;
        }
         
        FormCP.PatNumCur = listQueue[gridMain.getSelectedIndex()].PatNum;
        FormCP.ClaimNumCur = listQueue[gridMain.getSelectedIndex()].ClaimNum;
        FormCP.PrintImmediately = false;
        FormCP.ShowDialog();
        fillGrid();
        fillHistory();
    }

    private void toolBarButBlank_Click() throws Exception {
        PrintDocument pd = new PrintDocument();
        if (!PrinterL.SetPrinter(pd, PrintSituation.Claim, 0, "Blank claim printed"))
        {
            return ;
        }
         
        FormClaimPrint FormCP = new FormClaimPrint();
        FormCP.PrintBlank = true;
        FormCP.printImmediate(pd.PrinterSettings);
    }

    private void toolBarButPrint_Click() throws Exception {
        FormClaimPrint FormCP = new FormClaimPrint();
        if (gridMain.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < listQueue.Length;i++)
            {
                if ((StringSupport.equals(listQueue[i].ClaimStatus, "W") || StringSupport.equals(listQueue[i].ClaimStatus, "P")) && listQueue[i].NoSendElect)
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
            if (!MsgBox.show(this,true,"No claims were selected.  Print all selected paper claims?"))
            {
                return ;
            }
             
        }
         
        PrintDocument pd = new PrintDocument();
        if (!PrinterL.SetPrinter(pd, PrintSituation.Claim, 0, "Multiple claims printed"))
        {
            return ;
        }
         
        pd.PrinterSettings.Copies = 1;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //Used to be sent in the FormCP.PrintImmediate function call below.  Moved up here to keep same logic.
            FormCP.PatNumCur = listQueue[gridMain.getSelectedIndices()[i]].PatNum;
            FormCP.ClaimNumCur = listQueue[gridMain.getSelectedIndices()[i]].ClaimNum;
            FormCP.ClaimFormCur = null;
            //so that it will pull from the individual claim or plan.
            if (!FormCP.printImmediate(pd.PrinterSettings))
            {
                return ;
            }
             
            Etranss.SetClaimSentOrPrinted(listQueue[gridMain.getSelectedIndices()[i]].ClaimNum, listQueue[gridMain.getSelectedIndices()[i]].PatNum, 0, EtransType.ClaimPrinted, 0);
        }
        fillGrid();
        fillHistory();
    }

    private void toolBarButLabels_Click() throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select a claim first."));
            return ;
        }
         
        //PrintDocument pd=new PrintDocument();//only used to pass printerName
        //if(!PrinterL.SetPrinter(pd,PrintSituation.LabelSingle)){
        //	return;
        //}
        //Carrier carrier;
        Claim claim;
        InsPlan plan;
        List<long> carrierNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            claim = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[i]].ClaimNum);
            plan = InsPlans.GetPlan(claim.PlanNum, new List<InsPlan>());
            carrierNums.Add(plan.CarrierNum);
        }
        //carrier=Carriers.GetCarrier(plan.CarrierNum);
        //LabelSingle label=new LabelSingle();
        LabelSingle.PrintCarriers(carrierNums);
    }

    //,pd.PrinterSettings.PrinterName)){
    //	return;
    //}
    /**
    * Use clearinghouseNum of 0 to indicate automatic calculation of clearinghouses.
    */
    private void sendEclaimsToClearinghouse(long clearinghouseNum) throws Exception {
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            //Clinics is in use
            if (clearinghouseNum == 0)
            {
                MsgBox.show(this,"When the Clinics option is enabled, you must use the dropdown list to select the clearinghouse to send to.");
                return ;
            }
             
        }
         
        Clearinghouse clearDefault;
        if (clearinghouseNum == 0)
        {
            clearDefault = Clearinghouses.getDefaultDental();
        }
        else
        {
            clearDefault = ClearinghouseL.getClearinghouse(clearinghouseNum);
        } 
        if (clearDefault != null && StringSupport.equals(clearDefault.ISA08, "113504607") && Process.GetProcessesByName("TesiaLink").Length == 0)
        {
            MsgBox.show(this,"Please start TesiaLink first.");
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < listQueue.Length;i++)
            {
                //if none are selected
                //loop through all rows
                if (!listQueue[i].NoSendElect && StringSupport.equals(listQueue[i].MissingData, ""))
                {
                    if (clearinghouseNum == 0)
                    {
                        //they did not use the dropdown list for specific clearinghouse
                        //If clearinghouse is zero because they just pushed the button instead of using the dropdown list,
                        //then don't check the clearinghouse of each claim.  Just select them if they are electronic.
                        gridMain.setSelected(i,true);
                    }
                    else
                    {
                        //if they used the dropdown list,
                        //then first, try to only select items in the list that match the clearinghouse.
                        if (listQueue[i].ClearinghouseNum == clearinghouseNum)
                        {
                            gridMain.setSelected(i,true);
                        }
                         
                    } 
                }
                 
            }
            //If they used the dropdown list, and there still aren't any in the list that match the selected clearinghouse
            //then ask user if they want to send all of the electronic ones through this clearinghouse.
            if (clearinghouseNum != 0 && gridMain.getSelectedIndices().Length == 0)
            {
                if (comboClinic.SelectedIndex == 0)
                {
                    MsgBox.show(this,"Please filter by clinic first.");
                    return ;
                }
                 
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Send all e-claims through selected clearinghouse?"))
                {
                    return ;
                }
                 
                for (int i = 0;i < listQueue.Length;i++)
                {
                    if (!listQueue[i].NoSendElect && StringSupport.equals(listQueue[i].MissingData, ""))
                    {
                        //no Missing Info
                        gridMain.setSelected(i,true);
                    }
                     
                }
            }
             
            //this will include other clearinghouses
            if (gridMain.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"No claims to send.");
                return ;
            }
             
            if (clearinghouseNum != 0)
            {
                //if they used the dropdown list to specify clearinghouse
                int[] selectedindices = (int[])gridMain.getSelectedIndices().Clone();
                for (int i = 0;i < selectedindices.Length;i++)
                {
                    Clearinghouse clearRow = Clearinghouses.GetClearinghouse(listQueue[selectedindices[i]].ClearinghouseNum);
                    if (clearDefault.Eformat != clearRow.Eformat)
                    {
                        MsgBox.show(this,"The default clearinghouse format does not match the format of the selected clearinghouse.  You may need to change the clearinghouse format.  Or, you may need to add a Payor ID into a clearhouse.");
                        return ;
                    }
                     
                    gridMain.getRows()[selectedindices[i]].Cells[5].Text = clearDefault.Description;
                }
                //show the changed clearinghouse
                gridMain.Invalidate();
            }
             
            if (!MsgBox.show(this,true,"Send all selected e-claims?"))
            {
                fillGrid();
                return ;
            }
             
        }
        else
        {
            //this changes back any clearinghouse descriptions that we changed manually.
            //some rows were manually selected by the user
            if (clearinghouseNum != 0)
            {
                //if they used the dropdown list to specify clearinghouse
                int[] selectedindices = (int[])gridMain.getSelectedIndices().Clone();
                for (int i = 0;i < selectedindices.Length;i++)
                {
                    Clearinghouse clearRow = Clearinghouses.GetClearinghouse(listQueue[selectedindices[i]].ClearinghouseNum);
                    if (clearDefault.Eformat != clearRow.Eformat)
                    {
                        MsgBox.show(this,"The default clearinghouse format does not match the format of the selected clearinghouse.  You may need to change the clearinghouse format.  Or, you may need to add a Payor ID into a clearhouse.");
                        return ;
                    }
                     
                    gridMain.getRows()[selectedindices[i]].Cells[5].Text = clearDefault.Description;
                }
                //show the changed clearinghouse
                gridMain.Invalidate();
            }
             
        } 
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            //Clinics is in use
            long clinicNum0 = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[0]].ClaimNum).ClinicNum;
            for (int i = 1;i < gridMain.getSelectedIndices().Length;i++)
            {
                long clinicNum = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[i]].ClaimNum).ClinicNum;
                if (clinicNum0 != clinicNum)
                {
                    MsgBox.show(this,"All claims must be for the same clinic.  You can use the combobox at the top to filter.");
                    return ;
                }
                 
            }
        }
         
        long clearinghouseNum0 = listQueue[gridMain.getSelectedIndices()[0]].ClearinghouseNum;
        EnumClaimMedType medType0 = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[0]].ClaimNum).MedType;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //we start with 0 so that we can check medtype match on the first claim
            long clearinghouseNumI = listQueue[gridMain.getSelectedIndices()[i]].ClearinghouseNum;
            if (clearinghouseNum0 != clearinghouseNumI)
            {
                MsgBox.show(this,"All claims must be for the same clearinghouse.");
                return ;
            }
             
            EnumClaimMedType medTypeI = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[i]].ClaimNum).MedType;
            if (medType0 != medTypeI)
            {
                MsgBox.show(this,"All claims must have the same MedType.");
                return ;
            }
             
            Clearinghouse clearh = Clearinghouses.getClearinghouse(clearinghouseNumI);
            if (clearh.Eformat == ElectronicClaimFormat.x837D_4010 || clearh.Eformat == ElectronicClaimFormat.x837D_5010_dental)
            {
                if (medTypeI != EnumClaimMedType.Dental)
                {
                    MessageBox.Show("On claim " + i.ToString() + ", the MedType does not match the clearinghouse e-format.");
                    return ;
                }
                 
            }
             
            if (clearh.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
            {
                if (medTypeI != EnumClaimMedType.Medical && medTypeI != EnumClaimMedType.Institutional)
                {
                    MessageBox.Show("On claim " + i.ToString() + ", the MedType does not match the clearinghouse e-format.");
                    return ;
                }
                 
            }
             
        }
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (!StringSupport.equals(listQueue[gridMain.getSelectedIndices()[i]].MissingData, ""))
            {
                MsgBox.show(this,"Not allowed to send e-claims with missing information.");
                return ;
            }
             
            if (listQueue[gridMain.getSelectedIndices()[i]].NoSendElect)
            {
                MsgBox.show(this,"Not allowed to send paper claims electronically.");
                return ;
            }
             
        }
        List<ClaimSendQueueItem> queueItems = new List<ClaimSendQueueItem>();
        //a list of queue items to send
        ClaimSendQueueItem queueitem;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            queueitem = listQueue[gridMain.getSelectedIndices()[i]].Copy();
            if (clearinghouseNum != 0)
            {
                queueitem.ClearinghouseNum = clearinghouseNum;
            }
             
            queueItems.Add(queueitem);
        }
        Clearinghouse clearhouse = ClearinghouseL.GetClearinghouse(queueItems[0].ClearinghouseNum);
        EnumClaimMedType medType = Claims.GetClaim(listQueue[gridMain.getSelectedIndices()[0]].ClaimNum).MedType;
        //Already validated that all claims are for the same clearinghouse, clinic, and medType.
        //Validated that medtype matches clearinghouse e-format
        Cursor = Cursors.WaitCursor;
        Eclaims.SendBatch(queueItems, clearhouse, medType);
        Cursor = Cursors.Default;
        //statuses changed to S in SendBatches
        fillGrid();
        fillHistory();
        for (int i = 0;i < queueItems.Count;i++)
        {
            for (int j = 0;j < tableHistory.Rows.Count;j++)
            {
                //Now, the cool part.  Highlight all the claims that were just sent in the history grid
                long claimNum = PIn.Long(tableHistory.Rows[j]["ClaimNum"].ToString());
                if (claimNum == queueItems[i].ClaimNum)
                {
                    gridHistory.setSelected(j,true);
                    break;
                }
                 
            }
        }
    }

    private void toolBarButReports_Click() throws Exception {
        FormClaimReports FormC = new FormClaimReports();
        FormC.ShowDialog();
        fillHistory();
    }

    //To show 277s after imported.
    private void toolBarButOutstanding_Click() throws Exception {
        FormCanadaOutstandingTransactions fcot = new FormCanadaOutstandingTransactions();
        fcot.ShowDialog();
    }

    private void toolBarButPayRec_Click() throws Exception {
        FormCanadaPaymentReconciliation fcpr = new FormCanadaPaymentReconciliation();
        fcpr.ShowDialog();
    }

    private void toolBarButSummaryRec_Click() throws Exception {
        FormCanadaSummaryReconciliation fcsr = new FormCanadaSummaryReconciliation();
        fcsr.ShowDialog();
    }

    private void comboClinic_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboCustomTracking_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillHistory() throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = new DateTime();
        if (StringSupport.equals(textDateTo.Text, ""))
        {
            dateTo = DateTime.MaxValue;
        }
        else
        {
            dateTo = PIn.Date(textDateTo.Text);
        } 
        tableHistory = Etranss.refreshHistory(dateFrom,dateTo);
        //listQueue=Claims.GetQueueList();
        gridHistory.beginUpdate();
        gridHistory.getColumns().Clear();
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            ODGridColumn col;
            col = new ODGridColumn(Lan.g("TableClaimHistory","Patient Name"),130);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Carrier Name"),170);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Clearinghouse"),90);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Date"),70);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Type"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","AckCode"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Note"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Office#"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","CarrierCount"),0);
            gridHistory.getColumns().add(col);
            gridHistory.getRows().Clear();
            OpenDental.UI.ODGridRow row;
            for (int i = 0;i < tableHistory.Rows.Count;i++)
            {
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(tableHistory.Rows[i]["patName"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["CarrierName"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["Clearinghouse"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["dateTimeTrans"].ToString());
                //((DateTime)tableHistory.Rows[i]["DateTimeTrans"]).ToShortDateString());
                //still need to trim the _CA
                row.getCells().Add(tableHistory.Rows[i]["etype"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["ack"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["Note"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["OfficeSequenceNumber"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["CarrierTransCounter"].ToString());
                gridHistory.getRows().add(row);
            }
        }
        else
        {
            ODGridColumn col;
            col = new ODGridColumn(Lan.g("TableClaimHistory","Patient Name"),130);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Carrier Name"),170);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Clearinghouse"),90);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Date"),70);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Type"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","AckCode"),100);
            gridHistory.getColumns().add(col);
            col = new ODGridColumn(Lan.g("TableClaimHistory","Note"),0);
            gridHistory.getColumns().add(col);
            gridHistory.getRows().Clear();
            OpenDental.UI.ODGridRow row;
            for (int i = 0;i < tableHistory.Rows.Count;i++)
            {
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(tableHistory.Rows[i]["patName"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["CarrierName"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["Clearinghouse"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["dateTimeTrans"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["etype"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["ack"].ToString());
                row.getCells().Add(tableHistory.Rows[i]["Note"].ToString());
                gridHistory.getRows().add(row);
            }
        } 
        gridHistory.endUpdate();
        gridHistory.scrollToEnd();
    }

    private void panelSplitter_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = true;
        SplitterOriginalY = panelSplitter.Top;
        OriginalMouseY = panelSplitter.Top + e.Y;
    }

    private void panelSplitter_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnSplitter)
            return ;
         
        int splitterNewY = SplitterOriginalY + (panelSplitter.Top + e.Y) - OriginalMouseY;
        if (splitterNewY < 130)
            //keeps it from going too high
            splitterNewY = 130;
         
        if (splitterNewY > Height - 130)
            //keeps it from going off the bottom edge
            splitterNewY = Height - 130;
         
        panelSplitter.Top = splitterNewY;
        adjustPanelSplit();
    }

    private void adjustPanelSplit() throws Exception {
        gridMain.Height = panelSplitter.Top - gridMain.Top;
        panelHistory.Top = panelSplitter.Bottom;
        panelHistory.Height = this.ClientSize.Height - panelHistory.Top - 1;
    }

    private void panelSplitter_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = false;
    }

    private void butDropFrom_Click(Object sender, EventArgs e) throws Exception {
        toggleCalendars();
    }

    private void butDropTo_Click(Object sender, EventArgs e) throws Exception {
        toggleCalendars();
    }

    private void toggleCalendars() throws Exception {
        if (calendarFrom.Visible)
        {
            //hide the calendars
            calendarFrom.Visible = false;
            calendarTo.Visible = false;
        }
        else
        {
            //set the date on the calendars to match what's showing in the boxes
            if (StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") && StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
            {
                //if no date errors
                if (StringSupport.equals(textDateFrom.Text, ""))
                {
                    calendarFrom.SetDate(DateTime.Today);
                }
                else
                {
                    calendarFrom.SetDate(PIn.Date(textDateFrom.Text));
                } 
                if (StringSupport.equals(textDateTo.Text, ""))
                {
                    calendarTo.SetDate(DateTime.Today);
                }
                else
                {
                    calendarTo.SetDate(PIn.Date(textDateTo.Text));
                } 
            }
             
            //show the calendars
            calendarFrom.Visible = true;
            calendarTo.Visible = true;
        } 
    }

    private void calendarFrom_DateSelected(Object sender, DateRangeEventArgs e) throws Exception {
        textDateFrom.Text = calendarFrom.SelectionStart.ToShortDateString();
    }

    private void calendarTo_DateSelected(Object sender, DateRangeEventArgs e) throws Exception {
        textDateTo.Text = calendarTo.SelectionStart.ToShortDateString();
    }

    private void toolBarHistory_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar2 = e.getButton().getTag().ToString();
        if (__dummyScrutVar2.equals("Refresh"))
        {
            refreshHistory_Click();
        }
        else if (__dummyScrutVar2.equals("Undo"))
        {
            undo_Click();
        }
        else if (__dummyScrutVar2.equals("PrintList"))
        {
            printHistory_Click();
        }
        else if (__dummyScrutVar2.equals("PrintItem"))
        {
            printItem_Click();
        }
            
    }

    private void refreshHistory_Click() throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        calendarFrom.Visible = false;
        calendarTo.Visible = false;
        fillHistory();
    }

    private void undo_Click() throws Exception {
        if (gridHistory.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one item first.");
            return ;
        }
         
        if (gridHistory.getSelectedIndices().Length > 1)
        {
            //if there are multiple items selected.
            //then they must all be Claim_Ren, ClaimSent, or ClaimPrinted
            EtransType etype = EtransType.ClaimSent;
            for (int i = 0;i < gridHistory.getSelectedIndices().Length;i++)
            {
                etype = (EtransType)PIn.Long(tableHistory.Rows[gridHistory.getSelectedIndices()[i]]["Etype"].ToString());
                if (etype != EtransType.Claim_Ren && etype != EtransType.ClaimSent && etype != EtransType.ClaimPrinted)
                {
                    MsgBox.show(this,"That type of transaction cannot be undone as a group.  Please undo one at a time.");
                    return ;
                }
                 
            }
        }
         
        for (int i = 0;i < gridHistory.getSelectedIndices().Length;i++)
        {
            //loop through each selected item, and see if they are allowed to be "undone".
            //at this point,
            if ((EtransType)PIn.Long(tableHistory.Rows[gridHistory.getSelectedIndices()[i]]["Etype"].ToString()) == EtransType.Claim_CA)
            {
            }
             
        }
        //if a
        //else if(){
        //}
        if (!MsgBox.show(this,true,"Remove the selected claims from the history list, and change the claim status from 'Sent' back to 'Waiting to Send'?"))
        {
            return ;
        }
         
        for (int i = 0;i < gridHistory.getSelectedIndices().Length;i++)
        {
            Etranss.Undo(PIn.Long(tableHistory.Rows[gridHistory.getSelectedIndices()[i]]["EtransNum"].ToString()));
        }
        fillGrid();
        fillHistory();
    }

    private void printHistory_Click() throws Exception {
        pagesPrinted = 0;
        //headingPrinted=false;
        printReport(false);
    }

    private void gridHistory_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Etrans et = Etranss.GetEtrans(PIn.Long(tableHistory.Rows[e.getRow()]["EtransNum"].ToString()));
        if (et.Etype == EtransType.StatusNotify_277)
        {
            FormEtrans277Edit Form277 = new FormEtrans277Edit();
            Form277.EtransCur = et;
            Form277.ShowDialog();
            return ;
        }
         
        //No refresh needed because 277 are not editable, they are read only.
        FormEtransEdit FormE = new FormEtransEdit();
        FormE.EtransCur = et;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        int scroll = gridHistory.getScrollValue();
        fillHistory();
        for (int i = 0;i < tableHistory.Rows.Count;i++)
        {
            if (tableHistory.Rows[i]["EtransNum"].ToString() == et.EtransNum.ToString())
            {
                gridHistory.setSelected(i,true);
            }
             
        }
        gridHistory.setScrollValue(scroll);
    }

    private void showRawMessage_Clicked(Object sender, System.EventArgs e) throws Exception {
    }

    //accessed by right clicking on history
    /**
    * Preview is only used for debugging.
    */
    public void printReport(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.OriginAtMargins = true;
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            //isPrinting=true;
            //FillGrid();
            if (justPreview)
            {
                FormRpPrintPreview pView = new FormRpPrintPreview();
                pView.printPreviewControl2.Document = pd2;
                pView.ShowDialog();
            }
            else
            {
                if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Claim history list printed"))
                {
                    pd2.Print();
                }
                 
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    //isPrinting=false;
    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = new Rectangle(50, 40, 800, 1035);
        //Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Claim History");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = textDateFrom.Text + " " + Lan.g(this,"to") + " " + textDateTo.Text;
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridHistory.printPage(g,pagesPrinted,bounds,headingPrintH);
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

    private void printItem_Click() throws Exception {
        //not currently accessible
        if (gridHistory.getRows().Count == 0)
        {
            MsgBox.show(this,"There are no items to print.");
            return ;
        }
         
        if (gridHistory.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one item first.");
            return ;
        }
         
        //does not yet handle multiple selections
        Etrans etrans = Etranss.GetEtrans(PIn.Long(tableHistory.Rows[gridHistory.getSelectedIndices()[0]]["EtransNum"].ToString()));
        new FormCCDPrint(etrans,EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum),false);
    }

}


//Show the form and allow the user to print manually if desired.
//MessageBox.Show(etrans.MessageText);