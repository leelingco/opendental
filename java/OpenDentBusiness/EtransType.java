//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum EtransType
{
    /**
    * The _CA of some types should get stripped off when displaying to users.0 X12-837.  Should we differenitate between different kinds of 837s and 4010 vs 5010?
    */
    ClaimSent,
    /**
    * 1 claim
    */
    ClaimPrinted,
    /**
    * 2 Canada. Type 01
    */
    Claim_CA,
    /**
    * 3 Renaissance
    */
    Claim_Ren,
    /**
    * 4 Canada. Type 11
    */
    ClaimAck_CA,
    /**
    * 5 Canada. Type 21
    */
    ClaimEOB_CA,
    /**
    * 6 Canada. Type 08
    */
    Eligibility_CA,
    /**
    * 7 Canada. Type 18. V02 type 10.
    */
    EligResponse_CA,
    /**
    * 8 Canada. Type 02
    */
    ClaimReversal_CA,
    /**
    * 9 Canada. Type 03
    */
    Predeterm_CA,
    /**
    * 10 Canada. Type 04
    */
    RequestOutstand_CA,
    /**
    * 11 Canada. Type 05
    */
    RequestSumm_CA,
    /**
    * 12 Canada. Type 06
    */
    RequestPay_CA,
    /**
    * 13 Canada. Type 07
    */
    ClaimCOB_CA,
    /**
    * 14 Canada. Type 12
    */
    ReverseResponse_CA,
    /**
    * 15 Canada. Type 13
    */
    PredetermAck_CA,
    /**
    * 16 Canada. Type 23
    */
    PredetermEOB_CA,
    /**
    * 17 Canada. Type 14
    */
    OutstandingAck_CA,
    /**
    * 18 Canada. Type 24
    */
    EmailResponse_CA,
    /**
    * 19 Canada. Type 16
    */
    PaymentResponse_CA,
    /**
    * 20 Canada. Type 15
    */
    SummaryResponse_CA,
    /**
    * 21 Ack from clearinghouse. X12-997.
    */
    Acknowledge_997,
    /**
    * 22 X12-277. Unsolicited claim status notification.
    */
    StatusNotify_277,
    /**
    * 23 Text report from clearinghouse in human readable format.
    */
    TextReport,
    /**
    * 24 X12-270.
    */
    BenefitInquiry270,
    /**
    * 25 X12-271
    */
    BenefitResponse271,
    /**
    * 26 When a Canadian message is sent, and an error comes back instead of a message.  This stores information about the error.  The etrans with this type is attached it to the original etrans as an ack.
    */
    AckError,
    /**
    * 27 X12-835. Not used yet.
    */
    ERA_835,
    /**
    * 28 Ack from clearinghouse. X12-999.
    */
    Acknowledge_999,
    /**
    * 29 Simple and generic ack from clearinghouse which is used to replace 997s, 999s, or 277s.
    */
    Ack_Interchange
}

