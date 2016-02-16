//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProviderSupplementalID;
import OpenDentBusiness.TableBase;

/**
* Some insurance companies require special provider ID #s, and this table holds them.
*/
public class ProviderIdent  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProviderIdentNum = new long();
    /**
    * FK to provider.ProvNum.  An ID only applies to one provider.
    */
    public long ProvNum = new long();
    /**
    * FK to carrier.ElectID  aka Electronic ID. An ID only applies to one insurance carrier.
    */
    public String PayorID = new String();
    /**
    * Enum:ProviderSupplementalID
    */
    public ProviderSupplementalID SuppIDType = ProviderSupplementalID.BlueCross;
    /**
    * The number assigned by the ins carrier.
    */
    public String IDNumber = new String();
}


