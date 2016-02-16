//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDiseaseEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.FunctionalStatus;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;

/**
* 
*/
public class FormDiseaseEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Disease DiseaseCur;
    private TextBox textNote = new TextBox();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private OpenDental.UI.Button butDelete;
    private TextBox textProblem = new TextBox();
    private TextBox textIcd9 = new TextBox();
    private Label label3 = new Label();
    private Label labelStatus = new Label();
    private ComboBox comboStatus = new ComboBox();
    private Label label4 = new Label();
    private Label label5 = new Label();
    private OpenDental.ValidDate textDateStart;
    private OpenDental.ValidDate textDateStop;
    private OpenDental.UI.Button butTodayStart;
    private OpenDental.UI.Button butTodayStop;
    private TextBox textSnomed = new TextBox();
    private Label label6 = new Label();
    private ComboBox comboSnomedProblemType = new ComboBox();
    private Label labelSnomedProblemType = new Label();
    private Label label7 = new Label();
    private Label labelFunctionalStatus = new Label();
    private ComboBox comboEhrFunctionalStatus = new ComboBox();
    private Label label8 = new Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormDiseaseEdit(Disease diseaseCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        DiseaseCur = diseaseCur;
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDiseaseEdit.class);
        this.textNote = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textProblem = new System.Windows.Forms.TextBox();
        this.textIcd9 = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.labelStatus = new System.Windows.Forms.Label();
        this.comboStatus = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textSnomed = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.comboSnomedProblemType = new System.Windows.Forms.ComboBox();
        this.labelSnomedProblemType = new System.Windows.Forms.Label();
        this.butTodayStop = new OpenDental.UI.Button();
        this.butTodayStart = new OpenDental.UI.Button();
        this.textDateStop = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label7 = new System.Windows.Forms.Label();
        this.labelFunctionalStatus = new System.Windows.Forms.Label();
        this.comboEhrFunctionalStatus = new System.Windows.Forms.ComboBox();
        this.label8 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(118, 169);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(322, 120);
        this.textNote.TabIndex = 3;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 169);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 4;
        this.label1.Text = "Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 13);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 19);
        this.label2.TabIndex = 5;
        this.label2.Text = "Problem";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProblem
        //
        this.textProblem.Location = new System.Drawing.Point(118, 12);
        this.textProblem.Name = "textProblem";
        this.textProblem.ReadOnly = true;
        this.textProblem.Size = new System.Drawing.Size(199, 20);
        this.textProblem.TabIndex = 7;
        //
        // textIcd9
        //
        this.textIcd9.Location = new System.Drawing.Point(118, 38);
        this.textIcd9.Name = "textIcd9";
        this.textIcd9.ReadOnly = true;
        this.textIcd9.Size = new System.Drawing.Size(321, 20);
        this.textIcd9.TabIndex = 9;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(14, 39);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 19);
        this.label3.TabIndex = 8;
        this.label3.Text = "ICD9";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelStatus
        //
        this.labelStatus.Location = new System.Drawing.Point(3, 92);
        this.labelStatus.Name = "labelStatus";
        this.labelStatus.Size = new System.Drawing.Size(111, 15);
        this.labelStatus.TabIndex = 82;
        this.labelStatus.Text = "Status";
        this.labelStatus.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboStatus
        //
        this.comboStatus.Cursor = System.Windows.Forms.Cursors.Default;
        this.comboStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatus.Location = new System.Drawing.Point(118, 90);
        this.comboStatus.MaxDropDownItems = 10;
        this.comboStatus.Name = "comboStatus";
        this.comboStatus.Size = new System.Drawing.Size(126, 21);
        this.comboStatus.TabIndex = 83;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(14, 117);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 19);
        this.label4.TabIndex = 5;
        this.label4.Text = "Start Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(14, 143);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 19);
        this.label5.TabIndex = 5;
        this.label5.Text = "Stop Date";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSnomed
        //
        this.textSnomed.Location = new System.Drawing.Point(118, 64);
        this.textSnomed.Name = "textSnomed";
        this.textSnomed.ReadOnly = true;
        this.textSnomed.Size = new System.Drawing.Size(322, 20);
        this.textSnomed.TabIndex = 87;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(14, 64);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(100, 19);
        this.label6.TabIndex = 88;
        this.label6.Text = "SNOMED CT";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSnomedProblemType
        //
        this.comboSnomedProblemType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSnomedProblemType.FormattingEnabled = true;
        this.comboSnomedProblemType.Location = new System.Drawing.Point(118, 295);
        this.comboSnomedProblemType.Name = "comboSnomedProblemType";
        this.comboSnomedProblemType.Size = new System.Drawing.Size(209, 21);
        this.comboSnomedProblemType.TabIndex = 89;
        //
        // labelSnomedProblemType
        //
        this.labelSnomedProblemType.Location = new System.Drawing.Point(12, 296);
        this.labelSnomedProblemType.Name = "labelSnomedProblemType";
        this.labelSnomedProblemType.Size = new System.Drawing.Size(100, 17);
        this.labelSnomedProblemType.TabIndex = 90;
        this.labelSnomedProblemType.Text = "Problem Type";
        this.labelSnomedProblemType.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butTodayStop
        //
        this.butTodayStop.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTodayStop.setAutosize(true);
        this.butTodayStop.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTodayStop.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTodayStop.setCornerRadius(4F);
        this.butTodayStop.Location = new System.Drawing.Point(263, 141);
        this.butTodayStop.Name = "butTodayStop";
        this.butTodayStop.Size = new System.Drawing.Size(64, 23);
        this.butTodayStop.TabIndex = 86;
        this.butTodayStop.Text = "Today";
        this.butTodayStop.Click += new System.EventHandler(this.butTodayStop_Click);
        //
        // butTodayStart
        //
        this.butTodayStart.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTodayStart.setAutosize(true);
        this.butTodayStart.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTodayStart.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTodayStart.setCornerRadius(4F);
        this.butTodayStart.Location = new System.Drawing.Point(263, 115);
        this.butTodayStart.Name = "butTodayStart";
        this.butTodayStart.Size = new System.Drawing.Size(65, 23);
        this.butTodayStart.TabIndex = 85;
        this.butTodayStart.Text = "Today";
        this.butTodayStart.Click += new System.EventHandler(this.butTodayStart_Click);
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(118, 143);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.Size = new System.Drawing.Size(126, 20);
        this.textDateStop.TabIndex = 84;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(118, 117);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(126, 20);
        this.textDateStart.TabIndex = 84;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(12, 356);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(83, 26);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(327, 356);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(408, 356);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(333, 296);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(150, 17);
        this.label7.TabIndex = 91;
        this.label7.Text = "Only for CCD";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelFunctionalStatus
        //
        this.labelFunctionalStatus.Location = new System.Drawing.Point(17, 320);
        this.labelFunctionalStatus.Name = "labelFunctionalStatus";
        this.labelFunctionalStatus.Size = new System.Drawing.Size(97, 23);
        this.labelFunctionalStatus.TabIndex = 93;
        this.labelFunctionalStatus.Text = "Functional Status";
        this.labelFunctionalStatus.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboEhrFunctionalStatus
        //
        this.comboEhrFunctionalStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboEhrFunctionalStatus.FormattingEnabled = true;
        this.comboEhrFunctionalStatus.Location = new System.Drawing.Point(118, 322);
        this.comboEhrFunctionalStatus.Name = "comboEhrFunctionalStatus";
        this.comboEhrFunctionalStatus.Size = new System.Drawing.Size(210, 21);
        this.comboEhrFunctionalStatus.TabIndex = 92;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(333, 323);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(150, 17);
        this.label8.TabIndex = 94;
        this.label8.Text = "Only for CCD";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormDiseaseEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(495, 394);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.labelFunctionalStatus);
        this.Controls.Add(this.comboEhrFunctionalStatus);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.labelSnomedProblemType);
        this.Controls.Add(this.comboSnomedProblemType);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textSnomed);
        this.Controls.Add(this.butTodayStop);
        this.Controls.Add(this.butTodayStart);
        this.Controls.Add(this.textDateStop);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.labelStatus);
        this.Controls.Add(this.comboStatus);
        this.Controls.Add(this.textIcd9);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textProblem);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDiseaseEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Problem";
        this.Load += new System.EventHandler(this.FormDiseaseEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDiseaseEdit_Load(Object sender, EventArgs e) throws Exception {
        DiseaseDef diseasedef = DiseaseDefs.getItem(DiseaseCur.DiseaseDefNum);
        //guaranteed to have one
        textProblem.Text = diseasedef.DiseaseName;
        String i9descript = ICD9s.getCodeAndDescription(diseasedef.ICD9Code);
        if (StringSupport.equals(i9descript, ""))
        {
            textIcd9.Text = diseasedef.ICD9Code;
        }
        else
        {
            textIcd9.Text = i9descript;
        } 
        String sdescript = Snomeds.getCodeAndDescription(diseasedef.SnomedCode);
        if (StringSupport.equals(sdescript, ""))
        {
            textSnomed.Text = diseasedef.SnomedCode;
        }
        else
        {
            textSnomed.Text = sdescript;
        } 
        comboStatus.Items.Clear();
        for (int i = 0;i < Enum.GetNames(ProblemStatus.class).Length;i++)
        {
            comboStatus.Items.Add(Enum.GetNames(ProblemStatus.class)[i]);
        }
        comboStatus.SelectedIndex = ((Enum)DiseaseCur.ProbStatus).ordinal();
        textNote.Text = DiseaseCur.PatNote;
        if (DiseaseCur.DateStart.Year > 1880)
        {
            textDateStart.Text = DiseaseCur.DateStart.ToShortDateString();
        }
         
        if (DiseaseCur.DateStop.Year > 1880)
        {
            textDateStop.Text = DiseaseCur.DateStop.ToShortDateString();
        }
         
        comboSnomedProblemType.Items.Clear();
        comboSnomedProblemType.Items.Add("Problem");
        //0 - Default value.  Problem (finding).  55607006
        comboSnomedProblemType.Items.Add("Finding");
        //1 - Clinical finding (finding).  404684003
        comboSnomedProblemType.Items.Add("Complaint");
        //2 - Complaint (finding).  409586006
        comboSnomedProblemType.Items.Add("Dignosis");
        //3 - Diagnosis interpretation (observable entity).  282291009
        comboSnomedProblemType.Items.Add("Condition");
        //4 - Disease (disorder).  64572001
        comboSnomedProblemType.Items.Add("FunctionalLimitation");
        //5 - Finding of functional performance and activity (finding).  248536006
        comboSnomedProblemType.Items.Add("Symptom");
        //6 - Finding reported by subject or history provider (finding).  418799008
        if (StringSupport.equals(DiseaseCur.SnomedProblemType, "404684003"))
        {
            //Finding
            comboSnomedProblemType.SelectedIndex = 1;
        }
        else if (StringSupport.equals(DiseaseCur.SnomedProblemType, "409586006"))
        {
            //Complaint
            comboSnomedProblemType.SelectedIndex = 2;
        }
        else if (StringSupport.equals(DiseaseCur.SnomedProblemType, "282291009"))
        {
            //Dignosis
            comboSnomedProblemType.SelectedIndex = 3;
        }
        else if (StringSupport.equals(DiseaseCur.SnomedProblemType, "64572001"))
        {
            //Condition
            comboSnomedProblemType.SelectedIndex = 4;
        }
        else if (StringSupport.equals(DiseaseCur.SnomedProblemType, "248536006"))
        {
            //FunctionalLimitation
            comboSnomedProblemType.SelectedIndex = 5;
        }
        else if (StringSupport.equals(DiseaseCur.SnomedProblemType, "418799008"))
        {
            //Symptom
            comboSnomedProblemType.SelectedIndex = 6;
        }
        else
        {
            //Problem
            comboSnomedProblemType.SelectedIndex = 0;
        }      
        comboEhrFunctionalStatus.Items.Clear();
        String[] arrayFunctionalStatusNames = Enum.GetNames(FunctionalStatus.class);
        for (int i = 0;i < arrayFunctionalStatusNames.Length;i++)
        {
            comboEhrFunctionalStatus.Items.Add(Lan.g(this, arrayFunctionalStatusNames[i]));
        }
        comboEhrFunctionalStatus.SelectedIndex = ((Enum)DiseaseCur.FunctionStatus).ordinal();
    }

    //The default value is 'Problem'
    private void butTodayStart_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Now.ToShortDateString();
        DiseaseCur.DateStart = DateTime.Now;
    }

    private void butTodayStop_Click(Object sender, EventArgs e) throws Exception {
        textDateStop.Text = DateTime.Now.ToShortDateString();
        DiseaseCur.DateStop = DateTime.Now;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            //This code is never hit in current implementation 09/26/2013.
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        List<Vitalsign> listVitals = Vitalsigns.getListFromPregDiseaseNum(DiseaseCur.DiseaseNum);
        if (listVitals.Count > 0)
        {
            //if attached to vital sign exam, block delete
            String dates = "";
            for (int i = 0;i < listVitals.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                dates += "\r\n" + listVitals[i].DateTaken.ToShortDateString();
            }
            MsgBox.show(this,"Not allowed to delete this problem.  It is attached to " + listVitals.Count.ToString() + "vital sign exams with dates including:" + dates + ".");
            return ;
        }
        else
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
            {
                return ;
            }
             
        } 
        SecurityLogs.makeLogEntry(Permissions.PatProblemListEdit,DiseaseCur.PatNum,DiseaseDefs.getName(DiseaseCur.DiseaseDefNum) + " deleted");
        Diseases.delete(DiseaseCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
        {
            MsgBox.show(this,"Please fix date.");
            return ;
        }
         
        DiseaseCur.DateStart = PIn.Date(textDateStart.Text);
        DiseaseCur.DateStop = PIn.Date(textDateStop.Text);
        DiseaseCur.ProbStatus = (ProblemStatus)comboStatus.SelectedIndex;
        DiseaseCur.PatNote = textNote.Text;
        if (comboSnomedProblemType.SelectedIndex == 1)
        {
            //Finding
            DiseaseCur.SnomedProblemType = "404684003";
        }
        else if (comboSnomedProblemType.SelectedIndex == 2)
        {
            //Complaint
            DiseaseCur.SnomedProblemType = "409586006";
        }
        else if (comboSnomedProblemType.SelectedIndex == 3)
        {
            //Dignosis
            DiseaseCur.SnomedProblemType = "282291009";
        }
        else if (comboSnomedProblemType.SelectedIndex == 4)
        {
            //Condition
            DiseaseCur.SnomedProblemType = "64572001";
        }
        else if (comboSnomedProblemType.SelectedIndex == 5)
        {
            //FunctionalLimitation
            DiseaseCur.SnomedProblemType = "248536006";
        }
        else if (comboSnomedProblemType.SelectedIndex == 6)
        {
            //Symptom
            DiseaseCur.SnomedProblemType = "418799008";
        }
        else
        {
            //Problem
            DiseaseCur.SnomedProblemType = "55607006";
        }      
        DiseaseCur.FunctionStatus = (FunctionalStatus)comboEhrFunctionalStatus.SelectedIndex;
        if (IsNew)
        {
            //This code is never hit in current implementation 09/26/2013.
            Diseases.insert(DiseaseCur);
            SecurityLogs.makeLogEntry(Permissions.PatProblemListEdit,DiseaseCur.PatNum,DiseaseDefs.getName(DiseaseCur.DiseaseDefNum) + " added");
        }
        else
        {
            //See if this problem is the pregnancy linked to a vitalsign exam
            List<Vitalsign> listVitalsAttached = Vitalsigns.getListFromPregDiseaseNum(DiseaseCur.DiseaseNum);
            if (listVitalsAttached.Count > 0)
            {
                //See if the vitalsign exam date is now outside of the active dates of the disease (pregnancy)
                String dates = "";
                for (int i = 0;i < listVitalsAttached.Count;i++)
                {
                    if (listVitalsAttached[i].DateTaken < DiseaseCur.DateStart || (DiseaseCur.DateStop.Year > 1880 && listVitalsAttached[i].DateTaken > DiseaseCur.DateStop))
                    {
                        dates += "\r\n" + listVitalsAttached[i].DateTaken.ToShortDateString();
                    }
                     
                }
                //If vitalsign exam is now outside the dates of the problem, tell the user they must fix the dates of the pregnancy dx
                if (dates.Length > 0)
                {
                    MsgBox.show(this,"This problem is attached to 1 or more vital sign exams as a pregnancy diagnosis with dates:" + dates + "\r\nNot allowed to change the active dates of the diagnosis to be outside the dates of the exam(s).  You must first remove the diagnosis from the vital sign exam(s).");
                    return ;
                }
                 
            }
             
            Diseases.update(DiseaseCur);
            SecurityLogs.makeLogEntry(Permissions.PatProblemListEdit,DiseaseCur.PatNum,DiseaseDefs.getName(DiseaseCur.DiseaseDefNum) + " edited");
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


