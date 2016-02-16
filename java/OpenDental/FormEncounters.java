//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEncounterEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Encounter;
import OpenDentBusiness.Encounters;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Patient;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEncounters;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEncounters  extends Form 
{

    private List<Encounter> listEncs = new List<Encounter>();
    public Patient PatCur;
    public FormEncounters() throws Exception {
        initializeComponent();
    }

    public void formEncounters_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Prov",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code",110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",180);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",100);
        gridMain.getColumns().add(col);
        listEncs = Encounters.refresh(PatCur.PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listEncs.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listEncs[i].DateEncounter.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(listEncs[i].ProvNum));
            row.getCells().Add(listEncs[i].CodeValue);
            String descript = "";
            //to get description, first determine which table the code is from.  Encounter is only allowed to be a CDT, CPT, HCPCS, and SNOMEDCT.
            CodeSystem __dummyScrutVar0 = listEncs[i].CodeSystem;
            if (__dummyScrutVar0.equals("CDT"))
            {
                descript = ProcedureCodes.GetProcCode(listEncs[i].CodeValue).Descript;
            }
            else if (__dummyScrutVar0.equals("CPT"))
            {
                Cpt cptCur = Cpts.GetByCode(listEncs[i].CodeValue);
                if (cptCur != null)
                {
                    descript = cptCur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("HCPCS"))
            {
                Hcpcs hCur = Hcpcses.GetByCode(listEncs[i].CodeValue);
                if (hCur != null)
                {
                    descript = hCur.DescriptionShort;
                }
                 
            }
            else if (__dummyScrutVar0.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(listEncs[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
                
            row.getCells().add(descript);
            row.getCells().Add(listEncs[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEncounterEdit FormEE = new FormEncounterEdit(listEncs[e.getRow()]);
        FormEE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        Encounter EncCur = new Encounter();
        EncCur.PatNum = PatCur.PatNum;
        EncCur.ProvNum = PatCur.PriProv;
        EncCur.DateEncounter = DateTime.Today;
        EncCur.setIsNew(true);
        FormEncounterEdit FormEE = new FormEncounterEdit(EncCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEncounters.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(718, 361);
        this.gridMain.TabIndex = 26;
        this.gridMain.setTitle("Encounters");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(655, 388);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 124;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(12, 388);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 123;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormEncounters
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(742, 423);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEncounters";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Encounters";
        this.Load += new System.EventHandler(this.FormEncounters_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
}


