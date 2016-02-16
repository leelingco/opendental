//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Deposit;
import OpenDentBusiness.Deposits;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.Documents;
import OpenDentBusiness.Employers;
import OpenDentBusiness.Family;
import OpenDentBusiness.FrequencyType;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Laboratories;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionProcs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.PlannedAppts;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.Sites;
import OpenDentBusiness.TreatPlan;
import OpenDentBusiness.TreatPlans;

public class SheetFiller   
{
    /**
    * Gets the data from the database and fills the fields.
    */
    public static void fillFields(Sheet sheet) throws Exception {
        for (Object __dummyForeachVar0 : sheet.Parameters)
        {
            SheetParameter param = (SheetParameter)__dummyForeachVar0;
            if (param.IsRequired && param.ParamValue == null)
            {
                throw new ApplicationException(Lan.g("Sheet","Parameter not specified for sheet: ") + param.ParamName);
            }
             
        }
        Patient pat = null;
        Referral refer = null;
        Deposit deposit = null;
        switch(sheet.SheetType)
        {
            case LabelPatient: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForLabelPatient(sheet,pat);
                break;
            case LabelCarrier: 
                Carrier carrier = Carriers.getCarrier((Long)getParamByName(sheet,"CarrierNum").ParamValue);
                fillFieldsForLabelCarrier(sheet,carrier);
                break;
            case LabelReferral: 
                refer = Referrals.getReferral((Long)getParamByName(sheet,"ReferralNum").ParamValue);
                fillFieldsForLabelReferral(sheet,refer);
                break;
            case ReferralSlip: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                refer = Referrals.getReferral((Long)getParamByName(sheet,"ReferralNum").ParamValue);
                fillFieldsForReferralSlip(sheet,pat,refer);
                break;
            case LabelAppointment: 
                Appointment appt = Appointments.getOneApt((Long)getParamByName(sheet,"AptNum").ParamValue);
                pat = Patients.getPat(appt.PatNum);
                fillFieldsForLabelAppointment(sheet,appt,pat);
                break;
            case Rx: 
                RxPat rx = RxPats.getRx((Long)getParamByName(sheet,"RxNum").ParamValue);
                pat = Patients.getPat(rx.PatNum);
                Provider prov = Providers.getProv(rx.ProvNum);
                fillFieldsForRx(sheet,rx,pat,prov);
                break;
            case Consent: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForConsent(sheet,pat);
                break;
            case PatientLetter: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForPatientLetter(sheet,pat);
                break;
            case ReferralLetter: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                refer = Referrals.getReferral((Long)getParamByName(sheet,"ReferralNum").ParamValue);
                fillFieldsForReferralLetter(sheet,pat,refer);
                break;
            case PatientForm: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForPatientForm(sheet,pat);
                break;
            case RoutingSlip: 
                Appointment apt = Appointments.getOneApt((Long)getParamByName(sheet,"AptNum").ParamValue);
                pat = Patients.getPat(apt.PatNum);
                fillFieldsForRoutingSlip(sheet,pat,apt);
                break;
            case MedicalHistory: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForMedicalHistory(sheet,pat);
                break;
            case LabSlip: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                LabCase lab = LabCases.getOne((Long)getParamByName(sheet,"LabCaseNum").ParamValue);
                fillFieldsForLabCase(sheet,pat,lab);
                break;
            case ExamSheet: 
                pat = Patients.getPat((Long)getParamByName(sheet,"PatNum").ParamValue);
                fillFieldsForExamSheet(sheet,pat);
                break;
            case DepositSlip: 
                deposit = Deposits.getOne((Long)getParamByName(sheet,"DepositNum").ParamValue);
                fillFieldsForDepositSlip(sheet,deposit);
                break;
        
        }
        fillFieldsInStaticText(sheet,pat);
        fillPatientImages(sheet,pat);
    }

    private static SheetParameter getParamByName(Sheet sheet, String paramName) throws Exception {
        for (Object __dummyForeachVar1 : sheet.Parameters)
        {
            SheetParameter param = (SheetParameter)__dummyForeachVar1;
            if (StringSupport.equals(param.ParamName, paramName))
            {
                return param;
            }
             
        }
        return null;
    }

    /**
    * Pat can be null sometimes.  For example, in deposit slip.
    */
    private static void fillFieldsInStaticText(Sheet sheet, Patient pat) throws Exception {
        String fldval = "";
        String address = "";
        String apptsAllFuture = "";
        String birthdate = "";
        String carrierName = "";
        String carrierAddress = "";
        String carrierCityStZip = "";
        String subscriberId = "";
        String subscriberNameFL = "";
        String insAnnualMax = "";
        String insDeductible = "";
        String insDeductibleUsed = "";
        String insPending = "";
        String insPercentages = "";
        String insPlanGroupNumber = "";
        String insPlanGroupName = "";
        String insPlanNote = "";
        String insSubNote = "";
        String insRemaining = "";
        String insUsed = "";
        String carrier2Name = "";
        String subscriber2NameFL = "";
        String ins2AnnualMax = "";
        String ins2Deductible = "";
        String ins2DeductibleUsed = "";
        String ins2Pending = "";
        String ins2Percentages = "";
        String ins2Remaining = "";
        String ins2Used = "";
        String clinicDescription = "";
        String clinicAddress = "";
        String clinicCityStZip = "";
        String phone = "";
        String clinicPhone = "";
        String plannedAppointmentInfo = "";
        String dateFirstVisit = "";
        String treatmentPlanProcs = "";
        String dateOfLastSavedTP = "";
        String tpResponsPartyAddress = "";
        String tpResponsPartyCityStZip = "";
        String tpResponsPartyNameFL = "";
        String dateRecallDue = "";
        String recallInterval = "";
        String nextSchedApptDateT = "";
        String dateTimeLastAppt = "";
        String nextSchedApptsFam = "";
        String serviceNote = "";
        String insFreqBW = "";
        String insFreqExams = "";
        String insFreqPanoFMX = "";
        String insType = "";
        //(ppo, etc)
        String referredFrom = "";
        //(just one)
        String referredTo = "";
        //(typically Drs. could be multiline. Include date)
        String dateLastBW = "";
        String dateLastExam = "";
        String dateLastPanoFMX = "";
        String dateLastProphy = "";
        String genderHeShe = "";
        String genderheshe = "";
        String genderHimHer = "";
        String genderhimher = "";
        String genderHimselfHerself = "";
        String genderhimselfherself = "";
        String genderHisHer = "";
        String genderhisher = "";
        String genderHisHers = "";
        String genderhishers = "";
        String guarantorNameF = "";
        String guarantorNameFL = "";
        String guarantorNameL = "";
        String guarantorNamePref = "";
        String guarantorNameLF = "";
        Family fam = null;
        Provider priProv = null;
        if (pat != null)
        {
            switch(pat.Gender)
            {
                case Male: 
                    genderHeShe = "He";
                    genderheshe = "he";
                    genderHimHer = "Him";
                    genderhimher = "him";
                    genderHimselfHerself = "Himself";
                    genderhimselfherself = "Herself";
                    genderHisHer = "His";
                    genderhisher = "his";
                    genderHisHers = "His";
                    genderhishers = "his";
                    break;
                case Female: 
                    genderHeShe = "She";
                    genderheshe = "she";
                    genderHimHer = "Her";
                    genderhimher = "her";
                    genderHimselfHerself = "Herself";
                    genderhimselfherself = "herself";
                    genderHisHer = "Her";
                    genderhisher = "her";
                    genderHisHers = "Hers";
                    genderhishers = "hers";
                    break;
                case Unknown: 
                    genderHeShe = "The patient";
                    genderheshe = "the patient";
                    genderHimHer = "The patient";
                    genderhimher = "the patient";
                    genderHimselfHerself = "The patient";
                    genderhimselfherself = "the patient";
                    genderHisHer = "The patient's";
                    genderhisher = "the patient's";
                    genderHisHers = "The patient's";
                    genderhishers = "the patient's";
                    break;
            
            }
            Patient guar = Patients.getPat(pat.Guarantor);
            if (guar != null)
            {
                guarantorNameF = guar.FName;
                guarantorNameFL = guar.getNameFL();
                guarantorNameL = guar.LName;
                guarantorNameLF = guar.getNameLF();
                guarantorNamePref = guar.Preferred;
            }
             
            address = pat.Address;
            if (!StringSupport.equals(pat.Address2, ""))
            {
                address += ", " + pat.Address2;
            }
             
            birthdate = pat.Birthdate.ToShortDateString();
            if (pat.Birthdate.Year < 1880)
            {
                birthdate = "";
            }
             
            dateFirstVisit = pat.DateFirstVisit.ToShortDateString();
            if (pat.DateFirstVisit.Year < 1880)
            {
                dateFirstVisit = "";
            }
             
            fam = Patients.getFamily(pat.PatNum);
            //todo some day: move this section down to TP section
            List<Procedure> procsList = null;
            //there is another variable that does the same thing. Carefully combine them.
            if (Sheets.containsStaticField(sheet,"treatmentPlanProcs") || Sheets.containsStaticField(sheet,"plannedAppointmentInfo"))
            {
                procsList = Procedures.refresh(pat.PatNum);
                if (Sheets.containsStaticField(sheet,"treatmentPlanProcs"))
                {
                    Procedure[] procListTP = Procedures.GetListTP(procsList);
                    for (int i = 0;i < procListTP.Length;i++)
                    {
                        //sorted by priority, then toothnum
                        if (procListTP[i].ProcStatus != OpenDentBusiness.ProcStat.TP)
                        {
                            continue;
                        }
                         
                        if (!StringSupport.equals(treatmentPlanProcs, ""))
                        {
                            treatmentPlanProcs += "\r\n";
                        }
                         
                        treatmentPlanProcs += ProcedureCodes.GetStringProcCode(procListTP[i].CodeNum) + ", " + Procedures.GetDescription(procListTP[i]) + ", " + procListTP[i].ProcFee.ToString("c");
                    }
                }
                 
            }
             
            serviceNote = PatientNotes.refresh(pat.PatNum,pat.Guarantor).Service;
            List<RefAttach> RefAttachList = RefAttaches.Refresh(pat.PatNum);
            Referral tempReferralFrom = Referrals.getReferralForPat(pat.PatNum);
            if (Referrals.getReferralForPat(pat.PatNum) != null)
            {
                if (tempReferralFrom.IsDoctor)
                {
                    referredFrom += tempReferralFrom.FName + " " + tempReferralFrom.LName + " " + tempReferralFrom.Title + " : " + tempReferralFrom.Specialty.ToString();
                }
                else
                {
                    referredFrom += tempReferralFrom.FName + " " + tempReferralFrom.LName;
                } 
            }
             
            for (int i = 0;i < RefAttachList.Count;i++)
            {
                if (RefAttachList[i].IsFrom)
                {
                    continue;
                }
                 
                Referral tempRef = Referrals.GetReferral(RefAttachList[i].ReferralNum);
                if (tempRef.IsDoctor)
                {
                    referredTo += tempRef.FName + " " + tempRef.LName + " " + tempRef.Title + " : " + tempRef.Specialty.ToString() + " " + RefAttachList[i].RefDate.ToShortDateString() + "\r\n";
                }
                else
                {
                    referredTo += tempRef.FName + " " + tempRef.LName + " " + RefAttachList[i].RefDate.ToShortDateString() + "\r\n";
                } 
            }
            //Insurance-------------------------------------------------------------------------------------------------------------------
            List<InsSub> subList = InsSubs.refreshForFam(fam);
            List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
            List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
            long subNum = PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, patPlanList, planList, subList));
            long patPlanNum = PatPlans.GetPatPlanNum(subNum, patPlanList);
            InsSub sub = InsSubs.GetSub(subNum, subList);
            InsPlan plan = null;
            if (sub != null)
            {
                plan = InsPlans.GetPlan(sub.PlanNum, planList);
                insSubNote = sub.SubscNote;
            }
             
