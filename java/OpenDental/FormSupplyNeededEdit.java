//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.SupplyNeeded;
import OpenDentBusiness.SupplyNeededs;
import OpenDental.Properties.Resources;

public class FormSupplyNeededEdit  extends Form 
{

    public SupplyNeeded Supp;
    public FormSupplyNeededEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplyNeededEdit_Load(Object sender, EventArgs e) throws Exception {
        textDate.Text = Supp.DateAdded.ToShortDateString();
        textDescription.Text = Supp.Description;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (Supp.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
         
        //if(!MsgBox.){
        //}
        SupplyNeededs.deleteObject(Supp);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        Supp.DateAdded = PIn.Date(textDate.Text);
        Supp.Description = textDescription.Text;
        if (Supp.getIsNew())
        {
            SupplyNeededs.insert(Supp);
        }
        else
        {
            SupplyNeededs.update(Supp);
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
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 81);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(132, 18);
        this.label1.TabIndex = 4;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(135, 80);
        this.textDescription.Multiline = true;
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(401, 47);
        this.textDescription.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(132, 18);
        this.label2.TabIndex = 8;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(135, 39);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 1;
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
        this.butDelete.Location = new System.Drawing.Point(27, 240);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(528, 199);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
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
        this.butCancel.Location = new System.Drawing.Point(528, 240);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(114, 240);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(217, 33);
        this.label3.TabIndex = 9;
        this.label3.Text = "Once the supply is added to an order, delete it from this list.";
        //
        // FormSupplyNeededEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(628, 291);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSupplyNeededEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supply Needed";
        this.Load += new System.EventHandler(this.FormSupplyNeededEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


