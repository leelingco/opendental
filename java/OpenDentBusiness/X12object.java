//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.X12FunctionalGroup;
import OpenDentBusiness.X12Segment;
import OpenDentBusiness.X12Separators;
import OpenDentBusiness.X12Transaction;

/**
* Encapsulates one entire X12 Interchange object, including multiple functional groups and transaction sets. It does not care what type of transactions are contained.  It just stores them.  It does not inherit either.  It is up to other classes to use this as needed.
*/
public class X12object   
{
    /**
    * usually *,:,and ~
    */
    public X12Separators Separators = new X12Separators();
    /**
    * A collection of X12FunctionalGroups.
    */
    public List<X12FunctionalGroup> FunctGroups = new List<X12FunctionalGroup>();
    /**
    * All segments for the entiremessage.
    */
    public List<X12Segment> Segments = new List<X12Segment>();
    public static boolean isX12(String messageText) throws Exception {
        if (messageText == null || messageText.Length < 106)
        {
            return false;
        }
         
        if (StringSupport.equals(messageText.Substring(0, 3), "ISA"))
        {
            return true;
        }
         
        return false;
    }

    /**
    * This override is never explicitly used.
    */
    protected X12object() throws Exception {
    }

    /**
    * Takes raw text and converts it into an X12Object.
    */
    public X12object(String messageText) throws Exception {
        messageText = messageText.Replace("\r", "");
        messageText = messageText.Replace("\n", "");
        if (!StringSupport.equals(messageText.Substring(0, 3), "ISA"))
        {
            throw new ApplicationException("ISA not found");
        }
         
        Separators = new X12Separators();
        Separators.Element = messageText.Substring(3, 1);
        Separators.Subelement = messageText.Substring(104, 1);
        Separators.Segment = messageText.Substring(105, 1);
        String[] messageRows = messageText.Split(new String[]{ Separators.Segment }, StringSplitOptions.None);
        FunctGroups = new List<X12FunctionalGroup>();
        Segments = new List<X12Segment>();
        String row = new String();
        X12Segment segment;
        for (int i = 1;i < messageRows.Length;i++)
        {
            row = messageRows[i];
            segment = new X12Segment(row,Separators);
            Segments.Add(segment);
            if (StringSupport.equals(messageRows[i], ""))
            {
            }
            else //do nothing
            if (StringSupport.equals(segment.SegmentID, "IEA"))
            {
            }
            else //if end of interchange
            //do nothing
            if (StringSupport.equals(segment.SegmentID, "GS"))
            {
                //if new functional group
                FunctGroups.Add(new X12FunctionalGroup(segment));
            }
            else if (StringSupport.equals(segment.SegmentID, "GE"))
            {
            }
            else //if end of functional group
            //do nothing
            if (StringSupport.equals(segment.SegmentID, "ST"))
            {
                //if new transaction set
                if (lastGroup().Transactions == null)
                {
                    lastGroup().Transactions = new List<X12Transaction>();
                }
                 
                lastGroup().Transactions.Add(new X12Transaction(segment));
            }
            else if (StringSupport.equals(segment.SegmentID, "SE"))
            {
            }
            else //if end of transaction
            //do nothing
            if (StringSupport.equals(segment.SegmentID, "TA1"))
            {
            }
            else
            {
                //This segment can either replace or supplement any GS segments for any ack type (997,999,277).  The TA1 will always be before the first GS segment.
                //Ignore for now.  We should eventually match TA101 with the ISA13 of the claim that we sent, so we can report the status to the user using fields TA104 and TA105.
                //This segment is neither mandated or prohibited (see 277.pdf pg. 207).
                //it must be a detail segment within a transaction.
                if (lastTransaction().Segments == null)
                {
                    lastTransaction().Segments = new List<X12Segment>();
                }
                 
                lastTransaction().Segments.Add(segment);
            }       
        }
    }

    //row=sr.ReadLine();
    /**
    * Example of values returned: 004010X097A1 (4010 dental), 005010X222A1 (5010 medical), 005010X223A2 (5010 institutional), 005010X224A2 (5010 dental)
    */
    public String getFormat() throws Exception {
        for (int i = 0;i < Segments.Count;i++)
        {
            if (StringSupport.equals(Segments[i].SegmentID, "GS"))
            {
                return Segments[i].Get(8);
            }
             
        }
        return "";
    }

    /**
    * Returns true if the X12 object is in 4010 format.
    */
    public boolean isFormat4010() throws Exception {
        String format = getFormat();
        if (format.Length >= 6)
        {
            return (StringSupport.equals(format.Substring(2, 4), "4010"));
        }
         
        return false;
    }

    /**
    * Returns true if the X12 object is in 5010 format.
    */
    public boolean isFormat5010() throws Exception {
        String format = getFormat();
        if (format.Length >= 6)
        {
            return (StringSupport.equals(format.Substring(2, 4), "5010"));
        }
         
        return false;
    }

    /**
    * Returns true if there is a TA1 segment. The TA1 segment is neither mandated or prohibited (see 277.pdf pg. 207).
    * The Inmidiata clearinghouse likes to use TA1 segments to replace the usual acknowledgements (format ISA-TA1-IEA).
    */
    public boolean isAckInterchange() throws Exception {
        for (int i = 0;i < Segments.Count;i++)
        {
            if (StringSupport.equals(Segments[i].SegmentID, "GS"))
            {
                return false;
            }
             
        }
        for (int i = 0;i < Segments.Count;i++)
        {
            //If a GS is present, it will get handled elsewhere.
            if (StringSupport.equals(Segments[i].SegmentID, "TA1"))
            {
                return true;
            }
             
        }
        return false;
    }

    //A TA1 can be used when there are no GS segments.  That implies that it is an interchange ack.
    public boolean is997() throws Exception {
        //There is only one transaction set (ST/SE) per functional group (GS/GE), but I think there can be multiple functional groups
        //if acking multiple
        if (this.FunctGroups.Count != 1)
        {
            return false;
        }
         
        if (StringSupport.equals(this.FunctGroups[0].Transactions[0].Header.Get(1), "997"))
        {
            return true;
        }
         
        return false;
    }

    public boolean is999() throws Exception {
        //There is only one transaction set (ST/SE) per functional group (GS/GE).
        if (this.FunctGroups.Count != 1)
        {
            return false;
        }
         
        if (StringSupport.equals(this.FunctGroups[0].Transactions[0].Header.Get(1), "999"))
        {
            return true;
        }
         
        return false;
    }

    public boolean is271() throws Exception {
        if (StringSupport.equals(this.FunctGroups[0].Transactions[0].Header.Get(1), "271"))
        {
            return true;
        }
         
        return false;
    }

    private X12FunctionalGroup lastGroup() throws Exception {
        return (X12FunctionalGroup)FunctGroups[FunctGroups.Count - 1];
    }

    private X12Transaction lastTransaction() throws Exception {
        return (X12Transaction)lastGroup().Transactions[lastGroup().Transactions.Count - 1];
    }

}


