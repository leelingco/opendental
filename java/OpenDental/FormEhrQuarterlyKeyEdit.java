//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEHR;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.EhrQuarterlyKeys;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

public class FormEhrQuarterlyKeyEdit  extends Form 
{

    public EhrQuarterlyKey KeyCur;
    public FormEhrQuarterlyKeyEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrQuarterlyKeyEdit_Load(Object sender, EventArgs e) throws Exception {
        if (KeyCur.YearValue > 0)
        {
            textYear.Text = KeyCur.YearValue.ToString();
        }
         
        if (KeyCur.QuarterValue > 0)
        {
            textQuarter.Text = KeyCur.QuarterValue.ToString();
        }
         
        textKey.Text = KeyCur.KeyValue;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (KeyCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        EhrQuarterlyKeys.delete(KeyCur.EhrQuarterlyKeyNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            MsgBox.show(this,"You must go to Setup, Show Features, and activate EHR before entering keys.");
            return ;
        }
         
        if (StringSupport.equals(textYear.Text, ""))
        {
            MessageBox.Show("Please enter a year.");
            return ;
        }
         
        if (StringSupport.equals(textQuarter.Text, ""))
        {
            MessageBox.Show("Please enter a quarter.");
            return ;
        }
         
        if (!StringSupport.equals(textYear.errorProvider1.GetError(textYear), "") || !StringSupport.equals(textQuarter.errorProvider1.GetError(textQuarter), ""))
        {
            MessageBox.Show("Please fix errors first.");
            return ;
        }
         
        if (!FormEHR.QuarterlyKeyIsValid(textYear.Text, textQuarter.Text, PrefC.getString(PrefName.PracticeTitle), textKey.Text))
        {
            MsgBox.show(this,"Invalid quarterly key");
            return ;
        }
         
        KeyCur.YearValue = PIn.Int(textYear.Text);
        KeyCur.QuarterValue = PIn.Int(textQuarter.Text);
        KeyCur.KeyValue = textKey.Text;
        KeyCur.PracticeName = PrefC.getString(PrefName.PracticeTitle);
        if (KeyCur.getIsNew())
        {
            EhrQuarterlyKeys.insert(KeyCur);
        }
        else
        {
            EhrQuarterlyKeys.update(KeyCur);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textKey = new System.Windows.Forms.TextBox();
        this.textYear = new OpenDental.ValidNum();
        this.textQuarter = new OpenDental.ValidNum();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(204, 119);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.Location = new System.Drawing.Point(294, 119);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(17, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 18);
        this.label1.TabIndex = 6;
        this.label1.Text = "Year, ex: 12";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(17, 50);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 18);
        this.label2.TabIndex = 7;
        this.label2.Text = "Quarter, ex: 2";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(17, 74);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 18);
        this.label3.TabIndex = 8;
        this.label3.Text = "Key";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textKey
        //
        this.textKey.Location = new System.Drawing.Point(122, 73);
        this.textKey.Name = "textKey";
        this.textKey.Size = new System.Drawing.Size(163, 20);
        this.textKey.TabIndex = 2;
        //
        // textYear
        //
        this.textYear.Location = new System.Drawing.Point(122, 23);
        this.textYear.setMaxVal(20);
        this.textYear.setMinVal(11);
        this.textYear.Name = "textYear";
        this.textYear.Size = new System.Drawing.Size(58, 20);
        this.textYear.TabIndex = 0;
        //
        // textQuarter
        //
        this.textQuarter.Location = new System.Drawing.Point(122, 48);
        this.textQuarter.setMaxVal(4);
        this.textQuarter.setMinVal(1);
        this.textQuarter.Name = "textQuarter";
        this.textQuarter.Size = new System.Drawing.Size(58, 20);
        this.textQuarter.TabIndex = 1;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(12, 119);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 3;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormEhrQuarterlyKeyEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(388, 158);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textQuarter);
        this.Controls.Add(this.textYear);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textKey);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEhrQuarterlyKeyEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Ehr Quarterly Key";
        this.Load += new System.EventHandler(this.FormEhrQuarterlyKeyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textKey = new System.Windows.Forms.TextBox();
    private OpenDental.ValidNum textYear;
    private OpenDental.ValidNum textQuarter;
    private OpenDental.UI.Button butDelete;
}


