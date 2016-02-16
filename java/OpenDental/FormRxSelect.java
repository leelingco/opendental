//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:44 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormRxDefEdit;
import OpenDental.FormRxEdit;
import OpenDental.FormRxSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.RxAlertL;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RxDef;
import OpenDentBusiness.RxDefs;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxSendStatus;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormRxSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label labelInstructions = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butBlank;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    private Patient PatCur;
    private OpenDental.UI.ODGrid gridMain;
    private RxDef[] RxDefList = new RxDef[]();
    /**
    * This is set for any medical orders that are selected.
    */
    public long _medOrderNum = new long();
    /**
    * 
    */
    public FormRxSelect(Patient patCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        PatCur = patCur;
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRxSelect.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelInstructions = new System.Windows.Forms.Label();
        this.butBlank = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
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
        this.butCancel.Location = new System.Drawing.Point(848, 636);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
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
        this.butOK.Location = new System.Drawing.Point(756, 636);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelInstructions
        //
        this.labelInstructions.Location = new System.Drawing.Point(8, 8);
        this.labelInstructions.Name = "labelInstructions";
        this.labelInstructions.Size = new System.Drawing.Size(470, 16);
        this.labelInstructions.TabIndex = 15;
        this.labelInstructions.Text = "Please select a Prescription from the list or click Blank to start with a blank p" + "rescription.";
        //
        // butBlank
        //
        this.butBlank.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBlank.setAutosize(true);
        this.butBlank.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBlank.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBlank.setCornerRadius(4F);
        this.butBlank.Location = new System.Drawing.Point(472, 5);
        this.butBlank.Name = "butBlank";
        this.butBlank.Size = new System.Drawing.Size(75, 26);
        this.butBlank.TabIndex = 0;
        this.butBlank.Text = "&Blank";
        this.butBlank.Click += new System.EventHandler(this.butBlank_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 37);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(911, 586);
        this.gridMain.TabIndex = 16;
        this.gridMain.setTitle("Prescriptions");
        this.gridMain.setTranslationName("TableRxSetup");
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
        // FormRxSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(942, 674);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butBlank);
        this.Controls.Add(this.labelInstructions);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRxSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Prescription";
        this.Load += new System.EventHandler(this.FormRxSelect_Load);
        this.ResumeLayout(false);
    }

    private void formRxSelect_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            //We cannot allow blank prescription when using EHR, because each prescription created in this window must have an RxCui.
            //If we allowed blank, we would not know where to pull the RxCui from.
            butBlank.Visible = false;
            labelInstructions.Text = "Please select a Prescription from the list.";
        }
         
    }

    private void fillGrid() throws Exception {
        RxDefList = RxDefs.refresh();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRxSetup","Drug"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Controlled"), 70, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Sig"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Disp"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Refills"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Notes"),300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RxDefList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RxDefList[i].Drug);
            if (RxDefList[i].IsControlled)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(RxDefList[i].Sig);
            row.getCells().Add(RxDefList[i].Disp);
            row.getCells().Add(RxDefList[i].Refills);
            row.getCells().Add(RxDefList[i].Notes);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        rxSelected();
    }

    private void rxSelected() throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        //this should never happen
        RxDef RxDefCur = RxDefList[gridMain.getSelectedIndex()];
        if (PrefC.getBool(PrefName.ShowFeatureEhr) && RxDefCur.RxCui == 0)
        {
            String strMsgText = Lan.g(this,"The selected prescription is missing an RxNorm") + ".\r\n" + Lan.g(this,"Prescriptions without RxNorms cannot be exported in EHR documents") + ".\r\n" + Lan.g(this,"Edit RxNorm in Rx Template?");
            if (MsgBox.show(this,true,strMsgText))
            {
                FormRxDefEdit form = new FormRxDefEdit(RxDefCur);
                form.ShowDialog();
                RxDefCur = RxDefs.getOne(RxDefCur.RxDefNum);
            }
             
        }
         
        //FormRxDefEdit does not modify the RxDefCur object, so we must get the updated RxCui from the db.
        //Alert
        if (!RxAlertL.displayAlerts(PatCur.PatNum,RxDefCur.RxDefNum))
        {
            return ;
        }
         
        //User OK with alert
        RxPat RxPatCur = new RxPat();
        RxPatCur.RxDate = DateTime.Today;
        RxPatCur.PatNum = PatCur.PatNum;
        RxPatCur.Drug = RxDefCur.Drug;
        RxPatCur.IsControlled = RxDefCur.IsControlled;
        RxPatCur.Sig = RxDefCur.Sig;
        RxPatCur.Disp = RxDefCur.Disp;
        RxPatCur.Refills = RxDefCur.Refills;
        if (PrefC.getBool(PrefName.RxSendNewToQueue))
        {
            RxPatCur.SendStatus = RxSendStatus.InElectQueue;
        }
        else
        {
            RxPatCur.SendStatus = RxSendStatus.Unsent;
        } 
        //Notes not copied: we don't want these kinds of notes cluttering things
        FormRxEdit FormE = new FormRxEdit(PatCur,RxPatCur);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        boolean isProvOrder = false;
        if (Security.getCurUser().ProvNum != 0)
        {
            //The user who is currently logged in is a provider.
            isProvOrder = true;
        }
         
        _medOrderNum = MedicationPats.insertOrUpdateMedOrderForRx(RxPatCur,RxDefCur.RxCui,isProvOrder);
        //RxDefCur.RxCui can be 0.
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.CPOE_MedOrdered;
        newMeasureEvent.PatNum = PatCur.PatNum;
        newMeasureEvent.MoreInfo = "";
        newMeasureEvent.FKey = _medOrderNum;
        EhrMeasureEvents.insert(newMeasureEvent);
        DialogResult = DialogResult.OK;
    }

    private void butBlank_Click(Object sender, System.EventArgs e) throws Exception {
        RxPat RxPatCur = new RxPat();
        RxPatCur.RxDate = DateTime.Today;
        RxPatCur.PatNum = PatCur.PatNum;
        if (PrefC.getBool(PrefName.RxSendNewToQueue))
        {
            RxPatCur.SendStatus = RxSendStatus.InElectQueue;
        }
        else
        {
            RxPatCur.SendStatus = RxSendStatus.Unsent;
        } 
        FormRxEdit FormE = new FormRxEdit(PatCur,RxPatCur);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //We do not need to make a medical order here, because butBlank is not visible in EHR mode.
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select Rx first or click Blank");
            return ;
        }
         
        rxSelected();
    }

}


