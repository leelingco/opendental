//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSnomeds;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDental.Properties.Resources;

public class FormAllergyEdit  extends Form 
{

    public Allergy AllergyCur;
    private List<AllergyDef> allergyDefList = new List<AllergyDef>();
    private Snomed snomedReaction;
    public FormAllergyEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAllergyEdit_Load(Object sender, EventArgs e) throws Exception {
        int allergyIndex = 0;
        allergyDefList = AllergyDefs.getAll(false);
        if (allergyDefList.Count < 1)
        {
            MsgBox.show(this,"Need to set up at least one Allergy from EHR setup window.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        for (int i = 0;i < allergyDefList.Count;i++)
        {
            comboAllergies.Items.Add(allergyDefList[i].Description);
            if (!AllergyCur.getIsNew() && allergyDefList[i].AllergyDefNum == AllergyCur.AllergyDefNum)
            {
                allergyIndex = i;
            }
             
        }
        snomedReaction = Snomeds.getByCode(AllergyCur.SnomedReaction);
        if (snomedReaction != null)
        {
            textSnomedReaction.Text = snomedReaction.Description;
        }
         
        if (!AllergyCur.getIsNew())
        {
            if (AllergyCur.DateAdverseReaction < DateTime.Parse("01-01-1880"))
            {
                textDate.Text = "";
            }
            else
            {
                textDate.Text = AllergyCur.DateAdverseReaction.ToShortDateString();
            } 
            comboAllergies.SelectedIndex = allergyIndex;
            textReaction.Text = AllergyCur.Reaction;
            checkActive.Checked = AllergyCur.StatusIsActive;
        }
        else
        {
            comboAllergies.SelectedIndex = 0;
        } 
    }

    private void butSnomedReactionSelect_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds formS = new FormSnomeds();
        formS.IsSelectionMode = true;
        if (formS.ShowDialog() == DialogResult.OK)
        {
            snomedReaction = formS.SelectedSnomed;
            textSnomedReaction.Text = snomedReaction.Description;
        }
         
    }

    private void butNoneSnomedReaction_Click(Object sender, EventArgs e) throws Exception {
        snomedReaction = null;
        textSnomedReaction.Text = "";
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (AllergyCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        Allergies.delete(AllergyCur.AllergyNum);
        SecurityLogs.makeLogEntry(Permissions.PatAllergyListEdit,AllergyCur.PatNum,AllergyDefs.getDescription(AllergyCur.AllergyDefNum) + " deleted");
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //Validate
        if (!StringSupport.equals(textDate.Text, ""))
        {
            try
            {
                DateTime.Parse(textDate.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show("Please input a valid date.");
                return ;
            }
        
        }
         
        //Save
        if (!StringSupport.equals(textDate.Text, ""))
        {
            AllergyCur.DateAdverseReaction = DateTime.Parse(textDate.Text);
        }
        else
        {
            AllergyCur.DateAdverseReaction = DateTime.MinValue;
        } 
        AllergyCur.AllergyDefNum = allergyDefList[comboAllergies.SelectedIndex].AllergyDefNum;
        AllergyCur.Reaction = textReaction.Text;
        AllergyCur.SnomedReaction = "";
        if (snomedReaction != null)
        {
            AllergyCur.SnomedReaction = snomedReaction.SnomedCode;
        }
         
        AllergyCur.StatusIsActive = checkActive.Checked;
        if (AllergyCur.getIsNew())
        {
            Allergies.insert(AllergyCur);
            SecurityLogs.makeLogEntry(Permissions.PatAllergyListEdit,AllergyCur.PatNum,AllergyDefs.getDescription(AllergyCur.AllergyDefNum) + " added");
        }
        else
        {
            Allergies.update(AllergyCur);
            SecurityLogs.makeLogEntry(Permissions.PatAllergyListEdit,AllergyCur.PatNum,AllergyDefs.getDescription(AllergyCur.AllergyDefNum) + " edited");
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
        this.checkActive = new System.Windows.Forms.CheckBox();
        this.textReaction = new System.Windows.Forms.TextBox();
        this.labelReaction = new System.Windows.Forms.Label();
        this.labelAllergy = new System.Windows.Forms.Label();
        this.comboAllergies = new System.Windows.Forms.ComboBox();
        this.textDate = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textSnomedReaction = new System.Windows.Forms.TextBox();
        this.butNoneSnomedReaction = new OpenDental.UI.Button();
        this.butSnomedReactionSelect = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // checkActive
        //
        this.checkActive.Checked = true;
        this.checkActive.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkActive.Location = new System.Drawing.Point(59, 153);
        this.checkActive.Name = "checkActive";
        this.checkActive.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkActive.Size = new System.Drawing.Size(100, 24);
        this.checkActive.TabIndex = 11;
        this.checkActive.Text = "Is Active";
        this.checkActive.UseVisualStyleBackColor = true;
        //
        // textReaction
        //
        this.textReaction.Location = new System.Drawing.Point(144, 74);
        this.textReaction.Multiline = true;
        this.textReaction.Name = "textReaction";
        this.textReaction.Size = new System.Drawing.Size(272, 53);
        this.textReaction.TabIndex = 10;
        //
        // labelReaction
        //
        this.labelReaction.Location = new System.Drawing.Point(7, 74);
        this.labelReaction.Name = "labelReaction";
        this.labelReaction.Size = new System.Drawing.Size(134, 20);
        this.labelReaction.TabIndex = 9;
        this.labelReaction.Text = "Reaction Description";
        this.labelReaction.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelAllergy
        //
        this.labelAllergy.Location = new System.Drawing.Point(60, 18);
        this.labelAllergy.Name = "labelAllergy";
        this.labelAllergy.Size = new System.Drawing.Size(81, 20);
        this.labelAllergy.TabIndex = 12;
        this.labelAllergy.Text = "Allergy";
        this.labelAllergy.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboAllergies
        //
        this.comboAllergies.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAllergies.FormattingEnabled = true;
        this.comboAllergies.Location = new System.Drawing.Point(144, 19);
        this.comboAllergies.Name = "comboAllergies";
        this.comboAllergies.Size = new System.Drawing.Size(272, 21);
        this.comboAllergies.TabIndex = 13;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(144, 133);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 15;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(4, 132);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(137, 20);
        this.label1.TabIndex = 16;
        this.label1.Text = "Date Adverse Reaction";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(0, 46);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(141, 20);
        this.label2.TabIndex = 26;
        this.label2.Text = "SNOMED CT Reaction";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSnomedReaction
        //
        this.textSnomedReaction.Location = new System.Drawing.Point(144, 46);
        this.textSnomedReaction.Name = "textSnomedReaction";
        this.textSnomedReaction.ReadOnly = true;
        this.textSnomedReaction.Size = new System.Drawing.Size(272, 20);
        this.textSnomedReaction.TabIndex = 25;
        //
        // butNoneSnomedReaction
        //
        this.butNoneSnomedReaction.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneSnomedReaction.setAutosize(true);
        this.butNoneSnomedReaction.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneSnomedReaction.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneSnomedReaction.setCornerRadius(4F);
        this.butNoneSnomedReaction.Location = new System.Drawing.Point(449, 46);
        this.butNoneSnomedReaction.Name = "butNoneSnomedReaction";
        this.butNoneSnomedReaction.Size = new System.Drawing.Size(51, 22);
        this.butNoneSnomedReaction.TabIndex = 28;
        this.butNoneSnomedReaction.Text = "None";
        this.butNoneSnomedReaction.Click += new System.EventHandler(this.butNoneSnomedReaction_Click);
        //
        // butSnomedReactionSelect
        //
        this.butSnomedReactionSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSnomedReactionSelect.setAutosize(true);
        this.butSnomedReactionSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSnomedReactionSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSnomedReactionSelect.setCornerRadius(4F);
        this.butSnomedReactionSelect.Location = new System.Drawing.Point(421, 46);
        this.butSnomedReactionSelect.Name = "butSnomedReactionSelect";
        this.butSnomedReactionSelect.Size = new System.Drawing.Size(22, 22);
        this.butSnomedReactionSelect.TabIndex = 27;
        this.butSnomedReactionSelect.Text = "...";
        this.butSnomedReactionSelect.Click += new System.EventHandler(this.butSnomedReactionSelect_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(427, 215);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 14;
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
        this.butOK.Location = new System.Drawing.Point(346, 215);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
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
        this.butDelete.Location = new System.Drawing.Point(19, 215);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 2;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormAllergyEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(514, 251);
        this.Controls.Add(this.butNoneSnomedReaction);
        this.Controls.Add(this.butSnomedReactionSelect);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textSnomedReaction);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.comboAllergies);
        this.Controls.Add(this.labelAllergy);
        this.Controls.Add(this.checkActive);
        this.Controls.Add(this.textReaction);
        this.Controls.Add(this.labelReaction);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butDelete);
        this.Name = "FormAllergyEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Allergy Edit";
        this.Load += new System.EventHandler(this.FormAllergyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.CheckBox checkActive = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textReaction = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelReaction = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelAllergy = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboAllergies = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butNoneSnomedReaction;
    private OpenDental.UI.Button butSnomedReactionSelect;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSnomedReaction = new System.Windows.Forms.TextBox();
}


