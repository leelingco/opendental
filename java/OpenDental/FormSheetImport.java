//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.FormCarriers;
import OpenDental.FormInsPlans;
import OpenDental.FormMedications;
import OpenDental.FormReferralSelect;
import OpenDental.FormSheetImportEnumPicker;
import OpenDental.FormSubscriberSelect;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.Document;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.Employers;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Relat;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.YN;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;


//Acrobat forms
public class FormSheetImport  extends Form 
{

    public Sheet SheetCur;
    public Document DocCur;
    private List<SheetImportRow> Rows = new List<SheetImportRow>();
    private Patient PatCur;
    /**
    * A copy of the patient when the form loads.  Used to know the what will change upon import.
    */
    private Patient PatOld;
    private Family Fam;
    /**
    * We must have a readily available bool, whether or not this checkbox field is present on the sheet.  It gets set at the very beginning, then gets changes based on user input on the sheet and in this window.
    */
    private boolean AddressSameForFam = new boolean();
    private InsPlan Plan1;
    private InsPlan Plan2;
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private List<InsPlan> PlanList = new List<InsPlan>();
    private PatPlan PatPlan1;
    private PatPlan PatPlan2;
    private Relat? Ins1Relat = Relat.Self;
    private Relat? Ins2Relat = Relat.Self;
    private Carrier Carrier1;
    private Carrier Carrier2;
    private Dictionary<String, String> DictAcrobatFields = new Dictionary<String, String>();
    private List<InsSub> SubList = new List<InsSub>();
    private InsSub Sub1;
    private InsSub Sub2;
    /**
    * In order to import insurance plans the sheet must contain Relationship, Subscriber, SubscriberID, CarrierName, and CarrierPhone.  This variable gets set when the sheet loads and will indicate if all fields are present for primary OR for secondary insurance.  Insurance should not attempt to import if this is false.
    */
    private boolean HasRequiredInsFields = new boolean();
    public FormSheetImport() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSheetImport_Load(Object sender, EventArgs e) throws Exception {
        if (SheetCur != null)
        {
            PatCur = Patients.getPat(SheetCur.PatNum);
            PatOld = PatCur.copy();
        }
        else
        {
            throw new NotImplementedException();
        } 
        //js this broke with the move to dot net 4.0.
        /*
        				pat=Patients.GetPat(DocCur.PatNum);
        				CAcroApp acroApp=null;
        				try {
        					acroApp=new AcroAppClass();//Initialize Acrobat by creating App object
        				}
        				catch {
        					MsgBox.Show(this,"Requires Acrobat 9 Pro to be installed on this computer.");
        					DialogResult=DialogResult.Cancel;
        					return;
        				}
        				//acroApp.Show();// Show Acrobat Viewer
        				//acroApp.Hide();//This is annoying if Acrobat is already open for some other reason.
        				CAcroAVDoc avDoc=new AcroAVDocClass();
        				string pathToPdf=CodeBase.ODFileUtils.CombinePaths(ImageStore.GetPatientFolder(pat),DocCur.FileName);
        				if(!avDoc.Open(pathToPdf,"")){
        					MessageBox.Show(Lan.g(this,"Could not open")+" "+pathToPdf);
        					DialogResult=DialogResult.Cancel;
        					return;
        				}
        				IAFormApp formApp=new AFormAppClass();//Create a IAFormApp object so we can access the form fields in the open document
        				IFields myFields=(IFields)formApp.Fields;// Get the IFields object associated with the form
        				IEnumerator myEnumerator = myFields.GetEnumerator();// Get the IEnumerator object for myFields
        				dictAcrobatFields=new Dictionary<string,string>();
        				IField myField;
        				string nameClean;
        				string valClean;
        				while(myEnumerator.MoveNext()) {
        					myField=(IField)myEnumerator.Current;// Get the IField object
        					if(myField.Value==null){
        						continue;
        					}
        					//if the form was designed in LiveCycle, the names will look like this: topmostSubform[0].page1[0].SSN[0]
        					//Whereas, if it was designed in Acrobat, the names will look like this: SSN
        					//So...
        					nameClean=myField.Name;
        					if(nameClean.Contains("[") && nameClean.Contains(".")) {
        						nameClean=nameClean.Substring(nameClean.LastIndexOf(".")+1);
        						nameClean=nameClean.Substring(0,nameClean.IndexOf("["));
        					}
        					if(nameClean=="misc") {
        						int suffix=1;
        						nameClean=nameClean+suffix.ToString();
        						while(dictAcrobatFields.ContainsKey(nameClean)) {//untested.
        							suffix++;
        							nameClean=nameClean+suffix.ToString();
        						}
        					}
        					valClean=myField.Value;
        					if(valClean=="Off") {
        						valClean="";
        					}
        					//myField.Type//possible values include text,radiobutton,checkbox
        					//MessageBox.Show("Raw:"+myField.Name+"  Name:"+nameClean+"  Value:"+myField.Value);
        					if(dictAcrobatFields.ContainsKey(nameClean)) {
        						continue;
        					}
        					dictAcrobatFields.Add(nameClean,valClean);
        					//name:topmostSubform[0].page1[0].SSN[0]
        				}
        				//acroApp.Hide();//Doesn't work well enough
        				//this.BringToFront();//Doesn't work
        				//acroApp.Minimize();
        				acroApp.Exit();
        				acroApp=null;
        				*/
        Fam = Patients.getFamily(PatCur.PatNum);
        AddressSameForFam = true;
        for (int i = 0;i < Fam.ListPats.Length;i++)
        {
            if (!StringSupport.equals(PatCur.HmPhone, Fam.ListPats[i].HmPhone) || !StringSupport.equals(PatCur.Address, Fam.ListPats[i].Address) || !StringSupport.equals(PatCur.Address2, Fam.ListPats[i].Address2) || !StringSupport.equals(PatCur.City, Fam.ListPats[i].City) || !StringSupport.equals(PatCur.State, Fam.ListPats[i].State) || !StringSupport.equals(PatCur.Zip, Fam.ListPats[i].Zip))
            {
                AddressSameForFam = false;
                break;
            }
             
        }
        PatPlanList = PatPlans.refresh(PatCur.PatNum);
        SubList = InsSubs.refreshForFam(Fam);
        PlanList = InsPlans.refreshForSubList(SubList);
        if (PatPlanList.Count == 0)
        {
            PatPlan1 = null;
            Plan1 = null;
            Sub1 = null;
            Ins1Relat = null;
            Carrier1 = null;
        }
        else
        {
            PatPlan1 = PatPlanList[0];
            Sub1 = InsSubs.getSub(PatPlan1.InsSubNum,SubList);
            Plan1 = InsPlans.getPlan(Sub1.PlanNum,PlanList);
            Ins1Relat = PatPlan1.Relationship;
            Carrier1 = Carriers.getCarrier(Plan1.CarrierNum);
        } 
        if (PatPlanList.Count < 2)
        {
            PatPlan2 = null;
            Plan2 = null;
            Sub2 = null;
            Ins2Relat = null;
            Carrier2 = null;
        }
        else
        {
            PatPlan2 = PatPlanList[1];
            Sub2 = InsSubs.getSub(PatPlan2.InsSubNum,SubList);
            Plan2 = InsPlans.getPlan(Sub2.PlanNum,PlanList);
            Ins2Relat = PatPlan2.Relationship;
            Carrier2 = Carriers.getCarrier(Plan2.CarrierNum);
        } 
        fillRows();
        fillGrid();
        //All the fields have been loaded on the sheet at this point.  Set the required insurance boolean if this is a patient form.
        if (SheetCur.SheetType == SheetTypeEnum.PatientForm)
        {
            setHasRequiredInsFields();
        }
         
    }

