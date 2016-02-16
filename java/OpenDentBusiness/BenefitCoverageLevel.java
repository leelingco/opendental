//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum BenefitCoverageLevel
{
    /**
    * Used in the benefit table.0- Since this is a situational X12 field, we can also have none.  Typical for percentages and copayments.
    */
    None,
    /**
    * 1- The default for deductibles and maximums.
    */
    Individual,
    /**
    * 2- For example, family deductible or family maximum.
    */
    Family
}

