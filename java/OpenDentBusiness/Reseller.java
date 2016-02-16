//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Reseller;
import OpenDentBusiness.TableBase;

/**
* Only used at HQ.  If a row is present in this table, then this customer is a reseller.  Also holds their credentials for the reseller portal.
*/
public class Reseller  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ResellerNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * User name used to log into the reseller portal with.
    */
    public String UserName = new String();
    /**
    * Password used to log into the reseller portal with.  Stored as plain text.
    */
    public String ResellerPassword = new String();
    /**
    * Returns a copy of this Reseller.
    */
    public Reseller copy() throws Exception {
        return (Reseller)this.MemberwiseClone();
    }

}


