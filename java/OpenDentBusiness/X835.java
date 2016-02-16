//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:14 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X12Segment;

/**
* X12 835 Health Care Claim Payment/Advice. This transaction type is a response to an 837 claim submission. The 835 will always come after a 277 is received and a 277 will always come after a 999. Neither the 277 nor the 999 are required, so it is possible that an 835 will be received directly after the 837. The 835 is not required either, so it is possible that none of the 997, 999, 277 or 835 reports will be returned from the carrier.
*/
public class X835  extends X12object 
{
    /**
    * All segments within the transaction.
    */
    private List<X12Segment> segments = new List<X12Segment>();
    /**
    * BPR segment (pg. 69). Financial Information segment. Required.
    */
    private X12Segment segBPR;
    /**
    * TRN segment (pg. 77). Reassociation Trace Number segment. Required.
    */
    private X12Segment segTRN;
    /**
    * N1*PR segment of loop 1000A (pg. 87). Payer Identification segment. Required.
    */
    private X12Segment segN1_PR;
    /**
    * N3 segment of loop 1000A (pg. 89). Payer Address. Required.
    */
    private X12Segment segN3_PR;
    /**
    * N4 segment of loop 1000A (pg. 90). Payer City, Sate, Zip code. Required.
    */
    private X12Segment segN4_PR;
    /**
    * PER*BL segment of loop 1000A (pg. 97). Payer technical contact information. Required. Can repeat more than once, but we only are about the first occurrence.
    */
    private X12Segment segPER_BL;
    /**
    * N1*PE segment of loop 1000B (pg. 102). Payee identification. Required. We include this information because it could be helpful for those customers who are using clinics.
    */
    private X12Segment segN1_PE;
    /**
    * CLP of loop 2100 (pg. 123). Claim payment information.
    */
    private List<int> segNumsCLP = new List<int>();
    /**
    * SVC of loop 2110 (pg. 186). Service (procedure) payment information. One sub-list for each claim (CLP segment).
    */
    private List<List<int>> segNumsSVC = new List<List<int>>();
    /**
    * PLB segments (pg.217). Provider Adjustment. Situational. This is the footer and table 3 if pesent.
    */
    private List<int> segNumsPLB = new List<int>();
    public static boolean is835(X12object xobj) throws Exception {
        if (xobj.FunctGroups.Count != 1)
        {
            return false;
        }
         
        //Exactly 1 GS segment in each 835.
        if (StringSupport.equals(xobj.FunctGroups[0].Header.Get(1), "HP"))
        {
            return true;
        }
         
        return false;
    }

    //GS01 (pg. 279)
    public X835(String messageText) throws Exception {
        super(messageText);
        segments = FunctGroups[0].Transactions[0].Segments;
        //The GS segment contains exactly one ST segment below it.
        segBPR = segments[0];
        //Always present, because required.
        segTRN = segments[1];
        //Always present, because required.
        segNumsCLP = new List<int>();
        segNumsSVC = new List<List<int>>();
        List<int> segNumsSVC_cur = new List<int>();
        for (int i = 0;i < segments.Count;i++)
        {
            X12Segment seg = segments[i];
            if (StringSupport.equals(seg.SegmentID, "N1") && StringSupport.equals(seg.get(1), "PR"))
            {
                segN1_PR = seg;
                segN3_PR = segments[i + 1];
                segN4_PR = segments[i + 2];
            }
            else if (StringSupport.equals(seg.SegmentID, "PER") && StringSupport.equals(seg.get(1), "BL"))
            {
                if (segPER_BL == null)
                {
                    //This segment can repeat. We only care about the first occurrence.
                    segPER_BL = seg;
                }
                 
            }
            else if (StringSupport.equals(seg.SegmentID, "N1") && StringSupport.equals(seg.get(1), "PE"))
            {
                segN1_PE = seg;
            }
            else if (StringSupport.equals(seg.SegmentID, "CLP"))
            {
                //The CLP segment only exists is within the 2100 loop.
                segNumsCLP.Add(i);
                segNumsSVC.Add(segNumsSVC_cur);
                segNumsSVC_cur = new List<int>();
            }
            else //Start a new list of procedures.
            if (StringSupport.equals(seg.SegmentID, "SVC"))
            {
                //The SVC segment only exists is within the 2100 loop.
                segNumsSVC_cur.Add(i);
            }
            else if (StringSupport.equals(seg.SegmentID, "PLB"))
            {
                segNumsPLB.Add(i);
            }
                  
        }
        segNumsSVC.Add(segNumsSVC_cur);
    }

    /**
    * Gets the description for the transaction handling code in Table 1 (Header) BPR01. Required.
    */
    public String getTransactionHandlingCodeDescription() throws Exception {
        String transactionHandlingCode = segBPR.get(1);
        if (StringSupport.equals(transactionHandlingCode, "C"))
        {
            return "Payment Accompanies Remittance Advice";
        }
        else if (StringSupport.equals(transactionHandlingCode, "D"))
        {
            return "Make Payment Only";
        }
        else if (StringSupport.equals(transactionHandlingCode, "H"))
        {
            return "Notification Only";
        }
        else if (StringSupport.equals(transactionHandlingCode, "I"))
        {
            return "Remittance Information Only";
        }
        else if (StringSupport.equals(transactionHandlingCode, "P"))
        {
            return "Prenotification of Future Transfers";
        }
        else if (StringSupport.equals(transactionHandlingCode, "U"))
        {
            return "Split Payment and Remittance";
        }
        else if (StringSupport.equals(transactionHandlingCode, "X"))
        {
            return "Handling Party's Option to Split Payment and Remittance";
        }
               
        return "UNKNOWN";
    }

    /**
    * Gets the payment credit or debit amount in Table 1 (Header) BPR02. Required.
    */
    public String getPaymentAmount() throws Exception {
        return segBPR.get(2);
    }

    /**
    * Gets the payment credit or debit flag in Table 1 (Header) BPR03. Returns "Credit" or "Debit". Required.
    */
    public String getCreditDebit() throws Exception {
        String creditDebitFlag = segBPR.get(3);
        if (StringSupport.equals(creditDebitFlag, "C"))
        {
            return "Credit";
        }
        else if (StringSupport.equals(creditDebitFlag, "D"))
        {
            return "Debit";
        }
          
        return "";
    }

    /**
    * Gets the description for the payment method in Table 1 (Header) BPR04. Required.
    */
    public String getPaymentMethodDescription() throws Exception {
        String paymentMethodCode = segBPR.get(4);
        if (StringSupport.equals(paymentMethodCode, "ACH"))
        {
            return "Automated Clearing House (ACH)";
        }
        else if (StringSupport.equals(paymentMethodCode, "BOP"))
        {
            return "Financial Institution Option";
        }
        else if (StringSupport.equals(paymentMethodCode, "CHK"))
        {
            return "Check";
        }
        else if (StringSupport.equals(paymentMethodCode, "FWT"))
        {
            return "Federal Reserve Funds/Wire Transfer - Nonrepetitive";
        }
        else if (StringSupport.equals(paymentMethodCode, "NON"))
        {
            return "Non-payment Data";
        }
             
        return "UNKNOWN";
    }

    /**
    * Gets the last 4 digits of the account number for the receiving company (the provider office) in Table 1 (Header) BPR15. Situational. If not present, then returns empty string.
    */
    public String getAccountNumReceivingShort() throws Exception {
        String accountNumber = segBPR.get(15);
        if (accountNumber.Length <= 4)
        {
            return accountNumber;
        }
         
        return accountNumber.Substring(accountNumber.Length - 4);
    }

