//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PIn;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X12Segment;

/**
* 
*/
public class X999  extends X12object 
{
    public X999(String messageText) throws Exception {
        super(messageText);
    }

    /**
    * In X12 lingo, the batchNumber is known as the functional group.
    */
    public int getBatchNumber() throws Exception {
        if (this.FunctGroups[0].Transactions.Count != 1)
        {
            return 0;
        }
         
        X12Segment seg = FunctGroups[0].Transactions[0].GetSegmentByID("AK1");
        if (seg == null)
        {
            return 0;
        }
         
        String num = seg.get(2);
        try
        {
            return PIn.int(num);
        }
        catch (Exception __dummyCatchVar0)
        {
            return 0;
        }
    
    }

    /**
    * Do this first to get a list of all trans nums that are contained within this 999.  Then, for each trans num, we can later retrieve the AckCode for that single trans num.
    */
    public List<int> getTransNums() throws Exception {
        List<int> retVal = new List<int>();
        X12Segment seg;
        int transNum = 0;
        for (int i = 0;i < FunctGroups[0].Transactions[0].Segments.Count;i++)
        {
            seg = FunctGroups[0].Transactions[0].Segments[i];
            if (StringSupport.equals(seg.SegmentID, "AK2"))
            {
                transNum = 0;
                try
                {
                    transNum = PIn.int(seg.get(2));
                }
                catch (Exception __dummyCatchVar1)
                {
                    transNum = 0;
                }

                if (transNum != 0)
                {
                    retVal.Add(transNum);
                }
                 
            }
             
        }
        return retVal;
    }

    /**
    * Use after GetTransNums.  Will return A=Accepted, R=Rejected, or "" if can't determine.
    */
    public String getAckForTrans(int transNum) throws Exception {
        X12Segment seg;
        boolean foundTransNum = false;
        int thisTransNum = 0;
        for (int i = 0;i < FunctGroups[0].Transactions[0].Segments.Count;i++)
        {
            seg = FunctGroups[0].Transactions[0].Segments[i];
            if (foundTransNum)
            {
                if (!StringSupport.equals(seg.SegmentID, "IK5"))
                {
                    continue;
                }
                 
                String code = seg.get(1);
                String ack = "";
                if (StringSupport.equals(code, "A") || StringSupport.equals(code, "E"))
                {
                    //Accepted or accepted with Errors.
                    ack = "A";
                }
                else
                {
                    //M, R, W or X
                    ack = "R";
                } 
                return ack;
            }
             
            if (StringSupport.equals(seg.SegmentID, "AK2"))
            {
                thisTransNum = 0;
                try
                {
                    thisTransNum = PIn.int(seg.get(2));
                }
                catch (Exception __dummyCatchVar2)
                {
                    thisTransNum = 0;
                }

                if (thisTransNum == transNum)
                {
                    foundTransNum = true;
                }
                 
            }
             
        }
        return "";
    }

    /**
    * Will return "" if unable to determine.  But would normally return A=Accepted or R=Rejected or P=Partially accepted if only some of the transactions were accepted.
    */
    public String getBatchAckCode() throws Exception {
        if (this.FunctGroups[0].Transactions.Count != 1)
        {
            return "";
        }
         
        X12Segment seg = FunctGroups[0].Transactions[0].GetSegmentByID("AK9");
        if (seg == null)
        {
            return "";
        }
         
        String code = seg.get(1);
        String ack = "";
        if (StringSupport.equals(code, "A") || StringSupport.equals(code, "E"))
        {
            //Accepted or accepted with Errors.
            ack = "A";
        }
        else if (StringSupport.equals(code, "P"))
        {
            //Partially accepted
            ack = "P";
        }
        else
        {
            //M, R, W, X
            ack = "R";
        }  
        return ack;
    }

