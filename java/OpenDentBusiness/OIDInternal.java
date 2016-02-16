//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.OIDInternal;
import OpenDentBusiness.TableBase;

/**
* 
*/
public class OIDInternal  extends TableBase 
{
    /**
    * Primary key.
    */
    public long OIDInternalNum = new long();
    /**
    * Internal data type to be associated with OIDRoot
    */
    public IdentifierType IDType = IdentifierType.Root;
    /**
    * This is the root OID for this data type, when combined with extension, uniquely identifies a single object.
    */
    public String IDRoot = new String();
    /**
    * 
    */
    public OIDInternal copy() throws Exception {
        return (OIDInternal)MemberwiseClone();
    }

}


