//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70123
{
    /**
    * Result Status.  OID:2.16.840.1.113883.12.123  HL70369 code:HL70123.  Source HL7 2.5.1 Labratory Reporting Interface documentation.0 - Some but not all results available.
    */
    A,
    /**
    * 1 - Correction to results.
    */
    C,
    /**
    * 2 - Final Results; results stored and verified. Can only be changed with a corrected result.
    */
    F,
    /**
    * 3 - No results available; specimen received, procedure incomplete.
    */
    I,
    /**
    * 4 - Order received; specimen not yet received.
    */
    O,
    /**
    * 5 - Preliminary: A verified early result is available, final results not yet obtained.
    */
    P,
    /**
    * 6 - Results stored; not yet verified.
    */
    R,
    /**
    * 7 - No results available; procedure scheduled but not done.
    */
    S,
    /**
    * 8 - No results available; Order canceled.
    */
    X
}

