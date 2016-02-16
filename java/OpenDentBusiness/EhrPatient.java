//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrPatient;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.YN;

/**
* Patient information needed for EHR.  1:1 relation to patient table.  They are stored here because we want to try to keep the size of the patient table a bit smaller.
*/
public class EhrPatient  extends TableBase 
{
    /**
    * FK to patient.PatNum.  Also the primary key for this table. Always one to one relationship with patient table.  A new patient might not have an entry here until needed.
    */
    public long PatNum = new long();
    /**
    * Mother's maiden first name.  Exported in HL7 PID-6 for immunization messages.
    */
    public String MotherMaidenFname = new String();
    /**
    * Mother's maiden last name.  Exported in HL7 PID-6 for immunization messages.
    */
    public String MotherMaidenLname = new String();
    /**
    * Enum:YN. Indicates whether or not the patient wants to share their vaccination information with other EHRs.  Used in immunization export.
    */
    public YN VacShareOk = YN.Unknown;
    /**
    * 
    */
    public EhrPatient clone() {
        try
        {
            return (EhrPatient)this.MemberwiseClone();
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