    //rejected
    /**
    * 
    */
    public String getHumanReadable() throws Exception {
        String retVal = "";
        for (int i = 0;i < Segments.Count;i++)
        {
            if (!StringSupport.equals(Segments[i].SegmentID, "IK3") && !StringSupport.equals(Segments[i].SegmentID, "IK4"))
            {
                continue;
            }
             
            if (!StringSupport.equals(retVal, ""))
            {
                //if multiple errors
                retVal += "\r\n";
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "IK3"))
            {
                retVal += "Segment " + Segments[i].Get(1) + ": " + GetSegmentSyntaxError(Segments[i].Get(4));
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "IK4"))
            {
                retVal += "Element " + Segments[i].Get(1) + ": " + GetElementSyntaxError(Segments[i].Get(3));
            }
             
        }
        return retVal;
    }

    //retVal+=GetRejectReason(Segments[i].Get(3))+", "
    //	+GetFollowupAction(Segments[i].Get(4));
    private String getSegmentSyntaxError(String code) throws Exception {
        System.String __dummyScrutVar0 = code;
        if (__dummyScrutVar0.equals("1"))
        {
            return "Unrecognized segment ID";
        }
        else if (__dummyScrutVar0.equals("2"))
        {
            return "Unexpected segment";
        }
        else if (__dummyScrutVar0.equals("3"))
        {
            return "Required segment missing";
        }
        else if (__dummyScrutVar0.equals("4"))
        {
            return "Loop occurs over maximum times";
        }
        else if (__dummyScrutVar0.equals("5"))
        {
            return "Segment exceeds maximum use";
        }
        else if (__dummyScrutVar0.equals("6"))
        {
            return "Segment not in defined transaction set";
        }
        else if (__dummyScrutVar0.equals("7"))
        {
            return "Segment not in proper sequence";
        }
        else if (__dummyScrutVar0.equals("8"))
        {
            return "Segment has data element errors";
        }
        else if (__dummyScrutVar0.equals("I4"))
        {
            return "Implementation \"not used\" segment present";
        }
        else if (__dummyScrutVar0.equals("I6"))
        {
            return "Implementation dependent segment missing";
        }
        else if (__dummyScrutVar0.equals("I7"))
        {
            return "Implementation loop occurs under minimum times";
        }
        else if (__dummyScrutVar0.equals("I8"))
        {
            return "Implementation segment below minimum use";
        }
        else if (__dummyScrutVar0.equals("I9"))
        {
            return "Implementation dependent \"not used\" segment present";
        }
        else
        {
            return code;
        }             
    }

    //will never happen
    private String getElementSyntaxError(String code) throws Exception {
        System.String __dummyScrutVar1 = code;
        if (__dummyScrutVar1.equals("1"))
        {
            return "Required data element missing";
        }
        else if (__dummyScrutVar1.equals("2"))
        {
            return "Conditional required data element missing";
        }
        else if (__dummyScrutVar1.equals("3"))
        {
            return "Too many data elements";
        }
        else if (__dummyScrutVar1.equals("4"))
        {
            return "Data element too short";
        }
        else if (__dummyScrutVar1.equals("5"))
        {
            return "Data element too long";
        }
        else if (__dummyScrutVar1.equals("6"))
        {
            return "Invalid character in data element";
        }
        else if (__dummyScrutVar1.equals("7"))
        {
            return "Invalid code value";
        }
        else if (__dummyScrutVar1.equals("8"))
        {
            return "Invalid date";
        }
        else if (__dummyScrutVar1.equals("9"))
        {
            return "Invalid time";
        }
        else if (__dummyScrutVar1.equals("10"))
        {
            return "Exclusion condition violated";
        }
        else if (__dummyScrutVar1.equals("12"))
        {
            return "Too many repetitions";
        }
        else if (__dummyScrutVar1.equals("13"))
        {
            return "Too many components";
        }
        else if (__dummyScrutVar1.equals("I10"))
        {
            return "Implementation \"not used\" data element present";
        }
        else if (__dummyScrutVar1.equals("I11"))
        {
            return "Implementation too few repetitions";
        }
        else if (__dummyScrutVar1.equals("I12"))
        {
            return "Implementation pattern match failure";
        }
        else if (__dummyScrutVar1.equals("I13"))
        {
            return "Implementation dependent \"not used\" data element present";
        }
        else if (__dummyScrutVar1.equals("I6"))
        {
            return "Code value not used in implementation";
        }
        else if (__dummyScrutVar1.equals("I9"))
        {
            return "Implementation dependent data element missing";
        }
        else
        {
            return code;
        }                  
    }

}


//will never happen