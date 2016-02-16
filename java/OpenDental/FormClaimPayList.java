//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormClaimPayBatch;
import OpenDental.FormClaimPayEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormClaimPayList  extends Form 
{

    List<ClaimPayment> ListClaimPay = new List<ClaimPayment>();
    /**
    * If this is not zero upon closing, then we will jump to the account module of that patient and highlight the claim.
    */
    public long GotoClaimNum = new long();
    /**
    * If this is not zero upon closing, then we will jump to the account module of that patient and highlight the claim.
    */
    public long GotoPatNum = new long();
    //<summary>Set to true if the batch list was accessed originally by going through a claim.  This disables the GotoAccount feature.</summary>
    //public bool IsFromClaim;
    public FormClaimPayList() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formClaimPayList_Load(Object sender, EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Now.AddMonths(-1).ToShortDateString();
        textDateTo.Text = DateTime.Now.ToShortDateString();
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("All");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
        } 
        fillGrid();
    }

    private void fillGrid() throws Exception {
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        long clinicNum = 0;
        if (comboClinic.SelectedIndex > 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        ListClaimPay = ClaimPayments.getForDateRange(dateFrom,dateTo,clinicNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Amount"), 75, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Partial"), 40, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Carrier"),250);
        gridMain.getColumns().add(col);
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            col = new ODGridColumn(Lan.g(this,"Clinic"),100);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g(this,"Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListClaimPay.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (ListClaimPay[i].CheckDate.Year < 1800)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListClaimPay[i].CheckDate.ToShortDateString());
            } 
            row.getCells().Add(ListClaimPay[i].CheckAmt.ToString("c"));
            row.getCells().add(ListClaimPay[i].IsPartial ? "X" : "");
            row.getCells().Add(ListClaimPay[i].CarrierName);
            if (!PrefC.getBool(PrefName.EasyNoClinics))
            {
                row.getCells().Add(Clinics.GetDesc(ListClaimPay[i].ClinicNum));
            }
             
            row.getCells().Add(ListClaimPay[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.scrollToEnd();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.InsPayCreate))
        {
            return ;
        }
         
        //date not checked here, but it will be checked when saving the check to prevent backdating
        ClaimPayment claimPayment = new ClaimPayment();
        claimPayment.CheckDate = DateTime.Now;
        claimPayment.IsPartial = true;
        FormClaimPayEdit FormCPE = new FormClaimPayEdit(claimPayment);
        FormCPE.IsNew = true;
        FormCPE.ShowDialog();
        if (FormCPE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        FormClaimPayBatch FormCPB = new FormClaimPayBatch(claimPayment);
        //FormCPB.IsFromClaim=IsFromClaim;
        FormCPB.ShowDialog();
        if (FormCPB.GotoClaimNum != 0)
        {
            GotoClaimNum = FormCPB.GotoClaimNum;
            GotoPatNum = FormCPB.GotoPatNum;
            Close();
        }
        else
        {
            fillGrid();
        } 
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.InsPayCreate))
        {
            return ;
        }
         
        FormClaimPayBatch FormCPB = new FormClaimPayBatch(ListClaimPay[gridMain.getSelectedIndex()]);
        //FormCPB.IsFromClaim=IsFromClaim;
        FormCPB.ShowDialog();
        if (FormCPB.GotoClaimNum != 0)
        {
            GotoClaimNum = FormCPB.GotoClaimNum;
            GotoPatNum = FormCPB.GotoPatNum;
            Close();
        }
        else
        {
            fillGrid();
        } 
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.labelFromDate = new System.Windows.Forms.Label();
        this.labelToDate = new System.Windows.Forms.Label();
        this.labelClinic = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.butRefresh = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textDateTo = new OpenDental.ValidDate();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.textDateFrom = new OpenDental.ValidDate();
        this.SuspendLayout();
        //
        // labelFromDate
        //
        this.labelFromDate.Location = new System.Drawing.Point(24, 18);
        this.labelFromDate.Name = "labelFromDate";
        this.labelFromDate.Size = new System.Drawing.Size(77, 14);
        this.labelFromDate.TabIndex = 11;
        this.labelFromDate.Text = "From Date";
        this.labelFromDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelToDate
        //
        this.labelToDate.Location = new System.Drawing.Point(206, 17);
        this.labelToDate.Name = "labelToDate";
        this.labelToDate.Size = new System.Drawing.Size(77, 14);
        this.labelToDate.TabIndex = 12;
        this.labelToDate.Text = "To Date";
        this.labelToDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(419, 19);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(91, 14);
        this.labelClinic.TabIndex = 22;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(512, 15);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(181, 21);
        this.comboClinic.TabIndex = 23;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(779, 12);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(84, 24);
        this.butRefresh.TabIndex = 24;
        this.butRefresh.Text = "&Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(851, 526);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Insurance Payments (EOBs)");
        this.gridMain.setTranslationName("FormClaimPayList");
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
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(286, 15);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(94, 20);
        this.textDateTo.TabIndex = 14;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(12, 576);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 2;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(788, 576);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(104, 15);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(94, 20);
        this.textDateFrom.TabIndex = 13;
        //
        // FormClaimPayList
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(888, 612);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.labelToDate);
        this.Controls.Add(this.labelFromDate);
        this.Name = "FormClaimPayList";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Batch Insurance Payments";
        this.Load += new System.EventHandler(this.FormClaimPayList_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label labelFromDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelToDate = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butRefresh;
    private OpenDental.UI.Button butAdd;
}


