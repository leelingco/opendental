//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum MatchCardinality
{
    /**
    * 0 - If any one of the conditions are met from any of the categories.
    */
    One,
    /**
    * 1 - Must have one match from each of the categories with set values. Categories are :Medication, Allergy, Problem, Vitals, Age, Gender, and Lab Results.
    */
    OneOfEachCategory,
    /**
    * 2 - Must match any two conditions, may be from same category.
    */
    TwoOrMore,
    /**
    * 3 - Must match every code defined in the EhrTrigger.
    */
    All
}

