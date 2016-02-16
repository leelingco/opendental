//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
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

public class ClaimTC   
{
    /**
    * Remember that this is 0-based.  So subtract 1 from the script number to get the index in this list.
    */
    public static List<long> ClaimNums = new List<long>();
    public static String createAllClaims() throws Exception {
        ClaimNums = new List<long>();
        createOne();
        createTwo();
        createThree();
        createFour();
        createFive();
        createSix();
        createSeven();
        createEight();
        createNine();
        createTen();
        createEleven();
        createTwelve();
        return "Procedure objects set.\r\nClaim objects set.\r\n";
    }

    private static void createOne() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum1);
        //patient#1, Lisa Fête"
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"26","MOD",107.6,"X",provNum));
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "X";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
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
        Patient pat = Patients.getPat(PatientTC.PatNum1);
        //patient#1, Lisa Fête"
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"25","MIV",107.6,"X",provNum));
        //impossible surfaces
        proc = ProcTC.AddProc("27211", pat.PatNum, new DateTime(1999, 1, 1), "24", "", 450, "X", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "24", "", 238, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        proc = ProcTC.AddProc("27213", pat.PatNum, new DateTime(1999, 1, 1), "26", "", 450, "E", provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "26", "", 210, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procLab = ProcTC.AddProc("99222", pat.PatNum, new DateTime(1999, 1, 1), "26", "", 35, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        procList.Add(ProcTC.AddProc("32222", pat.PatNum, new DateTime(1999, 1, 1), "36", "", 65, "X", provNum));
        procList.Add(ProcTC.addProc("39202",pat.PatNum,new DateTime(1999, 1, 1),"36","",67.5,"X",provNum));
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createThree() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum2);
        //patient#2, John Smith
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"46","DOV",107.6,"X",provNum));
        proc = ProcTC.addProc("56112",pat.PatNum,new DateTime(1999, 1, 1),"","L",217.2,"S",provNum);
        //lower
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 315, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
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
        claim.CanadianDateInitialLower = new DateTime(1984, 4, 7);
        claim.CanadianMandProsthMaterial = 4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createFour() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum4);
        //patient#4, John Smith, Jr.
        //Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21113",pat.PatNum,new DateTime(1999, 1, 1),"52","MIV",107.6,"A",provNum));
        //the date in the script is a typo.
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        pat.SchoolName = "Wilson Elementary School";
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = new DateTime(1998, 11, 2);
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createFive() throws Exception {
        long provNum = ProviderC.getListShort()[1].ProvNum;
        //dentist#2
        Patient pat = Patients.getPat(PatientTC.PatNum5);
        //patient#5, Bob Howard
        //Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"26","MOD",107.6,"A",provNum));
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "081234500";
        //missing from documentation
        claim.CanadianReferralReason = 4;
        pat.SchoolName = "";
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSix() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum5);
        //patient#5, Bob Howard
        //Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",37.5,"X",provNum));
        //wrong code in documentation
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21213",pat.PatNum,new DateTime(1999, 1, 1),"22","DIV",107.6,"X",provNum));
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "081234500";
        claim.CanadianReferralReason = 4;
        pat.SchoolName = "";
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createSeven() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum5);
        //patient#5, Bob Howard
        //Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01202",pat.PatNum,new DateTime(1999, 1, 1),"","",37.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        procList.Add(ProcTC.addProc("21213",pat.PatNum,new DateTime(1999, 1, 1),"22","DIV",107.6,"X",provNum));
        //wrong code in documentation
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "081234500";
        claim.CanadianReferralReason = 4;
        pat.SchoolName = "";
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "PD78901234";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createEight() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum6);
        //patient#6, Martha West
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.addProc("67211",pat.PatNum,new DateTime(1999, 1, 1),"10","",450.6,"X",provNum);
        procList.Add(proc);
        procLab = ProcTC.addProc("99111",pat.PatNum,new DateTime(1999, 1, 1),"","",487.3,"",provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreateClaim(pat, procList, provNum);
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
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createNine() throws Exception {
        long provNum = ProviderC.getListShort()[1].ProvNum;
        //dentist#2
        Patient pat = Patients.getPat(PatientTC.PatNum7);
        //patient#7, Madeleine Arpege
        Procedure proc;
        Procedure procLab;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.addProc("67301",pat.PatNum,new DateTime(1999, 1, 1),"11","",412.6,"X",provNum);
        procList.Add(proc);
        procLab = ProcTC.AddProc("99111", pat.PatNum, new DateTime(1999, 1, 1), "", "", 380, "", provNum);
        ProcTC.attachLabProc(proc.ProcNum,procLab);
        Claim claim = CreateClaim(pat, procList, provNum);
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
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = true;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createTen() throws Exception {
        long provNum = ProviderC.getListShort()[1].ProvNum;
        //dentist#2
        Patient pat = Patients.getPat(PatientTC.PatNum8);
        //patient#8, Fred Jones
        Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"X",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"X",provNum));
        proc = ProcTC.AddProc("21222", pat.PatNum, new DateTime(1999, 1, 1), "15", "MD", 102, "X", provNum);
        procList.Add(proc);
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "X";
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createEleven() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum9);
        //patient#9, Fred Smith
        Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"",provNum));
        proc = ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"26","MOD",107.6,"",provNum);
        procList.Add(proc);
        Claim claim = CreateClaim(pat, procList, provNum);
        claim.CanadianMaterialsForwarded = "";
        //billing prov already handled
        claim.CanadianReferralProviderNum = "";
        claim.CanadianReferralReason = 0;
        //pat.SchoolName
        //assignBen can't be set here because it changes per claim in the scripts
        claim.AccidentDate = DateTime.MinValue;
        claim.PreAuthString = "";
        claim.CanadianIsInitialUpper = "Y";
        //Example documentation suggests 'X', but c11.txt and the test environment suggest this value should be 'Y', with a blank date. Very strange.
        claim.CanadianDateInitialUpper = DateTime.MinValue;
        claim.CanadianIsInitialLower = "X";
        claim.CanadianDateInitialLower = DateTime.MinValue;
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static void createTwelve() throws Exception {
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist#1
        Patient pat = Patients.getPat(PatientTC.PatNum9);
        //patient#9, Fred Smith
        Procedure proc;
        List<Procedure> procList = new List<Procedure>();
        procList.Add(ProcTC.addProc("01201",pat.PatNum,new DateTime(1999, 1, 1),"","",27.5,"",provNum));
        procList.Add(ProcTC.addProc("02102",pat.PatNum,new DateTime(1999, 1, 1),"","",87.25,"",provNum));
        proc = ProcTC.addProc("21223",pat.PatNum,new DateTime(1999, 1, 1),"27","MOD",107.6,"",provNum);
        procList.Add(proc);
        Claim claim = CreateClaim(pat, procList, provNum);
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
        //claim.CanadianMandProsthMaterial=4;
        claim.IsOrtho = false;
        Claims.update(claim);
        ClaimNums.Add(claim.ClaimNum);
    }

    private static Claim createClaim(Patient pat, List<Procedure> procList, long provTreat) throws Exception {
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
        List<ClaimProc> claimProcList = ClaimProcs.refresh(pat.PatNum);
        List<Procedure> procsForPat = Procedures.refresh(pat.PatNum);
        InsSub sub = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, 1), subList);
        InsPlan insPlan = InsPlans.GetPlan(sub.PlanNum, planList);
        InsSub sub2 = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, 2), subList);
        InsPlan insPlan2 = null;
        if (sub2.InsSubNum != 0)
        {
            insPlan2 = InsPlans.GetPlan(sub2.PlanNum, planList);
        }
         
        Claim claim = new Claim();
        Claims.insert(claim);
        //to retreive a key for new Claim.ClaimNum
        claim.PatNum = pat.PatNum;
        claim.DateService = procList[0].ProcDate;
        claim.DateSent = DateTime.Today;
        claim.ClaimStatus = "W";
        claim.PlanNum = insPlan.PlanNum;
        if (insPlan2 != null)
        {
            claim.PlanNum2 = insPlan2.PlanNum;
        }
         
        claim.InsSubNum = PatPlans.GetInsSubNum(patPlanList, 1);
        claim.InsSubNum2 = PatPlans.GetInsSubNum(patPlanList, 2);
        claim.PatRelat = PatPlans.GetRelat(patPlanList, 1);
        claim.PatRelat2 = PatPlans.GetRelat(patPlanList, 2);
        //if(ordinal==1) {
        claim.ClaimType = "P";
        //}
        //else {
        //	claim.ClaimType="S";
        //}
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

    public static String run(int scriptNum, String responseExpected, String responseTypeExpected, Claim claim, boolean showForms) throws Exception {
        String retVal = "";
        ClaimSendQueueItem queueItem = Claims.GetQueueList(claim.ClaimNum, claim.ClinicNum, 0)[0];
        Eclaims.getMissingData(queueItem);
        //,out warnings);
        if (!StringSupport.equals(queueItem.MissingData, ""))
        {
            return "Cannot send claim until missing data is fixed:\r\n" + queueItem.MissingData + "\r\n";
        }
         
        long etransNum = Canadian.sendClaim(queueItem,showForms);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseType = formData.getValue("A04");
        if (!StringSupport.equals(responseType, responseTypeExpected))
        {
            return "Form type should be " + responseTypeExpected + "\r\n";
        }
         
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, responseExpected))
        {
            return "G05 should be " + responseExpected + "\r\n";
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
         
        retVal += "Claim #" + scriptNum.ToString() + " successful.\r\n";
        return retVal;
    }

    public static String runOne(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[0]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return run(1,"C","11",claim,showForms);
    }

    public static String runTwo(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[1]);
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(2,"","21",claim,showForms);
    }

    public static String runThree(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[2]);
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return run(3,"","21",claim,showForms);
    }

    //Even though the test says 1, the example message uses 2
    //expecting EOB
    public static String runFour(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[3]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return run(4,"","21",claim,showForms);
    }

    //expecting EOB
    public static String runFive(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[4]);
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return run(5,"C","11",claim,showForms);
    }

    public static String runSix(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[5]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(6,"R","11",claim,showForms);
    }

    public static String runSeven(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[6]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(7,"","21",claim,showForms);
    }

    public static String runEight(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[7]);
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 2);
        return run(8,"","21",claim,showForms);
    }

    public static String runNine(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[8]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(9,"","21",claim,showForms);
    }

    //test the result of the COB
    public static String runTen(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[9]);
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(10,"","21",claim,showForms);
    }

    //test the result of the COB
    public static String runEleven(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[10]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        String oldVersion = CarrierTC.setCDAnetVersion(claim.PlanNum,"02");
        String retval = run(11,"","11",claim,showForms);
        CarrierTC.setCDAnetVersion(claim.PlanNum,oldVersion);
        return retval;
    }

    public static String runTwelve(boolean showForms) throws Exception {
        Claim claim = Claims.GetClaim(ClaimNums[11]);
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        String oldVersion = CarrierTC.setCDAnetVersion(claim.PlanNum,"02");
        String retval = run(12,"","21",claim,showForms);
        CarrierTC.setCDAnetVersion(claim.PlanNum,oldVersion);
        return retval;
    }

}


