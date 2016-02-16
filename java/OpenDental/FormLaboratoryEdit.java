//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormLaboratoryEdit;
import OpenDental.FormLabTurnaroundEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Laboratories;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.LabTurnaround;
import OpenDentBusiness.LabTurnarounds;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.TelephoneNumbers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLaboratoryEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textNotes = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
    public Laboratory LabCur;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butDeleteTurnaround;
    private ComboBox comboSlip = new ComboBox();
    private Label label21 = new Label();
    private Label label4 = new Label();
    private List<LabTurnaround> turnaroundList = new List<LabTurnaround>();
    private TextBox textWirelessPhone = new TextBox();
    private Label label5 = new Label();
    private TextBox textAddress = new TextBox();
    private Label label6 = new Label();
    private TextBox textCity = new TextBox();
    private Label label7 = new Label();
    private TextBox textState = new TextBox();
    private TextBox textZip = new TextBox();
    private TextBox textEmail = new TextBox();
    private Label label8 = new Label();
    private List<SheetDef> SlipList = new List<SheetDef>();
    /**
    * 
    */
    public FormLaboratoryEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLaboratoryEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textNotes = new System.Windows.Forms.TextBox();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.comboSlip = new System.Windows.Forms.ComboBox();
        this.label21 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butDeleteTurnaround = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textWirelessPhone = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textCity = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textState = new System.Windows.Forms.TextBox();
        this.textZip = new System.Windows.Forms.TextBox();
        this.textEmail = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(99, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(104, 7);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(241, 20);
        this.textDescription.TabIndex = 0;
        //
        // textNotes
        //
        this.textNotes.Location = new System.Drawing.Point(104, 146);
        this.textNotes.MaxLength = 255;
        this.textNotes.Multiline = true;
        this.textNotes.Name = "textNotes";
        this.textNotes.Size = new System.Drawing.Size(388, 52);
        this.textNotes.TabIndex = 2;
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(104, 30);
        this.textPhone.MaxLength = 255;
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(157, 20);
        this.textPhone.TabIndex = 1;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(0, 148);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(102, 17);
        this.label3.TabIndex = 101;
        this.label3.Text = "Notes";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(1, 33);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(102, 17);
        this.label2.TabIndex = 99;
        this.label2.Text = "Phone";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboSlip
        //
        this.comboSlip.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSlip.Location = new System.Drawing.Point(104, 204);
        this.comboSlip.MaxDropDownItems = 30;
        this.comboSlip.Name = "comboSlip";
        this.comboSlip.Size = new System.Drawing.Size(275, 21);
        this.comboSlip.TabIndex = 131;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(382, 206);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(283, 16);
        this.label21.TabIndex = 130;
        this.label21.Text = "(custom lab slips may be added in Sheets)";
        this.label21.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(-1, 207);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(102, 17);
        this.label4.TabIndex = 132;
        this.label4.Text = "Lab Slip";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butDeleteTurnaround
        //
        this.butDeleteTurnaround.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteTurnaround.setAutosize(true);
        this.butDeleteTurnaround.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteTurnaround.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteTurnaround.setCornerRadius(4F);
        this.butDeleteTurnaround.Image = Resources.getdeleteX();
        this.butDeleteTurnaround.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteTurnaround.Location = new System.Drawing.Point(17, 264);
        this.butDeleteTurnaround.Name = "butDeleteTurnaround";
        this.butDeleteTurnaround.Size = new System.Drawing.Size(81, 24);
        this.butDeleteTurnaround.TabIndex = 129;
        this.butDeleteTurnaround.Text = "Delete";
        this.butDeleteTurnaround.Click += new System.EventHandler(this.butDeleteTurnaround_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(104, 232);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(561, 261);
        this.gridMain.TabIndex = 128;
        this.gridMain.setTitle("Turnaround Times");
        this.gridMain.setTranslationName("TableLabTurnaround");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(17, 232);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(81, 24);
        this.butAdd.TabIndex = 127;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butDelete.Location = new System.Drawing.Point(17, 594);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 4;
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
        this.butOK.Location = new System.Drawing.Point(606, 594);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.Location = new System.Drawing.Point(697, 594);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textWirelessPhone
        //
        this.textWirelessPhone.Location = new System.Drawing.Point(104, 53);
        this.textWirelessPhone.MaxLength = 255;
        this.textWirelessPhone.Name = "textWirelessPhone";
        this.textWirelessPhone.Size = new System.Drawing.Size(157, 20);
        this.textWirelessPhone.TabIndex = 133;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(1, 56);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(102, 17);
        this.label5.TabIndex = 134;
        this.label5.Text = "Wireless Phone";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(104, 76);
        this.textAddress.MaxLength = 255;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(241, 20);
        this.textAddress.TabIndex = 135;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(1, 79);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(102, 17);
        this.label6.TabIndex = 136;
        this.label6.Text = "Address";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(104, 99);
        this.textCity.MaxLength = 255;
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(157, 20);
        this.textCity.TabIndex = 137;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(1, 102);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(102, 17);
        this.label7.TabIndex = 138;
        this.label7.Text = "City, ST Zip";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(267, 99);
        this.textState.MaxLength = 255;
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(47, 20);
        this.textState.TabIndex = 139;
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(320, 99);
        this.textZip.MaxLength = 255;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(82, 20);
        this.textZip.TabIndex = 140;
        //
        // textEmail
        //
        this.textEmail.Location = new System.Drawing.Point(104, 122);
        this.textEmail.MaxLength = 255;
        this.textEmail.Name = "textEmail";
        this.textEmail.Size = new System.Drawing.Size(275, 20);
        this.textEmail.TabIndex = 141;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(-1, 125);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(102, 17);
        this.label8.TabIndex = 142;
        this.label8.Text = "Email";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormLaboratoryEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(798, 638);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textEmail);
        this.Controls.Add(this.textZip);
        this.Controls.Add(this.textState);
        this.Controls.Add(this.textCity);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textAddress);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textWirelessPhone);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.comboSlip);
        this.Controls.Add(this.label21);
        this.Controls.Add(this.butDeleteTurnaround);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.textPhone);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLaboratoryEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Laboratory";
        this.Load += new System.EventHandler(this.FormLaboratoryEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLaboratoryEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = LabCur.Description;
        textPhone.Text = LabCur.Phone;
        textWirelessPhone.Text = LabCur.WirelessPhone;
        textAddress.Text = LabCur.Address;
        textCity.Text = LabCur.City;
        textState.Text = LabCur.State;
        textZip.Text = LabCur.Zip;
        textEmail.Text = LabCur.Email;
        textNotes.Text = LabCur.Notes;
        turnaroundList = LabTurnarounds.getForLab(LabCur.LaboratoryNum);
        comboSlip.Items.Add(Lan.g(this,"Default"));
        comboSlip.SelectedIndex = 0;
        SlipList = SheetDefs.getCustomForType(SheetTypeEnum.LabSlip);
        for (int i = 0;i < SlipList.Count;i++)
        {
            comboSlip.Items.Add(SlipList[i].Description);
            if (LabCur.Slip == SlipList[i].SheetDefNum)
            {
                comboSlip.SelectedIndex = i + 1;
            }
             
        }
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //does not refresh from database.
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableLabTurnaround","Service Description"),300);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabTurnaround","Days Published"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabTurnaround","Actual Days"),120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < turnaroundList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(turnaroundList[i].Description);
            if (turnaroundList[i].DaysPublished == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(turnaroundList[i].DaysPublished.ToString());
            } 
            row.getCells().Add(turnaroundList[i].DaysActual.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormLabTurnaroundEdit FormL = new FormLabTurnaroundEdit();
        FormL.LabTurnaroundCur = new LabTurnaround();
        FormL.ShowDialog();
        if (FormL.DialogResult == DialogResult.OK)
        {
            turnaroundList.Add(FormL.LabTurnaroundCur);
            fillGrid();
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormLabTurnaroundEdit FormL = new FormLabTurnaroundEdit();
        FormL.LabTurnaroundCur = turnaroundList[e.getRow()];
        FormL.ShowDialog();
        if (FormL.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void butDeleteTurnaround_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        turnaroundList.RemoveAt(gridMain.getSelectedIndex());
        fillGrid();
    }

    private void textPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textPhone.SelectionStart;
        int length = textPhone.Text.Length;
        textPhone.Text = TelephoneNumbers.AutoFormat(textPhone.Text);
        if (textPhone.Text.Length > length)
            cursor++;
         
        textPhone.SelectionStart = cursor;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete this entire Laboratory?"))
        {
            return ;
        }
         
        try
        {
            Laboratories.delete(LabCur.LaboratoryNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Description cannot be blank.");
            return ;
        }
         
        LabCur.Description = textDescription.Text;
        LabCur.Phone = textPhone.Text;
        LabCur.WirelessPhone = textWirelessPhone.Text;
        LabCur.Address = textAddress.Text;
        LabCur.City = textCity.Text;
        LabCur.State = textState.Text;
        LabCur.Zip = textZip.Text;
        LabCur.Email = textEmail.Text;
        LabCur.Notes = textNotes.Text;
        LabCur.Slip = 0;
        if (comboSlip.SelectedIndex > 0)
        {
            LabCur.Slip = SlipList[comboSlip.SelectedIndex - 1].SheetDefNum;
        }
         
        try
        {
            if (IsNew)
            {
                Laboratories.insert(LabCur);
            }
            else
            {
                Laboratories.update(LabCur);
            } 
            LabTurnarounds.setForLab(LabCur.LaboratoryNum,turnaroundList);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


