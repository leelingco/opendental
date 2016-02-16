//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import TestCanada.ToothInitialTC;

public class ProcTC   
{
    /**
    * The tooth number passed in should be in international format.
    */
    public static void setExtracted(String toothNumInternat, DateTime procDate, long patNum) throws Exception {
        Procedure proc = new Procedure();
        proc.CodeNum = ProcedureCodes.getCodeNum("71101");
        proc.PatNum = patNum;
        proc.ProcDate = procDate;
        proc.ToothNum = OpenDentBusiness.Tooth.fromInternat(toothNumInternat);
        proc.ProcStatus = OpenDentBusiness.ProcStat.EO;
        Procedures.insert(proc);
        ToothInitialTC.setMissing(toothNumInternat,patNum);
    }

    /**
    * Procedure will have a completed status.  For surfaces, since the scripts are faulty, pass in the exact surfaces that should be in the db, no validation will be done, and those exact same surfaces are what will go out on claim.
    */
    public static Procedure addProc(String procCode, long patNum, DateTime procDate, String toothNum, String surf, double fee, String typeCodes, long provNum) throws Exception {
        Procedure proc = new Procedure();
        ProcedureCode procedureCode = ProcedureCodes.getProcCode(procCode);
        //procnum
        proc.PatNum = patNum;
        //aptnum
        proc.CodeNum = procedureCode.CodeNum;
        proc.ProcDate = procDate;
        proc.DateTP = proc.ProcDate;
        proc.ProcFee = fee;
        System.String __dummyScrutVar0 = toothNum;
        if (__dummyScrutVar0.equals(""))
        {
            proc.ToothNum = "";
            proc.Surf = surf;
        }
        else if (__dummyScrutVar0.equals("10"))
        {
            proc.ToothNum = "";
            proc.Surf = "UR";
        }
        else if (__dummyScrutVar0.equals("20"))
        {
            proc.ToothNum = "";
            proc.Surf = "UL";
        }
        else if (__dummyScrutVar0.equals("30"))
        {
            proc.ToothNum = "";
            proc.Surf = "LL";
        }
        else if (__dummyScrutVar0.equals("40"))
        {
            proc.ToothNum = "";
            proc.Surf = "LR";
        }
        else
        {
            proc.ToothNum = OpenDentBusiness.Tooth.fromInternat(toothNum);
            proc.Surf = surf;
        }     
        //Tooth.SurfTidyFromDisplayToDb(surf,proc.ToothNum);
        //ToothRange
        proc.Priority = 0;
        proc.ProcStatus = OpenDentBusiness.ProcStat.C;
        proc.ProvNum = provNum;
        proc.Note = "";
        proc.ClinicNum = 0;
        //proc.Dx
        proc.MedicalCode = "";
        proc.BaseUnits = 0;
        proc.SiteNum = 0;
        //nextaptnum
        proc.CanadianTypeCodes = typeCodes;
        Procedures.insert(proc);
        return proc;
    }

    //if an extraction, then mark previous procs hidden.  Skip.
    //Recalls.Synch(PatCur.PatNum);//skip
    //Procedures.ComputeEstimates(proc,patNum,new List<ClaimProc>(),true,planList,patPlanList,benefitList,age);
    public static void attachLabProc(long procNum, Procedure procLab) throws Exception {
        Procedure oldProc = procLab.copy();
        procLab.ProcNumLab = procNum;
        Procedures.update(procLab,oldProc);
    }

}


