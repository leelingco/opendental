//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEhrAptObsEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EhrAptObs;
import OpenDentBusiness.EhrAptObses;
import OpenDentBusiness.EhrAptObsType;
import OpenDentBusiness.HL7.EhrADT_A01;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrAptObses;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrAptObses  extends Form 
{

    private Appointment _appt = null;
    public FormEhrAptObses(Appointment appt) throws Exception {
        initializeComponent();
        Lan.F(this);
        _appt = appt;
    }

    private void formEhrAptObses_Load(Object sender, EventArgs e) throws Exception {
        fillGridObservations();
    }

    private void fillGridObservations() throws Exception {
        gridObservations.beginUpdate();
        gridObservations.getColumns().Clear();
        gridObservations.getColumns().add(new ODGridColumn("Observation",200));
        //0
        gridObservations.getColumns().add(new ODGridColumn("Value Type",200));
        //1
        gridObservations.getColumns().add(new ODGridColumn("Value",0));
        //2
        gridObservations.getRows().Clear();
        List<EhrAptObs> listEhrAptObses = EhrAptObses.refresh(_appt.AptNum);
        for (int i = 0;i < listEhrAptObses.Count;i++)
        {
            EhrAptObs obs = listEhrAptObses[i];
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.setTag(obs);
            row.getCells().Add(obs.IdentifyingCode.ToString());
            //0 Observation
            if (obs.ValType == EhrAptObsType.Coded)
            {
                row.getCells().Add(obs.ValType.ToString() + " - " + obs.ValCodeSystem);
                //1 Value Type
                if (StringSupport.equals(obs.ValCodeSystem, "LOINC"))
                {
                    Loinc loincValue = Loincs.getByCode(obs.ValReported);
                    row.getCells().add(loincValue.NameShort);
                }
                else //2 Value
                if (StringSupport.equals(obs.ValCodeSystem, "SNOMEDCT"))
                {
                    Snomed snomedValue = Snomeds.getByCode(obs.ValReported);
                    row.getCells().add(snomedValue.Description);
                }
                else //2 Value
                if (StringSupport.equals(obs.ValCodeSystem, "ICD9"))
                {
                    ICD9 icd9Value = ICD9s.getByCode(obs.ValReported);
                    row.getCells().add(icd9Value.Description);
                }
                else //2 Value
                if (StringSupport.equals(obs.ValCodeSystem, "ICD10"))
                {
                    Icd10 icd10Value = Icd10s.getByCode(obs.ValReported);
                    row.getCells().add(icd10Value.Description);
                }
                    
            }
            else //2 Value
            if (obs.ValType == EhrAptObsType.Address)
            {
                String sendingFacilityAddress1 = PrefC.getString(PrefName.PracticeAddress);
                String sendingFacilityAddress2 = PrefC.getString(PrefName.PracticeAddress2);
                String sendingFacilityCity = PrefC.getString(PrefName.PracticeCity);
                String sendingFacilityState = PrefC.getString(PrefName.PracticeST);
                String sendingFacilityZip = PrefC.getString(PrefName.PracticeZip);
                if (!PrefC.getBool(PrefName.EasyNoClinics) && _appt.ClinicNum != 0)
                {
                    //Using clinics and a clinic is assigned.
                    Clinic clinic = Clinics.getClinic(_appt.ClinicNum);
                    sendingFacilityAddress1 = clinic.Address;
                    sendingFacilityAddress2 = clinic.Address2;
                    sendingFacilityCity = clinic.City;
                    sendingFacilityState = clinic.State;
                    sendingFacilityZip = clinic.Zip;
                }
                 
                row.getCells().Add(obs.ValType.ToString());
                //1 Value Type
                row.getCells().add(sendingFacilityAddress1 + " " + sendingFacilityAddress2 + " " + sendingFacilityCity + " " + sendingFacilityState + " " + sendingFacilityZip);
            }
            else
            {
                //2 Value
                row.getCells().Add(obs.ValType.ToString());
                //1 Value Type
                row.getCells().add(obs.ValReported);
            }  
            //2 Value
            gridObservations.getRows().add(row);
        }
        gridObservations.endUpdate();
    }

    private void gridObservations_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        EhrAptObs obs = (EhrAptObs)gridObservations.getRows().get___idx(e.getRow()).getTag();
        FormEhrAptObsEdit formE = new FormEhrAptObsEdit(obs);
        if (formE.ShowDialog() == DialogResult.OK)
        {
            if (obs.EhrAptObsNum != 0)
            {
                //Was not deleted.
                EhrAptObses.update(obs);
            }
             
            fillGridObservations();
        }
         
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        EhrAptObs obs = new EhrAptObs();
        obs.setIsNew(true);
        obs.AptNum = _appt.AptNum;
        FormEhrAptObsEdit formE = new FormEhrAptObsEdit(obs);
        if (formE.ShowDialog() == DialogResult.OK)
        {
            EhrAptObses.insert(obs);
            fillGridObservations();
        }
         
    }

    private void butExportHL7_Click(Object sender, EventArgs e) throws Exception {
        EhrADT_A01 adt_a03 = null;
        try
        {
            adt_a03 = new EhrADT_A01(_appt);
        }
        catch (Exception ex)
        {
            //Exception happens when validation fails.
            MessageBox.Show(ex.Message);
            return ;
        }

        //Show validation error messages.
        String outputStr = adt_a03.generateMessage();
        SaveFileDialog dlg = new SaveFileDialog();
        dlg.FileName = "adt.txt";
        DialogResult result = dlg.ShowDialog();
        if (result != DialogResult.OK)
        {
            return ;
        }
         
        if (File.Exists(dlg.FileName))
        {
            if (MessageBox.Show("Overwrite existing file?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        File.WriteAllText(dlg.FileName, outputStr);
        MessageBox.Show("Saved");
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrAptObses.class);
        this.butCancel = new OpenDental.UI.Button();
        this.gridObservations = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butExportHL7 = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(558, 447);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridObservations
        //
        this.gridObservations.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridObservations.setHScrollVisible(false);
        this.gridObservations.Location = new System.Drawing.Point(12, 12);
        this.gridObservations.Name = "gridObservations";
        this.gridObservations.setScrollValue(0);
        this.gridObservations.Size = new System.Drawing.Size(621, 429);
        this.gridObservations.TabIndex = 4;
        this.gridObservations.setTitle(null);
        this.gridObservations.setTranslationName(null);
        this.gridObservations.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridObservations.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridObservations_CellDoubleClick(sender, e);
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
        this.butAdd.Location = new System.Drawing.Point(12, 447);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(86, 24);
        this.butAdd.TabIndex = 73;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butExportHL7
        //
        this.butExportHL7.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExportHL7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butExportHL7.setAutosize(true);
        this.butExportHL7.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExportHL7.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExportHL7.setCornerRadius(4F);
        this.butExportHL7.Location = new System.Drawing.Point(286, 447);
        this.butExportHL7.Name = "butExportHL7";
        this.butExportHL7.Size = new System.Drawing.Size(75, 24);
        this.butExportHL7.TabIndex = 74;
        this.butExportHL7.Text = "Export HL7";
        this.butExportHL7.Click += new System.EventHandler(this.butExportHL7_Click);
        //
        // FormEhrAptObses
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(645, 483);
        this.Controls.Add(this.butExportHL7);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridObservations);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(100, 100);
        this.Name = "FormEhrAptObses";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment Observations";
        this.Load += new System.EventHandler(this.FormEhrAptObses_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridObservations;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butExportHL7;
}


