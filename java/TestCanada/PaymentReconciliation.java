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
import TestCanada.CarrierTC;

public class PaymentReconciliation   
{
    private static String run(int scriptNum, Carrier carrier, Provider treatProv, Provider billingProv, DateTime reconciliationDate, RefSupport<List<Etrans>> etransAcks) throws Exception {
        String retVal = "";
        etransAcks.setValue(CanadianOutput.getPaymentReconciliations(carrier,treatProv,billingProv,reconciliationDate));
        retVal += "Payment Reconciliation#" + scriptNum.ToString() + " successful.\r\n";
        return retVal;
    }

    public static String runOne() throws Exception {
        long carrierNum = CarrierTC.getCarrierNumById("666666");
        Carrier carrier = Carriers.getCarrier(carrierNum);
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        List<Etrans> etransAcks = new List<Etrans>();
        RefSupport<List<Etrans>> refVar___0 = new RefSupport<List<Etrans>>();
        resVar___0 = Run(1, carrier, prov, prov, new DateTime(1999, 6, 16), refVar___0);
        etransAcks = refVar___0.getValue();
        return resVar___0;
    }

    public static String runTwo() throws Exception {
        long carrierNum = CarrierTC.getCarrierNumById("777777");
        Carrier carrier = Carriers.getCarrier(carrierNum);
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        List<Etrans> etransAcks = new List<Etrans>();
        RefSupport<List<Etrans>> refVar___1 = new RefSupport<List<Etrans>>();
        resVar___1 = Run(1, carrier, prov, prov, new DateTime(1999, 6, 16), refVar___1);
        etransAcks = refVar___1.getValue();
        return resVar___1;
    }

    public static String runThree() throws Exception {
        long carrierNum = CarrierTC.getCarrierNumById("111555");
        Carrier carrier = Carriers.getCarrier(carrierNum);
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        List<Etrans> etransAcks = new List<Etrans>();
        RefSupport<List<Etrans>> refVar___2 = new RefSupport<List<Etrans>>();
        resVar___2 = Run(1, carrier, prov, prov, new DateTime(1999, 6, 16), refVar___2);
        etransAcks = refVar___2.getValue();
        return resVar___2;
    }

}


