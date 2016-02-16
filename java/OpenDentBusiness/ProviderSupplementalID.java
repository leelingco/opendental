//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ProviderSupplementalID
{
    /**
    * Used when submitting e-claims to some carriers who require extra provider identifiers.  Usage varies by company.  Only used as needed.  SiteNumber is the only one that is still used on 5010s.  The other 3 have been deprecated and replaced by NPI.0
    */
    BlueCross,
    /**
    * 1
    */
    BlueShield,
    /**
    * 2
    */
    SiteNumber,
    /**
    * 3
    */
    CommercialNumber
}

