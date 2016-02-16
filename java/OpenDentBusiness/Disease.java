//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Disease;
import OpenDentBusiness.FunctionalStatus;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.TableBase;

/**
* Each row is one disease that one patient has.  Now called a problem in the UI.  Must have a DiseaseDefNum.
*/
public class Disease  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DiseaseNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to diseasedef.DiseaseDefNum.  The disease description is in that table.
    */
    public long DiseaseDefNum = new long();
    /**
    * Any note about this disease that is specific to this patient.
    */
    public String PatNote = new String();
    /**
    * The last date and time this row was altered.  Not user editable.  Will be set to NOW by OD if this patient gets an OnlinePassword assigned.
    */
    public DateTime DateTStamp = new DateTime();
    //This column was removed
    /**
    * //FK to icd9.ICD9Num.  Will be zero if DiseaseDefNum has a value.
    */
    //public long ICD9Num;
    /**
    * Enum:ProblemStatus Active=0, Resolved=1, Inactive=2.
    */
    public ProblemStatus ProbStatus = ProblemStatus.Active;
    /**
    * Date that the disease was diagnosed.  Can be minval if unknown.
    */
    public DateTime DateStart = new DateTime();
    /**
    * Date that the disease was set resolved or inactive.  Will be minval if still active.  ProbStatus should be used to determine if it is active or not.
    */
    public DateTime DateStop = new DateTime();
    /**
    * FK snomed.SnomedCode.  Used in EHR CCD export/import only.  Must be one of the following SNOMED codes:
    * Problem/Concern (55607006 or blank), Finding (404684003), Complaint (409586006), Dignosis (282291009), Condition (64572001), FunctionalLimitation (248536006), Symptom (418799008).
    */
    public String SnomedProblemType = new String();
    /**
    * Enum:FunctionalStatus  Used to export EHR CCD functional status and/or cognitive status information only.
    */
    public FunctionalStatus FunctionStatus = FunctionalStatus.Problem;
    /**
    * 
    */
    public Disease copy() throws Exception {
        return (Disease)this.MemberwiseClone();
    }

}


