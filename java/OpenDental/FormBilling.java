//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:43 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormBilling;
import OpenDental.FormPatienteBill;
import OpenDental.FormRpStatement;
import OpenDental.FormStatementOptions;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PatientSelectedEventArgs;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AccountModules;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Statements;

/**
* 
*/
public class FormBilling  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butAll;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.Button butSend;
    private IContainer components = new IContainer();
    private OpenDental.UI.ODGrid gridBill;
    private Label labelTotal = new Label();
    private Label label1 = new Label();
    private RadioButton radioUnsent = new RadioButton();
    private RadioButton radioSent = new RadioButton();
    private GroupBox groupBox1 = new GroupBox();
    private Label labelSelected = new Label();
    private Label labelEmailed = new Label();
    private Label labelPrinted = new Label();
    private OpenDental.UI.Button butEdit;
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.ValidDate textDateStart;
    private Label label2 = new Label();
    private Label label3 = new Label();
    private OpenDental.UI.Button butRefresh;
    private ComboBox comboOrder = new ComboBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butPrintList;
    private ContextMenuStrip contextMenu = new ContextMenuStrip();
    private ToolStripMenuItem menuItemGoTo = new ToolStripMenuItem();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private Label label5 = new Label();
    private int pagesPrinted = new int();
    /**
    * Used in the Activated event.
    */
    private boolean isPrinting = false;
    private DataTable table = new DataTable();
    private Label labelSentElect = new Label();
    /**
    * 
    */
    public PatientSelectedEventHandler GoToChanged = null;
    private boolean isInitial = true;
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private boolean ignoreRefreshOnce = new boolean();
    /**
    * ClinicNum should be set before this window by FormBillingOptions.
    */
    public long ClinicNum = new long();
    protected void onGoToChanged(long patNum) throws Exception {
        if (GoToChanged != null)
        {
            Patient pat = Patients.getPat(patNum);
            GoToChanged.invoke(this,new PatientSelectedEventArgs(pat));
        }
         
    }

    /**
    * 
    */
    public FormBilling() throws Exception {
        initializeComponent();
        //this.listMain.ContextMenu = this.menuEdit;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormBilling.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butSend = new OpenDental.UI.Button();
        this.butNone = new OpenDental.UI.Button();
        this.butAll = new OpenDental.UI.Button();
        this.gridBill = new OpenDental.UI.ODGrid();
        this.contextMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemGoTo = new System.Windows.Forms.ToolStripMenuItem();
        this.labelTotal = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.radioUnsent = new System.Windows.Forms.RadioButton();
        this.radioSent = new System.Windows.Forms.RadioButton();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelSentElect = new System.Windows.Forms.Label();
        this.labelEmailed = new System.Windows.Forms.Label();
        this.labelPrinted = new System.Windows.Forms.Label();
        this.labelSelected = new System.Windows.Forms.Label();
        this.butEdit = new OpenDental.UI.Button();
        this.textDateEnd = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.comboOrder = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butPrintList = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.contextMenu.SuspendLayout();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(802, 656);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 1;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSend.setAutosize(true);
        this.butSend.BackColor = System.Drawing.SystemColors.Control;
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(802, 622);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 24);
        this.butSend.TabIndex = 0;
        this.butSend.Text = "Send";
        this.butSend.UseVisualStyleBackColor = false;
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.Location = new System.Drawing.Point(103, 656);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(75, 24);
        this.butNone.TabIndex = 23;
        this.butNone.Text = "&None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(12, 656);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(75, 24);
        this.butAll.TabIndex = 22;
        this.butAll.Text = "&All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // gridBill
        //
        this.gridBill.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridBill.ContextMenuStrip = this.contextMenu;
        this.gridBill.setHScrollVisible(false);
        this.gridBill.Location = new System.Drawing.Point(12, 58);
        this.gridBill.Name = "gridBill";
        this.gridBill.setScrollValue(0);
        this.gridBill.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridBill.Size = new System.Drawing.Size(772, 590);
        this.gridBill.TabIndex = 28;
        this.gridBill.setTitle("Bills");
        this.gridBill.setTranslationName("TableBilling");
        this.gridBill.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridBill.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridBill_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridBill.CellClick = __MultiODGridClickEventHandler.combine(this.gridBill.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridBill_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridBill.MouseDown += new System.Windows.Forms.MouseEventHandler(this.gridBill_MouseDown);
        //
        // contextMenu
        //
        this.contextMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemGoTo });
        this.contextMenu.Name = "contextMenu";
        this.contextMenu.Size = new System.Drawing.Size(108, 26);
        //
        // menuItemGoTo
        //
        this.menuItemGoTo.Name = "menuItemGoTo";
        this.menuItemGoTo.Size = new System.Drawing.Size(107, 22);
        this.menuItemGoTo.Text = "Go To";
        this.menuItemGoTo.Click += new System.EventHandler(this.menuItemGoTo_Click);
        //
        // labelTotal
        //
        this.labelTotal.Location = new System.Drawing.Point(4, 19);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(89, 16);
        this.labelTotal.TabIndex = 29;
        this.labelTotal.Text = "Total=20";
        this.labelTotal.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(787, 530);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(91, 87);
        this.label1.TabIndex = 30;
        this.label1.Text = "This will immediately print or email all selected bills";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // radioUnsent
        //
        this.radioUnsent.Checked = true;
        this.radioUnsent.Location = new System.Drawing.Point(31, 7);
        this.radioUnsent.Name = "radioUnsent";
        this.radioUnsent.Size = new System.Drawing.Size(75, 20);
        this.radioUnsent.TabIndex = 31;
        this.radioUnsent.TabStop = true;
        this.radioUnsent.Text = "Unsent";
        this.radioUnsent.UseVisualStyleBackColor = true;
        this.radioUnsent.Click += new System.EventHandler(this.radioUnsent_Click);
        //
        // radioSent
        //
        this.radioSent.Location = new System.Drawing.Point(31, 29);
        this.radioSent.Name = "radioSent";
        this.radioSent.Size = new System.Drawing.Size(79, 20);
        this.radioSent.TabIndex = 32;
        this.radioSent.Text = "Sent";
        this.radioSent.UseVisualStyleBackColor = true;
        this.radioSent.Click += new System.EventHandler(this.radioSent_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.labelSentElect);
        this.groupBox1.Controls.Add(this.labelEmailed);
        this.groupBox1.Controls.Add(this.labelPrinted);
        this.groupBox1.Controls.Add(this.labelSelected);
        this.groupBox1.Controls.Add(this.labelTotal);
        this.groupBox1.Location = new System.Drawing.Point(788, 233);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(96, 119);
        this.groupBox1.TabIndex = 33;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Counts";
        //
        // labelSentElect
        //
        this.labelSentElect.Location = new System.Drawing.Point(4, 95);
        this.labelSentElect.Name = "labelSentElect";
        this.labelSentElect.Size = new System.Drawing.Size(89, 16);
        this.labelSentElect.TabIndex = 33;
        this.labelSentElect.Text = "SentElect=20";
        this.labelSentElect.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelEmailed
        //
        this.labelEmailed.Location = new System.Drawing.Point(4, 76);
        this.labelEmailed.Name = "labelEmailed";
        this.labelEmailed.Size = new System.Drawing.Size(89, 16);
        this.labelEmailed.TabIndex = 32;
        this.labelEmailed.Text = "Emailed=20";
        this.labelEmailed.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelPrinted
        //
        this.labelPrinted.Location = new System.Drawing.Point(4, 57);
        this.labelPrinted.Name = "labelPrinted";
        this.labelPrinted.Size = new System.Drawing.Size(89, 16);
        this.labelPrinted.TabIndex = 31;
        this.labelPrinted.Text = "Printed=20";
        this.labelPrinted.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelSelected
        //
        this.labelSelected.Location = new System.Drawing.Point(4, 38);
        this.labelSelected.Name = "labelSelected";
        this.labelSelected.Size = new System.Drawing.Size(89, 16);
        this.labelSelected.TabIndex = 30;
        this.labelSelected.Text = "Selected=20";
        this.labelSelected.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Location = new System.Drawing.Point(796, 67);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(82, 24);
        this.butEdit.TabIndex = 34;
        this.butEdit.Text = "Edit Selected";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(650, 32);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 38;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(651, 8);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 37;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(584, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(64, 14);
        this.label2.TabIndex = 36;
        this.label2.Text = "End Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(578, 11);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(70, 14);
        this.label3.TabIndex = 35;
        this.label3.Text = "Start Date";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(796, 7);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(82, 24);
        this.butRefresh.TabIndex = 39;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // comboOrder
        //
        this.comboOrder.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboOrder.Location = new System.Drawing.Point(195, 8);
        this.comboOrder.MaxDropDownItems = 40;
        this.comboOrder.Name = "comboOrder";
        this.comboOrder.Size = new System.Drawing.Size(133, 21);
        this.comboOrder.TabIndex = 41;
        this.comboOrder.SelectionChangeCommitted += new System.EventHandler(this.comboOrder_SelectionChangeCommitted);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(110, 12);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(83, 14);
        this.label4.TabIndex = 40;
        this.label4.Text = "Order by";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butPrintList
        //
        this.butPrintList.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintList.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrintList.setAutosize(true);
        this.butPrintList.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintList.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintList.setCornerRadius(4F);
        this.butPrintList.Image = Resources.getbutPrint();
        this.butPrintList.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintList.Location = new System.Drawing.Point(364, 656);
        this.butPrintList.Name = "butPrintList";
        this.butPrintList.Size = new System.Drawing.Size(88, 24);
        this.butPrintList.TabIndex = 42;
        this.butPrintList.Text = "Print List";
        this.butPrintList.Click += new System.EventHandler(this.butPrintList_Click);
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label5.Location = new System.Drawing.Point(456, 651);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(165, 29);
        this.label5.TabIndex = 43;
        this.label5.Text = "Does not print individual bills.  Just prints the list of bills.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(409, 8);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(133, 21);
        this.comboClinic.TabIndex = 44;
        this.comboClinic.Visible = false;
        this.comboClinic.SelectionChangeCommitted += new System.EventHandler(this.comboClinic_SelectionChangeCommitted);
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(334, 13);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(73, 14);
        this.labelClinic.TabIndex = 45;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelClinic.Visible = false;
        //
        // FormBilling
        //
        this.AcceptButton = this.butSend;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(888, 688);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboOrder);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.butPrintList);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.textDateEnd);
        this.Controls.Add(this.radioUnsent);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.radioSent);
        this.Controls.Add(this.gridBill);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butAll);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormBilling";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Bills";
        this.Activated += new System.EventHandler(this.FormBilling_Activated);
        this.Load += new System.EventHandler(this.FormBilling_Load);
        this.contextMenu.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formBilling_Load(Object sender, System.EventArgs e) throws Exception {
        labelPrinted.Text = Lan.g(this,"Printed=") + "0";
        labelEmailed.Text = Lan.g(this,"E-mailed=") + "0";
        labelSentElect.Text = Lan.g(this,"SentElect=") + "0";
        comboOrder.Items.Add(Lan.g(this,"BillingType"));
        comboOrder.Items.Add(Lan.g(this,"PatientName"));
        comboOrder.SelectedIndex = 0;
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            //Using clinics.
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
         
    }

    private void formBilling_Activated(Object sender, EventArgs e) throws Exception {
        //this gets fired very frequently, including right in the middle of printing a batch.
        if (isPrinting)
        {
            return ;
        }
         
        if (ignoreRefreshOnce)
        {
            ignoreRefreshOnce = false;
            return ;
        }
         
        fillGrid();
    }

    /**
    * We will always try to preserve the selected bills as well as the scroll postition.
    */
    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            ignoreRefreshOnce = true;
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        int scrollPos = gridBill.getScrollValue();
        List<long> selectedKeys = new List<long>();
        for (int i = 0;i < gridBill.getSelectedIndices().Length;i++)
        {
            selectedKeys.Add(PIn.Long(table.Rows[gridBill.getSelectedIndices()[i]]["StatementNum"].ToString()));
        }
        DateTime dateFrom = DateTime.MinValue;
        DateTime dateTo = new DateTime(2200, 1, 1);
        if (!StringSupport.equals(textDateStart.Text, ""))
        {
            dateFrom = PIn.Date(textDateStart.Text);
        }
         
        if (!StringSupport.equals(textDateEnd.Text, ""))
        {
            dateTo = PIn.Date(textDateEnd.Text);
        }
         
        ClinicNum = 0;
        if (comboClinic.SelectedIndex > 0)
        {
            ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        table = Statements.GetBilling(radioSent.Checked, comboOrder.SelectedIndex, dateFrom, dateTo, ClinicNum);
        gridBill.beginUpdate();
        gridBill.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableBilling","Name"),180);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","BillType"),110);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","Mode"),80);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","LastStatement"),100);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","BalTot"), 70, HorizontalAlignment.Right);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","-InsEst"), 70, HorizontalAlignment.Right);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","=AmtDue"), 70, HorizontalAlignment.Right);
        gridBill.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableBilling","PayPlanDue"), 70, HorizontalAlignment.Right);
        gridBill.getColumns().add(col);
        gridBill.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["name"].ToString());
            row.getCells().Add(table.Rows[i]["billingType"].ToString());
            row.getCells().Add(table.Rows[i]["mode"].ToString());
            row.getCells().Add(table.Rows[i]["lastStatement"].ToString());
            row.getCells().Add(table.Rows[i]["balTotal"].ToString());
            row.getCells().Add(table.Rows[i]["insEst"].ToString());
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(table.Rows[i]["amountDue"].ToString());
            } 
            row.getCells().Add(table.Rows[i]["payPlanDue"].ToString());
            gridBill.getRows().add(row);
        }
        gridBill.endUpdate();
        if (isInitial)
        {
            gridBill.setSelected(true);
            isInitial = false;
        }
        else
        {
            for (int i = 0;i < selectedKeys.Count;i++)
            {
                for (int j = 0;j < table.Rows.Count;j++)
                {
                    if (table.Rows[j]["StatementNum"].ToString() == selectedKeys[i].ToString())
                    {
                        gridBill.setSelected(j,true);
                    }
                     
                }
            }
        } 
        gridBill.setScrollValue(scrollPos);
        labelTotal.Text = Lan.g(this,"Total=") + table.Rows.Count.ToString();
        labelSelected.Text = Lan.g(this,"Selected=") + gridBill.getSelectedIndices().Length.ToString();
    }

    //labelSelected.Text=Lan.g(this,"Selected=")+"0";
    private void butAll_Click(Object sender, System.EventArgs e) throws Exception {
        gridBill.setSelected(true);
        labelSelected.Text = Lan.g(this,"Selected=") + gridBill.getSelectedIndices().Length.ToString();
    }

    private void butNone_Click(Object sender, System.EventArgs e) throws Exception {
        gridBill.setSelected(false);
        labelSelected.Text = Lan.g(this,"Selected=") + gridBill.getSelectedIndices().Length.ToString();
    }

    private void radioUnsent_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void radioSent_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

    private void comboOrder_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboClinic_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            ignoreRefreshOnce = true;
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        fillGrid();
    }

    private void gridBill_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        labelSelected.Text = Lan.g(this,"Selected=") + gridBill.getSelectedIndices().Length.ToString();
    }

    private void gridBill_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormStatementOptions FormSO = new FormStatementOptions();
        Statement stmt;
        stmt = Statements.CreateObject(PIn.Long(table.Rows[e.getRow()]["StatementNum"].ToString()));
        //FormSO.StmtList=stmtList;
        FormSO.StmtCur = stmt;
        FormSO.ShowDialog();
    }

    private void gridBill_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Right)
        {
            gridBill.setSelected(false);
        }
         
    }

    private void menuItemGoTo_Click(Object sender, EventArgs e) throws Exception {
        if (gridBill.getSelectedIndices().Length == 0)
        {
            //I don't think this could happen
            MsgBox.show(this,"Please select one bill first.");
            return ;
        }
        else
        {
            long patNum = PIn.Long(table.Rows[gridBill.getSelectedIndices()[0]]["PatNum"].ToString());
            onGoToChanged(patNum);
            SendToBack();
        } 
    }

    //??
    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        if (gridBill.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select one or more bills first.");
            return ;
        }
         
        FormStatementOptions FormSO = new FormStatementOptions();
        List<Statement> stmtList = new List<Statement>();
        Statement stmt;
        for (int i = 0;i < gridBill.getSelectedIndices().Length;i++)
        {
            stmt = Statements.CreateObject(PIn.Long(table.Rows[gridBill.getSelectedIndices()[i]]["StatementNum"].ToString()));
            stmtList.Add(stmt.copy());
        }
        FormSO.StmtList = stmtList;
        //Statement stmt=new Statement();
        //stmt.DateRangeFrom=DateTime.
        //FormSO.StmtCur=stmt;
        FormSO.ShowDialog();
    }

    //FillGrid happens automatically through Activated event.
    private void butPrintList_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Billing list printed"))
        {
            return ;
        }
         
        try
        {
            pd.Print();
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Printer not available");
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
            text = Lan.g(this,"Billing List");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
            //text=textDateFrom.Text+" "+Lan.g(this,"to")+" "+textDateTo.Text;
            //g.DrawString(text,subHeadingFont,Brushes.Black,center-g.MeasureString(text,subHeadingFont).Width/2,yPos);
            yPos += 25;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridBill.printPage(g,pagesPrinted,bounds,headingPrintH);
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

    private void butSend_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridBill.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select items first."));
            return ;
        }
         
        labelPrinted.Text = Lan.g(this,"Printed=") + "0";
        labelEmailed.Text = Lan.g(this,"E-mailed=") + "0";
        labelSentElect.Text = Lan.g(this,"SentElect=") + "0";
        if (!MsgBox.show(this,true,"Please be prepared to wait up to ten minutes while all the bills get processed.\r\nOnce complete, the pdf print preview will be launched in Adobe Reader.  You will print from that program.  Continue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        isPrinting = true;
        FormRpStatement FormST = new FormRpStatement();
        Statement stmt;
        Random rnd = new Random();
        String fileName = new String();
        String filePathAndName = new String();
        String attachPath = new String();
        EmailMessage message;
        EmailAttach attach;
        EmailAddress emailAddress;
        Family fam;
        Patient pat;
        Clinic clinic;
        String patFolder = new String();
        int skipped = 0;
        int skippedElect = 0;
        int emailed = 0;
        int printed = 0;
        int sentelect = 0;
        //FormEmailMessageEdit FormEME=new FormEmailMessageEdit();
        //if(ImageStore.UpdatePatient == null){
        //	ImageStore.UpdatePatient = new FileStore.UpdatePatientDelegate(Patients.Update);
        //}
        //OpenDental.Imaging.ImageStoreBase imageStore;
        //Concat all the pdf's together to create one print job.
        //Also, if a statement is to be emailed, it does that here and does not attach it to the print job.
        //If something fails badly, it's no big deal, because we can click the radio button to see "sent" bills, and unsend them from there.
        PdfDocument outputDocument = new PdfDocument();
        PdfDocument inputDocument = new PdfDocument();
        PdfPage page = new PdfPage();
        String savedPdfPath = new String();
        PrintDocument pd = null;
        XmlWriterSettings xmlSettings = new XmlWriterSettings();
        xmlSettings.OmitXmlDeclaration = true;
        xmlSettings.Encoding = Encoding.UTF8;
        xmlSettings.Indent = true;
        xmlSettings.IndentChars = "   ";
        StringBuilder strBuildElect = new StringBuilder();
        XmlWriter writerElect = XmlWriter.Create(strBuildElect, xmlSettings);
        if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "1"))
        {
            OpenDental.Bridges.EHG_statements.generatePracticeInfo(writerElect);
        }
        else if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "2"))
        {
            OpenDental.Bridges.POS_statements.generatePracticeInfo(writerElect);
        }
        else if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "3"))
        {
            OpenDental.Bridges.ClaimX_Statements.generatePracticeInfo(writerElect);
        }
           
        DataSet dataSet = new DataSet();
        List<long> stateNumsElect = new List<long>();
        for (int i = 0;i < gridBill.getSelectedIndices().Length;i++)
        {
            stmt = Statements.CreateObject(PIn.Long(table.Rows[gridBill.getSelectedIndices()[i]]["StatementNum"].ToString()));
            fam = Patients.getFamily(stmt.PatNum);
            pat = fam.getPatient(stmt.PatNum);
            patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
            dataSet = AccountModules.getStatementDataSet(stmt);
            emailAddress = EmailAddresses.getByClinic(pat.ClinicNum);
            if (stmt.Mode_ == StatementMode.Email)
            {
                if (StringSupport.equals(emailAddress.SMTPserver, ""))
                {
                    MsgBox.show(this,"You need to enter an SMTP server name in e-mail setup before you can send e-mail.");
                    Cursor = Cursors.Default;
                    isPrinting = false;
                    return ;
                }
                 
                //FillGrid();//automatic
                if (StringSupport.equals(pat.Email, ""))
                {
                    skipped++;
                    continue;
                }
                 
            }
             
            stmt.IsSent = true;
            stmt.DateSent = DateTimeOD.getToday();
            FormST.createStatementPdf(stmt,pat,fam,dataSet);
            if (stmt.DocNum == 0)
            {
                MsgBox.show(this,"Failed to save PDF.  In Setup, DataPaths, please make sure the top radio button is checked.");
                Cursor = Cursors.Default;
                isPrinting = false;
                return ;
            }
             
            //imageStore = OpenDental.Imaging.ImageStore.GetImageStore(pat);
            savedPdfPath = ImageStore.getFilePath(Documents.getByNum(stmt.DocNum),patFolder);
            if (stmt.Mode_ == StatementMode.InPerson || stmt.Mode_ == StatementMode.Mail)
            {
                if (pd == null)
                {
                    pd = new PrintDocument();
                }
                 
                inputDocument = PdfReader.Open(savedPdfPath, PdfDocumentOpenMode.Import);
                for (int idx = 0;idx < inputDocument.PageCount;idx++)
                {
                    page = inputDocument.Pages[idx];
                    outputDocument.AddPage(page);
                }
                printed++;
                labelPrinted.Text = Lan.g(this,"Printed=") + printed.ToString();
                Application.DoEvents();
                Statements.markSent(stmt.StatementNum,stmt.DateSent);
            }
             
            if (stmt.Mode_ == StatementMode.Email)
            {
                attachPath = EmailMessages.getEmailAttachPath();
                rnd = new Random();
                fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
                filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
                File.Copy(savedPdfPath, filePathAndName);
                //Process.Start(filePathAndName);
                message = Statements.getEmailMessageForStatement(stmt,pat);
                attach = new EmailAttach();
                attach.DisplayedFileName = "Statement.pdf";
                attach.ActualFileName = fileName;
                message.Attachments.Add(attach);
                try
                {
                    EmailMessages.sendEmailUnsecure(message,emailAddress);
                    message.SentOrReceived = EmailSentOrReceived.Sent;
                    message.MsgDateTime = DateTime.Now;
                    EmailMessages.insert(message);
                    emailed++;
                    labelEmailed.Text = Lan.g(this,"E-mailed=") + emailed.ToString();
                    Application.DoEvents();
                }
                catch (Exception __dummyCatchVar1)
                {
                    //Cursor=Cursors.Default;
                    //MessageBox.Show(ex.Message);
                    //return;
                    skipped++;
                    continue;
                }

                Statements.markSent(stmt.StatementNum,stmt.DateSent);
            }
             
            if (stmt.Mode_ == StatementMode.Electronic)
            {
                Patient guar = fam.ListPats[0];
                if (StringSupport.equals(guar.Address.Trim(), "") || StringSupport.equals(guar.City.Trim(), "") || StringSupport.equals(guar.State.Trim(), "") || StringSupport.equals(guar.Zip.Trim(), ""))
                {
                    skippedElect++;
                    continue;
                }
                 
                boolean statementWritten = true;
                if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "1"))
                {
                    try
                    {
                        OpenDental.Bridges.EHG_statements.generateOneStatement(writerElect,stmt,pat,fam,dataSet);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(Lan.g(this,"Error sending statement") + ": " + Environment.NewLine + ex.ToString());
                        statementWritten = false;
                    }
                
                }
                else if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "2"))
                {
                    OpenDental.Bridges.POS_statements.generateOneStatement(writerElect,stmt,pat,fam,dataSet);
                }
                else if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "3"))
                {
                    OpenDental.Bridges.ClaimX_Statements.generateOneStatement(writerElect,stmt,pat,fam,dataSet);
                }
                   
                if (statementWritten)
                {
                    stateNumsElect.Add(stmt.StatementNum);
                    sentelect++;
                    labelSentElect.Text = Lan.g(this,"SentElect=") + sentelect.ToString();
                    Application.DoEvents();
                }
                 
            }
             
        }
        //do this later:
        //Statements.MarkSent(stmt.StatementNum,stmt.DateSent);
        //now print-------------------------------------------------------------------------------------
        if (pd != null)
        {
            String tempFileOutputDocument = Path.GetTempFileName() + ".pdf";
            outputDocument.Save(tempFileOutputDocument);
            try
            {
                Process.Start(tempFileOutputDocument);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Error: Please make sure Adobe Reader is installed.") + ex.Message);
            }
        
        }
         
        //}
        //finish up elect and send if needed------------------------------------------------------------
        if (sentelect > 0)
        {
            if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "1"))
            {
                OpenDental.Bridges.EHG_statements.generateWrapUp(writerElect);
                writerElect.Close();
                try
                {
                    //OpenDental.Bridges.Tesia_statements.Send(strBuildElect.ToString());
                    OpenDental.Bridges.EHG_statements.Send(strBuildElect.ToString());
                    for (int i = 0;i < stateNumsElect.Count;i++)
                    {
                        //CodeBase.MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(strBuildElect.ToString());
                        //msgbox.ShowDialog();
                        //loop through all statements and mark sent
                        Statements.MarkSent(stateNumsElect[i], DateTimeOD.getToday());
                    }
                }
                catch (Exception ex)
                {
                    MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(ex.Message);
                    msgbox.ShowDialog();
                    //MessageBox.Show();
                    sentelect = 0;
                    labelSentElect.Text = Lan.g(this,"SentElect=") + sentelect.ToString();
                }
            
            }
             
            if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "2"))
            {
                OpenDental.Bridges.POS_statements.generateWrapUp(writerElect);
                writerElect.Close();
                SaveFileDialog dlg = new SaveFileDialog();
                dlg.FileName = "Statements.xml";
                if (dlg.ShowDialog() != DialogResult.OK)
                {
                    sentelect = 0;
                    labelSentElect.Text = Lan.g(this,"SentElect=") + sentelect.ToString();
                }
                 
                File.WriteAllText(dlg.FileName, strBuildElect.ToString());
                for (int i = 0;i < stateNumsElect.Count;i++)
                {
                    Statements.MarkSent(stateNumsElect[i], DateTimeOD.getToday());
                }
            }
             
            if (StringSupport.equals(PrefC.getString(PrefName.BillingUseElectronic), "3"))
            {
                OpenDental.Bridges.ClaimX_Statements.generateWrapUp(writerElect);
                writerElect.Close();
                SaveFileDialog dlg = new SaveFileDialog();
                dlg.InitialDirectory = "C:\\StatementX\\";
                //Clint from ExtraDent requested this default path.
                if (!Directory.Exists(dlg.InitialDirectory))
                {
                    try
                    {
                        Directory.CreateDirectory(dlg.InitialDirectory);
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                    }
                
                }
                 
                dlg.FileName = "Statements.xml";
                if (dlg.ShowDialog() != DialogResult.OK)
                {
                    sentelect = 0;
                    labelSentElect.Text = Lan.g(this,"SentElect=") + sentelect.ToString();
                }
                 
                File.WriteAllText(dlg.FileName, strBuildElect.ToString());
                for (int i = 0;i < stateNumsElect.Count;i++)
                {
                    Statements.MarkSent(stateNumsElect[i], DateTimeOD.getToday());
                }
            }
             
        }
        else
        {
            writerElect.Close();
        } 
        String msg = "";
        if (skipped > 0)
        {
            msg += Lan.g(this,"Skipped due to missing or bad email address: ") + skipped.ToString() + "\r\n";
        }
         
        if (skippedElect > 0)
        {
            msg += Lan.g(this,"Skipped due to missing or bad mailing address: ") + skippedElect.ToString() + "\r\n";
        }
         
        msg += Lan.g(this,"Printed: ") + printed.ToString() + "\r\n" + Lan.g(this,"E-mailed: ") + emailed.ToString() + "\r\n" + Lan.g(this,"SentElect: ") + sentelect.ToString();
        MessageBox.Show(msg);
        Cursor = Cursors.Default;
        isPrinting = false;
        fillGrid();
    }

    //not automatic
    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        if (gridBill.getRows().Count > 0)
        {
            DialogResult result = MessageBox.Show(Lan.g(this,"You may leave this window open while you work.  If you do close it, do you want to delete all unsent bills?"), "", MessageBoxButtons.YesNoCancel);
            if (result == DialogResult.Yes)
            {
                int rowsChanged = 0;
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    if (StringSupport.equals(table.Rows[i]["IsSent"].ToString(), "0"))
                    {
                        Statements.DeleteObject(PIn.Long(table.Rows[i]["StatementNum"].ToString()));
                        rowsChanged++;
                    }
                     
                }
                //This is not an accurate permission type.
                SecurityLogs.MakeLogEntry(Permissions.Accounting, 0, "Billing: Unsent statements were deleted.");
                MessageBox.Show(Lan.g(this,"Unsent statements deleted: ") + rowsChanged.ToString());
            }
            else if (result == DialogResult.No)
            {
                DialogResult = DialogResult.Cancel;
                Close();
            }
            else
            {
                return ;
            }  
        }
         
        //cancel
        DialogResult = DialogResult.Cancel;
        Close();
    }

    /**
    * 
    */
    private void butSendEbill_Click(Object sender, EventArgs e) throws Exception {
        if (gridBill.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select items first."));
            return ;
        }
         
        Cursor.Current = Cursors.WaitCursor;
        // Populate Array And Open eBill Form
        ArrayList PatientList = new ArrayList();
        for (int i = 0;i < gridBill.getSelectedIndices().Length;i++)
            PatientList.Add(PIn.Long(table.Rows[gridBill.getSelectedIndices()[i]]["PatNum"].ToString()));
        // Open eBill form
        FormPatienteBill FormPatienteBill = new FormPatienteBill(PatientList);
        FormPatienteBill.ShowDialog();
        Cursor.Current = Cursors.Default;
    }

}


