//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormImportXML;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.Employers;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Relat;
import OpenDentBusiness.TelephoneNumbers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormImportXML  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * 
    */
    public TextBox textMain = new TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Patient pat;
    private Patient guar;
    private Patient subsc;
    private InsPlan plan;
    private InsSub sub;
    private Carrier carrier;
    private String targetVersion = "";
    private String warnings = "";
    /**
    * self, parent, or other
    */
    private String guarRelat = new String();
    private String GuarEmp = new String();
    private String InsEmp = new String();
    /**
    * self, parent, spouse, or guardian
    */
    private String insRelat = new String();
    private String NoteMedicalComp = new String();
    private boolean insPresent = new boolean();
    private double annualMax = new double();
    private double deductible = new double();
    private Patient existingPatOld;
    /**
    * 
    */
    public FormImportXML() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormImportXML.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textMain = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(756, 649);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
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
        this.butOK.Location = new System.Drawing.Point(756, 613);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "Import";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textMain
        //
        this.textMain.Location = new System.Drawing.Point(7, 7);
        this.textMain.Multiline = true;
        this.textMain.Name = "textMain";
        this.textMain.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textMain.Size = new System.Drawing.Size(737, 673);
        this.textMain.TabIndex = 2;
        //
        // FormImportXML
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(847, 687);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormImportXML";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Import Patient";
        this.Load += new System.EventHandler(this.FormImportXML_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formImportXML_Load(Object sender, System.EventArgs e) throws Exception {
    }

    /**
    * 
    */
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textMain.Text, ""))
        {
            MsgBox.show(this,"Please paste the text generated by the other program into the large box first.");
            return ;
        }
         
        pat = new Patient();
        pat.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
        pat.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        guar = new Patient();
        guar.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
        guar.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        subsc = new Patient();
        subsc.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
        subsc.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        sub = new InsSub();
        sub.ReleaseInfo = true;
        sub.AssignBen = true;
        plan = new InsPlan();
        carrier = new Carrier();
        insRelat = "self";
        //this is the default if not included
        guarRelat = "self";
        InsEmp = "";
        GuarEmp = "";
        NoteMedicalComp = "";
        insPresent = false;
        annualMax = -1;
        deductible = -1;
        XmlTextReader reader = new XmlTextReader(new StringReader(textMain.Text));
        reader.WhitespaceHandling = WhitespaceHandling.None;
        String element = "";
        String textValue = "";
        String rootElement = "";
        String segment = "";
        //eg PatientIdentification
        String field = "";
        //eg NameLast
        String endelement = "";
        warnings = "";
        try
        {
            while (reader.Read())
            {
                NodeType __dummyScrutVar0 = reader.NodeType;
                if (__dummyScrutVar0.equals(XmlNodeType.Element))
                {
                    element = reader.Name;
                    if (StringSupport.equals(rootElement, ""))
                    {
                        //should be the first node
                        if (StringSupport.equals(element, "Message"))
                        {
                            rootElement = "Message";
                        }
                        else
                        {
                            throw new Exception(element + " should not be the first element.");
                        } 
                    }
                    else if (StringSupport.equals(segment, ""))
                    {
                        //expecting a new segment
                        segment = element;
                        if (!StringSupport.equals(segment, "MessageHeader") && !StringSupport.equals(segment, "PatientIdentification") && !StringSupport.equals(segment, "Guarantor") && !StringSupport.equals(segment, "Insurance"))
                        {
                            throw new Exception(segment + " is not a recognized segment.");
                        }
                         
                    }
                    else
                    {
                        //expecting a new field
                        field = element;
                    }  
                    if (StringSupport.equals(segment, "Insurance"))
                    {
                        insPresent = true;
                    }
                     
                }
                else if (__dummyScrutVar0.equals(XmlNodeType.Text))
                {
                    textValue = reader.Value;
                    if (StringSupport.equals(field, ""))
                    {
                        throw new Exception("Unexpected text: " + textValue);
                    }
                     
                }
                else if (__dummyScrutVar0.equals(XmlNodeType.EndElement))
                {
                    endelement = reader.Name;
                    if (StringSupport.equals(field, ""))
                    {
                        //we're not in a field, so we must be closing a segment or rootelement
                        if (StringSupport.equals(segment, ""))
                        {
                            //we're not in a segment, so we must be closing the rootelement
                            if (StringSupport.equals(rootElement, "Message"))
                            {
                                rootElement = "";
                            }
                            else
                            {
                                throw new Exception("Message closing element expected.");
                            } 
                        }
                        else
                        {
                            //must be closing a segment
                            segment = "";
                        } 
                    }
                    else
                    {
                        field = "";
                        //closing a field
                        textValue = "";
                    } 
                }
                   
                //switch
                if (StringSupport.equals(rootElement, ""))
                {
                    break;
                }
                 
                //this will ignore anything after the message endelement
                if (!StringSupport.equals(field, "") && !StringSupport.equals(textValue, ""))
                {
                    if (StringSupport.equals(segment, "MessageHeader"))
                    {
                        processMSH(field,textValue);
                    }
                    else if (StringSupport.equals(segment, "PatientIdentification"))
                    {
                        processPID(field,textValue);
                    }
                    else if (StringSupport.equals(segment, "Guarantor"))
                    {
                        processGT(field,textValue);
                    }
                    else if (StringSupport.equals(segment, "Insurance"))
                    {
                        processINS(field,textValue);
                    }
                        
                }
                 
            }
        }
        catch (Exception ex)
        {
            //while
            MessageBox.Show(ex.Message);
            //MsgBox.Show(this,"Error in the XML format.");
            reader.Close();
            return ;
        }
        finally
        {
            reader.Close();
        }
        //Warnings and errors-----------------------------------------------------------------------------
        if (StringSupport.equals(pat.LName, "") || StringSupport.equals(pat.FName, "") || pat.Birthdate.Year < 1880)
        {
            MsgBox.show(this,"Patient first and last name and birthdate are required.  Could not import.");
            return ;
        }
         
        //if guarRelat is not self, and name and birthdate not supplied, no error.  Just make guar self.
        if (!StringSupport.equals(guarRelat, "self"))
        {
            if (StringSupport.equals(guar.LName, "") || StringSupport.equals(guar.FName, "") || guar.Birthdate.Year < 1880)
            {
                warnings += "Guarantor information incomplete.  Guarantor will be self.\r\n";
                guarRelat = "self";
            }
             
        }
         
        if (insPresent)
        {
            if (StringSupport.equals(carrier.CarrierName, ""))
            {
                warnings += "Insurance CompanyName is missing. No insurance info will be imported.\r\n";
                insPresent = false;
            }
            else if (!StringSupport.equals(insRelat, "self"))
            {
                if (StringSupport.equals(subsc.LName, "") || StringSupport.equals(subsc.FName, "") || subsc.Birthdate.Year < 1880)
                {
                    warnings += "Subscriber name or birthdate is missing. No insurance info will be imported.\r\n";
                    insPresent = false;
                }
                 
            }
            else if (StringSupport.equals(sub.SubscriberID, ""))
            {
                warnings += "PolicyNumber/SubscriberID missing.\r\n";
                sub.SubscriberID = " ";
            }
               
        }
         
        if (!StringSupport.equals(warnings, ""))
        {
            if (MessageBox.Show("It's safe to import, but you should be aware of the following issues:\r\n" + warnings + "\r\nContinue with Import?", "Warnings", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        //Patient-------------------------------------------------------------------------------------
        //DataTable table;
        long patNum = Patients.getPatNumByNameAndBirthday(pat.LName,pat.FName,pat.Birthdate);
        Patient existingPat = null;
        existingPatOld = null;
        //we will need this to do an update.
        if (patNum != 0)
        {
            //a patient already exists, so only add missing fields
            existingPat = Patients.getPat(patNum);
            existingPatOld = existingPat.copy();
            if (StringSupport.equals(existingPat.MiddleI, ""))
            {
                //only alter existing if blank
                existingPat.MiddleI = pat.MiddleI;
            }
             
            if (pat.Gender != PatientGender.Unknown)
            {
                existingPat.Gender = pat.Gender;
            }
             
            if (StringSupport.equals(existingPat.Preferred, ""))
            {
                existingPat.Preferred = pat.Preferred;
            }
             
            if (StringSupport.equals(existingPat.Address, ""))
            {
                existingPat.Address = pat.Address;
            }
             
            if (StringSupport.equals(existingPat.Address2, ""))
            {
                existingPat.Address2 = pat.Address2;
            }
             
            if (StringSupport.equals(existingPat.City, ""))
            {
                existingPat.City = pat.City;
            }
             
            if (StringSupport.equals(existingPat.State, ""))
            {
                existingPat.State = pat.State;
            }
             
            if (StringSupport.equals(existingPat.Zip, ""))
            {
                existingPat.Zip = pat.Zip;
            }
             
            if (StringSupport.equals(existingPat.HmPhone, ""))
            {
                existingPat.HmPhone = pat.HmPhone;
            }
             
            if (StringSupport.equals(existingPat.Email, ""))
            {
                existingPat.Email = pat.Email;
            }
             
            if (StringSupport.equals(existingPat.WkPhone, ""))
            {
                existingPat.WkPhone = pat.WkPhone;
            }
             
            if (existingPat.Position == PatientPosition.Single)
            {
                existingPat.Position = pat.Position;
            }
             
            if (StringSupport.equals(existingPat.SSN, ""))
            {
                existingPat.SSN = pat.SSN;
            }
             
            existingPat.AddrNote += pat.AddrNote;
            //concat
            Patients.update(existingPat,existingPatOld);
            PatientNote PatientNoteCur = PatientNotes.refresh(existingPat.PatNum,existingPat.Guarantor);
            PatientNoteCur.MedicalComp += NoteMedicalComp;
            PatientNotes.update(PatientNoteCur,existingPat.Guarantor);
        }
        else
        {
            //guarantor will not be altered in any way
            //if patient already exists
            //patient is new, so insert
            Patients.insert(pat,false);
            existingPatOld = pat.copy();
            pat.Guarantor = pat.PatNum;
            //this can be changed later.
            Patients.update(pat,existingPatOld);
            PatientNote PatientNoteCur = PatientNotes.refresh(pat.PatNum,pat.Guarantor);
            PatientNoteCur.MedicalComp += NoteMedicalComp;
            PatientNotes.update(PatientNoteCur,pat.Guarantor);
        } 
        //guar-----------------------------------------------------------------------------------------------------
        if (existingPat == null)
        {
            //only add or alter guarantor for new patients
            if (StringSupport.equals(guarRelat, "self"))
            {
                //pat is already set with guar as self
                //ignore all guar fields except EmployerName
                existingPatOld = pat.copy();
                pat.EmployerNum = Employers.getEmployerNum(GuarEmp);
                Patients.update(pat,existingPatOld);
            }
            else
            {
                //if guarRelat is not self, and name and birthdate not supplied, a warning was issued, and relat was changed to self.
                //add guarantor or attach to an existing guarantor
                long guarNum = Patients.getPatNumByNameAndBirthday(guar.LName,guar.FName,guar.Birthdate);
                if (guarNum != 0)
                {
                    //a guar already exists, so simply attach. Make no other changes
                    existingPatOld = pat.copy();
                    pat.Guarantor = guarNum;
                    if (StringSupport.equals(guarRelat, "parent"))
                    {
                        pat.Position = PatientPosition.Child;
                    }
                     
                    Patients.update(pat,existingPatOld);
                }
                else
                {
                    //we need to completely create guar, then attach
                    Patients.insert(guar,false);
                    //set guar for guar
                    existingPatOld = guar.copy();
                    guar.Guarantor = guar.PatNum;
                    guar.EmployerNum = Employers.getEmployerNum(GuarEmp);
                    Patients.update(guar,existingPatOld);
                    //set guar for pat
                    existingPatOld = pat.copy();
                    pat.Guarantor = guar.PatNum;
                    if (StringSupport.equals(guarRelat, "parent"))
                    {
                        pat.Position = PatientPosition.Child;
                    }
                     
                    Patients.update(pat,existingPatOld);
                } 
            } 
        }
         
        //subsc--------------------------------------------------------------------------------------------------
        if (!insPresent)
        {
            //this takes care of missing carrier name or subscriber info.
            MsgBox.show(this,"Done");
            DialogResult = DialogResult.OK;
        }
         
        if (StringSupport.equals(insRelat, "self"))
        {
            sub.Subscriber = pat.PatNum;
        }
        else
        {
            //we need to find or add the subscriber
            patNum = Patients.getPatNumByNameAndBirthday(subsc.LName,subsc.FName,subsc.Birthdate);
            if (patNum != 0)
            {
                //a subsc already exists, so simply attach. Make no other changes
                sub.Subscriber = patNum;
            }
            else
            {
                //need to create and attach a subscriber
                Patients.insert(subsc,false);
                //set guar to same guar as patient
                existingPatOld = subsc.copy();
                subsc.Guarantor = pat.Guarantor;
                Patients.update(subsc,existingPatOld);
                sub.Subscriber = subsc.PatNum;
            } 
        } 
        //carrier-------------------------------------------------------------------------------------------------
        //Carriers.Cur=carrier;
        carrier = Carriers.getIndentical(carrier);
        //this automatically finds or creates a carrier
        //plan------------------------------------------------------------------------------------------------------
        plan.EmployerNum = Employers.getEmployerNum(InsEmp);
        plan.CarrierNum = carrier.CarrierNum;
        InsPlans.insert(plan);
        //Attach plan to subscriber
        sub.PlanNum = plan.PlanNum;
        InsSubs.insert(sub);
        //Then attach plan
        List<PatPlan> PatPlanList = PatPlans.refresh(pat.PatNum);
        PatPlan patplan = new PatPlan();
        patplan.Ordinal = (byte)(PatPlanList.Count + 1);
        //so the ordinal of the first entry will be 1, NOT 0.
        patplan.PatNum = pat.PatNum;
        patplan.InsSubNum = sub.InsSubNum;
        System.String __dummyScrutVar1 = insRelat;
        if (__dummyScrutVar1.equals("self"))
        {
            patplan.Relationship = Relat.Self;
        }
        else if (__dummyScrutVar1.equals("parent"))
        {
            patplan.Relationship = Relat.Child;
        }
        else if (__dummyScrutVar1.equals("spouse"))
        {
            patplan.Relationship = Relat.Spouse;
        }
        else if (__dummyScrutVar1.equals("guardian"))
        {
            patplan.Relationship = Relat.Dependent;
        }
            
        PatPlans.insert(patplan);
        //benefits
        if (annualMax != -1 && CovCatC.getListShort().Count > 0)
        {
            Benefit ben = new Benefit();
            ben.BenefitType = InsBenefitType.Limitations;
            ben.CovCatNum = CovCatC.getListShort()[0].CovCatNum;
            ben.MonetaryAmt = annualMax;
            ben.PlanNum = plan.PlanNum;
            ben.TimePeriod = BenefitTimePeriod.CalendarYear;
            Benefits.insert(ben);
        }
         
        if (deductible != -1 && CovCatC.getListShort().Count > 0)
        {
            Benefit ben = new Benefit();
            ben.BenefitType = InsBenefitType.Deductible;
            ben.CovCatNum = CovCatC.getListShort()[0].CovCatNum;
            ben.MonetaryAmt = deductible;
            ben.PlanNum = plan.PlanNum;
            ben.TimePeriod = BenefitTimePeriod.CalendarYear;
            Benefits.insert(ben);
        }
         
        MsgBox.show(this,"Done");
        DialogResult = DialogResult.OK;
    }

    private void processMSH(String field, String textValue) throws Exception {
        //MessageBox.Show("MSH, "+field+", "+textValue);
        System.String __dummyScrutVar2 = field;
        if (__dummyScrutVar2.equals("DateTimeOfMessage"))
        {
        }
        else //ignore
        if (__dummyScrutVar2.equals("MessageType"))
        {
            if (!StringSupport.equals(textValue, "AdmitPatient"))
            {
                throw new Exception("MessageType must be AdmitPatient");
            }
             
        }
        else if (__dummyScrutVar2.equals("OpenDentalVersion"))
        {
            targetVersion = textValue;
        }
        else
        {
            warnings += "Unrecognized field: " + field + "\r\n";
        }   
    }

    private void processPID(String field, String textValue) throws Exception {
        //MessageBox.Show("PID, "+field+", "+textValue);
        System.String __dummyScrutVar3 = field;
        if (__dummyScrutVar3.equals("NameLast"))
        {
            pat.LName = textValue;
        }
        else if (__dummyScrutVar3.equals("NameFirst"))
        {
            pat.FName = textValue;
        }
        else if (__dummyScrutVar3.equals("NameMiddle"))
        {
            pat.MiddleI = textValue;
        }
        else if (__dummyScrutVar3.equals("DateOfBirth"))
        {
            pat.Birthdate = DateTime.MinValue;
            if (textValue.Length > 0)
            {
                try
                {
                    pat.Birthdate = DateTime.Parse(textValue);
                }
                catch (Exception __dummyCatchVar0)
                {
                    warnings += "Invalid DateOfBirth\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar3.equals("Sex"))
        {
            pat.Gender = PatientGender.Unknown;
            if (textValue.Length > 0)
            {
                System.String.APPLY.APPLY __dummyScrutVar4 = textValue.Substring(0, 1).ToUpper();
                if (__dummyScrutVar4.equals("M"))
                {
                    pat.Gender = PatientGender.Male;
                }
                else if (__dummyScrutVar4.equals("F"))
                {
                    pat.Gender = PatientGender.Female;
                }
                else if (__dummyScrutVar4.equals("U"))
                {
                    pat.Gender = PatientGender.Unknown;
                }
                else
                {
                    warnings += "Invalid Sex\r\n";
                }   
            }
             
        }
        else if (__dummyScrutVar3.equals("AliasFirst"))
        {
            pat.Preferred = textValue;
        }
        else if (__dummyScrutVar3.equals("AddressStreet"))
        {
            pat.Address = textValue;
        }
        else if (__dummyScrutVar3.equals("AddressOtherDesignation"))
        {
            pat.Address2 = textValue;
        }
        else if (__dummyScrutVar3.equals("AddressCity"))
        {
            pat.City = textValue;
        }
        else if (__dummyScrutVar3.equals("AddressStateOrProvince"))
        {
            pat.State = textValue;
        }
        else //we won't enforce two letters
        if (__dummyScrutVar3.equals("AddressZipOrPostalCode"))
        {
            pat.Zip = textValue;
        }
        else if (__dummyScrutVar3.equals("PhoneHome"))
        {
            pat.HmPhone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar3.equals("EmailAddressHome"))
        {
            pat.Email = textValue;
        }
        else if (__dummyScrutVar3.equals("PhoneBusiness"))
        {
            pat.WkPhone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar3.equals("MaritalStatus"))
        {
            pat.Position = PatientPosition.Single;
            if (textValue.Length > 0)
            {
                System.String.APPLY.APPLY __dummyScrutVar5 = textValue.Substring(0, 1).ToUpper();
                if (__dummyScrutVar5.equals("M"))
                {
                    pat.Position = PatientPosition.Married;
                }
                else if (__dummyScrutVar5.equals("S"))
                {
                    pat.Position = PatientPosition.Single;
                }
                else if (__dummyScrutVar5.equals("W"))
                {
                    pat.Position = PatientPosition.Widowed;
                }
                else
                {
                    warnings += "Invalid MaritalStatus\r\n";
                }   
            }
             
        }
        else if (__dummyScrutVar3.equals("SSN"))
        {
            pat.SSN = textValue;
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
            {
                if (Regex.IsMatch(pat.SSN, "^\\d\\d\\d-\\d\\d-\\d\\d\\d\\d$"))
                {
                    pat.SSN = pat.SSN.Replace("-", "");
                }
                 
                if (!Regex.IsMatch(pat.SSN, "^\\d{9}$"))
                {
                    //if not exactly 9 digits
                    warnings += "Invalid SSN\r\n";
                }
                 
            }
             
        }
        else if (__dummyScrutVar3.equals("NotePhoneAddress"))
        {
            pat.AddrNote = textValue;
        }
        else if (__dummyScrutVar3.equals("NoteMedicalComplete"))
        {
            NoteMedicalComp = textValue;
        }
        else
        {
            warnings += "Unrecognized field: " + field + "\r\n";
        }                  
    }

    private void processGT(String field, String textValue) throws Exception {
        //MessageBox.Show("GT, "+field+", "+textValue);
        System.String __dummyScrutVar6 = field;
        if (__dummyScrutVar6.equals("NameLast"))
        {
            guar.LName = textValue;
        }
        else if (__dummyScrutVar6.equals("NameFirst"))
        {
            guar.FName = textValue;
        }
        else if (__dummyScrutVar6.equals("NameMiddle"))
        {
            guar.MiddleI = textValue;
        }
        else if (__dummyScrutVar6.equals("AddressStreet"))
        {
            guar.Address = textValue;
        }
        else if (__dummyScrutVar6.equals("AddressOtherDesignation"))
        {
            guar.Address2 = textValue;
        }
        else if (__dummyScrutVar6.equals("AddressCity"))
        {
            guar.City = textValue;
        }
        else if (__dummyScrutVar6.equals("AddressStateOrProvince"))
        {
            guar.State = textValue;
        }
        else //we won't enforce two letters
        if (__dummyScrutVar6.equals("AddressZipOrPostalCode"))
        {
            guar.Zip = textValue;
        }
        else if (__dummyScrutVar6.equals("PhoneHome"))
        {
            guar.HmPhone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar6.equals("EmailAddressHome"))
        {
            guar.Email = textValue;
        }
        else if (__dummyScrutVar6.equals("PhoneBusiness"))
        {
            guar.WkPhone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar6.equals("DateOfBirth"))
        {
            guar.Birthdate = DateTime.MinValue;
            if (textValue.Length > 0)
            {
                try
                {
                    guar.Birthdate = DateTime.Parse(textValue);
                }
                catch (Exception __dummyCatchVar1)
                {
                    warnings += "Invalid DateOfBirth\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar6.equals("Sex"))
        {
            guar.Gender = PatientGender.Unknown;
            if (textValue.Length > 0)
            {
                System.String.APPLY.APPLY __dummyScrutVar7 = textValue.Substring(0, 1).ToUpper();
                if (__dummyScrutVar7.equals("M"))
                {
                    guar.Gender = PatientGender.Male;
                }
                else if (__dummyScrutVar7.equals("F"))
                {
                    guar.Gender = PatientGender.Female;
                }
                else if (__dummyScrutVar7.equals("U"))
                {
                    guar.Gender = PatientGender.Unknown;
                }
                else
                {
                    warnings += "Invalid Sex\r\n";
                }   
            }
             
        }
        else if (__dummyScrutVar6.equals("GuarantorRelationship"))
        {
            System.String.APPLY __dummyScrutVar8 = textValue.ToLower();
            if (__dummyScrutVar8.equals("self"))
            {
                guarRelat = "self";
            }
            else if (__dummyScrutVar8.equals("parent"))
            {
                guarRelat = "parent";
            }
            else if (__dummyScrutVar8.equals("other"))
            {
                guarRelat = "other";
            }
            else if (__dummyScrutVar8.equals(""))
            {
                guarRelat = "self";
            }
            else
            {
                guarRelat = "self";
                warnings += "Invalid GuarantorRelationship\r\n";
            }    
        }
        else if (__dummyScrutVar6.equals("SSN"))
        {
            guar.SSN = textValue;
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
            {
                if (Regex.IsMatch(guar.SSN, "^\\d\\d\\d-\\d\\d-\\d\\d\\d\\d$"))
                {
                    guar.SSN = guar.SSN.Replace("-", "");
                }
                 
                if (!Regex.IsMatch(guar.SSN, "^\\d{9}$"))
                {
                    //if not exactly 9 digits
                    warnings += "Invalid SSN\r\n";
                }
                 
            }
             
        }
        else if (__dummyScrutVar6.equals("EmployerName"))
        {
            GuarEmp = textValue;
        }
        else if (__dummyScrutVar6.equals("MaritalStatus"))
        {
            guar.Position = PatientPosition.Single;
            if (textValue.Length > 0)
            {
                System.String.APPLY.APPLY __dummyScrutVar9 = textValue.Substring(0, 1).ToUpper();
                if (__dummyScrutVar9.equals("M"))
                {
                    guar.Position = PatientPosition.Married;
                }
                else if (__dummyScrutVar9.equals("S"))
                {
                    guar.Position = PatientPosition.Single;
                }
                else if (__dummyScrutVar9.equals("W"))
                {
                    guar.Position = PatientPosition.Widowed;
                }
                else
                {
                    warnings += "Invalid MaritalStatus\r\n";
                }   
            }
             
        }
        else
        {
            warnings += "Unrecognized field: " + field + "\r\n";
        }                 
    }

    private void processINS(String field, String textValue) throws Exception {
        //MessageBox.Show("INS, "+field+", "+textValue);
        System.String __dummyScrutVar10 = field;
        if (__dummyScrutVar10.equals("CompanyName"))
        {
            carrier.CarrierName = textValue;
        }
        else if (__dummyScrutVar10.equals("AddressStreet"))
        {
            carrier.Address = textValue;
        }
        else if (__dummyScrutVar10.equals("AddressOtherDesignation"))
        {
            carrier.Address2 = textValue;
        }
        else if (__dummyScrutVar10.equals("AddressCity"))
        {
            carrier.City = textValue;
        }
        else if (__dummyScrutVar10.equals("AddressStateOrProvince"))
        {
            carrier.State = textValue;
        }
        else //we won't enforce two letters
        if (__dummyScrutVar10.equals("AddressZipOrPostalCode"))
        {
            carrier.Zip = textValue;
        }
        else if (__dummyScrutVar10.equals("PhoneNumber"))
        {
            carrier.Phone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar10.equals("GroupNumber"))
        {
            plan.GroupNum = textValue;
        }
        else if (__dummyScrutVar10.equals("GroupName"))
        {
            plan.GroupName = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredGroupEmpName"))
        {
            InsEmp = textValue;
        }
        else if (__dummyScrutVar10.equals("PlanEffectiveDate"))
        {
            sub.DateEffective = DateTime.MinValue;
            if (textValue.Length > 0)
            {
                try
                {
                    sub.DateEffective = DateTime.Parse(textValue);
                }
                catch (Exception __dummyCatchVar2)
                {
                    warnings += "Invalid PlanEffectiveDate\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar10.equals("PlanExpirationDate"))
        {
            sub.DateTerm = DateTime.MinValue;
            if (textValue.Length > 0)
            {
                try
                {
                    sub.DateTerm = DateTime.Parse(textValue);
                }
                catch (Exception __dummyCatchVar3)
                {
                    warnings += "Invalid PlanExpirationDate\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar10.equals("InsuredsNameLast"))
        {
            subsc.LName = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsNameFirst"))
        {
            subsc.FName = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsNameMiddle"))
        {
            subsc.MiddleI = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsRelationToPat"))
        {
            //Self, Parent, Spouse, or Guardian
            System.String.APPLY __dummyScrutVar11 = textValue.ToLower();
            if (__dummyScrutVar11.equals("self"))
            {
                insRelat = "self";
            }
            else if (__dummyScrutVar11.equals("parent"))
            {
                insRelat = "parent";
            }
            else if (__dummyScrutVar11.equals("spouse"))
            {
                insRelat = "spouse";
            }
            else if (__dummyScrutVar11.equals("guardian"))
            {
                insRelat = "guardian";
            }
            else if (__dummyScrutVar11.equals(""))
            {
                insRelat = "self";
            }
            else
            {
                insRelat = "self";
                warnings += "Invalid InsuredsRelationToPat\r\n";
            }     
        }
        else if (__dummyScrutVar10.equals("InsuredsDateOfBirth"))
        {
            subsc.Birthdate = DateTime.MinValue;
            if (textValue.Length > 0)
            {
                try
                {
                    subsc.Birthdate = DateTime.Parse(textValue);
                }
                catch (Exception __dummyCatchVar4)
                {
                    warnings += "Invalid InsuredsDateOfBirth\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar10.equals("InsuredsAddressStreet"))
        {
            subsc.Address = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsAddressOtherDesignation"))
        {
            subsc.Address2 = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsAddressCity"))
        {
            subsc.City = textValue;
        }
        else if (__dummyScrutVar10.equals("InsuredsAddressStateOrProvince"))
        {
            subsc.State = textValue;
        }
        else //we won't enforce two letters
        if (__dummyScrutVar10.equals("InsuredsAddressZipOrPostalCode"))
        {
            subsc.Zip = textValue;
        }
        else if (__dummyScrutVar10.equals("AssignmentOfBenefits"))
        {
            System.String.APPLY __dummyScrutVar12 = textValue.ToUpper();
            if (__dummyScrutVar12.equals("Y"))
            {
                sub.AssignBen = true;
            }
            else if (__dummyScrutVar12.equals("N"))
            {
                sub.AssignBen = false;
            }
            else if (__dummyScrutVar12.equals(""))
            {
                sub.AssignBen = true;
            }
            else
            {
                sub.AssignBen = true;
                warnings += "Invalid AssignmentOfBenefits\r\n";
            }   
        }
        else if (__dummyScrutVar10.equals("ReleaseInformationCode"))
        {
            System.String.APPLY __dummyScrutVar13 = textValue.ToUpper();
            if (__dummyScrutVar13.equals("Y"))
            {
                sub.ReleaseInfo = true;
            }
            else if (__dummyScrutVar13.equals("N"))
            {
                sub.ReleaseInfo = false;
            }
            else if (__dummyScrutVar13.equals(""))
            {
                sub.ReleaseInfo = true;
            }
            else
            {
                sub.ReleaseInfo = true;
                warnings += "Invalid ReleaseInformationCode\r\n";
            }   
        }
        else if (__dummyScrutVar10.equals("PolicyNumber"))
        {
            sub.SubscriberID = textValue;
        }
        else if (__dummyScrutVar10.equals("PolicyDeductible"))
        {
            deductible = -1;
            //unknown
            if (textValue.Length > 0)
            {
                try
                {
                    deductible = System.Convert.ToInt32(textValue);
                }
                catch (Exception __dummyCatchVar5)
                {
                    warnings += "Invalid PolicyDeductible\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar10.equals("PolicyLimitAmount"))
        {
            annualMax = -1;
            //unknown
            if (textValue.Length > 0)
            {
                try
                {
                    annualMax = System.Convert.ToInt32(textValue);
                }
                catch (Exception __dummyCatchVar6)
                {
                    warnings += "Invalid PolicyLimitAmount\r\n";
                }
            
            }
             
        }
        else if (__dummyScrutVar10.equals("InsuredsSex"))
        {
            subsc.Gender = PatientGender.Unknown;
            if (textValue.Length > 0)
            {
                System.String.APPLY.APPLY __dummyScrutVar14 = textValue.Substring(0, 1).ToUpper();
                if (__dummyScrutVar14.equals("M"))
                {
                    subsc.Gender = PatientGender.Male;
                }
                else if (__dummyScrutVar14.equals("F"))
                {
                    subsc.Gender = PatientGender.Female;
                }
                else if (__dummyScrutVar14.equals("U"))
                {
                    subsc.Gender = PatientGender.Unknown;
                }
                else
                {
                    warnings += "Invalid InsuredsSex\r\n";
                }   
            }
             
        }
        else if (__dummyScrutVar10.equals("InsuredsSSN"))
        {
            subsc.SSN = textValue;
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
            {
                if (Regex.IsMatch(subsc.SSN, "^\\d\\d\\d-\\d\\d-\\d\\d\\d\\d$"))
                {
                    subsc.SSN = subsc.SSN.Replace("-", "");
                }
                 
                if (!Regex.IsMatch(subsc.SSN, "^\\d{9}$"))
                {
                    //if not exactly 9 digits
                    warnings += "Invalid InsuredsSSN\r\n";
                }
                 
            }
             
        }
        else if (__dummyScrutVar10.equals("InsuredsPhoneHome"))
        {
            subsc.HmPhone = TelephoneNumbers.reFormat(textValue);
        }
        else if (__dummyScrutVar10.equals("NotePlan"))
        {
            sub.BenefitNotes = textValue;
        }
        else
        {
            warnings += "Unrecognized field: " + field + "\r\n";
        }                               
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


