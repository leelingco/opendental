//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import EhrLaboratories.HL70125;
import OpenDentBusiness.EhrOperand;
import OpenDentBusiness.EhrRestrictionType;

/**
* Used by ehr "Generate Patient Lists".  This object represents one row in the grid when building the report.  Multiple such elements will be ANDed together to automatically generate a query.
*/
public class EhrPatListElement2014   
{
    /**
    * Birthdate, Disease, Medication, or LabResult
    */
    public EhrRestrictionType Restriction = EhrRestrictionType.Birthdate;
    /**
    * For all 4 types, what to compare against.  Examples:  Birthdate: '50', Disease: '4140' (icd9 code will be followed by wildcard), Medication: 'Lisinopril' (not case sensitive, surrounded by wildcards), LabResult: 'HDL-cholesterol'. Allergy:'Allergy - Morphene'. CommPref:'MobilePh'(exact enum names)
    */
    public String CompareString = new String();
    /**
    * gt, lt, or equal.  Only used for lab and birthdate.
    */
    public EhrOperand Operand = EhrOperand.GreaterThan;
    /**
    * Only used for Lab.  Usually a number.
    */
    public String LabValue = new String();
    /**
    * Only select records after this date, i.e. date of diagnosis, prescription, lab date, etc... If ==null or ==DateTime.MinValue this value is ignored.
    */
    public DateTime StartDate = new DateTime();
    /**
    * Only select records before this date, i.e. date of diagnosis, prescription, lab date, etc... If ==null or ==DateTime.MinValue this value is ignored.
    */
    public DateTime EndDate = new DateTime();
    /**
    * Used to determine how the LabValue should be compaired.
    */
    public HL70125 LabValueType = HL70125.CE;
    /**
    * Ucum codes. Example: mg/dL
    */
    public String LabValueUnits = new String();
}


