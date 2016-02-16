//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormEhrGrowthCharts;
import OpenDental.FormVitalsignEdit2014;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormVitalsigns;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormVitalsigns  extends Form 
{

    private List<Vitalsign> listVs = new List<Vitalsign>();
    public long PatNum = new long();
    public FormVitalsigns() throws Exception {
        initializeComponent();
    }

    private void formVitalsigns_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Height",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Weight",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("BP",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("BMI",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Documentation for Followup or Ineligible",150);
        gridMain.getColumns().add(col);
        listVs = Vitalsigns.refresh(PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listVs.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listVs[i].DateTaken.ToShortDateString());
            row.getCells().Add(listVs[i].Height.ToString() + " in.");
            row.getCells().Add(listVs[i].Weight.ToString() + " lbs.");
            row.getCells().Add(listVs[i].BpSystolic.ToString() + "/" + listVs[i].BpDiastolic.ToString());
            //BMI = (lbs*703)/(in^2)
            float bmi = Vitalsigns.CalcBMI(listVs[i].Weight, listVs[i].Height);
            if (bmi != 0)
            {
                row.getCells().Add(bmi.ToString("n1"));
            }
            else
            {
                //leave cell blank because there is not a valid bmi
                row.getCells().add("");
            } 
            row.getCells().Add(listVs[i].Documentation);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long vitalNum = listVs[e.getRow()].VitalsignNum;
        //change for EHR 2014
        FormVitalsignEdit2014 FormVSE = new FormVitalsignEdit2014();
        //FormEhrVitalsignEdit FormVSE=new FormEhrVitalsignEdit();
        FormVSE.VitalsignCur = Vitalsigns.getOne(vitalNum);
        FormVSE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        //change for EHR 2014
        FormVitalsignEdit2014 FormVSE = new FormVitalsignEdit2014();
        //FormEhrVitalsignEdit FormVSE=new FormEhrVitalsignEdit();
        FormVSE.VitalsignCur = new Vitalsign();
        FormVSE.VitalsignCur.PatNum = PatNum;
        FormVSE.VitalsignCur.DateTaken = DateTime.Today;
        FormVSE.VitalsignCur.setIsNew(true);
        FormVSE.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
    }

    /**
    * Hidden if BPOnly vital sign measure.
    */
    private void butGrowthChart_Click(Object sender, EventArgs e) throws Exception {
        FormEhrGrowthCharts FormGC = new FormEhrGrowthCharts();
        FormGC.PatNum = PatNum;
        FormGC.ShowDialog();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormVitalsigns.class);
        this.butAdd = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.butGrowthChart = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(28, 425);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 1;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(584, 425);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butGrowthChart
        //
        this.butGrowthChart.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butGrowthChart.Location = new System.Drawing.Point(255, 425);
        this.butGrowthChart.Name = "butGrowthChart";
        this.butGrowthChart.Size = new System.Drawing.Size(94, 23);
        this.butGrowthChart.TabIndex = 3;
        this.butGrowthChart.Text = "Growth Chart";
        this.butGrowthChart.UseVisualStyleBackColor = true;
        this.butGrowthChart.Click += new System.EventHandler(this.butGrowthChart_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(28, 25);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(631, 364);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Vital Signs");
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
        // FormVitalsigns
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(683, 460);
        this.Controls.Add(this.butGrowthChart);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormVitalsigns";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Vital Signs";
        this.Load += new System.EventHandler(this.FormVitalsigns_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGrowthChart = new System.Windows.Forms.Button();
}


