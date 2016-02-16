//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Intervention;
import OpenDentBusiness.InterventionCodeSet;
import OpenDentBusiness.TableBase;

/**
* An intervention ordered or performed.  Examples: smoking cessation and weightloss counseling.  Links to a definition in the ehrcode table using the CodeValue and CodeSystem.
*/
public class Intervention  extends TableBase 
{
    /**
    * Primary key.
    */
    public long InterventionNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to ehrcode.CodeValue.  This code may not exist in the ehrcode table, it may have been chosen from a bigger list of available codes.  In that case, this will be a FK to a specific code system table identified by the CodeSystem column.  The code for this item from one of the code systems supported.  Examples: V65.3 or 418995006.
    */
    public String CodeValue = new String();
    /**
    * FK to codesystem.CodeSystemName. The code system name for this code.  Possible values are: CPT, HCPCS, ICD9CM, ICD10CM, and SNOMEDCT.
    */
    public String CodeSystem = new String();
    /**
    * User-entered details about the intervention for this patient.  Max length 4000.
    */
    public String Note = new String();
    /**
    * The date of the intervention.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * Enum:InterventionCodeSet AboveNormalWeight, BelowNormalWeight, TobaccoCessation, Nutrition, PhysicalActivity, Dialysis.
    */
    public InterventionCodeSet CodeSet = InterventionCodeSet.AboveNormalWeight;
    /**
    * 
    */
    public Intervention copy() throws Exception {
        return (Intervention)MemberwiseClone();
    }

}


