//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum EclaimsCommBridge
{
    /**
    * Each clearinghouse can have a hard-coded comm bridge which handles all the communications of transfering the claim files to the clearinghouse/carrier.  Does not just include X12, but can include any format at all.0-No comm bridge will be activated. The claim files will be created to the specified path, but they will not be uploaded.
    */
    None,
    /**
    * 1
    */
    WebMD,
    /**
    * 2
    */
    BCBSGA,
    /**
    * 3
    */
    Renaissance,
    /**
    * 4
    */
    ClaimConnect,
    /**
    * 5
    */
    RECS,
    /**
    * 6
    */
    Inmediata,
    /**
    * 7
    */
    AOS,
    /**
    * 8
    */
    PostnTrack,
    /**
    * 9 Canadian clearinghouse.
    */
    ITRANS,
    /**
    * 10
    */
    Tesia,
    /**
    * 11
    */
    MercuryDE,
    /**
    * 12
    */
    ClaimX,
    /**
    * 13
    */
    DentiCal,
    /**
    * 14
    */
    EmdeonMedical,
    /**
    * 15 Canadian clearinghouse.
    */
    Claimstream,
    /**
    * 16 UK clearinghouse.
    */
    NHS
}

