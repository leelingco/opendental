//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PatientRace;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.TableBase;

/**
* Each patient may have multiple races.  Used to represent a race or an ethnicity for a patient.
*/
public class PatientRace  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PatientRaceNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Enum:PatRace
    */
    public PatRace Race = PatRace.Aboriginal;
    /**
    * FK to cdcrec.CdcrecCode.  This code is mainly for Ehr reporting, but may also be used for other HL7 messages.  Will be blank if they choose a race, like Aboriginal, that is not in the cdcrec code list.  We will initially only use 8 of the cdcrec race codes, see enum below.
    */
    public String CdcrecCode = new String();
    /**
    * 
    */
    public PatientRace clone() {
        try
        {
            return (PatientRace)this.MemberwiseClone();
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


