//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Corresponds to the electid table in the database. Helps with entering elecronic/payor id's as well as keeping track of the specific carrier requirements. Only used by the X12 format.
*/
public class ElectID  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ElectIDNum = new long();
    /**
    * aka Electronic ID.  A simple string.
    */
    public String PayorID = new String();
    /**
    * Used when doing a search.
    */
    public String CarrierName = new String();
    /**
    * True if medicaid. Then, the billing and treating providers will have their Medicaid ID's attached.
    */
    public boolean IsMedicaid = new boolean();
    /**
    * Integers separated by commas. Each long represents a ProviderSupplementalID type that is required by this insurance. Usually only used for BCBS or other carriers that require supplemental provider id's.  Even if we don't put the supplemental types in here, the user can still add them.  This just helps by doing an additional check for known required types.
    */
    public String ProviderTypes = new String();
    /**
    * Any comments. Usually includes enrollment requirements and descriptions of how to use the provider id's supplied by the carrier because they might call them by different names.
    */
    public String Comments = new String();
}


