//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrPatientConfidentialPrefEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrCriterion;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import OpenDentBusiness.ReminderRule;
import OpenDentBusiness.ReminderRules;

public class FormEhrReminders  extends Form 
{

    public Patient PatCur;
    public List<ReminderRule> listReminders = new List<ReminderRule>();
    public List<EhrMeasureEvent> reminderSentList = new List<EhrMeasureEvent>();
    public FormEhrReminders() throws Exception {
        initializeComponent();
    }

    private void formReminders_Load(Object sender, EventArgs e) throws Exception {
        textPreferedConfidentialContact.Text = PatCur.PreferContactConfidential.ToString();
        fillGrid();
        fillGridProvided();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Reminder Criterion",135);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Message",200);
        gridMain.getColumns().add(col);
        listReminders = ReminderRules.getRemindersForPatient(PatCur);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listReminders.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            ReminderCriterion __dummyScrutVar0 = listReminders[i].ReminderCriterion;
            if (__dummyScrutVar0.equals(EhrCriterion.Problem))
            {
                DiseaseDef def = DiseaseDefs.GetItem(listReminders[i].CriterionFK);
                row.getCells().add("Problem =" + def.ICD9Code + " " + def.DiseaseName);
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Medication))
            {
                Medication tempMed = Medications.GetMedication(listReminders[i].CriterionFK);
                if (tempMed.MedicationNum == tempMed.GenericNum)
                {
                    //handle generic medication names.
                    row.getCells().add("Medication = " + tempMed.MedName);
                }
                else
                {
                    row.getCells().add("Medication = " + tempMed.MedName + " (" + Medications.getGenericName(tempMed.GenericNum) + ")");
                } 
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Allergy))
            {
                row.getCells().add("Allergy = " + AllergyDefs.GetOne(listReminders[i].CriterionFK).Description);
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Age))
            {
                row.getCells().add("Age " + listReminders[i].CriterionValue);
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Gender))
            {
                row.getCells().add("Gender is " + listReminders[i].CriterionValue);
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.LabResult))
            {
                row.getCells().add("LabResult " + listReminders[i].CriterionValue);
            }
                  
            //case EhrCriterion.ICD9:
            //  row.Cells.Add("ICD9 "+ICD9s.GetDescription(listReminders[i].CriterionFK));
            //  break;
            row.getCells().Add(listReminders[i].Message);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillGridProvided() throws Exception {
        gridProvided.beginUpdate();
        gridProvided.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime",130);
        gridProvided.getColumns().add(col);
        col = new ODGridColumn("Details",600);
        gridProvided.getColumns().add(col);
        reminderSentList = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.ReminderSent);
        gridProvided.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < reminderSentList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(reminderSentList[i].DateTEvent.ToString());
            row.getCells().Add(reminderSentList[i].MoreInfo);
            gridProvided.getRows().add(row);
        }
        gridProvided.endUpdate();
    }

    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.ReminderSent;
        newMeasureEvent.PatNum = PatCur.PatNum;
        String moreInfo = "";
        if (gridMain.getSelectedIndex() > -1)
        {
            moreInfo = gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getCells()[1].Text;
        }
         
        newMeasureEvent.MoreInfo = moreInfo;
        EhrMeasureEvents.insert(newMeasureEvent);
        fillGridProvided();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridProvided.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridProvided.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(reminderSentList[gridProvided.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
        fillGridProvided();
    }

    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        FormEhrPatientConfidentialPrefEdit FormCPE = new FormEhrPatientConfidentialPrefEdit();
        FormCPE.PatCur = PatCur;
        FormCPE.ShowDialog();
        textPreferedConfidentialContact.Text = PatCur.PreferContactConfidential.ToString();
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
        this.label1 = new System.Windows.Forms.Label();
        this.textPreferedConfidentialContact = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new System.Windows.Forms.Button();
        this.butSend = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.gridProvided = new OpenDental.UI.ODGrid();
        this.butEdit = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(17, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(213, 17);
        this.label1.TabIndex = 0;
        this.label1.Text = "Confidental Communication Preference";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPreferedConfidentialContact
        //
        this.textPreferedConfidentialContact.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textPreferedConfidentialContact.Location = new System.Drawing.Point(233, 16);
        this.textPreferedConfidentialContact.Name = "textPreferedConfidentialContact";
        this.textPreferedConfidentialContact.ReadOnly = true;
        this.textPreferedConfidentialContact.Size = new System.Drawing.Size(237, 20);
        this.textPreferedConfidentialContact.TabIndex = 1;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(535, 244);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Reminders");
        this.gridMain.setTranslationName(null);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(475, 585);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 4;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butSend
        //
        this.butSend.Location = new System.Drawing.Point(248, 292);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 23);
        this.butSend.TabIndex = 5;
        this.butSend.Text = "Send";
        this.butSend.UseVisualStyleBackColor = true;
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 586);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridProvided
        //
        this.gridProvided.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProvided.setHScrollVisible(false);
        this.gridProvided.Location = new System.Drawing.Point(18, 321);
        this.gridProvided.Name = "gridProvided";
        this.gridProvided.setScrollValue(0);
        this.gridProvided.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProvided.Size = new System.Drawing.Size(535, 258);
        this.gridProvided.TabIndex = 7;
        this.gridProvided.setTitle("Reminders Sent");
        this.gridProvided.setTranslationName(null);
        //
        // butEdit
        //
        this.butEdit.Location = new System.Drawing.Point(475, 14);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(75, 23);
        this.butEdit.TabIndex = 8;
        this.butEdit.Text = "Edit";
        this.butEdit.UseVisualStyleBackColor = true;
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // FormReminders
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(562, 617);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.gridProvided);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.textPreferedConfidentialContact);
        this.Controls.Add(this.label1);
        this.Name = "FormReminders";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reminders";
        this.Load += new System.EventHandler(this.FormReminders_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPreferedConfidentialContact = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSend = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridProvided;
    private System.Windows.Forms.Button butEdit = new System.Windows.Forms.Button();
}


