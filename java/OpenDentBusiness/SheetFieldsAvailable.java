//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.OutInCheck;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.Relat;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;

public class SheetFieldsAvailable   
{
    /*public static List<SheetFieldDef> GetListInput(SheetTypeEnum sheetType){
    			return GetList(sheetType,OutInCheck.In);
    		}
    		public static List<SheetFieldDef> GetListOutput(SheetTypeEnum sheetType){
    			return GetList(sheetType,OutInCheck.Out);
    		}
    		public static List<SheetFieldDef> GetListCheckBox(SheetTypeEnum sheetType){
    			return GetList(sheetType,OutInCheck.Check);
    		}*/
    /**
    * This is the list of input, output, or checkbox fieldnames for user to pick from.
    */
    public static List<SheetFieldDef> getList(SheetTypeEnum sheetType, OutInCheck outInCheck) throws Exception {
        switch(sheetType)
        {
            case LabelPatient: 
                return getLabelPatient(outInCheck);
            case LabelCarrier: 
                return getLabelCarrier(outInCheck);
            case LabelReferral: 
                return getLabelReferral(outInCheck);
            case ReferralSlip: 
                return getReferralSlip(outInCheck);
            case LabelAppointment: 
                return getLabelAppointment(outInCheck);
            case Rx: 
                return getRx(outInCheck);
            case Consent: 
                return getConsent(outInCheck);
            case PatientLetter: 
                return getPatientLetter(outInCheck);
            case ReferralLetter: 
                return getReferralLetter(outInCheck);
            case PatientForm: 
                return getPatientForm(outInCheck);
            case RoutingSlip: 
                return getRoutingSlip(outInCheck);
            case MedicalHistory: 
                return getMedicalHistory(outInCheck);
            case LabSlip: 
                return getLabSlip(outInCheck);
            case ExamSheet: 
                return getExamSheet(outInCheck);
            case DepositSlip: 
                return getDepositSlip(outInCheck);
        
        }
        return new List<SheetFieldDef>();
    }

    /**
    * For a given fieldName, return all the allowed radioButtonValues.  Will frequently be an empty list if a checkbox with this fieldname is not allowed to act as a radiobutton.
    */
    public static List<String> getRadio(String fieldName) throws Exception {
        List<String> retVal = new List<String>();
        String[] stringAr = null;
        System.String __dummyScrutVar1 = fieldName;
        if (__dummyScrutVar1.equals("Gender"))
        {
            stringAr = Enum.GetNames(PatientGender.class);
        }
        else if (__dummyScrutVar1.equals("ins1Relat") || __dummyScrutVar1.equals("ins2Relat"))
        {
            stringAr = Enum.GetNames(Relat.class);
        }
        else if (__dummyScrutVar1.equals("Position"))
        {
            stringAr = Enum.GetNames(PatientPosition.class);
        }
        else if (__dummyScrutVar1.equals("PreferContactMethod") || __dummyScrutVar1.equals("PreferConfirmMethod") || __dummyScrutVar1.equals("PreferRecallMethod"))
        {
            stringAr = Enum.GetNames(ContactMethod.class);
        }
        else if (__dummyScrutVar1.equals("StudentStatus"))
        {
            retVal.Add("Nonstudent");
            retVal.Add("Parttime");
            retVal.Add("Fulltime");
            return retVal;
        }
        else if (__dummyScrutVar1.equals("Race"))
        {
            //Sheets use PatientRaceOld for display purposes only.  They are not imported.  Therefore, the patientrace table does not need to be used here.
            stringAr = Enum.GetNames(PatientRaceOld.class);
        }
        else
        {
            return retVal;
        }      
        for (int i = 0;i < stringAr.Length;i++)
        {
            retVal.Add(stringAr[i]);
        }
        return retVal;
    }

    private static SheetFieldDef newOutput(String fieldName) throws Exception {
        return new SheetFieldDef(SheetFieldType.OutputText, fieldName, "", 0, "", false, 0, 0, 0, 0, GrowthBehaviorEnum.None, "");
    }

    private static SheetFieldDef newInput(String fieldName) throws Exception {
        return new SheetFieldDef(SheetFieldType.InputField, fieldName, "", 0, "", false, 0, 0, 0, 0, GrowthBehaviorEnum.None, "");
    }

    private static SheetFieldDef newCheck(String fieldName) throws Exception {
        return new SheetFieldDef(SheetFieldType.CheckBox, fieldName, "", 0, "", false, 0, 0, 0, 0, GrowthBehaviorEnum.None, "");
    }

