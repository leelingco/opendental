//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.OutInCheck;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldsAvailable;
import OpenDentBusiness.SheetTypeEnum;
import OpenDental.Properties.Resources;

public class FormSheetFieldCheckBox  extends Form 
{

    /**
    * This is the object we are editing.
    */
    public SheetFieldDef SheetFieldDefCur;
    /**
    * We need access to a few other fields of the sheetDef.
    */
    public SheetDef SheetDefCur;
    private List<SheetFieldDef> AvailFields = new List<SheetFieldDef>();
    public boolean IsReadOnly = new boolean();
    private List<String> radioButtonValues = new List<String>();
    private List<AllergyDef> allergyList = new List<AllergyDef>();
    private List<String> inputMedList = new List<String>();
    /**
    * True if the sheet type is MedicalHistory.
    */
    private boolean isMedHistSheet = new boolean();
    public boolean IsNew = new boolean();
    public FormSheetFieldCheckBox() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldCheckBox_Load(Object sender, EventArgs e) throws Exception {
        labelReportableName.Visible = false;
        textReportableName.Visible = false;
        if (SheetFieldDefCur.FieldName.StartsWith("misc"))
        {
            labelReportableName.Visible = true;
            textReportableName.Visible = true;
            textReportableName.Text = SheetFieldDefCur.ReportableName;
        }
         
        if (IsReadOnly)
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
         
        //not allowed to change sheettype or fieldtype once created.  So get all avail fields for this sheettype
        AvailFields = SheetFieldsAvailable.getList(SheetDefCur.SheetType,OutInCheck.Check);
        isMedHistSheet = SheetDefCur.SheetType == SheetTypeEnum.MedicalHistory;
        listFields.Items.Clear();
        for (int i = 0;i < AvailFields.Count;i++)
        {
            //static text is not one of the options.
            listFields.Items.Add(AvailFields[i].FieldName);
            //Sheets will have dynamic field names like "allergy:Pen".  They will always start with a valid FieldName.
            if (SheetFieldDefCur.FieldName.StartsWith(AvailFields[i].FieldName))
            {
                listFields.SelectedIndex = i;
            }
             
        }
        if (isMedHistSheet)
        {
            radioYes.Checked = true;
            if (SheetFieldDefCur.FieldName.StartsWith("allergy:"))
            {
                fillListMedical(MedicalListType.allergy);
                SetListMedicalSelectedIndex(MedicalListType.allergy, SheetFieldDefCur.FieldName.Remove(0, 8));
            }
            else if (SheetFieldDefCur.FieldName.StartsWith("problem:"))
            {
                fillListMedical(MedicalListType.problem);
                SetListMedicalSelectedIndex(MedicalListType.problem, SheetFieldDefCur.FieldName.Remove(0, 8));
            }
              
            if (StringSupport.equals(SheetFieldDefCur.RadioButtonValue, "N"))
            {
                radioNo.Checked = true;
                radioYes.Checked = false;
            }
             
        }
         
        textXPos.Text = SheetFieldDefCur.XPos.ToString();
        textYPos.Text = SheetFieldDefCur.YPos.ToString();
        textWidth.Text = SheetFieldDefCur.Width.ToString();
        textHeight.Text = SheetFieldDefCur.Height.ToString();
        textRadioGroupName.Text = SheetFieldDefCur.RadioButtonGroup;
        checkRequired.Checked = SheetFieldDefCur.IsRequired;
        textTabOrder.Text = SheetFieldDefCur.TabOrder.ToString();
    }

    /**
    * Fills listMedical with the corresponding list type.  This saves on load time by only filling necessary lists.
    */
    private void fillListMedical(MedicalListType medListType) throws Exception {
        switch(medListType)
        {
            case allergy: 
                if (allergyList == null)
                {
                    allergyList = AllergyDefs.getAll(false);
                }
                 
                listMedical.Items.Clear();
                for (int i = 0;i < allergyList.Count;i++)
                {
                    listMedical.Items.Add(allergyList[i].Description);
                }
                break;
            case problem: 
                listMedical.Items.Clear();
                for (int i = 0;i < DiseaseDefs.getList().Length;i++)
                {
                    listMedical.Items.Add(DiseaseDefs.getList()[i].DiseaseName);
                }
                break;
        
        }
    }