    /**
    * This can only be run once when the form first opens.  After that, the rows are just edited.
    */
    private void fillRows() throws Exception {
        if (SheetCur.SheetType == SheetTypeEnum.PatientForm)
        {
            Rows = new List<SheetImportRow>();
            SheetImportRow row;
            String fieldVal = new String();
            Rows.Add(createSeparator("Personal"));
            //LName---------------------------------------------
            fieldVal = getInputValue("LName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "LName";
                row.OldValDisplay = PatCur.LName;
                row.OldValObj = PatCur.LName;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //FName---------------------------------------------
            fieldVal = getInputValue("FName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "FName";
                row.OldValDisplay = PatCur.FName;
                row.OldValObj = PatCur.FName;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //MiddleI---------------------------------------------
            fieldVal = getInputValue("MiddleI");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "MiddleI";
                row.OldValDisplay = PatCur.MiddleI;
                row.OldValObj = PatCur.MiddleI;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Preferred---------------------------------------------
            fieldVal = getInputValue("Preferred");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Preferred";
                row.OldValDisplay = PatCur.Preferred;
                row.OldValObj = PatCur.Preferred;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Gender---------------------------------------------
            fieldVal = getRadioValue("Gender");
            if (fieldVal != null)
            {
                //field exists on form
                row = new SheetImportRow();
                row.FieldName = "Gender";
                row.OldValDisplay = Lan.g("enumPatientGender", PatCur.Gender.ToString());
                row.OldValObj = PatCur.Gender;
                if (StringSupport.equals(fieldVal, ""))
                {
                    //no box was checked
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        PatientGender gender = (PatientGender)Enum.Parse(PatientGender.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumPatientGender", gender.ToString());
                        row.NewValObj = gender;
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid gender."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = PatientGender.class;
                if (row.NewValObj != null && (PatientGender)row.NewValObj != PatCur.Gender)
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Position---------------------------------------------
            fieldVal = getRadioValue("Position");
            if (fieldVal != null)
            {
                //field exists on form
                row = new SheetImportRow();
                row.FieldName = "Position";
                row.OldValDisplay = Lan.g("enumPatientPositionr", PatCur.Position.ToString());
                row.OldValObj = PatCur.Position;
                if (StringSupport.equals(fieldVal, ""))
                {
                    //no box was checked
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        PatientPosition position = (PatientPosition)Enum.Parse(PatientPosition.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumPatientPosition", position.ToString());
                        row.NewValObj = position;
                    }
                    catch (Exception __dummyCatchVar1)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid PatientPosition."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = PatientPosition.class;
                if (row.NewValObj != null && (PatientPosition)row.NewValObj != PatCur.Position)
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Birthdate---------------------------------------------
            fieldVal = getInputValue("Birthdate");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Birthdate";
                if (PatCur.Birthdate.Year < 1880)
                {
                    row.OldValDisplay = "";
                }
                else
                {
                    row.OldValDisplay = PatCur.Birthdate.ToShortDateString();
                } 
                row.OldValObj = PatCur.Birthdate;
                row.NewValObj = PIn.Date(fieldVal);
                if (((DateTime)row.NewValObj).Year < 1880)
                {
                    row.NewValDisplay = "";
                }
                else
                {
                    row.NewValDisplay = ((DateTime)row.NewValObj).ToShortDateString();
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = DateTime.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //SSN---------------------------------------------
            fieldVal = getInputValue("SSN");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "SSN";
                row.OldValDisplay = PatCur.SSN;
                row.OldValObj = PatCur.SSN;
                row.NewValDisplay = fieldVal.Replace("-", "");
                //quickly strip dashes
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //WkPhone---------------------------------------------
            fieldVal = getInputValue("WkPhone");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "WkPhone";
                row.OldValDisplay = PatCur.WkPhone;
                row.OldValObj = PatCur.WkPhone;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //WirelessPhone---------------------------------------------
            fieldVal = getInputValue("WirelessPhone");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "WirelessPhone";
                row.OldValDisplay = PatCur.WirelessPhone;
                row.OldValObj = PatCur.WirelessPhone;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //wirelessCarrier---------------------------------------------
            fieldVal = getInputValue("wirelessCarrier");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "wirelessCarrier";
                row.OldValDisplay = "";
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                row.IsFlagged = true;
                //if user entered nothing, the red text won't show anyway.
                Rows.Add(row);
            }
             
            //Email---------------------------------------------
            fieldVal = getInputValue("Email");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Email";
                row.OldValDisplay = PatCur.Email;
                row.OldValObj = PatCur.Email;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //PreferContactMethod---------------------------------------------
            fieldVal = getRadioValue("PreferContactMethod");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "PreferContactMethod";
                row.OldValDisplay = Lan.g("enumContactMethod", PatCur.PreferContactMethod.ToString());
                row.OldValObj = PatCur.PreferContactMethod;
                if (StringSupport.equals(fieldVal, ""))
                {
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        ContactMethod cmeth = (ContactMethod)Enum.Parse(ContactMethod.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumContactMethod", cmeth.ToString());
                        row.NewValObj = cmeth;
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid ContactMethod."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = ContactMethod.class;
                if (row.NewValObj != null && (ContactMethod)row.NewValObj != PatCur.PreferContactMethod)
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //PreferConfirmMethod---------------------------------------------
            fieldVal = getRadioValue("PreferConfirmMethod");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "PreferConfirmMethod";
                row.OldValDisplay = Lan.g("enumContactMethod", PatCur.PreferConfirmMethod.ToString());
                row.OldValObj = PatCur.PreferConfirmMethod;
                if (StringSupport.equals(fieldVal, ""))
                {
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        ContactMethod cmeth = (ContactMethod)Enum.Parse(ContactMethod.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumContactMethod", cmeth.ToString());
                        row.NewValObj = cmeth;
                    }
                    catch (Exception __dummyCatchVar3)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid ContactMethod."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = ContactMethod.class;
                if (row.NewValObj != null && (ContactMethod)row.NewValObj != PatCur.PreferConfirmMethod)
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //PreferRecallMethod---------------------------------------------
            fieldVal = getRadioValue("PreferRecallMethod");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "PreferRecallMethod";
                row.OldValDisplay = Lan.g("enumContactMethod", PatCur.PreferRecallMethod.ToString());
                row.OldValObj = PatCur.PreferRecallMethod;
                if (StringSupport.equals(fieldVal, ""))
                {
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        ContactMethod cmeth = (ContactMethod)Enum.Parse(ContactMethod.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumContactMethod", cmeth.ToString());
                        row.NewValObj = cmeth;
                    }
                    catch (Exception __dummyCatchVar4)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid ContactMethod."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = ContactMethod.class;
                if (row.NewValObj != null && (ContactMethod)row.NewValObj != PatCur.PreferRecallMethod)
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //referredFrom---------------------------------------------
            fieldVal = getInputValue("referredFrom");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "referredFrom";
                Referral refer = Referrals.getReferralForPat(PatCur.PatNum);
                if (refer == null)
                {
                    //there was no existing referral
                    row.OldValDisplay = "";
                    row.OldValObj = null;
                    row.NewValDisplay = fieldVal;
                    row.NewValObj = null;
                    if (!StringSupport.equals(row.NewValDisplay, ""))
                    {
                        //user did enter a referral
                        row.ImpValDisplay = Lan.g(this,"[double click to pick]");
                        row.ImpValObj = null;
                        row.IsFlaggedImp = true;
                        row.DoImport = false;
                    }
                    else
                    {
                        //this will change to true after they pick a referral
                        //user still did not enter a referral
                        row.ImpValDisplay = "";
                        row.ImpValObj = null;
                        row.DoImport = false;
                    } 
                }
                else
                {
                    //there was an existing referral. We don't allow changing from here since mostly for new patients.
                    row.OldValDisplay = refer.getNameFL();
                    row.OldValObj = refer;
                    row.NewValDisplay = fieldVal;
                    row.NewValObj = null;
                    row.ImpValDisplay = "";
                    row.ImpValObj = null;
                    row.DoImport = false;
                    if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                    {
                        //if patient changed an existing referral, at least let user know.
                        row.IsFlagged = true;
                    }
                     
                } 
                //although they won't be able to do anything about it here
                row.ObjType = Referral.class;
                Rows.Add(row);
            }
             
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Address and Home Phone"));
            //SameForEntireFamily-------------------------------------------
            if (containsOneOfFields("addressAndHmPhoneIsSameEntireFamily"))
            {
                row = new SheetImportRow();
                row.FieldName = "addressAndHmPhoneIsSameEntireFamily";
                row.FieldDisplay = "Same for entire family";
                if (AddressSameForFam)
                {
                    //remember we calculated this in the form constructor.
                    row.OldValDisplay = "X";
                    row.OldValObj = "X";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                //And now, we will revise AddressSameForFam based on user input
                AddressSameForFam = isChecked("addressAndHmPhoneIsSameEntireFamily");
                if (AddressSameForFam)
                {
                    row.NewValDisplay = "X";
                    row.NewValObj = "X";
                    row.ImpValDisplay = "X";
                    row.ImpValObj = "X";
                }
                else
                {
                    row.NewValDisplay = "";
                    row.NewValObj = "";
                    row.ImpValDisplay = "";
                    row.ImpValObj = "";
                } 
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Address---------------------------------------------
            fieldVal = getInputValue("Address");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Address";
                row.OldValDisplay = PatCur.Address;
                row.OldValObj = PatCur.Address;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Address2---------------------------------------------
            fieldVal = getInputValue("Address2");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Address2";
                row.OldValDisplay = PatCur.Address2;
                row.OldValObj = PatCur.Address2;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //City---------------------------------------------
            fieldVal = getInputValue("City");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "City";
                row.OldValDisplay = PatCur.City;
                row.OldValObj = PatCur.City;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //State---------------------------------------------
            fieldVal = getInputValue("State");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "State";
                row.OldValDisplay = PatCur.State;
                row.OldValObj = PatCur.State;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Zip---------------------------------------------
            fieldVal = getInputValue("Zip");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "Zip";
                row.OldValDisplay = PatCur.Zip;
                row.OldValObj = PatCur.Zip;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //HmPhone---------------------------------------------
            fieldVal = getInputValue("HmPhone");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "HmPhone";
                row.OldValDisplay = PatCur.HmPhone;
                row.OldValObj = PatCur.HmPhone;
                row.NewValDisplay = fieldVal;
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Insurance Policy 1"));
            //It turns out that importing insurance is crazy complicated if we want it to be perfect.
            //So it's better to table that plan for now.
            //The new strategy is simply to show them what the user entered and notify them if it seems different.
            //ins1Relat------------------------------------------------------------
            fieldVal = getRadioValue("ins1Relat");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1Relat";
                row.FieldDisplay = "Relationship";
                row.OldValDisplay = Lan.g("enumRelat", Ins1Relat.ToString());
                row.OldValObj = Ins1Relat;
                if (StringSupport.equals(fieldVal, ""))
                {
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        Relat relat = (Relat)Enum.Parse(Relat.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumRelat", relat.ToString());
                        row.NewValObj = relat;
                    }
                    catch (Exception __dummyCatchVar5)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid Relationship."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = Relat.class;
                row.DoImport = false;
                if (row.NewValObj != null && !StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins1Subscriber---------------------------------------------
            fieldVal = getInputValue("ins1SubscriberNameF");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1Subscriber";
                row.FieldDisplay = "Subscriber";
                if (Plan1 != null)
                {
                    row.OldValDisplay = Fam.getNameInFamFirst(Sub1.Subscriber);
                    row.OldValObj = Sub1.Subscriber;
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = null;
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Patient.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins1SubscriberID---------------------------------------------
            fieldVal = getInputValue("ins1SubscriberID");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1SubscriberID";
                row.FieldDisplay = "Subscriber ID";
                if (Plan1 != null)
                {
                    row.OldValDisplay = Sub1.SubscriberID;
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins1CarrierName---------------------------------------------
            fieldVal = getInputValue("ins1CarrierName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1CarrierName";
                row.FieldDisplay = "Carrier";
                if (Carrier1 != null)
                {
                    row.OldValDisplay = Carrier1.CarrierName;
                    row.OldValObj = Carrier1;
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = "";
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Carrier.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins1CarrierPhone---------------------------------------------
            fieldVal = getInputValue("ins1CarrierPhone");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1CarrierPhone";
                row.FieldDisplay = "Phone";
                if (Carrier1 != null)
                {
                    row.OldValDisplay = Carrier1.Phone;
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = "";
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Carrier.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins1EmployerName---------------------------------------------
            fieldVal = getInputValue("ins1EmployerName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1EmployerName";
                row.FieldDisplay = "Employer";
                if (Plan1 == null)
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = Employers.getName(Plan1.EmployerNum);
                    row.OldValObj = Employers.getEmployer(Plan1.EmployerNum);
                } 
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins1GroupName---------------------------------------------
            fieldVal = getInputValue("ins1GroupName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1GroupName";
                row.FieldDisplay = "Group Name";
                if (Plan1 != null)
                {
                    row.OldValDisplay = Plan1.GroupName;
                }
                else
                {
                    row.OldValDisplay = "";
                } 
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins1GroupNum---------------------------------------------
            fieldVal = getInputValue("ins1GroupNum");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins1GroupNum";
                row.FieldDisplay = "Group Num";
                if (Plan1 != null)
                {
                    row.OldValDisplay = Plan1.GroupNum;
                }
                else
                {
                    row.OldValDisplay = "";
                } 
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Insurance Policy 2"));
            //It turns out that importing insurance is crazy complicated if want it to be perfect.
            //So it's better to table that plan for now.
            //The new strategy is simply to show them what the user entered and notify them if it seems different.
            //ins2Relat------------------------------------------------------------
            fieldVal = getRadioValue("ins2Relat");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2Relat";
                row.FieldDisplay = "Relationship";
                row.OldValDisplay = Lan.g("enumRelat", Ins2Relat.ToString());
                row.OldValObj = Ins2Relat;
                if (StringSupport.equals(fieldVal, ""))
                {
                    row.NewValDisplay = "";
                    row.NewValObj = null;
                }
                else
                {
                    try
                    {
                        Relat relat = (Relat)Enum.Parse(Relat.class, fieldVal);
                        row.NewValDisplay = Lan.g("enumRelat", relat.ToString());
                        row.NewValObj = relat;
                    }
                    catch (Exception __dummyCatchVar6)
                    {
                        MessageBox.Show(fieldVal + Lan.g(this," is not a valid Relationship."));
                    }
                
                } 
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = Relat.class;
                row.DoImport = false;
                if (row.NewValObj != null && !StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins2Subscriber---------------------------------------------
            fieldVal = getInputValue("ins2SubscriberNameF");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2Subscriber";
                row.FieldDisplay = "Subscriber";
                if (Plan2 != null)
                {
                    row.OldValDisplay = Fam.getNameInFamFirst(Sub2.Subscriber);
                    row.OldValObj = Sub2.Subscriber;
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = null;
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = row.NewValDisplay;
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Patient.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins2SubscriberID---------------------------------------------
            fieldVal = getInputValue("ins2SubscriberID");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2SubscriberID";
                row.FieldDisplay = "Subscriber ID";
                if (Plan2 != null)
                {
                    row.OldValDisplay = Sub2.SubscriberID;
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins2CarrierName---------------------------------------------
            fieldVal = getInputValue("ins2CarrierName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2CarrierName";
                row.FieldDisplay = "Carrier";
                if (Carrier2 != null)
                {
                    row.OldValDisplay = Carrier2.CarrierName;
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = "";
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Carrier.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins2CarrierPhone---------------------------------------------
            fieldVal = getInputValue("ins2CarrierPhone");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2CarrierPhone";
                row.FieldDisplay = "Phone";
                if (Carrier2 != null)
                {
                    row.OldValDisplay = Carrier2.Phone;
                    row.OldValObj = "";
                }
                else
                {
                    row.OldValDisplay = "";
                    row.OldValObj = "";
                } 
                row.NewValDisplay = fieldVal;
                //whether it's empty or has a value
                row.NewValObj = "";
                row.ImpValDisplay = "[double click to pick]";
                row.ImpValObj = null;
                row.ObjType = Carrier.class;
                row.DoImport = false;
                row.IsFlaggedImp = true;
                Rows.Add(row);
            }
             
            //ins2EmployerName---------------------------------------------
            fieldVal = getInputValue("ins2EmployerName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2EmployerName";
                row.FieldDisplay = "Employer";
                if (Plan2 == null)
                {
                    row.OldValDisplay = "";
                }
                else
                {
                    row.OldValDisplay = Employers.getName(Plan2.EmployerNum);
                } 
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins2GroupName---------------------------------------------
            fieldVal = getInputValue("ins2GroupName");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2GroupName";
                row.FieldDisplay = "Group Name";
                if (Plan2 != null)
                {
                    row.OldValDisplay = Plan2.GroupName;
                }
                else
                {
                    row.OldValDisplay = "";
                } 
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //ins2GroupNum---------------------------------------------
            fieldVal = getInputValue("ins2GroupNum");
            if (fieldVal != null)
            {
                row = new SheetImportRow();
                row.FieldName = "ins2GroupNum";
                row.FieldDisplay = "Group Num";
                if (Plan2 != null)
                {
                    row.OldValDisplay = Plan2.GroupNum;
                }
                else
                {
                    row.OldValDisplay = "";
                } 
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = fieldVal;
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = row.NewValObj;
                row.ObjType = String.class;
                row.DoImport = false;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
             
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Misc"));
            //misc----------------------------------------------------
            List<String> miscVals = getMiscValues();
            for (int i = 0;i < miscVals.Count;i++)
            {
                fieldVal = miscVals[i];
                row = new SheetImportRow();
                row.FieldName = "misc";
                row.FieldDisplay = "misc" + (i + 1).ToString();
                row.OldValDisplay = "";
                row.OldValObj = "";
                row.NewValDisplay = fieldVal;
                row.NewValObj = "";
                row.ImpValDisplay = "";
                row.ImpValObj = "";
                row.ObjType = String.class;
                row.DoImport = false;
                row.IsFlagged = true;
                Rows.Add(row);
            }
        }
        else if (SheetCur.SheetType == SheetTypeEnum.MedicalHistory)
        {
            Rows = new List<SheetImportRow>();
            String fieldVal = "";
            List<Allergy> allergies = null;
            List<Disease> diseases = null;
            SheetImportRow row;
            Rows.Add(createSeparator("Allergies"));
            //Get list of all the allergy check boxes
            List<SheetField> allergyList = getSheetFieldsByFieldName("allergy:");
            for (int i = 0;i < allergyList.Count;i++)
            {
                fieldVal = "";
                if (i < 1)
                {
                    allergies = Allergies.getAll(PatCur.PatNum,true);
                }
                 
                row = new SheetImportRow();
                row.FieldName = allergyList[i].FieldName.Remove(0, 8);
                row.OldValDisplay = "";
                row.OldValObj = null;
                for (int j = 0;j < allergies.Count;j++)
                {
                    //Check if allergy exists.
                    if (AllergyDefs.GetDescription(allergies[j].AllergyDefNum) == allergyList[i].FieldName.Remove(0, 8))
                    {
                        if (allergies[j].StatusIsActive)
                        {
                            row.OldValDisplay = "Y";
                        }
                        else
                        {
                            row.OldValDisplay = "N";
                        } 
                        row.OldValObj = allergies[j];
                        break;
                    }
                     
                }
                SheetField oppositeBox = GetOppositeSheetFieldCheckBox(allergyList, allergyList[i]);
                if (StringSupport.equals(allergyList[i].FieldValue, ""))
                {
                    //Current box not checked.
                    if (oppositeBox == null || StringSupport.equals(oppositeBox.FieldValue, ""))
                    {
                        //No opposite box or both boxes are not checked.
                        //Create a blank row just in case they want to import.
                        row.NewValDisplay = "";
                        row.NewValObj = allergyList[i];
                        row.ImpValDisplay = "";
                        row.ImpValObj = "";
                        row.ObjType = Allergy.class;
                        Rows.Add(row);
                        if (oppositeBox != null)
                        {
                            allergyList.Remove(oppositeBox);
                        }
                         
                        continue;
                    }
                     
                    //Removes possible duplicate entry.
                    //Opposite box is checked, figure out if it's a Y or N box.
                    if (StringSupport.equals(oppositeBox.RadioButtonValue, "Y"))
                    {
                        fieldVal = "Y";
                    }
                    else
                    {
                        fieldVal = "N";
                    } 
                }
                else
                {
                    //Current box is checked.
                    if (StringSupport.equals(allergyList[i].RadioButtonValue, "Y"))
                    {
                        fieldVal = "Y";
                    }
                    else
                    {
                        fieldVal = "N";
                    } 
                } 
                //Get rid of the opposite check box so field doesn't show up twice.
                if (oppositeBox != null)
                {
                    allergyList.Remove(oppositeBox);
                }
                 
                row.NewValDisplay = fieldVal;
                row.NewValObj = allergyList[i];
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = String.class;
                row.ObjType = Allergy.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay) && !(StringSupport.equals(row.OldValDisplay, "") && StringSupport.equals(row.NewValDisplay, "N")))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Medications"));
            List<SheetField> inputMedList = getSheetFieldsByFieldName("inputMed");
            List<SheetField> checkMedList = getSheetFieldsByFieldName("checkMed");
            List<SheetField> currentMedList = new List<SheetField>();
            List<SheetField> newMedList = new List<SheetField>();
            for (int i = 0;i < inputMedList.Count;i++)
            {
                if (inputMedList[i].FieldType == SheetFieldType.OutputText)
                {
                    currentMedList.Add(inputMedList[i]);
                }
                else
                {
                    //User might have tried to type in a new medication they are taking.
                    newMedList.Add(inputMedList[i]);
                } 
            }
            List<MedicationPat> listMedPatFull = MedicationPats.refresh(PatCur.PatNum,false);
            for (int i = 0;i < currentMedList.Count;i++)
            {
                fieldVal = "";
                row = new SheetImportRow();
                row.FieldName = currentMedList[i].FieldValue;
                //Will be the name of the drug.
                row.OldValDisplay = "N";
                row.OldValObj = null;
                for (int j = 0;j < listMedPatFull.Count;j++)
                {
                    String strMedName = listMedPatFull[j].MedDescript;
                    //for meds that came back from NewCrop
                    if (listMedPatFull[j].MedicationNum != 0)
                    {
                        //For meds entered in OD and linked to Medication list.
                        strMedName = Medications.GetDescription(listMedPatFull[j].MedicationNum);
                    }
                     
                    if (StringSupport.equals(currentMedList[i].FieldValue, strMedName))
                    {
                        row.OldValDisplay = "Y";
                        row.OldValObj = listMedPatFull[j];
                    }
                     
                }
                List<SheetField> relatedChkBoxes = GetRelatedMedicalCheckBoxes(checkMedList, currentMedList[i]);
                for (int j = 0;j < relatedChkBoxes.Count;j++)
                {
                    //Figure out which corresponding checkbox is checked.
                    if (!StringSupport.equals(relatedChkBoxes[j].FieldValue, ""))
                    {
                        //Patient checked this box.
                        if (StringSupport.equals(checkMedList[j].RadioButtonValue, "Y"))
                        {
                            fieldVal = "Y";
                        }
                        else
                        {
                            fieldVal = "N";
                        } 
                        break;
                    }
                     
                    //If sheet is only using N boxes and the patient already had this med marked as inactive and then they unchecked the N, so now we need to import it.
                    //Only using N boxes for this current medication.
                    //Patient has this medication but is currently marked as inactive.
                    if (relatedChkBoxes.Count == 1 && StringSupport.equals(relatedChkBoxes[j].RadioButtonValue, "N") && row.OldValObj != null && StringSupport.equals(row.OldValDisplay, "N") && StringSupport.equals(relatedChkBoxes[j].FieldValue, ""))
                    {
                        //Patient unchecked the medication so we activate it again.
                        fieldVal = "Y";
                    }
                     
                }
                if (relatedChkBoxes.Count == 1 && StringSupport.equals(relatedChkBoxes[0].RadioButtonValue, "N") && StringSupport.equals(relatedChkBoxes[0].FieldValue, "") && StringSupport.equals(row.OldValDisplay, "N") && row.OldValObj != null)
                {
                    row.DoImport = true;
                }
                 
                row.NewValDisplay = fieldVal;
                row.NewValObj = currentMedList[i];
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = String.class;
                row.ObjType = MedicationPat.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay) && !StringSupport.equals(row.NewValDisplay, ""))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
            for (int i = 0;i < newMedList.Count;i++)
            {
                if (StringSupport.equals(newMedList[i].FieldValue, ""))
                {
                    continue;
                }
                 
                //No medication entered by patient.
                row = new SheetImportRow();
                row.FieldName = newMedList[i].FieldValue;
                //Whatever the patient typed in...
                row.OldValDisplay = "";
                row.OldValObj = null;
                row.NewValDisplay = "Y";
                row.NewValObj = newMedList[i];
                row.ImpValDisplay = Lan.g(this,"[double click to pick]");
                row.ImpValObj = new long();
                row.IsFlaggedImp = true;
                row.DoImport = false;
                //this will change to true after they pick a medication
                row.ObjType = MedicationPat.class;
                Rows.Add(row);
            }
            //Separator-------------------------------------------
            Rows.Add(createSeparator("Problems"));
            List<SheetField> problemList = getSheetFieldsByFieldName("problem:");
            for (int i = 0;i < problemList.Count;i++)
            {
                fieldVal = "";
                if (i < 1)
                {
                    diseases = Diseases.refresh(PatCur.PatNum,false);
                }
                 
                row = new SheetImportRow();
                row.FieldName = problemList[i].FieldName.Remove(0, 8);
                //Figure out the current status of this allergy
                row.OldValDisplay = "";
                row.OldValObj = null;
                for (int j = 0;j < diseases.Count;j++)
                {
                    if (DiseaseDefs.GetName(diseases[j].DiseaseDefNum) == problemList[i].FieldName.Remove(0, 8))
                    {
                        if (diseases[j].ProbStatus == ProblemStatus.Active)
                        {
                            row.OldValDisplay = "Y";
                        }
                        else
                        {
                            row.OldValDisplay = "N";
                        } 
                        row.OldValObj = diseases[j];
                        break;
                    }
                     
                }
                SheetField oppositeBox = GetOppositeSheetFieldCheckBox(problemList, problemList[i]);
                if (StringSupport.equals(problemList[i].FieldValue, ""))
                {
                    //Current box not checked.
                    if (oppositeBox == null || StringSupport.equals(oppositeBox.FieldValue, ""))
                    {
                        //No opposite box or both boxes are not checked.
                        //Create a blank row just in case they still want to import.
                        row.NewValDisplay = "";
                        row.NewValObj = problemList[i];
                        row.ImpValDisplay = "";
                        row.ImpValObj = "";
                        row.ObjType = Disease.class;
                        Rows.Add(row);
                        if (oppositeBox != null)
                        {
                            problemList.Remove(oppositeBox);
                        }
                         
                        continue;
                    }
                     
                    //Removes possible duplicate entry.
                    //Opposite box is checked, figure out if it's a Y or N box.
                    if (StringSupport.equals(oppositeBox.RadioButtonValue, "Y"))
                    {
                        fieldVal = "Y";
                    }
                    else
                    {
                        fieldVal = "N";
                    } 
                }
                else
                {
                    //Current box is checked.
                    if (StringSupport.equals(problemList[i].RadioButtonValue, "Y"))
                    {
                        fieldVal = "Y";
                    }
                    else
                    {
                        fieldVal = "N";
                    } 
                } 
                //Get rid of the opposite check box so field doesn't show up twice.
                if (oppositeBox != null)
                {
                    problemList.Remove(oppositeBox);
                }
                 
                row.NewValDisplay = fieldVal;
                row.NewValObj = problemList[i];
                row.ImpValDisplay = row.NewValDisplay;
                row.ImpValObj = String.class;
                row.ObjType = Disease.class;
                if (!StringSupport.equals(row.OldValDisplay, row.NewValDisplay) && !(StringSupport.equals(row.OldValDisplay, "") && StringSupport.equals(row.NewValDisplay, "N")))
                {
                    row.DoImport = true;
                }
                 
                Rows.Add(row);
            }
        }
          
    }

    private void fillGrid() throws Exception {
        int scrollVal = gridMain.getScrollValue();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"FieldName"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Current Value"),175);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Entered Value"),175);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Import Value"),175);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Do Import"), 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (Rows[i].IsSeparator)
            {
                row.getCells().Add(Rows[i].FieldName);
                row.getCells().add("");
                row.getCells().add("");
                row.getCells().add("");
                row.getCells().add("");
                row.setColorBackG(Color.DarkSlateGray);
                row.setColorText(Color.White);
            }
            else
            {
                if (Rows[i].FieldDisplay != null)
                {
                    row.getCells().Add(Rows[i].FieldDisplay);
                }
                else
                {
                    row.getCells().Add(Rows[i].FieldName);
                } 
                row.getCells().Add(Rows[i].OldValDisplay);
                cell = new OpenDental.UI.ODGridCell(Rows[i].NewValDisplay);
                if (Rows[i].IsFlagged)
                {
                    cell.setColorText(Color.Firebrick);
                    cell.setBold(YN.Yes);
                }
                 
                row.getCells().Add(cell);
                cell = new OpenDental.UI.ODGridCell(Rows[i].ImpValDisplay);
                if (Rows[i].IsFlaggedImp)
                {
                    cell.setColorText(Color.Firebrick);
                    cell.setBold(YN.Yes);
                }
                 
                row.getCells().Add(cell);
                if (Rows[i].DoImport)
                {
                    row.getCells().add("X");
                    row.setColorBackG(Color.FromArgb(225, 225, 225));
                }
                else
                {
                    row.getCells().add("");
                } 
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setScrollValue(scrollVal);
    }

    /**
    * If the specified fieldName does not exist, returns null
    */
    private String getInputValue(String fieldName) throws Exception {
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(SheetCur.SheetFields[i].FieldName, fieldName))
                {
                    continue;
                }
                 
                return SheetCur.SheetFields[i].FieldValue;
            }
        }
        else
        {
            //pdf
            if (DictAcrobatFields.ContainsKey(fieldName))
            {
                return DictAcrobatFields[fieldName];
            }
             
        } 
        return null;
    }

    /**
    * If no radiobox with that name exists, returns null.  If no box is checked, it returns empty string.
    */
    private String getRadioValue(String fieldName) throws Exception {
        if (SheetCur != null)
        {
            boolean fieldFound = false;
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.CheckBox)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(SheetCur.SheetFields[i].FieldName, fieldName))
                {
                    continue;
                }
                 
                fieldFound = true;
                if (StringSupport.equals(SheetCur.SheetFields[i].FieldValue, "X"))
                {
                    return SheetCur.SheetFields[i].RadioButtonValue;
                }
                 
            }
            if (fieldFound)
            {
                return "";
            }
             
        }
        else
        {
            //but no X
            //pdf
            if (DictAcrobatFields.ContainsKey(fieldName))
            {
                return DictAcrobatFields[fieldName];
            }
             
        } 
        return null;
    }

    /**
    * Only the true condition is tested.  If the specified fieldName does not exist, returns false.
    */
    private boolean isChecked(String fieldName) throws Exception {
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.CheckBox)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(SheetCur.SheetFields[i].FieldName, fieldName))
                {
                    continue;
                }
                 
                if (StringSupport.equals(SheetCur.SheetFields[i].FieldValue, "X"))
                {
                    return true;
                }
                 
            }
        }
        else
        {
            if (DictAcrobatFields.ContainsKey(fieldName))
            {
                if (StringSupport.equals(DictAcrobatFields[fieldName], "true"))
                {
                    return true;
                }
                 
            }
             
        } 
        return false;
    }

    //need to test this
    /**
    * Returns the values of all the "misc" textbox fields on this form.
    */
    private List<String> getMiscValues() throws Exception {
        List<String> retVal = new List<String>();
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(SheetCur.SheetFields[i].FieldName, "misc"))
                {
                    continue;
                }
                 
                retVal.Add(SheetCur.SheetFields[i].FieldValue);
            }
        }
        else
        {
            //pdf
            int suffix = 1;
            String keyname = "misc" + suffix.ToString();
            while (DictAcrobatFields.ContainsKey(keyname))
            {
                //not rigorously tested
                retVal.Add(DictAcrobatFields[keyname]);
                suffix++;
                keyname = "misc" + suffix.ToString();
            }
        } 
        return retVal;
    }

    /**
    * Returns all the sheet fields with FieldNames that start with the passed in string.  Only works for check box, input and output fields for now.
    */
    private List<SheetField> getSheetFieldsByFieldName(String fieldName) throws Exception {
        List<SheetField> retVal = new List<SheetField>();
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.CheckBox && SheetCur.SheetFields[i].FieldType != SheetFieldType.InputField && SheetCur.SheetFields[i].FieldType != SheetFieldType.OutputText)
                {
                    continue;
                }
                 
                if (!SheetCur.SheetFields[i].FieldName.StartsWith(fieldName))
                {
                    continue;
                }
                 
                retVal.Add(SheetCur.SheetFields[i]);
            }
        }
         
        return retVal;
    }

    /**
    * Returns one sheet field with the same FieldName. Returns null if not found.
    */
    private SheetImportRow getImportRowByFieldName(String fieldName) throws Exception {
        if (Rows == null)
        {
            return null;
        }
         
        for (int i = 0;i < Rows.Count;i++)
        {
            if (StringSupport.equals(Rows[i].FieldName, fieldName))
            {
                return Rows[i];
            }
             
        }
        return null;
    }

    /**
    * Loops through the list passed in returns the opposite check box.  Returns null if one is not found.
    */
    private SheetField getOppositeSheetFieldCheckBox(List<SheetField> sheetFieldList, SheetField sheetFieldCur) throws Exception {
        for (int i = 0;i < sheetFieldList.Count;i++)
        {
            if (sheetFieldList[i].SheetFieldNum == sheetFieldCur.SheetFieldNum)
            {
                continue;
            }
             
            //FieldName will be the same.  Ex: allergy:Sudafed
            if (!StringSupport.equals(sheetFieldList[i].FieldName, sheetFieldCur.FieldName))
            {
                continue;
            }
             
            return sheetFieldList[i];
        }
        return null;
    }

    //This has to be the opposite check box.
    /**
    * Returns all checkboxes related to the inputMed passed in.
    */
    private List<SheetField> getRelatedMedicalCheckBoxes(List<SheetField> checkMedList, SheetField inputMed) throws Exception {
        List<SheetField> checkBoxes = new List<SheetField>();
        for (int i = 0;i < checkMedList.Count;i++)
        {
            if (checkMedList[i].FieldName.Remove(0, 8) == inputMed.FieldName.Remove(0, 8))
            {
                checkBoxes.Add(checkMedList[i]);
            }
             
        }
        return checkBoxes;
    }

    private boolean containsOneOfFields(String... fieldNames) throws Exception {
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.CheckBox && SheetCur.SheetFields[i].FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                for (int f = 0;f < fieldNames.Length;f++)
                {
                    if (SheetCur.SheetFields[i].FieldName == fieldNames[f])
                    {
                        return true;
                    }
                     
                }
            }
        }
        else
        {
            for (int f = 0;f < fieldNames.Length;f++)
            {
                if (DictAcrobatFields.ContainsKey(fieldNames[f]))
                {
                    return true;
                }
                 
            }
        } 
        return false;
    }

    private boolean containsFieldThatStartsWith(String fieldName) throws Exception {
        if (SheetCur != null)
        {
            for (int i = 0;i < SheetCur.SheetFields.Count;i++)
            {
                if (SheetCur.SheetFields[i].FieldType != SheetFieldType.CheckBox && SheetCur.SheetFields[i].FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                if (SheetCur.SheetFields[i].FieldName.StartsWith(fieldName))
                {
                    return true;
                }
                 
            }
        }
        else
        {
            for (Object __dummyForeachVar0 : DictAcrobatFields.Keys)
            {
                String fieldkey = (String)__dummyForeachVar0;
                if (fieldkey.StartsWith(fieldName))
                {
                    return true;
                }
                 
            }
        } 
        return false;
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() != 4)
        {
            return ;
        }
         
        if (Rows[e.getRow()].IsSeparator)
        {
            return ;
        }
         
        if (!IsImportable(Rows[e.getRow()]))
        {
            return ;
        }
         
        Rows[e.getRow()].DoImport = !Rows[e.getRow()].DoImport;
        fillGrid();
    }

    /**
    * Mostly the same as IsImportable.  But subtle differences.
    */
    private boolean isEditable(SheetImportRow row) throws Exception {
        if (StringSupport.equals(row.FieldName, "wirelessCarrier"))
        {
            MessageBox.Show(row.FieldName + " " + Lan.g(this,"cannot be imported."));
            return false;
        }
         
        if (StringSupport.equals(row.FieldName, "referredFrom"))
        {
            if (row.OldValObj != null)
            {
                MsgBox.show(this,"This patient already has a referral source selected and it cannot be changed from here.");
                return false;
            }
             
        }
         
        return true;
    }

    //if(row.FieldName.StartsWith("ins1") || row.FieldName.StartsWith("ins2")) {
    //  //if(patPlanList.Count>0) {
    //  MsgBox.Show(this,"Insurance cannot be imported yet.");
    //  return false;
    //  //}
    //}
    private boolean isImportable(SheetImportRow row) throws Exception {
        if (row.ImpValObj == null)
        {
            MsgBox.show(this,"Please enter a value for this row first.");
            return false;
        }
         
        return isEditable(row);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() != 3)
        {
            return ;
        }
         
        if (Rows[e.getRow()].IsSeparator)
        {
            return ;
        }
         
        if (!IsEditable(Rows[e.getRow()]))
        {
            return ;
        }
         
        if (StringSupport.equals(Rows[e.getRow()].FieldName, "referredFrom"))
        {
            FormReferralSelect formRS = new FormReferralSelect();
            formRS.IsSelectionMode = true;
            formRS.ShowDialog();
            if (formRS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            Referral referralSelected = formRS.SelectedReferral;
            Rows[e.getRow()].DoImport = true;
            Rows[e.getRow()].IsFlaggedImp = false;
            Rows[e.getRow()].ImpValDisplay = referralSelected.getNameFL();
            Rows[e.getRow()].ImpValObj = referralSelected;
        }
        else if (Rows[e.getRow()].ObjType == String.class)
        {
            InputBox inputbox = new InputBox(Rows[e.getRow()].FieldName);
            inputbox.textResult.Text = Rows[e.getRow()].ImpValDisplay;
            inputbox.ShowDialog();
            if (inputbox.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            if (StringSupport.equals(Rows[e.getRow()].FieldName, "addressAndHmPhoneIsSameEntireFamily"))
            {
                if (StringSupport.equals(inputbox.textResult.Text, ""))
                {
                    AddressSameForFam = false;
                }
                else if (!StringSupport.equals(inputbox.textResult.Text, "X"))
                {
                    AddressSameForFam = true;
                }
                else
                {
                    MsgBox.show(this,"The only allowed values are X or blank.");
                    return ;
                }  
            }
             
            if (Rows[e.getRow()].OldValDisplay == inputbox.textResult.Text)
            {
                //value is now same as original
                Rows[e.getRow()].DoImport = false;
            }
            else
            {
                Rows[e.getRow()].DoImport = true;
            } 
            Rows[e.getRow()].ImpValDisplay = inputbox.textResult.Text;
            Rows[e.getRow()].ImpValObj = inputbox.textResult.Text;
        }
        else if (Rows[e.getRow()].ObjType.IsEnum)
        {
            //Note.  This only works for zero-indexed enums.
            FormSheetImportEnumPicker formEnum = new FormSheetImportEnumPicker(Rows[e.getRow()].FieldName);
            for (int i = 0;i < Enum.GetNames(Rows[e.getRow()].ObjType).Length;i++)
            {
                formEnum.listResult.Items.Add(Enum.GetNames(Rows[e.getRow()].ObjType)[i]);
                if (Rows[e.getRow()].ImpValObj != null && i == (int)Rows[e.getRow()].ImpValObj)
                {
                    formEnum.listResult.SelectedIndex = i;
                }
                 
            }
            formEnum.ShowDialog();
            if (formEnum.DialogResult == DialogResult.OK)
            {
                int selectedI = formEnum.listResult.SelectedIndex;
                if (Rows[e.getRow()].ImpValObj == null)
                {
                    //was initially null
                    if (selectedI != -1)
                    {
                        //an item was selected
                        Rows[e.getRow()].ImpValObj = Enum.ToObject(Rows[e.getRow()].ObjType, selectedI);
                        Rows[e.getRow()].ImpValDisplay = Rows[e.getRow()].ImpValObj.ToString();
                    }
                     
                }
                else
                {
                    //was not initially null
                    if ((int)Rows[e.getRow()].ImpValObj != selectedI)
                    {
                        //value was changed.
                        //There's no way for the user to set it to null, so we do not need to test that
                        Rows[e.getRow()].ImpValObj = Enum.ToObject(Rows[e.getRow()].ObjType, selectedI);
                        Rows[e.getRow()].ImpValDisplay = Rows[e.getRow()].ImpValObj.ToString();
                    }
                     
                } 
                if (selectedI == -1)
                {
                    Rows[e.getRow()].DoImport = false;
                }
                else //impossible to import a null
                if (Rows[e.getRow()].OldValObj != null && (int)Rows[e.getRow()].ImpValObj == (int)Rows[e.getRow()].OldValObj)
                {
                    //it's the old setting for the patient, whether or not they actually changed it.
                    Rows[e.getRow()].DoImport = false;
                }
                else
                {
                    //so no need to import
                    Rows[e.getRow()].DoImport = true;
                }  
            }
             
        }
        else if (Rows[e.getRow()].ObjType == DateTime.class)
        {
            //this is only for one field so far: Birthdate
            InputBox inputbox = new InputBox(Rows[e.getRow()].FieldName);
            inputbox.textResult.Text = Rows[e.getRow()].ImpValDisplay;
            inputbox.ShowDialog();
            if (inputbox.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            DateTime enteredDate = new DateTime();
            if (StringSupport.equals(inputbox.textResult.Text, ""))
            {
                enteredDate = DateTime.MinValue;
                Rows[e.getRow()].ImpValObj = enteredDate;
                Rows[e.getRow()].ImpValDisplay = "";
            }
            else
            {
                try
                {
                    enteredDate = DateTime.Parse(inputbox.textResult.Text);
                }
                catch (Exception __dummyCatchVar7)
                {
                    MsgBox.show(this,"Invalid date");
                    return ;
                }

                if (enteredDate.Year < 1880 || enteredDate.Year > 2050)
                {
                    MsgBox.show(this,"Invalid date");
                    return ;
                }
                 
                Rows[e.getRow()].ImpValObj = enteredDate;
                Rows[e.getRow()].ImpValDisplay = enteredDate.ToShortDateString();
            } 
            if (Rows[e.getRow()].ImpValDisplay == Rows[e.getRow()].OldValDisplay)
            {
                //value is now same as original
                Rows[e.getRow()].DoImport = false;
            }
            else
            {
                Rows[e.getRow()].DoImport = true;
            } 
        }
        else if (Rows[e.getRow()].ObjType == MedicationPat.class || Rows[e.getRow()].ObjType == Allergy.class || Rows[e.getRow()].ObjType == Disease.class)
        {
            //User entered medications will have a MedicationNum as the ImpValObj.
            if (Rows[e.getRow()].ImpValObj.GetType() == long.class)
            {
                FormMedications FormM = new FormMedications();
                FormM.IsSelectionMode = true;
                FormM.textSearch.Text = Rows[e.getRow()].FieldName;
                FormM.ShowDialog();
                if (FormM.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                Rows[e.getRow()].ImpValDisplay = "Y";
                Rows[e.getRow()].ImpValObj = FormM.SelectedMedicationNum;
                String descript = Medications.getDescription(FormM.SelectedMedicationNum);
                Rows[e.getRow()].FieldDisplay = descript;
                ((SheetField)Rows[e.getRow()].NewValObj).FieldValue = descript;
                Rows[e.getRow()].NewValDisplay = "Y";
                Rows[e.getRow()].DoImport = true;
                Rows[e.getRow()].IsFlaggedImp = false;
            }
            else
            {
                FormSheetImportEnumPicker FormIEP = new FormSheetImportEnumPicker(Rows[e.getRow()].FieldName);
                for (int i = 0;i < Enum.GetNames(YN.class).Length;i++)
                {
                    FormIEP.listResult.Items.Add(Enum.GetNames(YN.class)[i]);
                }
                FormIEP.listResult.SelectedIndex = 0;
                //Unknown
                if (StringSupport.equals(Rows[e.getRow()].ImpValDisplay, "Y"))
                {
                    FormIEP.listResult.SelectedIndex = 1;
                }
                 
                if (StringSupport.equals(Rows[e.getRow()].ImpValDisplay, "N"))
                {
                    FormIEP.listResult.SelectedIndex = 2;
                }
                 
                FormIEP.ShowDialog();
                if (FormIEP.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                int selectedI = FormIEP.listResult.SelectedIndex;
                switch(selectedI)
                {
                    case 0: 
                        Rows[e.getRow()].ImpValDisplay = "";
                        break;
                    case 1: 
                        Rows[e.getRow()].ImpValDisplay = "Y";
                        break;
                    case 2: 
                        Rows[e.getRow()].ImpValDisplay = "N";
                        break;
                
                }
                if (Rows[e.getRow()].OldValDisplay == Rows[e.getRow()].ImpValDisplay)
                {
                    //value is now same as original
                    Rows[e.getRow()].DoImport = false;
                }
                else
                {
                    Rows[e.getRow()].DoImport = true;
                } 
                if (selectedI == -1 || selectedI == 0)
                {
                    Rows[e.getRow()].DoImport = false;
                }
                 
            } 
        }
        else if (Rows[e.getRow()].ObjType == Patient.class)
        {
            Patient subscriber = new Patient();
            FormSubscriberSelect FormSS = new FormSubscriberSelect(Fam);
            FormSS.ShowDialog();
            if (FormSS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            subscriber = Patients.getPat(FormSS.SelectedPatNum);
            if (subscriber == null)
            {
                return ;
            }
             
            //Should never happen but is a possibility.
            //Use GetNameFirst() because this is how OldValDisplay is displayed.
            String patName = Patients.getNameFirst(subscriber.FName,subscriber.Preferred);
            if (StringSupport.equals(Rows[e.getRow()].OldValDisplay, patName))
            {
                Rows[e.getRow()].DoImport = false;
            }
            else
            {
                Rows[e.getRow()].DoImport = true;
            } 
            Rows[e.getRow()].ImpValDisplay = patName;
            Rows[e.getRow()].ImpValObj = subscriber;
        }
        else if (Rows[e.getRow()].ObjType == Carrier.class)
        {
            //Change both carrier rows at the same time.
            String insStr = "ins1";
            if (Rows[e.getRow()].FieldName.StartsWith("ins2"))
            {
                insStr = "ins2";
            }
             
            SheetImportRow carrierNameRow = getImportRowByFieldName(insStr + "CarrierName");
            SheetImportRow carrierPhoneRow = getImportRowByFieldName(insStr + "CarrierPhone");
            Carrier carrier = new Carrier();
            FormCarriers FormC = new FormCarriers();
            FormC.IsSelectMode = true;
            if (carrierNameRow != null)
            {
                FormC.textCarrier.Text = carrierNameRow.NewValDisplay;
            }
             
            if (carrierPhoneRow != null)
            {
                FormC.textPhone.Text = carrierPhoneRow.NewValDisplay;
            }
             
            FormC.ShowDialog();
            if (FormC.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            carrier = FormC.SelectedCarrier;
            //Check for nulls because the name AND phone rows might not both be on the sheet.
            if (carrierNameRow != null)
            {
                if (StringSupport.equals(carrierNameRow.OldValDisplay, carrier.CarrierName))
                {
                    carrierNameRow.DoImport = false;
                }
                else
                {
                    carrierNameRow.DoImport = true;
                } 
                carrierNameRow.ImpValDisplay = carrier.CarrierName;
                carrierNameRow.ImpValObj = carrier;
            }
             
            if (carrierPhoneRow != null)
            {
                if (StringSupport.equals(carrierPhoneRow.OldValDisplay, carrier.Phone))
                {
                    carrierPhoneRow.DoImport = false;
                }
                else
                {
                    carrierPhoneRow.DoImport = true;
                } 
                carrierPhoneRow.ImpValDisplay = carrier.Phone;
                carrierPhoneRow.ImpValObj = carrier;
            }
             
        }
               
        fillGrid();
    }

    /**
    * Correctly sets the class wide boolean HasRequiredInsFields.  Loops through all the fields on the sheet and makes sure all the required insurance fields needed to import are present for primary OR for secondary insurance.  If some required fields are missing for an insurance, all related ins fields will have DoImport set to false.  Called after the list "Rows" has been filled.
    */
    private void setHasRequiredInsFields() throws Exception {
        //Start off assuming that neither primary nor secondary have the required insurance fields necessary to import insurance.
        HasRequiredInsFields = false;
        if (checkSheetForInsFields(true))
        {
            //Check primary fields.
            HasRequiredInsFields = true;
        }
        else
        {
            //Sheet does not have the required fields to import primary insurance.
            setDoImportToFalseForIns(true);
        } 
        //Unmark all primary ins fields for import.
        if (checkSheetForInsFields(false))
        {
            //Check secondary fields.
            HasRequiredInsFields = true;
        }
        else
        {
            //Sheet does not have the required fields to import secondary insurance.
            setDoImportToFalseForIns(false);
        } 
    }

    //Unmark all secondary ins fields for import.
    /**
    * Returns true if all the required insurance fields needed to import are present on the current sheet.  Only call after the list "Rows" has been filled.
    */
    private boolean checkSheetForInsFields(boolean isPrimary) throws Exception {
        String insStr = "ins1";
        if (!isPrimary)
        {
            insStr = "ins2";
        }
         
        //Load up all five required insurance rows.
        SheetImportRow relationRow = getImportRowByFieldName(insStr + "Relat");
        SheetImportRow subscriberRow = getImportRowByFieldName(insStr + "Subscriber");
        SheetImportRow subscriberIdRow = getImportRowByFieldName(insStr + "SubscriberID");
        SheetImportRow carrierNameRow = getImportRowByFieldName(insStr + "CarrierName");
        SheetImportRow carrierPhoneRow = getImportRowByFieldName(insStr + "CarrierPhone");
        //Check if all of the required insurance fields exist on this sheet.
        if (relationRow == null || subscriberRow == null || subscriberIdRow == null || carrierNameRow == null || carrierPhoneRow == null)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Loops through the related ins fields and forces DoImport to false.
    */
    private void setDoImportToFalseForIns(boolean isPrimary) throws Exception {
        boolean changed = false;
        String insStr = "ins1";
        if (!isPrimary)
        {
            insStr = "ins2";
        }
         
        //Only five ins fields have the possibility of DoImport being automatically set to true.  The others require a double click.
        SheetImportRow relationRow = getImportRowByFieldName(insStr + "Relat");
        SheetImportRow subscriberIdRow = getImportRowByFieldName(insStr + "SubscriberID");
        SheetImportRow employerNameRow = getImportRowByFieldName(insStr + "EmployerName");
        SheetImportRow groupNameRow = getImportRowByFieldName(insStr + "GroupName");
        SheetImportRow groupNumRow = getImportRowByFieldName(insStr + "GroupNum");
        if (relationRow != null)
        {
            relationRow.DoImport = false;
            changed = true;
        }
         
        if (subscriberIdRow != null)
        {
            subscriberIdRow.DoImport = false;
            changed = true;
        }
         
        if (employerNameRow != null)
        {
            employerNameRow.DoImport = false;
            changed = true;
        }
         
        if (groupNameRow != null)
        {
            groupNameRow.DoImport = false;
            changed = true;
        }
         
        if (groupNumRow != null)
        {
            groupNumRow.DoImport = false;
            changed = true;
        }
         
        if (changed)
        {
            fillGrid();
        }
         
    }

    //A change was made, refresh the grid.
    /**
    * Returns false if validation fails.  Returns true if all required insurance fields exist, import fields have valid values, and the insurance plan has been imported successfully.  The user will have the option to pick an existing ins plan.  If any fields on the selected plan do not exactly match the imported fields, they will be prompted to choose between the selected plan's values or to create a new ins plan with the import values.  After validating, the actual import of the new ins plan takes place.  That might consist of dropping the current plan and replacing it or simply inserting the new plan.
    */
    private boolean validateAndImportInsurance(boolean isPrimary) throws Exception {
        String insStr = "";
        String insWarnStr = "";
        byte ordinal = new byte();
        if (isPrimary)
        {
            insStr = "ins1";
            insWarnStr = "primary insurance";
            ordinal = 1;
        }
        else
        {
            insStr = "ins2";
            insWarnStr = "secondary insurance";
            ordinal = 2;
        } 
        //Load up every insurance row related to the particular ins.
        SheetImportRow relationRow = getImportRowByFieldName(insStr + "Relat");
        SheetImportRow subscriberRow = getImportRowByFieldName(insStr + "Subscriber");
        SheetImportRow subscriberIdRow = getImportRowByFieldName(insStr + "SubscriberID");
        SheetImportRow carrierNameRow = getImportRowByFieldName(insStr + "CarrierName");
        SheetImportRow carrierPhoneRow = getImportRowByFieldName(insStr + "CarrierPhone");
        SheetImportRow employerNameRow = getImportRowByFieldName(insStr + "EmployerName");
        SheetImportRow groupNameRow = getImportRowByFieldName(insStr + "GroupName");
        SheetImportRow groupNumRow = getImportRowByFieldName(insStr + "GroupNum");
        //Check if the required insurance fields exist on this sheet.
        //NOTE: Employer, group name and group num are optional fields.
        //Checking for nulls in the required fields still needs to be here in this method in case the user has the required fields for one insurance plan but not enough for the other.  They will hit this code ONLY if they have flagged one of the fields on the "other" insurance plan for import that does not have all of the required fields.
        if (relationRow == null || subscriberRow == null || subscriberIdRow == null || carrierNameRow == null || carrierPhoneRow == null)
        {
            MessageBox.Show(Lan.g(this,"Required ") + insWarnStr + Lan.g(this," fields are missing on this sheet.  You cannot import ") + insWarnStr + Lan.g(this," with this sheet until it contains all of required fields.  Required fields: Relationship, Subscriber, SubscriberID, CarrierName, and CarrierPhone."));
            return false;
        }
         
        if (relationRow.ImpValObj == null || subscriberRow.ImpValObj == null || StringSupport.equals((String)subscriberIdRow.ImpValObj, "") || carrierNameRow.ImpValObj == null || carrierPhoneRow.ImpValObj == null)
        {
            MessageBox.Show(Lan.g(this,"Cannot import ") + insWarnStr + Lan.g(this," until all required fields have been set.  Required fields: Relationship, Subscriber, SubscriberID, CarrierName, and CarrierPhone."));
            return false;
        }
         
        InsPlan plan = null;
        InsSub sub = null;
        long insSubNum = 0;
        long employerNum = 0;
        //Get the employer from the db.  If no matching employer found, a new one will automatically get created.
        if (employerNameRow != null && !StringSupport.equals(employerNameRow.ImpValDisplay.Trim(), ""))
        {
            employerNum = Employers.getEmployerNum(employerNameRow.ImpValDisplay);
        }
         
        Patient subscriber = (Patient)subscriberRow.ImpValObj;
        //Have user pick a plan------------------------------------------------------------------------------------------------------------
        boolean planIsNew = false;
        List<InsSub> subList = InsSubs.getListForSubscriber(subscriber.PatNum);
        FormInsPlans FormIP = new FormInsPlans();
        FormIP.carrierText = carrierNameRow.ImpValDisplay;
        if (employerNameRow != null)
        {
            FormIP.empText = employerNameRow.ImpValDisplay;
        }
         
        if (groupNameRow != null)
        {
            FormIP.groupNameText = groupNameRow.ImpValDisplay;
        }
         
        if (groupNumRow != null)
        {
            FormIP.groupNumText = groupNumRow.ImpValDisplay;
        }
         
        FormIP.IsSelectMode = true;
        FormIP.ShowDialog();
        if (FormIP.DialogResult != DialogResult.OK)
        {
            return false;
        }
         
        plan = FormIP.SelectedPlan;
        if (plan.PlanNum == 0)
        {
            //User clicked blank plan, so a new plan will be created using the import values.
            planIsNew = true;
        }
        else
        {
            for (int i = 0;i < subList.Count;i++)
            {
                //An existing plan was selected so see if the plan is already subscribed to by the subscriber or create a new inssub.  Patplan will be taken care of later.
                if (subList[i].PlanNum == plan.PlanNum)
                {
                    sub = subList[i];
                    insSubNum = sub.InsSubNum;
                }
                 
            }
            if (sub == null)
            {
                //Create a new inssub if subscriber is not subscribed to this plan yet.
                sub = new InsSub();
                sub.PlanNum = plan.PlanNum;
                sub.Subscriber = subscriber.PatNum;
                sub.SubscriberID = subscriberIdRow.ImpValDisplay;
                sub.ReleaseInfo = true;
                sub.AssignBen = true;
                insSubNum = InsSubs.insert(sub);
            }
             
        } 
        //User picked a plan but the information they want to import might be different than the chosen plan.  Give them options to use current values or created a new plan.
        //It's still okay to let the user return at this point in order to change importing information.
        DialogResult result = new DialogResult();
        //Carrier check-----------------------------------------------------------------------------------------
        if (!planIsNew && plan.CarrierNum != ((Carrier)carrierNameRow.ImpValObj).CarrierNum)
        {
            result = insuranceImportQuestion("carrier",isPrimary);
            //Yes means the user wants to keep the information on the plan they picked, nothing to do.
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        //Employer check----------------------------------------------------------------------------------------
        if (!planIsNew && employerNum > 0 && plan.EmployerNum != employerNum)
        {
            result = insuranceImportQuestion("employer",isPrimary);
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        //Subscriber check--------------------------------------------------------------------------------------
        if (!planIsNew && sub.Subscriber != ((Patient)subscriberRow.ImpValObj).PatNum)
        {
            result = insuranceImportQuestion("subscriber",isPrimary);
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        if (!planIsNew && !StringSupport.equals(sub.SubscriberID, subscriberIdRow.ImpValDisplay))
        {
            result = insuranceImportQuestion("subscriber id",isPrimary);
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        //Group name check--------------------------------------------------------------------------------------
        if (groupNameRow != null && !planIsNew && !StringSupport.equals(plan.GroupName, groupNameRow.ImpValDisplay))
        {
            result = insuranceImportQuestion("group name",isPrimary);
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        //Group num check---------------------------------------------------------------------------------------
        if (groupNumRow != null && !planIsNew && !StringSupport.equals(plan.GroupNum, groupNumRow.ImpValDisplay))
        {
            result = insuranceImportQuestion("group num",isPrimary);
            if (result == DialogResult.No)
            {
                planIsNew = true;
            }
             
            if (result == DialogResult.Cancel)
            {
                return false;
            }
             
        }
         
        //Create a new plan-------------------------------------------------------------------------------------
        if (planIsNew)
        {
            plan = new InsPlan();
            if (employerNum > 0)
            {
                plan.EmployerNum = employerNum;
            }
             
            plan.PlanType = "";
            plan.CarrierNum = ((Carrier)carrierNameRow.ImpValObj).CarrierNum;
            if (groupNameRow != null)
            {
                plan.GroupName = groupNameRow.ImpValDisplay;
            }
             
            if (groupNumRow != null)
            {
                plan.GroupNum = groupNumRow.ImpValDisplay;
            }
             
            InsPlans.insert(plan);
            sub = new InsSub();
            sub.PlanNum = plan.PlanNum;
            sub.Subscriber = subscriber.PatNum;
            sub.SubscriberID = subscriberIdRow.ImpValDisplay;
            sub.ReleaseInfo = true;
            sub.AssignBen = true;
            insSubNum = InsSubs.insert(sub);
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
                ben.PlanNum = plan.PlanNum;
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
                ben.PlanNum = plan.PlanNum;
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
                ben.PlanNum = plan.PlanNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = 0;
                ben.Percent = -1;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                Benefits.insert(ben);
            }
             
        }
         
        //Delete the old patplan-------------------------------------------------------------------------------------------------------------
        if (isPrimary && PatPlan1 != null)
        {
            //Importing primary and currently has primary ins.
            PatPlans.deleteNonContiguous(PatPlan1.PatPlanNum);
        }
         
        if (!isPrimary && PatPlan2 != null)
        {
            //Importing secondary and currently has secondary ins.
            PatPlans.deleteNonContiguous(PatPlan2.PatPlanNum);
        }
         
        //Then attach new patplan to the plan------------------------------------------------------------------------------------------------
        PatPlan patplan = new PatPlan();
        patplan.Ordinal = ordinal;
        //Not allowed to be 0.
        patplan.PatNum = PatCur.PatNum;
        patplan.InsSubNum = insSubNum;
        patplan.Relationship = ((Relat)relationRow.ImpValObj);
        PatPlans.insert(patplan);
        //After new plan has been imported, recompute all estimates for this patient because their coverage is now different.  Also set patient.HasIns to the correct value.
        List<ClaimProc> claimProcs = ClaimProcs.refresh(PatCur.PatNum);
        List<Procedure> procs = Procedures.refresh(PatCur.PatNum);
        List<PatPlan> patPlans = PatPlans.refresh(PatCur.PatNum);
        subList = InsSubs.refreshForFam(Fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<Benefit> benList = Benefits.Refresh(patPlans, subList);
        Procedures.ComputeEstimatesForAll(PatCur.PatNum, claimProcs, procs, planList, patPlans, benList, PatCur.getAge(), subList);
        Patients.setHasIns(PatCur.PatNum);
        return true;
    }

    /**
    * Displays a yes no cancel message to the user indicating that the import value does not match the selected plan.  They will choose to use the current plan's value or create a new plan.  Only called from ValidateAndImportInsurance.
    */
    private DialogResult insuranceImportQuestion(String importValue, boolean isPrimary) throws Exception {
        String insStr = "primary ";
        if (!isPrimary)
        {
            insStr = "secondary ";
        }
         
        return MessageBox.Show(Lan.g(this,"The ") + insStr + importValue + Lan.g(this," does not match the selected plan's ") + importValue + ".\r\n" + Lan.g(this,"Use the selected plan's ") + importValue + "?\r\n\r\n" + Lan.g(this,"No will create a new plan using all of the import values."), Lan.g(this,"Import ") + insStr + importValue, MessageBoxButtons.YesNoCancel);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        boolean importsPresent = false;
        for (int i = 0;i < Rows.Count;i++)
        {
            if (Rows[i].DoImport)
            {
                importsPresent = true;
                break;
            }
             
        }
        if (!importsPresent)
        {
            MsgBox.show(this,"No rows are set for import.");
            return ;
        }
         
        if (SheetCur.SheetType == SheetTypeEnum.PatientForm)
        {
            boolean importPriIns = false;
            boolean importSecIns = false;
            for (int i = 0;i < Rows.Count;i++)
            {
                if (!Rows[i].DoImport)
                {
                    continue;
                }
                 
                //Importing insurance happens later.
                if (Rows[i].FieldName.StartsWith("ins1"))
                {
                    importPriIns = true;
                    continue;
                }
                 
                if (Rows[i].FieldName.StartsWith("ins2"))
                {
                    importSecIns = true;
                    continue;
                }
                 
                FieldName __dummyScrutVar1 = Rows[i].FieldName;
                if (__dummyScrutVar1.equals("LName"))
                {
                    PatCur.LName = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("FName"))
                {
                    PatCur.FName = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("MiddleI"))
                {
                    PatCur.MiddleI = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("Preferred"))
                {
                    PatCur.Preferred = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("Gender"))
                {
                    PatCur.Gender = (PatientGender)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("Position"))
                {
                    PatCur.Position = (PatientPosition)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("Birthdate"))
                {
                    PatCur.Birthdate = (DateTime)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("SSN"))
                {
                    PatCur.SSN = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("WkPhone"))
                {
                    PatCur.WkPhone = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("WirelessPhone"))
                {
                    PatCur.WirelessPhone = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("Email"))
                {
                    PatCur.Email = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("PreferContactMethod"))
                {
                    PatCur.PreferContactMethod = (ContactMethod)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("PreferConfirmMethod"))
                {
                    PatCur.PreferConfirmMethod = (ContactMethod)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("PreferRecallMethod"))
                {
                    PatCur.PreferRecallMethod = (ContactMethod)Rows[i].ImpValObj;
                }
                else if (__dummyScrutVar1.equals("referredFrom"))
                {
                    RefAttach ra = new RefAttach();
                    ra.IsFrom = true;
                    ra.ItemOrder = 1;
                    ra.PatNum = PatCur.PatNum;
                    ra.RefDate = DateTimeOD.getToday();
                    ra.ReferralNum = ((Referral)Rows[i].ImpValObj).ReferralNum;
                    RefAttaches.Insert(ra);
                    //no security to block this action.
                    SecurityLogs.makeLogEntry(Permissions.RefAttachAdd,PatCur.PatNum,"Referred From " + Referrals.GetNameFL(ra.ReferralNum));
                }
                else //AddressSameForFam already set, but not really importable by itself
                if (__dummyScrutVar1.equals("Address"))
                {
                    PatCur.Address = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("Address2"))
                {
                    PatCur.Address2 = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("City"))
                {
                    PatCur.City = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("State"))
                {
                    PatCur.State = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("Zip"))
                {
                    PatCur.Zip = Rows[i].ImpValDisplay;
                }
                else if (__dummyScrutVar1.equals("HmPhone"))
                {
                    PatCur.HmPhone = Rows[i].ImpValDisplay;
                }
                                     
            }
            //Insurance importing happens before updating the patient information because there is a possibility of returning for more information.
            if (HasRequiredInsFields)
            {
                //Do not attempt to import any insurance unless they have the required fields for importing.
                boolean primaryImported = false;
                if (importPriIns)
                {
                    //A primary insurance field was flagged for importing.
                    if (!validateAndImportInsurance(true))
                    {
                        return ;
                    }
                     
                    //Field missing or user chose to back out to correct information.
                    //Nothing has been updated so it's okay to just return here.
                    primaryImported = true;
                }
                 
                if (importSecIns)
                {
                    //A secondary insurance field was flagged for importing.
                    if (!validateAndImportInsurance(false))
                    {
                        //Field missing or user chose to back out to correct information.
                        if (primaryImported)
                        {
                            //Primary has been imported, we cannot return at this point.  Simply notify the user that secondary could not be imported correctly.
                            MsgBox.show(this,"Primary insurance was imported successfully but secondary was unable to import.");
                        }
                        else
                        {
                            return ;
                        } 
                    }
                     
                }
                 
            }
            else
            {
                //Secondary had problems importing or the user chose to back out and correct information.
                //Nothing has been updated so it's okay to just return here.
                //Sheet does not contain the required ins fields.
                if (importPriIns)
                {
                    //The user has manually flagged a primary ins row for importing.
                    MsgBox.show(this,"Required primary insurance fields are missing on this sheet.  You cannot import primary insurance with this sheet until it contains all of required fields.  Required fields: Relationship, Subscriber, SubscriberID, CarrierName, and CarrierPhone.");
                }
                 
                if (importSecIns)
                {
                    //The user has manually flagged a secondary ins row for importing.
                    MsgBox.show(this,"Required secondary insurance fields are missing on this sheet.  You cannot import secondary insurance with this sheet until it contains all of required fields.  Required fields: Relationship, Subscriber, SubscriberID, CarrierName, and CarrierPhone.");
                }
                 
            } 
            //Patient information updating---------------------------------------------------------------------------------------------------------
            Patients.update(PatCur,PatOld);
            if (AddressSameForFam)
            {
                Patients.updateAddressForFam(PatCur);
            }
             
        }
        else if (SheetCur.SheetType == SheetTypeEnum.MedicalHistory)
        {
            for (int i = 0;i < Rows.Count;i++)
            {
                if (!Rows[i].DoImport)
                {
                    continue;
                }
                 
                if (Rows[i].ObjType == null)
                {
                    continue;
                }
                 
                //Should never happen.
                YN hasValue = YN.Unknown;
                if (StringSupport.equals(Rows[i].ImpValDisplay, "Y"))
                {
                    hasValue = YN.Yes;
                }
                 
                if (StringSupport.equals(Rows[i].ImpValDisplay, "N"))
                {
                    hasValue = YN.No;
                }
                 
                if (hasValue == YN.Unknown)
                {
                    continue;
                }
                 
                //Unknown, nothing to do.
                if (Rows[i].ObjType == Allergy.class)
                {
                    //Patient has this allergy in the db so just update the value.
                    if (Rows[i].OldValObj != null)
                    {
                        Allergy oldAllergy = (Allergy)Rows[i].OldValObj;
                        if (hasValue == YN.Yes)
                        {
                            oldAllergy.StatusIsActive = true;
                        }
                        else
                        {
                            oldAllergy.StatusIsActive = false;
                        } 
                        Allergies.update(oldAllergy);
                        continue;
                    }
                     
                    if (hasValue == YN.No)
                    {
                        continue;
                    }
                     
                    //We never import allergies with inactive status.
                    //Allergy does not exist for this patient yet so create one.
                    List<AllergyDef> allergyList = AllergyDefs.getAll(false);
                    SheetField allergySheet = (SheetField)Rows[i].NewValObj;
                    for (int j = 0;j < allergyList.Count;j++)
                    {
                        //Find what allergy user wants to import.
                        if (allergyList[j].Description == allergySheet.FieldName.Remove(0, 8))
                        {
                            Allergy newAllergy = new Allergy();
                            newAllergy.AllergyDefNum = allergyList[j].AllergyDefNum;
                            newAllergy.PatNum = PatCur.PatNum;
                            newAllergy.StatusIsActive = true;
                            Allergies.insert(newAllergy);
                            break;
                        }
                         
                    }
                }
                else if (Rows[i].ObjType == MedicationPat.class)
                {
                    //Patient has this medication in the db so leave it alone or set the stop date.
                    if (Rows[i].OldValObj != null)
                    {
                        //Set the stop date for the current medication(s).
                        MedicationPat oldMedPat = (MedicationPat)Rows[i].OldValObj;
                        if (hasValue == YN.Yes)
                        {
                            if (!MedicationPats.isMedActive(oldMedPat))
                            {
                                oldMedPat.DateStop = new DateTime(0001, 1, 1);
                            }
                             
                        }
                        else
                        {
                            //This will activate the med.
                            oldMedPat.DateStop = DateTime.Today;
                        } 
                        //Set the med as inactive.
                        MedicationPats.update(oldMedPat);
                        continue;
                    }
                     
                    if (hasValue == YN.No)
                    {
                        continue;
                    }
                     
                    //Don't import medications with inactive status.
                    //Medication does not exist for this patient yet so create it.
                    List<Medication> medList = Medications.getList("");
                    SheetField medSheet = (SheetField)Rows[i].NewValObj;
                    for (int j = 0;j < medList.Count;j++)
                    {
                        //Find what medication user wants to import.
                        if (StringSupport.equals(Medications.GetDescription(medList[j].MedicationNum), medSheet.FieldValue))
                        {
                            MedicationPat medPat = new MedicationPat();
                            medPat.PatNum = PatCur.PatNum;
                            medPat.MedicationNum = medList[j].MedicationNum;
                            MedicationPats.insert(medPat);
                            break;
                        }
                         
                    }
                }
                else if (Rows[i].ObjType == Disease.class)
                {
                    //Patient has this problem in the db so just update the value.
                    if (Rows[i].OldValObj != null)
                    {
                        Disease oldDisease = (Disease)Rows[i].OldValObj;
                        if (hasValue == YN.Yes)
                        {
                            oldDisease.ProbStatus = ProblemStatus.Active;
                        }
                        else
                        {
                            oldDisease.ProbStatus = ProblemStatus.Inactive;
                        } 
                        Diseases.update(oldDisease);
                        continue;
                    }
                     
                    if (hasValue == YN.No)
                    {
                        continue;
                    }
                     
                    //Don't create new problem with inactive status.
                    //Problem does not exist for this patient yet so create one.
                    SheetField diseaseSheet = (SheetField)Rows[i].NewValObj;
                    for (int j = 0;j < DiseaseDefs.getList().Length;j++)
                    {
                        //Find what allergy user wants to import.
                        if (DiseaseDefs.getList()[j].DiseaseName == diseaseSheet.FieldName.Remove(0, 8))
                        {
                            Disease newDisease = new Disease();
                            newDisease.PatNum = PatCur.PatNum;
                            newDisease.DiseaseDefNum = DiseaseDefs.getList()[j].DiseaseDefNum;
                            newDisease.ProbStatus = ProblemStatus.Active;
                            Diseases.insert(newDisease);
                            break;
                        }
                         
                    }
                }
                   
            }
        }
          
        MsgBox.show(this,"Done.");
        DialogResult = DialogResult.OK;
    }

    private boolean doImport(String fieldName) throws Exception {
        for (int i = 0;i < Rows.Count;i++)
        {
            if (!StringSupport.equals(Rows[i].FieldName, fieldName))
            {
                continue;
            }
             
            return Rows[i].DoImport;
        }
        return false;
    }

    /**
    * Will return null if field not found or if field marked to not import.
    */
    private Object getImpObj(String fieldName) throws Exception {
        for (int i = 0;i < Rows.Count;i++)
        {
            if (!StringSupport.equals(Rows[i].FieldName, fieldName))
            {
                continue;
            }
             
            if (!Rows[i].DoImport)
            {
                return null;
            }
             
            return Rows[i].ImpValObj;
        }
        return null;
    }

    /**
    * Will return empty string field not found or if field marked to not import.
    */
    private String getImpDisplay(String fieldName) throws Exception {
        for (int i = 0;i < Rows.Count;i++)
        {
            if (!StringSupport.equals(Rows[i].FieldName, fieldName))
            {
                continue;
            }
             
            if (!Rows[i].DoImport)
            {
                return "";
            }
             
            return Rows[i].ImpValDisplay;
        }
        return "";
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Returns a separator and sets the FieldName to the passed in string.
    */
    private SheetImportRow createSeparator(String displayText) throws Exception {
        SheetImportRow separator = new SheetImportRow();
        separator.FieldName = displayText;
        separator.IsSeparator = true;
        return separator;
    }

    private static class SheetImportRow   
    {
        public String FieldName = new String();
        /**
        * Overrides FieldName.  If null, use FieldName;
        */
        public String FieldDisplay = new String();
        public String OldValDisplay = new String();
        public Object OldValObj = new Object();
        public String NewValDisplay = new String();
        public Object NewValObj = new Object();
        public String ImpValDisplay = new String();
        public Object ImpValObj = new Object();
        public boolean DoImport = new boolean();
        public boolean IsSeparator = new boolean();
        /**
        * This is needed because the NewValObj might be null.
        */
        public Type ObjType = new Type();
        /**
        * Some fields are not importable, but they still need to be made obvious to user by coloring the user-entered value red.
        */
        public boolean IsFlagged = new boolean();
        /**
        * The import cell is shown with colored text to prompt user to notice.
        */
        public boolean IsFlaggedImp = new boolean();
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
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 629);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(614, 23);
        this.label1.TabIndex = 6;
        this.label1.Text = "Double click to edit an import value.";
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(27, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridMain.Size = new System.Drawing.Size(747, 605);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Sheet Import");
        this.gridMain.setTranslationName("FormSheetImport");
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(785, 593);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "Import";
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
        this.butCancel.Location = new System.Drawing.Point(785, 623);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetImport
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(880, 663);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetImport";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Sheet Import";
        this.Load += new System.EventHandler(this.FormSheetImport_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


