//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDiseaseDefs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.FamilyHealth;
import OpenDentBusiness.FamilyHealths;
import OpenDentBusiness.FamilyRelationship;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;
import OpenDental.Properties.Resources;

public class FormFamilyHealthEdit  extends Form 
{

    public FamilyHealth FamilyHealthCur;
    private DiseaseDef DisDefCur;
    public FormFamilyHealthEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formFamilyHealthEdit_Load(Object sender, EventArgs e) throws Exception {
        String[] familyRelationships = Enum.GetNames(FamilyRelationship.class);
        for (int i = 0;i < familyRelationships.Length;i++)
        {
            listRelationship.Items.Add(Lan.g("enumFamilyRelationship", familyRelationships[i]));
        }
        listRelationship.SelectedIndex = ((Enum)FamilyHealthCur.Relationship).ordinal();
        if (FamilyHealthCur.getIsNew())
        {
            return ;
        }
         
        //Don't need to set any of the info below.  All null.
        DisDefCur = DiseaseDefs.getItem(FamilyHealthCur.DiseaseDefNum);
        //Validation is done when deleting diseaseDefs to make sure they are not in use by FamilyHealths.
        textProblem.Text = DisDefCur.DiseaseName;
        textSnomed.Text = DisDefCur.SnomedCode;
        textName.Text = FamilyHealthCur.PersonName;
    }

    private void butPick_Click(Object sender, EventArgs e) throws Exception {
        FormDiseaseDefs FormD = new FormDiseaseDefs();
        FormD.IsSelectionMode = true;
        FormD.ShowDialog();
        if (FormD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DiseaseDef disDef = DiseaseDefs.getItem(FormD.SelectedDiseaseDefNum);
        if (StringSupport.equals(disDef.SnomedCode, ""))
        {
            MsgBox.show(this,"Selection must have a SNOMED CT code associated");
            return ;
        }
         
        textProblem.Text = disDef.DiseaseName;
        textSnomed.Text = disDef.SnomedCode;
        DisDefCur = disDef;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (FamilyHealthCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        FamilyHealths.delete(FamilyHealthCur.FamilyHealthNum);
        SecurityLogs.makeLogEntry(Permissions.PatFamilyHealthEdit,FamilyHealthCur.PatNum,FamilyHealthCur.PersonName + " " + FamilyHealthCur.Relationship + " deleted");
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listRelationship.SelectedIndex < 0)
        {
            MsgBox.show(this,"Relationship required.");
            return ;
        }
         
        if (StringSupport.equals(textName.Text.Trim(), ""))
        {
            MsgBox.show(this,"Name required.");
            return ;
        }
         
        if (DisDefCur == null)
        {
            MsgBox.show(this,"Problem required.");
            return ;
        }
         
        FamilyHealthCur.DiseaseDefNum = DisDefCur.DiseaseDefNum;
        FamilyHealthCur.Relationship = (FamilyRelationship)listRelationship.SelectedIndex;
        FamilyHealthCur.PersonName = textName.Text;
        if (FamilyHealthCur.getIsNew())
        {
            SecurityLogs.makeLogEntry(Permissions.PatFamilyHealthEdit,FamilyHealthCur.PatNum,FamilyHealthCur.PersonName + " " + FamilyHealthCur.Relationship + " added");
            FamilyHealths.insert(FamilyHealthCur);
        }
        else
        {
            FamilyHealths.update(FamilyHealthCur);
            SecurityLogs.makeLogEntry(Permissions.PatFamilyHealthEdit,FamilyHealthCur.PatNum,FamilyHealthCur.PersonName + " " + FamilyHealthCur.Relationship + " edited");
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
        this.listRelationship = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textName = new OpenDental.ODtextBox();
        this.textSnomed = new OpenDental.ODtextBox();
        this.textProblem = new OpenDental.ODtextBox();
        this.butPick = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listRelationship
        //
        this.listRelationship.FormattingEnabled = true;
        this.listRelationship.Location = new System.Drawing.Point(129, 29);
        this.listRelationship.Name = "listRelationship";
        this.listRelationship.Size = new System.Drawing.Size(145, 43);
        this.listRelationship.TabIndex = 63;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 130);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(114, 20);
        this.label3.TabIndex = 72;
        this.label3.Text = "SNOMED CT Code";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 104);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(114, 20);
        this.label1.TabIndex = 75;
        this.label1.Text = "Problem";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(12, 78);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(114, 20);
        this.label4.TabIndex = 76;
        this.label4.Text = "Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 29);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(114, 20);
        this.label2.TabIndex = 77;
        this.label2.Text = "Relationship";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.AcceptsTab = true;
        this.textName.DetectUrls = false;
        this.textName.Location = new System.Drawing.Point(129, 78);
        this.textName.Multiline = false;
        this.textName.Name = "textName";
        this.textName.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textName.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textName.Size = new System.Drawing.Size(145, 20);
        this.textName.TabIndex = 74;
        this.textName.Text = "";
        //
        // textSnomed
        //
        this.textSnomed.AcceptsTab = true;
        this.textSnomed.DetectUrls = false;
        this.textSnomed.Location = new System.Drawing.Point(129, 130);
        this.textSnomed.Multiline = false;
        this.textSnomed.Name = "textSnomed";
        this.textSnomed.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textSnomed.ReadOnly = true;
        this.textSnomed.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textSnomed.Size = new System.Drawing.Size(207, 20);
        this.textSnomed.TabIndex = 68;
        this.textSnomed.Text = "";
        //
        // textProblem
        //
        this.textProblem.AcceptsTab = true;
        this.textProblem.DetectUrls = false;
        this.textProblem.Location = new System.Drawing.Point(129, 104);
        this.textProblem.Multiline = false;
        this.textProblem.Name = "textProblem";
        this.textProblem.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textProblem.ReadOnly = true;
        this.textProblem.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textProblem.Size = new System.Drawing.Size(337, 20);
        this.textProblem.TabIndex = 67;
        this.textProblem.Text = "";
        //
        // butPick
        //
        this.butPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick.setAutosize(true);
        this.butPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick.setCornerRadius(4F);
        this.butPick.Location = new System.Drawing.Point(472, 104);
        this.butPick.Name = "butPick";
        this.butPick.Size = new System.Drawing.Size(52, 20);
        this.butPick.TabIndex = 60;
        this.butPick.Text = "Pick";
        this.butPick.Click += new System.EventHandler(this.butPick_Click);
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
        this.butDelete.Location = new System.Drawing.Point(21, 184);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 22;
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
        this.butOK.Location = new System.Drawing.Point(391, 184);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(472, 184);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormFamilyHealthEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(559, 220);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textSnomed);
        this.Controls.Add(this.textProblem);
        this.Controls.Add(this.listRelationship);
        this.Controls.Add(this.butPick);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormFamilyHealthEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Family Health Edit";
        this.Load += new System.EventHandler(this.FormFamilyHealthEdit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butPick;
    private System.Windows.Forms.ListBox listRelationship = new System.Windows.Forms.ListBox();
    private OpenDental.ODtextBox textProblem;
    private OpenDental.ODtextBox textSnomed;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textName;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


