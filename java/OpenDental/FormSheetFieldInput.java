//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.OutInCheck;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldsAvailable;
import OpenDentBusiness.SheetTypeEnum;
import OpenDental.Properties.Resources;

public class FormSheetFieldInput  extends Form 
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
    public FormSheetFieldInput() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldInput_Load(Object sender, EventArgs e) throws Exception {
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
        AvailFields = SheetFieldsAvailable.getList(SheetDefCur.SheetType,OutInCheck.In);
        listFields.Items.Clear();
        for (int i = 0;i < AvailFields.Count;i++)
        {
            //static text is not one of the options.
            listFields.Items.Add(AvailFields[i].FieldName);
            if (SheetFieldDefCur.FieldName.StartsWith(AvailFields[i].FieldName))
            {
                listFields.SelectedIndex = i;
            }
             
        }
        InstalledFontCollection fColl = new InstalledFontCollection();
        for (int i = 0;i < fColl.Families.Length;i++)
        {
            comboFontName.Items.Add(fColl.Families[i].Name);
        }
        comboFontName.Text = SheetFieldDefCur.FontName;
        textFontSize.Text = SheetFieldDefCur.FontSize.ToString();
        checkFontIsBold.Checked = SheetFieldDefCur.FontIsBold;
        for (int i = 0;i < Enum.GetNames(GrowthBehaviorEnum.class).Length;i++)
        {
            comboGrowthBehavior.Items.Add(Enum.GetNames(GrowthBehaviorEnum.class)[i]);
            if (((Enum)SheetFieldDefCur.GrowthBehavior).ordinal() == i)
            {
                comboGrowthBehavior.SelectedIndex = i;
            }
             
        }
        textXPos.Text = SheetFieldDefCur.XPos.ToString();
        textYPos.Text = SheetFieldDefCur.YPos.ToString();
        textWidth.Text = SheetFieldDefCur.Width.ToString();
        textHeight.Text = SheetFieldDefCur.Height.ToString();
        checkRequired.Checked = SheetFieldDefCur.IsRequired;
        textTabOrder.Text = SheetFieldDefCur.TabOrder.ToString();
    }

    private void listFields_DoubleClick(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void listFields_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (listFields.SelectedIndex == -1)
        {
            labelReportableName.Visible = false;
            textReportableName.Visible = false;
            textReportableName.Text = "";
            return ;
        }
         
        if (StringSupport.equals(AvailFields[listFields.SelectedIndex].FieldName, "misc"))
        {
            labelReportableName.Visible = true;
            textReportableName.Visible = true;
            textReportableName.Text = SheetFieldDefCur.ReportableName;
        }
        else
        {
            //will either be "" or saved ReportableName.
            labelReportableName.Visible = false;
            textReportableName.Visible = false;
            textReportableName.Text = "";
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
         
        if (StringSupport.equals(comboFontName.Text, ""))
        {
            //not going to bother testing for validity unless it will cause a crash.
            MsgBox.show(this,"Please select a font name first.");
            return ;
        }
         
        if (SheetDefCur.SheetType == SheetTypeEnum.ExamSheet)
        {
            if (textReportableName.Text.Contains(";") || textReportableName.Text.Contains(":"))
            {
                MsgBox.show(this,"Reportable name for Exam Sheet fields may not contain a ':' or a ';'.");
                return ;
            }
             
        }
         
        float fontSize = new float();
        try
        {
            fontSize = float.Parse(textFontSize.Text);
            if (fontSize < 2)
            {
                MsgBox.show(this,"Font size is invalid.");
                return ;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Font size is invalid.");
            return ;
        }

        SheetFieldDefCur.FieldName = AvailFields[listFields.SelectedIndex].FieldName;
        SheetFieldDefCur.ReportableName = textReportableName.Text;
        //always safe even if not a misc field or if textReportableName is blank.
        SheetFieldDefCur.FontName = comboFontName.Text;
        SheetFieldDefCur.FontSize = fontSize;
        SheetFieldDefCur.FontIsBold = checkFontIsBold.Checked;
        SheetFieldDefCur.XPos = PIn.Int(textXPos.Text);
        SheetFieldDefCur.YPos = PIn.Int(textYPos.Text);
        SheetFieldDefCur.Width = PIn.Int(textWidth.Text);
        SheetFieldDefCur.Height = PIn.Int(textHeight.Text);
        SheetFieldDefCur.GrowthBehavior = (GrowthBehaviorEnum)comboGrowthBehavior.SelectedIndex;
        SheetFieldDefCur.IsRequired = checkRequired.Checked;
        SheetFieldDefCur.TabOrder = PIn.Int(textTabOrder.Text);
        //don't save to database here.
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
        this.listFields = new System.Windows.Forms.ListBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkFontIsBold = new System.Windows.Forms.CheckBox();
        this.textFontSize = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboFontName = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.comboGrowthBehavior = new System.Windows.Forms.ComboBox();
        this.label9 = new System.Windows.Forms.Label();
        this.checkRequired = new System.Windows.Forms.CheckBox();
        this.labelTabOrder = new System.Windows.Forms.Label();
        this.textTabOrder = new OpenDental.ValidNum();
        this.butDelete = new OpenDental.UI.Button();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.textYPos = new OpenDental.ValidNum();
        this.textXPos = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textReportableName = new System.Windows.Forms.TextBox();
        this.labelReportableName = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
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
        this.listFields.Size = new System.Drawing.Size(142, 277);
        this.listFields.TabIndex = 85;
        this.listFields.SelectedIndexChanged += new System.EventHandler(this.listFields_SelectedIndexChanged);
        this.listFields.DoubleClick += new System.EventHandler(this.listFields_DoubleClick);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.checkFontIsBold);
        this.groupBox1.Controls.Add(this.textFontSize);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.comboFontName);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(182, 42);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(283, 95);
        this.groupBox1.TabIndex = 88;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Font";
        //
        // checkFontIsBold
        //
        this.checkFontIsBold.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkFontIsBold.Location = new System.Drawing.Point(16, 66);
        this.checkFontIsBold.Name = "checkFontIsBold";
        this.checkFontIsBold.Size = new System.Drawing.Size(85, 20);
        this.checkFontIsBold.TabIndex = 90;
        this.checkFontIsBold.Text = "Bold";
        this.checkFontIsBold.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkFontIsBold.UseVisualStyleBackColor = true;
        //
        // textFontSize
        //
        this.textFontSize.Location = new System.Drawing.Point(86, 40);
        this.textFontSize.Name = "textFontSize";
        this.textFontSize.Size = new System.Drawing.Size(44, 20);
        this.textFontSize.TabIndex = 89;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(13, 41);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(71, 16);
        this.label4.TabIndex = 89;
        this.label4.Text = "Size";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFontName
        //
        this.comboFontName.FormattingEnabled = true;
        this.comboFontName.Location = new System.Drawing.Point(86, 14);
        this.comboFontName.Name = "comboFontName";
        this.comboFontName.Size = new System.Drawing.Size(191, 21);
        this.comboFontName.TabIndex = 88;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(13, 15);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(71, 16);
        this.label3.TabIndex = 87;
        this.label3.Text = "Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label5.Location = new System.Drawing.Point(197, 172);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(71, 16);
        this.label5.TabIndex = 90;
        this.label5.Text = "X Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label6.Location = new System.Drawing.Point(197, 198);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(71, 16);
        this.label6.TabIndex = 92;
        this.label6.Text = "Y Pos";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label7.Location = new System.Drawing.Point(197, 224);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label8.Location = new System.Drawing.Point(197, 250);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboGrowthBehavior
        //
        this.comboGrowthBehavior.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.comboGrowthBehavior.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboGrowthBehavior.FormattingEnabled = true;
        this.comboGrowthBehavior.Location = new System.Drawing.Point(268, 143);
        this.comboGrowthBehavior.Name = "comboGrowthBehavior";
        this.comboGrowthBehavior.Size = new System.Drawing.Size(197, 21);
        this.comboGrowthBehavior.TabIndex = 99;
        //
        // label9
        //
        this.label9.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label9.Location = new System.Drawing.Point(161, 144);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(107, 16);
        this.label9.TabIndex = 98;
        this.label9.Text = "Growth Behavior";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkRequired
        //
        this.checkRequired.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkRequired.AutoSize = true;
        this.checkRequired.Location = new System.Drawing.Point(214, 275);
        this.checkRequired.Name = "checkRequired";
        this.checkRequired.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkRequired.Size = new System.Drawing.Size(69, 17);
        this.checkRequired.TabIndex = 101;
        this.checkRequired.Text = "Required";
        this.checkRequired.UseVisualStyleBackColor = true;
        //
        // labelTabOrder
        //
        this.labelTabOrder.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelTabOrder.Location = new System.Drawing.Point(197, 299);
        this.labelTabOrder.Name = "labelTabOrder";
        this.labelTabOrder.Size = new System.Drawing.Size(71, 16);
        this.labelTabOrder.TabIndex = 102;
        this.labelTabOrder.Text = "Tab Order";
        this.labelTabOrder.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTabOrder
        //
        this.textTabOrder.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textTabOrder.Location = new System.Drawing.Point(268, 298);
        this.textTabOrder.setMaxVal(2000);
        this.textTabOrder.setMinVal(-100);
        this.textTabOrder.Name = "textTabOrder";
        this.textTabOrder.Size = new System.Drawing.Size(69, 20);
        this.textTabOrder.TabIndex = 103;
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
        this.butDelete.Location = new System.Drawing.Point(15, 331);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(77, 24);
        this.butDelete.TabIndex = 100;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textHeight
        //
        this.textHeight.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textHeight.Location = new System.Drawing.Point(268, 249);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(1);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(69, 20);
        this.textHeight.TabIndex = 97;
        //
        // textWidth
        //
        this.textWidth.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textWidth.Location = new System.Drawing.Point(268, 223);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(1);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(69, 20);
        this.textWidth.TabIndex = 95;
        //
        // textYPos
        //
        this.textYPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textYPos.Location = new System.Drawing.Point(268, 197);
        this.textYPos.setMaxVal(2000);
        this.textYPos.setMinVal(-100);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(69, 20);
        this.textYPos.TabIndex = 93;
        //
        // textXPos
        //
        this.textXPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textXPos.Location = new System.Drawing.Point(268, 171);
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
        this.butOK.Location = new System.Drawing.Point(407, 301);
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
        this.butCancel.Location = new System.Drawing.Point(407, 331);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textReportableName
        //
        this.textReportableName.Location = new System.Drawing.Point(268, 17);
        this.textReportableName.Name = "textReportableName";
        this.textReportableName.Size = new System.Drawing.Size(197, 20);
        this.textReportableName.TabIndex = 104;
        //
        // labelReportableName
        //
        this.labelReportableName.Location = new System.Drawing.Point(127, 18);
        this.labelReportableName.Name = "labelReportableName";
        this.labelReportableName.Size = new System.Drawing.Size(141, 16);
        this.labelReportableName.TabIndex = 105;
        this.labelReportableName.Text = "Reportable Name";
        this.labelReportableName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormSheetFieldInput
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(494, 367);
        this.Controls.Add(this.textReportableName);
        this.Controls.Add(this.labelReportableName);
        this.Controls.Add(this.textTabOrder);
        this.Controls.Add(this.labelTabOrder);
        this.Controls.Add(this.checkRequired);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.comboGrowthBehavior);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textYPos);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textXPos);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listFields);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldInput";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Input Field";
        this.Load += new System.EventHandler(this.FormSheetFieldInput_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listFields = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboFontName = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.CheckBox checkFontIsBold = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textFontSize = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textXPos;
    private OpenDental.ValidNum textYPos;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textWidth;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textHeight;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboGrowthBehavior = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.CheckBox checkRequired = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelTabOrder = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textTabOrder;
    private System.Windows.Forms.TextBox textReportableName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelReportableName = new System.Windows.Forms.Label();
}


