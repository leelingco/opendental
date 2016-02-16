//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.FormReconcileAllergy;
import OpenDental.FormReconcileMedication;
import OpenDental.FormReconcileProblem;
import OpenDental.Lan;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.Patient;
import OpenDental.FormEhrSummaryCcdEdit;

public class FormEhrSummaryCcdEdit  extends Form 
{

    public String StrXmlFilePath = new String();
    public boolean DidPrint = new boolean();
    /**
    * Will be set to the patient that this CCD message is indicated for.  Can be null if not meant for incorporating into a patient's account.
    */
    private Patient _patCur;
    /**
    * Patient can be null.  If null, or if PatNum is 0, reconciles will not be available.
    */
    public FormEhrSummaryCcdEdit(String strXmlFilePath, Patient patCur) throws Exception {
        initializeComponent();
        StrXmlFilePath = strXmlFilePath;
        _patCur = patCur;
    }

    private void formEhrSummaryCcdEdit_Load(Object sender, EventArgs e) throws Exception {
        if (_patCur == null || _patCur.PatNum == 0)
        {
            //No patient is currently selected.  Do not show reconcile UI.
            labelReconcile.Visible = false;
            butReconcileAllergies.Visible = false;
            butReconcileMedications.Visible = false;
            butReconcileProblems.Visible = false;
        }
         
        Cursor = Cursors.WaitCursor;
        webBrowser1.Url = new Uri(StrXmlFilePath);
        Cursor = Cursors.Default;
    }

    /**
    * Can only be called if IsReconcile is true.  This function is for EHR module b.4.
    */
    private void butReconcileMedications_Click(Object sender, EventArgs e) throws Exception {
        XmlDocument xmlDocCcd = new XmlDocument();
        try
        {
            String strXmlText = File.ReadAllText(StrXmlFilePath);
            xmlDocCcd.LoadXml(strXmlText);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error reading file") + ": " + ex.Message);
            return ;
        }

        FormReconcileMedication formRM = new FormReconcileMedication(_patCur);
        formRM.ListMedicationPatNew = new List<MedicationPat>();
        EhrCCD.getListMedicationPats(xmlDocCcd,formRM.ListMedicationPatNew);
        formRM.ShowDialog();
    }

    /**
    * Can only be called if IsReconcile is true.  This function is for EHR module b.4.
    */
    private void butReconcileProblems_Click(Object sender, EventArgs e) throws Exception {
        XmlDocument xmlDocCcd = new XmlDocument();
        try
        {
            String strXmlText = File.ReadAllText(StrXmlFilePath);
            xmlDocCcd.LoadXml(strXmlText);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error reading file") + ": " + ex.Message);
            return ;
        }

        FormReconcileProblem formRP = new FormReconcileProblem(_patCur);
        formRP.ListProblemNew = new List<Disease>();
        formRP.ListProblemDefNew = new List<DiseaseDef>();
        EhrCCD.getListDiseases(xmlDocCcd,formRP.ListProblemNew,formRP.ListProblemDefNew);
        formRP.ShowDialog();
    }

    /**
    * Can only be called if IsReconcile is true.  This function is for EHR module b.4.
    */
    private void butReconcileAllergies_Click(Object sender, EventArgs e) throws Exception {
        XmlDocument xmlDocCcd = new XmlDocument();
        try
        {
            String strXmlText = File.ReadAllText(StrXmlFilePath);
            xmlDocCcd.LoadXml(strXmlText);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error reading file") + ": " + ex.Message);
            return ;
        }

        FormReconcileAllergy formRA = new FormReconcileAllergy(_patCur);
        formRA.ListAllergyNew = new List<Allergy>();
        formRA.ListAllergyDefNew = new List<AllergyDef>();
        EhrCCD.getListAllergies(xmlDocCcd,formRA.ListAllergyNew,formRA.ListAllergyDefNew);
        formRA.ShowDialog();
    }

