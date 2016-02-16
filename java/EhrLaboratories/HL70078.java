//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70078
{
    /**
    * Abnormal Flags.  OID:2.16.840.1.113883.12.78  HL70369 code:HL70078.  Source phinvads.cdc.gov0 - Abnormal
    * Applies to non-numeric results.
    */
    A,
    /**
    * 1 - Above absolute high-off instrument scale.  Actual value is ">" but symbol cannot be used as an enum value.
    */
    _gt,
    /**
    * 2 - Above high normal
    */
    H,
    /**
    * 3 - Above upper panic limits
    */
    HH,
    /**
    * 4 - Below absolute low-off instrument scale.  Actual value is ">" but symbol cannot be used as an enum value.
    */
    _lt,
    /**
    * 5 - Below low normal
    */
    L,
    /**
    * 6 - Below lower panic limits
    */
    LL,
    /**
    * 7 - Better
    * Use when direction not relevant.
    */
    B,
    /**
    * 8 - Intermediate
    * Indicates for microbiology susceptibilities only.
    */
    I,
    /**
    * 9 - Moderately susceptible
    * Indicates for microbiology susceptibilities only
    */
    MS,
    /**
    * 10 - No range defined, or normal ranges don't apply.  Actual value is "null" but is a reserved word in c#
    */
    _null,
    /**
    * 11 - Normal
    * Applies to non-numeric results.
    */
    N,
    /**
    * 12 - Resistant
    * Indicates for microbiology susceptibilities only.
    */
    R,
    /**
    * 13 - Significant change down
    */
    D,
    /**
    * 14 - Significant change up
    */
    U,
    /**
    * 15 - Susceptible
    * Indicates for microbiology susceptibilities only.
    */
    S,
    /**
    * 16 - Very abnormal
    * Applies to non-numeric units, analogous to panic limits for numeric units.
    */
    AA,
    /**
    * 17 - Very susceptible
    * Indicates for microbiology susceptibilities only.
    */
    VS,
    /**
    * 18 - Worse
    * Use when direction not relevant.
    */
    W
}

