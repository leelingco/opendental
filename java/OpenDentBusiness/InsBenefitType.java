//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum InsBenefitType
{
    /**
    * Used in the benefit table.  Corresponds to X12 EB01.0- Not usually used.  Would only be used if you are just indicating that the patient is covered, but without any specifics.
    */
    ActiveCoverage,
    /**
    * 1- Used for percentages to indicate portion that insurance will cover.  When interpreting electronic benefit information, this is the opposite percentage, the percentage that the patient will pay after deductible.
    */
    CoInsurance,
    /**
    * 2- The deductible amount.  Might be two entries if, for instance, deductible is waived on preventive.
    */
    Deductible,
    /**
    * 3- A dollar amount.
    */
    CoPayment,
    /**
    * 4- Services that are simply not covered at all.
    */
    Exclusions,
    /**
    * 5- Covers a variety of limitations, including Max, frequency, fee reductions, etc.
    */
    Limitations
}

