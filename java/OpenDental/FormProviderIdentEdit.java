//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import OpenDental.FormProviderIdentEdit;
import OpenDental.Lan;
import OpenDental.ProviderIdent;
import OpenDental.ProviderIdents;
import OpenDentBusiness.ProviderSupplementalID;

/**
* Summary description for FormBasicTemplate.
*/
public class FormProviderIdentEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Set this field externally before using this window.
    */
    public ProviderIdent ProvIdentCur;
    private System.Windows.Forms.ListBox listType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayorID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textIDNumber = new System.Windows.Forms.TextBox();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormProviderIdentEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProviderIdentEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listType = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textPayorID = new System.Windows.Forms.TextBox();
        this.textIDNumber = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(382, 220);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(382, 179);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listType
        //
        this.listType.Location = new System.Drawing.Point(162, 44);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(120, 95);
        this.listType.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(41, 42);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(118, 19);
        this.label1.TabIndex = 3;
        this.label1.Text = "Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(145, 19);
        this.label2.TabIndex = 4;
        this.label2.Text = "Payor (Electronic) ID";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayorID
        //
        this.textPayorID.Location = new System.Drawing.Point(162, 20);
        this.textPayorID.Name = "textPayorID";
        this.textPayorID.Size = new System.Drawing.Size(90, 20);
        this.textPayorID.TabIndex = 5;
        //
        // textIDNumber
        //
        this.textIDNumber.Location = new System.Drawing.Point(162, 143);
        this.textIDNumber.Name = "textIDNumber";
        this.textIDNumber.Size = new System.Drawing.Size(90, 20);
        this.textIDNumber.TabIndex = 7;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(14, 145);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(145, 19);
        this.label3.TabIndex = 6;
        this.label3.Text = "Assigned ID Number";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormProviderIdentEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(483, 269);
        this.Controls.Add(this.textIDNumber);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textPayorID);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProviderIdentEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Provider Identification";
        this.Load += new System.EventHandler(this.FormProviderIdentEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProviderIdentEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textPayorID.Text = ProvIdentCur.PayorID;
        //for(int i=0;i<.Length;i++){
        listType.Items.AddRange(Enum.GetNames(ProviderSupplementalID.class));
        //}
        listType.SelectedIndex = ((Enum)ProvIdentCur.SuppIDType).ordinal();
        textIDNumber.Text = ProvIdentCur.IDNumber;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ProvIdentCur.PayorID = textPayorID.Text;
        ProvIdentCur.SuppIDType = (ProviderSupplementalID)listType.SelectedIndex;
        ProvIdentCur.IDNumber = textIDNumber.Text;
        if (IsNew)
        {
            ProviderIdents.Insert(ProvIdentCur);
        }
        else
        {
            ProviderIdents.Update(ProvIdentCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


