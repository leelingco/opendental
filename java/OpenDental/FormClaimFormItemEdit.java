//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:48 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormClaimFormItemEdit;
import OpenDental.Lan;
import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.ClaimFormItems;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClaimFormItemEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelImageFileName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textImageFileName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelFieldName = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listFieldName = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textFormatString = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelFormatString = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public String[] FieldNames = new String[]();
    private OpenDental.UI.Button butDelete;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * This is the claimformitem that is being currently edited in this window.
    */
    public ClaimFormItem CFIcur;
    /**
    * 
    */
    public FormClaimFormItemEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimFormItemEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelImageFileName = new System.Windows.Forms.Label();
        this.textImageFileName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.labelFieldName = new System.Windows.Forms.Label();
        this.listFieldName = new System.Windows.Forms.ListBox();
        this.textFormatString = new System.Windows.Forms.TextBox();
        this.labelFormatString = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(838, 605);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(838, 564);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelImageFileName
        //
        this.labelImageFileName.Location = new System.Drawing.Point(25, 22);
        this.labelImageFileName.Name = "labelImageFileName";
        this.labelImageFileName.Size = new System.Drawing.Size(156, 16);
        this.labelImageFileName.TabIndex = 2;
        this.labelImageFileName.Text = "Image File Name";
        //
        // textImageFileName
        //
        this.textImageFileName.Location = new System.Drawing.Point(26, 40);
        this.textImageFileName.Name = "textImageFileName";
        this.textImageFileName.Size = new System.Drawing.Size(211, 20);
        this.textImageFileName.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(25, 67);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(209, 57);
        this.label2.TabIndex = 4;
        this.label2.Text = "This file must be present in the OpenDentalData folder.  It should be a jpg, gif," + " or emf.";
        //
        // labelFieldName
        //
        this.labelFieldName.Location = new System.Drawing.Point(255, 14);
        this.labelFieldName.Name = "labelFieldName";
        this.labelFieldName.Size = new System.Drawing.Size(156, 16);
        this.labelFieldName.TabIndex = 5;
        this.labelFieldName.Text = "or Field Name";
        //
        // listFieldName
        //
        this.listFieldName.Location = new System.Drawing.Point(254, 31);
        this.listFieldName.MultiColumn = true;
        this.listFieldName.Name = "listFieldName";
        this.listFieldName.Size = new System.Drawing.Size(560, 602);
        this.listFieldName.TabIndex = 6;
        this.listFieldName.DoubleClick += new System.EventHandler(this.listFieldName_DoubleClick);
        //
        // textFormatString
        //
        this.textFormatString.Location = new System.Drawing.Point(24, 208);
        this.textFormatString.Name = "textFormatString";
        this.textFormatString.Size = new System.Drawing.Size(211, 20);
        this.textFormatString.TabIndex = 8;
        //
        // labelFormatString
        //
        this.labelFormatString.Location = new System.Drawing.Point(24, 135);
        this.labelFormatString.Name = "labelFormatString";
        this.labelFormatString.Size = new System.Drawing.Size(210, 68);
        this.labelFormatString.TabIndex = 7;
        this.labelFormatString.Text = "Format String.  All dates must have a format.  Valid entries would include MM/dd/" + "yyyy, MM-dd-yy, and M d y as examples.";
        this.labelFormatString.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(29, 600);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(80, 25);
        this.butDelete.TabIndex = 9;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormClaimFormItemEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(939, 646);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textFormatString);
        this.Controls.Add(this.textImageFileName);
        this.Controls.Add(this.labelFormatString);
        this.Controls.Add(this.listFieldName);
        this.Controls.Add(this.labelFieldName);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.labelImageFileName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimFormItemEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Claim Form Item";
        this.Load += new System.EventHandler(this.FormClaimFormItemEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimFormItemEdit_Load(Object sender, System.EventArgs e) throws Exception {
        fillFieldNames();
        fillForm();
    }

    /**
    * This is called externally from Renaissance to error check each of the supplied fieldNames
    */
    public void fillFieldNames() throws Exception {
        FieldNames = new String[]{ "FixedText", "IsPreAuth", "IsStandardClaim", "ShowPreauthorizationIfPreauth", "IsMedicaidClaim", "IsGroupHealthPlan", "PreAuthString", "PriorAuthString", "PriInsCarrierName", "PriInsAddress", "PriInsAddress2", "PriInsAddressComplete", "PriInsCity", "PriInsST", "PriInsZip", "OtherInsExists", "OtherInsNotExists", "OtherInsExistsDent", "OtherInsExistsMed", "OtherInsSubscrLastFirst", "OtherInsSubscrDOB", "OtherInsSubscrIsMale", "OtherInsSubscrIsFemale", "OtherInsSubscrID", "OtherInsGroupNum", "OtherInsRelatIsSelf", "OtherInsRelatIsSpouse", "OtherInsRelatIsChild", "OtherInsRelatIsOther", "OtherInsCarrierName", "OtherInsAddress", "OtherInsCity", "OtherInsST", "OtherInsZip", "SubscrLastFirst", "SubscrAddress", "SubscrAddress2", "SubscrAddressComplete", "SubscrCity", "SubscrST", "SubscrZip", "SubscrPhone", "SubscrDOB", "SubscrIsMale", "SubscrIsFemale", "SubscrIsMarried", "SubscrIsSingle", "SubscrID", "SubscrIDStrict", "SubscrIsFTStudent", "SubscrIsPTStudent", "SubscrGender", "GroupNum", "GroupName", "DivisionNo", "EmployerName", "RelatIsSelf", "RelatIsSpouse", "RelatIsChild", "RelatIsOther", "Relationship", "IsFTStudent", "IsPTStudent", "IsStudent", "CollegeName", "PatientLastFirst", "PatientFirstMiddleLast", "PatientFirstName", "PatientMiddleName", "PatientLastName", "PatientAddress", "PatientAddress2", "PatientAddressComplete", "PatientCity", "PatientST", "PatientZip", "PatientPhone", "PatientDOB", "PatientIsMale", "PatientIsFemale", "PatientGender", "PatientGenderLetter", "PatientIsMarried", "PatientIsSingle", "PatIDFromPatPlan", "PatientSSN", "PatientMedicaidID", "PatientID-MedicaidOrSSN", "PatientPatNum", "PatientChartNum", "Diagnosis1", "Diagnosis2", "Diagnosis3", "Diagnosis4", "P1Date", "P1Area", "P1System", "P1ToothNumber", "P1Surface", "P1Code", "P1Description", "P1Fee", "P1TreatDentMedicaidID", "P1PlaceNumericCode", "P1Diagnosis", "P1Lab", "P1FeeMinusLab", "P1ToothNumOrArea", "P1TreatProvNPI", "P1RevCode", "P1CodeMod1", "P1CodeMod2", "P1CodeMod3", "P1CodeMod4", "P1UnitQty", "P1CodeAndMods", "P2Date", "P2Area", "P2System", "P2ToothNumber", "P2Surface", "P2Code", "P2Description", "P2Fee", "P2TreatDentMedicaidID", "P2PlaceNumericCode", "P2Diagnosis", "P2Lab", "P2FeeMinusLab", "P2ToothNumOrArea", "P2TreatProvNPI", "P2RevCode", "P2CodeMod1", "P2CodeMod2", "P2CodeMod3", "P2CodeMod4", "P2UnitQty", "P2CodeAndMods", "P3Date", "P3Area", "P3System", "P3ToothNumber", "P3Surface", "P3Code", "P3Description", "P3Fee", "P3TreatDentMedicaidID", "P3PlaceNumericCode", "P3Diagnosis", "P3Lab", "P3FeeMinusLab", "P3ToothNumOrArea", "P3TreatProvNPI", "P3RevCode", "P3CodeMod1", "P3CodeMod2", "P3CodeMod3", "P3CodeMod4", "P3UnitQty", "P3CodeAndMods", "P4Date", "P4Area", "P4System", "P4ToothNumber", "P4Surface", "P4Code", "P4Description", "P4Fee", "P4TreatDentMedicaidID", "P4PlaceNumericCode", "P4Diagnosis", "P4Lab", "P4FeeMinusLab", "P4ToothNumOrArea", "P4TreatProvNPI", "P4RevCode", "P4CodeMod1", "P4CodeMod2", "P4CodeMod3", "P4CodeMod4", "P4UnitQty", "P4CodeAndMods", "P5Date", "P5Area", "P5System", "P5ToothNumber", "P5Surface", "P5Code", "P5Description", "P5Fee", "P5TreatDentMedicaidID", "P5PlaceNumericCode", "P5Diagnosis", "P5Lab", "P5FeeMinusLab", "P5ToothNumOrArea", "P5TreatProvNPI", "P5RevCode", "P5CodeMod1", "P5CodeMod2", "P5CodeMod3", "P5CodeMod4", "P5UnitQty", "P5CodeAndMods", "P6Date", "P6Area", "P6System", "P6ToothNumber", "P6Surface", "P6Code", "P6Description", "P6Fee", "P6TreatDentMedicaidID", "P6PlaceNumericCode", "P6Diagnosis", "P6Lab", "P6FeeMinusLab", "P6ToothNumOrArea", "P6TreatProvNPI", "P6RevCode", "P6CodeMod1", "P6CodeMod2", "P6CodeMod3", "P6CodeMod4", "P6UnitQty", "P6CodeAndMods", "P7Date", "P7Area", "P7System", "P7ToothNumber", "P7Surface", "P7Code", "P7Description", "P7Fee", "P7TreatDentMedicaidID", "P7PlaceNumericCode", "P7Diagnosis", "P7Lab", "P7FeeMinusLab", "P7ToothNumOrArea", "P7TreatProvNPI", "P7RevCode", "P7CodeMod1", "P7CodeMod2", "P7CodeMod3", "P7CodeMod4", "P7UnitQty", "P7CodeAndMods", "P8Date", "P8Area", "P8System", "P8ToothNumber", "P8Surface", "P8Code", "P8Description", "P8Fee", "P8TreatDentMedicaidID", "P8PlaceNumericCode", "P8Diagnosis", "P8Lab", "P8FeeMinusLab", "P8ToothNumOrArea", "P8TreatProvNPI", "P8RevCode", "P8CodeMod1", "P8CodeMod2", "P8CodeMod3", "P8CodeMod4", "P8UnitQty", "P8CodeAndMods", "P9Date", "P9Area", "P9System", "P9ToothNumber", "P9Surface", "P9Code", "P9Description", "P9Fee", "P9TreatDentMedicaidID", "P9PlaceNumericCode", "P9Diagnosis", "P9Lab", "P9FeeMinusLab", "P9ToothNumOrArea", "P9TreatProvNPI", "P9RevCode", "P9CodeMod1", "P9CodeMod2", "P9CodeMod3", "P9CodeMod4", "P9UnitQty", "P9CodeAndMods", "P10Date", "P10Area", "P10System", "P10ToothNumber", "P10Surface", "P10Code", "P10Description", "P10Fee", "P10TreatDentMedicaidID", "P10PlaceNumericCode", "P10Diagnosis", "P10Lab", "P10FeeMinusLab", "P10ToothNumOrArea", "P10TreatProvNPI", "P10RevCode", "P10CodeMod1", "P10CodeMod2", "P10CodeMod3", "P10CodeMod4", "P10UnitQty", "P10CodeAndMods", "TotalFee", "Miss1", "Miss2", "Miss3", "Miss4", "Miss5", "Miss6", "Miss7", "Miss8", "Miss9", "Miss10", "Miss11", "Miss12", "Miss13", "Miss14", "Miss15", "Miss16", "Miss17", "Miss18", "Miss19", "Miss20", "Miss21", "Miss22", "Miss23", "Miss24", "Miss25", "Miss26", "Miss27", "Miss28", "Miss29", "Miss30", "Miss31", "Miss32", "Remarks", "PatientRelease", "PatientReleaseDate", "PatientAssignment", "PatientAssignmentDate", "PlaceIsOffice", "PlaceIsHospADA2002", "PlaceIsExtCareFacilityADA2002", "PlaceIsOtherADA2002", "PlaceIsInpatHosp", "PlaceIsOutpatHosp", "PlaceIsAdultLivCareFac", "PlaceIsSkilledNursFac", "PlaceIsPatientsHome", "PlaceIsOtherLocation", "PlaceNumericCode", "IsRadiographsAttached", "RadiographsNumAttached", "RadiographsNotAttached", "IsEnclosuresAttached", "AttachedImagesNum", "AttachedModelsNum", "IsNotOrtho", "IsOrtho", "DateOrthoPlaced", "MonthsOrthoRemaining", "IsNotProsth", "IsInitialProsth", "IsNotReplacementProsth", "IsReplacementProsth", "DatePriorProsthPlaced", "IsOccupational", "IsNotOccupational", "IsAutoAccident", "IsNotAutoAccident", "IsOtherAccident", "IsNotOtherAccident", "IsNotAccident", "IsAccident", "AccidentDate", "AccidentST", "BillingDentist", "BillingDentistMedicaidID", "BillingDentistProviderID", "BillingDentistNPI", "BillingDentistLicenseNum", "BillingDentistSSNorTIN", "BillingDentistNumIsSSN", "BillingDentistNumIsTIN", "BillingDentistPh123", "BillingDentistPh456", "BillingDentistPh78910", "BillingDentistPhoneFormatted", "BillingDentistPhoneRaw", "PayToDentistAddress", "PayToDentistAddress2", "PayToDentistCity", "PayToDentistST", "PayToDentistZip", "TreatingDentistFName", "TreatingDentistLName", "TreatingDentistSignature", "TreatingDentistSigDate", "TreatingDentistMedicaidID", "TreatingDentistProviderID", "TreatingDentistNPI", "TreatingDentistLicense", "TreatingDentistAddress", "TreatingDentistCity", "TreatingDentistST", "TreatingDentistZip", "TreatingDentistPh123", "TreatingDentistPh456", "TreatingDentistPh78910", "TreatingProviderSpecialty", "ReferringProvNPI", "ReferringProvNameFL", "DateService", "TotalPages", "MedInsCrossoverIndicator", "MedInsAName", "MedInsAPlanID", "MedInsARelInfo", "MedInsAAssignBen", "MedInsAPriorPmt", "MedInsAAmtDue", "MedInsAOtherProvID", "MedInsAInsuredName", "MedInsARelation", "MedInsAInsuredID", "MedInsAGroupName", "MedInsAGroupNum", "MedInsAAuthCode", "MedInsAEmployer", "MedInsBName", "MedInsBPlanID", "MedInsBRelInfo", "MedInsBAssignBen", "MedInsBPriorPmt", "MedInsBAmtDue", "MedInsBOtherProvID", "MedInsBInsuredName", "MedInsBRelation", "MedInsBInsuredID", "MedInsBGroupName", "MedInsBGroupNum", "MedInsBAuthCode", "MedInsBEmployer", "MedInsCName", "MedInsCPlanID", "MedInsCRelInfo", "MedInsCAssignBen", "MedInsCPriorPmt", "MedInsCAmtDue", "MedInsCOtherProvID", "MedInsCInsuredName", "MedInsCRelation", "MedInsCInsuredID", "MedInsCGroupName", "MedInsCGroupNum", "MedInsCAuthCode", "MedInsCEmployer", "MedValCode39a", "MedValAmount39a", "MedValCode39b", "MedValAmount39b", "MedValCode39c", "MedValAmount39c", "MedValCode39d", "MedValAmount39d", "MedValCode40a", "MedValAmount40a", "MedValCode40b", "MedValAmount40b", "MedValCode40c", "MedValAmount40c", "MedValCode40d", "MedValAmount40d", "MedValCode41a", "MedValAmount41a", "MedValCode41b", "MedValAmount41b", "MedValCode41c", "MedValAmount41c", "MedValCode41d", "MedValAmount41d", "MedConditionCode18", "MedConditionCode19", "MedConditionCode20", "MedConditionCode21", "MedConditionCode22", "MedConditionCode23", "MedConditionCode24", "MedConditionCode25", "MedConditionCode26", "MedConditionCode27", "MedConditionCode28", "MedUniformBillType", "MedAdmissionTypeCode", "MedAdmissionSourceCode", "MedPatientStatusCode", "MedAccidentCode" };
    }

    //Dependant Code in Canada
    //1
    //"P1UnitCode",
    //2
    //"P2UnitCode",
    //3
    //"P3UnitCode",
    //4
    //"P4UnitCode",
    //5
    //"P5UnitCode",
    //6
    //"P6UnitCode",
    //7
    //"P7UnitCode",
    //8
    //"P8UnitCode",
    //9
    //"P9UnitCode",
    //10
    //"P10UnitCode",
    //reason for replacement (ADA2000)
    //"BillingDentistAddress",
    //"BillingDentistAddress2",
    //"BillingDentistCity",
    //"BillingDentistST",
    //"BillingDentistZip",
    private void fillForm() throws Exception {
        textImageFileName.Text = CFIcur.ImageFileName;
        textFormatString.Text = CFIcur.FormatString;
        listFieldName.Items.Clear();
        for (int i = 0;i < FieldNames.Length;i++)
        {
            listFieldName.Items.Add(FieldNames[i]);
            if (StringSupport.equals(FieldNames[i], CFIcur.FieldName))
            {
                listFieldName.SelectedIndex = i;
            }
             
        }
    }

    private void listFieldName_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        saveAndClose();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        ClaimFormItems.delete(CFIcur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        saveAndClose();
    }

    private void saveAndClose() throws Exception {
        CFIcur.ImageFileName = textImageFileName.Text;
        CFIcur.FormatString = textFormatString.Text;
        if (listFieldName.SelectedIndex == -1)
        {
            CFIcur.FieldName = "";
        }
        else
        {
            CFIcur.FieldName = FieldNames[listFieldName.SelectedIndex];
        } 
        if (IsNew)
            ClaimFormItems.insert(CFIcur);
        else
            ClaimFormItems.update(CFIcur); 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


