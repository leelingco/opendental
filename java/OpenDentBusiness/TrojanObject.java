//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Benefit;

/**
* This is used as a container for plan and benefit info coming in from Trojan.
*/
public class TrojanObject   
{
    /**
    * TrojanID
    */
    public String TROJANID = new String();
    /**
    * Employer name
    */
    public String ENAME = new String();
    /**
    * GroupName
    */
    public String PLANDESC = new String();
    /**
    * Carrier phone
    */
    public String ELIGPHONE = new String();
    /**
    * GroupNum
    */
    public String POLICYNO = new String();
    /**
    * Accepts eclaims
    */
    public boolean ECLAIMS = new boolean();
    /**
    * ElectID
    */
    public String PAYERID = new String();
    /**
    * CarrierName
    */
    public String MAILTO = new String();
    /**
    * Address
    */
    public String MAILTOST = new String();
    /**
    * City
    */
    public String MAILCITYONLY = new String();
    /**
    * State
    */
    public String MAILSTATEONLY = new String();
    /**
    * Zip
    */
    public String MAILZIPONLY = new String();
    /**
    * The only thing that will be missing from these benefits is the PlanNum.
    */
    public List<Benefit> BenefitList = new List<Benefit>();
    /**
    * This can be filled at some point based on the carrier fields.
    */
    public long CarrierNum = new long();
    /**
    * 
    */
    public String BenefitNotes = new String();
    /**
    * 
    */
    public String PlanNote = new String();
}


