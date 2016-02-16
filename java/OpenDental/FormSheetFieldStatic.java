//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormSheetFieldExam;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetTypeEnum;
import OpenDental.Properties.Resources;

public class FormSheetFieldStatic  extends Form 
{

    /**
    * This is the object we are editing.
    */
    public SheetFieldDef SheetFieldDefCur;
    /**
    * We need access to a few other fields of the sheetDef.
    */
    public SheetDef SheetDefCur;
    public boolean IsReadOnly = new boolean();
    private int textSelectionStart = new int();
    public FormSheetFieldStatic() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldStatic_Load(Object sender, EventArgs e) throws Exception {
        if (IsReadOnly)
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
         
        if (SheetDefCur.SheetType == SheetTypeEnum.PatientLetter)
        {
            butExamSheet.Visible = true;
        }
        else
        {
            butExamSheet.Visible = false;
        } 
        textFieldValue.Text = SheetFieldDefCur.FieldValue;
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
        fillFields();
    }

    private void fillFields() throws Exception {
        String[] fieldArray = new String[]{ "address", "age", "apptsAllFuture", "balTotal", "bal_0_30", "bal_31_60", "bal_61_90", "balOver90", "balInsEst", "balTotalMinusInsEst", "BillingType", "Birthdate", "carrierName", "carrier2Name", "carrierAddress", "carrierCityStZip", "ChartNumber", "cityStateZip", "clinicDescription", "clinicAddress", "clinicCityStZip", "clinicPhone", "DateFirstVisit", "dateLastBW", "dateLastExam", "dateLastPanoFMX", "dateLastProphy", "dateOfLastSavedTP", "dateRecallDue", "dateTimeLastAppt", "dateToday", "Email", "famFinUrgNote", "gender", "genderHeShe", "genderheshe", "genderHimHer", "genderhimher", "genderHimselfHerself", "genderhimselfherself", "genderHisHer", "genderhisher", "genderHisHers", "genderhishers", "guarantorNameF", "guarantorNameFL", "guarantorNameL", "guarantorNamePref", "guarantorNameLF", "HmPhone", "insAnnualMax", "insDeductible", "insDeductibleUsed", "insFreqBW", "insFreqExams", "insFreqPanoFMX", "insPending", "insPercentages", "insPlanGroupName", "insPlanNote", "insPlanGroupNumber", "insRemaining", "insSubNote", "insType", "insUsed", "ins2AnnualMax", "ins2Deductible", "ins2DeductibleUsed", "ins2Pending", "ins2Percentages", "ins2Remaining", "ins2Used", "MedUrgNote", "nameF", "nameFL", "nameFLFormal", "nameL", "nameLF", "nameMI", "namePref", "nextSchedApptDateT", "nextSchedApptsFam", "PatNum", "plannedAppointmentInfo", "practiceTitle", "priProvNameFormal", "recallInterval", "referredFrom", "referredTo", "salutation", "serviceNote", "siteDescription", "SSN", "subscriberID", "subscriberNameFL", "subscriber2NameFL", "timeNow", "tpResponsPartyAddress", "tpResponsPartyCityStZip", "tpResponsPartyNameFL", "treatmentPlanProcs", "WirelessPhone", "WkPhone" };
        listFields.Items.Clear();
        for (int i = 0;i < fieldArray.Length;i++)
        {
            listFields.Items.Add(fieldArray[i]);
        }
    }

    private void listFields_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        String fieldStr = "";
        for (int i = 0;i < listFields.Items.Count;i++)
        {
            if (listFields.GetItemRectangle(i).Contains(e.Location))
            {
                fieldStr = listFields.Items[i].ToString();
            }
             
        }
        if (StringSupport.equals(fieldStr, ""))
        {
            return ;
        }
         
