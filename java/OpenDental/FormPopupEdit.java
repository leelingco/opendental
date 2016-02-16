//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPopupAudit;
import OpenDental.FormPopupEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.EnumPopupLevel;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Popup;
import OpenDentBusiness.Popups;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userods;

/**
* 
*/
public class FormPopupEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private CheckBox checkIsDisabled = new CheckBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDelete;
    public Popup PopupCur;
    public Popup PopupAudit;
    /**
    * The last edit date of the current popup.  Should be set before this form loads.
    */
    public DateTime DateLastEdit = new DateTime();
    private ComboBox comboPopupLevel = new ComboBox();
    private Label label2 = new Label();
    private Label label3 = new Label();
    private TextBox textPatient = new TextBox();
    private OpenDental.ODtextBox textDescription;
    private TextBox textCreateDate = new TextBox();
    private Label label5 = new Label();
    private TextBox textUser = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butAudit;
    private TextBox textEditDate = new TextBox();
    private Label label6 = new Label();
    private Patient Pat;
    /**
    * 
    */
    public FormPopupEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPopupEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.checkIsDisabled = new System.Windows.Forms.CheckBox();
        this.comboPopupLevel = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDescription = new OpenDental.ODtextBox();
        this.textCreateDate = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butAudit = new OpenDental.UI.Button();
        this.textEditDate = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(48, 160);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(101, 19);
        this.label1.TabIndex = 3;
        this.label1.Text = "Popup Message";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkIsDisabled
        //
        this.checkIsDisabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsDisabled.Location = new System.Drawing.Point(6, 137);
        this.checkIsDisabled.Name = "checkIsDisabled";
        this.checkIsDisabled.Size = new System.Drawing.Size(158, 18);
        this.checkIsDisabled.TabIndex = 4;
        this.checkIsDisabled.Text = "Permanently Disabled";
        this.checkIsDisabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsDisabled.UseVisualStyleBackColor = true;
        //
        // comboPopupLevel
        //
        this.comboPopupLevel.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPopupLevel.FormattingEnabled = true;
        this.comboPopupLevel.Location = new System.Drawing.Point(149, 110);
        this.comboPopupLevel.Name = "comboPopupLevel";
        this.comboPopupLevel.Size = new System.Drawing.Size(159, 21);
        this.comboPopupLevel.TabIndex = 6;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(28, 110);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(121, 18);
        this.label2.TabIndex = 7;
        this.label2.Text = "Level";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 86);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(142, 15);
        this.label3.TabIndex = 8;
        this.label3.Text = "Patient";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.Enabled = false;
        this.textPatient.Location = new System.Drawing.Point(149, 84);
        this.textPatient.Name = "textPatient";
        this.textPatient.Size = new System.Drawing.Size(271, 20);
        this.textPatient.TabIndex = 9;
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
        this.butDelete.Location = new System.Drawing.Point(28, 271);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 5;
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
        this.butOK.Location = new System.Drawing.Point(302, 271);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(77, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "OK";
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
        this.butCancel.Location = new System.Drawing.Point(385, 271);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDescription
        //
        this.textDescription.AcceptsTab = true;
        this.textDescription.DetectUrls = false;
        this.textDescription.Location = new System.Drawing.Point(149, 160);
        this.textDescription.Name = "textDescription";
        this.textDescription.setQuickPasteType(OpenDentBusiness.QuickPasteType.Popup);
        this.textDescription.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textDescription.Size = new System.Drawing.Size(271, 91);
        this.textDescription.TabIndex = 2;
        this.textDescription.Text = "";
        //
        // textCreateDate
        //
        this.textCreateDate.Enabled = false;
        this.textCreateDate.Location = new System.Drawing.Point(149, 33);
        this.textCreateDate.Name = "textCreateDate";
        this.textCreateDate.ReadOnly = true;
        this.textCreateDate.Size = new System.Drawing.Size(159, 20);
        this.textCreateDate.TabIndex = 16;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(15, 35);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(134, 15);
        this.label5.TabIndex = 15;
        this.label5.Text = "Date Created";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUser
        //
        this.textUser.Enabled = false;
        this.textUser.Location = new System.Drawing.Point(149, 7);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(159, 20);
        this.textUser.TabIndex = 14;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(18, 9);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(131, 15);
        this.label4.TabIndex = 13;
        this.label4.Text = "User";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butAudit
        //
        this.butAudit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAudit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAudit.setAutosize(true);
        this.butAudit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAudit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAudit.setCornerRadius(4F);
        this.butAudit.Location = new System.Drawing.Point(149, 271);
        this.butAudit.Name = "butAudit";
        this.butAudit.Size = new System.Drawing.Size(77, 26);
        this.butAudit.TabIndex = 17;
        this.butAudit.Text = "Audit Trail";
        this.butAudit.Click += new System.EventHandler(this.butAudit_Click);
        //
        // textEditDate
        //
        this.textEditDate.Enabled = false;
        this.textEditDate.Location = new System.Drawing.Point(149, 59);
        this.textEditDate.Name = "textEditDate";
        this.textEditDate.ReadOnly = true;
        this.textEditDate.Size = new System.Drawing.Size(159, 20);
        this.textEditDate.TabIndex = 18;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(15, 61);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(134, 15);
        this.label6.TabIndex = 19;
        this.label6.Text = "Date Edited";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormPopupEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(492, 314);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textEditDate);
        this.Controls.Add(this.butAudit);
        this.Controls.Add(this.textCreateDate);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboPopupLevel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkIsDisabled);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPopupEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Popup";
        this.Load += new System.EventHandler(this.FormPopupEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPopupEdit_Load(Object sender, EventArgs e) throws Exception {
        Pat = Patients.getPat(PopupCur.PatNum);
        textPatient.Text = Pat.getNameLF();
        if (PopupCur.getIsNew())
        {
            //If popup is new User is the logged-in user and create date is now.
            butAudit.Visible = false;
            textUser.Text = Security.getCurUser().UserName;
            textCreateDate.Text = DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString();
        }
        else
        {
            if (PopupCur.UserNum != 0)
            {
                //This check is so that any old popups without a user will still display correctly.
                textUser.Text = Userods.getUser(PopupCur.UserNum).UserName;
            }
             
            if (PopupAudit != null)
            {
                //This checks if this window opened from FormPopupAudit
                textCreateDate.Text = "";
                if (PopupAudit.DateTimeEntry.Year > 1880)
                {
                    textCreateDate.Text = PopupAudit.DateTimeEntry.ToShortDateString() + " " + PopupAudit.DateTimeEntry.ToShortTimeString();
                }
                 
                //Sets the original creation date.
                textEditDate.Text = "";
                if (DateLastEdit.Year > 1880)
                {
                    textEditDate.Text = DateLastEdit.ToShortDateString() + " " + DateLastEdit.ToShortTimeString();
                }
                 
            }
            else
            {
                textCreateDate.Text = "";
                if (PopupCur.DateTimeEntry.Year > 1880)
                {
                    textCreateDate.Text = PopupCur.DateTimeEntry.ToShortDateString() + " " + PopupCur.DateTimeEntry.ToShortTimeString();
                }
                 
                //Sets the original creation date.
                DateTime dateT = Popups.getLastEditDateTimeForPopup(PopupCur.PopupNum);
                textEditDate.Text = "";
                if (dateT.Year > 1880)
                {
                    textEditDate.Text = dateT.ToShortDateString() + " " + dateT.ToShortTimeString();
                }
                 
            } 
        } 
        //Sets the Edit date to the entry date of the last popup change that was archived for this popup.
        comboPopupLevel.Items.Add(Lan.g("enumEnumPopupFamily", Enum.GetNames(EnumPopupLevel.class)[0]));
        //Patient
        comboPopupLevel.Items.Add(Lan.g("enumEnumPopupFamily", Enum.GetNames(EnumPopupLevel.class)[1]));
        //Family
        if (Pat.SuperFamily != 0)
        {
            comboPopupLevel.Items.Add(Lan.g("enumEnumPopupFamily", Enum.GetNames(EnumPopupLevel.class)[2]));
        }
         
        //SuperFamily
        comboPopupLevel.SelectedIndex = ((Enum)PopupCur.PopupLevel).ordinal();
        checkIsDisabled.Checked = PopupCur.IsDisabled;
        textDescription.Text = PopupCur.Description;
        if (PopupCur.IsArchived)
        {
            butDelete.Enabled = false;
            butOK.Enabled = false;
            comboPopupLevel.Enabled = false;
            checkIsDisabled.Enabled = false;
            textDescription.ReadOnly = true;
            textPatient.ReadOnly = true;
        }
         
        if (PopupCur.PopupNumArchive != 0)
        {
            butAudit.Visible = false;
        }
         
    }

    private void butAudit_Click(Object sender, EventArgs e) throws Exception {
        FormPopupAudit FormPA = new FormPopupAudit();
        FormPA.PopupCur = PopupCur;
        FormPA.PatCur = Pat;
        FormPA.ShowDialog();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (PopupCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //don't ask user to make it go faster.
        PopupCur.IsArchived = true;
        PopupCur.PopupNumArchive = 0;
        Popups.update(PopupCur);
        //Runs an update to "archive" the popup, but allows it to be shown under the deleted section.
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Please enter text first");
            return ;
        }
         
        //PatNum cannot be set
        if (PopupCur.getIsNew())
        {
            //PopCur date got set on load
            PopupCur.PopupLevel = (EnumPopupLevel)comboPopupLevel.SelectedIndex;
            PopupCur.IsDisabled = checkIsDisabled.Checked;
            PopupCur.Description = textDescription.Text;
            PopupCur.UserNum = Security.getCurUser().UserNum;
            Popups.insert(PopupCur);
        }
        else
        {
            if (!StringSupport.equals(PopupCur.Description, textDescription.Text))
            {
                //if user changed the description
                Popup popupArchive = PopupCur.copy();
                popupArchive.IsArchived = true;
                popupArchive.PopupNumArchive = PopupCur.PopupNum;
                Popups.insert(popupArchive);
                PopupCur.Description = textDescription.Text;
                PopupCur.UserNum = Security.getCurUser().UserNum;
            }
             
            //No need to make an archive entry for changes to PopupLevel or IsDisabled so they get set on every OK Click.
            PopupCur.PopupLevel = (EnumPopupLevel)comboPopupLevel.SelectedIndex;
            PopupCur.IsDisabled = checkIsDisabled.Checked;
            Popups.update(PopupCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


