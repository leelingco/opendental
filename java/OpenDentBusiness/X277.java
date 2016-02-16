//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PIn;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X12Segment;

/**
* X12 277 Unsolicited Claim Status Notification. There is only one type of 277, but a 277 can be sent out unsolicited (without sending a request) or as a response to a 276 request.
*/
public class X277  extends X12object 
{
    private List<X12Segment> segments = new List<X12Segment>();
    /**
    * NM1 of loop 2100A.
    */
    private int segNumInfoSourceNM101 = new int();
    /**
    * NM1 of loop 2100B.
    */
    private int segNumInfoReceiverNM101 = new int();
    /**
    * NM1 of loop 2100C.
    */
    private List<int> segNumsBillingProviderNM1 = new List<int>();
    /**
    * NM1 of loop 2100D.
    */
    private List<int> segNumsPatientDetailNM1 = new List<int>();
    /**
    * TRN of loop 2200D.
    */
    private List<int> segNumsClaimTrackingNumberTRN = new List<int>();
    public static boolean is277(X12object xobj) throws Exception {
        if (xobj.FunctGroups.Count != 1)
        {
            return false;
        }
         
        //Exactly 1 GS segment in each 277.
        if (StringSupport.equals(xobj.FunctGroups[0].Header.Get(1), "HN"))
        {
            return true;
        }
         
        return false;
    }

    //GS01 (pgs. 139 & 7)
    public X277(String messageText) throws Exception {
        super(messageText);
        segments = FunctGroups[0].Transactions[0].Segments;
        //The GS segment contains exactly one ST segment below it.
        segNumInfoSourceNM101 = -1;
        segNumInfoReceiverNM101 = -1;
        segNumsBillingProviderNM1 = new List<int>();
        segNumsPatientDetailNM1 = new List<int>();
        segNumsClaimTrackingNumberTRN = new List<int>();
        for (int i = 0;i < segments.Count;i++)
        {
            X12Segment seg = segments[i];
            if (StringSupport.equals(seg.SegmentID, "NM1"))
            {
                String entityIdentifierCode = seg.get(1);
                if (StringSupport.equals(entityIdentifierCode, "AY") || StringSupport.equals(entityIdentifierCode, "PR"))
                {
                    segNumInfoSourceNM101 = i;
                    i += 4;
                }
                else if (StringSupport.equals(entityIdentifierCode, "41"))
                {
                    segNumInfoReceiverNM101 = i;
                    i += 3;
                    seg = segments[i];
                    while (StringSupport.equals(seg.SegmentID, "STC"))
                    {
                        i++;
                        seg = segments[i];
                    }
                    i += 4;
                }
                else if (StringSupport.equals(entityIdentifierCode, "85"))
                {
                    segNumsBillingProviderNM1.Add(i);
                }
                else if (StringSupport.equals(entityIdentifierCode, "QC"))
                {
                    segNumsPatientDetailNM1.Add(i);
                    do
                    {
                        //Loop 2200D: There can be multiple TRN segments for each NM1*QC.
                        i++;
                        segNumsClaimTrackingNumberTRN.Add(i);
                        //a TRN segment is required at this location.
                        i++;
                        seg = segments[i];
                        while (StringSupport.equals(seg.SegmentID, "STC"))
                        {
                            //at least one STC segment is required at this location.
                            //there may be multiple STC segments.
                            i++;
                            if (i >= segments.Count)
                            {
                                return ;
                            }
                             
                            //End of file
                            seg = segments[i];
                        }
                        for (int j = 0;j < 3 && (StringSupport.equals(seg.SegmentID, "REF"));j++)
                        {
                            //Followed by 0 to 3 situational REF segments.
                            i++;
                            if (i >= segments.Count)
                            {
                                return ;
                            }
                             
                            //End of file
                            seg = segments[i];
                        }
                        //Followed by 0 or 1 DTP segments.
                        if (StringSupport.equals(seg.SegmentID, "DTP"))
                        {
                            i++;
                            if (i >= segments.Count)
                            {
                                return ;
                            }
                             
                            //End of file
                            seg = segments[i];
                        }
                         
                    }
                    while (StringSupport.equals(seg.SegmentID, "TRN"));
                }
                    
            }
             
        }
    }

