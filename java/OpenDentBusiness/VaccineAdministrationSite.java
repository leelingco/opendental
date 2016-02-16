//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineAdministrationSite
{
    /**
    * Exported in HL7 RXR-2.  Corresponds to HL7 table 0163 (guide page 201).0 - No code.  Default.  Not sent in HL7 messages.  Used in UI only.
    */
    None,
    /**
    * 1- Code LT
    */
    LeftThigh,
    /**
    * 2 - Code LA
    */
    LeftArm,
    /**
    * 3 - Code LD
    */
    LeftDeltoid,
    /**
    * 4 - Code LG
    */
    LeftGluteousMedius,
    /**
    * 5 - Code LVL
    */
    LeftVastusLateralis,
    /**
    * 6 - Code LLFA
    */
    LeftLowerForearm,
    /**
    * 7 - Code RA
    */
    RightArm,
    /**
    * 8 - Code RT
    */
    RightThigh,
    /**
    * 9 - Code RVL
    */
    RightVastusLateralis,
    /**
    * 10 - Code RG
    */
    RightGluteousMedius,
    /**
    * 11 - Code RD
    */
    RightDeltoid,
    /**
    * 12 - Code RLFA
    */
    RightLowerForearm
}

