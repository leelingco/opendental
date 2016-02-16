//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ResellerService;
import OpenDentBusiness.TableBase;

/**
* An entry in a list of services for a specific reseller to pick from.  To determine which services a certain customer has access to, check the repeating charges table.
*/
public class ResellerService  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ResellerServiceNum = new long();
    /**
    * FK to reseller.ResellerNum.
    */
    public long ResellerNum = new long();
    /**
    * FK to procedurecode.CodeNum.
    */
    public long CodeNum = new long();
    /**
    * Amount of the service.  Might be a default, or might be the same for all customers of the reseller.
    */
    public double Fee = new double();
    /**
    * Returns a copy of this ResellerService.
    */
    public ResellerService copy() throws Exception {
        return (ResellerService)this.MemberwiseClone();
    }

}


