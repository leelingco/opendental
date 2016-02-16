//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.EnumClaimMedType;

/**
* Holds a list of claims to show in the claims 'queue' waiting to be sent.  Not an actual database table.
*/
public class ClaimSendQueueItem   
{
    /**
    * 
    */
    public long ClaimNum = new long();
    /**
    * 
    */
    public boolean NoSendElect = new boolean();
    /**
    * 
    */
    public String PatName = new String();
    /**
    * Single char: U,H,W,P,S,or R.U=Unsent, H=Hold until pri received, W=Waiting in queue, P=Probably sent, S=Sent, R=Received.  A(adj) is no longer used.
    */
    public String ClaimStatus = new String();
    /**
    * 
    */
    public String Carrier = new String();
    /**
    * 
    */
    public long PatNum = new long();
    /**
    * 
    */
    public long ClearinghouseNum = new long();
    /**
    * 
    */
    public long ClinicNum = new long();
    /**
    * Enum:EnumClaimMedType 0=Dental, 1=Medical, 2=Institutional
    */
    public EnumClaimMedType MedType = EnumClaimMedType.Dental;
    /**
    * 
    */
    public String MissingData = new String();
    /**
    * 
    */
    public String Warnings = new String();
    /**
    * 
    */
    public DateTime DateService = new DateTime();
    public ClaimSendQueueItem copy() throws Exception {
        return (ClaimSendQueueItem)MemberwiseClone();
    }

}


