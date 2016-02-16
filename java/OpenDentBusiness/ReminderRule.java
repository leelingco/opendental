//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrCriterion;
import OpenDentBusiness.ReminderRule;
import OpenDentBusiness.TableBase;

/**
* Ehr
*/
public class ReminderRule  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ReminderRuleNum = new long();
    /**
    * Enum:EhrCriterion Problem,Medication,Allergy,Age,Gender,LabResult.
    */
    public EhrCriterion ReminderCriterion = EhrCriterion.Problem;
    /**
    * Foreign key to disease.DiseaseDefNum, medicationpat.MedicationNum, or allergy.AllergyDefNum. Will be 0 if Age, Gender, or LabResult are the trigger.
    */
    public long CriterionFK = new long();
    /**
    * Only used if Age, Gender, or LabResult are the trigger. Examples: "<25"(must include < or >), "Male"/"Female", "INR" (the simple description of the lab test)
    */
    public String CriterionValue = new String();
    /**
    * Text that will show as the reminder.
    */
    public String Message = new String();
    /**
    * 
    */
    public ReminderRule clone() {
        try
        {
            return (ReminderRule)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


