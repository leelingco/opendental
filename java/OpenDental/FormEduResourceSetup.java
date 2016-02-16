//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEduResourceEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EduResource;
import OpenDentBusiness.EduResources;
import OpenDentBusiness.Medications;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEduResourceSetup;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEduResourceSetup  extends Form 
{

    private List<EduResource> eduResourceList = new List<EduResource>();
    public FormEduResourceSetup() throws Exception {
        initializeComponent();
    }

    private void formEduResourceSetup_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridEdu.beginUpdate();
        gridEdu.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Criteria",300);
        gridEdu.getColumns().add(col);
        col = new ODGridColumn("Link",700);
        gridEdu.getColumns().add(col);
        eduResourceList = EduResources.selectAll();
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
                row.getCells().add("Lab Results: " + eduResourceList[i].LabResultName + " " + eduResourceList[i].LabResultCompare);
            }  
            row.getCells().Add(eduResourceList[i].ResourceUrl);
            gridEdu.getRows().add(row);
        }
        gridEdu.endUpdate();
    }

    private void gridEdu_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEduResourceEdit FormERE = new FormEduResourceEdit();
        FormERE.EduResourceCur = eduResourceList[e.getRow()];
        FormERE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEduResourceEdit FormERE = new FormEduResourceEdit();
        FormERE.IsNew = true;
        FormERE.EduResourceCur = new EduResource();
        FormERE.ShowDialog();
        fillGrid();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEduResourceSetup.class);
        this.butClose = new System.Windows.Forms.Button();
        this.butAdd = new System.Windows.Forms.Button();
        this.gridEdu = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(805, 496);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(337, 496);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 4;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridEdu
        //
        this.gridEdu.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEdu.setHScrollVisible(false);
        this.gridEdu.Location = new System.Drawing.Point(12, 12);
        this.gridEdu.Name = "gridEdu";
        this.gridEdu.setScrollValue(0);
        this.gridEdu.Size = new System.Drawing.Size(868, 478);
        this.gridEdu.TabIndex = 3;
        this.gridEdu.setTitle("Educational Resources");
        this.gridEdu.setTranslationName(null);
        this.gridEdu.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridEdu.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEdu_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormEduResourceSetup
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(892, 531);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridEdu);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEduResourceSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Education Resources Setup";
        this.Load += new System.EventHandler(this.FormEduResourceSetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridEdu;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
}


