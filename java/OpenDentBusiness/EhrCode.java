//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrCode;
import OpenDentBusiness.TableBase;

/**
* For EHR module, these are all the codes from various code sets that will affect reporting clinical quality measures.  Users cannot edit.  This is not an actual table in the database.  The codes are loaded from the EHR.dll, so it is a static object, no inserts/updates.  Selecting from this 'table' will always use the cache pattern.
*/
public class EhrCode  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrCodeNum = new long();
    /**
    * Clinical quality measure test ID's that utilize this code.  Comma delimited list.  Example: 69v2,147v2.
    */
    public String MeasureIds = new String();
    /**
    * The National Library of Medicine Value Set Authority Center assigned value set name.  Example: Influenza Vaccination.
    */
    public String ValueSetName = new String();
    /**
    * The value set object identifier for reporting CQM.  Example: 2.16.840.1.113883.3.526.3.402.
    */
    public String ValueSetOID = new String();
    /**
    * The Quality Data Model category for this code.  2 examples: Condition/Diagnosis/Problem or Encounter.
    */
    public String QDMCategory = new String();
    /**
    * The code from the specified code system.  Example: 653.83.  This code can belong to multiple value sets, in which case this table will contain multiple rows for this code.
    */
    public String CodeValue = new String();
    /**
    * The description for this code.  This will frequently be duplicate data, but keeping it here ensures accurate CQM reporting.
    */
    public String Description = new String();
    /**
    * The code system name for this code.  Possible values are: CDCREC, CDT, CPT, CVX, HCPCS, ICD9CM, ICD10CM, LOINC, RXNORM, SNOMEDCT, SOP, and AdministrativeSex.
    */
    public String CodeSystem = new String();
    /**
    * The code system object identifier for reporting CQM.  Example: 2.16.840.1.113883.6.103.
    */
    public String CodeSystemOID = new String();
    /**
    * This is true if the code is in the corresponding table identified by CodeSystem.
    */
    public boolean IsInDb = new boolean();
    /**
    * 
    */
    public EhrCode copy() throws Exception {
        return (EhrCode)MemberwiseClone();
    }

}


