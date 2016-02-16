//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDiseaseDefEdit;
import OpenDental.FormIcd10s;
import OpenDental.FormIcd9s;
import OpenDental.FormSnomeds;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Snomeds;

/**
* 
*/
public class FormDiseaseDefEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button buttonDelete;
    private CheckBox checkIsHidden = new CheckBox();
    private Label label1 = new Label();
    private TextBox textICD9 = new TextBox();
    private Label label2 = new Label();
    private TextBox textSnomed = new TextBox();
    private Label label3 = new Label();
    private OpenDental.UI.Button butSnomed;
    private OpenDental.UI.Button butIcd9;
    private OpenDental.UI.Button butIcd10;
    private Label label4 = new Label();
    private TextBox textIcd10 = new TextBox();
    public DiseaseDef DiseaseDefCur;
    /**
    * 
    */
    public FormDiseaseDefEdit(DiseaseDef diseaseDefCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        DiseaseDefCur = diseaseDefCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDiseaseDefEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textName = new System.Windows.Forms.TextBox();
        this.buttonDelete = new OpenDental.UI.Button();
        this.checkIsHidden = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textICD9 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textSnomed = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.butSnomed = new OpenDental.UI.Button();
        this.butIcd9 = new OpenDental.UI.Button();
        this.butIcd10 = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.textIcd10 = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(372, 172);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(372, 136);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(118, 98);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(308, 20);
        this.textName.TabIndex = 2;
        //
        // buttonDelete
        //
        this.buttonDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.buttonDelete.setAutosize(true);
        this.buttonDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonDelete.setCornerRadius(4F);
        this.buttonDelete.Image = Resources.getdeleteX();
        this.buttonDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.buttonDelete.Location = new System.Drawing.Point(30, 172);
        this.buttonDelete.Name = "buttonDelete";
        this.buttonDelete.Size = new System.Drawing.Size(82, 25);
        this.buttonDelete.TabIndex = 5;
        this.buttonDelete.Text = "&Delete";
        this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
        //
        // checkIsHidden
        //
        this.checkIsHidden.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.Location = new System.Drawing.Point(28, 132);
        this.checkIsHidden.Name = "checkIsHidden";
        this.checkIsHidden.Size = new System.Drawing.Size(104, 24);
        this.checkIsHidden.TabIndex = 4;
        this.checkIsHidden.Text = "Hidden";
        this.checkIsHidden.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 96);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 23);
        this.label1.TabIndex = 5;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textICD9
        //
        this.textICD9.Location = new System.Drawing.Point(118, 20);
        this.textICD9.Name = "textICD9";
        this.textICD9.ReadOnly = true;
        this.textICD9.Size = new System.Drawing.Size(273, 20);
        this.textICD9.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 18);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 23);
        this.label2.TabIndex = 5;
        this.label2.Text = "ICD-9 Code";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSnomed
        //
        this.textSnomed.Location = new System.Drawing.Point(118, 72);
        this.textSnomed.Name = "textSnomed";
        this.textSnomed.ReadOnly = true;
        this.textSnomed.Size = new System.Drawing.Size(273, 20);
        this.textSnomed.TabIndex = 1;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(14, 70);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 23);
        this.label3.TabIndex = 5;
        this.label3.Text = "SNOMED CT Code";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSnomed
        //
        this.butSnomed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSnomed.setAutosize(true);
        this.butSnomed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSnomed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSnomed.setCornerRadius(4F);
        this.butSnomed.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSnomed.Location = new System.Drawing.Point(397, 70);
        this.butSnomed.Name = "butSnomed";
        this.butSnomed.Size = new System.Drawing.Size(29, 25);
        this.butSnomed.TabIndex = 8;
        this.butSnomed.Text = "...";
        this.butSnomed.Click += new System.EventHandler(this.butSnomed_Click);
        //
        // butIcd9
        //
        this.butIcd9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butIcd9.setAutosize(true);
        this.butIcd9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butIcd9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butIcd9.setCornerRadius(4F);
        this.butIcd9.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butIcd9.Location = new System.Drawing.Point(397, 17);
        this.butIcd9.Name = "butIcd9";
        this.butIcd9.Size = new System.Drawing.Size(29, 25);
        this.butIcd9.TabIndex = 9;
        this.butIcd9.Text = "...";
        this.butIcd9.Click += new System.EventHandler(this.butIcd9_Click);
        //
        // butIcd10
        //
        this.butIcd10.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butIcd10.setAutosize(true);
        this.butIcd10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butIcd10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butIcd10.setCornerRadius(4F);
        this.butIcd10.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butIcd10.Location = new System.Drawing.Point(397, 43);
        this.butIcd10.Name = "butIcd10";
        this.butIcd10.Size = new System.Drawing.Size(29, 25);
        this.butIcd10.TabIndex = 12;
        this.butIcd10.Text = "...";
        this.butIcd10.Click += new System.EventHandler(this.butIcd10_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(14, 44);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 23);
        this.label4.TabIndex = 11;
        this.label4.Text = "ICD-10 Code";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textIcd10
        //
        this.textIcd10.Location = new System.Drawing.Point(118, 46);
        this.textIcd10.Name = "textIcd10";
        this.textIcd10.ReadOnly = true;
        this.textIcd10.Size = new System.Drawing.Size(273, 20);
        this.textIcd10.TabIndex = 10;
        //
        // FormDiseaseDefEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(460, 208);
        this.Controls.Add(this.butIcd10);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textIcd10);
        this.Controls.Add(this.butIcd9);
        this.Controls.Add(this.butSnomed);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkIsHidden);
        this.Controls.Add(this.buttonDelete);
        this.Controls.Add(this.textSnomed);
        this.Controls.Add(this.textICD9);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDiseaseDefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Problem Def Edit";
        this.Load += new System.EventHandler(this.FormDiseaseDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDiseaseDefEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textName.Text = DiseaseDefCur.DiseaseName;
        String i9descript = ICD9s.getCodeAndDescription(DiseaseDefCur.ICD9Code);
        if (StringSupport.equals(i9descript, ""))
        {
            textICD9.Text = DiseaseDefCur.ICD9Code;
        }
        else
        {
            textICD9.Text = i9descript;
        } 
        String sdescript = Snomeds.getCodeAndDescription(DiseaseDefCur.SnomedCode);
        if (StringSupport.equals(sdescript, ""))
        {
            textSnomed.Text = DiseaseDefCur.SnomedCode;
        }
        else
        {
            textSnomed.Text = sdescript;
        } 
        checkIsHidden.Checked = DiseaseDefCur.IsHidden;
    }

    private void butSnomed_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        FormS.IsSelectionMode = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (DiseaseDefs.containsSnomed(FormS.SelectedSnomed.SnomedCode,DiseaseDefCur.DiseaseDefNum))
        {
            //DiseaseDefNum could be zero
            MsgBox.show(this,"Snomed code already exists in the problems list.");
            return ;
        }
         
        DiseaseDefCur.SnomedCode = FormS.SelectedSnomed.SnomedCode;
        String sdescript = Snomeds.getCodeAndDescription(FormS.SelectedSnomed.SnomedCode);
        if (StringSupport.equals(sdescript, ""))
        {
            textSnomed.Text = FormS.SelectedSnomed.SnomedCode;
        }
        else
        {
            textSnomed.Text = sdescript;
        } 
    }

    private void butIcd9_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s FormI = new FormIcd9s();
        FormI.IsSelectionMode = true;
        FormI.ShowDialog();
        if (FormI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (DiseaseDefs.containsICD9(FormI.SelectedIcd9.ICD9Code,DiseaseDefCur.DiseaseDefNum))
        {
            MsgBox.show(this,"ICD-9 code already exists in the problems list.");
            return ;
        }
         
        DiseaseDefCur.ICD9Code = FormI.SelectedIcd9.ICD9Code;
        String i9descript = ICD9s.getCodeAndDescription(FormI.SelectedIcd9.ICD9Code);
        if (StringSupport.equals(i9descript, ""))
        {
            textICD9.Text = FormI.SelectedIcd9.ICD9Code;
        }
        else
        {
            textICD9.Text = i9descript;
        } 
    }

    private void butIcd10_Click(Object sender, EventArgs e) throws Exception {
        FormIcd10s FormI = new FormIcd10s();
        FormI.IsSelectionMode = true;
        FormI.ShowDialog();
        if (FormI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (DiseaseDefs.containsIcd10(FormI.SelectedIcd10.Icd10Code,DiseaseDefCur.DiseaseDefNum))
        {
            MsgBox.show(this,"ICD-10 code already exists in the problems list.");
            return ;
        }
         
        DiseaseDefCur.Icd10Code = FormI.SelectedIcd10.Icd10Code;
        textIcd10.Text = FormI.SelectedIcd10.Icd10Code + "-" + FormI.SelectedIcd10.Description;
    }

    private void buttonDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        try
        {
            DiseaseDefs.delete(DiseaseDefCur);
            SecurityLogs.MakeLogEntry(Permissions.ProblemEdit, 0, DiseaseDefCur.DiseaseName + " deleted.");
            DialogResult = DialogResult.OK;
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textName.Text, ""))
        {
            MsgBox.show(this,"Not allowed to create a Disease Definition without a description.");
            return ;
        }
         
        //Icd9Code and SnomedCode set on load or on return from code picker forms
        DiseaseDefCur.DiseaseName = textName.Text;
        DiseaseDefCur.IsHidden = checkIsHidden.Checked;
        if (IsNew)
        {
            DiseaseDefs.insert(DiseaseDefCur);
            SecurityLogs.MakeLogEntry(Permissions.ProblemEdit, 0, DiseaseDefCur.DiseaseName + " added.");
        }
        else
        {
            DiseaseDefs.update(DiseaseDefCur);
            SecurityLogs.MakeLogEntry(Permissions.ProblemEdit, 0, DiseaseDefCur.DiseaseName);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


