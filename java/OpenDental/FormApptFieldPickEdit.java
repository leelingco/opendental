//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.ApptField;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptFields;

public class FormApptFieldPickEdit  extends Form 
{

    public boolean IsNew = new boolean();
    private ApptField Field;
    public FormApptFieldPickEdit(ApptField field) throws Exception {
        initializeComponent();
        Lan.F(this);
        Field = field;
    }

    private void formApptFieldPickEdit_Load(Object sender, EventArgs e) throws Exception {
        labelName.Text = Field.FieldName;
        String value = "";
        value = ApptFieldDefs.getPickListByFieldName(Field.FieldName);
        String[] valueArray = value.Split(new String[]{ "\r\n" }, StringSplitOptions.None);
        for (Object __dummyForeachVar0 : valueArray)
        {
            String s = (String)__dummyForeachVar0;
            listBoxPick.Items.Add(s);
        }
        if (!IsNew)
        {
            listBoxPick.SelectedItem = Field.FieldValue;
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listBoxPick.SelectedItems.Count == 0)
        {
            MsgBox.show(this,"Please select an item in the list first.");
            return ;
        }
         
        Field.FieldValue = listBoxPick.SelectedItem.ToString();
        if (StringSupport.equals(Field.FieldValue, ""))
        {
            //If blank, then delete
            if (IsNew)
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            ApptFields.delete(Field.ApptFieldNum);
            DialogResult = DialogResult.OK;
            return ;
        }
         
        if (IsNew)
        {
            ApptFields.insert(Field);
        }
        else
        {
            ApptFields.update(Field);
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
        this.listBoxPick = new System.Windows.Forms.ListBox();
        this.labelName = new System.Windows.Forms.Label();
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
        this.butOK.Location = new System.Drawing.Point(186, 501);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.Location = new System.Drawing.Point(280, 501);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listBoxPick
        //
        this.listBoxPick.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listBoxPick.FormattingEnabled = true;
        this.listBoxPick.Location = new System.Drawing.Point(22, 40);
        this.listBoxPick.Name = "listBoxPick";
        this.listBoxPick.Size = new System.Drawing.Size(333, 446);
        this.listBoxPick.TabIndex = 6;
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(19, 17);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(335, 20);
        this.labelName.TabIndex = 5;
        this.labelName.Text = "Field Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormApptFieldPickEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(375, 538);
        this.Controls.Add(this.listBoxPick);
        this.Controls.Add(this.labelName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormApptFieldPickEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment Field Pick List";
        this.Load += new System.EventHandler(this.FormApptFieldPickEdit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listBoxPick = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelName = new System.Windows.Forms.Label();
}


