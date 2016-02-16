//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldDefs;
import OpenDentBusiness.SheetTypeEnum;

public class FormSheetFieldExam  extends Form 
{

    private List<SheetDef> AvailExamDefs = new List<SheetDef>();
    public String ExamFieldSelected = new String();
    public FormSheetFieldExam() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldDefEdit_Load(Object sender, EventArgs e) throws Exception {
        AvailExamDefs = SheetDefs.getCustomForType(SheetTypeEnum.ExamSheet);
        if (AvailExamDefs == null)
        {
            MsgBox.show(this,"No custom Exam Sheets are defined.");
            return ;
        }
         
        listExamSheets.Items.Clear();
        for (int i = 0;i < AvailExamDefs.Count;i++)
        {
            listExamSheets.Items.Add(AvailExamDefs[i].Description);
        }
        listExamSheets.SetSelected(0, true);
        fillFieldList();
    }

    private void fillFieldList() throws Exception {
        listAvailFields.Sorted = true;
        //will alphabetize, since we are adding either FieldName,ReportableName, or RadioButtonGroup depending on field type
        listAvailFields.Items.Clear();
        //Add exam sheet fields to the list
        List<SheetFieldDef> availFields = SheetFieldDefs.GetForExamSheet(AvailExamDefs[listExamSheets.SelectedIndex].SheetDefNum);
        for (int i = 0;i < availFields.Count;i++)
        {
            if (StringSupport.equals(availFields[i].FieldName, ""))
            {
                continue;
            }
             
            if (!StringSupport.equals(availFields[i].FieldName, "misc"))
            {
                //This is an internally defined field
                listAvailFields.Items.Add(availFields[i].FieldName);
                continue;
            }
             
            //misc:
            if (!StringSupport.equals(availFields[i].RadioButtonGroup, ""))
            {
                //Only gets set if field is a 'misc' check box and assigned to a group
                if (listAvailFields.Items.Contains(availFields[i].RadioButtonGroup))
                {
                    continue;
                }
                else
                {
                    listAvailFields.Items.Add(availFields[i].RadioButtonGroup);
                    continue;
                } 
            }
             
            if (!StringSupport.equals(availFields[i].ReportableName, ""))
            {
                //Not internal type or part of a RadioButtonGroup so just add the reportable name if available
                listAvailFields.Items.Add(availFields[i].ReportableName);
                continue;
            }
             
        }
    }

    private void listExamSheets_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        fillFieldList();
    }

    private void listAvailFields_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listAvailFields.SelectedIndex == -1)
        {
            return ;
        }
         
        ExamFieldSelected = SheetTypeEnum.ExamSheet.ToString() + ":" + AvailExamDefs[listExamSheets.SelectedIndex].Description + ";" + listAvailFields.SelectedItem.ToString();
        //either RadioButtonGroup or ReportableName or internally defined field name
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listAvailFields.SelectedIndex == -1)
        {
            //if there is no selected reportable field
            MsgBox.show(this,"You must select a field first.");
            return ;
        }
         
        //example:  ExamSheet:NewPatient;Race
        ExamFieldSelected = "ExamSheet:" + AvailExamDefs[listExamSheets.SelectedIndex].Description + ";" + listAvailFields.SelectedItem.ToString();
        //either RadioButtonGroup or ReportableName or internally defined field name
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
        this.label2 = new System.Windows.Forms.Label();
        this.listExamSheets = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label10 = new System.Windows.Forms.Label();
        this.listAvailFields = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 47);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(108, 16);
        this.label2.TabIndex = 86;
        this.label2.Text = "Exam Sheet";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listExamSheets
        //
        this.listExamSheets.FormattingEnabled = true;
        this.listExamSheets.Location = new System.Drawing.Point(15, 66);
        this.listExamSheets.Name = "listExamSheets";
        this.listExamSheets.Size = new System.Drawing.Size(142, 290);
        this.listExamSheets.TabIndex = 85;
        this.listExamSheets.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listExamSheets_MouseClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(13, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(393, 33);
        this.label1.TabIndex = 87;
        this.label1.Text = "The value for this field will be retrieved later from the most recent exam sheet." + "";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(274, 376);
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
        this.butCancel.Location = new System.Drawing.Point(355, 376);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(193, 47);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(108, 16);
        this.label10.TabIndex = 102;
        this.label10.Text = "Available Fields";
        this.label10.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listAvailFields
        //
        this.listAvailFields.FormattingEnabled = true;
        this.listAvailFields.Location = new System.Drawing.Point(195, 66);
        this.listAvailFields.Name = "listAvailFields";
        this.listAvailFields.Size = new System.Drawing.Size(142, 290);
        this.listAvailFields.TabIndex = 101;
        this.listAvailFields.DoubleClick += new System.EventHandler(this.listAvailFields_DoubleClick);
        //
        // FormSheetFieldExam
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(442, 412);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.listAvailFields);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listExamSheets);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldExam";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Exam Sheet Field";
        this.Load += new System.EventHandler(this.FormSheetFieldDefEdit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listExamSheets = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listAvailFields = new System.Windows.Forms.ListBox();
}


