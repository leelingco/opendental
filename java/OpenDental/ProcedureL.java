//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.AutomationL;
import OpenDental.Lan;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.OrionProcs;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Programs;

public class ProcedureL   
{
    public static void setCompleteInAppt(Appointment apt, List<InsPlan> PlanList, List<PatPlan> patPlans, long siteNum, int patientAge, List<InsSub> subList) throws Exception {
        List<Procedure> procsInAppt = Procedures.getProcsForSingle(apt.AptNum,false);
        Procedures.SetCompleteInAppt(apt, PlanList, patPlans, siteNum, patientAge, procsInAppt, subList);
        if (Programs.getUsingOrion())
        {
            OrionProcs.SetCompleteInAppt(procsInAppt);
        }
         
        //automation
        List<String> procCodes = new List<String>();
        for (int i = 0;i < procsInAppt.Count;i++)
        {
            procCodes.Add(ProcedureCodes.GetStringProcCode(procsInAppt[i].CodeNum));
        }
        AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodes, apt.PatNum);
    }

    /**
    * Returns empty string if no duplicates, otherwise returns duplicate procedure information.  In all places where this is called, we are guaranteed to have the eCW bridge turned on.  So this is an eCW peculiarity rather than an HL7 restriction.  Other HL7 interfaces will not be checking for duplicate procedures unless we intentionally add that as a feature later.
    */
    public static String procsContainDuplicates(List<Procedure> procs) throws Exception {
        String info = "";
        List<Procedure> procsChecked = new List<Procedure>();
        for (int i = 0;i < procs.Count;i++)
        {
            Procedure proc = procs[i];
            ProcedureCode procCode = ProcedureCodes.GetProcCode(procs[i].CodeNum);
            String procCodeStr = procCode.ProcCode;
            if (procCodeStr.Length > 5 && procCodeStr.StartsWith("D"))
            {
                procCodeStr = procCodeStr.Substring(0, 5);
            }
             
            for (int j = 0;j < procsChecked.Count;j++)
            {
                Procedure procDup = procsChecked[j];
                ProcedureCode procCodeDup = ProcedureCodes.GetProcCode(procsChecked[j].CodeNum);
                String procCodeDupStr = procCodeDup.ProcCode;
                if (procCodeDupStr.Length > 5 && procCodeDupStr.StartsWith("D"))
                {
                    procCodeDupStr = procCodeDupStr.Substring(0, 5);
                }
                 
                if (!StringSupport.equals(procCodeDupStr, procCodeStr))
                {
                    continue;
                }
                 
                if (!StringSupport.equals(procDup.ToothNum, proc.ToothNum))
                {
                    continue;
                }
                 
                if (!StringSupport.equals(procDup.ToothRange, proc.ToothRange))
                {
                    continue;
                }
                 
                if (procDup.ProcFee != proc.ProcFee)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(procDup.Surf, proc.Surf))
                {
                    continue;
                }
                 
                if (!StringSupport.equals(info, ""))
                {
                    info += ", ";
                }
                 
                info += procCodeDupStr;
            }
            procsChecked.Add(proc);
        }
        if (!StringSupport.equals(info, ""))
        {
            info = Lan.g("ProcedureL","Duplicate procedures") + ": " + info;
        }
         
        return info;
    }

}


