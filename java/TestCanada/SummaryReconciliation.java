//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.Eclaims.CanadianOutput;
import OpenDentBusiness.CanadianNetwork;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import TestCanada.CarrierTC;

public class SummaryReconciliation   
{
    private static String run(int scriptNum, Carrier carrier, CanadianNetwork network, Provider prov, RefSupport<Etrans> etrans, DateTime reconciliationDate) throws Exception {
        String retVal = "";
        etrans.setValue(CanadianOutput.getSummaryReconciliation(carrier,network,prov,reconciliationDate));
        retVal += "Summary Reconciliation#" + scriptNum.ToString() + " successful.\r\n";
        return retVal;
    }

    public static String runOne() throws Exception {
        long carrierNum = CarrierTC.getCarrierNumById("666666");
        Carrier carrier = Carriers.getCarrier(carrierNum);
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        Etrans etransAck;
        RefSupport<Etrans> refVar___0 = new RefSupport<Etrans>();
        resVar___0 = run(1,carrier,null,prov,refVar___0,new DateTime(1999, 6, 1));
        etransAck = refVar___0.getValue();
        return resVar___0;
    }

    public static String runTwo() throws Exception {
        CanadianNetwork network = new CanadianNetwork();
        network.Descript = "Network 2";
        network.Abbrev = "Network 2";
        network.CanadianNetworkNum = 2;
        network.CanadianTransactionPrefix = "A";
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        Etrans etransAck;
        RefSupport<Etrans> refVar___1 = new RefSupport<Etrans>();
        resVar___1 = run(2,null,network,prov,refVar___1,new DateTime(1999, 6, 1));
        etransAck = refVar___1.getValue();
        return resVar___1;
    }

    public static String runThree() throws Exception {
        long carrierNum = CarrierTC.getCarrierNumById("111555");
        Carrier carrier = Carriers.getCarrier(carrierNum);
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        Etrans etransAck;
        RefSupport<Etrans> refVar___2 = new RefSupport<Etrans>();
        resVar___2 = run(3,carrier,null,prov,refVar___2,new DateTime(1999, 6, 1));
        etransAck = refVar___2.getValue();
        return resVar___2;
    }

}


