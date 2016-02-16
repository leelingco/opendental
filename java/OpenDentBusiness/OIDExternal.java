//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.OIDExternal;
import OpenDentBusiness.TableBase;

/**
* 
*/
public class OIDExternal  extends TableBase 
{
    /**
    * Primary key.
    */
    public long OIDExternalNum = new long();
    /**
    * Internal data type to be associated with.
    */
    public IdentifierType IDType = IdentifierType.Root;
    /**
    * This should be a Primary Key to a Table Type defined by the IDType field. Example: If IDType==Patient, then this field should be a PatNum that is a FK to Patient.Patnum
    */
    public long IDInternal = new long();
    /**
    * The OID extension, when combined with rootExternal it uniquely identifies an object.
    */
    public String IDExternal = new String();
    /**
    * The OID root, when combined with IDExternal it uniquely identifies an object.
    */
    public String rootExternal = new String();
    /**
    * 
    */
    public OIDExternal copy() throws Exception {
        return (OIDExternal)MemberwiseClone();
    }

}


