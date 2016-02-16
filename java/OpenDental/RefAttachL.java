//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.RefAttach;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;

/**
* 
*/
public class RefAttachL   
{
    /**
    * Pass in all the refattaches for the patient.  This funtion finds the first referral from a Dr and returns that Dr's name.  Used in specialty practices.  Function is only used right now in the Dr. Ceph bridge.
    */
    public static String getReferringDr(List<RefAttach> attachList) throws Exception {
        if (attachList.Count == 0)
        {
            return "";
        }
         
        if (!attachList[0].IsFrom)
        {
            return "";
        }
         
        Referral referral = Referrals.GetReferral(attachList[0].ReferralNum);
        if (referral.PatNum != 0)
        {
            return "";
        }
         
        String retVal = referral.FName + " " + referral.MName + " " + referral.LName;
        if (!StringSupport.equals(referral.Title, ""))
        {
            retVal += ", " + referral.Title;
        }
         
        return retVal;
    }

}


