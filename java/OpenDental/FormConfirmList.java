//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:54 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormApptEdit;
import OpenDental.FormConfirmList;
import OpenDental.FormRpConfirm;
import OpenDental.FormTxtMsgEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Security;
import OpenDentBusiness.YN;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormConfirmList  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    /**
    * Will be set to true when form closes if user click Send to Pinboard.
    */
    public boolean PinClicked = false;
    private OpenDental.UI.Button butReport;
    private int pagesPrinted = new int();
    private DataTable AddrTable = new DataTable();
    private int patientsPrinted = new int();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ComboBox comboStatus = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butLabels;
    private OpenDental.UI.Button butPostcards;
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private OpenDental.UI.ODGrid grid;
    /**
    * When this form closes, this will be the patNum of the last patient viewed.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    /**
    * This list of appointments displayed
    */
    private DataTable Table = new DataTable();
    private PrintDocument pd = new PrintDocument();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.FormPrintPreview printPreview;
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private ComboBox comboProv = new ComboBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butEmail;
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private ComboBox comboShowRecall = new ComboBox();
    private OpenDental.UI.Button butText;
    /**
    * Only used if PinClicked=true
    */
    public long AptSelected = new long();
    /**
    * 
    */
    public FormConfirmList() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormConfirmList.class);
        this.butClose = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.comboShowRecall = new System.Windows.Forms.ComboBox();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textDateTo = new OpenDental.ValidDate();
        this.textDateFrom = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.butReport = new OpenDental.UI.Button();
        this.butLabels = new OpenDental.UI.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.comboStatus = new System.Windows.Forms.ComboBox();
        this.butPostcards = new OpenDental.UI.Button();
        this.grid = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.butEmail = new OpenDental.UI.Button();
        this.butText = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
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
        this.butClose.Location = new System.Drawing.Point(895, 659);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(47, 12);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(84, 24);
        this.butRefresh.TabIndex = 2;
        this.butRefresh.Text = "&Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.comboShowRecall);
        this.groupBox1.Controls.Add(this.comboClinic);
        this.groupBox1.Controls.Add(this.labelClinic);
        this.groupBox1.Controls.Add(this.comboProv);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.textDateTo);
        this.groupBox1.Controls.Add(this.textDateFrom);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butRefresh);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(5, 4);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(632, 63);
        this.groupBox1.TabIndex = 1;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "View";
        //
        // comboShowRecall
        //
        this.comboShowRecall.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboShowRecall.FormattingEnabled = true;
        this.comboShowRecall.Items.AddRange(new Object[]{ "All", "Recall Only", "Exclude Recall" });
        this.comboShowRecall.Location = new System.Drawing.Point(29, 38);
        this.comboShowRecall.Name = "comboShowRecall";
        this.comboShowRecall.Size = new System.Drawing.Size(121, 21);
        this.comboShowRecall.TabIndex = 26;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(439, 36);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(181, 21);
        this.comboClinic.TabIndex = 25;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(346, 40);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(91, 14);
        this.labelClinic.TabIndex = 24;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(439, 12);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(181, 21);
        this.comboProv.TabIndex = 23;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(346, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(91, 14);
        this.label4.TabIndex = 22;
        this.label4.Text = "Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(232, 38);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(94, 20);
        this.textDateTo.TabIndex = 14;
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(232, 16);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(94, 20);
        this.textDateFrom.TabIndex = 13;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(161, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(68, 14);
        this.label2.TabIndex = 12;
        this.label2.Text = "To Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(152, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(77, 14);
        this.label1.TabIndex = 11;
        this.label1.Text = "From Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butReport
        //
        this.butReport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butReport.setAutosize(true);
        this.butReport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReport.setCornerRadius(4F);
        this.butReport.Location = new System.Drawing.Point(621, 659);
        this.butReport.Name = "butReport";
        this.butReport.Size = new System.Drawing.Size(87, 24);
        this.butReport.TabIndex = 13;
        this.butReport.Text = "R&un Report";
        this.butReport.Click += new System.EventHandler(this.butReport_Click);
        //
        // butLabels
        //
        this.butLabels.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabels.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabels.setAutosize(true);
        this.butLabels.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabels.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabels.setCornerRadius(4F);
        this.butLabels.Image = Resources.getbutLabel();
        this.butLabels.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabels.Location = new System.Drawing.Point(129, 659);
        this.butLabels.Name = "butLabels";
        this.butLabels.Size = new System.Drawing.Size(102, 24);
        this.butLabels.TabIndex = 14;
        this.butLabels.Text = "Label Preview";
        this.butLabels.Click += new System.EventHandler(this.butLabels_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.comboStatus);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(643, 4);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(143, 63);
        this.groupBox3.TabIndex = 15;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Set Status";
        //
        // comboStatus
        //
        this.comboStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatus.Location = new System.Drawing.Point(7, 22);
        this.comboStatus.MaxDropDownItems = 40;
        this.comboStatus.Name = "comboStatus";
        this.comboStatus.Size = new System.Drawing.Size(128, 21);
        this.comboStatus.TabIndex = 15;
        this.comboStatus.SelectedIndexChanged += new System.EventHandler(this.comboStatus_SelectedIndexChanged);
        this.comboStatus.SelectionChangeCommitted += new System.EventHandler(this.comboStatus_SelectionChangeCommitted);
        //
        // butPostcards
        //
        this.butPostcards.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPostcards.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butPostcards.setAutosize(true);
        this.butPostcards.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPostcards.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPostcards.setCornerRadius(4F);
        this.butPostcards.Image = Resources.getbutPrintSmall();
        this.butPostcards.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPostcards.Location = new System.Drawing.Point(4, 659);
        this.butPostcards.Name = "butPostcards";
        this.butPostcards.Size = new System.Drawing.Size(119, 24);
        this.butPostcards.TabIndex = 16;
        this.butPostcards.Text = "Postcard Preview";
        this.butPostcards.Click += new System.EventHandler(this.butPostcards_Click);
        //
        // grid
        //
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(4, 69);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.grid.Size = new System.Drawing.Size(963, 585);
        this.grid.TabIndex = 0;
        this.grid.setTitle("Confirmation List");
        this.grid.setTranslationName("TableConfirmList");
        this.grid.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.grid.CellClick = __MultiODGridClickEventHandler.combine(this.grid.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.grid.Click += new System.EventHandler(this.grid_Click);
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
        this.butPrint.Location = new System.Drawing.Point(714, 659);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 24);
        this.butPrint.TabIndex = 20;
        this.butPrint.Text = "Print List";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
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
        this.butEmail.Location = new System.Drawing.Point(237, 659);
        this.butEmail.Name = "butEmail";
        this.butEmail.Size = new System.Drawing.Size(91, 24);
        this.butEmail.TabIndex = 61;
        this.butEmail.Text = "E-Mail";
        this.butEmail.Click += new System.EventHandler(this.butEmail_Click);
        //
        // butText
        //
        this.butText.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butText.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butText.setAutosize(false);
        this.butText.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butText.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butText.setCornerRadius(4F);
        this.butText.Image = Resources.getText();
        this.butText.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butText.Location = new System.Drawing.Point(333, 659);
        this.butText.Name = "butText";
        this.butText.Size = new System.Drawing.Size(79, 24);
        this.butText.TabIndex = 61;
        this.butText.Text = "Text";
        this.butText.Click += new System.EventHandler(this.butText_Click);
        //
        // FormConfirmList
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(975, 688);
        this.Controls.Add(this.butText);
        this.Controls.Add(this.butEmail);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butPostcards);
        this.Controls.Add(this.grid);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.butLabels);
        this.Controls.Add(this.butReport);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormConfirmList";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Confirmation List";
        this.Load += new System.EventHandler(this.FormConfirmList_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formConfirmList_Load(Object sender, System.EventArgs e) throws Exception {
        comboShowRecall.SelectedIndex = 0;
        //Default to show all.
        textDateFrom.Text = AddWorkDays(1, DateTime.Today).ToShortDateString();
        textDateTo.Text = AddWorkDays(2, DateTime.Today).ToShortDateString();
        comboProv.Items.Add(Lan.g(this,"All"));
        comboProv.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        //textPostcardMessage.Text=PrefC.GetString(PrefName.ConfirmPostcardMessage");
        comboStatus.Items.Clear();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()].Length;i++)
        {
            comboStatus.Items.Add(DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].ItemName);
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
        if (!Programs.isEnabled(ProgramName.CallFire))
        {
            butText.Enabled = false;
        }
         
        fillMain();
        Plugins.hookAddCode(this,"FormConfirmList.Load_End",butText);
    }

    /**
    * Adds the specified number of work days, skipping saturday and sunday.
    */
    private DateTime addWorkDays(int days, DateTime date) throws Exception {
        DateTime retVal = date;
        for (int i = 0;i < days;i++)
        {
            retVal = retVal.AddDays(1);
            while (retVal.DayOfWeek == DayOfWeek.Saturday || retVal.DayOfWeek == DayOfWeek.Sunday)
            {
                //then, this part jumps to monday if on a sat or sun
                retVal = retVal.AddDays(1);
            }
        }
        return retVal;
    }

    private void fillMain() throws Exception {
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        long provNum = 0;
        if (comboProv.SelectedIndex != 0)
        {
            provNum = ProviderC.getListShort()[comboProv.SelectedIndex - 1].ProvNum;
        }
         
        long clinicNum = 0;
        if (comboClinic.SelectedIndex > 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        boolean showRecalls = false;
        boolean showNonRecalls = false;
        if (comboShowRecall.SelectedIndex == 0 || comboShowRecall.SelectedIndex == 1)
        {
            //All or Recall Only
            showRecalls = true;
        }
         
        if (comboShowRecall.SelectedIndex == 0 || comboShowRecall.SelectedIndex == 2)
        {
            //All or Exclude Recalls
            showNonRecalls = true;
        }
         
        Table = Appointments.getConfirmList(dateFrom,dateTo,provNum,clinicNum,showRecalls,showNonRecalls);
        int scrollVal = grid.getScrollValue();
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableConfirmList","Date Time"),70);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Patient"),80);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Age"),30);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Contact"),150);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Addr/Ph Note"),100);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Status"),80);
        //confirmed
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Procs"),110);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Medical"),80);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableConfirmList","Appt Note"),204);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //aptDateTime=PIn.PDateT(table.Rows[i][4].ToString());
            row.getCells().Add(Table.Rows[i]["aptDateTime"].ToString());
            //aptDateTime.ToShortDateString()+"\r\n"+aptDateTime.ToShortTimeString());
            row.getCells().Add(Table.Rows[i]["patientName"].ToString());
            row.getCells().Add(Table.Rows[i]["age"].ToString());
            row.getCells().Add(Table.Rows[i]["contactMethod"].ToString());
            row.getCells().Add(Table.Rows[i]["AddrNote"].ToString());
            row.getCells().Add(Table.Rows[i]["confirmed"].ToString());
            row.getCells().Add(Table.Rows[i]["ProcDescript"].ToString());
            cell = new OpenDental.UI.ODGridCell(Table.Rows[i]["medNotes"].ToString());
            cell.setColorText(Color.Red);
            row.getCells().Add(cell);
            row.getCells().Add(Table.Rows[i]["Note"].ToString());
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.setScrollValue(scrollVal);
    }

    private void grid_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //row selected before this event triggered
        SelectedPatNum = PIn.Long(Table.Rows[e.getRow()]["PatNum"].ToString());
        setFamilyColors();
        comboStatus.SelectedIndex = -1;
    }

    private void setFamilyColors() throws Exception {
        if (grid.getSelectedIndices().Length != 1)
        {
            for (int i = 0;i < grid.getRows().Count;i++)
            {
                grid.getRows().get___idx(i).setColorText(Color.Black);
            }
            grid.Invalidate();
            return ;
        }
         
        long guar = PIn.Long(Table.Rows[grid.getSelectedIndices()[0]]["Guarantor"].ToString());
        int famCount = 0;
        for (int i = 0;i < grid.getRows().Count;i++)
        {
            if (PIn.Long(Table.Rows[i]["Guarantor"].ToString()) == guar)
            {
                famCount++;
                grid.getRows().get___idx(i).setColorText(Color.Red);
            }
            else
            {
                grid.getRows().get___idx(i).setColorText(Color.Black);
            } 
        }
        if (famCount == 1)
        {
            //only the highlighted patient is red at this point
            grid.getRows()[grid.getSelectedIndices()[0]].ColorText = Color.Black;
        }
         
        grid.Invalidate();
    }

    private void grid_Click(Object sender, EventArgs e) throws Exception {
    }

    private void grid_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedPatNum = PIn.Long(Table.Rows[e.getRow()]["PatNum"].ToString());
        Cursor = Cursors.WaitCursor;
        long selectedApt = PIn.Long(Table.Rows[e.getRow()]["AptNum"].ToString());
        //Appointment apt=Appointments.GetOneApt(selectedApt);
        FormApptEdit FormA = new FormApptEdit(selectedApt);
        FormA.PinIsVisible = true;
        FormA.ShowDialog();
        if (FormA.PinClicked)
        {
            PinClicked = true;
            AptSelected = selectedApt;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            fillMain();
        } 
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            if (PIn.Long(Table.Rows[i]["AptNum"].ToString()) == selectedApt)
            {
                grid.setSelected(i,true);
            }
             
        }
        setFamilyColors();
        Cursor = Cursors.Default;
    }

    private void comboStatus_SelectionChangeCommitted(Object sender, System.EventArgs e) throws Exception {
        if (grid.getSelectedIndices().Length == 0)
        {
            return ;
        }
         
        //because user could never initiate this action.
        Appointment apt;
        Cursor = Cursors.WaitCursor;
        long[] selectedApts = new long[grid.getSelectedIndices().Length];
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            selectedApts[i] = PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString());
        }
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            apt = Appointments.GetOneApt(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString()));
            Appointment aptOld = apt.clone();
            int selectedI = comboStatus.SelectedIndex;
            apt.Confirmed = DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][selectedI].DefNum;
            try
            {
                Appointments.update(apt,aptOld);
            }
            catch (ApplicationException ex)
            {
                Cursor = Cursors.Default;
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
        fillMain();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            for (int j = 0;j < selectedApts.Length;j++)
            {
                //reselect all the apts
                if (PIn.Long(Table.Rows[i]["AptNum"].ToString()) == selectedApts[j])
                {
                    grid.setSelected(i,true);
                }
                 
            }
        }
        setFamilyColors();
        comboStatus.SelectedIndex = -1;
        Cursor = Cursors.Default;
    }

    private void comboStatus_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
    }

    //?
    private void butReport_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.UserQuery))
        {
            return ;
        }
         
        if (Table.Rows.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"There are no appointments in the list.  Must have at least one to run report."));
            return ;
        }
         
        long[] aptNums = new long[]();
        if (grid.getSelectedIndices().Length == 0)
        {
            aptNums = new long[Table.Rows.Count];
            for (int i = 0;i < aptNums.Length;i++)
            {
                aptNums[i] = PIn.Long(Table.Rows[i]["AptNum"].ToString());
            }
        }
        else
        {
            aptNums = new long[grid.getSelectedIndices().Length];
            for (int i = 0;i < aptNums.Length;i++)
            {
                aptNums[i] = PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString());
            }
        } 
        FormRpConfirm FormC = new FormRpConfirm(aptNums);
        FormC.ShowDialog();
    }

    private void butLabels_Click(Object sender, System.EventArgs e) throws Exception {
        if (Table.Rows.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"There are no appointments in the list.  Must have at least one to print."));
            return ;
        }
         
        if (grid.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < Table.Rows.Count;i++)
            {
                grid.setSelected(i,true);
            }
        }
         
        List<long> aptNums = new List<long>();
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            aptNums.Add(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString()));
        }
        AddrTable = Appointments.GetAddrTable(aptNums);
        pagesPrinted = 0;
        patientsPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdLabels_PrintPage);
        pd.OriginAtMargins = true;
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.LabelSheet, pd, (int)Math.Ceiling((double)AddrTable.Rows.Count / 30), 0, "Confirmation list labels printed");
        printPreview.ShowDialog();
    }

    /**
    * Changes made to printing confirmation postcards need to be made in FormRecallList.butPostcards_Click() as well.
    */
    private void butPostcards_Click(Object sender, System.EventArgs e) throws Exception {
        if (Table.Rows.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"There are no appointments in the list.  Must have at least one to print."));
            return ;
        }
         
        if (grid.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < Table.Rows.Count;i++)
            {
                cmeth = (ContactMethod)PIn.Long(Table.Rows[i]["PreferConfirmMethod"].ToString());
                if (cmeth != ContactMethod.Mail && cmeth != ContactMethod.None)
                {
                    continue;
                }
                 
                grid.setSelected(i,true);
            }
        }
         
        List<long> aptNums = new List<long>();
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            aptNums.Add(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString()));
        }
        AddrTable = Appointments.GetAddrTable(aptNums);
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
        printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.Postcard, pd, (int)Math.Ceiling((double)AddrTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet)), 0, "Confirmation list postcards printed");
        printPreview.ShowDialog();
    }

    /**
    * raised for each page to be printed.
    */
    private void pdLabels_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        int totalPages = (int)Math.Ceiling((double)AddrTable.Rows.Count / 30);
        Graphics g = ev.Graphics;
        float yPos = 75;
        float xPos = 50;
        String text = "";
        while (yPos < 1000 && patientsPrinted < AddrTable.Rows.Count)
        {
            text = AddrTable.Rows[patientsPrinted]["FName"].ToString() + " " + AddrTable.Rows[patientsPrinted]["MiddleI"].ToString() + " " + AddrTable.Rows[patientsPrinted]["LName"].ToString() + "\r\n" + AddrTable.Rows[patientsPrinted]["Address"].ToString() + "\r\n";
            if (!StringSupport.equals(AddrTable.Rows[patientsPrinted]["Address2"].ToString(), ""))
            {
                text += AddrTable.Rows[patientsPrinted]["Address2"].ToString() + "\r\n";
            }
             
            text += AddrTable.Rows[patientsPrinted]["City"].ToString() + ", " + AddrTable.Rows[patientsPrinted]["State"].ToString() + "   " + AddrTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
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
        if (pagesPrinted >= totalPages)
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
        int totalPages = (int)Math.Ceiling((double)AddrTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet));
        Graphics g = ev.Graphics;
        int yAdj = (int)(PrefC.getDouble(PrefName.RecallAdjustDown) * 100);
        int xAdj = (int)(PrefC.getDouble(PrefName.RecallAdjustRight) * 100);
        float yPos = 0 + yAdj;
        //these refer to the upper left origin of each postcard
        float xPos = 0 + xAdj;
        String str = new String();
        while (yPos < ev.PageBounds.Height - 100 && patientsPrinted < AddrTable.Rows.Count)
        {
            //Return Address--------------------------------------------------------------------------
            if (PrefC.getBool(PrefName.RecallCardsShowReturnAdd))
            {
                if (PrefC.getBool(PrefName.EasyNoClinics) || PIn.Long(AddrTable.Rows[patientsPrinted]["ClinicNum"].ToString()) == 0)
                {
                    //No clinics or no clinic selected for this appt
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
                else
                {
                    //Clinics enabled and clinic selected
                    Clinic clinic = Clinics.GetClinic(PIn.Long(AddrTable.Rows[patientsPrinted]["ClinicNum"].ToString()));
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
                g.DrawString(str, new Font(FontFamily.GenericSansSerif, 8), Brushes.Black, xPos + 45, yPos + 75);
            }
             
            //Body text-------------------------------------------------------------------------------
            str = PrefC.getString(PrefName.ConfirmPostcardMessage);
            //textPostcardMessage.Text;
            str = str.Replace("[date]", PIn.Date(AddrTable.Rows[patientsPrinted]["AptDateTime"].ToString()).ToShortDateString());
            str = str.Replace("[time]", PIn.Date(AddrTable.Rows[patientsPrinted]["AptDateTime"].ToString()).ToShortTimeString());
            g.DrawString(str, new Font(FontFamily.GenericSansSerif, 10), Brushes.Black, new RectangleF(xPos + 45, yPos + 180, 250, 190));
            //Patient's Address-----------------------------------------------------------------------
            str = AddrTable.Rows[patientsPrinted]["FName"].ToString() + " " + AddrTable.Rows[patientsPrinted]["MiddleI"].ToString() + " " + AddrTable.Rows[patientsPrinted]["LName"].ToString() + "\r\n" + AddrTable.Rows[patientsPrinted]["Address"].ToString() + "\r\n";
            if (!StringSupport.equals(AddrTable.Rows[patientsPrinted]["Address2"].ToString(), ""))
            {
                str += AddrTable.Rows[patientsPrinted]["Address2"].ToString() + "\r\n";
            }
             
            str += AddrTable.Rows[patientsPrinted]["City"].ToString() + ", " + AddrTable.Rows[patientsPrinted]["State"].ToString() + "   " + AddrTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
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
        fillMain();
    }

    private void butSetStatus_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /*if(comboStatus.SelectedIndex==-1){
    				return;
    			}
    			int[] originalRecalls=new int[tbMain.SelectedIndices.Length];
    			for(int i=0;i<tbMain.SelectedIndices.Length;i++){
    				originalRecalls[i]=((RecallItem)MainAL[tbMain.SelectedIndices[i]]).RecallNum;
    				Recalls.UpdateStatus(
    					((RecallItem)MainAL[tbMain.SelectedIndices[i]]).RecallNum,
    					DefC.Short[(int)DefCat.RecallUnschedStatus][comboStatus.SelectedIndex].DefNum);
    				//((RecallItem)MainAL[tbMain.SelectedIndices[i]]).up
    			}
    			FillMain();
    			for(int i=0;i<tbMain.MaxRows;i++){
    				for(int j=0;j<originalRecalls.Length;j++){
    					if(originalRecalls[j]==((RecallItem)MainAL[i]).RecallNum){
    						tbMain.SetSelected(i,true);
    					}
    				}
    			}*/
    private void butEmail_Click(Object sender, EventArgs e) throws Exception {
        if (grid.getRows().Count == 0)
        {
            MsgBox.show(this,"There are no Patients in the table.  Must have at least one.");
            return ;
        }
         
        if (!EmailAddresses.existsValidEmail())
        {
            MsgBox.show(this,"You need to enter an SMTP server name in e-mail setup before you can send e-mail.");
            return ;
        }
         
        if (PrefC.getLong(PrefName.ConfirmStatusEmailed) == 0)
        {
            MsgBox.show(this,"You need to set a status first for confirmation e-mails in the Recall Setup window.");
            return ;
        }
         
        if (grid.getSelectedIndices().Length == 0)
        {
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < Table.Rows.Count;i++)
            {
                cmeth = (ContactMethod)PIn.Int(Table.Rows[i]["PreferConfirmMethod"].ToString());
                if (cmeth != ContactMethod.Email)
                {
                    continue;
                }
                 
                if (StringSupport.equals(Table.Rows[i]["confirmed"].ToString(), DefC.getName(DefCat.ApptConfirmed,PrefC.getLong(PrefName.ConfirmStatusEmailed))))
                {
                    continue;
                }
                 
                //Already confirmed by email
                if (StringSupport.equals(Table.Rows[i]["email"].ToString(), ""))
                {
                    continue;
                }
                 
                grid.setSelected(i,true);
            }
            if (grid.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"Confirmations have been sent to all patients of email type who also have an email address entered.");
                return ;
            }
             
        }
        else
        {
            //deselect the ones that do not have email addresses specified
            int skipped = 0;
            for (int i = grid.getSelectedIndices().Length - 1;i >= 0;i--)
            {
                if (StringSupport.equals(Table.Rows[grid.getSelectedIndices()[i]]["email"].ToString(), ""))
                {
                    skipped++;
                    grid.SetSelected(grid.getSelectedIndices()[i], false);
                }
                 
            }
            if (grid.getSelectedIndices().Length == 0)
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
        EmailMessage message;
        String str = "";
        List<long> patNumsSelected = new List<long>();
        List<long> patNumsFailed = new List<long>();
        EmailAddress emailAddress;
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            message = new EmailMessage();
            message.PatNum = PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["PatNum"].ToString());
            message.ToAddress = Table.Rows[grid.getSelectedIndices()[i]]["email"].ToString();
            //Could be guarantor email.
            emailAddress = EmailAddresses.GetByClinic(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["ClinicNum"].ToString()));
            message.FromAddress = emailAddress.SenderAddress;
            message.Subject = PrefC.getString(PrefName.ConfirmEmailSubject);
            patNumsSelected.Add(message.PatNum);
            str = PrefC.getString(PrefName.ConfirmEmailMessage);
            str = str.Replace("[NameF]", Table.Rows[grid.getSelectedIndices()[i]]["nameF"].ToString());
            str = str.Replace("[NameFL]", Table.Rows[grid.getSelectedIndices()[i]]["nameFL"].ToString());
            str = str.Replace("[date]", ((DateTime)Table.Rows[grid.getSelectedIndices()[i]]["AptDateTime"]).ToShortDateString());
            str = str.Replace("[time]", ((DateTime)Table.Rows[grid.getSelectedIndices()[i]]["AptDateTime"]).ToShortTimeString());
            message.BodyText = str;
            try
            {
                EmailMessages.sendEmailUnsecure(message,emailAddress);
            }
            catch (Exception __dummyCatchVar0)
            {
                patNumsFailed.Add(message.PatNum);
                continue;
            }

            message.MsgDateTime = DateTime.Now;
            message.SentOrReceived = EmailSentOrReceived.Sent;
            EmailMessages.insert(message);
            Appointments.SetConfirmed(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString()), PrefC.getLong(PrefName.ConfirmStatusEmailed));
        }
        Cursor = Cursors.Default;
        if (patNumsFailed.Count == grid.getSelectedIndices().Length)
        {
            //all failed
            //no need to refresh
            MsgBox.show(this,"All emails failed. Possibly due to invalid email addresses, a loss of connectivity, or a firewall blocking communication.");
            return ;
        }
        else //msg: all failed
        if (patNumsFailed.Count > 0)
        {
            //if some failed
            fillMain();
            for (int i = 0;i < Table.Rows.Count;i++)
            {
                //reselect only the failed ones
                //table.Rows.Count=grid.Rows.Count
                long patNum = PIn.Long(Table.Rows[i]["PatNum"].ToString());
                if (patNumsFailed.Contains(patNum))
                {
                    grid.setSelected(i,true);
                }
                 
            }
            MsgBox.show(this,"Some emails failed to send.");
            return ;
        }
          
        //none failed
        fillMain();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            //reselect the original list
            long patNum = PIn.Long(Table.Rows[i]["PatNum"].ToString());
            if (patNumsSelected.Contains(patNum))
            {
                grid.setSelected(i,true);
            }
             
        }
    }

    private void butText_Click(Object sender, EventArgs e) throws Exception {
        long patNum = new long();
        String wirelessPhone = new String();
        YN txtMsgOk = YN.Unknown;
        if (grid.getRows().Count == 0)
        {
            MsgBox.show(this,"There are no Patients in the table.  Must have at least one.");
            return ;
        }
         
        if (PrefC.getLong(PrefName.ConfirmStatusTextMessaged) == 0)
        {
            MsgBox.show(this,"You need to set a status first for confirmation text messages in the Recall Setup window.");
            return ;
        }
         
        if (grid.getSelectedIndices().Length == 0)
        {
            //None selected. Select all of type text that are not yet confirmed by text message.
            ContactMethod cmeth = ContactMethod.None;
            for (int i = 0;i < Table.Rows.Count;i++)
            {
                cmeth = (ContactMethod)PIn.Int(Table.Rows[i]["PreferConfirmMethod"].ToString());
                if (cmeth != ContactMethod.TextMessage)
                {
                    continue;
                }
                 
                if (StringSupport.equals(Table.Rows[i]["confirmed"].ToString(), DefC.getName(DefCat.ApptConfirmed,PrefC.getLong(PrefName.ConfirmStatusTextMessaged))))
                {
                    continue;
                }
                 
                //Already confirmed by text
                if (!Table.Rows[i]["contactMethod"].ToString().StartsWith("Text:"))
                {
                    continue;
                }
                 
                //Check contact method
                grid.setSelected(i,true);
            }
            if (grid.getSelectedIndices().Length == 0)
            {
                MsgBox.show(this,"All patients of text message type have been sent confirmations.");
                return ;
            }
             
        }
         
        //deselect the ones that do not have text messages specified or are not OK to send texts to or have already been texted
        int skipped = 0;
        for (int i = grid.getSelectedIndices().Length - 1;i >= 0;i--)
        {
            wirelessPhone = Table.Rows[grid.getSelectedIndices()[i]]["WirelessPhone"].ToString();
            if (StringSupport.equals(wirelessPhone, ""))
            {
                //Check for wireless number
                skipped++;
                grid.SetSelected(grid.getSelectedIndices()[i], false);
                continue;
            }
             
            txtMsgOk = (YN)PIn.Int(Table.Rows[grid.getSelectedIndices()[i]]["TxtMsgOk"].ToString());
            if (txtMsgOk == YN.Unknown && PrefC.getBool(PrefName.TextMsgOkStatusTreatAsNo))
            {
                //Check if OK to text
                skipped++;
                grid.SetSelected(grid.getSelectedIndices()[i], false);
                continue;
            }
             
            if (txtMsgOk == YN.No)
            {
                //Check if OK to text
                skipped++;
                grid.SetSelected(grid.getSelectedIndices()[i], false);
                continue;
            }
             
        }
        if (grid.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"None of the selected patients have wireless phone numbers and are OK to text.");
            return ;
        }
         
        if (skipped > 0)
        {
            MessageBox.Show(Lan.g(this,"Selected patients skipped: ") + skipped.ToString());
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Send text message to all of the selected patients?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        FormTxtMsgEdit FormTME = new FormTxtMsgEdit();
        String message = "";
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            //Appointment apt;
            patNum = PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["PatNum"].ToString());
            wirelessPhone = PIn.String(Table.Rows[grid.getSelectedIndices()[i]]["WirelessPhone"].ToString());
            txtMsgOk = ((YN)PIn.Int(Table.Rows[grid.getSelectedIndices()[i]]["TxtMsgOk"].ToString()));
            message = PrefC.getString(PrefName.ConfirmTextMessage);
            message = message.Replace("[NameF]", Table.Rows[grid.getSelectedIndices()[i]]["nameF"].ToString());
            message = message.Replace("[NameFL]", Table.Rows[grid.getSelectedIndices()[i]]["nameFL"].ToString());
            message = message.Replace("[date]", ((DateTime)Table.Rows[grid.getSelectedIndices()[i]]["AptDateTime"]).ToShortDateString());
            message = message.Replace("[time]", ((DateTime)Table.Rows[grid.getSelectedIndices()[i]]["AptDateTime"]).ToShortTimeString());
            FormTME.sendText(patNum,wirelessPhone,message,txtMsgOk);
            Appointments.SetConfirmed(PIn.Long(Table.Rows[grid.getSelectedIndices()[i]]["AptNum"].ToString()), PrefC.getLong(PrefName.ConfirmStatusTextMessaged));
        }
        fillMain();
        Cursor = Cursors.Default;
    }

    private void butSave_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /*if(  textDaysPast.errorProvider1.GetError(textDaysPast)!=""
    				|| textDaysFuture.errorProvider1.GetError(textDaysFuture)!="")
    			{
    				MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
    				return;
    			}
    			Prefs.Cur.PrefName="RecallDaysPast";
    			Prefs.Cur.ValueString=textDaysPast.Text;
    			Prefs.UpdateCur();
    			Prefs.Cur.PrefName="RecallDaysFuture";
    			Prefs.Cur.ValueString=textDaysFuture.Text;
    			Prefs.UpdateCur();
    			DataValid.SetInvalid(InvalidTypes.Prefs);*/
    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        pd.DefaultPageSettings.Landscape = true;
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Confirmation list printed"))
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
            text = Lan.g(this,"Confirmation List");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = textDateFrom.Text + " " + Lan.g(this,"to") + " " + textDateTo.Text;
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = grid.printPage(g,pagesPrinted,bounds,headingPrintH);
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

}


