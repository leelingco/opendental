//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum BenefitQuantity
{
    /**
    * Used in the benefit table in conjunction with an integer quantity.0- This is used a lot. Most benefits do not need any sort of quantity.
    */
    None,
    /**
    * 1- For example, two exams per year
    */
    NumberOfServices,
    /**
    * 2- For example, 18 when flouride only covered to 18 y.o.
    */
    AgeLimit,
    /**
    * 3- For example, copay per 1 visit.
    */
    Visits,
    /**
    * 4- For example, pano every 5 years.
    */
    Years,
    /**
    * 5- For example, BWs every 6 months.
    */
    Months
}

