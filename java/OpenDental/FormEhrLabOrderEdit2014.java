//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import EhrLaboratories.HL70065;
import EhrLaboratories.HL70123;
import EhrLaboratories.HL70125;
import EhrLaboratories.HL70200;
import EhrLaboratories.HL70203;
import OpenDental.FormCDSIntervention;
import OpenDental.FormEhrLabImageEdit;
import OpenDental.FormEhrLabNoteEdit;
import OpenDental.FormEhrLabOrderEdit2014;
import OpenDental.FormEhrLabResultEdit2014;
import OpenDental.FormEhrLabSpecimenEdit;
import OpenDental.FormInfobutton;
import OpenDental.FormLoincs;
import OpenDental.FormProviderPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabNote;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabResults;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.OIDInternals;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabOrderEdit2014  extends Form 
{

    long PatCurNum = new long();
    public EhrLab EhrLabCur;
    private EhrLab ParentLab;
    public boolean IsNew = new boolean();
    public boolean IsImport = new boolean();
    public boolean IsViewOnly = new boolean();
    public FormEhrLabOrderEdit2014() throws Exception {
        initializeComponent();
    }

    private void formLabPanelEdit_Load(Object sender, EventArgs e) throws Exception {
        Height = System.Windows.Forms.Screen.GetWorkingArea(this).Height;
        this.SetDesktopLocation(DesktopLocation.X, 0);
        if (IsNew)
        {
            checkAutoID.Checked = true;
            checkAutoID.Visible = true;
        }
         
        if (IsImport || IsViewOnly)
        {
            for (Object __dummyForeachVar0 : this.Controls)
            {
                Control c = (Control)__dummyForeachVar0;
                c.Enabled = false;
            }
            butViewParent.Enabled = true;
            gridMain.Enabled = true;
            gridNotes.Enabled = true;
            gridSpecimen.Enabled = true;
            butCancel.Text = "Close";
            butCancel.Enabled = true;
        }
         
        //butAddNote.Enabled=false;
        //butAddCopyTo.Enabled=false;
        //butAddClinicalInfo.Enabled=false;
        //butAdd.Enabled=false;
        //butAddSpecimens.Enabled=false;
        //butPatientPick.Enabled=false;
        //textName.Enabled=false;
        //butProvPicker.Enabled=false;
        //butServicePicker.Enabled=false;
        //butParentPicker.Enabled=false;
        //butDelete.Enabled=false;
        /**
        * /combos
        */
        //comboOrderingProvIdType.Enabled=false;
        //comboOrderingProvNameType.Enabled=false;
        //comboResultStatus.Enabled=false;
        //comboSpecimenActionCode.Enabled=false;
        //TODO:hide the rest of the controls that shouldn't show if importing or view only.
        //Parent Result
        if (!StringSupport.equals(EhrLabCur.ParentPlacerOrderNum, ""))
        {
            textParentOrderNum.Text = EhrLabCur.ParentPlacerOrderNum;
        }
        else if (!StringSupport.equals(EhrLabCur.ParentFillerOrderNum, ""))
        {
            textParentOrderNum.Text = EhrLabCur.ParentFillerOrderNum;
        }
          
        fillOrderNums();
        fillProvInfo();
        //Results Handling
        checkResultsHandlingF.Checked = EhrLabCur.ListEhrLabResultsHandlingF;
        checkResultsHandlingN.Checked = EhrLabCur.ListEhrLabResultsHandlingN;
        //UpdateTime
        textResultDateTime.Text = EhrLab.formatDateFromHL7(EhrLabCur.ResultDateTime);
        fillUsi();
        //Service Identifier
        fillGrid();
        //LabResults
        textObservationDateTimeStart.Text = EhrLab.formatDateFromHL7(EhrLabCur.ObservationDateTimeStart);
        textObservationDateTimeEnd.Text = EhrLab.formatDateFromHL7(EhrLabCur.ObservationDateTimeEnd);
        //TQ1
        textTQ1Start.Text = EhrLab.formatDateFromHL7(EhrLabCur.TQ1DateTimeStart);
        textTQ1Stop.Text = EhrLab.formatDateFromHL7(EhrLabCur.TQ1DateTimeEnd);
        fillGridNotes();
        fillGridResultsCopyTo();
        fillGridClinicalInformation();
        fillGridSpecimen();
        //Specimen Action Code
        fillComboSpecimenActionCode();
        //Result Status
        fillComboResultStatus();
        //Only allow image editing on existing labs
        if (EhrLabCur == null || EhrLabCur.EhrLabNum <= 0 || EhrLabCur.PatNum <= 0)
        {
            butManageImages.Enabled = false;
            labelManageImages.Visible = true;
        }
        else
        {
            butManageImages.Enabled = true;
            labelManageImages.Visible = false;
        } 
    }

    private void fillComboSpecimenActionCode() throws Exception {
        comboSpecimenActionCode.Items.Clear();
        comboSpecimenActionCode.BeginUpdate();
        List<String> listSpecActionCodes = EhrLabs.getHL70065Descriptions();
        comboSpecimenActionCode.Items.AddRange(listSpecActionCodes.ToArray());
        comboSpecimenActionCode.EndUpdate();
        comboSpecimenActionCode.SelectedIndex = (int)Enum.Parse(HL70065.class, EhrLabCur.SpecimenActionCode.ToString(), true) + 1;
    }

    private void fillComboResultStatus() throws Exception {
        comboResultStatus.Items.Clear();
        comboResultStatus.BeginUpdate();
        List<String> listResStatCodes = EhrLabs.getHL70123Descriptions();
        comboResultStatus.Items.AddRange(listResStatCodes.ToArray());
        comboResultStatus.EndUpdate();
        comboResultStatus.SelectedIndex = (int)Enum.Parse(HL70123.class, EhrLabCur.ResultStatus.ToString(), true) + 1;
    }

    private void fillUsi() throws Exception {
        textUsiID.Text = EhrLabCur.UsiID;
        textUsiText.Text = EhrLabCur.UsiText;
        textUsiCodeSystemName.Text = EhrLabCur.UsiCodeSystemName;
        textUsiIDAlt.Text = EhrLabCur.UsiIDAlt;
        textUsiTextAlt.Text = EhrLabCur.UsiTextAlt;
        textUsiCodeSystemNameAlt.Text = EhrLabCur.UsiCodeSystemNameAlt;
        textUsiTextOriginal.Text = EhrLabCur.UsiTextOriginal;
    }

    private void fillProvInfo() throws Exception {
        textOrderingProvIdentifier.Text = EhrLabCur.OrderingProviderID;
        textOrderingProvLastName.Text = EhrLabCur.OrderingProviderLName;
        textOrderingProvFirstName.Text = EhrLabCur.OrderingProviderFName;
        textOrderingProvMiddleName.Text = EhrLabCur.OrderingProviderMiddleNames;
        textOrderingProvSuffix.Text = EhrLabCur.OrderingProviderSuffix;
        textOrderingProvPrefix.Text = EhrLabCur.OrderingProviderPrefix;
        textOrderingProvAANID.Text = EhrLabCur.OrderingProviderAssigningAuthorityNamespaceID;
        textOrderingProvAAUID.Text = EhrLabCur.OrderingProviderAssigningAuthorityUniversalID;
        textOrderingProvAAUIDType.Text = EhrLabCur.OrderingProviderAssigningAuthorityIDType;
        comboOrderingProvNameType.Items.Clear();
        comboOrderingProvNameType.BeginUpdate();
        //Fill medical director name combo with HL70200 enum.  Not sure if blank is acceptable.
        List<String> listOrderingProvNameType = EhrLabResults.getHL70200Descriptions();
        comboOrderingProvNameType.Items.AddRange(listOrderingProvNameType.ToArray());
        comboOrderingProvNameType.EndUpdate();
        comboOrderingProvNameType.SelectedIndex = (int)Enum.Parse(HL70200.class, EhrLabCur.OrderingProviderNameTypeCode.ToString(), true) + 1;
        comboOrderingProvIdType.Items.Clear();
        comboOrderingProvIdType.BeginUpdate();
        //Fill medical director type combo with HL70203 enum.  Not sure if blank is acceptable.
        List<String> listOrderingProvIdType = EhrLabs.getHL70203Descriptions();
        comboOrderingProvIdType.Items.AddRange(listOrderingProvIdType.ToArray());
        comboOrderingProvIdType.EndUpdate();
        comboOrderingProvIdType.SelectedIndex = (int)Enum.Parse(HL70203.class, EhrLabCur.OrderingProviderIdentifierTypeCode.ToString(), true) + 1;
    }

    private void fillOrderNums() throws Exception {
        //Placer Order Num
        textPlacerOrderNum.Text = EhrLabCur.PlacerOrderNum;
        textPlacerOrderNamespace.Text = EhrLabCur.PlacerOrderNamespace;
        textPlacerOrderUniversalID.Text = EhrLabCur.PlacerOrderUniversalID;
        textPlacerOrderUniversalIDType.Text = EhrLabCur.PlacerOrderUniversalIDType;
        //Placer Order Group Num
        textPlacerGroupNum.Text = EhrLabCur.PlacerGroupNum;
        textPlacerGroupNamespace.Text = EhrLabCur.PlacerGroupNamespace;
        textPlacerGroupUniversalID.Text = EhrLabCur.PlacerGroupUniversalID;
        textPlacerGroupUniversalIDType.Text = EhrLabCur.PlacerGroupUniversalIDType;
        //Filler Order Num
        textFillerOrderNum.Text = EhrLabCur.FillerOrderNum;
        textFillerOrderNamespace.Text = EhrLabCur.FillerOrderNamespace;
        textFillerOrderUniversalID.Text = EhrLabCur.FillerOrderUniversalID;
        textFillerOrderUniversalIDType.Text = EhrLabCur.FillerOrderUniversalIDType;
        return ;
    }

    private void checkAutoID_CheckedChanged(Object sender, EventArgs e) throws Exception {
        textPlacerOrderNum.Enabled = !checkAutoID.Checked;
        textPlacerOrderUniversalID.Enabled = !checkAutoID.Checked;
        textPlacerOrderUniversalIDType.Enabled = !checkAutoID.Checked;
        textPlacerOrderNamespace.Enabled = !checkAutoID.Checked;
    }

    /**
    * Lab Results
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
        {
            //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
            col = new ODGridColumn("",18);
            //infoButton
            col.setImageList(imageListInfoButton);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn("Test Date",70);
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("LOINC",60);
        //LoincCode
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Test Performed",230);
        //ShortDescription
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Result Value",160);
        //Complicated
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Units",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Flags",40);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EhrLabCur.getListEhrLabResults().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
            {
                //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
                row.getCells().add("0");
            }
             
            //index of infobutton
            if (EhrLabCur.getListEhrLabResults()[i].ObservationDateTime == null || StringSupport.equals(EhrLabCur.getListEhrLabResults()[i].ObservationDateTime, ""))
            {
                row.getCells().add("");
            }
            else
            {
                //null date
                String dateSt = EhrLabCur.getListEhrLabResults()[i].ObservationDateTime.Substring(0, 8);
                //stored in DB as yyyyMMdd[hh[mm[ss]]], []==optional components
                DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
                row.getCells().Add(dateT.ToShortDateString());
            } 
            //date only
            if (!StringSupport.equals(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierID, ""))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierID);
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierText);
            }
            else if (!StringSupport.equals(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierIDAlt, ""))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierIDAlt);
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationIdentifierTextAlt);
            }
            else
            {
                row.getCells().add("UNK");
                row.getCells().add("Unknown, could not find valid test code.");
            }  
            ValueType __dummyScrutVar0 = EhrLabCur.getListEhrLabResults()[i].ValueType;
            if (__dummyScrutVar0.equals(HL70125.CE) || __dummyScrutVar0.equals(HL70125.CWE))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueCodedElementText);
            }
            else if (__dummyScrutVar0.equals(HL70125.DT) || __dummyScrutVar0.equals(HL70125.TS))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueDateTime);
            }
            else if (__dummyScrutVar0.equals(HL70125.TM))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueTime.ToString());
            }
            else if (__dummyScrutVar0.equals(HL70125.NM))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueNumeric.ToString());
            }
            else if (__dummyScrutVar0.equals(HL70125.SN))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueComparator + EhrLabCur.getListEhrLabResults()[i].ObservationValueNumber1 + (StringSupport.equals(EhrLabCur.getListEhrLabResults()[i].ObservationValueSeparatorOrSuffix, "") ? "" : EhrLabCur.getListEhrLabResults()[i].ObservationValueSeparatorOrSuffix + EhrLabCur.getListEhrLabResults()[i].ObservationValueNumber2));
            }
            else if (__dummyScrutVar0.equals(HL70125.FT) || __dummyScrutVar0.equals(HL70125.ST) || __dummyScrutVar0.equals(HL70125.TX))
            {
                row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].ObservationValueText);
            }
                  
            row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].UnitsID);
            row.getCells().Add(EhrLabCur.getListEhrLabResults()[i].AbnormalFlags.Replace("N", ""));
            //abnormal flags, show blank if flag is "Normal"
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    /**
    * Lab Result Notes. Currently includes notes for results too... TODO: seperate notes for labs and results.
    */
    private void fillGridNotes() throws Exception {
        gridNotes.beginUpdate();
        gridNotes.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Note Num",60);
        gridNotes.getColumns().add(col);
        col = new ODGridColumn("Comments",300);
        gridNotes.getColumns().add(col);
        gridNotes.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EhrLabCur.getListEhrLabNotes().Count;i++)
        {
            for (int j = 0;j < EhrLabCur.getListEhrLabNotes()[i].Comments.Split('^').Length;j++)
            {
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add((j == 0 ? (i + 1).ToString() : ""));
                //add note number if this is first comment for the note, otherwise add blank cell.
                row.getCells().Add(EhrLabCur.getListEhrLabNotes()[i].Comments.Split('^')[j]);
                //Add each comment.
                gridNotes.getRows().add(row);
            }
        }
        gridNotes.endUpdate();
    }

    /**
    * 
    */
    private void fillGridResultsCopyTo() throws Exception {
        gridResultsCopyTo.beginUpdate();
        gridResultsCopyTo.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Name",60);
        gridResultsCopyTo.getColumns().add(col);
        gridResultsCopyTo.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EhrLabCur.getListEhrLabResultsCopyTo().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(EhrLabCur.getListEhrLabResultsCopyTo()[i].CopyToPrefix + " " + EhrLabCur.getListEhrLabResultsCopyTo()[i].CopyToFName + " " + EhrLabCur.getListEhrLabResultsCopyTo()[i].CopyToLName + " " + EhrLabCur.getListEhrLabResultsCopyTo()[i].CopyToSuffix);
            //TODO: Make this neater. Will display extra spaces if missing prefix suffix or middle names.
            gridResultsCopyTo.getRows().add(row);
        }
        gridResultsCopyTo.endUpdate();
    }

    private void fillGridClinicalInformation() throws Exception {
        gridClinicalInformation.beginUpdate();
        gridClinicalInformation.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",60);
        //arbitrary width, only column in grid.
        gridClinicalInformation.getColumns().add(col);
        gridClinicalInformation.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EhrLabCur.getListRelevantClinicalInformations().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(EhrLabCur.getListRelevantClinicalInformations()[i].ClinicalInfoText);
            //may be blank, if so, check the "alt" text
            gridClinicalInformation.getRows().add(row);
        }
        gridClinicalInformation.endUpdate();
    }

    /**
    * Lab Results
    */
    private void fillGridSpecimen() throws Exception {
        gridSpecimen.beginUpdate();
        gridSpecimen.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Rej", 30, HorizontalAlignment.Center);
        //arbitrary width, only column in grid.
        gridSpecimen.getColumns().add(col);
        col = new ODGridColumn("Specimen Type",60);
        //arbitrary width, only column in grid.
        gridSpecimen.getColumns().add(col);
        gridSpecimen.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EhrLabCur.getListEhrLabSpecimens().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add((EhrLabCur.getListEhrLabSpecimens()[i].ListEhrLabSpecimenRejectReason.Count == 0 ? "" : "X"));
            //X is specimen rejected.
            row.getCells().Add(EhrLabCur.getListEhrLabSpecimens()[i].SpecimenTypeText);
            //may be blank, if so, check the "alt" text
            gridSpecimen.getRows().add(row);
        }
        gridSpecimen.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabResultEdit2014 FormLRE = new FormEhrLabResultEdit2014();
        FormLRE.EhrLabResultCur = EhrLabCur.getListEhrLabResults()[e.getRow()];
        FormLRE.IsImport = IsImport;
        FormLRE.IsViewOnly = IsViewOnly;
        FormLRE.ShowDialog();
        if (IsImport || IsViewOnly || FormLRE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrLabCur.getListEhrLabResults()[e.getRow()] = FormLRE.EhrLabResultCur;
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEhrLabResultEdit2014 FormLRE = new FormEhrLabResultEdit2014();
        FormLRE.EhrLabResultCur = new EhrLabResult();
        FormLRE.EhrLabResultCur.setIsNew(true);
        FormLRE.ShowDialog();
        if (FormLRE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrLabCur.getListEhrLabResults().Add(FormLRE.EhrLabResultCur);
        fillGrid();
    }

    private void butAddNote_Click(Object sender, EventArgs e) throws Exception {
        FormEhrLabNoteEdit FormLNE = new FormEhrLabNoteEdit();
        FormLNE.LabNoteCur = new EhrLabNote();
        FormLNE.ShowDialog();
        if (FormLNE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        FormLNE.LabNoteCur.EhrLabNum = EhrLabCur.EhrLabNum;
        EhrLabCur.getListEhrLabNotes().Add(FormLNE.LabNoteCur);
        fillGridNotes();
    }

    private void butManageImages_Click(Object sender, EventArgs e) throws Exception {
        //EhrLabCur was verifed as valid on the form's load event. No need for validation here.
        new FormEhrLabImageEdit(EhrLabCur.PatNum,EhrLabCur.EhrLabNum).ShowDialog();
    }

    private void gridNotes_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabNoteEdit FormLNE = new FormEhrLabNoteEdit();
        FormLNE.IsViewOnly = IsViewOnly;
        FormLNE.IsImport = IsImport;
        FormLNE.LabNoteCur = EhrLabCur.getListEhrLabNotes()[e.getRow()];
        FormLNE.ShowDialog();
        if (FormLNE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrLabCur.getListEhrLabNotes()[e.getRow()] = FormLNE.LabNoteCur;
        fillGridNotes();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will delete the entire lab order and all attached lab results. This cannot be undone. Would you like to continue?"))
        {
            return ;
        }
         
        EhrLabs.delete(EhrLabCur.EhrLabNum);
        DialogResult = DialogResult.OK;
    }

    private void butViewParent_Click(Object sender, EventArgs e) throws Exception {
        EhrLab ehrLabParent = null;
        ehrLabParent = EhrLabs.getByGUID(EhrLabCur.ParentPlacerOrderUniversalID,EhrLabCur.ParentPlacerOrderNum);
        if (ehrLabParent == null)
        {
            ehrLabParent = EhrLabs.getByGUID(EhrLabCur.ParentFillerOrderUniversalID,EhrLabCur.ParentFillerOrderNum);
        }
         
        if (ehrLabParent == null)
        {
            return ;
        }
         
        FormEhrLabOrderEdit2014 FormELOE = new FormEhrLabOrderEdit2014();
        FormELOE.EhrLabCur = ehrLabParent;
        FormELOE.IsViewOnly = true;
        FormELOE.Text = Lan.g(this,"Parent Lab Order - READ ONLY");
        FormELOE.ShowDialog();
    }

    /**
    * 
    */
    private boolean entriesAreValid() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        //Order Numbers
        if (checkAutoID.Checked)
        {
            if (StringSupport.equals(OIDInternals.getForType(IdentifierType.LabOrder).IDRoot, ""))
            {
                errorMessage.AppendLine("  OID registry must be configured in order to use Automatic Lab Order IDs.");
            }
             
        }
        else //don't validate order numbers... it will be automatically generated when saved.
        //Blank placerOrderNum OR OrderNum w/ blank OID
        if ((StringSupport.equals(textPlacerOrderNum.Text, "") || (!StringSupport.equals(textPlacerOrderNum.Text, "") && StringSupport.equals(textPlacerOrderUniversalID.Text, ""))) && (StringSupport.equals(textFillerOrderNum.Text, "") || (!StringSupport.equals(textFillerOrderNum.Text, "") && StringSupport.equals(textFillerOrderUniversalID.Text, ""))))
        {
            //Blank fillerOrderNum OR OrderNum w/blank OID
            errorMessage.AppendLine("  Order must have valid placer or filler order number with universal ID.");
        }
          
        //Prov Numbers
        if (StringSupport.equals(textOrderingProvAAUID.Text, OIDInternals.getForType(IdentifierType.Provider).IDRoot))
        {
            Provider prov = null;
            try
            {
                prov = Providers.GetProv(PIn.Long(textOrderingProvIdentifier.Text));
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            if (prov == null)
            {
                errorMessage.AppendLine("  Ordering provider identifier or assigning authority is invalid.");
            }
             
        }
        else
        {
            if (!Regex.IsMatch(textOrderingProvIdentifier.Text, "^(\\d|\\w)+$"))
            {
                errorMessage.AppendLine("  Ordering Provider identifier must only contain numbers and letters.");
            }
             
        } 
        //TODO: validate the controls
        if (!StringSupport.equals(errorMessage.ToString(), ""))
        {
            errorMessage.Insert(0, "Unable to save current Lab Order for the following reasons:\r\n");
            MessageBox.Show(this, errorMessage.ToString());
            return false;
        }
         
        return true;
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
        {
            return ;
        }
         
        //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
        if (e.getCol() != 0)
        {
            return ;
        }
         
        FormInfobutton FormIB = new FormInfobutton();
        if (PatCurNum != null && PatCurNum > 0)
        {
            FormIB.PatCur = Patients.getPat(PatCurNum);
        }
         
        FormIB.ListObjects.Add(EhrLabCur.getListEhrLabResults()[e.getRow()]);
        FormIB.ShowDialog();
    }

    private void gridSpecimen_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabSpecimenEdit FormLSE = new FormEhrLabSpecimenEdit();
        FormLSE.EhrLabSpecimenCur = EhrLabCur.getListEhrLabSpecimens()[e.getRow()];
        FormLSE.ShowDialog();
    }

    private void butProvPicker_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick FormPP = new FormProviderPick();
        FormPP.ShowDialog();
        if (FormPP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Provider prov = Providers.getProv(FormPP.SelectedProvNum);
        if (!StringSupport.equals(prov.NationalProvID, ""))
        {
            textOrderingProvIdentifier.Text = prov.NationalProvID;
            comboOrderingProvIdType.SelectedIndex = ((Enum)HL70203.NPI).ordinal() + 1;
            textOrderingProvAANID.Text = "";
            textOrderingProvAAUID.Text = "2.16.840.1.113883.4.6";
            //NPI OID
            textOrderingProvAAUIDType.Text = "ISO";
        }
        else
        {
            textOrderingProvIdentifier.Text = prov.ProvNum.ToString();
            comboOrderingProvIdType.SelectedIndex = ((Enum)HL70203.PRN).ordinal() + 1;
            textOrderingProvAANID.Text = "";
            textOrderingProvAAUID.Text = OIDInternals.getForType(IdentifierType.Provider).IDRoot;
            //Internal OID
            textOrderingProvAAUIDType.Text = "ISO";
        } 
        comboOrderingProvNameType.SelectedIndex = ((Enum)HL70200.L).ordinal() + 1;
        textOrderingProvFirstName.Text = prov.FName;
        textOrderingProvLastName.Text = prov.LName;
        textOrderingProvMiddleName.Text = prov.MI;
    }

    private void butServicePicker_Click(Object sender, EventArgs e) throws Exception {
        FormLoincs FormL = new FormLoincs();
        FormL.IsSelectionMode = true;
        FormL.ShowDialog();
        if (FormL.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textUsiID.Text = FormL.SelectedLoinc.LoincCode;
        textUsiCodeSystemName.Text = "LN";
        textUsiText.Text = FormL.SelectedLoinc.NameShort;
        textUsiTextOriginal.Text = FormL.SelectedLoinc.NameLongCommon;
    }

    private void butLastUpdate_Click(Object sender, EventArgs e) throws Exception {
    }

    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        if (IsImport || IsViewOnly)
        {
            DialogResult = DialogResult.OK;
            return ;
        }
         
        if (!entriesAreValid())
        {
            return ;
        }
         
        if (Security.getCurUser().ProvNum != 0 && !StringSupport.equals(Providers.getProv(Security.getCurUser().ProvNum).EhrKey, ""))
        {
            //The user who is currently logged in is a provider and has a valid EHR key.
            EhrLabCur.IsCpoe = true;
        }
         
        if (EhrLabCur.PatNum == 0 && PatCurNum != null)
        {
            EhrLabCur.PatNum = PatCurNum;
        }
         
        //EhrLabCur.OrderControlCode=((HL70119)comb);//TODO:UI and this value.
        if (checkAutoID.Checked)
        {
            EhrLabCur.PlacerOrderNum = EhrLabs.getNextOrderNum().ToString();
            EhrLabCur.PlacerOrderNamespace = "";
            EhrLabCur.PlacerOrderUniversalID = OIDInternals.getForType(IdentifierType.LabOrder).IDRoot;
            EhrLabCur.PlacerOrderUniversalIDType = "ISO";
        }
        else
        {
            EhrLabCur.PlacerOrderNum = textPlacerOrderNum.Text;
            EhrLabCur.PlacerOrderNamespace = textPlacerOrderNamespace.Text;
            EhrLabCur.PlacerOrderUniversalID = textPlacerOrderUniversalID.Text;
            EhrLabCur.PlacerOrderUniversalIDType = textPlacerOrderUniversalIDType.Text;
        } 
        EhrLabCur.FillerOrderNum = textFillerOrderNum.Text;
        EhrLabCur.FillerOrderNamespace = textFillerOrderNamespace.Text;
        EhrLabCur.FillerOrderUniversalID = textFillerOrderUniversalID.Text;
        EhrLabCur.FillerOrderUniversalIDType = textFillerOrderUniversalIDType.Text;
        EhrLabCur.PlacerGroupNum = textPlacerGroupNum.Text;
        EhrLabCur.PlacerGroupNamespace = textPlacerGroupNamespace.Text;
        EhrLabCur.PlacerGroupUniversalID = textPlacerGroupUniversalID.Text;
        EhrLabCur.PlacerGroupUniversalIDType = textPlacerGroupUniversalIDType.Text;
        EhrLabCur.OrderingProviderID = textOrderingProvIdentifier.Text;
        EhrLabCur.OrderingProviderLName = textOrderingProvLastName.Text;
        EhrLabCur.OrderingProviderFName = textOrderingProvFirstName.Text;
        EhrLabCur.OrderingProviderMiddleNames = textOrderingProvMiddleName.Text;
        EhrLabCur.OrderingProviderSuffix = textOrderingProvSuffix.Text;
        EhrLabCur.OrderingProviderPrefix = textOrderingProvPrefix.Text;
        EhrLabCur.OrderingProviderAssigningAuthorityNamespaceID = textOrderingProvAANID.Text;
        EhrLabCur.OrderingProviderAssigningAuthorityUniversalID = textOrderingProvAAUID.Text;
        EhrLabCur.OrderingProviderAssigningAuthorityIDType = textOrderingProvAAUIDType.Text;
        EhrLabCur.OrderingProviderNameTypeCode = ((HL70200)comboOrderingProvNameType.SelectedIndex - 1);
        EhrLabCur.OrderingProviderIdentifierTypeCode = ((HL70203)comboOrderingProvIdType.SelectedIndex - 1);
        //EhrLabCur.SetIdOBR=PIn.Long("");//TODO: UI and Save
        EhrLabCur.UsiID = textUsiID.Text;
        EhrLabCur.UsiText = textUsiText.Text;
        EhrLabCur.UsiCodeSystemName = textUsiCodeSystemName.Text;
        EhrLabCur.UsiIDAlt = textUsiIDAlt.Text;
        EhrLabCur.UsiTextAlt = textUsiTextAlt.Text;
        EhrLabCur.UsiCodeSystemNameAlt = textUsiCodeSystemNameAlt.Text;
        EhrLabCur.UsiTextOriginal = textUsiTextOriginal.Text;
        EhrLabCur.ObservationDateTimeStart = EhrLab.formatDateToHL7(textObservationDateTimeStart.Text.Trim());
        EhrLabCur.ObservationDateTimeEnd = EhrLab.formatDateToHL7(textObservationDateTimeEnd.Text.Trim());
        EhrLabCur.SpecimenActionCode = ((HL70065)comboSpecimenActionCode.SelectedIndex - 1);
        EhrLabCur.ResultDateTime = EhrLab.formatDateToHL7(textResultDateTime.Text.Trim());
        //upper right hand corner of form.
        EhrLabCur.ResultStatus = ((HL70123)comboResultStatus.SelectedIndex - 1);
        //TODO: parent result.
        /*
        			EhrLabCur.ParentObservationID=
        			EhrLabCur.ParentObservationText=
        			EhrLabCur.ParentObservationCodeSystemName=
        			EhrLabCur.ParentObservationIDAlt=
        			EhrLabCur.ParentObservationTextAlt=
        			EhrLabCur.ParentObservationCodeSystemNameAlt=
        			EhrLabCur.ParentObservationTextOriginal=
        			EhrLabCur.ParentObservationSubID=
        			EhrLabCur.ParentPlacerOrderNum=
        			EhrLabCur.ParentPlacerOrderNamespace=
        			EhrLabCur.ParentPlacerOrderUniversalID=
        			EhrLabCur.ParentPlacerOrderUniversalIDType=
        			EhrLabCur.ParentFillerOrderNum=
        			EhrLabCur.ParentFillerOrderNamespace=
        			EhrLabCur.ParentFillerOrderUniversalID=
        			EhrLabCur.ParentFillerOrderUniversalIDType=
        			*/
        EhrLabCur.ListEhrLabResultsHandlingF = checkResultsHandlingF.Checked;
        EhrLabCur.ListEhrLabResultsHandlingN = checkResultsHandlingN.Checked;
        //EhrLabCur.TQ1SetId=//TODO:this
        EhrLabCur.TQ1DateTimeStart = EhrLab.formatDateToHL7(textTQ1Start.Text);
        EhrLabCur.TQ1DateTimeEnd = EhrLab.formatDateToHL7(textTQ1Stop.Text);
        EhrLabs.saveToDB(EhrLabCur);
        Patient patCur = Patients.getPat(EhrLabCur.PatNum);
        for (int i = 0;i < EhrLabCur.getListEhrLabResults().Count;i++)
        {
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).LabTestCDS)
            {
                FormCDSIntervention FormCDSI = new FormCDSIntervention();
                FormCDSI.ListCDSI = EhrTriggers.triggerMatch(EhrLabCur.getListEhrLabResults()[i],patCur);
                FormCDSI.showIfRequired(false);
            }
             
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrLabOrderEdit2014.class);
        this.butCancel = new System.Windows.Forms.Button();
        this.butOk = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.butAdd = new System.Windows.Forms.Button();
        this.labelPatient = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.butAddNote = new System.Windows.Forms.Button();
        this.butAddCopyTo = new System.Windows.Forms.Button();
        this.butAddSpecimens = new System.Windows.Forms.Button();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.checkAutoID = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPlacerOrderNum = new System.Windows.Forms.TextBox();
        this.textPlacerOrderUniversalIDType = new System.Windows.Forms.TextBox();
        this.label42 = new System.Windows.Forms.Label();
        this.label21 = new System.Windows.Forms.Label();
        this.textPlacerOrderNamespace = new System.Windows.Forms.TextBox();
        this.label22 = new System.Windows.Forms.Label();
        this.textPlacerOrderUniversalID = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textPlacerGroupNum = new System.Windows.Forms.TextBox();
        this.textPlacerGroupUniversalIDType = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.textPlacerGroupNamespace = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textPlacerGroupUniversalID = new System.Windows.Forms.TextBox();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butProvPicker = new OpenDental.UI.Button();
        this.comboOrderingProvIdType = new System.Windows.Forms.ComboBox();
        this.label33 = new System.Windows.Forms.Label();
        this.comboOrderingProvNameType = new System.Windows.Forms.ComboBox();
        this.label32 = new System.Windows.Forms.Label();
        this.label23 = new System.Windows.Forms.Label();
        this.textOrderingProvPrefix = new System.Windows.Forms.TextBox();
        this.label24 = new System.Windows.Forms.Label();
        this.textOrderingProvSuffix = new System.Windows.Forms.TextBox();
        this.label25 = new System.Windows.Forms.Label();
        this.textOrderingProvMiddleName = new System.Windows.Forms.TextBox();
        this.label29 = new System.Windows.Forms.Label();
        this.textOrderingProvFirstName = new System.Windows.Forms.TextBox();
        this.label30 = new System.Windows.Forms.Label();
        this.textOrderingProvLastName = new System.Windows.Forms.TextBox();
        this.label31 = new System.Windows.Forms.Label();
        this.textOrderingProvIdentifier = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textOrderingProvAAUIDType = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textOrderingProvAANID = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textOrderingProvAAUID = new System.Windows.Forms.TextBox();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textFillerOrderNum = new System.Windows.Forms.TextBox();
        this.textFillerOrderUniversalIDType = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.textFillerOrderNamespace = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textFillerOrderUniversalID = new System.Windows.Forms.TextBox();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.textObservationDateTimeStart = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label27 = new System.Windows.Forms.Label();
        this.textObservationDateTimeEnd = new System.Windows.Forms.TextBox();
        this.butServicePicker = new OpenDental.UI.Button();
        this.label44 = new System.Windows.Forms.Label();
        this.textUsiCodeSystemNameAlt = new System.Windows.Forms.TextBox();
        this.label43 = new System.Windows.Forms.Label();
        this.textUsiCodeSystemName = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.textUsiText = new System.Windows.Forms.TextBox();
        this.label15 = new System.Windows.Forms.Label();
        this.label36 = new System.Windows.Forms.Label();
        this.textUsiID = new System.Windows.Forms.TextBox();
        this.textUsiTextOriginal = new System.Windows.Forms.TextBox();
        this.textUsiTextAlt = new System.Windows.Forms.TextBox();
        this.label34 = new System.Windows.Forms.Label();
        this.label35 = new System.Windows.Forms.Label();
        this.textUsiIDAlt = new System.Windows.Forms.TextBox();
        this.butAddClinicalInfo = new System.Windows.Forms.Button();
        this.label16 = new System.Windows.Forms.Label();
        this.textParentOrderNum = new System.Windows.Forms.TextBox();
        this.checkResultsHandlingN = new System.Windows.Forms.CheckBox();
        this.checkResultsHandlingF = new System.Windows.Forms.CheckBox();
        this.groupBox7 = new System.Windows.Forms.GroupBox();
        this.label17 = new System.Windows.Forms.Label();
        this.textTQ1Stop = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.textTQ1Start = new System.Windows.Forms.TextBox();
        this.groupBox8 = new System.Windows.Forms.GroupBox();
        this.comboSpecimenActionCode = new System.Windows.Forms.ComboBox();
        this.label19 = new System.Windows.Forms.Label();
        this.textResultDateTime = new System.Windows.Forms.TextBox();
        this.label20 = new System.Windows.Forms.Label();
        this.comboResultStatus = new System.Windows.Forms.ComboBox();
        this.label26 = new System.Windows.Forms.Label();
        this.imageListInfoButton = new System.Windows.Forms.ImageList(this.components);
        this.butManageImages = new System.Windows.Forms.Button();
        this.labelManageImages = new System.Windows.Forms.Label();
        this.butViewParent = new OpenDental.UI.Button();
        this.butParentPicker = new OpenDental.UI.Button();
        this.gridResultsCopyTo = new OpenDental.UI.ODGrid();
        this.butPatientPick = new OpenDental.UI.Button();
        this.gridClinicalInformation = new OpenDental.UI.ODGrid();
        this.gridNotes = new OpenDental.UI.ODGrid();
        this.gridSpecimen = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox4.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.groupBox7.SuspendLayout();
        this.groupBox8.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(887, 661);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(806, 661);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 9;
        this.butOk.Text = "Save";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 661);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 3;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.Location = new System.Drawing.Point(881, 466);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(81, 24);
        this.butAdd.TabIndex = 13;
        this.butAdd.Text = "Add Result";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(-4, 14);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(96, 17);
        this.labelPatient.TabIndex = 15;
        this.labelPatient.Text = "Patient";
        this.labelPatient.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelPatient.Visible = false;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(95, 12);
        this.textName.Name = "textName";
        this.textName.ReadOnly = true;
        this.textName.Size = new System.Drawing.Size(193, 20);
        this.textName.TabIndex = 14;
        this.textName.Visible = false;
        this.textName.WordWrap = false;
        //
        // butAddNote
        //
        this.butAddNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddNote.Location = new System.Drawing.Point(881, 359);
        this.butAddNote.Name = "butAddNote";
        this.butAddNote.Size = new System.Drawing.Size(81, 24);
        this.butAddNote.TabIndex = 166;
        this.butAddNote.Text = "Add Notes";
        this.butAddNote.UseVisualStyleBackColor = true;
        this.butAddNote.Click += new System.EventHandler(this.butAddNote_Click);
        //
        // butAddCopyTo
        //
        this.butAddCopyTo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddCopyTo.Enabled = false;
        this.butAddCopyTo.Location = new System.Drawing.Point(881, 389);
        this.butAddCopyTo.Name = "butAddCopyTo";
        this.butAddCopyTo.Size = new System.Drawing.Size(81, 24);
        this.butAddCopyTo.TabIndex = 167;
        this.butAddCopyTo.Text = "Add Copy To";
        this.butAddCopyTo.UseVisualStyleBackColor = true;
        //
        // butAddSpecimens
        //
        this.butAddSpecimens.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddSpecimens.Location = new System.Drawing.Point(881, 496);
        this.butAddSpecimens.Name = "butAddSpecimens";
        this.butAddSpecimens.Size = new System.Drawing.Size(81, 24);
        this.butAddSpecimens.TabIndex = 168;
        this.butAddSpecimens.Text = "Add Spec";
        this.butAddSpecimens.UseVisualStyleBackColor = true;
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.checkAutoID);
        this.groupBox4.Controls.Add(this.label4);
        this.groupBox4.Controls.Add(this.textPlacerOrderNum);
        this.groupBox4.Controls.Add(this.textPlacerOrderUniversalIDType);
        this.groupBox4.Controls.Add(this.label42);
        this.groupBox4.Controls.Add(this.label21);
        this.groupBox4.Controls.Add(this.textPlacerOrderNamespace);
        this.groupBox4.Controls.Add(this.label22);
        this.groupBox4.Controls.Add(this.textPlacerOrderUniversalID);
        this.groupBox4.Location = new System.Drawing.Point(12, 35);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(309, 103);
        this.groupBox4.TabIndex = 0;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Placer Order Number";
        //
        // checkAutoID
        //
        this.checkAutoID.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkAutoID.Location = new System.Drawing.Point(6, 14);
        this.checkAutoID.Name = "checkAutoID";
        this.checkAutoID.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkAutoID.Size = new System.Drawing.Size(69, 20);
        this.checkAutoID.TabIndex = 2;
        this.checkAutoID.Text = "Auto";
        this.checkAutoID.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkAutoID.UseVisualStyleBackColor = true;
        this.checkAutoID.Visible = false;
        this.checkAutoID.CheckedChanged += new System.EventHandler(this.checkAutoID_CheckedChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(52, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(67, 17);
        this.label4.TabIndex = 245;
        this.label4.Text = "ID";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerOrderNum
        //
        this.textPlacerOrderNum.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerOrderNum.Location = new System.Drawing.Point(119, 14);
        this.textPlacerOrderNum.Multiline = true;
        this.textPlacerOrderNum.Name = "textPlacerOrderNum";
        this.textPlacerOrderNum.Size = new System.Drawing.Size(184, 20);
        this.textPlacerOrderNum.TabIndex = 0;
        //
        // textPlacerOrderUniversalIDType
        //
        this.textPlacerOrderUniversalIDType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerOrderUniversalIDType.Location = new System.Drawing.Point(119, 35);
        this.textPlacerOrderUniversalIDType.Multiline = true;
        this.textPlacerOrderUniversalIDType.Name = "textPlacerOrderUniversalIDType";
        this.textPlacerOrderUniversalIDType.Size = new System.Drawing.Size(184, 20);
        this.textPlacerOrderUniversalIDType.TabIndex = 1;
        //
        // label42
        //
        this.label42.Location = new System.Drawing.Point(13, 37);
        this.label42.Name = "label42";
        this.label42.Size = new System.Drawing.Size(106, 17);
        this.label42.TabIndex = 242;
        this.label42.Text = "Identifier Type";
        this.label42.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(13, 58);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(106, 17);
        this.label21.TabIndex = 236;
        this.label21.Text = "Namespace ID";
        this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerOrderNamespace
        //
        this.textPlacerOrderNamespace.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerOrderNamespace.Location = new System.Drawing.Point(119, 56);
        this.textPlacerOrderNamespace.Multiline = true;
        this.textPlacerOrderNamespace.Name = "textPlacerOrderNamespace";
        this.textPlacerOrderNamespace.Size = new System.Drawing.Size(184, 20);
        this.textPlacerOrderNamespace.TabIndex = 2;
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(13, 79);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(106, 17);
        this.label22.TabIndex = 238;
        this.label22.Text = "Universal ID";
        this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerOrderUniversalID
        //
        this.textPlacerOrderUniversalID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerOrderUniversalID.Location = new System.Drawing.Point(119, 77);
        this.textPlacerOrderUniversalID.Multiline = true;
        this.textPlacerOrderUniversalID.Name = "textPlacerOrderUniversalID";
        this.textPlacerOrderUniversalID.Size = new System.Drawing.Size(184, 20);
        this.textPlacerOrderUniversalID.TabIndex = 3;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.textPlacerGroupNum);
        this.groupBox2.Controls.Add(this.textPlacerGroupUniversalIDType);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.Controls.Add(this.textPlacerGroupNamespace);
        this.groupBox2.Controls.Add(this.label10);
        this.groupBox2.Controls.Add(this.textPlacerGroupUniversalID);
        this.groupBox2.Location = new System.Drawing.Point(12, 144);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(309, 103);
        this.groupBox2.TabIndex = 1;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Placer Order Group Number";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 15);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(106, 17);
        this.label2.TabIndex = 245;
        this.label2.Text = "ID";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerGroupNum
        //
        this.textPlacerGroupNum.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerGroupNum.Location = new System.Drawing.Point(119, 13);
        this.textPlacerGroupNum.Multiline = true;
        this.textPlacerGroupNum.Name = "textPlacerGroupNum";
        this.textPlacerGroupNum.Size = new System.Drawing.Size(184, 20);
        this.textPlacerGroupNum.TabIndex = 0;
        //
        // textPlacerGroupUniversalIDType
        //
        this.textPlacerGroupUniversalIDType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerGroupUniversalIDType.Location = new System.Drawing.Point(119, 34);
        this.textPlacerGroupUniversalIDType.Multiline = true;
        this.textPlacerGroupUniversalIDType.Name = "textPlacerGroupUniversalIDType";
        this.textPlacerGroupUniversalIDType.Size = new System.Drawing.Size(184, 20);
        this.textPlacerGroupUniversalIDType.TabIndex = 1;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(13, 36);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(106, 17);
        this.label8.TabIndex = 242;
        this.label8.Text = "Identifier Type";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(13, 57);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(106, 17);
        this.label9.TabIndex = 236;
        this.label9.Text = "Namespace ID";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerGroupNamespace
        //
        this.textPlacerGroupNamespace.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerGroupNamespace.Location = new System.Drawing.Point(119, 55);
        this.textPlacerGroupNamespace.Multiline = true;
        this.textPlacerGroupNamespace.Name = "textPlacerGroupNamespace";
        this.textPlacerGroupNamespace.Size = new System.Drawing.Size(184, 20);
        this.textPlacerGroupNamespace.TabIndex = 2;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(13, 78);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(106, 17);
        this.label10.TabIndex = 238;
        this.label10.Text = "Universal ID";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPlacerGroupUniversalID
        //
        this.textPlacerGroupUniversalID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textPlacerGroupUniversalID.Location = new System.Drawing.Point(119, 76);
        this.textPlacerGroupUniversalID.Multiline = true;
        this.textPlacerGroupUniversalID.Name = "textPlacerGroupUniversalID";
        this.textPlacerGroupUniversalID.Size = new System.Drawing.Size(184, 20);
        this.textPlacerGroupUniversalID.TabIndex = 3;
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butProvPicker);
        this.groupBox5.Controls.Add(this.comboOrderingProvIdType);
        this.groupBox5.Controls.Add(this.label33);
        this.groupBox5.Controls.Add(this.comboOrderingProvNameType);
        this.groupBox5.Controls.Add(this.label32);
        this.groupBox5.Controls.Add(this.label23);
        this.groupBox5.Controls.Add(this.textOrderingProvPrefix);
        this.groupBox5.Controls.Add(this.label24);
        this.groupBox5.Controls.Add(this.textOrderingProvSuffix);
        this.groupBox5.Controls.Add(this.label25);
        this.groupBox5.Controls.Add(this.textOrderingProvMiddleName);
        this.groupBox5.Controls.Add(this.label29);
        this.groupBox5.Controls.Add(this.textOrderingProvFirstName);
        this.groupBox5.Controls.Add(this.label30);
        this.groupBox5.Controls.Add(this.textOrderingProvLastName);
        this.groupBox5.Controls.Add(this.label31);
        this.groupBox5.Controls.Add(this.textOrderingProvIdentifier);
        this.groupBox5.Controls.Add(this.groupBox1);
        this.groupBox5.Location = new System.Drawing.Point(327, 35);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(336, 267);
        this.groupBox5.TabIndex = 3;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Ordering Provider";
        //
        // butProvPicker
        //
        this.butProvPicker.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvPicker.setAutosize(true);
        this.butProvPicker.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvPicker.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvPicker.setCornerRadius(4F);
        this.butProvPicker.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvPicker.Location = new System.Drawing.Point(10, 15);
        this.butProvPicker.Name = "butProvPicker";
        this.butProvPicker.Size = new System.Drawing.Size(29, 25);
        this.butProvPicker.TabIndex = 249;
        this.butProvPicker.Text = "...";
        this.butProvPicker.Click += new System.EventHandler(this.butProvPicker_Click);
        //
        // comboOrderingProvIdType
        //
        this.comboOrderingProvIdType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.comboOrderingProvIdType.FormattingEnabled = true;
        this.comboOrderingProvIdType.Location = new System.Drawing.Point(119, 12);
        this.comboOrderingProvIdType.Name = "comboOrderingProvIdType";
        this.comboOrderingProvIdType.Size = new System.Drawing.Size(203, 21);
        this.comboOrderingProvIdType.TabIndex = 0;
        //
        // label33
        //
        this.label33.Location = new System.Drawing.Point(50, 14);
        this.label33.Name = "label33";
        this.label33.Size = new System.Drawing.Size(70, 17);
        this.label33.TabIndex = 260;
        this.label33.Text = "Identifier Type";
        this.label33.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboOrderingProvNameType
        //
        this.comboOrderingProvNameType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.comboOrderingProvNameType.FormattingEnabled = true;
        this.comboOrderingProvNameType.Location = new System.Drawing.Point(119, 55);
        this.comboOrderingProvNameType.Name = "comboOrderingProvNameType";
        this.comboOrderingProvNameType.Size = new System.Drawing.Size(203, 21);
        this.comboOrderingProvNameType.TabIndex = 2;
        //
        // label32
        //
        this.label32.Location = new System.Drawing.Point(30, 57);
        this.label32.Name = "label32";
        this.label32.Size = new System.Drawing.Size(90, 17);
        this.label32.TabIndex = 258;
        this.label32.Text = "Name Type";
        this.label32.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label23
        //
        this.label23.Location = new System.Drawing.Point(30, 163);
        this.label23.Name = "label23";
        this.label23.Size = new System.Drawing.Size(90, 17);
        this.label23.TabIndex = 257;
        this.label23.Text = "Prefix";
        this.label23.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvPrefix
        //
        this.textOrderingProvPrefix.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvPrefix.Location = new System.Drawing.Point(119, 161);
        this.textOrderingProvPrefix.Name = "textOrderingProvPrefix";
        this.textOrderingProvPrefix.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvPrefix.TabIndex = 7;
        //
        // label24
        //
        this.label24.Location = new System.Drawing.Point(30, 142);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(90, 17);
        this.label24.TabIndex = 255;
        this.label24.Text = "Suffix";
        this.label24.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvSuffix
        //
        this.textOrderingProvSuffix.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvSuffix.Location = new System.Drawing.Point(119, 140);
        this.textOrderingProvSuffix.Name = "textOrderingProvSuffix";
        this.textOrderingProvSuffix.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvSuffix.TabIndex = 6;
        //
        // label25
        //
        this.label25.Location = new System.Drawing.Point(30, 121);
        this.label25.Name = "label25";
        this.label25.Size = new System.Drawing.Size(90, 17);
        this.label25.TabIndex = 253;
        this.label25.Text = "Middle Name";
        this.label25.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvMiddleName
        //
        this.textOrderingProvMiddleName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvMiddleName.Location = new System.Drawing.Point(119, 119);
        this.textOrderingProvMiddleName.Name = "textOrderingProvMiddleName";
        this.textOrderingProvMiddleName.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvMiddleName.TabIndex = 5;
        //
        // label29
        //
        this.label29.Location = new System.Drawing.Point(30, 100);
        this.label29.Name = "label29";
        this.label29.Size = new System.Drawing.Size(90, 17);
        this.label29.TabIndex = 251;
        this.label29.Text = "First Name";
        this.label29.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvFirstName
        //
        this.textOrderingProvFirstName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvFirstName.Location = new System.Drawing.Point(119, 98);
        this.textOrderingProvFirstName.Name = "textOrderingProvFirstName";
        this.textOrderingProvFirstName.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvFirstName.TabIndex = 4;
        //
        // label30
        //
        this.label30.Location = new System.Drawing.Point(30, 79);
        this.label30.Name = "label30";
        this.label30.Size = new System.Drawing.Size(90, 17);
        this.label30.TabIndex = 249;
        this.label30.Text = "Last Name";
        this.label30.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvLastName
        //
        this.textOrderingProvLastName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvLastName.Location = new System.Drawing.Point(119, 77);
        this.textOrderingProvLastName.Name = "textOrderingProvLastName";
        this.textOrderingProvLastName.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvLastName.TabIndex = 3;
        //
        // label31
        //
        this.label31.Location = new System.Drawing.Point(30, 36);
        this.label31.Name = "label31";
        this.label31.Size = new System.Drawing.Size(90, 17);
        this.label31.TabIndex = 247;
        this.label31.Text = "ID";
        this.label31.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvIdentifier
        //
        this.textOrderingProvIdentifier.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvIdentifier.Location = new System.Drawing.Point(119, 34);
        this.textOrderingProvIdentifier.Name = "textOrderingProvIdentifier";
        this.textOrderingProvIdentifier.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvIdentifier.TabIndex = 1;
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.textOrderingProvAAUIDType);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.textOrderingProvAANID);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.textOrderingProvAAUID);
        this.groupBox1.Location = new System.Drawing.Point(4, 178);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(324, 84);
        this.groupBox1.TabIndex = 245;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Assigning Authority";
        //
        // textOrderingProvAAUIDType
        //
        this.textOrderingProvAAUIDType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvAAUIDType.Location = new System.Drawing.Point(115, 15);
        this.textOrderingProvAAUIDType.Multiline = true;
        this.textOrderingProvAAUIDType.Name = "textOrderingProvAAUIDType";
        this.textOrderingProvAAUIDType.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvAAUIDType.TabIndex = 0;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(106, 17);
        this.label1.TabIndex = 242;
        this.label1.Text = "Identifier Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(10, 37);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(106, 17);
        this.label3.TabIndex = 236;
        this.label3.Text = "Namespace ID";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvAANID
        //
        this.textOrderingProvAANID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvAANID.Location = new System.Drawing.Point(115, 36);
        this.textOrderingProvAANID.Multiline = true;
        this.textOrderingProvAANID.Name = "textOrderingProvAANID";
        this.textOrderingProvAANID.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvAANID.TabIndex = 1;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(10, 58);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(106, 17);
        this.label6.TabIndex = 238;
        this.label6.Text = "Universal ID";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOrderingProvAAUID
        //
        this.textOrderingProvAAUID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textOrderingProvAAUID.Location = new System.Drawing.Point(115, 57);
        this.textOrderingProvAAUID.Multiline = true;
        this.textOrderingProvAAUID.Name = "textOrderingProvAAUID";
        this.textOrderingProvAAUID.Size = new System.Drawing.Size(203, 20);
        this.textOrderingProvAAUID.TabIndex = 2;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label7);
        this.groupBox3.Controls.Add(this.textFillerOrderNum);
        this.groupBox3.Controls.Add(this.textFillerOrderUniversalIDType);
        this.groupBox3.Controls.Add(this.label11);
        this.groupBox3.Controls.Add(this.label12);
        this.groupBox3.Controls.Add(this.textFillerOrderNamespace);
        this.groupBox3.Controls.Add(this.label13);
        this.groupBox3.Controls.Add(this.textFillerOrderUniversalID);
        this.groupBox3.Location = new System.Drawing.Point(12, 253);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(309, 103);
        this.groupBox3.TabIndex = 2;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Filler Order Number";
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(13, 16);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(106, 17);
        this.label7.TabIndex = 245;
        this.label7.Text = "ID";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFillerOrderNum
        //
        this.textFillerOrderNum.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textFillerOrderNum.Location = new System.Drawing.Point(119, 14);
        this.textFillerOrderNum.Multiline = true;
        this.textFillerOrderNum.Name = "textFillerOrderNum";
        this.textFillerOrderNum.Size = new System.Drawing.Size(184, 20);
        this.textFillerOrderNum.TabIndex = 0;
        //
        // textFillerOrderUniversalIDType
        //
        this.textFillerOrderUniversalIDType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textFillerOrderUniversalIDType.Location = new System.Drawing.Point(119, 35);
        this.textFillerOrderUniversalIDType.Multiline = true;
        this.textFillerOrderUniversalIDType.Name = "textFillerOrderUniversalIDType";
        this.textFillerOrderUniversalIDType.Size = new System.Drawing.Size(184, 20);
        this.textFillerOrderUniversalIDType.TabIndex = 1;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(13, 37);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(106, 17);
        this.label11.TabIndex = 242;
        this.label11.Text = "Identifier Type";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(13, 58);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(106, 17);
        this.label12.TabIndex = 236;
        this.label12.Text = "Namespace ID";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFillerOrderNamespace
        //
        this.textFillerOrderNamespace.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textFillerOrderNamespace.Location = new System.Drawing.Point(119, 56);
        this.textFillerOrderNamespace.Multiline = true;
        this.textFillerOrderNamespace.Name = "textFillerOrderNamespace";
        this.textFillerOrderNamespace.Size = new System.Drawing.Size(184, 20);
        this.textFillerOrderNamespace.TabIndex = 2;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(13, 79);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(106, 17);
        this.label13.TabIndex = 238;
        this.label13.Text = "Universal ID";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFillerOrderUniversalID
        //
        this.textFillerOrderUniversalID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textFillerOrderUniversalID.Location = new System.Drawing.Point(119, 77);
        this.textFillerOrderUniversalID.Multiline = true;
        this.textFillerOrderUniversalID.Name = "textFillerOrderUniversalID";
        this.textFillerOrderUniversalID.Size = new System.Drawing.Size(184, 20);
        this.textFillerOrderUniversalID.TabIndex = 3;
        //
        // groupBox6
        //
        this.groupBox6.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox6.Controls.Add(this.textObservationDateTimeStart);
        this.groupBox6.Controls.Add(this.label5);
        this.groupBox6.Controls.Add(this.label27);
        this.groupBox6.Controls.Add(this.textObservationDateTimeEnd);
        this.groupBox6.Controls.Add(this.butServicePicker);
        this.groupBox6.Controls.Add(this.label44);
        this.groupBox6.Controls.Add(this.textUsiCodeSystemNameAlt);
        this.groupBox6.Controls.Add(this.label43);
        this.groupBox6.Controls.Add(this.textUsiCodeSystemName);
        this.groupBox6.Controls.Add(this.label14);
        this.groupBox6.Controls.Add(this.textUsiText);
        this.groupBox6.Controls.Add(this.label15);
        this.groupBox6.Controls.Add(this.label36);
        this.groupBox6.Controls.Add(this.textUsiID);
        this.groupBox6.Controls.Add(this.textUsiTextOriginal);
        this.groupBox6.Controls.Add(this.textUsiTextAlt);
        this.groupBox6.Controls.Add(this.label34);
        this.groupBox6.Controls.Add(this.label35);
        this.groupBox6.Controls.Add(this.textUsiIDAlt);
        this.groupBox6.Location = new System.Drawing.Point(672, 35);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(290, 212);
        this.groupBox6.TabIndex = 5;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Service Identifier (LOINC Codes)";
        //
        // textObservationDateTimeStart
        //
        this.textObservationDateTimeStart.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textObservationDateTimeStart.Location = new System.Drawing.Point(134, 164);
        this.textObservationDateTimeStart.Name = "textObservationDateTimeStart";
        this.textObservationDateTimeStart.Size = new System.Drawing.Size(151, 20);
        this.textObservationDateTimeStart.TabIndex = 264;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 166);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(127, 17);
        this.label5.TabIndex = 267;
        this.label5.Text = "Date/Time Start";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label27
        //
        this.label27.Location = new System.Drawing.Point(6, 187);
        this.label27.Name = "label27";
        this.label27.Size = new System.Drawing.Size(127, 17);
        this.label27.TabIndex = 266;
        this.label27.Text = "Date/Time Stop";
        this.label27.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textObservationDateTimeEnd
        //
        this.textObservationDateTimeEnd.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textObservationDateTimeEnd.Location = new System.Drawing.Point(134, 185);
        this.textObservationDateTimeEnd.Name = "textObservationDateTimeEnd";
        this.textObservationDateTimeEnd.Size = new System.Drawing.Size(151, 20);
        this.textObservationDateTimeEnd.TabIndex = 265;
        //
        // butServicePicker
        //
        this.butServicePicker.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butServicePicker.setAutosize(true);
        this.butServicePicker.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butServicePicker.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butServicePicker.setCornerRadius(4F);
        this.butServicePicker.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butServicePicker.Location = new System.Drawing.Point(6, 14);
        this.butServicePicker.Name = "butServicePicker";
        this.butServicePicker.Size = new System.Drawing.Size(29, 25);
        this.butServicePicker.TabIndex = 255;
        this.butServicePicker.Text = "...";
        this.butServicePicker.Click += new System.EventHandler(this.butServicePicker_Click);
        //
        // label44
        //
        this.label44.Location = new System.Drawing.Point(6, 81);
        this.label44.Name = "label44";
        this.label44.Size = new System.Drawing.Size(127, 17);
        this.label44.TabIndex = 256;
        this.label44.Text = "Alt Code System";
        this.label44.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsiCodeSystemNameAlt
        //
        this.textUsiCodeSystemNameAlt.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiCodeSystemNameAlt.Location = new System.Drawing.Point(134, 80);
        this.textUsiCodeSystemNameAlt.Name = "textUsiCodeSystemNameAlt";
        this.textUsiCodeSystemNameAlt.Size = new System.Drawing.Size(151, 20);
        this.textUsiCodeSystemNameAlt.TabIndex = 3;
        //
        // label43
        //
        this.label43.Location = new System.Drawing.Point(6, 18);
        this.label43.Name = "label43";
        this.label43.Size = new System.Drawing.Size(127, 17);
        this.label43.TabIndex = 254;
        this.label43.Text = "Code System";
        this.label43.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsiCodeSystemName
        //
        this.textUsiCodeSystemName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiCodeSystemName.Location = new System.Drawing.Point(134, 17);
        this.textUsiCodeSystemName.Name = "textUsiCodeSystemName";
        this.textUsiCodeSystemName.Size = new System.Drawing.Size(151, 20);
        this.textUsiCodeSystemName.TabIndex = 0;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(6, 39);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(127, 17);
        this.label14.TabIndex = 2;
        this.label14.Text = "Observation ID";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsiText
        //
        this.textUsiText.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiText.Location = new System.Drawing.Point(134, 59);
        this.textUsiText.Multiline = true;
        this.textUsiText.Name = "textUsiText";
        this.textUsiText.Size = new System.Drawing.Size(151, 20);
        this.textUsiText.TabIndex = 2;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(5, 60);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(128, 17);
        this.label15.TabIndex = 228;
        this.label15.Text = "Observation Text";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label36
        //
        this.label36.Location = new System.Drawing.Point(5, 145);
        this.label36.Name = "label36";
        this.label36.Size = new System.Drawing.Size(128, 17);
        this.label36.TabIndex = 250;
        this.label36.Text = "Original Text";
        this.label36.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsiID
        //
        this.textUsiID.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiID.Location = new System.Drawing.Point(134, 38);
        this.textUsiID.Name = "textUsiID";
        this.textUsiID.Size = new System.Drawing.Size(151, 20);
        this.textUsiID.TabIndex = 1;
        //
        // textUsiTextOriginal
        //
        this.textUsiTextOriginal.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiTextOriginal.Location = new System.Drawing.Point(134, 143);
        this.textUsiTextOriginal.Multiline = true;
        this.textUsiTextOriginal.Name = "textUsiTextOriginal";
        this.textUsiTextOriginal.Size = new System.Drawing.Size(151, 20);
        this.textUsiTextOriginal.TabIndex = 6;
        //
        // textUsiTextAlt
        //
        this.textUsiTextAlt.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiTextAlt.Location = new System.Drawing.Point(134, 122);
        this.textUsiTextAlt.Multiline = true;
        this.textUsiTextAlt.Name = "textUsiTextAlt";
        this.textUsiTextAlt.Size = new System.Drawing.Size(151, 20);
        this.textUsiTextAlt.TabIndex = 5;
        //
        // label34
        //
        this.label34.Location = new System.Drawing.Point(6, 102);
        this.label34.Name = "label34";
        this.label34.Size = new System.Drawing.Size(127, 17);
        this.label34.TabIndex = 246;
        this.label34.Text = "Alt Observation ID";
        this.label34.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label35
        //
        this.label35.Location = new System.Drawing.Point(5, 123);
        this.label35.Name = "label35";
        this.label35.Size = new System.Drawing.Size(128, 17);
        this.label35.TabIndex = 248;
        this.label35.Text = "Alt Observation Text";
        this.label35.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsiIDAlt
        //
        this.textUsiIDAlt.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textUsiIDAlt.Location = new System.Drawing.Point(134, 101);
        this.textUsiIDAlt.Name = "textUsiIDAlt";
        this.textUsiIDAlt.Size = new System.Drawing.Size(151, 20);
        this.textUsiIDAlt.TabIndex = 4;
        //
        // butAddClinicalInfo
        //
        this.butAddClinicalInfo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddClinicalInfo.Enabled = false;
        this.butAddClinicalInfo.Location = new System.Drawing.Point(881, 419);
        this.butAddClinicalInfo.Name = "butAddClinicalInfo";
        this.butAddClinicalInfo.Size = new System.Drawing.Size(81, 24);
        this.butAddClinicalInfo.TabIndex = 256;
        this.butAddClinicalInfo.Text = "Add C. Info";
        this.butAddClinicalInfo.UseVisualStyleBackColor = true;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(356, 14);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(96, 17);
        this.label16.TabIndex = 258;
        this.label16.Text = "Parent Result";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textParentOrderNum
        //
        this.textParentOrderNum.Location = new System.Drawing.Point(452, 13);
        this.textParentOrderNum.Name = "textParentOrderNum";
        this.textParentOrderNum.ReadOnly = true;
        this.textParentOrderNum.Size = new System.Drawing.Size(157, 20);
        this.textParentOrderNum.TabIndex = 257;
        this.textParentOrderNum.WordWrap = false;
        //
        // checkResultsHandlingN
        //
        this.checkResultsHandlingN.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkResultsHandlingN.Location = new System.Drawing.Point(155, 13);
        this.checkResultsHandlingN.Name = "checkResultsHandlingN";
        this.checkResultsHandlingN.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkResultsHandlingN.Size = new System.Drawing.Size(173, 20);
        this.checkResultsHandlingN.TabIndex = 1;
        this.checkResultsHandlingN.Text = "Notify provider when ready";
        this.checkResultsHandlingN.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkResultsHandlingN.UseVisualStyleBackColor = true;
        //
        // checkResultsHandlingF
        //
        this.checkResultsHandlingF.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkResultsHandlingF.Location = new System.Drawing.Point(6, 13);
        this.checkResultsHandlingF.Name = "checkResultsHandlingF";
        this.checkResultsHandlingF.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkResultsHandlingF.Size = new System.Drawing.Size(143, 20);
        this.checkResultsHandlingF.TabIndex = 0;
        this.checkResultsHandlingF.Text = "Film results with patient";
        this.checkResultsHandlingF.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkResultsHandlingF.UseVisualStyleBackColor = true;
        //
        // groupBox7
        //
        this.groupBox7.Controls.Add(this.checkResultsHandlingF);
        this.groupBox7.Controls.Add(this.checkResultsHandlingN);
        this.groupBox7.Location = new System.Drawing.Point(327, 308);
        this.groupBox7.Name = "groupBox7";
        this.groupBox7.Size = new System.Drawing.Size(336, 38);
        this.groupBox7.TabIndex = 4;
        this.groupBox7.TabStop = false;
        this.groupBox7.Text = "Results Handling";
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(6, 15);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(127, 17);
        this.label17.TabIndex = 263;
        this.label17.Text = "Date/Time Start";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTQ1Stop
        //
        this.textTQ1Stop.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textTQ1Stop.Location = new System.Drawing.Point(134, 34);
        this.textTQ1Stop.Name = "textTQ1Stop";
        this.textTQ1Stop.Size = new System.Drawing.Size(151, 20);
        this.textTQ1Stop.TabIndex = 1;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(6, 36);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(127, 17);
        this.label18.TabIndex = 261;
        this.label18.Text = "Date/Time Stop";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTQ1Start
        //
        this.textTQ1Start.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textTQ1Start.Location = new System.Drawing.Point(134, 13);
        this.textTQ1Start.Name = "textTQ1Start";
        this.textTQ1Start.Size = new System.Drawing.Size(151, 20);
        this.textTQ1Start.TabIndex = 0;
        //
        // groupBox8
        //
        this.groupBox8.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox8.Controls.Add(this.textTQ1Start);
        this.groupBox8.Controls.Add(this.label17);
        this.groupBox8.Controls.Add(this.label18);
        this.groupBox8.Controls.Add(this.textTQ1Stop);
        this.groupBox8.Location = new System.Drawing.Point(672, 252);
        this.groupBox8.Name = "groupBox8";
        this.groupBox8.Size = new System.Drawing.Size(290, 59);
        this.groupBox8.TabIndex = 262;
        this.groupBox8.TabStop = false;
        this.groupBox8.Text = "TQ1";
        //
        // comboSpecimenActionCode
        //
        this.comboSpecimenActionCode.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.comboSpecimenActionCode.FormattingEnabled = true;
        this.comboSpecimenActionCode.Location = new System.Drawing.Point(669, 634);
        this.comboSpecimenActionCode.Name = "comboSpecimenActionCode";
        this.comboSpecimenActionCode.Size = new System.Drawing.Size(288, 21);
        this.comboSpecimenActionCode.TabIndex = 8;
        //
        // label19
        //
        this.label19.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label19.Location = new System.Drawing.Point(669, 614);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(164, 17);
        this.label19.TabIndex = 262;
        this.label19.Text = "Specimen Action Code:";
        this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textResultDateTime
        //
        this.textResultDateTime.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textResultDateTime.Location = new System.Drawing.Point(822, 7);
        this.textResultDateTime.Name = "textResultDateTime";
        this.textResultDateTime.Size = new System.Drawing.Size(135, 20);
        this.textResultDateTime.TabIndex = 6;
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(721, 9);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(95, 17);
        this.label20.TabIndex = 265;
        this.label20.Text = "Last Update";
        this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboResultStatus
        //
        this.comboResultStatus.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.comboResultStatus.FormattingEnabled = true;
        this.comboResultStatus.Location = new System.Drawing.Point(131, 634);
        this.comboResultStatus.Name = "comboResultStatus";
        this.comboResultStatus.Size = new System.Drawing.Size(532, 21);
        this.comboResultStatus.TabIndex = 7;
        //
        // label26
        //
        this.label26.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label26.Location = new System.Drawing.Point(12, 635);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(119, 17);
        this.label26.TabIndex = 266;
        this.label26.Text = "Results Status";
        this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // imageListInfoButton
        //
        this.imageListInfoButton.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListInfoButton.ImageStream")));
        this.imageListInfoButton.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListInfoButton.Images.SetKeyName(0, "iButton_16px.png");
        //
        // butManageImages
        //
        this.butManageImages.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butManageImages.Location = new System.Drawing.Point(327, 661);
        this.butManageImages.Name = "butManageImages";
        this.butManageImages.Size = new System.Drawing.Size(93, 24);
        this.butManageImages.TabIndex = 264;
        this.butManageImages.Text = "Manage Images";
        this.butManageImages.UseVisualStyleBackColor = true;
        this.butManageImages.Click += new System.EventHandler(this.butManageImages_Click);
        //
        // labelManageImages
        //
        this.labelManageImages.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelManageImages.Location = new System.Drawing.Point(426, 665);
        this.labelManageImages.Name = "labelManageImages";
        this.labelManageImages.Size = new System.Drawing.Size(374, 17);
        this.labelManageImages.TabIndex = 269;
        this.labelManageImages.Text = "Must save lab order one time before managing images";
        this.labelManageImages.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butViewParent
        //
        this.butViewParent.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butViewParent.setAutosize(true);
        this.butViewParent.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butViewParent.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butViewParent.setCornerRadius(4F);
        this.butViewParent.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butViewParent.Location = new System.Drawing.Point(333, 10);
        this.butViewParent.Name = "butViewParent";
        this.butViewParent.Size = new System.Drawing.Size(46, 25);
        this.butViewParent.TabIndex = 268;
        this.butViewParent.Text = "View";
        this.butViewParent.Click += new System.EventHandler(this.butViewParent_Click);
        //
        // butParentPicker
        //
        this.butParentPicker.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butParentPicker.setAutosize(true);
        this.butParentPicker.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butParentPicker.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butParentPicker.setCornerRadius(4F);
        this.butParentPicker.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butParentPicker.Location = new System.Drawing.Point(615, 10);
        this.butParentPicker.Name = "butParentPicker";
        this.butParentPicker.Size = new System.Drawing.Size(29, 25);
        this.butParentPicker.TabIndex = 259;
        this.butParentPicker.Text = "...";
        this.butParentPicker.Visible = false;
        //
        // gridResultsCopyTo
        //
        this.gridResultsCopyTo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.gridResultsCopyTo.setHScrollVisible(false);
        this.gridResultsCopyTo.Location = new System.Drawing.Point(446, 359);
        this.gridResultsCopyTo.Name = "gridResultsCopyTo";
        this.gridResultsCopyTo.setScrollValue(0);
        this.gridResultsCopyTo.Size = new System.Drawing.Size(217, 101);
        this.gridResultsCopyTo.TabIndex = 255;
        this.gridResultsCopyTo.setTitle("Results Copy To");
        this.gridResultsCopyTo.setTranslationName(null);
        //
        // butPatientPick
        //
        this.butPatientPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatientPick.setAutosize(true);
        this.butPatientPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatientPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatientPick.setCornerRadius(4F);
        this.butPatientPick.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPatientPick.Location = new System.Drawing.Point(294, 9);
        this.butPatientPick.Name = "butPatientPick";
        this.butPatientPick.Size = new System.Drawing.Size(29, 25);
        this.butPatientPick.TabIndex = 228;
        this.butPatientPick.Text = "...";
        this.butPatientPick.Visible = false;
        //
        // gridClinicalInformation
        //
        this.gridClinicalInformation.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.gridClinicalInformation.setHScrollVisible(false);
        this.gridClinicalInformation.Location = new System.Drawing.Point(669, 359);
        this.gridClinicalInformation.Name = "gridClinicalInformation";
        this.gridClinicalInformation.setScrollValue(0);
        this.gridClinicalInformation.Size = new System.Drawing.Size(206, 101);
        this.gridClinicalInformation.TabIndex = 11;
        this.gridClinicalInformation.setTitle("Clinical Information");
        this.gridClinicalInformation.setTranslationName(null);
        //
        // gridNotes
        //
        this.gridNotes.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridNotes.setHScrollVisible(false);
        this.gridNotes.Location = new System.Drawing.Point(12, 359);
        this.gridNotes.Name = "gridNotes";
        this.gridNotes.setScrollValue(0);
        this.gridNotes.Size = new System.Drawing.Size(428, 101);
        this.gridNotes.TabIndex = 20;
        this.gridNotes.setTitle("Notes");
        this.gridNotes.setTranslationName(null);
        this.gridNotes.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridNotes.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridNotes_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridSpecimen
        //
        this.gridSpecimen.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridSpecimen.setHScrollVisible(false);
        this.gridSpecimen.Location = new System.Drawing.Point(669, 466);
        this.gridSpecimen.Name = "gridSpecimen";
        this.gridSpecimen.setScrollValue(0);
        this.gridSpecimen.Size = new System.Drawing.Size(206, 145);
        this.gridSpecimen.TabIndex = 19;
        this.gridSpecimen.setTitle("Specimens");
        this.gridSpecimen.setTranslationName(null);
        this.gridSpecimen.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridSpecimen.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridSpecimen_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 466);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(651, 165);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Lab Results");
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
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormEhrLabOrderEdit2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(974, 696);
        this.Controls.Add(this.labelManageImages);
        this.Controls.Add(this.butManageImages);
        this.Controls.Add(this.butViewParent);
        this.Controls.Add(this.comboResultStatus);
        this.Controls.Add(this.label26);
        this.Controls.Add(this.textResultDateTime);
        this.Controls.Add(this.label20);
        this.Controls.Add(this.comboSpecimenActionCode);
        this.Controls.Add(this.label19);
        this.Controls.Add(this.groupBox8);
        this.Controls.Add(this.groupBox7);
        this.Controls.Add(this.butParentPicker);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.textParentOrderNum);
        this.Controls.Add(this.butAddClinicalInfo);
        this.Controls.Add(this.gridResultsCopyTo);
        this.Controls.Add(this.groupBox6);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.butPatientPick);
        this.Controls.Add(this.butAddSpecimens);
        this.Controls.Add(this.butAddCopyTo);
        this.Controls.Add(this.butAddNote);
        this.Controls.Add(this.gridClinicalInformation);
        this.Controls.Add(this.gridNotes);
        this.Controls.Add(this.gridSpecimen);
        this.Controls.Add(this.labelPatient);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(939, 679);
        this.Name = "FormEhrLabOrderEdit2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Order Edit";
        this.Load += new System.EventHandler(this.FormLabPanelEdit_Load);
        this.groupBox4.ResumeLayout(false);
        this.groupBox4.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox5.ResumeLayout(false);
        this.groupBox5.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupBox6.ResumeLayout(false);
        this.groupBox6.PerformLayout();
        this.groupBox7.ResumeLayout(false);
        this.groupBox8.ResumeLayout(false);
        this.groupBox8.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODGrid gridSpecimen;
    private OpenDental.UI.ODGrid gridNotes;
    private OpenDental.UI.ODGrid gridClinicalInformation;
    private System.Windows.Forms.Button butAddNote = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAddCopyTo = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAddSpecimens = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butPatientPick;
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerOrderNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPlacerOrderUniversalIDType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label42 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label21 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerOrderNamespace = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label22 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerOrderUniversalID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerGroupNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPlacerGroupUniversalIDType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerGroupNamespace = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlacerGroupUniversalID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butProvPicker;
    private System.Windows.Forms.ComboBox comboOrderingProvIdType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label33 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboOrderingProvNameType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label32 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label23 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvPrefix = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label24 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvSuffix = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label25 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvMiddleName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label29 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvFirstName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label30 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvLastName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label31 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvIdentifier = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textOrderingProvAAUIDType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvAANID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrderingProvAAUID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFillerOrderNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textFillerOrderUniversalIDType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFillerOrderNamespace = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFillerOrderUniversalID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox6 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label44 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsiCodeSystemNameAlt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label43 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsiCodeSystemName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsiText = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label36 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsiID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textUsiTextOriginal = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textUsiTextAlt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label34 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label35 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsiIDAlt = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butServicePicker;
    private OpenDental.UI.ODGrid gridResultsCopyTo;
    private System.Windows.Forms.Button butAddClinicalInfo = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butParentPicker;
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textParentOrderNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkResultsHandlingN = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkResultsHandlingF = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox7 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTQ1Stop = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label18 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTQ1Start = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox8 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ComboBox comboSpecimenActionCode = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label19 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textResultDateTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label20 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboResultStatus = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label26 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butViewParent;
    private System.Windows.Forms.ImageList imageListInfoButton = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.Button butManageImages = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelManageImages = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkAutoID = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textObservationDateTimeStart = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label27 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textObservationDateTimeEnd = new System.Windows.Forms.TextBox();
}


