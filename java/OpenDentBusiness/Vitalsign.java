//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.Vitalsign;

/**
* For EHR module, one dated vital sign entry.  BMI is calulated on demand based on height and weight and may be one of 4 ALOINC codes. 39156-5 "Body mass index (BMI) [Ratio]" is most applicable.
*/
public class Vitalsign  extends TableBase 
{
    /**
    * Primary key.
    */
    public long VitalsignNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Height of patient in inches. Fractions might be needed some day.  Allowed to be 0.  Six possible LOINC codes, most applicable is 8302-2, "Body height".
    */
    public float Height = new float();
    /**
    * Lbs.  Allowed to be 0. Six possible LOINC codes, most applicable is 29463-7, "Body weight".
    */
    public float Weight = new float();
    /**
    * Units are mmHg (millimeters of mercury). Allowed to be 0. LOINC code 8480-6.
    */
    public int BpSystolic = new int();
    /**
    * Units are mmHg (millimeters of mercury). Allowed to be 0. LOINC code 8462-4.
    */
    public int BpDiastolic = new int();
    /**
    * The date that the vitalsigns were taken.
    */
    public DateTime DateTaken = new DateTime();
    /**
    * For an abnormal BMI measurement this must be true in order to meet quality measurement.//intervention? I think these should be deprecated and use an Intervention object instead.
    */
    public boolean HasFollowupPlan = new boolean();
    /**
    * If a BMI was not recored, this must be true in order to meet quality measurement.  For children, this is used as an IsPregnant flag, the only valid reason for not taking BMI on children.//intervention? I think these should be deprecated and use an Intervention object instead.
    */
    public boolean IsIneligible = new boolean();
    /**
    * For HasFollowupPlan or IsIneligible, this documents the specifics.//intervention? I think these should be deprecated and use an Intervention object instead.
    */
    public String Documentation = new String();
    /**
    * .//intervention? I think these should be deprecated and use an Intervention object instead.
    */
    public boolean ChildGotNutrition = new boolean();
    /**
    * .//intervention? I think these should be deprecated and use an Intervention object instead.
    */
    public boolean ChildGotPhysCouns = new boolean();
    /**
    * Used for CQMs.  SNOMED CT code either Normal="", Overweight="238131007", or Underweight="248342006".  Set when BMI is found to be "out of range", based on age groups.  Should be calculated when vital sign is saved.  Calculate based on age as of Jan 1 of the year vitals were taken.  Not currently displayed to user.
    */
    public String WeightCode = new String();
    /**
    * FK to ehrcode.CodeValue.  Also FK to LOINC.LoincCode.  Used for CQMs.  LOINC code used to describe the height exam performed.  Examples: Body Height Measured=3137-7, Body Height Stated=3138-5, Body Height --pre surgery=8307-1.  We will default to Body Height=8302-2, but user can choose another from the list of 6 allowed.  Can be blank if BP only.
    */
    public String HeightExamCode = new String();
    /**
    * FK to ehrcode.CodeValue.  Also FK to LOINC.LoincCode.  Used for CQMs.  LOINC code used to describe the weight exam performed.  Examples: Body Weight Measured=3141-9, Body Weight Stated=3142-7, Body Weight --with clothes=8350-1.  We will default to Body Weight=29463-7, but user can choose another from the list of 6 allowed.  Can be blank if BP only.
    */
    public String WeightExamCode = new String();
    /**
    * FK to ehrcode.CodeValue.  Also FK to LOINC.LoincCode.  Used for CQMs.  LOINC code used to describe the BMI percentile calculated.  We will use LOINC 59576-9 - BMI Percentile Per age and gender.  Can be blank if BP only.
    */
    public String BMIExamCode = new String();
    /**
    * FK to ehrnotperformed.EhrNotPerformedNum.  This will link a vitalsign to the EhrNotPerformed object where the reason not performed will be stored.  The linking will allow us to display the not performed reason directly in the vital sign window and will make CQM queries easier.  Will be 0 if not linked to an EhrNotPerformed object.
    */
    public long EhrNotPerformedNum = new long();
    /**
    * FK to disease.DiseaseNum.  This will link this vitalsign object to a pregnancy diagnosis for this patient.  It will be 0 for non pregnant patients.  The disease it is linked to will be inserted automatically based on the default value set.  In order to change this code for this specific exam it will have to be changed in the problems list.
    */
    public long PregDiseaseNum = new long();
    /**
    * BMI percentile of patient, based on gender and age and the calculated BMI.  We will use the CDC numbers to calculate percentile found here: (http://www.cdc.gov/nchs/data/series/sr_11/sr11_246.pdf).
    */
    public int BMIPercentile = new int();
    /**
    * 
    */
    public Vitalsign copy() throws Exception {
        return (Vitalsign)MemberwiseClone();
    }

}


