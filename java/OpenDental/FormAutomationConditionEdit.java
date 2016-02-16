//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.AutoCondComparison;
import OpenDentBusiness.AutoCondField;
import OpenDentBusiness.AutomationCondition;
import OpenDentBusiness.AutomationConditions;
import OpenDental.Properties.Resources;

public class FormAutomationConditionEdit  extends Form 
{

    public boolean IsNew = new boolean();
    public AutomationCondition ConditionCur;
    public FormAutomationConditionEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAutomationConditionEdit_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(AutoCondField.class).Length;i++)
        {
            listCompareField.Items.Add(Enum.GetNames(AutoCondField.class)[i]);
            listCompareField.SelectedIndex = 0;
        }
        for (int i = 0;i < Enum.GetNames(AutoCondComparison.class).Length;i++)
        {
            listComparison.Items.Add(Enum.GetNames(AutoCondComparison.class)[i]);
            listComparison.SelectedIndex = 0;
        }
        if (!IsNew)
        {
            textCompareString.Text = ConditionCur.CompareString;
            listCompareField.SelectedIndex = ((Enum)ConditionCur.CompareField).ordinal();
            listComparison.SelectedIndex = ((Enum)ConditionCur.Comparison).ordinal();
        }
         
    }

    /**
    * Logic might get more complex as fields and comparisons are added so a seperate function was made.
    */
    private boolean reasonableLogic() throws Exception {
        AutoCondComparison comp = (AutoCondComparison)listComparison.SelectedIndex;
        AutoCondField cond = (AutoCondField)listCompareField.SelectedIndex;
        //So far Age is only thing that allows GreaterThan or LessThan.
        if (cond != AutoCondField.Age)
        {
            if (comp == AutoCondComparison.GreaterThan || comp == AutoCondComparison.LessThan)
            {
                return false;
            }
             
        }
         
        return true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete this condition?"))
        {
            return ;
        }
         
        try
        {
            AutomationConditions.delete(ConditionCur.AutomationConditionNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textCompareString.Text.Trim(), ""))
        {
            MsgBox.show(this,"Text not allowed to be blank.");
            return ;
        }
         
        if (!reasonableLogic())
        {
            MsgBox.show(this,"Comparison does not make sense with chosen field.");
            return ;
        }
         
        if (((AutoCondField)listCompareField.SelectedIndex == AutoCondField.Gender && !(StringSupport.equals(textCompareString.Text.ToLower(), "m") || StringSupport.equals(textCompareString.Text.ToLower(), "f"))))
        {
            MsgBox.show(this,"Allowed gender values are M or F.");
            return ;
        }
         
        ConditionCur.CompareString = textCompareString.Text;
        ConditionCur.CompareField = (AutoCondField)listCompareField.SelectedIndex;
        ConditionCur.Comparison = (AutoCondComparison)listComparison.SelectedIndex;
        if (IsNew)
        {
            AutomationConditions.insert(ConditionCur);
        }
        else
        {
            AutomationConditions.update(ConditionCur);
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
        this.labelCompareString = new System.Windows.Forms.Label();
        this.textCompareString = new System.Windows.Forms.TextBox();
        this.labelCompareField = new System.Windows.Forms.Label();
        this.labelComparison = new System.Windows.Forms.Label();
        this.listCompareField = new System.Windows.Forms.ListBox();
        this.listComparison = new System.Windows.Forms.ListBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelCompareString
        //
        this.labelCompareString.Location = new System.Drawing.Point(397, 20);
        this.labelCompareString.Name = "labelCompareString";
        this.labelCompareString.Size = new System.Drawing.Size(147, 20);
        this.labelCompareString.TabIndex = 4;
        this.labelCompareString.Text = "Text";
        this.labelCompareString.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textCompareString
        //
        this.textCompareString.Location = new System.Drawing.Point(397, 43);
        this.textCompareString.Name = "textCompareString";
        this.textCompareString.Size = new System.Drawing.Size(316, 20);
        this.textCompareString.TabIndex = 5;
        //
        // labelCompareField
        //
        this.labelCompareField.Location = new System.Drawing.Point(24, 20);
        this.labelCompareField.Name = "labelCompareField";
        this.labelCompareField.Size = new System.Drawing.Size(175, 20);
        this.labelCompareField.TabIndex = 31;
        this.labelCompareField.Text = "Field";
        this.labelCompareField.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // labelComparison
        //
        this.labelComparison.Location = new System.Drawing.Point(231, 20);
        this.labelComparison.Name = "labelComparison";
        this.labelComparison.Size = new System.Drawing.Size(138, 20);
        this.labelComparison.TabIndex = 32;
        this.labelComparison.Text = "Comparison";
        this.labelComparison.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // listCompareField
        //
        this.listCompareField.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listCompareField.FormattingEnabled = true;
        this.listCompareField.Location = new System.Drawing.Point(24, 43);
        this.listCompareField.Name = "listCompareField";
        this.listCompareField.Size = new System.Drawing.Size(181, 212);
        this.listCompareField.TabIndex = 71;
        //
        // listComparison
        //
        this.listComparison.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listComparison.FormattingEnabled = true;
        this.listComparison.Location = new System.Drawing.Point(234, 43);
        this.listComparison.Name = "listComparison";
        this.listComparison.Size = new System.Drawing.Size(138, 212);
        this.listComparison.TabIndex = 72;
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
        this.butDelete.Location = new System.Drawing.Point(24, 302);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 69;
        this.butDelete.Text = "&Delete";
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
        this.butOK.Location = new System.Drawing.Point(638, 261);
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
        this.butCancel.Location = new System.Drawing.Point(638, 302);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAutomationConditionEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(738, 353);
        this.Controls.Add(this.listComparison);
        this.Controls.Add(this.listCompareField);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.labelComparison);
        this.Controls.Add(this.labelCompareField);
        this.Controls.Add(this.textCompareString);
        this.Controls.Add(this.labelCompareString);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormAutomationConditionEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Automation Condition Edit";
        this.Load += new System.EventHandler(this.FormAutomationConditionEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelCompareString = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCompareString = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelCompareField = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelComparison = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.ListBox listCompareField = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listComparison = new System.Windows.Forms.ListBox();
}


