//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Supplier;
import OpenDentBusiness.Suppliers;
import OpenDentBusiness.Supplies;
import OpenDentBusiness.Supply;
import OpenDental.Properties.Resources;

public class FormSupplyEdit  extends Form 
{

    public Supply Supp;
    public List<Supplier> ListSupplier = new List<Supplier>();
    private boolean isHiddenInitialVal = new boolean();
    private long categoryInitialVal = new long();
    private Supply SuppOriginal;
    public FormSupplyEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplyEdit_Load(Object sender, EventArgs e) throws Exception {
        textSupplier.Text = Suppliers.getName(ListSupplier,Supp.SupplierNum);
        SuppOriginal = Supp.copy();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.SupplyCats).ordinal()].Length;i++)
        {
            comboCategory.Items.Add(DefC.getShort()[((Enum)DefCat.SupplyCats).ordinal()][i].ItemName);
            if (Supp.Category == DefC.getShort()[((Enum)DefCat.SupplyCats).ordinal()][i].DefNum)
            {
                comboCategory.SelectedIndex = i;
            }
             
        }
        if (comboCategory.SelectedIndex == -1)
        {
            comboCategory.SelectedIndex = 0;
        }
         
        //There are no hidden cats, and presence of cats is checked before allowing user to add new.
        categoryInitialVal = Supp.Category;
        textCatalogNumber.Text = Supp.CatalogNumber;
        textDescript.Text = Supp.Descript;
        if (Supp.LevelDesired != 0)
        {
            textLevelDesired.Text = Supp.LevelDesired.ToString();
        }
         
        if (Supp.Price != 0)
        {
            textPrice.Text = Supp.Price.ToString("n");
        }
         
        checkIsHidden.Checked = Supp.IsHidden;
        isHiddenInitialVal = Supp.IsHidden;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (Supp.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        try
        {
            Supplies.deleteObject(Supp);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        Supp = null;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textLevelDesired.errorProvider1.GetError(textLevelDesired), "") || !StringSupport.equals(textPrice.errorProvider1.GetError(textPrice), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDescript.Text, ""))
        {
            MsgBox.show(this,"Please enter a description.");
            return ;
        }
         
        Supp.Category = DefC.getShort()[((Enum)DefCat.SupplyCats).ordinal()][comboCategory.SelectedIndex].DefNum;
        Supp.CatalogNumber = textCatalogNumber.Text;
        Supp.Descript = textDescript.Text;
        Supp.LevelDesired = PIn.Float(textLevelDesired.Text);
        Supp.Price = PIn.Double(textPrice.Text);
        Supp.IsHidden = checkIsHidden.Checked;
        if (Supp.Category != categoryInitialVal)
        {
            Supp.ItemOrder = int.MaxValue;
        }
         
        //changed categories, new or existing, move to bottom of new category.
        if (Supp.getIsNew())
        {
            Supp = Supplies.getSupply(Supplies.insert(Supp,Supp.ItemOrder));
        }
        else
        {
            //insert Supp and update with PK and item order from DB.
            Supplies.update(SuppOriginal,Supp);
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
        this.textSupplier = new System.Windows.Forms.TextBox();
        this.textCatalogNumber = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textDescript = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.checkIsHidden = new System.Windows.Forms.CheckBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.comboCategory = new System.Windows.Forms.ComboBox();
        this.textLevelDesired = new OpenDental.ValidDouble();
        this.textPrice = new OpenDental.ValidDouble();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(31, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(132, 18);
        this.label1.TabIndex = 4;
        this.label1.Text = "Supplier";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSupplier
        //
        this.textSupplier.Location = new System.Drawing.Point(166, 8);
        this.textSupplier.Name = "textSupplier";
        this.textSupplier.ReadOnly = true;
        this.textSupplier.Size = new System.Drawing.Size(295, 20);
        this.textSupplier.TabIndex = 10;
        //
        // textCatalogNumber
        //
        this.textCatalogNumber.Location = new System.Drawing.Point(166, 61);
        this.textCatalogNumber.Name = "textCatalogNumber";
        this.textCatalogNumber.Size = new System.Drawing.Size(144, 20);
        this.textCatalogNumber.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(7, 62);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(156, 18);
        this.label2.TabIndex = 8;
        this.label2.Text = "Catalog Item Number";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescript
        //
        this.textDescript.Location = new System.Drawing.Point(166, 87);
        this.textDescript.MaxLength = 255;
        this.textDescript.Name = "textDescript";
        this.textDescript.Size = new System.Drawing.Size(401, 20);
        this.textDescript.TabIndex = 1;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(6, 88);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(157, 18);
        this.label3.TabIndex = 10;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(31, 35);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(132, 18);
        this.label5.TabIndex = 14;
        this.label5.Text = "Category";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(32, 113);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(132, 18);
        this.label6.TabIndex = 16;
        this.label6.Text = "Level Desired";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsHidden
        //
        this.checkIsHidden.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.Location = new System.Drawing.Point(76, 164);
        this.checkIsHidden.Name = "checkIsHidden";
        this.checkIsHidden.Size = new System.Drawing.Size(104, 18);
        this.checkIsHidden.TabIndex = 7;
        this.checkIsHidden.Text = "Hidden";
        this.checkIsHidden.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.UseVisualStyleBackColor = true;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(32, 139);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(132, 18);
        this.label8.TabIndex = 20;
        this.label8.Text = "Price";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(228, 116);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(173, 19);
        this.label12.TabIndex = 24;
        this.label12.Text = "Decimals allowed.";
        //
        // comboCategory
        //
        this.comboCategory.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCategory.FormattingEnabled = true;
        this.comboCategory.Location = new System.Drawing.Point(166, 34);
        this.comboCategory.Name = "comboCategory";
        this.comboCategory.Size = new System.Drawing.Size(228, 21);
        this.comboCategory.TabIndex = 11;
        //
        // textLevelDesired
        //
        this.textLevelDesired.BackColor = System.Drawing.SystemColors.Window;
        this.textLevelDesired.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textLevelDesired.Location = new System.Drawing.Point(166, 113);
        this.textLevelDesired.Name = "textLevelDesired";
        this.textLevelDesired.Size = new System.Drawing.Size(62, 20);
        this.textLevelDesired.TabIndex = 4;
        //
        // textPrice
        //
        this.textPrice.BackColor = System.Drawing.SystemColors.Window;
        this.textPrice.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textPrice.Location = new System.Drawing.Point(166, 139);
        this.textPrice.Name = "textPrice";
        this.textPrice.Size = new System.Drawing.Size(80, 20);
        this.textPrice.TabIndex = 5;
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
        this.butDelete.Location = new System.Drawing.Point(27, 214);
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
        this.butOK.Location = new System.Drawing.Point(499, 173);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 8;
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
        this.butCancel.Location = new System.Drawing.Point(499, 214);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSupplyEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(599, 265);
        this.Controls.Add(this.textLevelDesired);
        this.Controls.Add(this.comboCategory);
        this.Controls.Add(this.textPrice);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.checkIsHidden);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textCatalogNumber);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textSupplier);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSupplyEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supply";
        this.Load += new System.EventHandler(this.FormSupplyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSupplier = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textCatalogNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescript = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsHidden = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private OpenDental.ValidDouble textPrice;
    private System.Windows.Forms.ComboBox comboCategory = new System.Windows.Forms.ComboBox();
    private OpenDental.ValidDouble textLevelDesired;
}


