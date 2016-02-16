//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormAllergySetup;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormMedications;
import OpenDental.Lan;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrCriterion;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.ReminderRule;
import OpenDentBusiness.ReminderRules;

public class FormReminderRuleEdit  extends Form 
{

    public ReminderRule RuleCur = new ReminderRule();
    //both the RuleCur.ReminderCriterion and RuleCur.CriterionFK will be altered below in response to user actions.
    public boolean IsNew = new boolean();
    public FormReminderRuleEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReminderRuleEdit_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(EhrCriterion.class).Length;i++)
        {
            comboReminderCriterion.Items.Add(Enum.GetNames(EhrCriterion.class)[i]);
        }
        comboReminderCriterion.SelectedIndex = ((Enum)RuleCur.ReminderCriterion).ordinal();
        textCriterionValue.Text = RuleCur.CriterionValue;
        textReminderMessage.Text = RuleCur.Message;
        fillFK();
    }

    private void fillFK() throws Exception {
        if (RuleCur.CriterionFK == -1 || RuleCur.CriterionFK == 0)
        {
            textCriterionFK.Text = "";
            return ;
        }
         
        switch((EhrCriterion)comboReminderCriterion.SelectedIndex)
        {
            case Problem: 
                DiseaseDef def = DiseaseDefs.getItem(RuleCur.CriterionFK);
                textCriterionFK.Text = def.DiseaseName;
                textICD9.Text = def.ICD9Code;
                break;
            case Medication: 
                //case EhrCriterion.ICD9:
                //  textCriterionFK.Text=ICD9s.GetDescription(RuleCur.CriterionFK);
                //  break;
                Medication tempMed = Medications.getMedication(RuleCur.CriterionFK);
                if (tempMed.MedicationNum == tempMed.GenericNum)
                {
                    //handle generic medication names.
                    textCriterionFK.Text = tempMed.MedName;
                }
                else
                {
                    textCriterionFK.Text = tempMed.MedName + " / " + Medications.getGenericName(tempMed.GenericNum);
                } 
                break;
            case Allergy: 
                textCriterionFK.Text = AllergyDefs.getOne(RuleCur.CriterionFK).Description;
                break;
            case Age: 
            case Gender: 
            case LabResult: 
                break;
            default: 
                break;
        
        }
    }

    //The FK boxes won't even be visible.
    //This should not happen.
    private void comboReminderCriterion_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (RuleCur.ReminderCriterion != (EhrCriterion)comboReminderCriterion.SelectedIndex)
        {
            //if user just changed the type,
            RuleCur.CriterionFK = -1;
            //clear the FK data showing
            fillFK();
        }
         
        RuleCur.ReminderCriterion = (EhrCriterion)comboReminderCriterion.SelectedIndex;
        //if(RuleCur.ReminderCriterion==EhrCriterion.Problem || RuleCur.ReminderCriterion==EhrCriterion.Medication || RuleCur.ReminderCriterion==EhrCriterion.Allergy || RuleCur.ReminderCriterion==EhrCriterion.ICD9) {
        if (RuleCur.ReminderCriterion == EhrCriterion.Problem || RuleCur.ReminderCriterion == EhrCriterion.Medication || RuleCur.ReminderCriterion == EhrCriterion.Allergy)
        {
            labelCriterionFK.Text = RuleCur.ReminderCriterion.ToString();
            textCriterionValue.Visible = false;
            labelCriterionValue.Visible = false;
            labelExample.Visible = false;
            labelCriterionFK.Visible = true;
            textCriterionFK.Visible = true;
            butSelectFK.Visible = true;
            if (RuleCur.ReminderCriterion == EhrCriterion.Problem)
            {
                labelICD9.Visible = true;
                textICD9.Visible = true;
            }
            else
            {
                labelICD9.Visible = false;
                textICD9.Visible = false;
            } 
        }
        else
        {
            //field only used when Age, Gender, or Labresult are selected.
            textCriterionValue.Visible = true;
            labelCriterionValue.Visible = true;
            labelExample.Visible = true;
            labelCriterionFK.Visible = false;
            textCriterionFK.Visible = false;
            butSelectFK.Visible = false;
        } 
    }

    private void butSelectFK_Click(Object sender, EventArgs e) throws Exception {
        switch((EhrCriterion)comboReminderCriterion.SelectedIndex)
        {
            case Problem: 
                FormDiseaseDefs formD = new FormDiseaseDefs();
                formD.IsSelectionMode = true;
                formD.ShowDialog();
                if (formD.DialogResult != DialogResult.OK)
                {
                    RuleCur.CriterionFK = -1;
                    return ;
                }
                 
                RuleCur.CriterionFK = formD.SelectedDiseaseDefNum;
                break;
            case Medication: 
                FormMedications formM = new FormMedications();
                formM.IsSelectionMode = true;
                formM.ShowDialog();
                if (formM.DialogResult != DialogResult.OK)
                {
                    RuleCur.CriterionFK = -1;
                    return ;
                }
                 
                RuleCur.CriterionFK = formM.SelectedMedicationNum;
                break;
            case Allergy: 
                FormAllergySetup formA = new FormAllergySetup();
                formA.IsSelectionMode = true;
                formA.ShowDialog();
                if (formA.DialogResult != DialogResult.OK)
                {
                    RuleCur.CriterionFK = -1;
                    return ;
                }
                 
                RuleCur.CriterionFK = formA.SelectedAllergyDefNum;
                break;
            default: 
                //case EhrCriterion.ICD9:
                //  FormIcd9s FormICD9Select = new FormIcd9s();
                //  FormICD9Select.IsSelectionMode=true;
                //  FormICD9Select.ShowDialog();
                //  if(FormICD9Select.DialogResult!=DialogResult.OK){
                //    RuleCur.CriterionFK=-1;
                //    return;
                //  }
                //  RuleCur.CriterionFK=ICD9s.GetByCode(FormICD9Select.SelectedIcd9Code).ICD9Num;
                //  break;
                MessageBox.Show("You should never see this error message. Something has stopped working properly.");
                break;
        
        }
        fillFK();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            ReminderRules.delete(RuleCur.ReminderRuleNum);
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        //Validate
        RuleCur.ReminderCriterion = (EhrCriterion)comboReminderCriterion.SelectedIndex;
        if (RuleCur.ReminderCriterion == EhrCriterion.Problem || RuleCur.ReminderCriterion == EhrCriterion.Medication || RuleCur.ReminderCriterion == EhrCriterion.Allergy)
        {
            //|| RuleCur.ReminderCriterion==EhrCriterion.ICD9)
            RuleCur.CriterionValue = "";
            if (RuleCur.CriterionFK == -1 || RuleCur.CriterionFK == 0)
            {
                MessageBox.Show("Please select a valid " + RuleCur.ReminderCriterion.ToString().ToLower() + ".");
                return ;
            }
             
        }
        else if (RuleCur.ReminderCriterion == EhrCriterion.Gender)
        {
            RuleCur.CriterionFK = 0;
            if (!StringSupport.equals(textCriterionValue.Text.ToLower(), "male") && !StringSupport.equals(textCriterionValue.Text.ToLower(), "female"))
            {
                MessageBox.Show("Please input male or female for gender value.");
                return ;
            }
             
            RuleCur.CriterionValue = textCriterionValue.Text.ToLower();
        }
        else if (RuleCur.ReminderCriterion == EhrCriterion.LabResult)
        {
            RuleCur.CriterionFK = 0;
            if (StringSupport.equals(textCriterionValue.Text, ""))
            {
                MessageBox.Show("Please input a valid lab result.");
                return ;
            }
             
            RuleCur.CriterionValue = textCriterionValue.Text;
        }
        else
        {
            //Age
            if (textCriterionValue.Text.Length < 2)
            {
                MessageBox.Show("Criterion value must be comparator followed by an age. eg. \"<18\".");
                return ;
            }
             
            if (textCriterionValue.Text[0] != '<' && textCriterionValue.Text[0] != '>')
            {
                MessageBox.Show("Age criterion must begin with either \"<\" or \">\".");
                return ;
            }
             
            int tempAge = new int();
            RefSupport<int> refVar___0 = new RefSupport<int>();
            boolean boolVar___0 = !int.TryParse(textCriterionValue.Text.Substring(1, textCriterionValue.Text.Length - 1), refVar___0);
            tempAge = refVar___0.getValue();
            if (boolVar___0)
            {
                MessageBox.Show("Age criterion is not in the form of a valid age.");
                return ;
            }
             
            if (tempAge < 0 || tempAge > 200)
            {
                MessageBox.Show("Age must be between 0 and 200.");
                return ;
            }
             
            RuleCur.CriterionValue = textCriterionValue.Text;
        }   
        if (textReminderMessage.Text.Length > 255)
        {
            MessageBox.Show("Reminder message must be shorter than 255 characters.");
            return ;
        }
         
        RuleCur.Message = textReminderMessage.Text;
        if (StringSupport.equals(RuleCur.Message, ""))
        {
            MessageBox.Show("Reminder will be saved with no reminder message.");
        }
         
        if (IsNew)
        {
            ReminderRules.insert(RuleCur);
        }
        else
        {
            ReminderRules.update(RuleCur);
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
        this.butDelete = new System.Windows.Forms.Button();
        this.butOk = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.comboReminderCriterion = new System.Windows.Forms.ComboBox();
        this.textCriterionValue = new System.Windows.Forms.TextBox();
        this.textReminderMessage = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.labelCriterionValue = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.labelCriterionFK = new System.Windows.Forms.Label();
        this.textCriterionFK = new System.Windows.Forms.TextBox();
        this.butSelectFK = new System.Windows.Forms.Button();
        this.labelExample = new System.Windows.Forms.Label();
        this.textICD9 = new System.Windows.Forms.TextBox();
        this.labelICD9 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 178);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 0;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(278, 178);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 1;
        this.butOk.Text = "Ok";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(359, 178);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // comboReminderCriterion
        //
        this.comboReminderCriterion.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboReminderCriterion.FormattingEnabled = true;
        this.comboReminderCriterion.Location = new System.Drawing.Point(114, 28);
        this.comboReminderCriterion.Name = "comboReminderCriterion";
        this.comboReminderCriterion.Size = new System.Drawing.Size(121, 21);
        this.comboReminderCriterion.TabIndex = 3;
        this.comboReminderCriterion.SelectedIndexChanged += new System.EventHandler(this.comboReminderCriterion_SelectedIndexChanged);
        //
        // textCriterionValue
        //
        this.textCriterionValue.Location = new System.Drawing.Point(114, 55);
        this.textCriterionValue.Name = "textCriterionValue";
        this.textCriterionValue.Size = new System.Drawing.Size(100, 20);
        this.textCriterionValue.TabIndex = 4;
        //
        // textReminderMessage
        //
        this.textReminderMessage.Location = new System.Drawing.Point(114, 133);
        this.textReminderMessage.Name = "textReminderMessage";
        this.textReminderMessage.Size = new System.Drawing.Size(320, 20);
        this.textReminderMessage.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 29);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(107, 17);
        this.label1.TabIndex = 6;
        this.label1.Text = "Reminder Criterion";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCriterionValue
        //
        this.labelCriterionValue.Location = new System.Drawing.Point(5, 56);
        this.labelCriterionValue.Name = "labelCriterionValue";
        this.labelCriterionValue.Size = new System.Drawing.Size(107, 17);
        this.labelCriterionValue.TabIndex = 7;
        this.labelCriterionValue.Text = "Criterion Value";
        this.labelCriterionValue.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 134);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(107, 17);
        this.label3.TabIndex = 8;
        this.label3.Text = "Reminder Message";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCriterionFK
        //
        this.labelCriterionFK.Location = new System.Drawing.Point(5, 82);
        this.labelCriterionFK.Name = "labelCriterionFK";
        this.labelCriterionFK.Size = new System.Drawing.Size(107, 17);
        this.labelCriterionFK.TabIndex = 10;
        this.labelCriterionFK.Text = "Criterion FK";
        this.labelCriterionFK.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCriterionFK
        //
        this.textCriterionFK.Location = new System.Drawing.Point(114, 81);
        this.textCriterionFK.Name = "textCriterionFK";
        this.textCriterionFK.ReadOnly = true;
        this.textCriterionFK.Size = new System.Drawing.Size(290, 20);
        this.textCriterionFK.TabIndex = 9;
        //
        // butSelectFK
        //
        this.butSelectFK.Location = new System.Drawing.Point(410, 78);
        this.butSelectFK.Name = "butSelectFK";
        this.butSelectFK.Size = new System.Drawing.Size(24, 24);
        this.butSelectFK.TabIndex = 11;
        this.butSelectFK.Text = "...";
        this.butSelectFK.UseVisualStyleBackColor = true;
        this.butSelectFK.Click += new System.EventHandler(this.butSelectFK_Click);
        //
        // labelExample
        //
        this.labelExample.Location = new System.Drawing.Point(220, 56);
        this.labelExample.Name = "labelExample";
        this.labelExample.Size = new System.Drawing.Size(107, 17);
        this.labelExample.TabIndex = 12;
        this.labelExample.Text = "For example, <18";
        this.labelExample.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textICD9
        //
        this.textICD9.Location = new System.Drawing.Point(114, 107);
        this.textICD9.Name = "textICD9";
        this.textICD9.ReadOnly = true;
        this.textICD9.Size = new System.Drawing.Size(139, 20);
        this.textICD9.TabIndex = 9;
        this.textICD9.Visible = false;
        //
        // labelICD9
        //
        this.labelICD9.Location = new System.Drawing.Point(5, 108);
        this.labelICD9.Name = "labelICD9";
        this.labelICD9.Size = new System.Drawing.Size(107, 17);
        this.labelICD9.TabIndex = 10;
        this.labelICD9.Text = "ICD-9";
        this.labelICD9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelICD9.Visible = false;
        //
        // FormReminderRuleEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(446, 213);
        this.Controls.Add(this.labelExample);
        this.Controls.Add(this.butSelectFK);
        this.Controls.Add(this.labelICD9);
        this.Controls.Add(this.labelCriterionFK);
        this.Controls.Add(this.textICD9);
        this.Controls.Add(this.textCriterionFK);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.labelCriterionValue);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textReminderMessage);
        this.Controls.Add(this.textCriterionValue);
        this.Controls.Add(this.comboReminderCriterion);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butDelete);
        this.Name = "FormReminderRuleEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Reminder Rule";
        this.Load += new System.EventHandler(this.FormReminderRuleEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.ComboBox comboReminderCriterion = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textCriterionValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textReminderMessage = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCriterionValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCriterionFK = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCriterionFK = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butSelectFK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelExample = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textICD9 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelICD9 = new System.Windows.Forms.Label();
}


