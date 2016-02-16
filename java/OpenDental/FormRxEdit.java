//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:44 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPharmacies;
import OpenDental.FormProcGroup;
import OpenDental.FormProviderPick;
import OpenDental.FormRpPrintPreview;
import OpenDental.FormRxEdit;
import OpenDental.FormSheetFillEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.SheetFiller;
import OpenDental.SheetPrinting;
import OpenDental.SheetUtil;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.RxSendStatus;
import OpenDentBusiness.Security;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class FormRxEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textSig = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDisp = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textRefills = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDrug = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDate;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butDelete;
    private OpenDental.ODtextBox textNotes;
    /**
    * 
    */
    public FormRpPrintPreview pView = new FormRpPrintPreview();
    private Patient PatCur;
    private Label label8 = new Label();
    private OpenDental.UI.Button butPick;
    private TextBox textPharmacy = new TextBox();
    private CheckBox checkControlled = new CheckBox();
    private OpenDental.UI.Button butView;
    private Label labelView = new Label();
    //private User user;
    private RxPat RxPatCur;
    private Label label9 = new Label();
    private ComboBox comboSendStatus = new ComboBox();
    private TextBox textDosageCode = new TextBox();
    private Label labelDosageCode = new Label();
    private ComboBox comboProvNum = new ComboBox();
    private OpenDental.UI.Button butPickProv;
    private Label label7 = new Label();
    /**
    * If the Rx has already been printed, this will contain the archived sheet. The print button will be not visible, and the view button will be visible.
    */
    private Sheet sheet;
    private Label labelCPOE = new Label();
    private long _provNumSelected = new long();
    /**
    * 
    */
    public FormRxEdit(Patient patCur, RxPat rxPatCur) throws Exception {
        //){//
        initializeComponent();
        RxPatCur = rxPatCur;
        PatCur = patCur;
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRxEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textSig = new System.Windows.Forms.TextBox();
        this.textDisp = new System.Windows.Forms.TextBox();
        this.textRefills = new System.Windows.Forms.TextBox();
        this.textDrug = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.butPrint = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.label8 = new System.Windows.Forms.Label();
        this.butPick = new OpenDental.UI.Button();
        this.textPharmacy = new System.Windows.Forms.TextBox();
        this.checkControlled = new System.Windows.Forms.CheckBox();
        this.butView = new OpenDental.UI.Button();
        this.labelView = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.comboSendStatus = new System.Windows.Forms.ComboBox();
        this.textDosageCode = new System.Windows.Forms.TextBox();
        this.labelDosageCode = new System.Windows.Forms.Label();
        this.comboProvNum = new System.Windows.Forms.ComboBox();
        this.butPickProv = new OpenDental.UI.Button();
        this.label7 = new System.Windows.Forms.Label();
        this.textNotes = new OpenDental.ODtextBox();
        this.labelCPOE = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(543, 443);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
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
        this.butOK.Location = new System.Drawing.Point(543, 403);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 8;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textSig
        //
        this.textSig.AcceptsReturn = true;
        this.textSig.Location = new System.Drawing.Point(138, 105);
        this.textSig.Multiline = true;
        this.textSig.Name = "textSig";
        this.textSig.Size = new System.Drawing.Size(254, 44);
        this.textSig.TabIndex = 2;
        //
        // textDisp
        //
        this.textDisp.Location = new System.Drawing.Point(138, 154);
        this.textDisp.Name = "textDisp";
        this.textDisp.Size = new System.Drawing.Size(114, 20);
        this.textDisp.TabIndex = 3;
        //
        // textRefills
        //
        this.textRefills.Location = new System.Drawing.Point(138, 178);
        this.textRefills.Name = "textRefills";
        this.textRefills.Size = new System.Drawing.Size(114, 20);
        this.textRefills.TabIndex = 4;
        //
        // textDrug
        //
        this.textDrug.Location = new System.Drawing.Point(138, 80);
        this.textDrug.Name = "textDrug";
        this.textDrug.Size = new System.Drawing.Size(254, 20);
        this.textDrug.TabIndex = 1;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(49, 109);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(89, 14);
        this.label6.TabIndex = 17;
        this.label6.Text = "Sig";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(39, 158);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(99, 14);
        this.label5.TabIndex = 16;
        this.label5.Text = "Disp";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(39, 182);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(99, 14);
        this.label4.TabIndex = 15;
        this.label4.Text = "Refills";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(31, 253);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(107, 36);
        this.label3.TabIndex = 14;
        this.label3.Text = "Notes (will not show on printout)";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(45, 82);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(93, 14);
        this.label1.TabIndex = 13;
        this.label1.Text = "Drug";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(34, 37);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(105, 14);
        this.label2.TabIndex = 25;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(138, 33);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 0;
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(289, 443);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(81, 24);
        this.butPrint.TabIndex = 29;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
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
        this.butDelete.Location = new System.Drawing.Point(20, 443);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(88, 24);
        this.butDelete.TabIndex = 30;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(39, 370);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(99, 14);
        this.label8.TabIndex = 32;
        this.label8.Text = "Pharmacy";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butPick
        //
        this.butPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick.setAutosize(true);
        this.butPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick.setCornerRadius(4F);
        this.butPick.Location = new System.Drawing.Point(338, 365);
        this.butPick.Name = "butPick";
        this.butPick.Size = new System.Drawing.Size(58, 23);
        this.butPick.TabIndex = 65;
        this.butPick.TabStop = false;
        this.butPick.Text = "Pick";
        this.butPick.Click += new System.EventHandler(this.butPick_Click);
        //
        // textPharmacy
        //
        this.textPharmacy.AcceptsReturn = true;
        this.textPharmacy.Location = new System.Drawing.Point(138, 367);
        this.textPharmacy.Name = "textPharmacy";
        this.textPharmacy.ReadOnly = true;
        this.textPharmacy.Size = new System.Drawing.Size(198, 20);
        this.textPharmacy.TabIndex = 64;
        //
        // checkControlled
        //
        this.checkControlled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkControlled.Location = new System.Drawing.Point(7, 57);
        this.checkControlled.Name = "checkControlled";
        this.checkControlled.Size = new System.Drawing.Size(145, 20);
        this.checkControlled.TabIndex = 66;
        this.checkControlled.Text = "Controlled Substance";
        this.checkControlled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkControlled.UseVisualStyleBackColor = true;
        //
        // butView
        //
        this.butView.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butView.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butView.setAutosize(true);
        this.butView.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butView.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butView.setCornerRadius(4F);
        this.butView.Image = Resources.getprintPreview20();
        this.butView.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butView.Location = new System.Drawing.Point(376, 443);
        this.butView.Name = "butView";
        this.butView.Size = new System.Drawing.Size(81, 24);
        this.butView.TabIndex = 244;
        this.butView.Text = "View";
        this.butView.Click += new System.EventHandler(this.butView_Click);
        //
        // labelView
        //
        this.labelView.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.labelView.Location = new System.Drawing.Point(417, 470);
        this.labelView.Name = "labelView";
        this.labelView.Size = new System.Drawing.Size(199, 14);
        this.labelView.TabIndex = 245;
        this.labelView.Text = "This Rx has already been printed.";
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(39, 393);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(99, 14);
        this.label9.TabIndex = 250;
        this.label9.Text = "Send Status";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboSendStatus
        //
        this.comboSendStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSendStatus.FormattingEnabled = true;
        this.comboSendStatus.Location = new System.Drawing.Point(138, 390);
        this.comboSendStatus.Name = "comboSendStatus";
        this.comboSendStatus.Size = new System.Drawing.Size(198, 21);
        this.comboSendStatus.TabIndex = 251;
        //
        // textDosageCode
        //
        this.textDosageCode.Location = new System.Drawing.Point(138, 227);
        this.textDosageCode.Name = "textDosageCode";
        this.textDosageCode.Size = new System.Drawing.Size(114, 20);
        this.textDosageCode.TabIndex = 256;
        //
        // labelDosageCode
        //
        this.labelDosageCode.Location = new System.Drawing.Point(44, 231);
        this.labelDosageCode.Name = "labelDosageCode";
        this.labelDosageCode.Size = new System.Drawing.Size(94, 14);
        this.labelDosageCode.TabIndex = 257;
        this.labelDosageCode.Text = "Dosage Code";
        this.labelDosageCode.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboProvNum
        //
        this.comboProvNum.Location = new System.Drawing.Point(138, 202);
        this.comboProvNum.MaxDropDownItems = 30;
        this.comboProvNum.Name = "comboProvNum";
        this.comboProvNum.Size = new System.Drawing.Size(254, 21);
        this.comboProvNum.TabIndex = 259;
        this.comboProvNum.SelectionChangeCommitted += new System.EventHandler(this.comboProvNum_SelectionChangeCommitted);
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(394, 202);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 21);
        this.butPickProv.TabIndex = 260;
        this.butPickProv.Text = "...";
        this.butPickProv.Click += new System.EventHandler(this.butPickProv_Click);
        //
        // label7
        //
        this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label7.Location = new System.Drawing.Point(37, 206);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(100, 14);
        this.label7.TabIndex = 258;
        this.label7.Text = "Provider";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNotes
        //
        this.textNotes.AcceptsTab = true;
        this.textNotes.DetectUrls = false;
        this.textNotes.Location = new System.Drawing.Point(138, 252);
        this.textNotes.Name = "textNotes";
        this.textNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.Rx);
        this.textNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNotes.Size = new System.Drawing.Size(373, 111);
        this.textNotes.TabIndex = 31;
        this.textNotes.Text = "";
        //
        // labelCPOE
        //
        this.labelCPOE.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelCPOE.Location = new System.Drawing.Point(61, 9);
        this.labelCPOE.Name = "labelCPOE";
        this.labelCPOE.Size = new System.Drawing.Size(249, 14);
        this.labelCPOE.TabIndex = 261;
        this.labelCPOE.Text = "Computerized Provider Order Entry";
        this.labelCPOE.TextAlign = System.Drawing.ContentAlignment.TopRight;
        this.labelCPOE.Visible = false;
        //
        // FormRxEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(635, 491);
        this.Controls.Add(this.labelCPOE);
        this.Controls.Add(this.comboProvNum);
        this.Controls.Add(this.butPickProv);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textDosageCode);
        this.Controls.Add(this.labelDosageCode);
        this.Controls.Add(this.comboSendStatus);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.labelView);
        this.Controls.Add(this.butView);
        this.Controls.Add(this.checkControlled);
        this.Controls.Add(this.butPick);
        this.Controls.Add(this.textPharmacy);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.textSig);
        this.Controls.Add(this.textDisp);
        this.Controls.Add(this.textRefills);
        this.Controls.Add(this.textDrug);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRxEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Rx";
        this.Load += new System.EventHandler(this.FormRxEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRxEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            butView.Visible = false;
            labelView.Visible = false;
            sheet = null;
            if (PrefC.getBool(PrefName.ShowFeatureEhr) && Security.getCurUser().ProvNum != 0)
            {
                //Is CPOE
                labelCPOE.Visible = true;
                comboProvNum.Enabled = false;
                butPickProv.Enabled = false;
                RxPatCur.ProvNum = Security.getCurUser().ProvNum;
            }
             
        }
        else
        {
            sheet = Sheets.getRx(RxPatCur.PatNum,RxPatCur.RxNum);
            if (sheet == null)
            {
                butView.Visible = false;
                labelView.Visible = false;
            }
            else
            {
                butPrint.Visible = false;
            } 
        } 
        //security is handled on the Rx button click in the Chart module
        _provNumSelected = RxPatCur.ProvNum;
        comboProvNum.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNum.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            //Only visible provs added to combobox.
            if (ProviderC.getListShort()[i].ProvNum == RxPatCur.ProvNum)
            {
                comboProvNum.SelectedIndex = i;
            }
             
        }
        //Sets combo text too.
        if (_provNumSelected == 0)
        {
            //Is new
            comboProvNum.SelectedIndex = 0;
            _provNumSelected = ProviderC.getListShort()[0].ProvNum;
        }
         
        if (comboProvNum.SelectedIndex == -1)
        {
            //The provider exists but is hidden
            comboProvNum.Text = Providers.getLongDesc(_provNumSelected);
        }
         
        //Appends "(hidden)" to the end of the long description.
        textDate.Text = RxPatCur.RxDate.ToString("d");
        checkControlled.Checked = RxPatCur.IsControlled;
        for (int i = 0;i < Enum.GetNames(RxSendStatus.class).Length;i++)
        {
            comboSendStatus.Items.Add(Enum.GetNames(RxSendStatus.class)[i]);
        }
        comboSendStatus.SelectedIndex = ((Enum)RxPatCur.SendStatus).ordinal();
        textDrug.Text = RxPatCur.Drug;
        textSig.Text = RxPatCur.Sig;
        textDisp.Text = RxPatCur.Disp;
        textRefills.Text = RxPatCur.Refills;
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            textDosageCode.Text = RxPatCur.DosageCode;
        }
        else
        {
            labelDosageCode.Visible = false;
            textDosageCode.Visible = false;
        } 
        textNotes.Text = RxPatCur.Notes;
        textPharmacy.Text = Pharmacies.getDescription(RxPatCur.PharmacyNum);
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

    private void butPick_Click(Object sender, EventArgs e) throws Exception {
        FormPharmacies FormP = new FormPharmacies();
        FormP.IsSelectionMode = true;
        FormP.SelectedPharmacyNum = RxPatCur.PharmacyNum;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        RxPatCur.PharmacyNum = FormP.SelectedPharmacyNum;
        textPharmacy.Text = Pharmacies.getDescription(RxPatCur.PharmacyNum);
    }

    /**
    * Attempts to save, returning true if successful.
    */
    private boolean saveRx() throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            //|| textRxNorm.errorProvider1.GetError(textRxNorm)!=""
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return false;
        }
         
        RxPatCur.ProvNum = _provNumSelected;
        RxPatCur.RxDate = PIn.Date(textDate.Text);
        RxPatCur.Drug = textDrug.Text;
        RxPatCur.IsControlled = checkControlled.Checked;
        RxPatCur.Sig = textSig.Text;
        RxPatCur.Disp = textDisp.Text;
        RxPatCur.Refills = textRefills.Text;
        RxPatCur.DosageCode = textDosageCode.Text;
        RxPatCur.Notes = textNotes.Text;
        RxPatCur.SendStatus = (RxSendStatus)comboSendStatus.SelectedIndex;
        //pharmacy is set when using pick button.
        if (IsNew)
        {
            RxPatCur.RxNum = RxPats.insert(RxPatCur);
            //SecurityLogs.MakeLogEntry("Prescription Create",RxPats.cmd.CommandText,user);
            if (FormProcGroup.IsOpen)
            {
                FormProcGroup.RxNum = RxPatCur.RxNum;
            }
             
        }
        else
        {
            RxPats.update(RxPatCur);
        } 
        //SecurityLogs.MakeLogEntry("Prescription Edit",RxPats.cmd.CommandText,user);
        IsNew = false;
        return true;
    }

    //so that we can save it again after printing if needed.
    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete Prescription?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        RxPats.delete(RxPatCur.RxNum);
        DialogResult = DialogResult.OK;
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        //only visible if sheet==null.
        if (comboSendStatus.SelectedIndex == ((Enum)RxSendStatus.InElectQueue).ordinal() || comboSendStatus.SelectedIndex == ((Enum)RxSendStatus.SentElect).ordinal())
        {
        }
        else
        {
            //do not change status
            comboSendStatus.SelectedIndex = ((Enum)RxSendStatus.Printed).ordinal();
        } 
        if (!saveRx())
        {
            return ;
        }
         
        SheetDef sheetDef;
        List<SheetDef> customSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.Rx);
        if (customSheetDefs.Count == 0)
        {
            sheetDef = SheetsInternal.getSheetDef(SheetInternalType.Rx);
        }
        else
        {
            sheetDef = customSheetDefs[0];
            SheetDefs.getFieldsAndParameters(sheetDef);
        } 
        sheet = SheetUtil.createSheet(sheetDef,PatCur.PatNum);
        SheetParameter.setParameter(sheet,"RxNum",RxPatCur.RxNum);
        SheetFiller.fillFields(sheet);
        SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
        SheetPrinting.printRx(sheet,RxPatCur.IsControlled);
        DialogResult = DialogResult.OK;
    }

    private void butView_Click(Object sender, EventArgs e) throws Exception {
        //only visible if there is already a sheet.
        if (!saveRx())
        {
            return ;
        }
         
        SheetFields.getFieldsAndParameters(sheet);
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        if (FormSF.DialogResult == DialogResult.OK)
        {
            DialogResult = DialogResult.OK;
        }
         
    }

    //if user clicked cancel, then we can just stay in this form.
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveRx())
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


