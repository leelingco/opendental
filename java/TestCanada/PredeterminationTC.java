//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDental.ClaimL;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.CCDFieldInputter;
import OpenDental.Eclaims.Eclaims;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.YN;
import TestCanada.CarrierTC;
import TestCanada.InsSubTC;
import TestCanada.PatientTC;
import TestCanada.ProcTC;

public class PredeterminationTC   
{
    /**
    * Remember that this is 0-based.  So subtract 1 from the script number to get the index in this list.
    */
    public static List<long> ClaimNums = new List<long>();
    public static String createAllPredeterminations() throws Exception {
        ClaimNums = new List<long>();
        createOne();
        createTwo();
        createThree();
        createFour();
        createFive();
        createSix_1();
        createSix_2();
        createSeven_1();
        createSeven_2();
        createEight();
        return "Predetermination objects set.\r\n";
    }

    private static void createOne() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum1);
        //patient#1, Lisa Fête"
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "11", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "X";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createTwo() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum2);
        //patient#2, John Smith
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "41", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "XI";
        //N=XI
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "N";
        claim.CanadianDateInitialLower = new DateTime(1984, 04, 07);
        claim.CanadianMandProsthMaterial = 4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createThree() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum4);
        //patient#4, John Smith
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "41", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "MI";
        //L=MI
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = new DateTime(1997, 03, 02);
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "Y";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = true;
        claim.CanadaEstTreatStartDate = new DateTime(1999, 04, 01);
        claim.CanadaInitialPayment = 1000;
        claim.CanadaPaymentMode = 3;
        claim.CanadaTreatDuration = 48;
        claim.CanadaNumAnticipatedPayments = 16;
        claim.CanadaAnticipatedPayAmount = 200;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createFour() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum5);
        //patient#5, Bob L Howard
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "21", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "I";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        claim.CanadianReferralProviderNum = "081234500";
        claim.CanadianReferralReason = 4;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createFive() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum7);
        //patient#7, Madeleine Arpege
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "21", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "M";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSix_1() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum7);
        //patient#7, Madeleine Arpege
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "21", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"25","MIV",107.6,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"14","DIV",107.6,"X",provNum));
        proc = ProcTC.AddProc("27211", pat.PatNum, new DateTime(1999, 1, 1), "24", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 238, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        proc = ProcTC.AddProc("27213", pat.PatNum, new DateTime(1999, 1, 1), "26", "", 450, "E", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 210, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 35, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "M";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSix_2() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum7);
        //patient#7, Madeleine Arpege
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("39202",pat.PatNum,new DateTime(1999, 1, 1),"36","",67.5,"X",provNum));
        procList.Add(ProcTC.AddProc("32222", pat.PatNum, new DateTime(1999, 1, 1), "32", "", 65, "X", provNum));
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "M";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSeven_1() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum3);
        //patient#3, Mary Walls
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("67301", pat.PatNum, new DateTime(1999, 1, 1), "21", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 300, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 40, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"25","MIV",107.6,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"14","DIV",107.6,"X",provNum));
        proc = ProcTC.AddProc("27211", pat.PatNum, new DateTime(1999, 1, 1), "24", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 238, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        proc = ProcTC.AddProc("27213", pat.PatNum, new DateTime(1999, 1, 1), "26", "", 450, "E", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 210, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "", "", 35, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "M";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSeven_2() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum3);
        //patient#3, Mary Walls
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("39202",pat.PatNum,new DateTime(1999, 1, 1),"36","",67.5,"X",provNum));
        procList.Add(ProcTC.AddProc("32222", pat.PatNum, new DateTime(1999, 1, 1), "32", "", 65, "X", provNum));
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "M";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createEight() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum9);
        //patient#9, Fred Smith
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"25","MIV",107.60,"",provNum));
        Claim claim = CreatePredetermination(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static Claim createPredetermination(Patient pat, List<Procedure> procList, long provTreat) throws Exception {
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
        List<ClaimProc> claimProcList = ClaimProcs.refresh(pat.PatNum);
        List<Procedure> procsForPat = Procedures.refresh(pat.PatNum);
        InsSub sub = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, 1), subList);
        InsPlan insPlan = InsPlans.GetPlan(sub.PlanNum, planList);
        Claim claim = new Claim();
        Claims.insert(claim);
        //to retreive a key for new Claim.ClaimNum
        claim.PatNum = pat.PatNum;
        claim.DateService = procList[0].ProcDate;
        claim.DateSent = DateTime.Today;
        claim.ClaimStatus = "W";
        claim.InsSubNum = PatPlans.GetInsSubNum(patPlanList, 1);
        claim.InsSubNum2 = PatPlans.GetInsSubNum(patPlanList, 2);
        InsSub sub1 = InsSubs.GetSub(claim.InsSubNum, subList);
        InsSub sub2 = InsSubs.GetSub(claim.InsSubNum, subList);
        claim.PlanNum = sub1.PlanNum;
        claim.PlanNum2 = sub2.PlanNum;
        claim.PatRelat = PatPlans.GetRelat(patPlanList, 1);
        claim.PatRelat2 = PatPlans.GetRelat(patPlanList, 2);
        claim.ClaimType = "PreAuth";
        claim.ProvTreat = provTreat;
        claim.IsProsthesis = "N";
        claim.ProvBill = Providers.GetBillingProvNum(claim.ProvTreat, 0);
        claim.EmployRelated = YN.No;
        ClaimProc cp;
        List<Procedure> procListClaim = new List<Procedure>();
        for (int i = 0;i < procList.Count;i++)
        {
            //this list will exclude lab fees
            if (procList[i].ProcNumLab == 0)
            {
                procListClaim.Add(procList[i]);
            }
             
        }
        for (int i = 0;i < procListClaim.Count;i++)
        {
            cp = new ClaimProc();
            ClaimProcs.CreateEst(cp, procListClaim[i], insPlan, sub);
            cp.ClaimNum = claim.ClaimNum;
            cp.Status = OpenDentBusiness.ClaimProcStatus.NotReceived;
            cp.CodeSent = ProcedureCodes.GetProcCode(procListClaim[i].CodeNum).ProcCode;
            cp.LineNumber = (byte)(i + 1);
            ClaimProcs.update(cp);
        }
        claimProcList = ClaimProcs.refresh(pat.PatNum);
        ClaimL.CalculateAndUpdate(procsForPat, planList, claim, patPlanList, benefitList, pat.getAge(), subList);
        return claim;
    }

    public static String run(int scriptNum, String responseExpected, String responseTypeExpected, Claim claim, boolean showForms, int pageNumber, int lastPageNumber, double firstExamFee, double diagnosticPhaseFee) throws Exception {
        String retVal = "";
        ClaimSendQueueItem queueItem = Claims.GetQueueList(claim.ClaimNum, claim.ClinicNum, 0)[0];
        Eclaims.getMissingData(queueItem);
        //,out warnings);
        if (!StringSupport.equals(queueItem.MissingData, ""))
        {
            return "Cannot send predetermination until missing data is fixed:\r\n" + queueItem.MissingData + "\r\n";
        }
         
        long etransNum = Canadian.sendClaim(queueItem,showForms);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseType = formData.getValue("A04");
        if (!StringSupport.equals(responseType, responseTypeExpected))
        {
            return "Form type is '" + responseType + "' but should be '" + responseTypeExpected + "'\r\n";
        }
         
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, responseExpected))
        {
            return "G05 is '" + responseStatus + "' but should be '" + responseExpected + "'\r\n";
        }
         
        if (StringSupport.equals(responseExpected, "R") && StringSupport.equals(responseTypeExpected, "11"))
        {
            //so far, only for #6.  We need some other way to test if successful transaction
            String errorMsgCount = formData.getValue("G06");
            if (StringSupport.equals(errorMsgCount, "00"))
            {
                return "Wrong message count.\r\n";
            }
             
        }
         
        retVal += "Predetermination #" + scriptNum + " page " + pageNumber + " of " + lastPageNumber + " successful.\r\n";
        return retVal;
    }

    public static String runOne(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[0]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return Run(1, "C", "13", claim, showForms, 1, 1, 0, 0);
    }

    public static String runTwo(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[1]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return Run(2, "", "23", claim, showForms, 1, 1, 0, 0);
    }

    public static String runThree(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[2]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return Run(3, "", "23", claim, showForms, 1, 1, 350, 250);
    }

    public static String runFour(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[3]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return Run(4, "", "23", claim, showForms, 1, 1, 0, 0);
    }

    public static String runFive(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[4]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return Run(5, "", "13", claim, showForms, 1, 1, 0, 0);
    }

    public static String runSix(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[5]);
        //Claim 6 page 1
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        String result = Run(6, "", "13", claim, showForms, 1, 2, 0, 0);
        claim = Claims.GetClaim(ClaimNums[6]);
        //Claim 6 page 2
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        result += Environment.NewLine + Run(6, "", "13", claim, showForms, 2, 2, 0, 0);
        return result;
    }

    public static String runSeven(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[7]);
        //Claim 7 page 1
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        String result = Run(7, "", "13", claim, showForms, 1, 2, 0, 0);
        claim = Claims.GetClaim(ClaimNums[8]);
        //Claim 7 page 2
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        result += Environment.NewLine + Run(7, "", "13", claim, showForms, 2, 2, 0, 0);
        return result;
    }

    public static String runEight(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[9]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return Run(8, "C", "13", claim, showForms, 1, 1, 0, 0);
    }

}


