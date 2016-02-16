//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:49 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormClaimEdit;
import OpenDental.FormClaimPayBatch;
import OpenDental.FormClaimPayEdit;
import OpenDental.FormImages;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.ClaimPaySplit;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EobAttaches;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormClaimPayBatch  extends System.Windows.Forms.Form 
{
    private OpenDental.ValidDouble textAmount;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.TextBox textBankBranch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCheckNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butClose;
    private IContainer components = new IContainer();
    //private bool ControlDown;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    /**
    * The list of splits to display in the grid.
    */
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCarrierName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private ClaimPayment ClaimPaymentCur;
    private OpenDental.UI.ODGrid gridAttached;
    private OpenDental.ValidDate textDateIssued;
    private Label labelDateIssued = new Label();
    private TextBox textClinic = new TextBox();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butClaimPayEdit;
    private OpenDental.UI.ODGrid gridOut;
    List<ClaimPaySplit> ClaimsAttached = new List<ClaimPaySplit>();
    List<ClaimPaySplit> ClaimsOutstanding = new List<ClaimPaySplit>();
    private OpenDental.UI.Button butDetach;
    private OpenDental.ValidDouble textTotal;
    private Label label8 = new Label();
    private Label labelInstruct1 = new Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private Label labelInstruct2 = new Label();
    private ContextMenu menuRightAttached = new ContextMenu();
    private MenuItem menuItemGotoAccount = new MenuItem();
    private ContextMenu menuRightOut = new ContextMenu();
    private MenuItem menuItemGotoOut = new MenuItem();
    private boolean IsDeleting = new boolean();
    /**
    * If this is not zero upon closing, then we will jump to the account module of that patient and highlight the claim.
    */
    public long GotoClaimNum = new long();
    /**
    * If this is not zero upon closing, then we will jump to the account module of that patient and highlight the claim.
    */
    public long GotoPatNum = new long();
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private OpenDental.UI.Button butView;
    private TextBox textEobIsScanned = new TextBox();
    private OpenDental.UI.Button butAttach;
    private TextBox textShow = new TextBox();
    private Label label9 = new Label();
    private OpenDental.UI.Button butRefresh;
    /**
    * Set to true if the batch list was accessed originally by going through a claim.  This disables the GotoAccount feature.  It also causes OK/Cancel buttons to show so that user can cancel out of a brand new check creation.
    */
    public boolean IsFromClaim = new boolean();
    /**
    * 
    */
    public FormClaimPayBatch(ClaimPayment claimPaymentCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        ClaimPaymentCur = claimPaymentCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimPayBatch.class);
        this.textBankBranch = new System.Windows.Forms.TextBox();
        this.textCheckNum = new System.Windows.Forms.TextBox();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.labelClinic = new System.Windows.Forms.Label();
        this.textCarrierName = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.gridAttached = new OpenDental.UI.ODGrid();
        this.labelDateIssued = new System.Windows.Forms.Label();
        this.textClinic = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.gridOut = new OpenDental.UI.ODGrid();
        this.labelInstruct1 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.labelInstruct2 = new System.Windows.Forms.Label();
        this.menuRightAttached = new System.Windows.Forms.ContextMenu();
        this.menuItemGotoAccount = new System.Windows.Forms.MenuItem();
        this.menuRightOut = new System.Windows.Forms.ContextMenu();
        this.menuItemGotoOut = new System.Windows.Forms.MenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.textEobIsScanned = new System.Windows.Forms.TextBox();
        this.textShow = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.textTotal = new OpenDental.ValidDouble();
        this.butAttach = new OpenDental.UI.Button();
        this.butView = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butDetach = new OpenDental.UI.Button();
        this.textDateIssued = new OpenDental.ValidDate();
        this.butClaimPayEdit = new OpenDental.UI.Button();
        this.textAmount = new OpenDental.ValidDouble();
        this.textDate = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // textBankBranch
        //
        this.textBankBranch.Location = new System.Drawing.Point(362, 82);
        this.textBankBranch.MaxLength = 25;
        this.textBankBranch.Name = "textBankBranch";
        this.textBankBranch.ReadOnly = true;
        this.textBankBranch.Size = new System.Drawing.Size(100, 20);
        this.textBankBranch.TabIndex = 2;
        //
        // textCheckNum
        //
        this.textCheckNum.Location = new System.Drawing.Point(362, 61);
        this.textCheckNum.MaxLength = 25;
        this.textCheckNum.Name = "textCheckNum";
        this.textCheckNum.ReadOnly = true;
        this.textCheckNum.Size = new System.Drawing.Size(100, 20);
        this.textCheckNum.TabIndex = 1;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(362, 40);
        this.textNote.MaxLength = 255;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ReadOnly = true;
        this.textNote.Size = new System.Drawing.Size(288, 20);
        this.textNote.TabIndex = 3;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(23, 44);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(96, 16);
        this.label6.TabIndex = 37;
        this.label6.Text = "Payment Date";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(24, 86);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(95, 16);
        this.label5.TabIndex = 36;
        this.label5.Text = "Amount";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(269, 63);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(90, 16);
        this.label4.TabIndex = 35;
        this.label4.Text = "Check #";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(270, 85);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(91, 16);
        this.label3.TabIndex = 34;
        this.label3.Text = "Bank-Branch";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(254, 41);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(104, 16);
        this.label2.TabIndex = 33;
        this.label2.Text = "Note";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(32, 22);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(86, 14);
        this.labelClinic.TabIndex = 91;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCarrierName
        //
        this.textCarrierName.Location = new System.Drawing.Point(362, 19);
        this.textCarrierName.MaxLength = 25;
        this.textCarrierName.Name = "textCarrierName";
        this.textCarrierName.ReadOnly = true;
        this.textCarrierName.Size = new System.Drawing.Size(288, 20);
        this.textCarrierName.TabIndex = 93;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(252, 21);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(109, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Carrier Name";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridAttached
        //
        this.gridAttached.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridAttached.setHScrollVisible(false);
        this.gridAttached.Location = new System.Drawing.Point(230, 125);
        this.gridAttached.Name = "gridAttached";
        this.gridAttached.setScrollValue(0);
        this.gridAttached.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAttached.Size = new System.Drawing.Size(660, 226);
        this.gridAttached.TabIndex = 95;
        this.gridAttached.setTitle("Attached to this Payment");
        this.gridAttached.setTranslationName("TableClaimPaySplits");
        this.gridAttached.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridAttached.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAttached_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridAttached.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridAttached_MouseUp);
        //
        // labelDateIssued
        //
        this.labelDateIssued.Location = new System.Drawing.Point(23, 65);
        this.labelDateIssued.Name = "labelDateIssued";
        this.labelDateIssued.Size = new System.Drawing.Size(96, 16);
        this.labelDateIssued.TabIndex = 97;
        this.labelDateIssued.Text = "Date Issued";
        this.labelDateIssued.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textClinic
        //
        this.textClinic.Location = new System.Drawing.Point(121, 19);
        this.textClinic.MaxLength = 25;
        this.textClinic.Name = "textClinic";
        this.textClinic.ReadOnly = true;
        this.textClinic.Size = new System.Drawing.Size(123, 20);
        this.textClinic.TabIndex = 93;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelClinic);
        this.groupBox1.Controls.Add(this.textDateIssued);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.labelDateIssued);
        this.groupBox1.Controls.Add(this.butClaimPayEdit);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.textClinic);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textCarrierName);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.textNote);
        this.groupBox1.Controls.Add(this.textCheckNum);
        this.groupBox1.Controls.Add(this.textBankBranch);
        this.groupBox1.Controls.Add(this.textAmount);
        this.groupBox1.Controls.Add(this.textDate);
        this.groupBox1.Location = new System.Drawing.Point(230, 6);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(660, 110);
        this.groupBox1.TabIndex = 98;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Payment Details";
        //
        // gridOut
        //
        this.gridOut.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridOut.setHScrollVisible(false);
        this.gridOut.Location = new System.Drawing.Point(230, 387);
        this.gridOut.Name = "gridOut";
        this.gridOut.setScrollValue(0);
        this.gridOut.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridOut.Size = new System.Drawing.Size(660, 243);
        this.gridOut.TabIndex = 99;
        this.gridOut.setTitle("All Outstanding Claims");
        this.gridOut.setTranslationName("TableClaimPaySplits");
        this.gridOut.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridOut.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridOut_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridOut.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridOut_MouseUp);
        //
        // labelInstruct1
        //
        this.labelInstruct1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInstruct1.Location = new System.Drawing.Point(9, 1);
        this.labelInstruct1.Name = "labelInstruct1";
        this.labelInstruct1.Size = new System.Drawing.Size(177, 20);
        this.labelInstruct1.TabIndex = 102;
        this.labelInstruct1.Text = "Instructions";
        this.labelInstruct1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(701, 362);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(92, 16);
        this.label8.TabIndex = 36;
        this.label8.Text = "Total Payments";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelInstruct2
        //
        this.labelInstruct2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInstruct2.Location = new System.Drawing.Point(10, 27);
        this.labelInstruct2.Name = "labelInstruct2";
        this.labelInstruct2.Size = new System.Drawing.Size(207, 527);
        this.labelInstruct2.TabIndex = 105;
        this.labelInstruct2.Text = resources.GetString("labelInstruct2.Text");
        //
        // menuRightAttached
        //
        this.menuRightAttached.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemGotoAccount });
        //
        // menuItemGotoAccount
        //
        this.menuItemGotoAccount.Index = 0;
        this.menuItemGotoAccount.Text = "Go to Account";
        this.menuItemGotoAccount.Click += new System.EventHandler(this.menuItemGotoAccount_Click);
        //
        // menuRightOut
        //
        this.menuRightOut.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemGotoOut });
        //
        // menuItemGotoOut
        //
        this.menuItemGotoOut.Index = 0;
        this.menuItemGotoOut.Text = "Go to Account";
        this.menuItemGotoOut.Click += new System.EventHandler(this.menuItemGotoOut_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 583);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(123, 16);
        this.label1.TabIndex = 108;
        this.label1.Text = "EOB is Scanned";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textEobIsScanned
        //
        this.textEobIsScanned.Location = new System.Drawing.Point(145, 580);
        this.textEobIsScanned.MaxLength = 25;
        this.textEobIsScanned.Name = "textEobIsScanned";
        this.textEobIsScanned.ReadOnly = true;
        this.textEobIsScanned.Size = new System.Drawing.Size(72, 20);
        this.textEobIsScanned.TabIndex = 110;
        //
        // textShow
        //
        this.textShow.Location = new System.Drawing.Point(359, 359);
        this.textShow.Name = "textShow";
        this.textShow.Size = new System.Drawing.Size(132, 20);
        this.textShow.TabIndex = 112;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(313, 361);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(46, 16);
        this.label9.TabIndex = 113;
        this.label9.Text = "Show";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRefresh.Location = new System.Drawing.Point(494, 357);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(54, 24);
        this.butRefresh.TabIndex = 114;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(271, 357);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(39, 24);
        this.butDown.TabIndex = 104;
        this.butDown.Text = "#";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // textTotal
        //
        this.textTotal.Location = new System.Drawing.Point(792, 359);
        this.textTotal.Name = "textTotal";
        this.textTotal.ReadOnly = true;
        this.textTotal.Size = new System.Drawing.Size(81, 20);
        this.textTotal.TabIndex = 0;
        this.textTotal.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butAttach
        //
        this.butAttach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAttach.setAutosize(true);
        this.butAttach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAttach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAttach.setCornerRadius(4F);
        this.butAttach.Image = Resources.getup();
        this.butAttach.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAttach.Location = new System.Drawing.Point(554, 357);
        this.butAttach.Name = "butAttach";
        this.butAttach.Size = new System.Drawing.Size(71, 24);
        this.butAttach.TabIndex = 111;
        this.butAttach.Text = "Attach";
        this.butAttach.Click += new System.EventHandler(this.butAttach_Click);
        //
        // butView
        //
        this.butView.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butView.setAutosize(true);
        this.butView.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butView.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butView.setCornerRadius(4F);
        this.butView.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butView.Location = new System.Drawing.Point(145, 606);
        this.butView.Name = "butView";
        this.butView.Size = new System.Drawing.Size(72, 24);
        this.butView.TabIndex = 109;
        this.butView.Text = "View";
        this.butView.Click += new System.EventHandler(this.butView_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(734, 646);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 107;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(230, 357);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(39, 24);
        this.butUp.TabIndex = 103;
        this.butUp.Text = "#";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDetach
        //
        this.butDetach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetach.setAutosize(true);
        this.butDetach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetach.setCornerRadius(4F);
        this.butDetach.Image = Resources.getdown();
        this.butDetach.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDetach.Location = new System.Drawing.Point(627, 357);
        this.butDetach.Name = "butDetach";
        this.butDetach.Size = new System.Drawing.Size(71, 24);
        this.butDetach.TabIndex = 101;
        this.butDetach.Text = "Detach";
        this.butDetach.Click += new System.EventHandler(this.butDetach_Click);
        //
        // textDateIssued
        //
        this.textDateIssued.Location = new System.Drawing.Point(121, 61);
        this.textDateIssued.Name = "textDateIssued";
        this.textDateIssued.ReadOnly = true;
        this.textDateIssued.Size = new System.Drawing.Size(68, 20);
        this.textDateIssued.TabIndex = 96;
        this.textDateIssued.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butClaimPayEdit
        //
        this.butClaimPayEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClaimPayEdit.setAutosize(true);
        this.butClaimPayEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClaimPayEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClaimPayEdit.setCornerRadius(4F);
        this.butClaimPayEdit.Location = new System.Drawing.Point(575, 78);
        this.butClaimPayEdit.Name = "butClaimPayEdit";
        this.butClaimPayEdit.Size = new System.Drawing.Size(75, 24);
        this.butClaimPayEdit.TabIndex = 6;
        this.butClaimPayEdit.Text = "Edit";
        this.butClaimPayEdit.Click += new System.EventHandler(this.butClaimPayEdit_Click);
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(121, 82);
        this.textAmount.Name = "textAmount";
        this.textAmount.ReadOnly = true;
        this.textAmount.Size = new System.Drawing.Size(68, 20);
        this.textAmount.TabIndex = 0;
        this.textAmount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(121, 40);
        this.textDate.Name = "textDate";
        this.textDate.ReadOnly = true;
        this.textDate.Size = new System.Drawing.Size(68, 20);
        this.textDate.TabIndex = 3;
        this.textDate.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(13, 646);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 52;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(815, 646);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Cancel";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormClaimPayBatch
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(902, 676);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textShow);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textTotal);
        this.Controls.Add(this.butAttach);
        this.Controls.Add(this.textEobIsScanned);
        this.Controls.Add(this.butView);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelInstruct2);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.labelInstruct1);
        this.Controls.Add(this.butDetach);
        this.Controls.Add(this.gridOut);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridAttached);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.label8);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimPayBatch";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Payment (EOB)";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormClaimPayBatch_FormClosing);
        this.Load += new System.EventHandler(this.FormClaimPayEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimPayEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsFromClaim && IsNew)
        {
            //ok and cancel
            labelInstruct1.Visible = false;
            labelInstruct2.Visible = false;
            gridOut.Visible = false;
            butAttach.Visible = false;
        }
        else
        {
            butOK.Visible = false;
            butClose.Text = Lan.g(this,"Close");
        } 
        fillClaimPayment();
        textShow.Text = textCarrierName.Text;
        fillGrids();
        if (ClaimPaymentCur.IsPartial)
        {
        }
        else
        {
            //an incomplete payment that's not yet locked
            //locked
            if (!Security.isAuthorized(Permissions.InsPayEdit,ClaimPaymentCur.CheckDate))
            {
                butDelete.Enabled = false;
                gridAttached.Enabled = false;
                butClaimPayEdit.Enabled = false;
                butUp.Visible = false;
                butDown.Visible = false;
            }
             
            //someone with permission can double click on the top grid to edit amounts and can edit the object fields as well.
            butDetach.Visible = false;
            gridOut.Visible = false;
            labelInstruct1.Visible = false;
            labelInstruct2.Visible = false;
            butAttach.Visible = false;
        } 
        if (EobAttaches.exists(ClaimPaymentCur.ClaimPaymentNum))
        {
            textEobIsScanned.Text = Lan.g(this,"Yes");
        }
        else
        {
            textEobIsScanned.Text = Lan.g(this,"No");
        } 
    }

    private void fillClaimPayment() throws Exception {
        textClinic.Text = Clinics.getDesc(ClaimPaymentCur.ClinicNum);
        if (ClaimPaymentCur.CheckDate.Year > 1800)
        {
            textDate.Text = ClaimPaymentCur.CheckDate.ToShortDateString();
        }
         
        if (ClaimPaymentCur.DateIssued.Year > 1800)
        {
            textDateIssued.Text = ClaimPaymentCur.DateIssued.ToShortDateString();
        }
         
        textAmount.Text = ClaimPaymentCur.CheckAmt.ToString("F");
        textCheckNum.Text = ClaimPaymentCur.CheckNum;
        textBankBranch.Text = ClaimPaymentCur.BankBranch;
        textCarrierName.Text = ClaimPaymentCur.CarrierName;
        textNote.Text = ClaimPaymentCur.Note;
    }

    private void fillGrids() throws Exception {
        Cursor.Current = Cursors.WaitCursor;
        //gridAttached-----------------------------------------------------------------------------------------------
        ClaimsAttached = Claims.getAttachedToPayment(ClaimPaymentCur.ClaimPaymentNum);
        boolean didReorder = false;
        for (int i = 0;i < ClaimsAttached.Count;i++)
        {
            if (ClaimsAttached[i].PaymentRow != i + 1)
            {
                ClaimProcs.SetPaymentRow(ClaimsAttached[i].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, i + 1);
                didReorder = true;
            }
             
        }
        if (didReorder)
        {
            ClaimsAttached = Claims.getAttachedToPayment(ClaimPaymentCur.ClaimPaymentNum);
        }
         
        gridAttached.beginUpdate();
        gridAttached.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"#"),25);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Service Date"),80);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Clinic"),70);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Carrier"),186);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient"),130);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Fee"), 70, HorizontalAlignment.Right);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Payment"), 70, HorizontalAlignment.Right);
        gridAttached.getColumns().add(col);
        gridAttached.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ClaimsAttached.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ClaimsAttached[i].PaymentRow.ToString());
            row.getCells().Add(ClaimsAttached[i].DateClaim.ToShortDateString());
            row.getCells().Add(ClaimsAttached[i].ClinicDesc);
            row.getCells().Add(ClaimsAttached[i].Carrier);
            row.getCells().Add(ClaimsAttached[i].PatName);
            row.getCells().Add(ClaimsAttached[i].FeeBilled.ToString("F"));
            row.getCells().Add(ClaimsAttached[i].InsPayAmt.ToString("F"));
            gridAttached.getRows().add(row);
        }
        gridAttached.endUpdate();
        double total = 0;
        for (int i = 0;i < ClaimsAttached.Count;i++)
        {
            total += (double)ClaimsAttached[i].InsPayAmt;
        }
        textTotal.Text = total.ToString("F");
        //gridOutstanding-------------------------------------------------------------------------------------------------
        int scrollValue = gridOut.getScrollValue();
        int selectedIdx = gridOut.getSelectedIndex();
        ClaimsOutstanding = Claims.GetOutstandingClaims(textShow.Text);
        gridOut.beginUpdate();
        gridOut.getColumns().Clear();
        col = new ODGridColumn("",25);
        //so that it lines up with the grid above
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Service Date"),80);
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Clinic"),70);
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Carrier"),186);
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient"),130);
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Fee"), 70, HorizontalAlignment.Right);
        gridOut.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Payment"), 70, HorizontalAlignment.Right);
        gridOut.getColumns().add(col);
        gridOut.getRows().Clear();
        for (int i = 0;i < ClaimsOutstanding.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("");
            row.getCells().Add(ClaimsOutstanding[i].DateClaim.ToShortDateString());
            row.getCells().Add(ClaimsOutstanding[i].ClinicDesc);
            row.getCells().Add(ClaimsOutstanding[i].Carrier);
            row.getCells().Add(ClaimsOutstanding[i].PatName);
            row.getCells().Add(ClaimsOutstanding[i].FeeBilled.ToString("F"));
            row.getCells().Add(ClaimsOutstanding[i].InsPayAmt.ToString("F"));
            gridOut.getRows().add(row);
        }
        gridOut.endUpdate();
        gridOut.setScrollValue(scrollValue);
        gridOut.setSelected(selectedIdx,true);
        Cursor.Current = Cursors.Default;
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrids();
    }

    private void butClaimPayEdit_Click(Object sender, EventArgs e) throws Exception {
        FormClaimPayEdit FormCPE = new FormClaimPayEdit(ClaimPaymentCur);
        FormCPE.ShowDialog();
        fillClaimPayment();
        fillGrids();
    }

    //For customer 5769, who was getting ocassional Chinese chars in the Amount boxes.
    private void butAttach_Click(Object sender, EventArgs e) throws Exception {
        if (gridOut.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one paid claim from the Outstanding Claims grid below.");
            return ;
        }
         
        for (int i = 0;i < gridOut.getSelectedIndices().Length;i++)
        {
            if (ClaimsOutstanding[gridOut.getSelectedIndices()[i]].InsPayAmt == 0)
            {
                MsgBox.show(this,"All selected claims must have payments entered.  Unpaid claims cannot be attached.");
                return ;
            }
             
        }
        int paymentRow = ClaimsAttached.Count;
        for (int i = 0;i < gridOut.getSelectedIndices().Length;i++)
        {
            //1-indexed
            paymentRow++;
            ClaimProcs.AttachToPayment(ClaimsOutstanding[gridOut.getSelectedIndices()[i]].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, ClaimPaymentCur.CheckDate, paymentRow);
        }
        fillGrids();
    }

    private void butDetach_Click(Object sender, EventArgs e) throws Exception {
        if (gridAttached.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a claim from the attached claims grid above.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Remove selected claims from this check?"))
        {
            return ;
        }
         
        for (int i = 0;i < gridAttached.getSelectedIndices().Length;i++)
        {
            ClaimProcs.DetachFromPayment(ClaimsAttached[gridAttached.getSelectedIndices()[i]].ClaimNum, ClaimPaymentCur.ClaimPaymentNum);
        }
        fillGrids();
        boolean didReorder = false;
        for (int i = 0;i < ClaimsAttached.Count;i++)
        {
            if (ClaimsAttached[i].PaymentRow != i + 1)
            {
                ClaimProcs.SetPaymentRow(ClaimsAttached[i].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, i + 1);
                didReorder = true;
            }
             
        }
        if (didReorder)
        {
            fillGrids();
        }
         
    }

    private void gridAttached_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //top grid
        //bring up claimedit window.  User should be able to edit if not locked.
        Claim claimCur = Claims.GetClaim(ClaimsAttached[gridAttached.getSelectedIndex()].ClaimNum);
        FormClaimEdit FormCE = new FormClaimEdit(claimCur,Patients.getPat(claimCur.PatNum),Patients.getFamily(claimCur.PatNum));
        FormCE.IsFromBatchWindow = true;
        FormCE.ShowDialog();
        fillGrids();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        if (gridAttached.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item in the grid first.");
            return ;
        }
         
        int[] selected = new int[gridAttached.getSelectedIndices().Length];
        for (int i = 0;i < gridAttached.getSelectedIndices().Length;i++)
        {
            //remember the selected rows so that we can reselect them
            selected[i] = gridAttached.getSelectedIndices()[i];
        }
        if (selected[0] == 0)
        {
            return ;
        }
         
        for (int i = 0;i < selected.Length;i++)
        {
            //can't go up
            //In the db, move the one above down to the current pos
            ClaimProcs.SetPaymentRow(ClaimsAttached[selected[i] - 1].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, selected[i] + 1);
            //and move this row up one
            ClaimProcs.SetPaymentRow(ClaimsAttached[selected[i]].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, selected[i]);
        }
        //Then, swap them in the cached list.
        //ClaimsAttached.Reverse(selected[i]-1,2);
        fillGrids();
        for (int i = 0;i < selected.Length;i++)
        {
            gridAttached.SetSelected(selected[i] - 1, true);
        }
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        if (gridAttached.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item in the grid first.");
            return ;
        }
         
        int[] selected = new int[gridAttached.getSelectedIndices().Length];
        for (int i = 0;i < gridAttached.getSelectedIndices().Length;i++)
        {
            selected[i] = gridAttached.getSelectedIndices()[i];
        }
        if (selected[selected.Length - 1] == ClaimsAttached.Count - 1)
        {
            return ;
        }
         
        for (int i = selected.Length - 1;i >= 0;i--)
        {
            //already at the bottom
            //go backwards
            //In the db, move the one below up to the current pos
            ClaimProcs.SetPaymentRow(ClaimsAttached[selected[i] + 1].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, selected[i] + 1);
            //and move this row down one
            ClaimProcs.SetPaymentRow(ClaimsAttached[selected[i]].ClaimNum, ClaimPaymentCur.ClaimPaymentNum, selected[i] + 2);
        }
        //Then, swap them in the cached list.
        //ClaimsAttached.Reverse(selected[i],2);
        fillGrids();
        for (int i = 0;i < selected.Length;i++)
        {
            gridAttached.SetSelected(selected[i] + 1, true);
        }
    }

    private void gridOut_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //bottom grid
        //bring up claimedit window
        //after returning from the claim edit window, use a query to get a list of all the claimprocs that have amounts entered for that claim, but have ClaimPaymentNumber of 0.
        //Set all those claimprocs to be attached.
        Claim claimCur = Claims.GetClaim(ClaimsOutstanding[e.getRow()].ClaimNum);
        FormClaimEdit FormCE = new FormClaimEdit(claimCur,Patients.getPat(claimCur.PatNum),Patients.getFamily(claimCur.PatNum));
        FormCE.IsFromBatchWindow = true;
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ClaimProcs.AttachToPayment(claimCur.ClaimNum, ClaimPaymentCur.ClaimPaymentNum, ClaimPaymentCur.CheckDate, ClaimsAttached.Count + 1);
        fillGrids();
    }

    private void gridAttached_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        if (gridAttached.getSelectedIndices().Length != 1)
        {
            return ;
        }
         
        if (IsFromClaim)
        {
            return ;
        }
         
        menuRightAttached.Show(gridAttached, new Point(e.X, e.Y));
    }

    private void gridOut_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        if (gridOut.getSelectedIndices().Length != 1)
        {
            return ;
        }
         
        if (IsFromClaim)
        {
            return ;
        }
         
        menuRightOut.Show(gridOut, new Point(e.X, e.Y));
    }

    private void menuItemGotoAccount_Click(Object sender, EventArgs e) throws Exception {
        //for the upper grid
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        GotoPatNum = ClaimsAttached[gridAttached.getSelectedIndices()[0]].PatNum;
        GotoClaimNum = ClaimsAttached[gridAttached.getSelectedIndices()[0]].ClaimNum;
        Close();
    }

    private void menuItemGotoOut_Click(Object sender, EventArgs e) throws Exception {
        //for the lower grid
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        GotoPatNum = ClaimsOutstanding[gridOut.getSelectedIndices()[0]].PatNum;
        GotoClaimNum = ClaimsOutstanding[gridOut.getSelectedIndices()[0]].ClaimNum;
        Close();
    }

    //private void menuItemGoToAccount_Click(object sender,EventArgs e) {
    //Patient pat=Patients.GetPat(FormCS.GotoPatNum);
    //OnPatientSelected(FormCS.GotoPatNum,pat.GetNameLF(),pat.Email!="",pat.ChartNumber);
    //GotoModule.GotoClaim(FormCS.GotoClaimNum);
    //}
    private void showSecondaryClaims() throws Exception {
        DataTable secCPs = Claims.getSecondaryClaims(ClaimsAttached);
        if (secCPs.Rows.Count == 0)
        {
            return ;
        }
         
        String message = "Some of the payments have secondary claims: \r\n" + "Date | PatNum | Patient Name";
        for (int i = 0;i < secCPs.Rows.Count;i++)
        {
            //claimProc=secondaryClaims[i];
            message += "\r\n" + PIn.Date(secCPs.Rows[i]["ProcDate"].ToString()).ToShortDateString() + " | " + secCPs.Rows[i]["PatNum"].ToString() + " | " + Patients.GetPat(PIn.Long(secCPs.Rows[i]["PatNum"].ToString())).GetNameLF();
        }
        message += "\r\n\r\nPrint this list, then use it to review and send secondary claims.";
        MsgBoxCopyPaste msgBox = new MsgBoxCopyPaste(message);
        msgBox.ShowDialog();
    }

    private void butView_Click(Object sender, EventArgs e) throws Exception {
        FormImages formI = new FormImages();
        formI.ClaimPaymentNum = ClaimPaymentCur.ClaimPaymentNum;
        formI.ShowDialog();
        if (EobAttaches.exists(ClaimPaymentCur.ClaimPaymentNum))
        {
            textEobIsScanned.Text = Lan.g(this,"Yes");
        }
        else
        {
            textEobIsScanned.Text = Lan.g(this,"No");
        } 
        fillClaimPayment();
        //For customer 5769, who was getting ocassional Chinese chars in the Amount boxes.
        fillGrids();
    }

    //ditto
    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Delete this insurance check?"))
        {
            return ;
        }
         
        if (ClaimPaymentCur.IsPartial)
        {
        }
        else
        {
        } 
        try
        {
            //probably new
            //everyone should have permission to delete a partial payment
            //locked
            //this delete button already disabled if no permission
            ClaimPayments.delete(ClaimPaymentCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        IsDeleting = true;
        Close();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //only visible if IsFromClaim and IsNew
        if (textAmount.Text != textTotal.Text)
        {
            MsgBox.show(this,"Amounts do not match.");
            return ;
        }
         
        //No need to prompt user about secondary claims because they already went into each Account individually.
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formClaimPayBatch_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.Cancel && IsFromClaim && IsNew)
        {
            //This acts as a Cancel button. Happens when butClose or the red x is clicked.
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Delete this payment?"))
            {
                e.Cancel = true;
                return ;
            }
             
            IsDeleting = true;
        }
         
        //the actual deletion will be handled in FormClaimEdit.
        if (IsDeleting)
        {
            return ;
        }
         
        //This is here because the delete button could also set this.
        if (ClaimPaymentCur.IsPartial)
        {
            if (textAmount.Text == textTotal.Text)
            {
                if (ClaimsAttached.Count > 0)
                {
                    showSecondaryClaims();
                }
                 
                //always continues after this dlg
                ClaimPaymentCur.IsPartial = false;
                ClaimPayments.update(ClaimPaymentCur);
            }
             
        }
        else
        {
            //locked
            if (textAmount.Text != textTotal.Text)
            {
                //someone edited a locked payment
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Amounts do not match.  Continue anyway?"))
                {
                    e.Cancel = true;
                    return ;
                }
                 
            }
             
        } 
    }

}

