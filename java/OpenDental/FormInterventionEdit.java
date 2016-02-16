//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormMedPat;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
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
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.Medications;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDental.FormInterventionEdit;

public class FormInterventionEdit  extends Form 
{

    public Intervention InterventionCur;
    /**
    * This bool will determine if we show every intervention type we support or only a specific subset related to the form we launch from.  Currently only set to true when adding an intervention from FormInterventions when pressing the Add button.
    */
    public boolean IsAllTypes = new boolean();
    /**
    * Do not let them edit historical interventions, the OK button will be disabled if this is false.
    */
    public boolean IsSelectionMode = new boolean();
    private List<EhrCode> listCodes = new List<EhrCode>();
    private String Description = new String();
    private Dictionary<String, String> dictValueCodeSets = new Dictionary<String, String>();
    public FormInterventionEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    /**
    * 
    */
    private void formInterventionEdit_Load(Object sender, EventArgs e) throws Exception {
        butOK.Enabled = IsSelectionMode;
        //OK button only enabled in selection mode
        dictValueCodeSets = new Dictionary<String, String>();
        comboCodeSet.Items.Add("All");
        if (IsAllTypes || InterventionCur.CodeSet == InterventionCodeSet.AboveNormalWeight)
        {
            comboCodeSet.Items.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Follow-up");
            dictValueCodeSets.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Follow-up", "2.16.840.1.113883.3.600.1.1525");
            comboCodeSet.Items.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Referral");
            dictValueCodeSets.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Referral", "2.16.840.1.113883.3.600.1.1527");
            comboCodeSet.Items.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Medication");
            dictValueCodeSets.Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Medication", "2.16.840.1.113883.3.600.1.1498");
        }
         
        if (IsAllTypes || InterventionCur.CodeSet == InterventionCodeSet.BelowNormalWeight)
        {
            comboCodeSet.Items.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Follow-up");
            dictValueCodeSets.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Follow-up", "2.16.840.1.113883.3.600.1.1528");
            comboCodeSet.Items.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Referral");
            dictValueCodeSets.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Referral", "2.16.840.1.113883.3.600.1.1527");
            comboCodeSet.Items.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Medication");
            dictValueCodeSets.Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Medication", "2.16.840.1.113883.3.600.1.1499");
        }
         
        if (IsAllTypes || InterventionCur.CodeSet == InterventionCodeSet.Nutrition || InterventionCur.CodeSet == InterventionCodeSet.PhysicalActivity)
        {
            comboCodeSet.Items.Add(InterventionCodeSet.Nutrition.ToString() + " Counseling");
            dictValueCodeSets.Add(InterventionCodeSet.Nutrition.ToString() + " Counseling", "2.16.840.1.113883.3.464.1003.195.12.1003");
            comboCodeSet.Items.Add(InterventionCodeSet.PhysicalActivity.ToString() + " Counseling");
            dictValueCodeSets.Add(InterventionCodeSet.PhysicalActivity.ToString() + " Counseling", "2.16.840.1.113883.3.464.1003.118.12.1035");
        }
         
        if (IsAllTypes || InterventionCur.CodeSet == InterventionCodeSet.TobaccoCessation)
        {
            comboCodeSet.Items.Add(InterventionCodeSet.TobaccoCessation.ToString() + " Counseling");
            dictValueCodeSets.Add(InterventionCodeSet.TobaccoCessation.ToString() + " Counseling", "2.16.840.1.113883.3.526.3.509");
            comboCodeSet.Items.Add(InterventionCodeSet.TobaccoCessation.ToString() + " Medication");
            dictValueCodeSets.Add(InterventionCodeSet.TobaccoCessation.ToString() + " Medication", "2.16.840.1.113883.3.526.3.1190");
        }
         
        if (IsAllTypes || InterventionCur.CodeSet == InterventionCodeSet.Dialysis)
        {
            comboCodeSet.Items.Add(InterventionCodeSet.Dialysis.ToString() + " Education");
            dictValueCodeSets.Add(InterventionCodeSet.Dialysis.ToString() + " Education", "2.16.840.1.113883.3.464.1003.109.12.1016");
            comboCodeSet.Items.Add(InterventionCodeSet.Dialysis.ToString() + " Related Services");
            dictValueCodeSets.Add(InterventionCodeSet.Dialysis.ToString() + " Related Services", "2.16.840.1.113883.3.464.1003.109.12.1015");
        }
         
        comboCodeSet.SelectedIndex = 0;
        //need to set the comboCodeSet based on InterventionCur sent in
        int codeSetIdx = 0;
        for (Object __dummyForeachVar0 : dictValueCodeSets.Values)
        {
            //this will be the index to set the comboCodeSet to if found in a subset of codes, otherwise defaults to All (index 0).
            String val = (String)__dummyForeachVar0;
            codeSetIdx++;
            listCodes = EhrCodes.GetForValueSetOIDs(new List<String>{ val }, true);
            for (int i = 0;i < listCodes.Count;i++)
            {
                if (StringSupport.equals(listCodes[i].CodeValue, InterventionCur.CodeValue) && StringSupport.equals(listCodes[i].CodeSystem, InterventionCur.CodeSystem))
                {
                    comboCodeSet.SelectedIndex = codeSetIdx;
                    break;
                }
                 
            }
        }
        textDate.Text = InterventionCur.DateEntry.ToShortDateString();
        textNote.Text = InterventionCur.Note;
        Description = "";
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Code",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("CodeSystem",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        String selectedValue = comboCodeSet.SelectedItem.ToString();
        List<String> listValSetOIDs = new List<String>();
        if (StringSupport.equals(selectedValue, "All"))
        {
            listValSetOIDs = new List<String>(dictValueCodeSets.Values);
        }
        else
        {
            //this will limit the codes to only one value set oid
            listValSetOIDs.Add(dictValueCodeSets[selectedValue]);
        } 
        listCodes = EhrCodes.GetForValueSetOIDs(listValSetOIDs, true);
        //these codes will exist in the corresponding table or will not be in the list
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        int selectedIdx = -1;
        for (int i = 0;i < listCodes.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listCodes[i].CodeValue);
            row.getCells().Add(listCodes[i].CodeSystem);
            //Retrieve description from the associated table
            String descript = "";
            CodeSystem __dummyScrutVar0 = listCodes[i].CodeSystem;
            if (__dummyScrutVar0.equals("CPT"))
            {
                Cpt cCur = Cpts.GetByCode(listCodes[i].CodeValue);
                if (cCur != null)
                {
                    descript = cCur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("HCPCS"))
            {
                Hcpcs hCur = Hcpcses.GetByCode(listCodes[i].CodeValue);
                if (hCur != null)
                {
                    descript = hCur.DescriptionShort;
                }
                 
            }
            else if (__dummyScrutVar0.equals("ICD9CM"))
            {
                ICD9 i9Cur = ICD9s.GetByCode(listCodes[i].CodeValue);
                if (i9Cur != null)
                {
                    descript = i9Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("ICD10CM"))
            {
                Icd10 i10Cur = Icd10s.GetByCode(listCodes[i].CodeValue);
                if (i10Cur != null)
                {
                    descript = i10Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("RXNORM"))
            {
                descript = RxNorms.GetDescByRxCui(listCodes[i].CodeValue);
            }
            else if (__dummyScrutVar0.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(listCodes[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
                  
            row.getCells().add(descript);
            gridMain.getRows().add(row);
            if (StringSupport.equals(listCodes[i].CodeValue, InterventionCur.CodeValue) && StringSupport.equals(listCodes[i].CodeSystem, InterventionCur.CodeSystem))
            {
                selectedIdx = i;
            }
             
        }
        gridMain.endUpdate();
        if (selectedIdx > -1)
        {
            gridMain.setSelected(selectedIdx,true);
            gridMain.scrollToIndex(selectedIdx);
        }
         
    }

    private void comboCodeSet_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (InterventionCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        Interventions.delete(InterventionCur.InterventionNum);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //validate--------------------------------------
        DateTime date = new DateTime();
        if (StringSupport.equals(textDate.Text, ""))
        {
            MsgBox.show(this,"Please enter a date.");
            return ;
        }
         
        try
        {
            date = DateTime.Parse(textDate.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix date first.");
            return ;
        }

        String codeVal = "";
        String codeSys = "";
        if (gridMain.getSelectedIndex() == -1)
        {
            //no intervention code selected
            MsgBox.show(this,"You must select a code for this intervention.");
            return ;
        }
        else
        {
            codeVal = listCodes[gridMain.getSelectedIndex()].CodeValue;
            codeSys = listCodes[gridMain.getSelectedIndex()].CodeSystem;
            Description = gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getCells()[2].Text;
        } 
        //save--------------------------------------
        //Intervention grid may contain medications, have to insert a new med if necessary and load FormMedPat for user to input data
        if (StringSupport.equals(codeSys, "RXNORM"))
        {
            //codeVal will be RxCui of medication, see if it already exists in Medication table
            Medication medCur = Medications.GetMedicationFromDbByRxCui(PIn.Long(codeVal));
            if (medCur == null)
            {
                //no med with this RxCui, create one
                medCur = new Medication();
                Medications.insert(medCur);
                //so that we will have the primary key
                medCur.GenericNum = medCur.MedicationNum;
                medCur.RxCui = PIn.Long(codeVal);
                medCur.MedName = RxNorms.getDescByRxCui(codeVal);
                Medications.update(medCur);
                Medications.refresh();
            }
             
            //refresh cache to include new medication
            MedicationPat medPatCur = new MedicationPat();
            medPatCur.PatNum = InterventionCur.PatNum;
            medPatCur.ProvNum = InterventionCur.ProvNum;
            medPatCur.MedicationNum = medCur.MedicationNum;
            medPatCur.RxCui = medCur.RxCui;
            medPatCur.DateStart = date;
            FormMedPat FormMP = new FormMedPat();
            FormMP.MedicationPatCur = medPatCur;
            FormMP.IsNew = true;
            FormMP.ShowDialog();
            if (FormMP.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            if (FormMP.MedicationPatCur.DateStart.Date < InterventionCur.DateEntry.AddMonths(-6).Date || FormMP.MedicationPatCur.DateStart.Date > InterventionCur.DateEntry.Date)
            {
                MsgBox.show(this,"The medication order just entered is not within the 6 months prior to the date of this intervention.  You can modify the date of the medication order in the patient's medical history section.");
            }
             
            DialogResult = DialogResult.OK;
            return ;
        }
         
        InterventionCur.DateEntry = date;
        InterventionCur.CodeValue = codeVal;
        InterventionCur.CodeSystem = codeSys;
        InterventionCur.Note = textNote.Text;
        String selectedCodeSet = comboCodeSet.SelectedItem.ToString().Split(new char[]{ ' ' }, StringSplitOptions.RemoveEmptyEntries)[0];
        if (IsAllTypes)
        {
            //CodeSet will be set by calling function unless showing all types, in which case we need to determine which InterventionCodeSet to assign
            if (StringSupport.equals(selectedCodeSet, "All"))
            {
                //All types showing and set to All, have to determine which InterventionCodeSet this code belongs to
                List<String> listVSFound = new List<String>();
                for (Object __dummyForeachVar1 : dictValueCodeSets)
                {
                    KeyValuePair<String, String> kvp = (KeyValuePair<String, String>)__dummyForeachVar1;
                    List<EhrCode> listCodes = EhrCodes.GetForValueSetOIDs(new List<String>{ kvp.Value }, true);
                    for (int i = 0;i < listCodes.Count;i++)
                    {
                        if (StringSupport.equals(listCodes[i].CodeValue, codeVal))
                        {
                            listVSFound.Add(kvp.Key);
                            break;
                        }
                         
                    }
                }
                if (listVSFound.Count > 1)
                {
                    //Selected code found in more than one value set, ask the user which InterventionCodeSet to assign to this intervention
                    InputBox chooseSet = new InputBox(Lan.g(this,"The selected code belongs to more than one intervention code set.  Select the code set to assign to this intervention from the list below."), listVSFound);
                    if (chooseSet.ShowDialog() != DialogResult.OK)
                    {
                        return ;
                    }
                     
                    if (chooseSet.comboSelection.SelectedIndex == -1)
                    {
                        MsgBox.show(this,"You must select an intervention code set for the selected code.");
                        return ;
                    }
                     
                    selectedCodeSet = chooseSet.comboSelection.SelectedItem.ToString().Split(new char[]{ ' ' }, StringSplitOptions.RemoveEmptyEntries)[0];
                }
                else
                {
                    //the code must belong to at least one value set, since count in listVSFound is not greater than 1, it must be a code from exactly one set, use that for the InterventionCodeSet
                    selectedCodeSet = listVSFound[0].Split(new char[]{ ' ' }, StringSplitOptions.RemoveEmptyEntries)[0];
                } 
            }
             
            InterventionCur.CodeSet = (InterventionCodeSet)Enum.Parse(InterventionCodeSet.class, selectedCodeSet);
        }
        else //Nutrition is used for both nutrition and physical activity counseling for children, we have to determine which set this code belongs to
        if (InterventionCur.CodeSet == InterventionCodeSet.Nutrition && !StringSupport.equals(selectedCodeSet, "Nutrition"))
        {
            //Nutrition set by calling form, user is showing all or physical activity codes only
            if (StringSupport.equals(selectedCodeSet, "All"))
            {
                //showing all codes from Nutrition and PhysicalActivity interventions, determine which set it belongs to
                //No codes exist in both code sets, so if it is not in the PhysicalActivity code set, we can safely assume this is a Nutrition intervention
                List<EhrCode> listCodes = EhrCodes.GetForValueSetOIDs(new List<String>{ dictValueCodeSets[InterventionCodeSet.PhysicalActivity.ToString() + " Counseling"] }, true);
                for (int i = 0;i < listCodes.Count;i++)
                {
                    if (StringSupport.equals(listCodes[i].CodeValue, codeVal))
                    {
                        InterventionCur.CodeSet = InterventionCodeSet.PhysicalActivity;
                        break;
                    }
                     
                }
            }
            else
            {
                InterventionCur.CodeSet = InterventionCodeSet.PhysicalActivity;
            } 
        }
        else
        {
        }  
        //if not all types, and not Nutrition with All or PhysicalActivity selected in combo box, the code set sent in by calling form will remain the code set for this intervention
        if (InterventionCur.getIsNew())
        {
            Interventions.insert(InterventionCur);
        }
        else
        {
            Interventions.update(InterventionCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInterventionEdit.class);
        this.textDate = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.comboCodeSet = new System.Windows.Forms.ComboBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textNote = new OpenDental.ODtextBox();
        this.SuspendLayout();
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(90, 14);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(80, 20);
        this.textDate.TabIndex = 143;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 16);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(78, 17);
        this.label5.TabIndex = 144;
        this.label5.Text = "Date";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(12, 441);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(78, 17);
        this.label6.TabIndex = 152;
        this.label6.Text = "Note";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(484, 513);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 153;
        this.butOK.Text = "&OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(565, 513);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 154;
        this.butCancel.Text = "&Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 513);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 155;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "&Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(176, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(78, 17);
        this.label1.TabIndex = 157;
        this.label1.Text = "Code Set";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCodeSet
        //
        this.comboCodeSet.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCodeSet.DropDownWidth = 125;
        this.comboCodeSet.Location = new System.Drawing.Point(257, 12);
        this.comboCodeSet.MaxDropDownItems = 30;
        this.comboCodeSet.Name = "comboCodeSet";
        this.comboCodeSet.Size = new System.Drawing.Size(180, 21);
        this.comboCodeSet.TabIndex = 156;
        this.comboCodeSet.SelectionChangeCommitted += new System.EventHandler(this.comboCodeSet_SelectionChangeCommitted);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 39);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(628, 394);
        this.gridMain.TabIndex = 158;
        this.gridMain.setTitle("Intervention Codes");
        this.gridMain.setTranslationName(null);
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(90, 439);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(550, 60);
        this.textNote.TabIndex = 147;
        this.textNote.Text = "";
        //
        // FormInterventionEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(652, 548);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboCodeSet);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label5);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormInterventionEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Intervention";
        this.Load += new System.EventHandler(this.FormInterventionEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboCodeSet = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.ODGrid gridMain;
}


