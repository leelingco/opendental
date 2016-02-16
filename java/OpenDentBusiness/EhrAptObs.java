//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrAptObs;
import OpenDentBusiness.EhrAptObsIdentifier;
import OpenDentBusiness.EhrAptObsType;
import OpenDentBusiness.TableBase;

/**
* An EHR appointment observation.  Needed for syndromic surveillance messaging.  Each syndromic message requires at least one observation.
*/
public class EhrAptObs  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrAptObsNum = new long();
    /**
    * FK to appointment.AptNum.  There can be an unlimited number of observations per appointment.
    */
    public long AptNum = new long();
    /**
    * Used in HL7 OBX-3 for syndromic surveillance.
    */
    public EhrAptObsIdentifier IdentifyingCode = EhrAptObsIdentifier.BodyTemp;
    /**
    * Enum:EhrAptObsType .  Used in HL7 OBX-2 for syndromic surveillance.  Identifies the data type for the observation value in ValReported.
    */
    public EhrAptObsType ValType = EhrAptObsType.Address;
    /**
    * The value of the observation. The value format must match the ValType.  This field could be text, a datetime, a code, etc..  Used in HL7 OBX-5 for syndromic surveillance.
    */
    public String ValReported = new String();
    /**
    * Used in HL7 OBX-6 for syndromic surveillance when ValType is Numeric (otherwise left blank).
    */
    public String UcumCode = new String();
    /**
    * When ValType is Coded, then this contains the code system corresponding to the code in ValReported.  When ValType is not Coded, then this field should be blank.
    * Allowed values are LOINC,SNOMEDCT,ICD9,ICD10.
    */
    public String ValCodeSystem = new String();
    public EhrAptObs clone() {
        try
        {
            return (EhrAptObs)this.MemberwiseClone();
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