    private void butShowXml_Click(Object sender, EventArgs e) throws Exception {
        String strCcd = File.ReadAllText(StrXmlFilePath);
        //Reformat to add newlines after each element to make more readable.
        strCcd = strCcd.Replace("\r\n", "").Replace("\n", "").Replace("\r", "");
        //Remove existsing newlines.
        strCcd = strCcd.Replace(">", ">\r\n");
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(strCcd);
        msgbox.ShowDialog();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        //use the modeless version, which also allows user to choose printer
        webBrowser1.ShowPrintDialog();
        DidPrint = true;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrSummaryCcdEdit.class);
        this.butClose = new System.Windows.Forms.Button();
        this.webBrowser1 = new System.Windows.Forms.WebBrowser();
        this.butPrint = new System.Windows.Forms.Button();
        this.butReconcileMedications = new System.Windows.Forms.Button();
        this.labelReconcile = new System.Windows.Forms.Label();
        this.butReconcileProblems = new System.Windows.Forms.Button();
        this.butReconcileAllergies = new System.Windows.Forms.Button();
        this.butShowXml = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(804, 610);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // webBrowser1
        //
        this.webBrowser1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowser1.Location = new System.Drawing.Point(1, 1);
        this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowser1.Name = "webBrowser1";
        this.webBrowser1.Size = new System.Drawing.Size(882, 603);
        this.webBrowser1.TabIndex = 1;
        //
        // butPrint
        //
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.Location = new System.Drawing.Point(628, 610);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 2;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butReconcileMedications
        //
        this.butReconcileMedications.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butReconcileMedications.Location = new System.Drawing.Point(104, 610);
        this.butReconcileMedications.Name = "butReconcileMedications";
        this.butReconcileMedications.Size = new System.Drawing.Size(75, 23);
        this.butReconcileMedications.TabIndex = 3;
        this.butReconcileMedications.Text = "Medications";
        this.butReconcileMedications.UseVisualStyleBackColor = true;
        this.butReconcileMedications.Click += new System.EventHandler(this.butReconcileMedications_Click);
        //
        // labelReconcile
        //
        this.labelReconcile.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelReconcile.Location = new System.Drawing.Point(1, 613);
        this.labelReconcile.Name = "labelReconcile";
        this.labelReconcile.Size = new System.Drawing.Size(97, 16);
        this.labelReconcile.TabIndex = 4;
        this.labelReconcile.Text = "Reconcile:";
        this.labelReconcile.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelReconcile.Visible = false;
        //
        // butReconcileProblems
        //
        this.butReconcileProblems.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butReconcileProblems.Location = new System.Drawing.Point(185, 610);
        this.butReconcileProblems.Name = "butReconcileProblems";
        this.butReconcileProblems.Size = new System.Drawing.Size(75, 23);
        this.butReconcileProblems.TabIndex = 5;
        this.butReconcileProblems.Text = "Problems";
        this.butReconcileProblems.UseVisualStyleBackColor = true;
        this.butReconcileProblems.Click += new System.EventHandler(this.butReconcileProblems_Click);
        //
        // butReconcileAllergies
        //
        this.butReconcileAllergies.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butReconcileAllergies.Location = new System.Drawing.Point(266, 610);
        this.butReconcileAllergies.Name = "butReconcileAllergies";
        this.butReconcileAllergies.Size = new System.Drawing.Size(75, 23);
        this.butReconcileAllergies.TabIndex = 6;
        this.butReconcileAllergies.Text = "Allergies";
        this.butReconcileAllergies.UseVisualStyleBackColor = true;
        this.butReconcileAllergies.Click += new System.EventHandler(this.butReconcileAllergies_Click);
        //
        // butShowXml
        //
        this.butShowXml.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butShowXml.Location = new System.Drawing.Point(453, 610);
        this.butShowXml.Name = "butShowXml";
        this.butShowXml.Size = new System.Drawing.Size(75, 23);
        this.butShowXml.TabIndex = 7;
        this.butShowXml.Text = "Show xml";
        this.butShowXml.UseVisualStyleBackColor = true;
        this.butShowXml.Click += new System.EventHandler(this.butShowXml_Click);
        //
        // FormEhrSummaryCcdEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(884, 639);
        this.Controls.Add(this.butShowXml);
        this.Controls.Add(this.butReconcileAllergies);
        this.Controls.Add(this.butReconcileProblems);
        this.Controls.Add(this.labelReconcile);
        this.Controls.Add(this.butReconcileMedications);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.webBrowser1);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(650, 200);
        this.Name = "FormEhrSummaryCcdEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Summary of Care";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormEhrSummaryCcdEdit_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.WebBrowser webBrowser1 = new System.Windows.Forms.WebBrowser();
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butReconcileMedications = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelReconcile = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butReconcileProblems = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butReconcileAllergies = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShowXml = new System.Windows.Forms.Button();
}


