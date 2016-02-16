//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormEhrAmendments;
import OpenDental.FormEhrCarePlans;
import OpenDental.FormEhrClinicalSummary;
import OpenDental.FormEhrEduResourcesPat;
import OpenDental.FormEhrElectronicCopy;
import OpenDental.FormEhrEncryption;
import OpenDental.FormEhrHash;
import OpenDental.FormEhrLabOrders;
import OpenDental.FormEhrMedicalOrders;
import OpenDental.FormEhrNotPerformed;
import OpenDental.FormEhrPatientSmoking;
import OpenDental.FormEhrPatList;
import OpenDental.FormEhrQualityMeasures;
import OpenDental.FormEhrQualityMeasures2014;
import OpenDental.FormEhrReminders;
import OpenDental.FormEhrSummaryOfCare;
import OpenDental.FormEhrVaccines;
import OpenDental.FormEncounters;
import OpenDental.FormInterventions;
import OpenDental.FormMedical;
import OpenDental.FormMedPat;
import OpenDental.FormPatientEdit;
import OpenDental.FormPatientPortal;
import OpenDental.FormReferralsPatient;
import OpenDental.FormVitalsigns;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrFormResult;
import OpenDentBusiness.EhrMeasures;
import OpenDentBusiness.EhrMeasureType;
import OpenDentBusiness.EhrMu;
import OpenDentBusiness.EhrPatListElement;
import OpenDentBusiness.Family;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.MuMet;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEHR;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEHR  extends Form 
{

    public long PatNum = new long();
    private Patient PatCur;
    public PatientNote PatNotCur;
    public Family PatFamCur;
    /**
    * If ResultOnClosing=RxEdit, then this specifies which Rx to open.
    */
    public long LaunchRxNum = new long();
    /**
    * After this form closes, this can trigger other things to happen.  DialogResult is not checked.  After the other actions are taken, FormEHR opens back up again for seamless user experience.
    */
    public EhrFormResult ResultOnClosing = EhrFormResult.None;
    private List<EhrMu> listMu = new List<EhrMu>();
    /**
    * If ResultOnClosing=MedicationPatEdit, then this specifies which MedicationPat to open.
    */
    public long LaunchMedicationPatNum = new long();
    /**
    * If set to true, this will cause medical orders window to come up.
    */
    public boolean OnShowLaunchOrders = new boolean();
    /**
    * This is set every time the form is shown.  It is used to determine if there is an Ehr key for the patient's primary provider.  If not, then The main grid will show empty.
    */
    private Provider ProvPat;
    /**
    * This will be null if EHR didn't load up.  EHRTEST conditional compilation constant is used because the EHR project is only part of the solution here at HQ.  We need to use late binding in a few places so that it will still compile for people who download our sourcecode.  But late binding prevents us from stepping through for debugging, so the EHRTEST lets us switch to early binding.
    */
    public static Object ObjFormEhrMeasures = new Object();
    /**
    * This will be null if EHR didn't load up.
    */
    public static Assembly AssemblyEHR = new Assembly();
    public FormEHR() throws Exception {
        initializeComponent();
        if (PrefC.getBoolSilent(PrefName.ShowFeatureEhr,false))
        {
            constructObjFormEhrMeasuresHelper();
        }
         
    }

    /**
    * Constructs the ObjFormEhrMeasures fro use with late binding.
    */
    private static void constructObjFormEhrMeasuresHelper() throws Exception {
        String dllPathEHR = CodeBase.ODFileUtils.CombinePaths(Application.StartupPath, "EHR.dll");
        ObjFormEhrMeasures = null;
        AssemblyEHR = null;
        if (File.Exists(dllPathEHR))
        {
            //EHR.dll is available, so load it up
            AssemblyEHR = Assembly.LoadFile(dllPathEHR);
            Type type = AssemblyEHR.GetType("EHR.FormEhrMeasures");
            //namespace.class
            ObjFormEhrMeasures = Activator.CreateInstance(type);
            return ;
        }
         
    }

    /**
    * Loads a resource file from the EHR assembly and returns the file text as a string.
    * Returns "" is the EHR assembly did not load. strResourceName can be either "CCD" or "CCR".
    * This function performs a late binding to the EHR.dll, because resellers do not have EHR.dll necessarily.
    */
    public static String getEhrResource(String strResourceName) throws Exception {
        if (AssemblyEHR == null)
        {
            constructObjFormEhrMeasuresHelper();
            if (AssemblyEHR == null)
            {
                return "";
            }
             
        }
         
        Stream stream = AssemblyEHR.GetManifestResourceStream("EHR.Properties.Resources.resources");
        System.Resources.ResourceReader resourceReader = new System.Resources.ResourceReader(stream);
        String strResourceType = "";
        byte[] arrayResourceBytes = null;
        RefSupport<String> refVar___0 = new RefSupport<String>();
        RefSupport<byte[]> refVar___1 = new RefSupport<byte[]>();
        resourceReader.GetResourceData(strResourceName, refVar___0, refVar___1);
        strResourceType = refVar___0.getValue();
        arrayResourceBytes = refVar___1.getValue();
        resourceReader.Dispose();
        stream.Dispose();
        MemoryStream ms = new MemoryStream(arrayResourceBytes);
        BinaryReader br = new BinaryReader(ms);
        String retVal = br.ReadString();
        //Removes the leading binary characters from the string.
        ms.Dispose();
        br.Dispose();
        return retVal;
    }

    private void formEHR_Load(Object sender, EventArgs e) throws Exception {
    }

    //Can't really use this, because it's only loaded once at the very beginning of OD starting up.
    private void formEHR_Shown(Object sender, EventArgs e) throws Exception {
        ResultOnClosing = EhrFormResult.None;
        PatCur = Patients.getPat(PatNum);
        ProvPat = Providers.getProv(PatCur.PriProv);
        labelProvPat.Text = ProvPat.getLongDesc();
        if (StringSupport.equals(ProvPat.EhrKey, ""))
        {
            labelProvPat.Text += " (no ehr provider key entered)";
        }
         
        if (Security.getCurUser().ProvNum == 0)
        {
            labelProvUser.Text = "none";
        }
        else
        {
            Provider provUser = Providers.getProv(Security.getCurUser().ProvNum);
            labelProvUser.Text = Providers.getLongDesc(provUser.ProvNum);
            if (StringSupport.equals(provUser.EhrKey, ""))
            {
                labelProvUser.Text += " (no ehr provider key entered)";
            }
             
        } 
        fillGridMu();
        if (OnShowLaunchOrders)
        {
            //LaunchOrdersWindow();
            OnShowLaunchOrders = false;
        }
         
        if (StringSupport.equals(ProvPat.EhrKey, ""))
        {
            MessageBox.Show("No ehr provider key entered for this patient's primary provider.");
        }
         
    }

    private void fillGridMu() throws Exception {
        gridMu.beginUpdate();
        gridMu.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("MeasureType",145);
        gridMu.getColumns().add(col);
        col = new ODGridColumn("Met", 35, HorizontalAlignment.Center);
        gridMu.getColumns().add(col);
        col = new ODGridColumn("Details",170);
        gridMu.getColumns().add(col);
        col = new ODGridColumn("Click to Take Action",168);
        gridMu.getColumns().add(col);
        col = new ODGridColumn("Related Actions",142);
        gridMu.getColumns().add(col);
        if (StringSupport.equals(ProvPat.EhrKey, ""))
        {
            listMu = new List<EhrMu>();
        }
        else
        {
            if (PrefC.getBool(PrefName.MeaningfulUseTwo))
            {
                gridMu.setTitle("Stage 2 Meaningful Use for this patient");
                listMu = EhrMeasures.getMu2(PatCur);
            }
            else
            {
                gridMu.setTitle("Stage 1 Meaningful Use for this patient");
                listMu = EhrMeasures.getMu(PatCur);
            } 
        } 
        gridMu.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listMu.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listMu[i].MeasureType.ToString());
            if (listMu[i].Met == MuMet.True)
            {
                row.getCells().add("X");
                row.setColorBackG(Color.FromArgb(178, 255, 178));
            }
            else if (listMu[i].Met == MuMet.NA)
            {
                row.getCells().add("N/A");
                row.setColorBackG(Color.FromArgb(178, 255, 178));
            }
            else
            {
                row.getCells().add("");
            }  
            row.getCells().Add(listMu[i].Details);
            row.getCells().Add(listMu[i].Action);
            row.getCells().Add(listMu[i].Action2);
            gridMu.getRows().add(row);
        }
        gridMu.endUpdate();
    }

    private void gridMu_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormMedical FormMed;
        if (e.getCol() == 3)
        {
            MeasureType __dummyScrutVar0 = listMu[e.getRow()].MeasureType;
            if (__dummyScrutVar0.equals(EhrMeasureType.ProblemList))
            {
                FormMed = new FormMedical(PatNotCur,PatCur);
                FormMed.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Medical;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.MedicationList))
            {
                FormMed = new FormMedical(PatNotCur,PatCur);
                FormMed.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Medical;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.AllergyList))
            {
                FormMed = new FormMedical(PatNotCur,PatCur);
                FormMed.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Medical;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.Demographics))
            {
                FormPatientEdit FormPatEdit = new FormPatientEdit(PatCur,PatFamCur);
                FormPatEdit.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.PatientEdit;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.Education))
            {
                FormEhrEduResourcesPat FormEDUPat = new FormEhrEduResourcesPat();
                FormEDUPat.patCur = PatCur;
                FormEDUPat.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.TimelyAccess) || __dummyScrutVar0.equals(EhrMeasureType.ElectronicCopyAccess))
            {
                FormPatientPortal FormPatPort = new FormPatientPortal();
                FormPatPort.PatCur = PatCur;
                FormPatPort.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Online;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.ProvOrderEntry) || __dummyScrutVar0.equals(EhrMeasureType.CPOE_MedOrdersOnly) || __dummyScrutVar0.equals(EhrMeasureType.CPOE_PreviouslyOrdered))
            {
            }
            else //LaunchOrdersWindow();
            if (__dummyScrutVar0.equals(EhrMeasureType.Rx))
            {
            }
            else //no action available
            if (__dummyScrutVar0.equals(EhrMeasureType.VitalSigns) || __dummyScrutVar0.equals(EhrMeasureType.VitalSignsBMIOnly) || __dummyScrutVar0.equals(EhrMeasureType.VitalSignsBPOnly) || __dummyScrutVar0.equals(EhrMeasureType.VitalSigns2014))
            {
                FormVitalsigns FormVital = new FormVitalsigns();
                FormVital.PatNum = PatNum;
                FormVital.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.Smoking))
            {
                FormEhrPatientSmoking FormPS = new FormEhrPatientSmoking();
                FormPS.PatCur = PatCur;
                FormPS.ShowDialog();
                PatCur = Patients.getPat(PatNum);
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.Lab))
            {
                FormEhrLabOrders FormLP = new FormEhrLabOrders();
                FormLP.PatCur = PatCur;
                FormLP.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.ElectronicCopy))
            {
                if (StringSupport.equals(listMu[e.getRow()].Action, "Provide elect copy to Pt"))
                {
                    FormEhrElectronicCopy FormE = new FormEhrElectronicCopy();
                    FormE.PatCur = PatCur;
                    FormE.ShowDialog();
                    fillGridMu();
                }
                 
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.ClinicalSummaries))
            {
                FormEhrClinicalSummary FormCS = new FormEhrClinicalSummary();
                FormCS.PatCur = PatCur;
                FormCS.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.Reminders))
            {
                FormEhrReminders FormRem = new FormEhrReminders();
                FormRem.PatCur = PatCur;
                FormRem.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.MedReconcile))
            {
                FormEhrSummaryOfCare FormMedRec = new FormEhrSummaryOfCare();
                FormMedRec.PatCur = PatCur;
                FormMedRec.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.MedReconcile;
            //Close();
            if (__dummyScrutVar0.equals(EhrMeasureType.SummaryOfCare))
            {
                FormEhrSummaryOfCare FormSoC = new FormEhrSummaryOfCare();
                FormSoC.PatCur = PatCur;
                FormSoC.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.SummaryOfCareElectronic))
            {
                FormEhrSummaryOfCare FormSoCE = new FormEhrSummaryOfCare();
                FormSoCE.PatCur = PatCur;
                FormSoCE.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.SecureMessaging))
            {
            }
            else //Patient Sent
            if (__dummyScrutVar0.equals(EhrMeasureType.FamilyHistory))
            {
                FormMed = new FormMedical(PatNotCur,PatCur);
                FormMed.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.ElectronicNote))
            {
            }
            else //Sign a Note
            if (__dummyScrutVar0.equals(EhrMeasureType.CPOE_LabOrdersOnly))
            {
                FormEhrLabOrders FormLab = new FormEhrLabOrders();
                FormLab.PatCur = PatCur;
                FormLab.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.CPOE_RadiologyOrdersOnly))
            {
                FormEhrLabOrders FormRad = new FormEhrLabOrders();
                FormRad.PatCur = PatCur;
                FormRad.ShowDialog();
                fillGridMu();
            }
            else if (__dummyScrutVar0.equals(EhrMeasureType.LabImages))
            {
                FormEhrLabOrders FormLO = new FormEhrLabOrders();
                FormLO.PatCur = PatCur;
                FormLO.ShowDialog();
                fillGridMu();
            }
                                   
        }
         
        if (e.getCol() == 4)
        {
            MeasureType __dummyScrutVar1 = listMu[e.getRow()].MeasureType;
            if (__dummyScrutVar1.equals(EhrMeasureType.MedReconcile))
            {
                FormReferralsPatient FormRefMed = new FormReferralsPatient();
                FormRefMed.PatNum = PatCur.PatNum;
                FormRefMed.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Referrals;
            //Close();
            if (__dummyScrutVar1.equals(EhrMeasureType.SummaryOfCare) || __dummyScrutVar1.equals(EhrMeasureType.SummaryOfCareElectronic))
            {
                FormReferralsPatient FormRefSum = new FormReferralsPatient();
                FormRefSum.PatNum = PatCur.PatNum;
                FormRefSum.ShowDialog();
                fillGridMu();
            }
            else //ResultOnClosing=EhrFormResult.Referrals;
            //Close();
            if (__dummyScrutVar1.equals(EhrMeasureType.Lab))
            {
            }
               
        }
         
    }

    //Redundant now that everything is done from one window
    /**
    * This can happen when clicking in the grid, or when the form is Shown.  The latter would happen after user unknowingly exited ehr in order to use FormMedPat.  Popping back to the Orders window makes the experience seamless.  This can be recursive if the user edits a series of medicationpats.
    */
    private void launchOrdersWindow() throws Exception {
        FormEhrMedicalOrders FormOrd = new FormEhrMedicalOrders();
        FormOrd._patCur = PatCur;
        FormOrd.ShowDialog();
        //if(FormOrd.DialogResult!=DialogResult.OK) {//There is no ok button
        //	return;
        //}
        /*not currently used, but might be if we let users generate Rx from med order.
        			if(FormOrd.LaunchRx) {
        				if(FormOrd.LaunchRxNum==0) {
        					ResultOnClosing=EhrFormResult.RxSelect;
        				}
        				else {
        					ResultOnClosing=EhrFormResult.RxEdit;
        					LaunchRxNum=FormOrd.LaunchRxNum;
        				}
        				Close();
        			}
        			else*/
        if (FormOrd.LaunchMedicationPat)
        {
            //if(FormOrd.LaunchMedicationPatNum==0) {
            //	ResultOnClosing=EhrFormResult.MedicationPatNew;//This cannot happen unless a provider is logged in with a valid ehr key
            //}
            //else {
            FormMedPat FormMP = new FormMedPat();
            FormMP.MedicationPatCur = MedicationPats.getOne(FormOrd.LaunchMedicationPatNum);
            FormMP.ShowDialog();
            //ResultOnClosing=EhrFormResult.MedicationPatEdit;
            //LaunchMedicationPatNum=FormOrd.LaunchMedicationPatNum;
            //}
            //Close();
            //}
            //else {
            fillGridMu();
        }
         
    }

    private void butMeasures_Click(Object sender, EventArgs e) throws Exception {
        if (ObjFormEhrMeasures == null)
        {
            return ;
        }
         
        Type type = AssemblyEHR.GetType("EHR.FormEhrMeasures");
        //namespace.class
        //type.InvokeMember("ShowDialog",System.Reflection.BindingFlags.InvokeMethod,null,ObjFormEhrMeasures,null);
        Form FormEM = (Form)type.InvokeMember("FormEhrMeasures", System.Reflection.BindingFlags.CreateInstance, null, ObjFormEhrMeasures, null);
        //AssemblyEHR.GetModule("FormEhrMeasures");
        FormEM.ShowDialog();
        long patNum = 0;
        try
        {
            patNum = (long)type.InvokeMember("SelectedPatNum", System.Reflection.BindingFlags.GetProperty, null, FormEM, null);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        if (FormEM.DialogResult == DialogResult.OK && patNum != 0)
        {
            PatNum = patNum;
            ResultOnClosing = EhrFormResult.PatientSelect;
            DialogResult = DialogResult.OK;
            Close();
            return ;
        }
         
        //long patNum;
        fillGridMu();
    }

    private void butHash_Click(Object sender, EventArgs e) throws Exception {
        FormEhrHash FormH = new FormEhrHash();
        FormH.ShowDialog();
    }

    private void butEncryption_Click(Object sender, EventArgs e) throws Exception {
        FormEhrEncryption FormE = new FormEhrEncryption();
        FormE.ShowDialog();
    }

    private void butQuality_Click(Object sender, EventArgs e) throws Exception {
        FormEhrQualityMeasures FormQ = new FormEhrQualityMeasures();
        FormQ.ShowDialog();
        fillGridMu();
    }

    private void but2014CQM_Click(Object sender, EventArgs e) throws Exception {
        FormEhrQualityMeasures2014 FormQ = new FormEhrQualityMeasures2014();
        FormQ.ShowDialog();
        if (FormQ.DialogResult == DialogResult.OK && FormQ.selectedPatNum != 0)
        {
            PatNum = FormQ.selectedPatNum;
            ResultOnClosing = EhrFormResult.PatientSelect;
            DialogResult = DialogResult.OK;
            Close();
            return ;
        }
         
        fillGridMu();
    }

    private void butVaccines_Click(Object sender, EventArgs e) throws Exception {
        FormEhrVaccines FormVac = new FormEhrVaccines();
        FormVac.PatCur = PatCur;
        FormVac.ShowDialog();
    }

    private void butPatList_Click(Object sender, EventArgs e) throws Exception {
        FormEhrPatList FormPL = new FormEhrPatList();
        FormPL.ElementList = new List<EhrPatListElement>();
        FormPL.ShowDialog();
    }

    private void butPatList14_Click(Object sender, EventArgs e) throws Exception {
        MessageBox.Show("This form was moved to the OpenDental project and should be launched from Reports ?");
    }

    //OpenDental.FormPatList2014 FormPL14=new OpenDental.FormPatList2014();
    //FormPL14.ShowDialog();
    private void butLabPanelLOINC_Click(Object sender, EventArgs e) throws Exception {
        //TODO: Add this to the Grid instead of the button
        FormEhrLabOrders FormEHRLO = new FormEhrLabOrders();
        FormEHRLO.PatCur = PatCur;
        FormEHRLO.ShowDialog();
    }

    private void butAmendments_Click(Object sender, EventArgs e) throws Exception {
        FormEhrAmendments FormAmd = new FormEhrAmendments();
        FormAmd.PatCur = PatCur;
        FormAmd.ShowDialog();
    }

    private void butEhrNotPerformed_Click(Object sender, EventArgs e) throws Exception {
        FormEhrNotPerformed FormNP = new FormEhrNotPerformed();
        FormNP.PatCur = PatCur;
        FormNP.ShowDialog();
    }

    private void butEncounters_Click(Object sender, EventArgs e) throws Exception {
        FormEncounters FormEnc = new FormEncounters();
        FormEnc.PatCur = PatCur;
        FormEnc.ShowDialog();
    }

    private void butInterventions_Click(Object sender, EventArgs e) throws Exception {
        FormInterventions FormInt = new FormInterventions();
        FormInt.PatCur = PatCur;
        FormInt.ShowDialog();
    }

    private void butCarePlans_Click(Object sender, EventArgs e) throws Exception {
        FormEhrCarePlans FormCP = new FormEhrCarePlans(PatCur);
        FormCP.ShowDialog();
    }

    private void but2011Labs_Click(Object sender, EventArgs e) throws Exception {
        launchOrdersWindow();
    }

    public static boolean provKeyIsValid(String lName, String fName, boolean hasReportAccess, String provKey) throws Exception {
        try
        {
            constructObjFormEhrMeasuresHelper();
            Type type = AssemblyEHR.GetType("EHR.FormEhrMeasures");
            //namespace.class
            Object[] args = new Object[]{ lName, fName, hasReportAccess, provKey };
            return (boolean)type.InvokeMember("ProvKeyIsValid", System.Reflection.BindingFlags.InvokeMethod, null, ObjFormEhrMeasures, args);
        }
        catch (Exception __dummyCatchVar1)
        {
            return false;
        }
    
    }

    public static boolean quarterlyKeyIsValid(String year, String quarter, String practiceTitle, String qkey) throws Exception {
        try
        {
            constructObjFormEhrMeasuresHelper();
            Type type = AssemblyEHR.GetType("EHR.FormEhrMeasures");
            //namespace.class
            Object[] args = new Object[]{ year, quarter, practiceTitle, qkey };
            return (boolean)type.InvokeMember("QuarterlyKeyIsValid", System.Reflection.BindingFlags.InvokeMethod, null, ObjFormEhrMeasures, args);
        }
        catch (Exception __dummyCatchVar2)
        {
            return false;
        }
    
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEHR.class);
        this.butClose = new System.Windows.Forms.Button();
        this.butMeasures = new System.Windows.Forms.Button();
        this.butHash = new System.Windows.Forms.Button();
        this.butEncryption = new System.Windows.Forms.Button();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.but2014CQM = new System.Windows.Forms.Button();
        this.butEhrNotPerformed = new System.Windows.Forms.Button();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butVaccines = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.labelProvPat = new System.Windows.Forms.Label();
        this.labelProvUser = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butAmendments = new System.Windows.Forms.Button();
        this.butEncounters = new System.Windows.Forms.Button();
        this.butInterventions = new System.Windows.Forms.Button();
        this.butCarePlans = new System.Windows.Forms.Button();
        this.but2011Labs = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.gridMu = new OpenDental.UI.ODGrid();
        this.groupBox4.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(713, 638);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(86, 23);
        this.butClose.TabIndex = 9;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butMeasures
        //
        this.butMeasures.Location = new System.Drawing.Point(10, 19);
        this.butMeasures.Name = "butMeasures";
        this.butMeasures.Size = new System.Drawing.Size(84, 23);
        this.butMeasures.TabIndex = 11;
        this.butMeasures.Text = "Measure Calc";
        this.butMeasures.UseVisualStyleBackColor = true;
        this.butMeasures.Click += new System.EventHandler(this.butMeasures_Click);
        //
        // butHash
        //
        this.butHash.Location = new System.Drawing.Point(10, 19);
        this.butHash.Name = "butHash";
        this.butHash.Size = new System.Drawing.Size(84, 23);
        this.butHash.TabIndex = 13;
        this.butHash.Text = "Hash";
        this.butHash.UseVisualStyleBackColor = true;
        this.butHash.Click += new System.EventHandler(this.butHash_Click);
        //
        // butEncryption
        //
        this.butEncryption.Location = new System.Drawing.Point(10, 48);
        this.butEncryption.Name = "butEncryption";
        this.butEncryption.Size = new System.Drawing.Size(84, 23);
        this.butEncryption.TabIndex = 17;
        this.butEncryption.Text = "Encryption";
        this.butEncryption.UseVisualStyleBackColor = true;
        this.butEncryption.Click += new System.EventHandler(this.butEncryption_Click);
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.but2014CQM);
        this.groupBox4.Controls.Add(this.butMeasures);
        this.groupBox4.Location = new System.Drawing.Point(702, 81);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(104, 83);
        this.groupBox4.TabIndex = 25;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "For All Patients";
        //
        // but2014CQM
        //
        this.but2014CQM.Location = new System.Drawing.Point(10, 48);
        this.but2014CQM.Name = "but2014CQM";
        this.but2014CQM.Size = new System.Drawing.Size(84, 23);
        this.but2014CQM.TabIndex = 21;
        this.but2014CQM.Text = "Quality Meas";
        this.but2014CQM.UseVisualStyleBackColor = true;
        this.but2014CQM.Click += new System.EventHandler(this.but2014CQM_Click);
        //
        // butEhrNotPerformed
        //
        this.butEhrNotPerformed.Location = new System.Drawing.Point(712, 317);
        this.butEhrNotPerformed.Name = "butEhrNotPerformed";
        this.butEhrNotPerformed.Size = new System.Drawing.Size(84, 23);
        this.butEhrNotPerformed.TabIndex = 38;
        this.butEhrNotPerformed.Text = "Not Performed";
        this.butEhrNotPerformed.UseVisualStyleBackColor = true;
        this.butEhrNotPerformed.Click += new System.EventHandler(this.butEhrNotPerformed_Click);
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butHash);
        this.groupBox5.Controls.Add(this.butEncryption);
        this.groupBox5.Location = new System.Drawing.Point(702, 170);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(104, 83);
        this.groupBox5.TabIndex = 26;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Tools";
        //
        // butVaccines
        //
        this.butVaccines.Location = new System.Drawing.Point(713, 259);
        this.butVaccines.Name = "butVaccines";
        this.butVaccines.Size = new System.Drawing.Size(84, 23);
        this.butVaccines.TabIndex = 27;
        this.butVaccines.Text = "Vaccines";
        this.butVaccines.UseVisualStyleBackColor = true;
        this.butVaccines.Click += new System.EventHandler(this.butVaccines_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(128, 18);
        this.label1.TabIndex = 28;
        this.label1.Text = "Provider for this patient:";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 30);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(128, 18);
        this.label2.TabIndex = 29;
        this.label2.Text = "Provider logged on:";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelProvPat
        //
        this.labelProvPat.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelProvPat.ForeColor = System.Drawing.Color.DarkRed;
        this.labelProvPat.Location = new System.Drawing.Point(135, 8);
        this.labelProvPat.Name = "labelProvPat";
        this.labelProvPat.Size = new System.Drawing.Size(426, 18);
        this.labelProvPat.TabIndex = 30;
        this.labelProvPat.Text = "Abbr - ProvLName, ProvFName";
        this.labelProvPat.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelProvUser
        //
        this.labelProvUser.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelProvUser.ForeColor = System.Drawing.Color.DarkRed;
        this.labelProvUser.Location = new System.Drawing.Point(135, 29);
        this.labelProvUser.Name = "labelProvUser";
        this.labelProvUser.Size = new System.Drawing.Size(426, 18);
        this.labelProvUser.TabIndex = 31;
        this.labelProvUser.Text = "Abbr - ProvLName, ProvFName";
        this.labelProvUser.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(6, 53);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(688, 32);
        this.label3.TabIndex = 33;
        this.label3.Text = resources.GetString("label3.Text");
        //
        // butAmendments
        //
        this.butAmendments.Location = new System.Drawing.Point(712, 288);
        this.butAmendments.Name = "butAmendments";
        this.butAmendments.Size = new System.Drawing.Size(84, 23);
        this.butAmendments.TabIndex = 36;
        this.butAmendments.Text = "Amendments";
        this.butAmendments.UseVisualStyleBackColor = true;
        this.butAmendments.Click += new System.EventHandler(this.butAmendments_Click);
        //
        // butEncounters
        //
        this.butEncounters.Location = new System.Drawing.Point(712, 346);
        this.butEncounters.Name = "butEncounters";
        this.butEncounters.Size = new System.Drawing.Size(84, 23);
        this.butEncounters.TabIndex = 39;
        this.butEncounters.Text = "Encounters";
        this.butEncounters.UseVisualStyleBackColor = true;
        this.butEncounters.Click += new System.EventHandler(this.butEncounters_Click);
        //
        // butInterventions
        //
        this.butInterventions.Location = new System.Drawing.Point(712, 375);
        this.butInterventions.Name = "butInterventions";
        this.butInterventions.Size = new System.Drawing.Size(84, 23);
        this.butInterventions.TabIndex = 40;
        this.butInterventions.Text = "Interventions";
        this.butInterventions.UseVisualStyleBackColor = true;
        this.butInterventions.Click += new System.EventHandler(this.butInterventions_Click);
        //
        // butCarePlans
        //
        this.butCarePlans.Location = new System.Drawing.Point(712, 404);
        this.butCarePlans.Name = "butCarePlans";
        this.butCarePlans.Size = new System.Drawing.Size(84, 23);
        this.butCarePlans.TabIndex = 42;
        this.butCarePlans.Text = "Care Plans";
        this.butCarePlans.UseVisualStyleBackColor = true;
        this.butCarePlans.Click += new System.EventHandler(this.butCarePlans_Click);
        //
        // but2011Labs
        //
        this.but2011Labs.Location = new System.Drawing.Point(10, 19);
        this.but2011Labs.Name = "but2011Labs";
        this.but2011Labs.Size = new System.Drawing.Size(84, 23);
        this.but2011Labs.TabIndex = 43;
        this.but2011Labs.Text = "Labs/Rads";
        this.but2011Labs.UseVisualStyleBackColor = true;
        this.but2011Labs.Click += new System.EventHandler(this.but2011Labs_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.but2011Labs);
        this.groupBox1.Location = new System.Drawing.Point(702, 495);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(104, 53);
        this.groupBox1.TabIndex = 26;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "2011 Ed.";
        //
        // gridMu
        //
        this.gridMu.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMu.setHScrollVisible(false);
        this.gridMu.Location = new System.Drawing.Point(6, 88);
        this.gridMu.Name = "gridMu";
        this.gridMu.setScrollValue(0);
        this.gridMu.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridMu.Size = new System.Drawing.Size(688, 573);
        this.gridMu.TabIndex = 24;
        this.gridMu.setTitle("Stage 1 Meaningful Use for this patient");
        this.gridMu.setTranslationName(null);
        this.gridMu.CellClick = __MultiODGridClickEventHandler.combine(this.gridMu.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMu_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormEHR
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(817, 674);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butCarePlans);
        this.Controls.Add(this.butInterventions);
        this.Controls.Add(this.butEncounters);
        this.Controls.Add(this.butEhrNotPerformed);
        this.Controls.Add(this.butAmendments);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.labelProvUser);
        this.Controls.Add(this.labelProvPat);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butVaccines);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.gridMu);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEHR";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EHR";
        this.Load += new System.EventHandler(this.FormEHR_Load);
        this.Shown += new System.EventHandler(this.FormEHR_Shown);
        this.groupBox4.ResumeLayout(false);
        this.groupBox5.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butMeasures = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butHash = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butEncryption = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMu;
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butVaccines = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelProvPat = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelProvUser = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butAmendments = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butEhrNotPerformed = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button but2014CQM = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butEncounters = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butInterventions = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCarePlans = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button but2011Labs = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
}


