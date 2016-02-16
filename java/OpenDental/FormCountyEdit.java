//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:54 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCountyEdit;
import OpenDental.Lan;
import OpenDentBusiness.Counties;
import OpenDentBusiness.County;

/**
* Summary description for FormBasicTemplate.
*/
public class FormCountyEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textCountyName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCountyCode = new System.Windows.Forms.TextBox();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    public County CountyCur;
    /**
    * 
    */
    public FormCountyEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCountyEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textCountyName = new System.Windows.Forms.TextBox();
        this.textCountyCode = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(362, 189);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(79, 26);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(362, 148);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(79, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(13, 45);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(130, 20);
        this.label1.TabIndex = 2;
        this.label1.Text = "County Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCountyName
        //
        this.textCountyName.Location = new System.Drawing.Point(144, 47);
        this.textCountyName.Name = "textCountyName";
        this.textCountyName.Size = new System.Drawing.Size(297, 20);
        this.textCountyName.TabIndex = 0;
        this.textCountyName.TextChanged += new System.EventHandler(this.textCountyName_TextChanged);
        //
        // textCountyCode
        //
        this.textCountyCode.Location = new System.Drawing.Point(144, 83);
        this.textCountyCode.Name = "textCountyCode";
        this.textCountyCode.Size = new System.Drawing.Size(297, 20);
        this.textCountyCode.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(64, 86);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(78, 52);
        this.label2.TabIndex = 4;
        this.label2.Text = "County Code (use varies)";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormCountyEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(486, 264);
        this.Controls.Add(this.textCountyCode);
        this.Controls.Add(this.textCountyName);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormCountyEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "County Edit";
        this.Load += new System.EventHandler(this.FormCountyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formCountyEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textCountyName.Text = CountyCur.CountyName;
        textCountyCode.Text = CountyCur.CountyCode;
    }

    private void textCountyName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textCountyName.Text.Length == 1)
        {
            textCountyName.Text = textCountyName.Text.ToUpper();
            textCountyName.SelectionStart = 1;
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        CountyCur.CountyName = textCountyName.Text;
        CountyCur.CountyCode = textCountyCode.Text;
        if (IsNew)
        {
            if (Counties.doesExist(CountyCur.CountyName))
            {
                MessageBox.Show(Lan.g(this,"County name already exists. Duplicate not allowed."));
                return ;
            }
             
            Counties.insert(CountyCur);
        }
        else
        {
            //existing County
            if (!StringSupport.equals(CountyCur.CountyName, CountyCur.OldCountyName))
            {
                //County name was changed
                if (Counties.doesExist(CountyCur.CountyName))
                {
                    //changed to a name that already exists.
                    MessageBox.Show(Lan.g(this,"County name already exists. Duplicate not allowed."));
                    return ;
                }
                 
            }
             
            Counties.update(CountyCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


