//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:39 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormApptRuleEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AppointmentRule;
import OpenDentBusiness.AppointmentRules;
import OpenDentBusiness.ProcedureCodes;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptRuleEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private Label label1 = new Label();
    private TextBox textRuleDesc = new TextBox();
    private TextBox textCodeStart = new TextBox();
    private Label label2 = new Label();
    private TextBox textCodeEnd = new TextBox();
    private Label label3 = new Label();
    private CheckBox checkIsEnabled = new CheckBox();
    private AppointmentRule ApptRuleCur;
    /**
    * 
    */
    public FormApptRuleEdit(AppointmentRule apptRuleCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        ApptRuleCur = apptRuleCur.clone();
        initializeComponent();
        Lan.f(this);
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
        OpenDental.UI.Button butDelete;
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptRuleEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textRuleDesc = new System.Windows.Forms.TextBox();
        this.textCodeStart = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textCodeEnd = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.checkIsEnabled = new System.Windows.Forms.CheckBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butDelete
        //
        butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        butDelete.setAutosize(true);
        butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        butDelete.setCornerRadius(4F);
        butDelete.Image = Resources.getdeleteX();
        butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        butDelete.Location = new System.Drawing.Point(15, 178);
        butDelete.Name = "butDelete";
        butDelete.Size = new System.Drawing.Size(75, 26);
        butDelete.TabIndex = 16;
        butDelete.Text = "&Delete";
        butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(111, 20);
        this.label1.TabIndex = 11;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRuleDesc
        //
        this.textRuleDesc.Location = new System.Drawing.Point(125, 25);
        this.textRuleDesc.Name = "textRuleDesc";
        this.textRuleDesc.Size = new System.Drawing.Size(297, 20);
        this.textRuleDesc.TabIndex = 0;
        //
        // textCodeStart
        //
        this.textCodeStart.Location = new System.Drawing.Point(125, 51);
        this.textCodeStart.Name = "textCodeStart";
        this.textCodeStart.Size = new System.Drawing.Size(113, 20);
        this.textCodeStart.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 50);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 20);
        this.label2.TabIndex = 18;
        this.label2.Text = "Code Start";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCodeEnd
        //
        this.textCodeEnd.Location = new System.Drawing.Point(125, 77);
        this.textCodeEnd.Name = "textCodeEnd";
        this.textCodeEnd.Size = new System.Drawing.Size(113, 20);
        this.textCodeEnd.TabIndex = 2;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 76);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(111, 20);
        this.label3.TabIndex = 20;
        this.label3.Text = "Code End";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsEnabled
        //
        this.checkIsEnabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsEnabled.Location = new System.Drawing.Point(9, 103);
        this.checkIsEnabled.Name = "checkIsEnabled";
        this.checkIsEnabled.Size = new System.Drawing.Size(130, 21);
        this.checkIsEnabled.TabIndex = 3;
        this.checkIsEnabled.Text = "Is Enabled";
        this.checkIsEnabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsEnabled.UseVisualStyleBackColor = true;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(347, 146);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(347, 178);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormApptRuleEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(448, 222);
        this.Controls.Add(this.checkIsEnabled);
        this.Controls.Add(this.textCodeEnd);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textCodeStart);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textRuleDesc);
        this.Controls.Add(butDelete);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptRuleEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment Rule";
        this.Load += new System.EventHandler(this.FormApptRuleEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formApptRuleEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textRuleDesc.Text = ApptRuleCur.RuleDesc;
        textCodeStart.Text = ApptRuleCur.CodeStart;
        textCodeEnd.Text = ApptRuleCur.CodeEnd;
        checkIsEnabled.Checked = ApptRuleCur.IsEnabled;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        AppointmentRules.delete(ApptRuleCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textRuleDesc.Text, ""))
        {
            MsgBox.show(this,"Description not allowed to be blank.");
            return ;
        }
         
        if (!ProcedureCodes.IsValidCode(textCodeStart.Text) || !ProcedureCodes.IsValidCode(textCodeEnd.Text))
        {
            MsgBox.show(this,"Start and end codes must be valid procedure codes.");
            return ;
        }
         
        ApptRuleCur.RuleDesc = textRuleDesc.Text;
        ApptRuleCur.CodeStart = textCodeStart.Text;
        ApptRuleCur.CodeEnd = textCodeEnd.Text;
        ApptRuleCur.IsEnabled = checkIsEnabled.Checked;
        if (IsNew)
        {
            AppointmentRules.insert(ApptRuleCur);
        }
        else
        {
            AppointmentRules.update(ApptRuleCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