            Carrier carrier = null;
            List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
            List<ClaimProcHist> histList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlanList, planList, DateTimeOD.getToday(), subList);
            double doubAnnualMax = new double();
            double doubDeductible = new double();
            double doubDeductibleUsed = new double();
            double doubPending = new double();
            double doubRemain = new double();
            double doubUsed = new double();
            if (plan != null)
            {
                insPlanGroupName = plan.GroupName;
                insPlanGroupNumber = plan.GroupNum;
                insPlanNote = plan.PlanNote;
                carrier = Carriers.getCarrier(plan.CarrierNum);
                carrierName = carrier.CarrierName;
                carrierAddress = carrier.Address;
                if (!StringSupport.equals(carrier.Address2, ""))
                {
                    carrierAddress += ", " + carrier.Address2;
                }
                 
                carrierCityStZip = carrier.City + ", " + carrier.State + "  " + carrier.Zip;
                subscriberId = sub.SubscriberID;
                subscriberNameFL = Patients.getLim(sub.Subscriber).getNameFL();
                doubAnnualMax = Benefits.GetAnnualMaxDisplay(benefitList, plan.PlanNum, patPlanNum, false);
                doubRemain = -1;
                if (doubAnnualMax != -1)
                {
                    insAnnualMax = doubAnnualMax.ToString("c");
                    doubRemain = doubAnnualMax;
                }
                 
                doubDeductible = Benefits.GetDeductGeneralDisplay(benefitList, plan.PlanNum, patPlanNum, BenefitCoverageLevel.Individual);
                if (doubDeductible != -1)
                {
                    insDeductible = doubDeductible.ToString("c");
                }
                 
                doubDeductibleUsed = InsPlans.GetDedUsedDisplay(histList, DateTime.Today, plan.PlanNum, patPlanNum, -1, planList, BenefitCoverageLevel.Individual, pat.PatNum);
                if (doubDeductibleUsed != -1)
                {
                    insDeductibleUsed = doubDeductibleUsed.ToString("c");
                }
                 
                doubPending = InsPlans.GetPendingDisplay(histList, DateTime.Today, plan, patPlanNum, -1, pat.PatNum, subNum, benefitList);
                if (doubPending != -1)
                {
                    insPending = doubPending.ToString("c");
                    if (doubRemain != -1)
                    {
                        doubRemain -= doubPending;
                    }
                     
                }
                 
                doubUsed = InsPlans.GetInsUsedDisplay(histList, DateTime.Today, plan.PlanNum, patPlanNum, -1, planList, benefitList, pat.PatNum, subNum);
                if (doubUsed != -1)
                {
                    insUsed = doubUsed.ToString("c");
                    if (doubRemain != -1)
                    {
                        doubRemain -= doubUsed;
                    }
                     
                }
                 
                if (doubRemain != -1)
                {
                    insRemaining = doubRemain.ToString("c");
                }
                 
                for (int j = 0;j < benefitList.Count;j++)
                {
                    if (benefitList[j].PlanNum != plan.PlanNum)
                    {
                        continue;
                    }
                     
                    if (benefitList[j].BenefitType != InsBenefitType.CoInsurance)
                    {
                        continue;
                    }
                     
                    if (!StringSupport.equals(insPercentages, ""))
                    {
                        insPercentages += ",  ";
                    }
                     
                    insPercentages += CovCats.GetDesc(benefitList[j].CovCatNum) + " " + benefitList[j].Percent.ToString() + "%";
                }
                insFreqBW = Benefits.GetFrequencyDisplay(FrequencyType.BW, benefitList);
                insFreqExams = Benefits.GetFrequencyDisplay(FrequencyType.Exam, benefitList);
                insFreqPanoFMX = Benefits.GetFrequencyDisplay(FrequencyType.PanoFMX, benefitList);
                System.String __dummyScrutVar2 = plan.PlanType;
                //(ppo, etc)
                if (__dummyScrutVar2.equals("p"))
                {
                    insType = "PPO Percentage";
                }
                else if (__dummyScrutVar2.equals("f"))
                {
                    insType = "Medicaid or Flat Copay";
                }
                else if (__dummyScrutVar2.equals("c"))
                {
                    insType = "Capitation";
                }
                else if (__dummyScrutVar2.equals(""))
                {
                    insType = "Category Percentage";
                }
                    
            }
             
            subNum = PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, planList, subList));
            patPlanNum = PatPlans.GetPatPlanNum(subNum, patPlanList);
            sub = InsSubs.GetSub(subNum, subList);
            if (sub != null)
            {
                plan = InsPlans.GetPlan(sub.PlanNum, planList);
            }
             
            if (plan != null)
            {
                carrier = Carriers.getCarrier(plan.CarrierNum);
                carrier2Name = carrier.CarrierName;
                //carrierAddress=carrier.Address;
                //if(carrier.Address2!="") {
                //	carrierAddress+=", "+carrier.Address2;
                //}
                //carrierCityStZip=carrier.City+", "+carrier.State+"  "+carrier.Zip;
                //subscriberId=plan.SubscriberID;
                subscriber2NameFL = Patients.getLim(sub.Subscriber).getNameFL();
                doubAnnualMax = Benefits.GetAnnualMaxDisplay(benefitList, plan.PlanNum, patPlanNum, false);
                doubRemain = -1;
                if (doubAnnualMax != -1)
                {
                    ins2AnnualMax = doubAnnualMax.ToString("c");
                    doubRemain = doubAnnualMax;
                }
                 
                doubDeductible = Benefits.GetDeductGeneralDisplay(benefitList, plan.PlanNum, patPlanNum, BenefitCoverageLevel.Individual);
                if (doubDeductible != -1)
                {
                    ins2Deductible = doubDeductible.ToString("c");
                }
                 
                doubDeductibleUsed = InsPlans.GetDedUsedDisplay(histList, DateTime.Today, plan.PlanNum, patPlanNum, -1, planList, BenefitCoverageLevel.Individual, pat.PatNum);
                if (doubDeductibleUsed != -1)
                {
                    ins2DeductibleUsed = doubDeductibleUsed.ToString("c");
                }
                 
                doubPending = InsPlans.GetPendingDisplay(histList, DateTime.Today, plan, patPlanNum, -1, pat.PatNum, subNum, benefitList);
                if (doubPending != -1)
                {
                    ins2Pending = doubPending.ToString("c");
                    if (doubRemain != -1)
                    {
                        doubRemain -= doubPending;
                    }
                     
                }
                 
                doubUsed = InsPlans.GetInsUsedDisplay(histList, DateTime.Today, plan.PlanNum, patPlanNum, -1, planList, benefitList, pat.PatNum, subNum);
                if (doubUsed != -1)
                {
                    ins2Used = doubUsed.ToString("c");
                    if (doubRemain != -1)
                    {
                        doubRemain -= doubUsed;
                    }
                     
                }
                 
                if (doubRemain != -1)
                {
                    ins2Remaining = doubRemain.ToString("c");
                }
                 
                for (int j = 0;j < benefitList.Count;j++)
                {
                    if (benefitList[j].PlanNum != plan.PlanNum)
                    {
                        continue;
                    }
                     
                    if (benefitList[j].BenefitType != InsBenefitType.CoInsurance)
                    {
                        continue;
                    }
                     
                    if (!StringSupport.equals(ins2Percentages, ""))
                    {
                        ins2Percentages += ",  ";
                    }
                     
                    ins2Percentages += CovCats.GetDesc(benefitList[j].CovCatNum) + " " + benefitList[j].Percent.ToString() + "%";
                }
            }
             
            //Treatment plan-----------------------------------------------------------------------------------------------------------
            TreatPlan[] treatPlanList = TreatPlans.refresh(pat.PatNum);
            TreatPlan treatPlan = null;
            if (treatPlanList.Length > 0)
            {
                treatPlan = treatPlanList[treatPlanList.Length - 1].Copy();
                dateOfLastSavedTP = treatPlan.DateTP.ToShortDateString();
                Patient patRespParty = Patients.getPat(treatPlan.ResponsParty);
                if (patRespParty != null)
                {
                    tpResponsPartyAddress = patRespParty.Address;
                    if (!StringSupport.equals(patRespParty.Address2, ""))
                    {
                        tpResponsPartyAddress += ", " + patRespParty.Address2;
                    }
                     
                    tpResponsPartyCityStZip = patRespParty.City + ", " + patRespParty.State + "  " + patRespParty.Zip;
                    tpResponsPartyNameFL = patRespParty.getNameFL();
                }
                 
            }
             
            //Procedure Log-------------------------------------------------------------------------------------------------------------
            List<Procedure> proceduresList = Procedures.refresh(pat.PatNum);
            DateTime dBW = DateTime.MinValue;
            DateTime dExam = DateTime.MinValue;
            DateTime dPanoFMX = DateTime.MinValue;
            DateTime dProphy = DateTime.MinValue;
            for (int i = 0;i < proceduresList.Count;i++)
            {
                Procedure proc = proceduresList[i];
                //cache Proc to speed up process
                if (proc.ProcStatus != OpenDentBusiness.ProcStat.C && proc.ProcStatus != OpenDentBusiness.ProcStat.EC && proc.ProcStatus != OpenDentBusiness.ProcStat.EO)
                {
                    continue;
                }
                 
                //only look at completed or existing procedures
                //intraoral - complete series (including bitewings)
                //bitewing - single film
                //bitewings - two films
                //bitewings - four films
                //vertical bitewings - 7 to 8 films
                //bitewings - three films
                if ((proc.CodeNum == ProcedureCodes.getCodeNum("D0210") || proc.CodeNum == ProcedureCodes.getCodeNum("D0270") || proc.CodeNum == ProcedureCodes.getCodeNum("D0272") || proc.CodeNum == ProcedureCodes.getCodeNum("D0274") || proc.CodeNum == ProcedureCodes.getCodeNum("D0277") || proc.CodeNum == ProcedureCodes.getCodeNum("D0273")) && proc.ProcDate > dBW)
                {
                    //newest
                    dBW = proc.ProcDate;
                    dateLastBW = proc.ProcDate.ToShortDateString();
                }
                 
                //periodic oral evaluation - established patient
                //limited oral evaluation - problem focused
                //comprehensive oral evaluation - new or established patient
                //detailed and extensive oral evaluation - problem focused, by report
                if ((proc.CodeNum == ProcedureCodes.getCodeNum("D0120") || proc.CodeNum == ProcedureCodes.getCodeNum("D0140") || proc.CodeNum == ProcedureCodes.getCodeNum("D0150") || proc.CodeNum == ProcedureCodes.getCodeNum("D0160")) && proc.ProcDate > dExam)
                {
                    //newest
                    dExam = proc.ProcDate;
                    dateLastExam = proc.ProcDate.ToShortDateString();
                }
                 
                //intraoral - complete series (including bitewings)
                //panoramic film
                if ((proc.CodeNum == ProcedureCodes.getCodeNum("D0210") || proc.CodeNum == ProcedureCodes.getCodeNum("D0330")) && proc.ProcDate > dPanoFMX)
                {
                    //newest
                    dPanoFMX = proc.ProcDate;
                    dateLastPanoFMX = proc.ProcDate.ToShortDateString();
                }
                 
                //prophylaxis - adult
                //prophylaxis - child
                //Topical Fluoride Including Prophy-Child
                //Topical Fluoride Including Prophy-Adult
                if ((proc.CodeNum == ProcedureCodes.getCodeNum("D1110") || proc.CodeNum == ProcedureCodes.getCodeNum("D1120") || proc.CodeNum == ProcedureCodes.getCodeNum("D1201") || proc.CodeNum == ProcedureCodes.getCodeNum("D1205")) && proc.ProcDate > dProphy)
                {
                    //newest
                    dProphy = proc.ProcDate;
                    dateLastProphy = proc.ProcDate.ToShortDateString();
                        ;
                }
                 
            }
            //Recall--------------------------------------------------------------------------------------------------------------------
            Recall recall = Recalls.getRecallProphyOrPerio(pat.PatNum);
            if (recall != null && !recall.IsDisabled)
            {
                if (recall.DateDue.Year > 1880)
                {
                    dateRecallDue = recall.DateDue.ToShortDateString();
                }
                 
                recallInterval = recall.RecallInterval.toString();
            }
             
            //Appointments--------------------------------------------------------------------------------------------------------------
            List<Appointment> apptList = Appointments.getListForPat(pat.PatNum);
            List<Appointment> apptFutureList = Appointments.getFutureSchedApts(pat.PatNum);
            for (int i = 0;i < apptList.Count;i++)
            {
                if (apptList[i].AptStatus != ApptStatus.Scheduled && apptList[i].AptStatus != ApptStatus.Complete && apptList[i].AptStatus != ApptStatus.None && apptList[i].AptStatus != ApptStatus.ASAP)
                {
                    continue;
                }
                 
                if (apptList[i].AptDateTime < DateTime.Now)
                {
                    //this will happen repeatedly up until the most recent.
                    dateTimeLastAppt = apptList[i].AptDateTime.ToShortDateString() + "  " + apptList[i].AptDateTime.ToShortTimeString();
                }
                else
                {
                    //after now
                    if (StringSupport.equals(nextSchedApptDateT, ""))
                    {
                        //only the first one found
                        nextSchedApptDateT = apptList[i].AptDateTime.ToShortDateString() + "  " + apptList[i].AptDateTime.ToShortTimeString();
                        break;
                    }
                     
                } 
            }
            for (int i = 0;i < apptFutureList.Count;i++)
            {
                //we're done with the list now.
                //cannot be combined in loop above because of the break in the loop.
                apptsAllFuture += apptFutureList[i].AptDateTime.ToShortDateString() + " " + apptFutureList[i].AptDateTime.ToShortTimeString() + " : " + apptFutureList[i].ProcDescript + "\r\n";
            }
            for (int i = 0;i < fam.ListPats.Length;i++)
            {
                List<Appointment> futAptsList = Appointments.GetFutureSchedApts(fam.ListPats[i].PatNum);
                if (futAptsList.Count > 0)
                {
                    //just gets one future appt for each person
                    nextSchedApptsFam += fam.ListPats[i].FName + ": " + futAptsList[0].AptDateTime.ToShortDateString() + " " + futAptsList[0].AptDateTime.ToShortTimeString() + " : " + futAptsList[0].ProcDescript + "\r\n";
                }
                 
            }
            if (Sheets.containsStaticField(sheet,"plannedAppointmentInfo"))
            {
                PlannedAppt plannedAppt = PlannedAppts.getOneOrderedByItemOrder(pat.PatNum);
                for (int i = 0;i < apptList.Count;i++)
                {
                    if (plannedAppt != null && apptList[i].AptNum == plannedAppt.AptNum)
                    {
                        plannedAppointmentInfo = "Procedures: ";
                        plannedAppointmentInfo += apptList[i].ProcDescript + "\r\n";
                        int minutesTotal = apptList[i].Pattern.Length * 5;
                        int hours = minutesTotal / 60;
                        //automatically rounds down
                        int minutes = minutesTotal - hours * 60;
                        plannedAppointmentInfo += "Appt Length: ";
                        if (hours > 0)
                        {
                            plannedAppointmentInfo += hours.ToString() + " hours, ";
                        }
                         
                        plannedAppointmentInfo += minutes.ToString() + " min\r\n";
                        if (Programs.getUsingOrion())
                        {
                            DateTime newDateSched = new DateTime();
                            for (int p = 0;p < procsList.Count;p++)
                            {
                                if (procsList[p].PlannedAptNum == apptList[i].AptNum)
                                {
                                    OrionProc op = OrionProcs.GetOneByProcNum(procsList[p].ProcNum);
                                    if (op != null && op.DateScheduleBy.Year > 1880)
                                    {
                                        if (newDateSched.Year < 1880)
                                        {
                                            newDateSched = op.DateScheduleBy;
                                        }
                                        else
                                        {
                                            if (op.DateScheduleBy < newDateSched)
                                            {
                                                newDateSched = op.DateScheduleBy;
                                            }
                                             
                                        } 
                                    }
                                     
                                }
                                 
                            }
                            if (newDateSched.Year > 1880)
                            {
                                plannedAppointmentInfo += "Schedule by: " + newDateSched.ToShortDateString();
                            }
                            else
                            {
                                plannedAppointmentInfo += "No schedule by date.";
                            } 
                        }
                         
                    }
                     
                }
            }
             
            priProv = Providers.getProv(Patients.getProvNum(pat));
            //guaranteed to work
            //Clinic-------------------------------------------------------------------------------------------------------------
            Clinic clinic = Clinics.getClinic(pat.ClinicNum);
            if (clinic == null)
            {
                clinicDescription = PrefC.getString(PrefName.PracticeTitle);
                clinicAddress = PrefC.getString(PrefName.PracticeAddress);
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                {
                    clinicAddress += ", " + PrefC.getString(PrefName.PracticeAddress2);
                }
                 
                clinicCityStZip = PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip);
                phone = PrefC.getString(PrefName.PracticePhone);
            }
            else
            {
                clinicDescription = clinic.Description;
                clinicAddress = clinic.Address;
                if (!StringSupport.equals(clinic.Address2, ""))
                {
                    clinicAddress += ", " + clinic.Address2;
                }
                 
                clinicCityStZip = clinic.City + ", " + clinic.State + "  " + clinic.Zip;
                phone = clinic.Phone;
            } 
            if (phone.Length == 10 && StringSupport.equals(System.Globalization.CultureInfo.CurrentCulture.Name, "en-US"))
            {
                clinicPhone = "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
            }
            else
            {
                clinicPhone = phone;
            } 
        }
         
        for (Object __dummyForeachVar2 : sheet.SheetFields)
        {
            //End of if(pat!=null)
            //Fill fields---------------------------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar2;
            if (field.FieldType != SheetFieldType.StaticText)
            {
                continue;
            }
             
            fldval = field.FieldValue;
            if (pat != null)
            {
                fldval = fldval.Replace("[address]", address);
                fldval = fldval.Replace("[apptsAllFuture]", apptsAllFuture.TrimEnd());
                fldval = fldval.Replace("[age]", Patients.ageToString(pat.getAge()));
                fldval = fldval.Replace("[balTotal]", fam.ListPats[0].BalTotal.ToString("c"));
                fldval = fldval.Replace("[bal_0_30]", fam.ListPats[0].Bal_0_30.ToString("c"));
                fldval = fldval.Replace("[bal_31_60]", fam.ListPats[0].Bal_31_60.ToString("c"));
                fldval = fldval.Replace("[bal_61_90]", fam.ListPats[0].Bal_61_90.ToString("c"));
                fldval = fldval.Replace("[balOver90]", fam.ListPats[0].BalOver90.ToString("c"));
                fldval = fldval.Replace("[balInsEst]", fam.ListPats[0].InsEst.ToString("c"));
                fldval = fldval.Replace("[balTotalMinusInsEst]", (fam.ListPats[0].BalTotal - fam.ListPats[0].InsEst).ToString("c"));
                fldval = fldval.Replace("[BillingType]", DefC.getName(DefCat.BillingTypes,pat.BillingType));
                fldval = fldval.Replace("[Birthdate]", birthdate);
                fldval = fldval.Replace("[carrierName]", carrierName);
                fldval = fldval.Replace("[carrier2Name]", carrier2Name);
                fldval = fldval.Replace("[ChartNumber]", pat.ChartNumber);
                fldval = fldval.Replace("[carrierAddress]", carrierAddress);
                fldval = fldval.Replace("[carrierCityStZip]", carrierCityStZip);
                fldval = fldval.Replace("[cityStateZip]", pat.City + ", " + pat.State + "  " + pat.Zip);
                fldval = fldval.Replace("[clinicDescription]", clinicDescription);
                fldval = fldval.Replace("[clinicAddress]", clinicAddress);
                fldval = fldval.Replace("[clinicCityStZip]", clinicCityStZip);
                fldval = fldval.Replace("[clinicPhone]", clinicPhone);
                fldval = fldval.Replace("[DateFirstVisit]", dateFirstVisit);
                fldval = fldval.Replace("[dateLastBW]", dateLastBW);
                fldval = fldval.Replace("[dateLastExam]", dateLastExam);
                fldval = fldval.Replace("[dateLastPanoFMX]", dateLastPanoFMX);
                fldval = fldval.Replace("[dateLastProphy]", dateLastProphy);
                fldval = fldval.Replace("[dateOfLastSavedTP]", dateOfLastSavedTP);
                fldval = fldval.Replace("[dateRecallDue]", dateRecallDue);
                fldval = fldval.Replace("[dateTimeLastAppt]", dateTimeLastAppt);
                fldval = fldval.Replace("[Email]", pat.Email);
                fldval = fldval.Replace("[famFinUrgNote]", fam.ListPats[0].FamFinUrgNote);
                fldval = fldval.Replace("[guarantorNameF]", guarantorNameF);
                fldval = fldval.Replace("[guarantorNameFL]", guarantorNameFL);
                fldval = fldval.Replace("[guarantorNameL]", guarantorNameL);
                fldval = fldval.Replace("[guarantorNamePref]", guarantorNamePref);
                fldval = fldval.Replace("[guarantorNameLF]", guarantorNameLF);
                fldval = fldval.Replace("[gender]", Lan.g("enumPatientGender", pat.Gender.ToString()));
                fldval = fldval.Replace("[genderHeShe]", genderHeShe);
                fldval = fldval.Replace("[genderheshe]", genderheshe);
                fldval = fldval.Replace("[genderHimHer]", genderHimHer);
                fldval = fldval.Replace("[genderhimher]", genderhimher);
                fldval = fldval.Replace("[genderHimselfHerself]", genderHimselfHerself);
                fldval = fldval.Replace("[genderhimselfherself]", genderhimselfherself);
                fldval = fldval.Replace("[genderHisHer]", genderHisHer);
                fldval = fldval.Replace("[genderhisher]", genderhisher);
                fldval = fldval.Replace("[genderHisHers]", genderHisHers);
                fldval = fldval.Replace("[genderhishers]", genderhishers);
                fldval = fldval.Replace("[guarantorNameFL]", fam.ListPats[0].GetNameFL());
                fldval = fldval.Replace("[HmPhone]", stripPhoneBeyondSpace(pat.HmPhone));
                fldval = fldval.Replace("[insAnnualMax]", insAnnualMax);
                fldval = fldval.Replace("[insDeductible]", insDeductible);
                fldval = fldval.Replace("[insDeductibleUsed]", insDeductibleUsed);
                fldval = fldval.Replace("[insFreqBW]", insFreqBW.TrimEnd());
                fldval = fldval.Replace("[insFreqExams]", insFreqExams.TrimEnd());
                fldval = fldval.Replace("[insFreqPanoFMX]", insFreqPanoFMX.TrimEnd());
                fldval = fldval.Replace("[insPending]", insPending);
                fldval = fldval.Replace("[insPercentages]", insPercentages);
                fldval = fldval.Replace("[insPlanGroupNumber]", insPlanGroupNumber);
                fldval = fldval.Replace("[insPlanGroupName]", insPlanGroupName);
                fldval = fldval.Replace("[insPlanNote]", insPlanNote);
                fldval = fldval.Replace("[insType]", insType);
                fldval = fldval.Replace("[insSubNote]", insSubNote);
                fldval = fldval.Replace("[insRemaining]", insRemaining);
                fldval = fldval.Replace("[insUsed]", insUsed);
                fldval = fldval.Replace("[ins2AnnualMax]", ins2AnnualMax);
                fldval = fldval.Replace("[ins2Deductible]", ins2Deductible);
                fldval = fldval.Replace("[ins2DeductibleUsed]", ins2DeductibleUsed);
                fldval = fldval.Replace("[ins2Pending]", ins2Pending);
                fldval = fldval.Replace("[ins2Percentages]", ins2Percentages);
                fldval = fldval.Replace("[ins2Remaining]", ins2Remaining);
                fldval = fldval.Replace("[ins2Used]", ins2Used);
                fldval = fldval.Replace("[MedUrgNote]", pat.MedUrgNote);
                fldval = fldval.Replace("[nameF]", pat.FName);
                fldval = fldval.Replace("[nameFL]", pat.getNameFL());
                fldval = fldval.Replace("[nameFLFormal]", pat.getNameFLFormal());
                fldval = fldval.Replace("[nameL]", pat.LName);
                fldval = fldval.Replace("[nameLF]", pat.getNameLF());
                fldval = fldval.Replace("[nameMI]", pat.MiddleI);
                fldval = fldval.Replace("[namePref]", pat.Preferred);
                fldval = fldval.Replace("[nextSchedApptDateT]", nextSchedApptDateT);
                fldval = fldval.Replace("[nextSchedApptsFam]", nextSchedApptsFam.TrimEnd());
                fldval = fldval.Replace("[PatNum]", pat.PatNum.ToString());
                fldval = fldval.Replace("[plannedAppointmentInfo]", plannedAppointmentInfo);
                fldval = fldval.Replace("[priProvNameFormal]", priProv.getFormalName());
                fldval = fldval.Replace("[recallInterval]", recallInterval);
                fldval = fldval.Replace("[referredFrom]", referredFrom);
                fldval = fldval.Replace("[referredTo]", referredTo.TrimEnd());
                fldval = fldval.Replace("[salutation]", pat.getSalutation());
                fldval = fldval.Replace("[serviceNote]", serviceNote);
                fldval = fldval.Replace("[siteDescription]", Sites.getDescription(pat.SiteNum));
                fldval = fldval.Replace("[SSN]", pat.SSN);
                fldval = fldval.Replace("[subscriberID]", subscriberId);
                fldval = fldval.Replace("[subscriberNameFL]", subscriberNameFL);
                fldval = fldval.Replace("[subscriber2NameFL]", subscriber2NameFL);
                fldval = fldval.Replace("[timeNow]", DateTime.Now.ToShortTimeString());
                fldval = fldval.Replace("[tpResponsPartyAddress]", tpResponsPartyAddress);
                fldval = fldval.Replace("[tpResponsPartyCityStZip]", tpResponsPartyCityStZip);
                fldval = fldval.Replace("[tpResponsPartyNameFL]", tpResponsPartyNameFL);
                fldval = fldval.Replace("[treatmentPlanProcs]", treatmentPlanProcs);
                fldval = fldval.Replace("[WirelessPhone]", stripPhoneBeyondSpace(pat.WirelessPhone));
                fldval = fldval.Replace("[WkPhone]", stripPhoneBeyondSpace(pat.WkPhone));
            }
             
            fldval = fldval.Replace("[dateToday]", DateTime.Today.ToShortDateString());
            fldval = fldval.Replace("[practiceTitle]", PrefC.getString(PrefName.PracticeTitle));
            field.FieldValue = fldval;
        }
        //Fill Exam Sheet Fields----------------------------------------------------------------------------------------------
        //Example: ExamSheet:MyExamSheet;MyField
        if (sheet.SheetType == SheetTypeEnum.PatientLetter && pat != null)
        {
            for (Object __dummyForeachVar3 : sheet.SheetFields)
            {
                SheetField field = (SheetField)__dummyForeachVar3;
                if (field.FieldType != SheetFieldType.StaticText)
                {
                    continue;
                }
                 
                fldval = field.FieldValue;
                String rgx = "\\[ExamSheet\\:([^;]+);([^\\]]+)\\]";
                Match match = Regex.Match(fldval, rgx);
                while (match.Success)
                {
                    String examSheetDescript = match.Result("$1");
                    String fieldName = match.Result("$2");
                    List<SheetField> examFields = SheetFields.getFieldFromExamSheet(pat.PatNum,examSheetDescript,fieldName);
                    //Either a list of fields (if radio button) or single field
                    if (examFields == null || examFields.Count == 0)
                    {
                        match = match.NextMatch();
                        continue;
                    }
                     
                    if (!StringSupport.equals(examFields[0].RadioButtonGroup, ""))
                    {
                        for (int i = 0;i < examFields.Count;i++)
                        {
                            //a user defined 'misc' radio button check box, find the selected item and replace with reportable name
                            if (StringSupport.equals(examFields[i].FieldValue, "X"))
                            {
                                fldval = fldval.Replace(match.Value, examFields[i].ReportableName);
                                break;
                            }
                             
                        }
                    }
                    else //each radio button in the group has a different reportable name.
                    if (!StringSupport.equals(examFields[0].ReportableName, ""))
                    {
                        //not a radio button, so either user defined single misc check boxes or misc input field with reportable name
                        fldval = fldval.Replace(match.Value, examFields[0].FieldValue);
                    }
                    else //checkboxes from exam sheets will show as X or blank on letter.
                    if (!StringSupport.equals(examFields[0].FieldName, "") && !StringSupport.equals(examFields[0].FieldName, "misc"))
                    {
                        //internally defined
                        if (StringSupport.equals(examFields[0].RadioButtonValue, ""))
                        {
                            //internally defined field, not part of a radio button group
                            fldval = fldval.Replace(match.Value, examFields[0].FieldValue);
                        }
                        else
                        {
                            for (int i = 0;i < examFields.Count;i++)
                            {
                                //checkbox or input
                                //internally defined radio button, look for one selected
                                if (StringSupport.equals(examFields[i].FieldValue, "X"))
                                {
                                    fldval = fldval.Replace(match.Value, examFields[i].RadioButtonValue);
                                    break;
                                }
                                 
                            }
                        } 
                    }
                       
                    match = match.NextMatch();
                }
                //while
                field.FieldValue = fldval;
            }
        }
         
    }

    private static String stripPhoneBeyondSpace(String phone) throws Exception {
        if (!phone.Contains(" "))
        {
            return phone;
        }
         
        int idx = phone.IndexOf(" ");
        return phone.Substring(0, idx);
    }

    private static void fillPatientImages(Sheet sheet, Patient pat) throws Exception {
        if (pat == null)
        {
            return ;
        }
         
        OpenDentBusiness.Document[] docList = Documents.getAllWithPat(pat.PatNum);
        long category = new long();
        //string fieldVal;//zoom and pan
        //int x;
        //int y;
        //int w;
        //int h;
        float ratioObject = new float();
        //float ratioImage;
        Image img = new Image();
        for (Object __dummyForeachVar4 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar4;
            if (field.FieldType != SheetFieldType.PatImage)
            {
                continue;
            }
             
            category = PIn.Long(field.FieldName);
            field.FieldName = "0";
            //in case we can't find an image, this will be 0.
            field.FieldValue = "";
            for (int i = docList.Length - 1;i >= 0;i--)
            {
                //go backwards to find the latest date
                if (docList[i].DocCategory != category)
                {
                    continue;
                }
                 
                field.FieldName = docList[i].DocNum.ToString();
                ratioObject = (float)field.Width / (float)field.Height;
                img = Image.FromFile(docList[i].FileName);
                //ratioImage=(float)docList[i].wid  field.Width/(float)field.Height;
                //incomplete
                field.FieldValue = "";
                break;
            }
        }
    }

    private static void fillFieldsForLabelPatient(Sheet sheet, Patient pat) throws Exception {
        for (Object __dummyForeachVar5 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar5;
            System.String __dummyScrutVar3 = field.FieldName;
            if (__dummyScrutVar3.equals("nameFL"))
            {
                field.FieldValue = pat.getNameFLFormal();
            }
            else if (__dummyScrutVar3.equals("nameLF"))
            {
                field.FieldValue = pat.getNameLF();
            }
            else if (__dummyScrutVar3.equals("address"))
            {
                field.FieldValue = pat.Address;
                if (!StringSupport.equals(pat.Address2, ""))
                {
                    field.FieldValue += "\r\n" + pat.Address2;
                }
                 
            }
            else if (__dummyScrutVar3.equals("cityStateZip"))
            {
                field.FieldValue = pat.City + ", " + pat.State + " " + pat.Zip;
            }
            else if (__dummyScrutVar3.equals("ChartNumber"))
            {
                field.FieldValue = pat.ChartNumber;
            }
            else if (__dummyScrutVar3.equals("PatNum"))
            {
                field.FieldValue = pat.PatNum.ToString();
            }
            else if (__dummyScrutVar3.equals("dateTime.Today"))
            {
                field.FieldValue = DateTime.Today.ToShortDateString();
            }
            else if (__dummyScrutVar3.equals("birthdate"))
            {
                //only a temporary workaround:
                field.FieldValue = "BD: " + pat.Birthdate.ToShortDateString();
            }
            else if (__dummyScrutVar3.equals("priProvName"))
            {
                field.FieldValue = Providers.getLongDesc(pat.PriProv);
            }
            else if (__dummyScrutVar3.equals("text"))
            {
                field.FieldValue = getParamByName(sheet,"text").ParamValue.ToString();
            }
                      
        }
    }

    private static void fillFieldsForLabelCarrier(Sheet sheet, Carrier carrier) throws Exception {
        for (Object __dummyForeachVar6 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar6;
            System.String __dummyScrutVar4 = field.FieldName;
            if (__dummyScrutVar4.equals("CarrierName"))
            {
                field.FieldValue = carrier.CarrierName;
            }
            else if (__dummyScrutVar4.equals("address"))
            {
                field.FieldValue = carrier.Address;
                if (!StringSupport.equals(carrier.Address2, ""))
                {
                    field.FieldValue += "\r\n" + carrier.Address2;
                }
                 
            }
            else if (__dummyScrutVar4.equals("cityStateZip"))
            {
                field.FieldValue = carrier.City + ", " + carrier.State + " " + carrier.Zip;
            }
               
        }
    }

    private static void fillFieldsForLabelReferral(Sheet sheet, Referral refer) throws Exception {
        for (Object __dummyForeachVar7 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar7;
            System.String __dummyScrutVar5 = field.FieldName;
            if (__dummyScrutVar5.equals("nameFL"))
            {
                field.FieldValue = Referrals.getNameFL(refer.ReferralNum);
            }
            else if (__dummyScrutVar5.equals("address"))
            {
                field.FieldValue = refer.Address;
                if (!StringSupport.equals(refer.Address2, ""))
                {
                    field.FieldValue += "\r\n" + refer.Address2;
                }
                 
            }
            else if (__dummyScrutVar5.equals("cityStateZip"))
            {
                field.FieldValue = refer.City + ", " + refer.ST + " " + refer.Zip;
            }
               
        }
    }

    private static void fillFieldsForReferralSlip(Sheet sheet, Patient pat, Referral refer) throws Exception {
        for (Object __dummyForeachVar8 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar8;
            System.String __dummyScrutVar6 = field.FieldName;
            if (__dummyScrutVar6.equals("referral.nameFL"))
            {
                field.FieldValue = Referrals.getNameFL(refer.ReferralNum);
            }
            else if (__dummyScrutVar6.equals("referral.address"))
            {
                field.FieldValue = refer.Address;
                if (!StringSupport.equals(refer.Address2, ""))
                {
                    field.FieldValue += "\r\n" + refer.Address2;
                }
                 
            }
            else if (__dummyScrutVar6.equals("referral.cityStateZip"))
            {
                field.FieldValue = refer.City + ", " + refer.ST + " " + refer.Zip;
            }
            else if (__dummyScrutVar6.equals("referral.phone"))
            {
                field.FieldValue = "";
                if (refer.Telephone.Length == 10)
                {
                    field.FieldValue = "(" + refer.Telephone.Substring(0, 3) + ")" + refer.Telephone.Substring(3, 3) + "-" + refer.Telephone.Substring(6);
                }
                 
            }
            else if (__dummyScrutVar6.equals("referral.phone2"))
            {
                field.FieldValue = refer.Phone2;
            }
            else if (__dummyScrutVar6.equals("patient.nameFL"))
            {
                field.FieldValue = pat.getNameFL();
            }
            else if (__dummyScrutVar6.equals("dateTime.Today"))
            {
                field.FieldValue = DateTime.Today.ToShortDateString();
            }
            else if (__dummyScrutVar6.equals("patient.WkPhone"))
            {
                field.FieldValue = pat.WkPhone;
            }
            else if (__dummyScrutVar6.equals("patient.HmPhone"))
            {
                field.FieldValue = pat.HmPhone;
            }
            else if (__dummyScrutVar6.equals("patient.WirelessPhone"))
            {
                field.FieldValue = pat.WirelessPhone;
            }
            else if (__dummyScrutVar6.equals("patient.address"))
            {
                field.FieldValue = pat.Address;
                if (!StringSupport.equals(pat.Address2, ""))
                {
                    field.FieldValue += "\r\n" + pat.Address2;
                }
                 
            }
            else if (__dummyScrutVar6.equals("patient.cityStateZip"))
            {
                field.FieldValue = pat.City + ", " + pat.State + " " + pat.Zip;
            }
            else if (__dummyScrutVar6.equals("patient.provider"))
            {
                field.FieldValue = Providers.getProv(Patients.getProvNum(pat)).getFormalName();
            }
                         
        }
    }

    //case "notes"://an input field
    private static void fillFieldsForLabelAppointment(Sheet sheet, Appointment appt, Patient pat) throws Exception {
        for (Object __dummyForeachVar9 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar9;
            System.String __dummyScrutVar7 = field.FieldName;
            if (__dummyScrutVar7.equals("nameFL"))
            {
                field.FieldValue = pat.getNameFirstOrPrefL();
            }
            else if (__dummyScrutVar7.equals("nameLF"))
            {
                field.FieldValue = pat.getNameLF();
            }
            else if (__dummyScrutVar7.equals("weekdayDateTime"))
            {
                field.FieldValue = appt.AptDateTime.ToString("ddd") + "   " + appt.AptDateTime.ToShortDateString() + "  " + appt.AptDateTime.ToShortTimeString();
            }
            else //  h:mm tt");
            if (__dummyScrutVar7.equals("length"))
            {
                int minutesTotal = appt.Pattern.Length * 5;
                int hours = minutesTotal / 60;
                //automatically rounds down
                int minutes = minutesTotal - hours * 60;
                field.FieldValue = "";
                if (hours > 0)
                {
                    field.FieldValue = hours.ToString() + " hours, ";
                }
                 
                field.FieldValue += minutes.ToString() + " min";
            }
                
        }
    }

    private static void fillFieldsForRx(Sheet sheet, RxPat rx, Patient pat, Provider prov) throws Exception {
        Clinic clinic = null;
        if (pat.ClinicNum != 0)
        {
            clinic = Clinics.getClinic(pat.ClinicNum);
        }
         
        String text = new String();
        for (Object __dummyForeachVar10 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar10;
            System.String __dummyScrutVar8 = field.FieldName;
            if (__dummyScrutVar8.equals("prov.nameFL"))
            {
                field.FieldValue = prov.getFormalName();
            }
            else if (__dummyScrutVar8.equals("prov.address"))
            {
                if (clinic == null)
                {
                    field.FieldValue = PrefC.getString(PrefName.PracticeAddress);
                    if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                    {
                        field.FieldValue += "\r\n" + PrefC.getString(PrefName.PracticeAddress2);
                    }
                     
                }
                else
                {
                    field.FieldValue = clinic.Address;
                    if (!StringSupport.equals(clinic.Address2, ""))
                    {
                        field.FieldValue += "\r\n" + clinic.Address2;
                    }
                     
                } 
            }
            else if (__dummyScrutVar8.equals("prov.cityStateZip"))
            {
                if (clinic == null)
                {
                    field.FieldValue = PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + " " + PrefC.getString(PrefName.PracticeZip);
                }
                else
                {
                    field.FieldValue = clinic.City + ", " + clinic.State + " " + clinic.Zip;
                } 
            }
            else if (__dummyScrutVar8.equals("prov.phone"))
            {
                if (clinic == null)
                {
                    text = PrefC.getString(PrefName.PracticePhone);
                }
                else
                {
                    text = clinic.Phone;
                } 
                field.FieldValue = text;
                if (text.Length == 10)
                {
                    field.FieldValue = "(" + text.Substring(0, 3) + ")" + text.Substring(3, 3) + "-" + text.Substring(6);
                }
                 
            }
            else if (__dummyScrutVar8.equals("RxDate"))
            {
                field.FieldValue = rx.RxDate.ToShortDateString();
            }
            else if (__dummyScrutVar8.equals("RxDateMonthSpelled"))
            {
                field.FieldValue = rx.RxDate.ToString("MMM dd,yyyy");
            }
            else if (__dummyScrutVar8.equals("prov.dEANum"))
            {
                if (rx.IsControlled)
                {
                    field.FieldValue = prov.DEANum;
                }
                else
                {
                    field.FieldValue = "";
                } 
            }
            else if (__dummyScrutVar8.equals("pat.nameFL"))
            {
                //Can't include preferred, so:
                field.FieldValue = pat.FName + " " + pat.MiddleI + "  " + pat.LName;
            }
            else if (__dummyScrutVar8.equals("pat.Birthdate"))
            {
                if (pat.Birthdate.Year < 1880)
                {
                    field.FieldValue = "";
                }
                else
                {
                    field.FieldValue = pat.Birthdate.ToShortDateString();
                } 
            }
            else if (__dummyScrutVar8.equals("pat.HmPhone"))
            {
                field.FieldValue = pat.HmPhone;
            }
            else if (__dummyScrutVar8.equals("pat.address"))
            {
                field.FieldValue = pat.Address;
                if (!StringSupport.equals(pat.Address2, ""))
                {
                    field.FieldValue += "\r\n" + pat.Address2;
                }
                 
            }
            else if (__dummyScrutVar8.equals("pat.cityStateZip"))
            {
                field.FieldValue = pat.City + ", " + pat.State + " " + pat.Zip;
            }
            else if (__dummyScrutVar8.equals("Drug"))
            {
                field.FieldValue = rx.Drug;
            }
            else if (__dummyScrutVar8.equals("Disp"))
            {
                field.FieldValue = rx.Disp;
            }
            else if (__dummyScrutVar8.equals("Sig"))
            {
                field.FieldValue = rx.Sig;
            }
            else if (__dummyScrutVar8.equals("Refills"))
            {
                field.FieldValue = rx.Refills;
            }
            else if (__dummyScrutVar8.equals("prov.stateRxID"))
            {
                field.FieldValue = prov.StateRxID;
            }
            else if (__dummyScrutVar8.equals("prov.StateLicense"))
            {
                field.FieldValue = prov.StateLicense;
            }
            else if (__dummyScrutVar8.equals("prov.NationalProvID"))
            {
                field.FieldValue = prov.NationalProvID;
            }
                               
        }
    }

    private static void fillFieldsForConsent(Sheet sheet, Patient pat) throws Exception {
        for (Object __dummyForeachVar11 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar11;
            System.String __dummyScrutVar9 = field.FieldName;
            if (__dummyScrutVar9.equals("patient.nameFL"))
            {
                field.FieldValue = pat.getNameFL();
            }
            else if (__dummyScrutVar9.equals("dateTime.Today"))
            {
                field.FieldValue = DateTime.Today.ToShortDateString();
            }
              
        }
    }

    private static void fillFieldsForPatientLetter(Sheet sheet, Patient pat) throws Exception {
        for (Object __dummyForeachVar12 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar12;
            System.String __dummyScrutVar10 = field.FieldName;
            if (__dummyScrutVar10.equals("PracticeTitle"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeTitle);
            }
            else if (__dummyScrutVar10.equals("PracticeAddress"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeAddress);
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                {
                    field.FieldValue += "\r\n" + PrefC.getString(PrefName.PracticeAddress2);
                }
                 
            }
            else if (__dummyScrutVar10.equals("practiceCityStateZip"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip);
            }
            else if (__dummyScrutVar10.equals("patient.nameFL"))
            {
                field.FieldValue = pat.getNameFLFormal();
            }
            else if (__dummyScrutVar10.equals("patient.address"))
            {
                field.FieldValue = pat.Address;
                if (!StringSupport.equals(pat.Address2, ""))
                {
                    field.FieldValue += "\r\n" + pat.Address2;
                }
                 
            }
            else if (__dummyScrutVar10.equals("patient.cityStateZip"))
            {
                field.FieldValue = pat.City + ", " + pat.State + " " + pat.Zip;
            }
            else if (__dummyScrutVar10.equals("today.DayDate"))
            {
                field.FieldValue = DateTime.Today.ToString("dddd") + ", " + DateTime.Today.ToShortDateString();
            }
            else if (__dummyScrutVar10.equals("patient.salutation"))
            {
                field.FieldValue = "Dear " + pat.getSalutation() + ":";
            }
            else if (__dummyScrutVar10.equals("patient.priProvNameFL"))
            {
                field.FieldValue = Providers.getFormalName(pat.PriProv);
            }
                     
        }
    }

    private static void fillFieldsForReferralLetter(Sheet sheet, Patient pat, Referral refer) throws Exception {
        for (Object __dummyForeachVar13 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar13;
            System.String __dummyScrutVar11 = field.FieldName;
            if (__dummyScrutVar11.equals("PracticeTitle"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeTitle);
            }
            else if (__dummyScrutVar11.equals("PracticeAddress"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeAddress);
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                {
                    field.FieldValue += "\r\n" + PrefC.getString(PrefName.PracticeAddress2);
                }
                 
            }
            else if (__dummyScrutVar11.equals("practiceCityStateZip"))
            {
                field.FieldValue = PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip);
            }
            else if (__dummyScrutVar11.equals("referral.phone"))
            {
                field.FieldValue = "";
                if (refer.Telephone.Length == 10)
                {
                    field.FieldValue = "(" + refer.Telephone.Substring(0, 3) + ")" + refer.Telephone.Substring(3, 3) + "-" + refer.Telephone.Substring(6);
                }
                 
            }
            else if (__dummyScrutVar11.equals("referral.phone2"))
            {
                field.FieldValue = refer.Phone2;
            }
            else if (__dummyScrutVar11.equals("referral.nameFL"))
            {
                field.FieldValue = Referrals.getNameFL(refer.ReferralNum);
            }
            else if (__dummyScrutVar11.equals("referral.address"))
            {
                field.FieldValue = refer.Address;
                if (!StringSupport.equals(refer.Address2, ""))
                {
                    field.FieldValue += "\r\n" + refer.Address2;
                }
                 
            }
            else if (__dummyScrutVar11.equals("referral.cityStateZip"))
            {
                field.FieldValue = refer.City + ", " + refer.ST + " " + refer.Zip;
            }
            else if (__dummyScrutVar11.equals("today.DayDate"))
            {
                field.FieldValue = DateTime.Today.ToString("dddd") + ", " + DateTime.Today.ToShortDateString();
            }
            else if (__dummyScrutVar11.equals("patient.nameFL"))
            {
                field.FieldValue = pat.getNameFL();
            }
            else if (__dummyScrutVar11.equals("referral.salutation"))
            {
                field.FieldValue = "Dear " + refer.FName + ":";
            }
            else if (__dummyScrutVar11.equals("patient.priProvNameFL"))
            {
                field.FieldValue = Providers.getFormalName(pat.PriProv);
            }
                        
        }
    }

    private static void fillFieldsForPatientForm(Sheet sheet, Patient pat) throws Exception {
        Family fam = Patients.getFamily(pat.PatNum);
        List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        InsPlan insplan1 = null;
        InsSub sub1 = null;
        Carrier carrier1 = null;
        if (patPlanList.Count > 0)
        {
            sub1 = InsSubs.GetSub(patPlanList[0].InsSubNum, subList);
            insplan1 = InsPlans.GetPlan(sub1.PlanNum, planList);
            carrier1 = Carriers.getCarrier(insplan1.CarrierNum);
        }
         
        InsPlan insplan2 = null;
        InsSub sub2 = null;
        Carrier carrier2 = null;
        if (patPlanList.Count > 1)
        {
            sub2 = InsSubs.GetSub(patPlanList[1].InsSubNum, subList);
            insplan2 = InsPlans.GetPlan(sub2.PlanNum, planList);
            carrier2 = Carriers.getCarrier(insplan2.CarrierNum);
        }
         
        for (Object __dummyForeachVar14 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar14;
            System.String __dummyScrutVar12 = field.FieldName;
            if (__dummyScrutVar12.equals("Address"))
            {
                field.FieldValue = pat.Address;
            }
            else if (__dummyScrutVar12.equals("Address2"))
            {
                field.FieldValue = pat.Address2;
            }
            else if (__dummyScrutVar12.equals("addressAndHmPhoneIsSameEntireFamily"))
            {
                boolean isSame = true;
                for (int i = 0;i < fam.ListPats.Length;i++)
                {
                    if (!StringSupport.equals(pat.HmPhone, fam.ListPats[i].HmPhone) || !StringSupport.equals(pat.Address, fam.ListPats[i].Address) || !StringSupport.equals(pat.Address2, fam.ListPats[i].Address2) || !StringSupport.equals(pat.City, fam.ListPats[i].City) || !StringSupport.equals(pat.State, fam.ListPats[i].State) || !StringSupport.equals(pat.Zip, fam.ListPats[i].Zip))
                    {
                        isSame = false;
                        break;
                    }
                     
                }
                if (isSame)
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("Birthdate"))
            {
                field.FieldValue = pat.Birthdate.ToShortDateString();
            }
            else if (__dummyScrutVar12.equals("City"))
            {
                field.FieldValue = pat.City;
            }
            else if (__dummyScrutVar12.equals("Email"))
            {
                field.FieldValue = pat.Email;
            }
            else if (__dummyScrutVar12.equals("FName"))
            {
                field.FieldValue = pat.FName;
            }
            else if (__dummyScrutVar12.equals("Gender"))
            {
                if (StringSupport.equals(field.RadioButtonValue, pat.Gender.ToString()))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("HmPhone"))
            {
                field.FieldValue = pat.HmPhone;
            }
            else if (__dummyScrutVar12.equals("ins1CarrierName"))
            {
                if (carrier1 != null)
                {
                    field.FieldValue = carrier1.CarrierName;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1CarrierPhone"))
            {
                if (carrier1 != null)
                {
                    field.FieldValue = carrier1.Phone;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1EmployerName"))
            {
                if (insplan1 != null)
                {
                    field.FieldValue = Employers.getName(insplan1.EmployerNum);
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1GroupName"))
            {
                if (insplan1 != null)
                {
                    field.FieldValue = insplan1.GroupName;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1GroupNum"))
            {
                if (insplan1 != null)
                {
                    field.FieldValue = insplan1.GroupNum;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1Relat"))
            {
                if (patPlanList.Count > 0 && StringSupport.equals(patPlanList[0].Relationship.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1SubscriberID"))
            {
                if (insplan1 != null)
                {
                    field.FieldValue = sub1.SubscriberID;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins1SubscriberNameF"))
            {
                if (insplan1 != null)
                {
                    field.FieldValue = fam.getNameInFamFirst(sub1.Subscriber);
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2CarrierName"))
            {
                if (carrier2 != null)
                {
                    field.FieldValue = carrier2.CarrierName;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2CarrierPhone"))
            {
                if (carrier2 != null)
                {
                    field.FieldValue = carrier2.Phone;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2EmployerName"))
            {
                if (insplan2 != null)
                {
                    field.FieldValue = Employers.getName(insplan2.EmployerNum);
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2GroupName"))
            {
                if (insplan2 != null)
                {
                    field.FieldValue = insplan2.GroupName;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2GroupNum"))
            {
                if (insplan2 != null)
                {
                    field.FieldValue = insplan2.GroupNum;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2Relat"))
            {
                if (patPlanList.Count > 1 && StringSupport.equals(patPlanList[1].Relationship.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2SubscriberID"))
            {
                if (insplan2 != null)
                {
                    field.FieldValue = sub2.SubscriberID;
                }
                 
            }
            else if (__dummyScrutVar12.equals("ins2SubscriberNameF"))
            {
                if (insplan2 != null)
                {
                    field.FieldValue = fam.getNameInFamFirst(sub2.Subscriber);
                }
                 
            }
            else if (__dummyScrutVar12.equals("LName"))
            {
                field.FieldValue = pat.LName;
            }
            else if (__dummyScrutVar12.equals("MiddleI"))
            {
                field.FieldValue = pat.MiddleI;
            }
            else if (__dummyScrutVar12.equals("Position"))
            {
                if (StringSupport.equals(pat.Position.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("PreferConfirmMethod"))
            {
                if (StringSupport.equals(pat.PreferConfirmMethod.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("PreferContactMethod"))
            {
                if (StringSupport.equals(pat.PreferContactMethod.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("PreferRecallMethod"))
            {
                if (StringSupport.equals(pat.PreferRecallMethod.ToString(), field.RadioButtonValue))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("Preferred"))
            {
                field.FieldValue = pat.Preferred;
            }
            else if (__dummyScrutVar12.equals("referredFrom"))
            {
                Referral referral = Referrals.getReferralForPat(pat.PatNum);
                if (referral != null)
                {
                    field.FieldValue = Referrals.getNameFL(referral.ReferralNum);
                }
                 
            }
            else if (__dummyScrutVar12.equals("SSN"))
            {
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && pat.SSN.Length == 9)
                {
                    //and length exactly 9 (no data gets lost in formatting)
                    field.FieldValue = pat.SSN.Substring(0, 3) + "-" + pat.SSN.Substring(3, 2) + "-" + pat.SSN.Substring(5, 4);
                }
                else
                {
                    field.FieldValue = pat.SSN;
                } 
            }
            else if (__dummyScrutVar12.equals("State"))
            {
                field.FieldValue = pat.State;
            }
            else if (__dummyScrutVar12.equals("StudentStatus"))
            {
                if (StringSupport.equals(pat.StudentStatus, "F") && StringSupport.equals(field.RadioButtonValue, "Fulltime"))
                {
                    field.FieldValue = "X";
                }
                 
                if (StringSupport.equals(pat.StudentStatus, "N") && StringSupport.equals(field.RadioButtonValue, "Nonstudent"))
                {
                    field.FieldValue = "X";
                }
                 
                if (StringSupport.equals(pat.StudentStatus, "P") && StringSupport.equals(field.RadioButtonValue, "Parttime"))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar12.equals("WirelessPhone"))
            {
                field.FieldValue = pat.WirelessPhone;
            }
            else if (__dummyScrutVar12.equals("wirelessCarrier"))
            {
                field.FieldValue = "";
            }
            else //not implemented
            if (__dummyScrutVar12.equals("WkPhone"))
            {
                field.FieldValue = pat.WkPhone;
            }
            else if (__dummyScrutVar12.equals("Zip"))
            {
                field.FieldValue = pat.Zip;
            }
                                                    
        }
    }

    private static void fillFieldsForRoutingSlip(Sheet sheet, Patient pat, Appointment apt) throws Exception {
        Family fam = Patients.getFamily(apt.PatNum);
        String str = new String();
        for (Object __dummyForeachVar15 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar15;
            System.String __dummyScrutVar13 = field.FieldName;
            if (__dummyScrutVar13.equals("appt.timeDate"))
            {
                field.FieldValue = apt.AptDateTime.ToShortTimeString() + "  " + apt.AptDateTime.ToShortDateString();
            }
            else if (__dummyScrutVar13.equals("appt.length"))
            {
                field.FieldValue = (apt.Pattern.Length * 5).ToString() + " " + Lan.g("SheetRoutingSlip","minutes");
            }
            else if (__dummyScrutVar13.equals("appt.providers"))
            {
                str = Providers.getLongDesc(apt.ProvNum);
                if (apt.ProvHyg != 0)
                {
                    str += "\r\n" + Providers.getLongDesc(apt.ProvHyg);
                }
                 
                field.FieldValue = str;
            }
            else if (__dummyScrutVar13.equals("appt.procedures"))
            {
                str = "";
                List<Procedure> procs = Procedures.getProcsForSingle(apt.AptNum,false);
                boolean isOnlyTP = true;
                for (int i = 0;i < procs.Count;i++)
                {
                    if (procs[i].ProcStatus != OpenDentBusiness.ProcStat.TP)
                    {
                        isOnlyTP = false;
                        break;
                    }
                     
                }
                if (isOnlyTP)
                {
                    Procedure[] procListTP = Procedures.GetListTP(procs);
                    for (int i = 0;i < procListTP.Length;i++)
                    {
                        //this sorts.  Doesn't work unless all are TP.
                        if (i > 0)
                        {
                            str += "\r\n";
                        }
                         
                        str += Procedures.GetDescription(procListTP[i]);
                    }
                }
                else
                {
                    for (int i = 0;i < procs.Count;i++)
                    {
                        if (i > 0)
                        {
                            str += "\r\n";
                        }
                         
                        str += Procedures.GetDescription(procs[i]);
                    }
                } 
                field.FieldValue = str;
            }
            else if (__dummyScrutVar13.equals("appt.Note"))
            {
                field.FieldValue = apt.Note;
            }
            else if (__dummyScrutVar13.equals("otherFamilyMembers"))
            {
                str = "";
                for (int i = 0;i < fam.ListPats.Length;i++)
                {
                    if (fam.ListPats[i].PatNum == pat.PatNum)
                    {
                        continue;
                    }
                     
                    if (fam.ListPats[i].PatStatus == PatientStatus.Archived || fam.ListPats[i].PatStatus == PatientStatus.Deceased)
                    {
                        continue;
                    }
                     
                    //Prospective patients will show.
                    if (!StringSupport.equals(str, ""))
                    {
                        str += "\r\n";
                    }
                     
                    str += fam.ListPats[i].GetNameFL();
                    if (fam.ListPats[i].Age > 0)
                    {
                        str += ",   " + fam.ListPats[i].Age.ToString();
                    }
                     
                }
                field.FieldValue = str;
            }
                  
        }
    }

    private static void fillFieldsForMedicalHistory(Sheet sheet, Patient pat) throws Exception {
        List<SheetField> inputMedList = new List<SheetField>();
        for (Object __dummyForeachVar16 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar16;
            System.String __dummyScrutVar14 = field.FieldName;
            if (__dummyScrutVar14.equals("Birthdate"))
            {
                field.FieldValue = pat.Birthdate.ToShortDateString();
                continue;
            }
            else if (__dummyScrutVar14.equals("FName"))
            {
                field.FieldValue = pat.FName;
                continue;
            }
            else if (__dummyScrutVar14.equals("LName"))
            {
                field.FieldValue = pat.LName;
                continue;
            }
               
            if (field.FieldType == SheetFieldType.CheckBox)
            {
                if (field.FieldName.StartsWith("allergy:"))
                {
                    //"allergy:Pen"
                    List<Allergy> allergies = Allergies.getAll(pat.PatNum,true);
                    for (int i = 0;i < allergies.Count;i++)
                    {
                        if (AllergyDefs.GetDescription(allergies[i].AllergyDefNum) == field.FieldName.Remove(0, 8))
                        {
                            if (allergies[i].StatusIsActive && StringSupport.equals(field.RadioButtonValue, "Y"))
                            {
                                field.FieldValue = "X";
                            }
                            else if (!allergies[i].StatusIsActive && StringSupport.equals(field.RadioButtonValue, "N"))
                            {
                                field.FieldValue = "X";
                            }
                              
                            break;
                        }
                         
                    }
                }
                else if (field.FieldName.StartsWith("problem:"))
                {
                    //"problem:Hepatitis B"
                    List<Disease> diseases = Diseases.refresh(pat.PatNum,false);
                    for (int i = 0;i < diseases.Count;i++)
                    {
                        if (DiseaseDefs.GetName(diseases[i].DiseaseDefNum) == field.FieldName.Remove(0, 8))
                        {
                            if (diseases[i].ProbStatus == ProblemStatus.Active && StringSupport.equals(field.RadioButtonValue, "Y"))
                            {
                                field.FieldValue = "X";
                            }
                            else if (diseases[i].ProbStatus != ProblemStatus.Active && StringSupport.equals(field.RadioButtonValue, "N"))
                            {
                                field.FieldValue = "X";
                            }
                              
                            break;
                        }
                         
                    }
                }
                  
            }
            else if (field.FieldType == SheetFieldType.InputField && field.FieldName.StartsWith("inputMed"))
            {
                inputMedList.Add(field);
            }
              
        }
        //Special logic for checkMed and inputMed.
        if (inputMedList.Count > 0)
        {
            inputMedList.Sort(CompareSheetFieldNames);
            //Loop through the patients medications and fill in the input fields.
            List<MedicationPat> medPatList = MedicationPats.refresh(pat.PatNum,false);
            for (int i = 0;i < medPatList.Count;i++)
            {
                if (i == inputMedList.Count)
                {
                    break;
                }
                 
                //Pat has more medications than inputMed fields on sheet.
                if (medPatList[i].MedicationNum == 0)
                {
                    inputMedList[i].FieldValue = medPatList[i].MedDescript;
                }
                else
                {
                    inputMedList[i].FieldValue = Medications.GetDescription(medPatList[i].MedicationNum);
                } 
                inputMedList[i].FieldType = SheetFieldType.OutputText;
            }
        }
         
    }

    //Don't try to import as a new medication.
    private static int compareSheetFieldNames(SheetField input1, SheetField input2) throws Exception {
        if (Convert.ToInt32(input1.FieldName.Remove(0, 8)) < Convert.ToInt32(input2.FieldName.Remove(0, 8)))
        {
            return -1;
        }
         
        if (Convert.ToInt32(input1.FieldName.Remove(0, 8)) > Convert.ToInt32(input2.FieldName.Remove(0, 8)))
        {
            return 1;
        }
         
        return 0;
    }

    private static void fillFieldsForLabCase(Sheet sheet, Patient pat, LabCase labcase) throws Exception {
        Laboratory lab = Laboratories.getOne(labcase.LaboratoryNum);
        //might possibly be null
        Provider prov = Providers.getProv(labcase.ProvNum);
        Appointment appt = Appointments.getOneApt(labcase.AptNum);
        for (Object __dummyForeachVar17 : sheet.SheetFields)
        {
            //might be null
            SheetField field = (SheetField)__dummyForeachVar17;
            System.String __dummyScrutVar15 = field.FieldName;
            if (__dummyScrutVar15.equals("lab.Description"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.Description;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.Phone"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.Phone;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.Notes"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.Notes;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.WirelessPhone"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.WirelessPhone;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.Address"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.Address;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.CityStZip"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.City + ", " + lab.State + " " + lab.Zip;
                }
                 
            }
            else if (__dummyScrutVar15.equals("lab.Email"))
            {
                if (lab != null)
                {
                    field.FieldValue = lab.Email;
                }
                 
            }
            else if (__dummyScrutVar15.equals("appt.DateTime"))
            {
                if (appt != null)
                {
                    field.FieldValue = appt.AptDateTime.ToShortDateString() + "  " + appt.AptDateTime.ToShortTimeString();
                }
                 
            }
            else if (__dummyScrutVar15.equals("labcase.DateTimeDue"))
            {
                field.FieldValue = labcase.DateTimeDue.ToShortDateString() + "  " + labcase.DateTimeDue.ToShortTimeString();
            }
            else if (__dummyScrutVar15.equals("labcase.DateTimeCreated"))
            {
                field.FieldValue = labcase.DateTimeCreated.ToShortDateString() + "  " + labcase.DateTimeCreated.ToShortTimeString();
            }
            else if (__dummyScrutVar15.equals("labcase.Instructions"))
            {
                field.FieldValue = labcase.Instructions;
            }
            else if (__dummyScrutVar15.equals("prov.nameFormal"))
            {
                field.FieldValue = prov.getFormalName();
            }
            else if (__dummyScrutVar15.equals("prov.stateLicence"))
            {
                field.FieldValue = prov.StateLicense;
            }
                         
        }
    }

    private static void fillFieldsForExamSheet(Sheet sheet, Patient pat) throws Exception {
        for (Object __dummyForeachVar18 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar18;
            System.String __dummyScrutVar16 = field.FieldName;
            if (__dummyScrutVar16.equals("Birthdate"))
            {
                field.FieldValue = pat.Birthdate.ToShortDateString();
            }
            else if (__dummyScrutVar16.equals("FName"))
            {
                field.FieldValue = pat.FName;
            }
            else if (__dummyScrutVar16.equals("Gender"))
            {
                if (StringSupport.equals(field.RadioButtonValue, pat.Gender.ToString()))
                {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar16.equals("LName"))
            {
                field.FieldValue = pat.LName;
            }
            else if (__dummyScrutVar16.equals("MiddleI"))
            {
                field.FieldValue = pat.MiddleI;
            }
            else if (__dummyScrutVar16.equals("patient.priProvNameFL"))
            {
                field.FieldValue = Providers.getFormalName(pat.PriProv);
            }
            else if (__dummyScrutVar16.equals("Preferred"))
            {
                field.FieldValue = pat.Preferred;
            }
            else if (__dummyScrutVar16.equals("Race"))
            {
                if (StringSupport.equals(field.RadioButtonValue, PatientRaces.getPatientRaceOldFromPatientRaces(pat.PatNum).ToString()))
                {
                    //==pat.Race.ToString()) {
                    field.FieldValue = "X";
                }
                 
            }
            else if (__dummyScrutVar16.equals("sheet.DateTimeSheet"))
            {
                field.FieldValue = sheet.DateTimeSheet.ToString();
            }
                     
        }
    }

    private static void fillFieldsForDepositSlip(Sheet sheet, Deposit deposit) throws Exception {
        List<Payment> PatPayList = Payments.getForDeposit(deposit.DepositNum);
        ClaimPayment[] ClaimPayList = ClaimPayments.getForDeposit(deposit.DepositNum);
        List<String[]> depositList = new List<String[]>();
        int[] colSize = new int[]{ 11, 33, 15, 14, 0 };
        for (int i = 0;i < PatPayList.Count;i++)
        {
            String amount = PatPayList[i].PayAmt.ToString("F");
            colSize[4] = Math.Max(colSize[4], amount.Length);
        }
        for (int i = 0;i < ClaimPayList.Length;i++)
        {
            String amount = ClaimPayList[i].CheckAmt.ToString("F");
            colSize[4] = Math.Max(colSize[4], amount.Length);
        }
        for (int i = 0;i < PatPayList.Count;i++)
        {
            String[] depositItem = new String[5];
            String date = PatPayList[i].PayDate.ToShortDateString();
            if (date.Length > colSize[0])
            {
                date = date.Substring(0, colSize[0]);
            }
             
            depositItem[0] = date.PadRight(colSize[0], ' ') + " ";
            Patient pat = Patients.GetPat(PatPayList[i].PatNum);
            String name = pat.getNameLF();
            if (name.Length > colSize[1])
            {
                name = name.Substring(0, colSize[1]);
            }
             
            depositItem[1] = name.PadRight(colSize[1], ' ') + " ";
            String checkNum = PatPayList[i].CheckNum;
            if (checkNum.Length > colSize[2])
            {
                checkNum = checkNum.Substring(0, colSize[2]);
            }
             
            depositItem[2] = checkNum.PadRight(colSize[2], ' ') + " ";
            String bankBranch = PatPayList[i].BankBranch;
            if (bankBranch.Length > colSize[3])
            {
                bankBranch = bankBranch.Substring(0, colSize[3]);
            }
             
            depositItem[3] = bankBranch.PadRight(colSize[3], ' ') + " ";
            depositItem[4] = PatPayList[i].PayAmt.ToString("F").PadLeft(colSize[4], ' ');
            depositList.Add(depositItem);
        }
        for (int i = 0;i < ClaimPayList.Length;i++)
        {
            String[] depositItem = new String[5];
            String date = ClaimPayList[i].CheckDate.ToShortDateString();
            if (date.Length > colSize[0])
            {
                date = date.Substring(0, colSize[0]);
            }
             
            depositItem[0] = date.PadRight(colSize[0], ' ') + " ";
            String name = ClaimPayList[i].CarrierName;
            if (name.Length > colSize[1])
            {
                name = name.Substring(0, colSize[1]);
            }
             
            depositItem[1] = name.PadRight(colSize[1], ' ') + " ";
            String checkNum = ClaimPayList[i].CheckNum;
            if (checkNum.Length > colSize[2])
            {
                checkNum = checkNum.Substring(0, colSize[2]);
            }
             
            depositItem[2] = checkNum.PadRight(colSize[2], ' ') + " ";
            String bankBranch = ClaimPayList[i].BankBranch;
            if (bankBranch.Length > colSize[3])
            {
                bankBranch = bankBranch.Substring(0, colSize[3]);
            }
             
            depositItem[3] = bankBranch.PadRight(colSize[3], ' ') + " ";
            depositItem[4] = ClaimPayList[i].CheckAmt.ToString("F").PadLeft(colSize[4], ' ');
            depositList.Add(depositItem);
        }
        for (Object __dummyForeachVar19 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar19;
            System.String __dummyScrutVar17 = field.FieldName;
            if (__dummyScrutVar17.equals("deposit.BankAccountInfo"))
            {
                field.FieldValue = deposit.BankAccountInfo;
            }
            else if (__dummyScrutVar17.equals("deposit.DateDeposit"))
            {
                field.FieldValue = deposit.DateDeposit.ToShortDateString();
            }
            else if (__dummyScrutVar17.equals("depositList"))
            {
                StringBuilder depositListB = new StringBuilder("Date        Name                              Check Number    Bank-Branch    Amount" + Environment.NewLine);
                for (int i = 0;i < depositList.Count;i++)
                {
                    if (i > 0)
                    {
                        depositListB.Append(Environment.NewLine);
                    }
                     
                    for (int j = 0;j < 5;j++)
                    {
                        depositListB.Append(depositList[i][j]);
                    }
                }
                field.FieldValue = depositListB.ToString();
            }
            else if (__dummyScrutVar17.equals("depositTotal"))
            {
                double total = 0;
                for (int i = 0;i < PatPayList.Count;i++)
                {
                    total += (double)PatPayList[i].PayAmt;
                }
                for (int i = 0;i < ClaimPayList.Length;i++)
                {
                    total += (double)ClaimPayList[i].CheckAmt;
                }
                field.FieldValue = total.ToString("n").PadLeft(12, ' ');
            }
            else if (__dummyScrutVar17.equals("depositItemCount"))
            {
                field.FieldValue = (PatPayList.Count + ClaimPayList.Length).ToString().PadLeft(2, '0');
            }
            else if (__dummyScrutVar17.equals("depositItem01"))
            {
                if (depositList.Count >= 1)
                {
                    field.FieldValue = depositList[0][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem02"))
            {
                if (depositList.Count >= 2)
                {
                    field.FieldValue = depositList[1][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem03"))
            {
                if (depositList.Count >= 3)
                {
                    field.FieldValue = depositList[2][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem04"))
            {
                if (depositList.Count >= 4)
                {
                    field.FieldValue = depositList[3][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem05"))
            {
                if (depositList.Count >= 5)
                {
                    field.FieldValue = depositList[4][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem06"))
            {
                if (depositList.Count >= 6)
                {
                    field.FieldValue = depositList[5][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem07"))
            {
                if (depositList.Count >= 7)
                {
                    field.FieldValue = depositList[6][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem08"))
            {
                if (depositList.Count >= 8)
                {
                    field.FieldValue = depositList[7][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem09"))
            {
                if (depositList.Count >= 9)
                {
                    field.FieldValue = depositList[8][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem10"))
            {
                if (depositList.Count >= 10)
                {
                    field.FieldValue = depositList[9][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem11"))
            {
                if (depositList.Count >= 11)
                {
                    field.FieldValue = depositList[10][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem12"))
            {
                if (depositList.Count >= 12)
                {
                    field.FieldValue = depositList[11][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem13"))
            {
                if (depositList.Count >= 13)
                {
                    field.FieldValue = depositList[12][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem14"))
            {
                if (depositList.Count >= 14)
                {
                    field.FieldValue = depositList[13][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem15"))
            {
                if (depositList.Count >= 15)
                {
                    field.FieldValue = depositList[14][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem16"))
            {
                if (depositList.Count >= 16)
                {
                    field.FieldValue = depositList[15][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem17"))
            {
                if (depositList.Count >= 17)
                {
                    field.FieldValue = depositList[16][4].PadLeft(12, ' ');
                }
                 
            }
            else if (__dummyScrutVar17.equals("depositItem18"))
            {
                if (depositList.Count >= 18)
                {
                    field.FieldValue = depositList[17][4].PadLeft(12, ' ');
                }
                 
            }
                                   
        }
    }

}


