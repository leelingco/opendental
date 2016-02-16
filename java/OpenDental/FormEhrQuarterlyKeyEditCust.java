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
import OpenDental.Properties.Resources;

public class FormEhrQuarterlyKeyEditCust  extends Form 
{

    public EhrQuarterlyKey KeyCur;
    public FormEhrQuarterlyKeyEditCust() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrQuarterlyKeyEditCust_Load(Object sender, EventArgs e) throws Exception {
        textYear.Text = KeyCur.YearValue.ToString();
        textQuarter.Text = KeyCur.QuarterValue.ToString();
        textPracticeTitle.Text = KeyCur.PracticeName;
        textEhrKey.Text = KeyCur.KeyValue;
        textNotes.Text = KeyCur.Notes;
    }

    private void butGenerate_Click(Object sender, EventArgs e) throws Exception {
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
         
        if (StringSupport.equals(textPracticeTitle.Text, ""))
        {
            MessageBox.Show("Please enter a practice title.");
            return ;
        }
         
        if (!StringSupport.equals(textYear.errorProvider1.GetError(textYear), "") || !StringSupport.equals(textQuarter.errorProvider1.GetError(textQuarter), ""))
        {
            MessageBox.Show("Please fix errors first.");
            return ;
        }
         
        //Path for testing:
        //@"E:\My Documents\Shared Projects Subversion\EhrProvKeyGenerator\EhrProvKeyGenerator\bin\Debug\EhrProvKeyGenerator.exe"
        String progPath = PrefC.getString(PrefName.EhrProvKeyGeneratorPath);
        ProcessStartInfo startInfo = new ProcessStartInfo(progPath);
        startInfo.Arguments = "Q \"" + textYear.Text + "\" \"" + textQuarter.Text + "\" \"" + textPracticeTitle.Text + "\"";
        startInfo.UseShellExecute = false;
        startInfo.RedirectStandardOutput = true;
        Process process = Process.Start(startInfo);
        String result = process.StandardOutput.ReadToEnd();
        result = result.Trim();
        //remove \r\n from the end
        textEhrKey.Text = result;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
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
         
        if (StringSupport.equals(textPracticeTitle.Text, ""))
        {
            MessageBox.Show("Please enter a practice title.");
            return ;
        }
         
        if (!StringSupport.equals(textYear.errorProvider1.GetError(textYear), "") || !StringSupport.equals(textQuarter.errorProvider1.GetError(textQuarter), ""))
        {
            MessageBox.Show("Please fix errors first.");
            return ;
        }
         
        int quarterValue = PIn.Int(textQuarter.Text);
        int yearValue = PIn.Int(textYear.Text);
        int monthOfQuarter = 1;
        if (quarterValue == 2)
        {
            monthOfQuarter = 4;
        }
         
        if (quarterValue == 3)
        {
            monthOfQuarter = 7;
        }
         
        if (quarterValue == 4)
        {
            monthOfQuarter = 10;
        }
         
        DateTime firstDayOfQuarter = new DateTime(2000 + yearValue, monthOfQuarter, 1);
        DateTime earliestReleaseDate = firstDayOfQuarter.AddMonths(-1);
        if (DateTime.Today < earliestReleaseDate)
        {
            MessageBox.Show("Quarterly keys cannot be released more than one month in advance.");
            return ;
        }
         
        if (!FormEHR.QuarterlyKeyIsValid(textYear.Text, textQuarter.Text, textPracticeTitle.Text, textEhrKey.Text))
        {
            MsgBox.show(this,"Invalid quarterly key");
            return ;
        }
         
        KeyCur.YearValue = PIn.Int(textYear.Text);
        KeyCur.QuarterValue = PIn.Int(textQuarter.Text);
        KeyCur.PracticeName = textPracticeTitle.Text;
        KeyCur.KeyValue = textEhrKey.Text;
        KeyCur.Notes = textNotes.Text;
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
        this.label1 = new System.Windows.Forms.Label();
        this.textNotes = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPracticeTitle = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textEhrKey = new System.Windows.Forms.TextBox();
        this.labelEhrKey = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butGenerate = new OpenDental.UI.Button();
        this.textQuarter = new OpenDental.ValidNum();
        this.textYear = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(42, 93);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 18);
        this.label2.TabIndex = 13;
        this.label2.Text = "Quarter, ex: 2";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(42, 67);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 18);
        this.label1.TabIndex = 12;
        this.label1.Text = "Year, ex: 12";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNotes
        //
        this.textNotes.Location = new System.Drawing.Point(147, 170);
        this.textNotes.Multiline = true;
        this.textNotes.Name = "textNotes";
        this.textNotes.Size = new System.Drawing.Size(319, 92);
        this.textNotes.TabIndex = 126;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(59, 174);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(88, 14);
        this.label4.TabIndex = 127;
        this.label4.Text = "Notes";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPracticeTitle
        //
        this.textPracticeTitle.Location = new System.Drawing.Point(147, 117);
        this.textPracticeTitle.MaxLength = 100;
        this.textPracticeTitle.Name = "textPracticeTitle";
        this.textPracticeTitle.Size = new System.Drawing.Size(319, 20);
        this.textPracticeTitle.TabIndex = 130;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(19, 121);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(127, 14);
        this.label8.TabIndex = 131;
        this.label8.Text = "Practice Title";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textEhrKey
        //
        this.textEhrKey.Location = new System.Drawing.Point(147, 143);
        this.textEhrKey.MaxLength = 15;
        this.textEhrKey.Name = "textEhrKey";
        this.textEhrKey.Size = new System.Drawing.Size(161, 20);
        this.textEhrKey.TabIndex = 128;
        //
        // labelEhrKey
        //
        this.labelEhrKey.Location = new System.Drawing.Point(22, 147);
        this.labelEhrKey.Name = "labelEhrKey";
        this.labelEhrKey.Size = new System.Drawing.Size(125, 14);
        this.labelEhrKey.TabIndex = 129;
        this.labelEhrKey.Text = "Quarterly EHR Key";
        this.labelEhrKey.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(146, 25);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(422, 38);
        this.label3.TabIndex = 133;
        this.label3.Text = "The quarterly key is tied to the practice title as entered in THEIR system.  It\'s" + " best to copy/paste from their practice setup window.";
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
        this.butDelete.Location = new System.Drawing.Point(25, 292);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 134;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butGenerate
        //
        this.butGenerate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGenerate.setAutosize(true);
        this.butGenerate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGenerate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGenerate.setCornerRadius(4F);
        this.butGenerate.Location = new System.Drawing.Point(311, 141);
        this.butGenerate.Name = "butGenerate";
        this.butGenerate.Size = new System.Drawing.Size(75, 24);
        this.butGenerate.TabIndex = 132;
        this.butGenerate.Text = "Generate";
        this.butGenerate.Click += new System.EventHandler(this.butGenerate_Click);
        //
        // textQuarter
        //
        this.textQuarter.Location = new System.Drawing.Point(147, 91);
        this.textQuarter.setMaxVal(4);
        this.textQuarter.setMinVal(1);
        this.textQuarter.Name = "textQuarter";
        this.textQuarter.Size = new System.Drawing.Size(58, 20);
        this.textQuarter.TabIndex = 17;
        //
        // textYear
        //
        this.textYear.Location = new System.Drawing.Point(147, 66);
        this.textYear.setMaxVal(20);
        this.textYear.setMinVal(11);
        this.textYear.Name = "textYear";
        this.textYear.Size = new System.Drawing.Size(58, 20);
        this.textYear.TabIndex = 16;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(430, 292);
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
        this.butCancel.Location = new System.Drawing.Point(523, 292);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormEhrQuarterlyKeyEditCust
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(610, 328);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butGenerate);
        this.Controls.Add(this.textPracticeTitle);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textEhrKey);
        this.Controls.Add(this.labelEhrKey);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textQuarter);
        this.Controls.Add(this.textYear);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEhrQuarterlyKeyEditCust";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Ehr Quarterly Key for Customer";
        this.Load += new System.EventHandler(this.FormEhrQuarterlyKeyEditCust_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.ValidNum textQuarter;
    private OpenDental.ValidNum textYear;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNotes = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butGenerate;
    private System.Windows.Forms.TextBox textPracticeTitle = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEhrKey = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelEhrKey = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
}


