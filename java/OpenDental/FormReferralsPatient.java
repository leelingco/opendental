//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:41 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormRefAttachEdit;
import OpenDental.FormReferralSelect;
import OpenDental.FormReferralsPatient;
import OpenDental.FormSheetFillEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetsInternal;

/**
* 
*/
public class FormReferralsPatient  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.Button butAddFrom;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public long PatNum = new long();
    private OpenDental.UI.Button butSlip;
    private OpenDental.UI.Button butAddTo;
    private List<RefAttach> RefAttachList = new List<RefAttach>();
    private CheckBox checkShowAll = new CheckBox();
    /**
    * This number is normally zero.  If this number is set externally before opening this form, then this will behave differently.
    */
    public long ProcNum = new long();
    /**
    * Selection mode is currently only used for transitions of care.  Changes text of butClose to Cancel and shows OK and None buttons.
    */
    public boolean IsSelectionMode = new boolean();
    /**
    * This number is normally zero.  If in selection mode, this will be the PK of the selected refattach.
    */
    public long RefAttachNum = new long();
    /**
    * 
    */
    public FormReferralsPatient() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReferralsPatient.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.checkShowAll = new System.Windows.Forms.CheckBox();
        this.butAddTo = new OpenDental.UI.Button();
        this.butSlip = new OpenDental.UI.Button();
        this.butAddFrom = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butNone = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(839, 261);
        this.gridMain.TabIndex = 74;
        this.gridMain.setTitle("Referrals Attached");
        this.gridMain.setTranslationName("TableRefList");
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
        // checkShowAll
        //
        this.checkShowAll.Location = new System.Drawing.Point(560, 18);
        this.checkShowAll.Name = "checkShowAll";
        this.checkShowAll.Size = new System.Drawing.Size(162, 20);
        this.checkShowAll.TabIndex = 92;
        this.checkShowAll.Text = "Show All";
        this.checkShowAll.UseVisualStyleBackColor = true;
        this.checkShowAll.Click += new System.EventHandler(this.checkShowAll_Click);
        //
        // butAddTo
        //
        this.butAddTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddTo.setAutosize(true);
        this.butAddTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddTo.setCornerRadius(4F);
        this.butAddTo.Image = Resources.getAdd();
        this.butAddTo.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddTo.Location = new System.Drawing.Point(127, 10);
        this.butAddTo.Name = "butAddTo";
        this.butAddTo.Size = new System.Drawing.Size(94, 24);
        this.butAddTo.TabIndex = 91;
        this.butAddTo.Text = "Refer To";
        this.butAddTo.Click += new System.EventHandler(this.butAddTo_Click);
        //
        // butSlip
        //
        this.butSlip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSlip.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSlip.setAutosize(true);
        this.butSlip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSlip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSlip.setCornerRadius(4F);
        this.butSlip.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSlip.Location = new System.Drawing.Point(12, 317);
        this.butSlip.Name = "butSlip";
        this.butSlip.Size = new System.Drawing.Size(86, 24);
        this.butSlip.TabIndex = 90;
        this.butSlip.Text = "Referral Slip";
        this.butSlip.Click += new System.EventHandler(this.butSlip_Click);
        //
        // butAddFrom
        //
        this.butAddFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddFrom.setAutosize(true);
        this.butAddFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddFrom.setCornerRadius(4F);
        this.butAddFrom.Image = Resources.getAdd();
        this.butAddFrom.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddFrom.Location = new System.Drawing.Point(12, 10);
        this.butAddFrom.Name = "butAddFrom";
        this.butAddFrom.Size = new System.Drawing.Size(109, 24);
        this.butAddFrom.TabIndex = 72;
        this.butAddFrom.Text = "Referred From";
        this.butAddFrom.Click += new System.EventHandler(this.butAddFrom_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(776, 317);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(695, 316);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 93;
        this.butOK.Text = "OK";
        this.butOK.Visible = false;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.Location = new System.Drawing.Point(394, 317);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(75, 24);
        this.butNone.TabIndex = 94;
        this.butNone.Text = "None";
        this.butNone.Visible = false;
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // FormReferralsPatient
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(863, 352);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.checkShowAll);
        this.Controls.Add(this.butAddTo);
        this.Controls.Add(this.butSlip);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAddFrom);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.MinimumSize = new System.Drawing.Size(871, 200);
        this.Name = "FormReferralsPatient";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Referrals for Patient";
        this.Load += new System.EventHandler(this.FormReferralsPatient_Load);
        this.ResumeLayout(false);
    }

    private void formReferralsPatient_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.One);
            butClose.Text = "Cancel";
            butOK.Visible = true;
            butNone.Visible = true;
        }
         
        if (ProcNum != 0)
        {
            Text = Lan.g(this,"Referrals");
            butAddFrom.Visible = false;
        }
        else
        {
            //all for patient
            checkShowAll.Visible = false;
        } 
        //we will always show all
        fillGrid();
        if (RefAttachList.Count > 0 && !IsSelectionMode)
        {
            gridMain.setSelected(0,true);
        }
         
    }

    private void checkShowAll_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        RefAttachList = RefAttaches.RefreshFiltered(PatNum, checkShowAll.Checked, ProcNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRefList","From/To"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Name"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Status"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Proc"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Note"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRefList","Email"),120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            //Referral referral;
            row = new OpenDental.UI.ODGridRow();
            if (RefAttachList[i].IsFrom)
            {
                row.getCells().add(Lan.g(this,"From"));
            }
            else
            {
                row.getCells().add(Lan.g(this,"To"));
            } 
            row.getCells().Add(Referrals.GetNameFL(RefAttachList[i].ReferralNum));
            //referral=ReferralL.GetReferral(RefAttachList[i].ReferralNum);
            if (RefAttachList[i].RefDate.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(RefAttachList[i].RefDate.ToShortDateString());
            } 
            row.getCells().Add(Lan.g("enumReferralToStatus", RefAttachList[i].RefToStatus.ToString()));
            if (RefAttachList[i].ProcNum == 0)
            {
                row.getCells().add("");
            }
            else
            {
                Procedure proc = Procedures.GetOneProc(RefAttachList[i].ProcNum, false);
                String str = Procedures.getDescription(proc);
                row.getCells().add(str);
            } 
            row.getCells().Add(RefAttachList[i].Note);
            Referral referral = Referrals.GetReferral(RefAttachList[i].ReferralNum);
            row.getCells().add(referral.EMail);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //This does not automatically select a retattch when in selection mode; it just lets user edit.
        FormRefAttachEdit FormRAE2 = new FormRefAttachEdit();
        RefAttach refattach = RefAttachList[e.getRow()].Copy();
        FormRAE2.RefAttachCur = refattach;
        FormRAE2.ShowDialog();
        fillGrid();
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            //reselect
            if (RefAttachList[i].RefAttachNum == refattach.RefAttachNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butAddFrom_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.RefAttachAdd))
        {
            return ;
        }
         
        FormReferralSelect FormRS = new FormReferralSelect();
        FormRS.IsSelectionMode = true;
        FormRS.ShowDialog();
        if (FormRS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        RefAttach refattach = new RefAttach();
        refattach.ReferralNum = FormRS.SelectedReferral.ReferralNum;
        refattach.PatNum = PatNum;
        refattach.IsFrom = true;
        refattach.RefDate = DateTimeOD.getToday();
        if (FormRS.SelectedReferral.IsDoctor)
        {
            //whether using ehr or not
            //we're not going to ask.  That's stupid.
            //if(MsgBox.Show(this,MsgBoxButtons.YesNo,"Is this an incoming transition of care from another provider?")){
            refattach.IsTransitionOfCare = true;
        }
         
        int order = 0;
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            if (RefAttachList[i].ItemOrder > order)
            {
                order = RefAttachList[i].ItemOrder;
            }
             
        }
        refattach.ItemOrder = order + 1;
        RefAttaches.Insert(refattach);
        SecurityLogs.makeLogEntry(Permissions.RefAttachAdd,PatNum,"Referred From " + Referrals.GetNameFL(refattach.ReferralNum));
        fillGrid();
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            if (RefAttachList[i].RefAttachNum == refattach.RefAttachNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butAddTo_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.RefAttachAdd))
        {
            return ;
        }
         
        FormReferralSelect FormRS = new FormReferralSelect();
        FormRS.IsSelectionMode = true;
        FormRS.ShowDialog();
        if (FormRS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        RefAttach refattach = new RefAttach();
        refattach.ReferralNum = FormRS.SelectedReferral.ReferralNum;
        refattach.PatNum = PatNum;
        refattach.IsFrom = false;
        refattach.RefDate = DateTimeOD.getToday();
        if (FormRS.SelectedReferral.IsDoctor)
        {
            refattach.IsTransitionOfCare = true;
        }
         
        int order = 0;
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            if (RefAttachList[i].ItemOrder > order)
            {
                order = RefAttachList[i].ItemOrder;
            }
             
        }
        refattach.ItemOrder = order + 1;
        refattach.ProcNum = ProcNum;
        RefAttaches.Insert(refattach);
        SecurityLogs.makeLogEntry(Permissions.RefAttachAdd,PatNum,"Referred To " + Referrals.GetNameFL(refattach.ReferralNum));
        fillGrid();
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            if (RefAttachList[i].ReferralNum == refattach.ReferralNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butSlip_Click(Object sender, EventArgs e) throws Exception {
        int idx = gridMain.getSelectedIndex();
        if (idx == -1)
        {
            MsgBox.show(this,"Please select a referral first");
            return ;
        }
         
        Referral referral = Referrals.GetReferral(RefAttachList[idx].ReferralNum);
        SheetDef sheetDef;
        if (referral.Slip == 0)
        {
            sheetDef = SheetsInternal.getSheetDef(SheetInternalType.ReferralSlip);
        }
        else
        {
            sheetDef = SheetDefs.getSheetDef(referral.Slip);
        } 
        Sheet sheet = SheetUtil.createSheet(sheetDef,PatNum);
        SheetParameter.setParameter(sheet,"PatNum",PatNum);
        SheetParameter.setParameter(sheet,"ReferralNum",referral.ReferralNum);
        SheetFiller.fillFields(sheet);
        SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
        FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
        FormS.ShowDialog();
    }

    //grid will not be refilled, so no need to reselect.
    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() < 0)
        {
            MsgBox.show(this,"Please select a referral first");
            return ;
        }
         
        RefAttachNum = RefAttachList[gridMain.getSelectedIndex()].RefAttachNum;
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            //Allows us to know that the user wants to cancel out of picking a refattach.  They should click None if no refattach is needed.
            DialogResult = DialogResult.Cancel;
        }
         
        Close();
    }

}


