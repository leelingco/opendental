//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCpts;
import OpenDental.FormHcpcs;
import OpenDental.FormIcd10s;
import OpenDental.FormIcd9s;
import OpenDental.FormProcCodes;
import OpenDental.FormSnomeds;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Security;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDental.FormEhrSettings;

public class FormEhrSettings  extends Form 
{

    private List<String> ListRecEncCodes = new List<String>();
    private List<String> ListRecPregCodes = new List<String>();
    private int OldEncListSelectedIdx = new int();
    private int OldPregListSelectedIdx = new int();
    private String NewEncCodeSystem = new String();
    private String NewPregCodeSystem = new String();
    public FormEhrSettings() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrSettings_Load(Object sender, EventArgs e) throws Exception {
        checkAlertHighSeverity.Checked = PrefC.getBool(PrefName.EhrRxAlertHighSeverity);
        checkMU2.Checked = PrefC.getBool(PrefName.MeaningfulUseTwo);
        fillRecEncCodesList();
        String defaultEncCode = PrefC.getString(PrefName.CQMDefaultEncounterCodeValue);
        String defaultEncCodeSystem = PrefC.getString(PrefName.CQMDefaultEncounterCodeSystem);
        NewEncCodeSystem = defaultEncCodeSystem;
        OldEncListSelectedIdx = -1;
        int countNotInSnomedTable = 0;
        for (int i = 0;i < ListRecEncCodes.Count;i++)
        {
            if (i == 0)
            {
                comboEncCodes.Items.Add(ListRecEncCodes[i]);
                comboEncCodes.SelectedIndex = i;
                if (StringSupport.equals(defaultEncCode, ListRecEncCodes[i]))
                {
                    comboEncCodes.SelectedIndex = i;
                    OldEncListSelectedIdx = i;
                }
                 
                continue;
            }
             
            if (!Snomeds.CodeExists(ListRecEncCodes[i]))
            {
                countNotInSnomedTable++;
                continue;
            }
             
            comboEncCodes.Items.Add(ListRecEncCodes[i]);
            if (StringSupport.equals(ListRecEncCodes[i], defaultEncCode) && StringSupport.equals(defaultEncCodeSystem, "SNOMEDCT"))
            {
                comboEncCodes.SelectedIndex = i;
                OldEncListSelectedIdx = i;
                labelEncWarning.Visible = false;
                textEncCodeDescript.Text = Snomeds.GetByCode(ListRecEncCodes[i]).Description;
            }
             
        }
        //Guaranteed to exist in snomed table from above check
        if (countNotInSnomedTable > 0)
        {
            MsgBox.show(this,"The snomed table does not contain one or more codes from the list of recommended encounter codes.  The snomed table should be updated by running the Code System Importer tool found in Setup | EHR.");
        }
         
        if (comboEncCodes.SelectedIndex == -1)
        {
            //default enc code set to code not in recommended list and not 'none'
            System.String __dummyScrutVar0 = defaultEncCodeSystem;
            if (__dummyScrutVar0.equals("CDT"))
            {
                textEncCodeValue.Text = ProcedureCodes.getProcCode(defaultEncCode).ProcCode;
                //Will return a new ProcCode object, not null, if not found
                textEncCodeDescript.Text = ProcedureCodes.getProcCode(defaultEncCode).Descript;
            }
            else if (__dummyScrutVar0.equals("CPT"))
            {
                Cpt cEnc = Cpts.getByCode(defaultEncCode);
                if (cEnc != null)
                {
                    textEncCodeValue.Text = cEnc.CptCode;
                    textEncCodeDescript.Text = cEnc.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("SNOMEDCT"))
            {
                Snomed sEnc = Snomeds.getByCode(defaultEncCode);
                if (sEnc != null)
                {
                    textEncCodeValue.Text = sEnc.SnomedCode;
                    textEncCodeDescript.Text = sEnc.Description;
                }
                 
            }
            else if (__dummyScrutVar0.equals("HCPCS"))
            {
                Hcpcs hEnc = Hcpcses.getByCode(defaultEncCode);
                if (hEnc != null)
                {
                    textEncCodeValue.Text = hEnc.HcpcsCode;
                    textEncCodeDescript.Text = hEnc.DescriptionShort;
                }
                 
            }
                
        }
         
        fillRecPregCodesList();
        String defaultPregCode = PrefC.getString(PrefName.PregnancyDefaultCodeValue);
        String defaultPregCodeSystem = PrefC.getString(PrefName.PregnancyDefaultCodeSystem);
        NewPregCodeSystem = defaultPregCodeSystem;
        OldPregListSelectedIdx = -1;
        countNotInSnomedTable = 0;
        for (int i = 0;i < ListRecPregCodes.Count;i++)
        {
            if (i == 0)
            {
                comboPregCodes.Items.Add(ListRecPregCodes[i]);
                comboPregCodes.SelectedIndex = i;
                if (StringSupport.equals(defaultPregCode, ListRecPregCodes[i]))
                {
                    comboPregCodes.SelectedIndex = i;
                    OldPregListSelectedIdx = i;
                }
                 
                continue;
            }
             
            if (!Snomeds.CodeExists(ListRecPregCodes[i]))
            {
                countNotInSnomedTable++;
                continue;
            }
             
            comboPregCodes.Items.Add(ListRecPregCodes[i]);
            if (StringSupport.equals(ListRecPregCodes[i], defaultPregCode) && StringSupport.equals(defaultPregCodeSystem, "SNOMEDCT"))
            {
                comboPregCodes.SelectedIndex = i;
                OldPregListSelectedIdx = i;
                labelPregWarning.Visible = false;
                textPregCodeDescript.Text = Snomeds.GetByCode(ListRecPregCodes[i]).Description;
            }
             
        }
        //Guaranteed to exist in snomed table from above check
        if (countNotInSnomedTable > 0)
        {
            MsgBox.show(this,"The snomed table does not contain one or more codes from the list of recommended pregnancy codes.  The snomed table should be updated by running the Code System Importer tool found in Setup | EHR.");
        }
         
        if (comboPregCodes.SelectedIndex == -1)
        {
            //default preg code set to code not in recommended list and not 'none'
            System.String __dummyScrutVar1 = defaultPregCodeSystem;
            if (__dummyScrutVar1.equals("ICD9CM"))
            {
                ICD9 i9Preg = ICD9s.getByCode(defaultPregCode);
                if (i9Preg != null)
                {
                    textPregCodeValue.Text = i9Preg.ICD9Code;
                    textPregCodeDescript.Text = i9Preg.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("SNOMEDCT"))
            {
                Snomed sPreg = Snomeds.getByCode(defaultPregCode);
                if (sPreg != null)
                {
                    textPregCodeValue.Text = sPreg.SnomedCode;
                    textPregCodeDescript.Text = sPreg.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("ICD10CM"))
            {
                Icd10 i10Preg = Icd10s.getByCode(defaultPregCode);
                if (i10Preg != null)
                {
                    textPregCodeValue.Text = i10Preg.Icd10Code;
                    textPregCodeDescript.Text = i10Preg.Description;
                }
                 
            }
            else
            {
            }   
        }
         
    }

    private void checkAlertHighSeverity_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            checkAlertHighSeverity.Checked = PrefC.getBool(PrefName.EhrRxAlertHighSeverity);
        }
         
    }

    private void fillRecEncCodesList() throws Exception {
        //All of the recommended codes are SNOMEDCT codes
        ListRecEncCodes = new List<String>();
        ListRecEncCodes.Add("none");
        ListRecEncCodes.Add("90526000");
        //Initial evaluation and management of healthy individual (procedure)
        ListRecEncCodes.Add("185349003");
        //Encounter for "check-up" (procedure)
        ListRecEncCodes.Add("185463005");
        //Visit out of hours (procedure)
        ListRecEncCodes.Add("185465003");
        //Weekend visit (procedure)
        ListRecEncCodes.Add("270427003");
        //Patient-initiated encounter (procedure)
        ListRecEncCodes.Add("270430005");
        //Provider-initiated encounter (procedure)
        ListRecEncCodes.Add("308335008");
        //Patient encounter procedure (procedure)
        ListRecEncCodes.Add("390906007");
        //Follow-up encounter (procedure)
        ListRecEncCodes.Add("406547006");
    }

    //Urgent follow-up (procedure)
    private void fillRecPregCodesList() throws Exception {
        //All of the recommended codes are SNOMEDCT codes
        ListRecPregCodes = new List<String>();
        ListRecPregCodes.Add("none");
        ListRecPregCodes.Add("72892002");
        //Normal pregnancy (finding)
        ListRecPregCodes.Add("77386006");
        //Patient currently pregnant (finding)
        ListRecPregCodes.Add("83074005");
        //Unplanned pregnancy (finding)
        ListRecPregCodes.Add("169560008");
        //Pregnant - urine test confirms (finding)
        ListRecPregCodes.Add("169563005");
        //Pregnant - on history (finding)
        ListRecPregCodes.Add("169565003");
        //Pregnant - planned (finding)
        ListRecPregCodes.Add("237238006");
        //Pregnancy with uncertain dates (finding)
        ListRecPregCodes.Add("248985009");
        //Presentation of pregnancy (finding)
        ListRecPregCodes.Add("314204000");
    }

    //Early stage of pregnancy (finding)
    private void checkMU2_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            checkMU2.Checked = PrefC.getBool(PrefName.MeaningfulUseTwo);
        }
         
    }

    private void comboEncCodes_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            comboEncCodes.SelectedIndex = OldEncListSelectedIdx;
            return ;
        }
         
        NewEncCodeSystem = "SNOMEDCT";
        textEncCodeValue.Text = "";
        if (comboEncCodes.SelectedIndex == 0)
        {
            //none
            textEncCodeDescript.Clear();
            labelEncWarning.Visible = true;
        }
        else
        {
            Snomed sEnc = Snomeds.GetByCode(comboEncCodes.SelectedItem.ToString());
            if (sEnc == null)
            {
                //this check may not be necessary now that we are not adding the code to the list to be selected if they do not have it in the snomed table.  Harmelss and safe.
                MsgBox.show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
            }
            else
            {
                textEncCodeDescript.Text = sEnc.Description;
            } 
            labelEncWarning.Visible = false;
        } 
    }

    private void comboPregCodes_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            comboPregCodes.SelectedIndex = OldPregListSelectedIdx;
            return ;
        }
         
        NewPregCodeSystem = "SNOMEDCT";
        textPregCodeValue.Text = "";
        if (comboPregCodes.SelectedIndex == 0)
        {
            //none
            textPregCodeDescript.Clear();
            labelPregWarning.Visible = true;
        }
        else
        {
            Snomed sPreg = Snomeds.GetByCode(comboPregCodes.SelectedItem.ToString());
            if (sPreg == null)
            {
                MsgBox.show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
            }
            else
            {
                textPregCodeDescript.Text = sPreg.Description;
            } 
            labelPregWarning.Visible = false;
        } 
    }

    private void butEncSnomed_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormS.IsSelectionMode = false;
        }
        else
        {
            FormS.IsSelectionMode = true;
        } 
        FormS.ShowDialog();
        if (FormS.DialogResult == DialogResult.OK)
        {
            NewEncCodeSystem = "SNOMEDCT";
            for (int i = 1;i < comboEncCodes.Items.Count;i++)
            {
                if (StringSupport.equals(FormS.SelectedSnomed.SnomedCode, comboEncCodes.Items[i].ToString()))
                {
                    //if they go to snomed list and select one of the recommended codes, select in list
                    comboEncCodes.SelectedIndex = i;
                    textEncCodeValue.Clear();
                    textEncCodeDescript.Text = FormS.SelectedSnomed.Description;
                    labelEncWarning.Visible = false;
                    return ;
                }
                 
            }
            comboEncCodes.SelectedIndex = -1;
            textEncCodeValue.Text = FormS.SelectedSnomed.SnomedCode;
            textEncCodeDescript.Text = FormS.SelectedSnomed.Description;
            labelEncWarning.Visible = true;
        }
         
    }

    private void butEncHcpcs_Click(Object sender, EventArgs e) throws Exception {
        FormHcpcs FormH = new FormHcpcs();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormH.IsSelectionMode = false;
        }
        else
        {
            FormH.IsSelectionMode = true;
        } 
        FormH.ShowDialog();
        if (FormH.DialogResult == DialogResult.OK)
        {
            NewEncCodeSystem = "HCPCS";
            comboEncCodes.SelectedIndex = -1;
            textEncCodeValue.Text = FormH.SelectedHcpcs.HcpcsCode;
            textEncCodeDescript.Text = FormH.SelectedHcpcs.DescriptionShort;
            labelEncWarning.Visible = true;
        }
         
    }

    private void butEncCdt_Click(Object sender, EventArgs e) throws Exception {
        FormProcCodes FormP = new FormProcCodes();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormP.IsSelectionMode = false;
        }
        else
        {
            FormP.IsSelectionMode = true;
        } 
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.OK)
        {
            NewEncCodeSystem = "CDT";
            comboEncCodes.SelectedIndex = -1;
            ProcedureCode procCur = ProcedureCodes.getProcCode(FormP.SelectedCodeNum);
            textEncCodeValue.Text = procCur.ProcCode;
            textEncCodeDescript.Text = procCur.Descript;
            //We might implement a CodeSystem column on the ProcCode table since it may have ICD9 and ICD10 codes in it.  If so, we can set the NewEncCodeSystem to the value in that new column.
            //NewEncCodeSystem=procCur.CodeSystem;
            labelEncWarning.Visible = true;
        }
         
    }

    private void butEncCpt_Click(Object sender, EventArgs e) throws Exception {
        FormCpts FormC = new FormCpts();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormC.IsSelectionMode = false;
        }
        else
        {
            FormC.IsSelectionMode = true;
        } 
        FormC.ShowDialog();
        if (FormC.DialogResult == DialogResult.OK)
        {
            NewEncCodeSystem = "CPT";
            comboEncCodes.SelectedIndex = -1;
            textEncCodeValue.Text = FormC.SelectedCpt.CptCode;
            textEncCodeDescript.Text = FormC.SelectedCpt.Description;
            labelEncWarning.Visible = true;
        }
         
    }

    private void butPregSnomed_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormS.IsSelectionMode = false;
        }
        else
        {
            FormS.IsSelectionMode = true;
        } 
        FormS.ShowDialog();
        if (FormS.DialogResult == DialogResult.OK)
        {
            NewPregCodeSystem = "SNOMEDCT";
            for (int i = 1;i < comboPregCodes.Items.Count;i++)
            {
                if (StringSupport.equals(FormS.SelectedSnomed.SnomedCode, comboPregCodes.Items[i].ToString()))
                {
                    //if they go to snomed list and select one of the recommended codes, select in list
                    comboPregCodes.SelectedIndex = i;
                    textPregCodeValue.Clear();
                    textPregCodeDescript.Text = FormS.SelectedSnomed.Description;
                    labelPregWarning.Visible = false;
                    return ;
                }
                 
            }
            comboPregCodes.SelectedIndex = -1;
            textPregCodeValue.Text = FormS.SelectedSnomed.SnomedCode;
            textPregCodeDescript.Text = FormS.SelectedSnomed.Description;
            labelPregWarning.Visible = true;
        }
         
    }

    private void butPregIcd9_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s FormI9 = new FormIcd9s();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormI9.IsSelectionMode = false;
        }
        else
        {
            FormI9.IsSelectionMode = true;
        } 
        FormI9.ShowDialog();
        if (FormI9.DialogResult == DialogResult.OK)
        {
            NewPregCodeSystem = "ICD9CM";
            comboPregCodes.SelectedIndex = -1;
            textPregCodeValue.Text = FormI9.SelectedIcd9.ICD9Code;
            textPregCodeDescript.Text = FormI9.SelectedIcd9.Description;
            labelPregWarning.Visible = true;
        }
         
    }

    private void butPregIcd10_Click(Object sender, EventArgs e) throws Exception {
        FormIcd10s FormI10 = new FormIcd10s();
        if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
        {
            FormI10.IsSelectionMode = false;
        }
        else
        {
            FormI10.IsSelectionMode = true;
        } 
        FormI10.ShowDialog();
        if (FormI10.DialogResult == DialogResult.OK)
        {
            NewPregCodeSystem = "ICD10CM";
            comboPregCodes.SelectedIndex = -1;
            textPregCodeValue.Text = FormI10.SelectedIcd10.Icd10Code;
            textPregCodeDescript.Text = FormI10.SelectedIcd10.Description;
            labelPregWarning.Visible = true;
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Prefs.UpdateBool(PrefName.EhrRxAlertHighSeverity, checkAlertHighSeverity.Checked);
        Prefs.UpdateBool(PrefName.MeaningfulUseTwo, checkMU2.Checked);
        Prefs.updateString(PrefName.CQMDefaultEncounterCodeSystem,NewEncCodeSystem);
        Prefs.updateString(PrefName.PregnancyDefaultCodeSystem,NewPregCodeSystem);
        if (comboEncCodes.SelectedIndex == -1)
        {
            Prefs.UpdateString(PrefName.CQMDefaultEncounterCodeValue, textEncCodeValue.Text);
        }
        else
        {
            Prefs.UpdateString(PrefName.CQMDefaultEncounterCodeValue, comboEncCodes.SelectedItem.ToString());
        } 
        if (comboPregCodes.SelectedIndex == -1)
        {
            Prefs.UpdateString(PrefName.PregnancyDefaultCodeValue, textPregCodeValue.Text);
        }
        else
        {
            Prefs.UpdateString(PrefName.PregnancyDefaultCodeValue, comboPregCodes.SelectedItem.ToString());
        } 
        //A diseasedef with this default pregnancy code will be inserted if needed the first time they check the pregnant box on a vitalsign.  The DiseaseName will be "Pregnant" with the correct codevalue/system.
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrSettings.class);
        this.checkMU2 = new System.Windows.Forms.CheckBox();
        this.groupEncounter = new System.Windows.Forms.GroupBox();
        this.butEncCpt = new OpenDental.UI.Button();
        this.comboEncCodes = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.labelEncWarning = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textEncCodeValue = new System.Windows.Forms.TextBox();
        this.textEncCodeDescript = new OpenDental.ODtextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butEncHcpcs = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.butEncSnomed = new OpenDental.UI.Button();
        this.butEncCdt = new OpenDental.UI.Button();
        this.groupPregnancy = new System.Windows.Forms.GroupBox();
        this.comboPregCodes = new System.Windows.Forms.ComboBox();
        this.labelPregWarning = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.textPregCodeValue = new System.Windows.Forms.TextBox();
        this.textPregCodeDescript = new OpenDental.ODtextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.butPregIcd9 = new OpenDental.UI.Button();
        this.butPregSnomed = new OpenDental.UI.Button();
        this.butPregIcd10 = new OpenDental.UI.Button();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.groupGlobalSettings = new System.Windows.Forms.GroupBox();
        this.checkAlertHighSeverity = new System.Windows.Forms.CheckBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupEncounter.SuspendLayout();
        this.groupPregnancy.SuspendLayout();
        this.groupGlobalSettings.SuspendLayout();
        this.SuspendLayout();
        //
        // checkMU2
        //
        this.checkMU2.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkMU2.Location = new System.Drawing.Point(6, 19);
        this.checkMU2.Name = "checkMU2";
        this.checkMU2.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkMU2.Size = new System.Drawing.Size(162, 20);
        this.checkMU2.TabIndex = 5;
        this.checkMU2.Text = "Meaningful Use Stage 2";
        this.checkMU2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkMU2.UseVisualStyleBackColor = true;
        this.checkMU2.Click += new System.EventHandler(this.checkMU2_Click);
        //
        // groupEncounter
        //
        this.groupEncounter.Controls.Add(this.butEncCpt);
        this.groupEncounter.Controls.Add(this.comboEncCodes);
        this.groupEncounter.Controls.Add(this.label5);
        this.groupEncounter.Controls.Add(this.labelEncWarning);
        this.groupEncounter.Controls.Add(this.label6);
        this.groupEncounter.Controls.Add(this.textEncCodeValue);
        this.groupEncounter.Controls.Add(this.textEncCodeDescript);
        this.groupEncounter.Controls.Add(this.label1);
        this.groupEncounter.Controls.Add(this.label2);
        this.groupEncounter.Controls.Add(this.label4);
        this.groupEncounter.Controls.Add(this.butEncHcpcs);
        this.groupEncounter.Controls.Add(this.label3);
        this.groupEncounter.Controls.Add(this.butEncSnomed);
        this.groupEncounter.Controls.Add(this.butEncCdt);
        this.groupEncounter.Location = new System.Drawing.Point(12, 70);
        this.groupEncounter.Name = "groupEncounter";
        this.groupEncounter.Size = new System.Drawing.Size(453, 265);
        this.groupEncounter.TabIndex = 119;
        this.groupEncounter.TabStop = false;
        this.groupEncounter.Text = "Default Encounter Code";
        //
        // butEncCpt
        //
        this.butEncCpt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEncCpt.setAutosize(true);
        this.butEncCpt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEncCpt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEncCpt.setCornerRadius(4F);
        this.butEncCpt.Location = new System.Drawing.Point(288, 181);
        this.butEncCpt.Name = "butEncCpt";
        this.butEncCpt.Size = new System.Drawing.Size(75, 24);
        this.butEncCpt.TabIndex = 130;
        this.butEncCpt.Text = "CPT";
        this.butEncCpt.Click += new System.EventHandler(this.butEncCpt_Click);
        //
        // comboEncCodes
        //
        this.comboEncCodes.FormattingEnabled = true;
        this.comboEncCodes.Location = new System.Drawing.Point(124, 77);
        this.comboEncCodes.Name = "comboEncCodes";
        this.comboEncCodes.Size = new System.Drawing.Size(158, 21);
        this.comboEncCodes.TabIndex = 122;
        this.comboEncCodes.SelectionChangeCommitted += new System.EventHandler(this.comboEncCodes_SelectionChangeCommitted);
        //
        // label5
        //
        this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label5.Location = new System.Drawing.Point(6, 210);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(115, 17);
        this.label5.TabIndex = 127;
        this.label5.Text = "Code";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelEncWarning
        //
        this.labelEncWarning.ForeColor = System.Drawing.Color.Red;
        this.labelEncWarning.Location = new System.Drawing.Point(29, 232);
        this.labelEncWarning.Name = "labelEncWarning";
        this.labelEncWarning.Size = new System.Drawing.Size(403, 26);
        this.labelEncWarning.TabIndex = 129;
        this.labelEncWarning.Text = "Warning: In order for patients to be considered for CQM calculations, you will ha" + "ve to manually create encounters with a qualified code specific to each measure." + "";
        //
        // label6
        //
        this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label6.Location = new System.Drawing.Point(285, 78);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(162, 17);
        this.label6.TabIndex = 128;
        this.label6.Text = "\'none\' disables auto encounters";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textEncCodeValue
        //
        this.textEncCodeValue.Location = new System.Drawing.Point(124, 209);
        this.textEncCodeValue.Name = "textEncCodeValue";
        this.textEncCodeValue.ReadOnly = true;
        this.textEncCodeValue.Size = new System.Drawing.Size(158, 20);
        this.textEncCodeValue.TabIndex = 126;
        //
        // textEncCodeDescript
        //
        this.textEncCodeDescript.AcceptsTab = true;
        this.textEncCodeDescript.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textEncCodeDescript.DetectUrls = false;
        this.textEncCodeDescript.Location = new System.Drawing.Point(124, 102);
        this.textEncCodeDescript.Name = "textEncCodeDescript";
        this.textEncCodeDescript.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textEncCodeDescript.ReadOnly = true;
        this.textEncCodeDescript.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textEncCodeDescript.Size = new System.Drawing.Size(323, 46);
        this.textEncCodeDescript.TabIndex = 108;
        this.textEncCodeDescript.Text = "";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(441, 55);
        this.label1.TabIndex = 4;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 102);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(115, 17);
        this.label2.TabIndex = 109;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label4.Location = new System.Drawing.Point(6, 78);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(115, 17);
        this.label4.TabIndex = 110;
        this.label4.Text = "Recommended Codes";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butEncHcpcs
        //
        this.butEncHcpcs.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEncHcpcs.setAutosize(true);
        this.butEncHcpcs.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEncHcpcs.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEncHcpcs.setCornerRadius(4F);
        this.butEncHcpcs.Location = new System.Drawing.Point(369, 181);
        this.butEncHcpcs.Name = "butEncHcpcs";
        this.butEncHcpcs.Size = new System.Drawing.Size(75, 24);
        this.butEncHcpcs.TabIndex = 124;
        this.butEncHcpcs.Text = "HCPCS";
        this.butEncHcpcs.Click += new System.EventHandler(this.butEncHcpcs_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(124, 152);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(276, 26);
        this.label3.TabIndex = 113;
        this.label3.Text = "Choosing a code not in the recommended list might make it more difficult to incre" + "ase your CQM percentages.";
        //
        // butEncSnomed
        //
        this.butEncSnomed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEncSnomed.setAutosize(true);
        this.butEncSnomed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEncSnomed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEncSnomed.setCornerRadius(4F);
        this.butEncSnomed.Location = new System.Drawing.Point(124, 181);
        this.butEncSnomed.Name = "butEncSnomed";
        this.butEncSnomed.Size = new System.Drawing.Size(77, 24);
        this.butEncSnomed.TabIndex = 125;
        this.butEncSnomed.Text = "SNOMED CT";
        this.butEncSnomed.Click += new System.EventHandler(this.butEncSnomed_Click);
        //
        // butEncCdt
        //
        this.butEncCdt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEncCdt.setAutosize(true);
        this.butEncCdt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEncCdt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEncCdt.setCornerRadius(4F);
        this.butEncCdt.Location = new System.Drawing.Point(207, 181);
        this.butEncCdt.Name = "butEncCdt";
        this.butEncCdt.Size = new System.Drawing.Size(75, 24);
        this.butEncCdt.TabIndex = 123;
        this.butEncCdt.Text = "CDT";
        this.butEncCdt.Click += new System.EventHandler(this.butEncCdt_Click);
        //
        // groupPregnancy
        //
        this.groupPregnancy.Controls.Add(this.comboPregCodes);
        this.groupPregnancy.Controls.Add(this.labelPregWarning);
        this.groupPregnancy.Controls.Add(this.label8);
        this.groupPregnancy.Controls.Add(this.label9);
        this.groupPregnancy.Controls.Add(this.textPregCodeValue);
        this.groupPregnancy.Controls.Add(this.textPregCodeDescript);
        this.groupPregnancy.Controls.Add(this.label10);
        this.groupPregnancy.Controls.Add(this.butPregIcd9);
        this.groupPregnancy.Controls.Add(this.butPregSnomed);
        this.groupPregnancy.Controls.Add(this.butPregIcd10);
        this.groupPregnancy.Controls.Add(this.label11);
        this.groupPregnancy.Controls.Add(this.label12);
        this.groupPregnancy.Controls.Add(this.label13);
        this.groupPregnancy.Location = new System.Drawing.Point(12, 341);
        this.groupPregnancy.Name = "groupPregnancy";
        this.groupPregnancy.Size = new System.Drawing.Size(453, 265);
        this.groupPregnancy.TabIndex = 120;
        this.groupPregnancy.TabStop = false;
        this.groupPregnancy.Text = "Default Pregnancy Diagnosis Code";
        //
        // comboPregCodes
        //
        this.comboPregCodes.FormattingEnabled = true;
        this.comboPregCodes.Location = new System.Drawing.Point(124, 77);
        this.comboPregCodes.Name = "comboPregCodes";
        this.comboPregCodes.Size = new System.Drawing.Size(158, 21);
        this.comboPregCodes.TabIndex = 130;
        this.comboPregCodes.SelectionChangeCommitted += new System.EventHandler(this.comboPregCodes_SelectionChangeCommitted);
        //
        // labelPregWarning
        //
        this.labelPregWarning.ForeColor = System.Drawing.Color.Red;
        this.labelPregWarning.Location = new System.Drawing.Point(48, 232);
        this.labelPregWarning.Name = "labelPregWarning";
        this.labelPregWarning.Size = new System.Drawing.Size(366, 26);
        this.labelPregWarning.TabIndex = 129;
        this.labelPregWarning.Text = "Warning: In order for patients to be excluded from certain CQM calculations, you " + "will have to manually enter a pregnancy Dx with a qualified code.";
        //
        // label8
        //
        this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label8.Location = new System.Drawing.Point(285, 78);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(162, 17);
        this.label8.TabIndex = 128;
        this.label8.Text = "\'none\' disables auto preg Dx\'s";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label9
        //
        this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label9.Location = new System.Drawing.Point(6, 210);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(115, 17);
        this.label9.TabIndex = 127;
        this.label9.Text = "Code";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPregCodeValue
        //
        this.textPregCodeValue.Location = new System.Drawing.Point(124, 209);
        this.textPregCodeValue.Name = "textPregCodeValue";
        this.textPregCodeValue.ReadOnly = true;
        this.textPregCodeValue.Size = new System.Drawing.Size(158, 20);
        this.textPregCodeValue.TabIndex = 126;
        //
        // textPregCodeDescript
        //
        this.textPregCodeDescript.AcceptsTab = true;
        this.textPregCodeDescript.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textPregCodeDescript.DetectUrls = false;
        this.textPregCodeDescript.Location = new System.Drawing.Point(124, 102);
        this.textPregCodeDescript.Name = "textPregCodeDescript";
        this.textPregCodeDescript.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textPregCodeDescript.ReadOnly = true;
        this.textPregCodeDescript.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textPregCodeDescript.Size = new System.Drawing.Size(323, 46);
        this.textPregCodeDescript.TabIndex = 108;
        this.textPregCodeDescript.Text = "";
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(6, 102);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(115, 17);
        this.label10.TabIndex = 109;
        this.label10.Text = "Description";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butPregIcd9
        //
        this.butPregIcd9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPregIcd9.setAutosize(true);
        this.butPregIcd9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPregIcd9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPregIcd9.setCornerRadius(4F);
        this.butPregIcd9.Location = new System.Drawing.Point(207, 181);
        this.butPregIcd9.Name = "butPregIcd9";
        this.butPregIcd9.Size = new System.Drawing.Size(75, 24);
        this.butPregIcd9.TabIndex = 124;
        this.butPregIcd9.Text = "ICD9CM";
        this.butPregIcd9.Click += new System.EventHandler(this.butPregIcd9_Click);
        //
        // butPregSnomed
        //
        this.butPregSnomed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPregSnomed.setAutosize(true);
        this.butPregSnomed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPregSnomed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPregSnomed.setCornerRadius(4F);
        this.butPregSnomed.Location = new System.Drawing.Point(124, 181);
        this.butPregSnomed.Name = "butPregSnomed";
        this.butPregSnomed.Size = new System.Drawing.Size(77, 24);
        this.butPregSnomed.TabIndex = 125;
        this.butPregSnomed.Text = "SNOMED CT";
        this.butPregSnomed.Click += new System.EventHandler(this.butPregSnomed_Click);
        //
        // butPregIcd10
        //
        this.butPregIcd10.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPregIcd10.setAutosize(true);
        this.butPregIcd10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPregIcd10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPregIcd10.setCornerRadius(4F);
        this.butPregIcd10.Location = new System.Drawing.Point(288, 181);
        this.butPregIcd10.Name = "butPregIcd10";
        this.butPregIcd10.Size = new System.Drawing.Size(75, 24);
        this.butPregIcd10.TabIndex = 123;
        this.butPregIcd10.Text = "ICD10CM";
        this.butPregIcd10.Click += new System.EventHandler(this.butPregIcd10_Click);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(6, 16);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(441, 55);
        this.label11.TabIndex = 4;
        this.label11.Text = resources.GetString("label11.Text");
        //
        // label12
        //
        this.label12.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label12.Location = new System.Drawing.Point(6, 78);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(115, 17);
        this.label12.TabIndex = 110;
        this.label12.Text = "Recommended Codes";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(124, 152);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(290, 26);
        this.label13.TabIndex = 113;
        this.label13.Text = "Choosing a code not in the recommended list will generate a Dx that might not exc" + "lude the patient from certain measures.";
        //
        // groupGlobalSettings
        //
        this.groupGlobalSettings.Controls.Add(this.checkAlertHighSeverity);
        this.groupGlobalSettings.Controls.Add(this.checkMU2);
        this.groupGlobalSettings.Location = new System.Drawing.Point(12, 12);
        this.groupGlobalSettings.Name = "groupGlobalSettings";
        this.groupGlobalSettings.Size = new System.Drawing.Size(453, 52);
        this.groupGlobalSettings.TabIndex = 121;
        this.groupGlobalSettings.TabStop = false;
        this.groupGlobalSettings.Text = "Global Settings";
        //
        // checkAlertHighSeverity
        //
        this.checkAlertHighSeverity.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkAlertHighSeverity.Location = new System.Drawing.Point(174, 19);
        this.checkAlertHighSeverity.Name = "checkAlertHighSeverity";
        this.checkAlertHighSeverity.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkAlertHighSeverity.Size = new System.Drawing.Size(270, 20);
        this.checkAlertHighSeverity.TabIndex = 6;
        this.checkAlertHighSeverity.Text = "Only show high significance Rx alerts";
        this.checkAlertHighSeverity.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkAlertHighSeverity.UseVisualStyleBackColor = true;
        this.checkAlertHighSeverity.Click += new System.EventHandler(this.checkAlertHighSeverity_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(309, 621);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(390, 621);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormEhrSettings
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(477, 657);
        this.Controls.Add(this.groupGlobalSettings);
        this.Controls.Add(this.groupPregnancy);
        this.Controls.Add(this.groupEncounter);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrSettings";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EHR Settings";
        this.Load += new System.EventHandler(this.FormEhrSettings_Load);
        this.groupEncounter.ResumeLayout(false);
        this.groupEncounter.PerformLayout();
        this.groupPregnancy.ResumeLayout(false);
        this.groupPregnancy.PerformLayout();
        this.groupGlobalSettings.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.CheckBox checkMU2 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupEncounter = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelEncWarning = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEncCodeValue = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textEncCodeDescript;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEncHcpcs;
    private OpenDental.UI.Button butEncSnomed;
    private OpenDental.UI.Button butEncCdt;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupPregnancy = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelPregWarning = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPregCodeValue = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textPregCodeDescript;
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPregIcd9;
    private OpenDental.UI.Button butPregSnomed;
    private OpenDental.UI.Button butPregIcd10;
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupGlobalSettings = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ComboBox comboEncCodes = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPregCodes = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butEncCpt;
    private System.Windows.Forms.CheckBox checkAlertHighSeverity = new System.Windows.Forms.CheckBox();
}


