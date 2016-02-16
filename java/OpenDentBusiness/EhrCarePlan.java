//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrCarePlan;
import OpenDentBusiness.TableBase;

public class EhrCarePlan  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrCarePlanNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Snomed code describing the type of educational instruction provided.  Limited to terms descending from the Snomed 409073007 (Education Hierarchy).
    */
    public String SnomedEducation = new String();
    /**
    * Instructions provided to the patient.
    */
    public String Instructions = new String();
    /**
    * This field does not help much with care plan instructions, but will be more helpful for other types of care plans if we expand in the future (for example, planned procedures).  We also saw examples where this date was included in the human readable part of a CCD, but not in the machine readable part.
    */
    public DateTime DatePlanned = new DateTime();
    /**
    * 
    */
    public EhrCarePlan clone() {
        try
        {
            return (EhrCarePlan)this.MemberwiseClone();
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


