//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;


public enum EhrCriterion
{
    /**
    * EhrCriterion: Problem,Medication,Allergy,Age,Gender,LabResult0-DiseaseDef.  Shows as 'problem' because it needs to be human readable.
    */
    Problem,
    /**
    * 1-Medication
    */
    Medication,
    /**
    * 2-AllergyDef
    */
    Allergy,
    /**
    * 3-Age
    */
    Age,
    /**
    * 4-Gender
    */
    Gender,
    /**
    * 5-LabResult
    */
    LabResult
}

/**
* //6-ICD9//now handled by Problem(DiseaseDef)
*/
//ICD9