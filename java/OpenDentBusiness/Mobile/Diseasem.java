//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Diseasem;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.TableBase;

/**
* Links medications to patients. Patient portal version
*/
public class Diseasem  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long DiseaseNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to diseasedef.DiseaseDefNum.  The disease description is in that table.  Will be zero if ICD9Num has a value.
    */
    public long DiseaseDefNum = new long();
    /**
    * Any note about this disease that is specific to this patient.
    */
    public String PatNote = new String();
    /**
    * FK to icd9.ICD9Num.  Will be zero if DiseaseDefNum has a value.
    */
    public long ICD9Num = new long();
    /**
    * Enum: ProblemStatus: Active=0, Resolved=1, Inactive=2.
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
    * 
    */
    public Diseasem copy() throws Exception {
        return (Diseasem)this.MemberwiseClone();
    }

}


