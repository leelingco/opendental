//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.PayorType;
import OpenDentBusiness.PayorTypes;
import OpenDentBusiness.Sops;
import OpenDental.FormPayorTypeEdit;
import OpenDental.Properties.Resources;

public class FormPayorTypeEdit  extends Form 
{

    public boolean IsNew = new boolean();
    private PayorType PayorTypeCur;
    private int _selectedIndex = new int();
    //used to keep track of the selected index in the comboSopCode drop down box since we are setting the text differently than the contents of the drop down list
    public FormPayorTypeEdit(PayorType payorType) throws Exception {
        initializeComponent();
        Lan.F(this);
        PayorTypeCur = payorType;
    }

    private void formPayorTypeEdit_Load(Object sender, EventArgs e) throws Exception {
        _selectedIndex = -1;
        for (int i = 0;i < Sops.getListt().Count;i++)
        {
            comboSopCode.Items.Add(Sops.getListt()[i].SopCode + " - " + Sops.getListt()[i].Description);
            if (StringSupport.equals(PayorTypeCur.SopCode, Sops.getListt()[i].SopCode))
            {
                comboSopCode.SelectedIndex = i;
            }
             
        }
        _selectedIndex = comboSopCode.SelectedIndex;
        textDate.Text = PayorTypeCur.DateStart.ToShortDateString();
        textNote.Text = PayorTypeCur.Note;
    }

    private void comboSopCode_DropDown(Object sender, EventArgs e) throws Exception {
        comboSopCode.Items.Clear();
        for (int i = 0;i < Sops.getListt().Count;i++)
        {
            comboSopCode.Items.Add("".PadRight(Sops.getListt()[i].SopCode.Length * 4 - 4, ' ') + Sops.getListt()[i].SopCode + " - " + Sops.getListt()[i].Description);
        }
        comboSopCode.SelectedIndex = _selectedIndex;
    }

    private void comboSopCode_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        _selectedIndex = comboSopCode.SelectedIndex;
        comboSopCode.Items.Clear();
        for (int i = 0;i < Sops.getListt().Count;i++)
        {
            comboSopCode.Items.Add(Sops.getListt()[i].SopCode + " - " + Sops.getListt()[i].Description);
        }
        comboSopCode.SelectedIndex = _selectedIndex;
    }

    private void comboSopCode_DropDownClosed(Object sender, EventArgs e) throws Exception {
        _selectedIndex = comboSopCode.SelectedIndex;
        //if they expanded the drop down and then collapsed it without changing their selection, re-fill combo box without spaces for heirarchy
        //we don't want to do this every time because you can see the text changing and it is an annoyance, better to do in SelectionChangeCommitted
        if (comboSopCode.Items.Count > 2 && comboSopCode.Items[1].ToString().Length > 0 && StringSupport.equals(comboSopCode.Items[1].ToString().Substring(0, 1), " "))
        {
            comboSopCode.Items.Clear();
            for (int i = 0;i < Sops.getListt().Count;i++)
            {
                comboSopCode.Items.Add(Sops.getListt()[i].SopCode + " - " + Sops.getListt()[i].Description);
            }
        }
         
        comboSopCode.SelectedIndex = _selectedIndex;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete entry?"))
        {
            return ;
        }
         
        PayorTypes.delete(PayorTypeCur.PayorTypeNum);
        DialogResult = DialogResult.OK;
    }

    //Causes grid to refresh in case this amendment is not new.
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDate.Text, ""))
        {
            MsgBox.show(this,"Please enter a date.");
            return ;
        }
         
        if (comboSopCode.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an Sop Code.");
            return ;
        }
         
        //Make sure there is not already a payor type entered with the selected date.
        //If there is, they should edit the existing one for that date.  There should not be two payor types that start on the same date.
        List<PayorType> listPayorTypes = PayorTypes.refresh(PayorTypeCur.PatNum);
        for (int i = 0;i < listPayorTypes.Count;i++)
        {
            //if updating an existing payor type, move past the current one
            if (listPayorTypes[i].PayorTypeNum == PayorTypeCur.PayorTypeNum)
            {
                continue;
            }
             
            if (listPayorTypes[i].DateStart == PIn.Date(textDate.Text))
            {
                MsgBox.show(this,"There is already a payor type with the selected start date.  Either change the date of this payor type or edit the existing payor type with this date.");
                return ;
            }
             
        }
        PayorTypeCur.SopCode = Sops.getListt()[comboSopCode.SelectedIndex].SopCode;
        PayorTypeCur.Note = textNote.Text;
        PayorTypeCur.DateStart = PIn.Date(textDate.Text);
        if (IsNew)
        {
            PayorTypes.insert(PayorTypeCur);
        }
        else
        {
            PayorTypes.update(PayorTypeCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayorTypeEdit.class);
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.comboSopCode = new System.Windows.Forms.ComboBox();
        this.textDate = new ODR.ValidDate();
        this.SuspendLayout();
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
        this.butDelete.Location = new System.Drawing.Point(11, 208);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 127;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(537, 208);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 126;
        this.butCancel.Text = "Cancel";
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
        this.butOK.Location = new System.Drawing.Point(456, 208);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 125;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(95, 69);
        this.textNote.MaxLength = 2000;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(490, 105);
        this.textNote.TabIndex = 134;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 42);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(87, 21);
        this.label2.TabIndex = 137;
        this.label2.Text = "Payor Type";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(7, 16);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(86, 17);
        this.label7.TabIndex = 136;
        this.label7.Text = "Start Date";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 69);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(87, 21);
        this.label5.TabIndex = 135;
        this.label5.Text = "Note";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSopCode
        //
        this.comboSopCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSopCode.Location = new System.Drawing.Point(95, 42);
        this.comboSopCode.MaxDropDownItems = 30;
        this.comboSopCode.Name = "comboSopCode";
        this.comboSopCode.Size = new System.Drawing.Size(490, 21);
        this.comboSopCode.TabIndex = 138;
        this.comboSopCode.DropDown += new System.EventHandler(this.comboSopCode_DropDown);
        this.comboSopCode.SelectionChangeCommitted += new System.EventHandler(this.comboSopCode_SelectionChangeCommitted);
        this.comboSopCode.DropDownClosed += new System.EventHandler(this.comboSopCode_DropDownClosed);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(95, 15);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 139;
        //
        // FormPayorTypeEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(624, 244);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.comboSopCode);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPayorTypeEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Payor Type";
        this.Load += new System.EventHandler(this.FormPayorTypeEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSopCode = new System.Windows.Forms.ComboBox();
    private ODR.ValidDate textDate;
}


