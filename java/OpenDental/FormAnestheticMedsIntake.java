//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAnesthMedSuppliers;
import OpenDental.Lan;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDental.FormAnestheticMedsIntake;
import OpenDental.Properties.Resources;

public class FormAnestheticMedsIntake  extends Form 
{

    public boolean IsNew = new boolean();
    public AnesthMedsInventory Med = new AnesthMedsInventory();
    public double qtyOnHand = new double();
    public FormAnestheticMedsIntake() throws Exception {
        initializeComponent();
        //Binds date to the textDate textbox.
        textDate.Text = MiscData.getNowDateTime().ToString("yyyy-MM-dd");
        //Binds the combobox comboBoxAnesthMed with Medication names from the database.
        this.comboAnesthMedName.Items.Clear();
        this.comboAnesthMedName.Items.Insert(0, "");
        int noOfRows = AnestheticQueries.bindAMedName().Tables[0].Rows.Count;
        for (int i = 0;i <= noOfRows - 1;i++)
        {
            this.comboAnesthMedName.Items.Add(AnestheticQueries.bindAMedName().Tables[0].Rows[i][0].ToString());
            this.comboAnesthMedName.SelectedIndex = 0;
        }
        //Binds the combobox comboBoxSupplier with Medication names from the database.
        this.comboSupplierName.Items.Clear();
        this.comboSupplierName.Items.Insert(0, "");
        int noOfRows2 = AnestheticQueries.bindSuppliers().Tables[0].Rows.Count;
        for (int i = 0;i <= noOfRows2 - 1;i++)
        {
            this.comboSupplierName.Items.Add(AnestheticQueries.bindSuppliers().Tables[0].Rows[i][0].ToString());
            this.comboSupplierName.SelectedIndex = 0;
        }
        Lan.F(this);
    }

