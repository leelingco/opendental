//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.Eclaims.CanadianOutput;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

public class OutstandingTrans   
{
    private static String run(int scriptNum, boolean version2, boolean sendToItrans, Carrier carrier, RefSupport<List<Etrans>> etransRequests) throws Exception {
        String retVal = "";
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        etransRequests.setValue(CanadianOutput.getOutstandingTransactions(version2,sendToItrans,carrier,prov));
        retVal += "Outstanding Transactions#" + scriptNum.ToString() + " successful.\r\n";
        return retVal;
    }

    public static String runOne() throws Exception {
        List<Etrans> etransRequests = new List<Etrans>();
        //Claim claim=Claims.GetClaim(ClaimTC.ClaimNums[0]);
        Carrier carrier = Carriers.getCanadian("666666");
        carrier.CanadianEncryptionMethod = 1;
        RefSupport<List<Etrans>> refVar___0 = new RefSupport<List<Etrans>>();
        String retval = Run(1, false, false, carrier, refVar___0);
        etransRequests = refVar___0.getValue();
        return retval;
    }

    /**
    * /EOB
    */
    //etransRequests[0].PatNum=claim.PatNum;
    //etransRequests[0].PlanNum=claim.PlanNum;
    //etransRequests[0].InsSubNum=claim.InsSubNum;
    //string message=EtransMessageTexts.GetMessageText(etransRequests[0].EtransMessageTextNum);
    //FormCCDPrint FormP=new FormCCDPrint(etransRequests[0],message);//Print the form.
    //FormP.Print();
    /**
    * /Email
    */
    //etransRequests[1].PatNum=claim.PatNum;
    //etransRequests[1].PlanNum=claim.PlanNum;
    //etransRequests[1].InsSubNum=claim.InsSubNum;
    //message=EtransMessageTexts.GetMessageText(etransRequests[1].EtransMessageTextNum);
    //FormP=new FormCCDPrint(etransRequests[1],message);//Print the form.
    //FormP.Print();
    public static String runTwo() throws Exception {
        List<Etrans> etransRequests = new List<Etrans>();
        //Claim claim=Claims.GetClaim(ClaimTC.ClaimNums[6]);
        Carrier carrier = Carriers.getCanadian("888888");
        RefSupport<List<Etrans>> refVar___1 = new RefSupport<List<Etrans>>();
        String retval = Run(2, false, false, carrier, refVar___1);
        etransRequests = refVar___1.getValue();
        return retval;
    }

    /**
    * /EOB
    */
    //etransRequests[0].PatNum=claim.PatNum;
    //etransRequests[0].PlanNum=claim.PlanNum;
    //etransRequests[0].InsSubNum=claim.InsSubNum;
    //string message=EtransMessageTexts.GetMessageText(etransRequests[0].EtransMessageTextNum);
    //FormCCDPrint FormP=new FormCCDPrint(etransRequests[0],message);//Print the form.
    //FormP.Print();
    public static String runThree() throws Exception {
        List<Etrans> etransRequests = new List<Etrans>();
        //Claim claim=Claims.GetClaim(ClaimTC.ClaimNums[]);
        Carrier carrier = Carriers.getCanadian("777777");
        RefSupport<List<Etrans>> refVar___2 = new RefSupport<List<Etrans>>();
        String retval = Run(3, false, false, carrier, refVar___2);
        etransRequests = refVar___2.getValue();
        return retval;
    }

}


/**
* /EOB
*/
//etransRequests[0].PatNum=claim.PatNum;
//etransRequests[0].PlanNum=claim.PlanNum;
//etransRequests[0].InsSubNum=claim.InsSubNum;
//string message=EtransMessageTexts.GetMessageText(etransRequests[0].EtransMessageTextNum);
//FormCCDPrint FormP=new FormCCDPrint(etransRequests[0],message);//Print the form.
//FormP.Print();