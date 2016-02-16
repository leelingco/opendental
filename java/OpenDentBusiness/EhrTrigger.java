//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.EhrTrigger;
import OpenDentBusiness.MatchCardinality;
import OpenDentBusiness.TableBase;

/**
* Used for CDS automation.  May later be expanded to replace "automation."
*/
public class EhrTrigger  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrTriggerNum = new long();
    /**
    * Short description to describe the trigger.
    */
    public String Description = new String();
    /**
    * 
    */
    public String ProblemSnomedList = new String();
    /**
    * 
    */
    public String ProblemIcd9List = new String();
    /**
    * 
    */
    public String ProblemIcd10List = new String();
    /**
    * 
    */
    public String ProblemDefNumList = new String();
    /**
    * 
    */
    public String MedicationNumList = new String();
    /**
    * 
    */
    public String RxCuiList = new String();
    /**
    * 
    */
    public String CvxList = new String();
    /**
    * 
    */
    public String AllergyDefNumList = new String();
    /**
    * Age, Gender.  Can be multiple age entries but only one gender entry as coma delimited values.  Example: " age,>18  age,<=55  gender,male"
    */
    public String DemographicsList = new String();
    /**
    * //Tab delimited list, sub-components separated by semicolon. Loinc;Value;Units\t Example: Cholesterol [Mass/volume] in Serum or Plasma>150mg/dL=="2093-3;>150;mg/dL"List of loinc codes padded with spaces.
    */
    public String LabLoincList = new String();
    /**
    * Examples:  Height,>=72  Weight<,100  BMI=  (BP currently not implemented.)
    */
    public String VitalLoincList = new String();
    /**
    * The reccomended course of action for this intervention.
    */
    public String Instructions = new String();
    /**
    * Bibliographic information, not a URL.
    */
    public String Bibliography = new String();
    /**
    * Requires One, OneOfEachCategory, TwoOrMore, or All for trigger to match.
    */
    public MatchCardinality Cardinality = MatchCardinality.One;
    /**
    * 
    */
    public EhrTrigger copy() throws Exception {
        return (EhrTrigger)this.MemberwiseClone();
    }

    public EhrTrigger() throws Exception {
        Description = "";
        ProblemSnomedList = "";
        ProblemIcd9List = "";
        ProblemIcd10List = "";
        ProblemDefNumList = "";
        MedicationNumList = "";
        RxCuiList = "";
        CvxList = "";
        AllergyDefNumList = "";
        DemographicsList = "";
        LabLoincList = "";
        VitalLoincList = "";
        Cardinality = MatchCardinality.One;
    }

    /**
    * Used for displaying what elements of the trigger are set. Example: Medication, Demographics
    */
    public String getTriggerCategories() throws Exception {
        String retVal = "";
        if (!StringSupport.equals(ProblemSnomedList.Trim(), "") || !StringSupport.equals(ProblemIcd9List.Trim(), "") || !StringSupport.equals(ProblemIcd10List.Trim(), "") || !StringSupport.equals(ProblemDefNumList.Trim(), ""))
        {
            retVal += "Problem";
        }
         
        if (!StringSupport.equals(MedicationNumList.Trim(), "") || !StringSupport.equals(CvxList.Trim(), "") || !StringSupport.equals(RxCuiList.Trim(), ""))
        {
            retVal += (StringSupport.equals(retVal, "") ? "" : ", ") + "Medication";
        }
         
        if (!StringSupport.equals(AllergyDefNumList.Trim(), ""))
        {
            retVal += (StringSupport.equals(retVal, "") ? "" : ", ") + "Allergy";
        }
         
        if (!StringSupport.equals(DemographicsList.Trim(), ""))
        {
            retVal += (StringSupport.equals(retVal, "") ? "" : ", ") + "Demographic";
        }
         
        if (!StringSupport.equals(LabLoincList.Trim(), ""))
        {
            retVal += (StringSupport.equals(retVal, "") ? "" : ", ") + "Lab Result";
        }
         
        if (!StringSupport.equals(VitalLoincList.Trim(), ""))
        {
            retVal += (StringSupport.equals(retVal, "") ? "" : ", ") + "Vitals";
        }
         
        return retVal;
    }

}


