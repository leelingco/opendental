//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:39 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormRecallTypeEdit;
import OpenDental.FormRecallTypes;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTrigger;
import OpenDentBusiness.RecallTriggers;
import OpenDentBusiness.RecallType;
import OpenDentBusiness.RecallTypeC;
import OpenDentBusiness.RecallTypes;

/**
* Summary description for FormBasicTemplate.
*/
public class FormRecallTypes  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butSynch;
    private Label label1 = new Label();
    private boolean changed = new boolean();
    //public bool IsSelectionMode;
    //<summary>Only used if IsSelectionMode.  On OK, contains selected pharmacyNum.  Can be 0.  Can also be set ahead of time externally.</summary>
    //public int SelectedPharmacyNum;
    /**
    * 
    */
    public FormRecallTypes() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRecallTypes.class);
        this.label1 = new System.Windows.Forms.Label();
        this.butSynch = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(290, 298);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(337, 23);
        this.label1.TabIndex = 17;
        this.label1.Text = "Forces a resynchronization of recall for all patients.";
        //
        // butSynch
        //
        this.butSynch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSynch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSynch.setAutosize(true);
        this.butSynch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSynch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSynch.setCornerRadius(4F);
        this.butSynch.Location = new System.Drawing.Point(211, 292);
        this.butSynch.Name = "butSynch";
        this.butSynch.Size = new System.Drawing.Size(75, 24);
        this.butSynch.TabIndex = 16;
        this.butSynch.Text = "Synch";
        this.butSynch.Click += new System.EventHandler(this.butSynch_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(842, 262);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("RecallTypes");
        this.gridMain.setTranslationName("TableRecallTypes");
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
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(17, 292);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
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
        this.butClose.Location = new System.Drawing.Point(784, 292);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormRecallTypes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(887, 332);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butSynch);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRecallTypes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Recall Types";
        this.Load += new System.EventHandler(this.FormRecallTypes_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormRecallTypes_FormClosing);
        this.ResumeLayout(false);
    }

    private void formRecallTypes_Load(Object sender, System.EventArgs e) throws Exception {
        /*if(IsSelectionMode){
        				butClose.Text=Lan.g(this,"Cancel");
        			}
        			else{
        				butOK.Visible=false;
        				butNone.Visible=false;
        			}*/
        fillGrid();
    }

    /*if(SelectedPharmacyNum!=0){
    				for(int i=0;i<PharmacyC.Listt.Count;i++){
    					if(PharmacyC.Listt[i].PharmacyNum==SelectedPharmacyNum){
    						gridMain.SetSelected(i,true);
    						break;
    					}
    				}
    			}*/
    private void fillGrid() throws Exception {
        RecallTypes.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRecallTypes","Description"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallTypes","Special Type"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallTypes","Triggers"),190);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallTypes","Interval"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallTypes","Time Pattern"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallTypes","Procedures"),190);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            //string txt;
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RecallTypeC.getListt()[i].Description);
            row.getCells().Add(RecallTypes.GetSpecialTypeStr(RecallTypeC.getListt()[i].RecallTypeNum));
            row.getCells().Add(GetStringForType(RecallTypeC.getListt()[i].RecallTypeNum));
            row.getCells().Add(RecallTypeC.getListt()[i].DefaultInterval.ToString());
            row.getCells().Add(RecallTypeC.getListt()[i].TimePattern);
            row.getCells().Add(RecallTypeC.getListt()[i].Procedures);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private String getStringForType(long recallTypeNum) throws Exception {
        if (recallTypeNum == 0)
        {
            return "";
        }
         
        List<RecallTrigger> triggerList = RecallTriggers.getForType(recallTypeNum);
        String retVal = "";
        for (int i = 0;i < triggerList.Count;i++)
        {
            if (i > 0)
            {
                retVal += ",";
            }
             
            retVal += ProcedureCodes.GetStringProcCode(triggerList[i].CodeNum);
        }
        return retVal;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormRecallTypeEdit FormRE = new FormRecallTypeEdit();
        FormRE.RecallTypeCur = new RecallType();
        FormRE.RecallTypeCur.setIsNew(true);
        FormRE.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        /*if(IsSelectionMode){
        				SelectedPharmacyNum=PharmacyC.Listt[e.Row].PharmacyNum;
        				DialogResult=DialogResult.OK;
        				return;
        			}
        			else{*/
        FormRecallTypeEdit FormR = new FormRecallTypeEdit();
        FormR.RecallTypeCur = RecallTypeC.getListt()[e.getRow()].Copy();
        FormR.ShowDialog();
        fillGrid();
        changed = true;
    }

    //}*/
    private void butSynch_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        DataValid.setInvalid(InvalidType.RecallTypes);
        Recalls.synchAllPatients();
        changed = false;
        Cursor = Cursors.Default;
        MsgBox.show(this,"Done.");
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formRecallTypes_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.RecallTypes);
            if (MessageBox.Show(Lan.g(this,"Recalls for all patients should be synchronized.  Synchronize now?"), "", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                Cursor = Cursors.WaitCursor;
                Recalls.synchAllPatients();
                Cursor = Cursors.Default;
            }
             
        }
         
    }

}


