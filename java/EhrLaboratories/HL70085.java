//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70085
{
    /**
    * Observation Result Status.  OID:2.16.840.1.113883.12.85  HL70369 code:HL70085.  Source phinvads.cdc.gov0 - Deletes the OBX record
    */
    D,
    /**
    * 1 - Final results; Can only be changed with a corrected result.
    */
    F,
    /**
    * 2 - Not asked; used to affirmatively document that the observation identified in the OBX was not sought when the universal service ID in OBR-4 implies that it would be sought.
    */
    N,
    /**
    * 3 - Order detail description only (no result)
    */
    O,
    /**
    * 4 - Partial results
    */
    S,
    /**
    * 5 - Post original as wrong, e.g., transmitted for wrong patient
    */
    W,
    /**
    * 6 - Preliminary results
    */
    P,
    /**
    * 7 - Record coming over is a correction and thus replaces a final result
    */
    C,
    /**
    * 8 - Results cannot be obtained for this observation
    */
    X,
    /**
    * 9 - Results entered -- not verified
    */
    R,
    /**
    * 10 - Results status change to final without retransmitting results already sent as _preliminary._  E.g., radiology changes status from preliminary to final
    */
    U,
    /**
    * 11 - Specimen in lab; results pending
    */
    I
}

