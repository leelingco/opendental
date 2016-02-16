//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDental.Properties.Resources;


/**
* This window cannot even be accessed any longer.
*/
public class FormIcd9Edit  extends Form 
{

    private ICD9 Icd9Cur;
    public boolean IsNew = new boolean();
    public FormIcd9Edit(ICD9 icd9Cur) throws Exception {
        initializeComponent();
        Lan.F(this);
        Icd9Cur = icd9Cur;
    }

    private void formIcd9Edit_Load(Object sender, EventArgs e) throws Exception {
        if (!IsNew)
        {
            textCode.Enabled = false;
        }
         
        textCode.Text = Icd9Cur.ICD9Code;
        textDescription.Text = Icd9Cur.Description;
    }

    private void buttonDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        try
        {
            ICD9s.delete(Icd9Cur.ICD9Num);
            DialogResult = DialogResult.OK;
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Icd9Cur.ICD9Code = textCode.Text;
        Icd9Cur.Description = textDescription.Text;
        if (IsNew)
        {
            //Used the "+Add" button to open this form.
            if (ICD9s.codeExists(Icd9Cur.ICD9Code))
            {
                //Must enter a unique code.
                MsgBox.show(this,"You must choose a unique code.");
                return ;
            }
             
            ICD9s.insert(Icd9Cur);
        }
        else
        {
            ICD9s.update(Icd9Cur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textCode = new System.Windows.Forms.TextBox();
        this.buttonDelete = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.service11 = new OpenDental.localhost.Service1();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(91, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Code";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(9, 49);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(94, 16);
        this.label2.TabIndex = 4;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(109, 45);
        this.textDescription.Name = "textDescription";
        this.textDescription.ReadOnly = true;
        this.textDescription.Size = new System.Drawing.Size(317, 20);
        this.textDescription.TabIndex = 1;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(109, 19);
        this.textCode.Name = "textCode";
        this.textCode.ReadOnly = true;
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 0;
        //
        // buttonDelete
        //
        this.buttonDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.buttonDelete.setAutosize(true);
        this.buttonDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonDelete.setCornerRadius(4F);
        this.buttonDelete.Image = Resources.getdeleteX();
        this.buttonDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.buttonDelete.Location = new System.Drawing.Point(22, 97);
        this.buttonDelete.Name = "buttonDelete";
        this.buttonDelete.Size = new System.Drawing.Size(82, 25);
        this.buttonDelete.TabIndex = 6;
        this.buttonDelete.Text = "&Delete";
        this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(352, 98);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // service11
        //
        this.service11.setUrl("http://localhost:3824/Service1.asmx");
        this.service11.setUseDefaultCredentials(true);
        //
        // FormIcd9Edit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(452, 144);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.buttonDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Name = "FormIcd9Edit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "ICD9 Edit";
        this.Load += new System.EventHandler(this.FormIcd9Edit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button buttonDelete;
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private OpenDental.localhost.Service1 service11;
}