    //An entire iteration of loop 2200D is now finished. If another iteration is present, it will begin with a TRN segment.
    /**
    * NM101 of loop 2100A.
    */
    public String getInformationSourceType() throws Exception {
        if (segNumInfoSourceNM101 != -1)
        {
            if (StringSupport.equals(segments[segNumInfoSourceNM101].Get(1), "AY"))
            {
                return "Clearinghouse";
            }
             
            return "Payor";
        }
         
        return "";
    }

    /**
    * NM103 of loop 2100A.
    */
    public String getInformationSourceName() throws Exception {
        if (segNumInfoSourceNM101 != -1)
        {
            return segments[segNumInfoSourceNM101].Get(3);
        }
         
        return "";
    }

    /**
    * DTP03 of loop 2200A.
    */
    public DateTime getInformationSourceReceiptDate() throws Exception {
        if (segNumInfoSourceNM101 != -1)
        {
            try
            {
                String dateStr = segments[segNumInfoSourceNM101 + 2].Get(3);
                int dateYear = PIn.Int(dateStr.Substring(0, 4));
                int dateMonth = PIn.Int(dateStr.Substring(4, 2));
                int dateDay = PIn.Int(dateStr.Substring(6, 2));
                return new DateTime(dateYear, dateMonth, dateDay);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
         
        return DateTime.MinValue;
    }

    /**
    * DTP03 of loop 2200A.
    */
    public DateTime getInformationSourceProcessDate() throws Exception {
        if (segNumInfoSourceNM101 != -1)
        {
            try
            {
                String dateStr = segments[segNumInfoSourceNM101 + 3].Get(3);
                int dateYear = PIn.Int(dateStr.Substring(0, 4));
                int dateMonth = PIn.Int(dateStr.Substring(4, 2));
                int dateDay = PIn.Int(dateStr.Substring(6, 2));
                return new DateTime(dateYear, dateMonth, dateDay);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
         
        return DateTime.MinValue;
    }

    /**
    * Last STC segment in loop 2200B. Returns -1 on error.
    */
    private int getSegNumLastSTC2200B() throws Exception {
        if (segNumInfoReceiverNM101 != -1)
        {
            int segNum = segNumInfoReceiverNM101 + 2;
            X12Segment seg = segments[segNum];
            while (StringSupport.equals(seg.SegmentID, "STC"))
            {
                segNum++;
                //End of message can happen because the QTY and AMT segments are situational, and so are the two HL segments after this.
                if (segNum >= segments.Count)
                {
                    return segNum - 1;
                }
                 
                seg = segments[segNum];
            }
            return segNum - 1;
        }
         
        return -1;
    }

    /**
    * QTY02 of loop 2200B.
    */
    public long getQuantityAccepted() throws Exception {
        int segNum = getSegNumLastSTC2200B();
        if (segNum != -1)
        {
            segNum++;
            if (segNum < segments.Count)
            {
                X12Segment seg = segments[segNum];
                if (StringSupport.equals(seg.SegmentID, "QTY") && StringSupport.equals(seg.get(1), "90"))
                {
                    return long.Parse(seg.get(2));
                }
                 
            }
             
        }
         
        return 0;
    }

    /**
    * QTY02 of loop 2200B.
    */
    public long getQuantityRejected() throws Exception {
        int segNum = getSegNumLastSTC2200B();
        if (segNum != -1)
        {
            segNum++;
            if (segNum < segments.Count)
            {
                X12Segment seg = segments[segNum];
                if (StringSupport.equals(seg.SegmentID, "QTY"))
                {
                    try
                    {
                        if (StringSupport.equals(seg.get(1), "AA"))
                        {
                            return long.Parse(seg.get(2));
                        }
                        else
                        {
                            segNum++;
                            if (segNum < segments.Count)
                            {
                                seg = segments[segNum];
                                if (StringSupport.equals(seg.SegmentID, "QTY") && StringSupport.equals(seg.get(1), "AA"))
                                {
                                    return long.Parse(seg.get(2));
                                }
                                 
                            }
                             
                        } 
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                    }
                
                }
                 
            }
             
        }
         
        return 0;
    }

    /**
    * AMT02 of loop 2200B.
    */
    public double getAmountAccepted() throws Exception {
        int segNum = getSegNumLastSTC2200B();
        if (segNum != -1)
        {
            segNum++;
            if (segNum < segments.Count)
            {
                X12Segment seg = segments[segNum];
                while (StringSupport.equals(seg.SegmentID, "QTY"))
                {
                    segNum++;
                    if (segNum >= segments.Count)
                    {
                        return 0;
                    }
                     
                    seg = segments[segNum];
                }
                if (StringSupport.equals(seg.SegmentID, "AMT") && StringSupport.equals(seg.get(1), "YU"))
                {
                    return double.Parse(seg.get(2));
                }
                 
            }
             
        }
         
        return 0;
    }

    /**
    * AMT02 of loop 2200B.
    */
    public double getAmountRejected() throws Exception {
        int segNum = getSegNumLastSTC2200B();
        if (segNum != -1)
        {
            segNum++;
            if (segNum < segments.Count)
            {
                X12Segment seg = segments[segNum];
                while (StringSupport.equals(seg.SegmentID, "QTY"))
                {
                    segNum++;
                    if (segNum >= segments.Count)
                    {
                        return 0;
                    }
                     
                    seg = segments[segNum];
                }
                if (StringSupport.equals(seg.SegmentID, "AMT"))
                {
                    if (StringSupport.equals(seg.get(1), "YY"))
                    {
                        return double.Parse(seg.get(2));
                    }
                    else
                    {
                        segNum++;
                        if (segNum < segments.Count)
                        {
                            seg = segments[segNum];
                            if (StringSupport.equals(seg.SegmentID, "AMT") && StringSupport.equals(seg.get(1), "YY"))
                            {
                                return double.Parse(seg.get(2));
                            }
                             
                        }
                         
                    } 
                }
                 
            }
             
        }
         
        return 0;
    }

    /**
    * TRN02 in loop 2200D. Do this first to get a list of all claim tracking numbers that are contained within this 277.  Then, for each claim tracking number, we can later retrieve the AckCode for that single claim. The claim tracking numbers correspond to CLM01 exactly as submitted in the 837. We refer to CLM01 as the claim identifier on our end. We allow more than just digits in our claim identifiers, so we must return a list of strings.
    */
    public List<String> getClaimTrackingNumbers() throws Exception {
        List<String> retVal = new List<String>();
        for (int i = 0;i < segNumsClaimTrackingNumberTRN.Count;i++)
        {
            X12Segment seg = segments[segNumsClaimTrackingNumberTRN[i]];
            //TRN segment.
            retVal.Add(seg.get(2));
        }
        return retVal;
    }

    /**
    * Result will contain strings in the following order: 0 Patient Last Name (NM103), 1 Patient First Name (NM104), 2 Patient Middle Name (NM105),
    * 3 Claim Status (STC03), 4 Payor's Claim Control Number (REF02), 5 Institutional Type of Bill (REF02), 6 Claim Date Service Start (DTP03),
    * 7 Claim Date Service End (DTP03), 8 Reason (STC01-2), 9 Amount (STC04)
    */
    public String[] getClaimInfo(String trackingNumber) throws Exception {
        String[] result = new String[10];
        for (int i = 0;i < result.Length;i++)
        {
            result[i] = "";
        }
        for (int i = 0;i < segNumsClaimTrackingNumberTRN.Count;i++)
        {
            int segNum = segNumsClaimTrackingNumberTRN[i];
            X12Segment seg = segments[segNum];
            //TRN segment.
            if (StringSupport.equals(seg.get(2), trackingNumber))
            {
                //TRN02
                //Locate the NM1 segment corresponding to the claim tracking number. One NM1 segment can be shared with multiple TRN segments.
                //The strategy is to locate the NM1 segment furthest down in the message that is above the TRN segment for the tracking number.
                int segNumNM1 = segNumsPatientDetailNM1[segNumsPatientDetailNM1.Count - 1];
                for (int j = 0;j < segNumsPatientDetailNM1.Count - 1;j++)
                {
                    //very last NM1 segment
                    if (segNum > segNumsPatientDetailNM1[j] && segNum < segNumsPatientDetailNM1[j + 1])
                    {
                        segNumNM1 = segNumsPatientDetailNM1[j];
                        break;
                    }
                     
                }
                seg = segments[segNumNM1];
                //NM1 segment.
                result[0] = seg.get(3);
                //NM103 Last Name
                result[1] = seg.get(4);
                //NM104 First Name
                result[2] = seg.get(5);
                //NM105 Middle Name
                segNum++;
                seg = segments[segNum];
                //STC segment. At least one, maybe multiple, but we only care about the first one.
                String[] stc01 = seg.get(1).Split(new String[]{ Separators.Subelement }, StringSplitOptions.None);
                result[3] = GetStringForExternalSourceCode507(stc01[0]);
                if (stc01.Length > 1)
                {
                    result[8] = GetStringForExternalSourceCode508(stc01[1]);
                }
                 
                //STC01-2
                result[9] = seg.get(4);
                //Skip the remaining STC segments (if any).
                segNum++;
                if (segNum >= segments.Count)
                {
                    return result;
                }
                 
                //End of file
                seg = segments[segNum];
                while (StringSupport.equals(seg.SegmentID, "STC"))
                {
                    segNum++;
                    seg = segments[segNum];
                }
                while (StringSupport.equals(seg.SegmentID, "REF"))
                {
                    String refIdQualifier = seg.get(1);
                    if (StringSupport.equals(refIdQualifier, "1K"))
                    {
                        result[4] = seg.get(2);
                    }
                    else //REF02 Payor's Claim Control Number.
                    if (StringSupport.equals(refIdQualifier, "D9"))
                    {
                    }
                    else //REF02 Claim Identifier Number for Clearinghouse and Other Transmission Intermediary from the 837.
                    //When we send this it is the same as the claim identifier/claim tracking number, so we don't use this for now.
                    if (StringSupport.equals(refIdQualifier, "BLT"))
                    {
                        //REF02 Institutional Type of Bill that was sent in the 837.
                        result[5] = seg.get(2);
                    }
                       
                    segNum++;
                    if (segNum >= segments.Count)
                    {
                        return result;
                    }
                     
                    //End of file
                    seg = segments[segNum];
                }
                //The DTP segment for the date of service will not be present when an invalid date was originally sent to the carrier (even though the specifications have it marked as a required segment).
                if (StringSupport.equals(seg.SegmentID, "DTP"))
                {
                    String dateServiceStr = seg.get(3);
                    int dateServiceStartYear = PIn.Int(dateServiceStr.Substring(0, 4));
                    int dateServiceStartMonth = PIn.Int(dateServiceStr.Substring(4, 2));
                    int dateServiceStartDay = PIn.Int(dateServiceStr.Substring(6, 2));
                    result[6] = (new DateTime(dateServiceStartYear, dateServiceStartMonth, dateServiceStartDay)).ToShortDateString();
                    if (dateServiceStr.Length == 17)
                    {
                        //Date range.
                        int dateServiceEndYear = PIn.Int(dateServiceStr.Substring(9, 4));
                        int dateServiceEndMonth = PIn.Int(dateServiceStr.Substring(13, 2));
                        int dateServiceEndDay = PIn.Int(dateServiceStr.Substring(15, 2));
                        result[7] = (new DateTime(dateServiceEndYear, dateServiceEndMonth, dateServiceEndDay)).ToShortDateString();
                    }
                     
                }
                 
            }
             
        }
        return result;
    }

    public String getHumanReadable() throws Exception {
        String result = "Claim Status Reponse From " + getInformationSourceType() + " " + getInformationSourceName() + Environment.NewLine + "Receipt Date: " + getInformationSourceReceiptDate().ToShortDateString() + Environment.NewLine + "Process Date: " + getInformationSourceProcessDate().ToShortDateString() + Environment.NewLine + "Quantity Accepted: " + getQuantityAccepted() + Environment.NewLine + "Quantity Rejected: " + getQuantityRejected() + Environment.NewLine + "Amount Accepted: " + getAmountAccepted() + Environment.NewLine + "Amount Rejected: " + getAmountRejected() + Environment.NewLine + "Individual Claim Status List: " + Environment.NewLine + "Tracking Num  LName  FName  MName  Status  PayorControlNum  InstBillType DateServiceStart  DateServiceEnd";
        List<String> claimTrackingNumbers = getClaimTrackingNumbers();
        for (int i = 0;i < claimTrackingNumbers.Count;i++)
        {
            String[] claimInfo = GetClaimInfo(claimTrackingNumbers[i]);
            for (int j = 0;j < claimInfo.Length;j++)
            {
                result += claimInfo[j] + "\\t";
            }
            result += Environment.NewLine;
        }
        return result;
    }

    /**
    * Code source 507. Since we only send batches, we can only get status codes starting with "A" returned to us.  Returns empty string if no matches.
    * The codes are pulled from the Washington Publishing Company website (the makers of X12). http://www.wpc-edi.com/reference/codelists/healthcare/claim-status-category-codes.
    */
    public static String getStringForExternalSourceCode507(String sourceCode) throws Exception {
        System.String __dummyScrutVar0 = sourceCode;
        if (__dummyScrutVar0.equals("A0"))
        {
            return "A";
        }
        else //Acknowledgement/Forwarded-The claim/encounter has been forwarded to another entity.
        if (__dummyScrutVar0.equals("A1"))
        {
            return "A";
        }
        else //Acknowledgement/Receipt-The claim/encounter has been received. This does not mean that the claim has been accepted for adjudication.
        //We say accepted, because if rejected later, then there should hopefully be another 277 which overwrites this one.
        if (__dummyScrutVar0.equals("A2"))
        {
            return "A";
        }
        else //Acknowledgement/Acceptance into adjudication system-The claim/encounter has been accepted into the adjudication system.
        if (__dummyScrutVar0.equals("A3"))
        {
            return "R";
        }
        else //Acknowledgement/Returned as unprocessable claim-The claim/encounter has been rejected and has not been entered into the adjudication system.
        if (__dummyScrutVar0.equals("A4"))
        {
            return "R";
        }
        else //Acknowledgement/Not Found-The claim/encounter can not be found in the adjudication system.
        //Not really rejected, but no way it could be accepted.
        if (__dummyScrutVar0.equals("A5"))
        {
            return "A";
        }
        else //Acknowledgement/Split Claim-The claim/encounter has been split upon acceptance into the adjudication system.
        if (__dummyScrutVar0.equals("A6"))
        {
            return "R";
        }
        else //Acknowledgement/Rejected for Missing Information - The claim/encounter is missing the information specified in the Status details and has been rejected.
        if (__dummyScrutVar0.equals("A7"))
        {
            return "R";
        }
        else //Acknowledgement/Rejected for Invalid Information - The claim/encounter has invalid information as specified in the Status details and has been rejected.
        if (__dummyScrutVar0.equals("A8"))
        {
            return "R";
        }
                 
        return "";
    }

    //cknowledgement / Rejected for relational field in error.
    //We have to return blank because this value is used to update the etrans table.
    /**
    * Convert the given source code to a string. Only 0 to 56 included so far. All codes listed at http://www.wpc-edi.com/reference/codelists/healthcare/claim-status-codes.
    */
    public static String getStringForExternalSourceCode508(String sourceCode) throws Exception {
        System.String __dummyScrutVar1 = sourceCode;
        if (__dummyScrutVar1.equals("0"))
        {
            return "Cannot provide further status electronically.";
        }
        else if (__dummyScrutVar1.equals("1"))
        {
            return "For more detailed information, see remittance advice.";
        }
        else if (__dummyScrutVar1.equals("2"))
        {
            return "More detailed information in letter.";
        }
        else if (__dummyScrutVar1.equals("3"))
        {
            return "Claim has been adjudicated and is awaiting payment cycle.";
        }
        else if (__dummyScrutVar1.equals("6"))
        {
            return "Balance due from the subscriber.";
        }
        else if (__dummyScrutVar1.equals("12"))
        {
            return "One or more originally submitted procedure codes have been combined.";
        }
        else if (__dummyScrutVar1.equals("15"))
        {
            return "One or more originally submitted procedure code have been modified.";
        }
        else if (__dummyScrutVar1.equals("16"))
        {
            return "Claim/encounter has been forwarded to entity. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("17"))
        {
            return "Claim/encounter has been forwarded by third party entity to entity. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("18"))
        {
            return "Entity received claim/encounter, but returned invalid status. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("19"))
        {
            return "Entity acknowledges receipt of claim/encounter. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("20"))
        {
            return "Accepted for processing.";
        }
        else if (__dummyScrutVar1.equals("21"))
        {
            return "Missing or invalid information. Note: At least one other status code is required to identify the missing or invalid information.";
        }
        else if (__dummyScrutVar1.equals("23"))
        {
            return "Returned to Entity. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("24"))
        {
            return "Entity not approved as an electronic submitter. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("25"))
        {
            return "Entity not approved. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("26"))
        {
            return "Entity not found. Note: This code requires use of an Entity Code.";
        }
        else if (__dummyScrutVar1.equals("27"))
        {
            return "Policy canceled.";
        }
        else if (__dummyScrutVar1.equals("29"))
        {
            return "Subscriber and policy number/contract number mismatched.";
        }
        else if (__dummyScrutVar1.equals("30"))
        {
            return "Subscriber and subscriber id mismatched.";
        }
        else if (__dummyScrutVar1.equals("31"))
        {
            return "Subscriber and policyholder name mismatched.";
        }
        else if (__dummyScrutVar1.equals("32"))
        {
            return "Subscriber and policy number/contract number not found.";
        }
        else if (__dummyScrutVar1.equals("33"))
        {
            return "Subscriber and subscriber id not found.";
        }
        else if (__dummyScrutVar1.equals("34"))
        {
            return "Subscriber and policyholder name not found.";
        }
        else if (__dummyScrutVar1.equals("35"))
        {
            return "Claim/encounter not found.";
        }
        else if (__dummyScrutVar1.equals("37"))
        {
            return "Predetermination is on file, awaiting completion of services.";
        }
        else if (__dummyScrutVar1.equals("38"))
        {
            return "Awaiting next periodic adjudication cycle.";
        }
        else if (__dummyScrutVar1.equals("39"))
        {
            return "Charges for pregnancy deferred until delivery.";
        }
        else if (__dummyScrutVar1.equals("40"))
        {
            return "Waiting for final approval.";
        }
        else if (__dummyScrutVar1.equals("41"))
        {
            return "Special handling required at payer site.";
        }
        else if (__dummyScrutVar1.equals("42"))
        {
            return "Awaiting related charges.";
        }
        else if (__dummyScrutVar1.equals("44"))
        {
            return "Charges pending provider audit.";
        }
        else if (__dummyScrutVar1.equals("45"))
        {
            return "Awaiting benefit determination.";
        }
        else if (__dummyScrutVar1.equals("46"))
        {
            return "Internal review/audit.";
        }
        else if (__dummyScrutVar1.equals("47"))
        {
            return "Internal review/audit - partial payment made.";
        }
        else if (__dummyScrutVar1.equals("49"))
        {
            return "Pending provider accreditation review.";
        }
        else if (__dummyScrutVar1.equals("50"))
        {
            return "Claim waiting for internal provider verification.";
        }
        else if (__dummyScrutVar1.equals("51"))
        {
            return "Investigating occupational illness/accident.";
        }
        else if (__dummyScrutVar1.equals("52"))
        {
            return "Investigating existence of other insurance coverage.";
        }
        else if (__dummyScrutVar1.equals("53"))
        {
            return "Claim being researched for Insured ID/Group Policy Number error.";
        }
        else if (__dummyScrutVar1.equals("54"))
        {
            return "Duplicate of a previously processed claim/line.";
        }
        else if (__dummyScrutVar1.equals("55"))
        {
            return "Claim assigned to an approver/analyst.";
        }
        else if (__dummyScrutVar1.equals("56"))
        {
            return "Awaiting eligibility determination.";
        }
                                                   
        return sourceCode.ToString();
    }

}


//There are over 700 total, but we chose to display the most common codes. This will at least display the code number for the user so they can look it up.
//EXAMPLE 1 - From X12 Specification
//ISA*00*          *00*          *ZZ*810624427      *ZZ*133052274      *060131*0756*^*00501*000000017*0*T*:~
//GS*HN*810624427*133052274*20060131*0756*17*X*005010X214~
//ST*277*0001*005010X214~
//BHT*0085*08*277X2140001*20060205*1635*TH~
//HL*1**20*1~
//NM1*AY*2*FIRST CLEARINGHOUSE*****46*CLHR00~
//TRN*1*200102051635S00001ABCDEF~
//DTP*050*D8*20060205~
//DTP*009*D8*20060207~
//HL*2*1*21*1~
//NM1*41*2*BEST BILLING SERVICE*****46*S00001~
//TRN*2*2002020542857~
//STC*A0:16:PR*20060205*WQ*1000~
//QTY*90*1~
//QTY*AA*2~
//AMT*YU*200~
//AMT*YY*800~
//HL*3*2*19*1~
//NM1*85*2*SMITH CLINIC*****FI*123456789~
//HL*4*3*PT~
//NM1*QC*1*DOE*JOHN****MI*00ABCD1234~
//TRN*2*4001/1339~
//STC*A0:16:PR*20060205*WQ*200~
//REF*1K*22029500123407X~
//DTP*472*RD8*20060128-20060131~
//HL*5*3*PT~
//NM1*QC*1*DOE*JANE****MI*45613027602~
//TRN*2*2890/4~
//STC*A3:21:82*20060205*U*500~
//DTP*472*D8*20060115~
//SVC*HC:22305:22*350*****1~
//STC*A3:122**U*******A3:153:82~
//REF*FJ*11~
//HL*6*3*PT~
//NM1*QC*1*VEST*HELEN****MI*45602708901~
//TRN*2*00000000000000000000~
//STC*A3:401*20060205*U*300~
//DTP*472*RD8*20060120-20060120~
//SE*37*0001~
//GE*1*17~
//IEA*1*000000017~
//EXAMPLE 2 - From X12 Specification
//ISA*00*          *00*          *ZZ*810624427      *ZZ*133052274      *060131*0756*^*00501*000000017*0*T*:~
//GS*HN*810624427*133052274*20060131*0756*17*X*005010X214~
//ST*277*0002*005010X214~
//BHT*0085*08*277X2140002*20060201*0405*TH~
//HL*1**20*1~
//NM1*AY*2*FIRST CLEARINGHOUSE*****46*CLHR00~
//TRN*1*200201312005S00002XYZABC~
//DTP*050*D8*20060131~
//DTP*009*D8*20060201~
//HL*2*1*21*0~
//NM1*41*2*LAST BILLING SERVICE*****46*S00002~
//TRN*2*20020131052389~
//STC*A3:24:41**U~
//QTY*AA*3~
//AMT*YY*800~
//SE*14*00002~
//GE*1*17~
//IEA*1*000000017~
//EXAMPLE 3 - From X12 Specification
//ISA*00*          *00*          *ZZ*810624427      *ZZ*133052274      *060131*0756*^*00501*000000017*0*T*:~
//GS*HN*810624427*133052274*20060131*0756*17*X*005010X214~
//ST*277*0003*005010X214~
//BHT*0085*08*277X2140003*20060221*1025*TH~
//HL*1**20*1~
//NM1*PR*2*YOUR INSURANCE COMPANY*****PI*YIC01~
//TRN*1*0091182~
//DTP*050*D8*20060220~
//DTP*009*D8*20060221~
//HL*2*1*21*1~
//NM1*41*1*JONES*HARRY*B**MD*46*S00003~
//TRN*2*2002022045678~
//STC*A1:19:PR*20060221*WQ*365.5~
//QTY*90*3~
//QTY*AA*2~
//AMT*YU*200.5~
//AMT*YY*165~
//HL*3*2*19*1~
//NM1*85*1*JONES*HARRY*B**MD*FI*234567894~
//HL*4*3*PT~
//NM1*QC*1*PATIENT*FEMALE****MI*2222222222~
//TRN*2*PATIENT22222~
//STC*A2:20:PR*20060221*WQ*100~
//REF*1K*220216359803X~
//DTP*472*D8*20060214~
//HL*5*3*PT~
//NM1*QC*1*PATIENT*MALE****MI*3333333333~
//TRN*2*PATIENT33333~
//STC*A3:187:PR*20060221*U*65~
//DTP*472*D8*20090221~
//HL*6*3*PT~
//NM1*QC*1*JONES*LARRY****MI*4444444444~
//TRN*2*JONES44444~
//STC*A3:21:77*20060221*U*100~
//DTP*472*D8*20060211~
//HL*7*3*PT~
//NM1*QC*1*JOHNSON*MARY****MI*5555555555~
//TRN*2*JONHSON55555~
//STC*A2:20:PR*20060221*WQ*50.5~
//REF*1K*220216359806X~
//DTP*472*D8*20060210~
//HL*8*3*PT~
//NM1*QC*1*MILLS*HARRIETT****MI*6666666666~
//TRN*2*MILLS66666~
//STC*A2:20:PR*20060221*WQ*50~
//REF*1K*220216359807X~
//DTP*472*D8*20060205~
//SE*46*0003~
//GE*1*17~
//IEA*1*000000017~
//EXAMPLE 4 - From X12 Specification
//ISA*00*          *00*          *ZZ*810624427      *ZZ*133052274      *060131*0756*^*00501*000000017*0*T*:~
//GS*HN*810624427*133052274*20060131*0756*17*X*005010X214~
//ST*277*0004*005010X214~
//BHT*0085*08*277X2140004*20060321*1025*TH~
//HL*1**20*1~
//NM1*PR*2*OUR INSURANCE COMPANY*****PI*OIC02~
//TRN*1*00911232~
//DTP*050*D8*20060320~
//DTP*009*D8*20060321~
//HL*2*1*21*1~
//NM1*41*1*KING*EWELL*B**MD*46*S00005~
//TRN*2*200203207890~
//STC*A1:19:PR*20060321*WQ*455~
//QTY*90*3~
//QTY*AA*5~
//AMT*YU*155~
//AMT*YY*300~
//HL*3*2*19*1~
//NM1*85*1*KING*EWELL*B**MD*XX*5365432101~
//TRN*2*00098765432~
//STC*A1:19:PR**WQ*305~
//HL*4*3*PT~
//NM1*QC*1*PATIENT*FEMALE****MI*2222222222~
//TRN*2*PATIENT22222~
//STC*A2:20:PR*20060321*WQ*55~
//REF*1K*220216359803X~
//DTP*472*D8*20060314~
//HL*5*3*PT~
//NM1*QC*1*PATIENT*MALE****MI*3333333333~
//TRN*2*PATIENT33333~
//STC*A3:187:PR*20060321*U*50~
//HL*6*3*PT~
//NM1*QC*1*JONES*MARY****MI*4444444444~
//TRN*2*JONES44444~
//STC*A3:116*20060321*U*100~
//DTP*472*D8*20060311~
//HL*7*3*PT~
//NM1*QC*1*JOHNSON*JIMMY****MI*5555555555~
//TRN*2*JOHNSON55555~
//STC*A2:20:PR*20060321*WQ*50~
//REF*1K*220216359806X~
//DTP*472*D8*20060310~
//HL*8*3*PT~
//NM1*QC*1*MILLS*HALEY****MI*6666666666~
//TRN*2*MILLS66666~
//STC*A2:20:PR*20060321*WQ*50~
//REF*1K*200216359807X~
//DTP*472*D8*20060305~
//HL*9*2*19*0~
//NM1*85*1*REED*I*B**MD*FI*567012345~
//TRN*2*00023456789~
//STC*A3:24:85*20060321*U*150~
//QTY*QC*3~
//AMT*YY*150~
//SE*53*0004~
//GE*1*17~
//IEA*1*000000017~