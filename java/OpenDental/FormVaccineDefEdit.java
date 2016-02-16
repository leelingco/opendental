//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCvxs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DrugManufacturers;
import OpenDentBusiness.VaccineDef;
import OpenDentBusiness.VaccineDefs;
import OpenDental.FormVaccineDefEdit;
import OpenDental.Properties.Resources;

public class FormVaccineDefEdit  extends Form 
{

    public VaccineDef VaccineDefCur;
    public boolean IsNew = new boolean();
    public FormVaccineDefEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formVaccineDefEdit_Load(Object sender, EventArgs e) throws Exception {
        textCVXCode.Text = VaccineDefCur.CVXCode;
        textVaccineName.Text = VaccineDefCur.VaccineName;
        for (int i = 0;i < DrugManufacturers.getListt().Count;i++)
        {
            comboManufacturer.Items.Add(DrugManufacturers.getListt()[i].ManufacturerCode + " - " + DrugManufacturers.getListt()[i].ManufacturerName);
            if (DrugManufacturers.getListt()[i].DrugManufacturerNum == VaccineDefCur.DrugManufacturerNum)
            {
                comboManufacturer.SelectedIndex = i;
            }
             
        }
    }

    private void butCvxSelect_Click(Object sender, EventArgs e) throws Exception {
        FormCvxs FormC = new FormCvxs();
        FormC.IsSelectionMode = true;
        FormC.ShowDialog();
        if (FormC.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textCVXCode.Text = FormC.SelectedCvx.CvxCode;
        textVaccineName.Text = FormC.SelectedCvx.Description;
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
            VaccineDefs.delete(VaccineDefCur.VaccineDefNum);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textCVXCode.Text, "") || StringSupport.equals(textVaccineName.Text, ""))
        {
            MsgBox.show(this,"Blank fields are not allowed.");
            return ;
        }
         
        if (comboManufacturer.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a manufacturer.");
            return ;
        }
         
        VaccineDefCur.CVXCode = textCVXCode.Text;
        VaccineDefCur.VaccineName = textVaccineName.Text;
        VaccineDefCur.DrugManufacturerNum = DrugManufacturers.getListt()[comboManufacturer.SelectedIndex].DrugManufacturerNum;
        if (IsNew)
        {
            for (int i = 0;i < VaccineDefs.getListt().Count;i++)
            {
                //We used to block creation if the CVX code already existed.
                //For EHR immunization testing, we had to allow duplicate CVX codes, because some test cases had the same CVX codes but different manufacturers.
                if (VaccineDefs.getListt()[i].CVXCode == textCVXCode.Text && VaccineDefs.getListt()[i].DrugManufacturerNum == VaccineDefCur.DrugManufacturerNum)
                {
                    MsgBox.show(this,"CVX Code already exists for the chosen manufacturer.");
                    return ;
                }
                 
            }
            VaccineDefs.insert(VaccineDefCur);
        }
        else
        {
            VaccineDefs.update(VaccineDefCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormVaccineDefEdit.class);
        this.comboManufacturer = new System.Windows.Forms.ComboBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.textVaccineName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textCVXCode = new System.Windows.Forms.TextBox();
        this.labelCVXCode = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butCvxSelect = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // comboManufacturer
        //
        this.comboManufacturer.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboManufacturer.Location = new System.Drawing.Point(101, 83);
        this.comboManufacturer.Name = "comboManufacturer";
        this.comboManufacturer.Size = new System.Drawing.Size(173, 21);
        this.comboManufacturer.TabIndex = 3;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(202, 135);
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
        this.butCancel.Location = new System.Drawing.Point(283, 135);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(1, 83);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(98, 18);
        this.label3.TabIndex = 22;
        this.label3.Text = "Manufacturer";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textVaccineName
        //
        this.textVaccineName.Location = new System.Drawing.Point(101, 57);
        this.textVaccineName.Name = "textVaccineName";
        this.textVaccineName.Size = new System.Drawing.Size(229, 20);
        this.textVaccineName.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 56);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(88, 20);
        this.label1.TabIndex = 19;
        this.label1.Text = "Vaccine Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCVXCode
        //
        this.textCVXCode.Location = new System.Drawing.Point(101, 31);
        this.textCVXCode.Name = "textCVXCode";
        this.textCVXCode.ReadOnly = true;
        this.textCVXCode.Size = new System.Drawing.Size(229, 20);
        this.textCVXCode.TabIndex = 0;
        this.textCVXCode.TabStop = false;
        //
        // labelCVXCode
        //
        this.labelCVXCode.Location = new System.Drawing.Point(11, 30);
        this.labelCVXCode.Name = "labelCVXCode";
        this.labelCVXCode.Size = new System.Drawing.Size(88, 20);
        this.labelCVXCode.TabIndex = 17;
        this.labelCVXCode.Text = "CVX Code";
        this.labelCVXCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butDelete.Location = new System.Drawing.Point(12, 135);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCvxSelect
        //
        this.butCvxSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCvxSelect.setAutosize(true);
        this.butCvxSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCvxSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCvxSelect.setCornerRadius(4F);
        this.butCvxSelect.Location = new System.Drawing.Point(336, 30);
        this.butCvxSelect.Name = "butCvxSelect";
        this.butCvxSelect.Size = new System.Drawing.Size(22, 22);
        this.butCvxSelect.TabIndex = 1;
        this.butCvxSelect.Text = "...";
        this.butCvxSelect.Click += new System.EventHandler(this.butCvxSelect_Click);
        //
        // FormVaccineDefEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(370, 186);
        this.Controls.Add(this.butCvxSelect);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.comboManufacturer);
        this.Controls.Add(this.textVaccineName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCVXCode);
        this.Controls.Add(this.labelCVXCode);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormVaccineDefEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Vaccine Definition Edit";
        this.Load += new System.EventHandler(this.FormVaccineDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textVaccineName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCVXCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelCVXCode = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butCvxSelect;
    private System.Windows.Forms.ComboBox comboManufacturer = new System.Windows.Forms.ComboBox();
}


