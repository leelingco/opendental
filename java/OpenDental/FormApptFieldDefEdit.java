//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:38 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormApptFieldDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ApptFieldDef;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptFieldType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptFieldDefEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button buttonDelete;
    private ApptFieldDef FieldDef;
    private Label labelStatus = new Label();
    private Label labelWarning = new Label();
    private TextBox textPickList = new TextBox();
    private Label labelFieldType = new Label();
    private ComboBox comboFieldType = new ComboBox();
    private String OldFieldName = new String();
    /**
    * 
    */
    public FormApptFieldDefEdit(ApptFieldDef fieldDef) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        FieldDef = fieldDef;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptFieldDefEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textName = new System.Windows.Forms.TextBox();
        this.buttonDelete = new OpenDental.UI.Button();
        this.labelStatus = new System.Windows.Forms.Label();
        this.labelWarning = new System.Windows.Forms.Label();
        this.textPickList = new System.Windows.Forms.TextBox();
        this.labelFieldType = new System.Windows.Forms.Label();
        this.comboFieldType = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(254, 451);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
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
        this.butOK.Location = new System.Drawing.Point(254, 416);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(20, 27);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(308, 20);
        this.textName.TabIndex = 0;
        //
        // buttonDelete
        //
        this.buttonDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.buttonDelete.setAutosize(true);
        this.buttonDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonDelete.setCornerRadius(4F);
        this.buttonDelete.Image = Resources.getdeleteX();
        this.buttonDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.buttonDelete.Location = new System.Drawing.Point(20, 451);
        this.buttonDelete.Name = "buttonDelete";
        this.buttonDelete.Size = new System.Drawing.Size(82, 24);
        this.buttonDelete.TabIndex = 3;
        this.buttonDelete.Text = "&Delete";
        this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
        //
        // labelStatus
        //
        this.labelStatus.Location = new System.Drawing.Point(17, 9);
        this.labelStatus.Name = "labelStatus";
        this.labelStatus.Size = new System.Drawing.Size(143, 15);
        this.labelStatus.TabIndex = 80;
        this.labelStatus.Text = "Field Name";
        this.labelStatus.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelWarning
        //
        this.labelWarning.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelWarning.Location = new System.Drawing.Point(203, 71);
        this.labelWarning.Name = "labelWarning";
        this.labelWarning.Size = new System.Drawing.Size(101, 14);
        this.labelWarning.TabIndex = 89;
        this.labelWarning.Text = "One Per Line";
        this.labelWarning.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelWarning.Visible = false;
        //
        // textPickList
        //
        this.textPickList.AcceptsReturn = true;
        this.textPickList.Location = new System.Drawing.Point(20, 96);
        this.textPickList.MaxLength = 4000;
        this.textPickList.Multiline = true;
        this.textPickList.Name = "textPickList";
        this.textPickList.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textPickList.Size = new System.Drawing.Size(309, 301);
        this.textPickList.TabIndex = 88;
        //
        // labelFieldType
        //
        this.labelFieldType.Location = new System.Drawing.Point(17, 50);
        this.labelFieldType.Name = "labelFieldType";
        this.labelFieldType.Size = new System.Drawing.Size(143, 15);
        this.labelFieldType.TabIndex = 87;
        this.labelFieldType.Text = "Field Type";
        this.labelFieldType.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboFieldType
        //
        this.comboFieldType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboFieldType.DropDownWidth = 177;
        this.comboFieldType.FormattingEnabled = true;
        this.comboFieldType.Location = new System.Drawing.Point(20, 68);
        this.comboFieldType.MaxDropDownItems = 30;
        this.comboFieldType.Name = "comboFieldType";
        this.comboFieldType.Size = new System.Drawing.Size(177, 21);
        this.comboFieldType.TabIndex = 86;
        this.comboFieldType.SelectedIndexChanged += new System.EventHandler(this.comboFieldType_SelectedIndexChanged);
        //
        // FormApptFieldDefEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(349, 493);
        this.Controls.Add(this.labelWarning);
        this.Controls.Add(this.textPickList);
        this.Controls.Add(this.labelFieldType);
        this.Controls.Add(this.comboFieldType);
        this.Controls.Add(this.labelStatus);
        this.Controls.Add(this.buttonDelete);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptFieldDefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment Field Def";
        this.Load += new System.EventHandler(this.FormApptFieldDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formApptFieldDefEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textName.Text = FieldDef.FieldName;
        textPickList.Visible = false;
        comboFieldType.Items.Clear();
        comboFieldType.Items.AddRange(Enum.GetNames(ApptFieldType.class));
        comboFieldType.SelectedIndex = ((Enum)FieldDef.FieldType).ordinal();
        if (!IsNew)
        {
            OldFieldName = FieldDef.FieldName;
        }
         
        if (comboFieldType.SelectedIndex == ((Enum)ApptFieldType.PickList).ordinal())
        {
            textPickList.Visible = true;
            labelWarning.Visible = true;
            textPickList.Text = FieldDef.PickList;
        }
         
    }

    private void comboFieldType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        textPickList.Visible = false;
        labelWarning.Visible = false;
        if (comboFieldType.SelectedIndex == ((Enum)ApptFieldType.PickList).ordinal())
        {
            textPickList.Visible = true;
            labelWarning.Visible = true;
        }
         
    }

    private void buttonDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        try
        {
            ApptFieldDefs.delete(FieldDef);
            DialogResult = DialogResult.OK;
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(OldFieldName, textName.Text))
        {
            for (int i = 0;i < ApptFieldDefs.getListt().Count;i++)
            {
                if (ApptFieldDefs.getListt()[i].FieldName == textName.Text)
                {
                    MsgBox.show(this,"Field name currently being used.");
                    return ;
                }
                 
            }
        }
         
        FieldDef.FieldName = textName.Text;
        FieldDef.FieldType = (ApptFieldType)comboFieldType.SelectedIndex;
        if (FieldDef.FieldType == ApptFieldType.PickList)
        {
            if (StringSupport.equals(textPickList.Text, ""))
            {
                MsgBox.show(this,"List cannot be blank.");
                return ;
            }
             
            FieldDef.PickList = textPickList.Text;
        }
         
        if (IsNew)
        {
            ApptFieldDefs.insert(FieldDef);
        }
        else
        {
            ApptFieldDefs.update(FieldDef,OldFieldName);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