    /**
    * Loops through corresponding list and sets the index to the item matching fieldName passed in.  Only called on load.
    */
    private void setListMedicalSelectedIndex(MedicalListType medListType, String fieldName) throws Exception {
        switch(medListType)
        {
            case allergy: 
                for (int i = 0;i < allergyList.Count;i++)
                {
                    if (StringSupport.equals(AllergyDefs.GetDescription(allergyList[i].AllergyDefNum), fieldName))
                    {
                        listMedical.SelectedIndex = i;
                    }
                     
                }
                break;
            case problem: 
                for (int i = 0;i < DiseaseDefs.getList().Length;i++)
                {
                    if (StringSupport.equals(DiseaseDefs.getList()[i].DiseaseName, fieldName))
                    {
                        listMedical.SelectedIndex = i;
                    }
                     
                }
                break;
        
        }
    }

    private void listFields_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        labelMiscInstructions.Visible = false;
        labelReportableName.Visible = false;
        textReportableName.Visible = false;
        groupRadio.Visible = false;
        groupRadioMisc.Visible = false;
        labelRequired.Visible = false;
        checkRequired.Visible = false;
        labelMedical.Visible = false;
        listMedical.Visible = false;
        radioYes.Visible = false;
        radioNo.Visible = false;
        if (listFields.SelectedIndex == -1)
        {
            return ;
        }
         
        if (isMedHistSheet)
        {
            labelRequired.Visible = true;
            checkRequired.Visible = true;
            radioYes.Visible = true;
            radioNo.Visible = true;
            FieldName __dummyScrutVar2 = AvailFields[listFields.SelectedIndex].FieldName;
            if (__dummyScrutVar2.equals("allergy"))
            {
                labelMedical.Visible = true;
                listMedical.Visible = true;
                labelMedical.Text = "Allergies";
                fillListMedical(MedicalListType.allergy);
            }
            else if (__dummyScrutVar2.equals("problem"))
            {
                labelMedical.Visible = true;
                listMedical.Visible = true;
                labelMedical.Text = "Problems";
                fillListMedical(MedicalListType.problem);
            }
              
        }
         