        if (textSelectionStart < textFieldValue.Text.Length - 1)
        {
            textFieldValue.Text = textFieldValue.Text.Substring(0, textSelectionStart) + "[" + fieldStr + "]" + textFieldValue.Text.Substring(textSelectionStart);
        }
        else
        {
            //otherwise, just tack it on the end
            textFieldValue.Text += "[" + fieldStr + "]";
        } 
        textFieldValue.Select(textSelectionStart + fieldStr.Length + 2, 0);
        textFieldValue.Focus();
    }

    //if(!textFieldValue.Focused){
    //	textFieldValue.Text+="["+fieldStr+"]";
    //	return;
    //}
    //MessageBox.Show(textFieldValue.SelectionStart.ToString());
    private void textFieldValue_Leave(Object sender, EventArgs e) throws Exception {
        textSelectionStart = textFieldValue.SelectionStart;
    }

    private void textFieldValue_TextChanged(Object sender, EventArgs e) throws Exception {
        int textW = 0;
        float fontSize = 10f;
        try
        {
            fontSize = float.Parse(textFontSize.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        Graphics g = this.CreateGraphics();
        try
        {
            {
                Font font = new Font(comboFontName.Text, fontSize);
                try
                {
                    {
                        textW = (int)g.MeasureString(textFieldValue.Text, font).Width;
                    }
                }
                finally
                {
                    if (font != null)
                        Disposable.mkDisposable(font).dispose();
                     
                }
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        labelTextW.Text = Lan.g(this,"TextW: ") + textW.ToString();
    }

    private void butExamSheet_Click(Object sender, EventArgs e) throws Exception {
        FormSheetFieldExam FormE = new FormSheetFieldExam();
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (textSelectionStart < textFieldValue.Text.Length - 1)
        {
            //if cursor is not at the end of the text in textFieldValue, insert into text beginning at cursor
            textFieldValue.Text = textFieldValue.Text.Substring(0, textSelectionStart) + "[" + FormE.ExamFieldSelected + "]" + textFieldValue.Text.Substring(textSelectionStart);
        }
        else
        {
            //otherwise, just tack it on the end
            textFieldValue.Text += "[" + FormE.ExamFieldSelected + "]";
        } 
        textFieldValue.Select(textSelectionStart + FormE.ExamFieldSelected.Length + 2, 0);
        textFieldValue.Focus();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        SheetFieldDefCur = null;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textXPos.errorProvider1.GetError(textXPos), "") || !StringSupport.equals(textYPos.errorProvider1.GetError(textYPos), "") || !StringSupport.equals(textWidth.errorProvider1.GetError(textWidth), "") || !StringSupport.equals(textHeight.errorProvider1.GetError(textHeight), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textFieldValue.Text, ""))
        {
            MsgBox.show(this,"Please set a field value first.");
            return ;
        }
         
        if (StringSupport.equals(comboFontName.Text, ""))
        {
            //not going to bother testing for validity unless it will cause a crash.
            MsgBox.show(this,"Please select a font name first.");
            return ;
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
        catch (Exception __dummyCatchVar1)
        {
            MsgBox.show(this,"Font size is invalid.");
            return ;
        }

        SheetFieldDefCur.FieldValue = textFieldValue.Text;
        SheetFieldDefCur.FontName = comboFontName.Text;
        SheetFieldDefCur.FontSize = fontSize;
        SheetFieldDefCur.FontIsBold = checkFontIsBold.Checked;
        SheetFieldDefCur.XPos = PIn.Int(textXPos.Text);
        SheetFieldDefCur.YPos = PIn.Int(textYPos.Text);
        SheetFieldDefCur.Width = PIn.Int(textWidth.Text);
        SheetFieldDefCur.Height = PIn.Int(textHeight.Text);
        SheetFieldDefCur.GrowthBehavior = (GrowthBehaviorEnum)comboGrowthBehavior.SelectedIndex;
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
        this.textFieldValue = new System.Windows.Forms.TextBox();
        this.listFields = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.textYPos = new OpenDental.ValidNum();
        this.textXPos = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.labelTextW = new System.Windows.Forms.Label();
        this.butExamSheet = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.checkFontIsBold);
        this.groupBox1.Controls.Add(this.textFontSize);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.comboFontName);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(18, 361);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(287, 95);
        this.groupBox1.TabIndex = 88;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Font";
        //
        // checkFontIsBold
        //
        this.checkFontIsBold.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkFontIsBold.Location = new System.Drawing.Point(13, 66);
        this.checkFontIsBold.Name = "checkFontIsBold";
        this.checkFontIsBold.Size = new System.Drawing.Size(85, 20);
        this.checkFontIsBold.TabIndex = 90;
        this.checkFontIsBold.Text = "Bold";
        this.checkFontIsBold.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkFontIsBold.UseVisualStyleBackColor = true;
        //
        // textFontSize
        //
        this.textFontSize.Location = new System.Drawing.Point(83, 41);
        this.textFontSize.Name = "textFontSize";
        this.textFontSize.Size = new System.Drawing.Size(44, 20);
        this.textFontSize.TabIndex = 89;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(10, 42);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(71, 16);
        this.label4.TabIndex = 89;
        this.label4.Text = "Size";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFontName
        //
        this.comboFontName.FormattingEnabled = true;
        this.comboFontName.Location = new System.Drawing.Point(83, 14);
        this.comboFontName.Name = "comboFontName";
        this.comboFontName.Size = new System.Drawing.Size(197, 21);
        this.comboFontName.TabIndex = 88;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(10, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(71, 16);
        this.label3.TabIndex = 87;
        this.label3.Text = "Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(31, 502);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(71, 16);
        this.label5.TabIndex = 90;
        this.label5.Text = "X Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(31, 528);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(71, 16);
        this.label6.TabIndex = 92;
        this.label6.Text = "Y Pos";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(31, 554);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(31, 580);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboGrowthBehavior
        //
        this.comboGrowthBehavior.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboGrowthBehavior.FormattingEnabled = true;
        this.comboGrowthBehavior.Location = new System.Drawing.Point(101, 473);
        this.comboGrowthBehavior.Name = "comboGrowthBehavior";
        this.comboGrowthBehavior.Size = new System.Drawing.Size(197, 21);
        this.comboGrowthBehavior.TabIndex = 99;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(-5, 474);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(107, 16);
        this.label9.TabIndex = 98;
        this.label9.Text = "Growth Behavior";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFieldValue
        //
        this.textFieldValue.AcceptsReturn = true;
        this.textFieldValue.AcceptsTab = true;
        this.textFieldValue.Location = new System.Drawing.Point(18, 17);
        this.textFieldValue.Multiline = true;
        this.textFieldValue.Name = "textFieldValue";
        this.textFieldValue.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textFieldValue.Size = new System.Drawing.Size(854, 335);
        this.textFieldValue.TabIndex = 102;
        this.textFieldValue.TextChanged += new System.EventHandler(this.textFieldValue_TextChanged);
        this.textFieldValue.Leave += new System.EventHandler(this.textFieldValue_Leave);
        //
        // listFields
        //
        this.listFields.FormattingEnabled = true;
        this.listFields.IntegralHeight = false;
        this.listFields.Location = new System.Drawing.Point(313, 374);
        this.listFields.MultiColumn = true;
        this.listFields.Name = "listFields";
        this.listFields.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listFields.Size = new System.Drawing.Size(559, 279);
        this.listFields.TabIndex = 0;
        this.listFields.TabStop = false;
        this.listFields.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listFields_MouseClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(311, 355);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(107, 16);
        this.label1.TabIndex = 103;
        this.label1.Text = "Click to insert Field";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
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
        this.butDelete.Location = new System.Drawing.Point(15, 659);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(77, 24);
        this.butDelete.TabIndex = 100;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(101, 579);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(-100);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(69, 20);
        this.textHeight.TabIndex = 97;
        //
        // textWidth
        //
        this.textWidth.Location = new System.Drawing.Point(101, 553);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(-100);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(69, 20);
        this.textWidth.TabIndex = 95;
        //
        // textYPos
        //
        this.textYPos.Location = new System.Drawing.Point(101, 527);
        this.textYPos.setMaxVal(2000);
        this.textYPos.setMinVal(-100);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(69, 20);
        this.textYPos.TabIndex = 93;
        //
        // textXPos
        //
        this.textXPos.Location = new System.Drawing.Point(101, 501);
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
        this.butOK.Location = new System.Drawing.Point(707, 659);
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
        this.butCancel.Location = new System.Drawing.Point(797, 659);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // labelTextW
        //
        this.labelTextW.Location = new System.Drawing.Point(176, 555);
        this.labelTextW.Name = "labelTextW";
        this.labelTextW.Size = new System.Drawing.Size(109, 16);
        this.labelTextW.TabIndex = 104;
        this.labelTextW.Text = "TextW: ";
        this.labelTextW.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butExamSheet
        //
        this.butExamSheet.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExamSheet.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExamSheet.setAutosize(true);
        this.butExamSheet.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExamSheet.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExamSheet.setCornerRadius(4F);
        this.butExamSheet.Location = new System.Drawing.Point(313, 659);
        this.butExamSheet.Name = "butExamSheet";
        this.butExamSheet.Size = new System.Drawing.Size(98, 24);
        this.butExamSheet.TabIndex = 105;
        this.butExamSheet.Text = "Exam Sheet Field";
        this.butExamSheet.Click += new System.EventHandler(this.butExamSheet_Click);
        //
        // FormSheetFieldStatic
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(884, 691);
        this.Controls.Add(this.butExamSheet);
        this.Controls.Add(this.labelTextW);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listFields);
        this.Controls.Add(this.textFieldValue);
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
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldStatic";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Static Text Field";
        this.Load += new System.EventHandler(this.FormSheetFieldStatic_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
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
    private System.Windows.Forms.TextBox textFieldValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listFields = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTextW = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butExamSheet;
}


