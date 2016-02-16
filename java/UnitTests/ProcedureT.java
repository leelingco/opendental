//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.Benefit;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;

public class ProcedureT   
{
    /**
    * Returns the proc
    */
    public static Procedure createProcedure(Patient pat, String procCodeStr, OpenDentBusiness.ProcStat procStatus, String toothNum, double procFee) throws Exception {
        Procedure proc = new Procedure();
        proc.CodeNum = ProcedureCodes.getCodeNum(procCodeStr);
        proc.PatNum = pat.PatNum;
        proc.ProcDate = DateTime.Today;
        proc.ProcStatus = procStatus;
        proc.ProvNum = pat.PriProv;
        proc.ProcFee = procFee;
        proc.ToothNum = toothNum;
        proc.Prosthesis = "I";
        Procedures.insert(proc);
        return proc;
    }

    /*public static void SetToothNum(Procedure procedure,string toothNum){
    			Procedure oldProcedure=procedure.Copy();
    			procedure.ToothNum=toothNum;
    			Procedures.Update(procedure,oldProcedure);
    		}*/
    public static void setPriority(Procedure procedure, int priority) throws Exception {
        Procedure oldProcedure = procedure.copy();
        procedure.Priority = DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][priority].DefNum;
        Procedures.update(procedure,oldProcedure);
    }

    public static void setComplete(Procedure proc, Patient pat, List<InsPlan> planList, List<PatPlan> patPlanList, List<ClaimProc> claimProcList, List<Benefit> benefitList, List<InsSub> subList) throws Exception {
        Procedure procOld = proc.copy();
        ProcedureCode procCode = ProcedureCodes.getProcCode(proc.CodeNum);
        proc.DateEntryC = DateTime.Now;
        proc.ProcStatus = OpenDentBusiness.ProcStat.C;
        Procedures.update(proc,procOld);
        Procedures.ComputeEstimates(proc, proc.PatNum, claimProcList, false, planList, patPlanList, benefitList, pat.getAge(), subList);
    }

}


