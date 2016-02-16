//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:39 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormProviderPick;
import OpenDental.FormRefAttachEdit;
import OpenDental.FormReferralEdit;
import OpenDental.FormReferralSelect;
import OpenDental.FormSheetFillEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.ReferralToStatus;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.Sheets;

/**
* 
*/
public class FormRefAttachEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidNumber textOrder;
    private System.Windows.Forms.Label labelOrder = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEdit;
    private OpenDental.ValidDate textRefDate;
    private System.Windows.Forms.TextBox textReferralNotes = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private Label label6 = new Label();
    private TextBox textNote = new TextBox();
    private ComboBox comboRefToStatus = new ComboBox();
    private Label label7 = new Label();
    private OpenDental.UI.Button butDelete;
    private ListBox listSheets = new ListBox();
    /**
    * 
    */
    public RefAttach RefAttachCur;
    private Label label8 = new Label();
    private ListBox listFromTo = new ListBox();
    private CheckBox checkIsTransitionOfCare = new CheckBox();
    private Label label4 = new Label();
    private TextBox textProc = new TextBox();
    private Label label13 = new Label();
    private OpenDental.ValidDate textDateProcCompleted;
    private Label label9 = new Label();
    private OpenDental.UI.Button butChangeReferral;
    private OpenDental.UI.Button butNoneProv;
    private ComboBox comboProvNum = new ComboBox();
    private OpenDental.UI.Button butPickProv;
    private Label label10 = new Label();
    /**
    * List of referral slips for this pat/ref combo.
    */
    private List<Sheet> SheetList = new List<Sheet>();
    /**
    * Select a referring provider for referals to other providers.
    */
    private long _provNumSelected = new long();
    /**
    * 
    */
    public FormRefAttachEdit() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRefAttachEdit.class);
        this.label3 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.labelOrder = new System.Windows.Forms.Label();
        this.textReferralNotes = new System.Windows.Forms.TextBox();
        this.labelPatient = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.comboRefToStatus = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.listSheets = new System.Windows.Forms.ListBox();
        this.label8 = new System.Windows.Forms.Label();
        this.listFromTo = new System.Windows.Forms.ListBox();
        this.checkIsTransitionOfCare = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textProc = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.comboProvNum = new System.Windows.Forms.ComboBox();
        this.label10 = new System.Windows.Forms.Label();
        this.butNoneProv = new OpenDental.UI.Button();
        this.butPickProv = new OpenDental.UI.Button();
        this.textDateProcCompleted = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.textRefDate = new OpenDental.ValidDate();
        this.butChangeReferral = new OpenDental.UI.Button();
        this.butEdit = new OpenDental.UI.Button();
        this.textOrder = new OpenDental.ValidNumber();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(65, 176);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(84, 16);
        this.label3.TabIndex = 16;
        this.label3.Text = "Date";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(59, 47);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(90, 18);
        this.label1.TabIndex = 17;
        this.label1.Text = "Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(151, 43);
        this.textName.Name = "textName";
        this.textName.ReadOnly = true;
        this.textName.Size = new System.Drawing.Size(258, 20);
        this.textName.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(65, 13);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(86, 16);
        this.label2.TabIndex = 20;
        this.label2.Text = "From/To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelOrder
        //
        this.labelOrder.Location = new System.Drawing.Point(69, 197);
        this.labelOrder.Name = "labelOrder";
        this.labelOrder.Size = new System.Drawing.Size(82, 14);
        this.labelOrder.TabIndex = 73;
        this.labelOrder.Text = "Order";
        this.labelOrder.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textReferralNotes
        //
        this.textReferralNotes.Location = new System.Drawing.Point(151, 84);
        this.textReferralNotes.Multiline = true;
        this.textReferralNotes.Name = "textReferralNotes";
        this.textReferralNotes.ReadOnly = true;
        this.textReferralNotes.Size = new System.Drawing.Size(363, 66);
        this.textReferralNotes.TabIndex = 78;
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(150, 65);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(98, 18);
        this.labelPatient.TabIndex = 80;
        this.labelPatient.Text = "(a patient)";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(3, 84);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(146, 38);
        this.label5.TabIndex = 81;
        this.label5.Text = "Notes about referral source";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 236);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(146, 38);
        this.label6.TabIndex = 83;
        this.label6.Text = "Patient note";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(151, 236);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(363, 66);
        this.textNote.TabIndex = 1;
        //
        // comboRefToStatus
        //
        this.comboRefToStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboRefToStatus.FormattingEnabled = true;
        this.comboRefToStatus.Location = new System.Drawing.Point(151, 214);
        this.comboRefToStatus.MaxDropDownItems = 20;
        this.comboRefToStatus.Name = "comboRefToStatus";
        this.comboRefToStatus.Size = new System.Drawing.Size(180, 21);
        this.comboRefToStatus.TabIndex = 84;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(6, 217);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(143, 14);
        this.label7.TabIndex = 85;
        this.label7.Text = "Status (if referred out)";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listSheets
        //
        this.listSheets.FormattingEnabled = true;
        this.listSheets.Location = new System.Drawing.Point(151, 303);
        this.listSheets.Name = "listSheets";
        this.listSheets.Size = new System.Drawing.Size(120, 69);
        this.listSheets.TabIndex = 90;
        this.listSheets.DoubleClick += new System.EventHandler(this.listSheets_DoubleClick);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(9, 305);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(140, 40);
        this.label8.TabIndex = 91;
        this.label8.Text = "Referral Slips\r\n(double click to view)";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listFromTo
        //
        this.listFromTo.FormattingEnabled = true;
        this.listFromTo.Items.AddRange(new Object[]{ "From", "To" });
        this.listFromTo.Location = new System.Drawing.Point(151, 12);
        this.listFromTo.Name = "listFromTo";
        this.listFromTo.Size = new System.Drawing.Size(65, 30);
        this.listFromTo.TabIndex = 92;
        this.listFromTo.SelectedIndexChanged += new System.EventHandler(this.listFromTo_SelectedIndexChanged);
        //
        // checkIsTransitionOfCare
        //
        this.checkIsTransitionOfCare.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsTransitionOfCare.Location = new System.Drawing.Point(29, 375);
        this.checkIsTransitionOfCare.Name = "checkIsTransitionOfCare";
        this.checkIsTransitionOfCare.Size = new System.Drawing.Size(136, 18);
        this.checkIsTransitionOfCare.TabIndex = 93;
        this.checkIsTransitionOfCare.Text = "Transition of Care";
        this.checkIsTransitionOfCare.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsTransitionOfCare.UseVisualStyleBackColor = true;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(166, 376);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(195, 14);
        this.label4.TabIndex = 94;
        this.label4.Text = "(From or To another doctor)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textProc
        //
        this.textProc.BackColor = System.Drawing.SystemColors.Control;
        this.textProc.ForeColor = System.Drawing.Color.DarkRed;
        this.textProc.Location = new System.Drawing.Point(151, 395);
        this.textProc.Name = "textProc";
        this.textProc.Size = new System.Drawing.Size(232, 20);
        this.textProc.TabIndex = 171;
        this.textProc.Text = "test";
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(65, 398);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(84, 16);
        this.label13.TabIndex = 170;
        this.label13.Text = "Procedure";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(14, 420);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(135, 16);
        this.label9.TabIndex = 172;
        this.label9.Text = "Date Proc Completed";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboProvNum
        //
        this.comboProvNum.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvNum.Location = new System.Drawing.Point(151, 151);
        this.comboProvNum.MaxDropDownItems = 30;
        this.comboProvNum.Name = "comboProvNum";
        this.comboProvNum.Size = new System.Drawing.Size(254, 21);
        this.comboProvNum.TabIndex = 280;
        this.comboProvNum.SelectionChangeCommitted += new System.EventHandler(this.comboProvNum_SelectionChangeCommitted);
        //
        // label10
        //
        this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label10.Location = new System.Drawing.Point(21, 151);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(130, 17);
        this.label10.TabIndex = 279;
        this.label10.Text = "Referring Provider";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butNoneProv
        //
        this.butNoneProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneProv.setAutosize(false);
        this.butNoneProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneProv.setCornerRadius(2F);
        this.butNoneProv.Location = new System.Drawing.Point(428, 151);
        this.butNoneProv.Name = "butNoneProv";
        this.butNoneProv.Size = new System.Drawing.Size(44, 21);
        this.butNoneProv.TabIndex = 282;
        this.butNoneProv.Text = "None";
        this.butNoneProv.Click += new System.EventHandler(this.butNoneProv_Click);
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(407, 151);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 21);
        this.butPickProv.TabIndex = 281;
        this.butPickProv.Text = "...";
        this.butPickProv.Click += new System.EventHandler(this.butPickProv_Click);
        //
        // textDateProcCompleted
        //
        this.textDateProcCompleted.Location = new System.Drawing.Point(151, 416);
        this.textDateProcCompleted.Name = "textDateProcCompleted";
        this.textDateProcCompleted.Size = new System.Drawing.Size(100, 20);
        this.textDateProcCompleted.TabIndex = 173;
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
        this.butDelete.Location = new System.Drawing.Point(14, 477);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 86;
        this.butDelete.Text = "Detach";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textRefDate
        //
        this.textRefDate.Location = new System.Drawing.Point(151, 172);
        this.textRefDate.Name = "textRefDate";
        this.textRefDate.Size = new System.Drawing.Size(100, 20);
        this.textRefDate.TabIndex = 75;
        //
        // butChangeReferral
        //
        this.butChangeReferral.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangeReferral.setAutosize(true);
        this.butChangeReferral.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangeReferral.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangeReferral.setCornerRadius(4F);
        this.butChangeReferral.Location = new System.Drawing.Point(514, 40);
        this.butChangeReferral.Name = "butChangeReferral";
        this.butChangeReferral.Size = new System.Drawing.Size(95, 24);
        this.butChangeReferral.TabIndex = 74;
        this.butChangeReferral.Text = "Change Referral";
        this.butChangeReferral.Click += new System.EventHandler(this.butChangeReferral_Click);
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Location = new System.Drawing.Point(413, 40);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(95, 24);
        this.butEdit.TabIndex = 74;
        this.butEdit.Text = "&Edit Referral";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // textOrder
        //
        this.textOrder.Location = new System.Drawing.Point(151, 193);
        this.textOrder.setMaxVal(255);
        this.textOrder.setMinVal(0);
        this.textOrder.Name = "textOrder";
        this.textOrder.Size = new System.Drawing.Size(36, 20);
        this.textOrder.TabIndex = 72;
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
        this.butCancel.Location = new System.Drawing.Point(602, 477);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 6;
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
        this.butOK.Location = new System.Drawing.Point(602, 440);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 0;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRefAttachEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(689, 513);
        this.Controls.Add(this.butNoneProv);
        this.Controls.Add(this.comboProvNum);
        this.Controls.Add(this.butPickProv);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.textDateProcCompleted);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textProc);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.checkIsTransitionOfCare);
        this.Controls.Add(this.listFromTo);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.listSheets);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.comboRefToStatus);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.labelPatient);
        this.Controls.Add(this.textReferralNotes);
        this.Controls.Add(this.textRefDate);
        this.Controls.Add(this.butChangeReferral);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.textOrder);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelOrder);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label3);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRefAttachEdit";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Referral Attachment";
        this.Load += new System.EventHandler(this.FormRefAttachEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRefAttachEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            this.Text = Lan.g(this,"Add Referral Attachment");
        }
        else
        {
            this.Text = Lan.g(this,"Edit Referral Attachment");
        } 
        fillData();
        fillSheets();
        _provNumSelected = RefAttachCur.ProvNum;
        comboProvNum.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNum.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            //Only visible provs added to combobox.
            if (ProviderC.getListShort()[i].ProvNum == RefAttachCur.ProvNum)
            {
                comboProvNum.SelectedIndex = i;
            }
             
        }
        //Sets combo text too.
        if (comboProvNum.SelectedIndex == -1)
        {
            //The provider exists but is hidden
            comboProvNum.Text = Providers.getLongDesc(_provNumSelected);
        }
         
        //Appends "(hidden)" to the end of the long description.
        if (RefAttachCur.IsFrom)
        {
            butNoneProv.Visible = false;
            butPickProv.Visible = false;
            comboProvNum.Visible = false;
            label10.Visible = false;
        }
        else
        {
            butNoneProv.Visible = true;
            butPickProv.Visible = true;
            comboProvNum.Visible = true;
            label10.Visible = true;
        } 
    }

    private void fillData() throws Exception {
        Referral referral = Referrals.GetReferral(RefAttachCur.ReferralNum);
        textName.Text = referral.getNameFL();
        labelPatient.Visible = referral.PatNum > 0;
        textReferralNotes.Text = referral.Note;
        if (RefAttachCur.IsFrom)
        {
            listFromTo.SelectedIndex = 0;
        }
        else
        {
            listFromTo.SelectedIndex = 1;
        } 
        if (RefAttachCur.RefDate.Year < 1880)
        {
            textRefDate.Text = "";
        }
        else
        {
            textRefDate.Text = RefAttachCur.RefDate.ToShortDateString();
        } 
        textOrder.Text = RefAttachCur.ItemOrder.ToString();
        comboRefToStatus.Items.Clear();
        for (int i = 0;i < Enum.GetNames(ReferralToStatus.class).Length;i++)
        {
            comboRefToStatus.Items.Add(Lan.g("enumReferralToStatus", Enum.GetNames(ReferralToStatus.class)[i]));
            if ((int)RefAttachCur.RefToStatus == i)
            {
                comboRefToStatus.SelectedIndex = i;
            }
             
        }
        textNote.Text = RefAttachCur.Note;
        checkIsTransitionOfCare.Checked = RefAttachCur.IsTransitionOfCare;
        textProc.Text = "";
        if (RefAttachCur.ProcNum != 0)
        {
            Procedure proc = Procedures.GetOneProc(RefAttachCur.ProcNum, false);
            textProc.Text = Procedures.getDescription(proc);
        }
         
        if (RefAttachCur.DateProcComplete.Year < 1880)
        {
            textDateProcCompleted.Text = "";
        }
        else
        {
            textDateProcCompleted.Text = RefAttachCur.DateProcComplete.ToShortDateString();
        } 
    }

    private void fillSheets() throws Exception {
        SheetList = Sheets.GetReferralSlips(RefAttachCur.PatNum, RefAttachCur.ReferralNum);
        listSheets.Items.Clear();
        for (int i = 0;i < SheetList.Count;i++)
        {
            listSheets.Items.Add(SheetList[i].DateTimeSheet.ToShortDateString());
        }
    }

    private void butEdit_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            dataToCur();
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        Referral referral = Referrals.GetReferral(RefAttachCur.ReferralNum);
        FormReferralEdit FormRE = new FormReferralEdit(referral);
        FormRE.ShowDialog();
        Referrals.refreshCache();
        fillData();
    }

    private void butChangeReferral_Click(Object sender, EventArgs e) throws Exception {
        FormReferralSelect FormRS = new FormReferralSelect();
        FormRS.IsSelectionMode = true;
        FormRS.ShowDialog();
        if (FormRS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        RefAttachCur.ReferralNum = FormRS.SelectedReferral.ReferralNum;
        fillData();
    }

    private void listSheets_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listSheets.SelectedIndex == -1)
        {
            return ;
        }
         
        Sheet sheet = SheetList[listSheets.SelectedIndex];
        SheetFields.getFieldsAndParameters(sheet);
        FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
        FormS.ShowDialog();
        fillSheets();
    }

    /**
    * Surround with try-catch.  Attempts to take the data on the form and set the values of RefAttachCur.
    */
    private void dataToCur() throws Exception {
        if (!StringSupport.equals(textOrder.errorProvider1.GetError(textOrder), "") || !StringSupport.equals(textRefDate.errorProvider1.GetError(textRefDate), "") || !StringSupport.equals(textDateProcCompleted.errorProvider1.GetError(textDateProcCompleted), ""))
        {
            throw new ApplicationException(Lan.g(this,"Please fix data entry errors first."));
        }
         
        if (listFromTo.SelectedIndex == 0)
        {
            RefAttachCur.IsFrom = true;
            RefAttachCur.ProvNum = 0;
        }
        else
        {
            RefAttachCur.IsFrom = false;
            RefAttachCur.ProvNum = _provNumSelected;
        } 
        RefAttachCur.RefDate = PIn.Date(textRefDate.Text);
        RefAttachCur.ItemOrder = PIn.Int(textOrder.Text);
        RefAttachCur.RefToStatus = (ReferralToStatus)comboRefToStatus.SelectedIndex;
        RefAttachCur.Note = textNote.Text;
        RefAttachCur.IsTransitionOfCare = checkIsTransitionOfCare.Checked;
        RefAttachCur.DateProcComplete = PIn.Date(textDateProcCompleted.Text);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.RefAttachDelete))
        {
            return ;
        }
         
        if (!MsgBox.show(this,true,"Detach Referral?"))
        {
            return ;
        }
         
        SecurityLogs.MakeLogEntry(Permissions.RefAttachDelete, RefAttachCur.PatNum, "Referral attachment deleted for " + Referrals.GetNameFL(RefAttachCur.ReferralNum));
        RefAttaches.Delete(RefAttachCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            //this is an old pattern
            dataToCur();
            if (IsNew)
            {
                RefAttaches.Insert(RefAttachCur);
            }
            else
            {
                RefAttaches.Update(RefAttachCur);
            } 
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

    private void comboProvNum_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        _provNumSelected = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
    }

    private void butPickProv_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formP = new FormProviderPick();
        if (comboProvNum.SelectedIndex > -1)
        {
            //Initial formP selection if selected prov is not hidden.
            formP.SelectedProvNum = _provNumSelected;
        }
         
        formP.ShowDialog();
        if (formP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvNum.SelectedIndex = Providers.getIndex(formP.SelectedProvNum);
        _provNumSelected = formP.SelectedProvNum;
    }

    private void butNoneProv_Click(Object sender, EventArgs e) throws Exception {
        _provNumSelected = 0;
        comboProvNum.SelectedIndex = -1;
    }

    private void listFromTo_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (listFromTo.SelectedIndex == 0)
        {
            //Hide referring provider
            butNoneProv.Visible = false;
            butPickProv.Visible = false;
            comboProvNum.Visible = false;
            label10.Visible = false;
        }
        else
        {
            //Show referring provider
            butNoneProv.Visible = true;
            butPickProv.Visible = true;
            comboProvNum.Visible = true;
            label10.Visible = true;
        } 
    }

}


