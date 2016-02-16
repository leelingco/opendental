//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormReminderRuleEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrCriterion;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.ReminderRule;
import OpenDentBusiness.ReminderRules;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormReminderRules  extends Form 
{

    public List<ReminderRule> listReminders = new List<ReminderRule>();
    public FormReminderRules() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReminderRules_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Reminder Criterion",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Message",200);
        gridMain.getColumns().add(col);
        listReminders = ReminderRules.selectAll();
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
                    row.getCells().add("Medication = " + tempMed.MedName + " / " + Medications.getGenericName(tempMed.GenericNum));
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

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormReminderRuleEdit FormRRE = new FormReminderRuleEdit();
        FormRRE.RuleCur = listReminders[e.getRow()];
        FormRRE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormReminderRuleEdit FormRRE = new FormReminderRuleEdit();
        FormRRE.IsNew = true;
        FormRRE.ShowDialog();
        fillGrid();
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
        this.butClose = new System.Windows.Forms.Button();
        this.butAdd = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(583, 380);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(299, 380);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 1;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(646, 362);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Reminder Rules");
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
        // FormReminderRules
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(670, 415);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Name = "FormReminderRules";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reminder Rules";
        this.Load += new System.EventHandler(this.FormReminderRules_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
}


