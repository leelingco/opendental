//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.SegmentNameHL7;

public class MessageHL7   
{
    public List<SegmentHL7> Segments = new List<SegmentHL7>();
    private String originalMsgText = new String();
    //We'll store this for now, but I don't think we'll use it.
    public MessageTypeHL7 MsgType = MessageTypeHL7.NotDefined;
    public EventTypeHL7 EventType = EventTypeHL7.NotDefined;
    public String ControlId = new String();
    public String AckCode = new String();
    /**
    * We will grab the event type sent to us to echo back to eCW in acknowledgment. All ADT's and SIU's will be treated the same, so while they may send an event type we do not have in our enumeration, we still want to process it and send back the ACK with the correct event type.
    */
    public String AckEvent = new String();
    /**
    * Only use this constructor when generating a message instead of parsing a message.
    */
    public MessageHL7(MessageTypeHL7 msgType) throws Exception {
        Segments = new List<SegmentHL7>();
        MsgType = msgType;
        ControlId = "";
        AckCode = "";
        AckEvent = "";
    }

    public MessageHL7(String msgtext) throws Exception {
        AckCode = "";
        ControlId = "";
        AckEvent = "";
        originalMsgText = msgtext;
        Segments = new List<SegmentHL7>();
        String[] rows = msgtext.Split(new String[]{ "\r", "\n" }, StringSplitOptions.RemoveEmptyEntries);
        SegmentHL7 segment;
        for (int i = 0;i < rows.Length;i++)
        {
            segment = new SegmentHL7(rows[i]);
            //this creates the field objects.
            Segments.Add(segment);
            if (i == 0 && segment.Name == SegmentNameHL7.MSH)
            {
                //js 7/3/12 Make this more intelligent because we also now need the suffix
                String msgtype = segment.getFieldComponent(8,0);
                //We force the user to leave the 'messageType' field in this position, position 8 of the MSH segment
                String evnttype = segment.getFieldComponent(8,1);
                AckEvent = evnttype;
                //We will use this when constructing the acknowledgment to echo back to sender the same event type sent to us
                //If message type or event type are not in this list, they will default to the not supported type and will not be processed
                if (StringSupport.equals(msgtype, MessageTypeHL7.ADT.ToString()))
                {
                    MsgType = MessageTypeHL7.ADT;
                }
                else if (StringSupport.equals(msgtype, MessageTypeHL7.ACK.ToString()))
                {
                    MsgType = MessageTypeHL7.ACK;
                }
                else if (StringSupport.equals(msgtype, MessageTypeHL7.SIU.ToString()))
                {
                    MsgType = MessageTypeHL7.SIU;
                }
                else if (StringSupport.equals(msgtype, MessageTypeHL7.DFT.ToString()))
                {
                    MsgType = MessageTypeHL7.DFT;
                }
                    
                if (StringSupport.equals(evnttype, EventTypeHL7.A04.ToString()))
                {
                    EventType = EventTypeHL7.A04;
                }
                else if (StringSupport.equals(evnttype, EventTypeHL7.P03.ToString()))
                {
                    EventType = EventTypeHL7.P03;
                }
                else if (StringSupport.equals(evnttype, EventTypeHL7.S12.ToString()))
                {
                    EventType = EventTypeHL7.S12;
                }
                   
            }
             
        }
    }

    /**
    * This will always be generated on the fly, based on the FullText of all the segments combined.  FullText for any other object is cached rather than being generated on the fly.
    */
    public String toString() {
        try
        {
            String retVal = "";
            for (int i = 0;i < Segments.Count;i++)
            {
                if (i > 0)
                {
                    retVal += "\r\n";
                }
                 
                //in our generic HL7 interface, we should change this to just an \r aka 0D aka \\u000d
                retVal += Segments[i].FullText;
            }
            return retVal;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * If an optional segment is not present, it will return null.
    */
    public SegmentHL7 getSegment(SegmentNameHL7 segmentName, boolean isRequired) throws Exception {
        for (int i = 0;i < Segments.Count;i++)
        {
            if (Segments[i].Name == segmentName)
            {
                return Segments[i];
            }
             
        }
        if (isRequired)
        {
            throw new ApplicationException(segmentName + " segment is missing.");
        }
         
        return null;
    }

}


/*
		///<summary>The list will be ordered by sequence number.</summary>
		public List<SegmentHL7> GetSegments(SegmentName segmentName) {
			List<SegmentHL7> retVal=new List<SegmentHL7>();
			for(int i=0;i<Segments.Count;i++) {
				if(Segments[i].Name!=segmentName) {
					continue;
				}
				if(Segments[i].GetFieldFullText(1) != (retVal.Count+1).ToString()){//wrong sequence number
					continue;
				}
				retVal.Add(Segments[i]);
			}
			return retVal;
		}*/