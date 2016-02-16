//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormToothGridDef;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDental.Properties.Resources;

public class FormSheetFieldSpecial  extends Form 
{

    /**
    * This is the object we are editing.
    */
    public SheetFieldDef SheetFieldDefCur;
    /**
    * We need access to a few other fields of the sheetDef.
    */
    public SheetDef SheetDefCur;
    //private List<SheetFieldDef> AvailFields;
    public boolean IsReadOnly = new boolean();
    public FormSheetFieldSpecial() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldDefEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsReadOnly)
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
         
        textXPos.Text = SheetFieldDefCur.XPos.ToString();
        textYPos.Text = SheetFieldDefCur.YPos.ToString();
        textWidth.Text = SheetFieldDefCur.Width.ToString();
        textHeight.Text = SheetFieldDefCur.Height.ToString();
    }

    private void listFields_DoubleClick(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void butSetup_Click(Object sender, EventArgs e) throws Exception {
        FormToothGridDef FormTGD = new FormToothGridDef();
        //FormTGD.SheetFieldDefCur=SheetFieldDefCur;
        FormTGD.ShowDialog();
        if (FormTGD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
    }

    //SheetFieldDefCur=FormTGD.SheetFieldDefCur;
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        SheetFieldDefCur = null;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        saveAndClose();
    }

    private void saveAndClose() throws Exception {
        if (!StringSupport.equals(textXPos.errorProvider1.GetError(textXPos), "") || !StringSupport.equals(textYPos.errorProvider1.GetError(textYPos), "") || !StringSupport.equals(textWidth.errorProvider1.GetError(textWidth), "") || !StringSupport.equals(textHeight.errorProvider1.GetError(textHeight), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        SheetFieldDefCur.FieldName = "ToothGrid";
        SheetFieldDefCur.XPos = PIn.Int(textXPos.Text);
        SheetFieldDefCur.YPos = PIn.Int(textYPos.Text);
        SheetFieldDefCur.Width = PIn.Int(textWidth.Text);
        SheetFieldDefCur.Height = PIn.Int(textHeight.Text);
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
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.textYPos = new OpenDental.ValidNum();
        this.textXPos = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.butSetup = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 23);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(108, 36);
        this.label2.TabIndex = 86;
        this.label2.Text = "FieldName:\r\nToothGrid";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(197, 90);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(71, 16);
        this.label5.TabIndex = 90;
        this.label5.Text = "X Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(197, 116);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(71, 16);
        this.label6.TabIndex = 92;
        this.label6.Text = "Y Pos";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(197, 142);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(197, 168);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(268, 167);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(-100);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(69, 20);
        this.textHeight.TabIndex = 97;
        //
        // textWidth
        //
        this.textWidth.Location = new System.Drawing.Point(268, 141);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(-100);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(69, 20);
        this.textWidth.TabIndex = 95;
        //
        // textYPos
        //
        this.textYPos.Location = new System.Drawing.Point(268, 115);
        this.textYPos.setMaxVal(2000);
        this.textYPos.setMinVal(-100);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(69, 20);
        this.textYPos.TabIndex = 93;
        //
        // textXPos
        //
        this.textXPos.Location = new System.Drawing.Point(268, 89);
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
        this.butOK.Location = new System.Drawing.Point(391, 205);
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
        this.butCancel.Location = new System.Drawing.Point(391, 235);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(15, 235);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(77, 24);
        this.butDelete.TabIndex = 100;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(175, 22);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(207, 65);
        this.label3.TabIndex = 101;
        this.label3.Text = "A tooth grid has 32 rows, one for each tooth.  Columns are customizable.  Recomme" + "nded size is 500 x 300.";
        //
        // butSetup
        //
        this.butSetup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSetup.setAutosize(true);
        this.butSetup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetup.setCornerRadius(4F);
        this.butSetup.Location = new System.Drawing.Point(391, 23);
        this.butSetup.Name = "butSetup";
        this.butSetup.Size = new System.Drawing.Size(75, 24);
        this.butSetup.TabIndex = 102;
        this.butSetup.Text = "Setup";
        this.butSetup.Click += new System.EventHandler(this.butSetup_Click);
        //
        // FormSheetFieldSpecial
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(478, 271);
        this.Controls.Add(this.butSetup);
        this.Controls.Add(this.label3);
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
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldSpecial";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Special Field";
        this.Load += new System.EventHandler(this.FormSheetFieldDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textXPos;
    private OpenDental.ValidNum textYPos;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textWidth;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textHeight;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSetup;
}


