//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormInsPlans;
import OpenDental.FormZipCodeEdit;
import OpenDental.FormZipSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.ZipCode;
import OpenDental.ZipCodes;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CustReference;
import OpenDentBusiness.CustReferences;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Employers;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TelephoneNumbers;

public class FormPatientAddAll  extends Form 
{

    public String LName = new String();
    public String FName = new String();
    public long SelectedPatNum = new long();
    private String mostRecentLName = new String();
    private List<Referral> similarReferrals = new List<Referral>();
    private String referralOriginal = new String();
    private System.Windows.Forms.ListBox listReferral = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListReferral = new boolean();
    private Referral selectedReferral;
    //private int selectedSubscriberIndex1;
    /**
    * displayed from within code, not designer
    */
    private System.Windows.Forms.ListBox listEmps1 = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListEmps1 = new boolean();
    private String empOriginal1 = new String();
    /**
    * displayed from within code, not designer
    */
    private System.Windows.Forms.ListBox listEmps2 = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListEmps2 = new boolean();
    private String empOriginal2 = new String();
    private List<Carrier> similarCars1 = new List<Carrier>();
    private String carOriginal1 = new String();
    private System.Windows.Forms.ListBox listCars1 = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListCars1 = new boolean();
    private Carrier selectedCarrier1;
    private List<Carrier> similarCars2 = new List<Carrier>();
    private String carOriginal2 = new String();
    private System.Windows.Forms.ListBox listCars2 = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListCars2 = new boolean();
    private Carrier selectedCarrier2;
    /**
    * If user picks a plan from list, but then changes one of the critical fields, this will be ignored.  Keep in mind that the plan here is just a copy.  It can't be updated, but must instead be inserted.
    */
    private InsPlan selectedPlan1;
    private InsPlan selectedPlan2;
    public FormPatientAddAll() throws Exception {
        initializeComponent();
        Lan.F(this);
        listReferral = new ListBox();
        listReferral.Location = new Point(textReferral.Left, textReferral.Bottom);
        listReferral.Size = new Size(400, 140);
        listReferral.HorizontalScrollbar = true;
        listReferral.Visible = false;
        listReferral.Click += new System.EventHandler(listReferral_Click);
        listReferral.DoubleClick += new System.EventHandler(listReferral_DoubleClick);
        listReferral.MouseEnter += new System.EventHandler(listReferral_MouseEnter);
        listReferral.MouseLeave += new System.EventHandler(listReferral_MouseLeave);
        Controls.Add(listReferral);
        listReferral.BringToFront();
        listEmps1 = new ListBox();
        listEmps1.Location = new Point(groupIns1.Left + textEmployer1.Left, groupIns1.Top + textEmployer1.Bottom);
        listEmps1.Size = new Size(254, 100);
        listEmps1.Visible = false;
        listEmps1.Click += new System.EventHandler(listEmps1_Click);
        listEmps1.DoubleClick += new System.EventHandler(listEmps1_DoubleClick);
        listEmps1.MouseEnter += new System.EventHandler(listEmps1_MouseEnter);
        listEmps1.MouseLeave += new System.EventHandler(listEmps1_MouseLeave);
        Controls.Add(listEmps1);
        listEmps1.BringToFront();
        listEmps2 = new ListBox();
        listEmps2.Location = new Point(groupIns2.Left + textEmployer2.Left, groupIns2.Top + textEmployer2.Bottom);
        listEmps2.Size = new Size(254, 100);
        listEmps2.Visible = false;
        listEmps2.Click += new System.EventHandler(listEmps2_Click);
        listEmps2.DoubleClick += new System.EventHandler(listEmps2_DoubleClick);
        listEmps2.MouseEnter += new System.EventHandler(listEmps2_MouseEnter);
        listEmps2.MouseLeave += new System.EventHandler(listEmps2_MouseLeave);
        Controls.Add(listEmps2);
        listEmps2.BringToFront();
        listCars1 = new ListBox();
        listCars1.Location = new Point(groupIns1.Left + textCarrier1.Left, groupIns1.Top + textCarrier1.Bottom);
        listCars1.Size = new Size(700, 100);
        listCars1.HorizontalScrollbar = true;
        listCars1.Visible = false;
        listCars1.Click += new System.EventHandler(listCars1_Click);
        listCars1.DoubleClick += new System.EventHandler(listCars1_DoubleClick);
        listCars1.MouseEnter += new System.EventHandler(listCars1_MouseEnter);
        listCars1.MouseLeave += new System.EventHandler(listCars1_MouseLeave);
        Controls.Add(listCars1);
        listCars1.BringToFront();
        listCars2 = new ListBox();
        listCars2.Location = new Point(groupIns2.Left + textCarrier2.Left, groupIns2.Top + textCarrier2.Bottom);
        listCars2.Size = new Size(700, 100);
        listCars2.HorizontalScrollbar = true;
        listCars2.Visible = false;
        listCars2.Click += new System.EventHandler(listCars2_Click);
        listCars2.DoubleClick += new System.EventHandler(listCars2_DoubleClick);
        listCars2.MouseEnter += new System.EventHandler(listCars2_MouseEnter);
        listCars2.MouseLeave += new System.EventHandler(listCars2_MouseLeave);
        Controls.Add(listCars2);
        listCars2.BringToFront();
    }

    private void formPatientAddAll_Load(Object sender, EventArgs e) throws Exception {
        textLName1.Text = LName;
        textFName1.Text = FName;
        listGender1.SelectedIndex = 0;
        listGender2.SelectedIndex = 0;
        listGender3.SelectedIndex = 0;
        listGender4.SelectedIndex = 0;
        listGender5.SelectedIndex = 0;
        listPosition1.SelectedIndex = 1;
        listPosition2.SelectedIndex = 1;
        comboSecProv1.Items.Add(Lan.g(this,"none"));
        comboSecProv1.SelectedIndex = 0;
        comboSecProv2.Items.Add(Lan.g(this,"none"));
        comboSecProv2.SelectedIndex = 0;
        comboSecProv3.Items.Add(Lan.g(this,"none"));
        comboSecProv3.SelectedIndex = 0;
        comboSecProv4.Items.Add(Lan.g(this,"none"));
        comboSecProv4.SelectedIndex = 0;
        comboSecProv5.Items.Add(Lan.g(this,"none"));
        comboSecProv5.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboPriProv1.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboSecProv1.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboPriProv2.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboSecProv2.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboPriProv3.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboSecProv3.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboPriProv4.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboSecProv4.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboPriProv5.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            comboSecProv5.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        int defaultindex = Providers.getIndex(PrefC.getLong(PrefName.PracticeDefaultProv));
        if (defaultindex == -1)
        {
            //default provider hidden
            defaultindex = 0;
        }
         
        comboPriProv1.SelectedIndex = defaultindex;
        comboPriProv2.SelectedIndex = defaultindex;
        comboPriProv3.SelectedIndex = defaultindex;
        comboPriProv4.SelectedIndex = defaultindex;
        comboPriProv5.SelectedIndex = defaultindex;
        if (!Security.isAuthorized(Permissions.RefAttachAdd,true))
        {
            textReferral.Enabled = false;
            textReferralFName.Enabled = false;
        }
         
        if (!PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            labelST.Text = "ST";
            textCountry.Visible = false;
        }
         
        fillComboZip();
        resetSubscriberLists();
    }

    private void formPatientAddAll_Shown(Object sender, EventArgs e) throws Exception {
    }

