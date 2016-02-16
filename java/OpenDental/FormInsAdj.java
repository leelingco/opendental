//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:14 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormInsAdj;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;

/**
* 
*/
public class FormInsAdj  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDate;
    private OpenDental.ValidDouble textInsUsed;
    private OpenDental.ValidDouble textDedUsed;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    private ClaimProc ClaimProcCur;
    /**
    * 
    */
    public FormInsAdj(ClaimProc claimProcCur) throws Exception {
        ClaimProcCur = claimProcCur;
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsAdj.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.textInsUsed = new OpenDental.ValidDouble();
        this.textDedUsed = new OpenDental.ValidDouble();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(29, 74);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 14);
        this.label1.TabIndex = 0;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(15, 130);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(114, 14);
        this.label2.TabIndex = 1;
        this.label2.Text = "Deductible Used";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(17, 102);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(112, 14);
        this.label3.TabIndex = 2;
        this.label3.Text = "Insurance Used";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(131, 71);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(75, 20);
        this.textDate.TabIndex = 0;
        //
        // textInsUsed
        //
        this.textInsUsed.Location = new System.Drawing.Point(131, 99);
        this.textInsUsed.Name = "textInsUsed";
        this.textInsUsed.Size = new System.Drawing.Size(69, 20);
        this.textInsUsed.TabIndex = 1;
        //
        // textDedUsed
        //
        this.textDedUsed.Location = new System.Drawing.Point(131, 127);
        this.textDedUsed.Name = "textDedUsed";
        this.textDedUsed.Size = new System.Drawing.Size(69, 20);
        this.textDedUsed.TabIndex = 2;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(260, 148);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(260, 180);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(32, 12);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(259, 41);
        this.label4.TabIndex = 5;
        this.label4.Text = "Make sure the date you use falls within the correct benefit year.";
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
        this.butDelete.Location = new System.Drawing.Point(18, 180);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormInsAdj
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(354, 217);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textDedUsed);
        this.Controls.Add(this.textInsUsed);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsAdj";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Insurance Adjustments";
        this.Load += new System.EventHandler(this.FormInsAdj_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsAdj_Load(Object sender, System.EventArgs e) throws Exception {
        textDate.Text = ClaimProcCur.ProcDate.ToShortDateString();
        textInsUsed.Text = ClaimProcCur.InsPayAmt.ToString("F");
        textDedUsed.Text = ClaimProcCur.DedApplied.ToString("F");
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show("All",MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        ClaimProcs.delete(ClaimProcCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textInsUsed.errorProvider1.GetError(textInsUsed), "") || !StringSupport.equals(textDedUsed.errorProvider1.GetError(textDedUsed), ""))
        {
            MessageBox.Show(Lan.g("All","Please fix data entry errors first."));
            return ;
        }
         
        ClaimProcCur.ProcDate = PIn.Date(textDate.Text);
        ClaimProcCur.InsPayAmt = PIn.Double(textInsUsed.Text);
        ClaimProcCur.DedApplied = PIn.Double(textDedUsed.Text);
        if (IsNew)
        {
            ClaimProcs.insert(ClaimProcCur);
        }
        else
        {
            ClaimProcs.update(ClaimProcCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


