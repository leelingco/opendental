//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Supplier;
import OpenDentBusiness.Suppliers;
import OpenDentBusiness.SupplyOrder;
import OpenDentBusiness.SupplyOrders;
import OpenDental.Properties.Resources;

public class FormSupplyOrderEdit  extends Form 
{

    public SupplyOrder Order;
    public List<Supplier> ListSupplier = new List<Supplier>();
    /**
    * This form is only going to be used to edit existing supplyOrders, not to add new ones.
    */
    public FormSupplyOrderEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplyOrderEdit_Load(Object sender, EventArgs e) throws Exception {
        textSupplier.Text = Suppliers.getName(ListSupplier,Order.SupplierNum);
        if (Order.DatePlaced.Year > 2200)
        {
            textDatePlaced.Text = "";
        }
        else
        {
            textDatePlaced.Text = Order.DatePlaced.ToShortDateString();
        } 
        textAmountTotal.Text = Order.AmountTotal.ToString("n");
        textNote.Text = Order.Note;
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDatePlaced.Text = DateTime.Today.ToShortDateString();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //if(Order.IsNew){//never
        //	DialogResult=DialogResult.Cancel;
        //}
        if (!StringSupport.equals(textDatePlaced.Text, ""))
        {
            MsgBox.show(this,"Not allowed to delete unless date is blank.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete entire order?"))
        {
            return ;
        }
         
        SupplyOrders.deleteObject(Order);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDatePlaced.errorProvider1.GetError(textDatePlaced), "") || !StringSupport.equals(textAmountTotal.errorProvider1.GetError(textAmountTotal), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDatePlaced.Text, ""))
        {
            Order.DatePlaced = new DateTime(2500, 1, 1);
        }
        else
        {
            Order.DatePlaced = PIn.Date(textDatePlaced.Text);
        } 
        Order.AmountTotal = PIn.Double(textAmountTotal.Text);
        Order.Note = textNote.Text;
        SupplyOrders.update(Order);
        //never new
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
        this.textNote = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butToday = new OpenDental.UI.Button();
        this.textDatePlaced = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textAmountTotal = new OpenDental.ValidDouble();
        this.label8 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
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
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(166, 86);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(401, 60);
        this.textNote.TabIndex = 6;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(33, 85);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(132, 18);
        this.label7.TabIndex = 18;
        this.label7.Text = "Note";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(31, 34);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(132, 18);
        this.label2.TabIndex = 20;
        this.label2.Text = "Date Placed";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(351, 36);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(376, 18);
        this.label3.TabIndex = 21;
        this.label3.Text = "Leave date blank to indicate order not placed yet and is pending";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(270, 31);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(75, 24);
        this.butToday.TabIndex = 22;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // textDatePlaced
        //
        this.textDatePlaced.Location = new System.Drawing.Point(166, 34);
        this.textDatePlaced.Name = "textDatePlaced";
        this.textDatePlaced.Size = new System.Drawing.Size(100, 20);
        this.textDatePlaced.TabIndex = 19;
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
        this.butDelete.Location = new System.Drawing.Point(27, 207);
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
        this.butOK.Location = new System.Drawing.Point(639, 166);
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
        this.butCancel.Location = new System.Drawing.Point(639, 207);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textAmountTotal
        //
        this.textAmountTotal.BackColor = System.Drawing.SystemColors.Window;
        this.textAmountTotal.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textAmountTotal.Location = new System.Drawing.Point(166, 60);
        this.textAmountTotal.Name = "textAmountTotal";
        this.textAmountTotal.Size = new System.Drawing.Size(80, 20);
        this.textAmountTotal.TabIndex = 23;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(32, 60);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(132, 18);
        this.label8.TabIndex = 24;
        this.label8.Text = "Total Amount";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(247, 60);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(319, 18);
        this.label4.TabIndex = 25;
        this.label4.Text = "Auto calculates unless some items have zero amount.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormSupplyOrderEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(739, 258);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textAmountTotal);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.butToday);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDatePlaced);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textSupplier);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSupplyOrderEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supply Order";
        this.Load += new System.EventHandler(this.FormSupplyOrderEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSupplier = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDatePlaced;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butToday;
    private OpenDental.ValidDouble textAmountTotal;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
}


