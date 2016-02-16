//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.TableBase;

/**
* Every InsPlan has a Carrier.  The carrier stores the name and address.
*/
public class Carrier  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CarrierNum = new long();
    /**
    * Name of the carrier.
    */
    public String CarrierName = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Second line of address.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * 2 char in the US.
    */
    public String State = new String();
    /**
    * Postal code.
    */
    public String Zip = new String();
    /**
    * Includes any punctuation.
    */
    public String Phone = new String();
    /**
    * E-claims electronic payer id.  5 char in USA.  6 digits in Canada.  I've seen an ID this long before: "LA-DHH-MEDICAID".  The user interface currently limits length to 20, although db limits length to 255.  X12 requires length between 2 and 80.
    */
    public String ElectID = new String();
    /**
    * Do not send electronically.  It's just a default; you can still send electronically.
    */
    public boolean NoSendElect = new boolean();
    /**
    * Canada: True if a CDAnet carrier.  This has significant implications:  1. It can be filtered for in the list of carriers.  2. An ElectID is required.  3. The ElectID can never be used by another carrier.  4. If the carrier is attached to any etrans, then the ElectID cannot be changed (and, of course, the carrier cannot be deleted or combined).
    */
    public boolean IsCDA = new boolean();
    /**
    * The version of CDAnet supported.  Either 02 or 04.
    */
    public String CDAnetVersion = new String();
    /**
    * FK to canadiannetwork.CanadianNetworkNum.  Only used in Canada.  Right now, there is no UI to the canadiannetwork table in our db.
    */
    public long CanadianNetworkNum = new long();
    /**
    * .
    */
    public boolean IsHidden = new boolean();
    /**
    * 1=No Encryption, 2=CDAnet standard #1, 3=CDAnet standard #2.  Field A10.
    */
    public byte CanadianEncryptionMethod = new byte();
    /**
    * Bit flags.
    */
    public CanSupTransTypes CanadianSupportedTypes = CanSupTransTypes.None;
    public Carrier copy() throws Exception {
        return (Carrier)this.MemberwiseClone();
    }

    public Carrier() throws Exception {
    }

}


