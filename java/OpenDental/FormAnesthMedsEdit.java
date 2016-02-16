//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import OpenDental.Properties.Resources;

public class FormAnesthMedsEdit  extends Form 
{

    public AnesthMedsInventory Med = new AnesthMedsInventory();
    public List<AnesthMedInvAdjC> ListAnesthMedsInvAdj = new List<AnesthMedInvAdjC>();
    public AnesthMedsInventoryAdj Adj = new AnesthMedsInventoryAdj();
    private AnesthMedsInventoryAdj AdjustNumCur = new AnesthMedsInventoryAdj();
    public FormAnesthMedsEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAnesthMedsEdit_Load(Object sender, EventArgs e) throws Exception {
        //prevents user from changing med names when adjusting quantities
        if (Med.IsNew == false)
        {
            textAnesthMedName.Enabled = false;
            textAnesthHowSupplied.Enabled = false;
        }
        else
        {
            textAnesthMedName.Enabled = true;
            textAnesthHowSupplied.Enabled = true;
        } 
        textAnesthMedName.Text = Med.AnesthMedName;
        textAnesthHowSupplied.Text = Med.AnesthHowSupplied;
        comboDEASchedule.Text = Med.DEASchedule;
        textQtyOnHand.Text = Med.QtyOnHand;
        //prevents editing of QtyOnHand from this form
        if (!StringSupport.equals(Med.QtyOnHand, "0"))
        {
            textQtyOnHand.Enabled = false;
        }
         
        if (Med.IsNew == true)
        {
            textQtyAdj.Enabled = false;
            textNotes.Enabled = false;
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (Med.IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        AnesthMeds.DeleteObject(Med);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        AdjustNumCur = new AnesthMedsInventoryAdj();
        if (StringSupport.equals(textAnesthMedName.Text, ""))
        {
            MsgBox.show(this,"Please enter a name.");
            return ;
        }
         
        //write inventory data to anesthetic meds inventory db
        Med.AnesthMedName = textAnesthMedName.Text;
        Med.AnesthHowSupplied = textAnesthHowSupplied.Text;
        Med.DEASchedule = comboDEASchedule.Text;
        if (Med.IsNew != true)
        {
            Med.QtyOnHand = textQtyOnHand.Text;
        }
         
        if (Med.QtyOnHand == null)
        {
            Med.QtyOnHand = "0";
        }
         
        if (Med.DEASchedule == null)
        {
            Med.DEASchedule = "";
        }
         
        //write adjustment data to anesthetic meds inventory adjustment db
        Userod curUser = Security.getCurUser();
        AdjustNumCur.AnestheticMedNum = Convert.ToInt32(Med.AnestheticMedNum);
        if (!StringSupport.equals(textQtyAdj.Text, ""))
        {
            Regex regex = new Regex("^-\\d{1,6}?$|^\\d{1,6}?$");
            if (!(regex.IsMatch(textQtyAdj.Text)) && !StringSupport.equals(textQtyAdj.Text, ""))
            {
                MessageBox.Show("The Quantity field should be a 1-6 digit integer.");
                textQtyAdj.Focus();
                return ;
            }
            else
                AdjustNumCur.QtyAdj = Convert.ToDouble(textQtyAdj.Text); 
        }
         
        AdjustNumCur.UserNum = Convert.ToInt32(curUser.UserNum);
        if (!StringSupport.equals(textNotes.Text, ""))
        {
            AdjustNumCur.Notes = textNotes.Text;
        }
         
        AdjustNumCur.TimeStamp = DateTime.Now;
        AnesthMedInvAdjs.Insert(AdjustNumCur);
        //write inventory adjustment back to table anesthmedsinventory
        double newQty = Convert.ToDouble(Med.QtyOnHand) + Convert.ToDouble(AdjustNumCur.QtyAdj);
        Med.QtyOnHand = newQty.ToString();
        AnesthMeds.WriteObject(Med);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void textAnesthMedName_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textQtyOnHand_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void groupAnesthMedsEdit_Enter(Object sender, EventArgs e) throws Exception {
    }

    private void textQtyAdj_TextChanged(Object sender, EventArgs e) throws Exception {
        //prevents user from using this form to enter initial quantities. This should be done on FormAnestheticMedsIntake
        if (AnesthMeds.GetQtyOnHand(textAnesthMedName.Text) == 0)
        {
            MessageBox.Show(this, "Please use the 'Intake new meds' button on the previous form \rto add initial inventory quantities");
            butOK.Enabled = false;
        }
         
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
        this.textAnesthMedName = new System.Windows.Forms.TextBox();
        this.textAnesthHowSupplied = new System.Windows.Forms.TextBox();
        this.groupAnesthMedsEdit = new System.Windows.Forms.GroupBox();
        this.labelNotes = new System.Windows.Forms.Label();
        this.textNotes = new System.Windows.Forms.TextBox();
        this.labelQtyAdj = new System.Windows.Forms.Label();
        this.textQtyAdj = new System.Windows.Forms.TextBox();
        this.labelQtyOnHand = new System.Windows.Forms.Label();
        this.textQtyOnHand = new System.Windows.Forms.TextBox();
        this.labelDEASched = new System.Windows.Forms.Label();
        this.comboDEASchedule = new System.Windows.Forms.ComboBox();
        this.labelHowSupplied = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.labelHowSuppl = new System.Windows.Forms.Label();
        this.labelUnitDose = new System.Windows.Forms.Label();
        this.labelChanges = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupAnesthMedsEdit.SuspendLayout();
        this.SuspendLayout();
        //
        // textAnesthMedName
        //
        this.textAnesthMedName.Location = new System.Drawing.Point(36, 48);
        this.textAnesthMedName.Name = "textAnesthMedName";
        this.textAnesthMedName.Size = new System.Drawing.Size(213, 20);
        this.textAnesthMedName.TabIndex = 1;
        this.textAnesthMedName.TextChanged += new System.EventHandler(this.textAnesthMedName_TextChanged);
        //
        // textAnesthHowSupplied
        //
        this.textAnesthHowSupplied.Location = new System.Drawing.Point(36, 92);
        this.textAnesthHowSupplied.Name = "textAnesthHowSupplied";
        this.textAnesthHowSupplied.Size = new System.Drawing.Size(213, 20);
        this.textAnesthHowSupplied.TabIndex = 5;
        //
        // groupAnesthMedsEdit
        //
        this.groupAnesthMedsEdit.Controls.Add(this.labelNotes);
        this.groupAnesthMedsEdit.Controls.Add(this.textNotes);
        this.groupAnesthMedsEdit.Controls.Add(this.labelQtyAdj);
        this.groupAnesthMedsEdit.Controls.Add(this.textQtyAdj);
        this.groupAnesthMedsEdit.Controls.Add(this.labelQtyOnHand);
        this.groupAnesthMedsEdit.Controls.Add(this.textQtyOnHand);
        this.groupAnesthMedsEdit.Controls.Add(this.labelDEASched);
        this.groupAnesthMedsEdit.Controls.Add(this.comboDEASchedule);
        this.groupAnesthMedsEdit.Controls.Add(this.textAnesthMedName);
        this.groupAnesthMedsEdit.Controls.Add(this.labelHowSupplied);
        this.groupAnesthMedsEdit.Controls.Add(this.textAnesthHowSupplied);
        this.groupAnesthMedsEdit.Controls.Add(this.label1);
        this.groupAnesthMedsEdit.Controls.Add(this.labelHowSuppl);
        this.groupAnesthMedsEdit.Controls.Add(this.labelUnitDose);
        this.groupAnesthMedsEdit.Location = new System.Drawing.Point(27, 23);
        this.groupAnesthMedsEdit.Name = "groupAnesthMedsEdit";
        this.groupAnesthMedsEdit.Size = new System.Drawing.Size(522, 213);
        this.groupAnesthMedsEdit.TabIndex = 11;
        this.groupAnesthMedsEdit.TabStop = false;
        this.groupAnesthMedsEdit.Text = "Add or Edit Anesthetic Medication(s)";
        this.groupAnesthMedsEdit.Enter += new System.EventHandler(this.groupAnesthMedsEdit_Enter);
        //
        // labelNotes
        //
        this.labelNotes.AutoSize = true;
        this.labelNotes.Location = new System.Drawing.Point(257, 137);
        this.labelNotes.Name = "labelNotes";
        this.labelNotes.Size = new System.Drawing.Size(35, 13);
        this.labelNotes.TabIndex = 15;
        this.labelNotes.Text = "Notes";
        //
        // textNotes
        //
        this.textNotes.Location = new System.Drawing.Point(257, 156);
        this.textNotes.Multiline = true;
        this.textNotes.Name = "textNotes";
        this.textNotes.Size = new System.Drawing.Size(246, 42);
        this.textNotes.TabIndex = 14;
        //
        // labelQtyAdj
        //
        this.labelQtyAdj.AutoSize = true;
        this.labelQtyAdj.Location = new System.Drawing.Point(17, 181);
        this.labelQtyAdj.Name = "labelQtyAdj";
        this.labelQtyAdj.Size = new System.Drawing.Size(95, 13);
        this.labelQtyAdj.TabIndex = 13;
        this.labelQtyAdj.Text = "Qty Adjustment +/-";
        //
        // textQtyAdj
        //
        this.textQtyAdj.Location = new System.Drawing.Point(118, 178);
        this.textQtyAdj.Name = "textQtyAdj";
        this.textQtyAdj.Size = new System.Drawing.Size(100, 20);
        this.textQtyAdj.TabIndex = 12;
        this.textQtyAdj.TextChanged += new System.EventHandler(this.textQtyAdj_TextChanged);
        //
        // labelQtyOnHand
        //
        this.labelQtyOnHand.AutoSize = true;
        this.labelQtyOnHand.Location = new System.Drawing.Point(45, 154);
        this.labelQtyOnHand.Name = "labelQtyOnHand";
        this.labelQtyOnHand.Size = new System.Drawing.Size(67, 13);
        this.labelQtyOnHand.TabIndex = 11;
        this.labelQtyOnHand.Text = "Qty on Hand";
        //
        // textQtyOnHand
        //
        this.textQtyOnHand.Location = new System.Drawing.Point(118, 151);
        this.textQtyOnHand.Name = "textQtyOnHand";
        this.textQtyOnHand.ReadOnly = true;
        this.textQtyOnHand.Size = new System.Drawing.Size(100, 20);
        this.textQtyOnHand.TabIndex = 10;
        this.textQtyOnHand.TextChanged += new System.EventHandler(this.textQtyOnHand_TextChanged);
        //
        // labelDEASched
        //
        this.labelDEASched.AutoSize = true;
        this.labelDEASched.Location = new System.Drawing.Point(35, 125);
        this.labelDEASched.Name = "labelDEASched";
        this.labelDEASched.Size = new System.Drawing.Size(77, 13);
        this.labelDEASched.TabIndex = 9;
        this.labelDEASched.Text = "DEA Schedule";
        //
        // comboDEASchedule
        //
        this.comboDEASchedule.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDEASchedule.FormattingEnabled = true;
        this.comboDEASchedule.Items.AddRange(new Object[]{ "II", "III", "IV", "V" });
        this.comboDEASchedule.Location = new System.Drawing.Point(118, 122);
        this.comboDEASchedule.Name = "comboDEASchedule";
        this.comboDEASchedule.Size = new System.Drawing.Size(43, 21);
        this.comboDEASchedule.TabIndex = 8;
        //
        // labelHowSupplied
        //
        this.labelHowSupplied.AutoSize = true;
        this.labelHowSupplied.Location = new System.Drawing.Point(33, 75);
        this.labelHowSupplied.Name = "labelHowSupplied";
        this.labelHowSupplied.Size = new System.Drawing.Size(71, 13);
        this.labelHowSupplied.TabIndex = 4;
        this.labelHowSupplied.Text = "How supplied";
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(33, 29);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(178, 13);
        this.label1.TabIndex = 0;
        this.label1.Text = "Anesthetic medication and unit dose";
        //
        // labelHowSuppl
        //
        this.labelHowSuppl.AutoSize = true;
        this.labelHowSuppl.Location = new System.Drawing.Point(263, 95);
        this.labelHowSuppl.Name = "labelHowSuppl";
        this.labelHowSuppl.Size = new System.Drawing.Size(240, 13);
        this.labelHowSuppl.TabIndex = 6;
        this.labelHowSuppl.Text = "(e.g. 2 mL ampules, 10 mL Multi Dose Vial (MDV))";
        //
        // labelUnitDose
        //
        this.labelUnitDose.AutoSize = true;
        this.labelUnitDose.Location = new System.Drawing.Point(263, 51);
        this.labelUnitDose.Name = "labelUnitDose";
        this.labelUnitDose.Size = new System.Drawing.Size(215, 13);
        this.labelUnitDose.TabIndex = 3;
        this.labelUnitDose.Text = "(e.g. Fentanyl 50 mcg/mL, Versed 5 mg/mL)";
        //
        // labelChanges
        //
        this.labelChanges.Location = new System.Drawing.Point(41, 252);
        this.labelChanges.Name = "labelChanges";
        this.labelChanges.Size = new System.Drawing.Size(508, 33);
        this.labelChanges.TabIndex = 7;
        this.labelChanges.Text = "NOTE: Once a medication has been added, spelling changes can be made here, but th" + "e name and type of medication should not be changed or the inventory system will" + " be adversely affected...";
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
        this.butDelete.Location = new System.Drawing.Point(30, 300);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 10;
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
        this.butOK.Location = new System.Drawing.Point(460, 300);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(92, 26);
        this.butOK.TabIndex = 9;
        this.butOK.Text = "Save and  Close";
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
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(370, 300);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(83, 26);
        this.butCancel.TabIndex = 8;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAnesthMedsEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(575, 339);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.groupAnesthMedsEdit);
        this.Controls.Add(this.labelChanges);
        this.Name = "FormAnesthMedsEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Add/Edit Anesthetic Med(s)";
        this.Load += new System.EventHandler(this.FormAnesthMedsEdit_Load);
        this.groupAnesthMedsEdit.ResumeLayout(false);
        this.groupAnesthMedsEdit.PerformLayout();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.TextBox textAnesthMedName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAnesthHowSupplied = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.GroupBox groupAnesthMedsEdit = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelHowSupplied = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelHowSuppl = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelUnitDose = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelChanges = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDEASched = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboDEASchedule = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelQtyOnHand = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textQtyOnHand = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelQtyAdj = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textQtyAdj = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelNotes = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNotes = new System.Windows.Forms.TextBox();
}


