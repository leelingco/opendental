//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEhrMeasureEventEdit;
import OpenDental.FormInterventionEdit;
import OpenDental.FormMedPat;
import OpenDental.FormSnomeds;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.EhrCode;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Intervention;
import OpenDentBusiness.InterventionCodeSet;
import OpenDentBusiness.Interventions;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.SmokingSnoMed;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrPatientSmoking;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrPatientSmoking  extends Form 
{

    public Patient PatCur;
    /**
    * A copy of the original patient object, as it was when this form was first opened.
    */
    private Patient PatOld;
    private List<EhrMeasureEvent> _ListEvents = new List<EhrMeasureEvent>();
    private List<Intervention> _ListInterventions = new List<Intervention>();
    private List<EhrCode> _ListAssessmentCodes = new List<EhrCode>();
    private List<MedicationPat> _ListMedPats = new List<MedicationPat>();
    private String _TobaccoCodeSelected = new String();
    public FormEhrPatientSmoking() throws Exception {
        initializeComponent();
    }

    private void formPatientSmoking_Load(Object sender, EventArgs e) throws Exception {
        PatOld = PatCur.copy();
        _TobaccoCodeSelected = PatCur.SmokingSnoMed;
        textDateAssessed.Text = DateTime.Now.ToString();
        comboSmokeStatus.Items.Add("None");
        for (int i = 0;i < Enum.GetNames(SmokingSnoMed.class).Length;i++)
        {
            //First and default index
            //Smoking statuses add in the same order as they appear in the SmokingSnoMed enum (Starting at comboSmokeStatus index 1). Changes to the enum order will change the order added so they will always match
            //if snomed code exists in the snomed table, use the code - description for the combo box, otherwise use the original abbreviated description
            Snomed smokeCur = Snomeds.GetByCode((SmokingSnoMed.values()[i]).ToString().Substring(1));
            if (smokeCur != null)
            {
                comboSmokeStatus.Items.Add(smokeCur.Description);
            }
            else
            {
                switch(SmokingSnoMed.values()[i])
                {
                    case _266927001: 
                        comboSmokeStatus.Items.Add("UnknownIfEver");
                        break;
                    case _77176002: 
                        comboSmokeStatus.Items.Add("SmokerUnknownCurrent");
                        break;
                    case _266919005: 
                        comboSmokeStatus.Items.Add("NeverSmoked");
                        break;
                    case _8517006: 
                        comboSmokeStatus.Items.Add("FormerSmoker");
                        break;
                    case _428041000124106: 
                        comboSmokeStatus.Items.Add("CurrentSomeDay");
                        break;
                    case _449868002: 
                        comboSmokeStatus.Items.Add("CurrentEveryDay");
                        break;
                    case _428061000124105: 
                        comboSmokeStatus.Items.Add("LightSmoker");
                        break;
                    case _428071000124103: 
                        comboSmokeStatus.Items.Add("HeavySmoker");
                        break;
                
                }
            } 
        }
        comboSmokeStatus.SelectedIndex = 0;
        try
        {
            //None
            comboSmokeStatus.SelectedIndex = (int)Enum.Parse(SmokingSnoMed.class, "_" + _TobaccoCodeSelected, true) + 1;
        }
        catch (Exception __dummyCatchVar0)
        {
            //if not one of the statuses in the enum, get the Snomed object from the patient's current smoking snomed code
            Snomed smokeCur = Snomeds.getByCode(_TobaccoCodeSelected);
            if (smokeCur != null)
            {
                //valid snomed code, set the combo box text to this snomed description
                comboSmokeStatus.SelectedIndex = -1;
                comboSmokeStatus.Text = smokeCur.Description;
            }
             
        }

        //This takes a while the first time the window loads due to Code Systems.
        Cursor = Cursors.WaitCursor;
        fillGrid();
        Cursor = Cursors.Default;
        _ListAssessmentCodes = EhrCodes.GetForValueSetOIDs(new List<String>{ "2.16.840.1.113883.3.526.3.1278" }, true);
        //'Tobacco Use Screening' value set
        if (_ListAssessmentCodes.Count == 0)
        {
            //This should only happen if the EHR.dll does not exist or if the codes in the ehrcode list do not exist in the corresponding table
            MsgBox.show(this,"The codes used for Tobacco Use Screening assessments do not exist in the LOINC table in your database.  You must run the Code System Importer tool in Setup | EHR to import this code set.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        EhrMeasureEvent mostRecentAssessment = new EhrMeasureEvent();
        for (int i = _ListEvents.Count - 1;i > -1;i--)
        {
            if (_ListEvents[i].EventType == EhrMeasureEventType.TobaccoUseAssessed)
            {
                mostRecentAssessment = _ListEvents[i];
                break;
            }
             
        }
        for (int i = 0;i < _ListAssessmentCodes.Count;i++)
        {
            //_ListEvents filled ordered by DateTEvent, most recent assessment is last one in the list of type assessed
            comboAssessmentType.Items.Add(_ListAssessmentCodes[i].Description);
            if (i == 0)
            {
                comboAssessmentType.SelectedIndex = i;
            }
             
            //default to the first one in the list, 'History of tobacco use Narrative'
            if (StringSupport.equals(mostRecentAssessment.CodeValueEvent, _ListAssessmentCodes[i].CodeValue) && StringSupport.equals(mostRecentAssessment.CodeSystemEvent, _ListAssessmentCodes[i].CodeSystem))
            {
                comboAssessmentType.SelectedIndex = i;
            }
             
        }
    }

    //set to most recent assessment
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Type",170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Documentation",170);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        List<OpenDental.UI.ODGridRow> listRows = new List<OpenDental.UI.ODGridRow>();
        _ListEvents = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.TobaccoUseAssessed);
        for (int i = 0;i < _ListEvents.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(_ListEvents[i].DateTEvent.ToShortDateString());
            Loinc lCur = Loincs.GetByCode(_ListEvents[i].CodeValueEvent);
            //TobaccoUseAssessed events can be one of three types, all LOINC codes
            if (lCur != null)
            {
                row.getCells().add(lCur.NameLongCommon);
            }
            else
            {
                row.getCells().Add(_ListEvents[i].EventType.ToString());
            } 
            Snomed sCur = Snomeds.GetByCode(_ListEvents[i].CodeValueResult);
            if (sCur != null)
            {
                row.getCells().add(sCur.Description);
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(_ListEvents[i].MoreInfo);
            row.setTag(_ListEvents[i]);
            listRows.Add(row);
        }
        _ListInterventions = Interventions.refresh(PatCur.PatNum,InterventionCodeSet.TobaccoCessation);
        for (int i = 0;i < _ListInterventions.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(_ListInterventions[i].DateEntry.ToShortDateString());
            row.getCells().Add(InterventionCodeSet.TobaccoCessation.ToString() + " Counseling");
            String descript = "";
            CodeSystem __dummyScrutVar1 = _ListInterventions[i].CodeSystem;
            if (__dummyScrutVar1.equals("CPT"))
            {
                Cpt cptCur = Cpts.GetByCode(_ListInterventions[i].CodeValue);
                if (cptCur != null)
                {
                    descript = cptCur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(_ListInterventions[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
              
            row.getCells().add(descript);
            row.getCells().Add(_ListInterventions[i].Note);
            row.setTag(_ListInterventions[i]);
            listRows.Add(row);
        }
        _ListMedPats = MedicationPats.refresh(PatCur.PatNum,true);
        List<EhrCode> listEhrMeds = EhrCodes.GetForValueSetOIDs(new List<String>{ "2.16.840.1.113883.3.526.3.1190" }, true);
        for (int i = _ListMedPats.Count - 1;i > -1;i--)
        {
            //Tobacco Use Cessation Pharmacotherapy Value Set
            //listEhrMeds will contain 41 medications for tobacco cessation if those exist in the rxnorm table
            boolean found = false;
            for (int j = 0;j < listEhrMeds.Count;j++)
            {
                if (_ListMedPats[i].RxCui.ToString() == listEhrMeds[j].CodeValue)
                {
                    found = true;
                    break;
                }
                 
            }
            if (!found)
            {
                _ListMedPats.RemoveAt(i);
            }
             
        }
        for (int i = 0;i < _ListMedPats.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            String dateRange = "";
            if (_ListMedPats[i].DateStart.Year > 1880)
            {
                dateRange = _ListMedPats[i].DateStart.ToShortDateString();
            }
             
            if (_ListMedPats[i].DateStop.Year > 1880)
            {
                if (!StringSupport.equals(dateRange, ""))
                {
                    dateRange += " - ";
                }
                 
                dateRange += _ListMedPats[i].DateStop.ToShortDateString();
            }
             
            if (StringSupport.equals(dateRange, ""))
            {
                dateRange = _ListMedPats[i].DateTStamp.ToShortDateString();
            }
             
            row.getCells().add(dateRange);
            row.getCells().Add(InterventionCodeSet.TobaccoCessation.ToString() + " Medication");
            String medDescript = RxNorms.GetDescByRxCui(_ListMedPats[i].RxCui.ToString());
            row.getCells().add(medDescript);
            row.getCells().Add(_ListMedPats[i].PatNote);
            row.setTag(_ListMedPats[i]);
            listRows.Add(row);
        }
        listRows.Sort(SortDate);
        for (int i = 0;i < listRows.Count;i++)
        {
            gridMain.getRows().Add(listRows[i]);
        }
        gridMain.endUpdate();
    }

    /**
    * Sort function to order grid of ehrmeasureevents, interventions, and medicationpats by date, then alphabetically by type name.
    */
    private int sortDate(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        int diff = PIn.Date(row1.getCells()[0].Text).Date.CompareTo(PIn.Date(row2.getCells()[0].Text).Date);
        if (diff != 0)
        {
            return diff;
        }
         
        return row1.getTag().GetType().Name.CompareTo(row2.getTag().GetType().Name);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Object objCur = gridMain.getRows().get___idx(e.getRow()).getTag();
        //gridMain is filled with assessments, interventions, and/or medications
        if (StringSupport.equals(objCur.GetType().Name, "EhrMeasureEvent"))
        {
            //if assessment, we will allow them to change the DateTEvent, but not the status or more info box
            FormEhrMeasureEventEdit FormM = new FormEhrMeasureEventEdit();
            FormM.MeasCur = (EhrMeasureEvent)objCur;
            FormM.ShowDialog();
        }
         
        if (StringSupport.equals(objCur.GetType().Name, "Intervention"))
        {
            FormInterventionEdit FormI = new FormInterventionEdit();
            FormI.InterventionCur = (Intervention)objCur;
            FormI.IsAllTypes = false;
            FormI.IsSelectionMode = false;
            FormI.ShowDialog();
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

    private void comboSmokeStatus_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboSmokeStatus.SelectedIndex <= 0)
        {
            return ;
        }
         
        //If None or text set to other selected Snomed code so -1, do not create an event
        //SelectedIndex guaranteed to be greater than 0
        _TobaccoCodeSelected = ((SmokingSnoMed)comboSmokeStatus.SelectedIndex - 1).ToString().Substring(1);
        DateTime dateTEntered = PIn.DateT(textDateAssessed.Text);
        for (int i = 0;i < _ListEvents.Count;i++)
        {
            //will be set to DateTime.Now when form loads
            //Automatically make an entry
            if (_ListEvents[i].DateTEvent.Date == dateTEntered.Date)
            {
                return ;
            }
             
        }
        //if assessment already exists for date set, do not auto insert
        //an entry for the date entered does not yet exist
        EhrMeasureEvent meas = new EhrMeasureEvent();
        meas.DateTEvent = dateTEntered;
        meas.EventType = EhrMeasureEventType.TobaccoUseAssessed;
        meas.PatNum = PatCur.PatNum;
        meas.CodeValueEvent = _ListAssessmentCodes[comboAssessmentType.SelectedIndex].CodeValue;
        meas.CodeSystemEvent = _ListAssessmentCodes[comboAssessmentType.SelectedIndex].CodeSystem;
        meas.CodeValueResult = _TobaccoCodeSelected;
        meas.CodeSystemResult = "SNOMEDCT";
        //only allow SNOMEDCT codes for now.
        meas.MoreInfo = "";
        EhrMeasureEvents.insert(meas);
        fillGrid();
    }

    private void butAssessed_Click(Object sender, EventArgs e) throws Exception {
        if (comboSmokeStatus.SelectedIndex == 0)
        {
            //None
            MessageBox.Show("You must select a smoking status.");
            return ;
        }
         
        DateTime dateTEntered = PIn.DateT(textDateAssessed.Text);
        for (int i = 0;i < _ListEvents.Count;i++)
        {
            if (_ListEvents[i].DateTEvent.Date == dateTEntered.Date)
            {
                MessageBox.Show("A Tobacco Assessment entry already exists with the selected date.");
                return ;
            }
             
        }
        EhrMeasureEvent meas = new EhrMeasureEvent();
        meas.DateTEvent = dateTEntered;
        meas.EventType = EhrMeasureEventType.TobaccoUseAssessed;
        meas.PatNum = PatCur.PatNum;
        meas.CodeValueEvent = _ListAssessmentCodes[comboAssessmentType.SelectedIndex].CodeValue;
        meas.CodeSystemEvent = _ListAssessmentCodes[comboAssessmentType.SelectedIndex].CodeSystem;
        meas.CodeValueResult = _TobaccoCodeSelected;
        meas.CodeSystemResult = "SNOMEDCT";
        //only allow SNOMEDCT codes for now.
        meas.MoreInfo = "";
        EhrMeasureEvents.insert(meas);
        fillGrid();
    }

    private void butIntervention_Click(Object sender, EventArgs e) throws Exception {
        if (comboSmokeStatus.SelectedIndex == 0)
        {
            //None
            MessageBox.Show("You must select a smoking status.");
            return ;
        }
         
        FormInterventionEdit FormInt = new FormInterventionEdit();
        FormInt.InterventionCur = new Intervention();
        FormInt.InterventionCur.setIsNew(true);
        FormInt.InterventionCur.PatNum = PatCur.PatNum;
        FormInt.InterventionCur.ProvNum = PatCur.PriProv;
        FormInt.InterventionCur.DateEntry = PIn.Date(textDateAssessed.Text);
        FormInt.InterventionCur.CodeSet = InterventionCodeSet.TobaccoCessation;
        FormInt.IsAllTypes = false;
        FormInt.IsSelectionMode = true;
        FormInt.ShowDialog();
        if (FormInt.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void butSnomed_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"Selecting a code that is not in the recommended list of codes may make it more difficult to meet Meaningful Use and CQM's.");
        FormSnomeds FormS = new FormSnomeds();
        FormS.IsSelectionMode = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < Enum.GetNames(SmokingSnoMed.class).Length;i++)
        {
            if (StringSupport.equals(FormS.SelectedSnomed.SnomedCode, (SmokingSnoMed.values()[i]).ToString().Substring(1)))
            {
                comboSmokeStatus.SelectedIndex = i + 1;
                _TobaccoCodeSelected = (SmokingSnoMed.values()[i]).ToString().Substring(1);
                return ;
            }
             
        }
        _TobaccoCodeSelected = FormS.SelectedSnomed.SnomedCode;
        comboSmokeStatus.SelectedIndex = -1;
        comboSmokeStatus.Text = FormS.SelectedSnomed.Description;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (comboSmokeStatus.SelectedIndex == 0)
        {
            //None
            PatCur.SmokingSnoMed = "";
        }
        else
        {
            PatCur.SmokingSnoMed = _TobaccoCodeSelected;
        } 
        Patients.update(PatCur,PatOld);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrPatientSmoking.class);
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.comboSmokeStatus = new System.Windows.Forms.ComboBox();
        this.butAssessed = new System.Windows.Forms.Button();
        this.butIntervention = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.comboAssessmentType = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.butSnomed = new System.Windows.Forms.Button();
        this.textDateAssessed = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(615, 412);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 8;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(534, 412);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 9;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 71);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 11;
        this.label2.Text = "Smoking Status";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSmokeStatus
        //
        this.comboSmokeStatus.FormattingEnabled = true;
        this.comboSmokeStatus.Location = new System.Drawing.Point(117, 71);
        this.comboSmokeStatus.MaxDropDownItems = 30;
        this.comboSmokeStatus.Name = "comboSmokeStatus";
        this.comboSmokeStatus.Size = new System.Drawing.Size(260, 21);
        this.comboSmokeStatus.TabIndex = 12;
        this.comboSmokeStatus.SelectionChangeCommitted += new System.EventHandler(this.comboSmokeStatus_SelectionChangeCommitted);
        //
        // butAssessed
        //
        this.butAssessed.Location = new System.Drawing.Point(117, 107);
        this.butAssessed.Name = "butAssessed";
        this.butAssessed.Size = new System.Drawing.Size(75, 23);
        this.butAssessed.TabIndex = 17;
        this.butAssessed.Text = "Assessed";
        this.butAssessed.UseVisualStyleBackColor = true;
        this.butAssessed.Click += new System.EventHandler(this.butAssessed_Click);
        //
        // butIntervention
        //
        this.butIntervention.Location = new System.Drawing.Point(198, 107);
        this.butIntervention.Name = "butIntervention";
        this.butIntervention.Size = new System.Drawing.Size(75, 23);
        this.butIntervention.TabIndex = 20;
        this.butIntervention.Text = "Intervention";
        this.butIntervention.UseVisualStyleBackColor = true;
        this.butIntervention.Click += new System.EventHandler(this.butIntervention_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(413, 67);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(209, 30);
        this.label1.TabIndex = 24;
        this.label1.Text = "The patient\'s current smoking status.  Used for calculating MU measures.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(279, 109);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(411, 18);
        this.label4.TabIndex = 25;
        this.label4.Text = "The history is used for calculating Tabacco Assessment and Cessation CQMs";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // comboAssessmentType
        //
        this.comboAssessmentType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAssessmentType.DropDownWidth = 350;
        this.comboAssessmentType.FormattingEnabled = true;
        this.comboAssessmentType.Location = new System.Drawing.Point(117, 44);
        this.comboAssessmentType.Name = "comboAssessmentType";
        this.comboAssessmentType.Size = new System.Drawing.Size(260, 21);
        this.comboAssessmentType.TabIndex = 27;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(13, 44);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 17);
        this.label5.TabIndex = 26;
        this.label5.Text = "Assessment Type";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSnomed
        //
        this.butSnomed.Location = new System.Drawing.Point(383, 71);
        this.butSnomed.Name = "butSnomed";
        this.butSnomed.Size = new System.Drawing.Size(24, 21);
        this.butSnomed.TabIndex = 28;
        this.butSnomed.Text = "...";
        this.butSnomed.UseVisualStyleBackColor = true;
        this.butSnomed.Click += new System.EventHandler(this.butSnomed_Click);
        //
        // textDateAssessed
        //
        this.textDateAssessed.Location = new System.Drawing.Point(117, 18);
        this.textDateAssessed.Name = "textDateAssessed";
        this.textDateAssessed.ReadOnly = true;
        this.textDateAssessed.Size = new System.Drawing.Size(140, 20);
        this.textDateAssessed.TabIndex = 29;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(13, 18);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(100, 17);
        this.label6.TabIndex = 30;
        this.label6.Text = "Date Assessed";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = System.Windows.Forms.AnchorStyles.None;
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(117, 136);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(573, 263);
        this.gridMain.TabIndex = 19;
        this.gridMain.setTitle("History");
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
        // FormEhrPatientSmoking
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(702, 447);
        this.Controls.Add(this.textDateAssessed);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butSnomed);
        this.Controls.Add(this.comboAssessmentType);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butIntervention);
        this.Controls.Add(this.butAssessed);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.comboSmokeStatus);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrPatientSmoking";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Smoking";
        this.Load += new System.EventHandler(this.FormPatientSmoking_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSmokeStatus = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button butAssessed = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butIntervention = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboAssessmentType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butSnomed = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textDateAssessed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
}


