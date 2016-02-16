//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ClaimProcStatus
{
    /**
    * Claimproc Status.  The status must generally be the same as the claim, although it is sometimes not strictly enforced.0: For claims that have been created or sent, but have not been received.
    */
    NotReceived,
    /**
    * 1: For claims that have been received.
    */
    Received,
    /**
    * 2: For preauthorizations.
    */
    Preauth,
    /**
    * 3: The only place that this status is used is to make adjustments to benefits from the coverage window.  It is never attached to a claim.
    */
    Adjustment,
    /**
    * 4:This differs from Received only slightly.  It's for additional payments on procedures already received.  Most fields are blank.
    */
    Supplemental,
    /**
    * 5: CapClaim is used when you want to send a claim to a capitation insurance company.  These are similar to Supplemental in that there will always be a duplicate claimproc for a procedure. The first claimproc tracks the copay and writeoff, has a status of CapComplete, and is never attached to a claim. The second claimproc has status of CapClaim.
    */
    CapClaim,
    /**
    * 6: Estimates have replaced the fields that were in the procedure table.  Once a procedure is complete, the claimprocstatus will still be Estimate.  An Estimate can be attached to a claim and status gets changed to NotReceived.
    */
    Estimate,
    /**
    * 7: For capitation procedures that are complete.  This replaces the old procedurelog.CapCoPay field. This stores the copay and writeoff amounts.  The copay is only there for reference, while it is the writeoff that actually affects the balance. Never attached to a claim. If procedure is TP, then status will be CapEstimate.  Only set to CapComplete if procedure is Complete.
    */
    CapComplete,
    /**
    * 8: For capitation procedures that are still estimates rather than complete.  When procedure is completed, this can be changed to CapComplete, but never to anything else.
    */
    CapEstimate
}

