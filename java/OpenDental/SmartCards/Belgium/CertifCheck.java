//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards.Belgium;

import OpenDental.SmartCards.Belgium.BelgianIdentityCard;

public class CertifCheck   
{
    public CertifCheck() {
    }

    /**
    * Policy used: 0=None/1=OCSP/2=CRL
    */
    public int Policy = new int();
    /**
    * Array of BEID_Certif structures
    */
    public byte[] Certificates = new byte[]();
    /**
    * Number of elements in Array
    */
    public int Length = new int();
    /**
    * Status of signature (for ID and Address) or hash (for Picture) on retrieved field
    */
    public int SignatureCheck = new int();
    /**
    * reserved for future use
    */
    public byte[] RFU = new byte[]();
}


