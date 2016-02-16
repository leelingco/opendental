//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormInterventionEdit;
import OpenDental.FormMedPat;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.EhrCode;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Intervention;
import OpenDentBusiness.InterventionCodeSet;
import OpenDentBusiness.Interventions;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormInterventions;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormInterventions  extends Form 
{

    public Patient PatCur;
    private List<Intervention> listIntervention = new List<Intervention>();
    private List<MedicationPat> listMedPats = new List<MedicationPat>();
    public FormInterventions() throws Exception {
        initializeComponent();
    }

    private void formInterventions_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Prov",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Intervention Type",115);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code System",85);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code Description",300);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        listIntervention = Interventions.refresh(PatCur.PatNum);
        for (int i = 0;i < listIntervention.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listIntervention[i].DateEntry.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(listIntervention[i].ProvNum));
            row.getCells().Add(listIntervention[i].CodeSet.ToString());
            row.getCells().Add(listIntervention[i].CodeValue);
            row.getCells().Add(listIntervention[i].CodeSystem);
            //Description of Intervention---------------------------------------------
            //to get description, first determine which table the code is from.  Interventions are allowed to be SNOMEDCT, ICD9, ICD10, HCPCS, or CPT.
            String descript = "";
            CodeSystem __dummyScrutVar0 = listIntervention[i].CodeSystem;
            if (__dummyScrutVar0.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(listIntervention[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("ICD9CM"))
            {
                ICD9 i9Cur = ICD9s.GetByCode(listIntervention[i].CodeValue);
                if (i9Cur != null)
                {
                    descript = i9Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("ICD10CM"))
            {
                Icd10 i10Cur = Icd10s.GetByCode(listIntervention[i].CodeValue);
                if (i10Cur != null)
                {
                    descript = i10Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("HCPCS"))
            {
                Hcpcs hCur = Hcpcses.GetByCode(listIntervention[i].CodeValue);
                if (hCur != null)
                {
                    descript = hCur.DescriptionShort;
                }
                 
            }
            else if (__dummyScrutVar0.equals("CPT"))
            {
                Cpt cptCur = Cpts.GetByCode(listIntervention[i].CodeValue);
                if (cptCur != null)
                {
                    descript = cptCur.Description;
                }
                 
            }
                 
            row.getCells().add(descript);
            row.getCells().Add(listIntervention[i].Note);
            row.setTag(listIntervention[i]);
            gridMain.getRows().add(row);
        }
        listMedPats = MedicationPats.refresh(PatCur.PatNum,true);
        if (listMedPats.Count > 0)
        {
            //The following medications are used as interventions for some measures.  Include them in the intervention window if they belong to these value sets.
            //Above Normal Medications RxNorm Value Set, Below Normal Medications RxNorm Value Set, Tobacco Use Cessation Pharmacotherapy Value Set
            List<String> listVS = new List<String>{ "2.16.840.1.113883.3.600.1.1498", "2.16.840.1.113883.3.600.1.1499", "2.16.840.1.113883.3.526.3.1190" };
            List<EhrCode> listEhrMeds = EhrCodes.GetForValueSetOIDs(listVS, true);
            for (int i = listMedPats.Count - 1;i > -1;i--)
            {
                boolean found = false;
                for (int j = 0;j < listEhrMeds.Count;j++)
                {
                    if (listMedPats[i].RxCui.ToString() == listEhrMeds[j].CodeValue)
                    {
                        found = true;
                        break;
                    }
                     
                }
                if (!found)
                {
                    listMedPats.RemoveAt(i);
                }
                 
            }
        }
         
        for (int i = 0;i < listMedPats.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listMedPats[i].DateStart.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(listMedPats[i].ProvNum));
            if (listMedPats[i].RxCui == 314153 || listMedPats[i].RxCui == 692876)
            {
                row.getCells().Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Medication");
            }
            else if (listMedPats[i].RxCui == 577154 || listMedPats[i].RxCui == 860215 || listMedPats[i].RxCui == 860221 || listMedPats[i].RxCui == 860225 || listMedPats[i].RxCui == 860231)
            {
                row.getCells().Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Medication");
            }
            else
            {
                //There are 48 total medications that can be used as interventions.  The remaining 41 medications are tobacco cessation medications
                row.getCells().Add(InterventionCodeSet.TobaccoCessation.ToString() + " Medication");
            }  
            row.getCells().Add(listMedPats[i].RxCui.ToString());
            row.getCells().add("RXNORM");
            //Medications that are used as interventions are all RxNorm codes, get description from that table
            String descript = RxNorms.GetDescByRxCui(listMedPats[i].RxCui.ToString());
            row.getCells().add(descript);
            row.getCells().Add(listMedPats[i].PatNote);
            row.setTag(listMedPats[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Object objCur = gridMain.getRows().get___idx(e.getRow()).getTag();
        if (StringSupport.equals(objCur.GetType().Name, "Intervention"))
        {
            //grid can contain MedicationPat or Intervention objects, launch appropriate window
            FormInterventionEdit FormInt = new FormInterventionEdit();
            FormInt.InterventionCur = (Intervention)objCur;
            FormInt.IsAllTypes = false;
            FormInt.IsSelectionMode = false;
            FormInt.ShowDialog();
        }
         
        if (StringSupport.equals(objCur.GetType().Name, "MedicationPat"))
        {
            FormMedPat FormMP = new FormMedPat();
            FormMP.MedicationPatCur = (MedicationPat)objCur;
            FormMP.IsNew = false;
            FormMP.ShowDialog();
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormInterventionEdit FormInt = new FormInterventionEdit();
        FormInt.InterventionCur = new Intervention();
        FormInt.InterventionCur.setIsNew(true);
        FormInt.InterventionCur.PatNum = PatCur.PatNum;
        FormInt.InterventionCur.ProvNum = PatCur.PriProv;
        FormInt.InterventionCur.DateEntry = DateTime.Now;
        FormInt.IsAllTypes = true;
        FormInt.IsSelectionMode = true;
        FormInt.ShowDialog();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInterventions.class);
        this.butAdd = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(11, 388);
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
        this.butClose.Location = new System.Drawing.Point(781, 388);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 31;
        this.butClose.Text = "&Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(11, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(845, 360);
        this.gridMain.TabIndex = 30;
        this.gridMain.setTitle("Interventions");
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
        // FormInterventions
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(867, 423);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormInterventions";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Interventions";
        this.Load += new System.EventHandler(this.FormInterventions_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
}