    private void formAnestheticMedsIntake_Load(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AnesthesiaIntakeMeds))
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
    }

    private void textDate_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textAnesthMed_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butAddSupplier_Click(Object sender, EventArgs e) throws Exception {
        FormAnesthMedSuppliers FormMS = new FormAnesthMedSuppliers();
        FormMS.ShowDialog();
        //re-binds the Supplier name list to the Combo box in case a supplier is added while adding a new med
        if (FormMS.DialogResult == DialogResult.OK)
        {
            this.comboSupplierName.Items.Clear();
            this.comboSupplierName.Items.Insert(0, "");
            int noOfRows2 = AnestheticQueries.bindSuppliers().Tables[0].Rows.Count;
            for (int i = 0;i <= noOfRows2 - 1;i++)
            {
                this.comboSupplierName.Items.Add(AnestheticQueries.bindSuppliers().Tables[0].Rows[i][0].ToString());
                this.comboSupplierName.SelectedIndex = 0;
            }
            Lan.F(this);
        }
         
    }

    private void comboAnesthMed_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        if (comboAnesthMedName.SelectedIndex == -1 || StringSupport.equals(textQty.Text, "") || comboSupplierName.SelectedIndex == -1 || StringSupport.equals(textInvoiceNum.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"All fields are mandatory."));
            return ;
        }
        else
        {
            Regex regex = new Regex("^\\d{1,6}?$");
            if (!(regex.IsMatch(textQty.Text)) && !StringSupport.equals(textQty.Text, ""))
            {
                MessageBox.Show(Lan.g(this,"The Quantity field should be a 1-6 digit integer."));
                textQty.Focus();
                return ;
            }
            else
            {
                if (comboAnesthMedName.SelectedIndex != 0 && comboSupplierName.SelectedIndex != 0)
                {
                    if (StringSupport.equals(textInvoiceNum.Text.Trim(), ""))
                    {
                        MessageBox.Show(Lan.g(this,"Invoice # does not accept spaces."));
                        textInvoiceNum.Focus();
                    }
                     
                }
                 
            } 
            //the current QtyOnHand of a scheduled anesthetic medication
            double qtyOnHand = Convert.ToDouble(AnesthMeds.GetQtyOnHand(comboAnesthMedName.SelectedItem.ToString()));
            //records transaction into tableanesthmedsintake which tracks intake of scheduled anesthetic medications into inventory
            int supplierIDNum = AnesthMedSuppliers.GetSupplierIDNum(comboSupplierName.SelectedIndex);
            AnesthMeds.InsertMed_Intake(comboAnesthMedName.SelectedItem.ToString(), Convert.ToInt32(textQty.Text), supplierIDNum.ToString(), textInvoiceNum.Text);
            //updates QtyOnHand in tableanesthmedsinventory when a new order of scheduled anesthetic medications is received into inventory
            AnesthMeds.UpdateAMedInvAdj(comboAnesthMedName.SelectedItem.ToString(), Convert.ToDouble(textQty.Text), qtyOnHand);
            DialogResult = DialogResult.OK;
            Close();
        } 
    }

    private void textQty_TextChanged(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnestheticMedsIntake.class);
        this.textQty = new System.Windows.Forms.TextBox();
        this.labelQty = new System.Windows.Forms.Label();
        this.groupAnesthMed = new System.Windows.Forms.GroupBox();
        this.comboAnesthMedName = new System.Windows.Forms.ComboBox();
        this.textInvoiceNum = new System.Windows.Forms.TextBox();
        this.groupSupplier = new System.Windows.Forms.GroupBox();
        this.butAddSupplier = new OpenDental.UI.Button();
        this.comboSupplierName = new System.Windows.Forms.ComboBox();
        this.butClose = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.labelInvoice = new System.Windows.Forms.Label();
        this.textDate = new System.Windows.Forms.TextBox();
        this.labelDate = new System.Windows.Forms.Label();
        this.groupAnesthMed.SuspendLayout();
        this.groupSupplier.SuspendLayout();
        this.SuspendLayout();
        //
        // textQty
        //
        this.textQty.Location = new System.Drawing.Point(251, 19);
        this.textQty.Name = "textQty";
        this.textQty.Size = new System.Drawing.Size(116, 20);
        this.textQty.TabIndex = 2;
        this.textQty.TextChanged += new System.EventHandler(this.textQty_TextChanged);
        //
        // labelQty
        //
        this.labelQty.AutoSize = true;
        this.labelQty.Location = new System.Drawing.Point(246, 0);
        this.labelQty.Name = "labelQty";
        this.labelQty.Size = new System.Drawing.Size(173, 13);
        this.labelQty.TabIndex = 4;
        this.labelQty.Text = "Quantity (units must be in total mLs)";
        //
        // groupAnesthMed
        //
        this.groupAnesthMed.Controls.Add(this.comboAnesthMedName);
        this.groupAnesthMed.Controls.Add(this.textQty);
        this.groupAnesthMed.Controls.Add(this.labelQty);
        this.groupAnesthMed.Location = new System.Drawing.Point(30, 39);
        this.groupAnesthMed.Name = "groupAnesthMed";
        this.groupAnesthMed.Size = new System.Drawing.Size(482, 48);
        this.groupAnesthMed.TabIndex = 6;
        this.groupAnesthMed.TabStop = false;
        this.groupAnesthMed.Text = "Anesthetic Medication";
        //
        // comboAnesthMedName
        //
        this.comboAnesthMedName.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAnesthMedName.FormattingEnabled = true;
        this.comboAnesthMedName.Location = new System.Drawing.Point(10, 19);
        this.comboAnesthMedName.Name = "comboAnesthMedName";
        this.comboAnesthMedName.Size = new System.Drawing.Size(231, 21);
        this.comboAnesthMedName.TabIndex = 0;
        this.comboAnesthMedName.SelectedIndexChanged += new System.EventHandler(this.comboAnesthMed_SelectedIndexChanged);
        //
        // textInvoiceNum
        //
        this.textInvoiceNum.Location = new System.Drawing.Point(280, 110);
        this.textInvoiceNum.Name = "textInvoiceNum";
        this.textInvoiceNum.Size = new System.Drawing.Size(216, 20);
        this.textInvoiceNum.TabIndex = 7;
        //
        // groupSupplier
        //
        this.groupSupplier.Controls.Add(this.butAddSupplier);
        this.groupSupplier.Controls.Add(this.comboSupplierName);
        this.groupSupplier.Controls.Add(this.butClose);
        this.groupSupplier.Controls.Add(this.butCancel);
        this.groupSupplier.Controls.Add(this.labelInvoice);
        this.groupSupplier.Location = new System.Drawing.Point(30, 93);
        this.groupSupplier.Name = "groupSupplier";
        this.groupSupplier.Size = new System.Drawing.Size(482, 95);
        this.groupSupplier.TabIndex = 8;
        this.groupSupplier.TabStop = false;
        this.groupSupplier.Text = "Supplier";
        //
        // butAddSupplier
        //
        this.butAddSupplier.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddSupplier.setAutosize(true);
        this.butAddSupplier.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSupplier.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSupplier.setCornerRadius(4F);
        this.butAddSupplier.Image = Resources.getAdd();
        this.butAddSupplier.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSupplier.Location = new System.Drawing.Point(43, 53);
        this.butAddSupplier.Name = "butAddSupplier";
        this.butAddSupplier.Size = new System.Drawing.Size(163, 26);
        this.butAddSupplier.TabIndex = 139;
        this.butAddSupplier.Text = "Add new supplier...";
        this.butAddSupplier.UseVisualStyleBackColor = true;
        this.butAddSupplier.Click += new System.EventHandler(this.butAddSupplier_Click);
        //
        // comboSupplierName
        //
        this.comboSupplierName.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSupplierName.FormattingEnabled = true;
        this.comboSupplierName.Location = new System.Drawing.Point(10, 15);
        this.comboSupplierName.Name = "comboSupplierName";
        this.comboSupplierName.Size = new System.Drawing.Size(231, 21);
        this.comboSupplierName.TabIndex = 138;
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClose.Location = new System.Drawing.Point(373, 53);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(90, 26);
        this.butClose.TabIndex = 137;
        this.butClose.Text = "Save and Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(301, 53);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(66, 26);
        this.butCancel.TabIndex = 54;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // labelInvoice
        //
        this.labelInvoice.AutoSize = true;
        this.labelInvoice.Location = new System.Drawing.Point(247, 1);
        this.labelInvoice.Name = "labelInvoice";
        this.labelInvoice.Size = new System.Drawing.Size(52, 13);
        this.labelInvoice.TabIndex = 9;
        this.labelInvoice.Text = "Invoice #";
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(73, 12);
        this.textDate.Name = "textDate";
        this.textDate.ReadOnly = true;
        this.textDate.Size = new System.Drawing.Size(115, 20);
        this.textDate.TabIndex = 9;
        //
        // labelDate
        //
        this.labelDate.AutoSize = true;
        this.labelDate.Location = new System.Drawing.Point(37, 15);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(30, 13);
        this.labelDate.TabIndex = 10;
        this.labelDate.Text = "Date";
        //
        // FormAnestheticMedsIntake
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(532, 209);
        this.Controls.Add(this.labelDate);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.textInvoiceNum);
        this.Controls.Add(this.groupAnesthMed);
        this.Controls.Add(this.groupSupplier);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnestheticMedsIntake";
        this.Text = "Anesthetic Medication Intake Form";
        this.Load += new System.EventHandler(this.FormAnestheticMedsIntake_Load);
        this.groupAnesthMed.ResumeLayout(false);
        this.groupAnesthMed.PerformLayout();
        this.groupSupplier.ResumeLayout(false);
        this.groupSupplier.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textQty = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelQty = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupAnesthMed = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textInvoiceNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupSupplier = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelInvoice = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboAnesthMedName = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboSupplierName = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butAddSupplier;
}


