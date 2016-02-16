//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum PatRace
{
    /**
    * This enum was not able to completely replace the old enum because we keep string representations of the old enums in certain places like sheets and HL7.0 - Hidden for EHR.
    */
    Aboriginal,
    /**
    * 1 - CDCREC:2054-5 Race
    */
    AfricanAmerican,
    /**
    * 2 - CDCREC:1002-5 Race
    */
    AmericanIndian,
    /**
    * 3 - CDCREC:2028-9 Race
    */
    Asian,
    /**
    * 4 - Our hard-coded option for EHR reporting.
    */
    DeclinedToSpecifyRace,
    /**
    * 5 - CDCREC:2076-8 Race
    */
    HawaiiOrPacIsland,
    /**
    * 6 - CDCREC:2135-2 Ethnicicty.  If EHR is turned on, our UI will force this to be supplemental to a base 'race'.
    */
    Hispanic,
    //should be renamed to HispanicOrLatino
    /**
    * 7 - We had to keep this for backward compatibility.  Hidden for EHR because it's explicitly not allowed.
    */
    Multiracial,
    /**
    * 8 - CDCREC:2131-1 Race.
    */
    Other,
    /**
    * 9 - CDCREC:2106-3 Race
    */
    White,
    /**
    * 10 - CDCREC:2186-5 Ethnicity.  We originally used the lack of Hispanic to indicate NonHispanic.  Now we are going to explicitly store NonHispanic to make queries for ClinicalQualityMeasures easier.
    */
    NotHispanic,
    /**
    * 11 - Our hard-coded option for EHR reporting.
    */
    DeclinedToSpecifyEthnicity
}

