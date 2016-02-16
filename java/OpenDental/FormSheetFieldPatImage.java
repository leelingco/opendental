//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.SheetUtil;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDental.Properties.Resources;

public class FormSheetFieldPatImage  extends Form 
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
    public FormSheetFieldPatImage() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetFieldPatImage_Load(Object sender, EventArgs e) throws Exception {
        if (IsReadOnly)
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
         
        fillCombo();
        comboFieldName.Text = SheetFieldDefCur.FieldName;
        fillImage();
        textXPos.Text = SheetFieldDefCur.XPos.ToString();
        textYPos.Text = SheetFieldDefCur.YPos.ToString();
        textWidth.Text = SheetFieldDefCur.Width.ToString();
        textHeight.Text = SheetFieldDefCur.Height.ToString();
    }

    private void fillCombo() throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            comboFieldName.Items.Clear();
            String[] files = Directory.GetFiles(SheetUtil.getImagePath());
            for (int i = 0;i < files.Length;i++)
            {
                comboFieldName.Items.Add(Path.GetFileName(files[i]));
            }
        }
         
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog dlg = new OpenFileDialog();
        dlg.Multiselect = false;
        if (dlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (!File.Exists(dlg.FileName))
        {
            MsgBox.show(this,"File does not exist.");
            return ;
        }
         
        String newName = CodeBase.ODFileUtils.CombinePaths(SheetUtil.getImagePath(), Path.GetFileName(dlg.FileName));
        if (File.Exists(newName))
        {
            MsgBox.show(this,"A file of that name already exists in SheetImages.  Please rename the file before importing.");
            return ;
        }
         
        File.Copy(dlg.FileName, newName);
        fillCombo();
        for (int i = 0;i < comboFieldName.Items.Count;i++)
        {
            if (comboFieldName.Items[i].ToString() == Path.GetFileName(newName))
            {
                comboFieldName.SelectedIndex = i;
                comboFieldName.Text = Path.GetFileName(newName);
                fillImage();
                shrinkToFit();
            }
             
        }
    }

    private void comboFieldName_TextUpdate(Object sender, EventArgs e) throws Exception {
        fillImage();
        shrinkToFit();
    }

    private void comboFieldName_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        comboFieldName.Text = comboFieldName.SelectedItem.ToString();
        fillImage();
        shrinkToFit();
    }

    private void fillImage() throws Exception {
        textFullPath.Text = CodeBase.ODFileUtils.CombinePaths(SheetUtil.getImagePath(), comboFieldName.Text);
        if (File.Exists(textFullPath.Text))
        {
            pictureBox.Image = Image.FromFile(textFullPath.Text);
            textWidth2.Text = pictureBox.Image.Width.ToString();
            textHeight2.Text = pictureBox.Image.Height.ToString();
        }
        else
        {
            pictureBox.Image = null;
            textWidth2.Text = "";
            textHeight2.Text = "";
        } 
    }

    private void butShrink_Click(Object sender, EventArgs e) throws Exception {
        shrinkToFit();
    }

    private void shrinkToFit() throws Exception {
        if (pictureBox.Image == null)
        {
            return ;
        }
         
        if (pictureBox.Image.Width > SheetDefCur.Width || pictureBox.Image.Height > SheetDefCur.Height)
        {
            //image would be too big
            float imgWtoH = ((float)pictureBox.Image.Width) / ((float)pictureBox.Image.Height);
            float sheetWtoH = ((float)SheetDefCur.Width) / ((float)SheetDefCur.Height);
            float newRatio = new float();
            int newW = new int();
            int newH = new int();
            if (imgWtoH < sheetWtoH)
            {
                //image is tall and skinny
                newRatio = ((float)SheetDefCur.Height) / ((float)pictureBox.Image.Height);
                //restrict by height
                newW = (int)(((float)pictureBox.Image.Width) * newRatio);
                newH = (int)(((float)pictureBox.Image.Height) * newRatio);
                textWidth.Text = newW.ToString();
                textHeight.Text = newH.ToString();
            }
            else
            {
                //image is short and fat
                newRatio = ((float)SheetDefCur.Width) / ((float)pictureBox.Image.Width);
                //restrict by width
                newW = (int)(((float)pictureBox.Image.Width) * newRatio);
                newH = (int)(((float)pictureBox.Image.Height) * newRatio);
                textWidth.Text = newW.ToString();
                textHeight.Text = newH.ToString();
            } 
        }
        else
        {
            textWidth.Text = pictureBox.Image.Width.ToString();
            textHeight.Text = pictureBox.Image.Height.ToString();
        } 
    }

    private void textWidth_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        if (!checkRatio.Checked)
        {
            return ;
        }
         
        if (pictureBox.Image == null)
        {
            return ;
        }
         
        float w = new float();
        try
        {
            w = PIn.Float(textWidth.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        float imgWtoH = ((float)pictureBox.Image.Width) / ((float)pictureBox.Image.Height);
        int newH = (int)(w / imgWtoH);
        textHeight.Text = newH.ToString();
    }

    private void textHeight_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        if (!checkRatio.Checked)
        {
            return ;
        }
         
        if (pictureBox.Image == null)
        {
            return ;
        }
         
        float h = new float();
        try
        {
            h = PIn.Float(textHeight.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            return ;
        }

        float imgWtoH = ((float)pictureBox.Image.Width) / ((float)pictureBox.Image.Height);
        int newW = (int)(h * imgWtoH);
        textWidth.Text = newW.ToString();
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
         
        if (StringSupport.equals(comboFieldName.Text, ""))
        {
            MsgBox.show(this,"Please enter a file name first.");
            return ;
        }
         
        if (!File.Exists(textFullPath.Text))
        {
            MsgBox.show(this,"Image file does not exist.");
            return ;
        }
         
        SheetFieldDefCur.FieldName = comboFieldName.Text;
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
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textFullPath = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboFieldName = new System.Windows.Forms.ComboBox();
        this.textWidth2 = new System.Windows.Forms.TextBox();
        this.textHeight2 = new System.Windows.Forms.TextBox();
        this.pictureBox = new System.Windows.Forms.PictureBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkRatio = new System.Windows.Forms.CheckBox();
        this.butShrink = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.textHeight = new OpenDental.ValidNum();
        this.textWidth = new OpenDental.ValidNum();
        this.textYPos = new OpenDental.ValidNum();
        this.textXPos = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).BeginInit();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(70, 332);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(71, 16);
        this.label5.TabIndex = 90;
        this.label5.Text = "X Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(70, 358);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(71, 16);
        this.label6.TabIndex = 92;
        this.label6.Text = "Y Pos";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(70, 384);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(71, 16);
        this.label7.TabIndex = 94;
        this.label7.Text = "Width";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(70, 410);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(71, 16);
        this.label8.TabIndex = 96;
        this.label8.Text = "Height";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(26, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(113, 16);
        this.label1.TabIndex = 101;
        this.label1.Text = "File Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFullPath
        //
        this.textFullPath.Location = new System.Drawing.Point(141, 43);
        this.textFullPath.Name = "textFullPath";
        this.textFullPath.ReadOnly = true;
        this.textFullPath.Size = new System.Drawing.Size(434, 20);
        this.textFullPath.TabIndex = 104;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(26, 44);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(113, 16);
        this.label2.TabIndex = 103;
        this.label2.Text = "Full Path";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFieldName
        //
        this.comboFieldName.FormattingEnabled = true;
        this.comboFieldName.Location = new System.Drawing.Point(141, 16);
        this.comboFieldName.MaxDropDownItems = 100;
        this.comboFieldName.Name = "comboFieldName";
        this.comboFieldName.Size = new System.Drawing.Size(257, 21);
        this.comboFieldName.TabIndex = 106;
        this.comboFieldName.SelectionChangeCommitted += new System.EventHandler(this.comboFieldName_SelectionChangeCommitted);
        this.comboFieldName.TextUpdate += new System.EventHandler(this.comboFieldName_TextUpdate);
        //
        // textWidth2
        //
        this.textWidth2.Location = new System.Drawing.Point(6, 14);
        this.textWidth2.Name = "textWidth2";
        this.textWidth2.ReadOnly = true;
        this.textWidth2.Size = new System.Drawing.Size(51, 20);
        this.textWidth2.TabIndex = 110;
        //
        // textHeight2
        //
        this.textHeight2.Location = new System.Drawing.Point(6, 40);
        this.textHeight2.Name = "textHeight2";
        this.textHeight2.ReadOnly = true;
        this.textHeight2.Size = new System.Drawing.Size(51, 20);
        this.textHeight2.TabIndex = 111;
        //
        // pictureBox
        //
        this.pictureBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.pictureBox.Location = new System.Drawing.Point(141, 69);
        this.pictureBox.Name = "pictureBox";
        this.pictureBox.Size = new System.Drawing.Size(255, 255);
        this.pictureBox.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
        this.pictureBox.TabIndex = 112;
        this.pictureBox.TabStop = false;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textWidth2);
        this.groupBox1.Controls.Add(this.textHeight2);
        this.groupBox1.Location = new System.Drawing.Point(198, 369);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(63, 66);
        this.groupBox1.TabIndex = 113;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "File Size";
        //
        // checkRatio
        //
        this.checkRatio.Checked = true;
        this.checkRatio.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkRatio.Location = new System.Drawing.Point(267, 413);
        this.checkRatio.Name = "checkRatio";
        this.checkRatio.Size = new System.Drawing.Size(104, 20);
        this.checkRatio.TabIndex = 115;
        this.checkRatio.Text = "Maintain Ratio";
        this.checkRatio.UseVisualStyleBackColor = true;
        //
        // butShrink
        //
        this.butShrink.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShrink.setAutosize(true);
        this.butShrink.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShrink.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShrink.setCornerRadius(4F);
        this.butShrink.Location = new System.Drawing.Point(267, 381);
        this.butShrink.Name = "butShrink";
        this.butShrink.Size = new System.Drawing.Size(79, 24);
        this.butShrink.TabIndex = 114;
        this.butShrink.Text = "ShrinkToFit";
        this.butShrink.Click += new System.EventHandler(this.butShrink_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Location = new System.Drawing.Point(404, 14);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 24);
        this.butImport.TabIndex = 105;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
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
        this.butDelete.Location = new System.Drawing.Point(15, 525);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(77, 24);
        this.butDelete.TabIndex = 100;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(141, 409);
        this.textHeight.setMaxVal(2000);
        this.textHeight.setMinVal(1);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(51, 20);
        this.textHeight.TabIndex = 97;
        this.textHeight.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textHeight_KeyUp);
        //
        // textWidth
        //
        this.textWidth.Location = new System.Drawing.Point(141, 383);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(1);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(51, 20);
        this.textWidth.TabIndex = 95;
        this.textWidth.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textWidth_KeyUp);
        //
        // textYPos
        //
        this.textYPos.Location = new System.Drawing.Point(141, 357);
        this.textYPos.setMaxVal(2000);
        this.textYPos.setMinVal(-100);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(51, 20);
        this.textYPos.TabIndex = 93;
        //
        // textXPos
        //
        this.textXPos.Location = new System.Drawing.Point(141, 331);
        this.textXPos.setMaxVal(2000);
        this.textXPos.setMinVal(-100);
        this.textXPos.Name = "textXPos";
        this.textXPos.Size = new System.Drawing.Size(51, 20);
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
        this.butOK.Location = new System.Drawing.Point(514, 495);
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
        this.butCancel.Location = new System.Drawing.Point(514, 525);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetFieldPatImage
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(601, 561);
        this.Controls.Add(this.checkRatio);
        this.Controls.Add(this.butShrink);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.pictureBox);
        this.Controls.Add(this.comboFieldName);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.textFullPath);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textYPos);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textXPos);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFieldPatImage";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Image Field";
        this.Load += new System.EventHandler(this.FormSheetFieldPatImage_Load);
        ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).EndInit();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textXPos;
    private OpenDental.ValidNum textYPos;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textWidth;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textHeight;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFullPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.ComboBox comboFieldName = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textWidth2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textHeight2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.PictureBox pictureBox = new System.Windows.Forms.PictureBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butShrink;
    private System.Windows.Forms.CheckBox checkRatio = new System.Windows.Forms.CheckBox();
}


