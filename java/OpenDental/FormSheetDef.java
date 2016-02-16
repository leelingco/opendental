//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;

public class FormSheetDef  extends Form 
{

    /**
    * 
    */
    public SheetDef SheetDefCur;
    //private List<SheetFieldDef> AvailFields;
    public boolean IsReadOnly = new boolean();
    /**
    * On creation of a new sheetdef, the user must pick a description and a sheettype before allowing to start editing the sheet.  After the initial sheettype selection, this will be false, indicating that the user may not change the type.
    */
    public boolean IsInitial = new boolean();
    public FormSheetDef() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetDef_Load(Object sender, EventArgs e) throws Exception {
        if (IsReadOnly)
        {
            butOK.Enabled = false;
        }
         
        if (!IsInitial)
        {
            listSheetType.Enabled = false;
        }
         
        textDescription.Text = SheetDefCur.Description;
        for (int i = 0;i < Enum.GetNames(SheetTypeEnum.class).Length;i++)
        {
            //not allowed to change sheettype once created.
            listSheetType.Items.Add(Enum.GetNames(SheetTypeEnum.class)[i]);
            if (((Enum)SheetDefCur.SheetType).ordinal() == i && !IsInitial)
            {
                listSheetType.SelectedIndex = i;
            }
             
        }
        InstalledFontCollection fColl = new InstalledFontCollection();
        for (int i = 0;i < fColl.Families.Length;i++)
        {
            comboFontName.Items.Add(fColl.Families[i].Name);
        }
        comboFontName.Text = SheetDefCur.FontName;
        textFontSize.Text = SheetDefCur.FontSize.ToString();
        textWidth.Text = SheetDefCur.Width.ToString();
        textHeight.Text = SheetDefCur.Height.ToString();
        checkIsLandscape.Checked = SheetDefCur.IsLandscape;
    }

    private void listSheetType_Click(Object sender, EventArgs e) throws Exception {
        if (!IsInitial)
        {
            return ;
        }
         
        if (listSheetType.SelectedIndex == -1)
        {
            return ;
        }
         
        SheetDef sheetdef = null;
        switch((SheetTypeEnum)listSheetType.SelectedIndex)
        {
            case LabelCarrier: 
            case LabelPatient: 
            case LabelReferral: 
                sheetdef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientMail);
                if (StringSupport.equals(textDescription.Text, ""))
                {
                    textDescription.Text = ((SheetTypeEnum)listSheetType.SelectedIndex).ToString();
                }
                 
                comboFontName.Text = sheetdef.FontName;
                textFontSize.Text = sheetdef.FontSize.ToString();
                textWidth.Text = sheetdef.Width.ToString();
                textHeight.Text = sheetdef.Height.ToString();
                checkIsLandscape.Checked = sheetdef.IsLandscape;
                break;
            case ReferralSlip: 
                sheetdef = SheetsInternal.getSheetDef(SheetInternalType.ReferralSlip);
                if (StringSupport.equals(textDescription.Text, ""))
                {
                    textDescription.Text = ((SheetTypeEnum)listSheetType.SelectedIndex).ToString();
                }
                 
                comboFontName.Text = sheetdef.FontName;
                textFontSize.Text = sheetdef.FontSize.ToString();
                textWidth.Text = sheetdef.Width.ToString();
                textHeight.Text = sheetdef.Height.ToString();
                checkIsLandscape.Checked = sheetdef.IsLandscape;
                break;
        
        }
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textWidth.errorProvider1.GetError(textWidth), "") || !StringSupport.equals(textHeight.errorProvider1.GetError(textHeight), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (listSheetType.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a sheet type first.");
            return ;
        }
         
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Description may not be blank.");
            return ;
        }
         
        if ((SheetTypeEnum)listSheetType.SelectedIndex == SheetTypeEnum.ExamSheet)
        {
            //make sure description for exam sheet does not contain a ':' or a ';' because this interferes with pulling the exam sheet fields to fill a patient letter
            if (textDescription.Text.Contains(":") || textDescription.Text.Contains(";"))
            {
                MsgBox.show(this,"Description for an Exam Sheet may not contain a ':' or a ';'.");
                return ;
            }
             
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
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Font size is invalid.");
            return ;
        }

        SheetDefCur.Description = textDescription.Text;
        SheetDefCur.SheetType = (SheetTypeEnum)listSheetType.SelectedIndex;
        SheetDefCur.FontName = comboFontName.Text;
        SheetDefCur.FontSize = fontSize;
        SheetDefCur.Width = PIn.Int(textWidth.Text);
        SheetDefCur.Height = PIn.Int(textHeight.Text);
        SheetDefCur.IsLandscape = checkIsLandscape.Checked;
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
        this.listSheetType = new System.Windows.Forms.ListBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textFontSize = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboFontName = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.checkIsLandscape = new System.Windows.Forms.CheckBox();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(-2, 39);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(154, 48);
        this.label2.TabIndex = 86;
        this.label2.Text = "Sheet Type\r\n(cannot be changed later)";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listSheetType
        //
        this.listSheetType.FormattingEnabled = true;
        this.listSheetType.Location = new System.Drawing.Point(154, 38);
        this.listSheetType.Name = "listSheetType";
        this.listSheetType.Size = new System.Drawing.Size(142, 147);
        this.listSheetType.TabIndex = 85;
        this.listSheetType.Click += new System.EventHandler(this.listSheetType_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textFontSize);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.comboFontName);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(74, 196);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(283, 72);
        this.groupBox1.TabIndex = 88;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Default Font";
        //
        // textFontSize
        //
        this.textFontSize.Location = new System.Drawing.Point(80, 41);
        this.textFontSize.Name = "textFontSize";
        this.textFontSize.Size = new System.Drawing.Size(44, 20);
        this.textFontSize.TabIndex = 89;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(7, 42);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(71, 16);
        this.label4.TabIndex = 89;
        this.label4.Text = "Size";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFontName
        //
        this.comboFontName.FormattingEnabled = true;
        this.comboFontName.Location = new System.Drawing.Point(80, 14);
        this.comboFontName.Name = "comboFontName";
        this.comboFontName.Size = new System.Drawing.Size(197, 21);
        this.comboFontName.TabIndex = 88;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(71, 16);
        this.label3.TabIndex = 87;
        this.label3.Text = "Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(83, 275);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(83, 301);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(154, 12);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(215, 20);
        this.textDescription.TabIndex = 99;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(39, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(113, 16);
        this.label1.TabIndex = 98;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsLandscape
        //
        this.checkIsLandscape.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsLandscape.Location = new System.Drawing.Point(42, 326);
        this.checkIsLandscape.Name = "checkIsLandscape";
        this.checkIsLandscape.Size = new System.Drawing.Size(126, 20);
        this.checkIsLandscape.TabIndex = 100;
        this.checkIsLandscape.Text = "Landscape";
        this.checkIsLandscape.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsLandscape.UseVisualStyleBackColor = true;
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(154, 300);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(-100);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(69, 20);
        this.textHeight.TabIndex = 97;
        //
        // textWidth
        //
        this.textWidth.Location = new System.Drawing.Point(154, 274);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(-100);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(69, 20);
        this.textWidth.TabIndex = 95;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(319, 354);
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
        this.butCancel.Location = new System.Drawing.Point(409, 354);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetDef
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(496, 390);
        this.Controls.Add(this.checkIsLandscape);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listSheetType);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetDef";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Sheet Def";
        this.Load += new System.EventHandler(this.FormSheetDef_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listSheetType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboFontName = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textFontSize = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textWidth;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textHeight;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsLandscape = new System.Windows.Forms.CheckBox();
}


