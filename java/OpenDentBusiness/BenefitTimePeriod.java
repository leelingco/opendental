//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum BenefitTimePeriod
{
    /**
    * Used in the benefit table.  Corresponds to X12 EB06.0- A timeperiod is frequenly not needed.  For example, percentages.
    */
    None,
    /**
    * 1- The renewal month is not Jan.  In this case, we need to know the effective date so that we know which month the benefits start over in.
    */
    ServiceYear,
    /**
    * 2- Renewal month is Jan.
    */
    CalendarYear,
    /**
    * 3- Usually used for ortho max.
    */
    Lifetime,
    /**
    * 4- Wouldn't be used alone.  Years would again be specified in the quantity field along with a number.
    */
    Years
}