    private void textLName1_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textLName1.Text.Length == 1)
        {
            textLName1.Text = textLName1.Text.ToUpper();
            textLName1.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void textLName2_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textLName2.Text.Length == 1)
        {
            textLName2.Text = textLName2.Text.ToUpper();
            textLName2.SelectionStart = 1;
        }
         
    }

    private void textLName3_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textLName3.Text.Length == 1)
        {
            textLName3.Text = textLName3.Text.ToUpper();
            textLName3.SelectionStart = 1;
        }
         
    }

    private void textLName4_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textLName4.Text.Length == 1)
        {
            textLName4.Text = textLName4.Text.ToUpper();
            textLName4.SelectionStart = 1;
        }
         
    }

    private void textLName5_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textLName5.Text.Length == 1)
        {
            textLName5.Text = textLName5.Text.ToUpper();
            textLName5.SelectionStart = 1;
        }
         
    }

    private void textFName1_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textFName1.Text.Length == 1)
        {
            textFName1.Text = textFName1.Text.ToUpper();
            textFName1.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void textFName2_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textFName2.Text.Length == 1)
        {
            textFName2.Text = textFName2.Text.ToUpper();
            textFName2.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void textFName3_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textFName3.Text.Length == 1)
        {
            textFName3.Text = textFName3.Text.ToUpper();
            textFName3.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void textFName4_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textFName4.Text.Length == 1)
        {
            textFName4.Text = textFName4.Text.ToUpper();
            textFName4.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void textFName5_TextChanged(Object sender, EventArgs e) throws Exception {
        if (textFName5.Text.Length == 1)
        {
            textFName5.Text = textFName5.Text.ToUpper();
            textFName5.SelectionStart = 1;
        }
         
        setLNames();
    }

    private void setLNames() throws Exception {
        if (StringSupport.equals(textLName2.Text, "") || StringSupport.equals(textLName2.Text, mostRecentLName))
        {
            if (StringSupport.equals(textFName2.Text, ""))
            {
                textLName2.Text = "";
            }
            else
            {
                textLName2.Text = textLName1.Text;
            } 
        }
         
        if (StringSupport.equals(textLName3.Text, "") || StringSupport.equals(textLName3.Text, mostRecentLName))
        {
            if (StringSupport.equals(textFName3.Text, ""))
            {
                textLName3.Text = "";
            }
            else
            {
                textLName3.Text = textLName1.Text;
            } 
        }
         
        if (StringSupport.equals(textLName4.Text, "") || StringSupport.equals(textLName4.Text, mostRecentLName))
        {
            if (StringSupport.equals(textFName4.Text, ""))
            {
                textLName4.Text = "";
            }
            else
            {
                textLName4.Text = textLName1.Text;
            } 
        }
         
        if (StringSupport.equals(textLName5.Text, "") || StringSupport.equals(textLName5.Text, mostRecentLName))
        {
            if (StringSupport.equals(textFName5.Text, ""))
            {
                textLName5.Text = "";
            }
            else
            {
                textLName5.Text = textLName1.Text;
            } 
        }
         
        mostRecentLName = textLName1.Text;
        resetSubscriberLists();
    }

    private void textBirthdate1_Validated(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate1.errorProvider1.GetError(textBirthdate1), ""))
        {
            textAge1.Text = "";
            return ;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate1.Text);
        if (birthdate > DateTime.Today)
        {
            birthdate = birthdate.AddYears(-100);
        }
         
        textAge1.Text = PatientLogic.dateToAgeString(birthdate);
    }

    private void textBirthdate2_Validated(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate2.errorProvider1.GetError(textBirthdate2), ""))
        {
            textAge2.Text = "";
            return ;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate2.Text);
        if (birthdate > DateTime.Today)
        {
            birthdate = birthdate.AddYears(-100);
        }
         
        textAge2.Text = PatientLogic.dateToAgeString(birthdate);
    }

    private void textBirthdate3_Validated(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate3.errorProvider1.GetError(textBirthdate3), ""))
        {
            textAge3.Text = "";
            return ;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate3.Text);
        if (birthdate > DateTime.Today)
        {
            birthdate = birthdate.AddYears(-100);
        }
         
        textAge3.Text = PatientLogic.dateToAgeString(birthdate);
    }

    private void textBirthdate4_Validated(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate4.errorProvider1.GetError(textBirthdate4), ""))
        {
            textAge4.Text = "";
            return ;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate4.Text);
        if (birthdate > DateTime.Today)
        {
            birthdate = birthdate.AddYears(-100);
        }
         
        textAge4.Text = PatientLogic.dateToAgeString(birthdate);
    }

    private void textBirthdate5_Validated(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate5.errorProvider1.GetError(textBirthdate5), ""))
        {
            textAge5.Text = "";
            return ;
        }
         
        DateTime birthdate = PIn.Date(textBirthdate5.Text);
        if (birthdate > DateTime.Today)
        {
            birthdate = birthdate.AddYears(-100);
        }
         
        textAge5.Text = PatientLogic.dateToAgeString(birthdate);
    }

    private void checkInsOne1_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textFName2.Text, "") && checkInsOne1.Checked)
        {
            checkInsOne2.Checked = true;
        }
        else
        {
            checkInsOne2.Checked = false;
        } 
        if (!StringSupport.equals(textFName3.Text, "") && checkInsOne1.Checked)
        {
            checkInsOne3.Checked = true;
        }
        else
        {
            checkInsOne3.Checked = false;
        } 
        if (!StringSupport.equals(textFName4.Text, "") && checkInsOne1.Checked)
        {
            checkInsOne4.Checked = true;
        }
        else
        {
            checkInsOne4.Checked = false;
        } 
        if (!StringSupport.equals(textFName5.Text, "") && checkInsOne1.Checked)
        {
            checkInsOne5.Checked = true;
        }
        else
        {
            checkInsOne5.Checked = false;
        } 
    }

    private void checkInsTwo1_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textFName2.Text, "") && checkInsTwo1.Checked)
        {
            checkInsTwo2.Checked = true;
        }
        else
        {
            checkInsTwo2.Checked = false;
        } 
        if (!StringSupport.equals(textFName3.Text, "") && checkInsTwo1.Checked)
        {
            checkInsTwo3.Checked = true;
        }
        else
        {
            checkInsTwo3.Checked = false;
        } 
        if (!StringSupport.equals(textFName4.Text, "") && checkInsTwo1.Checked)
        {
            checkInsTwo4.Checked = true;
        }
        else
        {
            checkInsTwo4.Checked = false;
        } 
        if (!StringSupport.equals(textFName5.Text, "") && checkInsTwo1.Checked)
        {
            checkInsTwo5.Checked = true;
        }
        else
        {
            checkInsTwo5.Checked = false;
        } 
    }

    private void comboPriProv1_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        comboPriProv2.SelectedIndex = comboPriProv1.SelectedIndex;
        comboPriProv3.SelectedIndex = comboPriProv1.SelectedIndex;
        comboPriProv4.SelectedIndex = comboPriProv1.SelectedIndex;
        comboPriProv5.SelectedIndex = comboPriProv1.SelectedIndex;
    }

    private void comboSecProv1_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        comboSecProv2.SelectedIndex = comboSecProv1.SelectedIndex;
        comboSecProv3.SelectedIndex = comboSecProv1.SelectedIndex;
        comboSecProv4.SelectedIndex = comboSecProv1.SelectedIndex;
        comboSecProv5.SelectedIndex = comboSecProv1.SelectedIndex;
    }

    private void textHmPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textHmPhone.SelectionStart;
        int length = textHmPhone.Text.Length;
        textHmPhone.Text = TelephoneNumbers.AutoFormat(textHmPhone.Text);
        if (textHmPhone.Text.Length > length)
        {
            cursor++;
        }
         
        textHmPhone.SelectionStart = cursor;
    }

    private void textAddress_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textAddress.Text.Length == 1)
        {
            textAddress.Text = textAddress.Text.ToUpper();
            textAddress.SelectionStart = 1;
        }
         
    }

    private void textAddress2_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textAddress2.Text.Length == 1)
        {
            textAddress2.Text = textAddress2.Text.ToUpper();
            textAddress2.SelectionStart = 1;
        }
         
    }

    private void textCity_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textCity.Text.Length == 1)
        {
            textCity.Text = textCity.Text.ToUpper();
            textCity.SelectionStart = 1;
        }
         
    }

    private void textState_TextChanged(Object sender, System.EventArgs e) throws Exception {
        //if USA or Canada, capitalize first 2 letters
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") || CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (textState.Text.Length == 1 || textState.Text.Length == 2)
            {
                textState.Text = textState.Text.ToUpper();
                textState.SelectionStart = 2;
            }
             
        }
        else
        {
            if (textState.Text.Length == 1)
            {
                textState.Text = textState.Text.ToUpper();
                textState.SelectionStart = 1;
            }
             
        } 
    }

    private void textZip_TextChanged(Object sender, System.EventArgs e) throws Exception {
        comboZip.SelectedIndex = -1;
    }

    private void comboZip_SelectionChangeCommitted(Object sender, System.EventArgs e) throws Exception {
        //this happens when a zipcode is selected from the combobox of frequent zips.
        //The combo box is tucked under textZip because Microsoft makes stupid controls.
        textCity.Text = ((ZipCode)ZipCodes.ALFrequent[comboZip.SelectedIndex]).City;
        textState.Text = ((ZipCode)ZipCodes.ALFrequent[comboZip.SelectedIndex]).State;
        textZip.Text = ((ZipCode)ZipCodes.ALFrequent[comboZip.SelectedIndex]).ZipCodeDigits;
    }

    private void textZip_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        //fired as soon as control loses focus.
        //it's here to validate if zip is typed in to text box instead of picked from list.
        //if(textZip.Text=="" && (textCity.Text!="" || textState.Text!="")){
        //	if(MessageBox.Show(Lan.g(this,"Delete the City and State?"),"",MessageBoxButtons.OKCancel)
        //		==DialogResult.OK){
        //		textCity.Text="";
        //		textState.Text="";
        //	}
        //	return;
        //}
        if (textZip.Text.Length < 5)
        {
            return ;
        }
         
        if (comboZip.SelectedIndex != -1)
        {
            return ;
        }
         
        //the autofill only works if both city and state are left blank
        if (!StringSupport.equals(textCity.Text, "") || !StringSupport.equals(textState.Text, ""))
        {
            return ;
        }
         
        ZipCodes.GetALMatches(textZip.Text);
        if (ZipCodes.ALMatches.Count == 0)
        {
            //No match found. Must enter info for new zipcode
            ZipCode ZipCodeCur = new ZipCode();
            ZipCodeCur.ZipCodeDigits = textZip.Text;
            FormZipCodeEdit FormZE = new FormZipCodeEdit();
            FormZE.ZipCodeCur = ZipCodeCur;
            FormZE.IsNew = true;
            FormZE.ShowDialog();
            if (FormZE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            DataValid.setInvalid(InvalidType.ZipCodes);
            //FormZipCodeEdit does not contain internal refresh
            fillComboZip();
            textCity.Text = ZipCodeCur.City;
            textState.Text = ZipCodeCur.State;
            textZip.Text = ZipCodeCur.ZipCodeDigits;
        }
        else if (ZipCodes.ALMatches.Count == 1)
        {
            //only one match found.  Use it.
            textCity.Text = ((ZipCode)ZipCodes.ALMatches[0]).City;
            textState.Text = ((ZipCode)ZipCodes.ALMatches[0]).State;
        }
        else
        {
            //multiple matches found.  Pick one
            FormZipSelect FormZS = new FormZipSelect();
            FormZS.ShowDialog();
            fillComboZip();
            if (FormZS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            DataValid.setInvalid(InvalidType.ZipCodes);
            textCity.Text = FormZS.ZipSelected.City;
            textState.Text = FormZS.ZipSelected.State;
            textZip.Text = FormZS.ZipSelected.ZipCodeDigits;
        }  
    }

    private void fillComboZip() throws Exception {
        comboZip.Items.Clear();
        for (int i = 0;i < ZipCodes.ALFrequent.Count;i++)
        {
            comboZip.Items.Add(((ZipCode)ZipCodes.ALFrequent[i]).ZipCodeDigits + "(" + ((ZipCode)ZipCodes.ALFrequent[i]).City + ")");
        }
    }

    /**
    * Fills the referral fields based on the specified referralNum.
    */
    private void fillReferral(long referralNum) throws Exception {
        selectedReferral = Referrals.getReferral(referralNum);
        textReferral.Text = selectedReferral.LName;
        textReferralFName.Text = selectedReferral.FName;
    }

    private void textReferral_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Return)
        {
            if (listReferral.SelectedIndex == -1)
            {
                textReferralFName.Focus();
            }
            else
            {
                FillReferral(similarReferrals[listReferral.SelectedIndex].ReferralNum);
                textReferral.Focus();
                textReferral.SelectionStart = textReferral.Text.Length;
            } 
            listReferral.Visible = false;
            return ;
        }
         
        if (StringSupport.equals(textReferral.Text, ""))
        {
            listReferral.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listReferral.Items.Count == 0)
            {
                return ;
            }
             
            if (listReferral.SelectedIndex == -1)
            {
                listReferral.SelectedIndex = 0;
                textReferral.Text = similarReferrals[listReferral.SelectedIndex].LName;
            }
            else if (listReferral.SelectedIndex == listReferral.Items.Count - 1)
            {
                listReferral.SelectedIndex = -1;
                textReferral.Text = referralOriginal;
            }
            else
            {
                listReferral.SelectedIndex++;
                textReferral.Text = similarReferrals[listReferral.SelectedIndex].LName;
            }  
            textReferral.SelectionStart = textReferral.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listReferral.Items.Count == 0)
            {
                return ;
            }
             
            if (listReferral.SelectedIndex == -1)
            {
                listReferral.SelectedIndex = listReferral.Items.Count - 1;
                textReferral.Text = similarReferrals[listReferral.SelectedIndex].LName;
            }
            else if (listReferral.SelectedIndex == 0)
            {
                listReferral.SelectedIndex = -1;
                textReferral.Text = referralOriginal;
            }
            else
            {
                listReferral.SelectedIndex--;
                textReferral.Text = similarReferrals[listReferral.SelectedIndex].LName;
            }  
            textReferral.SelectionStart = textReferral.Text.Length;
            return ;
        }
         
        if (textReferral.Text.Length == 1)
        {
            textReferral.Text = textReferral.Text.ToUpper();
            textReferral.SelectionStart = 1;
        }
         
        referralOriginal = textReferral.Text;
        //the original text is preserved when using up and down arrows
        listReferral.Items.Clear();
        similarReferrals = Referrals.GetSimilarNames(textReferral.Text);
        for (int i = 0;i < similarReferrals.Count;i++)
        {
            listReferral.Items.Add(similarReferrals[i].LName + ", " + similarReferrals[i].FName + ", " + similarReferrals[i].Title + ", " + similarReferrals[i].Note);
        }
        int h = 13 * similarReferrals.Count + 5;
        if (h > ClientSize.Height - listReferral.Top)
        {
            h = ClientSize.Height - listReferral.Top;
        }
         
        listReferral.Size = new Size(listReferral.Width, h);
        listReferral.Visible = true;
    }

    private void textReferral_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListReferral)
        {
            return ;
        }
         
        //or if user clicked on a different text box.
        if (listReferral.SelectedIndex != -1)
        {
            FillReferral(similarReferrals[listReferral.SelectedIndex].ReferralNum);
        }
         
        listReferral.Visible = false;
    }

    private void listReferral_Click(Object sender, System.EventArgs e) throws Exception {
        FillReferral(similarReferrals[listReferral.SelectedIndex].ReferralNum);
        textReferral.Focus();
        textReferral.SelectionStart = textReferral.Text.Length;
        listReferral.Visible = false;
    }

    private void listReferral_DoubleClick(Object sender, System.EventArgs e) throws Exception {
    }

    //no longer used
    private void listReferral_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListReferral = true;
    }

    private void listReferral_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListReferral = false;
    }

    /**
    * Resets the text for each of the six options in the dropdown.  Does this without changing the selected index.
    */
    private void resetSubscriberLists() throws Exception {
        int selectedIndex1 = comboSubscriber1.SelectedIndex;
        int selectedIndex2 = comboSubscriber2.SelectedIndex;
        comboSubscriber1.Items.Clear();
        comboSubscriber2.Items.Clear();
        comboSubscriber1.Items.Add(Lan.g(this,"none"));
        comboSubscriber2.Items.Add(Lan.g(this,"none"));
        String str = new String();
        for (int i = 0;i < 5;i++)
        {
            str = (i + 1).ToString() + " - ";
            switch(i)
            {
                case 0: 
                    str += textLName1.Text + ", " + textFName1.Text;
                    break;
                case 1: 
                    str += textLName2.Text + ", " + textFName2.Text;
                    break;
                case 2: 
                    str += textLName3.Text + ", " + textFName3.Text;
                    break;
                case 3: 
                    str += textLName4.Text + ", " + textFName4.Text;
                    break;
                case 4: 
                    str += textLName5.Text + ", " + textFName5.Text;
                    break;
            
            }
            comboSubscriber1.Items.Add(str);
            comboSubscriber2.Items.Add(str);
        }
        if (selectedIndex1 == -1)
        {
            comboSubscriber1.SelectedIndex = 0;
        }
        else
        {
            comboSubscriber1.SelectedIndex = selectedIndex1;
        } 
        if (selectedIndex2 == -1)
        {
            comboSubscriber2.SelectedIndex = 0;
        }
        else
        {
            comboSubscriber2.SelectedIndex = selectedIndex2;
        } 
    }

    private void textEmployer1_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        //key up is used because that way it will trigger AFTER the textBox has been changed.
        if (e.KeyCode == Keys.Return)
        {
            listEmps1.Visible = false;
            textCarrier1.Focus();
            return ;
        }
         
        if (StringSupport.equals(textEmployer1.Text, ""))
        {
            listEmps1.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listEmps1.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps1.SelectedIndex == -1)
            {
                listEmps1.SelectedIndex = 0;
                textEmployer1.Text = listEmps1.SelectedItem.ToString();
            }
            else if (listEmps1.SelectedIndex == listEmps1.Items.Count - 1)
            {
                listEmps1.SelectedIndex = -1;
                textEmployer1.Text = empOriginal1;
            }
            else
            {
                listEmps1.SelectedIndex++;
                textEmployer1.Text = listEmps1.SelectedItem.ToString();
            }  
            textEmployer1.SelectionStart = textEmployer1.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listEmps1.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps1.SelectedIndex == -1)
            {
                listEmps1.SelectedIndex = listEmps1.Items.Count - 1;
                textEmployer1.Text = listEmps1.SelectedItem.ToString();
            }
            else if (listEmps1.SelectedIndex == 0)
            {
                listEmps1.SelectedIndex = -1;
                textEmployer1.Text = empOriginal1;
            }
            else
            {
                listEmps1.SelectedIndex--;
                textEmployer1.Text = listEmps1.SelectedItem.ToString();
            }  
            textEmployer1.SelectionStart = textEmployer1.Text.Length;
            return ;
        }
         
        if (textEmployer1.Text.Length == 1)
        {
            textEmployer1.Text = textEmployer1.Text.ToUpper();
            textEmployer1.SelectionStart = 1;
        }
         
        empOriginal1 = textEmployer1.Text;
        //the original text is preserved when using up and down arrows
        listEmps1.Items.Clear();
        List<Employer> similarEmps = Employers.GetSimilarNames(textEmployer1.Text);
        for (int i = 0;i < similarEmps.Count;i++)
        {
            listEmps1.Items.Add(similarEmps[i].EmpName);
        }
        int h = 13 * similarEmps.Count + 5;
        if (h > ClientSize.Height - listEmps1.Top)
        {
            h = ClientSize.Height - listEmps1.Top;
        }
         
        listEmps1.Size = new Size(231, h);
        listEmps1.Visible = true;
    }

    private void textEmployer1_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListEmps1)
        {
            return ;
        }
         
        listEmps1.Visible = false;
    }

    private void listEmps1_Click(Object sender, System.EventArgs e) throws Exception {
        textEmployer1.Text = listEmps1.SelectedItem.ToString();
        textEmployer1.Focus();
        textEmployer1.SelectionStart = textEmployer1.Text.Length;
        listEmps1.Visible = false;
    }

    private void listEmps1_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        //no longer used
        textEmployer1.Text = listEmps1.SelectedItem.ToString();
        textEmployer1.Focus();
        textEmployer1.SelectionStart = textEmployer1.Text.Length;
        listEmps1.Visible = false;
    }

    private void listEmps1_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps1 = true;
    }

    private void listEmps1_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps1 = false;
    }

    private void textEmployer2_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        //key up is used because that way it will trigger AFTER the textBox has been changed.
        if (e.KeyCode == Keys.Return)
        {
            listEmps2.Visible = false;
            textCarrier2.Focus();
            return ;
        }
         
        if (StringSupport.equals(textEmployer2.Text, ""))
        {
            listEmps2.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listEmps2.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps2.SelectedIndex == -1)
            {
                listEmps2.SelectedIndex = 0;
                textEmployer2.Text = listEmps2.SelectedItem.ToString();
            }
            else if (listEmps2.SelectedIndex == listEmps2.Items.Count - 1)
            {
                listEmps2.SelectedIndex = -1;
                textEmployer2.Text = empOriginal2;
            }
            else
            {
                listEmps2.SelectedIndex++;
                textEmployer2.Text = listEmps2.SelectedItem.ToString();
            }  
            textEmployer2.SelectionStart = textEmployer2.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listEmps2.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps2.SelectedIndex == -1)
            {
                listEmps2.SelectedIndex = listEmps2.Items.Count - 1;
                textEmployer2.Text = listEmps2.SelectedItem.ToString();
            }
            else if (listEmps2.SelectedIndex == 0)
            {
                listEmps2.SelectedIndex = -1;
                textEmployer2.Text = empOriginal2;
            }
            else
            {
                listEmps2.SelectedIndex--;
                textEmployer2.Text = listEmps2.SelectedItem.ToString();
            }  
            textEmployer2.SelectionStart = textEmployer2.Text.Length;
            return ;
        }
         
        if (textEmployer2.Text.Length == 1)
        {
            textEmployer2.Text = textEmployer2.Text.ToUpper();
            textEmployer2.SelectionStart = 1;
        }
         
        empOriginal2 = textEmployer2.Text;
        //the original text is preserved when using up and down arrows
        listEmps2.Items.Clear();
        List<Employer> similarEmps2 = Employers.GetSimilarNames(textEmployer2.Text);
        for (int i = 0;i < similarEmps2.Count;i++)
        {
            listEmps2.Items.Add(similarEmps2[i].EmpName);
        }
        int h = 13 * similarEmps2.Count + 5;
        if (h > ClientSize.Height - listEmps2.Top)
        {
            h = ClientSize.Height - listEmps2.Top;
        }
         
        listEmps2.Size = new Size(231, h);
        listEmps2.Visible = true;
    }

    private void textEmployer2_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListEmps2)
        {
            return ;
        }
         
        listEmps2.Visible = false;
    }

    private void listEmps2_Click(Object sender, System.EventArgs e) throws Exception {
        textEmployer2.Text = listEmps2.SelectedItem.ToString();
        textEmployer2.Focus();
        textEmployer2.SelectionStart = textEmployer2.Text.Length;
        listEmps2.Visible = false;
    }

    private void listEmps2_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        //no longer used
        textEmployer2.Text = listEmps2.SelectedItem.ToString();
        textEmployer2.Focus();
        textEmployer2.SelectionStart = textEmployer2.Text.Length;
        listEmps2.Visible = false;
    }

    private void listEmps2_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps2 = true;
    }

    private void listEmps2_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps2 = false;
    }

    /**
    * Fills the carrier fields on the form based on the specified carrierNum.
    */
    private void fillCarrier1(long carrierNum) throws Exception {
        selectedCarrier1 = Carriers.getCarrier(carrierNum);
        textCarrier1.Text = selectedCarrier1.CarrierName;
        textPhone1.Text = selectedCarrier1.Phone;
    }

    private void textCarrier1_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Return)
        {
            if (listCars1.SelectedIndex == -1)
            {
                textPhone1.Focus();
            }
            else
            {
                FillCarrier1(similarCars1[listCars1.SelectedIndex].CarrierNum);
                textCarrier1.Focus();
                textCarrier1.SelectionStart = textCarrier1.Text.Length;
            } 
            listCars1.Visible = false;
            return ;
        }
         
        if (StringSupport.equals(textCarrier1.Text, ""))
        {
            listCars1.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listCars1.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars1.SelectedIndex == -1)
            {
                listCars1.SelectedIndex = 0;
                textCarrier1.Text = similarCars1[listCars1.SelectedIndex].CarrierName;
            }
            else if (listCars1.SelectedIndex == listCars1.Items.Count - 1)
            {
                listCars1.SelectedIndex = -1;
                textCarrier1.Text = carOriginal1;
            }
            else
            {
                listCars1.SelectedIndex++;
                textCarrier1.Text = similarCars1[listCars1.SelectedIndex].CarrierName;
            }  
            textCarrier1.SelectionStart = textCarrier1.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listCars1.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars1.SelectedIndex == -1)
            {
                listCars1.SelectedIndex = listCars1.Items.Count - 1;
                textCarrier1.Text = similarCars1[listCars1.SelectedIndex].CarrierName;
            }
            else if (listCars1.SelectedIndex == 0)
            {
                listCars1.SelectedIndex = -1;
                textCarrier1.Text = carOriginal1;
            }
            else
            {
                listCars1.SelectedIndex--;
                textCarrier1.Text = similarCars1[listCars1.SelectedIndex].CarrierName;
            }  
            textCarrier1.SelectionStart = textCarrier1.Text.Length;
            return ;
        }
         
        if (textCarrier1.Text.Length == 1)
        {
            textCarrier1.Text = textCarrier1.Text.ToUpper();
            textCarrier1.SelectionStart = 1;
        }
         
        carOriginal1 = textCarrier1.Text;
        //the original text is preserved when using up and down arrows
        listCars1.Items.Clear();
        similarCars1 = Carriers.GetSimilarNames(textCarrier1.Text);
        for (int i = 0;i < similarCars1.Count;i++)
        {
            listCars1.Items.Add(similarCars1[i].CarrierName + ", " + similarCars1[i].Phone + ", " + similarCars1[i].Address + ", " + similarCars1[i].Address2 + ", " + similarCars1[i].City + ", " + similarCars1[i].State + ", " + similarCars1[i].Zip);
        }
        int h = 13 * similarCars1.Count + 5;
        if (h > ClientSize.Height - listCars1.Top)
        {
            h = ClientSize.Height - listCars1.Top;
        }
         
        listCars1.Size = new Size(listCars1.Width, h);
        listCars1.Visible = true;
    }

    private void textCarrier1_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListCars1)
        {
            return ;
        }
         
        //or if user clicked on a different text box.
        if (listCars1.SelectedIndex != -1)
        {
            FillCarrier1(similarCars1[listCars1.SelectedIndex].CarrierNum);
        }
         
        listCars1.Visible = false;
    }

    private void listCars1_Click(Object sender, System.EventArgs e) throws Exception {
        FillCarrier1(similarCars1[listCars1.SelectedIndex].CarrierNum);
        textCarrier1.Focus();
        textCarrier1.SelectionStart = textCarrier1.Text.Length;
        listCars1.Visible = false;
    }

    private void listCars1_DoubleClick(Object sender, System.EventArgs e) throws Exception {
    }

    //no longer used
    private void listCars1_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars1 = true;
    }

    private void listCars1_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars1 = false;
    }

    /**
    * Fills the carrier fields on the form based on the specified carrierNum.
    */
    private void fillCarrier2(long carrierNum) throws Exception {
        selectedCarrier2 = Carriers.getCarrier(carrierNum);
        textCarrier2.Text = selectedCarrier2.CarrierName;
        textPhone2.Text = selectedCarrier2.Phone;
    }

    private void textCarrier2_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Return)
        {
            if (listCars2.SelectedIndex == -1)
            {
                textPhone2.Focus();
            }
            else
            {
                FillCarrier2(similarCars2[listCars2.SelectedIndex].CarrierNum);
                textCarrier2.Focus();
                textCarrier2.SelectionStart = textCarrier2.Text.Length;
            } 
            listCars2.Visible = false;
            return ;
        }
         
        if (StringSupport.equals(textCarrier2.Text, ""))
        {
            listCars2.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listCars2.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars2.SelectedIndex == -1)
            {
                listCars2.SelectedIndex = 0;
                textCarrier2.Text = similarCars2[listCars2.SelectedIndex].CarrierName;
            }
            else if (listCars2.SelectedIndex == listCars2.Items.Count - 1)
            {
                listCars2.SelectedIndex = -1;
                textCarrier2.Text = carOriginal2;
            }
            else
            {
                listCars2.SelectedIndex++;
                textCarrier2.Text = similarCars2[listCars2.SelectedIndex].CarrierName;
            }  
            textCarrier2.SelectionStart = textCarrier2.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listCars2.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars2.SelectedIndex == -1)
            {
                listCars2.SelectedIndex = listCars2.Items.Count - 1;
                textCarrier2.Text = similarCars2[listCars2.SelectedIndex].CarrierName;
            }
            else if (listCars2.SelectedIndex == 0)
            {
                listCars2.SelectedIndex = -1;
                textCarrier2.Text = carOriginal2;
            }
            else
            {
                listCars2.SelectedIndex--;
                textCarrier2.Text = similarCars2[listCars2.SelectedIndex].CarrierName;
            }  
            textCarrier2.SelectionStart = textCarrier2.Text.Length;
            return ;
        }
         
        if (textCarrier2.Text.Length == 1)
        {
            textCarrier2.Text = textCarrier2.Text.ToUpper();
            textCarrier2.SelectionStart = 1;
        }
         
        carOriginal2 = textCarrier2.Text;
        //the original text is preserved when using up and down arrows
        listCars2.Items.Clear();
        similarCars2 = Carriers.GetSimilarNames(textCarrier2.Text);
        for (int i = 0;i < similarCars2.Count;i++)
        {
            listCars2.Items.Add(similarCars2[i].CarrierName + ", " + similarCars2[i].Phone + ", " + similarCars2[i].Address + ", " + similarCars2[i].Address2 + ", " + similarCars2[i].City + ", " + similarCars2[i].State + ", " + similarCars2[i].Zip);
        }
        int h = 13 * similarCars2.Count + 5;
        if (h > ClientSize.Height - listCars2.Top)
        {
            h = ClientSize.Height - listCars2.Top;
        }
         
        listCars2.Size = new Size(listCars2.Width, h);
        listCars2.Visible = true;
    }

    private void textCarrier2_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListCars2)
        {
            return ;
        }
         
        //or if user clicked on a different text box.
        if (listCars2.SelectedIndex != -1)
        {
            FillCarrier2(similarCars2[listCars2.SelectedIndex].CarrierNum);
        }
         
        listCars2.Visible = false;
    }

    private void listCars2_Click(Object sender, System.EventArgs e) throws Exception {
        FillCarrier2(similarCars2[listCars2.SelectedIndex].CarrierNum);
        textCarrier2.Focus();
        textCarrier2.SelectionStart = textCarrier2.Text.Length;
        listCars2.Visible = false;
    }

    private void listCars2_DoubleClick(Object sender, System.EventArgs e) throws Exception {
    }

    //no longer used
    private void listCars2_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars2 = true;
    }

    private void listCars2_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars2 = false;
    }

    private void butPick1_Click(Object sender, EventArgs e) throws Exception {
        FormInsPlans FormIP = new FormInsPlans();
        FormIP.empText = textEmployer1.Text;
        FormIP.carrierText = textCarrier1.Text;
        FormIP.IsSelectMode = true;
        FormIP.ShowDialog();
        if (FormIP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        selectedPlan1 = FormIP.SelectedPlan.copy();
        //Non-synched fields:
        //selectedPlan1.SubscriberID=textSubscriberID.Text;//later
        //selectedPlan1.DateEffective=DateTime.MinValue;
        //selectedPlan1.DateTerm=DateTime.MinValue;
        //PlanCur.ReleaseInfo=checkRelease.Checked;
        //PlanCur.AssignBen=checkAssign.Checked;
        //PlanCur.SubscNote=textSubscNote.Text;
        //Benefits will be created when click OK.
        textEmployer1.Text = Employers.getName(selectedPlan1.EmployerNum);
        fillCarrier1(selectedPlan1.CarrierNum);
        textGroupName1.Text = selectedPlan1.GroupName;
        textGroupNum1.Text = selectedPlan1.GroupNum;
    }

    private void butPick2_Click(Object sender, EventArgs e) throws Exception {
        FormInsPlans FormIP = new FormInsPlans();
        FormIP.empText = textEmployer2.Text;
        FormIP.carrierText = textCarrier2.Text;
        FormIP.IsSelectMode = true;
        FormIP.ShowDialog();
        if (FormIP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        selectedPlan2 = FormIP.SelectedPlan.copy();
        //Non-synched fields:
        //selectedPlan2.SubscriberID=textSubscriberID.Text;//later
        //selectedPlan2.DateEffective=DateTime.MinValue;
        //selectedPlan2.DateTerm=DateTime.MinValue;
        //PlanCur.ReleaseInfo=checkRelease.Checked;
        //PlanCur.AssignBen=checkAssign.Checked;
        //PlanCur.SubscNote=textSubscNote.Text;
        //Benefits will be created when click OK.
        textEmployer2.Text = Employers.getName(selectedPlan2.EmployerNum);
        fillCarrier2(selectedPlan2.CarrierNum);
        textGroupName2.Text = selectedPlan2.GroupName;
        textGroupNum2.Text = selectedPlan2.GroupNum;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBirthdate1.errorProvider1.GetError(textBirthdate1), "") || !StringSupport.equals(textBirthdate2.errorProvider1.GetError(textBirthdate2), "") || !StringSupport.equals(textBirthdate3.errorProvider1.GetError(textBirthdate3), "") || !StringSupport.equals(textBirthdate4.errorProvider1.GetError(textBirthdate4), "") || !StringSupport.equals(textBirthdate5.errorProvider1.GetError(textBirthdate5), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        //no validation on birthdate reasonableness.
        if (StringSupport.equals(textLName1.Text, "") || StringSupport.equals(textFName1.Text, ""))
        {
            MsgBox.show(this,"Guarantor name must be entered.");
            return ;
        }
         
        // Validate Insurance subscribers--------------------------------------------------------------------------------------------------------
        if ((comboSubscriber1.SelectedIndex == 2 || comboSubscriber2.SelectedIndex == 2) && (StringSupport.equals(textFName2.Text, "") || StringSupport.equals(textLName2.Text, "")))
        {
            MsgBox.show(this,"Subscriber must have name entered.");
            return ;
        }
         
        if ((comboSubscriber1.SelectedIndex == 3 || comboSubscriber2.SelectedIndex == 3) && (StringSupport.equals(textFName3.Text, "") || StringSupport.equals(textLName3.Text, "")))
        {
            MsgBox.show(this,"Subscriber must have name entered.");
            return ;
        }
         
        if ((comboSubscriber1.SelectedIndex == 4 || comboSubscriber2.SelectedIndex == 4) && (StringSupport.equals(textFName4.Text, "") || StringSupport.equals(textLName4.Text, "")))
        {
            MsgBox.show(this,"Subscriber must have name entered.");
            return ;
        }
         
        if ((comboSubscriber1.SelectedIndex == 5 || comboSubscriber2.SelectedIndex == 5) && (StringSupport.equals(textFName5.Text, "") || StringSupport.equals(textLName5.Text, "")))
        {
            MsgBox.show(this,"Subscriber must have name entered.");
            return ;
        }
         
        // Validate Insurance Plans--------------------------------------------------------------------------------------------------------------
        boolean insComplete1 = false;
        boolean insComplete2 = false;
        if (comboSubscriber1.SelectedIndex > 0 && !StringSupport.equals(textSubscriberID1.Text, "") && !StringSupport.equals(textCarrier1.Text, ""))
        {
            insComplete1 = true;
        }
         
        if (comboSubscriber2.SelectedIndex > 0 && !StringSupport.equals(textSubscriberID2.Text, "") && !StringSupport.equals(textCarrier2.Text, ""))
        {
            insComplete2 = true;
        }
         
        //test for insurance having only some of the critical fields filled in
        if (comboSubscriber1.SelectedIndex > 0 || !StringSupport.equals(textSubscriberID1.Text, "") || !StringSupport.equals(textCarrier1.Text, ""))
        {
            if (!insComplete1)
            {
                MsgBox.show(this,"Subscriber, Subscriber ID, and Carrier are all required fields if adding insurance.");
                return ;
            }
             
        }
         
        if (comboSubscriber2.SelectedIndex > 0 || !StringSupport.equals(textSubscriberID2.Text, "") || !StringSupport.equals(textCarrier2.Text, ""))
        {
            if (!insComplete2)
            {
                MsgBox.show(this,"Subscriber, Subscriber ID, and Carrier are all required fields if adding insurance.");
                return ;
            }
             
        }
         
        if (checkInsOne1.Checked || checkInsOne2.Checked || checkInsOne3.Checked || checkInsOne4.Checked || checkInsOne5.Checked)
        {
            if (!insComplete1)
            {
                MsgBox.show(this,"Subscriber, Subscriber ID, and Carrier are all required fields if adding insurance.");
                return ;
            }
             
        }
         
        if (checkInsTwo1.Checked || checkInsTwo2.Checked || checkInsTwo3.Checked || checkInsTwo4.Checked || checkInsTwo5.Checked)
        {
            if (!insComplete2)
            {
                MsgBox.show(this,"Subscriber, Subscriber ID, and Carrier are all required fields if adding insurance.");
                return ;
            }
             
        }
         
        //Validate Insurance subscriptions---------------------------------------------------------------------------------------------------
        if (insComplete1)
        {
            if (!checkInsOne1.Checked && !checkInsOne2.Checked && !checkInsOne3.Checked && !checkInsOne4.Checked && !checkInsOne5.Checked)
            {
                MsgBox.show(this,"Insurance information has been filled in, but has not been assigned to any patients.");
                return ;
            }
             
            //Insurance1 assigned to invalid patient1
            //Insurance1 assigned to invalid patient2
            //Insurance1 assigned to invalid patient3
            //Insurance1 assigned to invalid patient4
            if (checkInsOne1.Checked && (StringSupport.equals(textLName1.Text, "") || StringSupport.equals(textFName1.Text, "")) || checkInsOne2.Checked && (StringSupport.equals(textLName2.Text, "") || StringSupport.equals(textFName2.Text, "")) || checkInsOne3.Checked && (StringSupport.equals(textLName3.Text, "") || StringSupport.equals(textFName3.Text, "")) || checkInsOne4.Checked && (StringSupport.equals(textLName4.Text, "") || StringSupport.equals(textFName4.Text, "")) || checkInsOne5.Checked && (StringSupport.equals(textLName5.Text, "") || StringSupport.equals(textFName5.Text, "")))
            {
                //Insurance1 assigned to invalid patient5
                MsgBox.show(this,"Insurance information 1 has been filled in, but has been assigned to a patient with no name.");
                return ;
            }
             
        }
         
        if (insComplete2)
        {
            if (!checkInsTwo1.Checked && !checkInsTwo2.Checked && !checkInsTwo3.Checked && !checkInsTwo4.Checked && !checkInsTwo5.Checked)
            {
                MsgBox.show(this,"Insurance information 2 has been filled in, but has not been assigned to any patients.");
                return ;
            }
             
            //Insurance2 assigned to invalid patient1
            //Insurance2 assigned to invalid patient2
            //Insurance2 assigned to invalid patient3
            //Insurance2 assigned to invalid patient4
            if (checkInsTwo1.Checked && (StringSupport.equals(textLName1.Text, "") || StringSupport.equals(textFName1.Text, "")) || checkInsTwo2.Checked && (StringSupport.equals(textLName2.Text, "") || StringSupport.equals(textFName2.Text, "")) || checkInsTwo3.Checked && (StringSupport.equals(textLName3.Text, "") || StringSupport.equals(textFName3.Text, "")) || checkInsTwo4.Checked && (StringSupport.equals(textLName4.Text, "") || StringSupport.equals(textFName4.Text, "")) || checkInsTwo5.Checked && (StringSupport.equals(textLName5.Text, "") || StringSupport.equals(textFName5.Text, "")))
            {
                //Insurance2 assigned to invalid patient5
                MsgBox.show(this,"Insurance information 2 has been filled in, but has been assigned to a patient with no name.");
                return ;
            }
             
        }
         
        //End of validation------------------------------------------------------------------------------------------
        //Create Guarantor-------------------------------------------------------------------------------------------
        Patient guar = new Patient();
        guar.LName = textLName1.Text;
        guar.FName = textFName1.Text;
        if (listGender1.SelectedIndex == 0)
        {
            guar.Gender = PatientGender.Male;
        }
        else
        {
            guar.Gender = PatientGender.Female;
        } 
        if (listPosition1.SelectedIndex == 0)
        {
            guar.Position = PatientPosition.Single;
        }
        else
        {
            guar.Position = PatientPosition.Married;
        } 
        guar.Birthdate = PIn.Date(textBirthdate1.Text);
        guar.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        guar.PatStatus = PatientStatus.Patient;
        guar.PriProv = ProviderC.getListShort()[comboPriProv1.SelectedIndex].ProvNum;
        if (comboSecProv1.SelectedIndex > 0)
        {
            guar.SecProv = ProviderC.getListShort()[comboSecProv1.SelectedIndex - 1].ProvNum;
        }
         
        guar.HmPhone = textHmPhone.Text;
        guar.Address = textAddress.Text;
        guar.Address2 = textAddress2.Text;
        guar.City = textCity.Text;
        guar.State = textState.Text;
        guar.Country = textCountry.Text;
        guar.Zip = textZip.Text;
        guar.AddrNote = textAddrNotes.Text;
        guar.ClinicNum = Security.getCurUser().ClinicNum;
        Patients.insert(guar,false);
        CustReference custRef = new CustReference();
        custRef.PatNum = guar.PatNum;
        CustReferences.insert(custRef);
        Patient guarOld = guar.copy();
        guar.Guarantor = guar.PatNum;
        Patients.update(guar,guarOld);
        RefAttach refAttach;
        if (!StringSupport.equals(textReferral.Text, ""))
        {
            //selectedReferral will already be set if user picked from list.
            //but, if selectedReferral doesn't match data in boxes, then clear it.
            if (selectedReferral != null && (!StringSupport.equals(selectedReferral.LName, textReferral.Text) || !StringSupport.equals(selectedReferral.FName, textReferralFName.Text)))
            {
                selectedReferral = null;
            }
             
            if (selectedReferral == null)
            {
                selectedReferral = new Referral();
                selectedReferral.LName = textReferral.Text;
                selectedReferral.FName = textReferralFName.Text;
                Referrals.insert(selectedReferral);
            }
             
            //Now we will always have a valid referral to attach.  We will use it again for the other family members.
            refAttach = new RefAttach();
            refAttach.IsFrom = true;
            refAttach.RefDate = DateTimeOD.getToday();
            refAttach.ReferralNum = selectedReferral.ReferralNum;
            refAttach.PatNum = guar.PatNum;
            RefAttaches.Insert(refAttach);
            SecurityLogs.MakeLogEntry(Permissions.RefAttachAdd, refAttach.PatNum, "Referred From " + Referrals.GetNameFL(refAttach.ReferralNum));
        }
         
        //Patient #2-----------------------------------------------------------------------------------------------------
        Patient pat2 = null;
        if (!StringSupport.equals(textFName2.Text, "") && !StringSupport.equals(textLName2.Text, ""))
        {
            pat2 = new Patient();
            pat2.LName = textLName2.Text;
            pat2.FName = textFName2.Text;
            if (listGender2.SelectedIndex == 0)
            {
                pat2.Gender = PatientGender.Male;
            }
            else
            {
                pat2.Gender = PatientGender.Female;
            } 
            if (listPosition2.SelectedIndex == 0)
            {
                pat2.Position = PatientPosition.Single;
            }
            else
            {
                pat2.Position = PatientPosition.Married;
            } 
            pat2.Birthdate = PIn.Date(textBirthdate2.Text);
            pat2.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
            pat2.PatStatus = PatientStatus.Patient;
            pat2.PriProv = ProviderC.getListShort()[comboPriProv2.SelectedIndex].ProvNum;
            if (comboSecProv2.SelectedIndex > 0)
            {
                pat2.SecProv = ProviderC.getListShort()[comboSecProv2.SelectedIndex - 1].ProvNum;
            }
             
            pat2.HmPhone = textHmPhone.Text;
            pat2.Address = textAddress.Text;
            pat2.Address2 = textAddress2.Text;
            pat2.City = textCity.Text;
            pat2.State = textState.Text;
            pat2.Country = textCountry.Text;
            pat2.Zip = textZip.Text;
            pat2.AddrNote = textAddrNotes.Text;
            pat2.ClinicNum = Security.getCurUser().ClinicNum;
            pat2.Guarantor = guar.Guarantor;
            Patients.insert(pat2,false);
            custRef = new CustReference();
            custRef.PatNum = pat2.PatNum;
            CustReferences.insert(custRef);
            if (!StringSupport.equals(textReferral.Text, ""))
            {
                //selectedReferral will already have been set in the guarantor loop
                refAttach = new RefAttach();
                refAttach.IsFrom = true;
                refAttach.RefDate = DateTimeOD.getToday();
                refAttach.ReferralNum = selectedReferral.ReferralNum;
                refAttach.PatNum = pat2.PatNum;
                RefAttaches.Insert(refAttach);
                SecurityLogs.MakeLogEntry(Permissions.RefAttachAdd, refAttach.PatNum, "Referred From " + Referrals.GetNameFL(refAttach.ReferralNum));
            }
             
        }
         
        //Patient #3-----------------------------------------------------------------------------------------------------
        Patient pat3 = null;
        if (!StringSupport.equals(textFName3.Text, "") && !StringSupport.equals(textLName3.Text, ""))
        {
            pat3 = new Patient();
            pat3.LName = textLName3.Text;
            pat3.FName = textFName3.Text;
            if (listGender3.SelectedIndex == 0)
            {
                pat3.Gender = PatientGender.Male;
            }
            else
            {
                pat3.Gender = PatientGender.Female;
            } 
            pat3.Position = PatientPosition.Child;
            pat3.Birthdate = PIn.Date(textBirthdate3.Text);
            pat3.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
            pat3.PatStatus = PatientStatus.Patient;
            pat3.PriProv = ProviderC.getListShort()[comboPriProv3.SelectedIndex].ProvNum;
            if (comboSecProv3.SelectedIndex > 0)
            {
                pat3.SecProv = ProviderC.getListShort()[comboSecProv3.SelectedIndex - 1].ProvNum;
            }
             
            pat3.HmPhone = textHmPhone.Text;
            pat3.Address = textAddress.Text;
            pat3.Address2 = textAddress2.Text;
            pat3.City = textCity.Text;
            pat3.State = textState.Text;
            pat3.Country = textCountry.Text;
            pat3.Zip = textZip.Text;
            pat3.AddrNote = textAddrNotes.Text;
            pat3.ClinicNum = Security.getCurUser().ClinicNum;
            pat3.Guarantor = guar.Guarantor;
            Patients.insert(pat3,false);
            custRef = new CustReference();
            custRef.PatNum = pat3.PatNum;
            CustReferences.insert(custRef);
            if (!StringSupport.equals(textReferral.Text, ""))
            {
                //selectedReferral will already have been set in the guarantor loop
                refAttach = new RefAttach();
                refAttach.IsFrom = true;
                refAttach.RefDate = DateTimeOD.getToday();
                refAttach.ReferralNum = selectedReferral.ReferralNum;
                refAttach.PatNum = pat3.PatNum;
                RefAttaches.Insert(refAttach);
                SecurityLogs.MakeLogEntry(Permissions.RefAttachAdd, refAttach.PatNum, "Referred From " + Referrals.GetNameFL(refAttach.ReferralNum));
            }
             
        }
         
        //Patient #4-----------------------------------------------------------------------------------------------------
        Patient pat4 = null;
        if (!StringSupport.equals(textFName4.Text, "") && !StringSupport.equals(textLName4.Text, ""))
        {
            pat4 = new Patient();
            pat4.LName = textLName4.Text;
            pat4.FName = textFName4.Text;
            if (listGender4.SelectedIndex == 0)
            {
                pat4.Gender = PatientGender.Male;
            }
            else
            {
                pat4.Gender = PatientGender.Female;
            } 
            pat4.Position = PatientPosition.Child;
            pat4.Birthdate = PIn.Date(textBirthdate4.Text);
            pat4.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
            pat4.PatStatus = PatientStatus.Patient;
            pat4.PriProv = ProviderC.getListShort()[comboPriProv4.SelectedIndex].ProvNum;
            if (comboSecProv4.SelectedIndex > 0)
            {
                pat4.SecProv = ProviderC.getListShort()[comboSecProv4.SelectedIndex - 1].ProvNum;
            }
             
            pat4.HmPhone = textHmPhone.Text;
            pat4.Address = textAddress.Text;
            pat4.Address2 = textAddress2.Text;
            pat4.City = textCity.Text;
            pat4.State = textState.Text;
            pat4.Country = textCountry.Text;
            pat4.Zip = textZip.Text;
            pat4.AddrNote = textAddrNotes.Text;
            pat4.ClinicNum = Security.getCurUser().ClinicNum;
            pat4.Guarantor = guar.Guarantor;
            Patients.insert(pat4,false);
            custRef = new CustReference();
            custRef.PatNum = pat4.PatNum;
            CustReferences.insert(custRef);
            if (!StringSupport.equals(textReferral.Text, ""))
            {
                //selectedReferral will already have been set in the guarantor loop
                refAttach = new RefAttach();
                refAttach.IsFrom = true;
                refAttach.RefDate = DateTimeOD.getToday();
                refAttach.ReferralNum = selectedReferral.ReferralNum;
                refAttach.PatNum = pat4.PatNum;
                RefAttaches.Insert(refAttach);
                SecurityLogs.MakeLogEntry(Permissions.RefAttachAdd, refAttach.PatNum, "Referred From " + Referrals.GetNameFL(refAttach.ReferralNum));
            }
             
        }
         
        //Patient #5-----------------------------------------------------------------------------------------------------
        Patient pat5 = null;
        if (!StringSupport.equals(textFName5.Text, "") && !StringSupport.equals(textLName5.Text, ""))
        {
            pat5 = new Patient();
            pat5.LName = textLName5.Text;
            pat5.FName = textFName5.Text;
            if (listGender5.SelectedIndex == 0)
            {
                pat5.Gender = PatientGender.Male;
            }
            else
            {
                pat5.Gender = PatientGender.Female;
            } 
            pat5.Position = PatientPosition.Child;
            pat5.Birthdate = PIn.Date(textBirthdate5.Text);
            pat5.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
            pat5.PatStatus = PatientStatus.Patient;
            pat5.PriProv = ProviderC.getListShort()[comboPriProv5.SelectedIndex].ProvNum;
            if (comboSecProv5.SelectedIndex > 0)
            {
                pat5.SecProv = ProviderC.getListShort()[comboSecProv5.SelectedIndex - 1].ProvNum;
            }
             
            pat5.HmPhone = textHmPhone.Text;
            pat5.Address = textAddress.Text;
            pat5.Address2 = textAddress2.Text;
            pat5.City = textCity.Text;
            pat5.State = textState.Text;
            pat5.Country = textCountry.Text;
            pat5.Zip = textZip.Text;
            pat5.AddrNote = textAddrNotes.Text;
            pat5.ClinicNum = Security.getCurUser().ClinicNum;
            pat5.Guarantor = guar.Guarantor;
            Patients.insert(pat5,false);
            custRef = new CustReference();
            custRef.PatNum = pat5.PatNum;
            CustReferences.insert(custRef);
            if (!StringSupport.equals(textReferral.Text, ""))
            {
                //selectedReferral will already have been set in the guarantor loop
                refAttach = new RefAttach();
                refAttach.IsFrom = true;
                refAttach.RefDate = DateTimeOD.getToday();
                refAttach.ReferralNum = selectedReferral.ReferralNum;
                refAttach.PatNum = pat5.PatNum;
                RefAttaches.Insert(refAttach);
                SecurityLogs.MakeLogEntry(Permissions.RefAttachAdd, refAttach.PatNum, "Referred From " + Referrals.GetNameFL(refAttach.ReferralNum));
            }
             
        }
         
        //Insurance------------------------------------------------------------------------------------------------------------
        InsSub sub1 = null;
        InsSub sub2 = null;
        if (selectedPlan1 != null)
        {
            //validate the ins fields.  If they don't match perfectly, then set it to null
            if (!StringSupport.equals(Employers.getName(selectedPlan1.EmployerNum), textEmployer1.Text) || !StringSupport.equals(Carriers.getName(selectedPlan1.CarrierNum), textCarrier1.Text) || !StringSupport.equals(selectedPlan1.GroupName, textGroupName1.Text) || !StringSupport.equals(selectedPlan1.GroupNum, textGroupNum1.Text))
            {
                selectedPlan1 = null;
            }
             
        }
         
        if (selectedPlan2 != null)
        {
            if (!StringSupport.equals(Employers.getName(selectedPlan2.EmployerNum), textEmployer2.Text) || !StringSupport.equals(Carriers.getName(selectedPlan2.CarrierNum), textCarrier2.Text) || !StringSupport.equals(selectedPlan2.GroupName, textGroupName2.Text) || !StringSupport.equals(selectedPlan2.GroupNum, textGroupNum2.Text))
            {
                selectedPlan2 = null;
            }
             
        }
         
        if (selectedCarrier1 != null)
        {
            //validate the carrier fields.  If they don't match perfectly, then set it to null
            if (!StringSupport.equals(selectedCarrier1.CarrierName, textCarrier1.Text) || !StringSupport.equals(selectedCarrier1.Phone, textPhone1.Text))
            {
                selectedCarrier1 = null;
            }
             
        }
         
        if (selectedCarrier2 != null)
        {
            if (!StringSupport.equals(selectedCarrier2.CarrierName, textCarrier2.Text) || !StringSupport.equals(selectedCarrier2.Phone, textPhone2.Text))
            {
                selectedCarrier2 = null;
            }
             
        }
         
        if (insComplete1)
        {
            if (selectedCarrier1 == null)
            {
                //get a carrier, possibly creating a new one if needed.
                selectedCarrier1 = Carriers.GetByNameAndPhone(textCarrier1.Text, textPhone1.Text);
            }
             
            long empNum1 = Employers.GetEmployerNum(textEmployer1.Text);
            if (selectedPlan1 == null)
            {
                //don't try to get a copy of an existing plan. Instead, start from scratch.
                selectedPlan1 = new InsPlan();
                selectedPlan1.EmployerNum = empNum1;
                selectedPlan1.CarrierNum = selectedCarrier1.CarrierNum;
                selectedPlan1.GroupName = textGroupName1.Text;
                selectedPlan1.GroupNum = textGroupNum1.Text;
                selectedPlan1.PlanType = "";
                InsPlans.insert(selectedPlan1);
                Benefit ben;
                for (int i = 0;i < CovCatC.getListShort().Count;i++)
                {
                    if (CovCatC.getListShort()[i].DefaultPercent == -1)
                    {
                        continue;
                    }
                     
                    ben = new Benefit();
                    ben.BenefitType = InsBenefitType.CoInsurance;
                    ben.CovCatNum = CovCatC.getListShort()[i].CovCatNum;
                    ben.PlanNum = selectedPlan1.PlanNum;
                    ben.Percent = CovCatC.getListShort()[i].DefaultPercent;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.CodeNum = 0;
                    Benefits.insert(ben);
                }
                //Zero deductible diagnostic
                if (CovCats.getForEbenCat(EbenefitCategory.Diagnostic) != null)
                {
                    ben = new Benefit();
                    ben.CodeNum = 0;
                    ben.BenefitType = InsBenefitType.Deductible;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
                    ben.PlanNum = selectedPlan1.PlanNum;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.MonetaryAmt = 0;
                    ben.Percent = -1;
                    ben.CoverageLevel = BenefitCoverageLevel.Individual;
                    Benefits.insert(ben);
                }
                 
                //Zero deductible preventive
                if (CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) != null)
                {
                    ben = new Benefit();
                    ben.CodeNum = 0;
                    ben.BenefitType = InsBenefitType.Deductible;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                    ben.PlanNum = selectedPlan1.PlanNum;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.MonetaryAmt = 0;
                    ben.Percent = -1;
                    ben.CoverageLevel = BenefitCoverageLevel.Individual;
                    Benefits.insert(ben);
                }
                 
            }
             
            sub1 = new InsSub();
            sub1.PlanNum = selectedPlan1.PlanNum;
            sub1.AssignBen = true;
            sub1.ReleaseInfo = true;
            sub1.DateEffective = DateTime.MinValue;
            sub1.DateTerm = DateTime.MinValue;
            if (comboSubscriber1.SelectedIndex == 1)
            {
                sub1.Subscriber = guar.PatNum;
            }
             
            if (comboSubscriber1.SelectedIndex == 2)
            {
                sub1.Subscriber = pat2.PatNum;
            }
             
            if (comboSubscriber1.SelectedIndex == 3)
            {
                sub1.Subscriber = pat3.PatNum;
            }
             
            if (comboSubscriber1.SelectedIndex == 4)
            {
                sub1.Subscriber = pat4.PatNum;
            }
             
            if (comboSubscriber1.SelectedIndex == 5)
            {
                sub1.Subscriber = pat5.PatNum;
            }
             
            sub1.SubscriberID = textSubscriberID1.Text;
            InsSubs.insert(sub1);
        }
         
        if (insComplete2)
        {
            if (selectedCarrier2 == null)
            {
                selectedCarrier2 = Carriers.GetByNameAndPhone(textCarrier2.Text, textPhone2.Text);
            }
             
            long empNum2 = Employers.GetEmployerNum(textEmployer2.Text);
            if (selectedPlan2 == null)
            {
                //don't try to get a copy of an existing plan. Instead, start from scratch.
                selectedPlan2 = new InsPlan();
                selectedPlan2.EmployerNum = empNum2;
                selectedPlan2.CarrierNum = selectedCarrier2.CarrierNum;
                selectedPlan2.GroupName = textGroupName2.Text;
                selectedPlan2.GroupNum = textGroupNum2.Text;
                selectedPlan2.PlanType = "";
                InsPlans.insert(selectedPlan2);
                Benefit ben;
                for (int i = 0;i < CovCatC.getListShort().Count;i++)
                {
                    if (CovCatC.getListShort()[i].DefaultPercent == -1)
                    {
                        continue;
                    }
                     
                    ben = new Benefit();
                    ben.BenefitType = InsBenefitType.CoInsurance;
                    ben.CovCatNum = CovCatC.getListShort()[i].CovCatNum;
                    ben.PlanNum = selectedPlan2.PlanNum;
                    ben.Percent = CovCatC.getListShort()[i].DefaultPercent;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.CodeNum = 0;
                    Benefits.insert(ben);
                }
                //Zero deductible diagnostic
                if (CovCats.getForEbenCat(EbenefitCategory.Diagnostic) != null)
                {
                    ben = new Benefit();
                    ben.CodeNum = 0;
                    ben.BenefitType = InsBenefitType.Deductible;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
                    ben.PlanNum = selectedPlan2.PlanNum;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.MonetaryAmt = 0;
                    ben.Percent = -1;
                    ben.CoverageLevel = BenefitCoverageLevel.Individual;
                    Benefits.insert(ben);
                }
                 
                //Zero deductible preventive
                if (CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) != null)
                {
                    ben = new Benefit();
                    ben.CodeNum = 0;
                    ben.BenefitType = InsBenefitType.Deductible;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                    ben.PlanNum = selectedPlan2.PlanNum;
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.MonetaryAmt = 0;
                    ben.Percent = -1;
                    ben.CoverageLevel = BenefitCoverageLevel.Individual;
                    Benefits.insert(ben);
                }
                 
            }
             
            sub2 = new InsSub();
            sub2.PlanNum = selectedPlan2.PlanNum;
            sub2.AssignBen = true;
            sub2.ReleaseInfo = true;
            sub2.DateEffective = DateTime.MinValue;
            sub2.DateTerm = DateTime.MinValue;
            if (comboSubscriber2.SelectedIndex == 1)
            {
                sub2.Subscriber = guar.PatNum;
            }
             
            if (comboSubscriber2.SelectedIndex == 2)
            {
                sub2.Subscriber = pat2.PatNum;
            }
             
            if (comboSubscriber2.SelectedIndex == 3)
            {
                sub2.Subscriber = pat3.PatNum;
            }
             
            if (comboSubscriber2.SelectedIndex == 4)
            {
                sub2.Subscriber = pat4.PatNum;
            }
             
            if (comboSubscriber2.SelectedIndex == 5)
            {
                sub2.Subscriber = pat5.PatNum;
            }
             
            sub2.SubscriberID = textSubscriberID2.Text;
            InsSubs.insert(sub2);
        }
         
        PatPlan patplan;
        //attach insurance to subscriber--------------------------------------------------------------------------------
        if (checkInsOne1.Checked)
        {
            patplan = new PatPlan();
            //the only situation where ordinal would be 2 is if ins2 has this patient as the subscriber.
            if (comboSubscriber2.SelectedIndex == 1)
            {
                patplan.Ordinal = 2;
            }
            else
            {
                patplan.Ordinal = 1;
            } 
            patplan.PatNum = guar.PatNum;
            patplan.InsSubNum = sub1.InsSubNum;
            if (comboSubscriber1.SelectedIndex == 1)
            {
                patplan.Relationship = Relat.Self;
            }
            else if (comboSubscriber1.SelectedIndex == 2)
            {
                patplan.Relationship = Relat.Spouse;
            }
            else
            {
            }  
            //the subscriber would never be a child
            PatPlans.insert(patplan);
        }
         
        if (checkInsTwo1.Checked)
        {
            patplan = new PatPlan();
            //the only situations where ordinal would be 1 is if ins1 is not checked or if ins2 has this patient as subscriber.
            if (comboSubscriber2.SelectedIndex == 1)
            {
                patplan.Ordinal = 1;
            }
            else if (!checkInsOne1.Checked)
            {
                patplan.Ordinal = 1;
            }
            else
            {
                patplan.Ordinal = 2;
            }  
            patplan.PatNum = guar.PatNum;
            patplan.InsSubNum = sub2.InsSubNum;
            if (comboSubscriber2.SelectedIndex == 1)
            {
                patplan.Relationship = Relat.Self;
            }
            else if (comboSubscriber2.SelectedIndex == 2)
            {
                patplan.Relationship = Relat.Spouse;
            }
            else
            {
            }  
            //the subscriber would never be a child
            PatPlans.insert(patplan);
        }
         
        //attach insurance to patient 2, the other parent----------------------------------------------------------------------
        if (checkInsOne2.Checked)
        {
            patplan = new PatPlan();
            //the only situation where ordinal would be 2 is if ins2 has this patient as the subscriber.
            if (comboSubscriber2.SelectedIndex == 2)
            {
                patplan.Ordinal = 2;
            }
            else
            {
                patplan.Ordinal = 1;
            } 
            patplan.PatNum = pat2.PatNum;
            patplan.InsSubNum = sub1.InsSubNum;
            if (comboSubscriber1.SelectedIndex == 2)
            {
                patplan.Relationship = Relat.Self;
            }
            else if (comboSubscriber1.SelectedIndex == 1)
            {
                patplan.Relationship = Relat.Spouse;
            }
            else
            {
            }  
            //the subscriber would never be a child
            PatPlans.insert(patplan);
        }
         
        if (checkInsTwo2.Checked)
        {
            patplan = new PatPlan();
            //the only situations where ordinal would be 1 is if ins1 is not checked or if ins2 has this patient as subscriber.
            if (comboSubscriber2.SelectedIndex == 2)
            {
                patplan.Ordinal = 1;
            }
            else if (!checkInsOne2.Checked)
            {
                patplan.Ordinal = 1;
            }
            else
            {
                patplan.Ordinal = 2;
            }  
            patplan.PatNum = pat2.PatNum;
            patplan.InsSubNum = sub2.InsSubNum;
            if (comboSubscriber2.SelectedIndex == 2)
            {
                patplan.Relationship = Relat.Self;
            }
            else if (comboSubscriber2.SelectedIndex == 1)
            {
                patplan.Relationship = Relat.Spouse;
            }
            else
            {
            }  
            //the subscriber would never be a child
            PatPlans.insert(patplan);
        }
         
        //attach insurance to patient 3, a child----------------------------------------------------------------------
        if (checkInsOne3.Checked)
        {
            patplan = new PatPlan();
            patplan.Ordinal = 1;
            patplan.PatNum = pat3.PatNum;
            patplan.InsSubNum = sub1.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        if (checkInsTwo3.Checked)
        {
            patplan = new PatPlan();
            //the only situation where ordinal would be 1 is if ins1 is not checked.
            if (!checkInsOne3.Checked)
            {
                patplan.Ordinal = 1;
            }
            else
            {
                patplan.Ordinal = 2;
            } 
            patplan.PatNum = pat3.PatNum;
            patplan.InsSubNum = sub2.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        //attach insurance to patient 4, a child----------------------------------------------------------------------
        if (checkInsOne4.Checked)
        {
            patplan = new PatPlan();
            patplan.Ordinal = 1;
            patplan.PatNum = pat4.PatNum;
            patplan.InsSubNum = sub1.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        if (checkInsTwo4.Checked)
        {
            patplan = new PatPlan();
            //the only situation where ordinal would be 1 is if ins1 is not checked.
            if (!checkInsOne4.Checked)
            {
                patplan.Ordinal = 1;
            }
            else
            {
                patplan.Ordinal = 2;
            } 
            patplan.PatNum = pat4.PatNum;
            patplan.InsSubNum = sub2.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        //attach insurance to patient 5, a child----------------------------------------------------------------------
        if (checkInsOne5.Checked)
        {
            patplan = new PatPlan();
            patplan.Ordinal = 1;
            patplan.PatNum = pat5.PatNum;
            patplan.InsSubNum = sub1.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        if (checkInsTwo5.Checked)
        {
            patplan = new PatPlan();
            //the only situation where ordinal would be 1 is if ins1 is not checked.
            if (!checkInsOne5.Checked)
            {
                patplan.Ordinal = 1;
            }
            else
            {
                patplan.Ordinal = 2;
            } 
            patplan.PatNum = pat5.PatNum;
            patplan.InsSubNum = sub2.InsSubNum;
            patplan.Relationship = Relat.Child;
            PatPlans.insert(patplan);
        }
         
        SelectedPatNum = guar.PatNum;
        MessageBox.Show("Done");
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
        this.textLName4 = new System.Windows.Forms.TextBox();
        this.textLName5 = new System.Windows.Forms.TextBox();
        this.textLName1 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textFName1 = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.listPosition1 = new System.Windows.Forms.ListBox();
        this.listGender1 = new System.Windows.Forms.ListBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textHmPhone = new System.Windows.Forms.TextBox();
        this.textZip = new System.Windows.Forms.TextBox();
        this.comboZip = new System.Windows.Forms.ComboBox();
        this.textCountry = new System.Windows.Forms.TextBox();
        this.textState = new System.Windows.Forms.TextBox();
        this.labelST = new System.Windows.Forms.Label();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.labelCity = new System.Windows.Forms.Label();
        this.textAddress2 = new System.Windows.Forms.TextBox();
        this.labelZip = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.textCity = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label39 = new System.Windows.Forms.Label();
        this.label38 = new System.Windows.Forms.Label();
        this.comboSecProv1 = new System.Windows.Forms.ComboBox();
        this.comboPriProv1 = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.checkInsOne1 = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkInsTwo1 = new System.Windows.Forms.CheckBox();
        this.checkInsTwo2 = new System.Windows.Forms.CheckBox();
        this.checkInsOne2 = new System.Windows.Forms.CheckBox();
        this.listPosition2 = new System.Windows.Forms.ListBox();
        this.listGender2 = new System.Windows.Forms.ListBox();
        this.textFName2 = new System.Windows.Forms.TextBox();
        this.textLName2 = new System.Windows.Forms.TextBox();
        this.checkInsTwo3 = new System.Windows.Forms.CheckBox();
        this.checkInsOne3 = new System.Windows.Forms.CheckBox();
        this.listGender3 = new System.Windows.Forms.ListBox();
        this.textFName3 = new System.Windows.Forms.TextBox();
        this.textLName3 = new System.Windows.Forms.TextBox();
        this.checkInsTwo4 = new System.Windows.Forms.CheckBox();
        this.checkInsOne4 = new System.Windows.Forms.CheckBox();
        this.listGender4 = new System.Windows.Forms.ListBox();
        this.textFName4 = new System.Windows.Forms.TextBox();
        this.checkInsTwo5 = new System.Windows.Forms.CheckBox();
        this.checkInsOne5 = new System.Windows.Forms.CheckBox();
        this.listGender5 = new System.Windows.Forms.ListBox();
        this.textFName5 = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textPhone1 = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textCarrier1 = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.groupIns1 = new System.Windows.Forms.GroupBox();
        this.butPick1 = new OpenDental.UI.Button();
        this.textGroupNum1 = new System.Windows.Forms.TextBox();
        this.labelGroupNum = new System.Windows.Forms.Label();
        this.label19 = new System.Windows.Forms.Label();
        this.textGroupName1 = new System.Windows.Forms.TextBox();
        this.textEmployer1 = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.textSubscriberID1 = new System.Windows.Forms.TextBox();
        this.label15 = new System.Windows.Forms.Label();
        this.comboSubscriber1 = new System.Windows.Forms.ComboBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label20 = new System.Windows.Forms.Label();
        this.label21 = new System.Windows.Forms.Label();
        this.label22 = new System.Windows.Forms.Label();
        this.groupIns2 = new System.Windows.Forms.GroupBox();
        this.butPick2 = new OpenDental.UI.Button();
        this.textGroupNum2 = new System.Windows.Forms.TextBox();
        this.label23 = new System.Windows.Forms.Label();
        this.label24 = new System.Windows.Forms.Label();
        this.textGroupName2 = new System.Windows.Forms.TextBox();
        this.textEmployer2 = new System.Windows.Forms.TextBox();
        this.label25 = new System.Windows.Forms.Label();
        this.textSubscriberID2 = new System.Windows.Forms.TextBox();
        this.label26 = new System.Windows.Forms.Label();
        this.comboSubscriber2 = new System.Windows.Forms.ComboBox();
        this.label27 = new System.Windows.Forms.Label();
        this.textPhone2 = new System.Windows.Forms.TextBox();
        this.label28 = new System.Windows.Forms.Label();
        this.label29 = new System.Windows.Forms.Label();
        this.textCarrier2 = new System.Windows.Forms.TextBox();
        this.label30 = new System.Windows.Forms.Label();
        this.textReferral = new System.Windows.Forms.TextBox();
        this.textAge1 = new System.Windows.Forms.TextBox();
        this.textAge2 = new System.Windows.Forms.TextBox();
        this.textAge3 = new System.Windows.Forms.TextBox();
        this.textAge4 = new System.Windows.Forms.TextBox();
        this.textAge5 = new System.Windows.Forms.TextBox();
        this.comboSecProv2 = new System.Windows.Forms.ComboBox();
        this.comboPriProv2 = new System.Windows.Forms.ComboBox();
        this.comboSecProv3 = new System.Windows.Forms.ComboBox();
        this.comboPriProv3 = new System.Windows.Forms.ComboBox();
        this.comboSecProv4 = new System.Windows.Forms.ComboBox();
        this.comboPriProv4 = new System.Windows.Forms.ComboBox();
        this.comboSecProv5 = new System.Windows.Forms.ComboBox();
        this.comboPriProv5 = new System.Windows.Forms.ComboBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textReferralFName = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textAddrNotes = new OpenDental.ODtextBox();
        this.textBirthdate5 = new OpenDental.ValidDate();
        this.textBirthdate4 = new OpenDental.ValidDate();
        this.textBirthdate3 = new OpenDental.ValidDate();
        this.textBirthdate2 = new OpenDental.ValidDate();
        this.textBirthdate1 = new OpenDental.ValidDate();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupIns1.SuspendLayout();
        this.groupIns2.SuspendLayout();
        this.SuspendLayout();
        //
        // textLName4
        //
        this.textLName4.Location = new System.Drawing.Point(580, 28);
        this.textLName4.MaxLength = 100;
        this.textLName4.Name = "textLName4";
        this.textLName4.Size = new System.Drawing.Size(149, 20);
        this.textLName4.TabIndex = 49;
        this.textLName4.TextChanged += new System.EventHandler(this.textLName4_TextChanged);
        //
        // textLName5
        //
        this.textLName5.Location = new System.Drawing.Point(733, 28);
        this.textLName5.MaxLength = 100;
        this.textLName5.Name = "textLName5";
        this.textLName5.Size = new System.Drawing.Size(149, 20);
        this.textLName5.TabIndex = 57;
        this.textLName5.TextChanged += new System.EventHandler(this.textLName5_TextChanged);
        //
        // textLName1
        //
        this.textLName1.Location = new System.Drawing.Point(121, 28);
        this.textLName1.MaxLength = 100;
        this.textLName1.Name = "textLName1";
        this.textLName1.Size = new System.Drawing.Size(149, 20);
        this.textLName1.TabIndex = 0;
        this.textLName1.TextChanged += new System.EventHandler(this.textLName1_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(3, 29);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 16);
        this.label2.TabIndex = 5;
        this.label2.Text = "Last Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textFName1
        //
        this.textFName1.Location = new System.Drawing.Point(121, 49);
        this.textFName1.MaxLength = 100;
        this.textFName1.Name = "textFName1";
        this.textFName1.Size = new System.Drawing.Size(149, 20);
        this.textFName1.TabIndex = 1;
        this.textFName1.TextChanged += new System.EventHandler(this.textFName1_TextChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(3, 50);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(118, 16);
        this.label1.TabIndex = 7;
        this.label1.Text = "First Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(3, 102);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(118, 16);
        this.label5.TabIndex = 13;
        this.label5.Text = "Birthdate / Age";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(3, 73);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(118, 16);
        this.label7.TabIndex = 15;
        this.label7.Text = "Gender / Position";
        this.label7.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // listPosition1
        //
        this.listPosition1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.listPosition1.Items.AddRange(new Object[]{ "Single", "Married" });
        this.listPosition1.Location = new System.Drawing.Point(183, 70);
        this.listPosition1.Name = "listPosition1";
        this.listPosition1.Size = new System.Drawing.Size(61, 30);
        this.listPosition1.TabIndex = 17;
        //
        // listGender1
        //
        this.listGender1.Items.AddRange(new Object[]{ "Male", "Female" });
        this.listGender1.Location = new System.Drawing.Point(121, 70);
        this.listGender1.Name = "listGender1";
        this.listGender1.Size = new System.Drawing.Size(61, 30);
        this.listGender1.TabIndex = 14;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textHmPhone);
        this.groupBox1.Controls.Add(this.textZip);
        this.groupBox1.Controls.Add(this.comboZip);
        this.groupBox1.Controls.Add(this.textCountry);
        this.groupBox1.Controls.Add(this.textState);
        this.groupBox1.Controls.Add(this.labelST);
        this.groupBox1.Controls.Add(this.textAddress);
        this.groupBox1.Controls.Add(this.label12);
        this.groupBox1.Controls.Add(this.labelCity);
        this.groupBox1.Controls.Add(this.textAddress2);
        this.groupBox1.Controls.Add(this.labelZip);
        this.groupBox1.Controls.Add(this.label16);
        this.groupBox1.Controls.Add(this.textCity);
        this.groupBox1.Controls.Add(this.label11);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(29, 211);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(409, 167);
        this.groupBox1.TabIndex = 11;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Address and Phone";
        //
        // textHmPhone
        //
        this.textHmPhone.Location = new System.Drawing.Point(128, 29);
        this.textHmPhone.MaxLength = 30;
        this.textHmPhone.Name = "textHmPhone";
        this.textHmPhone.Size = new System.Drawing.Size(174, 20);
        this.textHmPhone.TabIndex = 0;
        this.textHmPhone.TextChanged += new System.EventHandler(this.textHmPhone_TextChanged);
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(128, 129);
        this.textZip.MaxLength = 100;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(179, 20);
        this.textZip.TabIndex = 3;
        this.textZip.TextChanged += new System.EventHandler(this.textZip_TextChanged);
        this.textZip.Validating += new System.ComponentModel.CancelEventHandler(this.textZip_Validating);
        //
        // comboZip
        //
        this.comboZip.DropDownWidth = 198;
        this.comboZip.Location = new System.Drawing.Point(128, 129);
        this.comboZip.Name = "comboZip";
        this.comboZip.Size = new System.Drawing.Size(198, 21);
        this.comboZip.TabIndex = 60;
        this.comboZip.TabStop = false;
        this.comboZip.SelectionChangeCommitted += new System.EventHandler(this.comboZip_SelectionChangeCommitted);
        //
        // textCountry
        //
        this.textCountry.Location = new System.Drawing.Point(190, 109);
        this.textCountry.MaxLength = 100;
        this.textCountry.Name = "textCountry";
        this.textCountry.Size = new System.Drawing.Size(191, 20);
        this.textCountry.TabIndex = 5;
        this.textCountry.TabStop = false;
        this.textCountry.TextChanged += new System.EventHandler(this.textState_TextChanged);
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(128, 109);
        this.textState.MaxLength = 100;
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(61, 20);
        this.textState.TabIndex = 5;
        this.textState.TabStop = false;
        this.textState.TextChanged += new System.EventHandler(this.textState_TextChanged);
        //
        // labelST
        //
        this.labelST.Location = new System.Drawing.Point(3, 113);
        this.labelST.Name = "labelST";
        this.labelST.Size = new System.Drawing.Size(123, 14);
        this.labelST.TabIndex = 13;
        this.labelST.Text = "ST, Country";
        this.labelST.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(128, 49);
        this.textAddress.MaxLength = 100;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(253, 20);
        this.textAddress.TabIndex = 1;
        this.textAddress.TextChanged += new System.EventHandler(this.textAddress_TextChanged);
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(3, 73);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(123, 14);
        this.label12.TabIndex = 11;
        this.label12.Text = "Address2";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelCity
        //
        this.labelCity.Location = new System.Drawing.Point(3, 93);
        this.labelCity.Name = "labelCity";
        this.labelCity.Size = new System.Drawing.Size(123, 14);
        this.labelCity.TabIndex = 12;
        this.labelCity.Text = "City";
        this.labelCity.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textAddress2
        //
        this.textAddress2.Location = new System.Drawing.Point(128, 69);
        this.textAddress2.MaxLength = 100;
        this.textAddress2.Name = "textAddress2";
        this.textAddress2.Size = new System.Drawing.Size(253, 20);
        this.textAddress2.TabIndex = 2;
        this.textAddress2.TextChanged += new System.EventHandler(this.textAddress2_TextChanged);
        //
        // labelZip
        //
        this.labelZip.Location = new System.Drawing.Point(3, 134);
        this.labelZip.Name = "labelZip";
        this.labelZip.Size = new System.Drawing.Size(123, 14);
        this.labelZip.TabIndex = 14;
        this.labelZip.Text = "Zip";
        this.labelZip.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(3, 31);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(123, 14);
        this.label16.TabIndex = 15;
        this.label16.Text = "Home Phone";
        this.label16.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(128, 89);
        this.textCity.MaxLength = 100;
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(191, 20);
        this.textCity.TabIndex = 4;
        this.textCity.TabStop = false;
        this.textCity.TextChanged += new System.EventHandler(this.textCity_TextChanged);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(3, 52);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(123, 14);
        this.label11.TabIndex = 10;
        this.label11.Text = "Address";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label39
        //
        this.label39.Location = new System.Drawing.Point(3, 166);
        this.label39.Name = "label39";
        this.label39.Size = new System.Drawing.Size(118, 16);
        this.label39.TabIndex = 68;
        this.label39.Text = "Secondary Provider";
        this.label39.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label38
        //
        this.label38.Location = new System.Drawing.Point(3, 144);
        this.label38.Name = "label38";
        this.label38.Size = new System.Drawing.Size(118, 16);
        this.label38.TabIndex = 67;
        this.label38.Text = "Primary Provider";
        this.label38.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSecProv1
        //
        this.comboSecProv1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSecProv1.FormattingEnabled = true;
        this.comboSecProv1.Location = new System.Drawing.Point(121, 164);
        this.comboSecProv1.MaxDropDownItems = 30;
        this.comboSecProv1.Name = "comboSecProv1";
        this.comboSecProv1.Size = new System.Drawing.Size(149, 21);
        this.comboSecProv1.TabIndex = 19;
        this.comboSecProv1.SelectionChangeCommitted += new System.EventHandler(this.comboSecProv1_SelectionChangeCommitted);
        //
        // comboPriProv1
        //
        this.comboPriProv1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriProv1.FormattingEnabled = true;
        this.comboPriProv1.Location = new System.Drawing.Point(121, 142);
        this.comboPriProv1.MaxDropDownItems = 30;
        this.comboPriProv1.Name = "comboPriProv1";
        this.comboPriProv1.Size = new System.Drawing.Size(149, 21);
        this.comboPriProv1.TabIndex = 17;
        this.comboPriProv1.SelectionChangeCommitted += new System.EventHandler(this.comboPriProv1_SelectionChangeCommitted);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(121, 11);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(149, 16);
        this.label3.TabIndex = 27;
        this.label3.Text = "Guarantor";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // checkInsOne1
        //
        this.checkInsOne1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsOne1.Location = new System.Drawing.Point(121, 121);
        this.checkInsOne1.Name = "checkInsOne1";
        this.checkInsOne1.Size = new System.Drawing.Size(37, 21);
        this.checkInsOne1.TabIndex = 30;
        this.checkInsOne1.TabStop = false;
        this.checkInsOne1.Text = "1";
        this.checkInsOne1.Click += new System.EventHandler(this.checkInsOne1_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(3, 122);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(118, 16);
        this.label4.TabIndex = 29;
        this.label4.Text = "Insurance";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // checkInsTwo1
        //
        this.checkInsTwo1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsTwo1.Location = new System.Drawing.Point(164, 121);
        this.checkInsTwo1.Name = "checkInsTwo1";
        this.checkInsTwo1.Size = new System.Drawing.Size(37, 21);
        this.checkInsTwo1.TabIndex = 32;
        this.checkInsTwo1.TabStop = false;
        this.checkInsTwo1.Text = "2";
        this.checkInsTwo1.Click += new System.EventHandler(this.checkInsTwo1_Click);
        //
        // checkInsTwo2
        //
        this.checkInsTwo2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsTwo2.Location = new System.Drawing.Point(317, 121);
        this.checkInsTwo2.Name = "checkInsTwo2";
        this.checkInsTwo2.Size = new System.Drawing.Size(37, 21);
        this.checkInsTwo2.TabIndex = 40;
        this.checkInsTwo2.TabStop = false;
        this.checkInsTwo2.Text = "2";
        //
        // checkInsOne2
        //
        this.checkInsOne2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsOne2.Location = new System.Drawing.Point(274, 121);
        this.checkInsOne2.Name = "checkInsOne2";
        this.checkInsOne2.Size = new System.Drawing.Size(37, 21);
        this.checkInsOne2.TabIndex = 39;
        this.checkInsOne2.TabStop = false;
        this.checkInsOne2.Text = "1";
        //
        // listPosition2
        //
        this.listPosition2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.listPosition2.Items.AddRange(new Object[]{ "Single", "Married" });
        this.listPosition2.Location = new System.Drawing.Point(336, 70);
        this.listPosition2.Name = "listPosition2";
        this.listPosition2.Size = new System.Drawing.Size(61, 30);
        this.listPosition2.TabIndex = 37;
        //
        // listGender2
        //
        this.listGender2.Items.AddRange(new Object[]{ "Male", "Female" });
        this.listGender2.Location = new System.Drawing.Point(274, 70);
        this.listGender2.Name = "listGender2";
        this.listGender2.Size = new System.Drawing.Size(61, 30);
        this.listGender2.TabIndex = 36;
        //
        // textFName2
        //
        this.textFName2.Location = new System.Drawing.Point(274, 49);
        this.textFName2.MaxLength = 100;
        this.textFName2.Name = "textFName2";
        this.textFName2.Size = new System.Drawing.Size(149, 20);
        this.textFName2.TabIndex = 2;
        this.textFName2.TextChanged += new System.EventHandler(this.textFName2_TextChanged);
        //
        // textLName2
        //
        this.textLName2.Location = new System.Drawing.Point(274, 28);
        this.textLName2.MaxLength = 100;
        this.textLName2.Name = "textLName2";
        this.textLName2.Size = new System.Drawing.Size(149, 20);
        this.textLName2.TabIndex = 33;
        this.textLName2.TextChanged += new System.EventHandler(this.textLName2_TextChanged);
        //
        // checkInsTwo3
        //
        this.checkInsTwo3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsTwo3.Location = new System.Drawing.Point(470, 121);
        this.checkInsTwo3.Name = "checkInsTwo3";
        this.checkInsTwo3.Size = new System.Drawing.Size(37, 21);
        this.checkInsTwo3.TabIndex = 48;
        this.checkInsTwo3.TabStop = false;
        this.checkInsTwo3.Text = "2";
        //
        // checkInsOne3
        //
        this.checkInsOne3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsOne3.Location = new System.Drawing.Point(427, 121);
        this.checkInsOne3.Name = "checkInsOne3";
        this.checkInsOne3.Size = new System.Drawing.Size(37, 21);
        this.checkInsOne3.TabIndex = 47;
        this.checkInsOne3.TabStop = false;
        this.checkInsOne3.Text = "1";
        //
        // listGender3
        //
        this.listGender3.Items.AddRange(new Object[]{ "Male", "Female" });
        this.listGender3.Location = new System.Drawing.Point(427, 70);
        this.listGender3.Name = "listGender3";
        this.listGender3.Size = new System.Drawing.Size(61, 30);
        this.listGender3.TabIndex = 44;
        //
        // textFName3
        //
        this.textFName3.Location = new System.Drawing.Point(427, 49);
        this.textFName3.MaxLength = 100;
        this.textFName3.Name = "textFName3";
        this.textFName3.Size = new System.Drawing.Size(149, 20);
        this.textFName3.TabIndex = 3;
        this.textFName3.TextChanged += new System.EventHandler(this.textFName3_TextChanged);
        //
        // textLName3
        //
        this.textLName3.Location = new System.Drawing.Point(427, 28);
        this.textLName3.MaxLength = 100;
        this.textLName3.Name = "textLName3";
        this.textLName3.Size = new System.Drawing.Size(149, 20);
        this.textLName3.TabIndex = 41;
        this.textLName3.TextChanged += new System.EventHandler(this.textLName3_TextChanged);
        //
        // checkInsTwo4
        //
        this.checkInsTwo4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsTwo4.Location = new System.Drawing.Point(623, 121);
        this.checkInsTwo4.Name = "checkInsTwo4";
        this.checkInsTwo4.Size = new System.Drawing.Size(37, 21);
        this.checkInsTwo4.TabIndex = 56;
        this.checkInsTwo4.TabStop = false;
        this.checkInsTwo4.Text = "2";
        //
        // checkInsOne4
        //
        this.checkInsOne4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsOne4.Location = new System.Drawing.Point(580, 121);
        this.checkInsOne4.Name = "checkInsOne4";
        this.checkInsOne4.Size = new System.Drawing.Size(37, 21);
        this.checkInsOne4.TabIndex = 55;
        this.checkInsOne4.TabStop = false;
        this.checkInsOne4.Text = "1";
        //
        // listGender4
        //
        this.listGender4.Items.AddRange(new Object[]{ "Male", "Female" });
        this.listGender4.Location = new System.Drawing.Point(580, 70);
        this.listGender4.Name = "listGender4";
        this.listGender4.Size = new System.Drawing.Size(61, 30);
        this.listGender4.TabIndex = 52;
        //
        // textFName4
        //
        this.textFName4.Location = new System.Drawing.Point(580, 49);
        this.textFName4.MaxLength = 100;
        this.textFName4.Name = "textFName4";
        this.textFName4.Size = new System.Drawing.Size(149, 20);
        this.textFName4.TabIndex = 4;
        this.textFName4.TextChanged += new System.EventHandler(this.textFName4_TextChanged);
        //
        // checkInsTwo5
        //
        this.checkInsTwo5.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsTwo5.Location = new System.Drawing.Point(776, 121);
        this.checkInsTwo5.Name = "checkInsTwo5";
        this.checkInsTwo5.Size = new System.Drawing.Size(37, 21);
        this.checkInsTwo5.TabIndex = 64;
        this.checkInsTwo5.TabStop = false;
        this.checkInsTwo5.Text = "2";
        //
        // checkInsOne5
        //
        this.checkInsOne5.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInsOne5.Location = new System.Drawing.Point(733, 121);
        this.checkInsOne5.Name = "checkInsOne5";
        this.checkInsOne5.Size = new System.Drawing.Size(37, 21);
        this.checkInsOne5.TabIndex = 63;
        this.checkInsOne5.TabStop = false;
        this.checkInsOne5.Text = "1";
        //
        // listGender5
        //
        this.listGender5.Items.AddRange(new Object[]{ "Male", "Female" });
        this.listGender5.Location = new System.Drawing.Point(733, 70);
        this.listGender5.Name = "listGender5";
        this.listGender5.Size = new System.Drawing.Size(61, 30);
        this.listGender5.TabIndex = 60;
        //
        // textFName5
        //
        this.textFName5.Location = new System.Drawing.Point(733, 49);
        this.textFName5.MaxLength = 100;
        this.textFName5.Name = "textFName5";
        this.textFName5.Size = new System.Drawing.Size(149, 20);
        this.textFName5.TabIndex = 5;
        this.textFName5.TextChanged += new System.EventHandler(this.textFName5_TextChanged);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 408);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(153, 58);
        this.label6.TabIndex = 69;
        this.label6.Text = "Address and Phone Notes";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPhone1
        //
        this.textPhone1.Location = new System.Drawing.Point(95, 95);
        this.textPhone1.MaxLength = 30;
        this.textPhone1.Name = "textPhone1";
        this.textPhone1.Size = new System.Drawing.Size(157, 20);
        this.textPhone1.TabIndex = 4;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(3, 98);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(91, 15);
        this.label13.TabIndex = 155;
        this.label13.Text = "Phone";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCarrier1
        //
        this.textCarrier1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textCarrier1.Location = new System.Drawing.Point(95, 75);
        this.textCarrier1.MaxLength = 50;
        this.textCarrier1.Multiline = true;
        this.textCarrier1.Name = "textCarrier1";
        this.textCarrier1.Size = new System.Drawing.Size(254, 20);
        this.textCarrier1.TabIndex = 3;
        this.textCarrier1.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textCarrier1_KeyUp);
        this.textCarrier1.Leave += new System.EventHandler(this.textCarrier1_Leave);
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(3, 77);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(91, 15);
        this.label17.TabIndex = 156;
        this.label17.Text = "Carrier";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupIns1
        //
        this.groupIns1.Controls.Add(this.butPick1);
        this.groupIns1.Controls.Add(this.textGroupNum1);
        this.groupIns1.Controls.Add(this.labelGroupNum);
        this.groupIns1.Controls.Add(this.label19);
        this.groupIns1.Controls.Add(this.textGroupName1);
        this.groupIns1.Controls.Add(this.textEmployer1);
        this.groupIns1.Controls.Add(this.label18);
        this.groupIns1.Controls.Add(this.textSubscriberID1);
        this.groupIns1.Controls.Add(this.label15);
        this.groupIns1.Controls.Add(this.comboSubscriber1);
        this.groupIns1.Controls.Add(this.label14);
        this.groupIns1.Controls.Add(this.textPhone1);
        this.groupIns1.Controls.Add(this.label17);
        this.groupIns1.Controls.Add(this.label13);
        this.groupIns1.Controls.Add(this.textCarrier1);
        this.groupIns1.Location = new System.Drawing.Point(444, 211);
        this.groupIns1.Name = "groupIns1";
        this.groupIns1.Size = new System.Drawing.Size(396, 167);
        this.groupIns1.TabIndex = 15;
        this.groupIns1.TabStop = false;
        this.groupIns1.Text = "Insurance 1";
        //
        // butPick1
        //
        this.butPick1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick1.setAutosize(true);
        this.butPick1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick1.setCornerRadius(4F);
        this.butPick1.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPick1.Location = new System.Drawing.Point(305, 31);
        this.butPick1.Name = "butPick1";
        this.butPick1.Size = new System.Drawing.Size(90, 23);
        this.butPick1.TabIndex = 168;
        this.butPick1.Text = "Pick From List";
        this.butPick1.Click += new System.EventHandler(this.butPick1_Click);
        //
        // textGroupNum1
        //
        this.textGroupNum1.Location = new System.Drawing.Point(95, 135);
        this.textGroupNum1.MaxLength = 20;
        this.textGroupNum1.Name = "textGroupNum1";
        this.textGroupNum1.Size = new System.Drawing.Size(129, 20);
        this.textGroupNum1.TabIndex = 6;
        //
        // labelGroupNum
        //
        this.labelGroupNum.Location = new System.Drawing.Point(3, 138);
        this.labelGroupNum.Name = "labelGroupNum";
        this.labelGroupNum.Size = new System.Drawing.Size(91, 15);
        this.labelGroupNum.TabIndex = 167;
        this.labelGroupNum.Text = "Group Num";
        this.labelGroupNum.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(3, 118);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(91, 15);
        this.label19.TabIndex = 166;
        this.label19.Text = "Group Name";
        this.label19.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textGroupName1
        //
        this.textGroupName1.Location = new System.Drawing.Point(95, 115);
        this.textGroupName1.MaxLength = 50;
        this.textGroupName1.Name = "textGroupName1";
        this.textGroupName1.Size = new System.Drawing.Size(193, 20);
        this.textGroupName1.TabIndex = 5;
        //
        // textEmployer1
        //
        this.textEmployer1.Location = new System.Drawing.Point(95, 55);
        this.textEmployer1.MaxLength = 40;
        this.textEmployer1.Name = "textEmployer1";
        this.textEmployer1.Size = new System.Drawing.Size(254, 20);
        this.textEmployer1.TabIndex = 2;
        this.textEmployer1.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textEmployer1_KeyUp);
        this.textEmployer1.Leave += new System.EventHandler(this.textEmployer1_Leave);
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(3, 57);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(91, 15);
        this.label18.TabIndex = 162;
        this.label18.Text = "Employer";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubscriberID1
        //
        this.textSubscriberID1.Location = new System.Drawing.Point(95, 35);
        this.textSubscriberID1.MaxLength = 20;
        this.textSubscriberID1.Name = "textSubscriberID1";
        this.textSubscriberID1.Size = new System.Drawing.Size(129, 20);
        this.textSubscriberID1.TabIndex = 1;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(3, 37);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(91, 15);
        this.label15.TabIndex = 160;
        this.label15.Text = "Subscriber ID";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboSubscriber1
        //
        this.comboSubscriber1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSubscriber1.FormattingEnabled = true;
        this.comboSubscriber1.Location = new System.Drawing.Point(95, 14);
        this.comboSubscriber1.MaxDropDownItems = 30;
        this.comboSubscriber1.Name = "comboSubscriber1";
        this.comboSubscriber1.Size = new System.Drawing.Size(198, 21);
        this.comboSubscriber1.TabIndex = 0;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(3, 18);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(91, 15);
        this.label14.TabIndex = 157;
        this.label14.Text = "Subscriber";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(491, 73);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(61, 16);
        this.label20.TabIndex = 158;
        this.label20.Text = "Child";
        this.label20.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(644, 73);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(61, 16);
        this.label21.TabIndex = 159;
        this.label21.Text = "Child";
        this.label21.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(797, 73);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(61, 16);
        this.label22.TabIndex = 160;
        this.label22.Text = "Child";
        this.label22.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupIns2
        //
        this.groupIns2.Controls.Add(this.butPick2);
        this.groupIns2.Controls.Add(this.textGroupNum2);
        this.groupIns2.Controls.Add(this.label23);
        this.groupIns2.Controls.Add(this.label24);
        this.groupIns2.Controls.Add(this.textGroupName2);
        this.groupIns2.Controls.Add(this.textEmployer2);
        this.groupIns2.Controls.Add(this.label25);
        this.groupIns2.Controls.Add(this.textSubscriberID2);
        this.groupIns2.Controls.Add(this.label26);
        this.groupIns2.Controls.Add(this.comboSubscriber2);
        this.groupIns2.Controls.Add(this.label27);
        this.groupIns2.Controls.Add(this.textPhone2);
        this.groupIns2.Controls.Add(this.label28);
        this.groupIns2.Controls.Add(this.label29);
        this.groupIns2.Controls.Add(this.textCarrier2);
        this.groupIns2.Location = new System.Drawing.Point(444, 384);
        this.groupIns2.Name = "groupIns2";
        this.groupIns2.Size = new System.Drawing.Size(396, 167);
        this.groupIns2.TabIndex = 16;
        this.groupIns2.TabStop = false;
        this.groupIns2.Text = "Insurance 2";
        //
        // butPick2
        //
        this.butPick2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick2.setAutosize(true);
        this.butPick2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick2.setCornerRadius(4F);
        this.butPick2.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPick2.Location = new System.Drawing.Point(305, 31);
        this.butPick2.Name = "butPick2";
        this.butPick2.Size = new System.Drawing.Size(90, 23);
        this.butPick2.TabIndex = 169;
        this.butPick2.Text = "Pick From List";
        this.butPick2.Click += new System.EventHandler(this.butPick2_Click);
        //
        // textGroupNum2
        //
        this.textGroupNum2.Location = new System.Drawing.Point(95, 135);
        this.textGroupNum2.MaxLength = 20;
        this.textGroupNum2.Name = "textGroupNum2";
        this.textGroupNum2.Size = new System.Drawing.Size(129, 20);
        this.textGroupNum2.TabIndex = 6;
        //
        // label23
        //
        this.label23.Location = new System.Drawing.Point(3, 138);
        this.label23.Name = "label23";
        this.label23.Size = new System.Drawing.Size(91, 15);
        this.label23.TabIndex = 167;
        this.label23.Text = "Group Num";
        this.label23.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label24
        //
        this.label24.Location = new System.Drawing.Point(3, 118);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(91, 15);
        this.label24.TabIndex = 166;
        this.label24.Text = "Group Name";
        this.label24.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textGroupName2
        //
        this.textGroupName2.Location = new System.Drawing.Point(95, 115);
        this.textGroupName2.MaxLength = 50;
        this.textGroupName2.Name = "textGroupName2";
        this.textGroupName2.Size = new System.Drawing.Size(193, 20);
        this.textGroupName2.TabIndex = 5;
        //
        // textEmployer2
        //
        this.textEmployer2.Location = new System.Drawing.Point(95, 55);
        this.textEmployer2.MaxLength = 40;
        this.textEmployer2.Name = "textEmployer2";
        this.textEmployer2.Size = new System.Drawing.Size(254, 20);
        this.textEmployer2.TabIndex = 2;
        this.textEmployer2.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textEmployer2_KeyUp);
        this.textEmployer2.Leave += new System.EventHandler(this.textEmployer2_Leave);
        //
        // label25
        //
        this.label25.Location = new System.Drawing.Point(3, 57);
        this.label25.Name = "label25";
        this.label25.Size = new System.Drawing.Size(91, 15);
        this.label25.TabIndex = 162;
        this.label25.Text = "Employer";
        this.label25.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubscriberID2
        //
        this.textSubscriberID2.Location = new System.Drawing.Point(95, 35);
        this.textSubscriberID2.MaxLength = 20;
        this.textSubscriberID2.Name = "textSubscriberID2";
        this.textSubscriberID2.Size = new System.Drawing.Size(129, 20);
        this.textSubscriberID2.TabIndex = 1;
        //
        // label26
        //
        this.label26.Location = new System.Drawing.Point(3, 37);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(91, 15);
        this.label26.TabIndex = 160;
        this.label26.Text = "Subscriber ID";
        this.label26.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboSubscriber2
        //
        this.comboSubscriber2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSubscriber2.FormattingEnabled = true;
        this.comboSubscriber2.Location = new System.Drawing.Point(95, 14);
        this.comboSubscriber2.MaxDropDownItems = 30;
        this.comboSubscriber2.Name = "comboSubscriber2";
        this.comboSubscriber2.Size = new System.Drawing.Size(198, 21);
        this.comboSubscriber2.TabIndex = 0;
        //
        // label27
        //
        this.label27.Location = new System.Drawing.Point(3, 18);
        this.label27.Name = "label27";
        this.label27.Size = new System.Drawing.Size(91, 15);
        this.label27.TabIndex = 157;
        this.label27.Text = "Subscriber";
        this.label27.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPhone2
        //
        this.textPhone2.Location = new System.Drawing.Point(95, 95);
        this.textPhone2.MaxLength = 30;
        this.textPhone2.Name = "textPhone2";
        this.textPhone2.Size = new System.Drawing.Size(157, 20);
        this.textPhone2.TabIndex = 4;
        //
        // label28
        //
        this.label28.Location = new System.Drawing.Point(3, 77);
        this.label28.Name = "label28";
        this.label28.Size = new System.Drawing.Size(91, 15);
        this.label28.TabIndex = 156;
        this.label28.Text = "Carrier";
        this.label28.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label29
        //
        this.label29.Location = new System.Drawing.Point(3, 98);
        this.label29.Name = "label29";
        this.label29.Size = new System.Drawing.Size(91, 15);
        this.label29.TabIndex = 155;
        this.label29.Text = "Phone";
        this.label29.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCarrier2
        //
        this.textCarrier2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textCarrier2.Location = new System.Drawing.Point(95, 75);
        this.textCarrier2.MaxLength = 50;
        this.textCarrier2.Multiline = true;
        this.textCarrier2.Name = "textCarrier2";
        this.textCarrier2.Size = new System.Drawing.Size(254, 20);
        this.textCarrier2.TabIndex = 3;
        this.textCarrier2.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textCarrier2_KeyUp);
        this.textCarrier2.Leave += new System.EventHandler(this.textCarrier2_Leave);
        //
        // label30
        //
        this.label30.Location = new System.Drawing.Point(3, 479);
        this.label30.Name = "label30";
        this.label30.Size = new System.Drawing.Size(153, 15);
        this.label30.TabIndex = 163;
        this.label30.Text = "Referral Source";
        this.label30.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textReferral
        //
        this.textReferral.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textReferral.Location = new System.Drawing.Point(157, 477);
        this.textReferral.MaxLength = 50;
        this.textReferral.Multiline = true;
        this.textReferral.Name = "textReferral";
        this.textReferral.Size = new System.Drawing.Size(237, 20);
        this.textReferral.TabIndex = 13;
        this.textReferral.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textReferral_KeyUp);
        this.textReferral.Leave += new System.EventHandler(this.textReferral_Leave);
        //
        // textAge1
        //
        this.textAge1.Location = new System.Drawing.Point(204, 101);
        this.textAge1.Name = "textAge1";
        this.textAge1.ReadOnly = true;
        this.textAge1.Size = new System.Drawing.Size(50, 20);
        this.textAge1.TabIndex = 164;
        //
        // textAge2
        //
        this.textAge2.Location = new System.Drawing.Point(357, 101);
        this.textAge2.Name = "textAge2";
        this.textAge2.ReadOnly = true;
        this.textAge2.Size = new System.Drawing.Size(50, 20);
        this.textAge2.TabIndex = 166;
        //
        // textAge3
        //
        this.textAge3.Location = new System.Drawing.Point(510, 101);
        this.textAge3.Name = "textAge3";
        this.textAge3.ReadOnly = true;
        this.textAge3.Size = new System.Drawing.Size(50, 20);
        this.textAge3.TabIndex = 167;
        //
        // textAge4
        //
        this.textAge4.Location = new System.Drawing.Point(663, 101);
        this.textAge4.Name = "textAge4";
        this.textAge4.ReadOnly = true;
        this.textAge4.Size = new System.Drawing.Size(50, 20);
        this.textAge4.TabIndex = 168;
        //
        // textAge5
        //
        this.textAge5.Location = new System.Drawing.Point(816, 101);
        this.textAge5.Name = "textAge5";
        this.textAge5.ReadOnly = true;
        this.textAge5.Size = new System.Drawing.Size(50, 20);
        this.textAge5.TabIndex = 169;
        //
        // comboSecProv2
        //
        this.comboSecProv2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSecProv2.FormattingEnabled = true;
        this.comboSecProv2.Location = new System.Drawing.Point(274, 164);
        this.comboSecProv2.MaxDropDownItems = 30;
        this.comboSecProv2.Name = "comboSecProv2";
        this.comboSecProv2.Size = new System.Drawing.Size(149, 21);
        this.comboSecProv2.TabIndex = 171;
        //
        // comboPriProv2
        //
        this.comboPriProv2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriProv2.FormattingEnabled = true;
        this.comboPriProv2.Location = new System.Drawing.Point(274, 142);
        this.comboPriProv2.MaxDropDownItems = 30;
        this.comboPriProv2.Name = "comboPriProv2";
        this.comboPriProv2.Size = new System.Drawing.Size(149, 21);
        this.comboPriProv2.TabIndex = 170;
        //
        // comboSecProv3
        //
        this.comboSecProv3.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSecProv3.FormattingEnabled = true;
        this.comboSecProv3.Location = new System.Drawing.Point(427, 164);
        this.comboSecProv3.MaxDropDownItems = 30;
        this.comboSecProv3.Name = "comboSecProv3";
        this.comboSecProv3.Size = new System.Drawing.Size(149, 21);
        this.comboSecProv3.TabIndex = 173;
        //
        // comboPriProv3
        //
        this.comboPriProv3.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriProv3.FormattingEnabled = true;
        this.comboPriProv3.Location = new System.Drawing.Point(427, 142);
        this.comboPriProv3.MaxDropDownItems = 30;
        this.comboPriProv3.Name = "comboPriProv3";
        this.comboPriProv3.Size = new System.Drawing.Size(149, 21);
        this.comboPriProv3.TabIndex = 172;
        //
        // comboSecProv4
        //
        this.comboSecProv4.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSecProv4.FormattingEnabled = true;
        this.comboSecProv4.Location = new System.Drawing.Point(580, 164);
        this.comboSecProv4.MaxDropDownItems = 30;
        this.comboSecProv4.Name = "comboSecProv4";
        this.comboSecProv4.Size = new System.Drawing.Size(149, 21);
        this.comboSecProv4.TabIndex = 175;
        //
        // comboPriProv4
        //
        this.comboPriProv4.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriProv4.FormattingEnabled = true;
        this.comboPriProv4.Location = new System.Drawing.Point(580, 142);
        this.comboPriProv4.MaxDropDownItems = 30;
        this.comboPriProv4.Name = "comboPriProv4";
        this.comboPriProv4.Size = new System.Drawing.Size(149, 21);
        this.comboPriProv4.TabIndex = 174;
        //
        // comboSecProv5
        //
        this.comboSecProv5.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSecProv5.FormattingEnabled = true;
        this.comboSecProv5.Location = new System.Drawing.Point(733, 164);
        this.comboSecProv5.MaxDropDownItems = 30;
        this.comboSecProv5.Name = "comboSecProv5";
        this.comboSecProv5.Size = new System.Drawing.Size(149, 21);
        this.comboSecProv5.TabIndex = 177;
        //
        // comboPriProv5
        //
        this.comboPriProv5.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriProv5.FormattingEnabled = true;
        this.comboPriProv5.Location = new System.Drawing.Point(733, 142);
        this.comboPriProv5.MaxDropDownItems = 30;
        this.comboPriProv5.Name = "comboPriProv5";
        this.comboPriProv5.Size = new System.Drawing.Size(149, 21);
        this.comboPriProv5.TabIndex = 176;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(3, 499);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(153, 15);
        this.label8.TabIndex = 179;
        this.label8.Text = "Referral First Name";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textReferralFName
        //
        this.textReferralFName.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textReferralFName.Location = new System.Drawing.Point(157, 497);
        this.textReferralFName.MaxLength = 50;
        this.textReferralFName.Multiline = true;
        this.textReferralFName.Name = "textReferralFName";
        this.textReferralFName.Size = new System.Drawing.Size(141, 20);
        this.textReferralFName.TabIndex = 14;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(299, 499);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(138, 15);
        this.label9.TabIndex = 180;
        this.label9.Text = "(if applicable)";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textAddrNotes
        //
        this.textAddrNotes.AcceptsTab = true;
        this.textAddrNotes.DetectUrls = false;
        this.textAddrNotes.Location = new System.Drawing.Point(157, 407);
        this.textAddrNotes.Name = "textAddrNotes";
        this.textAddrNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.PatAddressNote);
        this.textAddrNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textAddrNotes.Size = new System.Drawing.Size(237, 59);
        this.textAddrNotes.TabIndex = 12;
        this.textAddrNotes.Text = "";
        //
        // textBirthdate5
        //
        this.textBirthdate5.Location = new System.Drawing.Point(733, 101);
        this.textBirthdate5.Name = "textBirthdate5";
        this.textBirthdate5.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate5.TabIndex = 10;
        this.textBirthdate5.Validated += new System.EventHandler(this.textBirthdate5_Validated);
        //
        // textBirthdate4
        //
        this.textBirthdate4.Location = new System.Drawing.Point(580, 101);
        this.textBirthdate4.Name = "textBirthdate4";
        this.textBirthdate4.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate4.TabIndex = 9;
        this.textBirthdate4.Validated += new System.EventHandler(this.textBirthdate4_Validated);
        //
        // textBirthdate3
        //
        this.textBirthdate3.Location = new System.Drawing.Point(427, 101);
        this.textBirthdate3.Name = "textBirthdate3";
        this.textBirthdate3.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate3.TabIndex = 8;
        this.textBirthdate3.Validated += new System.EventHandler(this.textBirthdate3_Validated);
        //
        // textBirthdate2
        //
        this.textBirthdate2.Location = new System.Drawing.Point(274, 101);
        this.textBirthdate2.Name = "textBirthdate2";
        this.textBirthdate2.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate2.TabIndex = 7;
        this.textBirthdate2.Validated += new System.EventHandler(this.textBirthdate2_Validated);
        //
        // textBirthdate1
        //
        this.textBirthdate1.Location = new System.Drawing.Point(121, 101);
        this.textBirthdate1.Name = "textBirthdate1";
        this.textBirthdate1.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate1.TabIndex = 6;
        this.textBirthdate1.Validated += new System.EventHandler(this.textBirthdate1_Validated);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(796, 582);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 16;
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
        this.butCancel.Location = new System.Drawing.Point(796, 623);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 17;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPatientAddAll
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(896, 674);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textReferralFName);
        this.Controls.Add(this.comboSecProv5);
        this.Controls.Add(this.comboPriProv5);
        this.Controls.Add(this.comboSecProv4);
        this.Controls.Add(this.comboPriProv4);
        this.Controls.Add(this.comboSecProv3);
        this.Controls.Add(this.comboPriProv3);
        this.Controls.Add(this.comboSecProv2);
        this.Controls.Add(this.comboPriProv2);
        this.Controls.Add(this.label39);
        this.Controls.Add(this.textAge5);
        this.Controls.Add(this.comboSecProv1);
        this.Controls.Add(this.label38);
        this.Controls.Add(this.comboPriProv1);
        this.Controls.Add(this.textAge4);
        this.Controls.Add(this.textAge3);
        this.Controls.Add(this.textAge2);
        this.Controls.Add(this.textAge1);
        this.Controls.Add(this.label30);
        this.Controls.Add(this.textReferral);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textAddrNotes);
        this.Controls.Add(this.groupIns2);
        this.Controls.Add(this.textLName2);
        this.Controls.Add(this.textLName1);
        this.Controls.Add(this.label22);
        this.Controls.Add(this.label21);
        this.Controls.Add(this.label20);
        this.Controls.Add(this.groupIns1);
        this.Controls.Add(this.checkInsTwo5);
        this.Controls.Add(this.checkInsOne5);
        this.Controls.Add(this.listGender5);
        this.Controls.Add(this.textBirthdate5);
        this.Controls.Add(this.textFName5);
        this.Controls.Add(this.textLName5);
        this.Controls.Add(this.checkInsTwo4);
        this.Controls.Add(this.checkInsOne4);
        this.Controls.Add(this.listGender4);
        this.Controls.Add(this.textBirthdate4);
        this.Controls.Add(this.textFName4);
        this.Controls.Add(this.textLName4);
        this.Controls.Add(this.checkInsTwo3);
        this.Controls.Add(this.checkInsOne3);
        this.Controls.Add(this.listGender3);
        this.Controls.Add(this.textBirthdate3);
        this.Controls.Add(this.textFName3);
        this.Controls.Add(this.textLName3);
        this.Controls.Add(this.checkInsTwo2);
        this.Controls.Add(this.checkInsOne2);
        this.Controls.Add(this.listPosition2);
        this.Controls.Add(this.listGender2);
        this.Controls.Add(this.textBirthdate2);
        this.Controls.Add(this.textFName2);
        this.Controls.Add(this.checkInsTwo1);
        this.Controls.Add(this.checkInsOne1);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.listPosition1);
        this.Controls.Add(this.listGender1);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textBirthdate1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textFName1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormPatientAddAll";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Add Family";
        this.Load += new System.EventHandler(this.FormPatientAddAll_Load);
        this.Shown += new System.EventHandler(this.FormPatientAddAll_Shown);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupIns1.ResumeLayout(false);
        this.groupIns1.PerformLayout();
        this.groupIns2.ResumeLayout(false);
        this.groupIns2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textLName1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFName1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textBirthdate1;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPosition1 = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listGender1 = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label39 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label38 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSecProv1 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPriProv1 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textHmPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox comboZip = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelST = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCity = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddress2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelZip = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkInsOne1 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkInsTwo1 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkInsTwo2 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkInsOne2 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ListBox listPosition2 = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listGender2 = new System.Windows.Forms.ListBox();
    private OpenDental.ValidDate textBirthdate2;
    private System.Windows.Forms.TextBox textFName2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textLName2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkInsTwo3 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkInsOne3 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ListBox listGender3 = new System.Windows.Forms.ListBox();
    private OpenDental.ValidDate textBirthdate3;
    private System.Windows.Forms.TextBox textFName3 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textLName3 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkInsTwo4 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkInsOne4 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ListBox listGender4 = new System.Windows.Forms.ListBox();
    private OpenDental.ValidDate textBirthdate4;
    private System.Windows.Forms.TextBox textFName4 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkInsTwo5 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkInsOne5 = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ListBox listGender5 = new System.Windows.Forms.ListBox();
    private OpenDental.ValidDate textBirthdate5;
    private System.Windows.Forms.TextBox textFName5 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textAddrNotes;
    private System.Windows.Forms.TextBox textPhone1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCarrier1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupIns1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ComboBox comboSubscriber1 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubscriberID1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEmployer1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label18 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textGroupNum1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelGroupNum = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label19 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textGroupName1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label20 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label21 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label22 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupIns2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textGroupNum2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label23 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label24 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textGroupName2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textEmployer2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label25 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubscriberID2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label26 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSubscriber2 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label27 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPhone2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label28 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label29 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCarrier2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label30 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReferral = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAge1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAge2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAge3 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAge4 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAge5 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butPick1;
    private OpenDental.UI.Button butPick2;
    private System.Windows.Forms.TextBox textLName4 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textLName5 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox comboSecProv2 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPriProv2 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboSecProv3 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPriProv3 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboSecProv4 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPriProv4 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboSecProv5 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPriProv5 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReferralFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCountry = new System.Windows.Forms.TextBox();
}


