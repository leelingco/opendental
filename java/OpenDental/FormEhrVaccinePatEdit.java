//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormProviderPick;
import OpenDental.FormVaccineObsEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DrugManufacturer;
import OpenDentBusiness.DrugManufacturers;
import OpenDentBusiness.DrugUnits;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;
import OpenDentBusiness.VaccineAction;
import OpenDentBusiness.VaccineAdministrationNote;
import OpenDentBusiness.VaccineAdministrationRoute;
import OpenDentBusiness.VaccineAdministrationSite;
import OpenDentBusiness.VaccineCompletionStatus;
import OpenDentBusiness.VaccineDef;
import OpenDentBusiness.VaccineDefs;
import OpenDentBusiness.VaccineObs;
import OpenDentBusiness.VaccineObses;
import OpenDentBusiness.VaccinePat;
import OpenDentBusiness.VaccinePats;
import OpenDentBusiness.VaccineRefusalReason;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrVaccinePatEdit;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrVaccinePatEdit  extends Form 
{

    public VaccinePat VaccinePatCur;
    public boolean IsNew = new boolean();
    private long _provNumSelectedOrdering = new long();
    private long _provNumSelectedAdministering = new long();
    private List<VaccineObs> _listVaccineObservations = new List<VaccineObs>();
    private List<VaccineObs> _listVaccineObservationGroups = new List<VaccineObs>();
    public FormEhrVaccinePatEdit() throws Exception {
        initializeComponent();
    }

    private void formVaccinePatEdit_Load(Object sender, EventArgs e) throws Exception {
        Patient pat = Patients.getLim(VaccinePatCur.PatNum);
        if (pat.getAge() != 0 && pat.getAge() < 3)
        {
            labelDocument.Text = "Document reason not given below.  Reason can include a contraindication due to a specific allergy, adverse effect, intollerance, or specific disease.";
        }
         
        //less leeway with reasons for kids.
        comboVaccine.Items.Clear();
        for (int i = 0;i < VaccineDefs.getListt().Count;i++)
        {
            comboVaccine.Items.Add(VaccineDefs.getListt()[i].CVXCode + " - " + VaccineDefs.getListt()[i].VaccineName);
            if (VaccineDefs.getListt()[i].VaccineDefNum == VaccinePatCur.VaccineDefNum)
            {
                comboVaccine.SelectedIndex = i;
            }
             
        }
        if (!IsNew && VaccinePatCur.VaccineDefNum != 0)
        {
            VaccineDef vaccineDef = VaccineDefs.getOne(VaccinePatCur.VaccineDefNum);
            //Need vaccine to get manufacturer.
            DrugManufacturer manufacturer = DrugManufacturers.getOne(vaccineDef.DrugManufacturerNum);
            textManufacturer.Text = manufacturer.ManufacturerCode + " - " + manufacturer.ManufacturerName;
        }
         
        if (VaccinePatCur.DateTimeStart.Year > 1880)
        {
            textDateTimeStart.Text = VaccinePatCur.DateTimeStart.ToString();
        }
         
        if (VaccinePatCur.DateTimeEnd.Year > 1880)
        {
            textDateTimeStop.Text = VaccinePatCur.DateTimeEnd.ToString();
        }
         
        if (VaccinePatCur.AdministeredAmt != 0)
        {
            textAmount.Text = VaccinePatCur.AdministeredAmt.ToString();
        }
         
        comboUnits.Items.Clear();
        comboUnits.Items.Add("none");
        comboUnits.SelectedIndex = 0;
        for (int i = 0;i < DrugUnits.getListt().Count;i++)
        {
            comboUnits.Items.Add(DrugUnits.getListt()[i].UnitIdentifier);
            if (DrugUnits.getListt()[i].DrugUnitNum == VaccinePatCur.DrugUnitNum)
            {
                comboUnits.SelectedIndex = i + 1;
            }
             
        }
        textLotNum.Text = VaccinePatCur.LotNumber;
        if (VaccinePatCur.DateExpire.Year > 1880)
        {
            textDateExpiration.Text = VaccinePatCur.DateExpire.ToShortDateString();
        }
         
        listRefusalReason.Items.Clear();
        String[] arrayVaccineRefusalReasons = Enum.GetNames(VaccineRefusalReason.class);
        for (int i = 0;i < arrayVaccineRefusalReasons.Length;i++)
        {
            listRefusalReason.Items.Add(arrayVaccineRefusalReasons[i]);
            VaccineRefusalReason refusalReason = VaccineRefusalReason.values()[i];
            if (refusalReason == VaccinePatCur.RefusalReason)
            {
                listRefusalReason.SelectedIndex = i;
            }
             
        }
        listCompletionStatus.Items.Clear();
        String[] arrayCompletionStatuses = Enum.GetNames(VaccineCompletionStatus.class);
        for (int i = 0;i < arrayCompletionStatuses.Length;i++)
        {
            listCompletionStatus.Items.Add(arrayCompletionStatuses[i]);
            VaccineCompletionStatus completionStatus = VaccineCompletionStatus.values()[i];
            if (completionStatus == VaccinePatCur.CompletionStatus)
            {
                listCompletionStatus.SelectedIndex = i;
            }
             
        }
        textNote.Text = VaccinePatCur.Note;
        if (IsNew)
        {
            if (pat.ClinicNum == 0)
            {
                VaccinePatCur.FilledCity = PrefC.getString(PrefName.PracticeCity);
                VaccinePatCur.FilledST = PrefC.getString(PrefName.PracticeST);
            }
            else
            {
                Clinic clinic = Clinics.getClinic(pat.ClinicNum);
                VaccinePatCur.FilledCity = clinic.City;
                VaccinePatCur.FilledST = clinic.State;
            } 
        }
         
        textFilledCity.Text = VaccinePatCur.FilledCity;
        textFilledSt.Text = VaccinePatCur.FilledST;
        if (IsNew)
        {
            VaccinePatCur.UserNum = Security.getCurUser().UserNum;
        }
         
        Userod user = Userods.getUser(VaccinePatCur.UserNum);
        if (user != null)
        {
            //Will be null for vaccines entered in older versions, before the UserNum column was created.
            textUser.Text = user.UserName;
        }
         
        _provNumSelectedOrdering = VaccinePatCur.ProvNumOrdering;
        comboProvNumOrdering.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNumOrdering.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            //Only visible provs added to combobox.
            if (ProviderC.getListShort()[i].ProvNum == VaccinePatCur.ProvNumOrdering)
            {
                comboProvNumOrdering.SelectedIndex = i;
            }
             
        }
        //Sets combo text too.
        if (comboProvNumOrdering.SelectedIndex == -1)
        {
            //The provider exists but is hidden
            comboProvNumOrdering.Text = Providers.getLongDesc(_provNumSelectedOrdering);
        }
         
        //Appends "(hidden)" to the end of the long description.
        _provNumSelectedAdministering = VaccinePatCur.ProvNumAdminister;
        comboProvNumAdministering.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNumAdministering.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            //Only visible provs added to combobox.
            if (ProviderC.getListShort()[i].ProvNum == VaccinePatCur.ProvNumAdminister)
            {
                comboProvNumAdministering.SelectedIndex = i;
            }
             
        }
        //Sets combo text too.
        if (comboProvNumAdministering.SelectedIndex == -1)
        {
            //The provider exists but is hidden
            comboProvNumAdministering.Text = Providers.getLongDesc(_provNumSelectedAdministering);
        }
         
        //Appends "(hidden)" to the end of the long description.
        comboAdministrationRoute.Items.Clear();
        String[] arrayVaccineAdministrationRoutes = Enum.GetNames(VaccineAdministrationRoute.class);
        for (int i = 0;i < arrayVaccineAdministrationRoutes.Length;i++)
        {
            comboAdministrationRoute.Items.Add(arrayVaccineAdministrationRoutes[i]);
            VaccineAdministrationRoute administrationRoute = VaccineAdministrationRoute.values()[i];
            if (administrationRoute == VaccinePatCur.AdministrationRoute)
            {
                comboAdministrationRoute.SelectedIndex = i;
            }
             
        }
        comboAdministrationSite.Items.Clear();
        String[] arrayVaccineAdministrationSites = Enum.GetNames(VaccineAdministrationSite.class);
        for (int i = 0;i < arrayVaccineAdministrationSites.Length;i++)
        {
            comboAdministrationSite.Items.Add(arrayVaccineAdministrationSites[i]);
            VaccineAdministrationSite administrationSite = VaccineAdministrationSite.values()[i];
            if (administrationSite == VaccinePatCur.AdministrationSite)
            {
                comboAdministrationSite.SelectedIndex = i;
            }
             
        }
        listAdministrationNote.Items.Clear();
        String[] arrayAdministrationNotes = Enum.GetNames(VaccineAdministrationNote.class);
        for (int i = 0;i < arrayAdministrationNotes.Length;i++)
        {
            listAdministrationNote.Items.Add(arrayAdministrationNotes[i]);
            VaccineAdministrationNote administrationNote = VaccineAdministrationNote.values()[i];
            if (administrationNote == VaccinePatCur.AdministrationNoteCode)
            {
                listAdministrationNote.SelectedIndex = i;
            }
             
        }
        listAction.Items.Clear();
        String[] arrayVaccineActions = Enum.GetNames(VaccineAction.class);
        for (int i = 0;i < arrayVaccineActions.Length;i++)
        {
            listAction.Items.Add(arrayVaccineActions[i]);
            VaccineAction action = VaccineAction.values()[i];
            if (action == VaccinePatCur.ActionCode)
            {
                listAction.SelectedIndex = i;
            }
             
        }
        _listVaccineObservations = VaccineObses.getForVaccine(VaccinePatCur.VaccinePatNum);
        fillObservations();
    }

    private void fillObservations() throws Exception {
        gridObservations.beginUpdate();
        gridObservations.getColumns().Clear();
        gridObservations.getColumns().add(new ODGridColumn("Question",150));
        gridObservations.getColumns().add(new ODGridColumn("Value",0));
        gridObservations.endUpdate();
        gridObservations.beginUpdate();
        gridObservations.getRows().Clear();
        for (int i = 0;i < _listVaccineObservations.Count;i++)
        {
            VaccineObs vaccineObs = _listVaccineObservations[i];
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.setTag(vaccineObs);
            row.getCells().Add(new OpenDental.UI.ODGridCell(vaccineObs.IdentifyingCode.ToString()));
            row.getCells().Add(new OpenDental.UI.ODGridCell(vaccineObs.ValReported));
            gridObservations.getRows().add(row);
        }
        if (_listVaccineObservationGroups == null)
        {
            _listVaccineObservationGroups = new List<VaccineObs>();
            for (int i = 0;i < _listVaccineObservations.Count;i++)
            {
                VaccineObs vaccineObs = _listVaccineObservations[i];
                if (vaccineObs.VaccineObsNumGroup == 0 || vaccineObs.VaccineObsNumGroup == vaccineObs.VaccineObsNum)
                {
                    _listVaccineObservationGroups.Add(vaccineObs);
                }
                else
                {
                    for (int j = 0;j < _listVaccineObservations.Count;j++)
                    {
                        if (j != i && _listVaccineObservations[j].VaccineObsNum == _listVaccineObservations[i].VaccineObsNumGroup)
                        {
                            _listVaccineObservationGroups.Add(_listVaccineObservations[j]);
                            break;
                        }
                         
                    }
                } 
            }
        }
         
        gridObservations.endUpdate();
    }

    private void comboVaccine_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        DrugManufacturer manufacturer = DrugManufacturers.GetOne(VaccineDefs.getListt()[comboVaccine.SelectedIndex].DrugManufacturerNum);
        textManufacturer.Text = manufacturer.ManufacturerCode + " - " + manufacturer.ManufacturerName;
    }

    private void comboProvNumOrdering_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        _provNumSelectedOrdering = ProviderC.getListShort()[comboProvNumOrdering.SelectedIndex].ProvNum;
    }

    private void butPickProvOrdering_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formP = new FormProviderPick();
        if (comboProvNumOrdering.SelectedIndex > -1)
        {
            //Initial formP selection if selected prov is not hidden.
            formP.SelectedProvNum = _provNumSelectedOrdering;
        }
         
        formP.ShowDialog();
        if (formP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvNumOrdering.SelectedIndex = Providers.getIndex(formP.SelectedProvNum);
        _provNumSelectedOrdering = formP.SelectedProvNum;
    }

    private void butNoneProvOrdering_Click(Object sender, EventArgs e) throws Exception {
        _provNumSelectedOrdering = 0;
        comboProvNumOrdering.SelectedIndex = -1;
    }

    private void comboProvNumAdministering_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        _provNumSelectedAdministering = ProviderC.getListShort()[comboProvNumAdministering.SelectedIndex].ProvNum;
    }

    private void butPickProvAdministering_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formP = new FormProviderPick();
        if (comboProvNumAdministering.SelectedIndex > -1)
        {
            //Initial formP selection if selected prov is not hidden.
            formP.SelectedProvNum = _provNumSelectedAdministering;
        }
         
        formP.ShowDialog();
        if (formP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvNumAdministering.SelectedIndex = Providers.getIndex(formP.SelectedProvNum);
        _provNumSelectedAdministering = formP.SelectedProvNum;
    }

    private void butNoneProvAdministering_Click(Object sender, EventArgs e) throws Exception {
        _provNumSelectedAdministering = 0;
        comboProvNumAdministering.SelectedIndex = -1;
    }

    private void gridObservations_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridObservations.getSelectedIndices().Length > 1)
        {
            return ;
        }
         
        //Do not select group if the user has selected more than one item (otherwise it would deselect some of the rows the user clicked, which would make using the group button impossible).
        //Select all observations which are in the same group.
        VaccineObs vaccineObsGroup = _listVaccineObservationGroups[e.getRow()];
        gridObservations.setSelected(false);
        for (int i = 0;i < _listVaccineObservationGroups.Count;i++)
        {
            //Deselect all.
            if (_listVaccineObservationGroups[i] == vaccineObsGroup)
            {
                gridObservations.setSelected(i,true);
            }
             
        }
    }

    private void gridObservations_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        VaccineObs vaccineObs = (VaccineObs)gridObservations.getRows().get___idx(e.getRow()).getTag();
        FormVaccineObsEdit form = new FormVaccineObsEdit(vaccineObs);
        form.ShowDialog();
        if (vaccineObs.VaccinePatNum == 0)
        {
            //Was deleted
            //If the observation identifying the group is deleted, then we need to reassign a new group.
            List<int> listRegroupIndicies = new List<int>();
            for (int i = 0;i < _listVaccineObservations.Count;i++)
            {
                if (i != e.getRow() && _listVaccineObservationGroups[i] == _listVaccineObservationGroups[e.getRow()])
                {
                    listRegroupIndicies.Add(i);
                }
                 
            }
            if (listRegroupIndicies.Count > 0)
            {
                VaccineObs vaccineObsGroup = _listVaccineObservations[listRegroupIndicies[0]];
                for (int i = 0;i < listRegroupIndicies.Count;i++)
                {
                    _listVaccineObservationGroups[listRegroupIndicies[i]] = vaccineObsGroup;
                }
            }
             
            //Delete the observation and corresponding group reference.
            _listVaccineObservations.RemoveAt(e.getRow());
            _listVaccineObservationGroups.RemoveAt(e.getRow());
        }
         
        fillObservations();
    }

    private void butAddObservation_Click(Object sender, EventArgs e) throws Exception {
        VaccineObs vaccineObs = new VaccineObs();
        vaccineObs.setIsNew(true);
        vaccineObs.VaccinePatNum = -1;
        //Temporary dummy value (cannot be zero). Helps track new observations which have not been deleted.
        FormVaccineObsEdit form = new FormVaccineObsEdit(vaccineObs);
        if (form.ShowDialog() == DialogResult.OK)
        {
            _listVaccineObservations.Add(vaccineObs);
            _listVaccineObservationGroups.Add(vaccineObs);
            //In its own group with a single item initially.
            fillObservations();
        }
         
    }

    private void butGroupObservations_Click(Object sender, EventArgs e) throws Exception {
        if (gridObservations.getSelectedIndices().Length < 2)
        {
            MsgBox.show(this,"Two or more observations must be selected.");
            return ;
        }
         
        VaccineObs vaccineObsGroup = (VaccineObs)gridObservations.getRows()[gridObservations.getSelectedIndices()[0]].Tag;
        for (int i = 0;i < gridObservations.getSelectedIndices().Length;i++)
        {
            _listVaccineObservationGroups[gridObservations.getSelectedIndices()[i]] = vaccineObsGroup;
        }
    }

    private void butUngroupObservations_Click(Object sender, EventArgs e) throws Exception {
        if (gridObservations.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"At least one observation must be selected.");
            return ;
        }
         
        for (int i = 0;i < gridObservations.getSelectedIndices().Length;i++)
        {
            int index = gridObservations.getSelectedIndices()[i];
            _listVaccineObservationGroups[index] = _listVaccineObservations[index];
        }
        //The vaccine is in its own group with a single item.
        gridObservations.setSelected(false);
    }

    //Deselect all.
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (MessageBox.Show("Delete?", "Delete?", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
        {
            return ;
        }
         
        VaccinePats.delete(VaccinePatCur.VaccinePatNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateExpiration.errorProvider1.GetError(textDateExpiration), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        VaccineCompletionStatus vaccineCompletionStatus = (VaccineCompletionStatus)listCompletionStatus.SelectedIndex;
        if (comboVaccine.SelectedIndex == -1 && vaccineCompletionStatus != VaccineCompletionStatus.NotAdministered)
        {
            //When the vaccine is not administered, the CVX code is automatically assumed to be 998 and there is no manufacturer.  Therefore, no vaccine def is needed.
            MessageBox.Show(this, "Please select a vaccine.");
            return ;
        }
         
        if (vaccineCompletionStatus == VaccineCompletionStatus.NotAdministered)
        {
            if (StringSupport.equals(textNote.Text, ""))
            {
                MessageBox.Show(this, "Please enter documentation in the note.");
                return ;
            }
             
            VaccinePatCur.VaccineDefNum = 0;
        }
        else
        {
            //Written for clarity
            VaccinePatCur.VaccineDefNum = VaccineDefs.getListt()[comboVaccine.SelectedIndex].VaccineDefNum;
        } 
        try
        {
            VaccinePatCur.DateTimeStart = PIn.DateT(textDateTimeStart.Text);
            VaccinePatCur.DateTimeEnd = PIn.DateT(textDateTimeStop.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(this, "Please enter start and end times in format DD/MM/YYYY HH:mm AM/PM");
        }

        if (StringSupport.equals(textAmount.Text, ""))
        {
            VaccinePatCur.AdministeredAmt = 0;
        }
        else
        {
            try
            {
                VaccinePatCur.AdministeredAmt = PIn.Float(textAmount.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                MessageBox.Show(this, "Please enter a valid amount.");
            }
        
        } 
        if (comboUnits.SelectedIndex == 0)
        {
            //'none'
            VaccinePatCur.DrugUnitNum = 0;
        }
        else
        {
            VaccinePatCur.DrugUnitNum = DrugUnits.getListt()[comboUnits.SelectedIndex - 1].DrugUnitNum;
        } 
        VaccinePatCur.LotNumber = textLotNum.Text;
        VaccinePatCur.DateExpire = PIn.Date(textDateExpiration.Text);
        VaccinePatCur.RefusalReason = (VaccineRefusalReason)listRefusalReason.SelectedIndex;
        VaccinePatCur.CompletionStatus = (VaccineCompletionStatus)listCompletionStatus.SelectedIndex;
        VaccinePatCur.Note = textNote.Text;
        VaccinePatCur.FilledCity = textFilledCity.Text;
        VaccinePatCur.FilledST = textFilledSt.Text;
        //VaccinePatCur.UserNum;//Was set when loading and cannot be edited by the user.
        VaccinePatCur.ProvNumOrdering = _provNumSelectedOrdering;
        VaccinePatCur.ProvNumAdminister = _provNumSelectedAdministering;
        VaccinePatCur.AdministrationRoute = (VaccineAdministrationRoute)comboAdministrationRoute.SelectedIndex;
        VaccinePatCur.AdministrationSite = (VaccineAdministrationSite)comboAdministrationSite.SelectedIndex;
        VaccinePatCur.AdministrationNoteCode = (VaccineAdministrationNote)listAdministrationNote.SelectedIndex;
        VaccinePatCur.ActionCode = (VaccineAction)listAction.SelectedIndex;
        if (IsNew)
        {
            VaccinePats.insert(VaccinePatCur);
        }
        else
        {
            VaccinePats.update(VaccinePatCur);
        } 
        //We must delete then update/insert the observations after we insert the vaccinepat record, in case the vaccinepat is new.
        VaccineObses.deleteForVaccinePat(VaccinePatCur.VaccinePatNum);
        for (int i = 0;i < _listVaccineObservations.Count;i++)
        {
            VaccineObs vaccineObs = _listVaccineObservations[i];
            vaccineObs.VaccinePatNum = VaccinePatCur.VaccinePatNum;
            VaccineObses.insert(vaccineObs);
        }
        for (int i = 0;i < _listVaccineObservations.Count;i++)
        {
            //Update the vaccine observation group ids, now that the vaccine observation records have been inserted.
            VaccineObs vaccineObs = _listVaccineObservations[i];
            vaccineObs.VaccineObsNumGroup = _listVaccineObservationGroups[i].VaccineObsNum;
            VaccineObses.update(vaccineObs);
        }
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrVaccinePatEdit.class);
        this.label5 = new System.Windows.Forms.Label();
        this.labelAmount = new System.Windows.Forms.Label();
        this.textAmount = new System.Windows.Forms.TextBox();
        this.comboVaccine = new System.Windows.Forms.ComboBox();
        this.labelVaccine = new System.Windows.Forms.Label();
        this.comboUnits = new System.Windows.Forms.ComboBox();
        this.textManufacturer = new System.Windows.Forms.TextBox();
        this.labelDateTimeStartStop = new System.Windows.Forms.Label();
        this.textDateTimeStart = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textLotNum = new System.Windows.Forms.TextBox();
        this.textDateTimeStop = new System.Windows.Forms.TextBox();
        this.labelDateTimeStop = new System.Windows.Forms.Label();
        this.labelDocument = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textFilledCity = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textFilledSt = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.listCompletionStatus = new System.Windows.Forms.ListBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.listAdministrationNote = new System.Windows.Forms.ListBox();
        this.label9 = new System.Windows.Forms.Label();
        this.comboProvNumOrdering = new System.Windows.Forms.ComboBox();
        this.label10 = new System.Windows.Forms.Label();
        this.comboProvNumAdministering = new System.Windows.Forms.ComboBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.listRefusalReason = new System.Windows.Forms.ListBox();
        this.label14 = new System.Windows.Forms.Label();
        this.listAction = new System.Windows.Forms.ListBox();
        this.label15 = new System.Windows.Forms.Label();
        this.comboAdministrationRoute = new System.Windows.Forms.ComboBox();
        this.comboAdministrationSite = new System.Windows.Forms.ComboBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.gridObservations = new OpenDental.UI.ODGrid();
        this.butUngroupObservations = new OpenDental.UI.Button();
        this.butGroupObservations = new OpenDental.UI.Button();
        this.butAddObservation = new OpenDental.UI.Button();
        this.button1 = new OpenDental.UI.Button();
        this.button2 = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butNoneProvAdministering = new OpenDental.UI.Button();
        this.butNoneProvOrdering = new OpenDental.UI.Button();
        this.textDateExpiration = new OpenDental.ValidDate();
        this.butPickProvAdministering = new OpenDental.UI.Button();
        this.butPickProvOrdering = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(5, 50);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(130, 17);
        this.label5.TabIndex = 12;
        this.label5.Text = "Manufacturer";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelAmount
        //
        this.labelAmount.Location = new System.Drawing.Point(5, 128);
        this.labelAmount.Name = "labelAmount";
        this.labelAmount.Size = new System.Drawing.Size(130, 17);
        this.labelAmount.TabIndex = 10;
        this.labelAmount.Text = "Amount";
        this.labelAmount.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(136, 128);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(63, 20);
        this.textAmount.TabIndex = 3;
        //
        // comboVaccine
        //
        this.comboVaccine.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboVaccine.FormattingEnabled = true;
        this.comboVaccine.Location = new System.Drawing.Point(136, 23);
        this.comboVaccine.Name = "comboVaccine";
        this.comboVaccine.Size = new System.Drawing.Size(201, 21);
        this.comboVaccine.TabIndex = 0;
        this.comboVaccine.SelectedIndexChanged += new System.EventHandler(this.comboVaccine_SelectedIndexChanged);
        //
        // labelVaccine
        //
        this.labelVaccine.Location = new System.Drawing.Point(5, 23);
        this.labelVaccine.Name = "labelVaccine";
        this.labelVaccine.Size = new System.Drawing.Size(130, 17);
        this.labelVaccine.TabIndex = 13;
        this.labelVaccine.Text = "Vaccine Def";
        this.labelVaccine.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboUnits
        //
        this.comboUnits.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUnits.FormattingEnabled = true;
        this.comboUnits.Location = new System.Drawing.Point(136, 155);
        this.comboUnits.Name = "comboUnits";
        this.comboUnits.Size = new System.Drawing.Size(63, 21);
        this.comboUnits.TabIndex = 4;
        //
        // textManufacturer
        //
        this.textManufacturer.Location = new System.Drawing.Point(136, 48);
        this.textManufacturer.Name = "textManufacturer";
        this.textManufacturer.ReadOnly = true;
        this.textManufacturer.Size = new System.Drawing.Size(201, 20);
        this.textManufacturer.TabIndex = 14;
        this.textManufacturer.TabStop = false;
        //
        // labelDateTimeStartStop
        //
        this.labelDateTimeStartStop.Location = new System.Drawing.Point(5, 74);
        this.labelDateTimeStartStop.Name = "labelDateTimeStartStop";
        this.labelDateTimeStartStop.Size = new System.Drawing.Size(130, 17);
        this.labelDateTimeStartStop.TabIndex = 11;
        this.labelDateTimeStartStop.Text = "Date Time Start";
        this.labelDateTimeStartStop.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTimeStart
        //
        this.textDateTimeStart.Location = new System.Drawing.Point(136, 74);
        this.textDateTimeStart.Name = "textDateTimeStart";
        this.textDateTimeStart.Size = new System.Drawing.Size(151, 20);
        this.textDateTimeStart.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 182);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(130, 17);
        this.label2.TabIndex = 9;
        this.label2.Text = "Lot Number";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLotNum
        //
        this.textLotNum.Location = new System.Drawing.Point(136, 182);
        this.textLotNum.Name = "textLotNum";
        this.textLotNum.Size = new System.Drawing.Size(118, 20);
        this.textLotNum.TabIndex = 5;
        //
        // textDateTimeStop
        //
        this.textDateTimeStop.Location = new System.Drawing.Point(136, 101);
        this.textDateTimeStop.Name = "textDateTimeStop";
        this.textDateTimeStop.Size = new System.Drawing.Size(151, 20);
        this.textDateTimeStop.TabIndex = 2;
        //
        // labelDateTimeStop
        //
        this.labelDateTimeStop.Location = new System.Drawing.Point(5, 101);
        this.labelDateTimeStop.Name = "labelDateTimeStop";
        this.labelDateTimeStop.Size = new System.Drawing.Size(130, 17);
        this.labelDateTimeStop.TabIndex = 11;
        this.labelDateTimeStop.Text = "Date Time Stop";
        this.labelDateTimeStop.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDocument
        //
        this.labelDocument.Location = new System.Drawing.Point(136, 371);
        this.labelDocument.Name = "labelDocument";
        this.labelDocument.Size = new System.Drawing.Size(336, 45);
        this.labelDocument.TabIndex = 16;
        this.labelDocument.Text = "Document reason not given below.  Reason can include a specific allergy, adverse " + "effect, intollerance, patient declines, specific disease, etc.";
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(136, 419);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(336, 75);
        this.textNote.TabIndex = 17;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 419);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(130, 17);
        this.label3.TabIndex = 18;
        this.label3.Text = "Note";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 155);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(130, 17);
        this.label1.TabIndex = 19;
        this.label1.Text = "Units";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFilledCity
        //
        this.textFilledCity.Location = new System.Drawing.Point(624, 23);
        this.textFilledCity.Name = "textFilledCity";
        this.textFilledCity.Size = new System.Drawing.Size(151, 20);
        this.textFilledCity.TabIndex = 20;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(494, 23);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(130, 17);
        this.label4.TabIndex = 21;
        this.label4.Text = "City Where Filled";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFilledSt
        //
        this.textFilledSt.Location = new System.Drawing.Point(624, 48);
        this.textFilledSt.Name = "textFilledSt";
        this.textFilledSt.Size = new System.Drawing.Size(151, 20);
        this.textFilledSt.TabIndex = 22;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(494, 48);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(130, 17);
        this.label6.TabIndex = 23;
        this.label6.Text = "State Where Filled";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listCompletionStatus
        //
        this.listCompletionStatus.FormattingEnabled = true;
        this.listCompletionStatus.Location = new System.Drawing.Point(136, 234);
        this.listCompletionStatus.Name = "listCompletionStatus";
        this.listCompletionStatus.Size = new System.Drawing.Size(151, 56);
        this.listCompletionStatus.TabIndex = 24;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(5, 234);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(130, 17);
        this.label7.TabIndex = 25;
        this.label7.Text = "Completion Status";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(494, 208);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(130, 17);
        this.label8.TabIndex = 27;
        this.label8.Text = "Administration Note";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listAdministrationNote
        //
        this.listAdministrationNote.FormattingEnabled = true;
        this.listAdministrationNote.Location = new System.Drawing.Point(624, 208);
        this.listAdministrationNote.Name = "listAdministrationNote";
        this.listAdministrationNote.Size = new System.Drawing.Size(151, 121);
        this.listAdministrationNote.TabIndex = 26;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(494, 74);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(130, 17);
        this.label9.TabIndex = 28;
        this.label9.Text = "User";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProvNumOrdering
        //
        this.comboProvNumOrdering.Location = new System.Drawing.Point(624, 101);
        this.comboProvNumOrdering.MaxDropDownItems = 30;
        this.comboProvNumOrdering.Name = "comboProvNumOrdering";
        this.comboProvNumOrdering.Size = new System.Drawing.Size(254, 21);
        this.comboProvNumOrdering.TabIndex = 262;
        this.comboProvNumOrdering.SelectionChangeCommitted += new System.EventHandler(this.comboProvNumOrdering_SelectionChangeCommitted);
        //
        // label10
        //
        this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label10.Location = new System.Drawing.Point(494, 101);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(130, 17);
        this.label10.TabIndex = 261;
        this.label10.Text = "Ordering Provider";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProvNumAdministering
        //
        this.comboProvNumAdministering.Location = new System.Drawing.Point(624, 128);
        this.comboProvNumAdministering.MaxDropDownItems = 30;
        this.comboProvNumAdministering.Name = "comboProvNumAdministering";
        this.comboProvNumAdministering.Size = new System.Drawing.Size(254, 21);
        this.comboProvNumAdministering.TabIndex = 265;
        this.comboProvNumAdministering.SelectionChangeCommitted += new System.EventHandler(this.comboProvNumAdministering_SelectionChangeCommitted);
        //
        // label11
        //
        this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label11.Location = new System.Drawing.Point(494, 128);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(130, 17);
        this.label11.TabIndex = 264;
        this.label11.Text = "Administering Provider";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label12.Location = new System.Drawing.Point(5, 208);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(130, 17);
        this.label12.TabIndex = 268;
        this.label12.Text = "Date Expiration";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(5, 296);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(130, 17);
        this.label13.TabIndex = 270;
        this.label13.Text = "Refusal Reason";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listRefusalReason
        //
        this.listRefusalReason.FormattingEnabled = true;
        this.listRefusalReason.Location = new System.Drawing.Point(136, 296);
        this.listRefusalReason.Name = "listRefusalReason";
        this.listRefusalReason.Size = new System.Drawing.Size(151, 69);
        this.listRefusalReason.TabIndex = 269;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(494, 335);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(130, 17);
        this.label14.TabIndex = 272;
        this.label14.Text = "Action";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listAction
        //
        this.listAction.FormattingEnabled = true;
        this.listAction.Location = new System.Drawing.Point(624, 335);
        this.listAction.Name = "listAction";
        this.listAction.Size = new System.Drawing.Size(151, 43);
        this.listAction.TabIndex = 271;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(494, 155);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(130, 17);
        this.label15.TabIndex = 274;
        this.label15.Text = "Administration Route";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboAdministrationRoute
        //
        this.comboAdministrationRoute.FormattingEnabled = true;
        this.comboAdministrationRoute.Location = new System.Drawing.Point(624, 155);
        this.comboAdministrationRoute.Name = "comboAdministrationRoute";
        this.comboAdministrationRoute.Size = new System.Drawing.Size(151, 21);
        this.comboAdministrationRoute.TabIndex = 275;
        //
        // comboAdministrationSite
        //
        this.comboAdministrationSite.FormattingEnabled = true;
        this.comboAdministrationSite.Location = new System.Drawing.Point(624, 182);
        this.comboAdministrationSite.Name = "comboAdministrationSite";
        this.comboAdministrationSite.Size = new System.Drawing.Size(151, 21);
        this.comboAdministrationSite.TabIndex = 277;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(494, 182);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(130, 17);
        this.label16.TabIndex = 276;
        this.label16.Text = "Administration Site";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(624, 74);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(151, 20);
        this.textUser.TabIndex = 283;
        //
        // gridObservations
        //
        this.gridObservations.setHScrollVisible(false);
        this.gridObservations.Location = new System.Drawing.Point(624, 384);
        this.gridObservations.Name = "gridObservations";
        this.gridObservations.setScrollValue(0);
        this.gridObservations.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridObservations.Size = new System.Drawing.Size(254, 110);
        this.gridObservations.TabIndex = 284;
        this.gridObservations.setTitle("Observations");
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
        this.gridObservations.CellClick = __MultiODGridClickEventHandler.combine(this.gridObservations.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridObservations_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butUngroupObservations
        //
        this.butUngroupObservations.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUngroupObservations.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butUngroupObservations.setAutosize(true);
        this.butUngroupObservations.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUngroupObservations.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUngroupObservations.setCornerRadius(4F);
        this.butUngroupObservations.Location = new System.Drawing.Point(803, 497);
        this.butUngroupObservations.Name = "butUngroupObservations";
        this.butUngroupObservations.Size = new System.Drawing.Size(75, 22);
        this.butUngroupObservations.TabIndex = 287;
        this.butUngroupObservations.Text = "Ungroup";
        this.butUngroupObservations.Click += new System.EventHandler(this.butUngroupObservations_Click);
        //
        // butGroupObservations
        //
        this.butGroupObservations.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGroupObservations.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butGroupObservations.setAutosize(true);
        this.butGroupObservations.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGroupObservations.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGroupObservations.setCornerRadius(4F);
        this.butGroupObservations.Location = new System.Drawing.Point(725, 497);
        this.butGroupObservations.Name = "butGroupObservations";
        this.butGroupObservations.Size = new System.Drawing.Size(75, 22);
        this.butGroupObservations.TabIndex = 286;
        this.butGroupObservations.Text = "Group";
        this.butGroupObservations.Click += new System.EventHandler(this.butGroupObservations_Click);
        //
        // butAddObservation
        //
        this.butAddObservation.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddObservation.setAutosize(true);
        this.butAddObservation.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddObservation.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddObservation.setCornerRadius(4F);
        this.butAddObservation.Image = Resources.getAdd();
        this.butAddObservation.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddObservation.Location = new System.Drawing.Point(624, 497);
        this.butAddObservation.Name = "butAddObservation";
        this.butAddObservation.Size = new System.Drawing.Size(75, 22);
        this.butAddObservation.TabIndex = 285;
        this.butAddObservation.Text = "&Add";
        this.butAddObservation.Click += new System.EventHandler(this.butAddObservation_Click);
        //
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.setCornerRadius(4F);
        this.button1.Location = new System.Drawing.Point(772, 563);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(92, 24);
        this.button1.TabIndex = 282;
        this.button1.Text = "&OK";
        this.button1.Click += new System.EventHandler(this.butOK_Click);
        //
        // button2
        //
        this.button2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.button2.setAutosize(true);
        this.button2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button2.setCornerRadius(4F);
        this.button2.Location = new System.Drawing.Point(870, 563);
        this.button2.Name = "button2";
        this.button2.Size = new System.Drawing.Size(92, 24);
        this.button2.TabIndex = 281;
        this.button2.Text = "&Cancel";
        this.button2.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(11, 563);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(92, 24);
        this.butDelete.TabIndex = 280;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butNoneProvAdministering
        //
        this.butNoneProvAdministering.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneProvAdministering.setAutosize(false);
        this.butNoneProvAdministering.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneProvAdministering.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneProvAdministering.setCornerRadius(2F);
        this.butNoneProvAdministering.Location = new System.Drawing.Point(901, 128);
        this.butNoneProvAdministering.Name = "butNoneProvAdministering";
        this.butNoneProvAdministering.Size = new System.Drawing.Size(44, 21);
        this.butNoneProvAdministering.TabIndex = 279;
        this.butNoneProvAdministering.Text = "None";
        this.butNoneProvAdministering.Click += new System.EventHandler(this.butNoneProvAdministering_Click);
        //
        // butNoneProvOrdering
        //
        this.butNoneProvOrdering.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneProvOrdering.setAutosize(false);
        this.butNoneProvOrdering.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneProvOrdering.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneProvOrdering.setCornerRadius(2F);
        this.butNoneProvOrdering.Location = new System.Drawing.Point(901, 101);
        this.butNoneProvOrdering.Name = "butNoneProvOrdering";
        this.butNoneProvOrdering.Size = new System.Drawing.Size(44, 21);
        this.butNoneProvOrdering.TabIndex = 278;
        this.butNoneProvOrdering.Text = "None";
        this.butNoneProvOrdering.Click += new System.EventHandler(this.butNoneProvOrdering_Click);
        //
        // textDateExpiration
        //
        this.textDateExpiration.Location = new System.Drawing.Point(136, 208);
        this.textDateExpiration.Name = "textDateExpiration";
        this.textDateExpiration.Size = new System.Drawing.Size(151, 20);
        this.textDateExpiration.TabIndex = 267;
        //
        // butPickProvAdministering
        //
        this.butPickProvAdministering.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProvAdministering.setAutosize(false);
        this.butPickProvAdministering.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProvAdministering.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProvAdministering.setCornerRadius(2F);
        this.butPickProvAdministering.Location = new System.Drawing.Point(880, 128);
        this.butPickProvAdministering.Name = "butPickProvAdministering";
        this.butPickProvAdministering.Size = new System.Drawing.Size(18, 21);
        this.butPickProvAdministering.TabIndex = 266;
        this.butPickProvAdministering.Text = "...";
        this.butPickProvAdministering.Click += new System.EventHandler(this.butPickProvAdministering_Click);
        //
        // butPickProvOrdering
        //
        this.butPickProvOrdering.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProvOrdering.setAutosize(false);
        this.butPickProvOrdering.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProvOrdering.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProvOrdering.setCornerRadius(2F);
        this.butPickProvOrdering.Location = new System.Drawing.Point(880, 101);
        this.butPickProvOrdering.Name = "butPickProvOrdering";
        this.butPickProvOrdering.Size = new System.Drawing.Size(18, 21);
        this.butPickProvOrdering.TabIndex = 263;
        this.butPickProvOrdering.Text = "...";
        this.butPickProvOrdering.Click += new System.EventHandler(this.butPickProvOrdering_Click);
        //
        // FormEhrVaccinePatEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(974, 599);
        this.Controls.Add(this.butUngroupObservations);
        this.Controls.Add(this.butGroupObservations);
        this.Controls.Add(this.butAddObservation);
        this.Controls.Add(this.gridObservations);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.button1);
        this.Controls.Add(this.button2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butNoneProvAdministering);
        this.Controls.Add(this.butNoneProvOrdering);
        this.Controls.Add(this.comboAdministrationSite);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.comboAdministrationRoute);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.listAction);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.listRefusalReason);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.textDateExpiration);
        this.Controls.Add(this.comboProvNumAdministering);
        this.Controls.Add(this.butPickProvAdministering);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.comboProvNumOrdering);
        this.Controls.Add(this.butPickProvOrdering);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.listAdministrationNote);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.listCompletionStatus);
        this.Controls.Add(this.textFilledSt);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textFilledCity);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.labelDocument);
        this.Controls.Add(this.textDateTimeStop);
        this.Controls.Add(this.textDateTimeStart);
        this.Controls.Add(this.labelDateTimeStop);
        this.Controls.Add(this.labelDateTimeStartStop);
        this.Controls.Add(this.comboUnits);
        this.Controls.Add(this.comboVaccine);
        this.Controls.Add(this.labelVaccine);
        this.Controls.Add(this.textLotNum);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.textManufacturer);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.labelAmount);
        this.Controls.Add(this.label5);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrVaccinePatEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Vaccine Edit";
        this.Load += new System.EventHandler(this.FormVaccinePatEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelAmount = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAmount = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox comboVaccine = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelVaccine = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboUnits = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textManufacturer = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDateTimeStartStop = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTimeStart = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLotNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateTimeStop = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDateTimeStop = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDocument = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFilledCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFilledSt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listCompletionStatus = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listAdministrationNote = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProvNumOrdering = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butPickProvOrdering;
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProvNumAdministering = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butPickProvAdministering;
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateExpiration;
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listRefusalReason = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listAction = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboAdministrationRoute = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboAdministrationSite = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butNoneProvOrdering;
    private OpenDental.UI.Button butNoneProvAdministering;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button button1;
    private OpenDental.UI.Button button2;
    private System.Windows.Forms.TextBox textUser = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODGrid gridObservations;
    private OpenDental.UI.Button butAddObservation;
    private OpenDental.UI.Button butGroupObservations;
    private OpenDental.UI.Button butUngroupObservations;
}


