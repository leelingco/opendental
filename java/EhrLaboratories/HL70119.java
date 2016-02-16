//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70119
{
    /**
    * Order Control Code.  We only use RE.  OID:2.16.840.1.113883.12.119  HL70369 code:HL70119.  Source phinvads.cdc.gov0 - Cancel order/service request
    */
    CA,
    /**
    * 1 - Canceled as requested
    */
    CR,
    /**
    * 2 - Change order/service request
    */
    XO,
    /**
    * 3 - Changed as requested
    */
    XR,
    /**
    * 4 - Child order/service
    */
    CH,
    /**
    * 5 - Combined result
    */
    CN,
    /**
    * 6 - Data errors
    */
    DE,
    /**
    * 7 - Discontinue order/service request
    */
    DC,
    /**
    * 8 - Discontinued as requested
    */
    DR,
    /**
    * 9 - Hold order request
    */
    HD,
    /**
    * 10 - Link order/service to patient care problem or goal
    */
    LI,
    /**
    * 11 - New order/service
    */
    NW,
    /**
    * 12 - Notification of order for outside dispense
    */
    OP,
    /**
    * 13 - Notification of replacement order for outside dispense
    */
    PY,
    /**
    * 14 - Number assigned
    */
    NA,
    /**
    * 15 - Observations/Performed Service to follow
    */
    RE,
    /**
    * 16 - On hold as requested
    */
    HR,
    ///<summary>17 - Order/service accepted & OK</summary>
    OK,
    /**
    * 18 - Order/service canceled
    */
    OC,
    /**
    * 19 - Order/service changed, unsol.
    */
    XX,
    /**
    * 20 - Order/service discontinued
    */
    OD,
    /**
    * 21 - Order/service held
    */
    OH,
    /**
    * 22 - Order/service refill request approval
    */
    AF,
    /**
    * 23 - Order/service refill request denied
    */
    DF,
    /**
    * 24 - Order/service refilled as requested
    */
    OF,
    /**
    * 25 - Order/service refilled, unsolicited
    */
    FU,
    /**
    * 26 - Order/service released
    */
    OE,
    /**
    * 27 - Order/service replace request
    */
    RP,
    /**
    * 28 - Parent order/service
    */
    PA,
    /**
    * 29 - Previous Results with new order/service
    */
    PR,
    /**
    * 30 - Refill order/service request
    */
    RF,
    /**
    * 31 - Release previous hold
    */
    RL,
    /**
    * 32 - Released as requested
    */
    OR,
    /**
    * 33 - Replaced as requested
    */
    RQ,
    /**
    * 34 - Replaced unsolicited
    */
    RU,
    /**
    * 35 - Replacement order
    */
    RO,
    /**
    * 36 - Request received
    */
    RR,
    /**
    * 37 - Response to send order/service status request
    */
    SR,
    /**
    * 38 - Send order/service number
    */
    SN,
    /**
    * 39 - Send order/service status request
    */
    SS,
    /**
    * 40 - Status changed
    */
    SC,
    /**
    * 41 - Unable to accept order/service
    */
    UA,
    /**
    * 42 - Unable to cancel
    */
    UC,
    /**
    * 43 - Unable to change
    */
    UX,
    /**
    * 44 - Unable to discontinue
    */
    UD,
    /**
    * 45 - Unable to put on hold
    */
    UH,
    /**
    * 46 - Unable to refill
    */
    UF,
    /**
    * 46 - Unable to release
    */
    UR,
    /**
    * 47 - Unable to replace
    */
    UM,
    /**
    * 48 - Unlink order/service from patient care problem or goal
    */
    UN
}

