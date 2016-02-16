//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EduResource;
import OpenDentBusiness.TableBase;

/**
* EHR education resource.  Only one of the 3 FK fields will be used at a time (DiseaseDefNum, MedicationNum, or LabResultID).  The other two will be blank.   Displays a clickable URL if the patient meets certain criteria.
*/
public class EduResource  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EduResourceNum = new long();
    /**
    * FK to diseasedef.DiseaseDefNum.  This now also handles ICD9s and Snomeds via the entry in DiseaseDef.
    */
    public long DiseaseDefNum = new long();
    /**
    * FK to medication.MedicationNum.
    */
    public long MedicationNum = new long();
    /**
    * FK to labresult.TestID.
    */
    public String LabResultID = new String();
    /**
    * Used for display in the grid.
    */
    public String LabResultName = new String();
    /**
    * String, example <43. Must start with < or > followed by int.  Only used if FK LabResultID is used.
    */
    public String LabResultCompare = new String();
    /**
    * .
    */
    public String ResourceUrl = new String();
    /**
    * //FK to icd9.ICD9Num.//this is now obtained by pointing to a DiseaseDef which has an ICD9
    */
    //public long Icd9Num;
    /**
    * 
    */
    public EduResource copy() throws Exception {
        return (EduResource)this.MemberwiseClone();
    }

}


