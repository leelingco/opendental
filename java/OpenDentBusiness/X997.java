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
public class X997  extends X12object 
{
    public X997(String messageText) throws Exception {
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
    * Do this first to get a list of all trans nums that are contained within this 997.  Then, for each trans num, we can later retrieve the AckCode for that single trans num.
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
                if (!StringSupport.equals(seg.SegmentID, "AK5"))
                {
                    continue;
                }
                 
                String code = seg.get(1);
                if (StringSupport.equals(code, "A") || StringSupport.equals(code, "E"))
                {
                    return "A";
                }
                 
                return "R";
            }
             
            //Accepted or accepted with Errors.
            //rejected
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
        if (StringSupport.equals(code, "A") || StringSupport.equals(code, "E"))
        {
            return "A";
        }
         
        //Accepted or accepted with Errors.
        if (StringSupport.equals(code, "P"))
        {
            return "P";
        }
         
        return "R";
    }

    //Partially accepted
    //rejected
    /*Example 997
    		ISA*00*          *00*          *ZZ*113504607      *ZZ*               *070813*0930*U*00401*705547511*0*P*:~
    		GS*FA*113504607**20070813*0930*705547511*X*004010X097A1~
    		ST*997*0001~
    		AK1*HC*0001~
    		AK2*837*0001~
    		AK5*A~
    		AK9*A*1*1*1~
    		SE*6*0001~
    		GE*1*705547511~
    		IEA*1*705547511~
    		*/
    //the only rows that we evaluate are AK2, which has transaction# (batchNumber), and AK5 which has ack code.
    /**
    * 
    */
    public String getHumanReadable() throws Exception {
        String retVal = "";
        for (int i = 0;i < Segments.Count;i++)
        {
            if (!StringSupport.equals(Segments[i].SegmentID, "AK3") && !StringSupport.equals(Segments[i].SegmentID, "AK4"))
            {
                continue;
            }
             
            if (!StringSupport.equals(retVal, ""))
            {
                //if multiple errors
                retVal += "\r\n";
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "AK3"))
            {
                retVal += "Segment " + Segments[i].Get(1) + ": " + GetSegmentSyntaxError(Segments[i].Get(4));
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "AK4"))
            {
                retVal += "Element " + Segments[i].Get(1) + ": " + GetElementSyntaxError(Segments[i].Get(3));
            }
             
        }
        return retVal;
    }

    //retVal+=GetRejectReason(Segments[i].Get(3))+", "
    //	+GetFollowupAction(Segments[i].Get(4));
    /*Example of 997 from failed 270 request.
    		ISA*00*          *00*          *30*330989922      *ZZ*810624427      *090819*1501*U*00401*000000000*0*T*:~
    		GS*FA*330989922*330989922*20090819*1501*0*X*004010~
    		ST*997*0001~
    		AK1*HS*26~
    		AK2*270*0001~
    		AK3*NM1*4**8~
    		AK4*9*725*4*1~
    		AK5*R*5~
    		AK9*R*1*1*0~
    		SE*8*0001~
    		GE*1*0~
    		IEA*1*000000000~
    		 */
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
            return "Mandatory segment missing";
        }
        else if (__dummyScrutVar0.equals("4"))
        {
            return "Loop Occurs Over Maximum Times";
        }
        else if (__dummyScrutVar0.equals("5"))
        {
            return "Segment Exceeds Maximum Use";
        }
        else if (__dummyScrutVar0.equals("6"))
        {
            return "Segment Not in Defined Transaction Set";
        }
        else if (__dummyScrutVar0.equals("7"))
        {
            return "Segment Not in Proper Sequence";
        }
        else if (__dummyScrutVar0.equals("8"))
        {
            return "Segment Has Data Element Errors";
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
            return "Mandatory data element missing";
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
            return "Invalid Date";
        }
        else if (__dummyScrutVar1.equals("9"))
        {
            return "Invalid Time";
        }
        else if (__dummyScrutVar1.equals("10"))
        {
            return "Exclusion Condition Violated";
        }
        else
        {
            return code;
        }          
    }

}


//will never happen