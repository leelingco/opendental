//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;

public class ClaimProcT   
{
    public static void addInsUsedAdjustment(long patNum, long planNum, double amtPaid, long subNum, double dedApplied) throws Exception {
        ClaimProc cp = new ClaimProc();
        cp.PatNum = patNum;
        cp.PlanNum = planNum;
        cp.InsSubNum = subNum;
        cp.ProcDate = DateTime.Today;
        cp.Status = OpenDentBusiness.ClaimProcStatus.Adjustment;
        cp.InsPayAmt = amtPaid;
        cp.DedApplied = dedApplied;
        ClaimProcs.insert(cp);
    }

    /**
    * This tells the calculating logic that insurance paid on a procedure.  It avoids the creation of an actual claim.
    */
    public static void addInsPaid(long patNum, long planNum, long procNum, double amtPaid, long subNum, double dedApplied, double writeOff) throws Exception {
        ClaimProc cp = new ClaimProc();
        cp.ProcNum = procNum;
        cp.PatNum = patNum;
        cp.PlanNum = planNum;
        cp.InsSubNum = subNum;
        cp.InsPayAmt = amtPaid;
        cp.DedApplied = dedApplied;
        cp.WriteOff = writeOff;
        cp.Status = OpenDentBusiness.ClaimProcStatus.Received;
        cp.DateCP = DateTime.Today;
        cp.ProcDate = DateTime.Today;
        ClaimProcs.insert(cp);
    }

}


