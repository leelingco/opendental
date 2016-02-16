//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrNotPerformed;
import OpenDentBusiness.TableBase;

/**
* For EHR module, these are all the items 'not performed' on patients.  Each row will link to the ehrcode table to retrieve relevant data.  To join this table to the ehrcode table you must join on CodeValue and CodeSystem.  Some items will have associated reasons attached to specify why it was not performed.  Those reasons will also be defined in the ehrcode table, so it may be necessary to join with that table again for the data relevant to the reason.
*/
public class EhrNotPerformed  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrNotPerformedNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to ehrcode.CodeValue.  This code may not exist in the ehrcode table, it may have been chosen from a bigger list of available codes.  In that case, this will be a FK to a specific code system table identified by the CodeSystem column.  The code for this item from one of the code systems supported.  Examples: 90656 or 442333005.
    */
    public String CodeValue = new String();
    /**
    * FK to codesystem.CodeSystemName. The code system name for this code.  Possible values are: CPT, CVX, LOINC, SNOMEDCT.
    */
    public String CodeSystem = new String();
    /**
    * FK to ehrcode.CodeValue.  This code may not exist in the ehrcode table, it may have been chosen from a bigger list of available codes.  In that case, this will be a FK to a specific code system table identified by the CodeSystem column.  The code for the reason the item was not performed from one of the code systems supported.  Examples: 182856006 or 419808006.
    */
    public String CodeValueReason = new String();
    /**
    * FK to codesystem.CodeSystemName. The code system name for this code.  Possible value is: SNOMEDCT.
    */
    public String CodeSystemReason = new String();
    /**
    * Relevant notes for this not performed item.  Just in case users want it, does not get reported in EHR quality measure reporting.  Max length 4000.
    */
    public String Note = new String();
    /**
    * The date and time this item was created.  Can be edited to the date and time the item actually occurred.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * 
    */
    public EhrNotPerformed copy() throws Exception {
        return (EhrNotPerformed)MemberwiseClone();
    }

}


