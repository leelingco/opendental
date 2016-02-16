//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;


public enum CanSupTransTypes
{
    /**
    * Type 23, Predetermination EOB (regular and embedded) are not included because they are not part of the testing scripts.  The three required types are not included: ClaimTransaction_01, ClaimAcknowledgement_11, and ClaimEOB_21.  Can't find specs for PredeterminationEobEmbedded.
    */
    None,
    EligibilityTransaction_08,
    EligibilityResponse_18,
    CobClaimTransaction_07,
    /**
    * ClaimAck_11 is not here because it's required by all carriers.
    */
    ClaimAckEmbedded_11e,
    /**
    * ClaimEob_21 is not here because it's required by all carriers.
    */
    ClaimEobEmbedded_21e,
    ClaimReversal_02,
    ClaimReversalResponse_12,
    PredeterminationSinglePage_03,
    PredeterminationMultiPage_03,
    PredeterminationAck_13,
    PredeterminationAckEmbedded_13e,
    RequestForOutstandingTrans_04,
    OutstandingTransAck_14,
    /**
    * Response
    */
    EmailTransaction_24,
    RequestForSummaryReconciliation_05,
    SummaryReconciliation_15,
    RequestForPaymentReconciliation_06,
    PaymentReconciliation_16
}