    /**
    * Gets the effective payment date in Table 1 (Header) BPR16. Required.
    */
    public DateTime getDateEffective() throws Exception {
        String dateEffectiveStr = segBPR.get(16);
        //BPR16 will be blank if the payment is a check.
        if (dateEffectiveStr.Length < 8)
        {
            return DateTime.MinValue;
        }
         
        int dateEffectiveYear = int.Parse(dateEffectiveStr.Substring(0, 4));
        int dateEffectiveMonth = int.Parse(dateEffectiveStr.Substring(4, 2));
        int dateEffectiveDay = int.Parse(dateEffectiveStr.Substring(6, 2));
        return new DateTime(dateEffectiveYear, dateEffectiveMonth, dateEffectiveDay);
    }

    /**
    * Gets the check number or transaction reference number in Table 1 (Header) TRN02. Required.
    */
    public String getTransactionReferenceNumber() throws Exception {
        return segTRN.get(2);
    }

    /**
    * Gets the payer name in Table 1 (Header) N102. Required.
    */
    public String getPayerName() throws Exception {
        return segN1_PR.get(2);
    }

    /**
    * Gets the payer electronic ID in Table 1 (Header) N104 of segment N1*PR. Situational. If not present, then returns empty string.
    */
    public String getPayerID() throws Exception {
        if (segN1_PR.Elements.Length >= 4)
        {
            return segN1_PR.get(4);
        }
         
        return "";
    }

    /**
    * Gets the payer address line 1 in Table 1 (Header) N301 of segment N1*PR. Required.
    */
    public String getPayerAddress1() throws Exception {
        return segN3_PR.get(1);
    }

    /**
    * Gets the payer city name in Table 1 (Header) N401 of segment N1*PR. Required.
    */
    public String getPayerCityName() throws Exception {
        return segN4_PR.get(1);
    }

    /**
    * Gets the payer state in Table 1 (Header) N402 of segment N1*PR. Required when in USA or Canada.
    */
    public String getPayerState() throws Exception {
        return segN4_PR.get(2);
    }

    /**
    * Gets the payer zip code in Table 1 (Header) N403 of segment N1*PR. Required when in USA or Canada.
    */
    public String getPayerZip() throws Exception {
        return segN4_PR.get(3);
    }

    /**
    * Gets the contact information from segment PER*BL. Phone/email in PER04 or the contact phone/email in PER06 or both. If neither PER04 nor PER06 are present, then returns empty string.
    */
    public String getPayerContactInfo() throws Exception {
        String contact_info = "";
        if (segPER_BL.Elements.Length >= 4 && !StringSupport.equals(segPER_BL.get(4), ""))
        {
            //Contact number 1.
            contact_info = segPER_BL.get(4);
        }
         
        if (segPER_BL.Elements.Length >= 6 && !StringSupport.equals(segPER_BL.get(6), ""))
        {
            //Contact number 2.
            if (!StringSupport.equals(contact_info, ""))
            {
                contact_info += " or ";
            }
             
            contact_info += segPER_BL.get(6);
        }
         
        if (segPER_BL.Elements.Length >= 8 && !StringSupport.equals(segPER_BL.get(8), ""))
        {
            //Telephone extension for contact number 2.
            if (!StringSupport.equals(contact_info, ""))
            {
                contact_info += " x";
            }
             
            contact_info += segPER_BL.get(8);
        }
         
        return contact_info;
    }

    /**
    * Gets the payee name in Table 1 (Header) N102 of segment N1*PE. Required.
    */
    public String getPayeeName() throws Exception {
        return segN1_PE.get(2);
    }

    /**
    * Gets a human readable description for the identifiation code qualifier found in N103 of segment N1*PE. Required.
    */
    public String getPayeeIdType() throws Exception {
        String qualifier = segN1_PE.get(3);
        if (StringSupport.equals(qualifier, "FI"))
        {
            return "TIN";
        }
        else if (StringSupport.equals(qualifier, "XV"))
        {
            return "Medicaid ID";
        }
        else if (StringSupport.equals(qualifier, "XX"))
        {
            return "NPI";
        }
           
        return "";
    }

    /**
    * Gets the payee identification number found in N104 of segment N1*PE. Required. Usually the NPI number.
    */
    public String getPayeeId() throws Exception {
        return segN1_PE.get(4);
    }

    /**
    * CLP01 in loop 2100. Referred to in this format as a Patient Control Number. Do this first to get a list of all claim tracking numbers that are contained within this 835.  Then, for each claim tracking number, we can later retrieve specific information for that single claim. The claim tracking numbers correspond to CLM01 exactly as submitted in the 837. We refer to CLM01 as the claim identifier on our end. We allow more than just digits in our claim identifiers, so we must return a list of strings.
    */
    public List<String> getClaimTrackingNumbers() throws Exception {
        List<String> retVal = new List<String>();
        for (int i = 0;i < segNumsCLP.Count;i++)
        {
            X12Segment seg = segments[segNumsCLP[i]];
            //CLP segment.
            retVal.Add(seg.get(1));
        }
        return retVal;
    }

    //CLP01
    /**
    * Result will contain strings in the following order: Claim Status Code Description (CLP02), Total Claim Charge Amount (CLP03), Claim Payment Amount (CLP04), Patient Responsibility Amount (CLP05), Payer Claim Control Number (CLP07).
    */
    public String[] getClaimInfo(String trackingNumber) throws Exception {
        String[] result = new String[5];
        for (int i = 0;i < result.Length;i++)
        {
            result[i] = "";
        }
        for (int i = 0;i < segNumsCLP.Count;i++)
        {
            int segNum = segNumsCLP[i];
            X12Segment segCLP = segments[segNum];
            if (!StringSupport.equals(segCLP.get(1), trackingNumber))
            {
                continue;
            }
             
            //CLP01 Patient Control Number
            result[0] = getClaimStatusDescriptionForCode(segCLP.get(2));
            //CLP02 Claim Status Code Description
            result[1] = segCLP.get(3);
            //CLP03 Total Claim Charge Amount
            result[2] = segCLP.get(4);
            //CLP04 Claim Payment Amount
            result[3] = segCLP.get(5);
            //CLP05 Patient Responsibility Amount
            result[4] = segCLP.get(7);
            break;
        }
        return result;
    }

    //CLP07 Payer Claim Control Number
    private String getClaimStatusDescriptionForCode(String claimStatusCode) throws Exception {
        String claimStatusCodeDescript = "";
        if (StringSupport.equals(claimStatusCode, "1"))
        {
            claimStatusCodeDescript = "Processed as Primary";
        }
        else if (StringSupport.equals(claimStatusCode, "2"))
        {
            claimStatusCodeDescript = "Processed as Secondary";
        }
        else if (StringSupport.equals(claimStatusCode, "3"))
        {
            claimStatusCodeDescript = "Processed as Tertiary";
        }
        else if (StringSupport.equals(claimStatusCode, "4"))
        {
            claimStatusCodeDescript = "Denied";
        }
        else if (StringSupport.equals(claimStatusCode, "19"))
        {
            claimStatusCodeDescript = "Processed as Primary, Forwarded to Additional Payer(s)";
        }
        else if (StringSupport.equals(claimStatusCode, "20"))
        {
            claimStatusCodeDescript = "Processed as Secondary, Forwarded to Additional Payer(s)";
        }
        else if (StringSupport.equals(claimStatusCode, "21"))
        {
            claimStatusCodeDescript = "Processed as Tertiary, Forwarded to Additional Payer(s)";
        }
        else if (StringSupport.equals(claimStatusCode, "22"))
        {
            claimStatusCodeDescript = "Reversal of Previous Payment";
        }
        else if (StringSupport.equals(claimStatusCode, "23"))
        {
            claimStatusCodeDescript = "Not Our Claim, Forwarded to Additional Payer(s)";
        }
        else if (StringSupport.equals(claimStatusCode, "25"))
        {
            claimStatusCodeDescript = "Predetermination Pricing Only - No Payment";
        }
                  
        return claimStatusCodeDescript;
    }

