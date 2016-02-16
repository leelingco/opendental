//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CanadianNetwork;
import OpenDentBusiness.TableBase;

/**
* Not user-editable.
*/
public class CanadianNetwork  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CanadianNetworkNum = new long();
    /**
    * This will also be the folder name
    */
    public String Abbrev = new String();
    /**
    * .
    */
    public String Descript = new String();
    /**
    * A01.  Up to 12 char.
    */
    public String CanadianTransactionPrefix = new String();
    /**
    * Set to true if this network is in charge of handling all Request for Payment Reconciliation (RPR) transactions for all carriers within this network, as opposed to the individual carriers wihtin the network processing the RPR transactions themselves.
    */
    public boolean CanadianIsRprHandler = new boolean();
    /**
    * 
    */
    public CanadianNetwork copy() throws Exception {
        return (CanadianNetwork)this.MemberwiseClone();
    }

}


