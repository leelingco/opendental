//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;


/**
* During the ClaimProc.ComputeBaseEst() and related sections, this holds historical payment information for one procedure or an adjustment to insurance benefits from patplan.
*/
public class ClaimProcHist   
{
    public DateTime ProcDate = new DateTime();
    public String StrProcCode = new String();
    /**
    * Insurance paid or est, depending on the status.
    */
    public double Amount = new double();
    /**
    * Deductible paid or est.
    */
    public double Deduct = new double();
    /**
    * Because a list can store info for an entire family.
    */
    public long PatNum = new long();
    /**
    * Because a list can store info about multiple plans.
    */
    public long PlanNum = new long();
    /**
    * So that we can exclude history from the claim that we are in.
    */
    public long ClaimNum = new long();
    /**
    * Only 4 statuses get used anyway.  This helps us filter the pending items sometimes.
    */
    public OpenDentBusiness.ClaimProcStatus Status = OpenDentBusiness.ClaimProcStatus.NotReceived;
    /**
    * 
    */
    public long InsSubNum = new long();
    /**
    * This is needed to filter out primary histList entries on secondary claims.
    */
    public long ProcNum = new long();
    public String toString() {
        try
        {
            return StrProcCode + " " + Status.ToString() + " " + Amount.ToString() + " ded:" + Deduct.ToString();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