    /**
    * Each item returned contains a string[] with values:
    * 00 Provider NPI (PLB01),
    * 01 Fiscal Period Date (PLB02),
    * 02 ReasonCodeDescription,
    * 03 ReasonCode (PLB03-1 or PLB05-1 or PLB07-1 or PLB09-1 or PLB11-1 or PLB13-1 2/2),
    * 04 ReferenceIdentification (PLB03-2 or PLB05-2 or PLB07-2 or PLB09-2 or PLB11-2 or PLB13-2 1/50),
    * 05 Amount (PLB04 1/18).
    */
    public List<String[]> getProviderLevelAdjustments() throws Exception {
        List<String[]> result = new List<String[]>();
        for (int i = 0;i < segNumsPLB.Count;i++)
        {
            X12Segment segPLB = segments[segNumsPLB[i]];
            String provNPI = segPLB.get(1);
            //PLB01 is required.
            String dateFiscalPeriodStr = segPLB.get(2);
            //PLB02 is required.
            DateTime dateFiscalPeriod = DateTime.MinValue;
            try
            {
                int dateEffectiveYear = int.Parse(dateFiscalPeriodStr.Substring(0, 4));
                int dateEffectiveMonth = int.Parse(dateFiscalPeriodStr.Substring(4, 2));
                int dateEffectiveDay = int.Parse(dateFiscalPeriodStr.Substring(6, 2));
                dateFiscalPeriod = new DateTime(dateEffectiveYear, dateEffectiveMonth, dateEffectiveDay);
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            //Oh well, not very important infomration anyway.
            int segNumAdjCode = 3;
            while (segNumAdjCode < segPLB.Elements.Length)
            {
                //PLB03 is required.
                String reasonCode = segPLB.get(segNumAdjCode,1);
                //For each adjustment reason code, the reference identification is optional.
                String referenceIdentification = "";
                if (segPLB.get(3).Length > reasonCode.Length)
                {
                    referenceIdentification = segPLB.get(3,2);
                }
                 
                //For each adjustment reason code, an amount is required.
                String amount = segPLB.get(segNumAdjCode + 1);
                result.Add(new String[]{ provNPI, dateFiscalPeriod.ToShortDateString(), getDescriptForProvAdjCode(reasonCode), reasonCode, referenceIdentification, amount });
            }
        }
        return result;
    }

    /**
    * Used for the reason codes in the PLB segment.
    */
    private String getDescriptForProvAdjCode(String reasonCode) throws Exception {
        if (StringSupport.equals(reasonCode, "50"))
        {
            return "Late Charge";
        }
         
        if (StringSupport.equals(reasonCode, "51"))
        {
            return "Interest Penalty Charge";
        }
         
        if (StringSupport.equals(reasonCode, "72"))
        {
            return "Authorized Return";
        }
         
        if (StringSupport.equals(reasonCode, "90"))
        {
            return "Early Payment Allowance";
        }
         
        if (StringSupport.equals(reasonCode, "AH"))
        {
            return "Origination Fee";
        }
         
        if (StringSupport.equals(reasonCode, "AM"))
        {
            return "Applied to Borrower's Account";
        }
         
        if (StringSupport.equals(reasonCode, "AP"))
        {
            return "Acceleration of Benefits";
        }
         
        if (StringSupport.equals(reasonCode, "B2"))
        {
            return "Rebate";
        }
         
        if (StringSupport.equals(reasonCode, "B3"))
        {
            return "Recovery Allowance";
        }
         
        if (StringSupport.equals(reasonCode, "BD"))
        {
            return "Bad Debt Adjustment";
        }
         
        if (StringSupport.equals(reasonCode, "BN"))
        {
            return "Bonus";
        }
         
        if (StringSupport.equals(reasonCode, "C5"))
        {
            return "Temporary Allowance";
        }
         
        if (StringSupport.equals(reasonCode, "CR"))
        {
            return "Capitation Interest";
        }
         
        if (StringSupport.equals(reasonCode, "CS"))
        {
            return "Adjustment";
        }
         
        if (StringSupport.equals(reasonCode, "CT"))
        {
            return "Capitation Payment";
        }
         
        if (StringSupport.equals(reasonCode, "CV"))
        {
            return "Capital Passthru";
        }
         
        if (StringSupport.equals(reasonCode, "CW"))
        {
            return "Certified Registered Nurse Anesthetist Passthru";
        }
         
        if (StringSupport.equals(reasonCode, "DM"))
        {
            return "Direct Medical Education Passthru";
        }
         
        if (StringSupport.equals(reasonCode, "E3"))
        {
            return "Withholding";
        }
         
        if (StringSupport.equals(reasonCode, "FB"))
        {
            return "Forwarding Balance";
        }
         
        if (StringSupport.equals(reasonCode, "FC"))
        {
            return "Fund Allocation";
        }
         
        if (StringSupport.equals(reasonCode, "GO"))
        {
            return "Graduate Medical Education Passthru";
        }
         
        if (StringSupport.equals(reasonCode, "HM"))
        {
            return "Hemophilia Clotting Factor Supplement";
        }
         
        if (StringSupport.equals(reasonCode, "IP"))
        {
            return "Incentive Premium Payment";
        }
         
        if (StringSupport.equals(reasonCode, "IR"))
        {
            return "Internal Revenue Service Withholding";
        }
         
        if (StringSupport.equals(reasonCode, "IS"))
        {
            return "Interim Settlement";
        }
         
        if (StringSupport.equals(reasonCode, "J1"))
        {
            return "Nonreimbursable";
        }
         
        if (StringSupport.equals(reasonCode, "L3"))
        {
            return "Penalty";
        }
         
        if (StringSupport.equals(reasonCode, "L6"))
        {
            return "Interest Owed";
        }
         
        if (StringSupport.equals(reasonCode, "LE"))
        {
            return "Levy";
        }
         
        if (StringSupport.equals(reasonCode, "LS"))
        {
            return "Lump Sum";
        }
         
        if (StringSupport.equals(reasonCode, "OA"))
        {
            return "Organ Acquisition Passthru";
        }
         
        if (StringSupport.equals(reasonCode, "OB"))
        {
            return "Offset for Affiliated Providers";
        }
         
        if (StringSupport.equals(reasonCode, "PI"))
        {
            return "Periodic Interim Payment";
        }
         
        if (StringSupport.equals(reasonCode, "PL"))
        {
            return "Payment Final";
        }
         
        if (StringSupport.equals(reasonCode, "RA"))
        {
            return "Retro-activity Adjustment";
        }
         
        if (StringSupport.equals(reasonCode, "RE"))
        {
            return "Return on Equity";
        }
         
        if (StringSupport.equals(reasonCode, "SL"))
        {
            return "Student Loan Repayment";
        }
         
        if (StringSupport.equals(reasonCode, "TL"))
        {
            return "Third Party Liability";
        }
         
        if (StringSupport.equals(reasonCode, "WO"))
        {
            return "Overpayment Recovery";
        }
         
        if (StringSupport.equals(reasonCode, "WU"))
        {
            return "Unspecified Recovery";
        }
         
        return "Reason " + reasonCode;
    }

    /**
    * Code Source 139. The complete list can be found at: http://www.wpc-edi.com/reference/codelists/healthcare/claim-adjustment-reason-codes/ .
    * Used for claim and procedure reason codes.
    */
    private String getDescriptForReasonCode(String reasonCode) throws Exception {
        if (StringSupport.equals(reasonCode, "1"))
        {
            return "Deductible Amount";
        }
         
        if (StringSupport.equals(reasonCode, "2"))
        {
            return "Coinsurance Amount";
        }
         
        if (StringSupport.equals(reasonCode, "3"))
        {
            return "Co-payment Amount";
        }
         
        if (StringSupport.equals(reasonCode, "4"))
        {
            return "The procedure code is inconsistent with the modifier used or a required modifier is missing.";
        }
         
        if (StringSupport.equals(reasonCode, "5"))
        {
            return "The procedure code/bill type is inconsistent with the place of service.";
        }
         
        if (StringSupport.equals(reasonCode, "6"))
        {
            return "	The procedure/revenue code is inconsistent with the patient's age.";
        }
         
        if (StringSupport.equals(reasonCode, "7"))
        {
            return "The procedure/revenue code is inconsistent with the patient's gender.";
        }
         
        if (StringSupport.equals(reasonCode, "8"))
        {
            return "The procedure code is inconsistent with the provider type/specialty (taxonomy).";
        }
         
        if (StringSupport.equals(reasonCode, "9"))
        {
            return "The diagnosis is inconsistent with the patient's age.";
        }
         
        if (StringSupport.equals(reasonCode, "10"))
        {
            return "The diagnosis is inconsistent with the patient's gender.";
        }
         
        if (StringSupport.equals(reasonCode, "11"))
        {
            return "The diagnosis is inconsistent with the procedure.";
        }
         
        if (StringSupport.equals(reasonCode, "12"))
        {
            return "The diagnosis is inconsistent with the provider type.";
        }
         
        if (StringSupport.equals(reasonCode, "13"))
        {
            return "The date of death precedes the date of service.";
        }
         
        if (StringSupport.equals(reasonCode, "14"))
        {
            return "The date of birth follows the date of service.";
        }
         
        if (StringSupport.equals(reasonCode, "15"))
        {
            return "The authorization number is missing, invalid, or does not apply to the billed services or provider.";
        }
         
        if (StringSupport.equals(reasonCode, "16"))
        {
            return "Claim/service lacks information which is needed for adjudication.";
        }
         
        if (StringSupport.equals(reasonCode, "18"))
        {
            return "Exact duplicate claim/service";
        }
         
        if (StringSupport.equals(reasonCode, "19"))
        {
            return "This is a work-related injury/illness and thus the liability of the Worker's Compensation Carrier.";
        }
         
        if (StringSupport.equals(reasonCode, "20"))
        {
            return "This injury/illness is covered by the liability carrier.";
        }
         
        if (StringSupport.equals(reasonCode, "21"))
        {
            return "This injury/illness is the liability of the no-fault carrier.";
        }
         
        if (StringSupport.equals(reasonCode, "22"))
        {
            return "This care may be covered by another payer per coordination of benefits.";
        }
         
        if (StringSupport.equals(reasonCode, "23"))
        {
            return "The impact of prior payer(s) adjudication including payments and/or adjustments.";
        }
         
        if (StringSupport.equals(reasonCode, "24"))
        {
            return "Charges are covered under a capitation agreement/managed care plan.";
        }
         
        if (StringSupport.equals(reasonCode, "26"))
        {
            return "Expenses incurred prior to coverage.";
        }
         
        if (StringSupport.equals(reasonCode, "27"))
        {
            return "Expenses incurred after coverage terminated.";
        }
         
        if (StringSupport.equals(reasonCode, "29"))
        {
            return "The time limit for filing has expired.";
        }
         
        if (StringSupport.equals(reasonCode, "Patient cannot be identified as our insured."))
        {
            return "";
        }
         
        if (StringSupport.equals(reasonCode, "32"))
        {
            return "Our records indicate that this dependent is not an eligible dependent as defined.";
        }
         
        if (StringSupport.equals(reasonCode, "33"))
        {
            return "Insured has no dependent coverage.";
        }
         
        if (StringSupport.equals(reasonCode, "34"))
        {
            return "Insured has no coverage for newborns.";
        }
         
        if (StringSupport.equals(reasonCode, "35"))
        {
            return "Lifetime benefit maximum has been reached.";
        }
         
        if (StringSupport.equals(reasonCode, "39"))
        {
            return "Services denied at the time authorization/pre-certification was requested.";
        }
         
        if (StringSupport.equals(reasonCode, "40"))
        {
            return "Charges do not meet qualifications for emergent/urgent care. Note: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.";
        }
         
        if (StringSupport.equals(reasonCode, "44"))
        {
            return "Prompt-pay discount.";
        }
         
        if (StringSupport.equals(reasonCode, "45"))
        {
            return "Charge exceeds fee schedule/maximum allowable or contracted/legislated fee arrangement.";
        }
         
        if (StringSupport.equals(reasonCode, "49"))
        {
            return "These are non-covered services because this is a routine exam or screening procedure done in conjunction with a routine exam.";
        }
         
        if (StringSupport.equals(reasonCode, "50"))
        {
            return "These are non-covered services because this is not deemed a 'medical necessity' by the payer.";
        }
         
        if (StringSupport.equals(reasonCode, "51"))
        {
            return "These are non-covered services because this is a pre-existing condition.";
        }
         
        if (StringSupport.equals(reasonCode, "53"))
        {
            return "Services by an immediate relative or a member of the same household are not covered.";
        }
         
        if (StringSupport.equals(reasonCode, "54"))
        {
            return "Multiple physicians/assistants are not covered in this case.";
        }
         
        if (StringSupport.equals(reasonCode, "55"))
        {
            return "Procedure/treatment is deemed experimental/investigational by the payer.";
        }
         
        if (StringSupport.equals(reasonCode, "56"))
        {
            return "Procedure/treatment has not been deemed 'proven to be effective' by the payer.";
        }
         
        if (StringSupport.equals(reasonCode, "58"))
        {
            return "Treatment was deemed by the payer to have been rendered in an inappropriate or invalid place of service.";
        }
         
        if (StringSupport.equals(reasonCode, "59"))
        {
            return "Processed based on multiple or concurrent procedure rules.";
        }
         
        if (StringSupport.equals(reasonCode, "60"))
        {
            return "Charges for outpatient services are not covered when performed within a period of time prior to or after inpatient services.";
        }
         
        if (StringSupport.equals(reasonCode, "61"))
        {
            return "Penalty for failure to obtain second surgical opinion.";
        }
         
        if (StringSupport.equals(reasonCode, "66"))
        {
            return "Blood Deductible.";
        }
         
        if (StringSupport.equals(reasonCode, "69"))
        {
            return "Day outlier amount.";
        }
         
        if (StringSupport.equals(reasonCode, "70"))
        {
            return "Cost outlier - Adjustment to compensate for additional costs.";
        }
         
        if (StringSupport.equals(reasonCode, "74"))
        {
            return "Indirect Medical Education Adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "75"))
        {
            return "Direct Medical Education Adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "76"))
        {
            return "Disproportionate Share Adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "78"))
        {
            return "Non-Covered days/Room charge adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "85"))
        {
            return "Patient Interest Adjustment";
        }
         
        //Use only Group Code PR
        if (StringSupport.equals(reasonCode, "89"))
        {
            return "Professional fees removed from charges.";
        }
         
        if (StringSupport.equals(reasonCode, "90"))
        {
            return "Ingredient cost adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "91"))
        {
            return "Dispensing fee adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "94"))
        {
            return "Processed in Excess of charges.";
        }
         
        if (StringSupport.equals(reasonCode, "95"))
        {
            return "Plan procedures not followed.";
        }
         
        if (StringSupport.equals(reasonCode, "96"))
        {
            return "Non-covered charge(s).";
        }
         
        if (StringSupport.equals(reasonCode, "97"))
        {
            return "The benefit for this service is included in the payment/allowance for another service/procedure that has already been adjudicated.";
        }
         
        if (StringSupport.equals(reasonCode, "100"))
        {
            return "Payment made to patient/insured/responsible party/employer.";
        }
         
        if (StringSupport.equals(reasonCode, "101"))
        {
            return "Predetermination: anticipated payment upon completion of services or claim adjudication.";
        }
         
        if (StringSupport.equals(reasonCode, "102"))
        {
            return "Major Medical Adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "103"))
        {
            return "Provider promotional discount";
        }
         
        if (StringSupport.equals(reasonCode, "104"))
        {
            return "Managed care withholding.";
        }
         
        if (StringSupport.equals(reasonCode, "105"))
        {
            return "Tax withholding.";
        }
         
        if (StringSupport.equals(reasonCode, "106"))
        {
            return "Patient payment option/election not in effect.";
        }
         
        if (StringSupport.equals(reasonCode, "107"))
        {
            return "The related or qualifying claim/service was not identified on this claim.";
        }
         
        if (StringSupport.equals(reasonCode, "108"))
        {
            return "Rent/purchase guidelines were not met.";
        }
         
        if (StringSupport.equals(reasonCode, "109"))
        {
            return "Claim/service not covered by this payer/contractor.";
        }
         
        if (StringSupport.equals(reasonCode, "110"))
        {
            return "Billing date predates service date.";
        }
         
        if (StringSupport.equals(reasonCode, "111"))
        {
            return "Not covered unless the provider accepts assignment.";
        }
         
        if (StringSupport.equals(reasonCode, "112"))
        {
            return "Service not furnished directly to the patient and/or not documented.";
        }
         
        if (StringSupport.equals(reasonCode, "114"))
        {
            return "Procedure/product not approved by the Food and Drug Administration.";
        }
         
        if (StringSupport.equals(reasonCode, "115"))
        {
            return "Procedure postponed, canceled, or delayed.";
        }
         
        if (StringSupport.equals(reasonCode, "116"))
        {
            return "The advance indemnification notice signed by the patient did not comply with requirements.";
        }
         
        if (StringSupport.equals(reasonCode, "117"))
        {
            return "Transportation is only covered to the closest facility that can provide the necessary care.";
        }
         
        if (StringSupport.equals(reasonCode, "118"))
        {
            return "ESRD network support adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "119"))
        {
            return "Benefit maximum for this time period or occurrence has been reached.";
        }
         
        if (StringSupport.equals(reasonCode, "121"))
        {
            return "Indemnification adjustment - compensation for outstanding member responsibility.";
        }
         
        if (StringSupport.equals(reasonCode, "122"))
        {
            return "Psychiatric reduction.";
        }
         
        if (StringSupport.equals(reasonCode, "125"))
        {
            return "Submission/billing error(s).";
        }
         
        if (StringSupport.equals(reasonCode, "128"))
        {
            return "Newborn's services are covered in the mother's Allowance.";
        }
         
        if (StringSupport.equals(reasonCode, "129"))
        {
            return "Prior processing information appears incorrect.";
        }
         
        if (StringSupport.equals(reasonCode, "130"))
        {
            return "Claim submission fee.";
        }
         
        if (StringSupport.equals(reasonCode, "131"))
        {
            return "Claim specific negotiated discount.";
        }
         
        if (StringSupport.equals(reasonCode, "132"))
        {
            return "Prearranged demonstration project adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "133"))
        {
            return "The disposition of the claim/service is pending further review.";
        }
         
        //Use only with Group Code OA
        if (StringSupport.equals(reasonCode, "134"))
        {
            return "Technical fees removed from charges.";
        }
         
        if (StringSupport.equals(reasonCode, "135"))
        {
            return "Interim bills cannot be processed.";
        }
         
        if (StringSupport.equals(reasonCode, "136"))
        {
            return "Failure to follow prior payer's coverage rules.";
        }
         
        //Use Group Code OA
        if (StringSupport.equals(reasonCode, "137"))
        {
            return "Regulatory Surcharges, Assessments, Allowances or Health Related Taxes.";
        }
         
        if (StringSupport.equals(reasonCode, "138"))
        {
            return "Appeal procedures not followed or time limits not met.";
        }
         
        if (StringSupport.equals(reasonCode, "139"))
        {
            return "Contracted funding agreement - Subscriber is employed by the provider of services.";
        }
         
        if (StringSupport.equals(reasonCode, "140"))
        {
            return "Patient/Insured health identification number and name do not match.";
        }
         
        if (StringSupport.equals(reasonCode, "142"))
        {
            return "Monthly Medicaid patient liability amount.";
        }
         
        if (StringSupport.equals(reasonCode, "143"))
        {
            return "Portion of payment deferred.";
        }
         
        if (StringSupport.equals(reasonCode, "144"))
        {
            return "Incentive adjustment, e.g. preferred product/service.";
        }
         
        if (StringSupport.equals(reasonCode, "146"))
        {
            return "Diagnosis was invalid for the date(s) of service reported.";
        }
         
        if (StringSupport.equals(reasonCode, "147"))
        {
            return "Provider contracted/negotiated rate expired or not on file.";
        }
         
        if (StringSupport.equals(reasonCode, "148"))
        {
            return "Information from another provider was not provided or was insufficient/incomplete.";
        }
         
        if (StringSupport.equals(reasonCode, "149"))
        {
            return "Lifetime benefit maximum has been reached for this service/benefit category.";
        }
         
        if (StringSupport.equals(reasonCode, "150"))
        {
            return "Payer deems the information submitted does not support this level of service.";
        }
         
        if (StringSupport.equals(reasonCode, "151"))
        {
            return "Payment adjusted because the payer deems the information submitted does not support this many/frequency of services.";
        }
         
        if (StringSupport.equals(reasonCode, "152"))
        {
            return "Payer deems the information submitted does not support this length of service.";
        }
         
        if (StringSupport.equals(reasonCode, "153"))
        {
            return "Payer deems the information submitted does not support this dosage.";
        }
         
        if (StringSupport.equals(reasonCode, "154"))
        {
            return "Payer deems the information submitted does not support this day's supply.";
        }
         
        if (StringSupport.equals(reasonCode, "155"))
        {
            return "Patient refused the service/procedure.";
        }
         
        if (StringSupport.equals(reasonCode, "157"))
        {
            return "Service/procedure was provided as a result of an act of war.";
        }
         
        if (StringSupport.equals(reasonCode, "158"))
        {
            return "Service/procedure was provided outside of the United States.";
        }
         
        if (StringSupport.equals(reasonCode, "159"))
        {
            return "Service/procedure was provided as a result of terrorism.";
        }
         
        if (StringSupport.equals(reasonCode, "160"))
        {
            return "Injury/illness was the result of an activity that is a benefit exclusion.";
        }
         
        if (StringSupport.equals(reasonCode, "161"))
        {
            return "Provider performance bonus";
        }
         
        if (StringSupport.equals(reasonCode, "162"))
        {
            return "State-mandated Requirement for Property and Casualty, see Claim Payment Remarks Code for specific explanation.";
        }
         
        if (StringSupport.equals(reasonCode, "163"))
        {
            return "Attachment referenced on the claim was not received.";
        }
         
        if (StringSupport.equals(reasonCode, "164"))
        {
            return "Attachment referenced on the claim was not received in a timely fashion.";
        }
         
        if (StringSupport.equals(reasonCode, "165"))
        {
            return "Referral absent or exceeded.";
        }
         
        if (StringSupport.equals(reasonCode, "166"))
        {
            return "These services were submitted after this payers responsibility for processing claims under this plan ended.";
        }
         
        if (StringSupport.equals(reasonCode, "167"))
        {
            return "This (these) diagnosis(es) is (are) not covered.";
        }
         
        if (StringSupport.equals(reasonCode, "168"))
        {
            return "Service(s) have been considered under the patient's medical plan. Benefits are not available under this dental plan.";
        }
         
        if (StringSupport.equals(reasonCode, "169"))
        {
            return "Alternate benefit has been provided.";
        }
         
        if (StringSupport.equals(reasonCode, "170"))
        {
            return "Payment is denied when performed/billed by this type of provider.";
        }
         
        if (StringSupport.equals(reasonCode, "171"))
        {
            return "Payment is denied when performed/billed by this type of provider in this type of facility.";
        }
         
        if (StringSupport.equals(reasonCode, "172"))
        {
            return "Payment is adjusted when performed/billed by a provider of this specialty.";
        }
         
        if (StringSupport.equals(reasonCode, "173"))
        {
            return "Service was not prescribed by a physician.";
        }
         
        if (StringSupport.equals(reasonCode, "174"))
        {
            return "Service was not prescribed prior to delivery.";
        }
         
        if (StringSupport.equals(reasonCode, "175"))
        {
            return "Prescription is incomplete.";
        }
         
        if (StringSupport.equals(reasonCode, "176"))
        {
            return "Prescription is not current.";
        }
         
        if (StringSupport.equals(reasonCode, "177"))
        {
            return "Patient has not met the required eligibility requirements.";
        }
         
        if (StringSupport.equals(reasonCode, "178"))
        {
            return "Patient has not met the required spend down requirements.";
        }
         
        if (StringSupport.equals(reasonCode, "179"))
        {
            return "Patient has not met the required waiting requirements.";
        }
         
        if (StringSupport.equals(reasonCode, "180"))
        {
            return "Patient has not met the required residency requirements.";
        }
         
        if (StringSupport.equals(reasonCode, "181"))
        {
            return "Procedure code was invalid on the date of service.";
        }
         
        if (StringSupport.equals(reasonCode, "182"))
        {
            return "Procedure modifier was invalid on the date of service.";
        }
         
        if (StringSupport.equals(reasonCode, "183"))
        {
            return "The referring provider is not eligible to refer the service billed.";
        }
         
        if (StringSupport.equals(reasonCode, "184"))
        {
            return "The prescribing/ordering provider is not eligible to prescribe/order the service billed.";
        }
         
        if (StringSupport.equals(reasonCode, "185"))
        {
            return "The rendering provider is not eligible to perform the service billed.";
        }
         
        if (StringSupport.equals(reasonCode, "186"))
        {
            return "Level of care change adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "187"))
        {
            return "Consumer Spending Account payments.";
        }
         
        if (StringSupport.equals(reasonCode, "188"))
        {
            return "This product/procedure is only covered when used according to FDA recommendations.";
        }
         
        if (StringSupport.equals(reasonCode, "189"))
        {
            return "'Not otherwise classified' or 'unlisted' procedure code (CPT/HCPCS) was billed when there is a specific procedure code for this procedure/service.";
        }
         
        if (StringSupport.equals(reasonCode, "190"))
        {
            return "Payment is included in the allowance for a Skilled Nursing Facility (SNF) qualified stay.";
        }
         
        if (StringSupport.equals(reasonCode, "191"))
        {
            return "Not a work related injury/illness and thus not the liability of the workers' compensation carrier";
        }
         
        if (StringSupport.equals(reasonCode, "192"))
        {
            return "Non standard adjustment code from paper remittance.";
        }
         
        if (StringSupport.equals(reasonCode, "193"))
        {
            return "Original payment decision is being maintained. Upon review, it was determined that this claim was processed properly.";
        }
         
        if (StringSupport.equals(reasonCode, "194"))
        {
            return "Anesthesia performed by the operating physician, the assistant surgeon or the attending physician.";
        }
         
        if (StringSupport.equals(reasonCode, "195"))
        {
            return "Refund issued to an erroneous priority payer for this claim/service.";
        }
         
        if (StringSupport.equals(reasonCode, "197"))
        {
            return "Precertification/authorization/notification absent.";
        }
         
        if (StringSupport.equals(reasonCode, "198"))
        {
            return "Precertification/authorization exceeded.";
        }
         
        if (StringSupport.equals(reasonCode, "199"))
        {
            return "Revenue code and Procedure code do not match.";
        }
         
        if (StringSupport.equals(reasonCode, "200"))
        {
            return "Expenses incurred during lapse in coverage";
        }
         
        if (StringSupport.equals(reasonCode, "201"))
        {
            return "Workers' Compensation case settled. Patient is responsible for amount of this claim/service through WC 'Medicare set aside arrangement' or other agreement.";
        }
         
        //Use group code PR
        if (StringSupport.equals(reasonCode, "202"))
        {
            return "Non-covered personal comfort or convenience services.";
        }
         
        if (StringSupport.equals(reasonCode, "203"))
        {
            return "Discontinued or reduced service.";
        }
         
        if (StringSupport.equals(reasonCode, "204"))
        {
            return "This service/equipment/drug is not covered under the patient’s current benefit plan.";
        }
         
        if (StringSupport.equals(reasonCode, "205"))
        {
            return "Pharmacy discount card processing fee";
        }
         
        if (StringSupport.equals(reasonCode, "206"))
        {
            return "National Provider Identifier - missing.";
        }
         
        if (StringSupport.equals(reasonCode, "207"))
        {
            return "National Provider identifier - Invalid format";
        }
         
        if (StringSupport.equals(reasonCode, "208"))
        {
            return "National Provider Identifier - Not matched.";
        }
         
        if (StringSupport.equals(reasonCode, "209"))
        {
            return "Per regulatory or other agreement. The provider cannot collect this amount from the patient. However, this amount may be billed to subsequent payer. Refund to patient if collected.";
        }
         
        //Use Group code OA
        if (StringSupport.equals(reasonCode, "210"))
        {
            return "Payment adjusted because pre-certification/authorization not received in a timely fashion";
        }
         
        if (StringSupport.equals(reasonCode, "211"))
        {
            return "National Drug Codes (NDC) not eligible for rebate, are not covered.";
        }
         
        if (StringSupport.equals(reasonCode, "212"))
        {
            return "Administrative surcharges are not covered";
        }
         
        if (StringSupport.equals(reasonCode, "213"))
        {
            return "Non-compliance with the physician self referral prohibition legislation or payer policy.";
        }
         
        if (StringSupport.equals(reasonCode, "214"))
        {
            return "Workers' Compensation claim adjudicated as non-compensable. This Payer not liable for claim or service/treatment.";
        }
         
        if (StringSupport.equals(reasonCode, "215"))
        {
            return "Based on subrogation of a third party settlement";
        }
         
        if (StringSupport.equals(reasonCode, "216"))
        {
            return "Based on the findings of a review organization";
        }
         
        if (StringSupport.equals(reasonCode, "217"))
        {
            return "Based on payer reasonable and customary fees. No maximum allowable defined by legislated fee arrangement.";
        }
         
        if (StringSupport.equals(reasonCode, "218"))
        {
            return "Based on entitlement to benefits.";
        }
         
        if (StringSupport.equals(reasonCode, "219"))
        {
            return "Based on extent of injury.";
        }
         
        if (StringSupport.equals(reasonCode, "220"))
        {
            return "The applicable fee schedule/fee database does not contain the billed code. Please resubmit a bill with the appropriate fee schedule/fee database code(s) that best describe the service(s) provided and supporting documentation if required.";
        }
         
        if (StringSupport.equals(reasonCode, "221"))
        {
            return "Workers' Compensation claim is under investigation.";
        }
         
        if (StringSupport.equals(reasonCode, "222"))
        {
            return "Exceeds the contracted maximum number of hours/days/units by this provider for this period. This is not patient specific.";
        }
         
        if (StringSupport.equals(reasonCode, "223"))
        {
            return "Adjustment code for mandated federal, state or local law/regulation that is not already covered by another code and is mandated before a new code can be created.";
        }
         
        if (StringSupport.equals(reasonCode, "224"))
        {
            return "Patient identification compromised by identity theft. Identity verification required for processing this and future claims.";
        }
         
        if (StringSupport.equals(reasonCode, "225"))
        {
            return "Penalty or Interest Payment by Payer";
        }
         
        if (StringSupport.equals(reasonCode, "226"))
        {
            return "Information requested from the Billing/Rendering Provider was not provided or was insufficient/incomplete.";
        }
         
        if (StringSupport.equals(reasonCode, "227"))
        {
            return "Information requested from the patient/insured/responsible party was not provided or was insufficient/incomplete.";
        }
         
        if (StringSupport.equals(reasonCode, "228"))
        {
            return "Denied for failure of this provider, another provider or the subscriber to supply requested information to a previous payer for their adjudication.";
        }
         
        if (StringSupport.equals(reasonCode, "229"))
        {
            return "Partial charge amount not considered by Medicare due to the initial claim Type of Bill being 12X.";
        }
         
        //Use only with Group Code PR
        if (StringSupport.equals(reasonCode, "230"))
        {
            return "No available or correlating CPT/HCPCS code to describe this service.";
        }
         
        if (StringSupport.equals(reasonCode, "231"))
        {
            return "Mutually exclusive procedures cannot be done in the same day/setting.";
        }
         
        if (StringSupport.equals(reasonCode, "232"))
        {
            return "Institutional Transfer Amount.";
        }
         
        if (StringSupport.equals(reasonCode, "233"))
        {
            return "Services/charges related to the treatment of a hospital-acquired condition or preventable medical error.";
        }
         
        if (StringSupport.equals(reasonCode, "234"))
        {
            return "This procedure is not paid separately.";
        }
         
        if (StringSupport.equals(reasonCode, "235"))
        {
            return "Sales Tax";
        }
         
        if (StringSupport.equals(reasonCode, "236"))
        {
            return "This procedure or procedure/modifier combination is not compatible with another procedure or procedure/modifier combination provided on the same day according to the National Correct Coding Initiative.";
        }
         
        if (StringSupport.equals(reasonCode, "237"))
        {
            return "Legislated/Regulatory Penalty.";
        }
         
        if (StringSupport.equals(reasonCode, "238"))
        {
            return "Claim spans eligible and ineligible periods of coverage, this is the reduction for the ineligible period.";
        }
         
        //Use Group Code PR
        if (StringSupport.equals(reasonCode, "239"))
        {
            return "Claim spans eligible and ineligible periods of coverage. Rebill separate claims.";
        }
         
        if (StringSupport.equals(reasonCode, "240"))
        {
            return "The diagnosis is inconsistent with the patient's birth weight.";
        }
         
        if (StringSupport.equals(reasonCode, "241"))
        {
            return "Low Income Subsidy (LIS) Co-payment Amount";
        }
         
        if (StringSupport.equals(reasonCode, "242"))
        {
            return "Services not provided by network/primary care providers.";
        }
         
        if (StringSupport.equals(reasonCode, "243"))
        {
            return "Services not authorized by network/primary care providers.";
        }
         
        if (StringSupport.equals(reasonCode, "244"))
        {
            return "Payment reduced to zero due to litigation. Additional information will be sent following the conclusion of litigation.";
        }
         
        if (StringSupport.equals(reasonCode, "245"))
        {
            return "Provider performance program withhold.";
        }
         
        if (StringSupport.equals(reasonCode, "246"))
        {
            return "This non-payable code is for required reporting only.";
        }
         
        if (StringSupport.equals(reasonCode, "247"))
        {
            return "Deductible for Professional service rendered in an Institutional setting and billed on an Institutional claim.";
        }
         
        if (StringSupport.equals(reasonCode, "248"))
        {
            return "Coinsurance for Professional service rendered in an Institutional setting and billed on an Institutional claim.";
        }
         
        if (StringSupport.equals(reasonCode, "249"))
        {
            return "This claim has been identified as a readmission.";
        }
         
        //Use only with Group Code CO
        if (StringSupport.equals(reasonCode, "250"))
        {
            return "The attachment content received is inconsistent with the expected content.";
        }
         
        if (StringSupport.equals(reasonCode, "251"))
        {
            return "The attachment content received did not contain the content required to process this claim or service.";
        }
         
        if (StringSupport.equals(reasonCode, "252"))
        {
            return "An attachment is required to adjudicate this claim/service.";
        }
         
        if (StringSupport.equals(reasonCode, "A0"))
        {
            return "Patient refund amount.";
        }
         
        if (StringSupport.equals(reasonCode, "A1"))
        {
            return "Claim/Service denied.";
        }
         
        if (StringSupport.equals(reasonCode, "A5"))
        {
            return "Medicare Claim PPS Capital Cost Outlier Amount.";
        }
         
        if (StringSupport.equals(reasonCode, "A6"))
        {
            return "Prior hospitalization or 30 day transfer requirement not met.";
        }
         
        if (StringSupport.equals(reasonCode, "A7"))
        {
            return "Presumptive Payment Adjustment";
        }
         
        if (StringSupport.equals(reasonCode, "A8"))
        {
            return "Ungroupable DRG.";
        }
         
        if (StringSupport.equals(reasonCode, "B1"))
        {
            return "Non-covered visits.";
        }
         
        if (StringSupport.equals(reasonCode, "B4"))
        {
            return "Late filing penalty.";
        }
         
        if (StringSupport.equals(reasonCode, "B5"))
        {
            return "Coverage/program guidelines were not met or were exceeded.";
        }
         
        if (StringSupport.equals(reasonCode, "B7"))
        {
            return "This provider was not certified/eligible to be paid for this procedure/service on this date of service.";
        }
         
        if (StringSupport.equals(reasonCode, "B8"))
        {
            return "Alternative services were available, and should have been utilized.";
        }
         
        if (StringSupport.equals(reasonCode, "B9"))
        {
            return "Patient is enrolled in a Hospice.";
        }
         
        if (StringSupport.equals(reasonCode, "B10"))
        {
            return "Allowed amount has been reduced because a component of the basic procedure/test was paid. The beneficiary is not liable for more than the charge limit for the basic procedure/test.";
        }
         
        if (StringSupport.equals(reasonCode, "B11"))
        {
            return "The claim/service has been transferred to the proper payer/processor for processing. Claim/service not covered by this payer/processor.";
        }
         
        if (StringSupport.equals(reasonCode, "B12"))
        {
            return "Services not documented in patients' medical records.";
        }
         
        if (StringSupport.equals(reasonCode, "B13"))
        {
            return "Previously paid. Payment for this claim/service may have been provided in a previous payment.";
        }
         
        if (StringSupport.equals(reasonCode, "B14"))
        {
            return "Only one visit or consultation per physician per day is covered.";
        }
         
        if (StringSupport.equals(reasonCode, "B15"))
        {
            return "This service/procedure requires that a qualifying service/procedure be received and covered. The qualifying other service/procedure has not been received/adjudicated.";
        }
         
        if (StringSupport.equals(reasonCode, "B16"))
        {
            return "'New Patient' qualifications were not met.";
        }
         
        if (StringSupport.equals(reasonCode, "B20"))
        {
            return "Procedure/service was partially or fully furnished by another provider.";
        }
         
        if (StringSupport.equals(reasonCode, "B22"))
        {
            return "This payment is adjusted based on the diagnosis.";
        }
         
        if (StringSupport.equals(reasonCode, "B23"))
        {
            return "Procedure billed is not authorized per your Clinical Laboratory Improvement Amendment (CLIA) proficiency test.";
        }
         
        if (StringSupport.equals(reasonCode, "W1"))
        {
            return "Workers' compensation jurisdictional fee schedule adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "W2"))
        {
            return "Payment reduced or denied based on workers' compensation jurisdictional regulations or payment policies, use only if no other code is applicable.";
        }
         
        if (StringSupport.equals(reasonCode, "W3"))
        {
            return "The Benefit for this Service is included in the payment/allowance for another service/procedure that has been performed on the same day.";
        }
         
        if (StringSupport.equals(reasonCode, "W4"))
        {
            return "Workers' Compensation Medical Treatment Guideline Adjustment.";
        }
         
        if (StringSupport.equals(reasonCode, "Y1"))
        {
            return "Payment denied based on Medical Payments Coverage (MPC) or Personal Injury Protection (PIP) Benefits jurisdictional regulations or payment policies, use only if no other code is applicable.";
        }
         
        if (StringSupport.equals(reasonCode, "Y2"))
        {
            return "Payment adjusted based on Medical Payments Coverage (MPC) or Personal Injury Protection (PIP) Benefits jurisdictional regulations or payment policies, use only if no other code is applicable.";
        }
         
        if (StringSupport.equals(reasonCode, "Y3"))
        {
            return "Medical Payments Coverage (MPC) or Personal Injury Protection (PIP) Benefits jurisdictional fee schedule adjustment.";
        }
         
        return "Reason " + reasonCode + ".";
    }

}


//Worst case, if we do not recognize the code, display it verbatim so the user can look it up.
//Example 1 From 835 Specification:
//ST*835*1234~
//BPR*C*150000*C*ACH*CTX*01*999999992*DA*123456*1512345678*01*999988880*DA*98765*20020913~
//TRN*1*12345*1512345678~
//DTM*405*20020916~
//N1*PR*INSURANCE COMPANY OF TIMBUCKTU~
//N3*1 MAIN STREET~
//N4*TIMBUCKTU*AK*89111~
//REF*2U*999~
//N1*PE*REGIONAL HOPE HOSPITAL*XX*6543210903~
//LX*110212~
//TS3*6543210903*11*20021231*1*211366.97****138018.4**73348.57~
//TS2*2178.45*1919.71**56.82*197.69*4.23~
//CLP*666123*1*211366.97*138018.4**MA*1999999444444*11*1~
//CAS*CO*45*73348.57~
//NM1*QC*1*JONES*SAM*O***HN*666666666A~
//MIA*0***138018.4~
//DTM*232*20020816~
//DTM*233*20020824~
//QTY*CA*8~
//LX*130212~
//TS3*6543210909*13*19961231*1*15000****11980.33**3019.67~
//CLP*777777*1*150000*11980.33**MB*1999999444445*13*1~
//CAS*CO*35*3019.67~
//NM1*QC*1*BORDER*LIZ*E***HN*996669999B~
//MOA***MA02~
//DTM*232*20020512~
//PLB*6543210903*20021231*CV:CP*-1.27~
//SE*28*1234~
//Example 2 From 835 Specification:
//ST*835*12233~
//BPR*I*945*C*ACH*CCP*01*888999777*DA*24681012*1935665544*01*111333555*DA*144444*20020316~
//TRN*1*71700666555*1935665544~
//DTM*405*20020314~
//N1*PR*RUSHMORE LIFE~
//N3*10 SOUTH AVENUE~
//N4*RAPID CITY*SD*55111~
//N1*PE*ACME MEDICAL CENTER*XX*5544667733~
//REF*TJ*777667755~
//LX*1~
//CLP*55545554444*1*800*450*300*12*94060555410000~
//CAS*CO*A2*50~
//NM1*QC*1*BUDD*WILLIAM****MI*33344555510~
//SVC*HC:99211*800*500~
//DTM*150*20020301~
//DTM*151*20020304~
//CAS*PR*1*300~
//CLP*8765432112*1*1200*495*600*12*9407779923000~
//CAS*CO*A2*55~
//NM1*QC*1*SETTLE*SUSAN****MI*44455666610~
//SVC*HC:93555*1200*550~
//DTM*150*20020310~
//DTM*151*20020312~
//CAS*PR*1*600~
//CAS*CO*45*50~
//SE*25*112233~
//Example 3 From 835 Specification:
//ST*835*0001~
//BPR*I*1222*C*CHK************20050412~
//TRN*1*0012524965*1559123456~
//REF*EV*030240928~
//DTM*405*20050412~
//N1*PR*YOUR TAX DOLLARS AT WORK~
//N3*481A00 DEER RUN ROAD~
//N4*WEST PALM BCH*FL*11114~
//N1*PE*ACME MEDICAL CENTER*FI*5999944521~
//N3*PO BOX 863382~
//N4*ORLANDO*FL*55115~
//REF*PQ*10488~
//LX*1~
//CLP*L0004828311*2*10323.64*912**12*05090256390*11*1~
//CAS*OA*23*9411.64~
//NM1*QC*1*TOWNSEND*WILLIAM*P***MI*XXX123456789~
//NM1*82*2*ACME MEDICAL CENTER*****BD*987~
//DTM*232*20050303~
//DTM*233*20050304~
//AMT*AU*912~
//LX*2~
//CLP*0001000053*2*751.50*310*220*12*50630626430~
//NM1*QC*1*BAKI*ANGI****MI*456789123~
//NM1*82*2*SMITH JONES PA*****BS*34426~
//DTM*232*20050106~
//DTM*233*20050106~
//SVC*HC>12345>26*166.5*30**1~
//DTM*472*20050106~
//CAS*OA*23*136.50~
//REF*1B*43285~
//AMT*AU*150~
//SVC*HC>66543>26*585*280*220*1~
//DTM*472*20050106~
//CAS*PR*1*150**2*70~
//CAS*CO*42*85~
//REF*1B*43285~
//AMT*AU*500~
//SE*38*0001~
//Example 4 From 835 Specification:
//ST*835*0001~
//BPR*I*187.50*C*CHK************20050412~
//TRN*1*0012524879*1559123456~
//REF*EV*030240928~
//DTM*405*20050412~
//N1*PR*YOUR TAX DOLLARS AT WORK~
//N3*481A00 DEER RUN ROAD~
//N4*WEST PALM BCH*FL*11114~
//N1*PE*ACME MEDICAL CENTER*FI*599944521~
//N3*PO BOX 863382~
//N4*ORLANDO*FL*55115~
//REF*PQ*10488~
//LX*1~
//CLP*0001000054*3*1766.5*187.50**12*50580155533~
//NM1*QC*1*ISLAND*ELLIS*E****MI*789123456~
//NM1*82*2*JONES JONES ASSOCIATES*****BS*AB34U~
//DTM*232*20050120~
//SVC*HC*24599*1766.5*187.50**1~
//DTM*472*20050120~
//CAS*OA*23*1579~
//REF*1B*44280~
//AMT*AU*1700~
//SE*38*0001~
//Example 5 From 835 Specification:
//ST*835*0001~
//BPR*I*34.00*C*CHK************20050318~
//TRN*1*0063158ABC*1566339911~
//REF*EV*030240928~
//DTM*405*20050318~
//N1*PR*YOUR TAX DOLLARS AT WORK~
//N3*481A00 DEER RUN ROAD~
//N4*WEST PALM BCH*FL*11114~
//N1*PE*ATONEWITHHEALTH*FI*3UR334563~
//N3*3501 JOHNSON STREET~
//N4*SUNSHINE*FL*12345~
//REF*PQ*11861~
//LX*1~
//CLP*0001000055*2*541*34**12*50650619501~
//NM1*QC*1*BRUCK*RAYMOND*W***MI*987654321~
//NM1*82*2*PROFESSIONAL TEST 1*****BS*34426~
//DTM*232*20050202~
//DTM*233*20050202~
//SVC*HC>55669*541*34**1~
//DTM*472*20050202~
//CAS*OA*23*516~
//CAS*OA*94*-9~
//REF*1B*44280~
//AMT*AU*550~
//SE*38*0001~