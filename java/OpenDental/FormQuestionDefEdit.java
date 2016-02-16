//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import OpenDental.FormQuestionDefEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.QuestionDef;
import OpenDentBusiness.QuestionDefs;
import OpenDentBusiness.QuestionType;

/**
* 
*/
public class FormQuestionDefEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textQuestion = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button buttonDelete;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private ListBox listType = new ListBox();
    private QuestionDef QuestionDefCur;
    /**
    * 
    */
    public FormQuestionDefEdit(QuestionDef questionDefCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        QuestionDefCur = questionDefCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuestionDefEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textQuestion = new System.Windows.Forms.TextBox();
        this.buttonDelete = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
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
        this.butCancel.Location = new System.Drawing.Point(518, 298);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 2;
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
        this.butOK.Location = new System.Drawing.Point(518, 257);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textQuestion
        //
        this.textQuestion.Location = new System.Drawing.Point(118, 68);
        this.textQuestion.Multiline = true;
        this.textQuestion.Name = "textQuestion";
        this.textQuestion.Size = new System.Drawing.Size(475, 151);
        this.textQuestion.TabIndex = 0;
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
        this.buttonDelete.Location = new System.Drawing.Point(54, 298);
        this.buttonDelete.Name = "buttonDelete";
        this.buttonDelete.Size = new System.Drawing.Size(82, 25);
        this.buttonDelete.TabIndex = 3;
        this.buttonDelete.Text = "&Delete";
        this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 65);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 23);
        this.label1.TabIndex = 5;
        this.label1.Text = "Question";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 12);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 23);
        this.label2.TabIndex = 6;
        this.label2.Text = "Type";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listType
        //
        this.listType.FormattingEnabled = true;
        this.listType.Location = new System.Drawing.Point(118, 16);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(120, 30);
        this.listType.TabIndex = 7;
        //
        // FormQuestionDefEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(641, 347);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.buttonDelete);
        this.Controls.Add(this.textQuestion);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQuestionDefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Question";
        this.Load += new System.EventHandler(this.FormQuestionDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formQuestionDefEdit_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(QuestionType.class).Length;i++)
        {
            listType.Items.Add(Lan.g("enumQuestionType", Enum.GetNames(QuestionType.class)[i]));
        }
        listType.SelectedIndex = ((Enum)QuestionDefCur.QuestType).ordinal();
        textQuestion.Text = QuestionDefCur.Description;
    }

    private void buttonDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        QuestionDefs.delete(QuestionDefCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        QuestionDefCur.QuestType = (QuestionType)listType.SelectedIndex;
        QuestionDefCur.Description = textQuestion.Text;
        if (IsNew)
        {
            QuestionDefs.insert(QuestionDefCur);
        }
        else
        {
            QuestionDefs.update(QuestionDefCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