        if (StringSupport.equals(AvailFields[listFields.SelectedIndex].FieldName, "misc"))
        {
            labelMiscInstructions.Visible = true;
            labelReportableName.Visible = true;
            textReportableName.Visible = true;
            textReportableName.Text = SheetFieldDefCur.ReportableName;
            //will either be "" or saved ReportableName.
            groupRadioMisc.Visible = true;
            labelRequired.Visible = true;
            checkRequired.Visible = true;
        }
        else
        {
            labelMiscInstructions.Visible = false;
            labelReportableName.Visible = false;
            textReportableName.Visible = false;
            textReportableName.Text = "";
            radioButtonValues = SheetFieldsAvailable.GetRadio(AvailFields[listFields.SelectedIndex].FieldName);
            if (radioButtonValues.Count == 0)
            {
                return ;
            }
             
            groupRadio.Visible = true;
            labelRequired.Visible = true;
            checkRequired.Visible = true;
            listRadio.Items.Clear();
            for (int i = 0;i < radioButtonValues.Count;i++)
            {
                listRadio.Items.Add(radioButtonValues[i]);
                if (StringSupport.equals(SheetFieldDefCur.RadioButtonValue, radioButtonValues[i]))
                {
                    listRadio.SelectedIndex = i;
                }
                 
            }
        } 
    }

    private void listRadio_Click(Object sender, EventArgs e) throws Exception {
        if (listRadio.SelectedIndex == -1)
        {
            return ;
        }
         
        SheetFieldDefCur.RadioButtonValue = radioButtonValues[listRadio.SelectedIndex];
    }

    private void listFields_DoubleClick(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void listMedical_DoubleClick(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void radioYes_Click(Object sender, EventArgs e) throws Exception {
        if (radioYes.Checked)
        {
            radioNo.Checked = false;
        }
        else
        {
            radioNo.Checked = true;
        } 
    }

    private void radioNo_Click(Object sender, EventArgs e) throws Exception {
        if (radioNo.Checked)
        {
            radioYes.Checked = false;
        }
        else
        {
            radioYes.Checked = true;
        } 
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        SheetFieldDefCur = null;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void saveAndClose() throws Exception {
        if (!StringSupport.equals(textXPos.errorProvider1.GetError(textXPos), "") || !StringSupport.equals(textYPos.errorProvider1.GetError(textYPos), "") || !StringSupport.equals(textWidth.errorProvider1.GetError(textWidth), "") || !StringSupport.equals(textHeight.errorProvider1.GetError(textHeight), "") || !StringSupport.equals(textTabOrder.errorProvider1.GetError(textTabOrder), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (listFields.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a field name first.");
            return ;
        }
         
        if (SheetDefCur.SheetType == SheetTypeEnum.ExamSheet)
        {
            if (textReportableName.Text.Contains(";") || textReportableName.Text.Contains(":"))
            {
                MsgBox.show(this,"Reportable name for Exam Sheet fields may not contain a ':' or a ';'.");
                return ;
            }
             
            if (textRadioGroupName.Text.Contains(";") || textRadioGroupName.Text.Contains(":"))
            {
                MsgBox.show(this,"Radio button group name for Exam Sheet fields may not contain a ':' or a ';'.");
                return ;
            }
             
        }
         
        String fieldName = AvailFields[listFields.SelectedIndex].FieldName;
        String radioButtonValue = "";
        if (isMedHistSheet)
        {
            if (listMedical.Visible)
            {
                if (listMedical.SelectedIndex == -1)
                {
                    System.String __dummyScrutVar3 = fieldName;
                    if (__dummyScrutVar3.equals("allergy"))
                    {
                        MsgBox.show(this,"Please select an allergy first.");
                        return ;
                    }
                    else if (__dummyScrutVar3.equals("problem"))
                    {
                        MsgBox.show(this,"Please select a problem first.");
                        return ;
                    }
                      
                }
                 
                fieldName += ":" + listMedical.SelectedItem;
            }
             
            if (radioNo.Checked)
            {
                radioButtonValue = "N";
            }
            else
            {
                radioButtonValue = "Y";
            } 
        }
         
        SheetFieldDefCur.FieldName = fieldName;
        SheetFieldDefCur.ReportableName = textReportableName.Text;
        //always safe even if not a misc field or if textReportableName is blank.
        SheetFieldDefCur.XPos = PIn.Int(textXPos.Text);
        SheetFieldDefCur.YPos = PIn.Int(textYPos.Text);
        SheetFieldDefCur.Width = PIn.Int(textWidth.Text);
        SheetFieldDefCur.Height = PIn.Int(textHeight.Text);
        SheetFieldDefCur.RadioButtonGroup = "";
        SheetFieldDefCur.RadioButtonValue = radioButtonValue;
        if (groupRadio.Visible && listRadio.SelectedIndex >= 0)
        {
            SheetFieldDefCur.RadioButtonValue = radioButtonValues[listRadio.SelectedIndex];
        }
        else if (groupRadioMisc.Visible)
        {
            SheetFieldDefCur.RadioButtonGroup = textRadioGroupName.Text;
        }
          
        SheetFieldDefCur.IsRequired = checkRequired.Checked;
        SheetFieldDefCur.TabOrder = PIn.Int(textTabOrder.Text);
        //don't save to database here.
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private enum MedicalListType
    {
        allergy,
        checkMed,
        problem
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
        this.listFields = new System.Windows.Forms.ListBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textRadioGroupName = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.checkRequired = new System.Windows.Forms.CheckBox();
        this.groupRadioMisc = new System.Windows.Forms.GroupBox();
        this.label1 = new System.Windows.Forms.Label();
        this.listRadio = new System.Windows.Forms.ListBox();
        this.groupRadio = new System.Windows.Forms.GroupBox();
        this.labelTabOrder = new System.Windows.Forms.Label();
        this.listMedical = new System.Windows.Forms.ListBox();
        this.labelMedical = new System.Windows.Forms.Label();
        this.radioYes = new System.Windows.Forms.RadioButton();
        this.radioNo = new System.Windows.Forms.RadioButton();
        this.textTabOrder = new OpenDental.ValidNum();
        this.butDelete = new OpenDental.UI.Button();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.textYPos = new OpenDental.ValidNum();
        this.textXPos = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.labelRequired = new System.Windows.Forms.Label();
        this.textReportableName = new System.Windows.Forms.TextBox();
        this.labelReportableName = new System.Windows.Forms.Label();
        this.labelMiscInstructions = new System.Windows.Forms.Label();
        this.groupRadioMisc.SuspendLayout();
        this.groupRadio.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 18);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(108, 16);
        this.label2.TabIndex = 86;
        this.label2.Text = "Field Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listFields
        //
        this.listFields.FormattingEnabled = true;
        this.listFields.Location = new System.Drawing.Point(15, 37);
        this.listFields.Name = "listFields";
        this.listFields.Size = new System.Drawing.Size(142, 472);
        this.listFields.TabIndex = 85;
        this.listFields.SelectedIndexChanged += new System.EventHandler(this.listFields_SelectedIndexChanged);
        this.listFields.DoubleClick += new System.EventHandler(this.listFields_DoubleClick);
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label5.Location = new System.Drawing.Point(385, 9);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(71, 16);
        this.label5.TabIndex = 90;
        this.label5.Text = "X Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label6.Location = new System.Drawing.Point(385, 32);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(71, 16);
        this.label6.TabIndex = 92;
        this.label6.Text = "Y Pos";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label7.Location = new System.Drawing.Point(385, 55);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label8.Location = new System.Drawing.Point(385, 78);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 51);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 16);
        this.label3.TabIndex = 103;
        this.label3.Text = "Group Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRadioGroupName
        //
        this.textRadioGroupName.Location = new System.Drawing.Point(94, 50);
        this.textRadioGroupName.Name = "textRadioGroupName";
        this.textRadioGroupName.Size = new System.Drawing.Size(197, 20);
        this.textRadioGroupName.TabIndex = 102;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(11, 15);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(280, 33);
        this.label9.TabIndex = 106;
        this.label9.Text = "Use the same Field Name (misc) and the same Group Name for each radio button in a" + " group.";
        //
        // checkRequired
        //
        this.checkRequired.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkRequired.Location = new System.Drawing.Point(373, 184);
        this.checkRequired.Name = "checkRequired";
        this.checkRequired.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkRequired.Size = new System.Drawing.Size(97, 17);
        this.checkRequired.TabIndex = 107;
        this.checkRequired.Text = "Required";
        this.checkRequired.UseVisualStyleBackColor = true;
        this.checkRequired.Visible = false;
        //
        // groupRadioMisc
        //
        this.groupRadioMisc.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupRadioMisc.Controls.Add(this.label9);
        this.groupRadioMisc.Controls.Add(this.textRadioGroupName);
        this.groupRadioMisc.Controls.Add(this.label3);
        this.groupRadioMisc.Location = new System.Drawing.Point(362, 100);
        this.groupRadioMisc.Name = "groupRadioMisc";
        this.groupRadioMisc.Size = new System.Drawing.Size(297, 78);
        this.groupRadioMisc.TabIndex = 106;
        this.groupRadioMisc.TabStop = false;
        this.groupRadioMisc.Text = "Radio Button";
        this.groupRadioMisc.Visible = false;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(283, 31);
        this.label1.TabIndex = 87;
        this.label1.Text = "Use the same Field Name for each radio button in a group.  But set a different Ra" + "dio Button Value for each.";
        //
        // listRadio
        //
        this.listRadio.FormattingEnabled = true;
        this.listRadio.Location = new System.Drawing.Point(94, 56);
        this.listRadio.Name = "listRadio";
        this.listRadio.Size = new System.Drawing.Size(142, 121);
        this.listRadio.TabIndex = 88;
        this.listRadio.Click += new System.EventHandler(this.listRadio_Click);
        //
        // groupRadio
        //
        this.groupRadio.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupRadio.Controls.Add(this.listRadio);
        this.groupRadio.Controls.Add(this.label1);
        this.groupRadio.Location = new System.Drawing.Point(362, 243);
        this.groupRadio.Name = "groupRadio";
        this.groupRadio.Size = new System.Drawing.Size(297, 183);
        this.groupRadio.TabIndex = 101;
        this.groupRadio.TabStop = false;
        this.groupRadio.Text = "Radio Button Value";
        this.groupRadio.Visible = false;
        //
        // labelTabOrder
        //
        this.labelTabOrder.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelTabOrder.Location = new System.Drawing.Point(385, 491);
        this.labelTabOrder.Name = "labelTabOrder";
        this.labelTabOrder.Size = new System.Drawing.Size(71, 16);
        this.labelTabOrder.TabIndex = 108;
        this.labelTabOrder.Text = "Tab Order";
        this.labelTabOrder.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listMedical
        //
        this.listMedical.FormattingEnabled = true;
        this.listMedical.Location = new System.Drawing.Point(186, 37);
        this.listMedical.Name = "listMedical";
        this.listMedical.Size = new System.Drawing.Size(142, 472);
        this.listMedical.TabIndex = 110;
        this.listMedical.Visible = false;
        this.listMedical.DoubleClick += new System.EventHandler(this.listMedical_DoubleClick);
        //
        // labelMedical
        //
        this.labelMedical.Location = new System.Drawing.Point(183, 18);
        this.labelMedical.Name = "labelMedical";
        this.labelMedical.Size = new System.Drawing.Size(108, 16);
        this.labelMedical.TabIndex = 111;
        this.labelMedical.Text = "labelMedical";
        this.labelMedical.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelMedical.Visible = false;
        //
        // radioYes
        //
        this.radioYes.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.radioYes.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioYes.Location = new System.Drawing.Point(409, 203);
        this.radioYes.Name = "radioYes";
        this.radioYes.Size = new System.Drawing.Size(61, 17);
        this.radioYes.TabIndex = 114;
        this.radioYes.TabStop = true;
        this.radioYes.Text = "Yes";
        this.radioYes.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioYes.UseVisualStyleBackColor = true;
        this.radioYes.Visible = false;
        this.radioYes.Click += new System.EventHandler(this.radioYes_Click);
        //
        // radioNo
        //
        this.radioNo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.radioNo.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioNo.Location = new System.Drawing.Point(409, 221);
        this.radioNo.Name = "radioNo";
        this.radioNo.Size = new System.Drawing.Size(61, 17);
        this.radioNo.TabIndex = 115;
        this.radioNo.TabStop = true;
        this.radioNo.Text = "No";
        this.radioNo.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioNo.UseVisualStyleBackColor = true;
        this.radioNo.Visible = false;
        this.radioNo.Click += new System.EventHandler(this.radioNo_Click);
        //
        // textTabOrder
        //
        this.textTabOrder.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textTabOrder.Location = new System.Drawing.Point(456, 490);
        this.textTabOrder.setMaxVal(2000);
        this.textTabOrder.setMinVal(-100);
        this.textTabOrder.Name = "textTabOrder";
        this.textTabOrder.Size = new System.Drawing.Size(69, 20);
        this.textTabOrder.TabIndex = 109;
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
        this.butDelete.Location = new System.Drawing.Point(16, 530);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(77, 24);
        this.butDelete.TabIndex = 100;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textHeight
        //
        this.textHeight.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textHeight.Location = new System.Drawing.Point(456, 77);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(1);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(69, 20);
        this.textHeight.TabIndex = 97;
        //
        // textWidth
        //
        this.textWidth.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textWidth.Location = new System.Drawing.Point(456, 54);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(1);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(69, 20);
        this.textWidth.TabIndex = 95;
        //
        // textYPos
        //
        this.textYPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textYPos.Location = new System.Drawing.Point(456, 31);
        this.textYPos.setMaxVal(2000);
        this.textYPos.setMinVal(-100);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(69, 20);
        this.textYPos.TabIndex = 93;
        //
        // textXPos
        //
        this.textXPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textXPos.Location = new System.Drawing.Point(456, 8);
        this.textXPos.setMaxVal(2000);
        this.textXPos.setMinVal(-100);
        this.textXPos.Name = "textXPos";
        this.textXPos.Size = new System.Drawing.Size(69, 20);
        this.textXPos.TabIndex = 91;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(503, 530);
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
        this.butCancel.Location = new System.Drawing.Point(584, 530);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // labelRequired
        //
        this.labelRequired.Location = new System.Drawing.Point(480, 184);
        this.labelRequired.Name = "labelRequired";
        this.labelRequired.Size = new System.Drawing.Size(177, 56);
        this.labelRequired.TabIndex = 116;
        this.labelRequired.Text = "Radio buttons in a radio button group must all be marked required or all be marke" + "d not required.";
        //
        // textReportableName
        //
        this.textReportableName.Location = new System.Drawing.Point(456, 466);
        this.textReportableName.Name = "textReportableName";
        this.textReportableName.Size = new System.Drawing.Size(197, 20);
        this.textReportableName.TabIndex = 107;
        //
        // labelReportableName
        //
        this.labelReportableName.Location = new System.Drawing.Point(315, 467);
        this.labelReportableName.Name = "labelReportableName";
        this.labelReportableName.Size = new System.Drawing.Size(141, 16);
        this.labelReportableName.TabIndex = 108;
        this.labelReportableName.Text = "Reportable Name";
        this.labelReportableName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMiscInstructions
        //
        this.labelMiscInstructions.Location = new System.Drawing.Point(370, 429);
        this.labelMiscInstructions.Name = "labelMiscInstructions";
        this.labelMiscInstructions.Size = new System.Drawing.Size(289, 32);
        this.labelMiscInstructions.TabIndex = 117;
        this.labelMiscInstructions.Text = "To make misc radio buttons reportable, set a different Reportable Name for each b" + "utton in the group.";
        //
        // FormSheetFieldCheckBox
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(675, 568);
        this.Controls.Add(this.listMedical);
        this.Controls.Add(this.labelMiscInstructions);
        this.Controls.Add(this.textReportableName);
        this.Controls.Add(this.labelReportableName);
        this.Controls.Add(this.labelRequired);
        this.Controls.Add(this.radioNo);
        this.Controls.Add(this.radioYes);
        this.Controls.Add(this.labelMedical);
        this.Controls.Add(this.textTabOrder);
        this.Controls.Add(this.labelTabOrder);
        this.Controls.Add(this.checkRequired);
        this.Controls.Add(this.groupRadioMisc);
        this.Controls.Add(this.groupRadio);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textYPos);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textXPos);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listFields);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldCheckBox";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit CheckBox";
        this.Load += new System.EventHandler(this.FormSheetFieldCheckBox_Load);
        this.groupRadioMisc.ResumeLayout(false);
        this.groupRadioMisc.PerformLayout();
        this.groupRadio.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listFields = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textXPos;
    private OpenDental.ValidNum textYPos;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textWidth;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textHeight;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textRadioGroupName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkRequired = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupRadioMisc = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listRadio = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupRadio = new System.Windows.Forms.GroupBox();
    private OpenDental.ValidNum textTabOrder;
    private System.Windows.Forms.Label labelTabOrder = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listMedical = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelMedical = new System.Windows.Forms.Label();
    private System.Windows.Forms.RadioButton radioYes = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioNo = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelRequired = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReportableName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelReportableName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelMiscInstructions = new System.Windows.Forms.Label();
}