    private static SheetFieldDef newRadio(String fieldName, String radioButtonValue) throws Exception {
        return new SheetFieldDef(SheetFieldType.CheckBox, fieldName, "", 0, "", false, 0, 0, 0, 0, GrowthBehaviorEnum.None, radioButtonValue);
    }

    private static SheetFieldDef newSpecial(String fieldName) throws Exception {
        return new SheetFieldDef(SheetFieldType.Special, fieldName, "", 0, "", false, 0, 0, 0, 0, GrowthBehaviorEnum.None, "");
    }

    private static List<SheetFieldDef> getLabelPatient(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("nameFL"));
            list.Add(newOutput("nameLF"));
            list.Add(newOutput("address"));
            //includes address2
            list.Add(newOutput("cityStateZip"));
            list.Add(newOutput("ChartNumber"));
            list.Add(newOutput("PatNum"));
            list.Add(newOutput("dateTime.Today"));
            list.Add(newOutput("birthdate"));
            list.Add(newOutput("priProvName"));
        }
         
        return list;
    }

    private static List<SheetFieldDef> getLabelCarrier(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("CarrierName"));
            list.Add(newOutput("address"));
            //includes address2
            list.Add(newOutput("cityStateZip"));
        }
         
        return list;
    }

    private static List<SheetFieldDef> getLabelReferral(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("nameFL"));
            //includes Title
            list.Add(newOutput("address"));
            //includes address2
            list.Add(newOutput("cityStateZip"));
        }
         
        return list;
    }

    private static List<SheetFieldDef> getReferralSlip(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("referral.nameFL"));
            list.Add(newOutput("referral.address"));
            list.Add(newOutput("referral.cityStateZip"));
            list.Add(newOutput("referral.phone"));
            list.Add(newOutput("referral.phone2"));
            list.Add(newOutput("patient.nameFL"));
            list.Add(newOutput("dateTime.Today"));
            list.Add(newOutput("patient.WkPhone"));
            list.Add(newOutput("patient.HmPhone"));
            list.Add(newOutput("patient.WirelessPhone"));
            list.Add(newOutput("patient.address"));
            list.Add(newOutput("patient.cityStateZip"));
            list.Add(newOutput("patient.provider"));
        }
        else if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("notes"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("misc"));
        }
           
        return list;
    }

    private static List<SheetFieldDef> getLabelAppointment(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("nameFL"));
            list.Add(newOutput("nameLF"));
            list.Add(newOutput("weekdayDateTime"));
            list.Add(newOutput("length"));
        }
         
        return list;
    }

    private static List<SheetFieldDef> getRx(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("prov.nameFL"));
            list.Add(newOutput("prov.address"));
            list.Add(newOutput("prov.cityStateZip"));
            list.Add(newOutput("prov.phone"));
            list.Add(newOutput("RxDate"));
            list.Add(newOutput("RxDateMonthSpelled"));
            list.Add(newOutput("prov.dEANum"));
            list.Add(newOutput("pat.nameFL"));
            list.Add(newOutput("pat.Birthdate"));
            list.Add(newOutput("pat.HmPhone"));
            list.Add(newOutput("pat.address"));
            list.Add(newOutput("pat.cityStateZip"));
            list.Add(newOutput("Drug"));
            list.Add(newOutput("Disp"));
            list.Add(newOutput("Sig"));
            list.Add(newOutput("Refills"));
            list.Add(newOutput("prov.stateRxID"));
            list.Add(newOutput("prov.StateLicense"));
            list.Add(newOutput("prov.NationalProvID"));
        }
        else if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("notes"));
        }
          
        return list;
    }

    private static List<SheetFieldDef> getConsent(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("dateTime.Today"));
            list.Add(newOutput("patient.nameFL"));
        }
        else if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("toothNum"));
            list.Add(newInput("misc"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("misc"));
        }
           
        return list;
    }

    private static List<SheetFieldDef> getPatientLetter(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("PracticeTitle"));
            list.Add(newOutput("PracticeAddress"));
            list.Add(newOutput("practiceCityStateZip"));
            list.Add(newOutput("patient.nameFL"));
            list.Add(newOutput("patient.address"));
            list.Add(newOutput("patient.cityStateZip"));
            list.Add(newOutput("today.DayDate"));
            list.Add(newOutput("patient.salutation"));
            list.Add(newOutput("patient.priProvNameFL"));
        }
        else if (outInCheck == OutInCheck.In)
        {
        }
          
        return list;
    }

    //none
    private static List<SheetFieldDef> getReferralLetter(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("PracticeTitle"));
            list.Add(newOutput("PracticeAddress"));
            list.Add(newOutput("practiceCityStateZip"));
            list.Add(newOutput("referral.phone"));
            list.Add(newOutput("referral.phone2"));
            list.Add(newOutput("referral.nameFL"));
            list.Add(newOutput("referral.address"));
            list.Add(newOutput("referral.cityStateZip"));
            list.Add(newOutput("today.DayDate"));
            list.Add(newOutput("patient.nameFL"));
            list.Add(newOutput("referral.salutation"));
            list.Add(newOutput("patient.priProvNameFL"));
        }
        else if (outInCheck == OutInCheck.In)
        {
        }
        else //none
        if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("misc"));
        }
           
        return list;
    }

    private static List<SheetFieldDef> getPatientForm(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
        }
        else //I can't really think of any for this kind
        if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("Address"));
            list.Add(newInput("Address2"));
            list.Add(newInput("Birthdate"));
            list.Add(newInput("City"));
            list.Add(newInput("Email"));
            list.Add(newInput("FName"));
            list.Add(newInput("HmPhone"));
            list.Add(newInput("ins1CarrierName"));
            list.Add(newInput("ins1CarrierPhone"));
            list.Add(newInput("ins1EmployerName"));
            list.Add(newInput("ins1GroupName"));
            list.Add(newInput("ins1GroupNum"));
            list.Add(newInput("ins1SubscriberID"));
            list.Add(newInput("ins1SubscriberNameF"));
            list.Add(newInput("ins2CarrierName"));
            list.Add(newInput("ins2CarrierPhone"));
            list.Add(newInput("ins2EmployerName"));
            list.Add(newInput("ins2GroupName"));
            list.Add(newInput("ins2GroupNum"));
            list.Add(newInput("ins2SubscriberID"));
            list.Add(newInput("ins2SubscriberNameF"));
            list.Add(newInput("LName"));
            list.Add(newInput("MiddleI"));
            list.Add(newInput("misc"));
            list.Add(newInput("Preferred"));
            list.Add(newInput("referredFrom"));
            list.Add(newInput("SSN"));
            list.Add(newInput("State"));
            list.Add(newInput("WkPhone"));
            list.Add(newInput("WirelessPhone"));
            list.Add(newInput("wirelessCarrier"));
            list.Add(newInput("Zip"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("addressAndHmPhoneIsSameEntireFamily"));
            list.Add(newCheck("Gender"));
            list.Add(newCheck("ins1Relat"));
            list.Add(newCheck("ins2Relat"));
            list.Add(newCheck("misc"));
            list.Add(newCheck("Position"));
            list.Add(newCheck("PreferConfirmMethod"));
            list.Add(newCheck("PreferContactMethod"));
            list.Add(newCheck("PreferRecallMethod"));
            list.Add(newCheck("StudentStatus"));
        }
           
        return list;
    }

    private static List<SheetFieldDef> getRoutingSlip(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("appt.timeDate"));
            list.Add(newOutput("appt.length"));
            list.Add(newOutput("appt.providers"));
            list.Add(newOutput("appt.procedures"));
            list.Add(newOutput("appt.Note"));
            list.Add(newOutput("otherFamilyMembers"));
        }
        else //most fields turned out to work best as static text.
        if (outInCheck == OutInCheck.In)
        {
        }
        else //Not applicable
        if (outInCheck == OutInCheck.Check)
        {
        }
           
        return list;
    }

    //Not applicable
    private static List<SheetFieldDef> getMedicalHistory(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
        }
        else //none
        if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("Birthdate"));
            list.Add(newInput("FName"));
            list.Add(newInput("LName"));
            list.Add(newInput("misc"));
            list.Add(newInput("inputMed1"));
            list.Add(newInput("inputMed2"));
            list.Add(newInput("inputMed3"));
            list.Add(newInput("inputMed4"));
            list.Add(newInput("inputMed5"));
            list.Add(newInput("inputMed6"));
            list.Add(newInput("inputMed7"));
            list.Add(newInput("inputMed8"));
            list.Add(newInput("inputMed9"));
            list.Add(newInput("inputMed10"));
            list.Add(newInput("inputMed11"));
            list.Add(newInput("inputMed12"));
            list.Add(newInput("inputMed13"));
            list.Add(newInput("inputMed14"));
            list.Add(newInput("inputMed15"));
            list.Add(newInput("inputMed16"));
            list.Add(newInput("inputMed17"));
            list.Add(newInput("inputMed18"));
            list.Add(newInput("inputMed19"));
            list.Add(newInput("inputMed20"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("allergy"));
            list.Add(newCheck("problem"));
            list.Add(newCheck("misc"));
            list.Add(newInput("checkMed1"));
            list.Add(newInput("checkMed2"));
            list.Add(newInput("checkMed3"));
            list.Add(newInput("checkMed4"));
            list.Add(newInput("checkMed5"));
            list.Add(newInput("checkMed6"));
            list.Add(newInput("checkMed7"));
            list.Add(newInput("checkMed8"));
            list.Add(newInput("checkMed9"));
            list.Add(newInput("checkMed10"));
            list.Add(newInput("checkMed11"));
            list.Add(newInput("checkMed12"));
            list.Add(newInput("checkMed13"));
            list.Add(newInput("checkMed14"));
            list.Add(newInput("checkMed15"));
            list.Add(newInput("checkMed16"));
            list.Add(newInput("checkMed17"));
            list.Add(newInput("checkMed18"));
            list.Add(newInput("checkMed19"));
            list.Add(newInput("checkMed20"));
        }
           
        return list;
    }

    private static List<SheetFieldDef> getLabSlip(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("lab.Description"));
            list.Add(newOutput("lab.Phone"));
            list.Add(newOutput("lab.Notes"));
            list.Add(newOutput("lab.WirelessPhone"));
            list.Add(newOutput("lab.Address"));
            list.Add(newOutput("lab.CityStZip"));
            list.Add(newOutput("lab.Email"));
            list.Add(newOutput("appt.DateTime"));
            list.Add(newOutput("labcase.DateTimeDue"));
            list.Add(newOutput("labcase.DateTimeCreated"));
            list.Add(newOutput("prov.nameFormal"));
            list.Add(newOutput("prov.stateLicence"));
        }
        else //patient fields already handled with static text: name,age,gender.
        //other fields already handled: dateToday, practice address and phone.
        if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("notes"));
            list.Add(newInput("labcase.Instructions"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("misc"));
        }
           
        return list;
    }

    public static List<SheetFieldDef> getExamSheet(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("patient.priProvNameFL"));
            list.Add(newOutput("sheet.DateTimeSheet"));
        }
        else if (outInCheck == OutInCheck.In)
        {
            list.Add(newInput("Birthdate"));
            list.Add(newInput("FName"));
            list.Add(newInput("LName"));
            list.Add(newInput("MiddleI"));
            list.Add(newInput("misc"));
            list.Add(newInput("Preferred"));
        }
        else if (outInCheck == OutInCheck.Check)
        {
            list.Add(newCheck("Gender"));
            list.Add(newCheck("misc"));
            list.Add(newCheck("Race"));
        }
           
        return list;
    }

    //This is really race/ethnicity combined.
    public static List<SheetFieldDef> getDepositSlip(OutInCheck outInCheck) throws Exception {
        List<SheetFieldDef> list = new List<SheetFieldDef>();
        if (outInCheck == OutInCheck.Out)
        {
            list.Add(newOutput("deposit.BankAccountInfo"));
            list.Add(newOutput("deposit.DateDeposit"));
            list.Add(newOutput("depositList"));
            list.Add(newOutput("depositTotal"));
            list.Add(newOutput("depositItemCount"));
            list.Add(newOutput("depositItem01"));
            list.Add(newOutput("depositItem02"));
            list.Add(newOutput("depositItem03"));
            list.Add(newOutput("depositItem04"));
            list.Add(newOutput("depositItem05"));
            list.Add(newOutput("depositItem06"));
            list.Add(newOutput("depositItem07"));
            list.Add(newOutput("depositItem08"));
            list.Add(newOutput("depositItem09"));
            list.Add(newOutput("depositItem10"));
            list.Add(newOutput("depositItem11"));
            list.Add(newOutput("depositItem12"));
            list.Add(newOutput("depositItem13"));
            list.Add(newOutput("depositItem14"));
            list.Add(newOutput("depositItem15"));
            list.Add(newOutput("depositItem16"));
            list.Add(newOutput("depositItem17"));
            list.Add(newOutput("depositItem18"));
        }
        else if (outInCheck == OutInCheck.In)
        {
        }
        else if (outInCheck == OutInCheck.Check)
        {
        }
           
        return list;
    }

}


