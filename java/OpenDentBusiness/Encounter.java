//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Encounter;
import OpenDentBusiness.TableBase;

/**
* Mostly used for EHR.  This rigorously records encounters using rich automation, so that reporting can be easy and meaningful.  Encounters can also be tracked separately using billable procedures.  In contrast, encounters in this table are not billable.  There can be multiple encounters at one appointment because there can be different types.
*/
public class Encounter  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EncounterNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to ehrcode.CodeValue.  This code may not exist in the ehrcode table, it may have been chosen from a bigger list of available codes.  In that case, this will be a FK to a specific code system table identified by the CodeSystem column.  The code for this item from one of the code systems supported.  Examples: 185349003 or 406547006.
    */
    public String CodeValue = new String();
    /**
    * FK to codesystem.CodeSystemName. This will determine which specific code system table the CodeValue is a FK to.  We only allow the following CodeSystems in this table: CDT, CPT, HCPCS, and SNOMEDCT.
    */
    public String CodeSystem = new String();
    /**
    * Max length 2000.
    */
    public String Note = new String();
    /**
    * Date the encounter occurred
    */
    public DateTime DateEncounter = new DateTime();
    /**
    * Returns a copy of this Encounter.
    */
    public Encounter clone() {
        try
        {
            return (Encounter)this.MemberwiseClone();
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


