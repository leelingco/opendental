//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrEduBrowser;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EduResource;
import OpenDentBusiness.EduResources;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrEduResourcesPat;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrEduResourcesPat  extends Form 
{

    public Patient patCur;
    private List<EduResource> eduResourceList = new List<EduResource>();
    private List<EhrMeasureEvent> eduMeasureProvidedList = new List<EhrMeasureEvent>();
    public FormEhrEduResourcesPat() throws Exception {
        initializeComponent();
    }

    private void formEduResourcesPat_Load(Object sender, EventArgs e) throws Exception {
        fillGridEdu();
        fillGridProvided();
    }

    private void fillGridEdu() throws Exception {
        gridEdu.beginUpdate();
        gridEdu.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Criteria",300);
        gridEdu.getColumns().add(col);
        col = new ODGridColumn("Link",100);
        gridEdu.getColumns().add(col);
        eduResourceList = EduResources.generateForPatient(patCur.PatNum);
        gridEdu.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < eduResourceList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (eduResourceList[i].DiseaseDefNum != 0)
            {
                row.getCells().add("Problem: " + DiseaseDefs.GetItem(eduResourceList[i].DiseaseDefNum).DiseaseName);
            }
            else //row.Cells.Add("ICD9: "+DiseaseDefs.GetItem(eduResourceList[i].DiseaseDefNum).ICD9Code);
            if (eduResourceList[i].MedicationNum != 0)
            {
                row.getCells().add("Medication: " + Medications.GetDescription(eduResourceList[i].MedicationNum));
            }
            else
            {
                row.getCells().add("Lab Results: " + eduResourceList[i].LabResultName);
            }  
            row.getCells().Add(eduResourceList[i].ResourceUrl);
            gridEdu.getRows().add(row);
        }
        gridEdu.endUpdate();
    }

    private void gridEdu_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() != 1)
        {
            return ;
        }
         
        boolean didPrint = false;
        try
        {
            FormEhrEduBrowser FormEDUB = new FormEhrEduBrowser(eduResourceList[e.getRow()].ResourceUrl);
            FormEDUB.ShowDialog();
            didPrint = FormEDUB.DidPrint;
        }
        catch (Exception __dummyCatchVar0)
        {
            //System.Diagnostics.Process.Start(eduResourceList[e.Row].ResourceUrl);
            MessageBox.Show("Link not found.");
            return ;
        }

        if (didPrint)
        {
            EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            newMeasureEvent.DateTEvent = DateTime.Now;
            newMeasureEvent.EventType = EhrMeasureEventType.EducationProvided;
            newMeasureEvent.PatNum = patCur.PatNum;
            newMeasureEvent.MoreInfo = eduResourceList[e.getRow()].ResourceUrl;
            EhrMeasureEvents.insert(newMeasureEvent);
            fillGridProvided();
        }
         
    }

    private void fillGridProvided() throws Exception {
        gridProvided.beginUpdate();
        gridProvided.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime",140);
        gridProvided.getColumns().add(col);
        col = new ODGridColumn("Details",600);
        gridProvided.getColumns().add(col);
        eduMeasureProvidedList = EhrMeasureEvents.refreshByType(patCur.PatNum,EhrMeasureEventType.EducationProvided);
        gridProvided.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < eduMeasureProvidedList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(eduMeasureProvidedList[i].DateTEvent.ToString());
            row.getCells().Add(eduMeasureProvidedList[i].MoreInfo);
            gridProvided.getRows().add(row);
        }
        gridProvided.endUpdate();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridProvided.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridProvided.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(eduMeasureProvidedList[gridProvided.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
        fillGridProvided();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrEduResourcesPat.class);
        this.butClose = new System.Windows.Forms.Button();
        this.gridEdu = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.gridProvided = new OpenDental.UI.ODGrid();
        this.label2 = new System.Windows.Forms.Label();
        this.butDelete = new System.Windows.Forms.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(784, 636);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridEdu
        //
        this.gridEdu.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEdu.setHScrollVisible(false);
        this.gridEdu.Location = new System.Drawing.Point(12, 53);
        this.gridEdu.Name = "gridEdu";
        this.gridEdu.setScrollValue(0);
        this.gridEdu.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridEdu.Size = new System.Drawing.Size(847, 264);
        this.gridEdu.TabIndex = 1;
        this.gridEdu.setTitle("Educational Resources");
        this.gridEdu.setTranslationName(null);
        this.gridEdu.CellClick = __MultiODGridClickEventHandler.combine(this.gridEdu.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEdu_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(679, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "To generate a patient education resource, single click on one of the links below," + " then print.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridProvided
        //
        this.gridProvided.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProvided.setHScrollVisible(false);
        this.gridProvided.Location = new System.Drawing.Point(12, 357);
        this.gridProvided.Name = "gridProvided";
        this.gridProvided.setScrollValue(0);
        this.gridProvided.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProvided.Size = new System.Drawing.Size(847, 273);
        this.gridProvided.TabIndex = 3;
        this.gridProvided.setTitle("Education Provided");
        this.gridProvided.setTranslationName(null);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 338);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(679, 16);
        this.label2.TabIndex = 4;
        this.label2.Text = "This is a historical record of education resources provided to this patient.  Del" + "ete any entries that are inaccurate.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.Location = new System.Drawing.Point(12, 636);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 5;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 29);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(798, 16);
        this.label3.TabIndex = 6;
        this.label3.Text = "Please note that it will not be possible to enter patient-specific educational re" + "sources for patients who have no medications, problems, or lab results.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormEhrEduResourcesPat
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(871, 671);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.gridProvided);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridEdu);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrEduResourcesPat";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Educational Resources";
        this.Load += new System.EventHandler(this.FormEduResourcesPat_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridEdu;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridProvided;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


