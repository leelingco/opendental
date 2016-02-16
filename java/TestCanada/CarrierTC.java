//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CanadianNetwork;
import OpenDentBusiness.CanadianNetworks;
import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;

public class CarrierTC   
{
    public static String setInitialCarriers() throws Exception {
        //We are starting with zero carriers
        CanadianNetwork network = new CanadianNetwork();
        network.Abbrev = "CDANET14";
        network.Descript = "CDANET14";
        network.CanadianTransactionPrefix = "CDANET14";
        CanadianNetworks.insert(network);
        Carrier carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "Carrier 1";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "04";
        carrier.ElectID = "666666";
        carrier.CanadianEncryptionMethod = 2;
        //claim_01 is implied
        //claimAck_11 is implied
        //claimEob_21 is implied
        carrier.CanadianSupportedTypes = CanSupTransTypes.CobClaimTransaction_07 | CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.ClaimReversalResponse_12 | CanSupTransTypes.PredeterminationSinglePage_03 | CanSupTransTypes.PredeterminationMultiPage_03 | CanSupTransTypes.RequestForOutstandingTrans_04 | CanSupTransTypes.EmailTransaction_24 | CanSupTransTypes.RequestForSummaryReconciliation_05 | CanSupTransTypes.RequestForPaymentReconciliation_06;
        Carriers.insert(carrier);
        //Carrier2---------------------------------------------------
        network = new CanadianNetwork();
        network.Abbrev = "A";
        network.Descript = "A";
        network.CanadianTransactionPrefix = "A";
        CanadianNetworks.insert(network);
        carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "Carrier 2";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "04";
        carrier.ElectID = "777777";
        carrier.CanadianEncryptionMethod = 1;
        //claim_01 is implied
        //claimAck_11 is implied
        //claimEob_21 is implied
        carrier.CanadianSupportedTypes = CanSupTransTypes.EligibilityTransaction_08 | CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.PredeterminationSinglePage_03 | CanSupTransTypes.RequestForOutstandingTrans_04 | CanSupTransTypes.EmailTransaction_24 | CanSupTransTypes.RequestForPaymentReconciliation_06;
        Carriers.insert(carrier);
        //Carrier3---------------------------------------------------
        network = new CanadianNetwork();
        network.Abbrev = "AB";
        network.Descript = "AB";
        network.CanadianTransactionPrefix = "AB";
        CanadianNetworks.insert(network);
        carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "Carrier 3";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "04";
        carrier.ElectID = "888888";
        carrier.CanadianEncryptionMethod = 2;
        //claim_01 is implied
        //claimAck_11 is implied
        //claimEob_21 is implied
        carrier.CanadianSupportedTypes = CanSupTransTypes.EligibilityTransaction_08 | CanSupTransTypes.CobClaimTransaction_07 | CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.PredeterminationSinglePage_03 | CanSupTransTypes.RequestForOutstandingTrans_04 | CanSupTransTypes.EmailTransaction_24 | CanSupTransTypes.RequestForPaymentReconciliation_06;
        Carriers.insert(carrier);
        //Carrier4---------------------------------------------------
        network = new CanadianNetwork();
        network.Abbrev = "ABC";
        network.Descript = "ABC";
        network.CanadianTransactionPrefix = "ABC";
        CanadianNetworks.insert(network);
        carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "Carrier 4";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "04";
        carrier.ElectID = "999111";
        carrier.CanadianEncryptionMethod = 2;
        //claim_01 is implied
        //claimAck_11 is implied
        //claimEob_21 is implied
        carrier.CanadianSupportedTypes = CanSupTransTypes.EligibilityTransaction_08 | CanSupTransTypes.CobClaimTransaction_07 | CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.PredeterminationSinglePage_03 | CanSupTransTypes.RequestForOutstandingTrans_04 | CanSupTransTypes.EmailTransaction_24 | CanSupTransTypes.RequestForPaymentReconciliation_06;
        Carriers.insert(carrier);
        //Carrier5---------------------------------------------------
        network = new CanadianNetwork();
        network.Abbrev = "V2CAR";
        network.Descript = "V2CAR";
        network.CanadianTransactionPrefix = "V2CAR";
        CanadianNetworks.insert(network);
        carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "Carrier 5";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "02";
        carrier.ElectID = "555555";
        carrier.CanadianEncryptionMethod = 0;
        //not applicable
        //claim_01 is implied
        //claimAck_11 is implied
        //claimEob_21 is implied
        carrier.CanadianSupportedTypes = CanSupTransTypes.EligibilityTransaction_08 | CanSupTransTypes.EligibilityResponse_18 | CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.PredeterminationSinglePage_03 | CanSupTransTypes.PredeterminationMultiPage_03;
        Carriers.insert(carrier);
        //---------------------------------------------------------
        //Used for Payment Reconciliation test #3 and Summary Reconciliation test #3
        network = new CanadianNetwork();
        network.Abbrev = "ABC";
        network.Descript = "ABC";
        network.CanadianTransactionPrefix = "ABC";
        CanadianNetworks.insert(network);
        carrier = new Carrier();
        carrier.IsCDA = true;
        carrier.CarrierName = "111555";
        carrier.CanadianNetworkNum = network.CanadianNetworkNum;
        carrier.CDAnetVersion = "04";
        carrier.ElectID = "111555";
        carrier.CanadianEncryptionMethod = 0;
        //not applicable
        carrier.CanadianSupportedTypes = CanSupTransTypes.PaymentReconciliation_16;
        Carriers.insert(carrier);
        return "Carrier objects set.\r\n";
    }

    public static long getCarrierNumById(String carrierId) throws Exception {
        for (int i = 0;i < Carriers.getListt().Length;i++)
        {
            if (StringSupport.equals(Carriers.getListt()[i].ElectID, carrierId))
            {
                return Carriers.getListt()[i].CarrierNum;
            }
             
        }
        return 0;
    }

    /**
    * encryptionMethod should be 1 or 2.
    */
    public static void setEncryptionMethod(long planNum, byte encryptionMethod) throws Exception {
        InsPlan plan = InsPlans.refreshOne(planNum);
        Carrier carrier = Carriers.getCarrier(plan.CarrierNum);
        if (carrier.CanadianEncryptionMethod != encryptionMethod)
        {
            carrier.CanadianEncryptionMethod = encryptionMethod;
            Carriers.update(carrier);
            Carriers.refreshCache();
        }
         
    }

    /**
    * version should be "02" or "04". Returns old version.
    */
    public static String setCDAnetVersion(long planNum, String version) throws Exception {
        InsPlan plan = InsPlans.refreshOne(planNum);
        Carrier carrier = Carriers.getCarrier(plan.CarrierNum);
        String oldVersion = carrier.CDAnetVersion;
        if (!StringSupport.equals(carrier.CDAnetVersion, version))
        {
            carrier.CDAnetVersion = version;
            Carriers.update(carrier);
            Carriers.refreshCache();
        }
         
        return oldVersion;
    }

}


