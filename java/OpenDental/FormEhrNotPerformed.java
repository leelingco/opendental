//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEhrNotPerformedEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Cvx;
import OpenDentBusiness.Cvxs;
import OpenDentBusiness.EhrNotPerformed;
import OpenDentBusiness.EhrNotPerformedItem;
import OpenDentBusiness.EhrNotPerformeds;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrNotPerformed;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrNotPerformed  extends Form 
{

    private List<EhrNotPerformed> listNotPerf = new List<EhrNotPerformed>();
    public Patient PatCur;
    public FormEhrNotPerformed() throws Exception {
        initializeComponent();
    }

    private void formEhrNotPerformed_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Prov",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Item Not Performed",130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code",102);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code Description",150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Reason Code",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Reason Description",150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",150);
        gridMain.getColumns().add(col);
        listNotPerf = EhrNotPerformeds.refresh(PatCur.PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listNotPerf.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listNotPerf[i].DateEntry.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(listNotPerf[i].ProvNum));
            //Item not performed------------------------------------------------------------
            CodeValue __dummyScrutVar0 = listNotPerf[i].CodeValue;
            if (__dummyScrutVar0.equals("39156-5"))
            {
                //BMI exam
                row.getCells().Add(EhrNotPerformedItem.BMIExam.ToString());
            }
            else if (__dummyScrutVar0.equals("428191000124101"))
            {
                //CurrentMedsDocumented
                row.getCells().Add(EhrNotPerformedItem.DocumentCurrentMeds.ToString());
            }
            else //History of tobacco use Narrative
            //Have you used tobacco in the last 30 days
            if (__dummyScrutVar0.equals("11366-2") || __dummyScrutVar0.equals("68535-4") || __dummyScrutVar0.equals("68536-2"))
            {
                //Have you used smokeless tobacco in last 30 days
                row.getCells().Add(EhrNotPerformedItem.TobaccoScreening.ToString());
            }
            else
            {
                //We will default to Influenza Vaccine, there are 26 codes, for this item
                row.getCells().Add(EhrNotPerformedItem.InfluenzaVaccination.ToString());
            }   
            //Code not performed------------------------------------------------------------
            row.getCells().Add(listNotPerf[i].CodeValue + " (" + listNotPerf[i].CodeSystem + ")");
            //Description of code not performed---------------------------------------------
            String descript = "";
            //to get description, first determine which table the code is from.  EhrNotPerformed is allowed to be CPT, CVX, LOINC, SNOMEDCT.
            CodeSystem __dummyScrutVar1 = listNotPerf[i].CodeSystem;
            if (__dummyScrutVar1.equals("CPT"))
            {
                Cpt cptCur = Cpts.GetByCode(listNotPerf[i].CodeValue);
                if (cptCur != null)
                {
                    descript = cptCur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("CVX"))
            {
                Cvx cvxCur = Cvxs.GetOneFromDb(listNotPerf[i].CodeValue);
                if (cvxCur != null)
                {
                    descript = cvxCur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("LOINC"))
            {
                Loinc lCur = Loincs.GetByCode(listNotPerf[i].CodeValue);
                if (lCur != null)
                {
                    descript = lCur.NameLongCommon;
                }
                 
            }
            else if (__dummyScrutVar1.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(listNotPerf[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
                
            row.getCells().add(descript);
            //Reason Code-------------------------------------------------------------------
            row.getCells().Add(listNotPerf[i].CodeValueReason + " (" + listNotPerf[i].CodeSystemReason + ")");
            //Reason Description------------------------------------------------------------
            descript = "";
            if (!StringSupport.equals(listNotPerf[i].CodeValueReason, ""))
            {
                //reason codes are only allowed to be SNOMEDCT codes
                Snomed sCur = Snomeds.GetByCode(listNotPerf[i].CodeValueReason);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
             
            row.getCells().add(descript);
            //Note--------------------------------------------------------------------------
            row.getCells().Add(listNotPerf[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrNotPerformedEdit FormEE = new FormEhrNotPerformedEdit();
        FormEE.EhrNotPerfCur = listNotPerf[e.getRow()];
        FormEE.SelectedItemIndex = (int)Enum.Parse(EhrNotPerformedItem.class, gridMain.getRows().get___idx(e.getRow()).getCells()[2].Text, true);
        //Parse the text displayed from the enum and convert to int
        FormEE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        List<String> listItems = new List<String>();
        for (int i = 0;i < Enum.GetNames(EhrNotPerformedItem.class).Length;i++)
        {
            listItems.Add(Enum.GetNames(EhrNotPerformedItem.class)[i]);
        }
        InputBox chooseItem = new InputBox(Lan.g(this,"Select the item not being performed from the list below."), listItems);
        if (chooseItem.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (chooseItem.comboSelection.SelectedIndex == -1)
        {
            MsgBox.show(this,"You must select an item that is not being performed from the list of available items.");
            return ;
        }
         
        FormEhrNotPerformedEdit FormEE = new FormEhrNotPerformedEdit();
        FormEE.EhrNotPerfCur = new EhrNotPerformed();
        FormEE.EhrNotPerfCur.setIsNew(true);
        FormEE.EhrNotPerfCur.PatNum = PatCur.PatNum;
        FormEE.EhrNotPerfCur.ProvNum = PatCur.PriProv;
        FormEE.EhrNotPerfCur.DateEntry = DateTime.Now;
        FormEE.SelectedItemIndex = chooseItem.comboSelection.SelectedIndex;
        //Send in the int of index of selected item
        FormEE.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrNotPerformed.class);
        this.butAdd = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(12, 388);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 32;
        this.butAdd.Text = "&Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(830, 388);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 31;
        this.butClose.Text = "&Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(893, 361);
        this.gridMain.TabIndex = 30;
        this.gridMain.setTitle("Clinical Quality Measure Items Not Performed");
        this.gridMain.setTranslationName(null);
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
        // FormEhrNotPerformed
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(917, 423);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrNotPerformed";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "CQM Items Not Performed";
        this.Load += new System.EventHandler(this.FormEhrNotPerformed_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
}


