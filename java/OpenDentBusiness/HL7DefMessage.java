//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.SegmentNameHL7;
import OpenDentBusiness.TableBase;

/**
* There is no field for MessageStructureHL7 (ADT_A01), because that will be inferred. Defined in HL7 specs, section 2.16.3.
*/
public class HL7DefMessage  extends TableBase 
{
    /**
    * Primary key.
    */
    public long HL7DefMessageNum = new long();
    /**
    * FK to HL7Def.HL7DefNum
    */
    public long HL7DefNum = new long();
    /**
    * Stored in db as string, but used in OD as enum MessageTypeHL7. Example: ADT
    */
    public MessageTypeHL7 MessageType = MessageTypeHL7.NotDefined;
    /**
    * Stored in db as string, but used in OD as enum EventTypeHL7. Example: A04, which is only used iwth ADT/ACK.
    */
    public EventTypeHL7 EventType = EventTypeHL7.NotDefined;
    /**
    * Enum:InOutHL7 Incoming, Outgoing
    */
    public InOutHL7 InOrOut = InOutHL7.Incoming;
    /**
    * The only purpose of this column is to let you change the order in the HL7 Def windows.  It's just for convenience.
    */
    public int ItemOrder = new int();
    /**
    * text
    */
    public String Note = new String();
    //VendorCustomized, an enumeration.  Example: PDF TPs.
    /**
    * List of segments associated with this hierarchical definition.  Use items in this list to get to items lower in the hierarchy.
    */
    public List<HL7DefSegment> hl7DefSegments = new List<HL7DefSegment>();
    /**
    * 
    */
    public HL7DefMessage clone() {
        try
        {
            return (HL7DefMessage)this.MemberwiseClone();
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

    public void addSegment(HL7DefSegment seg, int itemOrder, boolean canRepeat, boolean isOptional, SegmentNameHL7 segmentName, String note) throws Exception {
        if (hl7DefSegments == null)
        {
            hl7DefSegments = new List<HL7DefSegment>();
        }
         
        seg.ItemOrder = itemOrder;
        seg.CanRepeat = canRepeat;
        seg.IsOptional = isOptional;
        seg.SegmentName = segmentName;
        seg.Note = note;
        this.hl7DefSegments.Add(seg);
    }

    public void addSegment(HL7DefSegment seg, int itemOrder, boolean canRepeat, boolean isOptional, SegmentNameHL7 segmentName) throws Exception {
        addSegment(seg,itemOrder,canRepeat,isOptional,segmentName,"");
    }

    public void addSegment(HL7DefSegment seg, int itemOrder, SegmentNameHL7 segmentName) throws Exception {
        addSegment(seg,itemOrder,false,false,segmentName,"");
    }

}


