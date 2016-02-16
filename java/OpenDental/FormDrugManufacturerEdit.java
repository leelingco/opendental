//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DrugManufacturer;
import OpenDentBusiness.DrugManufacturers;
import OpenDental.Properties.Resources;

public class FormDrugManufacturerEdit  extends Form 
{

    public DrugManufacturer DrugManufacturerCur;
    public boolean IsNew = new boolean();
    public FormDrugManufacturerEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formDrugManufacturerEdit_Load(Object sender, EventArgs e) throws Exception {
        textManufacturerName.Text = DrugManufacturerCur.ManufacturerName;
        textManufacturerCode.Text = DrugManufacturerCur.ManufacturerCode;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
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
            DrugManufacturers.delete(DrugManufacturerCur.DrugManufacturerNum);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textManufacturerName.Text, "") || StringSupport.equals(textManufacturerCode.Text, ""))
        {
            MsgBox.show(this,"Bank fields are not allowed.");
            return ;
        }
         
        DrugManufacturerCur.ManufacturerName = textManufacturerName.Text;
        DrugManufacturerCur.ManufacturerCode = textManufacturerCode.Text;
        if (IsNew)
        {
            for (int i = 0;i < DrugManufacturers.getListt().Count;i++)
            {
                if (DrugManufacturers.getListt()[i].ManufacturerCode == textManufacturerCode.Text)
                {
                    MsgBox.show(this,"Manufacturer with this code already exists.");
                    return ;
                }
                 
            }
            DrugManufacturers.insert(DrugManufacturerCur);
        }
        else
        {
            DrugManufacturers.update(DrugManufacturerCur);
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
        this.butDelete = new OpenDental.UI.Button();
        this.textManufacturerName = new System.Windows.Forms.TextBox();
        this.labelManufacturerName = new System.Windows.Forms.Label();
        this.textManufacturerCode = new System.Windows.Forms.TextBox();
        this.labelManufacturerCode = new System.Windows.Forms.Label();
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
        this.butOK.Location = new System.Drawing.Point(218, 115);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(299, 115);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(26, 115);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textManufacturerName
        //
        this.textManufacturerName.Location = new System.Drawing.Point(137, 28);
        this.textManufacturerName.Name = "textManufacturerName";
        this.textManufacturerName.Size = new System.Drawing.Size(237, 20);
        this.textManufacturerName.TabIndex = 0;
        //
        // labelManufacturerName
        //
        this.labelManufacturerName.Location = new System.Drawing.Point(12, 27);
        this.labelManufacturerName.Name = "labelManufacturerName";
        this.labelManufacturerName.Size = new System.Drawing.Size(119, 20);
        this.labelManufacturerName.TabIndex = 111;
        this.labelManufacturerName.Text = "Manufacturer Name";
        this.labelManufacturerName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textManufacturerCode
        //
        this.textManufacturerCode.Location = new System.Drawing.Point(137, 53);
        this.textManufacturerCode.Name = "textManufacturerCode";
        this.textManufacturerCode.Size = new System.Drawing.Size(82, 20);
        this.textManufacturerCode.TabIndex = 1;
        //
        // labelManufacturerCode
        //
        this.labelManufacturerCode.Location = new System.Drawing.Point(20, 53);
        this.labelManufacturerCode.Name = "labelManufacturerCode";
        this.labelManufacturerCode.Size = new System.Drawing.Size(111, 20);
        this.labelManufacturerCode.TabIndex = 109;
        this.labelManufacturerCode.Text = "Manufacturer Code";
        this.labelManufacturerCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormDrugManufacturerEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(399, 166);
        this.Controls.Add(this.textManufacturerName);
        this.Controls.Add(this.labelManufacturerName);
        this.Controls.Add(this.textManufacturerCode);
        this.Controls.Add(this.labelManufacturerCode);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormDrugManufacturerEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Drug Manufacturer Edit";
        this.Load += new System.EventHandler(this.FormDrugManufacturerEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textManufacturerName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelManufacturerName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textManufacturerCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelManufacturerCode = new System.Windows.Forms.Label();
}


