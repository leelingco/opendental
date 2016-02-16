//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.CanadianOutput;
import OpenDental.Eclaims.CCDFieldInputter;
import OpenDentBusiness.Claim;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import TestCanada.CarrierTC;
import TestCanada.ClaimTC;
import TestCanada.InsSubTC;

public class Reversal   
{
    public static String run(int scriptNum, String responseExpected, Claim claim) throws Exception {
        String retVal = "";
        InsPlan insPlan = InsPlans.getPlan(claim.PlanNum,null);
        InsSub insSub = InsSubs.getOne(claim.InsSubNum);
        long etransNum = CanadianOutput.sendClaimReversal(claim,insPlan,insSub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, responseExpected))
        {
            return "G05 should be " + responseExpected + "\r\n";
        }
         
        retVal += "Reversal #" + scriptNum.ToString() + " successful.\r\n";
        return retVal;
    }

    public static String runOne() throws Exception {
        Claim claim = Claims.GetClaim(ClaimTC.ClaimNums[1]);
        claim.CanadaTransRefNum = "BCD12345      ";
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(1,"A",claim);
    }

    public static String runTwo() throws Exception {
        Claim claim = Claims.GetClaim(ClaimTC.ClaimNums[2]);
        claim.CanadaTransRefNum = "BCD88345      ";
        InsSubTC.setAssignBen(true,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(2,"A",claim);
    }

    //TODO: We need to run the reversal for the secondary claim. Can't do this yet since we don't have COB claims implemented yet.
    public static String runThree() throws Exception {
        Claim claim = Claims.GetClaim(ClaimTC.ClaimNums[6]);
        claim.CanadaTransRefNum = "CCC12345      ";
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        return run(3,"R",claim);
    }

    public static String runFour() throws Exception {
        Claim claim = Claims.GetClaim(ClaimTC.ClaimNums[11]);
        claim.CanadaTransRefNum = "AB123456V2    ";
        InsSubTC.setAssignBen(false,claim.InsSubNum);
        CarrierTC.SetEncryptionMethod(claim.PlanNum, 1);
        String oldVersion = CarrierTC.setCDAnetVersion(claim.PlanNum,"02");
        String retval = run(4,"A",claim);
        CarrierTC.setCDAnetVersion(claim.PlanNum,oldVersion);
        return retval;
    }

}


