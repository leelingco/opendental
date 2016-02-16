//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RxDef;
import OpenDentBusiness.TableBase;

/**
* Rx definitions.  Can safely delete or alter, because they get copied to the rxPat table, not referenced.
*/
public class RxDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RxDefNum = new long();
    /**
    * The name of the drug.
    */
    public String Drug = new String();
    /**
    * Directions.
    */
    public String Sig = new String();
    /**
    * Amount to dispense.
    */
    public String Disp = new String();
    /**
    * Number of refills.
    */
    public String Refills = new String();
    /**
    * Notes about this drug. Will not be copied to the rxpat.
    */
    public String Notes = new String();
    /**
    * Is a controlled substance.  This will affect the way it prints.
    */
    public boolean IsControlled = new boolean();
    /**
    * RxNorm Code identifier.  Copied down into medicationpat.RxCui (medical order) when a prescription is written.
    */
    public long RxCui = new long();
    /**
    * 
    */
    public RxDef copy() throws Exception {
        return (RxDef)this.MemberwiseClone();
    }

}


