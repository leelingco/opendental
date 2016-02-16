//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDefEditImages;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Def;
import OpenDentBusiness.Defs;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormDefEditImages  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ColorDialog colorDialog1 = new System.Windows.Forms.ColorDialog();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.CheckBox checkHidden = new System.Windows.Forms.CheckBox();
    private Def DefCur;
    private CheckBox checkT = new CheckBox();
    private CheckBox checkS = new CheckBox();
    private CheckBox checkP = new CheckBox();
    private CheckBox checkX = new CheckBox();
    private CheckBox checkF = new CheckBox();
    private GroupBox groupBox1 = new GroupBox();
    /**
    * 
    */
    public FormDefEditImages(Def defCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Lan.f(this);
        DefCur = defCur.copy();
    }

    /**
    * 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDefEditImages.class);
        this.labelName = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkHidden = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkT = new System.Windows.Forms.CheckBox();
        this.checkS = new System.Windows.Forms.CheckBox();
        this.checkP = new System.Windows.Forms.CheckBox();
        this.checkX = new System.Windows.Forms.CheckBox();
        this.checkF = new System.Windows.Forms.CheckBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(47, 24);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(150, 16);
        this.labelName.TabIndex = 0;
        this.labelName.Text = "Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(32, 40);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(178, 20);
        this.textName.TabIndex = 0;
        //
        // colorDialog1
        //
        this.colorDialog1.FullOpen = true;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(376, 159);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(471, 159);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkHidden
        //
        this.checkHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHidden.Location = new System.Drawing.Point(449, 38);
        this.checkHidden.Name = "checkHidden";
        this.checkHidden.Size = new System.Drawing.Size(99, 24);
        this.checkHidden.TabIndex = 3;
        this.checkHidden.Text = "Hidden";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.checkF);
        this.groupBox1.Controls.Add(this.checkT);
        this.groupBox1.Controls.Add(this.checkS);
        this.groupBox1.Controls.Add(this.checkP);
        this.groupBox1.Controls.Add(this.checkX);
        this.groupBox1.Location = new System.Drawing.Point(226, 22);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(215, 119);
        this.groupBox1.TabIndex = 7;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Usage";
        //
        // checkT
        //
        this.checkT.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkT.Location = new System.Drawing.Point(8, 91);
        this.checkT.Name = "checkT";
        this.checkT.Size = new System.Drawing.Size(201, 18);
        this.checkT.TabIndex = 7;
        this.checkT.Text = "Graphical Tooth Charts (only one)";
        //
        // checkS
        //
        this.checkS.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkS.Location = new System.Drawing.Point(8, 73);
        this.checkS.Name = "checkS";
        this.checkS.Size = new System.Drawing.Size(201, 18);
        this.checkS.TabIndex = 6;
        this.checkS.Text = "Statements (only one)";
        //
        // checkP
        //
        this.checkP.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkP.Location = new System.Drawing.Point(8, 55);
        this.checkP.Name = "checkP";
        this.checkP.Size = new System.Drawing.Size(201, 18);
        this.checkP.TabIndex = 5;
        this.checkP.Text = "Patient Pictures (only one)";
        //
        // checkX
        //
        this.checkX.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkX.Location = new System.Drawing.Point(8, 19);
        this.checkX.Name = "checkX";
        this.checkX.Size = new System.Drawing.Size(201, 18);
        this.checkX.TabIndex = 4;
        this.checkX.Text = "Show in Chart module";
        //
        // checkF
        //
        this.checkF.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkF.Location = new System.Drawing.Point(8, 37);
        this.checkF.Name = "checkF";
        this.checkF.Size = new System.Drawing.Size(201, 18);
        this.checkF.TabIndex = 8;
        this.checkF.Text = "Show in Patient Forms";
        //
        // FormDefEditImages
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(558, 196);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.checkHidden);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.labelName);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDefEditImages";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Image Category";
        this.Load += new System.EventHandler(this.FormDefEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDefEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //Also see DefC.GetImageCat and ImageCategorySpecial when reworking this form.
        textName.Text = DefCur.ItemName;
        //textValue.Text=DefCur.ItemValue;
        if (DefCur.ItemValue.Contains("X"))
        {
            checkX.Checked = true;
        }
         
        if (DefCur.ItemValue.Contains("F"))
        {
            checkF.Checked = true;
        }
         
        if (DefCur.ItemValue.Contains("P"))
        {
            checkP.Checked = true;
        }
         
        if (DefCur.ItemValue.Contains("S"))
        {
            checkS.Checked = true;
        }
         
        if (DefCur.ItemValue.Contains("T"))
        {
            checkT.Checked = true;
        }
         
        checkHidden.Checked = DefCur.IsHidden;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textName.Text, ""))
        {
            MsgBox.show(this,"Name required.");
            return ;
        }
         
        DefCur.ItemName = textName.Text;
        String itemVal = "";
        if (checkX.Checked)
        {
            itemVal += "X";
        }
         
        if (checkF.Checked)
        {
            itemVal += "F";
        }
         
        if (checkP.Checked)
        {
            itemVal += "P";
        }
         
        if (checkS.Checked)
        {
            itemVal += "S";
        }
         
        if (checkT.Checked)
        {
            itemVal += "T";
        }
         
        DefCur.ItemValue = itemVal;
        DefCur.IsHidden = checkHidden.Checked;
        if (IsNew)
        {
            Defs.insert(DefCur);
        }
        else
        {
            Defs.update(DefCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


