//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDental.FormRecallListUndo;

public class FormRecallListUndo  extends Form 
{

    public FormRecallListUndo() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formRecallListUndo_Load(Object sender, EventArgs e) throws Exception {
        textDate.Text = DateTime.Today.ToShortDateString();
    }

    private void textDate_TextChanged(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            int count = Commlogs.GetRecallUndoCount(PIn.Date(textDate.Text));
            labelCount.Text = count.ToString();
        }
        else
        {
            labelCount.Text = "";
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Invalid date");
            return ;
        }
         
        DateTime date = PIn.Date(textDate.Text);
        if (date < DateTime.Today.AddDays(-7))
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Date is from more than one week ago.  Continue anyway?"))
            {
                return ;
            }
             
        }
         
        if (MessageBox.Show("Delete all " + labelCount.Text + " commlog entries?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        Commlogs.recallUndo(date);
        MsgBox.show(this,"Done");
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRecallListUndo.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.labelCount = new System.Windows.Forms.Label();
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
        this.butOK.Location = new System.Drawing.Point(401, 159);
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
        this.butCancel.Location = new System.Drawing.Point(401, 200);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(455, 74);
        this.label1.TabIndex = 4;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(126, 95);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 5;
        this.textDate.TextChanged += new System.EventHandler(this.textDate_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(54, 95);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(68, 18);
        this.label2.TabIndex = 6;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 128);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(108, 18);
        this.label3.TabIndex = 7;
        this.label3.Text = "Patients Affected:";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCount
        //
        this.labelCount.Location = new System.Drawing.Point(123, 128);
        this.labelCount.Name = "labelCount";
        this.labelCount.Size = new System.Drawing.Size(68, 18);
        this.labelCount.TabIndex = 8;
        this.labelCount.Text = "100";
        this.labelCount.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormRecallListUndo
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(501, 251);
        this.Controls.Add(this.labelCount);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormRecallListUndo";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Recall List Undo";
        this.Load += new System.EventHandler(this.FormRecallListUndo_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCount = new System.Windows.Forms.Label();
}


