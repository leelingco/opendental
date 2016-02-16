//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DTP271;
import OpenDentBusiness.EB271;
import OpenDentBusiness.X12object;

/**
* A 271 is the eligibility response to a 270.
*/
public class X271  extends X12object 
{
    public X271(String messageText) throws Exception {
        super(messageText);
    }

    /**
    * In realtime mode, X12 limits the request to one patient.  We will always use the subscriber.  So all EB segments are for the subscriber.
    */
    public List<EB271> getListEB(boolean isInNetwork) throws Exception {
        List<EB271> retVal = new List<EB271>();
        EB271 eb = null;
        for (int i = 0;i < Segments.Count;i++)
        {
            //loop until we encounter the first EB
            if (!StringSupport.equals(Segments[i].SegmentID, "EB") && eb == null)
            {
                continue;
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "EB"))
            {
                //add the previous eb
                if (eb != null)
                {
                    retVal.Add(eb);
                }
                 
                //then, start the next one
                eb = new EB271(Segments[i], isInNetwork);
                continue;
            }
            else if (StringSupport.equals(Segments[i].SegmentID, "SE"))
            {
                //end of benefits
                retVal.Add(eb);
                break;
            }
            else
            {
                //add to existing eb
                eb.SupplementalSegments.Add(Segments[i]);
                continue;
            }  
        }
        return retVal;
    }

    /**
    * Only the DTP segments that come before the EB segments.  X12 loop 2100C.
    */
    public List<DTP271> getListDtpSubscriber() throws Exception {
        List<DTP271> retVal = new List<DTP271>();
        DTP271 dtp;
        for (int i = 0;i < Segments.Count;i++)
        {
            if (StringSupport.equals(Segments[i].SegmentID, "EB"))
            {
                break;
            }
             
            if (!StringSupport.equals(Segments[i].SegmentID, "DTP"))
            {
                continue;
            }
             
            dtp = new DTP271(Segments[i]);
            retVal.Add(dtp);
        }
        return retVal;
    }

    /**
    * If there was no processing error (2100A, 2100B, 2100C, 2110C AAA segment), then this will return empty string.
    */
    public String getProcessingError() throws Exception {
        String retVal = "";
        for (int i = 0;i < Segments.Count;i++)
        {
            if (!StringSupport.equals(Segments[i].SegmentID, "AAA"))
            {
                continue;
            }
             
            if (!StringSupport.equals(retVal, ""))
            {
                //if multiple errors
                retVal += ", ";
            }
             
            retVal += GetRejectReason(Segments[i].Get(3)) + ", " + GetFollowupAction(Segments[i].Get(4));
        }
        return retVal;
    }

    /**
    * Some of these codes are only found in certain loops.
    */
    private String getRejectReason(String code) throws Exception {
        System.String __dummyScrutVar0 = code;
        if (__dummyScrutVar0.equals("04"))
        {
            return "Authorized Quantity Exceeded (too many patients in request)";
        }
        else if (__dummyScrutVar0.equals("15"))
        {
            return "Required application data missing";
        }
        else if (__dummyScrutVar0.equals("41"))
        {
            return "Authorization Access Restriction (not allowed to submit requests)";
        }
        else if (__dummyScrutVar0.equals("42"))
        {
            return "Unable to Respond at Current Time";
        }
        else if (__dummyScrutVar0.equals("43"))
        {
            return "Invalid/Missing Provider Identification";
        }
        else if (__dummyScrutVar0.equals("44"))
        {
            return "Invalid/Missing Provider Name";
        }
        else if (__dummyScrutVar0.equals("45"))
        {
            return "Invalid/Missing Provider Specialty";
        }
        else if (__dummyScrutVar0.equals("46"))
        {
            return "Invalid/Missing Provider Phone Number";
        }
        else if (__dummyScrutVar0.equals("47"))
        {
            return "Invalid/Missing Provider State";
        }
        else if (__dummyScrutVar0.equals("48"))
        {
            return "Invalid/Missing Referring Provider Identification Number";
        }
        else if (__dummyScrutVar0.equals("49"))
        {
            return "Provider is Not Primary Care Physician";
        }
        else if (__dummyScrutVar0.equals("50"))
        {
            return "Provider Ineligible for Inquiries";
        }
        else if (__dummyScrutVar0.equals("51"))
        {
            return "Provider Not on File";
        }
        else if (__dummyScrutVar0.equals("52"))
        {
            return "Service Dates Not Within Provider Plan Enrollment";
        }
        else if (__dummyScrutVar0.equals("53"))
        {
            return "Inquired Benefit Inconsistent with Provider Type";
        }
        else if (__dummyScrutVar0.equals("54"))
        {
            return "Inappropriate Product/Service ID Qualifier";
        }
        else if (__dummyScrutVar0.equals("55"))
        {
            return "Inappropriate Product/Service ID";
        }
        else if (__dummyScrutVar0.equals("56"))
        {
            return "Inappropriate Date";
        }
        else if (__dummyScrutVar0.equals("57"))
        {
            return "Invalid/Missing Date(s) of Service";
        }
        else if (__dummyScrutVar0.equals("58"))
        {
            return "Invalid/Missing Date-of-Birth";
        }
        else if (__dummyScrutVar0.equals("60"))
        {
            return "Date of Birth Follows Date(s) of Service";
        }
        else if (__dummyScrutVar0.equals("61"))
        {
            return "Date of Death Precedes Date(s) of Service";
        }
        else if (__dummyScrutVar0.equals("62"))
        {
            return "Date of Service Not Within Allowable Inquiry Period";
        }
        else if (__dummyScrutVar0.equals("63"))
        {
            return "Date of Service in Future";
        }
        else if (__dummyScrutVar0.equals("64"))
        {
            return "Invalid/Missing Patient ID";
        }
        else if (__dummyScrutVar0.equals("65"))
        {
            return "Invalid/Missing Patient Name";
        }
        else if (__dummyScrutVar0.equals("66"))
        {
            return "Invalid/Missing Patient Gender Code";
        }
        else if (__dummyScrutVar0.equals("67"))
        {
            return "Patient Not Found";
        }
        else if (__dummyScrutVar0.equals("68"))
        {
            return "Duplicate Patient ID Number";
        }
        else if (__dummyScrutVar0.equals("69"))
        {
            return "Inconsistent with Patient’s Age";
        }
        else if (__dummyScrutVar0.equals("70"))
        {
            return "Inconsistent with Patient’s Gender";
        }
        else if (__dummyScrutVar0.equals("71"))
        {
            return "Patient Birth Date Does Not Match That for the Patient on the Database";
        }
        else if (__dummyScrutVar0.equals("72"))
        {
            return "Invalid/Missing Subscriber/Insured ID";
        }
        else if (__dummyScrutVar0.equals("73"))
        {
            return "Invalid/Missing Subscriber/Insured Name";
        }
        else if (__dummyScrutVar0.equals("74"))
        {
            return "Invalid/Missing Subscriber/Insured Gender Code";
        }
        else if (__dummyScrutVar0.equals("75"))
        {
            return "Subscriber/Insured Not Found";
        }
        else if (__dummyScrutVar0.equals("76"))
        {
            return "Duplicate Subscriber/Insured ID Number";
        }
        else if (__dummyScrutVar0.equals("77"))
        {
            return "Subscriber Found, Patient Not Found";
        }
        else if (__dummyScrutVar0.equals("78"))
        {
            return "Subscriber/Insured Not in Group/Plan Identified";
        }
        else if (__dummyScrutVar0.equals("79"))
        {
            return "Invalid Participant Identification (this payer does not provide e-benefits)";
        }
        else if (__dummyScrutVar0.equals("80"))
        {
            return "No Response received - Transaction Terminated";
        }
        else if (__dummyScrutVar0.equals("97"))
        {
            return "Invalid or Missing Provider Address";
        }
        else if (__dummyScrutVar0.equals("T4"))
        {
            return "Payer Name or Identifier Missing";
        }
        else
        {
            return "Error code '" + code + "' not valid.";
        }                                           
    }

    private String getFollowupAction(String code) throws Exception {
        System.String __dummyScrutVar1 = code;
        if (__dummyScrutVar1.equals("C"))
        {
            return "Please Correct and Resubmit";
        }
        else if (__dummyScrutVar1.equals("N"))
        {
            return "Resubmission Not Allowed";
        }
        else if (__dummyScrutVar1.equals("P"))
        {
            return "Please Resubmit Original Transaction";
        }
        else if (__dummyScrutVar1.equals("R"))
        {
            return "Resubmission Allowed";
        }
        else if (__dummyScrutVar1.equals("S"))
        {
            return "Do Not Resubmit; Inquiry Initiated to a Third Party";
        }
        else if (__dummyScrutVar1.equals("W"))
        {
            return "Please Wait 30 Days and Resubmit";
        }
        else if (__dummyScrutVar1.equals("X"))
        {
            return "Please Wait 10 Days and Resubmit";
        }
        else if (__dummyScrutVar1.equals("Y"))
        {
            return "Do Not Resubmit; We Will Hold Your Request and Respond Again Shortly";
        }
        else
        {
            return "Error code '" + code + "' not valid.";
        }        
    }

}


