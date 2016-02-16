//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrVaccinePatEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrPatient;
import OpenDentBusiness.EhrPatients;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.Patient;
import OpenDentBusiness.VaccineDefs;
import OpenDentBusiness.VaccinePat;
import OpenDentBusiness.VaccinePats;
import OpenDentBusiness.YN;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrVaccines;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrVaccines  extends Form 
{

    public Patient PatCur;
    private List<VaccinePat> VaccineList = new List<VaccinePat>();
    private EhrPatient _ehrPatientCur;
    public FormEhrVaccines() throws Exception {
        initializeComponent();
    }

    private void formVaccines_Load(Object sender, EventArgs e) throws Exception {
        fillGridVaccine();
        _ehrPatientCur = EhrPatients.refresh(PatCur.PatNum);
        listVacShareOk.SelectedIndex = ((Enum)_ehrPatientCur.VacShareOk).ordinal();
    }

    private void fillGridVaccine() throws Exception {
        gridVaccine.beginUpdate();
        gridVaccine.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",90);
        gridVaccine.getColumns().add(col);
        col = new ODGridColumn("Vaccine",100);
        gridVaccine.getColumns().add(col);
        VaccineList = VaccinePats.refresh(PatCur.PatNum);
        gridVaccine.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < VaccineList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (VaccineList[i].DateTimeStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(VaccineList[i].DateTimeStart.ToShortDateString());
            } 
            String str = "";
            if (VaccineList[i].VaccineDefNum == 0)
            {
                str = "Not administered: " + VaccineList[i].Note;
            }
            else
            {
                str = VaccineDefs.GetOne(VaccineList[i].VaccineDefNum).VaccineName;
            } 
            row.getCells().add(str);
            gridVaccine.getRows().add(row);
        }
        gridVaccine.endUpdate();
    }

    private void gridVaccine_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridVaccine.getSelectedIndex() == -1)
        {
            return ;
        }
         
        FormEhrVaccinePatEdit FormV = new FormEhrVaccinePatEdit();
        FormV.VaccinePatCur = VaccineList[gridVaccine.getSelectedIndex()];
        FormV.ShowDialog();
        fillGridVaccine();
    }

    private void butAddVaccine_Click(Object sender, EventArgs e) throws Exception {
        FormEhrVaccinePatEdit FormV = new FormEhrVaccinePatEdit();
        FormV.VaccinePatCur = new VaccinePat();
        FormV.VaccinePatCur.PatNum = PatCur.PatNum;
        FormV.VaccinePatCur.DateTimeStart = DateTime.Now;
        FormV.VaccinePatCur.DateTimeEnd = DateTime.Now;
        FormV.IsNew = true;
        FormV.ShowDialog();
        fillGridVaccine();
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        if (gridVaccine.getSelectedIndices().Length == 0)
        {
            MessageBox.Show("Please select at least one vaccine.");
            return ;
        }
         
        List<VaccinePat> vaccines = new List<VaccinePat>();
        for (int i = 0;i < gridVaccine.getSelectedIndices().Length;i++)
        {
            vaccines.Add(VaccineList[gridVaccine.getSelectedIndices()[i]]);
        }
        OpenDentBusiness.HL7.EhrVXU vxu = null;
        try
        {
            vxu = new OpenDentBusiness.HL7.EhrVXU(PatCur, vaccines);
        }
        catch (Exception ex)
        {
            //Exception happens when validation fails.
            MessageBox.Show(ex.Message);
            return ;
        }

        //Show validation error messages.
        String outputStr = vxu.generateMessage();
        SaveFileDialog dlg = new SaveFileDialog();
        dlg.FileName = "vxu.txt";
        DialogResult result = dlg.ShowDialog();
        if (result != DialogResult.OK)
        {
            return ;
        }
         
        if (File.Exists(dlg.FileName))
        {
            if (MessageBox.Show("Overwrite existing file?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        File.WriteAllText(dlg.FileName, outputStr);
        MessageBox.Show("Saved");
    }

    private void butSubmitImmunization_Click(Object sender, EventArgs e) throws Exception {
        if (gridVaccine.getSelectedIndices().Length == 0)
        {
            MessageBox.Show("Please select at least one vaccine.");
            return ;
        }
         
        List<VaccinePat> vaccines = new List<VaccinePat>();
        for (int i = 0;i < gridVaccine.getSelectedIndices().Length;i++)
        {
            vaccines.Add(VaccineList[gridVaccine.getSelectedIndices()[i]]);
        }
        OpenDentBusiness.HL7.EhrVXU vxu = null;
        try
        {
            vxu = new OpenDentBusiness.HL7.EhrVXU(PatCur, vaccines);
        }
        catch (Exception ex)
        {
            //Exception happens when validation fails.
            MessageBox.Show(ex.Message);
            return ;
        }

        //Show validation error messages.
        String outputStr = vxu.generateMessage();
        Cursor = Cursors.WaitCursor;
        try
        {
            EmailMessages.sendTestUnsecure("Immunization Submission","vxu.txt",outputStr);
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MessageBox.Show("Sent");
    }

    private void listVacShareOk_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        _ehrPatientCur.VacShareOk = (YN)listVacShareOk.SelectedIndex;
        EhrPatients.update(_ehrPatientCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrVaccines.class);
        this.butSubmitImmunization = new System.Windows.Forms.Button();
        this.butAddVaccine = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.butExport = new System.Windows.Forms.Button();
        this.label45 = new System.Windows.Forms.Label();
        this.gridVaccine = new OpenDental.UI.ODGrid();
        this.listVacShareOk = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
        //
        // butSubmitImmunization
        //
        this.butSubmitImmunization.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSubmitImmunization.Location = new System.Drawing.Point(569, 438);
        this.butSubmitImmunization.Name = "butSubmitImmunization";
        this.butSubmitImmunization.Size = new System.Drawing.Size(86, 23);
        this.butSubmitImmunization.TabIndex = 3;
        this.butSubmitImmunization.Text = "Submit HL7";
        this.butSubmitImmunization.UseVisualStyleBackColor = true;
        this.butSubmitImmunization.Click += new System.EventHandler(this.butSubmitImmunization_Click);
        //
        // butAddVaccine
        //
        this.butAddVaccine.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddVaccine.Location = new System.Drawing.Point(569, 12);
        this.butAddVaccine.Name = "butAddVaccine";
        this.butAddVaccine.Size = new System.Drawing.Size(86, 23);
        this.butAddVaccine.TabIndex = 2;
        this.butAddVaccine.Text = "Add";
        this.butAddVaccine.UseVisualStyleBackColor = true;
        this.butAddVaccine.Click += new System.EventHandler(this.butAddVaccine_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(569, 481);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(86, 23);
        this.butClose.TabIndex = 4;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butExport
        //
        this.butExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butExport.Location = new System.Drawing.Point(569, 409);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(86, 23);
        this.butExport.TabIndex = 5;
        this.butExport.Text = "Export HL7";
        this.butExport.UseVisualStyleBackColor = true;
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // label45
        //
        this.label45.Location = new System.Drawing.Point(13, 487);
        this.label45.Name = "label45";
        this.label45.Size = new System.Drawing.Size(255, 17);
        this.label45.TabIndex = 130;
        this.label45.Text = "Patient allows exporting to other EHRs";
        this.label45.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridVaccine
        //
        this.gridVaccine.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridVaccine.setHScrollVisible(false);
        this.gridVaccine.Location = new System.Drawing.Point(12, 12);
        this.gridVaccine.Name = "gridVaccine";
        this.gridVaccine.setScrollValue(0);
        this.gridVaccine.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridVaccine.Size = new System.Drawing.Size(547, 469);
        this.gridVaccine.TabIndex = 0;
        this.gridVaccine.setTitle("Vaccines");
        this.gridVaccine.setTranslationName(null);
        this.gridVaccine.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridVaccine.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridVaccine_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // listVacShareOk
        //
        this.listVacShareOk.ColumnWidth = 30;
        this.listVacShareOk.Items.AddRange(new Object[]{ "??", "Yes", "No" });
        this.listVacShareOk.Location = new System.Drawing.Point(269, 487);
        this.listVacShareOk.MultiColumn = true;
        this.listVacShareOk.Name = "listVacShareOk";
        this.listVacShareOk.Size = new System.Drawing.Size(95, 17);
        this.listVacShareOk.TabIndex = 131;
        this.listVacShareOk.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listVacShareOk_MouseClick);
        //
        // FormEhrVaccines
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(663, 516);
        this.Controls.Add(this.listVacShareOk);
        this.Controls.Add(this.label45);
        this.Controls.Add(this.butExport);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAddVaccine);
        this.Controls.Add(this.butSubmitImmunization);
        this.Controls.Add(this.gridVaccine);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrVaccines";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormVaccines";
        this.Load += new System.EventHandler(this.FormVaccines_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butSubmitImmunization = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAddVaccine = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridVaccine;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butExport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label45 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listVacShareOk = new System.Windows.Forms.ListBox();
}


