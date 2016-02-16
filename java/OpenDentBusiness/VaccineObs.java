//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.VaccineObs;
import OpenDentBusiness.VaccineObsIdentifier;
import OpenDentBusiness.VaccineObsType;
import OpenDentBusiness.VaccineObsValCodeSystem;

/**
* Vaccine observation.  There may be multiple vaccine observations for each vaccine.
*/
public class VaccineObs  extends TableBase 
{
    /**
    * Primary key.
    */
    public long VaccineObsNum = new long();
    /**
    * FK to vaccinepat.VaccinePatNum.
    */
    public long VaccinePatNum = new long();
    /**
    * Enum:VaccineObsType Coded, Dated, Numeric, Text, DateAndTime.  Used in HL7 OBX-2.
    */
    public VaccineObsType ValType = VaccineObsType.Coded;
    /**
    * Enum:VaccineObsIdentifier.  Identifies the observation question.  Used in HL7 OBX-3.
    */
    public VaccineObsIdentifier IdentifyingCode = VaccineObsIdentifier.DatePublished;
    /**
    * The observation value.  The type of the value depends on the ValType.  Used in HL7 OBX-5.
    */
    public String ValReported = new String();
    /**
    * Enum:VaccineObsValCodeSystem CVX, HL70064.  The observation value code system when ValType is Coded.  Used in HL7 OBX-5.
    */
    public VaccineObsValCodeSystem ValCodeSystem = VaccineObsValCodeSystem.CVX;
    /**
    * FK to vaccineobs.VaccineObsNum.  All vaccineobs records with matching GroupId are in the same group.  Set to 0 if this vaccine observation is not part of a group.  Used in HL7 OBX-4.
    */
    public long VaccineObsNumGroup = new long();
    /**
    * Used in HL7 OBX-6.
    */
    public String UcumCode = new String();
    /**
    * Date of observation.  Used in HL7 OBX-14.
    */
    public DateTime DateObs = new DateTime();
    /**
    * Code from code set CDCPHINVS (this code system is not yet fully defined, so user has to enter manually).  Used in HL7 OBX-17.  Only required when IdentifyingCode is FundPgmEligCat.
    */
    public String MethodCode = new String();
    public VaccineObs clone() {
        try
        {
            return (VaccineObs)this.MemberwiseClone();
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


