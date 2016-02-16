//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Links procedures to claims.  Also links ins payments to procedures or claims.  Also used for estimating procedures even if no claim yet.  Warning: One proc might be linked twice to a given claim if insurance made two payments.  Many of the important fields are actually optional.  For instance, ProcNum is only required if itemizing ins payment, and ClaimNum is blank if Status=adjustment,cap,or estimate.
*/
public class ClaimProc  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClaimProcNum = new long();
    /**
    * FK to procedurelog.ProcNum.
    */
    public long ProcNum = new long();
    /**
    * FK to claim.ClaimNum.
    */
    public long ClaimNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to provider.ProvNum.  At least one office has been manually setting their claimproc provider to a different provider when entering payments as a means to track provider income.  So we can't force this to always be the same as the procedure.  We also don't want to change any historical data, so only synched when setting appt complete or if an estimate.  Right now on e-claims, we are sending the prov from the procedure.  When we have time, we will change e-claims to send the proc from the ClaimProc.
    */
    public long ProvNum = new long();
    /**
    * Fee billed to insurance. Might not be the same as the actual fee.  The fee billed can be different than the actual procedure.  For instance, if you have set the insurance plan to bill insurance using UCR fees, then this field will contain the UCR fee instead of the fee that the patient was charged.
    */
    public double FeeBilled = new double();
    /**
    * Only if attached to a claim.  Actual amount this carrier is expected to pay, after taking everything else into account. Considers annual max, override, percentAmt, copayAmt, deductible, etc. This estimate is computed automatically when sent to ins.
    */
    public double InsPayEst = new double();
    /**
    * 0 if blank.  Deductible applied to this procedure only. Only for procedures attached to claims.  Otherwise, the DedEst and DedEstOverride are used.
    */
    public double DedApplied = new double();
    /**
    * Enum:ClaimProcStatus .
    */
    public OpenDentBusiness.ClaimProcStatus Status = OpenDentBusiness.ClaimProcStatus.NotReceived;
    /**
    * Amount insurance actually paid.
    */
    public double InsPayAmt = new double();
    /**
    * The remarks that insurance sends in the EOB about procedures.
    */
    public String Remarks = new String();
    /**
    * FK to claimpayment.ClaimPaymentNum(the insurance check).
    */
    public long ClaimPaymentNum = new long();
    /**
    * FK to insplan.PlanNum
    */
    public long PlanNum = new long();
    /**
    * This is the date that is used for payment reports and tracks the payment date.  Always exactly matches the date of the ClaimPayment it's attached to.  See the note under Ledgers.ComputePayments.  This will eventually not be used for aging. The ProcDate will instead be used. See ProcDate.
    */
    public DateTime DateCP = new DateTime();
    /**
    * Amount not covered by ins which is written off.  The writeoff estimate goes in a different column.
    */
    public double WriteOff = new double();
    /**
    * The procedure code that was sent to insurance. This is not necessarily the usual procedure code.  It will already have been trimmed to 5 char if it started with "D", or it could be the alternate code.  Not allowed to be blank if it is procedure.
    */
    public String CodeSent = new String();
    /**
    * The allowed fee (not the override) is a complex calculation which is performed on the fly in Procedure.ComputeEstimates/ClaimProc.ComputeBaseEst.  It is the amount that the percentage is based on.  If this carrier has a lower UCR than the office, then the allowed fee is where that is handled.  It can be pulled from an allowed fee schedule.  It is also where substitutions for posterior composites are handled.  The AllowedOverride allows the user to override the calculation.  -1 indicates blank.  A new use of this field is for when entering insurance payments.  On the eob, it will tell you what the allowed/UCR fee is.  The user will now be able to enter this information into the AllowedOverride field.  They will simultaneously pass the info to the allowed fee schedule.  AllowedOverride is never changed automatically by the program except to sometimes set it to -1 if NoBillIns.
    */
    public double AllowedOverride = new double();
    /**
    * -1 if blank.  Otherwise a number between 0 and 100.  The percentage that insurance pays on this procedure, as determined from insurance categories. Not user editable.
    */
    public int Percentage = new int();
    /**
    * -1 if blank.  Otherwise a number between 0 and 100.  Can only be changed by user.
    */
    public int PercentOverride = new int();
    /**
    * -1 if blank. Calculated automatically. User cannot edit but can use CopayOverride instead.  Opposite of InsEst, because this is the patient portion estimate.  Two different uses: 1. For capitation, this automates calculation of writeoff. 2. For any other insurance, it gets deducted during calculation as shown in the edit window. Neither use directly affects patient balance.
    */
    public double CopayAmt = new double();
    /**
    * Set to true to not bill to this insurance plan.
    */
    public boolean NoBillIns = new boolean();
    /**
    * -1 if blank. The amount paid or estimated to be paid by another insurance.  This amount is then subtracted from what the current insurance would pay.  When running the calculation and considering other claimprocs, it will ignore any patPlan with a higher ordinal.  So, always blank for primary claims.  User cannot edit, but can use PaidOtherInsOverride.
    */
    public double PaidOtherIns = new double();
    /**
    * Always has a value. Used in TP, etc. The base estimate is the ((fee or allowedOverride)-Copay) x (percentage or percentOverride). Does not include all the extras like ded, annualMax,and paidOtherIns that InsEstTotal holds.  BaseEst cannot be overridden by the user.  Instead, the following fields can be manipulated: allowedOverride, CopayOverride, PercentOverride.
    */
    public double BaseEst = new double();
    /**
    * -1 if blank.  See description of CopayAmt.  This lets the user set a copay that will never be overwritten by automatic calculations.
    */
    public double CopayOverride = new double();
    /**
    * Date of the procedure.  Currently only used for tracking annual insurance benefits remaining. Important in Adjustments to benefits.  For total claim payments, MUST be the date of the procedures to correctly figure benefits.  Will eventually transition to use this field to actually calculate aging.  See the note under Ledgers.ComputePayments.
    */
    public DateTime ProcDate = new DateTime();
    /**
    * Date that it was changed to status received or supplemental.  It is usually attached to a claimPayment at that point, but not if user forgets.  This is still the date that it becomes important financial data.  Only applies if Received or Supplemental.  Otherwise, the date is disregarded.  User may never edit. Important in audit trail.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * Assigned when claim is created as a way to order the procs showing on a claim.  Really only used in Canadian claims for now as F07.
    */
    public byte LineNumber = new byte();
    /**
    * -1 if blank.  Not sure why we need to allow -1.  Calculated automatically.  User cannot edit, but can use DedEstOverride instead.
    */
    public double DedEst = new double();
    /**
    * -1 if blank.  Overrides the DedEst value.
    */
    public double DedEstOverride = new double();
    /**
    * Always has a value.  BaseEst-(DedEst or DedEstOverride)-PaidOtherIns-OverAnnualMax.  User cannot edit, but can instead use InsEstTotalOverride.
    */
    public double InsEstTotal = new double();
    /**
    * -1 if blank.  Overrides the InsEstTotal value.
    */
    public double InsEstTotalOverride = new double();
    /**
    * -1 if blank.  Overrides the PaidOtherIns value.
    */
    public double PaidOtherInsOverride = new double();
    /**
    * An automatically generated note that displays information about over max, exclusions, and other limitations for which there are no fields.  Only applies to estimate.  Once it's attached to a claim, similar information can go in the remarks field.
    */
    public String EstimateNote = new String();
    /**
    * -1 if blank.  The estimated writeoff as calculated by OD.  Usually only used for PPOs.
    */
    public double WriteOffEst = new double();
    /**
    * -1 if blank.  Overrides WriteOffEst.  Usually only used for PPOs.
    */
    public double WriteOffEstOverride = new double();
    /**
    * FK to clinic.ClinicNum.  Can be zero.  No user interface for editing.  Forced to always be the same as the procedure, or if no procedure, then the claim.
    */
    public long ClinicNum = new long();
    /**
    * FK to inssub.InsSubNum.
    */
    public long InsSubNum = new long();
    /**
    * 1-indexed.  Allows user to sort the order of payments on an EOB.  All claimprocs for a payment will have the same PaymentRow value.
    */
    public int PaymentRow = new int();
    /**
    * Not a database column.  Used to help manage passing lists around.
    */
    public boolean DoDelete = new boolean();
    /**
    * Returns a copy of this ClaimProc.
    */
    public ClaimProc copy() throws Exception {
        return (ClaimProc)MemberwiseClone();
    }

    public boolean equals(Object obj) {
        try
        {
            ClaimProc cp = (ClaimProc)obj;
            if (ClaimProcNum != cp.ClaimProcNum || ProcNum != cp.ProcNum || ClaimNum != cp.ClaimNum || PatNum != cp.PatNum || ProvNum != cp.ProvNum || FeeBilled != cp.FeeBilled || InsPayEst != cp.InsPayEst || DedApplied != cp.DedApplied || Status != cp.Status || InsPayAmt != cp.InsPayAmt || !StringSupport.equals(Remarks, cp.Remarks) || ClaimPaymentNum != cp.ClaimPaymentNum || PlanNum != cp.PlanNum || DateCP != cp.DateCP || WriteOff != cp.WriteOff || !StringSupport.equals(CodeSent, cp.CodeSent) || AllowedOverride != cp.AllowedOverride || Percentage != cp.Percentage || PercentOverride != cp.PercentOverride || CopayAmt != cp.CopayAmt || NoBillIns != cp.NoBillIns || PaidOtherIns != cp.PaidOtherIns || BaseEst != cp.BaseEst || CopayOverride != cp.CopayOverride || ProcDate != cp.ProcDate || DateEntry != cp.DateEntry || LineNumber != cp.LineNumber || DedEst != cp.DedEst || DedEstOverride != cp.DedEstOverride || InsEstTotal != cp.InsEstTotal || InsEstTotalOverride != cp.InsEstTotalOverride || PaidOtherInsOverride != cp.PaidOtherInsOverride || !StringSupport.equals(EstimateNote, cp.EstimateNote) || WriteOffEst != cp.WriteOffEst || WriteOffEstOverride != cp.WriteOffEstOverride || ClinicNum != cp.ClinicNum)
            {
                return false;
            }
             
            return true;
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

    public String toString() {
        try
        {
            return Status.ToString() + ProcDate.ToShortDateString() + " est:" + InsEstTotal.ToString() + " ded:" + DedEst.ToString();
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

    public int hashCode() {
        try
        {
            return super.GetHashCode();
        }
        catch (RuntimeException __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        catch (Exception __dummyCatchVar2)
        {
            throw new RuntimeException(__dummyCatchVar2);
        }
    
    }

}


